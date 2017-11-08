package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_DOC_RELATIONDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_DOC_RELATIONSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class DELPictureBL {
private static Logger logger = Logger.getLogger(DELPictureBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();

	/** 需要传到关系的数据* */

	// 统一更新日期，时间
	private String mContNo;
	private String mPrtNo;

	/** 业务处理相关变量 */
	private TransferData mTransferData = new TransferData();

	public DELPictureBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		try {
			mInputData = (VData) cInputData.clone();
			this.mOperate = cOperate;
			logger.debug("now in DELPictureBL submit");
			// 将外部传入的数据分解到本类的属性中，准备处理
			// 判断是初始化还是处理体检医院信息
			if (this.getInputData() == false) {
				return false;
			}
			logger.debug("---getInputData---");
			if (this.checkData() == false) {
				return false;
			}
			logger.debug("---checkData---");

			// 根据业务逻辑对数据进行处理
			if (this.dealData() == false) {
				return false;
			}

			// 装配处理好的数据，准备给后台进行保存
			this.prepareOutputData();
			logger.debug("---prepareOutputData---");

			// 数据提交、保存
			PubSubmit tPubSubmit = new PubSubmit();
			logger.debug("Start DELPictureBL Submit...");
			if (!tPubSubmit.submitData(mInputData, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		}

		logger.debug("---commitData---");

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			// 客户表
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			mContNo = (String) mTransferData.getValueByName("ContNo");
			return true;
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			return false;

		}

	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		if (mContNo == null || "".equals(mContNo)) {
			CError.buildErr(this, "未录入单证号码！");
			return false;
		} else {
			LCContDB tLCContDB = new LCContDB();
			LCContSet tLCContSet = new LCContSet();
			tLCContDB.setContNo(mContNo);
			tLCContSet = tLCContDB.query();
			if (tLCContSet.size() != 1) {
				CError.buildErr(this, "查询合同信息失败！");
				return false;
			} else {
				mPrtNo = tLCContSet.get(1).getPrtNo();
			}
		}
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
		tES_DOC_MAINDB.setDocCode(mPrtNo);
		tES_DOC_MAINDB.setSubType("UR301");
		tES_DOC_MAINSet = tES_DOC_MAINDB.query();
		if (tES_DOC_MAINSet.size() != 1) {
			CError.buildErr(this, "查询扫描件信息失败！");
			return false;
		}
		// String tSql =
		// "select to_char(docid) from es_doc_main where doccode='"
		// + mPrtNo + "' and subtype='UR301'";
		// ExeSQL tExeSQL = new ExeSQL();
		String tDocCode = tES_DOC_MAINSet.get(1).getDocCode();
		// tDocID = tExeSQL.getOneValue(tSql);
		ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		ES_DOC_PAGESSet tES_DOC_PAGESSet = new ES_DOC_PAGESSet();
		// tES_DOC_PAGESDB.setDocID(tDocCode);
		String tSql = "select * from es_doc_pages where docid=(select docid from es_doc_main where doccode='"
				+ "?doccode?" + "' and subtype='UR301')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("doccode", tDocCode);
		tES_DOC_PAGESSet = tES_DOC_PAGESDB.executeQuery(sqlbv);
		// tES_DOC_PAGESSet = tES_DOC_PAGESDB.query();
		if (tES_DOC_PAGESSet.size() < 1) {
			CError.buildErr(this, "查询扫描件页数失败！");
			return false;
		}
		ES_DOC_RELATIONDB tES_DOC_RELATIONDB = new ES_DOC_RELATIONDB();
		ES_DOC_RELATIONSet tES_DOC_RELATIONSet = new ES_DOC_RELATIONSet();
		tES_DOC_RELATIONDB.setDocCode(tDocCode);
		tES_DOC_RELATIONDB.setSubType("UR301");
		tES_DOC_RELATIONDB.setBussNo(mPrtNo);
		tES_DOC_RELATIONSet = tES_DOC_RELATIONDB.query();
		if (tES_DOC_RELATIONSet.size() < 1) {
			CError.buildErr(this, "查询es_doc_relation表失败！");
			return false;
		}
		map.put(tES_DOC_MAINSet, "DELETE");
		map.put(tES_DOC_PAGESSet, "DELETE");
		map.put(tES_DOC_RELATIONSet, "DELETE");
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}

}
