package ru.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.restaurant_voting.config.json.VoteDeserializer;
import ru.restaurant_voting.config.json.VoteSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(name = "vote_user_date_idx", columnNames = {"user_id", "date"})})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonDeserialize(using = VoteDeserializer.class)
@JsonSerialize(using = VoteSerializer.class)
public class Vote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;

    public Vote(Integer id, User user, Restaurant restaurant) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.date = LocalDate.now();
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate localDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.date = localDate;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                '}';
    }
}
