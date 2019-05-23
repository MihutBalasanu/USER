package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "authentications")
public class Authentication extends BaseModel {

    @Id
    @Column(name = "token")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authentication)) return false;
        Authentication that = (Authentication) o;
        return Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getCreationTime(), that.getCreationTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken(), getUser(), getCreationTime());
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "token=" + token +
                ", user=" + user +
                ", creationTime=" + creationTime +
                '}';
    }
}
