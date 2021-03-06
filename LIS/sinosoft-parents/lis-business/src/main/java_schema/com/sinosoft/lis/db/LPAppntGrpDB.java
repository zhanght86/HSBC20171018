/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LPAppntGrpSchema;
import com.sinosoft.lis.vschema.LPAppntGrpSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPAppntGrpDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LPAppntGrpDB extends LPAppntGrpSchema
{
private static Logger logger = Logger.getLogger(LPAppntGrpDB.class);

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
	public LPAppntGrpDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LPAppntGrp" );
		mflag = true;
	}

	public LPAppntGrpDB()
	{
		con = null;
		db = new DBOper( "LPAppntGrp" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPAppntGrpSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPAppntGrpSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
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
			pstmt = con.prepareStatement("DELETE FROM LPAppntGrp WHERE  EdorNo = ? AND EdorType = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPAppntGrp");
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
		SQLString sqlObj = new SQLString("LPAppntGrp");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LPAppntGrp SET  EdorNo = ? , EdorType = ? , PolNo = ? , GrpNo = ? , RelationToInsured = ? , AppntGrade = ? , Password = ? , GrpName = ? , GrpAddressCode = ? , GrpAddress = ? , GrpZipCode = ? , BusinessType = ? , GrpNature = ? , Peoples = ? , RgtMoney = ? , Asset = ? , NetProfitRate = ? , MainBussiness = ? , Corporation = ? , ComAera = ? , LinkMan1 = ? , Department1 = ? , HeadShip1 = ? , Phone1 = ? , E_Mail1 = ? , Fax1 = ? , LinkMan2 = ? , Department2 = ? , HeadShip2 = ? , Phone2 = ? , E_Mail2 = ? , Fax2 = ? , Fax = ? , Phone = ? , GetFlag = ? , Satrap = ? , EMail = ? , FoundDate = ? , BankAccNo = ? , BankCode = ? , GrpGroupNo = ? , State = ? , Remark = ? , BlacklistFlag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  EdorNo = ? AND EdorType = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolNo());
			}
			if(this.getGrpNo() == null || this.getGrpNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpNo());
			}
			if(this.getRelationToInsured() == null || this.getRelationToInsured().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRelationToInsured());
			}
			if(this.getAppntGrade() == null || this.getAppntGrade().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getAppntGrade());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getPassword());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getGrpName());
			}
			if(this.getGrpAddressCode() == null || this.getGrpAddressCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getGrpAddressCode());
			}
			if(this.getGrpAddress() == null || this.getGrpAddress().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getGrpAddress());
			}
			if(this.getGrpZipCode() == null || this.getGrpZipCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getGrpZipCode());
			}
			if(this.getBusinessType() == null || this.getBusinessType().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getBusinessType());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getGrpNature());
			}
			pstmt.setInt(14, this.getPeoples());
			pstmt.setDouble(15, this.getRgtMoney());
			pstmt.setDouble(16, this.getAsset());
			pstmt.setDouble(17, this.getNetProfitRate());
			if(this.getMainBussiness() == null || this.getMainBussiness().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getMainBussiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getCorporation());
			}
			if(this.getComAera() == null || this.getComAera().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getComAera());
			}
			if(this.getLinkMan1() == null || this.getLinkMan1().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getLinkMan1());
			}
			if(this.getDepartment1() == null || this.getDepartment1().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getDepartment1());
			}
			if(this.getHeadShip1() == null || this.getHeadShip1().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getHeadShip1());
			}
			if(this.getPhone1() == null || this.getPhone1().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPhone1());
			}
			if(this.getE_Mail1() == null || this.getE_Mail1().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getE_Mail1());
			}
			if(this.getFax1() == null || this.getFax1().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getFax1());
			}
			if(this.getLinkMan2() == null || this.getLinkMan2().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getLinkMan2());
			}
			if(this.getDepartment2() == null || this.getDepartment2().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getDepartment2());
			}
			if(this.getHeadShip2() == null || this.getHeadShip2().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getHeadShip2());
			}
			if(this.getPhone2() == null || this.getPhone2().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getPhone2());
			}
			if(this.getE_Mail2() == null || this.getE_Mail2().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getE_Mail2());
			}
			if(this.getFax2() == null || this.getFax2().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getFax2());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getFax());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getPhone());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(35, 1);
			} else {
				pstmt.setString(35, this.getGetFlag());
			}
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getSatrap());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getEMail());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getFoundDate()));
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getBankAccNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getBankCode());
			}
			if(this.getGrpGroupNo() == null || this.getGrpGroupNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getGrpGroupNo());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getState());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getRemark());
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getBlacklistFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getModifyTime());
			}
			// set where condition
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getEdorType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
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
		SQLString sqlObj = new SQLString("LPAppntGrp");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPAppntGrp(EdorNo ,EdorType ,PolNo ,GrpNo ,RelationToInsured ,AppntGrade ,Password ,GrpName ,GrpAddressCode ,GrpAddress ,GrpZipCode ,BusinessType ,GrpNature ,Peoples ,RgtMoney ,Asset ,NetProfitRate ,MainBussiness ,Corporation ,ComAera ,LinkMan1 ,Department1 ,HeadShip1 ,Phone1 ,E_Mail1 ,Fax1 ,LinkMan2 ,Department2 ,HeadShip2 ,Phone2 ,E_Mail2 ,Fax2 ,Fax ,Phone ,GetFlag ,Satrap ,EMail ,FoundDate ,BankAccNo ,BankCode ,GrpGroupNo ,State ,Remark ,BlacklistFlag ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolNo());
			}
			if(this.getGrpNo() == null || this.getGrpNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpNo());
			}
			if(this.getRelationToInsured() == null || this.getRelationToInsured().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRelationToInsured());
			}
			if(this.getAppntGrade() == null || this.getAppntGrade().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getAppntGrade());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getPassword());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getGrpName());
			}
			if(this.getGrpAddressCode() == null || this.getGrpAddressCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getGrpAddressCode());
			}
			if(this.getGrpAddress() == null || this.getGrpAddress().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getGrpAddress());
			}
			if(this.getGrpZipCode() == null || this.getGrpZipCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getGrpZipCode());
			}
			if(this.getBusinessType() == null || this.getBusinessType().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getBusinessType());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getGrpNature());
			}
			pstmt.setInt(14, this.getPeoples());
			pstmt.setDouble(15, this.getRgtMoney());
			pstmt.setDouble(16, this.getAsset());
			pstmt.setDouble(17, this.getNetProfitRate());
			if(this.getMainBussiness() == null || this.getMainBussiness().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getMainBussiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getCorporation());
			}
			if(this.getComAera() == null || this.getComAera().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getComAera());
			}
			if(this.getLinkMan1() == null || this.getLinkMan1().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getLinkMan1());
			}
			if(this.getDepartment1() == null || this.getDepartment1().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getDepartment1());
			}
			if(this.getHeadShip1() == null || this.getHeadShip1().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getHeadShip1());
			}
			if(this.getPhone1() == null || this.getPhone1().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPhone1());
			}
			if(this.getE_Mail1() == null || this.getE_Mail1().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getE_Mail1());
			}
			if(this.getFax1() == null || this.getFax1().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getFax1());
			}
			if(this.getLinkMan2() == null || this.getLinkMan2().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getLinkMan2());
			}
			if(this.getDepartment2() == null || this.getDepartment2().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getDepartment2());
			}
			if(this.getHeadShip2() == null || this.getHeadShip2().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getHeadShip2());
			}
			if(this.getPhone2() == null || this.getPhone2().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getPhone2());
			}
			if(this.getE_Mail2() == null || this.getE_Mail2().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getE_Mail2());
			}
			if(this.getFax2() == null || this.getFax2().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getFax2());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getFax());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getPhone());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(35, 1);
			} else {
				pstmt.setString(35, this.getGetFlag());
			}
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getSatrap());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getEMail());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getFoundDate()));
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getBankAccNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getBankCode());
			}
			if(this.getGrpGroupNo() == null || this.getGrpGroupNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getGrpGroupNo());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getState());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getRemark());
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getBlacklistFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LPAppntGrp WHERE  EdorNo = ? AND EdorType = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
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
					tError.moduleName = "LPAppntGrpDB";
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
			tError.moduleName = "LPAppntGrpDB";
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

	public LPAppntGrpSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPAppntGrpSet aLPAppntGrpSet = new LPAppntGrpSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPAppntGrp");
			LPAppntGrpSchema aSchema = this.getSchema();
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
				LPAppntGrpSchema s1 = new LPAppntGrpSchema();
				s1.setSchema(rs,i);
				aLPAppntGrpSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
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

		return aLPAppntGrpSet;

	}

	public LPAppntGrpSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPAppntGrpSet aLPAppntGrpSet = new LPAppntGrpSet();

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
				LPAppntGrpSchema s1 = new LPAppntGrpSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPAppntGrpDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPAppntGrpSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
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

		return aLPAppntGrpSet;
	}

	public LPAppntGrpSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPAppntGrpSet aLPAppntGrpSet = new LPAppntGrpSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPAppntGrp");
			LPAppntGrpSchema aSchema = this.getSchema();
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

				LPAppntGrpSchema s1 = new LPAppntGrpSchema();
				s1.setSchema(rs,i);
				aLPAppntGrpSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
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

		return aLPAppntGrpSet;

	}

	public LPAppntGrpSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPAppntGrpSet aLPAppntGrpSet = new LPAppntGrpSet();

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

				LPAppntGrpSchema s1 = new LPAppntGrpSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPAppntGrpDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPAppntGrpSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
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

		return aLPAppntGrpSet;
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
			SQLString sqlObj = new SQLString("LPAppntGrp");
			LPAppntGrpSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LPAppntGrp " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPAppntGrpDB";
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
			tError.moduleName = "LPAppntGrpDB";
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
        tError.moduleName = "LPAppntGrpDB";
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
        tError.moduleName = "LPAppntGrpDB";
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
        tError.moduleName = "LPAppntGrpDB";
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
        tError.moduleName = "LPAppntGrpDB";
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
 * @return LPAppntGrpSet
 */
