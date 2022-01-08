package ru.restaurant_voting.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = {CascadeType.MERGE}, orphanRemoval = true)
    @CollectionTable
    private List<Menu> menu;

    public Restaurant(Integer id, String name, List<Menu> menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", menu:" + menu +
                '}';
    }
}
