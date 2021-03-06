/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LPPENoticeSchema;
import com.sinosoft.lis.vschema.LPPENoticeSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPPENoticeDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全流程
 */
public class LPPENoticeDB extends LPPENoticeSchema
{
private static Logger logger = Logger.getLogger(LPPENoticeDB.class);

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
	public LPPENoticeDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LPPENotice" );
		mflag = true;
	}

	public LPPENoticeDB()
	{
		con = null;
		db = new DBOper( "LPPENotice" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPPENoticeSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPPENoticeSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
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
			pstmt = con.prepareStatement("DELETE FROM LPPENotice WHERE  EdorAcceptNo = ? AND EdorNo = ? AND ProposalContNo = ? AND PrtSeq = ?");
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getProposalContNo());
			}
			if(this.getPrtSeq() == null || this.getPrtSeq().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPrtSeq());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPPENotice");
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
		SQLString sqlObj = new SQLString("LPPENotice");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LPPENotice SET  EdorAcceptNo = ? , EdorNo = ? , GrpContNo = ? , ContNo = ? , ProposalContNo = ? , PrtSeq = ? , AppName = ? , RiskName = ? , PEDate = ? , CustomerNo = ? , Name = ? , PEAddress = ? , PEBeforeCond = ? , PrintFlag = ? , ManageCom = ? , AgentName = ? , AgentCode = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Remark = ? , PEResult = ? , GrpPrtSeq = ? , CustomerNoType = ? , PEReason = ? , HospitCode = ? WHERE  EdorAcceptNo = ? AND EdorNo = ? AND ProposalContNo = ? AND PrtSeq = ?");
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getProposalContNo());
			}
			if(this.getPrtSeq() == null || this.getPrtSeq().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPrtSeq());
			}
			if(this.getAppName() == null || this.getAppName().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAppName());
			}
			if(this.getRiskName() == null || this.getRiskName().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getRiskName());
			}
			if(this.getPEDate() == null || this.getPEDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getPEDate()));
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getCustomerNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getName());
			}
			if(this.getPEAddress() == null || this.getPEAddress().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getPEAddress());
			}
			if(this.getPEBeforeCond() == null || this.getPEBeforeCond().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getPEBeforeCond());
			}
			if(this.getPrintFlag() == null || this.getPrintFlag().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getPrintFlag());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getManageCom());
			}
			if(this.getAgentName() == null || this.getAgentName().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentName());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentCode());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getModifyTime());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getRemark());
			}
			if(this.getPEResult() == null || this.getPEResult().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPEResult());
			}
			if(this.getGrpPrtSeq() == null || this.getGrpPrtSeq().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getGrpPrtSeq());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getCustomerNoType());
			}
			if(this.getPEReason() == null || this.getPEReason().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getPEReason());
			}
			if(this.getHospitCode() == null || this.getHospitCode().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getHospitCode());
			}
			// set where condition
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getEdorAcceptNo());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getEdorNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getProposalContNo());
			}
			if(this.getPrtSeq() == null || this.getPrtSeq().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getPrtSeq());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
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
		SQLString sqlObj = new SQLString("LPPENotice");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPPENotice(EdorAcceptNo ,EdorNo ,GrpContNo ,ContNo ,ProposalContNo ,PrtSeq ,AppName ,RiskName ,PEDate ,CustomerNo ,Name ,PEAddress ,PEBeforeCond ,PrintFlag ,ManageCom ,AgentName ,AgentCode ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Remark ,PEResult ,GrpPrtSeq ,CustomerNoType ,PEReason ,HospitCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getProposalContNo());
			}
			if(this.getPrtSeq() == null || this.getPrtSeq().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPrtSeq());
			}
			if(this.getAppName() == null || this.getAppName().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAppName());
			}
			if(this.getRiskName() == null || this.getRiskName().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getRiskName());
			}
			if(this.getPEDate() == null || this.getPEDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getPEDate()));
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getCustomerNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getName());
			}
			if(this.getPEAddress() == null || this.getPEAddress().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getPEAddress());
			}
			if(this.getPEBeforeCond() == null || this.getPEBeforeCond().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getPEBeforeCond());
			}
			if(this.getPrintFlag() == null || this.getPrintFlag().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getPrintFlag());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getManageCom());
			}
			if(this.getAgentName() == null || this.getAgentName().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentName());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentCode());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getModifyTime());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getRemark());
			}
			if(this.getPEResult() == null || this.getPEResult().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPEResult());
			}
			if(this.getGrpPrtSeq() == null || this.getGrpPrtSeq().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getGrpPrtSeq());
			}
			if(this.getCustomerNoType() == null || this.getCustomerNoType().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getCustomerNoType());
			}
			if(this.getPEReason() == null || this.getPEReason().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getPEReason());
			}
			if(this.getHospitCode() == null || this.getHospitCode().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getHospitCode());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LPPENotice WHERE  EdorAcceptNo = ? AND EdorNo = ? AND ProposalContNo = ? AND PrtSeq = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getProposalContNo());
			}
			if(this.getPrtSeq() == null || this.getPrtSeq().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPrtSeq());
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
					tError.moduleName = "LPPENoticeDB";
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
			tError.moduleName = "LPPENoticeDB";
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

	public LPPENoticeSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPPENoticeSet aLPPENoticeSet = new LPPENoticeSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPPENotice");
			LPPENoticeSchema aSchema = this.getSchema();
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
				LPPENoticeSchema s1 = new LPPENoticeSchema();
				s1.setSchema(rs,i);
				aLPPENoticeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
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

		return aLPPENoticeSet;

	}

	public LPPENoticeSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPPENoticeSet aLPPENoticeSet = new LPPENoticeSet();

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
				LPPENoticeSchema s1 = new LPPENoticeSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPPENoticeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPPENoticeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
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

		return aLPPENoticeSet;
	}

	public LPPENoticeSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPPENoticeSet aLPPENoticeSet = new LPPENoticeSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPPENotice");
			LPPENoticeSchema aSchema = this.getSchema();
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

				LPPENoticeSchema s1 = new LPPENoticeSchema();
				s1.setSchema(rs,i);
				aLPPENoticeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
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

		return aLPPENoticeSet;

	}

	public LPPENoticeSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPPENoticeSet aLPPENoticeSet = new LPPENoticeSet();

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

				LPPENoticeSchema s1 = new LPPENoticeSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPPENoticeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPPENoticeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
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

		return aLPPENoticeSet;
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
			SQLString sqlObj = new SQLString("LPPENotice");
			LPPENoticeSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LPPENotice " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPPENoticeDB";
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
			tError.moduleName = "LPPENoticeDB";
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
        tError.moduleName = "LPPENoticeDB";
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
        tError.moduleName = "LPPENoticeDB";
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
        tError.moduleName = "LPPENoticeDB";
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
        tError.moduleName = "LPPENoticeDB";
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
 * @return LPPENoticeSet
 */
