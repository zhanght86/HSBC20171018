/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPCSpecDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全流程
 */
public class LPCSpecDB extends LPCSpecSchema
{
private static Logger logger = Logger.getLogger(LPCSpecDB.class);

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
	public LPCSpecDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LPCSpec" );
		mflag = true;
	}

	public LPCSpecDB()
	{
		con = null;
		db = new DBOper( "LPCSpec" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPCSpecSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPCSpecSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
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
			pstmt = con.prepareStatement("DELETE FROM LPCSpec WHERE  EdorNo = ? AND EdorType = ? AND ContNo = ? AND SerialNo = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSerialNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPCSpec");
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
		SQLString sqlObj = new SQLString("LPCSpec");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LPCSpec SET  EdorNo = ? , EdorType = ? , GrpContNo = ? , ContNo = ? , ProposalContNo = ? , PrtSeq = ? , SerialNo = ? , EndorsementNo = ? , SpecType = ? , SpecCode = ? , SpecContent = ? , PrtFlag = ? , BackupType = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , CustomerNo = ? , SpecReason = ? , NeedPrint = ? WHERE  EdorNo = ? AND EdorType = ? AND ContNo = ? AND SerialNo = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getProposalContNo());
			}
			if(this.getPrtSeq() == null || this.getPrtSeq().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPrtSeq());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getSerialNo());
			}
			if(this.getEndorsementNo() == null || this.getEndorsementNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getEndorsementNo());
			}
			if(this.getSpecType() == null || this.getSpecType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getSpecType());
			}
			if(this.getSpecCode() == null || this.getSpecCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getSpecCode());
			}
			if(this.getSpecContent() == null || this.getSpecContent().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getSpecContent());
			}
			if(this.getPrtFlag() == null || this.getPrtFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getPrtFlag());
			}
			if(this.getBackupType() == null || this.getBackupType().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getBackupType());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getModifyTime());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getCustomerNo());
			}
			if(this.getSpecReason() == null || this.getSpecReason().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getSpecReason());
			}
			if(this.getNeedPrint() == null || this.getNeedPrint().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getNeedPrint());
			}
			// set where condition
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getEdorType());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getContNo());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getSerialNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
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
		SQLString sqlObj = new SQLString("LPCSpec");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPCSpec(EdorNo ,EdorType ,GrpContNo ,ContNo ,ProposalContNo ,PrtSeq ,SerialNo ,EndorsementNo ,SpecType ,SpecCode ,SpecContent ,PrtFlag ,BackupType ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,CustomerNo ,SpecReason ,NeedPrint) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getProposalContNo());
			}
			if(this.getPrtSeq() == null || this.getPrtSeq().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPrtSeq());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getSerialNo());
			}
			if(this.getEndorsementNo() == null || this.getEndorsementNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getEndorsementNo());
			}
			if(this.getSpecType() == null || this.getSpecType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getSpecType());
			}
			if(this.getSpecCode() == null || this.getSpecCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getSpecCode());
			}
			if(this.getSpecContent() == null || this.getSpecContent().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getSpecContent());
			}
			if(this.getPrtFlag() == null || this.getPrtFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getPrtFlag());
			}
			if(this.getBackupType() == null || this.getBackupType().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getBackupType());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getModifyTime());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getCustomerNo());
			}
			if(this.getSpecReason() == null || this.getSpecReason().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getSpecReason());
			}
			if(this.getNeedPrint() == null || this.getNeedPrint().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getNeedPrint());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LPCSpec WHERE  EdorNo = ? AND EdorType = ? AND ContNo = ? AND SerialNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSerialNo());
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
					tError.moduleName = "LPCSpecDB";
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
			tError.moduleName = "LPCSpecDB";
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

	public LPCSpecSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPCSpecSet aLPCSpecSet = new LPCSpecSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPCSpec");
			LPCSpecSchema aSchema = this.getSchema();
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
				LPCSpecSchema s1 = new LPCSpecSchema();
				s1.setSchema(rs,i);
				aLPCSpecSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
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

		return aLPCSpecSet;

	}

	public LPCSpecSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPCSpecSet aLPCSpecSet = new LPCSpecSet();

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
				LPCSpecSchema s1 = new LPCSpecSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPCSpecDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPCSpecSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
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

		return aLPCSpecSet;
	}

	public LPCSpecSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPCSpecSet aLPCSpecSet = new LPCSpecSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPCSpec");
			LPCSpecSchema aSchema = this.getSchema();
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

				LPCSpecSchema s1 = new LPCSpecSchema();
				s1.setSchema(rs,i);
				aLPCSpecSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
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

		return aLPCSpecSet;

	}

	public LPCSpecSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPCSpecSet aLPCSpecSet = new LPCSpecSet();

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

				LPCSpecSchema s1 = new LPCSpecSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPCSpecDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPCSpecSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
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

		return aLPCSpecSet;
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
			SQLString sqlObj = new SQLString("LPCSpec");
			LPCSpecSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LPCSpec " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPCSpecDB";
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
			tError.moduleName = "LPCSpecDB";
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
        tError.moduleName = "LPCSpecDB";
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
        tError.moduleName = "LPCSpecDB";
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
        tError.moduleName = "LPCSpecDB";
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
        tError.moduleName = "LPCSpecDB";
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
 * @return LPCSpecSet
 */
public LPCSpecSet getData()
{
    int tCount = 0;
    LPCSpecSet tLPCSpecSet = new LPCSpecSet();
    LPCSpecSchema tLPCSpecSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPCSpecDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLPCSpecSchema = new LPCSpecSchema();
        tLPCSpecSchema.setSchema(mResultSet, 1);
        tLPCSpecSet.add(tLPCSpecSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLPCSpecSchema = new LPCSpecSchema();
                tLPCSpecSchema.setSchema(mResultSet, 1);
                tLPCSpecSet.add(tLPCSpecSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPCSpecDB";
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
    return tLPCSpecSet;
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
            tError.moduleName = "LPCSpecDB";
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
        tError.moduleName = "LPCSpecDB";
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
            tError.moduleName = "LPCSpecDB";
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
        tError.moduleName = "LPCSpecDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LPCSpecSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPCSpecSet aLPCSpecSet = new LPCSpecSet();

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
				LPCSpecSchema s1 = new LPCSpecSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPCSpecDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPCSpecSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
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

		return aLPCSpecSet;
	}

	public LPCSpecSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPCSpecSet aLPCSpecSet = new LPCSpecSet();

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

				LPCSpecSchema s1 = new LPCSpecSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPCSpecDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPCSpecSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPCSpecDB";
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

		return aLPCSpecSet; 
	}

}
