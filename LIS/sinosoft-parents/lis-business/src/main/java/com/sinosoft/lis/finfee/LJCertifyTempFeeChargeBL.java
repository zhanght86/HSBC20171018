package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 单证保证金日结清单
 * </p>
 * 统计界面：统计机构，统计起期，统计止期
 * 
 * <p>
 * Copyright : Copyright (c) 2002
 * </p>
 * <p>
 * Company :
 * </p>
 * 
 * @author : wl,2006-09-09
 * @version : 1.0
 */

public class LJCertifyTempFeeChargeBL implements PrintService {
private static Logger logger = Logger.getLogger(LJCertifyTempFeeChargeBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private PubFun mPubFun = new PubFun();

	private String mCurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String mCurrentTime = PubFun.getCurrentTime(); // 系统时间
	private String mOperator = "";
	private String mManageCom = "";
	private String mGetNoticeNo = ""; // 收费收据号
	private String mIDNo = ""; // 证件号码

	public LJCertifyTempFeeChargeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("---打印收据---LJCertifyTempFeeChargeBL测试---开始---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 处理业务数据
		if (!dealData()) {
			return false;
		}
		logger.debug("---打印收据---LJCertifyTempFeeChargeBL测试---结束---");

		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mGetNoticeNo = (String) mTransferData.getValueByName("GetNoticeNo"); // 收费收据号
		mIDNo = (String) mTransferData.getValueByName("IDNo"); // 证件号码
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 新建一个XmlExport的实例
		XmlExport xmlexport = new XmlExport();
		// 所用模板名称
		xmlexport.createDocument("LJCertifyTempFeeCharge.vts", "");

		logger.debug("*************************************************");
		logger.debug("*********以下为“ListTable实例”准备数据*************");
		String tSQLF = " select Name,PayMoney,IDType,IDNo,MakeDate,(select username from lduser where usercode = '?mOperator?') from ljcertifytempfee where"
				+ " getnoticeno = '?mGetNoticeNo?' and IDNo = '?mIDNo?' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQLF);
		sqlbv.put("mOperator", mOperator);
		sqlbv.put("mGetNoticeNo", mGetNoticeNo);
		sqlbv.put("mIDNo", mIDNo);

		logger.debug("**************以下执行外层查询语句********************");
		String tIDTypeName = ""; // 证件类型
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRSF = new SSRS();
		tSSRSF = tExeSQL.execSQL(sqlbv);
		String mName = tSSRSF.GetText(1, 1); // 姓名
		String mPayMoney = "0.00"; // 金额
		mPayMoney = tSSRSF.GetText(1, 2); // 金额
		double Money = Double.parseDouble(mPayMoney);
		String mMoney = mPubFun.getChnMoney(Money);
		String mIDType = tSSRSF.GetText(1, 3); // 证件类型
		String mIDNo = tSSRSF.GetText(1, 4); // 证件号码
		String mMakeDate = tSSRSF.GetText(1, 5); // 日期
		String mUserName = tSSRSF.GetText(1, 6);// 收款人姓名
		if (mIDType.equals("0")) {
			tIDTypeName = "身份证"; // 证件类型
		}
		if (mIDType.equals("1")) {
			tIDTypeName = "护照"; // 证件类型
		}
		if (mIDType.equals("2")) {
			tIDTypeName = "军官证"; // 证件类型
		}
		if (mIDType.equals("3")) {
			tIDTypeName = "驾照"; // 证件类型
		}
		if (mIDType.equals("4")) {
			tIDTypeName = "出生证明"; // 证件类型
		}
		if (mIDType.equals("5")) {
			tIDTypeName = "户口薄"; // 证件类型
		}
		if (mIDType.equals("8")) {
			tIDTypeName = "其他"; // 证件类型
		}
		if (mIDType.equals("9")) {
			tIDTypeName = "数据转换证件"; // 证件类型
		}

		if (tSSRSF.getMaxRow() == 0) {
			CError tError = new CError();
			tError.moduleName = "LJCertifyTempFeeChargeBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("*********以下为“TextTag实例”准备数据*************");
		// 新建一个TextTag的实例
		TextTag tTextTag = new TextTag();
		// 为表头赋值

		tTextTag.add("GetNoticeNo", mGetNoticeNo); // 收费收据号:
		tTextTag.add("Name", mName); // 交款人:
		tTextTag.add("IDType", tIDTypeName); // 交款人收据类型:
		tTextTag.add("IDNo", mIDNo); // 交款人证件号码:
		tTextTag.add("PayMoney", mPayMoney); // 金额
		ExeSQL ttExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql("select name from ldcom where comcode = '?mManageCom?'");
		sqlbv1.put("mManageCom", mManageCom);
		SSRS ttSSRS = ttExeSQL
				.execSQL(sqlbv1);
		String tManageComName = ttSSRS.GetText(1, 1);
		tTextTag.add("ManageCom", tManageComName);// 管理机构
		tTextTag.add("MakeDate", mMakeDate); // 日期
		tTextTag.add("MoneyDaXie", mMoney);// 大写
		tTextTag.add("LingKuanRen", mUserName);// 领款人

		logger.debug("**以上为“TextTag实例”准备数据完成，成功！！！******");
		logger.debug("**********************************************");
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			xmlexport.addTextTag(tTextTag);
		}
		logger.debug("---以下 将XmlExport打包，返回前台--");
		mResult.clear();
		mResult.add(xmlexport);
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	// 得到结果集
	public VData getResult() {
		return mResult;
	}

	// 主函数
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("GetNoticeNo", "3600000011252"); // 收费收据号
		tTransferData.setNameAndValue("IDNo", "258"); // 证件号码

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LJCertifyTempFeeChargeBL tLJCertifyTempFeeChargeBL = new LJCertifyTempFeeChargeBL();
		if (tLJCertifyTempFeeChargeBL.submitData(tVData, "") == false) {
			logger.debug("---打印收据---失败----");
		}
		int n = tLJCertifyTempFeeChargeBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content
					+ "原因是: "
					+ tLJCertifyTempFeeChargeBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
