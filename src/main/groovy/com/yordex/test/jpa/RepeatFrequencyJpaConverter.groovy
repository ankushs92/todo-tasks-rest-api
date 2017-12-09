package com.yordex.test.jpa

import com.yordex.test.domain.RepeatFrequency
import com.yordex.test.util.PreConditions
import groovy.util.logging.Slf4j

import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * Created by Ankush on 07/05/17.
 */
@Converter(autoApply = true)
class RepeatFrequencyJpaConverter implements AttributeConverter<RepeatFrequency,Integer>{

    @Override
    Integer convertToDatabaseColumn(RepeatFrequency attribute) {
        // RepeatFrequency guaranteed to be non null
        attribute.code
    }

    @Override
    RepeatFrequency convertToEntityAttribute(Integer dbData) {
        PreConditions.notNull(dbData, 'int value to convert to its RepeatFrequency enum representation cannot be null')
        RepeatFrequency.from(dbData)
    }
}
