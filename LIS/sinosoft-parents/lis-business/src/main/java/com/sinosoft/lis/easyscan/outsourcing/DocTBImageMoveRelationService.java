package com.sinosoft.lis.easyscan.outsourcing;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Date;

import org.jdom.Document;
import org.jdom.output.XMLOutputter;

import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 录入外包扫描功能——新契约单证处理接口实现类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author liuxin
 * @version 1.0
 */
public class DocTBImageMoveRelationService {
private static Logger logger = Logger.getLogger(DocTBImageMoveRelationService.class);
	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public DocTBImageMoveRelationService() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 影像存放在应用服务器的绝对路径，通过配置获得，与strTempFolder一致
		File strDownloadFolder = null;
		FileOperate fileoperate = new FileOperate();

		try {
			/** **********************得到具体数据信息**************** */
			// 取ES_DOC_MAINSchema
			ES_DOC_MAINSet tES_DOC_MAINSet = (ES_DOC_MAINSet) cInputData.get(0);
			if (tES_DOC_MAINSet.size() != 1) {
				logger.debug("DocTBImageMoveRelationService->submitData传入数据出错!");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "传入数据出错!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 取ES_DOC_PAGESSet
			ES_DOC_PAGESSet tES_DOC_PAGESSet = (ES_DOC_PAGESSet) cInputData
					.get(1);
			if (tES_DOC_PAGESSet.size() == 0) {
				logger.debug("DocTBImageMoveRelationService->submitData传入数据出错");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "传入数据出错!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 取TransferData
			TransferData tTransferData = (TransferData) cInputData
					.getObjectByObjectName("TransferData", 0);
			if (tTransferData == null) {
				logger.debug("DocTBImageMoveRelationService->submitData传入数据出错");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "传入数据出错!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 取处理类的名称
			String strClassName = (String) cInputData.get(3);
			if (strClassName == null || strClassName == "") {
				logger.debug("DocTBImageMoveRelationService->submitData传入数据出错－处理类名称");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "传入数据出错!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 取影像文件格式定义
			String tPageSuffixType = (String) cInputData.get(4);
			if (!(tPageSuffixType == "1" || tPageSuffixType == "2" || tPageSuffixType == "3")) {
				logger.debug("DocTBImageMoveRelationService->submitData传入数据出错-文件格式");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "传入数据出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			/** ***********************生成应用服务器下载临时路径************************ */
			// EsDocMoveFactoryModule tEsDocMoveFactoryModule = new
			// EsDocMoveFactoryModule();
			// 接口本身是不能实例化的，但是可以通过子类来构造一个对象
			// EsDocMoveFactory tEsDocMoveFactory = tEsDocMoveFactoryModule.
			// getClassName(
			// strClassName); //可以方便地创建各种类型的XML，而程序结构和代码的修改量达到最小
			Class tClass = Class.forName(strClassName);
			logger.debug("处理类：" + strClassName);
			EsDocMoveInterface tEsDocMoveInterface = (EsDocMoveInterface) tClass
					.newInstance();

			// 应用服务器上临时文件夹的创建,该路径必须是以"/"结束
			VData cVData = new VData();
			/** ***接口getTempFolder**** */
			cVData = tEsDocMoveInterface.getTempFolder(cInputData);
			if (cVData == null) {
				logger.debug("DocTBImageMoveRelationService->submitData获取临时路径出错!");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "获取临时路径出错,为空!";
				this.mErrors.addOneError(tError);
				return false;
			}
			String strTempFolder = (String) cVData.get(0);
			if (strTempFolder == "") {
				logger.debug("DocTBImageMoveRelationService->submitData获取临时路径出错!");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "获取临时路径出错!";
				this.mErrors.addOneError(tError);
				return false;
			}
			strDownloadFolder = new File(strTempFolder, String
					.valueOf((new Date().getTime())));
			fileoperate.createDir(strDownloadFolder);

			/** ***********************生成xml过程************************ */
			logger.debug("#starting build xml...");

			// 创建xml
			Document Doc = tEsDocMoveInterface.GetXMLDoc(cInputData);
			/** ***接口getXMLName**** */
			String strXMLName = tEsDocMoveInterface.getXMLName(cInputData);
			logger.debug(strXMLName);
			if (strXMLName == null || strXMLName == "") {
				logger.debug("DocTBImageMoveRelationService->submitData获取xml文件名称出错!");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationServce";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "获取xml文件名称出错，为空!";
				tError.errorMessage = "获取xml文件名称出错，为空!";
				this.mErrors.addOneError(tError);
				fileoperate.delFolder(strDownloadFolder);
				return false;
			}

			if (!BuildXMLDoc(strDownloadFolder, Doc, strXMLName)) {
				logger.debug("DocTBImageMoveRelationService->submitDataxml数据生成出错!");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "xml据生成出错!";
				this.mErrors.addOneError(tError);
				fileoperate.delFolder(strDownloadFolder);
				return false;
			}

			/** ***********************下载影像文件过程*********************** */
			logger.debug("#starting download...");
			DownloadFile tDownloadFile = new DownloadFile();
			// 根据格式下载不同的影像文件
			if (tPageSuffixType == "1") {
				tDownloadFile = getDownloadFile(tES_DOC_PAGESSet,
						strDownloadFolder, ".tif", tDownloadFile);
			} else if (tPageSuffixType == "2") {
				tDownloadFile = getDownloadFile(tES_DOC_PAGESSet,
						strDownloadFolder, ".gif", tDownloadFile);
			} else if (tPageSuffixType == "3") {

				tDownloadFile = getDownloadFile(tES_DOC_PAGESSet,
						strDownloadFolder, ".tif", tDownloadFile);

				tDownloadFile = getDownloadFile(tES_DOC_PAGESSet,
						strDownloadFolder, ".gif", tDownloadFile);

			}
			if (!tDownloadFile.downLoadByList()) {
				CError tError = new CError();
				logger.debug("DocTBImageMoveRelationService->submitDataxml影像文件下载出错!");
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件下载出错!";
				this.mErrors.addOneError(tError);
				fileoperate.delFolder(strDownloadFolder);
				return false;
			}

			/** **********************xml文件和影像转移目的地址的获取******************** */
			logger.debug("#starting prepare transform...");
			// 获取转移的目标文件夹，通过调用接口实现
			// 格式如："//192.168.71.125/vss_es/temp/";
			VData tVData = new VData();
			/** ***接口getDesFolder**** */
			tVData = tEsDocMoveInterface.getDesFolder(cInputData);
			if (tVData == null) {
				logger.debug("DocTBImageMoveRelationService->submitData获取目的路径出错!");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "获取目的路径出错，为空!";
				this.mErrors.addOneError(tError);
				fileoperate.delFolder(strDownloadFolder);
				return false;
			}
			String strDesFolder = (String) tVData.get(0);
			// String strDesFolder = "//192.168.71.125/vss_es/temp/";
			if (strDesFolder == "") {
				logger.debug("DocTBImageMoveRelationService->submitDataxml获取目的路径出错");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "获取目的路径出错!";
				this.mErrors.addOneError(tError);
				fileoperate.delFolder(strDownloadFolder);
				return false;
			}

			fileoperate.createDir(new File(strDesFolder));

			/** **********************xml文件和影像转移过程******************** */
			logger.debug("#starting do transform...");
			// 校验影像文件是否存在
			if (!checkImageExist(tES_DOC_PAGESSet, strDownloadFolder,
					tPageSuffixType)) {
				logger.debug("文件转移时，影像文件不存在！");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationSerice";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件不存在!";

				fileoperate.delFolder(strDownloadFolder);
				return false;
			}

			// 影像文件的转移
			if (!moveImageExist(tES_DOC_PAGESSet, strDownloadFolder,
					strDesFolder, tPageSuffixType)) {
				logger.debug("文件转移时，影像文件不存在！");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationSerice";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件不存在!";
				fileoperate.delFolder(strDownloadFolder);
				return false;
			}

			// xml文件的转移
			File strXMLSouPath = new File(strDownloadFolder, strXMLName
					+ ".xml");
			File strXMLDesPath = new File(strDesFolder, strXMLName + ".xml");
			logger.debug("#xml transform path：" + strXMLDesPath);
			// 校验xml文件是否存在
			if (!checkXMLExist(strXMLSouPath)) {
				logger.debug("文件转移时，xml文件不存在！");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationSerice";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "xml文件不存在!";
				this.mErrors.addOneError(tError);

				fileoperate.delFolder(strDownloadFolder);
				return false;
			}

			if (!moveFileToBPO(strXMLSouPath, strXMLDesPath)) {
				logger.debug("DocTBImageMoveRelationSerice－>submitData,xml文件转移出错!");
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationSerice";
				tError.functionName = "submitData";
				tError.errorNo = "-500";
				tError.errorMessage = "xml文件转移出错!";
				this.mErrors.addOneError(tError);
				fileoperate.delFolder(strDownloadFolder);
				return false;
			}
			logger.debug("#starting del temp path " + strDownloadFolder);
			fileoperate.delFolder(strDownloadFolder);
			logger.debug("#all done");
			closeFtp();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, e.getMessage());
			fileoperate.delAllFile(strDownloadFolder);
			logger.debug("#bpo error:" + e.getMessage());
			return false;
		}

	}

	public String getImagePath(ES_DOC_PAGESSchema tES_DOC_PAGESSchema,
			String strPageSuffix) {
		try {
			ES_SERVER_INFODB tES_SERVER_INFODB = new ES_SERVER_INFODB();
			tES_SERVER_INFODB.setHostName(tES_DOC_PAGESSchema.getHostName());

			ES_SERVER_INFOSet tES_SERVER_INFOSet = tES_SERVER_INFODB.query();

			if (tES_SERVER_INFOSet.size() == 0) {
				// @@错误处
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "getImagePath";
				tError.errorNo = "-12";
				tError.errorMessage = "没有查询到保存单证页"
						+ tES_DOC_PAGESSchema.getPageName() + "的服务器";
				this.mErrors.addOneError(tError);
				return "";
			}
			// 下载的源地址(包括路径、文件名字及类型)
			String strUrl = "http://"
					+ tES_SERVER_INFOSet.get(1).getServerPort() + "/"
					+ tES_DOC_PAGESSchema.getPicPath()
					+ tES_DOC_PAGESSchema.getPageName() + strPageSuffix;
			return strUrl;
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DocTBImageMoveRelationService";
			tError.functionName = "getImagePat";
			tError.errorNo = "-99";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return "";
		}

	}

	// 校验xml文件是否存在
	public boolean checkXMLExist(File strFilePath) {
		File oldImagefile = strFilePath;
		if (!oldImagefile.exists()) {
			// 文件不存在时
			CError tError = new CError();
			tError.moduleName = "DocTBImageMoveRelationService";
			tError.functionName = "checkFileExist";
			tError.errorNo = "-500";
			tError.errorMessage = "影像文件转移时，源文件不存在!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			return true;
		}
	}

	// 校验影像文件是否存在
	public boolean checkImageExist(ES_DOC_PAGESSet tES_DOC_PAGESSet,
			File strDownloadFolder, String strPageSuffixType) {
		if (strPageSuffixType == "1") {
			if (!checkImageExistbySuffix(tES_DOC_PAGESSet, strDownloadFolder,
					".tif")) {
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "checkImageExist";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件转移时，源影像文件不存在!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else if (strPageSuffixType == "2") {
			if (!checkImageExistbySuffix(tES_DOC_PAGESSet, strDownloadFolder,
					".gif")) {
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "checkImageExist";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件转移时，源影像文件不存在!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else if (strPageSuffixType == "3") {
			if (!checkImageExistbySuffix(tES_DOC_PAGESSet, strDownloadFolder,
					".tif")) {
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "checkImageExist";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件转移时，源影像文件不存在!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (!checkImageExistbySuffix(tES_DOC_PAGESSet, strDownloadFolder,
					".gif")) {
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "checkImageExist";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件转移时，源影像文件不存在!";
				this.mErrors.addOneError(tError);
				return false;
			}

		}

		return true;

	}

	// 通过影像文件后缀来校验影像
	public boolean checkImageExistbySuffix(ES_DOC_PAGESSet tES_DOC_PAGESSet,
			File strDownloadFolder, String strPageSuffix) {

		for (int i = 0; i < tES_DOC_PAGESSet.size(); i++) {
			ES_DOC_PAGESSchema tES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
			tES_DOC_PAGESSchema = tES_DOC_PAGESSet.get(i + 1);
			if (tES_DOC_PAGESSchema.getPageSuffix() == ".jpg") {
				String strFileSouPath = strDownloadFolder
						+ tES_DOC_PAGESSchema.getPageName() + ".jpg";
				File oldImagefile = new File(strFileSouPath);
				if (!oldImagefile.exists()) {
					// 文件不存在时
					CError tError = new CError();
					tError.moduleName = "DocTBImageMoveRelationService";
					tError.functionName = "checkImageExist";
					tError.errorNo = "-500";
					tError.errorMessage = "影像文件转移时，源影像文件不存在!";
					this.mErrors.addOneError(tError);
					return false;
				}
			} else {
				// 影像文件的路径信息
				File oldImagefile = new File(strDownloadFolder,
						tES_DOC_PAGESSchema.getPageName() + strPageSuffix);
				if (!oldImagefile.exists()) {
					// 文件不存在时
					CError tError = new CError();
					tError.moduleName = "DocTBImageMoveRelationService";
					tError.functionName = "checkImageExist";
					tError.errorNo = "-500";
					tError.errorMessage = "影像文件转移时，源影像文件不存在!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
		}

		return true;

	}

	// 转移影像文件
	public boolean moveImageExist(ES_DOC_PAGESSet tES_DOC_PAGESSet,
			File strDownloadFolder, String strDesFolder,
			String strPageSuffixType) {

		if (strPageSuffixType == "1") {
			if (!moveImagebySuffix(tES_DOC_PAGESSet, strDownloadFolder,
					strDesFolder, ".tif")) {
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "checkImageExist";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件转移时，源影像文件不存在!";
				this.mErrors.addOneError(tError);
				return false;
			}

		} else if (strPageSuffixType == "2") {
			if (!moveImagebySuffix(tES_DOC_PAGESSet, strDownloadFolder,
					strDesFolder, ".gif")) {
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "checkImageExist";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件转移时，源影像文件不存在!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else if (strPageSuffixType == "3") {
			// if!moveImagebySuffix(tESDOC_PAGESSet, strDownloadFoder,
			// strDesFolder,
			if (!moveImagebySuffix(tES_DOC_PAGESSet, strDownloadFolder,
					strDesFolder, ".tif")) {
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "checkImageExist";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件转移时，源影像文件不存在!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (!moveImagebySuffix(tES_DOC_PAGESSet, strDownloadFolder,
					strDesFolder, ".gif")) {
				CError tError = new CError();
				tError.moduleName = "DocTBImageMoveRelationService";
				tError.functionName = "checkImageExist";
				tError.errorNo = "-500";
				tError.errorMessage = "影像文件转移时，源影像文件不存在!";
				this.mErrors.addOneError(tError);
				return false;
			}

		}

		return true;

	}

	// 通过影像文件后缀来转移影像
	public boolean moveImagebySuffix(ES_DOC_PAGESSet tES_DOC_PAGESSet,
			File strDownloadFolder, String strDesFolder, String strPageSuffix) {
		FileOperate fileoperate = new FileOperate();
		for (int i = 0; i < tES_DOC_PAGESSet.size(); i++) {
			ES_DOC_PAGESSchema tES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
			tES_DOC_PAGESSchema = tES_DOC_PAGESSet.get(i + 1);
			if (tES_DOC_PAGESSchema.getPageSuffix() == ".jpg") {
				// 影像文件的路径信息
				File strFileSouPath = new File(strDownloadFolder,
						tES_DOC_PAGESSchema.getPageName() + ".jpg");

				// 影像文件的目的路径
				File strFileDesPath = new File(strDesFolder,
						tES_DOC_PAGESSchema.getPageName() + ".jpg");

				logger.debug(strFileSouPath);
				logger.debug(strFileDesPath);
				if (!moveFileToBPO(strFileSouPath, strFileDesPath)) {
					logger.debug("DocTBImageMoveRelationSerice－>submitData,影像转移出错!");
					CError tError = new CError();
					tError.moduleName = "DocTBImageMoveRelationSerice";
					tError.functionName = "submitData";
					tError.errorNo = "-500";
					tError.errorMessage = "影像转移出错!";
					this.mErrors.addOneError(tError);
					fileoperate.delAllFile(strDownloadFolder);
					return false;
				}
			} else {
				// 影像文件的路径信息
				File strFileSouPath = new File(strDownloadFolder,
						tES_DOC_PAGESSchema.getPageName() + strPageSuffix);

				// 影像文件的目的路径
				File strFileDesPath = new File(strDesFolder,
						tES_DOC_PAGESSchema.getPageName() + strPageSuffix);

				logger.debug(strFileSouPath);
				logger.debug(strFileDesPath);
				if (!moveFileToBPO(strFileSouPath, strFileDesPath)) {
					logger.debug("DocTBImageMoveRelationSerice－>submitData,影像转移出错!");
					CError tError = new CError();
					tError.moduleName = "DocTBImageMoveRelationSerice";
					tError.functionName = "submitData";
					tError.errorNo = "-500";
					tError.errorMessage = "影像转移出错!";
					this.mErrors.addOneError(tError);
					fileoperate.delAllFile(strDownloadFolder);
					return false;
				}

			}
		}
		return true;
	}

	FtpClient ftp = null;


	private boolean moveFileToBPO(File src, File desc) {
		if(needftpmove())
			return moveFileFtp(src, desc);
		else
			return new FileOperate().moveFile(src, desc);
	}

	int needftp=0;
	private boolean needftpmove() {
		if (needftp == 0) {
			needftp = 1;
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar("bpoftp");
			if (tLDSysVarDB.getInfo() && tLDSysVarDB.getSysVarValue() != null
					&& tLDSysVarDB.getSysVarValue().startsWith("ftp"))
				needftp = 2;
		}
		if (needftp == 2) {
			return true;
		} else {
			return false;
		}
	}

	private boolean moveFileFtp(File src, File desc) {
		if (!isConnectFtp()) {
			connectToFtp();
		}
		if (ftp == null) {
			CError.buildErr(this, "ftp连接失败");
			return false;
		}
		InputStream sendFile = null;
		TelnetOutputStream outs = null;
		DataOutputStream outputs = null;
		try {
			sendFile = new FileInputStream(src);

//			outs = ftp.put(desc.getPath());
			outs = (TelnetOutputStream) ftp.putFileStream(desc.getPath(), true);  //jdk 1.7
			outputs = new DataOutputStream(outs);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = sendFile.read(buffer)) >= 0) {
				outputs.write(buffer, 0, len);
			}
			logger.debug("上传文件" + src + "成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "文件" + src + "上传到失败");
			return false;
		} finally {
			try {
				outputs.close();
				outs.close();
				sendFile.close();
			} catch (Exception ex) {
			}
		}

		return true;
	}

	private void connectToFtp() {
		LDSysVarDB tLDSysVarDB=new LDSysVarDB();
		tLDSysVarDB.setSysVar("bpoftp");
		if(!tLDSysVarDB.getInfo())
			throw new RuntimeException("缺少bpoftp描述");
		String url=tLDSysVarDB.getSysVarValue();
		// ftp,10.0.2.7,21,bea,password
		String[] ss=url.split(",");
		if(ss.length<5)
			throw new RuntimeException("bpoftp描述错误");
		if(!"ftp".equals(ss[0].trim()))
			throw new RuntimeException("bpoftp描述错误");
		
		String path = ss[1].trim();
//		try {
//			ftp = new FtpClient(path, Integer.parseInt(ss[2]));
//			ftp.login(ss[3].trim(), ss[4].trim());
//			ftp.binary();
//		} catch (Exception ex) {
//			try {
//				ftp.closeServer();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			throw new RuntimeException("FtpServer " + path + "连接失败", ex);
//		}
		//jdk 1.7 
		FtpClient ftp = null;
		try {
			ftp =FtpClient.create(); 
			ftp.connect(new InetSocketAddress(path, Integer.parseInt(ss[2]))); 
			ftp.login(ss[3],ss[4].toCharArray());
			ftp.setBinaryType();  
		} catch (Exception ex) {
			try {
				ftp.close();;
			} catch (IOException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("FtpServer " + path + "连接失败", ex);
		}
	}

	private void closeFtp() {
		if(!needftpmove())
			return;
		try {
//			ftp.closeServer();
			ftp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isConnectFtp() {
//		try {
//			if (ftp == null)
//				return false;
//			ftp.pwd();
//		} catch (IOException e) {
//			return false;
//		}
//		return true;
		return ftp.isConnected();//JDK 1.7
	}

	public DownloadFile getDownloadFile(ES_DOC_PAGESSet tES_DOC_PAGESSet,
			File strDownloadFolder, String strPageSuffix,
			DownloadFile tDownloadFile) {

		ES_DOC_PAGESSchema tES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();

		for (int i = 0; i < tES_DOC_PAGESSet.size(); i++) {

			tES_DOC_PAGESSchema = tES_DOC_PAGESSet.get(i + 1);
			if (tES_DOC_PAGESSchema.getPageSuffix() == ".jpg") {
				// 下载的源地址
				// String strUrl = getImagePath(tES_DOC_PAGESSchema);
				String strUrl = getImagePath(tES_DOC_PAGESSchema, ".jpg");
				logger.debug("#download source:" + strUrl);
				// 下载的目的地址（包括径、文件名字及类）
				File strFileName = new File(strDownloadFolder,
						tES_DOC_PAGESSchema.getPageName() + ".jpg");
				logger.debug("#download target:" + strFileName.getAbsolutePath());
				tDownloadFile.addItem(strUrl, strFileName.getAbsolutePath());
			} else {
				// 下载的源地址
				// String strUrl = getImagePath(tES_DOC_PAGESSchema);
				String strUrl = getImagePath(tES_DOC_PAGESSchema, strPageSuffix);
				logger.debug("#download source:" + strUrl);
				// 下载的目的地址（包括径、文件名字及类）
				File strFileName = new File(strDownloadFolder,
						tES_DOC_PAGESSchema.getPageName() + strPageSuffix);
				logger.debug("#download target:" + strFileName.getAbsolutePath());
				tDownloadFile.addItem(strUrl, strFileName.getAbsolutePath());
			}
		}
		return tDownloadFile;
	}

	public boolean BuildXMLDoc(File strXMLPath, Document Doc, String strXMLName) {
		try {
			XMLOutputter XMLOut = new XMLOutputter();

			// 输出的路径，包括文件名称
			File strOutputPath;
			// FileOperate tfileoperate = new FileOperat();
			FileOperate tfileoperate = new FileOperate();
			// tfileoperate.createDir(strXMLPath);
			tfileoperate.createDir(strXMLPath);
			strOutputPath = new File(strXMLPath, strXMLName + ".xml");
			logger.debug("#xml build path：" + strOutputPath);
			// 输出xml文件
			// XMLOut.output(Doc, new FileOutputStream("d:/user2.xml"));
			// XMLOut.setIndent(true);
			// XMLOut.setNewlines(true);
			FileOutputStream os = new FileOutputStream(strOutputPath);
			XMLOut.output(Doc, os);
			os.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EsDocMove";
			tError.functionName = "BuildXMLDoc";
			tError.errorNo = "-99";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("----------------生成XML结束 ------------");
		return true;

	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
		tES_DOC_MAINSchema.setDocID("215");
		tES_DOC_MAINSchema.setDocCode("10012007081505");
		tES_DOC_MAINSchema.setSubType("UA001");
		tES_DOC_MAINSchema.setNumPages("2");
		ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
		tES_DOC_MAINSet.add(tES_DOC_MAINSchema);

		ES_DOC_PAGESSchema tES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
		tES_DOC_PAGESSchema.setPageID("268");
		// tES_DOC_PAGESSchem.setPageCode("2001");
		tES_DOC_PAGESSchema.setPageCode("2001");
		tES_DOC_PAGESSchema.setPageName("F268");
		tES_DOC_PAGESSchema.setPageSuffix(".gif");
		tES_DOC_PAGESSchema.setHostName("test");
		tES_DOC_PAGESSchema.setPicPath("xerox/EasyScan/86/2007/08/14/");
		ES_DOC_PAGESSet tES_DOC_PAGESSet = new ES_DOC_PAGESSet();
		tES_DOC_PAGESSet.add(tES_DOC_PAGESSchema);
		ES_DOC_PAGESSchema mES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
		mES_DOC_PAGESSchema.setPageID("269");
		mES_DOC_PAGESSchema.setPageCode("2002");
		mES_DOC_PAGESSchema.setPageName("F269");
		mES_DOC_PAGESSchema.setPageSuffix(".gif");
		mES_DOC_PAGESSchema.setHostName("test");
		mES_DOC_PAGESSchema.setPicPath("xerox/EasyScan/86/2007/08/14/");
		tES_DOC_PAGESSet.add(mES_DOC_PAGESSchema);

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Missionid", "113");
		tTransferData.setNameAndValue("SubMissionid", "222");
		tTransferData.setNameAndValue("ActivityId", "333");
		tTransferData.setNameAndValue("ContNo", "444");
		tTransferData.setNameAndValue("PrtSeq", "555");
		tTransferData.setNameAndValue("Managecom", "86");
		tTransferData.setNameAndValue("MakeDate", "77");
		tTransferData.setNameAndValue("MakeDate", "777");
		tTransferData.setNameAndValue("TempPath", "D:/Temp/");

		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();

		tLCIssuePolSchema.setIssueType("1212");

		tLCIssuePolSchema.setIssueCont("2121");
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet.add(tLCIssuePolSchema);
		tTransferData.setNameAndValue("LCIssuePolSet", tLCIssuePolSet);
		VData tVData = new VData();
		tVData.add(0, tES_DOC_MAINSet);
		tVData.add(1, tES_DOC_PAGESSet);
		tVData.add(2, tTransferData);
		tVData.add(3, "com.sinosoft.lis.easyscan.outsourcing.EsDocMove");
		tVData.add(4, "3");
		DocTBImageMoveRelationService tDocTBImageMoveRelationService = new DocTBImageMoveRelationService();
		String toperator = "";
		tDocTBImageMoveRelationService.submitData(tVData, toperator);
	}

	private void jbInit() throws Exception {
	}
}
