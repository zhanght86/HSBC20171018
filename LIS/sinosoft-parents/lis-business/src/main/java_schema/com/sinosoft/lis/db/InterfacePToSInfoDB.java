

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;

import com.sinosoft.lis.schema.InterfacePToSInfoSchema;
import com.sinosoft.lis.schema.InterfacePToSInfoSchema;
import com.sinosoft.lis.vschema.InterfacePToSInfoSet;
import com.sinosoft.lis.vschema.InterfacePToSInfoSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: InterfacePToSInfoDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class InterfacePToSInfoDB extends InterfacePToSInfoSchema
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 错误信息

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;
	private Statement mStatement = null;
	// @Constructor
	public InterfacePToSInfoDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "InterfacePToSInfo" );
		mflag = true;
	}

	public InterfacePToSInfoDB()
	{
		con = null;
		db = new DBOper( "InterfacePToSInfo" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		InterfacePToSInfoSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		InterfacePToSInfoSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("DELETE FROM InterfacePToSInfo WHERE  ProductCode = ? AND IProductCode = ? AND IsEffectiveDate = ? AND IsSpouseCode = ? AND IsStaffCode = ? AND IsJoinCode = ? AND IsPremPayPeriod = ? AND IsBenefitOption = ? AND IsBenefitPeriod = ? AND IsChannel = ? AND IsCurrency = ? AND IsPar = ? AND IsUreUWType = ?");
			if(this.getProductCode() == null || this.getProductCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getProductCode());
			}
			if(this.getIProductCode() == null || this.getIProductCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getIProductCode());
			}
			if(this.getIsEffectiveDate() == null || this.getIsEffectiveDate().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getIsEffectiveDate());
			}
			if(this.getIsSpouseCode() == null || this.getIsSpouseCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getIsSpouseCode());
			}
			if(this.getIsStaffCode() == null || this.getIsStaffCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getIsStaffCode());
			}
			if(this.getIsJoinCode() == null || this.getIsJoinCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getIsJoinCode());
			}
			if(this.getIsPremPayPeriod() == null || this.getIsPremPayPeriod().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getIsPremPayPeriod());
			}
			if(this.getIsBenefitOption() == null || this.getIsBenefitOption().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getIsBenefitOption());
			}
			if(this.getIsBenefitPeriod() == null || this.getIsBenefitPeriod().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getIsBenefitPeriod());
			}
			if(this.getIsChannel() == null || this.getIsChannel().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getIsChannel());
			}
			if(this.getIsCurrency() == null || this.getIsCurrency().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getIsCurrency());
			}
			if(this.getIsPar() == null || this.getIsPar().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getIsPar());
			}
			if(this.getIsUreUWType() == null || this.getIsUreUWType().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getIsUreUWType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("InterfacePToSInfo");
		sqlObj.setSQL(4, this);
		sqlObj.getSQL();

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("InterfacePToSInfo");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE InterfacePToSInfo SET  ProductCode = ? , ProductCHName = ? , ProductENName = ? , IProductCode = ? , IProductCHName = ? , IProductENName = ? , IsEffectiveDate = ? , EffectiveDate = ? , EffectiveEndDate = ? , IsSpouseCode = ? , SpouseCode = ? , IsStaffCode = ? , StaffCode = ? , IsJoinCode = ? , JoinCode = ? , IsPremPayPeriod = ? , PremPayPeriodType = ? , PremPayPeriod = ? , IsBenefitOption = ? , BenefitOptionType = ? , BenefitOption = ? , IsBenefitPeriod = ? , BenefitPeriodType = ? , BenefitPeriod = ? , IsChannel = ? , Channel = ? , IsCurrency = ? , Currency = ? , IsPar = ? , Par = ? , IsUreUWType = ? , UreUWType = ? , BatchNo = ? , OPERATOR = ? , MAKEDATE = ? , MAKETIME = ? , MODIFYDATE = ? , MODIFYTIME = ? , StandByFlag1 = ? , StandByFlag2 = ? , StandByFlag3 = ? WHERE  ProductCode = ? AND IProductCode = ? AND IsEffectiveDate = ? AND IsSpouseCode = ? AND IsStaffCode = ? AND IsJoinCode = ? AND IsPremPayPeriod = ? AND IsBenefitOption = ? AND IsBenefitPeriod = ? AND IsChannel = ? AND IsCurrency = ? AND IsPar = ? AND IsUreUWType = ?");
			if(this.getProductCode() == null || this.getProductCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getProductCode());
			}
			if(this.getProductCHName() == null || this.getProductCHName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getProductCHName());
			}
			if(this.getProductENName() == null || this.getProductENName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getProductENName());
			}
			if(this.getIProductCode() == null || this.getIProductCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getIProductCode());
			}
			if(this.getIProductCHName() == null || this.getIProductCHName().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getIProductCHName());
			}
			if(this.getIProductENName() == null || this.getIProductENName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getIProductENName());
			}
			if(this.getIsEffectiveDate() == null || this.getIsEffectiveDate().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getIsEffectiveDate());
			}
			if(this.getEffectiveDate() == null || this.getEffectiveDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getEffectiveDate()));
			}
			if(this.getEffectiveEndDate() == null || this.getEffectiveEndDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getEffectiveEndDate()));
			}
			if(this.getIsSpouseCode() == null || this.getIsSpouseCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getIsSpouseCode());
			}
			if(this.getSpouseCode() == null || this.getSpouseCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getSpouseCode());
			}
			if(this.getIsStaffCode() == null || this.getIsStaffCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getIsStaffCode());
			}
			if(this.getStaffCode() == null || this.getStaffCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getStaffCode());
			}
			if(this.getIsJoinCode() == null || this.getIsJoinCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getIsJoinCode());
			}
			if(this.getJoinCode() == null || this.getJoinCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getJoinCode());
			}
			if(this.getIsPremPayPeriod() == null || this.getIsPremPayPeriod().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getIsPremPayPeriod());
			}
			if(this.getPremPayPeriodType() == null || this.getPremPayPeriodType().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPremPayPeriodType());
			}
			pstmt.setInt(18, this.getPremPayPeriod());
			if(this.getIsBenefitOption() == null || this.getIsBenefitOption().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getIsBenefitOption());
			}
			if(this.getBenefitOptionType() == null || this.getBenefitOptionType().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getBenefitOptionType());
			}
			if(this.getBenefitOption() == null || this.getBenefitOption().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getBenefitOption());
			}
			if(this.getIsBenefitPeriod() == null || this.getIsBenefitPeriod().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getIsBenefitPeriod());
			}
			if(this.getBenefitPeriodType() == null || this.getBenefitPeriodType().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getBenefitPeriodType());
			}
			pstmt.setInt(24, this.getBenefitPeriod());
			if(this.getIsChannel() == null || this.getIsChannel().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getIsChannel());
			}
			if(this.getChannel() == null || this.getChannel().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getChannel());
			}
			if(this.getIsCurrency() == null || this.getIsCurrency().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getIsCurrency());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getCurrency());
			}
			if(this.getIsPar() == null || this.getIsPar().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getIsPar());
			}
			if(this.getPar() == null || this.getPar().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getPar());
			}
			if(this.getIsUreUWType() == null || this.getIsUreUWType().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getIsUreUWType());
			}
			if(this.getUreUWType() == null || this.getUreUWType().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getUreUWType());
			}
			pstmt.setInt(33, this.getBatchNo());
			if(this.getOPERATOR() == null || this.getOPERATOR().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getOPERATOR());
			}
			if(this.getMAKEDATE() == null || this.getMAKEDATE().equals("null")) {
				pstmt.setNull(35, 91);
			} else {
				pstmt.setDate(35, Date.valueOf(this.getMAKEDATE()));
			}
			if(this.getMAKETIME() == null || this.getMAKETIME().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getMAKETIME());
			}
			if(this.getMODIFYDATE() == null || this.getMODIFYDATE().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getMODIFYDATE()));
			}
			if(this.getMODIFYTIME() == null || this.getMODIFYTIME().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getMODIFYTIME());
			}
			if(this.getStandByFlag1() == null || this.getStandByFlag1().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getStandByFlag1());
			}
			if(this.getStandByFlag2() == null || this.getStandByFlag2().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getStandByFlag2());
			}
			if(this.getStandByFlag3() == null || this.getStandByFlag3().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getStandByFlag3());
			}
			// set where condition
			if(this.getProductCode() == null || this.getProductCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getProductCode());
			}
			if(this.getIProductCode() == null || this.getIProductCode().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getIProductCode());
			}
			if(this.getIsEffectiveDate() == null || this.getIsEffectiveDate().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getIsEffectiveDate());
			}
			if(this.getIsSpouseCode() == null || this.getIsSpouseCode().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getIsSpouseCode());
			}
			if(this.getIsStaffCode() == null || this.getIsStaffCode().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getIsStaffCode());
			}
			if(this.getIsJoinCode() == null || this.getIsJoinCode().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getIsJoinCode());
			}
			if(this.getIsPremPayPeriod() == null || this.getIsPremPayPeriod().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getIsPremPayPeriod());
			}
			if(this.getIsBenefitOption() == null || this.getIsBenefitOption().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getIsBenefitOption());
			}
			if(this.getIsBenefitPeriod() == null || this.getIsBenefitPeriod().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getIsBenefitPeriod());
			}
			if(this.getIsChannel() == null || this.getIsChannel().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getIsChannel());
			}
			if(this.getIsCurrency() == null || this.getIsCurrency().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getIsCurrency());
			}
			if(this.getIsPar() == null || this.getIsPar().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getIsPar());
			}
			if(this.getIsUreUWType() == null || this.getIsUreUWType().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getIsUreUWType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("InterfacePToSInfo");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO InterfacePToSInfo(ProductCode ,ProductCHName ,ProductENName ,IProductCode ,IProductCHName ,IProductENName ,IsEffectiveDate ,EffectiveDate ,EffectiveEndDate ,IsSpouseCode ,SpouseCode ,IsStaffCode ,StaffCode ,IsJoinCode ,JoinCode ,IsPremPayPeriod ,PremPayPeriodType ,PremPayPeriod ,IsBenefitOption ,BenefitOptionType ,BenefitOption ,IsBenefitPeriod ,BenefitPeriodType ,BenefitPeriod ,IsChannel ,Channel ,IsCurrency ,Currency ,IsPar ,Par ,IsUreUWType ,UreUWType ,BatchNo ,OPERATOR ,MAKEDATE ,MAKETIME ,MODIFYDATE ,MODIFYTIME ,StandByFlag1 ,StandByFlag2 ,StandByFlag3) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getProductCode() == null || this.getProductCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getProductCode());
			}
			if(this.getProductCHName() == null || this.getProductCHName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getProductCHName());
			}
			if(this.getProductENName() == null || this.getProductENName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getProductENName());
			}
			if(this.getIProductCode() == null || this.getIProductCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getIProductCode());
			}
			if(this.getIProductCHName() == null || this.getIProductCHName().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getIProductCHName());
			}
			if(this.getIProductENName() == null || this.getIProductENName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getIProductENName());
			}
			if(this.getIsEffectiveDate() == null || this.getIsEffectiveDate().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getIsEffectiveDate());
			}
			if(this.getEffectiveDate() == null || this.getEffectiveDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getEffectiveDate()));
			}
			if(this.getEffectiveEndDate() == null || this.getEffectiveEndDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getEffectiveEndDate()));
			}
			if(this.getIsSpouseCode() == null || this.getIsSpouseCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getIsSpouseCode());
			}
			if(this.getSpouseCode() == null || this.getSpouseCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getSpouseCode());
			}
			if(this.getIsStaffCode() == null || this.getIsStaffCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getIsStaffCode());
			}
			if(this.getStaffCode() == null || this.getStaffCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getStaffCode());
			}
			if(this.getIsJoinCode() == null || this.getIsJoinCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getIsJoinCode());
			}
			if(this.getJoinCode() == null || this.getJoinCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getJoinCode());
			}
			if(this.getIsPremPayPeriod() == null || this.getIsPremPayPeriod().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getIsPremPayPeriod());
			}
			if(this.getPremPayPeriodType() == null || this.getPremPayPeriodType().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPremPayPeriodType());
			}
			pstmt.setInt(18, this.getPremPayPeriod());
			if(this.getIsBenefitOption() == null || this.getIsBenefitOption().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getIsBenefitOption());
			}
			if(this.getBenefitOptionType() == null || this.getBenefitOptionType().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getBenefitOptionType());
			}
			if(this.getBenefitOption() == null || this.getBenefitOption().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getBenefitOption());
			}
			if(this.getIsBenefitPeriod() == null || this.getIsBenefitPeriod().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getIsBenefitPeriod());
			}
			if(this.getBenefitPeriodType() == null || this.getBenefitPeriodType().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getBenefitPeriodType());
			}
			pstmt.setInt(24, this.getBenefitPeriod());
			if(this.getIsChannel() == null || this.getIsChannel().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getIsChannel());
			}
			if(this.getChannel() == null || this.getChannel().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getChannel());
			}
			if(this.getIsCurrency() == null || this.getIsCurrency().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getIsCurrency());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getCurrency());
			}
			if(this.getIsPar() == null || this.getIsPar().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getIsPar());
			}
			if(this.getPar() == null || this.getPar().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getPar());
			}
			if(this.getIsUreUWType() == null || this.getIsUreUWType().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getIsUreUWType());
			}
			if(this.getUreUWType() == null || this.getUreUWType().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getUreUWType());
			}
			pstmt.setInt(33, this.getBatchNo());
			if(this.getOPERATOR() == null || this.getOPERATOR().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getOPERATOR());
			}
			if(this.getMAKEDATE() == null || this.getMAKEDATE().equals("null")) {
				pstmt.setNull(35, 91);
			} else {
				pstmt.setDate(35, Date.valueOf(this.getMAKEDATE()));
			}
			if(this.getMAKETIME() == null || this.getMAKETIME().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getMAKETIME());
			}
			if(this.getMODIFYDATE() == null || this.getMODIFYDATE().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getMODIFYDATE()));
			}
			if(this.getMODIFYTIME() == null || this.getMODIFYTIME().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getMODIFYTIME());
			}
			if(this.getStandByFlag1() == null || this.getStandByFlag1().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getStandByFlag1());
			}
			if(this.getStandByFlag2() == null || this.getStandByFlag2().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getStandByFlag2());
			}
			if(this.getStandByFlag3() == null || this.getStandByFlag3().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getStandByFlag3());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean getInfo()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("SELECT * FROM InterfacePToSInfo WHERE  ProductCode = ? AND IProductCode = ? AND IsEffectiveDate = ? AND IsSpouseCode = ? AND IsStaffCode = ? AND IsJoinCode = ? AND IsPremPayPeriod = ? AND IsBenefitOption = ? AND IsBenefitPeriod = ? AND IsChannel = ? AND IsCurrency = ? AND IsPar = ? AND IsUreUWType = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getProductCode() == null || this.getProductCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getProductCode());
			}
			if(this.getIProductCode() == null || this.getIProductCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getIProductCode());
			}
			if(this.getIsEffectiveDate() == null || this.getIsEffectiveDate().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getIsEffectiveDate());
			}
			if(this.getIsSpouseCode() == null || this.getIsSpouseCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getIsSpouseCode());
			}
			if(this.getIsStaffCode() == null || this.getIsStaffCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getIsStaffCode());
			}
			if(this.getIsJoinCode() == null || this.getIsJoinCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getIsJoinCode());
			}
			if(this.getIsPremPayPeriod() == null || this.getIsPremPayPeriod().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getIsPremPayPeriod());
			}
			if(this.getIsBenefitOption() == null || this.getIsBenefitOption().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getIsBenefitOption());
			}
			if(this.getIsBenefitPeriod() == null || this.getIsBenefitPeriod().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getIsBenefitPeriod());
			}
			if(this.getIsChannel() == null || this.getIsChannel().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getIsChannel());
			}
			if(this.getIsCurrency() == null || this.getIsCurrency().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getIsCurrency());
			}
			if(this.getIsPar() == null || this.getIsPar().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getIsPar());
			}
			if(this.getIsUreUWType() == null || this.getIsUreUWType().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getIsUreUWType());
			}
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "InterfacePToSInfoDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors .addOneError(tError);

					try{ rs.close(); } catch( Exception ex ) {}
					try{ pstmt.close(); } catch( Exception ex1 ) {}

					if (!mflag)
					{
						try
						{
							con.close();
						}
						catch(Exception et){}
					}
					return false;
				}
				break;
			}
			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if( i == 0 )
			{
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

	public InterfacePToSInfoSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		InterfacePToSInfoSet aInterfacePToSInfoSet = new InterfacePToSInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("InterfacePToSInfo");
			InterfacePToSInfoSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				InterfacePToSInfoSchema s1 = new InterfacePToSInfoSchema();
				s1.setSchema(rs,i);
				aInterfacePToSInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aInterfacePToSInfoSet;
	}

	public InterfacePToSInfoSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		InterfacePToSInfoSet aInterfacePToSInfoSet = new InterfacePToSInfoSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				InterfacePToSInfoSchema s1 = new InterfacePToSInfoSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "InterfacePToSInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aInterfacePToSInfoSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch(Exception ex2) {}
			try{ pstmt.close(); } catch(Exception ex3) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e) {}
		}

		return aInterfacePToSInfoSet;
	}
	
	
	public InterfacePToSInfoSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		InterfacePToSInfoSet aInterfacePToSInfoSet = new InterfacePToSInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;
				InterfacePToSInfoSchema s1 = new InterfacePToSInfoSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "InterfacePToSInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aInterfacePToSInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aInterfacePToSInfoSet;
	}

	public InterfacePToSInfoSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		InterfacePToSInfoSet aInterfacePToSInfoSet = new InterfacePToSInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("InterfacePToSInfo");
			InterfacePToSInfoSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				InterfacePToSInfoSchema s1 = new InterfacePToSInfoSchema();
				s1.setSchema(rs,i);
				aInterfacePToSInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aInterfacePToSInfoSet;
	}

	public InterfacePToSInfoSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		InterfacePToSInfoSet aInterfacePToSInfoSet = new InterfacePToSInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				InterfacePToSInfoSchema s1 = new InterfacePToSInfoSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "InterfacePToSInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aInterfacePToSInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aInterfacePToSInfoSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("InterfacePToSInfo");
			InterfacePToSInfoSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update InterfacePToSInfo " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "InterfacePToSInfoDB";
				tError.functionName = "update";
				tError.errorMessage = "更新数据失败!";
				this.mErrors .addOneError(tError);

				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

