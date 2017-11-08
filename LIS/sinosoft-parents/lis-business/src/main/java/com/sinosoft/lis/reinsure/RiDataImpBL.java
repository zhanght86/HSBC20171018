

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIPolRecordSet;
import com.sinosoft.lis.vschema.RIWFLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 再保费率表磁盘导入类
 * </p>
 * <p>
 * Description: 把从磁盘导入的费率表添加到数据库
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZhangBin
 * @version 1.0
 */
public class RiDataImpBL {
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局变量 */
	private GlobalInput mGlobalInput;
	private VData mInputData = new VData();
	private VData mResult = new VData();
	private MMap mMap = new MMap();

	private String ImportPath = "";
	private String FileName = "";
	private String FIleType = "";
	private String configName = "RiDataImp.xml";
	private String sheetName = "Sheet1";
	private String batchno = "";

	private RIPolRecordSet outRIPolRecordSet = new RIPolRecordSet();
	private RIWFLogSet outRIWFLog = new RIWFLogSet();

	public RiDataImpBL(GlobalInput tVdata) {
		this.mGlobalInput = tVdata;
	}

	public boolean submitdata(String ImportPath, String FileName) {
		try {
			this.ImportPath = ImportPath;
			this.FileName = FileName;

			System.out.println("---Start DealData---");
			if (!dealdata()) {
				return false;
			}
			System.out.println("---End DealData---");

			System.out.println("---Start prepareOutputData---");
			if (!prepareOutputData()) {
				return false;
			}
			System.out.println("---End prepareOutputData---");

			System.out.println("---Start PubSubmit---");
			PubSubmit tPubSubmit = new PubSubmit();
			System.out.println(" bbbbbbbbbbbbbbbbbbb: " + mInputData.size());
			if (!tPubSubmit.submitData(mInputData)) {
				CError.buildErr(this,
						"数据提交失败!" + tPubSubmit.mErrors.getFirstError());
				return false;
			}
			System.out.println("---End PubSubmit---");

		} catch (Exception e) {
			CError tError = new CError();
			e.printStackTrace();
			tError.moduleName = "RiDataImpBL";
			tError.functionName = "submitdata";
			tError.errorMessage = "数据处理失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据处理
	 * */
	public boolean dealdata() {
		FIleType = FileName.substring(FileName.lastIndexOf('.') + 1,
				FileName.length());
		// 从磁盘导入数据
		RiImportAct tRiImportAct = new RiImportAct(ImportPath + FileName,
				ImportPath + configName, sheetName);
		System.out.println(" path+fileName======" + ImportPath + configName);

		if (!tRiImportAct.doImport()) {
			this.mErrors.copyAllErrors(tRiImportAct.mErrrors);
			return false;
		}
		// 得到RIPolRecordSet
		RIPolRecordSet tRIPolRecordSet = (RIPolRecordSet) tRiImportAct
				.getSchemaSet();

		if (tRIPolRecordSet == null || tRIPolRecordSet.size() == 0) {
			System.out.println("导入数据为空！");
			return false;
		}

		// 批次号
		batchno = PubFun1.CreateMaxNo("RIWFLOG", 10);
		System.out.println("生成batchno========" + batchno);

		if (tRIPolRecordSet != null && tRIPolRecordSet.size() > 0) {
			RIWFLogSchema tRIWFLogSchema = new RIWFLogSchema();
			// 生成日志表
			tRIWFLogSchema.setBatchNo(batchno);
			tRIWFLogSchema.setTaskCode(tRIPolRecordSet.get(1)
					.getAccumulateDefNO());
			tRIWFLogSchema.setTaskType("01");
			tRIWFLogSchema.setNodeState("02");
			tRIWFLogSchema.setStartDate("2010-1-1");
			tRIWFLogSchema.setEndDate("2010-12-31");
			tRIWFLogSchema.setOperator(mGlobalInput.Operator);
			tRIWFLogSchema.setManageCom(mGlobalInput.ManageCom);
			tRIWFLogSchema.setMakeDate(PubFun.getCurrentDate());
			tRIWFLogSchema.setMakeTime(PubFun.getCurrentTime());
			tRIWFLogSchema.setModifyDate(PubFun.getCurrentDate());
			tRIWFLogSchema.setModifyTime(PubFun.getCurrentTime());
			outRIWFLog.add(tRIWFLogSchema);
		}
		for (int i = 0; i < tRIPolRecordSet.size(); i++) {
			RIPolRecordSchema tRIPolRecordSchema = tRIPolRecordSet.get(i + 1);

			tRIPolRecordSchema.setDataFlag("01");
			tRIPolRecordSchema.setNodeState("02");
			tRIPolRecordSchema.setReinsreFlag("00");

			tRIPolRecordSchema.setStandPrem(tRIPolRecordSchema.getPrem());
			tRIPolRecordSchema.setPreStandPrem(tRIPolRecordSchema.getPrePrem());
			tRIPolRecordSchema.setEndDate(PubFun.getCurrentDate());
			tRIPolRecordSchema.setStandGetMoney(tRIPolRecordSchema
					.getClmGetMoney());
			tRIPolRecordSchema.setGrpContNo(tRIPolRecordSchema.getContNo());
			tRIPolRecordSchema.setProposalGrpContNo(tRIPolRecordSchema
					.getContNo());
			tRIPolRecordSchema.setProposalNo(tRIPolRecordSchema.getPolNo());
			tRIPolRecordSchema.setUnionKey(tRIPolRecordSchema.getEventNo());

			tRIPolRecordSchema.setMakeDate(PubFun.getCurrentDate());
			tRIPolRecordSchema.setMakeTime(PubFun.getCurrentTime());
			tRIPolRecordSchema.setManageCom("8600");

			String tTaskCode = tRIPolRecordSchema.getAccumulateDefNO();
			tRIPolRecordSchema.setRiskAmnt(tRIPolRecordSchema.getAmnt());
			tRIPolRecordSchema.setPreRiskAmnt(tRIPolRecordSchema.getPreAmnt());
			// 转化后的当前风险保额
			tRIPolRecordSchema.setPreChRiskAmnt(tRIPolRecordSchema.getPreAmnt()
					* tRIPolRecordSchema.getChangeRate());
			// 转化后的历史风险保额
			tRIPolRecordSchema.setChRiskAmnt(tRIPolRecordSchema.getAmnt()
					* tRIPolRecordSchema.getChangeRate());

			// 修改业务数据
			tRIPolRecordSchema.setBatchNo(batchno);

			if ("L000000006".equals(tTaskCode)) {
				tRIPolRecordSchema.setRecordType("02");// 共保
				tRIPolRecordSchema.setRiskAmnt(8000 > tRIPolRecordSchema
						.getAmnt() ? tRIPolRecordSchema.getAmnt()
						: 1.02 * tRIPolRecordSchema.getAmnt());
				tRIPolRecordSchema.setPreRiskAmnt(8000 > tRIPolRecordSchema
						.getPreAmnt() ? tRIPolRecordSchema.getPreAmnt()
						: 1.02 * tRIPolRecordSchema.getPreAmnt());

				System.out.println("========="
						+ tRIPolRecordSchema.getCValiDate());
				System.out.println(PubFun.checkDate("2010-12-31",
						tRIPolRecordSchema.getCValiDate()));
				if (!PubFun.checkDate("2010-12-31",
						tRIPolRecordSchema.getCValiDate()))
					continue;
			}
			tRIPolRecordSchema.setMakeDate(PubFun.getCurrentDate());
			tRIPolRecordSchema.setMakeTime(PubFun.getCurrentTime());
			tRIPolRecordSchema.setPeakLine(0);
			if ("L000000001".equals(tTaskCode)) {
				String riskcode = tRIPolRecordSchema.getRiskCode();
				String tSQL = "select nvl((select Cashvalue from ";
				ExeSQL tExeSQL = new ExeSQL();
				String cv = "0";
				if ("BWL".equals(riskcode)) {
					tSQL = tSQL + "CV_BWL where age='"
							+ tRIPolRecordSchema.getInsuredAge()
							+ "' and Duration='"
							+ tRIPolRecordSchema.getInsuredYear()
							+ "' ),0) from dual ";
					cv = tExeSQL.getOneValue(tSQL);
				} else if ("BWLT".equals(riskcode)) {
					tSQL = tSQL + "CV_BWLT where age='"
							+ tRIPolRecordSchema.getInsuredAge()
							+ "' and Duration='"
							+ tRIPolRecordSchema.getInsuredYear()
							+ "' ),0) from dual ";
					cv = tExeSQL.getOneValue(tSQL);

				} else if ("EMF55".equals(riskcode)) {
					tSQL = tSQL + "CV_EMF55 where age='"
							+ tRIPolRecordSchema.getInsuredAge()
							+ "' and Duration='"
							+ tRIPolRecordSchema.getInsuredYear()
							+ "' ),0) from dual ";
					cv = tExeSQL.getOneValue(tSQL);
				}
				if (cv != null && !"".equals(cv)) {
					tRIPolRecordSchema.setPeakLine(cv);
					tRIPolRecordSchema.setRiskAmnt(tRIPolRecordSchema.getAmnt()
							- tRIPolRecordSchema.getPeakLine());
					tRIPolRecordSchema.setPreRiskAmnt(tRIPolRecordSchema
							.getPreAmnt() - tRIPolRecordSchema.getPeakLine());
				} else {
					tRIPolRecordSchema.setPeakLine(0);
				}

			}
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa: "
					+ tRIPolRecordSchema.getInsuYearFlag());
			outRIPolRecordSet.add(tRIPolRecordSchema);

		}
		return true;
	}

	/**
	 * 往后台数据
	 * */
	public boolean prepareOutputData() {
		try {
			mInputData.clear();
			if (outRIPolRecordSet != null && outRIPolRecordSet.size() != 0)
				mMap.put(outRIPolRecordSet, "INSERT");
			if (outRIWFLog != null && outRIWFLog.size() != 0)
				mMap.put(outRIWFLog, "INSERT");
			mInputData.add(mMap);
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "RiDataImpBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String args[]) {
		System.out.println(PubFun.checkDate("2011-1-1", "2011-1-1"));
	}
}
