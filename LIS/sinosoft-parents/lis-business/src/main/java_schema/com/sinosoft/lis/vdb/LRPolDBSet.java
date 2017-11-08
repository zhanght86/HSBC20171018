/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LRPolSchema;
import com.sinosoft.lis.vschema.LRPolSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LRPolDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LRPolDBSet extends LRPolSet
{
private static Logger logger = Logger.getLogger(LRPolDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LRPolDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LRPol");
		mflag = true;
	}

	public LRPolDBSet()
	{
		db = new DBOper( "LRPol" );
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
			tError.moduleName = "LRPolDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LRPol WHERE  PolNo = ? AND ReinsureCom = ? AND ReinsurItem = ? AND InsuredYear = ? AND RiskCalSort = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPolNo());
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, StrTool.space1(this.get(i).getReinsurItem(), 1));
			}
			pstmt.setInt(4, this.get(i).getInsuredYear());
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, StrTool.space1(this.get(i).getRiskCalSort(), 1));
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LRPol");
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
			tError.moduleName = "LRPolDBSet";
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
			pstmt = con.prepareStatement("UPDATE LRPol SET  PolNo = ? , ReinsureCom = ? , ReinsurItem = ? , InsuredYear = ? , ContNo = ? , GrpPolNo = ? , ProposalNo = ? , MainPolNo = ? , MasterPolNo = ? , RiskCode = ? , RiskVersion = ? , InsuredNo = ? , InsuredName = ? , InsuredSex = ? , InsuredBirthday = ? , InsuredAppAge = ? , Years = ? , CValiDate = ? , EndDate = ? , StandPrem = ? , Prem = ? , SumPrem = ? , Amnt = ? , RiskAmnt = ? , PayIntv = ? , PayMode = ? , PayYears = ? , PayEndYearFlag = ? , PayEndYear = ? , InsuYearFlag = ? , InsuYear = ? , ProtItem = ? , CessStart = ? , CessEnd = ? , EnterCA = ? , CessionRate = ? , CessionAmount = ? , CessPremRate = ? , CessPrem = ? , CessCommRate = ? , CessComm = ? , ExPrem = ? , ExCessPremRate = ? , ExCessPrem = ? , ExCessComm = ? , ExcessCommRate = ? , PolStat = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , SignDate = ? , SumRiskAmount = ? , NowRiskAmount = ? , RiskCalSort = ? , AppntNo = ? , AppntName = ? , AppntType = ? , SaleChnl = ? , OldPolNo = ? , TransSaleChnl = ? , DutyCode = ? WHERE  PolNo = ? AND ReinsureCom = ? AND ReinsurItem = ? AND InsuredYear = ? AND RiskCalSort = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPolNo());
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getReinsurItem());
			}
			pstmt.setInt(4, this.get(i).getInsuredYear());
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getProposalNo() == null || this.get(i).getProposalNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getProposalNo());
			}
			if(this.get(i).getMainPolNo() == null || this.get(i).getMainPolNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMainPolNo());
			}
			if(this.get(i).getMasterPolNo() == null || this.get(i).getMasterPolNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMasterPolNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRiskVersion());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInsuredName());
			}
			if(this.get(i).getInsuredSex() == null || this.get(i).getInsuredSex().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getInsuredSex());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getInsuredBirthday()));
			}
			pstmt.setInt(16, this.get(i).getInsuredAppAge());
			pstmt.setInt(17, this.get(i).getYears());
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getEndDate()));
			}
			pstmt.setDouble(20, this.get(i).getStandPrem());
			pstmt.setDouble(21, this.get(i).getPrem());
			pstmt.setDouble(22, this.get(i).getSumPrem());
			pstmt.setDouble(23, this.get(i).getAmnt());
			pstmt.setDouble(24, this.get(i).getRiskAmnt());
			pstmt.setInt(25, this.get(i).getPayIntv());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getPayMode());
			}
			pstmt.setInt(27, this.get(i).getPayYears());
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(29, this.get(i).getPayEndYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(31, this.get(i).getInsuYear());
			if(this.get(i).getProtItem() == null || this.get(i).getProtItem().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getProtItem());
			}
			if(this.get(i).getCessStart() == null || this.get(i).getCessStart().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getCessStart()));
			}
			if(this.get(i).getCessEnd() == null || this.get(i).getCessEnd().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getCessEnd()));
			}
			pstmt.setDouble(35, this.get(i).getEnterCA());
			pstmt.setDouble(36, this.get(i).getCessionRate());
			pstmt.setDouble(37, this.get(i).getCessionAmount());
			pstmt.setDouble(38, this.get(i).getCessPremRate());
			pstmt.setDouble(39, this.get(i).getCessPrem());
			pstmt.setDouble(40, this.get(i).getCessCommRate());
			pstmt.setDouble(41, this.get(i).getCessComm());
			pstmt.setDouble(42, this.get(i).getExPrem());
			pstmt.setDouble(43, this.get(i).getExCessPremRate());
			pstmt.setDouble(44, this.get(i).getExCessPrem());
			pstmt.setDouble(45, this.get(i).getExCessComm());
			pstmt.setDouble(46, this.get(i).getExcessCommRate());
			if(this.get(i).getPolStat() == null || this.get(i).getPolStat().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getPolStat());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(48,null);
			} else {
				pstmt.setDate(48, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(50,null);
			} else {
				pstmt.setDate(50, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getModifyTime());
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(52,null);
			} else {
				pstmt.setDate(52, Date.valueOf(this.get(i).getSignDate()));
			}
			pstmt.setDouble(53, this.get(i).getSumRiskAmount());
			pstmt.setDouble(54, this.get(i).getNowRiskAmount());
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getRiskCalSort());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getAppntNo());
			}
			if(this.get(i).getAppntName() == null || this.get(i).getAppntName().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getAppntName());
			}
			if(this.get(i).getAppntType() == null || this.get(i).getAppntType().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getAppntType());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getSaleChnl());
			}
			if(this.get(i).getOldPolNo() == null || this.get(i).getOldPolNo().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getOldPolNo());
			}
			if(this.get(i).getTransSaleChnl() == null || this.get(i).getTransSaleChnl().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getTransSaleChnl());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getDutyCode());
			}
			// set where condition
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getPolNo());
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, StrTool.space1(this.get(i).getReinsurItem(), 1));
			}
			pstmt.setInt(66, this.get(i).getInsuredYear());
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, StrTool.space1(this.get(i).getRiskCalSort(), 1));
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LRPol");
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
			tError.moduleName = "LRPolDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LRPol(PolNo ,ReinsureCom ,ReinsurItem ,InsuredYear ,ContNo ,GrpPolNo ,ProposalNo ,MainPolNo ,MasterPolNo ,RiskCode ,RiskVersion ,InsuredNo ,InsuredName ,InsuredSex ,InsuredBirthday ,InsuredAppAge ,Years ,CValiDate ,EndDate ,StandPrem ,Prem ,SumPrem ,Amnt ,RiskAmnt ,PayIntv ,PayMode ,PayYears ,PayEndYearFlag ,PayEndYear ,InsuYearFlag ,InsuYear ,ProtItem ,CessStart ,CessEnd ,EnterCA ,CessionRate ,CessionAmount ,CessPremRate ,CessPrem ,CessCommRate ,CessComm ,ExPrem ,ExCessPremRate ,ExCessPrem ,ExCessComm ,ExcessCommRate ,PolStat ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,SignDate ,SumRiskAmount ,NowRiskAmount ,RiskCalSort ,AppntNo ,AppntName ,AppntType ,SaleChnl ,OldPolNo ,TransSaleChnl ,DutyCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPolNo());
			}
			if(this.get(i).getReinsureCom() == null || this.get(i).getReinsureCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getReinsureCom());
			}
			if(this.get(i).getReinsurItem() == null || this.get(i).getReinsurItem().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getReinsurItem());
			}
			pstmt.setInt(4, this.get(i).getInsuredYear());
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getProposalNo() == null || this.get(i).getProposalNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getProposalNo());
			}
			if(this.get(i).getMainPolNo() == null || this.get(i).getMainPolNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMainPolNo());
			}
			if(this.get(i).getMasterPolNo() == null || this.get(i).getMasterPolNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMasterPolNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRiskVersion());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInsuredName());
			}
			if(this.get(i).getInsuredSex() == null || this.get(i).getInsuredSex().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getInsuredSex());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getInsuredBirthday()));
			}
			pstmt.setInt(16, this.get(i).getInsuredAppAge());
			pstmt.setInt(17, this.get(i).getYears());
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getEndDate()));
			}
			pstmt.setDouble(20, this.get(i).getStandPrem());
			pstmt.setDouble(21, this.get(i).getPrem());
			pstmt.setDouble(22, this.get(i).getSumPrem());
			pstmt.setDouble(23, this.get(i).getAmnt());
			pstmt.setDouble(24, this.get(i).getRiskAmnt());
			pstmt.setInt(25, this.get(i).getPayIntv());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getPayMode());
			}
			pstmt.setInt(27, this.get(i).getPayYears());
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(29, this.get(i).getPayEndYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(31, this.get(i).getInsuYear());
			if(this.get(i).getProtItem() == null || this.get(i).getProtItem().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getProtItem());
			}
			if(this.get(i).getCessStart() == null || this.get(i).getCessStart().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getCessStart()));
			}
			if(this.get(i).getCessEnd() == null || this.get(i).getCessEnd().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getCessEnd()));
			}
			pstmt.setDouble(35, this.get(i).getEnterCA());
			pstmt.setDouble(36, this.get(i).getCessionRate());
			pstmt.setDouble(37, this.get(i).getCessionAmount());
			pstmt.setDouble(38, this.get(i).getCessPremRate());
			pstmt.setDouble(39, this.get(i).getCessPrem());
			pstmt.setDouble(40, this.get(i).getCessCommRate());
			pstmt.setDouble(41, this.get(i).getCessComm());
			pstmt.setDouble(42, this.get(i).getExPrem());
			pstmt.setDouble(43, this.get(i).getExCessPremRate());
			pstmt.setDouble(44, this.get(i).getExCessPrem());
			pstmt.setDouble(45, this.get(i).getExCessComm());
			pstmt.setDouble(46, this.get(i).getExcessCommRate());
			if(this.get(i).getPolStat() == null || this.get(i).getPolStat().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getPolStat());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(48,null);
			} else {
				pstmt.setDate(48, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(50,null);
			} else {
				pstmt.setDate(50, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getModifyTime());
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(52,null);
			} else {
				pstmt.setDate(52, Date.valueOf(this.get(i).getSignDate()));
			}
			pstmt.setDouble(53, this.get(i).getSumRiskAmount());
			pstmt.setDouble(54, this.get(i).getNowRiskAmount());
			if(this.get(i).getRiskCalSort() == null || this.get(i).getRiskCalSort().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getRiskCalSort());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getAppntNo());
			}
			if(this.get(i).getAppntName() == null || this.get(i).getAppntName().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getAppntName());
			}
			if(this.get(i).getAppntType() == null || this.get(i).getAppntType().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getAppntType());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getSaleChnl());
			}
			if(this.get(i).getOldPolNo() == null || this.get(i).getOldPolNo().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getOldPolNo());
			}
			if(this.get(i).getTransSaleChnl() == null || this.get(i).getTransSaleChnl().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getTransSaleChnl());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LRPol");
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
			tError.moduleName = "LRPolDBSet";
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
