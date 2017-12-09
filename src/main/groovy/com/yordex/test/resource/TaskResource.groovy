package com.yordex.test.resource

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.yordex.test.domain.RepeatFrequency
import com.yordex.test.domain.Task
import com.yordex.test.jackson.deserializer.LocalDateDeserializer
import com.yordex.test.jackson.deserializer.RepeatFrequencyDeserializer
import com.yordex.test.jackson.serializer.LocalDateSerializer
import com.yordex.test.jackson.serializer.RepeatFrequencySerializer
import com.yordex.test.util.PreConditions
import io.swagger.annotations.ApiModelProperty

import java.time.LocalDate

/**
 * The JSON representation of a Task. It is a good idea to keep the DTO objects separate from their JSON representation objects
 * Created by Ankush on 06/05/17.
 */
@JsonIgnoreProperties(["metaClass"])
class TaskResource {

    final Integer id
    final String name
    final String description

    @JsonSerialize(using = LocalDateSerializer)
    @JsonDeserialize(using = LocalDateDeserializer)
    final LocalDate dueDate

    @JsonSerialize(using = RepeatFrequencySerializer)
    @JsonDeserialize(using = RepeatFrequencyDeserializer)
    final RepeatFrequency repeatFrequency

    final Integer repeatCount

    TaskResource(){}
    TaskResource(Task task){
        PreConditions.notNull(task , 'task cannot be null')
        this.id = task.id
        this.name = task.name
        this.description = task.description
        this.dueDate = task.dueDate
        this.repeatFrequency = task.repeatFrequency
        this.repeatCount = task.repeatCount
    }
}
