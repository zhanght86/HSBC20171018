/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LCMedBuyDetailSchema;
import com.sinosoft.lis.vschema.LCMedBuyDetailSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCMedBuyDetailDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LCMedBuyDetailDBSet extends LCMedBuyDetailSet
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
	public LCMedBuyDetailDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCMedBuyDetail");
		mflag = true;
	}

	public LCMedBuyDetailDBSet()
	{
		db = new DBOper( "LCMedBuyDetail" );
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
			tError.moduleName = "LCMedBuyDetailDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCMedBuyDetail WHERE  BatchNo = ? AND SeqNo = ? AND MedCardNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBatchNo());
			}
			if(this.get(i).getSeqNo() == null || this.get(i).getSeqNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSeqNo());
			}
			if(this.get(i).getMedCardNo() == null || this.get(i).getMedCardNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMedCardNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCMedBuyDetail");
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
			tError.moduleName = "LCMedBuyDetailDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCMedBuyDetail SET  BatchNo = ? , SeqNo = ? , MedCardNo = ? , MedName = ? , MedType1 = ? , MedType2 = ? , MedType3 = ? , Quanity = ? , UnitPrice = ? , SumPrice = ? , BXFlag = ? , MakeDate = ? , MakeTime = ? , MedShopCode = ? , ConterNo = ? , Memo = ? , StandByFlag1 = ? , StandByFlag2 = ? WHERE  BatchNo = ? AND SeqNo = ? AND MedCardNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBatchNo());
			}
			if(this.get(i).getSeqNo() == null || this.get(i).getSeqNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSeqNo());
			}
			if(this.get(i).getMedCardNo() == null || this.get(i).getMedCardNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMedCardNo());
			}
			if(this.get(i).getMedName() == null || this.get(i).getMedName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMedName());
			}
			if(this.get(i).getMedType1() == null || this.get(i).getMedType1().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getMedType1());
			}
			if(this.get(i).getMedType2() == null || this.get(i).getMedType2().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getMedType2());
			}
			if(this.get(i).getMedType3() == null || this.get(i).getMedType3().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getMedType3());
			}
			pstmt.setDouble(8, this.get(i).getQuanity());
			pstmt.setDouble(9, this.get(i).getUnitPrice());
			pstmt.setDouble(10, this.get(i).getSumPrice());
			if(this.get(i).getBXFlag() == null || this.get(i).getBXFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBXFlag());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getMakeTime());
			}
			if(this.get(i).getMedShopCode() == null || this.get(i).getMedShopCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getMedShopCode());
			}
			if(this.get(i).getConterNo() == null || this.get(i).getConterNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getConterNo());
			}
			if(this.get(i).getMemo() == null || this.get(i).getMemo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getMemo());
			}
			if(this.get(i).getStandByFlag1() == null || this.get(i).getStandByFlag1().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStandByFlag1());
			}
			if(this.get(i).getStandByFlag2() == null || this.get(i).getStandByFlag2().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStandByFlag2());
			}
			// set where condition
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getBatchNo());
			}
			if(this.get(i).getSeqNo() == null || this.get(i).getSeqNo().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getSeqNo());
			}
			if(this.get(i).getMedCardNo() == null || this.get(i).getMedCardNo().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMedCardNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCMedBuyDetail");
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
			tError.moduleName = "LCMedBuyDetailDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCMedBuyDetail VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBatchNo());
			}
			if(this.get(i).getSeqNo() == null || this.get(i).getSeqNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSeqNo());
			}
			if(this.get(i).getMedCardNo() == null || this.get(i).getMedCardNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMedCardNo());
			}
			if(this.get(i).getMedName() == null || this.get(i).getMedName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMedName());
			}
			if(this.get(i).getMedType1() == null || this.get(i).getMedType1().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getMedType1());
			}
			if(this.get(i).getMedType2() == null || this.get(i).getMedType2().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getMedType2());
			}
			if(this.get(i).getMedType3() == null || this.get(i).getMedType3().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getMedType3());
			}
			pstmt.setDouble(8, this.get(i).getQuanity());
			pstmt.setDouble(9, this.get(i).getUnitPrice());
			pstmt.setDouble(10, this.get(i).getSumPrice());
			if(this.get(i).getBXFlag() == null || this.get(i).getBXFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBXFlag());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getMakeTime());
			}
			if(this.get(i).getMedShopCode() == null || this.get(i).getMedShopCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getMedShopCode());
			}
			if(this.get(i).getConterNo() == null || this.get(i).getConterNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getConterNo());
			}
			if(this.get(i).getMemo() == null || this.get(i).getMemo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getMemo());
			}
			if(this.get(i).getStandByFlag1() == null || this.get(i).getStandByFlag1().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStandByFlag1());
			}
			if(this.get(i).getStandByFlag2() == null || this.get(i).getStandByFlag2().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStandByFlag2());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCMedBuyDetail");
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
			tError.moduleName = "LCMedBuyDetailDBSet";
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
