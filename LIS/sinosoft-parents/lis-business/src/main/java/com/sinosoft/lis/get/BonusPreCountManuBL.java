package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

/**
 * <p>Title: 红利补计算手工触发</p>
 * <p>Description: 
 *  根据红利主表状态进行红利的计算,包括红利系数计算以及红利的计算
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author  pst
 * @version 1.0
 */


import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOBonusPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
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

public class BonusPreCountManuBL implements BusinessService{
private static Logger logger = Logger.getLogger(BonusPreCountManuBL.class);
	public BonusPreCountManuBL() {
	}

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/**选数SQL，循环处理，筛选一部分保单，查询出本保单下没有终止的，但可以是失效的所有的分红险，
	 * 但由于没有添加保单年期限制，会多选出很多保单,这种分段计算红利的方式要求数据要绝对稳定！
	 * */
	private String tSql = "";

	/** 封装要操作的数据，以便一次提交 */
	/** 往后面传输数据的容器 */
	private VData mInputData= new VData();

	/**处理的机构*/
	private GlobalInput tGlobalInput = null;

	private TransferData mTransferData = new TransferData();

	/**分红年度*/
	private String tFiscalYear;

	/**红利分配组号*/
	private String tGroupID;

	/**存放不参与分红那个的险种，其格式为 '211014','254056',''*/
	private String tBonusCondtion;

	/**tComputerState=“4”，说明红利系数计算完毕，而且代精算将红利主表信息维护完毕后，其进行红利金额计算，
	 tComputerState=“2”，说明本年度本组还没有进行红利系数进行，需要进行红利系数计算*/
	private String tComputerState = "";

	/** 数据操作字符串 */
	private String mOperate;

	/**保单号*/
	private String tContNo;

	private String tCurMakeDate = PubFun.getCurrentDate();

	private String tCurMakeTime = PubFun.getCurrentTime();

	public String mContent = "";
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	
	private String mFiscalYearFlag="";  //待计算会计年度的红利分配方案是否出台

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

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("after dealData...");

