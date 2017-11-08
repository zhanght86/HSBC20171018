package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
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
 * @author GaoHT
 * @version 1.0
 */
public class XQThirdWoodcutterCNN {
private static Logger logger = Logger.getLogger(XQThirdWoodcutterCNN.class);

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

	public XQThirdWoodcutterCNN() {
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

		// ManageCom = tGI.ManageCom;
		return true;
	}

	private boolean PrepareData() {
		return true;
	}

	private boolean dealData() {
		logger.debug("----------------------开始业务处理-----------------------");
		// boolean tFlag = false ;
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SSRS t1SSRS = new SSRS();
		// SSRS t2SSRS = new SSRS () ;
		/*
		 * tSSRS = tExeSQL.execSQL ("select polno,bankdealdate , paymoney , accno
		 * from lyreturnfrombankb where notype = '2' and banksuccflag = '0000' " + "
		 * and comcode like '" + ManageCom + "%' and bankdealdate >= '" + mStartDate + "'
		 * and bankdealdate <= '" + mEndDate + "'") ;
		 */
		String tempSQL = "select polno,bankdealdate , paymoney "
				+ " from lyreturnfrombankb lyr"
				+ " where lyr.notype = '"
				+ "?mSaleChnl?"
				+ "' "
				+ " and lyr.banksuccflag = '0000' "
				+ " and lyr.bankdealdate >= '"
				+ "?mStartDate?"
				+ "' and  lyr.bankdealdate <= '"
				+ "?mEndDate?"
				+ "'"
				// 去掉冲正,回退
				+ " And Not Exists (Select 'X' From Ljapayperson b Where b.getnoticeno = lyr.paycode "
				+ " And Exists (Select 'X' From Ljapayperson a Where a.getnoticeno =b.getnoticeno And a.Sumactupaymoney < 0))"
				// 去掉暂收退费
				+ " And Not Exists (Select 'X' From ljagettempfee Where tempfeeno=lyr.Paycode)";
		// tSSRS = tExeSQL.execSQL("select polno,bankdealdate , paymoney , accno from
		// lyreturnfrombankb where notype = '"+mSaleChnl+"' and banksuccflag = '0000'
		// ");

		if (ManageCom.equals("") || ManageCom.equals(null)
				|| ManageCom.equals("null")) {
			tempSQL = tempSQL + " and lyr.comcode like concat('" + "?ManageCom1?"
					+ "','%')";
		} else {
			tempSQL = tempSQL + " and lyr.comcode like concat('" + "?ManageCom?" + "','%')";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tempSQL);
		sqlbv1.put("mSaleChnl", mSaleChnl);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		sqlbv1.put("ManageCom1", tGI.ManageCom);
		sqlbv1.put("ManageCom", ManageCom);
		tSSRS = tExeSQL.execSQL(sqlbv1);
		data = new String[tSSRS.getMaxRow() + 1][7];
		// int set = 0 ;
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			/*-----------------------准备往生成excel的二维数组--------------------------*/
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("select t.ZipCode ,"
					+ " concat((select placename from ldaddress where placetype = '03' and placecode = t.county),t.PostalAddress) ,"
					+ " n.appntname ,"
					+ " Getappntphone(lc.contno,'') as phone ,"
					+ " n.appntsex"
					+ " from lccont lc, lcaddress t,lcappnt n "
					+ " where n.addressno = t.addressno "
					+ " and n.appntno = lc.appntno "
					+ " and t.customerno = n.appntno "
					+ " and n.contno = lc.contno  "
					+ " and lc.contno = '" + "?contno?" + "' ");
			sqlbv2.put("contno", tSSRS.GetText(i, 1));
			t1SSRS = tExeSQL.execSQL(sqlbv2);

			// 性别
			if (!t1SSRS.GetText(1, 5).equals("")
					&& t1SSRS.GetText(1, 5) != null
					&& !t1SSRS.GetText(1, 5).equals("null")) {
				if (t1SSRS.GetText(1, 5).equals("0")) {
					data[i - 1][0] = "男";
				} else if (t1SSRS.GetText(1, 5).equals("1")) {
					data[i - 1][0] = "女";
				} else {
					data[i - 1][0] = "";
				}
			} else {
				data[i - 1][0] = "";
			}

			// 姓名
			data[i - 1][1] = t1SSRS.GetText(1, 3);
			// 保单号
			data[i - 1][2] = tSSRS.GetText(i, 1);
			FYDate tFYDate = new FYDate(tSSRS.GetText(i, 2));
			// 月
			data[i - 1][3] = tFYDate.getMonth();
			// 日
			data[i - 1][4] = tFYDate.getDateOfMonth();
			// 金额
			// new DecimalFormat("0.00").format(tSSRS.GetText(i,3));

			data[i - 1][5] = new DecimalFormat("0.00").format(Double
					.parseDouble(tSSRS.GetText(i, 3)));
			// 电话
			data[i - 1][6] = t1SSRS.GetText(1, 4);
			/*----------------------准备结束---------------------------*/
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
		mGlobalInput.ComCode = "86320000";
		mGlobalInput.ManageCom = "86320000";
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2005-9-28");
		tTransferData.setNameAndValue("EndDate", "2005-9-28");
		tVData.addElement(mGlobalInput);
		tVData.addElement(tTransferData);
		XQThirdWoodcutterCNN tThirdFilesBL = new XQThirdWoodcutterCNN();
		if (!tThirdFilesBL.submitData(tVData, "OPERATE")) {
			logger.debug("出错了！！！！！！！！！！1");
		}
	}
}
