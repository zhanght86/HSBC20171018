package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGCUWErrorDB;
import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCGCUWSubDB;
import com.sinosoft.lis.db.LCGUWErrorDB;
import com.sinosoft.lis.db.LCGUWMasterDB;
import com.sinosoft.lis.db.LCGUWSubDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDUWGradePersonDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGCUWErrorSchema;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCGCUWSubSchema;
import com.sinosoft.lis.schema.LCGUWErrorSchema;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.schema.LCGUWSubSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGCUWErrorSet;
import com.sinosoft.lis.vschema.LCGCUWMasterSet;
import com.sinosoft.lis.vschema.LCGCUWSubSet;
import com.sinosoft.lis.vschema.LCGUWErrorSet;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.vschema.LCGUWSubSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保团体自动核保业务部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @modified by ZhangRong 2004.11
 * @version 2.0
 */
public class GrpUWAutoChkBL {
private static Logger logger = Logger.getLogger(GrpUWAutoChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private String mOperate = "";

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mPolPassFlag = "0"; // 险种通过标记
	private String mContPassFlag = "0"; // 合同通过标记
	private String mUWGrade = "";
	private String mmUWGrade = "A";// add by wanglei
	private String mCalCode; // 计算编码
	private String mUser;
	private FDate fDate = new FDate();
	private double mValue;
	private LCGrpContSet mAllLCGrpContSet = new LCGrpContSet();
	private LCGrpPolSet mAllLCGrpPolSet = new LCGrpPolSet();
	private String mGrpContNo = "";
	private String mProposalGrpContNo = "";
	private String mGrpPolNo = "";
	private String mApproveFlag = "";
	/** 合同核保主表 */
	private LCGCUWMasterSet mLCGCUWMasterSet = new LCGCUWMasterSet();
	private LCGCUWMasterSet mAllLCGCUWMasterSet = new LCGCUWMasterSet();

	/** 合同核保子表 */
	private LCGCUWSubSet mLCGCUWSubSet = new LCGCUWSubSet();
	private LCGCUWSubSet mAllLCGCUWSubSet = new LCGCUWSubSet();

	/** 合同核保错误信息表 */
	private LCGCUWErrorSet mLCGCUWErrorSet = new LCGCUWErrorSet();
	private LCGCUWErrorSet mAllLCGCUWErrorSet = new LCGCUWErrorSet();

	/** 各险种核保主表 */
	private LCGUWMasterSet mLCGUWMasterSet = new LCGUWMasterSet();
	private LCGUWMasterSet mAllLCGUWMasterSet = new LCGUWMasterSet();

	/** 各险种核保子表 */
	private LCGUWSubSet mLCGUWSubSet = new LCGUWSubSet();
	private LCGUWSubSet mAllLCGUWSubSet = new LCGUWSubSet();

	/** 核保错误信息表 */
	private LCGUWErrorSet mLCGUWErrorSet = new LCGUWErrorSet();
	private LCGUWErrorSet mAllLCErrSet = new LCGUWErrorSet();
	private CalBase mCalBase = new CalBase();

	
	//tongmeng 2009-04-21 add
	//记录险种的最终核保结论
	private String mIndContFlag = "9";
	public GrpUWAutoChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		TransferData tTransferData = new TransferData();
		mResult.clear();
		this.mOperate = cOperate;

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		logger.debug("=====This is GrpUWAutoChkBL->submitData!!=====\n");

		// 得到外部传入的数据,将数据备份到本类中
		LCGrpContSchema tLCGrpContSchema = getInputData(cInputData);

		if (tLCGrpContSchema == null) {
			tTransferData.setNameAndValue("FinishFlag", "0");
			tTransferData.setNameAndValue("UWFlag", "0");
			mResult.add(tTransferData);
			return false;
		} 
		if (!checkData(tLCGrpContSchema)) {
			tTransferData.setNameAndValue("FinishFlag", "0");
			tTransferData.setNameAndValue("UWFlag", "0");
			mResult.add(tTransferData);
			return false;
		}
		if (checkFinished(tLCGrpContSchema)) {
			tTransferData.setNameAndValue("FinishFlag", "1");
			tTransferData.setNameAndValue("UWFlag", mContPassFlag);
			mResult.add(tTransferData);
			return true;
		}
		if (!dealData(tLCGrpContSchema)) {
			tTransferData.setNameAndValue("FinishFlag", "0");
			tTransferData.setNameAndValue("UWFlag", "0");
			tTransferData.setNameAndValue("UWGrade", mUWGrade);
			mResult.add(tTransferData);
			return false;
		}
		// 准备给后台的数据
		if (!prepareOutputData(tLCGrpContSchema)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "提交的数据准备失败!";
			this.mErrors.addOneError(tError);
			tTransferData.setNameAndValue("FinishFlag", "0");
			tTransferData.setNameAndValue("UWFlag", "0");
			mResult.add(tTransferData);
			return false;
		}
		tTransferData.setNameAndValue("FinishFlag", "1");
		tTransferData.setNameAndValue("UWFlag", mContPassFlag);
		mResult.add(tTransferData);
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCGrpContSchema tLCGrpContSchema) {

		logger.debug("=====This is GrpUWAutoChkBL->dealData=====\n");
		// 获得保单号
		mGrpContNo = tLCGrpContSchema.getGrpContNo();
		mProposalGrpContNo = tLCGrpContSchema.getProposalGrpContNo();
//modify by tansz on 20110925 暂时将国际版修改的内容改为原MS的处理过程 		
//		//增加团险自核校验调用程序 add by gaobo for MMA 20100916 start
////		GrpAutoUWCheckBL tGrpAutoUWCheckBL = new GrpAutoUWCheckBL();
////		TransferData tTransferData = new TransferData();
//		
////		TransferData tTransferData = new TransferData();
////		tTransferData.setNameAndValue("GrpContNo",mGrpContNo);
//		VData tInputData = new VData();
//		GlobalInput tG = new GlobalInput();
////		tG.Operator = this.mOperator;
////		tInputData.clear();
////		tInputData.add(tG);
////		tInputData.add(tTransferData);
////		if (!tGrpAutoUWCheckBL.submitData(tInputData, "")) {
////			// @@错误处理
////			this.mErrors.copyAllErrors(tGrpAutoUWCheckBL.mErrors);
////			CError tError = new CError();
////			tError.moduleName = "GrpUWAutoChkBL--AutoUWCheckBL";
////			tError.functionName = "dealData";
////			tError.errorMessage = "对集体保单号为：" + mGrpContNo
////					+ " 的集体保单进行自动核保时失败";
////			this.mErrors.addOneError(tError);
////			return false;
////		}
//		//增加团险自核校验调用程序 add by gaobo for MMA 20100916 end
//		
//		mUWGrade = "B1";
//		mApproveFlag = "9";
//		LCContDB tLCContDB = new LCContDB();
//		tLCContDB.setGrpContNo(mGrpContNo);
//		LCContSet tLCContSet = tLCContDB.query();
//		if (tLCContSet == null) {
//			// @@错误处理
//			CError tError = new CError();
//			tError.moduleName = "GrpUWAutoChkBL";
//			tError.functionName = "dealData";
//			tError.errorMessage = "取集体保单号为：" + mGrpContNo + " 的个人保单失败";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//		LCContSchema tLCContSchema = null;
//		
//		/* 团险保单级别的自核规则调用改造，由UWAutoChkBL改为AutoUWCheckBL
//		 * modify by gaobo for MMA at 20100920*/
////		UWAutoChkBL tUWAutoChkBL = null;
//		GrpAutoUWCheckBL tGrpAutoUWCheckBL = null;
//		int nCount = tLCContSet.size();
//		int nIndex = 0;
//		for (nIndex = 1; nIndex <= nCount; nIndex++) {
//			tLCContSchema = tLCContSet.get(nIndex);
//			//modify by tansz on 20110920 for 团单下个单可正常进行自动核保 
//			TransferData tTransferData = new TransferData();
//			tTransferData.setNameAndValue("ContNo",tLCContSchema.getContNo());
//			tG.Operator = this.mOperator;
//			tG.ManageCom = tLCContSchema.getManageCom();
//			tInputData.clear();
//			tInputData.add(tG);
//			tInputData.add(tTransferData);
//			tGrpAutoUWCheckBL = new GrpAutoUWCheckBL();
//			if (!tGrpAutoUWCheckBL.submitData(tInputData, "")) {
//				// @@错误处理
//				this.mErrors.copyAllErrors(tGrpAutoUWCheckBL.mErrors);
//				CError tError = new CError();
//				tError.moduleName = "GrpUWAutoChkBL";
//				tError.functionName = "dealData";
//				tError.errorMessage = "对集体保单号为：" + mGrpContNo
//						+ " 的集体保单下的个人保单进行核保时失败";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//
//			if (tInputData == null) {
//				// @@错误处理
//				CError tError = new CError();
//				tError.moduleName = "GrpUWAutoChkBL";
//				tError.functionName = "dealData";
//				tError.errorMessage = "对集体保单号为：" + mGrpContNo
//						+ " 的集体保单下的个人保单进行核保时出现返回空信息错误";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//			if(!"SingleCheck".equals(mOperate)){//对于从GrpPolApproveAfterInitService来的，不做如下处理。
//				if (!this.mContPassFlag.equals("5")) {
//					this.mContPassFlag = (String) tInputData.get(0);
//				}
//				//tongmeng 2009-04-21 add
//				//保存合同层核保结论.
//				this.mIndContFlag = (String) tInputData.get(0);
//				if(mIndContFlag!=null&&mIndContFlag.equals("5"))
//				{
//					this.mContPassFlag = "5";
//					this.mPolPassFlag = "5";
//				}
//				
//				if (this.mUWGrade.compareTo((String) tInputData.get(1)) < 0) {
//					this.mUWGrade = (String) tInputData.get(1);
//				}
//			}
//		}
		mUWGrade = "B1";
		mApproveFlag = "9";
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setGrpContNo(mGrpContNo);
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "dealData";
			tError.errorMessage = "取集体保单号为：" + mGrpContNo + " 的个人保单失败";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCContSchema tLCContSchema = null;
		GlobalInput tG = new GlobalInput();
		tG.Operator = this.mOperator;
		VData tInputData = new VData();
		UWAutoChkBL tUWAutoChkBL = null;
		int nCount = tLCContSet.size();
		int nIndex = 0;
		for (nIndex = 1; nIndex <= nCount; nIndex++) {
			tLCContSchema = tLCContSet.get(nIndex);
			tInputData.clear();
			tInputData.add(tG);
			tInputData.add(tLCContSchema);
			tUWAutoChkBL = new UWAutoChkBL();
			if (!tUWAutoChkBL.submitData(tInputData, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tUWAutoChkBL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpUWAutoChkBL";
				tError.functionName = "dealData";
				tError.errorMessage = "对集体保单号为：" + mGrpContNo
						+ " 的集体保单下的个人保单进行核保时失败";
				this.mErrors.addOneError(tError);
				return false;
			}

			if (tInputData == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpUWAutoChkBL";
				tError.functionName = "dealData";
				tError.errorMessage = "对集体保单号为：" + mGrpContNo
						+ " 的集体保单下的个人保单进行核保时出现返回空信息错误";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (!this.mContPassFlag.equals("5")) {
				this.mContPassFlag = (String) tInputData.get(0);
			}
			//tongmeng 2009-04-21 add
			//保存合同层核保结论.
			this.mIndContFlag = (String) tInputData.get(0);
			if(mIndContFlag!=null&&mIndContFlag.equals("5"))
			{
				this.mContPassFlag = "5";
				this.mPolPassFlag = "5";
			}
			
			if (this.mUWGrade.compareTo((String) tInputData.get(1)) < 0) {
				this.mUWGrade = (String) tInputData.get(1);
			}
		}
//end by tansz on 20110925
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolSchema tLCGrpPolSchema = null;
		tLCGrpPolDB.setGrpContNo(mGrpContNo);
		tLCGrpPolSet = tLCGrpPolDB.query();
		nCount = tLCGrpPolSet.size();
		nIndex = 0;

		// 未通过的核保规则
		LMUWSet tLMUWSetUnpass = new LMUWSet();

		// 所有核保规则
		LMUWSet tLMUWSetAll = null;

		// 需要风险检测的特殊核保规则
		LMUWSet tLMUWSetSpecial = null;
		LMUWSchema tLMUWSchema = null;

		for (nIndex = 1; nIndex <= nCount; nIndex++) {
			tLCGrpPolSchema = tLCGrpPolSet.get(nIndex);

			// 获得保单险种号
			this.mGrpPolNo = tLCGrpPolSchema.getGrpPolNo();

			// 获得险种号
			String tkinds = tLCGrpPolSchema.getRiskCode();

			// 准备算法，获取某险种的所有核保规则的集合
			tLMUWSetUnpass.clear();

			if (tLMUWSetAll != null) {
				tLMUWSetAll.clear();
			}
			if (tLMUWSetSpecial != null) {
				tLMUWSetSpecial.clear();
			}
			tLMUWSetAll = CheckKinds(tLCGrpPolSchema);
			if (tLMUWSetAll == null) {
				return false;
			}
			tLMUWSetSpecial = CheckKinds2(tLCGrpPolSchema);
			if (tLMUWSetSpecial == null) {
				return false;
			}

			// 准备数据，从险种信息中获取各项计算信息
			CheckPolInit(tLCGrpPolSchema);

			// 个人单核保
			// 核保通过标志，初始为未核保
			if(!mPolPassFlag.equals("5"))
			{
				mPolPassFlag = "0";
			}

			// 核保规则数量
			int n = tLMUWSetAll.size();
			if (n == 0) {
				// 无核保规则则置标志为通过
				if(!mPolPassFlag.equals("5"))
				{
					mPolPassFlag = "9";
				}
			} else {
				// 目前目前所有的险种均有一些公共的核保规则,所以必定走该分枝
				int j = 0;
				for (int i = 1; i <= n; i++) {
					// 取计算编码
					tLMUWSchema = new LMUWSchema();
					tLMUWSchema = tLMUWSetAll.get(i);
					mCalCode = tLMUWSchema.getCalCode();
					if (CheckPol(tLCGrpPolSchema.getRiskCode()) == 0) {
					} else {
//						没有通过，需要将lmuw同当前的mUWGrade作比较，将最大的保存到mUWGrade中
						String tUWGrade =tLMUWSchema.getUWGrade();
						if ((tUWGrade == null)
								|| (mUWGrade.compareTo(tUWGrade) < 0)) {
							// 当需要人工核保时候当即tLMUWSetUnpass.size()>0时,mUWGrade应该不为null,
							// 否则是自动核保规则中核保级别字段缺少了数据
							mUWGrade = tUWGrade;
						}
						j++;
						tLMUWSetUnpass.add(tLMUWSchema);
						mPolPassFlag = "5"; // 待人工核保
						mContPassFlag = "5";

						// 取核保级别
						// String tuwgrade = tLMUWSchema.getUWGrade();
					}
				}
//				mUWGrade = "A";
				logger.debug("校验子和规则后得到的级别为："+mUWGrade);
				String tempuwgrade = checkRiskAmnt(tLCGrpPolSchema);
				if (tempuwgrade != null) {
					if ((mUWGrade == null)
							|| (mUWGrade.compareTo(tempuwgrade) < 0)) {
						// 当需要人工核保时候当即tLMUWSetUnpass.size()>0时,mUWGrade应该不为null,
						// 否则是自动核保规则中核保级别字段缺少了数据
						mUWGrade = tempuwgrade;
					}
					if (mmUWGrade.compareTo(mUWGrade) < 0) {
						mmUWGrade = mUWGrade;
					} else {
						mUWGrade = mmUWGrade;
					}

				}
				logger.debug("after checkRiskAmnt规则后得到的级别为："+mUWGrade);
				if (mPolPassFlag.equals("0")) {
					mPolPassFlag = "9";
				}
			}
//			计算最高保额核保级别
			String tAmntUWGrade = checkRiskAmnt1(tLCGrpPolSchema);
			if(mUWGrade.compareTo(tAmntUWGrade)<0){
				mUWGrade = tAmntUWGrade;
			}
			logger.debug("after checkRiskAmnt1(团险新自核级别)规则后得到的级别为："+mUWGrade);
			if (dealOnePol(tLCGrpPolSchema, tLMUWSetUnpass) == false) {
				return false;
			}
		}

		/* 合同核保 */
		// 未通过的合同核保规则
		LMUWSet tLMUWSetContUnpass = new LMUWSet();
		// 所有合同核保规则
		LMUWSet tLMUWSetContAll = CheckKinds3();
		// 准备数据，从险种信息中获取各项计算信息
		CheckContInit(tLCGrpContSchema);

		// 个人合同核保
		// 核保规则数量
		int tCount = tLMUWSetContAll.size();
		if (tCount == 0) {
			if (!mContPassFlag.equals("5")) {
				// 无核保规则则置标志为通过
				mContPassFlag = "9";
			}
		} else {
			// 目前目前所有的险种均有一些公共的核保规则,所以必定走该分枝
			int j = 0;

			for (int index = 1; index <= tCount; index++) {
				// 取计算编码
				tLMUWSchema = new LMUWSchema();
				tLMUWSchema = tLMUWSetContAll.get(index);
				mCalCode = tLMUWSchema.getCalCode();

				if (CheckPol("000000") == 0) {
				} else {
//					没有通过，需要将lmuw同当前的mUWGrade作比较，将最大的保存到mUWGrade中
					String tUWGrade =tLMUWSchema.getUWGrade();
					if ((tUWGrade == null)
							|| (mUWGrade.compareTo(tUWGrade) < 0)) {
						// 当需要人工核保时候当即tLMUWSetUnpass.size()>0时,mUWGrade应该不为null,
						// 否则是自动核保规则中核保级别字段缺少了数据
						mUWGrade = tUWGrade;
					}
					j++;
					tLMUWSetContUnpass.add(tLMUWSchema);
					mContPassFlag = "5"; // 核保不通过，待人工核保

					// 取核保级别
					// String tuwgrade = tLMUWSchema.getUWGrade();
				}
			}
			if (mContPassFlag.equals("0")) {
				if (!mContPassFlag.equals("5")) {
					mContPassFlag = "9";
				}
			}
		}
		dealOneCont(tLCGrpContSchema, tLMUWSetContUnpass);
		return true;
	}
   private String checkRiskAmnt1(LCGrpPolSchema tLCGrpPolSchema){
	   String tUWGrade ="";
	   //String sql = "select  nvl(getgrpuwgrade('"+tLCGrpPolSchema.getGrpContNo()+"','"+tLCGrpPolSchema.getPrtNo()+"','"+tLCGrpPolSchema.getRiskCode()+"'),'A') from dual";
	   String uwSQL = "select getgrpuwgrade('"+tLCGrpPolSchema.getGrpContNo()+"','"+tLCGrpPolSchema.getPrtNo()+"') from dual";
	   SSRS tSSRS = new SSRS();
	   ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(uwSQL);
		tUWGrade = tSSRS.GetText(1, 1);
		logger.debug("通过getGrpUWGrade函数计算出来的核保级别为："+tUWGrade);
		
     return tUWGrade;
   }
	/**
	 * 根据保额校验核保级别
	 * 
	 * @return
	 */
	private String checkRiskAmnt(LCGrpPolSchema tLCGrpPolSchema) {
		String tUWGrade = "";

		// 计算
		// Calculator mCalculator = new Calculator();
		// mCalculator.setCalCode(mCalCode);

		// 增加基本要素
		// mCalculator.addBasicFactor("Get", mCalBase.getGet());
		// mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		// mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		// mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		// mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		// mCalculator.addBasicFactor("Job", mCalBase.getJob());
		// mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		// mCalculator.addBasicFactor("GetStartDate", "");
		// mCalculator.addBasicFactor("Years", mCalBase.getYears());
		// mCalculator.addBasicFactor("Grp", "");
		// mCalculator.addBasicFactor("GetFlag", "");
		// mCalculator.addBasicFactor("ValiDate", "");
		// mCalculator.addBasicFactor("Count", mCalBase.getCount());
		// mCalculator.addBasicFactor("FirstPayDate", "");
		// mCalculator.addBasicFactor("GrpPolNo", mCalBase.getGrpPolNo());
		// mCalculator.addBasicFactor("GrpContNo", mGrpContNo);
		//
		// //
		// mCalculator.addBasicFactor("InsuredNo",tLCGrpPolSchema.getInsuredNo());;
		// mCalculator.addBasicFactor("RiskCode",
		// tLCGrpPolSchema.getRiskCode());
		// ;
		//
		// String tStr = "";
		// tStr = mCalculator.calculate();
		//
		// if (tStr.trim().equals("")) {
		// tUWGrade = "";
		// } else {
		// tUWGrade = tStr.trim();
		// }

		// logger.debug("AmntGrade:" + tUWGrade);
		// 取那么多的SQL执行效率低，现改为直接从数据库中取值
		// 1:判断险类 2：查询LDUWGRADEPERSON
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		String sql = "select * From lmriskapp where riskcode='"
				+ tLCGrpPolSchema.getRiskCode() + "'";
		LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
		tLMRiskAppSet = tLMRiskAppDB.executeQuery(sql);
//		if (tLCGrpPolSchema.getRiskCode().equals("101")) {// 王磊暂为101做的特殊分支，以后要为101等年金险修改描述来控制核保级别
//			return "B1";
//		}
		if (tLMRiskAppDB.mErrors.needDealError()) {

			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "CheckKinds";
			tError.errorMessage = "险种分类信息查询失败!";
			this.mErrors.addOneError(tError);

		}
		if (tLMRiskAppSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "CheckKinds";
			tError.errorMessage = "险种分类信息查询失败!";
			this.mErrors.addOneError(tError);

		}
		String temrikstype = tLMRiskAppSet.get(1).getRiskType();
		String risktype = "10";
		if (temrikstype.trim().equals("H")) {
			risktype = "30";
		}
		// 查询团单下个人最大的保额
		String GrpContno = tLCGrpPolSchema.getGrpContNo();
		ExeSQL tExeSQL = new ExeSQL();
		// 查询保单下的通知书号，根据通知书号进行处理，一个通知书号代表一个领取人

		String tSql = "select max(amnt/peoples) from lccont where grpcontno='"
				+ GrpContno + "' and poltype!='2' and poltype!='3'";
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(tSql);
		double temamnt = parseFloat(tSSRS.GetText(1, 1));
		// end

		LDUWGradePersonDB tLDUWGradePersonDB = new LDUWGradePersonDB();
//		sql = "select * From lduwgradeperson where uwkind='" + risktype
//				+ "' and maxamnt>=" + temamnt
//				+ " and uwtype='1' order by uwpopedom";// 1:核保 2：核赔
		//sql = "select  nvl(getgrpuwgrade('"+tLCGrpPolSchema.getGrpContNo()+"','"+tLCGrpPolSchema.getPrtNo()+"','"+tLCGrpPolSchema.getRiskCode()+"'),'A') from dual";
		//tSSRS = new SSRS();
		//tSSRS = tExeSQL.execSQL(sql);
		//tUWGrade = tSSRS.GetText(1, 1);
		//logger.debug("通过getUWGrade函数计算出来的核保级别为："+tUWGrade);
		//LDUWGradePersonSet tLDUWGradePersonSet = new LDUWGradePersonSet();
		//tLDUWGradePersonSet = tLDUWGradePersonDB.executeQuery(sql);
//		logger.debug(sql);
//		if (tLDUWGradePersonSet.size() == 0) {
//			tUWGrade = "A";
//		} else {
//			tUWGrade = tLDUWGradePersonSet.get(1).getUWPopedom();
//		}
		// 查询公共帐户，确认真正的核保级别
		// tSql ="select max(amnt) from lccont where grpcontno='"+GrpContno+"'
		// and poltype='2'";
		// tSSRS = tExeSQL.execSQL(tSql);
		// double amnt=10;
		// if(tSSRS.MaxRow>0)
		// {
		// amnt = parseFloat(tSSRS.GetText(1, 1));
		// }
		// sql="select * From lduwgradeperson where uwkind='"+risktype+"' and
		// maxamnt>="+amnt+" and uwtype='1'";//1:核保 2：核赔
		// tLDUWGradePersonSet=tLDUWGradePersonDB.executeQuery(sql);
		// if(tLDUWGradePersonSet!=null && tLDUWGradePersonSet.size()>0)
		// {
		// String temgrade=tLDUWGradePersonSet.get(1).getUWPopedom();
		// if(temgrade.compareTo(tUWGrade)>0)
		// {
		// tUWGrade= temgrade;
		// }
		// }
		tSql = "select prem_ctrl('" + tLCGrpPolSchema.getGrpPolNo()
				+ "') from dual";
		tSSRS = tExeSQL.execSQL(tSql);
		int premctrl = 0;
		if (tSSRS.MaxRow > 0) {
			premctrl = Integer.parseInt(tSSRS.GetText(1, 1));
		}
		String tCommonUWGrade = "A";
		if (premctrl > 0) {
			tCommonUWGrade = "C6";
		}
		if (tCommonUWGrade.compareTo(tUWGrade) > 0) {
			tUWGrade = tCommonUWGrade;
		}

		logger.debug("团单自核的返回的核保级别:" + tUWGrade);
		if (tUWGrade.compareTo("D2") > 0) {
			tUWGrade = "D2";
		}
		return tUWGrade;
	}

	private float parseFloat(String s) {
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else {
				tmp = "-1";
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		// logger.debug("tmp:"+tmp+" f1:"+f1);
		return f1;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol(LCGrpPolSchema tLCGrpPolSchema,
			LMUWSet tLMUWSetUnpass) {
		// 保单
		if (preparePol(tLCGrpPolSchema) == false) {
			return false;
		}

		// 核保信息
		if (preparePolUW(tLCGrpPolSchema, tLMUWSetUnpass) == false) {
			return false;
		}

		LCGrpPolSchema tLCGrpPolSchemaDup = new LCGrpPolSchema();
		tLCGrpPolSchemaDup.setSchema(tLCGrpPolSchema);
		mAllLCGrpPolSet.add(tLCGrpPolSchemaDup);

		LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
		tLCGUWMasterSet.set(mLCGUWMasterSet);
		mAllLCGUWMasterSet.add(tLCGUWMasterSet);

		LCGUWSubSet tLCGUWSubSet = new LCGUWSubSet();
		tLCGUWSubSet.set(mLCGUWSubSet);
		mAllLCGUWSubSet.add(tLCGUWSubSet);

		LCGUWErrorSet tLCGUWErrorSet = new LCGUWErrorSet();
		tLCGUWErrorSet.set(mLCGUWErrorSet);
		mAllLCErrSet.add(tLCGUWErrorSet);

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOneCont(LCGrpContSchema tLCGrpContSchema,
			LMUWSet tLMUWSetContUnpass) {
		if (!prepareContUW(tLCGrpContSchema, tLMUWSetContUnpass)) {
			return false;
		}

		LCGrpContSchema tLCGrpContSchemaDup = new LCGrpContSchema();
		tLCGrpContSchemaDup.setSchema(tLCGrpContSchema);
		mAllLCGrpContSet.add(tLCGrpContSchemaDup);

		LCGCUWMasterSet tLCGCUWMasterSet = new LCGCUWMasterSet();
		tLCGCUWMasterSet.set(mLCGCUWMasterSet);
		mAllLCGCUWMasterSet.add(tLCGCUWMasterSet);

		LCGCUWSubSet tLCGCUWSubSet = new LCGCUWSubSet();
		tLCGCUWSubSet.set(mLCGCUWSubSet);
		mAllLCGCUWSubSet.add(tLCGCUWSubSet);

		LCGCUWErrorSet tLCGCUWErrorSet = new LCGCUWErrorSet();
		tLCGCUWErrorSet.set(mLCGCUWErrorSet);
		mAllLCGCUWErrorSet.add(tLCGCUWErrorSet);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private LCGrpContSchema getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperator = tGlobalInput.Operator;

		LCGrpContSchema tLCGrpContSchema = (LCGrpContSchema) cInputData
				.getObjectByObjectName("LCGrpContSchema", 0); // 从输入数据中获取合同记录的数据
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());

		if (tLCGrpContDB.getInfo()) { // 验证LCGrpCont表中是否存在该合同项记录
			return tLCGrpContDB.getSchema();
		} else {
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "集体合同号为" + tLCGrpContSchema.getGrpContNo()
					+ "的合同信息未查询到!";
			this.mErrors.addOneError(tError);

			return null;
		}
	}

	private boolean checkFinished(LCGrpContSchema tLCGrpContSchema) {
		if (tLCGrpContSchema.getUWFlag() != null
				&& !tLCGrpContSchema.getUWFlag().equals("")
				&& !tLCGrpContSchema.getUWFlag().equals("0")) {
			mContPassFlag = tLCGrpContSchema.getUWFlag();
			return true;
		}
		return false;
	}

	private boolean checkData(LCGrpContSchema tLCGrpContSchema) {
		// 校验核保级别
		// if (!checkUWGrade())
		// {
		// return false;
		// }

		// 校验是否复核
		// if (!checkApprove(tLCGrpContSchema))
		// {
		// return false;
		// }

		return true;
	}

	/**
	 * 校验投保单是否复核 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkApprove(LCGrpContSchema tLCGrpContSchema) {
		if ((tLCGrpContSchema.getApproveFlag() == null)
				|| !tLCGrpContSchema.getApproveFlag().equals("9")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号："
					+ tLCGrpContSchema.getProposalGrpContNo().trim() + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);

		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();

		if ((tUWPopedom == null) || tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private LMUWSet CheckKinds(LCGrpPolSchema tLCGrpPolSchema) {
		String tsql = "";
		LMUWSchema tLMUWSchema = new LMUWSchema();

		// 查询算法编码
		tsql = "select * from lmuw where (riskcode = '000000' and relapoltype = 'G' and uwtype = '11') or (riskcode = '"
				+ tLCGrpPolSchema.getRiskCode().trim()
				+ "' and relapoltype = 'G' and uwtype = '1')  order by calcode";

		LMUWDB tLMUWDB = new LMUWDB();

		LMUWSet tLMUWSet = tLMUWDB.executeQuery(tsql);

		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "CheckKinds";
			tError.errorMessage = tLCGrpPolSchema.getRiskCode().trim()
					+ "险种核保信息查询失败!";
			this.mErrors.addOneError(tError);
			tLMUWSet.clear();

			return null;
		}

		return tLMUWSet;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private LMUWSet CheckKinds2(LCGrpPolSchema tLCGrpPolSchema) {
		String tsql = "";
		LMUWSchema tLMUWSchema = new LMUWSchema();

		// 查询算法编码
		tsql = "select * from lmuw where riskcode = '000000' and relapoltype = 'G' and uwtype = '12'";

		LMUWDB tLMUWDB = new LMUWDB();

		LMUWSet tLMUWSet = tLMUWDB.executeQuery(tsql);

		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "CheckKinds2";
			tError.errorMessage = tLCGrpPolSchema.getRiskCode().trim()
					+ "险种信息核保查询失败!";
			this.mErrors.addOneError(tError);
			tLMUWSet.clear();

			return null;
		}

		return tLMUWSet;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private LMUWSet CheckKinds3() {
		String tsql = "";
		LMUWSchema tLMUWSchema = new LMUWSchema();

		// 查询算法编码
		tsql = "select * from lmuw where riskcode = '000000' and relapoltype = 'G' and uwtype = '19'";

		LMUWDB tLMUWDB = new LMUWDB();

		LMUWSet tLMUWSet = tLMUWDB.executeQuery(tsql);

		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "CheckKinds3";
			tError.errorMessage = "集体合同险种核保信息查询失败!";
			this.mErrors.addOneError(tError);
			tLMUWSet.clear();

			return null;
		}

		return tLMUWSet;
	}

	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckPolInit(LCGrpPolSchema tLCGrpPolSchema) {
		mCalBase = new CalBase();
		mCalBase.setPrem(tLCGrpPolSchema.getPrem());
		mCalBase.setGet(tLCGrpPolSchema.getAmnt());
		mCalBase.setMult(tLCGrpPolSchema.getMult());

		mCalBase.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
		mCalBase.setGrpContNo(mGrpContNo);

	}

	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckContInit(LCGrpContSchema tLCGrpContSchema) {
		mCalBase = new CalBase();
		mCalBase.setPrem(tLCGrpContSchema.getPrem());
		mCalBase.setGet(tLCGrpContSchema.getAmnt());
		mCalBase.setMult(tLCGrpContSchema.getMult());
		mCalBase.setGrpContNo(mGrpContNo);
		mCalBase.setCustomerNo(tLCGrpContSchema.getAppntNo());
	}

	/**
	 * 个人单核保 输出：如果发生错误则返回false,否则返回true
	 */
	private double CheckPol(String tRiskCode) {
		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);

		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("Years", mCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("ValiDate", "");
		mCalculator.addBasicFactor("Count", mCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("GrpPolNo", mCalBase.getGrpPolNo());
		mCalculator.addBasicFactor("GrpContNo", mCalBase.getGrpContNo());

		// mCalculator.addBasicFactor("InsuredNo", tInsuredNo);
		// //tLCGrpPolSchema.getInsuredNo());;
		mCalculator.addBasicFactor("RiskCode", tRiskCode); // tLCGrpPolSchema.getRiskCode());;

		String tStr = "";
		tStr = mCalculator.calculate();

		if ((tStr == null) || tStr.trim().equals("")) {
			mValue = 0;
		} else {
			mValue = Double.parseDouble(tStr);
		}

		// logger.debug(mValue);

		return mValue;
	}

	/**
	 * 准备保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePol(LCGrpPolSchema tLCGrpPolSchema) {
		logger.debug("险种核保标志" + mPolPassFlag);
		/*-- add by heyq for NCL 将复核功能和自核合并--*/
		tLCGrpPolSchema.setApproveFlag(mApproveFlag);
		tLCGrpPolSchema.setApproveCode(mOperator);
		tLCGrpPolSchema.setApproveDate(PubFun.getCurrentDate());
		tLCGrpPolSchema.setApproveTime(PubFun.getCurrentTime());
		/** --end add 20050530----------------------* */
		tLCGrpPolSchema.setUWFlag(mPolPassFlag);
		tLCGrpPolSchema.setUWDate(PubFun.getCurrentDate());
		tLCGrpPolSchema.setUWTime(PubFun.getCurrentTime());
		tLCGrpPolSchema.setUWOperator(mOperator);
		tLCGrpPolSchema.setUWDate(PubFun.getCurrentDate());
		tLCGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGrpPolSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 准备合同核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareContUW(LCGrpContSchema tLCGrpContSchema,
			LMUWSet tLMUWSetContUnpass) {
		/*-- add by heyq for NCL 将复核功能和自核合并--*/
		tLCGrpContSchema.setApproveFlag(mApproveFlag);
		tLCGrpContSchema.setApproveCode(mOperator);
		tLCGrpContSchema.setApproveDate(PubFun.getCurrentDate());
		tLCGrpContSchema.setApproveTime(PubFun.getCurrentTime());
		/** --end add 20050530----------------------* */

		tLCGrpContSchema.setUWFlag(mContPassFlag);
		tLCGrpContSchema.setUWDate(PubFun.getCurrentDate());
		tLCGrpContSchema.setUWTime(PubFun.getCurrentTime());
		tLCGrpContSchema.setUWOperator(mOperator);
		tLCGrpContSchema.setUWDate(PubFun.getCurrentDate());
		tLCGrpContSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGrpContSchema.setModifyTime(PubFun.getCurrentTime());

		// 合同核保主表
		boolean firstUW = true;
		LCGCUWMasterSchema tLCGCUWMasterSchema = new LCGCUWMasterSchema();
		LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
		tLCGCUWMasterDB.setGrpContNo(mGrpContNo);

		LCGCUWMasterSet tLCGCUWMasterSet = new LCGCUWMasterSet();
		tLCGCUWMasterSet = tLCGCUWMasterDB.query();

		if (tLCGCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mGrpContNo + "合同核保总表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (tLCGCUWMasterSet.size() == 0) {
			tLCGCUWMasterSchema.setGrpContNo(mGrpContNo);
			tLCGCUWMasterSchema.setProposalGrpContNo(tLCGrpContSchema
					.getProposalGrpContNo());
			tLCGCUWMasterSchema.setUWNo(1);
			tLCGCUWMasterSchema.setAgentCode(tLCGrpContSchema.getAgentCode());
			tLCGCUWMasterSchema.setAgentGroup(tLCGrpContSchema.getAgentGroup());
			tLCGCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCGCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCGCUWMasterSchema.setPostponeDay("");
			tLCGCUWMasterSchema.setPostponeDate("");
			tLCGCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCGCUWMasterSchema.setState(mContPassFlag);
			tLCGCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCGCUWMasterSchema.setHealthFlag("0");
			tLCGCUWMasterSchema.setSpecFlag("0");
			tLCGCUWMasterSchema.setQuesFlag("0");
			tLCGCUWMasterSchema.setReportFlag("0");
			tLCGCUWMasterSchema.setChangePolFlag("0");
			tLCGCUWMasterSchema.setPrintFlag("0");
			tLCGCUWMasterSchema.setPrintFlag2("0");
			tLCGCUWMasterSchema.setManageCom(tLCGrpContSchema.getManageCom());
			tLCGCUWMasterSchema.setUWIdea("");
			tLCGCUWMasterSchema.setUpReportContent("");
			tLCGCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCGCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLCGCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLCGCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			firstUW = false;
			tLCGCUWMasterSchema = tLCGCUWMasterSet.get(1);
			tLCGCUWMasterSchema.setUWNo(tLCGCUWMasterSchema.getUWNo() + 1);
			tLCGCUWMasterSchema.setState(mContPassFlag);
			tLCGCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCGCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCGCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCGCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCGCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCGCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		}

		mLCGCUWMasterSet.clear();
		mLCGCUWMasterSet.add(tLCGCUWMasterSchema);

		// 合同核保轨迹表
		LCGCUWSubSchema tLCGCUWSubSchema = new LCGCUWSubSchema();
		LCGCUWSubDB tLCGCUWSubDB = new LCGCUWSubDB();
		tLCGCUWSubDB.setGrpContNo(mGrpContNo);

		LCGCUWSubSet tLCGCUWSubSet = new LCGCUWSubSet();
		tLCGCUWSubSet = tLCGCUWSubDB.query();

		if (tLCGCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mGrpContNo + "合同核保轨迹表查失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int nUWNo = tLCGCUWSubSet.size();

		if (nUWNo > 0) {
			tLCGCUWSubSchema.setUWNo(++nUWNo); // 第几次核保
		} else {
			tLCGCUWSubSchema.setUWNo(1); // 第1次核保
		}

		tLCGCUWSubSchema.setGrpContNo(tLCGCUWMasterSchema.getGrpContNo());
		tLCGCUWSubSchema.setProposalGrpContNo(tLCGCUWMasterSchema
				.getProposalGrpContNo());
		tLCGCUWSubSchema.setAgentCode(tLCGCUWMasterSchema.getAgentCode());
		tLCGCUWSubSchema.setAgentGroup(tLCGCUWMasterSchema.getAgentGroup());
		tLCGCUWSubSchema.setUWGrade(tLCGCUWMasterSchema.getUWGrade()); // 核保级别
		tLCGCUWSubSchema.setAppGrade(tLCGCUWMasterSchema.getAppGrade()); // 申请级别
		tLCGCUWSubSchema.setAutoUWFlag(tLCGCUWMasterSchema.getAutoUWFlag());
		tLCGCUWSubSchema.setState(tLCGCUWMasterSchema.getState());
		tLCGCUWSubSchema.setPassFlag(tLCGCUWMasterSchema.getState());
		tLCGCUWSubSchema.setPostponeDay(tLCGCUWMasterSchema.getPostponeDay());
		tLCGCUWSubSchema.setPostponeDate(tLCGCUWMasterSchema.getPostponeDate());
		tLCGCUWSubSchema.setUpReportContent(tLCGCUWMasterSchema
				.getUpReportContent());
		tLCGCUWSubSchema.setHealthFlag(tLCGCUWMasterSchema.getHealthFlag());
		tLCGCUWSubSchema.setSpecFlag(tLCGCUWMasterSchema.getSpecFlag());
		tLCGCUWSubSchema.setSpecReason(tLCGCUWMasterSchema.getSpecReason());
		tLCGCUWSubSchema.setQuesFlag(tLCGCUWMasterSchema.getQuesFlag());
		tLCGCUWSubSchema.setReportFlag(tLCGCUWMasterSchema.getReportFlag());
		tLCGCUWSubSchema.setChangePolFlag(tLCGCUWMasterSchema
				.getChangePolFlag());
		tLCGCUWSubSchema.setChangePolReason(tLCGCUWMasterSchema
				.getChangePolReason());
		tLCGCUWSubSchema.setAddPremReason(tLCGCUWMasterSchema
				.getAddPremReason());
		tLCGCUWSubSchema.setPrintFlag(tLCGCUWMasterSchema.getPrintFlag());
		tLCGCUWSubSchema.setPrintFlag2(tLCGCUWMasterSchema.getPrintFlag2());
		tLCGCUWSubSchema.setUWIdea(tLCGCUWMasterSchema.getUWIdea());
		tLCGCUWSubSchema.setOperator(tLCGCUWMasterSchema.getOperator()); // 操作员
		tLCGCUWSubSchema.setManageCom(tLCGCUWMasterSchema.getManageCom());
		tLCGCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLCGCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLCGCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		mLCGCUWSubSet.clear();
		mLCGCUWSubSet.add(tLCGCUWSubSchema);

		// 核保错误信息表
		LCGCUWErrorSchema tLCGCUWErrorSchema = new LCGCUWErrorSchema();
		LCGCUWErrorDB tLCGCUWErrorDB = new LCGCUWErrorDB();
		tLCGCUWErrorDB.setGrpContNo(mGrpContNo);

		LCGCUWErrorSet tLCGCUWErrorSet = new LCGCUWErrorSet();
		tLCGCUWErrorSet = tLCGCUWErrorDB.query();

		if (tLCGCUWErrorDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGCUWErrorDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mGrpContNo + "合同错误信息表查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		tLCGCUWErrorSchema.setSerialNo("0");

		if (nUWNo > 0) {
			tLCGCUWErrorSchema.setUWNo(nUWNo);
		} else {
			tLCGCUWErrorSchema.setUWNo(1);
		}

		tLCGCUWErrorSchema.setGrpContNo(mGrpContNo);
		tLCGCUWErrorSchema.setProposalGrpContNo(tLCGCUWSubSchema
				.getProposalGrpContNo());
		tLCGCUWErrorSchema.setManageCom(tLCGCUWSubSchema.getManageCom());
		tLCGCUWErrorSchema.setUWRuleCode(""); // 核保规则编码
		tLCGCUWErrorSchema.setUWError(""); // 核保出错信息
		tLCGCUWErrorSchema.setCurrValue(""); // 当前值
		tLCGCUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGCUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
		tLCGCUWErrorSchema.setUWPassFlag(mPolPassFlag);

		// 取核保错误信息
		mLCGCUWErrorSet.clear();

		int merrcount = tLMUWSetContUnpass.size();

		if (merrcount > 0) {
			for (int i = 1; i <= merrcount; i++) {
				// 取出错信息
				LMUWSchema tLMUWSchema = new LMUWSchema();
				tLMUWSchema = tLMUWSetContUnpass.get(i);

				// 生成流水号
				String tserialno = "" + i;

				tLCGCUWErrorSchema.setSerialNo(tserialno);
				tLCGCUWErrorSchema.setUWRuleCode(tLMUWSchema.getUWCode()); // 核保规则编码
				tLCGCUWErrorSchema.setUWError(tLMUWSchema.getRemark().trim()); // 核保出错信息，即核保规则的文字描述内容
				tLCGCUWErrorSchema.setUWGrade(tLMUWSchema.getUWGrade());
				tLCGCUWErrorSchema.setCurrValue(""); // 当前值

				LCGCUWErrorSchema ttLCGCUWErrorSchema = new LCGCUWErrorSchema();
				ttLCGCUWErrorSchema.setSchema(tLCGCUWErrorSchema);
				mLCGCUWErrorSet.add(ttLCGCUWErrorSchema);
			}
		}

		return true;
	}

	/**
	 * 准备险种核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePolUW(LCGrpPolSchema tLCGrpPolSchema,
			LMUWSet tLMUWSetUnpass) {
		int tuwno = 0;
		LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
		LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB();
		tLCGUWMasterDB.setGrpPolNo(mGrpPolNo);

		LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
		tLCGUWMasterSet = tLCGUWMasterDB.query();

		if (tLCGUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mGrpPolNo + "个人核保总表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int n = tLCGUWMasterSet.size();

		if (n == 0) {
			tLCGUWMasterSchema.setGrpContNo(mGrpContNo);
			tLCGUWMasterSchema.setGrpPolNo(mGrpPolNo);
			tLCGUWMasterSchema.setProposalGrpContNo(mProposalGrpContNo);
			tLCGUWMasterSchema.setGrpProposalNo(tLCGrpPolSchema
					.getGrpProposalNo());
			tLCGUWMasterSchema.setUWNo(1);
			tLCGUWMasterSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());
			tLCGUWMasterSchema.setAgentGroup(tLCGrpPolSchema.getAgentGroup());
			tLCGUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCGUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCGUWMasterSchema.setPostponeDay("");
			tLCGUWMasterSchema.setPostponeDate("");
			tLCGUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCGUWMasterSchema.setState(mPolPassFlag);
			tLCGUWMasterSchema.setPassFlag(mPolPassFlag);
			tLCGUWMasterSchema.setHealthFlag("0");
			tLCGUWMasterSchema.setSpecFlag("0");
			tLCGUWMasterSchema.setQuesFlag("0");
			tLCGUWMasterSchema.setReportFlag("0");
			tLCGUWMasterSchema.setChangePolFlag("0");
			tLCGUWMasterSchema.setPrintFlag("0");
			tLCGUWMasterSchema.setManageCom(tLCGrpPolSchema.getManageCom());
			tLCGUWMasterSchema.setUWIdea("");
			tLCGUWMasterSchema.setUpReportContent("");
			tLCGUWMasterSchema.setOperator(mOperator); // 操作员
			tLCGUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLCGUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLCGUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else if (n == 1) {
			tLCGUWMasterSchema = tLCGUWMasterSet.get(1);

			tuwno = tLCGUWMasterSchema.getUWNo();
			tuwno = tuwno + 1;

			tLCGUWMasterSchema.setUWNo(tuwno);
			tLCGUWMasterSchema.setProposalGrpContNo(mProposalGrpContNo);
			tLCGUWMasterSchema.setState(mPolPassFlag);
			tLCGUWMasterSchema.setPassFlag(mPolPassFlag);
			tLCGUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCGUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCGUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCGUWMasterSchema.setOperator(mOperator); // 操作员
			tLCGUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mGrpPolNo + "个人核保总表取数据不唯一!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCGUWMasterSet.clear();
		mLCGUWMasterSet.add(tLCGUWMasterSchema);

		// 核保轨迹表
		LCGUWSubSchema tLCGUWSubSchema = new LCGUWSubSchema();
		LCGUWSubDB tLCGUWSubDB = new LCGUWSubDB();
		tLCGUWSubDB.setGrpPolNo(mGrpPolNo);

		LCGUWSubSet tLCGUWSubSet = new LCGUWSubSet();
		tLCGUWSubSet = tLCGUWSubDB.query();

		if (tLCGUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mGrpPolNo + "个人核保轨迹表查失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int m = tLCGUWSubSet.size();

		if (m > 0) {
			tLCGUWSubSchema.setUWNo(++m); // 第几次核保
		} else {
			tLCGUWSubSchema.setUWNo(1); // 第1次核保
		}

		tLCGUWSubSchema.setGrpContNo(mGrpContNo);
		tLCGUWSubSchema.setGrpPolNo(mGrpPolNo);
		tLCGUWSubSchema.setProposalGrpContNo(tLCGUWMasterSchema
				.getProposalGrpContNo());
		tLCGUWSubSchema.setGrpProposalNo(tLCGUWMasterSchema.getGrpProposalNo());
		tLCGUWSubSchema.setAgentCode(tLCGUWMasterSchema.getAgentCode());
		tLCGUWSubSchema.setAgentGroup(tLCGUWMasterSchema.getAgentGroup());
		tLCGUWSubSchema.setUWGrade(tLCGUWMasterSchema.getUWGrade()); // 核保级别
		tLCGUWSubSchema.setAppGrade(tLCGUWMasterSchema.getAppGrade()); // 申请级别
		tLCGUWSubSchema.setAutoUWFlag(tLCGUWMasterSchema.getAutoUWFlag());
		tLCGUWSubSchema.setState(tLCGUWMasterSchema.getState());
		tLCGUWSubSchema.setPassFlag(tLCGUWMasterSchema.getState());
		tLCGUWSubSchema.setPostponeDay(tLCGUWMasterSchema.getPostponeDay());
		tLCGUWSubSchema.setPostponeDate(tLCGUWMasterSchema.getPostponeDate());
		tLCGUWSubSchema.setUpReportContent(tLCGUWMasterSchema
				.getUpReportContent());
		tLCGUWSubSchema.setHealthFlag(tLCGUWMasterSchema.getHealthFlag());
		tLCGUWSubSchema.setSpecFlag(tLCGUWMasterSchema.getSpecFlag());
		tLCGUWSubSchema.setSpecReason(tLCGUWMasterSchema.getSpecReason());
		tLCGUWSubSchema.setQuesFlag(tLCGUWMasterSchema.getQuesFlag());
		tLCGUWSubSchema.setReportFlag(tLCGUWMasterSchema.getReportFlag());
		tLCGUWSubSchema.setChangePolFlag(tLCGUWMasterSchema.getChangePolFlag());
		tLCGUWSubSchema.setChangePolReason(tLCGUWMasterSchema
				.getChangePolReason());
		tLCGUWSubSchema.setAddPremReason(tLCGUWMasterSchema.getAddPremReason());
		tLCGUWSubSchema.setPrintFlag(tLCGUWMasterSchema.getPrintFlag());
		tLCGUWSubSchema.setPrintFlag2(tLCGUWMasterSchema.getPrintFlag2());
		tLCGUWSubSchema.setUWIdea(tLCGUWMasterSchema.getUWIdea());
		tLCGUWSubSchema.setOperator(tLCGUWMasterSchema.getOperator()); // 操作员
		tLCGUWSubSchema.setManageCom(tLCGUWMasterSchema.getManageCom());
		tLCGUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLCGUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLCGUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		mLCGUWSubSet.clear();
		mLCGUWSubSet.add(tLCGUWSubSchema);

		// 核保错误信息表
		LCGUWErrorSchema tLCGUWErrorSchema = new LCGUWErrorSchema();
		LCGUWErrorDB tLCGUWErrorDB = new LCGUWErrorDB();
		tLCGUWErrorDB.setGrpPolNo(mGrpPolNo);

		LCGUWErrorSet tLCGUWErrorSet = new LCGUWErrorSet();
		tLCGUWErrorSet = tLCGUWErrorDB.query();

		if (tLCGUWErrorDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWErrorDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mGrpPolNo + "个人错误信息表查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		tLCGUWErrorSchema.setSerialNo("0");

		if (m > 0) {
			tLCGUWErrorSchema.setUWNo(m);
		} else {
			tLCGUWErrorSchema.setUWNo(1);
		}

		tLCGUWErrorSchema.setGrpContNo(mGrpContNo);
		tLCGUWErrorSchema.setProposalGrpContNo(mProposalGrpContNo);
		tLCGUWErrorSchema.setGrpPolNo(mGrpPolNo);
		tLCGUWErrorSchema.setGrpProposalNo(tLCGrpPolSchema.getGrpProposalNo());
		tLCGUWErrorSchema.setManageCom(tLCGrpPolSchema.getManageCom());
		tLCGUWErrorSchema.setUWRuleCode(""); // 核保规则编码
		tLCGUWErrorSchema.setUWError(""); // 核保出错信息
		tLCGUWErrorSchema.setCurrValue(""); // 当前值
		tLCGUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
		tLCGUWErrorSchema.setUWPassFlag(mPolPassFlag);

		// 取核保错误信息
		mLCGUWErrorSet.clear();

		int merrcount = tLMUWSetUnpass.size();

		if (merrcount > 0) {
			for (int i = 1; i <= merrcount; i++) {
				// 取出错信息
				LMUWSchema tLMUWSchema = new LMUWSchema();
				tLMUWSchema = tLMUWSetUnpass.get(i);

				// 生成流水号
				String tserialno = "" + i;

				tLCGUWErrorSchema.setSerialNo(tserialno);
				tLCGUWErrorSchema.setUWRuleCode(tLMUWSchema.getUWCode()); // 核保规则编码
				tLCGUWErrorSchema.setUWError(tLMUWSchema.getRemark().trim()); // 核保出错信息，即核保规则的文字描述内容
				tLCGUWErrorSchema.setUWGrade(tLMUWSchema.getUWGrade());
				tLCGUWErrorSchema.setCurrValue(""); // 当前值

				LCGUWErrorSchema ttLCGUWErrorSchema = new LCGUWErrorSchema();
				ttLCGUWErrorSchema.setSchema(tLCGUWErrorSchema);
				mLCGUWErrorSet.add(ttLCGUWErrorSchema);
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareOutputData(LCGrpContSchema tLCGrpContSchema) {

		mMap.put(tLCGrpContSchema, "UPDATE");
		mMap.put(mLCGCUWMasterSet.get(1), "DELETE&INSERT");
		mMap.put(mLCGCUWSubSet, "INSERT");
		mMap.put(mLCGCUWErrorSet, "INSERT");

		mMap.put(mAllLCGrpPolSet, "UPDATE");

		// int n = mAllLCGUWMasterSet.size();

		// for (int i = 1; i <= n; i++)
		// {
		// LCGUWMasterSchema tLCGUWMasterSchema = mAllLCGUWMasterSet.get(i);
		// mMap.put(tLCGUWMasterSchema, "DELETE&INSERT");
		// }
		mMap.put(mAllLCGUWMasterSet, "DELETE&INSERT");

		mMap.put(mAllLCGUWSubSet, "INSERT");
		mMap.put(mAllLCErrSet, "INSERT");

		mResult.add(mMap);
		mResult.add(mContPassFlag);
		return true;
	}

	/**
	 * //返回核保师级别
	 */
	public String getUWGrade() {
		return mUWGrade;
	}

	/**
	 * 
	 */
	public VData getResult() {
		return mResult;
	}
}
