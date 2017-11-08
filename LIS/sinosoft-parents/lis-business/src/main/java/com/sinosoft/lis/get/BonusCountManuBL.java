package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

/**
 * <p>Title: 红利计算手工触发批处理</p>
 * <p>Description: 
 *  根据红利主表状态进行红利的计算,包括红利系数计算以及红利的计算
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author  pst
 * @version 1.0
 */


import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
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

public class BonusCountManuBL implements BusinessService{
private static Logger logger = Logger.getLogger(BonusCountManuBL.class);
	public BonusCountManuBL() {
	}
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/**选数SQL，循环处理，筛选一部分保单，查询出本保单下没有终止的，但可以是失效的所有的分红险，
	 * 但由于没有添加保单年期限制，会多选出很多保单,这种分段计算红利的方式要求数据要绝对稳定！
	 * */
	private String tSql = "";	
	/** 封装要操作的数据，以便一次提交 */
	/** 往后面传输数据的容器 */
	private VData mInputData;
    /**处理的机构*/
	private GlobalInput tGlobalInput=null;
	
	private TransferData mTransferData = new TransferData();
	/**分红年度*/
	private String tFiscalYear;
	/**红利分配组号*/
	private String tGroupID;
    /**存放不参与分红那个的险种，其格式为 '211014','254056',''*/
	private String tBonusCondtion;
	/**tComputerState=“4”，说明红利系数计算完毕，而且代精算将红利主表信息维护完毕后，其进行红利金额计算，
	   tComputerState=“2”，说明本年度本组还没有进行红利系数进行，需要进行红利系数计算*/
	private  String tComputerState="";

	/** 数据操作字符串 */
	private String mOperate;	
	private String tCurMakeDate = PubFun.getCurrentDate();
	private String tCurMakeTime = PubFun.getCurrentTime();
	
