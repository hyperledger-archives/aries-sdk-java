package com.hyperledger.aries.protocols.connections;

public enum MessageType {
    CONNECTION_INVITATION("https://didcomm.org/connections/1.0/invitation"),
    CONNECTION_REQUEST("https://didcomm.org/connections/1.0/request"),
    CONNECTION_RESPONSE("https://didcomm.org/connections/1.0/response"),
    PROBLEM_REPORT("https://didcomm.org/connections/1.0/problem_report");

    String connectionMessageType;

    MessageType(String connectionMessageType) {
        this.connectionMessageType = connectionMessageType;
    }

    public String getMessageType() {
        return connectionMessageType;
    }
}
