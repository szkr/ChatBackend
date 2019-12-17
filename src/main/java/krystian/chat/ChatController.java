package krystian.chat;

import krystian.chat.message.Message;
import krystian.chat.message.MessageService;
import krystian.chat.room.ChatRoom;
import krystian.chat.room.ChatRoomService;
import krystian.chat.room.RoomMessages;
import krystian.chat.room.RoomTime;
import krystian.chat.user.ChatUser;
import krystian.chat.user.ChatUserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@RestController
//@CrossOrigin(origins = "http://192.168.1.121:4200", maxAge = 0, allowCredentials = "true")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 0, allowCredentials = "true")
@RequestMapping("/chat")
public class ChatController {

    private final ChatUserService chatUserService;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;


    public ChatController(ChatUserService chatUserService, ChatRoomService chatRoomService, MessageService messageService) {
        this.chatUserService = chatUserService;
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
    }

    @GetMapping("/messages/{roomId}/{from}")
    private List<Message> get(HttpSession session, @PathVariable long roomId, @PathVariable long from) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        if (user.isEmpty())
            return null;
        Optional<ChatRoom> room = chatRoomService.getRoomById(roomId);
        if (room.isEmpty() || !room.get().getUsers().contains(user.get()))
            return null;
        return messageService.getMessages(room.get(), from);
    }

    @PostMapping("/messages")
    private List<RoomMessages> getMessagesMulti(@RequestBody List<RoomTime> requestedRooms, HttpSession session) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        if (user.isEmpty())
            return Collections.emptyList();
        return messageService.getNewMessages(user.get(), requestedRooms);
    }

    @PostMapping("/send/{roomId}")
    private Message send(@RequestBody Message m, @PathVariable long roomId, HttpSession session) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        if (user.isEmpty())
            return null;
        Optional<ChatRoom> room = chatRoomService.getRoomById(roomId);
        if (room.isEmpty())
            return null;
        messageService.sendMessage(m, user.get(), room.get());
        return m;
    }

    @GetMapping("/me")
    private ChatUser me(HttpSession session) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        return user.orElse(null);
    }

    @PostMapping("/register")
    private ChatUser register(@RequestBody ChatUser c, HttpSession session) {
        Optional<ChatUser> user = chatUserService.addUser(c.getName(), c.getPictureUrl(), session.getId());
        return user.orElse(null);
    }

    @GetMapping("/register")
    private ChatUser register(HttpSession session) {
        Optional<ChatUser> user = chatUserService.addUser("test", "pictureUrl", session.getId());
        return user.orElse(null);
    }

    @GetMapping("/room/{id}/users")
    private List<ChatUser> getRoomMembers(HttpSession session, @PathVariable long id) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        if (user.isPresent()) {
            Optional<ChatRoom> room = chatRoomService.getRoomById(id);
            if (room.isPresent() && room.get().getUsers().contains(user.get()))
                return room.get().getUsers();
        }
        return Collections.emptyList();
    }

    @GetMapping("/user/{id}")
    private ChatUser getUser(@PathVariable long id) {
        Optional<ChatUser> user = chatUserService.getUserById(id);
        return user.orElse(null);
    }

    @GetMapping("/join/{roomId}")
    private ChatRoom join(HttpSession session, @PathVariable long roomId) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        if (user.isEmpty())
            return null;
        Optional<ChatRoom> room = chatRoomService.joinRoom(user.get(), roomId);
        return room.orElse(null);
    }

    @GetMapping("/leave/{roomId}")
    private void leave(HttpSession session, @PathVariable long roomId) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        if (user.isEmpty())
            return;
        chatRoomService.leaveRoom(user.get(), roomId);
    }

    @GetMapping("/delete/{roomId}")
    private void delete(HttpSession session, @PathVariable long roomId) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        if (user.isEmpty())
            return;
        chatRoomService.deleteRoom(user.get(), roomId);
    }

    @GetMapping("/getAvailableRooms")
    private List<ChatRoom> getAvailableRooms(HttpSession session) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        if (user.isPresent())
            return chatRoomService.getAvailableRooms(user.get());
        return Collections.emptyList();
    }

    @GetMapping("/getRoomsJoined")
    private List<ChatRoom> getRoomsJoined(HttpSession session) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        if (user.isPresent())
            return chatRoomService.getRoomsJoined(user.get());
        return Collections.emptyList();
    }

    @PostMapping("/create")
    private ChatRoom createRoom(@RequestBody ChatRoom room, HttpSession session) {
        Optional<ChatUser> user = chatUserService.getUserForSessionId(session.getId());
        if (user.isPresent()) {
            Optional<ChatRoom> chatRoom = chatRoomService.createRoom(user.get(), room);
            if (chatRoom.isPresent())
                return chatRoom.get();
        }
        return null;
    }
}
