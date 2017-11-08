/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LARateCommisionSchema;
import com.sinosoft.lis.vschema.LARateCommisionSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LARateCommisionDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LARateCommisionDB extends LARateCommisionSchema
{
private static Logger logger = Logger.getLogger(LARateCommisionDB.class);

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
	public LARateCommisionDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LARateCommision" );
		mflag = true;
	}

	public LARateCommisionDB()
	{
		con = null;
		db = new DBOper( "LARateCommision" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LARateCommisionSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LARateCommisionSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
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
			pstmt = con.prepareStatement("DELETE FROM LARateCommision WHERE  BranchType = ? AND RiskCode = ? AND sex = ? AND AppAge = ? AND Year = ? AND PayIntv = ? AND CurYear = ? AND F01 = ? AND F02 = ? AND F03 = ? AND F04 = ? AND F05 = ? AND F06 = ? AND ManageCom = ? AND VersionType = ?");
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBranchType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskCode());
			}
			if(this.getsex() == null || this.getsex().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, StrTool.space1(this.getsex(), 1));
			}
			pstmt.setInt(4, this.getAppAge());
			pstmt.setInt(5, this.getYear());
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPayIntv());
			}
			pstmt.setInt(7, this.getCurYear());
			if(this.getF01() == null || this.getF01().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getF01());
			}
			if(this.getF02() == null || this.getF02().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getF02());
			}
			pstmt.setDouble(10, this.getF03());
			pstmt.setDouble(11, this.getF04());
			if(this.getF05() == null || this.getF05().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getF05());
			}
			if(this.getF06() == null || this.getF06().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getF06());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getManageCom());
			}
			if(this.getVersionType() == null || this.getVersionType().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getVersionType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LARateCommision");
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
		SQLString sqlObj = new SQLString("LARateCommision");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LARateCommision SET  BranchType = ? , RiskCode = ? , sex = ? , AppAge = ? , Year = ? , PayIntv = ? , CurYear = ? , F01 = ? , F02 = ? , F03 = ? , F04 = ? , F05 = ? , F06 = ? , Rate = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , ManageCom = ? , VersionType = ? WHERE  BranchType = ? AND RiskCode = ? AND sex = ? AND AppAge = ? AND Year = ? AND PayIntv = ? AND CurYear = ? AND F01 = ? AND F02 = ? AND F03 = ? AND F04 = ? AND F05 = ? AND F06 = ? AND ManageCom = ? AND VersionType = ?");
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBranchType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskCode());
			}
			if(this.getsex() == null || this.getsex().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, this.getsex());
			}
			pstmt.setInt(4, this.getAppAge());
			pstmt.setInt(5, this.getYear());
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPayIntv());
			}
			pstmt.setInt(7, this.getCurYear());
			if(this.getF01() == null || this.getF01().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getF01());
			}
			if(this.getF02() == null || this.getF02().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getF02());
			}
			pstmt.setDouble(10, this.getF03());
			pstmt.setDouble(11, this.getF04());
			if(this.getF05() == null || this.getF05().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getF05());
			}
			if(this.getF06() == null || this.getF06().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getF06());
			}
			pstmt.setDouble(14, this.getRate());
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getModifyTime());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getManageCom());
			}
			if(this.getVersionType() == null || this.getVersionType().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getVersionType());
			}
			// set where condition
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getBranchType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getRiskCode());
			}
			if(this.getsex() == null || this.getsex().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, StrTool.space1(this.getsex(), 1));
			}
			pstmt.setInt(25, this.getAppAge());
			pstmt.setInt(26, this.getYear());
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getPayIntv());
			}
			pstmt.setInt(28, this.getCurYear());
			if(this.getF01() == null || this.getF01().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getF01());
			}
			if(this.getF02() == null || this.getF02().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getF02());
			}
			pstmt.setDouble(31, this.getF03());
			pstmt.setDouble(32, this.getF04());
			if(this.getF05() == null || this.getF05().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getF05());
			}
			if(this.getF06() == null || this.getF06().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getF06());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getManageCom());
			}
			if(this.getVersionType() == null || this.getVersionType().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getVersionType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
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
		SQLString sqlObj = new SQLString("LARateCommision");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LARateCommision(BranchType ,RiskCode ,sex ,AppAge ,Year ,PayIntv ,CurYear ,F01 ,F02 ,F03 ,F04 ,F05 ,F06 ,Rate ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,ManageCom ,VersionType) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBranchType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskCode());
			}
			if(this.getsex() == null || this.getsex().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, this.getsex());
			}
			pstmt.setInt(4, this.getAppAge());
			pstmt.setInt(5, this.getYear());
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPayIntv());
			}
			pstmt.setInt(7, this.getCurYear());
			if(this.getF01() == null || this.getF01().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getF01());
			}
			if(this.getF02() == null || this.getF02().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getF02());
			}
			pstmt.setDouble(10, this.getF03());
			pstmt.setDouble(11, this.getF04());
			if(this.getF05() == null || this.getF05().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getF05());
			}
			if(this.getF06() == null || this.getF06().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getF06());
			}
			pstmt.setDouble(14, this.getRate());
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getModifyTime());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getManageCom());
			}
			if(this.getVersionType() == null || this.getVersionType().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getVersionType());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LARateCommision WHERE  BranchType = ? AND RiskCode = ? AND sex = ? AND AppAge = ? AND Year = ? AND PayIntv = ? AND CurYear = ? AND F01 = ? AND F02 = ? AND F03 = ? AND F04 = ? AND F05 = ? AND F06 = ? AND ManageCom = ? AND VersionType = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBranchType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskCode());
			}
			if(this.getsex() == null || this.getsex().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, StrTool.space1(this.getsex(), 1));
			}
			pstmt.setInt(4, this.getAppAge());
			pstmt.setInt(5, this.getYear());
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPayIntv());
			}
			pstmt.setInt(7, this.getCurYear());
			if(this.getF01() == null || this.getF01().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getF01());
			}
			if(this.getF02() == null || this.getF02().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getF02());
			}
			pstmt.setDouble(10, this.getF03());
			pstmt.setDouble(11, this.getF04());
			if(this.getF05() == null || this.getF05().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getF05());
			}
			if(this.getF06() == null || this.getF06().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getF06());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getManageCom());
			}
			if(this.getVersionType() == null || this.getVersionType().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getVersionType());
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
					tError.moduleName = "LARateCommisionDB";
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
			tError.moduleName = "LARateCommisionDB";
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

	public LARateCommisionSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LARateCommisionSet aLARateCommisionSet = new LARateCommisionSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LARateCommision");
			LARateCommisionSchema aSchema = this.getSchema();
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
				LARateCommisionSchema s1 = new LARateCommisionSchema();
				s1.setSchema(rs,i);
				aLARateCommisionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
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

		return aLARateCommisionSet;

	}

	public LARateCommisionSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LARateCommisionSet aLARateCommisionSet = new LARateCommisionSet();

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
				LARateCommisionSchema s1 = new LARateCommisionSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LARateCommisionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLARateCommisionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
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

		return aLARateCommisionSet;
	}

	public LARateCommisionSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LARateCommisionSet aLARateCommisionSet = new LARateCommisionSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LARateCommision");
			LARateCommisionSchema aSchema = this.getSchema();
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

				LARateCommisionSchema s1 = new LARateCommisionSchema();
				s1.setSchema(rs,i);
				aLARateCommisionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
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

		return aLARateCommisionSet;

	}

	public LARateCommisionSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LARateCommisionSet aLARateCommisionSet = new LARateCommisionSet();

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

				LARateCommisionSchema s1 = new LARateCommisionSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LARateCommisionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLARateCommisionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
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

		return aLARateCommisionSet;
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
			SQLString sqlObj = new SQLString("LARateCommision");
			LARateCommisionSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LARateCommision " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LARateCommisionDB";
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
			tError.moduleName = "LARateCommisionDB";
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
        tError.moduleName = "LARateCommisionDB";
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
        tError.moduleName = "LARateCommisionDB";
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
        tError.moduleName = "LARateCommisionDB";
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
        tError.moduleName = "LARateCommisionDB";
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
 * @return LARateCommisionSet
 */
