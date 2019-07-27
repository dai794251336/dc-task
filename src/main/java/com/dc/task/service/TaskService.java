/**
 * @title: TaskService.java
 * @description: 注解类
 * @author DaiChao
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @date: 20:36 2019/7/16
 * <p>
 * 修改历史:
 * Date          Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 2019/7/16       DaiChao        1.0            初始化版本
 */
package com.dc.task.service;

import com.dc.framework.sql.service.GenericService;
import com.dc.task.model.Task;

public interface TaskService extends GenericService<Task, Long> {


    /**
     * 开始
     *
     * @param task 任务
     * @return {@link Task}
     */
    Task start(Task task);

    /**
     * 完成任务
     *
     * @param task 任务
     * @return {@link Task}
     */
    Task finish(Task task);

    /**
     * 完成任务
     *
     * @param task 任务
     * @return {@link Task}
     */
    Task updateTask(Task task);

    /**
     * 任务失败
     *
     * @param task 任务
     * @return {@link Task}
     */
    Task fail(Task task);
}
