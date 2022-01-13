package ru.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(name = "vote_user_id_idx", columnNames = {"user_id"})})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateTime;

    public Vote(Integer id, User user, Restaurant restaurant) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.dateTime = LocalDateTime.now();
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDateTime dateTime) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", userId=" + user +
                ", restaurantId=" + restaurant +
                '}';
    }
}
