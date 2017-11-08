/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LFItemRelaSchema;
import com.sinosoft.lis.vschema.LFItemRelaSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LFItemRelaDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LFItemRelaDBSet extends LFItemRelaSet
{
private static Logger logger = Logger.getLogger(LFItemRelaDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LFItemRelaDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LFItemRela");
		mflag = true;
	}

	public LFItemRelaDBSet()
	{
		db = new DBOper( "LFItemRela" );
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
			tError.moduleName = "LFItemRelaDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LFItemRela WHERE  ItemCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getItemCode() == null || this.get(i).getItemCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getItemCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFItemRela");
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
			tError.moduleName = "LFItemRelaDBSet";
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
			pstmt = con.prepareStatement("UPDATE LFItemRela SET  ItemCode = ? , ItemName = ? , OutItemCode = ? , UpItemCode = ? , ItemLevel = ? , CorpType = ? , ItemType = ? , IsQuick = ? , IsMon = ? , IsQut = ? , IsHalYer = ? , IsYear = ? , IsLeaf = ? , General = ? , Layer = ? , Description = ? , Remark = ? , OutputFlag = ? , ComFlag = ? , IsCalFlag = ? WHERE  ItemCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getItemCode() == null || this.get(i).getItemCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getItemCode());
			}
			if(this.get(i).getItemName() == null || this.get(i).getItemName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getItemName());
			}
			if(this.get(i).getOutItemCode() == null || this.get(i).getOutItemCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOutItemCode());
			}
			if(this.get(i).getUpItemCode() == null || this.get(i).getUpItemCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getUpItemCode());
			}
			pstmt.setInt(5, this.get(i).getItemLevel());
			if(this.get(i).getCorpType() == null || this.get(i).getCorpType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCorpType());
			}
			if(this.get(i).getItemType() == null || this.get(i).getItemType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getItemType());
			}
			if(this.get(i).getIsQuick() == null || this.get(i).getIsQuick().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getIsQuick());
			}
			if(this.get(i).getIsMon() == null || this.get(i).getIsMon().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getIsMon());
			}
			if(this.get(i).getIsQut() == null || this.get(i).getIsQut().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getIsQut());
			}
			if(this.get(i).getIsHalYer() == null || this.get(i).getIsHalYer().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getIsHalYer());
			}
			if(this.get(i).getIsYear() == null || this.get(i).getIsYear().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIsYear());
			}
			if(this.get(i).getIsLeaf() == null || this.get(i).getIsLeaf().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getIsLeaf());
			}
			if(this.get(i).getGeneral() == null || this.get(i).getGeneral().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getGeneral());
			}
			pstmt.setInt(15, this.get(i).getLayer());
			if(this.get(i).getDescription() == null || this.get(i).getDescription().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getDescription());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRemark());
			}
			if(this.get(i).getOutputFlag() == null || this.get(i).getOutputFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getOutputFlag());
			}
			pstmt.setInt(19, this.get(i).getComFlag());
			if(this.get(i).getIsCalFlag() == null || this.get(i).getIsCalFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getIsCalFlag());
			}
			// set where condition
			if(this.get(i).getItemCode() == null || this.get(i).getItemCode().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getItemCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFItemRela");
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
			tError.moduleName = "LFItemRelaDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LFItemRela(ItemCode ,ItemName ,OutItemCode ,UpItemCode ,ItemLevel ,CorpType ,ItemType ,IsQuick ,IsMon ,IsQut ,IsHalYer ,IsYear ,IsLeaf ,General ,Layer ,Description ,Remark ,OutputFlag ,ComFlag ,IsCalFlag) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getItemCode() == null || this.get(i).getItemCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getItemCode());
			}
			if(this.get(i).getItemName() == null || this.get(i).getItemName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getItemName());
			}
			if(this.get(i).getOutItemCode() == null || this.get(i).getOutItemCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOutItemCode());
			}
			if(this.get(i).getUpItemCode() == null || this.get(i).getUpItemCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getUpItemCode());
			}
			pstmt.setInt(5, this.get(i).getItemLevel());
			if(this.get(i).getCorpType() == null || this.get(i).getCorpType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCorpType());
			}
			if(this.get(i).getItemType() == null || this.get(i).getItemType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getItemType());
			}
			if(this.get(i).getIsQuick() == null || this.get(i).getIsQuick().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getIsQuick());
			}
			if(this.get(i).getIsMon() == null || this.get(i).getIsMon().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getIsMon());
			}
			if(this.get(i).getIsQut() == null || this.get(i).getIsQut().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getIsQut());
			}
			if(this.get(i).getIsHalYer() == null || this.get(i).getIsHalYer().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getIsHalYer());
			}
			if(this.get(i).getIsYear() == null || this.get(i).getIsYear().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIsYear());
			}
			if(this.get(i).getIsLeaf() == null || this.get(i).getIsLeaf().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getIsLeaf());
			}
			if(this.get(i).getGeneral() == null || this.get(i).getGeneral().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getGeneral());
			}
			pstmt.setInt(15, this.get(i).getLayer());
			if(this.get(i).getDescription() == null || this.get(i).getDescription().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getDescription());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRemark());
			}
			if(this.get(i).getOutputFlag() == null || this.get(i).getOutputFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getOutputFlag());
			}
			pstmt.setInt(19, this.get(i).getComFlag());
			if(this.get(i).getIsCalFlag() == null || this.get(i).getIsCalFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getIsCalFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFItemRela");
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
			tError.moduleName = "LFItemRelaDBSet";
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
