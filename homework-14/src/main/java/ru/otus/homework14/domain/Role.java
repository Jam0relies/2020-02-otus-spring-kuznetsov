package ru.otus.homework14.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("roles")
public class Role implements GrantedAuthority {
    @Id
    private String id;
    private String authority;

    public Role(String authority) {
        this.authority = authority;
    }
}
