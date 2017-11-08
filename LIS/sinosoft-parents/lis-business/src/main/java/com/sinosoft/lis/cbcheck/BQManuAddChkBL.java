package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.bq.PEdorDiscountCalBL;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDiscountDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.db.LPUWMasterDB;
import com.sinosoft.lis.db.LPUWSubDB;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDiscountSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.schema.LPUWMasterSchema;
import com.sinosoft.lis.schema.LPUWSubSchema;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDiscountSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.lis.vschema.LPUWMasterSet;
import com.sinosoft.lis.vschema.LPUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BQManuAddChkBL {
private static Logger logger = Logger.getLogger(BQManuAddChkBL.class);

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
	private String mEdorType = "";
	private String mEdorNo = "";
	private Reflections mReflections = new Reflections();

	/** 执行保全工作流加费活动表任务0000000002 */
	/** 保单表 */
	private LPContSchema mLPContSchema = null;
	private LPPolSet mLPPolSet = new LPPolSet();
	/** 核保主表 */
	private LPUWMasterSet mLPUWMasterSet = new LPUWMasterSet();
	private LPUWSubSet mLPUWSubSet = new LPUWSubSet();
	private LPCUWMasterSchema mLPCUWMasterSchema = new LPCUWMasterSchema();

	private LPDutySet mNewLPDutySet = new LPDutySet();
	private LPPremSet mNewLPPremSet = new LPPremSet();
	private LPPolSet mNewLPPolSet = new LPPolSet();
	private LPPremSet mOldLPPremSet = new LPPremSet();
	private String mSQL_LJSGetEndorsePrem = "";
	private String mSQL_LJSGetEndorsePremZK = "";
	private String mSQL_LJSGetEndorseLX = "";
	private LJSGetEndorseSet aLJSGetEndorseSet = new LJSGetEndorseSet();
	private LJSGetEndorseSet aZKLJSGetEndorseSet = new LJSGetEndorseSet();
	/** 保费表 */
	private LPPremSet mLPPremSet = new LPPremSet();
	private SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
	private SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
	private SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();

	
	//暂存修改SQL
	private VData mSQLData = new VData();
	public BQManuAddChkBL() {
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
			CError.buildErr(this, "数据提交失败!") ;
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
		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setContNo(mContNo);
		tLPCUWMasterDB.setEdorNo(mEdorNo);
		tLPCUWMasterDB.setEdorType(mEdorType);
		if(!tLPCUWMasterDB.getInfo())
		{
			CError.buildErr(this, "合同核保主表查询失败!") ;
			return false;
		}
		if(mUpReporFlag.equals("Y"))
		{
			mLPCUWMasterSchema = tLPCUWMasterDB.getSchema();
			mLPCUWMasterSchema.setSugPassFlag(mUpReporFlag);//是否需要再保呈报标志，下合同核保结论时用2008-12-12 ln add
			mLPCUWMasterSchema.setOperator(mOperater);
			mLPCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			mLPCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		}	

		return true;

	}	

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验合同单信息
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setContNo(mContNo);
		LPContSet tLPContSet = tLPContDB.query();
		if (tLPContSet == null || tLPContSet.size() <= 0) {
			CError.buildErr(this,"合同" + mContNo + "信息查询失败!");
			return false;
		}
		mLPContSchema = tLPContSet.get(1);
		mGrpContNo = mLPContSchema.getGrpContNo();

		// 校验保单信息
		for(int i=1;i<=this.mLPPolSet.size();i++)
		{
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setPolNo(mLPPolSet.get(i).getPolNo());
			tLPPolDB.setEdorNo(mLPPolSet.get(i).getEdorNo());
			tLPPolDB.setEdorType(mLPPolSet.get(i).getEdorType());
			if (!tLPPolDB.getInfo()) {
				CError.buildErr(this,"保单" + mLPPolSet.get(i).getPolNo() + "信息查询失败!");
				return false;
			}
			LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
			tLPUWMasterDB.setPolNo(mLPPolSet.get(i).getPolNo());
			tLPUWMasterDB.setEdorNo(mLPPolSet.get(i).getEdorNo());
			tLPUWMasterDB.setEdorType(mLPPolSet.get(i).getEdorType());
			if (!tLPUWMasterDB.getInfo()) {
				CError.buildErr(this,"保单" + mLPPolSet.get(i).getPolNo() + "保全批单核保主表信息查询失败!");
				return false;
			}
			//mLPUWMasterSet.add(tLPUWMasterDB);
			this.mLPPolSet.get(i).setSchema(tLPPolDB);
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
		int tAddPoint = tLDUWUserDB.getAddPoint();
		for(int n=1;n<=this.mLPPremSet.size();n++)
		{
			if(mLPPremSet.get(n).getPayPlanType().equals("01")
					&& mLPPremSet.get(n).getAddFeeDirect().equals("01")
					&& (mLPPremSet.get(n).getSuppRiskScore() - tAddPoint > 0)
					)
			{
				// @@错误处理
				CError.buildErr(this,"加费评点数（"+mLPPremSet.get(n).getSuppRiskScore()+"）超过核保权限（"+tAddPoint+"），请重新录入后保存！");
				return false;
			}
		}
		
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
		this.mEdorType = (String)mTransferData.getValueByName("EdorType");
		this.mEdorNo = (String)mTransferData.getValueByName("EdorNo");
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

		mOperate = cOperate;

		mLPPolSet = (LPPolSet) cInputData.getObjectByObjectName(
				"LPPolSet", 0);
		mAddReason = (String) cInputData.getObjectByObjectName("String", 0);
		mUpReporFlag = (String) cInputData.getObjectByObjectName("String", 1);

		mLPPremSet = (LPPremSet) cInputData.getObjectByObjectName("LPPremSet",
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
		for(int i=1;i<=this.mLPPolSet.size();i++)
		{
			LPPolSchema tempLPPolSchema = new LPPolSchema();
			tempLPPolSchema = mLPPolSet.get(i);
			// 取险种名称
			LMRiskSchema tLMRiskSchema = new LMRiskSchema();
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(tempLPPolSchema.getRiskCode());
			if (!tLMRiskDB.getInfo()) {
				// @@错误处理
				CError.buildErr(this,"取险种名称失败");
				return false;
			}

			// 取代理人姓名
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tempLPPolSchema.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				// @@错误处理
				CError.buildErr(this,"取代理人姓名失败");
				return false;
			}
			//add by ln 2009-4-7 
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setEdorNo(tempLPPolSchema.getEdorNo());
			tLPEdorItemDB.setEdorType(tempLPPolSchema.getEdorType());
			tLPEdorItemDB.setContNo(tempLPPolSchema.getContNo());
			LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
			if(tLPEdorItemSet==null || tLPEdorItemSet.size()<=0)
			{
				CError.buildErr(this,"LPEdorItem查询失败");
				return false;
			}
			tLPEdorItemSchema = tLPEdorItemSet.get(1);
			
			//过滤当前保单的加费信息
			LPPremSet tempLPPremSet = new LPPremSet();
			for(int n=1;n<=this.mLPPremSet.size();n++)
			{
				if(mLPPremSet.get(n).getPolNo().equals(tempLPPolSchema.getPolNo()))
				{
					tempLPPremSet.add(mLPPremSet.get(n));
				}
			}
			//生成加费信息
			if(tempLPPremSet.size()>0)
			{
				// 取责任信息
				LPDutyDB tLPDutyDB = new LPDutyDB();
				tLPDutyDB.setEdorNo(tempLPPolSchema.getEdorNo());
				tLPDutyDB.setContNo(tempLPPolSchema.getContNo());
				tLPDutyDB.setPolNo(tempLPPolSchema.getPolNo());
				
				LPDutySet tempLPDutySet = new LPDutySet();
				tempLPDutySet = tLPDutyDB.query();

				// 计算除去本次加费项目,承保时的基本保费项后，该保单在该的加费项目数。以便计算本次加费的编码起始编码值.
				String tsql = "select count(*) from LPPrem where edorno='"+"?edorno?"+"' and polno = '"
						+ "?polno?"
						+ "'  and state in ('1','3')";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tsql);
				sqlbv.put("edorno", tempLPPolSchema.getEdorNo());
				sqlbv.put("polno", tempLPPolSchema.getPolNo().trim());
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
				
				//查询交至日期
				String tMainPaytoDate = null;
				//tsql = "select max(paytodate) from lpprem where polno = (select mainpolno from lcpol where polno = '"+tempLPPolSchema.getPolNo()+"') and edorno = '"+tempLPPolSchema.getEdorNo()+"'";
				//排除增额交清的情况。
				tsql = "select to_char(max(paytodate),'YYYY-MM-DD') from lpprem where length(dutycode)<7 and polno = (select mainpolno from lcpol where polno = '"+"?polno?"+"') and edorno in (select edorno from lpprem where polno=(select mainpolno from lcpol where polno = '"+"?polno?"+"') and contno='"+"?contno?"+"')";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tsql);
				sqlbv1.put("polno", tempLPPolSchema.getPolNo());
				sqlbv1.put("contno", mContNo);
				tMainPaytoDate = tExeSQL.getOneValue(sqlbv1);
				if (tMainPaytoDate == null || tMainPaytoDate.equals("")) {
					CError.buildErr(this,"查询主险保费交至日期失败！");
					return false;
				}
				
				int tCount = 0;
				tCount = Integer.parseInt(tReSult);// 已包括了本次节点及相关同步节点

				// 更新责任项
				if (tempLPDutySet.size() > 0) {
					for (int m = 1; m <= tempLPDutySet.size(); m++) {
						int maxno = 0;
						LPDutySchema tLPDutySchema = new LPDutySchema();
						tLPDutySchema = tempLPDutySet.get(m);					

						// 为投保单表和责任表加上本次的加费.同时形成加费信息
						for (int k = 1; k <= tempLPPremSet.size(); k++) {
							double tPrem;

							if (tempLPPremSet.get(k).getDutyCode().equals(
									tLPDutySchema.getDutyCode())) {
								
								if(!tempLPPremSet.get(k).getAddForm().equals("4") )//不是维持原条件
								{
									// 减去该责任的原本次加费金额
									String sql = "select * from LPPrem where edorno='"+"?edorno?"+"' and payplancode  like '000000%' and polno = '"
											+ "?polno?"
											+ "' and dutycode = '"
											+ "?dutycode?"
											+ "' and payplantype='"+"?payplantype?"+"' "
											+ " and (addform is null or addform <> '3') and state = '1'";//不包括终止的加费
									SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
									sqlbv2.sql(sql);
									sqlbv2.put("edorno", tempLPPolSchema.getEdorNo());
									sqlbv2.put("polno", tempLPPolSchema.getPolNo().trim());
									sqlbv2.put("dutycode", tLPDutySchema.getDutyCode().trim());
									sqlbv2.put("payplantype", tempLPPremSet.get(k).getPayPlanType());
									LPPremDB tLPPremDB = new LPPremDB();
									LPPremSet tLPPremSet = new LPPremSet();

									tLPPremSet = tLPPremDB.executeQuery(sqlbv2);

									if (tLPPremSet.size() > 0) {
										for (int j = 1; j <= tLPPremSet.size(); j++) {
											LPPremSchema tLPPremSchema;// = new LCPremSchema();
											tLPPremSchema = tLPPremSet.get(j);

											tLPDutySchema.setPrem(tLPDutySchema.getPrem()
													- tLPPremSchema.getPrem());
											tempLPPolSchema.setPrem(tempLPPolSchema.getPrem()
													- tLPPremSchema.getPrem());
											mLPContSchema.setPrem(mLPContSchema.getPrem()
													- tLPPremSchema.getPrem());
										}
									}
								}								
								
								maxno = maxno + 1;
								// 形成加费编码
								String PayPlanCode = "";
								if(tempLPPremSet.get(k).getPayPlanCode()==null 
										|| tempLPPremSet.get(k).getPayPlanCode().equals(""))
								{
									PayPlanCode = String.valueOf(maxno + tCount);
									for (int j = PayPlanCode.length(); j < 8; j++) {
										PayPlanCode = "0" + PayPlanCode;
									}
								}
								else
								{
									PayPlanCode = tempLPPremSet.get(k).getPayPlanCode();
								}

								// 保单总保费
								tPrem = tempLPPolSchema.getPrem()
										+ tempLPPremSet.get(k).getPrem();
								tSumStandPrem = tSumStandPrem
										+ tempLPPremSet.get(k).getPrem();

								tempLPPremSet.get(k).setGrpContNo(mGrpContNo);
								tempLPPremSet.get(k).setContNo(mContNo);
								tempLPPremSet.get(k).setPayPlanCode(PayPlanCode);
								tempLPPremSet.get(k).setPayIntv(
										tLPDutySchema.getPayIntv());
								tempLPPremSet.get(k).setStandPrem(
										tempLPPremSet.get(k).getPrem());
								tempLPPremSet.get(k).setSumPrem("0");
								if("0".equals(tempLPPremSet.get(k).getAddForm())||"1".equals(tempLPPremSet.get(k).getAddForm())){
									
									tempLPPremSet.get(k).setPaytoDate(tMainPaytoDate);
								}else{
									
									tempLPPremSet.get(k).setPaytoDate(tempLPPremSet.get(k).getPayStartDate());
								}
								tempLPPremSet.get(k).setState("1");// 0:承保时的保费项。1:承保时的加费项；2：本次项目加费项
								// 3：前几次不通批单下的加费：
								tempLPPremSet.get(k).setUrgePayFlag("Y");// 加费相一定要催交，而不是去取该险种所描述的催交标志。
								tempLPPremSet.get(k).setManageCom(
										tempLPPolSchema.getManageCom());
								tempLPPremSet.get(k).setAppntNo(
										tempLPPolSchema.getAppntNo());
								tempLPPremSet.get(k).setAppntType("1");// 个人投保
								tempLPPremSet.get(k).setOperator(mOperater);
								tempLPPremSet.get(k).setMakeDate(
										PubFun.getCurrentDate());
								tempLPPremSet.get(k).setMakeTime(
										PubFun.getCurrentTime());
								tempLPPremSet.get(k).setModifyDate(
										PubFun.getCurrentDate());
								tempLPPremSet.get(k).setModifyTime(
										PubFun.getCurrentTime());
								tempLPPremSet.get(k).setCurrency(tempLPPolSchema.getCurrency());//TODO hq
							/*	
								tempLCPremSet.get(k).setAddFeeDirect(
										tempLCPremSet.get(k).getAddFeeDirect());
								tempLCPremSet.get(k).setSuppRiskScore(
										tempLCPremSet.get(k).getSuppRiskScore());
							*/
								if(!tempLPPremSet.get(k).getAddForm().equals("3") )//不是终止
								{ 
									boolean tFlagAdd = true;
									if(tempLPPremSet.get(k).getAddForm().equals("4"))//维持原条件
									{
										String sql = "select * from LPPrem where edorno='"+"?edorno?"+"' and payplancode  like '000000%' and polno = '"
										+ "?polno?"
										+ "' and dutycode = '"
										+ "?dutycode?"
										+ "' and payplantype='"+"?payplantype?"+"' "
										+ " and (addform is null or addform <>'3') and state = '1'";//上次加费不是终止
										SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
										sqlbv3.sql(sql);
										sqlbv3.put("edorno", tempLPPolSchema.getEdorNo());
										sqlbv3.put("polno", tempLPPolSchema.getPolNo().trim());
										sqlbv3.put("dutycode", tLPDutySchema.getDutyCode().trim());
										sqlbv3.put("payplantype", tempLPPremSet.get(k).getPayPlanType());
										LPPremDB tLPPremDB = new LPPremDB();
										LPPremSet tLPPremSet = new LPPremSet();
										tLPPremSet = tLPPremDB.executeQuery(sqlbv3);
										if(tLPPremSet !=null && tLPPremSet.size()>0)
										{
											tFlagAdd = false;//不需要加上本次加费费用
										}
									}
									if(tFlagAdd == true)
									{
										// 更新保险责任
										tLPDutySchema.setPrem(tLPDutySchema.getPrem()
												+ tempLPPremSet.get(k).getPrem());
										// 更新保单数据
										tempLPPolSchema.setPrem(tempLPPolSchema.getPrem()
												+ tempLPPremSet.get(k).getPrem());
										tempLPPolSchema.setModifyDate(tCurrentDate);
										tempLPPolSchema.setModifyTime(tCurrentTime);
										// 更新合同单数据
										mLPContSchema.setPrem(mLPContSchema.getPrem()
												+ tempLPPremSet.get(k).getPrem());
									}									
								}
								
								// 准备删除上一次该项目的加费的数据
								String tSQL = "select * from lpprem a where edorno='"+tempLPPolSchema.getEdorNo()+"' and polno = '" + tempLPPolSchema.getPolNo() + "'"
										+ " and substr(payplancode,1,6) = '000000' and PAYPLANTYPE='"+tempLPPremSet.get(k).getPayPlanType()+"' "
										+ " and state = '1'";// 0:承保时的保费项。1:承保时的加费项；2：本次项目加费项
								if(!tempLPPremSet.get(k).getAddForm().equals("3")&&!tempLPPremSet.get(k).getAddForm().equals("4"))
									tSQL = tSQL + " and addform in ('0','1','2')";
								// 3：前几次不通批单下的加费：
								SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
								sqlbv4.sql(tSQL);
								sqlbv4.put("edorno", tempLPPolSchema.getEdorNo());
								sqlbv4.put("polno", tempLPPolSchema.getPolNo());
								sqlbv4.put("payplantype", tempLPPremSet.get(k).getPayPlanType());
								LPPremDB tLPPremDB = new LPPremDB();
								LPPremSet oldPremSet = new LPPremSet();
								oldPremSet = tLPPremDB.executeQuery(sqlbv4);
								if (oldPremSet == null) {
									// @@错误处理
									CError.buildErr(this,"查询加费信息失败");
									return false;
								}
								this.mOldLPPremSet.add(oldPremSet);
								
								//财务处理 add by ln 2009-4-7
								// 加费开始时间类型除了下期加费类型、终止、维持外 需 保存加费保费与利息
								if(tempLPPremSet.get(k).getAddForm()!=null && !tempLPPremSet.get(k).getAddForm().equals("")
										&& (tempLPPremSet.get(k).getAddForm().equals("0") || tempLPPremSet.get(k).getAddForm().equals("1") || tempLPPremSet.get(k).getAddForm().equals("2")))
								{
									//先删除 数据
									mSQL_LJSGetEndorsePrem = "delete from LJSGetEndorse where GetNoticeNo = '"+"?mEdorNo?"+"' and EndorsementNo = '"+"?mEdorNo?"+"' "
							        + " and FeeOperationType ='"+"mEdorType"+"' and polno = '"+"?polno?"+"' and otherno= '"+"?mEdorNo?"+"' "
							        + " and dutycode='" + "?dutycode?" + "' "
									   + " and payplancode = '" + "?payplancode?" + "' and subfeeoperationtype = '"+"?subfeeoperationtype?"+"' and FeeFinaType = 'BF'" 
							        ; 
									//SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
									sqlbv7.sql(mSQL_LJSGetEndorsePrem);
									sqlbv7.put("mEdorNo", mEdorNo);
									sqlbv7.put("mEdorType", mEdorType);
									sqlbv7.put("polno", tempLPPolSchema.getPolNo());
									sqlbv7.put("dutycode", tLPDutySchema.getDutyCode());
									sqlbv7.put("payplancode", PayPlanCode);
									sqlbv7.put("subfeeoperationtype", BqCode.Pay_addPrem);
									logger.debug("mSQL_LJSGetEndorsePrem:"+mSQL_LJSGetEndorsePrem);
									
									mSQL_LJSGetEndorsePremZK = "delete from LJSGetEndorse where GetNoticeNo = '"+"?mEdorNo?"+"' and EndorsementNo = '"+"?mEdorNo?"+"' "
							        + " and FeeOperationType ='"+"mEdorType"+"' and polno = '"+"?polno?"+"' and otherno= '"+"?mEdorNo?"+"' "
							        + " and dutycode='" + "?dutycode?" + "' "
									   + " and payplancode = '" + "?payplancode?" + "' and subfeeoperationtype in (select code from ldcode where codetype='discounttype') and FeeFinaType = 'ZK'" 
							        ; 
									//SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
									sqlbv8.sql(mSQL_LJSGetEndorsePremZK);
									sqlbv8.put("mEdorNo", mEdorNo);
									sqlbv8.put("mEdorType", mEdorType);
									sqlbv8.put("polno", tempLPPolSchema.getPolNo());
									sqlbv8.put("dutycode", tLPDutySchema.getDutyCode());
									sqlbv8.put("payplancode", PayPlanCode);
									logger.debug("mSQL_LJSGetEndorsePremZK:"+mSQL_LJSGetEndorsePremZK);
									
									mSQL_LJSGetEndorseLX = "delete from LJSGetEndorse where GetNoticeNo = '"+"?mEdorNo?"+"' and EndorsementNo = '"+"?mEdorNo?"+"' "
										        + " and FeeOperationType ='"+"mEdorType"+"' and polno = '"+"?polno?"+"' and otherno= '"+"?mEdorNo?"+"' "
										        + " and dutycode='" + "?dutycode?" + "' "
												   + " and payplancode = '" + "?payplancode?" + "' and subfeeoperationtype = '"+"?subfeeoperationtype?"+"' and FeeFinaType = 'LX'" 
										        ; 
									//SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
									sqlbv9.sql(mSQL_LJSGetEndorseLX);
									sqlbv9.put("mEdorNo", mEdorNo);
									sqlbv9.put("mEdorType", mEdorType);
									sqlbv9.put("polno", tempLPPolSchema.getPolNo());
									sqlbv9.put("dutycode", tLPDutySchema.getDutyCode());
									sqlbv9.put("payplancode", PayPlanCode);
									sqlbv9.put("subfeeoperationtype", BqCode.Pay_addPremInterest);
									logger.debug("mSQL_LJSGetEndorseLX:"+mSQL_LJSGetEndorseLX);
									
									if(tempLPPremSet.get(k).getAddForm().equals("0") || tempLPPremSet.get(k).getAddForm().equals("1"))
									{							
										//准备新增的数据
										double[] payAddPrem = { 0.00, 0.00 };
										payAddPrem = calPayPrem(tempLPPolSchema,tLPEdorItemSchema,tempLPPremSet.get(k),tempLPPremSet.get(k).getAddForm());
										if(payAddPrem==null)
											return false;
										logger.debug("payAddPrem[0]" + payAddPrem[0]);
										//准备数据 ,但不保存LJSPayPerson	
										LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
										tLJSPayPersonSchema.setPolNo(tempLPPolSchema.getPolNo());
										tLJSPayPersonSchema.setGrpContNo(tempLPPolSchema.getGrpContNo());
										tLJSPayPersonSchema.setGrpPolNo(tempLPPolSchema.getGrpPolNo());
										tLJSPayPersonSchema.setGrpContNo(tempLPPolSchema.getGrpContNo());
										tLJSPayPersonSchema.setGrpPolNo(tempLPPolSchema.getGrpPolNo());
										tLJSPayPersonSchema.setContNo(tempLPPolSchema.getContNo());
										tLJSPayPersonSchema.setManageCom(tempLPPolSchema.getManageCom());
										tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
										tLJSPayPersonSchema.setRiskCode(tempLPPolSchema.getRiskCode());
										tLJSPayPersonSchema.setAppntNo(tempLPPolSchema.getAppntNo());
										tLJSPayPersonSchema.setPayAimClass("1");
										tLJSPayPersonSchema.setDutyCode(tLPDutySchema.getDutyCode());
										tLJSPayPersonSchema.setPayPlanCode(PayPlanCode);
										tLJSPayPersonSchema.setPayIntv(tempLPPolSchema.getPayIntv());
										tLJSPayPersonSchema.setPayDate(tLPEdorItemSchema.getEdorValiDate());
										tLJSPayPersonSchema.setPayType(tLPEdorItemSchema.getEdorType());
										tLJSPayPersonSchema.setLastPayToDate(tLPEdorItemSchema.getEdorValiDate());
										tLJSPayPersonSchema.setCurPayToDate(tempLPPolSchema.getPaytoDate());
										tLJSPayPersonSchema.setAgentCode(tempLPPolSchema.getAgentCode());
										tLJSPayPersonSchema.setAgentGroup(tempLPPolSchema.getAgentGroup());
										tLJSPayPersonSchema.setGetNoticeNo(tLPEdorItemSchema.getEdorNo());
			
										// 保费项的保存 SumActuPayMoney = SumDuePayMoney
										tLJSPayPersonSchema.setSumActuPayMoney(payAddPrem[0]);
										tLJSPayPersonSchema.setSumDuePayMoney(payAddPrem[0]);
										
										tLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
										tLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
										tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
										tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
										tLJSPayPersonSchema.setBankAccNo("");
										tLJSPayPersonSchema.setBankCode("");
										tLJSPayPersonSchema.setGetNoticeNo(tLPEdorItemSchema.getEdorNo());
										tLJSPayPersonSchema.setEndorsementNo(tLPEdorItemSchema.getEdorNo());
										tLJSPayPersonSchema.setCurrency(tempLPPolSchema.getCurrency());//TODO hq
										// 添加保费LJSGetEndorse
										if (!createLJSGetEndorseSchema(tLPEdorItemSchema,tempLPPolSchema,tLJSPayPersonSchema)) {
											mErrors.addOneError("添加保费LJSGetEndorse!");
											return false;
										}

										logger.debug("payAddPrem[1]" + payAddPrem[1]);
										// 添加利息到LJSGetEndorse
										if (!createLJSGetEndorseSchema(tLPEdorItemSchema,tempLPPolSchema,tLJSPayPersonSchema,payAddPrem[1])) {
											CError.buildErr(this, "添加利息到LJSGetEndorse!");
											return false;
										}
										
										//添加折扣到LJSGetEndorse
										aLJSGetEndorseSet.add(aZKLJSGetEndorseSet);
									}									
								}
								
							}
						}
						mNewLPDutySet.add(tLPDutySchema);
					}
				}
			}							
			this.mNewLPPremSet.add(tempLPPremSet);
			this.mNewLPPolSet.add(tempLPPolSchema);			

			// 准备核保主表信息
			LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
			tLPUWMasterDB.setPolNo(tempLPPolSchema.getPolNo());
			tLPUWMasterDB.setEdorNo(mEdorNo);
			tLPUWMasterDB.setEdorType(mEdorType);
			if (tLPUWMasterDB.getInfo() == false) {
				// @@错误处理
				CError.buildErr(this,"查询核保主表失败");
				return false;
			}
			LPUWMasterSchema tempLPUWMasterSchema = new LPUWMasterSchema();
			tempLPUWMasterSchema.setSchema(tLPUWMasterDB);
			if (mAddReason != null && !mAddReason.trim().equals("")) {
				tempLPUWMasterSchema.setAddPremReason(mAddReason);
			} else {
				tempLPUWMasterSchema.setAddPremReason("");
			}

			if (tempLPPremSet != null && tempLPPremSet.size() > 0) {
				tempLPUWMasterSchema.setAddPremFlag("1");// 有加费标识
			} else {
				tempLPUWMasterSchema.setAddPremFlag("0");// 无加费标识
			}
			
			tempLPUWMasterSchema.setPassFlag("y");//区别下险种结论 加费操作标志
			tempLPUWMasterSchema.setOperator(mOperater);
			tempLPUWMasterSchema.setManageCom(mManageCom);
			tempLPUWMasterSchema.setModifyDate(tCurrentDate);
			tempLPUWMasterSchema.setModifyTime(tCurrentTime);
			this.mLPUWMasterSet.add(tempLPUWMasterSchema);
			
			// 准备核保子表信息
			LPUWSubDB tLPUWSubDB = new LPUWSubDB();
			String sql = "select * from lpuwsub where PolNo='"+"?PolNo?"
						+"' and edorno='"+"?edorno?"
						+"' and edortype='"+"?edortype?"
						+"'order by uwno desc";		
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(sql);
			sqlbv6.put("PolNo", tempLPUWMasterSchema.getPolNo());
			sqlbv6.put("edorno", mEdorNo);
			sqlbv6.put("edortype", mEdorType);
			LPUWSubSet tLPUWSubSet = tLPUWSubDB.executeQuery(sqlbv6);
			if(tLPUWSubSet==null)
			{
				CError.buildErr(this,"查询险种核保子表出错!");
				return false;
			}
			LPUWSubSchema tempLPUWSubSchema = new LPUWSubSchema();
			tempLPUWSubSchema = tLPUWSubSet.get(1);
			tempLPUWSubSchema.setAddPremReason(tempLPUWMasterSchema.getAddPremReason());
			tempLPUWSubSchema.setAddPremFlag(tempLPUWMasterSchema.getAddPremFlag());
			tempLPUWSubSchema.setPassFlag(tempLPUWMasterSchema.getPassFlag());
			tempLPUWSubSchema.setUWNo(tLPUWSubSet.get(1).getUWNo()+i);
			tempLPUWSubSchema.setOperator(mOperater);
			tempLPUWSubSchema.setManageCom(mManageCom);
			tempLPUWSubSchema.setModifyDate(tCurrentDate);
			tempLPUWSubSchema.setModifyTime(tCurrentTime);
			this.mLPUWSubSet.add(tempLPUWSubSchema);
			
			//tongmeng 2008-12-04 add
			//如果没有加费记录的话,需要修改lcduty和,lcpol,LPCont的数据.
			/*String tSQL_Duty = "update lpduty a set prem = (select nvl(sum(prem),0) from lpprem where polno=a.polno and edorno=a.edorno and edortype=a.edortype) "
				             + " where polno='"+tempLPPolSchema.getPolNo()+"' and edorno='"+tempLPPolSchema.getEdorNo()
				             + "' and edortype='"+tempLPPolSchema.getEdorType()+"'"; 
			logger.debug("tSQL_Duty:"+tSQL_Duty);
			String tSQL_LPPol = "update lppol a set prem = (select nvl(sum(prem),0) from lpduty where polno=a.polno and edorno=a.edorno and edortype=a.edortype) "
				         //     + " ,sumprem = (select nvl(sum(prem),0) from lcprem where polno=a.polno) "
				              + " where polno = '"+tempLPPolSchema.getPolNo()+"' and edorno='"+tempLPPolSchema.getEdorNo()
				              + "' and edortype='"+tempLPPolSchema.getEdorType()+"'";
			logger.debug("tSQL_LCPol:"+tSQL_LPPol);
			mSQLData.add(tSQL_Duty);
			mSQLData.add(tSQL_LPPol);*/
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
	
	//准备加费 xAddPrem 未折扣的原始加费
	public double[] calPayPrem(LPPolSchema xLPPolSchema, LPEdorItemSchema xLPEdorItemSchema, LPPremSchema xLPPremSchema,String xAddForm) {
		
		double xAddPrem = xLPPremSchema.getPrem();
		// 保费和利息分开
		double banlancePrem[] = { 0.0000, 0.0000 };
		// 补交利息
		double tBJInterest = 0.00;
		int tIntv = xLPPolSchema.getPayIntv();
		
		double tAddPrem = 0.00;//折扣后的加费		
		
		if(xAddForm.equals("0"))//追溯
		{
			int PayCount = 1;//默认为趸交有利息
			if(tIntv > 0)
			{
				// 期交等效成交费的间隔月份数除以当前的payintv
				PayCount = PubFun.calInterval(xLPPolSchema.getCValiDate(), xLPPolSchema.getPaytoDate(), "M") / xLPPolSchema.getPayIntv();
			}			

			// 每次的补交保费的利息
			double xInterest = 0.00;

			// 每次循环的计息的起期 相当于lastpaytodate
			String tTepStartDate = "";

			// 这里应该是循环累加每一次缴费，因为每次的保费都有可能不一样
			// 年缴保单每年交的保费不一样???均衡保费（level premium)
			for (int i = 0; i <= PayCount - 1; i++) {
				tTepStartDate = FinFeePubFun.calOFDate(xLPPolSchema.getCValiDate(), i * xLPPolSchema.getPayIntv(), "M", xLPPolSchema
						.getCValiDate());

				// 补交折扣				
				double xAddZK = dealDiscount(xLPPolSchema,xLPEdorItemSchema,xLPPremSchema);	
				if(xAddZK>0)
					return null;
				
				tAddPrem = tAddPrem + xAddZK;
				
				// 补交保费利息为贷款利息 L
				xInterest = tAddPrem
						* AccountManage.calMultiRateMS(tTepStartDate, xLPEdorItemSchema.getEdorValiDate(), xLPPolSchema
								.getRiskCode(), xLPEdorItemSchema.getEdorType(), "L", "C", "Y",xLPPolSchema.getCurrency());

				// 累计补交保费的利息
				tBJInterest += xInterest;
			}

			// 补交保费
			banlancePrem[0] = xAddPrem * PayCount ;
			// 补交保费产生的利息
			banlancePrem[1] = tBJInterest;			
		}
		else if(xAddForm.equals("1"))//当期 无利息 交费间隔不能为趸交，前台有校验
		{			
			// 每次的补交保费的利息
			double xInterest = 0.00;

			// 补交保费
			banlancePrem[0] = xAddPrem ;
			// 补交保费产生的利息
			banlancePrem[1] = xInterest;			
			// 补交折扣				
			double xAddZK = dealDiscount(xLPPolSchema,xLPEdorItemSchema,xLPPremSchema);	
			if(xAddZK>0)
				return null;
		}		

		logger.debug("应补交的保费:" + banlancePrem[0]);
		logger.debug("应补交的利息:" + banlancePrem[1]);

		return banlancePrem;
	}
	
	/**
	 * 生成交退费记录
	 * 
	 * @return
	 */
	private LJSGetEndorseSchema initLJSGetEndorse(LPEdorItemSchema tLPEdorItemSchema, LPPolSchema tLPPolSchema, String strfinType) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema.setGetNoticeNo(tLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setEndorsementNo(tLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setFeeOperationType(tLPEdorItemSchema.getEdorType());
		tLJSGetEndorseSchema.setFeeFinaType(strfinType);
		tLJSGetEndorseSchema.setDutyCode("0");
		tLJSGetEndorseSchema.setPayPlanCode("0");
		// 走保全交费财务流程
		tLJSGetEndorseSchema.setOtherNo(tLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
		tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
		mReflections.transFields(tLJSGetEndorseSchema, tLPPolSchema);
		tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		tLJSGetEndorseSchema.setGetDate(tLPEdorItemSchema.getEdorValiDate());
		// 0为补费1为退费
		tLJSGetEndorseSchema.setGetFlag("0");

		return tLJSGetEndorseSchema;
	}
	
	/**
	 * 由LJSPayPerson信息生成当期保费的批改补退费信息
	 * 
	 * @param cLJSPayPersonSchema
	 * @return
	 */
	// 添加保费至aLJSGetEndorseSet
	private boolean createLJSGetEndorseSchema(LPEdorItemSchema tLPEdorItemSchema, LPPolSchema tLPPolSchema, LJSPayPersonSchema cLJSPayPersonSchema) {

		// add by jiaqiangli 2009-01-04 附加豁免金额为负时不处理
		if (cLJSPayPersonSchema.getSumDuePayMoney() > 0) {
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema = this.initLJSGetEndorse(tLPEdorItemSchema,tLPPolSchema,"BF");
			mReflections.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
			tLJSGetEndorseSchema.setGetMoney(cLJSPayPersonSchema.getSumDuePayMoney());
			
			tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_addPrem);
			aLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}
		return true;
	}
	
	/**
	 * 由LJSPayPerson信息生成当期保费折扣的批改补退费信息
	 * 
	 * @param cLJSPayPersonSchema
	 * @return
	 */
	// 添加保费至aLJSGetEndorseSet
	private boolean createLJSGetEndorseSchemaZK(LPEdorItemSchema tLPEdorItemSchema, LPPolSchema tLPPolSchema, LJSPayPersonSchema cLJSPayPersonSchema) {

			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema = this.initLJSGetEndorse(tLPEdorItemSchema,tLPPolSchema,"ZK");
			mReflections.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
			tLJSGetEndorseSchema.setGetMoney(cLJSPayPersonSchema.getSumDuePayMoney());
			
			tLJSGetEndorseSchema.setSubFeeOperationType(cLJSPayPersonSchema.getPayType());
			aZKLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		return true;
	}
	
	private boolean createLJSGetEndorseSchema(LPEdorItemSchema tLPEdorItemSchema, LPPolSchema tLPPolSchema, LJSPayPersonSchema tmpLJSPayPersonSchema, double tInterest) {
		// 生成保全RE保费复利记录
		// comment by jiaqiangli 2008-12-24 利息为为负值的不插表
		if (tInterest > 0) {
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema = this.initLJSGetEndorse(tLPEdorItemSchema,tLPPolSchema,"LX");
			mReflections.transFields(tLJSGetEndorseSchema, tmpLJSPayPersonSchema);
			// GetMoney保存利息
			tLJSGetEndorseSchema.setGetMoney(tInterest);
			tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_addPremInterest);
			tLJSGetEndorseSchema.setPolNo(tLPPolSchema.getPolNo());
			aLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}
		return true;
	}
	
	private double dealDiscount(LPPolSchema tLPPolSchema, LPEdorItemSchema tLPEdorItemSchema,LPPremSchema tLPPremSchema)
	{
		double tZK = 0.00;
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		LPDiscountSet qLPDiscountSet = new LPDiscountSet();
		LPDiscountDB tLPDiscountDB = new LPDiscountDB();
		tLPDiscountDB.setPolNo(tLPPolSchema.getPolNo());			
		qLPDiscountSet = tLPDiscountDB.query();
		//************ start
		if(qLPDiscountSet==null || qLPDiscountSet.size()<1)
		{
			LCDiscountSet qLCDiscountSet = new LCDiscountSet();
			LCDiscountDB tLCDiscountDB = new LCDiscountDB();
			tLCDiscountDB.setPolNo(tLPPolSchema.getPolNo());			
			qLCDiscountSet = tLCDiscountDB.query();
			//************ start
			if(qLCDiscountSet!=null && qLCDiscountSet.size()>0)
			{
				for(int i=1;i<=qLCDiscountSet.size();i++)
				{
					LPDiscountSchema qLPDiscountSchema = new LPDiscountSchema();
					mReflections.transFields(qLPDiscountSchema, qLCDiscountSet.get(i));
					qLPDiscountSet.add(qLPDiscountSchema);
				}				
			}			
		}
		
		if(qLPDiscountSet!=null && qLPDiscountSet.size()>0)
		{		
			LPPremSet tLPPremSet=new LPPremSet();
			tLPPremSet.add(tLPPremSchema);
			TransferData tTransferData=new TransferData();
			tTransferData.setNameAndValue("PayCount", "");
			tTransferData.setNameAndValue("PayIntv", String.valueOf(tLPPolSchema.getPayIntv()));
			tTransferData.setNameAndValue("Operator", mGlobalInput.Operator);			
			PEdorDiscountCalBL tDiscountCalBL = new PEdorDiscountCalBL();
			VData tzkVData = new VData();
			tzkVData.add(tLPPolSchema);
			tzkVData.add(tLPPremSet);
			tzkVData.add(qLPDiscountSet);
			tzkVData.add(tLPEdorItemSchema);
			tzkVData.add(tTransferData);
			//
			if(!tDiscountCalBL.calculate(tzkVData))
			{
				CError.buildErr(this, "折扣计算失败！");
				tZK = 1;
			}
			
			//
			VData rVData = tDiscountCalBL.getResult();
			tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
		}	
		if(tLJSPayPersonSet!=null && tLJSPayPersonSet.size()>0)
		{
			// 添加保费LJSGetEndorse
			for(int i=1;i<=tLJSPayPersonSet.size();i++)
			{
				if (!createLJSGetEndorseSchemaZK(tLPEdorItemSchema,tLPPolSchema,tLJSPayPersonSet.get(i))) {
					mErrors.addOneError("添加保费LJSGetEndorse!");
					tZK = 1;
				}
				//计算折扣总额
				tZK = tZK +tLJSPayPersonSet.get(i).getSumActuPayMoney();
			}			
		}
		
		return tZK;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 删除上一个加费数据
		if (mOldLPPremSet != null && mOldLPPremSet.size() > 0) {
			map.put(mOldLPPremSet, "DELETE");
		}

		// 添加本次加费数据
		if (mNewLPPremSet != null && mNewLPPremSet.size() > 0) {
			map.put(mNewLPPremSet, "INSERT");
		}

		// 修改本次加费后更新的保单责任数据
		if (mNewLPDutySet != null && mNewLPDutySet.size() > 0) {
			map.put(mNewLPDutySet, "UPDATE");
		}

		// 修改本次加费后更新的保单数据
		if (mNewLPPolSet != null) {
			map.put(mNewLPPolSet, "UPDATE");
		}

		// 修改本次加费后更新的合同单数据
		if (mLPContSchema != null) {
			map.put(mLPContSchema, "UPDATE");
		}		
		
		if(!mSQL_LJSGetEndorsePrem.equals(""))
			map.put(sqlbv7, "DELETE");
		
		if(!mSQL_LJSGetEndorsePremZK.equals(""))
			map.put(sqlbv8, "DELETE");
		
		if(!mSQL_LJSGetEndorseLX.equals(""))
			map.put(sqlbv9, "DELETE");
		
		logger.debug("aLJSGetEndorseSet.size()" + aLJSGetEndorseSet.size());
		if (aLJSGetEndorseSet != null && aLJSGetEndorseSet.size() > 0) {			
			map.put(aLJSGetEndorseSet, "INSERT");
		}

		// 更新批单核保主表
		map.put(mLPUWMasterSet, "UPDATE");
		
		// 更新批单核保主表
		map.put(mLPUWSubSet, "INSERT");
		
		//修改SQL
		/*for(int i=0;i<this.mSQLData.size();i++)
		{
			String tSQL = (String)this.mSQLData.get(i);
			map.put(tSQL, "UPDATE");
		}*/
		/*String tSQL_Cont = "update lpcont a set prem="
						+"a.prem"
						+"+(select nvl(sum(prem),0) from lpprem c where payplancode like '000000%' and c.edorno=a.edorno and c.edortype=a.edortype and c.contno=a.contno) "
						 +" where a.edorno='"+mEdorNo+"' and a.edortype='"+mEdorType+"'"
			            // + " ,sumprem = (select nvl(sum(prem),0) from lcpol where contno=a.contno) " 
						 + " and a.contno='"+this.mContNo+"' ";
		logger.debug("tSQL_Cont:"+tSQL_Cont);
		
		map.put(tSQL_Cont, "UPDATE");*/

		// 更新合同单核保主表
	//	map.put(mLCCUWMasterSchema, "UPDATE");

		//if(mLPCUWMasterSchema!=null)
			// 更新合同单核保主表
		//	map.put(mLPCUWMasterSchema, "UPDATE");
		
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
