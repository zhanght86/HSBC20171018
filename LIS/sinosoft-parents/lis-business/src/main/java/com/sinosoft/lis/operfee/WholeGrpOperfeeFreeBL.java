//WholeGrpOperfeeFeeBL.java

package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContHangUpStateDB;
import com.sinosoft.lis.pubfun.ContHangUpBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContHangUpStateSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class WholeGrpOperfeeFreeBL {
private static Logger logger = Logger.getLogger(WholeGrpOperfeeFreeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 统一更新日期，时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	// 全局变量
	private String mOperate;
	private MMap mMap = new MMap();
	private String mGrpContNo;
	private GlobalInput tGI = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private VData mInputData;
	private String mOperator;

	// 业务处理相关变量
	public WholeGrpOperfeeFreeBL() {
	}

	private boolean getInputData(VData cInputData) {

		tGI = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		logger.debug("!!!!!!!!!!!!GrpContNo=" + mGrpContNo);
		mOperator = tGI.Operator;

		if (tGI == null || mGrpContNo == null || mGrpContNo == "") {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "HoleGrpOperfeeFreeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;

		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mInputData, "")) {
			// @@错误处理
			CError.buildErr(this, "传入参数为空");

		}
		logger.debug("over");
		return true;
	}

	private boolean dealData() {
		if (this.mOperate.equals("")) {
			// 查询保单挂起标志
			String sqlStr = "select HangUpType from lcgrpconthangupstate where grpcontno = '"
					+ mGrpContNo + "'";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlStr);

			if (tSSRS.MaxRow < 1) {
				CError tError = new CError();
				tError.moduleName = "HoleGrpOperfeeFreeBL";
				tError.functionName = "dealData";
				tError.errorMessage = "该保单未整张挂起";
				this.mErrors.addOneError(tError);
				return false;
			}

			String tHangUpType = tSSRS.GetText(1, 1);

			logger.debug(tHangUpType);
			// 判断保单挂起状态；
			ContHangUpBL tContHangUpBL = new ContHangUpBL(tGI);
			if (tContHangUpBL.checkGrpHangUpState(mGrpContNo, tHangUpType)) {
				CError tError = new CError();
				tError.moduleName = "HoleGrpOperfeeFreeBL";
				tError.functionName = "dealData";
				tError.errorMessage = "该保单未整张挂起";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 解挂续期；
			LCGrpContHangUpStateSchema tLCGrpContHangUpStateSchema = new LCGrpContHangUpStateSchema();

			LCGrpContHangUpStateDB tLGrpCContHangUpStateDB = new LCGrpContHangUpStateDB();
			String tstr = "select * from LCGrpContHangUpState where grpcontno = '"
					+ mGrpContNo + "'";
			tLCGrpContHangUpStateSchema = (tLGrpCContHangUpStateDB
					.executeQuery(tstr)).get(1);

			tLCGrpContHangUpStateSchema.setRNFlag("0");
			tLCGrpContHangUpStateSchema.setOperator(mOperator);

			MMap tMap = new MMap();
			tMap = tContHangUpBL.updGrpHangUpFlag(tLCGrpContHangUpStateSchema);
			mMap.add(tMap);

			boolean all = true;
			for (int i = tLCGrpContHangUpStateSchema.getFieldIndex("NBFlag"); i <= tLCGrpContHangUpStateSchema
					.getFieldIndex("RNFlag"); i++) {
				logger.debug(tLCGrpContHangUpStateSchema.getV(i));
				if (!tLCGrpContHangUpStateSchema.getV(i).equals("0")) {
					all = false;
					break;
				}
			}

			if (all == true) {
				// 张团体保单整体解挂；
				MMap ttMap = new MMap();
				ttMap = tContHangUpBL.unHangUpGrpCont(mGrpContNo, tHangUpType);
				mMap.add(ttMap);
				logger.debug("整张解挂！");
			}
		} else {
			return false;
		}

		return true;
	}

	private boolean prepareOutputData() {
		mInputData = new VData();
		try {
			mInputData.add(mMap); // 更新团体保单挂起状态表
			logger.debug("prepareOutputData:");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpOperfeeFreeBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "zhangtao";
		tGlobalInput.ManageCom = "86";

		WholeGrpOperfeeFreeBL tWB = new WholeGrpOperfeeFreeBL();
		MMap tMap = null;
		String tGrpContNo = "880000007710";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", tGrpContNo);
		String tOperate = "";

		VData tInputData = new VData();
		tInputData.add(tGlobalInput);
		tInputData.add(tTransferData);

		tWB.submitData(tInputData, tOperate);

	}
}
