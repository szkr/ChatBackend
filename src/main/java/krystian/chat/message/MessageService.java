package krystian.chat.message;

import krystian.chat.room.ChatRoom;
import krystian.chat.room.ChatRoomRepository;
import krystian.chat.room.RoomMessages;
import krystian.chat.room.RoomTime;
import krystian.chat.user.ChatUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;


    public MessageService(MessageRepository messageRepository, ChatRoomRepository chatRoomRepository) {
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
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

    public List<RoomMessages> getNewMessages(ChatUser user, List<RoomTime> rooms) {
        List<RoomMessages> response = new ArrayList<>();
        rooms.forEach(r -> {
            chatRoomRepository.findByIdAndUsersContaining(r.getRoomId(), user).ifPresent(room -> {
                RoomMessages rm = new RoomMessages();
                rm.setRoomId(room.getId());
                rm.setMessages(getMessages(room, r.getTimeFrom()));
                response.add(rm);
            });
        });
        return response;
    }
}
