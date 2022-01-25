package com.hyperledger.aries.storage;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.hyperledger.indy.sdk.IndyException;
import org.hyperledger.indy.sdk.non_secrets.WalletRecord;
import org.hyperledger.indy.sdk.non_secrets.WalletSearch;
import org.hyperledger.indy.sdk.wallet.Wallet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.hyperledger.indy.sdk.non_secrets.WalletRecord.*;
import static org.hyperledger.indy.sdk.non_secrets.WalletSearch.open;

// TODO: CompletableFuture style (return type and chaining)
abstract class BaseRecord<T> {
    String id;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updatedAt;

    static <T extends BaseRecord> T retrieveById(Wallet wallet, String type, String id, RecordOption option, Class<T> clazz) throws IndyException, ExecutionException, InterruptedException {
        Gson encoder = new Gson();
        String optionJson = encoder.toJson(option);
        String found = WalletRecord.get(wallet, type, id, optionJson).get();
        RecordResult record = encoder.fromJson(found, RecordResult.class);
        return encoder.fromJson(record.value, clazz);
    }

    static <T extends BaseRecord> List<T> retrieve(Wallet wallet, String type, String query, int pageSize, SearchOption option, Class<T> clazz) throws IndyException, ExecutionException, InterruptedException {
        Gson encoder = new Gson();
        String optionJson = encoder.toJson(option);
        try (WalletSearch handler = open(wallet, type, query, optionJson).get()) {
            String found = handler.fetchNextRecords(wallet, pageSize).get();
            SearchResult result = encoder.fromJson(found, SearchResult.class);
            return result.records.stream()
                    .map(r -> encoder.fromJson(r.value, clazz))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return emptyList();
        }
    }

    private String toJson() {
        Gson encoder = new Gson();
        return encoder.toJson(this);
    }

    abstract String getType();

    abstract String getTagsJson();

    String save(Wallet wallet) throws IndyException, ExecutionException, InterruptedException {
        updatedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date());

        boolean created = true;
        if (createdAt == null) {
            if (null == id) {
                id = UUID.randomUUID().toString();
            }
            createdAt = updatedAt;
            created = false;
        }

        String type = getType();
        String value = toJson();
        String tagsJson = getTagsJson();

        if (!created) {
            add(wallet, type, id, value, tagsJson).get();
            addTags(wallet, type, id, tagsJson).get();
        } else {
            updateValue(wallet, type, id, value).get();
            updateTags(wallet, type, id, tagsJson).get();
        }

        return id;
    }

    void delete(Wallet wallet) throws IndyException, ExecutionException, InterruptedException {
        if (this.createdAt == null) {
            return;
        }

        String type = getType();
        WalletRecord.delete(wallet, type, id).get();
        createdAt = null;
    }
}
