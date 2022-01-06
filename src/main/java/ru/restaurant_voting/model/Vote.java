package ru.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(name = "vote_user_id_idx", columnNames = {"user_id"})})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    @NotNull
    private Integer userId;

    @Column(name = "restaurant_id", nullable = false)
    @NotNull
    private Integer restaurantId;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime date;

    public Vote(Integer id, Integer userId, Integer restaurantId) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.date = LocalDateTime.now();
    }
}
