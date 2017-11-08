/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.FIAboriginalDataSchema;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIAboriginalDataDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIAboriginalDataDB extends FIAboriginalDataSchema
{
private static Logger logger = Logger.getLogger(FIAboriginalDataDB.class);

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
	public FIAboriginalDataDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "FIAboriginalData" );
		mflag = true;
	}

	public FIAboriginalDataDB()
	{
		con = null;
		db = new DBOper( "FIAboriginalData" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		FIAboriginalDataSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		FIAboriginalDataSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
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
			pstmt = con.prepareStatement("DELETE FROM FIAboriginalData WHERE  ASerialNo = ?");
			if(this.getASerialNo() == null || this.getASerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getASerialNo().trim());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("FIAboriginalData");
		sqlObj.setSQL(4, this);
		sqlObj.getSQL();
//真实的执行语句
logger.debug(String.valueOf("True Error Sql is : DELETE FROM FIAboriginalData WHERE ASerialNo='"+this.getASerialNo() + "'").replaceAll("'null'", "null"));

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


		try
		{
			pstmt = con.prepareStatement("UPDATE FIAboriginalData SET  ASerialNo = ? , AcquisitionID = ? , DataBaseID = ? , BatchNo = ? , CertificateID = ? , CostID = ? , DataState = ? , CheckFlag = ? , BusinessCode = ? , FeeCode = ? , SumActuMoney = ? , AccountDate = ? , BusinessNo = ? , StringInfo01 = ? , StringInfo02 = ? , StringInfo03 = ? , StringInfo04 = ? , StringInfo05 = ? , StringInfo06 = ? , StringInfo07 = ? , StringInfo08 = ? , StringInfo09 = ? , StringInfo10 = ? , StringInfo11 = ? , StringInfo12 = ? , StringInfo13 = ? , StringInfo14 = ? , StringInfo15 = ? , DateInfo01 = ? , DateInfo02 = ? , DateInfo03 = ? , DateInfo04 = ? , DateInfo05 = ? , DateInfo06 = ? , DateInfo07 = ? , DateInfo08 = ? , DateInfo09 = ? , DateInfo10 = ? , NumInfo01 = ? , NumInfo02 = ? , NumInfo03 = ? , NumInfo04 = ? , NumInfo05 = ? , BusinessNo01 = ? , BusinessNo02 = ? , BusinessNo03 = ? , BusinessNo04 = ? , BusinessNo05 = ? , BusinessNo06 = ? , BusinessNo07 = ? , BusinessNo08 = ? , BusinessNo09 = ? , BusinessNo10 = ? , TypeFlag01 = ? , TypeFlag02 = ? , TypeFlag03 = ? , TypeFlag04 = ? , TypeFlag05 = ? , TypeFlag06 = ? , TypeFlag07 = ? , TypeFlag08 = ? , TypeFlag09 = ? , TypeFlag10 = ? , Relationcode = ? , MakeDate = ? , MakeTime = ? WHERE  ASerialNo = ?");
			if(this.getASerialNo() == null || this.getASerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getASerialNo().trim());
			}
			if(this.getAcquisitionID() == null || this.getAcquisitionID().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getAcquisitionID().trim());
			}
			if(this.getDataBaseID() == null || this.getDataBaseID().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getDataBaseID().trim());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getBatchNo().trim());
			}
			if(this.getCertificateID() == null || this.getCertificateID().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getCertificateID().trim());
			}
			if(this.getCostID() == null || this.getCostID().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCostID().trim());
			}
			if(this.getDataState() == null || this.getDataState().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getDataState().trim());
			}
			if(this.getCheckFlag() == null || this.getCheckFlag().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getCheckFlag().trim());
			}
			if(this.getBusinessCode() == null || this.getBusinessCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getBusinessCode().trim());
			}
			if(this.getFeeCode() == null || this.getFeeCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getFeeCode().trim());
			}
			pstmt.setDouble(11, this.getSumActuMoney());
			if(this.getAccountDate() == null || this.getAccountDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getAccountDate()));
			}
			if(this.getBusinessNo() == null || this.getBusinessNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getBusinessNo().trim());
			}
			if(this.getStringInfo01() == null || this.getStringInfo01().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getStringInfo01().trim());
			}
			if(this.getStringInfo02() == null || this.getStringInfo02().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getStringInfo02().trim());
			}
			if(this.getStringInfo03() == null || this.getStringInfo03().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getStringInfo03().trim());
			}
			if(this.getStringInfo04() == null || this.getStringInfo04().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getStringInfo04().trim());
			}
			if(this.getStringInfo05() == null || this.getStringInfo05().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getStringInfo05().trim());
			}
			if(this.getStringInfo06() == null || this.getStringInfo06().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getStringInfo06().trim());
			}
			if(this.getStringInfo07() == null || this.getStringInfo07().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getStringInfo07().trim());
			}
			if(this.getStringInfo08() == null || this.getStringInfo08().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getStringInfo08().trim());
			}
			if(this.getStringInfo09() == null || this.getStringInfo09().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getStringInfo09().trim());
			}
			if(this.getStringInfo10() == null || this.getStringInfo10().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getStringInfo10().trim());
			}
			if(this.getStringInfo11() == null || this.getStringInfo11().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getStringInfo11().trim());
			}
			if(this.getStringInfo12() == null || this.getStringInfo12().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getStringInfo12().trim());
			}
			if(this.getStringInfo13() == null || this.getStringInfo13().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStringInfo13().trim());
			}
			if(this.getStringInfo14() == null || this.getStringInfo14().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getStringInfo14().trim());
			}
			if(this.getStringInfo15() == null || this.getStringInfo15().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getStringInfo15().trim());
			}
			if(this.getDateInfo01() == null || this.getDateInfo01().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getDateInfo01()));
			}
			if(this.getDateInfo02() == null || this.getDateInfo02().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getDateInfo02()));
			}
			if(this.getDateInfo03() == null || this.getDateInfo03().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getDateInfo03()));
			}
			if(this.getDateInfo04() == null || this.getDateInfo04().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getDateInfo04()));
			}
			if(this.getDateInfo05() == null || this.getDateInfo05().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getDateInfo05()));
			}
			if(this.getDateInfo06() == null || this.getDateInfo06().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getDateInfo06()));
			}
			if(this.getDateInfo07() == null || this.getDateInfo07().equals("null")) {
				pstmt.setNull(35, 91);
			} else {
				pstmt.setDate(35, Date.valueOf(this.getDateInfo07()));
			}
			if(this.getDateInfo08() == null || this.getDateInfo08().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getDateInfo08()));
			}
			if(this.getDateInfo09() == null || this.getDateInfo09().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getDateInfo09()));
			}
			if(this.getDateInfo10() == null || this.getDateInfo10().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getDateInfo10()));
			}
			pstmt.setDouble(39, this.getNumInfo01());
			pstmt.setDouble(40, this.getNumInfo02());
			pstmt.setDouble(41, this.getNumInfo03());
			pstmt.setDouble(42, this.getNumInfo04());
			pstmt.setDouble(43, this.getNumInfo05());
			if(this.getBusinessNo01() == null || this.getBusinessNo01().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getBusinessNo01().trim());
			}
			if(this.getBusinessNo02() == null || this.getBusinessNo02().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getBusinessNo02().trim());
			}
			if(this.getBusinessNo03() == null || this.getBusinessNo03().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getBusinessNo03().trim());
			}
			if(this.getBusinessNo04() == null || this.getBusinessNo04().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getBusinessNo04().trim());
			}
			if(this.getBusinessNo05() == null || this.getBusinessNo05().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getBusinessNo05().trim());
			}
			if(this.getBusinessNo06() == null || this.getBusinessNo06().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getBusinessNo06().trim());
			}
			if(this.getBusinessNo07() == null || this.getBusinessNo07().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getBusinessNo07().trim());
			}
			if(this.getBusinessNo08() == null || this.getBusinessNo08().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getBusinessNo08().trim());
			}
			if(this.getBusinessNo09() == null || this.getBusinessNo09().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getBusinessNo09().trim());
			}
			if(this.getBusinessNo10() == null || this.getBusinessNo10().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getBusinessNo10().trim());
			}
			if(this.getTypeFlag01() == null || this.getTypeFlag01().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getTypeFlag01().trim());
			}
			if(this.getTypeFlag02() == null || this.getTypeFlag02().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getTypeFlag02().trim());
			}
			if(this.getTypeFlag03() == null || this.getTypeFlag03().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getTypeFlag03().trim());
			}
			if(this.getTypeFlag04() == null || this.getTypeFlag04().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getTypeFlag04().trim());
			}
			if(this.getTypeFlag05() == null || this.getTypeFlag05().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getTypeFlag05().trim());
			}
			if(this.getTypeFlag06() == null || this.getTypeFlag06().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getTypeFlag06().trim());
			}
			if(this.getTypeFlag07() == null || this.getTypeFlag07().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getTypeFlag07().trim());
			}
			if(this.getTypeFlag08() == null || this.getTypeFlag08().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getTypeFlag08().trim());
			}
			if(this.getTypeFlag09() == null || this.getTypeFlag09().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getTypeFlag09().trim());
			}
			if(this.getTypeFlag10() == null || this.getTypeFlag10().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getTypeFlag10().trim());
			}
			if(this.getRelationcode() == null || this.getRelationcode().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getRelationcode().trim());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(65, 91);
			} else {
				pstmt.setDate(65, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getMakeTime().trim());
			}
			// set where condition
			if(this.getASerialNo() == null || this.getASerialNo().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getASerialNo().trim());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("FIAboriginalData");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();
