/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LABranchGroupBSchema;
import com.sinosoft.lis.vschema.LABranchGroupBSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LABranchGroupBDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LABranchGroupBDBSet extends LABranchGroupBSet
{
private static Logger logger = Logger.getLogger(LABranchGroupBDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LABranchGroupBDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LABranchGroupB");
		mflag = true;
	}

	public LABranchGroupBDBSet()
	{
		db = new DBOper( "LABranchGroupB" );
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
			tError.moduleName = "LABranchGroupBDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LABranchGroupB WHERE  AgentGroup = ? AND EdorNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAgentGroup());
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LABranchGroupB");
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
			tError.moduleName = "LABranchGroupBDBSet";
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
			pstmt = con.prepareStatement("UPDATE LABranchGroupB SET  AgentGroup = ? , Name = ? , ManageCom = ? , UpBranch = ? , BranchAttr = ? , BranchType = ? , BranchLevel = ? , BranchManager = ? , BranchAddressCode = ? , BranchAddress = ? , BranchPhone = ? , BranchFax = ? , BranchZipcode = ? , FoundDate = ? , EndDate = ? , EndFlag = ? , CertifyFlag = ? , FieldFlag = ? , State = ? , Operator2 = ? , MakeDate2 = ? , MakeTime2 = ? , ModifyDate2 = ? , ModifyTime2 = ? , EdorNo = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BranchManagerName = ? , UpBranchAttr = ? , BranchJobType = ? , EdorType = ? , BranchKind = ? , IndexCalNo = ? WHERE  AgentGroup = ? AND EdorNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAgentGroup());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getName());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getManageCom());
			}
			if(this.get(i).getUpBranch() == null || this.get(i).getUpBranch().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getUpBranch());
			}
			if(this.get(i).getBranchAttr() == null || this.get(i).getBranchAttr().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBranchAttr());
			}
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBranchType());
			}
			if(this.get(i).getBranchLevel() == null || this.get(i).getBranchLevel().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBranchLevel());
			}
			if(this.get(i).getBranchManager() == null || this.get(i).getBranchManager().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getBranchManager());
			}
			if(this.get(i).getBranchAddressCode() == null || this.get(i).getBranchAddressCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getBranchAddressCode());
			}
			if(this.get(i).getBranchAddress() == null || this.get(i).getBranchAddress().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getBranchAddress());
			}
			if(this.get(i).getBranchPhone() == null || this.get(i).getBranchPhone().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBranchPhone());
			}
			if(this.get(i).getBranchFax() == null || this.get(i).getBranchFax().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getBranchFax());
			}
			if(this.get(i).getBranchZipcode() == null || this.get(i).getBranchZipcode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getBranchZipcode());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getEndFlag() == null || this.get(i).getEndFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getEndFlag());
			}
			if(this.get(i).getCertifyFlag() == null || this.get(i).getCertifyFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getCertifyFlag());
			}
			if(this.get(i).getFieldFlag() == null || this.get(i).getFieldFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getFieldFlag());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getState());
			}
			if(this.get(i).getOperator2() == null || this.get(i).getOperator2().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getOperator2());
			}
			if(this.get(i).getMakeDate2() == null || this.get(i).getMakeDate2().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getMakeDate2()));
			}
			if(this.get(i).getMakeTime2() == null || this.get(i).getMakeTime2().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMakeTime2());
			}
			if(this.get(i).getModifyDate2() == null || this.get(i).getModifyDate2().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getModifyDate2()));
			}
			if(this.get(i).getModifyTime2() == null || this.get(i).getModifyTime2().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getModifyTime2());
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getEdorNo());
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
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getModifyTime());
			}
			if(this.get(i).getBranchManagerName() == null || this.get(i).getBranchManagerName().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBranchManagerName());
			}
			if(this.get(i).getUpBranchAttr() == null || this.get(i).getUpBranchAttr().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getUpBranchAttr());
			}
			if(this.get(i).getBranchJobType() == null || this.get(i).getBranchJobType().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getBranchJobType());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getEdorType());
			}
			if(this.get(i).getBranchKind() == null || this.get(i).getBranchKind().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getBranchKind());
			}
			if(this.get(i).getIndexCalNo() == null || this.get(i).getIndexCalNo().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getIndexCalNo());
			}
			// set where condition
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getAgentGroup());
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getEdorNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LABranchGroupB");
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
			tError.moduleName = "LABranchGroupBDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LABranchGroupB(AgentGroup ,Name ,ManageCom ,UpBranch ,BranchAttr ,BranchType ,BranchLevel ,BranchManager ,BranchAddressCode ,BranchAddress ,BranchPhone ,BranchFax ,BranchZipcode ,FoundDate ,EndDate ,EndFlag ,CertifyFlag ,FieldFlag ,State ,Operator2 ,MakeDate2 ,MakeTime2 ,ModifyDate2 ,ModifyTime2 ,EdorNo ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BranchManagerName ,UpBranchAttr ,BranchJobType ,EdorType ,BranchKind ,IndexCalNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAgentGroup());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getName());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getManageCom());
			}
			if(this.get(i).getUpBranch() == null || this.get(i).getUpBranch().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getUpBranch());
			}
			if(this.get(i).getBranchAttr() == null || this.get(i).getBranchAttr().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBranchAttr());
			}
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBranchType());
			}
			if(this.get(i).getBranchLevel() == null || this.get(i).getBranchLevel().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBranchLevel());
			}
			if(this.get(i).getBranchManager() == null || this.get(i).getBranchManager().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getBranchManager());
			}
			if(this.get(i).getBranchAddressCode() == null || this.get(i).getBranchAddressCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getBranchAddressCode());
			}
			if(this.get(i).getBranchAddress() == null || this.get(i).getBranchAddress().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getBranchAddress());
			}
			if(this.get(i).getBranchPhone() == null || this.get(i).getBranchPhone().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBranchPhone());
			}
			if(this.get(i).getBranchFax() == null || this.get(i).getBranchFax().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getBranchFax());
			}
			if(this.get(i).getBranchZipcode() == null || this.get(i).getBranchZipcode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getBranchZipcode());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getEndFlag() == null || this.get(i).getEndFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getEndFlag());
			}
			if(this.get(i).getCertifyFlag() == null || this.get(i).getCertifyFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getCertifyFlag());
			}
			if(this.get(i).getFieldFlag() == null || this.get(i).getFieldFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getFieldFlag());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getState());
			}
			if(this.get(i).getOperator2() == null || this.get(i).getOperator2().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getOperator2());
			}
			if(this.get(i).getMakeDate2() == null || this.get(i).getMakeDate2().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getMakeDate2()));
			}
			if(this.get(i).getMakeTime2() == null || this.get(i).getMakeTime2().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMakeTime2());
			}
			if(this.get(i).getModifyDate2() == null || this.get(i).getModifyDate2().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getModifyDate2()));
			}
			if(this.get(i).getModifyTime2() == null || this.get(i).getModifyTime2().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getModifyTime2());
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getEdorNo());
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
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getModifyTime());
			}
			if(this.get(i).getBranchManagerName() == null || this.get(i).getBranchManagerName().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBranchManagerName());
			}
			if(this.get(i).getUpBranchAttr() == null || this.get(i).getUpBranchAttr().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getUpBranchAttr());
			}
			if(this.get(i).getBranchJobType() == null || this.get(i).getBranchJobType().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getBranchJobType());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getEdorType());
			}
			if(this.get(i).getBranchKind() == null || this.get(i).getBranchKind().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getBranchKind());
			}
			if(this.get(i).getIndexCalNo() == null || this.get(i).getIndexCalNo().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getIndexCalNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LABranchGroupB");
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
			tError.moduleName = "LABranchGroupBDBSet";
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
