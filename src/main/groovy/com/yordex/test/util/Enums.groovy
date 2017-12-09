package com.yordex.test.util

/**
 * Created by Ankush on 07/05/17.
 */
class Enums{
    /*
    *
    * Consider an enum Status with states ACTIVE,DEACTIVE
    * Then, this method would convert the string to its Enum representation. For example "ACTIVE" or "active" (ignores case) -> Status.ACTIVE
    *
    * */
    static <T extends Enum<T>> T getEnumFromString( Class<T> enumClass, String value) {
        PreConditions.notNull(enumClass, "EnumClass value can't be null.")

        for (enumValue in enumClass.enumConstants) {
            if ( (enumValue as String ).equalsIgnoreCase(value)) {
                return (T) enumValue
            }
        }

        def errorMessage = new StringBuilder()
        boolean bFirstTime = true
        for(enumValue in enumClass.enumConstants) {
             errorMessage.append(bFirstTime ? "" : ", ").append(enumValue)
             bFirstTime = false
        }
        throw new IllegalArgumentException(value + " is invalid value. Supported values are " + errorMessage);
    }

}
