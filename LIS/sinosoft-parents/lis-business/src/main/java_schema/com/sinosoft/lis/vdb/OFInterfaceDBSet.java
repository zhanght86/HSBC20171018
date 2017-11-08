/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.OFInterfaceSchema;
import com.sinosoft.lis.vschema.OFInterfaceSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: OFInterfaceDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口
 */
public class OFInterfaceDBSet extends OFInterfaceSet
{
private static Logger logger = Logger.getLogger(OFInterfaceDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public OFInterfaceDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"OFInterface");
		mflag = true;
	}

	public OFInterfaceDBSet()
	{
		db = new DBOper( "OFInterface" );
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
			tError.moduleName = "OFInterfaceDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM OFInterface WHERE  RecordID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getRecordID());

		// only for debug purpose
		SQLString sqlObj = new SQLString("OFInterface");
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
			tError.moduleName = "OFInterfaceDBSet";
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
			pstmt = con.prepareStatement("UPDATE OFInterface SET  RecordID = ? , ReversedStatus = ? , OrigRowID = ? , ReversedRowID = ? , CurrencyCode = ? , VoucherType = ? , ManageCom = ? , Segment2 = ? , AccountCode = ? , AccountSubCode = ? , SaleChnl = ? , RiskCode = ? , Segment7 = ? , Segment8 = ? , TransDate = ? , AccountingDate = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , MatchID = ? , BatchNo = ? , EnteredDR = ? , EnteredCR = ? , HeadDescription = ? , LineDescription = ? , CurrencyConversionDate = ? , CurrencyConversionRate = ? , AccountedDR = ? , AccountedCR = ? , Attribute1 = ? , PolNo = ? , InsuredName = ? , BussNo = ? , Attribute5 = ? , Attribute6 = ? , Attribute7 = ? , Attribute8 = ? , VoucherID = ? , VoucherFlag = ? , VoucherDate = ? , Attribute9 = ? WHERE  RecordID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getRecordID());
			if(this.get(i).getReversedStatus() == null || this.get(i).getReversedStatus().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getReversedStatus());
			}
			pstmt.setInt(3, this.get(i).getOrigRowID());
			pstmt.setInt(4, this.get(i).getReversedRowID());
			if(this.get(i).getCurrencyCode() == null || this.get(i).getCurrencyCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCurrencyCode());
			}
			if(this.get(i).getVoucherType() == null || this.get(i).getVoucherType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getVoucherType());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getManageCom());
			}
			if(this.get(i).getSegment2() == null || this.get(i).getSegment2().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSegment2());
			}
			if(this.get(i).getAccountCode() == null || this.get(i).getAccountCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAccountCode());
			}
			if(this.get(i).getAccountSubCode() == null || this.get(i).getAccountSubCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAccountSubCode());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSaleChnl());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRiskCode());
			}
			if(this.get(i).getSegment7() == null || this.get(i).getSegment7().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getSegment7());
			}
			if(this.get(i).getSegment8() == null || this.get(i).getSegment8().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getSegment8());
			}
			if(this.get(i).getTransDate() == null || this.get(i).getTransDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getTransDate()));
			}
			if(this.get(i).getAccountingDate() == null || this.get(i).getAccountingDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getAccountingDate()));
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			pstmt.setInt(21, this.get(i).getMatchID());
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getBatchNo());
			}
			pstmt.setDouble(23, this.get(i).getEnteredDR());
			pstmt.setDouble(24, this.get(i).getEnteredCR());
			if(this.get(i).getHeadDescription() == null || this.get(i).getHeadDescription().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getHeadDescription());
			}
			if(this.get(i).getLineDescription() == null || this.get(i).getLineDescription().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getLineDescription());
			}
			if(this.get(i).getCurrencyConversionDate() == null || this.get(i).getCurrencyConversionDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getCurrencyConversionDate()));
			}
			pstmt.setInt(28, this.get(i).getCurrencyConversionRate());
			pstmt.setDouble(29, this.get(i).getAccountedDR());
			pstmt.setDouble(30, this.get(i).getAccountedCR());
			if(this.get(i).getAttribute1() == null || this.get(i).getAttribute1().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getAttribute1());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPolNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getInsuredName());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getBussNo());
			}
			if(this.get(i).getAttribute5() == null || this.get(i).getAttribute5().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getAttribute5());
			}
			if(this.get(i).getAttribute6() == null || this.get(i).getAttribute6().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getAttribute6());
			}
			if(this.get(i).getAttribute7() == null || this.get(i).getAttribute7().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getAttribute7());
			}
			if(this.get(i).getAttribute8() == null || this.get(i).getAttribute8().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getAttribute8());
			}
			if(this.get(i).getVoucherID() == null || this.get(i).getVoucherID().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getVoucherID());
			}
			if(this.get(i).getVoucherFlag() == null || this.get(i).getVoucherFlag().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getVoucherFlag());
			}
			if(this.get(i).getVoucherDate() == null || this.get(i).getVoucherDate().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getVoucherDate());
			}
			if(this.get(i).getAttribute9() == null || this.get(i).getAttribute9().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getAttribute9());
			}
			// set where condition
			pstmt.setInt(43, this.get(i).getRecordID());

		// only for debug purpose
		SQLString sqlObj = new SQLString("OFInterface");
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
			tError.moduleName = "OFInterfaceDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO OFInterface(RecordID ,ReversedStatus ,OrigRowID ,ReversedRowID ,CurrencyCode ,VoucherType ,ManageCom ,Segment2 ,AccountCode ,AccountSubCode ,SaleChnl ,RiskCode ,Segment7 ,Segment8 ,TransDate ,AccountingDate ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,MatchID ,BatchNo ,EnteredDR ,EnteredCR ,HeadDescription ,LineDescription ,CurrencyConversionDate ,CurrencyConversionRate ,AccountedDR ,AccountedCR ,Attribute1 ,PolNo ,InsuredName ,BussNo ,Attribute5 ,Attribute6 ,Attribute7 ,Attribute8 ,VoucherID ,VoucherFlag ,VoucherDate ,Attribute9) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getRecordID());
			if(this.get(i).getReversedStatus() == null || this.get(i).getReversedStatus().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getReversedStatus());
			}
			pstmt.setInt(3, this.get(i).getOrigRowID());
			pstmt.setInt(4, this.get(i).getReversedRowID());
			if(this.get(i).getCurrencyCode() == null || this.get(i).getCurrencyCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCurrencyCode());
			}
			if(this.get(i).getVoucherType() == null || this.get(i).getVoucherType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getVoucherType());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getManageCom());
			}
			if(this.get(i).getSegment2() == null || this.get(i).getSegment2().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSegment2());
			}
			if(this.get(i).getAccountCode() == null || this.get(i).getAccountCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAccountCode());
			}
			if(this.get(i).getAccountSubCode() == null || this.get(i).getAccountSubCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAccountSubCode());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSaleChnl());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRiskCode());
			}
			if(this.get(i).getSegment7() == null || this.get(i).getSegment7().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getSegment7());
			}
			if(this.get(i).getSegment8() == null || this.get(i).getSegment8().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getSegment8());
			}
			if(this.get(i).getTransDate() == null || this.get(i).getTransDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getTransDate()));
			}
			if(this.get(i).getAccountingDate() == null || this.get(i).getAccountingDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getAccountingDate()));
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			pstmt.setInt(21, this.get(i).getMatchID());
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getBatchNo());
			}
			pstmt.setDouble(23, this.get(i).getEnteredDR());
			pstmt.setDouble(24, this.get(i).getEnteredCR());
			if(this.get(i).getHeadDescription() == null || this.get(i).getHeadDescription().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getHeadDescription());
			}
			if(this.get(i).getLineDescription() == null || this.get(i).getLineDescription().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getLineDescription());
			}
			if(this.get(i).getCurrencyConversionDate() == null || this.get(i).getCurrencyConversionDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getCurrencyConversionDate()));
			}
			pstmt.setInt(28, this.get(i).getCurrencyConversionRate());
			pstmt.setDouble(29, this.get(i).getAccountedDR());
			pstmt.setDouble(30, this.get(i).getAccountedCR());
			if(this.get(i).getAttribute1() == null || this.get(i).getAttribute1().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getAttribute1());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPolNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getInsuredName());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getBussNo());
			}
			if(this.get(i).getAttribute5() == null || this.get(i).getAttribute5().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getAttribute5());
			}
			if(this.get(i).getAttribute6() == null || this.get(i).getAttribute6().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getAttribute6());
			}
			if(this.get(i).getAttribute7() == null || this.get(i).getAttribute7().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getAttribute7());
			}
			if(this.get(i).getAttribute8() == null || this.get(i).getAttribute8().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getAttribute8());
			}
			if(this.get(i).getVoucherID() == null || this.get(i).getVoucherID().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getVoucherID());
			}
			if(this.get(i).getVoucherFlag() == null || this.get(i).getVoucherFlag().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getVoucherFlag());
			}
			if(this.get(i).getVoucherDate() == null || this.get(i).getVoucherDate().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getVoucherDate());
			}
			if(this.get(i).getAttribute9() == null || this.get(i).getAttribute9().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getAttribute9());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("OFInterface");
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
			tError.moduleName = "OFInterfaceDBSet";
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
