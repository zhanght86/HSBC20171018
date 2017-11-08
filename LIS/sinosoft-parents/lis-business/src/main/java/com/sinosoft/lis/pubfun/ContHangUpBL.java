package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

/*
 * @(#)ContHangUpBL.java	2006-02-15
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

import com.sinosoft.lis.db.LCContHangUpStateDB;
import com.sinosoft.lis.db.LCGrpContHangUpStateDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.schema.LCGrpContHangUpStateSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.lis.vschema.LCGrpContHangUpStateSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 业务系统
 * </p>
 * <p>
 * Description: 保单挂起公用处理类
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
public class ContHangUpBL {
private static Logger logger = Logger.getLogger(ContHangUpBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 统一更新日期，时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public ContHangUpBL(GlobalInput pGlobalInput) {
		mGlobalInput.setSchema(pGlobalInput);
	}

	/**
	 * 判断保单挂起状态
	 * 
	 * @param sContNo
	 *            保单号
	 * @param HangUpType
	 *            需要判断挂起状态的模块标志 1-新契约 2-保全 3-续期 4-理赔 5-渠道
	 * @return boolean
	 */
	public boolean checkHangUpState(String sContNo, String sHangUpType) {
		String sql = " select * from ldcode where code = '" + "?f1?" + "'"
				+ " and codetype = 'conthanguptype'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("f1", sHangUpType);
		LDCodeDB tLDCodeDB = new LDCodeDB();
		LDCodeSet tLDCodeSet = tLDCodeDB.executeQuery(sqlbv1);
		if (tLDCodeDB.mErrors.needDealError() || tLDCodeSet == null
				|| tLDCodeSet.size() < 1) {
			CError.buildErr(this, "保单挂起状态描述查询失败!");
			return false;
		}
		String XXFlag = tLDCodeSet.get(1).getCodeName(); // 需要判断的模块标志对应的字段名
		String XXName = tLDCodeSet.get(1).getCodeAlias();// 需要判断挂起状态的模块名

		sql = " select * from lcconthangupstate where " + "?f5?" + " = '1' "
				+ " and contno = '" + "?f6?" + "'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("f5", XXFlag);
		sqlbv2.put("f6", sContNo);
		LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		LCContHangUpStateSet tLCContHangUpStateSet = tLCContHangUpStateDB
				.executeQuery(sqlbv2);
		if (tLCContHangUpStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单挂起状态查询失败!");
			return false;
		}
		if (tLCContHangUpStateSet == null || tLCContHangUpStateSet.size() < 1) {
			// 该挂起类型没有任何挂起记录
			return true;
		} else {
			String sHangUpType2 = tLCContHangUpStateSet.get(1).getHangUpType();
			sql = " select * from ldcode where code = '" + "?f11?" + "'"
					+ " and codetype = 'conthanguptype'";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(sql);
			sqlbv3.put("f11", sHangUpType2);
			tLDCodeDB = new LDCodeDB();
			tLDCodeSet = tLDCodeDB.executeQuery(sqlbv3);
			if (tLDCodeDB.mErrors.needDealError() || tLDCodeSet == null
					|| tLDCodeSet.size() < 1) {
				CError.buildErr(this, "保单挂起状态描述查询失败!");
				return false;
			}
			String XXName2 = tLDCodeSet.get(1).getCodeAlias(); // 模块名

			// 返回错误信息，告知该保单的XX状态被什么模块挂起，并提示挂起号码
			CError.buildErr(this, "保单" + sContNo + "的" + XXName + "状态被"
					+ XXName2 + "挂起，相关号码："
					+ tLCContHangUpStateSet.get(1).getHangUpNo());
			return false;
		}
	}

	/**
	 * 判断团体保单挂起状态 add by zhangtao 2006-09-20
	 * 
	 * @param sGrpContNo
	 *            团体保单号
	 * @param HangUpType
	 *            需要判断挂起状态的模块标志 1-新契约 2-保全 3-续期 4-理赔 5-渠道
	 * @return boolean
	 */
	public boolean checkGrpHangUpState(String sGrpContNo, String sHangUpType) {
		String sql = " select * from ldcode where code = '" + "?f12?" + "'"
				+ " and codetype = 'conthanguptype'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("f12", sHangUpType);
		LDCodeDB tLDCodeDB = new LDCodeDB();
		LDCodeSet tLDCodeSet = tLDCodeDB.executeQuery(sqlbv4);
		if (tLDCodeDB.mErrors.needDealError() || tLDCodeSet == null
				|| tLDCodeSet.size() < 1) {
			CError.buildErr(this, "保单挂起状态描述查询失败!");
			return false;
		}
		String XXFlag = tLDCodeSet.get(1).getCodeName(); // 需要判断的模块标志对应的字段名
		String XXName = tLDCodeSet.get(1).getCodeAlias();// 需要判断挂起状态的模块名

		sql = " select * from lcgrpconthangupstate where " + "?f14?" + " = '1' "
				+ " and grpcontno = '" + "?f13?" + "'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("f14", XXFlag);
		sqlbv5.put("f13", sGrpContNo);
		LCGrpContHangUpStateDB tLCGrpContHangUpStateDB = new LCGrpContHangUpStateDB();
		LCGrpContHangUpStateSet tLCGrpContHangUpStateSet = tLCGrpContHangUpStateDB
				.executeQuery(sqlbv5);
		if (tLCGrpContHangUpStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单挂起状态查询失败!");
			return false;
		}
		if (tLCGrpContHangUpStateSet == null
				|| tLCGrpContHangUpStateSet.size() < 1) {
			// 该挂起类型没有任何挂起记录
			return true;
		} else {
			String sHangUpType2 = tLCGrpContHangUpStateSet.get(1)
					.getHangUpType();
			sql = " select * from ldcode where code = '" + "?f16?" + "'"
					+ " and codetype = 'conthanguptype'";
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql(sql);
			sqlbv6.put("f16", sHangUpType2);
			tLDCodeDB = new LDCodeDB();
			tLDCodeSet = tLDCodeDB.executeQuery(sqlbv6);
			if (tLDCodeDB.mErrors.needDealError() || tLDCodeSet == null
					|| tLDCodeSet.size() < 1) {
				CError.buildErr(this, "保单挂起状态描述查询失败!");
				return false;
			}
			String XXName2 = tLDCodeSet.get(1).getCodeAlias(); // 模块名

			// 返回错误信息，告知该保单的XX状态被什么模块挂起，并提示挂起号码
			CError.buildErr(this, "保单" + sGrpContNo + "的" + XXName + "状态被"
					+ XXName2 + "挂起，相关号码："
					+ tLCGrpContHangUpStateSet.get(1).getHangUpNo());
			return false;
		}
	}

	/**
	 * 单张保单挂起
	 * 
	 * @param tLCContHangUpStateSchema
	 *            传入保单号、挂起类型、挂起标志位（将需要挂起的标志位置1，不需要挂起的标志位不用置值）以及备用标志和备注项
	 * @return boolean
	 */
	public MMap hangUpCont(LCContHangUpStateSchema tLCContHangUpStateSchema) {
		String sContNo = tLCContHangUpStateSchema.getContNo(); // 保单号
		String sHangUpType = tLCContHangUpStateSchema.getHangUpType(); // 挂起类型

		LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		tLCContHangUpStateDB.setContNo(sContNo);
		tLCContHangUpStateDB.setPolNo("000000");
		tLCContHangUpStateDB.setInsuredNo("000000");
		tLCContHangUpStateDB.setHangUpType(sHangUpType);
		LCContHangUpStateSet tLCContHangUpStateSet = tLCContHangUpStateDB
				.query();
		if (tLCContHangUpStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单挂起状态查询失败!");
			return null;
		}
		if (tLCContHangUpStateSet != null && tLCContHangUpStateSet.size() > 0) {
			if (tLCContHangUpStateSet.get(1).getHangUpNo().equals(
					tLCContHangUpStateSchema.getHangUpNo())) {
				return new MMap();
			}
			CError.buildErr(this, "该保单已经被挂起，挂起号码："
					+ tLCContHangUpStateSet.get(1).getHangUpNo());
			return null;
		}

		// 保单挂起时默认值均为不挂起
		LCContHangUpStateSchema dLCContHangUpStateSchema = new LCContHangUpStateSchema();
		dLCContHangUpStateSchema.setAgentFlag("0");
		dLCContHangUpStateSchema.setClaimFlag("0");
		dLCContHangUpStateSchema.setPosFlag("0");
		dLCContHangUpStateSchema.setNBFlag("0");
		dLCContHangUpStateSchema.setRNFlag("0");
		// 置默认值
		putDefaultValue(tLCContHangUpStateSchema, dLCContHangUpStateSchema);

		tLCContHangUpStateSchema.setPolNo("000000");
		tLCContHangUpStateSchema.setInsuredNo("000000");
		tLCContHangUpStateSchema.setOperator(mGlobalInput.Operator);
		tLCContHangUpStateSchema.setMakeDate(mCurrentDate);
		tLCContHangUpStateSchema.setMakeTime(mCurrentTime);
		tLCContHangUpStateSchema.setModifyDate(mCurrentDate);
		tLCContHangUpStateSchema.setModifyTime(mCurrentTime);

		MMap tMap = new MMap();
		tMap.put(tLCContHangUpStateSchema, "INSERT");

		return tMap;
	}

	/**
	 * 单张团体保单挂起 add by zhangtao 2006-09-20
	 * 
	 * @param tLCGrpContHangUpStateSchema
	 *            传入保单号、挂起类型、挂起标志位（将需要挂起的标志位置1，不需要挂起的标志位不用置值）以及备用标志和备注项
	 * @return boolean
	 */
	public MMap hangUpGrpCont(
			LCGrpContHangUpStateSchema tLCGrpContHangUpStateSchema) {
		String sGrpContNo = tLCGrpContHangUpStateSchema.getGrpContNo(); // 团体保单号
		String sHangUpType = tLCGrpContHangUpStateSchema.getHangUpType(); // 挂起类型

		LCGrpContHangUpStateDB tLCGrpContHangUpStateDB = new LCGrpContHangUpStateDB();
		tLCGrpContHangUpStateDB.setGrpContNo(sGrpContNo);
		tLCGrpContHangUpStateDB.setHangUpType(sHangUpType);
		LCGrpContHangUpStateSet tLCGrpContHangUpStateSet = tLCGrpContHangUpStateDB
				.query();
		if (tLCGrpContHangUpStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单挂起状态查询失败!");
			return null;
		}
		if (tLCGrpContHangUpStateSet != null
				&& tLCGrpContHangUpStateSet.size() > 0) {
			// Q:如果该挂起类型已经有挂起记录，直接返回true还是报错？ zhangtao 2006-02-15
			// A: 暂定报错 zhangtao 2006-02-15
			CError.buildErr(this, "该保单已经被挂起，挂起号码："
					+ tLCGrpContHangUpStateSet.get(1).getHangUpNo());
			return null;
		}

		// 保单挂起时默认值均为不挂起
		LCGrpContHangUpStateSchema dLCGrpContHangUpStateSchema = new LCGrpContHangUpStateSchema();
		dLCGrpContHangUpStateSchema.setAgentFlag("0");
		dLCGrpContHangUpStateSchema.setClaimFlag("0");
		dLCGrpContHangUpStateSchema.setPosFlag("0");
		dLCGrpContHangUpStateSchema.setNBFlag("0");
		dLCGrpContHangUpStateSchema.setRNFlag("0");
		// 置默认值
		putGrpDefaultValue(tLCGrpContHangUpStateSchema,
				dLCGrpContHangUpStateSchema);
		tLCGrpContHangUpStateSchema.setGrpPolNo("000000");
		tLCGrpContHangUpStateSchema.setOperator(mGlobalInput.Operator);
		tLCGrpContHangUpStateSchema.setMakeDate(mCurrentDate);
		tLCGrpContHangUpStateSchema.setMakeTime(mCurrentTime);
		tLCGrpContHangUpStateSchema.setModifyDate(mCurrentDate);
		tLCGrpContHangUpStateSchema.setModifyTime(mCurrentTime);

		MMap tMap = new MMap();
		tMap.put(tLCGrpContHangUpStateSchema, "INSERT");

		return tMap;
	}

	/**
	 * 单张保单整体解挂
	 * 
	 * @param sContNo
	 *            保单号
	 * @param sHangUpType
	 *            挂起类型
	 * @return boolean
	 */
	public MMap unHangUpCont(String sContNo, String sHangUpType) {

		// ===del===zhangtao===2006-03-09===为了容错，解挂时如果没有挂起记录也不用报错===BGN====
		// LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		// tLCContHangUpStateDB.setContNo(sContNo);
		// tLCContHangUpStateDB.setPolNo("000000");
		// tLCContHangUpStateDB.setInsuredNo("000000");
		// tLCContHangUpStateDB.setHangUpType(sHangUpType);
		// LCContHangUpStateSet tLCContHangUpStateSet =
		// tLCContHangUpStateDB.query();
		// if (tLCContHangUpStateDB.mErrors.needDealError())
		// {
		// CError.buildErr(this, "保单挂起状态查询失败!");
		// return null;
		// }
		// if (tLCContHangUpStateSet == null || tLCContHangUpStateSet.size() <
		// 1)
		// {
		// CError.buildErr(this, "没有挂起记录，不能解挂!");
		// return null;
		// }
		// ===del===zhangtao===2006-03-09===为了容错，解挂时如果没有挂起记录也不用报错===END====

		// 删除挂起记录
		String sql = " delete from lcconthangupstate "
				+ " where polno = '000000' and insuredno = '000000' "
				+ " and hanguptype = '" + "?a1?" + "' and contno = '"
				+ "?a2?" + "'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(sql);
		sqlbv7.put("a1", sHangUpType);
		sqlbv7.put("a2", sContNo);
		MMap tMap = new MMap();
		tMap.put(sqlbv7, "DELETE");
		return tMap;
	}

	/**
	 * 单张团体保单整体解挂 add by zhangtao 2006-09-20
	 * 
	 * @param sGrpContNo
	 *            团体保单号
	 * @param sHangUpType
	 *            挂起类型
	 * @return boolean
	 */
	public MMap unHangUpGrpCont(String sGrpContNo, String sHangUpType) {
		// 删除挂起记录
		String sql = " delete from lcgrpconthangupstate " + " where 1=1 "
				+ " and hanguptype = '" + "?a3?" + "' and grpcontno = '"
				+ "?a4?" + "'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(sql);
		sqlbv7.put("a3", sHangUpType);
		sqlbv7.put("a4", sGrpContNo);
		MMap tMap = new MMap();
		tMap.put(sqlbv7, "DELETE");
		return tMap;
	}

	/**
	 * 根据挂起号码对其挂起的所有保单进行整体解挂
	 * 
	 * @param sHangUpNo
	 *            挂起号码
	 * @param sHangUpType
	 *            挂起类型
	 * @return boolean
	 */
	public MMap unHangUpContByHangUpNo(String sHangUpNo, String sHangUpType) {
		// ===del===zhangtao===2006-03-09===为了容错，解挂时如果没有挂起记录也不用报错===BGN====
		// LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		// tLCContHangUpStateDB.setHangUpNo(sHangUpNo);
		// tLCContHangUpStateDB.setPolNo("000000");
		// tLCContHangUpStateDB.setInsuredNo("000000");
		// tLCContHangUpStateDB.setHangUpType(sHangUpType);
		// LCContHangUpStateSet tLCContHangUpStateSet =
		// tLCContHangUpStateDB.query();
		// if (tLCContHangUpStateDB.mErrors.needDealError())
		// {
		// CError.buildErr(this, "保单挂起状态查询失败!");
		// return null;
		// }
		// if (tLCContHangUpStateSet == null || tLCContHangUpStateSet.size() <
		// 1)
		// {
		// CError.buildErr(this, "没有挂起记录，不能解挂!");
		// return null;
		// }
		// ===del===zhangtao===2006-03-09===为了容错，解挂时如果没有挂起记录也不用报错===END====
		// 删除挂起记录
		String sql = " delete from lcconthangupstate "
				+ " where polno = '000000' and insuredno = '000000' "
				+ " and hanguptype = '" + "?a5?" + "' and hangupno = '"
				+ "?a6?" + "'";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(sql);
		sqlbv8.put("a5", sHangUpType);
		sqlbv8.put("a6", sHangUpNo);
		MMap tMap = new MMap();
		tMap.put(sqlbv8, "DELETE");
		return tMap;
	}

	/**
	 * 根据挂起号码对其挂起的所有团体保单进行整体解挂 add by zhangtao 2006-09-20
	 * 
	 * @param sHangUpNo
	 *            挂起号码
	 * @param sHangUpType
	 *            挂起类型
	 * @return boolean
	 */
	public MMap unHangUpGrpContByHangUpNo(String sHangUpNo, String sHangUpType) {
		// 删除挂起记录
		String sql = " delete from lcgrpconthangupstate " + " where 1=1 "
				+ " and hanguptype = '" + "?a7?" + "' and hangupno = '"
				+ "?a8?" + "'";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(sql);
		sqlbv9.put("a7", sHangUpType);
		sqlbv9.put("a8", sHangUpNo);
		MMap tMap = new MMap();
		tMap.put(sqlbv9, "DELETE");
		return tMap;
	}

	/**
	 * 修改挂起标志位（例如保全申请确认后有些项目需要单独解挂续期）
	 * 
	 * @param tLCContHangUpStateSchema
	 *            传入保单号、挂起类型、挂起标志位（将需要修改的标志位置值，不需要修改的标志位不用置值）以及备用标志和备注项
	 * @return boolean
	 */
	public MMap updHangUpFlag(LCContHangUpStateSchema tLCContHangUpStateSchema) {
		String sContNo = tLCContHangUpStateSchema.getContNo(); // 保单号
		String sHangUpType = tLCContHangUpStateSchema.getHangUpType(); // 挂起类型

		LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		tLCContHangUpStateDB.setContNo(sContNo);
		tLCContHangUpStateDB.setPolNo("000000");
		tLCContHangUpStateDB.setInsuredNo("000000");
		tLCContHangUpStateDB.setHangUpType(sHangUpType);
		LCContHangUpStateSet dLCContHangUpStateSet = tLCContHangUpStateDB
				.query();
		if (tLCContHangUpStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单挂起状态查询失败!");
			return null;
		}
		if (dLCContHangUpStateSet == null || dLCContHangUpStateSet.size() < 1) {
			CError.buildErr(this, "没有挂起记录，不能修改!");
			return null;
		}

		// 置默认值（修改挂起记录时默认原值不变）
		putDefaultValue(tLCContHangUpStateSchema, dLCContHangUpStateSet.get(1));

		dLCContHangUpStateSet.get(1).setAgentFlag(
				tLCContHangUpStateSchema.getAgentFlag());
		dLCContHangUpStateSet.get(1).setClaimFlag(
				tLCContHangUpStateSchema.getClaimFlag());
		dLCContHangUpStateSet.get(1).setNBFlag(
				tLCContHangUpStateSchema.getNBFlag());
		dLCContHangUpStateSet.get(1).setPosFlag(
				tLCContHangUpStateSchema.getPosFlag());
		dLCContHangUpStateSet.get(1).setRNFlag(
				tLCContHangUpStateSchema.getRNFlag());

		dLCContHangUpStateSet.get(1).setOperator(mGlobalInput.Operator);
		dLCContHangUpStateSet.get(1).setModifyDate(mCurrentDate);
		dLCContHangUpStateSet.get(1).setModifyTime(mCurrentTime);

		MMap tMap = new MMap();
		tMap.put(dLCContHangUpStateSet.get(1), "UPDATE");
		return tMap;
	}

	/**
	 * 修改团体挂起标志位（例如保全申请确认后有些项目需要单独解挂续期） add by zhangtao 2006-09-20
	 * 
	 * @param tLCGrpContHangUpStateSchema
	 *            传入团体保单号、挂起类型、挂起标志位（将需要修改的标志位置值，不需要修改的标志位不用置值）以及备用标志和备注项
	 * @return boolean
	 */
	public MMap updGrpHangUpFlag(
			LCGrpContHangUpStateSchema tLCGrpContHangUpStateSchema) {
		String sGrpContNo = tLCGrpContHangUpStateSchema.getGrpContNo(); // 保单号
		String sHangUpType = tLCGrpContHangUpStateSchema.getHangUpType(); // 挂起类型

		LCGrpContHangUpStateDB tLCGrpContHangUpStateDB = new LCGrpContHangUpStateDB();
		tLCGrpContHangUpStateDB.setGrpContNo(sGrpContNo);
		tLCGrpContHangUpStateDB.setHangUpType(sHangUpType);
		LCGrpContHangUpStateSet dLCGrpContHangUpStateSet = tLCGrpContHangUpStateDB
				.query();
		if (tLCGrpContHangUpStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单挂起状态查询失败!");
			return null;
		}
		if (dLCGrpContHangUpStateSet == null
				|| dLCGrpContHangUpStateSet.size() < 1) {
			CError.buildErr(this, "没有挂起记录，不能修改!");
			return null;
		}

		// 置默认值（修改挂起记录时默认原值不变）
		putGrpDefaultValue(tLCGrpContHangUpStateSchema,
				dLCGrpContHangUpStateSet.get(1));

		dLCGrpContHangUpStateSet.get(1).setAgentFlag(
				tLCGrpContHangUpStateSchema.getAgentFlag());
		dLCGrpContHangUpStateSet.get(1).setClaimFlag(
				tLCGrpContHangUpStateSchema.getClaimFlag());
		dLCGrpContHangUpStateSet.get(1).setNBFlag(
				tLCGrpContHangUpStateSchema.getNBFlag());
		dLCGrpContHangUpStateSet.get(1).setPosFlag(
				tLCGrpContHangUpStateSchema.getPosFlag());
		dLCGrpContHangUpStateSet.get(1).setRNFlag(
				tLCGrpContHangUpStateSchema.getRNFlag());

		dLCGrpContHangUpStateSet.get(1).setOperator(mGlobalInput.Operator);
		dLCGrpContHangUpStateSet.get(1).setModifyDate(mCurrentDate);
		dLCGrpContHangUpStateSet.get(1).setModifyTime(mCurrentTime);

		MMap tMap = new MMap();
		tMap.put(dLCGrpContHangUpStateSet.get(1), "UPDATE");
		return tMap;
	}

	/**
	 * 置默认值（如果传入的某些挂起字段为空，填上默认值避免数据库操作报错）
	 * 
	 * @param tLCContHangUpStateSchema
	 *            //传入的挂起记录
	 * @param dLCContHangUpStateSchema
	 *            //默认的挂起记录
	 * @return boolean
	 */
	private void putDefaultValue(
			LCContHangUpStateSchema tLCContHangUpStateSchema,
			LCContHangUpStateSchema dLCContHangUpStateSchema) {
		// 渠道挂起标志
		if (tLCContHangUpStateSchema.getAgentFlag() == null
				|| tLCContHangUpStateSchema.getAgentFlag().trim().equals("")) {
			tLCContHangUpStateSchema.setAgentFlag(dLCContHangUpStateSchema
					.getAgentFlag());
		}
		// 理赔挂起标志
		if (tLCContHangUpStateSchema.getClaimFlag() == null
				|| tLCContHangUpStateSchema.getClaimFlag().trim().equals("")) {
			tLCContHangUpStateSchema.setClaimFlag(dLCContHangUpStateSchema
					.getClaimFlag());
		}
		// 新契约挂起标志
		if (tLCContHangUpStateSchema.getNBFlag() == null
				|| tLCContHangUpStateSchema.getNBFlag().trim().equals("")) {
			tLCContHangUpStateSchema.setNBFlag(dLCContHangUpStateSchema
					.getNBFlag());
		}
		// 保全挂起标志
		if (tLCContHangUpStateSchema.getPosFlag() == null
				|| tLCContHangUpStateSchema.getPosFlag().trim().equals("")) {
			tLCContHangUpStateSchema.setPosFlag(dLCContHangUpStateSchema
					.getPosFlag());
		}
		// 续期挂起标志
		if (tLCContHangUpStateSchema.getRNFlag() == null
				|| tLCContHangUpStateSchema.getRNFlag().trim().equals("")) {
			tLCContHangUpStateSchema.setRNFlag(dLCContHangUpStateSchema
					.getRNFlag());
		}
	}

	/**
	 * 置默认值（如果传入的某些挂起字段为空，填上默认值避免数据库操作报错） add by zhangtao 2006-09-20
	 * 
	 * @param tLCContHangUpStateSchema
	 *            //传入的挂起记录
	 * @param dLCContHangUpStateSchema
	 *            //默认的挂起记录
	 * @return boolean
	 */
	private void putGrpDefaultValue(
			LCGrpContHangUpStateSchema tLCGrpContHangUpStateSchema,
			LCGrpContHangUpStateSchema dLCGrpContHangUpStateSchema) {
		// 渠道挂起标志
		if (tLCGrpContHangUpStateSchema.getAgentFlag() == null
				|| tLCGrpContHangUpStateSchema.getAgentFlag().trim().equals("")) {
			tLCGrpContHangUpStateSchema
					.setAgentFlag(dLCGrpContHangUpStateSchema.getAgentFlag());
		}
		// 理赔挂起标志
		if (tLCGrpContHangUpStateSchema.getClaimFlag() == null
				|| tLCGrpContHangUpStateSchema.getClaimFlag().trim().equals("")) {
			tLCGrpContHangUpStateSchema
					.setClaimFlag(dLCGrpContHangUpStateSchema.getClaimFlag());
		}
		// 新契约挂起标志
		if (tLCGrpContHangUpStateSchema.getNBFlag() == null
				|| tLCGrpContHangUpStateSchema.getNBFlag().trim().equals("")) {
			tLCGrpContHangUpStateSchema.setNBFlag(dLCGrpContHangUpStateSchema
					.getNBFlag());
		}
		// 保全挂起标志
		if (tLCGrpContHangUpStateSchema.getPosFlag() == null
				|| tLCGrpContHangUpStateSchema.getPosFlag().trim().equals("")) {
			tLCGrpContHangUpStateSchema.setPosFlag(dLCGrpContHangUpStateSchema
					.getPosFlag());
		}
		// 续期挂起标志
		if (tLCGrpContHangUpStateSchema.getRNFlag() == null
				|| tLCGrpContHangUpStateSchema.getRNFlag().trim().equals("")) {
			tLCGrpContHangUpStateSchema.setRNFlag(dLCGrpContHangUpStateSchema
					.getRNFlag());
		}
	}

	public static void main(String[] args) {

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "zhangtao";
		tGlobalInput.ManageCom = "86110000";
		ContHangUpBL tContHangUpBL = new ContHangUpBL(tGlobalInput);
		MMap tMap = null;

		// ===校验保单挂起状态测试==================================================
		if (!tContHangUpBL.checkHangUpState("HB520526111000266", "3")) {
			logger.debug(tContHangUpBL.mErrors.getFirstError());
		} else {
			logger.debug("保单XX没有被挂起");
		}

		// //===保单挂起测试=========================================================
		// //挂起时传入的挂起表记录只需将要挂起的字段置为1即可，不需要挂起的字段可以不用置值
		// LCContHangUpStateSchema tLCContHangUpStateSchema = new
		// LCContHangUpStateSchema();
		// tLCContHangUpStateSchema.setContNo("HB520526111000266");
		// tLCContHangUpStateSchema.setHangUpType("2"); //1-新契约 2-保全 3-续期 4-理赔
		// 5-渠道
		// tLCContHangUpStateSchema.setHangUpNo("6120060215000012"); //保全受理号
		// tLCContHangUpStateSchema.setPosFlag("1"); //挂起保全
		// tLCContHangUpStateSchema.setClaimFlag("1"); //挂起理赔
		// tLCContHangUpStateSchema.setRNFlag("1"); //挂起续期
		// tMap = tContHangUpBL.hangUpCont(tLCContHangUpStateSchema);

		// //===修改挂起标志测试（例保全挂起后申请确认时解挂续期）========================
		// //修改时传入的挂起表记录只需将要修改的字段置值，不需要修改的字段可以不用置值
		// LCContHangUpStateSchema updLCContHangUpStateSchema = new
		// LCContHangUpStateSchema();
		// updLCContHangUpStateSchema.setContNo("HB520526111000266");
		// updLCContHangUpStateSchema.setHangUpType("2"); //2-保全
		// updLCContHangUpStateSchema.setRNFlag("0"); //修改续期挂起标志
		// updLCContHangUpStateSchema.setAgentFlag("1"); //修改渠道挂起标志
		// tMap = tContHangUpBL.updHangUpFlag(updLCContHangUpStateSchema);

		// //===保单解挂测试=========================================================
		// tMap = tContHangUpBL.unHangUpCont("HB520526111000266", "2");

		// //===根据挂起号码解挂测试==================================================
		// tMap = tContHangUpBL.unHangUpContByHangUpNo("6120060215000012", "2");

		if (tMap == null) {
			logger.debug(tContHangUpBL.mErrors.getFirstError());
		} else {
			VData mInputData = new VData();
			mInputData.add(tMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, "")) {
				logger.debug("error");
			} else {
				logger.debug("succ");
			}
		}
	}
}
