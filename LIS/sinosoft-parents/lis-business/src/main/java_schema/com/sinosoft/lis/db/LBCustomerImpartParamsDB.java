/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBCustomerImpartParamsSchema;
import com.sinosoft.lis.vschema.LBCustomerImpartParamsSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBCustomerImpartParamsDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LBCustomerImpartParamsDB extends LBCustomerImpartParamsSchema
{
private static Logger logger = Logger.getLogger(LBCustomerImpartParamsDB.class);

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
	public LBCustomerImpartParamsDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LBCustomerImpartParams" );
		mflag = true;
	}

	public LBCustomerImpartParamsDB()
	{
		con = null;
		db = new DBOper( "LBCustomerImpartParams" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LBCustomerImpartParamsSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LBCustomerImpartParamsSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
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
			pstmt = con.prepareStatement("DELETE FROM LBCustomerImpartParams WHERE  ProposalContNo = ? AND ImpartCode = ? AND ImpartVer = ? AND CustomerNo = ? AND CustomerNoType = ? AND ImpartParamNo = ? AND PatchNo = ?");
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getProposalContNo());
			}
			if(this.getImpartCode() == null || this.getImpartCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getImpartCode());
			}
			if(this.getImpartVer() == null || this.getImpartVer().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getImpartVer());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCustomerNo());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, StrTool.space1(this.getCustomerNoType(), 1));
			}
			if(this.getImpartParamNo() == null || this.getImpartParamNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getImpartParamNo());
			}
			pstmt.setInt(7, this.getPatchNo());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBCustomerImpartParams");
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
		SQLString sqlObj = new SQLString("LBCustomerImpartParams");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LBCustomerImpartParams SET  EdorNo = ? , GrpContNo = ? , ContNo = ? , ProposalContNo = ? , PrtNo = ? , ImpartCode = ? , ImpartVer = ? , CustomerNo = ? , CustomerNoType = ? , ImpartParamNo = ? , ImpartParamName = ? , ImpartParam = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , PatchNo = ? WHERE  ProposalContNo = ? AND ImpartCode = ? AND ImpartVer = ? AND CustomerNo = ? AND CustomerNoType = ? AND ImpartParamNo = ? AND PatchNo = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getProposalContNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPrtNo());
			}
			if(this.getImpartCode() == null || this.getImpartCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getImpartCode());
			}
			if(this.getImpartVer() == null || this.getImpartVer().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getImpartVer());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getCustomerNo());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getCustomerNoType());
			}
			if(this.getImpartParamNo() == null || this.getImpartParamNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getImpartParamNo());
			}
			if(this.getImpartParamName() == null || this.getImpartParamName().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getImpartParamName());
			}
			if(this.getImpartParam() == null || this.getImpartParam().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getImpartParam());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getModifyTime());
			}
			pstmt.setInt(18, this.getPatchNo());
			// set where condition
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getProposalContNo());
			}
			if(this.getImpartCode() == null || this.getImpartCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getImpartCode());
			}
			if(this.getImpartVer() == null || this.getImpartVer().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getImpartVer());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getCustomerNo());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, StrTool.space1(this.getCustomerNoType(), 1));
			}
			if(this.getImpartParamNo() == null || this.getImpartParamNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getImpartParamNo());
			}
			pstmt.setInt(25, this.getPatchNo());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
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
		SQLString sqlObj = new SQLString("LBCustomerImpartParams");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LBCustomerImpartParams(EdorNo ,GrpContNo ,ContNo ,ProposalContNo ,PrtNo ,ImpartCode ,ImpartVer ,CustomerNo ,CustomerNoType ,ImpartParamNo ,ImpartParamName ,ImpartParam ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,PatchNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getProposalContNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPrtNo());
			}
			if(this.getImpartCode() == null || this.getImpartCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getImpartCode());
			}
			if(this.getImpartVer() == null || this.getImpartVer().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getImpartVer());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getCustomerNo());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getCustomerNoType());
			}
			if(this.getImpartParamNo() == null || this.getImpartParamNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getImpartParamNo());
			}
			if(this.getImpartParamName() == null || this.getImpartParamName().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getImpartParamName());
			}
			if(this.getImpartParam() == null || this.getImpartParam().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getImpartParam());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getModifyTime());
			}
			pstmt.setInt(18, this.getPatchNo());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LBCustomerImpartParams WHERE  ProposalContNo = ? AND ImpartCode = ? AND ImpartVer = ? AND CustomerNo = ? AND CustomerNoType = ? AND ImpartParamNo = ? AND PatchNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getProposalContNo());
			}
			if(this.getImpartCode() == null || this.getImpartCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getImpartCode());
			}
			if(this.getImpartVer() == null || this.getImpartVer().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getImpartVer());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCustomerNo());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, StrTool.space1(this.getCustomerNoType(), 1));
			}
			if(this.getImpartParamNo() == null || this.getImpartParamNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getImpartParamNo());
			}
			pstmt.setInt(7, this.getPatchNo());
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBCustomerImpartParamsDB";
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
			tError.moduleName = "LBCustomerImpartParamsDB";
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

	public LBCustomerImpartParamsSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBCustomerImpartParamsSet aLBCustomerImpartParamsSet = new LBCustomerImpartParamsSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBCustomerImpartParams");
			LBCustomerImpartParamsSchema aSchema = this.getSchema();
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
				LBCustomerImpartParamsSchema s1 = new LBCustomerImpartParamsSchema();
				s1.setSchema(rs,i);
				aLBCustomerImpartParamsSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
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

		return aLBCustomerImpartParamsSet;

	}

	public LBCustomerImpartParamsSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBCustomerImpartParamsSet aLBCustomerImpartParamsSet = new LBCustomerImpartParamsSet();

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
				LBCustomerImpartParamsSchema s1 = new LBCustomerImpartParamsSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBCustomerImpartParamsDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBCustomerImpartParamsSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
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

		return aLBCustomerImpartParamsSet;
	}

	public LBCustomerImpartParamsSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBCustomerImpartParamsSet aLBCustomerImpartParamsSet = new LBCustomerImpartParamsSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBCustomerImpartParams");
			LBCustomerImpartParamsSchema aSchema = this.getSchema();
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

				LBCustomerImpartParamsSchema s1 = new LBCustomerImpartParamsSchema();
				s1.setSchema(rs,i);
				aLBCustomerImpartParamsSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
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

		return aLBCustomerImpartParamsSet;

	}

	public LBCustomerImpartParamsSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBCustomerImpartParamsSet aLBCustomerImpartParamsSet = new LBCustomerImpartParamsSet();

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

				LBCustomerImpartParamsSchema s1 = new LBCustomerImpartParamsSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBCustomerImpartParamsDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBCustomerImpartParamsSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
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

		return aLBCustomerImpartParamsSet;
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
			SQLString sqlObj = new SQLString("LBCustomerImpartParams");
			LBCustomerImpartParamsSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LBCustomerImpartParams " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LBCustomerImpartParamsDB";
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
			tError.moduleName = "LBCustomerImpartParamsDB";
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
        tError.moduleName = "LBCustomerImpartParamsDB";
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
        tError.moduleName = "LBCustomerImpartParamsDB";
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
        tError.moduleName = "LBCustomerImpartParamsDB";
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
        tError.moduleName = "LBCustomerImpartParamsDB";
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
 * @return LBCustomerImpartParamsSet
 */
