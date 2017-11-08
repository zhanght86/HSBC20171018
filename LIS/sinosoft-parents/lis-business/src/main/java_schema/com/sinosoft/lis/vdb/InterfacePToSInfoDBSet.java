

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.InterfacePToSInfoSchema;
import com.sinosoft.lis.vschema.InterfacePToSInfoSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: InterfacePToSInfoDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class InterfacePToSInfoDBSet extends InterfacePToSInfoSet
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
	public InterfacePToSInfoDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"InterfacePToSInfo");
		mflag = true;
	}

	public InterfacePToSInfoDBSet()
	{
		db = new DBOper( "InterfacePToSInfo" );
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
			tError.moduleName = "InterfacePToSInfoDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM InterfacePToSInfo WHERE  ProductCode = ? AND IProductCode = ? AND IsEffectiveDate = ? AND IsSpouseCode = ? AND IsStaffCode = ? AND IsJoinCode = ? AND IsPremPayPeriod = ? AND IsBenefitOption = ? AND IsBenefitPeriod = ? AND IsChannel = ? AND IsCurrency = ? AND IsPar = ? AND IsUreUWType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getProductCode() == null || this.get(i).getProductCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getProductCode());
			}
			if(this.get(i).getIProductCode() == null || this.get(i).getIProductCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getIProductCode());
			}
			if(this.get(i).getIsEffectiveDate() == null || this.get(i).getIsEffectiveDate().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getIsEffectiveDate());
			}
			if(this.get(i).getIsSpouseCode() == null || this.get(i).getIsSpouseCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getIsSpouseCode());
			}
			if(this.get(i).getIsStaffCode() == null || this.get(i).getIsStaffCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getIsStaffCode());
			}
			if(this.get(i).getIsJoinCode() == null || this.get(i).getIsJoinCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getIsJoinCode());
			}
			if(this.get(i).getIsPremPayPeriod() == null || this.get(i).getIsPremPayPeriod().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getIsPremPayPeriod());
			}
			if(this.get(i).getIsBenefitOption() == null || this.get(i).getIsBenefitOption().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getIsBenefitOption());
			}
			if(this.get(i).getIsBenefitPeriod() == null || this.get(i).getIsBenefitPeriod().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getIsBenefitPeriod());
			}
			if(this.get(i).getIsChannel() == null || this.get(i).getIsChannel().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getIsChannel());
			}
			if(this.get(i).getIsCurrency() == null || this.get(i).getIsCurrency().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getIsCurrency());
			}
			if(this.get(i).getIsPar() == null || this.get(i).getIsPar().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIsPar());
			}
			if(this.get(i).getIsUreUWType() == null || this.get(i).getIsUreUWType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getIsUreUWType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("InterfacePToSInfo");
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
			tError.moduleName = "InterfacePToSInfoDBSet";
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
			pstmt = con.prepareStatement("UPDATE InterfacePToSInfo SET  ProductCode = ? , ProductCHName = ? , ProductENName = ? , IProductCode = ? , IProductCHName = ? , IProductENName = ? , IsEffectiveDate = ? , EffectiveDate = ? , EffectiveEndDate = ? , IsSpouseCode = ? , SpouseCode = ? , IsStaffCode = ? , StaffCode = ? , IsJoinCode = ? , JoinCode = ? , IsPremPayPeriod = ? , PremPayPeriodType = ? , PremPayPeriod = ? , IsBenefitOption = ? , BenefitOptionType = ? , BenefitOption = ? , IsBenefitPeriod = ? , BenefitPeriodType = ? , BenefitPeriod = ? , IsChannel = ? , Channel = ? , IsCurrency = ? , Currency = ? , IsPar = ? , Par = ? , IsUreUWType = ? , UreUWType = ? , BatchNo = ? , OPERATOR = ? , MAKEDATE = ? , MAKETIME = ? , MODIFYDATE = ? , MODIFYTIME = ? , StandByFlag1 = ? , StandByFlag2 = ? , StandByFlag3 = ? WHERE  ProductCode = ? AND IProductCode = ? AND IsEffectiveDate = ? AND IsSpouseCode = ? AND IsStaffCode = ? AND IsJoinCode = ? AND IsPremPayPeriod = ? AND IsBenefitOption = ? AND IsBenefitPeriod = ? AND IsChannel = ? AND IsCurrency = ? AND IsPar = ? AND IsUreUWType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getProductCode() == null || this.get(i).getProductCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getProductCode());
			}
			if(this.get(i).getProductCHName() == null || this.get(i).getProductCHName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getProductCHName());
			}
			if(this.get(i).getProductENName() == null || this.get(i).getProductENName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getProductENName());
			}
			if(this.get(i).getIProductCode() == null || this.get(i).getIProductCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getIProductCode());
			}
			if(this.get(i).getIProductCHName() == null || this.get(i).getIProductCHName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getIProductCHName());
			}
			if(this.get(i).getIProductENName() == null || this.get(i).getIProductENName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getIProductENName());
			}
			if(this.get(i).getIsEffectiveDate() == null || this.get(i).getIsEffectiveDate().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getIsEffectiveDate());
			}
			if(this.get(i).getEffectiveDate() == null || this.get(i).getEffectiveDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getEffectiveDate()));
			}
			if(this.get(i).getEffectiveEndDate() == null || this.get(i).getEffectiveEndDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getEffectiveEndDate()));
			}
			if(this.get(i).getIsSpouseCode() == null || this.get(i).getIsSpouseCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getIsSpouseCode());
			}
			if(this.get(i).getSpouseCode() == null || this.get(i).getSpouseCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSpouseCode());
			}
			if(this.get(i).getIsStaffCode() == null || this.get(i).getIsStaffCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIsStaffCode());
			}
			if(this.get(i).getStaffCode() == null || this.get(i).getStaffCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getStaffCode());
			}
			if(this.get(i).getIsJoinCode() == null || this.get(i).getIsJoinCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getIsJoinCode());
			}
			if(this.get(i).getJoinCode() == null || this.get(i).getJoinCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getJoinCode());
			}
			if(this.get(i).getIsPremPayPeriod() == null || this.get(i).getIsPremPayPeriod().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getIsPremPayPeriod());
			}
			if(this.get(i).getPremPayPeriodType() == null || this.get(i).getPremPayPeriodType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getPremPayPeriodType());
			}
			pstmt.setInt(18, this.get(i).getPremPayPeriod());
			if(this.get(i).getIsBenefitOption() == null || this.get(i).getIsBenefitOption().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getIsBenefitOption());
			}
			if(this.get(i).getBenefitOptionType() == null || this.get(i).getBenefitOptionType().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getBenefitOptionType());
			}
			if(this.get(i).getBenefitOption() == null || this.get(i).getBenefitOption().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getBenefitOption());
			}
			if(this.get(i).getIsBenefitPeriod() == null || this.get(i).getIsBenefitPeriod().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getIsBenefitPeriod());
			}
			if(this.get(i).getBenefitPeriodType() == null || this.get(i).getBenefitPeriodType().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getBenefitPeriodType());
			}
			pstmt.setInt(24, this.get(i).getBenefitPeriod());
			if(this.get(i).getIsChannel() == null || this.get(i).getIsChannel().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getIsChannel());
			}
			if(this.get(i).getChannel() == null || this.get(i).getChannel().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getChannel());
			}
			if(this.get(i).getIsCurrency() == null || this.get(i).getIsCurrency().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getIsCurrency());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getCurrency());
			}
			if(this.get(i).getIsPar() == null || this.get(i).getIsPar().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getIsPar());
			}
			if(this.get(i).getPar() == null || this.get(i).getPar().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getPar());
			}
			if(this.get(i).getIsUreUWType() == null || this.get(i).getIsUreUWType().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getIsUreUWType());
			}
			if(this.get(i).getUreUWType() == null || this.get(i).getUreUWType().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getUreUWType());
			}
			pstmt.setInt(33, this.get(i).getBatchNo());
			if(this.get(i).getOPERATOR() == null || this.get(i).getOPERATOR().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOPERATOR());
			}
			if(this.get(i).getMAKEDATE() == null || this.get(i).getMAKEDATE().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getMAKEDATE()));
			}
			if(this.get(i).getMAKETIME() == null || this.get(i).getMAKETIME().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getMAKETIME());
			}
			if(this.get(i).getMODIFYDATE() == null || this.get(i).getMODIFYDATE().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getMODIFYDATE()));
			}
			if(this.get(i).getMODIFYTIME() == null || this.get(i).getMODIFYTIME().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getMODIFYTIME());
			}
			if(this.get(i).getStandByFlag1() == null || this.get(i).getStandByFlag1().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getStandByFlag1());
			}
			if(this.get(i).getStandByFlag2() == null || this.get(i).getStandByFlag2().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getStandByFlag2());
			}
			if(this.get(i).getStandByFlag3() == null || this.get(i).getStandByFlag3().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getStandByFlag3());
			}
			// set where condition
			if(this.get(i).getProductCode() == null || this.get(i).getProductCode().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getProductCode());
			}
			if(this.get(i).getIProductCode() == null || this.get(i).getIProductCode().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getIProductCode());
			}
			if(this.get(i).getIsEffectiveDate() == null || this.get(i).getIsEffectiveDate().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getIsEffectiveDate());
			}
			if(this.get(i).getIsSpouseCode() == null || this.get(i).getIsSpouseCode().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getIsSpouseCode());
			}
			if(this.get(i).getIsStaffCode() == null || this.get(i).getIsStaffCode().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getIsStaffCode());
			}
			if(this.get(i).getIsJoinCode() == null || this.get(i).getIsJoinCode().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getIsJoinCode());
			}
			if(this.get(i).getIsPremPayPeriod() == null || this.get(i).getIsPremPayPeriod().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getIsPremPayPeriod());
			}
			if(this.get(i).getIsBenefitOption() == null || this.get(i).getIsBenefitOption().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getIsBenefitOption());
			}
			if(this.get(i).getIsBenefitPeriod() == null || this.get(i).getIsBenefitPeriod().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getIsBenefitPeriod());
			}
			if(this.get(i).getIsChannel() == null || this.get(i).getIsChannel().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getIsChannel());
			}
			if(this.get(i).getIsCurrency() == null || this.get(i).getIsCurrency().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getIsCurrency());
			}
			if(this.get(i).getIsPar() == null || this.get(i).getIsPar().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getIsPar());
			}
			if(this.get(i).getIsUreUWType() == null || this.get(i).getIsUreUWType().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getIsUreUWType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("InterfacePToSInfo");
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
			tError.moduleName = "InterfacePToSInfoDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO InterfacePToSInfo(ProductCode ,ProductCHName ,ProductENName ,IProductCode ,IProductCHName ,IProductENName ,IsEffectiveDate ,EffectiveDate ,EffectiveEndDate ,IsSpouseCode ,SpouseCode ,IsStaffCode ,StaffCode ,IsJoinCode ,JoinCode ,IsPremPayPeriod ,PremPayPeriodType ,PremPayPeriod ,IsBenefitOption ,BenefitOptionType ,BenefitOption ,IsBenefitPeriod ,BenefitPeriodType ,BenefitPeriod ,IsChannel ,Channel ,IsCurrency ,Currency ,IsPar ,Par ,IsUreUWType ,UreUWType ,BatchNo ,OPERATOR ,MAKEDATE ,MAKETIME ,MODIFYDATE ,MODIFYTIME ,StandByFlag1 ,StandByFlag2 ,StandByFlag3) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getProductCode() == null || this.get(i).getProductCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getProductCode());
			}
			if(this.get(i).getProductCHName() == null || this.get(i).getProductCHName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getProductCHName());
			}
			if(this.get(i).getProductENName() == null || this.get(i).getProductENName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getProductENName());
			}
			if(this.get(i).getIProductCode() == null || this.get(i).getIProductCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getIProductCode());
			}
			if(this.get(i).getIProductCHName() == null || this.get(i).getIProductCHName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getIProductCHName());
			}
			if(this.get(i).getIProductENName() == null || this.get(i).getIProductENName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getIProductENName());
			}
			if(this.get(i).getIsEffectiveDate() == null || this.get(i).getIsEffectiveDate().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getIsEffectiveDate());
			}
			if(this.get(i).getEffectiveDate() == null || this.get(i).getEffectiveDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getEffectiveDate()));
			}
			if(this.get(i).getEffectiveEndDate() == null || this.get(i).getEffectiveEndDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getEffectiveEndDate()));
			}
			if(this.get(i).getIsSpouseCode() == null || this.get(i).getIsSpouseCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getIsSpouseCode());
			}
			if(this.get(i).getSpouseCode() == null || this.get(i).getSpouseCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSpouseCode());
			}
			if(this.get(i).getIsStaffCode() == null || this.get(i).getIsStaffCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIsStaffCode());
			}
			if(this.get(i).getStaffCode() == null || this.get(i).getStaffCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getStaffCode());
			}
			if(this.get(i).getIsJoinCode() == null || this.get(i).getIsJoinCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getIsJoinCode());
			}
			if(this.get(i).getJoinCode() == null || this.get(i).getJoinCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getJoinCode());
			}
			if(this.get(i).getIsPremPayPeriod() == null || this.get(i).getIsPremPayPeriod().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getIsPremPayPeriod());
			}
			if(this.get(i).getPremPayPeriodType() == null || this.get(i).getPremPayPeriodType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getPremPayPeriodType());
			}
			pstmt.setInt(18, this.get(i).getPremPayPeriod());
			if(this.get(i).getIsBenefitOption() == null || this.get(i).getIsBenefitOption().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getIsBenefitOption());
			}
			if(this.get(i).getBenefitOptionType() == null || this.get(i).getBenefitOptionType().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getBenefitOptionType());
			}
			if(this.get(i).getBenefitOption() == null || this.get(i).getBenefitOption().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getBenefitOption());
			}
			if(this.get(i).getIsBenefitPeriod() == null || this.get(i).getIsBenefitPeriod().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getIsBenefitPeriod());
			}
			if(this.get(i).getBenefitPeriodType() == null || this.get(i).getBenefitPeriodType().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getBenefitPeriodType());
			}
			pstmt.setInt(24, this.get(i).getBenefitPeriod());
			if(this.get(i).getIsChannel() == null || this.get(i).getIsChannel().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getIsChannel());
			}
			if(this.get(i).getChannel() == null || this.get(i).getChannel().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getChannel());
			}
			if(this.get(i).getIsCurrency() == null || this.get(i).getIsCurrency().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getIsCurrency());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getCurrency());
			}
			if(this.get(i).getIsPar() == null || this.get(i).getIsPar().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getIsPar());
			}
			if(this.get(i).getPar() == null || this.get(i).getPar().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getPar());
			}
			if(this.get(i).getIsUreUWType() == null || this.get(i).getIsUreUWType().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getIsUreUWType());
			}
			if(this.get(i).getUreUWType() == null || this.get(i).getUreUWType().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getUreUWType());
			}
			pstmt.setInt(33, this.get(i).getBatchNo());
			if(this.get(i).getOPERATOR() == null || this.get(i).getOPERATOR().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOPERATOR());
			}
			if(this.get(i).getMAKEDATE() == null || this.get(i).getMAKEDATE().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getMAKEDATE()));
			}
			if(this.get(i).getMAKETIME() == null || this.get(i).getMAKETIME().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getMAKETIME());
			}
			if(this.get(i).getMODIFYDATE() == null || this.get(i).getMODIFYDATE().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getMODIFYDATE()));
			}
			if(this.get(i).getMODIFYTIME() == null || this.get(i).getMODIFYTIME().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getMODIFYTIME());
			}
			if(this.get(i).getStandByFlag1() == null || this.get(i).getStandByFlag1().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getStandByFlag1());
			}
			if(this.get(i).getStandByFlag2() == null || this.get(i).getStandByFlag2().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getStandByFlag2());
			}
			if(this.get(i).getStandByFlag3() == null || this.get(i).getStandByFlag3().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getStandByFlag3());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("InterfacePToSInfo");
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
			tError.moduleName = "InterfacePToSInfoDBSet";
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

