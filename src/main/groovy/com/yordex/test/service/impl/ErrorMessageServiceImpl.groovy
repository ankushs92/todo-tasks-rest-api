package com.yordex.test.service.impl

import com.yordex.test.resource.ErrorMessage
import com.yordex.test.service.ErrorMessageService
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError

/**
 * Created by Ankush on 06/05/17.
 */
@Service
class ErrorMessageServiceImpl implements ErrorMessageService{

    final MessageSource msgSource

    ErrorMessageServiceImpl(MessageSource msgSource){
        this.msgSource = msgSource
    }

    @Override
    List<ErrorMessage> listErrors(BindingResult bindingResult) {
        List<ObjectError> globalErrors = bindingResult.globalErrors
        return globalErrors.collect{ fieldError ->
            def field= fieldError.code
            def msg =  fieldError.defaultMessage
            return new ErrorMessage(field : field, msg : msg)
        }
    }
}
