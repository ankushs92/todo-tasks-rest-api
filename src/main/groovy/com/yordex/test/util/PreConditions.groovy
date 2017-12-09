package com.yordex.test.util

/**
 * Created by Ankush on 05/05/17.
 */
class PreConditions {

    static <T> void  notNull(T t , String errorMsg){
        if(t == null){
            throw new IllegalArgumentException(errorMsg)
        }
    }

}
