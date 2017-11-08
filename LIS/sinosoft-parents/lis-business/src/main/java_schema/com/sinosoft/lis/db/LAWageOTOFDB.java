/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LAWageOTOFSchema;
import com.sinosoft.lis.vschema.LAWageOTOFSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LAWageOTOFDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAWageOTOFDB extends LAWageOTOFSchema
{
private static Logger logger = Logger.getLogger(LAWageOTOFDB.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 错误信息

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;
	private Statement mStatement = null;
	// @Constructor
	public LAWageOTOFDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LAWageOTOF" );
		mflag = true;
	}

	public LAWageOTOFDB()
	{
		con = null;
		db = new DBOper( "LAWageOTOF" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LAWageOTOFSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LAWageOTOFSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("DELETE FROM LAWageOTOF WHERE  IndexCalNo = ? AND AgentCode = ?");
			if(this.getIndexCalNo() == null || this.getIndexCalNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getIndexCalNo());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getAgentCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAWageOTOF");
		sqlObj.setSQL(4, this);
		sqlObj.getSQL();

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAWageOTOF");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LAWageOTOF SET  IndexCalNo = ? , AgentCode = ? , AgentGroup = ? , ManageCom = ? , GetDate = ? , SumMoney = ? , LastMoney = ? , CurrMoney = ? , F01 = ? , F02 = ? , F03 = ? , F04 = ? , F05 = ? , F06 = ? , F07 = ? , F08 = ? , F09 = ? , F10 = ? , F11 = ? , F12 = ? , F13 = ? , F14 = ? , F15 = ? , F16 = ? , F17 = ? , F18 = ? , F19 = ? , F20 = ? , F21 = ? , F22 = ? , F23 = ? , F24 = ? , F25 = ? , F26 = ? , F27 = ? , F28 = ? , F29 = ? , F30 = ? , K01 = ? , K02 = ? , K03 = ? , K04 = ? , K05 = ? , K06 = ? , K07 = ? , K08 = ? , K09 = ? , K10 = ? , K11 = ? , K12 = ? , K13 = ? , K14 = ? , K15 = ? , K16 = ? , K17 = ? , K18 = ? , K19 = ? , K20 = ? , ConfirmDate = ? , State = ? , PayMode = ? , Operator = ? , Operator2 = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BranchType = ? , ShouldMoney = ? , BranchAttr = ? , AgentGrade = ? , W01 = ? , W02 = ? , W03 = ? , W04 = ? , W05 = ? , W06 = ? , W07 = ? , W08 = ? , W09 = ? , W10 = ? , K21 = ? WHERE  IndexCalNo = ? AND AgentCode = ?");
			if(this.getIndexCalNo() == null || this.getIndexCalNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getIndexCalNo());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getAgentGroup());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getManageCom());
			}
			if(this.getGetDate() == null || this.getGetDate().equals("null")) {
				pstmt.setNull(5, 91);
			} else {
				pstmt.setDate(5, Date.valueOf(this.getGetDate()));
			}
			pstmt.setDouble(6, this.getSumMoney());
			pstmt.setDouble(7, this.getLastMoney());
			pstmt.setDouble(8, this.getCurrMoney());
			pstmt.setDouble(9, this.getF01());
			pstmt.setDouble(10, this.getF02());
			pstmt.setDouble(11, this.getF03());
			pstmt.setDouble(12, this.getF04());
			pstmt.setDouble(13, this.getF05());
			pstmt.setDouble(14, this.getF06());
			pstmt.setDouble(15, this.getF07());
			pstmt.setDouble(16, this.getF08());
			pstmt.setDouble(17, this.getF09());
			pstmt.setDouble(18, this.getF10());
			pstmt.setDouble(19, this.getF11());
			pstmt.setDouble(20, this.getF12());
			pstmt.setDouble(21, this.getF13());
			pstmt.setDouble(22, this.getF14());
			pstmt.setDouble(23, this.getF15());
			pstmt.setDouble(24, this.getF16());
			pstmt.setDouble(25, this.getF17());
			pstmt.setDouble(26, this.getF18());
			pstmt.setDouble(27, this.getF19());
			pstmt.setDouble(28, this.getF20());
			pstmt.setDouble(29, this.getF21());
			pstmt.setDouble(30, this.getF22());
			pstmt.setDouble(31, this.getF23());
			pstmt.setDouble(32, this.getF24());
			pstmt.setDouble(33, this.getF25());
			pstmt.setDouble(34, this.getF26());
			pstmt.setDouble(35, this.getF27());
			pstmt.setDouble(36, this.getF28());
			pstmt.setDouble(37, this.getF29());
			pstmt.setDouble(38, this.getF30());
			pstmt.setDouble(39, this.getK01());
			pstmt.setDouble(40, this.getK02());
			pstmt.setDouble(41, this.getK03());
			pstmt.setDouble(42, this.getK04());
			pstmt.setDouble(43, this.getK05());
			pstmt.setDouble(44, this.getK06());
			pstmt.setDouble(45, this.getK07());
			pstmt.setDouble(46, this.getK08());
			pstmt.setDouble(47, this.getK09());
			pstmt.setDouble(48, this.getK10());
			pstmt.setDouble(49, this.getK11());
			pstmt.setDouble(50, this.getK12());
			pstmt.setDouble(51, this.getK13());
			pstmt.setDouble(52, this.getK14());
			pstmt.setDouble(53, this.getK15());
			pstmt.setDouble(54, this.getK16());
			pstmt.setDouble(55, this.getK17());
			pstmt.setDouble(56, this.getK18());
			pstmt.setDouble(57, this.getK19());
			pstmt.setDouble(58, this.getK20());
			if(this.getConfirmDate() == null || this.getConfirmDate().equals("null")) {
				pstmt.setNull(59, 91);
			} else {
				pstmt.setDate(59, Date.valueOf(this.getConfirmDate()));
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getState());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getPayMode());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getOperator());
			}
			if(this.getOperator2() == null || this.getOperator2().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getOperator2());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(64, 91);
			} else {
				pstmt.setDate(64, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(65, 1);
			} else {
				pstmt.setString(65, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(66, 91);
			} else {
				pstmt.setDate(66, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(67, 1);
			} else {
				pstmt.setString(67, this.getModifyTime());
			}
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getBranchType());
			}
			pstmt.setDouble(69, this.getShouldMoney());
			if(this.getBranchAttr() == null || this.getBranchAttr().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getBranchAttr());
			}
			if(this.getAgentGrade() == null || this.getAgentGrade().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getAgentGrade());
			}
			pstmt.setDouble(72, this.getW01());
			pstmt.setDouble(73, this.getW02());
			pstmt.setDouble(74, this.getW03());
			pstmt.setDouble(75, this.getW04());
			pstmt.setDouble(76, this.getW05());
			pstmt.setDouble(77, this.getW06());
			pstmt.setDouble(78, this.getW07());
			pstmt.setDouble(79, this.getW08());
			pstmt.setDouble(80, this.getW09());
			pstmt.setDouble(81, this.getW10());
			pstmt.setDouble(82, this.getK21());
			// set where condition
			if(this.getIndexCalNo() == null || this.getIndexCalNo().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getIndexCalNo());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getAgentCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAWageOTOF");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LAWageOTOF(IndexCalNo ,AgentCode ,AgentGroup ,ManageCom ,GetDate ,SumMoney ,LastMoney ,CurrMoney ,F01 ,F02 ,F03 ,F04 ,F05 ,F06 ,F07 ,F08 ,F09 ,F10 ,F11 ,F12 ,F13 ,F14 ,F15 ,F16 ,F17 ,F18 ,F19 ,F20 ,F21 ,F22 ,F23 ,F24 ,F25 ,F26 ,F27 ,F28 ,F29 ,F30 ,K01 ,K02 ,K03 ,K04 ,K05 ,K06 ,K07 ,K08 ,K09 ,K10 ,K11 ,K12 ,K13 ,K14 ,K15 ,K16 ,K17 ,K18 ,K19 ,K20 ,ConfirmDate ,State ,PayMode ,Operator ,Operator2 ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BranchType ,ShouldMoney ,BranchAttr ,AgentGrade ,W01 ,W02 ,W03 ,W04 ,W05 ,W06 ,W07 ,W08 ,W09 ,W10 ,K21) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getIndexCalNo() == null || this.getIndexCalNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getIndexCalNo());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getAgentGroup());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getManageCom());
			}
			if(this.getGetDate() == null || this.getGetDate().equals("null")) {
				pstmt.setNull(5, 91);
			} else {
				pstmt.setDate(5, Date.valueOf(this.getGetDate()));
			}
			pstmt.setDouble(6, this.getSumMoney());
			pstmt.setDouble(7, this.getLastMoney());
			pstmt.setDouble(8, this.getCurrMoney());
			pstmt.setDouble(9, this.getF01());
			pstmt.setDouble(10, this.getF02());
			pstmt.setDouble(11, this.getF03());
			pstmt.setDouble(12, this.getF04());
			pstmt.setDouble(13, this.getF05());
			pstmt.setDouble(14, this.getF06());
			pstmt.setDouble(15, this.getF07());
			pstmt.setDouble(16, this.getF08());
			pstmt.setDouble(17, this.getF09());
			pstmt.setDouble(18, this.getF10());
			pstmt.setDouble(19, this.getF11());
			pstmt.setDouble(20, this.getF12());
			pstmt.setDouble(21, this.getF13());
			pstmt.setDouble(22, this.getF14());
			pstmt.setDouble(23, this.getF15());
			pstmt.setDouble(24, this.getF16());
			pstmt.setDouble(25, this.getF17());
			pstmt.setDouble(26, this.getF18());
			pstmt.setDouble(27, this.getF19());
			pstmt.setDouble(28, this.getF20());
			pstmt.setDouble(29, this.getF21());
			pstmt.setDouble(30, this.getF22());
			pstmt.setDouble(31, this.getF23());
			pstmt.setDouble(32, this.getF24());
			pstmt.setDouble(33, this.getF25());
			pstmt.setDouble(34, this.getF26());
			pstmt.setDouble(35, this.getF27());
			pstmt.setDouble(36, this.getF28());
			pstmt.setDouble(37, this.getF29());
			pstmt.setDouble(38, this.getF30());
			pstmt.setDouble(39, this.getK01());
			pstmt.setDouble(40, this.getK02());
			pstmt.setDouble(41, this.getK03());
			pstmt.setDouble(42, this.getK04());
			pstmt.setDouble(43, this.getK05());
			pstmt.setDouble(44, this.getK06());
			pstmt.setDouble(45, this.getK07());
			pstmt.setDouble(46, this.getK08());
			pstmt.setDouble(47, this.getK09());
			pstmt.setDouble(48, this.getK10());
			pstmt.setDouble(49, this.getK11());
			pstmt.setDouble(50, this.getK12());
			pstmt.setDouble(51, this.getK13());
			pstmt.setDouble(52, this.getK14());
			pstmt.setDouble(53, this.getK15());
			pstmt.setDouble(54, this.getK16());
			pstmt.setDouble(55, this.getK17());
			pstmt.setDouble(56, this.getK18());
			pstmt.setDouble(57, this.getK19());
			pstmt.setDouble(58, this.getK20());
			if(this.getConfirmDate() == null || this.getConfirmDate().equals("null")) {
				pstmt.setNull(59, 91);
			} else {
				pstmt.setDate(59, Date.valueOf(this.getConfirmDate()));
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getState());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getPayMode());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getOperator());
			}
			if(this.getOperator2() == null || this.getOperator2().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getOperator2());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(64, 91);
			} else {
				pstmt.setDate(64, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(65, 1);
			} else {
				pstmt.setString(65, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(66, 91);
			} else {
				pstmt.setDate(66, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(67, 1);
			} else {
				pstmt.setString(67, this.getModifyTime());
			}
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getBranchType());
			}
			pstmt.setDouble(69, this.getShouldMoney());
			if(this.getBranchAttr() == null || this.getBranchAttr().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getBranchAttr());
			}
			if(this.getAgentGrade() == null || this.getAgentGrade().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getAgentGrade());
			}
			pstmt.setDouble(72, this.getW01());
			pstmt.setDouble(73, this.getW02());
			pstmt.setDouble(74, this.getW03());
			pstmt.setDouble(75, this.getW04());
			pstmt.setDouble(76, this.getW05());
			pstmt.setDouble(77, this.getW06());
			pstmt.setDouble(78, this.getW07());
			pstmt.setDouble(79, this.getW08());
			pstmt.setDouble(80, this.getW09());
			pstmt.setDouble(81, this.getW10());
			pstmt.setDouble(82, this.getK21());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean getInfo()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("SELECT * FROM LAWageOTOF WHERE  IndexCalNo = ? AND AgentCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getIndexCalNo() == null || this.getIndexCalNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getIndexCalNo());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getAgentCode());
			}
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAWageOTOFDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors .addOneError(tError);

					try{ rs.close(); } catch( Exception ex ) {}
					try{ pstmt.close(); } catch( Exception ex1 ) {}

					if (!mflag)
					{
						try
						{
							con.close();
						}
						catch(Exception et){}
					}
					return false;
				}
				break;
			}
			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if( i == 0 )
			{
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

	public LAWageOTOFSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LAWageOTOFSet aLAWageOTOFSet = new LAWageOTOFSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LAWageOTOF");
			LAWageOTOFSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				LAWageOTOFSchema s1 = new LAWageOTOFSchema();
				s1.setSchema(rs,i);
				aLAWageOTOFSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLAWageOTOFSet;

	}

	public LAWageOTOFSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LAWageOTOFSet aLAWageOTOFSet = new LAWageOTOFSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;
				LAWageOTOFSchema s1 = new LAWageOTOFSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAWageOTOFDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAWageOTOFSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLAWageOTOFSet;
	}

	public LAWageOTOFSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LAWageOTOFSet aLAWageOTOFSet = new LAWageOTOFSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LAWageOTOF");
			LAWageOTOFSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LAWageOTOFSchema s1 = new LAWageOTOFSchema();
				s1.setSchema(rs,i);
				aLAWageOTOFSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLAWageOTOFSet;

	}

	public LAWageOTOFSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LAWageOTOFSet aLAWageOTOFSet = new LAWageOTOFSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LAWageOTOFSchema s1 = new LAWageOTOFSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAWageOTOFDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAWageOTOFSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLAWageOTOFSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LAWageOTOF");
			LAWageOTOFSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LAWageOTOF " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LAWageOTOFDB";
				tError.functionName = "update";
				tError.errorMessage = "更新数据失败!";
				this.mErrors .addOneError(tError);

				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

