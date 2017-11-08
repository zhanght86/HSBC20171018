package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单质押银行贷款清偿BL</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Howie
 * @version 1.0
 */


import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PEdorBDDetailBL {
private static Logger logger = Logger.getLogger(PEdorBDDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private TransferData mTransferData = new TransferData();

	/** 改变保单状态相关 */
	private Reflections mReflections = new Reflections();

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	public PEdorBDDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据查询，初始化界面时初始化MulLine用
		if (mOperate.equals("QUERY||MAIN")) {
			if (!getInterfaceData()) {
				return false;
			} else {
				return true;
			}
		}

		// 数据校验操作
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

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
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 保全项目校验
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
			tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
			tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
			if (!tLPEdorItemDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorBDDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询保全项目失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 如果是查询
			if (mOperate.equals("QUERY||MAIN")) {
				return true;
			}
			// 不是查询
			mLPEdorItemSchema = tLPEdorItemDB.getSchema();
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorBDDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	private boolean getInterfaceData() {
		logger.debug("---Start getInterfaceData---");
		// 是否发生过银行贷款标志 0--没做过，1--做过
		String tBankLoanFlag = "";
		// 贷款起期
		String tBankLoanDate = "";
		// 是否已经做过保全标志 0--没做过，1--做过
		String tHaveEdorFlag = "";
		String strSQL = "";
		strSQL = "select * from lccontstate " + " where contno='"
				+ "?contno?" + "'"
				+ " and statetype='BankLoan' and state='1'"
				+ " and enddate is null";
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql(strSQL);
        sbv1.put("contno", mLPEdorItemSchema.getContNo());
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		tLCContStateSet = tLCContStateDB.executeQuery(sbv1);
		if (tLCContStateSet == null || tLCContStateSet.size() <= 0) {
			tBankLoanFlag = "0";
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "PEdorRFDetailBL";
			// tError.functionName = "getInterfaceData";
			// tError.errorMessage = "没有未清偿的银行贷款信息！";
			// this.mErrors.addOneError(tError);
			// return false;
		} else {
			tBankLoanFlag = "1";
			tBankLoanDate = tLCContStateSet.get(1).getStartDate();
		}

		// 检查是否已经做过保全
		strSQL = "select * from LPContState b" + " where EdorNo='"
				+ "?EdorNo?" + "'" + " and EdorType='"
				+ "?EdorType?" + "'"
				+ " and StateType='BankLoan' and state='0' and EndDate is null"
				+ " and not exists(select 'x' from LPContState"
				+ " where ContNo=b.ContNo" + " and EdorNo=b.EdorNo"
				+ " and EdorType=b.EdorType" + " and InsuredNo=b.InsuredNo"
				+ " and PolNo=b.PolNo" + " and StateType='Terminate'"
				+ " and State='1'" + " and EndDate is null)";
		sbv1=new SQLwithBindVariables();
        sbv1.sql(strSQL);
        sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
        sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
		LPContStateDB tLPContStateDB = new LPContStateDB();
		LPContStateSet tLPContStateSet = new LPContStateSet();
		tLPContStateSet = tLPContStateDB.executeQuery(sbv1);
		if (tLPContStateSet == null || tLPContStateSet.size() <= 0) {
			tHaveEdorFlag = "0";
		} else {
			tHaveEdorFlag = "1";
		}
		// 组织数据
		mResult.clear();
		TransferData tTransferData = new TransferData();
		// 是否发生贷款
		tTransferData.setNameAndValue("BankLoanFlag", tBankLoanFlag);
		// 贷款起期
		tTransferData.setNameAndValue("BankLoanDate", tBankLoanDate);
		// 是否已经做过保全标志 0--没做过，1--做过
		tTransferData.setNameAndValue("HaveEdorFlag", tHaveEdorFlag);
		// 放入结果返回
		mResult.add(tTransferData);
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		if (!mOperate.equals("QUERY||MAIN")) {
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
			if (!tLPEdorItemDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorBDDetailBL";
				tError.functionName = "checkData";
				tError.errorMessage = "无保全申请数据！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
			if (tLPEdorItemDB.getEdorState().trim().equals("2")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorBDDetailBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该保全已经申请确认不能修改！";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		// 解除止付类型
		String tEndPayType = (String) mTransferData
				.getValueByName("EndPayType");
		// 查询保单状态
		String strsql = "";
		LCContStateSchema tLCContStateSchema = new LCContStateSchema();
		LPContStateSchema tLPContStateSchema = new LPContStateSchema();
		LPContStateSet tLPContStateSet = new LPContStateSet();
		try {
			strsql = "select * from LCContState " + " where ContNo='"
					+ "?ContNo?" + "'"
					+ " and StateType='BankLoan' and State='0'"
					+ " and EndDate is null";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strsql);
			sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// 按张容所说，如果新旧状态主键重复（指在C表中重复），则直接用新状态把旧状态替掉。2005-09-09日修改。
				LCContStateDB tLCContStateDB = new LCContStateDB();
				tLCContStateDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCContStateDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
				tLCContStateDB.setPolNo(mLPEdorItemSchema.getPolNo());
				tLCContStateDB.setStateType("BankLoan");
				tLCContStateDB
						.setStartDate(mLPEdorItemSchema.getEdorValiDate());
				if (!tLCContStateDB.getInfo()) {
					// 查询现在状态，将此状态结束
					strsql = "SELECT *" + " FROM LCContState"
							+ " WHERE ContNo='" + "?ContNo?"
							+ "'" + " and StateType='BankLoan' "
							+ " and EndDate is null";
					sqlbv=new SQLwithBindVariables();
					sqlbv.sql(strsql);
					sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
					tLCContStateDB = new LCContStateDB();
					LCContStateSet tLCContStateSet = new LCContStateSet();
					tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
					if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
						tLCContStateSchema = new LCContStateSchema();
						tLCContStateSchema = tLCContStateSet.get(1);
						// 状态在前一天结束
						tLCContStateSchema.setEndDate(PubFun.calDate(
								mLPEdorItemSchema.getEdorValiDate(), -1, "D",
								null));
						tLCContStateSchema.setOperator(mGlobalInput.Operator);
						tLCContStateSchema.setModifyDate(strCurrentDate);
						tLCContStateSchema.setModifyTime(strCurrentTime);
						tLPContStateSchema = new LPContStateSchema();
						this.mReflections.transFields(tLPContStateSchema,
								tLCContStateSchema);
						tLPContStateSchema.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						tLPContStateSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						tLPContStateSet.add(tLPContStateSchema);
					}
				}

				// 生成新状态记录
				tLPContStateSchema = new LPContStateSchema();
				tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPContStateSchema.setContNo(mLPEdorItemSchema.getContNo());
				tLPContStateSchema.setInsuredNo(mLPEdorItemSchema
						.getInsuredNo());
				tLPContStateSchema.setPolNo(mLPEdorItemSchema.getPolNo());
				tLPContStateSchema.setStateType("BankLoan");
				tLPContStateSchema.setState("0");
				tLPContStateSchema.setStartDate(mLPEdorItemSchema
						.getEdorValiDate());
				// 银行贷款清偿（解除止付类型）原因
				tLPContStateSchema.setStateReason(tEndPayType);
				tLPContStateSchema.setOperator(this.mGlobalInput.Operator);
				tLPContStateSchema.setMakeDate(strCurrentDate);
				tLPContStateSchema.setMakeTime(strCurrentTime);
				tLPContStateSchema.setModifyDate(strCurrentDate);
				tLPContStateSchema.setModifyTime(strCurrentTime);
				tLPContStateSet.add(tLPContStateSchema);
				mMap.put(tLPContStateSet, "DELETE&INSERT");
			}
			// 修改“个险保全项目表”相应信息
			mLPEdorItemSchema.setEdorState("3");
			mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
			mLPEdorItemSchema.setModifyDate(strCurrentDate);
			mLPEdorItemSchema.setModifyTime(strCurrentTime);
			mMap.put(mLPEdorItemSchema, "UPDATE");

			mResult.clear();
			mResult.add(mMap);
			mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
			mResult.add(mBqCalBase);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorBDDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误！" + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
