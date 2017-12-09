package com.yordex.test.service

import com.yordex.test.resource.ErrorMessage
import org.springframework.validation.BindingResult

/**
 * Created by Ankush on 06/05/17.
 */
interface ErrorMessageService {

    /*
    * List all the error messages that occured when validating attributes using Spring Validation API
    * */
    List<ErrorMessage> listErrors(BindingResult bindingResult)

}
