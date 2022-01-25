package com.hyperledger.aries.messaging;

import org.junit.Test;

import java.util.Optional;

import static com.hyperledger.aries.protocols.connections.MessageType.CONNECTION_INVITATION;
import static org.junit.Assert.*;

public class AgentMessageTest {

    @Test
    public void testCreateBasicMessage() {
        // GIVEN
        String expectedType = CONNECTION_INVITATION.toString();
        String expectedId = "12345678900987654321";

        // WHEN
        AgentMessage actual = new AgentMessage(expectedType, expectedId);

        // THEN
        assertEquals(expectedType, actual.getType());
        assertEquals(expectedId, actual.getId());
    }

    @Test
    public void testParseAgentMessage() {
        // GIVEN
        String rawJson = "{\n" +
                "\"@type\": \"https://didcomm.org/connections/1.0/invitation\",\n" +
                "\"@id\":\"21d4f8b3-2028-4d7e-8fe4-7618c9e9471c\"\n" +
                "}";

        // WHEN
        Optional<AgentMessage> actual = AgentMessage.from(rawJson);

        // THEN
        assertTrue(actual.isPresent());
        AgentMessage message = actual.get();
        assertEquals("https://didcomm.org/connections/1.0/invitation", message.getType());
        assertEquals("21d4f8b3-2028-4d7e-8fe4-7618c9e9471c", message.getId());
    }

    @Test
    public void testParseAgentMessageWithoutType() {
        // GIVEN
        String rawJson = "{\n" +
                "\"@id\":\"21d4f8b3-2028-4d7e-8fe4-7618c9e9471c\"\n" +
                "}";

        // WHEN
        Optional<AgentMessage> actual = AgentMessage.from(rawJson);

        // THEN
        assertFalse(actual.isPresent());
    }

    @Test
    public void testParseAgentMessageWithNullType() {
        // GIVEN
        String rawJson = "{\n" +
                "\"@type\": null,\n" +
                "\"@id\":\"21d4f8b3-2028-4d7e-8fe4-7618c9e9471c\"\n" +
                "}";

        // WHEN
        Optional<AgentMessage> actual = AgentMessage.from(rawJson);

        // THEN
        assertFalse(actual.isPresent());
    }

    @Test
    public void testParseAgentMessageWithoutId() {
        // GIVEN
        String rawJson = "{\n" +
                "\"@type\": \"https://didcomm.org/connections/1.0/invitation\",\n" +
                "}";

        // WHEN
        Optional<AgentMessage> actual = AgentMessage.from(rawJson);

        // THEN
        assertFalse(actual.isPresent());
    }

    @Test
    public void testParseAgentMessageWithNullId() {
        // GIVEN
        String rawJson = "{\n" +
                "\"@type\": \"https://didcomm.org/connections/1.0/invitation\",\n" +
                "\"@id\": null\n" +
                "}";

        // WHEN
        Optional<AgentMessage> actual = AgentMessage.from(rawJson);

        // THEN
        assertFalse(actual.isPresent());
    }
}
