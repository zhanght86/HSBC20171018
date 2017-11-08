/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LMEdorItemSchema;
import com.sinosoft.lis.vschema.LMEdorItemSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMEdorItemDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LMEdorItemDBSet extends LMEdorItemSet
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LMEdorItemDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMEdorItem");
		mflag = true;
	}

	public LMEdorItemDBSet()
	{
		db = new DBOper( "LMEdorItem" );
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
			tError.moduleName = "LMEdorItemDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMEdorItem WHERE  EdorCode = ? AND AppObj = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorCode() == null || this.get(i).getEdorCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorCode());
			}
			if(this.get(i).getAppObj() == null || this.get(i).getAppObj().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAppObj());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMEdorItem");
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
			tError.moduleName = "LMEdorItemDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMEdorItem SET  EdorCode = ? , EdorName = ? , AppObj = ? , DisplayFlag = ? , CalFlag = ? , NeedDetail = ? , GrpNeedList = ? , EdorPopedom = ? , EdorConstraints = ? , PwdFlag = ? , AccBalaFlag = ? , PriceSellFlag = ? , PriceBuyFlag = ? , SameAccFlag = ? , EdorLevel = ? , EdorClass = ? , ActivityID = ? , ValDateFlag = ? , AloneFlag = ? , UWFlag = ? , EdorDesc = ? , EdorMaterial = ? , MattersNeedAtt = ? WHERE  EdorCode = ? AND AppObj = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorCode() == null || this.get(i).getEdorCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorCode());
			}
			if(this.get(i).getEdorName() == null || this.get(i).getEdorName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorName());
			}
			if(this.get(i).getAppObj() == null || this.get(i).getAppObj().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAppObj());
			}
			if(this.get(i).getDisplayFlag() == null || this.get(i).getDisplayFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDisplayFlag());
			}
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCalFlag());
			}
			if(this.get(i).getNeedDetail() == null || this.get(i).getNeedDetail().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getNeedDetail());
			}
			if(this.get(i).getGrpNeedList() == null || this.get(i).getGrpNeedList().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGrpNeedList());
			}
			if(this.get(i).getEdorPopedom() == null || this.get(i).getEdorPopedom().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getEdorPopedom());
			}
			if(this.get(i).getEdorConstraints() == null || this.get(i).getEdorConstraints().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getEdorConstraints());
			}
			if(this.get(i).getPwdFlag() == null || this.get(i).getPwdFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getPwdFlag());
			}
			if(this.get(i).getAccBalaFlag() == null || this.get(i).getAccBalaFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAccBalaFlag());
			}
			if(this.get(i).getPriceSellFlag() == null || this.get(i).getPriceSellFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPriceSellFlag());
			}
			if(this.get(i).getPriceBuyFlag() == null || this.get(i).getPriceBuyFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPriceBuyFlag());
			}
			if(this.get(i).getSameAccFlag() == null || this.get(i).getSameAccFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getSameAccFlag());
			}
			if(this.get(i).getEdorLevel() == null || this.get(i).getEdorLevel().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getEdorLevel());
			}
			if(this.get(i).getEdorClass() == null || this.get(i).getEdorClass().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getEdorClass());
			}
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getActivityID());
			}
			if(this.get(i).getValDateFlag() == null || this.get(i).getValDateFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getValDateFlag());
			}
			if(this.get(i).getAloneFlag() == null || this.get(i).getAloneFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAloneFlag());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getUWFlag());
			}
			if(this.get(i).getEdorDesc() == null || this.get(i).getEdorDesc().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getEdorDesc());
			}
			if(this.get(i).getEdorMaterial() == null || this.get(i).getEdorMaterial().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getEdorMaterial());
			}
			if(this.get(i).getMattersNeedAtt() == null || this.get(i).getMattersNeedAtt().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getMattersNeedAtt());
			}
			// set where condition
			if(this.get(i).getEdorCode() == null || this.get(i).getEdorCode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getEdorCode());
			}
			if(this.get(i).getAppObj() == null || this.get(i).getAppObj().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAppObj());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMEdorItem");
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
			tError.moduleName = "LMEdorItemDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMEdorItem VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorCode() == null || this.get(i).getEdorCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorCode());
			}
			if(this.get(i).getEdorName() == null || this.get(i).getEdorName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorName());
			}
			if(this.get(i).getAppObj() == null || this.get(i).getAppObj().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAppObj());
			}
			if(this.get(i).getDisplayFlag() == null || this.get(i).getDisplayFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDisplayFlag());
			}
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCalFlag());
			}
			if(this.get(i).getNeedDetail() == null || this.get(i).getNeedDetail().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getNeedDetail());
			}
			if(this.get(i).getGrpNeedList() == null || this.get(i).getGrpNeedList().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGrpNeedList());
			}
			if(this.get(i).getEdorPopedom() == null || this.get(i).getEdorPopedom().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getEdorPopedom());
			}
			if(this.get(i).getEdorConstraints() == null || this.get(i).getEdorConstraints().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getEdorConstraints());
			}
			if(this.get(i).getPwdFlag() == null || this.get(i).getPwdFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getPwdFlag());
			}
			if(this.get(i).getAccBalaFlag() == null || this.get(i).getAccBalaFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAccBalaFlag());
			}
			if(this.get(i).getPriceSellFlag() == null || this.get(i).getPriceSellFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPriceSellFlag());
			}
			if(this.get(i).getPriceBuyFlag() == null || this.get(i).getPriceBuyFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPriceBuyFlag());
			}
			if(this.get(i).getSameAccFlag() == null || this.get(i).getSameAccFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getSameAccFlag());
			}
			if(this.get(i).getEdorLevel() == null || this.get(i).getEdorLevel().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getEdorLevel());
			}
			if(this.get(i).getEdorClass() == null || this.get(i).getEdorClass().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getEdorClass());
			}
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getActivityID());
			}
			if(this.get(i).getValDateFlag() == null || this.get(i).getValDateFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getValDateFlag());
			}
			if(this.get(i).getAloneFlag() == null || this.get(i).getAloneFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAloneFlag());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getUWFlag());
			}
			if(this.get(i).getEdorDesc() == null || this.get(i).getEdorDesc().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getEdorDesc());
			}
			if(this.get(i).getEdorMaterial() == null || this.get(i).getEdorMaterial().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getEdorMaterial());
			}
			if(this.get(i).getMattersNeedAtt() == null || this.get(i).getMattersNeedAtt().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getMattersNeedAtt());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMEdorItem");
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
			tError.moduleName = "LMEdorItemDBSet";
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
