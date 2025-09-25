package com.techie.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Document // ✅ No invalid collation
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Role role = Role.USER;

    public enum Role {
        USER, ADMIN
    }

    public Collection<? extends GrantedAuthority> getAuthorities(User existingUser) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+existingUser.getRole().name()));
    }
}
