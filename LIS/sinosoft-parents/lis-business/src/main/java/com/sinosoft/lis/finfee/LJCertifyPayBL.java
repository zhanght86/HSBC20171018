//LJCertifyPayBL.java

package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJCertifyGetAgentDB;
import com.sinosoft.lis.db.LJCertifyGetDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJCertifyGetAgentSchema;
import com.sinosoft.lis.schema.LJCertifyGetSchema;
import com.sinosoft.lis.vschema.LJCertifyGetAgentSet;
import com.sinosoft.lis.vschema.LJCertifyGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LJCertifyPayBL {
private static Logger logger = Logger.getLogger(LJCertifyPayBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private String tLimit = "";

	private String ActuGetNo = "";
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private String mLimit = "";
	/** 数据操作字符串 */
	private String mOperate;
	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private String mActuDrawerID = "";
	private String mActuDrawer = "";
	private LJCertifyGetAgentSchema mLJCertifyGetAgentSchema = new LJCertifyGetAgentSchema();
	private LJCertifyGetSchema mLJCertifyGetSchema = new LJCertifyGetSchema();

	public LJCertifyPayBL() {

	}

	public VData getResult() {
		return mResult;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		ActuGetNo = (String) tTransferData.getValueByName("ActuGetNo");
		mActuDrawerID = (String) tTransferData.getValueByName("DrawerID");
		mActuDrawer = (String) tTransferData.getValueByName("Drawer");
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		return true;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 获得业务数据；
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 数据效验
		if (!checkDate()) {
			return false;
		}
		// 获得打印数据
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJCertifyTempFeeBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		logger.debug("----------LJCertifyTempFeeBL End----------");
		return true;
	}

	private boolean checkDate() {
		String mManageCom = mGlobalInput.ManageCom.substring(0, 6);
		logger.debug("付费时管理机构取登陆机构的前六位::::" + mManageCom);
		String tSql = "select * from LJCertifyGet where actugetno='?ActuGetNo?' and managecom like concat('?mManageCom?','%')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ActuGetNo", ActuGetNo);
		sqlbv.put("mManageCom", mManageCom);
		LJCertifyGetSet tLJCertifyGetSet = new LJCertifyGetSet();
		LJCertifyGetDB tLJCertifyGetDB = new LJCertifyGetDB();
		tLJCertifyGetDB.setActuGetNo(ActuGetNo);
		tLJCertifyGetSet = tLJCertifyGetDB.executeQuery(sqlbv);

		if (tLJCertifyGetSet.size() == 0) {
			CError.buildErr(this, "未找到应付信息");
			return false;
		}

		String tEnterAccDate = tLJCertifyGetDB.getEnterAccDate();
		if (tEnterAccDate != null) {
			CError.buildErr(this, "此次付费已完成");
			return false;
		}

		LJCertifyGetAgentSet tLJCertifyGetAgentSet = new LJCertifyGetAgentSet();
		LJCertifyGetAgentDB tLJCertifyGetAgentDB = new LJCertifyGetAgentDB();
		tLJCertifyGetAgentDB.setActuGetNo(ActuGetNo);
		tLJCertifyGetAgentSet = tLJCertifyGetAgentDB.query();

		String ttEnterAccDate = tLJCertifyGetAgentDB.getEnterAccDate();
		if (tEnterAccDate != null) {
			CError.buildErr(this, "此次付费已完成");
			return false;
		}

		return true;
	}

	private boolean dealData() {
		LJCertifyGetSet tLJCertifyGetSet = new LJCertifyGetSet();
		LJCertifyGetDB tLJCertifyGetDB = new LJCertifyGetDB();
		tLJCertifyGetDB.setActuGetNo(ActuGetNo);
		tLJCertifyGetSet = tLJCertifyGetDB.query();

		LJCertifyGetSchema tLJCertifyGetSchema = tLJCertifyGetSet.get(1);
		tLJCertifyGetSchema.setEnterAccDate(CurrentDate);

		LJCertifyGetAgentSet tLJCertifyGetAgentSet = new LJCertifyGetAgentSet();
		LJCertifyGetAgentDB tLJCertifyGetAgentDB = new LJCertifyGetAgentDB();
		tLJCertifyGetAgentDB.setActuGetNo(ActuGetNo);
		tLJCertifyGetAgentSet = tLJCertifyGetAgentDB.query();

		for (int i = 1; i <= tLJCertifyGetAgentSet.size(); i++) {
			tLJCertifyGetAgentSet.get(i).setEnterAccDate(CurrentDate);
		}

		mLJCertifyGetSchema = tLJCertifyGetSchema;
		mLJCertifyGetSchema.setActualDrawer(mActuDrawer);
		mLJCertifyGetSchema.setActualDrawerID(mActuDrawerID);

		map.put(mLJCertifyGetSchema, "UPDATE");
		map.put(tLJCertifyGetAgentSet, "UPDATE");
		return true;
	}

	private boolean prepareOutputData() {
		try {

			mInputData.clear();

			mInputData.add(map);
			// mResult.add(map);//供前台界面使用
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJCertifyPayBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String args[]) {
		TransferData tTransferData = new TransferData();
		VData tVData = new VData();
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86210001";
		tVData.addElement(tGI);
		tTransferData.setNameAndValue("ActuGetNo", "370210000143802");
		tTransferData.setNameAndValue("DrawerID", "123");
		tTransferData.setNameAndValue("Drawer", "123");
		tVData.addElement(tTransferData);

		LJCertifyPayUI tLJCertifyPayUI = new LJCertifyPayUI();
		tLJCertifyPayUI.submitData(tVData, "VERIFY");

	}

}
