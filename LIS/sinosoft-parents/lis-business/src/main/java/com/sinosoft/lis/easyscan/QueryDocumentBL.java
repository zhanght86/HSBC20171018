package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
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
public class QueryDocumentBL {
private static Logger logger = Logger.getLogger(QueryDocumentBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private GlobalInput tGI = new GlobalInput();
	private ES_DOC_MAINSet inputES_DOC_MAINSet;
	private ES_DOC_PAGESSet tES_DOC_PAGESSet;
	private ES_DOC_MAINSchema tES_DOC_MAINSchema;
	private String[] strPage_URL;
	private String mstrClientURL;

	public QueryDocumentBL() {
	}

	public static void main(String[] args) {
		QueryDocumentBL queryDocumentBL1 = new QueryDocumentBL();
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

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData(String cOperate) {
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

		return true;
	}

	// 入参处理
	private boolean getInputData() {
		boolean tReturn = true;

		logger.debug("QueryDocumentBL: Start get Input Data ...");

		// 获取入参
		inputES_DOC_MAINSet = (ES_DOC_MAINSet) mInputData.get(0);
		tES_DOC_PAGESSet = (ES_DOC_PAGESSet) mInputData.get(1);

		// strPage_URL = (String[])mInputData.get(2);
		mstrClientURL = (String) mInputData.get(4);

		tES_DOC_MAINSchema = inputES_DOC_MAINSet.get(1);

		return tReturn;
	}

	// 数据校验
	private boolean checkInputData() {
		return true;
	}

	private boolean getReturnData() {
		String strDocID = "";
		logger.debug("QueryDocumentBL: start get Return Data  ...");

		try {
			// 根据ES_DOC_MAIN的DOC_ID查询符合条件的ES_DOC_PAGES的数据
			ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
			logger.debug("ant id" + tES_DOC_MAINSchema.getDocID());
			tES_DOC_PAGESDB.setDocID((int) tES_DOC_MAINSchema.getDocID());

			ES_DOC_PAGESSet outputES_DOC_PAGESSet = tES_DOC_PAGESDB.query();

			if (outputES_DOC_PAGESSet.size() == 0) {
				if (tES_DOC_PAGESDB.mErrors.needDealError()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "QueryDocumentBL";
					tError.functionName = "getReturnData";
					tError.errorNo = "-1";
					tError.errorMessage = "查询数据库出现错误:"
							+ tES_DOC_PAGESDB.mErrors.getFirstError();
					this.mErrors.addOneError(tError);

					return false;
				}
			}

			// 查询结果排序，按照Page_Code
			tES_DOC_PAGESSet.clear();

			for (int j = 1; j <= outputES_DOC_PAGESSet.size(); j++) {
				for (int iCount = 1; iCount <= outputES_DOC_PAGESSet.size(); iCount++) {
					if (outputES_DOC_PAGESSet.get(iCount).getPageCode() == j) {
						tES_DOC_PAGESSet.add(outputES_DOC_PAGESSet.get(iCount));
					}
				}
			}

			// 处理查询结果的PageURL
			strPage_URL = new String[tES_DOC_PAGESSet.size()];

			for (int i = 1; i <= tES_DOC_PAGESSet.size(); i++) {
				// 查询Es_Server_Info数据
				ES_SERVER_INFODB tES_SERVER_INFODB = new ES_SERVER_INFODB();
				tES_SERVER_INFODB.setHostName(tES_DOC_PAGESSet.get(i)
						.getHostName());

				ES_SERVER_INFOSet tES_SERVER_INFOSet = tES_SERVER_INFODB
						.query();

				if (tES_SERVER_INFOSet.size() == 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "QueryDocumentBL";
					tError.functionName = "getReturnData";
					tError.errorNo = "-12";
					tError.errorMessage = "没有查询到保存单证页"
							+ tES_DOC_PAGESSet.get(i).getPageName() + "的服务器";
					this.mErrors.addOneError(tError);

					return false;
				}

				// 设置PageURL
				EasyScanConfig tConfig = EasyScanConfig.getInstance();
				StringBuffer ImageUrl = new StringBuffer();

				strPage_URL[i - 1] = "http://"
						+ tES_SERVER_INFOSet.get(1).getServerPort() + "/";
				ImageUrl = ImageUrl.append(strPage_URL[i - 1]);

				logger.debug("before isForward mstrClientURL:"
						+ mstrClientURL);
				logger.debug("before isForward ImageUrl:" + ImageUrl);
				tConfig.isForward(mstrClientURL, ImageUrl);
				logger.debug("after isForward mstrClientURL:"
						+ mstrClientURL);
				logger.debug("after isForward ImageUrl:" + ImageUrl);
				strPage_URL[i - 1] = ImageUrl
						+ tES_DOC_PAGESSet.get(i).getPicPath()
						+ tES_DOC_PAGESSet.get(i).getPageName()
						+ tES_DOC_PAGESSet.get(i).getPageSuffix();
				// + EasyScanConfig.getInstance().fileSuffix;
			}

			// 设置返回值
			mInputData.setElementAt(tES_DOC_PAGESSet, 1);
			mInputData.setElementAt(strPage_URL, 2);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug("Exception in QueryDocumentBL->getReturnData");
			logger.debug("Exception:" + ex.toString());

			// ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "QueryDocumentBL";
			tError.functionName = "getReturnData";
			tError.errorNo = "-99";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}
}
