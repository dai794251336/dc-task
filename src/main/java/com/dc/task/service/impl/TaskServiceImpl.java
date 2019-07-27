/**
 * @title: TaskServiceImpl.java
 * @description: 注解类
 * @author DaiChao
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @date: 20:37 2019/7/16
 * <p>
 * 修改历史:
 * Date          Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 2019/7/16       DaiChao        1.0            初始化版本
 */
package com.dc.task.service.impl;

import com.dc.framework.sql.dao.BaseJpaRepository;
import com.dc.framework.sql.service.impl.GenericServiceImpl;
import com.dc.task.dao.TaskDao;
import com.dc.task.model.Task;
import com.dc.task.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskServiceImpl extends GenericServiceImpl<Task, Long> implements TaskService {

    private final TaskDao taskDao;

    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public BaseJpaRepository<Task, Long> getBaseRepository() {
        return taskDao;
    }

    @Override
    public Task start(Task task) {
        task.setStatus(Task.INPROGRESS);
        task.setBeginTime(new Date());
        task.setUpdateTime(new Date());
        return taskDao.saveAndFlush(task);
    }

    @Override
    public Task finish(Task task) {
        task.setStatus(Task.FINISH);
        task.setEndTime(new Date());
        task.setUpdateTime(new Date());
        return taskDao.saveAndFlush(task);
    }

    @Override
    public Task updateTask(Task task) {
        task.setUpdateTime(new Date());
        return taskDao.saveAndFlush(task);
    }

    @Override
    public Task fail(Task task) {
        task.setStatus(Task.FAILED);
        return taskDao.saveAndFlush(task);
    }
}
