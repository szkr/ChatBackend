package krystian.chat.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 */
public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    Optional<ChatUser> findBySessionId(String lastName);

    boolean existsByName(String name);
}
