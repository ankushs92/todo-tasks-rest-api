package com.yordex.test.domain

import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * Created by Ankush on 06/05/17.
 */
@MappedSuperclass
@ToString
class AbstractEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    Integer id
}
