package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:保险起期提前批单数据生成
 * </p>
 * 
 * <p>
 * Description: 生成客户资料批单打印所需要的数据
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class PEdorYBPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PEdorYBPrintBL.class);

	// 公共数据
	// public CErrors mErrors = new CErrors();

	// 结果容器
	private VData mResult = new VData();

	// 全局变量
	private VData mInputData;

	@SuppressWarnings("unused")
	private String mcOperate;

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private LPEdorItemSchema mLPEdorItemSchema;

	public PEdorYBPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mcOperate = cOperate;
		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("YB数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("YB传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("YB数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("YB准备数据失败!");
			return false;
		}

		return true;
	}

	private boolean getInputData() {

		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			mErrors.addOneError("YB得到数据失败!");
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			mErrors.addOneError("YB中查询保全数据失败!");
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean dealData() {
		/** 要显示的头和尾部分 */
		xmlexport.addDisplayControl("displayHead1");
		xmlexport.addDisplayControl("displayTail1");
		xmlexport.addDisplayControl("displayYB");

		String strSQL = "  select lp.cvalidate,lp.enddate ,lp.insuredname from lppol lp where 1=1  "
				+ " and   lp.edorno='?edorno?'  and lp.polno='?polno?'  and lp.contno='?contno?'  ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("polno", mLPEdorItemSchema.getPolNo());
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);

		if (!(tSSRS != null || tSSRS.MaxRow > 0)) {
			CError.buildErr(this, "查询数据出错！");
			return false;
		}

		mTextTag.add("InsuStartDate", tSSRS.GetText(1, 1));
		mTextTag.add("InsuStartDate2", tSSRS.GetText(1, 1));

		mTextTag.add("InsuEndDate", tSSRS.GetText(1, 2));

		mTextTag.add("InsuredName", tSSRS.GetText(1, 3));

		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {

			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		/** 批单头部 */
		mTextTag.add("AppntName", tLCContSchema.getAppntName()); // 原投保人姓名
		mTextTag.add("InsuredName", tLCContSchema.getInsuredName()); // 被保人姓名
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo()); // 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号

		/** 批单尾部 */
		mTextTag.add("LPEdor.ValiDate", mLPEdorItemSchema.getEdorValiDate()); // 生效日
		mTextTag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator())); // 经办人
		mTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPEdorItemSchema.getApproveOperator())); // 复核人

		String[] allArr = BqNameFun.getAllNames(tLCContSchema.getAgentCode());
		mTextTag.add("ManageCom", allArr[BqNameFun.SALE_SERVICE]);
		mTextTag.add("LABranchGroup.Name", allArr[BqNameFun.DEPART]);
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCContSchema.getAgentCode());

		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {

			mErrors.addOneError("生成数据失败!");
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
