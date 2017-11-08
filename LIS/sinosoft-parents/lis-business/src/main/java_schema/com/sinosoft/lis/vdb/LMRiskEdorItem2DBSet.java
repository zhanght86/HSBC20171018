

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LMRiskEdorItem2Schema;
import com.sinosoft.lis.vschema.LMRiskEdorItem2Set;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMRiskEdorItem2DBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskEdorItem2DBSet extends LMRiskEdorItem2Set
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
	public LMRiskEdorItem2DBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMRiskEdorItem2");
		mflag = true;
	}

	public LMRiskEdorItem2DBSet()
	{
		db = new DBOper( "LMRiskEdorItem2" );
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
			tError.moduleName = "LMRiskEdorItem2DBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMRiskEdorItem2 WHERE  RiskCode = ? AND RiskVer = ? AND EdorCode = ? AND AppObj = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, StrTool.space(this.get(i).getRiskVer(), 8));
			}
			if(this.get(i).getEdorCode() == null || this.get(i).getEdorCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, StrTool.space(this.get(i).getEdorCode(), 6));
			}
			if(this.get(i).getAppObj() == null || this.get(i).getAppObj().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, StrTool.space(this.get(i).getAppObj(), 1));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRiskEdorItem2");
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
			tError.moduleName = "LMRiskEdorItem2DBSet";
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
			pstmt = con.prepareStatement("UPDATE LMRiskEdorItem2 SET  RiskCode = ? , RiskVer = ? , RiskName = ? , EdorCode = ? , EdorName = ? , AppObj = ? , CutPremFlag = ? , ChgFlag = ? , ChgValueFlag = ? , CalFlag = ? , NeedDetail = ? , IntvType = ? , GrpNeedList = ? , EdorPopedom = ? WHERE  RiskCode = ? AND RiskVer = ? AND EdorCode = ? AND AppObj = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskVer());
			}
			if(this.get(i).getRiskName() == null || this.get(i).getRiskName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskName());
			}
			if(this.get(i).getEdorCode() == null || this.get(i).getEdorCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getEdorCode());
			}
			if(this.get(i).getEdorName() == null || this.get(i).getEdorName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getEdorName());
			}
			if(this.get(i).getAppObj() == null || this.get(i).getAppObj().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAppObj());
			}
			if(this.get(i).getCutPremFlag() == null || this.get(i).getCutPremFlag().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCutPremFlag());
			}
			if(this.get(i).getChgFlag() == null || this.get(i).getChgFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getChgFlag());
			}
			if(this.get(i).getChgValueFlag() == null || this.get(i).getChgValueFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getChgValueFlag());
			}
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCalFlag());
			}
			if(this.get(i).getNeedDetail() == null || this.get(i).getNeedDetail().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getNeedDetail());
			}
			if(this.get(i).getIntvType() == null || this.get(i).getIntvType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIntvType());
			}
			if(this.get(i).getGrpNeedList() == null || this.get(i).getGrpNeedList().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getGrpNeedList());
			}
			if(this.get(i).getEdorPopedom() == null || this.get(i).getEdorPopedom().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getEdorPopedom());
			}
			// set where condition
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, StrTool.space(this.get(i).getRiskVer(), 8));
			}
			if(this.get(i).getEdorCode() == null || this.get(i).getEdorCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, StrTool.space(this.get(i).getEdorCode(), 6));
			}
			if(this.get(i).getAppObj() == null || this.get(i).getAppObj().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, StrTool.space(this.get(i).getAppObj(), 1));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRiskEdorItem2");
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
			tError.moduleName = "LMRiskEdorItem2DBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMRiskEdorItem2(RiskCode ,RiskVer ,RiskName ,EdorCode ,EdorName ,AppObj ,CutPremFlag ,ChgFlag ,ChgValueFlag ,CalFlag ,NeedDetail ,IntvType ,GrpNeedList ,EdorPopedom) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskVer());
			}
			if(this.get(i).getRiskName() == null || this.get(i).getRiskName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskName());
			}
			if(this.get(i).getEdorCode() == null || this.get(i).getEdorCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getEdorCode());
			}
			if(this.get(i).getEdorName() == null || this.get(i).getEdorName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getEdorName());
			}
			if(this.get(i).getAppObj() == null || this.get(i).getAppObj().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAppObj());
			}
			if(this.get(i).getCutPremFlag() == null || this.get(i).getCutPremFlag().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCutPremFlag());
			}
			if(this.get(i).getChgFlag() == null || this.get(i).getChgFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getChgFlag());
			}
			if(this.get(i).getChgValueFlag() == null || this.get(i).getChgValueFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getChgValueFlag());
			}
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCalFlag());
			}
			if(this.get(i).getNeedDetail() == null || this.get(i).getNeedDetail().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getNeedDetail());
			}
			if(this.get(i).getIntvType() == null || this.get(i).getIntvType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIntvType());
			}
			if(this.get(i).getGrpNeedList() == null || this.get(i).getGrpNeedList().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getGrpNeedList());
			}
			if(this.get(i).getEdorPopedom() == null || this.get(i).getEdorPopedom().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getEdorPopedom());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRiskEdorItem2");
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
			tError.moduleName = "LMRiskEdorItem2DBSet";
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
