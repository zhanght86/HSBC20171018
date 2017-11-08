

package com.sinosoft.productdef;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.lis.db.LACommissionRateDB;
import com.sinosoft.lis.db.PD_LDStaffRateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LACommissionRateSchema;
import com.sinosoft.lis.schema.PD_LDStaffRateSchema;
import com.sinosoft.lis.schema.PD_LDStaffRateSchema;
import com.sinosoft.lis.vschema.LACommissionRateSet;
import com.sinosoft.lis.vschema.PD_LDStaffRateSet;
import com.sinosoft.lis.vschema.PD_LDStaffRateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

public class PD_RiskStaffRateBL {
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	PD_LDStaffRateSchema mPD_LDStaffRateSchema   = new PD_LDStaffRateSchema();
	PD_LDStaffRateSet mPD_LDStaffRateSet = new PD_LDStaffRateSet();
	private MMap map = new MMap();
	private PubFun mPubFun = new PubFun();
	
	public PD_RiskStaffRateBL(){
		
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
		System.out.println("Start PD_RiskStaffRateBL BL  Submit...");
		tPubSubmit.submitData(mInputData, "");
		System.out.println("End PD_RiskStaffRateBL BL  Submit...");
		// 如果有需要处理的错误，则返回
		// 如果有需要处理的错误，则返回
		if (tPubSubmit.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_RiskStaffRateBL";
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
		
		this.mPD_LDStaffRateSchema.setSchema((PD_LDStaffRateSchema) cInputData
				.getObjectByObjectName("PD_LDStaffRateSchema", 0));
		this.mPD_LDStaffRateSet.add(mPD_LDStaffRateSchema);
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_RiskStaffRateBL";
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
		
		/**
		 * ----------------------------------------
		 * 1 新增
		 * ----------------------------------------
		 * */
	     if(mOperate.equals("INSERT||MAIN")){	
	    	 
	    	 PD_LDStaffRateSet tPD_LDStaffRateSet = new PD_LDStaffRateSet();
	    	 mPD_LDStaffRateSchema = mPD_LDStaffRateSet.get(1);
	    	 ExeSQL tExeSQL = new ExeSQL();
	    	 
	    	 /**
	    	  * -------------------------------
	    	  * 1.1查询销售渠道，若填写了代理机构，
	    	  * 	按照代理机构的销售渠道，否则按照
	    	  * 	管理机构前四位判断销售渠道
	    	  * -------------------------------
	    	  * */
	    	 SSRS exBranchTypeSSRS = new SSRS();
			String exBranchType = "select BranchType from LACom where ManageCom = '"+mPD_LDStaffRateSchema.getManageCom()+"'";
			exBranchTypeSSRS = tExeSQL.execSQL(exBranchType);
			mPD_LDStaffRateSchema.setBranchtype(exBranchTypeSSRS.GetText(1, 1));
	    	
			/**
			 * -----------------------------------------
			 * 1.2查询相同和相似的信息
			 * -----------------------------------------
			 * */	
    		 String sql = "select l.StartDate,l.IDNo from PD_LDStaffRate l where 1=1"
				 +" and l.ManageCom = '"+mPD_LDStaffRateSchema.getManageCom()+"'"
			 	 +" and l.RiskCode = '"+mPD_LDStaffRateSchema.getRiskCode()+"'"
			 	 +" and l.PayYear = '"+mPD_LDStaffRateSchema.getPayYear()+"'"
			 	 +" and l.StartDate = '"+mPD_LDStaffRateSchema.getStartDate()+"'"
			 	 +" and l.Branchtype = '"+mPD_LDStaffRateSchema.getBranchtype()+"'"
			 	 +" and l.ValidFlag = '01'";//有效标志（validFlag） :01-有效、02-无效
    		   		 
    		 String sql2 = "select l.StartDate,l.IDNo from PD_LDStaffRate l where 1=1"
				 +" and l.ManageCom = '"+mPD_LDStaffRateSchema.getManageCom()+"'"
			 	 +" and l.RiskCode = '"+mPD_LDStaffRateSchema.getRiskCode()+"'"
			 	 +" and l.PayYear = '"+mPD_LDStaffRateSchema.getPayYear()+"'"
			 	 +" and l.Branchtype = '"+mPD_LDStaffRateSchema.getBranchtype()+"'"
			 	 +" and l.ValidFlag = '01'"//有效标志（validFlag） :01-有效、02-无效
			 	 +" and l.AppState = '02'";
    		 if(mPD_LDStaffRateSchema.getAgentCom()!=null&&!mPD_LDStaffRateSchema.getAgentCom().equals("")){
    			 sql+= " and (l.AgentCom = '"+mPD_LDStaffRateSchema.getAgentCom()+"'  or l.AgentCom ='000000')";
    			 sql2+= " and (l.AgentCom = '"+mPD_LDStaffRateSchema.getAgentCom()+"'  or l.AgentCom ='000000')";
    		 }
    		 
    		 if(mPD_LDStaffRateSchema.getPayYears()!=null&&!mPD_LDStaffRateSchema.getPayYears().equals("")){
    			 sql+=" and (l.PayYears = '"+mPD_LDStaffRateSchema.getPayYears()+"' or l.PayYears is null)";
    			 sql2+=" and (l.PayYears = '"+mPD_LDStaffRateSchema.getPayYears()+"' or l.PayYears is null)";
    			 sql+=" and  l.InsureAgeStart is null ";
    			 sql2+=" and l.InsureAgeStart is null ";
    			 sql+=" and  l.InsureAgeEnd is null ";
    			 sql2+=" and l.InsureAgeEnd is null ";
    			 sql+=" and  l.PayToAge is null ";
    			 sql2+=" and l.PayToAge is null ";
    		
    		 }
    		 if(mPD_LDStaffRateSchema.getInsureAgeStart()!=null&&!mPD_LDStaffRateSchema.getInsureAgeStart().equals("")){
    			 sql+=" and  l.PayYears is null ";
    			 sql2+=" and l.PayYears is null ";
    			 sql+=" and (l.InsureAgeStart = '"+mPD_LDStaffRateSchema.getInsureAgeStart()+"' or l.InsureAgeStart is null)";
    			 sql2+=" and (l.InsureAgeStart = '"+mPD_LDStaffRateSchema.getInsureAgeStart()+"' or l.InsureAgeStart is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getInsureAgeEnd()!=null&&!mPD_LDStaffRateSchema.getInsureAgeEnd().equals("")){
    			 sql+=" and (l.InsureAgeEnd = '"+mPD_LDStaffRateSchema.getInsureAgeEnd()+"' or l.InsureAgeEnd is null)";
    			 sql2+=" and (l.InsureAgeEnd = '"+mPD_LDStaffRateSchema.getInsureAgeEnd()+"' or l.InsureAgeEnd is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getPayToAge()!=null&&!mPD_LDStaffRateSchema.getPayToAge().equals("")){
    			 sql+=" and  l.PayYears is null ";
    			 sql2+=" and l.PayYears is null ";
    			 sql+=" and (l.PayToAge = '"+mPD_LDStaffRateSchema.getPayToAge()+"' or l.PayToAge is null)";
    			 sql2+=" and (l.PayToAge = '"+mPD_LDStaffRateSchema.getPayToAge()+"' or l.PayToAge is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getCurrency()!=null&&!mPD_LDStaffRateSchema.getCurrency().equals("")){
    			 sql+=" and (l.Currency = '"+mPD_LDStaffRateSchema.getCurrency()+"' or l.Currency is null)";
    			 sql2+=" and (l.Currency = '"+mPD_LDStaffRateSchema.getCurrency()+"' or l.Currency is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getInvestType()!=null&&!mPD_LDStaffRateSchema.getInvestType().equals("")){
    			 sql+=" and (l.InvestType = '"+mPD_LDStaffRateSchema.getInvestType()+"' or l.InvestType is null)";
    			 sql2+=" and (l.InvestType = '"+mPD_LDStaffRateSchema.getInvestType()+"' or l.InvestType is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getPayType()!=null&&!mPD_LDStaffRateSchema.getPayType().equals("")){
    			 sql+=" and (l.PayType = '"+mPD_LDStaffRateSchema.getPayType()+"' or l.PayType is null)";
    			 sql2+=" and (l.PayType = '"+mPD_LDStaffRateSchema.getPayType()+"' or l.PayType is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getProtectionPlan()!=null&&!mPD_LDStaffRateSchema.getProtectionPlan().equals("")){
    			 sql+=" and (l.ProtectionPlan = '"+mPD_LDStaffRateSchema.getProtectionPlan()+"' or l.ProtectionPlan is null)";
    			 sql2+=" and (l.ProtectionPlan = '"+mPD_LDStaffRateSchema.getProtectionPlan()+"' or l.ProtectionPlan is null)";
    		 }

            SSRS aSSRS = new SSRS();
			aSSRS = tExeSQL.execSQL(sql);// 查询是否有相同记录，得到aSSRS二维数组
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sql2);// 查询是否有类似记录，得到tSSRS二维数组
			int acount = aSSRS.getMaxRow();
			int tcount = tSSRS.getMaxRow();
			//判断是否有相同记录
			if(acount==0){
				//判断是否有类似记录
				if(tcount!=0){
					//定义一个HashSet存放从数据库中查找出来的起始日期
					HashSet dataSet = new HashSet();
					for(int j=1;j<=tcount;j++){
						dataSet.add(tSSRS.GetText(j, 1));
					}
					String startdate[] = new String[dataSet.size()+1];//定义一个tcount+1长的字符串数组
					// 把dataSet中的起始时间放到startdate字符串数组中
					Iterator<String> it = dataSet.iterator();
					for (int j = 0; j < dataSet.size(); j++) {				
						if(it.hasNext()){
							startdate[j] =it.next();
						}
					}
					
					/*for(int i=1;i<=tcount;i++){
						startdate[i-1]=tSSRS.GetText(i, 1);//把查询到相似记录的起始日期放到定义好的字符串数组中
					}*/
					startdate[tcount] = mPD_LDStaffRateSchema.getStartDate();//把新增记录中的起始日期也放到startdate字符串数组中
					Sort(startdate);//把起始日期从小到大进行排序
					dealDataSourceEndDate(startdate,tcount,tExeSQL,tSSRS);//处理数据库中的截止日期
	    			dealEndDate(startdate,mPD_LDStaffRateSchema);//处理PD_LDStaffRateSchema中的截止日期
				}
				
				/**
				 * ----------------------------------
				 * 1.3 新增
				 * ----------------------------------
				 * */
				if(mPD_LDStaffRateSchema.getAgentCom()==null||mPD_LDStaffRateSchema.getAgentCom().equals("")){
					mPD_LDStaffRateSchema.setAgentCom("000000");
				}
				//String aIDNo = PubFun.CreateSeq("SEQ_LACOMMISSIONRATE", 10);// 生成流水号
				String aIDNo = "PE"+PubFun1.CreateMaxNo("PE_LACOMMISSIONRATE", 8);
				mPD_LDStaffRateSchema.setIDNo(aIDNo);
				tPD_LDStaffRateSet.add(mPD_LDStaffRateSchema);
			 
			}else{
				CError tError = new CError();
				tError.moduleName = "LABKCommRateAuditBL";
				tError.functionName = "dealData";
				tError.errorMessage = "该条信息已存在！";
				this.mErrors.addOneError(tError);
				return false;	
			}
			map.put(tPD_LDStaffRateSet,"INSERT");
	     }
	     
	     if(mOperate.equals("UPDATE||MAIN")){
	    	 
	    	 PD_LDStaffRateSet tPD_LDStaffRateSet = new PD_LDStaffRateSet();
	    	 mPD_LDStaffRateSchema = mPD_LDStaffRateSet.get(1);
	    	 ExeSQL tExeSQL = new ExeSQL();
    		 SSRS aSSRS = new SSRS();
    		 SSRS tSSRS = new SSRS();
    		 SSRS mSSRS = new SSRS();
    		 SSRS nSSRS = new SSRS();
    		 
    		 
	    	 /**
	    	  * -------------------------------
	    	  * 2.1查询销售渠道，若填写了代理机构，
	    	  * 	按照代理机构的销售渠道，否则按照
	    	  * 	管理机构前四位判断销售渠道
	    	  * -------------------------------
	    	  * */
//			String tBranchType = "";
//			if(mPD_LDStaffRateSchema.getAgentCom()!=null&&!mPD_LDStaffRateSchema.getAgentCom().equals("")){
//				SSRS pSSRS = new SSRS();
//				String sql3 = "select BranchType from LACom where AgentCom = '"+mPD_LDStaffRateSchema.getAgentCom()+"'";
//				pSSRS = tExeSQL.execSQL(sql3);
//				tBranchType = pSSRS.GetText(1, 1);
//			}else{
//				if(mPD_LDStaffRateSchema.getManageCom().startsWith("HK01")){
//					tBranchType="03";
//				}else if(mPD_LDStaffRateSchema.getManageCom().startsWith("HK02")){
//					tBranchType="08";
//		    	}else if(mPD_LDStaffRateSchema.getManageCom().startsWith("HK03")){
//					tBranchType="04";
//				}else if(mPD_LDStaffRateSchema.getManageCom().startsWith("HK04")){
//					tBranchType="11";
//				}
//			}
    		 SSRS exBranchTypeSSRS = new SSRS();
 			String exBranchType = "select BranchType from LACom where ManageCom = '"+mPD_LDStaffRateSchema.getManageCom()+"'";
 			exBranchTypeSSRS = tExeSQL.execSQL(exBranchType);
 			mPD_LDStaffRateSchema.setBranchtype(exBranchTypeSSRS.GetText(1, 1));
    		 

    		 
    		 /**
    		  * ------------------------------------------
    		  * 2.2 删除的不能修改
    		  * ------------------------------------------
    		  * */
    		 String Sql3 = "select OrigIDNo,ActionType from PD_LDStaffRate where IDNo = '"+mPD_LDStaffRateSchema.getIDNo()+"' ";
     		
    		 mSSRS  = tExeSQL.execSQL(Sql3);//查询该记录是否有原记录和其操作类型是删除、新增还是修改
    		 System.out.println(mSSRS.GetText(1, 2));
    		 
    		 if(mSSRS.GetText(1, 2).equals("03")){
    			 CError tError = new CError();
 				tError.moduleName = "LABKCommRateAuditBL";
 				tError.functionName = "dealData";
 				tError.errorMessage = "该条信息操作是删除操作，不能进行修改，只能审核！";
 				this.mErrors.addOneError(tError);
 				return false;	
    		 }
    		 
    		 /**
    		  * ------------------------------------------
    		  * 2.3 删除待审核的不能修改
    		  * ------------------------------------------
    		  * */
    		 String Sql4 = "select IDNo,ActionType from PD_LDStaffRate where OrigIDNo = '"+mPD_LDStaffRateSchema.getIDNo()+"'";
    		 nSSRS  = tExeSQL.execSQL(Sql4);//查询该记录是否已经修改或删除，正在审核中
    		 if(nSSRS.getMaxRow()!=0){
    			 if(nSSRS.GetText(1, 2).equals("03")){
    				// @@错误处理
    				 CError tError = new CError();
    	 			 tError.moduleName = "LABKCommRateAuditBL";
    	 			 tError.functionName = "dealData";
    	 			 tError.errorMessage = "该条信息已删除，待审核中,无法对其修改！";
    	 			 this.mErrors.addOneError(tError);
    	 			 return false;
    			 }
    		 }
    		 
    		 
    		 /**
    		  * ------------------------------------------
    		  * 2.4 查询相同和相似的纪录
    		  * ------------------------------------------
    		  * */
    		 String sql = "select l.StartDate,l.IDNo from PD_LDStaffRate l where 1=1"
				 +" and l.ManageCom = '"+mPD_LDStaffRateSchema.getManageCom()+"'"
//			 	 +" and l.AgentCom = '"+mPD_LDStaffRateSchema.getAgentCom()+"' "
			 	 +" and l.RiskCode = '"+mPD_LDStaffRateSchema.getRiskCode()+"'"
			 	 +" and l.PayYear = '"+mPD_LDStaffRateSchema.getPayYear()+"'"
			 	 +" and l.StartDate = '"+mPD_LDStaffRateSchema.getStartDate()+"'"
			 	 +" and l.Branchtype = '"+mPD_LDStaffRateSchema.getBranchtype()+"'"
			 	 +" and l.ValidFlag = '01'"
			 	 +" and l.IDNo <> '"+mPD_LDStaffRateSchema.getIDNo()+"' "
			 	 +" and l.IDNo <> '"+nSSRS.GetText(1, 1)+"' ";
    		 
    		 String sql2 = "select l.StartDate,l.IDNo from PD_LDStaffRate l where 1=1"
				 +" and l.ManageCom = '"+mPD_LDStaffRateSchema.getManageCom()+"'"
			 	 +" and l.AgentCom = '"+mPD_LDStaffRateSchema.getAgentCom()+"' "
			 	 +" and l.RiskCode = '"+mPD_LDStaffRateSchema.getRiskCode()+"'"
			 	 +" and l.PayYear = '"+mPD_LDStaffRateSchema.getPayYear()+"'"
			 	 +" and l.Branchtype = '"+mPD_LDStaffRateSchema.getBranchtype()+"'"
			 	 +" and l.ValidFlag = '01'"
			 	 +" and l.AppState = '02'"
			 	 +" and l.IDNo <> '"+mPD_LDStaffRateSchema.getIDNo()+"' ";
    		 
    		 if(mPD_LDStaffRateSchema.getAgentCom()!=null&&!mPD_LDStaffRateSchema.getAgentCom().equals("")){
    			 sql+= " and (l.AgentCom = '"+mPD_LDStaffRateSchema.getAgentCom()+"'  or l.AgentCom ='000000')";
    			 sql2+= " and (l.AgentCom = '"+mPD_LDStaffRateSchema.getAgentCom()+"'  or l.AgentCom ='000000')";
    		 }
    		 
    		 if(mPD_LDStaffRateSchema.getPayYears()!=null&&!mPD_LDStaffRateSchema.getPayYears().equals("")){
    			 sql+=" and (l.PayYears = '"+mPD_LDStaffRateSchema.getPayYears()+"' or l.PayYears is null)";
    			 sql2+=" and (l.PayYears = '"+mPD_LDStaffRateSchema.getPayYears()+"' or l.PayYears is null)";
    			 sql+=" and  l.InsureAgeStart is null ";
    			 sql2+=" and l.InsureAgeStart is null ";
    			 sql+=" and  l.InsureAgeEnd is null ";
    			 sql2+=" and l.InsureAgeEnd is null ";
    			 sql+=" and  l.PayToAge is null ";
    			 sql2+=" and l.PayToAge is null ";
    		 }
    		 if(mPD_LDStaffRateSchema.getInsureAgeStart()!=null&&!mPD_LDStaffRateSchema.getInsureAgeStart().equals("")){
    			 sql+=" and  l.PayYears is null ";
    			 sql2+=" and l.PayYears is null ";
    			 sql+=" and (l.InsureAgeStart = '"+mPD_LDStaffRateSchema.getInsureAgeStart()+"' or l.InsureAgeStart is null)";
    			 sql2+=" and (l.InsureAgeStart = '"+mPD_LDStaffRateSchema.getInsureAgeStart()+"' or l.InsureAgeStart is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getInsureAgeEnd()!=null&&!mPD_LDStaffRateSchema.getInsureAgeEnd().equals("")){
    			 sql+=" and (l.InsureAgeEnd = '"+mPD_LDStaffRateSchema.getInsureAgeEnd()+"' or l.InsureAgeEnd is null)";
    			 sql2+=" and (l.InsureAgeEnd = '"+mPD_LDStaffRateSchema.getInsureAgeEnd()+"' or l.InsureAgeEnd is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getPayToAge()!=null&&!mPD_LDStaffRateSchema.getPayToAge().equals("")){
    			 sql+=" and  l.PayYears is null ";
    			 sql2+=" and l.PayYears is null ";
    			 sql+=" and (l.PayToAge = '"+mPD_LDStaffRateSchema.getPayToAge()+"' or l.PayToAge is null)";
    			 sql2+=" and (l.PayToAge = '"+mPD_LDStaffRateSchema.getPayToAge()+"' or l.PayToAge is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getCurrency()!=null&&!mPD_LDStaffRateSchema.getCurrency().equals("")){
    			 sql+=" and (l.Currency = '"+mPD_LDStaffRateSchema.getCurrency()+"' or l.Currency is null)";
    			 sql2+=" and (l.Currency = '"+mPD_LDStaffRateSchema.getCurrency()+"' or l.Currency is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getInvestType()!=null&&!mPD_LDStaffRateSchema.getInvestType().equals("")){
    			 sql+=" and (l.InvestType = '"+mPD_LDStaffRateSchema.getInvestType()+"' or l.InvestType is null)";
    			 sql2+=" and (l.InvestType = '"+mPD_LDStaffRateSchema.getInvestType()+"' or l.InvestType is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getPayType()!=null&&!mPD_LDStaffRateSchema.getPayType().equals("")){
    			 sql+=" and (l.PayType = '"+mPD_LDStaffRateSchema.getPayType()+"' or l.PayType is null)";
    			 sql2+=" and (l.PayType = '"+mPD_LDStaffRateSchema.getPayType()+"' or l.PayType is null)";
    		 }
    		 if(mPD_LDStaffRateSchema.getProtectionPlan()!=null&&!mPD_LDStaffRateSchema.getProtectionPlan().equals("")){
    			 sql+=" and (l.ProtectionPlan = '"+mPD_LDStaffRateSchema.getProtectionPlan()+"' or l.ProtectionPlan is null)";
    			 sql2+=" and (l.ProtectionPlan = '"+mPD_LDStaffRateSchema.getProtectionPlan()+"' or l.ProtectionPlan is null)";
    		 }
    		 
    	
    		
    		 
    		 aSSRS = tExeSQL.execSQL(sql);// 查询相同记录得到aSSRS二维数组	     		 
    		 int acount = aSSRS.getMaxRow();
              
    		 //判断是否有相同记录
    		 if(acount==0){   
    			 //判断是否有原记录，若有则把原记录设为无效
    			 if(!mSSRS.GetText(1, 1).equals("")){
    				 String sql4 = "update PD_LDStaffRate set  ValidFlag = '02',"
    					 +" ModifyDate = '"+currentdate+"'," 
    					 +" ModifyTime = '"+currenttime+"' where 1=1"
    					 +" and IDNo = '"+mSSRS.GetText(1, 1)+"'";
    				 tExeSQL.execUpdateSQL(sql4);
    			 }
    			 //判断是否有未审核的修改记录，若有则把未审核的修改记录设为无效
    			 if(!nSSRS.GetText(1, 1).equals("")){
    				 String sql4 = "update PD_LDStaffRate set  ValidFlag = '02',"
    					 +" ModifyDate = '"+currentdate+"'," 
    					 +" ModifyTime = '"+currenttime+"' where 1=1"
    					 +" and IDNo = '"+nSSRS.GetText(1, 1)+"'";
    				 tExeSQL.execUpdateSQL(sql4);
    			 }
    			 tSSRS = tExeSQL.execSQL(sql2);// 查询类似记录得到tSSRS二维数组	     		 
        		 int tcount = tSSRS.getMaxRow();
        		 
        		 //判断数据库中是否有类似记录
    			 if(tcount!=0){
    				//定义一个HashSet存放从数据库中查找出来的起始日期
						HashSet dataSet = new HashSet();
						for(int j=1;j<=tcount;j++){
							dataSet.add(tSSRS.GetText(j, 1));
						}
						String startdate[] = new String[dataSet.size()+1];//定义一个tcount+1长的字符串数组
						// 把dataSet中的起始时间放到startdate字符串数组中
						Iterator<String> it = dataSet.iterator();
						for (int j = 0; j < dataSet.size(); j++) {				
							if(it.hasNext()){
								startdate[j] =it.next();
							}
						}
						  				 
    				/* String startdate[] = new String[tcount+1];//定义一个字符串数组
    				 for(int i=1;i<=tcount;i++){
    					 startdate[i-1] = tSSRS.GetText(i, 1);//把查询到的相似记录中的起始日期放到startdate字符串数组中
    				 }*/
    				 startdate[tcount] =  mPD_LDStaffRateSchema.getStartDate();//把修改后的记录中的起始日期也放到startdate字符串数组中
    				 Sort(startdate);//把起始日期从小到大进行排序
    				 dealDataSourceEndDate(startdate,tcount,tExeSQL,tSSRS);//处理数据库中的截止日期
	    			 dealEndDate(startdate,mPD_LDStaffRateSchema);//处理PD_LDStaffRateSchema中的截止日期
    				   	System.out.println("tcount:+++++======"+tcount);		
    			 } 
    			 
    			/**
        		  * ------------------------------------------
        		  * 2.5 修改
        		  * ------------------------------------------
        		  * */
    			 
    			if(mPD_LDStaffRateSchema.getAgentCom()==null||mPD_LDStaffRateSchema.getAgentCom().equals(""))
    			{
    				mPD_LDStaffRateSchema.setAgentCom("000000");
    			}
    			mPD_LDStaffRateSchema.setDefaultFlag("1");
    			mPD_LDStaffRateSchema.setValidFlag("01");
 				mPD_LDStaffRateSchema.setActionType("");
 				mPD_LDStaffRateSchema.setAppState("02");
 				
 				PD_LDStaffRateSchema tPD_LDStaffRateSchema = new PD_LDStaffRateSchema();
 				PD_LDStaffRateDB tPD_LDStaffRateDB = new PD_LDStaffRateDB();
 				
 				tPD_LDStaffRateDB.setIDNo(mPD_LDStaffRateSchema.getIDNo());
 				tPD_LDStaffRateDB.getInfo();
 				tPD_LDStaffRateSchema = tPD_LDStaffRateDB.getSchema();
 				
 				
 				tPD_LDStaffRateSchema.setBranchtype(mPD_LDStaffRateSchema.getBranchtype());
 				tPD_LDStaffRateSchema.setManageCom(mPD_LDStaffRateSchema.getManageCom());
	        	tPD_LDStaffRateSchema.setRiskCode(mPD_LDStaffRateSchema.getRiskCode());
	        	tPD_LDStaffRateSchema.setPayYears(mPD_LDStaffRateSchema.getPayYears());
	        	tPD_LDStaffRateSchema.setInsureAgeStart(mPD_LDStaffRateSchema.getInsureAgeStart());
	        	tPD_LDStaffRateSchema.setInsureAgeEnd(mPD_LDStaffRateSchema.getInsureAgeEnd());
	        	tPD_LDStaffRateSchema.setPayToAge(mPD_LDStaffRateSchema.getPayToAge());
	        	tPD_LDStaffRateSchema.setCurrency(mPD_LDStaffRateSchema.getCurrency());
	        	tPD_LDStaffRateSchema.setPayType(mPD_LDStaffRateSchema.getPayType());
	        	tPD_LDStaffRateSchema.setInvestType(mPD_LDStaffRateSchema.getInvestType());
	        	tPD_LDStaffRateSchema.setProtectionPlan(mPD_LDStaffRateSchema.getProtectionPlan());
	        	tPD_LDStaffRateSchema.setPayYear(mPD_LDStaffRateSchema.getPayYear());
	        	tPD_LDStaffRateSchema.setCommRate("0");
	        	tPD_LDStaffRateSchema.setStartDate(mPD_LDStaffRateSchema.getStartDate());
 				tPD_LDStaffRateSchema.setEndDate(mPD_LDStaffRateSchema.getEndDate());
 				tPD_LDStaffRateSchema.setAgentCom("000000");
 				tPD_LDStaffRateSchema.setOperator(mPD_LDStaffRateSchema.getOperator());
 				tPD_LDStaffRateSchema.setDefaultFlag("1");
 				//tPD_LDStaffRateSchema.setSRFlag(mPD_LDStaffRateSchema.getCommRate()==tPD_LDStaffRateSchema.getStaffRate()?"Y":"N");
 				tPD_LDStaffRateSchema.setModifyDate(currentdate);
				tPD_LDStaffRateSchema.setModifyTime(currenttime);
				tPD_LDStaffRateSchema.setStaffRate(mPD_LDStaffRateSchema.getStaffRate());
 				tPD_LDStaffRateSchema.setAppDate(currentdate);
 				tPD_LDStaffRateSchema.setAppTime(currenttime);
    			tPD_LDStaffRateSchema.setValidFlag("01");
 				tPD_LDStaffRateSchema.setActionType("");
 				tPD_LDStaffRateSchema.setAppState("02");
 				tPD_LDStaffRateSchema.setSRFlag("N");
 				
 				tPD_LDStaffRateSet.add(tPD_LDStaffRateSchema);
    			 
    		 }else{
    			CError tError = new CError();
 				tError.moduleName = "LABKCommRateAuditBL";
 				tError.functionName = "dealData";
 				tError.errorMessage = "该条信息已存在,无需修改！";
 				this.mErrors.addOneError(tError);
 				return false;
    		 }
    		 map.put(tPD_LDStaffRateSet,"UPDATE");
	     }
	     if(mOperate.equals("DELETE||MAIN")){
	    	 
	    	 ExeSQL tExeSQL = new ExeSQL();
    		 SSRS aSSRS = new SSRS();
    		 String sql = "select l.ManageCom,l.AgentCom, l.RiskCode,l.PayYears,l.InsureAgeStart,l.InsureAgeEnd,l.PayToAge,l.Currency,l.InvestType,l.PayType,l.ProtectionPlan,l.PayYear,l.CommRate,l.StartDate,l.EndDate,l.AppState,l.AppDate,l.AppTime,l.Branchtype,l.MakeDate,l.MakeTime,l.StaffRate from PD_LDStaffRate l where 1=1"
				 +" and l.IDNo= '"+mPD_LDStaffRateSchema.getIDNo()+"'";	
    		 aSSRS = tExeSQL.execSQL(sql);// 查询要删除的信息，放在aSSRS二维数组
    		 PD_LDStaffRateSchema tPD_LDStaffRateSchema = new PD_LDStaffRateSchema();   
    		 PD_LDStaffRateDB tPD_LDStaffRateDB=new PD_LDStaffRateDB();
    		 tPD_LDStaffRateDB.setIDNo(mPD_LDStaffRateSchema.getIDNo());
    		 if(!tPD_LDStaffRateDB.getInfo()){
				 CError tError = new CError();
					tError.moduleName = "PD_RiskStaffRateBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该条记录不存在！";
					this.mErrors.addOneError(tError);
					return false;
    		 }else{
    			 return tPD_LDStaffRateDB.delete();
    		 }
	     }
		return true;
	}
	//把mPD_LDStaffRateSet中的记录按类似的记录分开，相同记录放在一起
	public void partSet(List added,List list2,List listSet){
		for(int i=0;i<listSet.size();i++){
 			  PD_LDStaffRateSchema LACRS1= new  PD_LDStaffRateSchema();
		    	 List  list1 = new ArrayList();	
		    	 LACRS1 =  (PD_LDStaffRateSchema) listSet.get(i);
		    	 int flag=1;
		    	 for (int k=0;k<added.size();k++){
		    		 if(i==(Integer)added.get(k)){
		    			 flag=0;
		    			 break;
		    		 }else{
		    			 flag=1;
		    			 
		    		 }
		    		 
		    	 }
		    	 list1.add(LACRS1);
		    		if(flag==1){
			    		  for(int j=i+1;j<listSet.size();j++){	
			    			  PD_LDStaffRateSchema LACRS2= new  PD_LDStaffRateSchema();
			    			  LACRS2 = (PD_LDStaffRateSchema) listSet.get(j);    			 
			    			  //if(compareTo(LACRS1.getManageCom(),LACRS2.getManageCom())&&compareTo(LACRS1.getAgentCom(),LACRS2.getAgentCom())&&compareTo(LACRS1.getRiskCode(),LACRS2.getRiskCode())&&compareTo(LACRS1.getPayYears(),LACRS2.getPayYears())&&compareTo(LACRS1.getInsureAgeStart(),LACRS2.getInsureAgeStart())&&compareTo(LACRS1.getInsureAgeEnd(),LACRS2.getInsureAgeEnd())&&compareTo(LACRS1.getPayToAge(),LACRS2.getPayToAge())&&compareTo(LACRS1.getCurrency(),LACRS2.getCurrency())&&compareTo(LACRS1.getInvestType(),LACRS2.getInvestType())&&compareTo(LACRS1.getPayType(),LACRS2.getPayType())&&compareTo(LACRS1.getProtectionPlan(),LACRS2.getProtectionPlan())&&LACRS1.getPayYear()==LACRS2.getPayYear())
			    			  System.out.println("StartCompareTo");
			    			  if(compareTo(LACRS1,LACRS2))
			    			  {	    				  
			    				  list1.add(LACRS2);
			    				  added.add(j);
			    				  //mPD_LDStaffRateSet.remove(LACRS2);
			    			  }
			    		  }
			    		  list2.add(list1) ;
		    		}		      	    
	    		  
	    	 }
	}
	
	
	
