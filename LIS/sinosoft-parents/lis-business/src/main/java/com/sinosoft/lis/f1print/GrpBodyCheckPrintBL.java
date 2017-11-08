package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpBodyCheckPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(GrpBodyCheckPrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 取得的保单号码
	private String mContNo = "";
	// String InsuredName="";
	// 输入的查询sql语句
	private String msql = "";
	// 取得的延期承保原因
	private String mUWError = "";
	// 取得的代理人编码
	private String mAgentCode = "";
	// 取得医院信息
	private String hospitalAdress = "";
	private String hospitalName = "";
	private String hospitalTel = "";
	private String hospitalAddress = "";
	private String hospitalTime = "";

	private String mBatchSQL = "";
	private String mmPrtSeq = "";
	// 客户端ip
	private String mIP = "";
	int ii = 0;
	private String mSql = "";
	private boolean Phisical = false;
	// 配置文件
	private String mConfigFile = "";

	private String mOperator; // 操作员
	private String mManageCom;
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSchema mmLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	private LDPersonSchema mLDPersonSchema = new LDPersonSchema();

	public GrpBodyCheckPrintBL() {
	}

	/**
	 * 传输数据的公共方法
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
		if (!getPrintData("printer")) {
			return false;
		}
		// 打印单个得体检通知书

		// this.afterPrt();//2005-7-15 14:18 郭明宇

		return true;
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

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		// this.mIP = (String) cInputData.get(2);
		// this.mConfigFile = (String) cInputData.get(3);

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

		cError.moduleName = "LCGrpContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private String getComName(String strComCode) {
		msql = "select CodeName from LDcode where Code = '" + strComCode
				+ "' and CodeType = 'station'";
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(msql);
	}

	private String getAgentName(String strAgentGrp) {
		msql = "select Name from LABranchGroup where AgentGroup = '"
				+ strAgentGrp + "'";
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(msql);
	}

	String printer = "";

	private boolean getPrintData(String tPrinter) {
		int iFlag = 0;
		int m, i;

		// 根据印刷号查询打印队列中的纪录
		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号

		logger.debug("PrtNo = " + PrtNo);
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		boolean PEFlag = false; // 打印体检件部分的判断标志

		// 外层模版

		// 打印单个体检通知书

		SSRS sSSRS = new SSRS();
		ExeSQL sExeSQL = new ExeSQL();
		String SQL = "select OldPrtSeq from LOPRTManager where otherno='"
				+ mLOPRTManagerSchema.getOtherNo() + "'" + " and Code = 'G"
				+ PrintManagerBL.CODE_PE + "'" + " and StateFlag = '" + 0 + "'";// 打印状态未"提交"
		sSSRS = sExeSQL.execSQL(SQL);

		if (sSSRS.getMaxRow() != 0) {

			mmPrtSeq = sSSRS.GetText(1, 1);
			logger.debug("mmPrtSeq=" + mmPrtSeq);
			LOPRTManagerDB ttLOPRTManagerDB = new LOPRTManagerDB();
			ttLOPRTManagerDB.setPrtSeq(mmPrtSeq);
			if (ttLOPRTManagerDB.getInfo() == false) {
				mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				buildError("outputXML", "在取得打印队列中数据时发生错误");
				return false;
			}
			mmLOPRTManagerSchema = ttLOPRTManagerDB.getSchema();

			boolean PPOlEFlag = false; // 打印体检件部分的判断标志
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mmLOPRTManagerSchema.getOtherNo());

			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				buildError("outputXML", "在取得LCCont的数据时发生错误");
				return false;
			}
			mLCContSchema = tLCContDB.getSchema();

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB ttLAAgentDB = new LAAgentDB();
			ttLAAgentDB.setAgentCode(mAgentCode);
			if (!ttLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(ttLAAgentDB.mErrors);
				buildError("outputXML", "在取得LAAgent的数据时发生错误");
				return false;
			}
			mLAAgentSchema = ttLAAgentDB.getSchema(); // 保存代理人信息

			// 2-体检信息
			// 2-1 查询体检主表
			LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
			LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
			tLCPENoticeDB.setProposalContNo(mLCContSchema.getContNo());
			tLCPENoticeDB.setPrtSeq(mmPrtSeq);
			tLCPENoticeDB.setCustomerNo(mmLOPRTManagerSchema.getStandbyFlag1());

			if (!tLCPENoticeDB.getInfo()) {
				mErrors.copyAllErrors(ttLAAgentDB.mErrors);
				buildError("outputXML", "在取得体检通知的数据时发生错误");
				return false;
			}

			tLCPENoticeSchema.setSchema(tLCPENoticeDB.getSchema());
			String HealthName = tLCPENoticeSchema.getName(); // 保存体检人

			String PEDate = tLCPENoticeSchema.getPEDate(); // 保存体检日期
			String NeedLimosis = "";
			if (tLCPENoticeSchema.getPEBeforeCond().equals("Y"))// 是否需
			{
				NeedLimosis = "是";
			} else {
				NeedLimosis = "否";
			}

			String PEAddress = tLCPENoticeSchema.getPEAddress(); // 体检地点代码

			// 体检人性别和年龄

			LDPersonDB tLDPersonDB = new LDPersonDB();

			tLDPersonDB.setCustomerNo(tLCPENoticeSchema.getCustomerNo());

			if (tLDPersonDB.getInfo() == false) {
				// mErrors.copyAllErrors(tLDPersonDB.mErrrs);
				buildError("outputXML", "在取得打印队列中数据时发生错误");
				return false;
			}
			mLDPersonSchema = tLDPersonDB.getSchema();

			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCodeType("hospitalcode");
			tLDCodeDB.setCode(PEAddress);
			if (tLDCodeDB.getInfo()) {
				PEAddress = tLDCodeDB.getCodeName();
			} else {
				tLDCodeDB.setCodeType("hospitalcodeuw");
				tLDCodeDB.setCode(PEAddress);
				if (tLDCodeDB.getInfo()) {
					PEAddress = tLDCodeDB.getCodeName();
				} else {
					PEAddress = "该医院代码尚未建立，请确认！";
				}
			}
			// 取得医院信息
			// LDHospitalDB tLDHospitalDB = new LDHospitalDB();
			// tLDHospitalDB.setHospitCode(tLCPENoticeSchema.getPEAddress());
			// if (tLDHospitalDB.getInfo())
			// {
			// hospitalName = tLDHospitalDB.getHospitName();
			// logger.debug("hospitalName");
			// String hospitalAdress = tLDHospitalDB.getAddress();
			// logger.debug("hospitalAdress=" + hospitalAdress);
			// String hospitalTel = tLDHospitalDB.getPhone();
			// logger.debug("hospitalTel=" + hospitalTel);
			// hospitalAddress=tLDHospitalDB.getAddress();
			// hospitalTime=tLDHospitalDB.getRemark();
			// }
			LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
			String[] HosTitle = new String[4];
			HosTitle[0] = "name";
			HosTitle[1] = "address";
			HosTitle[2] = "phone";
			HosTitle[2] = "time";
			ListTable tHosListTable = new ListTable();
			String strHos[] = null;
			tHosListTable.setName("Hospital"); // 对应模版体检部分的行对象名
			String strSql = "";
			logger.debug("分公司代码："
					+ mLCContSchema.getManageCom().substring(0, 4));
			if (mLCContSchema.getManageCom().substring(0, 4).equals("8632")
					|| mLCContSchema.getManageCom().substring(0, 4).equals(
							"8651")
					|| mLCContSchema.getManageCom().substring(0, 4).equals(
							"8637")
					|| mLCContSchema.getManageCom().substring(0, 4).equals(
							"8638")) {
				strSql = "select hospitname,address,phone,remark from ldhospital where 1=1 "
						+ "and areacode like '' || substr((select managecom from lccont where ContNo = '"
						+ mLOPRTManagerSchema.getOtherNo()
						+ "'),0,6) || '%%' order by length(trim(hospitname))";

			} else {
				strSql = "select hospitname,address,phone,remark from ldhospital where 1=1 "
						+ "and areacode like '' || substr((select managecom from lccont where ContNo = '"
						+ mLOPRTManagerSchema.getOtherNo()
						+ "'),0,8) || '%%' order by length(trim(hospitname))";
			}
			SSRS hosSSRS = new SSRS();
			ExeSQL hosExeSQL = new ExeSQL();
			hosSSRS = hosExeSQL.execSQL(strSql);
			if (hosSSRS.getMaxRow() == 0) {
				mErrors.copyAllErrors(tLCPENoticeSet.mErrors);
				buildError("outputXML", "在取得体检医院信息发生错误");
				return false;
			} else {
				for (int j = 1; j <= hosSSRS.getMaxRow(); j++) {
					strHos = new String[4];
					strHos[0] = hosSSRS.GetText(j, 1);
					strHos[1] = hosSSRS.GetText(j, 2); // 序号对应的内容
					strHos[2] = hosSSRS.GetText(j, 3);
					strHos[3] = hosSSRS.GetText(j, 4);
					tHosListTable.add(strHos);
				}
				// if (7 - hosSSRS.getMaxRow() > 0) {
				// for (int p = 0; p < 7 - hosSSRS.getMaxRow(); p++) {
				// strHos = new String[4];
				// strHos[0] = " ";
				// strHos[1] = " "; //序号对应的内容
				// strHos[2] = " ";
				// strHos[3] = " ";
				// tHosListTable.add(strHos);
				// }
				// }

			}

			// 2-1 查询体检子表
			String PEITEM = "";
			String[] PETitle = new String[2];
			PETitle[0] = "ID";
			PETitle[1] = "CHECKITEM";
			ListTable tPEListTable = new ListTable();
			String strPE[] = null;
			tPEListTable.setName("CHECKITEM"); // 对应模版体检部分的行对象名
			String ItemSql = "select PEItemName  from lcpenoticeitem where prtseq = '"
					+ PrtNo
					+ "' and contno = '"
					+ mLCContSchema.getContNo()
					+ "'" + " and FreePE = 'N' order by peitemcode";
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(ItemSql);
			if (tSSRS.getMaxRow() == 0) {
				PEFlag = false;
			} else {
				PEFlag = true;

				for (int p = 1; p <= tSSRS.getMaxRow(); p++) {
					if (tSSRS.GetText(p, 1).equals("身高")
							|| tSSRS.GetText(p, 1).equals("体重")
							|| tSSRS.GetText(p, 1).equals("血压")
							|| tSSRS.GetText(p, 1).equals("内科检查")
							|| tSSRS.GetText(p, 1).equals("外科检查")) {
						Phisical = true;
					}
				}
				if (Phisical == true) {
					PEITEM = "1.临床物理检查  ";
					for (i = 2; i <= tSSRS.getMaxRow() - 4; i++) {
						PEITEM = PEITEM + i + "." + tSSRS.GetText(i + 4, 1)
								+ "  ";
						logger.debug(tSSRS.GetText(i + 4, 1));
					}
				} else {
					for (i = 1; i <= tSSRS.getMaxRow(); i++) {
						PEITEM = PEITEM + i + "." + tSSRS.GetText(i, 1) + "  ";
					}
				}
			}

			// LCPENoticeItemDB tLCPENoticeItemDB = new LCPENoticeItemDB();
			// LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
			// tLCPENoticeItemDB.setContNo(mLCContSchema.getContNo());
			// tLCPENoticeItemDB.setPrtSeq(mmPrtSeq);
			// tLCPENoticeItemDB.setFreePE("N");
			// tLCPENoticeItemSet.set(tLCPENoticeItemDB.query());
			// if (tLCPENoticeItemSet == null ||
			// tLCPENoticeItemSet.size() == 0)
			// {
			// PEFlag = false;
			// }
			// else
			// {
			// PEFlag = true;
			// LCPENoticeItemSchema tLCPENoticeItemSchema;
			// for (int i = 1; i <= tLCPENoticeItemSet.size(); i++)
			// {
			// tLCPENoticeItemSchema = new LCPENoticeItemSchema();
			// tLCPENoticeItemSchema.setSchema(tLCPENoticeItemSet.get(
			// i));
			// strPE = new String[2];
			// strPE[0] = tLCPENoticeItemSchema.getPEItemCode(); //序号
			// logger.debug("序号:"+strPE[0]);
			// strPE[1] = tLCPENoticeItemSchema.getPEItemName(); //序号对应的内容
			// logger.debug("内容:"+strPE[1]);
			// tPEListTable.add(strPE);
			// }
			// }
			// String SpecRequire=tLCPENoticeSchema
			// .getRemark();
			// if(SpecRequire.equals("")){SpecRequire="无";}
			// 生成-年-月-日格式的日期

			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";
			PEDate = PEDate + "-";
			String CheckDate = StrTool.decodeStr(PEDate, "-", 1) + "年"
					+ StrTool.decodeStr(PEDate, "-", 2) + "月"
					+ StrTool.decodeStr(PEDate, "-", 3) + "日";

			// 模版自上而下的元素
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			// texttag.add("BarCode1", mLOPRTManagerSchema.getPrtSeq());
			// texttag.add("BarCodeParam1",
			// "BarHeight=30&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
			// texttag.add("Post", mLCAddressSchema.getZipCode()); //投保人邮政编码
			// texttag.add("Address", mLCAddressSchema.getPostalAddress());
			// //投保人地址
			// texttag.add("AppntName", mLCContSchema.getAppntName());

			// texttag.add("LCCont.AppntName",
			// mLCContSchema.getAppntName()); //投保人名称

			// 模版自上而下的元素
			texttag.add("BarCode1", "UN063");
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
			texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
			logger.debug(mLOPRTManagerSchema.getPrtSeq());
			texttag
					.add(
							"BarCodeParam2",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

			texttag.add("LCCont.ContNo", mLCContSchema.getGrpContNo()); // 投保单号
			logger.debug(mLCContSchema.getContNo());
			// texttag.add("AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.AppntName", tLCPENoticeSchema.getName()); // 投保人名称
			logger.debug(tLCPENoticeSchema.getName());
			texttag.add("CheckDate", CheckDate); // 体检日期
			logger.debug(CheckDate);

			texttag.add("SpecRequire", tLCPENoticeSchema.getRemark()); // 保存特殊要求
			logger.debug(tLCPENoticeSchema.getRemark());
			// texttag.add("Hospital1.Name",strHos[0]);//体检地点信息
			logger.debug(hospitalName);
			// texttag.add("Hospital1.Address",hospitalAdress);
			// texttag.add("Hospital1.Tel", strHos[2]);

			// texttag.add("Hospital1.Address", strHos[1]);
			// texttag.add("Hospital1.Time", strHos[3]);
			// texttag.add("HealthName", HealthName); //被保险人名称
			texttag.add("Birthday", mLDPersonSchema.getBirthday()); // 体检人的出生日期
			texttag.add("WorkType",
					getWork(mLDPersonSchema.getOccupationCode())); // 被保险人职业类型
			texttag.add("IDNO", getIDNO(tLCPENoticeSchema.getCustomerNo()));
			// texttag.add("NeedLimosis", NeedLimosis); //是否需要空腹
			// texttag.add("Hospital", PEAddress); //体检地点
			// texttag.add("Sex", getSexName(mLDPersonSchema.getSex()));
			// //体检人性别
			// texttag.add("Age",
			// PubFun.calInterval(mLDPersonSchema.getBirthday(),
			// PubFun.getCurrentDate(), "Y")); //体检人年龄

			// texttag.add("PrtNo", PrtNo); //刘水号
			// texttag.add("LCCont.PrtNo", mLCContSchema.getPrtNo()); //印刷号
			texttag.add("LCCont.UWOperator", tLCPENoticeDB.getOperator()); // 核保师代码
			logger.debug(tLCPENoticeDB.getOperator());
			texttag.add("SysDate", SysDate);
			logger.debug(SysDate);
			texttag.add("LAAgent.AgentCode", mLCContSchema.getAgentCode()); // 代理人业务号
			texttag.add("LAAgent.Name", mLAAgentSchema.getName()); // 代理人姓名
			texttag.add("ManageComName", getComName(mLCContSchema
					.getManageCom())); // 所属机构
			logger.debug(getComName(mLCContSchema.getManageCom()));
			texttag.add("CHECKITEM", PEITEM);
			texttag.add("LABranchGroup.Name", getAgentName(mLAAgentSchema
					.getAgentGroup()));// 所属分部
			logger.debug("分部为" + mLAAgentSchema.getAgentGroup());
			// 所属分部

			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("GrpPENotice.vts", printer); // 最好紧接着就初始化xml文档
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			// 保存体检信息

			xmlexport.addListTable(tPEListTable, PETitle); // 保存体检信息
			xmlexport.addListTable(tHosListTable, HosTitle); // 体检医院信息

//			Element telement = xmlexport.getDocument()
//					.getRootElement();

			// xmlexportall.addDataSet(xmlexportall.getDocument().getRootElement(),telement);

			// xmlexportall.outptDocumentToFile("e:\\,"test1");
			mResult.clear();
			mResult.addElement(xmlexport);
		}

		return true;

	}

	private String getSexName(String StrSex) {
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCode(StrSex);
		tLDCodeDB.setCodeType("sex");
		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return "";
		}
		return tLDCodeDB.getCodeName();

	}

	/*
	 * 2005-7-15 14:19 郭明宇 此方法是为了测试体检流程而用的,在批处理打印做好之后不再需要此方法
	 */
	private boolean afterPrt() { // 更新打印管理表对应任务的打印状态
		logger.debug("------come in afterPrt()");
		VData tResult = new VData();
		LOPRTManagerDB tLOPRTManaerDB = new LOPRTManagerDB();
		tLOPRTManaerDB.setSchema(mLOPRTManagerSchema);
		LOPRTManagerSchema tLOPRTManagerSchema = tLOPRTManaerDB.getSchema();
		tLOPRTManagerSchema.setStateFlag("1");
		logger.debug("状态更新成功");

		// 发放单证
		LZSysCertifySchema tLZSysCertifyschema = new LZSysCertifySchema();
		tLZSysCertifyschema.setCertifyCode("9888");
		tLZSysCertifyschema.setCertifyNo(tLOPRTManagerSchema.getPrtSeq());
		tLZSysCertifyschema.setSendOutCom("A" + mGlobalInput.ManageCom);
		tLZSysCertifyschema.setReceiveCom("D"
				+ mLOPRTManagerSchema.getAgentCode());
		logger.debug("-------afterPrt()1------------");
		// logger.debug("D" + mLOPRTManagerSchema.getAgentCode());
		// tLZSysCertifyschema.setHandler("SYS");
		tLZSysCertifyschema.setStateFlag("0");
		tLZSysCertifyschema.setHandleDate(PubFun.getCurrentDate());
		// tLZSysCertifyschema.setTakeBackNo(PubFun1.CreateMaxNo("TAKEBACKNO",
		// PubFun.getNoLimit(mManageCom.substring(1))));
		// tLZSysCertifyschema.setSendNo(PubFun1.CreateMaxNo("TAKEBACKNO",
		// PubFun.getNoLimit(mManageCom.substring(1))));
		tLZSysCertifyschema.setOperator(mGlobalInput.Operator);
		tLZSysCertifyschema.setMakeDate(PubFun.getCurrentDate());
		tLZSysCertifyschema.setMakeTime(PubFun.getCurrentTime());
		tLZSysCertifyschema.setModifyDate(PubFun.getCurrentDate());
		tLZSysCertifyschema.setModifyTime(PubFun.getCurrentTime());
		logger.debug("-------afterPrt()2------------");

		tResult.clear();
		MMap tMap = new MMap();
		tMap.put(tLOPRTManagerSchema, "UPDATE");
		tMap.put(tLZSysCertifyschema, "INSERT");
		tResult.add(tMap);
		PubSubmit tSubmit = new PubSubmit();
		logger.debug("-------afterPrt()3------------");
		if (!tSubmit.submitData(tResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpBodyCheckPrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 获取体检人职业
	 */
	private String getWork(String StrWork) {
		String mSql = "select occupationname from ldoccupation where occupationcode='"
				+ StrWork + "' ";
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(mSql);

	}

	// 获取体检人的证件号=======>haopan
	private String getIDNO(String customerno) {
		mSql = "select idno from ldperson where customerno = '" + customerno
				+ "'";
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(mSql);

	}

	public static void main(String args[]) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8100000000353");

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "001";
		tG.ManageCom = "86";

		VData tVData = new VData();
		tVData.addElement(tLOPRTManagerSchema);
		tVData.add(tG);
		GrpBodyCheckPrintUI tGrpBodyCheckPrintUI = new GrpBodyCheckPrintUI();
		tGrpBodyCheckPrintUI.submitData(tVData, "PRINT");
	}
}
