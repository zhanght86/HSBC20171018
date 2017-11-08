package com.sinosoft.lis.taskservice.taskinstance;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LOBonusPolDB;
import com.sinosoft.lis.get.BonusAssignBL;
import com.sinosoft.lis.get.BonusAssignManuBL;
import com.sinosoft.lis.operfee.IndiDueFeePartQuery;
import com.sinosoft.lis.operfee.RnDealBL;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.bq.EdorWorkFlowUI;

/**
 * <p>
 * Title: 个单保全转账失败通知书批处理
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangfh
 * @version 1.0
 */
public class BQBankFailureNoticeTMThread extends TaskThread {
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private static Logger logger = Logger
			.getLogger(BQBankFailureNoticeTMThread.class);
	public CErrors mErrors = new CErrors();
	private GlobalInput tG = new GlobalInput();
	private int tSuc = 0, tFail = 0;
	private String mCurrentDate = PubFun.getCurrentDate();
	/** 红利分配组号 */
	private String tGroupID;
	private String tCurMakeDate = PubFun.getCurrentDate();
	private String tCom;
	private int xLength;

	public BQBankFailureNoticeTMThread() {
	}

	public boolean dealMain() {

		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--个单保全转账失败通知书批处理" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		// 日志监控,初始化
		tG.LogID[0] = "TASK" + (String) mParameters.get("TaskCode");
		tG.LogID[1] = (String) mParameters.get("SerialNo");
		PubFun.logPerformance(tG, tG.LogID[1], "个单保全转账失败通知书批处理开始", "1");
		// 启动时间
		Date dNow = new Date();
		long initTime = dNow.getTime();

		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='BQBankFailureNoticeTMCount' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();

		// 处理的机构
		// tGlobalInput = (GlobalInput)mParameters.get("GlobalInput");

		TransferData tTransferData = new TransferData();
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tG);
		String tComSQL = "";

