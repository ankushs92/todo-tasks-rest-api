package com.yordex.test.domain.auth

import com.yordex.test.domain.AbstractEntity
import com.yordex.test.domain.Task
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.PrePersist
import javax.persistence.Table
import java.time.ZoneId
import java.time.ZonedDateTime
import org.hibernate.annotations.Type

@Entity
@ToString(includePackage = false, excludes = ["password"], includeSuper = true)
@Table(name='users')
class User  extends AbstractEntity implements  UserDetails{

    @Column(name='username', nullable = false, updatable = false, length=30)
    String username

    @Column(name='created_on', nullable = false, updatable = false)
    ZonedDateTime creationDateTime

    //Bcrypt
    @Column(name='password', nullable = false, length = 100) // Length kept 100 because Bcrypt passwords are long
    String password

    @Type(type="yes_no")
    @Column(name='is_enabled', nullable = false)
    Boolean isEnabled

    @Type(type="yes_no")
    @Column(name='credentials_non_expired', nullable = false)
    Boolean isCredentialsNonExpired

    @Type(type="yes_no")
    @Column(name='account_non_expired', nullable = false)
    Boolean isAccountNonExpired

    @Type(type="yes_no")
    @Column(name='account_non_locked', nullable = false)
    Boolean isAccountNonLocked

    @Column(name='role', nullable = false)
    @Enumerated(EnumType.STRING)
    Role role

    @OneToMany(mappedBy='user', fetch = FetchType.LAZY)
    List<Task> tasks

    @PrePersist
    void beforeCreating(){
        this.creationDateTime = ZonedDateTime.now(ZoneId.of('UTC'))
    }


    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        AuthorityUtils.createAuthorityList(role.toString())
    }


    @Override
    String getUsername() {
        return username
    }

    @Override
    boolean isAccountNonExpired() {
        return isAccountNonLocked
    }

    @Override
    boolean isAccountNonLocked() {
        return isAccountNonExpired
    }

    @Override
    boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired
    }

    @Override
    boolean isEnabled() {
        return isEnabled
    }


}
