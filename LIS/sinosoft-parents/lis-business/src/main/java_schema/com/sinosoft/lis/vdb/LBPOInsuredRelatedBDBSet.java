/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBPOInsuredRelatedBSchema;
import com.sinosoft.lis.vschema.LBPOInsuredRelatedBSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPOInsuredRelatedBDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LBPOInsuredRelatedBDBSet extends LBPOInsuredRelatedBSet
{
private static Logger logger = Logger.getLogger(LBPOInsuredRelatedBDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LBPOInsuredRelatedBDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBPOInsuredRelatedB");
		mflag = true;
	}

	public LBPOInsuredRelatedBDBSet()
	{
		db = new DBOper( "LBPOInsuredRelatedB" );
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
			tError.moduleName = "LBPOInsuredRelatedBDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBPOInsuredRelatedB WHERE  IDX = ? AND PolNo = ? AND CustomerNo = ? AND MainCustomerNo = ? AND InputNo = ? AND FillNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIDX());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPolNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCustomerNo());
			}
			if(this.get(i).getMainCustomerNo() == null || this.get(i).getMainCustomerNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMainCustomerNo());
			}
			pstmt.setInt(5, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFillNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOInsuredRelatedB");
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
			tError.moduleName = "LBPOInsuredRelatedBDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBPOInsuredRelatedB SET  IDX = ? , PolNo = ? , CustomerNo = ? , MainCustomerNo = ? , InputNo = ? , FillNo = ? , RelationToInsured = ? , AddressNo = ? , Name = ? , Sex = ? , Birthday = ? , IDType = ? , IDNo = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , SequenceNo = ? , TransDate = ? , TransTime = ? , TransOperator = ? WHERE  IDX = ? AND PolNo = ? AND CustomerNo = ? AND MainCustomerNo = ? AND InputNo = ? AND FillNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIDX());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPolNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCustomerNo());
			}
			if(this.get(i).getMainCustomerNo() == null || this.get(i).getMainCustomerNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMainCustomerNo());
			}
			pstmt.setInt(5, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFillNo());
			}
			if(this.get(i).getRelationToInsured() == null || this.get(i).getRelationToInsured().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRelationToInsured());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAddressNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBirthday());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getIDNo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMakeDate());
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getModifyDate());
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getModifyTime());
			}
			if(this.get(i).getSequenceNo() == null || this.get(i).getSequenceNo().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getSequenceNo());
			}
			if(this.get(i).getTransDate() == null || this.get(i).getTransDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getTransDate()));
			}
			if(this.get(i).getTransTime() == null || this.get(i).getTransTime().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getTransTime());
			}
			if(this.get(i).getTransOperator() == null || this.get(i).getTransOperator().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getTransOperator());
			}
			// set where condition
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getIDX());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getPolNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getCustomerNo());
			}
			if(this.get(i).getMainCustomerNo() == null || this.get(i).getMainCustomerNo().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getMainCustomerNo());
			}
			pstmt.setInt(27, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getFillNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOInsuredRelatedB");
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
			tError.moduleName = "LBPOInsuredRelatedBDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBPOInsuredRelatedB(IDX ,PolNo ,CustomerNo ,MainCustomerNo ,InputNo ,FillNo ,RelationToInsured ,AddressNo ,Name ,Sex ,Birthday ,IDType ,IDNo ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,SequenceNo ,TransDate ,TransTime ,TransOperator) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIDX());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPolNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCustomerNo());
			}
			if(this.get(i).getMainCustomerNo() == null || this.get(i).getMainCustomerNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMainCustomerNo());
			}
			pstmt.setInt(5, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFillNo());
			}
			if(this.get(i).getRelationToInsured() == null || this.get(i).getRelationToInsured().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRelationToInsured());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAddressNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBirthday());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getIDNo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMakeDate());
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getModifyDate());
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getModifyTime());
			}
			if(this.get(i).getSequenceNo() == null || this.get(i).getSequenceNo().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getSequenceNo());
			}
			if(this.get(i).getTransDate() == null || this.get(i).getTransDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getTransDate()));
			}
			if(this.get(i).getTransTime() == null || this.get(i).getTransTime().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getTransTime());
			}
			if(this.get(i).getTransOperator() == null || this.get(i).getTransOperator().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getTransOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOInsuredRelatedB");
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
			tError.moduleName = "LBPOInsuredRelatedBDBSet";
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
