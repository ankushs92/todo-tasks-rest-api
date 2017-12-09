package com.yordex.test.jpa

import javax.persistence.AttributeConverter
import javax.persistence.Converter
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.ZonedDateTime

/**
 * Created by Ankush on 06/05/17.
 */
@Converter(autoApply = true)
class LocalDateJpaConverter implements AttributeConverter<LocalDate,Date> {

    @Override
    Date convertToDatabaseColumn(LocalDate attribute) {
        if(attribute){
            Date.valueOf(attribute)
        }
    }

    @Override
    LocalDate convertToEntityAttribute(Date dbData) {
        dbData.toLocalDate()
    }
}
