/**
 * @title: TaskProcess.java
 * @description: 任务接口
 * @author DaiChao
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @date: 9:28 2019/7/9
 * <p>
 * 修改历史:
 * Date          Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 2019/7/9       DaiChao        1.0            初始化版本
 */
package com.dc.task.process;

import com.dc.task.model.Task;

import java.io.Serializable;


public interface TaskProcess {
    /**
     * 创建任务
     *
     * @param task 任务
     * @return Task {@link Task}
     */
    Task create(Task task)  ;

    /**
     * 获取任务
     *
     * @param id 任务ID
     * @return Task {@link Task}
     */
    Task get(Long id)   ;

    /**
     * 获取任务
     *
     * @param task 任务
     * @return Task {@link Task}
     */
    Task update(Task task)   ;


    /**
     * 任务开始
     *
     * @param task 任务
     * @return Task {@link Task}
     */
    Task start(Task task)  ;


    /**
     * 任务失败
     *
     * @param task 任务
     * @return Task {@link Task}
     */
    Task fail(Task task)  ;


    /**
     * 任务结束
     *
     * @param task 任务
     * @return Task {@link Task}
     */
    Task finish(Task task)  ;

    /**
     * 价差配置
     *
     * @param task 任务
     * @return boolean
     */
    boolean checkConfig(Task task)   ;

    /**
     * 选择器
     *
     * @param task 任务
     * @return boolean
     */
    boolean doProcess(Task task);

    /**
     * 具体的业务实现该方法
     *
     * @param task 任务
     * @return Task {@link Task}
     */
    boolean process(Task task);
}
