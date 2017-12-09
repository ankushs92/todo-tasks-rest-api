package com.yordex.test.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.yordex.test.domain.RepeatFrequency
import com.yordex.test.util.Enums
import com.yordex.test.util.Strings

/**
 * Created by Ankush on 07/05/17.
 */
class RepeatFrequencyDeserializer extends JsonDeserializer<RepeatFrequency> {

    @Override
    RepeatFrequency deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        def text = p.text
        if(Strings.hasText(text)){
            return Enums.getEnumFromString(RepeatFrequency, text)
        }
        else{
            //If empty string is passed, then RepeatFrequency = NONE
            return RepeatFrequency.NONE
        }
    }
}
