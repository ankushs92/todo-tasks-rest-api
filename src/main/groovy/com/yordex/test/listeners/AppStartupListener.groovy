package com.yordex.test.listeners

import com.yordex.test.domain.RepeatFrequency
import com.yordex.test.domain.Task
import com.yordex.test.domain.auth.Role
import com.yordex.test.domain.auth.User
import com.yordex.test.repository.TaskRepository
import com.yordex.test.repository.UserRepository
import com.yordex.test.service.TaskService
import com.yordex.test.util.TestConstants
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

import java.time.LocalDate

/**
 * This class inserts objects to H2 for integration testing.
 * Created by Ankush on 21/05/17.
 */
@Profile(["dev"])
@Component
@Slf4j
class AppStartupListener  implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    UserRepository userRepository

    @Autowired
    TaskRepository taskRepository

    @Override
    void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Inserting User objects to H2 for Integration testing")
        taskRepository.deleteAll()
        userRepository.deleteAll()

        def validUser = new User(
                username : TestConstants.VALID_USERNAME,
                password : new BCryptPasswordEncoder().encode(TestConstants.USER_PASSWORD),
                isEnabled : true,
                isCredentialsNonExpired : true,
                isAccountNonExpired : true,
                isAccountNonLocked : true,
                role : Role.USER,
                tasks : []
        )


        def userWithAccLocked = new User(
                username : TestConstants.USER_WITH_ACCOUNT_LOCKED,
                password : new BCryptPasswordEncoder().encode(TestConstants.USER_PASSWORD),
                isEnabled : true,
                isCredentialsNonExpired : true,
                isAccountNonExpired : true,
                isAccountNonLocked : false,
                role : Role.USER,
                tasks : []
        )


        def userWithAccountDisabled = new User(
                username : TestConstants.USER_WITH_ACCOUNT_DISABLED,
                password : new BCryptPasswordEncoder().encode(TestConstants.USER_PASSWORD),
                isEnabled : false,
                isCredentialsNonExpired : true,
                isAccountNonExpired : true,
                isAccountNonLocked : true,
                role : Role.USER,
                tasks : []
        )


        def userWithCredentialsExpired = new User(
                username : TestConstants.USER_WITH_CREDENTIALS_EXPIRED,
                password : new BCryptPasswordEncoder().encode(TestConstants.USER_PASSWORD),
                isEnabled : true,
                isCredentialsNonExpired : false,
                isAccountNonExpired : true,
                isAccountNonLocked : true,
                role : Role.USER,
                tasks : []
        )

        def userWithAccountExpired = new User(
                username : TestConstants.USER_WITH_ACCOUNT_EXPIRED,
                password : new BCryptPasswordEncoder().encode(TestConstants.USER_PASSWORD),
                isEnabled : true,
                isCredentialsNonExpired : false,
                isAccountNonExpired : true,
                isAccountNonLocked : true,
                role : Role.USER,
                tasks : []
        )

        userRepository.save([validUser,userWithAccLocked,userWithAccountDisabled,userWithCredentialsExpired,userWithAccountExpired])
        log.info("All User objects successfully inserted to  H2")

        log.info("Inserting Task objects to H2")
        def task1 = new Task(
                id: 1 ,
                name : TestConstants.TASK1_NAME,
                description : TestConstants.TASK1_DESC,
                dueDate : TestConstants.TASK1_DUE_DATE,
                repeatCount : TestConstants.TASK1_REPEAT_COUNT,
                repeatFrequency : TestConstants.TASK1_REPEAT_FREQ,
                user : validUser
        )

        def task2 = new Task(
                name : TestConstants.TASK2_NAME,
                description : TestConstants.TASK2_DESC,
                dueDate : TestConstants.TASK2_DUE_DATE,
                repeatCount : TestConstants.TASK2_REPEAT_COUNT,
                repeatFrequency : TestConstants.TASK2_REPEAT_FREQ,
                user : validUser
        )


        def task3 = new Task(
                name : TestConstants.TASK3_NAME,
                description : TestConstants.TASK3_DESC,
                dueDate : TestConstants.TASK3_DUE_DATE,
                repeatCount : TestConstants.TASK3_REPEAT_COUNT,
                repeatFrequency : TestConstants.TASK3_REPEAT_FREQ,
                user : validUser
        )


        taskRepository.save([task1, task2, task3])
        log.info("Successfully inserted Task objects to H2")

    }
}
