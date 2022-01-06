package ru.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Menu extends BaseEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String name;

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "price")
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    public Menu(Integer id, String name, LocalDateTime dateTime, Integer price, Restaurant restaurant) {
        super(id);
        this.name = name;
        this.dateTime = dateTime;
        this.price = price;
        this.restaurant = restaurant;
    }
}
