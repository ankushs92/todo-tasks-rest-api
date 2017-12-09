package com.yordex.test.util

import com.yordex.test.BaseSpec
import com.yordex.test.service.TaskService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Ankush on 07/05/17.
 */
class StringsSpec extends BaseSpec{

    def "Test null, whitespace and non whitespace String to check whether they have text"(){
        when :
            def result = Strings.hasText(str)
        then:
            result == expectedResult
        where:
            str      | expectedResult
            null     | false
            ''       | false
            '      ' | false
            'a '     | true
    }
}
