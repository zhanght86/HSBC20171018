/*
 * @(#)BqContHangUpBL.java  2006-02-20
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.ContHangUpBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.schema.LCGrpContHangUpStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * Title: 业务系统
 * </p>
 * <p>
 * Description: 保全-保单挂起处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class BqContHangUpBL {
private static Logger logger = Logger.getLogger(BqContHangUpBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private List mBomList = new ArrayList();

//	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();

	private boolean mBomListFlag = false;
	public BqContHangUpBL(GlobalInput pGlobalInput) {
		mGlobalInput.setSchema(pGlobalInput);
	}

	/**
	 * 判断保单挂起状态
	 * 
	 * @param sContNo
	 *            保单号
	 * @param OtherNoType
	 *            申请号码类型 1-客户号 3-保单号
	 * @return boolean
	 */
	public boolean checkHangUpState(String sContNo, String OtherNoType) {
		ContHangUpBL tContHangUpBL = new ContHangUpBL(mGlobalInput);
		if (!tContHangUpBL.checkHangUpState(sContNo, "2")) // 2-保全
		{
			mErrors.copyAllErrors(tContHangUpBL.mErrors);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断团体保单挂起状态 add by zhangtao 2006-09-20
	 * 
	 * @param sContNo
	 *            保单号
	 * @param OtherNoType
	 *            申请号码类型 1-客户号 3-保单号
	 * @return boolean
	 */
	public boolean checkGrpHangUpState(String sGrpContNo, String OtherNoType) {
		ContHangUpBL tContHangUpBL = new ContHangUpBL(mGlobalInput);
		if (!tContHangUpBL.checkGrpHangUpState(sGrpContNo, "2")) // 2-保全
		{
			mErrors.copyAllErrors(tContHangUpBL.mErrors);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 设置保单挂起状态
	 * 
	 * @param sEdorAcceptNo
	 *            保全受理号
	 * @param sHangUpType
	 *            1-挂起 0-解挂
	 * @return boolean
	 */
	public boolean hangUpEdorAccept(String sEdorAcceptNo, String sHangUpType) {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(sEdorAcceptNo);
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.query();
		if (tLPEdorAppDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全受理失败!");
			return false;
		}
		if (tLPEdorAppSet == null || tLPEdorAppSet.size() != 1) {
			CError.buildErr(this, "保全受理号不存在!");
			return false;
		}

		String sOtherNo = tLPEdorAppSet.get(1).getOtherNo();
		String sOtherNoType = tLPEdorAppSet.get(1).getOtherNoType();

		if (sOtherNoType.equals("3")) // 个人保单号
		{
			return hangUpCont(sOtherNo, sHangUpType, sEdorAcceptNo);
		}
		if (sOtherNoType.equals("4")) // 团体保单号
		{
			return hangUpGrpCont(sOtherNo, sHangUpType, sEdorAcceptNo);
		}
		if (sOtherNoType.equals("1")) // 个人客户号
		{
			// ===del===zhangtao===2006-02-21==========原对整个保全受理下所有保单的挂起处理==BGN==
			// //查出该客户下所有保单，每个保单都添加该保全项目
			// LCContSet tLCContSet = getCustomerCont(sOtherNo, sEdorAppDate,
			// sAppType);
			// if (tLCContSet == null || tLCContSet.size() < 1)
			// {
			// CError.buildErr(this, "该客户没有相关有效保单!");
			// return false;
			// }
			// for (int k = 1; k <= tLCContSet.size(); k++)
			// {
			// if (!hangUpCont(tLCContSet.get(k).getContNo(), sHangUpType,
			// sEdorAcceptNo) )
			// {
			// return false;
			// }
			// }
			// ===del===zhangtao===2006-02-21==========原对整个保全受理下所有保单的挂起处理==END==

			ContHangUpBL tContHangUpBL = new ContHangUpBL(mGlobalInput);
			map = tContHangUpBL.unHangUpContByHangUpNo(sEdorAcceptNo, "2"); // 2-保全
		}

		return true;
	}

	/**
	 * 设置保单挂起状态
	 * 
	 * @param sContNo
	 *            保单号
	 * @param sHangUpType
	 *            1-挂起 0-解挂
	 * @param sEdorAcceptNo
	 *            保全受理号
	 * @return boolean
	 */
	public boolean hangUpCont(String sContNo, String sHangUpType,
			String sEdorAcceptNo) {
		// ===del===zhangtao===2006-02-21==========原挂起表处理方式=============BGN========
		// LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		// tLCContHangUpStateDB.setContNo(sContNo);
		// tLCContHangUpStateDB.setInsuredNo("000000");
		// tLCContHangUpStateDB.setPolNo("000000");
		// LCContHangUpStateSet tLCContHangUpStateSet =
		// tLCContHangUpStateDB.query();
		// if (tLCContHangUpStateDB.mErrors.needDealError())
		// {
		// CError.buildErr(this, "保单挂起状态查询失败!");
		// return false;
		// }
		// LCContHangUpStateSchema tLCContHangUpStateSchema;
		// if (tLCContHangUpStateSet == null || tLCContHangUpStateSet.size() <
		// 1)
		// {
		// tLCContHangUpStateSchema = new LCContHangUpStateSchema();
		// tLCContHangUpStateSchema.setContNo(sContNo);
		// tLCContHangUpStateSchema.setPolNo("000000");
		// tLCContHangUpStateSchema.setInsuredNo("000000");
		// tLCContHangUpStateSchema.setNBFlag("0"); //承保
		// tLCContHangUpStateSchema.setClaimFlag(sHangUpType); //理赔
		// tLCContHangUpStateSchema.setPosFlag(sHangUpType); //保全
		// tLCContHangUpStateSchema.setAgentFlag("0"); //渠道
		// tLCContHangUpStateSchema.setRNFlag(sHangUpType); //续期
		// //为方便理赔判断，添加该字段置值
		// if (sHangUpType.equals("0")) //解挂时消除
		// {
		// tLCContHangUpStateSchema.setStandFlag2("");
		// }
		// else if (sHangUpType.equals("1")) //挂起时置值
		// {
		// tLCContHangUpStateSchema.setStandFlag2(sEdorAcceptNo);
		// }
		// tLCContHangUpStateSchema.setOperator(mGlobalInput.Operator);
		// tLCContHangUpStateSchema.setMakeDate(mCurrentDate);
		// tLCContHangUpStateSchema.setMakeTime(mCurrentTime);
		// tLCContHangUpStateSchema.setModifyDate(mCurrentDate);
		// tLCContHangUpStateSchema.setModifyTime(mCurrentTime);
		// map.put(tLCContHangUpStateSchema, "INSERT");
		// }
		// else
		// {
		// tLCContHangUpStateSchema = tLCContHangUpStateSet.get(1);
		// tLCContHangUpStateSchema.setClaimFlag(sHangUpType); //理赔
		// tLCContHangUpStateSchema.setRNFlag(sHangUpType); //续期
		// tLCContHangUpStateSchema.setPosFlag(sHangUpType); //保全
		// //为方便理赔判断，添加该字段置值
		// if (sHangUpType.equals("0")) //解挂时消除
		// {
		// tLCContHangUpStateSchema.setStandFlag2("");
		// }
		// else if (sHangUpType.equals("1")) //挂起时置值
		// {
		// tLCContHangUpStateSchema.setStandFlag2(sEdorAcceptNo);
		// }
		// tLCContHangUpStateSchema.setOperator(mGlobalInput.Operator);
		// tLCContHangUpStateSchema.setModifyDate(mCurrentDate);
		// tLCContHangUpStateSchema.setModifyTime(mCurrentTime);
		// map.put(tLCContHangUpStateSchema, "UPDATE");
		// }
		// ===del===zhangtao===2006-02-21==========原挂起表处理方式=============END========

		// ===add===zhangtao===2006-02-21==========新挂起表处理方式=============BGN========
		ContHangUpBL tContHangUpBL = new ContHangUpBL(mGlobalInput);

		if (sHangUpType.equals("1")) // 挂起
		{
			// 挂起时传入的挂起表记录只需将要挂起的字段置为1即可，不需要挂起的字段可以不用置值
			LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema();
			tLCContHangUpStateSchema.setContNo(sContNo);
			tLCContHangUpStateSchema.setHangUpType("2"); // 1-新契约 2-保全 3-续期
															// 4-理赔 5-渠道
			tLCContHangUpStateSchema.setHangUpNo(sEdorAcceptNo); // 保全受理号
			tLCContHangUpStateSchema.setPosFlag("1"); // 保全
			tLCContHangUpStateSchema.setClaimFlag("1"); // 理赔
			tLCContHangUpStateSchema.setRNFlag("1"); // 续期
			map = tContHangUpBL.hangUpCont(tLCContHangUpStateSchema);
			if (map == null) {
				mErrors.copyAllErrors(tContHangUpBL.mErrors);
				return false;
			}
		} else if (sHangUpType.equals("0")) // 解挂
		{
			map = tContHangUpBL.unHangUpCont(sContNo, "2");
			if (map == null) {
				mErrors.copyAllErrors(tContHangUpBL.mErrors);
				return false;
			}
		}
		// ===add===zhangtao===2006-02-21==========新挂起表处理方式=============END========
		return true;
	}

	/**
	 * 设置团体保单挂起状态
	 * 
	 * @param sGrpContNo
	 *            团体保单号
	 * @param sHangUpType
	 *            1-挂起 0-解挂
	 * @param sEdorAcceptNo
	 *            保全受理号
	 * @return boolean
	 */
	public boolean hangUpGrpCont(String sGrpContNo, String sHangUpType,
			String sEdorAcceptNo) {
		ContHangUpBL tContHangUpBL = new ContHangUpBL(mGlobalInput);

		if (sHangUpType.equals("1")) // 挂起
		{
			// 挂起时传入的挂起表记录只需将要挂起的字段置为1即可，不需要挂起的字段可以不用置值
			LCGrpContHangUpStateSchema tLCGrpContHangUpStateSchema = new LCGrpContHangUpStateSchema();
			tLCGrpContHangUpStateSchema.setGrpContNo(sGrpContNo);
			tLCGrpContHangUpStateSchema.setHangUpType("2"); // 1-新契约 2-保全 3-续期
															// 4-理赔 5-渠道
			tLCGrpContHangUpStateSchema.setHangUpNo(sEdorAcceptNo); // 保全受理号
			tLCGrpContHangUpStateSchema.setPosFlag("1"); // 保全
			// tLCGrpContHangUpStateSchema.setClaimFlag("1"); //理赔
			// tLCGrpContHangUpStateSchema.setRNFlag("1"); //续期
			map = tContHangUpBL.hangUpGrpCont(tLCGrpContHangUpStateSchema);
			if (map == null) {
				mErrors.copyAllErrors(tContHangUpBL.mErrors);
				return false;
			}
		} else if (sHangUpType.equals("0")) // 解挂
		{
			map = tContHangUpBL.unHangUpGrpContByHangUpNo(sEdorAcceptNo, "2");// 解挂所有团单
			map.add(tContHangUpBL.unHangUpContByHangUpNo(sEdorAcceptNo, "2"));// 解挂所有个人单
			if (map == null) {
				mErrors.copyAllErrors(tContHangUpBL.mErrors);
				return false;
			}
		}
		return true;
	}

	/**
	 * 申请确认时设置保单续期挂起状态
	 * 
	 * @param sEdorAcceptNo
	 *            保全受理号
	 * @param sContNo
	 *            保单号
	 * @param sHangUpType
	 *            1-挂起 0-解挂
	 * @return boolean
	 */
	public boolean HangUpRN(String sEdorAcceptNo, String sContNo,
			String sHangUpType) {

		String sNeedHangUpRN = getHangUpRNFlag(sEdorAcceptNo);// 续期解挂标志
		if (sNeedHangUpRN == null) {
			return false;
		}
		if (!sNeedHangUpRN.equals("1")) {
			return true; // 不需要解挂续期，直接返回
		}
		// ===del===zhangtao===2006-02-21==========原解挂续期处理方式=============BGN=======
		// LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		// tLCContHangUpStateDB.setContNo(sContNo);
		// tLCContHangUpStateDB.setInsuredNo("000000");
		// tLCContHangUpStateDB.setPolNo("000000");
		// LCContHangUpStateSet tLCContHangUpStateSet =
		// tLCContHangUpStateDB.query();
		// if (tLCContHangUpStateDB.mErrors.needDealError())
		// {
		// CError.buildErr(this, "保单挂起状态查询失败!");
		// return false;
		// }
		// LCContHangUpStateSchema tLCContHangUpStateSchema;
		// if (tLCContHangUpStateSet == null || tLCContHangUpStateSet.size() <
		// 1)
		// {
		// tLCContHangUpStateSchema = new LCContHangUpStateSchema();
		// tLCContHangUpStateSchema.setContNo(sContNo);
		// tLCContHangUpStateSchema.setPolNo("000000");
		// tLCContHangUpStateSchema.setInsuredNo("000000");
		// tLCContHangUpStateSchema.setNBFlag("0"); //承保
		// tLCContHangUpStateSchema.setClaimFlag("0"); //理赔
		// tLCContHangUpStateSchema.setPosFlag("0"); //保全
		// tLCContHangUpStateSchema.setAgentFlag("0"); //渠道
		// tLCContHangUpStateSchema.setRNFlag(sHangUpType); //续期
		// tLCContHangUpStateSchema.setOperator(mGlobalInput.Operator);
		// tLCContHangUpStateSchema.setMakeDate(mCurrentDate);
		// tLCContHangUpStateSchema.setMakeTime(mCurrentTime);
		// tLCContHangUpStateSchema.setModifyDate(mCurrentDate);
		// tLCContHangUpStateSchema.setModifyTime(mCurrentTime);
		// map.put(tLCContHangUpStateSchema, "INSERT");
		// }
		// else
		// {
		// tLCContHangUpStateSchema = tLCContHangUpStateSet.get(1);
		// tLCContHangUpStateSchema.setRNFlag(sHangUpType); //续期
		// tLCContHangUpStateSchema.setOperator(mGlobalInput.Operator);
		// tLCContHangUpStateSchema.setModifyDate(mCurrentDate);
		// tLCContHangUpStateSchema.setModifyTime(mCurrentTime);
		// map.put(tLCContHangUpStateSchema, "UPDATE");
		// }
		// ===del===zhangtao===2006-02-21==========原解挂续期处理方式=============END=======

		// ===add===zhangtao===2006-02-21==========新解挂续期处理方式=============BGN=======
		ContHangUpBL tContHangUpBL = new ContHangUpBL(mGlobalInput);

		LCContHangUpStateSchema updLCContHangUpStateSchema = new LCContHangUpStateSchema();
		updLCContHangUpStateSchema.setContNo(sContNo);
		updLCContHangUpStateSchema.setHangUpType("2"); // 2-保全
		updLCContHangUpStateSchema.setRNFlag("0"); // 解除续期挂起标志
		map = tContHangUpBL.updHangUpFlag(updLCContHangUpStateSchema);
		if (map == null) {
			CError.buildErr(this, "解除续期挂起标志失败!");
			return false;
		}
		// ===add===zhangtao===2006-02-21==========新解挂续期处理方式=============END=======
		return true;
	}

	/**
	 * 设置团体保单续期\理赔挂起状态
	 * 
	 * @param sEdorAcceptNo
	 *            保全受理号
	 * @param sContNo
	 *            保单号
	 * @param sHangUpType
	 *            1-挂起 0-解挂
	 * @return boolean
	 */
	public boolean HangUpGrp_RN_Claim(String sGrpContNo, String sEdorType,
			String sHangUpFlag) {

		String sRNFlag = "0";
		String sClaimFlag = "0";
		if (sHangUpFlag.equals("1"))// 挂起操作
		{
			sRNFlag = getGrpHangUpFlag("GrpRNFlag", sEdorType); // 续期挂起标志
			if (sRNFlag == null) {
				sRNFlag = "1"; // 默认挂起
			}
			sClaimFlag = getGrpHangUpFlag("GrpCMFlag", sEdorType); // 理赔挂起标志
			if (sClaimFlag == null) {
				sClaimFlag = "1"; // 默认挂起
			}

		}
		ContHangUpBL tContHangUpBL = new ContHangUpBL(mGlobalInput);

		LCGrpContHangUpStateSchema updLCGrpContHangUpStateSchema = new LCGrpContHangUpStateSchema();
		updLCGrpContHangUpStateSchema.setGrpContNo(sGrpContNo);
		updLCGrpContHangUpStateSchema.setHangUpType("2"); // 2-保全
		updLCGrpContHangUpStateSchema.setRNFlag(sRNFlag); // 续期挂起标志
		updLCGrpContHangUpStateSchema.setClaimFlag(sClaimFlag); // 理赔挂起标志
		map = tContHangUpBL.updGrpHangUpFlag(updLCGrpContHangUpStateSchema);
		if (map == null) {
			CError.buildErr(this, "挂起团体理赔续期状态失败!");
			return false;
		}
		return true;
	}

	/**
	 * 查询客户相关保单
	 * 
	 * @param sCustomerNo
	 * @return LCContSet
	 */
	public LCContSet getCustomerCont(String sCustomerNo, String sDate,
			String sAppType) {
		LCContDB tLCContDB = new LCContDB();
		// 查询出客户以投保人和被保人身份相关的所有保单

		// =====UPD=====zhangtao=======2006-01-19========客户相关保单查询修改======BGN======================
		// StringBuffer sbSQL = new StringBuffer();
		// sbSQL
		// .append("select /*+RULE*/ * from lccont c where contno in ( ")
		// .append(" select contno from lcinsured where insuredno = '")
		// .append(sCustomerNo)
		// .append("' union select contno from lcappnt where appntno = '")
		// .append(sCustomerNo)
		// .append("') and appflag = '1' and ( not exists (select 'X' from
		// lccontstate s ")
		// .append(" where trim(statetype) in('Terminate','Available') and
		// trim(state) = '1' ")
		// .append(" and not (trim(statetype) = 'Terminate' and
		// trim(statereason) is not null and trim(statereason) = '01') ")
		// .append(" and ((startdate <= '")
		// .append(sDate)
		// .append("' and '")
		// .append(sDate)
		// .append("' <= enddate ) or (startdate <= '")
		// .append(sDate)
		// .append("' and enddate is null )) ")
		// .append(" and contno = c.contno and trim(polno) = (select
		// trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno
		// = p1.mainpolno and rownum = 1))) " )
		// //.append(" and trim(insuredno) = (select trim(p2.insuredno) from
		// lcpol p2 where p2.contno = s.contno and p2.polno = p2.mainpolno and
		// p2.polno = s.polno and rownum = 1)) = 0)")
		// .append(" and CustomGetPolDate <= '")
		// .append(sDate)
		// .append("' ")
		// ;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		sql = "  select /*+RULE*/ * from lccont c "
				+ " where 1 = 1 "
				+ "and (c.GrpContNo is null or c.GrpContNo = '00000000000000000000') "
				+ "and c.contno in ( select contno from lcinsured where insuredno = '"
				+ "?sCustomerNo?"
				+ "' "
				+ " union " // 以投保人或被保人身份相关的保单
				+ " select contno from lcappnt where appntno = '"
				+ "?sCustomerNo?"
				+ "' ) "
				+ " and "
				+ " ( " // 保单未终止，并且主险未失效（失效保单不参与客户层变更）
				+ " ( appflag = '1' and not exists "
				+ " ( select 'X' from lccontstate s "
				+ " where trim(statetype) in ('Available') and trim(state) = '1' "
				+ " and ((startdate <= '"
				+ "?sDate?"
				+ "' and '"
				+ "?sDate?"
				+ "' <= enddate) or (startdate <= '"
				+ "?sDate?"
				+ "' and enddate is null))"
				+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno and rownum = 1) "
				+ " ) "
				+ " ) "
				+ " or "
				+ " ( appflag = '4' and exists " // 01-满期终止、05-自垫终止、06-贷款终止
													// 可以参与客户层变更
				+ " ( select 'X' from lccontstate s "
				+ " where trim(statetype) in ('Terminate') and trim(state) = '1' and trim(statereason) is not null and trim(statereason) in ('01', '05', '06', '09') "
				+ " and ((startdate <= '"
				+ "?sDate?"
				+ "' and '"
				+ "?sDate?"
				+ "' <= enddate) or (startdate <= '"
				+ "?sDate?"
				+ "' and enddate is null)) "
				+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno and rownum = 1) "
				+ " ) " + " ) " + " ) "
		// + " and CustomGetPolDate <= '" + sDate + "' "
		;
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
		sql = "  select * from lccont c "
				+ " where 1 = 1 "
				+ "and (c.GrpContNo is null or c.GrpContNo = '00000000000000000000') "
				+ "and c.contno in ( select contno from lcinsured where insuredno = '"
				+ "?sCustomerNo?"
				+ "' "
				+ " union " // 以投保人或被保人身份相关的保单
				+ " select contno from lcappnt where appntno = '"
				+ "?sCustomerNo?"
				+ "' ) "
				+ " and "
				+ " ( "  // 保单未终止，并且主险未失效（失效保单不参与客户层变更）
				+ " ( appflag = '1' and not exists "
				+ " ( select 'X' from lccontstate s "
				+ " where trim(statetype) in ('Available') and trim(state) = '1' "
				+ " and ((startdate <= '"
				+ "?sDate?"
				+ "' and '"
				+ "?sDate?"
				+ "' <= enddate) or (startdate <= '"
				+ "?sDate?"
				+ "' and enddate is null))"
				+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno limit 0,1) "
				+ " ) "
				+ " ) "
				+ " or "
				+ " ( appflag = '4' and exists " // 01-满期终止、05-自垫终止、06-贷款终止
													// 可以参与客户层变更
				+ " ( select 'X' from lccontstate s "
				+ " where trim(statetype) in ('Terminate') and trim(state) = '1' and trim(statereason) is not null and trim(statereason) in ('01', '05', '06', '09') "
				+ " and ((startdate <= '"
				+ "?sDate?"
				+ "' and '"
				+ "?sDate?"
				+ "' <= enddate) or (startdate <= '"
				+ "?sDate?"
				+ "' and enddate is null)) "
				+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno limit 0,1) "
				+ " ) " + " ) " + " ) "
			// + " and CustomGetPolDate <= '" + sDate + "' "
			;	
		}

		if (sAppType == null
				|| (!sAppType.trim().equals("6") && !sAppType.trim()
						.equals("7"))) // 客户层保全对部门转办取消客户签收日期限制 2006-01-21
		{
			sql += " and CustomGetPolDate <= '?sDate?' ";
		}
		sqlbv.sql(sql);
		sqlbv.put("sCustomerNo", sCustomerNo);
		sqlbv.put("sDate", sDate);
		logger.debug(sql);
		// =====UPD=====zhangtao=======2006-01-19========客户相关保单查询修改======END======================
		LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv);
		if (tLCContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询客户相关保单失败!");
			return null;
		}

		return tLCContSet;
	}

	/**
	 * 计算是否在申请确认后解挂续期
	 * 
	 * @param sEdorAcceptNo
	 *            保全受理号
	 * @return String 续期解挂标志 1-需要解挂 0-不需要解挂
	 */
	private String getHangUpRNFlag(String sEdorAcceptNo) {
		// 目前只对NS生效对应日前30天的情况解挂续期
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = "select * from lmedorcal where caltype='NdHangUpRN'";
		sqlbv.sql(sql);
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv);
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "续期解挂标志计算代码查询失败!");
			return null;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() < 1) {
			CError.buildErr(this, "续期解挂标志计算代码查询失败!");
			return null;
		}
		String sFinaCalCode = tLMEdorCalSet.get(1).getCalCode();
		BqCalBase mBqCalBase=new BqCalBase();
		mBqCalBase.setEdorAcceptNo(sEdorAcceptNo);
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(sFinaCalCode);
		tCalculator.addBasicFactor("EdorAcceptNo", sEdorAcceptNo); // 保全受理号
