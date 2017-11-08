package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.f1print.*;
import java.lang.*;
import java.sql.*;
import java.math.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证管理公用模块
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 周平
 * @version 1.0
 */

public class CertifyFunc {
private static Logger logger = Logger.getLogger(CertifyFunc.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public static CErrors mErrors = new CErrors();

	public static VData mResult = new VData();

	public static final String INPUT_COM = "00"; // 该机构编码表示：单证印刷后入库操作的机构

	public static final int FIRST_COM = 2; // 入库操作只能发放到一级机构，一级机构的长度是2

	public static final int LAST_COM = 9;

	public static final String CERTIFY_CLASS_CERTIFY = "P"; // 普通单证

	public static final String CERTIFY_CLASS_CARD = "D"; // D-定额单证

	public static final String CERTIFY_CLASS_SYS = "S"; // 系统单证

	private static final String VALID_AGENT_STATE = " IN ('01', '02') "; // 可用的代理人的状态

	public CertifyFunc() {
	}

	public static void main(String[] args) {		
	}

	/*
	 * 传入参数 globalInput:全局空间, aLZCardSchema:要入库的单证记录;
	 * aLZCardPrintSchema:单证印刷记录，其中记录着要入库的单证印刷号; strCertifyClass :要入库的单证类型，常量值;
	 * vResult : 返回的结果集。其中第一个元素是要删除的LZCard的信息， 第二和第三个元素是要插入的LZCard的信息，
	 * 第四个元素是要插入的LZCard的信息， 第五个元素是要插入的LZCardTrack的信息。
	 * 第六个元素是要更新的LZCardPrint的信息（对单证入库操作的支持）。
	 */
	protected static boolean inputCertify(GlobalInput globalInput, LZCardSchema aLZCardSchema,
			LZCardPrintSchema aLZCardPrintSchema, String strCertifyClass, VData vResult) {
		mErrors.clearErrors();
		vResult.clear();
		vResult.add(0, null);
		vResult.add(1, null);
		vResult.add(2, null);
		vResult.add(3, null);
		vResult.add(4, null);
		vResult.add(5, null);
		vResult.add(6, null);
		vResult.add(7, null);

		// 查询LZCardPrint，并更新其状态
		String szSql = "SELECT * FROM LZCardPrint WHERE PrtNo = '";
		szSql += "?PrtNo?";
		szSql += "' AND CertifyCode = '";
		szSql += "?CertifyCode?";
		szSql += "' AND State = '1'";
		szSql += " AND ManageCom = '" + "?ManageCom?" + "'";
		logger.debug("szSql=" + szSql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(szSql);
		sqlbv.put("PrtNo", aLZCardPrintSchema.getPrtNo());
		sqlbv.put("CertifyCode", aLZCardSchema.getCertifyCode());
		sqlbv.put("ManageCom",  globalInput.ComCode);
		LZCardPrintDB dbLZCardPrint = new LZCardPrintDB();
		LZCardPrintSchema schemaLZCardPrint = null;
		LZCardPrintSet setLZCP = dbLZCardPrint.executeQuery(sqlbv);
		if (setLZCP.size() != 1) {
			buildError("inputCertify", "没有该印刷号的单证或者当前机构不能操作此种单证");
			return false;
		} else {
			schemaLZCardPrint = setLZCP.get(1);
		}

		dbLZCardPrint.setSchema(schemaLZCardPrint);
		dbLZCardPrint.setModifyDate(PubFun.getCurrentDate());
		dbLZCardPrint.setModifyTime(PubFun.getCurrentTime());
		dbLZCardPrint.setState("2");
		vResult.set(5, dbLZCardPrint.getSchema());

		// 检查单证状态表，看是否已经存在这样的数据
		szSql = "select * from lzcard where certifycode = '" + "?certifycode?" + "' "
				+ "and startno <= '" + "?startno?" + "' " + "and endno >= '"
				+ "?endno?" + "'";
		logger.debug("szSql=" + szSql);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(szSql);
		sqlbv1.put("certifycode", schemaLZCardPrint.getCertifyCode());
		sqlbv1.put("startno", schemaLZCardPrint.getEndNo());
		sqlbv1.put("endno", schemaLZCardPrint.getStartNo());
		LZCardDB dbLZCard = new LZCardDB();
		if (dbLZCard.executeQuery(sqlbv1).size() >= 1) {
			buildError("inputCertify", "在单证状态表中已经存在要入库的单证");
			return false;
		}

		// 查询LMCertifyDes，并校验单证编码
		szSql = "SELECT * FROM LMCertifyDes WHERE CertifyCode = '";
		szSql += "?CertifyCode?" + "'";
		logger.debug("校验单证编码:" + szSql);
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(szSql);
		sqlbv2.put("CertifyCode", schemaLZCardPrint.getCertifyCode());
		LMCertifyDesDB dbLMCertifyDes = new LMCertifyDesDB();
		LMCertifyDesSet setLMCertifyDes = dbLMCertifyDes.executeQuery(sqlbv2);
		if (setLMCertifyDes.size() == 0) {
			buildError("inputCertify", "输入的单证编码有误.");
			return false;
		}

		LMCertifyDesSchema schemaLMCertifyDes = setLMCertifyDes.get(1);

		// 新增单证状态记录
		LZCardSchema tLZCardSchema = new LZCardSchema();
		tLZCardSchema.setCertifyCode(schemaLZCardPrint.getCertifyCode());

		tLZCardSchema.setSubCode(schemaLMCertifyDes.getSubCode());
		tLZCardSchema.setRiskCode(schemaLMCertifyDes.getRiskCode());
		tLZCardSchema.setRiskVersion(schemaLMCertifyDes.getRiskVersion());

		tLZCardSchema.setStartNo(schemaLZCardPrint.getStartNo());
		tLZCardSchema.setEndNo(schemaLZCardPrint.getEndNo());
		tLZCardSchema.setSendOutCom(INPUT_COM);
		tLZCardSchema.setReceiveCom(aLZCardSchema.getReceiveCom());
		tLZCardSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(schemaLZCardPrint.getEndNo(),
				schemaLZCardPrint.getStartNo()) + 1);
		tLZCardSchema.setHandler(aLZCardSchema.getHandler());
		tLZCardSchema.setHandleDate(aLZCardSchema.getHandleDate());
		tLZCardSchema.setInvaliDate(schemaLZCardPrint.getMaxDate());
		tLZCardSchema.setTakeBackNo(aLZCardSchema.getTakeBackNo());
		tLZCardSchema.setSaleChnl("");
		tLZCardSchema.setStateFlag("2");// 2、已入库（库存） 入库确认后的处于已入库(库存)。
		tLZCardSchema.setOperateFlag("4");// 4、入库
		tLZCardSchema.setPayFlag("");
		tLZCardSchema.setEnterAccFlag("");
		tLZCardSchema.setReason("");
		tLZCardSchema.setState("");
		tLZCardSchema.setOperator(globalInput.Operator);
		tLZCardSchema.setMakeDate(PubFun.getCurrentDate());
		tLZCardSchema.setMakeTime(PubFun.getCurrentTime());
		tLZCardSchema.setModifyDate(PubFun.getCurrentDate());
		tLZCardSchema.setModifyTime(PubFun.getCurrentTime());
		tLZCardSchema.setAgentCom(aLZCardSchema.getAgentCom());
		vResult.set(3, tLZCardSchema);

		// 记录单证操作轨迹
		LZCardTrackSchema tLZCardTrackSchema = new LZCardTrackSchema();
		tLZCardTrackSchema.setCertifyCode(schemaLZCardPrint.getCertifyCode());

		tLZCardTrackSchema.setSubCode(schemaLMCertifyDes.getSubCode());
		tLZCardTrackSchema.setRiskCode(schemaLMCertifyDes.getRiskCode());
		tLZCardTrackSchema.setRiskVersion(schemaLMCertifyDes.getRiskVersion());

		tLZCardTrackSchema.setStartNo(schemaLZCardPrint.getStartNo());
		tLZCardTrackSchema.setEndNo(schemaLZCardPrint.getEndNo());
		tLZCardTrackSchema.setSendOutCom(INPUT_COM);
		tLZCardTrackSchema.setReceiveCom(aLZCardSchema.getReceiveCom());
		tLZCardTrackSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(tLZCardTrackSchema.getEndNo(),
				tLZCardTrackSchema.getStartNo()) + 1);
		tLZCardTrackSchema.setPrem("");
		tLZCardTrackSchema.setAmnt("");
		tLZCardTrackSchema.setHandler(aLZCardSchema.getHandler());
		tLZCardTrackSchema.setHandleDate(aLZCardSchema.getHandleDate());
		tLZCardTrackSchema.setInvaliDate(schemaLZCardPrint.getMaxDate());
		tLZCardTrackSchema.setTakeBackNo(aLZCardSchema.getTakeBackNo());
		tLZCardTrackSchema.setSaleChnl("");
		tLZCardTrackSchema.setStateFlag("2");// 2、已入库（库存） 入库确认后的处于已入库(库存)。
		tLZCardTrackSchema.setOperateFlag("4");
		tLZCardTrackSchema.setPayFlag("");
		tLZCardTrackSchema.setEnterAccFlag("");
		tLZCardTrackSchema.setReason("");
		tLZCardTrackSchema.setState("");
		tLZCardTrackSchema.setOperator(globalInput.Operator);
		tLZCardTrackSchema.setMakeDate(PubFun.getCurrentDate());
		tLZCardTrackSchema.setMakeTime(PubFun.getCurrentTime());
		tLZCardTrackSchema.setModifyDate(PubFun.getCurrentDate());
		tLZCardTrackSchema.setModifyTime(PubFun.getCurrentTime());
		tLZCardTrackSchema.setAgentCom(aLZCardSchema.getAgentCom());

		vResult.set(4, tLZCardTrackSchema);

		return true;
	}

	/*
	 * Kevin 2003-03-24 拆分单证（入库） golbalInput : 全局空间 aLZCardSchema :
	 * 单证记录。表示要发放的单证信息 bLimitFlag : 增领标志。true表示要增领，则不校验业务员手中的最大单证数量；否则，需要校验。
	 * vResult : 返回的结果集。其中第一个元素是要删除的LZCard的信息， 第二和第三个元素是要插入的LZCard的信息，
	 * 第四个元素是要插入的LZCard的信息， 第五个元素是要插入的LZCardTrack的信息。
	 * 第六个元素是要更新的LZCardPrint的信息（对单证入库操作的支持）。
	 */
	protected static boolean splitCertifyGetIn(GlobalInput globalInput, LZCardSchema aLZCardSchema,
			boolean bLimitFlag, VData vResult) {
		logger.debug("**************进入入库时的单证拆分的处理函数!******************");
		mErrors.clearErrors();
		String szSql = "";
		String szCertifyCode = aLZCardSchema.getCertifyCode();
		String szReceiveCom = aLZCardSchema.getReceiveCom();
		String szSendOutCom = aLZCardSchema.getSendOutCom();
		String strStartNo = aLZCardSchema.getStartNo();
		String strEndNo = aLZCardSchema.getEndNo();
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证类型:" + szCertifyCode);
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证发放机构:" + szSendOutCom);
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证接收机构:" + szReceiveCom);
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证起始号:" + strStartNo);
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证终止号:" + strEndNo);
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证状态:" + aLZCardSchema.getStateFlag());
		logger.debug("splitCertifyGetIn: 传入的需要入库的操作标志:" + aLZCardSchema.getOperateFlag());

		vResult.clear();
		vResult.add(0, null);
		vResult.add(1, null);
		vResult.add(2, null);
		vResult.add(3, null);
		vResult.add(4, null);
		vResult.add(5, null);
		vResult.add(6, null);
		vResult.add(7, null);

		szSql = "SELECT * FROM LZCard WHERE CertifyCode = '";
		szSql += "?CertifyCode?";
		szSql += "' AND StateFlag = '1' AND ReceiveCom = '";
		szSql += "?ReceiveCom?";
		szSql += "' AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '" + "?StartNo?";
		szSql += "' AND StartNo <= '" + "?EndNo?" + "' AND EndNo >= '" + "?EndNo?" + "'";
		logger.debug("splitCertifyGetIn: 单证入库拆分SQL:" + szSql);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(szSql);
		sqlbv3.put("CertifyCode", szCertifyCode);
		sqlbv3.put("ReceiveCom", szReceiveCom);
		sqlbv3.put("StartNo", strStartNo);
		sqlbv3.put("EndNo", strEndNo);
		LZCardDB dbLZCard = new LZCardDB();
		LZCardSet set = dbLZCard.executeQuery(sqlbv3);
		if (set.size() == 1) { // 输入的单证号在可用的单证号内
			dbLZCard.setSchema(set.get(1)); // 直接使用查询出来的那条数据
			String strOldStartNo = dbLZCard.getStartNo();
			String strOldEndNo = dbLZCard.getEndNo();
			int nNoLen = getCertifyNoLen(dbLZCard.getCertifyCode());

			logger.debug("splitCertifyGetIn:需要删除的单证起始号:" + strOldStartNo);
			logger.debug("splitCertifyGetIn:需要删除的单证终止号:" + strOldEndNo);
			logger.debug("splitCertifyGetIn:需要删除的单证的定义的号码长度:" + nNoLen);

			// 从LZCard表中删除原有单证
			vResult.set(0, dbLZCard.getSchema());

			// 插入第一个新单证到LZCard表中
			if (CertifyFunc.bigIntegerDiff(strStartNo, strOldStartNo) > 0) {
				logger.debug("splitCertifyGetIn: 准备插入的第一部分的单证的数据");

				dbLZCard.setStartNo(strOldStartNo);
				dbLZCard.setEndNo(bigIntegerPlus(strStartNo, "-1", nNoLen));
				dbLZCard.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard
						.getStartNo()) + 1);
				dbLZCard.setModifyDate(PubFun.getCurrentDate());
				dbLZCard.setModifyTime(PubFun.getCurrentTime());

				logger.debug("splitCertifyGetIn: 准备插入的第一部分的单证的起始号:" + dbLZCard.getStartNo());
				logger.debug("splitCertifyGetIn: 准备插入的第一部分的单证的终止号:" + dbLZCard.getEndNo());
				logger.debug("splitCertifyGetIn: 准备插入的第一部分的单证的数量:" + dbLZCard.getSumCount());

				vResult.set(1, dbLZCard.getSchema());
			}

