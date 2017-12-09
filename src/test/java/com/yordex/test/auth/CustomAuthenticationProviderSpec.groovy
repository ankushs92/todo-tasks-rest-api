package com.yordex.test.auth

import com.yordex.test.BaseSpec
import com.yordex.test.TestUtil
import com.yordex.test.auth.CustomAuthenticationProvider
import com.yordex.test.domain.auth.Role
import com.yordex.test.domain.auth.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Created by Ankush on 07/05/17.
 */
class CustomAuthenticationProviderSpec extends BaseSpec {

    @Autowired
    CustomAuthenticationProvider authProvider


    def "Authenticate a user with valid credentials. Should return a token of type UsernameandPasswordAuthenticationToken"(){

        given : "An valid user whose account is enabled, not locked, username exists and entered the right password"
            def auth = new TestingAuthenticationToken(validUser.username, 'ankush123')

        when :
            def result = authProvider.authenticate(auth)

        then :
            result == new UsernamePasswordAuthenticationToken(validUser, validUser.username, validUser.getAuthorities())

    }

    def "Attempt to authenticate a User who has entered an invalid username. UsernameNotFoundException would be thrown"(){
        given : "Some invalid username"
            def auth = new TestingAuthenticationToken("Some invalid username", 'ankush123')

        when :
            authProvider.authenticate(auth)

        then :
            thrown(UsernameNotFoundException)
    }


    def "Attempt to authenticate a User who has entered an wrong password. UsernameNotFoundException would be thrown"(){
        given : "Some invalid password"
            def auth = new TestingAuthenticationToken(validUser.username, 'Some Wrong Password')

        when :
            authProvider.authenticate(auth)

        then :
            thrown(BadCredentialsException)
    }

    def "Attempt to authenticate a User whose account has been disabled. DisabledException would be thrown"(){
        given : "Save a User with valid credentials but whose account has been disabled"
            def user = TestUtil.createUser(false, true,  true ,true)
            userRepository.save(user)
            def auth = new TestingAuthenticationToken(user.username, 'ankush123')

        when :
            authProvider.authenticate(auth)

        then :
            thrown(DisabledException)
    }

    def "Attempt to authenticate a User whose account has been locked. LockedException would be thrown"(){
        given : "Save a User with valid credentials but whose account has been locked"
            def user = TestUtil.createUser(true, false,  true ,true)
            userRepository.save(user)
            def auth = new TestingAuthenticationToken(user.username, 'ankush123')

        when :
            authProvider.authenticate(auth)

        then :
            thrown(LockedException)
    }


    def "Attempt to authenticate a User whose account has expired. ExpiredException would be thrown"(){
        given : "Save a User with valid credentials but whose account has expired"
            def user = TestUtil.createUser(true, true,  false ,true)
            userRepository.save(user)
            def auth = new TestingAuthenticationToken(user.username, 'ankush123')

        when :
            authProvider.authenticate(auth)

        then :
            thrown(AccountExpiredException)
    }

    def "Attempt to authenticate a User whose credentials have expired. CredentialsExpiredException would be thrown"(){
        given : "Save a User whose credentials have expired"
            def user = TestUtil.createUser(true, true,  true ,false)
            userRepository.save(user)
            def auth = new TestingAuthenticationToken(user.username, 'ankush123')

        when :
            authProvider.authenticate(auth)

        then :
            thrown(CredentialsExpiredException)
    }

    def "Attempt to authenticate a User whose has entered blank username and password. BadCredentialsException would be thrown"(){
        given : "A user who has entered a blank username and password"
            def auth = new TestingAuthenticationToken(' ', ' ')

        when :
            authProvider.authenticate(auth)

        then :
            thrown(BadCredentialsException)
    }

}
