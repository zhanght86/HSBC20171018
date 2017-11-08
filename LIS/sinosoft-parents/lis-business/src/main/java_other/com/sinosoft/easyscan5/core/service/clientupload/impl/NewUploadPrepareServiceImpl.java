package com.sinosoft.easyscan5.core.service.clientupload.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.service.impl.BaseServiceImpl;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.EsDocMainPToEsQcMain;
import com.sinosoft.easyscan5.common.EsDocPagesPToEsQcPage;
import com.sinosoft.easyscan5.core.service.clientupload.UploadPrepareService;
import com.sinosoft.easyscan5.core.vo.EsDocAndPageVO;
import com.sinosoft.easyscan5.entity.EsBatchnoInfo;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.util.FDate;
import com.sinosoft.lis.db.ES_BATCHNODB;
import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.schema.ES_BATCHNOSchema;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_DOC_PROPERTYSchema;
import com.sinosoft.lis.vdb.ES_BATCHNODBSet;
import com.sinosoft.lis.vschema.ES_BATCHNOSet;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.db.ES_COM_SERVERDB;
import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.easyscan.ParameterDataConvert;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_COM_SERVERSchema;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_SERVER_INFOSchema;
import com.sinosoft.lis.vschema.ES_COM_SERVERSet;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.VData;

/**
 * 新单上载校验
 * 
 */
public class NewUploadPrepareServiceImpl extends BaseServiceImpl implements
		UploadPrepareService {
	Logger logger = Logger.getLogger(NewUploadPrepareServiceImpl.class);
	/** 输入* */
	private List<EsDocAndPageVO> inputEsDocAndPageVO;
	/** 输出 */
	private List<EsDocAndPageVO> outEsDocAndPageVO = new ArrayList<EsDocAndPageVO>();
	private VData mOutputData;
	/** 服务器信息* */
	private ES_SERVER_INFOSchema esServerInfo;

	public boolean submitData(VData cInputData, String channel, String operate)
			throws Exception {
		boolean flag = false;
		inputEsDocAndPageVO = cInputData.get(0) != null ? (List<EsDocAndPageVO>) cInputData
				.get(0) : null;
		// 业务校验
		if (!bussCheck()) {
			return false;
		}
		// 获取上载服务器信息
		if (inputEsDocAndPageVO != null && inputEsDocAndPageVO.size() > 0) {
			EsDocAndPageVO esDocAndPageVO = (EsDocAndPageVO) inputEsDocAndPageVO
					.get(0);
			EsQcMain esQcMain = esDocAndPageVO.getEsQcMain();
			if (!getServer(esQcMain)) {
				return false;
			}
			EsDocAndPageVO esDocVO = new EsDocAndPageVO();
			esDocVO.setEsQcMain(esQcMain);
			esDocVO.setPage_URL(new String[] {
					Constants.HTTP+esServerInfo.getServerPort()
							+ Constants.UPLOAD_FILE_JSP,
					esQcMain.getManageCom() + "/"
							+ FDate.getCurrentDate().replaceAll("-", "/") + "/" });
			esDocVO.setReturn_Number(Constants.CLIENT_UPLOAD_SUCCESS);
			esDocVO.setReturn_Message(Constants.CLIENT_UPLOAD_SUCCESS_MESSAGE);
			outEsDocAndPageVO.add(esDocVO);
			return true;
		} else {
			EsDocAndPageVO esDocVo = new EsDocAndPageVO();
			esDocVo.setReturn_Number("-500");
			esDocVo.setReturn_Message("上载失败，上载信息的Vdata无参数");
			outEsDocAndPageVO.add(esDocVo);
			return false;
		}
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

	public boolean bussCheck() throws Exception {
		// 业务校验，通过返回true，不通过返回false
		/*
		 * 返回不通过的原因 EsDocAndPageVO esDocVo = new EsDocAndPageVO();
		 * esDocVo.setReturn_Number("-500"); esDocVo.setReturn_Message("校验不通过");
		 * outEsDocAndPageVO.add(esDocVo); return false;
		 */
//		for(int i=0;i<inputEsDocAndPageVO.size();i++){
//			;
//			this.getDocPropertySchema(inputEsDocAndPageVO.get(i).getEsQcMain());
//		}
		
		
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