	//处理数据库中的截止日期
	public void dealDataSourceEndDate(String startdate[],int tcount,ExeSQL tExeSQL,SSRS aSSRS){
		if(startdate.length==1&&startdate[0].equals(aSSRS.GetText(1, 1))){
			for(int i=1;i<=tcount;i++){
				String sql5 = "update PD_LDStaffRate set EndDate = '' where trim(IDNo) =trim('"+aSSRS.GetText(1, 2)+"')";
				tExeSQL.execUpdateSQL(sql5);	
			}
		}			
		for(int p=0;p<startdate.length-1;p++){
				String date1 = startdate[p];
				String date2 = startdate[p + 1];
				String date4 = PubFun.calDate(date2, -1, "D", null); // 获取一个日期的前一天
					int k=0;
					for(int n=1;n<=tcount;n++){
						if(aSSRS.GetText(n, 1).equals(date1)){
							k=n;
							break;
						}
					}
					if(k!=0){
						String sql5 = "update PD_LDStaffRate set EndDate = '"+date4+"' where trim(IDNo) =trim('"+aSSRS.GetText(k, 2)+"') ";
						tExeSQL.execUpdateSQL(sql5);
						k=0;
					}
				
				//当起始日期是最大，并且该起始日期是从数据库中查询到的起始日期，该条记录的截止日期设为空值
				int m=0;
				if(date2.equals(startdate[startdate.length-1])){
					for(int n=1;n<=tcount;n++){
						if(aSSRS.GetText(n, 1).equals(date2)){
							m=n;
							break;
						}					
						if(m>0){
							String sql6= "update PD_LDStaffRate set EndDate = '' where trim(IDNo) =trim('"+aSSRS.GetText(m, 2)+"')";						
							tExeSQL.execUpdateSQL(sql6);
							m=0;
							
						}
					}
					
				}
		 }
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

	//判断两个字符串是否相等
	public boolean compareTo(String s1,String s2){
		if(s1==null&&s2==null){
			return true;
		}else if(s1!=null&&s2!=null){
			return s1.equals(s2);
		}
		return false;
		
	}
	
	public void dealEndDate(String startdate[],PD_LDStaffRateSchema tPD_LDStaffRateSchema){
		for(int p=0;p<startdate.length-1;p++){
				String date1 = startdate[p];
				String date2 = startdate[p + 1];
				String date4 = PubFun.calDate(date2, -1, "D", null); // 获取一个日期的前一天
				if(date1.equals(tPD_LDStaffRateSchema.getStartDate())){
					tPD_LDStaffRateSchema.setEndDate(date4);  
				 } 
				
		}		
	}
	
	//判断两个字符串是否相似
	public boolean compareTo(PD_LDStaffRateSchema Schema1,PD_LDStaffRateSchema Schema2)
	{
		if(!Schema1.getManageCom().equals(Schema2.getManageCom()))
		{
			return false;
		}
		if(!Schema1.getRiskCode().equals(Schema2.getRiskCode()))
		{
			return false;
		}
		if(Schema1.getPayYear()!=Schema2.getPayYear()){
			return false;
		}
		System.out.println("PayYears======="+Schema1.getPayYears()+"===");
		System.out.println("InsureAgeStart====="+Schema1.getInsureAgeStart()+"===");
		if(Schema1.getPayYears()!=null&&Schema2.getInsureAgeStart()!=null&&!"".equals(Schema1.getPayYears())&&!"".equals(Schema2.getInsureAgeStart()))
		{
			return false;
		}
		if(Schema1.getPayYears()!=null&&Schema2.getPayToAge()!=null&&!"".equals(Schema1.getPayYears())&&!"".equals(Schema2.getPayToAge()))
		{
			return false;
		}
		
		if(Schema1.getInsureAgeStart()!=null&&Schema2.getPayYears()!=null&&!"".equals(Schema1.getInsureAgeStart())&&!"".equals(Schema2.getPayYears()))
		{
			return false;
		}
		if(Schema1.getPayToAge()!=null&&Schema2.getPayYears()!=null&&!"".equals(Schema1.getPayToAge())&&!"".equals(Schema2.getPayYears()))
		{
			return false;
		}
		if(Schema1.getAgentCom()!=null&&Schema2.getAgentCom()!=null&&!"".equals(Schema1.getAgentCom())&&!"".equals(Schema2.getAgentCom())&&!Schema1.getAgentCom().equals(Schema2.getAgentCom()))
		{
			return false;
		}
		if(Schema1.getPayYears()!=null&&Schema2.getPayYears()!=null&&!"".equals(Schema1.getPayYears())&&!"".equals(Schema2.getPayYears())&&!Schema1.getPayYears().equals(Schema2.getPayYears()))
		{
			return false;
		}
		
		if(Schema1.getInsureAgeStart()!=null&&Schema2.getInsureAgeStart()!=null&&!"".equals(Schema1.getInsureAgeStart())&&!"".equals(Schema2.getInsureAgeStart())&&!Schema1.getInsureAgeStart().equals(Schema2.getInsureAgeStart()))
		{
			return false;
		}
		
		if(Schema1.getInsureAgeEnd()!=null&&Schema2.getInsureAgeEnd()!=null&&!Schema1.getInsureAgeEnd().equals(Schema2.getInsureAgeEnd()))
		{
			return false;
		}
		
		if(Schema1.getPayToAge()!=null&&Schema2.getPayToAge()!=null&&!Schema1.getPayToAge().equals(Schema2.getPayToAge()))
		{
			return false;
		}
		
		if(Schema1.getCurrency()!=null&&Schema2.getCurrency()!=null&&!Schema1.getCurrency().equals(Schema2.getCurrency()))
		{
			return false;
		}
		
		if(Schema1.getInvestType()!=null&&Schema2.getInvestType()!=null&&!Schema1.getInvestType().equals(Schema2.getInvestType()))
		{
			return false;
		}
		
		if(Schema1.getPayType()!=null&&Schema2.getPayType()!=null&&!Schema1.getPayType().equals(Schema2.getPayType()))
		{
			return false;
		}
		
		if(Schema1.getProtectionPlan()!=null&&Schema2.getProtectionPlan()!=null&&!Schema1.getProtectionPlan().equals(Schema2.getProtectionPlan()))
		{
			return false;
		}
		
		
		
		return true;
		
	}


}

