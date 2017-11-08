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
import com.sinosoft.lis.db.LDAttachmentDB;

/*
 * <p>ClassName: LDAttachmentSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 配置管理
 */
public class LDAttachmentSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDAttachmentSchema.class);
	// @Field
	/** 附件id */
	private String AttachID;
	/** 业务类型 */
	private String OtherNoType;
	/** 业务号码 */
	private String OtherNo;
	/** 次业务号码 */
	private String SubOtherNo;
	/** 附件类型 */
	private String AttachType;
	/** 附件细类 */
	private String SubAttachType;
	/** 附件名称 */
	private String AttachName;
	/** 附件系统名称 */
	private String AttachSysName;
	/** 上传节点 */
	private String UploadNode;
	/** 附件路径 */
	private String AttachPath;
	/** 扩展名 */
	private String ExtenName;
	/** 附件标识 */
	private String AttachFlag;
	/** 附件件数 */
	private int AttachNum;
	/** 备注 */
	private String Remark;
	/** 备用字段1 */
	private String Segment1;
	/** 备用字段2 */
	private String Segment2;
	/** 备用字段3 */
	private String Segment3;
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

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDAttachmentSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AttachID";

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
		LDAttachmentSchema cloned = (LDAttachmentSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAttachID()
	{
		return AttachID;
	}
	public void setAttachID(String aAttachID)
	{
		if(aAttachID!=null && aAttachID.length()>10)
			throw new IllegalArgumentException("附件idAttachID值"+aAttachID+"的长度"+aAttachID.length()+"大于最大值10");
		AttachID = aAttachID;
	}
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		if(aOtherNoType!=null && aOtherNoType.length()>5)
			throw new IllegalArgumentException("业务类型OtherNoType值"+aOtherNoType+"的长度"+aOtherNoType.length()+"大于最大值5");
		OtherNoType = aOtherNoType;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("业务号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	public String getSubOtherNo()
	{
		return SubOtherNo;
	}
	public void setSubOtherNo(String aSubOtherNo)
	{
		if(aSubOtherNo!=null && aSubOtherNo.length()>20)
			throw new IllegalArgumentException("次业务号码SubOtherNo值"+aSubOtherNo+"的长度"+aSubOtherNo.length()+"大于最大值20");
		SubOtherNo = aSubOtherNo;
	}
	public String getAttachType()
	{
		return AttachType;
	}
	public void setAttachType(String aAttachType)
	{
		if(aAttachType!=null && aAttachType.length()>2)
			throw new IllegalArgumentException("附件类型AttachType值"+aAttachType+"的长度"+aAttachType.length()+"大于最大值2");
		AttachType = aAttachType;
	}
	public String getSubAttachType()
	{
		return SubAttachType;
	}
	public void setSubAttachType(String aSubAttachType)
	{
		if(aSubAttachType!=null && aSubAttachType.length()>2)
			throw new IllegalArgumentException("附件细类SubAttachType值"+aSubAttachType+"的长度"+aSubAttachType.length()+"大于最大值2");
		SubAttachType = aSubAttachType;
	}
	public String getAttachName()
	{
		return AttachName;
	}
	public void setAttachName(String aAttachName)
	{
		if(aAttachName!=null && aAttachName.length()>200)
			throw new IllegalArgumentException("附件名称AttachName值"+aAttachName+"的长度"+aAttachName.length()+"大于最大值200");
		AttachName = aAttachName;
	}
	public String getAttachSysName()
	{
		return AttachSysName;
	}
	public void setAttachSysName(String aAttachSysName)
	{
		if(aAttachSysName!=null && aAttachSysName.length()>200)
			throw new IllegalArgumentException("附件系统名称AttachSysName值"+aAttachSysName+"的长度"+aAttachSysName.length()+"大于最大值200");
		AttachSysName = aAttachSysName;
	}
	public String getUploadNode()
	{
		return UploadNode;
	}
	public void setUploadNode(String aUploadNode)
	{
		if(aUploadNode!=null && aUploadNode.length()>10)
			throw new IllegalArgumentException("上传节点UploadNode值"+aUploadNode+"的长度"+aUploadNode.length()+"大于最大值10");
		UploadNode = aUploadNode;
	}
	public String getAttachPath()
	{
		return AttachPath;
	}
	public void setAttachPath(String aAttachPath)
	{
		if(aAttachPath!=null && aAttachPath.length()>200)
			throw new IllegalArgumentException("附件路径AttachPath值"+aAttachPath+"的长度"+aAttachPath.length()+"大于最大值200");
		AttachPath = aAttachPath;
	}
	public String getExtenName()
	{
		return ExtenName;
	}
	public void setExtenName(String aExtenName)
	{
		if(aExtenName!=null && aExtenName.length()>10)
			throw new IllegalArgumentException("扩展名ExtenName值"+aExtenName+"的长度"+aExtenName.length()+"大于最大值10");
		ExtenName = aExtenName;
	}
	public String getAttachFlag()
	{
		return AttachFlag;
	}
	public void setAttachFlag(String aAttachFlag)
	{
		if(aAttachFlag!=null && aAttachFlag.length()>2)
			throw new IllegalArgumentException("附件标识AttachFlag值"+aAttachFlag+"的长度"+aAttachFlag.length()+"大于最大值2");
		AttachFlag = aAttachFlag;
	}
	public int getAttachNum()
	{
		return AttachNum;
	}
	public void setAttachNum(int aAttachNum)
	{
		AttachNum = aAttachNum;
	}
	public void setAttachNum(String aAttachNum)
	{
		if (aAttachNum != null && !aAttachNum.equals(""))
		{
			Integer tInteger = new Integer(aAttachNum);
			int i = tInteger.intValue();
			AttachNum = i;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>3000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值3000");
		Remark = aRemark;
	}
	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>20)
			throw new IllegalArgumentException("备用字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值20");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>20)
			throw new IllegalArgumentException("备用字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值20");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>20)
			throw new IllegalArgumentException("备用字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值20");
		Segment3 = aSegment3;
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
	* 使用另外一个 LDAttachmentSchema 对象给 Schema 赋值
	* @param: aLDAttachmentSchema LDAttachmentSchema
	**/
	public void setSchema(LDAttachmentSchema aLDAttachmentSchema)
	{
		this.AttachID = aLDAttachmentSchema.getAttachID();
		this.OtherNoType = aLDAttachmentSchema.getOtherNoType();
		this.OtherNo = aLDAttachmentSchema.getOtherNo();
		this.SubOtherNo = aLDAttachmentSchema.getSubOtherNo();
		this.AttachType = aLDAttachmentSchema.getAttachType();
		this.SubAttachType = aLDAttachmentSchema.getSubAttachType();
		this.AttachName = aLDAttachmentSchema.getAttachName();
		this.AttachSysName = aLDAttachmentSchema.getAttachSysName();
		this.UploadNode = aLDAttachmentSchema.getUploadNode();
		this.AttachPath = aLDAttachmentSchema.getAttachPath();
		this.ExtenName = aLDAttachmentSchema.getExtenName();
		this.AttachFlag = aLDAttachmentSchema.getAttachFlag();
		this.AttachNum = aLDAttachmentSchema.getAttachNum();
		this.Remark = aLDAttachmentSchema.getRemark();
		this.Segment1 = aLDAttachmentSchema.getSegment1();
		this.Segment2 = aLDAttachmentSchema.getSegment2();
		this.Segment3 = aLDAttachmentSchema.getSegment3();
		this.MakeOperator = aLDAttachmentSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLDAttachmentSchema.getMakeDate());
		this.MakeTime = aLDAttachmentSchema.getMakeTime();
		this.ModifyOperator = aLDAttachmentSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLDAttachmentSchema.getModifyDate());
		this.ModifyTime = aLDAttachmentSchema.getModifyTime();
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
			if( rs.getString("AttachID") == null )
				this.AttachID = null;
			else
				this.AttachID = rs.getString("AttachID").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("SubOtherNo") == null )
				this.SubOtherNo = null;
			else
				this.SubOtherNo = rs.getString("SubOtherNo").trim();

			if( rs.getString("AttachType") == null )
				this.AttachType = null;
			else
				this.AttachType = rs.getString("AttachType").trim();

			if( rs.getString("SubAttachType") == null )
				this.SubAttachType = null;
			else
				this.SubAttachType = rs.getString("SubAttachType").trim();

			if( rs.getString("AttachName") == null )
				this.AttachName = null;
			else
				this.AttachName = rs.getString("AttachName").trim();

			if( rs.getString("AttachSysName") == null )
				this.AttachSysName = null;
			else
				this.AttachSysName = rs.getString("AttachSysName").trim();

			if( rs.getString("UploadNode") == null )
				this.UploadNode = null;
			else
				this.UploadNode = rs.getString("UploadNode").trim();

			if( rs.getString("AttachPath") == null )
				this.AttachPath = null;
			else
				this.AttachPath = rs.getString("AttachPath").trim();

			if( rs.getString("ExtenName") == null )
				this.ExtenName = null;
			else
				this.ExtenName = rs.getString("ExtenName").trim();

			if( rs.getString("AttachFlag") == null )
				this.AttachFlag = null;
			else
				this.AttachFlag = rs.getString("AttachFlag").trim();

			this.AttachNum = rs.getInt("AttachNum");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Segment1") == null )
				this.Segment1 = null;
			else
				this.Segment1 = rs.getString("Segment1").trim();

			if( rs.getString("Segment2") == null )
				this.Segment2 = null;
			else
				this.Segment2 = rs.getString("Segment2").trim();

			if( rs.getString("Segment3") == null )
				this.Segment3 = null;
			else
				this.Segment3 = rs.getString("Segment3").trim();

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
			logger.debug("数据库中的LDAttachment表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDAttachmentSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDAttachmentSchema getSchema()
	{
		LDAttachmentSchema aLDAttachmentSchema = new LDAttachmentSchema();
		aLDAttachmentSchema.setSchema(this);
		return aLDAttachmentSchema;
	}

	public LDAttachmentDB getDB()
	{
		LDAttachmentDB aDBOper = new LDAttachmentDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDAttachment描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AttachID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubOtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AttachType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubAttachType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AttachName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AttachSysName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UploadNode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AttachPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExtenName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AttachFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AttachNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDAttachment>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AttachID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SubOtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AttachType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SubAttachType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AttachName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AttachSysName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			UploadNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AttachPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ExtenName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AttachFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AttachNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDAttachmentSchema";
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
		if (FCode.equalsIgnoreCase("AttachID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AttachID));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("SubOtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubOtherNo));
		}
		if (FCode.equalsIgnoreCase("AttachType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AttachType));
		}
		if (FCode.equalsIgnoreCase("SubAttachType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubAttachType));
		}
		if (FCode.equalsIgnoreCase("AttachName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AttachName));
		}
		if (FCode.equalsIgnoreCase("AttachSysName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AttachSysName));
		}
		if (FCode.equalsIgnoreCase("UploadNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UploadNode));
		}
		if (FCode.equalsIgnoreCase("AttachPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AttachPath));
		}
		if (FCode.equalsIgnoreCase("ExtenName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtenName));
		}
		if (FCode.equalsIgnoreCase("AttachFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AttachFlag));
		}
		if (FCode.equalsIgnoreCase("AttachNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AttachNum));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment1));
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment2));
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment3));
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
				strFieldValue = StrTool.GBKToUnicode(AttachID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SubOtherNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AttachType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SubAttachType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AttachName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AttachSysName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(UploadNode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AttachPath);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ExtenName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AttachFlag);
				break;
			case 12:
				strFieldValue = String.valueOf(AttachNum);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 22:
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

		if (FCode.equalsIgnoreCase("AttachID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AttachID = FValue.trim();
			}
			else
				AttachID = null;
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNoType = FValue.trim();
			}
			else
				OtherNoType = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("SubOtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubOtherNo = FValue.trim();
			}
			else
				SubOtherNo = null;
		}
		if (FCode.equalsIgnoreCase("AttachType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AttachType = FValue.trim();
			}
			else
				AttachType = null;
		}
		if (FCode.equalsIgnoreCase("SubAttachType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubAttachType = FValue.trim();
			}
			else
				SubAttachType = null;
		}
		if (FCode.equalsIgnoreCase("AttachName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AttachName = FValue.trim();
			}
			else
				AttachName = null;
		}
		if (FCode.equalsIgnoreCase("AttachSysName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AttachSysName = FValue.trim();
			}
			else
				AttachSysName = null;
		}
		if (FCode.equalsIgnoreCase("UploadNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UploadNode = FValue.trim();
			}
			else
				UploadNode = null;
		}
		if (FCode.equalsIgnoreCase("AttachPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AttachPath = FValue.trim();
			}
			else
				AttachPath = null;
		}
		if (FCode.equalsIgnoreCase("ExtenName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExtenName = FValue.trim();
			}
			else
				ExtenName = null;
		}
		if (FCode.equalsIgnoreCase("AttachFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AttachFlag = FValue.trim();
			}
			else
				AttachFlag = null;
		}
		if (FCode.equalsIgnoreCase("AttachNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AttachNum = i;
			}
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
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment1 = FValue.trim();
			}
			else
				Segment1 = null;
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment2 = FValue.trim();
			}
			else
				Segment2 = null;
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment3 = FValue.trim();
			}
			else
				Segment3 = null;
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
		LDAttachmentSchema other = (LDAttachmentSchema)otherObject;
		return
			AttachID.equals(other.getAttachID())
			&& OtherNoType.equals(other.getOtherNoType())
			&& OtherNo.equals(other.getOtherNo())
			&& SubOtherNo.equals(other.getSubOtherNo())
			&& AttachType.equals(other.getAttachType())
			&& SubAttachType.equals(other.getSubAttachType())
			&& AttachName.equals(other.getAttachName())
			&& AttachSysName.equals(other.getAttachSysName())
			&& UploadNode.equals(other.getUploadNode())
			&& AttachPath.equals(other.getAttachPath())
			&& ExtenName.equals(other.getExtenName())
			&& AttachFlag.equals(other.getAttachFlag())
			&& AttachNum == other.getAttachNum()
			&& Remark.equals(other.getRemark())
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
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
		if( strFieldName.equals("AttachID") ) {
			return 0;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 1;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 2;
		}
		if( strFieldName.equals("SubOtherNo") ) {
			return 3;
		}
		if( strFieldName.equals("AttachType") ) {
			return 4;
		}
		if( strFieldName.equals("SubAttachType") ) {
			return 5;
		}
		if( strFieldName.equals("AttachName") ) {
			return 6;
		}
		if( strFieldName.equals("AttachSysName") ) {
			return 7;
		}
		if( strFieldName.equals("UploadNode") ) {
			return 8;
		}
		if( strFieldName.equals("AttachPath") ) {
			return 9;
		}
		if( strFieldName.equals("ExtenName") ) {
			return 10;
		}
		if( strFieldName.equals("AttachFlag") ) {
			return 11;
		}
		if( strFieldName.equals("AttachNum") ) {
			return 12;
		}
		if( strFieldName.equals("Remark") ) {
			return 13;
		}
		if( strFieldName.equals("Segment1") ) {
			return 14;
		}
		if( strFieldName.equals("Segment2") ) {
			return 15;
		}
		if( strFieldName.equals("Segment3") ) {
			return 16;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
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
				strFieldName = "AttachID";
				break;
			case 1:
				strFieldName = "OtherNoType";
				break;
			case 2:
				strFieldName = "OtherNo";
				break;
			case 3:
				strFieldName = "SubOtherNo";
				break;
			case 4:
				strFieldName = "AttachType";
				break;
			case 5:
				strFieldName = "SubAttachType";
				break;
			case 6:
				strFieldName = "AttachName";
				break;
			case 7:
				strFieldName = "AttachSysName";
				break;
			case 8:
				strFieldName = "UploadNode";
				break;
			case 9:
				strFieldName = "AttachPath";
				break;
			case 10:
				strFieldName = "ExtenName";
				break;
			case 11:
				strFieldName = "AttachFlag";
				break;
			case 12:
				strFieldName = "AttachNum";
				break;
			case 13:
				strFieldName = "Remark";
				break;
			case 14:
				strFieldName = "Segment1";
				break;
			case 15:
				strFieldName = "Segment2";
				break;
			case 16:
				strFieldName = "Segment3";
				break;
			case 17:
				strFieldName = "MakeOperator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyOperator";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
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
		if( strFieldName.equals("AttachID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubOtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AttachType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubAttachType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AttachName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AttachSysName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UploadNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AttachPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExtenName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AttachFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AttachNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment3") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
