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
import com.sinosoft.lis.db.LCReinsureReportTraceDB;

/*
 * <p>ClassName: LCReinsureReportTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LCReinsureReportTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCReinsureReportTraceSchema.class);

	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 再保呈报顺序号 */
	private int ReinsuOrder;
	/** 再保结论 */
	private String ReinsuredResult;
	/** 再保呈报日期 */
	private Date MakeDate;
	/** 再保呈报时间 */
	private String MakeTime;
	/** 再保呈报人代码 */
	private String UserCode;
	/** 再保处理人代码 */
	private String DealUserCode;
	/** 再保处理日期 */
	private Date ModifyDate;
	/** 再保处理时间 */
	private String ModifyTime;
	/** 原因描述 */
	private String ReinsuDesc;
	/** 再保备注 */
	private String ReinsuRemark;
	/** 呈报备注 */
	private String ReportRemark;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCReinsureReportTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ContNo";
		pk[1] = "ReinsuOrder";

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
		LCReinsureReportTraceSchema cloned = (LCReinsureReportTraceSchema)super.clone();
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
		GrpContNo = aGrpContNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	public int getReinsuOrder()
	{
		return ReinsuOrder;
	}
	public void setReinsuOrder(int aReinsuOrder)
	{
		ReinsuOrder = aReinsuOrder;
	}
	public void setReinsuOrder(String aReinsuOrder)
	{
		if (aReinsuOrder != null && !aReinsuOrder.equals(""))
		{
			Integer tInteger = new Integer(aReinsuOrder);
			int i = tInteger.intValue();
			ReinsuOrder = i;
		}
	}

	/**
	* 1-同意按原核保结论承保<p>
	* 2-建议不予承保<p>
	* 3-建议加费承保<p>
	* 4-建议降额承保<p>
	* 5-需补充材料
	*/
	public String getReinsuredResult()
	{
		return ReinsuredResult;
	}
	public void setReinsuredResult(String aReinsuredResult)
	{
		ReinsuredResult = aReinsuredResult;
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
	public String getUserCode()
	{
		return UserCode;
	}
	public void setUserCode(String aUserCode)
	{
		UserCode = aUserCode;
	}
	public String getDealUserCode()
	{
		return DealUserCode;
	}
	public void setDealUserCode(String aDealUserCode)
	{
		DealUserCode = aDealUserCode;
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
	public String getReinsuDesc()
	{
		return ReinsuDesc;
	}
	public void setReinsuDesc(String aReinsuDesc)
	{
		ReinsuDesc = aReinsuDesc;
	}
	public String getReinsuRemark()
	{
		return ReinsuRemark;
	}
	public void setReinsuRemark(String aReinsuRemark)
	{
		ReinsuRemark = aReinsuRemark;
	}
	public String getReportRemark()
	{
		return ReportRemark;
	}
	public void setReportRemark(String aReportRemark)
	{
		ReportRemark = aReportRemark;
	}

	/**
	* 使用另外一个 LCReinsureReportTraceSchema 对象给 Schema 赋值
	* @param: aLCReinsureReportTraceSchema LCReinsureReportTraceSchema
	**/
	public void setSchema(LCReinsureReportTraceSchema aLCReinsureReportTraceSchema)
	{
		this.GrpContNo = aLCReinsureReportTraceSchema.getGrpContNo();
		this.ContNo = aLCReinsureReportTraceSchema.getContNo();
		this.ProposalContNo = aLCReinsureReportTraceSchema.getProposalContNo();
		this.ReinsuOrder = aLCReinsureReportTraceSchema.getReinsuOrder();
		this.ReinsuredResult = aLCReinsureReportTraceSchema.getReinsuredResult();
		this.MakeDate = fDate.getDate( aLCReinsureReportTraceSchema.getMakeDate());
		this.MakeTime = aLCReinsureReportTraceSchema.getMakeTime();
		this.UserCode = aLCReinsureReportTraceSchema.getUserCode();
		this.DealUserCode = aLCReinsureReportTraceSchema.getDealUserCode();
		this.ModifyDate = fDate.getDate( aLCReinsureReportTraceSchema.getModifyDate());
		this.ModifyTime = aLCReinsureReportTraceSchema.getModifyTime();
		this.ReinsuDesc = aLCReinsureReportTraceSchema.getReinsuDesc();
		this.ReinsuRemark = aLCReinsureReportTraceSchema.getReinsuRemark();
		this.ReportRemark = aLCReinsureReportTraceSchema.getReportRemark();
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

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			this.ReinsuOrder = rs.getInt("ReinsuOrder");
			if( rs.getString("ReinsuredResult") == null )
				this.ReinsuredResult = null;
			else
				this.ReinsuredResult = rs.getString("ReinsuredResult").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("UserCode") == null )
				this.UserCode = null;
			else
				this.UserCode = rs.getString("UserCode").trim();

			if( rs.getString("DealUserCode") == null )
				this.DealUserCode = null;
			else
				this.DealUserCode = rs.getString("DealUserCode").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("ReinsuDesc") == null )
				this.ReinsuDesc = null;
			else
				this.ReinsuDesc = rs.getString("ReinsuDesc").trim();

			if( rs.getString("ReinsuRemark") == null )
				this.ReinsuRemark = null;
			else
				this.ReinsuRemark = rs.getString("ReinsuRemark").trim();

			if( rs.getString("ReportRemark") == null )
				this.ReportRemark = null;
			else
				this.ReportRemark = rs.getString("ReportRemark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCReinsureReportTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCReinsureReportTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCReinsureReportTraceSchema getSchema()
	{
		LCReinsureReportTraceSchema aLCReinsureReportTraceSchema = new LCReinsureReportTraceSchema();
		aLCReinsureReportTraceSchema.setSchema(this);
		return aLCReinsureReportTraceSchema;
	}

	public LCReinsureReportTraceDB getDB()
	{
		LCReinsureReportTraceDB aDBOper = new LCReinsureReportTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCReinsureReportTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ReinsuOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsuredResult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealUserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsuDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsuRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReportRemark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCReinsureReportTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ReinsuOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			ReinsuredResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DealUserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ReinsuDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ReinsuRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ReportRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCReinsureReportTraceSchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("ReinsuOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsuOrder));
		}
		if (FCode.equalsIgnoreCase("ReinsuredResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsuredResult));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("UserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserCode));
		}
		if (FCode.equalsIgnoreCase("DealUserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealUserCode));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ReinsuDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsuDesc));
		}
		if (FCode.equalsIgnoreCase("ReinsuRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsuRemark));
		}
		if (FCode.equalsIgnoreCase("ReportRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReportRemark));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 3:
				strFieldValue = String.valueOf(ReinsuOrder);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReinsuredResult);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(UserCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DealUserCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ReinsuDesc);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ReinsuRemark);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ReportRemark);
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
		}
		if (FCode.equalsIgnoreCase("ReinsuOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ReinsuOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("ReinsuredResult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsuredResult = FValue.trim();
			}
			else
				ReinsuredResult = null;
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
		if (FCode.equalsIgnoreCase("UserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserCode = FValue.trim();
			}
			else
				UserCode = null;
		}
		if (FCode.equalsIgnoreCase("DealUserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealUserCode = FValue.trim();
			}
			else
				DealUserCode = null;
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
		if (FCode.equalsIgnoreCase("ReinsuDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsuDesc = FValue.trim();
			}
			else
				ReinsuDesc = null;
		}
		if (FCode.equalsIgnoreCase("ReinsuRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsuRemark = FValue.trim();
			}
			else
				ReinsuRemark = null;
		}
		if (FCode.equalsIgnoreCase("ReportRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReportRemark = FValue.trim();
			}
			else
				ReportRemark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCReinsureReportTraceSchema other = (LCReinsureReportTraceSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& ReinsuOrder == other.getReinsuOrder()
			&& ReinsuredResult.equals(other.getReinsuredResult())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& UserCode.equals(other.getUserCode())
			&& DealUserCode.equals(other.getDealUserCode())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ReinsuDesc.equals(other.getReinsuDesc())
			&& ReinsuRemark.equals(other.getReinsuRemark())
			&& ReportRemark.equals(other.getReportRemark());
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
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ReinsuOrder") ) {
			return 3;
		}
		if( strFieldName.equals("ReinsuredResult") ) {
			return 4;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 5;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 6;
		}
		if( strFieldName.equals("UserCode") ) {
			return 7;
		}
		if( strFieldName.equals("DealUserCode") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 10;
		}
		if( strFieldName.equals("ReinsuDesc") ) {
			return 11;
		}
		if( strFieldName.equals("ReinsuRemark") ) {
			return 12;
		}
		if( strFieldName.equals("ReportRemark") ) {
			return 13;
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
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "ProposalContNo";
				break;
			case 3:
				strFieldName = "ReinsuOrder";
				break;
			case 4:
				strFieldName = "ReinsuredResult";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
				strFieldName = "MakeTime";
				break;
			case 7:
				strFieldName = "UserCode";
				break;
			case 8:
				strFieldName = "DealUserCode";
				break;
			case 9:
				strFieldName = "ModifyDate";
				break;
			case 10:
				strFieldName = "ModifyTime";
				break;
			case 11:
				strFieldName = "ReinsuDesc";
				break;
			case 12:
				strFieldName = "ReinsuRemark";
				break;
			case 13:
				strFieldName = "ReportRemark";
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsuOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ReinsuredResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealUserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsuDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsuRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReportRemark") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