		try {
			// 循环处理退费转账失败问题件
			// a.GetMoney,a.bankcode,a.bankaccno,b.accname ljspay.getnoticeno
			// modify by jiaqiangli 2009-11-04 a.managecom
			// ExeSQL tExeSQL = new ExeSQL();
			int snum = 0;
			int fnum = 0;
			// 循环处理收费转账失败问题件
			// a.GetMoney,a.bankcode,a.bankaccno,b.accname ljspay.getnoticeno
			String tPaySQL = "select b.EdorAcceptNo,b.ContNo,b.EdorType,b.edorappdate,a.GetNoticeNo "
					+ "from LJSPay a, LPEdorItem b where 1 = 1 and  (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) and b.ManageCom like concat('"
					// add by jiaqiangli 2009-05-06 ljspay.payform已另作他用
					// 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
					+ "?ManageCom?"
					+ "','%') and a.OtherNoType = '10' and b.EdorAcceptNo = a.OtherNo and a.sendbankcount=3 and a.bankcode is not null "
					+ "and not exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno) "
					// 个险保全 lpedoritem.grpcontno可判断，但效率低
					+ "and exists (select 1 from lccont where contno=b.contno and conttype='1') "
					+ "union "
					+ "select b.EdorAcceptNo,b.ContNo,b.EdorType,b.edorappdate,a.GetNoticeNo "
					+ "from LJSPay a, LPEdorItem b where 1 = 1 and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) and b.ManageCom like concat('"
					// 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
					+ "?ManageCom?"
					+ "','%') and a.OtherNoType = '10' and b.EdorAcceptNo = a.OtherNo and a.sendbankcount=3 and a.bankcode is not null "
					+ "and exists (select 1 from lccont where contno=b.contno and conttype='1') "
					// 3表变更修改
					+ "and exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno and stateflag='3') "
					// add by jiaqiangli 2009-08-27 还需要反向判断
					+ "and not exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno and stateflag < '3') "
					// sendbankcount=3后仍为ljspay表转账失败
					+ "order by EdorAcceptNo asc ";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tPaySQL);
			sqlbv1.put("ManageCom", tG.ManageCom);
			SSRS tPaySSRS = new SSRS();
			tPaySSRS = tExeSQL.execSQL(sqlbv1);
			String tPayDateReasonSQL = "";
			SSRS tPayDateReasonSSRS = new SSRS();
			SSRS tGetDateReasonSSRS = new SSRS();
			String LockNo = "";
			if (tPaySSRS != null && !tPaySSRS.equals("")) {

				xLength = tPaySSRS.MaxRow;
				VData hVData = new VData();
				for (int i = 1; i <= tPaySSRS.MaxRow; i++) {

					// 日志监控,过程监控
					String ssrsData[][] = null;
					// PubFun.logTrack
					// (tG,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发开始");
					ssrsData = tPaySSRS.getAllData();
					// TransferData hTransferData = new TransferData();
					VData mResult = new VData();

					Map map = new HashMap();
					map.put("ssrsData", ssrsData);
					map.put("GlobalInput", tG);
					hVData.add(map);

					// 业务逻辑处理
					if (i % tConutMax == 0) {
						mTaskWaitList.add(hVData);
						hVData = new VData();
					}

					if (i % tConutMax != 0 && i == xLength) {
						mTaskWaitList.add(hVData);
						hVData = new VData();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, ex.getMessage());
		} finally {

			mPubLock.unLock();
		}

		String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		int tThreadCount = 5;
		tExeSQL = new ExeSQL();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='BQBankFailureNoticeTMCountThread'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL_ThreadCount);
		sqlbv2.put("ManageCom", tG.ManageCom);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv2);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (xLength < tThreadCount) {
			tThreadCount = xLength;
		}

		this.mServiceA = new ServiceA(
				"com.sinosoft.lis.bq.BQBankTransferFailureNoticeTMThread",
				mTaskWaitList, tThreadCount, 10, TaskCode);
		this.mServiceA.start();

		try {
			// 循环处理退费转账失败问题件
			// a.GetMoney,a.bankcode,a.bankaccno,b.accname ljspay.getnoticeno
			// modify by jiaqiangli 2009-11-04 a.managecom
			// ExeSQL tExeSQL = new ExeSQL();
			int snum = 0;
			int fnum = 0;
			// 循环处理收费转账失败问题件
			// a.GetMoney,a.bankcode,a.bankaccno,b.accname ljspay.getnoticeno
			// 日志监控,结果监控

			mTaskWaitList = null;
			// 循环处理退费转账失败问题件
			// a.GetMoney,a.bankcode,a.bankaccno,b.accname ljspay.getnoticeno
			String tGetSQL = "select b.EdorAcceptNo,b.ContNo,b.EdorType,b.edorappdate,a.actugetNo,a.GetNoticeNo "
					+ "from LJaget a, LPEdorItem b,lpedorapp c where 1 = 1 and b.ManageCom like concat('"
					// 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
					+ "?ManageCom?"
					+ "','%') and a.OtherNoType = '10' and c.EDORCONFNO = a.OtherNo and b.EdorAcceptNo = c.EdorAcceptNo and a.sendbankcount=3 and a.paymode='4' "
					// 3次转账失败的问题件
					+ "and EnterAccDate is null and a.ConfDate is null "
					+ "and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) "
					+ "and a.BankAccNo is not null "
					// 防止重复插入loprtmanager
					// 要么是没有打印管理表
					+ "and not exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno and StandbyFlag3=a.actugetNo) "
					+ "and exists (select 1 from lccont where contno=b.contno and conttype='1') "
					+ "union "
					+ "select b.EdorAcceptNo,b.ContNo,b.EdorType,b.edorappdate,a.actugetNo,a.GetNoticeNo "
					+ "from LJaget a, LPEdorItem b,lpedorapp c where 1 = 1 and b.ManageCom like concat('"
					// 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
					+ "?ManageCom?"
					+ "','%') and a.OtherNoType = '10' and c.EDORCONFNO = a.OtherNo and b.EdorAcceptNo = c.EdorAcceptNo and a.sendbankcount=3 and a.paymode='4' "
					// 3次转账失败的问题件
					+ "and EnterAccDate is null and a.ConfDate is null "
					+ "and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) "
					+ "and a.BankAccNo is not null "
					+ "and exists (select 1 from lccont where contno=b.contno and conttype='1') "
					// 防止重复插入loprtmanager
					// 要么是打印管理表已经回复 表示新一轮转帐失败
					// 强制回收不需要ljaget.sendbankcount清0(否则可能导致以后转账成功) and remark is
					// null表强制回销 3表变更修改
					// add by jiaqiangli 2009-08-27 加上实付号
					+ "and exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno and stateflag='3' and StandbyFlag3=a.actugetNo) "
					// add by jiaqiangli 2009-08-27 同时需要加上不能存在小于3的 未核销之前都应该不再生成
					// comment by jiaqiangli 2009-08-27
					// 其实条件可以合二为一(只需要这个条件即可而不需要union 逆向思维判断)
					+ "and not exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno and stateflag < '3' and StandbyFlag3=a.actugetNo) "
					+ "order by EdorAcceptNo asc ";
			SSRS tGetSSRS = new SSRS();
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tGetSQL);
			sqlbv1.put("ManageCom", tG.ManageCom);
			tGetSSRS = tExeSQL.execSQL(sqlbv1);
			String tPayDateReasonSQL = "";
			SSRS tPayDateReasonSSRS = new SSRS();
			SSRS tGetDateReasonSSRS = new SSRS();
			String LockNo = "";
			if (tGetSSRS == null || tGetSSRS.MaxRow <= 0) {
				return false;
			}
			xLength += tGetSSRS.MaxRow;
			VData hVData = new VData();

			for (int i = 1; i <= tGetSSRS.MaxRow; i++) {

				// 日志监控,过程监控
				String ssrsData[][] = null;
				// PubFun.logTrack
				// (tG,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发开始");
				ssrsData = tGetSSRS.getAllData();
				// TransferData hTransferData = new TransferData();

				VData mResult = new VData();
				Map map = new HashMap();
				map.put("ssrsData", ssrsData);
				map.put("GlobalInput", tG);
				hVData.add(map);
				// 业务逻辑处理
				if (i % tConutMax == 0) {
					mTaskWaitList.add(hVData);
					hVData = new VData();
				}

				if (i % tConutMax != 0 && i == xLength) {
					mTaskWaitList.add(hVData);
					hVData = new VData();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, ex.getMessage());
		} finally {

			mPubLock.unLock();
		}

		// tongmeng 2009-05-21 add
		// 自适应线程数
		tExeSQL = new ExeSQL();
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (xLength < tThreadCount) {
			tThreadCount = xLength;
		}

		this.mServiceA = new ServiceA(
				"com.sinosoft.lis.bq.BQBankTransferFailureNoticeTMThread",
				mTaskWaitList, tThreadCount, 10, TaskCode);
		this.mServiceA.start();

		PubFun.logTrack(tG, tG.LogID[1], "个单保全转账失败通知书批处理");
		// 完成时间
		Date dend = new Date();
		long endTime = dend.getTime();
		long tt = (endTime - initTime) / 1000;
		// 日志监控,结果监控

		PubFun.logResult(tG, tG.LogID[1],
				"个单保全转账失败通知书批处理" + String.valueOf(xLength) + "笔,共花费" + tt + "秒");
		return true;
	}

	// ==========================================================================

	public static void main(String[] args) {
		BQBankFailureNoticeTMThread tBQBankFailureNoticeTMThread = new BQBankFailureNoticeTMThread();
		tBQBankFailureNoticeTMThread.dealMain();
	}
}
