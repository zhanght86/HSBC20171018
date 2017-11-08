package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_COM_SERVERDB;
import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_PROCESS_DEFDB;
import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_SERVER_INFOSchema;
import com.sinosoft.lis.vschema.ES_COM_SERVERSet;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_PROCESS_DEFSet;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: WEB业务系统
 * </p>
 * <p>
 * Description: 数据上载预处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Liuqiang
 * @version 1.0
 */
public class UploadPrepareBL {
private static Logger logger = Logger.getLogger(UploadPrepareBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private GlobalInput tGI = new GlobalInput();
	private boolean bNewDoc = true;
	private ES_DOC_MAINSet inputES_DOC_MAINSet;
	private ES_DOC_PAGESSet tES_DOC_PAGESSet;
	private ES_DOC_MAINSchema tES_DOC_MAINSchema;
	private String[] strPage_URL;
	private String mstrClientURL;
	private boolean blnPageNumChanged = false;

	public UploadPrepareBL() {
	}

	public static void main(String[] args) {
		UploadPrepareBL uploadPrepareBL1 = new UploadPrepareBL();
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		// 进行业务处理
		if (!dealData(cOperate)) {
			tReturn = false;
		}
		mResult.clear();
		mResult = mInputData;
		mInputData = null;
		return tReturn;
	}

	public VData getResult() {
		return mResult;
	}

	// 入参处理
	private boolean getInputData() {
		boolean tReturn = true;
		logger.debug("UploadPrepareBL: Start get Input Data ...");
		// 获取入参
		inputES_DOC_MAINSet = (ES_DOC_MAINSet) mInputData.get(0);
		tES_DOC_PAGESSet = (ES_DOC_PAGESSet) mInputData.get(1);
		strPage_URL = (String[]) mInputData.get(2);
		mstrClientURL = (String) mInputData.get(4); // 盗用MESSAGE作为ClientURL的输入
		tES_DOC_MAINSchema = inputES_DOC_MAINSet.get(1);
		// 如果DOC_FLAGE 不等于 "0" 则表示不是新单
		if (!tES_DOC_MAINSchema.getDocFlag().trim().equals("0")) {
			bNewDoc = false;
		}
		return tReturn;
	}

	// 数据校验
	/*
	 * 判断逻辑 by wellhi 2005.11.02 if (ES_DOC_MAINSchema.DocFlag=="1") {
	 * 单证没有修改过,提示单证已经上载; } else if(ES_DOC_MAINSchema.DocFlag=="0") {
	 * 单证修改过,进行保存处理; if (待上载单证页数与数据库中单证页数不相等 { blnPageNumChanged = true; }
	 * 
	 * 通过两个循环,逐一比较待上载单证页的PageID与数据库中单证页的PageID是否完全相同 if(!完全相同) {
	 * blnDocChanged=true; }
	 * 
	 * if(!(blnPageNumChanged ||blnDocChanged)) { 单证没有修改过,提示单证已经上载; } }
	 */
	private boolean checkInputData() {
		try {
			logger.debug("UploadPrepareBL: Start check input data ...");
			String strDocCode = tES_DOC_MAINSchema.getDocCode();
			// 先判断单证号码是否为空
			if (strDocCode == null || strDocCode.equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "UploadPrepareBL";
				tError.functionName = "checkInputData";
				tError.errorNo = "-3500";
				tError.errorMessage = "单证号码不能为空,请通过本地查询,修改单证号码后再上载!";
				this.mErrors.addOneError(tError);
				return false;
			}

			ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
			tES_DOC_MAINDB.setDocCode(tES_DOC_MAINSchema.getDocCode());
			tES_DOC_MAINDB.setBussType(tES_DOC_MAINSchema.getBussType());
			tES_DOC_MAINDB.setSubType(tES_DOC_MAINSchema.getSubType());
			// 查询ES_DOC_MAIN数据
			ES_DOC_MAINSet tES_DOC_MAINSet = tES_DOC_MAINDB.query();
			if (tES_DOC_MAINSchema.getDocFlag().equals("0")) {
				if (ifNeedCheck() == false) {
					if (tES_DOC_MAINSet.size() > 0) {
						// 如果中心有记录表示重复上载
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "UploadPrepareBL";
						tError.functionName = "checkInputData";
						tError.errorNo = "1";
						tError.errorMessage = "单证"
								+ tES_DOC_MAINSchema.getDocCode() + "已经上载";
						this.mErrors.addOneError(tError);

						return false;
					}

				} else {
					logger.debug("该单证可以进行多次上载");
				}

			} else if (tES_DOC_MAINSchema.getDocFlag().equals("1")) {
				// 新单上载
				// 补充上载
				if (tES_DOC_MAINSet.size() == 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "UploadPrepareBL";
					tError.functionName = "checkInputData";
					tError.errorNo = "3";
					tError.errorMessage = "单证"
							+ tES_DOC_MAINSchema.getDocCode() + "不存在";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					ES_DOC_MAINSchema tES_DOC_MAINSchema2 = tES_DOC_MAINSet
							.get(1);
					// add by wellhi 2005.05.24
					/*
					 * 先循环判断单证的索引PageFlag, if(PageFlag=1)单证页没经过改变,不处理
					 * if(PageFlag=0) 单证页是新上载的,添加
					 * 再判断是否有被删除的单证页(数据库中有,而当前索引里没有的PageID的单证页),从数据库中删除索引
					 */

					if (tES_DOC_MAINSchema2.getNumPages() != (int) tES_DOC_MAINSchema
							.getNumPages()) {
						blnPageNumChanged = true;
					}
					ES_DOC_PAGESSet oldES_DOC_PAGESSet = new ES_DOC_PAGESSet();
					ES_DOC_PAGESSchema oldES_DOC_PAGESSchema;
					ES_DOC_PAGESDB oldES_DOC_PAGESDB = new ES_DOC_PAGESDB();
					oldES_DOC_PAGESDB.setDocID(tES_DOC_MAINSchema.getDocID());
					oldES_DOC_PAGESSet = oldES_DOC_PAGESDB.query();
					int intOldPageCount = oldES_DOC_PAGESSet.size();
					int intCurPageCount = tES_DOC_PAGESSet.size();
					boolean blnDocChanged = false;
					for (int i = 0; i < intOldPageCount; i++) {
						oldES_DOC_PAGESSchema = oldES_DOC_PAGESSet.get(i + 1);
						for (int j = 0; j < intCurPageCount; j++) {
							ES_DOC_PAGESSchema curES_DOC_PAGESSchema = tES_DOC_PAGESSet
									.get(j + 1);
							if (oldES_DOC_PAGESSchema.getPageID() == curES_DOC_PAGESSchema
									.getPageID()) {
								if (curES_DOC_PAGESSchema.getPageFlag().equals(
										"1")) {
									continue;
								} else {
									blnDocChanged = true;
									break;
								}
							} else {
								blnDocChanged = true;
								break;
							}
						}
					}
					// if ( (int) tES_DOC_MAINSchema2.getNumPages() ==(int)
					// tES_DOC_MAINSchema.getNumPages()) {
					if (!(blnPageNumChanged || blnDocChanged)) {
						// 如果页数相等表示已经上载
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "UploadPrepareBL";
						tError.functionName = "checkInputData";
						tError.errorNo = "2";
						tError.errorMessage = "待上载的单证页已经存在";
						this.mErrors.addOneError(tError);
						return false;
					}

				}
			} else if (tES_DOC_MAINSchema.getDocFlag().equals("2")) {

			}
		} catch (Exception ex) {
			logger.debug("Exception in UploadPrepareBL->checkInputData");
			logger.debug("Exception:" + ex.toString());

			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UploadPrepareBL";
			tError.functionName = "checkInputData";
			tError.errorNo = "-99";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		// Added by wellhi 2005.10.14
		// 单证处理定义表查询
		logger.debug("查询需要处理的服务类.......");
		String tStr = "select * from ES_PROCESS_DEF where BussType = '"
				+ "?BussType?"
				+ "'"
				+ " and SubType = '"
				+ "?SubType?"
				+ "'"
				+ " and ServiceType = '4' and validflag='0' order by ProcessCode";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStr);
		sqlbv1.put("BussType", tES_DOC_MAINSchema.getBussType());
		sqlbv1.put("SubType", tES_DOC_MAINSchema.getSubType());
		ES_PROCESS_DEFDB nES_PROCESS_DEFDB = new ES_PROCESS_DEFDB();
		ES_PROCESS_DEFSet tES_PROCESS_DEFSet = nES_PROCESS_DEFDB
				.executeQuery(sqlbv1);
		// 如果没有找到需要处理的服务类,则直接跳出
		if (tES_PROCESS_DEFSet.size() == 0) {
			return true;
		}
		try {
			// 返回需要数据库提交的数据
			for (int i = 0; i < tES_PROCESS_DEFSet.size(); i++) {
				String strClassName = tES_PROCESS_DEFSet.get(i + 1)
						.getProcessService();
				Class tClass = Class.forName(strClassName);
				logger.debug(1 + strClassName);
				EasyScanService tservice = (EasyScanService) tClass
						.newInstance();
				if (!tservice.submitData(mInputData, "")) {
					CError tError = new CError();
					tError.moduleName = "CallService";
					tError.functionName = "save";
					tError.errorNo = "-1";
					tError.errorMessage = "服务类 [" + strClassName
							+ "] 处理出错!请与系统管理员联系.";
					this.mErrors.clearErrors();
					this.mErrors.copyAllErrors(tservice.getErrors());
					return false;
				}
				mResult.add(tservice.getResult());
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "CallService";
			tError.functionName = "save";
			tError.errorNo = "-1";
			tError.errorMessage = "服务类初始化出错!请与系统管理员联系.";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean getReturnData() {
		String strDocID = "";
		int i = 0;
		logger.debug("UploadPrepareBL: start get Return Data  ...");

		try {
			// 获得影像服务器信息
			ES_COM_SERVERDB tES_COM_SERVERDB = new ES_COM_SERVERDB();
			// Edited by wellhi 2005.08.27
			// 中心单证修改时借用了ManageCom保存IssueDocID，故要先去掉
			ParameterDataConvert nConvert = new ParameterDataConvert();
			String strManageCom = nConvert.getManageCom(tES_DOC_MAINSchema
					.getManageCom());
			tES_COM_SERVERDB.setManageCom(strManageCom);
			// logger.debug(strManageCom);
			ES_COM_SERVERSet tES_COM_SERVERSet = tES_COM_SERVERDB.query();
			logger.debug(tES_COM_SERVERSet.size());
			if (tES_COM_SERVERSet.size() == 0) {
				// 管理机构没有设置对应的文件服务器
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "UploadPrepareBL";
				tError.functionName = "getReturnData";
				tError.errorNo = "-500";
				tError.errorMessage = "管理机构" + strManageCom + "没有设置对应的文件服务器";
				this.mErrors.addOneError(tError);

				return false;
			}

			// 查询Es_Server_Info的数据
			ES_SERVER_INFODB tES_SERVER_INFODB = new ES_SERVER_INFODB();
			String strHostName = tES_COM_SERVERSet.get(1).getHostName();
			tES_SERVER_INFODB.setHostName(strHostName);

			ES_SERVER_INFOSet tES_SERVER_INFOSet = tES_SERVER_INFODB.query();
			if (tES_SERVER_INFOSet == null || tES_SERVER_INFOSet.size() != 1) {
				CError tError = new CError();
				tError.moduleName = "服务器信息出错";
				tError.functionName = "getReturnData";
				tError.errorNo = "-500";
				tError.errorMessage = "管理机构所对应的服务器 [" + strHostName + "] 配置有误!";
				this.mErrors.addOneError(tError);

				return false;
			}
			ES_SERVER_INFOSchema tES_SERVER_INFOSchema = tES_SERVER_INFOSet
					.get(1);
			// 设置ES_DOC_PAGES的数据
			// 如果是新单,获取DOC_ID
			if (bNewDoc) {
				strDocID = getMaxNo("DocID");

				if (strDocID.equals("")) {
					return false;
				}

				tES_DOC_MAINSchema.setDocID(strDocID);
			} else {
				strDocID = String.valueOf((int) tES_DOC_MAINSchema.getDocID());
			}

			// 获得PAGE_ID
			for (i = 0; i < tES_DOC_PAGESSet.size(); i++) {
				if (tES_DOC_PAGESSet.get(i + 1).getPageFlag().trim()
						.equals("0")) { // changed
					String strPageID = getMaxNo("PageID");
					if (strPageID.equals("")) {
						return false;
					}

					tES_DOC_PAGESSet.get(i + 1).setPageID(strPageID);
					tES_DOC_PAGESSet.get(i + 1).setPageName("F" + strPageID);
					tES_DOC_PAGESSet.get(i + 1).setDocID(strDocID);
				}
				// 设置存储信息
				String subDir = "";
				if (EasyScanConfig.getInstance().saveMultipleDir) {
					subDir = getSubDir(strManageCom, PubFun.getCurrentDate());
				}
				tES_DOC_PAGESSet.get(i + 1).setHostName(strHostName);
				// Edited by wellhi 2005.08.13
				// 直接配置在数据库
				String strServerPort = tES_SERVER_INFOSchema.getServerPort();
				tES_DOC_PAGESSet.get(i + 1).setPicPathFTP(
						strServerPort + "/" + subDir);
				// tES_DOC_PAGESSet.get(i+1).setPicPathFTP("192.168.70.112:8080/ui/");
				// Edit By wellhi 2005.08.25
				// 先把ServerBasePath取出来暂时与PicPath放在一起用"|@@|"分隔开，到UploadImageFile.java时再分别取值
				tES_DOC_PAGESSet.get(i + 1).setPicPath(
						tES_SERVER_INFOSchema.getServerBasePath() + "||"
								+ tES_SERVER_INFOSchema.getPicPath() + "||"
								+ subDir);

				// Page_URL在上载处理中用来保存上载文件访问的URL
				EasyScanConfig tConfig = EasyScanConfig.getInstance();

				String strUrl = "http://" + strServerPort + "/";
				StringBuffer serverUrl = new StringBuffer();
				serverUrl.append(strUrl);
				logger.debug("mstrClientURL=" + mstrClientURL);
				logger.debug("serverUrl=" + serverUrl);
				tConfig.isForward(mstrClientURL, serverUrl);

				strPage_URL[i] = serverUrl.toString(); // +
														// tES_SERVER_INFOSchema.getPicPath();
				logger.debug("strPage_URL_OLD   " + strPage_URL[i]);
				logger.debug("strPage_URL_NEW   " + "http://"
						+ strServerPort + "/");
				// strPage_URL[i] = "http://" + strServerPort + "/";
			}
		} catch (Exception ex) {
			logger.debug("Exception in UploadPrepareBL->getReturnData");
			logger.debug("Exception:" + ex.toString());
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UploadPrepareBL";
			tError.functionName = "getReturnData";
			tError.errorNo = "-99";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 生成图像保存的子目录
	private String getSubDir(String manageCom, String inputDate) {
		StringBuffer bufSubDir = new StringBuffer();
		bufSubDir.append(inputDate.substring(0, 4));
		bufSubDir.append("/");
		bufSubDir.append(inputDate.substring(5, 7));
		bufSubDir.append("/");
		bufSubDir.append(inputDate.substring(8, 10));
		bufSubDir.append("/");
		bufSubDir.append(manageCom);
		bufSubDir.append("/");

		return bufSubDir.toString();
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

	// 团体有些单证扫描时是批量扫，但是doccode和subtype是一样的，不需要校验是否已经扫描上载
	private boolean ifNeedCheck() {
		Calculator tCaculator = new Calculator();
		tCaculator.setCalCode("STYPE");
		tCaculator.addBasicFactor("subtype", tES_DOC_MAINSchema.getSubType());
		String needflag = tCaculator.calculate();
		logger.debug("------校验该单证是否能够多次上载-----hp-----");
		logger.debug(tCaculator.getCalSQL());
		if (needflag == null || needflag.trim().equals("")
				|| needflag.trim().equals("0")) {
			return false;
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData(String cOperate) {
		try {
			// 获得入参
			if (!getInputData()) {
				return false;
			}

			// 数据检查
			if (!checkInputData()) {
				return false;
			}

			if (!getReturnData()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
}
