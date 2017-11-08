/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LMCertifyDesTraceSchema;
import com.sinosoft.lis.vschema.LMCertifyDesTraceSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMCertifyDesTraceDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LMCertifyDesTraceDBSet extends LMCertifyDesTraceSet
{
private static Logger logger = Logger.getLogger(LMCertifyDesTraceDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LMCertifyDesTraceDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMCertifyDesTrace");
		mflag = true;
	}

	public LMCertifyDesTraceDBSet()
	{
		db = new DBOper( "LMCertifyDesTrace" );
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
			tError.moduleName = "LMCertifyDesTraceDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMCertifyDesTrace WHERE  CertifyCode = ? AND MakeDate = ? AND MakeTime = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCertifyCode());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(2,null);
			} else {
				pstmt.setDate(2, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, StrTool.space1(this.get(i).getMakeTime(), 8));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMCertifyDesTrace");
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
			tError.moduleName = "LMCertifyDesTraceDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMCertifyDesTrace SET  CertifyCode = ? , CertifyName = ? , SubCode = ? , RiskCode = ? , RiskVersion = ? , CertifyClass = ? , HavePrice = ? , CertifyClass2 = ? , ImportantLevel = ? , HaveNumber = ? , CertifyLength = ? , TackBackFlag = ? , CertifyPrice = ? , Unit = ? , MaxPrintNo = ? , SplitOnSend = ? , HaveLimit = ? , MaxUnit1 = ? , MaxUnit2 = ? , HaveValidate = ? , Validate1 = ? , Validate2 = ? , State = ? , Note = ? , ManageCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , JugAgtFlag = ? WHERE  CertifyCode = ? AND MakeDate = ? AND MakeTime = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCertifyCode());
			}
			if(this.get(i).getCertifyName() == null || this.get(i).getCertifyName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCertifyName());
			}
			if(this.get(i).getSubCode() == null || this.get(i).getSubCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSubCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRiskVersion());
			}
			if(this.get(i).getCertifyClass() == null || this.get(i).getCertifyClass().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCertifyClass());
			}
			if(this.get(i).getHavePrice() == null || this.get(i).getHavePrice().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getHavePrice());
			}
			if(this.get(i).getCertifyClass2() == null || this.get(i).getCertifyClass2().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCertifyClass2());
			}
			if(this.get(i).getImportantLevel() == null || this.get(i).getImportantLevel().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getImportantLevel());
			}
			if(this.get(i).getHaveNumber() == null || this.get(i).getHaveNumber().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getHaveNumber());
			}
			pstmt.setInt(11, this.get(i).getCertifyLength());
			if(this.get(i).getTackBackFlag() == null || this.get(i).getTackBackFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getTackBackFlag());
			}
			pstmt.setDouble(13, this.get(i).getCertifyPrice());
			if(this.get(i).getUnit() == null || this.get(i).getUnit().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getUnit());
			}
			if(this.get(i).getMaxPrintNo() == null || this.get(i).getMaxPrintNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMaxPrintNo());
			}
			if(this.get(i).getSplitOnSend() == null || this.get(i).getSplitOnSend().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getSplitOnSend());
			}
			if(this.get(i).getHaveLimit() == null || this.get(i).getHaveLimit().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getHaveLimit());
			}
			pstmt.setInt(18, this.get(i).getMaxUnit1());
			pstmt.setInt(19, this.get(i).getMaxUnit2());
			if(this.get(i).getHaveValidate() == null || this.get(i).getHaveValidate().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getHaveValidate());
			}
			pstmt.setInt(21, this.get(i).getValidate1());
			pstmt.setInt(22, this.get(i).getValidate2());
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getState());
			}
			if(this.get(i).getNote() == null || this.get(i).getNote().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getNote());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getManageCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getMakeTime());
			}
			if(this.get(i).getJugAgtFlag() == null || this.get(i).getJugAgtFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getJugAgtFlag());
			}
			// set where condition
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getCertifyCode());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, StrTool.space1(this.get(i).getMakeTime(), 8));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMCertifyDesTrace");
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
			tError.moduleName = "LMCertifyDesTraceDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMCertifyDesTrace(CertifyCode ,CertifyName ,SubCode ,RiskCode ,RiskVersion ,CertifyClass ,HavePrice ,CertifyClass2 ,ImportantLevel ,HaveNumber ,CertifyLength ,TackBackFlag ,CertifyPrice ,Unit ,MaxPrintNo ,SplitOnSend ,HaveLimit ,MaxUnit1 ,MaxUnit2 ,HaveValidate ,Validate1 ,Validate2 ,State ,Note ,ManageCom ,Operator ,MakeDate ,MakeTime ,JugAgtFlag) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCertifyCode());
			}
			if(this.get(i).getCertifyName() == null || this.get(i).getCertifyName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCertifyName());
			}
			if(this.get(i).getSubCode() == null || this.get(i).getSubCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSubCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRiskVersion());
			}
			if(this.get(i).getCertifyClass() == null || this.get(i).getCertifyClass().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCertifyClass());
			}
			if(this.get(i).getHavePrice() == null || this.get(i).getHavePrice().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getHavePrice());
			}
			if(this.get(i).getCertifyClass2() == null || this.get(i).getCertifyClass2().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCertifyClass2());
			}
			if(this.get(i).getImportantLevel() == null || this.get(i).getImportantLevel().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getImportantLevel());
			}
			if(this.get(i).getHaveNumber() == null || this.get(i).getHaveNumber().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getHaveNumber());
			}
			pstmt.setInt(11, this.get(i).getCertifyLength());
			if(this.get(i).getTackBackFlag() == null || this.get(i).getTackBackFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getTackBackFlag());
			}
			pstmt.setDouble(13, this.get(i).getCertifyPrice());
			if(this.get(i).getUnit() == null || this.get(i).getUnit().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getUnit());
			}
			if(this.get(i).getMaxPrintNo() == null || this.get(i).getMaxPrintNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMaxPrintNo());
			}
			if(this.get(i).getSplitOnSend() == null || this.get(i).getSplitOnSend().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getSplitOnSend());
			}
			if(this.get(i).getHaveLimit() == null || this.get(i).getHaveLimit().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getHaveLimit());
			}
			pstmt.setInt(18, this.get(i).getMaxUnit1());
			pstmt.setInt(19, this.get(i).getMaxUnit2());
			if(this.get(i).getHaveValidate() == null || this.get(i).getHaveValidate().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getHaveValidate());
			}
			pstmt.setInt(21, this.get(i).getValidate1());
			pstmt.setInt(22, this.get(i).getValidate2());
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getState());
			}
			if(this.get(i).getNote() == null || this.get(i).getNote().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getNote());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getManageCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getMakeTime());
			}
			if(this.get(i).getJugAgtFlag() == null || this.get(i).getJugAgtFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getJugAgtFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMCertifyDesTrace");
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
			tError.moduleName = "LMCertifyDesTraceDBSet";
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
