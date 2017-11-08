/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LSQuotBasicSchema;
import com.sinosoft.lis.vschema.LSQuotBasicSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LSQuotBasicDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotBasicDBSet extends LSQuotBasicSet
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
	public LSQuotBasicDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LSQuotBasic");
		mflag = true;
	}

	public LSQuotBasicDBSet()
	{
		db = new DBOper( "LSQuotBasic" );
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
			tError.moduleName = "LSQuotBasicDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LSQuotBasic WHERE  QuotNo = ? AND QuotBatNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getQuotNo());
			}
			pstmt.setInt(2, this.get(i).getQuotBatNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotBasic");
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
			tError.moduleName = "LSQuotBasicDBSet";
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
			pstmt = con.prepareStatement("UPDATE LSQuotBasic SET  QuotNo = ? , QuotBatNo = ? , PreCustomerNo = ? , PreCustomerName = ? , IDType = ? , IDNo = ? , GrpNature = ? , BusiCategory = ? , Address = ? , CustomerIntro = ? , ProdType = ? , SaleChannel = ? , PremMode = ? , PrePrem = ? , RenewFlag = ? , BlanketFlag = ? , ElasticFlag = ? , PayIntv = ? , InsuPeriod = ? , InsuPeriodFlag = ? , ValDateType = ? , AppointValDate = ? , Coinsurance = ? , Segment1 = ? , Segment2 = ? , Segment3 = ? , QuotState = ? , FileReason = ? , FileDesc = ? , ManageCom = ? , ComCode = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  QuotNo = ? AND QuotBatNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getQuotNo());
			}
			pstmt.setInt(2, this.get(i).getQuotBatNo());
			if(this.get(i).getPreCustomerNo() == null || this.get(i).getPreCustomerNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPreCustomerNo());
			}
			if(this.get(i).getPreCustomerName() == null || this.get(i).getPreCustomerName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPreCustomerName());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getIDNo());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGrpNature());
			}
			if(this.get(i).getBusiCategory() == null || this.get(i).getBusiCategory().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getBusiCategory());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAddress());
			}
			if(this.get(i).getCustomerIntro() == null || this.get(i).getCustomerIntro().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCustomerIntro());
			}
			if(this.get(i).getProdType() == null || this.get(i).getProdType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getProdType());
			}
			if(this.get(i).getSaleChannel() == null || this.get(i).getSaleChannel().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSaleChannel());
			}
			if(this.get(i).getPremMode() == null || this.get(i).getPremMode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPremMode());
			}
			pstmt.setDouble(14, this.get(i).getPrePrem());
			if(this.get(i).getRenewFlag() == null || this.get(i).getRenewFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRenewFlag());
			}
			if(this.get(i).getBlanketFlag() == null || this.get(i).getBlanketFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBlanketFlag());
			}
			if(this.get(i).getElasticFlag() == null || this.get(i).getElasticFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getElasticFlag());
			}
			if(this.get(i).getPayIntv() == null || this.get(i).getPayIntv().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPayIntv());
			}
			pstmt.setInt(19, this.get(i).getInsuPeriod());
			if(this.get(i).getInsuPeriodFlag() == null || this.get(i).getInsuPeriodFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getInsuPeriodFlag());
			}
			if(this.get(i).getValDateType() == null || this.get(i).getValDateType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getValDateType());
			}
			if(this.get(i).getAppointValDate() == null || this.get(i).getAppointValDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getAppointValDate()));
			}
			if(this.get(i).getCoinsurance() == null || this.get(i).getCoinsurance().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getCoinsurance());
			}
			if(this.get(i).getSegment1() == null || this.get(i).getSegment1().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSegment1());
			}
			if(this.get(i).getSegment2() == null || this.get(i).getSegment2().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getSegment2());
			}
			if(this.get(i).getSegment3() == null || this.get(i).getSegment3().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getSegment3());
			}
			if(this.get(i).getQuotState() == null || this.get(i).getQuotState().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getQuotState());
			}
			if(this.get(i).getFileReason() == null || this.get(i).getFileReason().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getFileReason());
			}
			if(this.get(i).getFileDesc() == null || this.get(i).getFileDesc().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getFileDesc());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getManageCom());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getComCode());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getQuotNo());
			}
			pstmt.setInt(39, this.get(i).getQuotBatNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotBasic");
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
			tError.moduleName = "LSQuotBasicDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LSQuotBasic VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getQuotNo());
			}
			pstmt.setInt(2, this.get(i).getQuotBatNo());
			if(this.get(i).getPreCustomerNo() == null || this.get(i).getPreCustomerNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPreCustomerNo());
			}
			if(this.get(i).getPreCustomerName() == null || this.get(i).getPreCustomerName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPreCustomerName());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getIDNo());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGrpNature());
			}
			if(this.get(i).getBusiCategory() == null || this.get(i).getBusiCategory().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getBusiCategory());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAddress());
			}
			if(this.get(i).getCustomerIntro() == null || this.get(i).getCustomerIntro().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCustomerIntro());
			}
			if(this.get(i).getProdType() == null || this.get(i).getProdType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getProdType());
			}
			if(this.get(i).getSaleChannel() == null || this.get(i).getSaleChannel().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSaleChannel());
			}
			if(this.get(i).getPremMode() == null || this.get(i).getPremMode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPremMode());
			}
			pstmt.setDouble(14, this.get(i).getPrePrem());
			if(this.get(i).getRenewFlag() == null || this.get(i).getRenewFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRenewFlag());
			}
			if(this.get(i).getBlanketFlag() == null || this.get(i).getBlanketFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBlanketFlag());
			}
			if(this.get(i).getElasticFlag() == null || this.get(i).getElasticFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getElasticFlag());
			}
			if(this.get(i).getPayIntv() == null || this.get(i).getPayIntv().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPayIntv());
			}
			pstmt.setInt(19, this.get(i).getInsuPeriod());
			if(this.get(i).getInsuPeriodFlag() == null || this.get(i).getInsuPeriodFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getInsuPeriodFlag());
			}
			if(this.get(i).getValDateType() == null || this.get(i).getValDateType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getValDateType());
			}
			if(this.get(i).getAppointValDate() == null || this.get(i).getAppointValDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getAppointValDate()));
			}
			if(this.get(i).getCoinsurance() == null || this.get(i).getCoinsurance().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getCoinsurance());
			}
			if(this.get(i).getSegment1() == null || this.get(i).getSegment1().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSegment1());
			}
			if(this.get(i).getSegment2() == null || this.get(i).getSegment2().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getSegment2());
			}
			if(this.get(i).getSegment3() == null || this.get(i).getSegment3().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getSegment3());
			}
			if(this.get(i).getQuotState() == null || this.get(i).getQuotState().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getQuotState());
			}
			if(this.get(i).getFileReason() == null || this.get(i).getFileReason().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getFileReason());
			}
			if(this.get(i).getFileDesc() == null || this.get(i).getFileDesc().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getFileDesc());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getManageCom());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getComCode());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotBasic");
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
			tError.moduleName = "LSQuotBasicDBSet";
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
