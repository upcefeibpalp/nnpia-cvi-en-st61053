package nnpia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String password;

    // Vztah 1:1, kde sloupec address_id v tabulce app_user odkazuje na sloupec id v tabulce address.
    @ManyToOne(cascade = CascadeType.PERSIST, optional = true)

    // @JoinColumn(name = "addressId", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private Address address;


    public User(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, Address address) {
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
