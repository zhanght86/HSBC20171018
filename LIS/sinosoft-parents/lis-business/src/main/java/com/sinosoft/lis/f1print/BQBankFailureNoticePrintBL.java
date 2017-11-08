package com.sinosoft.lis.f1print;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LMEdorItemDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class BQBankFailureNoticePrintBL implements PrintService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger
			.getLogger(BQBankFailureNoticePrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private XmlExportNew tXmlExport = new XmlExportNew();

	private TextTag texttag = new TextTag(); // 新建一个TextTag的实例

	public BQBankFailureNoticePrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {

		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验操作
		if (!checkData()) {
			return false;
		}

		// 从数据库获得数据
		if (!getBaseData()) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}

		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:打印流水号不能为空！"));
			return false;
		}

		// 取打印通知书记录
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (!tLOPRTManagerDB.getInfo()) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		return true;
	}

	private boolean checkData() {
		return true;
	}

	// 获取数据库数据
	private boolean getBaseData() {
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJAGetDB tLJAGetDB = new LJAGetDB();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		if(!tLCContDB.getInfo()){
			CError.buildErr(this, "查询保单信息失败!");
			return false;
		}
		// 是收费还是付费
		// 1表收费
		String printName = "";//打印名称
		if (mLOPRTManagerSchema.getStandbyFlag5() != null
				&& mLOPRTManagerSchema.getStandbyFlag5().equals("1")) {
			printName = "转账收费不成功通知书";
			tLJSPayDB.setGetNoticeNo(mLOPRTManagerSchema.getStandbyFlag3());
			tLJSPayDB.setCurrency("01");
			if (!tLJSPayDB.getInfo()) {
				CError.buildErr(this, "查询应收汇总表失败!");  
				return false;
			}
			texttag.add("AccName", tLJSPayDB.getAccName());// 户名
			LDBankDB tLDBankDB = new LDBankDB();
			tLDBankDB.setBankCode(tLJSPayDB.getBankCode());
			if (!tLDBankDB.getInfo()) {
				CError.buildErr(this, "查询银行表失败!");
				return false;
			}
			texttag.add("BankCodeName", tLDBankDB.getBankName());// 银行名
			texttag.add("BankAccNo", tLJSPayDB.getBankAccNo());// 账户号
			texttag.add("GetPayMoney", tLJSPayDB.getSumDuePayMoney());// 转账金额
		}
		// 2表付费
		else if (mLOPRTManagerSchema.getStandbyFlag5() != null
				&& mLOPRTManagerSchema.getStandbyFlag5().equals("2")) {
			printName = "转账退费不成功通知书";
			tLJAGetDB.setActuGetNo(mLOPRTManagerSchema.getStandbyFlag3());
			if (!tLJAGetDB.getInfo()) {
				CError.buildErr(this, "查询应付汇总表失败!");
				return false;
			}
			texttag.add("AccName", tLJAGetDB.getAccName());// 户名
			LDBankDB tLDBankDB = new LDBankDB();
			tLDBankDB.setBankCode(tLJAGetDB.getBankCode());
			if (!tLDBankDB.getInfo()) {
				CError.buildErr(this, "查询银行表失败!");
				return false;
			}
			texttag.add("BankCodeName", tLDBankDB.getBankName());// 银行名
			texttag.add("BankAccNo", tLJAGetDB.getBankAccNo());// 账户号
			texttag.add("GetPayMoney", tLJAGetDB.getSumGetMoney());// 转账金额
		}
		tXmlExport.createDocument(printName); // 初始化xml文档
		PrintTool.setBarCode(tXmlExport, mLOPRTManagerSchema.getPrtSeq());
		String uLanguage = PrintTool.getCustomerLanguage(tLCContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) {
			tXmlExport.setUserLanguage(uLanguage);
		}
		tXmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLOPRTManagerSchema.getStandbyFlag1());
		if (!tLPEdorAppDB.getInfo()) {
			CError.buildErr(this, "查询保全申请表失败!");
			return false;
		}
		texttag.add("AgentCode", mLOPRTManagerSchema.getAgentCode());// 代理人
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLOPRTManagerSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			CError.buildErr(this, "查询代理人表失败!");
			return false;
		}
		texttag.add("AgentCodeName", tLAAgentDB.getName());// 代理人
		texttag.add("ManageCom", mLOPRTManagerSchema.getManageCom());// 所属管理机构
		texttag.add("PrintDate", PubFun.getCurrentDate());// 打印日期
		LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
		tLMEdorItemDB.setEdorCode(mLOPRTManagerSchema.getStandbyFlag2());
		tLMEdorItemDB.setAppObj("I");
		if (!tLMEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询险种保全表失败!");
			return false;
		}
		texttag.add("EdorCodeName", tLMEdorItemDB.getEdorCode() + "-"
				+ tLMEdorItemDB.getEdorName());// 保全项目名
		texttag.add("ContNo", mLOPRTManagerSchema.getOtherNo());// 保单号
		texttag.add("ApplyName", tLPEdorAppDB.getEdorAppName());// 申请资格人
		texttag.add("EdorAppDate", mLOPRTManagerSchema.getStandbyFlag4());// 申请日期
		texttag.add("PrtSeqNo", mLOPRTManagerSchema.getPrtSeq());// 审核通知书号码
		texttag.add("CurrentDate", PubFun.getCurrentDate());// 审核时间

		texttag.add("TransferDate", mLOPRTManagerSchema.getStandbyFlag6());// 最后一次转账失败时间
		texttag.add("FailureReason", mLOPRTManagerSchema.getStandbyFlag7());// 最后一次转账失败原因

		texttag.add("Operator", tLPEdorAppDB.getOperator()); // 保险公司经办人
		texttag.add("Approver", tLPEdorAppDB.getApproveOperator()); // 审核人 可能没有
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean preparePrintData() {
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		mResult.add(tXmlExport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
	}
}
