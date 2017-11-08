package com.sinosoft.workflow.bq;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class EdorManuDataDeal {

	public CErrors mErrors = new CErrors();

	private TransferData mTransferData = new TransferData();
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;

	private String mEdorNo;
	private String mContNo;

	public EdorManuDataDeal() {

	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(TransferData tTransferData) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		// mTransferData = (TransferData)
		// cInputData.getObjectByObjectName("TransferData", 0);
		mTransferData = tTransferData;
		// 获得业务数据
		if (mTransferData == null) {
			CError.buildErr(this, "前台传输业务数据失败!");
			return false;
		}
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!");
			return false;
		}
		mActivityID = (String) mTransferData.getValueByName("ActivityID");
		if (mActivityID == null) {
			CError.buildErr(this, "前台传输业务数据中ActivityIDD失败!");
			return false;
		}
		return true;
	}

	public boolean submitData(TransferData tTransferData) {

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(tTransferData))
			return false;
		// 进行业务处理
		if (!dealData())
			return false;

		return true;
	}

	private boolean dealData() {
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		tLWMissionDB.setActivityID(mActivityID);

		if (!tLWMissionDB.getInfo()) {
			CError.buildErr(this, "查询工作流失败!");
			return false;
		}
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		tLWMissionSchema = tLWMissionDB.getSchema();

		if ("0000000305".equals(mActivityID)) {
			mEdorNo = tLWMissionSchema.getMissionProp9();
			mContNo = tLWMissionSchema.getMissionProp2();
		}
		if ("0000000314".equals(mActivityID)) {
			mEdorNo = tLWMissionSchema.getMissionProp15();
			mContNo = tLWMissionSchema.getMissionProp2();
		}
		if ("0000000332".equals(mActivityID)) {
			mEdorNo = tLWMissionSchema.getMissionProp8();
			mContNo = tLWMissionSchema.getMissionProp2();
		}
		if ("0000000330".equals(mActivityID)) {
			mEdorNo = tLWMissionSchema.getMissionProp4();
			mContNo = tLWMissionSchema.getMissionProp2();
		}
		if (mEdorNo == null || mEdorNo == "") {
			CError.buildErr(this, "查询mEdorNo失败!");
			return false;
		}
		if (mContNo == null || mContNo == "") {
			CError.buildErr(this, "查询mContNo失败!");
			return false;
		}
		// EdorAcceptNo 保全受理号 MissionProp1
		// OtherNo 申请号码 MissionProp2
		// OtherNoType 申请号码类型 MissionProp3
		// EdorAppName 申请人名称 MissionProp4
		// Apptype 申请方式 MissionProp5
		// ManageCom 管理机构 MissionProp7
		// AppntName 投保人 MissionProp11
		// PaytoDate 交费对应日 MissionProp12
		// BackDate 最后回复日期 MissionProp9
		// BackTime 最后回复时间 MissionProp10
		// UWActivityStatus 保全核保状态 MissionProp18
		ExeSQL tExeSQL = new ExeSQL();
		String EdorAcceptNoSql = "select edoracceptno from lpedormain where edorno='"
				+ "?mEdorNo?" + "'";

		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(EdorAcceptNoSql);
		sqlbv1.put("mEdorNo", mEdorNo);
		String mEdorAcceptNo = tExeSQL.getOneValue(sqlbv1);
		mTransferData.setNameAndValue("EdorAcceptNo", mEdorAcceptNo);

		mTransferData.setNameAndValue("OtherNo", mContNo);

		String tSql = "select othernotype,EdorAppName,Apptype,ManageCom  from LPEdorApp where EdorAcceptNo = '"
				+ "?mEdorAcceptNo?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("mEdorAcceptNo", mEdorAcceptNo);
		SSRS tSSRS = tExeSQL.execSQL(sqlbv2);
		if (tSSRS.MaxRow > 0) {
			mTransferData.setNameAndValue("OtherNoType", tSSRS.GetText(1, 1));
			mTransferData.setNameAndValue("EdorAppName", tSSRS.GetText(1, 2));
			mTransferData.setNameAndValue("Apptype", tSSRS.GetText(1, 3));
			mTransferData.setNameAndValue("ManageCom", tSSRS.GetText(1, 4));
		} else {
			CError.buildErr(this, "查询保全LPEdorApp表数据失败!");
			return false;
		}
		String pSql = "select paytodate from lpcont where contno = '" + "?mContNo?"
				+ "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(pSql);
		sqlbv3.put("mContNo", mContNo);
		String tPaytodate = tExeSQL.getOneValue(sqlbv3);
		
		String appSql = "select p.AppntName from lcappnt p, ldperson h where 1 = 1 and h.CustomerNo = p.AppntNo and p.Contno = '"
				+ "?mContNo?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(appSql);
		sqlbv4.put("mContNo", mContNo);
		String tAppntName = tExeSQL.getOneValue(sqlbv4);
		mTransferData.setNameAndValue("AppntName", tAppntName);
		mTransferData.setNameAndValue("PaytoDate", tPaytodate);
		mTransferData.setNameAndValue("BackDate", PubFun.getCurrentDate());
		mTransferData.setNameAndValue("BackTime", PubFun.getCurrentTime());
		mTransferData.setNameAndValue("UWActivityStatus", "2");

		return true;
	}

	public TransferData getTransferData() {
		return this.mTransferData;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}
}
