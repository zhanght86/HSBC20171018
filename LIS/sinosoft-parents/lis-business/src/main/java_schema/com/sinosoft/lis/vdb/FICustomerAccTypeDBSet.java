package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import com.sinosoft.lis.vschema.FICustomerAccTypeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.SQLString;

/**
 * <p>
 * ClassName: FICustomerAccTypeDBSet
 * </p>
 * <p>
 * Description: 账户类型定义表（FICustomerAccType）的DBSet文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-12-30
 */
public class FICustomerAccTypeDBSet extends FICustomerAccTypeSet
{
private static Logger logger = Logger.getLogger(FICustomerAccTypeDBSet.class);

	// @Field
	private Connection con;

	private DBOper db;

	/**
	 * flag = true: 传入Connection flag = false: 不传入Connection
	 **/
	private boolean mflag = false;

	// @Constructor
	public FICustomerAccTypeDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con, "FICustomerAccType");
		mflag = true;
	}

	public FICustomerAccTypeDBSet()
	{
		db = new DBOper("FICustomerAccType");
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
			tError.moduleName = "FICustomerAccTypeDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FICustomerAccType WHERE  AccNo = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getAccNo() == null || this.get(i).getAccNo().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getAccNo());
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
			tError.moduleName = "FICustomerAccTypeDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("FICustomerAccType");
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
			pstmt = con.prepareStatement("UPDATE FICustomerAccType SET  AccNo = ? , AccType = ? , UpAccType = ? , State = ? , Remark = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  AccNo = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getAccNo() == null || this.get(i).getAccNo().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getAccNo());
				}
				if (this.get(i).getAccType() == null || this.get(i).getAccType().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getAccType());
				}
				if (this.get(i).getUpAccType() == null || this.get(i).getUpAccType().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getUpAccType());
				}
				if (this.get(i).getState() == null || this.get(i).getState().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getState());
				}
				if (this.get(i).getRemark() == null || this.get(i).getRemark().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getRemark());
				}
				if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
				{
					pstmt.setString(6, null);
				}
				else
				{
					pstmt.setString(6, this.get(i).getOperator());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(7, null);
				}
				else
				{
					pstmt.setDate(7, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(8, null);
				}
				else
				{
					pstmt.setString(8, this.get(i).getMakeTime());
				}
				if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
				{
					pstmt.setDate(9, null);
				}
				else
				{
					pstmt.setDate(9, Date.valueOf(this.get(i).getModifyDate()));
				}
				if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
				{
					pstmt.setString(10, null);
				}
				else
				{
					pstmt.setString(10, this.get(i).getModifyTime());
				}
				// set where condition
				if (this.get(i).getAccNo() == null || this.get(i).getAccNo().equals("null"))
				{
					pstmt.setString(11, null);
				}
				else
				{
					pstmt.setString(11, this.get(i).getAccNo());
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
			tError.moduleName = "FICustomerAccTypeDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("FICustomerAccType");
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
			pstmt = con.prepareStatement("INSERT INTO FICustomerAccType VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getAccNo() == null || this.get(i).getAccNo().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getAccNo());
				}
				if (this.get(i).getAccType() == null || this.get(i).getAccType().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getAccType());
				}
				if (this.get(i).getUpAccType() == null || this.get(i).getUpAccType().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getUpAccType());
				}
				if (this.get(i).getState() == null || this.get(i).getState().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getState());
				}
				if (this.get(i).getRemark() == null || this.get(i).getRemark().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getRemark());
				}
				if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
				{
					pstmt.setString(6, null);
				}
				else
				{
					pstmt.setString(6, this.get(i).getOperator());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(7, null);
				}
				else
				{
					pstmt.setDate(7, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(8, null);
				}
				else
				{
					pstmt.setString(8, this.get(i).getMakeTime());
				}
				if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
				{
					pstmt.setDate(9, null);
				}
				else
				{
					pstmt.setDate(9, Date.valueOf(this.get(i).getModifyDate()));
				}
				if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
				{
					pstmt.setString(10, null);
				}
				else
				{
					pstmt.setString(10, this.get(i).getModifyTime());
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
			tError.moduleName = "FICustomerAccTypeDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("FICustomerAccType");
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
