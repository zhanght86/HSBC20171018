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
import com.sinosoft.lis.db.LCSubmitApplyDB;

/*
 * <p>ClassName: LCSubmitApplySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LCSubmitApplySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCSubmitApplySchema.class);
	// @Field
	/** 号码 */
	private String GrpContNo;
	/** 呈报序号 */
	private String SubNo;
	/** 呈报主题 */
	private String Title;
	/** 呈报次数 */
	private int SubCount;
	/** 客户号 */
	private String CustomerNo;
	/** 客户 */
	private String CustomerName;
	/** 紧急程度 */
	private String InitPhase;
	/** 呈报类型 */
	private String SubType;
	/** 呈报原因 */
	private String Reason;
	/** 呈报描述 */
	private String SubDesc;
	/** 解决方法 */
	private String DealWay;
	/** 呈报人 */
	private String SubPer;
	/** 呈报日期 */
	private Date SubDate;
	/** 呈报机构 */
	private String ManageCom;
	/** 呈报状态 */
	private String SubState;
	/** 承接机构代码 */
	private String DispDept;
	/** 承接人员编号 */
	private String DispPer;
	/** 处理日期 */
	private Date DispDate;
	/** 处理类型 */
	private String DispType;
	/** 处理意见 */
	private String DispIdea;
	/** 操作员 */
	private String Operator;
	/** 处理完毕标志 */
	private String HaveDealed;
	/** 联系方式 */
	private String Communicate;
	/** 附件1 */
	private String AppText1;
	/** 附件2 */
	private String AppText2;
	/** 附件3 */
	private String AppText3;
	/** 到达日期 */
	private Date ArriveDate;
	/** 到达时间 */
	private String ArriveTime;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 32;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCSubmitApplySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SubNo";

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
		LCSubmitApplySchema cloned = (LCSubmitApplySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getSubNo()
	{
		return SubNo;
	}
	public void setSubNo(String aSubNo)
	{
		if(aSubNo!=null && aSubNo.length()>20)
			throw new IllegalArgumentException("呈报序号SubNo值"+aSubNo+"的长度"+aSubNo.length()+"大于最大值20");
		SubNo = aSubNo;
	}
	public String getTitle()
	{
		return Title;
	}
	public void setTitle(String aTitle)
	{
		if(aTitle!=null && aTitle.length()>200)
			throw new IllegalArgumentException("呈报主题Title值"+aTitle+"的长度"+aTitle.length()+"大于最大值200");
		Title = aTitle;
	}
	public int getSubCount()
	{
		return SubCount;
	}
	public void setSubCount(int aSubCount)
	{
		SubCount = aSubCount;
	}
	public void setSubCount(String aSubCount)
	{
		if (aSubCount != null && !aSubCount.equals(""))
		{
			Integer tInteger = new Integer(aSubCount);
			int i = tInteger.intValue();
			SubCount = i;
		}
	}

	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>20)
			throw new IllegalArgumentException("客户CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值20");
		CustomerName = aCustomerName;
	}
	/**
	* 报案 审核
	*/
	public String getInitPhase()
	{
		return InitPhase;
	}
	public void setInitPhase(String aInitPhase)
	{
		if(aInitPhase!=null && aInitPhase.length()>6)
			throw new IllegalArgumentException("紧急程度InitPhase值"+aInitPhase+"的长度"+aInitPhase.length()+"大于最大值6");
		InitPhase = aInitPhase;
	}
	/**
	* 0中心到分公司 1分公司到总公司 2总公司到分公司
	*/
	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		if(aSubType!=null && aSubType.length()>20)
			throw new IllegalArgumentException("呈报类型SubType值"+aSubType+"的长度"+aSubType.length()+"大于最大值20");
		SubType = aSubType;
	}
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		if(aReason!=null && aReason.length()>6)
			throw new IllegalArgumentException("呈报原因Reason值"+aReason+"的长度"+aReason.length()+"大于最大值6");
		Reason = aReason;
	}
	/**
	* 是否是本地调查
	*/
	public String getSubDesc()
	{
		return SubDesc;
	}
	public void setSubDesc(String aSubDesc)
	{
		if(aSubDesc!=null && aSubDesc.length()>2000)
			throw new IllegalArgumentException("呈报描述SubDesc值"+aSubDesc+"的长度"+aSubDesc.length()+"大于最大值2000");
		SubDesc = aSubDesc;
	}
	public String getDealWay()
	{
		return DealWay;
	}
	public void setDealWay(String aDealWay)
	{
		if(aDealWay!=null && aDealWay.length()>2000)
			throw new IllegalArgumentException("解决方法DealWay值"+aDealWay+"的长度"+aDealWay.length()+"大于最大值2000");
		DealWay = aDealWay;
	}
	public String getSubPer()
	{
		return SubPer;
	}
	public void setSubPer(String aSubPer)
	{
		if(aSubPer!=null && aSubPer.length()>20)
			throw new IllegalArgumentException("呈报人SubPer值"+aSubPer+"的长度"+aSubPer.length()+"大于最大值20");
		SubPer = aSubPer;
	}
	public String getSubDate()
	{
		if( SubDate != null )
			return fDate.getString(SubDate);
		else
			return null;
	}
	public void setSubDate(Date aSubDate)
	{
		SubDate = aSubDate;
	}
	public void setSubDate(String aSubDate)
	{
		if (aSubDate != null && !aSubDate.equals("") )
		{
			SubDate = fDate.getDate( aSubDate );
		}
		else
			SubDate = null;
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("呈报机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	/**
	* 申请 完成
	*/
	public String getSubState()
	{
		return SubState;
	}
	public void setSubState(String aSubState)
	{
		if(aSubState!=null && aSubState.length()>6)
			throw new IllegalArgumentException("呈报状态SubState值"+aSubState+"的长度"+aSubState.length()+"大于最大值6");
		SubState = aSubState;
	}
	public String getDispDept()
	{
		return DispDept;
	}
	public void setDispDept(String aDispDept)
	{
		if(aDispDept!=null && aDispDept.length()>20)
			throw new IllegalArgumentException("承接机构代码DispDept值"+aDispDept+"的长度"+aDispDept.length()+"大于最大值20");
		DispDept = aDispDept;
	}
	public String getDispPer()
	{
		return DispPer;
	}
	public void setDispPer(String aDispPer)
	{
		if(aDispPer!=null && aDispPer.length()>20)
			throw new IllegalArgumentException("承接人员编号DispPer值"+aDispPer+"的长度"+aDispPer.length()+"大于最大值20");
		DispPer = aDispPer;
	}
	public String getDispDate()
	{
		if( DispDate != null )
			return fDate.getString(DispDate);
		else
			return null;
	}
	public void setDispDate(Date aDispDate)
	{
		DispDate = aDispDate;
	}
	public void setDispDate(String aDispDate)
	{
		if (aDispDate != null && !aDispDate.equals("") )
		{
			DispDate = fDate.getDate( aDispDate );
		}
		else
			DispDate = null;
	}

	/**
	* 2给出呈报处理意见 0提起调查 1呈报总公司
	*/
	public String getDispType()
	{
		return DispType;
	}
	public void setDispType(String aDispType)
	{
		if(aDispType!=null && aDispType.length()>6)
			throw new IllegalArgumentException("处理类型DispType值"+aDispType+"的长度"+aDispType.length()+"大于最大值6");
		DispType = aDispType;
	}
	public String getDispIdea()
	{
		return DispIdea;
	}
	public void setDispIdea(String aDispIdea)
	{
		if(aDispIdea!=null && aDispIdea.length()>2000)
			throw new IllegalArgumentException("处理意见DispIdea值"+aDispIdea+"的长度"+aDispIdea.length()+"大于最大值2000");
		DispIdea = aDispIdea;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>20)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值20");
		Operator = aOperator;
	}
	/**
	* 2:处理完毕 1:有人批复 0：申请
	*/
	public String getHaveDealed()
	{
		return HaveDealed;
	}
	public void setHaveDealed(String aHaveDealed)
	{
		if(aHaveDealed!=null && aHaveDealed.length()>1)
			throw new IllegalArgumentException("处理完毕标志HaveDealed值"+aHaveDealed+"的长度"+aHaveDealed.length()+"大于最大值1");
		HaveDealed = aHaveDealed;
	}
	public String getCommunicate()
	{
		return Communicate;
	}
	public void setCommunicate(String aCommunicate)
	{
		if(aCommunicate!=null && aCommunicate.length()>200)
			throw new IllegalArgumentException("联系方式Communicate值"+aCommunicate+"的长度"+aCommunicate.length()+"大于最大值200");
		Communicate = aCommunicate;
	}
	public String getAppText1()
	{
		return AppText1;
	}
	public void setAppText1(String aAppText1)
	{
		if(aAppText1!=null && aAppText1.length()>1000)
			throw new IllegalArgumentException("附件1AppText1值"+aAppText1+"的长度"+aAppText1.length()+"大于最大值1000");
		AppText1 = aAppText1;
	}
	public String getAppText2()
	{
		return AppText2;
	}
	public void setAppText2(String aAppText2)
	{
		if(aAppText2!=null && aAppText2.length()>1000)
			throw new IllegalArgumentException("附件2AppText2值"+aAppText2+"的长度"+aAppText2.length()+"大于最大值1000");
		AppText2 = aAppText2;
	}
	public String getAppText3()
	{
		return AppText3;
	}
	public void setAppText3(String aAppText3)
	{
		if(aAppText3!=null && aAppText3.length()>1000)
			throw new IllegalArgumentException("附件3AppText3值"+aAppText3+"的长度"+aAppText3.length()+"大于最大值1000");
		AppText3 = aAppText3;
	}
	public String getArriveDate()
	{
		if( ArriveDate != null )
			return fDate.getString(ArriveDate);
		else
			return null;
	}
	public void setArriveDate(Date aArriveDate)
	{
		ArriveDate = aArriveDate;
	}
	public void setArriveDate(String aArriveDate)
	{
		if (aArriveDate != null && !aArriveDate.equals("") )
		{
			ArriveDate = fDate.getDate( aArriveDate );
		}
		else
			ArriveDate = null;
	}

	public String getArriveTime()
	{
		return ArriveTime;
	}
	public void setArriveTime(String aArriveTime)
	{
		if(aArriveTime!=null && aArriveTime.length()>8)
			throw new IllegalArgumentException("到达时间ArriveTime值"+aArriveTime+"的长度"+aArriveTime.length()+"大于最大值8");
		ArriveTime = aArriveTime;
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LCSubmitApplySchema 对象给 Schema 赋值
	* @param: aLCSubmitApplySchema LCSubmitApplySchema
	**/
	public void setSchema(LCSubmitApplySchema aLCSubmitApplySchema)
	{
		this.GrpContNo = aLCSubmitApplySchema.getGrpContNo();
		this.SubNo = aLCSubmitApplySchema.getSubNo();
		this.Title = aLCSubmitApplySchema.getTitle();
		this.SubCount = aLCSubmitApplySchema.getSubCount();
		this.CustomerNo = aLCSubmitApplySchema.getCustomerNo();
		this.CustomerName = aLCSubmitApplySchema.getCustomerName();
		this.InitPhase = aLCSubmitApplySchema.getInitPhase();
		this.SubType = aLCSubmitApplySchema.getSubType();
		this.Reason = aLCSubmitApplySchema.getReason();
		this.SubDesc = aLCSubmitApplySchema.getSubDesc();
		this.DealWay = aLCSubmitApplySchema.getDealWay();
		this.SubPer = aLCSubmitApplySchema.getSubPer();
		this.SubDate = fDate.getDate( aLCSubmitApplySchema.getSubDate());
		this.ManageCom = aLCSubmitApplySchema.getManageCom();
		this.SubState = aLCSubmitApplySchema.getSubState();
		this.DispDept = aLCSubmitApplySchema.getDispDept();
		this.DispPer = aLCSubmitApplySchema.getDispPer();
		this.DispDate = fDate.getDate( aLCSubmitApplySchema.getDispDate());
		this.DispType = aLCSubmitApplySchema.getDispType();
		this.DispIdea = aLCSubmitApplySchema.getDispIdea();
		this.Operator = aLCSubmitApplySchema.getOperator();
		this.HaveDealed = aLCSubmitApplySchema.getHaveDealed();
		this.Communicate = aLCSubmitApplySchema.getCommunicate();
		this.AppText1 = aLCSubmitApplySchema.getAppText1();
		this.AppText2 = aLCSubmitApplySchema.getAppText2();
		this.AppText3 = aLCSubmitApplySchema.getAppText3();
		this.ArriveDate = fDate.getDate( aLCSubmitApplySchema.getArriveDate());
		this.ArriveTime = aLCSubmitApplySchema.getArriveTime();
		this.MakeDate = fDate.getDate( aLCSubmitApplySchema.getMakeDate());
		this.MakeTime = aLCSubmitApplySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCSubmitApplySchema.getModifyDate());
		this.ModifyTime = aLCSubmitApplySchema.getModifyTime();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("SubNo") == null )
				this.SubNo = null;
			else
				this.SubNo = rs.getString("SubNo").trim();

			if( rs.getString("Title") == null )
				this.Title = null;
			else
				this.Title = rs.getString("Title").trim();

			this.SubCount = rs.getInt("SubCount");
			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("InitPhase") == null )
				this.InitPhase = null;
			else
				this.InitPhase = rs.getString("InitPhase").trim();

			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("SubDesc") == null )
				this.SubDesc = null;
			else
				this.SubDesc = rs.getString("SubDesc").trim();

			if( rs.getString("DealWay") == null )
				this.DealWay = null;
			else
				this.DealWay = rs.getString("DealWay").trim();

			if( rs.getString("SubPer") == null )
				this.SubPer = null;
			else
				this.SubPer = rs.getString("SubPer").trim();

			this.SubDate = rs.getDate("SubDate");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("SubState") == null )
				this.SubState = null;
			else
				this.SubState = rs.getString("SubState").trim();

			if( rs.getString("DispDept") == null )
				this.DispDept = null;
			else
				this.DispDept = rs.getString("DispDept").trim();

			if( rs.getString("DispPer") == null )
				this.DispPer = null;
			else
				this.DispPer = rs.getString("DispPer").trim();

			this.DispDate = rs.getDate("DispDate");
			if( rs.getString("DispType") == null )
				this.DispType = null;
			else
				this.DispType = rs.getString("DispType").trim();

			if( rs.getString("DispIdea") == null )
				this.DispIdea = null;
			else
				this.DispIdea = rs.getString("DispIdea").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("HaveDealed") == null )
				this.HaveDealed = null;
			else
				this.HaveDealed = rs.getString("HaveDealed").trim();

			if( rs.getString("Communicate") == null )
				this.Communicate = null;
			else
				this.Communicate = rs.getString("Communicate").trim();

			if( rs.getString("AppText1") == null )
				this.AppText1 = null;
			else
				this.AppText1 = rs.getString("AppText1").trim();

			if( rs.getString("AppText2") == null )
				this.AppText2 = null;
			else
				this.AppText2 = rs.getString("AppText2").trim();

			if( rs.getString("AppText3") == null )
				this.AppText3 = null;
			else
				this.AppText3 = rs.getString("AppText3").trim();

			this.ArriveDate = rs.getDate("ArriveDate");
			if( rs.getString("ArriveTime") == null )
				this.ArriveTime = null;
			else
				this.ArriveTime = rs.getString("ArriveTime").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCSubmitApply表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCSubmitApplySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCSubmitApplySchema getSchema()
	{
		LCSubmitApplySchema aLCSubmitApplySchema = new LCSubmitApplySchema();
		aLCSubmitApplySchema.setSchema(this);
		return aLCSubmitApplySchema;
	}

	public LCSubmitApplyDB getDB()
	{
		LCSubmitApplyDB aDBOper = new LCSubmitApplyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCSubmitApply描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Title)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SubCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InitPhase)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealWay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SubDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DispDept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DispPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DispDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DispType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DispIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HaveDealed)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Communicate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppText1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppText2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppText3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ArriveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArriveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCSubmitApply>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SubNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Title = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SubCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InitPhase = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SubDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DealWay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SubPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			SubDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			SubState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			DispDept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			DispPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			DispDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			DispType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			DispIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			HaveDealed = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Communicate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AppText1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AppText2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AppText3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ArriveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			ArriveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCSubmitApplySchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("SubNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubNo));
		}
		if (FCode.equalsIgnoreCase("Title"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Title));
		}
		if (FCode.equalsIgnoreCase("SubCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubCount));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("InitPhase"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitPhase));
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("SubDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubDesc));
		}
		if (FCode.equalsIgnoreCase("DealWay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealWay));
		}
		if (FCode.equalsIgnoreCase("SubPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubPer));
		}
		if (FCode.equalsIgnoreCase("SubDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSubDate()));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("SubState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubState));
		}
		if (FCode.equalsIgnoreCase("DispDept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DispDept));
		}
		if (FCode.equalsIgnoreCase("DispPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DispPer));
		}
		if (FCode.equalsIgnoreCase("DispDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDispDate()));
		}
		if (FCode.equalsIgnoreCase("DispType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DispType));
		}
		if (FCode.equalsIgnoreCase("DispIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DispIdea));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("HaveDealed"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HaveDealed));
		}
		if (FCode.equalsIgnoreCase("Communicate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Communicate));
		}
		if (FCode.equalsIgnoreCase("AppText1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppText1));
		}
		if (FCode.equalsIgnoreCase("AppText2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppText2));
		}
		if (FCode.equalsIgnoreCase("AppText3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppText3));
		}
		if (FCode.equalsIgnoreCase("ArriveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getArriveDate()));
		}
		if (FCode.equalsIgnoreCase("ArriveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArriveTime));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SubNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Title);
				break;
			case 3:
				strFieldValue = String.valueOf(SubCount);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InitPhase);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SubDesc);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DealWay);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(SubPer);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSubDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(SubState);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(DispDept);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(DispPer);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDispDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(DispType);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(DispIdea);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(HaveDealed);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Communicate);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AppText1);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AppText2);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AppText3);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getArriveDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ArriveTime);
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("SubNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubNo = FValue.trim();
			}
			else
				SubNo = null;
		}
		if (FCode.equalsIgnoreCase("Title"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Title = FValue.trim();
			}
			else
				Title = null;
		}
		if (FCode.equalsIgnoreCase("SubCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SubCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerName = FValue.trim();
			}
			else
				CustomerName = null;
		}
		if (FCode.equalsIgnoreCase("InitPhase"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InitPhase = FValue.trim();
			}
			else
				InitPhase = null;
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubType = FValue.trim();
			}
			else
				SubType = null;
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
		}
		if (FCode.equalsIgnoreCase("SubDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubDesc = FValue.trim();
			}
			else
				SubDesc = null;
		}
		if (FCode.equalsIgnoreCase("DealWay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealWay = FValue.trim();
			}
			else
				DealWay = null;
		}
		if (FCode.equalsIgnoreCase("SubPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubPer = FValue.trim();
			}
			else
				SubPer = null;
		}
		if (FCode.equalsIgnoreCase("SubDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SubDate = fDate.getDate( FValue );
			}
			else
				SubDate = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("SubState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubState = FValue.trim();
			}
			else
				SubState = null;
		}
		if (FCode.equalsIgnoreCase("DispDept"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DispDept = FValue.trim();
			}
			else
				DispDept = null;
		}
		if (FCode.equalsIgnoreCase("DispPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DispPer = FValue.trim();
			}
			else
				DispPer = null;
		}
		if (FCode.equalsIgnoreCase("DispDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DispDate = fDate.getDate( FValue );
			}
			else
				DispDate = null;
		}
		if (FCode.equalsIgnoreCase("DispType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DispType = FValue.trim();
			}
			else
				DispType = null;
		}
		if (FCode.equalsIgnoreCase("DispIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DispIdea = FValue.trim();
			}
			else
				DispIdea = null;
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
		if (FCode.equalsIgnoreCase("HaveDealed"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HaveDealed = FValue.trim();
			}
			else
				HaveDealed = null;
		}
		if (FCode.equalsIgnoreCase("Communicate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Communicate = FValue.trim();
			}
			else
				Communicate = null;
		}
		if (FCode.equalsIgnoreCase("AppText1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppText1 = FValue.trim();
			}
			else
				AppText1 = null;
		}
		if (FCode.equalsIgnoreCase("AppText2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppText2 = FValue.trim();
			}
			else
				AppText2 = null;
		}
		if (FCode.equalsIgnoreCase("AppText3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppText3 = FValue.trim();
			}
			else
				AppText3 = null;
		}
		if (FCode.equalsIgnoreCase("ArriveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ArriveDate = fDate.getDate( FValue );
			}
			else
				ArriveDate = null;
		}
		if (FCode.equalsIgnoreCase("ArriveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArriveTime = FValue.trim();
			}
			else
				ArriveTime = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCSubmitApplySchema other = (LCSubmitApplySchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& SubNo.equals(other.getSubNo())
			&& Title.equals(other.getTitle())
			&& SubCount == other.getSubCount()
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& InitPhase.equals(other.getInitPhase())
			&& SubType.equals(other.getSubType())
			&& Reason.equals(other.getReason())
			&& SubDesc.equals(other.getSubDesc())
			&& DealWay.equals(other.getDealWay())
			&& SubPer.equals(other.getSubPer())
			&& fDate.getString(SubDate).equals(other.getSubDate())
			&& ManageCom.equals(other.getManageCom())
			&& SubState.equals(other.getSubState())
			&& DispDept.equals(other.getDispDept())
			&& DispPer.equals(other.getDispPer())
			&& fDate.getString(DispDate).equals(other.getDispDate())
			&& DispType.equals(other.getDispType())
			&& DispIdea.equals(other.getDispIdea())
			&& Operator.equals(other.getOperator())
			&& HaveDealed.equals(other.getHaveDealed())
			&& Communicate.equals(other.getCommunicate())
			&& AppText1.equals(other.getAppText1())
			&& AppText2.equals(other.getAppText2())
			&& AppText3.equals(other.getAppText3())
			&& fDate.getString(ArriveDate).equals(other.getArriveDate())
			&& ArriveTime.equals(other.getArriveTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("SubNo") ) {
			return 1;
		}
		if( strFieldName.equals("Title") ) {
			return 2;
		}
		if( strFieldName.equals("SubCount") ) {
			return 3;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 4;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 5;
		}
		if( strFieldName.equals("InitPhase") ) {
			return 6;
		}
		if( strFieldName.equals("SubType") ) {
			return 7;
		}
		if( strFieldName.equals("Reason") ) {
			return 8;
		}
		if( strFieldName.equals("SubDesc") ) {
			return 9;
		}
		if( strFieldName.equals("DealWay") ) {
			return 10;
		}
		if( strFieldName.equals("SubPer") ) {
			return 11;
		}
		if( strFieldName.equals("SubDate") ) {
			return 12;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 13;
		}
		if( strFieldName.equals("SubState") ) {
			return 14;
		}
		if( strFieldName.equals("DispDept") ) {
			return 15;
		}
		if( strFieldName.equals("DispPer") ) {
			return 16;
		}
		if( strFieldName.equals("DispDate") ) {
			return 17;
		}
		if( strFieldName.equals("DispType") ) {
			return 18;
		}
		if( strFieldName.equals("DispIdea") ) {
			return 19;
		}
		if( strFieldName.equals("Operator") ) {
			return 20;
		}
		if( strFieldName.equals("HaveDealed") ) {
			return 21;
		}
		if( strFieldName.equals("Communicate") ) {
			return 22;
		}
		if( strFieldName.equals("AppText1") ) {
			return 23;
		}
		if( strFieldName.equals("AppText2") ) {
			return 24;
		}
		if( strFieldName.equals("AppText3") ) {
			return 25;
		}
		if( strFieldName.equals("ArriveDate") ) {
			return 26;
		}
		if( strFieldName.equals("ArriveTime") ) {
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "SubNo";
				break;
			case 2:
				strFieldName = "Title";
				break;
			case 3:
				strFieldName = "SubCount";
				break;
			case 4:
				strFieldName = "CustomerNo";
				break;
			case 5:
				strFieldName = "CustomerName";
				break;
			case 6:
				strFieldName = "InitPhase";
				break;
			case 7:
				strFieldName = "SubType";
				break;
			case 8:
				strFieldName = "Reason";
				break;
			case 9:
				strFieldName = "SubDesc";
				break;
			case 10:
				strFieldName = "DealWay";
				break;
			case 11:
				strFieldName = "SubPer";
				break;
			case 12:
				strFieldName = "SubDate";
				break;
			case 13:
				strFieldName = "ManageCom";
				break;
			case 14:
				strFieldName = "SubState";
				break;
			case 15:
				strFieldName = "DispDept";
				break;
			case 16:
				strFieldName = "DispPer";
				break;
			case 17:
				strFieldName = "DispDate";
				break;
			case 18:
				strFieldName = "DispType";
				break;
			case 19:
				strFieldName = "DispIdea";
				break;
			case 20:
				strFieldName = "Operator";
				break;
			case 21:
				strFieldName = "HaveDealed";
				break;
			case 22:
				strFieldName = "Communicate";
				break;
			case 23:
				strFieldName = "AppText1";
				break;
			case 24:
				strFieldName = "AppText2";
				break;
			case 25:
				strFieldName = "AppText3";
				break;
			case 26:
				strFieldName = "ArriveDate";
				break;
			case 27:
				strFieldName = "ArriveTime";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Title") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InitPhase") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealWay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DispDept") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DispPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DispDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DispType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DispIdea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HaveDealed") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Communicate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppText1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppText2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppText3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ArriveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ArriveTime") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
