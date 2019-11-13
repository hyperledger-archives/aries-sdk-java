package com.hyperledger.aries.messaging.connections.messages;

public enum MessageType {
    CONNECTION_INVITATION("did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/connections/1.0/invitation"),
    CONNECTION_REQUEST("did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/connections/1.0/request"),
    CONNECTION_RESPONSE("did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/connections/1.0/response"),
    PROBLEM_REPORT("did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/connections/1.0/problem_report");

    String connectionMessageType;

    MessageType(String connectionMessageType) {
        this.connectionMessageType = connectionMessageType;
    }
}
