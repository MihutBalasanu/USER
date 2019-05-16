package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notifications")
public class Notification extends BaseModel{


    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "details", length = 255)
    private String details;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "sent_time", nullable = false)
    private LocalDateTime sentTime;

    public Notification() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getDetails(), that.getDetails()) &&
                Objects.equals(getCreatedTime(), that.getCreatedTime()) &&
                Objects.equals(getSentTime(), that.getSentTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getDetails(), getCreatedTime(), getSentTime());
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", user=" + user +
                ", details='" + details + '\'' +
                ", createdTime=" + createdTime +
                ", sentTime=" + sentTime +
                '}';
    }
}
