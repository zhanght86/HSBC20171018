package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LASPayPersonDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LOPRTManagerSubDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCRnewDelTraceSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LOPRTManagerSubSchema;
import com.sinosoft.lis.vschema.LASPayPersonSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LOPRTManagerSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @author Y
 * @version 1.0
 */
public class IndiDueFeeCancelBL {
private static Logger logger = Logger.getLogger(IndiDueFeeCancelBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private GlobalInput tGI = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate = "";

	private String mSubmitFlag = "";

	private String CurrentDate = PubFun.getCurrentDate();

	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	
	/** 数据表 保存数据 */
	// 个人保单表
	private LCContSchema mLCContSchema = new LCContSchema();

	// 应收个人交费表
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();

	private LASPayPersonSet mLASPayPersonSet = new LASPayPersonSet();

	// 因收总表
	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();

	// 自动续保险种保单的撤销
	private LCPolSet mLCPolSet = new LCPolSet();

	// 打印管理表
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	private LOPRTManagerSubSet mLOPRTManagerSubSet = new LOPRTManagerSubSet();
	
	private LCRnewDelTraceSchema mLCRnewDelTraceSchema = new LCRnewDelTraceSchema();

	private MMap mMMap = new MMap(); //

	private RNHangUp mrn = new RNHangUp(tGI);

	public IndiDueFeeCancelBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) 
	{
		try 
		{
			// 将操作数据拷贝到本类中
			this.mOperate = cOperate;
	
			if (!getInputData(cInputData)) 
			{
				return false;
			}
			// 加锁contno，LR0005：续期催收撤销
			if (!mPubLock.lock(mLCContSchema.getContNo(), "LR0005", tGI.Operator))
			{
				CError tError = new CError(mPubLock.mErrors.getLastError());
				this.mErrors.addOneError(tError);
				return false;
			}
	
			// 进行业务处理
			if (dealData() == false)
			{
				return false;
			}
	
			// 准备数据
			if (!prepareData())
			{
				return false;
			}
			// ====ADD====zhangtao====2005-05-21===========BGN==============================
			if (mSubmitFlag.equals("noSubmit")) 
			{
				return true;
			}
			// ====ADD====zhangtao====2005-05-21===========END==============================
			
			// 公共提交
			//zy 2008-10-28 续期暂收退费需要对抽档进行撤销，直接调用抽档撤销程序，但是不在该程序中进行提交,理赔死亡报案或立案
			//add by xiongzh 09-7-2 续期简易复效调用催收撤销，但是不在此提交。
			//modify by xiongzh 09-11-25 保全撤销应收也返回数据，并不在此提交
			if(  (!"TempDelete".equals(mOperate))&&
				 (!"LiPeiDelete".equals(mOperate))&&
				 (!"RNSpecialA".endsWith(mOperate))&&
				 (!"BQDelete".endsWith(mOperate)) )
			{
				PubSubmit tPubSubmit = new PubSubmit();
				if (tPubSubmit.submitData(mResult, "") == false) 
				{
					return false;
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		} 
		finally 
		{
			mPubLock.unLock();// 解锁
		}
	
	   return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean prepareData() {
		// 准备Map数据-提交
		mMMap.put(mLJSPaySchema, "DELETE");
		mMMap.put(mLJSPayPersonSet, "DELETE");
		mMMap.put(mLASPayPersonSet, "DELETE");

		mMMap.put(mLOPRTManagerSet, "DELETE");
		mMMap.put(mLOPRTManagerSubSet, "DELETE");

		if (mLCPolSet.size() > 0)
			mMMap.put(mLCPolSet, "DELETE");
		
		mMMap.put(mLCRnewDelTraceSchema, "INSERT");

		mResult.add(mMMap);
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

		mLCContSchema = (LCContSchema) mInputData.getObjectByObjectName(
				"LCContSchema", 0);

		if ((tGI == null) || (mLCContSchema == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiDueFeeCancelBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);

			return false;
		}
		// ====ADD====zhangtao====2005-05-21===========BGN==============================
		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		if (tTransferData != null) {
			mSubmitFlag = (String) tTransferData.getValueByName("SubmitFlag");
		}
		// ====ADD====zhangtao====2005-05-21===========END==============================
		return true;
	}

	/**
	 * 准备打印数据
	 * 
	 * @param tLCPolSchema
	 * @return
	 */
	public VData getPrintData(LCPolSchema tLCPolSchema,
			LJSPaySchema tLJSPaySchema) {
		VData tVData = new VData();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		LOPRTManagerSubSchema tLOPRTManagerSubSchema = new LOPRTManagerSubSchema();

		try {
			String tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
			String prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			tLOPRTManagerSchema.setPrtSeq(prtSeqNo);
			tLOPRTManagerSchema.setOtherNo(tLCPolSchema.getPrtNo());
			tLOPRTManagerSchema.setOtherNoType("00");
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PRnewNotice);
			tLOPRTManagerSchema.setManageCom(tLCPolSchema.getManageCom());
			tLOPRTManagerSchema.setAgentCode(tLCPolSchema.getAgentCode());
			tLOPRTManagerSchema.setReqCom(tLCPolSchema.getManageCom());
			tLOPRTManagerSchema.setReqOperator(tLCPolSchema.getOperator());
			tLOPRTManagerSchema.setPrtType("0");
			tLOPRTManagerSchema.setStateFlag("0");
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setOldPrtSeq(prtSeqNo);
			tLOPRTManagerSchema.setStandbyFlag1(tLCPolSchema.getInsuredName());
			tLOPRTManagerSchema.setStandbyFlag2(tLJSPaySchema.getGetNoticeNo());
			tLOPRTManagerSchema.setPatchFlag("0");

			tLOPRTManagerSubSchema.setPrtSeq(prtSeqNo);
			tLOPRTManagerSubSchema.setGetNoticeNo(tLJSPaySchema
					.getGetNoticeNo());
			tLOPRTManagerSubSchema.setOtherNo(tLCPolSchema.getPolNo());
			tLOPRTManagerSubSchema.setOtherNoType("00");
			tLOPRTManagerSubSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLOPRTManagerSubSchema.setDuePayMoney(tLJSPaySchema
					.getSumDuePayMoney());
			tLOPRTManagerSubSchema.setAppntName(tLCPolSchema.getAppntName());
			tLOPRTManagerSubSchema.setTypeFlag("1");
		} catch (Exception ex) {
			return null;
		}

		tVData.add(tLOPRTManagerSchema);
		tVData.add(tLOPRTManagerSubSchema);

		return tVData;
	}
	/**
	 * 检查续保续期
	 * 
	 * @return
	 */
	private boolean dealData() {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLCContSchema.getContNo());
		if (tLCContDB.getInfo() == false) {
			CError.buildErr(this, "保单查询失败");
			return false;
		} else {
			mLCContSchema = tLCContDB.getSchema();
		}

		// 由于合同上仅仅默认了改值是0，所以暂时不做处理
		// if (mLCContSchema.getPayIntv() <= 0) {
		// CError.buildErr(this, "保单缴费间隔不是续期交费，请查询保单详细信息");
		// return false;
		// }

		// 校验是否挂起，保全调用不校验挂起
		if (!mOperate.equals("BQApp") || mOperate == null
				|| mOperate.equals("")) {
			// 理赔立案时被挂起，未知原因，后续查出原因再开放
			// if (!mrn.checkHangUP(mLCContSchema.getContNo())) {
			// CError.buildErr(this, "保单已挂起,不能撤销!");
			// return false;
			// }
		}

		/*
		// 检查附加险
		ExeSQL tExeSQL = new ExeSQL();
		String count = tExeSQL
				.getOneValue("select count(*) from LCRnewStateLog where PrtNo='"
						+ mLCContSchema.getPrtNo()
						+ "' and State not in ('5','6')");
		if (tExeSQL.mErrors.needDealError() || count == null
				|| count.equals("")) {
			CError.buildErr(this, "检查附加险状态失败！", mErrors);
			return false;
		}
		if (!count.equals("0")) {
			CError.buildErr(this, "有附加险续保的催收数据存在，请先做附加险的续保催收撤销！");
			return false;
		}
		*/

		// ==gaoht=======add for 自动续保==
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(mLCContSchema.getContNo());
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet.size() <= 0)
			return false;

		for (int n = 1; n <= tLCPolSet.size(); n++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(n);
			if (tLCPolSchema.getAppFlag().equals("9")) {//9 - 附加险自动续保期间
				mLCPolSet.add(tLCPolSchema);
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql("delete from lcduty where polno = '?polno?'");
				sqlbv.put("polno", tLCPolSchema.getPolNo());
				mMMap.put(sqlbv, "DELETE");
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql("delete from lcprem where polno = '?polno?'");
				sqlbv1.put("polno", tLCPolSchema.getPolNo());
				mMMap.put(sqlbv1, "DELETE");
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql("delete from lcget where polno = '?polno?'");
				sqlbv2.put("polno", tLCPolSchema.getPolNo());
				mMMap.put(sqlbv2, "DELETE");
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql("delete from lcrnewstatehistory where proposalno = '?polno?'");
				sqlbv3.put("polno", tLCPolSchema.getPolNo());
				mMMap.put(sqlbv3, "DELETE");
			}

			// 校验主险是否失效
//			if (tLCPolSchema.getMainPolNo().equals(tLCPolSchema.getPolNo())) {
//				String StateSql = "select * from lccontstate where contno='"
//						+ tLCPolSchema.getContNo()
//						+ "' and polno = '"
//						+ tLCPolSchema.getPolNo()
//						+ "' and StateType in ('Available') and state='1' and StartDate<='"
//						+ CurrentDate + "' and enddate is null";
//				LCContStateDB tLCContStateDB = new LCContStateDB();
//				LCContStateSet tLCContStateSet = new LCContStateSet();
//				tLCContStateSet = tLCContStateDB.executeQuery(StateSql);
//				if (tLCContStateSet.size() > 0) {
//					CError.buildErr(this, "主险失效，不能撤销");
//					return false;
//				}
//			}
		}
		// ==end adding for 自动续保==
		//判断是否有续保二核件，若有，不允许撤销
		int UW_check =0;
		ExeSQL xxExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql("select count(*) from LCRnewStateHistory where " +
				"contno = '?contno?' and state ='2'");
		sqlbv4.put("contno", mLCContSchema.getContNo());
		UW_check= Integer.parseInt(xxExeSQL.getOneValue(sqlbv4) );
		if(UW_check>=1)
		{
			CError.buildErr(this, "该保单"+mLCContSchema.getContNo()+"仍处于人工核保状态，不允许撤销！");
			return false;
		}
		
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(mLCContSchema.getContNo());
		tLJSPaySet = tLJSPayDB.query();
		if (tLJSPaySet.size() <= 0) {
			CError.buildErr(this, "没有查询到保单续期催收的应收数据");
			return false;
		}
		mLJSPaySchema = tLJSPaySet.get(1);
//		if (mLJSPaySchema.getBankOnTheWayFlag() != null) {
//			if (mLJSPaySchema.getBankOnTheWayFlag().equals("1")) {
//				CError.buildErr(this, "应收数据目前银行在途中,不能撤销!");
//				return false;
//			}
//		}

		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		tLJSPayPersonDB.setGetNoticeNo(mLJSPaySchema.getGetNoticeNo());
		mLJSPayPersonSet = tLJSPayPersonDB.query();

		LASPayPersonDB tLASPayPersonDB = new LASPayPersonDB();
		tLASPayPersonDB.setGetNoticeNo(mLJSPaySchema.getGetNoticeNo());
		mLASPayPersonSet = tLASPayPersonDB.query();

		if(!mOperate.equals("TempDelete")) //非暂收费退费程序调用情况下，需要判断暂收费
		{
			//ZY 2009-06-17 修改判断银行在途的逻辑，因为银行回盘成功不修改在途
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			String TempfeeSql = "select * from LJTempFee where TempfeeNo = '?TempfeeNo?'";
//					+ " and ConfDate is null ";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(TempfeeSql);
			sqlbv5.put("TempfeeNo", mLJSPaySchema.getGetNoticeNo());
			logger.debug("查询暂缴费记录==" + TempfeeSql);
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv5);
			if(tLJTempFeeSet.size()>0)
			{
				if("".equals(tLJTempFeeSet.get(1).getConfDate()) || tLJTempFeeSet.get(1).getConfDate()==null)
				{
					if (mOperate != null && mOperate.equals("FinFee")) 
					{
						if (tLJTempFeeSet.get(1).getEnterAccDate().equals("")) 
						{
							CError.buildErr(this, "财务录入暂交费未到账");
							return false;
						}
					} 
					else if (mSubmitFlag != null && mSubmitFlag.equals("NotSure")) 
					{
					} 
					else if (tLJTempFeeSet.size() > 0) 
					{
						CError.buildErr(this, "财务已经录入暂交费,不能撤销！");
						return false;
					}
				}
			}
			else
			{
				if (mLJSPaySchema.getBankOnTheWayFlag() != null) {
				if (mLJSPaySchema.getBankOnTheWayFlag().equals("1")) {
					CError.buildErr(this, "应收数据目前银行在途中,不能撤销!");
					return false;
				}
			}
				
			}
		}

		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		String PrtSql = "select * from LOPRTManager where otherno='?otherno?' and (code in ('47','32') or code in ('35','36')) and othernotype ='02' "
				+ "and ((Standbyflag1='?Standbyflag1?') or (Standbyflag2='?Standbyflag2?'))";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(PrtSql);
		sqlbv6.put("otherno", mLCContSchema.getContNo());
		sqlbv6.put("Standbyflag1", mLJSPaySchema.getGetNoticeNo());
		sqlbv6.put("Standbyflag2", mLJSPaySchema.getGetNoticeNo());
		logger.debug("查询LOPRTManager==" + PrtSql);
		mLOPRTManagerSet = tLOPRTManagerDB.executeQuery(sqlbv6);

