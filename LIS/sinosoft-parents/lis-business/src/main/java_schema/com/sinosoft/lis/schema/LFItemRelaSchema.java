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
import com.sinosoft.lis.db.LFItemRelaDB;

/*
 * <p>ClassName: LFItemRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LFItemRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LFItemRelaSchema.class);

	// @Field
	/** 内部科目编码 */
	private String ItemCode;
	/** 科目名称 */
	private String ItemName;
	/** 外部科目编码 */
	private String OutItemCode;
	/** 上级内部科目编码 */
	private String UpItemCode;
	/** 科目级别 */
	private int ItemLevel;
	/** 公司类型 */
	private String CorpType;
	/** 类型 */
	private String ItemType;
	/** 是否是快报 */
	private String IsQuick;
	/** 是否是月报 */
	private String IsMon;
	/** 是否是季报 */
	private String IsQut;
	/** 是否是半年报 */
	private String IsHalYer;
	/** 是否是年报 */
	private String IsYear;
	/** 是否是叶子结点 */
	private String IsLeaf;
	/** 总分 */
	private String General;
	/** 层级 */
	private int Layer;
	/** 定义口径描述 */
	private String Description;
	/** 备注 */
	private String Remark;
	/** 是否上报标志 */
	private String OutputFlag;
	/** 上报数据的机构粒度 */
	private int ComFlag;
	/** 是否一级计算标志 */
	private String IsCalFlag;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LFItemRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ItemCode";

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
		LFItemRelaSchema cloned = (LFItemRelaSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getItemCode()
	{
		return ItemCode;
	}
	public void setItemCode(String aItemCode)
	{
		ItemCode = aItemCode;
	}
	public String getItemName()
	{
		return ItemName;
	}
	public void setItemName(String aItemName)
	{
		ItemName = aItemName;
	}
	public String getOutItemCode()
	{
		return OutItemCode;
	}
	public void setOutItemCode(String aOutItemCode)
	{
		OutItemCode = aOutItemCode;
	}
	public String getUpItemCode()
	{
		return UpItemCode;
	}
	public void setUpItemCode(String aUpItemCode)
	{
		UpItemCode = aUpItemCode;
	}
	/**
	* 由保监会提供的科目表中获取如（１级到７级）也可以更低级或更高级。有可变动性
	*/
	public int getItemLevel()
	{
		return ItemLevel;
	}
	public void setItemLevel(int aItemLevel)
	{
		ItemLevel = aItemLevel;
	}
	public void setItemLevel(String aItemLevel)
	{
		if (aItemLevel != null && !aItemLevel.equals(""))
		{
			Integer tInteger = new Integer(aItemLevel);
			int i = tInteger.intValue();
			ItemLevel = i;
		}
	}

	/**
	* 1-产<p>
	* 2-寿<p>
	* 3-再<p>
	* 4-集
	*/
	public String getCorpType()
	{
		return CorpType;
	}
	public void setCorpType(String aCorpType)
	{
		CorpType = aCorpType;
	}
	/**
	* 01-资产<p>
	* 02-负债<p>
	* 03-权益<p>
	* 04-损益<p>
	* 05-现金流<p>
	* 06-统计<p>
	* 07-资金
	*/
	public String getItemType()
	{
		return ItemType;
	}
	public void setItemType(String aItemType)
	{
		ItemType = aItemType;
	}
	/**
	* 0--不是<p>
	* 1--是
	*/
	public String getIsQuick()
	{
		return IsQuick;
	}
	public void setIsQuick(String aIsQuick)
	{
		IsQuick = aIsQuick;
	}
	/**
	* 0--不是<p>
	* 1--是
	*/
	public String getIsMon()
	{
		return IsMon;
	}
	public void setIsMon(String aIsMon)
	{
		IsMon = aIsMon;
	}
	/**
	* 0--不是<p>
	* 1--是
	*/
	public String getIsQut()
	{
		return IsQut;
	}
	public void setIsQut(String aIsQut)
	{
		IsQut = aIsQut;
	}
	/**
	* 0--不是<p>
	* 1--是
	*/
	public String getIsHalYer()
	{
		return IsHalYer;
	}
	public void setIsHalYer(String aIsHalYer)
	{
		IsHalYer = aIsHalYer;
	}
	/**
	* 0--不是<p>
	* 1--是
	*/
	public String getIsYear()
	{
		return IsYear;
	}
	public void setIsYear(String aIsYear)
	{
		IsYear = aIsYear;
	}
	/**
	* 0--不是<p>
	* 1--是
	*/
	public String getIsLeaf()
	{
		return IsLeaf;
	}
	public void setIsLeaf(String aIsLeaf)
	{
		IsLeaf = aIsLeaf;
	}
	public String getGeneral()
	{
		return General;
	}
	public void setGeneral(String aGeneral)
	{
		General = aGeneral;
	}
	/**
	* 由保监会提供的科目表中获取如1、2、9
	*/
	public int getLayer()
	{
		return Layer;
	}
	public void setLayer(int aLayer)
	{
		Layer = aLayer;
	}
	public void setLayer(String aLayer)
	{
		if (aLayer != null && !aLayer.equals(""))
		{
			Integer tInteger = new Integer(aLayer);
			int i = tInteger.intValue();
			Layer = i;
		}
	}

	public String getDescription()
	{
		return Description;
	}
	public void setDescription(String aDescription)
	{
		Description = aDescription;
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
	* 0--不上报<p>
	* 1--上报
	*/
	public String getOutputFlag()
	{
		return OutputFlag;
	}
	public void setOutputFlag(String aOutputFlag)
	{
		OutputFlag = aOutputFlag;
	}
	/**
	* 保险公司提取数据的粒度：<p>
	* 1--数据明细到总公司，<p>
	* 2--数据明细到分公司，<p>
	* 3--明细到中心支公司，<p>
	* 4--明细到支公司
	*/
	public int getComFlag()
	{
		return ComFlag;
	}
	public void setComFlag(int aComFlag)
	{
		ComFlag = aComFlag;
	}
	public void setComFlag(String aComFlag)
	{
		if (aComFlag != null && !aComFlag.equals(""))
		{
			Integer tInteger = new Integer(aComFlag);
			int i = tInteger.intValue();
			ComFlag = i;
		}
	}

	/**
	* 0--不计算<p>
	* 1--计算
	*/
	public String getIsCalFlag()
	{
		return IsCalFlag;
	}
	public void setIsCalFlag(String aIsCalFlag)
	{
		IsCalFlag = aIsCalFlag;
	}

	/**
	* 使用另外一个 LFItemRelaSchema 对象给 Schema 赋值
	* @param: aLFItemRelaSchema LFItemRelaSchema
	**/
	public void setSchema(LFItemRelaSchema aLFItemRelaSchema)
	{
		this.ItemCode = aLFItemRelaSchema.getItemCode();
		this.ItemName = aLFItemRelaSchema.getItemName();
		this.OutItemCode = aLFItemRelaSchema.getOutItemCode();
		this.UpItemCode = aLFItemRelaSchema.getUpItemCode();
		this.ItemLevel = aLFItemRelaSchema.getItemLevel();
		this.CorpType = aLFItemRelaSchema.getCorpType();
		this.ItemType = aLFItemRelaSchema.getItemType();
		this.IsQuick = aLFItemRelaSchema.getIsQuick();
		this.IsMon = aLFItemRelaSchema.getIsMon();
		this.IsQut = aLFItemRelaSchema.getIsQut();
		this.IsHalYer = aLFItemRelaSchema.getIsHalYer();
		this.IsYear = aLFItemRelaSchema.getIsYear();
		this.IsLeaf = aLFItemRelaSchema.getIsLeaf();
		this.General = aLFItemRelaSchema.getGeneral();
		this.Layer = aLFItemRelaSchema.getLayer();
		this.Description = aLFItemRelaSchema.getDescription();
		this.Remark = aLFItemRelaSchema.getRemark();
		this.OutputFlag = aLFItemRelaSchema.getOutputFlag();
		this.ComFlag = aLFItemRelaSchema.getComFlag();
		this.IsCalFlag = aLFItemRelaSchema.getIsCalFlag();
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
			if( rs.getString("ItemCode") == null )
				this.ItemCode = null;
			else
				this.ItemCode = rs.getString("ItemCode").trim();

			if( rs.getString("ItemName") == null )
				this.ItemName = null;
			else
				this.ItemName = rs.getString("ItemName").trim();

			if( rs.getString("OutItemCode") == null )
				this.OutItemCode = null;
			else
				this.OutItemCode = rs.getString("OutItemCode").trim();

			if( rs.getString("UpItemCode") == null )
				this.UpItemCode = null;
			else
				this.UpItemCode = rs.getString("UpItemCode").trim();

			this.ItemLevel = rs.getInt("ItemLevel");
			if( rs.getString("CorpType") == null )
				this.CorpType = null;
			else
				this.CorpType = rs.getString("CorpType").trim();

			if( rs.getString("ItemType") == null )
				this.ItemType = null;
			else
				this.ItemType = rs.getString("ItemType").trim();

			if( rs.getString("IsQuick") == null )
				this.IsQuick = null;
			else
				this.IsQuick = rs.getString("IsQuick").trim();

			if( rs.getString("IsMon") == null )
				this.IsMon = null;
			else
				this.IsMon = rs.getString("IsMon").trim();

			if( rs.getString("IsQut") == null )
				this.IsQut = null;
			else
				this.IsQut = rs.getString("IsQut").trim();

			if( rs.getString("IsHalYer") == null )
				this.IsHalYer = null;
			else
				this.IsHalYer = rs.getString("IsHalYer").trim();

			if( rs.getString("IsYear") == null )
				this.IsYear = null;
			else
				this.IsYear = rs.getString("IsYear").trim();

			if( rs.getString("IsLeaf") == null )
				this.IsLeaf = null;
			else
				this.IsLeaf = rs.getString("IsLeaf").trim();

			if( rs.getString("General") == null )
				this.General = null;
			else
				this.General = rs.getString("General").trim();

			this.Layer = rs.getInt("Layer");
			if( rs.getString("Description") == null )
				this.Description = null;
			else
				this.Description = rs.getString("Description").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("OutputFlag") == null )
				this.OutputFlag = null;
			else
				this.OutputFlag = rs.getString("OutputFlag").trim();

			this.ComFlag = rs.getInt("ComFlag");
			if( rs.getString("IsCalFlag") == null )
				this.IsCalFlag = null;
			else
				this.IsCalFlag = rs.getString("IsCalFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LFItemRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFItemRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LFItemRelaSchema getSchema()
	{
		LFItemRelaSchema aLFItemRelaSchema = new LFItemRelaSchema();
		aLFItemRelaSchema.setSchema(this);
		return aLFItemRelaSchema;
	}

	public LFItemRelaDB getDB()
	{
		LFItemRelaDB aDBOper = new LFItemRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFItemRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ItemLevel));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CorpType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsQuick)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsMon)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsQut)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsHalYer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsYear)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsLeaf)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(General)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Layer));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Description)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutputFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ComFlag));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsCalFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFItemRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ItemName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OutItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			UpItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ItemLevel= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			CorpType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			IsQuick = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			IsMon = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			IsQut = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IsHalYer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IsYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			IsLeaf = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			General = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Layer= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			Description = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			OutputFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ComFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			IsCalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFItemRelaSchema";
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
		if (FCode.equalsIgnoreCase("ItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemCode));
		}
		if (FCode.equalsIgnoreCase("ItemName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemName));
		}
		if (FCode.equalsIgnoreCase("OutItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutItemCode));
		}
		if (FCode.equalsIgnoreCase("UpItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpItemCode));
		}
		if (FCode.equalsIgnoreCase("ItemLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemLevel));
		}
		if (FCode.equalsIgnoreCase("CorpType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CorpType));
		}
		if (FCode.equalsIgnoreCase("ItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemType));
		}
		if (FCode.equalsIgnoreCase("IsQuick"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsQuick));
		}
		if (FCode.equalsIgnoreCase("IsMon"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsMon));
		}
		if (FCode.equalsIgnoreCase("IsQut"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsQut));
		}
		if (FCode.equalsIgnoreCase("IsHalYer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsHalYer));
		}
		if (FCode.equalsIgnoreCase("IsYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsYear));
		}
		if (FCode.equalsIgnoreCase("IsLeaf"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsLeaf));
		}
		if (FCode.equalsIgnoreCase("General"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(General));
		}
		if (FCode.equalsIgnoreCase("Layer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Layer));
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Description));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("OutputFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutputFlag));
		}
		if (FCode.equalsIgnoreCase("ComFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComFlag));
		}
		if (FCode.equalsIgnoreCase("IsCalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsCalFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ItemCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ItemName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OutItemCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(UpItemCode);
				break;
			case 4:
				strFieldValue = String.valueOf(ItemLevel);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CorpType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ItemType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(IsQuick);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(IsMon);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(IsQut);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IsHalYer);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IsYear);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(IsLeaf);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(General);
				break;
			case 14:
				strFieldValue = String.valueOf(Layer);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Description);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(OutputFlag);
				break;
			case 18:
				strFieldValue = String.valueOf(ComFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(IsCalFlag);
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

		if (FCode.equalsIgnoreCase("ItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemCode = FValue.trim();
			}
			else
				ItemCode = null;
		}
		if (FCode.equalsIgnoreCase("ItemName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemName = FValue.trim();
			}
			else
				ItemName = null;
		}
		if (FCode.equalsIgnoreCase("OutItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutItemCode = FValue.trim();
			}
			else
				OutItemCode = null;
		}
		if (FCode.equalsIgnoreCase("UpItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpItemCode = FValue.trim();
			}
			else
				UpItemCode = null;
		}
		if (FCode.equalsIgnoreCase("ItemLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ItemLevel = i;
			}
		}
		if (FCode.equalsIgnoreCase("CorpType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CorpType = FValue.trim();
			}
			else
				CorpType = null;
		}
		if (FCode.equalsIgnoreCase("ItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemType = FValue.trim();
			}
			else
				ItemType = null;
		}
		if (FCode.equalsIgnoreCase("IsQuick"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsQuick = FValue.trim();
			}
			else
				IsQuick = null;
		}
		if (FCode.equalsIgnoreCase("IsMon"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsMon = FValue.trim();
			}
			else
				IsMon = null;
		}
		if (FCode.equalsIgnoreCase("IsQut"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsQut = FValue.trim();
			}
			else
				IsQut = null;
		}
		if (FCode.equalsIgnoreCase("IsHalYer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsHalYer = FValue.trim();
			}
			else
				IsHalYer = null;
		}
		if (FCode.equalsIgnoreCase("IsYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsYear = FValue.trim();
			}
			else
				IsYear = null;
		}
		if (FCode.equalsIgnoreCase("IsLeaf"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsLeaf = FValue.trim();
			}
			else
				IsLeaf = null;
		}
		if (FCode.equalsIgnoreCase("General"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				General = FValue.trim();
			}
			else
				General = null;
		}
		if (FCode.equalsIgnoreCase("Layer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Layer = i;
			}
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Description = FValue.trim();
			}
			else
				Description = null;
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
		if (FCode.equalsIgnoreCase("OutputFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutputFlag = FValue.trim();
			}
			else
				OutputFlag = null;
		}
		if (FCode.equalsIgnoreCase("ComFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ComFlag = i;
			}
		}
		if (FCode.equalsIgnoreCase("IsCalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsCalFlag = FValue.trim();
			}
			else
				IsCalFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LFItemRelaSchema other = (LFItemRelaSchema)otherObject;
		return
			ItemCode.equals(other.getItemCode())
			&& ItemName.equals(other.getItemName())
			&& OutItemCode.equals(other.getOutItemCode())
			&& UpItemCode.equals(other.getUpItemCode())
			&& ItemLevel == other.getItemLevel()
			&& CorpType.equals(other.getCorpType())
			&& ItemType.equals(other.getItemType())
			&& IsQuick.equals(other.getIsQuick())
			&& IsMon.equals(other.getIsMon())
			&& IsQut.equals(other.getIsQut())
			&& IsHalYer.equals(other.getIsHalYer())
			&& IsYear.equals(other.getIsYear())
			&& IsLeaf.equals(other.getIsLeaf())
			&& General.equals(other.getGeneral())
			&& Layer == other.getLayer()
			&& Description.equals(other.getDescription())
			&& Remark.equals(other.getRemark())
			&& OutputFlag.equals(other.getOutputFlag())
			&& ComFlag == other.getComFlag()
			&& IsCalFlag.equals(other.getIsCalFlag());
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
		if( strFieldName.equals("ItemCode") ) {
			return 0;
		}
		if( strFieldName.equals("ItemName") ) {
			return 1;
		}
		if( strFieldName.equals("OutItemCode") ) {
			return 2;
		}
		if( strFieldName.equals("UpItemCode") ) {
			return 3;
		}
		if( strFieldName.equals("ItemLevel") ) {
			return 4;
		}
		if( strFieldName.equals("CorpType") ) {
			return 5;
		}
		if( strFieldName.equals("ItemType") ) {
			return 6;
		}
		if( strFieldName.equals("IsQuick") ) {
			return 7;
		}
		if( strFieldName.equals("IsMon") ) {
			return 8;
		}
		if( strFieldName.equals("IsQut") ) {
			return 9;
		}
		if( strFieldName.equals("IsHalYer") ) {
			return 10;
		}
		if( strFieldName.equals("IsYear") ) {
			return 11;
		}
		if( strFieldName.equals("IsLeaf") ) {
			return 12;
		}
		if( strFieldName.equals("General") ) {
			return 13;
		}
		if( strFieldName.equals("Layer") ) {
			return 14;
		}
		if( strFieldName.equals("Description") ) {
			return 15;
		}
		if( strFieldName.equals("Remark") ) {
			return 16;
		}
		if( strFieldName.equals("OutputFlag") ) {
			return 17;
		}
		if( strFieldName.equals("ComFlag") ) {
			return 18;
		}
		if( strFieldName.equals("IsCalFlag") ) {
			return 19;
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
				strFieldName = "ItemCode";
				break;
			case 1:
				strFieldName = "ItemName";
				break;
			case 2:
				strFieldName = "OutItemCode";
				break;
			case 3:
				strFieldName = "UpItemCode";
				break;
			case 4:
				strFieldName = "ItemLevel";
				break;
			case 5:
				strFieldName = "CorpType";
				break;
			case 6:
				strFieldName = "ItemType";
				break;
			case 7:
				strFieldName = "IsQuick";
				break;
			case 8:
				strFieldName = "IsMon";
				break;
			case 9:
				strFieldName = "IsQut";
				break;
			case 10:
				strFieldName = "IsHalYer";
				break;
			case 11:
				strFieldName = "IsYear";
				break;
			case 12:
				strFieldName = "IsLeaf";
				break;
			case 13:
				strFieldName = "General";
				break;
			case 14:
				strFieldName = "Layer";
				break;
			case 15:
				strFieldName = "Description";
				break;
			case 16:
				strFieldName = "Remark";
				break;
			case 17:
				strFieldName = "OutputFlag";
				break;
			case 18:
				strFieldName = "ComFlag";
				break;
			case 19:
				strFieldName = "IsCalFlag";
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
		if( strFieldName.equals("ItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemLevel") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CorpType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsQuick") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsMon") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsQut") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsHalYer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsLeaf") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("General") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Layer") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Description") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutputFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IsCalFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
