package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCStopInsuredSchema;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCStopInsuredSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 家庭单下暂停承保
 * </p>
 * <p>
 * Description: 家庭单下如果有人希望暂停承保则生成一张新保单
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class SplitFamilyBL {
private static Logger logger = Logger.getLogger(SplitFamilyBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private MMap map = new MMap();

	/** 业务处理类 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCAppntSchema mLCAppntSchema = new LCAppntSchema();
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	private LCStopInsuredSet mLCStopInsuredSet = new LCStopInsuredSet();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;

	/** 业务操作字符串 */
	private String mContNo;
	private String mInsuredNo;
	private String mNewContNo;
	private double mPrem = 0;
	private double mNewPrem = 0;
	private double mAmnt = 0;
	private double mNewAmnt = 0;
	private double mMult = 0;
	private double mNewMult = 0;

	public SplitFamilyBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");
		//
		// logger.debug("Start Submit...");
		//
		// PubSubmit tPubSubmit = new PubSubmit();
		//
		// if(!tPubSubmit.submitData(mResult,""))
		// {
		// CError tError = new CError();
		// tError.moduleName = "SplitFamilyBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据库提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// logger.debug("End Submit....");
		return true;
	}

	/**
	 * dealData
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		// 生成新的合同号
		mNewContNo = PubFun1.CreateMaxNo("ContNo", mManageCom);

		// 准备新生成保单的数据
		if (!prepareNewCont()) {
			return false;
		}

		// 准备需要修改的表
		if (!prepareUpdateTable()) {
			return false;
		}

		mResult.add(0, mNewContNo);
		mResult.add(map);

		return true;
	}

	/**
	 * prepareUpdateTable
	 * 
	 * @return boolean
	 */
	private boolean prepareUpdateTable() {
		String tInsuredStat = "";
		String tInsuredNo = "";
		String tPolNo = "";
		String tSqlBnf = "";
		String tSqlDuty = "";
		String tSqlInsureAccFee = "";
		String tSqlInsureAccClassFee = "";
		String tSqlInsureAccClass = "";
		String tSqlInsureAccTrace = "";
		String tSqlInsureAcc = "";
		String tSqlGet = "";
		String tSqlPrem = "";
		String tSqlPol = "";
		String tSqlInsured = "";
		String tSqlLCCustomerImpart = "";
		String tSqlLCCustomerImpartParams = "";
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		LCStopInsuredSchema tLCStopInsuredSchema;
		for (int i = 1; i <= mLCInsuredSet.size(); i++) {
			tInsuredStat = mLCInsuredSet.get(i).getInsuredStat();
			if (tInsuredStat == null || tInsuredStat.length() <= 0) {
			} else {
				if (tInsuredStat.equals("1")) {
					// 暂停被保人表
					tLCStopInsuredSchema = new LCStopInsuredSchema();

					tLCStopInsuredSchema.setContNo(mNewContNo);
					tLCStopInsuredSchema.setPrtNo(mLCContSchema.getPrtNo());
					tLCStopInsuredSchema.setInsuredNo(mLCInsuredSet.get(i)
							.getInsuredNo());
					tLCStopInsuredSchema.setOldContNo(mLCInsuredSet.get(i)
							.getContNo());
					tLCStopInsuredSchema.setOperator(mOperator);
					tLCStopInsuredSchema.setMakeDate(PubFun.getCurrentDate());
					tLCStopInsuredSchema.setMakeTime(PubFun.getCurrentTime());
					tLCStopInsuredSchema.setModifyDate(PubFun.getCurrentDate());
					tLCStopInsuredSchema.setModifyTime(PubFun.getCurrentTime());

					mLCStopInsuredSet.add(tLCStopInsuredSchema);

					// 更新被保人表
					tSqlInsured = " update LCInsured set ContNo = '"
							+ "?newContNo?" + "' where ContNo = '" + "?ContNo?" + "'"
							+ " and InsuredNo = '"
							+ "?InsuredNo?" + "'";
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(tSqlInsured);
					sqlbv.put("newContNo", mNewContNo);
					sqlbv.put("ContNo", mContNo);
					sqlbv.put("InsuredNo", mLCInsuredSet.get(i).getInsuredNo());
					map.put(sqlbv, "UPDATE");

					// 更新客户告知
					tSqlLCCustomerImpart = " update LCInsured set ContNo = '"
							+ "?newContNo?" + "' where ContNo = '" + "?ContNo?" + "'"
							+ " and InsuredNo = '"
							+ "?InsuredNo?" + "'";
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(tSqlLCCustomerImpart);
					sqlbv1.put("newContNo", mNewContNo);
					sqlbv1.put("ContNo", mContNo);
					sqlbv1.put("InsuredNo", mLCInsuredSet.get(i).getInsuredNo());
					map.put(sqlbv1, "UPDATE");

					// 更新客户告知参数
					tSqlLCCustomerImpartParams = " update LCInsured set ContNo = '"
							+ "?newContNo?" + "' where ContNo = '" + "?ContNo?" + "'"
							+ " and InsuredNo = '"
							+ "?InsuredNo?" + "'";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tSqlLCCustomerImpart);
					sqlbv2.put("newContNo", mNewContNo);
					sqlbv2.put("ContNo", mContNo);
					sqlbv2.put("InsuredNo", mLCInsuredSet.get(i).getInsuredNo());

					map.put(sqlbv2, "UPDATE");

					tInsuredNo = mLCInsuredSet.get(i).getInsuredNo();
					tLCPolDB.setContNo(mContNo);
					tLCPolDB.setInsuredNo(tInsuredNo);
					tLCPolSet = tLCPolDB.query();
					if (tLCPolSet.size() <= 0) {
						CError tError = new CError();
						tError.moduleName = "SplitFamilyBL";
						tError.functionName = "prepareUpdateTable";
						tError.errorMessage = "查询险种保单表失败!";
						this.mErrors.addOneError(tError);
						return false;
					}
					for (int j = 1; j <= tLCPolSet.size(); j++) {
						tPolNo = tLCPolSet.get(j).getPolNo();
						// 更新收益人表
						tSqlBnf = " update LCBnf set ContNo = '" + "?ContNo?"
								+ "' where PolNo = '" + "?PolNo?" + "'";
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(tSqlBnf);
						sqlbv3.put("ContNo", mNewContNo);
						sqlbv3.put("PolNo", tPolNo);
						map.put(sqlbv3, "UPDATE");

						// 更新保单险种责任表
						tSqlDuty = " update LCDuty set ContNo = '" 
								+ "?ContNo?"
								+ "' where PolNo = '" + "?PolNo?" + "'";
						SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
						sqlbv4.sql(tSqlDuty);
						sqlbv4.put("ContNo", mNewContNo);
						sqlbv4.put("PolNo", tPolNo);
						map.put(sqlbv4, "UPDATE");

						// 更新保险帐户管理费表
						tSqlInsureAccFee = " update LCInsureAccFee set ContNo = '"
								+ "?ContNo?"
								+ "' where PolNo = '" + "?PolNo?" + "'";
						SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
						sqlbv5.sql(tSqlInsureAccFee);
						sqlbv5.put("ContNo", mNewContNo);
						sqlbv5.put("PolNo", tPolNo);
						map.put(sqlbv5, "UPDATE");

						// 更新保险账户管理费分类表
						tSqlInsureAccClassFee = " update LCInsureAccClassFee set ContNo = '"
								+ "?ContNo?"
								+ "' where PolNo = '" + "?PolNo?" + "'";
						SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
						sqlbv6.sql(tSqlInsureAccClassFee);
						sqlbv6.put("ContNo", mNewContNo);
						sqlbv6.put("PolNo", tPolNo);
						map.put(sqlbv6, "UPDATE");

						// 更新保险账户分类表
						tSqlInsureAccClass = " update LCInsureAccClass set ContNo = '"
								+ "?ContNo?"
								+ "' where PolNo = '" + "?PolNo?" + "'";
						SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
						sqlbv7.sql(tSqlInsureAccClass);
						sqlbv7.put("ContNo", mNewContNo);
						sqlbv7.put("PolNo", tPolNo);
						map.put(sqlbv7, "UPDATE");

						// 更新保险帐户表记价履历表
						tSqlInsureAccTrace = " update LCInsureAccTrace set ContNo = '"
								+ "?ContNo?"
								+ "' where PolNo = '" + "?PolNo?" + "'";
						SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
						sqlbv8.sql(tSqlInsureAccClass);
						sqlbv8.put("ContNo", mNewContNo);
						sqlbv8.put("PolNo", tPolNo);
						map.put(sqlbv8, "UPDATE");

						// 更新保险帐户表
						tSqlInsureAcc = " update LCInsureAcc set ContNo = '"
								+ "?ContNo?"
								+ "' where PolNo = '" + "?PolNo?" + "'";
						SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
						sqlbv9.sql(tSqlInsureAcc);
						sqlbv9.put("ContNo", mNewContNo);
						sqlbv9.put("PolNo", tPolNo);
						map.put(sqlbv9, "UPDATE");

						// 更新领取项表
						tSqlGet = " update LCGet set ContNo = '" 
								+ "?ContNo?"
								+ "' where PolNo = '" + "?PolNo?" + "'";
						SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
						sqlbv10.sql(tSqlGet);
						sqlbv10.put("ContNo", mNewContNo);
						sqlbv10.put("PolNo", tPolNo);
						map.put(sqlbv10, "UPDATE");

						// 更新保费项表
						tSqlPrem = " update LCPrem set ContNo = '" 
								+ "?ContNo?"
								+ "' where PolNo = '" + "?PolNo?" + "'";
						SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
						sqlbv11.sql(tSqlPrem);
						sqlbv11.put("ContNo", mNewContNo);
						sqlbv11.put("PolNo", tPolNo);
						map.put(sqlbv11, "UPDATE");

						// 更新个人险种表
						tSqlPol = " update LCPol set ContNo = '" 
								+ "?ContNo?"
								+ "' where PolNo = '" + "?PolNo?" + "'";
						SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
						sqlbv12.sql(tSqlPol);
						sqlbv12.put("ContNo", mNewContNo);
						sqlbv12.put("PolNo", tPolNo);
						map.put(sqlbv12, "UPDATE");
					}
				}
			}
		}
		map.put(mLCStopInsuredSet, "INSERT");

		return true;
	}

	/**
	 * prepareNewCont 准备暂停后新的保单数据
	 * 
	 * @return boolean
	 */
	private boolean prepareNewCont() {
		String tInsuredStat = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();

		logger.debug("cal Prem,Amnt,Mult Start...");
		mPrem = mLCContSchema.getPrem();
		mAmnt = mLCContSchema.getAmnt();
		mMult = mLCContSchema.getMult();

		for (int i = 1; i <= mLCInsuredSet.size(); i++) {
			tInsuredStat = mLCInsuredSet.get(i).getInsuredStat();
			if (tInsuredStat == null || tInsuredStat.length() <= 0) {
			} else {

				String tSql = "select sum(prem),sum(amnt),sum(mult) from lcpol where 1=1 "
						+ " and ContNo = '"
						+ "?ContNo?"
						+ "'"
						+ " and InsuredNo = '"
						+ "?InsuredNo?" + "'";
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
				sqlbv13.sql(tSql);
				sqlbv13.put("ContNo", mContNo);
				sqlbv13.put("InsuredNo", mLCInsuredSet.get(i).getInsuredNo());
				tSSRS = tExeSQL.execSQL(sqlbv13);
				int n = tSSRS.getMaxRow();
				if (n > 1) {
					CError tError = new CError();
					tError.moduleName = "SplitFamilyBL";
					tError.functionName = "getInputData";
					tError.errorMessage = "计算拆分保单保费失败!";
					this.mErrors.addOneError(tError);
					return false;
				}

				String tResult[][] = tSSRS.getAllData();
				// 拆分后新保单保费、保额和份数
				mNewPrem = mNewPrem + Double.parseDouble(tResult[0][0]);
				mNewAmnt = mNewAmnt + Double.parseDouble(tResult[0][1]);
				mNewMult = mNewMult + Double.parseDouble(tResult[0][2]);
				logger.debug("mNewPrem=" + mNewPrem + "  mNewAmnt="
						+ mNewAmnt + "  mNewMult=" + mNewMult);
			}
		}
		// 拆分后原保单保费、保额和份数
		mPrem = mPrem - mNewPrem;
		mAmnt = mAmnt - mNewAmnt;
		mMult = mMult - mNewMult;
		logger.debug("Prem=" + mPrem + "  Amnt=" + mAmnt + "  Mult="
				+ mMult);

		mLCContSchema.setContNo(mNewContNo);
		mLCContSchema.setPrem(mPrem);
		mLCContSchema.setAmnt(mAmnt);
		mLCContSchema.setMult(mMult);

		logger.debug("cal Prem,Amnt,Mult End...");

		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mContNo);
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "SplitFamilyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询投保人表失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCAppntSchema = tLCAppntDB.getSchema();
		mLCAppntSchema.setContNo(mNewContNo);

		map.put(mLCContSchema, "INSERT");
		map.put(mLCAppntSchema, "INSERT");

		return true;
	}

	/**
	 * checkData 校验数据正确性
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "SplitFamilyBL";
			tError.functionName = "checkData";
			tError.errorMessage = "查询报单表失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mContNo);
		mLCInsuredSet = tLCInsuredDB.query();
		if (mLCInsuredSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "SplitFamilyBL";
			tError.functionName = "checkData";
			tError.errorMessage = "查询险种保单表失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * getInputData 得到前台传输的数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "SplitFamilyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "SplitFamilyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mManageCom = mGlobalInput.Operator;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "SplitFamilyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "SplitFamilyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();

		/** 全局变量 */
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";
		tGlobalInput.ManageCom = "86";

		tTransferData.setNameAndValue("ContNo", "130110000009144");

		tVData.add(tGlobalInput);
		tVData.add(tTransferData);

		SplitFamilyBL tSplitFamilyBL = new SplitFamilyBL();
		if (!tSplitFamilyBL.submitData(tVData, "")) {
			logger.debug("error");
		}

	}
}
