/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LDMenuSchema;
import com.sinosoft.lis.vschema.LDMenuSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDMenuDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDMenuDBSet extends LDMenuSet
{
private static Logger logger = Logger.getLogger(LDMenuDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LDMenuDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LDMenu");
		mflag = true;
	}

	public LDMenuDBSet()
	{
		db = new DBOper( "LDMenu" );
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
			tError.moduleName = "LDMenuDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LDMenu WHERE  NodeCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getNodeCode() == null || this.get(i).getNodeCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getNodeCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDMenu");
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
			tError.moduleName = "LDMenuDBSet";
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
			pstmt = con.prepareStatement("UPDATE LDMenu SET  NodeCode = ? , ParentNodeCode = ? , NodeLevel = ? , NodeName = ? , ChildFlag = ? , NodeKey = ? , RunScript = ? , NodeDescription = ? , NodeSign = ? , NodeOrder = ? WHERE  NodeCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getNodeCode() == null || this.get(i).getNodeCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getNodeCode());
			}
			if(this.get(i).getParentNodeCode() == null || this.get(i).getParentNodeCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getParentNodeCode());
			}
			if(this.get(i).getNodeLevel() == null || this.get(i).getNodeLevel().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getNodeLevel());
			}
			if(this.get(i).getNodeName() == null || this.get(i).getNodeName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getNodeName());
			}
			if(this.get(i).getChildFlag() == null || this.get(i).getChildFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getChildFlag());
			}
			if(this.get(i).getNodeKey() == null || this.get(i).getNodeKey().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getNodeKey());
			}
			if(this.get(i).getRunScript() == null || this.get(i).getRunScript().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRunScript());
			}
			if(this.get(i).getNodeDescription() == null || this.get(i).getNodeDescription().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getNodeDescription());
			}
			if(this.get(i).getNodeSign() == null || this.get(i).getNodeSign().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getNodeSign());
			}
			pstmt.setInt(10, this.get(i).getNodeOrder());
			// set where condition
			if(this.get(i).getNodeCode() == null || this.get(i).getNodeCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getNodeCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDMenu");
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
			tError.moduleName = "LDMenuDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LDMenu(NodeCode ,ParentNodeCode ,NodeLevel ,NodeName ,ChildFlag ,NodeKey ,RunScript ,NodeDescription ,NodeSign ,NodeOrder) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getNodeCode() == null || this.get(i).getNodeCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getNodeCode());
			}
			if(this.get(i).getParentNodeCode() == null || this.get(i).getParentNodeCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getParentNodeCode());
			}
			if(this.get(i).getNodeLevel() == null || this.get(i).getNodeLevel().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getNodeLevel());
			}
			if(this.get(i).getNodeName() == null || this.get(i).getNodeName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getNodeName());
			}
			if(this.get(i).getChildFlag() == null || this.get(i).getChildFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getChildFlag());
			}
			if(this.get(i).getNodeKey() == null || this.get(i).getNodeKey().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getNodeKey());
			}
			if(this.get(i).getRunScript() == null || this.get(i).getRunScript().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRunScript());
			}
			if(this.get(i).getNodeDescription() == null || this.get(i).getNodeDescription().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getNodeDescription());
			}
			if(this.get(i).getNodeSign() == null || this.get(i).getNodeSign().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getNodeSign());
			}
			pstmt.setInt(10, this.get(i).getNodeOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDMenu");
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
			tError.moduleName = "LDMenuDBSet";
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