	private ExeSQL tExeSQL = new ExeSQL();
	
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	
	public  String mContent="";
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
		this.mResult.add(this.mContent);
		return true;
	}
	private boolean getInputData() {
		// 全局变量
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		tFiscalYear = (String) mTransferData.getValueByName("FiscalYear");
		// 如果会计年度录入直接将其返回
		if (tFiscalYear == null || "".equals(tFiscalYear)) {
			return false;
		}
		tGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (tGlobalInput == null) {
			tGlobalInput= new GlobalInput();
			tGlobalInput.Operator = "001";
			tGlobalInput.ManageCom = "86";
		}
		return true;
	}
	public boolean dealData() {
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  Insurance dividends Start ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		// 红利计算组,如果为空则将其置为 ”1“,现在都为 “1” ，以后可能要分离
		if (tGroupID == null || "".equals(tGroupID)) {
			tGroupID = "1";
		}


		// 校验保单红利主表状态
		if (!vertifyBonusStatus(tFiscalYear)) {
			return false;
		}

		tComputerState = getState(tFiscalYear, tGroupID);
		// tComputerState=“4”，说明红利系数计算完毕，而且精算将其进行红利金额计算，
		// tComputerState=“2”，说明本年度本组还没有进行红利系数进行，需要进行红利系数计算
		if ("".equals(tComputerState)) {
			return false;
		}
		tBonusCondtion=getBonusCondtion(tFiscalYear, tGroupID);
		if("".equals(tBonusCondtion))
		{
			tBonusCondtion="''";
		}
		
		// 即再计算红利时，能够进行红利计算的保单应该和计算红利系数的保单保持一致
		// 计算红利系数
		if ("2".equals(tComputerState)) {
			if (!CountBonusQuotiety(tFiscalYear,
					tGlobalInput)) {
				return false;
			}
		}

		if ("4".equals(tComputerState)) {
			// 根据先前红利系数再计算红利，但必须进行关键性校验，即红利主表关键信息已经由精算进行维护
			if (!CountBonusMoney(tFiscalYear, tGlobalInput)) {
				return false;
			}
		}
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  Insurance dividends End ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		return true;
	}

	/**
	 * 校验该保单的分红情况，如果该保单已经计算过红利则不再计算
	 * @param cFiscalYear : 接收传入的会计年度值
	 * @return
	 */
	private boolean vertifyBonusStatus(String cFiscalYear) {
		String sql;
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = null;		
			//查询待计算会计年度的红利分配方案是否出台
			sql = "select groupid from lobonusmain  where fiscalyear = '?cFiscalYear?'  and computeState in ('2','4') ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("cFiscalYear", cFiscalYear);
			logger.debug("查询" + cFiscalYear + "的分红方案是否出台 ： " + sql);
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS==null || tSSRS.MaxRow <= 0) {
				CError tCError = new CError();
				tCError.functionName = "vertifyBonusStatus";
				tCError.moduleName = "BonusCountManuBL";
				tCError.errorMessage = "红利分配主表的关键数据有误!";
				mErrors.addOneError(tCError);
				return false;
			}
			tGroupID=tSSRS.GetText(1, 1);
		return true;
	}

	/**计算红利系数, 以LCPol 为计算单位
	 *  tComputerState=“4”，说明红利系数计算完毕，而且精算将其进行红利金额计算，
		tComputerState=“2”，说明本年度本组还没有进行红利系数进行，需要进行红利系数计算
		*/
	private boolean CountBonusQuotiety(String tFiscalYear, GlobalInput tGlobalInput) {
		
		try {
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ Insurance dividends Count Rate Start!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");	
		String tEndDate=tFiscalYear+"-12-31";
		tSql = "SELECT *"
				+ " FROM LCPol a"
				+ " WHERE not exists(select 'A' from LCContState where PolNo=a.PolNo and StateType='Terminate' and State='1' and EndDate is null)"
				+ " and exists (select 'B' from LMRiskApp where RiskCode=a.RiskCode and bonusflag = '1')"
				+ " and not exists (select 'C' from lobonuspol where polno =a.polno and fiscalyear = ?tFiscalYear?)  and "
				+ " riskcode not in (?tBonusCondtion?)"
				+ " and a.AppFlag='1' and  a.cvalidate <= '?tEndDate?' ";
		logger.debug(tSql);
		LCPolSet tLCPolSet = null;
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("tFiscalYear", tFiscalYear);
		sqlbv1.put("tBonusCondtion", tBonusCondtion);
		sqlbv1.put("tEndDate", tEndDate);
		tLCPolSet = new LCPolSet();
		int tSuc = 0, tFail = 0;
		RSWrapper rsWrapper = new RSWrapper();
		//采取长连接的方式
		if (!rsWrapper.prepareData(tLCPolSet, sqlbv1)) {
			
			this.mErrors.copyAllErrors(rsWrapper.mErrors);
			logger.debug(rsWrapper.mErrors.getFirstError());
			return false;
		}
		do {
			rsWrapper.getData();
			if (tLCPolSet == null || tLCPolSet.size() <= 0) {
				break;
			}
			String LockNo= "";
			//根据保单计算红利系数
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
				BonusCountBL tBonusCountBL = new BonusCountBL();
				tVData.add(tGlobalInput);
				tVData.add(tLCPolSchema);
				tVData.add(tTransferData);
				//业务逻辑处理
				if (!tBonusCountBL.submitData(tVData, "BONUS")) {
					this.mErrors.copyAllErrors(tBonusCountBL.getErrors());
					tFail++;
					continue;
				}
				//数据提交
				mResult.clear();
				mResult = tBonusCountBL.getResult();
				if(mResult.size()>0)
				{
					PubSubmit tSubmit = new PubSubmit();
					if (!tSubmit.submitData(mResult, "")) {
						// @@错误处理
						this.mErrors.copyAllErrors(tSubmit.mErrors);
						tFail++;
						continue;
					}					
				}

				tSuc++;
				logger.debug("*********************保单红利系数计算完成！保单号："+ tLCPolSchema.getContNo() + "*********************");
				mPubLock.unLock();
			}
		} while (tLCPolSet != null && tLCPolSet.size() > 0);
		rsWrapper.close();
		this.mContent="成功:" + tSuc + "单，失败: " + tFail + "单";
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝   Insurance dividends Count Rate End!!!  !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
         logger.debug("成功:" + tSuc + "单，失败: " + tFail + "单");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			mPubLock.unLock();
		}
         
         
         VData rResult = new VData();
		PubSubmit rSubmit = new PubSubmit();
		
		//累积红利主表信息,只汇总计算时有效的保单
        String strSQL="select sum(BonusCoef) from LOBonusPol "
            +"where fiscalyear = ?tFiscalYear? and ValiFlag='1' and GroupID = ?tGroupID?" ;
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(strSQL);
        sqlbv3.put("tFiscalYear", tFiscalYear);
        sqlbv3.put("tGroupID", tGroupID);
        ExeSQL tExeSQL = new ExeSQL();
        String  tSumBonusCoef=tExeSQL.getOneValue(sqlbv3);
        if("".equals(tSumBonusCoef))
        {
			return false;
        }
        MMap mMap = new MMap();
		//所有保单红利系数计算完成，红利主表计算状态为”3“
		String tUPDateSQL="update LOBonusMain set state='0',ComputeState='3',BonusCoefSum=?tSumBonusCoef?,ModifyDate='?tCurMakeDate?',ModifyTime='?tCurMakeTime?'"
                          +" where GroupID=?tGroupID? and FiscalYear=?tFiscalYear?";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tUPDateSQL);
		sqlbv2.put("tSumBonusCoef", tSumBonusCoef);
		sqlbv2.put("tCurMakeDate", tCurMakeDate);
		sqlbv2.put("tCurMakeTime", tCurMakeTime);
		sqlbv2.put("tGroupID", tGroupID);
		sqlbv2.put("tFiscalYear", tFiscalYear);
		mMap.put(sqlbv2, "UPDATE");
		rResult.clear();
		rResult.add(mMap);
		if (!rSubmit.submitData(rResult, "")) {
			this.mErrors.copyAllErrors(rSubmit.mErrors);
			return false;
		}		

		return true;
	}

	/**计算红利金额，以LOBonusPol 为处理单位
	 *  tComputerState=“4”，说明红利系数计算完毕，而且精算将其进行红利金额计算，
		tComputerState=“2”，说明本年度本组还没有进行红利系数进行，需要进行红利系数计算
	 * */
	private boolean CountBonusMoney(String tFiscalYear, GlobalInput tGlobalInput) {
		
		try
		{
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝Insurance dividends Cout Bonus Start!!!＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		tSql = "SELECT *"
				+ " FROM LOBonusPol a"
				+ " WHERE a.fiscalyear = ?tFiscalYear? and (BonusMakeDate is null or BonusMakeDate = '') "
			    +" ";
		logger.debug(tSql);
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(tSql);
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
			//
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
				
				VData tVData = new VData();
				VData mResult = new VData();
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("FiscalYear", tFiscalYear);
				tTransferData.setNameAndValue("GroupID", tGroupID);
				tVData.add(tGlobalInput);
				tVData.add(tLOBonusPolSchema);
				tVData.add(tTransferData);

				BonusCountBL tBonusCountBL = new BonusCountBL();
				//业务逻辑处理
				if (!tBonusCountBL.submitData(tVData, "BONUSM")) {
					this.mErrors.copyAllErrors(tBonusCountBL.getErrors());
					tFail++;
					continue;
				}
				
				//数据提交
				mResult.clear();
				mResult = tBonusCountBL.getResult();
				PubSubmit tSubmit = new PubSubmit();
				if (!tSubmit.submitData(mResult, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tSubmit.mErrors);
					continue;
				}
				tSuc++;
				logger.debug("*********************红利金额计算完成！保单号："
						+ tLOBonusPolSchema.getContNo() + "*********************");
				mPubLock.unLock();
			}
		} while (tLOBonusPolSet != null && tLOBonusPolSet.size() > 0);
		rsWrapper.close();
		this.mContent="成功:" + tSuc + "单，失败: " + tFail + "单";
		logger.debug("成功:" + tSuc + "单，失败: " + tFail + "单");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			mPubLock.unLock();
			
		}
        
        VData rResult = new VData();
		PubSubmit rSubmit = new PubSubmit();
		MMap mMap = new MMap();
		
		//所有保单红利金额计算完成，红利主表计算状态为”5“
		String tUPDateSQL="update LOBonusMain set ComputeState='5',ModifyDate='?tCurMakeDate?',ModifyTime='?tCurMakeTime?'"
                         +" where GroupID=?tGroupID? and FiscalYear=?tFiscalYear?";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(tUPDateSQL);
		sqlbv5.put("tCurMakeDate", tCurMakeDate);
		sqlbv5.put("tCurMakeTime", tCurMakeTime);
		sqlbv5.put("tGroupID", tGroupID);
		sqlbv5.put("tFiscalYear", tFiscalYear);
		mMap.put(sqlbv5, "UPDATE");
		rResult.clear();
		rResult.add(mMap);
		if (!rSubmit.submitData(rResult, "")) {
			this.mErrors.copyAllErrors(rSubmit.mErrors);
			return false;
		}
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  Insurance dividends Cout Bonus End ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		return true;
	}

	/** 获取红利主表的计算状态标记
	 * 	tComputerState=“4”，说明红利系数计算完毕，而且精算将其进行红利金额计算，
		tComputerState=“2”，说明本年度本组还没有进行红利系数进行，需要进行红利系数计算
		*/
    private String getState(String cFiscalYear,String tGroupID)
    {
    	ExeSQL tExeSQL = new ExeSQL();
		//查询待计算会计年度的红利分配方案是否出台
		String sql = "select Computestate from LOBonusMain where fiscalyear = '?cFiscalYear?' and Groupid='?tGroupID?'";
		
		logger.debug("查询" + cFiscalYear + "的分红方案的计算状态 ： " + sql);
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sql);
		sqlbv6.put("cFiscalYear", cFiscalYear);
		sqlbv6.put("tGroupID", tGroupID);
		String tFlag = tExeSQL.getOneValue(sqlbv6);
		if ("".equals(tFlag)) {
			CError tCError = new CError();
			tCError.functionName = "vertifyBonusStatus";
			tCError.moduleName = "BonusCountManuBL";
			tCError.errorMessage = "分红方案的计算状态出错";
			mErrors.addOneError(tCError);
			return "";
		}else
		{
			return tFlag;
		}
    }
    
	/** 查询不参与分红的险种
		*/
    private String getBonusCondtion(String cFiscalYear,String tGroupID)
    {
    	SSRS tSSRS = new SSRS();
    	String tRiskCode="";
       //查询不参与分红的险种
		String sql = "select riskcode from LoBonusRiskRem where fiscalyear = '?cFiscalYear?' and state='0'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(sql);
		sqlbv7.put("cFiscalYear", cFiscalYear);
		
		logger.debug("查询" + cFiscalYear + "的分红方案的计算状态 ： " + sql);
		tSSRS= tExeSQL.execSQL(sqlbv7);
		tSSRS= tExeSQL.execSQL(sqlbv7);
		if (tSSRS!=null && tSSRS.getMaxCol()>0) {			
			for (int k=1;k<=tSSRS.getMaxRow();k++)
			{
				tRiskCode+="'"+tSSRS.GetText(k, 1)+"',";
			}
			tRiskCode+="' '";
		}
		return tRiskCode;
    }
    
	public static void main(String str[]) {
		  TransferData tTransferData = new TransferData();
		  //测试保单 ContNo=86110020080219002444
		  tTransferData.setNameAndValue("ContNo","86110020080219002444");
		  tTransferData.setNameAndValue("FiscalYear","2007");
		  VData tVData = new VData();   
		  tVData.addElement(tTransferData);
		  BonusCountManuBL tBonusCountManuBL = new BonusCountManuBL();
		  tBonusCountManuBL.submitData(tVData,"");

	}
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}
}
