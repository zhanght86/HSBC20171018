/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LOBCustomerContactInfoSchema;
import com.sinosoft.lis.vschema.LOBCustomerContactInfoSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LOBCustomerContactInfoDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LOBCustomerContactInfoDB extends LOBCustomerContactInfoSchema
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
	public LOBCustomerContactInfoDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LOBCustomerContactInfo" );
		mflag = true;
	}

	public LOBCustomerContactInfoDB()
	{
		con = null;
		db = new DBOper( "LOBCustomerContactInfo" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LOBCustomerContactInfoSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LOBCustomerContactInfoSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
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
			pstmt = con.prepareStatement("DELETE FROM LOBCustomerContactInfo WHERE  BussType = ? AND BussNo = ? AND ContNo = ? AND CustomerNo = ?");
			if(this.getBussType() == null || this.getBussType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBussType());
			}
			if(this.getBussNo() == null || this.getBussNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBussNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCustomerNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOBCustomerContactInfo");
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
		SQLString sqlObj = new SQLString("LOBCustomerContactInfo");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LOBCustomerContactInfo SET  BussType = ? , BussNo = ? , PolicyNo = ? , PropNo = ? , ContNo = ? , CustomerNo = ? , Province = ? , City = ? , County = ? , PostalAddress = ? , ZipCode = ? , Phone = ? , Fax = ? , HomeAddress = ? , HomeZipCode = ? , HomePhone = ? , HomeFax = ? , CompanyName = ? , CompanyAddress = ? , CompanyZipCode = ? , CompanyPhone = ? , CompanyFax = ? , Mobile1 = ? , EMail1 = ? , QQ1 = ? , MSN1 = ? , Wechat1 = ? , Mobile2 = ? , EMail2 = ? , QQ2 = ? , MSN2 = ? , Wechat2 = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  BussType = ? AND BussNo = ? AND ContNo = ? AND CustomerNo = ?");
			if(this.getBussType() == null || this.getBussType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBussType());
			}
			if(this.getBussNo() == null || this.getBussNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBussNo());
			}
			if(this.getPolicyNo() == null || this.getPolicyNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolicyNo());
			}
			if(this.getPropNo() == null || this.getPropNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPropNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getContNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCustomerNo());
			}
			if(this.getProvince() == null || this.getProvince().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getProvince());
			}
			if(this.getCity() == null || this.getCity().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getCity());
			}
			if(this.getCounty() == null || this.getCounty().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getCounty());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getPhone());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getFax());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getHomeAddress());
			}
			if(this.getHomeZipCode() == null || this.getHomeZipCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getHomeZipCode());
			}
			if(this.getHomePhone() == null || this.getHomePhone().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getHomePhone());
			}
			if(this.getHomeFax() == null || this.getHomeFax().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getHomeFax());
			}
			if(this.getCompanyName() == null || this.getCompanyName().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getCompanyName());
			}
			if(this.getCompanyAddress() == null || this.getCompanyAddress().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getCompanyAddress());
			}
			if(this.getCompanyZipCode() == null || this.getCompanyZipCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getCompanyZipCode());
			}
			if(this.getCompanyPhone() == null || this.getCompanyPhone().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getCompanyPhone());
			}
			if(this.getCompanyFax() == null || this.getCompanyFax().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getCompanyFax());
			}
			if(this.getMobile1() == null || this.getMobile1().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getMobile1());
			}
			if(this.getEMail1() == null || this.getEMail1().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getEMail1());
			}
			if(this.getQQ1() == null || this.getQQ1().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getQQ1());
			}
			if(this.getMSN1() == null || this.getMSN1().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getMSN1());
			}
			if(this.getWechat1() == null || this.getWechat1().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getWechat1());
			}
			if(this.getMobile2() == null || this.getMobile2().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getMobile2());
			}
			if(this.getEMail2() == null || this.getEMail2().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getEMail2());
			}
			if(this.getQQ2() == null || this.getQQ2().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getQQ2());
			}
			if(this.getMSN2() == null || this.getMSN2().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getMSN2());
			}
			if(this.getWechat2() == null || this.getWechat2().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getWechat2());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getModifyTime());
			}
			// set where condition
			if(this.getBussType() == null || this.getBussType().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getBussType());
			}
			if(this.getBussNo() == null || this.getBussNo().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getBussNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getContNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getCustomerNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
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
		SQLString sqlObj = new SQLString("LOBCustomerContactInfo");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LOBCustomerContactInfo VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getBussType() == null || this.getBussType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBussType());
			}
			if(this.getBussNo() == null || this.getBussNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBussNo());
			}
			if(this.getPolicyNo() == null || this.getPolicyNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolicyNo());
			}
			if(this.getPropNo() == null || this.getPropNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPropNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getContNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCustomerNo());
			}
			if(this.getProvince() == null || this.getProvince().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getProvince());
			}
			if(this.getCity() == null || this.getCity().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getCity());
			}
			if(this.getCounty() == null || this.getCounty().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getCounty());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getPhone());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getFax());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getHomeAddress());
			}
			if(this.getHomeZipCode() == null || this.getHomeZipCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getHomeZipCode());
			}
			if(this.getHomePhone() == null || this.getHomePhone().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getHomePhone());
			}
			if(this.getHomeFax() == null || this.getHomeFax().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getHomeFax());
			}
			if(this.getCompanyName() == null || this.getCompanyName().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getCompanyName());
			}
			if(this.getCompanyAddress() == null || this.getCompanyAddress().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getCompanyAddress());
			}
			if(this.getCompanyZipCode() == null || this.getCompanyZipCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getCompanyZipCode());
			}
			if(this.getCompanyPhone() == null || this.getCompanyPhone().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getCompanyPhone());
			}
			if(this.getCompanyFax() == null || this.getCompanyFax().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getCompanyFax());
			}
			if(this.getMobile1() == null || this.getMobile1().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getMobile1());
			}
			if(this.getEMail1() == null || this.getEMail1().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getEMail1());
			}
			if(this.getQQ1() == null || this.getQQ1().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getQQ1());
			}
			if(this.getMSN1() == null || this.getMSN1().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getMSN1());
			}
			if(this.getWechat1() == null || this.getWechat1().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getWechat1());
			}
			if(this.getMobile2() == null || this.getMobile2().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getMobile2());
			}
			if(this.getEMail2() == null || this.getEMail2().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getEMail2());
			}
			if(this.getQQ2() == null || this.getQQ2().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getQQ2());
			}
			if(this.getMSN2() == null || this.getMSN2().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getMSN2());
			}
			if(this.getWechat2() == null || this.getWechat2().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getWechat2());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LOBCustomerContactInfo WHERE  BussType = ? AND BussNo = ? AND ContNo = ? AND CustomerNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getBussType() == null || this.getBussType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBussType());
			}
			if(this.getBussNo() == null || this.getBussNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBussNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCustomerNo());
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
					tError.moduleName = "LOBCustomerContactInfoDB";
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
			tError.moduleName = "LOBCustomerContactInfoDB";
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

	public LOBCustomerContactInfoSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LOBCustomerContactInfoSet aLOBCustomerContactInfoSet = new LOBCustomerContactInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LOBCustomerContactInfo");
			LOBCustomerContactInfoSchema aSchema = this.getSchema();
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
				LOBCustomerContactInfoSchema s1 = new LOBCustomerContactInfoSchema();
				s1.setSchema(rs,i);
				aLOBCustomerContactInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
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

		return aLOBCustomerContactInfoSet;
	}

	public LOBCustomerContactInfoSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LOBCustomerContactInfoSet aLOBCustomerContactInfoSet = new LOBCustomerContactInfoSet();

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
				LOBCustomerContactInfoSchema s1 = new LOBCustomerContactInfoSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LOBCustomerContactInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLOBCustomerContactInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
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

		return aLOBCustomerContactInfoSet;
	}

	public LOBCustomerContactInfoSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LOBCustomerContactInfoSet aLOBCustomerContactInfoSet = new LOBCustomerContactInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LOBCustomerContactInfo");
			LOBCustomerContactInfoSchema aSchema = this.getSchema();
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

				LOBCustomerContactInfoSchema s1 = new LOBCustomerContactInfoSchema();
				s1.setSchema(rs,i);
				aLOBCustomerContactInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
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

		return aLOBCustomerContactInfoSet;
	}

	public LOBCustomerContactInfoSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LOBCustomerContactInfoSet aLOBCustomerContactInfoSet = new LOBCustomerContactInfoSet();

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

				LOBCustomerContactInfoSchema s1 = new LOBCustomerContactInfoSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LOBCustomerContactInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLOBCustomerContactInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
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

		return aLOBCustomerContactInfoSet;
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
			SQLString sqlObj = new SQLString("LOBCustomerContactInfo");
			LOBCustomerContactInfoSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LOBCustomerContactInfo " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LOBCustomerContactInfoDB";
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
			tError.moduleName = "LOBCustomerContactInfoDB";
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
        tError.moduleName = "LOBCustomerContactInfoDB";
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
        tError.moduleName = "LOBCustomerContactInfoDB";
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
        tError.moduleName = "LOBCustomerContactInfoDB";
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
        tError.moduleName = "LOBCustomerContactInfoDB";
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
 * @return LOBCustomerContactInfoSet
 */
