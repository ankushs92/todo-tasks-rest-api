package com.yordex.test.util

/**
 * Created by Ankush on 05/05/17.
 */
class Strings {

    static final String EMPTY = ""

    static boolean hasText(String str){
        if(!str){
            return false
        }
        int length = str.length()
        for(int i = 0 ; i < length ; i++){
            if(!Character.isWhitespace(str.charAt(i))){
                return true
            }
        }
        return false
    }
}
