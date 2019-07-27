/**
 * @title: DemoProcess.java
 * @description: 注解类
 * @author DaiChao
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @date: 21:24 2019/7/16
 * <p>
 * 修改历史:
 * Date          Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 2019/7/16       DaiChao        1.0            初始化版本
 */
package com.dc.task.processor;

import com.dc.framework.utils.JsonUtils;
import com.dc.task.model.Task;
import com.dc.task.process.AbstractTaskProcess;
import com.dc.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class DemoProcess extends AbstractTaskProcess {

    public DemoProcess(TaskService taskService) {
        super(taskService);
    }

    @Override
    public void setName() {
        setTaskName("demoProcess");
    }

    @Override
    public boolean process(Task task) {
        start(task);
        log.info("任务信息：{}", JsonUtils.toJsonString(task));
        task.setPercent(BigDecimal.ONE);
        finish(task);
        return true;
    }
}
