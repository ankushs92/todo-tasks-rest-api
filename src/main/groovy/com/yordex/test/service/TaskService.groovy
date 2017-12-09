package com.yordex.test.service

import com.yordex.test.domain.Task
import com.yordex.test.domain.auth.User
import com.yordex.test.request.TaskRequest
import com.yordex.test.resource.TaskListResource
import com.yordex.test.resource.TaskResource
import org.springframework.data.domain.Pageable

/**
 * Created by Ankush on 06/05/17.
 */
interface TaskService {

    /*
    * Save a Task based on request attributes
    * */
    Task create(User user, TaskRequest taskRequest)

    /*
    * Get list of tasks
    * */
    TaskListResource findAllForUser(User user, Pageable pageable)

    /*
    * Get a Task for a User by its taskId
    * */
    TaskResource findOneForUser(User user ,Integer taskId)

    /*
    * Delete a task for user by taskId
    * */
    void delete(User user, Integer taskId)

    /*
    * Update a task for a User by and the attributes received in TaskRequest
    * */
    Task update(User user, Integer taskId , TaskRequest taskRequest )

}
