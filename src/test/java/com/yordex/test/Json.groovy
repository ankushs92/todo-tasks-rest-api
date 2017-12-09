package com.yordex.test

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.yordex.test.util.PreConditions

/**
 * Created by Ankush on 11/04/17.
 */
class Json {

     static <T> T toObject( String json ,  Class<T> clazz) throws Exception{
        PreConditions.notNull(json,"json cannot be null");
        PreConditions.notNull(clazz,"clazz cannot be null");

        ObjectMapper objMapper = new ObjectMapper()
        return objMapper.readValue(json,clazz)
    }

     static String toJson( Object object) throws JsonProcessingException {
        PreConditions.notNull(object,"object cannot be null")

        ObjectMapper objMapper = new ObjectMapper()
        return objMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(object)
    }

}
