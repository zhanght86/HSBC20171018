/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.Es_IssueDocSchema;
import com.sinosoft.lis.vschema.Es_IssueDocSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: Es_IssueDocDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_12
 */
public class Es_IssueDocDBSet extends Es_IssueDocSet
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
	public Es_IssueDocDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"Es_IssueDoc");
		mflag = true;
	}

	public Es_IssueDocDBSet()
	{
		db = new DBOper( "Es_IssueDoc" );
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
			tError.moduleName = "Es_IssueDocDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM Es_IssueDoc WHERE  BussNo = ? AND BussNoType = ? AND SubType = ? AND IssueDocID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBussNo());
			}
			if(this.get(i).getBussNoType() == null || this.get(i).getBussNoType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBussNoType());
			}
			if(this.get(i).getSubType() == null || this.get(i).getSubType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSubType());
			}
			if(this.get(i).getIssueDocID() == null || this.get(i).getIssueDocID().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getIssueDocID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("Es_IssueDoc");
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
			tError.moduleName = "Es_IssueDocDBSet";
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
			pstmt = con.prepareStatement("UPDATE Es_IssueDoc SET  BussNo = ? , BussNoType = ? , BussType = ? , SubType = ? , IssueDesc = ? , Status = ? , Result = ? , ReplyOperator = ? , PromptOperator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , IssueDocID = ? WHERE  BussNo = ? AND BussNoType = ? AND SubType = ? AND IssueDocID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBussNo());
			}
			if(this.get(i).getBussNoType() == null || this.get(i).getBussNoType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBussNoType());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBussType());
			}
			if(this.get(i).getSubType() == null || this.get(i).getSubType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSubType());
			}
			if(this.get(i).getIssueDesc() == null || this.get(i).getIssueDesc().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getIssueDesc());
			}
			if(this.get(i).getStatus() == null || this.get(i).getStatus().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getStatus());
			}
			if(this.get(i).getResult() == null || this.get(i).getResult().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getResult());
			}
			if(this.get(i).getReplyOperator() == null || this.get(i).getReplyOperator().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getReplyOperator());
			}
			if(this.get(i).getPromptOperator() == null || this.get(i).getPromptOperator().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getPromptOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getModifyTime());
			}
			if(this.get(i).getIssueDocID() == null || this.get(i).getIssueDocID().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getIssueDocID());
			}
			// set where condition
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBussNo());
			}
			if(this.get(i).getBussNoType() == null || this.get(i).getBussNoType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBussNoType());
			}
			if(this.get(i).getSubType() == null || this.get(i).getSubType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSubType());
			}
			if(this.get(i).getIssueDocID() == null || this.get(i).getIssueDocID().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getIssueDocID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("Es_IssueDoc");
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
			tError.moduleName = "Es_IssueDocDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO Es_IssueDoc VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBussNo());
			}
			if(this.get(i).getBussNoType() == null || this.get(i).getBussNoType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBussNoType());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBussType());
			}
			if(this.get(i).getSubType() == null || this.get(i).getSubType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSubType());
			}
			if(this.get(i).getIssueDesc() == null || this.get(i).getIssueDesc().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getIssueDesc());
			}
			if(this.get(i).getStatus() == null || this.get(i).getStatus().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getStatus());
			}
			if(this.get(i).getResult() == null || this.get(i).getResult().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getResult());
			}
			if(this.get(i).getReplyOperator() == null || this.get(i).getReplyOperator().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getReplyOperator());
			}
			if(this.get(i).getPromptOperator() == null || this.get(i).getPromptOperator().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getPromptOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getModifyTime());
			}
			if(this.get(i).getIssueDocID() == null || this.get(i).getIssueDocID().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getIssueDocID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("Es_IssueDoc");
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
			tError.moduleName = "Es_IssueDocDBSet";
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
