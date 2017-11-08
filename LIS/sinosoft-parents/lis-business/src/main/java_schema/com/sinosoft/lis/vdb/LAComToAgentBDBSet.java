/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LAComToAgentBSchema;
import com.sinosoft.lis.vschema.LAComToAgentBSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LAComToAgentBDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAComToAgentBDBSet extends LAComToAgentBSet
{
private static Logger logger = Logger.getLogger(LAComToAgentBDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LAComToAgentBDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LAComToAgentB");
		mflag = true;
	}

	public LAComToAgentBDBSet()
	{
		db = new DBOper( "LAComToAgentB" );
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
			tError.moduleName = "LAComToAgentBDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LAComToAgentB WHERE  EdorNo = ? AND EdorType = ? AND AgentCom = ? AND RelaType = ? AND AgentGroup = ?");
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
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAgentCom());
			}
			if(this.get(i).getRelaType() == null || this.get(i).getRelaType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, StrTool.space1(this.get(i).getRelaType(), 1));
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAgentGroup());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAComToAgentB");
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
			tError.moduleName = "LAComToAgentBDBSet";
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
			pstmt = con.prepareStatement("UPDATE LAComToAgentB SET  EdorNo = ? , EdorType = ? , AgentCom = ? , RelaType = ? , AgentCode = ? , AgentGroup = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , StartDate = ? , EndDate = ? WHERE  EdorNo = ? AND EdorType = ? AND AgentCom = ? AND RelaType = ? AND AgentGroup = ?");
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
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAgentCom());
			}
			if(this.get(i).getRelaType() == null || this.get(i).getRelaType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRelaType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAgentGroup());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getModifyTime());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getEndDate()));
			}
			// set where condition
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getEdorType());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAgentCom());
			}
			if(this.get(i).getRelaType() == null || this.get(i).getRelaType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, StrTool.space1(this.get(i).getRelaType(), 1));
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAgentGroup());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAComToAgentB");
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
			tError.moduleName = "LAComToAgentBDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LAComToAgentB(EdorNo ,EdorType ,AgentCom ,RelaType ,AgentCode ,AgentGroup ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,StartDate ,EndDate) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAgentCom());
			}
			if(this.get(i).getRelaType() == null || this.get(i).getRelaType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRelaType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAgentGroup());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getModifyTime());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getEndDate()));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAComToAgentB");
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
			tError.moduleName = "LAComToAgentBDBSet";
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
