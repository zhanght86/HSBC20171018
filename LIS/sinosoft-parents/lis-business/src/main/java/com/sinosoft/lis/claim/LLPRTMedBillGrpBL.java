package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 医疗理赔给付清单[团体] -- PCT016,MedicalListC00070.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhaorx 2006-12-05
 * @version 1.0
 */
public class LLPRTMedBillGrpBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRTMedBillGrpBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private TransferData mTransferData = new TransferData();

	private String mGrpClmNo = ""; // 团体赔案号

	public LLPRTMedBillGrpBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------医疗理赔给付清单[团体]-----LLPRTMedBillGrpBL测试-----开始----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		logger.debug("----------医疗理赔给付清单[团体]-----LLPRTMedBillGrpBL测试-----结束----------");
		return true;
	}

	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpClmNo", "70000000009");
		VData tVData = new VData();
		tVData.add(tTransferData);
		LLPRTMedBillGrpBL tLLPRTMedBillGrpBL = new LLPRTMedBillGrpBL();
		if (!tLLPRTMedBillGrpBL.submitData(tVData, "")) {
			String str = "提交失败，原因是: "
					+ tLLPRTMedBillGrpBL.mErrors.getError(0).errorMessage;
			logger.debug(str);
		}
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mGrpClmNo = (String) mTransferData.getValueByName("GrpClmNo");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
		String[] Title = new String[13];
		ListTable tListTable = new ListTable();
		tListTable.setName("MedBillGrp");
