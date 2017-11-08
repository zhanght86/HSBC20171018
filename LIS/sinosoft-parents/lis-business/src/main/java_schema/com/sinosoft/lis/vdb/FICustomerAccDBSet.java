package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import com.sinosoft.lis.vschema.FICustomerAccSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.SQLString;

/**
 * <p>
 * ClassName: FICustomerAccDBSet
 * </p>
 * <p>
 * Description: 客户账户总表（FICustomerAcc）的DBSet文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-12-30
 */
public class FICustomerAccDBSet extends FICustomerAccSet
{
private static Logger logger = Logger.getLogger(FICustomerAccDBSet.class);

	// @Field
	private Connection con;

	private DBOper db;

	/**
	 * flag = true: 传入Connection flag = false: 不传入Connection
	 **/
	private boolean mflag = false;

	// @Constructor
	public FICustomerAccDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con, "FICustomerAcc");
		mflag = true;
	}

	public FICustomerAccDBSet()
	{
		db = new DBOper("FICustomerAcc");
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
			tError.moduleName = "FICustomerAccDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FICustomerAcc WHERE  InsuAccNo = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getInsuAccNo());
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
			tError.moduleName = "FICustomerAccDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("FICustomerAcc");
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
			pstmt = con.prepareStatement("UPDATE FICustomerAcc SET  InsuAccNo = ? , Currency = ? , CustomerNo = ? , CustomerType = ? , AccType = ? , Summoney = ? , State = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  InsuAccNo = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getInsuAccNo());
				}
				if (this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getCurrency());
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
				if (this.get(i).getAccType() == null || this.get(i).getAccType().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getAccType());
				}
				pstmt.setDouble(6, this.get(i).getSummoney());
				if (this.get(i).getState() == null || this.get(i).getState().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getState());
				}
				if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
				{
					pstmt.setString(8, null);
				}
				else
				{
					pstmt.setString(8, this.get(i).getOperator());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(9, null);
				}
				else
				{
					pstmt.setDate(9, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(10, null);
				}
				else
				{
					pstmt.setString(10, this.get(i).getMakeTime());
				}
				if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
				{
					pstmt.setDate(11, null);
				}
				else
				{
					pstmt.setDate(11, Date.valueOf(this.get(i).getModifyDate()));
				}
				if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
				{
					pstmt.setString(12, null);
				}
				else
				{
					pstmt.setString(12, this.get(i).getModifyTime());
				}
				// set where condition
				if (this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null"))
				{
					pstmt.setString(13, null);
				}
				else
				{
					pstmt.setString(13, this.get(i).getInsuAccNo());
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
			tError.moduleName = "FICustomerAccDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("FICustomerAcc");
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
			pstmt = con.prepareStatement("INSERT INTO FICustomerAcc VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getInsuAccNo());
				}
				if (this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getCurrency());
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
				if (this.get(i).getAccType() == null || this.get(i).getAccType().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getAccType());
				}
				pstmt.setDouble(6, this.get(i).getSummoney());
				if (this.get(i).getState() == null || this.get(i).getState().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getState());
				}
				if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
				{
					pstmt.setString(8, null);
				}
				else
				{
					pstmt.setString(8, this.get(i).getOperator());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(9, null);
				}
				else
				{
					pstmt.setDate(9, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(10, null);
				}
				else
				{
					pstmt.setString(10, this.get(i).getMakeTime());
				}
				if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
				{
					pstmt.setDate(11, null);
				}
				else
				{
					pstmt.setDate(11, Date.valueOf(this.get(i).getModifyDate()));
				}
				if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
				{
					pstmt.setString(12, null);
				}
				else
				{
					pstmt.setString(12, this.get(i).getModifyTime());
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
			tError.moduleName = "FICustomerAccDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("FICustomerAcc");
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
