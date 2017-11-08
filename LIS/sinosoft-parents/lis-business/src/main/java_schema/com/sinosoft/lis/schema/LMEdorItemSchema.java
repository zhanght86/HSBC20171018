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
import com.sinosoft.lis.db.LMEdorItemDB;

/*
 * <p>ClassName: LMEdorItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LMEdorItemSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMEdorItemSchema.class);
	// @Field
	/** 保全项目编码 */
	private String EdorCode;
	/** 保全项目名称 */
	private String EdorName;
	/** 申请对象类型 */
	private String AppObj;
	/** 页面展示层次标记 */
	private String DisplayFlag;
	/** 是否需要重算 */
	private String CalFlag;
	/** 界面中是否显示项目明细 */
	private String NeedDetail;
	/** 是否需要打印保全清单 */
	private String GrpNeedList;
	/** 保全权限 */
	private String EdorPopedom;
	/** 保全项目限制 */
	private String EdorConstraints;
	/** 密码校验标识 */
	private String PwdFlag;
	/** 金额不足时处理方式 */
	private String AccBalaFlag;
	/** 卖出时价格处理 */
	private String PriceSellFlag;
	/** 买入时价格处理 */
	private String PriceBuyFlag;
	/** 同一投资账户转换处理方式 */
	private String SameAccFlag;
	/** 保全项目级别 */
	private String EdorLevel;
	/** 保全项目类型 */
	private String EdorClass;
	/** 保全操作节点 */
	private String ActivityID;
	/** 是否需要录入生效日期 */
	private String ValDateFlag;
	/** 是否单独操作 */
	private String AloneFlag;
	/** 是否需要核保 */
	private String UWFlag;
	/** 保全描述 */
	private String EdorDesc;
	/** 应备资料 */
	private String EdorMaterial;
	/** 注意事项 */
	private String MattersNeedAtt;

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMEdorItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "EdorCode";
		pk[1] = "AppObj";

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
		LMEdorItemSchema cloned = (LMEdorItemSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorCode()
	{
		return EdorCode;
	}
	public void setEdorCode(String aEdorCode)
	{
		if(aEdorCode!=null && aEdorCode.length()>6)
			throw new IllegalArgumentException("保全项目编码EdorCode值"+aEdorCode+"的长度"+aEdorCode.length()+"大于最大值6");
		EdorCode = aEdorCode;
	}
	public String getEdorName()
	{
		return EdorName;
	}
	public void setEdorName(String aEdorName)
	{
		if(aEdorName!=null && aEdorName.length()>120)
			throw new IllegalArgumentException("保全项目名称EdorName值"+aEdorName+"的长度"+aEdorName.length()+"大于最大值120");
		EdorName = aEdorName;
	}
	/**
	* G－团单(Group) I－个单(Individual) A－团体客户号 B－个人客户号 C－团个单均可 D－团个人均可 E－全部均可
	*/
	public String getAppObj()
	{
		return AppObj;
	}
	public void setAppObj(String aAppObj)
	{
		if(aAppObj!=null && aAppObj.length()>1)
			throw new IllegalArgumentException("申请对象类型AppObj值"+aAppObj+"的长度"+aAppObj.length()+"大于最大值1");
		AppObj = aAppObj;
	}
	/**
	* 1－只显示保单 2－需要显示被保人 3－需要显示险种 4－显示团体保单 5－显示团体险种
	*/
	public String getDisplayFlag()
	{
		return DisplayFlag;
	}
	public void setDisplayFlag(String aDisplayFlag)
	{
		if(aDisplayFlag!=null && aDisplayFlag.length()>1)
			throw new IllegalArgumentException("页面展示层次标记DisplayFlag值"+aDisplayFlag+"的长度"+aDisplayFlag.length()+"大于最大值1");
		DisplayFlag = aDisplayFlag;
	}
	/**
	* Y－需要 N－不需要
	*/
	public String getCalFlag()
	{
		return CalFlag;
	}
	public void setCalFlag(String aCalFlag)
	{
		if(aCalFlag!=null && aCalFlag.length()>1)
			throw new IllegalArgumentException("是否需要重算CalFlag值"+aCalFlag+"的长度"+aCalFlag.length()+"大于最大值1");
		CalFlag = aCalFlag;
	}
	/**
	* 0－不需要项目明细 1－ 需要项目明细
	*/
	public String getNeedDetail()
	{
		return NeedDetail;
	}
	public void setNeedDetail(String aNeedDetail)
	{
		if(aNeedDetail!=null && aNeedDetail.length()>1)
			throw new IllegalArgumentException("界面中是否显示项目明细NeedDetail值"+aNeedDetail+"的长度"+aNeedDetail.length()+"大于最大值1");
		NeedDetail = aNeedDetail;
	}
	/**
	* Y－需要打印 N－不需要打印
	*/
	public String getGrpNeedList()
	{
		return GrpNeedList;
	}
	public void setGrpNeedList(String aGrpNeedList)
	{
		if(aGrpNeedList!=null && aGrpNeedList.length()>1)
			throw new IllegalArgumentException("是否需要打印保全清单GrpNeedList值"+aGrpNeedList+"的长度"+aGrpNeedList.length()+"大于最大值1");
		GrpNeedList = aGrpNeedList;
	}
	/**
	* A级客服人员 B级客服人员 C级客服人员 D级客服人员 E级客服人员 F级客服人员
	*/
	public String getEdorPopedom()
	{
		return EdorPopedom;
	}
	public void setEdorPopedom(String aEdorPopedom)
	{
		if(aEdorPopedom!=null && aEdorPopedom.length()>2)
			throw new IllegalArgumentException("保全权限EdorPopedom值"+aEdorPopedom+"的长度"+aEdorPopedom.length()+"大于最大值2");
		EdorPopedom = aEdorPopedom;
	}
	public String getEdorConstraints()
	{
		return EdorConstraints;
	}
	public void setEdorConstraints(String aEdorConstraints)
	{
		if(aEdorConstraints!=null && aEdorConstraints.length()>180)
			throw new IllegalArgumentException("保全项目限制EdorConstraints值"+aEdorConstraints+"的长度"+aEdorConstraints.length()+"大于最大值180");
		EdorConstraints = aEdorConstraints;
	}
	/**
	* 0－不需要校验保单密码 1－需要校验保单密码
	*/
	public String getPwdFlag()
	{
		return PwdFlag;
	}
	public void setPwdFlag(String aPwdFlag)
	{
		if(aPwdFlag!=null && aPwdFlag.length()>1)
			throw new IllegalArgumentException("密码校验标识PwdFlag值"+aPwdFlag+"的长度"+aPwdFlag.length()+"大于最大值1");
		PwdFlag = aPwdFlag;
	}
	public String getAccBalaFlag()
	{
		return AccBalaFlag;
	}
	public void setAccBalaFlag(String aAccBalaFlag)
	{
		if(aAccBalaFlag!=null && aAccBalaFlag.length()>2)
			throw new IllegalArgumentException("金额不足时处理方式AccBalaFlag值"+aAccBalaFlag+"的长度"+aAccBalaFlag.length()+"大于最大值2");
		AccBalaFlag = aAccBalaFlag;
	}
	/**
	* 投连使用 01－卖出时使用买入价 02－卖出时使用卖出价
	*/
	public String getPriceSellFlag()
	{
		return PriceSellFlag;
	}
	public void setPriceSellFlag(String aPriceSellFlag)
	{
		if(aPriceSellFlag!=null && aPriceSellFlag.length()>2)
			throw new IllegalArgumentException("卖出时价格处理PriceSellFlag值"+aPriceSellFlag+"的长度"+aPriceSellFlag.length()+"大于最大值2");
		PriceSellFlag = aPriceSellFlag;
	}
	/**
	* 投连使用 01－买入时使用买入价 02－买入时使用卖出价
	*/
	public String getPriceBuyFlag()
	{
		return PriceBuyFlag;
	}
	public void setPriceBuyFlag(String aPriceBuyFlag)
	{
		if(aPriceBuyFlag!=null && aPriceBuyFlag.length()>2)
			throw new IllegalArgumentException("买入时价格处理PriceBuyFlag值"+aPriceBuyFlag+"的长度"+aPriceBuyFlag.length()+"大于最大值2");
		PriceBuyFlag = aPriceBuyFlag;
	}
	/**
	* 投连使用 01－不进行买入卖出处理，直接进行转换不需要延迟生效处理 02－需要买入卖出处理，需要等待价格公布，进行延迟生效处理
	*/
	public String getSameAccFlag()
	{
		return SameAccFlag;
	}
	public void setSameAccFlag(String aSameAccFlag)
	{
		if(aSameAccFlag!=null && aSameAccFlag.length()>2)
			throw new IllegalArgumentException("同一投资账户转换处理方式SameAccFlag值"+aSameAccFlag+"的长度"+aSameAccFlag.length()+"大于最大值2");
		SameAccFlag = aSameAccFlag;
	}
	/**
	* 0-团体保单层级,1-被保险人层级
	*/
	public String getEdorLevel()
	{
		return EdorLevel;
	}
	public void setEdorLevel(String aEdorLevel)
	{
		if(aEdorLevel!=null && aEdorLevel.length()>1)
			throw new IllegalArgumentException("保全项目级别EdorLevel值"+aEdorLevel+"的长度"+aEdorLevel.length()+"大于最大值1");
		EdorLevel = aEdorLevel;
	}
	/**
	* 0-普通保全项目且不涉及补退费,1-普通保全项目涉及补退费,2-险种特殊保全项目
	*/
	public String getEdorClass()
	{
		return EdorClass;
	}
	public void setEdorClass(String aEdorClass)
	{
		if(aEdorClass!=null && aEdorClass.length()>1)
			throw new IllegalArgumentException("保全项目类型EdorClass值"+aEdorClass+"的长度"+aEdorClass.length()+"大于最大值1");
		EdorClass = aEdorClass;
	}
	public String getActivityID()
	{
		return ActivityID;
	}
	public void setActivityID(String aActivityID)
	{
		if(aActivityID!=null && aActivityID.length()>10)
			throw new IllegalArgumentException("保全操作节点ActivityID值"+aActivityID+"的长度"+aActivityID.length()+"大于最大值10");
		ActivityID = aActivityID;
	}
	public String getValDateFlag()
	{
		return ValDateFlag;
	}
	public void setValDateFlag(String aValDateFlag)
	{
		if(aValDateFlag!=null && aValDateFlag.length()>1)
			throw new IllegalArgumentException("是否需要录入生效日期ValDateFlag值"+aValDateFlag+"的长度"+aValDateFlag.length()+"大于最大值1");
		ValDateFlag = aValDateFlag;
	}
	public String getAloneFlag()
	{
		return AloneFlag;
	}
	public void setAloneFlag(String aAloneFlag)
	{
		if(aAloneFlag!=null && aAloneFlag.length()>1)
			throw new IllegalArgumentException("是否单独操作AloneFlag值"+aAloneFlag+"的长度"+aAloneFlag.length()+"大于最大值1");
		AloneFlag = aAloneFlag;
	}
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		if(aUWFlag!=null && aUWFlag.length()>1)
			throw new IllegalArgumentException("是否需要核保UWFlag值"+aUWFlag+"的长度"+aUWFlag.length()+"大于最大值1");
		UWFlag = aUWFlag;
	}
	public String getEdorDesc()
	{
		return EdorDesc;
	}
	public void setEdorDesc(String aEdorDesc)
	{
		if(aEdorDesc!=null && aEdorDesc.length()>2000)
			throw new IllegalArgumentException("保全描述EdorDesc值"+aEdorDesc+"的长度"+aEdorDesc.length()+"大于最大值2000");
		EdorDesc = aEdorDesc;
	}
	public String getEdorMaterial()
	{
		return EdorMaterial;
	}
	public void setEdorMaterial(String aEdorMaterial)
	{
		if(aEdorMaterial!=null && aEdorMaterial.length()>2000)
			throw new IllegalArgumentException("应备资料EdorMaterial值"+aEdorMaterial+"的长度"+aEdorMaterial.length()+"大于最大值2000");
		EdorMaterial = aEdorMaterial;
	}
	public String getMattersNeedAtt()
	{
		return MattersNeedAtt;
	}
	public void setMattersNeedAtt(String aMattersNeedAtt)
	{
		if(aMattersNeedAtt!=null && aMattersNeedAtt.length()>2000)
			throw new IllegalArgumentException("注意事项MattersNeedAtt值"+aMattersNeedAtt+"的长度"+aMattersNeedAtt.length()+"大于最大值2000");
		MattersNeedAtt = aMattersNeedAtt;
	}

	/**
	* 使用另外一个 LMEdorItemSchema 对象给 Schema 赋值
	* @param: aLMEdorItemSchema LMEdorItemSchema
	**/
	public void setSchema(LMEdorItemSchema aLMEdorItemSchema)
	{
		this.EdorCode = aLMEdorItemSchema.getEdorCode();
		this.EdorName = aLMEdorItemSchema.getEdorName();
		this.AppObj = aLMEdorItemSchema.getAppObj();
		this.DisplayFlag = aLMEdorItemSchema.getDisplayFlag();
		this.CalFlag = aLMEdorItemSchema.getCalFlag();
		this.NeedDetail = aLMEdorItemSchema.getNeedDetail();
		this.GrpNeedList = aLMEdorItemSchema.getGrpNeedList();
		this.EdorPopedom = aLMEdorItemSchema.getEdorPopedom();
		this.EdorConstraints = aLMEdorItemSchema.getEdorConstraints();
		this.PwdFlag = aLMEdorItemSchema.getPwdFlag();
		this.AccBalaFlag = aLMEdorItemSchema.getAccBalaFlag();
		this.PriceSellFlag = aLMEdorItemSchema.getPriceSellFlag();
		this.PriceBuyFlag = aLMEdorItemSchema.getPriceBuyFlag();
		this.SameAccFlag = aLMEdorItemSchema.getSameAccFlag();
		this.EdorLevel = aLMEdorItemSchema.getEdorLevel();
		this.EdorClass = aLMEdorItemSchema.getEdorClass();
		this.ActivityID = aLMEdorItemSchema.getActivityID();
		this.ValDateFlag = aLMEdorItemSchema.getValDateFlag();
		this.AloneFlag = aLMEdorItemSchema.getAloneFlag();
		this.UWFlag = aLMEdorItemSchema.getUWFlag();
		this.EdorDesc = aLMEdorItemSchema.getEdorDesc();
		this.EdorMaterial = aLMEdorItemSchema.getEdorMaterial();
		this.MattersNeedAtt = aLMEdorItemSchema.getMattersNeedAtt();
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

			if( rs.getString("DisplayFlag") == null )
				this.DisplayFlag = null;
			else
				this.DisplayFlag = rs.getString("DisplayFlag").trim();

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			if( rs.getString("NeedDetail") == null )
				this.NeedDetail = null;
			else
				this.NeedDetail = rs.getString("NeedDetail").trim();

			if( rs.getString("GrpNeedList") == null )
				this.GrpNeedList = null;
			else
				this.GrpNeedList = rs.getString("GrpNeedList").trim();

			if( rs.getString("EdorPopedom") == null )
				this.EdorPopedom = null;
			else
				this.EdorPopedom = rs.getString("EdorPopedom").trim();

			if( rs.getString("EdorConstraints") == null )
				this.EdorConstraints = null;
			else
				this.EdorConstraints = rs.getString("EdorConstraints").trim();

			if( rs.getString("PwdFlag") == null )
				this.PwdFlag = null;
			else
				this.PwdFlag = rs.getString("PwdFlag").trim();

			if( rs.getString("AccBalaFlag") == null )
				this.AccBalaFlag = null;
			else
				this.AccBalaFlag = rs.getString("AccBalaFlag").trim();

			if( rs.getString("PriceSellFlag") == null )
				this.PriceSellFlag = null;
			else
				this.PriceSellFlag = rs.getString("PriceSellFlag").trim();

			if( rs.getString("PriceBuyFlag") == null )
				this.PriceBuyFlag = null;
			else
				this.PriceBuyFlag = rs.getString("PriceBuyFlag").trim();

			if( rs.getString("SameAccFlag") == null )
				this.SameAccFlag = null;
			else
				this.SameAccFlag = rs.getString("SameAccFlag").trim();

			if( rs.getString("EdorLevel") == null )
				this.EdorLevel = null;
			else
				this.EdorLevel = rs.getString("EdorLevel").trim();

			if( rs.getString("EdorClass") == null )
				this.EdorClass = null;
			else
				this.EdorClass = rs.getString("EdorClass").trim();

			if( rs.getString("ActivityID") == null )
				this.ActivityID = null;
			else
				this.ActivityID = rs.getString("ActivityID").trim();

			if( rs.getString("ValDateFlag") == null )
				this.ValDateFlag = null;
			else
				this.ValDateFlag = rs.getString("ValDateFlag").trim();

			if( rs.getString("AloneFlag") == null )
				this.AloneFlag = null;
			else
				this.AloneFlag = rs.getString("AloneFlag").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("EdorDesc") == null )
				this.EdorDesc = null;
			else
				this.EdorDesc = rs.getString("EdorDesc").trim();

			if( rs.getString("EdorMaterial") == null )
				this.EdorMaterial = null;
			else
				this.EdorMaterial = rs.getString("EdorMaterial").trim();

			if( rs.getString("MattersNeedAtt") == null )
				this.MattersNeedAtt = null;
			else
				this.MattersNeedAtt = rs.getString("MattersNeedAtt").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMEdorItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMEdorItemSchema getSchema()
	{
		LMEdorItemSchema aLMEdorItemSchema = new LMEdorItemSchema();
		aLMEdorItemSchema.setSchema(this);
		return aLMEdorItemSchema;
	}

	public LMEdorItemDB getDB()
	{
		LMEdorItemDB aDBOper = new LMEdorItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorItem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppObj)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DisplayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedDetail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNeedList)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorConstraints)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PwdFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccBalaFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PriceSellFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PriceBuyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SameAccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActivityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValDateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AloneFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorMaterial)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MattersNeedAtt));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorItem>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AppObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DisplayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			NeedDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GrpNeedList = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			EdorPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			EdorConstraints = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PwdFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AccBalaFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PriceSellFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PriceBuyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			SameAccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			EdorLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			EdorClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ValDateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AloneFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			EdorDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			EdorMaterial = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MattersNeedAtt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorItemSchema";
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
		if (FCode.equalsIgnoreCase("DisplayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DisplayFlag));
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equalsIgnoreCase("NeedDetail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedDetail));
		}
		if (FCode.equalsIgnoreCase("GrpNeedList"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNeedList));
		}
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorPopedom));
		}
		if (FCode.equalsIgnoreCase("EdorConstraints"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorConstraints));
		}
		if (FCode.equalsIgnoreCase("PwdFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PwdFlag));
		}
		if (FCode.equalsIgnoreCase("AccBalaFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccBalaFlag));
		}
		if (FCode.equalsIgnoreCase("PriceSellFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PriceSellFlag));
		}
		if (FCode.equalsIgnoreCase("PriceBuyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PriceBuyFlag));
		}
		if (FCode.equalsIgnoreCase("SameAccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SameAccFlag));
		}
		if (FCode.equalsIgnoreCase("EdorLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorLevel));
		}
		if (FCode.equalsIgnoreCase("EdorClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorClass));
		}
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityID));
		}
		if (FCode.equalsIgnoreCase("ValDateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValDateFlag));
		}
		if (FCode.equalsIgnoreCase("AloneFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AloneFlag));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("EdorDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorDesc));
		}
		if (FCode.equalsIgnoreCase("EdorMaterial"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorMaterial));
		}
		if (FCode.equalsIgnoreCase("MattersNeedAtt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MattersNeedAtt));
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
				strFieldValue = StrTool.GBKToUnicode(EdorCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AppObj);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DisplayFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(NeedDetail);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GrpNeedList);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(EdorPopedom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(EdorConstraints);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PwdFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AccBalaFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PriceSellFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PriceBuyFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(SameAccFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(EdorLevel);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(EdorClass);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ActivityID);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ValDateFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AloneFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(EdorDesc);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(EdorMaterial);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MattersNeedAtt);
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
		if (FCode.equalsIgnoreCase("DisplayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DisplayFlag = FValue.trim();
			}
			else
				DisplayFlag = null;
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
		if (FCode.equalsIgnoreCase("EdorConstraints"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorConstraints = FValue.trim();
			}
			else
				EdorConstraints = null;
		}
		if (FCode.equalsIgnoreCase("PwdFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PwdFlag = FValue.trim();
			}
			else
				PwdFlag = null;
		}
		if (FCode.equalsIgnoreCase("AccBalaFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccBalaFlag = FValue.trim();
			}
			else
				AccBalaFlag = null;
		}
		if (FCode.equalsIgnoreCase("PriceSellFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PriceSellFlag = FValue.trim();
			}
			else
				PriceSellFlag = null;
		}
		if (FCode.equalsIgnoreCase("PriceBuyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PriceBuyFlag = FValue.trim();
			}
			else
				PriceBuyFlag = null;
		}
		if (FCode.equalsIgnoreCase("SameAccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SameAccFlag = FValue.trim();
			}
			else
				SameAccFlag = null;
		}
		if (FCode.equalsIgnoreCase("EdorLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorLevel = FValue.trim();
			}
			else
				EdorLevel = null;
		}
		if (FCode.equalsIgnoreCase("EdorClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorClass = FValue.trim();
			}
			else
				EdorClass = null;
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
		if (FCode.equalsIgnoreCase("ValDateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValDateFlag = FValue.trim();
			}
			else
				ValDateFlag = null;
		}
		if (FCode.equalsIgnoreCase("AloneFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AloneFlag = FValue.trim();
			}
			else
				AloneFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("EdorDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorDesc = FValue.trim();
			}
			else
				EdorDesc = null;
		}
		if (FCode.equalsIgnoreCase("EdorMaterial"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorMaterial = FValue.trim();
			}
			else
				EdorMaterial = null;
		}
		if (FCode.equalsIgnoreCase("MattersNeedAtt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MattersNeedAtt = FValue.trim();
			}
			else
				MattersNeedAtt = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMEdorItemSchema other = (LMEdorItemSchema)otherObject;
		return
			EdorCode.equals(other.getEdorCode())
			&& EdorName.equals(other.getEdorName())
			&& AppObj.equals(other.getAppObj())
			&& DisplayFlag.equals(other.getDisplayFlag())
			&& CalFlag.equals(other.getCalFlag())
			&& NeedDetail.equals(other.getNeedDetail())
			&& GrpNeedList.equals(other.getGrpNeedList())
			&& EdorPopedom.equals(other.getEdorPopedom())
			&& EdorConstraints.equals(other.getEdorConstraints())
			&& PwdFlag.equals(other.getPwdFlag())
			&& AccBalaFlag.equals(other.getAccBalaFlag())
			&& PriceSellFlag.equals(other.getPriceSellFlag())
			&& PriceBuyFlag.equals(other.getPriceBuyFlag())
			&& SameAccFlag.equals(other.getSameAccFlag())
			&& EdorLevel.equals(other.getEdorLevel())
			&& EdorClass.equals(other.getEdorClass())
			&& ActivityID.equals(other.getActivityID())
			&& ValDateFlag.equals(other.getValDateFlag())
			&& AloneFlag.equals(other.getAloneFlag())
			&& UWFlag.equals(other.getUWFlag())
			&& EdorDesc.equals(other.getEdorDesc())
			&& EdorMaterial.equals(other.getEdorMaterial())
			&& MattersNeedAtt.equals(other.getMattersNeedAtt());
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
		if( strFieldName.equals("EdorCode") ) {
			return 0;
		}
		if( strFieldName.equals("EdorName") ) {
			return 1;
		}
		if( strFieldName.equals("AppObj") ) {
			return 2;
		}
		if( strFieldName.equals("DisplayFlag") ) {
			return 3;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 4;
		}
		if( strFieldName.equals("NeedDetail") ) {
			return 5;
		}
		if( strFieldName.equals("GrpNeedList") ) {
			return 6;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return 7;
		}
		if( strFieldName.equals("EdorConstraints") ) {
			return 8;
		}
		if( strFieldName.equals("PwdFlag") ) {
			return 9;
		}
		if( strFieldName.equals("AccBalaFlag") ) {
			return 10;
		}
		if( strFieldName.equals("PriceSellFlag") ) {
			return 11;
		}
		if( strFieldName.equals("PriceBuyFlag") ) {
			return 12;
		}
		if( strFieldName.equals("SameAccFlag") ) {
			return 13;
		}
		if( strFieldName.equals("EdorLevel") ) {
			return 14;
		}
		if( strFieldName.equals("EdorClass") ) {
			return 15;
		}
		if( strFieldName.equals("ActivityID") ) {
			return 16;
		}
		if( strFieldName.equals("ValDateFlag") ) {
			return 17;
		}
		if( strFieldName.equals("AloneFlag") ) {
			return 18;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 19;
		}
		if( strFieldName.equals("EdorDesc") ) {
			return 20;
		}
		if( strFieldName.equals("EdorMaterial") ) {
			return 21;
		}
		if( strFieldName.equals("MattersNeedAtt") ) {
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
				strFieldName = "EdorCode";
				break;
			case 1:
				strFieldName = "EdorName";
				break;
			case 2:
				strFieldName = "AppObj";
				break;
			case 3:
				strFieldName = "DisplayFlag";
				break;
			case 4:
				strFieldName = "CalFlag";
				break;
			case 5:
				strFieldName = "NeedDetail";
				break;
			case 6:
				strFieldName = "GrpNeedList";
				break;
			case 7:
				strFieldName = "EdorPopedom";
				break;
			case 8:
				strFieldName = "EdorConstraints";
				break;
			case 9:
				strFieldName = "PwdFlag";
				break;
			case 10:
				strFieldName = "AccBalaFlag";
				break;
			case 11:
				strFieldName = "PriceSellFlag";
				break;
			case 12:
				strFieldName = "PriceBuyFlag";
				break;
			case 13:
				strFieldName = "SameAccFlag";
				break;
			case 14:
				strFieldName = "EdorLevel";
				break;
			case 15:
				strFieldName = "EdorClass";
				break;
			case 16:
				strFieldName = "ActivityID";
				break;
			case 17:
				strFieldName = "ValDateFlag";
				break;
			case 18:
				strFieldName = "AloneFlag";
				break;
			case 19:
				strFieldName = "UWFlag";
				break;
			case 20:
				strFieldName = "EdorDesc";
				break;
			case 21:
				strFieldName = "EdorMaterial";
				break;
			case 22:
				strFieldName = "MattersNeedAtt";
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
		if( strFieldName.equals("EdorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppObj") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DisplayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedDetail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNeedList") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorConstraints") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PwdFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccBalaFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PriceSellFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PriceBuyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SameAccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActivityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValDateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AloneFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorMaterial") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MattersNeedAtt") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
