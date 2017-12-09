package com.yordex.test.service.impl

import com.yordex.test.domain.Task
import com.yordex.test.domain.auth.User
import com.yordex.test.repository.TaskRepository
import com.yordex.test.request.TaskRequest
import com.yordex.test.resource.TaskListResource
import com.yordex.test.resource.TaskResource
import com.yordex.test.service.TaskService
import com.yordex.test.util.PreConditions
import groovy.util.logging.Slf4j
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by Ankush on 06/05/17.
 */
@Service
@Slf4j
@Transactional
class TaskServiceImpl implements TaskService {

    final TaskRepository taskRepository

    TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository
    }

    @Override
    Task create(User user, TaskRequest taskRequest) {
        PreConditions.notNull(user, 'user cannot be null')
        PreConditions.notNull(taskRequest , 'taskRequest cannot be null')

        def task = new Task(
                name : taskRequest.name,
                description : taskRequest.description,
                dueDate : taskRequest.dueDate,
                repeatFrequency:  taskRequest.repeatFrequency,
                repeatCount : taskRequest.repeatCount,
                user : user
        )
        return taskRepository.save(task)
    }


    @Override
    TaskListResource findAllForUser(User user, Pageable pageable) {
        PreConditions.notNull(user, 'user cannot be null')
        PreConditions.notNull(pageable , 'pageable cannot be null')

        def tasks = taskRepository.findAllTaskForUser(user.id, pageable)
        List taskResourceList = []
        if(tasks){
            taskResourceList = tasks.collect{ task -> new TaskResource(task) }
        }
        return new TaskListResource( taskResourceList )
    }


    @Override
    TaskResource findOneForUser(User user, Integer taskId) {
        PreConditions.notNull(taskId, 'taskId cannot be null')
        PreConditions.notNull(user, 'user cannot be null')

        def task = taskRepository.findOneForUser(taskId, user.id)
        def taskResource
        if(task){
            taskResource = new TaskResource(task)
        }
        return taskResource
    }

    @Override
    void delete(User user, Integer taskId) {
        PreConditions.notNull(taskId, 'taskId cannot be null')
        PreConditions.notNull(user, 'user cannot be null')

        taskRepository.deleteTaskForUser(taskId, user.id)
    }

    @Override
    Task update(User user, Integer taskId,  TaskRequest taskRequest) {
        PreConditions.notNull(user, 'user cannot be null')
        PreConditions.notNull(taskRequest , 'taskRequest cannot be null')
        PreConditions.notNull(taskId , 'taskId cannot be null')

        def task = new Task(
                id : taskId,
                name : taskRequest.name,
                description : taskRequest.description,
                dueDate : taskRequest.dueDate,
                repeatCount : taskRequest.repeatCount,
                repeatFrequency : taskRequest.repeatFrequency,
                user : user
        )
        return taskRepository.save(task)
    }
}
