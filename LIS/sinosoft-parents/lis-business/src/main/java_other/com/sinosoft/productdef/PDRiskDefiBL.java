



/**
 * <p>Title: PDRiskDefi</p>
 * <p>Description: 产品基础信息定义</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-12
 */

package com.sinosoft.productdef;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.sinosoft.lis.db.PD_LMDutyDB;
import com.sinosoft.lis.db.PD_LMDutyGetDB;
import com.sinosoft.lis.db.PD_LMDutyPayDB;
import com.sinosoft.lis.db.PD_LMRiskAppDB;
import com.sinosoft.lis.db.PD_LMRiskDB;
import com.sinosoft.lis.db.PD_LMRiskDutyDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.RiskState;
import com.sinosoft.lis.schema.PD_LMRiskAppSchema;
import com.sinosoft.lis.schema.PD_LMRiskPayIntvSchema;
import com.sinosoft.lis.schema.PD_LMRiskRelaSchema;
import com.sinosoft.lis.schema.PD_LMRiskSchema;
import com.sinosoft.lis.schema.PD_LMRiskSortSchema;
import com.sinosoft.lis.vschema.PD_LMDutyGetSet;
import com.sinosoft.lis.vschema.PD_LMDutyPaySet;
import com.sinosoft.lis.vschema.PD_LMRiskDutySet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDRiskDefiBL implements BusinessService{
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private MMap map = new MMap();

	public PDRiskDefiBL() {
	}

	private PD_LMRiskAppSchema pd_LMRiskAppSchema = null;

	private PD_LMRiskSchema pd_LMRiskSchema = null;

	private String isSubRisk;

	TransferData mTransferData;

	private String riskCode;

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		if (!check()) {
			return false;
		}
		
		prepareData(cInputData);

		if (!dealdata(cInputData, cOperate)) {
			return false;
		}

		VData data = new VData();
		data.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		
		if (!tPubSubmit.submitData(data, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpBalanceOnTimeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}
		
		if(cOperate.trim().equals("save")||cOperate.trim().equals("update")){
			//账户型
			String tInsuAccFlag = pd_LMRiskSchema.getInsuAccFlag();
			
			//分红
			String tBonusFlag = pd_LMRiskAppSchema.getBonusFlag();
			
			//险种细类
			String tRiskTypeDetail = pd_LMRiskAppSchema.getRiskTypeDetail();
			
			//团个险
			String tRiskProp =  pd_LMRiskAppSchema.getRiskProp();
			
			System.out.println("tInsuAccFlag:" + tInsuAccFlag + " tBonusFlag:" + tBonusFlag + " tRiskTypeDetail:" + tRiskTypeDetail + " tRiskProp:" + tRiskProp);
			
			//将基本信息设置状态
			new RiskState().setState(riskCode, "产品条款定义->基本信息", "1");
			
			//通过判断添加新的节点
//			new RiskState().addNode(riskCode, tPath, tNodeUrl)
			if(tInsuAccFlag.trim().equals("Y")){
				//new RiskState().addNode(riskCode, "理赔业务控制->保障责任赔付明细", "账户责任给付明细:../productdef/PDRiskAccGetInput.jsp?riskcode=" + riskCode);
				//new RiskState().addNode(riskCode, "理赔业务控制->险种账户定义", "账户责任给付明细:../productdef/PDRiskInsuAccInput.jsp?riskcode=" + riskCode);
				
				new RiskState().addNode(riskCode, "产品条款定义->责任生存给付信息", "险种账户定义:../productdef/PDRiskInsuAccMain.jsp?riskcode=" + riskCode);
				new RiskState().addNode(riskCode, "产品条款定义->险种账户定义", "账户缴费定义:../productdef/PDRiskAccPayMain.jsp?riskcode=" + riskCode);
				new RiskState().addNode(riskCode, "产品条款定义->账户缴费定义", "账户给付定义:../productdef/PDRiskAccGetMain.jsp?riskcode=" + riskCode);
				new RiskState().addNode(riskCode, "产品条款定义->账户给付定义", "账户管理费定义:../productdef/PDRiskFeeMain.jsp?riskcode=" + riskCode);

				
			}
			
			if(tBonusFlag.trim().equals("Y")){
				new RiskState().addNode(riskCode, "保全业务控制->保全项目算法定义", "分红定义:../productdef/PDRiskBonusInput.jsp?riskcode=" + riskCode);
			}
			
			//医疗保险
			if(tRiskTypeDetail.trim().equals("M")){
				new RiskState().addNode(riskCode, "理赔业务控制->算法定义", "关联账单定义:../productdef/PDRiskAccGetInput.jsp?riskcode=" + riskCode);
			}
			
			if(tRiskProp.trim().equals("G")){
				new RiskState().addNode(riskCode, "契约业务控制->险种投保规则", "保障计划约定要素:../productdef/PDRiskDutyFactorInput.jsp?riskcode=" + riskCode);
			}else{
				new RiskState().addNode(riskCode, "契约业务控制->条款打印定义", "个险页面录入配置:../productdef/PDRiskParamsDefInput.jsp?riskcode=" + riskCode);
			}
		}
		// 进行业务处理
		/*
		 * CommonBase commonBase = new CommonBase(); boolean result =
		 * commonBase.submitData(cInputData, cOperate);
		 * 
		 * if(!result) { this.mErrors.addOneError("PDRiskDefiBL.submitData处理失败，" +
		 * commonBase.mErrors.getFirstError()); return false; }
		 */
		new RiskState().setState(riskCode, "产品条款定义->基本信息", "1");
		return true;
	}

	private void prepareData(VData cInputData) {
		// TODO Auto-generated method stub
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"transferData", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"globalInput", 0);
		isSubRisk = (String) mTransferData.getValueByName("isSubRisk");
		pd_LMRiskAppSchema = (PD_LMRiskAppSchema) cInputData
				.getObjectByObjectName("pd_LMRiskAppSchema", 0);

		pd_LMRiskSchema = (PD_LMRiskSchema) cInputData.getObjectByObjectName(
				"pd_LMRiskSchema", 0);
		riskCode = (String) mTransferData.getValueByName("riskCode");
	}

	/**
	 * 处理险种
	 * 
	 * @param cInputData
	 */
	private boolean dealIsSubRisk(VData cInputData, String operator) {
		if ("S".equals(isSubRisk)) {
			ArrayList riskCodeList = (ArrayList) mTransferData
					.getValueByName("mainRiskCode");
			
			String sql2 = "delete from pd_lmriskrela where relariskcode='"
					+ riskCode + "'";
			map.put(sql2, "DELETE");
			if(riskCodeList == null){
				return true;
			}
			
			for (int i = 0; i < riskCodeList.size(); i++) {
				PD_LMRiskRelaSchema tPD_LMRiskRelaSchema = new PD_LMRiskRelaSchema();
				String mainRiskCode = (String) riskCodeList.get(i);
				if ("".equals(mainRiskCode) || mainRiskCode == null) {
					continue;
				}

				PD_LMRiskDB pd_LMRiskDB = new PD_LMRiskDB();
				pd_LMRiskDB.setRiskCode(mainRiskCode);
				if (!pd_LMRiskDB.getInfo()) {
					this.mErrors.addOneError("主险编码中有非存在险种,请先检查");
					return false;
				}

				tPD_LMRiskRelaSchema.setRiskCode(mainRiskCode);
				tPD_LMRiskRelaSchema.setRelaRiskCode(riskCode);
				tPD_LMRiskRelaSchema.setRelaCode("01");
				tPD_LMRiskRelaSchema.setV("Operator", mGlobalInput.Operator);
				tPD_LMRiskRelaSchema.setV("MakeDate", PubFun.getCurrentDate());
				//-------- update by jucy1203
				//修改ManageComGrp字段存储方式为：存储当前登录机构。
				tPD_LMRiskRelaSchema.setManageComGrp("HK");
				//-------- end
				
				tPD_LMRiskRelaSchema.setV("MakeTime", PubFun.getCurrentTime());
				tPD_LMRiskRelaSchema.setV("ModifyDate", PubFun.getCurrentDate());
				tPD_LMRiskRelaSchema.setV("ModifyTime", PubFun.getCurrentTime());
				map.put(tPD_LMRiskRelaSchema, operator);
			}
		} else {
			ArrayList riskCodeList = (ArrayList) mTransferData
					.getValueByName("subRiskCode");		
			
			String sql1 = "delete from pd_lmriskrela where riskcode='"
					+ riskCode + "'";
			map.put(sql1, "DELETE");
			if(riskCodeList == null){
				return true;
			}
			for (int i = 0; i < riskCodeList.size(); i++) {
				PD_LMRiskRelaSchema tPD_LMRiskRelaSchema = new PD_LMRiskRelaSchema();
				String subRiskCode = (String) riskCodeList.get(i);
				if ("".equals(subRiskCode) || subRiskCode == null) {
					continue;
				}
				tPD_LMRiskRelaSchema.setRelaRiskCode(subRiskCode);
				tPD_LMRiskRelaSchema.setRiskCode(riskCode);
				tPD_LMRiskRelaSchema.setRelaCode("01");
				tPD_LMRiskRelaSchema.setV("Operator", mGlobalInput.Operator);
				tPD_LMRiskRelaSchema.setV("MakeDate", PubFun.getCurrentDate());
				tPD_LMRiskRelaSchema.setManageComGrp("HK");
				tPD_LMRiskRelaSchema.setV("MakeTime", PubFun.getCurrentTime());
				tPD_LMRiskRelaSchema.setV("ModifyDate", PubFun.getCurrentDate());
				tPD_LMRiskRelaSchema.setV("ModifyTime", PubFun.getCurrentTime());
				map.put(tPD_LMRiskRelaSchema, operator);
			}
		}
		return true;
		/*
		 * else { ArrayList riskCodeListM = (ArrayList) mTransferData
		 * .getValueByName("mainRiskCode"); for (int i = 0; i <
		 * riskCodeListM.size(); i++) { PD_LMRiskRelaSchema tPD_LMRiskRelaSchema =
		 * new PD_LMRiskRelaSchema(); String mainRiskCode = (String)
		 * riskCodeListM.get(i); if ("".equals(mainRiskCode) || mainRiskCode ==
		 * null) { continue; } tPD_LMRiskRelaSchema.setRiskCode(mainRiskCode);
		 * tPD_LMRiskRelaSchema.setRelaRiskCode(riskCode);
		 * tPD_LMRiskRelaSchema.setRelaCode("01");
		 * tPD_LMRiskRelaSchema.setV("Operator", mGlobalInput.Operator);
		 * tPD_LMRiskRelaSchema.setV("MakeDate", PubFun.getCurrentDate());
		 * tPD_LMRiskRelaSchema.setManageComGrp("000000");
		 * tPD_LMRiskRelaSchema.setV("MakeTime", PubFun.getCurrentTime());
		 * tPD_LMRiskRelaSchema .setV("ModifyDate", PubFun.getCurrentDate());
		 * tPD_LMRiskRelaSchema .setV("ModifyTime", PubFun.getCurrentTime());
		 * map.put(tPD_LMRiskRelaSchema, operator); } ArrayList riskCodeListS =
		 * (ArrayList) mTransferData .getValueByName("subRiskCode"); for (int i =
		 * 0; i < riskCodeListS.size(); i++) { PD_LMRiskRelaSchema
		 * tPD_LMRiskRelaSchema = new PD_LMRiskRelaSchema(); String subRiskCode =
		 * (String) riskCodeListS.get(i); if ("".equals(subRiskCode) ||
		 * subRiskCode == null) { continue; }
		 * tPD_LMRiskRelaSchema.setRelaRiskCode(subRiskCode);
		 * tPD_LMRiskRelaSchema.setRiskCode(riskCode);
		 * tPD_LMRiskRelaSchema.setRelaCode("01");
		 * tPD_LMRiskRelaSchema.setV("Operator", mGlobalInput.Operator);
		 * tPD_LMRiskRelaSchema.setV("MakeDate", PubFun.getCurrentDate());
		 * tPD_LMRiskRelaSchema.setManageComGrp("000000");
		 * tPD_LMRiskRelaSchema.setV("MakeTime", PubFun.getCurrentTime());
		 * tPD_LMRiskRelaSchema .setV("ModifyDate", PubFun.getCurrentDate());
		 * tPD_LMRiskRelaSchema .setV("ModifyTime", PubFun.getCurrentTime());
		 * map.put(tPD_LMRiskRelaSchema, operator); } }
		 */
	}

	private boolean dealdata(VData cInputData, String cOperate) {

		if ("save".equalsIgnoreCase(cOperate)) {
			PD_LMRiskDB tPD_LMRiskDB = new PD_LMRiskDB();
			tPD_LMRiskDB.setRiskCode(pd_LMRiskAppSchema.getRiskCode());
			if (tPD_LMRiskDB.getInfo()) {
				this.mErrors.addOneError("数据库存在此记录,无法增加");
				return false;
			}
			PD_LMRiskAppDB tPD_LMRiskAppDB = new PD_LMRiskAppDB();
			tPD_LMRiskAppDB.setRiskCode(pd_LMRiskAppSchema.getRiskCode());
			if (tPD_LMRiskAppDB.getInfo()) {
				this.mErrors.addOneError("数据库存在此记录,无法增加");
				return false;
			}

			if (!dealIsSubRisk(cInputData, "INSERT")) {
				return false;// 处理主附险种
			}
			
			String riskType4 = pd_LMRiskAppSchema.getRiskType4();
			setDefaultValue("pd_lmriskApp", pd_LMRiskAppSchema);
			pd_LMRiskAppSchema.setRiskType4(riskType4);
			
			String riskStatName = pd_LMRiskSchema.getRiskStatName();
			setDefaultValue("pd_lmrisk", pd_LMRiskSchema);
			pd_LMRiskSchema.setRiskStatName(riskStatName);
			
			PD_LMRiskSortSchema tPD_LMRiskSortSchema=new PD_LMRiskSortSchema();
			tPD_LMRiskSortSchema.setRiskCode(pd_LMRiskSchema.getRiskCode());
			tPD_LMRiskSortSchema.setRiskSortType("7");
			tPD_LMRiskSortSchema.setRiskSortValue("0");
			setDefaultValue("PD_LMRiskSort", tPD_LMRiskSortSchema);
			
			Integer tPD_LMRiskPayIntvSchemaLengthInt =  (Integer)mTransferData.getValueByName("PD_LMRiskPayIntvSchemaLength");
			System.out.println("tPD_LMRiskPayIntvSchemaLength:" + tPD_LMRiskPayIntvSchemaLengthInt);					
			if(tPD_LMRiskPayIntvSchemaLengthInt!=null)
			{
			for(int i = 0; i < tPD_LMRiskPayIntvSchemaLengthInt; i ++){
				PD_LMRiskPayIntvSchema tPD_LMRiskPayIntvSchema = (PD_LMRiskPayIntvSchema)mTransferData.getValueByName("PD_LMRiskPayIntvSchema" + i);
				this.setDefaultValue("PD_LMRiskPayIntv", tPD_LMRiskPayIntvSchema);
				map.put(tPD_LMRiskPayIntvSchema, "INSERT");
			}
			}
			
			map.put(pd_LMRiskSchema, "INSERT");
			map.put(pd_LMRiskAppSchema, "INSERT");
			map.put(tPD_LMRiskSortSchema, "INSERT");

		} else if ("del".equalsIgnoreCase(cOperate)) {
			PD_LMRiskDB tPD_LMRiskDB = new PD_LMRiskDB();
			tPD_LMRiskDB.setRiskCode(pd_LMRiskAppSchema.getRiskCode());
			if (!tPD_LMRiskDB.getInfo()) {
				this.mErrors.addOneError("该险种不存在或已被删除!");
			}
			PD_LMRiskAppDB tPD_LMRiskAppDB = new PD_LMRiskAppDB();
			tPD_LMRiskAppDB.setRiskCode(pd_LMRiskAppSchema.getRiskCode());
			if (tPD_LMRiskAppDB.getInfo()) {
				map.put(tPD_LMRiskAppDB.getSchema(), "DELETE");
			}
			String sql1 = "delete from pd_lmriskrela where riskcode='"
					+ riskCode + "'";
			map.put(sql1, "DELETE");
			String sql2 = "delete from pd_lmriskrela where relariskcode='"
					+ riskCode + "'";
			map.put(sql2, "DELETE");
			
			String sql3 = "delete from PD_LMRiskSort where riskcode='"
				+ riskCode + "'";
			map.put(sql3, "DELETE");
			sql3 = "delete from PD_LMRiskPayIntv where riskcode='"+riskCode+"'";
			map.put(sql3, "DELETE");
			sql3 = "delete from PD_LMRiskPay where riskcode='"+riskCode+"'";
			map.put(sql3, "DELETE");
			
			//-------- add by jucy
			//删除险种时 删除多语言信息LDMsgInfo表
			String sql4="delete from LDMsgInfo Where keyid='"+riskCode+"' and msgtype='Risk'";
			map.put(sql4, "DELETE");
			//-------- end
			
			PD_LMRiskDutyDB tPD_LMRiskDutyDB = new PD_LMRiskDutyDB();
			tPD_LMRiskDutyDB.setRiskCode(pd_LMRiskAppSchema.getRiskCode());
			PD_LMRiskDutySet tPD_LMRiskDutySet = tPD_LMRiskDutyDB.query();
			if (tPD_LMRiskDutySet != null) {
				for (int i = 1; i <= tPD_LMRiskDutySet.size(); i++) {
					PD_LMDutyGetDB tPD_LMDutyGetDB = new PD_LMDutyGetDB();
					tPD_LMDutyGetDB.setDutyCode(tPD_LMRiskDutySet.get(i)
							.getDutyCode());
					PD_LMDutyGetSet tPD_LMDutyGetSet = tPD_LMDutyGetDB.query();
					if (tPD_LMDutyGetSet != null) {
						for (int j = 1; j <= tPD_LMDutyGetSet.size(); j++) {
							map.put(tPD_LMDutyGetSet.get(j), "DELETE");
						}
					}
					PD_LMDutyPayDB tPD_LMDutyPayDB = new PD_LMDutyPayDB();
					tPD_LMDutyPayDB.setDutyCode(tPD_LMRiskDutySet.get(i)
							.getDutyCode());
					PD_LMDutyPaySet tPD_LMDutyPaySet = tPD_LMDutyPayDB.query();
					if (tPD_LMDutyPaySet != null) {
						for (int j = 1; j <= tPD_LMDutyPaySet.size(); j++) {
							map.put(tPD_LMDutyPaySet.get(j), "DELETE");
						}
					}
					map.put(tPD_LMRiskDutySet.get(i), "DELETE");
					PD_LMDutyDB tPD_LMDutyDB = new PD_LMDutyDB();
					tPD_LMDutyDB.setDutyCode(tPD_LMRiskDutySet.get(i)
							.getDutyCode());
					if (tPD_LMDutyDB.getInfo()) {
						map.put(tPD_LMDutyDB.getSchema(), "DELETE");
					}

				}
				map.put(tPD_LMRiskDB.getSchema(), "DELETE");
			}

		} else if ("update".equalsIgnoreCase(cOperate)) {
			PD_LMRiskDB tPD_LMRiskDB = new PD_LMRiskDB();
			tPD_LMRiskDB.setRiskCode(pd_LMRiskAppSchema.getRiskCode());
			if (!tPD_LMRiskDB.getInfo()) {
				this.mErrors.addOneError("数据库无此记录,无法修改");
				return false;
			}
			PD_LMRiskAppDB tPD_LMRiskAppDB = new PD_LMRiskAppDB();
			tPD_LMRiskAppDB.setRiskCode(pd_LMRiskAppSchema.getRiskCode());
			if (!tPD_LMRiskAppDB.getInfo()) {
				this.mErrors.addOneError("数据库无此记录,无法修改");
				return false;
			}
			PD_LMRiskDB qPD_LMRiskDB = new PD_LMRiskDB();
			qPD_LMRiskDB.setRiskCode(pd_LMRiskSchema.getRiskCode());

			if (qPD_LMRiskDB.getInfo()) {
				PD_LMRiskSchema qPD_LMRiskSchema = qPD_LMRiskDB.getSchema();
				qPD_LMRiskSchema.setRiskName(pd_LMRiskSchema.getRiskName());
				qPD_LMRiskSchema.setRiskShortName(pd_LMRiskSchema.getRiskShortName());
				qPD_LMRiskSchema.setRiskEnName(pd_LMRiskSchema.getRiskEnName());
				qPD_LMRiskSchema.setRiskEnShortName(pd_LMRiskSchema.getRiskEnShortName());
				qPD_LMRiskSchema.setChoDutyFlag(pd_LMRiskSchema.getChoDutyFlag());
				qPD_LMRiskSchema.setRnewFlag(pd_LMRiskSchema.getRnewFlag());
				qPD_LMRiskSchema.setCPayFlag(pd_LMRiskSchema.getCPayFlag());
				qPD_LMRiskSchema.setRinsFlag(pd_LMRiskSchema.getRinsFlag());
				qPD_LMRiskSchema.setGetFlag(pd_LMRiskSchema.getGetFlag());
				qPD_LMRiskSchema.setInsuAccFlag(pd_LMRiskSchema.getInsuAccFlag());
				qPD_LMRiskSchema.setOrigRiskCode(pd_LMRiskSchema.getOrigRiskCode());
				//-------- add by jucy 
				//加入修改时间和修改日期。
				qPD_LMRiskSchema.setModifyDate(PubFun.getCurrentDate());
				qPD_LMRiskSchema.setModifyTime(PubFun.getCurrentTime());
				//-------- end
				map.put(qPD_LMRiskSchema, "UPDATE");
			}

			PD_LMRiskAppDB qPD_LMRiskAppDB = new PD_LMRiskAppDB();
			qPD_LMRiskAppDB.setRiskCode(pd_LMRiskAppSchema.getRiskCode());

			if (qPD_LMRiskAppDB.getInfo()) {
				PD_LMRiskAppSchema qPD_LMRiskAppSchema = qPD_LMRiskAppDB.getSchema();
				qPD_LMRiskAppSchema.setKindCode(pd_LMRiskAppSchema.getKindCode());
				qPD_LMRiskAppSchema.setRiskProp(pd_LMRiskAppSchema.getRiskProp());
				qPD_LMRiskAppSchema.setRiskType1(pd_LMRiskAppSchema.getRiskType1());
				qPD_LMRiskAppSchema.setRiskTypeDetail(pd_LMRiskAppSchema.getRiskTypeDetail());
				qPD_LMRiskAppSchema.setRiskPeriod(pd_LMRiskAppSchema.getRiskPeriod());
				qPD_LMRiskAppSchema.setPolType(pd_LMRiskAppSchema.getPolType());
				qPD_LMRiskAppSchema.setRiskName(pd_LMRiskAppSchema.getRiskName());
				qPD_LMRiskAppSchema.setStartDate(pd_LMRiskAppSchema.getStartDate());
				qPD_LMRiskAppSchema.setEndDate(pd_LMRiskAppSchema.getEndDate());
				qPD_LMRiskAppSchema.setSubRiskFlag(pd_LMRiskAppSchema.getSubRiskFlag());
				qPD_LMRiskAppSchema.setRiskType(pd_LMRiskAppSchema.getRiskType());
				qPD_LMRiskAppSchema.setRiskType5(pd_LMRiskAppSchema.getRiskType5());
				qPD_LMRiskAppSchema.setRiskType4(pd_LMRiskAppSchema.getRiskType4());
				qPD_LMRiskAppSchema.setAutoPayFlag(pd_LMRiskAppSchema.getAutoPayFlag());
				qPD_LMRiskAppSchema.setInvestFlag(pd_LMRiskAppSchema.getInvestFlag());
				qPD_LMRiskAppSchema.setCutAmntStopPay(pd_LMRiskAppSchema.getCutAmntStopPay());
				qPD_LMRiskAppSchema.setBonusFlag(pd_LMRiskAppSchema.getBonusFlag());
				qPD_LMRiskAppSchema.setListFlag(pd_LMRiskAppSchema.getListFlag());
				qPD_LMRiskAppSchema.setSignDateCalMode(pd_LMRiskAppSchema.getSignDateCalMode());
				qPD_LMRiskAppSchema.setMinAppntAge(pd_LMRiskAppSchema.getMinAppntAge());
				qPD_LMRiskAppSchema.setMaxAppntAge(pd_LMRiskAppSchema.getMaxAppntAge());
				qPD_LMRiskAppSchema.setMaxInsuredAge(pd_LMRiskAppSchema.getMaxInsuredAge());
				qPD_LMRiskAppSchema.setMinInsuredAge(pd_LMRiskAppSchema.getMinInsuredAge());
				qPD_LMRiskAppSchema.setInsuredFlag(pd_LMRiskAppSchema.getInsuredFlag());
				qPD_LMRiskAppSchema.setMngCom(pd_LMRiskAppSchema.getMngCom());
				qPD_LMRiskAppSchema.setRiskType2(pd_LMRiskAppSchema.getRiskType2());
				qPD_LMRiskAppSchema.setRiskType3(pd_LMRiskAppSchema.getRiskType3());
				qPD_LMRiskAppSchema.setRiskType4(pd_LMRiskAppSchema.getRiskType4());
				qPD_LMRiskAppSchema.setRiskType5(pd_LMRiskAppSchema.getRiskType5());
				qPD_LMRiskAppSchema.setRiskType7(pd_LMRiskAppSchema.getRiskType7());
				qPD_LMRiskAppSchema.setRiskType9(pd_LMRiskAppSchema.getRiskType9());
				qPD_LMRiskAppSchema.setCancleForeGetSpecFlag(pd_LMRiskAppSchema.getCancleForeGetSpecFlag());
				qPD_LMRiskAppSchema.setRiskTypeAcc(pd_LMRiskAppSchema.getRiskTypeAcc());
				qPD_LMRiskAppSchema.setNeedGetPolDate(pd_LMRiskAppSchema.getNeedGetPolDate());
				qPD_LMRiskAppSchema.setSpecFlag(pd_LMRiskAppSchema.getSpecFlag());

				qPD_LMRiskAppSchema.setRiskFlag(pd_LMRiskAppSchema.getRiskFlag());
				qPD_LMRiskAppSchema.setBonusMode(pd_LMRiskAppSchema.getBonusMode());
				qPD_LMRiskAppSchema.setLoanFlag(pd_LMRiskAppSchema.getLoanFlag());
				
				qPD_LMRiskAppSchema.setSaleFlag(pd_LMRiskAppSchema.getSaleFlag());
				qPD_LMRiskAppSchema.setAutoCTFlag(pd_LMRiskAppSchema.getAutoCTFlag());
				qPD_LMRiskAppSchema.setAutoPayType(pd_LMRiskAppSchema.getAutoPayType());
				qPD_LMRiskAppSchema.setAutoETIFlag(pd_LMRiskAppSchema.getAutoETIFlag());
				qPD_LMRiskAppSchema.setAutoETIType(pd_LMRiskAppSchema.getAutoETIType());
				qPD_LMRiskAppSchema.setNonParFlag(pd_LMRiskAppSchema.getNonParFlag());
				qPD_LMRiskAppSchema.setBackDateFlag(pd_LMRiskAppSchema.getBackDateFlag());

				//MortagageFlag
				qPD_LMRiskAppSchema.setMortagageFlag(pd_LMRiskAppSchema.getMortagageFlag());
				
				//-------- add by jucy 
				//加入修改时间和修改日期。
				qPD_LMRiskAppSchema.setModifyDate(PubFun.getCurrentDate());
				qPD_LMRiskAppSchema.setModifyTime(PubFun.getCurrentTime());
				//-------- end
				//----modify by wanghaichao
				qPD_LMRiskAppSchema.setHoneymoonFlag(pd_LMRiskAppSchema.getHoneymoonFlag());
				qPD_LMRiskAppSchema.setNLGFlag(pd_LMRiskAppSchema.getNLGFlag());
				//end
				
				map.put(qPD_LMRiskAppSchema, "UPDATE");
			}
			if (!dealIsSubRisk(cInputData, "INSERT")) {
				return false;
			}
			
			map.put("delete from PD_LMRiskPayIntv where riskcode='" + pd_LMRiskAppSchema.getRiskCode() + "'", "DELETE");
			Integer tPD_LMRiskPayIntvSchemaLengthInt =  (Integer)mTransferData.getValueByName("PD_LMRiskPayIntvSchemaLength");
			System.out.println("tPD_LMRiskPayIntvSchemaLength:" + tPD_LMRiskPayIntvSchemaLengthInt);					
			if(tPD_LMRiskPayIntvSchemaLengthInt!=null)
			{
			for(int i = 0; i < tPD_LMRiskPayIntvSchemaLengthInt; i ++){
				PD_LMRiskPayIntvSchema tPD_LMRiskPayIntvSchema = (PD_LMRiskPayIntvSchema)mTransferData.getValueByName("PD_LMRiskPayIntvSchema" + i);
				this.setDefaultValue("PD_LMRiskPayIntv", tPD_LMRiskPayIntvSchema);
				map.put(tPD_LMRiskPayIntvSchema, "INSERT");
			}
			}
		}
		return true;
	}

	/**
	 * 取得PDRiskDefiBL传入的表名及riskcode
	 * 
	 * @param cInputData
	 * @return
	 */
	public void getDefaultValue() {
		System.out.println("A");
	}

	private boolean check() {
		return true;
	}

	private ExeSQL exec = new ExeSQL();

	private void setDefaultValue(String tableName, Schema schema) {
		String sql = "select fieldcode, fieldinitvalue from pd_basefield where tablecode=upper('"
				+ tableName + "') and isdisplay=0 order by displayorder ";
		SSRS ssrs = exec.execSQL(sql);
		int rowCount = ssrs.MaxRow;
		for (int i = 1; i <= rowCount; i++) {
			schema.setV(ssrs.GetText(i, 1), ssrs.GetText(i, 2));
		}
		schema.setV("Operator", mGlobalInput.Operator);
		schema.setV("MakeDate", PubFun.getCurrentDate());
		schema.setV("MakeTime", PubFun.getCurrentTime());
		schema.setV("ModifyDate", PubFun.getCurrentDate());
		schema.setV("ModifyTime", PubFun.getCurrentTime());
	}

	public void prepareUpdate(String tableName, Schema schema) {

		try {
			Class schemaClass = Class.forName("com.sinosoft.lis.db."
					+ tableName + "DB");
			Object obj = (Object) schemaClass.newInstance();
			Method m = null;
			if (schema instanceof PD_LMRiskAppSchema) {

			}
			m = obj.getClass().getMethod("getInfo", null);
			Boolean b = (Boolean) m.invoke(obj, null);
			m = obj.getClass().getMethod("getSchema", null);
			schema = (Schema) m.invoke(obj, null);

		} catch (Exception ex) {
			ex.printStackTrace();
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
	 * 设置显示状态
	 * 
	 * @return
	 */
	private boolean setState(){
		
		return true;
	}
	
	public static void main(String[] args) {
	}
}