public LPAppntGrpSet getData()
{
    int tCount = 0;
    LPAppntGrpSet tLPAppntGrpSet = new LPAppntGrpSet();
    LPAppntGrpSchema tLPAppntGrpSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPAppntGrpDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLPAppntGrpSchema = new LPAppntGrpSchema();
        tLPAppntGrpSchema.setSchema(mResultSet, 1);
        tLPAppntGrpSet.add(tLPAppntGrpSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLPAppntGrpSchema = new LPAppntGrpSchema();
                tLPAppntGrpSchema.setSchema(mResultSet, 1);
                tLPAppntGrpSet.add(tLPAppntGrpSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPAppntGrpDB";
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
    return tLPAppntGrpSet;
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
            tError.moduleName = "LPAppntGrpDB";
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
        tError.moduleName = "LPAppntGrpDB";
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
            tError.moduleName = "LPAppntGrpDB";
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
        tError.moduleName = "LPAppntGrpDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LPAppntGrpSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPAppntGrpSet aLPAppntGrpSet = new LPAppntGrpSet();

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
				LPAppntGrpSchema s1 = new LPAppntGrpSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPAppntGrpDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPAppntGrpSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
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

		return aLPAppntGrpSet;
	}

	public LPAppntGrpSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPAppntGrpSet aLPAppntGrpSet = new LPAppntGrpSet();

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

				LPAppntGrpSchema s1 = new LPAppntGrpSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPAppntGrpDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPAppntGrpSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAppntGrpDB";
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

		return aLPAppntGrpSet; 
	}

}
