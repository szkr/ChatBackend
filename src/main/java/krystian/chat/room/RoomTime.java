package krystian.chat.room;

/**
 *
 */
public class RoomTime {
    private long roomId;
    private long timeFrom;

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public void setTimeFrom(long timeFrom) {
        this.timeFrom = timeFrom;
    }

    public long getRoomId() {
        return roomId;
    }

    public long getTimeFrom() {
        return timeFrom;
    }
}
