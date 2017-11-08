/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LARateCommisionSchema;
import com.sinosoft.lis.vschema.LARateCommisionSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LARateCommisionDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LARateCommisionDBSet extends LARateCommisionSet
{
private static Logger logger = Logger.getLogger(LARateCommisionDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LARateCommisionDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LARateCommision");
		mflag = true;
	}

	public LARateCommisionDBSet()
	{
		db = new DBOper( "LARateCommision" );
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
			tError.moduleName = "LARateCommisionDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LARateCommision WHERE  BranchType = ? AND RiskCode = ? AND sex = ? AND AppAge = ? AND Year = ? AND PayIntv = ? AND CurYear = ? AND F01 = ? AND F02 = ? AND F03 = ? AND F04 = ? AND F05 = ? AND F06 = ? AND ManageCom = ? AND VersionType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBranchType());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskCode());
			}
			if(this.get(i).getsex() == null || this.get(i).getsex().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, StrTool.space1(this.get(i).getsex(), 1));
			}
			pstmt.setInt(4, this.get(i).getAppAge());
			pstmt.setInt(5, this.get(i).getYear());
			if(this.get(i).getPayIntv() == null || this.get(i).getPayIntv().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPayIntv());
			}
			pstmt.setInt(7, this.get(i).getCurYear());
			if(this.get(i).getF01() == null || this.get(i).getF01().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getF01());
			}
			if(this.get(i).getF02() == null || this.get(i).getF02().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getF02());
			}
			pstmt.setDouble(10, this.get(i).getF03());
			pstmt.setDouble(11, this.get(i).getF04());
			if(this.get(i).getF05() == null || this.get(i).getF05().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getF05());
			}
			if(this.get(i).getF06() == null || this.get(i).getF06().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getF06());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getManageCom());
			}
			if(this.get(i).getVersionType() == null || this.get(i).getVersionType().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getVersionType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LARateCommision");
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
			tError.moduleName = "LARateCommisionDBSet";
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
			pstmt = con.prepareStatement("UPDATE LARateCommision SET  BranchType = ? , RiskCode = ? , sex = ? , AppAge = ? , Year = ? , PayIntv = ? , CurYear = ? , F01 = ? , F02 = ? , F03 = ? , F04 = ? , F05 = ? , F06 = ? , Rate = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , ManageCom = ? , VersionType = ? WHERE  BranchType = ? AND RiskCode = ? AND sex = ? AND AppAge = ? AND Year = ? AND PayIntv = ? AND CurYear = ? AND F01 = ? AND F02 = ? AND F03 = ? AND F04 = ? AND F05 = ? AND F06 = ? AND ManageCom = ? AND VersionType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBranchType());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskCode());
			}
			if(this.get(i).getsex() == null || this.get(i).getsex().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getsex());
			}
			pstmt.setInt(4, this.get(i).getAppAge());
			pstmt.setInt(5, this.get(i).getYear());
			if(this.get(i).getPayIntv() == null || this.get(i).getPayIntv().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPayIntv());
			}
			pstmt.setInt(7, this.get(i).getCurYear());
			if(this.get(i).getF01() == null || this.get(i).getF01().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getF01());
			}
			if(this.get(i).getF02() == null || this.get(i).getF02().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getF02());
			}
			pstmt.setDouble(10, this.get(i).getF03());
			pstmt.setDouble(11, this.get(i).getF04());
			if(this.get(i).getF05() == null || this.get(i).getF05().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getF05());
			}
			if(this.get(i).getF06() == null || this.get(i).getF06().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getF06());
			}
			pstmt.setDouble(14, this.get(i).getRate());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getModifyTime());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getManageCom());
			}
			if(this.get(i).getVersionType() == null || this.get(i).getVersionType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getVersionType());
			}
			// set where condition
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getBranchType());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRiskCode());
			}
			if(this.get(i).getsex() == null || this.get(i).getsex().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, StrTool.space1(this.get(i).getsex(), 1));
			}
			pstmt.setInt(25, this.get(i).getAppAge());
			pstmt.setInt(26, this.get(i).getYear());
			if(this.get(i).getPayIntv() == null || this.get(i).getPayIntv().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getPayIntv());
			}
			pstmt.setInt(28, this.get(i).getCurYear());
			if(this.get(i).getF01() == null || this.get(i).getF01().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getF01());
			}
			if(this.get(i).getF02() == null || this.get(i).getF02().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getF02());
			}
			pstmt.setDouble(31, this.get(i).getF03());
			pstmt.setDouble(32, this.get(i).getF04());
			if(this.get(i).getF05() == null || this.get(i).getF05().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getF05());
			}
			if(this.get(i).getF06() == null || this.get(i).getF06().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getF06());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getManageCom());
			}
			if(this.get(i).getVersionType() == null || this.get(i).getVersionType().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getVersionType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LARateCommision");
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
			tError.moduleName = "LARateCommisionDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LARateCommision(BranchType ,RiskCode ,sex ,AppAge ,Year ,PayIntv ,CurYear ,F01 ,F02 ,F03 ,F04 ,F05 ,F06 ,Rate ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,ManageCom ,VersionType) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBranchType());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskCode());
			}
			if(this.get(i).getsex() == null || this.get(i).getsex().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getsex());
			}
			pstmt.setInt(4, this.get(i).getAppAge());
			pstmt.setInt(5, this.get(i).getYear());
			if(this.get(i).getPayIntv() == null || this.get(i).getPayIntv().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPayIntv());
			}
			pstmt.setInt(7, this.get(i).getCurYear());
			if(this.get(i).getF01() == null || this.get(i).getF01().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getF01());
			}
			if(this.get(i).getF02() == null || this.get(i).getF02().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getF02());
			}
			pstmt.setDouble(10, this.get(i).getF03());
			pstmt.setDouble(11, this.get(i).getF04());
			if(this.get(i).getF05() == null || this.get(i).getF05().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getF05());
			}
			if(this.get(i).getF06() == null || this.get(i).getF06().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getF06());
			}
			pstmt.setDouble(14, this.get(i).getRate());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getModifyTime());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getManageCom());
			}
			if(this.get(i).getVersionType() == null || this.get(i).getVersionType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getVersionType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LARateCommision");
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
			tError.moduleName = "LARateCommisionDBSet";
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