public LBCustomerImpartParamsSet getData()
{
    int tCount = 0;
    LBCustomerImpartParamsSet tLBCustomerImpartParamsSet = new LBCustomerImpartParamsSet();
    LBCustomerImpartParamsSchema tLBCustomerImpartParamsSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LBCustomerImpartParamsDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLBCustomerImpartParamsSchema = new LBCustomerImpartParamsSchema();
        tLBCustomerImpartParamsSchema.setSchema(mResultSet, 1);
        tLBCustomerImpartParamsSet.add(tLBCustomerImpartParamsSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLBCustomerImpartParamsSchema = new LBCustomerImpartParamsSchema();
                tLBCustomerImpartParamsSchema.setSchema(mResultSet, 1);
                tLBCustomerImpartParamsSet.add(tLBCustomerImpartParamsSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LBCustomerImpartParamsDB";
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
    return tLBCustomerImpartParamsSet;
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
            tError.moduleName = "LBCustomerImpartParamsDB";
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
        tError.moduleName = "LBCustomerImpartParamsDB";
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
            tError.moduleName = "LBCustomerImpartParamsDB";
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
        tError.moduleName = "LBCustomerImpartParamsDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LBCustomerImpartParamsSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBCustomerImpartParamsSet aLBCustomerImpartParamsSet = new LBCustomerImpartParamsSet();

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
				LBCustomerImpartParamsSchema s1 = new LBCustomerImpartParamsSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBCustomerImpartParamsDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBCustomerImpartParamsSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
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

		return aLBCustomerImpartParamsSet;
	}

	public LBCustomerImpartParamsSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBCustomerImpartParamsSet aLBCustomerImpartParamsSet = new LBCustomerImpartParamsSet();

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

				LBCustomerImpartParamsSchema s1 = new LBCustomerImpartParamsSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBCustomerImpartParamsDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBCustomerImpartParamsSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartParamsDB";
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

		return aLBCustomerImpartParamsSet; 
	}

}
