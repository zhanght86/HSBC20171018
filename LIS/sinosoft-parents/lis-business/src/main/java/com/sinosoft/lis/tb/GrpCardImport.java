package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.ArrayList;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LDPlanDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LACommisionDetailSchema;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredRelatedSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartDetailSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GrpCardImport {
private static Logger logger = Logger.getLogger(GrpCardImport.class);
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

	String sCurrentDate = PubFun.getCurrentDate();
	String sCurrentTime = PubFun.getCurrentTime();

	/** 传入信息包** */
	/*
	 * <p>mGlobalInput(在JSP中通过session取出)，文件名等信息应该在JSP文件中得到然后放到TransferData传到此类!</p>
	 */
	private TransferData mTransferData = new TransferData();

	// 防止一次性签很多的保单,在验证销售渠道多次查表耗时过多,用一个连表保存销售渠道的方式
	private ArrayList selltypeList;
	// 性别的链表
	private ArrayList sexList;
	// 证件类型
	private ArrayList idTypeList;
	// 职业类别
	private ArrayList occuTypeList;
	// 职业代码
	private ArrayList occuList;
	// 关系
	private ArrayList relationList;

	private ArrayList bnfOrderList;

	// 构造函数,不传入文件名称
	public GrpCardImport() {
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
			GrpCardImportExcelToXml parserToXml = new GrpCardImportExcelToXml(
					mExcelFileName, mConfigFileName, mXmlFileName, true);
			if (parserToXml.transform() == false) {
				this.mErrors.copyAllErrors(parserToXml.mErrors);
				logger.debug("***导入解析文件失败：");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			buildError("ImportData", "解析文件失败:" + e.getLocalizedMessage());
			return false;
		}
		logger.debug("****************  解析文件完成时间  ******************"
				+ PubFun.getCurrentTime());

		// 文件解析成功后,则调用准备校验list
		if (getAllCheckList() == false) {
			logger.debug("****************准备基础数据(用于校验)失败******************");
			buildError("ImportData", "准备基础数据(用于校验)失败");
			return false;
		}

		//

		try {
			logger.debug("****************  读取数据，业务处理开始时间  ********************"
							+ PubFun.getCurrentTime());
			GrpCardImportDataFromXml parserFromXML = new GrpCardImportDataFromXml(
					mXmlFileName);
			// 取出本次导入的卡单数量
			CardListNum = parserFromXML.getCardListSize();
			logger.debug("*******保单数量为：====" + CardListNum);
			// 循环取出每张卡单信息，然后处理每张保单数据。
			// 如果出现错误，则记录在日志中，跳出循环
			for (int curIndex = 0; curIndex < CardListNum; curIndex++) {
				VData OneCardData = new VData(); //
				OneCardData = (VData) parserFromXML.genOneCard(curIndex)
						.clone();
				LCContSchema tLCContSchema = new LCContSchema();
				tLCContSchema = (LCContSchema) OneCardData
						.getObjectByObjectName("LCContSchema", 0);
				if (checkContData(OneCardData, tLCContSchema.getContNo()) == false) {
					logger.debug("第 " + curIndex + " 保单数量校验出错.");
					continue;
				}
				OneCardData = forMatContData(OneCardData);
				if (ProposalInputData(OneCardData, tLCContSchema.getContNo()) == false) {
					continue;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			buildError("ImportData", "导入时抛出异常：" + e.getMessage());
			return false;
		}

		return true;
	}

	/** * 本方法调用业务处理类,每次处理一张卡单 */
	private boolean ProposalInputData(VData sOneCardData, String sContNo) {
		GrpCardImportProposal signBL = new GrpCardImportProposal();
		if (!signBL.submitData(sOneCardData, "INSERT||CONT")) {
			CError cError = new CError();
			cError.moduleName = "卡单导入";
			cError.errorMessage = "保单 < " + sContNo + " > 处理过程中出现如下错误:";
			mErrors.addOneError(cError);
			mErrors.copyAllErrors(signBL.mErrors);
			return false;
		}
		return true;
	}

	// 用于格式化数据,然后返回
	private VData forMatContData(VData OneCardData) {
		VData tOneCardData = new VData();
		// tOneCardData = (VData) OneCardData.clone();

		//
		String sSaleChnl = "2";
		String sSellType = "01";
		String sContType = "2";
		// 业务员号码
		String sAgentCode = "";
		String sAgentGroup = "";
		String sManageCom = "";

		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setSchema((LCContSchema) OneCardData
				.getObjectByObjectName("LCContSchema", 0));
		// 获得业务员代码
		sAgentCode = tLCContSchema.getAgentCode();
		LAAgentDB sLAAgentDB = new LAAgentDB();
		sLAAgentDB.setAgentCode(sAgentCode);
		if (!sLAAgentDB.getInfo()) {
			buildError("forMatContData", "查询校验业务失败。");
			return null;
		}
		sAgentGroup = sLAAgentDB.getAgentGroup();
		sManageCom = sLAAgentDB.getManageCom();
		// 补全保单表数据
		tLCContSchema.setGrpContNo(SysConst.ZERONO);
		tLCContSchema.setAgentCode(sAgentCode);
		tLCContSchema.setAgentGroup(sAgentGroup);
		tLCContSchema.setManageCom(sManageCom);
		tLCContSchema.setPolType("0");
		tLCContSchema.setContType(sContType);
		if (tLCContSchema.getPolApplyDate() == null
				|| tLCContSchema.getPolApplyDate().equals("")) {
			tLCContSchema.setPolApplyDate(sCurrentDate);
		}
		tLCContSchema.setForceUWFlag("0");
		// tLCContSchema.setForceUWReason(tLCContSchema.getContNo());//卡单时暂时用于存储卡号
		tOneCardData.add(tLCContSchema);
		// 补全投保人地址信息
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		tLCAddressSchema.setSchema((LCAddressSchema) OneCardData
				.getObjectByObjectName("LCAddressSchema", 0));
		tOneCardData.add(tLCAddressSchema);
		// 投保人表
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntSchema.setSchema((LCAppntSchema) OneCardData
				.getObjectByObjectName("LCAppntSchema", 0));
		tLCAppntSchema.setGrpContNo(SysConst.ZERONO);
		if (tLCAppntSchema.getIDType() != null
				&& tLCAppntSchema.getIDType().equals("0")) {
			// 从身份证号提取客户性别和出生日期
			tLCAppntSchema.setAppntSex(PubFun.getSexFromId(tLCAppntSchema
					.getIDNo()));
			tLCAppntSchema.setAppntBirthday(PubFun
					.getBirthdayFromId(tLCAppntSchema.getIDNo()));
		}
		tOneCardData.add(tLCAppntSchema);

		// 账户表
		LCAccountSchema tLCAccountSchema = new LCAccountSchema();
		tOneCardData.add(tLCAccountSchema);

		// 告知表
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
		tOneCardData.add(tLCCustomerImpartSet);
		tOneCardData.add(tLCCustomerImpartDetailSet);

		// 分佣表
		LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
		LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
		tLACommisionDetailSchema.setGrpContNo(tLCContSchema.getContNo());
		tLACommisionDetailSchema.setAgentCode(tLCContSchema.getAgentCode());
		tLACommisionDetailSchema.setBusiRate(1);
		tLACommisionDetailSchema.setAgentGroup(tLCContSchema.getAgentGroup());
		tLACommisionDetailSchema.setPolType("0");
		tLACommisionDetailSet.add(tLACommisionDetailSchema);
		tOneCardData.add(tLACommisionDetailSet);

		// 被保人
		LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();
		tOneCardData.add(tmLCInsuredSchema);
		// 客户表
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		tOneCardData.add(tLDPersonSchema);

		LCAddressSchema tmLCAddressSchema = new LCAddressSchema();
		tOneCardData.add(tmLCAddressSchema);

		// 其他数据
		TransferData tTransferData = new TransferData();
		tTransferData = (TransferData) OneCardData.getObjectByObjectName(
				"TransferData", 0);
		tOneCardData.add(tTransferData);

		// 被保人表set
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		tLCInsuredSet.set((LCInsuredSet) OneCardData.getObjectByObjectName(
				"LCInsuredSet", 0));
		for (int i = 1; i <= tLCInsuredSet.size(); i++) {
			tLCInsuredSet.get(i).setContPlanCode(
					(String) tTransferData.getValueByName("ContPlanCode"));
			// 从身份证里提取客户性别和出生日期
			if (tLCInsuredSet.get(i).getIDType() != null
					&& tLCInsuredSet.get(i).getIDType().equals("0")) {
				tLCInsuredSet.get(i).setSex(
						PubFun.getSexFromId(tLCInsuredSet.get(i).getIDNo()));
				tLCInsuredSet.get(i).setBirthday(
						PubFun
								.getBirthdayFromId(tLCInsuredSet.get(i)
										.getIDNo()));
			}
		}
		tOneCardData.add(tLCInsuredSet);

		// 险种
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tOneCardData.add(tLCPolSchema);

		// 险种set
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet.set((LCPolSet) OneCardData.getObjectByObjectName("LCPolSet",
				0));
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			tLCPolSet.get(i).setGrpContNo(tLCContSchema.getGrpContNo());
			tLCPolSet.get(i).setContNo(tLCContSchema.getContNo());
			tLCPolSet.get(i).setPrtNo(tLCContSchema.getPrtNo());
			tLCPolSet.get(i).setSaleChnl(tLCContSchema.getSaleChnl());
			tLCPolSet.get(i).setContType(tLCContSchema.getContType());
			tLCPolSet.get(i).setPolApplyDate(tLCContSchema.getPolApplyDate());
			tLCPolSet.get(i).setPolTypeFlag(tLCContSchema.getPolType());
			tLCPolSet.get(i).setManageCom(tLCContSchema.getManageCom());
			tLCPolSet.get(i).setCValiDate(tLCContSchema.getPolApplyDate());
			tLCPolSet.get(i).setRnewFlag("-1");// 续保标志
		}
		tOneCardData.add(tLCPolSet);

		// 连带被保人
		LCInsuredRelatedSet tmLCInsuredRelatedSet = new LCInsuredRelatedSet();
		tmLCInsuredRelatedSet.set(getLCInsuredRelatedSet(tLCInsuredSet));
		tOneCardData.add(tmLCInsuredRelatedSet);
		// 受益人
		LCBnfSet tLCBnfSet = new LCBnfSet();
		tLCBnfSet.set((LCBnfSet) OneCardData.getObjectByObjectName("LCBnfSet",
				0));
		for (int i = 1; i <= tLCBnfSet.size(); i++) {
			if (tLCBnfSet.get(i).getBnfType() == null
					|| tLCBnfSet.get(i).getBnfType().equals("")) {
				tLCBnfSet.get(i).setBnfType("1");
			}
		}
		tOneCardData.add(tLCBnfSet);
		// 特约信息
		LCSpecSet tLCSpecSet = new LCSpecSet();
		tOneCardData.add(tLCSpecSet);

		// 将操作信息打包
		tOneCardData.add(mGlobalInput);

		return tOneCardData;
	}

	// 填充oneCard中所需的主被保人数据,
	private static LCInsuredSchema getMainInsured(LCInsuredSet tLCInsuredSet) {
		if (tLCInsuredSet == null || tLCInsuredSet.size() == 0) {
			return null;
		}
		LCInsuredSchema insured = new LCInsuredSchema();
		for (int i = 1; i <= tLCInsuredSet.size(); i++) {

			if (tLCInsuredSet.get(i).getRelationToMainInsured().equals("00")
					&& tLCInsuredSet.get(i).getSequenceNo().equals("1")) {
				insured.setSchema(tLCInsuredSet.get(i).getSchema());
				break;
			}
		}
		return insured;
	}

	// 填充oneCard中所需的LCInsuredRelatedSet
	private static LCInsuredRelatedSet getLCInsuredRelatedSet(
			LCInsuredSet tLCInsuredSet) {
		if (tLCInsuredSet == null || tLCInsuredSet.size() == 0) {
			return null;
		}

		LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		for (int i = 1; i <= tLCInsuredSet.size(); i++) {
			tLCInsuredSchema.setSchema(tLCInsuredSet.get(i));
			if (!tLCInsuredSchema.getRelationToMainInsured().equals("00")
					&& !tLCInsuredSchema.getSequenceNo().equals("1")) {
				LCInsuredRelatedSchema tLCInsuRelatedSchema = new LCInsuredRelatedSchema();
				Reflections ref = new Reflections();
				ref.transFields(tLCInsuRelatedSchema, tLCInsuredSchema); // 客户表
				tLCInsuRelatedSchema.setMainCustomerNo(getMainInsured(
						tLCInsuredSet).getSequenceNo());
				tLCInsuRelatedSchema.setCustomerNo(tLCInsuredSchema
						.getSequenceNo());
				tLCInsuRelatedSchema.setRelationToInsured(tLCInsuredSchema
						.getRelationToMainInsured());
				tLCInsuredRelatedSet.add(tLCInsuRelatedSchema);
			}
		}
		//
		return tLCInsuredRelatedSet;
	}

	/** ***准备数据链表,用于进行校验**** */
	private boolean getAllCheckList() {
		selltypeList = this.getCodeList("selltype");
		sexList = this.getCodeList("sex");
		idTypeList = this.getCodeList("idtype");
		occuTypeList = this.getCodeList("occupation1");
		relationList = this.getCodeList("relation");
		bnfOrderList = this.getCodeList("bnforder");
		occuList = this.getOccupation();
		return true;
	}

	/**
	 * 效验一张保单的信息 每次得到一张保单的全部 数据(包括 投保人的LDPreson,投保人的LCAddress,
	 * LCCont,LCAppnt,,LCInsured,LCPol,LCBnf)
	 */
	private boolean checkContData(VData sOneCardData, String contNo) {
		// 校验保单信息
		if (checkLCCont(sOneCardData, contNo) == false) {
			return false;
		}
		// 校验投保人
		if (checkLCAppnt(sOneCardData, contNo) == false) {
			return false;
		}

		if (checkLDPerson(sOneCardData, contNo) == false) {
			return false;
		}

		if (checkLCAddress(sOneCardData, contNo) == false) {
			return false;
		}
		// 校验被保人
		if (checkLCInsured(sOneCardData, contNo) == false) {
			return false;
		}
		// 校验险种 或 产品组合
		if (checkLCPol(sOneCardData, contNo) == false) {
			return false;
		}
		// 校验受益人
		if (checkLCBnf(sOneCardData, contNo) == false) {
			return false;
		}

		return true;
	}

	/** *****校验LCCont保单信息******* */
	private boolean checkLCCont(VData sOneCardData, String contNo) {
		// 校验合同基本信息(LCCont表)
		LCContSchema tLCCont = (LCContSchema) sOneCardData
				.getObjectByObjectName("LCContSchema", 0);
		if (tLCCont == null) {
			buildError("checkLCCont", "保单 < " + contNo + " > 获取卡信息失败");
			return false;
		}

		// 校验 如果 "保险凭证号码"或"业务员代码"代码为空 则返回错误
		String sContNo = tLCCont.getContNo();
		String sAgentCode = tLCCont.getAgentCode();
		if (("").equals(sContNo) || ("").equals(sAgentCode)) {
			buildError("checkLCCont", "保单 < " + contNo
					+ " >保险凭证号码 和 业务员代码均不能为空.");
			return false;
		}

		// 校验保单号码是否为空
		String prtno = tLCCont.getForceUWReason();
		if (prtno == null || prtno.equals("")) {
			buildError("checkLCCont", "保单 < " + contNo + " >保单号不能为空.");
			return false;
		}

		// 校验业务员是否存在
		LAAgentDB sLAAgentDB = new LAAgentDB();
		sLAAgentDB.setAgentCode(sAgentCode);
		if (!sLAAgentDB.getInfo()) {
			buildError("checkLCCont", "保单 < " + contNo + " >业务员代码查询失败.");
			return false;
		}

		// 如果投保日期大于当前日期则返回错误
		String sPolApplyDate = tLCCont.getPolApplyDate();
		if (!("").equals(sContNo)) {
			if (PubFun.calInterval(sCurrentDate, sPolApplyDate, "D") > 0) {
				buildError("checkLCCont", "保单 < " + contNo + " >投保日期不能大于当前日期");
				return false;
			}
		}

		return true;
	}

	// 校验合同投保人基本信息(LCAppntt表)
	private boolean checkLCAppnt(VData sOneCardData, String contNo) {
		LCAppntSchema tLCAppnt = (LCAppntSchema) sOneCardData
				.getObjectByObjectName("LCAppntSchema", 0);
		if (tLCAppnt == null) {
			buildError("checkLCAppnt", "保单 < " + contNo + " >获取投保人信息失败");
			return false;
		}

		String sAppntName = tLCAppnt.getAppntName(); //
		String sAppntSex = tLCAppnt.getAppntSex();
		String sAppntBirthday = tLCAppnt.getAppntBirthday();
		String sAppntIDType = tLCAppnt.getIDType();
		String sAppntIDNo = tLCAppnt.getIDNo();
		String sOccupationCode = tLCAppnt.getOccupationCode();
		String sOccupationType = tLCAppnt.getOccupationType();

		logger.debug("********sAppntName====  " + sAppntName);
		logger.debug("********sAppntSex====  " + sAppntSex);
		logger.debug("********sAppntBirthday====  " + sAppntBirthday);
		logger.debug("********sAppntIDType====  " + sAppntIDType);
		logger.debug("********sAppntIDNo====  " + sAppntIDNo);
		logger.debug("********sOccupationCode====  " + sOccupationCode);
		logger.debug("********sOccupationType====  " + sOccupationType);

		//
		if (sAppntName == null || ("").equals(sAppntName)
				||
				// sAppntSex == null || ("").equals(sAppntSex) ||
				sAppntIDType == null || ("").equals(sAppntIDType)
				|| sAppntIDNo == null || ("").equals(sAppntIDNo)) {
			buildError("checkLCAppnt", "保单 < " + contNo
					+ " >投保人基本资料(姓名、性别、证件类型及号码)不能为空。");
			return false;
		}

		if (!idTypeList.contains(sAppntIDType)) {
			buildError("checkLCAppnt", "保单 < " + contNo + " >投保人证件类型填写有误");
			return false;
		}

		//
		if (!sAppntIDType.equals("0")
				&& (sAppntBirthday == null || sAppntBirthday.equals("")
						|| sAppntSex == null || sAppntSex.equals(""))) {

			buildError("checkLCAppnt", "保单 < " + contNo
					+ " >证件类型不是身份证，投保人出生日期和性别不能为空");
			return false;
		}

		// if(sAppntSex!=null && sAppntSex.equals(""))
		// {
		// if (!sexList.contains(sAppntSex) ||
		// !idTypeList.contains(sAppntIDType)) {
		// buildError("checkLCAppnt","保单 < " + contNo + " >投保人性别填写有误");
		// return false;
		// }
		// }

		if (sAppntIDType.equals("0")
				&& (sAppntIDNo.length() != 15 && sAppntIDNo.length() != 18)) {
			buildError("checkLCAppnt", "保单 < " + contNo
					+ " >投保人身份证号码填写有误,必须为 15或18 位");
			return false;
		}

		// if (sOccupationType==null || ("").equals(sOccupationType) ||
		// !occuTypeList.contains(sOccupationType))
		// {
		// buildError("checkLCAppnt", "保单 < "+contNo+"
		// >保单录入的投保人职业类别不能为空或职业类别有误！");
		// return false;
		// }

		// if(sOccupationCode!=null && !("").equals(sOccupationCode))
		// {
		// if (!occuList.contains(sOccupationCode))
		// {
		// buildError("checkLCAppnt", "保单 < "+contNo+" >保单录入的投保人职业代码有误！");
		// return false;
		// }
		// }

		return true;
	}

	private boolean checkLDPerson(VData sOneCardData, String contNo) {
		return true;
	}

	private boolean checkLCAddress(VData sOneCardData, String contNo) {
		return true;
	}

	private boolean checkLCInsured(VData sOneCardData, String contNo) {
		LCInsuredSet insuredSet = (LCInsuredSet) sOneCardData
				.getObjectByObjectName("LCInsuredSet", 0);

		if (insuredSet == null || insuredSet.size() == 0) {
			buildError("checkLCInsured", "保单 < " + contNo + " >被保人不能为空！");
			return false;
		}
		int insuredNum = insuredSet.size();
		LCInsuredSchema sLCInsuredSchema = new LCInsuredSchema();
		ArrayList tempSeqList = new ArrayList();// 用于缓存被保人序号
		String sSequenceNo = sLCInsuredSchema.getSequenceNo();
		String sRelaAppnt = sLCInsuredSchema.getRelationToAppnt();
		String sRelaMainInsu = sLCInsuredSchema.getRelationToMainInsured();
		String sInsuName = sLCInsuredSchema.getName();
		String sInsuSex = sLCInsuredSchema.getSex();
		String sInsuBirthday = sLCInsuredSchema.getBirthday();
		String sInsuIDNo = sLCInsuredSchema.getIDNo();
		String sInsuIDType = sLCInsuredSchema.getIDType();
		String sInsuOccuCode = sLCInsuredSchema.getOccupationCode();
		String sInsuOccuType = sLCInsuredSchema.getOccupationType();
		String sInsuredState = sLCInsuredSchema.getInsuredStat();

		for (int i = 1; i <= insuredNum; i++) {
			sLCInsuredSchema = insuredSet.get(i);
			sSequenceNo = sLCInsuredSchema.getSequenceNo();
			sRelaAppnt = sLCInsuredSchema.getRelationToAppnt();
			sRelaMainInsu = sLCInsuredSchema.getRelationToMainInsured();
			sInsuName = sLCInsuredSchema.getName();
			sInsuSex = sLCInsuredSchema.getSex();
			sInsuBirthday = sLCInsuredSchema.getBirthday();
			sInsuIDNo = sLCInsuredSchema.getIDNo();
			sInsuIDType = sLCInsuredSchema.getIDType();
			sInsuOccuCode = sLCInsuredSchema.getOccupationCode();
			sInsuOccuType = sLCInsuredSchema.getOccupationType();
			sInsuredState = sLCInsuredSchema.getInsuredStat();
			// 判断被保人序号
			if (sSequenceNo == null || sSequenceNo.equals("")) {
				buildError("checkLCInsured", "保单 < " + contNo
						+ " >保单的被保人序号不允许为空！");
				return false;
			} else if (tempSeqList.contains(sSequenceNo)) {
				buildError("checkLCInsured", "保单 < " + contNo
						+ " >保单存在重复的被保人序号！");
				return false;
			} else {
				tempSeqList.add(sLCInsuredSchema.getSequenceNo());
			}
			//
			if (sRelaAppnt == null || sRelaAppnt.equals("")
					|| sRelaMainInsu == null || sRelaMainInsu.equals("")
					|| sInsuName == null
					|| sInsuName.equals("")
					||
					// sInsuSex == null || sInsuSex.equals("") ||
					sInsuIDNo == null || sInsuIDNo.equals("")
					|| sInsuIDType == null || sInsuIDType.equals("")
					|| sInsuOccuType == null || sInsuOccuType.equals("")) {
				String errInfo = "被保人的基本资料(如与投保人、主被保人关系 和 姓名、证件类型及号码、职业类别等)不允许为空";
				buildError("checkLCInsured", "保单 < " + contNo + " >第"
						+ sSequenceNo + "号" + errInfo);
				return false;
			}

			// if(i==1&&(!sRelaMainInsu.equals("00"))){
			// buildError("checkLCInsured", "保单 < "+contNo+" >第1号被保人必须是主被保人" );
			// return false;
			// }

			//

			// 校验激活字段，如果为空则默认为0,如果录入则必须为0或者1
			if (sInsuredState == null || sInsuredState.equals("")) {

				sLCInsuredSchema.setInsuredStat("0");
			} else if (!(sInsuredState.equals("0") || sInsuredState.equals("1"))) {
				buildError("checkLCInsured", "保单 < " + contNo
						+ " >激活标志录入有误！激活标志必须是‘0’或则‘1’");
				return false;

			} else {

			}

			if (!sInsuIDType.equals("0")
					&& (sInsuBirthday == null || sInsuBirthday.equals("")
							|| sInsuSex == null || sInsuSex.equals(""))) {
				buildError("checkLCInsured", "保单 < " + contNo + " >第"
						+ sSequenceNo + "号被保人的证件不是身份证，因此被保人出生日期和性别不能为空");
				return false;
			}

			if (sInsuIDType.equals("0")
					&& (sInsuIDNo.length() != 15 && sInsuIDNo.length() != 18)) {
				buildError("checkLCAppnt", "保单 < " + contNo + " >第"
						+ sSequenceNo + "号被保人身份证号码填写有误,必须为 15或18 位");
				return false;
			}

			if (sInsuOccuType == null || ("").equals(sInsuOccuType)
					|| !occuTypeList.contains(sInsuOccuType)) {
				buildError("checkLCAppnt", "保单 < " + contNo + " >第"
						+ sSequenceNo + "号被保人职业类别不能为空或职业类别有误！");
				return false;
			}

			if (sInsuOccuCode != null && !("").equals(sInsuOccuCode)) {
				if (!occuList.contains(sInsuOccuCode)) {
					buildError("checkLCAppnt", "保单 < " + contNo + " >第"
							+ sSequenceNo + "号被保人职业代码有误！");
					return false;
				}
			}

		}

		return true;
	}

	private boolean checkLCPol(VData sOneCardData, String contNo) {
		// 获取TransferData信息
		TransferData sTransferData = new TransferData();
		sTransferData = (TransferData) sOneCardData.getObjectByObjectName(
				"TransferData", 0);
		if (sTransferData == null) {
			buildError("checkLCPol", "保单 < " + contNo + " >保单中获取其它信息失败");
			return false;
		}
		String ISPlanRisk = (String) sTransferData.getValueByName("ISPlanRisk");
		String ContPlanCode = (String) sTransferData
				.getValueByName("ContPlanCode");
		String ContPlanMult = (String) sTransferData.getValueByName("Mult");
		// 获取险种信息
		LCPolSet polSet = (LCPolSet) sOneCardData.getObjectByObjectName(
				"LCPolSet", 0);
		int polNum = polSet.size();

		// 校验产品组合
		if ("Y".equals(ISPlanRisk)) {
			if (ContPlanCode == null || ContPlanMult == null
					|| ContPlanCode.equals("") || ContPlanMult.equals("")) {
				buildError("checkLCPol", "保单 < " + contNo + " >产品组合信息录入有误");
				return false;
			}

			LDPlanDB tLDPlanDB = new LDPlanDB();
			tLDPlanDB.setContPlanCode(ContPlanCode);
			tLDPlanDB.setPlanType("0");
			// tLDPlanDB.getInfo();
			if (!tLDPlanDB.getInfo()) {
				buildError("checkLCPol", "保单 < " + contNo + " >产品组合代码[ "
						+ ContPlanCode + " ]查询失败");
				return false;
			}
			// LDPlanSet tLDPlanSet = new LDPlanSet();
			// tLDPlanSet=tLDPlanDB.query();
			// if(tLDPlanSet!=null || tLDPlanSet.size()!=1) { }

			// 如果录入产品组合，就不能录入险种信息
			if (polNum > 0) {
				buildError("checkLCPol", "保单 < " + contNo
						+ " >已经录入了产品组合，就不能在录入险种信息。");
				return false;
			}

		} else {
			// 如果没有产品组合，则险种信息不能为空
			if (polNum == 0) {
				buildError("checkLCPol", "保单 < " + contNo
						+ " >的险种信息为空，请录入险种信息。");
				return false;
			}
		}

		// 缓存主险编码
		String mainRiskCode = null;

		// 校验险种
		for (int i = 1; i <= polNum; i++) {
			LCPolSchema polSchema = polSet.get(i);
			// 校验险种是否存在
			String riskCode = polSchema.getRiskCode();

			LMRiskSchema riskSchema = new LMRiskSchema();
			riskSchema.setRiskCode(riskCode);
			if (!riskSchema.getDB().getInfo()) {
				buildError("checkLCPol", "保单 < " + contNo + " >保单中录入了错误的险种代码:"
						+ riskCode);
				return false;
			}

			if (i == 1) {
				mainRiskCode = polSchema.getMainPolNo();

				LMRiskAppDB tRiskDB = new LMRiskAppDB();
				tRiskDB.setRiskCode(mainRiskCode);
				if (!tRiskDB.getInfo() || !tRiskDB.getSubRiskFlag().equals("M")) {
					buildError("checkLCPol", "保单 < " + contNo
							+ " >的第一位录入的险种的主险编码不存在对应的险种或则其对应的险种不是主险！");
					return false;
				}

				if (!riskCode.equals(mainRiskCode)) {
					buildError("checkLCPol", "保单 < " + contNo
							+ " >的第一位录入的险种必须是主险！");
					return false;
				}

			}

			else {
				if (!polSchema.getMainPolNo().equals(mainRiskCode)) {
					buildError("checkLCPol", "保单 < " + contNo + " >的第" + i
							+ "位录入的险种的主险编码录入有误！");
					return false;
				}
			}
		}
		return true;
	}

	/* 校验受益人信息 */
	private boolean checkLCBnf(VData sOneCardData, String contNo) {

		LCBnfSet bnfSet = (LCBnfSet) sOneCardData.getObjectByObjectName(
				"LCBnfSet", 0);
		int bnfNum = bnfSet.size();

		// 查询出这张保单下的所有被保人的序号
		ArrayList seqList = new ArrayList();
		LCInsuredSet insuredSet = (LCInsuredSet) sOneCardData
				.getObjectByObjectName("LCInsuredSet", 0);
		int insNum = insuredSet.size();
		for (int i = 1; i <= insNum; i++) {
			LCInsuredSchema insSchema = insuredSet.get(i);
			seqList.add(insSchema.getSequenceNo());
		}
		// 存放每个被保人及其受益人的信息
		MMap bnfToInsu = new MMap();

		for (int i = 1; i <= bnfNum; i++) {
			LCBnfSchema bnfSchema = bnfSet.get(i);

			// 因为不知道受益人挂在险种下还是挂在保单下，受益份额暂不校验

			// 检验受益的所属被保人的序号是否录入正确,目前不知道放在哪个字段,暂时当做是InsuredNo
			if (!seqList.contains(bnfSchema.getInsuredNo())) {
				this.allMessage.append(" 第 " + i + "个受益人没有录入所属被保人序号！");
				buildError("checkLCBnf", "保单" + contNo + " 第 " + i
						+ " 个受益人没有录入所属被保人序号！");
				return false;
			}

			// 检验受益人和被保人的关系录入是否有误
			if (!this.relationList.contains(bnfSchema.getRelationToInsured())) {
				this.allMessage.append(" 第 " + i + "个受益人和被保人的关系录入有误！");
				buildError("checkLCBnf", "保单" + contNo + " 第 " + i
						+ " 个受益人和被保人的关系录入有误！");
				return false;
			}

			// 如果是本人并且知道是属于那个被保人，就不去校验别的字段，因为在被保人里面都已经录入了
			if (bnfSchema.getRelationToInsured().equals("00")) {
				return true;
			}

			// 校验受益人姓名--必须存在
			if (bnfSchema.getName() == null || bnfSchema.getName().equals("")) {
				this.allMessage.append(" 第 " + i + "个受益人没有录入姓名！");
				buildError("checkLCBnf", "保单" + contNo + " 第 " + i
						+ " 个受益人没有录入姓名！");
				return false;
			}

			// 检查被保人的收益顺序是否录入有误
			if (!this.bnfOrderList.contains(bnfSchema.getBnfGrade())) {
				this.allMessage.append(" 第 " + i + "个受益人的受益顺序录入有误！");
				buildError("checkLCBnf", "保单" + contNo + " 第 " + i
						+ " 个受益人的受益顺序录入有误！");
				return false;
			}

			// 检验受益人性别是否录入有误
			if (!this.sexList.contains(bnfSchema.getSex())) {
				this.allMessage.append(" 第 " + i + "个受益人的性别录入有误！");
				buildError("checkLCBnf", "保单" + contNo + " 第 " + i
						+ " 个受益人的性别录入有误！");
				return false;
			}

			// 校验投保人证件类型
			if (bnfSchema.getIDType() == null
					|| bnfSchema.getIDType().equals("")) {
				bnfSchema.setIDType("0");
			}
			if (!this.idTypeList.contains(bnfSchema.getIDType())) {
				this.allMessage.append(" 第 " + i + "受益人证件类型录入有误！");
				buildError("checkLCBnf", "保单" + contNo + " 第 " + i
						+ " 号受益人证件类型录入有误！");
				return false;
			}

			// 如果证件类型为0(身份证)，则校验证件号码必须是15位,或则18位
			if (bnfSchema.getIDType().equals("0")) {
				if (!(bnfSchema.getIDNo().length() == 18 || bnfSchema.getIDNo()
						.length() == 15)) {
					this.allMessage.append(" 第 " + i
							+ "受益人证件号码录入有误：身份证必须是15位或则18位！");
					buildError("checkLCBnf", "保单" + contNo + " 第 " + i
							+ " 受益人证件号码录入有误：身份证必须是15位或则18位！");
					return false;
				}
			}

			// 检验受益的所属被保人的序号是否录入正确,目前不知道放在哪个字段,暂时当做是InsuredNo
			String seq = bnfSchema.getInsuredNo();
			if (bnfToInsu.get(seq) == null) {
				LCBnfSet tBnfSet = new LCBnfSet();
				tBnfSet.add(bnfSchema);
				bnfToInsu.put(seq, tBnfSet);
			} else {
				LCBnfSet tBnf = (LCBnfSet) (bnfToInsu.get(seq));
				tBnf.add(bnfSchema);
			}
		}
		// 校验每个被保人下的受益人的受益份额
		if (!checkBnfLot(contNo, bnfToInsu)) {
			return false;
		}
		return true;
	}

	// 校验每个被保人下的受益人的受益份额
	private boolean checkBnfLot(String contNo, MMap bnfToInsu) {
		int bnfNumToOneInsu = bnfToInsu.keySet().size();
		for (int i = 1; i <= bnfNumToOneInsu; i++) {
			LCBnfSet tBnfSet = (LCBnfSet) bnfToInsu.get(bnfToInsu
					.getKeyByOrder(String.valueOf(i)));
			if (!checkBnfLotInOneInsu(contNo, tBnfSet)) {
				return false;
			}
		}
		return true;
	}

	// 校验某一个被保人下的受益份额
	private boolean checkBnfLotInOneInsu(String contNo, LCBnfSet tBnfSet) {

		// 遍历一个被保人下的每一个受益级别的数据
		String insuredNum = tBnfSet.get(1).getInsuredNo();// 被保人的序号

		MMap tMap = new MMap();
		int tSize = tBnfSet.size();
		for (int i = 1; i <= tSize; i++) {
			LCBnfSchema tBnf = tBnfSet.get(i);
			String BnfGrade = tBnf.getBnfGrade();
			double tDouble = tBnf.getBnfLot();
			Object tObject = tMap.get(BnfGrade);
			if (tObject == null) {
				tMap.put(BnfGrade, new Double(tDouble));
			} else {
				double added = ((Double) tObject).doubleValue() + tDouble;
				tMap.put(BnfGrade, new Double(added));
			}

		}

		int tMapSize = tMap.keySet().size();
		// 即如果录入受益级别为3的，则受益级别为1，2的必须录满受益份额1。并且3也要录到1个受益份额。
		ArrayList gradeList = new ArrayList();

		int gradeListSize = gradeList.size();// 最高受益级别

		for (int i = 1; i <= gradeListSize; i++) {
			gradeList.add(String.valueOf(i));
		}
		for (int i = 1; i <= tMapSize; i++) {
			String tStr = (String) tMap.getKeyByOrder(String.valueOf(i));
			gradeList.remove(tStr);
		}
		if (!gradeList.isEmpty()) {
			StringBuffer tStrBuff = new StringBuffer();
			int tempSize = gradeList.size();
			for (int i = 0; i < tempSize; i++) {
				tStrBuff.append(" ");
				tStrBuff.append(gradeList.get(i));
				tStrBuff.append(" ");
			}
			this.allMessage.append(" " + insuredNum + "号被保人险种受益人下,你录入了"
					+ gradeListSize + "受益级别。但是没录入" + tStrBuff + "受益级别");
			buildError("checkBnfLotInOneInsu", "保单" + contNo + " 第 "
					+ insuredNum + "号被保人险种受益人下,你录入了" + gradeListSize
					+ "受益级别。但是没录入" + tStrBuff + "受益级别");
			return false;
		}

		// 应该每个受益级别的受益份额和为1。

		for (int i = 1; i <= tMapSize; i++) {
			String tStr = (String) tMap.getKeyByOrder(String.valueOf(i));
			double tDbouble = ((Double) tMap.get(tStr)).doubleValue();
			if ((tDbouble - 1 > 0.0001) || (1 - tDbouble > 0.0001)) {
				this.allMessage.append(insuredNum + "号被保人险种下的受益人受益份额之和为"
						+ tDbouble + "。应该为1！");
				buildError("checkBnfLotInOneInsu", "保单" + contNo + " 第 "
						+ insuredNum + "号被保人险种下的受益人受益份额之和为" + tDbouble
						+ "。应该为1！");
				return false;
			}
		}
		return true;
	}

	/** ****捕获错误后，将错误信息保存起来****** */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpCardImport";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		mErrors.addOneError(cError);
	}

	// 得到错误信息,在部分签单完成时很有用。得到额外的信息
	private String getErrorMessage(CErrors tErrors) {

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

	private ArrayList getCodeList(String codeType) {
		ArrayList codeList = new ArrayList();
		// 初始化得到一个链表
		SSRS resultSet = new ExeSQL()
				.execSQL("select code from ldcode where codetype='" + codeType
						+ "' ");
		for (int i = 1; i < resultSet.MaxRow; i++) {
			codeList.add(resultSet.GetText(i, 1));
		}
		return codeList;
	}

	private ArrayList getOccupation() {
		ArrayList codeList = new ArrayList();
		// 初始化得到一个链表
		SSRS resultSet = new ExeSQL()
				.execSQL("select OccupationCode from ldoccupation where worktype='GR'");
		for (int i = 1; i < resultSet.MaxRow; i++) {
			codeList.add(resultSet.GetText(i, 1));
		}
		return codeList;
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

			String previousExcelFileName = "D:/lis/ui/upload/GrpImport20070425.xls";
			String configFileName = "D:/lis/ui/upload/GrpCardImport.xml";
			String createdFileName = "D:/lis/ui/upload/GrpImport20070425.xml";

			TransferData transferData = new TransferData();
			transferData
					.setNameAndValue("ExcelFileName", previousExcelFileName);
			transferData.setNameAndValue("ConfigFileName", configFileName);
			transferData.setNameAndValue("XmlFileName", createdFileName);
			tVData.add(transferData);

			GlobalInput tGI = new GlobalInput();
			tGI.Operator = "001";
			tGI.ManageCom = "86210000";
			tGI.ComCode = "86210000";

			tVData.add(tGI);

			GrpCardImport grpCardBL = new GrpCardImport();

			if (!grpCardBL.submitData(tVData, "")) {
			}

			String FlagStr = "Fail";
			String Content = "Fail";
			if (grpCardBL.mErrors.needDealError()) {
				FlagStr = "Fail";
				logger.debug("" + grpCardBL.mErrors.getErrContent());
				Content = grpCardBL.mErrors.getErrContent();

			} else {
				FlagStr = "Succ";
				Content = "导入成功";
			}
			logger.debug("*********************Content*********************");
			logger.debug(Content);

		} catch (Exception e) {
			logger.debug("***抛出如下异常：" + e.getMessage());
			e.printStackTrace();
		}
	}

}
