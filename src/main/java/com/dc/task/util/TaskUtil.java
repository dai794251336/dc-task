/**
 * @title: TaskUtil.java
 * @description: 注解类
 * @author DaiChao
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @date: 20:40 2019/7/16
 * <p>
 * 修改历史:
 * Date          Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 2019/7/16       DaiChao        1.0            初始化版本
 */
package com.dc.task.util;

import com.dc.framework.utils.BaseAppIdUtil;
import com.dc.framework.utils.JsonUtils;
import com.dc.jms.processor.BaseMessage;
import com.dc.jms.utils.AmqpSendUtil;
import com.dc.task.model.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class TaskUtil {

    private final BaseAppIdUtil baseAppIdUtil;

    private final AmqpSendUtil amqpSendUtil;

    public TaskUtil(BaseAppIdUtil baseAppIdUtil, AmqpSendUtil amqpSendUtil) {
        this.baseAppIdUtil = baseAppIdUtil;
        this.amqpSendUtil = amqpSendUtil;
    }

    private final static BlockingQueue<Task> TASKS_QUEUE = new LinkedBlockingQueue<>(2000);

    /**
     * 机器ID 提供get方法
     */
    private String workId;

    public String getWorkId() {
        return workId;
    }

    @PostConstruct
    public void init() {
        workId = baseAppIdUtil.getWorkId();
    }

    /**
     * 加入队列
     *
     * @param task 任务队列
     * @throws RuntimeException 异常
     */
    public void add(Task task) {
        if (!TASKS_QUEUE.offer(task)) {
            throw new RuntimeException("ExcelTask加入队列失败");
        }
    }

    /**
     * 获取任务
     */
    public Task get() {
        try {
            return TASKS_QUEUE.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送任务
     *
     * @param task 任务
     */
    public void sendTask(Task task) {
        if (StringUtils.isBlank(task.getName())) {
            throw new RuntimeException("任务名称不能为空");
        }
        task.setTaskId(UUID.randomUUID().toString());
        task.setWorkId(workId);
        task.setPercent(BigDecimal.ZERO);

        BaseMessage baseMessage = new BaseMessage();
        Map<String, Object> params = new HashMap<>(1);
        params.put("task", JsonUtils.toJson(task));
        baseMessage.setBeanName("jmsTaskProcessor");
        baseMessage.setConfig(params);

        amqpSendUtil.sendDefaultMsg(baseMessage);
    }
}
