/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LPCGrpSpecSchema;
import com.sinosoft.lis.vschema.LPCGrpSpecSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPCGrpSpecDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全流程
 */
public class LPCGrpSpecDBSet extends LPCGrpSpecSet
{
private static Logger logger = Logger.getLogger(LPCGrpSpecDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LPCGrpSpecDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LPCGrpSpec");
		mflag = true;
	}

	public LPCGrpSpecDBSet()
	{
		db = new DBOper( "LPCGrpSpec" );
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
			tError.moduleName = "LPCGrpSpecDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LPCGrpSpec WHERE  EdorNo = ? AND EdorType = ? AND ProposalGrpContNo = ? AND SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorType());
			}
			if(this.get(i).getProposalGrpContNo() == null || this.get(i).getProposalGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getProposalGrpContNo());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPCGrpSpec");
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
			tError.moduleName = "LPCGrpSpecDBSet";
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
			pstmt = con.prepareStatement("UPDATE LPCGrpSpec SET  EdorNo = ? , EdorType = ? , GrpContNo = ? , ProposalGrpContNo = ? , PrtSeq = ? , SerialNo = ? , EndorsementNo = ? , SpecType = ? , SpecCode = ? , SpecContent = ? , PrtFlag = ? , BackupType = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  EdorNo = ? AND EdorType = ? AND ProposalGrpContNo = ? AND SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorType());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getProposalGrpContNo() == null || this.get(i).getProposalGrpContNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getProposalGrpContNo());
			}
			if(this.get(i).getPrtSeq() == null || this.get(i).getPrtSeq().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPrtSeq());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getSerialNo());
			}
			if(this.get(i).getEndorsementNo() == null || this.get(i).getEndorsementNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getEndorsementNo());
			}
			if(this.get(i).getSpecType() == null || this.get(i).getSpecType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSpecType());
			}
			if(this.get(i).getSpecCode() == null || this.get(i).getSpecCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getSpecCode());
			}
			if(this.get(i).getSpecContent() == null || this.get(i).getSpecContent().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSpecContent());
			}
			if(this.get(i).getPrtFlag() == null || this.get(i).getPrtFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getPrtFlag());
			}
			if(this.get(i).getBackupType() == null || this.get(i).getBackupType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getBackupType());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getEdorType());
			}
			if(this.get(i).getProposalGrpContNo() == null || this.get(i).getProposalGrpContNo().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getProposalGrpContNo());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPCGrpSpec");
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
			tError.moduleName = "LPCGrpSpecDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LPCGrpSpec(EdorNo ,EdorType ,GrpContNo ,ProposalGrpContNo ,PrtSeq ,SerialNo ,EndorsementNo ,SpecType ,SpecCode ,SpecContent ,PrtFlag ,BackupType ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorType());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getProposalGrpContNo() == null || this.get(i).getProposalGrpContNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getProposalGrpContNo());
			}
			if(this.get(i).getPrtSeq() == null || this.get(i).getPrtSeq().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPrtSeq());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getSerialNo());
			}
			if(this.get(i).getEndorsementNo() == null || this.get(i).getEndorsementNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getEndorsementNo());
			}
			if(this.get(i).getSpecType() == null || this.get(i).getSpecType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSpecType());
			}
			if(this.get(i).getSpecCode() == null || this.get(i).getSpecCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getSpecCode());
			}
			if(this.get(i).getSpecContent() == null || this.get(i).getSpecContent().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSpecContent());
			}
			if(this.get(i).getPrtFlag() == null || this.get(i).getPrtFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getPrtFlag());
			}
			if(this.get(i).getBackupType() == null || this.get(i).getBackupType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getBackupType());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPCGrpSpec");
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
			tError.moduleName = "LPCGrpSpecDBSet";
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
