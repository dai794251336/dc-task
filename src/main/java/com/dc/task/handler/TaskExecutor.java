/**
 * @title: MnDpTaskExecutor.java
 * @description: 注解类
 * @author DaiChao
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @date: 10:49 2019/7/10
 * <p>
 * 修改历史:
 * Date          Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 2019/7/10       DaiChao        1.0            初始化版本
 */
package com.dc.task.handler;

import com.dc.framework.utils.sql.SqlFilter;
import com.dc.task.model.Task;
import com.dc.task.service.TaskService;
import com.dc.task.util.TaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Slf4j
@Component
public class TaskExecutor {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private final TaskHandler taskHandler;

    private final TaskService taskService;

    private final TaskUtil taskUtil;

    public TaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor, TaskHandler taskHandler, TaskService taskService, TaskUtil taskUtil) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.taskHandler = taskHandler;
        this.taskService = taskService;
        this.taskUtil = taskUtil;
    }

    @Value("#{${taks.pools:5}}")
    private int pools;


    @PostConstruct
    public void init() {
        for (int i = 0; i < pools; i++) {
            threadPoolTaskExecutor.execute(taskHandler);
        }

        SqlFilter sqlFilter = new SqlFilter();
        sqlFilter.addQueryParams("status", SqlFilter.EQ, Task.CREATE);
        sqlFilter.addQueryParams("workId", SqlFilter.EQ, taskUtil.getWorkId());
        Collection<Task> unFinishTask = taskService.findAll(sqlFilter);
        if (null != unFinishTask && unFinishTask.size() > 0) {
            log.info("未完成的任务个数：{}", unFinishTask.size());
            unFinishTask.forEach(taskUtil::add);
        }
    }

}
