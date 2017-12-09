package com.yordex.test.domain

import com.yordex.test.domain.auth.User
import groovy.transform.ToString

import javax.persistence.CascadeType
import javax.persistence.Column;
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime;

@Entity
@Table(name='tasks')
@ToString(includePackage = false, excludes = ["user"])
class Task extends AbstractEntity {

    @Column(name='description', length = 1000, nullable = false, columnDefinition = "VARCHAR(1000) DEFAULT '' ")
    String description

    @Column(name='name')
    String name

    @Column(name='created_on', nullable = false, updatable = false)
    ZonedDateTime creationDateTime

    @Column(name='modified_on', nullable = false)
    ZonedDateTime modificationDateTime

    @Column(name='due_date', nullable = false)
    LocalDate dueDate

    @Column(name='repeat_freq', nullable = false, columnDefinition = "INT(2) DEFAULT 0 ")
    RepeatFrequency repeatFrequency

    @Column(name='repeat_count', nullable = false, columnDefinition =  'INT(2) DEFAULT 0')
    Integer repeatCount

    @ManyToOne
    @JoinColumn(name='user_id')
    User user

    @PrePersist
    void beforeCreating(){
        def now = ZonedDateTime.now(ZoneId.of('UTC'))
        this.creationDateTime = now
        this.modificationDateTime = now  // Don't want to set a null value.
    }

    @PreUpdate
    void beforeUpdating(){
        this.modificationDateTime = ZonedDateTime.now(ZoneId.of('UTC'))
    }

}
