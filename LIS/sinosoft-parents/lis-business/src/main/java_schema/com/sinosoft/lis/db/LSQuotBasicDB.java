/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LSQuotBasicSchema;
import com.sinosoft.lis.vschema.LSQuotBasicSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LSQuotBasicDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotBasicDB extends LSQuotBasicSchema
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
	public LSQuotBasicDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LSQuotBasic" );
		mflag = true;
	}

	public LSQuotBasicDB()
	{
		con = null;
		db = new DBOper( "LSQuotBasic" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LSQuotBasicSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LSQuotBasicSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
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
			pstmt = con.prepareStatement("DELETE FROM LSQuotBasic WHERE  QuotNo = ? AND QuotBatNo = ?");
			if(this.getQuotNo() == null || this.getQuotNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getQuotNo());
			}
			pstmt.setInt(2, this.getQuotBatNo());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotBasic");
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
		SQLString sqlObj = new SQLString("LSQuotBasic");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LSQuotBasic SET  QuotNo = ? , QuotBatNo = ? , PreCustomerNo = ? , PreCustomerName = ? , IDType = ? , IDNo = ? , GrpNature = ? , BusiCategory = ? , Address = ? , CustomerIntro = ? , ProdType = ? , SaleChannel = ? , PremMode = ? , PrePrem = ? , RenewFlag = ? , BlanketFlag = ? , ElasticFlag = ? , PayIntv = ? , InsuPeriod = ? , InsuPeriodFlag = ? , ValDateType = ? , AppointValDate = ? , Coinsurance = ? , Segment1 = ? , Segment2 = ? , Segment3 = ? , QuotState = ? , FileReason = ? , FileDesc = ? , ManageCom = ? , ComCode = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  QuotNo = ? AND QuotBatNo = ?");
			if(this.getQuotNo() == null || this.getQuotNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getQuotNo());
			}
			pstmt.setInt(2, this.getQuotBatNo());
			if(this.getPreCustomerNo() == null || this.getPreCustomerNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPreCustomerNo());
			}
			if(this.getPreCustomerName() == null || this.getPreCustomerName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPreCustomerName());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getIDNo());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getGrpNature());
			}
			if(this.getBusiCategory() == null || this.getBusiCategory().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getBusiCategory());
			}
			if(this.getAddress() == null || this.getAddress().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAddress());
			}
			if(this.getCustomerIntro() == null || this.getCustomerIntro().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getCustomerIntro());
			}
			if(this.getProdType() == null || this.getProdType().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getProdType());
			}
			if(this.getSaleChannel() == null || this.getSaleChannel().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getSaleChannel());
			}
			if(this.getPremMode() == null || this.getPremMode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getPremMode());
			}
			pstmt.setDouble(14, this.getPrePrem());
			if(this.getRenewFlag() == null || this.getRenewFlag().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRenewFlag());
			}
			if(this.getBlanketFlag() == null || this.getBlanketFlag().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getBlanketFlag());
			}
			if(this.getElasticFlag() == null || this.getElasticFlag().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getElasticFlag());
			}
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getPayIntv());
			}
			pstmt.setInt(19, this.getInsuPeriod());
			if(this.getInsuPeriodFlag() == null || this.getInsuPeriodFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getInsuPeriodFlag());
			}
			if(this.getValDateType() == null || this.getValDateType().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getValDateType());
			}
			if(this.getAppointValDate() == null || this.getAppointValDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getAppointValDate()));
			}
			if(this.getCoinsurance() == null || this.getCoinsurance().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getCoinsurance());
			}
			if(this.getSegment1() == null || this.getSegment1().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getSegment1());
			}
			if(this.getSegment2() == null || this.getSegment2().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getSegment2());
			}
			if(this.getSegment3() == null || this.getSegment3().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getSegment3());
			}
			if(this.getQuotState() == null || this.getQuotState().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getQuotState());
			}
			if(this.getFileReason() == null || this.getFileReason().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getFileReason());
			}
			if(this.getFileDesc() == null || this.getFileDesc().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getFileDesc());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getComCode());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getModifyTime());
			}
			// set where condition
			if(this.getQuotNo() == null || this.getQuotNo().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getQuotNo());
			}
			pstmt.setInt(39, this.getQuotBatNo());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
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
		SQLString sqlObj = new SQLString("LSQuotBasic");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LSQuotBasic VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getQuotNo() == null || this.getQuotNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getQuotNo());
			}
			pstmt.setInt(2, this.getQuotBatNo());
			if(this.getPreCustomerNo() == null || this.getPreCustomerNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPreCustomerNo());
			}
			if(this.getPreCustomerName() == null || this.getPreCustomerName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPreCustomerName());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getIDNo());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getGrpNature());
			}
			if(this.getBusiCategory() == null || this.getBusiCategory().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getBusiCategory());
			}
			if(this.getAddress() == null || this.getAddress().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAddress());
			}
			if(this.getCustomerIntro() == null || this.getCustomerIntro().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getCustomerIntro());
			}
			if(this.getProdType() == null || this.getProdType().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getProdType());
			}
			if(this.getSaleChannel() == null || this.getSaleChannel().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getSaleChannel());
			}
			if(this.getPremMode() == null || this.getPremMode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getPremMode());
			}
			pstmt.setDouble(14, this.getPrePrem());
			if(this.getRenewFlag() == null || this.getRenewFlag().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRenewFlag());
			}
			if(this.getBlanketFlag() == null || this.getBlanketFlag().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getBlanketFlag());
			}
			if(this.getElasticFlag() == null || this.getElasticFlag().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getElasticFlag());
			}
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getPayIntv());
			}
			pstmt.setInt(19, this.getInsuPeriod());
			if(this.getInsuPeriodFlag() == null || this.getInsuPeriodFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getInsuPeriodFlag());
			}
			if(this.getValDateType() == null || this.getValDateType().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getValDateType());
			}
			if(this.getAppointValDate() == null || this.getAppointValDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getAppointValDate()));
			}
			if(this.getCoinsurance() == null || this.getCoinsurance().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getCoinsurance());
			}
			if(this.getSegment1() == null || this.getSegment1().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getSegment1());
			}
			if(this.getSegment2() == null || this.getSegment2().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getSegment2());
			}
			if(this.getSegment3() == null || this.getSegment3().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getSegment3());
			}
			if(this.getQuotState() == null || this.getQuotState().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getQuotState());
			}
			if(this.getFileReason() == null || this.getFileReason().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getFileReason());
			}
			if(this.getFileDesc() == null || this.getFileDesc().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getFileDesc());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getComCode());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LSQuotBasic WHERE  QuotNo = ? AND QuotBatNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getQuotNo() == null || this.getQuotNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getQuotNo());
			}
			pstmt.setInt(2, this.getQuotBatNo());
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LSQuotBasicDB";
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
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
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

	public LSQuotBasicSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LSQuotBasicSet aLSQuotBasicSet = new LSQuotBasicSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LSQuotBasic");
			LSQuotBasicSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				LSQuotBasicSchema s1 = new LSQuotBasicSchema();
				s1.setSchema(rs,i);
				aLSQuotBasicSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

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

		return aLSQuotBasicSet;
	}

	public LSQuotBasicSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LSQuotBasicSet aLSQuotBasicSet = new LSQuotBasicSet();

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
				LSQuotBasicSchema s1 = new LSQuotBasicSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LSQuotBasicDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLSQuotBasicSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
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

		return aLSQuotBasicSet;
	}

	public LSQuotBasicSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LSQuotBasicSet aLSQuotBasicSet = new LSQuotBasicSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LSQuotBasic");
			LSQuotBasicSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
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

				LSQuotBasicSchema s1 = new LSQuotBasicSchema();
				s1.setSchema(rs,i);
				aLSQuotBasicSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

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

		return aLSQuotBasicSet;
	}

	public LSQuotBasicSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LSQuotBasicSet aLSQuotBasicSet = new LSQuotBasicSet();

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

				LSQuotBasicSchema s1 = new LSQuotBasicSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LSQuotBasicDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLSQuotBasicSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
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

		return aLSQuotBasicSet;
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
			SQLString sqlObj = new SQLString("LSQuotBasic");
			LSQuotBasicSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LSQuotBasic " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LSQuotBasicDB";
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
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
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
        tError.moduleName = "LSQuotBasicDB";
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
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LSQuotBasicDB";
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
        tError.moduleName = "LSQuotBasicDB";
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
        tError.moduleName = "LSQuotBasicDB";
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
 * @return LSQuotBasicSet
 */
