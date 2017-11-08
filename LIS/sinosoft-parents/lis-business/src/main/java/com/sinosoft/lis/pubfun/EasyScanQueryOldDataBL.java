/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_MAIN_OLDDB;
import com.sinosoft.lis.db.ES_DOC_PAGES_OLDDB;
import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.easyscan.EasyScanConfig;
import com.sinosoft.lis.schema.ES_DOC_MAIN_OLDSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGES_OLDSchema;
import com.sinosoft.lis.schema.ES_SERVER_INFOSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAIN_OLDSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGES_OLDSet;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 扫描件处理类
 * </p>
 * <p>
 * Description: BL层业务处理逻辑类
 * </p>

 * 涉及到的表为es_doc_main_old/es_doc_pages_old/es_doc_relation_old
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: Niuzj
 * @version 1.0
 * @date 2006-09-26
 */

public class EasyScanQueryOldDataBL {
private static Logger logger = Logger.getLogger(EasyScanQueryOldDataBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private String DocID = "";
	private String BussNo = "";
	private String BussNoType = "";
	private String BussType = "";
	private String SubType = "";
	private String BussNo2 = "";
	private String BussNoType2 = "";
	private String mSQL = ""; // 查询语句
	private String mClientUrl = ""; // 浏览器访问的URL

	/** 扫描件表 */
	private ES_DOC_MAIN_OLDSet mES_DOC_MAIN_OLDSet = new ES_DOC_MAIN_OLDSet();
	private ES_DOC_MAIN_OLDSchema mES_DOC_MAIN_OLDSchema = new ES_DOC_MAIN_OLDSchema();

	private ES_DOC_PAGES_OLDSet mES_DOC_PAGES_OLDSet = new ES_DOC_PAGES_OLDSet();
	private ES_DOC_PAGES_OLDSchema mES_DOC_PAGES_OLDSchema = new ES_DOC_PAGES_OLDSchema();

	private ES_SERVER_INFOSet mES_SERVER_INFOSet = new ES_SERVER_INFOSet();
	private ES_SERVER_INFOSchema mES_SERVER_INFOSchema = new ES_SERVER_INFOSchema();

	public EasyScanQueryOldDataBL() {
	}

	/**
	 * 数据处理
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 将外部传入的数据分解到本类的属性中，准备处理
		if (!this.getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 查询数据,查询的分支可以根据业务要求放到不同的调用级别中

		if (!this.queryData()) {
			return false;
		}
		logger.debug("---End queryData---");

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		if (mOperate.equals("QUERY||9999")) {
			DocID = (String) mInputData.getObject(0);
			mClientUrl = (String) mInputData.getObject(1);
			if (DocID == null || DocID.trim().equals("")) {
				// @@错误处理
				this.mErrors.addOneError(throwErr("EasyScanQueryOldDataBL",
						"getInputData", "没有传入DocID单证号码!"));
				return false;
			}
		} else {
			// @@错误处理
			this.mErrors.addOneError(throwErr("EasyScanQueryOldDataBL",
					"getInputData", "传入错误的DocID单证号码!"));
			return false;
		}

		if (mClientUrl == null) {
			mClientUrl = "ClientURL is null ";
		}

		return true;
	}

	/**
	 * 主要信息查询
	 * 
	 * @return boolean
	 */
	private boolean queryData() {
		ES_DOC_MAIN_OLDSet tES_DOC_MAIN_OLDSet = new ES_DOC_MAIN_OLDSet();
		ES_DOC_MAIN_OLDSchema tES_DOC_MAIN_OLDSchema = new ES_DOC_MAIN_OLDSchema();
		VData tVDataResult = new VData();
		int i, j;

		StringBuffer sb = new StringBuffer();
		// ES_DOC_MAIN_OLD
		// Dealing*******************************************************
		// 根据印刷号获取DOC_ID、NUM_PAGES和DOC_FLAGE，要校验DOC_FLAGE是否为1，不为1则进行扫描文件出错处理

		if (mOperate.equals("QUERY||9999")) {
			sb.append("select a.* from ES_DOC_MAIN_OLD a where ");
			if (DocID != null && !DocID.equals("")) {
				sb.append("a.DocID = " + DocID);
			}
		} else {
			// @@错误处理
			this.mErrors.addOneError(throwErr("EasyScanQueryOldDataBL",
					"queryData", "传入错误的DocID单证号码!"));
			return false;
		}

		mSQL = sb.toString();
		logger.debug("ANT:" + mSQL);
		ES_DOC_MAIN_OLDDB tES_DOC_MAIN_OLDDB = mES_DOC_MAIN_OLDSchema.getDB();
		mES_DOC_MAIN_OLDSet = tES_DOC_MAIN_OLDDB.executeQuery(mSQL);

		if (tES_DOC_MAIN_OLDDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tES_DOC_MAIN_OLDDB.mErrors);
			this.mErrors.addOneError(throwErr("EasyScanQueryOldDataBL",
					"queryData", "ES_DOC_MAIN_OLD查询失败!"));
			mES_DOC_MAIN_OLDSet.clear();
			return false;
		}

		if (mES_DOC_MAIN_OLDSet.size() == 0) {
			// @@错误处理
			this.mErrors.addOneError(throwErr("EasyScanQueryOldDataBL",
					"queryData", "未找到ES_DOC_MAIN_OLD相关数据!"));
			mES_DOC_MAIN_OLDSet.clear();
			return false;
		}

		// 当一个印刷号对应多个记录的时候，没有该业务情况
		if (mES_DOC_MAIN_OLDSet.size() > 1) {
			logger.debug("该DocID对应的单证号记录多于1个，错误");
			this.mErrors.addOneError(throwErr("EasyScanQueryOldDataBL",
					"queryData", "该DocID记录多于1个，错误!"));
			mES_DOC_MAIN_OLDSet.clear();
			return false;
		}

		// ES_SERVER_INFO
		// Dealing****************************************************
		mSQL = "select * from ES_SERVER_INFO";
		ES_SERVER_INFODB tES_SERVER_INFODB = mES_SERVER_INFOSchema.getDB();
		mES_SERVER_INFOSet = tES_SERVER_INFODB.executeQuery(mSQL);

		if (tES_SERVER_INFODB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tES_SERVER_INFODB.mErrors);
			this.mErrors.addOneError(throwErr("EasyScanQueryOldDataBL",
					"queryData", "ES_SERVER_INFO查询失败!"));
			mES_SERVER_INFOSet.clear();
			return false;
		}

