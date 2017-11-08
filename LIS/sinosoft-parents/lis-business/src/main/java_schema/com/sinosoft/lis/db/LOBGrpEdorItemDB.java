/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LOBGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LOBGrpEdorItemSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LOBGrpEdorItemDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造业务表
 */
public class LOBGrpEdorItemDB extends LOBGrpEdorItemSchema
{
private static Logger logger = Logger.getLogger(LOBGrpEdorItemDB.class);

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
	public LOBGrpEdorItemDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LOBGrpEdorItem" );
		mflag = true;
	}

	public LOBGrpEdorItemDB()
	{
		con = null;
		db = new DBOper( "LOBGrpEdorItem" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LOBGrpEdorItemSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LOBGrpEdorItemSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
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
			pstmt = con.prepareStatement("DELETE FROM LOBGrpEdorItem WHERE  EdorAcceptNo = ? AND EdorNo = ? AND EdorType = ? AND GrpContNo = ?");
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpContNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOBGrpEdorItem");
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
		SQLString sqlObj = new SQLString("LOBGrpEdorItem");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LOBGrpEdorItem SET  EdorAcceptNo = ? , EdorNo = ? , EdorAppNo = ? , EdorType = ? , DisplayType = ? , GrpContNo = ? , ManageCom = ? , EdorValiDate = ? , EdorAppDate = ? , EdorState = ? , UWFlag = ? , UWOperator = ? , UWDate = ? , UWTime = ? , ChgPrem = ? , ChgAmnt = ? , GetMoney = ? , GetInterest = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Reason = ? , ReasonCode = ? , AppReason = ? , EdorReasonCode = ? , EdorReason = ? , ApproveFlag = ? , ApproveOperator = ? , ApproveDate = ? , ApproveTime = ? , EdorTypeCal = ? , Currency = ? WHERE  EdorAcceptNo = ? AND EdorNo = ? AND EdorType = ? AND GrpContNo = ?");
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorNo());
			}
			if(this.getEdorAppNo() == null || this.getEdorAppNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getEdorAppNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getEdorType());
			}
			if(this.getDisplayType() == null || this.getDisplayType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getDisplayType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getGrpContNo());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getManageCom());
			}
			if(this.getEdorValiDate() == null || this.getEdorValiDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getEdorValiDate()));
			}
			if(this.getEdorAppDate() == null || this.getEdorAppDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getEdorAppDate()));
			}
			if(this.getEdorState() == null || this.getEdorState().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getEdorState());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getUWFlag());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getUWTime());
			}
			pstmt.setDouble(15, this.getChgPrem());
			pstmt.setDouble(16, this.getChgAmnt());
			pstmt.setDouble(17, this.getGetMoney());
			pstmt.setDouble(18, this.getGetInterest());
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getModifyTime());
			}
			if(this.getReason() == null || this.getReason().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getReason());
			}
			if(this.getReasonCode() == null || this.getReasonCode().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getReasonCode());
			}
			if(this.getAppReason() == null || this.getAppReason().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getAppReason());
			}
			if(this.getEdorReasonCode() == null || this.getEdorReasonCode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getEdorReasonCode());
			}
			if(this.getEdorReason() == null || this.getEdorReason().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getEdorReason());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getApproveFlag());
			}
			if(this.getApproveOperator() == null || this.getApproveOperator().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getApproveOperator());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getApproveTime());
			}
			if(this.getEdorTypeCal() == null || this.getEdorTypeCal().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getEdorTypeCal());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getCurrency());
			}
			// set where condition
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getEdorAcceptNo());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getGrpContNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
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
		SQLString sqlObj = new SQLString("LOBGrpEdorItem");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LOBGrpEdorItem(EdorAcceptNo ,EdorNo ,EdorAppNo ,EdorType ,DisplayType ,GrpContNo ,ManageCom ,EdorValiDate ,EdorAppDate ,EdorState ,UWFlag ,UWOperator ,UWDate ,UWTime ,ChgPrem ,ChgAmnt ,GetMoney ,GetInterest ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Reason ,ReasonCode ,AppReason ,EdorReasonCode ,EdorReason ,ApproveFlag ,ApproveOperator ,ApproveDate ,ApproveTime ,EdorTypeCal ,Currency) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorNo());
			}
			if(this.getEdorAppNo() == null || this.getEdorAppNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getEdorAppNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getEdorType());
			}
			if(this.getDisplayType() == null || this.getDisplayType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getDisplayType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getGrpContNo());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getManageCom());
			}
			if(this.getEdorValiDate() == null || this.getEdorValiDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getEdorValiDate()));
			}
			if(this.getEdorAppDate() == null || this.getEdorAppDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getEdorAppDate()));
			}
			if(this.getEdorState() == null || this.getEdorState().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getEdorState());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getUWFlag());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getUWTime());
			}
			pstmt.setDouble(15, this.getChgPrem());
			pstmt.setDouble(16, this.getChgAmnt());
			pstmt.setDouble(17, this.getGetMoney());
			pstmt.setDouble(18, this.getGetInterest());
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getModifyTime());
			}
			if(this.getReason() == null || this.getReason().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getReason());
			}
			if(this.getReasonCode() == null || this.getReasonCode().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getReasonCode());
			}
			if(this.getAppReason() == null || this.getAppReason().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getAppReason());
			}
			if(this.getEdorReasonCode() == null || this.getEdorReasonCode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getEdorReasonCode());
			}
			if(this.getEdorReason() == null || this.getEdorReason().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getEdorReason());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getApproveFlag());
			}
			if(this.getApproveOperator() == null || this.getApproveOperator().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getApproveOperator());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getApproveTime());
			}
			if(this.getEdorTypeCal() == null || this.getEdorTypeCal().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getEdorTypeCal());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getCurrency());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LOBGrpEdorItem WHERE  EdorAcceptNo = ? AND EdorNo = ? AND EdorType = ? AND GrpContNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpContNo());
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
					tError.moduleName = "LOBGrpEdorItemDB";
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
			tError.moduleName = "LOBGrpEdorItemDB";
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

	public LOBGrpEdorItemSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LOBGrpEdorItemSet aLOBGrpEdorItemSet = new LOBGrpEdorItemSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LOBGrpEdorItem");
			LOBGrpEdorItemSchema aSchema = this.getSchema();
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
				LOBGrpEdorItemSchema s1 = new LOBGrpEdorItemSchema();
				s1.setSchema(rs,i);
				aLOBGrpEdorItemSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
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

		return aLOBGrpEdorItemSet;

	}

	public LOBGrpEdorItemSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LOBGrpEdorItemSet aLOBGrpEdorItemSet = new LOBGrpEdorItemSet();

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
				LOBGrpEdorItemSchema s1 = new LOBGrpEdorItemSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LOBGrpEdorItemDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLOBGrpEdorItemSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
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

		return aLOBGrpEdorItemSet;
	}

	public LOBGrpEdorItemSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LOBGrpEdorItemSet aLOBGrpEdorItemSet = new LOBGrpEdorItemSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LOBGrpEdorItem");
			LOBGrpEdorItemSchema aSchema = this.getSchema();
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

				LOBGrpEdorItemSchema s1 = new LOBGrpEdorItemSchema();
				s1.setSchema(rs,i);
				aLOBGrpEdorItemSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
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

		return aLOBGrpEdorItemSet;

	}

	public LOBGrpEdorItemSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LOBGrpEdorItemSet aLOBGrpEdorItemSet = new LOBGrpEdorItemSet();

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

				LOBGrpEdorItemSchema s1 = new LOBGrpEdorItemSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LOBGrpEdorItemDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLOBGrpEdorItemSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
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

		return aLOBGrpEdorItemSet;
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
			SQLString sqlObj = new SQLString("LOBGrpEdorItem");
			LOBGrpEdorItemSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LOBGrpEdorItem " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LOBGrpEdorItemDB";
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
			tError.moduleName = "LOBGrpEdorItemDB";
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
        tError.moduleName = "LOBGrpEdorItemDB";
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
        tError.moduleName = "LOBGrpEdorItemDB";
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
        tError.moduleName = "LOBGrpEdorItemDB";
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
        tError.moduleName = "LOBGrpEdorItemDB";
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
 * @return LOBGrpEdorItemSet
 */
