package com.sinosoft.lis.easyscan;
import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_DOC_RELATIONDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDScanCropDefDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageFactory;
import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageUploader;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.SignatureBL;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.schema.Es_Doc_LogSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_DOC_RELATIONSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: EasyScan上载图象索引处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Liuqiang changed by tuqiang
 * @version 1.0
 */
public class UploadIndexBL {
private static Logger logger = Logger.getLogger(UploadIndexBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 复制传入的参数，在本类中处理* */
	private VData mInputData;
	/** 需要返回的数据* */
	private VData mResult = new VData();

	/** 需要数据库提交的数据* */
	private VData mResult1 = new VData();

	/** 调用服务的类型* */
	private String m_strServiceType = "";
	private String mSubType = "";
	private String mDocCode = "";
	/** 数据提交* */
	MMap map = new MMap();

	/** 扫描日志流水号* */
	private final static String CON_ES_LOG_ID = "ES_LogID";

	private GlobalInput tGI = new GlobalInput();
	String mRtnCode = "0";
	String mRtnDesc = "";

	public UploadIndexBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("UploadIndexBL-----begin");

		boolean breturn = true;
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		mResult = mInputData;

		// 处理原始数据
		dealData(cOperate);

		CallService tCallService = new CallService();

		/** 得到工作流数据* */

		try{
		if (!tCallService.submitData(mInputData, cOperate, m_strServiceType)) {
			mErrors.copyAllErrors(tCallService.mErrors);
			return false;
		}
		}catch(Exception ex){
			CError.buildErr(this, ex.getMessage());
			return false;
		}

		map.add((MMap) tCallService.getResult()
				.getObjectByObjectName("MMap", 0));

		//以下内容没看出有何意义 by zzm
//		ES_DOC_MAINSet tES_DOC_MAINSET = (ES_DOC_MAINSet) mInputData.get(0);
//		ES_DOC_MAINSchema tES_DOC_MAINSchema = tES_DOC_MAINSET.get(1);
//		Calculator tCaculator = new Calculator();
//		tCaculator.setCalCode("STYPE");
//		tCaculator.addBasicFactor("subtype", tES_DOC_MAINSchema.getSubType());
//		String needflag = tCaculator.calculate();
//		if (needflag == null || needflag.trim().equals("")
//				|| needflag.trim().equals("0")) {
//			// 不属于多次上载的单证
//			// 防止出现同时对一个单证上载的情况产生----add by haopan
//			String chkSQl = " select 1  from dual "
//					+ " where(select count( * )   from es_doc_main "
//					+ "  where doccode = '" + tES_DOC_MAINSchema.getDocCode()
//					+ "' and busstype = '" + tES_DOC_MAINSchema.getBussType()
//					+ "' and subtype = '" + tES_DOC_MAINSchema.getSubType()
//					+ "') >= 2 ";
//			logger.debug("并发操作控制:" + chkSQl);
//			map.put(chkSQl, "SELECT");
//
//		}

		mResult1.add(map);
		// 如果前面都执行成功后进行数据的提交
		PubSubmit pubsubmit = new PubSubmit();
		if (!pubsubmit.submitData(mResult1, cOperate)) {
			mErrors.copyAllErrors(pubsubmit.mErrors);
			return false;
		}
		
		try{dealSignture();}catch(Exception ex){};
		try{uploadDataToCloud();}catch(Exception ex){};
		return true;
	}

