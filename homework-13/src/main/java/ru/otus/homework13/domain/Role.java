package ru.otus.homework13.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("roles")
public class Role implements GrantedAuthority {
    @Id
    private String id;
    @NotBlank
    private String authority;

    public Role(String authority) {
        this.authority = authority;
    }
}
