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
import com.sinosoft.lis.db.LGWorkDB;

/*
 * <p>ClassName: LGWorkSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LGWorkSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LGWorkSchema.class);

	// @Field
	/** 作业编号 */
	private String WorkNo;
	/** 受理编号 */
	private String AcceptNo;
	/** 当前结点编号 */
	private String NodeNo;
	/** 作业类型编号 */
	private String TypeNo;
	/** 作业状态编号 */
	private String StatusNo;
	/** 优先级别编号 */
	private String PriorityNo;
	/** 客户编号 */
	private String CustomerNo;
	/** 保单编号 */
	private String ContNo;
	/** 申请人类型编号 */
	private String ApplyTypeNo;
	/** 受理途径编号 */
	private String AcceptWayNo;
	/** 受理机构编号 */
	private String AcceptCom;
	/** 受理人编号 */
	private String AcceptorNo;
	/** 受理日期 */
	private Date AcceptDate;
	/** 时限 */
	private String DateLimit;
	/** 备注 */
	private String Remark;
	/** 待办天数 */
	private String PauseNo;
	/** 待办日期 */
	private Date PauseDate;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 申请人名称 */
	private String ApplyName;
	/** Detailworkno */
	private String DetailWorkNo;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LGWorkSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "WorkNo";

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
		LGWorkSchema cloned = (LGWorkSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 工单编号
	*/
	public String getWorkNo()
	{
		return WorkNo;
	}
	public void setWorkNo(String aWorkNo)
	{
		WorkNo = aWorkNo;
	}
	/**
	* 业务处理号码
	*/
	public String getAcceptNo()
	{
		return AcceptNo;
	}
	public void setAcceptNo(String aAcceptNo)
	{
		AcceptNo = aAcceptNo;
	}
	/**
	* 当前作业所处的结点，即末结点
	*/
	public String getNodeNo()
	{
		return NodeNo;
	}
	public void setNodeNo(String aNodeNo)
	{
		NodeNo = aNodeNo;
	}
	/**
	* 见作业类型表
	*/
	public String getTypeNo()
	{
		return TypeNo;
	}
	public void setTypeNo(String aTypeNo)
	{
		TypeNo = aTypeNo;
	}
	/**
	* 0－待办(无期)<p>
	* 1－定期(定期)<p>
	* 2－未经办<p>
	* 3－正经办<p>
	* 4－审核<p>
	* 5－结案<p>
	* 6－存档
	*/
	public String getStatusNo()
	{
		return StatusNo;
	}
	public void setStatusNo(String aStatusNo)
	{
		StatusNo = aStatusNo;
	}
	/**
	* 根据提醒方式确定
	*/
	public String getPriorityNo()
	{
		return PriorityNo;
	}
	public void setPriorityNo(String aPriorityNo)
	{
		PriorityNo = aPriorityNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	/**
	* 1-投保人<p>
	* 2-被保人<p>
	* 3-业务员
	*/
	public String getApplyTypeNo()
	{
		return ApplyTypeNo;
	}
	public void setApplyTypeNo(String aApplyTypeNo)
	{
		ApplyTypeNo = aApplyTypeNo;
	}
	/**
	* 1－电话<p>
	* 2－柜台<p>
	* 3－代理人反馈<p>
	* 4－信函<p>
	* 5－传真<p>
	* 6－网络媒体或消协传办<p>
	* 7－员工反馈
	*/
	public String getAcceptWayNo()
	{
		return AcceptWayNo;
	}
	public void setAcceptWayNo(String aAcceptWayNo)
	{
		AcceptWayNo = aAcceptWayNo;
	}
	public String getAcceptCom()
	{
		return AcceptCom;
	}
	public void setAcceptCom(String aAcceptCom)
	{
		AcceptCom = aAcceptCom;
	}
	public String getAcceptorNo()
	{
		return AcceptorNo;
	}
	public void setAcceptorNo(String aAcceptorNo)
	{
		AcceptorNo = aAcceptorNo;
	}
	public String getAcceptDate()
	{
		if( AcceptDate != null )
			return fDate.getString(AcceptDate);
		else
			return null;
	}
	public void setAcceptDate(Date aAcceptDate)
	{
		AcceptDate = aAcceptDate;
	}
	public void setAcceptDate(String aAcceptDate)
	{
		if (aAcceptDate != null && !aAcceptDate.equals("") )
		{
			AcceptDate = fDate.getDate( aAcceptDate );
		}
		else
			AcceptDate = null;
	}

	/**
	* 0-关<p>
	* 1-开
	*/
	public String getDateLimit()
	{
		return DateLimit;
	}
	public void setDateLimit(String aDateLimit)
	{
		DateLimit = aDateLimit;
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
	* 等待的天数,当记录设置为待办状态的日期，当StatusNo为待办(定期)时有效
	*/
	public String getPauseNo()
	{
		return PauseNo;
	}
	public void setPauseNo(String aPauseNo)
	{
		PauseNo = aPauseNo;
	}
	/**
	* 记录设置为待办状态的日期，当StatusNo为待办(定期)时有效
	*/
	public String getPauseDate()
	{
		if( PauseDate != null )
			return fDate.getString(PauseDate);
		else
			return null;
	}
	public void setPauseDate(Date aPauseDate)
	{
		PauseDate = aPauseDate;
	}
	public void setPauseDate(String aPauseDate)
	{
		if (aPauseDate != null && !aPauseDate.equals("") )
		{
			PauseDate = fDate.getDate( aPauseDate );
		}
		else
			PauseDate = null;
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
	public String getApplyName()
	{
		return ApplyName;
	}
	public void setApplyName(String aApplyName)
	{
		ApplyName = aApplyName;
	}
	public String getDetailWorkNo()
	{
		return DetailWorkNo;
	}
	public void setDetailWorkNo(String aDetailWorkNo)
	{
		DetailWorkNo = aDetailWorkNo;
	}

	/**
	* 使用另外一个 LGWorkSchema 对象给 Schema 赋值
	* @param: aLGWorkSchema LGWorkSchema
	**/
	public void setSchema(LGWorkSchema aLGWorkSchema)
	{
		this.WorkNo = aLGWorkSchema.getWorkNo();
		this.AcceptNo = aLGWorkSchema.getAcceptNo();
		this.NodeNo = aLGWorkSchema.getNodeNo();
		this.TypeNo = aLGWorkSchema.getTypeNo();
		this.StatusNo = aLGWorkSchema.getStatusNo();
		this.PriorityNo = aLGWorkSchema.getPriorityNo();
		this.CustomerNo = aLGWorkSchema.getCustomerNo();
		this.ContNo = aLGWorkSchema.getContNo();
		this.ApplyTypeNo = aLGWorkSchema.getApplyTypeNo();
		this.AcceptWayNo = aLGWorkSchema.getAcceptWayNo();
		this.AcceptCom = aLGWorkSchema.getAcceptCom();
		this.AcceptorNo = aLGWorkSchema.getAcceptorNo();
		this.AcceptDate = fDate.getDate( aLGWorkSchema.getAcceptDate());
		this.DateLimit = aLGWorkSchema.getDateLimit();
		this.Remark = aLGWorkSchema.getRemark();
		this.PauseNo = aLGWorkSchema.getPauseNo();
		this.PauseDate = fDate.getDate( aLGWorkSchema.getPauseDate());
		this.Operator = aLGWorkSchema.getOperator();
		this.MakeDate = fDate.getDate( aLGWorkSchema.getMakeDate());
		this.MakeTime = aLGWorkSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLGWorkSchema.getModifyDate());
		this.ModifyTime = aLGWorkSchema.getModifyTime();
		this.ApplyName = aLGWorkSchema.getApplyName();
		this.DetailWorkNo = aLGWorkSchema.getDetailWorkNo();
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
			if( rs.getString("WorkNo") == null )
				this.WorkNo = null;
			else
				this.WorkNo = rs.getString("WorkNo").trim();

			if( rs.getString("AcceptNo") == null )
				this.AcceptNo = null;
			else
				this.AcceptNo = rs.getString("AcceptNo").trim();

			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

			if( rs.getString("TypeNo") == null )
				this.TypeNo = null;
			else
				this.TypeNo = rs.getString("TypeNo").trim();

			if( rs.getString("StatusNo") == null )
				this.StatusNo = null;
			else
				this.StatusNo = rs.getString("StatusNo").trim();

			if( rs.getString("PriorityNo") == null )
				this.PriorityNo = null;
			else
				this.PriorityNo = rs.getString("PriorityNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ApplyTypeNo") == null )
				this.ApplyTypeNo = null;
			else
				this.ApplyTypeNo = rs.getString("ApplyTypeNo").trim();

			if( rs.getString("AcceptWayNo") == null )
				this.AcceptWayNo = null;
			else
				this.AcceptWayNo = rs.getString("AcceptWayNo").trim();

			if( rs.getString("AcceptCom") == null )
				this.AcceptCom = null;
			else
				this.AcceptCom = rs.getString("AcceptCom").trim();

			if( rs.getString("AcceptorNo") == null )
				this.AcceptorNo = null;
			else
				this.AcceptorNo = rs.getString("AcceptorNo").trim();

			this.AcceptDate = rs.getDate("AcceptDate");
			if( rs.getString("DateLimit") == null )
				this.DateLimit = null;
			else
				this.DateLimit = rs.getString("DateLimit").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("PauseNo") == null )
				this.PauseNo = null;
			else
				this.PauseNo = rs.getString("PauseNo").trim();

			this.PauseDate = rs.getDate("PauseDate");
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

			if( rs.getString("ApplyName") == null )
				this.ApplyName = null;
			else
				this.ApplyName = rs.getString("ApplyName").trim();

			if( rs.getString("DetailWorkNo") == null )
				this.DetailWorkNo = null;
			else
				this.DetailWorkNo = rs.getString("DetailWorkNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LGWork表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LGWorkSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LGWorkSchema getSchema()
	{
		LGWorkSchema aLGWorkSchema = new LGWorkSchema();
		aLGWorkSchema.setSchema(this);
		return aLGWorkSchema;
	}

	public LGWorkDB getDB()
	{
		LGWorkDB aDBOper = new LGWorkDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLGWork描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(WorkNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NodeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TypeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StatusNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PriorityNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyTypeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcceptWayNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcceptCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcceptorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AcceptDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DateLimit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PauseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PauseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DetailWorkNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLGWork>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			WorkNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AcceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TypeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			StatusNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PriorityNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ApplyTypeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AcceptWayNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AcceptCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AcceptorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AcceptDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			DateLimit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			PauseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PauseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ApplyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			DetailWorkNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LGWorkSchema";
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
		if (FCode.equalsIgnoreCase("WorkNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkNo));
		}
		if (FCode.equalsIgnoreCase("AcceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcceptNo));
		}
		if (FCode.equalsIgnoreCase("NodeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeNo));
		}
		if (FCode.equalsIgnoreCase("TypeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeNo));
		}
		if (FCode.equalsIgnoreCase("StatusNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StatusNo));
		}
		if (FCode.equalsIgnoreCase("PriorityNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PriorityNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ApplyTypeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyTypeNo));
		}
		if (FCode.equalsIgnoreCase("AcceptWayNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcceptWayNo));
		}
		if (FCode.equalsIgnoreCase("AcceptCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcceptCom));
		}
		if (FCode.equalsIgnoreCase("AcceptorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcceptorNo));
		}
		if (FCode.equalsIgnoreCase("AcceptDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAcceptDate()));
		}
		if (FCode.equalsIgnoreCase("DateLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DateLimit));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("PauseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PauseNo));
		}
		if (FCode.equalsIgnoreCase("PauseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPauseDate()));
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
		if (FCode.equalsIgnoreCase("ApplyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyName));
		}
		if (FCode.equalsIgnoreCase("DetailWorkNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailWorkNo));
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
				strFieldValue = StrTool.GBKToUnicode(WorkNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AcceptNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TypeNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(StatusNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PriorityNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ApplyTypeNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AcceptWayNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AcceptCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AcceptorNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAcceptDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(DateLimit);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(PauseNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPauseDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ApplyName);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(DetailWorkNo);
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

		if (FCode.equalsIgnoreCase("WorkNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkNo = FValue.trim();
			}
			else
				WorkNo = null;
		}
		if (FCode.equalsIgnoreCase("AcceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcceptNo = FValue.trim();
			}
			else
				AcceptNo = null;
		}
		if (FCode.equalsIgnoreCase("NodeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeNo = FValue.trim();
			}
			else
				NodeNo = null;
		}
		if (FCode.equalsIgnoreCase("TypeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeNo = FValue.trim();
			}
			else
				TypeNo = null;
		}
		if (FCode.equalsIgnoreCase("StatusNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StatusNo = FValue.trim();
			}
			else
				StatusNo = null;
		}
		if (FCode.equalsIgnoreCase("PriorityNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PriorityNo = FValue.trim();
			}
			else
				PriorityNo = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("ApplyTypeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyTypeNo = FValue.trim();
			}
			else
				ApplyTypeNo = null;
		}
		if (FCode.equalsIgnoreCase("AcceptWayNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcceptWayNo = FValue.trim();
			}
			else
				AcceptWayNo = null;
		}
		if (FCode.equalsIgnoreCase("AcceptCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcceptCom = FValue.trim();
			}
			else
				AcceptCom = null;
		}
		if (FCode.equalsIgnoreCase("AcceptorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcceptorNo = FValue.trim();
			}
			else
				AcceptorNo = null;
		}
		if (FCode.equalsIgnoreCase("AcceptDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AcceptDate = fDate.getDate( FValue );
			}
			else
				AcceptDate = null;
		}
		if (FCode.equalsIgnoreCase("DateLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DateLimit = FValue.trim();
			}
			else
				DateLimit = null;
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
		if (FCode.equalsIgnoreCase("PauseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PauseNo = FValue.trim();
			}
			else
				PauseNo = null;
		}
		if (FCode.equalsIgnoreCase("PauseDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PauseDate = fDate.getDate( FValue );
			}
			else
				PauseDate = null;
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
		if (FCode.equalsIgnoreCase("ApplyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyName = FValue.trim();
			}
			else
				ApplyName = null;
		}
		if (FCode.equalsIgnoreCase("DetailWorkNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DetailWorkNo = FValue.trim();
			}
			else
				DetailWorkNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LGWorkSchema other = (LGWorkSchema)otherObject;
		return
			WorkNo.equals(other.getWorkNo())
			&& AcceptNo.equals(other.getAcceptNo())
			&& NodeNo.equals(other.getNodeNo())
			&& TypeNo.equals(other.getTypeNo())
			&& StatusNo.equals(other.getStatusNo())
			&& PriorityNo.equals(other.getPriorityNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& ContNo.equals(other.getContNo())
			&& ApplyTypeNo.equals(other.getApplyTypeNo())
			&& AcceptWayNo.equals(other.getAcceptWayNo())
			&& AcceptCom.equals(other.getAcceptCom())
			&& AcceptorNo.equals(other.getAcceptorNo())
			&& fDate.getString(AcceptDate).equals(other.getAcceptDate())
			&& DateLimit.equals(other.getDateLimit())
			&& Remark.equals(other.getRemark())
			&& PauseNo.equals(other.getPauseNo())
			&& fDate.getString(PauseDate).equals(other.getPauseDate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ApplyName.equals(other.getApplyName())
			&& DetailWorkNo.equals(other.getDetailWorkNo());
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
		if( strFieldName.equals("WorkNo") ) {
			return 0;
		}
		if( strFieldName.equals("AcceptNo") ) {
			return 1;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 2;
		}
		if( strFieldName.equals("TypeNo") ) {
			return 3;
		}
		if( strFieldName.equals("StatusNo") ) {
			return 4;
		}
		if( strFieldName.equals("PriorityNo") ) {
			return 5;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 6;
		}
		if( strFieldName.equals("ContNo") ) {
			return 7;
		}
		if( strFieldName.equals("ApplyTypeNo") ) {
			return 8;
		}
		if( strFieldName.equals("AcceptWayNo") ) {
			return 9;
		}
		if( strFieldName.equals("AcceptCom") ) {
			return 10;
		}
		if( strFieldName.equals("AcceptorNo") ) {
			return 11;
		}
		if( strFieldName.equals("AcceptDate") ) {
			return 12;
		}
		if( strFieldName.equals("DateLimit") ) {
			return 13;
		}
		if( strFieldName.equals("Remark") ) {
			return 14;
		}
		if( strFieldName.equals("PauseNo") ) {
			return 15;
		}
		if( strFieldName.equals("PauseDate") ) {
			return 16;
		}
		if( strFieldName.equals("Operator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 21;
		}
		if( strFieldName.equals("ApplyName") ) {
			return 22;
		}
		if( strFieldName.equals("DetailWorkNo") ) {
			return 23;
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
				strFieldName = "WorkNo";
				break;
			case 1:
				strFieldName = "AcceptNo";
				break;
			case 2:
				strFieldName = "NodeNo";
				break;
			case 3:
				strFieldName = "TypeNo";
				break;
			case 4:
				strFieldName = "StatusNo";
				break;
			case 5:
				strFieldName = "PriorityNo";
				break;
			case 6:
				strFieldName = "CustomerNo";
				break;
			case 7:
				strFieldName = "ContNo";
				break;
			case 8:
				strFieldName = "ApplyTypeNo";
				break;
			case 9:
				strFieldName = "AcceptWayNo";
				break;
			case 10:
				strFieldName = "AcceptCom";
				break;
			case 11:
				strFieldName = "AcceptorNo";
				break;
			case 12:
				strFieldName = "AcceptDate";
				break;
			case 13:
				strFieldName = "DateLimit";
				break;
			case 14:
				strFieldName = "Remark";
				break;
			case 15:
				strFieldName = "PauseNo";
				break;
			case 16:
				strFieldName = "PauseDate";
				break;
			case 17:
				strFieldName = "Operator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
				strFieldName = "ModifyTime";
				break;
			case 22:
				strFieldName = "ApplyName";
				break;
			case 23:
				strFieldName = "DetailWorkNo";
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
		if( strFieldName.equals("WorkNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StatusNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PriorityNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyTypeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcceptWayNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcceptCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcceptorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcceptDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateLimit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PauseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PauseDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("ApplyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailWorkNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