	/**
	 * <p>检测可用云端并上传文件。若文件上传成功，则删除本地文件</p>
	 * @author <span style="font-family:sans-serif;">Wang Zhang</span>
	 */
	private void uploadDataToCloud(){
		CloudObjectStorageUploader cloudObjectStorageUploader = CloudObjectStorageFactory.getCloudObjectStorageUploader();
		if(cloudObjectStorageUploader == null){
			logger.info("没有找到任何被启用的云服务。文件将被保留在服务器。");
		}
		String tRealPath = PathHelper.autoTrimPath((String) mInputData.get(8), true, false, '/');
		List<String> picPaths = PathHelper.getPagesFilePaths(mDocCode);
		if(picPaths == null)
			return;
		
		for(String filePath : picPaths){
			String localPath = tRealPath + PathHelper.autoTrimPath(filePath, false, true, '/');
			File localFile = new File(localPath);
			if(localFile.exists()){
				if(cloudObjectStorageUploader.uploadFileToCloud(localPath, cloudObjectStorageUploader.getCloudFilePath(filePath))){
					logger.debug(localPath + "\r\n上传成功至\r\n\t" + cloudObjectStorageUploader.getCloudFilePath(filePath));
					try{
						localFile.delete();
						logger.debug("已删除文件\r\n\t" + localPath);
					}catch(Exception e){
						
					}
				}
			}
		}
	}
	private void dealSignture(){
		String tRealPath = (String) mInputData.get(8);
		tRealPath = PathHelper.autoTrimPath(tRealPath, true, false, '/');
		if(mSubType != null && !mSubType.equals("")){
			LDScanCropDefDB tLDScanCropDefDB = new LDScanCropDefDB();
			tLDScanCropDefDB.setCropType("7");
			tLDScanCropDefDB.setSubType(mSubType);
			if(tLDScanCropDefDB.getInfo()){

				String num = String.valueOf(tLDScanCropDefDB.getPageCode());
				String x1 = String.valueOf(tLDScanCropDefDB.getx1());
				String y1 = String.valueOf(tLDScanCropDefDB.gety1());
				String w = String.valueOf(tLDScanCropDefDB.getwidth());
				String h = String.valueOf(tLDScanCropDefDB.getheight());
				String tSql = "Select a.pageName from es_doc_pages a, es_doc_main b where a.docid = b.docid and b.doccode = '"+"?mDocCode?"+"' and a.pagecode = "+"?num?";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSql);
				sqlbv1.put("mDocCode", mDocCode);
				sqlbv1.put("num", num);
				String pageName = new ExeSQL().getOneValue(sqlbv1);
				if(pageName!=null&& !pageName.equals("")){
					TransferData sTransferData = new TransferData();
					sTransferData.setNameAndValue("x1", x1);
					sTransferData.setNameAndValue("y1", y1);
					sTransferData.setNameAndValue("w", w);
					sTransferData.setNameAndValue("h", h);
					sTransferData.setNameAndValue("pageName", pageName);
					sTransferData.setNameAndValue("docCode", mDocCode);
					sTransferData.setNameAndValue("realPath", tRealPath);
					VData tVData = new VData();
					tVData.add(sTransferData);
					SignatureBL tSB = new SignatureBL();
					tSB.submitData(tVData, "");
				}
			}
		}
	}

	public VData getResult() {
		return mResult;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData(String cOperate) {
		ES_DOC_MAINSet tES_DOC_MAINSET = (ES_DOC_MAINSet) mInputData.get(0);
		ES_DOC_PAGESSet tES_DOC_PAGESSet = (ES_DOC_PAGESSet) mInputData.get(1);
		String[] strScanType = (String[]) mInputData.get(7);
		logger.debug(tES_DOC_MAINSET.size());

		if (tES_DOC_MAINSET.size() != 1) {
			CError tError = new CError();
			tError.moduleName = "CallService";
			tError.functionName = "save";
			tError.errorNo = "-1";
			tError.errorMessage = "传入数据出错";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 对es_doc_main的一个数据转换过程
		ES_DOC_MAINSchema tES_DOC_MAINSchema = tES_DOC_MAINSET.get(1);
		mSubType = tES_DOC_MAINSchema.getSubType();
		mDocCode = tES_DOC_MAINSchema.getDocCode();
		// Edited by wellhi 2005.08.27
		// 中心单证修改时借用了ManageCom保存IssueDocID，故要先去掉
		String strManageCom = tES_DOC_MAINSchema.getManageCom();
		ParameterDataConvert nConvert = new ParameterDataConvert();
		String strRManageCom = nConvert.getManageCom(strManageCom);
		String strIssueDocID = nConvert.getIssueDocID(strManageCom);
		tES_DOC_MAINSchema.setManageCom(strRManageCom);
		String strModifyOperator = tES_DOC_MAINSchema.getScanOperator();

		String CurrentDate = PubFun.getCurrentDate();
		String CurrentTime = PubFun.getCurrentTime(); // //////////////取当前时间，不能用客户端传来的时间/////////////////
														// haopan
		// 新单证上载
		if (tES_DOC_MAINSchema.getDocFlag().equals("0")) {
			tES_DOC_MAINSchema.setMakeDate(CurrentDate);
			tES_DOC_MAINSchema.setMakeTime(CurrentTime);
			tES_DOC_MAINSchema.setModifyDate(CurrentDate);
			tES_DOC_MAINSchema.setModifyTime(CurrentTime);
			// 事前扫描
			if (strScanType[0].equals("0")) {
				m_strServiceType = "1";
			}
			// 事后扫描
			else if (strScanType[0].equals("1")) {
				m_strServiceType = "3";
			}
		} else if (tES_DOC_MAINSchema.getDocFlag().equals("1")) {
			// Edited by wellhi 2005.08.27
			// 单证修改
			tES_DOC_MAINSchema.setModifyDate(CurrentDate);
			tES_DOC_MAINSchema.setModifyTime(CurrentTime);
			m_strServiceType = "2";
		}
		// else if(strScanType[0].equals("1")){
		// //Added by wellhi 2005.10.21
		// //事后扫描
		// m_strServiceType = "3";
		// }
		// tES_DOC_MAINSchema.setManageCom(strManageCom);
		if (tES_DOC_MAINSchema.getDocFlag().equals("0")) {
			tES_DOC_MAINSchema.setDocFlag("1");
			map.put(tES_DOC_MAINSchema, "INSERT");
		} else {
			map.put(tES_DOC_MAINSchema, "UPDATE");
			ES_DOC_PAGESDB oldES_DOC_PAGESDB = new ES_DOC_PAGESDB();
			ES_DOC_PAGESSet oldES_DOC_PAGESSet = new ES_DOC_PAGESSet();
			oldES_DOC_PAGESDB.setDocID(tES_DOC_MAINSchema.getDocID());
			oldES_DOC_PAGESSet = oldES_DOC_PAGESDB.query();
			if (oldES_DOC_PAGESSet.size() < 1) {
				CError tError = new CError();
				tError.moduleName = "CallService";
				tError.functionName = "save";
				tError.errorNo = "-1";
				tError.errorMessage = "查询数据库出错";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 校验是否有被删除的Page
			for (int j = 0; j < oldES_DOC_PAGESSet.size(); j++) {
				boolean blnDelete = true;
				for (int i = 0; i < tES_DOC_PAGESSet.size(); i++) {
					if (oldES_DOC_PAGESSet.get(j + 1).getPageID() == tES_DOC_PAGESSet
							.get(i + 1).getPageID()) {
						blnDelete = false;
						break;
					}
				}
				if (blnDelete) {
					map.put(oldES_DOC_PAGESSet.get(j + 1), "DELETE");
					this.writeLog(oldES_DOC_PAGESSet.get(j + 1), "DELETE",
							strModifyOperator);
				}
			}
		}
		// 对es_doc_pages的数据转换过程
		for (int i = 0; i < tES_DOC_PAGESSet.size(); i++) {
			// Edited by wellhi 2005.05.26
			ES_DOC_PAGESSchema nES_DOC_PAGESSchema = tES_DOC_PAGESSet
					.get(i + 1);
			// Added by wellhi 2005.08.26
			// 因为再UploadPrepareBL里把ServerBasePath添加到了PicPath所以必需再转换回来
			String strPicPath = nES_DOC_PAGESSchema.getPicPath();
			strPicPath = strPicPath.substring(strPicPath.indexOf("||") + 2);
			StrTool nStrTool = new StrTool();
			nES_DOC_PAGESSchema.setPicPath(nStrTool.replace(strPicPath, "||",
					""));

			if (nES_DOC_PAGESSchema.getPageFlag().equals("0")) {
				nES_DOC_PAGESSchema.setPageFlag("1");
				nES_DOC_PAGESSchema.setMakeDate(CurrentDate);
				nES_DOC_PAGESSchema.setMakeTime(CurrentTime);
				nES_DOC_PAGESSchema.setModifyDate(CurrentDate);
				nES_DOC_PAGESSchema.setModifyTime(CurrentTime);
				map.put(nES_DOC_PAGESSchema, "INSERT");
				this.writeLog(nES_DOC_PAGESSchema, "INSERT", strModifyOperator);
			} else {
				nES_DOC_PAGESSchema.setPageFlag("1");
				nES_DOC_PAGESSchema.setModifyDate(CurrentDate);
				nES_DOC_PAGESSchema.setModifyTime(CurrentTime);
				map.put(nES_DOC_PAGESSchema, "UPDATE");
				this.writeLog(nES_DOC_PAGESSchema, "UPDATE", strModifyOperator);
			}
		}
		// tES_DOC_MAINSchema.setManageCom(strManageCom);
		mInputData.setElementAt(strIssueDocID, 6);
		return true;
	}

	private boolean writeLog(ES_DOC_PAGESSchema eES_DOC_PAGESSchema,
			String strModifyDesc, String strModifyOperator) {
		ES_DOC_RELATIONDB nES_DOC_RELATIONDB = new ES_DOC_RELATIONDB();
		nES_DOC_RELATIONDB.setDocID(eES_DOC_PAGESSchema.getDocID());
		// 可能会建立多个关联
		ES_DOC_RELATIONSet nES_DOC_RELATIONSet = nES_DOC_RELATIONDB.query();
		int intCount = nES_DOC_RELATIONSet.size();
		if (intCount > 0) {
			for (int i = 0; i < intCount; i++) {
				ES_DOC_RELATIONSchema nES_DOC_RELATIONSchema = nES_DOC_RELATIONSet
						.get(i + 1);
				// 填写日志信息
				Es_Doc_LogSchema nEs_Doc_LogSchema = new Es_Doc_LogSchema();
				nEs_Doc_LogSchema.setLogID(getMaxNo(CON_ES_LOG_ID));
				nEs_Doc_LogSchema.setBussType(nES_DOC_RELATIONSchema
						.getBussType());
				nEs_Doc_LogSchema.setSubType(nES_DOC_RELATIONSchema
						.getSubType());
				nEs_Doc_LogSchema.setBussNo(nES_DOC_RELATIONSchema.getBussNo());
				nEs_Doc_LogSchema.setBussNoType(nES_DOC_RELATIONSchema
						.getBussNoType());
				nEs_Doc_LogSchema.setPageID(eES_DOC_PAGESSchema.getPageID());
				nEs_Doc_LogSchema.setDocID(eES_DOC_PAGESSchema.getDocID());
				nEs_Doc_LogSchema.setManageCom(eES_DOC_PAGESSchema
						.getManageCom());
				nEs_Doc_LogSchema.setScanOperator(eES_DOC_PAGESSchema
						.getOperator());
				nEs_Doc_LogSchema
						.setPageCode(eES_DOC_PAGESSchema.getPageCode());
				nEs_Doc_LogSchema
						.setHostName(eES_DOC_PAGESSchema.getHostName());
				nEs_Doc_LogSchema
						.setPageName(eES_DOC_PAGESSchema.getPageName());
				nEs_Doc_LogSchema.setPageSuffix(eES_DOC_PAGESSchema
						.getPageSuffix());
				nEs_Doc_LogSchema.setPicPathFTP(eES_DOC_PAGESSchema
						.getPicPathFTP());
				nEs_Doc_LogSchema.setPicPath(eES_DOC_PAGESSchema.getPicPath());
				nEs_Doc_LogSchema.setScanNo(eES_DOC_PAGESSchema.getScanNo());
				nEs_Doc_LogSchema
						.setMakeDate(eES_DOC_PAGESSchema.getMakeDate());
				nEs_Doc_LogSchema
						.setMakeTime(eES_DOC_PAGESSchema.getMakeTime());
				nEs_Doc_LogSchema.setModifyDate(eES_DOC_PAGESSchema
						.getModifyDate());
				nEs_Doc_LogSchema.setModifyTime(eES_DOC_PAGESSchema
						.getModifyTime());
				nEs_Doc_LogSchema.setModifyDesc(strModifyDesc);
				nEs_Doc_LogSchema.setModifyOperator(strModifyOperator);
				map.put(nEs_Doc_LogSchema, "INSERT");
			}
		}
		return true;
	}

	// 生成流水号，包含错误处理
	private String getMaxNo(String cNoType) {
		String strNo = PubFun1.CreateMaxNo(cNoType, 1);

		if (strNo.equals("") || strNo.equals("0")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UploadPrepareBL";
			tError.functionName = "getReturnData";
			tError.errorNo = "-90";
			tError.errorMessage = "生成流水号失败!";
			this.mErrors.addOneError(tError);
			strNo = "";
		}

		return strNo;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		return true;
	}

	public static void main(String[] args) {

//		VData tVData = new VData();
//		ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
//		tES_DOC_MAINSchema.setDocID("999999");
//		tES_DOC_MAINSchema.setDocFlag("1");
//		tES_DOC_MAINSchema.setBussType("TB");
//		tES_DOC_MAINSchema.setDocCode("20070518000007");
//		tES_DOC_MAINSchema.setSubType("UN001");
//		ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
//		tES_DOC_MAINSet.add(tES_DOC_MAINSchema);
//		tVData.add(tES_DOC_MAINSet);
//		CallService tCallService1 = new CallService();
//		tCallService1.submitData(tVData, "", "1");
//		UploadIndexBL tUploadIndexBL = new UploadIndexBL();
//		tUploadIndexBL.submitData(tVData, "");
//		PubSubmit pubsubmit = new PubSubmit();
//		if (!pubsubmit.submitData(tCallService1.getResult(), "")) {
//			CError tError = new CError();
//			tError.moduleName = "UploadIndexBL";
//			tError.functionName = "save";
//			tError.errorNo = "-1";
//			tError.errorMessage = "处理出错";
//		}
		String info = "2|238|1515|268|114";
		String[] arr = info.split("\\|");
		logger.debug(arr[0]);
		logger.debug(arr[1]);
		logger.debug(arr[2]);
		logger.debug(arr[3]);
		logger.debug(arr[4]);
	}
}
