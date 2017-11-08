/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBMissionDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LBMissionDBSet extends LBMissionSet
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
	public LBMissionDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBMission");
		mflag = true;
	}

	public LBMissionDBSet()
	{
		db = new DBOper( "LBMission" );
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
			tError.moduleName = "LBMissionDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBMission WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBMission");
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
			tError.moduleName = "LBMissionDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBMission SET  SerialNo = ? , MissionID = ? , SubMissionID = ? , ProcessID = ? , ActivityID = ? , ActivityStatus = ? , MissionProp1 = ? , MissionProp2 = ? , MissionProp3 = ? , MissionProp4 = ? , MissionProp5 = ? , MissionProp6 = ? , MissionProp7 = ? , MissionProp8 = ? , MissionProp9 = ? , MissionProp10 = ? , MissionProp11 = ? , MissionProp12 = ? , MissionProp13 = ? , MissionProp14 = ? , MissionProp15 = ? , MissionProp16 = ? , MissionProp17 = ? , MissionProp18 = ? , MissionProp19 = ? , MissionProp20 = ? , DefaultOperator = ? , LastOperator = ? , CreateOperator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , InDate = ? , InTime = ? , OutDate = ? , OutTime = ? , MissionProp21 = ? , MissionProp22 = ? , MissionProp23 = ? , MissionProp24 = ? , MissionProp25 = ? , TimeID = ? , StandEndDate = ? , StandEndTime = ? , OperateCom = ? , MainMissionID = ? , SQLPriorityID = ? , PriorityID = ? , VERSION = ? WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			if(this.get(i).getMissionID() == null || this.get(i).getMissionID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getMissionID());
			}
			if(this.get(i).getSubMissionID() == null || this.get(i).getSubMissionID().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSubMissionID());
			}
			if(this.get(i).getProcessID() == null || this.get(i).getProcessID().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getProcessID());
			}
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getActivityID());
			}
			if(this.get(i).getActivityStatus() == null || this.get(i).getActivityStatus().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getActivityStatus());
			}
			if(this.get(i).getMissionProp1() == null || this.get(i).getMissionProp1().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getMissionProp1());
			}
			if(this.get(i).getMissionProp2() == null || this.get(i).getMissionProp2().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMissionProp2());
			}
			if(this.get(i).getMissionProp3() == null || this.get(i).getMissionProp3().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMissionProp3());
			}
			if(this.get(i).getMissionProp4() == null || this.get(i).getMissionProp4().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMissionProp4());
			}
			if(this.get(i).getMissionProp5() == null || this.get(i).getMissionProp5().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMissionProp5());
			}
			if(this.get(i).getMissionProp6() == null || this.get(i).getMissionProp6().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getMissionProp6());
			}
			if(this.get(i).getMissionProp7() == null || this.get(i).getMissionProp7().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getMissionProp7());
			}
			if(this.get(i).getMissionProp8() == null || this.get(i).getMissionProp8().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getMissionProp8());
			}
			if(this.get(i).getMissionProp9() == null || this.get(i).getMissionProp9().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMissionProp9());
			}
			if(this.get(i).getMissionProp10() == null || this.get(i).getMissionProp10().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getMissionProp10());
			}
			if(this.get(i).getMissionProp11() == null || this.get(i).getMissionProp11().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getMissionProp11());
			}
			if(this.get(i).getMissionProp12() == null || this.get(i).getMissionProp12().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMissionProp12());
			}
			if(this.get(i).getMissionProp13() == null || this.get(i).getMissionProp13().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getMissionProp13());
			}
			if(this.get(i).getMissionProp14() == null || this.get(i).getMissionProp14().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getMissionProp14());
			}
			if(this.get(i).getMissionProp15() == null || this.get(i).getMissionProp15().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMissionProp15());
			}
			if(this.get(i).getMissionProp16() == null || this.get(i).getMissionProp16().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMissionProp16());
			}
			if(this.get(i).getMissionProp17() == null || this.get(i).getMissionProp17().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getMissionProp17());
			}
			if(this.get(i).getMissionProp18() == null || this.get(i).getMissionProp18().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getMissionProp18());
			}
			if(this.get(i).getMissionProp19() == null || this.get(i).getMissionProp19().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getMissionProp19());
			}
			if(this.get(i).getMissionProp20() == null || this.get(i).getMissionProp20().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getMissionProp20());
			}
			if(this.get(i).getDefaultOperator() == null || this.get(i).getDefaultOperator().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getDefaultOperator());
			}
			if(this.get(i).getLastOperator() == null || this.get(i).getLastOperator().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getLastOperator());
			}
			if(this.get(i).getCreateOperator() == null || this.get(i).getCreateOperator().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCreateOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getModifyTime());
			}
			if(this.get(i).getInDate() == null || this.get(i).getInDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getInDate()));
			}
			if(this.get(i).getInTime() == null || this.get(i).getInTime().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getInTime());
			}
			if(this.get(i).getOutDate() == null || this.get(i).getOutDate().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getOutDate()));
			}
			if(this.get(i).getOutTime() == null || this.get(i).getOutTime().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOutTime());
			}
			if(this.get(i).getMissionProp21() == null || this.get(i).getMissionProp21().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getMissionProp21());
			}
			if(this.get(i).getMissionProp22() == null || this.get(i).getMissionProp22().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getMissionProp22());
			}
			if(this.get(i).getMissionProp23() == null || this.get(i).getMissionProp23().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getMissionProp23());
			}
			if(this.get(i).getMissionProp24() == null || this.get(i).getMissionProp24().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getMissionProp24());
			}
			if(this.get(i).getMissionProp25() == null || this.get(i).getMissionProp25().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getMissionProp25());
			}
			if(this.get(i).getTimeID() == null || this.get(i).getTimeID().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getTimeID());
			}
			if(this.get(i).getStandEndDate() == null || this.get(i).getStandEndDate().equals("null")) {
				pstmt.setDate(44,null);
			} else {
				pstmt.setDate(44, Date.valueOf(this.get(i).getStandEndDate()));
			}
			if(this.get(i).getStandEndTime() == null || this.get(i).getStandEndTime().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getStandEndTime());
			}
			if(this.get(i).getOperateCom() == null || this.get(i).getOperateCom().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getOperateCom());
			}
			if(this.get(i).getMainMissionID() == null || this.get(i).getMainMissionID().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getMainMissionID());
			}
			if(this.get(i).getSQLPriorityID() == null || this.get(i).getSQLPriorityID().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getSQLPriorityID());
			}
			if(this.get(i).getPriorityID() == null || this.get(i).getPriorityID().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getPriorityID());
			}
			if(this.get(i).getVERSION() == null || this.get(i).getVERSION().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getVERSION());
			}
			// set where condition
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBMission");
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
			tError.moduleName = "LBMissionDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBMission VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			if(this.get(i).getMissionID() == null || this.get(i).getMissionID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getMissionID());
			}
			if(this.get(i).getSubMissionID() == null || this.get(i).getSubMissionID().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSubMissionID());
			}
			if(this.get(i).getProcessID() == null || this.get(i).getProcessID().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getProcessID());
			}
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getActivityID());
			}
			if(this.get(i).getActivityStatus() == null || this.get(i).getActivityStatus().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getActivityStatus());
			}
			if(this.get(i).getMissionProp1() == null || this.get(i).getMissionProp1().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getMissionProp1());
			}
			if(this.get(i).getMissionProp2() == null || this.get(i).getMissionProp2().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMissionProp2());
			}
			if(this.get(i).getMissionProp3() == null || this.get(i).getMissionProp3().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMissionProp3());
			}
			if(this.get(i).getMissionProp4() == null || this.get(i).getMissionProp4().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMissionProp4());
			}
			if(this.get(i).getMissionProp5() == null || this.get(i).getMissionProp5().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMissionProp5());
			}
			if(this.get(i).getMissionProp6() == null || this.get(i).getMissionProp6().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getMissionProp6());
			}
			if(this.get(i).getMissionProp7() == null || this.get(i).getMissionProp7().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getMissionProp7());
			}
			if(this.get(i).getMissionProp8() == null || this.get(i).getMissionProp8().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getMissionProp8());
			}
			if(this.get(i).getMissionProp9() == null || this.get(i).getMissionProp9().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMissionProp9());
			}
			if(this.get(i).getMissionProp10() == null || this.get(i).getMissionProp10().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getMissionProp10());
			}
			if(this.get(i).getMissionProp11() == null || this.get(i).getMissionProp11().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getMissionProp11());
			}
			if(this.get(i).getMissionProp12() == null || this.get(i).getMissionProp12().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMissionProp12());
			}
			if(this.get(i).getMissionProp13() == null || this.get(i).getMissionProp13().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getMissionProp13());
			}
			if(this.get(i).getMissionProp14() == null || this.get(i).getMissionProp14().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getMissionProp14());
			}
			if(this.get(i).getMissionProp15() == null || this.get(i).getMissionProp15().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMissionProp15());
			}
			if(this.get(i).getMissionProp16() == null || this.get(i).getMissionProp16().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMissionProp16());
			}
			if(this.get(i).getMissionProp17() == null || this.get(i).getMissionProp17().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getMissionProp17());
			}
			if(this.get(i).getMissionProp18() == null || this.get(i).getMissionProp18().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getMissionProp18());
			}
			if(this.get(i).getMissionProp19() == null || this.get(i).getMissionProp19().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getMissionProp19());
			}
			if(this.get(i).getMissionProp20() == null || this.get(i).getMissionProp20().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getMissionProp20());
			}
			if(this.get(i).getDefaultOperator() == null || this.get(i).getDefaultOperator().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getDefaultOperator());
			}
			if(this.get(i).getLastOperator() == null || this.get(i).getLastOperator().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getLastOperator());
			}
			if(this.get(i).getCreateOperator() == null || this.get(i).getCreateOperator().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCreateOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getModifyTime());
			}
			if(this.get(i).getInDate() == null || this.get(i).getInDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getInDate()));
			}
			if(this.get(i).getInTime() == null || this.get(i).getInTime().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getInTime());
			}
			if(this.get(i).getOutDate() == null || this.get(i).getOutDate().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getOutDate()));
			}
			if(this.get(i).getOutTime() == null || this.get(i).getOutTime().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOutTime());
			}
			if(this.get(i).getMissionProp21() == null || this.get(i).getMissionProp21().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getMissionProp21());
			}
			if(this.get(i).getMissionProp22() == null || this.get(i).getMissionProp22().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getMissionProp22());
			}
			if(this.get(i).getMissionProp23() == null || this.get(i).getMissionProp23().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getMissionProp23());
			}
			if(this.get(i).getMissionProp24() == null || this.get(i).getMissionProp24().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getMissionProp24());
			}
			if(this.get(i).getMissionProp25() == null || this.get(i).getMissionProp25().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getMissionProp25());
			}
			if(this.get(i).getTimeID() == null || this.get(i).getTimeID().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getTimeID());
			}
			if(this.get(i).getStandEndDate() == null || this.get(i).getStandEndDate().equals("null")) {
				pstmt.setDate(44,null);
			} else {
				pstmt.setDate(44, Date.valueOf(this.get(i).getStandEndDate()));
			}
			if(this.get(i).getStandEndTime() == null || this.get(i).getStandEndTime().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getStandEndTime());
			}
			if(this.get(i).getOperateCom() == null || this.get(i).getOperateCom().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getOperateCom());
			}
			if(this.get(i).getMainMissionID() == null || this.get(i).getMainMissionID().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getMainMissionID());
			}
			if(this.get(i).getSQLPriorityID() == null || this.get(i).getSQLPriorityID().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getSQLPriorityID());
			}
			if(this.get(i).getPriorityID() == null || this.get(i).getPriorityID().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getPriorityID());
			}
			if(this.get(i).getVERSION() == null || this.get(i).getVERSION().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getVERSION());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBMission");
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
			tError.moduleName = "LBMissionDBSet";
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
