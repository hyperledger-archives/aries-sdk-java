package com.hyperledger.aries.storage;

import org.hyperledger.indy.sdk.IndyException;
import org.hyperledger.indy.sdk.wallet.Wallet;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertFalse;
import static org.hyperledger.indy.sdk.wallet.Wallet.*;
import static org.junit.Assert.*;

class TestRecord extends BaseRecord {

    @Override
    String getType() {
        return "test-type";
    }

    @Override
    String getTagsJson() {
        return "{}";
    }
}

public class BaseRecordTest {
    private String config;
    private String credentials;
    private Wallet wallet;

    @Before
    public void Setup() throws IndyException, ExecutionException, InterruptedException {
        config = new JSONObject().put("id", "wallet_id").toString();
        credentials = new JSONObject().put("key", "wallet_key").toString();
        createWallet(config, credentials).get();
        wallet = openWallet(config, credentials).get();
    }

    @After
    public void Cleanup() throws IndyException, ExecutionException, InterruptedException {
        wallet.closeWallet().get();
        deleteWallet(config, credentials).get();
    }

    @Test
    public void testSaveRecord() throws IndyException, ExecutionException, InterruptedException {
        // GIVEN
        BaseRecord record = new TestRecord();

        // WHEN
        String actual = record.save(wallet);

        // THEN
        assertEquals(record.id, actual);
        assertNotNull(record.createdAt);
        assertNotNull(record.updatedAt);
    }

    @Test
    public void testRetrieveRecordById() throws IndyException, ExecutionException, InterruptedException {
        // GIVEN
        BaseRecord record = new TestRecord();
        String recordId = record.save(wallet);

        // WHEN
        RecordOption option = new RecordOption();
        TestRecord actual = BaseRecord.retrieveById(wallet, record.getType(), recordId, option, TestRecord.class);

        // THEN
        assertNotNull(actual);
        assertEquals(recordId, actual.id);
    }

    @Test
    public void testRetrieveRecord() throws IndyException, ExecutionException, InterruptedException {
        // GIVEN
        BaseRecord record = new TestRecord();
        record.save(wallet);

        // WHEN
        SearchOption option = new SearchOption();
        List<TestRecord> actual = BaseRecord.retrieve(wallet, record.getType(), "{}", 10, option, TestRecord.class);

        // THEN
        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());
    }

    @Test
    public void testDeleteRecord() throws IndyException, ExecutionException, InterruptedException {
        // GIVEN
        BaseRecord record = new TestRecord();
        record.save(wallet);

        // WHEN
        record.delete(wallet);

        // THEN
        assertNull(record.id);
    }
}
