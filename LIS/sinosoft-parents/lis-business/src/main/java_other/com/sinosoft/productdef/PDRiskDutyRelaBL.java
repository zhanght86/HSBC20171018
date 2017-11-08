



/**
 * <p>Title: PDRiskDutyRelaBL</p>
 * <p>Description: 险种关联责任</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-12
 */

package com.sinosoft.productdef;
import org.apache.log4j.Logger;
import com.sinosoft.lis.db.PD_LMDutyDB;
import com.sinosoft.lis.db.PD_LMDutyGetDB;
import com.sinosoft.lis.db.PD_LMDutyGetRelaDB;
import com.sinosoft.lis.db.PD_LMDutyPayDB;
import com.sinosoft.lis.db.PD_LMDutyPayRelaDB;
import com.sinosoft.lis.db.PD_LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.RiskState;
import com.sinosoft.lis.schema.PD_LMDutyGetRelaSchema;
import com.sinosoft.lis.schema.PD_LMDutyGetSchema;
import com.sinosoft.lis.schema.PD_LMDutyPayRelaSchema;
import com.sinosoft.lis.schema.PD_LMDutyPaySchema;
import com.sinosoft.lis.schema.PD_LMDutySchema;
import com.sinosoft.lis.schema.PD_LMRiskDutySchema;
import com.sinosoft.lis.schema.PD_LMRiskPayIntvSchema;
import com.sinosoft.lis.schema.PD_LMRiskPaySchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDRiskDutyRelaBL implements BusinessService{
    
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private VData tResult = new VData();

	/** 往后面传输数据的容器 */
	// private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput globalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String operator;

	/** 业务处理相关变量 */
	private MMap map = new MMap();

	private TransferData mTransferData = new TransferData();

	private PD_LMDutyPaySchema pd_LMDutyPaySchema;
	
	private PD_LMDutyPayRelaSchema pd_LMDutyPayRelaSchema;

	private PD_LMDutyGetSchema pd_LMDutyGetSchema;
	
	private PD_LMDutyGetRelaSchema pd_LMDutyGetRelaSchema;

	private PD_LMRiskDutySchema pd_LMRiskDutySchema;

	private PD_LMDutySchema pd_LMDutySchema;

	private String payOrGet;

	private ExeSQL exeSQL = new ExeSQL();

	private String whichAlgo;

	private String payCalCode;
	private String payCalCodeBack;
	
	private String riskCode;
	private String mCalCodeType = "";
	private String mCalCodeTypeBack = "";
	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String operator) {
		try {
			this.operator = operator;

			if (!check()) {
				return false;
			}
			if (!getInputData(cInputData)) {
				return false;
			}

			if ("Pay".equals(payOrGet)) {
				if (!dealPayData(operator)) {
					CError tError = new CError();
					tError.moduleName = "$tableName$BL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据处理失败$tableName$BL-->dealData!";
					this.mErrors.addOneError(tError);
					return false;
				}
			} else if("Get".equals(payOrGet)){
				if (!dealGetDate(operator)) {
					CError tError = new CError();
					tError.moduleName = "$tableName$BL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据处理失败$tableName$BL-->dealData!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}else if("Duty".equals(payOrGet)){
				
			}

			/*
			 * // 进行业务处理 if (!synDutyPayGetData()) { return false; } // 准备给后台的数据
			 * if (!prepareOutputData(operator)) { return false; }
			 * 
			 */
			VData data = new VData();
			data.add(this.map);
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(data, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProdDefWorkFlowBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			
			
			//tongmeng 2011-07-19 add
			
			 if("Pay".equals(payOrGet)){
				   RiskState.setState(riskCode, "产品条款定义->缴费责任信息", "1");
			   }
			   else
			   {
				   RiskState.setState(riskCode, "产品条款定义->给付责任信息", "1");
			   }
			 
//			 String tGetDutyKind = mTransferData.getValueByName("getDutyKind")==null?"":(String)mTransferData.getValueByName("getDutyKind");
//			 if(tGetDutyKind.equals("0"))
//			 {
//				 RiskState.setState(riskCode,"产品条款定义->算法定义","责任生存给付");
//			 }
			return true;
		} catch (Exception ex) {
			this.mErrors.addOneError(ex.getMessage());
			return false;
		}
	}

	private boolean dealGetDate(String operator) {
		if ("save".equals(operator)) {
			PD_LMRiskDB pd_LMRiskDB = new PD_LMRiskDB();
			pd_LMRiskDB.setRiskCode(riskCode);
			if(!pd_LMRiskDB.getInfo()){
				this.mErrors.addOneError("请先定义险种");
				return false;
			}
			//生成给付项编码和算法编码
			pd_LMDutyGetSchema.setGetDutyCode(this.createGetDutyCode(pd_LMDutyGetSchema.getDutyCode()));
			//pd_LMDutyGetSchema.setCalCode(this.createCalCode(1));
			
			PD_LMDutyGetDB qPD_LMDutyGetDB = new PD_LMDutyGetDB();
			qPD_LMDutyGetDB.setDutyCode(pd_LMDutyGetSchema.getDutyCode());
			qPD_LMDutyGetDB.setGetDutyCode(pd_LMDutyGetSchema.getGetDutyCode());
//			if (qPD_LMDutyGetDB.getInfo()) {
//				this.mErrors.addOneError("数据库已有给付记录,无法更新");
//				return false;
//			} else {
				PD_LMDutyDB qPD_LMDutyDB = new PD_LMDutyDB();
				qPD_LMDutyDB.setDutyCode(pd_LMDutyGetSchema.getDutyCode());
				if (!qPD_LMDutyDB.getInfo()) {
					this.mErrors.addOneError("数据库无此责任记录,无法增加");
					return false;
				}
				setDefaultValue("pd_lmdutyget", pd_LMDutyGetSchema);
				if(!algoForGet(pd_LMDutyGetSchema))
				{
					return false;
				}
				pd_LMDutyGetRelaSchema.setGetDutyCode(pd_LMDutyGetSchema.getGetDutyCode());
				map.put(pd_LMDutyGetSchema, "INSERT");
				map.put(pd_LMDutyGetRelaSchema, "INSERT");
				return true;
//			}
		} else if ("update".equals(operator)) {
			PD_LMDutyDB qPD_LMDutyDB = new PD_LMDutyDB();
			qPD_LMDutyDB.setDutyCode(pd_LMDutyGetSchema.getDutyCode());
			if (!qPD_LMDutyDB.getInfo()) {
				this.mErrors.addOneError("数据库无责任记录,无法更新");
				return false;
			}
			// dutyget表无记录,不能更新
			PD_LMDutyGetDB qPD_LMDutyGetDB = new PD_LMDutyGetDB();
			qPD_LMDutyGetDB.setDutyCode(pd_LMDutyGetSchema.getDutyCode());
			qPD_LMDutyGetDB.setGetDutyCode(pd_LMDutyGetSchema.getGetDutyCode());
			if (!qPD_LMDutyGetDB.getInfo()) {
				this.mErrors.addOneError("数据库无此给付记录,无法更新");
				return false;
			}
			PD_LMDutyGetSchema qPD_LMDutyGetSchema = qPD_LMDutyGetDB
					.getSchema();
			qPD_LMDutyGetSchema.setGetDutyName(pd_LMDutyGetSchema
					.getGetDutyName());
			qPD_LMDutyGetSchema.setGetDutyKind(pd_LMDutyGetSchema
					.getGetDutyKind());
			qPD_LMDutyGetSchema.setAddAmntFlag(pd_LMDutyGetSchema.getAddAmntFlag());
			qPD_LMDutyGetSchema.setType(pd_LMDutyGetSchema.getType());
			qPD_LMDutyGetSchema.setNeedAcc(pd_LMDutyGetSchema.getNeedAcc());
			qPD_LMDutyGetSchema.setGetYear(pd_LMDutyGetSchema.getGetYear());
			qPD_LMDutyGetSchema.setCanGet(pd_LMDutyGetSchema.getCanGet());
			qPD_LMDutyGetSchema.setNeedCancelAcc(pd_LMDutyGetSchema.getNeedCancelAcc());
			qPD_LMDutyGetSchema.setGetIntv(pd_LMDutyGetSchema.getGetIntv());
			qPD_LMDutyGetSchema.setGetYearFlag(pd_LMDutyGetSchema.getGetYearFlag());
			qPD_LMDutyGetSchema.setStartDateCalRef(pd_LMDutyGetSchema.getStartDateCalRef());
			qPD_LMDutyGetSchema.setStartDateCalMode(pd_LMDutyGetSchema.getStartDateCalMode());
			qPD_LMDutyGetSchema.setGetEndPeriod(pd_LMDutyGetSchema.getGetEndPeriod());
			qPD_LMDutyGetSchema.setGetEndUnit(pd_LMDutyGetSchema.getGetEndUnit());
			qPD_LMDutyGetSchema.setEndDateCalRef(pd_LMDutyGetSchema.getEndDateCalRef());
			
			qPD_LMDutyGetSchema.setZeroFlag(pd_LMDutyGetSchema.getZeroFlag());
			qPD_LMDutyGetSchema.setGetType1(pd_LMDutyGetSchema.getGetType1());
			qPD_LMDutyGetSchema.setUrgeGetFlag(pd_LMDutyGetSchema.getUrgeGetFlag());
			qPD_LMDutyGetSchema.setCalCode(pd_LMDutyGetSchema.getCalCode());
			qPD_LMDutyGetSchema.setEndDateCalMode(pd_LMDutyGetSchema.getEndDateCalMode());
			qPD_LMDutyGetSchema.setPCalCode(pd_LMDutyGetSchema.getPCalCode());
			qPD_LMDutyGetSchema.setRCalAmntFlag(pd_LMDutyGetSchema.getRCalAmntFlag());
			qPD_LMDutyGetSchema.setRCalAmntCode(pd_LMDutyGetSchema.getRCalAmntCode());
			
			//-------- add by jucy
			qPD_LMDutyGetSchema.setGetType3(pd_LMDutyGetSchema.getGetType3());
			//-------- end
			
			if(!algoForGet(qPD_LMDutyGetSchema))
			{
				return false;
			}
			
			
			PD_LMDutyGetRelaDB qPD_LMDutyGetRelaDB = new PD_LMDutyGetRelaDB();
			qPD_LMDutyGetRelaDB.setDutyCode(pd_LMDutyGetSchema.getDutyCode());
			qPD_LMDutyGetRelaDB.setGetDutyCode(pd_LMDutyGetSchema.getGetDutyCode());
			PD_LMDutyGetRelaSchema qPD_LMDutyGetRelaSchema = qPD_LMDutyGetRelaDB.getSchema();
			qPD_LMDutyGetRelaSchema.setGetDutyName(pd_LMDutyGetSchema.getGetDutyName());
			
			//-------- add by jucy
			//
			qPD_LMDutyGetSchema.setModifyDate(PubFun.getCurrentDate());
			qPD_LMDutyGetSchema.setModifyTime(PubFun.getCurrentTime());
	        //-------- end
			
			map.put(qPD_LMDutyGetSchema, "UPDATE");
			map.put(qPD_LMDutyGetRelaSchema, "UPDATE");
			/*
			String sql = "update pd_lmdutyget set calcode = '',cntercalcode = '',othcalcode = '' where dutycode = '"
				+ qPD_LMDutyGetSchema.getDutyCode()
				+ "' and getdutycode='"
				+ qPD_LMDutyGetSchema.getGetDutyCode()+ "';";
			map.put(sql, "UPDATE");
			*/

		} else if ("del".equals(operator)) {
			String getDutyCode = pd_LMDutyGetSchema.getGetDutyCode();
			String lmdutygetalivesql = "delete from pd_lmdutygetalive Where getdutycode='"+getDutyCode+"'";
			map.put(lmdutygetalivesql, "DELETE");
			map.put(pd_LMDutyGetSchema, "DELETE");
			map.put(pd_LMDutyGetRelaSchema, "DELETE");
		}
		return true;
	}

	private boolean dealPayData(String operator) {
		if ("save".equals(operator)) {
			
			PD_LMRiskDB pd_LMRiskDB = new PD_LMRiskDB();
			pd_LMRiskDB.setRiskCode(riskCode);
			if(!pd_LMRiskDB.getInfo()){
				this.mErrors.addOneError("请先定义险种");
				return false;
			}
			
			// duty表有记录,不能增加
//			PD_LMDutyDB qPD_LMDutyDB = new PD_LMDutyDB();
//			qPD_LMDutyDB.setDutyCode(pd_LMDutyPaySchema.getDutyCode());
//			if (qPD_LMDutyDB.getInfo()) {
//				this.mErrors.addOneError("数据库已有此责任记录,不能增加");
//				// 有duty情况下进一步判断有无dutypay,有就返回false
//				PD_LMDutyPayDB qPD_LMDutyPayDB = new PD_LMDutyPayDB();
//				qPD_LMDutyPayDB.setDutyCode(pd_LMDutyPaySchema.getDutyCode());
//				qPD_LMDutyPayDB.setPayPlanCode(pd_LMDutyPaySchema
//						.getPayPlanCode());
//				if (qPD_LMDutyPayDB.getInfo()) {
//					this.mErrors.addOneError("数据库已有责任和责任缴费记录,无法更新");
//					return false;
//				} else {
//					setDefaultValue("pd_lmdutypay", pd_LMDutyPaySchema);
//					algoForPay(pd_LMDutyPaySchema);
//					map.put(pd_LMDutyPaySchema, "INSERT");
//					return true;
//				}
//			}
			//生成缴费项编码和算法编码
			String tPayPlanCode = this.createPayPlanCode(pd_LMDutyPaySchema.getDutyCode());
			pd_LMDutyPaySchema.setPayPlanCode(tPayPlanCode);
			//pd_LMDutyPaySchema.setCalCode(this.createCalCode(0));

			setDefaultValue("pd_lmdutypay", pd_LMDutyPaySchema);
			if(!algoForPay(pd_LMDutyPaySchema))
			{
				return false;
			}
			
			pd_LMDutyPayRelaSchema.setPayPlanCode(tPayPlanCode);

			map.put(pd_LMDutyPaySchema, "INSERT");
			map.put(pd_LMDutyPayRelaSchema, "INSERT");
			
			PD_LMRiskPayIntvSchema tPD_LMRiskPayIntvSchema=new PD_LMRiskPayIntvSchema();
			tPD_LMRiskPayIntvSchema.setRiskCode(riskCode);
			tPD_LMRiskPayIntvSchema.setPayIntv("0");
			setDefaultValue("PD_LMRiskPayIntv", tPD_LMRiskPayIntvSchema);
			map.put(tPD_LMRiskPayIntvSchema, "DELETE&INSERT");
			PD_LMRiskPaySchema tPD_LMRiskPaySchema=new PD_LMRiskPaySchema();
			tPD_LMRiskPaySchema.setRiskCode(riskCode);
			PD_LMRiskDB tPD_LMRiskDB=new PD_LMRiskDB();
			tPD_LMRiskDB.setRiskCode(riskCode);
			if(tPD_LMRiskDB.getInfo()){
				tPD_LMRiskPaySchema.setRiskName(tPD_LMRiskDB.getRiskName());
				setDefaultValue("PD_LMRiskPay", tPD_LMRiskPaySchema);
				//map.put(tPD_LMRiskPaySchema, "DELETE&INSERT");//PD_LMRiskPay不在此处处理
			}
			
			
		} else if ("update".equals(operator)) {
			// duty表无记录,不能更新
			PD_LMDutyDB qPD_LMDutyDB = new PD_LMDutyDB();
			qPD_LMDutyDB.setDutyCode(pd_LMDutyPaySchema.getDutyCode());
			if (!qPD_LMDutyDB.getInfo()) {
				this.mErrors.addOneError("数据库无对应责任记录,无法更新");
				return false;
			}

			// dutypay表无记录,不能更新
			PD_LMDutyPayDB qPD_LMDutyPayDB = new PD_LMDutyPayDB();
			qPD_LMDutyPayDB.setDutyCode(pd_LMDutyPaySchema.getDutyCode());
			qPD_LMDutyPayDB.setPayPlanCode(pd_LMDutyPaySchema.getPayPlanCode());
			if (!qPD_LMDutyPayDB.getInfo()) {
				this.mErrors.addOneError("数据库无此缴费责任记录,无法更新");
				return false;
			}


			PD_LMDutyPaySchema qPD_LMDutyPaySchema = qPD_LMDutyPayDB
					.getSchema();
			qPD_LMDutyPaySchema.setPayPlanName(pd_LMDutyPaySchema
					.getPayPlanName());
			qPD_LMDutyPaySchema.setZeroFlag(pd_LMDutyPaySchema.getZeroFlag());
			qPD_LMDutyPaySchema.setPayStartYear(pd_LMDutyPaySchema.getPayStartYear());
			qPD_LMDutyPaySchema.setPayStartYearFlag(pd_LMDutyPaySchema.getPayStartYearFlag());
			qPD_LMDutyPaySchema.setPayStartDateCalRef(pd_LMDutyPaySchema.getPayStartDateCalRef());
			qPD_LMDutyPaySchema.setPayStartDateCalMode(pd_LMDutyPaySchema.getPayStartDateCalMode());
			qPD_LMDutyPaySchema.setNeedAcc(pd_LMDutyPaySchema.getNeedAcc());
			qPD_LMDutyPaySchema.setPayEndYear(pd_LMDutyPaySchema.getPayEndYear());
			qPD_LMDutyPaySchema.setPayEndYearFlag(pd_LMDutyPaySchema.getPayEndYearFlag());
			qPD_LMDutyPaySchema.setPayEndDateCalMode(pd_LMDutyPaySchema.getPayEndDateCalMode());
			qPD_LMDutyPaySchema.setPayIntv(pd_LMDutyPaySchema.getPayIntv());
			qPD_LMDutyPaySchema.setCalCode(pd_LMDutyPaySchema.getCalCode());
			qPD_LMDutyPaySchema.setUrgePayFlag(pd_LMDutyPaySchema.getUrgePayFlag());
			qPD_LMDutyPaySchema.setPayAimClass(pd_LMDutyPaySchema.getPayAimClass()); 
			qPD_LMDutyPaySchema.setPCalCode(pd_LMDutyPaySchema.getPCalCode());
			qPD_LMDutyPaySchema.setRCalPremCode(pd_LMDutyPaySchema.getRCalPremCode());
			qPD_LMDutyPaySchema.setRCalPremFlag(pd_LMDutyPaySchema.getRCalPremFlag());
			//-------- add by jucy
			qPD_LMDutyPaySchema.setInvestType(pd_LMDutyPaySchema.getInvestType());
			//-------- end
			
			if(!algoForPay(qPD_LMDutyPaySchema))
			{
				 return false;
			}
			
			PD_LMDutyPayRelaDB qPD_LMDutyPayRelaDB = new PD_LMDutyPayRelaDB();
			qPD_LMDutyPayRelaDB.setDutyCode(pd_LMDutyPaySchema.getDutyCode());
			qPD_LMDutyPayRelaDB.setPayPlanCode(pd_LMDutyPaySchema.getPayPlanCode());
			PD_LMDutyPayRelaSchema qPD_LMDutyPayRelaSchema = qPD_LMDutyPayRelaDB.getSchema();
	        qPD_LMDutyPayRelaSchema.setPayPlanName(pd_LMDutyPaySchema.getPayPlanName());
	        //-------- add by jucy

	        qPD_LMDutyPaySchema.setModifyDate(PubFun.getCurrentDate());
	        qPD_LMDutyPaySchema.setModifyTime(PubFun.getCurrentTime());
			
	        //-------- end
			map.put(qPD_LMDutyPaySchema, "UPDATE");
			map.put(qPD_LMDutyPayRelaSchema, "UPDATE");
			
			//选择标记更新
//			PD_LMRiskDutyDB qPD_LMRiskDutyDB = new PD_LMRiskDutyDB();
//			qPD_LMRiskDutyDB.setRiskCode(riskCode);
//			qPD_LMRiskDutyDB.setDutyCode(pd_LMDutyPaySchema.getDutyCode());
//			if(qPD_LMRiskDutyDB.getInfo()){
//				PD_LMRiskDutySchema qPD_LMRiskDutySchema =qPD_LMRiskDutyDB.getSchema();
//				qPD_LMRiskDutySchema.setChoFlag(pd_LMRiskDutySchema.getChoFlag());
//				map.put(qPD_LMRiskDutySchema, "UPDATE");
//			}
		} else if ("del".equals(operator)) {
			map.put(pd_LMDutyPaySchema, "DELETE");
			map.put(pd_LMDutyPayRelaSchema, "DELETE");	
		}
		return true;
	}
	
	private boolean dealDutyData(){
		String sql = "";
		return true;
	}
	
	private void setDefaultValue(String tableName, Schema schema) {
		String sql = "select fieldcode, fieldinitvalue from pd_basefield where tablecode=upper('"
				+ tableName + "') and isdisplay=0 order by displayorder ";
		SSRS ssrs = exeSQL.execSQL(sql);
		int rowCount = ssrs.MaxRow;
		for (int i = 1; i <= rowCount; i++) {
			schema.setV(ssrs.GetText(i, 1), ssrs.GetText(i, 2));
		}
		schema.setV("Operator", this.globalInput.Operator);
		schema.setV("MakeDate", PubFun.getCurrentDate());
		schema.setV("MakeTime", PubFun.getCurrentTime());
		schema.setV("ModifyDate", PubFun.getCurrentDate());
		schema.setV("ModifyTime", PubFun.getCurrentTime());
	}

	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		payOrGet = (String) mTransferData.getValueByName("payOrGet");
		whichAlgo = (String) mTransferData.getValueByName("whichAlgo");
		payCalCode = (String) mTransferData.getValueByName("payCalCode");
		payCalCodeBack = (String) mTransferData.getValueByName("payCalCodeBack");
		riskCode = (String)mTransferData.getValueByName("riskCode");
		this.mCalCodeType = (String)mTransferData.getValueByName("CalCodeType");
		this.mCalCodeTypeBack = (String)mTransferData.getValueByName("CalCodeTypeBack");
		globalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		if ("Pay".equals(payOrGet)) {
			pd_LMDutyPaySchema = (PD_LMDutyPaySchema) cInputData
					.getObjectByObjectName("PD_LMDutyPaySchema", 0);
			pd_LMDutyPayRelaSchema = (PD_LMDutyPayRelaSchema) cInputData
			.getObjectByObjectName("PD_LMDutyPayRelaSchema", 0);
			
			
		} else {
			pd_LMDutyGetSchema = (PD_LMDutyGetSchema) cInputData
					.getObjectByObjectName("PD_LMDutyGetSchema", 0);
			pd_LMDutyGetRelaSchema = (PD_LMDutyGetRelaSchema) cInputData
			.getObjectByObjectName("PD_LMDutyGetRelaSchema", 0);
		}

		return true;
	}

	private boolean check() {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean algoForPay(PD_LMDutyPaySchema schema) {
		payCalCode = schema.getCalCode();
		if(payCalCode==null || "".equals(payCalCode)){
			payCalCode = schema.getCnterCalCode();
		}
		if(payCalCode==null || "".equals(payCalCode)){
			payCalCode = schema.getOthCalCode();
		}
		
		if(payCalCode==null||payCalCode.equals(""))
 		{
			payCalCode = PubFun1.CreateRuleCalCode("PD",mCalCodeType);
 			//mTransferData.removeByName("CALCODE");
 			//mTransferData.setNameAndValue("CALCODE", payCalCode);
 			this.mResult.add(0,payCalCode);
 		}
 		
 		else
 		{
 			//校验算法类型和算法编码的关系
/* 			if((payCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("N"))
 			||!payCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("Y"))
 			{
 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
 				return false;
 			}*/
 			if(payCalCode.toUpperCase().startsWith("RU")&&mCalCodeType.equals("N")){
 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
 				return false;
 			}
 			if(payCalCode.toUpperCase().startsWith("CL")&&mCalCodeType.equals("Y")){
 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
 				return false;
 			}
 			this.mResult.add(0,payCalCode);
 		}
		//增加校验
	if(payCalCodeBack!=null&&!payCalCodeBack.equals(""))
		{
/*			if((payCalCodeBack.substring(0,2).toUpperCase().equals("RU")&&mCalCodeTypeBack.equals("N"))
		 			||!payCalCodeBack.substring(0,2).toUpperCase().equals("RU")&&mCalCodeTypeBack.equals("Y"))
		 			{
		 				CError.buildErr(this, "反算算法编码与算法类型不一致,请删除后重新添加!");
		 				return false;
		 			}*/
		if(payCalCodeBack.toUpperCase().startsWith("RU")&&mCalCodeTypeBack.equals("N")){
				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
				return false;
			}
			if(payCalCodeBack.toUpperCase().startsWith("CL")&&mCalCodeTypeBack.equals("Y")){
				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
				return false;
			}
		}
		
		if ("1".equals(whichAlgo)) {
			schema.setCalCode(payCalCode);
			//schema.setCnterCalCode("");
			schema.setCnterCalCode(this.payCalCodeBack);
			schema.setOthCalCode("");
		} else if ("2".equals(whichAlgo)) {
			//schema.setCalCode("");
			schema.setCalCode(this.payCalCodeBack);
			schema.setCnterCalCode(payCalCode);
			schema.setOthCalCode("");
		} else {
			//schema.setCnterCalCode("");
			schema.setCnterCalCode(this.payCalCodeBack);
			schema.setCalCode("");
			schema.setOthCalCode(payCalCode);
		}
		
		return true;
	}

	private boolean algoForGet(PD_LMDutyGetSchema schema) {
		payCalCode = schema.getCalCode();
		if(payCalCode==null || "".equals(payCalCode)){
			payCalCode = schema.getCnterCalCode();
		}
		if(payCalCode==null || "".equals(payCalCode)){
			payCalCode = schema.getOthCalCode();
		}
		
		if(payCalCode==null||payCalCode.equals(""))
 		{
			payCalCode = PubFun1.CreateRuleCalCode("PD",mCalCodeType);
 			//mTransferData.removeByName("CALCODE");
 			//mTransferData.setNameAndValue("CALCODE", payCalCode);
 			this.mResult.add(0,payCalCode);
 		}else{
 			//校验算法类型和算法编码的关系
/*			if((payCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("N"))
 			||!payCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("Y"))
 			{
 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
 				return false;
 			}*/
 			if(payCalCode.toUpperCase().startsWith("RU")&&mCalCodeType.equals("N")){
 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
 				return false;
 			}
 			if(payCalCode.toUpperCase().startsWith("CL")&&mCalCodeType.equals("Y")){
 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
 				return false;
 			}
 			this.mResult.add(0,payCalCode);
 		}
		
		//增加校验
	if(payCalCodeBack!=null&&!payCalCodeBack.equals(""))
		{
/*			if((payCalCodeBack.substring(0,2).toUpperCase().equals("RU")&&mCalCodeTypeBack.equals("N"))
		 			||!payCalCodeBack.substring(0,2).toUpperCase().equals("RU")&&mCalCodeTypeBack.equals("Y"))
		 			{
		 				CError.buildErr(this, "反算算法编码与算法类型不一致,请删除后重新添加!");
		 				return false;
		 			}*/
			if(payCalCodeBack.toUpperCase().startsWith("RU")&&mCalCodeTypeBack.equals("N")){
 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
 				return false;
 			}
 			if(payCalCodeBack.toUpperCase().startsWith("CL")&&mCalCodeTypeBack.equals("Y")){
 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
 				return false;
 			}
		}
		
		if ("1".equals(whichAlgo)) {
			schema.setCalCode(payCalCode);
			//schema.setCnterCalCode("");
			schema.setCnterCalCode(this.payCalCodeBack);
			schema.setOthCalCode("");
		} else if ("2".equals(whichAlgo)) {
			schema.setCnterCalCode(payCalCode);
			//schema.setCalCode("");
			schema.setCalCode(this.payCalCodeBack);
			schema.setOthCalCode("");
		} else {
			schema.setOthCalCode(payCalCode);
			schema.setCalCode("");
			//schema.setCnterCalCode("");
			schema.setCnterCalCode(this.payCalCodeBack);
		}
		
		return true;
	}
	
	/**
	 * 根据险种责任编码生成缴费项编码，采用责任编码前三位+流水号（流水号不足3位的前面补0）方式生成
	 * @param dutyCode
	 * @return 生成的缴费项编码
	 * */
	private String createPayPlanCode(String dutyCode){
		String tempStr = PubFun1.CreateMaxNo("PAYPLANCODENOS", 3);
		String tPayPlanCode = dutyCode.substring(0, 3) + tempStr;
		return tPayPlanCode;
	}
	
	/**
	 * 根据险种责任编码生成给付项编码，采用责任编码前三位+流水号（流水号不足3位的前面补0）方式生成
	 * @param dutyCode
	 * @return 生成的给付项编码
	 * */
	private String createGetDutyCode(String dutyCode){
		String tempStr = PubFun1.CreateMaxNo("GETDUTYCODENOS", 3);
		String tGetDutyCode = dutyCode.substring(0, 3) + tempStr;
		return tGetDutyCode;
	}
	
	/**
	 * 生成缴费项/给付项算法编码，RI/RA+4位流水号
	 * @param codeType 算法编码类型：0-缴费项算法；1-给付项算法
	 * @return 生成的算法编码
	 * */
	private String createCalCode(int codeType){
		String noType = (codeType==0?"CALMODESELNO":"CALMODEGETSELNO");
		String tempStr = PubFun1.CreateMaxNo(noType, 4);
		String tCalCode = (codeType==0?"RI":"RA");
		tCalCode += tempStr;
		return tCalCode;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}

}


