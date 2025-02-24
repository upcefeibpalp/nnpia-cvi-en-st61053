package nnpia.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

@Entity
public class Address {

    @Id
    private long id;
    private String street;
    private String city;
    private String zip;
}
