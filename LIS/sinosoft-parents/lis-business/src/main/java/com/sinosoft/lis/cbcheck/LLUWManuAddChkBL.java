package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.db.LLUWMasterDB;
import com.sinosoft.lis.db.LLUWPremMasterDB;
import com.sinosoft.lis.db.LLUWSubDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LLCUWMasterSchema;
import com.sinosoft.lis.schema.LLUWMasterSchema;
import com.sinosoft.lis.schema.LLUWPremMasterSchema;
import com.sinosoft.lis.schema.LLUWSubSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LLUWMasterSet;
import com.sinosoft.lis.vschema.LLUWPremMasterSet;
import com.sinosoft.lis.vschema.LLUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLUWManuAddChkBL {
private static Logger logger = Logger.getLogger(LLUWManuAddChkBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	/** 业务数据操作字符串 */
	private String mGrpContNo = "";
	private String mContNo = "";
	private String mAddReason = "";
	private String mUpReporFlag = "";
	private String mClmNo = "";
	private String mBatNo = "";
	private Reflections mReflections = new Reflections();

	/** 执行保全工作流加费活动表任务0000000002 */
	/** 保单表 */
	private LCContSchema mLCContSchema = null;
	private LCPolSet mLCPolSet = new LCPolSet();
	/** 核保主表 */
	private LLUWMasterSet mLLUWMasterSet = new LLUWMasterSet();
	private LLUWSubSet mLLUWSubSet = new LLUWSubSet();
	private LLCUWMasterSchema mLLCUWMasterSchema = new LLCUWMasterSchema();

	private LCDutySet mNewLCDutySet = new LCDutySet();
	private LLUWPremMasterSet mNewLLUWPremMasterSet = new LLUWPremMasterSet();
	private LCPolSet mNewLCPolSet = new LCPolSet();
	private LLUWPremMasterSet mOldLLUWPremMasterSet = new LLUWPremMasterSet();
	/** 保费表 */
	private LLUWPremMasterSet mLLUWPremMasterSet = new LLUWPremMasterSet();

	//暂存修改SQL
	private VData mSQLData = new VData();
	public LLUWManuAddChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start UWManuAddChkBL Submit...");
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("UWManuAddChkBL Submit OK!");

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保特约信息
		if (prepareAdd() == false)
			return false;
		// 合同核保主表是否需要再保标志信息保存
		if (prepareUpReportFlag() == false)
			return false;

		return true;

	}
	
	/**
	 * 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean prepareUpReportFlag() {
//		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
//		tLCCUWMasterDB.setContNo(mContNo);
//		if(!tLCCUWMasterDB.getInfo())
//		{
//			CError.buildErr(this, "合同核保主表查询失败!") ;
//			return false;
//		}
//		if(mUpReporFlag.equals("Y"))
//		{
//			mLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
//			mLCCUWMasterSchema.setSugPassFlag(mUpReporFlag);//是否需要再保呈报标志，下合同核保结论时用2008-12-12 ln add
//			mLCCUWMasterSchema.setOperator(mOperater);
//			mLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
//			mLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
//		}	

		return true;

	}	

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验合同单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContSet == null || tLCContSet.size() <= 0) {
			CError.buildErr(this,"合同" + mContNo + "信息查询失败!");
			return false;
		}
		mLCContSchema = tLCContSet.get(1);
		mGrpContNo = mLCContSchema.getGrpContNo();

		// 校验保单信息
		for(int i=1;i<=this.mLCPolSet.size();i++)
		{
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mLCPolSet.get(i).getPolNo());
			if (!tLCPolDB.getInfo()) {
				CError.buildErr(this,"保单" + mLCPolSet.get(i).getPolNo() + "信息查询失败!");
				return false;
			}
//			LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();
//			tLLUWMasterDB.setPolNo(mLCPolSet.get(i).getPolNo());
//			if (!tLCUWMasterDB.getInfo()) {
//				CError.buildErr(this,"保单" + mLCPolSet.get(i).getPolNo() + "保全批单核保主表信息查询失败!");
//				return false;
//			}
//			mLLUWMasterSet.add(tLCUWMasterDB);
			this.mLCPolSet.get(i).setSchema(tLCPolDB);
		}

		//tongmeng 2008-10-15 add
		//此处需要校验,如果有待打印的核保通知书时,不允许增加加费记录.....
		//校验稍后加~
		//ln 2008-12-9 add
//		String tSQL = "select * from lcissuepol where contno='" + this.mContNo
//			+ "' and state in ('0','1') and needprint = 'Y' and prtseq is not null"
//			+ " and standbyflag2='Y' ";//如果有待打印的核保通知书（甲类）
//		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
//		 LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
//		 tLCIssuePolSet = tLCIssuePolDB.executeQuery(tSQL);
//		//是否有问题件
//		if (tLCIssuePolSet==null || tLCIssuePolSet.size() < 0) 
//		{
//			// @@错误处理
//			CError.buildErr(this,"问题件查询出错！");
//			return false;
//		}
//		if (tLCIssuePolSet.size() > 0) 
//		{
//			// @@错误处理
//			CError.buildErr(this,"有已发送未回收的打印类核保通知书时,不允许增加加费记录！");
//			return false;
//		}
		
		//ln 2008-12-9 add
		//加费评点限制
		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		tLDUWUserDB.setUserCode(mOperater);
		tLDUWUserDB.setUWType("1");
		if(!tLDUWUserDB.getInfo())
		{
			// @@错误处理
			CError.buildErr(this,"LDUWUser查询操作员核保信息出错！");
			return false;
		}
//		int tAddPoint = tLDUWUserDB.getAddPoint();
//		for(int n=1;n<=this.mLLUWPremMasterSet.size();n++)
//		{
//			if(mLLUWPremMasterSet.get(n).getPayPlanType().equals("01")
//					&& mLLUWPremMasterSet.get(n).getAddFeeDirect().equals("01")
//					&& (mLLUWPremMasterSet.get(n).getSuppRiskScore() - tAddPoint > 0)
//					)
//			{
//				// @@错误处理
//				CError.buildErr(this,"加费评点数（"+mLLUWPremMasterSet.get(n).getSuppRiskScore()+"）超过核保权限（"+tAddPoint+"），请重新录入后保存！");
//				return false;
//			}
//		}
		
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData =(TransferData)cInputData.getObjectByObjectName("TransferData",0);
		this.mContNo = (String)mTransferData.getValueByName("ContNo");
		this.mClmNo = (String)mTransferData.getValueByName("ClmNo");
		this.mBatNo = (String)mTransferData.getValueByName("BatNo");
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据失败");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据Operate失败");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据ManageCom失败");
			return false;
		}
		
		if (mClmNo == null || mClmNo.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据ClmNo失败");
			return false;
		}
		if (mBatNo == null || mBatNo.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据BatNo失败");
			return false;
		}

		mOperate = cOperate;

		mLCPolSet = (LCPolSet) cInputData.getObjectByObjectName(
				"LCPolSet", 0);
		mAddReason = (String) cInputData.getObjectByObjectName("String", 0);
		mUpReporFlag = (String) cInputData.getObjectByObjectName("String", 1);

		mLLUWPremMasterSet = (LLUWPremMasterSet) cInputData.getObjectByObjectName("LLUWPremMasterSet",
				0);

		return true;
	}

	/**
	 * 准备特约资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareAdd() {
		double tSumStandPrem = 0;
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();
		
		//循环待加费的险种
		for(int i=1;i<=this.mLCPolSet.size();i++)
		{
			LCPolSchema tempLCPolSchema = new LCPolSchema();
			tempLCPolSchema = mLCPolSet.get(i);
			// 取险种名称
			LMRiskSchema tLMRiskSchema = new LMRiskSchema();
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(tempLCPolSchema.getRiskCode());
			if (!tLMRiskDB.getInfo()) {
				// @@错误处理
				CError.buildErr(this,"取险种名称失败");
				return false;
			}

			// 取代理人姓名
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tempLCPolSchema.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				// @@错误处理
				CError.buildErr(this,"取代理人姓名失败");
				return false;
			}
			//过滤当前保单的加费信息
			LLUWPremMasterSet tempLLUWPremMasterSet = new LLUWPremMasterSet();
			for(int n=1;n<=this.mLLUWPremMasterSet.size();n++)
			{
				if(mLLUWPremMasterSet.get(n).getPolNo().equals(tempLCPolSchema.getPolNo()))
				{
					tempLLUWPremMasterSet.add(mLLUWPremMasterSet.get(n));
				}
			}
			//生成加费信息
			if(mLLUWPremMasterSet.size()>0)
			{
				// 取责任信息
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setPolNo(tempLCPolSchema.getPolNo());
				
				LCDutySet tempLCDutySet = new LCDutySet();
				tempLCDutySet = tLCDutyDB.query();

				// 计算除去本次加费项目,承保时的基本保费项后，该保单在该的加费项目数。以便计算本次加费的编码起始编码值.
				String tsql = "select count(*) from LLUWPremMaster where  polno = '"
						+ "?polno?"
						+ "'  "
						+ " and clmno='"+"?clmno?"+"' and batno='"+"?batno?"+"' "
						+ " and state in ('1','3')";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tsql);
				sqlbv.put("polno", tempLCPolSchema.getPolNo().trim());
				sqlbv.put("clmno", mClmNo);
				sqlbv.put("batno", mBatNo);
				String tReSult = new String();
				ExeSQL tExeSQL = new ExeSQL();
				tReSult = tExeSQL.getOneValue(sqlbv);
				if (tExeSQL.mErrors.needDealError()) {
					// @@错误处理
					CError.buildErr(this,"执行SQL语句：" + tsql + "失败!");
					return false;
				}
				if (tReSult == null || tReSult.equals("")) {
					return false;
				}

				int tCount = 0;
				tCount = Integer.parseInt(tReSult);// 已包括了本次节点及相关同步节点

				// 更新责任项
				if (tempLCDutySet.size() > 0) {
					for (int m = 1; m <= tempLCDutySet.size(); m++) {
						int maxno = 0;
						LCDutySchema tLCDutySchema = new LCDutySchema();
						tLCDutySchema = tempLCDutySet.get(m);

						// 减去该责任的原本次加费金额
						String sql = "select * from LLUWPremMaster where payplancode  like '000000%' and polno = '"
								+ "?polno?"
								+ "' and dutycode = '"
								+ "?dutycode?"
								+ "'and state = '1'";
						SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
						sqlbv1.sql(sql);
						sqlbv1.put("polno", tempLCPolSchema.getPolNo().trim());
						sqlbv1.put("dutycode", tLCDutySchema.getDutyCode().trim());
						LLUWPremMasterDB tLLUWPremMasterDB = new LLUWPremMasterDB();
						LLUWPremMasterSet tLLUWPremMasterSet = new LLUWPremMasterSet();

						tLLUWPremMasterSet = tLLUWPremMasterDB.executeQuery(sqlbv1);

						if (tLLUWPremMasterSet.size() > 0) {
							for (int j = 1; j <= tLLUWPremMasterSet.size(); j++) {
								LLUWPremMasterSchema tLLUWPremMasterSchema;// = new LCPremSchema();
								tLLUWPremMasterSchema = tLLUWPremMasterSet.get(j);

								tLCDutySchema.setPrem(tLCDutySchema.getPrem()
										- tLLUWPremMasterSchema.getPrem());
								tempLCPolSchema.setPrem(tempLCPolSchema.getPrem()
										- tLLUWPremMasterSchema.getPrem());
								mLCContSchema.setPrem(mLCContSchema.getPrem()
										- tLLUWPremMasterSchema.getPrem());
							}
						}

						// 为投保单表和责任表加上本次的加费.同时形成加费信息
						for (int k = 1; k <= tempLLUWPremMasterSet.size(); k++) {
							double tPrem;

							if (tempLLUWPremMasterSet.get(k).getDutyCode().equals(
									tLCDutySchema.getDutyCode())) {
								maxno = maxno + 1;
								// 形成加费编码
								String PayPlanCode = "";
								PayPlanCode = String.valueOf(maxno + tCount);
								for (int j = PayPlanCode.length(); j < 8; j++) {
									PayPlanCode = "0" + PayPlanCode;
								}

								// 保单总保费
								tPrem = tempLCPolSchema.getPrem()
										+ tempLLUWPremMasterSet.get(k).getPrem();
								tSumStandPrem = tSumStandPrem
										+ tempLLUWPremMasterSet.get(k).getPrem();

								tempLLUWPremMasterSet.get(k).setGrpContNo(mGrpContNo);
								tempLLUWPremMasterSet.get(k).setContNo(mContNo);
								tempLLUWPremMasterSet.get(k).setPayPlanCode(PayPlanCode);
								tempLLUWPremMasterSet.get(k).setPayIntv(
										tLCDutySchema.getPayIntv());
								tempLLUWPremMasterSet.get(k).setStandPrem(
										tempLLUWPremMasterSet.get(k).getPrem());
								tempLLUWPremMasterSet.get(k).setSumPrem("0");
								tempLLUWPremMasterSet.get(k).setPaytoDate(
										tempLLUWPremMasterSet.get(k).getPayStartDate());
								tempLLUWPremMasterSet.get(k).setState("1");// 0:承保时的保费项。1:承保时的加费项；2：本次项目加费项
								// 3：前几次不通批单下的加费：
								tempLLUWPremMasterSet.get(k).setUrgePayFlag("Y");// 加费相一定要催交，而不是去取该险种所描述的催交标志。
								tempLLUWPremMasterSet.get(k).setManageCom(
										tempLCPolSchema.getManageCom());
								tempLLUWPremMasterSet.get(k).setAppntNo(
										tempLCPolSchema.getAppntNo());
								tempLLUWPremMasterSet.get(k).setAppntType("1");// 个人投保
								tempLLUWPremMasterSet.get(k).setOperator(mOperater);
								tempLLUWPremMasterSet.get(k).setMakeDate(
										PubFun.getCurrentDate());
								tempLLUWPremMasterSet.get(k).setMakeTime(
										PubFun.getCurrentTime());
								tempLLUWPremMasterSet.get(k).setModifyDate(
										PubFun.getCurrentDate());
								tempLLUWPremMasterSet.get(k).setModifyTime(
										PubFun.getCurrentTime());
							/*	
								tempLCPremSet.get(k).setAddFeeDirect(
										tempLCPremSet.get(k).getAddFeeDirect());
								tempLCPremSet.get(k).setSuppRiskScore(
										tempLCPremSet.get(k).getSuppRiskScore());
							*/
								// 更新保险责任
//								tLCDutySchema.setPrem(tLCDutySchema.getPrem()
//										+ tempLLUWPremMasterSet.get(k).getPrem());
//								// 更新保单数据
//								tempLCPolSchema.setPrem(tPrem);
//								// 更新合同单数据
//								mLCContSchema.setPrem(mLCContSchema.getPrem()
//										+ tempLLUWPremMasterSet.get(k).getPrem());
							}
						}
						mNewLCDutySet.add(tLCDutySchema);
					}
				}
			}
			this.mNewLLUWPremMasterSet.add(tempLLUWPremMasterSet);
			this.mNewLCPolSet.add(tempLCPolSchema);
			
			// 准备删除上一次该项目的加费的数据
			String tSQL = "select * from lluwpremmaster where polno = '" + "?polno?" + "'"
					+ " and substr(payplancode,1,6) = '000000'"
					+ " and state = '1'"
					+ " and clmno='"+"?clmno?"+"' and batno='"+"?batno?"+"'";// 0:承保时的保费项。1:承保时的加费项；2：本次项目加费项
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL);
			sqlbv2.put("polno", tempLCPolSchema.getPolNo());
			sqlbv2.put("clmno", mClmNo);
			sqlbv2.put("batno", mBatNo);
			// 3：前几次不通批单下的加费：
			LLUWPremMasterDB tLLUWPremMasterDB = new LLUWPremMasterDB();
			LLUWPremMasterSet oldPremSet = new LLUWPremMasterSet();
			oldPremSet = tLLUWPremMasterDB.executeQuery(sqlbv2);
			if (oldPremSet == null) {
				// @@错误处理
				CError.buildErr(this,"查询加费信息失败");
				return false;
			}
			this.mOldLLUWPremMasterSet.add(oldPremSet);

			// 准备核保主表信息
			LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();
			tLLUWMasterDB.setPolNo(tempLCPolSchema.getPolNo());
			tLLUWMasterDB.setCaseNo(mClmNo);
			
			if (tLLUWMasterDB.getInfo() == false) {
				// @@错误处理
				CError.buildErr(this,"查询核保主表失败");
				return false;
			}
			LLUWMasterSchema tempLLUWMasterSchema = new LLUWMasterSchema();
			tempLLUWMasterSchema.setSchema(tLLUWMasterDB);
			if (mAddReason != null && !mAddReason.trim().equals("")) {
				tempLLUWMasterSchema.setAddPremReason(mAddReason);
			} else {
				tempLLUWMasterSchema.setAddPremReason("");
			}

			if (tempLLUWPremMasterSet != null && tempLLUWPremMasterSet.size() > 0) {
				tempLLUWMasterSchema.setAddPremFlag("1");// 有加费标识
			} else {
				tempLLUWMasterSchema.setAddPremFlag("0");// 无加费标识
			}

			tempLLUWMasterSchema.setOperator(mOperater);
			tempLLUWMasterSchema.setManageCom(mManageCom);
			tempLLUWMasterSchema.setModifyDate(tCurrentDate);
			tempLLUWMasterSchema.setModifyTime(tCurrentTime);
			this.mLLUWMasterSet.add(tempLLUWMasterSchema);
			
			// 准备核保子表信息
			LLUWSubDB tLLUWSubDB = new LLUWSubDB();
			String sql = "select * from lluwsub where PolNo='"+"?PolNo?"+"' "
							+" and caseno='"+"?caseno?"+"' and batno='"+"?batno?"+"' "
							+" order by uwno desc";		
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sql);
			sqlbv3.put("PolNo", tempLLUWMasterSchema.getPolNo());
			sqlbv3.put("caseno", mClmNo);
			sqlbv3.put("batno", mBatNo);
			LLUWSubSet tLLUWSubSet = tLLUWSubDB.executeQuery(sqlbv3);
			if(tLLUWSubSet==null)
			{
				CError.buildErr(this,"查询险种核保子表出错!");
				return false;
			}
			LLUWSubSchema tempLLUWSubSchema = new LLUWSubSchema();
			tempLLUWSubSchema = tLLUWSubSet.get(1);
			tempLLUWSubSchema.setAddPremReason(tempLLUWMasterSchema.getAddPremReason());
			tempLLUWSubSchema.setAddPremFlag(tempLLUWMasterSchema.getAddPremFlag());

			tempLLUWSubSchema.setUWNo(tLLUWSubSet.get(1).getUWNo()+i);
			tempLLUWSubSchema.setOperator(mOperater);
			tempLLUWSubSchema.setManageCom(mManageCom);
			tempLLUWSubSchema.setModifyDate(tCurrentDate);
			tempLLUWSubSchema.setModifyTime(tCurrentTime);
			this.mLLUWSubSet.add(tempLLUWSubSchema);
			
			//tongmeng 2008-12-04 add
			//如果没有加费记录的话,需要修改lcduty和,lcpol,lccont的数据.
