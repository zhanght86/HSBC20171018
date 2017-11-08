package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LOPRTManagerDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LOPRTManagerDBSet extends LOPRTManagerSet
{
private static Logger logger = Logger.getLogger(LOPRTManagerDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LOPRTManagerDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LOPRTManager");
		mflag = true;
	}

	public LOPRTManagerDBSet()
	{
		db = new DBOper( "LOPRTManager" );
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
			tError.moduleName = "LOPRTManagerDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LOPRTManager WHERE  PrtSeq = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPrtSeq() == null || this.get(i).getPrtSeq().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPrtSeq());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOPRTManager");
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
			tError.moduleName = "LOPRTManagerDBSet";
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
			pstmt = con.prepareStatement("UPDATE LOPRTManager SET  PrtSeq = ? , OtherNo = ? , OtherNoType = ? , Code = ? , ManageCom = ? , AgentCode = ? , ReqCom = ? , ReqOperator = ? , ExeCom = ? , ExeOperator = ? , PrtType = ? , StateFlag = ? , MakeDate = ? , MakeTime = ? , DoneDate = ? , DoneTime = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , OldPrtSeq = ? , PatchFlag = ? , StandbyFlag3 = ? , StandbyFlag4 = ? , ForMakeDate = ? , ForMakeTime = ? , ActMakeDate = ? , ActMakeTime = ? , Remark = ? , StandbyFlag5 = ? , StandbyFlag6 = ? , StandbyFlag7 = ? WHERE  PrtSeq = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPrtSeq() == null || this.get(i).getPrtSeq().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPrtSeq());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOtherNoType());
			}
			if(this.get(i).getCode() == null || this.get(i).getCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCode());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAgentCode());
			}
			if(this.get(i).getReqCom() == null || this.get(i).getReqCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getReqCom());
			}
			if(this.get(i).getReqOperator() == null || this.get(i).getReqOperator().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getReqOperator());
			}
			if(this.get(i).getExeCom() == null || this.get(i).getExeCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getExeCom());
			}
			if(this.get(i).getExeOperator() == null || this.get(i).getExeOperator().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getExeOperator());
			}
			if(this.get(i).getPrtType() == null || this.get(i).getPrtType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getPrtType());
			}
			if(this.get(i).getStateFlag() == null || this.get(i).getStateFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getStateFlag());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getMakeTime());
			}
			if(this.get(i).getDoneDate() == null || this.get(i).getDoneDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getDoneDate()));
			}
			if(this.get(i).getDoneTime() == null || this.get(i).getDoneTime().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getDoneTime());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getOldPrtSeq() == null || this.get(i).getOldPrtSeq().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getOldPrtSeq());
			}
			if(this.get(i).getPatchFlag() == null || this.get(i).getPatchFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPatchFlag());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getStandbyFlag4() == null || this.get(i).getStandbyFlag4().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getStandbyFlag4());
			}
			if(this.get(i).getForMakeDate() == null || this.get(i).getForMakeDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getForMakeDate()));
			}
			if(this.get(i).getForMakeTime() == null || this.get(i).getForMakeTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getForMakeTime());
			}
			if(this.get(i).getActMakeDate() == null || this.get(i).getActMakeDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getActMakeDate()));
			}
			if(this.get(i).getActMakeTime() == null || this.get(i).getActMakeTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getActMakeTime());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getRemark());
			}
			if(this.get(i).getStandbyFlag5() == null || this.get(i).getStandbyFlag5().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getStandbyFlag5());
			}
			if(this.get(i).getStandbyFlag6() == null || this.get(i).getStandbyFlag6().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getStandbyFlag6());
			}
			if(this.get(i).getStandbyFlag7() == null || this.get(i).getStandbyFlag7().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getStandbyFlag7());
			}
			// set where condition
			if(this.get(i).getPrtSeq() == null || this.get(i).getPrtSeq().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getPrtSeq());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOPRTManager");
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
			tError.moduleName = "LOPRTManagerDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LOPRTManager(PrtSeq ,OtherNo ,OtherNoType ,Code ,ManageCom ,AgentCode ,ReqCom ,ReqOperator ,ExeCom ,ExeOperator ,PrtType ,StateFlag ,MakeDate ,MakeTime ,DoneDate ,DoneTime ,StandbyFlag1 ,StandbyFlag2 ,OldPrtSeq ,PatchFlag ,StandbyFlag3 ,StandbyFlag4 ,ForMakeDate ,ForMakeTime ,ActMakeDate ,ActMakeTime ,Remark ,StandbyFlag5 ,StandbyFlag6 ,StandbyFlag7) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPrtSeq() == null || this.get(i).getPrtSeq().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPrtSeq());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOtherNoType());
			}
			if(this.get(i).getCode() == null || this.get(i).getCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCode());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAgentCode());
			}
			if(this.get(i).getReqCom() == null || this.get(i).getReqCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getReqCom());
			}
			if(this.get(i).getReqOperator() == null || this.get(i).getReqOperator().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getReqOperator());
			}
			if(this.get(i).getExeCom() == null || this.get(i).getExeCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getExeCom());
			}
			if(this.get(i).getExeOperator() == null || this.get(i).getExeOperator().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getExeOperator());
			}
			if(this.get(i).getPrtType() == null || this.get(i).getPrtType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getPrtType());
			}
			if(this.get(i).getStateFlag() == null || this.get(i).getStateFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getStateFlag());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getMakeTime());
			}
			if(this.get(i).getDoneDate() == null || this.get(i).getDoneDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getDoneDate()));
			}
			if(this.get(i).getDoneTime() == null || this.get(i).getDoneTime().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getDoneTime());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getOldPrtSeq() == null || this.get(i).getOldPrtSeq().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getOldPrtSeq());
			}
			if(this.get(i).getPatchFlag() == null || this.get(i).getPatchFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPatchFlag());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getStandbyFlag4() == null || this.get(i).getStandbyFlag4().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getStandbyFlag4());
			}
			if(this.get(i).getForMakeDate() == null || this.get(i).getForMakeDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getForMakeDate()));
			}
			if(this.get(i).getForMakeTime() == null || this.get(i).getForMakeTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getForMakeTime());
			}
			if(this.get(i).getActMakeDate() == null || this.get(i).getActMakeDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getActMakeDate()));
			}
			if(this.get(i).getActMakeTime() == null || this.get(i).getActMakeTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getActMakeTime());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getRemark());
			}
			if(this.get(i).getStandbyFlag5() == null || this.get(i).getStandbyFlag5().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getStandbyFlag5());
			}
			if(this.get(i).getStandbyFlag6() == null || this.get(i).getStandbyFlag6().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getStandbyFlag6());
			}
			if(this.get(i).getStandbyFlag7() == null || this.get(i).getStandbyFlag7().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getStandbyFlag7());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOPRTManager");
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
			tError.moduleName = "LOPRTManagerDBSet";
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
