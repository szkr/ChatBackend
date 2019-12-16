package krystian.chat.room;
import krystian.chat.user.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByIsPublic(boolean isPublic);

    List<ChatRoom> findByUsersContaining(ChatUser user);

    Optional<ChatRoom> findByIdAndOwner(long id, ChatUser owner);

    Optional<ChatRoom> findByIdAndUsersContaining(long id, ChatUser user);
}
