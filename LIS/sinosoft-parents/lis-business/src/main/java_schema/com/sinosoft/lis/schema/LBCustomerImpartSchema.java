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
import com.sinosoft.lis.db.LBCustomerImpartDB;

/*
 * <p>ClassName: LBCustomerImpartSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LBCustomerImpartSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBCustomerImpartSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 告知编码 */
	private String ImpartCode;
	/** 告知版别 */
	private String ImpartVer;
	/** 告知内容 */
	private String ImpartContent;
	/** 告知参数展现模版 */
	private String ImpartParamModle;
	/** 客户号码 */
	private String CustomerNo;
	/** 客户号码类型 */
	private String CustomerNoType;
	/** 是否参与核保核赔标志 */
	private String UWClaimFlg;
	/** 打印标志 */
	private String PrtFlag;
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
	/** 批次号 */
	private int PatchNo;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBCustomerImpartSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[7];
		pk[0] = "EdorNo";
		pk[1] = "ProposalContNo";
		pk[2] = "ImpartCode";
		pk[3] = "ImpartVer";
		pk[4] = "CustomerNo";
		pk[5] = "CustomerNoType";
		pk[6] = "PatchNo";

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
		LBCustomerImpartSchema cloned = (LBCustomerImpartSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
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
	/**
	* 第3,4位的意义如下：<p>
	* 11：个人印刷号码<p>
	* 12：集体印刷号码<p>
	* 15：银行险印刷号码
	*/
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	public String getImpartCode()
	{
		return ImpartCode;
	}
	public void setImpartCode(String aImpartCode)
	{
		ImpartCode = aImpartCode;
	}
	public String getImpartVer()
	{
		return ImpartVer;
	}
	public void setImpartVer(String aImpartVer)
	{
		ImpartVer = aImpartVer;
	}
	public String getImpartContent()
	{
		return ImpartContent;
	}
	public void setImpartContent(String aImpartContent)
	{
		ImpartContent = aImpartContent;
	}
	public String getImpartParamModle()
	{
		return ImpartParamModle;
	}
	public void setImpartParamModle(String aImpartParamModle)
	{
		ImpartParamModle = aImpartParamModle;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	/**
	* 0 --客户号码为投保人<p>
	* I --客户号码为被保人<p>
	* 3 －客户号码为受益人
	*/
	public String getCustomerNoType()
	{
		return CustomerNoType;
	}
	public void setCustomerNoType(String aCustomerNoType)
	{
		CustomerNoType = aCustomerNoType;
	}
	public String getUWClaimFlg()
	{
		return UWClaimFlg;
	}
	public void setUWClaimFlg(String aUWClaimFlg)
	{
		UWClaimFlg = aUWClaimFlg;
	}
	public String getPrtFlag()
	{
		return PrtFlag;
	}
	public void setPrtFlag(String aPrtFlag)
	{
		PrtFlag = aPrtFlag;
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
	public int getPatchNo()
	{
		return PatchNo;
	}
	public void setPatchNo(int aPatchNo)
	{
		PatchNo = aPatchNo;
	}
	public void setPatchNo(String aPatchNo)
	{
		if (aPatchNo != null && !aPatchNo.equals(""))
		{
			Integer tInteger = new Integer(aPatchNo);
			int i = tInteger.intValue();
			PatchNo = i;
		}
	}


	/**
	* 使用另外一个 LBCustomerImpartSchema 对象给 Schema 赋值
	* @param: aLBCustomerImpartSchema LBCustomerImpartSchema
	**/
	public void setSchema(LBCustomerImpartSchema aLBCustomerImpartSchema)
	{
		this.EdorNo = aLBCustomerImpartSchema.getEdorNo();
		this.GrpContNo = aLBCustomerImpartSchema.getGrpContNo();
		this.ContNo = aLBCustomerImpartSchema.getContNo();
		this.ProposalContNo = aLBCustomerImpartSchema.getProposalContNo();
		this.PrtNo = aLBCustomerImpartSchema.getPrtNo();
		this.ImpartCode = aLBCustomerImpartSchema.getImpartCode();
		this.ImpartVer = aLBCustomerImpartSchema.getImpartVer();
		this.ImpartContent = aLBCustomerImpartSchema.getImpartContent();
		this.ImpartParamModle = aLBCustomerImpartSchema.getImpartParamModle();
		this.CustomerNo = aLBCustomerImpartSchema.getCustomerNo();
		this.CustomerNoType = aLBCustomerImpartSchema.getCustomerNoType();
		this.UWClaimFlg = aLBCustomerImpartSchema.getUWClaimFlg();
		this.PrtFlag = aLBCustomerImpartSchema.getPrtFlag();
		this.Operator = aLBCustomerImpartSchema.getOperator();
		this.MakeDate = fDate.getDate( aLBCustomerImpartSchema.getMakeDate());
		this.MakeTime = aLBCustomerImpartSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBCustomerImpartSchema.getModifyDate());
		this.ModifyTime = aLBCustomerImpartSchema.getModifyTime();
		this.PatchNo = aLBCustomerImpartSchema.getPatchNo();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

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

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("ImpartCode") == null )
				this.ImpartCode = null;
			else
				this.ImpartCode = rs.getString("ImpartCode").trim();

			if( rs.getString("ImpartVer") == null )
				this.ImpartVer = null;
			else
				this.ImpartVer = rs.getString("ImpartVer").trim();

			if( rs.getString("ImpartContent") == null )
				this.ImpartContent = null;
			else
				this.ImpartContent = rs.getString("ImpartContent").trim();

			if( rs.getString("ImpartParamModle") == null )
				this.ImpartParamModle = null;
			else
				this.ImpartParamModle = rs.getString("ImpartParamModle").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerNoType") == null )
				this.CustomerNoType = null;
			else
				this.CustomerNoType = rs.getString("CustomerNoType").trim();

			if( rs.getString("UWClaimFlg") == null )
				this.UWClaimFlg = null;
			else
				this.UWClaimFlg = rs.getString("UWClaimFlg").trim();

			if( rs.getString("PrtFlag") == null )
				this.PrtFlag = null;
			else
				this.PrtFlag = rs.getString("PrtFlag").trim();

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

			this.PatchNo = rs.getInt("PatchNo");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBCustomerImpart表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBCustomerImpartSchema getSchema()
	{
		LBCustomerImpartSchema aLBCustomerImpartSchema = new LBCustomerImpartSchema();
		aLBCustomerImpartSchema.setSchema(this);
		return aLBCustomerImpartSchema;
	}

	public LBCustomerImpartDB getDB()
	{
		LBCustomerImpartDB aDBOper = new LBCustomerImpartDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBCustomerImpart描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartParamModle)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWClaimFlg)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PatchNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBCustomerImpart>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ImpartCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ImpartVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ImpartContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ImpartParamModle = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CustomerNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			UWClaimFlg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PrtFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			PatchNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
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
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("ImpartCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartCode));
		}
		if (FCode.equalsIgnoreCase("ImpartVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartVer));
		}
		if (FCode.equalsIgnoreCase("ImpartContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartContent));
		}
		if (FCode.equalsIgnoreCase("ImpartParamModle"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartParamModle));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNoType));
		}
		if (FCode.equalsIgnoreCase("UWClaimFlg"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWClaimFlg));
		}
		if (FCode.equalsIgnoreCase("PrtFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtFlag));
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
		if (FCode.equalsIgnoreCase("PatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchNo));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ImpartCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ImpartVer);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ImpartContent);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ImpartParamModle);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CustomerNoType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(UWClaimFlg);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PrtFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 18:
				strFieldValue = String.valueOf(PatchNo);
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
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
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
		}
		if (FCode.equalsIgnoreCase("ImpartCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartCode = FValue.trim();
			}
			else
				ImpartCode = null;
		}
		if (FCode.equalsIgnoreCase("ImpartVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartVer = FValue.trim();
			}
			else
				ImpartVer = null;
		}
		if (FCode.equalsIgnoreCase("ImpartContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartContent = FValue.trim();
			}
			else
				ImpartContent = null;
		}
		if (FCode.equalsIgnoreCase("ImpartParamModle"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartParamModle = FValue.trim();
			}
			else
				ImpartParamModle = null;
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
		if (FCode.equalsIgnoreCase("CustomerNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNoType = FValue.trim();
			}
			else
				CustomerNoType = null;
		}
		if (FCode.equalsIgnoreCase("UWClaimFlg"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWClaimFlg = FValue.trim();
			}
			else
				UWClaimFlg = null;
		}
		if (FCode.equalsIgnoreCase("PrtFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtFlag = FValue.trim();
			}
			else
				PrtFlag = null;
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
		if (FCode.equalsIgnoreCase("PatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PatchNo = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBCustomerImpartSchema other = (LBCustomerImpartSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ImpartCode.equals(other.getImpartCode())
			&& ImpartVer.equals(other.getImpartVer())
			&& ImpartContent.equals(other.getImpartContent())
			&& ImpartParamModle.equals(other.getImpartParamModle())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerNoType.equals(other.getCustomerNoType())
			&& UWClaimFlg.equals(other.getUWClaimFlg())
			&& PrtFlag.equals(other.getPrtFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PatchNo == other.getPatchNo();
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 3;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 4;
		}
		if( strFieldName.equals("ImpartCode") ) {
			return 5;
		}
		if( strFieldName.equals("ImpartVer") ) {
			return 6;
		}
		if( strFieldName.equals("ImpartContent") ) {
			return 7;
		}
		if( strFieldName.equals("ImpartParamModle") ) {
			return 8;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 9;
		}
		if( strFieldName.equals("CustomerNoType") ) {
			return 10;
		}
		if( strFieldName.equals("UWClaimFlg") ) {
			return 11;
		}
		if( strFieldName.equals("PrtFlag") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
		}
		if( strFieldName.equals("PatchNo") ) {
			return 18;
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "ProposalContNo";
				break;
			case 4:
				strFieldName = "PrtNo";
				break;
			case 5:
				strFieldName = "ImpartCode";
				break;
			case 6:
				strFieldName = "ImpartVer";
				break;
			case 7:
				strFieldName = "ImpartContent";
				break;
			case 8:
				strFieldName = "ImpartParamModle";
				break;
			case 9:
				strFieldName = "CustomerNo";
				break;
			case 10:
				strFieldName = "CustomerNoType";
				break;
			case 11:
				strFieldName = "UWClaimFlg";
				break;
			case 12:
				strFieldName = "PrtFlag";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
				strFieldName = "ModifyTime";
				break;
			case 18:
				strFieldName = "PatchNo";
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartParamModle") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWClaimFlg") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtFlag") ) {
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
		if( strFieldName.equals("PatchNo") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
