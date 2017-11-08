/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCRnewStateHistorySchema;
import com.sinosoft.lis.vschema.LCRnewStateHistorySet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCRnewStateHistoryDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保费续期及中介
 */
public class LCRnewStateHistoryDBSet extends LCRnewStateHistorySet
{
private static Logger logger = Logger.getLogger(LCRnewStateHistoryDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LCRnewStateHistoryDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCRnewStateHistory");
		mflag = true;
	}

	public LCRnewStateHistoryDBSet()
	{
		db = new DBOper( "LCRnewStateHistory" );
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
			tError.moduleName = "LCRnewStateHistoryDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCRnewStateHistory WHERE  ProposalNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getProposalNo() == null || this.get(i).getProposalNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getProposalNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCRnewStateHistory");
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
			tError.moduleName = "LCRnewStateHistoryDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCRnewStateHistory SET  ContNo = ? , RiskCode = ? , State = ? , ManageCom = ? , AgentCode = ? , AgentGroup = ? , MakeDate = ? , ModifyDate = ? , Remark = ? , PaytoDate = ? , ProposalNo = ? WHERE  ProposalNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getContNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskCode());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getState());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getManageCom());
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
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRemark());
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getProposalNo() == null || this.get(i).getProposalNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getProposalNo());
			}
			// set where condition
			if(this.get(i).getProposalNo() == null || this.get(i).getProposalNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getProposalNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCRnewStateHistory");
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
			tError.moduleName = "LCRnewStateHistoryDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCRnewStateHistory(ContNo ,RiskCode ,State ,ManageCom ,AgentCode ,AgentGroup ,MakeDate ,ModifyDate ,Remark ,PaytoDate ,ProposalNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getContNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskCode());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getState());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getManageCom());
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
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRemark());
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getProposalNo() == null || this.get(i).getProposalNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getProposalNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCRnewStateHistory");
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
			tError.moduleName = "LCRnewStateHistoryDBSet";
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
