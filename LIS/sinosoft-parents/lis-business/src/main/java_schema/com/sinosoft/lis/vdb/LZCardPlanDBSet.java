/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LZCardPlanSchema;
import com.sinosoft.lis.vschema.LZCardPlanSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LZCardPlanDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LZCardPlanDBSet extends LZCardPlanSet
{
private static Logger logger = Logger.getLogger(LZCardPlanDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LZCardPlanDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LZCardPlan");
		mflag = true;
	}

	public LZCardPlanDBSet()
	{
		db = new DBOper( "LZCardPlan" );
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
			tError.moduleName = "LZCardPlanDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LZCardPlan WHERE  PlanID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPlanID() == null || this.get(i).getPlanID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPlanID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LZCardPlan");
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
			tError.moduleName = "LZCardPlanDBSet";
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
			pstmt = con.prepareStatement("UPDATE LZCardPlan SET  PlanID = ? , CertifyCode = ? , PlanType = ? , AppCount = ? , AppOperator = ? , AppCom = ? , ConCount = ? , ConOperator = ? , ConCom = ? , RetCount = ? , RetOperator = ? , RetCom = ? , RetState = ? , AssignCount = ? , RelaPlan = ? , RelaPrint = ? , MakeDate = ? , MakeTime = ? , UpdateDate = ? , UpdateTime = ? , PlanState = ? WHERE  PlanID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPlanID() == null || this.get(i).getPlanID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPlanID());
			}
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCertifyCode());
			}
			if(this.get(i).getPlanType() == null || this.get(i).getPlanType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPlanType());
			}
			pstmt.setInt(4, this.get(i).getAppCount());
			if(this.get(i).getAppOperator() == null || this.get(i).getAppOperator().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppOperator());
			}
			if(this.get(i).getAppCom() == null || this.get(i).getAppCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAppCom());
			}
			pstmt.setInt(7, this.get(i).getConCount());
			if(this.get(i).getConOperator() == null || this.get(i).getConOperator().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getConOperator());
			}
			if(this.get(i).getConCom() == null || this.get(i).getConCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getConCom());
			}
			pstmt.setInt(10, this.get(i).getRetCount());
			if(this.get(i).getRetOperator() == null || this.get(i).getRetOperator().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRetOperator());
			}
			if(this.get(i).getRetCom() == null || this.get(i).getRetCom().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRetCom());
			}
			if(this.get(i).getRetState() == null || this.get(i).getRetState().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRetState());
			}
			pstmt.setInt(14, this.get(i).getAssignCount());
			if(this.get(i).getRelaPlan() == null || this.get(i).getRelaPlan().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRelaPlan());
			}
			if(this.get(i).getRelaPrint() == null || this.get(i).getRelaPrint().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getRelaPrint());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getUpdateDate() == null || this.get(i).getUpdateDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getUpdateDate()));
			}
			if(this.get(i).getUpdateTime() == null || this.get(i).getUpdateTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getUpdateTime());
			}
			if(this.get(i).getPlanState() == null || this.get(i).getPlanState().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getPlanState());
			}
			// set where condition
			if(this.get(i).getPlanID() == null || this.get(i).getPlanID().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getPlanID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LZCardPlan");
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
			tError.moduleName = "LZCardPlanDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LZCardPlan(PlanID ,CertifyCode ,PlanType ,AppCount ,AppOperator ,AppCom ,ConCount ,ConOperator ,ConCom ,RetCount ,RetOperator ,RetCom ,RetState ,AssignCount ,RelaPlan ,RelaPrint ,MakeDate ,MakeTime ,UpdateDate ,UpdateTime ,PlanState) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPlanID() == null || this.get(i).getPlanID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPlanID());
			}
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCertifyCode());
			}
			if(this.get(i).getPlanType() == null || this.get(i).getPlanType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPlanType());
			}
			pstmt.setInt(4, this.get(i).getAppCount());
			if(this.get(i).getAppOperator() == null || this.get(i).getAppOperator().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppOperator());
			}
			if(this.get(i).getAppCom() == null || this.get(i).getAppCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAppCom());
			}
			pstmt.setInt(7, this.get(i).getConCount());
			if(this.get(i).getConOperator() == null || this.get(i).getConOperator().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getConOperator());
			}
			if(this.get(i).getConCom() == null || this.get(i).getConCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getConCom());
			}
			pstmt.setInt(10, this.get(i).getRetCount());
			if(this.get(i).getRetOperator() == null || this.get(i).getRetOperator().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRetOperator());
			}
			if(this.get(i).getRetCom() == null || this.get(i).getRetCom().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRetCom());
			}
			if(this.get(i).getRetState() == null || this.get(i).getRetState().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRetState());
			}
			pstmt.setInt(14, this.get(i).getAssignCount());
			if(this.get(i).getRelaPlan() == null || this.get(i).getRelaPlan().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRelaPlan());
			}
			if(this.get(i).getRelaPrint() == null || this.get(i).getRelaPrint().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getRelaPrint());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getUpdateDate() == null || this.get(i).getUpdateDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getUpdateDate()));
			}
			if(this.get(i).getUpdateTime() == null || this.get(i).getUpdateTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getUpdateTime());
			}
			if(this.get(i).getPlanState() == null || this.get(i).getPlanState().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getPlanState());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LZCardPlan");
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
			tError.moduleName = "LZCardPlanDBSet";
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
