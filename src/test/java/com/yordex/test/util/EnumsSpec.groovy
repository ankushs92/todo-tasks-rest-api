package com.yordex.test.util

import com.yordex.test.BaseSpec

/**
 * Created by Ankush on 07/05/17.
 */
class EnumsSpec extends BaseSpec{

    private static enum Test{
        A,
        B,
    }

    def
    """Enums.getEnumFromString should take in a String value and class, and map it to the appropriate enum value
     Also, the case of the string shouldn't matter
    """  ()
    {

        when:
        def result = Enums.getEnumFromString(Test,string)

        then:
        result == expectedResult

        where:
        string | expectedResult
        "a" | Test.A
        "B" | Test.B
    }

    def
    """If a String is passed that has no enum representation, then exception would be thrown
    """  ()
    {
        when:
            Enums.getEnumFromString(Test,"ccc")

        then:
            thrown(IllegalArgumentException)
    }

}