		LOPRTManagerSubDB tLOPRTManagerSubDB = new LOPRTManagerSubDB();
		tLOPRTManagerSubDB.setGetNoticeNo(mLJSPaySchema.getGetNoticeNo());
		mLOPRTManagerSubSet = tLOPRTManagerSubDB.query();
		
		//轨迹信息		
		String serNo = ""; //流水号
        String tLimit = PubFun.getNoLimit(tGI.ComCode);
        serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
        mLCRnewDelTraceSchema.setSerialNo(serNo);
        mLCRnewDelTraceSchema.setDelType("01");//续期
        mLCRnewDelTraceSchema.setPrtNo(mLCContSchema.getPrtNo());
        mLCRnewDelTraceSchema.setContNo(mLCContSchema.getContNo());
        mLCRnewDelTraceSchema.setManageCom(tGI.ManageCom);
        mLCRnewDelTraceSchema.setOldOperator(mLJSPaySchema.getOperator());
        mLCRnewDelTraceSchema.setOldMakeDate(mLJSPaySchema.getMakeDate());
        mLCRnewDelTraceSchema.setOldMakeTime(mLJSPaySchema.getMakeTime());
        mLCRnewDelTraceSchema.setMakeDate(PubFun.getCurrentDate());
        mLCRnewDelTraceSchema.setMakeTime(PubFun.getCurrentTime());
        mLCRnewDelTraceSchema.setOperator(tGI.Operator);        

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: MMap
	 */
	public MMap getMap() {
		return mMMap;
	}

	public static void main(String[] args) {
		IndiDueFeeCancelBL IndiDueFeeCancelBL1 = new IndiDueFeeCancelBL();
		LCContSchema tLCPolSchema = new LCContSchema();
		tLCPolSchema.setContNo("BJ050126011000663");

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86";
		tGI.ComCode = "86";

		VData tv = new VData();
		tv.add(tGI);
		tv.add(tLCPolSchema);
		IndiDueFeeCancelBL1.submitData(tv, "BQApp");
	}
}
