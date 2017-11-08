/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.vschema.FIAboriginalDataBSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIAboriginalDataBDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIAboriginalDataBDBSet extends FIAboriginalDataBSet
{
private static Logger logger = Logger.getLogger(FIAboriginalDataBDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public FIAboriginalDataBDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"FIAboriginalDataB");
		mflag = true;
	}

	public FIAboriginalDataBDBSet()
	{
		db = new DBOper( "FIAboriginalDataB" );
		con = null;
		mflag = false;
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
			tError.moduleName = "FIAboriginalDataBDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FIAboriginalDataB WHERE  DelSerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getDelSerialNo() == null || this.get(i).getDelSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getDelSerialNo().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataBDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIAboriginalDataB");
		sqlObj.setSQL(4, this.get(i));
		sqlObj.getSQL();
            }

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
			pstmt = con.prepareStatement("UPDATE FIAboriginalDataB SET  DelSerialNo = ? , DelOperator = ? , DelReason = ? , ASerialNo = ? , AcquisitionID = ? , DataBaseID = ? , BatchNo = ? , CertificateID = ? , CostID = ? , DataState = ? , CheckFlag = ? , BusinessCode = ? , FeeCode = ? , SumActuMoney = ? , AccountDate = ? , BusinessNo = ? , StringInfo01 = ? , StringInfo02 = ? , StringInfo03 = ? , StringInfo04 = ? , StringInfo05 = ? , StringInfo06 = ? , StringInfo07 = ? , StringInfo08 = ? , StringInfo09 = ? , StringInfo10 = ? , StringInfo11 = ? , StringInfo12 = ? , StringInfo13 = ? , StringInfo14 = ? , StringInfo15 = ? , DateInfo01 = ? , DateInfo02 = ? , DateInfo03 = ? , DateInfo04 = ? , DateInfo05 = ? , DateInfo06 = ? , DateInfo07 = ? , DateInfo08 = ? , DateInfo09 = ? , DateInfo10 = ? , NumInfo01 = ? , NumInfo02 = ? , NumInfo03 = ? , NumInfo04 = ? , NumInfo05 = ? , BusinessNo01 = ? , BusinessNo02 = ? , BusinessNo03 = ? , BusinessNo04 = ? , BusinessNo05 = ? , BusinessNo06 = ? , BusinessNo07 = ? , BusinessNo08 = ? , BusinessNo09 = ? , BusinessNo10 = ? , TypeFlag01 = ? , TypeFlag02 = ? , TypeFlag03 = ? , TypeFlag04 = ? , TypeFlag05 = ? , TypeFlag06 = ? , TypeFlag07 = ? , TypeFlag08 = ? , TypeFlag09 = ? , TypeFlag10 = ? , Relationcode = ? , MakeDate = ? , MakeTime = ? WHERE  DelSerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getDelSerialNo() == null || this.get(i).getDelSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getDelSerialNo().trim());
			}
			if(this.get(i).getDelOperator() == null || this.get(i).getDelOperator().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDelOperator().trim());
			}
			if(this.get(i).getDelReason() == null || this.get(i).getDelReason().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDelReason().trim());
			}
			if(this.get(i).getASerialNo() == null || this.get(i).getASerialNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getASerialNo().trim());
			}
			if(this.get(i).getAcquisitionID() == null || this.get(i).getAcquisitionID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAcquisitionID().trim());
			}
			if(this.get(i).getDataBaseID() == null || this.get(i).getDataBaseID().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDataBaseID().trim());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBatchNo().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCertificateID().trim());
			}
			if(this.get(i).getCostID() == null || this.get(i).getCostID().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCostID().trim());
			}
			if(this.get(i).getDataState() == null || this.get(i).getDataState().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getDataState().trim());
			}
			if(this.get(i).getCheckFlag() == null || this.get(i).getCheckFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCheckFlag().trim());
			}
			if(this.get(i).getBusinessCode() == null || this.get(i).getBusinessCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getBusinessCode().trim());
			}
			if(this.get(i).getFeeCode() == null || this.get(i).getFeeCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getFeeCode().trim());
			}
			pstmt.setDouble(14, this.get(i).getSumActuMoney());
			if(this.get(i).getAccountDate() == null || this.get(i).getAccountDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getAccountDate()));
			}
			if(this.get(i).getBusinessNo() == null || this.get(i).getBusinessNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBusinessNo().trim());
			}
			if(this.get(i).getStringInfo01() == null || this.get(i).getStringInfo01().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStringInfo01().trim());
			}
			if(this.get(i).getStringInfo02() == null || this.get(i).getStringInfo02().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStringInfo02().trim());
			}
			if(this.get(i).getStringInfo03() == null || this.get(i).getStringInfo03().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getStringInfo03().trim());
			}
			if(this.get(i).getStringInfo04() == null || this.get(i).getStringInfo04().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getStringInfo04().trim());
			}
			if(this.get(i).getStringInfo05() == null || this.get(i).getStringInfo05().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getStringInfo05().trim());
			}
			if(this.get(i).getStringInfo06() == null || this.get(i).getStringInfo06().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getStringInfo06().trim());
			}
			if(this.get(i).getStringInfo07() == null || this.get(i).getStringInfo07().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getStringInfo07().trim());
			}
			if(this.get(i).getStringInfo08() == null || this.get(i).getStringInfo08().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getStringInfo08().trim());
			}
			if(this.get(i).getStringInfo09() == null || this.get(i).getStringInfo09().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStringInfo09().trim());
			}
			if(this.get(i).getStringInfo10() == null || this.get(i).getStringInfo10().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStringInfo10().trim());
			}
			if(this.get(i).getStringInfo11() == null || this.get(i).getStringInfo11().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getStringInfo11().trim());
			}
			if(this.get(i).getStringInfo12() == null || this.get(i).getStringInfo12().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getStringInfo12().trim());
			}
			if(this.get(i).getStringInfo13() == null || this.get(i).getStringInfo13().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getStringInfo13().trim());
			}
			if(this.get(i).getStringInfo14() == null || this.get(i).getStringInfo14().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getStringInfo14().trim());
			}
			if(this.get(i).getStringInfo15() == null || this.get(i).getStringInfo15().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getStringInfo15().trim());
			}
			if(this.get(i).getDateInfo01() == null || this.get(i).getDateInfo01().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getDateInfo01()));
			}
			if(this.get(i).getDateInfo02() == null || this.get(i).getDateInfo02().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getDateInfo02()));
			}
			if(this.get(i).getDateInfo03() == null || this.get(i).getDateInfo03().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getDateInfo03()));
			}
			if(this.get(i).getDateInfo04() == null || this.get(i).getDateInfo04().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getDateInfo04()));
			}
			if(this.get(i).getDateInfo05() == null || this.get(i).getDateInfo05().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getDateInfo05()));
			}
			if(this.get(i).getDateInfo06() == null || this.get(i).getDateInfo06().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getDateInfo06()));
			}
			if(this.get(i).getDateInfo07() == null || this.get(i).getDateInfo07().equals("null")) {
				pstmt.setDate(38,null);
			} else {
				pstmt.setDate(38, Date.valueOf(this.get(i).getDateInfo07()));
			}
			if(this.get(i).getDateInfo08() == null || this.get(i).getDateInfo08().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getDateInfo08()));
			}
			if(this.get(i).getDateInfo09() == null || this.get(i).getDateInfo09().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getDateInfo09()));
			}
			if(this.get(i).getDateInfo10() == null || this.get(i).getDateInfo10().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getDateInfo10()));
			}
			pstmt.setDouble(42, this.get(i).getNumInfo01());
			pstmt.setDouble(43, this.get(i).getNumInfo02());
			pstmt.setDouble(44, this.get(i).getNumInfo03());
			pstmt.setDouble(45, this.get(i).getNumInfo04());
			pstmt.setDouble(46, this.get(i).getNumInfo05());
			if(this.get(i).getBusinessNo01() == null || this.get(i).getBusinessNo01().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getBusinessNo01().trim());
			}
			if(this.get(i).getBusinessNo02() == null || this.get(i).getBusinessNo02().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getBusinessNo02().trim());
			}
			if(this.get(i).getBusinessNo03() == null || this.get(i).getBusinessNo03().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getBusinessNo03().trim());
			}
			if(this.get(i).getBusinessNo04() == null || this.get(i).getBusinessNo04().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getBusinessNo04().trim());
			}
			if(this.get(i).getBusinessNo05() == null || this.get(i).getBusinessNo05().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getBusinessNo05().trim());
			}
			if(this.get(i).getBusinessNo06() == null || this.get(i).getBusinessNo06().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getBusinessNo06().trim());
			}
			if(this.get(i).getBusinessNo07() == null || this.get(i).getBusinessNo07().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getBusinessNo07().trim());
			}
			if(this.get(i).getBusinessNo08() == null || this.get(i).getBusinessNo08().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getBusinessNo08().trim());
			}
			if(this.get(i).getBusinessNo09() == null || this.get(i).getBusinessNo09().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getBusinessNo09().trim());
			}
			if(this.get(i).getBusinessNo10() == null || this.get(i).getBusinessNo10().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getBusinessNo10().trim());
			}
			if(this.get(i).getTypeFlag01() == null || this.get(i).getTypeFlag01().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getTypeFlag01().trim());
			}
			if(this.get(i).getTypeFlag02() == null || this.get(i).getTypeFlag02().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getTypeFlag02().trim());
			}
			if(this.get(i).getTypeFlag03() == null || this.get(i).getTypeFlag03().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getTypeFlag03().trim());
			}
			if(this.get(i).getTypeFlag04() == null || this.get(i).getTypeFlag04().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getTypeFlag04().trim());
			}
			if(this.get(i).getTypeFlag05() == null || this.get(i).getTypeFlag05().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getTypeFlag05().trim());
			}
			if(this.get(i).getTypeFlag06() == null || this.get(i).getTypeFlag06().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getTypeFlag06().trim());
			}
			if(this.get(i).getTypeFlag07() == null || this.get(i).getTypeFlag07().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getTypeFlag07().trim());
			}
			if(this.get(i).getTypeFlag08() == null || this.get(i).getTypeFlag08().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getTypeFlag08().trim());
			}
			if(this.get(i).getTypeFlag09() == null || this.get(i).getTypeFlag09().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getTypeFlag09().trim());
			}
			if(this.get(i).getTypeFlag10() == null || this.get(i).getTypeFlag10().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getTypeFlag10().trim());
			}
			if(this.get(i).getRelationcode() == null || this.get(i).getRelationcode().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getRelationcode().trim());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(68,null);
			} else {
				pstmt.setDate(68, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getMakeTime().trim());
			}
			// set where condition
			if(this.get(i).getDelSerialNo() == null || this.get(i).getDelSerialNo().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getDelSerialNo().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataBDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIAboriginalDataB");
		sqlObj.setSQL(2, this.get(i));
		sqlObj.getSQL();
            }

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
			pstmt = con.prepareStatement("INSERT INTO FIAboriginalDataB VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getDelSerialNo() == null || this.get(i).getDelSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getDelSerialNo().trim());
			}
			if(this.get(i).getDelOperator() == null || this.get(i).getDelOperator().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDelOperator().trim());
			}
			if(this.get(i).getDelReason() == null || this.get(i).getDelReason().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDelReason().trim());
			}
			if(this.get(i).getASerialNo() == null || this.get(i).getASerialNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getASerialNo().trim());
			}
			if(this.get(i).getAcquisitionID() == null || this.get(i).getAcquisitionID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAcquisitionID().trim());
			}
			if(this.get(i).getDataBaseID() == null || this.get(i).getDataBaseID().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDataBaseID().trim());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBatchNo().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCertificateID().trim());
			}
			if(this.get(i).getCostID() == null || this.get(i).getCostID().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCostID().trim());
			}
			if(this.get(i).getDataState() == null || this.get(i).getDataState().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getDataState().trim());
			}
			if(this.get(i).getCheckFlag() == null || this.get(i).getCheckFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCheckFlag().trim());
			}
			if(this.get(i).getBusinessCode() == null || this.get(i).getBusinessCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getBusinessCode().trim());
			}
			if(this.get(i).getFeeCode() == null || this.get(i).getFeeCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getFeeCode().trim());
			}
			pstmt.setDouble(14, this.get(i).getSumActuMoney());
			if(this.get(i).getAccountDate() == null || this.get(i).getAccountDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getAccountDate()));
			}
			if(this.get(i).getBusinessNo() == null || this.get(i).getBusinessNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBusinessNo().trim());
			}
			if(this.get(i).getStringInfo01() == null || this.get(i).getStringInfo01().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStringInfo01().trim());
			}
			if(this.get(i).getStringInfo02() == null || this.get(i).getStringInfo02().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStringInfo02().trim());
			}
			if(this.get(i).getStringInfo03() == null || this.get(i).getStringInfo03().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getStringInfo03().trim());
			}
			if(this.get(i).getStringInfo04() == null || this.get(i).getStringInfo04().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getStringInfo04().trim());
			}
			if(this.get(i).getStringInfo05() == null || this.get(i).getStringInfo05().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getStringInfo05().trim());
			}
			if(this.get(i).getStringInfo06() == null || this.get(i).getStringInfo06().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getStringInfo06().trim());
			}
			if(this.get(i).getStringInfo07() == null || this.get(i).getStringInfo07().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getStringInfo07().trim());
			}
			if(this.get(i).getStringInfo08() == null || this.get(i).getStringInfo08().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getStringInfo08().trim());
			}
			if(this.get(i).getStringInfo09() == null || this.get(i).getStringInfo09().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStringInfo09().trim());
			}
			if(this.get(i).getStringInfo10() == null || this.get(i).getStringInfo10().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStringInfo10().trim());
			}
			if(this.get(i).getStringInfo11() == null || this.get(i).getStringInfo11().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getStringInfo11().trim());
			}
			if(this.get(i).getStringInfo12() == null || this.get(i).getStringInfo12().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getStringInfo12().trim());
			}
			if(this.get(i).getStringInfo13() == null || this.get(i).getStringInfo13().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getStringInfo13().trim());
			}
			if(this.get(i).getStringInfo14() == null || this.get(i).getStringInfo14().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getStringInfo14().trim());
			}
			if(this.get(i).getStringInfo15() == null || this.get(i).getStringInfo15().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getStringInfo15().trim());
			}
			if(this.get(i).getDateInfo01() == null || this.get(i).getDateInfo01().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getDateInfo01()));
			}
			if(this.get(i).getDateInfo02() == null || this.get(i).getDateInfo02().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getDateInfo02()));
			}
			if(this.get(i).getDateInfo03() == null || this.get(i).getDateInfo03().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getDateInfo03()));
			}
			if(this.get(i).getDateInfo04() == null || this.get(i).getDateInfo04().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getDateInfo04()));
			}
			if(this.get(i).getDateInfo05() == null || this.get(i).getDateInfo05().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getDateInfo05()));
			}
			if(this.get(i).getDateInfo06() == null || this.get(i).getDateInfo06().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getDateInfo06()));
			}
			if(this.get(i).getDateInfo07() == null || this.get(i).getDateInfo07().equals("null")) {
				pstmt.setDate(38,null);
			} else {
				pstmt.setDate(38, Date.valueOf(this.get(i).getDateInfo07()));
			}
			if(this.get(i).getDateInfo08() == null || this.get(i).getDateInfo08().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getDateInfo08()));
			}
			if(this.get(i).getDateInfo09() == null || this.get(i).getDateInfo09().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getDateInfo09()));
			}
			if(this.get(i).getDateInfo10() == null || this.get(i).getDateInfo10().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getDateInfo10()));
			}
			pstmt.setDouble(42, this.get(i).getNumInfo01());
			pstmt.setDouble(43, this.get(i).getNumInfo02());
			pstmt.setDouble(44, this.get(i).getNumInfo03());
			pstmt.setDouble(45, this.get(i).getNumInfo04());
			pstmt.setDouble(46, this.get(i).getNumInfo05());
			if(this.get(i).getBusinessNo01() == null || this.get(i).getBusinessNo01().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getBusinessNo01().trim());
			}
			if(this.get(i).getBusinessNo02() == null || this.get(i).getBusinessNo02().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getBusinessNo02().trim());
			}
			if(this.get(i).getBusinessNo03() == null || this.get(i).getBusinessNo03().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getBusinessNo03().trim());
			}
			if(this.get(i).getBusinessNo04() == null || this.get(i).getBusinessNo04().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getBusinessNo04().trim());
			}
			if(this.get(i).getBusinessNo05() == null || this.get(i).getBusinessNo05().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getBusinessNo05().trim());
			}
			if(this.get(i).getBusinessNo06() == null || this.get(i).getBusinessNo06().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getBusinessNo06().trim());
			}
			if(this.get(i).getBusinessNo07() == null || this.get(i).getBusinessNo07().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getBusinessNo07().trim());
			}
			if(this.get(i).getBusinessNo08() == null || this.get(i).getBusinessNo08().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getBusinessNo08().trim());
			}
			if(this.get(i).getBusinessNo09() == null || this.get(i).getBusinessNo09().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getBusinessNo09().trim());
			}
			if(this.get(i).getBusinessNo10() == null || this.get(i).getBusinessNo10().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getBusinessNo10().trim());
			}
			if(this.get(i).getTypeFlag01() == null || this.get(i).getTypeFlag01().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getTypeFlag01().trim());
			}
			if(this.get(i).getTypeFlag02() == null || this.get(i).getTypeFlag02().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getTypeFlag02().trim());
			}
			if(this.get(i).getTypeFlag03() == null || this.get(i).getTypeFlag03().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getTypeFlag03().trim());
			}
			if(this.get(i).getTypeFlag04() == null || this.get(i).getTypeFlag04().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getTypeFlag04().trim());
			}
			if(this.get(i).getTypeFlag05() == null || this.get(i).getTypeFlag05().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getTypeFlag05().trim());
			}
			if(this.get(i).getTypeFlag06() == null || this.get(i).getTypeFlag06().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getTypeFlag06().trim());
			}
			if(this.get(i).getTypeFlag07() == null || this.get(i).getTypeFlag07().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getTypeFlag07().trim());
			}
			if(this.get(i).getTypeFlag08() == null || this.get(i).getTypeFlag08().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getTypeFlag08().trim());
			}
			if(this.get(i).getTypeFlag09() == null || this.get(i).getTypeFlag09().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getTypeFlag09().trim());
			}
			if(this.get(i).getTypeFlag10() == null || this.get(i).getTypeFlag10().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getTypeFlag10().trim());
			}
			if(this.get(i).getRelationcode() == null || this.get(i).getRelationcode().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getRelationcode().trim());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(68,null);
			} else {
				pstmt.setDate(68, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getMakeTime().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataBDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIAboriginalDataB");
		sqlObj.setSQL(1, this.get(i));
		sqlObj.getSQL();
            }

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
