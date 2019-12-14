package krystian.chat.message;

import krystian.chat.room.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoomAndTimestampAfter(ChatRoom room, long timestamp);
}
