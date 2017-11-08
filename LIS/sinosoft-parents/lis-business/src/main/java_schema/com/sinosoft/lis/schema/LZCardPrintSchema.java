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
import com.sinosoft.lis.db.LZCardPrintDB;

/*
 * <p>ClassName: LZCardPrintSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LZCardPrintSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LZCardPrintSchema.class);

	// @Field
	/** 印刷批次号 */
	private String PrtNo;
	/** 计划类型 */
	private String PlanType;
	/** 单证编码 */
	private String CertifyCode;
	/** 起始号 */
	private String StartNo;
	/** 终止号 */
	private String EndNo;
	/** 数量 */
	private int SumCount;
	/** 总公司印刷数量 */
	private int ParentNum;
	/** 使用截止日期 */
	private Date MaxDate;
	/** 印刷厂名称 */
	private String Printery;
	/** 印刷厂电话 */
	private String Phone;
	/** 印刷厂联系人 */
	private String LinkMan;
	/** 单证印刷价格 */
	private double CertifyPrice;
	/** 管理机构 */
	private String ManageCom;
	/** 计划汇总人 */
	private String InputMan;
	/** 计划汇总日期 */
	private Date InputDate;
	/** 计划汇总操作员 */
	private String InputOperator;
	/** 印刷人 */
	private String PrintMan;
	/** 印刷日期 */
	private Date PrintDate;
	/** 印刷操作员 */
	private String PrintOperator;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 状态 */
	private String State;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LZCardPrintSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PrtNo";

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
		LZCardPrintSchema cloned = (LZCardPrintSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	public String getPlanType()
	{
		return PlanType;
	}
	public void setPlanType(String aPlanType)
	{
		PlanType = aPlanType;
	}
	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getStartNo()
	{
		return StartNo;
	}
	public void setStartNo(String aStartNo)
	{
		StartNo = aStartNo;
	}
	public String getEndNo()
	{
		return EndNo;
	}
	public void setEndNo(String aEndNo)
	{
		EndNo = aEndNo;
	}
	public int getSumCount()
	{
		return SumCount;
	}
	public void setSumCount(int aSumCount)
	{
		SumCount = aSumCount;
	}
	public void setSumCount(String aSumCount)
	{
		if (aSumCount != null && !aSumCount.equals(""))
		{
			Integer tInteger = new Integer(aSumCount);
			int i = tInteger.intValue();
			SumCount = i;
		}
	}

	public int getParentNum()
	{
		return ParentNum;
	}
	public void setParentNum(int aParentNum)
	{
		ParentNum = aParentNum;
	}
	public void setParentNum(String aParentNum)
	{
		if (aParentNum != null && !aParentNum.equals(""))
		{
			Integer tInteger = new Integer(aParentNum);
			int i = tInteger.intValue();
			ParentNum = i;
		}
	}

	public String getMaxDate()
	{
		if( MaxDate != null )
			return fDate.getString(MaxDate);
		else
			return null;
	}
	public void setMaxDate(Date aMaxDate)
	{
		MaxDate = aMaxDate;
	}
	public void setMaxDate(String aMaxDate)
	{
		if (aMaxDate != null && !aMaxDate.equals("") )
		{
			MaxDate = fDate.getDate( aMaxDate );
		}
		else
			MaxDate = null;
	}

	public String getPrintery()
	{
		return Printery;
	}
	public void setPrintery(String aPrintery)
	{
		Printery = aPrintery;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		Phone = aPhone;
	}
	public String getLinkMan()
	{
		return LinkMan;
	}
	public void setLinkMan(String aLinkMan)
	{
		LinkMan = aLinkMan;
	}
	public double getCertifyPrice()
	{
		return CertifyPrice;
	}
	public void setCertifyPrice(double aCertifyPrice)
	{
		CertifyPrice = aCertifyPrice;
	}
	public void setCertifyPrice(String aCertifyPrice)
	{
		if (aCertifyPrice != null && !aCertifyPrice.equals(""))
		{
			Double tDouble = new Double(aCertifyPrice);
			double d = tDouble.doubleValue();
			CertifyPrice = d;
		}
	}

	/**
	* 86指总公司印刷<p>
	* 8611等其他指分公司授权印刷
	*/
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getInputMan()
	{
		return InputMan;
	}
	public void setInputMan(String aInputMan)
	{
		InputMan = aInputMan;
	}
	public String getInputDate()
	{
		if( InputDate != null )
			return fDate.getString(InputDate);
		else
			return null;
	}
	public void setInputDate(Date aInputDate)
	{
		InputDate = aInputDate;
	}
	public void setInputDate(String aInputDate)
	{
		if (aInputDate != null && !aInputDate.equals("") )
		{
			InputDate = fDate.getDate( aInputDate );
		}
		else
			InputDate = null;
	}

	public String getInputOperator()
	{
		return InputOperator;
	}
	public void setInputOperator(String aInputOperator)
	{
		InputOperator = aInputOperator;
	}
	public String getPrintMan()
	{
		return PrintMan;
	}
	public void setPrintMan(String aPrintMan)
	{
		PrintMan = aPrintMan;
	}
	public String getPrintDate()
	{
		if( PrintDate != null )
			return fDate.getString(PrintDate);
		else
			return null;
	}
	public void setPrintDate(Date aPrintDate)
	{
		PrintDate = aPrintDate;
	}
	public void setPrintDate(String aPrintDate)
	{
		if (aPrintDate != null && !aPrintDate.equals("") )
		{
			PrintDate = fDate.getDate( aPrintDate );
		}
		else
			PrintDate = null;
	}

	public String getPrintOperator()
	{
		return PrintOperator;
	}
	public void setPrintOperator(String aPrintOperator)
	{
		PrintOperator = aPrintOperator;
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
	* 0---计划汇总，计划汇总后生成待印刷数据后的状态<p>
	* 1---印刷完成，可以入库的单证状态。<p>
	* 2---入库完成，入库后单证状态。
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}

	/**
	* 使用另外一个 LZCardPrintSchema 对象给 Schema 赋值
	* @param: aLZCardPrintSchema LZCardPrintSchema
	**/
	public void setSchema(LZCardPrintSchema aLZCardPrintSchema)
	{
		this.PrtNo = aLZCardPrintSchema.getPrtNo();
		this.PlanType = aLZCardPrintSchema.getPlanType();
		this.CertifyCode = aLZCardPrintSchema.getCertifyCode();
		this.StartNo = aLZCardPrintSchema.getStartNo();
		this.EndNo = aLZCardPrintSchema.getEndNo();
		this.SumCount = aLZCardPrintSchema.getSumCount();
		this.ParentNum = aLZCardPrintSchema.getParentNum();
		this.MaxDate = fDate.getDate( aLZCardPrintSchema.getMaxDate());
		this.Printery = aLZCardPrintSchema.getPrintery();
		this.Phone = aLZCardPrintSchema.getPhone();
		this.LinkMan = aLZCardPrintSchema.getLinkMan();
		this.CertifyPrice = aLZCardPrintSchema.getCertifyPrice();
		this.ManageCom = aLZCardPrintSchema.getManageCom();
		this.InputMan = aLZCardPrintSchema.getInputMan();
		this.InputDate = fDate.getDate( aLZCardPrintSchema.getInputDate());
		this.InputOperator = aLZCardPrintSchema.getInputOperator();
		this.PrintMan = aLZCardPrintSchema.getPrintMan();
		this.PrintDate = fDate.getDate( aLZCardPrintSchema.getPrintDate());
		this.PrintOperator = aLZCardPrintSchema.getPrintOperator();
		this.ModifyDate = fDate.getDate( aLZCardPrintSchema.getModifyDate());
		this.ModifyTime = aLZCardPrintSchema.getModifyTime();
		this.State = aLZCardPrintSchema.getState();
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
			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("PlanType") == null )
				this.PlanType = null;
			else
				this.PlanType = rs.getString("PlanType").trim();

			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("StartNo") == null )
				this.StartNo = null;
			else
				this.StartNo = rs.getString("StartNo").trim();

			if( rs.getString("EndNo") == null )
				this.EndNo = null;
			else
				this.EndNo = rs.getString("EndNo").trim();

			this.SumCount = rs.getInt("SumCount");
			this.ParentNum = rs.getInt("ParentNum");
			this.MaxDate = rs.getDate("MaxDate");
			if( rs.getString("Printery") == null )
				this.Printery = null;
			else
				this.Printery = rs.getString("Printery").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("LinkMan") == null )
				this.LinkMan = null;
			else
				this.LinkMan = rs.getString("LinkMan").trim();

			this.CertifyPrice = rs.getDouble("CertifyPrice");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("InputMan") == null )
				this.InputMan = null;
			else
				this.InputMan = rs.getString("InputMan").trim();

			this.InputDate = rs.getDate("InputDate");
			if( rs.getString("InputOperator") == null )
				this.InputOperator = null;
			else
				this.InputOperator = rs.getString("InputOperator").trim();

			if( rs.getString("PrintMan") == null )
				this.PrintMan = null;
			else
				this.PrintMan = rs.getString("PrintMan").trim();

			this.PrintDate = rs.getDate("PrintDate");
			if( rs.getString("PrintOperator") == null )
				this.PrintOperator = null;
			else
				this.PrintOperator = rs.getString("PrintOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LZCardPrint表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCardPrintSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LZCardPrintSchema getSchema()
	{
		LZCardPrintSchema aLZCardPrintSchema = new LZCardPrintSchema();
		aLZCardPrintSchema.setSchema(this);
		return aLZCardPrintSchema;
	}

	public LZCardPrintDB getDB()
	{
		LZCardPrintDB aDBOper = new LZCardPrintDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCardPrint描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParentNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MaxDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Printery)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CertifyPrice));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PrintDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCardPrint>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StartNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			EndNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SumCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			ParentNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			MaxDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			Printery = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			LinkMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CertifyPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InputMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			InputOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PrintMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			PrintDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			PrintOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCardPrintSchema";
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
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanType));
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("StartNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartNo));
		}
		if (FCode.equalsIgnoreCase("EndNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndNo));
		}
		if (FCode.equalsIgnoreCase("SumCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumCount));
		}
		if (FCode.equalsIgnoreCase("ParentNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParentNum));
		}
		if (FCode.equalsIgnoreCase("MaxDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMaxDate()));
		}
		if (FCode.equalsIgnoreCase("Printery"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Printery));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan));
		}
		if (FCode.equalsIgnoreCase("CertifyPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyPrice));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("InputMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputMan));
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
		}
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputOperator));
		}
		if (FCode.equalsIgnoreCase("PrintMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintMan));
		}
		if (FCode.equalsIgnoreCase("PrintDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPrintDate()));
		}
		if (FCode.equalsIgnoreCase("PrintOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintOperator));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PlanType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(StartNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(EndNo);
				break;
			case 5:
				strFieldValue = String.valueOf(SumCount);
				break;
			case 6:
				strFieldValue = String.valueOf(ParentNum);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMaxDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Printery);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(LinkMan);
				break;
			case 11:
				strFieldValue = String.valueOf(CertifyPrice);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InputMan);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(InputOperator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PrintMan);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPrintDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PrintOperator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(State);
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

		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
		}
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanType = FValue.trim();
			}
			else
				PlanType = null;
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
		}
		if (FCode.equalsIgnoreCase("StartNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartNo = FValue.trim();
			}
			else
				StartNo = null;
		}
		if (FCode.equalsIgnoreCase("EndNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndNo = FValue.trim();
			}
			else
				EndNo = null;
		}
		if (FCode.equalsIgnoreCase("SumCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SumCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("ParentNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ParentNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("MaxDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MaxDate = fDate.getDate( FValue );
			}
			else
				MaxDate = null;
		}
		if (FCode.equalsIgnoreCase("Printery"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Printery = FValue.trim();
			}
			else
				Printery = null;
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkMan = FValue.trim();
			}
			else
				LinkMan = null;
		}
		if (FCode.equalsIgnoreCase("CertifyPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CertifyPrice = d;
			}
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
		if (FCode.equalsIgnoreCase("InputMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputMan = FValue.trim();
			}
			else
				InputMan = null;
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InputDate = fDate.getDate( FValue );
			}
			else
				InputDate = null;
		}
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputOperator = FValue.trim();
			}
			else
				InputOperator = null;
		}
		if (FCode.equalsIgnoreCase("PrintMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintMan = FValue.trim();
			}
			else
				PrintMan = null;
		}
		if (FCode.equalsIgnoreCase("PrintDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PrintDate = fDate.getDate( FValue );
			}
			else
				PrintDate = null;
		}
		if (FCode.equalsIgnoreCase("PrintOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintOperator = FValue.trim();
			}
			else
				PrintOperator = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LZCardPrintSchema other = (LZCardPrintSchema)otherObject;
		return
			PrtNo.equals(other.getPrtNo())
			&& PlanType.equals(other.getPlanType())
			&& CertifyCode.equals(other.getCertifyCode())
			&& StartNo.equals(other.getStartNo())
			&& EndNo.equals(other.getEndNo())
			&& SumCount == other.getSumCount()
			&& ParentNum == other.getParentNum()
			&& fDate.getString(MaxDate).equals(other.getMaxDate())
			&& Printery.equals(other.getPrintery())
			&& Phone.equals(other.getPhone())
			&& LinkMan.equals(other.getLinkMan())
			&& CertifyPrice == other.getCertifyPrice()
			&& ManageCom.equals(other.getManageCom())
			&& InputMan.equals(other.getInputMan())
			&& fDate.getString(InputDate).equals(other.getInputDate())
			&& InputOperator.equals(other.getInputOperator())
			&& PrintMan.equals(other.getPrintMan())
			&& fDate.getString(PrintDate).equals(other.getPrintDate())
			&& PrintOperator.equals(other.getPrintOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& State.equals(other.getState());
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
		if( strFieldName.equals("PrtNo") ) {
			return 0;
		}
		if( strFieldName.equals("PlanType") ) {
			return 1;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return 2;
		}
		if( strFieldName.equals("StartNo") ) {
			return 3;
		}
		if( strFieldName.equals("EndNo") ) {
			return 4;
		}
		if( strFieldName.equals("SumCount") ) {
			return 5;
		}
		if( strFieldName.equals("ParentNum") ) {
			return 6;
		}
		if( strFieldName.equals("MaxDate") ) {
			return 7;
		}
		if( strFieldName.equals("Printery") ) {
			return 8;
		}
		if( strFieldName.equals("Phone") ) {
			return 9;
		}
		if( strFieldName.equals("LinkMan") ) {
			return 10;
		}
		if( strFieldName.equals("CertifyPrice") ) {
			return 11;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 12;
		}
		if( strFieldName.equals("InputMan") ) {
			return 13;
		}
		if( strFieldName.equals("InputDate") ) {
			return 14;
		}
		if( strFieldName.equals("InputOperator") ) {
			return 15;
		}
		if( strFieldName.equals("PrintMan") ) {
			return 16;
		}
		if( strFieldName.equals("PrintDate") ) {
			return 17;
		}
		if( strFieldName.equals("PrintOperator") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 20;
		}
		if( strFieldName.equals("State") ) {
			return 21;
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
				strFieldName = "PrtNo";
				break;
			case 1:
				strFieldName = "PlanType";
				break;
			case 2:
				strFieldName = "CertifyCode";
				break;
			case 3:
				strFieldName = "StartNo";
				break;
			case 4:
				strFieldName = "EndNo";
				break;
			case 5:
				strFieldName = "SumCount";
				break;
			case 6:
				strFieldName = "ParentNum";
				break;
			case 7:
				strFieldName = "MaxDate";
				break;
			case 8:
				strFieldName = "Printery";
				break;
			case 9:
				strFieldName = "Phone";
				break;
			case 10:
				strFieldName = "LinkMan";
				break;
			case 11:
				strFieldName = "CertifyPrice";
				break;
			case 12:
				strFieldName = "ManageCom";
				break;
			case 13:
				strFieldName = "InputMan";
				break;
			case 14:
				strFieldName = "InputDate";
				break;
			case 15:
				strFieldName = "InputOperator";
				break;
			case 16:
				strFieldName = "PrintMan";
				break;
			case 17:
				strFieldName = "PrintDate";
				break;
			case 18:
				strFieldName = "PrintOperator";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
				strFieldName = "ModifyTime";
				break;
			case 21:
				strFieldName = "State";
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
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ParentNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MaxDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Printery") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PrintOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
