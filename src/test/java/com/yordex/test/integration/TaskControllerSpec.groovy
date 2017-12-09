package com.yordex.test.integration

import com.yordex.test.Json
import com.yordex.test.TestUtil
import com.yordex.test.domain.RepeatFrequency
import com.yordex.test.request.TaskRequest
import com.yordex.test.resource.TaskListResource
import com.yordex.test.resource.TaskResource
import com.yordex.test.util.TestConstants
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import spock.lang.Unroll

import java.time.LocalDate

/**
 * Created by Ankush on 21/05/17.
 */
class TaskControllerSpec extends IntegrationTestSpec {


    def "Query endpoint /api/#version/tasks a paginated list of Tasks with page=0 and size = 2. Should return Http Status 200 OK with a list of 2 task items" (){

        when :
            def page = 0
            def size = 2
            def response = testRestTemplate.withBasicAuth(TestConstants.VALID_USERNAME, TestConstants.USER_PASSWORD)
                            .getForEntity("$BASE_LOCALHOST_URL:$port/api/$version/tasks?page=$page&size=$size", String)
            List<TaskResource> taskList = Json.toObject(response.body, TaskListResource).tasks

        then :
            response.statusCode == HttpStatus.OK
            taskList.size() == 2
            with(taskList[0]){
                name == TestConstants.TASK1_NAME
                description == TestConstants.TASK1_DESC
                dueDate  == TestConstants.TASK1_DUE_DATE
                repeatCount == TestConstants.TASK1_REPEAT_COUNT
                repeatFrequency == TestConstants.TASK1_REPEAT_FREQ
            }
            with(taskList[1]){
                name == TestConstants.TASK2_NAME
                description == TestConstants.TASK2_DESC
                dueDate == TestConstants.TASK2_DUE_DATE
                repeatCount == TestConstants.TASK2_REPEAT_COUNT
                repeatFrequency == TestConstants.TASK2_REPEAT_FREQ
            }
    }

    def "Query endpoint /api/#version/tasks/{taskId} to get a Task item. Should return Http Status 200 OK" (){

        when :
            def response = testRestTemplate.withBasicAuth(TestConstants.VALID_USERNAME, TestConstants.USER_PASSWORD)
                    .getForEntity("$BASE_LOCALHOST_URL:$port/api/$version/tasks/$TestConstants.TASK1_ID", String)
            def task = Json.toObject(response.body, TaskResource)

        then :
           response.statusCode == HttpStatus.OK
           with(task){
               id == TestConstants.TASK1_ID
               name == TestConstants.TASK1_NAME
               description == TestConstants.TASK1_DESC
               dueDate  == TestConstants.TASK1_DUE_DATE
               repeatCount == TestConstants.TASK1_REPEAT_COUNT
               repeatFrequency == TestConstants.TASK1_REPEAT_FREQ
           }
    }

    def "Query endpoint /api/#version/tasks/{taskId} to get a Task item for a task that doesn't exist. Should return Http Status 404"(){

        when :
            def response = testRestTemplate.withBasicAuth(TestConstants.VALID_USERNAME, TestConstants.USER_PASSWORD)
                    .getForEntity("$BASE_LOCALHOST_URL:$port/api/$version/tasks/121212", String)

        then :
            response.statusCode == HttpStatus.NOT_FOUND
    }

}
