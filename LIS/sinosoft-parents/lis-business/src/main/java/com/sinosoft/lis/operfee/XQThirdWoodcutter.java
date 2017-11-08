package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.vschema.LCContSet;
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
 * 第三方文件
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
 * @author
 * @version 1.0
 */
public class XQThirdWoodcutter {
private static Logger logger = Logger.getLogger(XQThirdWoodcutter.class);

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
	private LCContSet mLCContSet = new LCContSet();
	private String ManageCom = "";
	private String mSaleChnl = "";

	public XQThirdWoodcutter() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("开始啦！！！！！！！！！");
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 数据校验
		if (!PrepareData()) {
			return false;
		}
		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("Start  Submit...");
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
		ManageCom = (String) mTransferData.getValueByName("ManageCom");
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl");
		if (mSaleChnl != null && !mSaleChnl.equals("null")
				&& !mSaleChnl.equals("")) {
			if (mSaleChnl.equals("1")) {
				mSaleChnl = "2";
			} else if (mSaleChnl.equals("2")) {
				mSaleChnl = "1";
			}
		} else {
			mSaleChnl = "2";
		}
		return true;
	}

	private boolean PrepareData() {
		return true;
	}

	private boolean dealData() {
		logger.debug("----------------------开始业务处理-----------------------");
		boolean tFlag = false;
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SSRS t1SSRS = new SSRS();
		SSRS t2SSRS = new SSRS();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String tempSQL = "select polno,bankdealdate , paymoney , accno,paycode,(select distinct bankname from ldbank where bankcode=lyr.bankcode And comcode =substr(lyr.comcode,1,6))"
				+ " from lyreturnfrombankb lyr"
				+ " where lyr.notype = '?mSaleChnl?' "
				+ " and lyr.banksuccflag = '0000' "
				+ " and lyr.bankdealdate >= '?mStartDate?' and  lyr.bankdealdate <= '?mEndDate?'"
				// 去掉冲正,回退
				+ " And Not Exists (Select 'X' From Ljapayperson b Where b.getnoticeno = lyr.paycode "
				+ " And Exists (Select 'X' From Ljapayperson a Where a.getnoticeno =b.getnoticeno And a.Sumactupaymoney < 0))"
				// 去掉暂收退费
				+ " And Not Exists (Select 'X' From ljagettempfee Where tempfeeno=lyr.Paycode) ";
		// tSSRS = tExeSQL.execSQL("select polno,bankdealdate , paymoney , accno from
		// lyreturnfrombankb where notype = '"+mSaleChnl+"' and banksuccflag = '0000'
		// ");

		sqlbv.put("mSaleChnl", mSaleChnl);
		sqlbv.put("mStartDate",mStartDate);
		sqlbv.put("mEndDate",mEndDate);
		if (ManageCom.equals("") || ManageCom.equals(null)
				|| ManageCom.equals("null")) {
			tempSQL = tempSQL + " and lyr.comcode like concat('?comcode1?','%')";
			sqlbv.put("comcode1", tGI.ManageCom);
		} else {
			tempSQL = tempSQL + " and lyr.comcode like concat('?comcode2?','%')";
			sqlbv.put("comcode2", ManageCom);
		}

		tSSRS = tExeSQL.execSQL(sqlbv);

		data = new String[tSSRS.getMaxRow() + 1][23];
		int set = 0;
		int i = 0;
		for (int k = 1; k <= tSSRS.getMaxRow(); k++) {
			/*-----------------------准备往生成excel的二维数组--------------------------*/
			tFlag = false; // 初始化如果主险取应收，附加险也取应收
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql("select t.ZipCode, "
					+ "concat((select placename from ldaddress where placetype = '03' and placecode =  t.county),t.PostalAddress),"
					+ "n.appntname, "
					+ "Getappntphone(lc.contno,'') as phone, "
					+ "n.appntsex, "
					+ "lc.agentcode, "
					+ "(select distinct name from laagent where agentcode = trim(lc.agentcode)), "
					+ "getGlobalBranch(trim(lc.agentcode),lc.contno) "
					+ " from lccont lc, lcaddress t,lcappnt n  "
					+ " where n.addressno = t.addressno "
					+ " and n.appntno = lc.appntno  "
					+ " and t.customerno = n.appntno "
					+ " and n.contno = lc.contno  "
					+ " and lc.contno = '?contno?' ");
			sqlbv1.put("contno",tSSRS.GetText(k, 1));
			t1SSRS = tExeSQL
					.execSQL(sqlbv1);
			if (t1SSRS.getMaxNumber() <= 0) {
				continue;
			} else {
				i = i + 1;
			}
			// 投保人邮编
			if (t1SSRS.GetText(1, 1).equals("") || t1SSRS.GetText(1, 1) == null
					|| t1SSRS.GetText(1, 1).equals("null")) {
				data[i - 1][0] = "";
			} else {
				data[i - 1][0] = t1SSRS.GetText(1, 1);
			}

			// 投保人住址
			if (t1SSRS.GetText(1, 2).equals("") || t1SSRS.GetText(1, 2) == null
					|| t1SSRS.GetText(1, 2).equals("null")) {
				data[i - 1][1] = "";
			} else {
				data[i - 1][1] = t1SSRS.GetText(1, 2);
			}

			// 投保人姓名
			if (t1SSRS.GetText(1, 3).equals("") || t1SSRS.GetText(1, 3) == null
					|| t1SSRS.GetText(1, 3).equals("null")) {
				data[i - 1][2] = "";
			} else {
				data[i - 1][2] = t1SSRS.GetText(1, 3);
			}

			// 投保人称呼
			if (!t1SSRS.GetText(1, 5).equals("")
					&& t1SSRS.GetText(1, 5) != null
					&& !t1SSRS.GetText(1, 5).equals("null")) {
				if (t1SSRS.GetText(1, 5).equals("0")) {
					data[i - 1][3] = "先生";
				} else if (t1SSRS.GetText(1, 5).equals("1")) {
					data[i - 1][3] = "女士";
				} else {
					data[i - 1][3] = "客户";
				}
			} else {
				data[i - 1][3] = "";
			}
			// 投保人电话
			if (t1SSRS.GetText(1, 4).equals("") || t1SSRS.GetText(1, 4) == null
					|| t1SSRS.GetText(1, 4).equals("null")) {
				data[i - 1][4] = "";
			} else {
				data[i - 1][4] = t1SSRS.GetText(1, 4);
			}

			// 业务员号
			if (t1SSRS.GetText(1, 6).equals("") || t1SSRS.GetText(1, 6) == null
					|| t1SSRS.GetText(1, 6).equals("null")) {
				data[i - 1][5] = "";
			} else {
				data[i - 1][5] = t1SSRS.GetText(1, 6);
			}

			// 业务员姓名
			if (t1SSRS.GetText(1, 7).equals("") || t1SSRS.GetText(1, 7) == null
					|| t1SSRS.GetText(1, 7).equals("null")) {
				data[i - 1][6] = "";
			} else {
				data[i - 1][6] = t1SSRS.GetText(1, 7);
			}

			// 营销服务部

			if (!t1SSRS.GetText(1, 8).equals("")
					&& t1SSRS.GetText(1, 8) != null
					&& !t1SSRS.GetText(1, 8).equals("null")) {
				data[i - 1][7] = t1SSRS.GetText(1, 8);
			} else {
				data[i - 1][7] = "";
			}
			// 划款成功日期
			data[i - 1][8] = tSSRS.GetText(k, 2);
			// 主险
			data[i - 1][9] = "";
			// 险种名称2
			data[i - 1][10] = "";
			// 险种名称3
			data[i - 1][11] = "";
			// 险种名称4
			data[i - 1][12] = "";
			// 保单号
			data[i - 1][13] = tSSRS.GetText(k, 1);
			// 缴费次
			data[i - 1][14] = "";
			// 主险保费
			data[i - 1][15] = "";
			// 应缴保费2
			data[i - 1][16] = "";
			// 应缴保费3
			data[i - 1][17] = "";
			// 应缴保费4
			data[i - 1][18] = "";

			// 取主险实收数据
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql("select (select distinct riskshortname from lmrisk where  riskcode=a.riskcode), "
					+ " sum(a.SumActuPayMoney) ,"
					+ " a.paycount, "
					+ " d.polno,d.mainpolno"
					+ " from ljapayperson a, ljapay b, lcpol d "
					+ " where b.otherno = a.contno "
					+ " and a.GetNoticeNo = b.GetNoticeNo "
					+ " and b.OtherNoType = '?mSaleChnl?'"
					+ " and a.contno = '?contno?' "
					+ " and b.GetNoticeNo='?GetNoticeNo?'"
					+ " and a.polno = d.polno "
					+ " and a.contno = d.contno "
					// + " and d.polno = d.mainpolno "
					// + " and a.confdate in (select max(confdate) from ljapayperson where contno =
					// '" + tSSRS.GetText (m,1) + "' "
					+ " and a.paytype = 'ZC' "
					+ " Group By a.Riskcode,a.Paycount,d.polno,d.mainpolno");
			sqlbv2.put("mSaleChnl", mSaleChnl);
			sqlbv2.put("contno", tSSRS.GetText(k, 1));
			sqlbv2.put("GetNoticeNo", tSSRS.GetText(k, 5));
			t2SSRS = tExeSQL
					.execSQL(sqlbv2);

			if (t2SSRS == null || t2SSRS.equals("null") || t2SSRS.MaxRow == 0) // 如果没有主险的实收就取主险应收数据
			{
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql("select (select riskshortname from lmrisk where  riskcode=a.riskcode),"
						+ " sum(a.SumActuPayMoney),"
						+ " a.paycount ,"
						+ " d.polno,d.mainpolno"
						+ " from ljspayperson a, ljspay b, lcpol d "
						+ " where b.otherno = a.contno "
						+ " and a.GetNoticeNo = b.GetNoticeNo  "
						+ " and b.OtherNoType = '?mSaleChnl?'"
						+ " and a.contno = '?contno?' "
						+ " and b.GetNoticeNo='?GetNoticeNo?'"
						+ " and a.polno = d.polno "
						+ " and a.contno = d.contno "
						// + " and d.polno = d.mainpolno "
						+ " Group By a.Riskcode,a.Paycount,d.polno,d.mainpolno");
				sqlbv3.put("mSaleChnl", mSaleChnl);
				sqlbv3.put("contno", tSSRS.GetText(k, 1));
				sqlbv3.put("GetNoticeNo", tSSRS.GetText(k, 5));

				t2SSRS = tExeSQL
						.execSQL(sqlbv3);

			} else {
				tFlag = true;
			}

			if (t2SSRS != null && !t2SSRS.equals("null") && t2SSRS.MaxRow != 0) {
				int m = 1;
				for (int j = 1; j <= t2SSRS.getMaxRow(); j++) {
					if (t2SSRS.GetText(j, 1) != null
							&& !t2SSRS.GetText(j, 1).equals("")
							&& !t2SSRS.GetText(j, 1).equals("null")) {
						if (t2SSRS.GetText(j, 4).equals(t2SSRS.GetText(j, 5))) { // 险种名称和次数
							data[i - 1][9] = t2SSRS.GetText(j, 1);
							data[i - 1][14] = t2SSRS.GetText(j, 3);
							// 主险保费
							if (t2SSRS.GetText(j, 2) != null
									&& !t2SSRS.GetText(j, 2).equals("")
									&& !t2SSRS.GetText(j, 2).equals("null")) {
								data[i - 1][15] = new DecimalFormat("0.00")
										.format(Double.parseDouble(t2SSRS
												.GetText(j, 2)));
							}
						} else {
							// 附加险险种名称
							if (t2SSRS.GetText(j, 1) != null
									&& !t2SSRS.GetText(j, 1).equals("null")) {
								data[i - 1][9 + m] = t2SSRS.GetText(j, 1);
								// 保费
								if (t2SSRS.GetText(j, 2) != null
										&& !t2SSRS.GetText(j, 2).equals("")
										&& !t2SSRS.GetText(j, 2).equals("null")) {
									data[i - 1][15 + m] = new DecimalFormat(
											"0.00").format(Double
											.parseDouble(t2SSRS.GetText(j, 2)));
								}
								m = m + 1;
							}
						}
					}
				}
				/*
				 * if (t2SSRS.GetText (1,1) != null && !t2SSRS.GetText (1,1).equals ("") &&
				 * !t2SSRS.GetText (1,1).equals ("null")) { data[i - 1][9] =
				 * t2SSRS.GetText (1,1) ; data[i - 1][14] = t2SSRS.GetText (1,3) ; }
				 * //主险保费 if (t2SSRS.GetText (1,2) != null && !t2SSRS.GetText
				 * (1,2).equals ("") && !t2SSRS.GetText (1,2).equals ("null")) { data[i -
				 * 1][15] = new DecimalFormat ("0.00").format (Double. parseDouble
				 * (t2SSRS.GetText (1,2))) ; }
				 */
			}
			/*
			 * //得到lcpol中附加险的费用,对应收和实收分别计算 if (tFlag || t2SSRS.getMaxRow () == 0) {
			 * t2SSRS = tExeSQL.execSQL ( " select (select distinct riskshortname
			 * from lmrisk where riskcode =a.riskcode)," + " sum(a.SumActuPayMoney) " + "
			 * from ljapayperson a, ljapay b, lcpol d " + " where b.otherno =
			 * a.contno " + " and a.GetNoticeNo = b.GetNoticeNo " + " and
			 * b.OtherNoType = '" + mSaleChnl + "'" + " and b.GetNoticeNo='" +
			 * tSSRS.GetText (k,5) + "'" + " and a.contno = '" + tSSRS.GetText (k,1) + "' " + "
			 * and a.polno = d.polno " + " and a.contno = d.contno " + " and d.polno <>
			 * d.mainpolno " + " Group By a.Riskcode") ; } if (!tFlag ||
			 * t2SSRS.getMaxRow () == 0) { t2SSRS = tExeSQL.execSQL ( " select
			 * (select riskshortname from lmrisk where riskcode =a.riskcode)," + "
			 * sum(a.SumActuPayMoney) " + " from ljspayperson a, ljspay b, lcpol d " + "
			 * where b.otherno = a.contno " + " and a.GetNoticeNo = b.GetNoticeNo " + "
			 * and b.OtherNoType = '" + mSaleChnl + "'" + " and a.contno = '" +
			 * tSSRS.GetText (k,1) + "' " + " and b.GetNoticeNo='" + tSSRS.GetText
			 * (k,5) + "'" + " and a.polno = d.polno " + " and a.contno = d.contno " + "
			 * and d.polno <> d.mainpolno " + " Group By a.Riskcode") ; } if
			 * (t2SSRS.MaxRow != 0) { for (int j = 1 ; j <= t2SSRS.getMaxRow () ;
			 * j++) { //附加险险种名称 if (t2SSRS.GetText (1,1) != null && !t2SSRS.GetText
			 * (1,1).equals ("null")) { data[i - 1][9 + j] = t2SSRS.GetText (j,1) ; }
			 * //保费 if (t2SSRS.GetText (1,2) != null && !t2SSRS.GetText (1,2).equals
			 * ("") && !t2SSRS.GetText (1,2).equals ("null")) { data[i - 1][15 + j] =
			 * new DecimalFormat ("0.00").format (Double. parseDouble
			 * (t2SSRS.GetText (1,2))) ; } } }
			 */
			// 银行帐号
			data[i - 1][19] = tSSRS.GetText(k, 4);
			// 保费合计
			data[i - 1][20] = new DecimalFormat("0.00").format(Double
					.parseDouble(tSSRS.GetText(k, 3)));
			data[i - 1][21] = PubFun.getChnMoney(Double.parseDouble(tSSRS
					.GetText(k, 3)));

			data[i - 1][22] = tSSRS.GetText(k, 6);

			logger.debug(data[i - 1][13] + "处理完毕");
		}
		data[tSSRS.getMaxRow()][0] = "电话类型说明：";
		data[tSSRS.getMaxRow()][1] = "M代表移动电话";
		data[tSSRS.getMaxRow()][2] = "H代表住宅电话";
		data[tSSRS.getMaxRow()][3] = "O代表办公电话";
		data[tSSRS.getMaxRow()][4] = "F代表传真电话";
		data[tSSRS.getMaxRow()][5] = "C代表联系电话";
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
		mGlobalInput.ComCode = "863200";
		mGlobalInput.ManageCom = "863200";
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2007-7-20");
		tTransferData.setNameAndValue("EndDate", "2007-08-09");
		tTransferData.setNameAndValue("ManageCom", "863200");
		tTransferData.setNameAndValue("SaleChnl", "");
		tVData.addElement(mGlobalInput);
		tVData.addElement(tTransferData);
		XQThirdWoodcutter tThirdFilesBL = new XQThirdWoodcutter();
		if (!tThirdFilesBL.submitData(tVData, "OPERATE")) {
			logger.debug("出错了！！！！！！！！！！1");
		}
	}
}
