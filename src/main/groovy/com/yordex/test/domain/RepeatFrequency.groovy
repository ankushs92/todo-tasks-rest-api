package com.yordex.test.domain

/**
 * Created by Ankush on 07/05/17.
 */
enum RepeatFrequency {
    NONE(0),
    DAILY(1),
    WEEKLY(2),
    MONTHLY(3),
    YEARLY(4);

    final int code

    RepeatFrequency(int code){
        this.code = code
    }

    static RepeatFrequency from(int code){
        def result
        switch(code){
         case 0: result = NONE
             break
         case 1: result = DAILY
             break
         case 2: result = WEEKLY
             break
         case 3: result = MONTHLY
             break
         case 4: result = YEARLY
             break
        }
        result
    }
}
