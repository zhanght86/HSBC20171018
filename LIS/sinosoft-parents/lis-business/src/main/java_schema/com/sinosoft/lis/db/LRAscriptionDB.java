/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LRAscriptionSchema;
import com.sinosoft.lis.vschema.LRAscriptionSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LRAscriptionDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保费续期及中介
 */
public class LRAscriptionDB extends LRAscriptionSchema
{
private static Logger logger = Logger.getLogger(LRAscriptionDB.class);

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
	public LRAscriptionDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LRAscription" );
		mflag = true;
	}

	public LRAscriptionDB()
	{
		con = null;
		db = new DBOper( "LRAscription" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LRAscriptionSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LRAscriptionSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
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
			pstmt = con.prepareStatement("DELETE FROM LRAscription WHERE  PolNo = ?");
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LRAscription");
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
		SQLString sqlObj = new SQLString("LRAscription");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LRAscription SET  PolNo = ? , UnionAgent = ? , AgentOld = ? , AgentNew = ? , AClass = ? , ValidFlag = ? , AscriptionDate = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , MainPolNo = ? , Noti = ? , ManageCom = ? , AscriptType = ? , PrtNo = ? , PayToDate = ? , PayCount = ? , OldPolno = ? , RiskCode = ? , CValiDate = ? , PayYear = ? , ZipCode = ? , AddCode = ? , ContNo = ? WHERE  PolNo = ?");
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			if(this.getUnionAgent() == null || this.getUnionAgent().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getUnionAgent());
			}
			if(this.getAgentOld() == null || this.getAgentOld().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getAgentOld());
			}
			if(this.getAgentNew() == null || this.getAgentNew().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAgentNew());
			}
			if(this.getAClass() == null || this.getAClass().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getAClass());
			}
			if(this.getValidFlag() == null || this.getValidFlag().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getValidFlag());
			}
			if(this.getAscriptionDate() == null || this.getAscriptionDate().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getAscriptionDate()));
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getModifyTime());
			}
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getMainPolNo());
			}
			if(this.getNoti() == null || this.getNoti().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getNoti());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getManageCom());
			}
			if(this.getAscriptType() == null || this.getAscriptType().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAscriptType());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPrtNo());
			}
			if(this.getPayToDate() == null || this.getPayToDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getPayToDate()));
			}
			pstmt.setInt(19, this.getPayCount());
			if(this.getOldPolno() == null || this.getOldPolno().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getOldPolno());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getRiskCode());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(23, this.getPayYear());
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getZipCode());
			}
			if(this.getAddCode() == null || this.getAddCode().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAddCode());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getContNo());
			}
			// set where condition
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
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
		SQLString sqlObj = new SQLString("LRAscription");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LRAscription(PolNo ,UnionAgent ,AgentOld ,AgentNew ,AClass ,ValidFlag ,AscriptionDate ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,MainPolNo ,Noti ,ManageCom ,AscriptType ,PrtNo ,PayToDate ,PayCount ,OldPolno ,RiskCode ,CValiDate ,PayYear ,ZipCode ,AddCode ,ContNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			if(this.getUnionAgent() == null || this.getUnionAgent().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getUnionAgent());
			}
			if(this.getAgentOld() == null || this.getAgentOld().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getAgentOld());
			}
			if(this.getAgentNew() == null || this.getAgentNew().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAgentNew());
			}
			if(this.getAClass() == null || this.getAClass().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getAClass());
			}
			if(this.getValidFlag() == null || this.getValidFlag().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getValidFlag());
			}
			if(this.getAscriptionDate() == null || this.getAscriptionDate().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getAscriptionDate()));
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getModifyTime());
			}
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getMainPolNo());
			}
			if(this.getNoti() == null || this.getNoti().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getNoti());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getManageCom());
			}
			if(this.getAscriptType() == null || this.getAscriptType().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAscriptType());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPrtNo());
			}
			if(this.getPayToDate() == null || this.getPayToDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getPayToDate()));
			}
			pstmt.setInt(19, this.getPayCount());
			if(this.getOldPolno() == null || this.getOldPolno().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getOldPolno());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getRiskCode());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(23, this.getPayYear());
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getZipCode());
			}
			if(this.getAddCode() == null || this.getAddCode().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAddCode());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getContNo());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LRAscription WHERE  PolNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
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
					tError.moduleName = "LRAscriptionDB";
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
			tError.moduleName = "LRAscriptionDB";
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

	public LRAscriptionSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LRAscriptionSet aLRAscriptionSet = new LRAscriptionSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LRAscription");
			LRAscriptionSchema aSchema = this.getSchema();
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
				LRAscriptionSchema s1 = new LRAscriptionSchema();
				s1.setSchema(rs,i);
				aLRAscriptionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
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

		return aLRAscriptionSet;

	}

	public LRAscriptionSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LRAscriptionSet aLRAscriptionSet = new LRAscriptionSet();

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
				LRAscriptionSchema s1 = new LRAscriptionSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LRAscriptionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLRAscriptionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
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

		return aLRAscriptionSet;
	}

	public LRAscriptionSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LRAscriptionSet aLRAscriptionSet = new LRAscriptionSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LRAscription");
			LRAscriptionSchema aSchema = this.getSchema();
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

				LRAscriptionSchema s1 = new LRAscriptionSchema();
				s1.setSchema(rs,i);
				aLRAscriptionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
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

		return aLRAscriptionSet;

	}

	public LRAscriptionSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LRAscriptionSet aLRAscriptionSet = new LRAscriptionSet();

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

				LRAscriptionSchema s1 = new LRAscriptionSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LRAscriptionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLRAscriptionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
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

		return aLRAscriptionSet;
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
			SQLString sqlObj = new SQLString("LRAscription");
			LRAscriptionSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LRAscription " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LRAscriptionDB";
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
			tError.moduleName = "LRAscriptionDB";
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
        tError.moduleName = "LRAscriptionDB";
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
        tError.moduleName = "LRAscriptionDB";
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
        tError.moduleName = "LRAscriptionDB";
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
        tError.moduleName = "LRAscriptionDB";
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
 * @return LRAscriptionSet
 */
public LRAscriptionSet getData()
{
    int tCount = 0;
    LRAscriptionSet tLRAscriptionSet = new LRAscriptionSet();
    LRAscriptionSchema tLRAscriptionSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LRAscriptionDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLRAscriptionSchema = new LRAscriptionSchema();
        tLRAscriptionSchema.setSchema(mResultSet, 1);
        tLRAscriptionSet.add(tLRAscriptionSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLRAscriptionSchema = new LRAscriptionSchema();
                tLRAscriptionSchema.setSchema(mResultSet, 1);
                tLRAscriptionSet.add(tLRAscriptionSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LRAscriptionDB";
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
    return tLRAscriptionSet;
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
            tError.moduleName = "LRAscriptionDB";
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
        tError.moduleName = "LRAscriptionDB";
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
            tError.moduleName = "LRAscriptionDB";
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
        tError.moduleName = "LRAscriptionDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LRAscriptionSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LRAscriptionSet aLRAscriptionSet = new LRAscriptionSet();

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
				LRAscriptionSchema s1 = new LRAscriptionSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LRAscriptionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLRAscriptionSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
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

		return aLRAscriptionSet;
	}

	public LRAscriptionSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LRAscriptionSet aLRAscriptionSet = new LRAscriptionSet();

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

				LRAscriptionSchema s1 = new LRAscriptionSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LRAscriptionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLRAscriptionSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRAscriptionDB";
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

		return aLRAscriptionSet; 
	}

}
