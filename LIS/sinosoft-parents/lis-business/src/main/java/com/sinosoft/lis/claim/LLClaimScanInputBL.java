/*
 * <p>ClassName: LLClaimScanInputBL </p>
 * <p>Description: LLClaimScanInputBL类文件 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证补扫
 * @CreateDate：2005-08-28
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_RELATIONDB;
import com.sinosoft.lis.db.Es_IssueDocDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.Es_IssueDocSchema;
import com.sinosoft.lis.vschema.ES_DOC_RELATIONSet;
import com.sinosoft.lis.vschema.Es_IssueDocSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class LLClaimScanInputBL {
private static Logger logger = Logger.getLogger(LLClaimScanInputBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private Es_IssueDocSchema mEs_IssueDocSchema = new Es_IssueDocSchema();
	private Es_IssueDocSet mEs_IssueDocSet = new Es_IssueDocSet();

	public LLClaimScanInputBL() {
	}

	public static void main(String[] args) {
		Es_IssueDocSchema tEs_IssueDocSchema = new Es_IssueDocSchema();
		tEs_IssueDocSchema.setBussNo("20061229000007");
		tEs_IssueDocSchema.setSubType("UA001");
		tEs_IssueDocSchema.setIssueDesc("asd");
		tEs_IssueDocSchema.setBussNoType("11");
		tEs_IssueDocSchema.setBussType("TB");
		tEs_IssueDocSchema.setStatus("0");

		GlobalInput tG = new GlobalInput();
		// tG.Operator = "Admin";
		// tG.ComCode = "001";
		VData tVData = new VData();

		tVData.addElement(tEs_IssueDocSchema);
		tVData.add(tG);
		LLClaimScanInputBL tLLClaimScanInputBL = new LLClaimScanInputBL();
		tLLClaimScanInputBL.submitData(tVData, "INSERT");

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimScanInputBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LLClaimScanInputBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		if (this.mOperate.equals("QUERY||MAIN")) {
			this.submitquery();
		} else {
			logger.debug("Start tLLClaimScanInputBL Submit...");
			LLClaimScanInputBLS tLLClaimScanInputBLS = new LLClaimScanInputBLS();
			tLLClaimScanInputBLS.submitData(mInputData, cOperate);
			logger.debug("End tLLClaimScanInputBL Submit...");
			// 如果有需要处理的错误，则返回
			if (tLLClaimScanInputBLS.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLLClaimScanInputBLS.mErrors);
				CError tError = new CError();
				tError.moduleName = "tLLClaimScanInputBL";
				tError.functionName = "submitDat";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		boolean tReturn = true;
		String currentDate = PubFun.getCurrentDate();
		String currentTime = PubFun.getCurrentTime();
		if (this.mOperate.equals("INSERT")) {// 确定录入的赔案号确实存在
			String tBusstype = this.mEs_IssueDocSchema.getBussType().trim();
			String tCaseNo = this.mEs_IssueDocSchema.getBussNo().trim();
			String tSubtype = this.mEs_IssueDocSchema.getSubType().trim();
			String tBussnotype;

			ES_DOC_RELATIONDB tES_DOC_RELATIONDB = new ES_DOC_RELATIONDB();

			String sql = "select * from ES_DOC_RELATION where 1=1"
					+ " and Busstype ='" + "?Busstype?" + "'" + " and Bussno = '"
					+ "?Bussno?" + "'" + " and Subtype = '" + "?Subtype?" + "'";
			logger.debug("Start query LLCase:" + sql);
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("Busstype", tBusstype);
			sqlbv.put("Bussno", tCaseNo);
			sqlbv.put("Subtype", tSubtype);
			ES_DOC_RELATIONSet tLLCaseSet = tES_DOC_RELATIONDB
					.executeQuery(sqlbv);

			if (tLLCaseSet == null || tLLCaseSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLClaimScanInputBL";
				tError.functionName = "dealData";
				tError.errorMessage = "此单证号不存在!";
				this.mErrors.addOneError(tError);
				tReturn = false;
			} else {
				String ttReturn = PubFun1.CreateMaxNo("ISSUEDOCID", 10); // 取最大号
				logger.debug("@@@@@@@ISSUEDOCID=" + ttReturn);
				tBussnotype = tLLCaseSet.get(1).getBussNoType();
				this.mEs_IssueDocSchema.setIssueDocID(ttReturn);
				this.mEs_IssueDocSchema.setBussNoType(tBussnotype);
				this.mEs_IssueDocSchema.setMakeDate(currentDate);
				this.mEs_IssueDocSchema.setMakeTime(currentTime);
				this.mEs_IssueDocSchema.setModifyDate(currentDate);
				this.mEs_IssueDocSchema.setModifyTime(currentTime);
				this.mEs_IssueDocSchema
						.setPromptOperator(this.mGlobalInput.Operator);
			}

		}
		/*
		 * else if (this.mOperate.equals("UPDATE||MAIN")) {
		 * 
		 * LATrainDB tLATrainDB = new LATrainDB();
		 * tLATrainDB.setAgentCode(mLATrainSchema.getAgentCode());
		 * tLATrainDB.setIdx(mLATrainSchema.getIdx()); if
		 * (!tLATrainDB.getInfo()) { // @@错误处理 CError tError = new CError();
		 * tError.moduleName = "LATrainBL"; tError.functionName = "dealData";
		 * tError.errorMessage = "原培训信息查询失败!"; this.mErrors.addOneError(tError);
		 * return false; }
		 * this.mLATrainSchema.setMakeDate(tLATrainDB.getMakeDate());
		 * this.mLATrainSchema.setMakeTime(tLATrainDB.getMakeTime());
		 * this.mLATrainSchema.setModifyDate(currentDate);
		 * this.mLATrainSchema.setModifyTime(currentTime);
		 * this.mLATrainSchema.setOperator(this.mGlobalInput.Operator); }
		 */
		// tReturn=true;
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.mEs_IssueDocSchema.setSchema((Es_IssueDocSchema) cInputData
				.getObjectByObjectName("Es_IssueDocSchema", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		logger.debug("Start LLClaimScanInputBLQuery Submit...");
		Es_IssueDocDB tEs_IssueDocDB = new Es_IssueDocDB();
		tEs_IssueDocDB.setSchema(this.mEs_IssueDocSchema);
		this.mEs_IssueDocSet = tEs_IssueDocDB.query();
		// //
		this.mResult.add(this.mEs_IssueDocSet);
		logger.debug("End LLClaimScanInputBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tEs_IssueDocDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tEs_IssueDocDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimScanInputBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData = new VData();
			this.mInputData.add(this.mGlobalInput);
			this.mInputData.add(this.mEs_IssueDocSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimScanInputBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

}
