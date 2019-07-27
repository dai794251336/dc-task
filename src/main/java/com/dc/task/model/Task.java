/**
 * @Title: Task.java
 * @Description: 作业/任务类
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @author: xingweiwei
 * @version V1.0
 * @Date: 2018年07月25日
 * <p>
 * 修改历史:
 * Date                 Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2018年07月25日       xingweiwei       1.0            初始化版本
 */
package com.dc.task.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "base_task")
public class Task implements Serializable {
    private static final long serialVersionUID = -8401055785071209865L;

    public static final int CREATE = 0;
    /**
     * 运行中
     */
    public static final int INPROGRESS = 1;

    public static final int FINISH = 2;

    public static final int FAILED = -1;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 任务编号
     */
    private String taskId;

    /**
     * 类型
     * once ： 单次执行
     * cron ： 定时执行
     */
    private String type;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务状态
     */
    private int status;

    /**
     * 任务完成百分比
     */
    private BigDecimal percent;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginTime;

    /**
     * 停止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 机器编号
     */
    private String workId;

    /**
     * 需要处理的对象
     */
    private String config;

    /**
     * 完成的对象
     */
    @Transient
    private List<Object> completed;

    /**
     * 错误信息等
     */
    private String msg;


}
