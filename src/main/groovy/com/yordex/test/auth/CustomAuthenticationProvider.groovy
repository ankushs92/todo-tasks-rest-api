package com.yordex.test.auth

import com.yordex.test.domain.auth.User
import com.yordex.test.util.Strings
import groovy.util.logging.Slf4j
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component


/**
 * Created by Ankush on 06/05/17.
 */
@Component
@Slf4j
class CustomAuthenticationProvider  implements AuthenticationProvider{

    final UserDetailsService userDetailsService

    CustomAuthenticationProvider(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService
    }

    @Override
    Authentication authenticate(Authentication auth) throws AuthenticationException {
        def username = auth.principal as String
        def password = auth.credentials as String

        log.debug('Username received {} ' , username)

        if(Strings.hasText(username) && Strings.hasText(password)){
            def user = userDetailsService.loadUserByUsername(username) as User
            def bcryptPasswordEncoder = new BCryptPasswordEncoder()

            def dbPassword = user.password
            def isAccountEnabled = user.isEnabled
            def isAccountNonLocked = user.isAccountNonLocked
            def isAccountNonExpired = user.isAccountNonExpired
            def isCredentialsNonExpired = user.isCredentialsNonExpired

            if(!bcryptPasswordEncoder.matches(password, dbPassword)){
                throw new BadCredentialsException('Invalid username or password')
            }

            if(!isAccountEnabled){
                throw new DisabledException('Account has been disabled')
            }

            if(!isAccountNonLocked){
                throw new LockedException('Account has been Locked')
            }

            if(!isAccountNonExpired){
                throw new AccountExpiredException('Account has expired')
            }

            if(!isCredentialsNonExpired){
                throw new CredentialsExpiredException('Credentials for this account have expired')
            }

            //Passing username for credentials value.
            def token = new UsernamePasswordAuthenticationToken(user, username, user.getAuthorities())
            return token
        }
        else{
            throw new BadCredentialsException('Please provide a valid username and password')
        }
    }

    @Override
    boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class == authentication
    }
}