//			String tSQL_Duty = "update lcduty a set prem = (select nvl(sum(prem),0) from lcprem where polno=a.polno) "
//				             + " where polno='"+tempLCPolSchema.getPolNo()+"' "; 
//			logger.debug("tSQL_Duty:"+tSQL_Duty);
//			String tSQL_LCPol = "update lcpol a set prem = (select nvl(sum(prem),0) from lcduty where polno=a.polno) "
//				         //     + " ,sumprem = (select nvl(sum(prem),0) from lcprem where polno=a.polno) "
//				              + " where polno = '"+tempLCPolSchema.getPolNo()+"' ";
//			logger.debug("tSQL_LCPol:"+tSQL_LCPol);
//			mSQLData.add(tSQL_Duty);
//			mSQLData.add(tSQL_LCPol);
		}
		
/*
		
		// 准备添加该项目的批改补退费表中人工核保加费的数据
		if (tSumStandPrem > 0 && mLCPremSet != null && mLCPremSet.size() > 0) {
			for (int i = 1; i <= mLCPremSet.size(); i++) {
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tLCDutyDB.setDutyCode(mLCPremSet.get(i).getDutyCode());
				tLCDutyDB.setPolNo(mLCPremSet.get(i).getPolNo());
				LCDutySet tLCDutySet = tLCDutyDB.query();
				if (tLCDutySet == null) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "UWManuAddChkBL";
					tError.functionName = "prepareAdd";
					tError.errorMessage = "查询责任表信息失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLCDutySchema = tLCDutySet.get(1);
				String tDutyPaytoDate = tLCDutySchema.getPaytoDate();
				String tPremPaytoDate = mLCPremSet.get(i).getPaytoDate();
			}
		}

	*/	
   /*
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		if (tLCCUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError.buildErr(this,"无合同单核保主表信息");
			return false;
		}
		mLCCUWMasterSchema.setSchema(tLCCUWMasterDB);
		if (mAddReason != null && !mAddReason.trim().equals("")) {
			mLCCUWMasterSchema.setAddPremReason(mAddReason);
		} else {
			mLCCUWMasterSchema.setAddPremReason("");
		}

		if (mLCPremSet != null && mLCPremSet.size() > 0) {
			mLCCUWMasterSchema.setAddPremFlag("1");// 有加费标识
		} else {
			mLCCUWMasterSchema.setAddPremFlag("0");// 无加费标识
		}

		mLCCUWMasterSchema.setOperator(mOperater);
		mLCCUWMasterSchema.setManageCom(mManageCom);
		mLCCUWMasterSchema.setModifyDate(tCurrentDate);
		mLCCUWMasterSchema.setModifyTime(tCurrentTime);
*/
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 删除上一个加费数据
		if (mOldLLUWPremMasterSet != null && mOldLLUWPremMasterSet.size() > 0) {
			map.put(mOldLLUWPremMasterSet, "DELETE");
		}

		// 添加本次加费数据
		if (mNewLLUWPremMasterSet != null && mNewLLUWPremMasterSet.size() > 0) {
			map.put(mNewLLUWPremMasterSet, "INSERT");
		}

		// 修改本次加费后更新的保单责任数据
