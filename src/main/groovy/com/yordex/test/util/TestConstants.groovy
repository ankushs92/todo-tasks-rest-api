package com.yordex.test.util

import com.yordex.test.domain.RepeatFrequency

import java.time.LocalDate

/**
 * Created by Ankush on 21/05/17.
 */
class TestConstants {

    static final String VALID_USERNAME = "ankush"
    static final String USER_PASSWORD = "ankush123"

    static final String USER_WITH_ACCOUNT_LOCKED = "accountLockedUser"
    static final String USER_WITH_ACCOUNT_DISABLED = "accountDisabledUser"
    static final String USER_WITH_CREDENTIALS_EXPIRED = "credentialsExpiredUser"
    static final String USER_WITH_ACCOUNT_EXPIRED = "accountExpiredUser"


    static final Integer TASK1_ID = 1
    static final String TASK1_NAME = "task1Name"
    static final String TASK1_DESC = "task1Desc"
    static final LocalDate TASK1_DUE_DATE = LocalDate.now()
    static final RepeatFrequency TASK1_REPEAT_FREQ = RepeatFrequency.DAILY
    static final Integer TASK1_REPEAT_COUNT = 1

    static final Integer TASK2_ID = 2
    static final String TASK2_NAME = "task2Name"
    static final String TASK2_DESC = "task2Desc"
    static final LocalDate TASK2_DUE_DATE = LocalDate.now()
    static final RepeatFrequency TASK2_REPEAT_FREQ = RepeatFrequency.MONTHLY
    static final Integer TASK2_REPEAT_COUNT = 2


    static final Integer TASK3_ID = 3
    static final String TASK3_NAME = "task3Name"
    static final String TASK3_DESC = "task3Desc"
    static final LocalDate TASK3_DUE_DATE = LocalDate.now()
    static final RepeatFrequency TASK3_REPEAT_FREQ = RepeatFrequency.YEARLY
    static final Integer TASK3_REPEAT_COUNT = 3



}
