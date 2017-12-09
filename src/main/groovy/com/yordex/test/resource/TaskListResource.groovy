package com.yordex.test.resource

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Immutable
import io.swagger.annotations.ApiModelProperty

/**
 * Created by Ankush on 06/05/17.
 */
class TaskListResource {

    final List<TaskResource> tasks

    TaskListResource(@JsonProperty("tasks") List<TaskResource> tasks){
        this.tasks = tasks
    }

}
