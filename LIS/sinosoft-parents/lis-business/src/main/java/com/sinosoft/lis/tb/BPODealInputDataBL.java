package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.BPOCheckCalModeDB;
import com.sinosoft.lis.db.BPOMissionStateDB;
import com.sinosoft.lis.db.BPOServerInfoDB;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDImpartDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.BPOCheckCalModeSchema;
import com.sinosoft.lis.schema.BPOMissionDetailErrorSchema;
import com.sinosoft.lis.schema.BPOMissionDetailStateSchema;
import com.sinosoft.lis.schema.BPOMissionStateSchema;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntIndSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LLAccountSchema;
import com.sinosoft.lis.vschema.BPOMissionDetailErrorSet;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCCustomerImpartDetailSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.SaveOriginalAfterEndService;

/**
 * <p>
 * Title: BPODealInputDataBL
 * </p>
 * <p>
 * Description: 录入外包数据导入处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ln
 * @version 1.0
 */

public class BPODealInputDataBL {
private static Logger logger = Logger.getLogger(BPODealInputDataBL.class);
	/** 数据处理类 */
	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private TransferData mSuccPolNoData = new TransferData();// 成功导入到系统的保单号集合
	/** 需要处理的数据文件集合 */
	private String[] mNeedDealFiles;
	/** 已经成功处理的数据文件集合 */
	private String[] mDealFiles;

	/**
	 * 如果没有定义外保方：外包返回保单数据路径集合 added 2008
	 */
	private String[] mBPOInputPaths;

	/** 如果定义外保方：外包返回保单数据路径 */
	private String mBPOInputPath = "";

	/**
	 * 外保方 added 2008
	 */
	private String tOperator = "";

	/** 一批最大处理文件数 */
	private int mBPOMaxCount = 1;

	/** XML标签常量 */
	private static final String XML_ONEPOLDATA = "OnePolData";

	public BPODealInputDataBL() {
	}

	public void setBPOInputPath(String tBPOInputPath)
	{
		this.mBPOInputPath = tBPOInputPath;
	}
	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// modified by ln 2008.5.27
		/**
		 * tOperator为自动运行定义的参数 如果定义了参数则执行else:读取该外包方下的文件
		 * 如果没有定义则执行if:循环执行所有外保方下的文件
		 */
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		tOperator = (String) tTransferData.getValueByName("tOperator");
		//test
// 		tOperator = "WB0001";

		if (tOperator == null || "".equals(tOperator)) {
			logger.debug("******BPO:N ");
			if (!getConfigDataN())
				return false;

			if (!DealInputDataN())
				return false;

		} else {
			if (!getInputData())
				return false;

			// 处理数据文件
			if (!dealData())
				return false;
		}

