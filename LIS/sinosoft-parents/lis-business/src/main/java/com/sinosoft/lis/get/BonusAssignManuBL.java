package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

/**
 * <p>Title: 红利分配批处理（手工触发）</p>
 * <p>Description: 
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author  pst
 * @version 1.0
 */


import com.sinosoft.lis.db.LOBonusPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BonusAssignManuBL implements BusinessService{
private static Logger logger = Logger.getLogger(BonusAssignManuBL.class);
	public BonusAssignManuBL() {
	}

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/**选数SQL，循环处理，筛选一部分保单，但不包括失效和终止的报单
	 * */
	private String tSql = "";

	/** 封装要操作的数据，以便一次提交 */
	/** 往后面传输数据的容器 */
	private VData mInputData;

	/**处理的机构*/
	private GlobalInput tGlobalInput = null;

	private TransferData mTransferData = new TransferData();

	/**分红年度*/
	private String tFiscalYear;

	/**红利分配组号*/
	private String tGroupID;


	/** 数据操作字符串 */
	private String mOperate;

	/**保单号*/
	private String tContNo;
	
	private String 	tCom;

	private String tCurMakeDate = PubFun.getCurrentDate();

    private  int tSuc = 0, tFail = 0;
	public String mContent = "";
	
	private ExeSQL tExeSQL = new ExeSQL();
	
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("after getInputData....");
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  Insurance dividends Assign Start !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		String tComSQL = "";
		if (!"".equals(tContNo) && tContNo != null) {
			tCom = tContNo.substring(0, 4);
			if (!dealData()) {
				 // 日志监控,结果监控
				PubFun.logResult (tGlobalInput,tGlobalInput.LogID[1],"本次红利派发成功"+ tSuc + "单");
				PubFun.logResult (tGlobalInput,tGlobalInput.LogID[1],"本次红利派发失败"+ tFail + "单");
				return false;
			}
		} else {
			tComSQL = "select distinct substr(comcode,1,4) com from ldcom  where comcode!='86' "
					+ " order by com";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tComSQL);
			SSRS tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError.buildErr(this, "查询分支机构失败!");
				return false;
			} else {
				for (int k = 1; k <= tSSRS.MaxRow; k++) {
					// 数据操作业务处理
					tCom = tSSRS.GetText(k, 1);
					if (!dealData()) {
						 // 日志监控,结果监控
						PubFun.logResult (tGlobalInput,tGlobalInput.LogID[1],"本次红利派发成功"+ tSuc + "单");
						PubFun.logResult (tGlobalInput,tGlobalInput.LogID[1],"本次红利派发失败"+ tFail + "单");
						return false;
					}
				}
			}
		}
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  Insurance dividends Assign End !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		logger.debug("after dealData...");
		 // 日志监控,结果监控
		PubFun.logResult (tGlobalInput,tGlobalInput.LogID[1],"本次红利派发成功"+ tSuc + "单");
		PubFun.logResult (tGlobalInput,tGlobalInput.LogID[1],"本次红利派发失败"+ tFail + "单");



		return true;
	}

	private boolean getInputData() {
		// 全局变量
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		tFiscalYear = (String) mTransferData.getValueByName("FiscalYear");
		// 如果会计年度录入为空直接将其返回
		if (tFiscalYear == null || "".equals(tFiscalYear)) {
			return false;
		}
		tContNo = (String) mTransferData.getValueByName("ContNo");

		tGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (tGlobalInput == null) {
			tGlobalInput = new GlobalInput();
			tGlobalInput.Operator = "001";
			tGlobalInput.ManageCom = "86";
		}
		
		return true;
	}


	public boolean dealData() {

		// 红利计算组,如果为空则将其置为 ”1“
		if (tGroupID == null || "".equals(tGroupID)) {
			tGroupID = "1";
		}
		if (tGlobalInput == null) {
			tGlobalInput.Operator = "001";
			tGlobalInput.ManageCom = "86";
		}

		try {
			LCContSet tLCContSet = new LCContSet();

			String tContSql = "select * from lccont a where exists (select 'X' from LOBonusPol b"
				+ " where a.contno=b.contno and FiscalYear='?tFiscalYear?' and BonusFlag='0'  and groupid='?tGroupID?'"
				+ " and SGetDate<='?tCurMakeDate?' and AGetDate is null"
				+ " and substr(ContNo,1,4)='?tCom?'"
				// modify by jiaqiangli 2009-09-04 不能这么判断 只需要应分日期<=交至日期即可 类似于生存金的处理
				//+ " and exists (select 'B' from LCContState  where PolNo=b.PolNo and StateType='Available' and State='0' AND ((STARTDATE<=b.sgetdate and  EndDate >=b.SGetDate) or (EndDate is null))))"
				+ " and exists (select 'B' from lcpol  where PolNo=b.PolNo and b.sgetdate<=paytodate)) "
				+ ReportPubFun.getWherePart("ContNo", ReportPubFun.getParameterStr(tContNo, "?tContNo?"))
			   + " and (a.AppFlag='1' or (a.AppFlag='4' and  exists (select 'T' from LCContState where contno=a.contno and StateType='Terminate' and State='1' "
		       +" and EndDate is null and StateReason in ('01'))) or (a.AppFlag='4' and  exists (select 'T' from LCContState where contno=a.contno and StateType='Terminate' and State='1' "
		       +" and EndDate is null and StateReason in ('07')))) " 			       			       
		       //满期终止，失效终止的也要进行红利计算
				+ " and not exists (select  'X' from lcconthangupstate d where d.ContNo=a.ContNo and d.hanguptype='2')";// ??
		// 可以确定处于保全挂起的不要进行分红。其他的应该不受影响


			logger.debug(tContSql);

			RSWrapper rsWrapper = new RSWrapper();
			// 采取长连接的方式
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tContSql);
			sqlbv1.put("tFiscalYear", tFiscalYear);
			sqlbv1.put("tGroupID", tGroupID);
			sqlbv1.put("tCurMakeDate", tCurMakeDate);
			sqlbv1.put("tCom", tCom);
			sqlbv1.put("tContNo", tContNo);
			if (!rsWrapper.prepareData(tLCContSet, sqlbv1)) {
				this.mErrors.copyAllErrors(rsWrapper.mErrors);
				logger.debug(rsWrapper.mErrors.getFirstError());
				return false;
			}
			do {
				rsWrapper.getData();
				if (tLCContSet == null || tLCContSet.size() <= 0) {
					break;
				}
				String LockNo= "";
				for (int k = 1; k <= tLCContSet.size(); k++) {
					LCContSchema tLCContSchema = tLCContSet.get(k);
					
					 // 日志监控,过程监控
					PubFun.logTrack (tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发开始");
					
					LockNo = "";
					LockNo= tLCContSchema.getContNo();
					if (!mPubLock.lock(LockNo, "LB0002", tGlobalInput.Operator)) 
					{
						CError tError = new CError(mPubLock.mErrors.getLastError());
						this.mErrors.addOneError(tError);
						 // 日志监控,状态监控				
						PubFun.logState(tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发失败","0");
						 // 日志监控,过程监控
						PubFun.logTrack (tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发结束");
						tFail++;
						continue;
					}
					
					LOBonusPolSet tLOBonusPolSet = new LOBonusPolSet();
					LOBonusPolDB tLOBonusPolDB = new LOBonusPolDB();
					tSql = "select * from LOBonusPol b"
						+ " where FiscalYear='?tFiscalYear?' and BonusFlag='0'  and groupid='?tGroupID?'"
						+ " and SGetDate<='?tCurMakeDate?' and AGetDate is null"
						+ " and substr(ContNo,1,4)='?tCom?'"
						+ " and b.ContNo='?ContNo?'";
//						+ " and (a.AppFlag='1' or (a.AppFlag='4' and  exists (select 'T' from LCContState where PolNo=a.PolNo and StateType='Terminate' and State='1' "
//						+ " and EndDate is null and StateReason in ('01'))) or (a.AppFlag='4' and  exists (select 'T' from LCContState where PolNo=a.PolNo and StateType='Terminate' and State='1' "
//		                +" and EndDate is null and StateReason in ('07')))) " //满期终止的也要进行红利计算
//						+ " and exists (select 'B' from LCContState  where PolNo=b.PolNo and StateType='Available' and State='0' AND ((STARTDATE<=b.sgetdate and  EndDate >=b.SGetDate) or ( EndDate is null)))";
				     logger.debug(tSql);
				     SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				     sqlbv2.sql(tSql);
				     sqlbv2.put("tFiscalYear", tFiscalYear);
				     sqlbv2.put("tGroupID", tGroupID);
				     sqlbv2.put("tCurMakeDate", tCurMakeDate);
				     sqlbv2.put("tCom", tCom);
				     sqlbv2.put("ContNo", tLCContSchema.getContNo());

					tLOBonusPolSet = tLOBonusPolDB.executeQuery(sqlbv2);
					if (tLOBonusPolSet == null || tLOBonusPolSet.size() < 1) {
						 // 日志监控,状态监控
						PubFun.logState(tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发失败","0");
						 // 日志监控,过程监控
						PubFun.logTrack (tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发结束");
						continue;
					}
					/** 分配红利金额，以LOBonusPolSet 为处理单位 */
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("FiscalYear", tFiscalYear);
					VData tVData = new VData();
					VData mResult = new VData();
					tVData.add(tGlobalInput);
					tVData.add(tLOBonusPolSet);
					tVData.add(tLCContSchema);
					tVData.add(tTransferData);
					BonusAssignBL tBonusAssignBL = new BonusAssignBL();
					// 业务逻辑处理
					if (!tBonusAssignBL.submitData(tVData, "")) {
						logger.debug(mErrors.getFirstError()
								+ " 红利分配处理失败，保单号：" + tLCContSchema.getContNo());
						tFail++;
						 // 日志监控,状态监控
						PubFun.logState(tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发失败","0");
						 // 日志监控,过程监控
						PubFun.logTrack (tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发结束");
						continue;
					}

					// 数据提交
					mResult.clear();
					mResult = tBonusAssignBL.getResult();
					if (mResult.size() > 0) {
						PubSubmit tSubmit = new PubSubmit();
						if (!tSubmit.submitData(mResult, "")) {
							// @@错误处理
							this.mErrors.copyAllErrors(tSubmit.mErrors);
							tFail++;
//							 日志监控,状态监控
							PubFun.logState(tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发失败","0");
							 // 日志监控,过程监控
							PubFun.logTrack (tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发结束");
							continue;
						}
					}
					tSuc++;
					logger.debug("*********************保单红利分配完成！保单号："
							+ tLCContSchema.getContNo()
							+ "*********************");
					 // 日志监控,状态监控
					PubFun.logState(tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发成功","1");
					 // 日志监控,过程监控
					PubFun.logTrack (tGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发结束");
					mPubLock.unLock();
				}

			} while (tLCContSet != null && tLCContSet.size() > 0);
			rsWrapper.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, ex.getMessage());
		} finally {

		mPubLock.unLock();
		}

		mContent = "成功:" + tSuc + "单，失败: " + tFail + "单";
		logger.debug("成功:" + tSuc + "单，失败: " + tFail + "单");
		

		return true;
	}
	
	public static void main(String str[]) {
		TransferData tTransferData = new TransferData();
		//测试保单 ContNo=86110020080219001970
		tTransferData.setNameAndValue("FiscalYear", "2008");
	//tTransferData.setNameAndValue("ContNo", "86350220050210000632");
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		BonusAssignManuBL tBonusAssignManuBL = new BonusAssignManuBL();
		tBonusAssignManuBL.submitData(tVData, "");

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
