/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LDUWAddFeeSchema;
import com.sinosoft.lis.vschema.LDUWAddFeeSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDUWAddFeeDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LDUWAddFeeDBSet extends LDUWAddFeeSet
{
private static Logger logger = Logger.getLogger(LDUWAddFeeDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LDUWAddFeeDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LDUWAddFee");
		mflag = true;
	}

	public LDUWAddFeeDBSet()
	{
		db = new DBOper( "LDUWAddFee" );
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
			tError.moduleName = "LDUWAddFeeDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LDUWAddFee WHERE  ICDCode = ? AND DiseasDegree = ? AND Sex = ? AND Age = ? AND RiskKind = ? AND OPSFlag = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getICDCode() == null || this.get(i).getICDCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getICDCode());
			}
			if(this.get(i).getDiseasDegree() == null || this.get(i).getDiseasDegree().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDiseasDegree());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, StrTool.space1(this.get(i).getSex(), 1));
			}
			pstmt.setInt(4, this.get(i).getAge());
			if(this.get(i).getRiskKind() == null || this.get(i).getRiskKind().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRiskKind());
			}
			if(this.get(i).getOPSFlag() == null || this.get(i).getOPSFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, StrTool.space1(this.get(i).getOPSFlag(), 1));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDUWAddFee");
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
			tError.moduleName = "LDUWAddFeeDBSet";
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
			pstmt = con.prepareStatement("UPDATE LDUWAddFee SET  ICDCode = ? , ICDName = ? , DiseasDegree = ? , Sex = ? , Age = ? , RiskKind = ? , OPSFlag = ? , UWResult = ? , OPSNo = ? , DiseasBTypeCode = ? , DiseasType = ? , DiseasSTypeCode = ? , DiseasSType = ? , RiskGrade = ? , OPSName = ? , Relapse = ? , DisText = ? , GestationRela = ? , State = ? , ObservedTime = ? , JudgeStandard = ? , Year = ? WHERE  ICDCode = ? AND DiseasDegree = ? AND Sex = ? AND Age = ? AND RiskKind = ? AND OPSFlag = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getICDCode() == null || this.get(i).getICDCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getICDCode());
			}
			if(this.get(i).getICDName() == null || this.get(i).getICDName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getICDName());
			}
			if(this.get(i).getDiseasDegree() == null || this.get(i).getDiseasDegree().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDiseasDegree());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSex());
			}
			pstmt.setInt(5, this.get(i).getAge());
			if(this.get(i).getRiskKind() == null || this.get(i).getRiskKind().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskKind());
			}
			if(this.get(i).getOPSFlag() == null || this.get(i).getOPSFlag().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOPSFlag());
			}
			if(this.get(i).getUWResult() == null || this.get(i).getUWResult().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getUWResult());
			}
			if(this.get(i).getOPSNo() == null || this.get(i).getOPSNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getOPSNo());
			}
			if(this.get(i).getDiseasBTypeCode() == null || this.get(i).getDiseasBTypeCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getDiseasBTypeCode());
			}
			if(this.get(i).getDiseasType() == null || this.get(i).getDiseasType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getDiseasType());
			}
			if(this.get(i).getDiseasSTypeCode() == null || this.get(i).getDiseasSTypeCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getDiseasSTypeCode());
			}
			if(this.get(i).getDiseasSType() == null || this.get(i).getDiseasSType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getDiseasSType());
			}
			if(this.get(i).getRiskGrade() == null || this.get(i).getRiskGrade().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getRiskGrade());
			}
			if(this.get(i).getOPSName() == null || this.get(i).getOPSName().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getOPSName());
			}
			if(this.get(i).getRelapse() == null || this.get(i).getRelapse().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getRelapse());
			}
			if(this.get(i).getDisText() == null || this.get(i).getDisText().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getDisText());
			}
			if(this.get(i).getGestationRela() == null || this.get(i).getGestationRela().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getGestationRela());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getState());
			}
			if(this.get(i).getObservedTime() == null || this.get(i).getObservedTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getObservedTime());
			}
			if(this.get(i).getJudgeStandard() == null || this.get(i).getJudgeStandard().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getJudgeStandard());
			}
			if(this.get(i).getYear() == null || this.get(i).getYear().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getYear());
			}
			// set where condition
			if(this.get(i).getICDCode() == null || this.get(i).getICDCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getICDCode());
			}
			if(this.get(i).getDiseasDegree() == null || this.get(i).getDiseasDegree().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getDiseasDegree());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, StrTool.space1(this.get(i).getSex(), 1));
			}
			pstmt.setInt(26, this.get(i).getAge());
			if(this.get(i).getRiskKind() == null || this.get(i).getRiskKind().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getRiskKind());
			}
			if(this.get(i).getOPSFlag() == null || this.get(i).getOPSFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, StrTool.space1(this.get(i).getOPSFlag(), 1));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDUWAddFee");
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
			tError.moduleName = "LDUWAddFeeDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LDUWAddFee(ICDCode ,ICDName ,DiseasDegree ,Sex ,Age ,RiskKind ,OPSFlag ,UWResult ,OPSNo ,DiseasBTypeCode ,DiseasType ,DiseasSTypeCode ,DiseasSType ,RiskGrade ,OPSName ,Relapse ,DisText ,GestationRela ,State ,ObservedTime ,JudgeStandard ,Year) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getICDCode() == null || this.get(i).getICDCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getICDCode());
			}
			if(this.get(i).getICDName() == null || this.get(i).getICDName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getICDName());
			}
			if(this.get(i).getDiseasDegree() == null || this.get(i).getDiseasDegree().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDiseasDegree());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSex());
			}
			pstmt.setInt(5, this.get(i).getAge());
			if(this.get(i).getRiskKind() == null || this.get(i).getRiskKind().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskKind());
			}
			if(this.get(i).getOPSFlag() == null || this.get(i).getOPSFlag().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOPSFlag());
			}
			if(this.get(i).getUWResult() == null || this.get(i).getUWResult().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getUWResult());
			}
			if(this.get(i).getOPSNo() == null || this.get(i).getOPSNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getOPSNo());
			}
			if(this.get(i).getDiseasBTypeCode() == null || this.get(i).getDiseasBTypeCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getDiseasBTypeCode());
			}
			if(this.get(i).getDiseasType() == null || this.get(i).getDiseasType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getDiseasType());
			}
			if(this.get(i).getDiseasSTypeCode() == null || this.get(i).getDiseasSTypeCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getDiseasSTypeCode());
			}
			if(this.get(i).getDiseasSType() == null || this.get(i).getDiseasSType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getDiseasSType());
			}
			if(this.get(i).getRiskGrade() == null || this.get(i).getRiskGrade().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getRiskGrade());
			}
			if(this.get(i).getOPSName() == null || this.get(i).getOPSName().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getOPSName());
			}
			if(this.get(i).getRelapse() == null || this.get(i).getRelapse().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getRelapse());
			}
			if(this.get(i).getDisText() == null || this.get(i).getDisText().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getDisText());
			}
			if(this.get(i).getGestationRela() == null || this.get(i).getGestationRela().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getGestationRela());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getState());
			}
			if(this.get(i).getObservedTime() == null || this.get(i).getObservedTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getObservedTime());
			}
			if(this.get(i).getJudgeStandard() == null || this.get(i).getJudgeStandard().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getJudgeStandard());
			}
			if(this.get(i).getYear() == null || this.get(i).getYear().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getYear());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDUWAddFee");
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
			tError.moduleName = "LDUWAddFeeDBSet";
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
