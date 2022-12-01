package ru.kata.spring.boot_security.demo.models;

import com.fasterxml.jackson.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

    public Role() {

    }

    public Role(String name) {
        this.name=name;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((id==null) ? 0 : id.hashCode());
        result = 31 * result + ((name==null) ? 0 : name.hashCode());
        return result;
    }
}
