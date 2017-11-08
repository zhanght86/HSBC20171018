package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔新增立案更新报案表系列的报案号为新的立案号_
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangzheng
 * @version 1.0
 */
public class LLClaimUpdateRptNoBL {
private static Logger logger = Logger.getLogger(LLClaimUpdateRptNoBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 全局变量
	private MMap map = new MMap();
	private GlobalInput mG = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	// 事件信息
	private LLAccidentSchema mLLAccidentSchema = new LLAccidentSchema();
	private LLAccidentSubSchema mLLAccidentSubSchema = new LLAccidentSubSchema();
	
	// 报案相关
	private LLReportSchema mLLReportSchema = new LLReportSchema();
	private LLSubReportSet mLLSubReportSet = new LLSubReportSet();
	private LLReportRelaSchema mLLReportRelaSchema = new LLReportRelaSchema();
	private LLCaseRelaSchema mLLCaseRelaSchema = new LLCaseRelaSchema();
	private LLReportReasonSet mLLReportReasonSet = new LLReportReasonSet();
	private LLReportAffixSet mLLReportAffixSet = new LLReportAffixSet();
	
	// 立案相关
	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
	private LLCaseSchema mLLCaseSchema = new LLCaseSchema();
	private LLCaseSet mLLCaseSet = new LLCaseSet();
	private LLAppClaimReasonSchema mLLAppClaimReasonSchema = null;
	private LLAppClaimReasonSet mLLAppClaimReasonSet = new LLAppClaimReasonSet();
	private LLAffixSet mLLAffixSet = new LLAffixSet();
	
	//调查,呈报系列表
	private LLInqApplySet mLLInqApplySet=null;//调查申请信息
	private LLInqCourseSet mLLInqCourseSet=null;//调查过程信息
	private LLInqConclusionSet mLLInqConclusionSet=null;//调查结论信息
	private LLInqCertificateSet mLLInqCertificateSet=null;//调查过程单证信息
	private LLInqFeeSet mLLInqFeeSet=null;//调查费用信息
	private LLSubmitApplySet mLLSubmitApplySet=null;//呈报信息
	
		
	//保单挂起表
	private LCContHangUpStateSet mLCContHangUpStateSet=null;

	

	// 号码
	private String mRptNo = "";// 报案号
	private String mRgtNo = "";// 立案号
	
	private String mIsAccExist="";//事件信息是否存在标志
	
	private String mGrpFlag="";//是否团险标志

	public LLClaimUpdateRptNoBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLClaimRegisterBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterBL after checkInputData----------");
		
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterBL after dealData----------");
		
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterBL after prepareOutputData----------");


		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		
		logger.debug("---LLClaimRegisterBL start getInputData()...");
		
		// 获取传入信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		
		mRptNo = (String) mTransferData.getValueByName("RptNo");
		logger.debug("******************mRptNo=" + mRptNo);
		
		mRgtNo = (String) mTransferData.getValueByName("RgtNo");
		logger.debug("******************mRgtNo=" + mRgtNo);
		
		mIsAccExist = (String) mTransferData.getValueByName("IsAccExist");
		mGrpFlag = (String) mTransferData.getValueByName("GrpFlag");
		logger.debug("******************mIsAccExist=" + mIsAccExist);
		
		//事件信息
		mLLAccidentSchema = (LLAccidentSchema) mInputData.getObjectByObjectName("LLAccidentSchema", 0);
		mLLAccidentSubSchema = (LLAccidentSubSchema) mInputData.getObjectByObjectName("LLAccidentSubSchema", 0);
		
		//报案信息
		mLLReportSchema = (LLReportSchema) mInputData.getObjectByObjectName("LLReportSchema", 0);
		mLLSubReportSet = (LLSubReportSet) mInputData.getObjectByObjectName("LLSubReportSet", 0);
		mLLReportRelaSchema = (LLReportRelaSchema) mInputData.getObjectByObjectName("LLReportRelaSchema", 0);
		mLLCaseRelaSchema = (LLCaseRelaSchema) mInputData.getObjectByObjectName("LLCaseRelaSchema", 0);
		mLLReportReasonSet = (LLReportReasonSet) mInputData.getObjectByObjectName("LLReportReasonSet", 0);
		mLLReportAffixSet = (LLReportAffixSet) mInputData.getObjectByObjectName("LLReportAffixSet", 0);
		
		//立案信息
		mLLRegisterSchema = (LLRegisterSchema) mInputData.getObjectByObjectName("LLRegisterSchema", 0);
		mLLCaseSet = (LLCaseSet) mInputData.getObjectByObjectName("LLCaseSet", 0);
		mLLAppClaimReasonSet = (LLAppClaimReasonSet) mInputData.getObjectByObjectName("LLAppClaimReasonSet", 0);
		mLLAffixSet = (LLAffixSet) mInputData.getObjectByObjectName("LLAffixSet", 0);

		return true;
		
	}

