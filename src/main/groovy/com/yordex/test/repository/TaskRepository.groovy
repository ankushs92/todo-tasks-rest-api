package com.yordex.test.repository

import com.yordex.test.domain.Task
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param



interface TaskRepository extends PagingAndSortingRepository<Task, Integer> {

    @Query('FROM Task WHERE user.id = :userId')
    List<Task> findAllTaskForUser(@Param('userId') Integer userId, Pageable pageable)

    @Query('FROM Task WHERE id=:taskId AND user.id = :userId')
    Task findOneForUser(@Param('taskId') Integer taskId, @Param('userId') Integer userId)

    @Query('DELETE FROM Task WHERE id = :taskId AND user.id = :userId')
    @Modifying
    void deleteTaskForUser(@Param('taskId') Integer taskId, @Param('userId') Integer userId)


}
