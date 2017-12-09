package com.yordex.test.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.yordex.test.domain.RepeatFrequency
import com.yordex.test.jackson.deserializer.LocalDateDeserializer
import com.yordex.test.jackson.deserializer.RepeatFrequencyDeserializer
import com.yordex.test.jackson.serializer.LocalDateSerializer
import com.yordex.test.jackson.serializer.RepeatFrequencySerializer
import groovy.transform.ToString
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull
import java.time.LocalDate
import java.time.ZonedDateTime

/**
 * Created by Ankush on 06/05/17.
 */
@JsonIgnoreProperties(value = "metaClass",ignoreUnknown = true)
@ToString(includePackage=false)
class TaskRequest {

    final String description
    final String name
    final LocalDate dueDate
    final RepeatFrequency repeatFrequency
    final Integer repeatCount

    TaskRequest(
            @JsonProperty(value = 'description',required = false) String description,
            @JsonProperty(value = 'name', required = false) String name,
            @JsonProperty(value = 'dueDate', required = false) @JsonSerialize(using = LocalDateSerializer)  @JsonDeserialize(using=LocalDateDeserializer) LocalDate dueDate,
            @JsonProperty(value = 'repeatFrequency', required = false, defaultValue = 'none') @JsonSerialize(using = RepeatFrequencySerializer) @JsonDeserialize(using= RepeatFrequencyDeserializer) RepeatFrequency repeatFrequency,
            @JsonProperty(value = 'repeatCount', required = false ) Integer repeatCount
    )
    {
        this.description = description
        this.name = name
        this.dueDate = dueDate
        this.repeatFrequency = repeatFrequency
        this.repeatCount = repeatCount
    }
}
