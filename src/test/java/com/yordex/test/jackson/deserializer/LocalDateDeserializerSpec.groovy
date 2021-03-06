package com.yordex.test.jackson.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.yordex.test.BaseSpec
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

import java.time.LocalDate

/**
 * As best as one can test this out
 * Created by Ankush on 07/05/17.
 */
class LocalDateDeserializerSpec extends BaseSpec {

    private ObjectMapper mapper
    def jsonParser
    def deserializationCtx

    def setup(){
        mapper = new ObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        deserializationCtx = mapper.getDeserializationContext()
    }

    def "Test out various date values. JsonDeserializer should convert string values to their LocalDate representation "(){
        given :
            def json = new JsonBuilder([dateTime: dateStr ]).toPrettyString()
            jsonParser = mapper.getFactory().createParser(json)

        when :
            jsonParser.nextToken()
            jsonParser.nextToken()
            jsonParser.nextToken()

            def result = new LocalDateDeserializer().deserialize(jsonParser,deserializationCtx)


        then :
            result  == expectedResult

        where:
            dateStr | expectedResult
            '2017-04-04' |  LocalDate.of(2017,4,4)
            ''           | null
    }
}
