package com.yordex.test.jackson.serializer

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.yordex.test.BaseSpec
import com.yordex.test.domain.RepeatFrequency

import java.time.LocalDate

/**
 * Created by Ankush on 07/05/17.
 */
class RepeatFrequencySerializerSpec extends BaseSpec{

    Writer writer
    def jsonGen
    def objMapper
    def serializerProvider

    def setup(){
        objMapper = new ObjectMapper()
        writer = new StringWriter()
        serializerProvider = objMapper.getSerializerProvider()
    }

    def "Test out various RepeatFrequency values. JsonSerializer should convert RepeatFrequency values to their string repr "(){
        given :
            jsonGen = new JsonFactory().createGenerator(writer)
            def serializer = new RepeatFrequencySerializer()

        when :
            serializer.serialize(RepeatFrequency.DAILY, jsonGen, serializerProvider)
            jsonGen.flush()

        then : 'the returned value will always be in lower case'
            writer.toString().replace('"','') == 'daily'
    }

    def "For a null, the corresponding String representation is an empty string "(){
        given :
            jsonGen = new JsonFactory().createGenerator(writer)
            def serializer = new LocalDateSerializer()

        when :
            serializer.serialize(null, jsonGen, serializerProvider)
            jsonGen.flush()

        then :
            writer.toString().replace('"','') == ''
    }

}
