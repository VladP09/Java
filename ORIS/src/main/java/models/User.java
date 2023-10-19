package models;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    private String name;
    private String email;
    private String password;
}
