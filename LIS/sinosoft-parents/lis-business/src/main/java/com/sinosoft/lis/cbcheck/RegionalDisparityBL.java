package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LATreeDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDUWDifferenceBSchema;
import com.sinosoft.lis.vschema.LATreeSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LDUWDifferenceBSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RegionalDisparityBL {
private static Logger logger = Logger.getLogger(RegionalDisparityBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	/** 机构代码 */
	private String mManageCom;
	/** 业务员代码 */
	private String mAgentCode;
	/** 业务员类别 */
	private String mUWClass;
	/** 差异化级别 */
	private String mUWLevel;
	/** 当前操作员 */
	private String mOperate;
	/** 当前日期 */
	private String mCurrentDate = PubFun.getCurrentDate();
	/** 当前时间 */
	private String mCurrentTime = PubFun.getCurrentTime();
	private MMap mMMap = new MMap();

	// private LATreeSet mLATreeSet = new LATreeSet();
	private ExeSQL mExeSQL = new ExeSQL();

	public RegionalDisparityBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!checkDate()) {
			return false;
		}
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		// 准备返回的数据
		prepareOutputData();

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this, "数据提交失败!");
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperate = tGlobalInput.Operator;
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			CError.buildErr(this, "传输数据失败！");
			return false;
		}
		mUWLevel = (String) mTransferData.getValueByName("UWLevel");
		mUWClass = (String) mTransferData.getValueByName("UWClass");
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		return true;
	}

	/**
	 * 校验数据合法性
	 * */
	private boolean checkDate() {
		if (mManageCom == null || "".equals(mManageCom)) {
			CError.buildErr(this, "管理机构为空，无法修改！");
			return false;
		}
		return true;
	}

	/**
	 * 修改业务员差异化核保级别 此处处理逻辑为如果只录了业务员代码则修改该业务员的差异化级别
	 * 如果录入了机构代码，则对该机构下的所有业务员的差异化级别进行修改
	 */
	private boolean dealData() {
		// 先初始化差异化核保数据备份表
		LDUWDifferenceBSet tLDUWDifferenceBSet = new LDUWDifferenceBSet();
		LDCodeDB tLDCodeDB = new LDCodeDB();
		LDCodeSet tLDCodeSet = new LDCodeSet();
		String tQuerySql = "select * from ldcode where codetype='station' and code like "
				+ "concat('?code?','%')" + "";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tQuerySql);
		sqlbv.put("code", mManageCom);
		tLDCodeSet = tLDCodeDB.executeQuery(sqlbv);
		if (tLDCodeSet.size() == 0) {
			CError.buildErr(this, "未查询到代码为" + mAgentCode + "的业务员信息");
			return false;
		} else {
			for (int i = 1; i <= tLDCodeSet.size(); i++) {
				LDUWDifferenceBSchema tLDUWDifferenceBSchema = new LDUWDifferenceBSchema();
				String tSerielNo = PubFun1.CreateMaxNo("DiffSerieno", 10);
				tLDUWDifferenceBSchema.setSerialNo(StrTool.getYear()
						+ StrTool.getMonth() + tSerielNo);
				tLDUWDifferenceBSchema.setDiffType("0");//0-机构  1-业务员
				tLDUWDifferenceBSchema
						.setManageCom(tLDCodeSet.get(i).getCode());
				tLDUWDifferenceBSchema
						.setManaUWType(tLDCodeSet.get(i).getComCode());
				tLDUWDifferenceBSchema.setUWLevel(tLDCodeSet.get(i)
						.getOtherSign());
				tLDUWDifferenceBSchema.setOperator(mOperate);
				tLDUWDifferenceBSchema.setMakeDate(getDateOrTime("1",
						tLDCodeSet.get(i).getCode()));
				tLDUWDifferenceBSchema.setMakeTime(getDateOrTime("2",
						tLDCodeSet.get(i).getCode()));
				tLDUWDifferenceBSchema.setMakeDate2(mCurrentDate);
				tLDUWDifferenceBSchema.setMakeTime2(mCurrentTime);
				tLDUWDifferenceBSet.add(tLDUWDifferenceBSchema);

				tLDCodeSet.get(i).setOtherSign(mUWLevel);
				tLDCodeSet.get(i).setComCode(mUWClass);
				tLDCodeSet.get(i).setCodeAlias(
						mCurrentDate + "|" + mCurrentTime);
			}
		}

		if (tLDUWDifferenceBSet.size() > 0) {
			mMMap.put(tLDUWDifferenceBSet, "INSERT");
		}
		if (tLDCodeSet.size() > 0) {
			mMMap.put(tLDCodeSet, "UPDATE");
		}
		
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mResult.clear();
		mResult.add(mMMap);
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 获得ldcode表中存放的维护时间加日期 存放形式为 yyyy-mm-dd|HH-MM-SS
	 * 
	 * @param type
	 *            :1-获得日期 2-获得时间
	 * 
	 * @return String
	 * */
	private String getDateOrTime(String type, String tManageCom) {
		String retStr = "";
		String str = "";
		String tQuerySql = "select codealias from ldcode where codetype='station' and code='"
				+ "?code?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tQuerySql);
		sqlbv1.put("code", tManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		str = tExeSQL.getOneValue(sqlbv1);
		// 判断查询出的日期时间格式是否合法以防抛出Exception
		if (str.indexOf("|") != -1) {
			if ("1".equals(type)) {
				retStr = str.substring(0, str.indexOf("|"));
			} else if ("2".equals(type)) {
				retStr = str.substring(str.indexOf("|") + 1);
			}
		}
		return retStr;
	}

	private String getManageComLevel(String tManageCom) {
		String tLevel = "";
		String tSql = "select comcode from ldcode where codetype='station' and code='"
				+ "?code?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("code", tManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		tLevel = tExeSQL.getOneValue(sqlbv2);
		return tLevel;
	}
}
