



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

import com.sinosoft.lis.db.PD_LMDutyDB;
import com.sinosoft.lis.db.PD_LMDutyGetDB;
import com.sinosoft.lis.db.PD_LMDutyPayDB;
import com.sinosoft.lis.db.PD_LMRiskDB;
import com.sinosoft.lis.db.PD_LMRiskDutyDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.RiskState;
import com.sinosoft.lis.schema.PD_LMDutyGetSchema;
import com.sinosoft.lis.schema.PD_LMDutyPaySchema;
import com.sinosoft.lis.schema.PD_LMDutySchema;
import com.sinosoft.lis.schema.PD_LMRiskDutySchema;
import com.sinosoft.lis.schema.PD_LMRiskPayIntvSchema;
import com.sinosoft.lis.schema.PD_LMRiskPaySchema;
import com.sinosoft.lis.vschema.PD_LMDutyGetSet;
import com.sinosoft.lis.vschema.PD_LMDutyPaySet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDRiskDutyBL implements BusinessService{

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
	
	private MMap map1 = new MMap();

	private TransferData mTransferData = new TransferData();

	private PD_LMDutyPaySchema pd_LMDutyPaySchema;

	private PD_LMDutyGetSchema pd_LMDutyGetSchema;

	private PD_LMDutyPaySet delPD_LMDutyPaySet;

	private PD_LMDutyGetSet delPD_LMDutyGetSet;
	
	private PD_LMRiskDutySchema pd_LMRiskDutySchema;
	
	private PD_LMDutySchema pd_LMDutySchema;

	private PD_LMRiskDutySchema delpd_LMRiskDutySchema =new PD_LMRiskDutySchema();

	private String payOrGet;

	private ExeSQL exeSQL = new ExeSQL();

	private String whichAlgo;

	private String payCalCode;
	
	private String riskCode;
	private String mDutyCode = "";

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String operator) {
		try {
			operator = operator;

			if (!check()) {
				return false;
			}
			if (!getInputData(cInputData)) {
				return false;
			}

			if (!dealDutyData(operator)) {
				CError tError = new CError();
				tError.moduleName = "$tableName$BL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败$tableName$BL-->dealData!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if("del".equals(operator))
			{
				VData data = new VData();
				MMap delmap = new MMap();
				delmap.put(delpd_LMRiskDutySchema, "DELETE");
				delmap.put(pd_LMDutySchema, "DELETE");
				delmap.put(delPD_LMDutyPaySet,"DELETE");
				delmap.put(delPD_LMDutyGetSet,"DELETE");
				data.add(delmap);
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
			}
			else if ("save".equals(operator))
			{
				VData data = new VData();
				map.put(pd_LMDutySchema, "INSERT");
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
				
				map1.put(pd_LMRiskDutySchema, "INSERT");
				VData data1 = new VData();
				data1.add(this.map1);
				if (!tSubmit.submitData(data1, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tSubmit.mErrors);
					CError tError = new CError();
					tError.moduleName = "ProdDefWorkFlowBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
			else if ("update".equals(operator))
			{
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
			}
		}
			catch (Exception ex) {
			this.mErrors.addOneError(ex.getMessage());
			return false;
		}
			String riskCode = (String) mTransferData.getValueByName("riskCode");
			RiskState.setState(riskCode, "产品条款定义->责任信息", "1");
		return true;
	}
	
	/**
	 * 处理责任信息
	 * 
	 * @param operator
	 * @return
	 */
	private boolean dealDutyData(String operator){
		if ("save".equals(operator)) {
			PD_LMRiskDB pd_LMRiskDB = new PD_LMRiskDB();
			pd_LMRiskDB.setRiskCode(riskCode);
			if(!pd_LMRiskDB.getInfo()){
				this.mErrors.addOneError("请先定义险种");
				return false;
			}
			//生成责任编码，使用ldmaxno方式，不使用ldcode，险种前三位+流水号（流水号不足3位的前面补0）
			String tDutyCode = this.createDutyCode(riskCode);
			pd_LMDutySchema.setDutyCode(tDutyCode);
			pd_LMRiskDutySchema.setDutyCode(tDutyCode);
			
			PD_LMDutyDB qPD_LMDutyDB = new PD_LMDutyDB();
			qPD_LMDutyDB.setDutyCode(pd_LMDutySchema.getDutyCode());
			
			setDefaultValue("pd_LMDuty", pd_LMDutySchema);
			setDefaultValue("pd_LMRiskDuty", pd_LMRiskDutySchema);
			
		}else if("del".equals(operator)){
			PD_LMRiskDB pd_LMRiskDB = new PD_LMRiskDB();
			pd_LMRiskDB.setRiskCode(riskCode);
			if(!pd_LMRiskDB.getInfo()){
				this.mErrors.addOneError("请先定义险种");
				return false;
			}

			PD_LMRiskDutyDB qPD_LMRiskDutyDB = new PD_LMRiskDutyDB();
			qPD_LMRiskDutyDB.setDutyCode(mDutyCode);
			qPD_LMRiskDutyDB.setRiskCode(pd_LMRiskDutySchema.getRiskCode());
			if(!qPD_LMRiskDutyDB.getInfo()){
				this.mErrors.addOneError("无此记录！");
				return false;
			}
			delpd_LMRiskDutySchema.setSchema(qPD_LMRiskDutyDB.getSchema());
			
			PD_LMDutyDB qPD_LMDutyDB = new PD_LMDutyDB();
			qPD_LMDutyDB.setDutyCode(mDutyCode);
			if(!qPD_LMDutyDB.getInfo()){
				this.mErrors.addOneError("无此记录！");
				return false;
			}
			pd_LMDutySchema.setSchema(qPD_LMDutyDB.getSchema());

			PD_LMDutyPayDB qPD_LMDutyPayDB = new PD_LMDutyPayDB();
			qPD_LMDutyPayDB.setDutyCode(mDutyCode);
			if(qPD_LMDutyPayDB.query().size()!=0)
			{
				delPD_LMDutyPaySet = qPD_LMDutyPayDB.query();
			}
			PD_LMDutyGetDB qPD_LMDutyGetDB = new PD_LMDutyGetDB();
			qPD_LMDutyGetDB.setDutyCode(mDutyCode);
			if(qPD_LMDutyGetDB.query().size() != 0)
			{
				delPD_LMDutyGetSet=qPD_LMDutyGetDB.query();
			}
		}else if ("update".equals(operator)) {
			PD_LMRiskDutyDB tPD_LMRiskDutyDB = new PD_LMRiskDutyDB();
			tPD_LMRiskDutyDB.setDutyCode(mDutyCode);
			tPD_LMRiskDutyDB.setRiskCode(pd_LMRiskDutySchema.getRiskCode());
			if (!tPD_LMRiskDutyDB.getInfo()) {
				this.mErrors.addOneError("数据库无此记录,无法修改");
				return false;
			}
			PD_LMDutyDB tPD_LMDutyDB = new PD_LMDutyDB();
			tPD_LMDutyDB.setDutyCode(mDutyCode);
			if (!tPD_LMDutyDB.getInfo()) {
				this.mErrors.addOneError("数据库无此记录,无法修改");
				return false;
			}
			PD_LMDutyDB qPD_LMDutyDB = new PD_LMDutyDB();
			qPD_LMDutyDB.setDutyCode(mDutyCode);

			if (qPD_LMDutyDB.getInfo()) {
				PD_LMDutySchema qPD_LMDutySchema = qPD_LMDutyDB.getSchema();
				qPD_LMDutySchema.setDutyName(pd_LMDutySchema.getDutyName());
				qPD_LMDutySchema.setPayEndDateCalRef(pd_LMDutySchema.getPayEndDateCalRef());
				qPD_LMDutySchema.setPayEndDateCalMode(pd_LMDutySchema.getPayEndDateCalMode());
				qPD_LMDutySchema.setPayEndYearRela(pd_LMDutySchema.getPayEndYearRela());
				qPD_LMDutySchema.setGetYear(pd_LMDutySchema.getGetYear());
				qPD_LMDutySchema.setGetYearFlag(pd_LMDutySchema.getGetYearFlag());
				qPD_LMDutySchema.setGetYearRela(pd_LMDutySchema.getGetYearRela());
				qPD_LMDutySchema.setCalMode(pd_LMDutySchema.getCalMode());
				qPD_LMDutySchema.setBasicCalCode(pd_LMDutySchema.getBasicCalCode());
				qPD_LMDutySchema.setAmntFlag(pd_LMDutySchema.getAmntFlag());
				qPD_LMDutySchema.setVPU(pd_LMDutySchema.getVPU());
				qPD_LMDutySchema.setInsuYear(pd_LMDutySchema.getInsuYear());
				qPD_LMDutySchema.setInsuYearFlag(pd_LMDutySchema.getInsuYearFlag());
				qPD_LMDutySchema.setInsuYearRela(pd_LMDutySchema.getInsuYearRela());
				//-------- add by jucy
				//建议书计算方法
				qPD_LMDutySchema.setPCalMode(pd_LMDutySchema.getPCalMode());
				//-------- end 
				//-------- add by jucy
				//修改modifydate modifytime
				qPD_LMDutySchema.setModifyDate(PubFun.getCurrentDate());
				qPD_LMDutySchema.setModifyTime(PubFun.getCurrentTime());
				//-------- end
				
				//-------- add by jucy
				//增加lmduty表里的ADDAMNTFLAG存储
				qPD_LMDutySchema.setAddAmntFlag(pd_LMDutySchema.getAddAmntFlag());
				//-------- end
				//-------- add by jucy
				//增加lmduty表里的ADDAMNTFLAG存储
				qPD_LMDutySchema.setPayEndYear(pd_LMDutySchema.getPayEndYear());
				qPD_LMDutySchema.setPayEndYearFlag(pd_LMDutySchema.getPayEndYearFlag());
				//-------- end
				
				map.put(qPD_LMDutySchema, "UPDATE");
			}
			
			PD_LMRiskDutyDB qPD_LMRiskDutyDB = new PD_LMRiskDutyDB();
			qPD_LMRiskDutyDB.setDutyCode(mDutyCode);
			qPD_LMRiskDutyDB.setRiskCode(pd_LMRiskDutySchema.getRiskCode());

			if (qPD_LMRiskDutyDB.getInfo()) {
				PD_LMRiskDutySchema qPD_LMRiskDutySchema = qPD_LMRiskDutyDB.getSchema();
				qPD_LMRiskDutySchema.setChoFlag(pd_LMRiskDutySchema.getChoFlag());

				//-------- add by jucy
				//修改modifydate modifytime
				qPD_LMRiskDutySchema.setModifyDate(PubFun.getCurrentDate());
				qPD_LMRiskDutySchema.setModifyTime(PubFun.getCurrentTime());
				//-------- end
				map.put(qPD_LMRiskDutySchema, "UPDATE");
			}
		}
		return true;
	}
	
	private boolean dealGetDate(String operator) {
		if ("save".equals(operator)) {
			PD_LMRiskDB pd_LMRiskDB = new PD_LMRiskDB();
			pd_LMRiskDB.setRiskCode(riskCode);
			if(!pd_LMRiskDB.getInfo()){
				this.mErrors.addOneError("请先定义险种");
				return false;
			}
			
			PD_LMDutyGetDB qPD_LMDutyGetDB = new PD_LMDutyGetDB();
			qPD_LMDutyGetDB.setDutyCode(pd_LMDutyGetSchema.getDutyCode());
			qPD_LMDutyGetDB.setGetDutyCode(pd_LMDutyGetSchema.getGetDutyCode());
			if (qPD_LMDutyGetDB.getInfo()) {
				this.mErrors.addOneError("数据库已有给付记录,无法更新");
				return false;
			} else {
				PD_LMDutyDB qPD_LMDutyDB = new PD_LMDutyDB();
				qPD_LMDutyDB.setDutyCode(pd_LMDutyGetSchema.getDutyCode());
				if (!qPD_LMDutyDB.getInfo()) {
					this.mErrors.addOneError("数据库无此责任记录,无法增加");
					return false;
				}
				setDefaultValue("pd_lmdutyget", pd_LMDutyGetSchema);
				algoForGet(pd_LMDutyGetSchema);
				map.put(pd_LMDutyGetSchema, "INSERT");
				return true;
			}
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
			algoForGet(qPD_LMDutyGetSchema);
			map.put(qPD_LMDutyGetSchema, "UPDATE");
			/*
			String sql = "update pd_lmdutyget set calcode = '',cntercalcode = '',othcalcode = '' where dutycode = '"
				+ qPD_LMDutyGetSchema.getDutyCode()
				+ "' and getdutycode='"
				+ qPD_LMDutyGetSchema.getGetDutyCode()+ "';";
			map.put(sql, "UPDATE");
			*/

		} else if ("del".equals(operator)) {
			PD_LMDutyGetDB qPD_LMDutyGetDB = new PD_LMDutyGetDB();
			qPD_LMDutyGetDB.setDutyCode(pd_LMDutyGetSchema.getDutyCode());
			qPD_LMDutyGetDB.setGetDutyCode(pd_LMDutyGetSchema.getGetDutyCode());
			if (!qPD_LMDutyGetDB.getInfo()) {
				this.mErrors.addOneError("数据库无此给付记录,无法删除");
				return false;
			}
			map.put(qPD_LMDutyGetDB.getSchema(), "DELETE");
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
			PD_LMDutyDB qPD_LMDutyDB = new PD_LMDutyDB();
			qPD_LMDutyDB.setDutyCode(pd_LMDutySchema.getDutyCode());
			if (qPD_LMDutyDB.getInfo()) {
				this.mErrors.addOneError("数据库已有此责任记录,不能增加");
				// 有duty情况下进一步判断有无dutypay,有就返回false
				PD_LMDutyPayDB qPD_LMDutyPayDB = new PD_LMDutyPayDB();
				qPD_LMDutyPayDB.setDutyCode(pd_LMDutyPaySchema.getDutyCode());
				qPD_LMDutyPayDB.setPayPlanCode(pd_LMDutyPaySchema
						.getPayPlanCode());
				if (qPD_LMDutyPayDB.getInfo()) {
					this.mErrors.addOneError("数据库已有责任和责任缴费记录,无法更新");
					return false;
				} else {
					setDefaultValue("pd_lmdutypay", pd_LMDutyPaySchema);
					algoForPay(pd_LMDutyPaySchema);
					map.put(pd_LMDutyPaySchema, "INSERT");
					return true;
				}
			}
			setDefaultValue("pd_lmduty", pd_LMDutySchema);
			setDefaultValue("pd_lmriskduty", pd_LMRiskDutySchema);
			setDefaultValue("pd_lmdutypay", pd_LMDutyPaySchema);
			algoForPay(pd_LMDutyPaySchema);
			pd_LMDutySchema.setPayEndYearRela("3"); // 默认值
			pd_LMDutySchema.setGetYearRela("4"); // 默认值
			pd_LMDutySchema.setInsuYearRela("0");// 默认值
			if ("1".equals(whichAlgo)) {
				pd_LMDutySchema.setCalMode("G");
			}else if ("2".equals(whichAlgo)) {
				pd_LMDutySchema.setCalMode("P");
			}else if ("3".equals(whichAlgo)) {
				pd_LMDutySchema.setCalMode("O");
			}
			map.put(pd_LMDutySchema, "INSERT");

			map.put(pd_LMDutyPaySchema, "INSERT");

			map.put(pd_LMRiskDutySchema, "INSERT");
			
			PD_LMRiskPayIntvSchema tPD_LMRiskPayIntvSchema=new PD_LMRiskPayIntvSchema();
			tPD_LMRiskPayIntvSchema.setRiskCode(pd_LMRiskDutySchema.getRiskCode());
			tPD_LMRiskPayIntvSchema.setPayIntv("0");
			setDefaultValue("PD_LMRiskPayIntv", tPD_LMRiskPayIntvSchema);
			map.put(tPD_LMRiskPayIntvSchema, "DELETE&INSERT");
			PD_LMRiskPaySchema tPD_LMRiskPaySchema=new PD_LMRiskPaySchema();
			tPD_LMRiskPaySchema.setRiskCode(pd_LMRiskDutySchema.getRiskCode());
			PD_LMRiskDB tPD_LMRiskDB=new PD_LMRiskDB();
			tPD_LMRiskDB.setRiskCode(pd_LMRiskDutySchema.getRiskCode());
			if(tPD_LMRiskDB.getInfo()){
				tPD_LMRiskPaySchema.setRiskName(tPD_LMRiskDB.getRiskName());
				setDefaultValue("PD_LMRiskPay", tPD_LMRiskPaySchema);
				//map.put(tPD_LMRiskPaySchema, "DELETE&INSERT");//PD_LMRiskPay不在此处处理
			}
			
			
		} else if ("update".equals(operator)) {
			// duty表无记录,不能更新
			PD_LMDutyDB qPD_LMDutyDB = new PD_LMDutyDB();
			qPD_LMDutyDB.setDutyCode(pd_LMDutySchema.getDutyCode());
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

			PD_LMDutySchema qPD_LMDutySchema = qPD_LMDutyDB.getSchema();
			qPD_LMDutySchema.setDutyName(pd_LMDutySchema.getDutyName());
			if ("1".equals(whichAlgo)) {
				qPD_LMDutySchema.setCalMode("G");
			}else if ("2".equals(whichAlgo)) {
				qPD_LMDutySchema.setCalMode("P");
			}else if ("3".equals(whichAlgo)) {
				qPD_LMDutySchema.setCalMode("O");
			}
			map.put(qPD_LMDutySchema, "UPDATE");

			PD_LMDutyPaySchema qPD_LMDutyPaySchema = qPD_LMDutyPayDB
					.getSchema();
			qPD_LMDutyPaySchema.setPayPlanName(pd_LMDutyPaySchema
					.getPayPlanName());
			qPD_LMDutyPaySchema.setZeroFlag(pd_LMDutyPaySchema.getZeroFlag());
			algoForPay(qPD_LMDutyPaySchema);
			map.put(qPD_LMDutyPaySchema, "UPDATE");
			
			//选择标记更新
			PD_LMRiskDutyDB qPD_LMRiskDutyDB = new PD_LMRiskDutyDB();
			qPD_LMRiskDutyDB.setRiskCode(riskCode);
			qPD_LMRiskDutyDB.setDutyCode(pd_LMDutySchema.getDutyCode());
			if(qPD_LMRiskDutyDB.getInfo()){
				PD_LMRiskDutySchema qPD_LMRiskDutySchema =qPD_LMRiskDutyDB.getSchema();
				qPD_LMRiskDutySchema.setChoFlag(pd_LMRiskDutySchema.getChoFlag());
				map.put(qPD_LMRiskDutySchema, "UPDATE");
			}
			
			
			/*
			String sql = "update pd_lmdutypay set calcode = '',cntercalcode = '',othcalcode = '' where dutycode = '"
				+ qPD_LMDutyPaySchema.getDutyCode()
				+ "' and payplancode='"
				+ qPD_LMDutyPaySchema.getPayPlanCode() + "';";
			map.put(sql, "UPDATE");
			*/
		
			
			

		} else if ("del".equals(operator)) {
			map.put(pd_LMRiskDutySchema, "DELETE");

			// 把dutypay全删
			String sql1 = "delete from pd_lmdutypay where dutycode='"
					+ pd_LMDutySchema.getDutyCode()+"'";
			map.put(sql1, "DELETE");
			// dutyget全删
			String sql2 = "delete from pd_lmdutyget where dutycode='"
					+ pd_LMDutySchema.getDutyCode()+"'";
			map.put(sql2, "DELETE");

			map.put(pd_LMDutySchema, "DELETE");
			
			String sql3 = "delete from PD_LMRiskPayIntv where riskcode='"+pd_LMRiskDutySchema.getRiskCode()+"'";
			map.put(sql3, "DELETE");
			sql3 = "delete from PD_LMRiskPay where riskcode='"+pd_LMRiskDutySchema.getRiskCode()+"'";
			map.put(sql3, "DELETE");
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
		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);

		whichAlgo = (String) mTransferData.getValueByName("whichAlgo");
		payCalCode = (String) mTransferData.getValueByName("payCalCode");
		riskCode = (String)mTransferData.getValueByName("riskCode");
		mDutyCode = (String)mTransferData.getValueByName("DutyCode");
		
		globalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		pd_LMDutySchema = (PD_LMDutySchema) cInputData.getObjectByObjectName("PD_LMDutySchema", 0);
		pd_LMRiskDutySchema = (PD_LMRiskDutySchema) cInputData.getObjectByObjectName("PD_LMRiskDutySchema", 0);

		return true;
	}

	private boolean check() {
		// TODO Auto-generated method stub
		return true;
	}

	private void algoForPay(PD_LMDutyPaySchema schema) {
		
		if ("1".equals(whichAlgo)) {
			schema.setCalCode(payCalCode);
			schema.setCnterCalCode("");
			schema.setOthCalCode("");
		} else if ("2".equals(whichAlgo)) {
			schema.setCnterCalCode(payCalCode);
			schema.setCalCode("");
			schema.setOthCalCode("");
		} else {
			schema.setOthCalCode(payCalCode);
			schema.setCalCode("");
			schema.setCnterCalCode("");
		}
	}

	private void algoForGet(PD_LMDutyGetSchema schema) {
		
		if ("1".equals(whichAlgo)) {
			schema.setCalCode(payCalCode);
			schema.setCnterCalCode("");
			schema.setOthCalCode("");
		} else if ("2".equals(whichAlgo)) {
			schema.setCnterCalCode(payCalCode);
			schema.setCalCode("");
			schema.setOthCalCode("");
		} else {
			schema.setOthCalCode(payCalCode);
			schema.setCalCode("");
			schema.setCnterCalCode("");
		}
	}
	 public CErrors getErrors() {
			// TODO Auto-generated method stub
			return mErrors;
		}

		public VData getResult() {
			// TODO Auto-generated method stub
			return this.mResult;
		}
	/**
	 * 根据险种编码生成责任编码，采用险种前三位+流水号（流水号不足3位的前面补0）方式生成
	 * @param riskCode
	 * @return 生成的责任编码
	 * */
	private String createDutyCode(String riskCode){
		String tDutyNo = PubFun1.CreateMaxNo("DUTYCODENO", 3);
		if(tDutyNo.length() < 3){
			int tempStrL = tDutyNo.length();
			for(int i = tempStrL; i < 3; i ++){
				tDutyNo = "0" + tDutyNo;
			}
		}
		String tDutyCode = "";
		if(riskCode.length() < 4){
			tDutyCode = tDutyCode + tDutyNo;
		}else{
			tDutyCode = riskCode.substring(0, 3);
			tDutyCode = tDutyCode + tDutyNo;
		}
		return tDutyCode;
	}

}