//		if (mNewLCDutySet != null && mNewLCDutySet.size() > 0) {
//			map.put(mNewLCDutySet, "UPDATE");
//		}

		// 修改本次加费后更新的保单数据
//		if (mNewLCPolSet != null) {
//			map.put(mNewLCPolSet, "UPDATE");
//		}

		// 修改本次加费后更新的合同单数据
//		if (mLCContSchema != null) {
//			map.put(mLCContSchema, "UPDATE");
//		}

		// 更新批单核保主表
		map.put(mLLUWMasterSet, "UPDATE");
		
		// 更新批单核保主表
		map.put(mLLUWSubSet, "INSERT");
		
		//修改SQL
		for(int i=0;i<this.mSQLData.size();i++)
		{
			String tSQL = (String)this.mSQLData.get(i);
			map.put(tSQL, "UPDATE");
		}
//		String tSQL_Cont = "update lccont a set prem=(select nvl(sum(prem),0) from lcpol where contno=a.contno) "
//			            // + " ,sumprem = (select nvl(sum(prem),0) from lcpol where contno=a.contno) " 
//						 + " where contno='"+this.mContNo+"' ";
//		logger.debug("tSQL_Cont:"+tSQL_Cont);
//		
//		map.put(tSQL_Cont, "UPDATE");

		// 更新合同单核保主表
//		map.put(mLLCUWMasterSchema, "UPDATE");

		if(mLLCUWMasterSchema!=null)
//			// 更新合同单核保主表
			map.put(mLLCUWMasterSchema, "UPDATE");
		
		mResult.add(map);
		return true;
	}

	/*
	 * public VData getResult() { return mResult; }
	 * 
	 * public TransferData getReturnTransferData() { return mTransferData; }
	 */// zhr
	public CErrors getErrors() {
		return mErrors;
	}
}
