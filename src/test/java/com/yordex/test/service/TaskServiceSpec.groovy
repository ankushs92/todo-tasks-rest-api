package com.yordex.test.service

import com.yordex.test.BaseSpec
import com.yordex.test.domain.RepeatFrequency
import com.yordex.test.domain.Task
import com.yordex.test.repository.TaskRepository
import com.yordex.test.request.TaskRequest
import com.yordex.test.resource.TaskResource
import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Repeat

import java.time.LocalDate

/**
 * Created by Ankush on 07/05/17.
 */
class TaskServiceSpec extends BaseSpec {

    @Autowired
    TaskService taskService

    @Autowired
    TaskRepository taskRepository


    def dbTask
    def setup(){
        taskRepository.deleteAll()
        dbTask = new Task(
                name : 'Some task name',
                description : 'Some task description',
                dueDate : LocalDate.now(),
                repeatFrequency : RepeatFrequency.DAILY,
                repeatCount : 1,
                user : validUser
        )
        taskRepository.save(dbTask)
    }

    def "Insert a Task for a User"(){
        given : 'A Task request and a User'
           def name = 'Some name'
           def description = 'Some description'
           def dueDate = LocalDate.now().plusDays(1)
           def repeatFreq = RepeatFrequency.NONE
           def repeatCount = 0
           def taskReq = new TaskRequest(description, name, dueDate, repeatFreq, repeatCount)

        when :
           def task = taskService.create(validUser, taskReq)

        then:
            with(task){
                task.id
                description == description
                name == name
                dueDate == dueDate
                repeatFrequency == repeatFreq
                user == validUser
                repeatCount == repeatCount
            }
    }


    def "Try to insert a Task with a null user . IllegalArgumentException would be thrown"(){
        given : 'a null user and some task attributes'
            def name = 'Some name'
            def description = 'Some description'
            def dueDate = LocalDate.now().plusDays(1)
            def repeatFreq = RepeatFrequency.NONE
            def repeatCount = 0

            def taskReq = new TaskRequest(description, name, dueDate,repeatFreq,repeatCount)
            def user

        when :
            taskService.create(user, taskReq)

        then :
            thrown(IllegalArgumentException)

    }


    def "Try to insert a Task with null TaskRequest  . IllegalArgumentException would be thrown"(){
        when :
            taskService.create(validUser, null)

        then :
            thrown(IllegalArgumentException)
    }

    def "Update an already existing Task in database for a user, given a TaskRequest"(){
        given : 'A Task request and a User'
            def updatedName = 'Updated name'
            def updatedDescription = 'Updated description'
            def updatedDueDate = LocalDate.now().plusDays(2)
            def updatedRepeatFrequency = RepeatFrequency.MONTHLY
            def updatedRepeatCount = 11
            def taskReq = new TaskRequest(updatedDescription, updatedName, updatedDueDate, updatedRepeatFrequency, updatedRepeatCount)

        when :
            def updatedTask = taskService.update(validUser, dbTask.id, taskReq)


        then:
            with(updatedTask){
                description == updatedDescription
                name == updatedName
                dueDate == updatedDueDate
                repeatFrequency == updatedRepeatFrequency
                repeatCount == updatedRepeatCount
            }
    }

    def "No task id passed for updating a task. IllegalArgumentException would be thrown"(){
        given : 'A Task request and a User'
            def updatedName = 'Updated name'
            def updatedDescription = 'Updated description'
            def updatedDueDate = LocalDate.now().plusDays(2)
            def updatedRepeatFrequency = RepeatFrequency.MONTHLY
            def updatedRepeatCount = 11
            def taskReq = new TaskRequest(updatedDescription, updatedName, updatedDueDate, updatedRepeatFrequency, updatedRepeatCount)

        when :
            taskService.update(validUser, null, taskReq)

        then:
            thrown(IllegalArgumentException)
    }

    def "No TaskRequest passed for updating a task. IllegalArgumentException would be thrown"(){
        when :
            taskService.update(validUser, dbTask.id, null)

        then:
            thrown(IllegalArgumentException)
    }

