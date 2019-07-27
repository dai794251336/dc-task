/**
 * @title: TaskHandler.java
 * @description: 注解类
 * @author DaiChao
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @date: 20:39 2019/7/16
 * <p>
 * 修改历史:
 * Date          Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 2019/7/16       DaiChao        1.0            初始化版本
 */
package com.dc.task.handler;

import com.dc.task.model.Task;
import com.dc.task.process.AbstractTaskProcess;
import com.dc.task.util.TaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class TaskHandler implements Runnable {

    private final TaskUtil taskUtil;

    private final Map<String, AbstractTaskProcess> process;

    public TaskHandler(TaskUtil taskUtil, Map<String, AbstractTaskProcess> process) {
        this.taskUtil = taskUtil;
        this.process = process;
    }

    @Override
    public void run() {
        while (true) {
            Task task = taskUtil.get();
            if (null == task) {
                log.info("没有任务");
                continue;
            }

            AbstractTaskProcess abstractTaskProcess = process.get(task.getName());
            boolean flag = abstractTaskProcess.doProcess(task);
            if (flag) {
                log.info("{}执行成功", task.getName());
            } else {
                log.error("{}执行失败", task.getName());
            }

        }
    }
}
