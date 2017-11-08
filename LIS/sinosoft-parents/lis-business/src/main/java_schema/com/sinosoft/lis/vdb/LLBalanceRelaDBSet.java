/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLBalanceRelaSchema;
import com.sinosoft.lis.vschema.LLBalanceRelaSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLBalanceRelaDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLBalanceRelaDBSet extends LLBalanceRelaSet
{
private static Logger logger = Logger.getLogger(LLBalanceRelaDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LLBalanceRelaDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLBalanceRela");
		mflag = true;
	}

	public LLBalanceRelaDBSet()
	{
		db = new DBOper( "LLBalanceRela" );
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
			tError.moduleName = "LLBalanceRelaDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLBalanceRela WHERE  BalType = ? AND SubBalType = ? AND FinaType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBalType() == null || this.get(i).getBalType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBalType());
			}
			if(this.get(i).getSubBalType() == null || this.get(i).getSubBalType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSubBalType());
			}
			if(this.get(i).getFinaType() == null || this.get(i).getFinaType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getFinaType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBalanceRela");
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
			tError.moduleName = "LLBalanceRelaDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLBalanceRela SET  BalType = ? , BalTypeDesc = ? , SubBalType = ? , SubBalTypeDesc = ? , FinaType = ? , FinaTypeDesc = ? , Prop1 = ? , Prop2 = ? , Prop3 = ? , Prop4 = ? , Prop5 = ? , Prop6 = ? , Prop7 = ? , Prop8 = ? , Prop9 = ? WHERE  BalType = ? AND SubBalType = ? AND FinaType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBalType() == null || this.get(i).getBalType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBalType());
			}
			if(this.get(i).getBalTypeDesc() == null || this.get(i).getBalTypeDesc().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBalTypeDesc());
			}
			if(this.get(i).getSubBalType() == null || this.get(i).getSubBalType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSubBalType());
			}
			if(this.get(i).getSubBalTypeDesc() == null || this.get(i).getSubBalTypeDesc().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSubBalTypeDesc());
			}
			if(this.get(i).getFinaType() == null || this.get(i).getFinaType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFinaType());
			}
			if(this.get(i).getFinaTypeDesc() == null || this.get(i).getFinaTypeDesc().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFinaTypeDesc());
			}
			if(this.get(i).getProp1() == null || this.get(i).getProp1().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getProp1());
			}
			if(this.get(i).getProp2() == null || this.get(i).getProp2().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getProp2());
			}
			if(this.get(i).getProp3() == null || this.get(i).getProp3().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getProp3());
			}
			if(this.get(i).getProp4() == null || this.get(i).getProp4().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getProp4());
			}
			if(this.get(i).getProp5() == null || this.get(i).getProp5().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getProp5());
			}
			if(this.get(i).getProp6() == null || this.get(i).getProp6().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getProp6());
			}
			if(this.get(i).getProp7() == null || this.get(i).getProp7().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getProp7());
			}
			if(this.get(i).getProp8() == null || this.get(i).getProp8().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getProp8());
			}
			if(this.get(i).getProp9() == null || this.get(i).getProp9().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getProp9());
			}
			// set where condition
			if(this.get(i).getBalType() == null || this.get(i).getBalType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBalType());
			}
			if(this.get(i).getSubBalType() == null || this.get(i).getSubBalType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSubBalType());
			}
			if(this.get(i).getFinaType() == null || this.get(i).getFinaType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getFinaType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBalanceRela");
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
			tError.moduleName = "LLBalanceRelaDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLBalanceRela(BalType ,BalTypeDesc ,SubBalType ,SubBalTypeDesc ,FinaType ,FinaTypeDesc ,Prop1 ,Prop2 ,Prop3 ,Prop4 ,Prop5 ,Prop6 ,Prop7 ,Prop8 ,Prop9) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBalType() == null || this.get(i).getBalType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBalType());
			}
			if(this.get(i).getBalTypeDesc() == null || this.get(i).getBalTypeDesc().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBalTypeDesc());
			}
			if(this.get(i).getSubBalType() == null || this.get(i).getSubBalType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSubBalType());
			}
			if(this.get(i).getSubBalTypeDesc() == null || this.get(i).getSubBalTypeDesc().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSubBalTypeDesc());
			}
			if(this.get(i).getFinaType() == null || this.get(i).getFinaType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFinaType());
			}
			if(this.get(i).getFinaTypeDesc() == null || this.get(i).getFinaTypeDesc().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFinaTypeDesc());
			}
			if(this.get(i).getProp1() == null || this.get(i).getProp1().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getProp1());
			}
			if(this.get(i).getProp2() == null || this.get(i).getProp2().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getProp2());
			}
			if(this.get(i).getProp3() == null || this.get(i).getProp3().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getProp3());
			}
			if(this.get(i).getProp4() == null || this.get(i).getProp4().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getProp4());
			}
			if(this.get(i).getProp5() == null || this.get(i).getProp5().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getProp5());
			}
			if(this.get(i).getProp6() == null || this.get(i).getProp6().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getProp6());
			}
			if(this.get(i).getProp7() == null || this.get(i).getProp7().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getProp7());
			}
			if(this.get(i).getProp8() == null || this.get(i).getProp8().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getProp8());
			}
			if(this.get(i).getProp9() == null || this.get(i).getProp9().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getProp9());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBalanceRela");
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
			tError.moduleName = "LLBalanceRelaDBSet";
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
