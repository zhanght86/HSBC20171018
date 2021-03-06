/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LOAccUnitPriceSchema;
import com.sinosoft.lis.vschema.LOAccUnitPriceSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LOAccUnitPriceDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LOAccUnitPriceDBSet extends LOAccUnitPriceSet
{
private static Logger logger = Logger.getLogger(LOAccUnitPriceDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LOAccUnitPriceDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LOAccUnitPrice");
		mflag = true;
	}

	public LOAccUnitPriceDBSet()
	{
		db = new DBOper( "LOAccUnitPrice" );
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
			tError.moduleName = "LOAccUnitPriceDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LOAccUnitPrice WHERE  InsuAccNo = ? AND StartDate = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(2,null);
			} else {
				pstmt.setDate(2, Date.valueOf(this.get(i).getStartDate()));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOAccUnitPrice");
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
			tError.moduleName = "LOAccUnitPriceDBSet";
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
			pstmt = con.prepareStatement("UPDATE LOAccUnitPrice SET  InsuAccNo = ? , StartDate = ? , EndDate = ? , InvestFlag = ? , SRateDate = ? , ARateDate = ? , UnitPriceBuy = ? , UnitPriceSell = ? , RedeemRate = ? , State = ? , RedeemMoney = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , InsuTotalMoney = ? , Liabilities = ? , OtherTax = ? , AccasManageFee = ? , CompanyUnitCount = ? , ComChgUnitCount = ? , CustomersUnitCount = ? , SKFlag = ? , NextPriceDate = ? , Currency = ? WHERE  InsuAccNo = ? AND StartDate = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(2,null);
			} else {
				pstmt.setDate(2, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(3,null);
			} else {
				pstmt.setDate(3, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getInvestFlag() == null || this.get(i).getInvestFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getInvestFlag());
			}
			if(this.get(i).getSRateDate() == null || this.get(i).getSRateDate().equals("null")) {
				pstmt.setDate(5,null);
			} else {
				pstmt.setDate(5, Date.valueOf(this.get(i).getSRateDate()));
			}
			if(this.get(i).getARateDate() == null || this.get(i).getARateDate().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getARateDate()));
			}
			pstmt.setDouble(7, this.get(i).getUnitPriceBuy());
			pstmt.setDouble(8, this.get(i).getUnitPriceSell());
			pstmt.setDouble(9, this.get(i).getRedeemRate());
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getState());
			}
			pstmt.setDouble(11, this.get(i).getRedeemMoney());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getModifyTime());
			}
			pstmt.setDouble(17, this.get(i).getInsuTotalMoney());
			pstmt.setDouble(18, this.get(i).getLiabilities());
			pstmt.setDouble(19, this.get(i).getOtherTax());
			pstmt.setDouble(20, this.get(i).getAccasManageFee());
			pstmt.setDouble(21, this.get(i).getCompanyUnitCount());
			pstmt.setDouble(22, this.get(i).getComChgUnitCount());
			pstmt.setDouble(23, this.get(i).getCustomersUnitCount());
			if(this.get(i).getSKFlag() == null || this.get(i).getSKFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSKFlag());
			}
			if(this.get(i).getNextPriceDate() == null || this.get(i).getNextPriceDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getNextPriceDate()));
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getCurrency());
			}
			// set where condition
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getStartDate()));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOAccUnitPrice");
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
			tError.moduleName = "LOAccUnitPriceDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LOAccUnitPrice VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(2,null);
			} else {
				pstmt.setDate(2, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(3,null);
			} else {
				pstmt.setDate(3, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getInvestFlag() == null || this.get(i).getInvestFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getInvestFlag());
			}
			if(this.get(i).getSRateDate() == null || this.get(i).getSRateDate().equals("null")) {
				pstmt.setDate(5,null);
			} else {
				pstmt.setDate(5, Date.valueOf(this.get(i).getSRateDate()));
			}
			if(this.get(i).getARateDate() == null || this.get(i).getARateDate().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getARateDate()));
			}
			pstmt.setDouble(7, this.get(i).getUnitPriceBuy());
			pstmt.setDouble(8, this.get(i).getUnitPriceSell());
			pstmt.setDouble(9, this.get(i).getRedeemRate());
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getState());
			}
			pstmt.setDouble(11, this.get(i).getRedeemMoney());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getModifyTime());
			}
			pstmt.setDouble(17, this.get(i).getInsuTotalMoney());
			pstmt.setDouble(18, this.get(i).getLiabilities());
			pstmt.setDouble(19, this.get(i).getOtherTax());
			pstmt.setDouble(20, this.get(i).getAccasManageFee());
			pstmt.setDouble(21, this.get(i).getCompanyUnitCount());
			pstmt.setDouble(22, this.get(i).getComChgUnitCount());
			pstmt.setDouble(23, this.get(i).getCustomersUnitCount());
			if(this.get(i).getSKFlag() == null || this.get(i).getSKFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSKFlag());
			}
			if(this.get(i).getNextPriceDate() == null || this.get(i).getNextPriceDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getNextPriceDate()));
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getCurrency());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOAccUnitPrice");
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
			tError.moduleName = "LOAccUnitPriceDBSet";
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
