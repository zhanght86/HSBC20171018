package com.sinosoft.lis.fininterface.checkdata;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FIAboriginalDataDB;
import com.sinosoft.lis.db.FIRuleDealErrLogDB;
import com.sinosoft.lis.fininterface.utility.FinCreateSerialNo;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FIAboriginalDataBSchema;
import com.sinosoft.lis.schema.FIRuleDealErrLogSchema;
import com.sinosoft.lis.vschema.FIAboriginalDataBSet;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/*******************************************************************************
 * 错误数据处理 处理逻辑：1、根据前台得到批次信息查询日志表 2、根据日志信息得到明细错误信息 3、根据错误信息对应的关联号码得到对应的业务数据
 * 4、执行删除操作并按照组合键方式删除对应的提数日志表以便后续重新处理
 * 
 * @author lijs
 * @createTime 2008-08-26
 * 
 */
public class FIRuleDealErrDataBL  implements BusinessService{
private static Logger logger = Logger.getLogger(FIRuleDealErrDataBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/***/
	private FIRuleDealErrLogSchema mFIRuleDealErrLogSchema = new FIRuleDealErrLogSchema();

	private Reflections mReflections = new Reflections();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FIRuleDealErrDataBL oDataBL = new FIRuleDealErrDataBL();
		VData oData = new VData();

		FIRuleDealErrLogSchema oDealErrLogSchema = new FIRuleDealErrLogSchema();
		oDealErrLogSchema.setErrSerialNo("111111");

		logger.debug(oDealErrLogSchema.encode());
		oData.addElement(oDealErrLogSchema);
		oData.addElement("11");

		oDataBL.submitData(oData, "Test");
	}

	private boolean getInputData(VData oData) {

		mFIRuleDealErrLogSchema.setSchema((FIRuleDealErrLogSchema) oData
				.getObjectByObjectName("FIRuleDealErrLogSchema", 0));
		if (mFIRuleDealErrLogSchema == null) {
			buildError("getInputData", "前台传入的待删除数据为空!");
			return false;
		}

		return true;
	}

	/***************************************************************************
	 * 提供一条错误日志信息记录 根据对应的该笔信息记录进行删除对应的数据和备份数据 并同时修改错误数据处理状态
	 * 
	 * @param oVData
	 * @param strOperator
	 * @return
	 */
	public boolean submitData(VData oVData, String strOperator) {

		/***********************************************************************
		 * 得到对应的错误数据源
		 */
		if (!getInputData(oVData)) {
			return false;
		}

		if (!DealData()) {

			return false;
		}

		return true;
	}

