package com.yordex.test.integration

import com.yordex.test.BaseSpec
import com.yordex.test.YordexTestApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

/**
 * Created by Ankush on 21/05/17.
 */
@SpringBootTest(classes = YordexTestApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTestSpec extends BaseSpec{

    protected static final String BASE_LOCALHOST_URL = "http://localhost"

    @LocalServerPort
    int port

    @Value("\${version}")
    String version

    @Autowired
    TestRestTemplate testRestTemplate
}