		if (mES_SERVER_INFOSet.size() == 0) {
			// @@错误处理
			this.mErrors.addOneError(throwErr("EasyScanQueryOldDataBL",
					"queryData", "未找到ES_SERVER_INFO相关数据!"));
			mES_SERVER_INFOSet.clear();
			return false;
		}
		// mResult.add(mES_SERVER_INFOSet.encode());
		// 取出所有服务器的信息，每个服务器取服务器名、IP和WEB根路径

		String[][] arrServerInfo = new String[mES_SERVER_INFOSet.size()][3];
		for (i = 1; i <= mES_SERVER_INFOSet.size(); i++) {
			ES_SERVER_INFOSchema tES_SERVER_INFOSchema = mES_SERVER_INFOSet
					.get(i);
			arrServerInfo[i - 1][0] = tES_SERVER_INFOSchema.getHostName();
			arrServerInfo[i - 1][1] = tES_SERVER_INFOSchema.getServerPort();
			arrServerInfo[i - 1][2] = tES_SERVER_INFOSchema.getPicPath();
		}

		// ES_DOC_PAGES_OLD
		// Dealing******************************************************
		// 获取PAGE_NAME、PAGE_PATH和SERVER_HOST，要校验PAGE_FLAGE是否为1，不为1则进行扫描文件出错处理（PAGE_FLAGE放到前台进行校验了）
		mResult.clear();
		VData Url = new VData();
		VData Pages = new VData();
		for (int k = 0; k < mES_DOC_MAIN_OLDSet.size(); k++) {
			mES_DOC_MAIN_OLDSchema = mES_DOC_MAIN_OLDSet.get(k + 1);
			mSQL = "select * from ES_DOC_PAGES_OLD where DOCID = "
					+ "?a1?" + " order by PAGECODE";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(mSQL);
			sqlbv.put("a1", mES_DOC_MAIN_OLDSchema.getDocID());
			logger.debug(mSQL);
			ES_DOC_PAGES_OLDDB tES_DOC_PAGES_OLDDB = mES_DOC_PAGES_OLDSchema
					.getDB();
			mES_DOC_PAGES_OLDSet = tES_DOC_PAGES_OLDDB.executeQuery(sqlbv);

			if (tES_DOC_PAGES_OLDDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tES_DOC_PAGES_OLDDB.mErrors);
				this.mErrors.addOneError(throwErr("EasyScanQueryOldDataBL",
						"queryData", "ES_DOC_PAGES_OLD查询失败!"));
				mES_DOC_MAIN_OLDSet.clear();
				return false;
			}