/**
 * 准备数据查询条件
 * @param strSQL String
 * @return boolean
 */
public boolean prepareData(String strSQL)
{
    if (mResultSet != null)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "InterfacePToSInfoDB";
        tError.functionName = "prepareData";
        tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
        this.mErrors.addOneError(tError);
        return false;
    }

    if (!mflag)
    {
        con = DBConnPool.getConnection();
    }
    try
    {
        mStatement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));
    }
    catch (Exception e)
    {
        e.printStackTrace();
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "InterfacePToSInfoDB";
        tError.functionName = "prepareData";
        tError.errorMessage = e.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }

    if (!mflag)
    {
        try
        {
            con.close();
        }
        catch (Exception e)
        {}
    }
    return true;
}

/**
 * 获取数据集
 * @return boolean
 */
public boolean hasMoreData()
{
    boolean flag = true;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "InterfacePToSInfoDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return false;
    }
    try
    {
        flag = mResultSet.next();
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "InterfacePToSInfoDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }
    return flag;
}
/**
 * 获取定量数据
 * @return InterfacePToSInfoSet
 */
public InterfacePToSInfoSet getData()
{
    int tCount = 0;
    InterfacePToSInfoSet tInterfacePToSInfoSet = new InterfacePToSInfoSet();
    InterfacePToSInfoSchema tInterfacePToSInfoSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "InterfacePToSInfoDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tInterfacePToSInfoSchema = new InterfacePToSInfoSchema();
        tInterfacePToSInfoSchema.setSchema(mResultSet, 1);
        tInterfacePToSInfoSet.add(tInterfacePToSInfoSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tInterfacePToSInfoSchema = new InterfacePToSInfoSchema();
                tInterfacePToSInfoSchema.setSchema(mResultSet, 1);
                tInterfacePToSInfoSet.add(tInterfacePToSInfoSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "InterfacePToSInfoDB";
        tError.functionName = "getData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return null;
    }
    return tInterfacePToSInfoSet;
}
/**
 * 关闭数据集
 * @return boolean
 */
public boolean closeData()
{
    boolean flag = true;
    try
    {
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "InterfacePToSInfoDB";
            tError.functionName = "closeData";
            tError.errorMessage = "数据集已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mResultSet.close();
            mResultSet = null;
        }
    }
    catch (Exception ex2)
    {
        CError tError = new CError();
        tError.moduleName = "InterfacePToSInfoDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex2.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    try
    {
        if (null == mStatement)
        {
            CError tError = new CError();
            tError.moduleName = "InterfacePToSInfoDB";
            tError.functionName = "closeData";
            tError.errorMessage = "语句已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mStatement.close();
            mStatement = null;
        }
    }
    catch (Exception ex3)
    {
        CError tError = new CError();
        tError.moduleName = "InterfacePToSInfoDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}

