package com.sinosoft.lis.ebaprt.claim;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *理赔立案结论批单     wood   2007-11-29
 * @author not attributable
 * @version 1.0
 */
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sinosoft.lis.claim.LLPRTPubFunBL;
import com.sinosoft.lis.claimgrp.Stringtools;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LLAffixDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.vschema.LLAffixSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ClaimRgtConclusion {
private static Logger logger = Logger.getLogger(ClaimRgtConclusion.class);

	public CErrors mErrors = new CErrors();

	// 输入数据
	private VData rInputData;

	private String rOperation;

	private GlobalInput rGlobalInput;

	private TransferData rTransferData;

	// 判断是打印索赔资料、
	private String flag;// flag='1'打印全部，flag='0'打印单个人

	private String rgtconclusion;// rgtconclusion='02' 打印理赔决定通知书

	// rgtconclusion='03' 打印理赔单证补充通知书

	private String filename;

	private String RealPath;

	private String RptNo;

	// 输出数据
	private MMap tMap;

	private VData rResultData;

	private Image gif;

	private String InsuredNo;

	public static PdfPCell cell;

	public ClaimRgtConclusion() {

	}

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 接收参数
		rInputData = new VData();
		rInputData = (VData) cInputData.clone();
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// 业务处理
		if (!getInputData())
			return false;

		if (!dealData())
			return false;

		return true;
	}

	/**
	 * 获取外部传入数据和校验必录字段的合法性
	 * 
	 * @param null
	 * @return boolean
	 */

	private boolean getInputData() {
		rTransferData = (TransferData) rInputData.getObjectByObjectName("TransferData", 0);
		if (rTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "ClaimRgtConclusion";
			tError.functionName = "getInputData";
			tError.errorMessage = "无法TransferData数据, 请确认数据不为空！";
			this.mErrors.addOneError(tError);
			logger.debug("打印失败，原因是：无法TransferData数据, 请确认数据不为空！");
			return false;
		}

		filename = (String) rTransferData.getValueByName("filename");
		RptNo = (String) rTransferData.getValueByName("RptNo");
		flag = (String) rTransferData.getValueByName("flag");
		RealPath = (String) rTransferData.getValueByName("RealPath");
		rgtconclusion = (String) rTransferData.getValueByName("rgtconclusion");
		if (flag.equals("0")) {
			InsuredNo = (String) rTransferData.getValueByName("InsuredNo");
		}

		try {
			gif = Image.getInstance(RealPath + "/common/images/minsheng2.GIF");
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "ClaimCertificateRenew";
			tError.functionName = "getInputData";
			tError.errorMessage = "取minsheng.GIF文件失败!";
			this.mErrors.addOneError(tError);
			logger.debug("打印失败，原因是：" + e.getMessage());
			logger.debug("打印失败，原因是：" + e.getStackTrace());
			return false;
		}

		return true;
	}

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		if (rgtconclusion.equals("02")) {
			if (flag.equals("0")) {

			} else if (flag.equals("1")) {
				if (!this.getClaimDecisionAll(filename, RptNo)) {

				}
			}
		} else if (rgtconclusion.equals("03")) {
			if (flag.equals("0")) {
				if (!this.getCertificateRenewByInsureNo(filename, InsuredNo, RptNo)) {
					return false;
				}
			} else if (flag.equals("1")) {
				if (!this.getCertificateRenewAll(filename, RptNo)) {

				}
			}
		}
		return true;
	}

	/**
	 * 根据立案号打印理赔决定通知书
	 * 
	 * @param Stirng
	 * @param String
	 * @return boolean
	 */

	public boolean getClaimDecisionAll(String filename, String RgtNo) {
		ExeSQL tExeSQL = new ExeSQL();
		try {
			logger.debug("filename=" + filename);
			logger.debug("RealPath=" + RealPath);
			logger.debug("RptNo=" + RptNo);
			// 定义字体
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			// BaseFont bf =
			// BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1",
			// BaseFont.IDENTITY_H,BaseFont.EMBEDDED);

			Font Font12 = new Font(bf, 14, Font.NORMAL);
			Font Font10 = new Font(bf, 12, Font.NORMAL);

			// Document document = new Document(PageSize.A4.rotate());
			Document document = new Document(PageSize.A4);

			// 生成的PDF文件文件名
			try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
			} catch (Exception e) {
				logger.debug("writer出错");
				logger.debug(e.getMessage());
			}

			document.setMargins(36, 36, 36, 36);
			document.open();

			String insuredNo = "";
			String mCusNoSQL = "select distinct CustomerNo from LLSubReport where SubRptNo='" + "?RgtNo?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(mCusNoSQL);
			sqlbv1.put("RgtNo", RgtNo);
			SSRS cusNoSSRS = new SSRS();
			cusNoSSRS = tExeSQL.execSQL(sqlbv1);
			for (int i = 1; i <= cusNoSSRS.getMaxRow(); i++) {
				insuredNo = cusNoSSRS.GetText(1, 1);
				gif.setAlignment(Image.RIGHT | Image.UNDERLYING);

				document.add(gif);
				document.add(new Paragraph(Stringtools.createspace(25) + "团险理赔不予立案通知书", Font12));
				document.add(new Paragraph("  ", Font12));
				document.add(new Paragraph("  ", Font12));

				float[] widths = { 20f, 20f };
				PdfPTable table = new PdfPTable(widths);
				String sql2 = "select grpcontno from llreport where rptno='" +"?RptNo?"+ "'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(sql2);
				sqlbv2.put("RptNo", RptNo);
				String GrpContNo = tExeSQL.getOneValue(sqlbv2);
				cell = new PdfPCell(new Phrase("团体保单号：" + GrpContNo, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("团体立案号：" + RptNo, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				table.setWidthPercentage(100);
				document.add(table);

				// 理赔类型---------------------------------------------------------------
				LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
				String ClaimType = tLLPRTPubFunBL.getSLLReportReason(RptNo, insuredNo);

				// 出险原因---------------------------------------------------------------
				String reason = tLLPRTPubFunBL.getLLReport(RptNo).getAccidentReason();
				String tSQL = "select codename from ldcode where code='" + "?reason?"
						+ "' and codetype='lloccurreason'";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tSQL);
				sqlbv3.put("reason", reason);
				String claimRsn = tExeSQL.getOneValue(sqlbv3);

				table = new PdfPTable(widths);
				cell = new PdfPCell(new Phrase("理赔类型：" + ClaimType, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("出险原因：" + claimRsn, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				table.setWidthPercentage(100);
				document.add(table);

				// 出险人姓名--------------------------------------------------------------
				String tSql = "select a.name from ldperson a where " + "a.customerno = '" + "?insuredNo?" + "'";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSql);
				sqlbv4.put("insuredNo", insuredNo);
				String tName = tExeSQL.getOneValue(sqlbv4);

				// 出险时间
				LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
				tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(RptNo);
				String claimTime = tLLAccidentSchema.getAccDate();

				table = new PdfPTable(widths);
				cell = new PdfPCell(new Phrase("出险人：" + tName, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("出险日期：" + claimTime, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				table.setWidthPercentage(100);
				document.add(table);

				String sex = "";
				if (tLLPRTPubFunBL.getCustSex(insuredNo).equals("男")) {
					sex = "先生";
				} else {
					sex = "女士";
				}

				float[] widths1 = { 20f };
				table = new PdfPTable(widths1);

				cell = new PdfPCell(new Phrase("尊敬的  " + tName + "  " + sex, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.setWidthPercentage(100);
				document.add(table);

				table = new PdfPTable(widths1);
				String tmpStr = "        根据保险条款及相关的法律，经审慎核定您权利所属的索赔申请及所提供的证明，" + "本公司确认对您的申请作如下理赔决定：";
				cell = new PdfPCell(new Phrase(tmpStr, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.setWidthPercentage(100);
				document.add(table);
				
				String sql5 = "select endcasedate from llregister where rgtno='"+ "?RptNo?" + "'";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(sql5);
				sqlbv5.put("RptNo", RptNo);
				// 结案日期
				String EndCaseDate = tExeSQL.getOneValue(sqlbv5);

				String sql6 = "select codename from ldcode where codetype='llnorgtreason2' "
						+ " and code=(select norgtreason from llregister where rgtno='" + "?RptNo?"
						+ "')";
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(sql6);
				sqlbv6.put("RptNo", RptNo);
				// 不予立案原因
				String NoRgtReason = tExeSQL.getOneValue(sqlbv6);

				float[] widths2 = { 20f, 30f, 100f };
				table = new PdfPTable(widths2);
				cell = new PdfPCell(new Phrase("理赔结论", Font10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("理赔决定日期", Font10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("不予立案原因", Font10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("不予立案", Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(EndCaseDate.substring(0, 10), Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(NoRgtReason, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.setWidthPercentage(100);
				document.add(table);

				table = new PdfPTable(widths1);
				tmpStr = "\n";
				tmpStr += "        若您对上述决定有异议，可在接到本同通知后向本公司理赔部门寻求解释，服务电话：        感谢您对MS理赔的理解与信任，我们愿不懈努力，竭诚为您继续提供服务。";
				tmpStr += "\n";
				tmpStr += "\n";
				tmpStr += "\n";
				cell = new PdfPCell(new Phrase(tmpStr, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.setWidthPercentage(100);
				document.add(table);

				// 保单服务人员和电话-()------------------------------------------------------
				String tAgentCodeSql = "select AgentCode from lcgrpcont where 1=1 " + " and grpcontno='"
						+ "?GrpContNo?" + "'";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(tAgentCodeSql);
				sqlbv7.put("GrpContNo", GrpContNo);
				String tAgentCode = tExeSQL.getOneValue(sqlbv7);

				String SeveiceName = "";
				String tel = "";
				if (tAgentCode.equals("") || tAgentCode == null) {
					SeveiceName = "";
					tel = "";
				} else {
					LAAgentDB tLAAgentDB = new LAAgentDB();
					tLAAgentDB.setAgentCode(tAgentCode);
					tLAAgentDB.getInfo();
					SeveiceName = tLAAgentDB.getName();
					tel = tLAAgentDB.getPhone();
					if (tel == null) {
						tel = "    ";
					}
				}

				// 经办人-----------------------------------------------------------------
				String operator = "";
				LLSubReportSchema ttLLSubReportSchema = new LLSubReportSchema();
				ttLLSubReportSchema = tLLPRTPubFunBL.getLLSubReport(RptNo, insuredNo);
				operator = ttLLSubReportSchema.getOperator();

				String checkerSQL = "select UserName from llclaimuser " + "where usercode='" + "?operator?" + "'";
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql(checkerSQL);
				sqlbv8.put("operator", operator);
				String jingbanren = tExeSQL.getOneValue(sqlbv8);

				// 通知日期
				String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" + StrTool.getDay() + "日";
				table = new PdfPTable(widths);

				cell = new PdfPCell(new Phrase("保单服务人员：" + tAgentCode + "-" + SeveiceName, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("联系电话：" + tel, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.setWidthPercentage(100);
				document.add(table);

				table = new PdfPTable(widths);
				cell = new PdfPCell(new Phrase("经办人：" + operator + "-" + jingbanren, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("通知日期：" + SysDate, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				table.setWidthPercentage(100);
				document.add(table);

				document.add(new Paragraph("  ", Font12));
				document.add(new Paragraph(
						"      若您对理赔有什么疑问或对理赔服务有什么建议，请与本公司理赔部门联系，如有其他问题请拨打全国统一客户服务电话：95596", Font12));

				document.add(new Paragraph("  ", Font12));
				document.add(new Paragraph("  ", Font12));
				document.add(new Paragraph(Stringtools.createspace(40) + "MSRS保险股份有限公司", Font12));
				document.add(new Paragraph(Stringtools.createspace(40) + "（盖章有效）", Font12));
				document.add(new Paragraph(Stringtools.createspace(40) + " 年  月  日", Font12));
				document.newPage();
			}
			document.close();
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "ClaimSuoPei";
			tError.functionName = "ClaimDanZheng";
			tError.errorMessage = "生成文件失败!";
			this.mErrors.addOneError(tError);
			logger.debug("打印失败，原因是：" + e.getMessage());
			return false;

		}

		return true;
	}

	/**
	 * 根据客户号打印单个人理赔单证补充通知书
	 * 
	 * @param Stirng
	 * @param String *
	 * @param String
	 * @return boolean
	 */

	public boolean getCertificateRenewByInsureNo(String filename, String insuredNo, String RptNo) {

		ExeSQL tExeSQL = new ExeSQL();

		try {
			// 定义字体
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font Font12 = new Font(bf, 14, Font.NORMAL);
			Font Font10 = new Font(bf, 12, Font.NORMAL);
			Document document = new Document(PageSize.A4);
			// 生成的PDF文件文件名
			try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
			} catch (Exception e) {
				logger.debug("writer出错");
				logger.debug(e.getMessage());
			}
			document.setMargins(36, 36, 36, 36);
			document.open();

			// 写logo图片

			gif.setAlignment(Image.RIGHT | Image.UNDERLYING);
			// gif.setAlignment(Image.RIGHT);
			document.add(gif);

			document.add(new Paragraph(Stringtools.createspace(70) + "理赔单证补充通知书", Font12));
			document.add(new Paragraph("  ", Font12));
			document.add(new Paragraph("  ", Font12));
			float[] widths = { 20f, 20f };
			PdfPTable table = new PdfPTable(widths);
			String sql9 = "select grpcontno from llreport where rptno='" + "?RptNo?"+ "'";
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(sql9);
			sqlbv9.put("RptNo", RptNo);
			String GrpContNo = tExeSQL.getOneValue(sqlbv9);
			cell = new PdfPCell(new Phrase("保单号：" + GrpContNo, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("赔案号：" + RptNo, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			table.setWidthPercentage(100);
			document.add(table);

			LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

			// 理赔类型---------------------------------------------------------------
			String ClaimType = tLLPRTPubFunBL.getSLLReportReason(RptNo, insuredNo);
			String sex = "";

			// 出险原因---------------------------------------------------------------
			String reason = tLLPRTPubFunBL.getLLReport(RptNo).getAccidentReason();
			String tSQL = "select codename from ldcode where code='" + "?reason?"
					+ "' and codetype='lloccurreason'";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tSQL);
			sqlbv10.put("reason", reason);
			ExeSQL tExeSQL1 = new ExeSQL();
			String claimRsn = tExeSQL1.getOneValue(sqlbv10);

			table = new PdfPTable(widths);
			cell = new PdfPCell(new Phrase("理赔类型：" + ClaimType, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("出险原因：" + claimRsn, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			table.setWidthPercentage(100);
			document.add(table);

			// 出险人姓名--------------------------------------------------------------
			String tSql = "select a.name from ldperson a where " + "a.customerno = '" + "?insuredNo?" + "'";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tSql);
			sqlbv11.put("insuredNo", insuredNo);
			String tName = tExeSQL.getOneValue(sqlbv11);
			LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
			tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(RptNo);
			// 出险时间---------------------------------------------------------------
			String claimTime = "";
			claimTime = tLLAccidentSchema.getAccDate();

			table = new PdfPTable(widths);
			cell = new PdfPCell(new Phrase("出险人：" + tName, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("出险日期：" + claimTime, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			table.setWidthPercentage(100);
			document.add(table);

			if (tLLPRTPubFunBL.getCustSex(insuredNo).equals("男")) {
				sex = "先生";
			} else {
				sex = "女士";
			}
			float[] widths1 = { 20f };
			table = new PdfPTable(widths1);

			cell = new PdfPCell(new Phrase("尊敬的  " + tName + "  " + sex, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			table.setWidthPercentage(100);
			document.add(table);

			table = new PdfPTable(widths1);
			String tmpStr = "        根据保险条款及相关的法律，经审慎核定您权利所属的索赔申请及所提供的证明，" + "您还需要向我公司提供以下证明：";
			cell = new PdfPCell(new Phrase(tmpStr, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			table.setWidthPercentage(100);
			document.add(table);

			// 理赔补充单证 "select * from Llaffix where AffixConclusion is null"
			LLAffixDB tLLAffixDB = new LLAffixDB();
			String affixSQL = "select * from Llaffix where AffixConclusion is null and rgtno='" + "?RptNo?"
					+ "' and customerno='" + "?insuredNo?" + "'";
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(affixSQL);
			sqlbv12.put("insuredNo", insuredNo);
			sqlbv12.put("RptNo", RptNo);
			LLAffixSet tLLAffixSet = tLLAffixDB.executeQuery(sqlbv12);
			String AffixStr = "";

			for (int i = 1; i <= 25; i++) {

				if (i <= tLLAffixSet.size()) {
					AffixStr += "        " + i + "." + tLLAffixSet.get(i).getAffixName() + "\n";

				} else {
					AffixStr += "\n";
				}

			}
			table = new PdfPTable(widths1);
			cell = new PdfPCell(new Phrase(AffixStr, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			table.setWidthPercentage(100);
			document.add(table);

			// 保单服务人员和电话-()------------------------------------------------------

			String tAgentCodeSql = "select AgentCode from lcgrpcont where 1=1 " + " and grpcontno='"
					+ "?GrpContNo?" + "'";
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tAgentCodeSql);
			sqlbv13.put("GrpContNo", GrpContNo);
			ExeSQL tAgentCodeExeSQL = new ExeSQL();
			String tAgentCode = tAgentCodeExeSQL.getOneValue(sqlbv13);
			String SeveiceName = "";
			String transNo = "";
			String tel = "";
			if (tAgentCode.equals("") || tAgentCode == null) {
				SeveiceName = "";
				transNo = "";
				tel = "";
			} else {
				LAAgentSchema tLAAgentSchema = new LAAgentSchema();
				LAAgentDB tLAAgentDB = new LAAgentDB();
				tLAAgentDB.setAgentCode(tAgentCode);
				tLAAgentDB.getInfo();
				tLAAgentSchema.setSchema(tLAAgentDB.getSchema());
				SeveiceName = tLAAgentSchema.getName();
				transNo = tLAAgentSchema.getAgentCode();
				tel = tLAAgentSchema.getPhone();
				if (tel == null) {
					tel = "    ";
				}
			}

			// 经办人-----------------------------------------------------------------
			String operator = "";
			LLSubReportSchema ttLLSubReportSchema = new LLSubReportSchema();
			ttLLSubReportSchema = tLLPRTPubFunBL.getLLSubReport(RptNo, insuredNo);
			operator = ttLLSubReportSchema.getOperator();
			String checkerSQL = "select UserName from llclaimuser " + "where usercode='" + "?operator?" + "'";
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(checkerSQL);
			sqlbv14.put("operator", operator);
			ExeSQL cExeSQL = new ExeSQL();
			String jingbanren = cExeSQL.getOneValue(sqlbv14);

			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" + StrTool.getDay() + "日";
			table = new PdfPTable(widths);

			cell = new PdfPCell(new Phrase("保单服务人员：" + tAgentCode + "-" + SeveiceName, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("联系电话：" + tel, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			table.setWidthPercentage(100);
			document.add(table);

			table = new PdfPTable(widths);
			cell = new PdfPCell(new Phrase("经办人：" + operator + "-" + jingbanren, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("通知日期：" + SysDate, Font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			table.setWidthPercentage(100);
			document.add(table);

			document.add(new Paragraph("  ", Font12));
			document.add(new Paragraph("      若您对理赔方面有什么疑问或对" + "理赔服务有什么建议，请拨打" + tel + ",如有其他问题请拨打"
					+ "全国统一客户服务电话：95522", Font12));

			document.close();
		} catch (Exception e) {
			// @@错误处理

			CError tError = new CError();
			tError.moduleName = "ClaimSuoPei";
			tError.functionName = "ClaimDanZhengDetail";
			tError.errorMessage = "生成文件失败!";
			this.mErrors.addOneError(tError);
			logger.debug("打印失败，原因是：" + e.getMessage());
			return false;

		}

		return true;
	}

	/**
	 * 根据立案号打印理赔单证补充通知书
	 * 
	 * @param Stirng
	 * @param String
	 * 
	 * @return boolean
	 */

	public boolean getCertificateRenewAll(String filename, String RgtNo) {
		ExeSQL tExeSQL = new ExeSQL();
		try {
			logger.debug("filename=" + filename);
			logger.debug("RealPath=" + RealPath);
			logger.debug("RptNo=" + RptNo);
			// 定义字体
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font Font12 = new Font(bf, 14, Font.NORMAL);
			Font Font10 = new Font(bf, 12, Font.NORMAL);

			// Document document = new Document(PageSize.A4.rotate());
			Document document = new Document(PageSize.A4);
			// 生成的PDF文件文件名
			try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
			} catch (Exception e) {
				logger.debug("writer出错");
				logger.debug(e.getMessage());
			}
			document.setMargins(36, 36, 36, 36);
			document.open();
			logger.debug("aa");

			logger.debug("bb");
			String insuredNo = "";
			String mCusNoSQL = "select distinct CustomerNo from LLSubReport where SubRptNo='" + "?RgtNo?" + "'";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(mCusNoSQL);
			sqlbv15.put("RgtNo", RgtNo);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS cusNoSSRS = new SSRS();
			cusNoSSRS = cusExeSQL.execSQL(sqlbv15);
			for (int i = 1; i <= cusNoSSRS.getMaxRow(); i++) {
				insuredNo = cusNoSSRS.GetText(1, 1);
				gif.setAlignment(Image.RIGHT | Image.UNDERLYING);
				// gif.setAlignment(Image.RIGHT);
				document.add(gif);

				document.add(new Paragraph(Stringtools.createspace(70) + "理赔单证通知书", Font12));
				document.add(new Paragraph("  ", Font12));
				document.add(new Paragraph("  ", Font12));
				float[] widths = { 20f, 20f };
				PdfPTable table = new PdfPTable(widths);
				String sql16 = "select grpcontno from llreport where rptno='" + "?RptNo?"+ "'";
				SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
				sqlbv16.sql(sql16);
				sqlbv16.put("RptNo", RptNo);
				String GrpContNo = tExeSQL.getOneValue(sqlbv16);
				cell = new PdfPCell(new Phrase("保单号：" + GrpContNo, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("赔案号：" + RptNo, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				table.setWidthPercentage(100);
				document.add(table);

				LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

				// 理赔类型---------------------------------------------------------------
				String ClaimType = tLLPRTPubFunBL.getSLLReportReason(RptNo, insuredNo);
				String sex = "";

				// 出险原因---------------------------------------------------------------
				String reason = tLLPRTPubFunBL.getLLReport(RptNo).getAccidentReason();
				String tSQL = "select codename from ldcode where code='" + "?reason?"
						+ "' and codetype='lloccurreason'";
				
				SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				sqlbv17.sql(tSQL);
				sqlbv17.put("reason", reason);
				ExeSQL tExeSQL1 = new ExeSQL();
				String claimRsn = tExeSQL1.getOneValue(sqlbv17);

				table = new PdfPTable(widths);
				cell = new PdfPCell(new Phrase("理赔类型：" + ClaimType, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("出险原因：" + claimRsn, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				table.setWidthPercentage(100);
				document.add(table);

				// 出险人姓名--------------------------------------------------------------
				String tSql = "select a.name from ldperson a where " + "a.customerno = '" + "?insuredNo?" + "'";
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql(tSql);
				sqlbv18.put("insuredNo", insuredNo);
				String tName = tExeSQL.getOneValue(sqlbv18);
				LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
				tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(RptNo);
				// 出险时间---------------------------------------------------------------
				String claimTime = "";
				claimTime = tLLAccidentSchema.getAccDate();

				table = new PdfPTable(widths);
				cell = new PdfPCell(new Phrase("出险人：" + tName, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("出险日期：" + claimTime, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				table.setWidthPercentage(100);
				document.add(table);

				if (tLLPRTPubFunBL.getCustSex(insuredNo).equals("男")) {
					sex = "先生";
				} else {
					sex = "女士";
				}
				float[] widths1 = { 20f };
				table = new PdfPTable(widths1);

				cell = new PdfPCell(new Phrase("尊敬的  " + tName + "  " + sex, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.setWidthPercentage(100);
				document.add(table);

				table = new PdfPTable(widths1);
				String tmpStr = "        根据保险条款及相关的法律，经审慎核定您权利所属的索赔申请及所提供的证明，" + "您还需要向我公司提供以下证明：";
				cell = new PdfPCell(new Phrase(tmpStr, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.setWidthPercentage(100);
				document.add(table);

				// 理赔补充单证 "select * from Llaffix where AffixConclusion is null"
				LLAffixDB tLLAffixDB = new LLAffixDB();
				String affixSQL = "select * from Llaffix where AffixConclusion is null and rgtno='" + "?RptNo?"
						+ "' and customerno='" + "?insuredNo?" + "'";
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(affixSQL);
				sqlbv19.put("RptNo", RptNo);
				sqlbv19.put("insuredNo", insuredNo);
				LLAffixSet tLLAffixSet = tLLAffixDB.executeQuery(sqlbv19);
				String AffixStr = "";

				for (int j = 1; j <= 25; j++) {

					if (j <= tLLAffixSet.size()) {
						AffixStr += "        " + j + "." + tLLAffixSet.get(j).getAffixName() + "\n";

					} else {
						AffixStr += "\n";
					}

				}
				table = new PdfPTable(widths1);
				cell = new PdfPCell(new Phrase(AffixStr, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.setWidthPercentage(100);
				document.add(table);

				// 保单服务人员和电话-()------------------------------------------------------

				String tAgentCodeSql = "select AgentCode from lcgrpcont where 1=1 " + " and grpcontno='"
						+ "?GrpContNo?" + "'";
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				sqlbv20.sql(tAgentCodeSql);
				sqlbv20.put("GrpContNo", GrpContNo);
				ExeSQL tAgentCodeExeSQL = new ExeSQL();
				String tAgentCode = tAgentCodeExeSQL.getOneValue(sqlbv20);
				String SeveiceName = "";
				String transNo = "";
				String tel = "";
				if (tAgentCode.equals("") || tAgentCode == null) {
					SeveiceName = "";
					transNo = "";
					tel = "";
				} else {
					LAAgentSchema tLAAgentSchema = new LAAgentSchema();
					LAAgentDB tLAAgentDB = new LAAgentDB();
					tLAAgentDB.setAgentCode(tAgentCode);
					tLAAgentDB.getInfo();
					tLAAgentSchema.setSchema(tLAAgentDB.getSchema());
					SeveiceName = tLAAgentSchema.getName();
					transNo = tLAAgentSchema.getAgentCode();
					tel = tLAAgentSchema.getPhone();
					if (tel == null) {
						tel = "    ";
					}
				}

				// 经办人-----------------------------------------------------------------
				String operator = "";
				LLSubReportSchema ttLLSubReportSchema = new LLSubReportSchema();
				ttLLSubReportSchema = tLLPRTPubFunBL.getLLSubReport(RptNo, insuredNo);
				operator = ttLLSubReportSchema.getOperator();
				String checkerSQL = "select UserName from llclaimuser " + "where usercode='" + "?operator?" + "'";
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql(checkerSQL);
				sqlbv21.put("operator", operator);
				ExeSQL cExeSQL = new ExeSQL();
				String jingbanren = cExeSQL.getOneValue(sqlbv21);

				String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" + StrTool.getDay() + "日";
				table = new PdfPTable(widths);

				cell = new PdfPCell(new Phrase("保单服务人员：" + tAgentCode + "-" + SeveiceName, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("联系电话：" + tel, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.setWidthPercentage(100);
				document.add(table);

				table = new PdfPTable(widths);
				cell = new PdfPCell(new Phrase("经办人：" + operator + "-" + jingbanren, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("通知日期：" + SysDate, Font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				table.setWidthPercentage(100);
				document.add(table);

				document.add(new Paragraph("  ", Font12));
				document.add(new Paragraph("      若您对理赔方面有什么疑问或对" + "理赔服务有什么建议，请拨打" + tel + ",如有其他问题请拨打"
						+ "全国统一客户服务电话：95522", Font12));
				document.newPage();
			}

			document.close();

		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "ClaimSuoPei";
			tError.functionName = "ClaimDanZheng";
			tError.errorMessage = "生成文件失败!";
			this.mErrors.addOneError(tError);
			logger.debug("打印失败，原因是：" + e.getMessage());
			logger.debug("打印失败，原因是：" + e.getStackTrace());
			return false;

		}

		return true;
	}

}
