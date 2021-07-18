package com.example.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private User user;
    private Set<Game>games;

    public Order() {
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User buyer) {
        this.user = buyer;
    }

    @ManyToMany
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
