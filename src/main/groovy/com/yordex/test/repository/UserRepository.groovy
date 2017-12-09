package com.yordex.test.repository

import com.yordex.test.domain.auth.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Ankush on 06/05/17.
 */
interface UserRepository extends JpaRepository<User,Integer> {

    User findOneByUsername(String username)
}
