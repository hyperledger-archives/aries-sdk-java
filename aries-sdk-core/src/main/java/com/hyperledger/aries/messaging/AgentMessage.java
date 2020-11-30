package com.hyperledger.aries.messaging;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

public class AgentMessage {
    @SerializedName("@type")
    private String type;

    @SerializedName("@id")
    private String id;

    public AgentMessage(String type) {
        this.type = type;
        this.id = UUID.randomUUID().toString();
    }

    public AgentMessage(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public static Optional<AgentMessage> from(String rawJson) {
        try {
            AgentMessage message = new Gson().fromJson(rawJson, AgentMessage.class);
            ofNullable(message.getType()).orElseThrow(() -> new IllegalArgumentException("invalid @type"));
            ofNullable(message.getId()).orElseThrow(() -> new IllegalArgumentException("invalid @id"));
            return Optional.of(message);

        } catch (Exception e) {
            // TODO: msg to log
            String msg = String.format("failed to parse message. rawJson: %s, err: %s", rawJson,  e.getMessage());
        }
        return Optional.empty();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
