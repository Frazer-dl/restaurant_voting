package ru.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.restaurant_voting.config.json.MenuDeserializer;
import ru.restaurant_voting.config.json.MenuSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(name = "menu_restaurant_date_name_idx", columnNames = {"restaurant_id", "date", "name"})})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonDeserialize(using = MenuDeserializer.class)
@JsonSerialize(using = MenuSerializer.class)
public class MenuItem extends NamedEntity {

    @Column(name = "date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    @NotNull
    private LocalDate date;

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @NotNull
    private Restaurant restaurant;

    public MenuItem(Integer id, String name, LocalDate date, Integer price, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
        this.restaurant = restaurant;
    }

    public MenuItem(Integer id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.date = LocalDate.now();
        this.price = price;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id:" + id + '\'' +
                ", name:'" + name + '\'' +
                ", price:" + price +
                '}';
    }
}