/**
 * 准备数据查询条件
 * @param strSQL String
 * @return boolean
 */
public boolean prepareData(String strSQL)
{
    if (mResultSet != null)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LAWageOTOFDB";
        tError.functionName = "prepareData";
        tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
        this.mErrors.addOneError(tError);
        return false;
    }

    if (!mflag)
    {
        con = DBConnPool.getConnection();
    }
    try
    {
        mStatement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));
    }
    catch (Exception e)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LAWageOTOFDB";
        tError.functionName = "prepareData";
        tError.errorMessage = e.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }

    if (!mflag)
    {
        try
        {
            con.close();
        }
        catch (Exception e)
        {}
    }
    return true;
}

/**
 * 获取数据集
 * @return boolean
 */
public boolean hasMoreData()
{
    boolean flag = true;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LAWageOTOFDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return false;
    }
    try
    {
        flag = mResultSet.next();
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LAWageOTOFDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }
    return flag;
}
/**
 * 获取定量数据
 * @return LAWageOTOFSet
 */
public LAWageOTOFSet getData()
{
    int tCount = 0;
    LAWageOTOFSet tLAWageOTOFSet = new LAWageOTOFSet();
    LAWageOTOFSchema tLAWageOTOFSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LAWageOTOFDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLAWageOTOFSchema = new LAWageOTOFSchema();
        tLAWageOTOFSchema.setSchema(mResultSet, 1);
        tLAWageOTOFSet.add(tLAWageOTOFSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLAWageOTOFSchema = new LAWageOTOFSchema();
                tLAWageOTOFSchema.setSchema(mResultSet, 1);
                tLAWageOTOFSet.add(tLAWageOTOFSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LAWageOTOFDB";
        tError.functionName = "getData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return null;
    }
    return tLAWageOTOFSet;
}
/**
 * 关闭数据集
 * @return boolean
 */
public boolean closeData()
{
    boolean flag = true;
    try
    {
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "LAWageOTOFDB";
            tError.functionName = "closeData";
            tError.errorMessage = "数据集已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mResultSet.close();
            mResultSet = null;
        }
    }
    catch (Exception ex2)
    {
        CError tError = new CError();
        tError.moduleName = "LAWageOTOFDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex2.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    try
    {
        if (null == mStatement)
        {
            CError tError = new CError();
            tError.moduleName = "LAWageOTOFDB";
            tError.functionName = "closeData";
            tError.errorMessage = "语句已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mStatement.close();
            mStatement = null;
        }
    }
    catch (Exception ex3)
    {
        CError tError = new CError();
        tError.moduleName = "LAWageOTOFDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LAWageOTOFSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LAWageOTOFSet aLAWageOTOFSet = new LAWageOTOFSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				LAWageOTOFSchema s1 = new LAWageOTOFSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAWageOTOFDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAWageOTOFSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch(Exception ex2) {}
			try{ pstmt.close(); } catch(Exception ex3) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e) {}
		}

		return aLAWageOTOFSet;
	}

	public LAWageOTOFSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LAWageOTOFSet aLAWageOTOFSet = new LAWageOTOFSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break; 
				}

				LAWageOTOFSchema s1 = new LAWageOTOFSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAWageOTOFDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAWageOTOFSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageOTOFDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e){}
		}

		return aLAWageOTOFSet; 
	}

}
