package com.pratice.car.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pratice.car.constant.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@Entity
@SequenceGenerator(name="member_id", sequenceName = "member_id", initialValue = 1,allocationSize = 1)
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id")
    @Column(name="member_id")
    private Long id;

    @Column private String name;
    @Column(unique = true) private String email;
    @Column private String password;
    @Column private String address;

    @Column private Long dealerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for(String role : role.toString().split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
