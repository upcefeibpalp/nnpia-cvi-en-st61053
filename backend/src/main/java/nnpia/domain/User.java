package nnpia.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString

public class User {
    private Long id;
    private String password;
    private String email;


}