			// 插入第二个新单证到LZCard表中
			if (CertifyFunc.bigIntegerDiff(strOldEndNo, strEndNo) > 0) {
				logger.debug("splitCertifyGetIn: 准备插入的第二部分的单证的数据");

				dbLZCard.setStartNo(bigIntegerPlus(strEndNo, "1", nNoLen));
				dbLZCard.setEndNo(strOldEndNo);
				dbLZCard.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard
						.getStartNo()) + 1);

				logger.debug("splitCertifyGetIn: 准备插入的第二部分的单证的起始号:" + dbLZCard.getStartNo());
				logger.debug("splitCertifyGetIn: 准备插入的第二部分的单证的终止号:" + dbLZCard.getEndNo());
				logger.debug("splitCertifyGetIn: 准备插入的第二部分的单证的数量:" + dbLZCard.getSumCount());

				vResult.set(2, dbLZCard.getSchema());
			}
		} else {
			buildError("splitCertify", "校验单证号失败，输入的单证号不在可用单证号的范围内!");
			return false;
		}

		// 保存新的单证
		dbLZCard.setCertifyCode(aLZCardSchema.getCertifyCode());
		dbLZCard.setStartNo(aLZCardSchema.getStartNo());
		dbLZCard.setEndNo(aLZCardSchema.getEndNo());
		dbLZCard.setSendOutCom(aLZCardSchema.getSendOutCom());
		dbLZCard.setReceiveCom(aLZCardSchema.getReceiveCom());
		dbLZCard
				.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard.getStartNo()) + 1);
		dbLZCard.setHandler(aLZCardSchema.getHandler());
		dbLZCard.setHandleDate(aLZCardSchema.getHandleDate());
		// dbLZCard.setInvaliDate(aLZCardSchema.getInvaliDate());
		dbLZCard.setTakeBackNo(aLZCardSchema.getTakeBackNo());
		dbLZCard.setSaleChnl("");
		dbLZCard.setStateFlag("2");// 2、已入库（库存） 入库确认后的处于已入库(库存)。
		dbLZCard.setOperateFlag("4");// 4、入库
		dbLZCard.setPayFlag("");
		dbLZCard.setEnterAccFlag("");
		dbLZCard.setState("");
		dbLZCard.setOperator(globalInput.Operator);
		dbLZCard.setMakeDate(PubFun.getCurrentDate());
		dbLZCard.setMakeTime(PubFun.getCurrentTime());
		dbLZCard.setModifyDate(PubFun.getCurrentDate());
		dbLZCard.setModifyTime(PubFun.getCurrentTime());
		dbLZCard.setAgentCom(aLZCardSchema.getAgentCom());

		logger.debug("splitCertifyGetIn: (Lzcard)准备插入的保存新的单证的起始号:" + dbLZCard.getStartNo());
		logger.debug("splitCertifyGetIn: (Lzcard)准备插入的保存新的单证的终止号:" + dbLZCard.getEndNo());
		logger.debug("splitCertifyGetIn: (Lzcard)准备插入的保存新的单证的数量:" + dbLZCard.getSumCount());

		vResult.set(3, dbLZCard.getSchema());

		// 记录单证操作轨迹
		LZCardTrackSchema tLZCardTrackSchema = new LZCardTrackSchema();

		tLZCardTrackSchema.setCertifyCode(aLZCardSchema.getCertifyCode());
		tLZCardTrackSchema.setSubCode(dbLZCard.getSubCode());
		tLZCardTrackSchema.setRiskCode(dbLZCard.getRiskCode());
		tLZCardTrackSchema.setRiskVersion(dbLZCard.getRiskVersion());
		tLZCardTrackSchema.setStartNo(aLZCardSchema.getStartNo());
		tLZCardTrackSchema.setEndNo(aLZCardSchema.getEndNo());
		tLZCardTrackSchema.setSendOutCom(aLZCardSchema.getSendOutCom());
		tLZCardTrackSchema.setReceiveCom(aLZCardSchema.getReceiveCom());
		tLZCardTrackSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(tLZCardTrackSchema.getEndNo(),
				tLZCardTrackSchema.getStartNo()) + 1);
		tLZCardTrackSchema.setPrem("");
		tLZCardTrackSchema.setAmnt(aLZCardSchema.getAmnt());
		tLZCardTrackSchema.setHandler(aLZCardSchema.getHandler());
		tLZCardTrackSchema.setHandleDate(aLZCardSchema.getHandleDate());
		tLZCardTrackSchema.setInvaliDate(dbLZCard.getInvaliDate());
		tLZCardTrackSchema.setTakeBackNo(aLZCardSchema.getTakeBackNo());
		tLZCardTrackSchema.setSaleChnl(aLZCardSchema.getSaleChnl());
		tLZCardTrackSchema.setStateFlag("2");// 2、已入库（库存） 入库确认后的处于已入库(库存)。
		tLZCardTrackSchema.setOperateFlag("4");
		tLZCardTrackSchema.setPayFlag("");
		tLZCardTrackSchema.setEnterAccFlag("");
		tLZCardTrackSchema.setReason("");
		tLZCardTrackSchema.setState("");
		tLZCardTrackSchema.setOperator(globalInput.Operator);
		tLZCardTrackSchema.setMakeDate(PubFun.getCurrentDate());
		tLZCardTrackSchema.setMakeTime(PubFun.getCurrentTime());
		tLZCardTrackSchema.setModifyDate(PubFun.getCurrentDate());
		tLZCardTrackSchema.setModifyTime(PubFun.getCurrentTime());
		tLZCardTrackSchema.setAgentCom(aLZCardSchema.getAgentCom());

		logger.debug("splitCertifyGetIn: (LzcardTrack)准备插入的保存新的单证的起始号:"
				+ tLZCardTrackSchema.getStartNo());
		logger.debug("splitCertifyGetIn: (LzcardTrack)准备插入的保存新的单证的终止号:" + tLZCardTrackSchema.getEndNo());
		logger.debug("splitCertifyGetIn: (LzcardTrack)准备插入的保存新的单证的数量:"
				+ tLZCardTrackSchema.getSumCount());

		vResult.set(4, tLZCardTrackSchema);

		logger.debug("**************入库时的单证拆分的处理函数结束!!******************");

		return true;
	}

	/*
	 * Kevin 2003-03-24 拆分单证（拒绝入库） golbalInput : 全局空间 aLZCardSchema :
	 * 单证记录。表示要发放的单证信息 bLimitFlag : 增领标志。true表示要增领，则不校验业务员手中的最大单证数量；否则，需要校验。
	 * vResult : 返回的结果集。其中第一个元素是要删除的LZCard的信息， 第二和第三个元素是要插入的LZCard的信息，
	 * 第四个元素是要插入的LZCard的信息， 第五个元素是要插入的LZCardTrack的信息。
	 * 第六个元素是要更新的LZCardPrint的信息（对单证入库操作的支持）。
	 */
	protected static boolean splitCertifyGetInCanel(GlobalInput globalInput, LZCardSchema aLZCardSchema,
			boolean bLimitFlag, VData vResult) {
		logger.debug("**************进入入库时的单证拆分的处理函数!******************");
		mErrors.clearErrors();
		String szSql = "";
		String szCertifyCode = aLZCardSchema.getCertifyCode();
		String szReceiveCom = aLZCardSchema.getReceiveCom();
		String szSendOutCom = aLZCardSchema.getSendOutCom();
		String strStartNo = aLZCardSchema.getStartNo();
		String strEndNo = aLZCardSchema.getEndNo();
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证类型:" + szCertifyCode);
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证发放机构:" + szSendOutCom);
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证接收机构:" + szReceiveCom);
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证起始号:" + strStartNo);
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证终止号:" + strEndNo);
		logger.debug("splitCertifyGetIn: 传入的需要入库的单证状态:" + aLZCardSchema.getStateFlag());
		logger.debug("splitCertifyGetIn: 传入的需要入库的操作标志:" + aLZCardSchema.getOperateFlag());
		
		vResult.clear();
		vResult.add(0, null);
		vResult.add(1, null);
		vResult.add(2, null);
		vResult.add(3, null);
		vResult.add(4, null);
		vResult.add(5, null);
		vResult.add(6, null);
		vResult.add(7, null);

		szSql = "SELECT * FROM LZCard WHERE CertifyCode = '";
		szSql += "?CertifyCode?";
		szSql += "' AND StateFlag = '1' AND ReceiveCom = '";
		szSql += "?ReceiveCom?";
		szSql += "' AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '" + "?StartNo?";
		szSql += "' AND StartNo <= '" + "?EndNo?" + "' AND EndNo >= '" + "?EndNo?" + "'";
		logger.debug("splitCertifyGetIn: 单证入库拆分SQL:" + szSql);
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(szSql);
		sqlbv4.put("CertifyCode", szCertifyCode);
		sqlbv4.put("ReceiveCom", szReceiveCom);
		sqlbv4.put("StartNo", strStartNo);
		sqlbv4.put("EndNo", strEndNo);
		LZCardDB dbLZCard = new LZCardDB();
		LZCardSet set = dbLZCard.executeQuery(sqlbv4);
		if (set.size() == 1) { // 输入的单证号在可用的单证号内
			dbLZCard.setSchema(set.get(1)); // 直接使用查询出来的那条数据
			String strOldStartNo = dbLZCard.getStartNo();
			String strOldEndNo = dbLZCard.getEndNo();
			int nNoLen = getCertifyNoLen(dbLZCard.getCertifyCode());

			logger.debug("splitCertifyGetIn:需要删除的单证起始号:" + strOldStartNo);
			logger.debug("splitCertifyGetIn:需要删除的单证终止号:" + strOldEndNo);
			logger.debug("splitCertifyGetIn:需要删除的单证的定义的号码长度:" + nNoLen);

			// 从LZCard表中删除原有单证
			vResult.set(0, dbLZCard.getSchema());

			// 插入第一个新单证到LZCard表中
			if (CertifyFunc.bigIntegerDiff(strStartNo, strOldStartNo) > 0) {
				logger.debug("splitCertifyGetIn: 准备插入的第一部分的单证的数据");

				dbLZCard.setStartNo(strOldStartNo);
				dbLZCard.setEndNo(bigIntegerPlus(strStartNo, "-1", nNoLen));
				dbLZCard.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard
						.getStartNo()) + 1);
				dbLZCard.setModifyDate(PubFun.getCurrentDate());
				dbLZCard.setModifyTime(PubFun.getCurrentTime());

				logger.debug("splitCertifyGetIn: 准备插入的第一部分的单证的起始号:" + dbLZCard.getStartNo());
				logger.debug("splitCertifyGetIn: 准备插入的第一部分的单证的终止号:" + dbLZCard.getEndNo());
				logger.debug("splitCertifyGetIn: 准备插入的第一部分的单证的数量:" + dbLZCard.getSumCount());

				vResult.set(1, dbLZCard.getSchema());
			}

			// 插入第二个新单证到LZCard表中
			if (CertifyFunc.bigIntegerDiff(strOldEndNo, strEndNo) > 0) {
				logger.debug("splitCertifyGetIn: 准备插入的第二部分的单证的数据");

				dbLZCard.setStartNo(bigIntegerPlus(strEndNo, "1", nNoLen));
				dbLZCard.setEndNo(strOldEndNo);
				dbLZCard.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard
						.getStartNo()) + 1);

				logger.debug("splitCertifyGetIn: 准备插入的第二部分的单证的起始号:" + dbLZCard.getStartNo());
				logger.debug("splitCertifyGetIn: 准备插入的第二部分的单证的终止号:" + dbLZCard.getEndNo());
				logger.debug("splitCertifyGetIn: 准备插入的第二部分的单证的数量:" + dbLZCard.getSumCount());

				vResult.set(2, dbLZCard.getSchema());
			}
		} else {
			buildError("splitCertify", "校验单证号失败，输入的单证号不在可用单证号的范围内!");
			return false;
		}

		// 保存新的单证
		dbLZCard.setCertifyCode(aLZCardSchema.getCertifyCode());
		dbLZCard.setStartNo(aLZCardSchema.getStartNo());
		dbLZCard.setEndNo(aLZCardSchema.getEndNo());

		dbLZCard.setSendOutCom(aLZCardSchema.getReceiveCom());// 返回原发放机构的待入库状态
		dbLZCard.setReceiveCom(aLZCardSchema.getSendOutCom());
		if (dbLZCard.getReceiveCom().length() == 9 || dbLZCard.getReceiveCom().charAt(0) == 'B'
				|| dbLZCard.getReceiveCom().charAt(0) == 'D') {
			dbLZCard.setStateFlag("3");// 3、已发放，待回销
		} else {
			dbLZCard.setStateFlag("1");// 1、待入库 处于待入库池中，需要进行入库确认。
		}
		dbLZCard
				.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard.getStartNo()) + 1);
		dbLZCard.setHandler(aLZCardSchema.getHandler());
		dbLZCard.setHandleDate(aLZCardSchema.getHandleDate());
		// dbLZCard.setInvaliDate(aLZCardSchema.getInvaliDate());
		dbLZCard.setTakeBackNo(aLZCardSchema.getTakeBackNo());
		dbLZCard.setSaleChnl("");
		dbLZCard.setOperateFlag("5");// 拒绝入库
		dbLZCard.setPayFlag("");
		dbLZCard.setEnterAccFlag("");
		dbLZCard.setReason(aLZCardSchema.getReason());
		dbLZCard.setState("");
		dbLZCard.setOperator(globalInput.Operator);
		dbLZCard.setMakeDate(PubFun.getCurrentDate());
		dbLZCard.setMakeTime(PubFun.getCurrentTime());
		dbLZCard.setModifyDate(PubFun.getCurrentDate());
		dbLZCard.setModifyTime(PubFun.getCurrentTime());
		dbLZCard.setAgentCom(aLZCardSchema.getAgentCom());

		logger.debug("splitCertifyGetIn: (Lzcard)准备插入的保存新的单证的起始号:" + dbLZCard.getStartNo());
		logger.debug("splitCertifyGetIn: (Lzcard)准备插入的保存新的单证的终止号:" + dbLZCard.getEndNo());
		logger.debug("splitCertifyGetIn: (Lzcard)准备插入的保存新的单证的数量:" + dbLZCard.getSumCount());

		vResult.set(3, dbLZCard.getSchema());

		// 记录单证操作轨迹
		LZCardTrackSchema tLZCardTrackSchema = new LZCardTrackSchema();

		tLZCardTrackSchema.setCertifyCode(aLZCardSchema.getCertifyCode());
		tLZCardTrackSchema.setSubCode(dbLZCard.getSubCode());
		tLZCardTrackSchema.setRiskCode(dbLZCard.getRiskCode());
		tLZCardTrackSchema.setRiskVersion(dbLZCard.getRiskVersion());
		tLZCardTrackSchema.setStartNo(aLZCardSchema.getStartNo());
		tLZCardTrackSchema.setEndNo(aLZCardSchema.getEndNo());

		tLZCardTrackSchema.setSendOutCom(aLZCardSchema.getReceiveCom());// 返回原发放机构的待入库状态
		tLZCardTrackSchema.setReceiveCom(aLZCardSchema.getSendOutCom());

		tLZCardTrackSchema.setStateFlag(dbLZCard.getStateFlag());

		tLZCardTrackSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(tLZCardTrackSchema.getEndNo(),
				tLZCardTrackSchema.getStartNo()) + 1);
		tLZCardTrackSchema.setPrem("");
		tLZCardTrackSchema.setAmnt(aLZCardSchema.getAmnt());
		tLZCardTrackSchema.setHandler(aLZCardSchema.getHandler());
		tLZCardTrackSchema.setHandleDate(aLZCardSchema.getHandleDate());
		tLZCardTrackSchema.setInvaliDate(dbLZCard.getInvaliDate());
		tLZCardTrackSchema.setTakeBackNo(aLZCardSchema.getTakeBackNo());
		tLZCardTrackSchema.setSaleChnl(aLZCardSchema.getSaleChnl());
		tLZCardTrackSchema.setOperateFlag("5");
		tLZCardTrackSchema.setPayFlag("");
		tLZCardTrackSchema.setEnterAccFlag("");
		tLZCardTrackSchema.setReason("");
		tLZCardTrackSchema.setState("");
		tLZCardTrackSchema.setOperator(globalInput.Operator);
		tLZCardTrackSchema.setMakeDate(PubFun.getCurrentDate());
		tLZCardTrackSchema.setMakeTime(PubFun.getCurrentTime());
		tLZCardTrackSchema.setModifyDate(PubFun.getCurrentDate());
		tLZCardTrackSchema.setModifyTime(PubFun.getCurrentTime());
		tLZCardTrackSchema.setAgentCom(aLZCardSchema.getAgentCom());

		logger.debug("splitCertifyGetIn: (LzcardTrack)准备插入的保存新的单证的起始号:"
				+ tLZCardTrackSchema.getStartNo());
		logger.debug("splitCertifyGetIn: (LzcardTrack)准备插入的保存新的单证的终止号:" + tLZCardTrackSchema.getEndNo());
		logger.debug("splitCertifyGetIn: (LzcardTrack)准备插入的保存新的单证的数量:"
				+ tLZCardTrackSchema.getSumCount());

		vResult.set(4, tLZCardTrackSchema);

		logger.debug("**************入库时的单证拆分的处理函数结束!!******************");

		return true;
	}

	/*
	 * Kevin 2003-03-24 拆分单证（发放） golbalInput:全局空间; aLZCardSchema:表示要发放的单证信息;
	 * vResult:返回的结果集：其中第一个元素是要删除的LZCard的信息，第二和第三个元素是要插入的LZCard的信息，
	 * 第四个元素是要插入的LZCard的信息，第五个元素是要插入的LZCardTrack的信息，
	 * 第六个元素是要更新的LZCardPrint的信息（对单证入库操作的支持），
	 * **为了避免在回收单证时候拆分单证，对于特定类型的单证，在发放时就拆分到单条单证记录，
	 * 这样，在这个函数中，需要一次插入多笔单条单证记录。因此，增加第七个元素，存放要插入的LZCardSet的信息。
	 */
	protected static boolean splitCertifySendOut(GlobalInput globalInput, LZCardSchema aLZCardSchema,
			VData vResult) {
		logger.debug("**************进入发放时的单证拆分的处理函数!******************");
		mErrors.clearErrors();
		vResult.clear();
		vResult.add(0, null);
		vResult.add(1, null);
		vResult.add(2, null);
		vResult.add(3, null);
		vResult.add(4, null);
		vResult.add(5, null);
		vResult.add(6, null);
		vResult.add(7, null);

		LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
		tLMCertifyDesDB.setCertifyCode(aLZCardSchema.getCertifyCode());
		if (!tLMCertifyDesDB.getInfo()) {
			buildError("splitCertify", "在单证描述表中找不到单证类型的信息！");
			return false;
		}

		String szCertifyCode = aLZCardSchema.getCertifyCode();
		String szReceiveCom = aLZCardSchema.getReceiveCom();
		String szSendOutCom = aLZCardSchema.getSendOutCom();
		String strStartNo = aLZCardSchema.getStartNo();
		String strEndNo = aLZCardSchema.getEndNo();

		int nNoLen = getCertifyNoLen(aLZCardSchema.getCertifyCode());

		logger.debug("splitCertifySendOut1: 传入的需要发放的单证类型:" + szCertifyCode);
		logger.debug("splitCertifySendOut1: 传入的需要发放的单证起始号:" + strStartNo);
		logger.debug("splitCertifySendOut1: 传入的需要发放的单证终止号:" + strEndNo);
		logger.debug("splitCertifySendOut1: 传入的需要发放的单证发放机构:" + szSendOutCom);
		logger.debug("splitCertifySendOut1: 传入的需要发放的单证接收机构:" + szReceiveCom);
		logger.debug("splitCertifySendOut1: 传入的需要发放的单证代理机构:" + aLZCardSchema.getAgentCom());
		logger.debug("splitCertifySendOut1: 传入的需要发放的单证状态:" + aLZCardSchema.getStateFlag());
		logger.debug("splitCertifySendOut1: 传入的需要发放的操作标志:" + aLZCardSchema.getOperateFlag());
		
		// 发放到部门的单证为3已发放未核销，也可以继续发放给代理人
		String szSql = "SELECT * FROM LZCard WHERE CertifyCode = '" + "?CertifyCode?"
				+ "' AND StateFlag in ('2','3') AND ReceiveCom = '" + "?ReceiveCom?" + "' AND StartNo <= '"
				+ "?StartNo?" + "' AND EndNo >= '" + "?StartNo?" + "' AND StartNo <= '" + "?EndNo?"
				+ "' AND EndNo >= '" + "?EndNo?" + "'";
		logger.debug("splitCertifySendOut2: 单证校验(发放的单证必须是本机机构已入库的)的SQL:" + szSql);
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(szSql);
		sqlbv5.put("CertifyCode", szCertifyCode);
		sqlbv5.put("ReceiveCom", szSendOutCom);
		sqlbv5.put("StartNo", strStartNo);
		sqlbv5.put("EndNo", strEndNo);
		LZCardDB dbLZCard = new LZCardDB();
		LZCardSet set = dbLZCard.executeQuery(sqlbv5);
		if (set.size() == 1) { // 输入的单证号在可用的单证号内
			dbLZCard.setSchema(set.get(1)); // 直接使用查询出来的那条数据
			String strOldStartNo = dbLZCard.getStartNo();
			String strOldEndNo = dbLZCard.getEndNo();

			logger.debug("splitCertifySendOut3: 查询出的需要被删除的单证起始号:" + strOldStartNo);
			logger.debug("splitCertifySendOut3: 查询出的需要被删除的单证终止号:" + strOldEndNo);
			logger.debug("splitCertifySendOut3: 查询出的需要被删除的单证号码长度:" + nNoLen);
			// 从LZCard表中删除原有单证
			vResult.set(0, dbLZCard.getSchema());

			// 插入第一个新单证到LZCard表中
			if (CertifyFunc.bigIntegerDiff(strStartNo, strOldStartNo) > 0) {
				logger.debug("splitCertifySendOut: 准备插入的第一部分的单证的数据");

				dbLZCard.setStartNo(strOldStartNo);
				dbLZCard.setEndNo(bigIntegerPlus(strStartNo, "-1", nNoLen));
				dbLZCard.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard
						.getStartNo()) + 1);
				dbLZCard.setModifyDate(PubFun.getCurrentDate());
				dbLZCard.setModifyTime(PubFun.getCurrentTime());

				logger.debug("splitCertifySendOut4: 准备插入的第一部分的单证的起始号:" + dbLZCard.getStartNo());
				logger.debug("splitCertifySendOut4: 准备插入的第一部分的单证的终止号:" + dbLZCard.getEndNo());
				logger.debug("splitCertifySendOut4: 准备插入的第一部分的单证的数量:" + dbLZCard.getSumCount());
				logger.debug("splitCertifySendOut4: 准备插入的第一部分的单证修改日期:" + dbLZCard.getModifyDate());
				logger.debug("splitCertifySendOut4: 准备插入的第一部分的单证修改日期:" + dbLZCard.getModifyTime());

				vResult.set(1, dbLZCard.getSchema());
			}

			// 插入第二个新单证到LZCard表中
			if (CertifyFunc.bigIntegerDiff(strOldEndNo, strEndNo) > 0) {
				logger.debug("splitCertifySendOut: 准备插入的第二部分的单证的数据");

				dbLZCard.setStartNo(bigIntegerPlus(strEndNo, "1", nNoLen));
				dbLZCard.setEndNo(strOldEndNo);
				dbLZCard.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard
						.getStartNo()) + 1);

				logger.debug("splitCertifySendOut5: 准备插入的第二部分的单证的起始号:" + dbLZCard.getStartNo());
				logger.debug("splitCertifySendOut5: 准备插入的第二部分的单证的终止号:" + dbLZCard.getEndNo());
				logger.debug("splitCertifySendOut5: 准备插入的第二部分的单证的数量:" + dbLZCard.getSumCount());

				vResult.set(2, dbLZCard.getSchema());
			}
		} else {
			buildError("splitCertify", "校验单证号失败，输入的单证号不在可用单证号的范围内!");
			return false;
		}

		// 保存新的单证
		// 使用截止日期，单证印刷的时候确定的，每张单证都应该有，当前时间>使用截止日期,单证不允许使用：
		// 区别于个人/非个人代理人使用期，是对发放到代理人后的有效使用期：发放时间makedate+个人/非个人代理人使用期>当前时间为过期单证
		String invalidate = getInvalidate(aLZCardSchema.getCertifyCode(), strStartNo, strEndNo);
		aLZCardSchema.setInvaliDate(invalidate); // add by ly

		// 需要核销的单证：部门、代理人、四级机构需要拆分
		// 在此处，根据单证描述表中的信息和单证接收者的信息决定是否要将单证拆分成单条记录。
		// 需要核销的单证：部门、代理人、四级机构需要拆分成每张单证一条记录，以便于回收的时候提高查询效率
		char cFlag = aLZCardSchema.getReceiveCom().charAt(0);
		if ((cFlag == 'B' || cFlag == 'D' || aLZCardSchema.getReceiveCom().length() == 9)
				&& "Y".equals(tLMCertifyDesDB.getTackBackFlag())) {
			dbLZCard.setCertifyCode(aLZCardSchema.getCertifyCode());
			dbLZCard.setSendOutCom(aLZCardSchema.getSendOutCom());
			dbLZCard.setReceiveCom(aLZCardSchema.getReceiveCom());
			dbLZCard.setSumCount(1);
			dbLZCard.setHandler(aLZCardSchema.getHandler());
			dbLZCard.setHandleDate(aLZCardSchema.getHandleDate());
			dbLZCard.setInvaliDate(aLZCardSchema.getInvaliDate());
			dbLZCard.setTakeBackNo(aLZCardSchema.getTakeBackNo());
			dbLZCard.setSaleChnl("");
			dbLZCard.setStateFlag("3");// 3、已发放未核销
			dbLZCard.setOperateFlag("0");
			dbLZCard.setPayFlag("");
			dbLZCard.setEnterAccFlag("");
			dbLZCard.setReason("");
			dbLZCard.setState("");
			dbLZCard.setOperator(globalInput.Operator);
			dbLZCard.setMakeDate(PubFun.getCurrentDate());
			dbLZCard.setMakeTime(PubFun.getCurrentTime());
			dbLZCard.setModifyDate(PubFun.getCurrentDate());
			dbLZCard.setModifyTime(PubFun.getCurrentTime());
			dbLZCard.setApplyNo(aLZCardSchema.getApplyNo());
			dbLZCard.setAgentCom(aLZCardSchema.getAgentCom());

			LZCardSet tLZCardSet = new LZCardSet();
			String strNewNo = aLZCardSchema.getStartNo();
			int nSize = (int) CertifyFunc
					.bigIntegerDiff(aLZCardSchema.getEndNo(), aLZCardSchema.getStartNo()) + 1;
			
			/**
			 * 2009-06-05 防止一次性拆分记录过多,增加一次性拆分的限制
			 * zhangzheng
			 */
			if(nSize>100000)
			{
				buildError("splitCertify", "一次拆分单证记录超过100000张,请分批发放!");
				return false;
			}
			
			for (int nIndex = 0; nIndex < nSize; nIndex++) {
				dbLZCard.setStartNo(strNewNo);
				dbLZCard.setEndNo(strNewNo);
				try {
					tLZCardSet.add((LZCardSchema) dbLZCard.clone());
				} catch (Exception ex) {
					ex.printStackTrace();
					buildError("splitCertify", "在生成单笔单证记录时出现错误！");
					return false;
				}

				strNewNo = CertifyFunc.bigIntegerPlus(strNewNo, "1", nNoLen);
			}

			vResult.set(6, tLZCardSet);
		} else { // 如果不需要拆分
			dbLZCard.setCertifyCode(aLZCardSchema.getCertifyCode());
			dbLZCard.setStartNo(aLZCardSchema.getStartNo());
			dbLZCard.setEndNo(aLZCardSchema.getEndNo());
			dbLZCard.setSendOutCom(aLZCardSchema.getSendOutCom());
			dbLZCard.setReceiveCom(aLZCardSchema.getReceiveCom());
			dbLZCard
					.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard.getStartNo()) + 1);
			dbLZCard.setHandler(aLZCardSchema.getHandler());
			dbLZCard.setHandleDate(aLZCardSchema.getHandleDate());
			dbLZCard.setInvaliDate(dbLZCard.getInvaliDate());
			dbLZCard.setTakeBackNo(aLZCardSchema.getTakeBackNo());
			dbLZCard.setSaleChnl("");
			dbLZCard.setOperateFlag("0");
			dbLZCard.setPayFlag("");
			dbLZCard.setEnterAccFlag("");
			dbLZCard.setReason("");
			dbLZCard.setState("");
			dbLZCard.setOperator(globalInput.Operator);
			dbLZCard.setMakeDate(PubFun.getCurrentDate());
			dbLZCard.setMakeTime(PubFun.getCurrentTime());
			dbLZCard.setModifyDate(PubFun.getCurrentDate());
			dbLZCard.setModifyTime(PubFun.getCurrentTime());
			dbLZCard.setApplyNo(aLZCardSchema.getApplyNo());
			dbLZCard.setAgentCom(aLZCardSchema.getAgentCom());

			// 接收者为B部门、D代理人、四级机构时，状态置为3、已发放未核销，可以直接从此回收核銷
			if (aLZCardSchema.getReceiveCom().charAt(0) == 'B'
					|| aLZCardSchema.getReceiveCom().charAt(0) == 'D'
					|| aLZCardSchema.getReceiveCom().length() == 9) {
				dbLZCard.setStateFlag("3");// 3、已发放未核销
			} else {
				dbLZCard.setStateFlag("1");// 1、待入库
			}

			logger.debug("splitCertifySendOut6: (Lzcard)准备插入的保存新的单证的起始号:" + dbLZCard.getStartNo());
			logger.debug("splitCertifySendOut6: (Lzcard)准备插入的保存新的单证的终止号:" + dbLZCard.getEndNo());
			logger.debug("splitCertifySendOut6: (Lzcard)准备插入的保存新的单证的数量:" + dbLZCard.getSumCount());

			vResult.set(3, dbLZCard.getSchema());
		}

		// 记录单证操作轨迹
		LZCardTrackSchema tLZCardTrackSchema = new LZCardTrackSchema();
		tLZCardTrackSchema.setCertifyCode(aLZCardSchema.getCertifyCode());
		tLZCardTrackSchema.setSubCode(dbLZCard.getSubCode());
		tLZCardTrackSchema.setRiskCode(dbLZCard.getRiskCode());
		tLZCardTrackSchema.setRiskVersion(dbLZCard.getRiskVersion());
		tLZCardTrackSchema.setStartNo(aLZCardSchema.getStartNo());
		tLZCardTrackSchema.setEndNo(aLZCardSchema.getEndNo());
		tLZCardTrackSchema.setSendOutCom(aLZCardSchema.getSendOutCom());
		tLZCardTrackSchema.setReceiveCom(aLZCardSchema.getReceiveCom());
		tLZCardTrackSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(tLZCardTrackSchema.getEndNo(),
				tLZCardTrackSchema.getStartNo()) + 1);
		tLZCardTrackSchema.setPrem("");
		tLZCardTrackSchema.setAmnt(aLZCardSchema.getAmnt());
		tLZCardTrackSchema.setHandler(aLZCardSchema.getHandler());
		tLZCardTrackSchema.setHandleDate(aLZCardSchema.getHandleDate());
		tLZCardTrackSchema.setInvaliDate(aLZCardSchema.getInvaliDate());
		tLZCardTrackSchema.setTakeBackNo(aLZCardSchema.getTakeBackNo());
		tLZCardTrackSchema.setSaleChnl("");
		tLZCardTrackSchema.setOperateFlag("0");
		tLZCardTrackSchema.setPayFlag("");
		tLZCardTrackSchema.setEnterAccFlag("");
		tLZCardTrackSchema.setReason("");
		tLZCardTrackSchema.setState("");
		tLZCardTrackSchema.setOperator(globalInput.Operator);
		tLZCardTrackSchema.setMakeDate(PubFun.getCurrentDate());
		tLZCardTrackSchema.setMakeTime(PubFun.getCurrentTime());
		tLZCardTrackSchema.setModifyDate(PubFun.getCurrentDate());
		tLZCardTrackSchema.setModifyTime(PubFun.getCurrentTime());
		tLZCardTrackSchema.setApplyNo(dbLZCard.getApplyNo());
		tLZCardTrackSchema.setAgentCom(aLZCardSchema.getAgentCom());

		// 接收者为B部门、D代理人时，状态置为3、已发放未核销
		if (aLZCardSchema.getReceiveCom().charAt(0) == 'B' || aLZCardSchema.getReceiveCom().charAt(0) == 'D'
				|| aLZCardSchema.getReceiveCom().length() == 9) {
			tLZCardTrackSchema.setStateFlag("3");// 3、已发放未核销
		} else {
			tLZCardTrackSchema.setStateFlag("1");// 1、待入库
		}

		logger.debug("splitCertifySendOut7:准备插入的LzcardTrack的起始号:" + tLZCardTrackSchema.getStartNo());
		logger.debug("splitCertifySendOut7:准备插入的LzcardTrack的终止号:" + tLZCardTrackSchema.getEndNo());
		logger.debug("splitCertifySendOut7:准备插入的LzcardTrack的数量:" + tLZCardTrackSchema.getSumCount());

		vResult.set(4, tLZCardTrackSchema);

		logger.debug("**************发放时的单证拆分的处理函数结束!!******************");

		return true;
	}

	/*
	 * 入库：校验输入的发放机构和接收机构
	 */
	protected static boolean verifyComsIn(GlobalInput globalInput, String szSendOutCom, String szReceiveCom) {
		if (!isComsExist(szSendOutCom, szReceiveCom)) {
			return false;
		}

		if (!szReceiveCom.equals("A" + globalInput.ComCode)) {
			buildError("verifyComs", "接收机构与管理员的登录机构不符");
			return false;
		}

		return true;
	}

	/*
	 * Kevin 2002-09-23 校验输入的发放机构和接收机构
	 */
	protected static boolean verifyComs(GlobalInput globalInput, String szSendOutCom, String szReceiveCom) {
		mErrors.clearErrors();

		if (!isComsExist(szSendOutCom, szReceiveCom)) {
			return false;
		}

		if (szSendOutCom.charAt(0) == 'A') { // 发放机构是区站
			if (!szSendOutCom.equals('A' + globalInput.ComCode)) {
				buildError("verifyComs", "发放机构与登录机构不符,请重新登录！");
				return false;
			}

			if (szReceiveCom.charAt(0) == 'A') { // 接收者为下级机构
				if (!verifyAccess(szSendOutCom, szReceiveCom))
					return false;
			} else if (szReceiveCom.charAt(0) == 'B') { // 接收者是部门
				if (!szSendOutCom.substring(1).equals(szReceiveCom.substring(1, szReceiveCom.length() - 2))) {
					logger.debug("发放者=" + szSendOutCom);
					logger.debug("接收者=" + szReceiveCom);
					buildError("verifyComs", "接收者不属于发放机构所属部门，请重新录入！");
					return false;
				}
			} else if (szReceiveCom.charAt(0) == 'D') { // 接收者是代理人
				if (!verifyAgent(szSendOutCom, szReceiveCom))
					return false;
			} else {
				buildError("verifyComs", "接收者的格式非法!");
				return false;
			}
		} else if (szSendOutCom.charAt(0) == 'B') { // 发放者是部门
			if (!szSendOutCom.substring(0, szSendOutCom.length() - 2).equals('B' + globalInput.ComCode)) {
				buildError("verifyComs", "发放部门所在机构与登录机构不符,请重新登录！");
				return false;
			}

			if (szReceiveCom.charAt(0) == 'D') { // 接收者是代理人
				if (!verifyAgent(szSendOutCom.substring(0, szSendOutCom.length() - 2), szReceiveCom))
					return false; // 取当前的登录机构做为发放机构
			} else {
				buildError("verifyComs", szSendOutCom + "接收者的格式非法!");
				return false;
			}
		} else {
			buildError("verifyComs", "发放者格式非法!");
			return false;
		}

		return true;
	}

	/*
	 * 对单证发放回退 校验输入的发放机构和接收机构
	 */
	protected static boolean verifyComsBack(GlobalInput globalInput, String szSendOutCom, String szReceiveCom) {
		mErrors.clearErrors();

		if (!isComsExist2(szSendOutCom, szReceiveCom)) {
			return false;
		}

		if (szReceiveCom.charAt(0) == 'A') {
			if (!szReceiveCom.equals('A' + globalInput.ComCode)) {
				buildError("verifyComs", "发放机构与登录机构不符,请重新登录！");
				return false;
			}
		} else if (szReceiveCom.charAt(0) == 'B') {
			if (!szReceiveCom.substring(0, szReceiveCom.length() - 2).equals('B' + globalInput.ComCode)) {
				buildError("verifyComs", "发放部门所在机构与登录机构不符,请重新登录！");
				return false;
			}
		}

		if (szSendOutCom.charAt(0) == 'A') { // 发放机构是区站
			if (szReceiveCom.charAt(0) == 'A') { // 接收者为下级机构
				if (!verifyAccess(szSendOutCom, szReceiveCom))
					return false;
			} else if (szReceiveCom.charAt(0) == 'B') { // 接收者是部门
				if (!szSendOutCom.substring(1).equals(szReceiveCom.substring(1, szReceiveCom.length() - 2))) {
					logger.debug("发放者=" + szSendOutCom);
					logger.debug("接收者=" + szReceiveCom);
					buildError("verifyComs", "接收者不属于发放机构所属部门，请重新录入！");
					return false;
				}
			} else if (szReceiveCom.charAt(0) == 'D') { // 接收者是代理人
				if (!verifyAgent2(szSendOutCom, szReceiveCom))
					return false;
			} else {
				buildError("verifyComs", "接收者的格式非法!");
				return false;
			}
		} else if (szSendOutCom.charAt(0) == 'B') { // 发放者是部门
			if (szReceiveCom.charAt(0) == 'D') { // 接收者是代理人
				if (!verifyAgent2(szSendOutCom.substring(0, szSendOutCom.length() - 2), szReceiveCom))
					return false; // 取当前的登录机构做为发放机构
			} else {
				buildError("verifyComs", szSendOutCom + "接收者的格式非法!");
				return false;
			}
		} else {
			buildError("verifyComs", "发放者格式非法!");
			return false;
		}

		return true;
	}

	/*
	 * 单证缴销/核销修订：校验接收者(即单证所属代理人、8位机构、部门)是否属于登录机构
	 */
	protected static boolean verifyComsTackBack(GlobalInput globalInput, String szReceiveCom) {
		String globalCom = "A" + globalInput.ComCode;// 登录机构

		if (szReceiveCom.charAt(0) == 'B') {
			
			szReceiveCom = "A" + szReceiveCom.substring(1, szReceiveCom.length() - 2);
			
			if (!szReceiveCom.startsWith(globalCom)) {// 校验接收者(即单证所属代理人、8位机构、部门)是否属于登录机构
				buildError("verifyComsTackBack", "单证所属机构" + szReceiveCom.substring(1) + "与登录机构不符,请重新登录！");
				return false;
			}
		}
		else if (szReceiveCom.charAt(0) == 'D') {
			ExeSQL exeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql("select substr(a.managecom,1,6) from laagent a where a.agentcode='"
					+ "?agentcode?" + "'and branchtype not in('6','7') union all"
					+ " select substr(a.managecom,1,4) from laagent a where a.agentcode='"
					+ "?agentcode?" + "'and branchtype in('6','7')");
			sqlbv6.put("agentcode", szReceiveCom.substring(1));
			szReceiveCom = "A"
					+ exeSQL.getOneValue(sqlbv6);	
			if(globalCom.length() <= szReceiveCom.length())
			{
				globalCom = globalCom;
			}
			else
			{
				globalCom = globalCom.substring(0, szReceiveCom.length());
			}
			logger.debug("globalCom="+globalCom);
			
			if (!szReceiveCom.startsWith(globalCom)) {// 校验接收者(即单证所属代理人、8位机构、部门)是否属于登录机构
				buildError("verifyComsTackBack", "单证所属机构" + szReceiveCom.substring(1) + "与登录机构不符,请重新登录！");
				return false;
			}
		}
		else if (szReceiveCom.charAt(0) == 'A')
		{
			if (!szReceiveCom.startsWith(globalCom)) {// 校验接收者(即单证所属代理人、8位机构、部门)是否属于登录机构
				buildError("verifyComsTackBack", "单证所属机构" + szReceiveCom.substring(1) + "与登录机构不符,请重新登录！");
				return false;
			}
		}



		return true;
	}
	
	
	/*
	 * 核销修订：校验操作员只能操作本级及下级机构案件
	 */
	protected static boolean verifyComsTackBack2(GlobalInput globalInput, String szReceiveCom) {

		if (szReceiveCom.charAt(0) == 'B') {			
			
			szReceiveCom = szReceiveCom.substring(1, szReceiveCom.length() - 2);			
		} 
		else if (szReceiveCom.charAt(0) == 'A') {			
			
			szReceiveCom = szReceiveCom.substring(1);			
		} 
		//zy 增加对代理人的校验
		else if(szReceiveCom.charAt(0) == 'D')
		{
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql("select substr(a.managecom,1,6) from laagent a where a.agentcode='"
					+ "?agentcode?" + "'and branchtype not in('6','7') union all"
					+ " select substr(a.managecom,1,4) from laagent a where a.agentcode='"
					+ "?agentcode?" + "'and branchtype in('6','7')");
			sqlbv7.put("agentcode", szReceiveCom.substring(1));
			ExeSQL exeSQL = new ExeSQL();
			szReceiveCom =  exeSQL.getOneValue(sqlbv7);	
			
			logger.debug("szReceiveCom="+szReceiveCom);		
		}
		else
		{
			buildError("verifyComsTackBack2", "该单证所属机构与登录机构不符，您不具有操作该单证的权限！");
			return false;	
		}
		
		if (!szReceiveCom.startsWith(globalInput.ComCode)) {// 校验操作员只能操作本级及下级机构案件
			
			buildError("verifyComsTackBack2", "该单证所属机构与登录机构不符，您不具有操作该单证的权限！");
			return false;
		}

		return true;
	}

	/*
	 * Kevin 2003-03-18 拆分单证（回收） 对于从业务员处的回收操作，允许单证的拆分操作；否则，必须整批回收。 golbalInput :
	 * 全局空间 aLZCardSchema : 单证记录。表示要回收的单证信息。 vResult :
	 * 返回的结果集。其中第一个元素是要删除的LZCard的信息， 第二和第三个元素是要插入的LZCard的信息，
	 * 第四个元素是要插入的LZCard的信息， 第五个元素是要插入的LZCardTrack的信息。
	 */
	protected static boolean splitCertifyTakeBack(GlobalInput globalInput, LZCardSchema inLZCardSchema,
			VData vResult) {
		// 清空返回结果集
		vResult.clear();

		logger.debug("**************进入回收时的单证拆分的处理函数!******************");

		// 初始化返回结果集
		vResult.add(0, null);
		vResult.add(1, null);
		vResult.add(2, null);
		vResult.add(3, null);
		vResult.add(4, null);

		String szSql = "";
		String szCertifyCode = inLZCardSchema.getCertifyCode();
		String strStartNo = inLZCardSchema.getStartNo();
		String strEndNo = inLZCardSchema.getEndNo();

		logger.debug("splitCertifyTakeBack1: 传入的需要回收的单证类型:" + szCertifyCode);
		logger.debug("splitCertifyTakeBack1: 传入的需要回收的单证起始号:" + strStartNo);
		logger.debug("splitCertifyTakeBack1: 传入的需要回收的单证终止号:" + strEndNo);
		logger.debug("splitCertifyTakeBack1: 传入的需要回收的单证状态:" + inLZCardSchema.getStateFlag());
		logger.debug("splitCertifyTakeBack1: 传入的需要回收的操作标志:" + inLZCardSchema.getOperateFlag());

		szSql = "SELECT * FROM LZCard WHERE CertifyCode = '" + "?CertifyCode?" + "' AND StateFlag = '3' "
				+ " AND StartNo = '" + "?StartNo?" + "' AND EndNo = '" + "?EndNo?" + "'";
		logger.debug("splitCertifyTakeBack: 单证状态校验的SQL:" + szSql);
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(szSql);
		sqlbv8.put("CertifyCode", szCertifyCode);
		sqlbv8.put("StartNo", strStartNo);
		sqlbv8.put("EndNo", strEndNo);
		LZCardDB dbLZCard = new LZCardDB();
		LZCardSet set = dbLZCard.executeQuery(sqlbv8);
		LZCardSchema tLZCardSchema = new LZCardSchema();
		if (set.size() != 1) {
			buildError("splitCertifyTakeBack", "校验单证状态失败，输入的单证号不在可用单证号的范围内!");
			return false;
		}
		tLZCardSchema = set.get(1); // 直接使用查询出来的那条数据
		vResult.set(0, tLZCardSchema);// 从LZCard表中删除原有单证，将要删除的数据放入返回结果集中
		logger.debug("splitCertifyTakeBack2: (LZCard)准备删除的单证的起始号:" + tLZCardSchema.getStartNo());
		logger.debug("splitCertifyTakeBack2: (LZCard)准备删除的单证的终止号:" + tLZCardSchema.getEndNo());
		logger.debug("splitCertifyTakeBack2: (LZCard)准备删除的单证的数量:" + tLZCardSchema.getSumCount());

		// 单证状态校验,D代理人回收，B从部门回收，9从四级机构回收
		if (tLZCardSchema.getReceiveCom().charAt(0) == 'D' || tLZCardSchema.getReceiveCom().charAt(0) == 'B'
				|| tLZCardSchema.getReceiveCom().length() == 9) {
		} else {
			buildError("splitCertifyTakeBack", "校验单证状态失败，输入的单证号不在可用单证号的范围内!");
			return false;
		}

		// 校验接收者(即单证所属代理人、8位机构、部门)是否属于登录机构
		/**
		 * zhangzheng 2009-06-18
		 * 去掉这个校验
		 */
		
		if (!verifyComsTackBack(globalInput, tLZCardSchema.getReceiveCom())) {
			mErrors.copyAllErrors(CertifyFunc.mErrors);
			return false;
		}
		

		// 保存新的单证,核销的单证保存于lzcardb
		LZCardBSchema tLZCardBSchema = new LZCardBSchema();

		Reflections tReflections = new Reflections();
		tReflections.transFields(tLZCardBSchema, tLZCardSchema);

		tLZCardBSchema.setSendOutCom(tLZCardSchema.getReceiveCom());// 交换发放机构和接收机构
		tLZCardBSchema.setReceiveCom(tLZCardSchema.getSendOutCom());

		tLZCardBSchema.setTakeBackNo(inLZCardSchema.getTakeBackNo());
		tLZCardBSchema.setHandler(inLZCardSchema.getHandler());
		tLZCardBSchema.setHandleDate(inLZCardSchema.getHandleDate());
		logger.debug("splitCertifyTakeBack3: (LZCardB)准备保存的单证的起始号:" + tLZCardBSchema.getStartNo());
		logger.debug("splitCertifyTakeBack3: (LZCardB)准备保存的单证的终止号:" + tLZCardBSchema.getEndNo());
		logger.debug("splitCertifyTakeBack3: (LZCardB)准备保存的单证的数量:" + tLZCardBSchema.getSumCount());

		if (inLZCardSchema.getStateFlag() == null || inLZCardSchema.getStateFlag().equals("")) {
			buildError("splitCertifyTakeBack", "没有输入单证状态!");
			return false;
		} else {
			tLZCardBSchema.setStateFlag(inLZCardSchema.getStateFlag());// 4、自动缴销,5、手工缴销,6、使用作废,7、停用作废
		}

		tLZCardBSchema.setOperateFlag("1");// 1：回收
		tLZCardBSchema.setOperator(globalInput.Operator);
		tLZCardBSchema.setMakeDate(PubFun.getCurrentDate());
		tLZCardBSchema.setMakeTime(PubFun.getCurrentTime());
		tLZCardBSchema.setModifyDate(PubFun.getCurrentDate());
		tLZCardBSchema.setModifyTime(PubFun.getCurrentTime());
		tLZCardBSchema.setAgentCom(inLZCardSchema.getAgentCom());

		vResult.set(3, tLZCardBSchema);

		// 记录单证操作轨迹,核销的单证保存于lzcardtrackb
		LZCardTrackBSchema tLZCardTrackBSchema = new LZCardTrackBSchema();
		tReflections.transFields(tLZCardTrackBSchema, tLZCardBSchema);
		logger.debug("splitCertifyTakeBack4: (LZCardTrackB)准备保存的单证轨迹的起始号:"
				+ tLZCardTrackBSchema.getStartNo());
		logger.debug("splitCertifyTakeBack4: (LZCardTrackB)准备保存的单证轨迹的终止号:"
				+ tLZCardTrackBSchema.getEndNo());
		logger.debug("splitCertifyTakeBack4: (LZCardTrackB)准备保存的单证轨迹的数量:"
				+ tLZCardTrackBSchema.getSumCount());

		vResult.set(4, tLZCardTrackBSchema);

		logger.debug("**************离开回收时的单证拆分的处理函数!******************");

		return true;
	}

	/*
	 * 单证挂失、单证作废拆分
	 * 
	 * aLZCardSchema : 单证记录,表示要回收的单证信息。 vResult : 返回的结果集:其中第一个元素是要删除的LZCard的信息，
	 * 第二和第三个元素是要插入的LZCard的信息， 第四个元素是要插入的LZCardB的信息， 第五个元素是要插入的LZCardTrackB的信息。
	 */
	protected static boolean splitCertifyLoss(GlobalInput globalInput, LZCardSchema aLZCardSchema,
			VData vResult) {
		// 清空返回结果集
		vResult.clear();
		logger.debug("**************进入单证遗失、单证作废拆分的处理函数!******************");

		// 初始化返回结果集
		vResult.add(0, null);
		vResult.add(1, null);
		vResult.add(2, null);
		vResult.add(3, null);
		vResult.add(4, null);

		String szSql = "";
		String szCertifyCode = aLZCardSchema.getCertifyCode();
		String strStartNo = aLZCardSchema.getStartNo();
		String strEndNo = aLZCardSchema.getEndNo();
		logger.debug("splitCertifyBlankOut: 传入的单证类型:" + szCertifyCode);
		logger.debug("splitCertifyBlankOut: 传入的单证起始号:" + strStartNo);
		logger.debug("splitCertifyBlankOut: 传入的单证终止号:" + strEndNo);
		logger.debug("splitCertifyBlankOut: 传入的单证状态:" + aLZCardSchema.getStateFlag());
		logger.debug("splitCertifyBlankOut: 传入的操作标志:" + aLZCardSchema.getOperateFlag());

		// 单证状态校验
		szSql = "SELECT * FROM LZCard WHERE CertifyCode = '" + "?CertifyCode?"
				+ "' AND StateFlag in ('3','8') AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '"
				+ "?StartNo?" + "' AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '" + "?StartNo?" + "'";
		logger.debug("splitCertifyBlankOut: 单证状态校验的SQL:" + szSql);
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(szSql);
		sqlbv9.put("CertifyCode", szCertifyCode);
		sqlbv9.put("StartNo", strStartNo);
		sqlbv9.put("EndNo", strEndNo);
		LZCardDB dbLZCard = new LZCardDB();
		LZCardSet set = dbLZCard.executeQuery(sqlbv9);
		if (set.size() != 1) { // 输入的单证号在可用的单证号内
			buildError("splitCertifyBlankOut", "校验单证状态失败，输入的单证号不在可用单证号的范围内!");
			return false;
		}

		dbLZCard.setSchema(set.get(1)); // 直接使用查询出来的那条数据
		String strOldStartNo = dbLZCard.getStartNo();
		String strOldEndNo = dbLZCard.getEndNo();
		String strOldStateFlag = dbLZCard.getStateFlag();
		int nNoLen = getCertifyNoLen(dbLZCard.getCertifyCode());

		logger.debug("splitCertifyBlankOut: 查询出的需要被拆分(被删除)的单证起始号:" + strOldStartNo);
		logger.debug("splitCertifyBlankOut: 查询出的需要被拆分(被删除)的单证终止号:" + strOldEndNo);
		logger.debug("splitCertifyBlankOut: 查询出的需要被拆分(被删除)的单证的定义的号码长度:" + nNoLen);
		logger.debug("splitCertifyBlankOut: 查询出的需要被拆分的单证的单证状态:" + strOldStateFlag);

		// 校验接收者(即单证所属代理人、8位机构、部门)是否属于登录机构
		if (!CertifyFunc.verifyComsTackBack(globalInput, dbLZCard.getReceiveCom())) {
			mErrors.copyAllErrors(CertifyFunc.mErrors);
			return false;
		}

		// 从LZCard表中删除原有单证，将要删除的数据放入返回结果集中
		vResult.set(0, dbLZCard.getSchema());

		// 插入第一个新单证到LZCard表中
		if (strStartNo.compareTo(strOldStartNo) > 0) {
			logger.debug("splitCertifyBlankOut: 准备插入的第一部分的单证的数据");

			dbLZCard.setStartNo(strOldStartNo);
			dbLZCard.setEndNo(bigIntegerPlus(strStartNo, "-1", nNoLen));
			dbLZCard
					.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard.getStartNo()) + 1);
			dbLZCard.setModifyDate(PubFun.getCurrentDate());
			dbLZCard.setModifyTime(PubFun.getCurrentTime());

			logger.debug("splitCertifyBlankOut: 准备插入的第一部分的单证的起始号:" + dbLZCard.getStartNo());
			logger.debug("splitCertifyBlankOut: 准备插入的第一部分的单证的终止号:" + dbLZCard.getEndNo());
			logger.debug("splitCertifyBlankOut: 准备插入的第一部分的单证的数量:" + dbLZCard.getSumCount());
			logger.debug("splitCertifyBlankOut: 准备插入的第一部分的日期(ModifyDate):" + dbLZCard.getModifyDate());
			logger.debug("splitCertifyBlankOut: 准备插入的第一部分的日期(ModifyTime):" + dbLZCard.getModifyTime());

			vResult.set(1, dbLZCard.getSchema());
		}

		// 插入第二个新单证到LZCard表中
		if (strOldEndNo.compareTo(strEndNo) > 0) {
			logger.debug("splitCertifyTakeBack: 准备插入的第二部分的单证的数据");

			dbLZCard.setStartNo(bigIntegerPlus(strEndNo, "1", nNoLen));
			dbLZCard.setEndNo(strOldEndNo);
			dbLZCard
					.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard.getStartNo()) + 1);

			logger.debug("splitCertifyBlankOut: 准备插入的第二部分的单证的起始号:" + dbLZCard.getStartNo());
			logger.debug("splitCertifyBlankOut: 准备插入的第二部分的单证的终止号:" + dbLZCard.getEndNo());
			logger.debug("splitCertifyBlankOut: 准备插入的第二部分的单证的数量:" + dbLZCard.getSumCount());

			vResult.set(2, dbLZCard.getSchema());
		}

		// 保存新的单证,核销的单证保存于lzcardb
		LZCardBSchema tLZCardBSchema = new LZCardBSchema();

		Reflections tReflections = new Reflections();
		tReflections.transFields(tLZCardBSchema, dbLZCard.getSchema());

		tLZCardBSchema.setStartNo(strStartNo);
		tLZCardBSchema.setEndNo(strEndNo);
		tLZCardBSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(tLZCardBSchema.getEndNo(), tLZCardBSchema
				.getStartNo()) + 1);
		tLZCardBSchema.setSendOutCom(dbLZCard.getSchema().getReceiveCom());// 交换发放机构和接收机构
		tLZCardBSchema.setReceiveCom(dbLZCard.getSchema().getSendOutCom());

		tLZCardBSchema.setTakeBackNo(aLZCardSchema.getTakeBackNo());
		tLZCardBSchema.setHandler(aLZCardSchema.getHandler());
		tLZCardBSchema.setHandleDate(aLZCardSchema.getHandleDate());
		tLZCardBSchema.setApplyNo(aLZCardSchema.getApplyNo());
		logger.debug("splitCertifyBlankOut: (LzcardB)准备保存新的单证的起始号:" + tLZCardBSchema.getStartNo());
		logger.debug("splitCertifyBlankOut: (LzcardB)准备保存新的单证的终止号:" + tLZCardBSchema.getEndNo());
		logger.debug("splitCertifyBlankOut: (LzcardB)准备保存新的单证的数量:" + tLZCardBSchema.getSumCount());

		if (aLZCardSchema.getStateFlag() != null && aLZCardSchema.getStateFlag().equals("")) {
			buildError("splitCertifyTakeBack", "没有输入单证状态!");
			return false;
		} else {
			tLZCardBSchema.setStateFlag(aLZCardSchema.getStateFlag());// 6、使用作废,7、停用作废
		}

		tLZCardBSchema.setOperateFlag("1");// 1：回收
		tLZCardBSchema.setOperator(globalInput.Operator);
		tLZCardBSchema.setMakeDate(PubFun.getCurrentDate());
		tLZCardBSchema.setMakeTime(PubFun.getCurrentTime());
		tLZCardBSchema.setModifyDate(PubFun.getCurrentDate());
		tLZCardBSchema.setModifyTime(PubFun.getCurrentTime());
		tLZCardBSchema.setAgentCom(aLZCardSchema.getAgentCom());

		vResult.set(3, tLZCardBSchema);

		// 记录单证操作轨迹,核销的单证保存于lzcardtrackb
		LZCardTrackBSchema tLZCardTrackBSchema = new LZCardTrackBSchema();
		tReflections.transFields(tLZCardTrackBSchema, tLZCardBSchema);
		logger.debug("splitCertifyBlankOut: (LzcardTrackB)准备保存新的单证的起始号:"
				+ tLZCardTrackBSchema.getStartNo());
		logger.debug("splitCertifyBlankOut: (LzcardTrackB)准备保存新的单证的终止号:"
				+ tLZCardTrackBSchema.getEndNo());
		logger.debug("splitCertifyBlankOut: (LzcardTrackB)准备保存新的单证的数量:"
				+ tLZCardTrackBSchema.getSumCount());

		vResult.set(4, tLZCardTrackBSchema);

		logger.debug("**************离开单证遗失、单证作废拆分的处理函数!******************");

		return true;
	}

	/*
	 * 单证销毁提交
	 * 
	 * aLZCardSchema : 单证记录,表示要回收的单证信息。 vResult : 返回的结果集:其中第一个元素是要删除的LZCardB的信息，
	 * 第二和第三个元素是要插入的LZCardB的信息， 第四个元素是要插入的LZCardB的信息， 第五个元素是要插入的LZCardTrackB的信息。
	 */
	protected static boolean splitCertifyDestroy(GlobalInput globalInput, LZCardBSchema aLZCardBSchema,
			VData vResult) {
		// 清空返回结果集
		vResult.clear();
		logger.debug("**************进入单证销毁提交拆分的处理函数!******************");

		// 初始化返回结果集
		vResult.add(0, null);
		vResult.add(1, null);
		vResult.add(2, null);
		vResult.add(3, null);
		vResult.add(4, null);

		String szSql = "";
		String szCertifyCode = aLZCardBSchema.getCertifyCode();
		String strStartNo = aLZCardBSchema.getStartNo();
		String strEndNo = aLZCardBSchema.getEndNo();
		logger.debug("splitCertifyDestroyApply: 传入的单证类型:" + szCertifyCode);
		logger.debug("splitCertifyDestroyApply: 传入的单证起始号:" + strStartNo);
		logger.debug("splitCertifyDestroyApply: 传入的单证终止号:" + strEndNo);
		logger.debug("splitCertifyDestroyApply: 传入的单证状态:" + aLZCardBSchema.getStateFlag());
		logger.debug("splitCertifyDestroyApply: 传入的操作标志:" + aLZCardBSchema.getOperateFlag());

		// 单证状态校验
		szSql = "SELECT * FROM LZCardB WHERE CertifyCode = '" + "?CertifyCode?"
				+ "' AND StateFlag in ('6','7') AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '"
				+ "?EndNo?" + "' ";
		logger.debug("splitCertifyBlankOut: 单证状态校验的SQL:" + szSql);
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(szSql);
		sqlbv10.put("CertifyCode", szCertifyCode);
		sqlbv10.put("StartNo", strStartNo);
		sqlbv10.put("EndNo", strEndNo);
		LZCardBDB dbLZCardB = new LZCardBDB();
		LZCardBSet set = dbLZCardB.executeQuery(sqlbv10);
		if (set.size() != 1) { // 输入的单证号在可用的单证号内
			buildError("splitCertifyDestroyApply", "校验单证状态失败，输入的单证号不在可用单证号的范围内!");
			return false;
		}

		dbLZCardB.setSchema(set.get(1)); // 直接使用查询出来的那条数据
		String strOldStartNo = dbLZCardB.getStartNo();
		String strOldEndNo = dbLZCardB.getEndNo();
		String strOldStateFlag = dbLZCardB.getStateFlag();
		int nNoLen = getCertifyNoLen(dbLZCardB.getCertifyCode());

		logger.debug("splitCertifyDestroyApply: 查询出的需要被拆分(被删除)的单证起始号:" + strOldStartNo);
		logger.debug("splitCertifyDestroyApply: 查询出的需要被拆分(被删除)的单证终止号:" + strOldEndNo);
		logger.debug("splitCertifyDestroyApply: 查询出的需要被拆分的单证的单证状态:" + strOldStateFlag);
		logger.debug("splitCertifyDestroyApply: 查询出的需要被拆分(被删除)的单证的定义的号码长度:" + nNoLen);

		// 校验接收者(即单证所属代理人、8位机构、部门)是否属于登录机构
		// 此处特殊处理，因为作废的时候单证已经回到上级机构，但是又允许下级机构对已作废的胆子做销毁，所以第二个参数为发放者,即作废处理的机构
		if (!CertifyFunc.verifyComsTackBack(globalInput, dbLZCardB.getSendOutCom())) {
			mErrors.copyAllErrors(CertifyFunc.mErrors);
			return false;
		}

		// 从LZCard表中删除原有单证，将要删除的数据放入返回结果集中
		vResult.set(0, dbLZCardB.getSchema());

		// 插入第一个新单证到LZCard表中
		if (strStartNo.compareTo(strOldStartNo) > 0) {
			logger.debug("splitCertifyDestroyApply: 准备插入的第一部分的单证的数据");

			dbLZCardB.setStartNo(strOldStartNo);
			dbLZCardB.setEndNo(bigIntegerPlus(strStartNo, "-1", nNoLen));
			dbLZCardB.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCardB.getEndNo(), dbLZCardB
					.getStartNo()) + 1);
			dbLZCardB.setModifyDate(PubFun.getCurrentDate());
			dbLZCardB.setModifyTime(PubFun.getCurrentTime());

			logger.debug("splitCertifyDestroyApply: 准备插入的第一部分的单证的起始号:" + dbLZCardB.getStartNo());
			logger.debug("splitCertifyDestroyApply: 准备插入的第一部分的单证的终止号:" + dbLZCardB.getEndNo());
			logger.debug("splitCertifyDestroyApply: 准备插入的第一部分的单证的数量:" + dbLZCardB.getSumCount());
			logger.debug("splitCertifyDestroyApply: 准备插入的第一部分的日期(ModifyDate):"
					+ dbLZCardB.getModifyDate());
			logger.debug("splitCertifyDestroyApply: 准备插入的第一部分的日期(ModifyTime):"
					+ dbLZCardB.getModifyTime());

			vResult.set(1, dbLZCardB.getSchema());
		}

		// 插入第二个新单证到LZCard表中
		if (strOldEndNo.compareTo(strEndNo) > 0) {
			logger.debug("splitCertifyTakeBack: 准备插入的第二部分的单证的数据");

			dbLZCardB.setStartNo(bigIntegerPlus(strEndNo, "1", nNoLen));
			dbLZCardB.setEndNo(strOldEndNo);
			dbLZCardB.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCardB.getEndNo(), dbLZCardB
					.getStartNo()) + 1);

			logger.debug("splitCertifyDestroyApply: 准备插入的第二部分的单证的起始号:" + dbLZCardB.getStartNo());
			logger.debug("splitCertifyDestroyApply: 准备插入的第二部分的单证的终止号:" + dbLZCardB.getEndNo());
			logger.debug("splitCertifyDestroyApply: 准备插入的第二部分的单证的数量:" + dbLZCardB.getSumCount());

			vResult.set(2, dbLZCardB.getSchema());
		}

		// 保存新的单证,核销的单证保存于lzcardb
		LZCardBSchema tLZCardBSchema = new LZCardBSchema();
		tLZCardBSchema.setSchema(dbLZCardB.getSchema());
		tLZCardBSchema.setStartNo(aLZCardBSchema.getStartNo());
		tLZCardBSchema.setEndNo(aLZCardBSchema.getEndNo());
		tLZCardBSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(tLZCardBSchema.getEndNo(), tLZCardBSchema
				.getStartNo()) + 1);
		tLZCardBSchema.setStateFlag("11");
		tLZCardBSchema.setTakeBackNo(aLZCardBSchema.getTakeBackNo());
		tLZCardBSchema.setHandler(aLZCardBSchema.getHandler());
		tLZCardBSchema.setHandleDate(aLZCardBSchema.getHandleDate());
		tLZCardBSchema.setApplyNo(aLZCardBSchema.getApplyNo());
		tLZCardBSchema.setOperateFlag("1");// 1：回收
		tLZCardBSchema.setOperator(globalInput.Operator);
		tLZCardBSchema.setMakeDate(PubFun.getCurrentDate());
		tLZCardBSchema.setMakeTime(PubFun.getCurrentTime());
		tLZCardBSchema.setModifyDate(PubFun.getCurrentDate());
		tLZCardBSchema.setModifyTime(PubFun.getCurrentTime());
		tLZCardBSchema.setAgentCom(aLZCardBSchema.getAgentCom());
		logger.debug("splitCertifyDestroyApply: (LzcardB)准备保存新的单证的起始号:" + tLZCardBSchema.getStartNo());
		logger.debug("splitCertifyDestroyApply: (LzcardB)准备保存新的单证的终止号:" + tLZCardBSchema.getEndNo());
		logger.debug("splitCertifyDestroyApply: (LzcardB)准备保存新的单证的数量:" + tLZCardBSchema.getSumCount());

		vResult.set(3, tLZCardBSchema);

		// 记录单证操作轨迹,核销的单证保存于lzcardtrackb
		LZCardTrackBSchema tLZCardTrackBSchema = new LZCardTrackBSchema();
		Reflections tReflections = new Reflections();
		tReflections.transFields(tLZCardTrackBSchema, tLZCardBSchema);
		logger.debug("splitCertifyDestroyApply: (LzcardTrackB)准备保存新的单证的起始号:"
				+ tLZCardTrackBSchema.getStartNo());
		logger.debug("splitCertifyDestroyApply: (LzcardTrackB)准备保存新的单证的终止号:"
				+ tLZCardTrackBSchema.getEndNo());
		logger.debug("splitCertifyDestroyApply: (LzcardTrackB)准备保存新的单证的数量:"
				+ tLZCardTrackBSchema.getSumCount());

		vResult.set(4, tLZCardTrackBSchema);

		logger.debug("**************离开单证遗失、单证作废拆分的处理函数!******************");

		return true;
	}

	/*
	 * Kevin 2003-04-14 拆分单证（反发放） 程序逻辑和单证回收的类似 golbalInput : 全局空间 aLZCardSchema :
	 * 单证记录。表示要回收的单证信息。 vResult : 返回的结果集。其中第一个元素是要删除的LZCard的信息，
	 * 第二和第三个元素是要插入的LZCard的信息， 第四个元素是要插入的LZCard的信息， 第五个元素是要插入的LZCardTrack的信息。
	 */
	protected static boolean splitCertReveSendOut(GlobalInput globalInput, LZCardSchema aLZCardSchema,
			VData vResult) {
		logger.debug("**************进入回退调拨时的单证拆分的处理函数!**************");

		// 初始化返回结果集
		mErrors.clearErrors();
		vResult.clear();
		vResult.add(0, null);
		vResult.add(1, null);
		vResult.add(2, null);
		vResult.add(3, null);
		vResult.add(4, null);

		String szSql = "";
		String szCertifyCode = aLZCardSchema.getCertifyCode();
		String szReceiveCom = aLZCardSchema.getReceiveCom();
		String szSendOutCom = aLZCardSchema.getSendOutCom();
		String strStartNo = aLZCardSchema.getStartNo();
		String strEndNo = aLZCardSchema.getEndNo();

		logger.debug("splitCertReveSendOut1: 传入的需要回退调拨的单证类型:" + szCertifyCode);
		logger.debug("splitCertReveSendOut1: 传入的需要回退调拨的单证起始号:" + strStartNo);
		logger.debug("splitCertReveSendOut1: 传入的需要回退调拨的单证终止号:" + strEndNo);
		logger.debug("splitCertReveSendOut1: 传入的需要回退调拨的单证发放机构:" + szSendOutCom);
		logger.debug("splitCertReveSendOut1: 传入的需要回退调拨的单证终止机构:" + szReceiveCom);
		logger.debug("splitCertReveSendOut1: 传入的需要回退调拨的单证状态:" + aLZCardSchema.getStateFlag());
		logger.debug("splitCertReveSendOut1: 传入的需要回退调拨的操作标志:" + aLZCardSchema.getOperateFlag());

		// 单证状态校验,单证处于发放状态，并且号码处于可用的范围。
		szSql = "SELECT * FROM LZCard WHERE CertifyCode = '" + "?CertifyCode?"
				+ "' AND StateFlag in ('2','3') AND ReceiveCom = '" + "?ReceiveCom?" + "' AND StartNo <= '"
				+ "?StartNo?" + "' AND EndNo >= '" + "?StartNo?" + "' AND StartNo <= '" + "?EndNo?"
				+ "' AND EndNo >= '" + "?EndNo?" + "'";
		logger.debug("splitCertReveSendOut3: 单证状态校验的SQL:" + szSql);
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql();
		sqlbv11.put("CertifyCode", szCertifyCode);
		sqlbv11.put("ReceiveCom", szSendOutCom);
		sqlbv11.put("StartNo", strStartNo);
		sqlbv11.put("EndNo", strEndNo);
		LZCardDB dbLZCard = new LZCardDB();
		LZCardSet set = dbLZCard.executeQuery(sqlbv11);
		
		if (set.size() != 1) { // 输入的单证号在可用的单证号内
			buildError("splitCertReveSendOut", "单证校验失败，请检查：1、输入的单证其状态是否为已入库或已发放未核销；2、输入的发放者和接收者是否正确!");
			return false;
		}
		

		/***********************************************************************
		 * * * * * * * * * * * * * * * 开始拆分单证 * * * * * * * * * * * * * *
		 */
		dbLZCard.setSchema(set.get(1)); // 直接使用查询出来的那条数据
		String strOldStartNo = dbLZCard.getStartNo();
		String strOldEndNo = dbLZCard.getEndNo();
		int nNoLen = getCertifyNoLen(dbLZCard.getCertifyCode());

		logger.debug("splitCertReveSendOut4: 查询出的需要被拆分(被删除)的单证起始号:" + strOldStartNo);
		logger.debug("splitCertReveSendOut4: 查询出的需要被拆分(被删除)的单证终止号:" + strOldEndNo);
		logger.debug("splitCertReveSendOut4: 查询出的需要被拆分(被删除)的单证的定义的号码长度:" + nNoLen);

		// 从LZCard表中删除原有单证，将要删除的数据放入返回结果集中
		vResult.set(0, dbLZCard.getSchema());

		// 插入第一个新单证到LZCard表中
		if (strStartNo.compareTo(strOldStartNo) > 0) {
			logger.debug("splitCertReveSendOut: 准备插入的第一部分的单证的数据");
			dbLZCard.setStartNo(strOldStartNo);
			dbLZCard.setEndNo(bigIntegerPlus(strStartNo, "-1", nNoLen));
			dbLZCard
					.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard.getStartNo()) + 1);
			dbLZCard.setModifyDate(PubFun.getCurrentDate());
			dbLZCard.setModifyTime(PubFun.getCurrentTime());

			logger.debug("splitCertReveSendOut5: 准备插入的第一部分的单证的起始号:" + dbLZCard.getStartNo());
			logger.debug("splitCertReveSendOut5: 准备插入的第一部分的单证的终止号:" + dbLZCard.getEndNo());
			logger.debug("splitCertReveSendOut5: 准备插入的第一部分的单证的数量:" + dbLZCard.getSumCount());
			logger.debug("splitCertReveSendOut5: 准备插入的第一部分的修改日期:" + dbLZCard.getModifyDate());
			logger.debug("splitCertReveSendOut5: 准备插入的第一部分的修改时间:" + dbLZCard.getModifyTime());

			vResult.set(1, dbLZCard.getSchema());
		}

		// 插入第二个新单证到LZCard表中
		if (strOldEndNo.compareTo(strEndNo) > 0) {
			logger.debug("splitCertReveSendOut: 准备插入的第二部分的单证的数据");
			dbLZCard.setStartNo(bigIntegerPlus(strEndNo, "1", nNoLen));
			dbLZCard.setEndNo(strOldEndNo);
			dbLZCard.setSumCount((int) CertifyFunc.bigIntegerDiff(strOldEndNo, dbLZCard.getStartNo()) + 1);

			logger.debug("splitCertReveSendOut6: 准备插入的第二部分的单证的起始号:" + dbLZCard.getStartNo());
			logger.debug("splitCertReveSendOut6: 准备插入的第二部分的单证的终止号:" + dbLZCard.getEndNo());
			logger.debug("splitCertReveSendOut6: 准备插入的第二部分的单证的数量:" + dbLZCard.getSumCount());

			vResult.set(2, dbLZCard.getSchema());
		}

		/***********************************************************************
		 * * * * * * * * * * * * * * * 拆分单证结束 * * * * * * * * * * * * * *
		 */

		// 保存新的单证
		dbLZCard.setCertifyCode(aLZCardSchema.getCertifyCode());
		dbLZCard.setStartNo(aLZCardSchema.getStartNo());
		dbLZCard.setEndNo(aLZCardSchema.getEndNo());
		dbLZCard.setSendOutCom(aLZCardSchema.getSendOutCom());
		dbLZCard.setReceiveCom(aLZCardSchema.getReceiveCom());
		dbLZCard.setReason(aLZCardSchema.getReason());
		dbLZCard
				.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCard.getEndNo(), dbLZCard.getStartNo()) + 1);
		dbLZCard.setHandler(aLZCardSchema.getHandler());
		dbLZCard.setHandleDate(aLZCardSchema.getHandleDate());
		dbLZCard.setTakeBackNo(aLZCardSchema.getTakeBackNo());

		logger.debug("splitCertReveSendOut7: (Lzcard)准备插入的保存新的单证的起始号:" + dbLZCard.getStartNo());
		logger.debug("splitCertReveSendOut7: (Lzcard)准备插入的保存新的单证的终止号:" + dbLZCard.getEndNo());
		logger.debug("splitCertReveSendOut7: (Lzcard)准备插入的保存新的单证的数量:" + dbLZCard.getSumCount());

		// 置状态标志和操作标志
		if (aLZCardSchema.getReceiveCom().length() == 9 || aLZCardSchema.getReceiveCom().charAt(0) == 'B') {
			dbLZCard.setStateFlag("3");// 3、已发放未核销 发放至B(部门)与D(代理人)下的未核销单证。
		} else {
			dbLZCard.setStateFlag("1");// 1、待入库 处于待入库池中，需要进行入库确认。
		}
		dbLZCard.setOperateFlag("2");// 2：反发放
		dbLZCard.setOperator(globalInput.Operator);
		dbLZCard.setMakeDate(PubFun.getCurrentDate());
		dbLZCard.setMakeTime(PubFun.getCurrentTime());
		dbLZCard.setModifyDate(PubFun.getCurrentDate());
		dbLZCard.setModifyTime(PubFun.getCurrentTime());
		dbLZCard.setAgentCom(aLZCardSchema.getAgentCom());
		
		vResult.set(3, dbLZCard.getSchema());

		// 记录单证操作轨迹
		LZCardTrackDB dbLZCardTrack = new LZCardTrackDB();
		dbLZCardTrack.setCertifyCode(aLZCardSchema.getCertifyCode());
		dbLZCardTrack.setSubCode(dbLZCard.getSubCode());
		dbLZCardTrack.setRiskCode(dbLZCard.getRiskCode());
		dbLZCardTrack.setRiskVersion(dbLZCard.getRiskVersion());
		dbLZCardTrack.setStartNo(aLZCardSchema.getStartNo());
		dbLZCardTrack.setEndNo(aLZCardSchema.getEndNo());
		dbLZCardTrack.setSendOutCom(aLZCardSchema.getSendOutCom());
		dbLZCardTrack.setReceiveCom(aLZCardSchema.getReceiveCom());
		dbLZCardTrack.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCardTrack.getEndNo(), dbLZCardTrack
				.getStartNo()) + 1);
		dbLZCardTrack.setInvaliDate(dbLZCard.getInvaliDate());
		dbLZCardTrack.setPrem("");
		dbLZCardTrack.setAmnt(aLZCardSchema.getAmnt());
		dbLZCardTrack.setHandler(aLZCardSchema.getHandler());
		dbLZCardTrack.setHandleDate(aLZCardSchema.getHandleDate());
		dbLZCardTrack.setTakeBackNo(dbLZCard.getTakeBackNo());
		dbLZCardTrack.setSaleChnl(aLZCardSchema.getSaleChnl());

		dbLZCardTrack.setStateFlag(dbLZCard.getStateFlag());// 置状态标志
		dbLZCardTrack.setOperateFlag("2");// 置操作标志

		dbLZCardTrack.setReason(dbLZCard.getReason());
		dbLZCardTrack.setState("");
		dbLZCardTrack.setOperator(globalInput.Operator);
		dbLZCardTrack.setMakeDate(PubFun.getCurrentDate());
		dbLZCardTrack.setMakeTime(PubFun.getCurrentTime());
		dbLZCardTrack.setModifyDate(PubFun.getCurrentDate());
		dbLZCardTrack.setModifyTime(PubFun.getCurrentTime());
		dbLZCardTrack.setAgentCom(aLZCardSchema.getAgentCom());

		logger.debug("splitCertReveSendOut8: 准备保存新的单证轨迹的起始号:" + dbLZCardTrack.getStartNo());
		logger.debug("splitCertReveSendOut8: 准备保存新的单证轨迹的终止号:" + dbLZCardTrack.getEndNo());
		logger.debug("splitCertReveSendOut8: 准备保存新的单证轨迹的数量:" + dbLZCardTrack.getSumCount());

		vResult.set(4, dbLZCardTrack.getSchema());

		logger.debug("**************离开回退调拨时的单证拆分的处理函数!**************");
		return true;
	}

	/*
	 * Kevin 2003-05-16 拆分单证（核销修订） 回收回退单证 golbalInput : 全局空间 aLZCardSchema :
	 * 单证记录。表示要回收的单证信息。 vResult : 返回的结果集。其中第一个元素是要删除的LZCardB的信息，
	 * 第二和第三个元素是要插入的LZCardB的信息， 第四个元素是要插入的LZCard的信息， 第五个元素是要插入的LZCardTrack的信息。
	 */
	public static boolean splitCertReveTakeBack(GlobalInput globalInput, LZCardBSchema aLZCardBSchema,
			VData vResult) {
		logger.debug("**************进入核销修订时的单证拆分的处理函数!******************");
		// 清空静态错误
		mErrors.clearErrors();

		// 初始化返回结果集
		vResult.clear();
		vResult.add(0, null);
		vResult.add(1, null);
		vResult.add(2, null);
		vResult.add(3, null);
		vResult.add(4, null);

		String szSql = "";
		String szCertifyCode = aLZCardBSchema.getCertifyCode();
		String strStartNo = aLZCardBSchema.getStartNo();
		String strEndNo = aLZCardBSchema.getEndNo();
		logger.debug("splitCertReveTakeBack1: 传入的需要回收修订的单证类型:" + szCertifyCode);
		logger.debug("splitCertReveTakeBack1: 传入的需要回收修订的单证起始号:" + strStartNo);
		logger.debug("splitCertReveTakeBack1: 传入的需要回收修订的单证终止号:" + strEndNo);
		logger.debug("splitCertReveTakeBack1: 传入的需要回收修订的单证状态:" + aLZCardBSchema.getStateFlag());
		logger.debug("splitCertReveTakeBack1: 传入的需要回收修订的操作标志:" + aLZCardBSchema.getOperateFlag());

		// 单证状态校验
		szSql = "SELECT * FROM LZCardB WHERE CertifyCode = '" + "?CertifyCode?"
				+ "' AND StateFlag in ('4','5','6','7') AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '"
				+ "?EndNo?" + "'";
		logger.debug("splitCertReveTakeBack3: 单证状态校验的SQL:" + szSql);
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(szSql);
		sqlbv12.put("CertifyCode", szCertifyCode);
		sqlbv12.put("StartNo", strStartNo);
		sqlbv12.put("EndNo", strEndNo);
		LZCardBDB dbLZCardB = new LZCardBDB();
		LZCardBSet set = dbLZCardB.executeQuery(sqlbv12);
		if (set.size() != 1) { // 输入的单证号在可用的单证号内
			buildError("splitCertReveTakeBack", "校验单证状态失败，输入的单证号不在可用单证号的范围内!");
			return false;
		}

		// 开始拆分单证
		dbLZCardB.setSchema(set.get(1)); // 直接使用查询出来的那条数据
		String strOldStartNo = dbLZCardB.getStartNo();
		String strOldEndNo = dbLZCardB.getEndNo();
		int nNoLen = getCertifyNoLen(dbLZCardB.getCertifyCode());
		logger.debug("splitCertReveTakeBack4: 查询出的需要被删除的单证起始号:" + strOldStartNo);
		logger.debug("splitCertReveTakeBack4: 查询出的需要被删除的单证终止号:" + strOldEndNo);
		logger.debug("splitCertReveTakeBack4: 查询出的需要被删除的单证的定义的号码长度:" + nNoLen);

		// 从LZCard表中删除原有单证，将要删除的数据放入返回结果集中
		vResult.set(0, dbLZCardB.getSchema());

		// 插入第一个新单证到LZCard表中
		if (strStartNo.compareTo(strOldStartNo) > 0) {
			dbLZCardB.setStartNo(strOldStartNo);
			dbLZCardB.setEndNo(bigIntegerPlus(strStartNo, "-1", nNoLen));
			dbLZCardB.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCardB.getEndNo(), dbLZCardB
					.getStartNo()) + 1);
			dbLZCardB.setModifyDate(PubFun.getCurrentDate());
			dbLZCardB.setModifyTime(PubFun.getCurrentTime());

			logger.debug("splitCertReveTakeBack5: LZCardB准备插入的第一部分的单证的起始号:" + dbLZCardB.getStartNo());
			logger.debug("splitCertReveTakeBack5: LZCardB准备插入的第一部分的单证的终止号:" + dbLZCardB.getEndNo());
			logger.debug("splitCertReveTakeBack5: LZCardB准备插入的第一部分的单证的数量:" + dbLZCardB.getSumCount());
			logger.debug("splitCertReveTakeBack5: LZCardB准备插入的第一部分的日期:" + dbLZCardB.getModifyDate());
			logger.debug("splitCertReveTakeBack5: LZCardB准备插入的第一部分的时间:" + dbLZCardB.getModifyTime());

			vResult.set(1, dbLZCardB.getSchema());
		}

		// 插入第二个新单证到LZCard表中
		if (strOldEndNo.compareTo(strEndNo) > 0) {
			dbLZCardB.setStartNo(bigIntegerPlus(strEndNo, "1", nNoLen));
			dbLZCardB.setEndNo(strOldEndNo);
			dbLZCardB.setSumCount((int) CertifyFunc.bigIntegerDiff(dbLZCardB.getEndNo(), dbLZCardB
					.getStartNo()) + 1);

			logger.debug("splitCertReveTakeBack6: LZCardB准备插入的第二部分的单证的起始号:" + dbLZCardB.getStartNo());
			logger.debug("splitCertReveTakeBack6: LZCardB准备插入的第二部分的单证的终止号:" + dbLZCardB.getEndNo());
			logger.debug("splitCertReveTakeBack6: LZCardB准备插入的第二部分的单证的数量:" + dbLZCardB.getSumCount());

			vResult.set(2, dbLZCardB.getSchema());
		}

		// 保存新的单证
		Reflections tReflections = new Reflections();
		LZCardSchema tLZCardSchema = new LZCardSchema();
		tReflections.transFields(tLZCardSchema, dbLZCardB.getSchema());
		tLZCardSchema.setStartNo(aLZCardBSchema.getStartNo());// 核销回退的起止号码
		tLZCardSchema.setEndNo(aLZCardBSchema.getEndNo());
		tLZCardSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(tLZCardSchema.getEndNo(), tLZCardSchema
				.getStartNo()) + 1);
		String tSendOutCom = tLZCardSchema.getSendOutCom();// 回退后发放接收机构互换
		tLZCardSchema.setSendOutCom(tLZCardSchema.getReceiveCom());
		tLZCardSchema.setReceiveCom(tSendOutCom);
		tLZCardSchema.setHandler(globalInput.Operator);
		tLZCardSchema.setHandleDate(PubFun.getCurrentDate());
		tLZCardSchema.setOperator(globalInput.Operator);
		tLZCardSchema.setMakeDate(PubFun.getCurrentDate());
		tLZCardSchema.setMakeTime(PubFun.getCurrentTime());
		tLZCardSchema.setModifyDate(PubFun.getCurrentDate());
		tLZCardSchema.setModifyTime(PubFun.getCurrentTime());
		tLZCardSchema.setTakeBackNo(aLZCardBSchema.getTakeBackNo());
		tLZCardSchema.setOperateFlag("3");// 反回收
		tLZCardSchema.setAgentCom(tLZCardSchema.getAgentCom());

		// 逾期:tLZCardSchema.getInvaliDate()>PubFun.getCurrentDate()
		if (PubFun.calInterval(tLZCardSchema.getInvaliDate(), PubFun.getCurrentDate(), "D") > 0) {// 逾期
			tLZCardSchema.setStateFlag("8");// 8、逾期
		} else if (tLZCardSchema.getReceiveCom().charAt(0) == 'D'
				|| tLZCardSchema.getReceiveCom().charAt(0) == 'B'
				|| tLZCardSchema.getReceiveCom().length() == 9) {
			tLZCardSchema.setStateFlag("3");// 3、已发放未核销
		} else {
			tLZCardSchema.setStateFlag("1");// 1、待入库
		}
		logger.debug("splitCertReveTakeBack7: (Lzcard)准备插入的保存新的单证的起始号:" + tLZCardSchema.getStartNo());
		logger.debug("splitCertReveTakeBack7: (Lzcard)准备插入的保存新的单证的终止号:" + tLZCardSchema.getEndNo());
		logger.debug("splitCertReveTakeBack7: (Lzcard)准备插入的保存新的单证的数量:" + tLZCardSchema.getSumCount());

		vResult.set(3, tLZCardSchema);

		// 记录单证操作轨迹
		LZCardTrackSchema tLZCardTrackSchema = new LZCardTrackSchema();
		tReflections.transFields(tLZCardTrackSchema, tLZCardSchema);

		logger.debug("splitCertReveTakeBack8: (LzcardTrack)准备插入的保存新的单证的起始号:"
				+ tLZCardTrackSchema.getStartNo());
		logger.debug("splitCertReveTakeBack8: (LzcardTrack)准备插入的保存新的单证的终止号:"
				+ tLZCardTrackSchema.getEndNo());
		logger.debug("splitCertReveTakeBack8: (LzcardTrack)准备插入的保存新的单证的数量:"
				+ tLZCardTrackSchema.getSumCount());

		vResult.set(4, tLZCardTrackSchema);

		logger.debug("**************核销修订时的单证拆分的处理函数完毕!******************");

		return true;
	}

	/**
	 * 用来处理外部的单证回收的请求
	 * 
	 * @return
	 */
	protected static boolean handleTakeBack(LZCardSchema aLZCardSchema, VData vOneResult) {

		mErrors.clearErrors();

		logger.debug("*******************开始处理外部的单证回收的请求********************");

		// 清除返回结果集
		vOneResult.clear();

		logger.debug("handleTakeBack1: 传入的需要回收修订的单证类型:" + aLZCardSchema.getCertifyCode());
		logger.debug("handleTakeBack1: 传入的需要回收修订的单证起始号:" + aLZCardSchema.getStartNo());
		logger.debug("handleTakeBack1: 传入的需要回收修订的单证终止号:" + aLZCardSchema.getEndNo());
		logger.debug("handleTakeBack1: 传入的需要回收修订的单证状态:" + aLZCardSchema.getStateFlag());
		logger.debug("handleTakeBack1: 传入的需要回收修订的操作标志:" + aLZCardSchema.getOperateFlag());

		// 根据传入的单证号码和单证类型从数据库中取出发放者和接收者
		String strSQL = "SELECT * FROM LZCard WHERE CertifyCode = '" + "?CertifyCode?"
				+ "' AND StartNo = '" + "?getStartNo?" + "' AND EndNo = '"
				+ "?getStartNo?" + "' AND StateFlag = '3'";
		logger.debug("handleTakeBack2: 根据传入的单证号码和单证类型从数据库中取出发放者和接收者的SQL:" + strSQL);
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(strSQL);
		sqlbv13.put("CertifyCode", aLZCardSchema.getCertifyCode());
		sqlbv13.put("getStartNo", aLZCardSchema.getStartNo());
		LZCardSet tLZCardSet = new LZCardDB().executeQuery(sqlbv13);
		if (tLZCardSet == null || tLZCardSet.size() < 1) {
			buildError("handleTakeBack", "找不到要回收的单证");
			return false;
		}
		LZCardSchema tLZCardSchema = tLZCardSet.get(1);

		// 对传入的数据做一些初始化的操作
		aLZCardSchema.setHandler(tLZCardSchema.getOperator());
		aLZCardSchema.setHandleDate(PubFun.getCurrentDate());
		aLZCardSchema.setStateFlag("4"); // 4、自动缴销,由系统在交费时自动实现。

		// 取得回收操作的发放者和接收者
		String strReceiveCom = aLZCardSchema.getReceiveCom();

		// 产生回收清算单号
		String strNoLimit = PubFun.getNoLimit(strReceiveCom.substring(1));
		String strTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", strNoLimit);
		logger.debug("handleTakeBack3: 产生回收清算单号:" + strTakeBackNo);
		aLZCardSchema.setTakeBackNo(strTakeBackNo);

		// 构造需要的GlobalInput对象
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ComCode = strReceiveCom.substring(1);
		tGlobalInput.Operator = tLZCardSchema.getOperator();

		logger.debug("handleTakeBack5: 开始调用splitCertifyTakeBack进行单证拆分");

		// 回收时拆分单证
		if (!splitCertifyTakeBack(tGlobalInput, aLZCardSchema, vOneResult)) {
			return false;
		}

		logger.debug("handleTakeBack6: 调用splitCertifyTakeBack进行单证拆分结束!");

		logger.debug("*******************处理外部的单证回收的请求结束********************");

		return true;
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private static void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "CertifyFunc";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;

		logger.debug("In CertifyFunc buildError() : " + szErrMsg);
		mErrors.addOneError(cError);
	}

	/*
	 * kevin, 2002-09-25 输入： SendOutCom 发放机构 AgentCode 代理人 功能：
	 * 查询LAAgent表和LZAccess表，看发放机构是否有对该代理人进行单证发放的权限
	 */
	private static boolean verifyAgentAccess(String szSendOutCom, String szAgentCode) {
		String szCom = "";
		String szSql = "SELECT * FROM LZAccess WHERE SendOutCom = '";
		szSql += "?SendOutCom?";
		szSql += "' AND ReceiveCom LIKE 'D%'";
		logger.debug(szSql);
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(szSql);
		sqlbv14.put("SendOutCom", szSendOutCom);
		LZAccessSet setLZAccess = new LZAccessDB().executeQuery(sqlbv14);

		for (int nIndex = 1; nIndex <= setLZAccess.size(); nIndex++) {
			szCom = szSendOutCom.substring(1);
			if (setLZAccess.get(nIndex).getReceiveCom().trim().length() > 2) {
				szCom += setLZAccess.get(nIndex).getReceiveCom().trim().substring(1);
			}

			szSql = "SELECT * FROM LAAgent WHERE AgentCode = '";
			szSql += "?AgentCode?";
			szSql += "' AND AgentState" + VALID_AGENT_STATE + " AND ManageCom LIKE concat('?szCom?','%')";
			logger.debug(szSql);
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(szSql);
			sqlbv15.put("AgentCode", szAgentCode);
			sqlbv15.put("ManageCom", szCom);
			
			LAAgentSet setLAAgent = new LAAgentDB().executeQuery(sqlbv15);
			if (setLAAgent.size() == 1) {
				return true; // pass
			}
		}

		buildError("verifyAgentAccess", "该发放机构没有往该代理人发放单证的权限");
		return false;
	}

	/*
	 * kevin, 2002-09-25 输入： SendOutCom 发放机构 ReceiveCom 接收机构 功能：
	 * 查询LZAccess表，看发放机构是否有对该接收机构进行单证发放的权限
	 */
	private static boolean verifyAccess(String szSendOutCom, String szReceiveCom) {
		String szSql = "SELECT * FROM LZAccess WHERE SendOutCom = '";
		szSql += "?SendOutCom?";
		szSql += "' AND ReceiveCom = '";
		szSql += "?ReceiveCom?";
		szSql += "'";
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql(szSql);
		sqlbv16.put("SendOutCom", szSendOutCom);
		sqlbv16.put("ReceiveCom", szReceiveCom);
		LZAccessSet setLZAccess = new LZAccessDB().executeQuery(sqlbv16);
		if (setLZAccess.size() == 1) {
			return true; // pass
		} else {
			buildError("verifyAccess", "该发放机构没有往该接收机构发放单证的权限");
			return false;
		}
	}
	
	
	/*
	 * kevin, 2002-09-25 输入： SendOutCom 发放机构 ReceiveCom 代理人 功能：
	 * 发放机构是否有对该代理进行单证发放的权限， 在此函数中将判断发放机构是不是最末一级的机构
	 * 不校验代理人是否有效,作废、遗失与回退
	 */
	private static boolean verifyAgent2(String szSendOutCom, String szReceiveCom) {
		
		/**
		 * 2009-6-19 zhangzheng
		 * 联办和中介的校验权限只限制到分公司一级
		 */
		// 看代理人是否属于这个机构
//		 String szSql = "select * from laagent " + "where agentcode = '" +
//		 szReceiveCom.substring(1)+"'"
////		 + "' and agentstate "
////		 + VALID_AGENT_STATE
//		 + " and branchtype in('6','7') and managecom like '" +
//		 szSendOutCom.substring(1,5) + "%' " // 
//		 + "union all " 
//		 + "select * from laagent " + "where agentcode = '" +
//		 szReceiveCom.substring(1)+"'"
//		 //+ "' and agentstate " + VALID_AGENT_STATE
//		 + " and branchtype not in('6','7') and managecom like '" +
//		 szSendOutCom.substring(1,7) + "%' ";
//		 
////		String szSql = "select * from laagent where agentcode = '" + szReceiveCom.substring(1)
////				+ "' and agentstate " + VALID_AGENT_STATE + " and managecom like '"
////				+ szSendOutCom.substring(1) + "%' ";
//		 
//		logger.debug("VertifyAgent1=" + szSql);
//		LAAgentSet setLAAgent = new LAAgentDB().executeQuery(szSql);
//		if (setLAAgent.size() != 1) {
//			buildError("verifyAgent", "代理人" + szReceiveCom.substring(1) + "不可用或者" + szReceiveCom.substring(1)
//					+ "不属于机构" + szSendOutCom.substring(1));
//			return false;
//		}
		
		//modify by duanyh 2009-8-28
		 String szSql = "select * from laagent where agentcode = '" +"?agentcode?"+"'";
		 SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		 sqlbv17.sql(szSql);
		 sqlbv17.put("agentcode", szReceiveCom.substring(1));
		 LAAgentSet setLAAgent = new LAAgentDB().executeQuery(sqlbv17);
		 if (setLAAgent.size() != 1) 
		 {
			 buildError("verifyAgent2", "代理人" + szReceiveCom.substring(1) + "不可用!");
				return false;
		 }
		 LAAgentSchema tLAAgentSchema = setLAAgent.get(1);
		 //联办和中介的校验权限只限制到分公司一级
		 String SendOutCom = szSendOutCom.substring(1);
		 logger.debug("发放的管理机构SendOutCom="+SendOutCom);
		 if(tLAAgentSchema.getBranchType().equals("6")||tLAAgentSchema.getBranchType().equals("7"))
		 {
			 if(SendOutCom.length()<4||(!tLAAgentSchema.getManageCom().startsWith(SendOutCom.substring(0, 4))))
			 {
				 buildError("verifyAgent2", szReceiveCom.substring(1)+ "不属于机构" + szSendOutCom.substring(1));
				 return false;
			 }			 
		 }
		 else 
		 {
			 if(!tLAAgentSchema.getManageCom().startsWith(SendOutCom))
			 {
				 buildError("verifyAgent2", szReceiveCom.substring(1)+ "不属于机构" + szSendOutCom.substring(1));
				 return false;
			 }			 
		 }

		// 如果不是最末级的机构(8位机构,或者为部门B)，则需要查询权限表
		if (szSendOutCom.length() != LAST_COM && szSendOutCom.charAt(0) != 'B') {
			szSql = "SELECT * FROM LZAccess WHERE SendOutCom = '" + "?SendOutCom?"
					+ "' AND ReceiveCom LIKE 'D%'";
			logger.debug("verifyAgent2=" + szSql);
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(szSql);
			sqlbv18.put("SendOutCom", szSendOutCom);
			LZAccessSet setLZAccess = new LZAccessDB().executeQuery(sqlbv18);
			if (setLZAccess.size() != 1) {
				buildError("verifyAgent", "机构" + szSendOutCom.substring(1) + "没有向代理人发放单证的权限");
				return false;
			}
		}

		return true;
	}

	/*
	 * kevin, 2002-09-25 输入： SendOutCom 发放机构 ReceiveCom 代理人 功能：
	 * 发放机构是否有对该代理进行单证发放的权限， 在此函数中将判断发放机构是不是最末一级的机构
	 */
	private static boolean verifyAgent(String szSendOutCom, String szReceiveCom) {
		
		/**
		 * 2009-6-19 zhangzheng
		 * 联办和中介的校验权限只限制到分公司一级
		 */
		// 看代理人是否属于这个机构
		/* String szSql = "select * from laagent " + "where agentcode = '" +
		 szReceiveCom.substring(1)
		 + "' and agentstate "
		 + VALID_AGENT_STATE
		 + " and branchtype in('6','7') and managecom like '" +
		 szSendOutCom.substring(1,5) + "%' " // 
		 + "union all " 
		 + "select * from laagent " + "where agentcode = '" +
		 szReceiveCom.substring(1)
		 + "' and agentstate " + VALID_AGENT_STATE
		 + " and branchtype not in('6','7') and managecom like '" +
		 szSendOutCom.substring(1,7) + "%' ";*/
		//zy 20100317 查询有问题，如果发放者是联办的则在获取 szSendOutCom.substring(1,7)则会数组越界
		String tBranchTypeSql="select branchtype from laagent where agentcode = '"+"?agentcode?"+"'";
		ExeSQL mExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql(tBranchTypeSql);
		sqlbv19.put("agentcode", szReceiveCom.substring(1));
		String tBranchType=mExeSQL.getOneValue(sqlbv19);
		if(tBranchType==null || "".equals(tBranchType))
		{
			buildError("verifyAgent", "代理人" + szReceiveCom.substring(1) + "没有代理人渠道信息，请核实");
			return false;
		}
		String szSql ="";
		if("6".equals(tBranchType) || "7".equals(tBranchType))
		{
			 szSql = "select * from laagent " + "where agentcode = '" +
			 "?agentcode?"
			 + "' and agentstate "
			 + VALID_AGENT_STATE
			 + " and branchtype in('6','7') and managecom like concat('" +
			 "?managecom1?" + "','%') ";
		}
		else
		{
			szSql ="select * from laagent " + "where agentcode = '" +
			 "?agentcode?"
			 + "' and agentstate " + VALID_AGENT_STATE
			 + " and branchtype not in('6','7') and managecom like concat('" +
			 "?managecom2?" + "','%') ";
		}
//		String szSql = "select * from laagent where agentcode = '" + szReceiveCom.substring(1)
//				+ "' and agentstate " + VALID_AGENT_STATE + " and managecom like '"
//				+ szSendOutCom.substring(1) + "%' ";
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql(szSql);
		sqlbv20.put("agentcode", szReceiveCom.substring(1));
		sqlbv20.put("managecom1", szSendOutCom.substring(1,5));
		sqlbv20.put("managecom2", szSendOutCom.substring(1,7));
		logger.debug("VertifyAgent1=" + szSql);
		LAAgentSet setLAAgent = new LAAgentDB().executeQuery(sqlbv20);
		if (setLAAgent.size() != 1) {
			buildError("verifyAgent", "代理人" + szReceiveCom.substring(1) + "不可用或者" + szReceiveCom.substring(1)
					+ "不属于机构" + szSendOutCom.substring(1));
			return false;
		}

		// 如果不是最末级的机构(8位机构,或者为部门B)，则需要查询权限表
		if (szSendOutCom.length() != LAST_COM && szSendOutCom.charAt(0) != 'B') {
			szSql = "SELECT * FROM LZAccess WHERE SendOutCom = '" + "?SendOutCom?"
					+ "' AND ReceiveCom LIKE 'D%'";
			logger.debug("verifyAgent2=" + szSql);
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.sql(szSql);
			sqlbv21.put("SendOutCom", szSendOutCom);
			LZAccessSet setLZAccess = new LZAccessDB().executeQuery(sqlbv21);
			if (setLZAccess.size() != 1) {
				buildError("verifyAgent", "机构" + szSendOutCom.substring(1) + "没有向代理人发放单证的权限");
				return false;
			}
		}

		return true;
	}

	/*
	 * wentao, 2004-10-26 输入： PolNo 保单号 SendOutCom 'D' + 代理人编码 ReceiveCom 'A' +
	 * 管理机构代码 功能： 该保单号对应的是否是这个代理人
	 */
	public static boolean verifyAgentCode(String szPolNo, String szSendOutCom, String szReceiveCom) {
		mErrors.clearErrors();

		// 查询保单对应的代理人编码和管理机构
		String sql = "SELECT * FROM lcpol WHERE polno = '" + "?polno?" + "' ";
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql(sql);
		sqlbv22.put("polno", szPolNo);
		// 查询该保单是否存在
		LCPolSet setLCPol = new LCPolDB().executeQuery(sqlbv22);
		if (setLCPol.size() != 1) {
			buildError("verifyAgentCode", "该保单不存在！");
			return false;
		}

		String agentcode = setLCPol.get(1).getAgentCode();
		String comcode = setLCPol.get(1).getManageCom();

		logger.debug("Query result : " + agentcode + " -- " + comcode);
		// 判断该保单对应的代理人和输入的代理人是否一致
		if (szSendOutCom.substring(1).trim().equals(agentcode) == false) {
			buildError("verifyAgentCode", "该保单的代理人和录入的代理人不一致！");
			return false;
		}
		if (szReceiveCom.substring(1).trim().equals(comcode) == false) {
			buildError("verifyAgentCode", "该保单的机构代码和录入的机构代码不一致！");
			return false;
		}
		return true;
	}

	/*
	 * Kevin 2002-10-30 校验发放机构和接收机构的存在
	 */
	protected static boolean isComsExist(String szSendOutCom, String szReceiveCom) {
		String szSql;
		mErrors.clearErrors();
		if (szSendOutCom == null || szReceiveCom == null || szSendOutCom.length() < 2
				|| szReceiveCom.length() < 2) {
			buildError("isComsExist", "没有指定发放/接收机构，或者是机构长度小于2位。");
			return false;
		}

		// verify SendOutCom
		if (szSendOutCom.charAt(0) == 'A') {
			szSql = "SELECT * FROM LDCom WHERE ComCode = '" + "?ComCode?" + "'";
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.sql(szSql);
			sqlbv23.put("ComCode", szSendOutCom.substring(1));
			if (new LDComDB().executeQuery(sqlbv23).size() != 1) {
				buildError("isComsExist", "发放机构【" + szSendOutCom + "】 不存在!");
				return false;
			}
		} else if (szSendOutCom.charAt(0) == 'B') {
			szSql = "SELECT * FROM LDCom WHERE ComCode = '"
					+ "?ComCode?" + "'";
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql(szSql);
			sqlbv24.put("ComCode", szSendOutCom.substring(1, szSendOutCom.length() - 2));
			if (new LDComDB().executeQuery(sqlbv24).size() != 1) {
				buildError("isComsExist", "发放部门【" + szSendOutCom + "】 不存在!");
				return false;
			}
		} else if (szSendOutCom.charAt(0) == 'D') {
			szSql = "SELECT * FROM LAAgent" + " WHERE AgentCode = '" + "?AgentCode?" + "'"
					+ " AND AgentState " + VALID_AGENT_STATE;
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			sqlbv25.sql(szSql);
			sqlbv25.put("AgentCode", szSendOutCom.substring(1));
			if (new LAAgentDB().executeQuery(sqlbv25).size() != 1) {
				buildError("isComsExist", "发放者【" + szSendOutCom + "】不存在或者已经失效!");
				return false;
			}
		} else if (szSendOutCom.equals("00")) {
			// do nothing
		} else {
			buildError("isComsExist", "发放者【" + szSendOutCom + "】格式错误，必须以A、B或D开头。");
			return false;
		}

		// verify ReceiveCom
		if (szReceiveCom.charAt(0) == 'A') {
			szSql = "SELECT * FROM LDCom WHERE ComCode = '" + "?ComCode?" + "'";
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.sql(szSql);
			sqlbv26.put("ComCode", szReceiveCom.substring(1));
			if (new LDComDB().executeQuery(sqlbv26).size() != 1) {
				buildError("isComsExist", "接收机构【" + szReceiveCom + "】不存在!");
				return false;
			}
		} else if (szReceiveCom.charAt(0) == 'B') {
			szSql = "SELECT * FROM LDCom WHERE ComCode = '"
					+ "?ComCode?" + "'";
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.sql(szSql);
			sqlbv27.put("ComCode", szReceiveCom.substring(1, szReceiveCom.length() - 2));
			if (new LDComDB().executeQuery(sqlbv27).size() != 1) {
				buildError("isComsExist", "接收部门【" + szReceiveCom + "】 不存在!");
				return false;
			}
		} else if (szReceiveCom.charAt(0) == 'D') {
			szSql = "SELECT * FROM LAAgent" + " WHERE AgentCode = '" + "?AgentCode?" + "'"
					+ " AND AgentState " + VALID_AGENT_STATE;
			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
			sqlbv28.sql(szSql);
			sqlbv28.put("AgentCode", szReceiveCom.substring(1));
			if (new LAAgentDB().executeQuery(sqlbv28).size() != 1) {
				buildError("isComsExist", "接收者【" + szReceiveCom + "】不存在或者已经失效!");
				return false;
			}
		} else {
			buildError("isComsExist", szReceiveCom + "格式错误，必须以A、B、C或D开头。");
			return false;
		}

		return true;
	}

	/*
	 * wentao 2004-10-12 校验发放机构和接收机构的存在 (个人保单的回执不进行校验业务员的状态)
	 */
	protected static boolean isComsExist2(String szSendOutCom, String szReceiveCom) {
		String szSql;
		// 清空静态错误
		mErrors.clearErrors();

		if (szSendOutCom == null || szReceiveCom == null || szSendOutCom.length() < 2
				|| szReceiveCom.length() < 2) {
			buildError("isComsExist2", "没有指定发放/接收机构，或者是机构长度小于2位。");
			return false;
		}

		// verify SendOutCom
		if (szSendOutCom.charAt(0) == 'A') {
			szSql = "SELECT * FROM LDCom WHERE ComCode = '" + "?ComCode?" + "'";
			SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
			sqlbv29.sql(szSql);
			sqlbv29.put("ComCode", szSendOutCom.substring(1));
			if (new LDComDB().executeQuery(sqlbv29).size() != 1) {
				buildError("isComsExist2", szSendOutCom.substring(1) + "该机构不存在!");
				return false;
			}

		} else if (szSendOutCom.charAt(0) == 'B') {
			//modify by duanyh 对部门的校验，如：B863201，校验8632是否存在于ldcom表中
			szSql = "SELECT * FROM LDCom WHERE ComCode = '" + "?ComCode?"+ "'";
			SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
			sqlbv30.sql(szSql);
			sqlbv30.put("ComCode", szSendOutCom.substring(1, szSendOutCom.trim().length()-2));
			if (new LDComDB().executeQuery(sqlbv30).size() != 1) {
				buildError("isComsExist2", szSendOutCom.substring(1, szSendOutCom.trim().length()-2) + "该机构不存在!");
				return false;
			}

		} else if (szSendOutCom.charAt(0) == 'D') {
			szSql = "SELECT * FROM LAAgent" + " WHERE AgentCode = '" + "?AgentCode?" + "'";
			SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
			sqlbv31.sql(szSql);
			sqlbv31.put("AgentCode", szSendOutCom.substring(1));
			if (new LAAgentDB().executeQuery(sqlbv31).size() != 1) {
				buildError("isComsExist2", szSendOutCom.substring(1) + "该业务员不存在!");
				return false;
			}

		} else if (szSendOutCom.equals("00")) {
			// do nothing

		} else {
			buildError("isComsExist2", szSendOutCom.substring(1) + "格式错误，必须以A、B或D开头。");
			return false;
		}

		// verify ReceiveCom
		if (szReceiveCom.charAt(0) == 'A') {
			szSql = "SELECT * FROM LDCom WHERE ComCode = '" + "?ComCode?" + "'";
			SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
			sqlbv32.sql(szSql);
			sqlbv32.put("ComCode", szReceiveCom.substring(1));
			if (new LDComDB().executeQuery(sqlbv32).size() != 1) {
				buildError("isComsExist2", szReceiveCom.substring(1) + "该机构不存在!");
				return false;
			}

		} else if (szReceiveCom.charAt(0) == 'B') {
//			modify by duanyh 对部门的校验，如：B863201，校验8632是否存在于ldcom表中
			szSql = "SELECT * FROM LDCom WHERE ComCode = '" + "?ComCode?"+ "'";
			SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
			sqlbv33.sql(szSql);
			sqlbv33.put("ComCode", szReceiveCom.substring(1, szReceiveCom.trim().length()-2));
			if (new LDComDB().executeQuery(sqlbv33).size() != 1) {
				buildError("isComsExist2", szReceiveCom.substring(1, szReceiveCom.trim().length()-2) + "该机构不存在!");
				return false;
			}

		} else if (szReceiveCom.charAt(0) == 'C') {
			szSql = "SELECT * FROM LDUser WHERE UserCode = '" + "?UserCode?" + "'";
			SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
			sqlbv34.sql(szSql);
			sqlbv34.put("UserCode",szReceiveCom.substring(1));
			if (new LDUserDB().executeQuery(sqlbv34).size() != 1) {
				buildError("isComsExist2", szReceiveCom.substring(1) + "该用户不存在!");
				return false;
			}

		} else if (szReceiveCom.charAt(0) == 'D') {
			szSql = "SELECT * FROM LAAgent" + " WHERE AgentCode = '" + "?AgentCode?" + "'";
			SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
			sqlbv35.sql(szSql);
			sqlbv35.put("AgentCode", szReceiveCom.substring(1));
			if (new LAAgentDB().executeQuery(sqlbv35).size() != 1) {
				buildError("isComsExist2", szReceiveCom.substring(1) + "该业务员不存在!");
				return false;
			}

		} else {
			buildError("isComsExist2", szReceiveCom + "格式错误，必须以A、B、C或D开头。");
			return false;
		}

		return true;
	}

	/**
	 * 返回两个大数字的差值
	 * 
	 * @param strMinuend
	 *            被减数
	 * @param strSubtrahend
	 *            减数
	 * @return strMinuend - strSubtrahend
	 */
	protected static long bigIntegerDiff(String strMinuend, String strSubtrahend) {
		BigInteger bigIntMinuend = new BigInteger(strMinuend);
		BigInteger bigIntSubtrahend = new BigInteger(strSubtrahend);
		return bigIntMinuend.subtract(bigIntSubtrahend).longValue();
	}

	/**
	 * 返回两个大数相加的结果
	 * 
	 * @param strX
	 *            加数一
	 * @param strY
	 *            加数二
	 * @param nMinLen
	 *            返回结果的最小长度
	 * @return strX + strY
	 */
	protected static String bigIntegerPlus(String strX, String strY, int nMinLen) {
		BigInteger bigX = new BigInteger(strX);
		BigInteger bigY = new BigInteger(strY);

		String str = (bigX.add(bigY)).toString();
		String strTemp = "00000000000000000000";
		strTemp = strTemp.substring(1, (nMinLen - str.length()) + 1);

		return strTemp + str;
	}

	/**
	 * 处理系统单证的描述
	 * 
	 * @param aLZSysCertifySchema
	 *            系统单证数据
	 * @param conn
	 *            数据库连接对象
	 * @param nFlag
	 *            标识：0表示执行BeforeSql；1表示执行AfterSql
	 * @param sb
	 *            在描述表中描述的错误信息
	 * @return true执行成功；false执行失败
	 * @throws Exception
	 */
	protected static boolean handleSysCertifyDesc(LZSysCertifySchema aLZSysCertifySchema, Connection conn,
			int nFlag, StringBuffer sb) throws Exception {
		// 清空静态错误
		mErrors.clearErrors();

		if (conn == null || (nFlag != 0 && nFlag != 1)) {
			throw new Exception("数据库连接为空或者是非法的标识");
		}

		// First, we get calcode associated with this kind of SysCertify
		LMCertifySubDB tLMCertifySubDB = new LMCertifySubDB(conn);

		tLMCertifySubDB.setCertifyCode(aLZSysCertifySchema.getCertifyCode());

		if (tLMCertifySubDB.getInfo() == false) {
			buildError("handleSysCertifyDesc", "系统单证的描述有错");
			return false;
		}

		sb.append(tLMCertifySubDB.getErrorMessage());

		// After getting the calcode, use
		String strCalCode = "";

		if (nFlag == 0) {
			strCalCode = tLMCertifySubDB.getBeforeSQL();
		} else {
			strCalCode = tLMCertifySubDB.getAfterSQL();
		}

		Calculator calculator = new Calculator();

		calculator.addBasicFactor("CertifyNo", aLZSysCertifySchema.getCertifyNo());
		calculator.addBasicFactor("StateFlag", aLZSysCertifySchema.getStateFlag());
		calculator.addBasicFactor("AgentCode", aLZSysCertifySchema.getReceiveCom());
		calculator.addBasicFactor("TakeBackDate", aLZSysCertifySchema.getTakeBackDate());
		calculator.setCalCode(strCalCode);

		String strSQL = calculator.calculate();

		logger.debug("SQL : " + strSQL);

		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		boolean bFlag = false;

		try {
			if (0 == nFlag) {
				rs = stmt.executeQuery(strSQL);
				if (rs.next()) {
					bFlag = rs.getString(1).equals("1");
				}
				rs.close();
			} else if (1 == nFlag) {
				bFlag = true;

				String strTemp = "";
				char cTemp = ' ';

				for (int nIndex = 0; nIndex < strSQL.length(); nIndex++) {
					cTemp = strSQL.charAt(nIndex);
					if (cTemp == ';') {
						stmt.executeUpdate(strTemp);
						logger.debug(strTemp);
						strTemp = "";
					} else {
						strTemp += String.valueOf(cTemp);
					}
				}

				if (!strTemp.equals("")) {
					logger.debug(strTemp);
					stmt.executeUpdate(strTemp);
				}
			}
			stmt.close();
		} catch (Exception ex) {
			if (rs != null) {
				rs.close();
			}
			stmt.close();
			return false;
		}

		return bFlag;
	}

	/**
	 * Kevin 2003-03-25 校验代理人可执有的单证数量的最大值
	 * 
	 * @param aLZCardSchema
	 * @return
	 */
	public static boolean verifyMaxCount(LZCardSchema aLZCardSchema) {
		// 清空静态错误
		mErrors.clearErrors();

		String szCertifyCode = aLZCardSchema.getCertifyCode();
		String szReceiveCom = aLZCardSchema.getReceiveCom();

		// 如果不是发到代理人手中，则不用校验了。
		if (szReceiveCom.charAt(0) != 'D') {
			return true;
		}

		// 查询单证的个人/非个人代理人最大领用数
		LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
		tLMCertifyDesDB.setCertifyCode(aLZCardSchema.getCertifyCode());
		if (!tLMCertifyDesDB.getInfo()) {
			buildError("splitCertify", "在单证描述表中找不到单证类型的信息！");
			return false;
		}

		// 不限量领用，则不用校验了。
		if (tLMCertifyDesDB.getHaveLimit().equals("N")) {
			return true;
		}

		// 查询代理人渠道，用于判断代理人是否个人代理人
		String tBranchType = "";
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(szReceiveCom.substring(1));
		if (tLAAgentDB.getInfo()) {
			tBranchType = tLAAgentDB.getBranchType();
		}

		int nMaxCount = 0;
		if ("2".equals(tBranchType) || "3".equals(tBranchType) || "6".equals(tBranchType)
				|| "7".equals(tBranchType)) {
			nMaxCount = tLMCertifyDesDB.getMaxUnit2();// 非个人代理人
		} else {
			nMaxCount = tLMCertifyDesDB.getMaxUnit1();// 个人代理人
		}

		// 得到代理人现有单证数量
		String strSQL = "SELECT (case when SUM(SUMCOUNT) is null then 0 else SUM(SUMCOUNT) end) FROM LZCard WHERE StateFlag = '3'"
				+ " AND CertifyCode = '" + "?CertifyCode?" + "'" + " AND ReceiveCom = '" + "?ReceiveCom?" + "'";
		SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
		sqlbv36.sql(strSQL);
		sqlbv36.put("CertifyCode", szCertifyCode);
		sqlbv36.put("ReceiveCom", szReceiveCom);
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSQL.execSQL(sqlbv36);
		if (exeSQL.mErrors.needDealError()) {
			mErrors.copyAllErrors(exeSQL.mErrors);
			return false;
		}
		int nCurCount = Integer.parseInt(ssrs.GetText(1, 1));

		// 本次发放单证的数量
		int nCount = 0;
		if ("Y".equals(tLMCertifyDesDB.getHaveNumber())) {// 有号单证取起止号之差
			String strErrMsg = verifyNo(aLZCardSchema);
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return false;
			}
			nCount = (int) bigIntegerDiff(aLZCardSchema.getEndNo(), aLZCardSchema.getStartNo()) + 1;
		} else if ("N".equals(tLMCertifyDesDB.getHaveNumber())) {// 有号单证取数量
			nCount = aLZCardSchema.getSumCount();
		}

		if (nCurCount + nCount > nMaxCount) {
			buildError("verifyMaxCount", "发放后，业务员手中执有的单证数将超过最大数量!");
			return false;
		}

		return true;
	}

	/**
	 * 从系统变量表中取得单证号码长度的定义
	 * 
	 * @return
	 */
	public static int getCertifyNoLen(String szCertifyCode) {
		LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
		tLMCertifyDesDB.setCertifyCode(szCertifyCode);

		int nLen = 14;

		if (tLMCertifyDesDB.getInfo()) {
			nLen = (int) tLMCertifyDesDB.getCertifyLength();
		} else {
			logger.debug("Can't get information,use default 14!");
		}

		return nLen;
	}

	/**
	 * 调用单证管理服务
	 * 
	 * @param aLMCertifySubSchema
	 * @return
	 */
	protected static boolean callCertifyService(LZSysCertifySchema aLZSysCertifySchema,
			GlobalInput aGlobalInput) {
		// 清空静态错误
		mErrors.clearErrors();

		LMCertifySubDB tLMCertifySubDB = new LMCertifySubDB();
		tLMCertifySubDB.setCertifyCode(aLZSysCertifySchema.getCertifyCode());
		if (!tLMCertifySubDB.getInfo()) {
			buildError("callCertifyService", "系统单证的描述有错");
			return false;
		}

		if (tLMCertifySubDB.getInterfaceClass() == null
				|| StrTool.cTrim(tLMCertifySubDB.getInterfaceClass()).equals("")) {
			return true;
		}

		// 调用单证管理服务
		try {
			Class cls = Class.forName(tLMCertifySubDB.getInterfaceClass());
			CertifyService ps = (CertifyService) cls.newInstance();

			// 准备数据
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setPrtSeq(aLZSysCertifySchema.getCertifyNo());
			if (!tLOPRTManagerDB.getInfo()) {
				buildError("callCertifyService", "打印管理队列有错");
				return false;
			}
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema.setSchema(tLOPRTManagerDB);

			String strOperate = "TakeBack";

			VData vData = new VData();

			vData.add(aGlobalInput);
			vData.add(tLOPRTManagerSchema);

			if (!ps.submitData(vData, strOperate)) {
				mErrors.copyAllErrors(ps.getErrors());
				return false;
			}

			mResult = ps.getResult();

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("callCertifyService", ex.toString());
			return false;
		}

		return true;
	}

	/**
	 * 单证发放：将传入的单证列表信息转化成单证操作所需要的格式。
	 * 
	 * 对有号单证，查询其号段分布的记录
	 * 
	 * 对于无号单证，将根据输入的单证数量，从数据库中凑足所需数量。
	 * 
	 * @param aLZCardSet
	 * @return
	 */
	public static LZCardSet formatCardList(LZCardSchema aLZCardSchema) {
		mErrors.clearErrors();
		LZCardSet tLZCardSet = new LZCardSet();

		// 查询单证描述表，确认是否是有号单证
		LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
		tLMCertifyDesDB.setCertifyCode(aLZCardSchema.getCertifyCode());
		if (!tLMCertifyDesDB.getInfo()) {
			mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
			return null;
		}

		// 有号单证
		if (tLMCertifyDesDB.getHaveNumber().equals("Y")) {
			logger.debug("formatCardList:" + tLMCertifyDesDB.getCertifyCode()
					+ "是有号单证,下面将要进行起止号码的长度校验!");
			String strErrMsg = verifyNo(aLZCardSchema);
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return null;
			}
			// 2-已入库（库存）,3-已发放未核销
			strErrMsg = getRealNo(aLZCardSchema, tLZCardSet, "('2','3')");
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return null;
			}
		}
		// 无号单证
		else if (tLMCertifyDesDB.getHaveNumber().equals("N")) {
			logger.debug("formatCardList:" + tLMCertifyDesDB.getCertifyCode()
					+ "是无号单证,下面将要根据数量，从业务系统中查询出所需数量的单证!");
			LZCardSet newLZCardSet = findNo(aLZCardSchema);
			if (newLZCardSet == null) {
				return null;
			}
			tLZCardSet.add(newLZCardSet);
		} else {
			buildError("formatCardSet", "单证是否有号标志错误!");
			return null;
		}

		return tLZCardSet;
	}

	/**
	 * 单证回收:将传入的单证列表信息转化成单证操作所需要的格式
	 */
	public static LZCardSet formatCardListTakeBack(LZCardSchema aLZCardSchema) {
		mErrors.clearErrors();
		LZCardSet tLZCardSet = new LZCardSet();

		// 查询单证描述表
		LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
		tLMCertifyDesDB.setCertifyCode(aLZCardSchema.getCertifyCode());
		if (!tLMCertifyDesDB.getInfo()) {
			mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
			return null;
		}
		// 校验是否需要回收
		if (!"Y".equals(tLMCertifyDesDB.getTackBackFlag())) {
			buildError("formatCardSet", "单证编码【" + aLZCardSchema.getCertifyCode() + "】是否回收标志不是Y，不需要回收！");
			return null;
		}
		// 校验是否有号单证
		if (tLMCertifyDesDB.getHaveNumber().equals("Y")) {
			logger.debug("formatCardList:" + tLMCertifyDesDB.getCertifyCode()
					+ "是有号单证,下面将要进行起止号码的长度校验!");
			String strErrMsg = verifyNo(aLZCardSchema);
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return null;
			}
			// 3-已发放未核销
			strErrMsg = getRealNo(aLZCardSchema, tLZCardSet, "('3')");
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return null;
			}
		} else {
			buildError("formatCardSet", "单证编码【" + aLZCardSchema.getCertifyCode() + "】不是有号单证不能核销！");
			return null;
		}

		return tLZCardSet;
	}


	/**
	 * 单证回收回退:将传入的单证列表信息转化成单证操作所需要的格式
	 */
	public static LZCardBSet formatCardListReveTakeBack(LZCardSchema aLZCardSchema) {
		mErrors.clearErrors();
		LZCardBSet tLZCardBSet = new LZCardBSet();
		// 查询单证描述表，确认某一个单证是否是有号单证
		LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
		tLMCertifyDesDB.setCertifyCode(aLZCardSchema.getCertifyCode());
		if (!tLMCertifyDesDB.getInfo()) {
			mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
			return null;
		}
		// 有号单证
		if (tLMCertifyDesDB.getHaveNumber().equals("Y")) {
			logger.debug("formatCardList:" + tLMCertifyDesDB.getCertifyCode()
					+ "是有号单证,下面将要进行起止号码的长度校验!");
			String strErrMsg = verifyNo(aLZCardSchema);
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return null;
			}
			// 4-自动缴销,5-手工缴销,6-使用作废,7-停用作废
			strErrMsg = getRealNoB(aLZCardSchema, tLZCardBSet, "('4','5','6','7')");
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return null;
			}
		} else {
			buildError("formatCardSet", "单证编码【" + aLZCardSchema.getCertifyCode() + "】不是有号单证不能核销！");
			return null;
		}

		return tLZCardBSet;
	}

	/**
	 * 单证挂失、单证作废:将传入的单证列表信息转化成单证操作所需要的格式
	 */
	public static LZCardSet formatCardListLoss(LZCardSchema aLZCardSchema) {
		mErrors.clearErrors();
		LZCardSet tLZCardSet = new LZCardSet();

		// 查询单证描述表，确认是否是有号单证
		LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
		tLMCertifyDesDB.setCertifyCode(aLZCardSchema.getCertifyCode());
		if (!tLMCertifyDesDB.getInfo()) {
			mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
			return null;
		}
		// 有号单证
		if (tLMCertifyDesDB.getHaveNumber().equals("Y")) {
			logger.debug("formatCardList:" + tLMCertifyDesDB.getCertifyCode()
					+ "是有号单证,下面将要进行起止号码的长度校验!");
			String strErrMsg = verifyNo(aLZCardSchema);
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return null;
			}
			// 3-已发放未核销,8-逾期
			strErrMsg = getRealNo(aLZCardSchema, tLZCardSet, "('3','8')");
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return null;
			}
		} else {
			buildError("formatCardSet", "单证编码【" + aLZCardSchema.getCertifyCode() + "】不是有号单证不能核销！");
			return null;
		}

		return tLZCardSet;
	}

	/**
	 * 单证销毁:将传入的单证列表信息转化成单证操作所需要的格式
	 */
	public static LZCardBSet formatCardListDestroy(LZCardSchema aLZCardSchema) {
		mErrors.clearErrors();
		LZCardBSet tLZCardBSet = new LZCardBSet();

		// 查询单证描述表，确认某一个单证是否是有号单证
		LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
		tLMCertifyDesDB.setCertifyCode(aLZCardSchema.getCertifyCode());
		if (!tLMCertifyDesDB.getInfo()) {
			mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
			return null;
		}

		// 有号单证
		if (tLMCertifyDesDB.getHaveNumber().equals("Y")) {
			logger.debug("formatCardList:" + tLMCertifyDesDB.getCertifyCode()
					+ "是有号单证,下面将要进行起止号码的长度校验!");
			String strErrMsg = verifyNo(aLZCardSchema);
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return null;
			}
			// 6-使用作废,7-停用作废
			strErrMsg = getRealNoB(aLZCardSchema, tLZCardBSet, "('6','7')");
			if (!strErrMsg.equals("")) {
				buildError("formatCardSet", strErrMsg);
				return null;
			}
		} else {
			buildError("formatCardSet", "单证编码【" + aLZCardSchema.getCertifyCode() + "】不是有号单证不能核销！");
			return null;
		}

		return tLZCardBSet;
	}

	/**
	 * Kevin 2003-04-25 根据数量，从业务系统中查询出所需数量的单证。
	 * 
	 * @param aLZCardSchema
	 * @return 返回空值，表示出错。
	 */
	protected static LZCardSet findNo(LZCardSchema aLZCardSchema) {
		
		int nSumCount = aLZCardSchema.getSumCount();
		
		if (nSumCount <= 0) {
			buildError("findNo", "无号单证请录入发放数量，请检查！");
			return null;
		}
	

		LZCardDB tLZCardDB = new LZCardDB();
		String sql = "select * from LZCard a where a.certifycode='" + "?certifycode?"
				+ "' and a.ReceiveCom='" + "?ReceiveCom?" + "' and a.stateflag in ('2','3')";
		SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
		sqlbv37.sql(sql);
		sqlbv37.put("certifycode", aLZCardSchema.getCertifyCode());
		sqlbv37.put("ReceiveCom", aLZCardSchema.getSendOutCom());
		LZCardSet tempLZCardSet = tLZCardDB.executeQuery(sqlbv37);
		
		if (tLZCardDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLZCardDB.mErrors);
			return null;
		}
		

		LZCardSet tLZCardSet = new LZCardSet();
		int nIndex = 0;

		while (nSumCount > 0 && nIndex < tempLZCardSet.size()) {
			LZCardSchema tLZCardSchema = tempLZCardSet.get(nIndex + 1);

			if (nSumCount <= tLZCardSchema.getSumCount()) {
				tLZCardSchema.setSumCount(nSumCount);
				tLZCardSchema.setEndNo(bigIntegerPlus(tLZCardSchema.getStartNo(), String
						.valueOf(nSumCount - 1), getCertifyNoLen(tLZCardSchema.getCertifyCode())));
			}
			LZCardSchema newLZCardSchema = new LZCardSchema();
			newLZCardSchema.setSchema(aLZCardSchema);
			newLZCardSchema.setStartNo(tLZCardSchema.getStartNo());
			newLZCardSchema.setEndNo(tLZCardSchema.getEndNo());
			newLZCardSchema.setSumCount(tLZCardSchema.getSumCount());
			tLZCardSet.add(newLZCardSchema);

			nSumCount -= tLZCardSchema.getSumCount();
			nIndex++;
		}

		if (nSumCount > 0) {
			buildError("findNo", "没有足够的单证");
			return null;
		}

		return tLZCardSet;
	}

	/**
	 * 校验单证起止号码长度
	 * 
	 * @param aLZCardSchema
	 * @return
	 */
	public static String verifyNo(LZCardSchema aLZCardSchema) {
		logger.debug("开始进行CertifyFunc:vertifyNo函数!");

		int nNoLen = getCertifyNoLen(aLZCardSchema.getCertifyCode());

		logger.debug(aLZCardSchema.getCertifyCode() + "定义的单证长度:" + nNoLen);
		logger.debug("起始号:" + aLZCardSchema.getStartNo() + "trim后的号段长度:"
				+ aLZCardSchema.getStartNo().trim().length());
		logger.debug("终止号:" + aLZCardSchema.getEndNo() + "trim后的号段长度:"
				+ aLZCardSchema.getEndNo().trim().length());

		if (aLZCardSchema.getStartNo() == null || aLZCardSchema.getStartNo().trim().length() != nNoLen) {
			return "起始号长度不等于" + String.valueOf(nNoLen);
		}

		if (aLZCardSchema.getEndNo() == null || aLZCardSchema.getEndNo().trim().length() != nNoLen) {
			return "终止号长度不等于" + String.valueOf(nNoLen);
		}

		try {
			new BigInteger(aLZCardSchema.getStartNo());
		} catch (Exception ex) {
			return aLZCardSchema.getStartNo() + "不是数字";
		}

		try {
			new BigInteger(aLZCardSchema.getEndNo());
		} catch (Exception ex) {
			return aLZCardSchema.getStartNo() + "不是数字";
		}

		if (bigIntegerDiff(aLZCardSchema.getStartNo(), aLZCardSchema.getEndNo()) > 0) {
			return "输入的起始号码不能大于终止号码，请重新输入!";
		}

		return "";
	}

	/**
	 * 查询某一个代理人对于某一个单证的最大领用数
	 * 
	 * @param szAgentCode
	 * @param szCertifyCode
	 * @return
	 */
	protected static int getMaxCount(String szAgentCode, String szCertifyCode) {
		// 清空静态错误
		mErrors.clearErrors();

		// 先按代理人编码查询最大数配置
		LDAgentCardCountDB tLDAgentCardCountDB = new LDAgentCardCountDB();

		tLDAgentCardCountDB.setAgentCode(szAgentCode);
		tLDAgentCardCountDB.setCertifyCode(szCertifyCode);
		tLDAgentCardCountDB.setAgentGrade("*");
		tLDAgentCardCountDB.setManageCom("*");

		if (tLDAgentCardCountDB.getInfo()) {
			return (int) tLDAgentCardCountDB.getMaxCount();
		}

		// 如果查询失败，则按代理人所处的管理机构和代理人职级查询最大数的配置
		LATreeDB tLATreeDB = new LATreeDB();

		tLATreeDB.setAgentCode(szAgentCode);

		if (!tLATreeDB.getInfo()) {
			mErrors.copyAllErrors(tLATreeDB.mErrors);
			return -1;
		}

		tLDAgentCardCountDB = new LDAgentCardCountDB();
		tLDAgentCardCountDB.setAgentCode("*");
		tLDAgentCardCountDB.setCertifyCode(szCertifyCode);
		tLDAgentCardCountDB.setAgentGrade(tLATreeDB.getAgentGrade());
		tLDAgentCardCountDB.setManageCom(tLATreeDB.getManageCom());

		if (tLDAgentCardCountDB.getInfo()) {
			return (int) tLDAgentCardCountDB.getMaxCount();
		}

		// 查询整个系统的缺省配置
		tLDAgentCardCountDB = new LDAgentCardCountDB();
		tLDAgentCardCountDB.setAgentCode("*");
		tLDAgentCardCountDB.setCertifyCode("*");
		tLDAgentCardCountDB.setAgentGrade("*");
		tLDAgentCardCountDB.setManageCom("*");

		if (tLDAgentCardCountDB.getInfo()) {
			return (int) tLDAgentCardCountDB.getMaxCount();
		}

		buildError("getMaxCount", "缺少最大领用数的配置");
		return -1;
	}

	/**
	 * Kevin 2003-06-03
	 * 
	 * 如果数据库中有1到100号和101到200号单证。用户可以直接输入51到150号。
	 * 在这个函数中，将用户输入的51到150号拆分成51到100号和101到150号单证。
	 * 
	 * @param aLZCardSchema
	 * @return 返回空值，表示出错。
	 */
	protected static String getRealNo(LZCardSchema aLZCardSchema, LZCardSet aLZCardSet, String StateFlag) {
		
		/**
		 * 2009-06-05 防止查询记录过多,增加对查询记录数量的限制
		 * zhangzheng
		 */
		String checkSql="select count(1) from lzcard WHERE CertifyCode = '" + "?CertifyCode?"
				+ "' AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '"
				+ "?EndNo?" + "' AND StateFlag in " + StateFlag + " ";
		SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
		sqlbv38.sql(checkSql);
		sqlbv38.put("CertifyCode", aLZCardSchema.getCertifyCode());
		sqlbv38.put("StartNo", aLZCardSchema.getEndNo());
		sqlbv38.put("EndNo", aLZCardSchema.getStartNo());
		//sqlbv38.put("StateFlag", StateFlag);
		ExeSQL tExeSQL = new ExeSQL();
		if(Integer.parseInt(tExeSQL.getOneValue(sqlbv38))>100000)
		{
			return "根据录入的起始号和终止号查询的记录数超过了100000条,请详细查询后录入准确的需要操作的单证号码";
		}
		
		tExeSQL=null;
		
		String strSQL = "SELECT * FROM LZCard WHERE CertifyCode = '" + "?CertifyCode?"
				+ "' AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '"
				+ "?EndNo?" + "' AND StateFlag in " + StateFlag + " ORDER BY StartNo";
		logger.debug("有号单证:getRealNo():" + strSQL);
		SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
		sqlbv39.sql(strSQL);
		sqlbv39.put("CertifyCode", aLZCardSchema.getCertifyCode());
		sqlbv39.put("StartNo", aLZCardSchema.getEndNo());
		sqlbv39.put("EndNo", aLZCardSchema.getStartNo());
		//sqlbv39.put("StateFlag", StateFlag);
		LZCardDB tLZCardDB = new LZCardDB();
		LZCardSet tempLZCardSet = tLZCardDB.executeQuery(sqlbv39);
		if (tLZCardDB.mErrors.needDealError()) {
			return tLZCardDB.mErrors.getFirstError();
		}
		
		if (tempLZCardSet.size() == 0) {
			return "在库存中，找不到单证号为" + aLZCardSchema.getStartNo() + "的单证";
		}
		

		LZCardSchema tLZCardSchema = new LZCardSchema();
		tLZCardSchema.setSchema(aLZCardSchema);
		tLZCardSchema.setStartNo(tempLZCardSet.get(1).getStartNo());
		tLZCardSchema.setEndNo(tempLZCardSet.get(1).getEndNo());

		// 判断起始号
		if (bigIntegerDiff(tLZCardSchema.getStartNo(), aLZCardSchema.getStartNo()) > 0) {
			return "在库存中，找不到单证号为" + aLZCardSchema.getStartNo() + "的单证";
		}
		tLZCardSchema.setStartNo(aLZCardSchema.getStartNo());

		// 判断终止号
		if (bigIntegerDiff(tLZCardSchema.getEndNo(), aLZCardSchema.getEndNo()) > 0) {
			tLZCardSchema.setEndNo(aLZCardSchema.getEndNo());
			aLZCardSet.add(tLZCardSchema);
			return "";
		}

		// 将符合条件的记录放到返回的Set中
		aLZCardSet.add(tLZCardSchema);

		String strEndNo = tLZCardSchema.getEndNo(); // 记录下单证终止号
		int nNoLen = getCertifyNoLen(aLZCardSchema.getCertifyCode());

		for (int nIndex = 1; nIndex < tempLZCardSet.size(); nIndex++) {
			tLZCardSchema = new LZCardSchema();

			tLZCardSchema.setSchema(aLZCardSchema);
			tLZCardSchema.setStartNo(tempLZCardSet.get(nIndex + 1).getStartNo());
			tLZCardSchema.setEndNo(tempLZCardSet.get(nIndex + 1).getEndNo());

			if (bigIntegerDiff(tLZCardSchema.getStartNo(), strEndNo) != 1) {
				break;
			}

			if (bigIntegerDiff(tLZCardSchema.getEndNo(), aLZCardSchema.getEndNo()) > 0) {
				tLZCardSchema.setEndNo(aLZCardSchema.getEndNo());
			}

			strEndNo = tLZCardSchema.getEndNo();

			// 将符合条件的记录放到返回的Set中
			aLZCardSet.add(tLZCardSchema);
		}

		if (!strEndNo.equals(aLZCardSchema.getEndNo())) {
			return "在库存中，找不到单证号为" + bigIntegerPlus(strEndNo, "1", nNoLen) + "的单证";
		} else {
			return "";
		}
	}

	protected static String getRealNoB(LZCardSchema aLZCardSchema, LZCardBSet aLZCardBSet, String StateFlag) {
		
		
		/**
		 * 2009-06-05 防止一次性拆分记录过多,增加一次性拆分的限制
		 * zhangzheng
		 */
		
		String checkSql="select count(1) from lzcard WHERE CertifyCode = '" + "?CertifyCode?"
		+ "' AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '"
		+ "?EndNo?" + "' AND StateFlag in " + StateFlag + " ";
		SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
		sqlbv40.sql(checkSql);
		sqlbv40.put("CertifyCode", aLZCardSchema.getCertifyCode());
		sqlbv40.put("StartNo", aLZCardSchema.getEndNo());
		sqlbv40.put("EndNo", aLZCardSchema.getStartNo());
		//sqlbv40.put("StateFlag", StateFlag);
		ExeSQL tExeSQL = new ExeSQL();
		if(Integer.parseInt(tExeSQL.getOneValue(sqlbv40))>100000)
		{
			return "根据录入的起始号和终止号查询的记录数超过了100000条,请详细查询后录入准确的需要操作的单证号码";
		}

		tExeSQL=null;

		String strSQL = "SELECT * FROM LZCardB WHERE CertifyCode = '" + "?CertifyCode?"
				+ "' AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '"
				+ "?EndNo?" + "' AND StateFlag in " + StateFlag + " ORDER BY StartNo";
		logger.debug("有号单证:getRealNoB():" + strSQL);
		SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
		sqlbv41.sql(strSQL);
		sqlbv41.put("CertifyCode", aLZCardSchema.getCertifyCode());
		sqlbv41.put("StartNo", aLZCardSchema.getEndNo());
		sqlbv41.put("EndNo", aLZCardSchema.getStartNo());
		//sqlbv41.put("StateFlag", StateFlag);
		LZCardBDB tLZCardBDB = new LZCardBDB();
		LZCardBSet tempLZCardBSet = tLZCardBDB.executeQuery(sqlbv41);
		if (tLZCardBDB.mErrors.needDealError()) {
			return tLZCardBDB.mErrors.getFirstError();
		}

		if (tempLZCardBSet.size() == 0) {
			return "找不到单证号为" + aLZCardSchema.getStartNo() + "的单证";
		}

		LZCardBSchema tLZCardBSchema = new LZCardBSchema();
		Reflections tReflections = new Reflections();
		tReflections.transFields(tLZCardBSchema, aLZCardSchema);
		tLZCardBSchema.setStartNo(tempLZCardBSet.get(1).getStartNo());
		tLZCardBSchema.setEndNo(tempLZCardBSet.get(1).getEndNo());

		// 判断起始号
		if (bigIntegerDiff(tLZCardBSchema.getStartNo(), aLZCardSchema.getStartNo()) > 0) {
			return "找不到单证号为" + aLZCardSchema.getStartNo() + "的单证";
		}
		tLZCardBSchema.setStartNo(aLZCardSchema.getStartNo());

		// 判断终止号
		if (bigIntegerDiff(tLZCardBSchema.getEndNo(), aLZCardSchema.getEndNo()) > 0) {
			tLZCardBSchema.setEndNo(aLZCardSchema.getEndNo());
			aLZCardBSet.add(tLZCardBSchema);
			return "";
		}

		// 将符合条件的记录放到返回的Set中
		aLZCardBSet.add(tLZCardBSchema);

		String strEndNo = tLZCardBSchema.getEndNo(); // 记录下单证终止号
		int nNoLen = getCertifyNoLen(aLZCardSchema.getCertifyCode());

		for (int nIndex = 1; nIndex < tempLZCardBSet.size(); nIndex++) {
			tLZCardBSchema = new LZCardBSchema();
			tReflections.transFields(tLZCardBSchema, aLZCardSchema);
			tLZCardBSchema.setStartNo(tempLZCardBSet.get(nIndex + 1).getStartNo());
			tLZCardBSchema.setEndNo(tempLZCardBSet.get(nIndex + 1).getEndNo());

			if (bigIntegerDiff(tLZCardBSchema.getStartNo(), strEndNo) != 1) {
				break;
			}

			if (bigIntegerDiff(tLZCardBSchema.getEndNo(), aLZCardSchema.getEndNo()) > 0) {
				tLZCardBSchema.setEndNo(aLZCardSchema.getEndNo());
			}

			strEndNo = tLZCardBSchema.getEndNo();

			// 将符合条件的记录放到返回的Set中
			aLZCardBSet.add(tLZCardBSchema);
		}

		if (!strEndNo.equals(aLZCardSchema.getEndNo())) {
			return "找不到单证号为" + bigIntegerPlus(strEndNo, "1", nNoLen) + "的单证";
		} else {
			return "";
		}
	}

	/**
	 * 从LZCardPrint中取得MaxDate,作为Invalidate, 存进LZCard,LZCardTrack add by ly
	 * 
	 * @return String
	 */
	protected static String getInvalidate(String certifyCode, String startNo, String endNo) {
		logger.debug("getinvalidate begin ");
		String invalidate = "";
		String sql = " select * from lzcardprint where certifycode = '" + "?certifycode?" + "' and startno <= '"
				+ "?startno?" + "' and endno >= '" + "?endno?" + "'";
		SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
		sqlbv42.sql(sql);
		sqlbv42.put("", certifyCode);
		sqlbv42.put("startno", startNo);
		sqlbv42.put("endno", endNo);
		LZCardPrintDB dbLZCardPrint = new LZCardPrintDB();
		LZCardPrintSet tLZCardPrintSet = new LZCardPrintSet();
		tLZCardPrintSet = dbLZCardPrint.executeQuery(sqlbv42);
		if (tLZCardPrintSet != null && tLZCardPrintSet.size() > 0) {
			invalidate = tLZCardPrintSet.get(1).getMaxDate();
		}
		logger.debug(invalidate);
		logger.debug("getinvalidate end ");
		return invalidate;
	}

	/**
	 * 2007-03-20 校验判断一张单证是否在数据库里存在多条数据记录,如果存在多条则返回false，否则返回true
	 * 
	 * @param aLZCardSchema
	 *            LZCardSchema,单证记录
	 * @param Connection
	 *            aConn,数据连接
	 * @return boolean.
	 */
	protected static boolean verifySingleLZCard(LZCardSchema aLZCardSchema, Connection aConn) {
		if (aLZCardSchema == null) {
			return true;
		}
		String aCertifyCode = aLZCardSchema.getCertifyCode();
		String aStartNo = aLZCardSchema.getStartNo();
		String aEndNo = aLZCardSchema.getEndNo();
		String aStartSQL = "";
		String aEndSQL = "";

		if (aCertifyCode != null && aStartNo != null && !aCertifyCode.equals("") && !aStartNo.equals("")) {
			aStartSQL = "select count(*) from lzcard where certifycode='" + "?certifycode?" + "'"
					+ " and startno<='" + "?startno?" + "' and endno>='" + "?startno?" + "'";
		}

		if (aCertifyCode != null && aEndNo != null && !aCertifyCode.equals("") && !aEndNo.equals("")) {
			aEndSQL = "select count(*) from lzcard where certifycode='" + "?certifycode?" + "'"
					+ " and startno<='" + "?endno?" + "' and endno>='" + "?endno?" + "'";
		}

		// 查询是否有多条记录
		ExeSQL tExeSQL = new ExeSQL(aConn);
		String aCountNum = "0";
		logger.debug("校验单证记录是否唯一aSQL====" + aStartSQL);
		if (!aStartSQL.equals("")) {
			SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
			sqlbv43.sql(aStartSQL);
			sqlbv43.put("certifycode", aCertifyCode);
			sqlbv43.put("startno", aStartNo);
			aCountNum = tExeSQL.getOneValue(sqlbv43);
			if (Integer.parseInt(aCountNum) != 1) {
				return false;
			}
		}

		logger.debug("校验单证记录是否唯一aEndSQL====" + aEndSQL);
		if (!aEndSQL.equals("")) {
			SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
			sqlbv44.sql(aStartSQL);
			sqlbv44.put("certifycode", aCertifyCode);
			sqlbv44.put("endno", aEndNo);
			aCountNum = tExeSQL.getOneValue(sqlbv44);
			if (Integer.parseInt(aCountNum) != 1) {
				return false;
			}
		}

		return true;
	}
	protected static boolean DesConfirm(LZCardAppSet appLZCardAppSet,GlobalInput mGlobalInput) {


		// 产生回收清算单号
		String strTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", PubFun.getNoLimit(mGlobalInput.ComCode));

		for (int nIndex = 1; nIndex <= appLZCardAppSet.size(); nIndex++) {
			MMap mMap = new MMap();
			LZCardAppSchema tLZCardAppSchema = appLZCardAppSet.get(nIndex);
	
			LZCardAppDB tLZCardAppDB = new LZCardAppDB();
			tLZCardAppDB.setApplyNo(tLZCardAppSchema.getApplyNo());
			if (tLZCardAppDB.getInfo()) {
				tLZCardAppDB.setStateFlag("3");// 3、销毁确认
				tLZCardAppDB.setReplyPerson(tLZCardAppSchema.getReplyPerson());
				tLZCardAppDB.setReplyDate(tLZCardAppSchema.getReplyDate());
				mMap.put(tLZCardAppDB.getSchema(), "UPDATE");// 更新LZCardApp
			}
			String szCertifyCode = tLZCardAppDB.getCertifyCode();
			String appStartNo = tLZCardAppDB.getStartNo();
//			String strEndNo = tLZCardAppDB.getEndNo();
			int strLen=(int) CertifyFunc.bigIntegerDiff(tLZCardAppDB.getEndNo(),tLZCardAppDB.getStartNo())+1;
			int nNoLen = CertifyFunc.getCertifyNoLen(szCertifyCode);
			for(int n=0;n<strLen;n++)
			{
				MMap tMap = new MMap();
				String strStartNo = CertifyFunc.bigIntegerPlus(appStartNo, String.valueOf(n), nNoLen);
				// 单证状态校验
				String szSql = "SELECT * FROM LZCardB WHERE CertifyCode = '" + "?CertifyCode?"+ "' AND StateFlag in ('6','7') AND StartNo <= '" 
				             + "?StartNo?" + "' AND EndNo >= '"+ "?StartNo?" + "' ";
				logger.debug("splitCertifyBlankOut: 单证状态校验的SQL:" + szSql);
				LZCardBDB dbLZCardB = new LZCardBDB();
				SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
				sqlbv45.sql(szSql);
				sqlbv45.put("CertifyCode", szCertifyCode);
				sqlbv45.put("StartNo", strStartNo);
				LZCardBSet cLZCardBSet = dbLZCardB.executeQuery(sqlbv45);
				if (cLZCardBSet==null || cLZCardBSet.size()<1) { // 输入的单证号在可用的单证号内
	//				CError.buildErr(this,"不存在相应的单证信息");
					buildError("DesConfirm", "不存在相应的单证信息");
//					return false;
					logger.debug("相应的单证---"+strStartNo+"不存在");
					continue;
				}
				for(int i=1;i<=cLZCardBSet.size();i++)
				{
					LZCardBDB dsLZCardBDB = new LZCardBDB();
					LZCardBDB deLZCardBDB = new LZCardBDB();
					LZCardBDB dLZCardBDB = new LZCardBDB();
					dsLZCardBDB.setSchema(cLZCardBSet.get(i));
					deLZCardBDB.setSchema(cLZCardBSet.get(i));
					dLZCardBDB.setSchema(cLZCardBSet.get(i));
					String dStartNo = dLZCardBDB.getStartNo();//号段起始号
					String dEndNo = dLZCardBDB.getEndNo();//号段终止号
					if(dStartNo.compareTo(strStartNo) < 0) //待销毁单证起号大于号段起号
					{
						if(dEndNo.compareTo(strStartNo) > 0) //待销毁单证止号小于号段止号
						{
							tMap.put(dLZCardBDB.getSchema(), "DELETE");	
							
							String sEndNo=CertifyFunc.bigIntegerPlus(strStartNo, "-1", nNoLen);
							String eStartNo=CertifyFunc.bigIntegerPlus(strStartNo, "1", nNoLen);
							dsLZCardBDB.setStartNo(dStartNo);
							dsLZCardBDB.setEndNo(sEndNo);
							dsLZCardBDB.setSumCount((int) CertifyFunc.bigIntegerDiff(sEndNo, dStartNo) + 1);
							dsLZCardBDB.setModifyDate(PubFun.getCurrentDate());
							dsLZCardBDB.setModifyTime(PubFun.getCurrentTime());
							tMap.put(dsLZCardBDB.getSchema(), "DELETE&INSERT");	
							
	
							
							deLZCardBDB.setStartNo(eStartNo);
							deLZCardBDB.setEndNo(dEndNo);
							deLZCardBDB.setSumCount((int) CertifyFunc.bigIntegerDiff(dEndNo, eStartNo) + 1);
							deLZCardBDB.setModifyDate(PubFun.getCurrentDate());
							deLZCardBDB.setModifyTime(PubFun.getCurrentTime());
							tMap.put(deLZCardBDB.getSchema(), "DELETE&INSERT");	
						}
						else
						{
							tMap.put(dLZCardBDB.getSchema(), "DELETE");	
							
							String sEndNo=CertifyFunc.bigIntegerPlus(strStartNo, "-1", nNoLen);	
							dsLZCardBDB.setStartNo(dStartNo);
							dsLZCardBDB.setEndNo(sEndNo);
							dsLZCardBDB.setSumCount((int) CertifyFunc.bigIntegerDiff(sEndNo, dStartNo) + 1);
							dsLZCardBDB.setModifyDate(PubFun.getCurrentDate());
							dsLZCardBDB.setModifyTime(PubFun.getCurrentTime());
							tMap.put(dsLZCardBDB.getSchema(), "DELETE&INSERT");	
						}
							
					}
					else
					{
						if(dEndNo.compareTo(strStartNo) > 0) //待销毁单证止号小于号段止号
						{
							tMap.put(dLZCardBDB.getSchema(), "DELETE");	
							
							String eStartNo=CertifyFunc.bigIntegerPlus(strStartNo, "1", nNoLen);
							deLZCardBDB.setStartNo(eStartNo);
							deLZCardBDB.setEndNo(dEndNo);
							deLZCardBDB.setSumCount((int) CertifyFunc.bigIntegerDiff(dEndNo, eStartNo) + 1);
							deLZCardBDB.setModifyDate(PubFun.getCurrentDate());
							deLZCardBDB.setModifyTime(PubFun.getCurrentTime());
							tMap.put(deLZCardBDB.getSchema(), "DELETE&INSERT");	
						}
						else
						{
							tMap.put(dLZCardBDB.getSchema(), "DELETE");	
						}
					}
					
					
				}
	
	//			for(int n=0;n<strLen;n++)
	//			{
					LZCardBSchema tLZCardBSchema = new LZCardBSchema();
					tLZCardBSchema.setSchema(cLZCardBSet.get(1));
					tLZCardBSchema.setStartNo(strStartNo);
					tLZCardBSchema.setEndNo(strStartNo);
					tLZCardBSchema.setSumCount(1);
					tLZCardBSchema.setStateFlag("11");
					tLZCardBSchema.setTakeBackNo(strTakeBackNo);
					tLZCardBSchema.setHandler(tLZCardAppDB.getHandler());
					tLZCardBSchema.setHandleDate(tLZCardAppDB.getHandleDate());
					tLZCardBSchema.setApplyNo(tLZCardAppDB.getApplyNo());
					tLZCardBSchema.setOperateFlag("1");// 1：回收
					tLZCardBSchema.setOperator(mGlobalInput.Operator);
					tLZCardBSchema.setMakeDate(PubFun.getCurrentDate());
					tLZCardBSchema.setMakeTime(PubFun.getCurrentTime());
					tLZCardBSchema.setModifyDate(PubFun.getCurrentDate());
					tLZCardBSchema.setModifyTime(PubFun.getCurrentTime());
	//					tLZCardBSchema.setAgentCom(aLZCardBSchema.getAgentCom());
	//\					vResult.set(3, tLZCardBSchema);
					tMap.put(tLZCardBSchema, "INSERT");
	
					// 记录单证操作轨迹,核销的单证保存于lzcardtrackb
					LZCardTrackBSchema tLZCardTrackBSchema = new LZCardTrackBSchema();
					Reflections tReflections = new Reflections();
					tReflections.transFields(tLZCardTrackBSchema, tLZCardBSchema);
					logger.debug("splitCertifyDestroyApply: (LzcardTrackB)准备保存新的单证的起始号:"
							+ tLZCardTrackBSchema.getStartNo());
					logger.debug("splitCertifyDestroyApply: (LzcardTrackB)准备保存新的单证的终止号:"
							+ tLZCardTrackBSchema.getEndNo());
					logger.debug("splitCertifyDestroyApply: (LzcardTrackB)准备保存新的单证的数量:"
							+ tLZCardTrackBSchema.getSumCount());
	
					tMap.put(tLZCardTrackBSchema, "INSERT");
	//		}
	
	//			tVData.clear();
				VData tVData = new VData();
				tVData.add(tMap);
				PubSubmit tPubSubmit = new PubSubmit();
				if (!tPubSubmit.submitData(tVData, "")) {
	//				CError.buildErr(this, "批量导入失败！");
					buildError("DesConfirm", "进行数据库提交失败，可能单证销毁数据已存在");
//					return false;
					logger.debug("相应的单证---"+strStartNo+"不存在");
					continue;
				}
			}
			VData tVData = new VData();
			tVData.add(mMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tVData, "")) {
//				CError.buildErr(this, "批量导入失败！");
				buildError("DesConfirm", "进行数据库提交失败，可能单证销毁数据已存在");
				return false;
			}

		} // end of for(int nIndex = 0; ...
		return true;			
	}
}