    def "No User passed for updating a task. IllegalArgumentException would be thrown"(){
        given : 'A Task request and a User'
            def updatedName = 'Updated name'
            def updatedDescription = 'Updated description'
            def updatedDueDate = LocalDate.now().plusDays(2)
            def updatedRepeatFrequency = RepeatFrequency.MONTHLY
            def updatedRepeatCount = 11
            def taskReq = new TaskRequest(updatedDescription, updatedName, updatedDueDate, updatedRepeatFrequency, updatedRepeatCount)

        when :
            taskService.update(null, dbTask.id, taskReq)

        then:
            thrown(IllegalArgumentException)
    }






    def "Given a Task with some id and a Valid user, find the task with that id for that user " (){
        given : 'A task request and a valid user'
            def name = 'Some name'
            def description = 'Some description'
            def dueDate = LocalDate.now().plusDays(1)
            def repeatFreq = RepeatFrequency.NONE
            def repeatCount = 0
            def taskReq = new TaskRequest(description, name, dueDate,repeatFreq,repeatCount)

        when : 'Save it to db. Then retreive it by its id'
            def task = taskService.create(validUser , taskReq)
            def taskId = task.id
            def result = taskService.findOneForUser(validUser, taskId)

        then :
            with(result){
                description == description
                name == name
                dueDate == dueDate
                repeatFrequency == repeatFreq
                repeatCount == repeatCount
            }
    }

    def "Given a Task with some id and a null user, try to find the task with that id for that user. IllegalArgumentException would be thrown" (){
        when : 'no user passed'
            taskService.findOneForUser(null, dbTask.id)

        then :
            thrown(IllegalArgumentException)
    }

    def "A  null taskId is passed along with a valid user. Since no taskId is passed, IllegalArgumentException would be thrown" (){
        when : 'Save it to db. Then retreive it by its id'
            taskService.findOneForUser(validUser, null)

        then :
            thrown(IllegalArgumentException)
    }



    def "Given a list of Tasks for a specific user in the database, return a List of first 3 elements for this user"(){
        given : 'A valid user and a Page request with page = 0 and size = 3.'
            def pageRequest = new PageRequest(0,3)
            def today = LocalDate.now()
            def task1 = new TaskRequest('Description1','Name1', today.plusDays(1), RepeatFrequency.NONE,0)
            def task2 = new TaskRequest('Description2','Name2', today.plusDays(2), RepeatFrequency.DAILY,1)
            def task3 = new TaskRequest('Description3','Name3', today.plusDays(3), RepeatFrequency.WEEKLY,2)
            def task4 = new TaskRequest('Description4','Name4', today.plusDays(4), RepeatFrequency.MONTHLY,3)
            def task5 = new TaskRequest('Description5','Name5', today.plusDays(5), RepeatFrequency.YEARLY,4)

            def taskReqList = [ task1, task2, task3, task4, task5 ]

        when : "Save the items one by one ,in order. Then fetch it"
            taskReqList.each { task -> taskService.create(validUser, task) }
            def result = taskService.findAllForUser(validUser, pageRequest)
            def taskList = result.tasks

        then : "Although we have 6 items in the database, 5 added in this method and 1 added in setup, since we have requested only for 3 items for the first page, we will get only 3 items indeed!"
            taskList.size() == 3
            with(taskList[0]){
                name == dbTask.name
                description == dbTask.description
                dueDate == dbTask.dueDate
                repeatFrequency == dbTask.repeatFrequency
                repeatCount == dbTask.repeatCount
            }
            with(taskList[1]){
                name == 'Name1'
                description == 'Description1'
                dueDate == today.plusDays(1)
                repeatFrequency == RepeatFrequency.NONE
                repeatCount == 0
            }
            with(taskList[2]){
                name == 'Name2'
                description == 'Description2'
                dueDate == today.plusDays(2)
                repeatFrequency == RepeatFrequency.DAILY
                repeatCount == 1
            }
    }

    def cleanup(){
        taskRepository.deleteAll()
    }
}
