package com.janaldous.offspringy.user.domain;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.janaldous.offspringy.user.data.entity.Role;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserModel {
	
	private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;
    private Collection<Role> roles;
    
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }
}
