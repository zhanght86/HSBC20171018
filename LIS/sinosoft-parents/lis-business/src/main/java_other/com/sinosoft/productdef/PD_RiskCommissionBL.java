

package com.sinosoft.productdef;

import com.sinosoft.lis.db.PD_LACommissionRateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.PD_LACommissionRateSchema;
import com.sinosoft.lis.vschema.PD_LACommissionRateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

public class PD_RiskCommissionBL {
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	PD_LACommissionRateSchema mPD_LACommissionRateSchema   = new PD_LACommissionRateSchema();
	PD_LACommissionRateSet mPD_LACommissionRateSet = new PD_LACommissionRateSet();
	private MMap map = new MMap();
	private PubFun mPubFun = new PubFun();
	
	public PD_RiskCommissionBL(){
		
	}
	
	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();		
		System.out.println("Start LABKCommRateMaintain BL  Submit...");
		tPubSubmit.submitData(mInputData, "");
		System.out.println("End LABKCommRateMaintain BL  Submit...");
		// 如果有需要处理的错误，则返回
		// 如果有需要处理的错误，则返回
		if (tPubSubmit.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LABKCommRateMaintainBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		this.mPD_LACommissionRateSchema.setSchema((PD_LACommissionRateSchema) cInputData
				.getObjectByObjectName("PD_LACommissionRateSchema", 0));	
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LABKCommRateMaintainBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		String currentdate = PubFun.getCurrentDate();
		String currenttime = PubFun.getCurrentTime();
	     if(mOperate.equals("INSERT||MAIN")){	
	    	 ExeSQL tExeSQL = new ExeSQL();
    		 SSRS aSSRS = new SSRS();
    		 String sql = "select l.StartDate,l.IDNo from PD_LACommissionRate l where 1=1"
				 +" and l.ManageCom = '"+mPD_LACommissionRateSchema.getManageCom()+"'"
			 	 +" and l.AgentCom = '"+mPD_LACommissionRateSchema.getAgentCom()+"' "
			 	 +" and l.RiskCode = '"+mPD_LACommissionRateSchema.getRiskCode()+"'"		 	
			 	 +" and l.PayYear = '"+mPD_LACommissionRateSchema.getPayYear()+"'"
			 	 +" and l.StartDate = '"+mPD_LACommissionRateSchema.getStartDate()+"'"	 
			 	 +" and l.Branchtype = '"+mPD_LACommissionRateSchema.getBranchtype()+"'"
    		     +" and l.ValidFlag = '01'";//有效标志（validFlag） :01-有效、02-无效
    		 if(mPD_LACommissionRateSchema.getPayYears()!=null||!mPD_LACommissionRateSchema.getPayYears().equals("")){
    			 sql+= " and l.PayYears = '"+mPD_LACommissionRateSchema.getPayYears()+"'";
    		 }
    		 if(mPD_LACommissionRateSchema.getInsureAgeStart()!=null||!mPD_LACommissionRateSchema.getInsureAgeStart().equals("")){
    			 sql+= " and l.InsureAgeStart = '"+mPD_LACommissionRateSchema.getInsureAgeStart()+"'";
    		 }
    		 if(mPD_LACommissionRateSchema.getInsureAgeEnd()!=null||!mPD_LACommissionRateSchema.getInsureAgeEnd().equals("")){
    			 sql+= " and l.InsureAgeEnd = '"+mPD_LACommissionRateSchema.getInsureAgeEnd()+"'";
    		 }
    		 if(mPD_LACommissionRateSchema.getPayToAge()!=null||!mPD_LACommissionRateSchema.getPayToAge().equals("")){
    			 sql+= " and l.PayToAge = '"+mPD_LACommissionRateSchema.getPayToAge()+"'";
    		 }
    		 if(mPD_LACommissionRateSchema.getCurrency()!=null||!mPD_LACommissionRateSchema.getCurrency().equals("")){
    			 sql+= " and l.Currency = '"+mPD_LACommissionRateSchema.getCurrency()+"'";
    		 }
    		 if(mPD_LACommissionRateSchema.getPayType()!=null||!mPD_LACommissionRateSchema.getPayType().equals("")){
    			 sql+= " and l.PayType = '"+mPD_LACommissionRateSchema.getPayType()+"'";
    		 }
    		 if(mPD_LACommissionRateSchema.getInvestType()!=null||!mPD_LACommissionRateSchema.getInvestType().equals("")){
    			 sql+= " and l.InvestType = '"+mPD_LACommissionRateSchema.getInvestType()+"'";
    		 }
    		 if(mPD_LACommissionRateSchema.getProtectionPlan()!=null||!mPD_LACommissionRateSchema.getProtectionPlan().equals("")){
    			 sql+= " and l.ProtectionPlan = '"+mPD_LACommissionRateSchema.getProtectionPlan()+"'";
    		 }
			aSSRS = tExeSQL.execSQL(sql);// 查询出相同信息，放在aSSRS二维数组
			int tcount = aSSRS.getMaxRow();
			if(tcount!=0){
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LABKCommRateMaintainBL";
				tError.functionName = "dealData";
				tError.errorMessage = "该条信息已存在！";
				this.mErrors.addOneError(tError);
				return false;	
				
			}
			//根据代理机构查出销售渠道
			SSRS tSSRS = new SSRS();
			String sql2 = "select BranchType from LACom where ManageCom = '"+mPD_LACommissionRateSchema.getManageCom()+"'";
			tSSRS = tExeSQL.execSQL(sql2);
			mPD_LACommissionRateSchema.setBranchtype(tSSRS.GetText(1, 1));
			
				mPD_LACommissionRateSchema.setValidFlag("01");
				//String aIDNo = PubFun.CreateSeq("SEQ_LACOMMISSIONRATE", 10);// 生成ID号
				String aIDNo = "PE"+PubFun1.CreateMaxNo("PE_LACOMMISSIONRATE", 8);
				mPD_LACommissionRateSchema.setIDNo(aIDNo);
				mPD_LACommissionRateSchema.setActionType("");
				mPD_LACommissionRateSchema.setAppState("02");
				mPD_LACommissionRateSchema.setMakeDate(currentdate);
				mPD_LACommissionRateSchema.setMakeTime(currenttime);
				mPD_LACommissionRateSchema.setModifyDate(currentdate);
				mPD_LACommissionRateSchema.setModifyTime(currenttime);
				mPD_LACommissionRateSet.add(mPD_LACommissionRateSchema);
			
			map.put(mPD_LACommissionRateSet,"INSERT");
			
	     }
	     
	     if(mOperate.equals("UPDATE||MAIN")){
	    	 PD_LACommissionRateSchema tPD_LACommissionRateSchema = new PD_LACommissionRateSchema();
 
	    	 ExeSQL tExeSQL = new ExeSQL();
	    	 
	    	SSRS aSSRS = new SSRS();
    		String sql = "select l.AppDate,l.AppTime,l.MakeDate,l.MakeTime,l.OrigIDNo,ActionType from PD_LACommissionRate l where 1=1"
				 +" and l.IDNo= '"+mPD_LACommissionRateSchema.getIDNo()+"'";
			aSSRS = tExeSQL.execSQL(sql);// 查询要修改的记录信息，放在aSSRS二维数组
			
			if(aSSRS.GetText(1, 6).equals("03")){
				// @@错误处理
				CError tError = new CError();
	 			 tError.moduleName = "LABKCommRateAuditBL";
	 			 tError.functionName = "dealData";
	 			 tError.errorMessage = "该条信息的操作类型是删除操作,无法对其修改！";
	 			 this.mErrors.addOneError(tError);
	 			 return false;
			}
			
			SSRS tSSRS = new SSRS();
			 String sql2 = "select l.IDNo,l.ActionType from PD_LACommissionRate l where 1=1"
				 +" and l.OrigIDNo = '"+mPD_LACommissionRateSchema.getIDNo()+"'"
				 +" and l.ValidFlag = '01'"
				 +" and l.AppState = '02'";	
			 tSSRS = tExeSQL.execSQL(sql2);// 查询出删除或修改待审核的记录，放在tSSRS二维数组中
			 if(tSSRS.GetText(1, 2).equals("03")){
				// @@错误处理
				 CError tError = new CError();
	 			 tError.moduleName = "LABKCommRateAuditBL";
	 			 tError.functionName = "dealData";
	 			 tError.errorMessage = "该条信息已删除，待审核中,无法对其修改！";
	 			 this.mErrors.addOneError(tError);
	 			 return false;	
				}
	    	 
		//根据代理机构查出销售渠道
				SSRS nSSRS = new SSRS();
				String sql7 = "select BranchType from LACom where ManageCom = '"+mPD_LACommissionRateSchema.getManageCom()+"'";
				nSSRS = tExeSQL.execSQL(sql7);
				mPD_LACommissionRateSchema.setBranchtype(nSSRS.GetText(1, 1));
					
				 //tPD_LACommissionRateSchema.setBranchtype(mPD_LACommissionRateSchema.getBranchtype());
	        	 tPD_LACommissionRateSchema.setManageCom(mPD_LACommissionRateSchema.getManageCom());
	        	 tPD_LACommissionRateSchema.setAgentCom(mPD_LACommissionRateSchema.getAgentCom());
	        	 tPD_LACommissionRateSchema.setRiskCode(mPD_LACommissionRateSchema.getRiskCode());
	        	 tPD_LACommissionRateSchema.setPayYears(mPD_LACommissionRateSchema.getPayYears());
	        	 tPD_LACommissionRateSchema.setInsureAgeStart(mPD_LACommissionRateSchema.getInsureAgeStart());
	        	 tPD_LACommissionRateSchema.setInsureAgeEnd(mPD_LACommissionRateSchema.getInsureAgeEnd());
	        	 tPD_LACommissionRateSchema.setPayToAge(mPD_LACommissionRateSchema.getPayToAge());
	        	 tPD_LACommissionRateSchema.setCurrency(mPD_LACommissionRateSchema.getCurrency());
	        	 tPD_LACommissionRateSchema.setPayType(mPD_LACommissionRateSchema.getPayType());
	        	 tPD_LACommissionRateSchema.setInvestType(mPD_LACommissionRateSchema.getInvestType());
	        	 tPD_LACommissionRateSchema.setProtectionPlan(mPD_LACommissionRateSchema.getProtectionPlan());
	        	 tPD_LACommissionRateSchema.setPayYear(mPD_LACommissionRateSchema.getPayYear());
	        	 tPD_LACommissionRateSchema.setCommRate(mPD_LACommissionRateSchema.getCommRate());
	        	 tPD_LACommissionRateSchema.setStartDate(mPD_LACommissionRateSchema.getStartDate());
	        	 tPD_LACommissionRateSchema.setAppState("02");
	        	 tPD_LACommissionRateSchema.setIDNo(mPD_LACommissionRateSchema.getIDNo());
	        	 tPD_LACommissionRateSchema.setOrigIDNo("");
	        	 tPD_LACommissionRateSchema.setOperator(mPD_LACommissionRateSchema.getOperator());
	        	 tPD_LACommissionRateSchema.setMakeDate(aSSRS.GetText(1, 3));
	        	 tPD_LACommissionRateSchema.setMakeTime(aSSRS.GetText(1, 4));
	        	 tPD_LACommissionRateSchema.setValidFlag("01");
	        	 tPD_LACommissionRateSchema.setActionType("");
				 tPD_LACommissionRateSchema.setModifyDate(currentdate);
				 tPD_LACommissionRateSchema.setModifyTime(currenttime);
				 tPD_LACommissionRateSchema.setStaffRate("0");
				 tPD_LACommissionRateSchema.setDefaultFlag("1");
				 tPD_LACommissionRateSchema.setSRFlag("N");
				 tPD_LACommissionRateSchema.setAppDate(currentdate);
				 tPD_LACommissionRateSchema.setAppTime(currenttime);
				 mPD_LACommissionRateSet.add(tPD_LACommissionRateSchema);
				 map.put(mPD_LACommissionRateSet,"UPDATE");
			// }
			
	     }
	     if(mOperate.equals("DELETE||MAIN")){
	    	 
	    	 ExeSQL tExeSQL = new ExeSQL();
    		 SSRS aSSRS = new SSRS();
    		 String sql = "select l.ManageCom,l.AgentCom, l.RiskCode,l.PayYears,l.InsureAgeStart,l.InsureAgeEnd,l.PayToAge,l.Currency,l.InvestType,l.PayType,l.ProtectionPlan,l.PayYear,l.CommRate,l.StartDate,l.EndDate,l.AppState,l.AppDate,l.AppTime,l.Branchtype,l.MakeDate,l.MakeTime,l.StaffRate from PD_LACommissionRate l where 1=1"
				 +" and l.IDNo= '"+mPD_LACommissionRateSchema.getIDNo()+"'";	
    		 aSSRS = tExeSQL.execSQL(sql);// 查询要删除的信息，放在aSSRS二维数组
    		 PD_LACommissionRateSchema tPD_LACommissionRateSchema = new PD_LACommissionRateSchema();   
    		 PD_LACommissionRateDB tPD_LACommissionRateDB=new PD_LACommissionRateDB();
    		 tPD_LACommissionRateDB.setIDNo(mPD_LACommissionRateSchema.getIDNo());
    		 if(!tPD_LACommissionRateDB.getInfo()){
				 CError tError = new CError();
					tError.moduleName = "LABKCommRateMaintainBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该条记录不存在！";
					this.mErrors.addOneError(tError);
					return false;
    		 }else{
    			 return tPD_LACommissionRateDB.delete();
    		 }
	     }
		return true;
	}
	
	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAServCommissionRateBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	//时间从早到晚进行排序
	public void Sort(String[] sort){
		for (int i = 0; i < sort.length; i++) {
			for (int j = sort.length - 1; j > i; j--) {
				String dates = mPubFun.getBeforeDate(sort[i], sort[j]); // 两个日期比较取较早的日期
				String datess = mPubFun.getLaterDate(sort[i], sort[j]); // 两个日期比较取较晚的日期
				sort[i] = dates;
				sort[j] = datess;
			}
		}
	}
	public VData getResult() {
		return this.mResult;
	}
	

}

