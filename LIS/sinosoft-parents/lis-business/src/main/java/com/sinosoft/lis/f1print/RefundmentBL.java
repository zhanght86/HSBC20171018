package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author guomy
 * @version 1.0
 */

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LABranchGroupSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class RefundmentBL implements PrintService {
private static Logger logger = Logger.getLogger(RefundmentBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 取得的保单号码
	// private String mContNo = "";

	// 输入的查询sql语句
	private String msql = "";
	// 取得的代理人编码
	private String mAgentCode = "";

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	private LJAGetSchema mLJAGetSchema = new LJAGetSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCContSet mLCContSet = new LCContSet();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LABranchGroupSchema mLABranchGroupSchema = new LABranchGroupSchema();

	public RefundmentBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT") && !cOperate.equals("CONFIRM")) {
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

	public static void main(String[] args) {
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
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

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "LCContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getPrintData() {

		// 根据印刷号查询打印队列中的纪录
		String PrtSeq = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(PrtSeq);
		tLOPRTManagerSchema = tLOPRTManagerDB.query().get(1);

		LJAGetDB tLJAGetDB = new LJAGetDB();
		tLJAGetDB.setActuGetNo(tLOPRTManagerSchema.getStandbyFlag7());
		if (tLJAGetDB.getInfo() == false) {
			mErrors.copyAllErrors(tLJAGetDB.mErrors);
			buildError("outputXML", "在取得实付数据时发生错误");
			return false;
		}
		mLJAGetSchema = tLJAGetDB.getSchema();

		// LCContDB tLCContDB = new LCContDB();
		// tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		// int m, i;
		// if (!tLCContDB.getInfo())
		// {
		// mErrors.copyAllErrors(tLCContDB.mErrors);
		// buildError("outputXML", "在取得LCCont的数据时发生错误");
		// return false;
		// }
		// mLCContSchema = tLCContDB.getSchema();

		// LJTempFeeClassDB LJTempFeeClassDB = new LJTempFeeClassDB();
		// LJTempFeeClassSchema tLJTempFeeClassSchema = new
		// LJTempFeeClassSchema();
		// LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		// LJTempFeeClassDB.setTempFeeNo(mLOPRTManagerSchema.getStandbyFlag4());
		// tLJTempFeeClassSet = LJTempFeeClassDB.query();
		// tLJTempFeeClassSchema = tLJTempFeeClassSet.get(1);
		//
		// LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		// LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		// LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		// tLJTempFeeDB.setTempFeeNo(mLOPRTManagerSchema.getStandbyFlag4());
		// tLJTempFeeSet = tLJTempFeeDB.query();
		// tLJTempFeeSchema = tLJTempFeeSet.get(1);

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLJAGetSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		logger.debug("dflkdfjdasjgfiodjgijhd"
				+ mLAAgentSchema.getAgentGroup());
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LABranchGroupDB的数据时发生错误");
			return false;
		}
		mLABranchGroupSchema = tLABranchGroupDB.getSchema();

		// 其它模版上单独不成块的信息
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("Refundment.vts", "printer"); // 最好紧接着就初始化xml文档
		// 生成-年-月-日格式的日期
		StrTool tSrtTool = new StrTool();
		String SysDate = tSrtTool.getYear() + "年" + tSrtTool.getMonth() + "月"
				+ tSrtTool.getDay() + "日";

		// 模版自上而下的元素
		texttag.add("BarCode1", "UN014"); // 单证类型
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10"); // 条码地址

		texttag.add("Actugetno", mLJAGetSchema.getActuGetNo()); // 实付号
		texttag.add("LAAgent.Name", mLAAgentSchema.getName()); // 代理人姓名
		texttag.add("LCCont.AgentCode", mLJAGetSchema.getAgentCode()); // 代理人业务号
		texttag.add("AgentGroup", getComName(mLAAgentSchema.getManageCom())
				+ " " + getUpComName(mLAAgentSchema.getBranchCode())); // 营业机构
		texttag.add("prtno", mLJAGetSchema.getOtherNo());
		texttag.add("prem", mLJAGetSchema.getSumGetMoney());

		texttag.add("SysDate", SysDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("e:\\", "test"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	public String getUpComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return ttLABranchGroupDB.getName();
			}
		}
	}

	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 * @return
	 * @throws Exception
	 */
	private String getComName(String strComCode) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return "";
		}
		return tLDCodeDB.getCodeName();
	}
}
