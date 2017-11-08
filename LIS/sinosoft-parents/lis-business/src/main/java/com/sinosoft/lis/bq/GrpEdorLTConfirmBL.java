package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.pubfun.ContCancel;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全申请确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class GrpEdorLTConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorLTConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	boolean newaddrFlag = false;

	public GrpEdorLTConfirmBL() {
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
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据准备操作
		if (!prepareData()) {
			return false;
		}
		logger.debug("---End prepareData---");
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean prepareOutputData() {

		mResult.clear();
		mResult.add(map);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {

		String tGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();
		String tEdorNo = mLPGrpEdorItemSchema.getEdorNo();
		String tEdorType = mLPGrpEdorItemSchema.getEdorType();

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		String sql = "select * from lcgrppol where grpcontno='"
				+ tGrpContNo
				+ "'"
				+ " and riskcode in (select riskcode from lcpol where polno in "
				+ " (select polno from lpedoritem where edorno='" + tEdorNo
				+ "'" + " and EdorType='" + tEdorType + "'))";
		tLCGrpPolSet = tLCGrpPolDB.executeQuery(sql);
		if (tLCGrpPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团体险种表失败！");
			return false;
		}

		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			ContCancel tContCancel = new ContCancel();
			MMap tMMap = new MMap();
			tMMap = tContCancel.prepareGrpPolData(tLCGrpPolSet.get(i)
					.getGrpPolNo(), tEdorNo);
			if (tContCancel.mErrors.needDealError() || tMMap == null) {
				CError.buildErr(this, "准备团体保单数据失败！");
				return false;
			}
			map.add(tMMap);
		}
		return true;

	}

	public TransferData getReturnTransferData() {
		return null;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
