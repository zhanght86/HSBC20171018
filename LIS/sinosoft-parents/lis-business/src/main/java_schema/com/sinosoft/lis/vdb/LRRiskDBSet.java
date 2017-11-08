/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LRRiskSchema;
import com.sinosoft.lis.vschema.LRRiskSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LRRiskDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LRRiskDBSet extends LRRiskSet
{
private static Logger logger = Logger.getLogger(LRRiskDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LRRiskDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LRRisk");
		mflag = true;
	}

	public LRRiskDBSet()
	{
		db = new DBOper( "LRRisk" );
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
			tError.moduleName = "LRRiskDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LRRisk WHERE  RiskCode = ? AND RiskSort = ? AND RiskCalSort = ? AND ReinsureCom = ? AND ReinsurItem = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskSort() == null || this.get(i).getRiskSort().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, StrTool.space1(this.get(i).getRiskSort(), 1));
			}
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, StrTool.space1(this.get(i).getRiskCalSort(), 1));
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, StrTool.space1(this.get(i).getReinsurItem(), 1));
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LRRisk");
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
			tError.moduleName = "LRRiskDBSet";
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
			pstmt = con.prepareStatement("UPDATE LRRisk SET  RiskCode = ? , RiskSort = ? , RiskCalSort = ? , ReinsureCom = ? , ReinsurItem = ? , RetainLine = ? , CessionLimit = ? , CessionMode = ? , ExMorRate = ? , RetentRate = ? , CommsionRate = ? , CessPremMode = ? , RiskAmntCalCode = ? , CessPremCalCode = ? , CessCommCalCode2 = ? , ReturnPayCalCode = ? , NegoCalCode = ? , GrpPerAmtLimit = ? , GrpAverAmntLimit = ? , DutyCode = ? WHERE  RiskCode = ? AND RiskSort = ? AND RiskCalSort = ? AND ReinsureCom = ? AND ReinsurItem = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskSort() == null || this.get(i).getRiskSort().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskSort());
			}
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskCalSort());
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getReinsurItem());
			}
			pstmt.setDouble(6, this.get(i).getRetainLine());
			pstmt.setDouble(7, this.get(i).getCessionLimit());
			if(this.get(i).getCessionMode() == null || this.get(i).getCessionMode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCessionMode());
			}
			pstmt.setDouble(9, this.get(i).getExMorRate());
			pstmt.setDouble(10, this.get(i).getRetentRate());
			pstmt.setDouble(11, this.get(i).getCommsionRate());
			if(this.get(i).getCessPremMode() == null || this.get(i).getCessPremMode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCessPremMode());
			}
			if(this.get(i).getRiskAmntCalCode() == null || this.get(i).getRiskAmntCalCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRiskAmntCalCode());
			}
			if(this.get(i).getCessPremCalCode() == null || this.get(i).getCessPremCalCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCessPremCalCode());
			}
			if(this.get(i).getCessCommCalCode2() == null || this.get(i).getCessCommCalCode2().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCessCommCalCode2());
			}
			if(this.get(i).getReturnPayCalCode() == null || this.get(i).getReturnPayCalCode().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getReturnPayCalCode());
			}
			if(this.get(i).getNegoCalCode() == null || this.get(i).getNegoCalCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getNegoCalCode());
			}
			pstmt.setDouble(18, this.get(i).getGrpPerAmtLimit());
			pstmt.setDouble(19, this.get(i).getGrpAverAmntLimit());
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getDutyCode());
			}
			// set where condition
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskSort() == null || this.get(i).getRiskSort().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, StrTool.space1(this.get(i).getRiskSort(), 1));
			}
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, StrTool.space1(this.get(i).getRiskCalSort(), 1));
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, StrTool.space1(this.get(i).getReinsurItem(), 1));
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LRRisk");
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
			tError.moduleName = "LRRiskDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LRRisk(RiskCode ,RiskSort ,RiskCalSort ,ReinsureCom ,ReinsurItem ,RetainLine ,CessionLimit ,CessionMode ,ExMorRate ,RetentRate ,CommsionRate ,CessPremMode ,RiskAmntCalCode ,CessPremCalCode ,CessCommCalCode2 ,ReturnPayCalCode ,NegoCalCode ,GrpPerAmtLimit ,GrpAverAmntLimit ,DutyCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskSort() == null || this.get(i).getRiskSort().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskSort());
			}
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskCalSort());
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getReinsurItem());
			}
			pstmt.setDouble(6, this.get(i).getRetainLine());
			pstmt.setDouble(7, this.get(i).getCessionLimit());
			if(this.get(i).getCessionMode() == null || this.get(i).getCessionMode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCessionMode());
			}
			pstmt.setDouble(9, this.get(i).getExMorRate());
			pstmt.setDouble(10, this.get(i).getRetentRate());
			pstmt.setDouble(11, this.get(i).getCommsionRate());
			if(this.get(i).getCessPremMode() == null || this.get(i).getCessPremMode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCessPremMode());
			}
			if(this.get(i).getRiskAmntCalCode() == null || this.get(i).getRiskAmntCalCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRiskAmntCalCode());
			}
			if(this.get(i).getCessPremCalCode() == null || this.get(i).getCessPremCalCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCessPremCalCode());
			}
			if(this.get(i).getCessCommCalCode2() == null || this.get(i).getCessCommCalCode2().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCessCommCalCode2());
			}
			if(this.get(i).getReturnPayCalCode() == null || this.get(i).getReturnPayCalCode().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getReturnPayCalCode());
			}
			if(this.get(i).getNegoCalCode() == null || this.get(i).getNegoCalCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getNegoCalCode());
			}
			pstmt.setDouble(18, this.get(i).getGrpPerAmtLimit());
			pstmt.setDouble(19, this.get(i).getGrpAverAmntLimit());
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LRRisk");
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
			tError.moduleName = "LRRiskDBSet";
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
