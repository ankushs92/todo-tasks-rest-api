package com.yordex.test.integration

import com.yordex.test.util.TestConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

/**
 * Created by Ankush on 21/05/17.
 */
class AuthenticationSpec extends IntegrationTestSpec{


    def "Query endpoint /api/{version}/tasks with valid credentials. Http status code should be 200 0K"(){
        when :
            def response = testRestTemplate.withBasicAuth(TestConstants.VALID_USERNAME,TestConstants.USER_PASSWORD)
                                           .getForEntity("$BASE_LOCALHOST_URL:$port/api/$version/tasks", String)

        then :
            response.statusCode == HttpStatus.OK
    }

    def "Query endpoint /api/{version}/tasks for a user whose account is locked. Http status code should be 401 Unauthorized"(){
        when :
            def response = testRestTemplate.withBasicAuth(TestConstants.USER_WITH_ACCOUNT_LOCKED,TestConstants.USER_PASSWORD)
                    .getForEntity("$BASE_LOCALHOST_URL:$port/api/$version/tasks", String)

        then :
            response.statusCode == HttpStatus.UNAUTHORIZED
    }

    def "Query endpoint /api/{version}/tasks for a user whose account is disabled. Http status code should be 401 Unauthorized"(){
        when :
            def response = testRestTemplate.withBasicAuth(TestConstants.USER_WITH_ACCOUNT_DISABLED,TestConstants.USER_PASSWORD)
                  .getForEntity("$BASE_LOCALHOST_URL:$port/api/$version/tasks", String)

        then :
            response.statusCode == HttpStatus.UNAUTHORIZED
    }

    def "Query endpoint /api/{version}/tasks for a user whose credentials have expired. Http status code should be 401 Unauthorized"(){
        when :
            def response = testRestTemplate.withBasicAuth(TestConstants.USER_WITH_CREDENTIALS_EXPIRED,TestConstants.USER_PASSWORD)
                    .getForEntity("$BASE_LOCALHOST_URL:$port/api/$version/tasks", String)

        then :
            response.statusCode == HttpStatus.UNAUTHORIZED
    }

    def "Query endpoint /api/{version}/tasks for a user whose account has expired. Http status code should be 401 Unauthorized"(){
        when :
            def response = testRestTemplate.withBasicAuth(TestConstants.USER_WITH_ACCOUNT_EXPIRED,TestConstants.USER_PASSWORD)
                    .getForEntity("$BASE_LOCALHOST_URL:$port/api/$version/tasks", String)

        then :
            response.statusCode == HttpStatus.UNAUTHORIZED
    }




}
