package krystian.chat.room;

import krystian.chat.message.Message;

import java.util.List;

/**
 *
 */
public class RoomMessages {
    private long roomId;
    private List<Message> messages;

    public long getRoomId() {
        return roomId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
