package com.yordex.test.validator

import com.yordex.test.BaseSpec
import com.yordex.test.domain.Task
import com.yordex.test.request.TaskRequest
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Errors

/**
 * Created by Ankush on 07/05/17.
 */
class TaskValidatorSpec extends BaseSpec {

    def "Validate a Task"(){
        given : 'The task validator'
            def taskValidator = new TaskValidator()
            //Name greater than 255 characters
            //Description greater than 1000 characters
            //No due date passed
            def taskName = 'a' * 256
            def taskDescription = 'a' * 1001
            def taskRequest = new TaskRequest(taskDescription, taskName, null, null, null)

        when :
            Errors errors = new BeanPropertyBindingResult(taskRequest, "invalidTask")
            taskValidator.validate(taskRequest, errors)

        then : "The error count should = 4"
             errors
             errors.errorCount == 4
    }
}
