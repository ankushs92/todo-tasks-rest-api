package com.yordex.test

import com.yordex.test.domain.auth.Role
import com.yordex.test.domain.auth.User
import com.yordex.test.repository.TaskRepository
import com.yordex.test.repository.UserRepository
import groovy.util.logging.Slf4j
import org.hibernate.annotations.Type
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.annotation.Commit
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Ankush on 07/05/17.
 */
@SpringBootTest(classes = YordexTestApplication, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@ActiveProfiles("dev")
@Slf4j
class BaseSpec extends Specification {

    @Autowired
    UserRepository userRepository

    @Autowired
    TaskRepository taskRepository

    User validUser

    def setup(){
        taskRepository.deleteAll()
        userRepository.deleteAll()

        validUser = new User(
                username : 'ankush',
                password : new BCryptPasswordEncoder().encode('ankush123'),
                isEnabled : true,
                isCredentialsNonExpired : true,
                isAccountNonExpired : true,
                isAccountNonLocked : true,
                role : Role.USER,
                tasks : []
        )
        validUser = userRepository.save(validUser)
        log.info('User with credentials saved to H2: {}', validUser )
    }


    def cleanup(){
        taskRepository.deleteAll()
        userRepository.deleteAll()
    }

}
