package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保全申请确认
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 2.0
 */
public class PGrpEdorAppConfirmBL {
private static Logger logger = Logger.getLogger(PGrpEdorAppConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	String mPayPrintParams = "";

	/** 全局数据 */
	private GlobalInput mGlobalInput;
	private String mGrpContNo = "";
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private LPGrpEdorItemSet mLPGrpEdorItemSet = new LPGrpEdorItemSet();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();

	public PGrpEdorAppConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 数据准备操作,校验该团体保全申请的明细信息是否完整
		if (!prepareData())
			return false;
		logger.debug("---End prepareData---");

		// 数据操作业务处理
		if (cOperate.equals("INSERT||EDORAPPCONFIRM")
				|| cOperate.equals("INSERT||GEDORAPPCONFIRM")) {
			VData pInputData = new VData();

			LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
			tLPEdorMainSchema.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());

			// mLPGrpEdorMainSchema.setEdorNo(mLPGrpEdorItemSet.get(i).getEdorNo());

			logger.debug("\nStart 集体下个人的申请确认 BL...");
			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			tLPEdorMainDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
			tLPEdorMainDB.setEdorAcceptNo(mLPGrpEdorMainSchema
					.getEdorAcceptNo());
			tLPEdorMainDB.setEdorState("1");// 保全申请未确认
			mLPEdorMainSet = tLPEdorMainDB.query();
			if (tLPEdorMainDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询个人保全失败！");
			}
			for (int i = 1; i <= mLPEdorMainSet.size(); i++) {
				logger.debug("处理个人保全主表");
				pInputData.clear();
				pInputData.addElement(mGlobalInput);
				pInputData.addElement(mLPGrpEdorMainSchema);
				pInputData.addElement(mLPEdorMainSet.get(i));
				EdorAppConfirmBL tEdorAppConfirmBL = new EdorAppConfirmBL();
				if (!tEdorAppConfirmBL.submitData(pInputData, cOperate)) {
					this.mErrors.copyAllErrors(tEdorAppConfirmBL.mErrors);
					return false;
				}
			}
			logger.debug("End 集体下个人的申请确认 BL");

			// if (!tEdorAppConfirmBL.submitData(pInputData, cOperate)) {
			// this.mErrors.copyAllErrors(tEdorAppConfirmBL.mErrors);
			// remark by PQ 2004-12-30
			// //继续执行集体单主表更新（tjj chg 1024），不是很清楚为什么增加这个逻辑
			// logger.debug("集体下个人的申请确认失败，继续执行集体单主表更新");
			// if (!tGEdorAppConfirmBL.submitData(pInputData, cOperate)) {
			// this.mErrors.copyAllErrors(tGEdorAppConfirmBL.mErrors);
			// return false;
			// }
			// return false;
			// }

			GEdorAppConfirmBL tGEdorAppConfirmBL = new GEdorAppConfirmBL();
			pInputData.clear();
			pInputData.addElement(mGlobalInput);
			pInputData.addElement(mLPGrpEdorMainSchema);

			logger.debug("Start 集体申请确认 BL...");
			if (!tGEdorAppConfirmBL.submitData(pInputData,
					"INSERT||EDORAPPCONFIRM")) {
				this.mErrors.copyAllErrors(tGEdorAppConfirmBL.mErrors);
				return false;
			}
			logger.debug("End 集体申请确认 BL");

			// //查询收费打印参数
			// if (!getPrintParams())return false;
			// logger.debug("---End getPrintData---");

		}

