package krystian.chat.user;

import krystian.chat.room.ChatRoomRepository;
import krystian.chat.room.ChatRoomService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 */
@Service
public class ChatUserService {
    private final ChatUserRepository chatUserRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;


    public ChatUserService(ChatUserRepository chatUserRepository, ChatRoomRepository chatRoomRepository, ChatRoomService chatRoomService) {
        this.chatUserRepository = chatUserRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomService = chatRoomService;
    }

    public Optional<ChatUser> getUserForSessionId(String sessionId) {
        return chatUserRepository.findBySessionId(sessionId);
        //return users.stream().filter(u -> u.session.getId().equals(session.getId())).findAny();
    }

    public Optional<ChatUser> getUserById(long id) {
        return chatUserRepository.findById(id);
    }

    public Optional<ChatUser> addUser(String name, String url, String sessionId) {
        Optional<ChatUser> exists = getUserForSessionId(sessionId);
        if (exists.isPresent())
            return exists;

        if (chatUserRepository.existsByName(name))
            return Optional.empty();

        ChatUser user = new ChatUser();
        user.setName(name);
        user.setPictureUrl(url);
        user.setSessionId(sessionId);
        chatUserRepository.save(user);
        return Optional.ofNullable(user);
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        Optional<ChatUser> server = addUser("SERVER", "", "SxiAdZAqLhSSme8B2rV4");
        chatRoomService.createRoom(server.get(), "Main").ifPresent(r->{
            r.setPublic(true);
            chatRoomRepository.save(r);
        });
    }
}