public LARateCommisionSet getData()
{
    int tCount = 0;
    LARateCommisionSet tLARateCommisionSet = new LARateCommisionSet();
    LARateCommisionSchema tLARateCommisionSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LARateCommisionDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLARateCommisionSchema = new LARateCommisionSchema();
        tLARateCommisionSchema.setSchema(mResultSet, 1);
        tLARateCommisionSet.add(tLARateCommisionSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLARateCommisionSchema = new LARateCommisionSchema();
                tLARateCommisionSchema.setSchema(mResultSet, 1);
                tLARateCommisionSet.add(tLARateCommisionSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LARateCommisionDB";
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
    return tLARateCommisionSet;
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
            tError.moduleName = "LARateCommisionDB";
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
        tError.moduleName = "LARateCommisionDB";
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
            tError.moduleName = "LARateCommisionDB";
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
        tError.moduleName = "LARateCommisionDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LARateCommisionSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LARateCommisionSet aLARateCommisionSet = new LARateCommisionSet();

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
				LARateCommisionSchema s1 = new LARateCommisionSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LARateCommisionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLARateCommisionSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
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

		return aLARateCommisionSet;
	}

	public LARateCommisionSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LARateCommisionSet aLARateCommisionSet = new LARateCommisionSet();

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

				LARateCommisionSchema s1 = new LARateCommisionSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LARateCommisionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLARateCommisionSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARateCommisionDB";
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

		return aLARateCommisionSet; 
	}

}
