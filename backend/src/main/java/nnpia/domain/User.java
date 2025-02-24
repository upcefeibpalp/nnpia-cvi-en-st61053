package nnpia.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

@Entity
@Table(name = "app_user")
public class User {

    @Id
    private long id;
    @Column(unique = true)
    private String email;
    private String password;

    // Vztah 1:1, kde sloupec address_id v tabulce app_user odkazuje na sloupec id v tabulce address.
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "addressId", referencedColumnName = "id", nullable = true)
    private Address address;


    public User(long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
