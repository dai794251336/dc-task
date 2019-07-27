/**
 * @title: TaskDao.java
 * @description: 注解类
 * @author DaiChao
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @date: 15:14 2019/7/15
 * <p>
 * 修改历史:
 * Date          Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 2019/7/15       DaiChao        1.0            初始化版本
 */
package com.dc.task.dao;

import com.dc.framework.sql.dao.BaseJpaRepository;
import com.dc.task.model.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDao extends BaseJpaRepository<Task, Long> {
}
