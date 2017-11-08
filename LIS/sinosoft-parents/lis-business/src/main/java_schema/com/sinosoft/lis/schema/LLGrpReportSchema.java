/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LLGrpReportDB;

/*
 * <p>ClassName: LLGrpReportSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLGrpReportSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLGrpReportSchema.class);

	// @Field
	/** 团体报案号 */
	private String RptNo;
	/** 申请类型 */
	private String RgtClass;
	/** 号码类型 */
	private String RgtObj;
	/** 其他号码 */
	private String RgtObjNo;
	/** 报案方式 */
	private String RptMode;
	/** 报案人与被保人关系 */
	private String Relation;
	/** 报案人姓名 */
	private String RptorName;
	/** 报案人通讯地址 */
	private String RptorAddress;
	/** 报案人电话 */
	private String RptorPhone;
	/** 报案人手机 */
	private String RptorMobile;
	/** 报案人电邮 */
	private String Email;
	/** 邮政编码 */
	private String PostCode;
	/** 回执发送方式 */
	private String ReturnMode;
	/** 出险日期 */
	private Date AccidentDate;
	/** 出险地点 */
	private String AccidentSite;
	/** 出险原因 */
	private String AccidentReason;
	/** 出险过程和结果 */
	private String AccidentCourse;
	/** 报案日期 */
	private Date RptDate;
	/** 报备产生日期 */
	private Date CaseNoDate;
	/** 报备结束日期 */
	private Date CaseEndDate;
	/** 报案撤销原因 */
	private String RgtReason;
	/** 不立案原因 */
	private String NoRgtReason;
	/** 备注 */
	private String Remark;
	/** 立案标志 */
	private String RgtFlag;
	/** 案件有效标志 */
	private String AvaiFlag;
	/** 案件有效原因 */
	private String AvaliReason;
	/** 管理机构 */
	private String MngCom;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 受托人类型 */
	private String AssigneeType;
	/** 受托人代码 */
	private String AssigneeCode;
	/** 受托人姓名 */
	private String AssigneeName;
	/** 受托人性别 */
	private String AssigneeSex;
	/** 受托人电话 */
	private String AssigneePhone;
	/** 受托人地址 */
	private String AssigneeAddr;
	/** 受托人邮编 */
	private String AssigneeZip;
	/** 人数 */
	private int PerCount;

	public static final int FIELDNUM = 40;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLGrpReportSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RptNo";

		PK = pk;
	}

	/**
	* Schema克隆
	* @return Object
	* @throws CloneNotSupportedException
	*/
	public Object clone()
		throws CloneNotSupportedException
	{
		LLGrpReportSchema cloned = (LLGrpReportSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRptNo()
	{
		return RptNo;
	}
	public void setRptNo(String aRptNo)
	{
		RptNo = aRptNo;
	}
	/**
	* 1 个人<p>
	* 2 团体<p>
	* 3 家庭单
	*/
	public String getRgtClass()
	{
		return RgtClass;
	}
	public void setRgtClass(String aRgtClass)
	{
		RgtClass = aRgtClass;
	}
	/**
	* 1 个人<p>
	* 2 团体<p>
	* 3 家庭单
	*/
	public String getRgtObj()
	{
		return RgtObj;
	}
	public void setRgtObj(String aRgtObj)
	{
		RgtObj = aRgtObj;
	}
	public String getRgtObjNo()
	{
		return RgtObjNo;
	}
	public void setRgtObjNo(String aRgtObjNo)
	{
		RgtObjNo = aRgtObjNo;
	}
	/**
	* 01电话报案<p>
	* 02上门报案<p>
	* 03传真报案<p>
	* 04网上报案<p>
	* 05其它方式
	*/
	public String getRptMode()
	{
		return RptMode;
	}
	public void setRptMode(String aRptMode)
	{
		RptMode = aRptMode;
	}
	/**
	* GX01  本人<p>
	* GX02  保单服务人员<p>
	* GX03  同事<p>
	* GX04  朋友<p>
	* GX05  亲戚<p>
	* GX06  妻子<p>
	* GX07  丈夫<p>
	* GX08  儿女<p>
	* GX09  父亲<p>
	* GX10  母亲<p>
	* GX11  其他
	*/
	public String getRelation()
	{
		return Relation;
	}
	public void setRelation(String aRelation)
	{
		Relation = aRelation;
	}
	public String getRptorName()
	{
		return RptorName;
	}
	public void setRptorName(String aRptorName)
	{
		RptorName = aRptorName;
	}
	public String getRptorAddress()
	{
		return RptorAddress;
	}
	public void setRptorAddress(String aRptorAddress)
	{
		RptorAddress = aRptorAddress;
	}
	public String getRptorPhone()
	{
		return RptorPhone;
	}
	public void setRptorPhone(String aRptorPhone)
	{
		RptorPhone = aRptorPhone;
	}
	public String getRptorMobile()
	{
		return RptorMobile;
	}
	public void setRptorMobile(String aRptorMobile)
	{
		RptorMobile = aRptorMobile;
	}
	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String aEmail)
	{
		Email = aEmail;
	}
	public String getPostCode()
	{
		return PostCode;
	}
	public void setPostCode(String aPostCode)
	{
		PostCode = aPostCode;
	}
	/**
	* 【不用】
	*/
	public String getReturnMode()
	{
		return ReturnMode;
	}
	public void setReturnMode(String aReturnMode)
	{
		ReturnMode = aReturnMode;
	}
	public String getAccidentDate()
	{
		if( AccidentDate != null )
			return fDate.getString(AccidentDate);
		else
			return null;
	}
	public void setAccidentDate(Date aAccidentDate)
	{
		AccidentDate = aAccidentDate;
	}
	public void setAccidentDate(String aAccidentDate)
	{
		if (aAccidentDate != null && !aAccidentDate.equals("") )
		{
			AccidentDate = fDate.getDate( aAccidentDate );
		}
		else
			AccidentDate = null;
	}

	public String getAccidentSite()
	{
		return AccidentSite;
	}
	public void setAccidentSite(String aAccidentSite)
	{
		AccidentSite = aAccidentSite;
	}
	/**
	* 1意外<p>
	* 2疾病
	*/
	public String getAccidentReason()
	{
		return AccidentReason;
	}
	public void setAccidentReason(String aAccidentReason)
	{
		AccidentReason = aAccidentReason;
	}
	public String getAccidentCourse()
	{
		return AccidentCourse;
	}
	public void setAccidentCourse(String aAccidentCourse)
	{
		AccidentCourse = aAccidentCourse;
	}
	public String getRptDate()
	{
		if( RptDate != null )
			return fDate.getString(RptDate);
		else
			return null;
	}
	public void setRptDate(Date aRptDate)
	{
		RptDate = aRptDate;
	}
	public void setRptDate(String aRptDate)
	{
		if (aRptDate != null && !aRptDate.equals("") )
		{
			RptDate = fDate.getDate( aRptDate );
		}
		else
			RptDate = null;
	}

	/**
	* 【不用】
	*/
	public String getCaseNoDate()
	{
		if( CaseNoDate != null )
			return fDate.getString(CaseNoDate);
		else
			return null;
	}
	public void setCaseNoDate(Date aCaseNoDate)
	{
		CaseNoDate = aCaseNoDate;
	}
	public void setCaseNoDate(String aCaseNoDate)
	{
		if (aCaseNoDate != null && !aCaseNoDate.equals("") )
		{
			CaseNoDate = fDate.getDate( aCaseNoDate );
		}
		else
			CaseNoDate = null;
	}

	/**
	* 【不用】
	*/
	public String getCaseEndDate()
	{
		if( CaseEndDate != null )
			return fDate.getString(CaseEndDate);
		else
			return null;
	}
	public void setCaseEndDate(Date aCaseEndDate)
	{
		CaseEndDate = aCaseEndDate;
	}
	public void setCaseEndDate(String aCaseEndDate)
	{
		if (aCaseEndDate != null && !aCaseEndDate.equals("") )
		{
			CaseEndDate = fDate.getDate( aCaseEndDate );
		}
		else
			CaseEndDate = null;
	}

	/**
	* 【不用】
	*/
	public String getRgtReason()
	{
		return RgtReason;
	}
	public void setRgtReason(String aRgtReason)
	{
		RgtReason = aRgtReason;
	}
	public String getNoRgtReason()
	{
		return NoRgtReason;
	}
	public void setNoRgtReason(String aNoRgtReason)
	{
		NoRgtReason = aNoRgtReason;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	/**
	* 一旦保存立案信息，都算已经立案<p>
	* 10未立案<p>
	* 20已立案<p>
	* 30来自立案
	*/
	public String getRgtFlag()
	{
		return RgtFlag;
	}
	public void setRgtFlag(String aRgtFlag)
	{
		RgtFlag = aRgtFlag;
	}
	/**
	* 记录该案件是否确认，如果确认将会产生工作流信息<p>
	* 10-保存未确认<p>
	* 20-已确认进入立案并产生工作流
	*/
	public String getAvaiFlag()
	{
		return AvaiFlag;
	}
	public void setAvaiFlag(String aAvaiFlag)
	{
		AvaiFlag = aAvaiFlag;
	}
	/**
	* 【不用】
	*/
	public String getAvaliReason()
	{
		return AvaliReason;
	}
	public void setAvaliReason(String aAvaliReason)
	{
		AvaliReason = aAvaliReason;
	}
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		MngCom = aMngCom;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/**
	* 0客户<p>
	* 1业务员
	*/
	public String getAssigneeType()
	{
		return AssigneeType;
	}
	public void setAssigneeType(String aAssigneeType)
	{
		AssigneeType = aAssigneeType;
	}
	public String getAssigneeCode()
	{
		return AssigneeCode;
	}
	public void setAssigneeCode(String aAssigneeCode)
	{
		AssigneeCode = aAssigneeCode;
	}
	public String getAssigneeName()
	{
		return AssigneeName;
	}
	public void setAssigneeName(String aAssigneeName)
	{
		AssigneeName = aAssigneeName;
	}
	public String getAssigneeSex()
	{
		return AssigneeSex;
	}
	public void setAssigneeSex(String aAssigneeSex)
	{
		AssigneeSex = aAssigneeSex;
	}
	public String getAssigneePhone()
	{
		return AssigneePhone;
	}
	public void setAssigneePhone(String aAssigneePhone)
	{
		AssigneePhone = aAssigneePhone;
	}
	public String getAssigneeAddr()
	{
		return AssigneeAddr;
	}
	public void setAssigneeAddr(String aAssigneeAddr)
	{
		AssigneeAddr = aAssigneeAddr;
	}
	public String getAssigneeZip()
	{
		return AssigneeZip;
	}
	public void setAssigneeZip(String aAssigneeZip)
	{
		AssigneeZip = aAssigneeZip;
	}
	public int getPerCount()
	{
		return PerCount;
	}
	public void setPerCount(int aPerCount)
	{
		PerCount = aPerCount;
	}
	public void setPerCount(String aPerCount)
	{
		if (aPerCount != null && !aPerCount.equals(""))
		{
			Integer tInteger = new Integer(aPerCount);
			int i = tInteger.intValue();
			PerCount = i;
		}
	}


	/**
	* 使用另外一个 LLGrpReportSchema 对象给 Schema 赋值
	* @param: aLLGrpReportSchema LLGrpReportSchema
	**/
	public void setSchema(LLGrpReportSchema aLLGrpReportSchema)
	{
		this.RptNo = aLLGrpReportSchema.getRptNo();
		this.RgtClass = aLLGrpReportSchema.getRgtClass();
		this.RgtObj = aLLGrpReportSchema.getRgtObj();
		this.RgtObjNo = aLLGrpReportSchema.getRgtObjNo();
		this.RptMode = aLLGrpReportSchema.getRptMode();
		this.Relation = aLLGrpReportSchema.getRelation();
		this.RptorName = aLLGrpReportSchema.getRptorName();
		this.RptorAddress = aLLGrpReportSchema.getRptorAddress();
		this.RptorPhone = aLLGrpReportSchema.getRptorPhone();
		this.RptorMobile = aLLGrpReportSchema.getRptorMobile();
		this.Email = aLLGrpReportSchema.getEmail();
		this.PostCode = aLLGrpReportSchema.getPostCode();
		this.ReturnMode = aLLGrpReportSchema.getReturnMode();
		this.AccidentDate = fDate.getDate( aLLGrpReportSchema.getAccidentDate());
		this.AccidentSite = aLLGrpReportSchema.getAccidentSite();
		this.AccidentReason = aLLGrpReportSchema.getAccidentReason();
		this.AccidentCourse = aLLGrpReportSchema.getAccidentCourse();
		this.RptDate = fDate.getDate( aLLGrpReportSchema.getRptDate());
		this.CaseNoDate = fDate.getDate( aLLGrpReportSchema.getCaseNoDate());
		this.CaseEndDate = fDate.getDate( aLLGrpReportSchema.getCaseEndDate());
		this.RgtReason = aLLGrpReportSchema.getRgtReason();
		this.NoRgtReason = aLLGrpReportSchema.getNoRgtReason();
		this.Remark = aLLGrpReportSchema.getRemark();
		this.RgtFlag = aLLGrpReportSchema.getRgtFlag();
		this.AvaiFlag = aLLGrpReportSchema.getAvaiFlag();
		this.AvaliReason = aLLGrpReportSchema.getAvaliReason();
		this.MngCom = aLLGrpReportSchema.getMngCom();
		this.Operator = aLLGrpReportSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLGrpReportSchema.getMakeDate());
		this.MakeTime = aLLGrpReportSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLGrpReportSchema.getModifyDate());
		this.ModifyTime = aLLGrpReportSchema.getModifyTime();
		this.AssigneeType = aLLGrpReportSchema.getAssigneeType();
		this.AssigneeCode = aLLGrpReportSchema.getAssigneeCode();
		this.AssigneeName = aLLGrpReportSchema.getAssigneeName();
		this.AssigneeSex = aLLGrpReportSchema.getAssigneeSex();
		this.AssigneePhone = aLLGrpReportSchema.getAssigneePhone();
		this.AssigneeAddr = aLLGrpReportSchema.getAssigneeAddr();
		this.AssigneeZip = aLLGrpReportSchema.getAssigneeZip();
		this.PerCount = aLLGrpReportSchema.getPerCount();
	}

	/**
	* 使用 ResultSet 中的第 i 行给 Schema 赋值
	* @param: rs ResultSet
	* @param: i int
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// 非滚动游标
			if( rs.getString("RptNo") == null )
				this.RptNo = null;
			else
				this.RptNo = rs.getString("RptNo").trim();

			if( rs.getString("RgtClass") == null )
				this.RgtClass = null;
			else
				this.RgtClass = rs.getString("RgtClass").trim();

			if( rs.getString("RgtObj") == null )
				this.RgtObj = null;
			else
				this.RgtObj = rs.getString("RgtObj").trim();

			if( rs.getString("RgtObjNo") == null )
				this.RgtObjNo = null;
			else
				this.RgtObjNo = rs.getString("RgtObjNo").trim();

			if( rs.getString("RptMode") == null )
				this.RptMode = null;
			else
				this.RptMode = rs.getString("RptMode").trim();

			if( rs.getString("Relation") == null )
				this.Relation = null;
			else
				this.Relation = rs.getString("Relation").trim();

			if( rs.getString("RptorName") == null )
				this.RptorName = null;
			else
				this.RptorName = rs.getString("RptorName").trim();

			if( rs.getString("RptorAddress") == null )
				this.RptorAddress = null;
			else
				this.RptorAddress = rs.getString("RptorAddress").trim();

			if( rs.getString("RptorPhone") == null )
				this.RptorPhone = null;
			else
				this.RptorPhone = rs.getString("RptorPhone").trim();

			if( rs.getString("RptorMobile") == null )
				this.RptorMobile = null;
			else
				this.RptorMobile = rs.getString("RptorMobile").trim();

			if( rs.getString("Email") == null )
				this.Email = null;
			else
				this.Email = rs.getString("Email").trim();

			if( rs.getString("PostCode") == null )
				this.PostCode = null;
			else
				this.PostCode = rs.getString("PostCode").trim();

			if( rs.getString("ReturnMode") == null )
				this.ReturnMode = null;
			else
				this.ReturnMode = rs.getString("ReturnMode").trim();

			this.AccidentDate = rs.getDate("AccidentDate");
			if( rs.getString("AccidentSite") == null )
				this.AccidentSite = null;
			else
				this.AccidentSite = rs.getString("AccidentSite").trim();

			if( rs.getString("AccidentReason") == null )
				this.AccidentReason = null;
			else
				this.AccidentReason = rs.getString("AccidentReason").trim();

			if( rs.getString("AccidentCourse") == null )
				this.AccidentCourse = null;
			else
				this.AccidentCourse = rs.getString("AccidentCourse").trim();

			this.RptDate = rs.getDate("RptDate");
			this.CaseNoDate = rs.getDate("CaseNoDate");
			this.CaseEndDate = rs.getDate("CaseEndDate");
			if( rs.getString("RgtReason") == null )
				this.RgtReason = null;
			else
				this.RgtReason = rs.getString("RgtReason").trim();

			if( rs.getString("NoRgtReason") == null )
				this.NoRgtReason = null;
			else
				this.NoRgtReason = rs.getString("NoRgtReason").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("RgtFlag") == null )
				this.RgtFlag = null;
			else
				this.RgtFlag = rs.getString("RgtFlag").trim();

			if( rs.getString("AvaiFlag") == null )
				this.AvaiFlag = null;
			else
				this.AvaiFlag = rs.getString("AvaiFlag").trim();

			if( rs.getString("AvaliReason") == null )
				this.AvaliReason = null;
			else
				this.AvaliReason = rs.getString("AvaliReason").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("AssigneeType") == null )
				this.AssigneeType = null;
			else
				this.AssigneeType = rs.getString("AssigneeType").trim();

			if( rs.getString("AssigneeCode") == null )
				this.AssigneeCode = null;
			else
				this.AssigneeCode = rs.getString("AssigneeCode").trim();

			if( rs.getString("AssigneeName") == null )
				this.AssigneeName = null;
			else
				this.AssigneeName = rs.getString("AssigneeName").trim();

			if( rs.getString("AssigneeSex") == null )
				this.AssigneeSex = null;
			else
				this.AssigneeSex = rs.getString("AssigneeSex").trim();

			if( rs.getString("AssigneePhone") == null )
				this.AssigneePhone = null;
			else
				this.AssigneePhone = rs.getString("AssigneePhone").trim();

			if( rs.getString("AssigneeAddr") == null )
				this.AssigneeAddr = null;
			else
				this.AssigneeAddr = rs.getString("AssigneeAddr").trim();

			if( rs.getString("AssigneeZip") == null )
				this.AssigneeZip = null;
			else
				this.AssigneeZip = rs.getString("AssigneeZip").trim();

			this.PerCount = rs.getInt("PerCount");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLGrpReport表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpReportSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLGrpReportSchema getSchema()
	{
		LLGrpReportSchema aLLGrpReportSchema = new LLGrpReportSchema();
		aLLGrpReportSchema.setSchema(this);
		return aLLGrpReportSchema;
	}

	public LLGrpReportDB getDB()
	{
		LLGrpReportDB aDBOper = new LLGrpReportDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLGrpReport描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtObj)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtObjNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RptMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Relation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RptorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RptorAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RptorPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RptorMobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Email)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReturnMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccidentDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentSite)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentCourse)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RptDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CaseNoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CaseEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NoRgtReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvaiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvaliReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneePhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeAddr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeZip)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PerCount));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLGrpReport>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RgtClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RgtObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RgtObjNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RptMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Relation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RptorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RptorAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RptorPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RptorMobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Email = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PostCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ReturnMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AccidentDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			AccidentSite = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AccidentReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AccidentCourse = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			RptDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			CaseNoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			CaseEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			RgtReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			NoRgtReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			RgtFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AvaiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AvaliReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			AssigneeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			AssigneeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			AssigneeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			AssigneeSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			AssigneePhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			AssigneeAddr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			AssigneeZip = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			PerCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpReportSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	* 取得对应传入参数的String形式的字段值
	* @param: FCode String 希望取得的字段名
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(String FCode)
	{
		String strReturn = "";
		if (FCode.equalsIgnoreCase("RptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RptNo));
		}
		if (FCode.equalsIgnoreCase("RgtClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtClass));
		}
		if (FCode.equalsIgnoreCase("RgtObj"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtObj));
		}
		if (FCode.equalsIgnoreCase("RgtObjNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtObjNo));
		}
		if (FCode.equalsIgnoreCase("RptMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RptMode));
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Relation));
		}
		if (FCode.equalsIgnoreCase("RptorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RptorName));
		}
		if (FCode.equalsIgnoreCase("RptorAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RptorAddress));
		}
		if (FCode.equalsIgnoreCase("RptorPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RptorPhone));
		}
		if (FCode.equalsIgnoreCase("RptorMobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RptorMobile));
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Email));
		}
		if (FCode.equalsIgnoreCase("PostCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostCode));
		}
		if (FCode.equalsIgnoreCase("ReturnMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnMode));
		}
		if (FCode.equalsIgnoreCase("AccidentDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
		}
		if (FCode.equalsIgnoreCase("AccidentSite"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentSite));
		}
		if (FCode.equalsIgnoreCase("AccidentReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentReason));
		}
		if (FCode.equalsIgnoreCase("AccidentCourse"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentCourse));
		}
		if (FCode.equalsIgnoreCase("RptDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRptDate()));
		}
		if (FCode.equalsIgnoreCase("CaseNoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCaseNoDate()));
		}
		if (FCode.equalsIgnoreCase("CaseEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCaseEndDate()));
		}
		if (FCode.equalsIgnoreCase("RgtReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtReason));
		}
		if (FCode.equalsIgnoreCase("NoRgtReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoRgtReason));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("RgtFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtFlag));
		}
		if (FCode.equalsIgnoreCase("AvaiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvaiFlag));
		}
		if (FCode.equalsIgnoreCase("AvaliReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvaliReason));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("AssigneeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeType));
		}
		if (FCode.equalsIgnoreCase("AssigneeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeCode));
		}
		if (FCode.equalsIgnoreCase("AssigneeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeName));
		}
		if (FCode.equalsIgnoreCase("AssigneeSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeSex));
		}
		if (FCode.equalsIgnoreCase("AssigneePhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneePhone));
		}
		if (FCode.equalsIgnoreCase("AssigneeAddr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeAddr));
		}
		if (FCode.equalsIgnoreCase("AssigneeZip"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeZip));
		}
		if (FCode.equalsIgnoreCase("PerCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PerCount));
		}
		if (strReturn.equals(""))
		{
			strReturn = "null";
		}

		return strReturn;
	}


	/**
	* 取得Schema中指定索引值所对应的字段值
	* @param: nFieldIndex int 指定的字段索引值
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch(nFieldIndex) {
			case 0:
				strFieldValue = StrTool.GBKToUnicode(RptNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RgtClass);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RgtObj);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RgtObjNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RptMode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Relation);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RptorName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RptorAddress);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RptorPhone);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RptorMobile);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Email);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PostCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ReturnMode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AccidentSite);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AccidentReason);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AccidentCourse);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRptDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCaseNoDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCaseEndDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(RgtReason);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(NoRgtReason);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(RgtFlag);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AvaiFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AvaliReason);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(AssigneeType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(AssigneeCode);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(AssigneeName);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(AssigneeSex);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(AssigneePhone);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(AssigneeAddr);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(AssigneeZip);
				break;
			case 39:
				strFieldValue = String.valueOf(PerCount);
				break;
			default:
				strFieldValue = "";
		};
		if( strFieldValue.equals("") ) {
			strFieldValue = "null";
		}
		return strFieldValue;
	}

	/**
	* 设置对应传入参数的String形式的字段值
	* @param: FCode String 需要赋值的对象
	* @param: FValue String 要赋的值
	* @return: boolean
	**/
	public boolean setV(String FCode ,String FValue)
	{
		if( StrTool.cTrim( FCode ).equals( "" ))
			return false;

		if (FCode.equalsIgnoreCase("RptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RptNo = FValue.trim();
			}
			else
				RptNo = null;
		}
		if (FCode.equalsIgnoreCase("RgtClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtClass = FValue.trim();
			}
			else
				RgtClass = null;
		}
		if (FCode.equalsIgnoreCase("RgtObj"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtObj = FValue.trim();
			}
			else
				RgtObj = null;
		}
		if (FCode.equalsIgnoreCase("RgtObjNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtObjNo = FValue.trim();
			}
			else
				RgtObjNo = null;
		}
		if (FCode.equalsIgnoreCase("RptMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RptMode = FValue.trim();
			}
			else
				RptMode = null;
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Relation = FValue.trim();
			}
			else
				Relation = null;
		}
		if (FCode.equalsIgnoreCase("RptorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RptorName = FValue.trim();
			}
			else
				RptorName = null;
		}
		if (FCode.equalsIgnoreCase("RptorAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RptorAddress = FValue.trim();
			}
			else
				RptorAddress = null;
		}
		if (FCode.equalsIgnoreCase("RptorPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RptorPhone = FValue.trim();
			}
			else
				RptorPhone = null;
		}
		if (FCode.equalsIgnoreCase("RptorMobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RptorMobile = FValue.trim();
			}
			else
				RptorMobile = null;
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Email = FValue.trim();
			}
			else
				Email = null;
		}
		if (FCode.equalsIgnoreCase("PostCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PostCode = FValue.trim();
			}
			else
				PostCode = null;
		}
		if (FCode.equalsIgnoreCase("ReturnMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnMode = FValue.trim();
			}
			else
				ReturnMode = null;
		}
		if (FCode.equalsIgnoreCase("AccidentDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccidentDate = fDate.getDate( FValue );
			}
			else
				AccidentDate = null;
		}
		if (FCode.equalsIgnoreCase("AccidentSite"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentSite = FValue.trim();
			}
			else
				AccidentSite = null;
		}
		if (FCode.equalsIgnoreCase("AccidentReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentReason = FValue.trim();
			}
			else
				AccidentReason = null;
		}
		if (FCode.equalsIgnoreCase("AccidentCourse"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentCourse = FValue.trim();
			}
			else
				AccidentCourse = null;
		}
		if (FCode.equalsIgnoreCase("RptDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RptDate = fDate.getDate( FValue );
			}
			else
				RptDate = null;
		}
		if (FCode.equalsIgnoreCase("CaseNoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CaseNoDate = fDate.getDate( FValue );
			}
			else
				CaseNoDate = null;
		}
		if (FCode.equalsIgnoreCase("CaseEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CaseEndDate = fDate.getDate( FValue );
			}
			else
				CaseEndDate = null;
		}
		if (FCode.equalsIgnoreCase("RgtReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtReason = FValue.trim();
			}
			else
				RgtReason = null;
		}
		if (FCode.equalsIgnoreCase("NoRgtReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoRgtReason = FValue.trim();
			}
			else
				NoRgtReason = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("RgtFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtFlag = FValue.trim();
			}
			else
				RgtFlag = null;
		}
		if (FCode.equalsIgnoreCase("AvaiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvaiFlag = FValue.trim();
			}
			else
				AvaiFlag = null;
		}
		if (FCode.equalsIgnoreCase("AvaliReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvaliReason = FValue.trim();
			}
			else
				AvaliReason = null;
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeType = FValue.trim();
			}
			else
				AssigneeType = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeCode = FValue.trim();
			}
			else
				AssigneeCode = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeName = FValue.trim();
			}
			else
				AssigneeName = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeSex = FValue.trim();
			}
			else
				AssigneeSex = null;
		}
		if (FCode.equalsIgnoreCase("AssigneePhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneePhone = FValue.trim();
			}
			else
				AssigneePhone = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeAddr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeAddr = FValue.trim();
			}
			else
				AssigneeAddr = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeZip"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeZip = FValue.trim();
			}
			else
				AssigneeZip = null;
		}
		if (FCode.equalsIgnoreCase("PerCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PerCount = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLGrpReportSchema other = (LLGrpReportSchema)otherObject;
		return
			RptNo.equals(other.getRptNo())
			&& RgtClass.equals(other.getRgtClass())
			&& RgtObj.equals(other.getRgtObj())
			&& RgtObjNo.equals(other.getRgtObjNo())
			&& RptMode.equals(other.getRptMode())
			&& Relation.equals(other.getRelation())
			&& RptorName.equals(other.getRptorName())
			&& RptorAddress.equals(other.getRptorAddress())
			&& RptorPhone.equals(other.getRptorPhone())
			&& RptorMobile.equals(other.getRptorMobile())
			&& Email.equals(other.getEmail())
			&& PostCode.equals(other.getPostCode())
			&& ReturnMode.equals(other.getReturnMode())
			&& fDate.getString(AccidentDate).equals(other.getAccidentDate())
			&& AccidentSite.equals(other.getAccidentSite())
			&& AccidentReason.equals(other.getAccidentReason())
			&& AccidentCourse.equals(other.getAccidentCourse())
			&& fDate.getString(RptDate).equals(other.getRptDate())
			&& fDate.getString(CaseNoDate).equals(other.getCaseNoDate())
			&& fDate.getString(CaseEndDate).equals(other.getCaseEndDate())
			&& RgtReason.equals(other.getRgtReason())
			&& NoRgtReason.equals(other.getNoRgtReason())
			&& Remark.equals(other.getRemark())
			&& RgtFlag.equals(other.getRgtFlag())
			&& AvaiFlag.equals(other.getAvaiFlag())
			&& AvaliReason.equals(other.getAvaliReason())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& AssigneeType.equals(other.getAssigneeType())
			&& AssigneeCode.equals(other.getAssigneeCode())
			&& AssigneeName.equals(other.getAssigneeName())
			&& AssigneeSex.equals(other.getAssigneeSex())
			&& AssigneePhone.equals(other.getAssigneePhone())
			&& AssigneeAddr.equals(other.getAssigneeAddr())
			&& AssigneeZip.equals(other.getAssigneeZip())
			&& PerCount == other.getPerCount();
	}

	/**
	* 取得Schema拥有字段的数量
       * @return: int
	**/
	public int getFieldCount()
	{
 		return FIELDNUM;
	}

	/**
	* 取得Schema中指定字段名所对应的索引值
	* 如果没有对应的字段，返回-1
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldIndex(String strFieldName)
	{
		if( strFieldName.equals("RptNo") ) {
			return 0;
		}
		if( strFieldName.equals("RgtClass") ) {
			return 1;
		}
		if( strFieldName.equals("RgtObj") ) {
			return 2;
		}
		if( strFieldName.equals("RgtObjNo") ) {
			return 3;
		}
		if( strFieldName.equals("RptMode") ) {
			return 4;
		}
		if( strFieldName.equals("Relation") ) {
			return 5;
		}
		if( strFieldName.equals("RptorName") ) {
			return 6;
		}
		if( strFieldName.equals("RptorAddress") ) {
			return 7;
		}
		if( strFieldName.equals("RptorPhone") ) {
			return 8;
		}
		if( strFieldName.equals("RptorMobile") ) {
			return 9;
		}
		if( strFieldName.equals("Email") ) {
			return 10;
		}
		if( strFieldName.equals("PostCode") ) {
			return 11;
		}
		if( strFieldName.equals("ReturnMode") ) {
			return 12;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return 13;
		}
		if( strFieldName.equals("AccidentSite") ) {
			return 14;
		}
		if( strFieldName.equals("AccidentReason") ) {
			return 15;
		}
		if( strFieldName.equals("AccidentCourse") ) {
			return 16;
		}
		if( strFieldName.equals("RptDate") ) {
			return 17;
		}
		if( strFieldName.equals("CaseNoDate") ) {
			return 18;
		}
		if( strFieldName.equals("CaseEndDate") ) {
			return 19;
		}
		if( strFieldName.equals("RgtReason") ) {
			return 20;
		}
		if( strFieldName.equals("NoRgtReason") ) {
			return 21;
		}
		if( strFieldName.equals("Remark") ) {
			return 22;
		}
		if( strFieldName.equals("RgtFlag") ) {
			return 23;
		}
		if( strFieldName.equals("AvaiFlag") ) {
			return 24;
		}
		if( strFieldName.equals("AvaliReason") ) {
			return 25;
		}
		if( strFieldName.equals("MngCom") ) {
			return 26;
		}
		if( strFieldName.equals("Operator") ) {
			return 27;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 28;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 31;
		}
		if( strFieldName.equals("AssigneeType") ) {
			return 32;
		}
		if( strFieldName.equals("AssigneeCode") ) {
			return 33;
		}
		if( strFieldName.equals("AssigneeName") ) {
			return 34;
		}
		if( strFieldName.equals("AssigneeSex") ) {
			return 35;
		}
		if( strFieldName.equals("AssigneePhone") ) {
			return 36;
		}
		if( strFieldName.equals("AssigneeAddr") ) {
			return 37;
		}
		if( strFieldName.equals("AssigneeZip") ) {
			return 38;
		}
		if( strFieldName.equals("PerCount") ) {
			return 39;
		}
		return -1;
	}

	/**
	* 取得Schema中指定索引值所对应的字段名
	* 如果没有对应的字段，返回""
       * @param: nFieldIndex int
       * @return: String
	**/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch(nFieldIndex) {
			case 0:
				strFieldName = "RptNo";
				break;
			case 1:
				strFieldName = "RgtClass";
				break;
			case 2:
				strFieldName = "RgtObj";
				break;
			case 3:
				strFieldName = "RgtObjNo";
				break;
			case 4:
				strFieldName = "RptMode";
				break;
			case 5:
				strFieldName = "Relation";
				break;
			case 6:
				strFieldName = "RptorName";
				break;
			case 7:
				strFieldName = "RptorAddress";
				break;
			case 8:
				strFieldName = "RptorPhone";
				break;
			case 9:
				strFieldName = "RptorMobile";
				break;
			case 10:
				strFieldName = "Email";
				break;
			case 11:
				strFieldName = "PostCode";
				break;
			case 12:
				strFieldName = "ReturnMode";
				break;
			case 13:
				strFieldName = "AccidentDate";
				break;
			case 14:
				strFieldName = "AccidentSite";
				break;
			case 15:
				strFieldName = "AccidentReason";
				break;
			case 16:
				strFieldName = "AccidentCourse";
				break;
			case 17:
				strFieldName = "RptDate";
				break;
			case 18:
				strFieldName = "CaseNoDate";
				break;
			case 19:
				strFieldName = "CaseEndDate";
				break;
			case 20:
				strFieldName = "RgtReason";
				break;
			case 21:
				strFieldName = "NoRgtReason";
				break;
			case 22:
				strFieldName = "Remark";
				break;
			case 23:
				strFieldName = "RgtFlag";
				break;
			case 24:
				strFieldName = "AvaiFlag";
				break;
			case 25:
				strFieldName = "AvaliReason";
				break;
			case 26:
				strFieldName = "MngCom";
				break;
			case 27:
				strFieldName = "Operator";
				break;
			case 28:
				strFieldName = "MakeDate";
				break;
			case 29:
				strFieldName = "MakeTime";
				break;
			case 30:
				strFieldName = "ModifyDate";
				break;
			case 31:
				strFieldName = "ModifyTime";
				break;
			case 32:
				strFieldName = "AssigneeType";
				break;
			case 33:
				strFieldName = "AssigneeCode";
				break;
			case 34:
				strFieldName = "AssigneeName";
				break;
			case 35:
				strFieldName = "AssigneeSex";
				break;
			case 36:
				strFieldName = "AssigneePhone";
				break;
			case 37:
				strFieldName = "AssigneeAddr";
				break;
			case 38:
				strFieldName = "AssigneeZip";
				break;
			case 39:
				strFieldName = "PerCount";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	* 取得Schema中指定字段名所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldType(String strFieldName)
	{
		if( strFieldName.equals("RptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtObj") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtObjNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RptMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Relation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RptorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RptorAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RptorPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RptorMobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Email") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReturnMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccidentSite") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentCourse") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RptDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CaseNoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CaseEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RgtReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NoRgtReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvaiFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvaliReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneePhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeAddr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeZip") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PerCount") ) {
			return Schema.TYPE_INT;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	* 取得Schema中指定索引值所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
       * @param: nFieldIndex int
       * @return: int
	**/
	public int getFieldType(int nFieldIndex)
	{
		int nFieldType = Schema.TYPE_NOFOUND;
		switch(nFieldIndex) {
			case 0:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
