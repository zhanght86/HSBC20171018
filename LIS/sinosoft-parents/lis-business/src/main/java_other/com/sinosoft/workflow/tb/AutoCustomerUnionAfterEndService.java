package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

public class AutoCustomerUnionAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(AutoCustomerUnionAfterEndService.class);

	public CErrors mErrors = new CErrors();
	
	private TransferData mTransferData = new TransferData();

	private String mAppntNo;
	
	private String mContNo;

	private String mInsuredNo;
	
	private String mPolNo;

	private String mCustomerNo_NEW;
	private String mCustomerNo_OLD;
	
	private VData mResult = new VData();

	private GlobalInput mGlobalInput = new GlobalInput();
	
	private String customerUnion="2";
	// 判断问题件修改完毕是否该扭转到下一结点标记
	private String mOtherNoticeFlag = "";
	private String mDealType;
	private LWMissionSet mLWMissionSet_Update = new LWMissionSet();// 如果结点未扭转,需要更新lwmission表的状态为等待中
	private LWMissionSet mLWMissionSet_Delete = new LWMissionSet();// 如果结点需要扭转,那么,删除除了本结点之外的所有其他结点
	private LBMissionSet mLBMissionSet_Delete = new LBMissionSet();// 如果结点需要扭转,那么,备份除了本结点之外的所有其他结点

	private String mRiskCode;
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
	private String mCustomerType;
	/**被保人信息*/
	private LDPersonSchema iLDPersonSchema = new LDPersonSchema();
	/**投保人信息*/
	private LDPersonSchema aLDPersonSchema = new LDPersonSchema();
	
	private boolean isAutoUnion=false;//判断是否需要人工合并	

	//tongmeng 2009-02-25 add
	//判断是否需要继续执行人工合并服务类
	private boolean judgeUnion = false;
	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