	/**
	 * 校验传入的报案信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		
		logger.debug("----------begin checkInputData----------");
		
		if(mRgtNo==null||mRgtNo.equals("")){
			CError.buildErr(this, "理赔换号失败,待替换立案号为空!");
			return false;
		}
		
		if(mRptNo==null||mRptNo.equals("")){
			CError.buildErr(this, "理赔换号失败,待替换报案号为空!");
			return false;
		}
		
		if(mIsAccExist==null||mIsAccExist.equals("")){
			CError.buildErr(this, "理赔换号失败!");
			return false;
		}
		
		
		
		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		
		logger.debug("---LLClaimUpdateRptNoBL start dealData()...");
		
		//报案号替换-理赔业务数据+保单挂起中的信息
		if(!updateRptNo())
		{
			// @@错误处理
			return false;
		}
		
		//2009-02-10  判断修改个险工作流还是修改团险工作流
		if(!"1".equals(mGrpFlag)){
			//报案号替换-工作流中的案件信息
			if(!updateLwmission())
			{
				// @@错误处理
				return false;
			}
		}else{
			if(!updateGrpLwmission()){
				CError.buildErr(this, "替换团险工作流中报案号失败！");
				return false;
			}
		}
		
		logger.debug("---LLClaimUpdateRptNoBL end dealData()...");
		return true;
	}
	
	/**
	 * 替换工作流中的报案号为立案号
	 * 
	 * @return boolean
	 */
	private boolean updateLwmission() {
     
		logger.debug("---LLClaimUpdateRptNoBL start updateLwmission()...");
		
		logger.debug("*******被替换的报案号"+mRptNo);
		logger.debug("*******接替的立案号"+mRgtNo);
		
		// 更新0000005005-报案确认节点:LBMission表
		String sql1="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005005' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000005005-报案确认节点:LBMission表sql:"+sql1);
		
		String sql2="";
		// 更新0000005015-立案确认节点:LWMission表
		if(this.mOperate.equals("INSERT"))
		{
			String tAcceptedDate = (String) mLLRegisterSchema.getAcceptedDate();
			sql2="update lwmission set missionprop1='"+"?rgtno?"+"',missionprop2='20',missionprop21='"+"?acpdate?"+"' where activityid ='0000005015' and missionprop1='"+"?rptno?"+"'";
		}
		else
		{
			sql2="update lwmission set missionprop1='"+"?rgtno?"+"',missionprop2='20' where activityid ='0000005015' and missionprop1='"+"?rptno?"+"'";
		}
		
		logger.debug("更新0000005015-立案确认节点:LWMission表sql:"+sql2);

