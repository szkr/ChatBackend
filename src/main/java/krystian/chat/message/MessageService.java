package krystian.chat.message;

import krystian.chat.room.ChatRoom;
import krystian.chat.user.ChatUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class MessageService {
    private final MessageRepository messageRepository;


    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages(ChatRoom room, long since) {
        return messageRepository.findByRoomAndTimestampAfter(room, since);
    }

    public Optional<Message> sendMessage(Message m, ChatUser user, ChatRoom room){
        if (room.isEmpty() || !room.getUsers().contains(user))
            return Optional.empty();
        m.setTimestamp(System.currentTimeMillis());
        m.setSender(user);
        m.setRoom(room);
        messageRepository.save(m);
        return Optional.of(m);
    }
}
