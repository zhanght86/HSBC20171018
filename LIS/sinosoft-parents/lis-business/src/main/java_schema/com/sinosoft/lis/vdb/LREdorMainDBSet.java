/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LREdorMainSchema;
import com.sinosoft.lis.vschema.LREdorMainSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LREdorMainDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LREdorMainDBSet extends LREdorMainSet
{
private static Logger logger = Logger.getLogger(LREdorMainDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LREdorMainDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LREdorMain");
		mflag = true;
	}

	public LREdorMainDBSet()
	{
		db = new DBOper( "LREdorMain" );
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
			tError.moduleName = "LREdorMainDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LREdorMain WHERE  EdorNo = ? AND PolNo = ? AND EdorType = ? AND ReinsureCom = ? AND ReinsurItem = ? AND InsuredYear = ? AND RiskCalSort = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPolNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEdorType());
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, StrTool.space1(this.get(i).getReinsurItem(), 1));
			}
			pstmt.setInt(6, this.get(i).getInsuredYear());
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, StrTool.space1(this.get(i).getRiskCalSort(), 1));
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LREdorMain");
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
			tError.moduleName = "LREdorMainDBSet";
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
			pstmt = con.prepareStatement("UPDATE LREdorMain SET  EdorNo = ? , PolNo = ? , EdorType = ? , ReinsureCom = ? , ReinsurItem = ? , InsuredYear = ? , EdorAppNo = ? , ManageCom = ? , ContNo = ? , GrpPolNo = ? , InsuredNo = ? , InsuredName = ? , PaytoDate = ? , SumPrem = ? , CalCode = ? , ChgPrem = ? , ChgAmnt = ? , ChgGetAmnt = ? , GetMoney = ? , GetInterest = ? , EdorValiDate = ? , EdorAppValiDate = ? , EdorAppDate = ? , EdorState = ? , UWState = ? , PubAccFlag = ? , ApproveCode = ? , ApproveDate = ? , UWOperator = ? , ConfOperator = ? , ConfDate = ? , ConfTime = ? , ChgCessAmt = ? , ShRePrem = ? , ShReComm = ? , CessStart = ? , CessEnd = ? , Operator = ? , MakeDate = ? , MakeTime = ? , SerialNo = ? , ModifyDate = ? , ModifyTime = ? , RiskCalSort = ? , EdorAcceptNo = ? , TransSaleChnl = ? , DutyCode = ? WHERE  EdorNo = ? AND PolNo = ? AND EdorType = ? AND ReinsureCom = ? AND ReinsurItem = ? AND InsuredYear = ? AND RiskCalSort = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPolNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEdorType());
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getReinsurItem());
			}
			pstmt.setInt(6, this.get(i).getInsuredYear());
			if(this.get(i).getEdorAppNo() == null || this.get(i).getEdorAppNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getEdorAppNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getManageCom());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getInsuredName());
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getPaytoDate()));
			}
			pstmt.setDouble(14, this.get(i).getSumPrem());
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCalCode());
			}
			pstmt.setDouble(16, this.get(i).getChgPrem());
			pstmt.setDouble(17, this.get(i).getChgAmnt());
			pstmt.setDouble(18, this.get(i).getChgGetAmnt());
			pstmt.setDouble(19, this.get(i).getGetMoney());
			pstmt.setDouble(20, this.get(i).getGetInterest());
			if(this.get(i).getEdorValiDate() == null || this.get(i).getEdorValiDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getEdorValiDate()));
			}
			if(this.get(i).getEdorAppValiDate() == null || this.get(i).getEdorAppValiDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getEdorAppValiDate()));
			}
			if(this.get(i).getEdorAppDate() == null || this.get(i).getEdorAppDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getEdorAppDate()));
			}
			if(this.get(i).getEdorState() == null || this.get(i).getEdorState().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getEdorState());
			}
			if(this.get(i).getUWState() == null || this.get(i).getUWState().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getUWState());
			}
			if(this.get(i).getPubAccFlag() == null || this.get(i).getPubAccFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getPubAccFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getUWOperator());
			}
			if(this.get(i).getConfOperator() == null || this.get(i).getConfOperator().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getConfOperator());
			}
			if(this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getConfDate()));
			}
			if(this.get(i).getConfTime() == null || this.get(i).getConfTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getConfTime());
			}
			pstmt.setDouble(33, this.get(i).getChgCessAmt());
			pstmt.setDouble(34, this.get(i).getShRePrem());
			pstmt.setDouble(35, this.get(i).getShReComm());
			if(this.get(i).getCessStart() == null || this.get(i).getCessStart().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getCessStart()));
			}
			if(this.get(i).getCessEnd() == null || this.get(i).getCessEnd().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getCessEnd()));
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getMakeTime());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getSerialNo());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(42,null);
			} else {
				pstmt.setDate(42, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getModifyTime());
			}
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getRiskCalSort());
			}
			if(this.get(i).getEdorAcceptNo() == null || this.get(i).getEdorAcceptNo().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getEdorAcceptNo());
			}
			if(this.get(i).getTransSaleChnl() == null || this.get(i).getTransSaleChnl().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getTransSaleChnl());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getDutyCode());
			}
			// set where condition
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getEdorNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getPolNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getEdorType());
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, StrTool.space1(this.get(i).getReinsurItem(), 1));
			}
			pstmt.setInt(53, this.get(i).getInsuredYear());
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, StrTool.space1(this.get(i).getRiskCalSort(), 1));
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LREdorMain");
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
			tError.moduleName = "LREdorMainDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LREdorMain(EdorNo ,PolNo ,EdorType ,ReinsureCom ,ReinsurItem ,InsuredYear ,EdorAppNo ,ManageCom ,ContNo ,GrpPolNo ,InsuredNo ,InsuredName ,PaytoDate ,SumPrem ,CalCode ,ChgPrem ,ChgAmnt ,ChgGetAmnt ,GetMoney ,GetInterest ,EdorValiDate ,EdorAppValiDate ,EdorAppDate ,EdorState ,UWState ,PubAccFlag ,ApproveCode ,ApproveDate ,UWOperator ,ConfOperator ,ConfDate ,ConfTime ,ChgCessAmt ,ShRePrem ,ShReComm ,CessStart ,CessEnd ,Operator ,MakeDate ,MakeTime ,SerialNo ,ModifyDate ,ModifyTime ,RiskCalSort ,EdorAcceptNo ,TransSaleChnl ,DutyCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPolNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEdorType());
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getReinsurItem());
			}
			pstmt.setInt(6, this.get(i).getInsuredYear());
			if(this.get(i).getEdorAppNo() == null || this.get(i).getEdorAppNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getEdorAppNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getManageCom());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getInsuredName());
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getPaytoDate()));
			}
			pstmt.setDouble(14, this.get(i).getSumPrem());
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCalCode());
			}
			pstmt.setDouble(16, this.get(i).getChgPrem());
			pstmt.setDouble(17, this.get(i).getChgAmnt());
			pstmt.setDouble(18, this.get(i).getChgGetAmnt());
			pstmt.setDouble(19, this.get(i).getGetMoney());
			pstmt.setDouble(20, this.get(i).getGetInterest());
			if(this.get(i).getEdorValiDate() == null || this.get(i).getEdorValiDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getEdorValiDate()));
			}
			if(this.get(i).getEdorAppValiDate() == null || this.get(i).getEdorAppValiDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getEdorAppValiDate()));
			}
			if(this.get(i).getEdorAppDate() == null || this.get(i).getEdorAppDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getEdorAppDate()));
			}
			if(this.get(i).getEdorState() == null || this.get(i).getEdorState().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getEdorState());
			}
			if(this.get(i).getUWState() == null || this.get(i).getUWState().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getUWState());
			}
			if(this.get(i).getPubAccFlag() == null || this.get(i).getPubAccFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getPubAccFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getUWOperator());
			}
			if(this.get(i).getConfOperator() == null || this.get(i).getConfOperator().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getConfOperator());
			}
			if(this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getConfDate()));
			}
			if(this.get(i).getConfTime() == null || this.get(i).getConfTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getConfTime());
			}
			pstmt.setDouble(33, this.get(i).getChgCessAmt());
			pstmt.setDouble(34, this.get(i).getShRePrem());
			pstmt.setDouble(35, this.get(i).getShReComm());
			if(this.get(i).getCessStart() == null || this.get(i).getCessStart().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getCessStart()));
			}
			if(this.get(i).getCessEnd() == null || this.get(i).getCessEnd().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getCessEnd()));
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getMakeTime());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getSerialNo());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(42,null);
			} else {
				pstmt.setDate(42, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getModifyTime());
			}
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getRiskCalSort());
			}
			if(this.get(i).getEdorAcceptNo() == null || this.get(i).getEdorAcceptNo().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getEdorAcceptNo());
			}
			if(this.get(i).getTransSaleChnl() == null || this.get(i).getTransSaleChnl().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getTransSaleChnl());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LREdorMain");
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
			tError.moduleName = "LREdorMainDBSet";
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
