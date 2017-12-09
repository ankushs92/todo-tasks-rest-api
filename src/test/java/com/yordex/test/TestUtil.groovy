package com.yordex.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.yordex.test.domain.auth.Role
import com.yordex.test.domain.auth.User
import com.yordex.test.util.PreConditions
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Created by Ankush on 12/05/17.
 */
class TestUtil {

    static User createUser(isEnabled, isNonLocked, isNonExpired, isCredsNonExpired){
        new User(
                username : "ankushOther",
                password : new BCryptPasswordEncoder().encode('ankush123'),
                isEnabled : isEnabled,
                isAccountNonLocked : isNonLocked,
                isAccountNonExpired : isNonExpired,
                isCredentialsNonExpired :  isCredsNonExpired,
                role : Role.USER
        )
    }

}