		return true;
		// modified by ln 2008.5.27
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 循环处理每一个数据文件
			logger.debug("******需要处理的文件数量 : " + mNeedDealFiles.length);
			for (int i = 0; i < mNeedDealFiles.length; i++) {
				//tongmeng 2009-02-09 add
		    	//增加自动运行时间限制
		    	if(!PubFun.checkAutoRunControl(""))
		    	{
		    		continue;
		    	}
				
				String tFileName = mNeedDealFiles[i];
				logger.debug("******FileName: " + tFileName);
				if (tFileName == null || "".equals(tFileName)) {
					continue;
				}
				// 产生每一个数据文件对应的Document
				Document tOneFileData = TBPubFun.produceXmlDoc(mBPOInputPath
						+ tFileName);
				if (tOneFileData != null) {
					if (!DealOneFile(tOneFileData, tFileName))
						return false;
				}
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * DealInputDataN added by ln 2008.5.23 实现对所有的外保方进行循环处理的功能 从输入数据中得到所有外保方路径
	 * 输出：如果所有路径下都没有需要处理的文件或处理过程出错，则返回false,否则返回true
	 */
	public boolean DealInputDataN() {
		// added by ln 2008.5.27
		try {
			// 是否有需要处理的数据文件的标志
			boolean state1 = false;
			// 是否有需要处理的数据的标志
			boolean state2 = false;

			// 取得一定数量的外包方返回的文件
			for (int i = 0; i < mBPOInputPaths.length; i++) {
				mBPOInputPath = mBPOInputPaths[i];
				mNeedDealFiles = TBPubFun.getFilesList(mBPOInputPath,
						mBPOMaxCount);
				if (mNeedDealFiles != null) {
					state1 = true;
				} else
					continue;

				if (mNeedDealFiles.length != 0) {
					state2 = true;

					// 处理数据
					logger.debug("****系统定义外包方数据返回的路径：  " + mBPOInputPath);
					// 处理数据文件
					if (!dealData())
						return false;

				}
			}

			if (state1 == false) {
				CError.buildErr(this, "没有需要处理的数据文件或者系统定义外包方数据返回的路径错误！");
				return false;
			}

			if (state2 == false) {
				CError.buildErr(this, "没有需要处理的数据！");
				return false;
			}

			return true;

		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}

	}

	// added by ln 2008.5.27

	/**
	 * 
	 * @param tAllBOPPol
	 * @param tOneFileData
	 * @return
	 */
	private boolean getAllBOPPolData(BPODataContainer tAllBOPPol,
			Document tOneFileData) {
		try {
			TBXMLTransfer TBXMLTransfer1 = new TBXMLTransfer(); // 将XML转换为VData
			BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
			VData schemaData = new VData();

			// 转化并保存每一张保单的数据
			List tAllPolData = tOneFileData.getRootElement().getChildren(
					XML_ONEPOLDATA);
			int tPolCount = tAllPolData.size();
			logger.debug("***保单数： " + tPolCount + "");
			// 1.循环处理每一张保单的数据
			for (int i = 0; i < tPolCount; i++) {
				Element tOnePolData = (Element) tAllPolData.get(i);
				if (tOnePolData != null) {
					schemaData = new VData();
					String tBussNo = ""; // 业务号，承保时是印刷号
					schemaData = TBXMLTransfer1.XMLToVData(tOnePolData);
					if (schemaData == null) {
						mErrors.copyAllErrors(TBXMLTransfer1.mErrors);
						return false;
					}
					tBPOMissionStateSchema = (BPOMissionStateSchema) schemaData
							.getObjectByObjectName("BPOMissionStateSchema", 0);
					tBussNo = tBPOMissionStateSchema.getBussNo();
					// 添家银代万能临时校验
					// if(!tBussNo.substring(2, 4).equals("35"))
					// {
					// CError tError = new CError();
					// tError.moduleName = "BPODealInputDataBL";
					// tError.functionName = "getAllBOPData";
					// tError.errorMessage = "万能险系统出现错误的印刷号:"+tBussNo;
					// logger.debug("万能险系统出现错误的印刷号:"+tBussNo);
					// return false;
					// }
					// 保存主险号
					String tMainRisk = getMainRiskCode(schemaData);
					// 保存每一个保单的VData和XML
					tAllBOPPol.add(tBussNo, schemaData, tOnePolData, tMainRisk);
				}
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 得到所有已经在核心业务系统中存在的保单（可能是手工录入）
	 * 
	 * @param tAllBOPPol
	 * @param tImportedBOPPol
	 * @return
	 */
	private boolean getImportedBOPPolData(BPODataContainer tAllBOPPol,
			BPODataContainer tImportedBOPPol) {
		try {
			// 获得所有的业务号码集合（承保时是印刷号）
			Vector tBussNoData = tAllBOPPol.getBussNoData();
			Vector tRiskNoData = tAllBOPPol.getRiskNoData();
			String[] tDelBussNoSet = new String[tBussNoData.size()]; // 记录需要删除的保单的印刷号
			int k = 0; // 计数
			logger.debug("***所有保单数量(处理getImportedBOPPolData前)： "
					+ tBussNoData.size());

			if (tBussNoData.size() == 0) {
				logger.debug("***所有保单数量为0，无需处理已经导入的保单(getImportedBOPPolData处理前)： "
								+ tBussNoData.size());
				return true;
			}
			for (int i = 0; i < tBussNoData.size(); i++) {
				String tBussNo = (String) tBussNoData.get(i);
				logger.debug("tBussNo: " + i + "  " + tBussNo);
				// 如果该保单返回之前已经被人手工录入
				ExeSQL tExeSQL = new ExeSQL();
				 String SQL = "select 1 from LCCont where PrtNo='"+"?tBussNo1?"+"'"
				 +" union "
				 +" select 1 from BPOMissionState where BussNo='"+"?tBussNo2?"+"' and BussNoType='TB' and State <>'2'"; // 不考虑已删除的异常件
				 // 查看工作流节点是否是处于投保单录入外包状态,如果不在此状态,当作已处理保单 weikai
				// String SQL = "select 1 from lwmission where missionprop1='"
				// + tBussNo
				// + "' and processid='0000000003'and activityid='0000001089'";
				 SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(SQL);
					sqlbv.put("tBussNo1", tBussNo);
					sqlbv.put("tBussNo2", tBussNo);
				 SSRS tSSRS = tExeSQL.execSQL(sqlbv);
			     int max=tSSRS.getMaxRow();
				logger.debug("***" + tBussNo
						+ "（是否已经处理标志：flag 0-没有处理 否则为已处理）：：：" + max);
				if(max>0) {
					tImportedBOPPol.add(tBussNo, tAllBOPPol
							.getschemaData(tBussNo), tAllBOPPol
							.getelementData(tBussNo), tRiskNoData.get(i));
					// tAllBOPPol.removeByName(tBussNo);
					tDelBussNoSet[k++] = tBussNo;
				}
			}
			// 从全体保单数据中删除已经导入的保单
			for (int m = 0; m < tDelBussNoSet.length; m++) {
				if (!"".equals(tDelBussNoSet[m])) {
					tAllBOPPol.removeByName(tDelBussNoSet[m]);
				}
			}

			tBussNoData = tAllBOPPol.getBussNoData();
			logger.debug("***所有保单数量getImportedBOPPolData(处理后)： "
					+ tBussNoData.size());
			Vector tBussNoData1 = tImportedBOPPol.getBussNoData();
			logger.debug("***已经在系统存在的保单数量(getImportedBOPPolData)： "
					+ tBussNoData1.size());
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 从原始得到异常件
	 * 
	 * @param tAllBOPPol
	 * @param tQuestBOPPol
	 * @return
	 */
	private boolean getQuestBOPPolData(BPODataContainer tAllBOPPol,
			BPODataContainer tQuestBOPPol) {
		try {
			// 获得所有的业务号码集合（承保时是印刷号）
			Vector tBussNoData = tAllBOPPol.getBussNoData();
			Vector tRiskNoData = tAllBOPPol.getRiskNoData();
			if (tBussNoData.size() == 0) {
				logger.debug("***所有保单数量为0，无需处理异常件(getQuestBOPPolData处理前)： "
								+ tBussNoData.size());
				return true;
			}
			String[] tDelBussNoSet = new String[tBussNoData.size()]; // 记录需要删除的保单的印刷号
			int k = 0; // 计数
			VData tVData;
			BPOMissionStateSchema tBPOMissionStateSchema;
			logger.debug("***所有保单数量(getQuestBOPPolData处理前)： "
					+ tBussNoData.size());
			for (int i = 0; i < tBussNoData.size(); i++) {
				// 校验是否是异常件
				String tBussNo = (String) tBussNoData.get(i);
				tVData = (VData) tAllBOPPol.getschemaData(tBussNo);
				tBPOMissionStateSchema = (BPOMissionStateSchema) tVData
						.getObjectByObjectName("BPOMissionStateSchema", 0);
				// 外包方返回可处理异常件 和 外包方返回不可处理的异常件
				if ( tBPOMissionStateSchema.getDealType()!=null && !tBPOMissionStateSchema.getDealType().equals("")
						&& ("02".equals(tBPOMissionStateSchema.getDealType())
						|| "03".equals(tBPOMissionStateSchema.getDealType()))) {
					tQuestBOPPol.add(tBussNo,
							tAllBOPPol.getschemaData(tBussNo), tAllBOPPol
									.getelementData(tBussNo), tRiskNoData
									.get(i));
					// tAllBOPPol.removeByName(tBussNo);
					tDelBussNoSet[k++] = tBussNo;
				}
			}
			// 从全体保单数据中删除异常件
			for (int m = 0; m < tDelBussNoSet.length; m++) {
				if (!"".equals(tDelBussNoSet[m])) {
					tAllBOPPol.removeByName(tDelBussNoSet[m]);
				}
			}
			tBussNoData = tAllBOPPol.getBussNoData();
			logger.debug("***所有保单数量(getQuestBOPPolData处理后)： "
					+ tBussNoData.size());
			Vector tBussNoData1 = tQuestBOPPol.getBussNoData();
			logger.debug("***原始异常件数量： " + tBussNoData1.size());
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param tAllBOPPol
	 * @param tQuestBOPPol
	 * @return
	 */
	private boolean getNeedCheckBOPPolData(String tBPOID,
			BPODataContainer tAllBOPPol, BPODataContainer tNeedCheckBOPPol) {
		try {
			// 此函数处理分两部：1。准备满足抽检条件的保单数据 2。按照抽检规则从满足抽检条件的保单中确定抽检件
			// 1.准备满足抽检条件的保单数据 :如果抽检条件中没有险种的限制，则取全部传入的清洁件；如果有险种限制，将包含该险种的保单挑选出来
			// 判断是否存在复核抽检规则
			BPODataContainer tAllCheckBOPPol = new BPODataContainer(); // 可以抽检的全部保单集合
			// ，其含义为：可以抽检的全部保单集合
			// ＝
			// 清洁件－已经在核心业务系统中的保单
			BPOCheckCalModeSchema tBPOCheckCalModeSchema = new BPOCheckCalModeSchema();
			BPOCheckCalModeDB tBPOCheckCalModeDB = new BPOCheckCalModeDB();
			tBPOCheckCalModeDB.setBPOID(tBPOID);
			tBPOCheckCalModeDB.setBussNoType("TB");
			// 如果没有抽检规则，则不需要抽检
			if (!tBPOCheckCalModeDB.getInfo()) {
				logger.debug("***" + tBPOID + "没有抽检规则，本次不需要抽检");
				return true;
			}
			tBPOCheckCalModeSchema.setSchema(tBPOCheckCalModeDB.getSchema());

			// 判断当天抽检保单数量是否已经达到最大抽检数量
			int tMaxNum = tBPOCheckCalModeSchema.getMaxMum(); // 抽检上限
			float tRate = (float) tBPOCheckCalModeSchema.getRate(); // 抽检比例
			logger.debug("***抽查上限 : " + tMaxNum);
			logger.debug("***抽查比例 : " + tRate);
			// 如果抽检上限为0，则不需要抽检
			if (tMaxNum < 1) {
				logger.debug("***抽检上限小于1，本次不需要抽检");
				return true;
			}
			ExeSQL tExeSQL = new ExeSQL();
			String tSQL = "select count(1) from BPOMissionState where DealType = '01'"
				    + " and bussnotype='TB'"
				    + " and bpoid='"
					+ "?tBPOID?"
					+ "' and MakeDate = '"
					+ "?CurrentDate?"
					+ "'";
			
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("tBPOID", tBPOID);
			sqlbv.put("CurrentDate", PubFun.getCurrentDate());
			
			int tAlreadyCheckCount = Integer
					.parseInt(tExeSQL.getOneValue(sqlbv));// 当天已经确定为抽检件的保单数量
			logger.debug("***当天已经确定为抽检件的保单数量 : " + tAlreadyCheckCount);
			if (tAlreadyCheckCount >= tMaxNum) {
				logger.debug("***当天已经确定为抽检件的保单数量达到抽检上限，本次不需要抽检");
				return true;
			}
			// 当天最大还需要抽检的保单数量
			int tNeedCheckCount = tMaxNum - tAlreadyCheckCount;

			// 获得所有的业务号码集合（承保时是印刷号）
			Vector tBussNoData = tAllBOPPol.getBussNoData();
			Vector tRiskNoData = tAllBOPPol.getRiskNoData();
			logger.debug("***所有保单数量(getNeedCheckBOPPolData处理前)： "
					+ tBussNoData.size());
			if (tBussNoData.size() == 0) {
				logger.debug("***所有保单数量为0，无需处理异常件(getQuestBOPPolData处理前)： "
								+ tBussNoData.size());
				return true;
			}
			logger.debug("***tBPOCheckCalModeSchema.ManageCom : "
					+ StrTool.cTrim(tBPOCheckCalModeSchema.getManageCom()));
			logger.debug("***tBPOCheckCalModeSchema.RiskCode : "
					+ StrTool.cTrim(tBPOCheckCalModeSchema.getRiskCode()));
			// 如果抽检不区分管理机构
			if ( "".equals(StrTool.cTrim(tBPOCheckCalModeSchema.getManageCom()))) {
				// 如果不区分险种，则抽检保单全集为此函数参数传入的所有清洁件
				if ("".equals(StrTool.cTrim(tBPOCheckCalModeSchema
						.getRiskCode()))) {
					for (int i = 0; i < tBussNoData.size(); i++) {
						String tBussNo = (String) tBussNoData.get(i);
						tAllCheckBOPPol.add(tBussNo, tAllBOPPol
								.getschemaData(tBussNo), tAllBOPPol
								.getelementData(tBussNo), tRiskNoData.get(i));
					}
				} else // 如果区分险种，则选出本批次中包含该险种的保单信息
				{
					String tCheckRiskcode = StrTool
							.cTrim(tBPOCheckCalModeSchema.getRiskCode());
					VData tVData;
					VData tInsuredRelaSet;
					VData tRiskBasicInfoMainSet;
					VData tRiskBasicInfoSet;
					RiskBasicInfo tRiskBasicInfo;

					for (int i = 0; i < tBussNoData.size(); i++) {
						String tBussNo = (String) tBussNoData.get(i);
						tVData = new VData();
						tInsuredRelaSet = new VData();						
						tVData = (VData) tAllBOPPol.getschemaData(tBussNo);
						tInsuredRelaSet = (VData) tVData.getObjectByObjectName(
								"VData", 0);
						if (tInsuredRelaSet != null) {
							for (int j = 0; j < tInsuredRelaSet.size(); j++) {
								VData tInsuredSet = new VData();
								tInsuredSet = (VData) tInsuredRelaSet
										.get(j);
								if (tInsuredSet != null && tInsuredSet.size()>0) {
									tRiskBasicInfoMainSet = new VData();
									tRiskBasicInfoMainSet = (VData) tInsuredSet
									.getObjectByObjectName("VData", 3);
									if (tRiskBasicInfoMainSet != null) {
										for (int k = 0; k < tRiskBasicInfoMainSet
												.size(); k++) {
											tRiskBasicInfoSet = new VData();
											tRiskBasicInfoSet = (VData) tRiskBasicInfoMainSet
													.get(k);
											if (tRiskBasicInfoSet != null) {
												for (int n = 0; n < tRiskBasicInfoSet
														.size(); n++) {
													tRiskBasicInfo = new RiskBasicInfo();
													tRiskBasicInfo = (RiskBasicInfo) tRiskBasicInfoSet
															.get(n);
													if (tCheckRiskcode
															.equals(tRiskBasicInfo
																	.getRiskCode()
																	.trim())) {
														tAllCheckBOPPol
																.add(
																		tBussNo,
																		tAllBOPPol
																				.getschemaData(tBussNo),
																		tAllBOPPol
																				.getelementData(tBussNo),
																		tRiskNoData
																				.get(i));
														break;
													}
												}
											}
		
										}
									}
								}
							}
						}

					}
				}
			} else {
				String tManageCom = StrTool.cTrim(tBPOCheckCalModeSchema
						.getManageCom());
				String tRiskCode = StrTool.cTrim(tBPOCheckCalModeSchema
						.getRiskCode());

				if ("".equals(tRiskCode)) // 当险种代码为空时
				{
					for (int i = 0; i < tBussNoData.size(); i++) {
						String tBussNo = (String) tBussNoData.get(i);
						VData tVData = (VData) tAllBOPPol
								.getschemaData(tBussNo);
						LCPolSchema tLCPolSchema = (LCPolSchema) tVData
								.getObjectByObjectName("LCPolSchema", 0);
						logger.debug("***tLCPolSchema.ManageCom : "
								+ StrTool.cTrim(tLCPolSchema.getManageCom()));
						if (StrTool.cTrim(tLCPolSchema.getManageCom())
								.startsWith(tManageCom)) {
							tAllCheckBOPPol.add(tBussNo, tAllBOPPol
									.getschemaData(tBussNo), tAllBOPPol
									.getelementData(tBussNo), tRiskNoData
									.get(i));
						}
					}
				} else // 当险种不为空时
				{
					String tCheckRiskcode = StrTool
							.cTrim(tBPOCheckCalModeSchema.getRiskCode());
					VData tVData;
					VData tInsuredRelaSet;
					VData tRiskBasicInfoMainSet;
					VData tRiskBasicInfoSet;
					RiskBasicInfo tRiskBasicInfo;

					for (int i = 0; i < tBussNoData.size(); i++) {
						int tRiskFlag = 0;
						String tBussNo = (String) tBussNoData.get(i);
						tVData = new VData();
						tInsuredRelaSet = new VData();						
						tVData = (VData) tAllBOPPol.getschemaData(tBussNo);
						LCPolSchema tLCPolSchema = (LCPolSchema) tVData
						.getObjectByObjectName("LCPolSchema", 0);
						tInsuredRelaSet = (VData) tVData.getObjectByObjectName(
								"VData", 0);
						if (tInsuredRelaSet != null) {
							for (int j = 0; j < tInsuredRelaSet.size(); j++) {
								VData tInsuredSet = new VData();
								tInsuredSet = (VData) tInsuredRelaSet
										.get(j);
								if (tInsuredSet != null && tInsuredSet.size()>0) {
									tRiskBasicInfoMainSet = new VData();
									tRiskBasicInfoMainSet = (VData) tInsuredSet
									.getObjectByObjectName("VData", 3);
									if (tRiskBasicInfoMainSet != null) {
										for (int k = 0; k < tRiskBasicInfoMainSet
												.size(); k++) {
											tRiskBasicInfoSet = new VData();
											tRiskBasicInfoSet = (VData) tRiskBasicInfoMainSet
													.get(k);
											if (tRiskBasicInfoSet != null) {
												for (int n = 0; n < tRiskBasicInfoSet
														.size(); n++) {
													tRiskBasicInfo = new RiskBasicInfo();
													tRiskBasicInfo = (RiskBasicInfo) tRiskBasicInfoSet
															.get(n);
													if (tCheckRiskcode
															.equals(tRiskBasicInfo
																	.getRiskCode()
																	.trim())) {
														tRiskFlag = 1;														
														break;
													}
												}
											}
		
										}
									}
								}
							}
						}
						logger.debug("***tLCPolSchema.ManageCom : "+StrTool.cTrim(tLCPolSchema.getManageCom()));
			            logger.debug("***tRiskFlag : "+tRiskFlag);
			            if(StrTool.cTrim(tLCPolSchema.getManageCom()).startsWith(tManageCom) && tRiskFlag == 1)
			            {
			            	tAllCheckBOPPol
							.add(
									tBussNo,
									tAllBOPPol
											.getschemaData(tBussNo),
									tAllBOPPol
											.getelementData(tBussNo),
									tRiskNoData
											.get(i));
			            }

					}
				}
			}

			// 2。按照抽检规则从满足抽检条件的保单中确定抽检件
			Vector tCheckBussNoData = tAllCheckBOPPol.getBussNoData();
			int tAllCheckCount = tCheckBussNoData.size();
			logger.debug("***所有满足抽检条件的保单数量(getNeedCheckBOPPolData处理前)： "
					+ tAllCheckCount);
			if (tAllCheckCount == 0) {
				logger.debug("***本次不需要抽检");
				return true;
			}

			//如果满足抽检条件的保单数量为1，则扩大一百倍进行抽检
			if (tAllCheckCount == 1) {
				tAllCheckCount = tAllCheckCount*100;
			}
			String[] tDelBussNoSet = new String[tBussNoData.size()]; // 记录需要删除的保单的印刷号
			int k = 0; // 计数

			// 确定抽检件
			int tCheckLevel = Math.round(tAllCheckCount * tRate); // 随即抽检基准 ＝
			// 抽检数×抽检比例
			// ，并且按照四舍五入取整
			logger.debug("tCheckLevel: " + tCheckLevel);
			Random tRandom = new Random();

			for (int i = 0; i < tCheckBussNoData.size(); i++) {
				int j = tRandom.nextInt(tAllCheckCount); // 随机产生一个0 到
				// 所有满足抽检条件的保单数量的值，如果该值在 0 与
				// 随即抽检基准 之间，确定为抽检件
				logger.debug("***tBussNo： "
						+ (String) tCheckBussNoData.get(i) + "   RandNo: " + j
						+ "   已确定的抽检数:  " + k);
				if (j <= tCheckLevel && (k + 1) <= tNeedCheckCount
						&& (k + 1) <= tCheckLevel) // 如果抽检数量已经大于”随即抽检基准“，直接跳出
				{
					String tBussNo = (String) tCheckBussNoData.get(i);
					tNeedCheckBOPPol.add(tBussNo, tAllCheckBOPPol
							.getschemaData(tBussNo), tAllCheckBOPPol
							.getelementData(tBussNo), tRiskNoData.get(i));
					// tAllBOPPol.removeByName(tBussNo);
					tDelBussNoSet[k++] = tBussNo;
				}
			}
			// 从全体保单数据中删除抽检件
			for (int m = 0; m < tDelBussNoSet.length; m++) {
				if (!"".equals(tDelBussNoSet[m])) {
					tAllBOPPol.removeByName(tDelBussNoSet[m]);
				}
			}

			tBussNoData = tAllBOPPol.getBussNoData();
			logger.debug("***所有保单数量(getNeedCheckBOPPolData处理后)： "
					+ tBussNoData.size());
			Vector tBussNoData1 = tNeedCheckBOPPol.getBussNoData();
			logger.debug("***抽检件数量： " + tBussNoData1.size());
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 处理已经导入件的保单和抽检件保单
	 * 
	 * @param tImportedBOPPol
	 * @param tNeedCheckBOPPol
	 * @param serNo
	 * @param tGI
	 * @param tBPOID
	 * @return 如果处理出现异常，返回false,否则true
	 */
	private boolean dealImportedAndNeedCheckData(
			BPODataContainer tImportedBOPPol,
			BPODataContainer tNeedCheckBOPPol, String serNo, GlobalInput tGI,
			String tBPOID) {
		try {
			// 1。先处理已经导入过的保单数据
			Vector tBussNoData = tImportedBOPPol.getBussNoData();
			Vector tRiskNoData = tImportedBOPPol.getRiskNoData();
			for (int i = 0; i < tBussNoData.size(); i++) {
				VData tVData = new VData();
				BPODealInputDataBLS tBPODealInputDataBLS = new BPODealInputDataBLS(); // 数据提交类
				BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
				BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = new BPOMissionDetailStateSchema();
				String tBussNo = (String) tBussNoData.get(i);
				tVData = (VData) tImportedBOPPol.getschemaData(tBussNo);
				Element tOnePolData = (Element) tImportedBOPPol
						.getelementData(tBussNo);
				String tMainRisk = (String) tRiskNoData.get(i);
				tBPOMissionStateSchema = (BPOMissionStateSchema) tVData
						.getObjectByObjectName("BPOMissionStateSchema", 0);
				tBPOMissionStateSchema.setDealType("05"); // 05－重复导入
				tBPOMissionStateSchema.setState("1"); // 1-成功导入系统
				tBPOMissionStateSchema.setRiskCode(tMainRisk);
				tBPOMissionStateSchema.setOperator(tGI.Operator);
				tBPOMissionStateSchema.setBPOID(tBPOID);
				tBPOMissionStateSchema.setSerialNo(serNo);
				tBPOMissionStateSchema.setMakeDate(PubFun.getCurrentDate());
				tBPOMissionStateSchema.setMakeTime(PubFun.getCurrentTime());
				tBPOMissionStateSchema.setModifyDate(PubFun.getCurrentDate());
				tBPOMissionStateSchema.setModifyTime(PubFun.getCurrentTime());

				Reflections tReflections = new Reflections();
				// 把对象tBPOMissionStateSchema的值赋给对象tBPOMissionDetailStateSchema
				tReflections.transFields(tBPOMissionDetailStateSchema,
						tBPOMissionStateSchema);
				ExeSQL tExeSQL = new ExeSQL();
				String tSQL = "select case when max(DealCount) is null then 0 else max(DealCount) end from BPOMissionDetailState where BussNo = '"
						+ "?tBussNo?"
						+ "' and BussNoType = 'TB'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSQL);
				sqlbv.put("tBussNo", tBPOMissionStateSchema.getBussNo());
				int tDealCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
				logger.debug("tDealCount: " + tDealCount);
				tBPOMissionDetailStateSchema.setDealCount(tDealCount + 1);
				tVData.add(tBPOMissionDetailStateSchema);
				tVData.add(tGI);

				// 分每一条提交数据库，目的是不要因为其中一条有问题，导致所有的保单无法提交
				if (!tBPODealInputDataBLS.submitData(tVData, tOnePolData)) {
					CError.buildErr(this, tBPOMissionStateSchema.getBussNo()
							+ "已导入的保单数据保存失败："
							+ tBPODealInputDataBLS.mErrors.getFirstError());
				}
			}

			// 2.保存抽检件
			Vector tCheckBussNoData = tNeedCheckBOPPol.getBussNoData();
			Vector tCheckRiskNoData = tNeedCheckBOPPol.getRiskNoData();
			for (int i = 0; i < tCheckBussNoData.size(); i++) {
				VData tVData = new VData();
				BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
				BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = new BPOMissionDetailStateSchema();
				String tBussNo = (String) tCheckBussNoData.get(i);
				tVData = (VData) tNeedCheckBOPPol.getschemaData(tBussNo);
				Element tOnePolData = (Element) tNeedCheckBOPPol
						.getelementData(tBussNo);
				String tMainRisk = (String) tCheckRiskNoData.get(i);
				tBPOMissionStateSchema = (BPOMissionStateSchema) tVData
						.getObjectByObjectName("BPOMissionStateSchema", 0);
				tBPOMissionStateSchema.setDealType("01"); // 01－抽检件
				tBPOMissionStateSchema.setState("0"); // 0－未完成
				tBPOMissionStateSchema.setRiskCode(tMainRisk);
				tBPOMissionStateSchema.setOperator(tGI.Operator);
				tBPOMissionStateSchema.setBPOID(tBPOID);
				tBPOMissionStateSchema.setSerialNo(serNo);
				tBPOMissionStateSchema.setMakeDate(PubFun.getCurrentDate());
				tBPOMissionStateSchema.setMakeTime(PubFun.getCurrentTime());
				tBPOMissionStateSchema.setModifyDate(PubFun.getCurrentDate());
				tBPOMissionStateSchema.setModifyTime(PubFun.getCurrentTime());

				Reflections tReflections = new Reflections();
				// 把对象tBPOMissionStateSchema的值赋给对象tBPOMissionDetailStateSchema
				tReflections.transFields(tBPOMissionDetailStateSchema,
						tBPOMissionStateSchema);
				ExeSQL tExeSQL = new ExeSQL();
				String tSQL = "select case when max(DealCount) is null then 0 else max(DealCount) end from BPOMissionDetailState where BussNo = '"
						+ "?tBussNo?"
						+ "' and BussNoType = 'TB'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSQL);
				sqlbv.put("tBussNo", tBPOMissionStateSchema.getBussNo());
				int tDealCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
				logger.debug("tDealCount: " + tDealCount);
				tBPOMissionDetailStateSchema.setDealCount(tDealCount + 1);
				tVData.add(tBPOMissionDetailStateSchema);
				tVData.add(tGI);

				// 分每一条提交数据库，目的是不要因为其中一条有问题，导致所有的保单无法提交
				BPODealInputDataBLS tBPODealInputDataBLS = new BPODealInputDataBLS(); // 数据提交类
				if (!tBPODealInputDataBLS.submitData(tVData, tOnePolData)) {
					CError.buildErr(this, tBPOMissionStateSchema.getBussNo()
							+ "抽检件保单数据保存失败："
							+ tBPODealInputDataBLS.mErrors.getFirstError());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "处理已经导入件的保单和抽检件保单发生异常！");
			return false;
		}
		return true;
	}

	/**
	 * 处理异常件保单
	 * 
	 * @param tImportedBOPPol
	 * @param tNeedCheckBOPPol
	 * @param serNo
	 * @param tGI
	 * @param tBPOID
	 * @return 如果处理出现异常，返回false,否则true
	 */
	private boolean dealQuestData(BPODataContainer tQuestBOPPol, String serNo,
			GlobalInput tGI, String tBPOID) {
		try {
			Vector tQuestBussNoData = tQuestBOPPol.getBussNoData();
			Vector tQuestRiskNoData = tQuestBOPPol.getRiskNoData();
			for (int i = 0; i < tQuestBussNoData.size(); i++) {
				VData tVData = new VData();
				BPODealInputDataBLS tBPODealInputDataBLS = new BPODealInputDataBLS(); // 数据提交类
				BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
				BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = new BPOMissionDetailStateSchema();
				BPOMissionDetailErrorSet tBPOMissionDetailErrorSet = new BPOMissionDetailErrorSet();
				String tBussNo = (String) tQuestBussNoData.get(i);
				tVData = (VData) tQuestBOPPol.getschemaData(tBussNo);
				Element tOnePolData = (Element) tQuestBOPPol
						.getelementData(tBussNo);
				String tMainRisk = (String) tQuestRiskNoData.get(i);

				// 1.BPOMissionState
				tBPOMissionStateSchema = (BPOMissionStateSchema) tVData
						.getObjectByObjectName("BPOMissionStateSchema", 0);
				tBPOMissionStateSchema.setState("0"); // 0－未完成处理
				// 如果是外包方无法处理的异常件（如整个扫描件无法识别），记录扫描件的主键 Docid
				if (( tBPOMissionStateSchema.getDealType()!=null && !"".equals(tBPOMissionStateSchema.getDealType()))&& "03".equals(tBPOMissionStateSchema.getDealType())) {
					ExeSQL tExeSQL1 = new ExeSQL();
					String tSQL1 = "select min(Docid) from es_doc_main where DocCode = '"
							+ "?tBussNo?"
							+ "' and BussType = 'TB'";
					
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(tSQL1);
					sqlbv.put("tBussNo", tBPOMissionStateSchema.getBussNo());
					tBPOMissionStateSchema.setRemark(tExeSQL1
							.getOneValue(sqlbv));
				}
				tBPOMissionStateSchema.setOperator(tGI.Operator);
				tBPOMissionStateSchema.setBPOID(tBPOID);
				tBPOMissionStateSchema.setSerialNo(serNo);
				tBPOMissionStateSchema.setRiskCode(tMainRisk);
				tBPOMissionStateSchema.setMakeDate(PubFun.getCurrentDate());
				tBPOMissionStateSchema.setMakeTime(PubFun.getCurrentTime());
				tBPOMissionStateSchema.setModifyDate(PubFun.getCurrentDate());
				tBPOMissionStateSchema.setModifyTime(PubFun.getCurrentTime());

				// 2.BPOMissionDetailState
				Reflections tReflections = new Reflections();
				// 把对象tBPOMissionStateSchema的值赋给对象tBPOMissionDetailStateSchema
				tReflections.transFields(tBPOMissionDetailStateSchema,
						tBPOMissionStateSchema);
				ExeSQL tExeSQL = new ExeSQL();
				String tSQL = "select case when max(DealCount) is null then 0 else max(DealCount) end from BPOMissionDetailState where BussNo = '"
						+ "?tBussNo?"
						+ "' and BussNoType = 'TB'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSQL);
				sqlbv.put("tBussNo", tBPOMissionStateSchema.getBussNo());
				int tDealCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
				logger.debug("tDealCount: " + tDealCount);
				tBPOMissionDetailStateSchema.setDealCount(tDealCount + 1);
				tVData.add(tBPOMissionDetailStateSchema);
				tVData.add(tGI);

				// 3.BPOMissionDetailError
				tBPOMissionDetailErrorSet = (BPOMissionDetailErrorSet) tVData
						.getObjectByObjectName("BPOMissionDetailErrorSet", 0);

				if (tBPOMissionDetailErrorSet != null) {
					for (int j = 1; j <= tBPOMissionDetailErrorSet.size(); j++) {
						tBPOMissionDetailErrorSet.get(j).setSerialNo(serNo);
						tBPOMissionDetailErrorSet.get(j).setOperator(
								tGI.Operator);
						tBPOMissionDetailErrorSet.get(j).setMakeDate(
								PubFun.getCurrentDate());
						tBPOMissionDetailErrorSet.get(j).setMakeTime(
								PubFun.getCurrentTime());
					}
				}

				// 分每一条提交数据库，目的是不要因为其中一条有问题，导致所有的保单无法提交
				if (!tBPODealInputDataBLS.submitData(tVData, tOnePolData)) {
					CError.buildErr(this, tBPOMissionStateSchema.getBussNo()
							+ "已导入的保单数据保存失败："
							+ tBPODealInputDataBLS.mErrors.getFirstError());
				}
			}
		} catch (Exception ex) {
			CError.buildErr(this, "处理异常件保单发生异常！");
			return false;
		}
		return true;
	}

	/**
	 * 得到一投保单的主险险种
	 * 
	 * @param tVData
	 * @return
	 */
	public String getMainRiskCode(VData tVData) {
		String tMainRiskCode = "";
		boolean stopFlag = false;
		try {
			VData tInsuredRelaSet = (VData) tVData.getObjectByObjectName(
					"VData", 0);

			if (tInsuredRelaSet != null) {
				for (int i = 0; i < tInsuredRelaSet.size(); i++) {
					if (stopFlag)
						break;
					VData tLCInsuredRelaSet = (VData) tInsuredRelaSet.get(i);
					if (tLCInsuredRelaSet != null && tLCInsuredRelaSet.size() > 0) {
						LCInsuredSet tLCInsuredSet = (LCInsuredSet) tLCInsuredRelaSet
								.getObjectByObjectName("LCInsuredSet", 0);
						if(tLCInsuredSet != null && tLCInsuredSet.size() > 0)
						{
							LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(1);
							if ((tLCInsuredSchema.getSequenceNo()!=null && !tLCInsuredSchema.getSequenceNo().equals("")) && (tLCInsuredSchema.getSequenceNo().equals("1")
									|| tLCInsuredSchema.getSequenceNo().equals("-1"))) {
								VData tRiskBasicInfoMainSet = (VData) tLCInsuredRelaSet
										.getObjectByObjectName("VData", 5);
								if (tRiskBasicInfoMainSet != null) {
									for (int j = 0; j < tRiskBasicInfoMainSet
											.size(); j++) {
										if (stopFlag)
											break;
										VData tRiskBasicInfoSet = (VData) tRiskBasicInfoMainSet
												.get(j);
										if(tRiskBasicInfoSet != null)
										{
											for(int k = 0; k < tRiskBasicInfoSet.size(); k++)
											{
												RiskBasicInfo tRiskBasicInfo = (RiskBasicInfo) tRiskBasicInfoSet
												.get(k);
												if ((tRiskBasicInfo.getRiskNo()!=null && !tRiskBasicInfo.getRiskNo().equals(""))
														&&(tRiskBasicInfo.getRiskNo().equals("1")
														|| tRiskBasicInfo.getRiskNo()
																.equals("-1"))) {
													if (tRiskBasicInfo.isMainRisk()) {
														tMainRiskCode = tRiskBasicInfo
																.getRiskCode();
														stopFlag = true;
														break;
													}
												}
											}
											
										}								
									}
								}
							}
						}					

					}

				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tMainRiskCode;
	}

	/**
	 * 删除一个印刷号下已经导入的险种
	 * 
	 * @param tBussNo
	 *            印刷号
	 * @return 如果处理出现异常，则返回false,否则返回true
	 */
	public boolean redoInputedPol(String tBussNo) {
		try {
			String tSQL = "select count(1) from LCPol where PrtNo='" + "?PrtNo?"
					+ "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("PrtNo", tBussNo);
			ExeSQL tExeSQL = new ExeSQL();
			int tExistCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
			logger.debug("***核心业务系统中已经存在的险种个数：" + tExistCount);

			tSQL = "select count(1) from LCCont where ContNo='" + "?ContNo?" + "'";
			tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("ContNo", tBussNo);
			int tExistContCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
			logger.debug("***本次导入的合同信息个数：" + tExistContCount);

			Vector tImoprtedPol = (Vector) mSuccPolNoData
					.getValueByName(tBussNo);

			if ((tImoprtedPol == null || tImoprtedPol.size() == 0)
					&& tExistContCount == 0) {
				logger.debug("***本次导入的合同以信息及险种个数为0，无需撤销" + tBussNo);
				return true;
			}

			if (tExistContCount > 0) {
				logger.debug("***准备删除部分导入的数据：" + tBussNo);
				MMap tDelMap = prepareDelMap(tBussNo, tImoprtedPol);
				if (tDelMap == null) // 准备数据出现异常
				{
					logger.debug("准备删除部分导入的数据发生异常：" + tBussNo);
					return false;
				}
				VData sVData = new VData();
				sVData.add(tDelMap);
				PubSubmit ps = new PubSubmit();
				if (!ps.submitData(sVData, "")) {
					CError.buildErr(this, tBussNo + "删除部分导入的保单数据失败："
							+ ps.mErrors.getFirstError());
					return false;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 异常件处理：初始化一个印刷号下的合同为未保存状态
	 * 
	 * @param tBussNo
	 *            印刷号
	 * @return 如果处理出现异常，则返回false,否则返回true
	 */
	public boolean redoInputedCont(String tBussNo, String tDealType,
			String tState, GlobalInput tGI) {
		MMap mapDel = new MMap();
		try {
			logger.debug("***准备更新bpomissionstate的状态：" + tBussNo);
			if (!updateBPOState(tBussNo, tDealType,
					tState, tGI)) {
				return false;
			}
			logger.debug("***准备更新已保存的数据的扫描状态：" + tBussNo);
			
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql("update es_doc_main set inputstate=null,inputstartdate=null,inputstarttime=null where doccode='" + "?tBussNo?" + "'");
			sqlbv.put("tBussNo", tBussNo);
			mapDel.put(sqlbv,"UPDATE");
			
			logger.debug("***准备删除已保存的数据：" + tBussNo);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("delete from LCCont where ContNo='"  + "?tBussNo?" + "'");
			sqlbv1.put("tBussNo", tBussNo);
			mapDel.put(sqlbv1,"DELETE");
			
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("delete from LCContState where contno = '"  + "?tBussNo?" + "'");
			sqlbv2.put("tBussNo", tBussNo);
			mapDel.put(sqlbv2,"DELETE");
			
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql("delete from LCAppnt where contno = '"  + "?tBussNo?" + "'");
			sqlbv3.put("tBussNo", tBussNo);
			mapDel.put(sqlbv3,"DELETE");
			
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql("delete from LCInsured where contno = '"  + "?tBussNo?" + "'");
			sqlbv4.put("tBussNo", tBussNo);
			mapDel.put(sqlbv4,"DELETE");
			
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql("delete from LCInsureAcc where contno = '"  + "?tBussNo?" + "'");
			sqlbv5.put("tBussNo", tBussNo);
			mapDel.put(sqlbv5,"DELETE");
			
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql("delete from LCInsureAccClass where contno = '"  + "?tBussNo?" + "'");
			sqlbv6.put("tBussNo", tBussNo);
			mapDel.put(sqlbv6,"DELETE");
			
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql("delete from LCInsureAccTrace where contno = '"  + "?tBussNo?" + "'");
			sqlbv7.put("tBussNo", tBussNo);
			mapDel.put(sqlbv7,"DELETE");
			
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql("delete from LCInsureAccFee where contno = '"  + "?tBussNo?" + "'");
			sqlbv8.put("tBussNo", tBussNo);
			mapDel.put(sqlbv8,"DELETE");
			
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql("delete from LCInsureAccClassFee where contno = '"  + "?tBussNo?" + "'");
			sqlbv9.put("tBussNo", tBussNo);
			mapDel.put(sqlbv9,"DELETE");
			
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql("delete from LCPol where contno = '"  + "?tBussNo?" + "'");
			sqlbv10.put("tBussNo", tBussNo);
			mapDel.put(sqlbv10,"DELETE");
			
			//删除投保单原始数据 2008-12-27 ln add 
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql("delete from LCPolOriginal where contno = '"  + "?tBussNo?" + "'");
			sqlbv11.put("tBussNo", tBussNo);
			mapDel.put(sqlbv11,"DELETE");
			
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql("delete from LCDuty where contno = '"  + "?tBussNo?" + "'");
			sqlbv12.put("tBussNo", tBussNo);
			mapDel.put(sqlbv12,"DELETE");
			
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql("delete from LCPrem where contno = '"  + "?tBussNo?" + "'");
			sqlbv13.put("tBussNo", tBussNo);
			mapDel.put(sqlbv13,"DELETE");
			
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql("delete from LCGet where contno = '"  + "?tBussNo?" + "'");
			sqlbv14.put("tBussNo", tBussNo);
			mapDel.put(sqlbv14,"DELETE");
			
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql("delete from LCBnf where contno = '"  + "?tBussNo?" + "'");
			sqlbv15.put("tBussNo", tBussNo);
			mapDel.put(sqlbv15,"DELETE");
			
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql("delete from LCCustomerImpart where contno = '"  + "?tBussNo?" + "'");
			sqlbv16.put("tBussNo", tBussNo);
			mapDel.put(sqlbv16,"DELETE");
			
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql("delete from LCCustomerImpartParams where contno = '"  + "?tBussNo?" + "'");
			sqlbv17.put("tBussNo", tBussNo);
			mapDel.put(sqlbv17,"DELETE");
			
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql("delete from LCCustomerImpartDetail where contno = '"  + "?tBussNo?" + "'");
			sqlbv18.put("tBussNo", tBussNo);
			mapDel.put(sqlbv18,"DELETE");
			
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql("delete from lcissuepol where contno = '"  + "?tBussNo?" + "'");
			sqlbv19.put("tBussNo", tBussNo);
			mapDel.put(sqlbv19,"DELETE");
			
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql("delete from LACommisionDetail where contno = '"  + "?tBussNo?" + "'");
			sqlbv20.put("tBussNo", tBussNo);
			mapDel.put(sqlbv20,"DELETE");

			String tPolWherePart = " polno in (select polno from lcpol where contno = '" + "?tBussNo?" + "')";
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.sql("delete from LCInsuredRelated where "
					+ tPolWherePart);
			sqlbv21.put("tBussNo", tBussNo);
			mapDel.put(sqlbv21,"DELETE");
			
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.sql("delete from LCSpec where " + tPolWherePart);
			sqlbv22.put("tBussNo", tBussNo);
			mapDel.put(sqlbv22,"DELETE");
			
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.sql("delete from LCPremToAcc where " + tPolWherePart);
			sqlbv23.put("tBussNo", tBussNo);
			mapDel.put(sqlbv23,"DELETE");
			
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql("delete from LCGetToAcc where " + tPolWherePart);
			sqlbv24.put("tBussNo", tBussNo);
			mapDel.put(sqlbv24,"DELETE");
						
			if (mapDel == null) // 准备数据出现异常
			{
				logger.debug("准备删除已保存的数据发生异常：" + tBussNo);
				return false;
			}
			VData sVData = new VData();
			sVData.add(mapDel);
			PubSubmit ps = new PubSubmit();
			if (!ps.submitData(sVData, "")) {
				CError.buildErr(this, tBussNo + "删除已保存的投保单数据失败："
							+ ps.mErrors.getFirstError());
				return false;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 处理清洁件保单的数据
	 * 
	 * @param tAllBOPPol
	 * @param tQuestBOPPol
	 * @param serNo
	 * @param tGI
	 * @param tBPOID
	 * @return 如果处理出现异常，则返回false,否则返回true
	 */
	private boolean dealRightData(BPODataContainer tAllBOPPol,
			BPODataContainer tQuestBOPPol, String serNo, GlobalInput tGI,
			String tBPOID) {
		try {
			Vector tRightBussNoData = tAllBOPPol.getBussNoData();
			Vector tRightRiskNoData = tAllBOPPol.getRiskNoData();
			for (int i = 0; i < tRightBussNoData.size(); i++) {
				String tBussNo = (String) tRightBussNoData.get(i);
				VData tVData = (VData) tAllBOPPol.getschemaData(tBussNo);
				String tMainRisk = (String) tRightRiskNoData.get(i);
				tVData.add(tGI);
				BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
				tBPOMissionStateSchema = (BPOMissionStateSchema) tVData
						.getObjectByObjectName("BPOMissionStateSchema", 0);
				// 1.导入数据
				if (!DealOneRightPol(tVData, 0)) // 如果导入失败，则将该清洁件视为导入时的异常件
				{
					logger.debug("***导入数据失败，原因：" + mErrors.getLastError());
					// 判断是否有部分险种已经导入，如果有，则删除
					if (!redoInputedPol(tBussNo)) {
						return false;
					}
					// 准备异常件的数据
					tBPOMissionStateSchema.setDealType("04");
					tBPOMissionStateSchema.setState("0"); // 0－未完成处理
					tBPOMissionStateSchema.setOperator(tGI.Operator);
					tBPOMissionStateSchema.setBPOID(tBPOID);
					tBPOMissionStateSchema.setSerialNo(serNo);
					tBPOMissionStateSchema.setMakeDate(PubFun.getCurrentDate());
					tBPOMissionStateSchema.setMakeTime(PubFun.getCurrentTime());
					tBPOMissionStateSchema.setModifyDate(PubFun
							.getCurrentDate());
					tBPOMissionStateSchema.setModifyTime(PubFun
							.getCurrentTime());
					tBPOMissionStateSchema.setRiskCode(tMainRisk);

					BPOMissionDetailErrorSchema tBPOMissionDetailErrorSchema = new BPOMissionDetailErrorSchema();
					BPOMissionDetailErrorSet tBPOMissionDetailErrorSet = new BPOMissionDetailErrorSet();
					tBPOMissionDetailErrorSchema
							.setBussNo(tBPOMissionStateSchema.getBussNo());
					tBPOMissionDetailErrorSchema.setBussNoType("TB");
					tBPOMissionDetailErrorSchema.setErrorCount(1);
					tBPOMissionDetailErrorSchema.setErrorTag("");
					tBPOMissionDetailErrorSchema.setErrorCode("");
					tBPOMissionDetailErrorSchema
							.setErrorContent(StrTool.cTrim(
									mErrors.getLastError()).indexOf(
									"NullPointerException") != -1 ? "导入时发生异常，请依次检查保费、保额、份数、交费期间、保险期间等值是否正确！"
									: StrTool.cTrim(mErrors.getLastError()));
					tBPOMissionDetailErrorSet.add(tBPOMissionDetailErrorSchema);
					tVData.add(tBPOMissionDetailErrorSet);

					tQuestBOPPol.add(tBussNo,
							tAllBOPPol.getschemaData(tBussNo), tAllBOPPol
									.getelementData(tBussNo), tRightRiskNoData
									.get(i));
				} else // 如果成功，则记录该保单的状态
				{
					Element tOnePolData = (Element) tAllBOPPol
							.getelementData(tBussNo);
					BPODealInputDataBLS tBPODealInputDataBLS = new BPODealInputDataBLS(); // 数据提交类
					BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = new BPOMissionDetailStateSchema();
					tBPOMissionStateSchema.setDealType("00"); // 00－清洁件导入
					tBPOMissionStateSchema.setState("1"); // 1-成功导入系统
					tBPOMissionStateSchema.setRiskCode(tMainRisk);
					tBPOMissionStateSchema.setOperator(tGI.Operator);
					tBPOMissionStateSchema.setBPOID(tBPOID);
					tBPOMissionStateSchema.setSerialNo(serNo);
					tBPOMissionStateSchema.setMakeDate(PubFun.getCurrentDate());
					tBPOMissionStateSchema.setMakeTime(PubFun.getCurrentTime());
					tBPOMissionStateSchema.setModifyDate(PubFun
							.getCurrentDate());
					tBPOMissionStateSchema.setModifyTime(PubFun
							.getCurrentTime());

					Reflections tReflections = new Reflections();
					// 把对象tBPOMissionStateSchema的值赋给对象tBPOMissionDetailStateSchema
					tReflections.transFields(tBPOMissionDetailStateSchema,
							tBPOMissionStateSchema);
					ExeSQL tExeSQL = new ExeSQL();
					String tSQL = "select case when max(DealCount) is null then 0 else max(DealCount) end from BPOMissionDetailState where BussNo = '"
							+ "?tBussNo?"
							+ "' and BussNoType = 'TB'";
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(tSQL);
					sqlbv.put("tBussNo", tBPOMissionStateSchema.getBussNo());
					int tDealCount = Integer
							.parseInt(tExeSQL.getOneValue(sqlbv));
					logger.debug("tDealCount: " + tDealCount);
					tBPOMissionDetailStateSchema.setDealCount(tDealCount + 1);
					tVData.add(tBPOMissionDetailStateSchema);

					if (!tBPODealInputDataBLS.submitData(tVData, tOnePolData)) {
						CError.buildErr(this, tBPOMissionStateSchema
								.getBussNo()
								+ "已导入的保单数据保存失败："
								+ tBPODealInputDataBLS.mErrors.getFirstError());
						logger.debug("***导入数据失败，原因：" + mErrors.getLastError());
						// 判断是否有部分险种已经导入，如果有，则删除
						if (!redoInputedPol(tBussNo)) {
							return false;
						}
					}
				}
			}
		} catch (Exception ex) {
			CError.buildErr(this, "处理清洁件保单发生异常！");
			return false;
		}
		return true;
	}

	private MMap prepareDelMap(String tPrtNo, Vector tImoprtedPol) {
		MMap mapDel = new MMap();
		try {
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql("delete from LCCont where ContNo='" + "?tPrtNo?" + "'");
			sqlbv.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv,"DELETE");
			
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("delete from LCAppnt where ContNo='" + "?tPrtNo?" + "'");
			sqlbv1.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv1,"DELETE");
			
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("delete from LCInsured where ContNo='" + "?tPrtNo?" + "'");
			sqlbv2.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv2,"DELETE");
			
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql("delete from LCInsureAcc where ContNo='" + "?tPrtNo?" + "'");
			sqlbv3.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv3,"DELETE");
			
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql("delete from LCInsureAccClass where ContNo='" + "?tPrtNo?" + "'");
			sqlbv4.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv4,"DELETE");
			
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql("delete from LCInsureAccTrace where ContNo='" + "?tPrtNo?" + "'");
			sqlbv5.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv5,"DELETE");
			
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql("delete from LCInsureAccFee where ContNo='" + "?tPrtNo?" + "'");
			sqlbv6.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv6,"DELETE");
			
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql("delete from LCInsureAccClassFee where ContNo='" + "?tPrtNo?" + "'");
			sqlbv7.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv7,"DELETE");
			
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql("delete from LCPol where ContNo='" + "?tPrtNo?" + "'");
			sqlbv8.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv8,"DELETE");
			
			//删除投保单原始数据 2008-12-27 ln add 
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql("delete from LCPolOriginal where ContNo='" + "?tPrtNo?" + "'");
			sqlbv9.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv9,"DELETE");
			
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql("delete from LCDuty where ContNo='" + "?tPrtNo?" + "'");
			sqlbv10.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv10,"DELETE");
			
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql("delete from LCPrem where ContNo='" + "?tPrtNo?" + "'");
			sqlbv11.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv11,"DELETE");
			
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql("delete from LCGet where ContNo='" + "?tPrtNo?" + "'");
			sqlbv12.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv12,"DELETE");
			
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql("delete from LCBnf where ContNo='" + "?tPrtNo?" + "'");
			sqlbv13.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv13,"DELETE");
			
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql("delete from LCCustomerImpart where ContNo='" + "?tPrtNo?" + "'");
			sqlbv14.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv14,"DELETE");
			
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql("delete from LCCustomerImpartParams where ContNo='" + "?tPrtNo?" + "'");
			sqlbv15.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv15,"DELETE");
			
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql("delete from LCCustomerImpartDetail where ContNo='" + "?tPrtNo?" + "'");
			sqlbv16.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv16,"DELETE");
			
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql("delete from LACommisionDetail where ContNo='" + "?tPrtNo?" + "'");
			sqlbv17.put("tPrtNo", tPrtNo);
			mapDel.put(sqlbv17,"DELETE");
			
			if (tImoprtedPol != null && tImoprtedPol.size() > 0) {
				StringBuffer tResult = new StringBuffer(" PolNo in (");
				
				ArrayList<String> list = new ArrayList<String>(); 
				for (int i = 0; i < tImoprtedPol.size(); i++) {
					if (i > 0 && i < tImoprtedPol.size()) {
						tResult.append(",");
					}
					tResult.append("'").append((String) tImoprtedPol.get(i))
							.append("'");
					list.add((String)tImoprtedPol.get(i));
				}
				tResult.append(")");
				String tPolWherePart = tResult.toString();
				logger.debug("***tPolWherePart: " + tPolWherePart);
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql("delete from LCInsuredRelated where "+" PolNo in ("
						+ "?list?" + ")");
				sqlbv18.put("list", list);
				mapDel.put(sqlbv18,"DELETE");
										
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql("delete from LCSpec where "+" PolNo in ("
						+ "?list?" + ")");
				sqlbv19.put("list", list);
				mapDel.put(sqlbv19,"DELETE");
				
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				sqlbv20.sql("delete from LCPremToAcc where "+" PolNo in ("
						+ "?list?" + ")");
				sqlbv20.put("list", list);
				mapDel.put(sqlbv20,"DELETE");
				
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql("delete from LCGetToAcc where "+" PolNo in ("
						+ "?list?" + ")");
				sqlbv21.put("list", list);
				mapDel.put(sqlbv21,"DELETE");
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return null;
		}
		return mapDel;
	}

	/**
	 * 处理一个数据文件
	 * 
	 * @param Document
	 *            tOneFileData
	 * @param String
	 *            tDataFile
	 * @return 如果处理出现异常，则返回false,否则返回true
	 */
	public boolean DealOneFile(Document tOneFileData, String tDataFile) {
		try {
			logger.debug("Start " + tDataFile + " ...");
			BPODataContainer tAllBOPPol = new BPODataContainer();// 保存所有保单的信息
			BPODataContainer tQuestBOPPol = new BPODataContainer();// 保存异常件的信息
			BPODataContainer tImportedBOPPol = new BPODataContainer();// 已经在系统中导入过的保单信息
			BPODataContainer tNeedCheckBOPPol = new BPODataContainer(); // 按照抽检规则,需要抽检的保单集合

			String tBPOID = getBOPID(tOneFileData);// 得到外包公司的编码
			BPOServerInfoDB tBPOServerInfoDB = new BPOServerInfoDB();
			tBPOServerInfoDB.setBPOID(tBPOID);
			if (!tBPOServerInfoDB.getInfo()) {
				CError.buildErr(this, "外包公司返回数据错误：错误的外包方编码！");
				return false;
			}

			String tDataBackupPath = tBPOServerInfoDB.getBackDataBackupPath();
			if (tDataBackupPath == null || "".equals(tDataBackupPath)) {
				CError.buildErr(this, "外包公司" + tBPOID + "数据备份路径未描述！");
				return false;
			}

			if (tBPOServerInfoDB.getLisOperator() == null
					|| "".equals(tBPOServerInfoDB.getLisOperator())) {
				CError.buildErr(this, "外包公司" + tBPOID + "操作员未描述！");
				return false;
			}

			GlobalInput tGI = new GlobalInput();
			tGI.Operator = tBPOServerInfoDB.getLisOperator();
			tGI.ManageCom = "86";
			tGI.ComCode = "86";

			logger.debug("...Start getAllBOPPolData ...");
			// 1。获取所有保单信息
			if (!getAllBOPPolData(tAllBOPPol, tOneFileData)) {
				return false;
			}

			logger.debug("...Start getImportedBOPPolData ...");
			// 2.ImportedBOPPol
			if (!getImportedBOPPolData(tAllBOPPol, tImportedBOPPol)) {
				return false;
			}

			logger.debug("...Start getQuestBOPPolData ...");
			// 3.QuestBOPPol
			if (!getQuestBOPPolData(tAllBOPPol, tQuestBOPPol)) {
				return false;
			}

			logger.debug("...Start getNeedCheckBOPPolData ...");
			// 4.NeedCheckBOPPol
			 if(!getNeedCheckBOPPolData(tBPOID,tAllBOPPol,tNeedCheckBOPPol))
			 {
			 	return false;
			 }

			// 5.处理已导入件和抽检件

			// 产生流水号
			String tLimit = PubFun.getNoLimit(tGI.ManageCom);
			String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			logger.debug("...Start dealImportedAndNeedCheckData ...");
			if (!dealImportedAndNeedCheckData(tImportedBOPPol,
					tNeedCheckBOPPol, serNo, tGI, tBPOID)) {
				return false;
			}

			// 6.清洁件系统导入功能
			logger.debug("...Start dealRightData ...");
			if (!dealRightData(tAllBOPPol, tQuestBOPPol, serNo, tGI, tBPOID)) {
				return false;
			}

			// 7.处理异常件
			logger.debug("...Start dealQuestData ...");
			if (!dealQuestData(tQuestBOPPol, serNo, tGI, tBPOID)) {
				return false;
			}

			// 8。备份该数据文件
			logger.debug("...Start 准备移动数据文件 ...");
			// alter the backuppath; added by ln 2008.5.28
			 String tDate = PubFun.getCurrentDate();
			      
			 logger.debug("...year ..." + PubFun.getStr(tDate, 1, "-"));
			 logger.debug("...month ..." + PubFun.getStr(tDate, 2, "-"));
			 logger.debug("...day ..." + PubFun.getStr(tDate, 3, "-"));
			 String path = tDataBackupPath + PubFun.getStr(tDate, 1, "-") + "/"
			 + PubFun.getStr(tDate, 2, "-") + "/"
			 + PubFun.getStr(tDate, 3, "-") + "/";
			 logger.debug("...备份路径 ..." + path);
			 // end added by ln 2008.5.28
			 if(!TBPubFun.moveFiles(tDataFile,mBPOInputPath,path))
			 {
				 CError.buildErr(this, "备份"+tDataFile+"文件时出错！");			 
			     return false;
			 }
			logger.debug("... 移动数据文件成功 ...");
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 处理合同信息
	 * 
	 * @param tVData
	 *            VData 从XML中解析出的数据集合
	 * @return boolean 如果处理发生异常，返回false；否则，返回true
	 */
	public boolean dealOneCont(VData tVData,int tFlag) {
		String tReturnPrtNo = "";
		try {
			logger.debug("........dealOneCont  Start .......");
			Reflections tReflections = new Reflections();
			// lis5.3 存储的数据
			LCPolSchema tOldLCPolSchema = (LCPolSchema) tVData
					.getObjectByObjectName("LCPolSchema", 0);
			LCAppntIndSchema tOldLCAppntIndSchema = (LCAppntIndSchema) tVData
					.getObjectByObjectName("LCAppntIndSchema", 0);
			LCCustomerImpartSet tOldLCCustomerImpartSet = (LCCustomerImpartSet) tVData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
			TransferData tOldTransferData = (TransferData) tVData
					.getObjectByObjectName("TransferData", 0);
			GlobalInput tGI = (GlobalInput) tVData.getObjectByObjectName(
					"GlobalInput", 0);

			// lis6.0 新数据结构
			VData tResultVData = new VData();
			LCContSchema tLCContSchema = new LCContSchema();
			LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			LCAddressSchema tLCAddressSchema = new LCAddressSchema();
			LCAccountSchema tLCAccountSchema = new LCAccountSchema();
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();			

			// 合同信息数据准备
			logger.debug("........LCContSchema  Start .......");

			// 校验代理人
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tOldLCPolSchema.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				CError.buildErr(this, "代理人编码错误"
						+ tOldLCPolSchema.getAgentCode());
				return false;
			}
			tOldLCPolSchema.setAgentGroup(tLAAgentDB.getAgentGroup());
			tOldLCPolSchema.setManageCom(tLAAgentDB.getManageCom());//合同管理机构为代理人管理机构
			// 销售渠道为03、05的投保单合同管理机构为代理机构的管理机构
			if (tOldLCPolSchema.getSaleChnl() != null
					&& ("03".equals(tOldLCPolSchema.getSaleChnl())
							||"05".equals(tOldLCPolSchema.getSaleChnl()))) {
				if ((tOldLCPolSchema.getAgentCom() == null || ""
						.equals(tOldLCPolSchema.getAgentCom()))) {
					CError.buildErr(this, "销售渠道为银行代理和专业代理时必须输入代理机构！");
					return false;
				}
				else
				{
					LAComDB tLAComDB = new LAComDB();
					tLAComDB.setAgentCom(tOldLCPolSchema.getAgentCom());
					if (!tLAComDB.getInfo()) {
						CError.buildErr(this, "代理机构编码错误！");
						return false;
					}
					//如果是联办代理的代理机构,保单的管理机构设置为扫描机构+2位0
					if(tLAComDB.getBranchType()!=null && tLAComDB.getBranchType().equals("6"))
					{
						String tSQL = null;
						if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
							tSQL = "select Managecom from ES_Doc_Main where DocCode='"+"?DocCode?"+"' and subtype='UA001' and rownum=1";
						}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
							tSQL = "select Managecom from ES_Doc_Main where DocCode='"+"?DocCode?"+"' and subtype='UA001' limit 1";
						}
						SQLwithBindVariables sqlbv = new SQLwithBindVariables();
						sqlbv.sql(tSQL);
						sqlbv.put("DocCode",tOldLCPolSchema.getPrtNo());
						ExeSQL tExeSQL = new ExeSQL();
						String tManageCom = tExeSQL.getOneValue(sqlbv);//扫描机构
						if (tManageCom != null && !"".equals(tManageCom)) {
							tOldLCPolSchema.setManageCom(PubFun.RCh(tManageCom,"0",8));
						}				        
					}
					//中介投保单的管理机构取代理机构的管理机构
					else
						tOldLCPolSchema.setManageCom(tLAComDB.getManageCom());
				}				
			}		
			
			logger.debug("Start checkContData...");			
			if(!checkContData(tOldLCPolSchema, tLAAgentDB.getSchema(), tOldTransferData))//校验合同级信息是否正确，如校验代理人与销售渠道的关系
			{
				return false;
			}
			
			tReturnPrtNo = tOldLCPolSchema.getPrtNo();

			tReflections.transFields(tLCContSchema, tOldLCPolSchema);
			tLCContSchema.setContNo(tOldLCPolSchema.getPrtNo());
			tLCContSchema.setPrtNo(tOldLCPolSchema.getPrtNo());
			tLCContSchema.setGrpContNo("00000000000000000000");
			tLCContSchema.setProposalContNo(tOldLCPolSchema.getPrtNo());
			tLCContSchema.setSellType("02");
			tLCContSchema.setPolType("0");
			tLCContSchema.setContType("1");
			tLCContSchema.setForceUWFlag("0");
			String payMode = StrTool.cTrim((String) tOldTransferData
					.getValueByName("NewPayMode"));//add 2009-2-9 ln --首期交费方式不能为空
			/*if(payMode.equals(""))
			{
				CError.buildErr(this, "首期交费方式不能为空！");
				return false;
			}*/
			tLCContSchema.setNewPayMode(payMode);
			tLCContSchema.setNewBankCode(StrTool.cTrim((String) tOldTransferData
					.getValueByName("NewBankCode")));
			tLCContSchema.setNewBankAccNo(StrTool.cTrim((String) tOldTransferData
					.getValueByName("NewBankAccNo")));
			tLCContSchema.setNewAccName(StrTool.cTrim((String) tOldTransferData
					.getValueByName("NewAccName")));
			String payLocation = StrTool.cTrim((String) tOldTransferData
					.getValueByName("PayLocation"));//add 2009-2-9 ln --续期交费方式不能为空
			/*if(payLocation.equals(""))
			{
				CError.buildErr(this, "续期交费方式不能为空！");
				return false;
			}*/
			//首续期交费形式必须录入 ln 2009-05-15 add 
			if(payMode == null || payMode.equals("")
					|| payLocation == null || payLocation.equals(""))
			{
				CError.buildErr(this,"交费形式不能为空！");
				return false;
			}
			tLCContSchema.setPayLocation(payLocation);
			tLCContSchema.setBankCode(StrTool.cTrim((String) tOldTransferData
					.getValueByName("BankCode")));
			tLCContSchema.setBankAccNo(StrTool.cTrim((String) tOldTransferData
					.getValueByName("BankAccNo")));
			tLCContSchema.setAccName(StrTool.cTrim((String) tOldTransferData
					.getValueByName("AccName")));
			tLCContSchema.setOutPayFlag(StrTool.cTrim((String) tOldTransferData
					.getValueByName("OutPayFlag")));
			tLCContSchema.setGetPolMode(StrTool.cTrim((String) tOldTransferData
					.getValueByName("GetPolMode")));
			String SignName = StrTool.cTrim((String) tOldTransferData
					.getValueByName("SignName"));
			if(SignName.equals("") && tFlag == 0)
			{
				CError.buildErr(this, "初审员签名不能为空！");
				return false;
			}
			if (SignName.indexOf("?") != -1
					|| SignName.indexOf(
							"？") != -1
					|| SignName.indexOf(
							"M-}") != -1) {
				CError.buildErr(this, "初审员签名中可能包含系统无法转换的生僻字！");
				return false;
			}
			tLCContSchema.setSignName(SignName);
			tLCContSchema.setFirstTrialDate(StrTool.cTrim((String) tOldTransferData
					.getValueByName("FirstTrialDate")));
			tLCContSchema.setXQremindflag(StrTool.cTrim((String) tOldTransferData
					.getValueByName("XQremindflag")));
			// 投保人信息
			logger.debug("........LCAppntSchema  Start .......");
			tReflections = new Reflections();

			if (tOldLCAppntIndSchema.getOccupationCode()!=null 
					&& !"".equals(StrTool.cTrim(tOldLCAppntIndSchema
					.getOccupationCode()))) {
				LDOccupationDB tLDOccupationDB = new LDOccupationDB();
				tLDOccupationDB.setOccupationCode(tOldLCAppntIndSchema
						.getOccupationCode());
				if (!tLDOccupationDB.getInfo()) {
					CError.buildErr(this, "投保人职业代码错误"
							+ tOldLCAppntIndSchema.getOccupationCode());
					return false;
				}
				tOldLCAppntIndSchema.setOccupationType(tLDOccupationDB
						.getOccupationType());
			}
						
			//2009-9-22 ln add --如录入为“终身”，则将“出生日期”中的“年+100”导入
			String tidEndDate = StrTool.cTrim((String) tOldTransferData
					.getValueByName("AppntIDEndDate"));
			/* 去掉校验
			String tBirthDay = StrTool.cTrim(tOldLCAppntIndSchema.getBirthday());
			if(!tidEndDate.equals("") && !tBirthDay.equals("")&& tidEndDate.equals("终身"))
				tidEndDate = PubFun.newCalDate(tBirthDay,"Y",100);//往后推100年
			tOldTransferData.removeByName("AppntIDEndDate");
			tOldTransferData.setNameAndValue("AppntIDEndDate", tidEndDate);*/
			if(!checkAppntData(tOldLCPolSchema, tOldLCAppntIndSchema, tOldTransferData))//校验投保人信息是否正确
			{
				return false;
			}			

			tReflections.transFields(tLCAppntSchema, tOldLCAppntIndSchema);
			tLCAppntSchema.setAppntNo(StrTool.cTrim(tOldLCAppntIndSchema
					.getCustomerNo()));
			tLCAppntSchema.setAppntName(tOldLCAppntIndSchema.getName());
			tLCAppntSchema.setAppntSex(tOldLCAppntIndSchema.getSex());
			tLCAppntSchema.setAppntBirthday(tOldLCAppntIndSchema.getBirthday());
			tLCAppntSchema.setBankCode(StrTool.cTrim((String) tOldTransferData
					.getValueByName("BankCode")));
			tLCAppntSchema.setAccName(StrTool.cTrim((String) tOldTransferData
					.getValueByName("AccName")));
			tLCAppntSchema.setBankAccNo(StrTool.cTrim((String) tOldTransferData
					.getValueByName("BankAccNo")));
			tLCAppntSchema.setRelationToInsured(tOldLCAppntIndSchema
					.getRelationToInsured());
			tLCAppntSchema.setSocialInsuFlag(StrTool.cTrim((String) tOldTransferData
					.getValueByName("AppntSocialInsuFlag")));//是否有公费医疗、社会医疗保险
			tLCAppntSchema.setIDExpDate(tidEndDate);//证件号码有效期

			// 客户账户表
			logger.debug("........LCAccountSchema  Start .......");
			tLCAccountSchema
					.setBankCode(StrTool.cTrim((String) tOldTransferData
							.getValueByName("BankCode")));
			tLCAccountSchema.setAccName(StrTool.cTrim((String) tOldTransferData
					.getValueByName("AccName")));
			tLCAccountSchema.setBankAccNo(StrTool
					.cTrim((String) tOldTransferData
							.getValueByName("BankAccNo")));
			tLCAccountSchema.setAccKind("1");

			// 个人客户地址表
			logger.debug("........LCAddressSchema  Start .......");
			tReflections = new Reflections();
			tReflections.transFields(tLCAddressSchema, tOldLCAppntIndSchema);
			tLCAddressSchema.setCustomerNo(StrTool.cTrim(tOldLCAppntIndSchema
					.getCustomerNo()));
			// tLCAddressSchema.setAddressNo("");
			tLCAddressSchema.setCompanyAddress(tOldLCAppntIndSchema
					.getGrpAddress());
			tLCAddressSchema
					.setCompanyPhone(tOldLCAppntIndSchema.getGrpPhone());
			tLCAddressSchema.setCompanyZipCode(tOldLCAppntIndSchema
					.getGrpZipCode());
			tLCAddressSchema.setMobile(tOldLCAppntIndSchema.getPhone());
			tLCAddressSchema.setPhone(tOldLCAppntIndSchema.getPhone2());
			
			logger.debug("........LCCustomerImpart  Start .......");
			if (tOldLCCustomerImpartSet != null
					&& tOldLCCustomerImpartSet.size() > 0) {
				for (int i = 0; i < tOldLCCustomerImpartSet.size(); i++) {
					LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
					tLCCustomerImpartSchema = tOldLCCustomerImpartSet
							.get(i + 1);
					// 投保人告知
					if (tLCCustomerImpartSchema.getCustomerNoType()!=null 
							&& !tLCCustomerImpartSchema.getCustomerNoType().equals("")
							&& ("0".equals(tLCCustomerImpartSchema.getCustomerNoType()))) {
						tLCCustomerImpartSchema
								.setProposalContNo(tOldLCPolSchema.getPrtNo());
						tLCCustomerImpartSchema.setContNo(tOldLCPolSchema
								.getPrtNo());
						tLCCustomerImpartSchema.setCustomerNoType("0");
						tLCCustomerImpartSchema.setPatchNo("0");
						// added by ln 2008-8-29
						LDImpartDB tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartCode(tLCCustomerImpartSchema.getImpartCode());
						tLDImpartDB.setImpartVer(tLCCustomerImpartSchema.getImpartVer());
						if(tLDImpartDB.getInfo())
					      {
					        logger.debug("***impartcontent为空！");
					        tLCCustomerImpartSchema.setImpartContent(tLDImpartDB.getImpartContent());
					      }																
						// end						
						
						tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
					}
					
					//业务员告知处理
					if (tLCCustomerImpartSchema.getCustomerNoType()!=null 
							&& !tLCCustomerImpartSchema.getCustomerNoType().equals("")
							&& ("2".equals(tLCCustomerImpartSchema.getCustomerNoType()))) {
						tLCCustomerImpartSchema
								.setProposalContNo(tOldLCPolSchema.getPrtNo());
						tLCCustomerImpartSchema.setContNo(tOldLCPolSchema
								.getPrtNo());
						tLCCustomerImpartSchema.setCustomerNoType("2");
						tLCCustomerImpartSchema.setPatchNo("0");
						// added by ln 2008-8-29
						LDImpartDB tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartCode(tLCCustomerImpartSchema.getImpartCode());
						tLDImpartDB.setImpartVer(tLCCustomerImpartSchema.getImpartVer());
						if(tLDImpartDB.getInfo())
					      {
					        logger.debug("***加入impartcontent的内容");
					        tLCCustomerImpartSchema.setImpartContent(tLDImpartDB.getImpartContent());
					      }																
						// end
						/*//替换告知信息中的/为,，以便正确保存2008-12-25 ln add
						if(tLCCustomerImpartSchema.getImpartParamModle().indexOf("/")!=-1)
						{
							String ImpartParamModle = tLCCustomerImpartSchema.getImpartParamModle().replaceAll("/", ",");			
							tLCCustomerImpartSchema.setImpartParamModle(ImpartParamModle);
						}	*/						
						//2008-12-25 end
						tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
					}
				}
				
			}

			logger.debug("end setSchema:");
			// 准备传输数据 VData

			logger.debug("***tLCContSchema :" + tLCContSchema.encode());
			logger.debug("***tLCAppntSchema :" + tLCAppntSchema.encode());
			logger.debug("***tLCAddressSchema :"
					+ tLCAddressSchema.encode());
			logger.debug("***tLCAccountSchema :"
					+ tLCAccountSchema.encode());
			logger.debug("***tLCCustomerImpartSet :"
					+ tLCCustomerImpartSet.encode());
			logger.debug("***tLACommisionDetailSet :"
					+ tLACommisionDetailSet.encode());

			tResultVData.add(tLCContSchema);
			tResultVData.add(tLCAddressSchema);
			tResultVData.add(tLCAppntSchema);
			tResultVData.add(tLCAccountSchema);
			tResultVData.add(tLCCustomerImpartSet);
			tResultVData.add(tLACommisionDetailSet);
			tResultVData.add(tGI);

			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("SavePolType", "0"); // 保全保存标记，默认为0，标识非保全
			tTransferData.setNameAndValue("GrpNo", "");
			tTransferData.setNameAndValue("GrpName", tOldLCAppntIndSchema
					.getGrpName());

			tResultVData.add(tTransferData);

			ContUI tContUI = new ContUI();
			if (tContUI.submitData(tResultVData, "INSERT||CONT") == false) {
				mErrors.copyAllErrors(tContUI.mErrors);
				return false;
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 准备被保人信息，方法：拆分成
	 * 
	 * @param tVData
	 *            VData 从XML中解析出的数据集合
	 * @return boolean 如果处理发生异常，返回false；否则，返回true
	 */
	public boolean prepareInsuredRelations(VData tAfterResultsSet, VData tVData) {
		try {
			logger.debug("........prepareInsuredRelations  Start .......");
			Reflections tReflections = new Reflections();
			// lis5.3 存储的数据
			LCPolSchema tOldLCPolSchema = (LCPolSchema) tVData
					.getObjectByObjectName("LCPolSchema", 0);
			LCAppntIndSchema tOldLCAppntIndSchema = (LCAppntIndSchema) tVData
					.getObjectByObjectName("LCAppntIndSchema", 0);
			LCCustomerImpartSet tOldLCCustomerImpartSet = (LCCustomerImpartSet) tVData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
			TransferData tOldTransferData = (TransferData) tVData
					.getObjectByObjectName("TransferData", 0);
			VData InsuredResults = (VData) tVData.getObjectByObjectName(
					"VData", 0);
			GlobalInput tGI = (GlobalInput) tVData.getObjectByObjectName(
					"GlobalInput", 0);

			// 拆分被保人相关信息
			logger.debug("........depart  Start .......");

			// Before
			LCInsuredSet tBeforeLCInsuredSet = new LCInsuredSet();
			LCInsuredSchema tBeforeLCInsuredSchema = new LCInsuredSchema();
			RiskBasicInfo tBeforeRiskBasicInfo = new RiskBasicInfo();
			TransferData tBeforeTransferData1 = new TransferData();// 生存
			LCPolSchema tBeforeLCPolSchema = new LCPolSchema();
			LCCustomerImpartSchema tBeforeLCCustomerImpartSchema = new LCCustomerImpartSchema();
			LCBnfSchema tBeforeLCBnfSchema = new LCBnfSchema();
			VData tBeforeTransferDataSet;
			TransferData tBeforeTransferData = new TransferData();// 被保人相关
			VData tBeforeTransferDataSet1;
			LCBnfSet tBeforeLCBnfSet;
			LCPolSet tBeforeLCPolSet1;// 生存
			VData tBeforeRiskBasicInfoMainSet;

			// After
			LCInsuredSet tAfterLCInsuredSet = new LCInsuredSet();// 所有被保人
			VData tAftertResultSet = new VData();// 支持多主险投保单
			VData tAfterResultsPartSet;
			// 被保人住址等信息集合
			LCPolSchema tAfterLCPolSchema = new LCPolSchema();
			LCCustomerImpartSet tAfterLCCustomerImpartSet = new LCCustomerImpartSet();// 告知
			VData tAftertResult = new VData();
			VData tAfterRiskBasicInfoSet = new VData();
			LCBnfSet tAfterLCBnfSet = new LCBnfSet();
			TransferData tAfterTransferData = new TransferData();

			int j = 0;
			int k = 0;
			String tRiskNo = "";
			String tInsuredNo = "";

			logger.debug("........depart  End .......");
			logger.debug("........Merger   Start .......");

			if (InsuredResults != null && InsuredResults.size() > 0) {// 多被保人
				for (int i = 0; i < InsuredResults.size(); i++) {// 一个主被保人
					logger.debug("***************被保人****************"
							+ (i + 1));
					tAfterResultsPartSet = new VData();
					tAfterLCInsuredSet = new LCInsuredSet();
					VData tInsuredRelaData = (VData) InsuredResults.get(i);

					tBeforeTransferDataSet = (VData) tInsuredRelaData
							.getObjectByObjectName("VData", 0);
					tBeforeTransferData = new TransferData();// 被保人相关
					tBeforeTransferDataSet1 = (VData) tInsuredRelaData
							.getObjectByObjectName("VData", 1);
					tBeforeLCInsuredSet = (LCInsuredSet) tInsuredRelaData
							.getObjectByObjectName("LCInsuredSet", 2);
					tBeforeLCBnfSet = (LCBnfSet) tInsuredRelaData
							.getObjectByObjectName("LCBnfSet", 0);
					tBeforeLCPolSet1 = (LCPolSet) tInsuredRelaData
							.getObjectByObjectName("LCPolSet", 0);// 生存
					tBeforeRiskBasicInfoMainSet = (VData) tInsuredRelaData
							.getObjectByObjectName("VData", 3);
					tAfterLCCustomerImpartSet = new LCCustomerImpartSet();
					tAftertResultSet = new VData();

					if (tBeforeLCInsuredSet != null
							&& tBeforeLCInsuredSet.size() > 0) {// 可能包含连带被保人
						for (j = 0; j < tBeforeLCInsuredSet.size(); j++) {// 一个被保人
							tBeforeLCInsuredSchema = new LCInsuredSchema();
							tBeforeLCInsuredSchema = (LCInsuredSchema) tBeforeLCInsuredSet
									.get(j + 1);
							tInsuredNo = tBeforeLCInsuredSchema.getSequenceNo();
							tBeforeLCInsuredSchema.setInsuredNo("");
							tBeforeLCInsuredSchema.setSequenceNo(tInsuredNo);
							tAfterLCInsuredSet.add(tBeforeLCInsuredSchema);
						}
						// end insuredset

						if (tOldLCCustomerImpartSet != null
								&& tOldLCCustomerImpartSet.size() > 0) {
							for (k = 0; k < tOldLCCustomerImpartSet.size(); k++) {// 一个告知信息
								tBeforeLCCustomerImpartSchema = new LCCustomerImpartSchema();
								tBeforeLCCustomerImpartSchema = (LCCustomerImpartSchema) tOldLCCustomerImpartSet
										.get(k + 1);
								if (//tBeforeLCCustomerImpartSchema
									//	.getCustomerNoType().equals("0")
									//	||
										tBeforeLCCustomerImpartSchema
										.getCustomerNoType()!=null 
										&& !tBeforeLCCustomerImpartSchema
										.getCustomerNoType().equals("")
										&&(tBeforeLCCustomerImpartSchema
												.getCustomerNoType()
												.equals("1") && (tBeforeLCCustomerImpartSchema
												.getCustomerNo().equals(
														tInsuredNo)|| tInsuredNo.equals("-1")))) {
									tBeforeLCCustomerImpartSchema
											.setCustomerNo("");
									tAfterLCCustomerImpartSet
											.add(tBeforeLCCustomerImpartSchema);
								}
							}
						}
						// end customerimpart

						logger.debug("***OldLCPolSchema :"
								+ tOldLCPolSchema.encode());

						if (tBeforeRiskBasicInfoMainSet != null
								&& tBeforeRiskBasicInfoMainSet.size() > 0) {// 多主险
							for (j = 0; j < tBeforeRiskBasicInfoMainSet.size(); j++) {// 一个主险
								logger.debug("-------------一个主险--------------"
												+ (j + 1));
								tAftertResult = new VData();
								tAfterRiskBasicInfoSet = new VData();
								tAfterLCBnfSet = new LCBnfSet();
								tAfterTransferData = new TransferData();
								tAfterLCPolSchema = new LCPolSchema();
								tReflections = new Reflections();
								tReflections.transFields(tAfterLCPolSchema,
										tOldLCPolSchema);
								// end lcpol1
								tBeforeTransferData = new TransferData();
								tBeforeTransferData = (TransferData) tBeforeTransferDataSet
										.get(0);// 被保人（非连带）地址等信息

								copyTransferData(
										tOldTransferData,
										tAfterTransferData);
								copyTransferData(
										tBeforeTransferData,
										tAfterTransferData);
								// end transferdata1

								VData tBeforeRiskBasicInfoSet = (VData) tBeforeRiskBasicInfoMainSet
										.get(j);
								if (tBeforeRiskBasicInfoSet != null
										&& tBeforeRiskBasicInfoSet.size() > 0) {
									for (k = 0; k < tBeforeRiskBasicInfoSet
											.size(); k++) {// 一个险种
										tBeforeRiskBasicInfo = new RiskBasicInfo();
										tBeforeRiskBasicInfo = (RiskBasicInfo) tBeforeRiskBasicInfoSet
												.get(k);
										tRiskNo = tBeforeRiskBasicInfo
												.getRiskNo();
										//tBeforeRiskBasicInfo.setRiskNo("");
										tAfterRiskBasicInfoSet
												.add(tBeforeRiskBasicInfo);
										logger.debug("···············一个险种···············"
														+ (k + 1));
									}
									// end riskset

									if (tBeforeLCPolSet1 != null
											&& tBeforeLCPolSet1.size() > 0) {
										for (k = 0; k < tBeforeLCPolSet1.size(); k++) {// 一个生存信息
											tBeforeLCPolSchema = new LCPolSchema();
											tBeforeLCPolSchema = (LCPolSchema) tBeforeLCPolSet1
													.get(k + 1);
											if (tBeforeLCPolSchema
													.getInsuredNo()!=null && !tBeforeLCPolSchema
													.getInsuredNo().equals("")
													&&(tBeforeLCPolSchema
													.getInsuredNo().equals(
															tRiskNo))) {
												tAfterLCPolSchema
														.setGetYear(tBeforeLCPolSchema
																.getGetYear());
												tAfterLCPolSchema
														.setGetYearFlag(tBeforeLCPolSchema
																.getGetYearFlag());
												tAfterLCPolSchema
														.setLiveGetMode(tBeforeLCPolSchema
																.getLiveGetMode());
												tAfterLCPolSchema
														.setBonusGetMode(tBeforeLCPolSchema
																.getBonusGetMode());
												// end lcpol2

												tBeforeTransferData1 = new TransferData();
												tBeforeTransferData1 = (TransferData) tBeforeTransferDataSet1
														.get(k);												

												tAfterTransferData
														.setNameAndValue(
																"GetYears",
																tBeforeTransferData1
																		.getValueByName("GetYears"));
												tAfterTransferData
														.setNameAndValue(
																"GetDutyKind",
																tBeforeTransferData1
																		.getValueByName("GetDutyKind"));
												// end transferdata2
												// end lcpol2
												break;
											}
										}
									}

									if (tBeforeLCBnfSet != null
											&& tBeforeLCBnfSet.size() > 0) {
										for (k = 0; k < tBeforeLCBnfSet.size(); k++) {// 一个受益人信息
											tBeforeLCBnfSchema = new LCBnfSchema();
											tBeforeLCBnfSchema = (LCBnfSchema) tBeforeLCBnfSet
													.get(k + 1);
											if (tBeforeLCBnfSchema.getBnfNo() == Integer
													.parseInt(tRiskNo)
													|| (!tRiskNo.equals("-1") && tBeforeLCBnfSchema
															.getBnfNo() == 0))
												tAfterLCBnfSet
														.add(tBeforeLCBnfSchema);
										}
										// end bnf
									}

								}// end if one main risk
								logger.debug("***AfterLCPolSchema :"
										+ tAfterLCPolSchema.encode());
								// logger.debug("***AfterLCInsuredSet
								// :"+tAfterLCInsuredSet.encode());
								logger.debug("***AfterLCBnfSet :"
										+ tAfterLCBnfSet.encode());
								// logger.debug("***AfterLCCustomerImpartSet
								// :"+tAfterLCCustomerImpartSet.encode());

								tAftertResult.add(tAfterLCPolSchema);
								// tAftertResult.add(tAfterLCInsuredSet);
								// tAftertResult.add(tBeforeTransferDataSet);
								tAftertResult.add(tAfterLCBnfSet);
								tAftertResult.add(tAfterTransferData);
								tAftertResult.add(tAfterRiskBasicInfoSet);

								tAftertResultSet.add(tAftertResult);
							} // end for one main risk
						}// end if more main risk //end dataset
						else {
							CError.buildErr(this, "投保单信息不全！");
							return false;
						}
						logger.debug("￥￥￥￥￥￥￥￥￥￥￥￥tAfterResultsPartSet￥￥￥￥￥￥￥￥￥￥￥￥ :");
						logger.debug("***AfterLCInsuredSet :"
								+ tAfterLCInsuredSet.encode());
						logger.debug("***AfterLCCustomerImpartSet :"
								+ tAfterLCCustomerImpartSet.encode());
						tAfterResultsPartSet.add(tGI);
						tAfterResultsPartSet.add(tOldLCPolSchema);
						tAfterResultsPartSet.add(tOldLCAppntIndSchema);
						tAfterResultsPartSet.add(tAfterLCInsuredSet);
						tAfterResultsPartSet.add(tBeforeTransferDataSet);
						tAfterResultsPartSet.add(tAfterLCCustomerImpartSet);
						tAfterResultsPartSet.add(tOldTransferData);
						tAfterResultsPartSet.add(tAftertResultSet);
						tAfterResultsSet.add(tAfterResultsPartSet);
					}// end if one insured

				}// end for one insured
			} else {
				CError.buildErr(this, "投保单信息不全！");
				return false;
			}

			logger.debug("........Merger   End .......");
			return true;

		}// try
		catch (Exception ex) {
			CError.buildErr(this, "投保单信息不全！");
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * 处理合同下被保险人信息
	 * 
	 * @param tVData
	 *            VData 从XML中解析出的数据集合
	 * @return boolean 如果处理发生异常，返回false；否则，返回true
	 */
	public boolean dealOneContInsured(VData tInsuredRelation) {
		try {
			logger.debug("........dealOneContInsured  Start .......");
			Reflections tReflections = new Reflections();
			// before
			LCAppntIndSchema tOldLCAppntIndSchema = (LCAppntIndSchema) tInsuredRelation
					.getObjectByObjectName("LCAppntIndSchema", 0);
			LCPolSchema tOldLCPolSchema = (LCPolSchema) tInsuredRelation
					.getObjectByObjectName("LCPolSchema", 0);
			LCInsuredSet tLCInsuredSet = (LCInsuredSet) tInsuredRelation
					.getObjectByObjectName("LCInsuredSet", 0);
			LCCustomerImpartSet tOldLCCustomerImpartSet = (LCCustomerImpartSet) tInsuredRelation
					.getObjectByObjectName("LCCustomerImpartSet", 0);
			VData tOldTransferDataSet = (VData) tInsuredRelation
					.getObjectByObjectName("VData", 0);
			TransferData tOldTransferPolData = (TransferData) tInsuredRelation
					.getObjectByObjectName("TransferData", 0);
			GlobalInput tGI = (GlobalInput) tInsuredRelation
					.getObjectByObjectName("GlobalInput", 0);

			TransferData tPolOldTransferData = new TransferData();
			TransferData tInsurOldTransferData = new TransferData();
			tPolOldTransferData = (TransferData) tOldTransferDataSet.get(0);
			String tsamePersonFlag = (String) tPolOldTransferData
					.getValueByName("samePersonFlag");
//			// 如果投保人与被保险人相同，则根据投保人信息初始化被保险人信息--已在解析xml文件时初始化
//			if ("1".equals(StrTool.cTrim(tsamePersonFlag))) {
//				tLCInsuredSet = new LCInsuredSet();
//				LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
//				tReflections
//						.transFields(tLCInsuredSchema, tOldLCAppntIndSchema);
//				tLCInsuredSchema.setInsuredNo(tOldLCAppntIndSchema
//						.getCustomerNo());
//				tLCInsuredSchema.setSequenceNo("1");
//				tLCInsuredSet.add(tLCInsuredSchema);
//			}

			if (tLCInsuredSet != null && tLCInsuredSet.size() > 0) {
				for (int i = 0; i < tLCInsuredSet.size(); i++) {
					LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(i + 1);
					String tAppntNo = "";
					//如果投保人与被保险人相同，查询投保人客户号
					if ("1".equals(StrTool.cTrim(tsamePersonFlag))) {
						LCAppntDB tLCAppntDB= new LCAppntDB();
						tLCAppntDB.setContNo(tOldLCPolSchema.getPrtNo());
						if(!tLCAppntDB.getInfo())
						{
							CError.buildErr(this, "投保人信息查询失败！");
							return false;
						}
						tAppntNo = tLCAppntDB.getAppntNo();
						tReflections.transFields(tLCInsuredSchema,tOldLCAppntIndSchema);
						tLCInsuredSchema.setIDExpDate(StrTool
								.cTrim((String) tOldTransferPolData
										.getValueByName("AppntIDEndDate")));
						tLCInsuredSchema.setSocialInsuFlag(StrTool
								.cTrim((String) tOldTransferPolData
										.getValueByName("AppntSocialInsuFlag")));
						tLCInsuredSchema.setInsuredNo(tAppntNo);
					}
					logger.debug("----------------AppntNo="+tAppntNo);
					
					if (tLCInsuredSchema.getOccupationCode()!= null && !"".equals(StrTool.cTrim(tLCInsuredSchema
							.getOccupationCode()))) {
						LDOccupationDB tLDOccupationDB = new LDOccupationDB();
						tLDOccupationDB.setOccupationCode(tLCInsuredSchema
								.getOccupationCode());
						if (!tLDOccupationDB.getInfo()) {
							CError.buildErr(this, "被保险人职业代码错误"
									+ tLCInsuredSchema.getOccupationCode());
							return false;
						}
						tLCInsuredSchema.setOccupationType(tLDOccupationDB
								.getOccupationType());
					}					
					
					/* 去掉校验
					//2009-9-22 ln add --如录入为“终身”，则将“出生日期”中的“年+100”导入
					String tidEndDate = StrTool.cTrim(tLCInsuredSchema.getIDExpDate());
					String tBirthDay = StrTool.cTrim(tLCInsuredSchema.getBirthday());
					if(!tidEndDate.equals("") && !tBirthDay.equals("")&& tidEndDate.equals("终身"))
						tidEndDate = PubFun.newCalDate(tBirthDay,"Y",100);//往后推100年
					tLCInsuredSchema.setIDExpDate(tidEndDate);*/
					if(!checkInsuredData(tOldLCPolSchema, tLCInsuredSchema))//校验被保人信息是否正确
					{
						return false;
					}					

					if (tLCInsuredSchema.getSequenceNo()==null || StrTool.cTrim(tLCInsuredSchema.getSequenceNo()).equals(
							"")) {
						tLCInsuredSchema.setSequenceNo("1");
					}
					VData tResultVData = new VData();
					TransferData tTransferData = new TransferData();
					LCContSchema tLCContSchema = new LCContSchema();
					LDPersonSchema tLDPersonSchema = new LDPersonSchema();
					LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();
					LCInsuredDB tOLDLCInsuredDB = new LCInsuredDB();
					LCAddressSchema tLCAddressSchema = new LCAddressSchema();
					LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
					LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
					LLAccountSchema mLLAccountSchema = new LLAccountSchema();

					// 合同信息数据准备
					logger.debug("........LCContSchema  Start .......");
					tLCContSchema.setGrpContNo("00000000000000000000");
					tLCContSchema.setContNo(tOldLCPolSchema.getPrtNo());
					tLCContSchema.setPrtNo(tOldLCPolSchema.getPrtNo());
					tLCContSchema.setManageCom(tOldLCPolSchema.getManageCom());

					// 被保险人数据准备
					logger.debug("........LCInsuredSchema  Start .......");
					tmLCInsuredSchema.setInsuredNo(StrTool
							.cTrim(tLCInsuredSchema.getInsuredNo()));
					tmLCInsuredSchema.setRelationToMainInsured("00");
					tmLCInsuredSchema.setRelationToAppnt(StrTool
							.cTrim(tLCInsuredSchema.getRelationToAppnt()));//??????????????
					tmLCInsuredSchema.setContNo(tOldLCPolSchema.getPrtNo());
					tmLCInsuredSchema.setGrpContNo("00000000000000000000");
					tmLCInsuredSchema.setContPlanCode("");
					tmLCInsuredSchema.setExecuteCom("");
					tmLCInsuredSchema.setJoinCompanyDate("");
					tmLCInsuredSchema.setInsuredPeoples("");
					tmLCInsuredSchema.setSalary("");
					tmLCInsuredSchema.setBankCode(StrTool
							.cTrim((String) tOldTransferPolData
									.getValueByName("BankCode")));
					tmLCInsuredSchema.setAccName(StrTool
							.cTrim((String) tOldTransferPolData
									.getValueByName("AccName")));
					tmLCInsuredSchema.setBankAccNo(StrTool
							.cTrim((String) tOldTransferPolData
									.getValueByName("BankAccNo")));
					tmLCInsuredSchema.setLicenseType("");

					// 被保险人数据准备
					logger.debug("........LCInsuredSchema  Start .......");
					tReflections.transFields(tLDPersonSchema, tLCInsuredSchema);
					tLDPersonSchema.setCustomerNo(StrTool
							.cTrim(tLCInsuredSchema.getInsuredNo()));
					tLDPersonSchema.setPassword("");

					// 被保险人地址数据准备
					logger.debug("...... Insured LCAddressSchema  Start .......");
					tLCAddressSchema.setCustomerNo(StrTool
							.cTrim(tLCInsuredSchema.getInsuredNo()));
					// tLCAddressSchema.setAddressNo("");
					if ("1".equals(StrTool.cTrim(tsamePersonFlag))) {
						tReflections.transFields(tLCAddressSchema,
								tOldLCAppntIndSchema);						
						tLCAddressSchema.setMobile(tOldLCAppntIndSchema.getPhone());
						tLCAddressSchema.setPhone(tOldLCAppntIndSchema.getPhone2());
					} else {
						tInsurOldTransferData = (TransferData) tOldTransferDataSet
								.get(i);
						tLCAddressSchema.setPostalAddress((StrTool
								.cTrim((String) tInsurOldTransferData
										.getValueByName("HomeAddress"))));
						tLCAddressSchema.setZipCode(StrTool
								.cTrim((String) tInsurOldTransferData
										.getValueByName("HomeZipCode")));
						tLCAddressSchema.setMobile(StrTool
								.cTrim((String) tInsurOldTransferData
										.getValueByName("Phone")));
						 tLCAddressSchema.setPhone(StrTool
								 .cTrim((String)tInsurOldTransferData
										 .getValueByName("Phone2")));
						 tLCAddressSchema.setGrpName(StrTool
								 .cTrim((String)tInsurOldTransferData
										 .getValueByName("GrpName")));
						 tLCAddressSchema.setEMail(StrTool
								 .cTrim((String)tInsurOldTransferData
										 .getValueByName("EMail")));
					}

					// 被保险人地址数据准备
					logger.debug("...... Insured LCCustomerImpart  Start .......");
					if (tOldLCCustomerImpartSet != null
							&& tOldLCCustomerImpartSet.size() > 0) {
						for (int j = 0; j < tOldLCCustomerImpartSet.size(); j++) 
						{
							LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
							tLCCustomerImpartSchema = tOldLCCustomerImpartSet
									.get(j + 1);
							if (tLCCustomerImpartSchema
									.getCustomerNoType()!=null && !tLCCustomerImpartSchema
									.getCustomerNoType().equals("") && "1".equals(tLCCustomerImpartSchema
									.getCustomerNoType())) {
								tLCCustomerImpartSchema
										.setProposalContNo(tOldLCPolSchema
												.getPrtNo());
								tLCCustomerImpartSchema.setCustomerNoType("1");
								tLCCustomerImpartSchema.setPatchNo("0");
								// added by ln 2008-8-29
								LDImpartDB tLDImpartDB = new LDImpartDB();
								tLDImpartDB.setImpartCode(tLCCustomerImpartSchema.getImpartCode());
								tLDImpartDB.setImpartVer(tLCCustomerImpartSchema.getImpartVer());
								if(tLDImpartDB.getInfo())
							      {
							        logger.debug("***加入impartcontent的内容");
							        tLCCustomerImpartSchema.setImpartContent(tLDImpartDB.getImpartContent());
							      }																
								// end
								/*//替换告知信息中的/为,，以便正确保存2008-12-25 ln add
								if(tLCCustomerImpartSchema.getImpartParamModle().indexOf("/")!=-1)
								{
									String ImpartParamModle = tLCCustomerImpartSchema.getImpartParamModle().replaceAll("/", ",");			
									tLCCustomerImpartSchema.setImpartParamModle(ImpartParamModle);
								}	*/						
								//2008-12-25 end
								tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
							}
						}						
					}
					/* 注释掉，在核心程序中进行处理
					String tSocialInsuFlag = "0";
					if (tOldLCCustomerImpartSet != null
							&& tOldLCCustomerImpartSet.size() > 0) {
						for (int j = 1; j <= tOldLCCustomerImpartSet.size(); j++) {
							LCCustomerImpartSchema tLCCustomerImpart = tOldLCCustomerImpartSet
									.get(j);
							logger.debug("tLCCustomerImpart.getImpartVer(): "
											+ tLCCustomerImpart.getImpartVer());
							logger.debug("tLCCustomerImpart.getImpartCode(): "
											+ tLCCustomerImpart.getImpartCode());
							logger.debug("tLCCustomerImpart.getCustomerNoType(): "
											+ tLCCustomerImpart
													.getCustomerNoType());

							if (tLCCustomerImpart.getImpartVer()!=null && !tLCCustomerImpart.getImpartVer().equals("")
									&& tLCCustomerImpart.getImpartCode()!=null && !tLCCustomerImpart.getImpartCode().equals("")
									&& tLCCustomerImpart.getCustomerNoType()!=null && !tLCCustomerImpart.getCustomerNoType().equals("")
									&& (("D02".equals(tLCCustomerImpart.getImpartVer())
									&& "D0118".equals(tLCCustomerImpart
											.getImpartCode())) || ("A02".equals(tLCCustomerImpart.getImpartVer())
													&& "A0119".equals(tLCCustomerImpart
															.getImpartCode())))
									&& "I".equals(tLCCustomerImpart
											.getCustomerNoType())) {
								tSocialInsuFlag = "1";
								break;
							}
						}
					}

					tmLCInsuredSchema.setSocialInsuFlag(tSocialInsuFlag);
					tLDPersonSchema.setSocialInsuFlag(tSocialInsuFlag);
					*/
					tLDPersonSchema.setSocialInsuFlag(tLCInsuredSchema.getSocialInsuFlag());
					tTransferData.setNameAndValue("SavePolType", "0"); // 保全保存标记，默认为0，标识非保全
					tTransferData.setNameAndValue("ContNo", tOldLCPolSchema
							.getPrtNo());
					tTransferData.setNameAndValue("FamilyType", "1"); // 家庭单标记
					tTransferData.setNameAndValue("ContType", ""); // 团单，个人单标记
					tTransferData.setNameAndValue("PolTypeFlag", "0"); // 无名单标记
					tTransferData.setNameAndValue("InsuredPeoples", ""); // 被保险人人数
					tTransferData.setNameAndValue("InsuredAppAge", String
							.valueOf(PubFun.calInterval2(tLCInsuredSchema
									.getBirthday(), tOldLCPolSchema
									.getPolApplyDate(), "Y"))); // 被保险人年龄
					tTransferData.setNameAndValue("SequenceNo", String
							.valueOf(tLCInsuredSchema.getSequenceNo())); // 内部客户号

					logger.debug("***tLCContSchema :"
							+ tLCContSchema.encode());
					logger.debug("***tLDPersonSchema :"
							+ tLDPersonSchema.encode());
					logger.debug("***tLCCustomerImpartSet :"
							+ tLCCustomerImpartSet.encode());
					logger.debug("***tLCCustomerImpartDetailSet :"
							+ tLCCustomerImpartDetailSet.encode());
					logger.debug("***tmLCInsuredSchema :"
							+ tmLCInsuredSchema.encode());
					logger.debug("***tLCAddressSchema :"
							+ tLCAddressSchema.encode());
					logger.debug("***tOLDLCInsuredDB :"
							+ tOLDLCInsuredDB.encode());

					tResultVData.add(tLCContSchema);
					tResultVData.add(tLDPersonSchema);
					tResultVData.add(tLCCustomerImpartSet);
					tResultVData.add(tLCCustomerImpartDetailSet);
					tResultVData.add(tmLCInsuredSchema);
					tResultVData.add(tLCAddressSchema);
					tResultVData.add(tOLDLCInsuredDB);
					tResultVData.add(tTransferData);
					tResultVData.add(tGI);
					tResultVData.add(mLLAccountSchema);

					ContInsuredUI tContInsuredUI = new ContInsuredUI();
					if (!tContInsuredUI.submitData(tResultVData,
							"INSERT||CONTINSURED")) {
						mErrors.copyAllErrors(tContInsuredUI.mErrors);
						return false;
					}
				}
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * 将一个清洁件导入到核心业务系统中
	 * 
	 * @param tVData
	 * @param tFlag
	 *            处理标志，0－表示系统正常导入，1－异常件、抽检件
	 * @return 如果导入出错，则返回false,否则，返回true
	 */
	public boolean DealOneRightPol(VData tVData, int tFlag) {
		VData tInsuredRelations = new VData();
		VData tInsuredRelation = new VData();
		VData tRiskRelations = new VData();
		VData tRiskRelation = new VData();		
		VData tAfterResultsSet = new VData();// 支持多主险多被保人投保单
		VData tAllSpecRiskVData = new VData();// 记录豁免险相关信息
		VData tSpecRiskVData = new VData();// 记录投保人（或被保人）豁免险相关信息，以便最后保存
		String tSpecRiskFlag = "";//标志是否有投保人（或被保人）豁免险，空为没有，appnt表示有投保人豁免险；insured表示有被保人豁免险，用于以后进行处理
		String tPrtNo = "";
		String tDealType = "";
		
		try {
			GlobalInput tGI = (GlobalInput) tVData.getObjectByObjectName("GlobalInput", 0);
			LCPolSchema tOldLCPolSchema = (LCPolSchema) tVData.getObjectByObjectName("LCPolSchema", 0);
			TransferData tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
			tPrtNo = tOldLCPolSchema.getPrtNo();
			tDealType = (String)tTransferData.getValueByName("DealType");
			
			logger.debug("...........................");
			// 0 校验必选告知是否录入
			///所有数据未保存前的校验
			WBChangeRiskField tWBChangeRiskField = new WBChangeRiskField();
			if (!tWBChangeRiskField.checkAllunSaveSpec(tVData,tFlag)) {
				this.mErrors.copyAllErrors(tWBChangeRiskField.mErrors);
				return false;
			}
			
			// 1 处理合同、投保人信息
			if (!dealOneCont(tVData,tFlag)) {
				return false;
			}			

			// 2 准备被保险人相关信息
			if (!prepareInsuredRelations(tAfterResultsSet, tVData)
					|| tAfterResultsSet == null) {
				return false;
			}

			// 3 处理被保险人相关信息
			if (tAfterResultsSet != null && tAfterResultsSet.size() > 0) {
				for (int i = 0; i < tAfterResultsSet.size(); i++) {// 多被保人
					tInsuredRelation = new VData();
					tInsuredRelation = (VData) tAfterResultsSet.get(i);
					if (tInsuredRelation != null && tInsuredRelation.size() > 0) {

						if (!dealOneContInsured(tInsuredRelation)) {
							return false;
						}

						tRiskRelations = new VData();
						tRiskRelations = (VData) tInsuredRelation
								.getObjectByObjectName("VData", 5);
						for (int j = 0; j < tRiskRelations.size(); j++) {// 多主险
							tRiskRelation = new VData();
							tRiskRelation = (VData) tRiskRelations.get(j);
							if (tRiskRelation != null) {
								// 处理险种信息，并返回豁免险信息
								tAllSpecRiskVData = DealOneMainRisk(tInsuredRelation,
										tRiskRelation, tFlag);
								if (tAllSpecRiskVData == null)
									return false;
								String tSpecRiskFlag1 = (String)tAllSpecRiskVData.getObjectByObjectName("String", 0);
								if(tSpecRiskFlag1!=null && !tSpecRiskFlag1.equals(""))
								{
									logger.debug("********************&&&&&&&&&有豁免险待处理&&&&&&&&&&&&********************");
									tSpecRiskFlag = tSpecRiskFlag1;
									tSpecRiskVData = (VData)tAllSpecRiskVData.getObjectByObjectName("VData", 0);
								}
									
							}
						}
						//处理被保人豁免险
						if(tSpecRiskFlag.equals("insured"))
						{
							RiskBasicInfo tRiskBasicInfo1 = (RiskBasicInfo)tSpecRiskVData.getObjectByObjectName("RiskBasicInfo", 0);
							VData tRiskRelation1 = (VData)tSpecRiskVData.getObjectByObjectName("VData", 0);
							tRiskRelation1.remove("VData");
							tRiskRelation1.add(tRiskBasicInfo1);
							// 处理险种信息
							if(!DealSpecRisk(tInsuredRelation,tRiskRelation1, tFlag , tSpecRiskFlag))
							{
								//CError.buildErr(this, "处理被保人豁免险信息失败！");
								logger.debug("处理被保人豁免险信息失败！");
								return false;
							}
								
						}
					}

				}
				//处理投保人豁免险
				if(tSpecRiskFlag.equals("appnt"))
				{
					RiskBasicInfo tRiskBasicInfo1 = (RiskBasicInfo)tSpecRiskVData.getObjectByObjectName("RiskBasicInfo", 0);
					VData tRiskRelation1 = (VData)tSpecRiskVData.getObjectByObjectName("VData", 0);
					tRiskRelation1.remove("VData");
					tRiskRelation1.add(tRiskBasicInfo1);
					// 处理险种信息
					if(!DealSpecRisk(tInsuredRelation,tRiskRelation1, tFlag, tSpecRiskFlag))
					{
						//CError.buildErr(this, "处理投保人豁免险信息失败！");
						logger.debug("处理投保人豁免险信息失败！");
						return false;
					}
				}
			}
			//修改合同表--被保人编码、险种编码等
			//生成保单原始数据 2009-2-10 ln add
			if(!updateLCCont(tPrtNo))
				return false;
			
			//所有数据保存完毕后的校验
			/*if(!checkAllImport(tOldLCPolSchema,tFlag))
				return false;*/
			///所有数据保存完毕后的校验
			//WBChangeRiskField tWBChangeRiskField = new WBChangeRiskField();
			if (!tWBChangeRiskField.checkAllSaveSpec(tOldLCPolSchema,tTransferData,tFlag)) {
				this.mErrors.copyAllErrors(tWBChangeRiskField.mErrors);
				return false;
			}
			
			// 如果异常件或者抽检件处理成功，修改任务处理表的状态
			if (tFlag == 1) {
				logger.debug("***  tDealType :  "	+ tDealType);
				if (!updateBPOState(tPrtNo, tDealType,
						"1", tGI)) {
					return false;
				}
			}

		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 将一个清洁件（一个被保人一个主险）导入到核心业务系统中
	 * 
	 * @param tVData
	 * @param tFlag
	 *            处理标志，0－表示系统正常导入，1－异常件、抽检件
	 * @return 如果导入出错，则返回false,否则，返回true
	 */
	public VData DealOneMainRisk(VData tInsuredRelation, VData tRiskRelation,
			int tFlag) {
		boolean tInsuredSpecFlag = false; // 是否需要对被保险人做特殊处理，如附加豁免需要对被保险人做特殊处理，值为true,正常主附险被保险人一致，则为false
		boolean tAppntSpecFlag = false; // 是否需要对投保人做特殊处理，如附加豁免需要对投保人做特殊处理，值为true,正常主附险被保险人一致，则为false
		boolean tTransSpecFlag = false; // tTransferData是否需要特殊处理
		LCPolSchema tMainLCPolSchema = new LCPolSchema(); // 主险信息
		TransferData tTransferDataSpec = new TransferData();
		LCDutySchema tLCDutySchema = new LCDutySchema();
		LCSpecSet tLCSpecSet = new LCSpecSet();
		Vector tSuccPolSet = new Vector(); // 处理成功的保单号码集合
		VData tAddFeeVData = new VData();// 记录加费相关信息
		VData tAllSpecRiskVData = new VData();// 存放投保人（或被保人）豁免险相关信息，以及是否有豁免险标志
		VData tSpecRiskVData = new VData();// 记录投保人（或被保人）豁免险相关信息，以便最后保存
		String tSpecRiskFlag = "";//标志是否有投保人（或被保人）豁免险，空为没有，appnt表示有投保人豁免险；insured表示有被保人豁免险，用于以后进行处理
		String tSpecRisk = "";//记录投保人豁免险险种编码，空为没有投保人豁免险（或记录被保人豁免险险种编码，空为没有被保人豁免险），用于判断现在不处理		
		
		try {
			// 处理险种信息
			LCContSchema tLCContSchema = new LCContSchema();
			LCContSchema tOldLCContSchema = new LCContSchema();
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LCInsuredSchema tLCInsuredSchemaSpec = new LCInsuredSchema(); // 需要对被保险人特殊处理的险种
			LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();
			LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet(); // 个单连带被保人表

			VData tOldRiskBasicInfoSet = (VData) tRiskRelation
					.getObjectByObjectName("VData", 0);
			LCPolSchema tLCPolSchema = (LCPolSchema) tRiskRelation
					.getObjectByObjectName("LCPolSchema", 0);
			
			LCBnfSet tLCBnfSet = (LCBnfSet) tRiskRelation
					.getObjectByObjectName("LCBnfSet", 0);
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			TransferData tTransferData = (TransferData) tRiskRelation
					.getObjectByObjectName("TransferData", 0);
//			String tDealType = (String) tTransferData
//					.getValueByName("DealType");
			logger.debug("***tFlag :  " + tFlag );
			GlobalInput tGI = (GlobalInput) tInsuredRelation
					.getObjectByObjectName("GlobalInput", 0);
			LCInsuredSet tLCInsuredSet = (LCInsuredSet) tInsuredRelation
					.getObjectByObjectName("LCInsuredSet", 0);
			if (tOldRiskBasicInfoSet == null
					|| tOldRiskBasicInfoSet.size() == 0) {
				CError.buildErr(this, "得到的险种为空！");
				return null;
			}

			// 查询合同信息
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tLCPolSchema.getPrtNo());
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "合同信息查询失败！");
				return null;
			}
			tOldLCContSchema.setSchema(tLCContDB.getSchema());			
			
			// 调整主附险顺序
			boolean tMainRiskFlag = false;

			for (int i = 0; i < tOldRiskBasicInfoSet.size(); i++) {
				RiskBasicInfo tRiskBasicInfo1 = new RiskBasicInfo();
				tRiskBasicInfo1 = (RiskBasicInfo) tOldRiskBasicInfoSet.get(i);
				if (tRiskBasicInfo1.isMainRisk()) // 如果主险不在第一位置
				{
					if (i == 0) {
						tMainRiskFlag = true;
						logger.debug("***主附险的位置无需调整！");
						break;
					}
					// 调整主附险的位置，将主险放在第一位，附加险放在后边
					RiskBasicInfo tRiskBasicInfoTemp = (RiskBasicInfo) tOldRiskBasicInfoSet
							.get(0);
					tOldRiskBasicInfoSet.set(0, tRiskBasicInfo1);
					tOldRiskBasicInfoSet.set(i, tRiskBasicInfoTemp);
					tMainRiskFlag = true;
					break;
				}
			}
			// 没有主险信息
			if (!tMainRiskFlag) {
				CError.buildErr(this, "没有传入主险的信息！");
				return null;
			}

			/***********************************附加豁免处理***********************************
			 * 分为投保人豁免和被保人豁免：lmriskapp 中 risktype7为空或零时表示一般险种，1表示投保人豁免险，2表示
			 * 被保人豁免险
			**/
			// 如果是被保人豁免险：调整附加险的位置，将附加豁免放到最后一位
			// 如果是投保人豁免险：从该被保人下所有险种中取出，最后处理
			// 如果投保人为第一被保人，则计算豁免保额的时候豁免的保费不用计算第一被保人的
			for (int i = 1; i < tOldRiskBasicInfoSet.size(); i++) {
				RiskBasicInfo tRiskBasicInfo1 = new RiskBasicInfo();
				tRiskBasicInfo1 = (RiskBasicInfo) tOldRiskBasicInfoSet.get(i);
				ExeSQL tExeSQL = new ExeSQL();
				String sql = "select case when risktype7 is null then '0' else risktype7 end from LMRiskApp where RiskCode='"
						+ "?RiskCode?" + "'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(sql);
				sqlbv.put("RiskCode", tRiskBasicInfo1.getRiskCode());
				String tRiskType = tExeSQL
						.getOneValue(sqlbv);
				if (tRiskType!=null && !tRiskType.equals("") && "2".equals(tRiskType)) {
					tSpecRisk = tRiskBasicInfo1.getRiskCode();//为被保人豁免险险种
					tSpecRiskVData.add(tRiskBasicInfo1);
					tSpecRiskVData.add(tRiskRelation.clone());	
					tSpecRiskFlag = "insured";
					break;
				}
				else if (tRiskType!=null && !tRiskType.equals("") && "1".equals(tRiskType)) {					
					tSpecRisk = tRiskBasicInfo1.getRiskCode();//为投保人豁免险险种
					tSpecRiskVData.add(tRiskBasicInfo1);
					tSpecRiskVData.add(tRiskRelation.clone());	
					tSpecRiskFlag = "appnt";
					break;
				}
			}
			
			//校验第二主险
			boolean tSFlag1 = false;
			boolean tSFlag2 = false;
			for (int i = 1; i < tOldRiskBasicInfoSet.size(); i++) {
				RiskBasicInfo tRiskBasicInfo1 = new RiskBasicInfo();
				tRiskBasicInfo1 = (RiskBasicInfo) tOldRiskBasicInfoSet.get(i);
				String tRiskCode = tRiskBasicInfo1.getRiskCode();
				if (tRiskCode!=null && !tRiskCode.equals(""))
				{
					if( "111801".equals(tRiskCode)) {
						if(i == (tOldRiskBasicInfoSet.size()-1))
						{}
						else
						{
							RiskBasicInfo tRiskBasicInfo2 = new RiskBasicInfo();
							tRiskBasicInfo2 = (RiskBasicInfo) tOldRiskBasicInfoSet.get(i+1);
							String tRiskCode2 = tRiskBasicInfo2.getRiskCode();
							if (tRiskCode2!=null && !tRiskCode2.equals("") && tRiskCode2.equals("121601"))
							{								
							}
							else
								tSFlag1 = true;
						}
					}
					else if( "121601".equals(tRiskCode)) {
						if(i == (tOldRiskBasicInfoSet.size()-1))
						{}
						else
						{
							RiskBasicInfo tRiskBasicInfo2 = new RiskBasicInfo();
							tRiskBasicInfo2 = (RiskBasicInfo) tOldRiskBasicInfoSet.get(i+1);
							String tRiskCode2 = tRiskBasicInfo2.getRiskCode();
							if (tRiskCode2!=null && !tRiskCode2.equals("") && tRiskCode2.equals("111801"))
							{								
							}
							else
								tSFlag2 = true;
						}
					}
				}
			}
			if(tSFlag1 == true)
			{
				CError.buildErr(this, "第二主险111801不能附加任何附加险！");
				return null;
			}
			if(tSFlag2 == true)
			{
				CError.buildErr(this, "第二主险121601不能附加任何附加险！");
				return null;
			}
			/**
			 * ////////////////down old////////////////////////////////////
			for (int i = 1; i < tOldRiskBasicInfoSet.size(); i++) {
				RiskBasicInfo tRiskBasicInfo1 = new RiskBasicInfo();
				tRiskBasicInfo1 = (RiskBasicInfo) tOldRiskBasicInfoSet.get(i);
				if ("121301".equals(tRiskBasicInfo1.getRiskCode())) {
					if (i == (tOldRiskBasicInfoSet.size() - 1)) // 如果附加豁免在最后一位置
					{
						logger.debug("***附加豁免"+tRiskBasicInfo1.getRiskCode()+"的位置无需调整！");
						break;
					}
					// 调整附加险的位置，将附加豁免在最后一位置
					RiskBasicInfo tRiskBasicInfoTemp = (RiskBasicInfo) tOldRiskBasicInfoSet
							.get(tOldRiskBasicInfoSet.size() - 1);
					tOldRiskBasicInfoSet.set(tOldRiskBasicInfoSet.size() - 1,
							tRiskBasicInfo1);
					tOldRiskBasicInfoSet.set(i, tRiskBasicInfoTemp);
					break;
				}
			}
             ///////////////////up old/////////////////////////////////////
            **/
			// 循环处理每一个险种，先主险，后附加险
			for (int i = 0; i < tOldRiskBasicInfoSet.size(); i++) {
				// 重置特殊处理标志
				tInsuredSpecFlag = false;
				tAppntSpecFlag = false;
				tTransSpecFlag = false;

				// LCContSchema
				tLCContSchema.setContNo(tLCPolSchema.getPrtNo());

				// LCAppntSchema
				tLCAppntSchema.setContNo(tOldLCContSchema.getContNo());
				tLCAppntSchema.setAppntNo(tOldLCContSchema.getAppntNo()); // 客户号
				tLCAppntSchema.setAppntName(tOldLCContSchema.getAppntName()); // 姓名
				tLCAppntSchema.setAppntSex(tOldLCContSchema.getAppntSex()); // 性别
				tLCAppntSchema.setAppntBirthday(tOldLCContSchema
						.getAppntBirthday()); // 出生日期

				// LCGrpAppntSchema
				tLCGrpAppntSchema.setCustomerNo("");
				tLCGrpAppntSchema.setGrpContNo("00000000000000000000");

				RiskBasicInfo tRiskBasicInfo = (RiskBasicInfo) tOldRiskBasicInfoSet
						.get(i);
				//add ln 2008-11-12
				if(!tSpecRisk.equals("") && tSpecRisk.equals(tRiskBasicInfo.getRiskCode()))
					continue;//如果为投保人豁免待以后被保人都处理完后再进行处理；如果为被保人豁免待该被保人下
				             //所有险种都处理完后在进行处理
				
				double inputPrem = tRiskBasicInfo.getPrem(); // 记录传入的保费
				double inputAmnt = tRiskBasicInfo.getAmnt(); // 记录传入的保额
				double inputMult = tRiskBasicInfo.getMult();
				tLCPolSchema.setContNo(tOldLCContSchema.getContNo());
				tLCPolSchema.setManageCom(tOldLCContSchema.getManageCom());
				tLCPolSchema.setRiskCode(tRiskBasicInfo.getRiskCode());
				tLCPolSchema.setMult(tRiskBasicInfo.getMult());
				tLCPolSchema.setPrem(tRiskBasicInfo.getPrem());
				//if(tFlag==0)
				{
					tLCPolSchema.setInputPrem(tRiskBasicInfo.getInputPrem());
				}
				tLCPolSchema.setSumPrem(tRiskBasicInfo.getPrem());
				tLCPolSchema.setAmnt(tRiskBasicInfo.getAmnt());
				tLCPolSchema.setCValiDate(tLCPolSchema.getPolApplyDate());
				tLCPolSchema.setInsuYear(tRiskBasicInfo.getInsuYear()); // 保险期间
				tLCPolSchema.setInsuYearFlag(tRiskBasicInfo.getInsuYearFlag());
				tLCPolSchema.setPayEndYear(tRiskBasicInfo.getPayEndYear()); // 交费年期
				tLCPolSchema.setPayEndYearFlag(tRiskBasicInfo
						.getPayEndYearFlag());
				tLCPolSchema.setRiskSequence(tRiskBasicInfo.getRiskNo()); // 主险序号
				
				tLCPolSchema.setAgentGroup(tOldLCContSchema.getAgentGroup());
				logger.debug("Start checkSaleChnl...");
				// 校验险种信息是否正确
				if (!checkSaleChnl(tLCPolSchema)) {
					return null;
				}			

				//初始化附加险的交费间隔
		        if(!tRiskBasicInfo.isMainRisk())
		        {
		          ExeSQL tExeSQL = new ExeSQL();
		          String sql = "select RiskPeriod from LMRiskApp where RiskCode='"+"?RiskCode?"+"'";
		          SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(sql);
					sqlbv.put("RiskCode", tLCPolSchema.getRiskCode());
		          String tRiskPeriod = tExeSQL.getOneValue(sqlbv);
		          if(!"L".equals(tRiskPeriod))  //如果附加险不是长期险,交费间隔为趸交，如果始长险，取主险的交费间隔
		          {
		            tLCPolSchema.setPayIntv(0);
		          }
		          else
		          {
		            tLCPolSchema.setPayIntv(tMainLCPolSchema.getPayIntv());
		          }
		        }
				
				logger.debug("Start initSpecRisk...");
				//tongmeng 2009-02-23 modify
				//险种转换单独使用WBChangeRiskField类
				WBChangeRiskField tWBChangeRiskField = new WBChangeRiskField();
				if(!tWBChangeRiskField.changeRiskFieldWB(tLCPolSchema, tTransferData, "WB"))
				{
					this.mErrors.copyAllErrors(tWBChangeRiskField.mErrors);
					return null;
				}
//				if (!initSpecRisk(tLCPolSchema, tTransferData, tFlag)) {
//					return null;
//				}

				logger.debug("Start checkSpecRisk...");
				// 校验特殊险种
				if (!tWBChangeRiskField.checkSpecRisk(tLCAppntSchema, tLCInsuredSet, tLCPolSchema,
						tTransferData, tRiskBasicInfo, tFlag)) {
					this.mErrors.copyAllErrors(tWBChangeRiskField.mErrors);
					return null;
				}

				tTransferData.setNameAndValue("deleteAccNo", "1");
				tTransferData.setNameAndValue("ChangePlanFlag", "1");
				tTransferData.setNameAndValue("SavePolType", "");
				tTransferData.setNameAndValue("getIntv", ""); // 领取间隔（方式）
				tTransferData.setNameAndValue("LoadFlag", null);
				tTransferData.setNameAndValue("SavePolType", null); // 保全保存标记
				tTransferData.setNameAndValue("EdorType", null);

				// 处理主险的数据
				if (tRiskBasicInfo.isMainRisk()) {
					// LCInsuredSchema
					tLCInsuredSchema.setContNo(tOldLCContSchema.getContNo());
					tLCInsuredSchema.setInsuredNo(tOldLCContSchema
							.getInsuredNo()); // 客户号

					// 受益人信息
					if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
						double[] sumLiveBnf = { 0.00, 0.00, 0.00, 0.00, 0.00,
								0.00, 0.00 }; // 各级别生存受益比例之和
						double[] sumDeadBnf = { 0.00, 0.00, 0.00, 0.00, 0.00,
								0.00, 0.00 }; // 各级别身故受益比例之和
						int maxLiveGrade = 0;//录入生存受益人的最大收益比例 初始化为0
						int maxDeadGrade = 0;//录入身故受益人的最大收益比例 初始化为0
						for (int k = 1; k <= tLCBnfSet.size(); k++) {
							LCBnfSchema tLCBnfSchema = tLCBnfSet.get(k);

							//身故受益人不能为被保人本人 ln 2009-05-15 add
							if ("1".equals(tLCBnfSchema.getBnfType())) {
								if(tLCBnfSchema.getRelationToInsured() == null || tLCBnfSchema.getRelationToInsured().equals(""))
								{
									logger.debug("受益人与被保人关系未填写，不做校验。。。。。。。");
								}
								else if(tLCBnfSchema.getRelationToInsured().equals("00"))
								{
									CError.buildErr(this, "身故受益人不能为被保人本人！");
									return null;
								}
							}
							
							// 校验受益人姓名中是否包含系统无法转换的生僻字
							if (StrTool.cTrim(tLCBnfSchema.getName()).indexOf(
									"?") != -1
									|| StrTool.cTrim(tLCBnfSchema.getName())
											.indexOf("？") != -1
									|| StrTool.cTrim(tLCBnfSchema.getName())
											.indexOf("M-}") != -1) {
								CError.buildErr(this, "受益人姓名中可能包含系统无法转换的生僻字！");
								return null;
							}

							// 受益人身份证校验
							if ("0".equals(tLCBnfSchema.getIDType())) {
								if (tLCBnfSchema.getIDNo() == null
										|| "".equals(tLCBnfSchema.getIDNo())) {
									CError.buildErr(this,
											"受益人的证件为身份证号，但证件号码为空！");
									return null;
								}

								if (tLCBnfSchema.getIDNo().length() != 15
										&& tLCBnfSchema.getIDNo().length() != 18) {
									CError.buildErr(this, "受益人的身份证号位数错误！");
									return null;
								}
							}

							if ("0".equals(tLCBnfSchema.getBnfType())) {
								sumLiveBnf[Integer.parseInt(tLCBnfSchema
										.getBnfGrade())] += tLCBnfSchema
										.getBnfLot();
								if(maxLiveGrade < Integer.parseInt(tLCBnfSchema.getBnfGrade()))
									maxLiveGrade = Integer.parseInt(tLCBnfSchema.getBnfGrade());
							} else {
								sumDeadBnf[Integer.parseInt(tLCBnfSchema
										.getBnfGrade())] += tLCBnfSchema
										.getBnfLot();
								if(maxDeadGrade < Integer.parseInt(tLCBnfSchema.getBnfGrade()))
									maxDeadGrade = Integer.parseInt(tLCBnfSchema.getBnfGrade());
							}
						}
						
						for (int m = 1; m <= maxLiveGrade; m++) {
							if (PubFun.round(sumLiveBnf[m], 2) -1 != 0) {
								logger.debug("生存受益人受益顺序 " + m
										+ " 的受益比例和为：" + sumLiveBnf[m]);
								CError.buildErr(this, "生存受益人受益顺序 " + m
										+ " 的受益比例和为：" + PubFun.round(sumLiveBnf[m], 2)
										+ " 不为100%！");
								return null;
							}
						}
						for (int n = 1; n <= maxDeadGrade; n++) {
							if (PubFun.round(sumDeadBnf[n], 2) -1 != 0) {
								logger.debug("身故受益人受益顺序 " + n
										+ " 的受益比例和为：" + sumDeadBnf[n]);
								CError.buildErr(this, "身故受益人受益顺序 " + n
										+ " 的受益比例和为：" + PubFun.round(sumDeadBnf[n], 2)
										+ " 不为100%！");
								return null;
							}
						}
					} // end 受益人校验
				} else // 附加险特殊出始化
				{					
					// 如果主险保单号为空
					if (tMainLCPolSchema.getPolNo() == null
							|| "".equals(tMainLCPolSchema.getPolNo())) {
						CError.buildErr(this, "主险保单号码为空！");
						return null;
					}
					tLCPolSchema.setMainPolNo(tMainLCPolSchema.getPolNo());
					tLCPolSchema.setBonusGetMode("");
					tLCPolSchema.setLiveGetMode("");
					
					logger.debug("Start checkSubSpecRisk...");						
			          
			       // 校验特殊险种
						if (!tWBChangeRiskField.checkSubSpecRisk(tLCAppntSchema, tLCInsuredSet, tLCPolSchema,
								tMainLCPolSchema, tTransferData, tRiskBasicInfo, tFlag)) {
							this.mErrors.copyAllErrors(tWBChangeRiskField.mErrors);
							return null;
						}

					// 校验主附险的关系
//					 if(!checkRiskRelation(tLCPolSchema.getMainPolNo(),tLCPolSchema.getRiskCode()))
//					 {
//					 CError.buildErr(this, "主附险包含关系错误，主险不能带这个附加险！");
//					 return null;
//					 }

					String tGetDutyKind = (String) tTransferData
							.getValueByName("GetDutyKind");
					if (tGetDutyKind!=null && !"".equals(StrTool.cTrim(tGetDutyKind))) {
						tTransSpecFlag = true;
						tTransferDataSpec = new TransferData();
						if (!copyTransferData(tTransferData, tTransferDataSpec)) {
							tTransferDataSpec.removeByName("GetDutyKind");
							tTransferDataSpec
									.setNameAndValue("GetDutyKind", "");
						}
					}					

					tLCBnfSet = new LCBnfSet();
					tLCCustomerImpartSet = new LCCustomerImpartSet();
				}

				tLCDutySchema.setPayIntv(tLCPolSchema.getPayIntv()); // 交费方式
				tLCDutySchema.setInsuYear(tLCPolSchema.getInsuYear()); // 保险期间
				tLCDutySchema.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
				tLCDutySchema.setPayEndYear(tLCPolSchema.getPayEndYear()); // 交费年期
				tLCDutySchema.setPayEndYearFlag(tLCPolSchema
						.getPayEndYearFlag());
				tLCDutySchema.setGetYear(tLCPolSchema.getGetYear()); // 年金开始领取年龄
				tLCDutySchema.setGetYearFlag(tLCPolSchema.getGetYearFlag());
				tLCDutySchema.setGetStartType("");
				tLCDutySchema.setFloatRate(1);

				VData newVData = new VData();

				/*if (tInsuredSpecFlag) // 如果需要对被保险人特殊处理的附加险险种，特殊准备
				{
					newVData.addElement(tLCInsuredSchemaSpec);
				} else // 正常数据
				{
					newVData.addElement(tLCInsuredSchema);
				}

				if (tTransSpecFlag) {
					newVData.addElement(tTransferDataSpec);
				} else {
					newVData.addElement(tTransferData);
				}*/

				newVData.addElement(tLCInsuredSchema);
				newVData.addElement(tTransferData);
				
				newVData.addElement(tLCContSchema);
				newVData.addElement(tLCPolSchema);
				newVData.addElement(tLCAppntSchema);
				newVData.addElement(tLCGrpAppntSchema);
				newVData.addElement(tLCInsuredRelatedSet);
				newVData.addElement(tLCBnfSet);
				newVData.addElement(tLCCustomerImpartSet);
				newVData.addElement(tLCSpecSet);
				newVData.addElement(tGI);
				newVData.addElement(tLCDutySchema);
//****************************************************************************************
				ProposalUI tProposalUI = new ProposalUI();
				if (!tProposalUI.submitData(newVData, "INSERT||PROPOSAL")) {
					CError.buildErr(this, "保存失败，原因是: "
							+ tProposalUI.mErrors.getError(0).errorMessage);
					return null;
				}
				logger.debug("tLCContSchema.encode(): "
						+ tLCContSchema.encode());
				logger.debug("tLCPolSchema.encode(): "
						+ tLCPolSchema.encode());
				logger.debug("tLCAppntSchema.encode(): "
						+ tLCAppntSchema.encode());
				logger.debug("tLCGrpAppntSchema.encode(): "
						+ tLCGrpAppntSchema.encode());
				logger.debug("tLCInsuredSchema.encode(): "
						+ tLCInsuredSchema.encode());
				logger.debug("tLCBnfSet.encode(): " + tLCBnfSet.encode());
				logger.debug("tLCCustomerImpartSet.encode(): "
						+ tLCCustomerImpartSet.encode());
				logger.debug("tLCDutySchema.encode(): "
						+ tLCDutySchema.encode());
				logger.debug("tLCInsuredRelatedSet.encode(): "
						+ tLCInsuredRelatedSet.encode());
				logger.debug("tLCSpecSet.encode(): "
						+ tLCSpecSet.encode());

				newVData.clear();
				newVData = tProposalUI.getResult();

				// 保单信息
				LCPolSchema tReturnLCPolSchema = ((LCPolSchema) newVData
						.getObjectByObjectName("LCPolSchema", 0));				

				// 记录导入成功的保单号
				tSuccPolSet.add(tReturnLCPolSchema.getPolNo());

				// 记录主险信息
				if (tRiskBasicInfo.isMainRisk()) {
					tMainLCPolSchema = tReturnLCPolSchema;
				}

				// 校验计算保费与录入保费是否一致
				double caculatePrem = PubFun.round(tReturnLCPolSchema.getPrem(), 2);
				logger.debug("****inputPrem: " + inputPrem
						+ "\n****inputAmnt: " + inputAmnt + "\n****inputMult: "
						+ inputMult);
				logger.debug("****tReturnLCPolSchema.getPrem(): "
						+ tReturnLCPolSchema.getPrem()
						+ "\n****tReturnLCPolSchema.getAmnt(): "
						+ tReturnLCPolSchema.getAmnt());

				if (inputPrem != caculatePrem) {
					CError
							.buildErr(this, "保存失败，原因是: 录入保费：" + inputPrem
									+ "与系统计算保费:" + caculatePrem
									+ "不一致");
					logger.debug("保存失败，原因是: 录入保费：" + inputPrem + "与系统计算保费:"
									+ caculatePrem + "不一致");
					return null;
				}
				// 校验计算保额与录入保额是否一致(只对个险和中介校验)
				if (tReturnLCPolSchema.getPrtNo() != null
						&& tReturnLCPolSchema.getPrtNo().trim().length() == 14
						&& ("11".equals(tReturnLCPolSchema.getPrtNo()
								.substring(2, 4)) || "21"
								.equals(tReturnLCPolSchema.getPrtNo()
										.substring(2, 4)))
						&& (inputMult == 0 && inputAmnt >= 0) // 按份数卖的险种不做保额校验
						&& inputAmnt != tReturnLCPolSchema.getAmnt()) {
					CError
							.buildErr(this, "保存失败，原因是: 录入保额：" + inputAmnt
									+ "与系统计算保额:" + tReturnLCPolSchema.getAmnt()
									+ "不一致");
					logger.debug("保存失败，原因是: 录入保额：" + inputAmnt + "与系统计算保额:"
									+ tReturnLCPolSchema.getAmnt() + "不一致");
					return null;
				}

				// 对于正常件，修改保单状态为复核已通过
				if (tFlag == 0) {
					VData tResult = new VData();
					PubSubmit tPubSubmit = new PubSubmit();
					MMap map = new MMap();
					String str1 = "update LCPol set ApproveCode='AUTO',ApproveFlag='9',ApproveDate='"
							+ "?ApproveDate?"
							+ "',ModifyDate='"
							+ "?ModifyDate?"
							+ "',ModifyTime='"
							+ "?ModifyTime?"
							+ "' where PolNo='"
							+ "?PolNo?" + "'";
			          SQLwithBindVariables sqlbv = new SQLwithBindVariables();
						sqlbv.sql(str1);
						sqlbv.put("ApproveDate", PubFun.getCurrentDate());
						sqlbv.put("ModifyDate", PubFun.getCurrentDate());
						sqlbv.put("ModifyTime", PubFun.getCurrentTime());
						sqlbv.put("PolNo", tReturnLCPolSchema.getPolNo());
					map.put(sqlbv, "UPDATE");
					tResult.add(map);
					if (!tPubSubmit.submitData(tResult, "")) {
						CError.buildErr(this, "保单复核状态修改失败，原因是: "
								+ tPubSubmit.mErrors.getError(0).errorMessage);
						return null;
					}
				}
				// 准备加费处理需要的数据
				tAddFeeVData.clear();
				tAddFeeVData.add(tReturnLCPolSchema);// 处理加费需要记录保单信息
				tAddFeeVData.add(tRiskBasicInfo);// 处理加费需要记录险种信息
	//***************************处理加费信息********************************************************
				if (tAddFeeVData == null)
					return null;
				// 处理加费信息
				tAddFeeVData.add(tGI);
				if(!DealAddFee(tAddFeeVData))
				{
					CError.buildErr(this, "尝试处理加费信息时发生错误！");
					return null;
				}				
	//***************************处理加费信息完毕********************************************************			
			}
			tAllSpecRiskVData.add(tSpecRiskVData);
			tAllSpecRiskVData.add(tSpecRiskFlag);			
			
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return null;
		} finally {
			logger.debug("****准备装入已经导入的主险保单号码（未处理加费）*****");
			logger.debug("****MainLCPolSchema.getPolNo(): "
					+ tMainLCPolSchema.getPolNo());
			logger.debug("****已导入险种数（未处理豁免）: " + tSuccPolSet.size());
			if (tMainLCPolSchema.getPolNo() != null
					&& !"".equals(tMainLCPolSchema.getPolNo())
					&& tSuccPolSet.size() > 0) {
				mSuccPolNoData.setNameAndValue(tMainLCPolSchema.getPrtNo(),
						tSuccPolSet);
			}
		}
		return tAllSpecRiskVData;
	}
	
	/**
	 * 处理豁免险
	 * 
	 * @param tVData
	 * @param tFlag
	 *            处理标志，0－表示系统正常导入，1－异常件、抽检件
	 * @return 如果导入出错，则返回false,否则，返回true
	 */
	public boolean DealSpecRisk(VData tInsuredRelation, VData tRiskRelation,
			int tFlag, String tSpecRiskFlag) {
		boolean tInsuredSpecFlag = false; // 是否需要对被保险人做特殊处理，如附加豁免需要对被保险人做特殊处理，值为true,正常主附险被保险人一致，则为false
		boolean tAppntSpecFlag = false; // 是否需要对投保人做特殊处理，如附加豁免需要对投保人做特殊处理，值为true,正常主附险被保险人一致，则为false
		boolean tTransSpecFlag = false; // tTransferData是否需要特殊处理
		LCPolSchema tMainLCPolSchema = new LCPolSchema(); // 主险信息
		TransferData tTransferDataSpec = new TransferData();
		LCDutySchema tLCDutySchema = new LCDutySchema();
		LCSpecSet tLCSpecSet = new LCSpecSet();
		Vector tSuccPolSet = new Vector(); // 处理成功的保单号码集合		
		
		try {
			// 处理险种信息
			LCContSchema tLCContSchema = new LCContSchema();
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LCInsuredSchema tLCInsuredSchemaSpec = new LCInsuredSchema(); // 需要对被保险人特殊处理的险种
			LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();
			LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet(); // 个单连带被保人表

			RiskBasicInfo tOldRiskBasicInfo = (RiskBasicInfo) tRiskRelation
					.getObjectByObjectName("RiskBasicInfo", 0);
			LCPolSchema tLCPolSchema = (LCPolSchema) tRiskRelation
					.getObjectByObjectName("LCPolSchema", 0);
//			if(tFlag==0)
//			{
//				tLCPolSchema.setInputPrem(tLCPolSchema.getPrem());
//			}
			LCBnfSet tLCBnfSet = (LCBnfSet) tRiskRelation
					.getObjectByObjectName("LCBnfSet", 0);
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			TransferData tTransferData = (TransferData) tRiskRelation
					.getObjectByObjectName("TransferData", 0);
			GlobalInput tGI = (GlobalInput) tInsuredRelation
					.getObjectByObjectName("GlobalInput", 0);
			LCInsuredSet tLCInsuredSetOld = (LCInsuredSet) tInsuredRelation
					.getObjectByObjectName("LCInsuredSet", 0);

			// 查询合同信息
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tLCPolSchema.getPrtNo());
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "合同信息查询失败！");
				return false;
			}
			tLCContSchema.setSchema(tLCContDB.getSchema());
			
//**********************************对投保人豁免、被保人豁免分别准备主险保单数据和校验保额********************************************
			if(tSpecRiskFlag.equals("insured"))
			{				
				//**********************************处理被保人豁免********************************************
				String tInsuredNo = tLCContSchema.getInsuredNo();//得到当前被保人编号
				logger.debug("********************&&&&&&&&&开始处理被保人"+tInsuredNo+"豁免险&&&&&&&&&&&&********************");
				//查询该被保人险种信息
				LCPolDB tLCPolDB = new LCPolDB();
				String tSql = "select * from lcpol where prtno='"+"?prtno?"+"' and insuredno='"+"?insuredno?"+"'"
				           +" and polno=mainpolno and (RiskSequence is null or RiskSequence in ('1','-1'))";
				 SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(tSql);
					sqlbv.put("prtno", tLCPolSchema.getPrtNo());
					sqlbv.put("insuredno", tInsuredNo);
				LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv);
				if (tLCPolSet.size()!=1) {
					CError.buildErr(this, "lcpol表查询失败！");
					return false;
				}
				//得到主险保单			
				tMainLCPolSchema = tLCPolSet.get(1);
				
				//查询该被保人信息
				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				LCInsuredSet tLCInsuredSet = new LCInsuredSet();
				tLCInsuredDB.setContNo(tLCContSchema.getContNo());
				tLCInsuredDB.setInsuredNo(tInsuredNo);
				tLCInsuredSet = tLCInsuredDB.query();
				if (tLCInsuredSet.size()!=1) {
					CError.buildErr(this, "被保人"+tInsuredNo+"信息查询失败！");
					return false;
				}
				tLCInsuredSchema = tLCInsuredSet.get(1);
			}
			else if(tSpecRiskFlag.equals("appnt"))
			{
				logger.debug("********************&&&&&&&&&开始处理投保人豁免险&&&&&&&&&&&&********************");
				//**********************************处理投保人豁免********************************************
				//查询第一被保人险种信息
				LCPolDB tLCPolDB = new LCPolDB();
				String tSql = "select * from lcpol where prtno='"+"?prtno?"+"' and insuredno="
				           +" (select insuredno from lcinsured where prtno='"+"?insuredno?"+"' and sequenceno in('1','-1'))"
				           +" and polno=mainpolno and (RiskSequence is null or RiskSequence in ('1','-1'))";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("prtno", tLCPolSchema.getPrtNo());
				sqlbv.put("insuredno", tLCPolSchema.getPrtNo());
				LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv);
				if (tLCPolSet.size()!=1) {
					CError.buildErr(this, "lcpol表查询失败！");
					return false;
				}
				//得到主险保单			
				tMainLCPolSchema = tLCPolSet.get(1);
				
				//查询第一被保人信息
				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				tSql = "select * from lcinsured where prtno='"+"?prtno?"+"' and sequenceno in ('1','-1')";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSql);
				sqlbv1.put("prtno", tLCPolSchema.getPrtNo());
				LCInsuredSet tLCInsuredSet = tLCInsuredDB.executeQuery(sqlbv1);
				if (tLCInsuredSet.size()!=1) {
					CError.buildErr(this, "第一被保人信息查询失败！");
					return false;
				}
				tLCInsuredSchema = tLCInsuredSet.get(1);
			}			
			
			tLCPolSchema.setMainPolNo(tMainLCPolSchema.getPolNo());
			tLCPolSchema.setBonusGetMode("");
			tLCPolSchema.setLiveGetMode("");				
			
			String tGetDutyKind = (String) tTransferData
							.getValueByName("GetDutyKind");
			if (tGetDutyKind!=null && !"".equals(StrTool.cTrim(tGetDutyKind))) 
			{
						tTransSpecFlag = true;
						tTransferDataSpec = new TransferData();
						if (!copyTransferData(tTransferData, tTransferDataSpec)) {
							tTransferDataSpec.removeByName("GetDutyKind");
							tTransferDataSpec
									.setNameAndValue("GetDutyKind", "");
						}
			}
			
			// 重置特殊处理标志
			tInsuredSpecFlag = false;
			tAppntSpecFlag = false;
			tTransSpecFlag = false;

			// LCAppntSchema
			tLCAppntSchema.setContNo(tLCContSchema.getContNo());
			tLCAppntSchema.setAppntNo(tLCContSchema.getAppntNo()); // 客户号
			tLCAppntSchema.setAppntName(tLCContSchema.getAppntName()); // 姓名
			tLCAppntSchema.setAppntSex(tLCContSchema.getAppntSex()); // 性别
			tLCAppntSchema.setAppntBirthday(tLCContSchema
					.getAppntBirthday()); // 出生日期

			// LCGrpAppntSchema
			tLCGrpAppntSchema.setCustomerNo("");
			tLCGrpAppntSchema.setGrpContNo("00000000000000000000");

			double inputPrem = tOldRiskBasicInfo.getPrem(); // 记录传入的保费
			double inputAmnt = tOldRiskBasicInfo.getAmnt(); // 记录传入的保额
			double inputMult = tOldRiskBasicInfo.getMult();
			tLCPolSchema.setContNo(tLCContSchema.getContNo());
			tLCPolSchema.setRiskCode(tOldRiskBasicInfo.getRiskCode());
			tLCPolSchema.setMult(tOldRiskBasicInfo.getMult());
			tLCPolSchema.setPrem(tOldRiskBasicInfo.getPrem());
			tLCPolSchema.setAmnt(tOldRiskBasicInfo.getAmnt());
			//if(tFlag==0)
			//{
				tLCPolSchema.setInputPrem(tOldRiskBasicInfo.getInputPrem());
			//}

			tLCPolSchema.setCValiDate(tLCPolSchema.getPolApplyDate());
			tLCPolSchema.setInsuYear(tOldRiskBasicInfo.getInsuYear()); // 保险期间
			tLCPolSchema.setInsuYearFlag(tOldRiskBasicInfo.getInsuYearFlag());
			tLCPolSchema.setPayEndYear(tOldRiskBasicInfo.getPayEndYear()); // 交费年期
			tLCPolSchema.setPayEndYearFlag(tOldRiskBasicInfo
					.getPayEndYearFlag());
			tLCPolSchema.setRiskSequence(tOldRiskBasicInfo.getRiskNo()); // 主险序号

			// 代理人
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				CError.buildErr(this, "代理人编码错误"
						+ tLCPolSchema.getAgentCode());
				return false;
			}
			tLCPolSchema.setAgentGroup(tLAAgentDB.getAgentGroup());
			
			// 初始化附加险的交费间隔
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("select RiskPeriod from LMRiskApp where RiskCode='"
					+ "?RiskCode?" + "'");
			sqlbv1.put("RiskCode", tLCPolSchema.getRiskCode());
			String tRiskPeriod = tExeSQL
						.getOneValue(sqlbv1);
			
				if (tRiskPeriod!=null && !"L".equals(tRiskPeriod)) // 如果附加险不是长期险,交费间隔为趸交，如果始长险，取主险的交费间隔
				{
					tLCPolSchema.setPayIntv(0);
				} else {
					tLCPolSchema.setPayIntv(tMainLCPolSchema.getPayIntv());
				}

			logger.debug("Start initSpecRisk...");
			//tongmeng 2009-02-23 modify
			//险种转换单独使用WBChangeRiskField类
			WBChangeRiskField tWBChangeRiskField = new WBChangeRiskField();
			if(!tWBChangeRiskField.changeRiskFieldWB(tLCPolSchema, tTransferData, "WB"))
			{
				this.mErrors.copyAllErrors(tWBChangeRiskField.mErrors);
				return false;
			}
//			if (!initSpecRisk(tLCPolSchema, tTransferData, tFlag)) {
//				return false;
//			}

			logger.debug("Start checkSpecRisk...");
			// 校验特殊险种
			if (!tWBChangeRiskField.checkSpecRisk(tLCAppntSchema, tLCInsuredSetOld, tLCPolSchema,
					tTransferData, tOldRiskBasicInfo, tFlag)) {
				this.mErrors.copyAllErrors(tWBChangeRiskField.mErrors);
				return false;
			}

			tTransferData.setNameAndValue("deleteAccNo", "1");
			tTransferData.setNameAndValue("ChangePlanFlag", "1");
			tTransferData.setNameAndValue("SavePolType", "");
			tTransferData.setNameAndValue("getIntv", ""); // 领取间隔（方式）
			tTransferData.setNameAndValue("LoadFlag", null);
			tTransferData.setNameAndValue("SavePolType", null); // 保全保存标记
			tTransferData.setNameAndValue("EdorType", null);

			tLCBnfSet = new LCBnfSet();
			tLCCustomerImpartSet = new LCCustomerImpartSet();

			// LCInsured
			/*if ("121301".equals(tLCPolSchema.getRiskCode())) // 附加豁免特殊处理
			{
				tInsuredSpecFlag = true;
				tAppntSpecFlag = true;

				// LCInsuredSchema
				tLCInsuredSchemaSpec.setContNo(tLCContSchema
						.getContNo());
				tLCInsuredSchemaSpec.setInsuredNo(tLCContSchema
						.getAppntNo()); // 客户号
				tLCInsuredSchemaSpec.setRelationToAppnt("00");

				if (tTransSpecFlag) {
					tTransferDataSpec.removeByName("samePersonFlag");
					tTransferDataSpec.setNameAndValue("samePersonFlag",
							"1"); // 投保人同被保人标志
				} else {
					tTransSpecFlag = true;
					tTransferDataSpec = new TransferData();
					if (!copyTransferData(tTransferData,
							tTransferDataSpec)) {
						tTransferDataSpec
								.removeByName("samePersonFlag");
						tTransferDataSpec.setNameAndValue(
								"samePersonFlag", "1"); // 投保人同被保人标志
					}
				}				

			}	*/				
			
			 if(tSpecRiskFlag.equals("insured"))
				{
					//**********************************校验被保人豁免保额********************************************
				    //查询该被保人豁免险可豁免的险种保单，用于计算豁免保额				
					String tSql = "select (case when sum(prem) is null then 0 else sum(prem) end) from lcpol where prtno='"+"?prtno?"+"' and insuredno='"+"?insuredno?"+"'"
							+ " and riskcode in (select code1 from ldcode1 where codetype='freerisk' and code = '"
							+ "?code?"+ "') ";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tSql);
					sqlbv2.put("prtno", tLCPolSchema.getPrtNo());
					sqlbv2.put("insuredno", tLCContSchema.getInsuredNo());
					sqlbv2.put("code", tLCPolSchema.getRiskCode());
					tExeSQL = new ExeSQL();
					double tSubAmnt = Double.parseDouble(tExeSQL
							.getOneValue(sqlbv2));
					if (tSubAmnt==0) {
						logger.debug("被保人"+tLCContSchema.getInsuredNo()+"不能带豁免险"+ tLCPolSchema.getRiskCode());
						tSql = "select riskname from lmriskapp where riskcode='"+"?riskcode?"+"' ";
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(tSql);
						sqlbv3.put("riskcode", tLCPolSchema.getRiskCode());
				        tExeSQL = new ExeSQL();
				        String tRiskName = tExeSQL.getOneValue(sqlbv3);
						CError.buildErr(this, "该主险不能附加"+ tRiskName);
						return false;
					}
					else if (tLCPolSchema.getAmnt() != tSubAmnt) {
						logger.debug("************** 计算保额（tSubAmnt）:"+tSubAmnt+"录入豁免保额"+tLCPolSchema.getAmnt());
						tSql = "select riskname from lmriskapp where riskcode='"+"?riskcode?"+"' ";
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(tSql);
						sqlbv3.put("riskcode", tLCPolSchema.getRiskCode());
						
				        tExeSQL = new ExeSQL();
				        String tRiskName = tExeSQL.getOneValue(sqlbv3);
						CError.buildErr(this, tRiskName+"的保额有误，应为"+tSubAmnt+"！");
						return false;
					}
				}
				else if(tSpecRiskFlag.equals("appnt"))
				{
					//**********************************校验投保人豁免保额********************************************
					//查询投保人豁免险可豁免的险种保单，用于计算豁免保额				
					String tSql = "select (case when sum(prem) is null then 0 else sum(prem) end) from lcpol where prtno='"+"?prtno?"+"' "
							+ " and riskcode in (select code1 from ldcode1 where codetype='freerisk' and code = '"
							+ "?code?"+ "') ";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tSql);
					sqlbv2.put("prtno", tLCPolSchema.getPrtNo());
					sqlbv2.put("code", tLCPolSchema.getRiskCode());
					tExeSQL = new ExeSQL();
					double tSubAmnt = Double.parseDouble(tExeSQL
							.getOneValue(sqlbv2));
					if (tSubAmnt==0) {
						logger.debug("该保单"+tLCContSchema.getContNo()+"不能带豁免险"+ tLCPolSchema.getRiskCode());
						tSql = "select riskname from lmriskapp where riskcode='"+"?riskcode?"+"' ";
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(tSql);
						sqlbv3.put("riskcode", tLCPolSchema.getRiskCode());
				        tExeSQL = new ExeSQL();
				        String tRiskName = tExeSQL.getOneValue(sqlbv3);
						CError.buildErr(this, "该主险不能附加"+ tRiskName);
						return false;
					}
					else if (tLCPolSchema.getAmnt() != tSubAmnt) {
						logger.debug("************** 计算保额（tSubAmnt）:"+tSubAmnt+"录入豁免保额"+tLCPolSchema.getAmnt());
						tSql = "select riskname from lmriskapp where riskcode='"+"?riskcode?"+"' ";
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(tSql);
						sqlbv3.put("riskcode", tLCPolSchema.getRiskCode());
				        tExeSQL = new ExeSQL();
				        String tRiskName = tExeSQL.getOneValue(sqlbv3);
						CError.buildErr(this, tRiskName+"的保额有误，应为"+tSubAmnt+"！");
						return false;
					}
				}
			
				tLCDutySchema.setPayIntv(tLCPolSchema.getPayIntv()); // 交费方式
				tLCDutySchema.setInsuYear(tLCPolSchema.getInsuYear()); // 保险期间
				tLCDutySchema.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
				tLCDutySchema.setPayEndYear(tLCPolSchema.getPayEndYear()); // 交费年期
				tLCDutySchema.setPayEndYearFlag(tLCPolSchema
						.getPayEndYearFlag());
				tLCDutySchema.setGetYear(tLCPolSchema.getGetYear()); // 年金开始领取年龄
				tLCDutySchema.setGetYearFlag(tLCPolSchema.getGetYearFlag());
				tLCDutySchema.setGetStartType("");
				tLCDutySchema.setFloatRate(1);

				VData newVData = new VData();

				/*if (tInsuredSpecFlag) // 如果需要对被保险人特殊处理的附加险险种，特殊准备
				{
					newVData.addElement(tLCInsuredSchemaSpec);
				} else // 正常数据
				{
					newVData.addElement(tLCInsuredSchema);
				}

				if (tTransSpecFlag) {
					newVData.addElement(tTransferDataSpec);
				} else {
					newVData.addElement(tTransferData);
				}*/
				newVData.addElement(tLCInsuredSchema);
				newVData.addElement(tTransferData);

				newVData.addElement(tLCContSchema);
				newVData.addElement(tLCPolSchema);
				newVData.addElement(tLCAppntSchema);
				newVData.addElement(tLCGrpAppntSchema);
				newVData.addElement(tLCInsuredRelatedSet);
				newVData.addElement(tLCBnfSet);
				newVData.addElement(tLCCustomerImpartSet);
				newVData.addElement(tLCSpecSet);
				newVData.addElement(tGI);
				newVData.addElement(tLCDutySchema);
//****************************************************************************************
				ProposalUI tProposalUI = new ProposalUI();
				if (!tProposalUI.submitData(newVData, "INSERT||PROPOSAL")) {
					CError.buildErr(this, "保存失败，原因是: "
							+ tProposalUI.mErrors.getError(0).errorMessage);
					return false;
				}
				logger.debug("tLCContSchema.encode(): "
						+ tLCContSchema.encode());
				logger.debug("tLCPolSchema.encode(): "
						+ tLCPolSchema.encode());
				logger.debug("tLCAppntSchema.encode(): "
						+ tLCAppntSchema.encode());
				logger.debug("tLCGrpAppntSchema.encode(): "
						+ tLCGrpAppntSchema.encode());
				logger.debug("tLCInsuredSchema.encode(): "
						+ tLCInsuredSchema.encode());
				logger.debug("tLCBnfSet.encode(): " + tLCBnfSet.encode());
				logger.debug("tLCCustomerImpartSet.encode(): "
						+ tLCCustomerImpartSet.encode());
				logger.debug("tLCDutySchema.encode(): "
						+ tLCDutySchema.encode());
				logger.debug("tLCInsuredRelatedSet.encode(): "
						+ tLCInsuredRelatedSet.encode());
				logger.debug("tLCSpecSet.encode(): "
						+ tLCSpecSet.encode());

				newVData.clear();
				newVData = tProposalUI.getResult();

				// 保单信息
				LCPolSchema tReturnLCPolSchema = ((LCPolSchema) newVData
						.getObjectByObjectName("LCPolSchema", 0));				

				// 记录导入成功的保单号
				tSuccPolSet.add(tReturnLCPolSchema.getPolNo());

				// 校验计算保费与录入保费是否一致
				double caculatePrem = PubFun.round(tReturnLCPolSchema.getPrem(), 2);
				logger.debug("****inputPrem: " + inputPrem
						+ "\n****inputAmnt: " + inputAmnt + "\n****inputMult: "
						+ inputMult);
				logger.debug("****tReturnLCPolSchema.getPrem(): "
						+ tReturnLCPolSchema.getPrem()
						+ "\n****tReturnLCPolSchema.getAmnt(): "
						+ tReturnLCPolSchema.getAmnt());

				if (inputPrem != caculatePrem) {
					CError.buildErr(this, "保存失败，原因是: 录入保费：" + inputPrem
									+ "与系统计算保费:" + caculatePrem
									+ "不一致");
					logger.debug("保存失败，原因是: 录入保费：" + inputPrem + "与系统计算保费:"
									+ caculatePrem + "不一致");
					return false;
				}
				// 校验计算保额与录入保额是否一致(只对个险和中介校验)
				if (tReturnLCPolSchema.getPrtNo() != null
						&& tReturnLCPolSchema.getPrtNo().trim().length() == 14
						&& ("11".equals(tReturnLCPolSchema.getPrtNo()
								.substring(2, 4)) || "21"
								.equals(tReturnLCPolSchema.getPrtNo()
										.substring(2, 4)))
						&& (inputMult == 0 && inputAmnt >= 0) // 按份数卖的险种不做保额校验
						&& inputAmnt != tReturnLCPolSchema.getAmnt()) {
					CError
							.buildErr(this, "保存失败，原因是: 录入保额：" + inputAmnt
									+ "与系统计算保额:" + tReturnLCPolSchema.getAmnt()
									+ "不一致");
					logger.debug("保存失败，原因是: 录入保额：" + inputAmnt + "与系统计算保额:"
									+ tReturnLCPolSchema.getAmnt() + "不一致");
					return false;
				}

				// 对于正常件，修改保单状态为复核已通过
				if (tFlag == 0) {
					VData tResult = new VData();
					PubSubmit tPubSubmit = new PubSubmit();
					MMap map = new MMap();
					String str1 = "update LCPol set ApproveCode='AUTO',ApproveFlag='9',ApproveDate='"
							+ "?ApproveDate?"
							+ "',ModifyDate='"
							+ "?ModifyDate?"
							+ "',ModifyTime='"
							+ "?ModifyTime?"
							+ "' where PolNo='"
							+ "?PolNo?" + "'";
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql(str1);
					sqlbv4.put("ApproveDate", PubFun.getCurrentDate());
					sqlbv4.put("ModifyDate", PubFun.getCurrentDate());
					sqlbv4.put("ModifyTime", PubFun.getCurrentTime());
					sqlbv4.put("PolNo", tReturnLCPolSchema.getPolNo());
					map.put(sqlbv4, "UPDATE");
					tResult.add(map);
					if (!tPubSubmit.submitData(tResult, "")) {
						CError.buildErr(this, "保单复核状态修改失败，原因是: "
								+ tPubSubmit.mErrors.getError(0).errorMessage);
						return false;
					}
				
			   }
				
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		} finally {
			logger.debug("****准备装入已经导入险种的保单号码（附加豁免主险保单）*****");
			logger.debug("****MainLCPolSchema.getPolNo(): "
					+ tMainLCPolSchema.getPolNo());
			logger.debug("****已导入险种数（附加豁免）: " + tSuccPolSet.size());
			if (tMainLCPolSchema.getPolNo() != null
					&& !"".equals(tMainLCPolSchema.getPolNo())
					&& tSuccPolSet.size() > 0) {
				mSuccPolNoData.setNameAndValue(tMainLCPolSchema.getPrtNo(),
						tSuccPolSet);
			}
		}
		return true;
	}
	
	/**
	 * 处理加费信息，方法：拆分成
	 * 
	 * @param tVData
	 *            VData 从XML中解析出的数据集合
	 * @return boolean 如果处理发生异常，返回false；否则，返回true
	 */
	public boolean DealAddFee(VData tAddFeeData) {
		try {
			logger.debug("........DealAddFee  Start .......");
			// 准备数据
			LCPolSchema tLCPolSchema = new LCPolSchema();
			RiskBasicInfo tRiskBasicInfo = new RiskBasicInfo();
			GlobalInput tGI = new GlobalInput();
			tLCPolSchema = (LCPolSchema)tAddFeeData.getObjectByObjectName("LCPolSchema", 0);
			tRiskBasicInfo = (RiskBasicInfo)tAddFeeData.getObjectByObjectName("RiskBasicInfo", 0);
			tGI = (GlobalInput)tAddFeeData.getObjectByObjectName("GlobalInput", 0);
			double tAddFee = tRiskBasicInfo.getAddPrem();// 加费金额
			//如果没有加费信息，返回
			if(tAddFee <= 0)
			{
				logger.debug("........+++++++++++没有加费信息，不做加费处理！！！++++++++++++ .......");
				return true;
			}
				
			logger.debug("........+++++++++++开始处理加费++++++++++++ .......");
			// 查询加费起期，加费止期，责任编码
			String tSQL = "select b.riskcode,a.dutycode,firstpaydate,payenddate "
	                   + " from lcduty a,lmriskduty b " 
	                   + " where a.dutycode=b.dutycode and polno ='"+"?polno?"+"'"; 
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL);
			sqlbv5.put("polno", tLCPolSchema.getPolNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv5);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				CError.buildErr(this, "保单"+tLCPolSchema.getPolNo()+"查询失败！");
				return false;
			}			
			// 校验加费金额是否正确--不用
			String tRiskCode = tSSRS.GetText(1, 1);			  
			String tAddFeeType = "02";// 加费类别
			String tAddFeeMethod = "03";// 加费方式
			String tDutyCode = tSSRS.GetText(1, 2);// 责任编码
			String tStartDate = tSSRS.GetText(1, 3);// 加费起期
			String tEndDate = tSSRS.GetText(1, 4);// 加费止期

			// 保存加费信息
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCPremSchema.setDutyCode( tDutyCode);
			tLCPremSchema.setPayStartDate( tStartDate);
			tLCPremSchema.setPayPlanType( tAddFeeType);
			tLCPremSchema.setPayEndDate( tEndDate);
			tLCPremSchema.setAddFeeDirect(tAddFeeMethod);// 加费方式
			tLCPremSchema.setPrem(tAddFee);		  	    
		  	logger.debug("责任编码: "+tDutyCode);
			
			// 查询合同单信息
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tLCPolSchema.getContNo());
			LCContSet tLCContSet = tLCContDB.query();
			if (tLCContSet == null || tLCContSet.size() <= 0) {
				CError.buildErr(this,"合同" + tLCPolSchema.getContNo() + "信息查询失败!");
				return false;
			}
			LCContSchema tLCContSchema = tLCContSet.get(1);
			
			
				//********************************************* 生成加费信息		
					
