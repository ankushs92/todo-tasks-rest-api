package com.yordex.test.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.yordex.test.util.Strings

import java.time.LocalDate

/**
 * Created by Ankush on 06/05/17.
 */
class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        if(value){
            def year = value.year
            //FORMAT : yyyy-MM-dd
            //For month and days with value less than 9, we append a '0' to it. For example, month of August becomes '08'
            def month = value.monthValue <= 9 ? "0" + value.monthValue : value.monthValue
            def day = value.dayOfMonth <= 9 ? "0" + value.dayOfMonth : value.dayOfMonth
            gen.writeString("$year-$month-$day")
        }
        else{
            gen.writeString(Strings.EMPTY)
        }
    }
}