public LOBGrpEdorItemSet getData()
{
    int tCount = 0;
    LOBGrpEdorItemSet tLOBGrpEdorItemSet = new LOBGrpEdorItemSet();
    LOBGrpEdorItemSchema tLOBGrpEdorItemSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LOBGrpEdorItemDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLOBGrpEdorItemSchema = new LOBGrpEdorItemSchema();
        tLOBGrpEdorItemSchema.setSchema(mResultSet, 1);
        tLOBGrpEdorItemSet.add(tLOBGrpEdorItemSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLOBGrpEdorItemSchema = new LOBGrpEdorItemSchema();
                tLOBGrpEdorItemSchema.setSchema(mResultSet, 1);
                tLOBGrpEdorItemSet.add(tLOBGrpEdorItemSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LOBGrpEdorItemDB";
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
    return tLOBGrpEdorItemSet;
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
            tError.moduleName = "LOBGrpEdorItemDB";
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
        tError.moduleName = "LOBGrpEdorItemDB";
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
            tError.moduleName = "LOBGrpEdorItemDB";
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
        tError.moduleName = "LOBGrpEdorItemDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LOBGrpEdorItemSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LOBGrpEdorItemSet aLOBGrpEdorItemSet = new LOBGrpEdorItemSet();

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
				LOBGrpEdorItemSchema s1 = new LOBGrpEdorItemSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LOBGrpEdorItemDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLOBGrpEdorItemSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
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

		return aLOBGrpEdorItemSet;
	}

	public LOBGrpEdorItemSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LOBGrpEdorItemSet aLOBGrpEdorItemSet = new LOBGrpEdorItemSet();

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

				LOBGrpEdorItemSchema s1 = new LOBGrpEdorItemSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LOBGrpEdorItemDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLOBGrpEdorItemSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemDB";
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

		return aLOBGrpEdorItemSet; 
	}

}
