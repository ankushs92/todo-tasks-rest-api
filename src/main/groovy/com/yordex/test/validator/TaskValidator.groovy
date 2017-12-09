package com.yordex.test.validator

import com.yordex.test.domain.RepeatFrequency
import com.yordex.test.request.TaskRequest
import com.yordex.test.util.Strings
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

import java.time.LocalDate
import java.time.ZoneId

/**
 * Validator for Tasks
 * Created by Ankush on 06/05/17.
 */
@Component
class TaskValidator implements Validator {

    @Override
    boolean supports(Class<?> clazz) {
        return TaskRequest.class.isAssignableFrom(clazz)
    }

    @Override
    void validate(Object target, Errors errors) {
        def taskReq = target as TaskRequest

        def name = taskReq.name
        def description = taskReq.description
        def dueDate = taskReq.dueDate
        def repeatFreq = taskReq.repeatFrequency
        def repeatCount = taskReq.repeatCount

        validateName(name,errors)
        validateDescription(description,errors)
        validateDueDate(dueDate,errors)
        validateRepeatFreqCount(repeatFreq, repeatCount, errors)

    }

    private static void validateName(name, errors){
        if(!Strings.hasText(name)){
            errors.reject('name','Please enter a name for task')
        }
        else{
            //Limiting the name of the task to 255 characters
            if(name.length() > 255) {
                errors.reject('name','Name cannot be more than 255 characters')
            }
        }
    }

    private static void validateDescription(desc, errors){
        if(!Strings.hasText(desc)){
            errors.reject('description','Please enter a description for task')
        }
        else{
            //Limiting the description of the task to 1000 characters
            if(desc.length() > 1000){
                errors.reject('description','Description cannot be more than 1000 characters')
            }
        }
    }

    private static void validateDueDate(dueDate, errors){
        if(!dueDate){
            errors.reject('dueDate','Please enter a due date for task')
        }
        else{
            def today = LocalDate.now(ZoneId.of('UTC'))
            if(dueDate.isBefore(today)){
                errors.reject('dueDate','Due date for task cannot possibly be before today')
            }
        }
    }
    // Default value of this field is 'none', so no need for a null check
    private static void validateRepeatFreqCount(repeatFreq, repeatCount, errors){

        //Avoid Integer class groovy truth because then !repeatCount expression would evaluate for false when repeatCount = 0
        if(repeatCount == null){
            errors.reject('repeatCount','Repeat Count cannot be null')
        }
        else{
            if(repeatFreq == RepeatFrequency.NONE){
                if(repeatCount > 0){
                    errors.reject('repeatCount','Repeat count for a Task cannot be greater than 0 when the task repeat frequency is NONE')
                }
            }
        }
    }


}