		return true;
	}

	private boolean getInputData() {
		// 全局变量
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		tFiscalYear = (String) mTransferData.getValueByName("FiscalYear");
		tContNo = (String) mTransferData.getValueByName("ContNo");
		// 如果会计年度录入为空直接将其返回
		if (tFiscalYear == null || "".equals(tFiscalYear)) {
			return false;
		}

		// 如果保单号录入则直接将其返回
		if (tContNo == null || "".equals(tContNo)) {
			return false;
		}
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
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  红利强制计算开始 !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		// 红利计算组,如果为空则将其置为 ”1“,现在都为 “1” ，以后可能要分离
		if (tGroupID == null || "".equals(tGroupID)) {
			tGroupID = "1";
		}

		tBonusCondtion = getBonusCondtion(tFiscalYear, tGroupID);
		if ("".equals(tBonusCondtion)) {
			tBonusCondtion = "''";
		}
        if(!CheckBonusSolution())
        {
			return false;       	
        }
		// 计算红利系数
        
        String sql;
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = null;
		//如果提前分红即强制分红
		//查询待计算会计年度的红利分配方案是否出台
		sql = "select 'X' from LOBonusMain where fiscalyear = '?tFiscalYear?' and to_number(Computestate)>=4";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("tFiscalYear", tFiscalYear);

		String tFlag = tExeSQL.getOneValue(sqlbv);
		if (!"".equals(tFlag)) 
		{
			mFiscalYearFlag = "Y";	
		}
		else
		{
			mFiscalYearFlag = "N";
		}
        
		if (!CountBonusQuotiety(tFiscalYear, tContNo, tGlobalInput)) {
			return false;
		}

		// 根据先前红利系数再计算红利
		if (!CountBonusMoney(tFiscalYear, tContNo, tGlobalInput)) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param cFiscalYear : 接收传入的会计年度值
	 * @return
	 */
	private String  vertifyBonusStatus(String cFiscalYear) {
		String sql;
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = null;
		//如果提前分红即强制分红
		//查询待计算会计年度的红利分配方案是否出台
		sql = "select 'X' from LOBonusMain where fiscalyear = '?cFiscalYear?' and to_number(Computestate)>=4";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("cFiscalYear", cFiscalYear);

		logger.debug("查询" + cFiscalYear + "的分红方案是否出台 ： " + sql);
		String tFlag = tExeSQL.getOneValue(sqlbv1);
		if (!"".equals(tFlag)) {
			return cFiscalYear;			
		}else
		{
			return String.valueOf(Integer.parseInt(tFiscalYear) - 1);
		}

	}

	/**判断传入年度和本年度的分红方案是否出台，若已经出台则继续，否则直接返回*/
	private boolean CheckBonusSolution()
	{
		//查询会计年度前一年的分红方案参数
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select groupid,distributerate,distributevalue,fiscalyear from lobonusmain "
				+ "where fiscalyear = ?fiscalyear?";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("fiscalyear", vertifyBonusStatus(tFiscalYear));
		SSRS tSSRS = tExeSQL.execSQL(sqlbv2);
		if (tSSRS == null || tSSRS.GetText(1, 2).compareTo("0") < 0
				|| tSSRS.GetText(1, 3).compareTo("0") < 0
				|| tSSRS.GetText(1, 4).equals("")) {
			CError tCError = new CError();
			tCError.functionName = "vertifyBonusStatus";
			tCError.moduleName = "BonusPreCountService";
			tCError.errorMessage = "上年度的红利分配主表数据有误!";
			mErrors.addOneError(tCError);
			return false;
		}else
		{
			return true;
		}
	}
	/**计算红利系数, 以LCPol 为计算单位*/
	private boolean CountBonusQuotiety(String tFiscalYear, String tContNo,
			GlobalInput tGlobalInput) {

		try
		{
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  红利系数计算开始 !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		tSql = "SELECT *"
				+ " FROM LCPol a"
				+ " WHERE a.ContNo='?tContNo?'"
				+ " and exists (select 'B' from LMRiskApp where RiskCode=a.RiskCode and bonusflag = '1')"
				+ " and not exists (select 'C' from lobonuspol where polno =a.polno and fiscalyear = ?tFiscalYear?) " + " and riskcode not in ("
				+ tBonusCondtion + ")" + " and (a.AppFlag='1' or (a.AppFlag='4' and  exists (select 'T' from LCContState where PolNo=a.PolNo and StateType='Terminate' and State='1' "
			   +" and EndDate is null and StateReason in ('01'))) or (a.AppFlag='4' and  exists (select 'T' from LCContState where PolNo=a.PolNo and StateType='Terminate' and State='1' "
			   +" and EndDate is null and StateReason in ('07')))) " //满期终止的也要进行红利计算
				+ " and not exists (select  'X' from lcconthangupstate d where d.ContNo=a.ContNo and d.hanguptype='2')";// // 可以确定处于保全挂起的不要进行分红。其他的应该不受影响
		logger.debug(tSql);
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("tContNo", tContNo);
		sqlbv2.put("tFiscalYear", tFiscalYear);
		LCPolSet tLCPolSet = null;
		tLCPolSet = new LCPolSet();
		int tSuc = 0, tFail = 0;
		RSWrapper rsWrapper = new RSWrapper();
		//采取长连接的方式
		if (!rsWrapper.prepareData(tLCPolSet, sqlbv2)) {
			this.mErrors.copyAllErrors(rsWrapper.mErrors);
			logger.debug(rsWrapper.mErrors.getFirstError());
			return false;
		}
		do {
			rsWrapper.getData();
			if (tLCPolSet == null || tLCPolSet.size() <= 0) {
				break;
			}
			//根据保单计算红利系数
			String LockNo="";
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				// 数据准备操作
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema = tLCPolSet.get(i);
				
				LockNo = "";
				LockNo= tLCPolSchema.getContNo();
				if (!mPubLock.lock(LockNo, "LB0002", tGlobalInput.Operator)) 
				{
					CError tError = new CError(mPubLock.mErrors.getLastError());
					this.mErrors.addOneError(tError);
					continue;
				}
				VData tVData = new VData();
				VData mResult = new VData();
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("FiscalYear", tFiscalYear);
				tTransferData.setNameAndValue("GroupID", tGroupID);
				tTransferData.setNameAndValue("FiscalYearFlag", this.mFiscalYearFlag);
				BonusPreCountBL tBonusPreCountBL = new BonusPreCountBL();
				tVData.add(tGlobalInput);
				tVData.add(tLCPolSchema);
				tVData.add(tTransferData);
				//业务逻辑处理
				if (!tBonusPreCountBL.submitData(tVData, "BONUS")) {
					this.mErrors.copyAllErrors(tBonusPreCountBL.getErrors());
					break;
				}
				//数据提交
				mResult.clear();
				mResult = tBonusPreCountBL.getResult();
				if (mResult.size() > 0) {
					PubSubmit tSubmit = new PubSubmit();
					if (!tSubmit.submitData(mResult, "")) {
						// @@错误处理
						this.mErrors.copyAllErrors(tSubmit.mErrors);
						break;
					}
				}

				logger.debug("*********************保单红利系数计算完成！保单号："
						+ tLCPolSchema.getContNo() + "*********************");
				mPubLock.unLock();
			}
		} while (tLCPolSet != null && tLCPolSet.size() > 0);
		rsWrapper.close();
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  保单红利系数结束 !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        logger.debug("成功:" + tSuc + "单，失败: " + tFail + "单");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		mPubLock.unLock();
		}


		return true;
	}

	/**计算红利金额，以LOBonusPol 为处理单位
	 * */
	/**计算红利金额，以LOBonusPol 未处理单位*/
	private boolean CountBonusMoney(String tFiscalYear, String tContNo,
			GlobalInput tGlobalInput) {

		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  红利金额计算开始 !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		//查询会计年度前一年的分红方案参数
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select groupid,distributerate,distributevalue,fiscalyear from lobonusmain "
				+ "where fiscalyear = ?fiscalyear?";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("fiscalyear", vertifyBonusStatus(tFiscalYear));
		SSRS tSSRS = tExeSQL.execSQL(sqlbv3);
		if (tSSRS == null || tSSRS.GetText(1, 2).compareTo("0") < 0
				|| tSSRS.GetText(1, 3).compareTo("0") < 0
				|| tSSRS.GetText(1, 4).equals("")) {
			CError tCError = new CError();
			tCError.functionName = "vertifyBonusStatus";
			tCError.moduleName = "BonusPreCountService";
			tCError.errorMessage = "上年度的红利分配主表数据有误!";
			mErrors.addOneError(tCError);
			return false;
		}
        try
        {
		tSql = "SELECT *" + " FROM LOBonusPol a" + " WHERE a.ContNo='?tContNo?'" + " and a.fiscalyear = ?tFiscalYear? and BonusMakeDate is null or BonusMakeDate = '' ";
		logger.debug(tSql);
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("tContNo", tContNo);
		sqlbv4.put("tFiscalYear", tFiscalYear);
		LOBonusPolSet tLOBonusPolSet = null;
		tLOBonusPolSet = new LOBonusPolSet();
		int tSuc = 0, tFail = 0;
		RSWrapper rsWrapper = new RSWrapper();
		//采取长连接的方式
		if (!rsWrapper.prepareData(tLOBonusPolSet, sqlbv4)) {
			this.mErrors.copyAllErrors(rsWrapper.mErrors);
			logger.debug(rsWrapper.mErrors.getFirstError());
			return false;
		}
		do {
			rsWrapper.getData();
			if (tLOBonusPolSet == null || tLOBonusPolSet.size() <= 0) {
				break;
			}
			//根据保单计算红利金额
			String LockNo="";
			for (int i = 1; i <= tLOBonusPolSet.size(); i++) {
				// 数据准备操作
				LOBonusPolSchema tLOBonusPolSchema = new LOBonusPolSchema();
				tLOBonusPolSchema = tLOBonusPolSet.get(i);
				LockNo = "";
				LockNo= tLOBonusPolSchema.getContNo();
				if (!mPubLock.lock(LockNo, "LB0002", tGlobalInput.Operator)) 
				{
					CError tError = new CError(mPubLock.mErrors.getLastError());
					this.mErrors.addOneError(tError);
					continue;
				}
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("FiscalYear", tFiscalYear);
				tTransferData.setNameAndValue("GroupID", tGroupID);
				tTransferData.setNameAndValue("FiscalYearFlag", this.mFiscalYearFlag);
				VData tVData = new VData();
				VData mResult = new VData();
				tVData.add(tGlobalInput);
				tVData.add(tLOBonusPolSchema);
				tVData.add(tTransferData);
				BonusPreCountBL tBonusPreCountBL = new BonusPreCountBL();
				//业务逻辑处理
				if (!tBonusPreCountBL.submitData(tVData, "BONUSM")) {
					this.mErrors.copyAllErrors(tBonusPreCountBL.getErrors());
					break;
				}
				//数据提交
				mResult.clear();
				mResult = tBonusPreCountBL.getResult();
				//红利系数计算和红利计算变为一个事物
				PubSubmit tSubmit = new PubSubmit();
				if (!tSubmit.submitData(mResult, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tSubmit.mErrors);
					break;
				}
				logger.debug("*********************红利金额计算完成！保单号："
						+ tLOBonusPolSchema.getContNo()
						+ "*********************");
				mPubLock.unLock();
			}
		} while (tLOBonusPolSet != null && tLOBonusPolSet.size() > 0);
		rsWrapper.close();
        }catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		mPubLock.unLock();
		}
        

		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  红利金额计算结束 !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		return true;
	}

	/** 查询不参与分红的险种
	 */
	private String getBonusCondtion(String cFiscalYear, String tGroupID) {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tRiskCode = "";
		//查询不参与分红的险种
		String sql = "select riskcode from LoBonusRiskRem where fiscalyear = '?cFiscalYear?' and state='1'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("cFiscalYear", cFiscalYear);

		logger.debug("查询" + cFiscalYear + "的分红方案的计算状态 ： " + sql);
		tSSRS = tExeSQL.execSQL(sqlbv5);
		if (tSSRS != null && tSSRS.getMaxCol() > 0) {
			for (int k = 1; k <= tSSRS.getMaxRow(); k++) {
				tRiskCode += "'" + tSSRS.GetText(k, 1) + "',";
			}
			tRiskCode += "' '";
		}
		return tRiskCode;
	}

	public static void main(String str[]) {
		TransferData tTransferData = new TransferData();
		//测试保单 ContNo=86110020080219001970
		tTransferData.setNameAndValue("FiscalYear", "2008");
		//tTransferData.setNameAndValue("ContNo", "86110020080219001970");
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		BonusPreCountManuBL tBonusPreCountManuBL = new BonusPreCountManuBL();
		tBonusPreCountManuBL.submitData(tVData, "");

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
