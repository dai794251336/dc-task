/**
 * @title: JmsTaskProcessor.java
 * @description: 注解类
 * @author DaiChao
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @date: 21:18 2019/7/16
 * <p>
 * 修改历史:
 * Date          Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 2019/7/16       DaiChao        1.0            初始化版本
 */
package com.dc.task.processor.jms;

import com.dc.framework.utils.JsonUtils;
import com.dc.jms.processor.BaseMessage;
import com.dc.jms.processor.Processor;
import com.dc.task.model.Task;
import com.dc.task.process.AbstractTaskProcess;
import com.dc.task.util.TaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class JmsTaskProcessor implements Processor {

    private final Map<String, AbstractTaskProcess> process;

    private final TaskUtil taskUtil;

    public JmsTaskProcessor(Map<String, AbstractTaskProcess> process, TaskUtil taskUtil) {
        this.process = process;
        this.taskUtil = taskUtil;
    }

    @Override
    public Boolean doProcessor(Map<String, Object> params) {
        Task task = JsonUtils.toObject(params.get("task").toString(), Task.class);
        if (task == null) {
            throw new NullPointerException("任务为空");
        }
        task = process.get(task.getName()).create(task);
        if (null != task.getId()) {
            taskUtil.add(task);
            return true;
        }
        log.error("任务保存失败！");
        return false;
    }
}
