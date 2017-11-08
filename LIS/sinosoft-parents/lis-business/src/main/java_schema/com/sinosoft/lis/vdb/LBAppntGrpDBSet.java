/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBAppntGrpSchema;
import com.sinosoft.lis.vschema.LBAppntGrpSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBAppntGrpDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LBAppntGrpDBSet extends LBAppntGrpSet
{
private static Logger logger = Logger.getLogger(LBAppntGrpDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LBAppntGrpDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBAppntGrp");
		mflag = true;
	}

	public LBAppntGrpDBSet()
	{
		db = new DBOper( "LBAppntGrp" );
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
			tError.moduleName = "LBAppntGrpDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBAppntGrp WHERE  PolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBAppntGrp");
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
			tError.moduleName = "LBAppntGrpDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBAppntGrp SET  EdorNo = ? , PolNo = ? , GrpNo = ? , RelationToInsured = ? , AppntGrade = ? , Password = ? , GrpName = ? , GrpAddressCode = ? , GrpAddress = ? , GrpZipCode = ? , BusinessType = ? , GrpNature = ? , Peoples = ? , RgtMoney = ? , Asset = ? , NetProfitRate = ? , MainBussiness = ? , Corporation = ? , ComAera = ? , LinkMan1 = ? , Department1 = ? , HeadShip1 = ? , Phone1 = ? , E_Mail1 = ? , Fax1 = ? , LinkMan2 = ? , Department2 = ? , HeadShip2 = ? , Phone2 = ? , E_Mail2 = ? , Fax2 = ? , Fax = ? , Phone = ? , GetFlag = ? , Satrap = ? , EMail = ? , FoundDate = ? , BankAccNo = ? , BankCode = ? , GrpGroupNo = ? , State = ? , Remark = ? , BlacklistFlag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  PolNo = ?");
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
			if(this.get(i).getGrpNo() == null || this.get(i).getGrpNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpNo());
			}
			if(this.get(i).getRelationToInsured() == null || this.get(i).getRelationToInsured().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRelationToInsured());
			}
			if(this.get(i).getAppntGrade() == null || this.get(i).getAppntGrade().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppntGrade());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPassword());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGrpName());
			}
			if(this.get(i).getGrpAddressCode() == null || this.get(i).getGrpAddressCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getGrpAddressCode());
			}
			if(this.get(i).getGrpAddress() == null || this.get(i).getGrpAddress().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getGrpAddress());
			}
			if(this.get(i).getGrpZipCode() == null || this.get(i).getGrpZipCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGrpZipCode());
			}
			if(this.get(i).getBusinessType() == null || this.get(i).getBusinessType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBusinessType());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getGrpNature());
			}
			pstmt.setInt(13, this.get(i).getPeoples());
			pstmt.setDouble(14, this.get(i).getRgtMoney());
			pstmt.setDouble(15, this.get(i).getAsset());
			pstmt.setDouble(16, this.get(i).getNetProfitRate());
			if(this.get(i).getMainBussiness() == null || this.get(i).getMainBussiness().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getMainBussiness());
			}
			if(this.get(i).getCorporation() == null || this.get(i).getCorporation().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCorporation());
			}
			if(this.get(i).getComAera() == null || this.get(i).getComAera().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getComAera());
			}
			if(this.get(i).getLinkMan1() == null || this.get(i).getLinkMan1().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getLinkMan1());
			}
			if(this.get(i).getDepartment1() == null || this.get(i).getDepartment1().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getDepartment1());
			}
			if(this.get(i).getHeadShip1() == null || this.get(i).getHeadShip1().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getHeadShip1());
			}
			if(this.get(i).getPhone1() == null || this.get(i).getPhone1().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getPhone1());
			}
			if(this.get(i).getE_Mail1() == null || this.get(i).getE_Mail1().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getE_Mail1());
			}
			if(this.get(i).getFax1() == null || this.get(i).getFax1().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getFax1());
			}
			if(this.get(i).getLinkMan2() == null || this.get(i).getLinkMan2().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getLinkMan2());
			}
			if(this.get(i).getDepartment2() == null || this.get(i).getDepartment2().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getDepartment2());
			}
			if(this.get(i).getHeadShip2() == null || this.get(i).getHeadShip2().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getHeadShip2());
			}
			if(this.get(i).getPhone2() == null || this.get(i).getPhone2().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getPhone2());
			}
			if(this.get(i).getE_Mail2() == null || this.get(i).getE_Mail2().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getE_Mail2());
			}
			if(this.get(i).getFax2() == null || this.get(i).getFax2().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getFax2());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getFax());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getPhone());
			}
			if(this.get(i).getGetFlag() == null || this.get(i).getGetFlag().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getGetFlag());
			}
			if(this.get(i).getSatrap() == null || this.get(i).getSatrap().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getSatrap());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getEMail());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getBankAccNo());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getBankCode());
			}
			if(this.get(i).getGrpGroupNo() == null || this.get(i).getGrpGroupNo().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getGrpGroupNo());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getState());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getRemark());
			}
			if(this.get(i).getBlacklistFlag() == null || this.get(i).getBlacklistFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getBlacklistFlag());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(45,null);
			} else {
				pstmt.setDate(45, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(47,null);
			} else {
				pstmt.setDate(47, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBAppntGrp");
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
			tError.moduleName = "LBAppntGrpDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBAppntGrp(EdorNo ,PolNo ,GrpNo ,RelationToInsured ,AppntGrade ,Password ,GrpName ,GrpAddressCode ,GrpAddress ,GrpZipCode ,BusinessType ,GrpNature ,Peoples ,RgtMoney ,Asset ,NetProfitRate ,MainBussiness ,Corporation ,ComAera ,LinkMan1 ,Department1 ,HeadShip1 ,Phone1 ,E_Mail1 ,Fax1 ,LinkMan2 ,Department2 ,HeadShip2 ,Phone2 ,E_Mail2 ,Fax2 ,Fax ,Phone ,GetFlag ,Satrap ,EMail ,FoundDate ,BankAccNo ,BankCode ,GrpGroupNo ,State ,Remark ,BlacklistFlag ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
			if(this.get(i).getGrpNo() == null || this.get(i).getGrpNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpNo());
			}
			if(this.get(i).getRelationToInsured() == null || this.get(i).getRelationToInsured().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRelationToInsured());
			}
			if(this.get(i).getAppntGrade() == null || this.get(i).getAppntGrade().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppntGrade());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPassword());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGrpName());
			}
			if(this.get(i).getGrpAddressCode() == null || this.get(i).getGrpAddressCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getGrpAddressCode());
			}
			if(this.get(i).getGrpAddress() == null || this.get(i).getGrpAddress().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getGrpAddress());
			}
			if(this.get(i).getGrpZipCode() == null || this.get(i).getGrpZipCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGrpZipCode());
			}
			if(this.get(i).getBusinessType() == null || this.get(i).getBusinessType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBusinessType());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getGrpNature());
			}
			pstmt.setInt(13, this.get(i).getPeoples());
			pstmt.setDouble(14, this.get(i).getRgtMoney());
			pstmt.setDouble(15, this.get(i).getAsset());
			pstmt.setDouble(16, this.get(i).getNetProfitRate());
			if(this.get(i).getMainBussiness() == null || this.get(i).getMainBussiness().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getMainBussiness());
			}
			if(this.get(i).getCorporation() == null || this.get(i).getCorporation().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCorporation());
			}
			if(this.get(i).getComAera() == null || this.get(i).getComAera().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getComAera());
			}
			if(this.get(i).getLinkMan1() == null || this.get(i).getLinkMan1().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getLinkMan1());
			}
			if(this.get(i).getDepartment1() == null || this.get(i).getDepartment1().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getDepartment1());
			}
			if(this.get(i).getHeadShip1() == null || this.get(i).getHeadShip1().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getHeadShip1());
			}
			if(this.get(i).getPhone1() == null || this.get(i).getPhone1().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getPhone1());
			}
			if(this.get(i).getE_Mail1() == null || this.get(i).getE_Mail1().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getE_Mail1());
			}
			if(this.get(i).getFax1() == null || this.get(i).getFax1().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getFax1());
			}
			if(this.get(i).getLinkMan2() == null || this.get(i).getLinkMan2().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getLinkMan2());
			}
			if(this.get(i).getDepartment2() == null || this.get(i).getDepartment2().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getDepartment2());
			}
			if(this.get(i).getHeadShip2() == null || this.get(i).getHeadShip2().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getHeadShip2());
			}
			if(this.get(i).getPhone2() == null || this.get(i).getPhone2().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getPhone2());
			}
			if(this.get(i).getE_Mail2() == null || this.get(i).getE_Mail2().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getE_Mail2());
			}
			if(this.get(i).getFax2() == null || this.get(i).getFax2().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getFax2());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getFax());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getPhone());
			}
			if(this.get(i).getGetFlag() == null || this.get(i).getGetFlag().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getGetFlag());
			}
			if(this.get(i).getSatrap() == null || this.get(i).getSatrap().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getSatrap());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getEMail());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getBankAccNo());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getBankCode());
			}
			if(this.get(i).getGrpGroupNo() == null || this.get(i).getGrpGroupNo().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getGrpGroupNo());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getState());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getRemark());
			}
			if(this.get(i).getBlacklistFlag() == null || this.get(i).getBlacklistFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getBlacklistFlag());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(45,null);
			} else {
				pstmt.setDate(45, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(47,null);
			} else {
				pstmt.setDate(47, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBAppntGrp");
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
			tError.moduleName = "LBAppntGrpDBSet";
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
