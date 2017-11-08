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
import com.sinosoft.lis.db.LBPOInsuredBnfListDB;

/*
 * <p>ClassName: LBPOInsuredBnfListSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LBPOInsuredBnfListSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPOInsuredBnfListSchema.class);
	// @Field
	/** 流水号 */
	private int SerialNo;
	/** 投保书号 */
	private String GrpPropNo;
	/** 操作节点 */
	private String ActivityID;
	/** 批次号 */
	private String BatchNo;
	/** 顺序号 */
	private int OrderNo;
	/** 被保险人id */
	private String InsuredID;
	/** 受益人类别 */
	private String BnfType;
	/** 受益人类别名称 */
	private String BnfTypeName;
	/** 受益人顺序 */
	private String BnfOrder;
	/** 受益人顺序名称 */
	private String BnfOrderName;
	/** 姓名 */
	private String BnfName;
	/** 证件类型 */
	private String IDType;
	/** 证件类型名称 */
	private String IDTypeName;
	/** 证件号码 */
	private String IDNo;
	/** 性别 */
	private String Gender;
	/** 性别名称 */
	private String GenderName;
	/** 出生日期 */
	private String Birthday;
	/** 与被保险人关系 */
	private String RelationToInsured;
	/** 与被保险人关系名称 */
	private String RelationToInsuredName;
	/** 受益比例 */
	private String BnfLot;
	/** 受益人级别 */
	private String BnfGrade;
	/** 状态 */
	private String State;
	/** 原因 */
	private String Reason;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 29;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPOInsuredBnfListSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		LBPOInsuredBnfListSchema cloned = (LBPOInsuredBnfListSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
	}

	public String getGrpPropNo()
	{
		return GrpPropNo;
	}
	public void setGrpPropNo(String aGrpPropNo)
	{
		if(aGrpPropNo!=null && aGrpPropNo.length()>20)
			throw new IllegalArgumentException("投保书号GrpPropNo值"+aGrpPropNo+"的长度"+aGrpPropNo.length()+"大于最大值20");
		GrpPropNo = aGrpPropNo;
	}
	public String getActivityID()
	{
		return ActivityID;
	}
	public void setActivityID(String aActivityID)
	{
		if(aActivityID!=null && aActivityID.length()>10)
			throw new IllegalArgumentException("操作节点ActivityID值"+aActivityID+"的长度"+aActivityID.length()+"大于最大值10");
		ActivityID = aActivityID;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		if(aBatchNo!=null && aBatchNo.length()>30)
			throw new IllegalArgumentException("批次号BatchNo值"+aBatchNo+"的长度"+aBatchNo.length()+"大于最大值30");
		BatchNo = aBatchNo;
	}
	public int getOrderNo()
	{
		return OrderNo;
	}
	public void setOrderNo(int aOrderNo)
	{
		OrderNo = aOrderNo;
	}
	public void setOrderNo(String aOrderNo)
	{
		if (aOrderNo != null && !aOrderNo.equals(""))
		{
			Integer tInteger = new Integer(aOrderNo);
			int i = tInteger.intValue();
			OrderNo = i;
		}
	}

	public String getInsuredID()
	{
		return InsuredID;
	}
	public void setInsuredID(String aInsuredID)
	{
		if(aInsuredID!=null && aInsuredID.length()>10)
			throw new IllegalArgumentException("被保险人idInsuredID值"+aInsuredID+"的长度"+aInsuredID.length()+"大于最大值10");
		InsuredID = aInsuredID;
	}
	public String getBnfType()
	{
		return BnfType;
	}
	public void setBnfType(String aBnfType)
	{
		if(aBnfType!=null && aBnfType.length()>2)
			throw new IllegalArgumentException("受益人类别BnfType值"+aBnfType+"的长度"+aBnfType.length()+"大于最大值2");
		BnfType = aBnfType;
	}
	public String getBnfTypeName()
	{
		return BnfTypeName;
	}
	public void setBnfTypeName(String aBnfTypeName)
	{
		if(aBnfTypeName!=null && aBnfTypeName.length()>30)
			throw new IllegalArgumentException("受益人类别名称BnfTypeName值"+aBnfTypeName+"的长度"+aBnfTypeName.length()+"大于最大值30");
		BnfTypeName = aBnfTypeName;
	}
	public String getBnfOrder()
	{
		return BnfOrder;
	}
	public void setBnfOrder(String aBnfOrder)
	{
		if(aBnfOrder!=null && aBnfOrder.length()>2)
			throw new IllegalArgumentException("受益人顺序BnfOrder值"+aBnfOrder+"的长度"+aBnfOrder.length()+"大于最大值2");
		BnfOrder = aBnfOrder;
	}
	public String getBnfOrderName()
	{
		return BnfOrderName;
	}
	public void setBnfOrderName(String aBnfOrderName)
	{
		if(aBnfOrderName!=null && aBnfOrderName.length()>30)
			throw new IllegalArgumentException("受益人顺序名称BnfOrderName值"+aBnfOrderName+"的长度"+aBnfOrderName.length()+"大于最大值30");
		BnfOrderName = aBnfOrderName;
	}
	public String getBnfName()
	{
		return BnfName;
	}
	public void setBnfName(String aBnfName)
	{
		if(aBnfName!=null && aBnfName.length()>300)
			throw new IllegalArgumentException("姓名BnfName值"+aBnfName+"的长度"+aBnfName.length()+"大于最大值300");
		BnfName = aBnfName;
	}
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		if(aIDType!=null && aIDType.length()>2)
			throw new IllegalArgumentException("证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值2");
		IDType = aIDType;
	}
	public String getIDTypeName()
	{
		return IDTypeName;
	}
	public void setIDTypeName(String aIDTypeName)
	{
		if(aIDTypeName!=null && aIDTypeName.length()>30)
			throw new IllegalArgumentException("证件类型名称IDTypeName值"+aIDTypeName+"的长度"+aIDTypeName.length()+"大于最大值30");
		IDTypeName = aIDTypeName;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		if(aIDNo!=null && aIDNo.length()>30)
			throw new IllegalArgumentException("证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值30");
		IDNo = aIDNo;
	}
	public String getGender()
	{
		return Gender;
	}
	public void setGender(String aGender)
	{
		if(aGender!=null && aGender.length()>1)
			throw new IllegalArgumentException("性别Gender值"+aGender+"的长度"+aGender.length()+"大于最大值1");
		Gender = aGender;
	}
	public String getGenderName()
	{
		return GenderName;
	}
	public void setGenderName(String aGenderName)
	{
		if(aGenderName!=null && aGenderName.length()>30)
			throw new IllegalArgumentException("性别名称GenderName值"+aGenderName+"的长度"+aGenderName.length()+"大于最大值30");
		GenderName = aGenderName;
	}
	public String getBirthday()
	{
		return Birthday;
	}
	public void setBirthday(String aBirthday)
	{
		if(aBirthday!=null && aBirthday.length()>30)
			throw new IllegalArgumentException("出生日期Birthday值"+aBirthday+"的长度"+aBirthday.length()+"大于最大值30");
		Birthday = aBirthday;
	}
	public String getRelationToInsured()
	{
		return RelationToInsured;
	}
	public void setRelationToInsured(String aRelationToInsured)
	{
		if(aRelationToInsured!=null && aRelationToInsured.length()>2)
			throw new IllegalArgumentException("与被保险人关系RelationToInsured值"+aRelationToInsured+"的长度"+aRelationToInsured.length()+"大于最大值2");
		RelationToInsured = aRelationToInsured;
	}
	public String getRelationToInsuredName()
	{
		return RelationToInsuredName;
	}
	public void setRelationToInsuredName(String aRelationToInsuredName)
	{
		if(aRelationToInsuredName!=null && aRelationToInsuredName.length()>30)
			throw new IllegalArgumentException("与被保险人关系名称RelationToInsuredName值"+aRelationToInsuredName+"的长度"+aRelationToInsuredName.length()+"大于最大值30");
		RelationToInsuredName = aRelationToInsuredName;
	}
	public String getBnfLot()
	{
		return BnfLot;
	}
	public void setBnfLot(String aBnfLot)
	{
		if(aBnfLot!=null && aBnfLot.length()>10)
			throw new IllegalArgumentException("受益比例BnfLot值"+aBnfLot+"的长度"+aBnfLot.length()+"大于最大值10");
		BnfLot = aBnfLot;
	}
	public String getBnfGrade()
	{
		return BnfGrade;
	}
	public void setBnfGrade(String aBnfGrade)
	{
		if(aBnfGrade!=null && aBnfGrade.length()>30)
			throw new IllegalArgumentException("受益人级别BnfGrade值"+aBnfGrade+"的长度"+aBnfGrade.length()+"大于最大值30");
		BnfGrade = aBnfGrade;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值2");
		State = aState;
	}
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		if(aReason!=null && aReason.length()>3000)
			throw new IllegalArgumentException("原因Reason值"+aReason+"的长度"+aReason.length()+"大于最大值3000");
		Reason = aReason;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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
	* 使用另外一个 LBPOInsuredBnfListSchema 对象给 Schema 赋值
	* @param: aLBPOInsuredBnfListSchema LBPOInsuredBnfListSchema
	**/
	public void setSchema(LBPOInsuredBnfListSchema aLBPOInsuredBnfListSchema)
	{
		this.SerialNo = aLBPOInsuredBnfListSchema.getSerialNo();
		this.GrpPropNo = aLBPOInsuredBnfListSchema.getGrpPropNo();
		this.ActivityID = aLBPOInsuredBnfListSchema.getActivityID();
		this.BatchNo = aLBPOInsuredBnfListSchema.getBatchNo();
		this.OrderNo = aLBPOInsuredBnfListSchema.getOrderNo();
		this.InsuredID = aLBPOInsuredBnfListSchema.getInsuredID();
		this.BnfType = aLBPOInsuredBnfListSchema.getBnfType();
		this.BnfTypeName = aLBPOInsuredBnfListSchema.getBnfTypeName();
		this.BnfOrder = aLBPOInsuredBnfListSchema.getBnfOrder();
		this.BnfOrderName = aLBPOInsuredBnfListSchema.getBnfOrderName();
		this.BnfName = aLBPOInsuredBnfListSchema.getBnfName();
		this.IDType = aLBPOInsuredBnfListSchema.getIDType();
		this.IDTypeName = aLBPOInsuredBnfListSchema.getIDTypeName();
		this.IDNo = aLBPOInsuredBnfListSchema.getIDNo();
		this.Gender = aLBPOInsuredBnfListSchema.getGender();
		this.GenderName = aLBPOInsuredBnfListSchema.getGenderName();
		this.Birthday = aLBPOInsuredBnfListSchema.getBirthday();
		this.RelationToInsured = aLBPOInsuredBnfListSchema.getRelationToInsured();
		this.RelationToInsuredName = aLBPOInsuredBnfListSchema.getRelationToInsuredName();
		this.BnfLot = aLBPOInsuredBnfListSchema.getBnfLot();
		this.BnfGrade = aLBPOInsuredBnfListSchema.getBnfGrade();
		this.State = aLBPOInsuredBnfListSchema.getState();
		this.Reason = aLBPOInsuredBnfListSchema.getReason();
		this.MakeOperator = aLBPOInsuredBnfListSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLBPOInsuredBnfListSchema.getMakeDate());
		this.MakeTime = aLBPOInsuredBnfListSchema.getMakeTime();
		this.ModifyOperator = aLBPOInsuredBnfListSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLBPOInsuredBnfListSchema.getModifyDate());
		this.ModifyTime = aLBPOInsuredBnfListSchema.getModifyTime();
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
			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("GrpPropNo") == null )
				this.GrpPropNo = null;
			else
				this.GrpPropNo = rs.getString("GrpPropNo").trim();

			if( rs.getString("ActivityID") == null )
				this.ActivityID = null;
			else
				this.ActivityID = rs.getString("ActivityID").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			this.OrderNo = rs.getInt("OrderNo");
			if( rs.getString("InsuredID") == null )
				this.InsuredID = null;
			else
				this.InsuredID = rs.getString("InsuredID").trim();

			if( rs.getString("BnfType") == null )
				this.BnfType = null;
			else
				this.BnfType = rs.getString("BnfType").trim();

			if( rs.getString("BnfTypeName") == null )
				this.BnfTypeName = null;
			else
				this.BnfTypeName = rs.getString("BnfTypeName").trim();

			if( rs.getString("BnfOrder") == null )
				this.BnfOrder = null;
			else
				this.BnfOrder = rs.getString("BnfOrder").trim();

			if( rs.getString("BnfOrderName") == null )
				this.BnfOrderName = null;
			else
				this.BnfOrderName = rs.getString("BnfOrderName").trim();

			if( rs.getString("BnfName") == null )
				this.BnfName = null;
			else
				this.BnfName = rs.getString("BnfName").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDTypeName") == null )
				this.IDTypeName = null;
			else
				this.IDTypeName = rs.getString("IDTypeName").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("Gender") == null )
				this.Gender = null;
			else
				this.Gender = rs.getString("Gender").trim();

			if( rs.getString("GenderName") == null )
				this.GenderName = null;
			else
				this.GenderName = rs.getString("GenderName").trim();

			if( rs.getString("Birthday") == null )
				this.Birthday = null;
			else
				this.Birthday = rs.getString("Birthday").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			if( rs.getString("RelationToInsuredName") == null )
				this.RelationToInsuredName = null;
			else
				this.RelationToInsuredName = rs.getString("RelationToInsuredName").trim();

			if( rs.getString("BnfLot") == null )
				this.BnfLot = null;
			else
				this.BnfLot = rs.getString("BnfLot").trim();

			if( rs.getString("BnfGrade") == null )
				this.BnfGrade = null;
			else
				this.BnfGrade = rs.getString("BnfGrade").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBPOInsuredBnfList表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredBnfListSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPOInsuredBnfListSchema getSchema()
	{
		LBPOInsuredBnfListSchema aLBPOInsuredBnfListSchema = new LBPOInsuredBnfListSchema();
		aLBPOInsuredBnfListSchema.setSchema(this);
		return aLBPOInsuredBnfListSchema;
	}

	public LBPOInsuredBnfListDB getDB()
	{
		LBPOInsuredBnfListDB aDBOper = new LBPOInsuredBnfListDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOInsuredBnfList描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActivityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OrderNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfOrder)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfOrderName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Gender)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GenderName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Birthday)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfLot)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOInsuredBnfList>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			GrpPropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OrderNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			InsuredID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BnfType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BnfTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BnfOrder = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BnfOrderName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BnfName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			IDTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Gender = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			GenderName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Birthday = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			RelationToInsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			BnfLot = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			BnfGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredBnfListSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPropNo));
		}
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityID));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrderNo));
		}
		if (FCode.equalsIgnoreCase("InsuredID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredID));
		}
		if (FCode.equalsIgnoreCase("BnfType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfType));
		}
		if (FCode.equalsIgnoreCase("BnfTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfTypeName));
		}
		if (FCode.equalsIgnoreCase("BnfOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfOrder));
		}
		if (FCode.equalsIgnoreCase("BnfOrderName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfOrderName));
		}
		if (FCode.equalsIgnoreCase("BnfName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfName));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDTypeName));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("Gender"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Gender));
		}
		if (FCode.equalsIgnoreCase("GenderName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GenderName));
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Birthday));
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equalsIgnoreCase("RelationToInsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsuredName));
		}
		if (FCode.equalsIgnoreCase("BnfLot"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfLot));
		}
		if (FCode.equalsIgnoreCase("BnfGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfGrade));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPropNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ActivityID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 4:
				strFieldValue = String.valueOf(OrderNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuredID);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BnfType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BnfTypeName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BnfOrder);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BnfOrderName);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BnfName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(IDTypeName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Gender);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(GenderName);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Birthday);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsuredName);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(BnfLot);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(BnfGrade);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 28:
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPropNo = FValue.trim();
			}
			else
				GrpPropNo = null;
		}
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityID = FValue.trim();
			}
			else
				ActivityID = null;
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OrderNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("InsuredID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredID = FValue.trim();
			}
			else
				InsuredID = null;
		}
		if (FCode.equalsIgnoreCase("BnfType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfType = FValue.trim();
			}
			else
				BnfType = null;
		}
		if (FCode.equalsIgnoreCase("BnfTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfTypeName = FValue.trim();
			}
			else
				BnfTypeName = null;
		}
		if (FCode.equalsIgnoreCase("BnfOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfOrder = FValue.trim();
			}
			else
				BnfOrder = null;
		}
		if (FCode.equalsIgnoreCase("BnfOrderName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfOrderName = FValue.trim();
			}
			else
				BnfOrderName = null;
		}
		if (FCode.equalsIgnoreCase("BnfName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfName = FValue.trim();
			}
			else
				BnfName = null;
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDType = FValue.trim();
			}
			else
				IDType = null;
		}
		if (FCode.equalsIgnoreCase("IDTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDTypeName = FValue.trim();
			}
			else
				IDTypeName = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equalsIgnoreCase("Gender"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Gender = FValue.trim();
			}
			else
				Gender = null;
		}
		if (FCode.equalsIgnoreCase("GenderName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GenderName = FValue.trim();
			}
			else
				GenderName = null;
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Birthday = FValue.trim();
			}
			else
				Birthday = null;
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsured = FValue.trim();
			}
			else
				RelationToInsured = null;
		}
		if (FCode.equalsIgnoreCase("RelationToInsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsuredName = FValue.trim();
			}
			else
				RelationToInsuredName = null;
		}
		if (FCode.equalsIgnoreCase("BnfLot"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfLot = FValue.trim();
			}
			else
				BnfLot = null;
		}
		if (FCode.equalsIgnoreCase("BnfGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfGrade = FValue.trim();
			}
			else
				BnfGrade = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LBPOInsuredBnfListSchema other = (LBPOInsuredBnfListSchema)otherObject;
		return
			SerialNo == other.getSerialNo()
			&& GrpPropNo.equals(other.getGrpPropNo())
			&& ActivityID.equals(other.getActivityID())
			&& BatchNo.equals(other.getBatchNo())
			&& OrderNo == other.getOrderNo()
			&& InsuredID.equals(other.getInsuredID())
			&& BnfType.equals(other.getBnfType())
			&& BnfTypeName.equals(other.getBnfTypeName())
			&& BnfOrder.equals(other.getBnfOrder())
			&& BnfOrderName.equals(other.getBnfOrderName())
			&& BnfName.equals(other.getBnfName())
			&& IDType.equals(other.getIDType())
			&& IDTypeName.equals(other.getIDTypeName())
			&& IDNo.equals(other.getIDNo())
			&& Gender.equals(other.getGender())
			&& GenderName.equals(other.getGenderName())
			&& Birthday.equals(other.getBirthday())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& RelationToInsuredName.equals(other.getRelationToInsuredName())
			&& BnfLot.equals(other.getBnfLot())
			&& BnfGrade.equals(other.getBnfGrade())
			&& State.equals(other.getState())
			&& Reason.equals(other.getReason())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPropNo") ) {
			return 1;
		}
		if( strFieldName.equals("ActivityID") ) {
			return 2;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 3;
		}
		if( strFieldName.equals("OrderNo") ) {
			return 4;
		}
		if( strFieldName.equals("InsuredID") ) {
			return 5;
		}
		if( strFieldName.equals("BnfType") ) {
			return 6;
		}
		if( strFieldName.equals("BnfTypeName") ) {
			return 7;
		}
		if( strFieldName.equals("BnfOrder") ) {
			return 8;
		}
		if( strFieldName.equals("BnfOrderName") ) {
			return 9;
		}
		if( strFieldName.equals("BnfName") ) {
			return 10;
		}
		if( strFieldName.equals("IDType") ) {
			return 11;
		}
		if( strFieldName.equals("IDTypeName") ) {
			return 12;
		}
		if( strFieldName.equals("IDNo") ) {
			return 13;
		}
		if( strFieldName.equals("Gender") ) {
			return 14;
		}
		if( strFieldName.equals("GenderName") ) {
			return 15;
		}
		if( strFieldName.equals("Birthday") ) {
			return 16;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 17;
		}
		if( strFieldName.equals("RelationToInsuredName") ) {
			return 18;
		}
		if( strFieldName.equals("BnfLot") ) {
			return 19;
		}
		if( strFieldName.equals("BnfGrade") ) {
			return 20;
		}
		if( strFieldName.equals("State") ) {
			return 21;
		}
		if( strFieldName.equals("Reason") ) {
			return 22;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 23;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 24;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 28;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "GrpPropNo";
				break;
			case 2:
				strFieldName = "ActivityID";
				break;
			case 3:
				strFieldName = "BatchNo";
				break;
			case 4:
				strFieldName = "OrderNo";
				break;
			case 5:
				strFieldName = "InsuredID";
				break;
			case 6:
				strFieldName = "BnfType";
				break;
			case 7:
				strFieldName = "BnfTypeName";
				break;
			case 8:
				strFieldName = "BnfOrder";
				break;
			case 9:
				strFieldName = "BnfOrderName";
				break;
			case 10:
				strFieldName = "BnfName";
				break;
			case 11:
				strFieldName = "IDType";
				break;
			case 12:
				strFieldName = "IDTypeName";
				break;
			case 13:
				strFieldName = "IDNo";
				break;
			case 14:
				strFieldName = "Gender";
				break;
			case 15:
				strFieldName = "GenderName";
				break;
			case 16:
				strFieldName = "Birthday";
				break;
			case 17:
				strFieldName = "RelationToInsured";
				break;
			case 18:
				strFieldName = "RelationToInsuredName";
				break;
			case 19:
				strFieldName = "BnfLot";
				break;
			case 20:
				strFieldName = "BnfGrade";
				break;
			case 21:
				strFieldName = "State";
				break;
			case 22:
				strFieldName = "Reason";
				break;
			case 23:
				strFieldName = "MakeOperator";
				break;
			case 24:
				strFieldName = "MakeDate";
				break;
			case 25:
				strFieldName = "MakeTime";
				break;
			case 26:
				strFieldName = "ModifyOperator";
				break;
			case 27:
				strFieldName = "ModifyDate";
				break;
			case 28:
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GrpPropNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActivityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrderNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfOrder") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfOrderName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Gender") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GenderName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Birthday") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfLot") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
