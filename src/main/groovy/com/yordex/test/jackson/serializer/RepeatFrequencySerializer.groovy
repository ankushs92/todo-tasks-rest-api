package com.yordex.test.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.yordex.test.domain.RepeatFrequency

/**
 * Created by Ankush on 07/05/17.
 */
class RepeatFrequencySerializer extends JsonSerializer<RepeatFrequency> {

    @Override
    void serialize(RepeatFrequency value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString( (value as String).toLowerCase())
    }
}