public LSQuotBasicSet getData()
{
    int tCount = 0;
    LSQuotBasicSet tLSQuotBasicSet = new LSQuotBasicSet();
    LSQuotBasicSchema tLSQuotBasicSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LSQuotBasicDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLSQuotBasicSchema = new LSQuotBasicSchema();
        tLSQuotBasicSchema.setSchema(mResultSet, 1);
        tLSQuotBasicSet.add(tLSQuotBasicSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLSQuotBasicSchema = new LSQuotBasicSchema();
                tLSQuotBasicSchema.setSchema(mResultSet, 1);
                tLSQuotBasicSet.add(tLSQuotBasicSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LSQuotBasicDB";
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
    return tLSQuotBasicSet;
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
            tError.moduleName = "LSQuotBasicDB";
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
        tError.moduleName = "LSQuotBasicDB";
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
            tError.moduleName = "LSQuotBasicDB";
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
        tError.moduleName = "LSQuotBasicDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LSQuotBasicSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LSQuotBasicSet aLSQuotBasicSet = new LSQuotBasicSet();

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
				LSQuotBasicSchema s1 = new LSQuotBasicSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LSQuotBasicDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLSQuotBasicSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
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

		return aLSQuotBasicSet;
	}

	public LSQuotBasicSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LSQuotBasicSet aLSQuotBasicSet = new LSQuotBasicSet();

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

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break; 
				}

				LSQuotBasicSchema s1 = new LSQuotBasicSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LSQuotBasicDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLSQuotBasicSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

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
			catch(Exception e){}
		}

		return aLSQuotBasicSet; 
	}

}