		// 更新0000005125-理赔调查申请节点
		String sql31="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005125' and missionprop1='"+"?rptno?"+"'";
		String sql32="update lwmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005125' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000005125-理赔调查申请节点:LBMission表sql:"+sql31);
		logger.debug("更新0000005125-理赔调查申请节点:LWMission表sql:"+sql32);
		
		// 更新0000005145-理赔调查过程录入节点
		String sql41="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005145' and missionprop1='"+"?rptno?"+"'";
		String sql42="update lwmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005145' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000005145-理赔调查过程录入节点:LBMission表sql:"+sql41);
		logger.debug("更新0000005145-理赔调查过程录入节点:LWMission表sql:"+sql42);
		
		// 更新0000005165-理赔调查结论录入节点
		String sql51="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005165' and missionprop1='"+"?rptno?"+"'";
		String sql52="update lwmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005165' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000005165-理赔调查结论录入节点:LBMission表sql:"+sql51);
		logger.debug("更新0000005165-理赔调查结论录入节点:LWMission表sql:"+sql52);
		
		// 更新0000005105-理赔呈报录入节点
		String sql61="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005105' and missionprop1='"+"?rptno?"+"'";
		String sql62="update lwmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005105' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000005105-理赔呈报录入节点:LBMission表sql:"+sql61);
		logger.debug("更新0000005105-理赔呈报录入节点:LWMission表sql:"+sql62);
		
		// 更新扫描件中的案件号
		String sql71="update es_doc_main set doccode='"+"?rgtno?"+"' where doccode='"+"?rptno?"+"'";
		String sql72="update es_doc_relation set bussno='"+"?rgtno?"+"' where bussno='"+"?rptno?"+"'";
		logger.debug("更新es_doc_main表sql:"+sql71);
		logger.debug("更新es_doc_relation表sql:"+sql72);
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql1);
		sqlbv1.put("rgtno", mRgtNo);
		sqlbv1.put("rptno", mRptNo);
		map.put(sqlbv1, "UPDATE");
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql2);
		sqlbv2.put("rgtno", mRgtNo);
		sqlbv2.put("rptno", mRptNo);
		sqlbv2.put("acpdate", (String) mLLRegisterSchema.getAcceptedDate());
		map.put(sqlbv2, "UPDATE");
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(sql31);
		sqlbv3.put("rgtno", mRgtNo);
		sqlbv3.put("rptno", mRptNo);
		map.put(sqlbv3, "UPDATE");
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(sql32);
		sqlbv4.put("rgtno", mRgtNo);
		sqlbv4.put("rptno", mRptNo);
		map.put(sqlbv4, "UPDATE");
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(sql41);
		sqlbv5.put("rgtno", mRgtNo);
		sqlbv5.put("rptno", mRptNo);
		map.put(sqlbv5, "UPDATE");
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(sql42);
		sqlbv6.put("rgtno", mRgtNo);
		sqlbv6.put("rptno", mRptNo);
		map.put(sqlbv6, "UPDATE");
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(sql51);
		sqlbv7.put("rgtno", mRgtNo);
		sqlbv7.put("rptno", mRptNo);
		map.put(sqlbv7, "UPDATE");
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(sql52);
		sqlbv8.put("rgtno", mRgtNo);
		sqlbv8.put("rptno", mRptNo);
		map.put(sqlbv8, "UPDATE");
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(sql61);
		sqlbv9.put("rgtno", mRgtNo);
		sqlbv9.put("rptno", mRptNo);
		map.put(sqlbv9, "UPDATE");
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(sql62);
		sqlbv10.put("rgtno", mRgtNo);
		sqlbv10.put("rptno", mRptNo);
		map.put(sqlbv10, "UPDATE");
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(sql71);
		sqlbv11.put("rgtno", mRgtNo);
		sqlbv11.put("rptno", mRptNo);
		map.put(sqlbv11, "UPDATE");
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(sql72);
		sqlbv12.put("rgtno", mRgtNo);
		sqlbv12.put("rptno", mRptNo);
		map.put(sqlbv12, "UPDATE");
		

		//释放强引用
		sql1=null;
		sql2=null;
		sql31=null;
		sql32=null;
		sql41=null;
		sql42=null;
		sql51=null;
		sql52=null;
		sql61=null;
		sql62=null;
		sql71=null;
		sql72=null;
		
		logger.debug("---LLClaimUpdateRptNoBL end updateLwmission()...");
		return true;
	}
	
	
	/**
	 * 替换团险理赔工作流中的报案号为立案号
	 * 
	 * @return boolean
	 */
	private boolean updateGrpLwmission() {

		logger.debug("---LLClaimUpdateRptNoBL start updateGrpLwmission()...");
		
		logger.debug("*******被替换的报案号"+mRptNo);
		logger.debug("*******接替的立案号"+mRgtNo);
		
		// 更新0000009005-报案确认节点:LBMission表
		String sql1="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000009005' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000009005-报案确认节点:LBMission表sql:"+sql1);
		
		
		// 更新0000009015-立案确认节点:LWMission表
		String sql2;
		
		if(this.mOperate.equals("INSERT"))
		{
			String tAcceptedDate = (String) mLLRegisterSchema.getAcceptedDate();
			sql2="update lwmission set missionprop1='"+"?rgtno?"+"',missionprop2='20',missionprop21='"+"?acpdate?"+"' where activityid ='0000009015' and missionprop1='"+"?rptno?"+"'";
			
		}
		else
		{
			sql2 ="update lwmission set missionprop1='"+"?rgtno?"+"',missionprop2='20' where activityid ='0000009015' and missionprop1='"+"?rptno?"+"'";
		
		}
		
		logger.debug("更新0000009015-立案确认节点:LWMission表sql:"+sql2);
		
