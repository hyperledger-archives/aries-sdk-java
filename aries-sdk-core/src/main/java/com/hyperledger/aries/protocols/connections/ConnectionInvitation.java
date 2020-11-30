package com.hyperledger.aries.protocols.connections;

import com.google.gson.Gson;
import com.hyperledger.aries.messaging.AgentMessage;

import java.util.List;
import java.util.Optional;

public class ConnectionInvitation extends AgentMessage {
    String did;
    List<String> recipientKeys;
    List<String> routingKeys;
    String serviceEndpoint;

    ConnectionInvitation(String did) {
        super(MessageType.CONNECTION_INVITATION.getMessageType());
        this.did = did;
    }

    ConnectionInvitation(String did, String id) {
        super(MessageType.CONNECTION_INVITATION.getMessageType(), id);
        this.did = did;
    }

    ConnectionInvitation(List<String> recipientKeys, String serviceEndpoint, List<String> routingKeys) {
        super(MessageType.CONNECTION_INVITATION.getMessageType());
        this.recipientKeys = recipientKeys;
        this.serviceEndpoint = serviceEndpoint;
        this.routingKeys = routingKeys;
    }

    ConnectionInvitation(List<String> recipientKeys, String serviceEndpoint, List<String> routingKeys, String id) {
        super(MessageType.CONNECTION_INVITATION.getMessageType(), id);
        this.recipientKeys = recipientKeys;
        this.serviceEndpoint = serviceEndpoint;
        this.routingKeys = routingKeys;
    }

    public static Optional<AgentMessage> from(String rawJson) {
        try {
            ConnectionInvitation message = new Gson().fromJson(rawJson, ConnectionInvitation.class);
            return Optional.of(message);

        } catch (Exception e) {
            // TODO: msg to log
            String msg = String.format("failed to parse message. rawJson: %s, err: %s", rawJson, e.getMessage());
        }
        return Optional.empty();
    }

    public String getDid() {
        return this.did;
    }

    public List<String> getRecipientKeys() {
        return this.recipientKeys;
    }

    public String getServiceEndpoint() {
        return this.serviceEndpoint;
    }

    public List<String> getRoutingKeys() {
        return this.routingKeys;
    }
}