public LOBCustomerContactInfoSet getData()
{
    int tCount = 0;
    LOBCustomerContactInfoSet tLOBCustomerContactInfoSet = new LOBCustomerContactInfoSet();
    LOBCustomerContactInfoSchema tLOBCustomerContactInfoSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LOBCustomerContactInfoDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLOBCustomerContactInfoSchema = new LOBCustomerContactInfoSchema();
        tLOBCustomerContactInfoSchema.setSchema(mResultSet, 1);
        tLOBCustomerContactInfoSet.add(tLOBCustomerContactInfoSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLOBCustomerContactInfoSchema = new LOBCustomerContactInfoSchema();
                tLOBCustomerContactInfoSchema.setSchema(mResultSet, 1);
                tLOBCustomerContactInfoSet.add(tLOBCustomerContactInfoSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LOBCustomerContactInfoDB";
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
    return tLOBCustomerContactInfoSet;
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
            tError.moduleName = "LOBCustomerContactInfoDB";
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
        tError.moduleName = "LOBCustomerContactInfoDB";
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
            tError.moduleName = "LOBCustomerContactInfoDB";
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
        tError.moduleName = "LOBCustomerContactInfoDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LOBCustomerContactInfoSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LOBCustomerContactInfoSet aLOBCustomerContactInfoSet = new LOBCustomerContactInfoSet();

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
				LOBCustomerContactInfoSchema s1 = new LOBCustomerContactInfoSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LOBCustomerContactInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLOBCustomerContactInfoSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
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

		return aLOBCustomerContactInfoSet;
	}

	public LOBCustomerContactInfoSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LOBCustomerContactInfoSet aLOBCustomerContactInfoSet = new LOBCustomerContactInfoSet();

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

				LOBCustomerContactInfoSchema s1 = new LOBCustomerContactInfoSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LOBCustomerContactInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLOBCustomerContactInfoSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LOBCustomerContactInfoDB";
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

		return aLOBCustomerContactInfoSet; 
	}

}
