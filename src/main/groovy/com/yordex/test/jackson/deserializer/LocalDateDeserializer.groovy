package com.yordex.test.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.yordex.test.util.Strings

import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

/**
 * Created by Ankush on 06/05/17.
 */
class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        def text = p.text
        if(Strings.hasText(text)){
            return LocalDate.parse(text, new DateTimeFormatterBuilder()
                                            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                            .appendOptional(DateTimeFormatter.ofPattern("yyyy-M-dd"))
                                            .appendOptional(DateTimeFormatter.ofPattern("dd-M-d"))
                                            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-d"))
                                            .toFormatter()
                                )
        }
        return null
    }
}
