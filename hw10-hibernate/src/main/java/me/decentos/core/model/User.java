package me.decentos.core.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = AddressDataSet.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "address_id")
    private AddressDataSet address;

    @OneToMany(targetEntity = PhoneDataSet.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    private Set<PhoneDataSet> phones = new HashSet<>();

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public void addPhone(PhoneDataSet phone) {
        this.phones.add(phone);
    }

    public void removePhone(PhoneDataSet phone) {
        this.phones.remove(phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
