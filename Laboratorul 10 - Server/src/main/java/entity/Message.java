package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
@NamedQuery(name = "Message.findByPersonId",
        query = "select e from Message e where e.friendId = ?1")
public class Message {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "friend_id")
    private Integer friendId;

    @Column(name = "message")
    private String text;

    public Message() {
    }

    public Message(Integer personId, Integer friendId, String text) {
        this.personId = personId;
        this.friendId = friendId;
        this.text = text;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", personId=" + personId +
                ", friendId=" + friendId +
                ", text='" + text + '\'' +
                '}';
    }
}
