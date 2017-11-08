package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft<S/p>
 * 
 * @author zhangxing
 * @version 1.0
 */
public class GrpUWChangePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(GrpUWChangePrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 输入的查询sql语句
	private String mAgentCode = "";

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LCGCUWMasterSchema mLCGCUWMasterSchema = new LCGCUWMasterSchema();

	public GrpUWChangePrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			buildError("getInputData", "没有得到足够的信息:印刷号不能为空！");
			return false;
		}
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCGrpContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {
		// 根据印刷号查询打印队列中的纪录
		String prtSeq = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(prtSeq);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		logger.debug(mLOPRTManagerSchema.getCode());
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLOPRTManagerSchema.getOtherNo());

		if (!tLCGrpContDB.getInfo()) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			buildError("outputXML", "在取得LCGrpCont的数据时发生错误");
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();

		LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
		tLCGCUWMasterDB.setProposalGrpContNo(mLOPRTManagerSchema.getOtherNo());

		if (!tLCGCUWMasterDB.getInfo()) {
			mErrors.copyAllErrors(tLCGCUWMasterDB.mErrors);
			buildError("outputXML", "在取得LCGCUWMasterDB的数据时发生错误");
			return false;
		}
		mLCGCUWMasterSchema = tLCGCUWMasterDB.getSchema();

		// 代理人信息
		mAgentCode = mLCGrpContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息

		// 代理人组别
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
			return false;
		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("GrpUWChange.vts", "");

		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		texttag.add("BarCode1", "UN064");
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("LCCont.ProposalContNo", mLCGrpContSchema.getGrpContNo());
		texttag.add("LCCont.AppntName", mLCGrpContSchema.getGrpName());
		texttag.add("AppntName", mLCGrpContSchema.getGrpName());
		texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
		texttag.add("LAAgent.Name", mLAAgentSchema.getName());
		texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
		texttag.add("LCCont.UWOperator", mLCGrpContSchema.getUWOperator());
		texttag.add("LOPRTManager.Remark", mLOPRTManagerSchema.getRemark());

		String tManageComName = "";
		if (mLCGrpContSchema.getManageCom().length() < 4) {
			tManageComName = getComName(mLCGrpContSchema.getManageCom());
		} else {
			tManageComName = getComName(mLCGrpContSchema.getManageCom()
					.substring(0, 4));
			if (mLCGrpContSchema.getManageCom().length() >= 6) {
				String tManageComCode = mLCGrpContSchema.getManageCom();
				tManageComName = tManageComName + getComName(tManageComCode);
			}
		}
		texttag.add("ManageComName", tManageComName);

		texttag.add("SumPrem", mLCGrpContSchema.getPrem());
		texttag.add("SysDate", SysDate);
		texttag.add("LCCUWMaster.ChangePolReason", mLCGCUWMasterSchema
				.getChangePolReason());
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		logger.debug("xmlexport=" + xmlexport);
		return true;
	}

	// 下面是一些辅助函数

	private String getComName(String strComCode) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return null;

		}
		return tLDCodeDB.getCodeName();
	}

}
