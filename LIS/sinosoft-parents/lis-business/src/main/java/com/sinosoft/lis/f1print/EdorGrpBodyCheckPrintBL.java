package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保全体检通知书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft Co. Ltd
 * </p>
 * 
 * @author：QianLy
 * @version：1.0
 * @CreateDate：2006-11-6
 */
public class EdorGrpBodyCheckPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(EdorGrpBodyCheckPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCPENoticeSchema mLCPENoticeSchema = new LCPENoticeSchema();

	private String mGrpContNo; // 集体合同号
	private String mContNo; // 合同号
	private String mCustomerNo; // 客户号
	private String mName; // 客户姓名
	private String mBirthDay; // 生日
	private String mOccupation; // 职业类别
	private boolean specFlag = false; // 是否显示特殊要求
	private String mSpec; // 特殊要求
	private String mPEDate; // 体检日期
	private String mAddress = ""; // 体检地址(体检医院地址+体检医院名称)
	private String mTel = ""; // 体检医院电话
	private String mUWOperator = ""; // 核保师
	private String mMakeDate; // 生成通知书时间
	private String mAgentCode; // 业务员编码
	private String mBank = ""; // 帐号
	private String mPrtSeq = ""; // 体检表的流水号
	private String mSaleChnl = "";
	private String mNameOnly = "";
	private String mIDNo = "";
	public static final String VTS_NAME = "EdorGrpPENotice.vts"; // 模板名称

	public EdorGrpBodyCheckPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!mOperate.equals("PRINT")) {
			CError tError = new CError();
			tError.moduleName = "BqPENoticePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
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

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:流水号不能为空！"));
			return false;
		}
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		mGrpContNo = mLOPRTManagerSchema.getStandbyFlag3();
		mContNo = mLOPRTManagerSchema.getOtherNo();

		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqPENoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单号失败！";
			this.mErrors.addOneError(tError);

			return false;
		}

		mCustomerNo = mLOPRTManagerSchema.getStandbyFlag1();
		if (mCustomerNo == null || mCustomerNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqPENoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取体检人客户号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "无保单信息！");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		return true;
	}

	private boolean getBaseData() {
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		// 查询客户信息
		strsql = "select name,birthday,(select occupationname from ldoccupation where trim(occupationcode) = trim(ldperson.occupationcode)),trim(sex),idno "
				+ " from ldperson "
				+ "  where customerno = '"
				+ mCustomerNo
				+ "'";
		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "没有体检人的客户信息！");
			return false;
		}
		mNameOnly = tssrs.GetText(1, 1);
		mName = tssrs.GetText(1, 1);
		mBirthDay = BqNameFun.getFDate(tssrs.GetText(1, 2));
		mOccupation = tssrs.GetText(1, 3);
		mIDNo = tssrs.GetText(1, 5);
		// 查询体检信息
		strsql = "select remark,pedate,peaddress from lcpenotice where prtseq = '"
				+ mPrtSeq
				+ "' "
				+ " and contno = '"
				+ mContNo
				+ "' "
				+ " and customerno = '" + mCustomerNo + "' ";
		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "没有体检信息！");
			return false;
		}
		mSpec = tssrs.GetText(1, 1);
		if (mSpec != null && !mSpec.trim().equals("")) {
			specFlag = true;
		}
		mPEDate = tssrs.GetText(1, 2);
		mPEDate = BqNameFun.getFDate(mPEDate);
		LAComSchema tLAComSchema = new LAComSchema();
		LAComDB tLAComDB = new LAComDB();
		tLAComDB.setAgentCom(mLCContSchema.getAgentCom());
		if (tLAComDB.getInfo()) {
			mBank = tLAComDB.getName();
			logger.debug("bankname:" + mBank);
		}
		mUWOperator = mLOPRTManagerSchema.getReqOperator();
		mAgentCode = mLCContSchema.getAgentCode();
		mMakeDate = mLOPRTManagerSchema.getMakeDate();
		mMakeDate = BqNameFun.getFDate(mMakeDate);

		mSaleChnl = mLCContSchema.getSaleChnl();

		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		strsql = "select peitemcode,peitemname " + " from lcpenoticeitem "
				+ " where nvl(freepe,0) <> 'Y'" + " and contno = '" + mContNo
				+ "' " + " and prtseq = '" + mPrtSeq + "' order by peitemcode ";
		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "没有体检项目信息！");
			return false;
		}

		String tPEItem = "";
		boolean Phisical = false;
		for (int p = 1; p <= tssrs.getMaxRow(); p++) {
			if (tssrs.GetText(p, 2).equals("身高")
					|| tssrs.GetText(p, 2).equals("体重")
					|| tssrs.GetText(p, 2).equals("血压")
					|| tssrs.GetText(p, 2).equals("内科检查")
					|| tssrs.GetText(p, 2).equals("外科检查")) {
				Phisical = true;
			}
		}
		if (Phisical == true) {
			tPEItem = "1.临床物理检查  ";
			for (int q = 2; q <= tssrs.getMaxRow() - 4; q++) {
				tPEItem = tPEItem + q + "." + tssrs.GetText(q + 4, 2) + "  ";
			}
		} else {
			for (int i = 1; i <= tssrs.getMaxRow(); i++) {
				tPEItem = tPEItem + i + "." + tssrs.GetText(i, 2) + " ";

			}
		}
		String[] HosTitle = new String[3];
		HosTitle[0] = "name";
		HosTitle[1] = "address";
		HosTitle[2] = "phone";
		ListTable tHosListTable = new ListTable();
		String strHos[] = null;
		tHosListTable.setName("Hospital"); // 对应模版体检部分的行对象名
		String tCom = mLCContSchema.getManageCom();
		if (tCom != null && tCom.length() >= 4) {
			tCom = mLCContSchema.getManageCom().substring(0, 4);
		}

		strsql = "select hospitname,remark,phone from ldhospital where 1=1 "
				+ "and areacode like '' || substr((select managecom from lccont where ContNo = '"
				+ mContNo
				+ "'),0,nvl((select codealias from ldcode where codetype = 'pehospital' and code = '"
				+ tCom + "'),8)) || '%%' ";

		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "没有体检地址信息！");
			// return false;
		} else {
			for (int j = 1; j <= tssrs.getMaxRow(); j++) {
				strHos = new String[3];
				strHos[0] = tssrs.GetText(j, 1);
				strHos[1] = tssrs.GetText(j, 2); // 序号对应的内容
				strHos[2] = tssrs.GetText(j, 3);
				tHosListTable.add(strHos);
			}
		}

		String[] allArr = BqNameFun.getAllNames(mAgentCode);
		texttag.add("BarCode1", "UN072");
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		// texttag.add("LCCont.ContNo", mContNo);//Modifid By QianLy on 2006-12-12 for
		// BUG 8036
		texttag.add("LCCont.ContNo", mGrpContNo);
		texttag.add("LCCont.AppntName", mName);
		texttag.add("Birthday", mBirthDay);
		texttag.add("WorkType", mOccupation);
		texttag.add("CheckDate", mPEDate);
		texttag.add("LAAgent.AgentCode", mAgentCode);
		texttag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		texttag.add("LABranchGroup.Name", allArr[BqNameFun.SALE_SERVICE]
				+ allArr[BqNameFun.DEPART]);
		texttag.add("SysDate", mMakeDate);
		texttag.add("LCCont.UWOperator", mUWOperator);
		texttag.add("CHECKITEM", tPEItem);
		if (mSaleChnl != null) {
			if (mSaleChnl.trim().equals("3") || mSaleChnl.trim().equals("")) {
				texttag.add("LCCont.BankCode", mBank);
				texttag.add("LCCont.AgentType", allArr[BqNameFun.DEPART]
						+ allArr[BqNameFun.TEAM]);
			}
		}
		if (specFlag) {
			texttag.add("SpecRequire", mSpec);
			tXmlExport.addDisplayControl("displaySpec");
		}
		texttag.add("ManageComName", BqNameFun.getCBranch(mLCContSchema
				.getManageCom()));
		texttag.add("LCCont.AppntName", mNameOnly);
		texttag.add("IDNO", mIDNo);
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tHosListTable, HosTitle); // 体检医院信息
		// tXmlExport.outputDocumentToFile("D:\\qly\\XMLDoc\\", "penotice");
		// //测试用
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
	// public static void main(String[] args)
	// {
	// logger.debug("test begin:");
	// EdorGrpBodyCheckPrintBL tEdorGrpBodyCheckPrintBL = new
	// EdorGrpBodyCheckPrintBL();
	//
	// VData tVData = new VData();
	//
	// GlobalInput tGlobalInput = new GlobalInput();
	// tGlobalInput.ManageCom = "86210000";
	// tGlobalInput.Operator = "001";
	//
	// String tPrtSeq = "8100000000310";
	// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	// tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
	//
	// tVData.add(tGlobalInput);
	// tVData.add(tLOPRTManagerSchema);
	// if(!tEdorGrpBodyCheckPrintBL.submitData(tVData, "PRINT"))
	// {
	// logger.debug("test failed");
	// }
	// logger.debug("test end");
	// }
}
