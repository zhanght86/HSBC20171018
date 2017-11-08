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
import com.sinosoft.lis.db.RnewPENoticeReplyDB;

/*
 * <p>ClassName: RnewPENoticeReplySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 续期续保二核
 */
public class RnewPENoticeReplySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(RnewPENoticeReplySchema.class);

	// @Field
	/** 合同号码 */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 打印流水号 */
	private String PrtSeq;
	/** 体检项目编号 */
	private String PEItemCode;
	/** 体检项目名称 */
	private String PEItemName;
	/** 体检项目细化编号 */
	private String PEItemDivCode;
	/** 体检项目细化名称 */
	private String PEItemDivName;
	/** 体检项目细化结论 */
	private String PEItemDivDes;
	/** 体检项目细化结果 */
	private String PEItemDivRes;
	/** 体检项目细化正常值 */
	private String PEItemDivNor;
	/** 是否免检 */
	private String FreePE;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RnewPENoticeReplySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ProposalContNo";
		pk[1] = "PrtSeq";
		pk[2] = "PEItemDivCode";

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
		RnewPENoticeReplySchema cloned = (RnewPENoticeReplySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public String getPrtSeq()
	{
		return PrtSeq;
	}
	public void setPrtSeq(String aPrtSeq)
	{
		PrtSeq = aPrtSeq;
	}
	public String getPEItemCode()
	{
		return PEItemCode;
	}
	public void setPEItemCode(String aPEItemCode)
	{
		PEItemCode = aPEItemCode;
	}
	public String getPEItemName()
	{
		return PEItemName;
	}
	public void setPEItemName(String aPEItemName)
	{
		PEItemName = aPEItemName;
	}
	public String getPEItemDivCode()
	{
		return PEItemDivCode;
	}
	public void setPEItemDivCode(String aPEItemDivCode)
	{
		PEItemDivCode = aPEItemDivCode;
	}
	public String getPEItemDivName()
	{
		return PEItemDivName;
	}
	public void setPEItemDivName(String aPEItemDivName)
	{
		PEItemDivName = aPEItemDivName;
	}
	public String getPEItemDivDes()
	{
		return PEItemDivDes;
	}
	public void setPEItemDivDes(String aPEItemDivDes)
	{
		PEItemDivDes = aPEItemDivDes;
	}
	public String getPEItemDivRes()
	{
		return PEItemDivRes;
	}
	public void setPEItemDivRes(String aPEItemDivRes)
	{
		PEItemDivRes = aPEItemDivRes;
	}
	public String getPEItemDivNor()
	{
		return PEItemDivNor;
	}
	public void setPEItemDivNor(String aPEItemDivNor)
	{
		PEItemDivNor = aPEItemDivNor;
	}
	/**
	* Y －－ 免检<p>
	* N －－ 不免检
	*/
	public String getFreePE()
	{
		return FreePE;
	}
	public void setFreePE(String aFreePE)
	{
		FreePE = aFreePE;
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
	* 使用另外一个 RnewPENoticeReplySchema 对象给 Schema 赋值
	* @param: aRnewPENoticeReplySchema RnewPENoticeReplySchema
	**/
	public void setSchema(RnewPENoticeReplySchema aRnewPENoticeReplySchema)
	{
		this.ContNo = aRnewPENoticeReplySchema.getContNo();
		this.ProposalContNo = aRnewPENoticeReplySchema.getProposalContNo();
		this.PrtSeq = aRnewPENoticeReplySchema.getPrtSeq();
		this.PEItemCode = aRnewPENoticeReplySchema.getPEItemCode();
		this.PEItemName = aRnewPENoticeReplySchema.getPEItemName();
		this.PEItemDivCode = aRnewPENoticeReplySchema.getPEItemDivCode();
		this.PEItemDivName = aRnewPENoticeReplySchema.getPEItemDivName();
		this.PEItemDivDes = aRnewPENoticeReplySchema.getPEItemDivDes();
		this.PEItemDivRes = aRnewPENoticeReplySchema.getPEItemDivRes();
		this.PEItemDivNor = aRnewPENoticeReplySchema.getPEItemDivNor();
		this.FreePE = aRnewPENoticeReplySchema.getFreePE();
		this.ModifyDate = fDate.getDate( aRnewPENoticeReplySchema.getModifyDate());
		this.ModifyTime = aRnewPENoticeReplySchema.getModifyTime();
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
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("PrtSeq") == null )
				this.PrtSeq = null;
			else
				this.PrtSeq = rs.getString("PrtSeq").trim();

			if( rs.getString("PEItemCode") == null )
				this.PEItemCode = null;
			else
				this.PEItemCode = rs.getString("PEItemCode").trim();

			if( rs.getString("PEItemName") == null )
				this.PEItemName = null;
			else
				this.PEItemName = rs.getString("PEItemName").trim();

			if( rs.getString("PEItemDivCode") == null )
				this.PEItemDivCode = null;
			else
				this.PEItemDivCode = rs.getString("PEItemDivCode").trim();

			if( rs.getString("PEItemDivName") == null )
				this.PEItemDivName = null;
			else
				this.PEItemDivName = rs.getString("PEItemDivName").trim();

			if( rs.getString("PEItemDivDes") == null )
				this.PEItemDivDes = null;
			else
				this.PEItemDivDes = rs.getString("PEItemDivDes").trim();

			if( rs.getString("PEItemDivRes") == null )
				this.PEItemDivRes = null;
			else
				this.PEItemDivRes = rs.getString("PEItemDivRes").trim();

			if( rs.getString("PEItemDivNor") == null )
				this.PEItemDivNor = null;
			else
				this.PEItemDivNor = rs.getString("PEItemDivNor").trim();

			if( rs.getString("FreePE") == null )
				this.FreePE = null;
			else
				this.FreePE = rs.getString("FreePE").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的RnewPENoticeReply表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RnewPENoticeReplySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RnewPENoticeReplySchema getSchema()
	{
		RnewPENoticeReplySchema aRnewPENoticeReplySchema = new RnewPENoticeReplySchema();
		aRnewPENoticeReplySchema.setSchema(this);
		return aRnewPENoticeReplySchema;
	}

	public RnewPENoticeReplyDB getDB()
	{
		RnewPENoticeReplyDB aDBOper = new RnewPENoticeReplyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRnewPENoticeReply描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEItemName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEItemDivCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEItemDivName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEItemDivDes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEItemDivRes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEItemDivNor)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FreePE)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRnewPENoticeReply>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PEItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PEItemName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PEItemDivCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PEItemDivName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PEItemDivDes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PEItemDivRes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PEItemDivNor = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			FreePE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RnewPENoticeReplySchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSeq));
		}
		if (FCode.equalsIgnoreCase("PEItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEItemCode));
		}
		if (FCode.equalsIgnoreCase("PEItemName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEItemName));
		}
		if (FCode.equalsIgnoreCase("PEItemDivCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEItemDivCode));
		}
		if (FCode.equalsIgnoreCase("PEItemDivName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEItemDivName));
		}
		if (FCode.equalsIgnoreCase("PEItemDivDes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEItemDivDes));
		}
		if (FCode.equalsIgnoreCase("PEItemDivRes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEItemDivRes));
		}
		if (FCode.equalsIgnoreCase("PEItemDivNor"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEItemDivNor));
		}
		if (FCode.equalsIgnoreCase("FreePE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FreePE));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PrtSeq);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PEItemCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PEItemName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PEItemDivCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PEItemDivName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PEItemDivDes);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PEItemDivRes);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PEItemDivNor);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(FreePE);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 12:
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
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtSeq = FValue.trim();
			}
			else
				PrtSeq = null;
		}
		if (FCode.equalsIgnoreCase("PEItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEItemCode = FValue.trim();
			}
			else
				PEItemCode = null;
		}
		if (FCode.equalsIgnoreCase("PEItemName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEItemName = FValue.trim();
			}
			else
				PEItemName = null;
		}
		if (FCode.equalsIgnoreCase("PEItemDivCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEItemDivCode = FValue.trim();
			}
			else
				PEItemDivCode = null;
		}
		if (FCode.equalsIgnoreCase("PEItemDivName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEItemDivName = FValue.trim();
			}
			else
				PEItemDivName = null;
		}
		if (FCode.equalsIgnoreCase("PEItemDivDes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEItemDivDes = FValue.trim();
			}
			else
				PEItemDivDes = null;
		}
		if (FCode.equalsIgnoreCase("PEItemDivRes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEItemDivRes = FValue.trim();
			}
			else
				PEItemDivRes = null;
		}
		if (FCode.equalsIgnoreCase("PEItemDivNor"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEItemDivNor = FValue.trim();
			}
			else
				PEItemDivNor = null;
		}
		if (FCode.equalsIgnoreCase("FreePE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FreePE = FValue.trim();
			}
			else
				FreePE = null;
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
		RnewPENoticeReplySchema other = (RnewPENoticeReplySchema)otherObject;
		return
			ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtSeq.equals(other.getPrtSeq())
			&& PEItemCode.equals(other.getPEItemCode())
			&& PEItemName.equals(other.getPEItemName())
			&& PEItemDivCode.equals(other.getPEItemDivCode())
			&& PEItemDivName.equals(other.getPEItemDivName())
			&& PEItemDivDes.equals(other.getPEItemDivDes())
			&& PEItemDivRes.equals(other.getPEItemDivRes())
			&& PEItemDivNor.equals(other.getPEItemDivNor())
			&& FreePE.equals(other.getFreePE())
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
		if( strFieldName.equals("ContNo") ) {
			return 0;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 1;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return 2;
		}
		if( strFieldName.equals("PEItemCode") ) {
			return 3;
		}
		if( strFieldName.equals("PEItemName") ) {
			return 4;
		}
		if( strFieldName.equals("PEItemDivCode") ) {
			return 5;
		}
		if( strFieldName.equals("PEItemDivName") ) {
			return 6;
		}
		if( strFieldName.equals("PEItemDivDes") ) {
			return 7;
		}
		if( strFieldName.equals("PEItemDivRes") ) {
			return 8;
		}
		if( strFieldName.equals("PEItemDivNor") ) {
			return 9;
		}
		if( strFieldName.equals("FreePE") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 12;
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
				strFieldName = "ContNo";
				break;
			case 1:
				strFieldName = "ProposalContNo";
				break;
			case 2:
				strFieldName = "PrtSeq";
				break;
			case 3:
				strFieldName = "PEItemCode";
				break;
			case 4:
				strFieldName = "PEItemName";
				break;
			case 5:
				strFieldName = "PEItemDivCode";
				break;
			case 6:
				strFieldName = "PEItemDivName";
				break;
			case 7:
				strFieldName = "PEItemDivDes";
				break;
			case 8:
				strFieldName = "PEItemDivRes";
				break;
			case 9:
				strFieldName = "PEItemDivNor";
				break;
			case 10:
				strFieldName = "FreePE";
				break;
			case 11:
				strFieldName = "ModifyDate";
				break;
			case 12:
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEItemName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEItemDivCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEItemDivName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEItemDivDes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEItemDivRes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEItemDivNor") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FreePE") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
