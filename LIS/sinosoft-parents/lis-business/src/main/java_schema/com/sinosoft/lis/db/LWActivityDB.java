/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LWActivitySchema;
import com.sinosoft.lis.vschema.LWActivitySet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LWActivityDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWActivityDB extends LWActivitySchema
{
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
	public LWActivityDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LWActivity" );
		mflag = true;
	}

	public LWActivityDB()
	{
		con = null;
		db = new DBOper( "LWActivity" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LWActivitySchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LWActivitySchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
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
			pstmt = con.prepareStatement("DELETE FROM LWActivity WHERE  ActivityID = ?");
			if(this.getActivityID() == null || this.getActivityID().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getActivityID());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LWActivity");
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
		SQLString sqlObj = new SQLString("LWActivity");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LWActivity SET  ActivityID = ? , ActivityName = ? , ActivityType = ? , ActivityDesc = ? , BeforeInit = ? , BeforeInitType = ? , AfterInit = ? , AfterInitType = ? , BeforeEnd = ? , BeforeEndType = ? , AfterEnd = ? , AfterEndType = ? , WatingTime = ? , WorkingTime = ? , TimeOut = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BusiType = ? , IsNeed = ? , ActivityFlag = ? , ImpDegree = ? , FunctionID = ? , CreateAction = ? , CreateActionType = ? , ApplyAction = ? , ApplyActionType = ? , DeleteAction = ? , DeleteActionType = ? , Together = ? , MenuNodeCode = ? WHERE  ActivityID = ?");
			if(this.getActivityID() == null || this.getActivityID().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getActivityID());
			}
			if(this.getActivityName() == null || this.getActivityName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getActivityName());
			}
			if(this.getActivityType() == null || this.getActivityType().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, this.getActivityType());
			}
			if(this.getActivityDesc() == null || this.getActivityDesc().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getActivityDesc());
			}
			if(this.getBeforeInit() == null || this.getBeforeInit().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getBeforeInit());
			}
			if(this.getBeforeInitType() == null || this.getBeforeInitType().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getBeforeInitType());
			}
			if(this.getAfterInit() == null || this.getAfterInit().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAfterInit());
			}
			if(this.getAfterInitType() == null || this.getAfterInitType().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getAfterInitType());
			}
			if(this.getBeforeEnd() == null || this.getBeforeEnd().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getBeforeEnd());
			}
			if(this.getBeforeEndType() == null || this.getBeforeEndType().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getBeforeEndType());
			}
			if(this.getAfterEnd() == null || this.getAfterEnd().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAfterEnd());
			}
			if(this.getAfterEndType() == null || this.getAfterEndType().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getAfterEndType());
			}
			pstmt.setInt(13, this.getWatingTime());
			pstmt.setInt(14, this.getWorkingTime());
			pstmt.setInt(15, this.getTimeOut());
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getModifyTime());
			}
			if(this.getBusiType() == null || this.getBusiType().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getBusiType());
			}
			if(this.getIsNeed() == null || this.getIsNeed().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getIsNeed());
			}
			if(this.getActivityFlag() == null || this.getActivityFlag().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getActivityFlag());
			}
			pstmt.setInt(24, this.getImpDegree());
			if(this.getFunctionID() == null || this.getFunctionID().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getFunctionID());
			}
			if(this.getCreateAction() == null || this.getCreateAction().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getCreateAction());
			}
			if(this.getCreateActionType() == null || this.getCreateActionType().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getCreateActionType());
			}
			if(this.getApplyAction() == null || this.getApplyAction().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getApplyAction());
			}
			if(this.getApplyActionType() == null || this.getApplyActionType().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getApplyActionType());
			}
			if(this.getDeleteAction() == null || this.getDeleteAction().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getDeleteAction());
			}
			if(this.getDeleteActionType() == null || this.getDeleteActionType().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getDeleteActionType());
			}
			if(this.getTogether() == null || this.getTogether().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getTogether());
			}
			if(this.getMenuNodeCode() == null || this.getMenuNodeCode().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getMenuNodeCode());
			}
			// set where condition
			if(this.getActivityID() == null || this.getActivityID().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getActivityID());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
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
		SQLString sqlObj = new SQLString("LWActivity");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LWActivity VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getActivityID() == null || this.getActivityID().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getActivityID());
			}
			if(this.getActivityName() == null || this.getActivityName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getActivityName());
			}
			if(this.getActivityType() == null || this.getActivityType().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, this.getActivityType());
			}
			if(this.getActivityDesc() == null || this.getActivityDesc().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getActivityDesc());
			}
			if(this.getBeforeInit() == null || this.getBeforeInit().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getBeforeInit());
			}
			if(this.getBeforeInitType() == null || this.getBeforeInitType().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getBeforeInitType());
			}
			if(this.getAfterInit() == null || this.getAfterInit().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAfterInit());
			}
			if(this.getAfterInitType() == null || this.getAfterInitType().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getAfterInitType());
			}
			if(this.getBeforeEnd() == null || this.getBeforeEnd().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getBeforeEnd());
			}
			if(this.getBeforeEndType() == null || this.getBeforeEndType().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getBeforeEndType());
			}
			if(this.getAfterEnd() == null || this.getAfterEnd().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAfterEnd());
			}
			if(this.getAfterEndType() == null || this.getAfterEndType().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getAfterEndType());
			}
			pstmt.setInt(13, this.getWatingTime());
			pstmt.setInt(14, this.getWorkingTime());
			pstmt.setInt(15, this.getTimeOut());
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getModifyTime());
			}
			if(this.getBusiType() == null || this.getBusiType().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getBusiType());
			}
			if(this.getIsNeed() == null || this.getIsNeed().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getIsNeed());
			}
			if(this.getActivityFlag() == null || this.getActivityFlag().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getActivityFlag());
			}
			pstmt.setInt(24, this.getImpDegree());
			if(this.getFunctionID() == null || this.getFunctionID().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getFunctionID());
			}
			if(this.getCreateAction() == null || this.getCreateAction().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getCreateAction());
			}
			if(this.getCreateActionType() == null || this.getCreateActionType().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getCreateActionType());
			}
			if(this.getApplyAction() == null || this.getApplyAction().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getApplyAction());
			}
			if(this.getApplyActionType() == null || this.getApplyActionType().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getApplyActionType());
			}
			if(this.getDeleteAction() == null || this.getDeleteAction().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getDeleteAction());
			}
			if(this.getDeleteActionType() == null || this.getDeleteActionType().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getDeleteActionType());
			}
			if(this.getTogether() == null || this.getTogether().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getTogether());
			}
			if(this.getMenuNodeCode() == null || this.getMenuNodeCode().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getMenuNodeCode());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LWActivity WHERE  ActivityID = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getActivityID() == null || this.getActivityID().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getActivityID());
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
					tError.moduleName = "LWActivityDB";
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
			tError.moduleName = "LWActivityDB";
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

	public LWActivitySet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LWActivitySet aLWActivitySet = new LWActivitySet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LWActivity");
			LWActivitySchema aSchema = this.getSchema();
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
				LWActivitySchema s1 = new LWActivitySchema();
				s1.setSchema(rs,i);
				aLWActivitySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
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

		return aLWActivitySet;
	}

	public LWActivitySet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LWActivitySet aLWActivitySet = new LWActivitySet();

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
				LWActivitySchema s1 = new LWActivitySchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LWActivityDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLWActivitySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
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

		return aLWActivitySet;
	}

	public LWActivitySet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LWActivitySet aLWActivitySet = new LWActivitySet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LWActivity");
			LWActivitySchema aSchema = this.getSchema();
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

				LWActivitySchema s1 = new LWActivitySchema();
				s1.setSchema(rs,i);
				aLWActivitySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
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

		return aLWActivitySet;
	}

	public LWActivitySet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LWActivitySet aLWActivitySet = new LWActivitySet();

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

				LWActivitySchema s1 = new LWActivitySchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LWActivityDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLWActivitySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
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

		return aLWActivitySet;
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
			SQLString sqlObj = new SQLString("LWActivity");
			LWActivitySchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LWActivity " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LWActivityDB";
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
			tError.moduleName = "LWActivityDB";
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
        tError.moduleName = "LWActivityDB";
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
        tError.moduleName = "LWActivityDB";
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
        tError.moduleName = "LWActivityDB";
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
        tError.moduleName = "LWActivityDB";
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
 * @return LWActivitySet
 */
public LWActivitySet getData()
{
    int tCount = 0;
    LWActivitySet tLWActivitySet = new LWActivitySet();
    LWActivitySchema tLWActivitySchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LWActivityDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLWActivitySchema = new LWActivitySchema();
        tLWActivitySchema.setSchema(mResultSet, 1);
        tLWActivitySet.add(tLWActivitySchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLWActivitySchema = new LWActivitySchema();
                tLWActivitySchema.setSchema(mResultSet, 1);
                tLWActivitySet.add(tLWActivitySchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LWActivityDB";
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
    return tLWActivitySet;
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
            tError.moduleName = "LWActivityDB";
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
        tError.moduleName = "LWActivityDB";
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
            tError.moduleName = "LWActivityDB";
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
        tError.moduleName = "LWActivityDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LWActivitySet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LWActivitySet aLWActivitySet = new LWActivitySet();

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
				LWActivitySchema s1 = new LWActivitySchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LWActivityDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLWActivitySet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
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

		return aLWActivitySet;
	}

	public LWActivitySet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LWActivitySet aLWActivitySet = new LWActivitySet();

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

				LWActivitySchema s1 = new LWActivitySchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LWActivityDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLWActivitySet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWActivityDB";
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

		return aLWActivitySet; 
	}

}
