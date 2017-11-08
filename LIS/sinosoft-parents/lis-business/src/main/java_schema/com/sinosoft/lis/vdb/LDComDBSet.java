/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.vschema.LDComSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDComDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDComDBSet extends LDComSet
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LDComDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LDCom");
		mflag = true;
	}

	public LDComDBSet()
	{
		db = new DBOper( "LDCom" );
		con = db.getConnection();
	}
	// @Method
	public boolean deleteSQL()
	{
		if (db.deleteSQL(this))
		{
		        return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDComDBSet";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

    /**
     * 删除操作
     * 删除条件：主键
     * @return boolean
     */
	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("DELETE FROM LDCom WHERE  ComCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getComCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDCom");
		sqlObj.setSQL(4, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDComDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

    /**
     * 更新操作
     * 更新条件：主键
     * @return boolean
     */
	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("UPDATE LDCom SET  ComCode = ? , OutComCode = ? , Name = ? , ShortName = ? , Address = ? , ZipCode = ? , Phone = ? , Fax = ? , EMail = ? , WebAddress = ? , SatrapName = ? , InsuMonitorCode = ? , InsureID = ? , SignID = ? , RegionalismCode = ? , ComNature = ? , ValidCode = ? , Sign = ? , ComCitySize = ? , ServiceName = ? , ServiceNo = ? , ServicePhone = ? , ServicePostAddress = ? , ComGrade = ? , ComAreaType = ? , UpComCode = ? , IsDirUnder = ? , ComAreaType1 = ? , Province = ? , City = ? , County = ? , FinDB = ? WHERE  ComCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getComCode());
			}
			if(this.get(i).getOutComCode() == null || this.get(i).getOutComCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getOutComCode());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getName());
			}
			if(this.get(i).getShortName() == null || this.get(i).getShortName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getShortName());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getZipCode());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPhone());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getFax());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getEMail());
			}
			if(this.get(i).getWebAddress() == null || this.get(i).getWebAddress().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getWebAddress());
			}
			if(this.get(i).getSatrapName() == null || this.get(i).getSatrapName().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSatrapName());
			}
			if(this.get(i).getInsuMonitorCode() == null || this.get(i).getInsuMonitorCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getInsuMonitorCode());
			}
			if(this.get(i).getInsureID() == null || this.get(i).getInsureID().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInsureID());
			}
			if(this.get(i).getSignID() == null || this.get(i).getSignID().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getSignID());
			}
			if(this.get(i).getRegionalismCode() == null || this.get(i).getRegionalismCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRegionalismCode());
			}
			if(this.get(i).getComNature() == null || this.get(i).getComNature().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getComNature());
			}
			if(this.get(i).getValidCode() == null || this.get(i).getValidCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getValidCode());
			}
			if(this.get(i).getSign() == null || this.get(i).getSign().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getSign());
			}
			if(this.get(i).getComCitySize() == null || this.get(i).getComCitySize().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getComCitySize());
			}
			if(this.get(i).getServiceName() == null || this.get(i).getServiceName().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getServiceName());
			}
			if(this.get(i).getServiceNo() == null || this.get(i).getServiceNo().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getServiceNo());
			}
			if(this.get(i).getServicePhone() == null || this.get(i).getServicePhone().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getServicePhone());
			}
			if(this.get(i).getServicePostAddress() == null || this.get(i).getServicePostAddress().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getServicePostAddress());
			}
			if(this.get(i).getComGrade() == null || this.get(i).getComGrade().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getComGrade());
			}
			if(this.get(i).getComAreaType() == null || this.get(i).getComAreaType().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getComAreaType());
			}
			if(this.get(i).getUpComCode() == null || this.get(i).getUpComCode().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getUpComCode());
			}
			if(this.get(i).getIsDirUnder() == null || this.get(i).getIsDirUnder().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getIsDirUnder());
			}
			if(this.get(i).getComAreaType1() == null || this.get(i).getComAreaType1().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getComAreaType1());
			}
			if(this.get(i).getProvince() == null || this.get(i).getProvince().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getProvince());
			}
			if(this.get(i).getCity() == null || this.get(i).getCity().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getCity());
			}
			if(this.get(i).getCounty() == null || this.get(i).getCounty().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getCounty());
			}
			if(this.get(i).getFinDB() == null || this.get(i).getFinDB().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getFinDB());
			}
			// set where condition
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getComCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDCom");
		sqlObj.setSQL(2, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDComDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

    /**
     * 新增操作
     * @return boolean
     */
	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("INSERT INTO LDCom VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getComCode());
			}
			if(this.get(i).getOutComCode() == null || this.get(i).getOutComCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getOutComCode());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getName());
			}
			if(this.get(i).getShortName() == null || this.get(i).getShortName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getShortName());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getZipCode());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPhone());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getFax());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getEMail());
			}
			if(this.get(i).getWebAddress() == null || this.get(i).getWebAddress().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getWebAddress());
			}
			if(this.get(i).getSatrapName() == null || this.get(i).getSatrapName().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSatrapName());
			}
			if(this.get(i).getInsuMonitorCode() == null || this.get(i).getInsuMonitorCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getInsuMonitorCode());
			}
			if(this.get(i).getInsureID() == null || this.get(i).getInsureID().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInsureID());
			}
			if(this.get(i).getSignID() == null || this.get(i).getSignID().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getSignID());
			}
			if(this.get(i).getRegionalismCode() == null || this.get(i).getRegionalismCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRegionalismCode());
			}
			if(this.get(i).getComNature() == null || this.get(i).getComNature().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getComNature());
			}
			if(this.get(i).getValidCode() == null || this.get(i).getValidCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getValidCode());
			}
			if(this.get(i).getSign() == null || this.get(i).getSign().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getSign());
			}
			if(this.get(i).getComCitySize() == null || this.get(i).getComCitySize().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getComCitySize());
			}
			if(this.get(i).getServiceName() == null || this.get(i).getServiceName().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getServiceName());
			}
			if(this.get(i).getServiceNo() == null || this.get(i).getServiceNo().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getServiceNo());
			}
			if(this.get(i).getServicePhone() == null || this.get(i).getServicePhone().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getServicePhone());
			}
			if(this.get(i).getServicePostAddress() == null || this.get(i).getServicePostAddress().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getServicePostAddress());
			}
			if(this.get(i).getComGrade() == null || this.get(i).getComGrade().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getComGrade());
			}
			if(this.get(i).getComAreaType() == null || this.get(i).getComAreaType().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getComAreaType());
			}
			if(this.get(i).getUpComCode() == null || this.get(i).getUpComCode().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getUpComCode());
			}
			if(this.get(i).getIsDirUnder() == null || this.get(i).getIsDirUnder().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getIsDirUnder());
			}
			if(this.get(i).getComAreaType1() == null || this.get(i).getComAreaType1().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getComAreaType1());
			}
			if(this.get(i).getProvince() == null || this.get(i).getProvince().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getProvince());
			}
			if(this.get(i).getCity() == null || this.get(i).getCity().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getCity());
			}
			if(this.get(i).getCounty() == null || this.get(i).getCounty().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getCounty());
			}
			if(this.get(i).getFinDB() == null || this.get(i).getFinDB().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getFinDB());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDCom");
		sqlObj.setSQL(1, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDComDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

}