public LPPENoticeSet getData()
{
    int tCount = 0;
    LPPENoticeSet tLPPENoticeSet = new LPPENoticeSet();
    LPPENoticeSchema tLPPENoticeSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPPENoticeDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLPPENoticeSchema = new LPPENoticeSchema();
        tLPPENoticeSchema.setSchema(mResultSet, 1);
        tLPPENoticeSet.add(tLPPENoticeSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLPPENoticeSchema = new LPPENoticeSchema();
                tLPPENoticeSchema.setSchema(mResultSet, 1);
                tLPPENoticeSet.add(tLPPENoticeSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPPENoticeDB";
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
    return tLPPENoticeSet;
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
            tError.moduleName = "LPPENoticeDB";
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
        tError.moduleName = "LPPENoticeDB";
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
            tError.moduleName = "LPPENoticeDB";
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
        tError.moduleName = "LPPENoticeDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LPPENoticeSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPPENoticeSet aLPPENoticeSet = new LPPENoticeSet();

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
				LPPENoticeSchema s1 = new LPPENoticeSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPPENoticeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPPENoticeSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
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

		return aLPPENoticeSet;
	}

	public LPPENoticeSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPPENoticeSet aLPPENoticeSet = new LPPENoticeSet();

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

				LPPENoticeSchema s1 = new LPPENoticeSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPPENoticeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPPENoticeSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPENoticeDB";
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

		return aLPPENoticeSet; 
	}

}
