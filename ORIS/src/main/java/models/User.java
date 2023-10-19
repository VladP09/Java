package models;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    private String uuid;
    private Integer id;
    private String name;
    private String email;
    private String password;
}