	/***************************************************************************
	 * 根据对应的明细批次信息以及对应的业务号码\凭证类型得到对应的错误数据
	 * 
	 * @return
	 */
	private boolean DealData() {

		if (mFIRuleDealErrLogSchema != null) {
			try {

				FIRuleDealErrLogDB oFIRuleDealErrLogDB = new FIRuleDealErrLogDB();
				oFIRuleDealErrLogDB.setSchema(mFIRuleDealErrLogSchema);

				if (oFIRuleDealErrLogDB.getInfo()) {
					// 表示存在数据
					// }
					if(oFIRuleDealErrLogDB.getDealState() != null
							&& oFIRuleDealErrLogDB.getDealState().equals("1")){
						//表示已经处理完毕
						buildError("DealData", "处理数据失败: 该笔数据已经处理完毕!");
						return false;
					}else{
					String strCertificateID = oFIRuleDealErrLogDB
							.getCertificateID();
					String strBusinessNo = oFIRuleDealErrLogDB.getbusinessno();
					String strBatchNo = oFIRuleDealErrLogDB
							.getDataSourceBatchNo();

					FIAboriginalDataDB oFIAboriginalDataDB = new FIAboriginalDataDB();
					oFIAboriginalDataDB.setCertificateID(strCertificateID);
					oFIAboriginalDataDB.setBusinessNo(strBusinessNo);
					oFIAboriginalDataDB.setBatchNo(strBatchNo);
					FIAboriginalDataSet oFIAboriginalDataSet = oFIAboriginalDataDB
							.query();
					FIAboriginalDataBSet oFIAboriginalDataTempSet = new FIAboriginalDataBSet();

					String strUpdateInfo = "update FIRuleDealErrLog set DealState = '1' where ErrSerialNo = '?ErrSerialNo?'";
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					sqlbv.sql(strUpdateInfo);
					sqlbv.put("ErrSerialNo", oFIRuleDealErrLogDB.getErrSerialNo().trim());
					String strDelInter = "delete from FIAboriginalData where batchno = '?strBatchNo?' "
							+ " and businessno = '?strBusinessNo?' and certificateid = '?strCertificateID?'";
					SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
					sqlbv1.sql(strDelInter);
					sqlbv1.put("strBatchNo", strBatchNo);
					sqlbv1.put("strBusinessNo", strBusinessNo);
					sqlbv1.put("strCertificateID", strCertificateID);
					String strDelInfo = "";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					ArrayList<String> strArr=new ArrayList<String>();
					boolean DelFlag = false;
					if (oFIAboriginalDataSet.size() > 0) {
						strDelInfo = "delete from FIDataDistilledInfo where ASerialNo in('?ASerialNo?')";

						DelFlag = true;
						for (int i = 1; i <= oFIAboriginalDataSet.size(); i++) {

							strArr.add(oFIAboriginalDataSet.get(i).getASerialNo());
							
//							if (i < oFIAboriginalDataSet.size()) {
//
//								strDelInfo = strDelInfo
//										+ "'"
//										+ oFIAboriginalDataSet.get(i)
//												.getASerialNo() + "',";
//							} else {
//								strDelInfo = strDelInfo
//										+ "'"
//										+ oFIAboriginalDataSet.get(i)
//												.getASerialNo() + "')";
//							}
						}
					}
						sqlbv2.sql(strDelInfo);
						sqlbv2.put("ASerialNo", strArr);
					String[] strSerialNo = FinCreateSerialNo.getSerialNo(
							"DelSerialNo", oFIAboriginalDataSet.size(), 25);

					for (int i = 1; i <= oFIAboriginalDataSet.size(); i++) {

						FIAboriginalDataBSchema oAboriginalDataTempSchema = new FIAboriginalDataBSchema();

						mReflections.transFields(oAboriginalDataTempSchema,
								oFIAboriginalDataSet.get(i));
						oAboriginalDataTempSchema.setDelReason("错误数据处理");
						oAboriginalDataTempSchema
								.setDelSerialNo(strSerialNo[i - 1]);
						oFIAboriginalDataTempSet.add(oAboriginalDataTempSchema);
					}

					PubSubmit oPubSubmit = new PubSubmit();
					MMap oMap = new MMap();
					oMap.put(sqlbv, "UPDATE");
					oMap.put(sqlbv1, "DELETE");
					if (DelFlag) {
						oMap.put(sqlbv2, "DELETE");
					}
					oMap.put(oFIAboriginalDataTempSet, "INSERT");

					logger.debug(strUpdateInfo + " -- update --");
					logger.debug(strDelInter + " -- DELETE --");
					logger.debug(strDelInfo + " -- DELETE --");

					logger.debug(oFIAboriginalDataTempSet.size()
							+ " -- INSERT --");

					VData oData = new VData();
					oData.add(oMap);

					if (oPubSubmit.submitData(oData, "DELETE")) {

						// 删除成功
					} else {
						// 删除失败
						return false;
					}
					}
				} else {
					// 没有得到具体数据
				}
			} catch (Exception e) {
				buildError("DealData", "处理数据失败: " + e.getMessage());
				return false;
			}
		}

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "FIRuleDealErrDataBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		logger.debug(szErrMsg);
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	public VData getResult() {
		return null;
	}

}
