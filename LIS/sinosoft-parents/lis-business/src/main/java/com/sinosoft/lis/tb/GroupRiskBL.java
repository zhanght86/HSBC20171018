package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.vschema.LCDelPolLogSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-09-25
 */
public class GroupRiskBL {
private static Logger logger = Logger.getLogger(GroupRiskBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private MMap map = new MMap();
	TransferData tTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LCGrpPolSet mOLDLCGrpPolSet = new LCGrpPolSet();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCDelPolLogSet mLCDelPolLogSet = new LCDelPolLogSet();

	// @Constructor
	public GroupRiskBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false) {
			return false;
		}
		logger.debug("---getInputData---");

		// 校验传入的数据
		if (!mOperate.equals("DELETE||GROUPRISK")) {
			if (this.checkData() == false) {
				return false;
			}
			logger.debug("---checkData---");
		}

		// 根据业务逻辑对数据进行处理
		if (!mOperate.equals("DELETE||GROUPRISK")) {
			if (this.dealData() == false) {
				return false;
			}
		} else {
			if (this.deleteData() == false) {
				return false;
			}
		}

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");
		// 数据提交、保存
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start GroupRiskBL Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---commitData---");

		return true;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean prepareSubmitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false) {
			return false;
		}
		logger.debug("---getInputData---");

		// 根据业务逻辑对数据进行处理
		if (!mOperate.equals("DELETE||GROUPRISK")) {
			if (this.dealData() == false) {
				return false;
			}
		} else {
			if (this.deleteData() == false) {
				return false;
			}
		}

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {

		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// 集体合同
		mLCGrpContSchema.setSchema((LCGrpContSchema) mInputData
				.getObjectByObjectName("LCGrpContSchema", 0));

		// 集体险种
		mLCGrpPolSet.set((LCGrpPolSet) mInputData.getObjectByObjectName(
				"LCGrpPolSet", 0));
		tTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mLCGrpContSchema == null || mLCGrpPolSet.size() == 0) {
			// @@错误处理
			buildError("getInputData", "在接受数据时没有得到集体合同或者集体险种!");
			return false;
		}
		LCGrpContDB mLCGrpContDB = new LCGrpContDB();
		mLCGrpContDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		if (mLCGrpContDB.getInfo() == false) {
			// @@错误处理
			buildError("getInputData", "没有查到该集体合同!");
			return false;
		}
		mLCGrpContSchema.setSchema(mLCGrpContDB);

		return true;
	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		if (!mOperate.equals("INSERT||GROUPRISK")
				&& !mOperate.equals("DELETE||GROUPRISK")) {
			buildError("getInputData", "只能新增或者删除!");
			return false;

		}
		if (mOperate.equals("INSERT||GROUPRISK")) {
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
			tLCGrpPolDB.setRiskCode(mLCGrpPolSet.get(1).getRiskCode());
			if (tLCGrpPolDB.getCount() > 0) {
				buildError("checkData", "该集体合同下已经添加过该险种!");
				return false;
			}

			String tSQL = "";
			ExeSQL exeSql = new ExeSQL();
			SSRS tmpSSRS = new SSRS();

			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSchema tLMRiskAppSchema = new LMRiskAppSchema();
			tLMRiskAppDB.setRiskCode(mLCGrpPolSet.get(1).getRiskCode());
			if (tLMRiskAppDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
				buildError("checkData", "LMRiskApp表查询失败!");
				return false;
			}
			tLMRiskAppSchema = tLMRiskAppDB.getSchema();
			// 如果添加的险种是主险时,校验该险种是否可以与该单已经原来有的主险险种搭配
			if (tLMRiskAppSchema.getSubRiskFlag().toUpperCase().equals("M")) {
				tSQL = "select grppolno,riskcode from lcgrppol where grpcontno='"
						+ mLCGrpContSchema.getGrpContNo()
						+ "'"
						+ " and riskcode in (select riskcode from lmriskapp where subriskflag='M')";
				tmpSSRS = exeSql.execSQL(tSQL);
				if (tmpSSRS.getMaxRow() >= 1) {
					for (int n = 1; n <= tmpSSRS.getMaxRow(); n++) {
						String tERiskCode = tmpSSRS.GetText(n, 2);
						String sSQL = " select * from lmriskrela where  relacode='02'"
								+ " and riskcode='"
								+ tERiskCode
								+ "' "
								+ " and relariskcode in('"
								+ tLMRiskAppSchema.getRiskCode()
								+ "','000000') ";
						SSRS ttSSRS = new SSRS();
						ttSSRS = exeSql.execSQL(sSQL);
						if (ttSSRS.getMaxRow() <= 0) {
							buildError("checkData", "该投保单下已经录入其它主险["
									+ tERiskCode + "]，不能再录入此主险["
									+ tLMRiskAppSchema.getRiskCode() + "]！！");
							return false;
						}
					}
				}
			}
			// 如果添加的险种是附加险时,校验可以搭配该附加险种的主险是否已经录入
			if (tLMRiskAppSchema.getSubRiskFlag().toUpperCase().equals("S")) {
				tSQL = "select * from lcgrppol where grpcontno='"
						+ mLCGrpContSchema.getGrpContNo()
						+ "' and riskcode in (select riskcode from lmriskrela where "
						+ " relacode='01' and relariskcode='"
						+ tLMRiskAppSchema.getRiskCode() + "')";
				try {
					tmpSSRS = exeSql.execSQL(tSQL);
					if (tmpSSRS == null || tmpSSRS.getMaxRow() < 1) {
						buildError("checkData", "请先录入此附加险对应的主险！");
						return false;
					}
				} catch (Exception ex) {
					buildError("checkData", ex.toString());
					return false;
				}
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
		// 产生集体投保单号码
		if (mOperate.equals("INSERT||GROUPRISK")) {

			String tLimit = PubFun.getNoLimit(mLCGrpContSchema.getManageCom());
			String tNo = PubFun1.CreateMaxNo("GrpProposalNo", tLimit);
			mLCGrpPolSet.get(1).setGrpPolNo(tNo); // 如果是新增
			mLCGrpPolSet.get(1).setGrpProposalNo(tNo);
			mLCGrpPolSet.get(1).setPrtNo(mLCGrpContSchema.getPrtNo());
			mLCGrpPolSet.get(1).setGrpContNo(mLCGrpContSchema.getGrpContNo());
			mLCGrpPolSet.get(1).setSaleChnl(mLCGrpContSchema.getSaleChnl());
			mLCGrpPolSet.get(1).setManageCom(mLCGrpContSchema.getManageCom());
			mLCGrpPolSet.get(1).setAgentCom(mLCGrpContSchema.getAgentCom());
			mLCGrpPolSet.get(1).setAgentType(mLCGrpContSchema.getAgentType());
			mLCGrpPolSet.get(1).setAgentCode(mLCGrpContSchema.getAgentCode());
			mLCGrpPolSet.get(1).setAgentGroup(mLCGrpContSchema.getAgentGroup());
			mLCGrpPolSet.get(1).setCustomerNo(mLCGrpContSchema.getAppntNo());
			mLCGrpPolSet.get(1).setAddressNo(mLCGrpContSchema.getAddressNo());
			mLCGrpPolSet.get(1).setGrpName(mLCGrpContSchema.getGrpName());
			logger.debug("核保测算录入");
			// 管理费用率和销售费用率
			logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@管理费用率:"
					+ mLCGrpContSchema.getStandbyFlag2());
			logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@销售费用率:"
					+ mLCGrpContSchema.getStandbyFlag3());

			mLCGrpPolSet.get(1).setAppFlag("0");
			mLCGrpPolSet.get(1).setUWFlag("0");
			mLCGrpPolSet.get(1).setApproveFlag("0");
			mLCGrpPolSet.get(1).setModifyDate(PubFun.getCurrentDate());
			mLCGrpPolSet.get(1).setModifyTime(PubFun.getCurrentTime());
			mLCGrpPolSet.get(1).setOperator(mGlobalInput.Operator);
			mLCGrpPolSet.get(1).setMakeDate(PubFun.getCurrentDate());
			mLCGrpPolSet.get(1).setMakeTime(PubFun.getCurrentTime());
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(mLCGrpPolSet.get(1).getRiskCode());
			if (tLMRiskDB.getInfo() == false) {
				// @@错误处理
				buildError("getInputDate", "没有查到险种信息!");
				return false;
			}
			tTransferData.setNameAndValue("RiskName", tLMRiskDB.getRiskName());
			map.put(mLCGrpPolSet, "INSERT");
		}

		return true;
	}

	/**
	 * 删除传入的险种
	 * 
	 * @param: 无
	 * @return: void
	 */
	private boolean deleteData() {

		for (int i = 1; i <= mLCGrpPolSet.size(); i++) {
			String riskcode = mLCGrpPolSet.get(i).getRiskCode();
			String GrpContNo = mLCGrpPolSet.get(i).getGrpContNo();
			String wherepart = "RiskCode ='" + riskcode + "' and GrpContNo = '"
					+ GrpContNo + "'";
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setGrpContNo(GrpContNo);
			tLCPolDB.setRiskCode(riskcode);
			int polCount = tLCPolDB.getCount();
			if (tLCPolDB.mErrors.needDealError()) {
				// @@错误处理
				buildError("deleteData", "查询个人险种保单时失败!");
				return false;
			}
			if (polCount > 0) {
				buildError("deleteData", "险种" + riskcode
						+ "下还有个人保单，请先删除险种下个人保单再删除险种！");
				return false;
			}
			LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
			tLCContPlanRiskDB.setGrpContNo(GrpContNo);
			tLCContPlanRiskDB.setRiskCode(riskcode);

			int planRiskCount = tLCContPlanRiskDB.getCount();
			if (tLCContPlanRiskDB.mErrors.needDealError()) {
				// @@错误处理
				buildError("deleteData", "查询险种信息（保险计划）时失败!");
				return false;

			}
			if (planRiskCount > 0) {
				// @@错误处理
				buildError("deleteData", "险种" + riskcode
						+ "还在险种信息（保险计划）中使用，请先从险种信息（保险计划）中删除该险种！");
				return false;
			}

			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpContNo(GrpContNo);
			tLCGrpPolDB.setRiskCode(riskcode);
			LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
			if (tLCGrpPolSet == null || tLCGrpPolSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpContBL";
				tError.functionName = "dealData";
				tError.errorMessage = "请您确认：要删除的集体险种代码传入错误!";
				this.mErrors.addOneError(tError);
				return false;
			}
			LCDelPolLogSchema mLCDelPolLog = new LCDelPolLogSchema();
			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolSchema.setSchema(tLCGrpPolSet.get(1));
			mLCDelPolLog.setOtherNo(tLCGrpPolSchema.getGrpPolNo());
			mLCDelPolLog.setOtherNoType("1");
			mLCDelPolLog.setPrtNo(tLCGrpPolSchema.getPrtNo());
			if (tLCGrpPolSchema.getAppFlag().equals("1")) {
				mLCDelPolLog.setIsPolFlag("1");
			} else {
				mLCDelPolLog.setIsPolFlag("0");
			}
			mLCDelPolLog.setOperator(mGlobalInput.Operator);
			mLCDelPolLog.setManageCom(mGlobalInput.ManageCom);
			mLCDelPolLog.setMakeDate(PubFun.getCurrentDate());
			mLCDelPolLog.setMakeTime(PubFun.getCurrentTime());
			mLCDelPolLog.setModifyDate(PubFun.getCurrentDate());
			mLCDelPolLog.setModifyTime(PubFun.getCurrentTime());
			mLCDelPolLogSet.add(mLCDelPolLog);
			if (i == mLCGrpPolSet.size()) {
				map.put(mLCDelPolLogSet, "INSERT");
			}
			map.put("insert into LOBGrpPol (select * from LCGrpPol where "
					+ wherepart + ")", "INSERT");
			map.put("delete from LCGrpPol where " + wherepart, "DELETE");
			// map.put("delete from LCPol where "+wherepart,"DELETE");
		}
		if (mLCGrpPolSet.size() > 0) {
			String fromPart = "from LCPol where GrpContNo='"
					+ mLCGrpPolSet.get(1).getGrpContNo() + "')";
			map
					.put(
							"update LCGrpCont set "
									+ "Prem=(select SUM(Prem) "
									+ fromPart
									+ ", Amnt=(select SUM(Amnt) "
									+ fromPart
									+ ", SumPrem=(select SUM(SumPrem) "
									+ fromPart
									+ ", Mult=(select SUM(Mult) "
									+ fromPart
									+ ", Peoples2=(select sum(Peoples) from LCCont where GrpContNo= '"
									+ mLCGrpPolSet.get(1).getGrpContNo() + "')"
									+ " where GrpContNo='"
									+ mLCGrpPolSet.get(1).getGrpContNo() + "'",
							"UPDATE");
		}

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mResult.clear();
		mResult.add(mLCGrpPolSet);
		mResult.add(tTransferData);
		mResult.add(map);
		mInputData.clear();
		mInputData.add(map);
	}

	private void buildError(String tFunName, String tMsg) {
		// @@错误处理
		CError tError = new CError();
		tError.moduleName = "GrpContBL";
		tError.functionName = tFunName;
		tError.errorMessage = tMsg;
		this.mErrors.addOneError(tError);
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