//		if (!prepareBOMList(mBqCalBase)) {
//			CError.buildErr(this, "Prepare BOMLIST Failed...");
//			return "执行规则引擎报错";
//		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		
		String sHangUpRNFlag = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "续期解挂标志计算失败!");
			return null;
		}

		if (sHangUpRNFlag == null || sHangUpRNFlag.trim().equals("")) {
			CError.buildErr(this, "续期解挂标志计算失败!");
			return null;
		}

		return sHangUpRNFlag;
	}

	/**
	 * 判断团体保全项目是否需要挂起理赔
	 * 
	 * @param sEdorType
	 *            保全受理号
	 * @return String 理赔挂起标志 1-需要挂起 0-不需要挂起
	 */
	private String getGrpHangUpFlag(String sCalType, String sEdorType) {
		// 目前只对NS生效对应日前30天的情况解挂续期
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = "select * from lmedorcal where caltype = '" + "?sCalType?"
				+ "' and edortype = '" + "?sEdorType?" + "'";
		sqlbv.sql(sql);
		sqlbv.put("sCalType", sCalType);
		sqlbv.put("sEdorType", sEdorType);
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv);

		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "挂起标志计算代码查询失败!");
			return null;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() < 1) {
			CError.buildErr(this, "挂起标志计算代码查询失败!");
			return null;
		}
		String sFinaCalCode = tLMEdorCalSet.get(1).getCalCode();
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(sFinaCalCode);
		BqCalBase mBqCalBase=new BqCalBase();
