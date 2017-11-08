package com.sinosoft.lis.vdb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import com.sinosoft.lis.vschema.LFCKErrorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.SQLString;

/**
 * <p>
 * ClassName: LFCKErrorDBSet
 * </p>
 * <p>
 * Description: 保监会报表校验错误信息表（LFCKError）的DBSet文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-11-03
 */
public class LFCKErrorDBSet extends LFCKErrorSet
{
	// @Field
	private Connection con;

	private DBOper db;

	/**
	 * flag = true: 传入Connection flag = false: 不传入Connection
	 **/
	private boolean mflag = false;

	// @Constructor
	public LFCKErrorDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con, "LFCKError");
		mflag = true;
	}

	public LFCKErrorDBSet()
	{
		db = new DBOper("LFCKError");
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
			tError.moduleName = "LFCKErrorDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LFCKError WHERE  SerialNo = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getSerialNo());
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
			tError.moduleName = "LFCKErrorDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("LFCKError");
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
			pstmt = con.prepareStatement("UPDATE LFCKError SET  SerialNo = ? , ItemCode = ? , comcodeisc = ? , CKRuleCode = ? , CKError = ? , MakeDate = ? , MakeTime = ? WHERE  SerialNo = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getSerialNo());
				}
				if (this.get(i).getItemCode() == null || this.get(i).getItemCode().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getItemCode());
				}
				if (this.get(i).getcomcodeisc() == null || this.get(i).getcomcodeisc().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getcomcodeisc());
				}
				if (this.get(i).getCKRuleCode() == null || this.get(i).getCKRuleCode().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getCKRuleCode());
				}
				if (this.get(i).getCKError() == null || this.get(i).getCKError().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getCKError());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(6, null);
				}
				else
				{
					pstmt.setDate(6, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getMakeTime());
				}
				// set where condition
				if (this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null"))
				{
					pstmt.setString(8, null);
				}
				else
				{
					pstmt.setString(8, this.get(i).getSerialNo());
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
			tError.moduleName = "LFCKErrorDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("LFCKError");
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
			pstmt = con.prepareStatement("INSERT INTO LFCKError VALUES( ? , ? , ? , ? , ? , ? , ?)");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getSerialNo());
				}
				if (this.get(i).getItemCode() == null || this.get(i).getItemCode().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getItemCode());
				}
				if (this.get(i).getcomcodeisc() == null || this.get(i).getcomcodeisc().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getcomcodeisc());
				}
				if (this.get(i).getCKRuleCode() == null || this.get(i).getCKRuleCode().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getCKRuleCode());
				}
				if (this.get(i).getCKError() == null || this.get(i).getCKError().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getCKError());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(6, null);
				}
				else
				{
					pstmt.setDate(6, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getMakeTime());
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
			tError.moduleName = "LFCKErrorDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			int tCount = this.size();
			for (int i = 1; i <= tCount; i++)
			{
				SQLString sqlObj = new SQLString("LFCKError");
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
