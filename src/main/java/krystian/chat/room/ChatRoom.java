package krystian.chat.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import krystian.chat.message.Message;
import krystian.chat.user.ChatUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToOne
    @JsonIgnore
    private ChatUser owner;

    @ManyToMany
    @JsonIgnore
    private List<ChatUser> users;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "room")
    @JsonIgnore
    private List<Message> messages;

    @JsonProperty
    private boolean isPublic = false;


    public ChatRoom(ChatUser owner, String name) {
        users = new ArrayList<>();
        messages = new ArrayList<>();
        users.add(owner);
        this.owner = owner;
        this.name = name;
    }

    public ChatRoom() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatUser getOwner() {
        return owner;
    }

    public void setOwner(ChatUser owner) {
        this.owner = owner;
    }

    public List<ChatUser> getUsers() {
        return users;
    }

    public void setUsers(List<ChatUser> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @JsonProperty
    public long getOwnerId() {
        return owner.getId();
    }

    @Transient
    @JsonIgnore
    public boolean isEmpty() {
        return users.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return id == chatRoom.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
