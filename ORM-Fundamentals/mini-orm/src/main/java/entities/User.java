package entities;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

import java.util.Date;

@Entity(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "age")
    private int age;

    @Column(name = "registration")
    private Date registration;

    public User (String username, int age, Date registration){
        this.username = username;
        this.age = age;
        this.registration = registration;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setRegistration(Date registration) {
        this.registration = registration;
    }

    public Date getRegistration() {
        return registration;
    }
}
