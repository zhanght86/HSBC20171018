/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCCustomerImpartDetailSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartDetailSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCCustomerImpartDetailDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCCustomerImpartDetailDB extends LCCustomerImpartDetailSchema
{
private static Logger logger = Logger.getLogger(LCCustomerImpartDetailDB.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 错误信息

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;
	private Statement mStatement = null;
	// @Constructor
	public LCCustomerImpartDetailDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCCustomerImpartDetail" );
		mflag = true;
	}

	public LCCustomerImpartDetailDB()
	{
		con = null;
		db = new DBOper( "LCCustomerImpartDetail" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCCustomerImpartDetailSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCCustomerImpartDetailSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("DELETE FROM LCCustomerImpartDetail WHERE  GrpContNo = ? AND ProposalContNo = ? AND ImpartCode = ? AND ImpartVer = ? AND SubSerialNo = ? AND CustomerNo = ? AND CustomerNoType = ?");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getProposalContNo());
			}
			if(this.getImpartCode() == null || this.getImpartCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getImpartCode());
			}
			if(this.getImpartVer() == null || this.getImpartVer().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getImpartVer());
			}
			if(this.getSubSerialNo() == null || this.getSubSerialNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getSubSerialNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCustomerNo());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, StrTool.space1(this.getCustomerNoType(), 1));
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCustomerImpartDetail");
		sqlObj.setSQL(4, this);
		sqlObj.getSQL();

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCustomerImpartDetail");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LCCustomerImpartDetail SET  GrpContNo = ? , ContNo = ? , ProposalContNo = ? , PrtNo = ? , ImpartCode = ? , ImpartVer = ? , SubSerialNo = ? , ImpartDetailContent = ? , DiseaseContent = ? , StartDate = ? , EndDate = ? , Prover = ? , CurrCondition = ? , IsProved = ? , CustomerNo = ? , CustomerNoType = ? , UWClaimFlg = ? , PrtFlag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , PatchNo = ? WHERE  GrpContNo = ? AND ProposalContNo = ? AND ImpartCode = ? AND ImpartVer = ? AND SubSerialNo = ? AND CustomerNo = ? AND CustomerNoType = ?");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getProposalContNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPrtNo());
			}
			if(this.getImpartCode() == null || this.getImpartCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getImpartCode());
			}
			if(this.getImpartVer() == null || this.getImpartVer().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getImpartVer());
			}
			if(this.getSubSerialNo() == null || this.getSubSerialNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getSubSerialNo());
			}
			if(this.getImpartDetailContent() == null || this.getImpartDetailContent().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getImpartDetailContent());
			}
			if(this.getDiseaseContent() == null || this.getDiseaseContent().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getDiseaseContent());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getStartDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getEndDate()));
			}
			if(this.getProver() == null || this.getProver().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getProver());
			}
			if(this.getCurrCondition() == null || this.getCurrCondition().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getCurrCondition());
			}
			if(this.getIsProved() == null || this.getIsProved().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getIsProved());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getCustomerNo());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getCustomerNoType());
			}
			if(this.getUWClaimFlg() == null || this.getUWClaimFlg().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getUWClaimFlg());
			}
			if(this.getPrtFlag() == null || this.getPrtFlag().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getPrtFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getModifyTime());
			}
			pstmt.setInt(24, this.getPatchNo());
			// set where condition
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getGrpContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getProposalContNo());
			}
			if(this.getImpartCode() == null || this.getImpartCode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getImpartCode());
			}
			if(this.getImpartVer() == null || this.getImpartVer().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getImpartVer());
			}
			if(this.getSubSerialNo() == null || this.getSubSerialNo().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getSubSerialNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getCustomerNo());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, StrTool.space1(this.getCustomerNoType(), 1));
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCustomerImpartDetail");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LCCustomerImpartDetail(GrpContNo ,ContNo ,ProposalContNo ,PrtNo ,ImpartCode ,ImpartVer ,SubSerialNo ,ImpartDetailContent ,DiseaseContent ,StartDate ,EndDate ,Prover ,CurrCondition ,IsProved ,CustomerNo ,CustomerNoType ,UWClaimFlg ,PrtFlag ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,PatchNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getProposalContNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPrtNo());
			}
			if(this.getImpartCode() == null || this.getImpartCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getImpartCode());
			}
			if(this.getImpartVer() == null || this.getImpartVer().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getImpartVer());
			}
			if(this.getSubSerialNo() == null || this.getSubSerialNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getSubSerialNo());
			}
			if(this.getImpartDetailContent() == null || this.getImpartDetailContent().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getImpartDetailContent());
			}
			if(this.getDiseaseContent() == null || this.getDiseaseContent().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getDiseaseContent());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getStartDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getEndDate()));
			}
			if(this.getProver() == null || this.getProver().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getProver());
			}
			if(this.getCurrCondition() == null || this.getCurrCondition().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getCurrCondition());
			}
			if(this.getIsProved() == null || this.getIsProved().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getIsProved());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getCustomerNo());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getCustomerNoType());
			}
			if(this.getUWClaimFlg() == null || this.getUWClaimFlg().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getUWClaimFlg());
			}
			if(this.getPrtFlag() == null || this.getPrtFlag().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getPrtFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getModifyTime());
			}
			pstmt.setInt(24, this.getPatchNo());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean getInfo()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("SELECT * FROM LCCustomerImpartDetail WHERE  GrpContNo = ? AND ProposalContNo = ? AND ImpartCode = ? AND ImpartVer = ? AND SubSerialNo = ? AND CustomerNo = ? AND CustomerNoType = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getProposalContNo());
			}
			if(this.getImpartCode() == null || this.getImpartCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getImpartCode());
			}
			if(this.getImpartVer() == null || this.getImpartVer().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getImpartVer());
			}
			if(this.getSubSerialNo() == null || this.getSubSerialNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getSubSerialNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCustomerNo());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, StrTool.space1(this.getCustomerNoType(), 1));
			}
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCCustomerImpartDetailDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors .addOneError(tError);

					try{ rs.close(); } catch( Exception ex ) {}
					try{ pstmt.close(); } catch( Exception ex1 ) {}

					if (!mflag)
					{
						try
						{
							con.close();
						}
						catch(Exception et){}
					}
					return false;
				}
				break;
			}
			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if( i == 0 )
			{
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

	public LCCustomerImpartDetailSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCCustomerImpartDetailSet aLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCCustomerImpartDetail");
			LCCustomerImpartDetailSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				LCCustomerImpartDetailSchema s1 = new LCCustomerImpartDetailSchema();
				s1.setSchema(rs,i);
				aLCCustomerImpartDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLCCustomerImpartDetailSet;

	}

	public LCCustomerImpartDetailSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCCustomerImpartDetailSet aLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;
				LCCustomerImpartDetailSchema s1 = new LCCustomerImpartDetailSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCCustomerImpartDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCCustomerImpartDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLCCustomerImpartDetailSet;
	}

	public LCCustomerImpartDetailSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCCustomerImpartDetailSet aLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCCustomerImpartDetail");
			LCCustomerImpartDetailSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LCCustomerImpartDetailSchema s1 = new LCCustomerImpartDetailSchema();
				s1.setSchema(rs,i);
				aLCCustomerImpartDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLCCustomerImpartDetailSet;

	}

	public LCCustomerImpartDetailSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCCustomerImpartDetailSet aLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LCCustomerImpartDetailSchema s1 = new LCCustomerImpartDetailSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCCustomerImpartDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCCustomerImpartDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLCCustomerImpartDetailSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LCCustomerImpartDetail");
			LCCustomerImpartDetailSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCCustomerImpartDetail " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCCustomerImpartDetailDB";
				tError.functionName = "update";
				tError.errorMessage = "更新数据失败!";
				this.mErrors .addOneError(tError);

				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

