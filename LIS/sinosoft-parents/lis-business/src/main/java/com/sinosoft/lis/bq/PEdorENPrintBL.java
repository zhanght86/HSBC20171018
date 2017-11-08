package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 满期不续保
 * </p>
 * <p>
 * Description: 数据生成
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * </p>
 * 
 * @author wangyiyan
 * @version 1.0
 */
public class PEdorENPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PEdorENPrintBL.class);

	private VData mResult = new VData();

	// public CErrors mErrors = new CErrors();

	// 全局变量
	@SuppressWarnings("unused")
	private String mOperate;

	private VData mInputData = new VData();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ListTable tENListTable = new ListTable();

	public PEdorENPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// logger.debug("Start preparing the data to print ====>" +
		// mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("EN数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("EN数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("准备数据失败!");
			return false;
		}

		// logger.debug("End preparing the data to print ====>" +
		// mOperate);
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	private boolean dealData() {
		// xmlexport.addDisplayControl("displayHead1");
		BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displayEN");
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		String tpolno = "'000000'";
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		// 查询本次满期不续保险种,保额（份数）
		tENListTable.setName("EN");
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo()); // 读取批单号
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType()); // 读取批单类型
		// tLPPolDB.setPolNo (mLPEdorItemSchema.getPolNo ()) ; //读取批单类型
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet.size() <= 0) {
			mErrors.addOneError("打印生成数据时，没有查询到该批单的信息!");
			return false;
		}
		String tChangeFLag = "";
		// 查询险种
		LPPolSchema tLPPolSchema = new LPPolSchema();
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			tLPPolSchema = new LPPolSchema();
			tLPPolSchema = tLPPolSet.get(i);
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
			if (!tLMRiskDB.getInfo()) {
				mErrors.addOneError("打印生成数据时，取险种名称信息失败!");
				return false;
			}
			String[] strEN = new String[3];
			// 续保方式变更的险种名称
			strEN[0] = tLMRiskDB.getRiskName() + "--" + tLMRiskDB.getRiskCode();

			if (tLPPolSchema.getRnewFlag() == -1) {
				strEN[1] = "不续保";
				strEN[2] = "自动续保";
				tChangeFLag = "1";// 由不续保变成自动续保标记
			}
			if (tLPPolSchema.getRnewFlag() == -2) {
				strEN[1] = "自动续保";
				strEN[2] = "不续保";
			}
			tENListTable.add(strEN);
			tpolno = tpolno + ",'" + tLPPolSchema.getPolNo().trim() + "'";
		}
		String[] b_strEN = new String[3];
		xmlexport.addListTable(tENListTable, b_strEN);
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL = "select 1 from lwmission where activityid='0000000009'  and missionprop1='?missionprop1?' and  missionprop2='?missionprop2?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("missionprop1", mLPEdorItemSchema.getEdorAcceptNo());
		sqlbv.put("missionprop2", mLPEdorItemSchema.getContNo());
		String tConfirmFLag = tExeSQL.getOneValue(sqlbv);
		if ("1".equals(tConfirmFLag) && "1".equals(tChangeFLag)) // 已经在保全确认节点。在保全确认时刻而且由不续保变成自动续保
		{
			xmlexport.addDisplayControl("displayENRemark");
			mTextTag.add("remark", "公司将在下一年度续保时对您的保单进行重新审核。");
		}

		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成打印数据失败!");
			return false;
		}
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
