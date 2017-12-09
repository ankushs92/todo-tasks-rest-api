package com.yordex.test.util

import com.yordex.test.BaseSpec

/**
 * Created by Ankush on 07/05/17.
 */
class PreConditionsSpec extends BaseSpec {

    def "For any null object passed, an IllegalArgumentException with an error msg would be thrown. Otheriwse, all OK"(){
        when:
            PreConditions.notNull(null, 'null passed, now throw IllegalArgumentException')

        then:
            thrown(IllegalArgumentException)
    }
}
