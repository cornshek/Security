package com.pd.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GTY
 * 用户详细信息表
 */
@Data
@KeySequence("PERSONAL_AUTO")
@TableName("TB_PERSON")
public class Person implements Serializable {

    private static final long serialVersionUID = -3582981299631419922L;
    /**个人信息编号*/
    @TableId(value = "PERSONAL_INFO_NO",type = IdType.INPUT)
    private Integer personalInfoNo;
    /**用户ID*/
    private Integer userId;
    /**身份证头像路径*/
    private String idCardA;
    /**身份证国徽路径*/
    private String  idCardB;
    /**人脸正面照*/
    private String face;
    /**真实姓名*/
    private String realName;
    /**身份证号*/
    private String idCardNo;
    /**签发机关*/
    private String issuingDepartment;
    /**证件地址*/
    private String  address;
    /**起始期限*/
    private String startingPerion;
    /**结束期限*/
    private String closingPeriod;
    /**联系地址*/
    private String contactAddress;
    /**邮编*/
    private String postCode;
    /**所属行业*/
    private String industry;
    /**职业*/
    private String occupation;
    /**学历*/
    private String education;
    /**账户受益人*/
    private String accountBeneficiary;
    /**实际控制人*/
    private String actualController;
    /**不良诚信记录*/
    private String badCreditRecoed;
    /**纳税居民身份*/
    private String taxResidentStatus;
    /**推荐人*/
    private String recommender;
    /**推荐人ID*/
    private Integer recommenderId;
    /**家庭年收入*/
    private String annualFamilyIncome;
    /**风险测试时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date testTime;
    /**风险测试分数*/
    private String testScore;
    /**用户等级*/
    private String userClass;
    /**联系电话*/
    private String phone;

}