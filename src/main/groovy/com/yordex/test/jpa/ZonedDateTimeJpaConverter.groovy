package com.yordex.test.jpa

import groovy.util.logging.Slf4j

import javax.persistence.AttributeConverter
import javax.persistence.Converter
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * JPA Converter to convert a ZonedDateTime to MySQL's Timestamp and vice-versa
 * Created by Ankush on 05/05/17.
 */
@Converter(autoApply = true)
@Slf4j
class ZonedDateTimeJpaConverter implements AttributeConverter<ZonedDateTime,Timestamp> {

    @Override
    Timestamp convertToDatabaseColumn(ZonedDateTime attribute) {
        if(attribute){
             Timestamp.valueOf(attribute.toLocalDateTime())
        }
    }

    @Override
    ZonedDateTime convertToEntityAttribute(Timestamp dbTimestamp) {
        if(dbTimestamp){
            def zonedDateTime = ZonedDateTime.of(dbTimestamp.toLocalDateTime(), ZoneId.of('UTC'))
            zonedDateTime
        }
    }
}
