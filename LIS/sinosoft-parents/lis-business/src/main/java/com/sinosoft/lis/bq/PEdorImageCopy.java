package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_DOC_RELATIONDB;
import com.sinosoft.lis.easyscan.RelationConfig;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_DOC_RELATIONSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

public class PEdorImageCopy implements BusinessService{
private static Logger logger = Logger.getLogger(PEdorImageCopy.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;
	/** 传入的保全处理号 */
	private String mEdorAcceptNo;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	String mManageCom = "";
	String mOperator = "";
	private MMap mMap = new MMap();

	public PEdorImageCopy() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!mOperate.equals("COPY")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorImageCopy";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}

		// 准备需要打印的数据
		if (!dealData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mTransferData == null) {
			CError.buildErr(this, "数据传输不完全！ ");
			return false;
		}

		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		if (mEdorAcceptNo == null || "".equals(mEdorAcceptNo)) {
			CError.buildErr(this, "错误的保全受理号！ " + mEdorAcceptNo);
			return false;
		}

		mOperator = (String) mTransferData.getValueByName("Operator");
		if (mOperator == null || mOperator.trim().equals("")) {
			mOperator = mGlobalInput.Operator;
		}

		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mManageCom = mGlobalInput.ManageCom;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}

	public boolean dealData() {
		logger.debug("====PEdorImageCopy start====");
		String strSql;

		String strEdorAcceptNo;

		logger.debug("通过保单号查询es_doc_main数据");

		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		strSql = " select * from es_doc_main " + " where doccode = '?mEdorAcceptNo?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strSql);
		sbv1.put("mEdorAcceptNo", mEdorAcceptNo);
		ES_DOC_MAINSet tES_DOC_MAINSet = tES_DOC_MAINDB.executeQuery(sbv1);

		if (tES_DOC_MAINSet == null || tES_DOC_MAINSet.size() < 1) {
			CError.buildErr(this, "通过保单号获取数据失败!");
			return false;
		}
		ES_DOC_MAINSchema tES_DOC_MAINSchema = tES_DOC_MAINSet.get(1);

		String strLimit = PubFun.getNoLimit(mManageCom);
		strEdorAcceptNo = PubFun1.CreateMaxNo("EdorAcceptNo", strLimit);
		if (StrTool.compareString(strEdorAcceptNo, "")) {
			CError.buildErr(this, "生成保全受理号错误!");
			return false;
		}

		double oldDocID = tES_DOC_MAINSchema.getDocID();
		String tDocID = getMaxNo("DocID");
		tES_DOC_MAINSchema.setDocCode(strEdorAcceptNo);
		tES_DOC_MAINSchema.setDocID(tDocID);
		tES_DOC_MAINSchema.setManageCom(mManageCom);
		tES_DOC_MAINSchema.setScanOperator(mOperator);
		mMap.put(tES_DOC_MAINSchema.getSchema(), "INSERT");

		logger.debug("通过DocID查询es_doc_pages数据");
		ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		strSql = "select * from es_doc_pages where docid=?oldDocID? and (pagetype is null or pagetype <> '7')";
		sbv1=new SQLwithBindVariables();
		sbv1.sql(strSql);
		sbv1.put("oldDocID", oldDocID);
		ES_DOC_PAGESSet tES_DOC_PAGESSet = tES_DOC_PAGESDB.executeQuery(sbv1);
		if (tES_DOC_PAGESSet == null || tES_DOC_PAGESSet.size() < 1) {
			CError.buildErr(this, "获取DOC_PAGES失败!");
			return false;
		}
		ES_DOC_PAGESSchema tES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
		int i;
		String tPageID;
		for (i = 0; i < tES_DOC_PAGESSet.size(); i++) {
			tES_DOC_PAGESSchema = tES_DOC_PAGESSet.get(i + 1);
			tES_DOC_PAGESSchema.setDocID(tDocID);
			tPageID = getMaxNo("PageID");
			tES_DOC_PAGESSchema.setPageID(tPageID);
			tES_DOC_PAGESSchema.setManageCom(mManageCom);
			tES_DOC_PAGESSchema.setOperator(mOperator);
			mMap.put(tES_DOC_PAGESSchema, "INSERT");
		}

		logger.debug("通过DocID查询es_doc_relation数据");
		ES_DOC_RELATIONDB tES_DOC_RELATIONDB = new ES_DOC_RELATIONDB();
		strSql = "select * from es_doc_relation where docid=?oldDocID?";
		sbv1=new SQLwithBindVariables();
		sbv1.sql(strSql);
		sbv1.put("oldDocID", oldDocID);
		ES_DOC_RELATIONSet tES_DOC_RELATIONSet = tES_DOC_RELATIONDB
				.executeQuery(sbv1);
		if (tES_DOC_RELATIONSet == null || tES_DOC_RELATIONSet.size() < 1) {
			CError.buildErr(this, "获取DOC_RELATION失败!");
			return false;
		}
		ES_DOC_RELATIONSchema tES_DOC_RELATIONSchema = tES_DOC_RELATIONSet
				.get(1);
		tES_DOC_RELATIONSchema.setDocID(tDocID);
		tES_DOC_RELATIONSchema.setBussNo(strEdorAcceptNo);
		// tES_DOC_RELATIONSchema.setDocCode("");

		mMap.put(tES_DOC_RELATIONSchema, "INSERT");

		logger.debug("生成新节点");
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", tES_DOC_MAINSchema
				.getDocCode());
		tTransferData.setNameAndValue("DocID", String
				.valueOf(tES_DOC_MAINSchema.getDocID()));
		tTransferData.setNameAndValue("InputDate", tES_DOC_MAINSchema
				.getMakeDate());
		tTransferData.setNameAndValue("ScanOperator", tES_DOC_MAINSchema
				.getScanOperator());
		tTransferData.setNameAndValue("ManageCom", tES_DOC_MAINSchema
				.getManageCom());
		tTransferData.setNameAndValue("SubType", tES_DOC_MAINSchema
				.getSubType());
		// 必须先经过转化
		RelationConfig nRelationConfig = RelationConfig.getInstance();
		String sSubType = nRelationConfig.getBackRelation(tES_DOC_MAINSchema
				.getSubType());
		if (sSubType == null || sSubType.length() != 6) {
			CError.buildErr(this, "传入数据出错!");
			return false;
		}

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = mOperator;
		tGlobalInput.ComCode = tES_DOC_MAINSchema.getManageCom();
		tGlobalInput.ManageCom = mManageCom;

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		MMap tMap;
		try {
			ActivityOperator tActivityOpertor = new ActivityOperator();
			if (tActivityOpertor.CreateStartMission("0000000000", "0000000001",
					tVData)) {

				VData aVData = new VData();
				aVData = tActivityOpertor.getResult();
				tMap = (MMap) aVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					CError.buildErr(this, "接受保全工作流节点信息失败!");
					return false;
				} else {
				}
			} else {
				CError.buildErr(this, "保全扫描申请录入工作流节点生成失败!");
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "保全扫描申请录入工作流节点生成失败!");
			return false;
		}
		mMap.add(tMap);
		strSql = " update lwmission set defaultoperator = '?mOperator?' "
				+ " where missionprop1 = '?strEdorAcceptNo?' ";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(strSql);
		sbv2.put("mOperator", mOperator);
		sbv2.put("strEdorAcceptNo", strEdorAcceptNo);
		mMap.put(sbv2, "UPDATE");
		mResult.add(mMap);

		logger.debug("处理数据");
		PubSubmit pubsubmit = new PubSubmit();
		/** 准备提交数据* */
		if (pubsubmit.submitData(this.getResult(), "")) {
			logger.debug("Image Copy OK");
			return true;
		} else {
			logger.debug("Image Copy Failed,Try Again!");
			logger.debug(this.getErrors().getErrorCount());
			return false;
		}

	}

	// 生成流水号，包含错误处理
	private String getMaxNo(String cNoType) {
		String strNo = PubFun1.CreateMaxNo(cNoType, 1);

		if (strNo.equals("") || strNo.equals("0")) {
			// @@错误处理
			CError.buildErr(this, "生成流水号失败！ ");
			strNo = "";
		}

		return strNo;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("start test PEdorImageCopy");
		CErrors tError = new CErrors();

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", "6120060403000035");
		tTransferData.setNameAndValue("ManageCom", "86100");
		tTransferData.setNameAndValue("Operator", "001");

		try {
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.addElement(tTransferData);

			PEdorImageCopy tPEdorImageCopy = new PEdorImageCopy();
			logger.debug(tPEdorImageCopy.submitData(tVData, "COPY"));

			logger.debug("test PEdorImageCopy ended successful");
		} catch (Exception ex) {
			logger.debug("复制失败，原因是:" + ex.toString());
		}

	}
}
