package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPRNPolAmntSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPRNPolAmntSet;
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
 * Description: 满期降低保额续保BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorERDetailBL {
private static Logger logger = Logger.getLogger(PEdorERDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// private Reflections mReflections = new Reflections();

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	// private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LPRNPolAmntSet mLPRNPolAmntSet = new LPRNPolAmntSet();
	private LPPolSet mLPPolSet = new LPPolSet();
	// 记录险种销售方式。第一列记录PolNo，第二列记录：1.按保额销售 2.按份数销售。getInputData函数中创建对象。
	private String mPolAmntFlag[][] = null;
	private double mAmntByMult = 0.0; // 存储根据份数算保额的结果。

	/** 用户输入新的保额 */
	// private double mNewAmnt = 0.0;
	/** 记录变动的保额，存入保全项目表 */
	private double mChangeAmnt = 0.0;

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 返回到界面结果，操作后要显示的信息，如果没有就不传 */
	// private TransferData mTransferData;
	private Reflections mReflections = new Reflections();

	public PEdorERDetailBL() {
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
				tError.moduleName = "PEdorERDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询保全项目失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLPEdorItemSchema = tLPEdorItemDB.getSchema();

			// 获得用户输入的保额
			LPPolSet tLPPolSet = new LPPolSet();
			try {
				tLPPolSet = (LPPolSet) mInputData.getObjectByObjectName(
						"LPPolSet", 0);
				if (tLPPolSet == null || tLPPolSet.size() <= 0) {
					// // @@错误处理
					// CError tError = new CError();
					// tError.moduleName = "PEdorERDetailBL";
					// tError.functionName = "getInputData";
					// tError.errorMessage = "无新的险种保额信息！";
					// this.mErrors.addOneError(tError);
					// return false;
					logger.debug("无新的险种保额信息！");
				}
			} catch (Exception e) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorERDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "获得用户输入的险种保额信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 获得全部用户变更保额的险种信息到全局的mLPPolSet
			// 注意：这里无论是保额还是份数，都是通过Amnt字段传近来的，在后台将数据分离
			if (tLPPolSet != null && tLPPolSet.size() > 0) {
				LPPolSchema tLPPolSchema = null;
				LCPolSchema tLCPolSchema = null;
				LCPolDB tLCPolDB = new LCPolDB();
				mPolAmntFlag = new String[tLPPolSet.size()][2];
				SSRS tSSRS = null;
				ExeSQL tExeSQL = new ExeSQL();
				for (int i = 1; i <= tLPPolSet.size(); i++) {
					tLCPolDB.setPolNo(tLPPolSet.get(i).getPolNo());
					if (!tLCPolDB.getInfo()) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "PEdorERDetailBL";
						tError.functionName = "getInputData";
						tError.errorMessage = "查询险种信息失败！";
						this.mErrors.addOneError(tError);
						return false;
					}
					tLCPolSchema = new LCPolSchema();
					tLCPolSchema.setSchema(tLCPolDB.getSchema());
					// 判断险种销售方式并记录
					String tSql = "SELECT (case when exists(select 'x' from LCDuty b where ContNo='?ContNo?' and PolNo='?PolNo?' and exists(select 'y' from LMDuty where DutyCode=b.DutyCode and AmntFlag='1')) then '1'"
							+ "  else '2'" + "  end)" + "FROM dual";
					SQLwithBindVariables sbv1=new SQLwithBindVariables();
					sbv1.sql(tSql);
					sbv1.put("ContNo", tLCPolSchema.getContNo());
					sbv1.put("PolNo", tLCPolSchema.getPolNo());
					tSSRS = new SSRS();
					tSSRS = tExeSQL.execSQL(sbv1);
					if (tSSRS == null || tSSRS.MaxRow <= 0) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "PEdorERDetailBL";
						tError.functionName = "getInputData";
						tError.errorMessage = "获得险种销售方式时产生错误！";
						this.mErrors.addOneError(tError);
						return false;
					}
					mPolAmntFlag[i - 1][0] = tLCPolSchema.getPolNo();
					mPolAmntFlag[i - 1][1] = tSSRS.GetText(1, 1);
					if ("2".equals(mPolAmntFlag[i - 1][1])) {
						// 按份数销售
						if (!getAmntByMult(tLPPolSet.get(i).getPolNo(),
								tLPPolSet.get(i).getAmnt())) {
							return false;
						}
						// /*****统计变动的保额，存入保全项目表用***************************\
						mChangeAmnt = tLCPolSchema.getAmnt() - mAmntByMult
								+ mChangeAmnt;
						// \***********************************************************/
						tLPPolSchema = new LPPolSchema();
						mReflections.transFields(tLPPolSchema, tLCPolSchema);
						tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPPolSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						tLPPolSchema.setAmnt(mAmntByMult); // 存入新的保额
						tLPPolSchema.setMult(tLPPolSet.get(i).getAmnt()); // 存入新的份数
						mLPPolSet.add(tLPPolSchema);
					} else {
						// /*****统计变动的保额，存入保全项目表用***************************\
						mChangeAmnt = tLCPolSchema.getAmnt()
								- tLPPolSet.get(i).getAmnt() + mChangeAmnt;
						// \***********************************************************/
						tLPPolSchema = new LPPolSchema();
						mReflections.transFields(tLPPolSchema, tLCPolSchema);
						tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPPolSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						tLPPolSchema.setAmnt(tLPPolSet.get(i).getAmnt()); // 存入新的保额
						mLPPolSet.add(tLPPolSchema);
					}
				}
			}
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorERDetailBL";
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
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorERDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "无保全申请数据！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		if (tLPEdorItemDB.getEdorState().trim().equals("2")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorERDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该保全已经申请确认不能修改！";
			this.mErrors.addOneError(tError);
			return false;
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

			// 续保保额参考表数据
			LCDutyDB tLCDutyDB = new LCDutyDB();
			LCDutySet tLCDutySet = null;
			LCDutySet tChangeLCDutySet = null;
			LCDutySchema tLCDutySchema = null;
			LPRNPolAmntSchema tLPRNPolAmntSchema = null;
			double tNewAmntOrMult = 0.0;
			String tSql = "";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			boolean tAmntTMultF = true;
			// 先查询所有责任信息
			if (mLPPolSet != null && mLPPolSet.size() > 0) {
				for (int i = 1; i <= mLPPolSet.size(); i++) {
					// 获得保额份数标志
					tAmntTMultF = true;
					for (int j = 0; j < mPolAmntFlag.length; j++) {
						if (mPolAmntFlag[j][0] != null
								&& mPolAmntFlag[j][0].equals(mLPPolSet.get(i)
										.getPolNo())) {
							if ("2".equals(mPolAmntFlag[j][1])) {
								tAmntTMultF = false;
							}
						}
					}
					tSql = "SELECT * FROM LCDuty WHERE PolNo='?PolNo?' ORDER BY DutyCode";
					sqlbv=new SQLwithBindVariables();
					sqlbv.sql(tSql);
					sqlbv.put("PolNo", mLPPolSet.get(i).getPolNo());
					tLCDutySet = new LCDutySet();
					tLCDutySet = tLCDutyDB.executeQuery(sqlbv);
					if (tLCDutySet == null || tLCDutySet.size() <= 0) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "PEdorERDetailBL";
						tError.functionName = "dealData";
						tError.errorMessage = "查询险种责任信息失败！";
						this.mErrors.addOneError(tError);
						return false;
					}
					if (tAmntTMultF) {
						tNewAmntOrMult = mLPPolSet.get(i).getAmnt();
						tChangeLCDutySet = new LCDutySet();
						for (int j = 1; j <= tLCDutySet.size(); j++) {
							tLCDutySchema = new LCDutySchema();
							tLCDutySchema.setSchema(tLCDutySet.get(j));
							if (tNewAmntOrMult <= tLCDutySchema.getAmnt()) {
								tLCDutySchema.setAmnt(tNewAmntOrMult);
								tChangeLCDutySet.add(tLCDutySchema);
								break;
							} else {
								tChangeLCDutySet.add(tLCDutySchema);
								tNewAmntOrMult -= tLCDutySchema.getAmnt();
							}
						}

						for (int k = 1; k <= tChangeLCDutySet.size(); k++) {
							tLPRNPolAmntSchema = new LPRNPolAmntSchema();
							tLPRNPolAmntSchema.setEdorNo(mLPEdorItemSchema
									.getEdorNo());
							tLPRNPolAmntSchema.setEdorType(mLPEdorItemSchema
									.getEdorType());
							tLPRNPolAmntSchema.setContNo(mLPPolSet.get(i)
									.getContNo());
							tLPRNPolAmntSchema.setPolNo(mLPPolSet.get(i)
									.getPolNo());
							tLPRNPolAmntSchema.setDutyCode(tChangeLCDutySet
									.get(k).getDutyCode());
							tLPRNPolAmntSchema.setAmnt(tChangeLCDutySet.get(k)
									.getAmnt());
							tLPRNPolAmntSchema.setState("0");
							tLPRNPolAmntSchema
									.setOperator(this.mGlobalInput.Operator);
							tLPRNPolAmntSchema.setMakeDate(strCurrentDate);
							tLPRNPolAmntSchema.setMakeTime(strCurrentTime);
							tLPRNPolAmntSchema.setModifyDate(strCurrentDate);
							tLPRNPolAmntSchema.setModifyTime(strCurrentTime);
							mLPRNPolAmntSet.add(tLPRNPolAmntSchema);
						}
					} else {
						tNewAmntOrMult = mLPPolSet.get(i).getMult();
						tChangeLCDutySet = new LCDutySet();
						for (int j = 1; j <= tLCDutySet.size(); j++) {
							tLCDutySchema = new LCDutySchema();
							tLCDutySchema.setSchema(tLCDutySet.get(j));
							if (tNewAmntOrMult <= tLCDutySchema.getMult()) {
								tLCDutySchema.setMult(tNewAmntOrMult);
								tChangeLCDutySet.add(tLCDutySchema);
								break;
							} else {
								tChangeLCDutySet.add(tLCDutySchema);
								tNewAmntOrMult -= tLCDutySchema.getMult();
							}
						}

						for (int k = 1; k <= tChangeLCDutySet.size(); k++) {
							tLPRNPolAmntSchema = new LPRNPolAmntSchema();
							tLPRNPolAmntSchema.setEdorNo(mLPEdorItemSchema
									.getEdorNo());
							tLPRNPolAmntSchema.setEdorType(mLPEdorItemSchema
									.getEdorType());
							tLPRNPolAmntSchema.setContNo(mLPPolSet.get(i)
									.getContNo());
							tLPRNPolAmntSchema.setPolNo(mLPPolSet.get(i)
									.getPolNo());
							tLPRNPolAmntSchema.setDutyCode(tChangeLCDutySet
									.get(k).getDutyCode());
							tLPRNPolAmntSchema.setAmnt(tChangeLCDutySet.get(k)
									.getMult());
							tLPRNPolAmntSchema.setState("0");
							tLPRNPolAmntSchema
									.setOperator(this.mGlobalInput.Operator);
							tLPRNPolAmntSchema.setMakeDate(strCurrentDate);
							tLPRNPolAmntSchema.setMakeTime(strCurrentTime);
							tLPRNPolAmntSchema.setModifyDate(strCurrentDate);
							tLPRNPolAmntSchema.setModifyTime(strCurrentTime);
							mLPRNPolAmntSet.add(tLPRNPolAmntSchema);
						}
					}
				}
				// 新续期数据
				mMap.put(mLPRNPolAmntSet, "DELETE&INSERT");
				// 数据存入P表，保全项目校验时用，在Confirm里再删除，其中Amnt存的是新的
				mMap.put(mLPPolSet, "DELETE&INSERT");
			} else {
				// 为了复核时不产生错误，同时为了做ER且确认了，但续期未抽档前又做ER能恢复到第一次做ER之前的Amnt，
				// 这里如果没有改变任何Amnt（相对于做第一次ER之前），则把所有LCPol数据复制到LPPol，确认时会删掉
				Reflections tReflections = new Reflections();
				LCPolDB tLCPolDB = new LCPolDB();
				tSql = "SELECT * FROM LCPol WHERE MainPolNo<>PolNo and AppFlag='1' and ContNo='?ContNo?'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
				LCPolSet tLCPolSet = new LCPolSet();
				tLCPolSet = tLCPolDB.executeQuery(sqlbv);
				if (tLCPolSet == null || tLCPolSet.size() <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorERDetailBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保单附加险信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LPPolSet tLPPolSet = new LPPolSet();
				LPPolSchema tLPPolSchema = null;
				for (int z = 1; z <= tLCPolSet.size(); z++) {
					tLPPolSchema = new LPPolSchema();
					tReflections.transFields(tLPPolSchema, tLCPolSet.get(z));
					tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPPolSet.add(tLPPolSchema);
				}
				// 数据存入P表，保全项目校验时用，在Confirm里再删除
				mMap.put(tLPPolSet, "DELETE&INSERT");
			}

			// 修改“个险保全项目表”相应信息
			mLPEdorItemSchema.setEdorState("3");
			mLPEdorItemSchema.setChgAmnt(mChangeAmnt); // 变动的保额，在getInputData函数里获得
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
			tError.moduleName = "PEdorERDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误！" + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据份数计算保额。结果存在mAmntByMult中。
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean getAmntByMult(String tPolNo, double tMult) {
		try {
			// 获得记入保额的险种下的所有GetDutyCode的CalCode
			mAmntByMult = 0.0;
			String tSql = "SELECT distinct OthCalCode"
					+ " FROM LMDutyGet a"
					+ " WHERE upper(AddAmntFlag)='Y'"
					+ " and exists(select 'X' from LCGet where GetDutyCode=a.GetDutyCode and PolNo='?tPolNo?')";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorERDetailBL";
				tError.functionName = "getAmntByMult";
				tError.errorMessage = "根据份数计算保额时查询算法编码错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				// 计算-->
				Calculator tCalculator = new Calculator();
				tCalculator.setCalCode(tSSRS.GetText(i, 1));
				// 组织要素
				tCalculator.addBasicFactor("Mult", String.valueOf(tMult)); // 份数
				// 结果
				String tResult = tCalculator.calculate();
				if (tResult == null || tResult.equals("")) {
					mAmntByMult = 0.0;
					CError tError = new CError();
					tError.moduleName = "PEdorERDetailBL";
					tError.functionName = "getAmntByMult";
					tError.errorMessage = "根据份数计算保额失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				mAmntByMult += Double.parseDouble(tResult);
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorERDetailBL";
			tError.functionName = "getAmntByMult";
			tError.errorMessage = "根据份数计算保额时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
