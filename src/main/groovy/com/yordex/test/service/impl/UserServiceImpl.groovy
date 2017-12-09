package com.yordex.test.service.impl

import com.yordex.test.repository.UserRepository
import com.yordex.test.util.Strings
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by Ankush on 06/05/17.
 */
@Service
@Transactional
class UserServiceImpl implements UserDetailsService {

    final UserRepository userRepository

    UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository
    }

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        def user = userRepository.findOneByUsername(username)
        if(!user){
            throw new UsernameNotFoundException("User with username $username not found")
        }
        return user
    }
}