//		if (!prepareBOMList(mBqCalBase)) {
//			CError.buildErr(this, "Prepare BOMLIST Failed...");
//			return "执行规则引擎报错";
//		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		String sHangUpFlag = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "挂起标志计算失败!");
			return null;
		}

		if (sHangUpFlag == null || sHangUpFlag.trim().equals("")) {
			CError.buildErr(this, "挂起标志计算失败!");
			return null;
		}

		return sHangUpFlag;
	}

	public MMap getMMap() {
		return map;
	}

	public static void main(String[] args) {

		// GlobalInput tGlobalInput = new GlobalInput();
		// tGlobalInput.Operator = "zhangtao";
		// tGlobalInput.ManageCom = "86110000";
		// ContHangUpBL tContHangUpBL = new ContHangUpBL(tGlobalInput);
		// LCContSet tLCContSet = tContHangUpBL.getCustomerCont("0000524820",
		// "2005-08-26");
		// for (int i = 1; i <= tLCContSet.size(); i++)
		// {
		// logger.debug(tLCContSet.get(i).getContNo());
		// }
		// if (!tContHangUpBL.hangUpCont("12345646", "1"))
		// {
		// logger.debug("error");
		// }
		// else
		// {
		// MMap tMap = tContHangUpBL.getMMap();
		// VData mInputData = new VData();
		// mInputData.add(tMap);
		// PubSubmit tPubSubmit = new PubSubmit();
		// if (!tPubSubmit.submitData(mInputData, ""))
		// {
		// logger.debug("error");
		// }
		// else
		// {
		// logger.debug("succ");
		// }
		// }

	}
	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
//	private boolean prepareBOMList(BqCalBase mBqCalBase) {
//		try {
//			if (!this.mBomListFlag) {
//				this.mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
//				LPEdorItemSchema tLPEdorItemSchema=new LPEdorItemSchema();
//				this.mBomList = this.mPrepareBOMBQEdorBL.dealData(tLPEdorItemSchema);
//				this.mBomListFlag = true;
//			}
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.mBomListFlag = false;
//			return false;
//		}
//	}
}