//		mResult.clear();
//		mResult.add(mTransferData);
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return null;
	}

	public boolean submitData(VData cInputData, String cOperate) {

		if (!getInputData(cInputData, cOperate))
			return false;
		
//		判断是否是外包导入失败，如果是，不进行客户号合并校验，直接返回  DealType——00导入成功
		//tongmeng 2009-02-20 modify
		//取消这段判断
	
//		if(!(mDealType==null||"".equals(mDealType))){
//			if(!"00".equals(mDealType)){
//				return true;
//			}
//			//return true;
//		}
//		if (!checkData())
//			return false;
		if(judgeUnion)
		{
			return true;
		}
		logger.debug("Start  dealData...");
		// 进行业务处理
		if (!dealData())
			return false;
	
		logger.debug("dealData successful!");
		//tongmeng 2009-02-21 add
		//如果当前是问题件修改岗,需要处理工作流扭转问题.
		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;
	
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		mActivityID=cOperate;
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if(mTransferData==null){
			CError.buildErr(this, "外部传输数据出错！");
			return false;
		}
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		
		if(mContNo==null||"".equals(mContNo)){
			CError.buildErr(this, "合同号码为空！");
			return false;
		}
		if(mMissionID==null||"".equals(mMissionID)){
			CError.buildErr(this, "传入MissionID失败！");
			return false;
		}
		if(mSubMissionID==null||"".equals(mSubMissionID)){
			CError.buildErr(this, "传入mSubMissionID失败！");
			return false;
		}
		if(mActivityID==null||"".equals(mActivityID)){
			CError.buildErr(this, "传入mActivityID失败！");
			return false;
		}
		
		//判断是否是外包导入失败，如果是，不进行客户号合并校验，直接返回  DealType——00导入成功
		//tongmeng 2009-02-20 modify
		//取消这段判断
		/*
		mDealType = (String) mTransferData.getValueByName("DealType");
		if(!(mDealType==null||"".equals(mDealType))){
			if(!"00".equals(mDealType)){
				return true;
			}
		}*/
		
		
		//查询投被保人客户号
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if(!tLCContDB.getInfo()){
			//CError.buildErr(this, "查询合同信息失败！");
			this.judgeUnion = true;
			return true;
			//return false;
		}
			
		mAppntNo = tLCContDB.getAppntNo();
		mInsuredNo = tLCContDB.getInsuredNo();
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(mContNo);
		tLCPolSet = tLCPolDB.query();
		if(tLCPolSet.size()>0){
			mPolNo = tLCPolSet.get(1).getPolNo();
		}else{
//			CError.buildErr(this, "查询险种信息失败！");
//			return false;
		}
		return true;
	}

	private boolean checkData() {
		if(mAppntNo==null||"".equals(mAppntNo)){
			CError.buildErr(this, "投保人客户号不能为空！");
			return false;
		}
		if(mInsuredNo==null||"".equals(mInsuredNo)){
			CError.buildErr(this, "被保人客户号不能为空！");
			return false;
		}
		if(mPolNo==null||"".equals(mPolNo)){
//			CError.buildErr(this, "保单号不能为空！");
//			return false;
		}
		return true;
	}

	private boolean dealData() {
		
		//投保人客户号自动合并
		try{
		/*
		LDPersonDB aLDPersonDB = new LDPersonDB();
		LDPersonSet aLDPersonSet = new LDPersonSet();
		LDPersonSet oldLDPersonSet = new LDPersonSet();
		aLDPersonDB.setCustomerNo(mAppntNo);
		aLDPersonSet = aLDPersonDB.query();
		aLDPersonSchema = aLDPersonSet.get(1);
		String aSql = "select * from ldperson where name='"
				+ aLDPersonSchema.getName() + "' and sex='"
				+ aLDPersonSchema.getSex() + "' and birthday='"
				+ aLDPersonSchema.getBirthday() + "' and customerno<> '"
				+ mAppntNo+"'";
		oldLDPersonSet = aLDPersonDB.executeQuery(aSql);
		String customer_sql = "";
		*/
			
		//tongmeng 2009-02-13 modify
		//修改客户号手工合并规则
		
		//获得该合同下的所有客户信息
		String tSQL_CustomerNo = " select appntno,'A' from lcappnt where contno='"+"?contno1?"+"' "
                               + " union " 
                               + " select insuredno,'I' from lcinsured where contno='"+"?contno2?"+"' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL_CustomerNo);
		sqlbv1.put("contno1", this.mContNo);
		sqlbv1.put("contno2", this.mContNo);
	
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		for(int i=1;i<=tSSRS.getMaxRow();i++)
		{
			String tCustomerNoType = tSSRS.GetText(i,2);
			String tCustomerNo = tSSRS.GetText(i,1);
			//判断是否符合客户号手工合并规则
			if(this.judgeCustomerNo(tCustomerNo, tCustomerNoType))
			{
				this.customerUnion = "1";
				//只要有一个符合就跳出
				break;
			}
		}
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "客户关联程序内部出错！");
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}


	public String get15IDNo(String IDNo) {
		String str = "";
		str += IDNo.substring(0, 6);
		str += IDNo.substring(8, 17);
		return str;
	}

	public static String get18IDNo(String IDNo, String Birthday) {
		if (IDNo.length() == 18) {
			if (IDNo.endsWith("x"))
				IDNo = IDNo.substring(0, 17) + "X";
			return IDNo;
		}
		String str = "";
		str += IDNo.substring(0, 6);
		if (Birthday.length() == 10) {
			str += Birthday.substring(0, 2);
		} else
			str += "19";
		str += IDNo.substring(6, 15);
		int n = 0;
		int[] weight = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
				8, 4, 2 };
		for (int i = 0; i < 17; i++) {
			n += Integer.parseInt(str.substring(i, i + 1)) * weight[i];
		}
		n %= 11;
		if (n == 0)
			str += "1";
		else if (n == 1)
			str += "0";
		else if (n == 2)
			str += "X";
		else if (n == 3)
			str += "9";
		else if (n == 4)
			str += "8";
		else if (n == 5)
			str += "7";
		else if (n == 6)
			str += "6";
		else if (n == 7)
			str += "5";
		else if (n == 8)
			str += "4";
		else if (n == 9)
			str += "3";
		else if (n == 10)
			str += "2";
		return str;
	}
	
	/** 为下一结点准备数据 */
	private boolean prepareTransferData() {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo);
		tLCPolDB.getInfo();
		tLCPolSchema = tLCPolDB.getSchema();
		
		mTransferData.setNameAndValue("AppntName",aLDPersonSchema.getName());
		mTransferData.setNameAndValue("AppntNo",aLDPersonSchema.getCustomerNo());
		if(mAppntNo!=null&&!mAppntNo.equals(mInsuredNo)){
			mTransferData.setNameAndValue("InsuredNo",iLDPersonSchema.getCustomerNo());
			mTransferData.setNameAndValue("InsuredName",iLDPersonSchema.getName());
		}else{
			mTransferData.setNameAndValue("InsuredNo",aLDPersonSchema.getCustomerNo());
			mTransferData.setNameAndValue("InsuredName",aLDPersonSchema.getName());
		}
		mTransferData.setNameAndValue("AgentCode", tLCPolSchema.getAgentCode());
		mTransferData.setNameAndValue("RiskCode", tLCPolSchema.getRiskCode());
		//tongmeng 2009-02-21 add
		//是否创建问题件修改岗工作流.
	
		this.mTransferData.setNameAndValue("OtherNoticeFlag", mOtherNoticeFlag);
		//tongmeng 2009-02-20 modify
		//首先判断一下是否有问题件,如果有问题件的话,需要先处理问题件,没有才可以进入客户号手工合并流程
		//QuesOrgFlag 机构问题件
		//ApproveModifyFlag 发送问题件修改岗
		//SendOperFlag 打印业务员通知书
		//
		String tQuesOrgFlag = (String)mTransferData.getValueByName("QuesOrgFlag");
		String tApproveModifyFlag = (String)mTransferData.getValueByName("ApproveModifyFlag");
		String tSendOperFlag = (String)mTransferData.getValueByName("SendOperFlag");
		String tState = (String)mTransferData.getValueByName("State");
		logger.debug("tState:"+tState);
		if(tState==null)
		{
			tState = "1";
		}
		if((tQuesOrgFlag!=null&&tQuesOrgFlag.equals("1"))
			||(tApproveModifyFlag!=null&&tApproveModifyFlag.equals("1"))
			||(tSendOperFlag!=null&&tSendOperFlag.equals("1"))
				)
		{
			//有问题件的不发放
			customerUnion = "0";
		}
		
		if(tState.equals("1"))
		{
			if(customerUnion.equals("1"))
			{
				//如果需要手工合并,不走自核流程!
				mTransferData.removeByName("State");
				mTransferData.setNameAndValue("State","0");
				
			}
			else
			{
				//tongmeng 2009-04-14 add
				//如果是问题件已回复,记录回复日期和时间
				mTransferData.setNameAndValue("ReplyDate", PubFun.getCurrentDate());
				mTransferData.setNameAndValue("ReplyTime", PubFun.getCurrentTime());
			}
		}
		
		mTransferData.setNameAndValue("customerUnion", customerUnion);
		return true;
	}
	
	/**
	 * 校验是否符合客户号手工合并规则
	 * @param tCustomerNo
	 * @param tCustomerNoType
	 * @return
	 */
	private boolean judgeCustomerNo(String tCustomerNo,String tCustomerNoType)
	{
		String tValue = "";
		ExeSQL tExeSQL = new ExeSQL();
		//获得客户信息
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(tCustomerNo);
		if(!tLDPersonDB.getInfo())
		{
			return false;
		}
		else
		{
			tLDPersonSchema.setSchema(tLDPersonDB.getSchema());
		}
		
		LCContDB tLCContDB = new LCContDB();
		LCContSchema ttLCContSchema = new LCContSchema();
		tLCContDB.setContNo(this.mContNo);
		if(!tLCContDB.getInfo()){
			return false;
		}
		else
		{
			ttLCContSchema.setSchema(tLCContDB.getSchema());
		}
		
//		若ldperson的makedate与合同生成相比较，若不一致认为是旧的客户，否则是新生成的客户号，不必手工合并		
		int t = PubFun.calInterval3(tLDPersonSchema.getMakeDate(),ttLCContSchema.getMakeDate(), "D");
		logger.debug("t="+t);
		if(t>0)
		{			
			return false;
		}	
		/*"Oracle：select decode(count(prtno),0,'0','1') from laagentascription 
		改造为：select (case count(prtno) when 0 then '0' else '1' end)from laagentascription "
        */
		//凡客户“姓名、性别、出生日期”三项与系统中原有客户资料完全一致
//		String tSQL_1 = " select (case count(*) when 0 then '0' else '1' end) from ldperson a,ldperson b where " 
//                      + " a.name=b.name and a .sex=b.sex and a.birthday=b.birthday "
//                      + " and a.customerno<>b.customerno "  
//                      + " and b.customerno = '"+"?customerno?"+"'"
//                      + " and  exists (select 1 from lcpol where conttype='1' and appntno=a.customerno"
//            		  + " union select 1  from lcpol where conttype='1' and insuredno=a.customerno)";
//		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
//		sqlbv2.sql(tSQL_1);
//		sqlbv2.put("customerno", tCustomerNo);
//		tValue = tExeSQL.getOneValue(sqlbv2);
//		if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>=1)
//		{
//			return true;
//		}
//		//凡客户 “证件类型”为“0-身份证、1-护照”且“证件号码”与系统中原有客户资料完全一致
//		/*
//		 （“证件号码”一致性的校验规则：“证件号码”15位或18位与原有客户资料不匹配时，
//		 系统自动将18位的号码转为15位后，进行校验；因“证件号码”中含有英文字母时，校验时不区分大小写。）
//		 */
//		String tSQL_2 = "";
//		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
//		if("0".equals(tLDPersonSchema.getIDType())){
//			if (tLDPersonSchema.getIDNo().length() == 18) {
//				tSQL_2 = "select (case count(*) when 0 then '0' else '1' end) from ldperson where ( "
//					+ " lower(idno)=lower('"+ "?lower?"+ "') "
//					+ " or lower(idno)=lower('"+ "?lower1?" + "') ) "
//					+ " and customerno <> '"+ "?customerno?"+"' "
//					+ " and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//          		    + " union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//					//+ " and idtype in ('0','1') "
//					;
//				sqlbv5.sql(tSQL_2);
//				sqlbv5.put("lower", get15IDNo(tLDPersonSchema.getIDNo()));
//				sqlbv5.put("lower1", tLDPersonSchema.getIDNo());
//				sqlbv5.put("customerno", tCustomerNo);
//				logger.debug("***tSQL_2 Step 2 : " + tSQL_2);
//			} else if (tLDPersonSchema.getIDNo().length() == 15) {
//				tSQL_2 = "select (case count(*) when 0 then '0' else '1' end) from ldperson where ( "
//					+ " lower(idno)=lower('"+ "?lower2?" + "') "
//					+ " or lower(idno)=lower('"+ "?lower3?" + "') ) "
//					+ " and customerno <> '"+ "?customerno1?"+"' "
//					+ " and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//          		    + " union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//					//+ " and idtype in ('0','1') "
//					;
//				sqlbv5.sql(tSQL_2);
//				sqlbv5.put("lower2", get18IDNo(tLDPersonSchema.getIDNo(),tLDPersonSchema.getBirthday()));
//				sqlbv5.put("lower3", tLDPersonSchema.getIDNo());
//				sqlbv5.put("customerno1", tCustomerNo);
//				logger.debug("***tSQL_2 Step 2 : " + tSQL_2);				
//			}
//		}else if("1".equals(tLDPersonSchema.getIDType())){
//			tSQL_2 = " select (case count(*) when 0 then '0' else '1' end) from ldperson where "
//				          + " lower(idno)=lower('"+ "?lower4?" + "') "
//				          + " and customerno <> '"+ "?customerno2?" +"'"
//				          + " and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//		          		  + " union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//		          		  ;
//			sqlbv5.sql(tSQL_2);
//			sqlbv5.put("lower4", tLDPersonSchema.getIDNo());
//			sqlbv5.put("customerno2", tCustomerNo);
//		}
//		
//		if (tSQL_2 != null && !tSQL_2.equals("")) {
//			tValue = tExeSQL.getOneValue(sqlbv5);
//			if (tValue != null && !tValue.equals("")
//					&& Integer.parseInt(tValue) >= 1) {
//				return true;
//			}
//		}
//		
//		//证件类型为“身份证”证件号码一致，且联系电话与原有客户资料相同时
//		//tongmeng 2009-03-04 add
//		//增加护照、驾照、户口本 1,3,4
//		if("0".equals(tLDPersonSchema.getIDType())){
//			//获得当前客户的联系号码
//			String tPhone1 = "";
//			String tPhone2 = "";
//			String tAddressNo = "";
//			String tSQL_Phone = "select mobile,phone,addressno from lcaddress where customerno='"+"?c1?"+"' "
//			                  + " and addressno = (select addressno from lcappnt where contno='"+"?c2?"+"' "
//			                  + " and appntno='"+"?c3?"+"' and 'A'='"+"?c3?"+"') "
//                              + " union "
//                              + " select mobile,phone,addressno from lcaddress where customerno='"+"?c4?"+"' "
//                              + " and addressno =(select addressno from lcinsured where contno='"+"?c5?"+"' "
//                              + " and insuredno='"+"?c6?"+"' and 'I'='"+"?c7?"+"')";
//			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
//			sqlbv6.sql(tSQL_Phone);
//			sqlbv6.put("c1", tCustomerNo);
//			sqlbv6.put("c2", this.mContNo);
//			sqlbv6.put("c3", tCustomerNo);
//			sqlbv6.put("c4", tCustomerNoType);
//			sqlbv6.put("c5", tCustomerNo);
//			sqlbv6.put("c6", this.mContNo);
//			sqlbv6.put("c7", tCustomerNo);
//			sqlbv6.put("c8", tCustomerNoType);
//			SSRS tempSSRS = new SSRS();
//			ExeSQL tempExeSQL = new ExeSQL();
//			tempSSRS = tempExeSQL.execSQL(sqlbv6);
//			if(tempSSRS.getMaxRow()>0)
//			{
//				tPhone1 = tempSSRS.GetText(1,1);
//				tPhone2 = tempSSRS.GetText(1,2);
//				if(tPhone1==null)
//				{
//					tPhone1 = " ";
//				}
//				if(tPhone2==null)
//				{
//					tPhone2 = " ";
//				}
//				if (!tPhone1.equals("") || !tPhone2.equals("")) {
//
//					tAddressNo = tempSSRS.GetText(1, 3);
//					String tSQL_3 = "";
//					SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
//					/*"①、Oracle：select regexp_replace(a, b, c) from dual;
//				       	改造为：select replace(a, b, c) from dual;
//					②、在java中使用if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){……}else 
//					            if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){……}方式进行改造。"
//*/
//					if (tLDPersonSchema.getIDNo().length() == 18) {
//						String tIDNo15 = get15IDNo(tLDPersonSchema.getIDNo());
//						tSQL_3 = "select * from ldperson a where ( "
//								+ " lower(idno)=lower('"
//								+ "?c9?"
//								+ "') "
//								+ " or lower(idno)=lower('"
//								+ "?c10?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?c11?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=a.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=a.customerno)"
//								+ " and '"
//								+ "?c12?"
//								+ "' in (select  replace(phone, '无', '') from lcaddress where customerno=a.customerno) "
//								+ " and '"+"?c13?"+"'<>' ' "
//								+ " union "
//								+ " select * from ldperson a where ( "
//								+ " lower(idno)=lower('"
//								+ "?c14?"
//								+ "') "
//								+ " or lower(idno)=lower('"
//								+ "?c15?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?c16?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=a.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=a.customerno)"
//								+ " and '"
//								+ "?c17?"
//								+ "' in (select replace(mobile, '无', '') from lcaddress where customerno=a.customerno) "
//								+ " and '"+"?c18?"+"'<>' ' "
//								+ " union "
//								+ " select * from ldperson a where ( "
//								+ " lower(idno)=lower('"
//								+ "?c19?"
//								+ "') "
//								+ " or lower(idno)=lower('"
//								+ "?c20?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?c21?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=a.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=a.customerno)"
//								+ " and '"
//								+ "?c22?"
//								+ "' in (select  replace(phone, '无', '') from lcaddress where customerno=a.customerno) "
//								+ " and '"+"?c23?"+"'<>' ' "
//								+ " union "
//								+ " select * from ldperson a where ( "
//								+ " lower(idno)=lower('"
//								+ "?c24?"
//								+ "') "
//								+ " or lower(idno)=lower('"
//								+ "?c25?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?c26?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=a.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=a.customerno)"
//								+ " and '"
//								+ "?c27?"
//								+ "' in (select replace(mobile, '无', '') from lcaddress where customerno=a.customerno) "
//								+ " and '"+"?c28?"+"'<>' ' "
//
//						// + " and idtype in ('0','1') "
//						;
//						
//						sqlbv7.sql(tSQL_3);
//						sqlbv7.put("c9", tIDNo15);
//						sqlbv7.put("c10", tLDPersonSchema.getIDNo());
//						sqlbv7.put("c11", tCustomerNo);
//						sqlbv7.put("c12", tPhone1);
//						sqlbv7.put("c13", tPhone1);
//						sqlbv7.put("c14", tIDNo15);
//						sqlbv7.put("c15", tLDPersonSchema.getIDNo());
//						sqlbv7.put("c16", tCustomerNo);
//						sqlbv7.put("c17", tPhone1);
//						sqlbv7.put("c18", tPhone1);
//						sqlbv7.put("c19", tIDNo15);
//						sqlbv7.put("c20", tLDPersonSchema.getIDNo());
//						sqlbv7.put("c21", tCustomerNo);
//						sqlbv7.put("c22", tPhone2);
//						sqlbv7.put("c23", tPhone2);
//						sqlbv7.put("c24", tIDNo15);
//						sqlbv7.put("c25", tLDPersonSchema.getIDNo());
//						sqlbv7.put("c26", tCustomerNo);
//						sqlbv7.put("c27", tPhone2);
//						sqlbv7.put("c28", tPhone2);
//						logger.debug("***tSQL_2 Step 3 : " + tSQL_3);
//					} else if (tLDPersonSchema.getIDNo().length() == 15) {
//						String tIDNo18 = get18IDNo(tLDPersonSchema.getIDNo(),
//								tLDPersonSchema.getBirthday());
//						tSQL_3 = "(select * from ldperson where ( "
//								+ " lower(idno)=lower('"
//								+ "?a1?"
//								+ "') "
//								+ " or lower(idno)=lower('"
//								+ "?a2?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?a3?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//								+ " and '"
//								+ "?a4?"
//								+ "' in (select replace(phone, '无', '') from lcaddress where customerno=ldperson.customerno) "
//								+ " and '"+"?a5?"+"'<>' ' )"
//								+ " union "
//								+ " (select * from ldperson where ( "
//								+ " lower(idno)=lower('"
//								+ "?a6?"
//								+ "') "
//								+ " or lower(idno)=lower('"
//								+ "?a7?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?a8?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//								+ " and '"
//								+ "?a9?"
//								+ "' in (select replace(mobile, '无', '') from lcaddress where customerno=ldperson.customerno) "
//								+ " and '"+"?a10?"+"'<>' ' )"
//								+ " union "
//								+ " (select * from ldperson where ( "
//								+ " lower(idno)=lower('"
//								+ "?a11?"
//								+ "') "
//								+ " or lower(idno)=lower('"
//								+ "?a12?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?a13?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//								+ " and '"
//								+ "?a14?"
//								+ "' in (select replace(phone, '无', '') from lcaddress where customerno=ldperson.customerno) "
//								+ " and '"+"?a15?"+"'<>' ' )"
//								+ " union "
//								+ " (select * from ldperson where ( "
//								+ " lower(idno)=lower('"
//								+ "?a16?"
//								+ "') "
//								+ " or lower(idno)=lower('"
//								+ "?a17?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?a18?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//								+ " and '"
//								+ "?a19?"
//								+ "' in (select replace(mobile, '无', '') from lcaddress where customerno=ldperson.customerno) "
//								+ " and '"+"?a20?"+"'<>' ' )"
//
//						//+ " and idtype in ('0','1') "
//								
//						;
//						
//						sqlbv7.sql(tSQL_3);
//						sqlbv7.put("a1", tIDNo18);
//						sqlbv7.put("a2", tLDPersonSchema.getIDNo());
//						sqlbv7.put("a3", tCustomerNo);
//						sqlbv7.put("a4", tPhone1);
//						sqlbv7.put("a5", tPhone1);
//						sqlbv7.put("a6", tIDNo18);
//						sqlbv7.put("a7", tLDPersonSchema.getIDNo());
//						sqlbv7.put("a8", tCustomerNo);
//						sqlbv7.put("a9", tPhone1);
//						sqlbv7.put("a10", tPhone1);
//						sqlbv7.put("a11", tIDNo18);
//						sqlbv7.put("a12", tLDPersonSchema.getIDNo());
//						sqlbv7.put("a13", tCustomerNo);
//						sqlbv7.put("a14", tPhone2);
//						sqlbv7.put("a15", tPhone2);
//						sqlbv7.put("a16", tIDNo18);
//						sqlbv7.put("a17", tLDPersonSchema.getIDNo());
//						sqlbv7.put("a18", tCustomerNo);
//						sqlbv7.put("a19", tPhone2);
//						sqlbv7.put("a20", tPhone2);
//						logger.debug("***tSQL_2 Step 3 : " + tSQL_3);
//					}
//					LDPersonSet tempLDPersonSet = new LDPersonSet();
//					LDPersonDB tempLDPersonDB = new LDPersonDB();
//					tempLDPersonSet = tempLDPersonDB.executeQuery(sqlbv7);
//					if (tempLDPersonSet.size() > 0) {
//						return true;
//					}
//				}
//			}
//			
//		}
//		
//		//////////////////////////////////////////////////////
//		if("1".equals(tLDPersonSchema.getIDType())
//				||"3".equals(tLDPersonSchema.getIDType())
//				||"4".equals(tLDPersonSchema.getIDType())
//				){
//			//获得当前客户的联系号码
//			String tPhone1 = "";
//			String tPhone2 = "";
//			String tAddressNo = "";
//			String tSQL_Phone = "select mobile,phone,addressno from lcaddress where customerno='"+"?customerno?"+"' "
//			                  + " and addressno = (select addressno from lcappnt where contno='"+"?contno?"+"' "
//			                  + " and appntno='"+"?appntno?"+"' and 'A'='"+"?and?"+"') "
//                              + " union "
//                              + " select mobile,phone,addressno from lcaddress where customerno='"+"?customerno?"+"' "
//                              + " and addressno =(select addressno from lcinsured where contno='"+"?contno1?"+"' "
//                              + " and insuredno='"+"?insuredno?"+"' and 'I'='"+"?and1?"+"')";
//			SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
//			sqlbv9.sql(tSQL_Phone);
//			sqlbv9.put("customerno", tCustomerNo);
//			sqlbv9.put("contno", this.mContNo);
//			sqlbv9.put("appntno", tCustomerNo);
//			sqlbv9.put("and", tCustomerNoType);
//			sqlbv9.put("customerno", tCustomerNo);
//			sqlbv9.put("contno1", this.mContNo);
//			sqlbv9.put("insuredno", tCustomerNo);
//			sqlbv9.put("and1", tCustomerNoType);
//			SSRS tempSSRS = new SSRS();
//			ExeSQL tempExeSQL = new ExeSQL();
//			tempSSRS = tempExeSQL.execSQL(sqlbv9);
//			if(tempSSRS.getMaxRow()>0)
//			{
//				tPhone1 = tempSSRS.GetText(1,1);
//				tPhone2 = tempSSRS.GetText(1,2);
//				if(tPhone1==null)
//				{
//					tPhone1 = " ";
//				}
//				if(tPhone2==null)
//				{
//					tPhone2 = " ";
//				}
//				if (!tPhone1.equals(" ") || !tPhone2.equals(" ")) {
//
//					tAddressNo = tempSSRS.GetText(1, 3);
//					String tSQL_3 = "";
//					SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
//					{
////						String tIDNo15 = get15IDNo(tLDPersonSchema.getIDNo());
//						tSQL_3 = "select * from ldperson a where ( "
//								+ " lower(idno)=lower('"
//								+ "?b1?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?b2?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=a.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=a.customerno)"
//								+ " and '"
//								+ "?b3?"
//								+ "' in (select replace(phone, '无', '') from lcaddress where customerno=a.customerno) "
//								+ " and '"+"?b4?"+"'<>' ' "
//								+ " union "
//								+ " select * from ldperson a where ( "
//								+ " lower(idno)=lower('"
//								+ "?b5?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?b6?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=a.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=a.customerno)"
//								+ " and '"
//								+ "?b7?"
//								+ "' in (select replace(mobile, '无', '') from lcaddress where customerno=a.customerno) "
//								+ " and '"+"?b8?"+"'<>' ' "
//								+ " union "
//								+ " select * from ldperson a where ( "
//								+ " lower(idno)=lower('"
//								+ "?b9?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?b10?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=a.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=a.customerno)"
//								+ " and '"
//								+ "?b11?"
//								+ "' in (select replace(phone, '无', '') from lcaddress where customerno=a.customerno) "
//								+ " and '"+"?b12?"+"'<>' ' "
//								+ " union "
//								+ " select * from ldperson a where ( "
//								+ " lower(idno)=lower('"
//								+ "?b13?"
//								+ "') ) "
//								+ " and customerno <> '"
//								+ "?b14?"
//								+ "' "
//								+ " and  exists (select 1 from lcpol where conttype='1' and appntno=a.customerno"
//				          		+ " union select 1  from lcpol where conttype='1' and insuredno=a.customerno)"
//								+ " and '"
//								+ "?b15?"
//								+ "' in (select replace(mobile, '无', '') from lcaddress where customerno=a.customerno) "
//								+ " and '"+"?b16?"+"'<>' ' "
//
//						// + " and idtype in ('0','1') "
//						;
//					
//						sqlbv10.sql(tSQL_3);
//						sqlbv10.put("b1", tLDPersonSchema.getIDNo());
//						sqlbv10.put("b2", tCustomerNo);
//						sqlbv10.put("b3", tPhone1);
//						sqlbv10.put("b4", tPhone1);
//						sqlbv10.put("b5", tLDPersonSchema.getIDNo());
//						sqlbv10.put("b6", tCustomerNo);
//						sqlbv10.put("b7", tPhone1);
//						sqlbv10.put("b8", tPhone1);
//						sqlbv10.put("b9", tLDPersonSchema.getIDNo());
//						sqlbv10.put("b10", tCustomerNo);
//						sqlbv10.put("b11", tPhone2);
//						sqlbv10.put("b12", tPhone2);
//						sqlbv10.put("b13", tLDPersonSchema.getIDNo());
//						sqlbv10.put("b14", tCustomerNo);	
//						sqlbv10.put("b15", tPhone2);
//						sqlbv10.put("b16", tPhone2);
//						
//						logger.debug("***tSQL_2 Step 3 : " + tSQL_3);
//					} 
//					LDPersonSet tempLDPersonSet = new LDPersonSet();
//					LDPersonDB tempLDPersonDB = new LDPersonDB();
//					tempLDPersonSet = tempLDPersonDB.executeQuery(sqlbv10);
//					if (tempLDPersonSet.size() > 0) {
//						return true;
//					}
//				}
//			}
//			
//		}
//		//////////////////////////////////////////////////////
//		
//		
//		//（4）“性别、出生日期”两项与系统中原有客户资料完全一致，证件类型为“军官证”时，对证件号码中的数字部分进行校验
//		if("2".equals(tLDPersonSchema.getIDType()))
//		{
//			String tSQL_4 = "";
//			tSQL_4 = " select * from ldperson where sex = '"+"?sex?"+"' "
//			       + " and birthday = '"+"?birthday?"+"' "
//			       + " and idtype='2' and customerno<>'"+"?customerno?"+"' "
//			       + " and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//	          	   + " union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)";
//			SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
//			sqlbv11.sql(tSQL_4);
//			sqlbv11.put("sex", tLDPersonSchema.getSex());
//			sqlbv11.put("birthday", tLDPersonSchema.getBirthday());
//			sqlbv11.put("customerno", tCustomerNo);
//			LDPersonSet tempLDPersonSet = new LDPersonSet();
//			LDPersonDB tempLDPersonDB = new LDPersonDB();
//			tempLDPersonSet = tempLDPersonDB.executeQuery(sqlbv11);
//			for(int i=1;i<=tempLDPersonSet.size();i++)
//			{
//				LDPersonSchema tempLDPersonSchema = new LDPersonSchema();
//				tempLDPersonSchema.setSchema(tempLDPersonSet.get(i));
//				String tCurrentIDNo = tLDPersonSchema.getIDNo();
//				tCurrentIDNo = tCurrentIDNo.replaceAll("[^0-9]", "");
//				String tTempIDNo = tempLDPersonSchema.getIDNo();
//				if(tTempIDNo==null)
//				{
//					tTempIDNo = "";
//				}
//				tTempIDNo = tTempIDNo.replaceAll("[^0-9]", "");
//				if(tCurrentIDNo.equals(tTempIDNo))
//				{
//					return true;
//				}
//			}
//		}
		
		return false;
	}
	

	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();


		//tongmeng 2009-02-21 add
		//将删除工作流节点的工作放在客户号手工合并服务类中处理.
		if (this.mLBMissionSet_Delete!=null && this.mLBMissionSet_Delete.size()>0)
			map.put(this.mLBMissionSet_Delete, "INSERT");
		if (this.mLWMissionSet_Delete!=null && this.mLWMissionSet_Delete.size()>0)
			map.put(this.mLWMissionSet_Delete, "DELETE");
		if (this.mLWMissionSet_Update!=null && this.mLWMissionSet_Update.size()>0)
			map.put(this.mLWMissionSet_Update, "UPDATE");
		mResult.add(map);
		return true;
	}
	//tongmeng 2009-02-21 add
	//问题件修改岗工作流扭转的判断放在此处....
	/**
	 * 准备下一结点判断字段
	 * 
	 * @return
	 */

	/**
	 * tongmeng 2009-02-21 add
	 * 问题件修改岗处理完毕后,如果有需要继续下发问题件(机构和业务员),那么不能扭转工作流.
	 * 不需要扭转 返回 true
	 * 需要扭转 返回 false
	 * @return
	 */
	private boolean checkIssuePol()
	{
		boolean tFlag = false;
		String tQuesOrgFlag = (String)mTransferData.getValueByName("QuesOrgFlag");
		String tApproveModifyFlag = (String)mTransferData.getValueByName("ApproveModifyFlag");
		String tSendOperFlag = (String)mTransferData.getValueByName("SendOperFlag");
		if((tQuesOrgFlag!=null&&tQuesOrgFlag.equals("1"))
				||(tApproveModifyFlag!=null&&tApproveModifyFlag.equals("1"))
				||(tSendOperFlag!=null&&tSendOperFlag.equals("1")))
		{
			//只要有机构.业务员或者问题件修改岗的问题件,就不能扭转工作流/
			return true;
		}
		//如果需要客户号手工合并,也不能删除工作流.
		if(this.customerUnion.equals("1"))
		{
			return true;
		}
		return tFlag;
	}
	
	
	
	
	public static void main(String args[]){
//		VData tVData = new VData();
//		TransferData tTransferData = new TransferData();
//		GlobalInput tGlobalInput = new GlobalInput();
//		//tGlobalInput.AgentCom = "86110000";
//		tGlobalInput.ComCode = "86110000";
//		tGlobalInput.ManageCom = "86110000";
//		tGlobalInput.Operator = "001";
//		tTransferData.setNameAndValue("GlobalInput", "");
//		tTransferData.setNameAndValue("AppntNo", "9000003634");
//		tTransferData.setNameAndValue("InsuredNo", "9000003634");
//		tTransferData.setNameAndValue("PolNo", "110410000000262");
//		tTransferData.setNameAndValue("ContNo", "86112009020425");
//		tTransferData.setNameAndValue("MissionID", "86512008111904");
//		tTransferData.setNameAndValue("SubMissionID", "86512008111904");
//		tTransferData.setNameAndValue("ActivityID", "86512008111904");
//		tVData.add(tGlobalInput);
//		tVData.add(tTransferData);
		
		AutoCustomerUnionAfterEndService auto = new AutoCustomerUnionAfterEndService();
//		auto.submitData(tVData, "12123");
//		VData ttVData = new VData();
//		ttVData=auto.getResult();
//		TransferData ttTransferData = new TransferData();
//		ttTransferData = (TransferData) ttVData.getObjectByObjectName("TransferData", 0);
//		String customerUnion = (String) ttTransferData.getValueByName("customerUnion");
//		logger.debug("customerUnion:"+customerUnion);
		/*
		String tIDNo = "tong123meng tese 21 3";
		logger.debug(tIDNo.replaceAll("[^0-9]", ""));
		*/
		
		if(auto.judgeCustomerNo("9000000798",""))
		{
			logger.debug("11");
		}
		else
		{
			logger.debug("22");
		}
	}
}
