/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.FIBusinessRuleDefSchema;
import com.sinosoft.lis.vschema.FIBusinessRuleDefSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: FIBusinessRuleDefDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_2
 */
public class FIBusinessRuleDefDBSet extends FIBusinessRuleDefSet
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
	public FIBusinessRuleDefDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"FIBusinessRuleDef");
		mflag = true;
	}

	public FIBusinessRuleDefDBSet()
	{
		db = new DBOper( "FIBusinessRuleDef" );
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
			tError.moduleName = "FIBusinessRuleDefDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FIBusinessRuleDef WHERE  VersionNo = ? AND RuleID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getVersionNo() == null || this.get(i).getVersionNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getVersionNo());
			}
			if(this.get(i).getRuleID() == null || this.get(i).getRuleID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRuleID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("FIBusinessRuleDef");
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
			tError.moduleName = "FIBusinessRuleDefDBSet";
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
			pstmt = con.prepareStatement("UPDATE FIBusinessRuleDef SET  VersionNo = ? , RuleID = ? , RuleName = ? , RuleDealMode = ? , RuleDealClass = ? , RuleFileName = ? , RuleDealSQL = ? , RuleState = ? , RuleReturnDataDef = ? , LastNode = ? , NextNode = ? , CallPointID = ? , DataBaseID = ? WHERE  VersionNo = ? AND RuleID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getVersionNo() == null || this.get(i).getVersionNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getVersionNo());
			}
			if(this.get(i).getRuleID() == null || this.get(i).getRuleID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRuleID());
			}
			if(this.get(i).getRuleName() == null || this.get(i).getRuleName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRuleName());
			}
			if(this.get(i).getRuleDealMode() == null || this.get(i).getRuleDealMode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRuleDealMode());
			}
			if(this.get(i).getRuleDealClass() == null || this.get(i).getRuleDealClass().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRuleDealClass());
			}
			if(this.get(i).getRuleFileName() == null || this.get(i).getRuleFileName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRuleFileName());
			}
			if(this.get(i).getRuleDealSQL() == null || this.get(i).getRuleDealSQL().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRuleDealSQL());
			}
			if(this.get(i).getRuleState() == null || this.get(i).getRuleState().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRuleState());
			}
			if(this.get(i).getRuleReturnDataDef() == null || this.get(i).getRuleReturnDataDef().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRuleReturnDataDef());
			}
			if(this.get(i).getLastNode() == null || this.get(i).getLastNode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getLastNode());
			}
			if(this.get(i).getNextNode() == null || this.get(i).getNextNode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getNextNode());
			}
			if(this.get(i).getCallPointID() == null || this.get(i).getCallPointID().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCallPointID());
			}
			if(this.get(i).getDataBaseID() == null || this.get(i).getDataBaseID().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getDataBaseID());
			}
			// set where condition
			if(this.get(i).getVersionNo() == null || this.get(i).getVersionNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getVersionNo());
			}
			if(this.get(i).getRuleID() == null || this.get(i).getRuleID().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRuleID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("FIBusinessRuleDef");
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
			tError.moduleName = "FIBusinessRuleDefDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO FIBusinessRuleDef VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getVersionNo() == null || this.get(i).getVersionNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getVersionNo());
			}
			if(this.get(i).getRuleID() == null || this.get(i).getRuleID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRuleID());
			}
			if(this.get(i).getRuleName() == null || this.get(i).getRuleName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRuleName());
			}
			if(this.get(i).getRuleDealMode() == null || this.get(i).getRuleDealMode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRuleDealMode());
			}
			if(this.get(i).getRuleDealClass() == null || this.get(i).getRuleDealClass().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRuleDealClass());
			}
			if(this.get(i).getRuleFileName() == null || this.get(i).getRuleFileName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRuleFileName());
			}
			if(this.get(i).getRuleDealSQL() == null || this.get(i).getRuleDealSQL().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRuleDealSQL());
			}
			if(this.get(i).getRuleState() == null || this.get(i).getRuleState().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRuleState());
			}
			if(this.get(i).getRuleReturnDataDef() == null || this.get(i).getRuleReturnDataDef().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRuleReturnDataDef());
			}
			if(this.get(i).getLastNode() == null || this.get(i).getLastNode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getLastNode());
			}
			if(this.get(i).getNextNode() == null || this.get(i).getNextNode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getNextNode());
			}
			if(this.get(i).getCallPointID() == null || this.get(i).getCallPointID().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCallPointID());
			}
			if(this.get(i).getDataBaseID() == null || this.get(i).getDataBaseID().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getDataBaseID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("FIBusinessRuleDef");
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
			tError.moduleName = "FIBusinessRuleDefDBSet";
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
