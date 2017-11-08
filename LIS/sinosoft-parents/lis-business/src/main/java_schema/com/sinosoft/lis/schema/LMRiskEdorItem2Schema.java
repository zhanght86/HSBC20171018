

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LMRiskEdorItem2DB;

/*
 * <p>ClassName: LMRiskEdorItem2Schema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskEdorItem2Schema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 保全项目编码 */
	private String EdorCode;
	/** 保全项目名称 */
	private String EdorName;
	/** 保全申请对象 */
	private String AppObj;
	/** 冲减保费标记 */
	private String CutPremFlag;
	/** 变动标记 */
	private String ChgFlag;
	/** 改金额标记 */
	private String ChgValueFlag;
	/** 涉及计算 */
	private String CalFlag;
	/** 界面中是否显示项目明细 */
	private String NeedDetail;
	/** 保全计算时参考的时间间隔 */
	private String IntvType;
	/** 集体保全批单中是否需要打印保全清单 */
	private String GrpNeedList;
	/** 保全权限 */
	private String EdorPopedom;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskEdorItem2Schema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "RiskVer";
		pk[2] = "EdorCode";
		pk[3] = "AppObj";

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
		LMRiskEdorItem2Schema cloned = (LMRiskEdorItem2Schema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	public String getRiskName()
	{
		return RiskName;
	}
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	public String getEdorCode()
	{
		return EdorCode;
	}
	public void setEdorCode(String aEdorCode)
	{
		EdorCode = aEdorCode;
	}
	public String getEdorName()
	{
		return EdorName;
	}
	public void setEdorName(String aEdorName)
	{
		EdorName = aEdorName;
	}
	/**
	* C--总单(Contract)、G--分单(Group)、I--个单(Individual)
	*/
	public String getAppObj()
	{
		return AppObj;
	}
	public void setAppObj(String aAppObj)
	{
		AppObj = aAppObj;
	}
	/**
	* Y-- 冲减保费 N-- 不冲减保费
	*/
	public String getCutPremFlag()
	{
		return CutPremFlag;
	}
	public void setCutPremFlag(String aCutPremFlag)
	{
		CutPremFlag = aCutPremFlag;
	}
	/**
	* 1-- 只允许改小   2-- 只允许改大
	*/
	public String getChgFlag()
	{
		return ChgFlag;
	}
	public void setChgFlag(String aChgFlag)
	{
		ChgFlag = aChgFlag;
	}
	/**
	* N--不允许改金额  Y--允许改金额
	*/
	public String getChgValueFlag()
	{
		return ChgValueFlag;
	}
	public void setChgValueFlag(String aChgValueFlag)
	{
		ChgValueFlag = aChgValueFlag;
	}
	/**
	* Y-- 涉及；N-- 不涉及
	*/
	public String getCalFlag()
	{
		return CalFlag;
	}
	public void setCalFlag(String aCalFlag)
	{
		CalFlag = aCalFlag;
	}
	/**
	* 0 －－ 不需要项目明细<p>
	* 1 －－ 需要项目明细
	*/
	public String getNeedDetail()
	{
		return NeedDetail;
	}
	public void setNeedDetail(String aNeedDetail)
	{
		NeedDetail = aNeedDetail;
	}
	/**
	* Y －－ Year<p>
	* M －－ Month<p>
	* D －－ Day
	*/
	public String getIntvType()
	{
		return IntvType;
	}
	public void setIntvType(String aIntvType)
	{
		IntvType = aIntvType;
	}
	/**
	* Y －－ 需要打印<p>
	* N －－ 不需要打印
	*/
	public String getGrpNeedList()
	{
		return GrpNeedList;
	}
	public void setGrpNeedList(String aGrpNeedList)
	{
		GrpNeedList = aGrpNeedList;
	}
	/**
	* EdorPopedom	A	A级核保员<p>
	* EdorPopedom	B	B级核保员<p>
	* EdorPopedom	C	C级核保员<p>
	* EdorPopedom	D	D级核保员<p>
	* EdorPopedom	E	E级核保员<p>
	* EdorPopedom	F	F级核保员
	*/
	public String getEdorPopedom()
	{
		return EdorPopedom;
	}
	public void setEdorPopedom(String aEdorPopedom)
	{
		EdorPopedom = aEdorPopedom;
	}

	/**
	* 使用另外一个 LMRiskEdorItem2Schema 对象给 Schema 赋值
	* @param: aLMRiskEdorItem2Schema LMRiskEdorItem2Schema
	**/
	public void setSchema(LMRiskEdorItem2Schema aLMRiskEdorItem2Schema)
	{
		this.RiskCode = aLMRiskEdorItem2Schema.getRiskCode();
		this.RiskVer = aLMRiskEdorItem2Schema.getRiskVer();
		this.RiskName = aLMRiskEdorItem2Schema.getRiskName();
		this.EdorCode = aLMRiskEdorItem2Schema.getEdorCode();
		this.EdorName = aLMRiskEdorItem2Schema.getEdorName();
		this.AppObj = aLMRiskEdorItem2Schema.getAppObj();
		this.CutPremFlag = aLMRiskEdorItem2Schema.getCutPremFlag();
		this.ChgFlag = aLMRiskEdorItem2Schema.getChgFlag();
		this.ChgValueFlag = aLMRiskEdorItem2Schema.getChgValueFlag();
		this.CalFlag = aLMRiskEdorItem2Schema.getCalFlag();
		this.NeedDetail = aLMRiskEdorItem2Schema.getNeedDetail();
		this.IntvType = aLMRiskEdorItem2Schema.getIntvType();
		this.GrpNeedList = aLMRiskEdorItem2Schema.getGrpNeedList();
		this.EdorPopedom = aLMRiskEdorItem2Schema.getEdorPopedom();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			if( rs.getString("EdorCode") == null )
				this.EdorCode = null;
			else
				this.EdorCode = rs.getString("EdorCode").trim();

			if( rs.getString("EdorName") == null )
				this.EdorName = null;
			else
				this.EdorName = rs.getString("EdorName").trim();

			if( rs.getString("AppObj") == null )
				this.AppObj = null;
			else
				this.AppObj = rs.getString("AppObj").trim();

			if( rs.getString("CutPremFlag") == null )
				this.CutPremFlag = null;
			else
				this.CutPremFlag = rs.getString("CutPremFlag").trim();

			if( rs.getString("ChgFlag") == null )
				this.ChgFlag = null;
			else
				this.ChgFlag = rs.getString("ChgFlag").trim();

			if( rs.getString("ChgValueFlag") == null )
				this.ChgValueFlag = null;
			else
				this.ChgValueFlag = rs.getString("ChgValueFlag").trim();

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			if( rs.getString("NeedDetail") == null )
				this.NeedDetail = null;
			else
				this.NeedDetail = rs.getString("NeedDetail").trim();

			if( rs.getString("IntvType") == null )
				this.IntvType = null;
			else
				this.IntvType = rs.getString("IntvType").trim();

			if( rs.getString("GrpNeedList") == null )
				this.GrpNeedList = null;
			else
				this.GrpNeedList = rs.getString("GrpNeedList").trim();

			if( rs.getString("EdorPopedom") == null )
				this.EdorPopedom = null;
			else
				this.EdorPopedom = rs.getString("EdorPopedom").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMRiskEdorItem2表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskEdorItem2Schema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskEdorItem2Schema getSchema()
	{
		LMRiskEdorItem2Schema aLMRiskEdorItem2Schema = new LMRiskEdorItem2Schema();
		aLMRiskEdorItem2Schema.setSchema(this);
		return aLMRiskEdorItem2Schema;
	}

	public LMRiskEdorItem2DB getDB()
	{
		LMRiskEdorItem2DB aDBOper = new LMRiskEdorItem2DB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskEdorItem2描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppObj)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CutPremFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChgFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChgValueFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedDetail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IntvType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNeedList)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorPopedom));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskEdorItem2>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			EdorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			EdorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AppObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CutPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ChgFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ChgValueFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			NeedDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IntvType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			GrpNeedList = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			EdorPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskEdorItem2Schema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equalsIgnoreCase("EdorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorCode));
		}
		if (FCode.equalsIgnoreCase("EdorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorName));
		}
		if (FCode.equalsIgnoreCase("AppObj"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppObj));
		}
		if (FCode.equalsIgnoreCase("CutPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CutPremFlag));
		}
		if (FCode.equalsIgnoreCase("ChgFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgFlag));
		}
		if (FCode.equalsIgnoreCase("ChgValueFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgValueFlag));
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equalsIgnoreCase("NeedDetail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedDetail));
		}
		if (FCode.equalsIgnoreCase("IntvType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IntvType));
		}
		if (FCode.equalsIgnoreCase("GrpNeedList"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNeedList));
		}
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorPopedom));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(EdorCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(EdorName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AppObj);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CutPremFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ChgFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ChgValueFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(NeedDetail);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IntvType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(GrpNeedList);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(EdorPopedom);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equalsIgnoreCase("EdorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorCode = FValue.trim();
			}
			else
				EdorCode = null;
		}
		if (FCode.equalsIgnoreCase("EdorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorName = FValue.trim();
			}
			else
				EdorName = null;
		}
		if (FCode.equalsIgnoreCase("AppObj"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppObj = FValue.trim();
			}
			else
				AppObj = null;
		}
		if (FCode.equalsIgnoreCase("CutPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CutPremFlag = FValue.trim();
			}
			else
				CutPremFlag = null;
		}
		if (FCode.equalsIgnoreCase("ChgFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChgFlag = FValue.trim();
			}
			else
				ChgFlag = null;
		}
		if (FCode.equalsIgnoreCase("ChgValueFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChgValueFlag = FValue.trim();
			}
			else
				ChgValueFlag = null;
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFlag = FValue.trim();
			}
			else
				CalFlag = null;
		}
		if (FCode.equalsIgnoreCase("NeedDetail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedDetail = FValue.trim();
			}
			else
				NeedDetail = null;
		}
		if (FCode.equalsIgnoreCase("IntvType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IntvType = FValue.trim();
			}
			else
				IntvType = null;
		}
		if (FCode.equalsIgnoreCase("GrpNeedList"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNeedList = FValue.trim();
			}
			else
				GrpNeedList = null;
		}
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorPopedom = FValue.trim();
			}
			else
				EdorPopedom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskEdorItem2Schema other = (LMRiskEdorItem2Schema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& EdorCode.equals(other.getEdorCode())
			&& EdorName.equals(other.getEdorName())
			&& AppObj.equals(other.getAppObj())
			&& CutPremFlag.equals(other.getCutPremFlag())
			&& ChgFlag.equals(other.getChgFlag())
			&& ChgValueFlag.equals(other.getChgValueFlag())
			&& CalFlag.equals(other.getCalFlag())
			&& NeedDetail.equals(other.getNeedDetail())
			&& IntvType.equals(other.getIntvType())
			&& GrpNeedList.equals(other.getGrpNeedList())
			&& EdorPopedom.equals(other.getEdorPopedom());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 1;
		}
		if( strFieldName.equals("RiskName") ) {
			return 2;
		}
		if( strFieldName.equals("EdorCode") ) {
			return 3;
		}
		if( strFieldName.equals("EdorName") ) {
			return 4;
		}
		if( strFieldName.equals("AppObj") ) {
			return 5;
		}
		if( strFieldName.equals("CutPremFlag") ) {
			return 6;
		}
		if( strFieldName.equals("ChgFlag") ) {
			return 7;
		}
		if( strFieldName.equals("ChgValueFlag") ) {
			return 8;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 9;
		}
		if( strFieldName.equals("NeedDetail") ) {
			return 10;
		}
		if( strFieldName.equals("IntvType") ) {
			return 11;
		}
		if( strFieldName.equals("GrpNeedList") ) {
			return 12;
		}
		if( strFieldName.equals("EdorPopedom") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "RiskName";
				break;
			case 3:
				strFieldName = "EdorCode";
				break;
			case 4:
				strFieldName = "EdorName";
				break;
			case 5:
				strFieldName = "AppObj";
				break;
			case 6:
				strFieldName = "CutPremFlag";
				break;
			case 7:
				strFieldName = "ChgFlag";
				break;
			case 8:
				strFieldName = "ChgValueFlag";
				break;
			case 9:
				strFieldName = "CalFlag";
				break;
			case 10:
				strFieldName = "NeedDetail";
				break;
			case 11:
				strFieldName = "IntvType";
				break;
			case 12:
				strFieldName = "GrpNeedList";
				break;
			case 13:
				strFieldName = "EdorPopedom";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppObj") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CutPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgValueFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedDetail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IntvType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNeedList") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorPopedom") ) {
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