					// 计算除去本次加费项目,承保时的基本保费项,前几次不通批单下的加费后，该保单的加费项目数。以便计算本次加费的编码起始编码值.
					String tsql = "select count(*) from LCPrem where  polno = '"
							+ "?polno?"
							+ "'  and state in ('1','3')";
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(tSQL);
					sqlbv6.put("polno", tLCPolSchema.getPolNo().trim());
					String tReSult = new String();
					tExeSQL = new ExeSQL();
					tReSult = tExeSQL.getOneValue(sqlbv6);
					if (tExeSQL.mErrors.needDealError()) {
						// @@错误处理
						CError.buildErr(this,"执行SQL语句：" + tsql + "失败!");
						return false;
					}
					if (tReSult == null || tReSult.equals("")) {
						return false;
					}

					int tCount = 0;
					tCount = Integer.parseInt(tReSult);// 已包括了本次节点及相关同步节点

					// 更新责任项
					// 取责任信息
					LCDutyDB tLCDutyDB = new LCDutyDB();
					tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());	
					tLCDutyDB.setDutyCode(tDutyCode);
					LCDutySet tempLCDutySet = new LCDutySet();
					tempLCDutySet = tLCDutyDB.query();
							
							LCDutySchema tLCDutySchema = new LCDutySchema();
							tLCDutySchema = tempLCDutySet.get(1);
