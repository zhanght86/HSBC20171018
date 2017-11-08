package com.sinosoft.lis.ebaprt.claim;

import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
/*
 程序功能：
 理赔批单打印=====>无模板====>入参：文件名，赔案号，管理机构
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
import com.sinosoft.lis.claimgrp.Stringtools;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;

public class CreateClaim {
	private static Logger logger = Logger.getLogger(CreateClaim.class);
	public static PdfPCell cell;

	public CreateClaim() {
	}

	// 1、打印理赔批单(文件名，赔案号，管理机构)
	public String CreateClaimBatch(String filename, String RgtNo,
			String ManageCom) {
		String state = "";
		logger.debug("理赔批单打印：" + filename + "|" + RgtNo + "|" + ManageCom);
		Document document = new Document(PageSize.A4);
		try {
			// 定义字体
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			// BaseFont bf =
			// BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1",
			// BaseFont.IDENTITY_H,
			// BaseFont.EMBEDDED);
			Font FontChinese = new Font(bf, 12, Font.NORMAL);

			// 生成的PDF文件文件名
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(filename));

			// 页边距的定义:左 右 上 下
			document.setMargins(36, 36, 36, 36);
			document.open();
			document.add(new Paragraph("   ", FontChinese));
			document.add(new Paragraph("   ", FontChinese));
			document.add(new Paragraph("   ", FontChinese));
			document.add(new Paragraph("   ", FontChinese));
			document.add(new Paragraph("   ", FontChinese));

			ExeSQL tExeSQL = new ExeSQL();
			// 团单号
			String sql1 = "select GrpContNo from llregister where rgtno='"
					+ "?RgtNo?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql1);
			sqlbv1.put("RgtNo", RgtNo);
			String GrpContNo = tExeSQL.getOneValue(sqlbv1);

			// 取一个被保险人姓名
			String sql2 = "select Name from lcinsured where GrpContNo='"
					+ "?GrpContNo?"
					+ "' and InsuredNO in (select InsuredNO from LLClaimpolicy where  ClmNo='"
					+ "?RgtNo?"
					+ "' and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql2);
			sqlbv2.put("GrpContNo", GrpContNo);
			sqlbv2.put("RgtNo", RgtNo);
			String InsuredName = tExeSQL.getOneValue(sqlbv2);

			// 人数
			String sql3 = "select count(*) from (select distinct InsuredNO from LLClaimpolicy where  ClmNo='"
					+ "?RgtNo?"
					+ "' and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2')) t";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sql3);
			sqlbv3.put("RgtNo", RgtNo);
			String CountInsured = tExeSQL.getOneValue(sqlbv3);

			// 全部保险责任形成一个串
			String Duty = "";
			String SqlDuty = "select distinct trim(b.CodeName)  GetDutyName   "
					+ " from LLClaimDetail a,ldcode b  "
					+ "  where a.RealPay>0 and b.codetype='getdutykind' "
					+ "  and a.GetDutyKind=b.code    and a.RgtNo='"
					+ "?RgtNo?"
					+ "'  and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2')";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(SqlDuty);
			sqlbv4.put("RgtNo", RgtNo);
			SSRS tSSRS = tExeSQL.execSQL(sqlbv4);
			if (tSSRS == null) {
				state = "2--打印理赔批单时，数据库连接失败!";
				return state;
			}
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				String tmp = tSSRS.GetText(i, 1);
				if (tmp == null) {
					tmp = "";
				}
				if (i == 1) {
					Duty = Duty + "" + tmp.trim();
				} else {
					Duty = Duty + "、" + tmp.trim();
				}
			}

			// 判断是否有死亡
			String DeadStr = "";
			if (Duty.indexOf("死亡") >= 0 || Duty.indexOf("身故") >= 0) {
				DeadStr = "，以上被保险人责任终止。";
			} else {
				DeadStr = "。";
			}

			// 全部保单号形成一个串
			String StrGrpContNO = "";
			String SqlGrpContNO = "select distinct grpcontno from lcpol  where PolNo in (select distinct polno from llclaimpolicy  where  RgtNo='"
					+ "?RgtNo?"
					+ "'  and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(SqlGrpContNO);
			sqlbv5.put("RgtNo", RgtNo);
			tSSRS = tExeSQL.execSQL(sqlbv5);
			if (tSSRS == null) {
				state = "2--打印理赔批单时，数据库连接失败!";
				return state;
			}
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				String tmp = tSSRS.GetText(i, 1); // .getString(1);
				if (tmp == null) {
					tmp = "";
				}
				if (i == 1) {
					StrGrpContNO = StrGrpContNO + "" + tmp.trim();
				} else {
					StrGrpContNO = StrGrpContNO + "、" + tmp.trim();
				}
			}

			// 计算总共的赔付金额
			String sql6 = "select sum(RealPay) from LLClaim where RgtNo='"
					+ "?RgtNo?" + "'   and (GiveType='0' or GiveType='4')";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(sql6);
			sqlbv6.put("RgtNo", RgtNo);
			String RealPay = tExeSQL.getOneValue(sqlbv6);
			RealPay = "\u00A5" + Stringtools.stringtostring(RealPay, 2);

			// 主管及经办：
			// String ClmUWer = my
			// .getOneValue("select b.UserName from LLClaim a,lduser b where
			// a.ClmUWer=b.UserCode and a.CaseNo='"
			// + RgtNo + "' and (a.GiveType='0' or a.GiveType='4')");
			// String Operator = my
			// .getOneValue("select b.UserName from LLRegister a,LDUser b where
			// a.Operator=b.UserCode and a.RgtNo='"
			// + RgtNo + "'");

			// 批改日期
			String sql7 = "select to_char(EndCaseDate,'yyyy-mm-dd') from LLRegister where RgtNo='"
					+ "?RgtNo?" + "' ";
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(sql7);
			sqlbv7.put("RgtNo", RgtNo);
			String EndCaseDate = tExeSQL.getOneValue(sqlbv7);

			// 数据准备结束，开始写批单的内容
			document.add(new Paragraph("团体立案号：" + RgtNo, FontChinese));

			int tmpcount = Stringtools.stringtoint(CountInsured);
			if (tmpcount == 1) {
				document.add(new Paragraph("被保险人：  " + InsuredName.trim(),
						FontChinese));
			} else if (tmpcount > 1) {
				document.add(new Paragraph("被保险人：  " + InsuredName.trim()
						+ " 等 " + CountInsured + " 人", FontChinese));
			}

			document.add(new Paragraph("批改日期：" + EndCaseDate, FontChinese));
			document.add(new Paragraph(" ", FontChinese));

			document.add(new Paragraph(Stringtools.createspace(9)
					+ "以上被保险人于保险有效期内，因 " + Duty + " 所引起的保险事故，经本公司审核属于保险责任，按 "
					+ StrGrpContNO + " 保险合同规定给付理赔保险金 " + RealPay + " 元"
					+ DeadStr, FontChinese));

			// GiveType
			/**
			 * String GiveType = tExeSQL.getOneValue("select givetype from
			 * llclaimpolicy where clmno='" + RgtNo + "' and rownum<=1"); if
			 * (GiveType.equals("0")) { document.add(new
			 * Paragraph(Stringtools.createspace(9) + "以上被保险人于保险有效期内，因 " + Duty
			 * + " 所引起的保险事故，经本公司审核属于保险责任，按 " + StrGrpContNO + " 保险合同规定给付理赔保险金 "
			 * + RealPay + " 元" + DeadStr, FontChinese)); } else if
			 * (GiveType.equals("2")) { document.add(new
			 * Paragraph(Stringtools.createspace(9) +
			 * "以上被保险人提出的索赔申请，经本公司审核不属于保险责任，考虑到该案件的实际情况符合我公司的通融原因，" +
			 * "我公司同意通融赔付保险金 " + RealPay + " 元" + DeadStr, FontChinese)); } else
			 * if (GiveType.equals("3")) { document.add(new
			 * Paragraph(Stringtools.createspace(9) +
			 * "以上被保险人提出的索赔申请，经本公司审核不属于保险责任，考虑到该案件的实际情况,经与受益人协商，" +
			 * "我公司同意协议赔付保险金 " + RealPay + " 元" + DeadStr, FontChinese)); }
			 */

			document.add(new Paragraph("   ", FontChinese));
			document.add(new Paragraph("   ", FontChinese));
			document.add(new Paragraph("   ", FontChinese));
			document.add(new Paragraph(Stringtools.createspace(90) + "特此批改",
					FontChinese));

			document.add(new Paragraph("   ", FontChinese));
			document.add(new Paragraph("   ", FontChinese));
			// document.add(new Paragraph(Stringtools.createspace(40) + "主管：" +
			// ClmUWer
			// + Stringtools.createspace(30) + "经办：" + Operator, FontChinese));

			document.close(); // 写PDF文件结束
		} catch (Exception ex) {
			Stringtools.log("", "CreateClaimBatch.java===>" + ex.getMessage(),
					"");
			state = "业务程序处理出错！" + ex.getMessage();
		}
		return state;
	}

	// 2、打印理赔支付清单(文件名,赔案号,管理机构)
	public static String CreateOutBill(String filename, String RgtNo,
			String ManageCom) {
		String state = "";
		try {
			// 定义字体
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			// BaseFont bf =
			// BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1",
			// BaseFont.IDENTITY_H,
			// BaseFont.EMBEDDED);
			Font Font12 = new Font(bf, 12, Font.NORMAL);
			Font Font10 = new Font(bf, 10, Font.NORMAL);
			// Document document = new Document(PageSize.A4.rotate());
			Document document = new Document(PageSize.A4);

			// 生成的PDF文件文件名
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(filename));

			// 页边距的定义:左 右 上 下
			document.setMargins(36, 36, 36, 36);
			document.open();
			document.add(new Paragraph("   ", Font12));

			ExeSQL tExeSQL = new ExeSQL();
			document.add(new Paragraph(Stringtools.createspace(35)
					+ "理 赔 支 付 清 单", Font12));
			document.add(new Paragraph(" ", Font12));

			// 取投保单位
			String sql8 = "";
			if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
				sql8 = "select GrpName from LLRegister where RgtNo='"
						+ "?RgtNo?" + "' and rownum<=1";
			} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
				sql8 = "select GrpName from LLRegister where RgtNo='"
						+ "?RgtNo?" + "' limit 1";
			}
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(sql8);
			sqlbv8.put("RgtNo", RgtNo);
			String CustomerName = tExeSQL.getOneValue(sqlbv8);

			// 取保单号
			String sql9 = "";
			if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
				sql9 = "select GrpContNo from LLRegister where RgtNo='"
						+ "?RgtNo?" + "'  and rownum<=1";
			} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
				sql9 = "select GrpContNo from LLRegister where RgtNo='"
						+ "?RgtNo?" + "'  limit 1";
			}

			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(sql9);
			sqlbv9.put("RgtNo", RgtNo);
			String GrpcontNo = tExeSQL.getOneValue(sqlbv9);
			document.add(new Paragraph("投保单位： " + CustomerName, Font12));
			document.add(new Paragraph("团体保单号： " + GrpcontNo, Font12));
			document.add(new Paragraph("团体立案号： " + RgtNo, Font12));
			document.add(new Paragraph("    ", Font12));

			// 初始化表格
			float[] widths = { 10f, 8f, 13f, 12f, 8f, 13f, 14f, 16f };
			PdfPTable table = new PdfPTable(widths);

			// 开始写表头
			cell = new PdfPCell(new Phrase("个人客户号", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("姓名", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("身份证号", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("本次赔付金额", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("收款人", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("收款银行", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("银行账户", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("实付号", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			// 个人客户号
			String InsuredNo = "";
			// 姓名
			String InsuredName = "";
			// 身份证号
			String IDNo = "";

			// 合计金额
			double dRealPay = 0.00;
			// 本次赔付
			String RealPay = "";
			// 收款人
			String AccName = "";
			// 收款银行
			String BankName = "";
			// 银行帐户
			String BankAccNo = "";
			// 实付号
			String actugetno = "";

			// 数据准备SQL文
			String Sql = "select CustomerNo,sum(realpay) from LLClaimDetail where "
					+ " GiveType='0'" // 不是拒赔
					+ " and clmno in (select RgtNo from  LLRegister where EndCaseDate is not null "
					+ " and EndCaseFlag='1' and RgtNo='"
					+ "?RgtNo?"
					+ "') and ClmNo='"
					+ "?RgtNo?"
					+ "'"
					+ " group by CustomerNo";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(Sql);
			sqlbv10.put("RgtNo", RgtNo);
			SSRS tSSRS = tExeSQL.execSQL(sqlbv10);
			if (tSSRS == null) {
				state = "打印赔款清单的数据库连接时出错,数据库连接失败!";
				return state;
			}

			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				InsuredNo = tSSRS.GetText(i, 1);
				if (InsuredNo == null) {
					state = "打印理赔支付清单时，被保险人客号为空值!赔案号:" + RgtNo;
					return state;
				}
				String sql11 = "select trim(Name) from LCInsured where GrpContNo='"
						+ "?GrpcontNo?"
						+ "' and  Insuredno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
				sqlbv11.sql(sql11);
				sqlbv11.put("GrpcontNo", GrpcontNo);
				sqlbv11.put("InsuredNo", InsuredNo);
				InsuredName = tExeSQL.getOneValue(sqlbv11);

				String sql12 = "select trim(IDNO) from LDPerson where CustomerNo='"
						+ "?InsuredNo?" + "'";
				SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
				sqlbv12.sql(sql12);
				sqlbv12.put("InsuredNo", InsuredNo);
				IDNo = tExeSQL.getOneValue(sqlbv12);

				Sql = " select a.sumgetmoney, a.drawer, "
						+ " (case when (select codename from ldcode where codetype='bank' and code=a.bankcode) is not null then (select codename from ldcode where codetype='bank' and code=a.bankcode) else '' end), "
						+ " (case when a.bankaccno is not null then a.bankaccno else '' end), a.actugetno "
						+ " from ljaget a where a.othernotype = '5' and a.otherno = '"
						+ "?RgtNo?" + "' " + " and a.insuredno = '"
						+ "?InsuredNo?" + "' ";
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
				sqlbv13.sql(Sql);
				sqlbv13.put("RgtNo", RgtNo);
				sqlbv13.put("InsuredNo", InsuredNo);
				tSSRS = tExeSQL.execSQL(sqlbv13);
				if (tSSRS == null) {
					state = "查询实付信息出错!";
					return state;
				}
				for (int j = 1; j <= tSSRS.getMaxRow(); j++) {
					RealPay = tSSRS.GetText(j, 1);
					AccName = tSSRS.GetText(j, 2);
					BankName = tSSRS.GetText(j, 3);
					BankAccNo = tSSRS.GetText(j, 4);
					actugetno = tSSRS.GetText(j, 5);

					cell = new PdfPCell(new Phrase(InsuredNo, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(InsuredName, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(IDNo, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(RealPay, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
					dRealPay = dRealPay + Stringtools.stringtodouble(RealPay);

					cell = new PdfPCell(new Phrase(AccName, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(BankName, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(BankAccNo, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(actugetno, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}
			} // 循环结束

			table.setWidthPercentage(105);
			document.add(table);

			document.add(new Paragraph("本次理赔金额合计:"
					+ Stringtools.stringtostring(
							Stringtools.doubletostring(dRealPay), 2) + "元",
					Font12));
			document.add(new Paragraph("  ", Font12));
			document.add(new Paragraph("  ", Font12));

			document.add(new Paragraph(Stringtools.createspace(140)
					+ "MSRS保险股份有限公司", Font12));
			document.add(new Paragraph(Stringtools.createspace(140)
					+ Stringtools.longtoday(), Font12));
			document.close();
		} catch (Exception e) {
			state = "打印理赔支付清单出错！" + e.getMessage();
			return state;
		}

		return state;
	}

	// 3、打印理赔赔款清单(文件名，赔案号，管理机构)
	public String CreatePayBill(String filename, String RgtNo, String ManageCom) {
		String state = "";
		Document document = new Document(PageSize.A4);
		try {
			// 定义字体
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			// BaseFont bf =
			// BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1",
			// BaseFont.IDENTITY_H,
			// BaseFont.EMBEDDED);
			Font Font12 = new Font(bf, 12, Font.NORMAL);
			Font Font10 = new Font(bf, 10, Font.NORMAL);

			// 生成的PDF文件文件名
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(filename));

			// 页边距的定义:左 右 上 下
			document.setMargins(36, 36, 36, 36);
			document.open();
			document.add(new Paragraph("   ", Font12));

			document.add(new Paragraph(Stringtools.createspace(35)
					+ "理 赔 赔 款 清 单", Font12));
			document.add(new Paragraph(" ", Font12));
			ExeSQL tExeSQL = new ExeSQL();

			// 取投保单位
			String sql14 = "select GrpName from LLRegister where RgtNo='"
					+ "?RgtNo?"
					+ "'  and EndCaseFlag='1' and RgtNo not in (select RgtNo from LLClaimpolicy where RgtNo='"
					+ "?RgtNo?" + "'  and GiveType ='1')";
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(sql14);
			sqlbv14.put("RgtNo", RgtNo);
			String CustomerName = tExeSQL.getOneValue(sqlbv14);
			logger.debug("CustomerName=" + CustomerName);

			// 取保单号
			String sql15 = "select GrpContNo from LLRegister where RgtNo='"
					+ "?RgtNo?"
					+ "'  and EndCaseFlag='1' and RgtNo not in (select RgtNo from LLClaimpolicy where RgtNo='"
					+ "?RgtNo?" + "'  and GiveType ='1')";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(sql15);
			sqlbv15.put("RgtNo", RgtNo);
			String GrpcontNo = tExeSQL.getOneValue(sqlbv15);
			logger.debug("GrpcontNo=" + GrpcontNo);

			document.add(new Paragraph("投保单位： " + CustomerName, Font12));
			document.add(new Paragraph("团体保单号： " + GrpcontNo, Font12));
			document.add(new Paragraph("团体立案号： " + RgtNo, Font12));
			document.add(new Paragraph("    ", Font12));

			// 初始化表格
			float[] widths = { 10f, 10f, 10f, 10f, 10f, 10f };
			PdfPTable table = new PdfPTable(widths);
			// 开始写表头
			cell = new PdfPCell(new Phrase("个人客户号", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("姓名", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("险种", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("保险金额", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("本次赔付金额", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("累计赔付金额", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			try {
				// 数据准备SQL文
				String Sql = "select CustomerNo,RiskCode,sum(amnt),sum(realpay) from LLClaimDetail "
						+ " where GiveType='0' " // 不是拒赔
						+ " and 1=1 "
						+ " and ClmNo='"
						+ "?RgtNo?"
						+ "'"
						+ " group by CustomerNo,RiskCode ";
				SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
				sqlbv16.sql(Sql);
				sqlbv16.put("RgtNo", RgtNo);
				SSRS tSSRS = tExeSQL.execSQL(sqlbv16);
				if (tSSRS == null) {
					state = "打印赔款清单的数据库连接时出错,数据库连接失败!";
					return state;
				}
				// 个人客户号
				String InsuredNo = "";
				// 姓名
				String InsuredName = "";
				// 保额
				String Amnt = "";
				// 本次赔付
				double dRealPay = 0.00;
				String RealPay = "";
				// 累计赔付金额
				String ljPay = "";
				// 险种
				String RiskCode = "";

				for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
					InsuredNo = tSSRS.GetText(tmpi, 1);
					if (InsuredNo == null) {
						state = "打印赔清单时，被保险人客号为空值!赔案号" + RgtNo;
						return state;
					}

					String sql17 = "select name from LCInsured where grpcontno='"
							+ "?GrpcontNo?"
							+ "' and InsuredNO='"
							+ "?InsuredNo?" + "'";
					SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
					sqlbv17.sql(Sql);
					sqlbv17.put("GrpcontNo", GrpcontNo);
					sqlbv17.put("InsuredNo", InsuredNo);
					InsuredName = tExeSQL.getOneValue(sqlbv17);
					if (InsuredName == null) {
						state = "打印赔清单时，被保险人姓名为空值!赔案号" + RgtNo + "客户号"
								+ InsuredNo;
						return state;
					}

					RiskCode = tSSRS.GetText(tmpi, 2);
					String sql18 = "select amnt from lcpol where GrpcontNo='"
							+ "?GrpcontNo?" + "' and riskcode='" + "?RiskCode?"
							+ "' and InsuredNo='" + "?InsuredNo?" + "'";
					SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
					sqlbv18.sql(sql18);
					sqlbv18.put("GrpcontNo", GrpcontNo);
					sqlbv18.put("RiskCode", RiskCode);
					sqlbv18.put("InsuredNo", InsuredNo);
					Amnt = tExeSQL.getOneValue(sqlbv18);
					RealPay = Stringtools.stringtostring(
							tSSRS.GetText(tmpi, 4), 2);

					// 客户号
					cell = new PdfPCell(new Phrase(InsuredNo, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					// 姓名
					cell = new PdfPCell(new Phrase(InsuredName, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					// 险种
					cell = new PdfPCell(new Phrase(RiskCode, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					// 保额
					cell = new PdfPCell(new Phrase(Amnt, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);

					// 本次赔付
					cell = new PdfPCell(new Phrase(RealPay, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
					dRealPay = dRealPay + Stringtools.stringtodouble(RealPay);

					// 累计赔付金额
					String tmpsql = "select sum(realpay) from LLClaimDetail where GRpContno='"
							+ "?GrpcontNo?"
							+ "' and CustomerNo='"
							+ "?InsuredNo?"
							+ "'  and RiskCode='"
							+ "?RiskCode?"
							+ "'"
							+ "   and RgtNo in (select RgtNo from  LLRegister "
							+ " where EndCaseFlag='1' and clmstate!='80' and EndCaseDate is not null  )";
					SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
					sqlbv19.sql(tmpsql);
					sqlbv19.put("GrpcontNo", GrpcontNo);
					sqlbv19.put("InsuredNo", InsuredNo);
					sqlbv19.put("RiskCode", RiskCode);
					ljPay = tExeSQL.getOneValue(sqlbv19);
					ljPay = Stringtools.stringtostring(ljPay, 2);
					cell = new PdfPCell(new Phrase(ljPay, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
				} // 循环结束

				table.setWidthPercentage(100);
				document.add(table);

				document.add(new Paragraph("本次理赔金额合计:"
						+ Stringtools.stringtostring(
								Stringtools.doubletostring(dRealPay), 2) + "元",
						Font12));
				document.add(new Paragraph("  ", Font12));
				document.add(new Paragraph("  ", Font12));

				document.add(new Paragraph(Stringtools.createspace(140)
						+ "MSRS保险股份有限公司", Font12));
				document.add(new Paragraph(Stringtools.createspace(140)
						+ Stringtools.longtoday(), Font12));
			} catch (Exception e) {
				state = "打印赔款清单的数据库连接时出错！" + e.getMessage();
				Stringtools.log("", state, "");
				return state;
			}

			document.add(new Paragraph("  ", Font12));
			document.add(new Paragraph("  ", Font12));
			document.add(new Paragraph("  ", Font12));
			document.add(new Paragraph(Stringtools.createspace(100)
					+ "TKRS保险股份有限公司", Font12));
			document.add(new Paragraph(Stringtools.createspace(120)
					+ Stringtools.longtoday(), Font12));

			document.close();
		} catch (Exception e) {
			state = "打印理赔赔款清单出错！" + e.getMessage();
			Stringtools.log("", state, "");
			return state;
		}
		return state;
	}

	// 4、打印理赔给付通知(文件名，赔案号，管理机构)
	public String CreatePayNotice(String filename, String RgtNo,
			String InsuredNo, String ManageCom) {
		String state = "";
		Document document = new Document(PageSize.A4);
		try {
			// 定义字体
			// BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
			// BaseFont.NOT_EMBEDDED);
			BaseFont bf = BaseFont.createFont(
					"c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			Font Font12 = new Font(bf, 12, Font.NORMAL);
			Font Font10 = new Font(bf, 10, Font.NORMAL);

			// 生成的PDF文件文件名
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(filename));

			// 页边距的定义:左 右 上 下
			document.setMargins(36, 36, 36, 36);
			document.open();
			document.add(new Paragraph("   ", Font12));

			document.add(new Paragraph(Stringtools.createspace(35)
					+ "理 赔 给 付 通 知", Font12));
			document.add(new Paragraph("   ", Font12));
			ExeSQL tExeSQL = new ExeSQL();

			// 取投保单位
			String sql20 = "select GrpName from LLRegister where RgtNo='"
					+ "?RgtNo?"
					+ "'  and EndCaseFlag='1' and RgtNo not in (select RgtNo from LLClaimpolicy where RgtNo='"
					+ "?RgtNo?" + "'  and GiveType ='1')";
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(sql20);
			sqlbv20.put("RgtNo", RgtNo);
			String CustomerName = tExeSQL.getOneValue(sqlbv20);
			logger.debug("CustomerName=" + CustomerName);

			// 取保单号
			String sql21 = "select GrpContNo from LLRegister where RgtNo='"
					+ "?RgtNo?"
					+ "'  and EndCaseFlag='1' and RgtNo not in (select RgtNo from LLClaimpolicy where RgtNo='"
					+ "?RgtNo?" + "'  and GiveType ='1')";
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.sql(sql21);
			sqlbv21.put("RgtNo", RgtNo);
			String GrpcontNo = tExeSQL.getOneValue(sqlbv21);
			logger.debug("GrpcontNo=" + GrpcontNo);

			// 出险日期
			String sql22 = "select to_char(a.accdate,'yyyy-mm-dd') from llcase a where a.rgtno='"
					+ "?RgtNo?" + "'";
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.sql(sql22);
			sqlbv22.put("RgtNo", RgtNo);
			String AccDate = tExeSQL.getOneValue(sqlbv22);

			// 出险人姓名
			String sql23 = "select name from LCInsured where grpcontno='"
					+ "?GrpcontNo?" + "' and InsuredNO='" + "?InsuredNo?" + "'";
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.sql(sql23);
			sqlbv23.put("GrpcontNo", GrpcontNo);
			sqlbv23.put("InsuredNo", InsuredNo);
			String InsuredName = tExeSQL.getOneValue(sqlbv23);
			if (InsuredName == null) {
				state = "打印赔清单时，被保险人姓名为空值!赔案号" + RgtNo + "客户号" + InsuredNo;
				return state;
			}

			document.add(new Paragraph("尊敬的 " + CustomerName + " 先生/女士", Font12));
			document.add(new Paragraph("    首先感谢您对本公司的支持！", Font12));
			document.add(new Paragraph("    您本次提交的关于" + AccDate
					+ "所发生事故的索赔资料，经本公司认真审核，现已理赔结案，赔付详情如下所示：", Font12));
			document.add(new Paragraph("    ", Font12));

			// 初始化表格
			float[] widths = { 10f, 10f, 10f, 15f, 10f, 15f };
			PdfPTable table = new PdfPTable(widths);

			try {
				// 数据准备SQL文
				String Sql = "select b.drawer, b.actugetno,"
						+ " (select a.relationtoinsured from llbnfgather a where a.clmno = b.otherno and a.bnfno = b.serialno and a.customerno = b.insuredno),"
						+ " (select a.codename from ldcode a where a.codetype = 'paymode' and a.code=b.paymode),"
						+ " b.drawerid, b.sumgetmoney, b.bankcode,"
						+ " (select a.accname from llbnfgather a where a.clmno = b.otherno and a.bnfno = b.serialno and a.customerno = b.insuredno), b.bankaccno"
						+ " from ljaget b where othernotype = '5' and b.otherno = '"
						+ "?RgtNo?" + "'" + " and b.insuredno='"
						+ "?InsuredNo?" + "'";
				SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
				sqlbv24.sql(Sql);
				sqlbv24.put("RgtNo", RgtNo);
				sqlbv24.put("InsuredNo", InsuredNo);
				SSRS tSSRS = tExeSQL.execSQL(sqlbv24);
				if (tSSRS == null || tSSRS.getMaxRow() <= 0) {
					state = "打印理赔给付通知时出错,未查询到出险人实际支付数据!";
					return state;
				}
				double dRealPay = 0.00;
				for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
					// 1、受益人
					cell = new PdfPCell(new Phrase("受益人", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(tSSRS.GetText(i, 1), Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					// 2、实付号
					cell = new PdfPCell(new Phrase("实付号", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(new Phrase(tSSRS.GetText(i,
							2), Font10)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					// 3、与被保险人关系
					cell = new PdfPCell(new Phrase("与被保险人关系", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(new Phrase(tSSRS.GetText(i,
							3), Font10)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					// 4、支付方式
					cell = new PdfPCell(new Phrase("支付方式", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(new Phrase(tSSRS.GetText(i,
							4), Font10)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					// 5、证件号码
					cell = new PdfPCell(new Phrase("证件号码", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(new Phrase(tSSRS.GetText(i,
							5), Font10)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					// 6、受益金额
					cell = new PdfPCell(new Phrase("受益金额", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(new Phrase(tSSRS.GetText(i,
							6), Font10)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					dRealPay = dRealPay
							+ Stringtools.stringtodouble(tSSRS.GetText(i, 6));

					// 7、支付银行
					cell = new PdfPCell(new Phrase("支付银行", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(new Phrase(tSSRS.GetText(i,
							7), Font10)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					// 8、银行帐户名
					cell = new PdfPCell(new Phrase("银行帐户名", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(new Phrase(tSSRS.GetText(i,
							8), Font10)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					// 9、银行帐号
					cell = new PdfPCell(new Phrase("银行帐号", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(new Phrase(tSSRS.GetText(i,
							9), Font10)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				} // 循环结束

				table.setWidthPercentage(100);
				document.add(table);

				document.add(new Paragraph("赔付结果:"
						+ Stringtools.stringtostring(
								Stringtools.doubletostring(dRealPay), 2) + "元",
						Font12));
				document.add(new Paragraph("  ", Font12));

				// -- 医疗账单费用 LLCaseReceipt Fee, 收据张数: LLFeeMain 收据数
				// -- 合理费用 LLCaseReceipt AdjSum
				// -- 费用类型 FeeItemType A:门诊费用,B:住院费用 分类sum金额;
				// -- 各种理赔类型下的理赔金额: select * from llclaimdetail
				// 根据customerno,getdutykind 汇总:

				// 医疗账单费用合计
				String sql25 = "select (case when sum(a.fee) is not null then sum(a.fee) else 0 end) from LLCaseReceipt a where a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
				sqlbv25.sql(sql25);
				sqlbv25.put("RgtNo", RgtNo);
				sqlbv25.put("InsuredNo", InsuredNo);
				String Fee = tExeSQL.getOneValue(sqlbv25);

				// 收据合计张数
				String sql26 = "select (case when count(a.mainfeeno) is not null then count(a.mainfeeno) else 0 end) from LLFeeMain a where a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
				sqlbv26.sql(sql26);
				sqlbv26.put("RgtNo", RgtNo);
				sqlbv26.put("InsuredNo", InsuredNo);
				String mainfeenoNum = tExeSQL.getOneValue(sqlbv26);

				// 合理费用
				String sql27 = "select (case when sum(a.adjsum) is not null then sum(a.adjsum) else 0 end) from LLCaseReceipt a where a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
				sqlbv27.sql(sql27);
				sqlbv27.put("RgtNo", RgtNo);
				sqlbv27.put("InsuredNo", InsuredNo);
				String adjsum = tExeSQL.getOneValue(sqlbv27);

				// 门诊费用
				String sql28 = "select (case when sum(a.adjsum) is not null then sum(a.adjsum) else 0 end) from LLCaseReceipt a where a.FeeItemType='A' and a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
				sqlbv28.sql(sql28);
				sqlbv28.put("RgtNo", RgtNo);
				sqlbv28.put("InsuredNo", InsuredNo);
				String adjsumA = tExeSQL.getOneValue(sqlbv28);

				// 住院费用
				String sql29 = "select (case when sum(a.adjsum) is not null then sum(a.adjsum) else 0 end) from LLCaseReceipt a where a.FeeItemType='B' and a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
				sqlbv29.sql(sql29);
				sqlbv29.put("RgtNo", RgtNo);
				sqlbv29.put("InsuredNo", InsuredNo);
				String adjsumB = tExeSQL.getOneValue(sqlbv29);

				// 本次赔付医疗保险金
				String sql30 = "select (case when sum(RealPay) is not null then sum(RealPay) else 0 end) from llclaimdetail a where a.getdutykind in ('100','200') and a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
				sqlbv30.sql(sql30);
				sqlbv30.put("RgtNo", RgtNo);
				sqlbv30.put("InsuredNo", InsuredNo);
				String RealPay00 = tExeSQL.getOneValue(sqlbv30);

				// 本次残疾保险金
				String sql31 = "select (case when sum(RealPay) is not null then sum(RealPay) else 0 end) from llclaimdetail a where a.getdutykind in ('101','201') and a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
				sqlbv31.sql(sql31);
				sqlbv31.put("RgtNo", RgtNo);
				sqlbv31.put("InsuredNo", InsuredNo);
				String RealPay01 = tExeSQL.getOneValue(sqlbv31);

				// 本次身故保险金
				String sql32 = "select (case when sum(RealPay) is not null then sum(RealPay) else 0 end) from llclaimdetail a where a.getdutykind in ('102','202') and a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
				sqlbv32.sql(sql32);
				sqlbv32.put("RgtNo", RgtNo);
				sqlbv32.put("InsuredNo", InsuredNo);
				String RealPay02 = tExeSQL.getOneValue(sqlbv32);

				// 本次高残保险金
				String sql33 = "select (case when sum(RealPay) is not null then sum(RealPay) else 0 end) from llclaimdetail a where a.getdutykind in ('103','203') and a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
				sqlbv33.sql(sql33);
				sqlbv33.put("RgtNo", RgtNo);
				sqlbv33.put("InsuredNo", InsuredNo);
				String RealPay03 = tExeSQL.getOneValue(sqlbv33);

				// 本次重大疾病保险金
				String sql34 = "select (case when sum(RealPay) is not null then sum(RealPay) else 0 end) from llclaimdetail a where a.getdutykind in ('104','204') and a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
				sqlbv34.sql(sql34);
				sqlbv34.put("RgtNo", RgtNo);
				sqlbv34.put("InsuredNo", InsuredNo);
				String RealPay04 = tExeSQL.getOneValue(sqlbv34);

				// 本次特种疾病保险金
				String sql35 = "select (case when sum(RealPay) is not null then sum(RealPay) else 0 end) from llclaimdetail a where a.getdutykind in ('105','205') and a.clmno='"
						+ "?RgtNo?"
						+ "' and a.customerno='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
				sqlbv35.sql(sql35);
				sqlbv35.put("RgtNo", RgtNo);
				sqlbv35.put("InsuredNo", InsuredNo);
				String RealPay05 = tExeSQL.getOneValue(sqlbv35);

				if (Double.parseDouble(Fee) > 0
						&& Double.parseDouble(mainfeenoNum) > 0) {
					document.add(new Paragraph("    您此次所提交的理赔申请材料中，医疗账单费用合计:"
							+ Fee + "元。收据合计：" + mainfeenoNum + "张。", Font12));

					document.add(new Paragraph("    依据保险合同的约定，经过审慎核定，合理费用为："
							+ adjsum + "元。其中:门诊费用：" + adjsumA + "元。住院费用："
							+ adjsumB + "元。", Font12));
				}

				// 各例牌类型的金额
				String RealPay = "";
				if (Double.parseDouble(RealPay00) > 0) {
					RealPay += "医疗保险金" + RealPay00 + "元,";
				}
				if (Double.parseDouble(RealPay01) > 0) {
					RealPay += "残疾保险金" + RealPay01 + "元,";
				}
				if (Double.parseDouble(RealPay02) > 0) {
					RealPay += "身故保险金" + RealPay02 + "元,";
				}
				if (Double.parseDouble(RealPay03) > 0) {
					RealPay += "高残保险金" + RealPay03 + "元,";
				}
				if (Double.parseDouble(RealPay04) > 0) {
					RealPay += "重大疾病保险金" + RealPay04 + "元,";
				}
				if (Double.parseDouble(RealPay05) > 0) {
					RealPay += "特种疾病保险金" + RealPay05 + "元,";
				}
				// 本保险年度累计赔付
				String sql36 = "select a.cvalidate from lcgrpcont a where a.grpcontno='"
						+ "?GrpcontNo?" + "'";
				SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
				sqlbv36.sql(sql36);
				sqlbv36.put("GrpcontNo", GrpcontNo);
				String cvalidate = tExeSQL.getOneValue(sqlbv36);
				int PolYear = PubFun.calPolYear(cvalidate,
						PubFun.getCurrentDate());
				String StartDate = PubFun.newCalDate(cvalidate, "Y",
						PolYear - 1);// 本保险年度开始日期
				String EndDate = PubFun.newCalDate(cvalidate, "Y", PolYear);// 本保险年度结束日期
				String sql37 = "select sum(a.sumgetmoney) from ljaget a where a.insuredno='"
						+ "?InsuredNo?"
						+ "' and a.confdate>='"
						+ "?StartDate?"
						+ "' and a.confdate<='" + "?EndDate?" + "'";
				SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
				sqlbv37.sql(sql37);
				sqlbv37.put("InsuredNo", InsuredNo);
				sqlbv37.put("StartDate", StartDate);
				sqlbv37.put("EndDate", EndDate);
				String RealPaySum = tExeSQL.getOneValue(sqlbv37);
				document.add(new Paragraph("    本次赔付" + RealPay
						+ "您在本保险年度累计赔付达：" + RealPaySum + "元。", Font12));
			} catch (Exception e) {
				state = "打印赔款清单的数据库连接时出错！" + e.getMessage();
				return state;
			}

			document.add(new Paragraph("  ", Font12));
			document.add(new Paragraph("  ", Font12));
			document.close();
		} catch (Exception e) {
			state = "打印理赔给付通知出错！" + e.getMessage();
			return state;
		}
		return state;
	}

	// 5、打印团险理赔决定通知书(文件名，赔案号，管理机构)
	public String CreateClaimNotice(String filename, String RgtNo,
			String ManageCom) {
		String state = "";
		logger.debug("团险理赔决定通知书打印：" + filename + "|" + RgtNo + "|" + ManageCom);
		Document document = new Document(PageSize.A4);
		try {
			// 定义字体
			// BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
			// BaseFont.NOT_EMBEDDED);
			BaseFont bf = BaseFont.createFont(
					"c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			Font Font10 = new Font(bf, 12, Font.NORMAL);
			Font Font12 = new Font(bf, 14, Font.NORMAL);

			// 生成的PDF文件文件名
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(filename));

			// 页边距的定义:左 右 上 下
			document.setMargins(36, 36, 36, 36);
			document.open();
			document.add(new Paragraph("   ", Font10));
			document.add(new Paragraph("   ", Font10));
			document.add(new Paragraph("   ", Font10));

			ExeSQL tExeSQL = new ExeSQL();
			// 团单号
			String sql38 = "select GrpContNo from llregister where rgtno='"
					+ "?RgtNo?" + "'";
			SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
			sqlbv38.sql(sql38);
			sqlbv38.put("RgtNo", RgtNo);
			String GrpContNo = tExeSQL.getOneValue(sqlbv38);

			// 人数
			String sql39 = "select count(*) from (select distinct InsuredNO from LLClaimpolicy where  ClmNo='"
					+ "?RgtNo?"
					+ "' and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2')) t1";
			SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
			sqlbv39.sql(sql39);
			sqlbv39.put("RgtNo", RgtNo);
			String CountInsured = tExeSQL.getOneValue(sqlbv39);

			// 取一个被保险人姓名
			String sql40 = "select Name from lcinsured where GrpContNo='"
					+ "?GrpContNo?"
					+ "' and InsuredNO in (select InsuredNO from LLClaimpolicy where  ClmNo='"
					+ "?RgtNo?"
					+ "' and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))";
			SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
			sqlbv40.sql(sql40);
			sqlbv40.put("GrpContNo", GrpContNo);
			sqlbv40.put("RgtNo", RgtNo);
			String InsuredName = tExeSQL.getOneValue(sqlbv40);
			if (Stringtools.stringtoint(CountInsured) > 1) {
				InsuredName = "被保险人：  " + InsuredName.trim() + " 等 "
						+ CountInsured + " 人";
			}

			// 出险日期
			String sql41 = "select to_char(a.accdate,'yyyy-mm-dd') from llcase a where a.rgtno='"
					+ "?RgtNo?" + "'";
			SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
			sqlbv41.sql(sql41);
			sqlbv41.put("RgtNo", RgtNo);
			String AccDate = tExeSQL.getOneValue(sqlbv41);

			// 理赔申请决定
			String AuditConclusion = "";

			// 决定的理由
			String sql42 = "select a.AuditConclusion,AuditNoPassReason from llclaimuwmain a where a.rgtno='"
					+ "?RgtNo?" + "'";
			SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
			sqlbv42.sql(sql42);
			sqlbv42.put("RgtNo", RgtNo);
			String AuditNoPassReason = "";
			SSRS tSSRS = tExeSQL.execSQL(sqlbv42);
			if ("1".equals(tSSRS.GetText(1, 1))) {// 1-拒付
				AuditConclusion = "拒付";
				String sql43 = "select * from ldcode a where a.codetype='llprotestreason' and code='"
						+ "?code?" + "'";
				SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
				sqlbv43.sql(sql43);
				sqlbv43.put("code", tSSRS.GetText(1, 2));
				AuditNoPassReason = tExeSQL.getOneValue(sqlbv43);
			} else if ("0".equals(tSSRS.GetText(1, 1))) {// 0-给付:判断协议给付/通融给付
				String sql44 = "select b.adjreason,b.AdjRemark from llclaimdetail b where b.clmno='"
						+ "?RgtNo?" + "'";
				SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
				sqlbv44.sql(sql44);
				sqlbv44.put("RgtNo", RgtNo);
				SSRS tSSRS1 = tExeSQL.execSQL(sqlbv44);
				if ("00".equals(tSSRS1.GetText(1, 1))) {
					AuditConclusion = "协议给付";
					AuditNoPassReason = tSSRS1.GetText(1, 2);
				} else if ("14".equals(tSSRS1.GetText(1, 1))) {
					AuditConclusion = "通融给付";
					AuditNoPassReason = tSSRS1.GetText(1, 2);
				} else {
					state = "该理赔结论不是拒付/协议给付/通融给付，不能打印此通知书!";
					return state;
				}
			} else {
				state = "该理赔结论不是拒付/协议给付/通融给付，不能打印此通知书!";
				return state;
			}

			// 数据准备结束，开始写批单的内容
			document.add(new Paragraph(Stringtools.createspace(25)
					+ "团险理赔决定通知书", Font12));

			document.add(new Paragraph("   ", Font10));
			document.add(new Paragraph("尊敬的客户：", Font10));

			document.add(new Paragraph(
					"    您提交的关于保险单号码 "
							+ GrpContNo
							+ " 保险合同之被保险人 "
							+ InsuredName
							+ " ，在 "
							+ AccDate
							+ " 出险事件的理赔申请及相关资料，本公司已经收悉。根据保险条款及相关法律规定，经我公司理赔部门审慎核定，本公司对您理赔申请决定 "
							+ AuditConclusion + " 保险金。", Font10));
			document.add(new Paragraph("    本公司作出上述处理决定的理由是："
					+ AuditNoPassReason, Font10));
			document.add(new Paragraph(
					"    若您对本公司的处理决定有异议，可于接到本通知之日起十日内，与本公司理赔部门联系。", Font10));

			document.add(new Paragraph("   ", Font10));
			document.add(new Paragraph(Stringtools.createspace(140)
					+ "MSRS保险股份有限公司", Font10));
			document.add(new Paragraph(Stringtools.createspace(140) + "（盖章有效）",
					Font10));
			document.add(new Paragraph(Stringtools.createspace(140)
					+ Stringtools.longtoday(), Font10));
			document.add(new Paragraph("   ", Font10));

			document.close(); // 写PDF文件结束
		} catch (Exception ex) {
			state = "业务程序处理出错！" + ex.getMessage();
		}
		return state;
	}

	/**
	 * *******************************理赔支付清单生成excel文件***************************
	 * *****************************
	 */
	// 打印理赔支付清单===>生成excel文件===>(文件名,赔案号,管理机构)
	public static String[] CreateOutBillExcel(String filename, String RgtNo,
			String ManageCom) {
		Stringtools.log("", "支付清单：Excel版" + filename + "|" + RgtNo + "|"
				+ ManageCom, "");
		// 生成excel的数组：
		int tmpcount = 0;
		String Sql = "select  count(*)";
		Sql = Sql + " from LLClaimDetail ";
		Sql = Sql + " where   ";
		Sql = Sql + " (GiveType='0' or givetype='2' or givetype='3') "; // 不是拒赔
		// 判断是否结案
		Sql = Sql + " and  clmno in (select RgtNo from  LLRegister ";
		Sql = Sql + " where EndCaseDate is not null ";
		Sql = Sql + " and EndCaseFlag='1' ";
		Sql = Sql + " and RgtNo='" + "?RgtNo?" + "')";
		Sql = Sql + " and ClmNo='" + "?RgtNo?" + "'";
		SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
		sqlbv45.sql(Sql);
		sqlbv45.put("RgtNo", RgtNo);
		ExeSQL my = new ExeSQL();
		tmpcount = Stringtools.stringtoint(my.getOneValue(sqlbv45));
		if (tmpcount == 0) {
			String[] data = { "0", "打印理赔支付清单EXCEL版出错，数组元素为0个" };
			return data;
		}
		tmpcount = tmpcount * 7 + 21;
		String[] data = new String[tmpcount];

		data[0] = "";
		data[1] = "理 赔 支 付 清 单";
		data[2] = "";
		data[3] = "";
		data[4] = "";
		data[5] = "";
		data[6] = "";
		// 取投保单位
		String sql46 = "";
		if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
			sql46 = "select GrpName from LLRegister where RgtNo='" + "?RgtNo?"
					+ "' and rownum<=1 ";
		} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
			sql46 = "select GrpName from LLRegister where RgtNo='" + "?RgtNo?"
					+ "' limit 1 ";
		}

		SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
		sqlbv46.sql(sql46);
		sqlbv46.put("RgtNo", RgtNo);
		String CustomerName = my.getOneValue(sqlbv46);
		// 取保单号
		String sql47 = "";
		if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
			sql47 = "select GrpContNo from LLRegister where RgtNo='"
					+ "?RgtNo?" + "'  and rownum<=1";
		} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
			sql47 = "select GrpContNo from LLRegister where RgtNo='"
					+ "?RgtNo?" + "'  limit 1";
		}
		SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
		sqlbv47.sql(sql47);
		sqlbv47.put("RgtNo", RgtNo);
		String GrpcontNo = my.getOneValue(sqlbv47);

		data[7] = "保单号： " + GrpcontNo;
		data[8] = "";
		data[9] = "赔案号： " + RgtNo;
		data[10] = "";
		data[11] = "投保单位：" + CustomerName;
		data[12] = "";
		data[13] = "";

		// 开始写表头
		data[14] = "个人客户号";
		data[15] = "姓名";
		data[16] = "身份证号";
		data[17] = "本次赔付金额";
		data[18] = "收款人";
		data[19] = "收款银行";
		data[20] = "银行账户";

		// 数据准备SQL文
		Sql = "select  CustomerNo,sum(realpay)";
		Sql = Sql + " from LLClaimDetail ";
		Sql = Sql + " where   ";
		Sql = Sql + " (GiveType='0' or givetype='2' or givetype='3') "; // 不是拒赔
		// 判断是否结案
		Sql = Sql + " and  clmno in (select RgtNo from  LLRegister ";
		Sql = Sql + " where EndCaseDate is not null ";
		Sql = Sql + " and EndCaseFlag='1' ";
		Sql = Sql + " and RgtNo='" + "?RgtNo?" + "')";
		Sql = Sql + " and ClmNo='" + "?RgtNo?" + "'";
		Sql = Sql + "   group by CustomerNo";

		Stringtools.log("", Sql, "");
		SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
		sqlbv48.sql(Sql);
		sqlbv48.put("RgtNo", RgtNo);
		ExeSQL myExeSQL = new ExeSQL();
		// 数据库连接
		try {
			SSRS tSSRS = myExeSQL.execSQL(sqlbv48);
			if (tSSRS == null) {
				data[0] = "0";
				data[1] = "打印赔款清单的数据库连接时出错,数据库连接失败!";
				Stringtools.log("", data[1], "");
				return data;
			}

			// 变量定义
			String InsuredNo = "";
			String InsuredName = "";
			// 本次赔付
			double dRealPay = 0.00;
			String RealPay = "";
			// 收款人
			String AccName = "";
			// 银行名
			String BankNo = "";
			String BankName = "";
			// 账号
			String BankAccNo = "";
			// ID
			String IDNo = "";
			int k = 21;
			for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {

				InsuredNo = tSSRS.GetText(tmpi, 1); // .getString(1);

				if (InsuredNo == null) {
					InsuredNo = "";
					data[0] = "0";
					data[1] = "打印理赔支付清单时，被保险人客号为空值!赔案号" + RgtNo;
					Stringtools.log("", data[1], "");
					return data;
				}
				data[k] = InsuredNo;
				String sql49 = "select trim(Name) from LDPerson where CustomerNo='"
						+ "?InsuredNo?" + "'";
				SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
				sqlbv49.sql(sql49);
				sqlbv49.put("InsuredNo", InsuredNo);
				InsuredName = my.getOneValue(sqlbv49);
				if (InsuredName == null) {
					InsuredName = "";
					data[0] = "0";
					data[1] = "打印赔清单时，被保险人姓名为空值!赔案号" + RgtNo + "客户号"
							+ InsuredNo;
					Stringtools.log("", data[1], "");
					return data;
				}
				data[k + 1] = InsuredName;
				String sql50 = "select trim(IDNO) from LDPerson where CustomerNo='"
						+ "?InsuredNo?" + "'";
				SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
				sqlbv50.sql(sql50);
				sqlbv50.put("InsuredNo", InsuredNo);
				IDNo = my.getOneValue(sqlbv50);
				data[k + 2] = IDNo;

				RealPay = Stringtools.stringtostring(tSSRS.GetText(tmpi, 2), 2);
				data[k + 3] = "double" + RealPay;
				String sql51 = "select Name from LLAccount where GrpContNo='"
						+ "?GrpcontNo?" + "' and InsuredNo='" + "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
				sqlbv51.sql(sql51);
				sqlbv51.put("GrpcontNo", GrpcontNo);
				sqlbv51.put("InsuredNo", InsuredNo);
				AccName = my.getOneValue(sqlbv51);
				data[k + 4] = AccName;

				// 银行代码
				// BankNo=my.getOneValue("select BankAccNo from LCInsured where
				// GrpContNo='"+GrpcontNo+ "' and InsuredNo='"+InsuredNo+"'");
				String sql52 = "select BankCode from LLAccount  where GrpContNo='"
						+ "?GrpcontNo?"
						+ "' and InsuredNo='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
				sqlbv52.sql(sql52);
				sqlbv52.put("GrpcontNo", GrpcontNo);
				sqlbv52.put("InsuredNo", InsuredNo);
				BankNo = my.getOneValue(sqlbv52);

				if (BankNo == null) {
					BankNo = "";
				}

				// 银行中文
				String tmpsql = "select codename from ldcode  where codetype='bank'  and code='"
						+ "?BankNo?" + "'";
				SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
				sqlbv53.sql(tmpsql);
				sqlbv53.put("BankNo", BankNo);
				BankName = my.getOneValue(sqlbv53);
				tmpsql = "";
				data[k + 5] = BankName;

				// BankAccNo=my.getOneValue("select BankAccNo from LCInsured
				// where GrpContNo='"+GrpcontNo+ "' and
				// InsuredNo='"+InsuredNo+"'");
				String sql54 = "select Account from LLAccount where GrpContNo='"
						+ "?GrpcontNo?"
						+ "' and InsuredNo='"
						+ "?InsuredNo?"
						+ "'";
				SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
				sqlbv54.sql(sql54);
				sqlbv54.put("GrpcontNo", GrpcontNo);
				sqlbv54.put("InsuredNo", InsuredNo);
				BankAccNo = my.getOneValue(sqlbv54);
				if (BankAccNo == null) {
					BankAccNo = "";
				}
				data[k + 6] = BankAccNo;
				k = k + 7;

			} // 循环结束
			k = 0;
		} catch (Exception e) {
			data[0] = "0";
			data[1] = "打印理赔支付清单的数据库连接时出错！" + e.getMessage();
			Stringtools.log("", data[1], "");
			e.printStackTrace();
			return data;
		}
		return data;
	}

	// 取是否拒赔 团业管：一个案子只要有一个拒赔，那么整个案子就是拒赔
	public boolean getRefuse(String RgtNo) {
		ExeSQL my = new ExeSQL();
		// GiveType='0'--给付;GiveType=1--拒赔
		String sql55 = "select ClmState from llregister where RgtNo='"
				+ "?RgtNo?" + "'";
		SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
		sqlbv55.sql(sql55);
		sqlbv55.put("RgtNo", RgtNo);
		String ClmState = my.getOneValue(sqlbv55);
		if (ClmState == null) {
			ClmState = "0";
		}
		if (Stringtools.stringtoint(ClmState) >= 80) {
			return true; // 拒赔
		} else {
			return false; // 赔付
		}
	}

	/**
	 * ***********************************************888拒赔批单(理赔决定通知书)**********
	 * ********************************************
	 */
	public String RefuseClaimBatch(String filename, String RgtNo,
			String ManageCom) {

		String state = "";
		// 控制上边空多少行
		int UpSpace = 5;
		Stringtools.log("", "理赔决定通知书：" + filename + "|" + RgtNo + "|"
				+ ManageCom, "");
		Document document = new Document(PageSize.A4);

		try {
			// 定义字体
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bf, 12, Font.NORMAL);
			// 生成的PDF文件文件名
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(filename));
			// 页边距的定义
			// 页边距的定义
			// Paragraph paragraph = new Paragraph();
			// paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			/**
			 * **********与mm的换算：1 mm = 2.8346 pt***** 72pt=1 英寸*****************
			 */
			/** **************** 左 右 上 下 ************************ */
			document.setMargins(36, 36, 36, 36);
			document.open();
			for (int i = 1; i <= UpSpace; i++) {
				document.add(new Paragraph("   ", FontChinese));
			}
			// 数据准备：
			// 赔案号已经传入！！ RgtNo
			// 取一个被保险人姓名
			ExeSQL my = new ExeSQL();
			String sql56 = "select GrpContno from llregister where rgtno='"
					+ "?RgtNo?" + "'";
			SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
			sqlbv56.sql(sql56);
			sqlbv56.put("RgtNo", RgtNo);
			String GrpcontNo = my.getOneValue(sqlbv56);

			String sql57 = "select Name from lcinsured where GrpContNo='"
					+ "?GrpcontNo?"
					+ "' and InsuredNO in (select CustomerNo from   LLClaimDetail where  ClmNo='"
					+ "?RgtNo?" + "' )";
			SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
			sqlbv57.sql(sql57);
			sqlbv57.put("GrpcontNo", GrpcontNo);
			sqlbv57.put("RgtNo", RgtNo);
			String InsuredName = my.getOneValue(sqlbv57);

			// 人数
			String sql58 = "select count(*) from (select distinct CustomerNo from LLClaimDetail where  ClmNo='"
					+ "?RgtNo?" + "') t";
			SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
			sqlbv58.sql(sql58);
			sqlbv58.put("RgtNo", RgtNo);
			String CountInsured = my.getOneValue(sqlbv58);

			// 全部保险责任形成一个串
			String Duty = "";
			// 全部保单号开成一个串
			String StrGrpContNO = "";

			ExeSQL myExeSQL = new ExeSQL();
			// 数据库连接
			try {
				String Sql = "select distinct grpcontno from lcpol  where PolNo in (select distinct polno from LLClaimDetail  where  RgtNo='"
						+ "?RgtNo?" + "'  )";
				SQLwithBindVariables sqlbv59 = new SQLwithBindVariables();
				sqlbv59.sql(Sql);
				sqlbv59.put("RgtNo", RgtNo);
				SSRS tSSRS = myExeSQL.execSQL(sqlbv59);
				if (tSSRS == null) {
					state = "2--打印理赔决定通知书时，数据库连接失败!";
					Stringtools.log("", state, "");
					return state;
				}
				Stringtools.log("", Sql, "");

				int tmp_k = 0;
				for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
					String tmp = tSSRS.GetText(tmpi, 1); // .getString(1);
					if (tmp == null) {
						tmp = "";
					}
					if (tmp_k == 0) {
						StrGrpContNO = StrGrpContNO + "   " + tmp.trim();
					} else {
						StrGrpContNO = StrGrpContNO + "、" + tmp.trim();
					}
				}

				if (StrGrpContNO.trim().length() == 0) {
					state = "拒赔批单打印-->取保单号失败,赔案号：" + RgtNo;
					return state;
				}

			} catch (Exception e) {
				state = "2--打印理赔决定通知书时，数据库连接出错!" + e.getMessage();
				Stringtools.log("", state, "");
				return state;
			}

			String sql60 = "select to_char(EndCaseDate,'yyyy-mm-dd') from LLRegister where RgtNo='"
					+ "?RgtNo?" + "' ";
			SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
			sqlbv60.sql(sql60);
			sqlbv60.put("RgtNo", RgtNo);
			String EndCaseDate = my.getOneValue(sqlbv60);

			// 数据准备结束，开始写批单的内容
			document.add(new Paragraph(Stringtools.createspace(80) + "理赔决定通知书",
					FontChinese));
			document.add(new Paragraph("尊敬的客户：", FontChinese));
			String strRefuse = Stringtools.createspace(10) + "您提交的关于保险单号码";
			strRefuse = strRefuse + StrGrpContNO;
			strRefuse = strRefuse + " 保险合同之被保险人 ";
			int tmpcount = Stringtools.stringtoint(CountInsured);
			if (tmpcount > 1) {
				strRefuse = strRefuse + InsuredName.trim() + " 等 "
						+ CountInsured + " 人，";
			} else {
				strRefuse = strRefuse + InsuredName.trim() + "，";
			}
			// 出险日期
			String sql61 = "";
			if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
				sql61 = "select concat(concat(concat(concat(concat(to_char(accdate ,'yyyy'),'年'),to_char(accdate ,'mm')),'月'),to_char(accdate ,'dd')),'日') from llcase where RgtNo='"
						+ "?RgtNo?" + "'  and rownum<=1";
			} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
				sql61 = "select concat(concat(concat(concat(concat(to_char(accdate ,'yyyy'),'年'),to_char(accdate ,'mm')),'月'),to_char(accdate ,'dd')),'日') from llcase where RgtNo='"
						+ "?RgtNo?" + "'  limit 1";
			}

			SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
			sqlbv61.sql(sql61);
			sqlbv61.put("RgtNo", RgtNo);
			String AccStartDate = my.getOneValue(sqlbv61);

			strRefuse = strRefuse
					+ "在 "
					+ AccStartDate
					+ " 出险事件的理赔申请及相关资料，本公司已经收悉。根据保险条款及相关法律规定，经我公司理赔部门审慎核定，本公司对您的理赔申请决定拒付保险金。";
			document.add(new Paragraph(strRefuse, FontChinese));
			// 拒赔原因
			String sql62 = "select trim(CodeName)  from ldcode where codetype = 'llprotestreason' and trim(code) in ( select trim(AuditNoPassReason) from LLClaimUWMain where ClmNo='"
					+ "?RgtNo?" + "' or RgtNo='" + "?RgtNo?" + "' )";
			SQLwithBindVariables sqlbv62 = new SQLwithBindVariables();
			sqlbv62.sql(sql62);
			sqlbv62.put("RgtNo", RgtNo);
			String strReson = my.getOneValue(sqlbv62);
			if (strReson.trim().length() >= 1) {
				document.add(new Paragraph(Stringtools.createspace(10)
						+ "本公司作出上述处理决定的理由为：" + strReson, FontChinese));
				// 拒赔依据
				String sql63 = "select trim(AuditNoPassDesc) from LLClaimUWMain where ClmNo='"
						+ "?RgtNo?" + "' or RgtNo='" + "?RgtNo?" + "' ";
				SQLwithBindVariables sqlbv63 = new SQLwithBindVariables();
				sqlbv63.sql(sql63);
				sqlbv63.put("RgtNo", RgtNo);
				String strGist = my.getOneValue(sqlbv63);
				if (strGist.trim().length() >= 1) {
					document.add(new Paragraph(Stringtools.createspace(10)
							+ "拒赔依据：" + strGist, FontChinese));
				}
			}
			document.add(new Paragraph(" ", FontChinese));
			document.add(new Paragraph(" ", FontChinese));
			document.add(new Paragraph(Stringtools.createspace(10)
					+ "若您对以上处理决定有异议，可于接到本通知之日起十日内要求本公司理赔部门详细解释。 ", FontChinese));

			// 加空行
			for (int i = 1; i <= 5; i++) {
				document.add(new Paragraph("   ", FontChinese));
			}
			document.add(new Paragraph(Stringtools.createspace(100)
					+ "TKRS保险股份有限公司", FontChinese));
			document.add(new Paragraph(Stringtools.createspace(120)
					+ EndCaseDate, FontChinese));
			document.add(new Paragraph(" ", FontChinese));
			document.close(); // 写PDF文件结束

		} catch (Exception ex) {
			Stringtools.log("", "CreateClaimBatch.java===>" + ex.getMessage(),
					"");
			state = "业务程序处理出错！" + ex.getMessage();
		}
		return state;
	}

	// 理赔个人清单 (生成的pdf文件名,赔案号,模板文件名)
	public String CreateClaimPer(String filename, String RgtNo,
			String TemplateFilename) {
		String state = "";

		String SQL = "select distinct customerno from llclaimdetail ";
		SQL = SQL + " where clmno='" + "?RgtNo?" + "' ";
		SQLwithBindVariables sqlbv64 = new SQLwithBindVariables();
		sqlbv64.sql(SQL);
		sqlbv64.put("RgtNo", RgtNo);
		ExeSQL myExeSQL = new ExeSQL();
		// 取公共数据（每页都相同的数据）
		String[] Publicstr = new String[8];

		// 保单号：
		String sql65 = "select Grpcontno from llregister where rgtno='"
				+ "?RgtNo?" + "'";
		SQLwithBindVariables sqlbv65 = new SQLwithBindVariables();
		sqlbv65.sql(sql65);
		sqlbv65.put("RgtNo", RgtNo);
		Publicstr[0] = myExeSQL.getOneValue(sqlbv65);
		// 投保单位：
		String sql66 = "select GrpName from lcgrpcont where GrpContNo='"
				+ "?GrpContNo?" + "'";
		SQLwithBindVariables sqlbv66 = new SQLwithBindVariables();
		sqlbv66.sql(sql66);
		sqlbv66.put("GrpContNo", Publicstr[0]);
		Publicstr[1] = myExeSQL.getOneValue(sqlbv66);
		// 保单生效日期
		String sql67 = "select to_char(cValidate,'yyyy-mm-dd') from lcgrpcont where GrpContno='"
				+ "?GrpContno?" + "'";
		SQLwithBindVariables sqlbv67 = new SQLwithBindVariables();
		sqlbv67.sql(sql67);
		sqlbv67.put("GrpContno", Publicstr[0]);
		Publicstr[2] = myExeSQL.getOneValue(sqlbv67);
		// 赔案号
		Publicstr[4] = RgtNo;
		// 页码(生成文件时自己计算)
		Publicstr[3] = "";
		// 受理日期
		String sql68 = "select to_char(RgtDate,'yyyy-mm-dd') from llregister where rgtno='"
				+ "?RgtNo?" + "' ";
		SQLwithBindVariables sqlbv68 = new SQLwithBindVariables();
		sqlbv68.sql(sql68);
		sqlbv68.put("RgtNo", RgtNo);
		Publicstr[5] = myExeSQL.getOneValue(sqlbv68);
		// 打印日期
		String sql69 = "select to_char(now(),'yyyy-mm-dd') from dual";
		SQLwithBindVariables sqlbv69 = new SQLwithBindVariables();
		sqlbv69.sql(sql69);
		Publicstr[6] = myExeSQL.getOneValue(sqlbv69);
		// 公司名称及地址
		String sql70 = "select concat(concat(name,'  地址：'),address) from ldcom  where comcode=(select substr(managecom,1,2) from lcgrpcont where grpcontno='"
				+ "?grpcontno?" + "')";
		SQLwithBindVariables sqlbv70 = new SQLwithBindVariables();
		sqlbv70.sql(sql70);
		sqlbv70.put("grpcontno", Publicstr[0]);
		Publicstr[7] = "TKRS保险股份有限公司  " + myExeSQL.getOneValue(sqlbv70);

		SSRS tSSRS = myExeSQL.execSQL(sqlbv64);
		int row = tSSRS.getMaxRow();
		int col = tSSRS.getMaxCol();

		// 定义一个数组以装载数据；
		// 计算数组元素个数
		int num = (row * 40);
		String[] getData = new String[num];

		int k = 0;
		try {
			String sql = "";
			for (int i = 1; i <= row; i++) {

				String Customerno = tSSRS.GetText(i, 1);
				String sql71 = "select Name from lcinsured where GrpContno='"
						+ "?GrpContno?" + "' and insuredno='" + "?Customerno?"
						+ "'";
				SQLwithBindVariables sqlbv71 = new SQLwithBindVariables();
				sqlbv71.sql(sql71);
				sqlbv71.put("GrpContno", Publicstr[0]);
				sqlbv71.put("Customerno", Customerno);
				String Name = myExeSQL.getOneValue(sqlbv71);
				getData[k] = Stringtools.inttostring(i);
				getData[k + 1] = Name;
				getData[k + 2] = Customerno;
				String sql72 = "select IdNo from ldperson where Customerno='"+ "?Customerno?" + "'";
				SQLwithBindVariables sqlbv72 = new SQLwithBindVariables();
				sqlbv72.sql(sql72);
				sqlbv72.put("Customerno", Customerno);
				getData[k + 3] = myExeSQL.getOneValue(sqlbv72);
				/***************************************************************
				 * 门诊
				 */
				// 保额 1
				sql = "select StandMoney from lcget where GrpContno='"
						+ "?GrpContno?" + "' ";
				sql = sql + " and insuredno='" + "?Customerno?" + "' ";
				sql = sql
						+ " and getdutycode in (select getdutycode from llclaimdetail where clmno='"
						+ "?RgtNo?" + "'";
				sql = sql + "                  and Customerno='" + "?Customerno?"
						+ "'";
				sql = sql
						+ "                  and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='1')";
				sql = sql + " )";
				SQLwithBindVariables sqlbv73 = new SQLwithBindVariables();
				sqlbv73.sql(sql);
				sqlbv73.put("GrpContno", Publicstr[0]);
				sqlbv73.put("Customerno", Customerno);
				sqlbv73.put("RgtNo", RgtNo);
				getData[k + 4] = myExeSQL.getOneValue(sqlbv73);
				// 申请费用 1
				sql = "select sum(Fee) from llcasereceipt where clmno='"
						+ "?RgtNo?" + "'";
				sql = sql + " and feeitemtype='A' and Customerno='"
						+ "?Customerno?" + "'";
				SQLwithBindVariables sqlbv74 = new SQLwithBindVariables();
				sqlbv74.sql(sql);
				sqlbv74.put("RgtNo", RgtNo);
				sqlbv74.put("Customerno", Customerno);
				getData[k + 5] = myExeSQL.getOneValue(sqlbv74);
				// 扣除费用
				sql = "select sum(RefuseAmnt) from llcasereceipt where clmno='"
						+ "?RgtNo?" + "'";
				sql = sql + " and feeitemtype='A' and Customerno='"
						+ "?Customerno?" + "'";
				SQLwithBindVariables sqlbv75 = new SQLwithBindVariables();
				sqlbv75.sql(sql);
				sqlbv75.put("RgtNo", RgtNo);
				sqlbv75.put("Customerno", Customerno);
				getData[k + 6] = myExeSQL.getOneValue(sqlbv75);
				// 合理费用
				sql = "select sum(AdjSum) from llcasereceipt ";
				sql = sql + "  where clmno='" + "?RgtNo?" + "'";
				sql = sql + " and feeitemtype='A' and Customerno='"
						+ "?Customerno?" + "'";
				SQLwithBindVariables sqlbv76 = new SQLwithBindVariables();
				sqlbv76.sql(sql);
				sqlbv76.put("RgtNo", RgtNo);
				sqlbv76.put("Customerno", Customerno);
				getData[k + 7] = myExeSQL.getOneValue(sqlbv76);
				// 津贴
				getData[k + 8] = "-";
				// 住院天数
				getData[k + 9] = "-";
				// 本次门诊赔付
				sql = "select sum(realpay) from llclaimdetail ";
				sql = sql + " where clmno='" + "?RgtNo?" + "' ";
				sql = sql + "  and Customerno='" + "?Customerno?" + "'";
				sql = sql
						+ " and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='1')";
				SQLwithBindVariables sqlbv77 = new SQLwithBindVariables();
				sqlbv77.sql(sql);
				sqlbv77.put("RgtNo", RgtNo);
				sqlbv77.put("Customerno", Customerno);
				getData[k + 10] = myExeSQL.getOneValue(sqlbv77);
				// 累计门诊赔付
				sql = " select sum(realpay) from llclaimdetail ";
				sql = sql + " where  Customerno='" + "?Customerno?" + "'";
				sql = sql
						+ " and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='1')";
				SQLwithBindVariables sqlbv78 = new SQLwithBindVariables();
				sqlbv78.sql(sql);
				sqlbv78.put("Customerno", Customerno);
				getData[k + 11] = myExeSQL.getOneValue(sqlbv78);
				// 扣除原因
				sql = "select AdjRemark from llcasereceipt ";
				sql = sql + " where clmno='" + "?RgtNo?" + "'";
				sql = sql + " and feeitemtype='A' ";
				sql = sql + " and Customerno='" + "?Customerno?"
						+ "' and AdjRemark is not null";
				SQLwithBindVariables sqlbv79 = new SQLwithBindVariables();
				sqlbv79.sql(sql);
				sqlbv79.put("RgtNo", RgtNo);
				sqlbv79.put("Customerno", Customerno);
				getData[k + 12] = myExeSQL.getOneValue(sqlbv79);
				/***************************************************************
				 * 住院
				 */
				// 保额 2
				k = k + 9;
				sql = "select StandMoney from lcget where GrpContno='"
						+ "?GrpContno?" + "' ";
				sql = sql + " and insuredno='" + "?Customerno?" + "' ";
				sql = sql
						+ " and getdutycode in (select getdutycode from llclaimdetail where clmno='"
						+ "?RgtNo?" + "'";
				sql = sql + "                  and Customerno='" + "?Customerno?"
						+ "'";
				sql = sql
						+ "                  and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='2')";
				sql = sql + " )";
				SQLwithBindVariables sqlbv80 = new SQLwithBindVariables();
				sqlbv80.sql(sql);
				sqlbv80.put("GrpContno", Publicstr[0]);
				sqlbv80.put("RgtNo", RgtNo);
				sqlbv80.put("Customerno", Customerno);
				getData[k + 4] = myExeSQL.getOneValue(sqlbv80);
				// 申请费用 2
				sql = "select sum(Fee) from llcasereceipt where clmno='"
						+ "?RgtNo?" + "'";
				sql = sql + " and feeitemtype='B' and Customerno='"
						+ "?Customerno?" + "'";
				SQLwithBindVariables sqlbv81 = new SQLwithBindVariables();
				sqlbv81.sql(sql);
				sqlbv81.put("RgtNo", RgtNo);
				sqlbv81.put("Customerno", Customerno);
				getData[k + 5] = myExeSQL.getOneValue(sqlbv81);
				// 扣除费用 2
				sql = "select sum(RefuseAmnt) from llcasereceipt where clmno='"
						+ "?RgtNo?" + "'";
				sql = sql + " and feeitemtype='B' and Customerno='"
						+ "?Customerno?" + "'";
				SQLwithBindVariables sqlbv82 = new SQLwithBindVariables();
				sqlbv82.sql(sql);
				sqlbv82.put("RgtNo", RgtNo);
				sqlbv82.put("Customerno", Customerno);
				getData[k + 6] = myExeSQL.getOneValue(sqlbv82);
				// 合理费用
				sql = "select sum(AdjSum) from llcasereceipt ";
				sql = sql + "  where clmno='" + "?RgtNo?" + "'";
				sql = sql + " and feeitemtype='B' and Customerno='"
						+ "?Customerno?" + "'";
				SQLwithBindVariables sqlbv83 = new SQLwithBindVariables();
				sqlbv83.sql(sql);
				sqlbv83.put("RgtNo", RgtNo);
				sqlbv83.put("Customerno", Customerno);
				getData[k + 7] = myExeSQL.getOneValue(sqlbv83);
				// 津贴
				sql = "select StandMoney from lcget ";
				sql = sql + "where Grpcontno='" + "?Grpcontno?" + "'";
				sql = sql + "  and insuredno='" + "?Customerno?" + "'";
				sql = sql
						+ " and dutycode in (select dutycode from lmduty where dutyname  like '%津贴%' )";
				SQLwithBindVariables sqlbv84 = new SQLwithBindVariables();
				sqlbv84.sql(sql);
				sqlbv84.put("Grpcontno", Publicstr[0]);
				sqlbv84.put("Customerno", Customerno);
				getData[k + 8] = myExeSQL.getOneValue(sqlbv84);
				// 住院天数
				sql = "select sum(daycount) from llcasereceipt";
				sql = sql + "  where  clmno='" + "?RgtNo?" + "' ";
				sql = sql + " and feeitemtype='B' and Customerno='"
						+ "?Customerno?" + "'";
				SQLwithBindVariables sqlbv85 = new SQLwithBindVariables();
				sqlbv85.sql(sql);
				sqlbv85.put("RgtNo", RgtNo);
				sqlbv85.put("Customerno", Customerno);
				getData[k + 9] = myExeSQL.getOneValue(sqlbv85);
				// 本次住院赔付
				sql = "select sum(realpay) from llclaimdetail ";
				sql = sql + " where clmno='" + "?RgtNo?" + "' ";
				sql = sql + "  and Customerno='" + "?Customerno?" + "'";
				sql = sql
						+ " and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='2')";
				SQLwithBindVariables sqlbv86 = new SQLwithBindVariables();
				sqlbv86.sql(sql);
				sqlbv86.put("RgtNo", RgtNo);
				sqlbv86.put("Customerno", Customerno);
				getData[k + 10] = myExeSQL.getOneValue(sqlbv86);
				// 累计住院赔付
				sql = " select sum(realpay) from llclaimdetail ";
				sql = sql + " where  Customerno='" + "?Customerno?" + "'";
				sql = sql
						+ " and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='2')";
				SQLwithBindVariables sqlbv87 = new SQLwithBindVariables();
				sqlbv87.sql(sql);
				sqlbv87.put("Customerno", Customerno);
				getData[k + 11] = myExeSQL.getOneValue(sqlbv87);
				// 扣除原因
				sql = "select AdjRemark from llcasereceipt ";
				sql = sql + " where clmno='" + "?RgtNo?" + "'";
				sql = sql + " and feeitemtype='B' ";
				sql = sql + " and Customerno='" + "?Customerno?"
						+ "' and AdjRemark is not null";
				SQLwithBindVariables sqlbv88 = new SQLwithBindVariables();
				sqlbv88.sql(sql);
				sqlbv88.put("RgtNo", RgtNo);
				sqlbv88.put("Customerno", Customerno);
				getData[k + 12] = myExeSQL.getOneValue(sqlbv88);
				/***************************************************************
				 * 其它
				 */
				// 保额 1
				k = k + 9;
				sql = "select StandMoney from lcget where GrpContno='"
						+ Publicstr[0] + "' ";
				sql = sql + " and insuredno='" + "?Customerno?" + "' ";
				sql = sql
						+ " and getdutycode in (select getdutycode from llclaimdetail where clmno='"
						+ "?RgtNo?" + "'";
				sql = sql + "                  and Customerno='" + "?Customerno?"
						+ "'";
				sql = sql
						+ "                  and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='3')";
				sql = sql + " )";
				SQLwithBindVariables sqlbv89 = new SQLwithBindVariables();
				sqlbv89.sql(sql);
				sqlbv89.put("RgtNo", RgtNo);
				sqlbv89.put("Customerno", Customerno);
				getData[k + 4] = myExeSQL.getOneValue(sqlbv89);
				// 申请费用 1
				sql = "select sum(Fee) from llcasereceipt";
				sql = sql + " where clmno='" + "?RgtNo?" + "'";
				sql = sql + " and feeitemtype<>'B' and feeitemtype<>'A' ";
				sql = sql + " and Customerno='" + "?Customerno?" + "'";
				SQLwithBindVariables sqlbv90 = new SQLwithBindVariables();
				sqlbv90.sql(sql);
				sqlbv90.put("RgtNo", RgtNo);
				sqlbv90.put("Customerno", Customerno);
				getData[k + 5] = myExeSQL.getOneValue(sqlbv90);
				// 扣除费用
				sql = "select sum(RefuseAmnt) from llcasereceipt ";
				sql = sql + " where clmno='" + "?RgtNo?" + "'";
				sql = sql + " and feeitemtype='B'  and feeitemtype<>'A'";
				sql = sql + " and Customerno='" + "?Customerno?" + "'";
				SQLwithBindVariables sqlbv91 = new SQLwithBindVariables();
				sqlbv91.sql(sql);
				sqlbv91.put("RgtNo", RgtNo);
				sqlbv91.put("Customerno", Customerno);
				getData[k + 6] = myExeSQL.getOneValue(sqlbv91);
				// 合理费用
				sql = "select sum(AdjSum) from llcasereceipt ";
				sql = sql + " where clmno='" + "?RgtNo?" + "'";
				sql = sql + " and feeitemtype<>'B'  and feeitemtype<>'A'";
				sql = sql + "  and Customerno='" + "?Customerno?" + "'";
				SQLwithBindVariables sqlbv92 = new SQLwithBindVariables();
				sqlbv92.sql(sql);
				sqlbv92.put("RgtNo", RgtNo);
				sqlbv92.put("Customerno", Customerno);
				getData[k + 7] = myExeSQL.getOneValue(sqlbv92);
				// 津贴
				getData[k + 8] = "-";
				// 住院天数 3
				sql = "select sum(daycount) from llcasereceipt ";
				sql = sql + " where  clmno='" + "?RgtNo?" + "'";
				sql = sql + " and feeitemtype<>'B' and feeitemtype<>'A' ";
				sql = sql + " and Customerno='" + "?Customerno?" + "'";
				SQLwithBindVariables sqlbv93 = new SQLwithBindVariables();
				sqlbv93.sql(sql);
				sqlbv93.put("RgtNo", RgtNo);
				sqlbv93.put("Customerno", Customerno);
				getData[k + 9] = myExeSQL.getOneValue(sqlbv93);
				// 本次住院赔付
				sql = "select sum(realpay) from llclaimdetail ";
				sql = sql + " where clmno='" + "?RgtNo?" + "' ";
				sql = sql + "  and Customerno='" + "?Customerno?" + "'";
				sql = sql
						+ " and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='3')";
				SQLwithBindVariables sqlbv94 = new SQLwithBindVariables();
				sqlbv94.sql(sql);
				sqlbv94.put("RgtNo", RgtNo);
				sqlbv94.put("Customerno", Customerno);
				getData[k + 10] = myExeSQL.getOneValue(sqlbv94);
				// 累计住院赔付
				sql = " select sum(realpay) from llclaimdetail ";
				sql = sql + " where  Customerno='" + "?Customerno?" + "'";
				sql = sql
						+ " and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='3')";
				SQLwithBindVariables sqlbv95 = new SQLwithBindVariables();
				sqlbv95.sql(sql);
				sqlbv95.put("Customerno", Customerno);
				getData[k + 11] = myExeSQL.getOneValue(sqlbv95);
				// 扣除原因
				sql = "select AdjRemark from llcasereceipt ";
				sql = sql + " where clmno='" + "?RgtNo?" + "'";
				sql = sql + " and feeitemtype<>'B'  and feeitemtype<>'A'";
				sql = sql + " and Customerno='" + "?Customerno?"
						+ "' and AdjRemark is not null";
				SQLwithBindVariables sqlbv96 = new SQLwithBindVariables();
				sqlbv96.sql(sql);
				sqlbv96.put("RgtNo", RgtNo);
				sqlbv96.put("Customerno", Customerno);
				getData[k + 12] = myExeSQL.getOneValue(sqlbv96);

				/***************************************************************
				 * 合计
				 */
				// 保额 1
				k = k + 9;
				sql = "select StandMoney from lcget where GrpContno='"
						+ "?GrpContno?"+ "' ";
				sql = sql + " and insuredno='" + "?Customerno?" + "' ";
				sql = sql
						+ " and getdutycode in (select getdutycode from llclaimdetail where clmno='"
						+ "?RgtNo?" + "'";
				sql = sql + "                  and Customerno='" + "?Customerno?"
						+ "'";
				sql = sql
						+ "                  and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='4')";
				sql = sql + " )";
				SQLwithBindVariables sqlbv97 = new SQLwithBindVariables();
				sqlbv97.sql(sql);
				sqlbv97.put("GrpContno", Publicstr[0] );
				sqlbv97.put("Customerno", Customerno);
				sqlbv97.put("RgtNo", RgtNo);
				sqlbv97.put("Customerno", Customerno);
				getData[k + 4] = myExeSQL.getOneValue(sqlbv97);
				// 申请费用 1
				getData[k + 5] = "-"; // myExeSQL.getOneValue("select sum(Fee)
				// from llcasereceipt where
				// clmno='"+RgtNo+"' and feeitemtype='B'
				// and Customerno='"+Customerno+"'");
				// 扣除费用
				getData[k + 6] = "-"; // myExeSQL.getOneValue("select
				// sum(RefuseAmnt) from llcasereceipt
				// where clmno='"+RgtNo+"' and
				// feeitemtype='B' and
				// Customerno='"+Customerno+"'");
				// 合理费用
				getData[k + 7] = "-"; // myExeSQL.getOneValue("select
				// sum(AdjSum) from llcasereceipt where
				// clmno='"+RgtNo+"' and feeitemtype='B'
				// and Customerno='"+Customerno+"'");
				// 津贴
				getData[k + 8] = "-";
				// 住院天数
				getData[k + 9] = "-";
				// 本次住院赔付
				sql = "select sum(realpay) from llclaimdetail ";
				sql = sql + " where clmno='" + "?RgtNo?" + "' ";
				sql = sql + "  and Customerno='" + "?Customerno?" + "'";
				sql = sql
						+ " and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='4')";
				SQLwithBindVariables sqlbv98 = new SQLwithBindVariables();
				sqlbv98.sql(sql);
				sqlbv98.put("RgtNo", RgtNo);
				sqlbv98.put("Customerno", Customerno);
				getData[k + 10] = myExeSQL.getOneValue(sqlbv98);
				// 累计住院赔付
				sql = " select sum(realpay) from llclaimdetail ";
				sql = sql + " where  Customerno='" + "?Customerno?" + "'";
				sql = sql
						+ " and concat(getdutycode,getdutykind) in (select concat(code,codename) from reportcode where codealias='4')";
				SQLwithBindVariables sqlbv99 = new SQLwithBindVariables();
				sqlbv99.sql(sql);
				sqlbv99.put("Customerno", Customerno);
				getData[k + 11] = myExeSQL.getOneValue(sqlbv99);
				// 扣除原因
				getData[k + 12] = "-";
				k = k + 9;
				k = k + 4;

			}
			// MU_Template tMU_Template = new MU_Template();
			// if (!tMU_Template.createreport(getData, filename,
			// TemplateFilename, 160, Publicstr)) {
			// state = "生成文件失败！";
			return state;
			// }
		} catch (Exception e) {
			state = "出错误信息：" + e.getMessage();
			logger.debug(state);
			return state;
		}
		// return state;
	}

	// 理赔个人清单，不套模板的方法，此方法可以提高效率
	public String CreatePerBill(String[] data, String filename) {
		String state = "";
		Document document = new Document(PageSize.B5);
		try {
			// 定义字体
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bf, 12, Font.NORMAL);
			Font Font10 = new Font(bf, 10, Font.NORMAL);

			// 生成的PDF文件文件名
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(filename));
			// 页边距的定义
			// 页边距的定义
			// Paragraph paragraph = new Paragraph();
			// paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			/**
			 * **********与mm的换算：1 mm = 2.8346 pt***** 72pt=1 英寸*****************
			 */
			/** **************** 左 右 上 下 ************************ */
			document.setMargins(36, 36, 80, 36);
			String StrLeft = Stringtools.createspace(10);
			Image bmp = Image.getInstance("logo.bmp");
			bmp.setAlignment(Image.LEFT | Image.UNDERLYING);
			document.add(new Paragraph(StrLeft + "TKRS保险股份有限公司", FontChinese));
			document.add(new Paragraph(StrLeft + "团体健康保险理赔清单", FontChinese));
			// 画一条水平线

		} catch (Exception e) {
			state = "出错信息：" + e.getMessage();
		}
		return state;
	}

	// 表格测试
	public static String testtable() {
		String state = "";
		try {
			// 定义字体
			// BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
			// BaseFont.NOT_EMBEDDED);
			BaseFont bf = BaseFont.createFont(
					"c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			Font Font10 = new Font(bf, 12, Font.NORMAL);
			Document document = new Document(PageSize.A4.rotate());
			// 生成的PDF文件文件名
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("d:\\test.pdf"));
			document.setMargins(36, 36, 36, 36);
			document.open();
			document.add(new Paragraph(
					"testtesttesttesttesttesttesttesttesttesttest", Font10));

			float[] widths = { 20f, 20f, 20f, 20f, 20f };

			PdfPTable table = new PdfPTable(widths);

			cell = new PdfPCell(new Phrase("变更内容", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("变更内容", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("变更内容", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("变更内容", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("变更内容", Font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			table.setWidthPercentage(100);
			document.add(table);
			document.close();

		} catch (Exception e) {
		}
		return state;
	}

}
