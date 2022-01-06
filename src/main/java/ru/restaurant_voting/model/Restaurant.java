package ru.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Menu> menu;

    public Restaurant(Integer id, String name, List<Menu> menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }
}