/**
 * 准备数据查询条件
 * @param strSQL String
 * @return boolean
 */
public boolean prepareData(String strSQL)
{
    if (mResultSet != null)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LCCustomerImpartDetailDB";
        tError.functionName = "prepareData";
        tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
        this.mErrors.addOneError(tError);
        return false;
    }

    if (!mflag)
    {
        con = DBConnPool.getConnection();
    }
    try
    {
        mStatement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));
    }
    catch (Exception e)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LCCustomerImpartDetailDB";
        tError.functionName = "prepareData";
        tError.errorMessage = e.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }

    if (!mflag)
    {
        try
        {
            con.close();
        }
        catch (Exception e)
        {}
    }
    return true;
}

/**
 * 获取数据集
 * @return boolean
 */
public boolean hasMoreData()
{
    boolean flag = true;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCCustomerImpartDetailDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return false;
    }
    try
    {
        flag = mResultSet.next();
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCCustomerImpartDetailDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }
    return flag;
}
/**
 * 获取定量数据
 * @return LCCustomerImpartDetailSet
 */
public LCCustomerImpartDetailSet getData()
{
    int tCount = 0;
    LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
    LCCustomerImpartDetailSchema tLCCustomerImpartDetailSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCCustomerImpartDetailDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCCustomerImpartDetailSchema = new LCCustomerImpartDetailSchema();
        tLCCustomerImpartDetailSchema.setSchema(mResultSet, 1);
        tLCCustomerImpartDetailSet.add(tLCCustomerImpartDetailSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCCustomerImpartDetailSchema = new LCCustomerImpartDetailSchema();
                tLCCustomerImpartDetailSchema.setSchema(mResultSet, 1);
                tLCCustomerImpartDetailSet.add(tLCCustomerImpartDetailSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCCustomerImpartDetailDB";
        tError.functionName = "getData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return null;
    }
    return tLCCustomerImpartDetailSet;
}
/**
 * 关闭数据集
 * @return boolean
 */
public boolean closeData()
{
    boolean flag = true;
    try
    {
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "LCCustomerImpartDetailDB";
            tError.functionName = "closeData";
            tError.errorMessage = "数据集已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mResultSet.close();
            mResultSet = null;
        }
    }
    catch (Exception ex2)
    {
        CError tError = new CError();
        tError.moduleName = "LCCustomerImpartDetailDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex2.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    try
    {
        if (null == mStatement)
        {
            CError tError = new CError();
            tError.moduleName = "LCCustomerImpartDetailDB";
            tError.functionName = "closeData";
            tError.errorMessage = "语句已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mStatement.close();
            mStatement = null;
        }
    }
    catch (Exception ex3)
    {
        CError tError = new CError();
        tError.moduleName = "LCCustomerImpartDetailDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LCCustomerImpartDetailSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCCustomerImpartDetailSet aLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				LCCustomerImpartDetailSchema s1 = new LCCustomerImpartDetailSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCCustomerImpartDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCCustomerImpartDetailSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch(Exception ex2) {}
			try{ pstmt.close(); } catch(Exception ex3) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e) {}
		}

		return aLCCustomerImpartDetailSet;
	}

	public LCCustomerImpartDetailSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCCustomerImpartDetailSet aLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break; 
				}

				LCCustomerImpartDetailSchema s1 = new LCCustomerImpartDetailSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCCustomerImpartDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCCustomerImpartDetailSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCustomerImpartDetailDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e){}
		}

		return aLCCustomerImpartDetailSet; 
	}

}
