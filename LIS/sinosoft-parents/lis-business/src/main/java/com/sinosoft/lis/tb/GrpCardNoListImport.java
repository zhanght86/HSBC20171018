package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.ArrayList;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDPlanDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GrpCardNoListImport {
private static Logger logger = Logger.getLogger(GrpCardNoListImport.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 储存签单的所有信息,包括错误信息和签单成功信息 */
	private StringBuffer allMessage = new StringBuffer();
	/** 传入的数据包** */
	private VData InputVata = new VData();
	/** 返回的数据包** */
	private VData ResultVata = new VData();
	/** 原始导入文件名(包括路径)** */
	private String mExcelFileName; //
	/** 配置文件名(包括路径)** */
	private String mConfigFileName; //
	/** 准备生成的文件名(包括路径)** */
	private String mXmlFileName; //

	// 导入保单数量
	private int CardListNum;

	/** 操作员登陆信息** */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mOperator;
	private String mManageCom;
	private String mComCode;
	private String sCurrentDate = PubFun.getCurrentDate();

	/** 传入信息包** */
	/*
	 * <p>mGlobalInput(在JSP中通过session取出)，文件名等信息应该在JSP文件中得到然后放到TransferData传到此类!</p>
	 */
	private TransferData mTransferData = new TransferData();

	// 构造函数,不传入文件名称
	public GrpCardNoListImport() {

	}

	/** ****************** */
	public boolean submitData(VData cInputData, String cOperate) {
		this.InputVata = (VData) cInputData.clone();
		if (!getInputData()) {
			return false;
		}
		if (!checkData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	/**
	 * 得到传入数据
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) InputVata.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) InputVata.getObjectByObjectName(
				"TransferData", 0);
		// 校验是否传入的操作信息
		if (mGlobalInput == null) {
			this.buildError("getInputData", "操作员信息为空!");
			return false;
		} else {
			mOperator = mGlobalInput.Operator;
			mManageCom = mGlobalInput.ManageCom;
			mComCode = mGlobalInput.ComCode;
		}

		// 校验其他信息是否传入
		if (mTransferData == null) {
			this.buildError("getInputData", "没有传入要导入文件的信息!");
			return false;
		} else {
			mExcelFileName = (String) mTransferData
					.getValueByName("ExcelFileName");
			mConfigFileName = (String) mTransferData
					.getValueByName("ConfigFileName");
			mXmlFileName = (String) mTransferData.getValueByName("XmlFileName");
		}
		return true;
	}

	/** 校验数据 */
	private boolean checkData() {
		return true;
	}

	private boolean dealData() {
		if (!ImportData()) {
			return false;
		}
		return true;
	}

	/** *****处理导入文件******* */
	private boolean ImportData() {
		logger.debug("导入处理开始时间:" + PubFun.getCurrentTime());

		// 调用excel转换到xml的类
		logger.debug("****************  解析文件开始时间  ********************"
				+ PubFun.getCurrentTime());
		try {
			GrpCardNoListImportExcelToXml parserToXml = new GrpCardNoListImportExcelToXml(
					mExcelFileName, mConfigFileName, mXmlFileName, true);
			if (parserToXml.transform() == false) {
				this.mErrors.copyAllErrors(parserToXml.mErrors);
				logger.debug("***导入解析文件失败：");
				this.buildError("ImportData", "导入解析文件失败!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		logger.debug("****************  解析文件完成时间  ******************"
				+ PubFun.getCurrentTime());

		//
		logger.debug("****************  读取数据，业务处理开始时间  ********************"
						+ PubFun.getCurrentTime());
		try {
			GrpCardNoListImportDataFromXml parserFromXML = new GrpCardNoListImportDataFromXml(
					mXmlFileName);
			// 取出本次导入的卡单数量
			CardListNum = parserFromXML.getCardListSize();

			// 循环取出每张卡单信息，然后处理每张保单数据。
			// 如果出现错误，则记录在日志中，跳出循环
			for (int curIndex = 0; curIndex < CardListNum; curIndex++) {
				VData OneCardData = new VData(); //
				OneCardData = (VData) parserFromXML.genOneCard(curIndex)
						.clone();
				OneCardData.add(this.mGlobalInput);

				// 通过allMessage监视保单的签单情况
				LCContSchema kLCContSchema = new LCContSchema();
				kLCContSchema.setSchema((LCContSchema) OneCardData
						.getObjectByObjectName("LCContSchema", 0));
				String contNo = kLCContSchema.getContNo();
				this.allMessage.append("凭证号为：" + contNo + " 正在录单\n");

				if (ProposalInputData(OneCardData, contNo) == false) {
					this.allMessage.append(" \n凭证号为：" + contNo + "的保单保存失败!\n ");
				} else {
					this.allMessage.append(" \n凭证号为：" + contNo + "的保单保存成功!\n ");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/** * 本方法调用业务处理类,每次处理一张卡单 */
	private boolean ProposalInputData(VData sOneCardData, String contNo) {
		// 补充往后台送的sOneCardData的业务信息
		if (!Complete(sOneCardData, contNo)) {
			return false;
		}

		// 初步校验保单信息及完善规则保单信息
		if (!checkContData(sOneCardData, contNo)) {
			return false;
		}

		// 后台业务处理
		try {
			CardProposalBL signBL = new CardProposalBL();
			if (!signBL.submitData(sOneCardData, "INSERT||CONT")) {
				this.mErrors.copyAllErrors(signBL.mErrors);
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	// 补充往后台送的sOneCardData的业务信息
	private boolean Complete(VData sOneCardData, String contNo) {
		// (LCCont表)
		LCContSchema tLCCont = (LCContSchema) sOneCardData
				.getObjectByObjectName("LCContSchema", 0);
		// (LCPol表)
		LCPolSchema tLCPol = (LCPolSchema) sOneCardData.getObjectByObjectName(
				"LCPolSchema", 0);
		// 获得业务员代码
		String sAgentCode = tLCCont.getAgentCode();
		LAAgentDB sLAAgentDB = new LAAgentDB();
		sLAAgentDB.setAgentCode(sAgentCode);
		if (!sLAAgentDB.getInfo()) {
			buildError("forMatContData", "查询校验业务失败。");
			return false;
		}
		String sAgentGroup = sLAAgentDB.getAgentGroup();
		String sManageCom = sLAAgentDB.getManageCom();

		// 给管理机构赋值
		// String comCode=this.mGlobalInput.ManageCom;
		tLCCont.setManageCom(sManageCom);
		tLCCont.setAgentGroup(sAgentGroup);
		tLCPol.setManageCom(sManageCom);
		return true;
	}

	/**
	 * 效验一张保单的信息 每次得到一张保单的全部 数据(包括 投保人的LDPreson,投保人的LCAddress,
	 * LCCont,LCAppnt,,LCInsured,LCPol,LCBnf)
	 */
	private boolean checkContData(VData sOneCardData, String contNo) {
		// 校验保单信息
		if (!checkLCCont(sOneCardData, contNo)) {
			return false;
		}

		if (checkLDPerson(sOneCardData, contNo)
				&& checkLCAddress(sOneCardData, contNo)
				&& checkLInsured(sOneCardData, contNo)
				&& checkLCPol(sOneCardData, contNo)) {
			return true;
		} else {
			return false;
		}

	}

	/** *****校验保单信息******* */
	private boolean checkLCCont(VData sOneCardData, String contNo) {
		// 校验合同基本信息(LCCont表)
		LCContSchema tLCCont = (LCContSchema) sOneCardData
				.getObjectByObjectName("LCContSchema", 0);
		if (tLCCont == null) {
			buildError("checkLCCont", "保单 < " + contNo + " > 获取卡信息失败！");
			return false;
		}
		// 校验 如果 "保险凭证号码"或"业务员代码"代码为空 则返回错误
		String sContNo = tLCCont.getContNo();
		String sAgentCode = tLCCont.getAgentCode();
		if (("").equals(sContNo) || sContNo == null) {
			buildError("checkLCCont", "保单 < " + contNo + " >保险凭证号码不能为空！");
			return false;
		}
		// 下面校验卡号是否存在
		String scomCode = this.mGlobalInput.ManageCom;
		LCContDB cont = new LCContDB();
		cont.setContNo(sContNo);
		cont.setManageCom(scomCode);
		if (cont.getInfo()) {
			this.allMessage.append("凭证号码已经存在，不能重复！");
			buildError("checkLCCont", "保单< " + contNo + " >凭证号码已经存在，不能重复！");
			return false;
		}

		if (("").equals(sAgentCode) || sAgentCode == null) {
			buildError("checkLCCont", "保单 < " + contNo + " >业务员代码不能为空！");
			return false;
		}

		// 下面校验业务员代码
		String agentCode = tLCCont.getAgentCode();
		String comCode = this.mGlobalInput.ManageCom;
		LAAgentDB agent = new LAAgentDB();
		agent.setAgentCode(agentCode);
		agent.setManageCom(comCode);
		if (!agent.getInfo()) {
			this.allMessage.append("业务员不存在或不是本机构的！");
			buildError("checkLCCont", "保单< " + contNo + " >业务员不正确或则不是本机构的!！");
			return false;
		}
		// 如果投保日期大于当前日期则返回错误
		String sPolApplyDate = tLCCont.getPolApplyDate();
		if (!("").equals(sContNo)) {
			if (PubFun.calInterval(sCurrentDate, sPolApplyDate, "D") > 0) {
				buildError("checkLCCont", "保单 < " + contNo + " >投保日期不能大于当前日期！");
				return false;
			}
		}

		return true;
	}

	private boolean checkLInsured(VData sOneCardData, String contNo) {
		// 校验合同基本信息(LCCont表)
		LCInsuredSchema tLCInsured = (LCInsuredSchema) sOneCardData
				.getObjectByObjectName("LCInsuredSchema", 0);
		if (tLCInsured == null) {
			buildError("checkLInsured", "保单 < " + contNo + " > 获取卡信息失败！");
			return false;
		}
		String sInsuredStat = tLCInsured.getInsuredStat();
		if (("").equals(sInsuredStat) || sInsuredStat == null) {
			buildError("checkLInsured", "保单 < " + contNo + " >激活标志不能为空！");
			return false;
		}

		return true;
	}

	private boolean checkLDPerson(VData sOneCardData, String contNo) {
		return true;
	}

	private boolean checkLCAddress(VData sOneCardData, String contNo) {
		return true;
	}

	private boolean checkLCPol(VData sOneCardData, String contNo) {
		LCPolSchema polSchema = (LCPolSchema) sOneCardData
				.getObjectByObjectName("LCPolSchema", 0);
		LCInsuredSchema insuredSchema = (LCInsuredSchema) sOneCardData
				.getObjectByObjectName("LCInsuredSchema", 0);
		// 校验险种是否存在
		String riskCode = polSchema.getRiskCode();
		String contplanCode = insuredSchema.getContPlanCode();
		if (contplanCode == null || contplanCode.equals("")) {
			if (riskCode == null || riskCode.equals("")) {
				this.allMessage.append("在保单" + contNo + "中必须录入产品组合代码或险种代码两者之一！"
						+ contplanCode);
				buildError("checkLCPol", "保单" + contNo
						+ "中必须录入产品组合代码或险种代码两者之一！" + contplanCode);
				return false;

			}
		}
		if (contplanCode != null && !contplanCode.equals("")) {
			LDPlanDB planDB = new LDPlanDB();
			planDB.setContPlanCode(contplanCode);
			planDB.setPlanType("0");
			if (!planDB.getInfo()) {
				this.allMessage.append("在保单" + contNo + "中录入了不存在的产品组合:"
						+ contplanCode);
				buildError("checkLCPol", "保单" + contNo + "中录入了不存在的产品组合:"
						+ contplanCode);
				return false;
			}
		}
		if (riskCode != null && !riskCode.equals("")) {
			LMRiskDB riskDB = new LMRiskDB();
			riskDB.setRiskCode(riskCode);
			if (!riskDB.getInfo()) {
				this.allMessage.append("在保单" + contNo + "中录入了不存在的险种:"
						+ riskCode);
				buildError("checkLCPol", "保单" + contNo + "中录入了不存在的险种:"
						+ riskCode);
				return false;
			}
		}
		// 校验份数
		TransferData nTransferData = new TransferData();
		nTransferData = (TransferData) sOneCardData.getObjectByObjectName(
				"TransferData", 0);
		String mult = (String) nTransferData.getValueByName("Mult");
		if (mult == null || mult.equals("")) {
			buildError("checkLCPol", "保单 < " + contNo + " > 份数不能为空！");
			return false;
		}

		return true;
	}

	/** ****捕获错误后，将错误信息保存起来****** */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpCardNoListImport";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		mErrors.addOneError(cError);
	}

	// 得到错误信息,在部分签单完成时很有用。得到额外的信息
	private static String getErrorMessage(CErrors tErrors) {

		StringBuffer strBuffer = new StringBuffer();
		int errorCount = tErrors.getErrorCount();
		for (int i = 0; i < errorCount; i++) {
			strBuffer.append(tErrors.getError(i));
		}
		return strBuffer.toString();
	}

	// 得到签单的所有信息
	public String getAllMessage() {
		return allMessage.toString();
	}

	/**
	 * * 测试函数 *
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		try {
			VData tVData = new VData();

			String previousExcelFileName = "G:/NewSys/ui/upload/GrpCardNoListImport.xls";
			String configFileName = "G:/NewSys/ui/upload/GrpSimpleImport.xml";
			String createdFileName = "G:/NewSys/ui/upload/GrpCardImport.xml";

			TransferData transferData = new TransferData();
			transferData
					.setNameAndValue("ExcelFileName", previousExcelFileName);
			transferData.setNameAndValue("ConfigFileName", configFileName);
			transferData.setNameAndValue("XmlFileName", createdFileName);
			tVData.add(transferData);

			GlobalInput tGI = new GlobalInput();
			tGI.Operator = "001";
			tGI.ManageCom = "86210001";
			tGI.ComCode = "86210001";
			tVData.add(tGI);

			GrpCardNoListImport tGrpCard = new GrpCardNoListImport();

			if (!tGrpCard.submitData(tVData, "")) {
				logger.debug("保单保存失败！失败原因如下:"
						+ tGrpCard.mErrors.getFirstError());
			} else if (tGrpCard.mErrors.getErrorCount() != 0) {
				// 某一个保单为签单,或则即使其签单了还存在一些错误
				logger.debug("部分保单保存成功！保存信息:" + tGrpCard.getAllMessage());
			} else {
				// 不存在错误，不打印签单信息
				logger.debug("全部保存成功!");
			}
		} catch (Exception e) {
			logger.debug("***抛出如下异常：");
			e.printStackTrace();
		}
	}

	public static ArrayList getCodeList(String codeType) {
		ArrayList codeList = new ArrayList();
		// 初始化得到一个链表
		SSRS resultSet = new ExeSQL()
				.execSQL("select code from ldcode where codetype='" + codeType
						+ "' ");
		for (int i = 1; i < resultSet.MaxRow; i++) {
			codeList.add(resultSet.GetText(i, 0));
		}
		return codeList;
	}

	public static ArrayList getOccupation() {
		ArrayList codeList = new ArrayList();
		// 初始化得到一个链表
		SSRS resultSet = new ExeSQL()
				.execSQL("select OccupationCode from ldoccupation");
		for (int i = 1; i < resultSet.MaxRow; i++) {
			codeList.add(resultSet.GetText(i, 0));
		}
		return codeList;
	}

}
