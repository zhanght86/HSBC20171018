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
import com.sinosoft.lis.db.LRTemplateBDB;

/*
 * <p>ClassName: LRTemplateBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LRTemplateBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRTemplateBSchema.class);
	// @Field
	/** 模板号 */
	private String Id;
	/** 版本号 */
	private int Version;
	/** 规则名 */
	private String RuleName;
	/** 规则中文 */
	private String RuleCh;
	/** 规则逻辑 */
	private String SQLStatement;
	/** 所用bom */
	private String BOMs;
	/** Sql参数 */
	private String SQLParameter;
	/** 有效标志 */
	private String Valid;
	/** 状态 */
	private String State;
	/** 级别 */
	private String TemplateLevel;
	/** 业务模块 */
	private String Business;
	/** 分类 */
	private String Type;
	/** 表名 */
	private String TableName;
	/** 生效日期 */
	private Date StartDate;
	/** 失效日期 */
	private Date EndDate;
	/** 功能描述 */
	private String Description;
	/** 创建人 */
	private String Creator;
	/** 修改人 */
	private String Modifier;
	/** 审批人 */
	private String Approver;
	/** 发布人 */
	private String Deployer;
	/** 入机时间 */
	private String MakeTime;
	/** 入机日期 */
	private Date MakeDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 审批时间 */
	private String AuthorTime;
	/** 审批日期 */
	private Date AuthorDate;
	/** 发布时间 */
	private String DeclTime;
	/** 发布日期 */
	private Date DeclDate;
	/** View参数 */
	private String ViewParameter;
	/** 算法中文 */
	private String RuleCalCh;

	public static final int FIELDNUM = 30;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRTemplateBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "Id";
		pk[1] = "Version";

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
		LRTemplateBSchema cloned = (LRTemplateBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getId()
	{
		return Id;
	}
	public void setId(String aId)
	{
		if(aId!=null && aId.length()>20)
			throw new IllegalArgumentException("模板号Id值"+aId+"的长度"+aId.length()+"大于最大值20");
		Id = aId;
	}
	public int getVersion()
	{
		return Version;
	}
	public void setVersion(int aVersion)
	{
		Version = aVersion;
	}
	public void setVersion(String aVersion)
	{
		if (aVersion != null && !aVersion.equals(""))
		{
			Integer tInteger = new Integer(aVersion);
			int i = tInteger.intValue();
			Version = i;
		}
	}

	public String getRuleName()
	{
		return RuleName;
	}
	public void setRuleName(String aRuleName)
	{
		if(aRuleName!=null && aRuleName.length()>200)
			throw new IllegalArgumentException("规则名RuleName值"+aRuleName+"的长度"+aRuleName.length()+"大于最大值200");
		RuleName = aRuleName;
	}
	public String getRuleCh()
	{
		return RuleCh;
	}
	public void setRuleCh(String aRuleCh)
	{
		if(aRuleCh!=null && aRuleCh.length()>4000)
			throw new IllegalArgumentException("规则中文RuleCh值"+aRuleCh+"的长度"+aRuleCh.length()+"大于最大值4000");
		RuleCh = aRuleCh;
	}
	/**
	* 规则的SQL语言描述
	*/
	public String getSQLStatement()
	{
		return SQLStatement;
	}
	public void setSQLStatement(String aSQLStatement)
	{
		if(aSQLStatement!=null && aSQLStatement.length()>4000)
			throw new IllegalArgumentException("规则逻辑SQLStatement值"+aSQLStatement+"的长度"+aSQLStatement.length()+"大于最大值4000");
		SQLStatement = aSQLStatement;
	}
	public String getBOMs()
	{
		return BOMs;
	}
	public void setBOMs(String aBOMs)
	{
		if(aBOMs!=null && aBOMs.length()>1000)
			throw new IllegalArgumentException("所用bomBOMs值"+aBOMs+"的长度"+aBOMs.length()+"大于最大值1000");
		BOMs = aBOMs;
	}
	/**
	* 列出所有的参数  和  运算符号<p>
	* 用来进行界面展现<p>
	* <p>
	* A.a>;B.b=
	*/
	public String getSQLParameter()
	{
		return SQLParameter;
	}
	public void setSQLParameter(String aSQLParameter)
	{
		if(aSQLParameter!=null && aSQLParameter.length()>4000)
			throw new IllegalArgumentException("Sql参数SQLParameter值"+aSQLParameter+"的长度"+aSQLParameter.length()+"大于最大值4000");
		SQLParameter = aSQLParameter;
	}
	/**
	* 0表示无效,1表示有效
	*/
	public String getValid()
	{
		return Valid;
	}
	public void setValid(String aValid)
	{
		if(aValid!=null && aValid.length()>1)
			throw new IllegalArgumentException("有效标志Valid值"+aValid+"的长度"+aValid.length()+"大于最大值1");
		Valid = aValid;
	}
	/**
	* 0——初始<p>
	* 1——待测试<p>
	* 2——待修改<p>
	* 3——待审批<p>
	* 4-------待发布<p>
	* 7------可使用<p>
	* 9------已作废
	*/
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
	/**
	* 1-系统级规则,2-组合级规则,3-产品级规则,4-业务级规则,5-客户级规则,6-保单级规则
	*/
	public String getTemplateLevel()
	{
		return TemplateLevel;
	}
	public void setTemplateLevel(String aTemplateLevel)
	{
		if(aTemplateLevel!=null && aTemplateLevel.length()>12)
			throw new IllegalArgumentException("级别TemplateLevel值"+aTemplateLevel+"的长度"+aTemplateLevel.length()+"大于最大值12");
		TemplateLevel = aTemplateLevel;
	}
	/**
	* 1-契约自核规则<p>
	* 2-保全自核规则,<p>
	* 3-理赔自核规则,<p>
	* 4- 续期续保核保规则,<p>
	* 5-销售管理模块
	*/
	public String getBusiness()
	{
		return Business;
	}
	public void setBusiness(String aBusiness)
	{
		if(aBusiness!=null && aBusiness.length()>2)
			throw new IllegalArgumentException("业务模块Business值"+aBusiness+"的长度"+aBusiness.length()+"大于最大值2");
		Business = aBusiness;
	}
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		if(aType!=null && aType.length()>8)
			throw new IllegalArgumentException("分类Type值"+aType+"的长度"+aType.length()+"大于最大值8");
		Type = aType;
	}
	/**
	* 规则所需数据的存储表名
	*/
	public String getTableName()
	{
		return TableName;
	}
	public void setTableName(String aTableName)
	{
		if(aTableName!=null && aTableName.length()>30)
			throw new IllegalArgumentException("表名TableName值"+aTableName+"的长度"+aTableName.length()+"大于最大值30");
		TableName = aTableName;
	}
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getDescription()
	{
		return Description;
	}
	public void setDescription(String aDescription)
	{
		if(aDescription!=null && aDescription.length()>1000)
			throw new IllegalArgumentException("功能描述Description值"+aDescription+"的长度"+aDescription.length()+"大于最大值1000");
		Description = aDescription;
	}
	public String getCreator()
	{
		return Creator;
	}
	public void setCreator(String aCreator)
	{
		if(aCreator!=null && aCreator.length()>80)
			throw new IllegalArgumentException("创建人Creator值"+aCreator+"的长度"+aCreator.length()+"大于最大值80");
		Creator = aCreator;
	}
	public String getModifier()
	{
		return Modifier;
	}
	public void setModifier(String aModifier)
	{
		if(aModifier!=null && aModifier.length()>80)
			throw new IllegalArgumentException("修改人Modifier值"+aModifier+"的长度"+aModifier.length()+"大于最大值80");
		Modifier = aModifier;
	}
	public String getApprover()
	{
		return Approver;
	}
	public void setApprover(String aApprover)
	{
		if(aApprover!=null && aApprover.length()>80)
			throw new IllegalArgumentException("审批人Approver值"+aApprover+"的长度"+aApprover.length()+"大于最大值80");
		Approver = aApprover;
	}
	public String getDeployer()
	{
		return Deployer;
	}
	public void setDeployer(String aDeployer)
	{
		if(aDeployer!=null && aDeployer.length()>80)
			throw new IllegalArgumentException("发布人Deployer值"+aDeployer+"的长度"+aDeployer.length()+"大于最大值80");
		Deployer = aDeployer;
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
	/**
	* 纪录产生该纪录的操作员。
	*/
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
	* 纪录最后一次修改的日期。
	*/
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

	public String getAuthorTime()
	{
		return AuthorTime;
	}
	public void setAuthorTime(String aAuthorTime)
	{
		if(aAuthorTime!=null && aAuthorTime.length()>8)
			throw new IllegalArgumentException("审批时间AuthorTime值"+aAuthorTime+"的长度"+aAuthorTime.length()+"大于最大值8");
		AuthorTime = aAuthorTime;
	}
	/**
	* 纪录产生该纪录的操作员。
	*/
	public String getAuthorDate()
	{
		if( AuthorDate != null )
			return fDate.getString(AuthorDate);
		else
			return null;
	}
	public void setAuthorDate(Date aAuthorDate)
	{
		AuthorDate = aAuthorDate;
	}
	public void setAuthorDate(String aAuthorDate)
	{
		if (aAuthorDate != null && !aAuthorDate.equals("") )
		{
			AuthorDate = fDate.getDate( aAuthorDate );
		}
		else
			AuthorDate = null;
	}

	public String getDeclTime()
	{
		return DeclTime;
	}
	public void setDeclTime(String aDeclTime)
	{
		if(aDeclTime!=null && aDeclTime.length()>8)
			throw new IllegalArgumentException("发布时间DeclTime值"+aDeclTime+"的长度"+aDeclTime.length()+"大于最大值8");
		DeclTime = aDeclTime;
	}
	/**
	* 纪录产生该纪录的操作员。
	*/
	public String getDeclDate()
	{
		if( DeclDate != null )
			return fDate.getString(DeclDate);
		else
			return null;
	}
	public void setDeclDate(Date aDeclDate)
	{
		DeclDate = aDeclDate;
	}
	public void setDeclDate(String aDeclDate)
	{
		if (aDeclDate != null && !aDeclDate.equals("") )
		{
			DeclDate = fDate.getDate( aDeclDate );
		}
		else
			DeclDate = null;
	}

	/**
	* 列出所有的参数  和  运算符号<p>
	* 用来进行界面展现<p>
	* <p>
	* A.a>;B.b=
	*/
	public String getViewParameter()
	{
		return ViewParameter;
	}
	public void setViewParameter(String aViewParameter)
	{
		ViewParameter = aViewParameter;
	}
	public String getRuleCalCh()
	{
		return RuleCalCh;
	}
	public void setRuleCalCh(String aRuleCalCh)
	{
		if(aRuleCalCh!=null && aRuleCalCh.length()>4000)
			throw new IllegalArgumentException("算法中文RuleCalCh值"+aRuleCalCh+"的长度"+aRuleCalCh.length()+"大于最大值4000");
		RuleCalCh = aRuleCalCh;
	}

	/**
	* 使用另外一个 LRTemplateBSchema 对象给 Schema 赋值
	* @param: aLRTemplateBSchema LRTemplateBSchema
	**/
	public void setSchema(LRTemplateBSchema aLRTemplateBSchema)
	{
		this.Id = aLRTemplateBSchema.getId();
		this.Version = aLRTemplateBSchema.getVersion();
		this.RuleName = aLRTemplateBSchema.getRuleName();
		this.RuleCh = aLRTemplateBSchema.getRuleCh();
		this.SQLStatement = aLRTemplateBSchema.getSQLStatement();
		this.BOMs = aLRTemplateBSchema.getBOMs();
		this.SQLParameter = aLRTemplateBSchema.getSQLParameter();
		this.Valid = aLRTemplateBSchema.getValid();
		this.State = aLRTemplateBSchema.getState();
		this.TemplateLevel = aLRTemplateBSchema.getTemplateLevel();
		this.Business = aLRTemplateBSchema.getBusiness();
		this.Type = aLRTemplateBSchema.getType();
		this.TableName = aLRTemplateBSchema.getTableName();
		this.StartDate = fDate.getDate( aLRTemplateBSchema.getStartDate());
		this.EndDate = fDate.getDate( aLRTemplateBSchema.getEndDate());
		this.Description = aLRTemplateBSchema.getDescription();
		this.Creator = aLRTemplateBSchema.getCreator();
		this.Modifier = aLRTemplateBSchema.getModifier();
		this.Approver = aLRTemplateBSchema.getApprover();
		this.Deployer = aLRTemplateBSchema.getDeployer();
		this.MakeTime = aLRTemplateBSchema.getMakeTime();
		this.MakeDate = fDate.getDate( aLRTemplateBSchema.getMakeDate());
		this.ModifyTime = aLRTemplateBSchema.getModifyTime();
		this.ModifyDate = fDate.getDate( aLRTemplateBSchema.getModifyDate());
		this.AuthorTime = aLRTemplateBSchema.getAuthorTime();
		this.AuthorDate = fDate.getDate( aLRTemplateBSchema.getAuthorDate());
		this.DeclTime = aLRTemplateBSchema.getDeclTime();
		this.DeclDate = fDate.getDate( aLRTemplateBSchema.getDeclDate());
		this.ViewParameter = aLRTemplateBSchema.getViewParameter();
		this.RuleCalCh = aLRTemplateBSchema.getRuleCalCh();
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
			if( rs.getString("Id") == null )
				this.Id = null;
			else
				this.Id = rs.getString("Id").trim();

			this.Version = rs.getInt("Version");
			if( rs.getString("RuleName") == null )
				this.RuleName = null;
			else
				this.RuleName = rs.getString("RuleName").trim();

			if( rs.getString("RuleCh") == null )
				this.RuleCh = null;
			else
				this.RuleCh = rs.getString("RuleCh").trim();

			if( rs.getString("SQLStatement") == null )
				this.SQLStatement = null;
			else
				this.SQLStatement = rs.getString("SQLStatement").trim();

			if( rs.getString("BOMs") == null )
				this.BOMs = null;
			else
				this.BOMs = rs.getString("BOMs").trim();

			if( rs.getString("SQLParameter") == null )
				this.SQLParameter = null;
			else
				this.SQLParameter = rs.getString("SQLParameter").trim();

			if( rs.getString("Valid") == null )
				this.Valid = null;
			else
				this.Valid = rs.getString("Valid").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("TemplateLevel") == null )
				this.TemplateLevel = null;
			else
				this.TemplateLevel = rs.getString("TemplateLevel").trim();

			if( rs.getString("Business") == null )
				this.Business = null;
			else
				this.Business = rs.getString("Business").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("TableName") == null )
				this.TableName = null;
			else
				this.TableName = rs.getString("TableName").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("Description") == null )
				this.Description = null;
			else
				this.Description = rs.getString("Description").trim();

			if( rs.getString("Creator") == null )
				this.Creator = null;
			else
				this.Creator = rs.getString("Creator").trim();

			if( rs.getString("Modifier") == null )
				this.Modifier = null;
			else
				this.Modifier = rs.getString("Modifier").trim();

			if( rs.getString("Approver") == null )
				this.Approver = null;
			else
				this.Approver = rs.getString("Approver").trim();

			if( rs.getString("Deployer") == null )
				this.Deployer = null;
			else
				this.Deployer = rs.getString("Deployer").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("AuthorTime") == null )
				this.AuthorTime = null;
			else
				this.AuthorTime = rs.getString("AuthorTime").trim();

			this.AuthorDate = rs.getDate("AuthorDate");
			if( rs.getString("DeclTime") == null )
				this.DeclTime = null;
			else
				this.DeclTime = rs.getString("DeclTime").trim();

			this.DeclDate = rs.getDate("DeclDate");
			if( rs.getString("ViewParameter") == null )
				this.ViewParameter = null;
			else
				this.ViewParameter = rs.getString("ViewParameter").trim();

			if( rs.getString("RuleCalCh") == null )
				this.RuleCalCh = null;
			else
				this.RuleCalCh = rs.getString("RuleCalCh").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRTemplateB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRTemplateBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRTemplateBSchema getSchema()
	{
		LRTemplateBSchema aLRTemplateBSchema = new LRTemplateBSchema();
		aLRTemplateBSchema.setSchema(this);
		return aLRTemplateBSchema;
	}

	public LRTemplateBDB getDB()
	{
		LRTemplateBDB aDBOper = new LRTemplateBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRTemplateB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Id)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Version));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleCh)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SQLStatement)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BOMs)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SQLParameter)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Valid)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TemplateLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Business)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Description)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Creator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Modifier)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Approver)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Deployer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuthorTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AuthorDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeclTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeclDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ViewParameter)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleCalCh));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRTemplateB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Id = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Version= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			RuleName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RuleCh = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SQLStatement = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BOMs = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SQLParameter = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Valid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			TemplateLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Business = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			TableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			Description = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Creator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Modifier = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Approver = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Deployer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			AuthorTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AuthorDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			DeclTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			DeclDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ViewParameter = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			RuleCalCh = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRTemplateBSchema";
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
		if (FCode.equalsIgnoreCase("Id"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Id));
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Version));
		}
		if (FCode.equalsIgnoreCase("RuleName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleName));
		}
		if (FCode.equalsIgnoreCase("RuleCh"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleCh));
		}
		if (FCode.equalsIgnoreCase("SQLStatement"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SQLStatement));
		}
		if (FCode.equalsIgnoreCase("BOMs"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BOMs));
		}
		if (FCode.equalsIgnoreCase("SQLParameter"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SQLParameter));
		}
		if (FCode.equalsIgnoreCase("Valid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Valid));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("TemplateLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TemplateLevel));
		}
		if (FCode.equalsIgnoreCase("Business"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Business));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("TableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TableName));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Description));
		}
		if (FCode.equalsIgnoreCase("Creator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Creator));
		}
		if (FCode.equalsIgnoreCase("Modifier"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Modifier));
		}
		if (FCode.equalsIgnoreCase("Approver"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Approver));
		}
		if (FCode.equalsIgnoreCase("Deployer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Deployer));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("AuthorTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuthorTime));
		}
		if (FCode.equalsIgnoreCase("AuthorDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAuthorDate()));
		}
		if (FCode.equalsIgnoreCase("DeclTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclTime));
		}
		if (FCode.equalsIgnoreCase("DeclDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeclDate()));
		}
		if (FCode.equalsIgnoreCase("ViewParameter"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ViewParameter));
		}
		if (FCode.equalsIgnoreCase("RuleCalCh"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleCalCh));
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
				strFieldValue = StrTool.GBKToUnicode(Id);
				break;
			case 1:
				strFieldValue = String.valueOf(Version);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RuleName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RuleCh);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SQLStatement);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BOMs);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SQLParameter);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Valid);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(TemplateLevel);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Business);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(TableName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Description);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Creator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Modifier);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Approver);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Deployer);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AuthorTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAuthorDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(DeclTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeclDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ViewParameter);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(RuleCalCh);
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

		if (FCode.equalsIgnoreCase("Id"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Id = FValue.trim();
			}
			else
				Id = null;
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Version = i;
			}
		}
		if (FCode.equalsIgnoreCase("RuleName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleName = FValue.trim();
			}
			else
				RuleName = null;
		}
		if (FCode.equalsIgnoreCase("RuleCh"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleCh = FValue.trim();
			}
			else
				RuleCh = null;
		}
		if (FCode.equalsIgnoreCase("SQLStatement"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SQLStatement = FValue.trim();
			}
			else
				SQLStatement = null;
		}
		if (FCode.equalsIgnoreCase("BOMs"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BOMs = FValue.trim();
			}
			else
				BOMs = null;
		}
		if (FCode.equalsIgnoreCase("SQLParameter"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SQLParameter = FValue.trim();
			}
			else
				SQLParameter = null;
		}
		if (FCode.equalsIgnoreCase("Valid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Valid = FValue.trim();
			}
			else
				Valid = null;
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
		if (FCode.equalsIgnoreCase("TemplateLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TemplateLevel = FValue.trim();
			}
			else
				TemplateLevel = null;
		}
		if (FCode.equalsIgnoreCase("Business"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Business = FValue.trim();
			}
			else
				Business = null;
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equalsIgnoreCase("TableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TableName = FValue.trim();
			}
			else
				TableName = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
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
		if (FCode.equalsIgnoreCase("Creator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Creator = FValue.trim();
			}
			else
				Creator = null;
		}
		if (FCode.equalsIgnoreCase("Modifier"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Modifier = FValue.trim();
			}
			else
				Modifier = null;
		}
		if (FCode.equalsIgnoreCase("Approver"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Approver = FValue.trim();
			}
			else
				Approver = null;
		}
		if (FCode.equalsIgnoreCase("Deployer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Deployer = FValue.trim();
			}
			else
				Deployer = null;
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
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
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
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("AuthorTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuthorTime = FValue.trim();
			}
			else
				AuthorTime = null;
		}
		if (FCode.equalsIgnoreCase("AuthorDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AuthorDate = fDate.getDate( FValue );
			}
			else
				AuthorDate = null;
		}
		if (FCode.equalsIgnoreCase("DeclTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeclTime = FValue.trim();
			}
			else
				DeclTime = null;
		}
		if (FCode.equalsIgnoreCase("DeclDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeclDate = fDate.getDate( FValue );
			}
			else
				DeclDate = null;
		}
		if (FCode.equalsIgnoreCase("ViewParameter"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ViewParameter = FValue.trim();
			}
			else
				ViewParameter = null;
		}
		if (FCode.equalsIgnoreCase("RuleCalCh"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleCalCh = FValue.trim();
			}
			else
				RuleCalCh = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRTemplateBSchema other = (LRTemplateBSchema)otherObject;
		return
			Id.equals(other.getId())
			&& Version == other.getVersion()
			&& RuleName.equals(other.getRuleName())
			&& RuleCh.equals(other.getRuleCh())
			&& SQLStatement.equals(other.getSQLStatement())
			&& BOMs.equals(other.getBOMs())
			&& SQLParameter.equals(other.getSQLParameter())
			&& Valid.equals(other.getValid())
			&& State.equals(other.getState())
			&& TemplateLevel.equals(other.getTemplateLevel())
			&& Business.equals(other.getBusiness())
			&& Type.equals(other.getType())
			&& TableName.equals(other.getTableName())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& Description.equals(other.getDescription())
			&& Creator.equals(other.getCreator())
			&& Modifier.equals(other.getModifier())
			&& Approver.equals(other.getApprover())
			&& Deployer.equals(other.getDeployer())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& AuthorTime.equals(other.getAuthorTime())
			&& fDate.getString(AuthorDate).equals(other.getAuthorDate())
			&& DeclTime.equals(other.getDeclTime())
			&& fDate.getString(DeclDate).equals(other.getDeclDate())
			&& ViewParameter.equals(other.getViewParameter())
			&& RuleCalCh.equals(other.getRuleCalCh());
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
		if( strFieldName.equals("Id") ) {
			return 0;
		}
		if( strFieldName.equals("Version") ) {
			return 1;
		}
		if( strFieldName.equals("RuleName") ) {
			return 2;
		}
		if( strFieldName.equals("RuleCh") ) {
			return 3;
		}
		if( strFieldName.equals("SQLStatement") ) {
			return 4;
		}
		if( strFieldName.equals("BOMs") ) {
			return 5;
		}
		if( strFieldName.equals("SQLParameter") ) {
			return 6;
		}
		if( strFieldName.equals("Valid") ) {
			return 7;
		}
		if( strFieldName.equals("State") ) {
			return 8;
		}
		if( strFieldName.equals("TemplateLevel") ) {
			return 9;
		}
		if( strFieldName.equals("Business") ) {
			return 10;
		}
		if( strFieldName.equals("Type") ) {
			return 11;
		}
		if( strFieldName.equals("TableName") ) {
			return 12;
		}
		if( strFieldName.equals("StartDate") ) {
			return 13;
		}
		if( strFieldName.equals("EndDate") ) {
			return 14;
		}
		if( strFieldName.equals("Description") ) {
			return 15;
		}
		if( strFieldName.equals("Creator") ) {
			return 16;
		}
		if( strFieldName.equals("Modifier") ) {
			return 17;
		}
		if( strFieldName.equals("Approver") ) {
			return 18;
		}
		if( strFieldName.equals("Deployer") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("AuthorTime") ) {
			return 24;
		}
		if( strFieldName.equals("AuthorDate") ) {
			return 25;
		}
		if( strFieldName.equals("DeclTime") ) {
			return 26;
		}
		if( strFieldName.equals("DeclDate") ) {
			return 27;
		}
		if( strFieldName.equals("ViewParameter") ) {
			return 28;
		}
		if( strFieldName.equals("RuleCalCh") ) {
			return 29;
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
				strFieldName = "Id";
				break;
			case 1:
				strFieldName = "Version";
				break;
			case 2:
				strFieldName = "RuleName";
				break;
			case 3:
				strFieldName = "RuleCh";
				break;
			case 4:
				strFieldName = "SQLStatement";
				break;
			case 5:
				strFieldName = "BOMs";
				break;
			case 6:
				strFieldName = "SQLParameter";
				break;
			case 7:
				strFieldName = "Valid";
				break;
			case 8:
				strFieldName = "State";
				break;
			case 9:
				strFieldName = "TemplateLevel";
				break;
			case 10:
				strFieldName = "Business";
				break;
			case 11:
				strFieldName = "Type";
				break;
			case 12:
				strFieldName = "TableName";
				break;
			case 13:
				strFieldName = "StartDate";
				break;
			case 14:
				strFieldName = "EndDate";
				break;
			case 15:
				strFieldName = "Description";
				break;
			case 16:
				strFieldName = "Creator";
				break;
			case 17:
				strFieldName = "Modifier";
				break;
			case 18:
				strFieldName = "Approver";
				break;
			case 19:
				strFieldName = "Deployer";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "ModifyTime";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
				strFieldName = "AuthorTime";
				break;
			case 25:
				strFieldName = "AuthorDate";
				break;
			case 26:
				strFieldName = "DeclTime";
				break;
			case 27:
				strFieldName = "DeclDate";
				break;
			case 28:
				strFieldName = "ViewParameter";
				break;
			case 29:
				strFieldName = "RuleCalCh";
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
		if( strFieldName.equals("Id") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Version") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RuleName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleCh") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SQLStatement") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BOMs") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SQLParameter") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Valid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TemplateLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Business") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Description") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Creator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Modifier") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Approver") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Deployer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AuthorTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuthorDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DeclTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeclDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ViewParameter") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleCalCh") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
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
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