/**
							// 减去该责任的原本次加费金额
							String sql = "select * from LCPrem where payplancode  like '000000%' and polno = '"
									+ tLCPolSchema.getPolNo().trim()
									+ "' and dutycode = '"
									+ tLCDutySchema.getDutyCode().trim()
									+ "'and state = '1'";
							LCPremDB tLCPremDB = new LCPremDB();
							LCPremSet tLCPremSet = new LCPremSet();
							tLCPremSet = tLCPremDB.executeQuery(sql);

							if (tLCPremSet.size() > 0) {
								for (int j = 1; j <= tLCPremSet.size(); j++) {
									LCPremSchema tempLCPremSchema;// = new
																// LCPremSchema();
									tempLCPremSchema = tLCPremSet.get(j);

									tLCDutySchema.setPrem(tLCDutySchema.getPrem()
											- tempLCPremSchema.getPrem());
									tLCPolSchema.setPrem(tLCPolSchema.getPrem()
											- tempLCPremSchema.getPrem());
									tLCContSchema.setPrem(tLCContSchema.getPrem()
											- tempLCPremSchema.getPrem());
								}
							}
**/
							// 为投保单表和责任表加上本次的加费.同时形成加费信息
									// 形成加费编码
									String PayPlanCode = "";
									PayPlanCode = String.valueOf(1 + tCount);
									for (int j = PayPlanCode.length(); j < 8; j++) {
										PayPlanCode = "0" + PayPlanCode;
									}										

									tLCPremSchema.setGrpContNo(tLCContSchema.getGrpContNo());
									tLCPremSchema.setContNo(tLCContSchema.getContNo());
									tLCPremSchema.setPayPlanCode(PayPlanCode);
									tLCPremSchema.setPayIntv(
											tLCDutySchema.getPayIntv());
									tLCPremSchema.setStandPrem(
											tLCPremSchema.getPrem());
									tLCPremSchema.setSumPrem("0");
									tLCPremSchema.setPaytoDate(
											tLCPremSchema.getPayStartDate());
									tLCPremSchema.setState("1");// 0:承保时的保费项。1:承保时的加费项；2：本次项目加费项
									// 3：前几次不通批单下的加费：
									tLCPremSchema.setUrgePayFlag("Y");// 加费相一定要催交，而不是去取该险种所描述的催交标志。
									tLCPremSchema.setManageCom(
											tLCPolSchema.getManageCom());
									tLCPremSchema.setAppntNo(
											tLCPolSchema.getAppntNo());
									tLCPremSchema.setAppntType("1");// 个人投保
									tLCPremSchema.setOperator(tGI.Operator);
									tLCPremSchema.setMakeDate(
											PubFun.getCurrentDate());
									tLCPremSchema.setMakeTime(
											PubFun.getCurrentTime());
									tLCPremSchema.setModifyDate(
											PubFun.getCurrentDate());
									tLCPremSchema.setModifyTime(
											PubFun.getCurrentTime());

									// 更新保险责任
									tLCDutySchema.setPrem(tLCDutySchema.getPrem()
											+ tLCPremSchema.getPrem());
									// 更新保单数据
									tLCPolSchema.setPrem(tLCPolSchema.getPrem()
											+ tLCPremSchema.getPrem());// 保单总保费	
									tLCPolSchema.setSumPrem(tLCPolSchema.getSumPrem()
											+ tLCPremSchema.getPrem());// 保单总保费
									// 更新合同单数据
									tLCContSchema.setPrem(tLCContSchema.getPrem()
											+ tLCPremSchema.getPrem());
									tLCContSchema.setSumPrem(tLCContSchema.getSumPrem()
											+ tLCPremSchema.getPrem());
