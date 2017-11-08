package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.util.Vector;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 计算延迟利息处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zhangzheng
 * @version 1.0
 */
public class LLClaimYZLXBL  implements BusinessService{
private static Logger logger = Logger.getLogger(LLClaimYZLXBL.class);
	
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mG = new GlobalInput();
	private String ClmNo;
	private int passDay;
	private String mmakedate;
	private String enddate;
	private String hasInqApply;//是否有调查信息
	private String mFeeFinalType="YCLX";
	private LJSGetClaimSet mLJSGetClaimSet=new LJSGetClaimSet();
	private String mEndCaseDate;
	private String mClmState;
	private boolean tFlag=true;
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
		logger.debug("----------LLEndCaseBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("----------after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------after checkInputData----------");
		
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------after dealData----------");
		
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");
		
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			
			// @@错误处理
			CError.buildErr(this, "数据提交失败,原因是"+tPubSubmit.mErrors.getLastError());
			return false;
		}
		mInputData = null;
		return true;
	}

	
	/**
	 * 业务处理
	 * @return
	 */
	private boolean dealData()
	{

		if(mOperate.equals("YZLX_compute")){
			
		  //业务规则校验
		  if(!checkCon())
			  return false;
		  if(tFlag)
		  {
			  //计算延迟利息
			  if(!computeall())
				  return false;
			  
			  //保存数据
			  if(!saveResult())
				  return false;
		  }
		  
		  return true;
		}
		
		/**
		 * 取消延迟利息
		 */
		else if(mOperate.equals("YZLX_delete")){
			
			if(!DBOp1())
				  return false;
			
			return true;
		}
		
		return true;
   }
	
	
	/**
	 * 
	 * 取消取消延迟利息计算
	 * @return
	 */
	private boolean DBOp1() {
		
		
		LJSGetClaimDB tLJSGetClaimDB=new LJSGetClaimDB();
		tLJSGetClaimDB.setOtherNo(this.ClmNo);
		tLJSGetClaimDB.setFeeFinaType(mFeeFinalType);
		LJSGetClaimSet tLJSGetClaimSet=tLJSGetClaimDB.query();
		
		if(tLJSGetClaimSet.size()==0){
			CError.buildErr(this,"没有延滞利息数据，请确认");
			return false;
		}
		else
		{
			for(int i=1;i<=tLJSGetClaimSet.size();i++){
				
				String sql="update ljsget set sumgetMoney=("
					  +" sumgetmoney-"+"?getpay?"+"),"
					  +" modifydate='"+"?CurDate?"+"',modifytime='"+"?CurTime?"+"' "
					  +" where otherno='"+"?otherno?"+"' and othernotype='5' and getnoticeno='"+"?getnoticeno?"+"'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(sql);
				sqlbv.put("getpay", tLJSGetClaimSet.get(i).getPay());
				sqlbv.put("CurDate", PubFun.getCurrentDate());
				sqlbv.put("CurTime", PubFun.getCurrentTime());
				sqlbv.put("otherno", tLJSGetClaimSet.get(i).getOtherNo());
				sqlbv.put("getnoticeno", tLJSGetClaimSet.get(i).getGetNoticeNo());
				map.put(sqlbv, "UPDATE");	
				
			}
		}
		

		String sql1="delete from ljsgetclaim where otherno='"+"?ClmNo?"+
			"' and feefinatype='"+"?feefinatype?"+"'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql1);
		sqlbv1.put("ClmNo", this.ClmNo);
		sqlbv1.put("feefinatype", mFeeFinalType);
		map.put(sqlbv1, "DELETE");
		
		tLJSGetClaimDB=null;
		tLJSGetClaimSet=null;

		return true;
	}

	
	private boolean saveResult() {
		mResult.add(mLJSGetClaimSet);
		return true;
	}

	/**
	 * 计算延迟利息
	 * 1 将算出的利息平均到每个受益人的上,llbnfGather平均增加金额,llbnf增加到这个受益人相应的查询到第1条记录上;
	 * 2 增加ljaget和ljagetclaim利息记录
	 */
	private boolean computeall() {
		
		/**
		 * No1.0 计算延迟利息并增加应付子表信息
		 * 
		 */
		LLBalanceDB tLLClaimPolicyDB=new LLBalanceDB();
		tLLClaimPolicyDB.setClmNo(this.ClmNo);
		tLLClaimPolicyDB.setFeeOperationType("A");//只针对理算金进行延滞利息的计算
		LLBalanceSet tLLBalanceSet=tLLClaimPolicyDB.query();
		if(tLLBalanceSet.size()==0)
		{
			CError.buildErr(this, "查询不到案件"+ClmNo+"的结算信息!");
			return false;	
		}
		
		for(int i=1;i<=tLLBalanceSet.size();i++){		

			
			/**
			 * No2.1 计算每个险种的延迟利息
			 * 复利 趸交保费本金及利息 趸交利息为贷款信息
			 */
			double lx=0.00;
			if(hasInqApply.equals("N")){//如果不存在调查信息且立案日期至操作日期当日超过10日，从11日开始计算延滞利息；
				 lx = tLLBalanceSet.get(i).getPay() * AccountManage.calMultiRateMS(PubFun.calDate(mmakedate,10,"D",null),enddate, 
						 tLLBalanceSet.get(i).getRiskCode(),"00", "R", "C", "Y",tLLBalanceSet.get(i).getCurrency());    
			}else if(hasInqApply.equals("Y")){//如果存在调查信息且立案日期至操作日期当日超过30日，从31日开始计算延滞利息；
				 lx = tLLBalanceSet.get(i).getPay() * AccountManage.calMultiRateMS(PubFun.calDate(mmakedate,30,"D",null),enddate, 
						 tLLBalanceSet.get(i).getRiskCode(),"00", "R", "C", "Y",tLLBalanceSet.get(i).getCurrency());    
			}
			logger.debug("llLLBalance表第"+i+"条记录："+"Clmno="+tLLBalanceSet.get(i).getClmNo()+"ContNo="+tLLBalanceSet.get(i).getContNo()+"PolNo="+tLLBalanceSet.get(i).getPolNo()+"RiskCode="+tLLBalanceSet.get(i).getRiskCode()+"Dutycode="+tLLBalanceSet.get(i).getDutyCode()+"GetDutyCode="+tLLBalanceSet.get(i).getGetDutyCode()+"GetDutyKind="+tLLBalanceSet.get(i).getGetDutyKind()+"FeeOperationType="+tLLBalanceSet.get(i).getFeeOperationType()+"Pay="+tLLBalanceSet.get(i).getPay());
			logger.debug("延迟利息为："+lx);
					
			lx=PubFun.setPrecision(lx,"0.00");
			
			if(lx<0)
			{
				CError.buildErr(this,"计算延迟利息不能小于0");
				return false;
			}
			

			LLBnfDB tLLBnfDB=new LLBnfDB();
			tLLBnfDB.setClmNo(this.ClmNo);
			tLLBnfDB.setPolNo(tLLBalanceSet.get(i).getPolNo());
			tLLBnfDB.setFeeOperationType(tLLBalanceSet.get(i).getFeeOperationType());
			LLBnfSet tLLBnfSet=tLLBnfDB.query();
			
			/**
			 * No2.3 循环受益人表分配延迟利息
			 * 
			 */
			
			for(int m=1;m<=tLLBnfSet.size();m++)
			{
				LJSGetClaimSet tLJSGetClaimSet=null;
				LJSGetClaimSchema tLJSGetClaimSchema=null;
				
				String LjsgetclaimSql="select * from ljsgetclaim where  polno='"+"?polno?"
				+ "' and dutycode='" + "?dutycode?" 
				+ "' and getdutycode='" + "?getdutycode?" 
				+ "' and getdutykind='" + "?getdutykind?"
				+ "' and otherno='"+"?ClmNo?"+"'and getnoticeno='"+"?getnoticeno?"
		        + "' and feeoperationtype='" + "?feeoperationtype?" + "'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(LjsgetclaimSql);
				sqlbv2.put("polno", tLLBalanceSet.get(i).getPolNo());
				sqlbv2.put("dutycode", tLLBalanceSet.get(i).getDutyCode());
				sqlbv2.put("getdutycode", tLLBalanceSet.get(i).getGetDutyCode());
				sqlbv2.put("getdutykind", tLLBalanceSet.get(i).getGetDutyKind());
				sqlbv2.put("ClmNo", this.ClmNo);
				sqlbv2.put("getnoticeno", tLLBnfSet.get(m).getOtherNo());
				sqlbv2.put("feeoperationtype", tLLBalanceSet.get(i).getFeeOperationType());
				LJSGetClaimDB tLJSGetClaimDB=new LJSGetClaimDB();
				tLJSGetClaimSet=tLJSGetClaimDB.executeQuery(sqlbv2);
				
				if(tLJSGetClaimSet.size()==0){
					CError.buildErr(this,"应付记录错误");
					return false;
				}
				
				tLJSGetClaimSchema =tLJSGetClaimSet.get(1);
				
				tLJSGetClaimSchema.setFeeFinaType(mFeeFinalType);
				double Bnflx=lx*(tLLBnfSet.get(m).getBnfLot())*0.01;
				tLJSGetClaimSchema.setPay(Bnflx);
				
				tLJSGetClaimSchema.setMakeDate(PubFun.getCurrentDate());
				tLJSGetClaimSchema.setModifyDate(PubFun.getCurrentDate());
				tLJSGetClaimSchema.setMakeTime(PubFun.getCurrentTime());
				tLJSGetClaimSchema.setModifyTime(PubFun.getCurrentTime());
				mLJSGetClaimSet.add(tLJSGetClaimSchema);
				
				String sql="update ljsget set sumgetMoney=("
					  +" sumgetmoney+"+"?getpay?"+"),"
					  +" modifydate='"+"?CurDate?"+"',modifytime='"+"?CurTime?"+"' "
					  +" where otherno='"+"?ClmNo?"+"' and othernotype='5' and getnoticeno='"+"?getnoticeno?"+"' and "+m+"+"+i+"="+m+"+"+i;
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(sql);
				sqlbv3.put("getpay", tLJSGetClaimSchema.getPay());
				sqlbv3.put("CurDate", PubFun.getCurrentDate());
				sqlbv3.put("CurTime", PubFun.getCurrentTime());
				sqlbv3.put("ClmNo", this.ClmNo);
				sqlbv3.put("getnoticeno", tLJSGetClaimSchema.getGetNoticeNo());
				map.put(sqlbv3, "UPDATE");

			}
		}
		
		map.put(mLJSGetClaimSet, "DELETE&INSERT");
		
		return true;
	}
	
	
	/**
	 * 汇总受益人信息
	 * @param pGetNoticeNo:给付通知书号
	 * @param pBnfNo:受益人序号
	 * @param pLX:利息
	 * @return LLBnfSchema
	 */
	private LLBnfSchema setLLBnf(String pGetNoticeNo,double pLX) {

		LLBnfGatherDB tLLBnfGatherDB=new LLBnfGatherDB();
		tLLBnfGatherDB.setClmNo(this.ClmNo);
		tLLBnfGatherDB.setBnfKind("A");//A:正常赔付,B:预付
		tLLBnfGatherDB.setOtherNo(pGetNoticeNo);
		
		LLBnfGatherSet tLLBnfGatherSet=tLLBnfGatherDB.query();
		
		LLBnfGatherSchema pLLBnfGatherSchema=null;
		
		if(tLLBnfGatherSet.size()==0){
			CError.buildErr(this,"查询理赔受益人汇总表记录为空!");
			return null;
		}
		else
		{
			pLLBnfGatherSchema=tLLBnfGatherSet.get(1);
		}
		
		LLBnfSchema tLLBnfSchema = new LLBnfSchema();
		
		tLLBnfSchema.setClmNo(pLLBnfGatherSchema.getClmNo());
		tLLBnfSchema.setCaseNo(pLLBnfGatherSchema.getClmNo());
		tLLBnfSchema.setBatNo("0");
		tLLBnfSchema.setBnfKind(pLLBnfGatherSchema.getBnfKind());
		tLLBnfSchema.setInsuredNo(pLLBnfGatherSchema.getInsuredNo());
		tLLBnfSchema.setBnfNo(pLLBnfGatherSchema.getBnfNo());
		tLLBnfSchema.setCustomerNo(pLLBnfGatherSchema.getCustomerNo());
		tLLBnfSchema.setName(pLLBnfGatherSchema.getName());
		tLLBnfSchema.setPayeeNo(pLLBnfGatherSchema.getPayeeNo());
		tLLBnfSchema.setPayeeName(pLLBnfGatherSchema.getPayeeName());
		tLLBnfSchema.setBnfType(pLLBnfGatherSchema.getBnfType());
		tLLBnfSchema.setBnfGrade(pLLBnfGatherSchema.getBnfGrade());
		tLLBnfSchema.setRelationToInsured(pLLBnfGatherSchema.getRelationToInsured());
		tLLBnfSchema.setSex(pLLBnfGatherSchema.getSex());
		tLLBnfSchema.setBirthday(pLLBnfGatherSchema.getBirthday());
		tLLBnfSchema.setIDType(pLLBnfGatherSchema.getIDType());
		tLLBnfSchema.setIDNo(pLLBnfGatherSchema.getIDNo());
		tLLBnfSchema.setRelationToPayee(pLLBnfGatherSchema.getRelationToPayee());
		tLLBnfSchema.setPayeeSex(pLLBnfGatherSchema.getPayeeSex());
		tLLBnfSchema.setPayeeBirthday(pLLBnfGatherSchema.getPayeeBirthday());
		tLLBnfSchema.setPayeeIDType(pLLBnfGatherSchema.getIDType());
		tLLBnfSchema.setPayeeIDNo(pLLBnfGatherSchema.getPayeeIDNo());
		tLLBnfSchema.setGetMoney(pLX);
		tLLBnfSchema.setFeeOperationType(mFeeFinalType);
		tLLBnfSchema.setBnfLot(pLLBnfGatherSchema.getBnfLot());
	    
		tLLBnfSchema.setCasePayMode(pLLBnfGatherSchema.getCasePayMode());
		tLLBnfSchema.setCasePayFlag("0");//保险金支付标志
		tLLBnfSchema.setBankCode(pLLBnfGatherSchema.getBankCode());
		tLLBnfSchema.setBankAccNo(pLLBnfGatherSchema.getBankAccNo());
		tLLBnfSchema.setAccName(pLLBnfGatherSchema.getAccName());
		tLLBnfSchema.setOtherNoType(pLLBnfGatherSchema.getOtherNoType());
		tLLBnfSchema.setOBankCode(pLLBnfGatherSchema.getBnfNo());//备份受益人序号
        tLLBnfSchema.setOtherNo(pLLBnfGatherSchema.getOtherNo());
        
        tLLBnfSchema.setGrpContNo("00000000000000000000");
        tLLBnfSchema.setGrpPolNo("00000000000000000000");
        tLLBnfSchema.setContNo("00000000000000000000");
        tLLBnfSchema.setContNo("00000000000000000000");
        tLLBnfSchema.setPolNo("00000000000000000000");
        
        tLLBnfSchema.setOperator(mG.Operator);
        tLLBnfSchema.setMakeDate(CurrentDate);
        tLLBnfSchema.setMakeTime(CurrentTime);
        tLLBnfSchema.setModifyDate(CurrentDate);
        tLLBnfSchema.setModifyTime(CurrentTime);
        
        tLLBnfGatherSet=null;
        tLLBnfGatherDB=null;
        pLLBnfGatherSchema=null;
 
		return tLLBnfSchema;

	}

	
	/**
	 * zhangzheng 
	 * 业务规则校验
	 * @return
	 */
	private boolean checkCon() {
		
		ExeSQL tExeSQL = new ExeSQL();

			
		//给付金额大于400
//		String tSelectSql1 = "";
//		tSelectSql1 = " select nvl(count(1),0) from llclaim where realpay>=400 and clmno= '" + ClmNo + "'";			
//		logger.debug("--判定在案件给付金额是否大于等于400:"+tSelectSql1);
//
//		int tCount = Integer.parseInt(tExeSQL.getOneValue(tSelectSql1));
//		if (tCount<=0) {
//			CError.buildErr(this, "案件给付金额小于400,不用计算延迟利息!");
//			return false;
//		}

		if(hasInqApply.equals("N") && passDay <=10){
				CError.buildErr(this, "此案件没有调查信息，案件处理时间小于10天,不用计算延迟利息!");
//				return false;由于延滞利息的计算纳入审批行列，所以不能返回错误信息
				tFlag=false;
				return true;
		}else if(hasInqApply.equals("Y") && passDay <=30){
				CError.buildErr(this, "此案件有调查信息，案件处理时间小于30天,不用计算延迟利息!");
//				return false;
				tFlag=false;
				return true;
		}
		
		String tSelectSql3="select (case when count(1) is null then 0 else count(1) end) from ljsgetclaim where otherno='"+"?clmno?"+"' and FeeFinaType='"+"?FeeFinaType?"+"'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSelectSql3);
		sqlbv4.put("clmno", ClmNo);
		sqlbv4.put("FeeFinaType", mFeeFinalType);
		int tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv4));	
		if (tCount>0) {
			CError.buildErr(this, "案件已计算延迟利息,不能重复计算!");
//			return false;
			tFlag=false;
			return true;
		}	

		tSelectSql3=null;
		tExeSQL=null;

		return true;
	}

	public VData getResult(){
		return mResult;
	}
	

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() 
	{
		
		if(!mOperate.equals("YZLX_compute") && !mOperate.equals("YZLX_delete")){
			CError.buildErr(this,"不支持的操作");
			return false;
		}
		
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		TransferData tTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",0);
		ClmNo = (String) tTransferData.getValueByName("ClmNo");
		if(ClmNo==null || ClmNo.equals("")){
			CError.buildErr(this, "无效赔案号");
			return false;
		}
		mClmState= (String) tTransferData.getValueByName("ClmState");
		mEndCaseDate= (String) tTransferData.getValueByName("EndCaseDate");
		return true;
	}
	
	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		
		LLClaimDB tLLClaimDB=new LLClaimDB();
		tLLClaimDB.setClmNo(this.ClmNo);		
		if(!tLLClaimDB.getInfo()){
			CError.buildErr(this,"没有赔案记录");
			return false;
		}
		
		if("".equals(tLLClaimDB.getEndCaseDate()) || tLLClaimDB.getEndCaseDate()==null)
		{
			tLLClaimDB.setEndCaseDate(mEndCaseDate);
			tLLClaimDB.setClmState(mClmState);
		}
		LLRegisterDB tLLRegisterDB=new LLRegisterDB();
		tLLRegisterDB.setRgtNo(tLLClaimDB.getRgtNo());
		if(!tLLRegisterDB.getInfo()){
			CError.buildErr(this,"没有立案记录");
			return false;
		}
		
		passDay=PubFun.calInterval(tLLRegisterDB.getRgtDate(),tLLClaimDB.getEndCaseDate(),"D");
		mmakedate=tLLRegisterDB.getRgtDate();
		enddate=tLLClaimDB.getEndCaseDate();		
		logger.debug("案件:"+ClmNo+"立案日期:"+mmakedate+",结案日期:"+enddate);
		
		//不清楚之前为什么要校验理赔状态为50，这里先不讨论 modify by wx 2016年10月31日16:57:27 start
		//修改状态取值，改为取前边类传过来的，而不是数据库中的
		if(!mClmState.equals("50")){
		//modify by wx 2016年10月31日16:57:27 end
			CError.buildErr(this,"非待确认案件");
			return false;
		}
		
		LLInqApplyDB LLInqApplyDB=new LLInqApplyDB();
		LLInqApplyDB.setClmNo(this.ClmNo);		
		LLInqApplySet tLLInqApplySet=LLInqApplyDB.query();
		if(tLLInqApplySet==null || tLLInqApplySet.size()<=0){			
			hasInqApply="N";//没有调查信息
		}else{
			hasInqApply="Y";//有调查信息
		}
		
		return true;
	}
	
	
	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LLClaimYZLXBL tLLClaimYZLXBL=new LLClaimYZLXBL();
//		VData  tVData=new VData();
//		TransferData tTransferData=new TransferData();
//		tTransferData.setNameAndValue("ClmNo","86130020050520000581");
//		tVData.add(tTransferData);
//		tLLClaimYZLXBL.submitData(tVData,"YZLX");
		
		tLLClaimYZLXBL.ClmNo="86000020060520000026";
		tLLClaimYZLXBL.DBOp1();
	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
