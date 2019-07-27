/**
 * @title: BaseTaskProcess.java
 * @description: 注解类
 * @author DaiChao
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @date: 9:47 2019/7/9
 * <p>
 * 修改历史:
 * Date          Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 2019/7/9       DaiChao        1.0            初始化版本
 */
package com.dc.task.process;

import com.dc.task.model.Task;
import com.dc.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
public abstract class AbstractTaskProcess implements TaskProcess {

    private String taskName;

    public abstract void setName();

    protected void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    private final TaskService taskService;

    public AbstractTaskProcess(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public Task create(Task task) {
        task.setStatus(Task.CREATE);
        task.setCreateTime(new Date());
        taskService.save(task);
        return task;
    }

    @Override
    public Task get(Long id) {
        return taskService.get(id);
    }

    @Override
    public Task update(Task task) {
        task.setUpdateTime(new Date());
        taskService.update(task);
        return task;
    }

    @Override
    public Task start(Task task) {
        return taskService.start(task);
    }

    @Override
    public Task fail(Task task) {
        return taskService.fail(task);
    }

    @Override
    public Task finish(Task task) {
        if (BigDecimal.ONE.compareTo(task.getPercent()) == 0) {
            return taskService.finish(task);
        } else {
            task.setMsg("任务状态与进度不符合");
            return taskService.fail(task);
        }
    }

    @Override
    public boolean checkConfig(Task task) {
        String taskConfig = task.getConfig();
        if (StringUtils.isBlank(taskConfig)) {
            log.error("任务配置信息错误, task id = " + task.getId());

            task.setMsg("任务配置信息错误");
            this.fail(task);
            return true;
        }
        return false;
    }

    @Override
    public boolean doProcess(Task task) {
        boolean flag = true;
        switch (task.getStatus()) {
            case Task.CREATE:
                flag = process(task);
                break;
            case Task.INPROGRESS:
                update(task);
                break;
            case Task.FINISH:
                finish(task);
                break;
            case Task.FAILED:
                fail(task);
                break;
            default:
                throw new IllegalArgumentException("未知的任务状态");
        }
        return flag;

    }
}