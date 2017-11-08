package com.sinosoft.lis.operfee;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LACommisionDetailBDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LACommisionDetailBSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LACommisionDetailBSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 第三方文件 银行文件(工行)/催缴保单(邮局)
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * SinoSoft
 * 
 * @author GaoHT
 * @version 1.0
 */
public class ThirdFilesBL {
private static Logger logger = Logger.getLogger(ThirdFilesBL.class);

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private String[] existTempFeeNo;
	private String[][] data = null; // 需要生成excel的数据容器
	private VData mInputData;
	private VData mLastFinData = new VData();
	private GlobalInput tGI = new GlobalInput();
	private VData FinFeeVData = new VData(); // 存放财务付费数据
	private MMap map = new MMap();
	private TransferData mTransferData = new TransferData();
	private VData mResult = new VData();
	/* 全局变量 */
	private String mStartDate = "";
	private String mEndDate = "";
	private String mContNo = "";
	private LCContSet mLCContSet = new LCContSet();
	private String ManageCom = "";
	private String mSaleChnl = "";
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	public ThirdFilesBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("银行文件(工行)/催缴保单(邮局)----start");
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 数据校验
		if (!PrepareData()) {
			return false;
		}
		logger.debug("银行文件(工行)/催缴保单(邮局)---Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("银行文件(工行)/催缴保单(邮局)----dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("银行文件(工行)/催缴保单(邮局)----Start  Submit   successful");
		// 提交数据

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {

		tGI = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		if (tGI == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);

			return false;

		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartQuery";
			tError.functionName = "getInputData";
			tError.errorMessage = "请输入查询条件!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		ManageCom = (String) mTransferData.getValueByName("ManageCom");
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl");
		return true;
	}

	private boolean PrepareData() {

		logger.debug("搜集范围为：时间" + mStartDate + "至" + mEndDate + "，管理机构："
				+ ManageCom);

		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String ContnoSql = " select a.* from lccont a, ljspay b"
				+ " where a.contno=b.otherno"
				+ " and b.othernotype in ('1', '2', '3')"
				+ " and b.startpaydate >= '?mStartDate?' and b.startpaydate <= '?mEndDate?'";
		// + "' and b.ManageCom like '" + tGI.ManageCom + "%'";
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);

		if (mSaleChnl != null && !mSaleChnl.equals("")) {
			ContnoSql = ContnoSql + " and a.SaleChnl='?mSaleChnl?'";
			sqlbv.put("mSaleChnl", mSaleChnl);
		}

		if (ManageCom != null && !ManageCom.equals("")) {
			ContnoSql = ContnoSql + " and b.ManageCom like concat('?ManageCom1?','%')";
			sqlbv.put("ManageCom1", ManageCom);
		} else {
			ContnoSql = ContnoSql + " and b.ManageCom like concat('?ManageCom2?','%')";
			sqlbv.put("ManageCom2", tGI.ManageCom);
		}

		if (mContNo != null && !mContNo.equals("")) {
			ContnoSql = ContnoSql + " and a.contno='?mContNo?'";
			sqlbv.put("mContNo", mContNo);
		}

		sqlbv.sql(ContnoSql);
		LCContDB tLCContDB = new LCContDB();
		mLCContSet = tLCContDB.executeQuery(sqlbv);
		logger.debug("搜集保单信息=========" + ContnoSql);
		if (mLCContSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartQuery";
			tError.functionName = "getInputData";
			tError.errorMessage = "此时间段无应收保单纪录";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("本次数据统计条数=========" + mLCContSet.size());
		return true;
	}

	private boolean dealData() {
		logger.debug("----------------------开始业务处理-----------------------");
		data = new String[mLCContSet.size()][32];
		for (int i = 1; i <= mLCContSet.size(); i++) {
			try {
				LCContSchema tLCContSchema = new LCContSchema();
				tLCContSchema = mLCContSet.get(i);
				String paymode = tLCContSchema.getPayLocation(); // 交费方式
				String Contno = tLCContSchema.getContNo(); // 保单号
				String AppntName = tLCContSchema.getAppntName(); // 投保人姓名

				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(Contno);
				if (!tLCAppntDB.getInfo()) {
					CError tError = new CError();
					tError.moduleName = "IndiDueFeePartQuery";
					tError.functionName = "getInputData";
					tError.errorMessage = "查询投保人信息失败";
					this.mErrors.addOneError(tError);
					// return false;
				}
				LCAppntSchema tLCAppntSchema = new LCAppntSchema();
				tLCAppntSchema = tLCAppntDB.getSchema();

				String CustomerNo = tLCAppntSchema.getAppntNo();
				String AddressNo = tLCAppntSchema.getAddressNo();

				LCAddressDB tLCAddressDB = new LCAddressDB();
				tLCAddressDB.setAddressNo(AddressNo);
				tLCAddressDB.setCustomerNo(CustomerNo);
				if (!tLCAddressDB.getInfo()) {
					CError tError = new CError();
					tError.moduleName = "IndiDueFeePartQuery";
					tError.functionName = "getInputData";
					tError.errorMessage = "查询客户地址信息失败";
					this.mErrors.addOneError(tError);
					// return false;
				}
				LCAddressSchema tLCAddressSchema = new LCAddressSchema();
				tLCAddressSchema = tLCAddressDB.getSchema();
				String ZipCode = tLCAddressSchema.getZipCode(); // 客户邮编
				// String PostalAddress = tLCAddressSchema.getPostalAddress () ; //客户地址
				ExeSQL tBExeSQL = new ExeSQL();
				SSRS tzuSSRS = new SSRS();
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql("select concat((select placename from ldaddress where placetype = '03' and trim(placecode) = lc.county),lc.postaladdress) "
						+ "from lcaddress lc "
						+ "where lc.addressno =?addressno? and lc.customerno='?customerno?'");
				sqlbv1.put("addressno", tLCAddressSchema.getAddressNo());
				sqlbv1.put("customerno", tLCAppntSchema.getAppntNo());
				tzuSSRS = tBExeSQL
						.execSQL(sqlbv1);
				String PostalAddress = "";
				if (tzuSSRS.MaxNumber == 0) {
					PostalAddress = tLCAddressSchema.getPostalAddress();
				} else {
					PostalAddress = tzuSSRS.GetText(1, 1);
				}
				String tAppntSex = tLCAppntSchema.getAppntSex();
				String txsAppntName = "";
				if (tAppntSex.equals("0")) {
					txsAppntName = "先生";
				} else if (tAppntSex.equals("1")) {
					txsAppntName = "女士";
				} else {
					txsAppntName = "客户";
				}

				String polSql = "select * from lcpol where contno = '?Contno?' and polno in (select polno from ljspayperson where paytype='ZC' and contno = '?Contno?') order by proposalno ";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(polSql);
				sqlbv2.put("Contno", Contno);
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				tLCPolSet = tLCPolDB.executeQuery(sqlbv2);

				int RiskCount = tLCPolSet.size();

				if (RiskCount == 0) {
					CError tError = new CError();
					tError.moduleName = "IndiDueFeePartQuery";
					tError.functionName = "getInputData";
					tError.errorMessage = "未找到保单险种信息";
					this.mErrors.addOneError(tError);
					// return false;
				}

				String payintv = ""; // 交费类型
				/*
				 * if (tLCContSchema.equals ("1")) { payintv = "月" ; } else if
				 * (tLCContSchema.equals ("3")) { payintv = "季" ; } else if
				 * (tLCContSchema.equals ("6")) { payintv = "半年" ; } else { payintv =
				 * "年" ; }
				 */
				if (tLCPolSet.get(1).getPayIntv() == 1) {
					payintv = "月";
				} else if (tLCPolSet.get(1).getPayIntv() == 3) {
					payintv = "季";
				} else if (tLCPolSet.get(1).getPayIntv() == 6) {
					payintv = "半年";
				} else if (tLCPolSet.get(1).getPayIntv() == 12) {
					payintv = "年";
				} else if (tLCPolSet.get(1).getPayIntv() == 0) {
					payintv = "趸";
				} else {
					payintv = "年";
				}

				// String paycountSql ="select max(paycount) from ljspayperson where contno='" +
				// Contno + "'" ;
				String paycountSql = "select paycount,to_char(lastpaytodate,'DD'),to_char(lastpaytodate,'YYMM') "
						+ "from ljspayperson ljs where contno='?Contno?' and ljs.paycount=(select distinct max(paycount) from ljspayperson where contno=ljs.contno) "
						+ "And Exists (Select 'X' From lcpol Where polno=mainpolno And ljs.polno=polno)";
				SSRS nSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql(paycountSql);
				sqlbv3.put("Contno", Contno);
				nSSRS = tExeSQL.execSQL(sqlbv3);

				String paycount = nSSRS.GetText(1, 1); // 缴费次数
				String BankAccNo = tLCContSchema.getBankAccNo(); // 银行账号

				// paycountSql = "select
				// to_char(lastpaytodate,'DD'),to_char(lastpaytodate,'YYMM') from ljspayperson
				// where contno='" + Contno + "' and paycount='" + paycount + "'" ;
				// tExeSQL = new ExeSQL () ;
				// nSSRS = tExeSQL.execSQL (paycountSql) ;
				String Yearmonth = nSSRS.GetText(1, 3);
				String Day = nSSRS.GetText(1, 2);

				String AgentCode = tLCContSchema.getAgentCode();
				String AgentGroup = tLCContSchema.getAgentGroup();
				String Managecom = tLCContSchema.getManageCom();
				// 查询代理人的电话地址
				String AgentSql = "select name,phone,bp from laagent where AgentCode = '?AgentCode?'";
				SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				sqlbv4.sql(AgentSql);
				sqlbv4.put("AgentCode", AgentCode);
				tExeSQL = new ExeSQL();
				nSSRS = tExeSQL.execSQL(sqlbv4);
				String AgentName = nSSRS.GetText(1, 1); // 代理人姓名
				String AgentPhone = nSSRS.GetText(1, 2); // 业务员电话
				String AgentBP = nSSRS.GetText(1, 3); // 呼机
				// 查询管理机构的地址电话
				String ManageComSql = "select servicephone,address,zipcode from ldcom where comcode = '?Managecom?'";
				tExeSQL = new ExeSQL();
				SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql(ManageComSql);
				sqlbv5.put("Managecom", Managecom);
				nSSRS = tExeSQL.execSQL(sqlbv5);
				String ServicePhone = nSSRS.GetText(1, 1); // 服务电话
				String ManagecomAddress = nSSRS.GetText(1, 2);
				String ManagecomZipcode = nSSRS.GetText(1, 3);
				// 查询区部组信息
				AgentSql = "select trim(BranchSeries) from LABranchGroup where AgentGroup = '?AgentGroup?'";
				SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
				sqlbv6.sql(AgentSql);
				sqlbv6.put("AgentGroup", AgentGroup);
				tExeSQL = new ExeSQL();
				nSSRS = tExeSQL.execSQL(sqlbv6);
				String BranchSeries = nSSRS.GetText(1, 1);
				String QU = "";
				String BU = "";
				if (BranchSeries.length() == 38 || BranchSeries.length() == 25) {
					String Sql = "select name from LABranchGroup where AgentGroup in ('?AgentGroup?')";
					ArrayList<String> strArr=new ArrayList<String>();
					strArr.add(BranchSeries.substring(0, 12));
					strArr.add(BranchSeries.substring(13, 25));
					SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
					sqlbv7.sql(Sql);
					sqlbv7.put("AgentGroup", strArr);
					tExeSQL = new ExeSQL();
					nSSRS = tExeSQL.execSQL(sqlbv7);
					QU = nSSRS.GetText(1, 1);
					BU = nSSRS.GetText(2, 1);
				} else if (BranchSeries.length() == 12) {
					String Sql = "select name from LABranchGroup where AgentGroup in ('?AgentGroup?')";
					SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
					sqlbv8.sql(Sql);
					sqlbv8.put("AgentGroup",BranchSeries.substring(0, 12));
					tExeSQL = new ExeSQL();
					nSSRS = tExeSQL.execSQL(sqlbv8);
					QU = nSSRS.GetText(1, 1);
				}
				String SumPrem = "select SumDuePayMoney from ljspay where othernotype in ('1','2','3') and otherno ='?Contno?'";
				SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
				sqlbv9.sql(SumPrem);
				sqlbv9.put("Contno",Contno);
				tExeSQL = new ExeSQL();
				nSSRS = tExeSQL.execSQL(sqlbv9);
				String SumMoney = nSSRS.GetText(1, 1);

				// 查询代理人是否发生变更，并取出变更数据
				LACommisionDetailBDB tLACommisionDetailBDB = new LACommisionDetailBDB();
				LACommisionDetailBSet tLACommisionDetailBSet = new LACommisionDetailBSet();
				String LJSql = "select * from LACommisionDetailB where GrpContNo = '?Contno?'";
				SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
				sqlbv10.sql(LJSql);
				sqlbv10.put("Contno", Contno);;
				tLACommisionDetailBSet = tLACommisionDetailBDB
						.executeQuery(sqlbv10);
				String ChageAgent = "";
				String ChageAgentPhone = "";
				String ChageAgentCont = "";
				if (tLACommisionDetailBSet.size() > 0) // 说明存在变更信息
				{
					for (int y = 1; y <= tLACommisionDetailBSet.size(); y++) {
						LACommisionDetailBSchema tLACommisionDetailBSchema = new LACommisionDetailBSchema();
						tLACommisionDetailBSchema = tLACommisionDetailBSet
								.get(y);

						String LastPayDateSql = "select * from ljapayperson where paycount in (select max(paycount) from ljapayperson where contno = '?contno?')";
						SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
						sqlbv11.sql(LastPayDateSql);
						sqlbv11.put("contno",tLACommisionDetailBSchema.getGrpContNo());
						LJAPayPersonSet tLJAPayPerSonSet = new LJAPayPersonSet();
						LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
						tLJAPayPerSonSet = tLJAPayPersonDB
								.executeQuery(sqlbv11);
						if (tLJAPayPerSonSet.size() > 0) {
							String LastPayDate = tLJAPayPerSonSet.get(1)
									.getLastPayToDate();
							if (LastPayDate.compareTo(tLACommisionDetailBSchema
									.getStartSerDate()) > 0
									&& LastPayDate
											.compareTo(tLACommisionDetailBSchema
													.getEndSerDate()) < 0) {
								String JBAgent = tLACommisionDetailBSchema
										.getAgentCode();
								String KBSql = "select name,phone from laagent where AgentCode = '?JBAgent?'";
								SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
								sqlbv12.sql(KBSql);
								sqlbv12.put("JBAgent",JBAgent);
								nSSRS = tExeSQL.execSQL(sqlbv12);
								ChageAgent = nSSRS.GetText(1, 1); // 变更后的业务员
								ChageAgentPhone = nSSRS.GetText(1, 2); // 变更后的业务员
								ChageAgentCont = tLACommisionDetailBSchema
										.getGrpContNo(); // 涉及的保单
							}
						}
					}

				}

				/*-----------------------准备往生成excel的二维数组--------------------------*/

				data[i - 1][0] = paymode;
				data[i - 1][1] = ZipCode;
				data[i - 1][2] = PostalAddress;
				data[i - 1][3] = AppntName;
				data[i - 1][4] = txsAppntName;
				data[i - 1][5] = Contno;
				data[i - 1][7] = Day;
				data[i - 1][6] = Yearmonth;

				switch (RiskCount) {
				case 1: {
					String RiskCode = tLCPolSet.get(1).getRiskCode();
					String PolNo = tLCPolSet.get(1).getPolNo();
					String RiskSql = "select riskshortname from lmrisk where riskcode = '?RiskCode?'";
					// tExeSQL = new ExeSQL () ;
					SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
					sqlbv13.sql(RiskSql);
					sqlbv13.put("RiskCode",RiskCode);
					nSSRS = tExeSQL.execSQL(sqlbv13);
					String RiskName = nSSRS.GetText(1, 1);
					String Prem = "select sum(SumActuPayMoney) from ljspayperson where polno = '?PolNo?'";
					SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
					sqlbv14.sql(Prem);
					sqlbv14.put("PolNo",PolNo);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv14);
					String Prem1 = nSSRS.GetText(1, 1);
					data[i - 1][8] = RiskName;
					data[i - 1][9] = Prem1;
					data[i - 1][10] = "";
					data[i - 1][11] = "";
					data[i - 1][12] = "";
					data[i - 1][13] = "";
					data[i - 1][14] = "";
					data[i - 1][15] = "";
					break;
				}
				case 2: {
					String RiskCode1 = tLCPolSet.get(1).getRiskCode();
					String RiskCode2 = tLCPolSet.get(2).getRiskCode();
					String RiskSql = "select riskshortname from lmrisk where riskcode = '?RiskCode1?'";
					SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
					sqlbv15.sql(RiskSql);
					sqlbv15.put("RiskCode1", RiskCode1);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv15);
					String RiskName1 = nSSRS.GetText(1, 1);
					RiskSql = "select riskshortname from lmrisk where riskcode = '?RiskCode2?'";
					SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
					sqlbv16.sql(RiskSql);
					sqlbv16.put("RiskCode2",RiskCode2);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv16);
					String RiskName2 = nSSRS.GetText(1, 1);
					String PolNo1 = tLCPolSet.get(1).getPolNo();
					String PremSql = "select sum(SumActuPayMoney) from ljspayperson where polno = '?PolNo1?'";
					SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
					sqlbv17.sql(PremSql);
					sqlbv17.put("PolNo1", PolNo1);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv17);
					String Prem1 = nSSRS.GetText(1, 1);
					String PolNo2 = tLCPolSet.get(2).getPolNo();
					PremSql = "select sum(SumActuPayMoney) from ljspayperson where polno = '?PolNo2?'";
					SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
					sqlbv18.sql(PremSql);
					sqlbv18.put("PolNo2", PolNo2);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv18);
					String Prem2 = nSSRS.GetText(1, 1);

					data[i - 1][8] = RiskName1;
					data[i - 1][9] = Prem1;
					data[i - 1][10] = RiskName2;
					data[i - 1][11] = Prem2;
					data[i - 1][12] = "";
					data[i - 1][13] = "";
					data[i - 1][14] = "";
					data[i - 1][15] = "";
					break;
				}
				case 3: {
					String RiskCode1 = tLCPolSet.get(1).getRiskCode();
					String RiskCode2 = tLCPolSet.get(2).getRiskCode();
					String RiskCode3 = tLCPolSet.get(3).getRiskCode();
					String RiskSql = "select riskshortname from lmrisk where riskcode = '?RiskCode1?'";
					SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
					sqlbv19.sql(RiskSql);
					sqlbv19.put("RiskCode1",RiskCode1);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv19);
					String RiskName1 = nSSRS.GetText(1, 1);
					RiskSql = "select riskshortname from lmrisk where riskcode = '?RiskCode2?'";
					SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
					sqlbv20.sql(RiskSql);
					sqlbv20.put("RiskCode2",RiskCode2);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv20);
					String RiskName2 = nSSRS.GetText(1, 1);
					RiskSql = "select riskshortname from lmrisk where riskcode = '?RiskCode3?'";
					SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
					sqlbv21.sql(RiskSql);
					sqlbv21.put("RiskCode3",RiskCode3);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv21);
					String RiskName3 = nSSRS.GetText(1, 1);
					String PolNo1 = tLCPolSet.get(1).getPolNo();
					String PremSql = "select sum(SumActuPayMoney) from ljspayperson where polno = '?PolNo1?'";
					SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
					sqlbv22.sql(PremSql);
					sqlbv22.put("PolNo1", PolNo1);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv22);
					String Prem1 = nSSRS.GetText(1, 1);
					String PolNo2 = tLCPolSet.get(2).getPolNo();
					PremSql = "select sum(SumActuPayMoney) from ljspayperson where polno = '?PolNo2?'";
					SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
					sqlbv23.sql(PremSql);
					sqlbv23.put("PolNo2", PolNo2);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv23);
					String Prem2 = nSSRS.GetText(1, 1);
					String PolNo3 = tLCPolSet.get(3).getPolNo();
					PremSql = "select sum(SumActuPayMoney) from ljspayperson where polno = '?PolNo3?'";
					SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
					sqlbv24.sql(PremSql);
					sqlbv24.put("PolNo3",PolNo3);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv24);
					String Prem3 = nSSRS.GetText(1, 1);
					data[i - 1][8] = RiskName1;
					data[i - 1][9] = Prem1;
					data[i - 1][10] = RiskName2;
					data[i - 1][11] = Prem2;
					data[i - 1][12] = RiskName3;
					data[i - 1][13] = Prem3;
					data[i - 1][14] = "";
					data[i - 1][15] = "";
					break;
				}
				case 4: {
					String RiskCode1 = tLCPolSet.get(1).getRiskCode();
					String RiskCode2 = tLCPolSet.get(2).getRiskCode();
					String RiskCode3 = tLCPolSet.get(3).getRiskCode();
					String RiskCode4 = tLCPolSet.get(4).getRiskCode();
					String RiskSql = "select riskshortname from lmrisk where riskcode = '?RiskCode1?'";
					SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
					sqlbv25.sql(RiskSql);
					sqlbv25.put("RiskCode1",RiskCode1);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv25);
					String RiskName1 = nSSRS.GetText(1, 1);
					RiskSql = "select riskshortname from lmrisk where riskcode = '?RiskCode2?'";
					SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
					sqlbv26.sql(RiskSql);
					sqlbv26.put("RiskCode2", RiskCode2);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv26);
					String RiskName2 = nSSRS.GetText(1, 1);
					RiskSql = "select riskshortname from lmrisk where riskcode = '?RiskCode3?'";
					SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
					sqlbv27.sql(RiskSql);
					sqlbv27.put("RiskCode3", RiskCode3);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv27);
					String RiskName3 = nSSRS.GetText(1, 1);
					RiskSql = "select riskshortname from lmrisk where riskcode = '?RiskCode4?'";
					SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
					sqlbv28.sql(RiskSql);
					sqlbv28.put("RiskCode4",RiskCode4);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv28);
					String RiskName4 = nSSRS.GetText(1, 1);
					String PolNo1 = tLCPolSet.get(1).getPolNo();
					String PremSql = "select sum(SumActuPayMoney) from ljspayperson where polno = '?PolNo1?'";
					SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
					sqlbv29.sql(PremSql);
					sqlbv29.put("PolNo1", PolNo1);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv29);
					String Prem1 = nSSRS.GetText(1, 1);
					String PolNo2 = tLCPolSet.get(2).getPolNo();
					PremSql = "select sum(SumActuPayMoney) from ljspayperson where polno = '?PolNo2?'";
					SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
					sqlbv30.sql(PremSql);
					sqlbv30.put("PolNo2",PolNo2);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv30);
					String Prem2 = nSSRS.GetText(1, 1);
					String PolNo3 = tLCPolSet.get(3).getPolNo();
					PremSql = "select sum(SumActuPayMoney) from ljspayperson where polno = '?PolNo3?'";
					SQLwithBindVariables sqlbv31=new SQLwithBindVariables();
					sqlbv31.sql(PremSql);
					sqlbv31.put("PolNo3",PolNo3);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv31);
					String Prem3 = nSSRS.GetText(1, 1);
					String PolNo4 = tLCPolSet.get(4).getPolNo();
					PremSql = "select sum(SumActuPayMoney) from ljspayperson where polno = '?PolNo4?'";
					SQLwithBindVariables sqlbv32=new SQLwithBindVariables();
					sqlbv32.sql(PremSql);
					sqlbv32.put("PolNo4",PolNo4);
					// tExeSQL = new ExeSQL () ;
					nSSRS = tExeSQL.execSQL(sqlbv32);
					String Prem4 = nSSRS.GetText(1, 1);
					data[i - 1][8] = RiskName1;
					data[i - 1][9] = Prem1;
					data[i - 1][10] = RiskName2;
					data[i - 1][11] = Prem2;
					data[i - 1][12] = RiskName3;
					data[i - 1][13] = Prem3;
					data[i - 1][14] = RiskName4;
					data[i - 1][15] = Prem4;
					break;
				}
				}
				data[i - 1][16] = payintv;
				data[i - 1][17] = paycount;
				data[i - 1][18] = BankAccNo;
				data[i - 1][19] = AgentName;
				data[i - 1][20] = AgentPhone;
				data[i - 1][21] = AgentBP;
				data[i - 1][22] = ServicePhone;
				data[i - 1][23] = ManagecomAddress;
				data[i - 1][24] = ManagecomZipcode;
				data[i - 1][22] = ServicePhone;
				data[i - 1][23] = ManagecomAddress;
				data[i - 1][24] = ManagecomZipcode;
				data[i - 1][25] = QU;
				data[i - 1][26] = BU;
				data[i - 1][27] = SumMoney;
				data[i - 1][28] = ChageAgent;
				data[i - 1][29] = ChageAgentPhone;
				data[i - 1][30] = ChageAgentCont;
				if (!tLCContSchema.getBankCode().equals("")
						&& !tLCContSchema.getBankCode().equals(null)) {
					SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
					sqlbv33.sql("select bankname from ldbank where bankcode='?bankcode?'");
					sqlbv33.put("bankcode",tLCContSchema.getBankCode());
					nSSRS = tExeSQL
							.execSQL(sqlbv33);
				}
				data[i - 1][31] = nSSRS.GetText(1, 1);
				/*----------------------准备结束------- --------------------*/

			} catch (Exception ex) {
				logger.debug("数据采集异常::::::::::::::::::::::::" + ex);
				continue;
			}
		}
		return true;
	}

	private boolean prepareOutputData() {
		return true;
	}

	public String[][] getResult() {
		return data;
	}

	public static void main(String arg[]) {
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "865100";
		mGlobalInput.ManageCom = "865100";
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2006-09-15");
		tTransferData.setNameAndValue("EndDate", "2006-09-25");
		tTransferData.setNameAndValue("ContNo", "");
		tVData.addElement(mGlobalInput);
		tVData.addElement(tTransferData);
		ThirdFilesBL tThirdFilesBL = new ThirdFilesBL();
		if (!tThirdFilesBL.submitData(tVData, "OPERATE")) {
			logger.debug("ThirdFilesBL出错");
		}
	}
}
