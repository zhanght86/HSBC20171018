package com.sinosoft.easyscan5.core.service.clientupload.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.service.impl.BaseServiceImpl;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.EsDocMainPToEsQcMain;
import com.sinosoft.easyscan5.common.EsDocPagesPToEsQcPage;
import com.sinosoft.easyscan5.core.service.clientupload.UploadIndexService;
import com.sinosoft.easyscan5.core.vo.EsDocAndPageVO;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.entity.EsQcPages;
import com.sinosoft.easyscan5.util.FDate;
import com.sinosoft.lis.db.ES_BATCHNODB;
import com.sinosoft.lis.db.ES_COM_SERVERDB;
import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.db.LDScanCropDefDB;
import com.sinosoft.lis.easyscan.CallService;
import com.sinosoft.lis.easyscan.ParameterDataConvert;
import com.sinosoft.lis.easyscan.PathHelper;
import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageFactory;
import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageUploader;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.SignatureBL;
import com.sinosoft.lis.schema.ES_BATCHNOSchema;
import com.sinosoft.lis.schema.ES_COM_SERVERSchema;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_DOC_PROPERTYSchema;
import com.sinosoft.lis.schema.ES_SERVER_INFOSchema;
import com.sinosoft.lis.vschema.ES_BATCHNOSet;
import com.sinosoft.lis.vschema.ES_COM_SERVERSet;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class NewUploadIndexServiceImpl extends BaseServiceImpl implements
		UploadIndexService {
	/** 输入* */
	private List<EsDocAndPageVO> inputEsDocAndPageVOList;
	/**输出 */
	private List<EsDocAndPageVO> outEsDocAndPageVO = new ArrayList<EsDocAndPageVO>();
	private VData mOutputData;

	MMap map = new MMap();
	/** 服务器信息* */
	private ES_COM_SERVERSchema esComServer;
	private ES_SERVER_INFOSchema esServerInfo;
	private Logger logger = Logger.getLogger(this.getClass());
	private String manageCom = "";
	private String scanNo = "";
	private String channel = "";
	public ExeSQL nExeSQL = new ExeSQL();

	private ES_DOC_MAINSet mES_DOC_MAINSet = new ES_DOC_MAINSet();
	private ES_DOC_PAGESSet mES_DOC_PAGESSet=new ES_DOC_PAGESSet();
	private String mSubType = "";
	private String mDocCode = "";
	private String mRealPath = "";
	public boolean submitData(VData cInputData, String operate)
			throws Exception {
		inputEsDocAndPageVOList = cInputData.get(0) != null ? (List<EsDocAndPageVO>) cInputData
				.get(0) : null;
		if (!dealService()) {
			return false;
		}
		return true;
	}

	private boolean dealService() throws Exception {
		// TODO Auto-generated method stub
		String operator = "";
		String serviceType = "";
		if (inputEsDocAndPageVOList != null
				&& inputEsDocAndPageVOList.size() > 0) {
			List<EsQcPages> savePageList = new ArrayList<EsQcPages>();
			List<EsQcMain> saveMainList = new ArrayList<EsQcMain>();
			for (int i = 0; i < inputEsDocAndPageVOList.size(); i++) {
				EsDocAndPageVO esDocAndPageVO = (EsDocAndPageVO) inputEsDocAndPageVOList
						.get(i);
				operator = esDocAndPageVO.getEsQcMain().getScanOperator();
				serviceType = esDocAndPageVO.getEsQcMain().getFu1();
				mSubType = esDocAndPageVO.getEsQcMain().getSubType();
				mDocCode = esDocAndPageVO.getEsQcMain().getDocCode();
				//校验资料是否已经上传成功
				if(i==0){
					if(esDocAndPageVO.getEsQcMain().getBatchNo()!=null&&!"".equals(esDocAndPageVO.getEsQcMain().getBatchNo())){
						ES_BATCHNODB es_BATCHNODB = new ES_BATCHNODB();
						es_BATCHNODB.setbatchno(esDocAndPageVO.getEsQcMain().getBatchNo());
						ES_BATCHNOSet es_BATCHNOSet = es_BATCHNODB.query();
						if(es_BATCHNOSet!=null&&es_BATCHNOSet.size()>0){
							EsDocAndPageVO esDocVO = new EsDocAndPageVO();
							esDocVO.setReturn_Number(Constants.CLIENT_UPLOAD_SUCCESS);
							esDocVO.setReturn_Message(Constants.CLIENT_UPLOAD_SUCCESS_MESSAGE);
							outEsDocAndPageVO.add(esDocVO);
							return true;
						}else{
							ES_BATCHNOSchema es_BATCHNOSchema = new ES_BATCHNOSchema();
							es_BATCHNOSchema.setbatchno(esDocAndPageVO.getEsQcMain().getBatchNo());
							map.put(es_BATCHNOSchema, "INSERT");
						}
						if(!this.getServer(esDocAndPageVO.getEsQcMain())){
							return false;
						}
						
					}
					
				}
				//业务校验
				if(!bussCheck()){
					return false;
				}
				//整理索引，涉及到重复上载的逻辑
				if (!updateIndex(esDocAndPageVO, savePageList, saveMainList)) {
					return false;
				}
				
						
			}
			//转换数据位schema
			if (!this.doData(savePageList, saveMainList)) {
				return false;
			}
			//??????????????????????????????????????????????
			//???????????????????????????????????????????????
			//事前事后扫描字段再EsDocMain 的Fu1字段
			//提交数据库
			//调用服务类
			/**
			 (Servicetype)为0
		 * (事前扫描)或者 1(事后扫描);
		 * 4.0半扫描 1是事前扫描,2是事后扫描. 在这里做下转换
			 */
			//serviceType = "1";
			CallService tCallService = new CallService();
			String transServiceType = "";
			if(serviceType!=null&&serviceType.equals("0")){
				transServiceType = "1";
			}else{
				transServiceType = "2";
			}
			/** 得到工作流数据* */
			VData calServiceInputData = new VData(); 
			calServiceInputData.add(mES_DOC_MAINSet);
			calServiceInputData.add(mES_DOC_PAGESSet);
			logger.info("准备调用扫描服务类,事前事后扫描类型:"+serviceType);
			try{
			if (!tCallService.submitData(calServiceInputData, operator, transServiceType)) {
				EsDocAndPageVO esDocVo = new EsDocAndPageVO();
				esDocVo.setReturn_Number("-500");
				esDocVo.setReturn_Message("扫描服务类调用失败");
				outEsDocAndPageVO.add(esDocVo);
				logger.info("扫描服务类调用失败");
				//mErrors.copyAllErrors(tCallService.mErrors);
				return false;
			}
			}catch(Exception ex){
				//CError.buildErr(this, ex.getMessage());
				return false;
			}

			map.add((MMap) tCallService.getResult()
					.getObjectByObjectName("MMap", 0));
			
			//结束调用服务类
			VData tVData = new VData();
			tVData.add(map);
			
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tVData, "INSERT")) {
				EsDocAndPageVO esDocVo = new EsDocAndPageVO();
				esDocVo.setReturn_Number("-500");
				esDocVo.setReturn_Message("提交数据库失败");
				outEsDocAndPageVO.add(esDocVo);
				logger.info("提交数据库失败");
				return false;
			}
			
			
			//签名加图片云调用
			
			mRealPath = this.esServerInfo.getServerBasePath();
			logger.info("mRealPath:"+mRealPath);
			try{dealSignture();}catch(Exception ex){};
			try{uploadDataToCloud();}catch(Exception ex){};
			
			EsDocAndPageVO esDocVO = new EsDocAndPageVO();
			esDocVO.setReturn_Number(Constants.CLIENT_UPLOAD_SUCCESS);
			esDocVO.setReturn_Message(Constants.CLIENT_UPLOAD_SUCCESS_MESSAGE);
			outEsDocAndPageVO.add(esDocVO);
		}
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
		String tRealPath = PathHelper.autoTrimPath(mRealPath, true, false, '/');
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
		String tRealPath = mRealPath;
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

	private boolean bussCheck() throws Exception{
		// TODO Auto-generated method stub
		return true;
	}

	
	private boolean updateIndex(EsDocAndPageVO esDocAndPageVO,
			List<EsQcPages> savePageList, List<EsQcMain> saveMainList)
			throws Exception {
		EsQcMain inputEsQcMain = esDocAndPageVO.getEsQcMain();
	//	inputEsQcMain.getFu1();//事前事后扫描
		inputEsQcMain.setCreateDate(new Date());
		inputEsQcMain.setUpdateDate(new Date());
		manageCom = inputEsQcMain.getManageCom();
		scanNo = inputEsQcMain.getScanNo();
		channel = inputEsQcMain.getChannel();
		inputEsQcMain.setSystemNo("0");
		String docCode = inputEsQcMain.getDocCode();
		List<EsQcPages> inputEsQcPagesList = esDocAndPageVO.getEsQcPagesList();
		ES_DOC_MAINDB es_DOC_MAINDB = new ES_DOC_MAINDB();
		es_DOC_MAINDB.setDocCode(inputEsQcMain.getDocCode());
		es_DOC_MAINDB.setBussType(inputEsQcMain.getBussType());
		es_DOC_MAINDB.setSubType(inputEsQcMain.getSubType());
		ES_DOC_MAINSet es_DOC_MAINSet = es_DOC_MAINDB.query();	
		if(es_DOC_MAINSet!=null&&es_DOC_MAINSet.size()>0){
			ES_DOC_MAINSchema nDoc_MAINSchema =  es_DOC_MAINSet.get(1);
			nDoc_MAINSchema.setModifyDate(FDate.getCurrentDate());
			nDoc_MAINSchema.setModifyTime(FDate.getCurrentTime());
			nDoc_MAINSchema.setNumPages(inputEsQcPagesList.size()+nDoc_MAINSchema.getNumPages());
			nDoc_MAINSchema.setVersion(String.valueOf((Integer.valueOf(nDoc_MAINSchema.getVersion())+1)));
			map.put(nDoc_MAINSchema, "UPDATE");
			for (int i = 0; i < inputEsQcPagesList.size(); i++) {
				EsQcPages esQcPages = inputEsQcPagesList.get(i);
				esQcPages.setCreateDate(new Date());
				esQcPages.setServerNo(esServerInfo.getHostName());
				esQcPages.setUpdateDate(new Date());
				esQcPages.setPicPath(Constants.PIC_PATH
						+ esQcPages.getPicPath());
				esQcPages.setPageNo(Long.valueOf(esQcPages.getPageNo()+(int)nDoc_MAINSchema.getNumPages()));
				savePageList.add(esQcPages);
			}
		} else {
			inputEsQcMain.setDocFlag("0");
			saveMainList.add(inputEsQcMain);
			for (int i = 0; i < inputEsQcPagesList.size(); i++) {
				EsQcPages esQcPages = inputEsQcPagesList.get(i);
				esQcPages.setCreateDate(new Date());
				esQcPages.setServerNo(esServerInfo.getHostName());
				esQcPages.setUpdateDate(new Date());
				esQcPages.setPicPath(Constants.PIC_PATH
						+ esQcPages.getPicPath());
				savePageList.add(esQcPages);
			}
		}
		return true;
	}




	/**
	 * 获取服务器信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean getServer(EsQcMain esQcMain) throws Exception {

		ES_COM_SERVERDB tES_COM_SERVERDB = new ES_COM_SERVERDB();
		ParameterDataConvert nConvert = new ParameterDataConvert();
		String strManageCom = nConvert.getManageCom(esQcMain.getManageCom());
		tES_COM_SERVERDB.setManageCom(strManageCom);
		ES_COM_SERVERSet tES_COM_SERVERSet = tES_COM_SERVERDB.query();
		logger.debug(tES_COM_SERVERSet.size());
		if (tES_COM_SERVERSet.size() == 0) {
			EsDocAndPageVO esDocVo = new EsDocAndPageVO();
			esDocVo.setReturn_Number("-500");
			esDocVo.setReturn_Message("管理机构" + strManageCom + "没有设置对应的文件服务器");
			outEsDocAndPageVO.add(esDocVo);
			return false;
		}

		// 查询Es_Server_Info的数据
		ES_SERVER_INFODB tES_SERVER_INFODB = new ES_SERVER_INFODB();
		String strHostName = tES_COM_SERVERSet.get(1).getHostName();
		tES_SERVER_INFODB.setHostName(strHostName);

		ES_SERVER_INFOSet tES_SERVER_INFOSet = tES_SERVER_INFODB.query();
		if (tES_SERVER_INFOSet == null || tES_SERVER_INFOSet.size() != 1) {
			EsDocAndPageVO esDocVo = new EsDocAndPageVO();
			esDocVo.setReturn_Number("-500");
			esDocVo.setReturn_Message("获取服务器信息失败，未配置服务器信息");
			outEsDocAndPageVO.add(esDocVo);
			return false;
		}
		esServerInfo = tES_SERVER_INFOSet.get(1);
		return true;
	}

	private boolean doData(List<EsQcPages> savePageList,
			List<EsQcMain> saveMainList) throws Exception {
		// MMap tMMap = new MMap();
		if (savePageList.size() > 0) {
			for (EsQcPages page : savePageList) {
				ES_DOC_PAGESSchema eSchema = new ES_DOC_PAGESSchema();
				EsDocPagesPToEsQcPage.esQcPageToEsDocPagesP(page, eSchema,
						manageCom, scanNo);
				map.put(eSchema, "INSERT");
				mES_DOC_PAGESSet.add(eSchema);
			}
		}
		if (saveMainList.size() > 0) {
			for (int i = 0; i < saveMainList.size(); i++) {
				EsQcMain doc = saveMainList.get(i);
				ES_DOC_MAINSchema eSchema = new ES_DOC_MAINSchema();
				EsDocMainPToEsQcMain.esQcMainToEsDocMainP(doc, eSchema);
				eSchema.setVersion("1");
				ES_DOC_PROPERTYSchema es_DOC_PROPERTYSchema  = getDocPropertySchema(doc);
				map.put(eSchema, "INSERT");
				map.put(es_DOC_PROPERTYSchema, "INSERT");
				
				mES_DOC_MAINSet.add(eSchema);
			}
		}
		
		
		return true;
	}

	/**
	 * 获取返回结果
	 */
	public VData getResult() {
		mOutputData = new VData();
		mOutputData.add(0, outEsDocAndPageVO);
		return mOutputData;
	}
	
	private  ES_DOC_PROPERTYSchema  getDocPropertySchema(EsQcMain inputesQcMain){
		ES_DOC_PROPERTYSchema nES_DOC_PROPERTYSchema = new ES_DOC_PROPERTYSchema();
		nES_DOC_PROPERTYSchema.setP1(inputesQcMain.getP1());
		nES_DOC_PROPERTYSchema.setP2(inputesQcMain.getP2());
		nES_DOC_PROPERTYSchema.setP3(inputesQcMain.getP3());
		nES_DOC_PROPERTYSchema.setP4(inputesQcMain.getP4());
		nES_DOC_PROPERTYSchema.setP5(inputesQcMain.getP5());
		nES_DOC_PROPERTYSchema.setP6(inputesQcMain.getP6());
		nES_DOC_PROPERTYSchema.setP7(inputesQcMain.getP7());
		nES_DOC_PROPERTYSchema.setP8(inputesQcMain.getP8());
		nES_DOC_PROPERTYSchema.setP9(inputesQcMain.getP9());
		nES_DOC_PROPERTYSchema.setP10(inputesQcMain.getP10());
		nES_DOC_PROPERTYSchema.setP11(inputesQcMain.getP11());
		nES_DOC_PROPERTYSchema.setP12(inputesQcMain.getP12());
		nES_DOC_PROPERTYSchema.setP13(inputesQcMain.getP13());
		nES_DOC_PROPERTYSchema.setP14(inputesQcMain.getP14());
		nES_DOC_PROPERTYSchema.setP15(inputesQcMain.getP15());
		nES_DOC_PROPERTYSchema.setP16(inputesQcMain.getP16());
		nES_DOC_PROPERTYSchema.setP17(inputesQcMain.getP17());
		nES_DOC_PROPERTYSchema.setP18(inputesQcMain.getP18());
		nES_DOC_PROPERTYSchema.setP19(inputesQcMain.getP19());
		nES_DOC_PROPERTYSchema.setP20(inputesQcMain.getP20());
		nES_DOC_PROPERTYSchema.setDocID(inputesQcMain.getDocId());
		nES_DOC_PROPERTYSchema.setMakeDate(FDate.getCurrentDate());
		nES_DOC_PROPERTYSchema.setMakeTime(FDate.getCurrentTime());
		nES_DOC_PROPERTYSchema.setDefCode("00000001");
		nES_DOC_PROPERTYSchema.setOperator(inputesQcMain.getScanOperator());
		nES_DOC_PROPERTYSchema.setModifyDate(FDate.getCurrentDate());
		nES_DOC_PROPERTYSchema.setModifyTime(FDate.getCurrentTime());
		return nES_DOC_PROPERTYSchema;
	}
}
