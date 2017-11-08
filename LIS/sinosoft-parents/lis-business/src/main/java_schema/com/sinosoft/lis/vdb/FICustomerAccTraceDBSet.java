package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import com.sinosoft.lis.vschema.FICustomerAccTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.SQLString;

/**
 * <p>
 * ClassName: FICustomerAccTraceDBSet
 * </p>
 * <p>
 * Description: 客户账户履历表（FICustomerAccTrace）的DBSet文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-12-30
 */
public class FICustomerAccTraceDBSet extends FICustomerAccTraceSet
{
private static Logger logger = Logger.getLogger(FICustomerAccTraceDBSet.class);

	// @Field
	private Connection con;

	private DBOper db;

	/**
	 * flag = true: 传入Connection flag = false: 不传入Connection
	 **/
	private boolean mflag = false;

	// @Constructor
	public FICustomerAccTraceDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con, "FICustomerAccTrace");
		mflag = true;
	}

	public FICustomerAccTraceDBSet()
	{
		db = new DBOper("FICustomerAccTrace");
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
			tError.moduleName = "FICustomerAccTraceDBSet";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 删除操作 删除条件：主键
	 * @return boolean
	 */
	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}

		try
		{
			int tCount = this.size();
			pstmt = con.prepareStatement("DELETE FROM FICustomerAccTrace WHERE  Sequence = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getSequence() == null || this.get(i).getSequence().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getSequence());
				}

				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
		}
		catch (Exception ex)
		{
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICustomerAccTraceDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("FICustomerAccTrace");
				sqlObj.printTrueSQL(this.get(i), "delete");
			}

			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
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
			{
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * 更新操作 更新条件：主键
	 * @return boolean
	 */
	public boolean update()
	{
		PreparedStatement pstmt = null;

		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}

		try
		{
			int tCount = this.size();
			pstmt = con.prepareStatement("UPDATE FICustomerAccTrace SET  Sequence = ? , InsuAccNo = ? , CustomerNo = ? , CustomerType = ? , ContNo = ? , OperationNo = ? , OperationType = ? , PayMode = ? , OperType = ? , OtherNo = ? , DCFlag = ? , Currency = ? , Money = ? , State = ? , ConfDate = ? , ConfTime = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , EnterAccDate = ? , EnterAccTime = ? WHERE  Sequence = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getSequence() == null || this.get(i).getSequence().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getSequence());
				}
				if (this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getInsuAccNo());
				}
				if (this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getCustomerNo());
				}
				if (this.get(i).getCustomerType() == null || this.get(i).getCustomerType().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getCustomerType());
				}
				if (this.get(i).getContNo() == null || this.get(i).getContNo().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getContNo());
				}
				if (this.get(i).getOperationNo() == null || this.get(i).getOperationNo().equals("null"))
				{
					pstmt.setString(6, null);
				}
				else
				{
					pstmt.setString(6, this.get(i).getOperationNo());
				}
				if (this.get(i).getOperationType() == null || this.get(i).getOperationType().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getOperationType());
				}
				if (this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null"))
				{
					pstmt.setString(8, null);
				}
				else
				{
					pstmt.setString(8, this.get(i).getPayMode());
				}
				if (this.get(i).getOperType() == null || this.get(i).getOperType().equals("null"))
				{
					pstmt.setString(9, null);
				}
				else
				{
					pstmt.setString(9, this.get(i).getOperType());
				}
				if (this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null"))
				{
					pstmt.setString(10, null);
				}
				else
				{
					pstmt.setString(10, this.get(i).getOtherNo());
				}
				if (this.get(i).getDCFlag() == null || this.get(i).getDCFlag().equals("null"))
				{
					pstmt.setString(11, null);
				}
				else
				{
					pstmt.setString(11, this.get(i).getDCFlag());
				}
				if (this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null"))
				{
					pstmt.setString(12, null);
				}
				else
				{
					pstmt.setString(12, this.get(i).getCurrency());
				}
				pstmt.setDouble(13, this.get(i).getMoney());
				if (this.get(i).getState() == null || this.get(i).getState().equals("null"))
				{
					pstmt.setString(14, null);
				}
				else
				{
					pstmt.setString(14, this.get(i).getState());
				}
				if (this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null"))
				{
					pstmt.setDate(15, null);
				}
				else
				{
					pstmt.setDate(15, Date.valueOf(this.get(i).getConfDate()));
				}
				if (this.get(i).getConfTime() == null || this.get(i).getConfTime().equals("null"))
				{
					pstmt.setString(16, null);
				}
				else
				{
					pstmt.setString(16, this.get(i).getConfTime());
				}
				if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
				{
					pstmt.setString(17, null);
				}
				else
				{
					pstmt.setString(17, this.get(i).getOperator());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(18, null);
				}
				else
				{
					pstmt.setDate(18, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(19, null);
				}
				else
				{
					pstmt.setString(19, this.get(i).getMakeTime());
				}
				if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
				{
					pstmt.setDate(20, null);
				}
				else
				{
					pstmt.setDate(20, Date.valueOf(this.get(i).getModifyDate()));
				}
				if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
				{
					pstmt.setString(21, null);
				}
				else
				{
					pstmt.setString(21, this.get(i).getModifyTime());
				}
				if (this.get(i).getEnterAccDate() == null || this.get(i).getEnterAccDate().equals("null"))
				{
					pstmt.setDate(22, null);
				}
				else
				{
					pstmt.setDate(22, Date.valueOf(this.get(i).getEnterAccDate()));
				}
				if (this.get(i).getEnterAccTime() == null || this.get(i).getEnterAccTime().equals("null"))
				{
					pstmt.setString(23, null);
				}
				else
				{
					pstmt.setString(23, this.get(i).getEnterAccTime());
				}
				// set where condition
				if (this.get(i).getSequence() == null || this.get(i).getSequence().equals("null"))
				{
					pstmt.setString(24, null);
				}
				else
				{
					pstmt.setString(24, this.get(i).getSequence());
				}

				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
		}
		catch (Exception ex)
		{
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICustomerAccTraceDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("FICustomerAccTrace");
				sqlObj.printTrueSQL(this.get(i), "update");
			}

			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
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
			{
				e.printStackTrace();
			}
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

		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}

		try
		{
			int tCount = this.size();
			pstmt = con.prepareStatement("INSERT INTO FICustomerAccTrace VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getSequence() == null || this.get(i).getSequence().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getSequence());
				}
				if (this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getInsuAccNo());
				}
				if (this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getCustomerNo());
				}
				if (this.get(i).getCustomerType() == null || this.get(i).getCustomerType().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getCustomerType());
				}
				if (this.get(i).getContNo() == null || this.get(i).getContNo().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getContNo());
				}
				if (this.get(i).getOperationNo() == null || this.get(i).getOperationNo().equals("null"))
				{
					pstmt.setString(6, null);
				}
				else
				{
					pstmt.setString(6, this.get(i).getOperationNo());
				}
				if (this.get(i).getOperationType() == null || this.get(i).getOperationType().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getOperationType());
				}
				if (this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null"))
				{
					pstmt.setString(8, null);
				}
				else
				{
					pstmt.setString(8, this.get(i).getPayMode());
				}
				if (this.get(i).getOperType() == null || this.get(i).getOperType().equals("null"))
				{
					pstmt.setString(9, null);
				}
				else
				{
					pstmt.setString(9, this.get(i).getOperType());
				}
				if (this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null"))
				{
					pstmt.setString(10, null);
				}
				else
				{
					pstmt.setString(10, this.get(i).getOtherNo());
				}
				if (this.get(i).getDCFlag() == null || this.get(i).getDCFlag().equals("null"))
				{
					pstmt.setString(11, null);
				}
				else
				{
					pstmt.setString(11, this.get(i).getDCFlag());
				}
				if (this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null"))
				{
					pstmt.setString(12, null);
				}
				else
				{
					pstmt.setString(12, this.get(i).getCurrency());
				}
				pstmt.setDouble(13, this.get(i).getMoney());
				if (this.get(i).getState() == null || this.get(i).getState().equals("null"))
				{
					pstmt.setString(14, null);
				}
				else
				{
					pstmt.setString(14, this.get(i).getState());
				}
				if (this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null"))
				{
					pstmt.setDate(15, null);
				}
				else
				{
					pstmt.setDate(15, Date.valueOf(this.get(i).getConfDate()));
				}
				if (this.get(i).getConfTime() == null || this.get(i).getConfTime().equals("null"))
				{
					pstmt.setString(16, null);
				}
				else
				{
					pstmt.setString(16, this.get(i).getConfTime());
				}
				if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
				{
					pstmt.setString(17, null);
				}
				else
				{
					pstmt.setString(17, this.get(i).getOperator());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(18, null);
				}
				else
				{
					pstmt.setDate(18, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(19, null);
				}
				else
				{
					pstmt.setString(19, this.get(i).getMakeTime());
				}
				if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
				{
					pstmt.setDate(20, null);
				}
				else
				{
					pstmt.setDate(20, Date.valueOf(this.get(i).getModifyDate()));
				}
				if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
				{
					pstmt.setString(21, null);
				}
				else
				{
					pstmt.setString(21, this.get(i).getModifyTime());
				}
				if (this.get(i).getEnterAccDate() == null || this.get(i).getEnterAccDate().equals("null"))
				{
					pstmt.setDate(22, null);
				}
				else
				{
					pstmt.setDate(22, Date.valueOf(this.get(i).getEnterAccDate()));
				}
				if (this.get(i).getEnterAccTime() == null || this.get(i).getEnterAccTime().equals("null"))
				{
					pstmt.setString(23, null);
				}
				else
				{
					pstmt.setString(23, this.get(i).getEnterAccTime());
				}

				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
		}
		catch (Exception ex)
		{
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICustomerAccTraceDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("FICustomerAccTrace");
				sqlObj.printTrueSQL(this.get(i), "insert");
			}

			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
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
			{
				e.printStackTrace();
			}
		}

		return true;
	}

}