//		 更新0000005105-理赔调查申请节点
		String sql21="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005105' and missionprop1='"+"?rptno?"+"'";
		String sql22="update lwmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000005105' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000005105-立案确认节点:LBMission表sql:"+sql21);
		logger.debug("更新0000005105-立案确认节点:LWMission表sql:"+sql22);

		// 更新0000009125-理赔调查申请节点
		String sql31="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000009125' and missionprop1='"+"?rptno?"+"'";
		String sql32="update lwmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000009125' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000009125-理赔调查申请节点:LBMission表sql:"+sql31);
		logger.debug("更新0000009125-理赔调查申请节点:LWMission表sql:"+sql32);
		
		// 更新0000009145-理赔调查过程录入节点
		String sql41="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000009145' and missionprop1='"+"?rptno?"+"'";
		String sql42="update lwmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000009145' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000009145-理赔调查过程录入节点:LBMission表sql:"+sql41);
		logger.debug("更新0000009145-理赔调查过程录入节点:LWMission表sql:"+sql42);
		
		// 更新0000009165-理赔调查结论录入节点
		String sql51="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000009165' and missionprop1='"+"?rptno?"+"'";
		String sql52="update lwmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000009165' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000009165-理赔调查结论录入节点:LBMission表sql:"+sql51);
		logger.debug("更新0000009165-理赔调查结论录入节点:LWMission表sql:"+sql52);
		
		// 更新0000009105-理赔呈报录入节点
		String sql61="update lbmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000009105' and missionprop1='"+"?rptno?"+"'";
		String sql62="update lwmission set missionprop1='"+"?rgtno?"+"' where activityid ='0000009105' and missionprop1='"+"?rptno?"+"'";
		logger.debug("更新0000009105-理赔呈报录入节点:LBMission表sql:"+sql61);
		logger.debug("更新0000009105-理赔呈报录入节点:LWMission表sql:"+sql62);
		
		String sql71="update es_doc_main set doccode='"+"?rgtno?"+"' where doccode='"+"?rptno?"+"'";
		String sql72="update es_doc_relation set bussno='"+"?rgtno?"+"' where bussno='"+"?rptno?"+"'";
		logger.debug("更新es_doc_main表sql:"+sql71);
		logger.debug("更新es_doc_relation表sql:"+sql72);
		
		//更新LLStandPayInfo(预估金额信息表)表 由于个险不会用到这张表，所以无法放到个险团险的公共方法中
		String sql8="delete from LLStandPayInfo where caseno='"+"?rptno?"+"'";
		LLStandPayInfoDB tLLStandPayInfoDB = new LLStandPayInfoDB();
		LLStandPayInfoSet tLLStandPayInfoSet = new LLStandPayInfoSet();
		tLLStandPayInfoDB.setCaseNo(mRptNo);
		tLLStandPayInfoSet = tLLStandPayInfoDB.query();
		for(int i=1;i<=tLLStandPayInfoSet.size();i++){
			tLLStandPayInfoSet.get(i).setCaseNo(mRgtNo);
		}
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(sql8);
		sqlbv13.put("rgtno", mRgtNo);
		sqlbv13.put("rptno", mRptNo);
		map.put(sqlbv13, "DELETE");
		map.put(tLLStandPayInfoSet, "INSERT");
		
		
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(sql1);
		sqlbv14.put("rgtno", mRgtNo);
		sqlbv14.put("rptno", mRptNo);
		map.put(sqlbv14, "UPDATE");
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql(sql2);
		sqlbv15.put("rgtno", mRgtNo);
		sqlbv15.put("rptno", mRptNo);
		sqlbv15.put("acpdate", (String) mLLRegisterSchema.getAcceptedDate());
		map.put(sqlbv15, "UPDATE");
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql(sql21);
		sqlbv16.put("rgtno", mRgtNo);
		sqlbv16.put("rptno", mRptNo);
		map.put(sqlbv16, "UPDATE");
		SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		sqlbv17.sql(sql22);
		sqlbv17.put("rgtno", mRgtNo);
		sqlbv17.put("rptno", mRptNo);
		map.put(sqlbv17, "UPDATE");
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		sqlbv18.sql(sql31);
		sqlbv18.put("rgtno", mRgtNo);
		sqlbv18.put("rptno", mRptNo);
		map.put(sqlbv18, "UPDATE");
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql(sql32);
		sqlbv19.put("rgtno", mRgtNo);
		sqlbv19.put("rptno", mRptNo);
		map.put(sqlbv19, "UPDATE");
		
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql(sql41);
		sqlbv20.put("rgtno", mRgtNo);
		sqlbv20.put("rptno", mRptNo);
		map.put(sqlbv20, "UPDATE");
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql(sql42);
		sqlbv21.put("rgtno", mRgtNo);
		sqlbv21.put("rptno", mRptNo);
		map.put(sqlbv21, "UPDATE");
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql(sql51);
		sqlbv22.put("rgtno", mRgtNo);
		sqlbv22.put("rptno", mRptNo);
		map.put(sqlbv22, "UPDATE");
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(sql52);
		sqlbv23.put("rgtno", mRgtNo);
		sqlbv23.put("rptno", mRptNo);
		map.put(sqlbv23, "UPDATE");
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		sqlbv24.sql(sql61);
		sqlbv24.put("rgtno", mRgtNo);
		sqlbv24.put("rptno", mRptNo);
		map.put(sqlbv24, "UPDATE");
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql(sql62);
		sqlbv25.put("rgtno", mRgtNo);
		sqlbv25.put("rptno", mRptNo);
		map.put(sqlbv25, "UPDATE");
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		sqlbv26.sql(sql71);
		sqlbv26.put("rgtno", mRgtNo);
		sqlbv26.put("rptno", mRptNo);
		map.put(sqlbv26, "UPDATE");
		SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
		sqlbv27.sql(sql72);
		sqlbv27.put("rgtno", mRgtNo);
		sqlbv27.put("rptno", mRptNo);
		map.put(sqlbv27, "UPDATE");

		//释放强引用
		sql1=null;
		sql2=null;
		sql21=null;
		sql22=null;
		sql31=null;
		sql32=null;
		sql41=null;
		sql42=null;
		sql51=null;
		sql52=null;
		sql61=null;
		sql62=null;
		sql71=null;
		sql72=null;
		
		logger.debug("---LLClaimUpdateRptNoBL end updateGrpLwmission()...");
		return true;
	}
	
	/**
	 * 替换理赔业务表+保单挂起信息中的报案号为立案号
	 * 
	 * @return boolean
	 */
	private boolean updateRptNo() {

		logger.debug("---LLClaimUpdateRptNoBL start updateRptNo()...");
		
		logger.debug("*******被替换的报案号"+mRptNo);
		logger.debug("*******接替的立案号"+mRgtNo);
				
		/*
		 * *************************************************************************
		 * 
		 * llreport 报案信息
		 * 
		 * *************************************************************************
		 */	
		
		mLLReportSchema.setRptNo(mRgtNo);		
		
		String sql1="delete from llreport where rptno='"+"?rptno?"+"'";
		logger.debug("删除llreport表中赔案"+mRptNo+"记录的sql:"+sql1);
		SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
		sqlbv28.sql(sql1);
		sqlbv28.put("rptno", mRptNo);
		map.put(sqlbv28, "DELETE");
		map.put(mLLReportSchema, "INSERT");
		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLReport");
		
		
		
		/*
		 * *************************************************************************
		 * 
		 * llsubreport 报案分案信息
		 * 
		 * *************************************************************************
		 */	
		
		
		for(int i=1;i<=mLLSubReportSet.size();i++)
		{
			mLLSubReportSet.get(i).setSubRptNo(mRgtNo);
		}
		
		String sql2="";
		if(mLLSubReportSet.size()>0){
			
			sql2="delete from llsubreport where SubRptNo='"+"?rptno?"+"'";
			logger.debug("删除llsubreport表中赔案"+mRptNo+"记录的sql:"+sql2);
			SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
			sqlbv29.sql(sql2);
			sqlbv29.put("rptno", mRptNo);
			map.put(sqlbv29, "DELETE");
			map.put(mLLSubReportSet, "INSERT");
		}
		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLSubReport");
		
		
		/*
		 * *************************************************************************
		 * 
		 * LLReportRela 报案分案关联信息
		 * 
		 * *************************************************************************
		 */	


		mLLReportRelaSchema.setRptNo(mRgtNo);
		mLLReportRelaSchema.setSubRptNo(mRgtNo);
		
		String sql3="delete from LLReportRela where rptno='"+"?rptno?"+"'";
		logger.debug("删除LLReportRela表中赔案"+mRptNo+"记录的sql:"+sql3);
		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
		sqlbv30.sql(sql3);
		sqlbv30.put("rptno", mRptNo);
		map.put(sqlbv30, "DELETE");
		map.put(mLLReportRelaSchema, "INSERT");
		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLReportRela");
		
		
		/*
		 * *************************************************************************
		 * 
		 * LLCaseRela 分案事件关联
		 * 
		 * *************************************************************************
		 */	

		mLLCaseRelaSchema.setCaseNo(mRgtNo);
		mLLCaseRelaSchema.setSubRptNo(mRgtNo);
		
		String sql4="delete from LLCaseRela where CaseNo='"+"?rptno?"+"'";
		logger.debug("删除LLCaseRela表中赔案"+mRptNo+"记录的sql:"+sql4);
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
		sqlbv31.sql(sql4);
		sqlbv31.put("rptno", mRptNo);
		map.put(sqlbv31, "DELETE");
		map.put(mLLCaseRelaSchema, "INSERT");
		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLCaseRela");
		
		
		
		/*
		 * *************************************************************************
		 * 
		 * LLReportReason 报案理赔类型
		 * 
		 * *************************************************************************
		 */	
		
	
		for(int i=1;i<=mLLReportReasonSet.size();i++)
		{
			mLLReportReasonSet.get(i).setRpNo(mRgtNo);
		}
		
		String sql5="";
		if(mLLReportReasonSet.size()>0){
			
			sql5="delete from LLReportReason where RpNo='"+"?rptno?"+"'";
			logger.debug("删除LLReportReason表中赔案"+mRptNo+"记录的sql:"+sql5);
			SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
			sqlbv32.sql(sql5);
			sqlbv32.put("rptno", mRptNo);
			map.put(sqlbv32, "DELETE");
			map.put(mLLReportReasonSet, "INSERT");
		}
		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLReportReason");
		
		
		/*
		 * *************************************************************************
		 * 
		 * LLReportAffix 报案附件信息
		 * 
		 * *************************************************************************
		 */	

		String sql6="";
		if (mLLReportAffixSet != null){
			
			for(int i=1;i<=mLLReportAffixSet.size();i++)
			{
				mLLReportAffixSet.get(i).setRptNo(mRgtNo);
			}	
			
			if(mLLReportAffixSet.size()>0){
				
				sql6="delete from LLReportAffix where RptNo='"+"?rptno?"+"'";
				logger.debug("删除LLReportAffix表中赔案"+mRptNo+"记录的sql:"+sql6);
				SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
				sqlbv33.sql(sql6);
				sqlbv33.put("rptno", mRptNo);
				map.put(sqlbv33, "DELETE");
				map.put(mLLReportAffixSet, "INSERT");
			}
		}
		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLReportAffix");
		
		
		
		/*
		 * *************************************************************************
		 * 
		 * LLInqApply 调查申请
		 * 
		 * *************************************************************************
		 */	

		LLInqApplyDB mLLInqApplyDB = new LLInqApplyDB();
		mLLInqApplyDB.setClmNo(mRptNo);
		mLLInqApplySet = mLLInqApplyDB.query();
		
		String sql7="";
		if (mLLInqApplySet != null){
			
			for (int i = 1; i <= mLLInqApplySet.size(); i++) {
				mLLInqApplySet.get(i).setClmNo(mRgtNo);
			}

			
			if(mLLInqApplySet.size()>0){
				
				sql7="delete from LLInqApply where clmno='"+"?rptno?"+"'";
				logger.debug("删除LLInqApply表中赔案"+mRptNo+"记录的sql:"+sql7);
				SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
				sqlbv34.sql(sql7);
				sqlbv34.put("rptno", mRptNo);
				map.put(sqlbv34, "DELETE");
				map.put(mLLInqApplySet, "INSERT");
			}
		}
		
		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLInqApply");
		
		
		
		/*
		 * *************************************************************************
		 * 
		 * LLInqCourse 调查过程
		 * 
		 * *************************************************************************
		 */	

		LLInqCourseDB mLLInqCourseDB = new LLInqCourseDB();
		mLLInqCourseDB.setClmNo(mRptNo);
		mLLInqCourseSet = mLLInqCourseDB.query();
		
		String sql8="";		
		if (mLLInqCourseSet != null){
			
			for (int i = 1; i <= mLLInqCourseSet.size(); i++) {
				mLLInqCourseSet.get(i).setClmNo(mRgtNo);
			}
			
			
			if(mLLInqCourseSet.size()>0){
		
				sql8="delete from LLInqCourse where clmno='"+"?rptno?"+"'";
				logger.debug("删除LLInqCourse表中赔案"+mRptNo+"记录的sql:"+sql8);
				SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
				sqlbv35.sql(sql8);
				sqlbv35.put("rptno", mRptNo);
				map.put(sqlbv35, "DELETE");
				map.put(mLLInqCourseSet, "INSERT");
			}
		}

		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLInqCourse");
		
		
		/*
		 * *************************************************************************
		 * 
		 * LLInqConclusion 调查结论信息
		 * 
		 * *************************************************************************
		 */	

		LLInqConclusionDB mLLInqConclusionDB = new LLInqConclusionDB();
		mLLInqConclusionDB.setClmNo(mRptNo);
		mLLInqConclusionSet = mLLInqConclusionDB.query();
		
		String sql9="";	
		if (mLLInqConclusionSet != null){
			
			for (int i = 1; i <= mLLInqConclusionSet.size(); i++) {
				mLLInqConclusionSet.get(i).setClmNo(mRgtNo);
			}
			
			
			if(mLLInqConclusionSet.size()>0){
		
				sql9="delete from LLInqConclusion where clmno='"+"?rptno?"+"'";
				logger.debug("删除LLInqConclusion表中赔案"+mRptNo+"记录的sql:"+sql9);
				SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
				sqlbv36.sql(sql9);
				sqlbv36.put("rptno", mRptNo);
				map.put(sqlbv36, "DELETE");
				map.put(mLLInqConclusionSet, "INSERT");
			}
		}
		
		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLInqConclusion");
		
		
		/*
		 * *************************************************************************
		 * 
		 * LLInqCertificate 调查单证信息
		 * 
		 * *************************************************************************
		 */	

		LLInqCertificateDB mLLInqCertificateDB = new LLInqCertificateDB();
		mLLInqCertificateDB.setClmNo(mRptNo);
		mLLInqCertificateSet = mLLInqCertificateDB.query();
		
		String sql10="";	
		if (mLLInqConclusionSet != null){
			
			for (int i = 1; i <= mLLInqCertificateSet.size(); i++) {
				mLLInqCertificateSet.get(i).setClmNo(mRgtNo);
			}
			
			if(mLLInqCertificateSet.size()>0){
		
				sql10="delete from LLInqCertificate where clmno='"+"?rptno?"+"'";
				logger.debug("删除LLInqCertificate表中赔案"+mRptNo+"记录的sql:"+sql10);
				SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
				sqlbv37.sql(sql10);
				sqlbv37.put("rptno", mRptNo);
				map.put(sqlbv37, "DELETE");
				map.put(mLLInqCertificateSet, "INSERT");
			}
		}

		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLInqCertificate");
		
		
		
		/*
		 * *************************************************************************
		 * 
		 * LLInqFee 调查费用信息
		 * 
		 * *************************************************************************
		 */	
		

		LLInqFeeDB mLLInqFeeDB = new LLInqFeeDB();
		mLLInqFeeDB.setClmNo(mRptNo);
		mLLInqFeeSet = mLLInqFeeDB.query();
		
		String sql11="";	
		if (mLLInqFeeSet != null){
			
			for (int i = 1; i <= mLLInqFeeSet.size(); i++) {
				mLLInqFeeSet.get(i).setClmNo(mLLReportSchema.getRptNo());
			}
			
			
			if(mLLInqFeeSet.size()>0){
		
				sql11="delete from LLInqFee where clmno='"+"?rptno?"+"'";
				logger.debug("删除LLInqFee表中赔案"+mRptNo+"记录的sql:"+sql11);
				SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
				sqlbv38.sql(sql11);
				sqlbv38.put("rptno", mRptNo);
				map.put(sqlbv38, "DELETE");
				map.put(mLLInqFeeSet, "INSERT");
			}
		}	
			
		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLSubmitApply");
		
		
		
		/*
		 * *************************************************************************
		 * 
		 * LLSubmitApply 呈报信息
		 * 
		 * *************************************************************************
		 */	
		

		LLSubmitApplyDB mLLSubmitApplyDB = new LLSubmitApplyDB();
		mLLSubmitApplyDB.setClmNo(mRptNo);
		mLLSubmitApplySet = mLLSubmitApplyDB.query();
		
		String sql12="";	
		if (mLLSubmitApplySet != null){
			
			for (int i = 1; i <= mLLSubmitApplySet.size(); i++) {
				mLLSubmitApplySet.get(i).setClmNo(mLLReportSchema.getRptNo());
			}
			
			
			if(mLLSubmitApplySet.size()>0){
		
				sql12="delete from LLSubmitApply where clmno='"+"?rptno?"+"'";
				logger.debug("删除LLSubmitApply表中赔案"+mRptNo+"记录的sql:"+sql12);
				SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
				sqlbv39.sql(sql12);
				sqlbv39.put("rptno", mRptNo);
				map.put(sqlbv39, "DELETE");
				map.put(mLLSubmitApplySet, "INSERT");
			}
		}

		
		logger.debug("---LLClaimUpdateRptNoBL end updateLLSubmitApply");
		
		
		
		
		/*
		 * *************************************************************************
		 * 
		 * LCContHangUpState 保单挂起信息
		 * 
		 * *************************************************************************
		 */	
		
		
		LCContHangUpStateDB mLCContHangUpStateDB = new LCContHangUpStateDB();
		mLCContHangUpStateDB.setHangUpNo(mRptNo);
		mLCContHangUpStateSet = mLCContHangUpStateDB.query();
		
		String sql13="";	
		if (mLCContHangUpStateSet != null){
			
			for (int i = 1; i <= mLCContHangUpStateSet.size(); i++) {
				mLCContHangUpStateSet.get(i).setHangUpNo(mLLReportSchema.getRptNo());
			}
			
			
			if(mLCContHangUpStateSet.size()>0){
		
				sql13="delete from LCContHangUpState where HangUpNo='"+"?rptno?"+"'";
				logger.debug("删除LCContHangUpState表中赔案"+mRptNo+"记录的sql:"+sql13);
				SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
				sqlbv40.sql(sql13);
				sqlbv40.put("rptno", mRptNo);
				map.put(sqlbv40, "DELETE");
				map.put(mLCContHangUpStateSet, "INSERT");
			}
		}

		
		logger.debug("---LLClaimUpdateRptNoBL end updateLCContHangUpState");
		
		
		//释放强引用
		sql1=null;
		sql2=null;
		sql3=null;
		sql4=null;
		sql5=null;
		sql6=null;
		sql7=null;
		sql8=null;
		sql9=null;
		sql10=null;
		sql11=null;
		sql12=null;
		sql13=null;
		
		logger.debug("---LLClaimUpdateRptNoBL end updateRptNo()...");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try 
		{
			
			map.put(mLLRegisterSchema, "INSERT");
			map.put(mLLCaseSet, "INSERT");
			map.put(mLLAppClaimReasonSet, "INSERT");
			map.put(mLLAffixSet, "INSERT");
			
			//事件表不存在换号问题,所以只有不报案直接立案时再进行插入
			if(mIsAccExist.equals("false")){				
				map.put(mLLAccidentSchema, "DELETE&INSERT");
				map.put(mLLAccidentSubSchema, "DELETE&INSERT");
			}

			
			//立案信息
			mResult.clear();
			mResult.add(mLLRegisterSchema);//立案信息
			mResult.add(mLLCaseSet);//立案分案信息
			mResult.add(mLLAppClaimReasonSet);//报案理赔类型
			mResult.add(mLLAffixSet);//立案附件表
			
			//报案信息
			mResult.add(mLLReportSchema);//报案表
			mResult.add(mLLSubReportSet);//报案分案表
			mResult.add(mLLReportRelaSchema);//报案事件关联
			mResult.add(mLLCaseRelaSchema);//分案事件关联
			mResult.add(mLLReportReasonSet);//报案理赔类型
			mResult.add(mLLReportAffixSet);//报案附件表
			
			//事件信息
			mResult.add(mLLAccidentSchema);//事件信息
			mResult.add(mLLAccidentSubSchema);//分事件信息
			mResult.add(mTransferData);
			mResult.add(mG);
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
