package com.hyperledger.aries.protocols.connections;

import com.hyperledger.aries.messaging.AgentMessage;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionInvitationTest {
    @Test
    public void testCreateInvitationWithDid() {
        // GIVEN
        String expectedDid = "did:sov:QmWbsNYhMrjHiqZDTUTEJs";

        // WHEN
        ConnectionInvitation actual = new ConnectionInvitation(expectedDid);

        // THEN
        assertEquals(MessageType.CONNECTION_INVITATION.getMessageType(), actual.getType());
        assertEquals(expectedDid, actual.getDid());
    }

    @Test
    public void testCreateInvitationWithDidAndId() {
        // GIVEN
        String expectedDid = "did:sov:QmWbsNYhMrjHiqZDTUTEJs";
        String expectedId = "12345678900987654321";

        // WHEN
        ConnectionInvitation actual = new ConnectionInvitation(expectedDid, expectedId);

        // THEN
        assertEquals(MessageType.CONNECTION_INVITATION.getMessageType(), actual.getType());
        assertEquals(expectedId, actual.getId());
        assertEquals(expectedDid, actual.getDid());
    }

    @Test
    public void testCreateInvitationWithKeys() {
        // GIVEN
        List<String> recipientKeys = Arrays.asList("8HH5gYEeNc3z7PYXmd54d4x6qAfCNrqQqEB3nS7Zfu7K");
        String serviceEndpoint = "https://example.com/endpoint";
        List<String> routingKeys = Arrays.asList("8HH5gYEeNc3z7PYXmd54d4x6qAfCNrqQqEB3nS7Zfu7K");

        // WHEN
        ConnectionInvitation actual = new ConnectionInvitation(recipientKeys, serviceEndpoint, routingKeys);

        // THEN
        assertEquals(MessageType.CONNECTION_INVITATION.getMessageType(), actual.getType());
        assertEquals(recipientKeys, actual.getRecipientKeys());
        assertEquals(serviceEndpoint, actual.getServiceEndpoint());
        assertEquals(routingKeys, actual.getRoutingKeys());
    }

    @Test
    public void testParseInvitationWithDid() {
        // GIVEN
        String rawJson = "{\n" +
                "\"@type\": \"https://didcomm.org/connections/1.0/invitation\",\n" +
                "\"@id\":\"21d4f8b3-2028-4d7e-8fe4-7618c9e9471c\",\n" +
                "\"did\": \"did:sov:QmWbsNYhMrjHiqZDTUTEJs\"\n" +
                "}";

        // WHEN
        Optional<AgentMessage> actual = ConnectionInvitation.from(rawJson);

        // THEN
        assertTrue(actual.isPresent());
        assertTrue(actual.get() instanceof ConnectionInvitation);
        ConnectionInvitation message = (ConnectionInvitation) actual.get();
        assertEquals("https://didcomm.org/connections/1.0/invitation", message.getType());
        assertEquals("21d4f8b3-2028-4d7e-8fe4-7618c9e9471c", message.getId());
        assertEquals("did:sov:QmWbsNYhMrjHiqZDTUTEJs", message.getDid());
    }

    @Test
    public void testParseInvitationWithKeys() {
        // GIVEN
        String rawJson = "{\n" +
                "\"@type\": \"https://didcomm.org/connections/1.0/invitation\",\n" +
                "\"@id\":\"21d4f8b3-2028-4d7e-8fe4-7618c9e9471c\",\n" +
                "\"recipientKeys\": [\"8HH5gYEeNc3z7PYXmd54d4x6qAfCNrqQqEB3nS7Zfu7K\"],\n" +
                "\"serviceEndpoint\":\"https://example.com/endpoint\",\n" +
                "\"routingKeys\": [\"8HH5gYEeNc3z7PYXmd54d4x6qAfCNrqQqEB3nS7Zfu7K\"]\n" +
                "}";

        // WHEN
        Optional<AgentMessage> actual = ConnectionInvitation.from(rawJson);

        // THEN
        assertTrue(actual.isPresent());
        assertTrue(actual.get() instanceof ConnectionInvitation);
        ConnectionInvitation message = (ConnectionInvitation) actual.get();
        assertEquals("https://didcomm.org/connections/1.0/invitation", message.getType());
        assertEquals("21d4f8b3-2028-4d7e-8fe4-7618c9e9471c", message.getId());
        assertEquals("8HH5gYEeNc3z7PYXmd54d4x6qAfCNrqQqEB3nS7Zfu7K", message.getRecipientKeys().get(0));
        assertEquals("https://example.com/endpoint", message.getServiceEndpoint());
        assertEquals("8HH5gYEeNc3z7PYXmd54d4x6qAfCNrqQqEB3nS7Zfu7K", message.getRoutingKeys().get(0));
    }
}
