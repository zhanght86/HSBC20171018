package com.sinosoft.lis.bq;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBGrpContDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LMEdorItemDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpAddressDB;
import com.sinosoft.lis.db.LPGrpAppntDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.db.LPGrpFreeEdorDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorPrint2Schema;
import com.sinosoft.lis.schema.LPEdorPrintSchema;
import com.sinosoft.lis.schema.LPGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpAppntSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpFreeEdorSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vdb.LCGrpPolDBSet;
import com.sinosoft.lis.vdb.LPAddressDBSet;
import com.sinosoft.lis.vdb.LPEdorItemDBSet;
import com.sinosoft.lis.vdb.LPGrpAppntDBSet;
import com.sinosoft.lis.vdb.LPGrpEdorItemDBSet;
import com.sinosoft.lis.vdb.LPInsuredDBSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LMEdorItemSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpAppntSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpFreeEdorSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class PrtGrpEndorsementBL {
private static Logger logger = Logger.getLogger(PrtGrpEndorsementBL.class);
	private XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例，对于集体
	private XmlExport xmlexportDetail = new XmlExport(); // 新建一个XmlExport的实例，对于个人明细

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private MMap map = new MMap();

	// 取得的集体合同号码
	private String mGrpContNo = "";

	// 批单号
	private String mEdorNo = "";

	// 业务处理相关变量
	private TextTag textTag = new TextTag(); // 新建一个TextTag的实例，对于集体
	private TextTag textTagDetail = new TextTag(); // 新建一个TextTag的实例，对于个人明细

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private ListTable tlistTable;
	private ListTable tlistTableDetail;
	double sum1 = 0; // 本次收款金额
	double sum2 = 0; // 本次付款金额
	double sum4 = 0; // 实收/实付金额
	int sum3 = 0; // 详细变动清单改动的人数
	int ab = 0;
	int extra = 0; // 1:考虑附加险 0：不考虑附加险
	@SuppressWarnings("unused")
	private final String[] Bnfname = null; // 对于团体BC的变更 需要显示所有受益人的名字
	@SuppressWarnings("unused")
	private final String[] BnfID = null; // 对于团体BC的变更 需要显示所有受益人的ID
	int detailno = 0; // 记录详细批单的序号

	// private String strArr[] = null;
	int showExRisk = 0; // 判断批单打印是否显示附加险 1：显示 0：不显示
	int showAllBnf = 0; // 判断批单打印是否显示所有受益人 1：显示 0：不显示

	public PrtGrpEndorsementBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 从前台传批单号
	public PrtGrpEndorsementBL(String aEdorNo) {
		mEdorNo = aEdorNo;
		// mGrpContNo = aGrpContNo;
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!this.dealData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	@SuppressWarnings("unchecked")
	private boolean dealData() {
		logger.debug("\nStart Print Grp Endorsement App");

		boolean tFlag = false; // 打印主批单标记
		boolean DFlag = false; // 打印明细批单标记
		boolean qFlag = false; // 打印保全变动清单所关联操作的标记

		xmlexport.createDocument("PrtGrpEndorsementApp.vts", "printer"); // 最好紧接着就初始化xml文档
		tlistTableDetail = new ListTable();

		// //查询团险保全批改表中批单号为mEdorNo，批改状态为2（批改确认），核保状态为9（核保通过）的记录
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB.setEdorNo(mEdorNo);
		LPGrpEdorMainSet tLPGrpEdorMainSet = new LPGrpEdorMainSet();
		tLPGrpEdorMainSet.set(tLPGrpEdorMainDB.query());
		if (tLPGrpEdorMainSet.size() == 0) {
			buildError("dealData", "在LPGrpEdorMain中无相关批单号的数据");
			return false;
		}
		LPGrpEdorMainSchema tLPGrpEdorMainSchema = tLPGrpEdorMainSet.get(1);

		mGrpContNo = tLPGrpEdorMainSchema.getGrpContNo(); // 取得保全记录的合同号

		// 查询团险保全项目表中批单号为mEdorNo的记录
		String sql = "select * from LPGrpEdorItem where edorno='?mEdorNo?' order by MakeDate,MakeTime";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mEdorNo", mEdorNo);
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemDBSet();
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(sqlbv);
		if (tLPGrpEdorItemSet.size() == 0) {
			buildError("dealData", "团险保全项目表中无相关批单号的数据");
			return false;
		}

		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			LBGrpContDB tLBGrpContDB = new LBGrpContDB();
			tLBGrpContDB.setGrpContNo(mGrpContNo);
			if (!tLBGrpContDB.getInfo()) {
				return false;
			}
			Reflections aReflections = new Reflections();
			aReflections
					.transFields(tLCGrpContSchema, tLBGrpContDB.getSchema());
		} else {
			tLCGrpContSchema.setSchema(tLCGrpContDB.getSchema());
		}

		xmlexport.addDisplayControl("displayHead");
		textTag.add("EdorAcceptNo", tLPGrpEdorMainSchema.getEdorAcceptNo());
		textTag.add("EdorNo", tLPGrpEdorMainSchema.getEdorNo());
		textTag.add("GrpContNo", tLPGrpEdorMainSchema.getGrpContNo());
		textTag.add("BarCode1", tLPGrpEdorMainSchema.getEdorNo());
		textTag
				.add(
						"BarCodeParam1",
						"BarHeight=25&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		textTag.add("AppDate", tLPGrpEdorMainSchema.getEdorAppDate());
		textTag.add("ConfDate", tLPGrpEdorMainSchema.getMakeDate());
		textTag.add("Operator", tLPGrpEdorMainSchema.getOperator());
		textTag.add("PrintDate", StrTool.replace(StrTool.getDate(), "/", "-"));
		textTag.add("CustomerNo", tLCGrpContSchema.getAppntNo());
		textTag.add("AppntName", tLCGrpContSchema.getGrpName());

		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		tLCGrpAppntDB.setGrpContNo(tLPGrpEdorMainSchema.getGrpContNo());
		if (!tLCGrpAppntDB.getInfo()) {
			buildError("getDetailAD", "团单投保人表LCGrpAppnt中无相关集体合同号的记录");
			return false;
		}
		LCGrpAppntSchema tLCGrpAppntSchema = tLCGrpAppntDB.getSchema();

		// 取（保全团体客户地址表）和（团体客户地址表）的相关记录，生称相应schema

		LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
		tLCGrpAddressDB.setCustomerNo(tLCGrpAppntSchema.getCustomerNo());
		tLCGrpAddressDB.setAddressNo(tLCGrpAppntSchema.getAddressNo());
		if (!tLCGrpAddressDB.getInfo()) {
			buildError("getDetailAD", "团体客户地址表LCGrpAddress中无相关集体合同号的记录");
			return false;
		}
		LCGrpAddressSchema tLCGrpAddressSchema = tLCGrpAddressDB.getSchema();

		textTag.add("EdorAppZipCode", tLCGrpAddressSchema.getGrpZipCode());
		textTag.add("EdorAppAddress", tLCGrpAddressSchema.getGrpAddress());
		textTag.add("EdorAppName", tLCGrpAddressSchema.getLinkMan1());

		for (int i = 1; i <= tLPGrpEdorItemSet.size(); i++) {
			LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
			tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);
			// if (i == 1)
			// {
			// xmlexport.addDisplayControl("displayHead");
			// textTag.add("EdorNo", tLPGrpEdorItemSchema.getEdorNo());
			// textTag.add("GrpContNo", tLPGrpEdorItemSchema.getGrpContNo());
			// textTag.add("AppDate", tLPGrpEdorItemSchema.getEdorAppDate());
			// textTag.add("AppntName", tLCGrpContSchema.getGrpName());
			// }
			if (tLPGrpEdorItemSchema.getEdorType().equals("AN")) { // 投保人变更
				xmlexport.addDisplayControl("displayAN");
				if (!this.getDetailAN(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true;
			} else if (tLPGrpEdorItemSchema.getEdorType().equals("AD")) { // 投保人联系方式变更
				xmlexport.addDisplayControl("displayAD");
				if (!this.getDetailAD(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true;
			} else if (tLPGrpEdorItemSchema.getEdorType().equals("NI")) { // 被保人增加
				xmlexport.addDisplayControl("displayNI");
				if (!this.getDetailNI(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true;
				DFlag = true;
			} else if (tLPGrpEdorItemSchema.getEdorType().equals("BB")) { // 被保险人信息变更
				xmlexport.addDisplayControl("displayBB");
				if (!this.getDetailBB(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true; // 不用明细表
			} else if (tLPGrpEdorItemSchema.getEdorType().equals("LR")) { // 保单遗失补发
				xmlexport.addDisplayControl("displayLR");
				if (!this.getDetailLR(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true; // 不用明细表
			} else if (tLPGrpEdorItemSchema.getEdorType().equals("FC")) { // 自由格式批改
				xmlexport.addDisplayControl("displayFC");
				if (!this.getDetailFC(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true; // 不用明细表
			} else if (tLPGrpEdorItemSchema.getEdorType().equals("XT")) { // 协议退保
				xmlexport.addDisplayControl("displayXT");
				if (!this.getDetailXT(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true; // 不用明细表
			}

			else if (tLPGrpEdorItemSchema.getEdorType().equals("ZT")) { // 减人
				xmlexport.addDisplayControl("displayZT");
				if (!this.getDetailZT(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true;
				DFlag = true;
			}

			else if (tLPGrpEdorItemSchema.getEdorType().equals("LT")) { // 险种退保
				xmlexport.addDisplayControl("displayLT");
				if (!this.getDetailLT(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true; // 不用明细表
			} else if (tLPGrpEdorItemSchema.getEdorType().equals("WT")) { // 犹豫期退保
				xmlexport.addDisplayControl("displayWT");
				if (!this.getDetailWT(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true; // 不用明细表
			}

			else if (tLPGrpEdorItemSchema.getEdorType().equals("CT")) { // 合同终止
				xmlexport.addDisplayControl("displayCT");
				if (!this.getDetailCT(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true; // 不用明细表
			} else if (tLPGrpEdorItemSchema.getEdorType().equals("IC")) { // 被保人重要信息变更
				xmlexport.addDisplayControl("displayIC");
				if (!this.getDetailIC(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true;
				DFlag = true;
			}

			else { // 对没有的保全项目类型生成空打印数据
				if (!this.getDetailForBlankType(tLPGrpEdorItemSchema)) {
					return false;
				}
				tFlag = true; // 不用明细表
			}

			/** 此处可以继续添加其他保全项目的getDetail处理 */

			logger.debug("End Print Grp Endorsement App\n");

			// 判断是否进行保全变动清单的打印
			LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
			tLMEdorItemDB.setEdorCode(tLPGrpEdorItemSchema.getEdorType());
			tLMEdorItemDB.setAppObj("G");
			LMEdorItemSet tLMEdorItemSet = tLMEdorItemDB.query();
			if (tLMEdorItemSet.size() == 0) {
				buildError("tLMEdorItemSet", "在取得LMEdorItemSet的数据时发生错误");
				return false;
			}
			tLMEdorItemDB.setSchema(tLMEdorItemSet.get(1));

			if (tLMEdorItemDB.getGrpNeedList() != null
					&& tLMEdorItemDB.getGrpNeedList().equals("Y")) {
				qFlag = true;
				int tDisplayFlag; // 取得保全清单是否打迎险种信息的表示，1，2－不需要打印险种信息，3－需要打迎险种信息
				if (tLMEdorItemDB.getDisplayFlag() != null
						&& !"".equals(tLMEdorItemDB.getDisplayFlag())) {
					tDisplayFlag = Integer.parseInt(tLMEdorItemDB
							.getDisplayFlag());
				} else {
					tDisplayFlag = 2;
				}
				if (!this.BillData(tLPGrpEdorItemSchema, tDisplayFlag)) {
					return false;
				}
			}
		}
		if (DFlag && !qFlag) {
			CError.buildErr(this, "DFlag表识为真，但LMEdorItemSet表中无打印保全清单描述");
			return false;
		}
		// 打印补退费明细表
		getPayGetDetails(tLPGrpEdorItemSet);

		// 生成保全清单的xml数据
		if (qFlag) {
			// 生成主险名
			LMRiskDB bLMRiskDB = new LMRiskDB();
			LCGrpPolDB bLCGrpPolDB = new LCGrpPolDB();
			bLCGrpPolDB.setGrpContNo(mGrpContNo);
			LCGrpPolSet bLCGrpPolSet = new LCGrpPolSet();
			bLCGrpPolSet.set(bLCGrpPolDB.query());
			if (bLCGrpPolSet.size() == 0) {
				return false;
			} else {
				boolean flag = false;
				LCGrpPolSchema bLCGrpPolSchema;
				LMRiskAppDB bLMRiskAppDB = new LMRiskAppDB();
				String mainRiskName = "";
				String subRiskName = "";
				for (int j = 1; j <= bLCGrpPolSet.size(); j++) {
					bLCGrpPolSchema = new LCGrpPolSchema();
					bLCGrpPolSchema.setSchema(bLCGrpPolSet.get(j));
					bLMRiskAppDB.setRiskCode(bLCGrpPolSchema.getRiskCode());
					if (!bLMRiskAppDB.getInfo()) {
						// mErrors.copyAllErrors(bLMRiskAppDB.mErrors);
						// buildError("dealData-getRiskApp",
						// "在取得LMRiskApp的数据时发生错误");
						// return false;
						continue;
					}
					if (bLMRiskAppDB.getSubRiskFlag().equals("M")) {
						bLMRiskDB.setRiskCode(bLCGrpPolSchema.getRiskCode());
						if (!bLMRiskDB.getInfo()) {
							// mErrors.copyAllErrors(bLMRiskDB.mErrors);
							// buildError("desalData-getLMRisk",
							// "在取得LMRisk的数据时发生错误");
							// return false;
							continue;
						}
						flag = true;
						mainRiskName += bLMRiskDB.getRiskName() + "、";
					} else if (bLMRiskAppDB.getSubRiskFlag().equals("S")) {
						bLMRiskDB.setRiskCode(bLCGrpPolSchema.getRiskCode());
						if (!bLMRiskDB.getInfo()) {
							// mErrors.copyAllErrors(bLMRiskDB.mErrors);
							// buildError("desalData-getLMRisk",
							// "在取得LMRisk的数据时发生错误");
							// return false;
							continue;
						}
						flag = true;
						subRiskName += bLMRiskDB.getRiskName() + "、";
					}
					if (flag == false) {
						// buildError("deal-getrisk", "没有相对应的险种！");
						// return false;
						continue;
					}
				}
				if (mainRiskName.endsWith("、")) {
					mainRiskName = mainRiskName.substring(0, mainRiskName
							.length() - 1);
				}
				if (subRiskName.endsWith("、")) {
					subRiskName = subRiskName.substring(0,
							subRiskName.length() - 1);
				}
				textTagDetail.add("RiskNameM", mainRiskName);
				textTagDetail.add("RiskNameS", subRiskName);
			}

			// 生成当天日期
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			textTagDetail.add("Today", df.format(new java.util.Date()));

			textTagDetail.add("EdorNo", mEdorNo);
			textTagDetail.add("GrpContNo", mGrpContNo);
			textTagDetail.add("GrpName", tLCGrpContSchema.getGrpName());
			textTagDetail.add("GrpNo", tLCGrpContSchema.getAppntNo());
			String sum11 = new DecimalFormat("0.00").format(Double
					.valueOf(String.valueOf(sum1)));
			String sum22 = new DecimalFormat("0.00").format(Double
					.valueOf(String.valueOf(sum2)));
			String sum44 = new DecimalFormat("0.00").format(Double
					.valueOf(String.valueOf(sum4)));
			textTagDetail.add("sum1", sum11);
			textTagDetail.add("sum2", sum22);
			textTagDetail.add("sum3", sum3);
			textTagDetail.add("sum4", sum44);

			// 加入textTagDetail
			if (textTagDetail.size() > 0) {
				xmlexportDetail.addTextTag(textTagDetail);
			}

			// 加入tlistTableDetail
			String strArr[] = new String[17];
			strArr[0] = "num";
			strArr[1] = "Name";
			strArr[2] = "sex";
			strArr[3] = "Birthday";
			strArr[4] = "IDType";
			strArr[5] = "IDNo";
			strArr[6] = "WorkType";
			strArr[7] = "RiskName";
			strArr[8] = "RiskName1";
			strArr[9] = "Prem";
			strArr[10] = "Amnt";
			strArr[11] = "Years";
			strArr[12] = "BnfName";
			strArr[13] = "GetMoney";
			strArr[14] = "Interest";
			strArr[15] = "sum";
			strArr[16] = "";

			xmlexportDetail.addListTable(tlistTableDetail, strArr);

		}

		// 打印主批单
		if (!tFlag) {

			buildError("dealData", "发生一个未知错误使主批单不能打印！");
			return false;

			// return true;
		}

		if (textTag.size() > 0) {
			xmlexport.addTextTag(textTag);
		}

		mResult.clear();
		MMap map = new MMap();

		// 生成主打印批单schema
		LPEdorPrintSchema tLPEdorPrintSchemaMain = new LPEdorPrintSchema();
		tLPEdorPrintSchemaMain.setEdorNo(mEdorNo);
		tLPEdorPrintSchemaMain.setManageCom(mGlobalInput.ManageCom);
		tLPEdorPrintSchemaMain.setPrtFlag("N");
		tLPEdorPrintSchemaMain.setPrtTimes(0);
		tLPEdorPrintSchemaMain.setMakeDate(PubFun.getCurrentDate());
		tLPEdorPrintSchemaMain.setMakeTime(PubFun.getCurrentTime());
		tLPEdorPrintSchemaMain.setOperator(mGlobalInput.Operator);
		tLPEdorPrintSchemaMain.setModifyDate(PubFun.getCurrentDate());
		tLPEdorPrintSchemaMain.setModifyTime(PubFun.getCurrentTime());
		InputStream ins = xmlexport.getInputStream();
		// xmlexport.outputDocumentToFile("c:\\", "xmlexport");
		tLPEdorPrintSchemaMain.setEdorInfo(ins);
		map.put(tLPEdorPrintSchemaMain, "BLOBINSERT");

		// 生成变动清单打印schema
		if (qFlag) {
			LPEdorPrint2Schema tLPEdorPrint2SchemaDetail = new LPEdorPrint2Schema();
			Reflections aReflections = new Reflections();
			aReflections.transFields(tLPEdorPrint2SchemaDetail,
					tLPEdorPrintSchemaMain);
			InputStream ins1 = xmlexportDetail.getInputStream();
			// xmlexportDetail.outputDocumentToFile("c:\\", "xmlexportDetail");
			tLPEdorPrint2SchemaDetail.setEdorInfo(ins1);
			map.put(tLPEdorPrint2SchemaDetail, "BLOBINSERT");
		}

		mResult.addElement(map);
		return true;
	}

	/**
	 * 获取团体保单数据，并初始化变动清单xml文档
	 * 
	 * @param tLPGrpEdorItemSchema
	 * @return
	 */
	private boolean BillData(LPGrpEdorItemSchema tLPGrpEdorItemSchema,
			int iDisplayFlag) {

		xmlexportDetail.createDocument("PrtGrpEndorsementBill.vts", "printer"); // 最好紧接着就初始化xml文档
		if (!this.getDetail(tLPGrpEdorItemSchema, iDisplayFlag)) {
			return false;
		}

		return true;
	}

	/**
	 * 获 取 保 险 合 同 保 全 变 动 清 单 数 据
	 * 
	 * @param tLCGrpPolSchema
	 * @param tLPGrpEdorMainSchema
	 * @return
	 */
	private boolean getDetail(LPGrpEdorItemSchema tLPGrpEdorItemSchema,
			int iDisplayFlag) {

		// 获取所有个人保全的数据
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorNo(tLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(tLPGrpEdorItemSchema.getEdorType());
		tLPEdorItemSet.set(tLPEdorItemDB.query());

		// // if(tLPGrpEdorItemSchema.getEdorType().equals("ZT")
		// // || tLPGrpEdorItemSchema.getEdorType().equals("GT")
		// // ) {
		// // for (int j=0; j<tLPEdorMainSet.size(); j++) {
		// // //因为这个保全项目没有为个人附加险生成批改主表，所以要自动生成批改主表
		// // String ztSql = "select * from lcpol where mainpolno='"+
		// tLPEdorMainSet.get(j+1).getPolNo()+"' and polno <> mainpolno";
		// // LCPolDB ztLCPolDB = new LCPolDB();
		// // LCPolSet ztLCPolSet = ztLCPolDB.executeQuery(ztSql);
		// //
		// // if (ztLCPolSet.size()!=0) {
		// // for(int i=1;i<=ztLCPolSet.size();i++) {
		// // LPEdorMainSchema ztLPEdorMainSchema = new LPEdorMainSchema();
		// // ztLPEdorMainSchema.setPolNo(ztLCPolSet.get(i).getPolNo());
		// // ztLPEdorMainSchema.setEdorNo(tLPGrpEdorMainSchema.getEdorNo());
		// //
		// ztLPEdorMainSchema.setEdorType(tLPGrpEdorMainSchema.getEdorType());
		// // tLPEdorMainSet.add(ztLPEdorMainSchema);
		// // }
		// // }
		// // }
		// // }
		//
		// if (tLPEdorMainSet.size()==0) {
		// //团体新增附加险没有个人保全主表数据，为了打印变动清单，所以要作一些特殊处理
		// if (tLPGrpEdorMainSchema.getEdorType().equals("NS")) {
		// String strSql = "select * from lcpol where prtno in (select prtno
		// from lcgrppol where grppolno='" + tLPGrpEdorMainSchema.getGrpPolNo()
		// + "') and appflag='2'";
		// LCPolDB tLCPolDB = new LCPolDB();
		// LCPolSet tLCPolSet = tLCPolDB.executeQuery(strSql);
		//
		// for (int i=0; i<tLCPolSet.size(); i++) {
		// LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		// tLPEdorMainSchema.setPolNo(tLCPolSet.get(i+1).getPolNo());
		// tLPEdorMainSchema.setEdorNo(tLPGrpEdorMainSchema.getEdorNo());
		// tLPEdorMainSchema.setEdorType(tLPGrpEdorMainSchema.getEdorType());
		// tLPEdorMainSchema.setGetMoney(tLCPolSet.get(i+1).getPrem());
		// tLPEdorMainSet.add(tLPEdorMainSchema);
		// }
		// }
		// else {
		// buildError("getDetail", "在取得个人保全主表数据时发生错误");
		// return false;
		// }
		// }

		String[] strArr = null;
		tlistTableDetail.setName("PRTGRPED");

		for (int k = 1; k <= tLPEdorItemSet.size(); k++) {
			// 获取个单保全主表数据
			LPEdorItemSchema tLPEdorItemSchema = tLPEdorItemSet.get(k);
			strArr = new String[17];

			// 取得保全被保人表
			LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();
			tLPInsuredDB.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPInsuredDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsuredDB.setContNo(tLPEdorItemSchema.getContNo());
			tLPInsuredDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
			if (!tLPInsuredDB.getInfo()) { // 此时尝试LCInsured表中取相关数据
				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				tLCInsuredDB.setContNo(tLPEdorItemSchema.getContNo());
				tLCInsuredDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
				if (!tLCInsuredDB.getInfo()) {
					buildError("getDetail", "LCInsured表中没有这个项目，继续处理下一个");
					// continue; //没有这个个人的项目，继续处理下一个
				} else {
					Reflections tReflections = new Reflections();
					tReflections.transFields(tLPInsuredSchema, tLCInsuredDB
							.getSchema());
				}

			} else {
				tLPInsuredSchema = tLPInsuredDB.getSchema();
			}

			// 获取保全项目名称
			LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
			tLMEdorItemDB.setEdorCode(tLPInsuredSchema.getEdorType());
			tLMEdorItemDB.setAppObj("G");
			LMEdorItemSet tLMEdorItemSet = tLMEdorItemDB.query();
			if (tLMEdorItemSet.size() == 0) {
				buildError("getDetail", "在LMEdorItem中没有对应的险种类型");
				// continue;
			}

			strArr[1] = tLMEdorItemSet.get(1).getEdorName();

			// 获取被保人姓名
			strArr[2] = tLPInsuredSchema.getName();
			// 获取被保人性别
			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCode(tLPInsuredSchema.getSex());
			tLDCodeDB.setCodeType("sex");
			tLDCodeDB.getInfo();
			strArr[3] = tLDCodeDB.getCodeName();
			// 获取被保人出生日期
			strArr[4] = tLPInsuredSchema.getBirthday();
			// 获取证件类型
			strArr[5] = tLPInsuredSchema.getIDType();
			// 获取证件号码
			strArr[6] = tLPInsuredSchema.getIDNo();
			// 获取职业工种
			strArr[7] = tLPInsuredSchema.getWorkType();
			// 获取险种名称
			if (iDisplayFlag > 2) { // 当显示险种标识大于2时才处理险种信息
				LPPolSchema tLPPolSchema = new LPPolSchema();
				LPPolDB tLPPolDB = new LPPolDB();
				tLPPolDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
				tLPPolDB.setEdorType(tLPEdorItemSchema.getEdorType());
				tLPPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
				if (!tLPPolDB.getInfo()) {
					LCPolDB tLCPolDB = new LCPolDB();
					tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
					if (!tLCPolDB.getInfo()) {
						buildError("getDetail", "个人险种表中没有这个项目，继续处理下一个");
						// continue; //个人险种表中没有这个项目，继续处理下一个
					} else {
						Reflections tReflections = new Reflections();
						tReflections.transFields(tLPPolSchema, tLCPolDB
								.getSchema());

					}
				} else {
					tLPPolSchema = tLPPolDB.getSchema();
				}
				LMRiskDB tLMRiskDB = new LMRiskDB();
				tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
				if (!tLMRiskDB.getInfo()) {
					buildError("getDetail", "LMRisk中没有相关的险种代码，继续处理下一个");
					// continue; //没有相关的险种名称，继续处理下一个
				}
				strArr[8] = tLMRiskDB.getRiskName();

				// 获取保额
				strArr[9] = new DecimalFormat("0.00").format(tLPPolSchema
						.getAmnt());
				// 获取变更后保费
				strArr[10] = new DecimalFormat("0.00").format(tLPPolSchema
						.getPrem());
				// 获取保费期间
				String insuYearUnit = "";
				if (tLPPolSchema.getInsuYearFlag().equals("M")) {
					insuYearUnit = "个月";
				}
				if (tLPPolSchema.getInsuYearFlag().equals("Y")) {
					insuYearUnit = "年";
				}
				if (tLPPolSchema.getInsuYear() > 200) {
					strArr[11] = "终身";
				} else {
					strArr[11] = String.valueOf(tLPPolSchema.getInsuYear())
							+ insuYearUnit + "，终止日期同原保单";
				}
				// 获取受益人姓名
				LPBnfDB tLPBnfDB = new LPBnfDB();
				LPBnfSet tLPBnfSet = new LPBnfSet();
				tLPBnfDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
				tLPBnfDB.setPolNo(tLPEdorItemSchema.getPolNo());
				tLPBnfDB.setEdorType(tLPEdorItemSchema.getEdorType());
				tLPBnfDB.setInsuredNo(tLPInsuredSchema.getInsuredNo());
				tLPBnfSet.set(tLPBnfDB.query());
				if (tLPBnfSet.size() == 0) {
					LCBnfDB tLCBnfDB = new LCBnfDB();
					tLCBnfDB.setPolNo(tLPEdorItemSchema.getPolNo());
					tLCBnfDB.setInsuredNo(tLPInsuredSchema.getInsuredNo());
					LCBnfSet tLCBnfSet = new LCBnfSet();
					tLCBnfSet.set(tLPBnfDB.query());
					if (tLCBnfSet.size() == 0) {
						buildError("getDetail", "LCBnf中没有相关的记录，继续处理下一个");
						// continue; //没有相关的险种名称，继续处理下一个
					} else {
						Reflections tReflections = new Reflections();
						tReflections.transFields(tLPBnfSet, tLCBnfSet);
					}
				}
				strArr[12] = "";
				strArr[13] = "";
				for (int i = 1; i <= tLPBnfSet.size(); i++) {

					LPBnfSchema tLPBnfSchema = new LPBnfSchema();
					tLPBnfSchema = tLPBnfSet.get(i);
					// 受益人姓名
					strArr[12] += tLPBnfSchema.getName() + ",";
					// 受益人证件号码
					strArr[13] += tLPBnfSchema.getIDNo() + ",";
				}
				if (strArr[12].indexOf(",") != -1) {
					strArr[12] = strArr[12].substring(0, strArr[12]
							.lastIndexOf(","));
				}
				if (strArr[13].indexOf(",") != -1) {
					strArr[13] = strArr[13].substring(0, strArr[13]
							.lastIndexOf(","));
				}
			}
			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setEndorsementNo(tLPEdorItemSchema.getEdorNo());
			tLJSGetEndorseDB.setFeeOperationType(tLPEdorItemSchema
					.getEdorType());
			tLJSGetEndorseDB.setPolNo(tLPEdorItemSchema.getPolNo());
			LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();

			double getMoney = 0.0; // 收费
			double getInterest = 0.0; // 收费利息
			double payMoney = 0.0; // 付费
			for (int i = 0; i < tLJSGetEndorseSet.size(); i++) {
				if (tLJSGetEndorseSet.get(i + 1).getGetFlag().equals("1")) {
					// 此时是应付金额
					payMoney = payMoney
							+ tLJSGetEndorseSet.get(i + 1).getGetMoney();
				} else {
					if (tLJSGetEndorseSet.get(i + 1).getFeeFinaType().equals(
							"LX")) {
						getInterest = getInterest
								+ tLJSGetEndorseSet.get(i + 1).getGetMoney();
					} else {
						getMoney = getMoney
								+ tLJSGetEndorseSet.get(i + 1).getGetMoney();
					}
				}
			}

			// 本次收款金额
			sum1 = sum1 + getInterest + getMoney;
			// 本次付款金额
			sum2 = sum2 + payMoney;

			// 收费金额
			String strGetMoney = new DecimalFormat("0.00").format(Double
					.valueOf(String.valueOf(getMoney)));
			strArr[14] = PubFun.getInt(strGetMoney);
			// 收费金额利息
			String strInterest = new DecimalFormat("0.00").format(Double
					.valueOf(String.valueOf(getInterest)));
			strArr[15] = PubFun.getInt(strInterest);

			// 付费金额
			String strPayMoney = new DecimalFormat("0.00").format(Double
					.valueOf(String.valueOf(payMoney)));
			strArr[16] = PubFun.getInt(strPayMoney);

			// //获取保单表数据
			// if (!getPolData(tLPEdorItemSchema, strArr))
			// {
			// // return false;
			// }
			//
			// //获取被保人表数据
			// if (!getInsuredData(tLPEdorItemSchema, strArr))
			// {
			// // return false;
			// }
			//
			// //获取保全描述表数据
			// if (!getEdorRiskData(tLPEdorItemSchema, strArr))
			// {
			// // return false;
			// }
			//
			// //受益人
			// if (!getBnfData(tLPEdorItemSchema, strArr))
			// {
			// // return false;
			// }
			//
			// //收费金额、利息
			// if (!getMoneyDetail(tLPGrpEdorItemSchema, tLPEdorItemSchema,
			// strArr))
			// {
			// // return false;
			// }

			// 增加一行
			strArr[0] = String.valueOf(detailno++);
			tlistTableDetail.add(strArr);
		}

		sum3++;
		sum4 = sum1 - sum2;
		return true;
	}

	// 投保人变更
	private boolean getDetailAN(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		// 根据批单号查询保全集体保单表LPGrpCont中的记录，生成schema
		LPGrpAppntDB tLPGrpAppntDB = new LPGrpAppntDB();
		tLPGrpAppntDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		LPGrpAppntSet tLPGrpAppntSet = new LPGrpAppntSet();
		tLPGrpAppntSet.set(tLPGrpAppntDB.query());
		if (tLPGrpAppntSet.size() == 0) {
			buildError("getDetailAN", "保全集体保单表LPGrpCont中无相关批单号的记录");
			return false;
		}
		LPGrpAppntSchema tLPGrpAppntSchema = new LPGrpAppntSchema();
		tLPGrpAppntSchema = tLPGrpAppntSet.get(1);

		// 根据集体合同号码查询集体保单表LCGrpCont中的记录，生成schema
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(aLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			buildError("getDetailAN", "集体保单表LCGrpCont中无相关集体合同号的记录");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();

		// 比较投保人姓名是否变化
		if (!StrTool.cTrim(tLCGrpContSchema.getGrpName()).equals(
				StrTool.cTrim(tLPGrpAppntSchema.getName()))) {
			textTag.add("AN_OldGrpName", tLCGrpContSchema.getGrpName());
			textTag.add("AN_NewGrpName", tLPGrpAppntSchema.getName());
			textTag.add("AN_EdorValiDate", aLPGrpEdorItemSchema
					.getEdorValiDate());
		}

		return true;
	}

	// 被保人基本信息变更
	private boolean getDetailBB(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemDBSet();
		tLPEdorItemSet.set(tLPEdorItemDB.query()); // ??
		int n = tLPEdorItemSet.size();
		for (int i = 1; i <= n; i++) {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			// 取（保全个单被保人表）和（被保人表）的相关记录，生称相应schema
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
			tLCInsuredDB.setContNo(tLPEdorItemSchema.getContNo());
			if (!tLCInsuredDB.getInfo()) {
				buildError("getDetailBB", "LCInsured中无相关记录");

			} else {
				tLCInsuredSchema = tLCInsuredDB.getSchema();
			}
			LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();
			tLPInsuredDB.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPInsuredDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsuredDB.setContNo(tLPEdorItemSchema.getContNo());
			tLPInsuredDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
			LPInsuredSet tLPInsuredSet = new LPInsuredDBSet();
			tLPInsuredSet.set(tLPInsuredDB.query());
			if (tLPInsuredSet.size() == 0) {
				buildError("getDetailBB", "保全个单被保人表LPInsured中无相关客户号的纪录");
			} else {
				tLPInsuredSchema = tLPInsuredSet.get(1);
			}
			// 取（保全个单地址表）和（个单地址表）的相关记录，生称相应schema
			LCAddressSchema tLCAddressSchema = new LCAddressSchema();
			LCAddressDB tLCAddressDB = new LCAddressDB();
			tLCAddressDB.setCustomerNo(tLCInsuredSchema.getInsuredNo());
			tLCAddressDB.setAddressNo(tLCInsuredSchema.getAddressNo());
			if (!tLCAddressDB.getInfo()) {
				buildError("getDetailBB", "团体客户地址表LCGrpAddress中无相关集体合同号的记录");
			} else {
				tLCAddressSchema = tLCAddressDB.getSchema();
			}

			LPAddressSchema tLPAddressSchema = new LPAddressSchema();
			LPAddressDB tLPAddressDB = new LPAddressDB();
			tLPAddressDB.setCustomerNo(tLPEdorItemSchema.getInsuredNo());
			tLPAddressDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPAddressDB.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPAddressDB.setAddressNo(tLPInsuredSchema.getAddressNo());
			LPAddressSet tLPAddressSet = new LPAddressDBSet();
			tLPAddressSet.set(tLPAddressDB.query());
			if (tLPAddressSet.size() == 0) { // 可能修改后的地址用的是原来LCAddress中的地址，所以在LPGrpAddress中没有记录
				// 尝试从LCAddress中取出此地址记录
				tLCAddressDB = new LCAddressDB();
				tLCAddressDB.setCustomerNo(tLCInsuredSchema.getInsuredNo());
				tLCAddressDB.setAddressNo(tLCInsuredSchema.getAddressNo());
				if (!tLCAddressDB.getInfo()) {
					buildError("getDetailBB",
							"保全团体客户地址表LPGrpAddress和LCAddress中无相关集体合同号的记录");
				} else {
					tLCAddressSchema = tLCAddressDB.getSchema();
					Reflections aReflections = new Reflections();
					aReflections
							.transFields(tLPAddressSchema, tLCAddressSchema);
				}
			} else {
				tLPAddressSchema = tLPAddressSet.get(1);
			}

			String[] strArr = new String[3];
			tlistTable = new ListTable(); // 设置循环的listTable
			tlistTable.setName("BB");

			// 比较证件类型是否变化
			if (!StrTool.cTrim(tLCInsuredSchema.getIDType()).equals(
					StrTool.cTrim(tLPInsuredSchema.getIDType()))) {
				strArr[0] = "证件类型";
				strArr[1] = StrTool.cTrim(tLCInsuredSchema.getIDType());
				strArr[2] = StrTool.cTrim(tLPInsuredSchema.getIDType());
				tlistTable.add(strArr);
			}

			// 比较证件号码是否变化
			if (!StrTool.cTrim(tLCInsuredSchema.getIDNo()).equals(
					StrTool.cTrim(tLPInsuredSchema.getIDNo()))) {
				strArr[0] = "证件号码";
				strArr[1] = StrTool.cTrim(tLCInsuredSchema.getIDNo());
				strArr[2] = StrTool.cTrim(tLPInsuredSchema.getIDNo());
				tlistTable.add(strArr);
			}

			// 比较家庭地址是否变化
			if (!StrTool.cTrim(tLCAddressSchema.getHomeAddress()).equals(
					StrTool.cTrim(tLPAddressSchema.getHomeAddress()))) {
				strArr[0] = "家庭地址";
				strArr[1] = StrTool.cTrim(tLCAddressSchema.getHomeAddress());
				strArr[2] = StrTool.cTrim(tLPAddressSchema.getHomeAddress());
				tlistTable.add(strArr);
			}

			// 比较通讯地址是否变化
			if (!StrTool.cTrim(tLCAddressSchema.getPostalAddress()).equals(
					StrTool.cTrim(tLPAddressSchema.getPostalAddress()))) {
				strArr[0] = "通讯地址";
				strArr[1] = StrTool.cTrim(tLCAddressSchema.getPostalAddress());
				strArr[2] = StrTool.cTrim(tLPAddressSchema.getPostalAddress());
				tlistTable.add(strArr);
			}

			// 比较邮编是否变化
			if (!StrTool.cTrim(tLCAddressSchema.getZipCode()).equals(
					StrTool.cTrim(tLPAddressSchema.getZipCode()))) {
				strArr[0] = "邮编";
				strArr[1] = StrTool.cTrim(tLCAddressSchema.getZipCode());
				strArr[2] = StrTool.cTrim(tLPAddressSchema.getZipCode());
				tlistTable.add(strArr);
			}

			// 比较电话是否变化
			if (!StrTool.cTrim(tLCAddressSchema.getPhone()).equals(
					StrTool.cTrim(tLPAddressSchema.getPhone()))) {
				strArr[0] = "电话";
				strArr[1] = StrTool.cTrim(tLCAddressSchema.getPhone());
				strArr[2] = StrTool.cTrim(tLPAddressSchema.getPhone());
				tlistTable.add(strArr);
			}

			// 比较手机号是否变化
			if (!StrTool.cTrim(tLCAddressSchema.getMobile()).equals(
					StrTool.cTrim(tLPAddressSchema.getMobile()))) {
				strArr[0] = "手机号";
				strArr[1] = StrTool.cTrim(tLCAddressSchema.getMobile());
				strArr[2] = StrTool.cTrim(tLPAddressSchema.getMobile());
				tlistTable.add(strArr);
			}

			String[] strArr1 = new String[3];
			strArr1[0] = "变更项目";
			strArr1[1] = "变更前";
			strArr1[2] = "变更后";

			textTag.add("BB_EdorValiDate", aLPGrpEdorItemSchema
					.getEdorValiDate());
			xmlexport.addListTable(tlistTable, strArr1);

		}
		return true;
	}

	// 被保险人重要资料变更
	private boolean getDetailIC(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPInsuredDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		LPInsuredSet tLPInsuredSet = new LPInsuredDBSet();
		tLPInsuredSet.set(tLPInsuredDB.query());
		int n = tLPInsuredSet.size();

		tlistTable = new ListTable(); // 设置循环的listTable
		tlistTable.setName("IC");
		for (int i = 1; i <= n; i++) {
			LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
			tLPInsuredSchema = tLPInsuredSet.get(i);

			// 取（保全个单被保人表）和（被保人表）的相关记录，生称相应schema
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setInsuredNo(tLPInsuredSchema.getInsuredNo());
			tLCInsuredDB.setContNo(tLPInsuredSchema.getContNo());
			if (!tLCInsuredDB.getInfo()) {
				buildError("getDetailIC", "LCInsured中无相关记录");

			} else {
				tLCInsuredSchema = tLCInsuredDB.getSchema();
			}
			// if (tLPInsuredSet.size() == 0) {
			// buildError("getDetailIC", "保全个单被保人表LPInsured中无相关客户号的纪录");
			// }
			// else {
			// tLPInsuredSchema = tLPInsuredSet.get(1);
			// }

			// textTag.add("IC_InsuredNo", tLPInsuredSchema.getInsuredNo());
			if (!StrTool.cTrim(tLCInsuredSchema.getName()).equals(
					StrTool.cTrim(tLPInsuredSchema.getName()))) {
				String[] strArr = new String[4];
				strArr[0] = StrTool.cTrim(tLCInsuredSchema.getInsuredNo());
				strArr[1] = "姓名";
				strArr[2] = StrTool.cTrim(tLCInsuredSchema.getName());
				strArr[3] = StrTool.cTrim(tLPInsuredSchema.getName());
				tlistTable.add(strArr);
			}

			// 比较证件类型是否变化
			if (!StrTool.cTrim(tLCInsuredSchema.getIDType()).equals(
					StrTool.cTrim(tLPInsuredSchema.getIDType()))) {

				String[] strArr = new String[4];
				strArr[0] = StrTool.cTrim(tLCInsuredSchema.getInsuredNo());
				strArr[1] = "证件类型";
				strArr[2] = decodeQuery("idtype", StrTool
						.cTrim(tLCInsuredSchema.getIDType()));
				strArr[3] = decodeQuery("idtype", StrTool
						.cTrim(tLPInsuredSchema.getIDType()));
				// strArr[2] = StrTool.cTrim(tLCInsuredSchema.getIDType());
				// strArr[3] = StrTool.cTrim(tLPInsuredSchema.getIDType());
				tlistTable.add(strArr);
			}

			// 比较证件号码是否变化
			if (!StrTool.cTrim(tLCInsuredSchema.getIDNo()).equals(
					StrTool.cTrim(tLPInsuredSchema.getIDNo()))) {
				String[] strArr = new String[4];
				strArr[0] = StrTool.cTrim(tLCInsuredSchema.getInsuredNo());
				strArr[1] = "证件号码";
				strArr[2] = StrTool.cTrim(tLCInsuredSchema.getIDNo());
				strArr[3] = StrTool.cTrim(tLPInsuredSchema.getIDNo());
				tlistTable.add(strArr);
			}

			// 比较出生日期是否变化
			if (!StrTool.cTrim(tLCInsuredSchema.getBirthday()).equals(
					StrTool.cTrim(tLPInsuredSchema.getBirthday()))) {
				String[] strArr = new String[4];
				strArr[0] = StrTool.cTrim(tLCInsuredSchema.getInsuredNo());
				strArr[1] = "出生日期";
				strArr[2] = StrTool.cTrim(tLCInsuredSchema.getBirthday());
				strArr[3] = StrTool.cTrim(tLPInsuredSchema.getBirthday());
				tlistTable.add(strArr);
			}

			// 比较性别是否变化
			if (!StrTool.cTrim(tLCInsuredSchema.getSex()).equals(
					StrTool.cTrim(tLPInsuredSchema.getSex()))) {

				String[] strArr = new String[4];
				strArr[0] = StrTool.cTrim(tLCInsuredSchema.getInsuredNo());
				strArr[1] = "性别";
				strArr[2] = decodeQuery("sex", StrTool.cTrim(tLCInsuredSchema
						.getSex()));
				strArr[3] = decodeQuery("sex", StrTool.cTrim(tLPInsuredSchema
						.getSex()));
				// strArr[2] = StrTool.cTrim(tLCInsuredSchema.getSex());
				// strArr[3] = StrTool.cTrim(tLPInsuredSchema.getSex());
				tlistTable.add(strArr);
			}
		}
		String[] strArr1 = new String[4];
		strArr1[0] = "客户编码";
		strArr1[1] = "变更项目";
		strArr1[2] = "变更前";
		strArr1[3] = "变更后";
		xmlexport.addListTable(tlistTable, strArr1);

		textTag.add("IC_EdorValiDate", aLPGrpEdorItemSchema.getEdorValiDate());
		double getMoney = aLPGrpEdorItemSchema.getGetMoney();
		if (getMoney > 0) {
			textTag.add("IC_GetMoney", "投保人共计缴纳保险费"
					+ aLPGrpEdorItemSchema.getGetMoney() + "元");
		} else {

			textTag
					.add("IC_GetMoney", "共计退还投保人保险费"
							+ String.valueOf(-aLPGrpEdorItemSchema
									.getGetMoney()) + "元");
		}
		return true;
	}

	// 被保险人重要资料变更
	private boolean getDetailWT(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		textTag.add("WT_GrpContNo", aLPGrpEdorItemSchema.getGrpContNo());
		textTag.add("WT_EdorValiDate", aLPGrpEdorItemSchema.getEdorValiDate());

		ListTable tlistTable = new ListTable();
		tlistTable.setName("WT");
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(aLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			mErrors.addOneError(new CError("查询保单号为"
					+ tLCGrpContDB.getGrpContNo() + "的保单信息时失败!"));
			return false;

		}
		String msql = "select sum(getmoney)  getmoney from LJSGetendorse where endorsementno ='?endorsementno?' and FeeOperationType ='?FeeOperationType?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("endorsementno", aLPGrpEdorItemSchema.getEdorNo());
		sqlbv1.put("FeeOperationType", aLPGrpEdorItemSchema.getEdorType());
		ExeSQL aExeSQL = new ExeSQL();
		String tReturn = aExeSQL.getOneValue(sqlbv1);
		logger.debug("msql:" + msql);
		if (tReturn == null || StrTool.cTrim(tReturn).equals("")) {
			mErrors.addOneError(new CError("没有相关的犹豫期退保记录！"));
			return false;
		}
		String yeSQL = msql + "and FeeFinaType = 'YE' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(yeSQL);
		sqlbv2.put("endorsementno", aLPGrpEdorItemSchema.getEdorNo());
		sqlbv2.put("FeeOperationType", aLPGrpEdorItemSchema.getEdorType());
		String yeReturn = aExeSQL.getOneValue(sqlbv2);
		if (yeReturn.equals("")) {
			yeReturn = "0";
		}
		String tfSQL = msql + "and FeeFinaType = 'TF' ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tfSQL);
		sqlbv3.put("endorsementno", aLPGrpEdorItemSchema.getEdorNo());
		sqlbv3.put("FeeOperationType", aLPGrpEdorItemSchema.getEdorType());
		String tfReturn = aExeSQL.getOneValue(sqlbv3);
		if (tfReturn.equals("")) {
			tfReturn = "0";
		}
		String gbSQL = msql + "and FeeFinaType = 'GB' ";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(gbSQL);
		sqlbv4.put("endorsementno", aLPGrpEdorItemSchema.getEdorNo());
		sqlbv4.put("FeeOperationType", aLPGrpEdorItemSchema.getEdorType());
		String gbReturn = aExeSQL.getOneValue(sqlbv4);
		if (gbReturn.equals("")) {
			gbReturn = "0";
		}
		textTag.add("WT_TF", Math.abs(Double.parseDouble(tfReturn)));
		textTag.add("WT_YE", Math.abs(Double.parseDouble(yeReturn)));
		textTag.add("WT_TJ", Math.abs(Double.parseDouble(gbReturn)));
		double total = Math.abs(Double.parseDouble(tfReturn)
				+ Double.parseDouble(yeReturn) + Double.parseDouble(gbReturn));
		textTag.add("WT_GetMoney", total);

		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		tLPGrpPolDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPGrpPolDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		tLPGrpPolDB.setGrpContNo(aLPGrpEdorItemSchema.getGrpContNo());
		LPGrpPolSet tLPGrpPolSet = tLPGrpPolDB.query();
		if (tLPGrpPolSet == null || tLPGrpPolSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "PrtGrpEndorsementBL";
			tError.functionName = "getDetailWT";
			tError.errorMessage = "LCPol不存在相应的记录!";
			this.mErrors.addOneError(tError);
			return false;
		}

		for (int i = 0; i < tLPGrpPolSet.size(); i++) {
			String printContent[] = new String[3];
			LPGrpPolSchema tLPGrpPOLSchema = tLPGrpPolSet.get(i + 1);
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(tLPGrpPOLSchema.getRiskCode());
			if (!tLMRiskDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "PrtGrpEndorsementBL";
				tError.functionName = "getDetailWT";
				tError.errorMessage = "LMRisk不存在相应的记录!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				printContent[0] = "" + tLPGrpPOLSchema.getGrpPolNo();
				printContent[1] = "" + tLMRiskDB.getRiskName();
				printContent[2] = "" + tLPGrpPOLSchema.getPrem() + "元\n";
			}
			tlistTable.add(printContent);
		}

		String strhead[] = new String[3];
		strhead[0] = "保险号";
		strhead[1] = "险种名称";
		strhead[2] = "退险金额";
		xmlexport.addListTable(tlistTable, strhead);
		return true;
	}

	// 协议退保
	private boolean getDetailXT(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		int decreaseCount = 0;
		double smoney = 0;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询协议退保项目时失败");
			return false;
		}
		tlistTable = new ListTable();
		tlistTable.setName("XT");

		decreaseCount = tLPEdorItemSet.size();
		for (int i = 0; i < tLPEdorItemSet.size(); i++) {
			LPEdorItemSchema tLPEdorItemSchema = tLPEdorItemSet.get(i + 1);
			String[] strArr = new String[4];
			strArr[0] = Integer.toString(i + 1);
			strArr[1] = StrTool.cTrim(tLPEdorItemSchema.getContNo()) + " ";
			strArr[2] = StrTool.cTrim(tLPEdorItemSchema.getInsuredNo());
			strArr[3] = Double.toString(java.lang.Math.abs(tLPEdorItemSchema
					.getGetMoney()));
			tlistTable.add(strArr);
			smoney = smoney + tLPEdorItemSchema.getGetMoney();

		} // end for
		textTag.add("XT_DecreaseInsuredCount", decreaseCount);
		textTag.add("XT_GrpContNo", aLPGrpEdorItemSchema.getGrpContNo());
		textTag.add("XT_EdorValiDate", aLPGrpEdorItemSchema.getEdorValiDate());
		textTag.add("XT_GetMoney", java.lang.Math.abs(smoney));
		return true;
	}

	// 投保人联系方式变更
	private boolean getDetailAD(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {

		// 取（保全团单投保人表）和（团单投保人表）的相关记录，生称相应schema
		LPGrpAppntDB tLPGrpAppntDB = new LPGrpAppntDB();
		tLPGrpAppntDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPGrpAppntDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		tLPGrpAppntDB.setGrpContNo(aLPGrpEdorItemSchema.getGrpContNo());
		LPGrpAppntSet tLPGrpAppntSet = new LPGrpAppntDBSet();
		tLPGrpAppntSet.set(tLPGrpAppntDB.query());
		if (!tLPGrpAppntDB.getInfo()) {
			buildError("getDetailAD", "保全团单投保人表LPGrpAppnt中无相关批单号的记录");
			// return false;
		}
		LPGrpAppntSchema tLPGrpAppntSchema = tLPGrpAppntDB.getSchema();

		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		tLCGrpAppntDB.setGrpContNo(tLPGrpAppntSchema.getGrpContNo());
		if (!tLCGrpAppntDB.getInfo()) {
			buildError("getDetailAD", "团单投保人表LCGrpAppnt中无相关集体合同号的记录");
			return false;
		}
		LCGrpAppntSchema tLCGrpAppntSchema = tLCGrpAppntDB.getSchema();

		// 取（保全团体客户地址表）和（团体客户地址表）的相关记录，生称相应schema

		LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
		tLCGrpAddressDB.setCustomerNo(tLCGrpAppntSchema.getCustomerNo());
		tLCGrpAddressDB.setAddressNo(tLCGrpAppntSchema.getAddressNo());
		if (!tLCGrpAddressDB.getInfo()) {
			buildError("getDetailAD", "团体客户地址表LCGrpAddress中无相关集体合同号的记录");
			return false;
		}
		LCGrpAddressSchema tLCGrpAddressSchema = tLCGrpAddressDB.getSchema();

		LPGrpAddressSchema tLPGrpAddressSchema = new LPGrpAddressSchema();
		LPGrpAddressDB tLPGrpAddressDB = new LPGrpAddressDB();
		tLPGrpAddressDB.setEdorNo(tLPGrpAppntSchema.getEdorNo());
		tLPGrpAddressDB.setEdorType(tLPGrpAppntSchema.getEdorType());
		tLPGrpAddressDB.setCustomerNo(tLPGrpAppntSchema.getCustomerNo());
		tLPGrpAddressDB.setAddressNo(tLPGrpAppntSchema.getAddressNo());

		if (!tLPGrpAddressDB.getInfo()) { // 可能修改后的地址用的是原来LCGrpAddress中的地址，所以在LPGrpAddress中没有记录
			// 尝试从LCGrpAddress中取出此地址记录
			tLCGrpAddressDB = new LCGrpAddressDB();
			tLCGrpAddressDB.setCustomerNo(tLPGrpAppntSchema.getCustomerNo());
			tLCGrpAddressDB.setAddressNo(tLPGrpAppntSchema.getAddressNo());
			if (!tLCGrpAddressDB.getInfo()) {
				buildError("getDetailAD", "保全团体客户地址表LPGrpAddress中无相关集体合同号的记录");
				return false;
			}
			Reflections aReflections = new Reflections();
			aReflections.transFields(tLPGrpAddressSchema, tLCGrpAddressDB
					.getSchema());
		} else {
			tLPGrpAddressSchema = tLPGrpAddressDB.getSchema();
		}

		String[] strArr = new String[3];
		tlistTable = new ListTable();
		tlistTable.setName("AD");

		// 比较地址是否变化
		if (!StrTool.cTrim(tLCGrpAddressSchema.getGrpAddress()).equals(
				StrTool.cTrim(tLPGrpAddressSchema.getGrpAddress()))) {
			strArr[0] = "地址";
			strArr[1] = tLCGrpAddressSchema.getGrpAddress();
			strArr[2] = tLPGrpAddressSchema.getGrpAddress();
			tlistTable.add(strArr);
		}

		// 比较邮编
		if (!StrTool.cTrim(tLCGrpAddressSchema.getGrpZipCode()).equals(
				StrTool.cTrim(tLPGrpAddressSchema.getGrpZipCode()))) {
			strArr[0] = "邮编";
			strArr[1] = tLCGrpAddressSchema.getGrpZipCode();
			strArr[2] = tLPGrpAddressSchema.getGrpZipCode();
			tlistTable.add(strArr);
		}

		// 比较联系人1
		if (!StrTool.cTrim(tLCGrpAddressSchema.getLinkMan1()).equals(
				StrTool.cTrim(tLPGrpAddressSchema.getLinkMan1()))) {
			strArr[0] = "联系人1";
			strArr[1] = tLCGrpAddressSchema.getLinkMan1();
			strArr[2] = tLPGrpAddressSchema.getLinkMan1();
			tlistTable.add(strArr);
		}

		// 比较联系电话1是否变化
		if (!StrTool.cTrim(tLCGrpAddressSchema.getPhone1()).equals(
				StrTool.cTrim(tLPGrpAddressSchema.getPhone1()))) {
			strArr[0] = "联系电话1";
			strArr[1] = tLCGrpAddressSchema.getPhone1();
			strArr[2] = tLPGrpAddressSchema.getPhone1();
			tlistTable.add(strArr);
		}

		// 比较传真1是否变化
		if (!StrTool.cTrim(tLCGrpAddressSchema.getFax1()).equals(
				StrTool.cTrim(tLPGrpAddressSchema.getFax1()))) {
			strArr[0] = "传真1";
			strArr[1] = tLCGrpAddressSchema.getFax1();
			strArr[2] = tLPGrpAddressSchema.getFax1();
			tlistTable.add(strArr);
		}

		// 比较联系人2
		if (!StrTool.cTrim(tLCGrpAddressSchema.getLinkMan2()).equals(
				StrTool.cTrim(tLPGrpAddressSchema.getLinkMan2()))) {
			strArr[0] = "联系人2";
			strArr[1] = tLCGrpAddressSchema.getLinkMan2();
			strArr[2] = tLPGrpAddressSchema.getLinkMan2();
			tlistTable.add(strArr);
		}

		// 比较联系电话2是否变化
		if (!StrTool.cTrim(tLCGrpAddressSchema.getPhone2()).equals(
				StrTool.cTrim(tLPGrpAddressSchema.getPhone2()))) {
			strArr[0] = "联系电话2";
			strArr[1] = tLCGrpAddressSchema.getPhone2();
			strArr[2] = tLPGrpAddressSchema.getPhone2();
			tlistTable.add(strArr);
		}

		// 比较传真2是否变化
		if (!StrTool.cTrim(tLCGrpAddressSchema.getFax2()).equals(
				StrTool.cTrim(tLPGrpAddressSchema.getFax2()))) {
			strArr[0] = "传真2";
			strArr[1] = tLCGrpAddressSchema.getFax2();
			strArr[2] = tLPGrpAddressSchema.getFax2();
			tlistTable.add(strArr);
		}

		String[] strArrHead = new String[3];
		strArrHead[0] = "变更项目";
		strArrHead[1] = "变更前";
		strArrHead[2] = "变更后";

		textTag.add("AD_EdorValiDate", aLPGrpEdorItemSchema.getEdorValiDate());
		xmlexport.addListTable(tlistTable, strArrHead);
		return true;
	}

	// 增加被保险人
	private boolean getDetailNI(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {

		String sql = "select * from LCCont where  GrpContNo='?GrpContNo?' and AppFlag='2'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("GrpContNo", aLPGrpEdorItemSchema.getGrpContNo());
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		tLCContSet.set(tLCContDB.executeQuery(sqlbv5));

		if (tLCContSet.size() == 0) {
			return false;
		}

		// 计算新增的总人数
		int AddPeople = tLCContSet.size(); // 团单中一个合同对应一个被保人
		textTag.add("NI_IncreasePeopleSum", AddPeople);

		double totalMoney = 0;
		for (int i = 0; i < tLCContSet.size(); i++) {
			totalMoney = totalMoney + tLCContSet.get(i + 1).getPrem();
		}
		textTag
				.add("NI_IncreaseMoney", PubFun
						.setPrecision(totalMoney, "0.00"));
		textTag.add("NI_EdorValiDate", aLPGrpEdorItemSchema.getEdorValiDate());

		return true;
	}

	// 保单遗失补发
	private boolean getDetailLR(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(aLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			mErrors.addOneError(new CError("相关的集体保单表中没有记录！"));
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();
		textTag.add("LR_GrpContNo", aLPGrpEdorItemSchema.getGrpContNo());
		textTag.add("LR_EdorValiDate", aLPGrpEdorItemSchema.getEdorValiDate());
		textTag.add("LR_LostTimes", tLCGrpContSchema.getLostTimes() + 1);
		textTag.add("LR_GetMoney", aLPGrpEdorItemSchema.getGetMoney());

		return true;
	}

	// 自由格式批改
	private boolean getDetailFC(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		LPGrpFreeEdorSchema tLPGrpFreeEdorSchema = new LPGrpFreeEdorSchema();
		LPGrpFreeEdorDB tLPGrpFreeEdorDB = new LPGrpFreeEdorDB();
		tLPGrpFreeEdorDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPGrpFreeEdorDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		tLPGrpFreeEdorDB.setGrpContNo(aLPGrpEdorItemSchema.getGrpContNo());
		logger.debug("a" + aLPGrpEdorItemSchema.getEdorNo());
		logger.debug("b" + aLPGrpEdorItemSchema.getEdorType());
		logger.debug("c" + aLPGrpEdorItemSchema.getGrpContNo());
		LPGrpFreeEdorSet tLPGrpFreeEdorSet = new LPGrpFreeEdorSet();
		tLPGrpFreeEdorSet = tLPGrpFreeEdorDB.query();
		if (tLPGrpFreeEdorSet.size() == 0) {
			mErrors.addOneError(new CError("相关的集体保单表中没有记录！"));
			return false;
		}
		tLPGrpFreeEdorSchema = tLPGrpFreeEdorSet.get(1);
		textTag.add("FC_GrpContNo", tLPGrpFreeEdorSchema.getGrpContNo());
		textTag.add("FC_EdorNo", tLPGrpFreeEdorSchema.getEdorNo());
		textTag.add("FC_EdorType", tLPGrpFreeEdorSchema.getEdorType());
		textTag.add("FC_EdorContent", tLPGrpFreeEdorSchema.getEdorContent());
		logger.debug("1" + tLPGrpFreeEdorSchema.getGrpContNo());
		logger.debug("2" + tLPGrpFreeEdorSchema.getEdorNo());
		logger.debug("3" + tLPGrpFreeEdorSchema.getEdorType());
		logger.debug("4" + tLPGrpFreeEdorSchema.getEdorContent());
		return true;
	}

	// 减人
	private boolean getDetailZT(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		int decreaseCount = 0;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询减人项目人数时失败");
			return false;
		}
		tlistTable = new ListTable();
		tlistTable.setName("ZT");

		decreaseCount = tLPEdorItemSet.size();
		for (int i = 0; i < tLPEdorItemSet.size(); i++) {
			LPEdorItemSchema tLPEdorItemSchema = tLPEdorItemSet.get(i + 1);
			LDPersonSchema tLDPersonSchema = new LDPersonSchema();
			LDPersonDB tLDPersonDB = new LDPersonDB();
			tLDPersonDB.setCustomerNo(tLPEdorItemSchema.getInsuredNo());
			if (!tLDPersonDB.getInfo()) {
				buildError("getDetailZT", "LDPerson中无相关记录");
			} else {
				tLDPersonSchema = tLDPersonDB.getSchema();
			}

			// tlistTable = new ListTable();
			// tlistTable.setName("");

			String[] strArr = new String[4];
			strArr[0] = Integer.toString(i + 1);
			strArr[1] = StrTool.cTrim(tLDPersonSchema.getName()) + " ";
			strArr[2] = StrTool.cTrim(tLPEdorItemSchema.getInsuredNo());
			strArr[3] = Double.toString(java.lang.Math.abs(tLPEdorItemSchema
					.getGetMoney()));
			tlistTable.add(strArr);
		} // end for
		String[] strArr1 = new String[4];
		strArr1[0] = "序号";
		strArr1[1] = "姓名";
		strArr1[2] = "客户编码";
		strArr1[3] = "退还保费（元）";

		xmlexport.addListTable(tlistTable, strArr1);

		textTag.add("ZT_DecreaseInsuredCount", decreaseCount);
		textTag.add("ZT_GrpContNo", aLPGrpEdorItemSchema.getGrpContNo());
		textTag.add("ZT_EdorValiDate", aLPGrpEdorItemSchema.getEdorValiDate());
		textTag.add("ZT_GetMoney", java.lang.Math.abs(aLPGrpEdorItemSchema
				.getGetMoney()));
		return true;
	}

	// 险种退保
	private boolean getDetailLT(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		String tEdorNo = aLPGrpEdorItemSchema.getEdorNo();
		String tEdorType = aLPGrpEdorItemSchema.getEdorType();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolDBSet();
		String sql = "select * from lcgrppol where grppolno in"
				+ " (select grppolno from lcpol where polno in"
				+ " (select polno from lpedoritem where edorno = '?tEdorNo?' and EdorType='?tEdorType?'))";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sql);
		sqlbv6.put("tEdorNo", tEdorNo);
		sqlbv6.put("tEdorType", tEdorType);
		tLCGrpPolSet = tLCGrpPolDB.executeQuery(sqlbv6);
		if (tLCGrpPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询险种退保项目团体险种单时失败");
			return false;
		}
		String grppolno = "";
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			if (i == tLCGrpPolSet.size()) {
				grppolno = grppolno + tLCGrpPolSet.get(i).getGrpPolNo();
			} else {
				grppolno = grppolno + tLCGrpPolSet.get(i).getGrpPolNo() + ",";
			}
		}
		textTag.add("LT_GrpPolNo", grppolno);
		textTag.add("LT_GrpContNo", aLPGrpEdorItemSchema.getGrpContNo());
		textTag.add("LT_EdorValiDate", aLPGrpEdorItemSchema.getEdorValiDate());
		textTag.add("LT_GetMoney", aLPGrpEdorItemSchema.getGetMoney());

		return true;
	}

	// 团单合同终止
	private boolean getDetailCT(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		textTag.add("CT_GrpContNo", aLPGrpEdorItemSchema.getGrpContNo());
		textTag.add("CT_EdorValiDate", aLPGrpEdorItemSchema.getEdorValiDate());
		textTag.add("CT_Total", ""
				+ String.valueOf(Math.abs(aLPGrpEdorItemSchema.getGetMoney()))
				+ "");
		ListTable tlistTable = new ListTable();
		tlistTable.setName("CT");
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(aLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			mErrors.addOneError(new CError("查询保单号为"
					+ tLCGrpContDB.getGrpContNo() + "的保单信息时失败!"));
			return false;

		}
		LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
		tLJSGetEndorseDB.setEndorsementNo(aLPGrpEdorItemSchema.getEdorNo());
		tLJSGetEndorseDB
				.setFeeOperationType(aLPGrpEdorItemSchema.getEdorType());
		LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();

		for (int i = 0; i < tLJSGetEndorseSet.size(); i++) {
			String printContent[] = new String[3];
			LJSGetEndorseSchema tLJSGetEndorseSchema = tLJSGetEndorseSet
					.get(i + 1);
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			tLPPolDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
			tLPPolDB.setPolNo(tLJSGetEndorseSchema.getPolNo());

			if (!tLPPolDB.getInfo()) {
				continue;
			}

			LPPolSchema tLPPolSchema = tLPPolDB.getSchema();
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
			if (!tLMRiskDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "PrtGrpEndorsementBL";
				tError.functionName = "getDetailWT";
				tError.errorMessage = "LMRisk不存在相应的记录!";
				this.mErrors.addOneError(tError);
			} else {
				printContent[0] = tLJSGetEndorseSchema.getPolNo();
				printContent[1] = tLMRiskDB.getRiskName();
				printContent[2] = Double.toString(Math.abs(tLJSGetEndorseSchema
						.getGetMoney()))
						+ "元";
			}
			tlistTable.add(printContent);
		}

		String strhead[] = new String[3];
		strhead[0] = "保险号";
		strhead[1] = "险种名称";
		strhead[2] = "退险金额";
		xmlexport.addListTable(tlistTable, strhead);
		return true;
	}

	// 对没有的保全项目类型生成空打迎数据
	private boolean getDetailForBlankType(
			LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		return true;
	}

	private boolean getPayGetDetails(LPGrpEdorItemSet tLPGrpEdorItemSet) {
		ListTable listTable = new ListTable();
		boolean bIsExport = false;
		double grossGetMoney = 0.0;

		try {
			for (int i = 1; i <= tLPGrpEdorItemSet.size(); i++) {
				LPGrpEdorItemSchema tLPGrpEdorItemSchema = tLPGrpEdorItemSet
						.get(i);
				LJSGetEndorseDB tLJSGetEndorsementDB = new LJSGetEndorseDB();

				String strSql1 = " select * from ljsgetendorse where endorsementno = '?endorsementno?' and FeeOperationType = '?FeeOperationType?' order by polno, feefinatype";
				SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
				sqlbv7.sql(strSql1);
				sqlbv7.put("endorsementno", tLPGrpEdorItemSchema.getEdorNo());
				sqlbv7.put("FeeOperationType", tLPGrpEdorItemSchema.getEdorType());
				logger.debug("strSql1 :" + strSql1);
				LJSGetEndorseSet tLJSGetEndorsementSet = tLJSGetEndorsementDB
						.executeQuery(sqlbv7);
				String[] strGetPayDetail = null;

				for (int j = 1; j <= tLJSGetEndorsementSet.size(); j++) {
					double getMoney = 0.0;
					double payMoney = 0.0;

					if (tLJSGetEndorsementSet.get(j).getGetFlag()
							.compareTo("0") == 0) {
						payMoney = tLJSGetEndorsementSet.get(j).getGetMoney();
						grossGetMoney -= payMoney;
					} else {
						getMoney = tLJSGetEndorsementSet.get(j).getGetMoney();
						grossGetMoney += getMoney;
					}

					if (getMoney == 0.0 && payMoney == 0.0) {
						continue;
					}

					bIsExport = true;

					strGetPayDetail = new String[5];

					String strSql = " select * from lmriskapp where riskcode = '?riskcode?'";
					SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
					sqlbv8.sql(strSql);
					sqlbv8.put("riskcode", tLJSGetEndorsementSet.get(j).getRiskCode());
					LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
					String strRiskName = tLMRiskAppDB.executeQuery(sqlbv8).get(
							1).getRiskName();
					strGetPayDetail[0] = strRiskName;

					String edortype = tLJSGetEndorsementSet.get(j)
							.getFeeOperationType().trim();
					LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();

					tLMEdorItemDB.setEdorCode(edortype);
					LMEdorItemSet tItemSet = tLMEdorItemDB.query();
					String edorname = "unknow";
					if (tItemSet == null || tItemSet.size() == 0) {
						edorname = edortype;
					} else {
						edorname = tItemSet.get(1).getEdorName();
					}

					strGetPayDetail[1] = edorname;
					strGetPayDetail[2] = getFeeFinaName(tLJSGetEndorsementSet
							.get(j).getFeeFinaType());

					strGetPayDetail[3] = String.valueOf(payMoney);
					strGetPayDetail[4] = String.valueOf(getMoney);
					listTable.add(strGetPayDetail);

				}

			}
		} catch (Exception e) {
			return false;
		}

		if (!bIsExport) {
			return false;
		}

		// 设置列表名字和字段名
		String[] strGetPayHeadInfo = new String[5];
		strGetPayHeadInfo[0] = "RiskCode";
		strGetPayHeadInfo[1] = "EdorType";
		strGetPayHeadInfo[2] = "FeefinaType";
		strGetPayHeadInfo[3] = "PayMoney";
		strGetPayHeadInfo[4] = "GetMoney";

		listTable.setName("FeeDetail");
		xmlexport.addDisplayControl("displayFeeDetail");

		String strGetMoneyInfo;
		if (grossGetMoney > 0.0) {
			strGetMoneyInfo = "应付合计："
					+ new DecimalFormat("0.00").format(grossGetMoney); // String.valueOf(grossGetMoney)
		} else {
			strGetMoneyInfo = "应交合计："
					+ new DecimalFormat("0.00").format(-grossGetMoney); // String.valueOf(-grossGetMoney)
		}
		textTag.add("FeeSum", strGetMoneyInfo);
		xmlexport.addListTable(listTable, strGetPayHeadInfo);
		return true;

	}

	private String getFeeFinaName(String strFeeFinaType) {
		LDCode1DB tLDCode1DB = new LDCode1DB();
		tLDCode1DB.setCode(strFeeFinaType);
		LDCode1Set tLDCode1Set = tLDCode1DB.query();
		if (tLDCode1Set != null && tLDCode1Set.size() > 0) {
			return tLDCode1Set.get(1).getCodeAlias();
		} else {
			return strFeeFinaType;
		}

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PrtGrpEndorsementBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	String decodeQuery(String codetype, String code) {

		LDCodeDB tLDCodeDB = new LDCodeDB();
		@SuppressWarnings("unused")
		LDCodeSchema tLDCodeSchema = new LDCodeSchema();
		tLDCodeDB.setCodeType(codetype);
		tLDCodeDB.setCode(code);
		tLDCodeDB.getInfo();
		return tLDCodeDB.getCodeName();

	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		PrtGrpEndorsementBL tPrtGrpEndorsementBL = new PrtGrpEndorsementBL(
				"430110000000120");
		VData tVData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		tVData.addElement(tGlobalInput);
		if (!tPrtGrpEndorsementBL.printGrpData("6120060413000009")) {
			logger.debug("ERROR");
		}
		VData vdata = tPrtGrpEndorsementBL.getResult();
		PubSubmit ps = new PubSubmit();
		if (ps.submitData(vdata, "")) {
			logger.debug("succeed in pubsubmit");
		}

	}

	/**
	 * 用于测试 CreateDate 2006-3-10 生成批单数据
	 * 
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public boolean printGrpData(String aEdorAcceptNo) {
		XmlExport xmlexport = new XmlExport();
		logger.debug("\n\nStart Write Print Data\n\n");
		logger.debug("EdorAcceptNo ================= > " + aEdorAcceptNo);

		// 按不同项目内容进行打印（一个批单号打印）
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema;
		ExeSQL aExeSQL = new ExeSQL();
		SSRS aSSRS = new SSRS();
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql("select distinct edorno from lpgrpedoritem where edoracceptno = '?aEdorAcceptNo?' group by edorno");
		sqlbv9.put("aEdorAcceptNo", aEdorAcceptNo);
		aSSRS = aExeSQL
				.execSQL(sqlbv9);
		if (aSSRS == null || aSSRS.MaxRow == 0) {
			CError.buildErr(this, "查询批改项目表失败!");
			return false;
		}

		String EdorNo = "";
		for (int j = 1; j <= aSSRS.getMaxRow(); j++) {
			EdorNo = aSSRS.GetText(j, 1);
			// 最好紧接着就初始化xml文档
			xmlexport.createDocument("PrtAppGrpEndorsement.vts", "printer");
			// 一个保单出一张批单
			tLPGrpEdorItemDB.setEdorAcceptNo(aEdorAcceptNo);
			tLPGrpEdorItemDB.setEdorNo(aSSRS.GetText(j, 1));
			tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
			if (tLPGrpEdorItemSet.size() < 1) {
				CError.buildErr(this, "查询保全项目表失败，未找到保全项目!");
				return false;
			}
			boolean mClassflag = true;
			for (int i = 1; i <= tLPGrpEdorItemSet.size(); i++) {
				// 批单打印有各个项目的单独生成打印数据
				tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);
				ExeSQL tExeSQL = new ExeSQL();
				SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
				sqlbv10.sql("select edorname from lmedoritem where edorcode = '?edorcode?' and appobj = 'G'");
				sqlbv10.put("edorcode", tLPGrpEdorItemSchema.getEdorType());
				String EdorName = tExeSQL
						.getOneValue(sqlbv10);
				try {
					Class tClass = Class.forName("com.sinosoft.lis.bq.GrpEdor"
							+ tLPGrpEdorItemSchema.getEdorType() + "PrintBL");
					EdorPrint tEdorPrint = (EdorPrint) tClass.newInstance();
					VData aVData = new VData();
					aVData.add(tLPGrpEdorItemSchema);
					aVData.add(xmlexport);
					aVData.add(mGlobalInput);
					if (!tEdorPrint.submitData(aVData, "PRINT"
							+ tLPGrpEdorItemSchema.getEdorType())) {
						mErrors.copyAllErrors(tEdorPrint.mErrors);
						mErrors.addOneError("保全项目" + EdorName + "打印处理失败!");
						return false;
					}
					VData cVData = new VData();
					cVData = tEdorPrint.getResult();
					xmlexport = (XmlExport) cVData.getObjectByObjectName(
							"XmlExport", 0);
					// 可能存在批单的附属清单
					MMap tMap = (MMap) cVData.getObjectByObjectName("MMap", 0);
					if (tMap != null) {
						map.add(tMap);
					}
				} catch (ClassNotFoundException ex) {
					mClassflag = false;
					logger.debug("未找到" + EdorName + "保全项目打印处理!");
				} catch (Exception ex) {
					ex.printStackTrace();
					mClassflag = false;
					CError.buildErr(this, "保全项目" + EdorName + "打印处理失败!");
					return false;
				}
				logger.debug("成功完成" + EdorName + "保全项目打印处理!");
			}
			if (mClassflag) {
				// 生成主打印批单schema
				LPEdorPrintSchema tLPEdorPrintSchemaMain = new LPEdorPrintSchema();
				tLPEdorPrintSchemaMain.setEdorNo(EdorNo);
				tLPEdorPrintSchemaMain.setManageCom(tLPGrpEdorItemSet.get(1)
						.getManageCom());
				tLPEdorPrintSchemaMain.setPrtFlag("N");
				tLPEdorPrintSchemaMain.setPrtTimes(0);
				tLPEdorPrintSchemaMain.setMakeDate(PubFun.getCurrentDate());
				tLPEdorPrintSchemaMain.setMakeTime(PubFun.getCurrentTime());
				tLPEdorPrintSchemaMain.setOperator(mGlobalInput.Operator);
				tLPEdorPrintSchemaMain.setModifyDate(PubFun.getCurrentDate());
				tLPEdorPrintSchemaMain.setModifyTime(PubFun.getCurrentTime());
				InputStream ins = xmlexport.getInputStream();

				tLPEdorPrintSchemaMain.setEdorInfo(ins);
				SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
				sqlbv11.sql("delete from LPEdorPrint where EdorNo='?EdorNo?'");
				sqlbv11.put("EdorNo", EdorNo);
				map
						.put(sqlbv11, "DELETE");
				map.put(tLPEdorPrintSchemaMain, "BLOBINSERT");
			}
		}
		mResult.clear();
		mResult.add(map);
		return true;
	}

	private void jbInit() throws Exception {
	}
}
