/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCInsureAccTraceBakSchema;
import com.sinosoft.lis.vschema.LCInsureAccTraceBakSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCInsureAccTraceBakDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LCInsureAccTraceBakDBSet extends LCInsureAccTraceBakSet
{
private static Logger logger = Logger.getLogger(LCInsureAccTraceBakDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LCInsureAccTraceBakDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCInsureAccTraceBak");
		mflag = true;
	}

	public LCInsureAccTraceBakDBSet()
	{
		db = new DBOper( "LCInsureAccTraceBak" );
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
			tError.moduleName = "LCInsureAccTraceBakDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCInsureAccTraceBak WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCInsureAccTraceBak");
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
			tError.moduleName = "LCInsureAccTraceBakDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCInsureAccTraceBak SET  GrpContNo = ? , GrpPolNo = ? , ContNo = ? , PolNo = ? , SerialNo = ? , InsuAccNo = ? , RiskCode = ? , PayPlanCode = ? , OtherNo = ? , OtherType = ? , AccAscription = ? , MoneyType = ? , Money = ? , UnitCount = ? , PayDate = ? , State = ? , ManageCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , FeeCode = ? , AccAlterNo = ? , AccAlterType = ? , PayNo = ? , EdorType = ? , BusyType = ? , ShouldValueDate = ? , ValueDate = ? , DutyCode = ? WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPolNo());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSerialNo());
			}
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskCode());
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getPayPlanCode());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherType() == null || this.get(i).getOtherType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getOtherType());
			}
			if(this.get(i).getAccAscription() == null || this.get(i).getAccAscription().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAccAscription());
			}
			if(this.get(i).getMoneyType() == null || this.get(i).getMoneyType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getMoneyType());
			}
			pstmt.setDouble(13, this.get(i).getMoney());
			pstmt.setDouble(14, this.get(i).getUnitCount());
			if(this.get(i).getPayDate() == null || this.get(i).getPayDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getPayDate()));
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getState());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getManageCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getModifyTime());
			}
			if(this.get(i).getFeeCode() == null || this.get(i).getFeeCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getFeeCode());
			}
			if(this.get(i).getAccAlterNo() == null || this.get(i).getAccAlterNo().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAccAlterNo());
			}
			if(this.get(i).getAccAlterType() == null || this.get(i).getAccAlterType().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAccAlterType());
			}
			if(this.get(i).getPayNo() == null || this.get(i).getPayNo().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getPayNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getEdorType());
			}
			if(this.get(i).getBusyType() == null || this.get(i).getBusyType().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getBusyType());
			}
			if(this.get(i).getShouldValueDate() == null || this.get(i).getShouldValueDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getShouldValueDate()));
			}
			if(this.get(i).getValueDate() == null || this.get(i).getValueDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getValueDate()));
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getDutyCode());
			}
			// set where condition
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCInsureAccTraceBak");
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
			tError.moduleName = "LCInsureAccTraceBakDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCInsureAccTraceBak(GrpContNo ,GrpPolNo ,ContNo ,PolNo ,SerialNo ,InsuAccNo ,RiskCode ,PayPlanCode ,OtherNo ,OtherType ,AccAscription ,MoneyType ,Money ,UnitCount ,PayDate ,State ,ManageCom ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,FeeCode ,AccAlterNo ,AccAlterType ,PayNo ,EdorType ,BusyType ,ShouldValueDate ,ValueDate ,DutyCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPolNo());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSerialNo());
			}
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskCode());
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getPayPlanCode());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherType() == null || this.get(i).getOtherType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getOtherType());
			}
			if(this.get(i).getAccAscription() == null || this.get(i).getAccAscription().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAccAscription());
			}
			if(this.get(i).getMoneyType() == null || this.get(i).getMoneyType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getMoneyType());
			}
			pstmt.setDouble(13, this.get(i).getMoney());
			pstmt.setDouble(14, this.get(i).getUnitCount());
			if(this.get(i).getPayDate() == null || this.get(i).getPayDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getPayDate()));
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getState());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getManageCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getModifyTime());
			}
			if(this.get(i).getFeeCode() == null || this.get(i).getFeeCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getFeeCode());
			}
			if(this.get(i).getAccAlterNo() == null || this.get(i).getAccAlterNo().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAccAlterNo());
			}
			if(this.get(i).getAccAlterType() == null || this.get(i).getAccAlterType().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAccAlterType());
			}
			if(this.get(i).getPayNo() == null || this.get(i).getPayNo().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getPayNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getEdorType());
			}
			if(this.get(i).getBusyType() == null || this.get(i).getBusyType().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getBusyType());
			}
			if(this.get(i).getShouldValueDate() == null || this.get(i).getShouldValueDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getShouldValueDate()));
			}
			if(this.get(i).getValueDate() == null || this.get(i).getValueDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getValueDate()));
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCInsureAccTraceBak");
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
			tError.moduleName = "LCInsureAccTraceBakDBSet";
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
