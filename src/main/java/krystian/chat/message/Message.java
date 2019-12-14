package krystian.chat.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import krystian.chat.room.ChatRoom;
import krystian.chat.user.ChatUser;

import javax.persistence.*;

/**
 *
 */

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String content;

    @OneToOne
    @JsonIgnore
    private ChatUser sender;

    private Long timestamp;

    @JsonIgnore
    @ManyToOne
    private ChatRoom room;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatUser getSender() {
        return sender;
    }

    public void setSender(ChatUser sender) {
        this.sender = sender;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    @JsonProperty
    public long getSenderId() {
        return sender.getId();
    }
}
