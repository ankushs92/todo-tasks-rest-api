package com.yordex.test.domain

import com.yordex.test.BaseSpec
import org.springframework.test.annotation.Repeat

/**
 * Created by Ankush on 07/05/17.
 */
class RepeatFrequencySpec extends BaseSpec {

    def "match the code for different RepeatFrequency enum representations"(){
        when :
            def result = RepeatFrequency.from(code)

        then:
            result == expectedResult

        where :
            code | expectedResult
            0    | RepeatFrequency.NONE
            1    | RepeatFrequency.DAILY
            2    | RepeatFrequency.WEEKLY
            3    | RepeatFrequency.MONTHLY
            4    | RepeatFrequency.YEARLY
    }

}
