package ru.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.restaurant_voting.config.json.RestaurantDeserializer;
import ru.restaurant_voting.config.json.RestaurantSerializer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(name = "restaurant_name_idx", columnNames = {"name"})})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonDeserialize(using = RestaurantDeserializer.class)
@JsonSerialize(using = RestaurantSerializer.class)
public class Restaurant extends NamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE) //https://stackoverflow.com/a/44988100/548473
    @JsonManagedReference
    private List<MenuItem> menu;

    public Restaurant(Integer id, String name, List<MenuItem> menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }

    public Restaurant(Restaurant r) {
        this(r.id, r.getName(), r.getMenu());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id:" + id +
                ", name:'" + name +
                '}';
    }
}
