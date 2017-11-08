/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LFDesbModeSchema;
import com.sinosoft.lis.vschema.LFDesbModeSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LFDesbModeDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LFDesbModeDBSet extends LFDesbModeSet
{
private static Logger logger = Logger.getLogger(LFDesbModeDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LFDesbModeDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LFDesbMode");
		mflag = true;
	}

	public LFDesbModeDBSet()
	{
		db = new DBOper( "LFDesbMode" );
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
			tError.moduleName = "LFDesbModeDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LFDesbMode WHERE  ItemCode = ? AND ItemNum = ? AND ItemType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getItemCode() == null || this.get(i).getItemCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, StrTool.space1(this.get(i).getItemCode(), 8));
			}
			pstmt.setInt(2, this.get(i).getItemNum());
			if(this.get(i).getItemType() == null || this.get(i).getItemType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, StrTool.space1(this.get(i).getItemType(), 2));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFDesbMode");
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
			tError.moduleName = "LFDesbModeDBSet";
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
			pstmt = con.prepareStatement("UPDATE LFDesbMode SET  ItemCode = ? , ItemNum = ? , ItemType = ? , DealType = ? , CalSQL = ? , InterfaceClassName = ? , DestTableName = ? , Remark = ? , CalSQL1 = ? , CalSQL2 = ? , CalSQL3 = ? WHERE  ItemCode = ? AND ItemNum = ? AND ItemType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getItemCode() == null || this.get(i).getItemCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getItemCode());
			}
			pstmt.setInt(2, this.get(i).getItemNum());
			if(this.get(i).getItemType() == null || this.get(i).getItemType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getItemType());
			}
			if(this.get(i).getDealType() == null || this.get(i).getDealType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDealType());
			}
			if(this.get(i).getCalSQL() == null || this.get(i).getCalSQL().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCalSQL());
			}
			if(this.get(i).getInterfaceClassName() == null || this.get(i).getInterfaceClassName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInterfaceClassName());
			}
			if(this.get(i).getDestTableName() == null || this.get(i).getDestTableName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getDestTableName());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRemark());
			}
			if(this.get(i).getCalSQL1() == null || this.get(i).getCalSQL1().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCalSQL1());
			}
			if(this.get(i).getCalSQL2() == null || this.get(i).getCalSQL2().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCalSQL2());
			}
			if(this.get(i).getCalSQL3() == null || this.get(i).getCalSQL3().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCalSQL3());
			}
			// set where condition
			if(this.get(i).getItemCode() == null || this.get(i).getItemCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, StrTool.space1(this.get(i).getItemCode(), 8));
			}
			pstmt.setInt(13, this.get(i).getItemNum());
			if(this.get(i).getItemType() == null || this.get(i).getItemType().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, StrTool.space1(this.get(i).getItemType(), 2));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFDesbMode");
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
			tError.moduleName = "LFDesbModeDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LFDesbMode(ItemCode ,ItemNum ,ItemType ,DealType ,CalSQL ,InterfaceClassName ,DestTableName ,Remark ,CalSQL1 ,CalSQL2 ,CalSQL3) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getItemCode() == null || this.get(i).getItemCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getItemCode());
			}
			pstmt.setInt(2, this.get(i).getItemNum());
			if(this.get(i).getItemType() == null || this.get(i).getItemType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getItemType());
			}
			if(this.get(i).getDealType() == null || this.get(i).getDealType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDealType());
			}
			if(this.get(i).getCalSQL() == null || this.get(i).getCalSQL().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCalSQL());
			}
			if(this.get(i).getInterfaceClassName() == null || this.get(i).getInterfaceClassName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInterfaceClassName());
			}
			if(this.get(i).getDestTableName() == null || this.get(i).getDestTableName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getDestTableName());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRemark());
			}
			if(this.get(i).getCalSQL1() == null || this.get(i).getCalSQL1().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCalSQL1());
			}
			if(this.get(i).getCalSQL2() == null || this.get(i).getCalSQL2().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCalSQL2());
			}
			if(this.get(i).getCalSQL3() == null || this.get(i).getCalSQL3().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCalSQL3());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFDesbMode");
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
			tError.moduleName = "LFDesbModeDBSet";
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
