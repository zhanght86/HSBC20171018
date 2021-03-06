/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LDHospitalBSchema;
import com.sinosoft.lis.vschema.LDHospitalBSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDHospitalBDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LDHospitalBDBSet extends LDHospitalBSet
{
private static Logger logger = Logger.getLogger(LDHospitalBDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LDHospitalBDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LDHospitalB");
		mflag = true;
	}

	public LDHospitalBDBSet()
	{
		db = new DBOper( "LDHospitalB" );
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
			tError.moduleName = "LDHospitalBDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LDHospitalB WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDHospitalB");
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
			tError.moduleName = "LDHospitalBDBSet";
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
			pstmt = con.prepareStatement("UPDATE LDHospitalB SET  SerialNo = ? , HospitCode = ? , MngCom = ? , HospitalName = ? , HospitShortName = ? , FixFlag = ? , Address = ? , ZipCode = ? , Phone = ? , WebAddress = ? , Fax = ? , HospitLicencNo = ? , bankCode = ? , AccName = ? , bankAccNo = ? , HosState = ? , Remark = ? , LinkMan = ? , ValidityDate = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BlackListFlag = ? , ReasonType = ? , HospitalGrade = ? , ModifyDate2 = ? , ModifyTime2 = ? , Operator2 = ? WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			if(this.get(i).getHospitCode() == null || this.get(i).getHospitCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getHospitCode());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMngCom());
			}
			if(this.get(i).getHospitalName() == null || this.get(i).getHospitalName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getHospitalName());
			}
			if(this.get(i).getHospitShortName() == null || this.get(i).getHospitShortName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getHospitShortName());
			}
			if(this.get(i).getFixFlag() == null || this.get(i).getFixFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFixFlag());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getZipCode());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getPhone());
			}
			if(this.get(i).getWebAddress() == null || this.get(i).getWebAddress().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getWebAddress());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getFax());
			}
			if(this.get(i).getHospitLicencNo() == null || this.get(i).getHospitLicencNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getHospitLicencNo());
			}
			if(this.get(i).getbankCode() == null || this.get(i).getbankCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getbankCode());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAccName());
			}
			if(this.get(i).getbankAccNo() == null || this.get(i).getbankAccNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getbankAccNo());
			}
			if(this.get(i).getHosState() == null || this.get(i).getHosState().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getHosState());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRemark());
			}
			if(this.get(i).getLinkMan() == null || this.get(i).getLinkMan().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getLinkMan());
			}
			if(this.get(i).getValidityDate() == null || this.get(i).getValidityDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getValidityDate()));
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getModifyTime());
			}
			if(this.get(i).getBlackListFlag() == null || this.get(i).getBlackListFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getBlackListFlag());
			}
			if(this.get(i).getReasonType() == null || this.get(i).getReasonType().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getReasonType());
			}
			if(this.get(i).getHospitalGrade() == null || this.get(i).getHospitalGrade().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getHospitalGrade());
			}
			if(this.get(i).getModifyDate2() == null || this.get(i).getModifyDate2().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getModifyDate2()));
			}
			if(this.get(i).getModifyTime2() == null || this.get(i).getModifyTime2().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getModifyTime2());
			}
			if(this.get(i).getOperator2() == null || this.get(i).getOperator2().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getOperator2());
			}
			// set where condition
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDHospitalB");
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
			tError.moduleName = "LDHospitalBDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LDHospitalB(SerialNo ,HospitCode ,MngCom ,HospitalName ,HospitShortName ,FixFlag ,Address ,ZipCode ,Phone ,WebAddress ,Fax ,HospitLicencNo ,bankCode ,AccName ,bankAccNo ,HosState ,Remark ,LinkMan ,ValidityDate ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BlackListFlag ,ReasonType ,HospitalGrade ,ModifyDate2 ,ModifyTime2 ,Operator2) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			if(this.get(i).getHospitCode() == null || this.get(i).getHospitCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getHospitCode());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMngCom());
			}
			if(this.get(i).getHospitalName() == null || this.get(i).getHospitalName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getHospitalName());
			}
			if(this.get(i).getHospitShortName() == null || this.get(i).getHospitShortName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getHospitShortName());
			}
			if(this.get(i).getFixFlag() == null || this.get(i).getFixFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFixFlag());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getZipCode());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getPhone());
			}
			if(this.get(i).getWebAddress() == null || this.get(i).getWebAddress().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getWebAddress());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getFax());
			}
			if(this.get(i).getHospitLicencNo() == null || this.get(i).getHospitLicencNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getHospitLicencNo());
			}
			if(this.get(i).getbankCode() == null || this.get(i).getbankCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getbankCode());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAccName());
			}
			if(this.get(i).getbankAccNo() == null || this.get(i).getbankAccNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getbankAccNo());
			}
			if(this.get(i).getHosState() == null || this.get(i).getHosState().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getHosState());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRemark());
			}
			if(this.get(i).getLinkMan() == null || this.get(i).getLinkMan().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getLinkMan());
			}
			if(this.get(i).getValidityDate() == null || this.get(i).getValidityDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getValidityDate()));
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getModifyTime());
			}
			if(this.get(i).getBlackListFlag() == null || this.get(i).getBlackListFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getBlackListFlag());
			}
			if(this.get(i).getReasonType() == null || this.get(i).getReasonType().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getReasonType());
			}
			if(this.get(i).getHospitalGrade() == null || this.get(i).getHospitalGrade().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getHospitalGrade());
			}
			if(this.get(i).getModifyDate2() == null || this.get(i).getModifyDate2().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getModifyDate2()));
			}
			if(this.get(i).getModifyTime2() == null || this.get(i).getModifyTime2().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getModifyTime2());
			}
			if(this.get(i).getOperator2() == null || this.get(i).getOperator2().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getOperator2());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDHospitalB");
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
			tError.moduleName = "LDHospitalBDBSet";
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
