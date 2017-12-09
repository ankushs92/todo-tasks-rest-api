package com.yordex.test.controller

import com.yordex.test.domain.auth.User
import com.yordex.test.request.TaskRequest
import com.yordex.test.resource.ErrorMessageResource
import com.yordex.test.resource.TaskListResource
import com.yordex.test.resource.TaskResource
import com.yordex.test.service.ErrorMessageService
import com.yordex.test.service.TaskService
import com.yordex.test.validator.TaskValidator
import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.Authorization
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import java.time.LocalDate
import java.time.ZoneId


/**
 * Created by Ankush on 06/05/17.
 */
@RestController
@RequestMapping("/api/v1.0/")
@Slf4j
@Api(value="tasks", description="Task operations")
class TaskController {

    final TaskService taskService
    final TaskValidator taskValidator
    final ErrorMessageService errorMessageService

    TaskController(
            TaskService taskService,
            TaskValidator taskValidator,
            ErrorMessageService errorMessageService
    )
    {
        this.taskService = taskService
        this.taskValidator = taskValidator
        this.errorMessageService = errorMessageService
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.addValidators(taskValidator)
    }

    /*
    * Get Lists of Tasks for a user.
    * If found, return the Task with HTTP Status OK
    * Otherwise, send an empty list with HTTP Status NOT_FOUND(404)
    * */
    @ApiOperation(value = "View a list of to-do tasks")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Get a List of Tasks. Pagination parameters are 'page' and 'size'."),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 404, message = "No tasks for user"),
        @ApiResponse(code = 500, message = "Internal Server Error")

    ])
    @ApiImplicitParams([
        @ApiImplicitParam(name = "page", defaultValue = "0",  dataType="integer", paramType = "query"),
        @ApiImplicitParam(name = "size", defaultValue = "25", dataType="integer", paramType = "query")
    ])
    @GetMapping(value = 'tasks', produces ='application/json;utf-8')
    ResponseEntity<TaskListResource> getTasksForUser(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @ApiParam(hidden= true )@AuthenticationPrincipal User user
    )
    {
        TaskListResource tasks =  taskService.findAllForUser(user, pageable)
        if(tasks){
            return new ResponseEntity<TaskListResource>(tasks, HttpStatus.OK)
        }
        return new ResponseEntity<TaskListResource>(new TaskListResource([]), HttpStatus.NOT_FOUND)
    }

    /*
    * Gets a Task for a User by taskId.
    * If it exists, we return the Task with HTTP Status OK
    * If the taskId does not exist for this user, we return an empty JSON with HTTP Status NOT_FOUND(404)
    * */
    @ApiOperation(value = "View a task by id")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Get a task by its id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "No tasks for user"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    ])
    @GetMapping(value = 'tasks/{taskId}', produces ='application/json;utf-8')
    ResponseEntity<TaskResource> getTaskForUser(
            @PathVariable Integer taskId,
            @AuthenticationPrincipal User user
    )
    {
        def task = taskService.findOneForUser(user,taskId)
        if(task){
            return new ResponseEntity<TaskResource>(task, HttpStatus.OK)
        }
        return new ResponseEntity<TaskResource>(HttpStatus.NOT_FOUND)
    }


    /*
    * Create a task.
    * */
    @ApiOperation(value = "Post a Task item")
    @ApiResponses(value = [
            @ApiResponse(code = 201, message = "Successfully created a Task"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    ])
    @PostMapping(value = 'tasks', consumes = 'application/json;utf-8')
    ResponseEntity<?> create(
            @RequestBody @Valid TaskRequest taskRequest,
            @AuthenticationPrincipal User user
    )
    {
       log.debug('Attempting to create Task with attributes : {} ', taskRequest)
       taskService.create(user,taskRequest)
       return new ResponseEntity<Void>(HttpStatus.CREATED)
    }

    /*
    * Delete a Task.
    * If this task does not exist for the user, we send an empty HTTP NOT_FOUND response.
    * Otherwise, we just delete the task and send a HTTP Ok response
    * */
    @ApiOperation(value = "Delete a task item")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully deleted a Task"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "No tasks for user"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    ])
    @DeleteMapping(value = 'tasks/{taskId}')
    ResponseEntity<Void> delete(
            @PathVariable Integer taskId,
            @AuthenticationPrincipal User user
    )
    {
        def task = taskService.findOneForUser(user,taskId)
        if(task){
            taskService.delete(user,taskId)
            return new ResponseEntity<Void>(HttpStatus.OK)
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND)
    }

    /*
    * Update a Task by its id.
    * If the task exist in database, we update it and send a empty HTTP Body.
    * Otherwise, we send a Http 404 error.
    * */
    @ApiOperation(value = "Update a task item")
    @ApiResponses(value = [
            @ApiResponse(code = 204, message = "Successfully updated a Task"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "No tasks for user"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    ])
    @PutMapping(value = 'tasks/{taskId}', consumes = 'application/json;charset=utf-8')
    ResponseEntity<?> update(
            @RequestBody @Valid TaskRequest taskRequest,
            @PathVariable Integer taskId,
            @AuthenticationPrincipal User user
        )
    {
        def responseEntity
        def task = taskService.findOneForUser(user, taskId)
        if(!task){
            responseEntity = new ResponseEntity<Void>(HttpStatus.NOT_FOUND)
        }
        else{
            taskService.update(user,taskId,taskRequest)
            responseEntity = new ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }
        return responseEntity
    }
}