//		tXmlExport.createDocument("MedicalListC00070.vts", "");
		tXmlExport.createDocument("医疗理赔给付清单[团体]", "", "", "0");

		double OriSumAdd = 0; // 总申请金额
		double InDutyAdd = 0; // 总责任内金额
		double ExpDutyAdd = 0; // 总除外金额
		double OtherAdd = 0; // 总第三方支付
		double OutDutyAdd = 0; // 总免赔额
		double RealPayAdd = 0; // 总给付金额
		double SYAdd = 0; // 总剩余有效保额
		// ---------------- 查询 团体赔案下的合同号 -----------------
		String tSQLF = " select rgtobjno from llgrpregister where rgtno='"
				+ mGrpClmNo + "' ";
		ExeSQL fExeSQL = new ExeSQL();
		String tGrpContNo = fExeSQL.getOneValue(tSQLF);
		// ---------------- 查询 团体下的分案号 -----------------
		String tSQLS = " select rgtno from llregister where rgtobjno= '"
				+ mGrpClmNo + "' " + " order by rgtno ";
		ExeSQL sExeSQL = new ExeSQL();
		SSRS sSSRS = new SSRS();
		sSSRS = sExeSQL.execSQL(tSQLS);
		for (int i = 1; i <= sSSRS.getMaxRow(); i++) {
			String tClmNo = sSSRS.GetText(i, 1);// 团体下的分案号
			logger.debug("----------- 第次" + i + "循环，查询赔案" + tClmNo
					+ "信息 开始！------------");

			// ---------------- 查询 序号、被保险人 -----------------
			String tSQLT = " select d.customerseqno,d.name from lcinsured d where 1=1 "
					+ " and exists (select 'X' from llcase c where c.customerno=d.insuredno "
					+ " and  caseno='"
					+ tClmNo
					+ "') and d.grpcontno = '"
					+ tGrpContNo + "' ";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(tSQLT);
			if (tSSRS.getMaxRow() == 0) {
				CError tError = new CError();
				tError.moduleName = "LLPRTMedBillGrpBL";
				tError.functionName = "dealdata";
				tError.errorMessage = "没有查到赔案" + tClmNo + "序号、被保险人信息！";
				mErrors.addOneError(tError);
				return false;
			}
			String tCusNumber = tSSRS.GetText(1, 1);// 序号
			String tName = tSSRS.GetText(1, 2);// 被保险人
			// ---------------- 查询 理赔类型 -----------------
			String tSQLN = " select d.codename from ldcode d where d.codetype='llclaimtype' "
					+ " and exists (select 'X' from llappclaimreason a where "
					+ " d.code = a.reasoncode and a.rgtno ='" + tClmNo + "')";
			ExeSQL nExeSQL = new ExeSQL();
			SSRS nSSRS = new SSRS();
			nSSRS = nExeSQL.execSQL(tSQLN);
			String tTypeName = "";
			String tMTypeName = "";
			if (nSSRS.getMaxRow() == 0) {
				CError tError = new CError();
				tError.moduleName = "LLPRTMedBillGrpBL";
				tError.functionName = "dealdata";
				tError.errorMessage = "没有查到赔案" + tClmNo + "理赔类型信息！";
				mErrors.addOneError(tError);
				return false;
			}
			for (int n = 1; n <= nSSRS.getMaxRow(); n++) {
				tMTypeName = nSSRS.GetText(n, 1);
				tTypeName = tTypeName + tMTypeName;
			}

			// ---------------- 查询 申请总金额、责任内总金额、除外总金额 -----------------
			String tSQLFo = " select sum(nvl(a.OriSum,0)),sum(nvl(a.AdjSum,0)-nvl(a.OutDutyAmnt,0)),"
					+ " sum(nvl(a.OriSum,0)-(nvl(a.AdjSum,0)-nvl(a.OutDutyAmnt,0))) "// ,sum(a.CalSum)
					+ " from LLClaimDutyFee a,llclaimdetail b where 1=1 "
					+ " and a.dutyfeetype in('A','B ') and a.clmno=b.clmno "// A,B是门诊和住院
					+ " and a.polno=b.polno  and a.getdutytype=b.getdutykind "
					+ " and a.dutycode=b.dutycode  and a.getdutycode=b.getdutycode "
					+ " and b.givetype='0' and b.clmno='" + tClmNo + "' ";
			ExeSQL foExeSQL = new ExeSQL();
			SSRS foSSRS = foExeSQL.execSQL(tSQLFo);
			if (foSSRS.getMaxRow() == 0) {
				CError tError = new CError();
				tError.moduleName = "LLPRTMedBillGrpBL";
				tError.functionName = "dealdata";
				tError.errorMessage = "没有查到赔案" + tClmNo
						+ "申请总金额、责任内总金额、除外总金额信息！";
				mErrors.addOneError(tError);
				return false;
			}
			String oriSum = foSSRS.GetText(1, 1);// 申请总金额
			String adjSum = foSSRS.GetText(1, 2);// 责任内总金额
			String expFee = foSSRS.GetText(1, 3);// 除外总金额
			logger.debug(oriSum);
			oriSum = (oriSum == null || oriSum.equals("") || oriSum == "null") ? "0"
					: oriSum;// see ghost
			adjSum = (adjSum == null || adjSum.equals("") || adjSum == "null") ? "0"
					: adjSum;
			expFee = (expFee == null || expFee.equals("") || expFee == "null") ? "0"
					: expFee;
			OriSumAdd = OriSumAdd + Double.parseDouble(oriSum);
			InDutyAdd = InDutyAdd + Double.parseDouble(adjSum);
			ExpDutyAdd = ExpDutyAdd + Double.parseDouble(expFee);

			// ---------------- 查询
			// 第三方支付（B001(社保给付)与B002(第三方给付)和）,给付总金额----------------
			String tSQLFi = " select sum(nvl(SocPay,0)+nvl(OthPay,0)),sum(nvl(RealPay,0))from llclaimdutykind "
					+ " where GetDutyKind in ('100','200') and clmno='"
					+ tClmNo + "' ";
			ExeSQL fiExeSQL = new ExeSQL();
			SSRS fiSSRS = fiExeSQL.execSQL(tSQLFi);
			if (fiSSRS.MaxRow == 0) {
				CError tError = new CError();
				tError.moduleName = "LLPRTMedBillGrpBL";
				tError.functionName = "dealdata";
				tError.errorMessage = "没有查到赔案" + tClmNo + "第三方支付总金额，给付总金额信息！";
				mErrors.addOneError(tError);
				return false;
			}
			String socPay = fiSSRS.GetText(1, 1);// 第三方支付
			String reaPay = fiSSRS.GetText(1, 2);// 给付总金额
			socPay = (socPay == null || socPay.equals("") || socPay == "null") ? "0"
					: socPay;
			reaPay = (reaPay == null || reaPay.equals("") || reaPay == "null") ? "0"
					: reaPay;
			OtherAdd = OtherAdd + Double.parseDouble(socPay);
			RealPayAdd = RealPayAdd + Double.parseDouble(reaPay);

			// ---------------- 查询 免赔额，赔付比例 ----------------
			String tSQLSi = " select sum(nvl(outdutyamnt,0)),"
					+ " case (sum(nvl(adjsum,0))-sum(nvl(outdutyamnt,0)))*100 when 0 then 100 "
					+ " else Round(sum(nvl(calsum,0))/(sum(nvl(adjsum,0))-sum(nvl(outdutyamnt,0)))*100,2) end "
					+ " from llclaimdutyfee where getdutytype in ('100','200') "
					+ " and clmno='" + tClmNo + "'";
			ExeSQL siExeSQL = new ExeSQL();
			SSRS siSSRS = siExeSQL.execSQL(tSQLSi);
			if (siSSRS.MaxRow == 0) {
				CError tError = new CError();
				tError.moduleName = "LLPRTMedBillGrpBL";
				tError.functionName = "dealdata";
				tError.errorMessage = "没有查到赔案" + tClmNo + "免赔额，赔付比例信息！";
				mErrors.addOneError(tError);
				return false;
			}
			String tOutDutyAmnt = siSSRS.GetText(1, 1);// 免赔额
			String tOutDutyRate = siSSRS.GetText(1, 2);// 赔付比例
			tOutDutyAmnt = (tOutDutyAmnt == null || tOutDutyAmnt.equals("") || tOutDutyAmnt == "null") ? "0"
					: tOutDutyAmnt;
			OutDutyAdd = OutDutyAdd + Double.parseDouble(tOutDutyAmnt);

			// ---------------- 查询 剩余有效保额 -----------------
			String tSQLSe = " select sum(nvl(standmoney,0))-sum(nvl(summoney,0)) from lcget c "
					+ " where c.grpcontno = '"
					+ tGrpContNo
					+ "' "
					+ " and exists (select 'X' from llcase e where e.caseno='"
					+ tClmNo
					+ "' "
					+ " and c.insuredno=e.customerno) "
					+ " and exists (select 'Y' from lmdutygetclm m where m.getdutykind in ('100','200') "
					+ " and m.getdutycode = c.getdutycode ) "
					+ " and not exists (select 'Z' from llcontstate s where s.clmno ='"
					+ tClmNo + "'" + " and s.polno=c.polno ) ";
			ExeSQL seExeSQL = new ExeSQL();
			String tFee = seExeSQL.getOneValue(tSQLSe);
			tFee = (tFee == null || tFee.equals("") || tFee == "null") ? "0"
					: tFee;
			SYAdd = SYAdd + Double.parseDouble(tFee);

			// ----------------查询 除外原因 -----------------
			String tSQLE = " select AdjReMark from llclaimdutyfee where clmno='"
					+ tClmNo + "' ";
			ExeSQL eExeSQL = new ExeSQL();
			SSRS eSSRS = eExeSQL.execSQL(tSQLE);
			if (eSSRS.MaxRow == 0) {
				CError tError = new CError();
				tError.moduleName = "LLPRTMedBillGrpBL";
				tError.functionName = "dealdata";
				tError.errorMessage = "没有查到赔案" + tClmNo + "除外原因信息！";
				mErrors.addOneError(tError);
				return false;
			}
			String tAdjReason = "";
			String tMAdjReason = "";
			for (int e = 1; e <= eSSRS.getMaxRow(); e++) {
				tMAdjReason = eSSRS.GetText(e, 1);
				tAdjReason = tAdjReason + tMAdjReason;
			}
			tAdjReason = tAdjReason.trim().equals("") ? "无" : tAdjReason;

			String[] tArray = new String[13];
			tArray[0] = tCusNumber;
			tArray[1] = tClmNo;
			tArray[2] = tName;
			tArray[3] = tTypeName;
			tArray[4] = oriSum;
			tArray[5] = adjSum;
			tArray[6] = expFee;
			tArray[7] = socPay;
			tArray[8] = tOutDutyAmnt;
			tArray[9] = tOutDutyRate + "%";
			tArray[10] = reaPay;
			tArray[11] = tFee;
			tArray[12] = tAdjReason;
			tListTable.add(tArray);

			logger.debug("----------- 第次" + i + "循环，查询赔案" + tClmNo
					+ "信息 结束！------------");
		}
		tXmlExport.addListTable(tListTable, Title);
		// ----------------查询 结案日期 -----------------
		String tSQLEndDate = " select max(endcasedate) from llregister "
				+ " where rgtobjno ='" + mGrpClmNo + "' ";
		ExeSQL dateExeSQL = new ExeSQL();
		SSRS dateSSRS = new SSRS();
		dateSSRS = dateExeSQL.execSQL(tSQLEndDate);
		if (dateSSRS.MaxRow == 0) {
			CError tError = new CError();
			tError.moduleName = "LLPRTMedBillGrpBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "查询结案日期失败！";
			mErrors.addOneError(tError);
			return false;
		}
		String tEndDate = dateSSRS.GetText(1, 1);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入单个字段的打印变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tTextTag.add("GrpClmNo", mGrpClmNo); // 团体赔案号
		tTextTag.add("GrpContNo", tGrpContNo); // 团体保单号
		tTextTag.add("GrpEndDate", tEndDate); // 结案日期
		tTextTag.add("OriSum", OriSumAdd); // 总申请金额
		tTextTag.add("InDutySum", InDutyAdd); // 总责任内金额
		tTextTag.add("ExpDutySum", ExpDutyAdd); // 总除外金额
		tTextTag.add("OtherSum", OtherAdd); // 总第三方支付
		tTextTag.add("OutDutySum", OutDutyAdd); // 总免赔额
		tTextTag.add("RealPaySum", RealPayAdd); // 总给付金额
		tTextTag.add("SYSum", SYAdd); // 总剩余有效保额

		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;

	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

}