//真实的执行语句
logger.debug(String.valueOf("True Error Sql is : UPDATE FIAboriginalData SET ASerialNo='"+this.getASerialNo() + "'" + " , AcquisitionID='"+this.getAcquisitionID() + "'" + " , DataBaseID='"+this.getDataBaseID() + "'" + " , BatchNo='"+this.getBatchNo() + "'" + " , CertificateID='"+this.getCertificateID() + "'" + " , CostID='"+this.getCostID() + "'" + " , DataState='"+this.getDataState() + "'" + " , CheckFlag='"+this.getCheckFlag() + "'" + " , BusinessCode='"+this.getBusinessCode() + "'" + " , FeeCode='"+this.getFeeCode() + "'" + " , SumActuMoney="+this.getSumActuMoney() + " , AccountDate='"+this.getAccountDate() + "'" + " , BusinessNo='"+this.getBusinessNo() + "'" + " , StringInfo01='"+this.getStringInfo01() + "'" + " , StringInfo02='"+this.getStringInfo02() + "'" + " , StringInfo03='"+this.getStringInfo03() + "'" + " , StringInfo04='"+this.getStringInfo04() + "'" + " , StringInfo05='"+this.getStringInfo05() + "'" + " , StringInfo06='"+this.getStringInfo06() + "'" + " , StringInfo07='"+this.getStringInfo07() + "'" + " , StringInfo08='"+this.getStringInfo08() + "'" + " , StringInfo09='"+this.getStringInfo09() + "'" + " , StringInfo10='"+this.getStringInfo10() + "'" + " , StringInfo11='"+this.getStringInfo11() + "'" + " , StringInfo12='"+this.getStringInfo12() + "'" + " , StringInfo13='"+this.getStringInfo13() + "'" + " , StringInfo14='"+this.getStringInfo14() + "'" + " , StringInfo15='"+this.getStringInfo15() + "'" + " , DateInfo01='"+this.getDateInfo01() + "'" + " , DateInfo02='"+this.getDateInfo02() + "'" + " , DateInfo03='"+this.getDateInfo03() + "'" + " , DateInfo04='"+this.getDateInfo04() + "'" + " , DateInfo05='"+this.getDateInfo05() + "'" + " , DateInfo06='"+this.getDateInfo06() + "'" + " , DateInfo07='"+this.getDateInfo07() + "'" + " , DateInfo08='"+this.getDateInfo08() + "'" + " , DateInfo09='"+this.getDateInfo09() + "'" + " , DateInfo10='"+this.getDateInfo10() + "'" + " , NumInfo01="+this.getNumInfo01() + " , NumInfo02="+this.getNumInfo02() + " , NumInfo03="+this.getNumInfo03() + " , NumInfo04="+this.getNumInfo04() + " , NumInfo05="+this.getNumInfo05() + " , BusinessNo01='"+this.getBusinessNo01() + "'" + " , BusinessNo02='"+this.getBusinessNo02() + "'" + " , BusinessNo03='"+this.getBusinessNo03() + "'" + " , BusinessNo04='"+this.getBusinessNo04() + "'" + " , BusinessNo05='"+this.getBusinessNo05() + "'" + " , BusinessNo06='"+this.getBusinessNo06() + "'" + " , BusinessNo07='"+this.getBusinessNo07() + "'" + " , BusinessNo08='"+this.getBusinessNo08() + "'" + " , BusinessNo09='"+this.getBusinessNo09() + "'" + " , BusinessNo10='"+this.getBusinessNo10() + "'" + " , TypeFlag01='"+this.getTypeFlag01() + "'" + " , TypeFlag02='"+this.getTypeFlag02() + "'" + " , TypeFlag03='"+this.getTypeFlag03() + "'" + " , TypeFlag04='"+this.getTypeFlag04() + "'" + " , TypeFlag05='"+this.getTypeFlag05() + "'" + " , TypeFlag06='"+this.getTypeFlag06() + "'" + " , TypeFlag07='"+this.getTypeFlag07() + "'" + " , TypeFlag08='"+this.getTypeFlag08() + "'" + " , TypeFlag09='"+this.getTypeFlag09() + "'" + " , TypeFlag10='"+this.getTypeFlag10() + "'" + " , Relationcode='"+this.getRelationcode() + "'" + " , MakeDate='"+this.getMakeDate() + "'" + " , MakeTime='"+this.getMakeTime() + "'"+" WHERE ASerialNo='"+this.getASerialNo() + "'").replaceAll("'null'", "null"));

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


		try
		{
			pstmt = con.prepareStatement("INSERT INTO FIAboriginalData VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getASerialNo() == null || this.getASerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getASerialNo().trim());
			}
			if(this.getAcquisitionID() == null || this.getAcquisitionID().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getAcquisitionID().trim());
			}
			if(this.getDataBaseID() == null || this.getDataBaseID().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getDataBaseID().trim());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getBatchNo().trim());
			}
			if(this.getCertificateID() == null || this.getCertificateID().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getCertificateID().trim());
			}
			if(this.getCostID() == null || this.getCostID().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCostID().trim());
			}
			if(this.getDataState() == null || this.getDataState().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getDataState().trim());
			}
			if(this.getCheckFlag() == null || this.getCheckFlag().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getCheckFlag().trim());
			}
			if(this.getBusinessCode() == null || this.getBusinessCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getBusinessCode().trim());
			}
			if(this.getFeeCode() == null || this.getFeeCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getFeeCode().trim());
			}
			pstmt.setDouble(11, this.getSumActuMoney());
			if(this.getAccountDate() == null || this.getAccountDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getAccountDate()));
			}
			if(this.getBusinessNo() == null || this.getBusinessNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getBusinessNo().trim());
			}
			if(this.getStringInfo01() == null || this.getStringInfo01().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getStringInfo01().trim());
			}
			if(this.getStringInfo02() == null || this.getStringInfo02().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getStringInfo02().trim());
			}
			if(this.getStringInfo03() == null || this.getStringInfo03().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getStringInfo03().trim());
			}
			if(this.getStringInfo04() == null || this.getStringInfo04().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getStringInfo04().trim());
			}
			if(this.getStringInfo05() == null || this.getStringInfo05().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getStringInfo05().trim());
			}
			if(this.getStringInfo06() == null || this.getStringInfo06().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getStringInfo06().trim());
			}
			if(this.getStringInfo07() == null || this.getStringInfo07().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getStringInfo07().trim());
			}
			if(this.getStringInfo08() == null || this.getStringInfo08().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getStringInfo08().trim());
			}
			if(this.getStringInfo09() == null || this.getStringInfo09().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getStringInfo09().trim());
			}
			if(this.getStringInfo10() == null || this.getStringInfo10().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getStringInfo10().trim());
			}
			if(this.getStringInfo11() == null || this.getStringInfo11().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getStringInfo11().trim());
			}
			if(this.getStringInfo12() == null || this.getStringInfo12().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getStringInfo12().trim());
			}
			if(this.getStringInfo13() == null || this.getStringInfo13().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStringInfo13().trim());
			}
			if(this.getStringInfo14() == null || this.getStringInfo14().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getStringInfo14().trim());
			}
			if(this.getStringInfo15() == null || this.getStringInfo15().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getStringInfo15().trim());
			}
			if(this.getDateInfo01() == null || this.getDateInfo01().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getDateInfo01()));
			}
			if(this.getDateInfo02() == null || this.getDateInfo02().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getDateInfo02()));
			}
			if(this.getDateInfo03() == null || this.getDateInfo03().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getDateInfo03()));
			}
			if(this.getDateInfo04() == null || this.getDateInfo04().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getDateInfo04()));
			}
			if(this.getDateInfo05() == null || this.getDateInfo05().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getDateInfo05()));
			}
			if(this.getDateInfo06() == null || this.getDateInfo06().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getDateInfo06()));
			}
			if(this.getDateInfo07() == null || this.getDateInfo07().equals("null")) {
				pstmt.setNull(35, 91);
			} else {
				pstmt.setDate(35, Date.valueOf(this.getDateInfo07()));
			}
			if(this.getDateInfo08() == null || this.getDateInfo08().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getDateInfo08()));
			}
			if(this.getDateInfo09() == null || this.getDateInfo09().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getDateInfo09()));
			}
			if(this.getDateInfo10() == null || this.getDateInfo10().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getDateInfo10()));
			}
			pstmt.setDouble(39, this.getNumInfo01());
			pstmt.setDouble(40, this.getNumInfo02());
			pstmt.setDouble(41, this.getNumInfo03());
			pstmt.setDouble(42, this.getNumInfo04());
			pstmt.setDouble(43, this.getNumInfo05());
			if(this.getBusinessNo01() == null || this.getBusinessNo01().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getBusinessNo01().trim());
			}
			if(this.getBusinessNo02() == null || this.getBusinessNo02().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getBusinessNo02().trim());
			}
			if(this.getBusinessNo03() == null || this.getBusinessNo03().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getBusinessNo03().trim());
			}
			if(this.getBusinessNo04() == null || this.getBusinessNo04().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getBusinessNo04().trim());
			}
			if(this.getBusinessNo05() == null || this.getBusinessNo05().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getBusinessNo05().trim());
			}
			if(this.getBusinessNo06() == null || this.getBusinessNo06().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getBusinessNo06().trim());
			}
			if(this.getBusinessNo07() == null || this.getBusinessNo07().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getBusinessNo07().trim());
			}
			if(this.getBusinessNo08() == null || this.getBusinessNo08().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getBusinessNo08().trim());
			}
			if(this.getBusinessNo09() == null || this.getBusinessNo09().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getBusinessNo09().trim());
			}
			if(this.getBusinessNo10() == null || this.getBusinessNo10().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getBusinessNo10().trim());
			}
			if(this.getTypeFlag01() == null || this.getTypeFlag01().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getTypeFlag01().trim());
			}
			if(this.getTypeFlag02() == null || this.getTypeFlag02().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getTypeFlag02().trim());
			}
			if(this.getTypeFlag03() == null || this.getTypeFlag03().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getTypeFlag03().trim());
			}
			if(this.getTypeFlag04() == null || this.getTypeFlag04().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getTypeFlag04().trim());
			}
			if(this.getTypeFlag05() == null || this.getTypeFlag05().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getTypeFlag05().trim());
			}
			if(this.getTypeFlag06() == null || this.getTypeFlag06().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getTypeFlag06().trim());
			}
			if(this.getTypeFlag07() == null || this.getTypeFlag07().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getTypeFlag07().trim());
			}
			if(this.getTypeFlag08() == null || this.getTypeFlag08().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getTypeFlag08().trim());
			}
			if(this.getTypeFlag09() == null || this.getTypeFlag09().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getTypeFlag09().trim());
			}
			if(this.getTypeFlag10() == null || this.getTypeFlag10().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getTypeFlag10().trim());
			}
			if(this.getRelationcode() == null || this.getRelationcode().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getRelationcode().trim());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(65, 91);
			} else {
				pstmt.setDate(65, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getMakeTime().trim());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("FIAboriginalData");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();
//真实的执行语句
logger.debug(String.valueOf("True Error Sql is : INSERT INTO FIAboriginalData VALUES('"+this.getASerialNo() + "'"+",'"+this.getAcquisitionID() + "'"+",'"+this.getDataBaseID() + "'"+",'"+this.getBatchNo() + "'"+",'"+this.getCertificateID() + "'"+",'"+this.getCostID() + "'"+",'"+this.getDataState() + "'"+",'"+this.getCheckFlag() + "'"+",'"+this.getBusinessCode() + "'"+",'"+this.getFeeCode() + "'"+","+this.getSumActuMoney()+",'"+this.getAccountDate() + "'"+",'"+this.getBusinessNo() + "'"+",'"+this.getStringInfo01() + "'"+",'"+this.getStringInfo02() + "'"+",'"+this.getStringInfo03() + "'"+",'"+this.getStringInfo04() + "'"+",'"+this.getStringInfo05() + "'"+",'"+this.getStringInfo06() + "'"+",'"+this.getStringInfo07() + "'"+",'"+this.getStringInfo08() + "'"+",'"+this.getStringInfo09() + "'"+",'"+this.getStringInfo10() + "'"+",'"+this.getStringInfo11() + "'"+",'"+this.getStringInfo12() + "'"+",'"+this.getStringInfo13() + "'"+",'"+this.getStringInfo14() + "'"+",'"+this.getStringInfo15() + "'"+",'"+this.getDateInfo01() + "'"+",'"+this.getDateInfo02() + "'"+",'"+this.getDateInfo03() + "'"+",'"+this.getDateInfo04() + "'"+",'"+this.getDateInfo05() + "'"+",'"+this.getDateInfo06() + "'"+",'"+this.getDateInfo07() + "'"+",'"+this.getDateInfo08() + "'"+",'"+this.getDateInfo09() + "'"+",'"+this.getDateInfo10() + "'"+","+this.getNumInfo01()+","+this.getNumInfo02()+","+this.getNumInfo03()+","+this.getNumInfo04()+","+this.getNumInfo05()+",'"+this.getBusinessNo01() + "'"+",'"+this.getBusinessNo02() + "'"+",'"+this.getBusinessNo03() + "'"+",'"+this.getBusinessNo04() + "'"+",'"+this.getBusinessNo05() + "'"+",'"+this.getBusinessNo06() + "'"+",'"+this.getBusinessNo07() + "'"+",'"+this.getBusinessNo08() + "'"+",'"+this.getBusinessNo09() + "'"+",'"+this.getBusinessNo10() + "'"+",'"+this.getTypeFlag01() + "'"+",'"+this.getTypeFlag02() + "'"+",'"+this.getTypeFlag03() + "'"+",'"+this.getTypeFlag04() + "'"+",'"+this.getTypeFlag05() + "'"+",'"+this.getTypeFlag06() + "'"+",'"+this.getTypeFlag07() + "'"+",'"+this.getTypeFlag08() + "'"+",'"+this.getTypeFlag09() + "'"+",'"+this.getTypeFlag10() + "'"+",'"+this.getRelationcode() + "'"+",'"+this.getMakeDate() + "'"+",'"+this.getMakeTime() + "'"+")").replaceAll("'null'", "null"));

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
			pstmt = con.prepareStatement("SELECT * FROM FIAboriginalData WHERE  ASerialNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getASerialNo() == null || this.getASerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getASerialNo().trim());
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
					tError.moduleName = "FIAboriginalDataDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
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

	public FIAboriginalDataSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		FIAboriginalDataSet aFIAboriginalDataSet = new FIAboriginalDataSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("FIAboriginalData");
			FIAboriginalDataSchema aSchema = this.getSchema();
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
				FIAboriginalDataSchema s1 = new FIAboriginalDataSchema();
				s1.setSchema(rs,i);
				aFIAboriginalDataSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
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

		return aFIAboriginalDataSet;

	}

	public FIAboriginalDataSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		FIAboriginalDataSet aFIAboriginalDataSet = new FIAboriginalDataSet();

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
				FIAboriginalDataSchema s1 = new FIAboriginalDataSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FIAboriginalDataDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aFIAboriginalDataSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
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

		return aFIAboriginalDataSet;
	}

	public FIAboriginalDataSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		FIAboriginalDataSet aFIAboriginalDataSet = new FIAboriginalDataSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("FIAboriginalData");
			FIAboriginalDataSchema aSchema = this.getSchema();
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

				FIAboriginalDataSchema s1 = new FIAboriginalDataSchema();
				s1.setSchema(rs,i);
				aFIAboriginalDataSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
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

		return aFIAboriginalDataSet;

	}

	public FIAboriginalDataSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		FIAboriginalDataSet aFIAboriginalDataSet = new FIAboriginalDataSet();

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

				FIAboriginalDataSchema s1 = new FIAboriginalDataSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FIAboriginalDataDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aFIAboriginalDataSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
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

		return aFIAboriginalDataSet;
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
			SQLString sqlObj = new SQLString("FIAboriginalData");
			FIAboriginalDataSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update FIAboriginalData " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "FIAboriginalDataDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
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
        tError.moduleName = "FIAboriginalDataDB";
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
        tError.moduleName = "FIAboriginalDataDB";
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
        tError.moduleName = "FIAboriginalDataDB";
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
        tError.moduleName = "FIAboriginalDataDB";
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
 * @return FIAboriginalDataSet
 */
