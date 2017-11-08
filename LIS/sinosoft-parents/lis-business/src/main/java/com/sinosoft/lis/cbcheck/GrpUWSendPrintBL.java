package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统个人单人工核保送打印队列部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class GrpUWSendPrintBL {
private static Logger logger = Logger.getLogger(GrpUWSendPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private String mManageCom;

	/** 业务处理相关变量 */

	private String mGrpContNo = "";
	private String mOldPolNo = "";
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 打印队列表* */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	/** 打印处理函数* */
	private PrintManagerBL tPrintManagerBL = new PrintManagerBL();

	public GrpUWSendPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---GrpUWSendPrintBL getInputData---");

		mOldPolNo = mLOPRTManagerSchema.getOtherNo();
		mGrpContNo = mLOPRTManagerSchema.getOtherNo();

		// 校验
		if (!checkPrint()) {
			return false;
		}
		logger.debug("---GrpUWSendPrintBL checkData---");
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("---GrpUWSendPrintBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---GrpUWSendPrintBL prepareOutputData---");

		// 数据提交

		logger.debug("Start GrpUWSendPrintBL Submit...");
		for (int i = 1; i <= mLOPRTManagerSet.size(); i++) {
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema = mLOPRTManagerSet.get(i);

			mInputData.clear();
			mInputData.add(mGlobalInput);
			mInputData.add(tLOPRTManagerSchema);

			if (!tPrintManagerBL.submitData(mInputData, "REQUEST")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPrintManagerBL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpUWSendPrintBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		logger.debug("---UWSendPrintBL commitData---");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		if (dealOnePol() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		// 健康信息
		if (preparePrint() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperate = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mLOPRTManagerSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入数据!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验是否打印
	 * 
	 * @return
	 */
	private boolean checkPrint() {
		// 生成流水号
		String tsql = "select distinct 1 from LOPRTManager where otherno = '"
				+ mGrpContNo
				+ "' and code = '"
				+ mLOPRTManagerSchema.getCode().trim()
				+ "' and stateflag = '0' and prtseq = (select max(prtseq) from LOPRTManager where otherno = '"
				+ mGrpContNo + "' and code = '"
				+ mLOPRTManagerSchema.getCode().trim() + "')";
		logger.debug(tsql);

		ExeSQL tExeSQL = new ExeSQL();
		String tflag = tExeSQL.getOneValue(tsql);

		if (tflag.trim().equals("1")) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "checkPrint";
			tError.errorMessage = "已在打印队列尚未打印!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLOPRTManagerSchema.getCode().equals("15")) {
			tsql = "select distinct 1 from LOPRTManager where otherno = '"
					+ mGrpContNo + "' and code = '"
					+ mLOPRTManagerSchema.getCode().trim() + "'";
			logger.debug(tsql);

			tExeSQL = new ExeSQL();
			tflag = tExeSQL.getOneValue(tsql);
			logger.debug("tflag" + tflag);
			if (tflag.trim().equals("1")) {
				logger.debug("tflag" + tflag);
				CError tError = new CError();
				tError.moduleName = "UWSendPrintBL";
				tError.functionName = "checkPrint";
				tError.errorMessage = "在签单处已发缴费催办通知书!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 校验是不是主险
	 * 
	 * @return
	 */

	// 集体核保通知书
	private boolean checkGrpMain() {

		// 发首期交费通知书的时候需要判断，该投保单是否确实到帐的金额不足

		// 计算以交保费

		double tTemperFee = 0.0;
		String tRealPayMoney = "";

		String tStrSQL;
		ExeSQL tExeSQL = new ExeSQL();

		tStrSQL = "select sum(PayMoney) from LJTempFee where "
				+ " TempFeeType in ('1','5')and otherno in (select prtno from lcGrpCont where GrpContNo='"
				+ mLOPRTManagerSchema.getOtherNo() + "')";

		tRealPayMoney = tExeSQL.getOneValue(tStrSQL);

		logger.debug("实际交的金额:" + tRealPayMoney);

		if (tRealPayMoney == null || tRealPayMoney.trim().equals("")) {
			tTemperFee = 0.0;
		} else {
			tTemperFee = Double.parseDouble(tRealPayMoney);
		}

		logger.debug("实际交的金额:" + tTemperFee);
		// 计算应交保费
		double tPrem = 0.0;
		String tShouldPayMoney = "";
		tStrSQL = "select sum(prem) from LCGrpCont where GrpContNo='"
				+ mLOPRTManagerSchema.getOtherNo() + "'";

		tShouldPayMoney = tExeSQL.getOneValue(tStrSQL);

		logger.debug("应该交的金额:" + tShouldPayMoney);

		if (tShouldPayMoney == null || tShouldPayMoney.trim().equals("")) {
			tPrem = 0.0;
		} else {
			tPrem = Double.parseDouble(tShouldPayMoney);
		}

		// 实交与应交进行比较
		if (tTemperFee >= tPrem) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "checkMain";
			tError.errorMessage = "该投保单到帐的金额已足,不必再发发首期交费通知书";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;

	}

	/**
	 * 校验是不是主险
	 * 
	 * @return
	 */
	private boolean checkMain15() {

		// 计算已交保费
		ExeSQL tExeSQL = new ExeSQL();
		double tTemperFee = 0.0;
		String tRealPayMoney = "";
		String tStrSQL = "select sum(PayMoney) from LJTempFee where "
				+ "( "
				+ " (TempFeeType in ('0','6','1')and otherno in (select contno from LCGrpCont where ContNo='"
				+ mLOPRTManagerSchema.getOtherNo()
				+ "'))"
				+ " or"
				+ " (TempFeeType in ('1','5')and otherno in (select prtno from LCGrpCont where contno='"
				+ mLOPRTManagerSchema.getOtherNo() + "'))" + ")"
				+ "and EnterAccDate is not null";

		tRealPayMoney = tExeSQL.getOneValue(tStrSQL);

		logger.debug("实际交的金额:" + tRealPayMoney);
		if (tRealPayMoney == null || tRealPayMoney.trim().equals("")) {
			tTemperFee = 0.0;
		} else {
			tTemperFee = Double.parseDouble(tRealPayMoney);
		}

		logger.debug("实际交的金额:" + tTemperFee);
		// 计算应交保费
		double tPrem = 0.0;
		String tShouldPayMoney = "";
		tStrSQL = "select prem from LCGrpCont where ContNo='"
				+ mLOPRTManagerSchema.getOtherNo() + "'";

		tShouldPayMoney = tExeSQL.getOneValue(tStrSQL);

		logger.debug("应该交的金额:" + tShouldPayMoney);

		if (tShouldPayMoney == null || tShouldPayMoney.trim().equals("")) {
			tPrem = 0.0;
		} else {
			tPrem = Double.parseDouble(tShouldPayMoney);
		}

		// 实交与应交进行比较
		if (tTemperFee >= tPrem) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "checkMain";
			tError.errorMessage = "该投保单到帐的金额已足,不必再发缴费通知书";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备打印信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePrint() {

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLOPRTManagerSchema.getOtherNo());
		if (tLCGrpContDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "没有保单信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema = tLCGrpContDB.getSchema();

		mLOPRTManagerSchema.setAgentCode(tLCGrpContSchema.getAgentCode());
		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
		mLOPRTManagerSchema.setManageCom(tLCGrpContSchema.getManageCom());

		// 通知书类型
		if (mLOPRTManagerSchema.getOtherNoType().equals("00")) {
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);
		}
		if (mLOPRTManagerSchema.getOtherNoType().equals("01")) {
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL);
		}
		if (mLOPRTManagerSchema.getOtherNoType().equals("02")) {
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);
		}
		if (mLOPRTManagerSchema.getOtherNoType().equals("03")) {
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_EDOR);
		}

		// 单据类型
		if (mLOPRTManagerSchema.getCode().equals("06")) {
			// mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_URGE);
			if (prepareURGE(mLOPRTManagerSchema) == false) {
				return false;
			}
		}
		if (mLOPRTManagerSchema.getCode().equals("57")) {
			if (!checkGrpMain()) {
				return false;
			}

			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_GRPFIRSTPAY);

			mLOPRTManagerSet.add(mLOPRTManagerSchema);
		}
		if (mLOPRTManagerSchema.getCode().equals("15")) {
			if (!checkMain15()) {
				return false;
			}

			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_URGE_PB);

			mLOPRTManagerSet.add(mLOPRTManagerSchema);
		}
		return true;
	}

	/**
	 * 取催发数据
	 */
	private boolean prepareURGE(LOPRTManagerSchema tLOPRTManagerSchema) {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("URGEInterval");

		if (tLDSysVarDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "prepareURGE";
			tError.errorMessage = "没有描述催发间隔!";
			this.mErrors.addOneError(tError);
			return false;
		}

		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());

		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);

		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		String tsql = "select * from loprtmanager where otherno = '"
				+ tLOPRTManagerSchema.getOtherNo()
				+ "' and code in ('03','05','07') and stateflag = '2' and donedate = "
				+ tDate;
		tLOPRTManagerSet = tLOPRTManagerDB.executeQuery(tsql);
		if (tLOPRTManagerSet.size() > 0) {
			for (int i = 1; i <= tLOPRTManagerSet.size(); i++) {
				LOPRTManagerSchema t2LOPRTManagerSchema = new LOPRTManagerSchema();
				t2LOPRTManagerSchema = tLOPRTManagerSet.get(i);

				mLOPRTManagerSchema.setCode(t2LOPRTManagerSchema.getCode());
				mLOPRTManagerSet.add(mLOPRTManagerSchema);
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		// mInputData.clear();
		// mInputData.add( mGlobalInput);
		// mInputData.add( mLOPRTManagerSet );
	}
}
