package com.yordex.test.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.yordex.test.BaseSpec
import com.yordex.test.domain.RepeatFrequency
import com.yordex.test.util.Enums
import com.yordex.test.util.Strings
import groovy.json.JsonBuilder

import java.time.LocalDate

/**
 * Created by Ankush on 07/05/17.
 */
class RepeatFrequencyDeserializerSpec extends BaseSpec {

    private ObjectMapper mapper
    def jsonParser
    def deserializationCtx

    def setup(){
        mapper = new ObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        deserializationCtx = mapper.getDeserializationContext()
    }

    def "Test out various RepeatFrequency values. JsonDeserializer should convert string values to their LocalDate representation "(){
        given :
            def json = new JsonBuilder([freq: repeatFreq ]).toPrettyString()
            jsonParser = mapper.getFactory().createParser(json)

        when :
            jsonParser.nextToken()
            jsonParser.nextToken()
            jsonParser.nextToken()

            def result = new RepeatFrequencyDeserializer().deserialize(jsonParser,deserializationCtx)


        then :
            result  == expectedResult

        where:
        repeatFreq          | expectedResult
            'none'          | RepeatFrequency.NONE
            'None'          | RepeatFrequency.NONE
            'daily'         | RepeatFrequency.DAILY
    }
}
