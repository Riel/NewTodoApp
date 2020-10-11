package com.task.todo.security;

import com.task.todo.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TodoUserDetails implements UserDetails {

  //region Fields
  private User user;
  //endregion

  //region Constructors
  public TodoUserDetails(User user) {
    this.user = user;
  }
  //endregion

  //region Getters & Setters
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();

    // Extract list of roles (ROLE_name)
    this.user.getRoleList().forEach(r -> {
      GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
      authorities.add(authority);
    });

    return authorities;
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    //return this.user.isActive() && user.isVerified();
    return true;
  }

  public Long getUserId() {
    return user.getId();
  }
  //endregion
}
