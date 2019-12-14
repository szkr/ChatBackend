package krystian.chat.room;

import krystian.chat.message.Message;
import krystian.chat.user.ChatUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;


    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public Optional<ChatRoom> createRoom(ChatUser owner, ChatRoom baseRoom) {
        ChatRoom createdRoom = new ChatRoom(owner, baseRoom.getName());
        createdRoom.setPublic(baseRoom.isPublic());
        chatRoomRepository.save(createdRoom);
        return Optional.of(createdRoom);
    }

    public Optional<ChatRoom> createRoom(ChatUser owner, String roomName) {
        ChatRoom room = new ChatRoom();
        room.setName(roomName);
        room.setPublic(false);
        return createRoom(owner, room);

    }

    public List<ChatRoom> getAvailableRooms(ChatUser user) {

        return chatRoomRepository.findByIsPublic(true);
    }

    public Optional<ChatRoom> getRoomById(long id) {
        return chatRoomRepository.findById(id);
    }

    public List<ChatRoom> getRoomsJoined(ChatUser user) {
        return chatRoomRepository.findByUsersContaining(user);
    }

    public Optional<ChatRoom> joinRoom(ChatUser user, long roomId) {
        Optional<ChatRoom> room = chatRoomRepository.findById(roomId);
        room.ifPresent(r -> {
            r.getUsers().add(user);
            chatRoomRepository.save(r);
        });
        return room;
    }

    public void leaveRoom(ChatUser user, long roomId) {
        Optional<ChatRoom> room = chatRoomRepository.findById(roomId);
        room.ifPresent(r -> {
            r.getUsers().remove(user);
            chatRoomRepository.save(r);
        });
    }

    public void deleteRoom(ChatUser user, long roomId) {
        Optional<ChatRoom> room = chatRoomRepository.findByIdAndOwner(roomId, user);
        room.ifPresent(chatRoomRepository::delete);
    }

    public boolean sendMessage(Message m) {
        return false;
    }
}