public FIAboriginalDataSet getData()
{
    int tCount = 0;
    FIAboriginalDataSet tFIAboriginalDataSet = new FIAboriginalDataSet();
    FIAboriginalDataSchema tFIAboriginalDataSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "FIAboriginalDataDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tFIAboriginalDataSchema = new FIAboriginalDataSchema();
        tFIAboriginalDataSchema.setSchema(mResultSet, 1);
        tFIAboriginalDataSet.add(tFIAboriginalDataSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tFIAboriginalDataSchema = new FIAboriginalDataSchema();
                tFIAboriginalDataSchema.setSchema(mResultSet, 1);
                tFIAboriginalDataSet.add(tFIAboriginalDataSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "FIAboriginalDataDB";
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
    return tFIAboriginalDataSet;
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
            tError.moduleName = "FIAboriginalDataDB";
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
        tError.moduleName = "FIAboriginalDataDB";
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
            tError.moduleName = "FIAboriginalDataDB";
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
        tError.moduleName = "FIAboriginalDataDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public FIAboriginalDataSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FIAboriginalDataSet aFIAboriginalDataSet = new FIAboriginalDataSet();

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
				FIAboriginalDataSchema s1 = new FIAboriginalDataSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FIAboriginalDataDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aFIAboriginalDataSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
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

		return aFIAboriginalDataSet;
	}

	public FIAboriginalDataSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FIAboriginalDataSet aFIAboriginalDataSet = new FIAboriginalDataSet();

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

				FIAboriginalDataSchema s1 = new FIAboriginalDataSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FIAboriginalDataDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aFIAboriginalDataSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataDB";
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

		return aFIAboriginalDataSet; 
	}

}
