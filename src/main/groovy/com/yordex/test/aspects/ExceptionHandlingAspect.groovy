package com.yordex.test.aspects

import com.yordex.test.resource.ErrorMessageResource
import com.yordex.test.service.ErrorMessageService
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import java.time.LocalDateTime

/**
 * Created by Ankush on 06/05/17.
 */
@ControllerAdvice
@Slf4j
class ExceptionHandlingAspect {

    final ErrorMessageService errorMessageService

    ExceptionHandlingAspect(ErrorMessageService errorMessageService){
        this.errorMessageService = errorMessageService
    }

    @ExceptionHandler(MethodArgumentNotValidException)
    ResponseEntity<ErrorMessageResource> listFieldErrors(MethodArgumentNotValidException ex){
        return new ResponseEntity<ErrorMessageResource>(
                new ErrorMessageResource(
                        errorMessageService.listErrors(ex.bindingResult)
                ),
                HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(AuthenticationException)
    ResponseEntity<?> handleAuthenticationErrors(HttpServletRequest request, AuthenticationException ex){
        def path = request.requestURI
        def errorMsg = ex.message
        def responseBody = [ error : errorMsg , timestamp : System.currentTimeMillis() , path : path ]
        return new ResponseEntity(responseBody, HttpStatus.UNAUTHORIZED)
    }


    @ExceptionHandler
    ResponseEntity<?> logAndRenderErrors(
            HttpServletRequest request,
            Throwable ex
    )
    {
        def path = request.requestURI
        log.error('Error occured. Datetime {} , error : ', LocalDateTime.now() , ex )
        def errorMsg = ex.message
        def responseBody  = [ error : errorMsg , timestamp : System.currentTimeMillis() , path : path ]
        return new ResponseEntity(responseBody, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
