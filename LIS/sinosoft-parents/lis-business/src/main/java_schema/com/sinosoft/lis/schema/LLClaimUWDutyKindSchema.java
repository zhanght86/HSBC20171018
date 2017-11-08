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
import com.sinosoft.lis.db.LLClaimUWDutyKindDB;

/*
 * <p>ClassName: LLClaimUWDutyKindSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimUWDutyKindSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimUWDutyKindSchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 核赔次数 */
	private String ClmUWNo;
	/** 账单金额 */
	private double TabFeeMoney;
	/** 核算赔付金额 */
	private double StandPay;
	/** 理算金额 */
	private double ClaimMoney;
	/** 社保给付 */
	private double SocPay;
	/** 第三方给付 */
	private double OthPay;
	/** 核赔赔付金额 */
	private double RealPay;
	/** 拒赔金额 */
	private double DeclinePay;
	/** 备注 */
	private String Remark;
	/** 回退号 */
	private String BackNo;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimUWDutyKindSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ClmNo";
		pk[1] = "GetDutyKind";
		pk[2] = "ClmUWNo";

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
		LLClaimUWDutyKindSchema cloned = (LLClaimUWDutyKindSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		ClmNo = aClmNo;
	}
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	public String getClmUWNo()
	{
		return ClmUWNo;
	}
	public void setClmUWNo(String aClmUWNo)
	{
		ClmUWNo = aClmUWNo;
	}
	/**
	* 医疗帐单录入原始金额
	*/
	public double getTabFeeMoney()
	{
		return TabFeeMoney;
	}
	public void setTabFeeMoney(double aTabFeeMoney)
	{
		TabFeeMoney = aTabFeeMoney;
	}
	public void setTabFeeMoney(String aTabFeeMoney)
	{
		if (aTabFeeMoney != null && !aTabFeeMoney.equals(""))
		{
			Double tDouble = new Double(aTabFeeMoney);
			double d = tDouble.doubleValue();
			TabFeeMoney = d;
		}
	}

	/**
	* 计算出来的金额,各个保项的汇总金额
	*/
	public double getStandPay()
	{
		return StandPay;
	}
	public void setStandPay(double aStandPay)
	{
		StandPay = aStandPay;
	}
	public void setStandPay(String aStandPay)
	{
		if (aStandPay != null && !aStandPay.equals(""))
		{
			Double tDouble = new Double(aStandPay);
			double d = tDouble.doubleValue();
			StandPay = d;
		}
	}

	/**
	* 为帐单金额,和核算赔付金额的最小值<p>
	* 加上<p>
	* 医疗类特殊附加险(类似225)
	*/
	public double getClaimMoney()
	{
		return ClaimMoney;
	}
	public void setClaimMoney(double aClaimMoney)
	{
		ClaimMoney = aClaimMoney;
	}
	public void setClaimMoney(String aClaimMoney)
	{
		if (aClaimMoney != null && !aClaimMoney.equals(""))
		{
			Double tDouble = new Double(aClaimMoney);
			double d = tDouble.doubleValue();
			ClaimMoney = d;
		}
	}

	public double getSocPay()
	{
		return SocPay;
	}
	public void setSocPay(double aSocPay)
	{
		SocPay = aSocPay;
	}
	public void setSocPay(String aSocPay)
	{
		if (aSocPay != null && !aSocPay.equals(""))
		{
			Double tDouble = new Double(aSocPay);
			double d = tDouble.doubleValue();
			SocPay = d;
		}
	}

	public double getOthPay()
	{
		return OthPay;
	}
	public void setOthPay(double aOthPay)
	{
		OthPay = aOthPay;
	}
	public void setOthPay(String aOthPay)
	{
		if (aOthPay != null && !aOthPay.equals(""))
		{
			Double tDouble = new Double(aOthPay);
			double d = tDouble.doubleValue();
			OthPay = d;
		}
	}

	/**
	* 最后确定赔付的金额<p>
	* 理算金额 - 社保给付 - 第三方给付
	*/
	public double getRealPay()
	{
		return RealPay;
	}
	public void setRealPay(double aRealPay)
	{
		RealPay = aRealPay;
	}
	public void setRealPay(String aRealPay)
	{
		if (aRealPay != null && !aRealPay.equals(""))
		{
			Double tDouble = new Double(aRealPay);
			double d = tDouble.doubleValue();
			RealPay = d;
		}
	}

	public double getDeclinePay()
	{
		return DeclinePay;
	}
	public void setDeclinePay(double aDeclinePay)
	{
		DeclinePay = aDeclinePay;
	}
	public void setDeclinePay(String aDeclinePay)
	{
		if (aDeclinePay != null && !aDeclinePay.equals(""))
		{
			Double tDouble = new Double(aDeclinePay);
			double d = tDouble.doubleValue();
			DeclinePay = d;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getBackNo()
	{
		return BackNo;
	}
	public void setBackNo(String aBackNo)
	{
		BackNo = aBackNo;
	}

	/**
	* 使用另外一个 LLClaimUWDutyKindSchema 对象给 Schema 赋值
	* @param: aLLClaimUWDutyKindSchema LLClaimUWDutyKindSchema
	**/
	public void setSchema(LLClaimUWDutyKindSchema aLLClaimUWDutyKindSchema)
	{
		this.ClmNo = aLLClaimUWDutyKindSchema.getClmNo();
		this.GetDutyKind = aLLClaimUWDutyKindSchema.getGetDutyKind();
		this.ClmUWNo = aLLClaimUWDutyKindSchema.getClmUWNo();
		this.TabFeeMoney = aLLClaimUWDutyKindSchema.getTabFeeMoney();
		this.StandPay = aLLClaimUWDutyKindSchema.getStandPay();
		this.ClaimMoney = aLLClaimUWDutyKindSchema.getClaimMoney();
		this.SocPay = aLLClaimUWDutyKindSchema.getSocPay();
		this.OthPay = aLLClaimUWDutyKindSchema.getOthPay();
		this.RealPay = aLLClaimUWDutyKindSchema.getRealPay();
		this.DeclinePay = aLLClaimUWDutyKindSchema.getDeclinePay();
		this.Remark = aLLClaimUWDutyKindSchema.getRemark();
		this.BackNo = aLLClaimUWDutyKindSchema.getBackNo();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("ClmUWNo") == null )
				this.ClmUWNo = null;
			else
				this.ClmUWNo = rs.getString("ClmUWNo").trim();

			this.TabFeeMoney = rs.getDouble("TabFeeMoney");
			this.StandPay = rs.getDouble("StandPay");
			this.ClaimMoney = rs.getDouble("ClaimMoney");
			this.SocPay = rs.getDouble("SocPay");
			this.OthPay = rs.getDouble("OthPay");
			this.RealPay = rs.getDouble("RealPay");
			this.DeclinePay = rs.getDouble("DeclinePay");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("BackNo") == null )
				this.BackNo = null;
			else
				this.BackNo = rs.getString("BackNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLClaimUWDutyKind表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUWDutyKindSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimUWDutyKindSchema getSchema()
	{
		LLClaimUWDutyKindSchema aLLClaimUWDutyKindSchema = new LLClaimUWDutyKindSchema();
		aLLClaimUWDutyKindSchema.setSchema(this);
		return aLLClaimUWDutyKindSchema;
	}

	public LLClaimUWDutyKindDB getDB()
	{
		LLClaimUWDutyKindDB aDBOper = new LLClaimUWDutyKindDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUWDutyKind描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TabFeeMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClaimMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SocPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OthPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DeclinePay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUWDutyKind>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ClmUWNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TabFeeMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			StandPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			ClaimMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			SocPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			OthPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			RealPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			DeclinePay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			BackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUWDutyKindSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("ClmUWNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWNo));
		}
		if (FCode.equalsIgnoreCase("TabFeeMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TabFeeMoney));
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPay));
		}
		if (FCode.equalsIgnoreCase("ClaimMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimMoney));
		}
		if (FCode.equalsIgnoreCase("SocPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocPay));
		}
		if (FCode.equalsIgnoreCase("OthPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthPay));
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealPay));
		}
		if (FCode.equalsIgnoreCase("DeclinePay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclinePay));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackNo));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClmUWNo);
				break;
			case 3:
				strFieldValue = String.valueOf(TabFeeMoney);
				break;
			case 4:
				strFieldValue = String.valueOf(StandPay);
				break;
			case 5:
				strFieldValue = String.valueOf(ClaimMoney);
				break;
			case 6:
				strFieldValue = String.valueOf(SocPay);
				break;
			case 7:
				strFieldValue = String.valueOf(OthPay);
				break;
			case 8:
				strFieldValue = String.valueOf(RealPay);
				break;
			case 9:
				strFieldValue = String.valueOf(DeclinePay);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(BackNo);
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("ClmUWNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmUWNo = FValue.trim();
			}
			else
				ClmUWNo = null;
		}
		if (FCode.equalsIgnoreCase("TabFeeMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TabFeeMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClaimMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClaimMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("SocPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SocPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("OthPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OthPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RealPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("DeclinePay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DeclinePay = d;
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
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackNo = FValue.trim();
			}
			else
				BackNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLClaimUWDutyKindSchema other = (LLClaimUWDutyKindSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& ClmUWNo.equals(other.getClmUWNo())
			&& TabFeeMoney == other.getTabFeeMoney()
			&& StandPay == other.getStandPay()
			&& ClaimMoney == other.getClaimMoney()
			&& SocPay == other.getSocPay()
			&& OthPay == other.getOthPay()
			&& RealPay == other.getRealPay()
			&& DeclinePay == other.getDeclinePay()
			&& Remark.equals(other.getRemark())
			&& BackNo.equals(other.getBackNo());
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 1;
		}
		if( strFieldName.equals("ClmUWNo") ) {
			return 2;
		}
		if( strFieldName.equals("TabFeeMoney") ) {
			return 3;
		}
		if( strFieldName.equals("StandPay") ) {
			return 4;
		}
		if( strFieldName.equals("ClaimMoney") ) {
			return 5;
		}
		if( strFieldName.equals("SocPay") ) {
			return 6;
		}
		if( strFieldName.equals("OthPay") ) {
			return 7;
		}
		if( strFieldName.equals("RealPay") ) {
			return 8;
		}
		if( strFieldName.equals("DeclinePay") ) {
			return 9;
		}
		if( strFieldName.equals("Remark") ) {
			return 10;
		}
		if( strFieldName.equals("BackNo") ) {
			return 11;
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "GetDutyKind";
				break;
			case 2:
				strFieldName = "ClmUWNo";
				break;
			case 3:
				strFieldName = "TabFeeMoney";
				break;
			case 4:
				strFieldName = "StandPay";
				break;
			case 5:
				strFieldName = "ClaimMoney";
				break;
			case 6:
				strFieldName = "SocPay";
				break;
			case 7:
				strFieldName = "OthPay";
				break;
			case 8:
				strFieldName = "RealPay";
				break;
			case 9:
				strFieldName = "DeclinePay";
				break;
			case 10:
				strFieldName = "Remark";
				break;
			case 11:
				strFieldName = "BackNo";
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmUWNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TabFeeMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClaimMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SocPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OthPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DeclinePay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackNo") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