			if (mES_DOC_PAGES_OLDSet.size() == 0) {
				// @@错误处理
				this.mErrors.addOneError(throwErr("EasyScanQueryOldDataBL",
						"queryData", "未找到ES_DOC_PAGES_OLD相关数据!"));
				mES_DOC_PAGES_OLDSet.clear();
				return false;
			}

			logger.debug(mES_DOC_PAGES_OLDSet.encode());

			// 拼URL，使VECTOR的每一个元素是一个URL
			logger.debug("--mClientURL=" + mClientUrl + "--");
			EasyScanConfig tConfig = EasyScanConfig.getInstance();
			StringBuffer serverUrlBuf = new StringBuffer();
			for (i = 1; i <= mES_DOC_PAGES_OLDSet.size(); i++) {
				ES_DOC_PAGES_OLDSchema tES_DOC_PAGES_OLDSchema = mES_DOC_PAGES_OLDSet
						.get(i);
				for (j = 0; j < arrServerInfo.length; j++) {
					if (arrServerInfo[j][0].equals(tES_DOC_PAGES_OLDSchema
							.getHostName())) {
						String strUrl = "http://" + arrServerInfo[j][1] + "/";
						serverUrlBuf.delete(0, serverUrlBuf.length());
						serverUrlBuf.append(strUrl);
						tConfig.isForward(mClientUrl, serverUrlBuf);

						// Edited by wellhi 2005.09.18
						// 如果数据库中保存的文件后缀是TIF,浏览器中就使用的GIF格式的图片;
						// 如果数据库中保存的文件后缀是JPG,浏览器中就使用的JPG格式的图片;
						String strSuffix = tES_DOC_PAGES_OLDSchema
								.getPageSuffix();
						if (strSuffix == null || strSuffix.equals("")) {
							strSuffix = ".gif";
						} else {
							if (strSuffix.equalsIgnoreCase(".TIF")) {
								strSuffix = ".gif";
							}
						}
						Url.add(serverUrlBuf
								// + arrServerInfo[j][2]
								+ tES_DOC_PAGES_OLDSchema.getPicPath()
								+ tES_DOC_PAGES_OLDSchema.getPageName()
								+ strSuffix);
						// + ".gif");
						Pages.add(Integer.toString(mES_DOC_PAGES_OLDSet.size())
								+ "_" + Integer.toString(i));
						break;
					}
				}
			}
			mResult.add(Url);
			mResult.add(Pages);
		}
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 简化错误处理类付值
	 * 
	 * @param cModuleName
	 *            String
	 * @param cFunctionName
	 *            String
	 * @param cErrorMessage
	 *            String
	 * @return CError
	 */
	private static CError throwErr(String cModuleName, String cFunctionName,
			String cErrorMessage) {
		CError tError = new CError();
		tError.moduleName = cModuleName;
		tError.functionName = cFunctionName;
		tError.errorMessage = cErrorMessage;
		return tError;
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		// EasyScanQueryBL easyScanQueryBL = new EasyScanQueryBL();
		// VData mVData = new VData();
		// mVData.add("1921212121");
		// mVData.add("11");
		// mVData.add("TB");
		// mVData.add("TB01");
		// easyScanQueryBL.submitData(mVData, "");
	}
}
