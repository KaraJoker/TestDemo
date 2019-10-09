package com.cn.company.cqrs.q.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ToString
@Accessors(chain = true)
@Document(collection = "pt")
public class PT implements Serializable {

    @Id
    private String id;

    /**
     * 学生id
     **/
    private String student_Id;

    /**
     * 学生姓名
     **/
    private String student_name;

    /**
     * 学校id
     **/
    private String school_id;

    /**
     * 学校名称
     **/
    private String school_name;

    /**
     * 年级id
     **/
    private String grade_id;

    /**
     * 年级名称
     **/
    private String grade_name;

    /**
     * 班级id
     **/
    private String class_id;

    /**
     * 班级名称
     **/
    private String class_name;

    /**
     * 批次
     **/
    private String batch;

    /**
     * 测试时间
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS",timezone = "GMT+8")
    private Date test_time;

    /**
     * 项目
     **/
    private List<Item> items;
}