		return true;
	}

	/**
	 * 查询收费打印参数
	 * 
	 * @return
	 */
	private boolean getPrintParams() {

		// 判断是否有保全交费产生，如果有，取得相应的打印交费表需要的参数
		logger.debug("TJS数据提取**************");
		String prtOtherNo = mLPGrpEdorMainSchema.getEdorNo();
		String strSql = "select * from ljspay where otherno = '" + prtOtherNo
				+ "' and othernotype='3'";
		logger.debug("strSql :" + strSql);

		LJSPayDB tPayDB = new LJSPayDB();
		LJSPaySet tPaySet = tPayDB.executeQuery(strSql);
		if (tPaySet != null && tPaySet.size() == 1) {
			mPayPrintParams = prtOtherNo;
		}
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			// 得到保单号（避免依靠前台）
			mLPGrpEdorMainSchema = (LPGrpEdorMainSchema) mInputData
					.getObjectByObjectName("LPGrpEdorMainSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 校验该团体保全申请的明细信息是否完整
	 * 
	 * @param aLPGrpEdorMainSchema
	 * @return
	 */
	private boolean checkData(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		VerifyEndorsement tVer = new VerifyEndorsement();
		VData tVData = new VData();
		tVData.addElement(aLPGrpEdorItemSchema);

		if (!tVer.verifyEdorDetail(tVData, "VERIFY||GRPDETAIL")) {
			this.mErrors.copyAllErrors(tVer.mErrors);
			return false;
		} else {
			if (tVer.getResult() != null && tVer.getResult().size() > 0) {
				String tErr = "";
				for (int i = 0; i < tVer.getResult().size(); i++) {
					tErr = tErr + (String) tVer.getResult().get(i);
				}

				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "VerifyEndorsement";
				tError.functionName = "verifyEdorDetail";
				tError.errorMessage = tErr;
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return
	 */
	private boolean prepareData() {
		// 查询团体保全主表中，该保单处于申请状态的保全数据
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		String strSql = "select * from LPGrpEdorItem "
				+ " where EdorState='1' and GrpContNo='"
				+ mLPGrpEdorMainSchema.getGrpContNo() + "' and EdorNo='"
				+ mLPGrpEdorMainSchema.getEdorNo() + "' and ManageCom like '"
				+ mGlobalInput.ManageCom
				+ "%' order by EdorNo, MakeDate, MakeTime"; // 去掉按EdorValiDate排序
		logger.debug("sql:" + strSql);
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB
				.executeQuery(strSql);
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询批单号为" + mLPGrpEdorMainSchema.getEdorNo()
					+ "的保全项目时失败！");
			return false;
		}

		if (tLPGrpEdorItemSet == null)
			return false;

		String tEdorNo = null;
		for (int i = 1; i <= tLPGrpEdorItemSet.size(); i++) {
			LPGrpEdorItemSchema tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);

			// 校验该团体保全申请的明细信息是否完整
			if (!checkData(tLPGrpEdorItemSchema))
				return false;
			logger.debug("End 校验该团体保全申请的明细信息是否完整");

			// if (i==1)
			// {
			// mLPGrpEdorItemSet.add(tLPGrpEdorItemSchema);
			// tEdorNo = tLPGrpEdorItemSchema.getEdorNo();
			// }
			// else
			// {
			// //确保批单号在mLpGrpEdorMainSet中唯一，前提是在前面的Sql语句中排了序
			// if (tLPGrpEdorItemSchema.getEdorNo().equals(tEdorNo))
			// {
			// continue;
			// }
			//
			// mLPGrpEdorItemSet.add(tLPGrpEdorItemSchema);
			// tEdorNo = tLPGrpEdorItemSchema.getEdorNo();
			// }
		}
		logger.debug("团体保全主表记录数:" + mLPGrpEdorItemSet.size());

		return true;
	}

	/**
	 * 取得相应的打印交费表需要的参数
	 * 
	 * @return
	 */
	public String getPrtParams() {
		return mPayPrintParams;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PGrpEdorAppConfirmBL aPGrpEdorAppConfirmBL = new PGrpEdorAppConfirmBL();
		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPGrpEdorMainSchema.setGrpContNo("240440000000022");
		tLPGrpEdorMainSchema.setEdorNo("430440000000029");
		tLPGrpEdorMainSchema.setEdorAcceptNo("86440000000029");

		tInputData.addElement(tLPGrpEdorMainSchema);
		tInputData.addElement(tGlobalInput);

		aPGrpEdorAppConfirmBL.submitData(tInputData, "INSERT||EDORAPPCONFIRM");
	}

}
