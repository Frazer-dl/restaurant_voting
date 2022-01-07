package ru.restaurant_voting.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends NamedEntity {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = {CascadeType.MERGE}, orphanRemoval = true)
    @CollectionTable
    private List<Menu> menu;

    public Restaurant(Integer id, String name, List<Menu> menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }
}
