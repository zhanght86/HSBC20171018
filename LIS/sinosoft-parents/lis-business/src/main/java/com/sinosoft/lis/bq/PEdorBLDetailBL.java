package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单质押银行贷款BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Howie
 * @version 1.0
 */

public class PEdorBLDetailBL {
private static Logger logger = Logger.getLogger(PEdorBLDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private double mPolCashValue = 0.0;
	private double mLoanCorpusAddInterest = 0.0;
	private double mAutoPayCorpusAddInterest = 0.0;

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private Reflections mReflections = new Reflections();

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	public PEdorBLDetailBL() {
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
			if (!getArrayForFormatPage()) {
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
				tError.moduleName = "PEdorBLDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询保全项目失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLPEdorItemSchema = tLPEdorItemDB.getSchema();
			// 如果是查询
			if (mOperate.equals("QUERY||MAIN")) {
				return true;
			}
			// 不是查询
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorBLDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
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
				tError.moduleName = "PEdorBLDetailBL";
				tError.functionName = "checkData";
				tError.errorMessage = "无保全申请数据！";
				this.mErrors.addOneError(tError);
				return false;
			}

			mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
			if (tLPEdorItemDB.getEdorState().trim().equals("2")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorBLDetailBL";
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
		try {
			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();
			double tAutoPay = 0.0;
			double tSumCash = 0.0;
			BqPolBalBL tBqPolBalBL = new BqPolBalBL();
			// /*******************贷款条件校验************************\
			// if (!checkLoan(mLPEdorItemSchema.getContNo(),
			// mLPEdorItemSchema.getEdorValiDate()))
			// {
			// return false;
			// }
			// /*****************************************************/
			// 保单质押贷款本息和
			if (tBqPolBalBL.calLoanCorpusAddInterest(this.mLPEdorItemSchema
					.getContNo(), this.mLPEdorItemSchema.getEdorValiDate())) {
				if (tBqPolBalBL.getCalResult() == -1) {
					mErrors.copyAllErrors(tBqPolBalBL.mErrors);
					return false;
				}
				this.mLoanCorpusAddInterest = tBqPolBalBL.getCalResult();
			}

			// 查询保单下的所有险种信息
			String tSql = "SELECT * FROM LCPol WHERE ContNo='"
					+ "?ContNo?" + "' and AppFlag='1'";
			LCPolSchema tLCPolSchema = new LCPolSchema();
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tSql);
			sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
			LCPolSet tLCPolSet = new LCPolSet();
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolSet = tLCPolDB.executeQuery(sbv1);
			if (tLCPolSet == null || tLCPolSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorBLDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询险种信息时产生错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				double tPC = 0.0;
				double tAP = 0.0;
				tLCPolSchema = tLCPolSet.get(i);
				EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
				// 现金价值
				tPC = tEdorCalZT.getCashValue(tLCPolSchema.getPolNo(),
						this.mLPEdorItemSchema.getEdorValiDate());
				if (tPC == -1) {
					mErrors.copyAllErrors(tEdorCalZT.mErrors);
					return false;
				}
				tSumCash += tPC;
				//add by jiaqiangli 2009-04-10 需要重新初始化类
				tBqPolBalBL = new BqPolBalBL();
				// 自垫本息和
				if (tBqPolBalBL.calAutoPayPremAddInterest(tLCPolSchema
						.getPolNo(), this.mLPEdorItemSchema.getEdorValiDate())) {
					if (tBqPolBalBL.getCalResult() == -1) {
						mErrors.copyAllErrors(tBqPolBalBL.mErrors);
						return false;
					}
					tAP = tBqPolBalBL.getCalResult();
				}
				tAutoPay += tAP;
			}
			this.mPolCashValue = this.getRound(tSumCash);
			this.mAutoPayCorpusAddInterest = tAutoPay;

			if (mPolCashValue - mLoanCorpusAddInterest
					- mAutoPayCorpusAddInterest < 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorBLDetailBL";
				tError.functionName = "checkLoan";
				tError.errorMessage = "保单现金价值金额小于各项欠款本息，不允许贷款！";
				this.mErrors.addOneError(tError);
				return false;
			}

			// //改变保单状态
			// LPContStateSet tLPContStateSet = new LPContStateSet();
			// LCContStateSchema tLCContStateSchema = new LCContStateSchema();
			// LPContStateSchema tLPContStateSchema = new LPContStateSchema();
			// String strsql = "";
			//
			// strsql = "select * from LCContState "
			// + " where ContNo='" + mLPEdorItemSchema.getContNo() + "'"
			// + " and StateType='BankLoan' and State='1'"
			// + " and EndDate is null";
			//
			// ExeSQL tExeSQL = new ExeSQL();
			// SSRS tSSRS = new SSRS();
			// tSSRS = tExeSQL.execSQL(strsql);
			// if (tSSRS != null && tSSRS.MaxRow > 0)
			// {
			// //现在的状态就是要改变后的状态
			// return true;
			// }
			// //查询现在状态，将此状态结束
			// strsql = "SELECT *"
			// + " FROM LCContState"
			// + " WHERE ContNo='" + mLPEdorItemSchema.getContNo() + "'"
			// + " and StateType='BankLoan' "
			// + " and EndDate is null";
			// LCContStateDB tLCContStateDB = new LCContStateDB();
			// LCContStateSet tLCContStateSet = new LCContStateSet();
			// tLCContStateSet = tLCContStateDB.executeQuery(strsql);
			// if (tLCContStateSet != null && tLCContStateSet.size() > 0)
			// {
			// tLCContStateSchema = new LCContStateSchema();
			// tLCContStateSchema = tLCContStateSet.get(1);
			// //状态在前一天结束
			// tLCContStateSchema.setEndDate(PubFun.calDate(mLPEdorItemSchema.
			// getEdorValiDate(), -1, "D", null));
			// tLCContStateSchema.setOperator(mGlobalInput.Operator);
			// tLCContStateSchema.setModifyDate(strCurrentDate);
			// tLCContStateSchema.setModifyTime(strCurrentTime);
			// tLPContStateSchema = new LPContStateSchema();
			// this.mReflections.transFields(tLPContStateSchema,
			// tLCContStateSchema);
			// tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			// tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			// tLPContStateSet.add(tLPContStateSchema);
			// }
			// //生成新状态记录
			// tLPContStateSchema = new LPContStateSchema();
			// tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			// tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			// tLPContStateSchema.setContNo(mLPEdorItemSchema.getContNo());
			// tLPContStateSchema.setInsuredNo("000000");
			// tLPContStateSchema.setPolNo("000000");
			// tLPContStateSchema.setStateType("BankLoan");
			// tLPContStateSchema.setState("1");
			// //银行贷款原因
			// //tLPContStateSchema.setStateReason();
			// tLPContStateSchema.setStartDate(mLPEdorItemSchema.getEdorValiDate());
			// tLPContStateSchema.setOperator(this.mGlobalInput.Operator);
			// tLPContStateSchema.setMakeDate(strCurrentDate);
			// tLPContStateSchema.setMakeTime(strCurrentTime);
			// tLPContStateSchema.setModifyDate(strCurrentDate);
			// tLPContStateSchema.setModifyTime(strCurrentTime);
			// tLPContStateSet.add(tLPContStateSchema);
			// mMap.put(tLPContStateSet, "DELETE&INSERT");

			// 查询保单状态
			String strsql = "";
			LCContStateSchema tLCContStateSchema = new LCContStateSchema();
			LPContStateSchema tLPContStateSchema = new LPContStateSchema();
			LPContStateSet tLPContStateSet = new LPContStateSet();
			strsql = "select * from LCContState " + " where ContNo='"
					+ "?ContNo?" + "'"
					+ " and StateType='BankLoan' and State='1'"
					+ " and EndDate is null";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(strsql);
			sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sbv2);
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
					sbv2=new SQLwithBindVariables();
					sbv2.sql(strsql);
					sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
					tLCContStateDB = new LCContStateDB();
					LCContStateSet tLCContStateSet = new LCContStateSet();
					tLCContStateSet = tLCContStateDB.executeQuery(sbv2);
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
				tLPContStateSchema.setInsuredNo("000000");
				tLPContStateSchema.setPolNo("000000");
				tLPContStateSchema.setStateType("BankLoan");
				tLPContStateSchema.setState("1");
				// 银行贷款原因
				// tLPContStateSchema.setStateReason();
				tLPContStateSchema.setStartDate(mLPEdorItemSchema
						.getEdorValiDate());
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
			mBqCalBase.setPolNo(tLCPolSchema.getPolNo());
			mBqCalBase.setRiskCode(tLCPolSchema.getRiskCode());
			mResult.add(mBqCalBase);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorBLDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误！" + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 获得一个二维数组，初始化MulLine用 行内容：险种代码、险种名称、保额、保费、现金价值、贷款本息、自垫本息、险种号（隐藏列）
	 * 
	 * @return true--Success,false--Fail
	 */
	private boolean getArrayForFormatPage() {
		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		// /*******************贷款条件校验************************\
		// if (!checkLoan(tRequitalDate))
		// {
		// return false;
		// }
		// \*****************************************************/
		// 获得险种项目，即二维数组行数（注意：此处的sql查询所有险种，包括短期附加险）
		String tSql = "SELECT distinct RiskCode,"
				+ " (select RiskName from LMRisk where RiskCode=a.RiskCode),"
				+ " Amnt," + " Prem,"
				+ " PolNo,"
				+ " (case MainPolNo when PolNo then '1' else '0' end)" // 是否主险标志
				+ " FROM LCPol a" + " WHERE ContNo='"
				+ "?ContNo?" + "' and AppFlag='1'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ContNo", this.mLPEdorItemSchema.getContNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow == 0) {
			// 没有险种信息
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorBLDetailBL";
			tError.functionName = "getArrayForFormatPage";
			tError.errorMessage = "没有查询到险种项目信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		int tArrLen = tSSRS.MaxRow;
		String rStrArray[][] = new String[tArrLen][8];
		for (int i = 0; i < tArrLen; i++) {
			// 险种代码
			rStrArray[i][0] = tSSRS.GetText(i + 1, 1);
			// 险种名称
			rStrArray[i][1] = tSSRS.GetText(i + 1, 2);
			// 保额
			rStrArray[i][2] = tSSRS.GetText(i + 1, 3);
			// 保费
			rStrArray[i][3] = tSSRS.GetText(i + 1, 4);
			// 险种号（隐藏列）
			rStrArray[i][7] = tSSRS.GetText(i + 1, 5);
			// 现金价值
			if (!getPolCashValue(rStrArray[i][7], this.mLPEdorItemSchema
					.getEdorValiDate())) {
				return false;
			}
			rStrArray[i][4] = String.valueOf(mPolCashValue);
			// 贷款本息和
			if (tBqPolBalBL.calLoanCorpusAddInterest(this.mLPEdorItemSchema
					.getContNo(), this.mLPEdorItemSchema.getEdorValiDate())) {
				if (tBqPolBalBL.getCalResult() == -1) {
					mErrors.copyAllErrors(tBqPolBalBL.mErrors);
					return false;
				}
				rStrArray[i][5] = String.valueOf(tBqPolBalBL.getCalResult());
			}
			//add by jiaqiangli 2009-04-10 需要重新初始化类
			tBqPolBalBL = new BqPolBalBL();
			// 自垫本息和
			if (tBqPolBalBL.calAutoPayPremAddInterest(rStrArray[i][7],
					this.mLPEdorItemSchema.getEdorValiDate())) {
				if (tBqPolBalBL.getCalResult() == -1) {
					mErrors.copyAllErrors(tBqPolBalBL.mErrors);
					return false;
				}
				rStrArray[i][6] = String.valueOf(tBqPolBalBL.getCalResult());
			}
		}
		// 组织数据
		mResult.clear();
		// 数组
		mResult.add(rStrArray);
		return true;
	}

	/**
	 * 获取保单（险种）的现金价值 临时写在这里的函数，以后再替换成真正的计算函数
	 * 
	 * @param String
	 *            tPolNo
	 * @param String
	 *            tCalDate 计算日期
	 * @return double
	 */
	private boolean getPolCashValue(String tPolNo, String tCalDate) {
		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
		mPolCashValue = tEdorCalZT.getCashValue(tPolNo, tCalDate);
		if (mPolCashValue == -1) {
			mErrors.copyAllErrors(tEdorCalZT.mErrors);
			return false;
		}
		return true;
	}

	/**
	 * 是否允许贷款的校验,暂时不添加校验条件
	 * 
	 * @param String
	 * @param
	 * @return boolean
	 */
	private boolean checkLoan(String tContNo, String tCountDate) {
		return true;
	}

	/**
	 * 四舍六入五靠偶数，保留两位
	 * 
	 * @param tValue
	 *            double
	 * @return double
	 */
	private double getRound(double tValue) {
//		String t = "0.00";
//		DecimalFormat tDF = new DecimalFormat(t);
//		return Double.parseDouble(tDF.format(tValue));
		//此保全项的编码为LN，此程序应该是废弃的 comment by jiaqiangli
		//MS采用通常意义上的四舍五入规则而不是上述的四舍六入五靠偶数
		//modify by jiaqiangli 2008-10-30 lis65程序四舍五入调用方法
		//修改子程序接口
		return PubFun.round(tValue,2);
	}

	/*
	 * private boolean checkLoan(String tRequitalDate) { try {
	 * //判断贷款到期时（生效日期+6个月），不得超过约定领取年龄的生效对应日 //实际与所有生存领取里起领日期最小的比 //查询对应最小起领日期
	 * String tSql = "SELECT to_char(min(GetStartDate),'YYYY-MM-DD')" + " FROM
	 * LCGet" + " WHERE ContNo='" + mLPEdorItemSchema.getContNo() + "'" + " and
	 * LiveGetType='0'"; SSRS tSSRS = new SSRS(); ExeSQL tExeSQL = new ExeSQL();
	 * tSSRS = tExeSQL.execSQL(tSql); if (tSSRS == null || tSSRS.MaxRow <= 0) {
	 * return true; } FDate tFDateA = new FDate(); FDate tFDateB = new FDate();
	 * if (tFDateA.getDate(tSSRS.GetText(1,
	 * 1)).before(tFDateB.getDate(tRequitalDate))) { // @@错误处理 CError tError =
	 * new CError(); tError.moduleName = "PEdorBLDetailBL"; tError.functionName =
	 * "checkLoan"; tError.errorMessage = "贷款到期日（" + tRequitalDate +
	 * "）超过约定领取年龄的生效对应日（" + tSSRS.GetText(1, 1) + "），不允许贷款！";
	 * this.mErrors.addOneError(tError); return false; } } catch(Exception e) {
	 * return true; } return true; }
	 */
}
