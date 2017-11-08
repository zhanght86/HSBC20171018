/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPAddressDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPAddressDB extends LPAddressSchema
{
private static Logger logger = Logger.getLogger(LPAddressDB.class);

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
	public LPAddressDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LPAddress" );
		mflag = true;
	}

	public LPAddressDB()
	{
		con = null;
		db = new DBOper( "LPAddress" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPAddressSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPAddressSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
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
			pstmt = con.prepareStatement("DELETE FROM LPAddress WHERE  EdorNo = ? AND EdorType = ? AND CustomerNo = ? AND AddressNo = ?");
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
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCustomerNo());
			}
			pstmt.setInt(4, this.getAddressNo());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPAddress");
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
		SQLString sqlObj = new SQLString("LPAddress");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LPAddress SET  EdorNo = ? , EdorType = ? , CustomerNo = ? , AddressNo = ? , PostalAddress = ? , ZipCode = ? , Phone = ? , Fax = ? , HomeAddress = ? , HomeZipCode = ? , HomePhone = ? , HomeFax = ? , CompanyAddress = ? , CompanyZipCode = ? , CompanyPhone = ? , CompanyFax = ? , Mobile = ? , MobileChs = ? , EMail = ? , BP = ? , Mobile2 = ? , MobileChs2 = ? , EMail2 = ? , BP2 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , GrpName = ? , Province = ? , City = ? , County = ? WHERE  EdorNo = ? AND EdorType = ? AND CustomerNo = ? AND AddressNo = ?");
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
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCustomerNo());
			}
			pstmt.setInt(4, this.getAddressNo());
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getPhone());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getFax());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getHomeAddress());
			}
			if(this.getHomeZipCode() == null || this.getHomeZipCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getHomeZipCode());
			}
			if(this.getHomePhone() == null || this.getHomePhone().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getHomePhone());
			}
			if(this.getHomeFax() == null || this.getHomeFax().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getHomeFax());
			}
			if(this.getCompanyAddress() == null || this.getCompanyAddress().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getCompanyAddress());
			}
			if(this.getCompanyZipCode() == null || this.getCompanyZipCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getCompanyZipCode());
			}
			if(this.getCompanyPhone() == null || this.getCompanyPhone().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getCompanyPhone());
			}
			if(this.getCompanyFax() == null || this.getCompanyFax().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getCompanyFax());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getMobile());
			}
			if(this.getMobileChs() == null || this.getMobileChs().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getMobileChs());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getEMail());
			}
			if(this.getBP() == null || this.getBP().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getBP());
			}
			if(this.getMobile2() == null || this.getMobile2().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getMobile2());
			}
			if(this.getMobileChs2() == null || this.getMobileChs2().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getMobileChs2());
			}
			if(this.getEMail2() == null || this.getEMail2().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getEMail2());
			}
			if(this.getBP2() == null || this.getBP2().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getBP2());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getModifyTime());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getGrpName());
			}
			if(this.getProvince() == null || this.getProvince().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getProvince());
			}
			if(this.getCity() == null || this.getCity().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getCity());
			}
			if(this.getCounty() == null || this.getCounty().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getCounty());
			}
			// set where condition
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getEdorType());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getCustomerNo());
			}
			pstmt.setInt(37, this.getAddressNo());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
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
		SQLString sqlObj = new SQLString("LPAddress");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPAddress(EdorNo ,EdorType ,CustomerNo ,AddressNo ,PostalAddress ,ZipCode ,Phone ,Fax ,HomeAddress ,HomeZipCode ,HomePhone ,HomeFax ,CompanyAddress ,CompanyZipCode ,CompanyPhone ,CompanyFax ,Mobile ,MobileChs ,EMail ,BP ,Mobile2 ,MobileChs2 ,EMail2 ,BP2 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,GrpName ,Province ,City ,County) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCustomerNo());
			}
			pstmt.setInt(4, this.getAddressNo());
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getPhone());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getFax());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getHomeAddress());
			}
			if(this.getHomeZipCode() == null || this.getHomeZipCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getHomeZipCode());
			}
			if(this.getHomePhone() == null || this.getHomePhone().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getHomePhone());
			}
			if(this.getHomeFax() == null || this.getHomeFax().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getHomeFax());
			}
			if(this.getCompanyAddress() == null || this.getCompanyAddress().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getCompanyAddress());
			}
			if(this.getCompanyZipCode() == null || this.getCompanyZipCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getCompanyZipCode());
			}
			if(this.getCompanyPhone() == null || this.getCompanyPhone().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getCompanyPhone());
			}
			if(this.getCompanyFax() == null || this.getCompanyFax().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getCompanyFax());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getMobile());
			}
			if(this.getMobileChs() == null || this.getMobileChs().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getMobileChs());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getEMail());
			}
			if(this.getBP() == null || this.getBP().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getBP());
			}
			if(this.getMobile2() == null || this.getMobile2().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getMobile2());
			}
			if(this.getMobileChs2() == null || this.getMobileChs2().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getMobileChs2());
			}
			if(this.getEMail2() == null || this.getEMail2().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getEMail2());
			}
			if(this.getBP2() == null || this.getBP2().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getBP2());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getModifyTime());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getGrpName());
			}
			if(this.getProvince() == null || this.getProvince().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getProvince());
			}
			if(this.getCity() == null || this.getCity().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getCity());
			}
			if(this.getCounty() == null || this.getCounty().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getCounty());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LPAddress WHERE  EdorNo = ? AND EdorType = ? AND CustomerNo = ? AND AddressNo = ?", 
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
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCustomerNo());
			}
			pstmt.setInt(4, this.getAddressNo());
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPAddressDB";
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
			tError.moduleName = "LPAddressDB";
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

	public LPAddressSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPAddressSet aLPAddressSet = new LPAddressSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPAddress");
			LPAddressSchema aSchema = this.getSchema();
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
				LPAddressSchema s1 = new LPAddressSchema();
				s1.setSchema(rs,i);
				aLPAddressSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
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

		return aLPAddressSet;

	}

	public LPAddressSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPAddressSet aLPAddressSet = new LPAddressSet();

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
				LPAddressSchema s1 = new LPAddressSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPAddressDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPAddressSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
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

		return aLPAddressSet;
	}

	public LPAddressSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPAddressSet aLPAddressSet = new LPAddressSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPAddress");
			LPAddressSchema aSchema = this.getSchema();
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

				LPAddressSchema s1 = new LPAddressSchema();
				s1.setSchema(rs,i);
				aLPAddressSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
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

		return aLPAddressSet;

	}

	public LPAddressSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPAddressSet aLPAddressSet = new LPAddressSet();

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

				LPAddressSchema s1 = new LPAddressSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPAddressDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPAddressSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
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

		return aLPAddressSet;
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
			SQLString sqlObj = new SQLString("LPAddress");
			LPAddressSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LPAddress " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPAddressDB";
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
			tError.moduleName = "LPAddressDB";
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
        tError.moduleName = "LPAddressDB";
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
        tError.moduleName = "LPAddressDB";
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
        tError.moduleName = "LPAddressDB";
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
        tError.moduleName = "LPAddressDB";
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
 * @return LPAddressSet
 */
public LPAddressSet getData()
{
    int tCount = 0;
    LPAddressSet tLPAddressSet = new LPAddressSet();
    LPAddressSchema tLPAddressSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPAddressDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLPAddressSchema = new LPAddressSchema();
        tLPAddressSchema.setSchema(mResultSet, 1);
        tLPAddressSet.add(tLPAddressSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLPAddressSchema = new LPAddressSchema();
                tLPAddressSchema.setSchema(mResultSet, 1);
                tLPAddressSet.add(tLPAddressSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPAddressDB";
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
    return tLPAddressSet;
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
            tError.moduleName = "LPAddressDB";
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
        tError.moduleName = "LPAddressDB";
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
            tError.moduleName = "LPAddressDB";
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
        tError.moduleName = "LPAddressDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LPAddressSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPAddressSet aLPAddressSet = new LPAddressSet();

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
				LPAddressSchema s1 = new LPAddressSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPAddressDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPAddressSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
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

		return aLPAddressSet;
	}

	public LPAddressSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPAddressSet aLPAddressSet = new LPAddressSet();

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

				LPAddressSchema s1 = new LPAddressSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPAddressDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPAddressSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAddressDB";
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

		return aLPAddressSet; 
	}

}