/**				
				// 准备删除上一次该项目的加费的数据
				String tSQL = "select * from lcprem where polno = '" + tLCPolSchema.getPolNo() + "'"
						+ " and substr(payplancode,1,6) = '000000'"
						+ " and state = '1'";// 0:承保时的保费项。1:承保时的加费项；2：本次项目加费项
				// 3：前几次不通批单下的加费：
				LCPremDB tLCPremDB = new LCPremDB();
				LCPremSet oldPremSet = new LCPremSet();
				oldPremSet = tLCPremDB.executeQuery(tSQL);
				if (oldPremSet == null) {
					// @@错误处理
					CError.buildErr(this,"查询加费信息失败");
					return false;
				}
				this.mOldLCPremSet.add(oldPremSet);
	**/		
			logger.debug("........save   Start .......");	
			VData tResult = new VData();
			MMap map = new MMap(); 
			map.put(tLCPremSchema, "INSERT");
			map.put(tLCDutySchema, "UPDATE");
			map.put(tLCPolSchema, "UPDATE");
			map.put(tLCContSchema, "UPDATE");
			tResult.add(map);
			PubSubmit tPubSubmit = new PubSubmit();
 			if (!tPubSubmit.submitData(tResult, "")) {
				CError.buildErr(this, "保单加费数据提交失败，原因是: "
						+ tPubSubmit.mErrors.getError(0).errorMessage);
				return false;
			}
			logger.debug("........save   End .......");

			logger.debug("........DealAddFee   End .......");
			return true;

		}// try
		catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * 当TransferData中的内容可以转化为String类型时，将原tOriginTransferData中的内容复制到tTargetTransferData中
	 * 
	 * @param tOriginTransferData
	 * @param tTargetTransferData
	 * @return
	 */
	private boolean copyTransferData(TransferData tOriginTransferData,
			TransferData tTargetTransferData) {
		try {
			Vector nameOriginVData = new Vector(); // 存放变量名
			nameOriginVData = tOriginTransferData.getValueNames();
			for (int i = 0; i < nameOriginVData.size(); i++) {
				String name = (String) nameOriginVData.get(i);
				String value = (String) tOriginTransferData
						.getValueByName(name);
				tTargetTransferData.setNameAndValue(name, value);
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 修改任务表的状态：当异常件、抽检件界面处理成功后，修改BPOMissionDetailState的状态为已处理状态
	 * 
	 * @param tBussNo
	 *            业务号码（印刷号）
	 * @param tDealType
	 *            处理类型
	 *            01－抽检件，02－外包方返回可处理异常件，03－外包方无法处理的异常件（如整个扫描件无法识别），04－清洁件导入系统出错所致的异常件
	 * @param tState
	 *            1-成功导入系统，2－删除
	 * @return 如果状态更新发生异常，返回false,否则，返回true
	 */
	public boolean updateBPOState(String tBussNo, String tDealType,
			String tState, GlobalInput tGI) {
		try {
			BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
			BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = new BPOMissionDetailStateSchema();
			BPOMissionStateDB tBPOMissionStateDB = new BPOMissionStateDB();
			tBPOMissionStateDB.setBussNo(tBussNo);
			tBPOMissionStateDB.setBussNoType("TB");
			if (tDealType == null || "".equals(tDealType)) {
				CError.buildErr(this, "任务处理状态表修改失败，原因是DealType传入为空！");
				return false;
			}
			tBPOMissionStateDB.setDealType(tDealType);
			if (!tBPOMissionStateDB.getInfo()) {
				CError.buildErr(this, "任务处理状态主表查询失败" + tBussNo);
				return false;
			}
			tBPOMissionStateSchema = tBPOMissionStateDB.getSchema();
			tBPOMissionStateSchema.setState(tState); // 修改处理状态
			tBPOMissionStateSchema.setModifyDate(PubFun.getCurrentDate());
			tBPOMissionStateSchema.setModifyTime(PubFun.getCurrentTime());

			Reflections tReflections = new Reflections();

			// 把对象tBPOMissionStateSchema的值赋给对象tBPOMissionDetailStateSchema
			tReflections.transFields(tBPOMissionDetailStateSchema,
					tBPOMissionStateSchema);
			ExeSQL tExeSQL = new ExeSQL();
			String tSQL = "select (case when max(DealCount) is null then 0 else max(DealCount) end) from BPOMissionDetailState where BussNo = '"
					+ "?BussNo?"
					+ "' and BussNoType = 'TB'";
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tSQL);
			sqlbv7.put("BussNo", tBPOMissionStateSchema.getBussNo());
			int tDealCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv7));
			logger.debug("tDealCount: " + tDealCount);
			tBPOMissionDetailStateSchema.setDealCount(tDealCount + 1);

			VData tUpdateVData = new VData();
			logger.debug("tBPOMissionStateSchema.encode(): "
					+ tBPOMissionStateSchema.encode());
			logger.debug("tBPOMissionDetailStateSchema.encode(): "
					+ tBPOMissionDetailStateSchema.encode());
			tUpdateVData.add(tBPOMissionStateSchema);
			tUpdateVData.add(tBPOMissionDetailStateSchema);
			tUpdateVData.add(tGI);
			BPODealInputDataBLS tBPODealInputDataBLS = new BPODealInputDataBLS(); // 数据提交类
			if (!tBPODealInputDataBLS.submitData(tUpdateVData, "UPDATE")) {
				CError.buildErr(this, tBPOMissionStateSchema.getBussNo()
						+ "已导入的保单数据保存失败："
						+ tBPODealInputDataBLS.mErrors.getFirstError());
				return false;
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 修改lccont:lccont导入时存的被保人编码是最近处理的被保人编码，并不是第一被保人编码，因此在最后进行修改
	 * @return 如果状态更新发生异常，返回false,否则，返回true
	 */
	public boolean updateLCCont(String tPrtNo) {
		try {
			logger.debug("........+++++++++++修改合同表 start++++++++++++ .......");
			//查询第一被保人信息
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			String tSql = "select * from lcinsured where prtno='"+"?tPrtNo?"+"' and sequenceno in ('1','-1')";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tPrtNo", tPrtNo);
			LCInsuredSet tLCInsuredSet = tLCInsuredDB.executeQuery(sqlbv);
			if (tLCInsuredSet.size()!=1) {
				CError.buildErr(this, "lcinsured表查询失败！");
				return false;
			}
			//得到第一被保人信息		
			LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(1);
			/**
			//查询第一被保人第一主险信息
			LCPolDB tLCPolDB = new LCPolDB();
			tSql = "select * from lcpol where prtno='"+tPrtNo+"' and insuredno="
	           +" (select insuredno from lcinsured where prtno='"+tPrtNo+"' and sequenceno in('1','-1')"
	           +" and polno=mainpolno and (RiskSequence is null or RiskSequence in ('1','-1'))";
			LCPolSet tLCPolSet = tLCPolDB.executeQuery(tSql);
			if (tLCPolSet.size()!=1) {
				CError.buildErr(this, "lcpol表查询失败！");
				return false;
			}
			//得到第一被保人第一主险信息		
			LCPolSchema tLCPolSchema = tLCPolSet.get(1);
			**/
			LCContSchema tLCContSchema = new LCContSchema();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tPrtNo);
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "合同表（"+tPrtNo+"）查询失败！");
				return false;
			}
			tLCContSchema = tLCContDB.getSchema();
			tLCContSchema.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			tLCContSchema.setInsuredName(tLCInsuredSchema.getName());
			tLCContSchema.setInsuredSex(tLCInsuredSchema.getSex());
			tLCContSchema.setInsuredBirthday(tLCInsuredSchema.getBirthday());
			tLCContSchema.setInsuredIDType(tLCInsuredSchema.getIDType());
			tLCContSchema.setInsuredIDNo(tLCInsuredSchema.getIDNo());
			tLCContSchema.setModifyDate(PubFun.getCurrentDate());
			tLCContSchema.setModifyTime(PubFun.getCurrentTime());
			
			//生成保单原始数据
			logger.debug("........+++++++++++生成保单原始数据 start++++++++++++ .......");
			
			TransferData tTransferData = new TransferData();			
			VData tVData = new VData();
			MMap tmap = new MMap();
			VData ttResult = new VData();
			SaveOriginalAfterEndService tProposalOriginalPrepa = new SaveOriginalAfterEndService();
			tTransferData.setNameAndValue("ContNo", tPrtNo);
			tVData.add(tTransferData);
			if(!tProposalOriginalPrepa.submitData(tVData, ""))
			{
				CError.buildErr(this, "保单原始数据准备失败，原因是: "
						+ tProposalOriginalPrepa.mErrors.getError(0).errorMessage);
				return false;
			}
			ttResult = tProposalOriginalPrepa.getResult();
			tmap = (MMap)ttResult.getObjectByObjectName("MMap", 0);
			if(tmap == null)
			{
				CError.buildErr(this, "保单原始数据准备失败！");
				return false;
			}
			
			VData tResult = new VData();
			PubSubmit tPubSubmit = new PubSubmit();
			tmap.put(tLCContSchema, "UPDATE");
			tResult.add(tmap);
			if (!tPubSubmit.submitData(tResult, "")) {
				CError.buildErr(this, "数据提交失败，原因是: "
						+ tPubSubmit.mErrors.getError(0).errorMessage);
				return false;
			}
			
			
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 校验主附险包含关系
	 * 
	 * @param tMainPolNo
	 * @param cRiskCode
	 * @return 如果主险能带附加险，返回true,如果不能，则返回false
	 */
	private boolean checkRiskRelation(String tMainPolNo, String cRiskCode) {
		ExeSQL tExeSQL = new ExeSQL();
		String strSql = "select RiskCode from LCPol where PolNo = '"
				+ "?tPrtNo?"
				+ "' and exists (select 1 from LDCode1 where CodeType='checkappendrisk' and Code = '"
				+ "?Code?" + "' and Code1=LCPol.RiskCode)";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("PolNo", tMainPolNo);
		sqlbv.put("Code", cRiskCode);
		String tResult = tExeSQL.getOneValue(sqlbv);
		if (tResult == null || "".equals(tResult)) {
			return false;
		}
		return true;
	}

	/**
	 * 校验客户身份证号码、性别、出生日期是否一致
	 * 
	 * @param tIDNo
	 * @param tIDType
	 * @param tBirthday
	 * @param tSex
	 * @return 如果正确返回true,否则，返回false
	 */
	private boolean checkIDNoWithBirthday(String tIDNo, String tIDType,
			String tBirthday, String tSex) {
		try {
			int tIDLength = 0; // 身份证长度
			int tLastNum = 0; // 身份证最后一位
			String[] tBirthArray = new String[3];// 生日拆分成数组
			String tBirthdayNew = ""; // 生日转换后，符合 YYYY-MM-DD
			// ,输入时可能是2006-1-1，转化后为2006-01-01
			// 只对身份证做校验
			if (tIDType == null || !"0".equals(tIDType))
				return true;
			if (tIDNo == null || "".equals(tIDNo)) {
				CError.buildErr(this, "身份证号码为空！");
				return false;
			}

			if (tBirthday == null || "".equals(tBirthday)) {
				CError.buildErr(this, "生日为空！");
				return false;
			}

			if (tSex == null || "".equals(tSex)) {
				CError.buildErr(this, "性别为空！");
				return false;
			}

			tIDLength = tIDNo.length();
			if (tIDLength != 15 && tIDLength != 18) {
				CError.buildErr(this, "身份证号码位数不正确，必须是15位或者18位！");
				return false;
			}
			tBirthArray = isDate(tBirthday);
			if (tBirthArray == null) {
				CError.buildErr(this, "生日格式有误！");
				return false;
			}
			if (tBirthArray[1].length() == 1)
				tBirthArray[1] = "0" + tBirthArray[1];
			if (tBirthArray[2].length() == 1)
				tBirthArray[2] = "0" + tBirthArray[2];
			tBirthdayNew = tBirthArray[0] + "-" + tBirthArray[1] + "-"
					+ tBirthArray[2];
			String tempBirthady = "";
			// 处理15位身份证号码
			if (tIDLength == 15) {
				tLastNum = Integer.parseInt(tIDNo.substring(14));
				tempBirthady = "19" + tIDNo.substring(6, 12);
			} else // 18位的身份证倒数第二位区分男女
			{
				tLastNum = Integer.parseInt(tIDNo.substring(16, 17));
				tempBirthady = tIDNo.substring(6, 14);
			}

			tempBirthady = tempBirthady.substring(0, 4) + "-"
					+ tempBirthady.substring(4, 6) + "-"
					+ tempBirthady.substring(6);
			if (tempBirthady.equals(tBirthdayNew)) {
				// 男性为奇数，女性为偶数
				if ((tSex.equals("0") && tLastNum % 2 == 0)
						|| (tSex.equals("1") && tLastNum % 2 == 1)) {
					CError.buildErr(this, "身份证与性别不匹配！");
					return false;
				}
			} else {
				CError.buildErr(this, "身份证与生日不匹配！");
				return false;
			}

		} catch (Exception ex) {
			CError.buildErr(this, "校验身份证时发生异常！");
			return false;
		}
		return true;
	}

	/**
	 * 校验输入的日期格式是否正确
	 */
	private String[] isDate(String Date) {
		String[] dateArray = new String[3];
		try {
			dateArray = Date.split("-");
			if (dateArray.length != 3)
				return null;
			int Year = Integer.parseInt(dateArray[0]);
			int Month = Integer.parseInt(dateArray[1]);
			int Day = Integer.parseInt(dateArray[2]);
			if (Year < 0 || Year > 9999 || Month < 0 || Month > 12 || Day < 0
					|| Day > 31)
				return null;
		} catch (Exception ex) {
			return null;
		}
		return dateArray;
	}
	

	/**
	 * 校验销售渠道与险种的关系
	 * 
	 * @param tLCPolSchema
	 * @param tLAAgentSchema
	 * @return
	 */
	private boolean checkSaleChnl(LCPolSchema tLCPolSchema) {
		try {	
			if (tLCPolSchema.getRiskCode() == null
					|| "".equals(tLCPolSchema.getRiskCode())) {
				CError.buildErr(this, "外包方传入的险种编码数据为空！");
				return false;
			}
			
			String tSQL;
			// 校验销售渠道与险种的校验关系
			tSQL = "select Code from ldcode where CodeType='salechnlriskrela' and Code = '"
					+ "?Code?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("Code", tLCPolSchema.getSaleChnl());
			ExeSQL tExeSQL = new ExeSQL();
			String tRestlt = tExeSQL.getOneValue(sqlbv);
			if (tRestlt != null && !"".equals(tRestlt)) {
				String tSubSQL = "select CodeType from ldcode1 where CodeType='salechnlriskrela' and Code = '"
						+ "?Code?"
						+ "' and Code1='"
						+ "?Code1?" + "'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSubSQL);
				sqlbv1.put("Code", tLCPolSchema.getSaleChnl());
				sqlbv1.put("Code1", tLCPolSchema.getRiskCode());
				String tSubResult = tExeSQL.getOneValue(sqlbv1);
				if (tSubResult == null || "".equals(tSubResult)) {
					CError.buildErr(this, "销售渠道：" + tLCPolSchema.getSaleChnl()
							+ " 不允许销售" + tLCPolSchema.getRiskCode() + "险种");
					return false;
				}
			}			
			
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 校验合同信息
	 * 
	 * @param tLCPolSchema
	 * @param tLAAgentSchema,tTransferData
	 * @return
	 */
	private boolean checkContData(LCPolSchema tLCPolSchema,
			LAAgentSchema tLAAgentSchema, TransferData tTransferData) {
		try {
			// 校验保单的管理机构与扫描件的管理机构
			String tSQL = "";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSQL = "SELECT managecom FROM es_doc_main where doccode='"
						+ "?doccode?" + "' and rownum=1";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSQL = "SELECT managecom FROM es_doc_main where doccode='"
						+ "?doccode?" + "' limit 1";
			}
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("doccode", tLCPolSchema.getPrtNo());
			ExeSQL tExeSQL = new ExeSQL();
			String tManageCom = tExeSQL.getOneValue(sqlbv);
			if (tManageCom != null && !"".equals(tManageCom)) {				
				if(tManageCom.length()>=6 && tLCPolSchema.getManageCom().length()>=6 
						&& tManageCom.substring(0, 6).equals(tLCPolSchema.getManageCom().substring(0, 6))) 
				{}
				else
				{
					// 销售渠道为05的投保单代理机构不为空branchtype为6不进行校验
					boolean checkFlag = true;
					if (tLCPolSchema.getSaleChnl() != null
							&& ("05".equals(tLCPolSchema.getSaleChnl())) 
									&& tLCPolSchema.getAgentCom() != null 
									&& !"".equals(tLCPolSchema.getAgentCom())) {
								LAComDB tLAComDB = new LAComDB();
								tLAComDB.setAgentCom(tLCPolSchema.getAgentCom());
								if (!tLAComDB.getInfo()) {
									CError.buildErr(this, "代理机构编码错误！");
									return false;
								}
								if(tLAComDB.getBranchType()!=null && "6".equals(tLAComDB.getBranchType()))
							    {
									checkFlag = false;
							    }							
						}
					if(checkFlag == true)
					{
						CError.buildErr(this, "保单的管理机构与扫描件的管理机构不一致！");
						return false;
					}					
				}
			}

			if (tLCPolSchema.getSaleChnl() == null
					|| "".equals(tLCPolSchema.getSaleChnl())) {
				CError.buildErr(this, "外包方传入的销售渠道数据为空！");
				return false;
			}

			// 校验销售渠道的正确性
			tSQL = "select 1 from ldcode where codetype = 'salechnl' and code='"
					+ "?code?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("code", tLCPolSchema.getSaleChnl());
			tExeSQL = new ExeSQL();
			String tRestlt = tExeSQL.getOneValue(sqlbv1);
			if (tRestlt == null || "".equals(tRestlt)) {
				CError.buildErr(this, "销售渠道数据错误！");
				return false;
			}			

			// 销售渠道为银代时必须输入代理机构
			if ("03".equals(tLCPolSchema.getSaleChnl())) {
				if (tLCPolSchema.getAgentCom() == null
						|| "".equals(tLCPolSchema.getAgentCom())) {
					CError.buildErr(this, "销售渠道为银代时必须输入代理机构！");
					return false;
				}

				LAComDB tLAComDB = new LAComDB();
				tLAComDB.setAgentCom(tLCPolSchema.getAgentCom());
				if (!tLAComDB.getInfo()) {
					CError.buildErr(this, "代理机构编码错误！");
					return false;
				}
			}

			// 中介投保单必须输入代理机构
			if (tLCPolSchema.getPrtNo() != null
					&& "21".equals(tLCPolSchema.getPrtNo().substring(2, 4))) {
				if ((tLCPolSchema.getAgentCom() == null || ""
						.equals(tLCPolSchema.getAgentCom()))) {
					CError.buildErr(this, "中介投保单必须输入代理机构！");
					return false;
				}

				LAComDB tLAComDB = new LAComDB();
				tLAComDB.setAgentCom(tLCPolSchema.getAgentCom());
				if (!tLAComDB.getInfo()) {
					CError.buildErr(this, "代理机构编码错误！");
					return false;
				}
				
				if (!"05".equals(tLCPolSchema.getSaleChnl())
				           && !"06".equals(tLCPolSchema.getSaleChnl())
				           && !"08".equals(tLCPolSchema.getSaleChnl())
				           && !"09".equals(tLCPolSchema.getSaleChnl())
				           && !"11".equals(tLCPolSchema.getSaleChnl())
				           )
				       {
				         CError.buildErr(this, "中介投保单销售渠道错误!") ;
				         return false;
				       }

				       if("7".equals(tLAComDB.getBranchType()))
				       {
				         if(!tLAComDB.getManageCom().equals(tLCPolSchema.getManageCom()))
				         {
				           CError.buildErr(this, "代理机构对应的管理机构和录入管理机构不一致!") ;
				           return false;
				         }
					     tExeSQL = new ExeSQL();
					     tSQL = "select 1 from LAComToAgentNew where agentcom='"+"?agentcom?"+"' and RelaType='2' and agentcode='"+"?agentcode?"+"' ";
					     SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
							sqlbv2.sql(tSQL);
							sqlbv2.put("agentcom", tLCPolSchema.getAgentCom());
							sqlbv2.put("agentcode", tLCPolSchema.getAgentCode());
					     if("".equals(StrTool.cTrim(tExeSQL.getOneValue(sqlbv2))))
					     {
					         CError.buildErr(this, "中介代理机构和该客户经理不对应!") ;
					         return false;
					     }
				       }
				       else if(!"6".equals(tLAComDB.getBranchType()))
				       {
				    	     CError.buildErr(this, "代理机构渠道不是中介渠道或者联办渠道!") ;
					         return false;
				       }
				
			}

			if (tLCPolSchema.getPrtNo() != null
					&& ("15".equals(tLCPolSchema.getPrtNo().substring(2, 4))
							||"35".equals(tLCPolSchema.getPrtNo().substring(2, 4)))) {
				if ((tLCPolSchema.getAgentCom() == null || ""
						.equals(tLCPolSchema.getAgentCom()))) {
					CError.buildErr(this, "银代投保单必须输入银行代理机构！");
					return false;
				}

				LAComDB tLAComDB = new LAComDB();
				tLAComDB.setAgentCom(tLCPolSchema.getAgentCom());
				if (!tLAComDB.getInfo()) {
					CError.buildErr(this, "代理机构编码错误！");
					return false;
				} else {
					if (!"3".equals(tLAComDB.getBranchType())) {
						CError.buildErr(this, "代理机构不是银行代理网点！");
						return false;
					}
				}
			}

			// 销售渠道为个人时不能输入代理机构
			if ("02".equals(tLCPolSchema.getSaleChnl())
					&& tLCPolSchema.getAgentCom() != null
					&& !"".equals(tLCPolSchema.getAgentCom())) {
				CError.buildErr(this, "销售渠道为个人时不能输入代理机构！");
				return false;
			}

			tExeSQL = new ExeSQL();
			tSQL = "SELECT BranchType FROM laagent where agentcode='"
					+ "?agentcode?" + "'";
			SQLwithBindVariables sqlbv66 = new SQLwithBindVariables();
			sqlbv66.sql(tSQL);
			sqlbv66.put("agentcode", tLAAgentSchema.getAgentCode());
			String tBranchType = tExeSQL.getOneValue(sqlbv66);
			if (tBranchType == null || "".equals(tBranchType)) {
				CError.buildErr(this, "代理人的展业类型未知！");
				return false;
			}

			tSQL = "select 1 from LDCode1 where codetype='salechnlagentctrl' and code='"
					+ "?code?"
					+ "' and ( code1='"
					+ "?code1?" + "' or othersign='1')"; // othersign='1'
			 SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSQL);
				sqlbv2.put("code", tLCPolSchema.getSaleChnl());
				sqlbv2.put("code1", tBranchType);
			// 表示销售渠道和代理人无校验
			 String tResult = tExeSQL.getOneValue(sqlbv2);
			 if(tResult == null || "".equals(tResult))
			 {
				 CError.buildErr(this, "保单的销售渠道与代理人不匹配！");			
			     return false;
			 }
			 
			//校验代理人姓名和编码是否一致
				String tAgentName = (String) tTransferData
						.getValueByName("AgentName");
				if (tAgentName == null || "".equals(tAgentName)) {
					CError.buildErr(this, "外包方传入的代理人姓名为空！");
					return false;
				}

				if (!tAgentName.equals(tLAAgentSchema.getName())) {
					CError.buildErr(this,"外包方传入的代理人姓名校验与编码不一致！");
					return false;
				}			

		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 校验投保人信息
	 * 
	 * @param tLCPolSchema
	 * @param tLAAgentSchema,tTransferData
	 * @return
	 */
	private boolean checkAppntData(LCPolSchema tLCPolSchema,
			LCAppntIndSchema tLCAppntIndSchema, TransferData tTransferData) 
	{
		try {
			logger.debug("Start checkIDNoWithBirthday...");
			// 校验投保单
			if (!checkIDNoWithBirthday(tLCAppntIndSchema.getIDNo(),
					tLCAppntIndSchema.getIDType(), tLCAppntIndSchema
							.getBirthday(), tLCAppntIndSchema.getSex())) {
				mErrors.removeLastError();
				CError
						.buildErr(this, "投保人身份证校验失败，原因："
								+ mErrors.getLastError());
				return false;
			}

			// 校验投保人姓名中是否有乱码
			if (tLCAppntIndSchema.getName()!=null
					&& "".equals(StrTool.cTrim(tLCAppntIndSchema.getName()))) {
				CError.buildErr(this, "投保人姓名不能为空！");
				return false;
			}

			if (StrTool.cTrim(tLCAppntIndSchema.getName()).indexOf("?") != -1
					|| StrTool.cTrim(tLCAppntIndSchema.getName()).indexOf(
							"？") != -1
					|| StrTool.cTrim(tLCAppntIndSchema.getName()).indexOf(
							"M-}") != -1) {
				CError.buildErr(this, "投保人姓名中可能包含系统无法转换的生僻字！");
				return false;
			}
			
			// 校验投保人通信地址中是否有乱码
			if (StrTool.cTrim(tLCAppntIndSchema.getPostalAddress()).indexOf(
					"?") != -1
					|| StrTool.cTrim(tLCAppntIndSchema.getPostalAddress())
							.indexOf("？") != -1
					|| StrTool.cTrim(tLCAppntIndSchema.getPostalAddress())
							.indexOf("M-}") != -1) {
				CError.buildErr(this, "投保人通信地址中可能包含系统无法转换的生僻字！");
				return false;
			}
			
			/*去掉证件有效期校验，华道已经校验
			//2009-9-22 ln add --增加投保人证件有效期校验
//			String tidEndDate = StrTool.cTrim((String) tTransferData
//					.getValueByName("AppntIDEndDate"));
			LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			tLCAppntSchema.setIDExpDate(StrTool.cTrim((String) tTransferData
					.getValueByName("AppntIDEndDate")));
			String tidEndDate = StrTool.cTrim(tLCAppntSchema.getIDExpDate());
			String tBirthDay = StrTool.cTrim(tLCAppntIndSchema.getBirthday());
			String tIDType = StrTool.cTrim(tLCAppntIndSchema.getIDType());
			String tPolApplyDate = StrTool.cTrim(tLCPolSchema.getPolApplyDate());
			if(!tIDType.equals("")&&(tIDType.equals("0")||tIDType.equals("1")
					||tIDType.equals("2")||tIDType.equals("D"))&&tidEndDate.equals(""))
			{
				CError.buildErr(this,"投保人证件类型为：身份证或军人证或警官证或护照，但没有填写投保人证件有效期！");
			    return false;
			}
			if(!tidEndDate.equals("")&&!tPolApplyDate.equals(""))
			{				
				if(PubFun.checkDate(tidEndDate , tPolApplyDate)) 
		    	{
		    		CError.buildErr(this,"投保人证件有效期早于投保申请日期！");
				    return false;
		    	}
				if(!tBirthDay.equals(""))
				{
					String tlidEndDate = PubFun.newCalDate(tBirthDay,"Y",100);//往后推100年
					if(!PubFun.checkDate(tidEndDate , tlidEndDate)) 
			    	{
			    		CError.buildErr(this,"投保人证件有效期晚于其出生日期加100年！");
					    return false;
			    	}
				}				
			}*/

		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 校验被保人信息
	 * 
	 * @param tLCPolSchema
	 * @param tLAAgentSchema,tTransferData
	 * @return
	 */
	private boolean checkInsuredData(LCPolSchema tLCPolSchema, LCInsuredSchema tLCInsuredSchema) 
	{
		try {
			// 被保险人身份证校验
			if (!checkIDNoWithBirthday(tLCInsuredSchema.getIDNo(),
					tLCInsuredSchema.getIDType(), tLCInsuredSchema
							.getBirthday(), tLCInsuredSchema.getSex())) {
				CError.buildErr(this, "被保险人身份证校验失败，原因："
						+ mErrors.getLastError());
				return false;
			}

			// 校验被保险人姓名中是否包含系统无法转换的生僻字
			if (StrTool.cTrim(tLCInsuredSchema.getName()).indexOf("?") != -1
					|| StrTool.cTrim(tLCInsuredSchema.getName())
							.indexOf("？") != -1
					|| StrTool.cTrim(tLCInsuredSchema.getName())
							.indexOf("M-}") != -1) {
				CError.buildErr(this, "被保险人姓名中可能包含系统无法转换的生僻字！");
				return false;
			}
			
			/*去掉证件有效期校验，华道已经校验
			//2009-9-22 ln add --增加投保人证件有效期校验
			String tidEndDate = StrTool.cTrim(tLCInsuredSchema.getIDExpDate());
			String tBirthDay = StrTool.cTrim(tLCInsuredSchema.getBirthday());
			String tIDType = StrTool.cTrim(tLCInsuredSchema.getIDType());
			String tPolApplyDate = StrTool.cTrim(tLCPolSchema.getPolApplyDate());
			if(!tIDType.equals("")&&(tIDType.equals("0")||tIDType.equals("1")
					||tIDType.equals("2")||tIDType.equals("D"))&&tidEndDate.equals(""))
			{
				CError.buildErr(this,"被保人（"+tLCInsuredSchema.getName()+"）的证件类型为：身份证或军人证或警官证或护照，但没有填写证件有效期！");
			    return false;
			}
			if(!tidEndDate.equals("")&&!tPolApplyDate.equals(""))
			{				
				if(PubFun.checkDate(tidEndDate , tPolApplyDate)) 
		    	{
		    		CError.buildErr(this,"被保人（"+tLCInsuredSchema.getName()+"）的证件有效期早于投保申请日期！");
				    return false;
		    	}
				if(!tBirthDay.equals(""))
				{
					String tlidEndDate = PubFun.newCalDate(tBirthDay,"Y",100);//往后推100年
					if(!PubFun.checkDate(tidEndDate , tlidEndDate)) 
			    	{
			    		CError.buildErr(this,"被保人（"+tLCInsuredSchema.getName()+"）的证件有效期晚于其出生日期加100年！");
					    return false;
			    	}
				}				
			}*/

		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private String getBOPID(Document doc) {
		String tBOPID = "";
		Element root = doc.getRootElement();
		tBOPID = root.getChild("Company").getTextTrim();
		return tBOPID;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		// mGI.setSchema((GlobalInput)
		// mInputData.getObjectByObjectName("GlobalInput",0));
		// 得到一些常量的配置信息
		if (!getConfigData())
			return false;

		// 得到需要处理数据文件信息
		if (!getNeedDealFiles())
			return false;

		return true;
	}

	/**
	 * 根据系统确定的路径和处理文件最大数取得数据文件名称列表 如果返回文件名称列表时发生异常，返回false,否则，返回true;
	 */
	private boolean getNeedDealFiles() {
		// 取得一定数量的外包方返回的文件
		mNeedDealFiles = TBPubFun.getFilesList(mBPOInputPath, mBPOMaxCount);
		if (mNeedDealFiles == null) {
			CError.buildErr(this, "没有需要处理的数据文件或者系统定义外包方数据返回的路径错误！");
			return false;
		}

		if (mNeedDealFiles.length == 0) {
			CError.buildErr(this, "没有需要处理的数据！");
			return false;
		}

		return true;
	}

	/**
	 * 查询数据库中一些配置信息 输出：如果出现异常，则返回false,否则返回true
	 */
	private boolean getConfigData() {
		// 1。查询系统定义外包方数据返回的路径
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL = "select backdatapath from bposerverinfo where lisoperator ='"
				+ "?lisoperator?" + "' and bussnotype='TB'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("lisoperator", tOperator);
		mBPOInputPath = tExeSQL.getOneValue(sqlbv);
		
//		mBPOInputPath = "D:/inputdata/JM001/";

		logger.debug("****系统定义外包方数据返回的路径：  " + mBPOInputPath);
		if (mBPOInputPath == null || "".equals(mBPOInputPath)) {
			CError.buildErr(this, "系统未定义外包方数据返回的路径！");
			return false;
		}

		// 2。查询系统定义处理外包方数据文件数量限制
		tExeSQL = new ExeSQL();
		tSQL = "select (case when sysvarvalue is null then '0' else sysvarvalue end) from ldsysvar where sysvar ='BPOMaxCount'";
		SQLwithBindVariables sqlbv80 = new SQLwithBindVariables();
		sqlbv80.sql(tSQL);
		String tBPOMaxCount = tExeSQL.getOneValue(sqlbv80);
		logger.debug("****系统定义外包方数据文件限制：  " + tBPOMaxCount);
		if (tBPOMaxCount != null && !"".equals(tBPOMaxCount)
				&& Integer.parseInt(tBPOMaxCount) > 1) {
			mBPOMaxCount = Integer.parseInt(tBPOMaxCount);
		}

		return true;
	}

	/**
	 * getConfigDataN added by ln 2008.5.22 查询数据库中一些配置信息
	 * 输出：如果出现异常，则返回false,否则返回true
	 */
	private boolean getConfigDataN() {
		try {
			// 1 查询系统定义外包方数据返回的路径集合
			ExeSQL tExeSQL = new ExeSQL();
			String tSQL = "select backdatapath from bposerverinfo where bussnotype='TB' order by lisoperator desc";
			// logger.debug("****系统定义外包方数据返回的路径tSQL： "+tSQL);
			SQLwithBindVariables sqlbv81 = new SQLwithBindVariables();
			sqlbv81.sql(tSQL);
			SSRS tSSRS = tExeSQL.execSQL(sqlbv81);
			int max = tSSRS.getMaxRow();

			// 是否有定义的外保方路径标志
			boolean state = false;

			if (tSSRS == null || max == 0) {
				CError.buildErr(this, "系统未定义任何外包方！");
				return false;
			}

			// 取得外包方返回的路径集合
			int i = 0;
			mBPOInputPaths = new String[max];

			while (tSSRS.getMaxRow() > i) {
				if (!(tSSRS.GetText(max, 1) == null || "".equals(tSSRS.GetText(
						max, 1)))) {
					mBPOInputPaths[i] = tSSRS.GetText(max, 1);
					state = true;
				}

				i++;
				max--;
			}

			if (state == false) {
				CError.buildErr(this, "系统未定义外包方数据返回的路径！");
				return false;
			}

			logger.debug("******可处理的外保方数目 : " + mBPOInputPaths.length);

			// 2 查询系统定义处理外包方数据文件数量限制
			tExeSQL = new ExeSQL();
			tSQL = "select (case when sysvarvalue is null then '0' else sysvarvalue end) from ldsysvar where sysvar ='BPOMaxCount'";
			SQLwithBindVariables sqlbv82 = new SQLwithBindVariables();
			sqlbv82.sql(tSQL);
			String tBPOMaxCount = tExeSQL.getOneValue(sqlbv82);
			if (tBPOMaxCount != null && !"".equals(tBPOMaxCount)
					&& Integer.parseInt(tBPOMaxCount) > 1) {
				mBPOMaxCount = Integer.parseInt(tBPOMaxCount);
			}

			return true;

		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}

	}

	public static void main(String[] args) {
		// BPODealInputDataBL BPODealInputDataBL1 = new BPODealInputDataBL();
		// VData tVData = new VData();
		// Document tOneFileData =
		// TBPubFun.produceXmlDoc("e:\\xml\\2007-12-29\\291200712281751194061.xml");
		// if(tOneFileData != null)
		// {
		// BPODealInputDataBL1.DealOneFile(tOneFileData,"外包方承保数据返回格式.xml");
		// }
		// List tElementlist
		// =tOneFileData.getRootElement().getChildren("OnePolData");
		// TBXMLTransfer TBXMLTransfer1 = new TBXMLTransfer();

		// Element tElement = (Element)tElementlist.get(0);
		// tVData = TBXMLTransfer1.getOnePolData("86350100000815");
		// BPODealInputDataBL1.DealOneRightPol(tVData, 0);
		// logger.debug("86350100000815".substring(2, 4));

		// VData t=TBXMLTransfer1.getOnePolData("86350100000777");
		// logger.debug("t.size: "+t.size());
		// GlobalInput tGlobalInput = new GlobalInput();
		// tGlobalInput.ManageCom="86110000";
		// tGlobalInput.Operator="001";
		// t.add(tGlobalInput);
		// BPODealInputDataBL1.DealOneRightPol(t,0);

		/*BPODealInputDataBL BPODealInputDataBL1 = new BPODealInputDataBL();
		VData cInputData = new VData();
		
		// TBXMLTransfer tTBXMLTransfer = new TBXMLTransfer();
		// tVData=
		// tTBXMLTransfer.XMLToVData(tOneFileData.getRootElement().getChild("OnePolData"));
		// GlobalInput tGI = new GlobalInput();
		// tGI.Operator="001";
		// tGI.ManageCom="86" ;
		// tGI.ComCode="86";
		// tVData.add(tGI);
		BPODealInputDataBL1.submitData(cInputData, "");*/
		Random tRandom = new Random();
		for (int i = 0; i < 1000; i++) {
			int j = tRandom.nextInt(1); // 随机产生一个0 到
			// 所有满足抽检条件的保单数量的值，如果该值在 0 与
			// 随即抽检基准 之间，确定为抽检件
			logger.debug("***随机数    RandNo: " + j);			
		}
	}
}
