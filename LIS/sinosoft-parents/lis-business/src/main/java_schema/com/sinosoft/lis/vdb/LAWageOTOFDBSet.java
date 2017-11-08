/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LAWageOTOFSchema;
import com.sinosoft.lis.vschema.LAWageOTOFSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LAWageOTOFDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAWageOTOFDBSet extends LAWageOTOFSet
{
private static Logger logger = Logger.getLogger(LAWageOTOFDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LAWageOTOFDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LAWageOTOF");
		mflag = true;
	}

	public LAWageOTOFDBSet()
	{
		db = new DBOper( "LAWageOTOF" );
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
			tError.moduleName = "LAWageOTOFDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LAWageOTOF WHERE  IndexCalNo = ? AND AgentCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIndexCalNo() == null || this.get(i).getIndexCalNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIndexCalNo());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAgentCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAWageOTOF");
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
			tError.moduleName = "LAWageOTOFDBSet";
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
			pstmt = con.prepareStatement("UPDATE LAWageOTOF SET  IndexCalNo = ? , AgentCode = ? , AgentGroup = ? , ManageCom = ? , GetDate = ? , SumMoney = ? , LastMoney = ? , CurrMoney = ? , F01 = ? , F02 = ? , F03 = ? , F04 = ? , F05 = ? , F06 = ? , F07 = ? , F08 = ? , F09 = ? , F10 = ? , F11 = ? , F12 = ? , F13 = ? , F14 = ? , F15 = ? , F16 = ? , F17 = ? , F18 = ? , F19 = ? , F20 = ? , F21 = ? , F22 = ? , F23 = ? , F24 = ? , F25 = ? , F26 = ? , F27 = ? , F28 = ? , F29 = ? , F30 = ? , K01 = ? , K02 = ? , K03 = ? , K04 = ? , K05 = ? , K06 = ? , K07 = ? , K08 = ? , K09 = ? , K10 = ? , K11 = ? , K12 = ? , K13 = ? , K14 = ? , K15 = ? , K16 = ? , K17 = ? , K18 = ? , K19 = ? , K20 = ? , ConfirmDate = ? , State = ? , PayMode = ? , Operator = ? , Operator2 = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BranchType = ? , ShouldMoney = ? , BranchAttr = ? , AgentGrade = ? , W01 = ? , W02 = ? , W03 = ? , W04 = ? , W05 = ? , W06 = ? , W07 = ? , W08 = ? , W09 = ? , W10 = ? , K21 = ? WHERE  IndexCalNo = ? AND AgentCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIndexCalNo() == null || this.get(i).getIndexCalNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIndexCalNo());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAgentGroup());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getManageCom());
			}
			if(this.get(i).getGetDate() == null || this.get(i).getGetDate().equals("null")) {
				pstmt.setDate(5,null);
			} else {
				pstmt.setDate(5, Date.valueOf(this.get(i).getGetDate()));
			}
			pstmt.setDouble(6, this.get(i).getSumMoney());
			pstmt.setDouble(7, this.get(i).getLastMoney());
			pstmt.setDouble(8, this.get(i).getCurrMoney());
			pstmt.setDouble(9, this.get(i).getF01());
			pstmt.setDouble(10, this.get(i).getF02());
			pstmt.setDouble(11, this.get(i).getF03());
			pstmt.setDouble(12, this.get(i).getF04());
			pstmt.setDouble(13, this.get(i).getF05());
			pstmt.setDouble(14, this.get(i).getF06());
			pstmt.setDouble(15, this.get(i).getF07());
			pstmt.setDouble(16, this.get(i).getF08());
			pstmt.setDouble(17, this.get(i).getF09());
			pstmt.setDouble(18, this.get(i).getF10());
			pstmt.setDouble(19, this.get(i).getF11());
			pstmt.setDouble(20, this.get(i).getF12());
			pstmt.setDouble(21, this.get(i).getF13());
			pstmt.setDouble(22, this.get(i).getF14());
			pstmt.setDouble(23, this.get(i).getF15());
			pstmt.setDouble(24, this.get(i).getF16());
			pstmt.setDouble(25, this.get(i).getF17());
			pstmt.setDouble(26, this.get(i).getF18());
			pstmt.setDouble(27, this.get(i).getF19());
			pstmt.setDouble(28, this.get(i).getF20());
			pstmt.setDouble(29, this.get(i).getF21());
			pstmt.setDouble(30, this.get(i).getF22());
			pstmt.setDouble(31, this.get(i).getF23());
			pstmt.setDouble(32, this.get(i).getF24());
			pstmt.setDouble(33, this.get(i).getF25());
			pstmt.setDouble(34, this.get(i).getF26());
			pstmt.setDouble(35, this.get(i).getF27());
			pstmt.setDouble(36, this.get(i).getF28());
			pstmt.setDouble(37, this.get(i).getF29());
			pstmt.setDouble(38, this.get(i).getF30());
			pstmt.setDouble(39, this.get(i).getK01());
			pstmt.setDouble(40, this.get(i).getK02());
			pstmt.setDouble(41, this.get(i).getK03());
			pstmt.setDouble(42, this.get(i).getK04());
			pstmt.setDouble(43, this.get(i).getK05());
			pstmt.setDouble(44, this.get(i).getK06());
			pstmt.setDouble(45, this.get(i).getK07());
			pstmt.setDouble(46, this.get(i).getK08());
			pstmt.setDouble(47, this.get(i).getK09());
			pstmt.setDouble(48, this.get(i).getK10());
			pstmt.setDouble(49, this.get(i).getK11());
			pstmt.setDouble(50, this.get(i).getK12());
			pstmt.setDouble(51, this.get(i).getK13());
			pstmt.setDouble(52, this.get(i).getK14());
			pstmt.setDouble(53, this.get(i).getK15());
			pstmt.setDouble(54, this.get(i).getK16());
			pstmt.setDouble(55, this.get(i).getK17());
			pstmt.setDouble(56, this.get(i).getK18());
			pstmt.setDouble(57, this.get(i).getK19());
			pstmt.setDouble(58, this.get(i).getK20());
			if(this.get(i).getConfirmDate() == null || this.get(i).getConfirmDate().equals("null")) {
				pstmt.setDate(59,null);
			} else {
				pstmt.setDate(59, Date.valueOf(this.get(i).getConfirmDate()));
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getState());
			}
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getPayMode());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getOperator());
			}
			if(this.get(i).getOperator2() == null || this.get(i).getOperator2().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getOperator2());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(64,null);
			} else {
				pstmt.setDate(64, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(66,null);
			} else {
				pstmt.setDate(66, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getModifyTime());
			}
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getBranchType());
			}
			pstmt.setDouble(69, this.get(i).getShouldMoney());
			if(this.get(i).getBranchAttr() == null || this.get(i).getBranchAttr().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getBranchAttr());
			}
			if(this.get(i).getAgentGrade() == null || this.get(i).getAgentGrade().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getAgentGrade());
			}
			pstmt.setDouble(72, this.get(i).getW01());
			pstmt.setDouble(73, this.get(i).getW02());
			pstmt.setDouble(74, this.get(i).getW03());
			pstmt.setDouble(75, this.get(i).getW04());
			pstmt.setDouble(76, this.get(i).getW05());
			pstmt.setDouble(77, this.get(i).getW06());
			pstmt.setDouble(78, this.get(i).getW07());
			pstmt.setDouble(79, this.get(i).getW08());
			pstmt.setDouble(80, this.get(i).getW09());
			pstmt.setDouble(81, this.get(i).getW10());
			pstmt.setDouble(82, this.get(i).getK21());
			// set where condition
			if(this.get(i).getIndexCalNo() == null || this.get(i).getIndexCalNo().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getIndexCalNo());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getAgentCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAWageOTOF");
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
			tError.moduleName = "LAWageOTOFDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LAWageOTOF(IndexCalNo ,AgentCode ,AgentGroup ,ManageCom ,GetDate ,SumMoney ,LastMoney ,CurrMoney ,F01 ,F02 ,F03 ,F04 ,F05 ,F06 ,F07 ,F08 ,F09 ,F10 ,F11 ,F12 ,F13 ,F14 ,F15 ,F16 ,F17 ,F18 ,F19 ,F20 ,F21 ,F22 ,F23 ,F24 ,F25 ,F26 ,F27 ,F28 ,F29 ,F30 ,K01 ,K02 ,K03 ,K04 ,K05 ,K06 ,K07 ,K08 ,K09 ,K10 ,K11 ,K12 ,K13 ,K14 ,K15 ,K16 ,K17 ,K18 ,K19 ,K20 ,ConfirmDate ,State ,PayMode ,Operator ,Operator2 ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BranchType ,ShouldMoney ,BranchAttr ,AgentGrade ,W01 ,W02 ,W03 ,W04 ,W05 ,W06 ,W07 ,W08 ,W09 ,W10 ,K21) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIndexCalNo() == null || this.get(i).getIndexCalNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIndexCalNo());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAgentGroup());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getManageCom());
			}
			if(this.get(i).getGetDate() == null || this.get(i).getGetDate().equals("null")) {
				pstmt.setDate(5,null);
			} else {
				pstmt.setDate(5, Date.valueOf(this.get(i).getGetDate()));
			}
			pstmt.setDouble(6, this.get(i).getSumMoney());
			pstmt.setDouble(7, this.get(i).getLastMoney());
			pstmt.setDouble(8, this.get(i).getCurrMoney());
			pstmt.setDouble(9, this.get(i).getF01());
			pstmt.setDouble(10, this.get(i).getF02());
			pstmt.setDouble(11, this.get(i).getF03());
			pstmt.setDouble(12, this.get(i).getF04());
			pstmt.setDouble(13, this.get(i).getF05());
			pstmt.setDouble(14, this.get(i).getF06());
			pstmt.setDouble(15, this.get(i).getF07());
			pstmt.setDouble(16, this.get(i).getF08());
			pstmt.setDouble(17, this.get(i).getF09());
			pstmt.setDouble(18, this.get(i).getF10());
			pstmt.setDouble(19, this.get(i).getF11());
			pstmt.setDouble(20, this.get(i).getF12());
			pstmt.setDouble(21, this.get(i).getF13());
			pstmt.setDouble(22, this.get(i).getF14());
			pstmt.setDouble(23, this.get(i).getF15());
			pstmt.setDouble(24, this.get(i).getF16());
			pstmt.setDouble(25, this.get(i).getF17());
			pstmt.setDouble(26, this.get(i).getF18());
			pstmt.setDouble(27, this.get(i).getF19());
			pstmt.setDouble(28, this.get(i).getF20());
			pstmt.setDouble(29, this.get(i).getF21());
			pstmt.setDouble(30, this.get(i).getF22());
			pstmt.setDouble(31, this.get(i).getF23());
			pstmt.setDouble(32, this.get(i).getF24());
			pstmt.setDouble(33, this.get(i).getF25());
			pstmt.setDouble(34, this.get(i).getF26());
			pstmt.setDouble(35, this.get(i).getF27());
			pstmt.setDouble(36, this.get(i).getF28());
			pstmt.setDouble(37, this.get(i).getF29());
			pstmt.setDouble(38, this.get(i).getF30());
			pstmt.setDouble(39, this.get(i).getK01());
			pstmt.setDouble(40, this.get(i).getK02());
			pstmt.setDouble(41, this.get(i).getK03());
			pstmt.setDouble(42, this.get(i).getK04());
			pstmt.setDouble(43, this.get(i).getK05());
			pstmt.setDouble(44, this.get(i).getK06());
			pstmt.setDouble(45, this.get(i).getK07());
			pstmt.setDouble(46, this.get(i).getK08());
			pstmt.setDouble(47, this.get(i).getK09());
			pstmt.setDouble(48, this.get(i).getK10());
			pstmt.setDouble(49, this.get(i).getK11());
			pstmt.setDouble(50, this.get(i).getK12());
			pstmt.setDouble(51, this.get(i).getK13());
			pstmt.setDouble(52, this.get(i).getK14());
			pstmt.setDouble(53, this.get(i).getK15());
			pstmt.setDouble(54, this.get(i).getK16());
			pstmt.setDouble(55, this.get(i).getK17());
			pstmt.setDouble(56, this.get(i).getK18());
			pstmt.setDouble(57, this.get(i).getK19());
			pstmt.setDouble(58, this.get(i).getK20());
			if(this.get(i).getConfirmDate() == null || this.get(i).getConfirmDate().equals("null")) {
				pstmt.setDate(59,null);
			} else {
				pstmt.setDate(59, Date.valueOf(this.get(i).getConfirmDate()));
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getState());
			}
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getPayMode());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getOperator());
			}
			if(this.get(i).getOperator2() == null || this.get(i).getOperator2().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getOperator2());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(64,null);
			} else {
				pstmt.setDate(64, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(66,null);
			} else {
				pstmt.setDate(66, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getModifyTime());
			}
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getBranchType());
			}
			pstmt.setDouble(69, this.get(i).getShouldMoney());
			if(this.get(i).getBranchAttr() == null || this.get(i).getBranchAttr().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getBranchAttr());
			}
			if(this.get(i).getAgentGrade() == null || this.get(i).getAgentGrade().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getAgentGrade());
			}
			pstmt.setDouble(72, this.get(i).getW01());
			pstmt.setDouble(73, this.get(i).getW02());
			pstmt.setDouble(74, this.get(i).getW03());
			pstmt.setDouble(75, this.get(i).getW04());
			pstmt.setDouble(76, this.get(i).getW05());
			pstmt.setDouble(77, this.get(i).getW06());
			pstmt.setDouble(78, this.get(i).getW07());
			pstmt.setDouble(79, this.get(i).getW08());
			pstmt.setDouble(80, this.get(i).getW09());
			pstmt.setDouble(81, this.get(i).getW10());
			pstmt.setDouble(82, this.get(i).getK21());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAWageOTOF");
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
			tError.moduleName = "LAWageOTOFDBSet";
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
