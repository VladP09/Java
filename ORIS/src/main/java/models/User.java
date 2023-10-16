package models;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode

public class User {
    private String name;
    private String email;
    private String password;
}
