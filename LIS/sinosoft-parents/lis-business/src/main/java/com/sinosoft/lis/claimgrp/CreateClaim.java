package com.sinosoft.lis.claimgrp;
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
import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Color;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.font.*;
import java.sql.*;

import com.sinosoft.lis.claimgrp.MU_Template;
import com.sinosoft.utility.*;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;

public class CreateClaim {
private static Logger logger = Logger.getLogger(CreateClaim.class);
	public static PdfPCell cell;

	public CreateClaim() {
	}

	// 表格测试
	public static String testtable() {
		String state = "";

		try {
			// 定义字体
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
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

	// 打印理赔批单(文件名，赔案号，管理机构)
	public String CreateClaimBatch(String filename, String RgtNo,
			String ManageCom) {

		String state = "";
		// 控制上边空多少行
		int UpSpace = 5;
		Stringtools.log("", "理赔批单打印：" + filename + "|" + RgtNo + "|"
				+ ManageCom, "");
		Document document = new Document(PageSize.B5);

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
			/** **********与mm的换算：1 mm = 2.8346 pt***** 72pt=1 英寸***************** */
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
			String GrpContNo = my
					.getOneValue("select GrpContNo from llregister where rgtno='"
							+ RgtNo + "'");
			String InsuredName = my
					.getOneValue("select Name from lcinsured where GrpContNo='"
							+ GrpContNo
							+ "' and InsuredNO in (select InsuredNO from   LLClaimpolicy where  ClmNo='"
							+ RgtNo
							+ "' and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))");
			// 人数
			String CountInsured = my
					.getOneValue("select count(*) from (select unique InsuredNO from LLClaimpolicy where  ClmNo='"
							+ RgtNo
							+ "' and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))");

			// 全部保险责任形成一个串
			String Duty = "";
			// 全部保单号开成一个串
			String StrGrpContNO = "";
			// 数据库连接
			ExeSQL myExeSQL = new ExeSQL();
			try {
				String Sql = "select unique trim(b.CodeName)  GetDutyName   ";
				Sql = Sql + " from LLClaimDetail a,ldcode b  ";
				Sql = Sql + "  where a.RealPay>0 and b.codetype='getdutykind' ";
				Sql = Sql
						+ "  and a.GetDutyKind=b.code    and a.RgtNo='"
						+ RgtNo
						+ "'  and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2')";
				SSRS tSSRS = myExeSQL.execSQL(Sql);
				if (tSSRS == null) {
					state = "2--打印理赔批单时，数据库连接失败!";
					Stringtools.log("", state, "");
					return state;
				}
				Stringtools.log("", Sql, "");

				int tmp_i = 0;
				for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
					String tmp = tSSRS.GetText(tmpi, 1); // .getString(1);
					if (tmp == null) {
						tmp = "";
					}
					if (tmp_i == 0) {
						Duty = Duty + "   " + tmp.trim();
					} else {
						Duty = Duty + "、" + tmp.trim();
					}
					tmp_i++;

				}
			} catch (Exception e) {
				state = "1--打印理赔批单时，数据库连接出错!" + e.getMessage();
				Stringtools.log("", state, "");
				return state;
			}
			// 数据库连接
			try {
				String Sql = "select unique grpcontno from lcpol  where PolNo in (select unique polno from llclaimpolicy  where  RgtNo='"
						+ RgtNo
						+ "'  and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))";
				SSRS tSSRS = myExeSQL.execSQL(Sql);
				if (tSSRS == null) {
					state = "2--打印理赔批单时，数据库连接失败!";
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
			} catch (Exception e) {
				state = "2--打印理赔批单时，数据库连接出错!" + e.getMessage();
				Stringtools.log("", state, "");
				return state;
			}

			// 计算总共的赔付金额
			String RealPay = my
					.getOneValue("select sum(RealPay) from LLClaim where RgtNo='"
							+ RgtNo + "'   and (GiveType='0' or GiveType='4')");
			RealPay = "\u00A5" + Stringtools.stringtostring(RealPay, 2);
			// 主管及经办：
			String ClmUWer = my
					.getOneValue("select b.UserName from LLClaim a,lduser b where  a.ClmUWer=b.UserCode and a.CaseNo='"
							+ RgtNo
							+ "' and  (a.GiveType='0' or a.GiveType='4')");
			String Operator = my
					.getOneValue("select b.UserName from LLRegister a,LDUser b where a.Operator=b.UserCode and a.RgtNo='"
							+ RgtNo + "'");
			String EndCaseDate = my
					.getOneValue("select to_char(EndCaseDate,'yyyy-mm-dd') from LLRegister where RgtNo='"
							+ RgtNo + "' ");
			// 数据准备结束，开始写批单的内容
			document.add(new Paragraph("赔案编号：" + RgtNo, FontChinese));
			int tmpcount = Stringtools.stringtoint(CountInsured);
			if (tmpcount > 1) {
				document.add(new Paragraph("被保险人：  " + InsuredName.trim()
						+ " 等 " + CountInsured + " 人", FontChinese));
			}
			if (tmpcount == 1) {
				document.add(new Paragraph("被保险人：  " + InsuredName.trim(),
						FontChinese));
			}

			document.add(new Paragraph("批改日期：" + EndCaseDate, FontChinese));
			document.add(new Paragraph(" ", FontChinese));
			// 判断是否有死亡
			String DeadStr = "";
			if (Duty.indexOf("死亡") >= 0 || Duty.indexOf("身故") >= 0) {
				DeadStr = "，以上被保险人责任终止。";
			} else {
				DeadStr = "。";

			}

			String GiveType = "";
			GiveType = my
					.getOneValue("select givetype from llclaimpolicy where clmno='"
							+ RgtNo + "' and rownum<=1");

			if (GiveType.equals("0")) {
				document.add(new Paragraph(Stringtools.createspace(9)
						+ "以上被保险人于保险有效期内，因 " + Duty
						+ " 所引起的保险事故，经本公司审核属于保险责任，按 " + StrGrpContNO
						+ " 保险合同规定给付理赔保险金 " + RealPay + " 元" + DeadStr,
						FontChinese));
			} else if (GiveType.equals("2")) {
				document.add(new Paragraph(Stringtools.createspace(9)
						+ "以上被保险人提出的索赔申请，经本公司审核不属于保险责任，考虑到该案件的实际情况符合我公司的通融原因，"
						+ "我公司同意通融赔付保险金 " + RealPay + " 元" + DeadStr,
						FontChinese));
			} else if (GiveType.equals("3")) {
				document.add(new Paragraph(Stringtools.createspace(9)
						+ "以上被保险人提出的索赔申请，经本公司审核不属于保险责任，考虑到该案件的实际情况,经与受益人协商，"
						+ "我公司同意协议赔付保险金 " + RealPay + " 元" + DeadStr,
						FontChinese));
			}
			for (int i = 1; i <= 3; i++) {
				document.add(new Paragraph("   ", FontChinese));
			}
			document.add(new Paragraph(Stringtools.createspace(90) + "特此批改",
					FontChinese));
			for (int i = 1; i <= 5; i++) {
				document.add(new Paragraph("   ", FontChinese));
			}
			document.add(new Paragraph(Stringtools.createspace(40) + "主管："
					+ ClmUWer + Stringtools.createspace(30) + "经办：" + Operator,
					FontChinese));
			document.close(); // 写PDF文件结束

		} catch (Exception ex) {
			Stringtools.log("", "CreateClaimBatch.java===>" + ex.getMessage(),
					"");
			state = "业务程序处理出错！" + ex.getMessage();
		}
		return state;
	}

	/** ***********************************8理赔赔款清单************************************************** */
	// 文件名，赔案号，管理机构
	public String CreatePayBill(String filename, String RgtNo, String ManageCom) {
		String state = "";
		int UpSpace = 1;
		Stringtools.log("", "赔款清单：" + filename + "|" + RgtNo + "|" + ManageCom,
				"");
		// A4 横放
		Document document = new Document(PageSize.A4);
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
			/** **********与mm的换算：1 mm = 2.8346 pt***** 72pt=1 英寸***************** */
			/** **************** 左 右 上 下 ************************ */
			document.setMargins(36, 36, 36, 36);
			document.open();
			for (int i = 1; i <= UpSpace; i++) {
				document.add(new Paragraph("   ", FontChinese));
			}

			ExeSQL my = new ExeSQL();
			document.add(new Paragraph(Stringtools.createspace(85)
					+ "理 赔 赔 款 清 单", FontChinese));
			document.add(new Paragraph(" ", FontChinese));
			// 取投保单位
			String CustomerName = my
					.getOneValue("select GrpName from LLRegister where RgtNo='"
							+ RgtNo
							+ "'  and EndCaseFlag='1' and RgtNo not in (select RgtNo from LLClaimpolicy where RgtNo='"
							+ RgtNo + "'  and GiveType ='1')");
			// 取保单号
			String GrpcontNo = my
					.getOneValue("select GrpContNo from LLRegister where RgtNo='"
							+ RgtNo
							+ "'  and EndCaseFlag='1' and RgtNo not in (select RgtNo from LLClaimpolicy where RgtNo='"
							+ RgtNo + "'  and GiveType ='1')");
			document.add(new Paragraph("投保单位： " + CustomerName, FontChinese));
			document.add(new Paragraph("保单号： " + GrpcontNo, FontChinese));
			document.add(new Paragraph("赔案号： " + RgtNo, FontChinese));
			document.add(new Paragraph("    ", FontChinese));
			logger.debug("GrpcontNo=" + GrpcontNo);
			// 针对广州本田单子的专门处理，增加字段
			if (GrpcontNo.equals(new String("230101077373"))) {

				// 初始化表格
				float[] widths = { 8f, 8f, 14f, 8f, 8f, 6f, 10f, 12f, 12f };
				PdfPTable table = new PdfPTable(widths);
				// 开始写表头
				cell = new PdfPCell(new Phrase("员工编号", Font10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("姓名", Font10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("部门/科室", Font10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("内诊比例", Font10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("外诊比例", Font10));
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

				// 数据准备SQL文
				String Sql = "select a.CustomerNo,a.RiskCode,sum(a.amnt),sum(a.realpay),b.othidno,b.department,b.number1,b.number2";
				Sql = Sql + "    from LLClaimDetail a,LDpersonrider b";
				Sql = Sql
						+ " where  (GiveType='0' or GiveType='2' or GiveType='3' ) and a.customerno=b.customerno "; // 不是拒赔
				// 判断是否结案
				Sql = Sql + " and 1=1 ";
				Sql = Sql + " and a.ClmNo='" + RgtNo + "'";
				Sql = Sql
						+ " group by a.CustomerNo,a.RiskCode,b.customerno,b.othidno,b.department,b.number1,b.number2 ";
				Stringtools.log("", "赔款清单 " + Sql, "");
				ExeSQL myExeSQL = new ExeSQL();
				// 数据库连接
				try {
					SSRS tSSRS = myExeSQL.execSQL(Sql);
					if (tSSRS == null) {
						state = "打印赔款清单的数据库连接时出错,数据库连接失败!";
						Stringtools.log("", state, "");
						return state;
					}
					// 变量定义
					String InsuredNo = "";
					String InsuredName = "";
					// 保额
					String Amnt = "";
					// 本次赔付
					double dRealPay = 0.00;
					String RealPay = "";
					// 累计赔付金额
					String ljPay = "";
					// 备注
					String AdjRemark = "";
					// 险种
					String RiskCode = "";
					// 员工编号
					String WorkNo = "";
					// 部门/科室
					String Department = "";
					// 内诊比例
					String nzbi = "";
					// 外诊比例
					String wzbi = "";
					for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
						InsuredNo = tSSRS.GetText(tmpi, 1); // .getString(1);
						if (InsuredNo == null) {
							InsuredNo = "";
							state = "打印赔清单时，被保险人客号为空值!赔案号" + RgtNo;
							Stringtools.log("", state, "");
							return state;
						}
						InsuredName = my
								.getOneValue("select name from LCInsured where grpcontno='"
										+ GrpcontNo
										+ "' and InsuredNO='"
										+ InsuredNo + "'");
						if (InsuredName == null) {
							InsuredName = "";
							state = "打印赔清单时，被保险人姓名为空值!赔案号" + RgtNo + "客户号"
									+ InsuredNo;
							Stringtools.log("", state, "");
							return state;
						}
						WorkNo = tSSRS.GetText(tmpi, 5);
						Department = tSSRS.GetText(tmpi, 6);
						nzbi = tSSRS.GetText(tmpi, 7);
						wzbi = tSSRS.GetText(tmpi, 8);
						RiskCode = tSSRS.GetText(tmpi, 2); // rs.getString(2);
						Amnt = my
								.getOneValue("select amnt from lcpol where GrpcontNo='"
										+ GrpcontNo
										+ "' and riskcode='"
										+ RiskCode
										+ "' and InsuredNo='"
										+ InsuredNo + "'"); // Stringtools.stringtostring(rs.getString(3),2);
						RealPay = Stringtools.stringtostring(tSSRS.GetText(
								tmpi, 4), 2);

						// 员工编号
						cell = new PdfPCell(new Phrase(WorkNo, Font10));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						// 姓名
						cell = new PdfPCell(new Phrase(InsuredName, Font10));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						// 部门/科室
						cell = new PdfPCell(new Phrase(Department, Font10));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						// 内诊比例
						cell = new PdfPCell(new Phrase(nzbi, Font10));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						// 外诊比例
						cell = new PdfPCell(new Phrase(wzbi, Font10));
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
						dRealPay = dRealPay
								+ Stringtools.stringtodouble(RealPay);

						String tmpsql = "select sum(realpay) from LLClaimDetail where GRpContno='"
								+ GrpcontNo
								+ "' and CustomerNo='"
								+ InsuredNo
								+ "'  and RiskCode='" + RiskCode + "'";
						tmpsql = tmpsql
								+ "   and RgtNo in (select RgtNo from  LLRegister ";
						tmpsql = tmpsql
								+ " where EndCaseFlag='1' and clmstate!='80' and EndCaseDate is not null  )";

						// 累计赔付金额
						ljPay = my.getOneValue(tmpsql);
						tmpsql = "";
						ljPay = Stringtools.stringtostring(ljPay, 2);
						cell = new PdfPCell(new Phrase(ljPay, Font10));
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell);

					} // 循环结束
					table.setWidthPercentage(100);
					document.add(table);

					document
							.add(new Paragraph("本次赔案理赔金额合计:"
									+ Stringtools.stringtostring(Stringtools
											.doubletostring(dRealPay), 2),
									FontChinese));
					document.add(new Paragraph("  ", FontChinese));
					document.add(new Paragraph("  ", FontChinese));

					document.add(new Paragraph(Stringtools.createspace(100)
							+ "泰康人寿保险股份有限公司", FontChinese));
					document.add(new Paragraph(Stringtools.createspace(120)
							+ Stringtools.longtoday(), FontChinese));
					/** **************************开始打印费用清单**************************************************************** */

					// 分页符
					document.newPage();
					for (int i = 1; i <= UpSpace; i++) {
						document.add(new Paragraph("   ", FontChinese));
					}

					document.add(new Paragraph(Stringtools.createspace(85)
							+ " 费 用 清 单 ", FontChinese));
					document.add(new Paragraph(" ", FontChinese));
					document.add(new Paragraph("投保单位： " + CustomerName,
							FontChinese));
					document
							.add(new Paragraph("保单号： " + GrpcontNo, FontChinese));
					document.add(new Paragraph("赔案号： " + RgtNo, FontChinese));
					document.add(new Paragraph("    ", FontChinese));

					// 初始化表格
					float[] widths1 = { 10f, 10f, 10f, 8f, 8f, 8f, 8f, 8f, 8f,
							30f };
					table = new PdfPTable(widths1);
					// 开始写表头
					cell = new PdfPCell(new Phrase("员工编号", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("姓名", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("部门/科室", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("帐单号", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("费用类别", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("费用项目", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("申请金额", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase("扣除费用", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("合理费用", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("扣除原因", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					String SQL = "select a.CustomerNo,a.MainFeeNo,a.feeitemtype,a.FeeItemName,a.Fee,a.RefuseAmnt,a.AdjSum,a.AdjRemark,b.othidno,b.department from LLCaseReceipt a,LDPersonRider b where a.ClmNo='"
							+ RgtNo
							+ "' and  a.mainfeeno <> '0000000000'  and a.customerno=b.customerno order by  a.CustomerNo";

					try {
						tSSRS = myExeSQL.execSQL(SQL);
						if (tSSRS == null) {
							state = "打印赔款清单的数据库连接时出错,数据库连接失败!";
							Stringtools.log("", state, "");
							return state;
						}

						if (tSSRS == null) {
							String ErrMsg = "打印理赔费用清单时出错!!";
							logger.debug(ErrMsg);
							Stringtools.log("", ErrMsg, "");
						} else {
							String insuredNo = "";
							String Name = "";
							String feeitemtype = "";
							String feeitemname = "";
							for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
								// 员工编号
								InsuredNo = tSSRS.GetText(tmpi, 1); // .getString(1);
								WorkNo = tSSRS.GetText(tmpi, 9);
								cell = new PdfPCell(new Phrase(WorkNo, Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);

								// 姓名
								Name = my
										.getOneValue("select name from LCInsured where GrpContNo='"
												+ GrpcontNo
												+ "'  and InsuredNO='"
												+ InsuredNo + "'");
								cell = new PdfPCell(new Phrase(Name, Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);

								// 部门/科室
								Department = tSSRS.GetText(tmpi, 10);
								cell = new PdfPCell(new Phrase(Department,
										Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);

								// 帐单号
								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 2), Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);

								// 费用类别(A-门诊B-住院)
								feeitemtype = tSSRS.GetText(tmpi, 3); // rs.getString(3);
								if (feeitemtype == null) {
									feeitemtype = "";
								}
								feeitemname = "其它";
								if (Stringtools.stringequals(feeitemtype, "A")) {
									feeitemname = feeitemtype.trim() + "--门诊";
								}
								if (Stringtools.stringequals(feeitemtype, "B")) {
									feeitemname = feeitemtype.trim() + "--住院";
								}
								cell = new PdfPCell(new Phrase(feeitemname,
										Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);

								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 4), Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);
								// 申请金额
								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 5), Font10));
								cell
										.setHorizontalAlignment(Element.ALIGN_RIGHT);
								table.addCell(cell);
								// 扣除费用
								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 6), Font10));
								cell
										.setHorizontalAlignment(Element.ALIGN_RIGHT);
								table.addCell(cell);
								// 合理费用
								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 7), Font10));
								cell
										.setHorizontalAlignment(Element.ALIGN_RIGHT);
								table.addCell(cell);
								// 扣除原因
								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 8), Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);
							}
							table.setWidthPercentage(105);
							document.add(table);
						}

					} catch (Exception e) {
						String ErrMsg = "打印理赔费用清单时出错!!" + e.getMessage();
						logger.debug(ErrMsg);
						Stringtools.log("", ErrMsg, "");
					}
				} catch (Exception e) {
					state = "打印赔款清单的数据库连接时出错！" + e.getMessage();
					Stringtools.log("", state, "");
					return state;
				}

			}
			// 针对广州本田单子的专门处理结束
			else {

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

				// 数据准备SQL文
				String Sql = "select CustomerNo,RiskCode,sum(amnt),sum(realpay)  from LLClaimDetail";
				Sql = Sql
						+ " where  (GiveType='0' or GiveType='2' or GiveType='3' )  "; // 不是拒赔
				// 判断是否结案
				Sql = Sql + " and 1=1 ";
				Sql = Sql + " and ClmNo='" + RgtNo + "'";
				Sql = Sql + " group by CustomerNo,RiskCode ";
				Stringtools.log("", "赔款清单 " + Sql, "");
				ExeSQL myExeSQL = new ExeSQL();
				// 数据库连接
				try {
					SSRS tSSRS = myExeSQL.execSQL(Sql);
					if (tSSRS == null) {
						state = "打印赔款清单的数据库连接时出错,数据库连接失败!";
						Stringtools.log("", state, "");
						return state;
					}
					// 变量定义
					String InsuredNo = "";
					String InsuredName = "";
					// 保额
					String Amnt = "";
					// 本次赔付
					double dRealPay = 0.00;
					String RealPay = "";
					// 累计赔付金额
					String ljPay = "";
					// 备注
					String AdjRemark = "";
					// 险种
					String RiskCode = "";
					for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
						InsuredNo = tSSRS.GetText(tmpi, 1); // .getString(1);
						if (InsuredNo == null) {
							InsuredNo = "";
							state = "打印赔清单时，被保险人客号为空值!赔案号" + RgtNo;
							Stringtools.log("", state, "");
							return state;
						}
						InsuredName = my
								.getOneValue("select name from LCInsured where grpcontno='"
										+ GrpcontNo
										+ "' and InsuredNO='"
										+ InsuredNo + "'");
						if (InsuredName == null) {
							InsuredName = "";
							state = "打印赔清单时，被保险人姓名为空值!赔案号" + RgtNo + "客户号"
									+ InsuredNo;
							Stringtools.log("", state, "");
							return state;
						}
						RiskCode = tSSRS.GetText(tmpi, 2); // rs.getString(2);
						Amnt = my
								.getOneValue("select amnt from lcpol where GrpcontNo='"
										+ GrpcontNo
										+ "' and riskcode='"
										+ RiskCode
										+ "' and InsuredNo='"
										+ InsuredNo + "'"); // Stringtools.stringtostring(rs.getString(3),2);
						RealPay = Stringtools.stringtostring(tSSRS.GetText(
								tmpi, 4), 2);

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
						dRealPay = dRealPay
								+ Stringtools.stringtodouble(RealPay);

						String tmpsql = "select sum(realpay) from LLClaimDetail where GRpContno='"
								+ GrpcontNo
								+ "' and CustomerNo='"
								+ InsuredNo
								+ "'  and RiskCode='" + RiskCode + "'";
						tmpsql = tmpsql
								+ "   and RgtNo in (select RgtNo from  LLRegister ";
						tmpsql = tmpsql
								+ " where EndCaseFlag='1' and clmstate!='80' and EndCaseDate is not null  )";

						// 累计赔付金额
						ljPay = my.getOneValue(tmpsql);
						tmpsql = "";
						ljPay = Stringtools.stringtostring(ljPay, 2);
						cell = new PdfPCell(new Phrase(ljPay, Font10));
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell);

					} // 循环结束
					table.setWidthPercentage(100);
					document.add(table);

					document
							.add(new Paragraph("本次赔案理赔金额合计:"
									+ Stringtools.stringtostring(Stringtools
											.doubletostring(dRealPay), 2),
									FontChinese));
					document.add(new Paragraph("  ", FontChinese));
					document.add(new Paragraph("  ", FontChinese));

					document.add(new Paragraph(Stringtools.createspace(100)
							+ "泰康人寿保险股份有限公司", FontChinese));
					document.add(new Paragraph(Stringtools.createspace(120)
							+ Stringtools.longtoday(), FontChinese));
					/** **************************开始打印费用清单**************************************************************** */

					// 分页符
					document.newPage();
					for (int i = 1; i <= UpSpace; i++) {
						document.add(new Paragraph("   ", FontChinese));
					}

					document.add(new Paragraph(Stringtools.createspace(85)
							+ " 费 用 清 单 ", FontChinese));
					document.add(new Paragraph(" ", FontChinese));
					document.add(new Paragraph("投保单位： " + CustomerName,
							FontChinese));
					document
							.add(new Paragraph("保单号： " + GrpcontNo, FontChinese));
					document.add(new Paragraph("赔案号： " + RgtNo, FontChinese));
					document.add(new Paragraph("    ", FontChinese));

					// 初始化表格
					float[] widths1 = { 10f, 10f, 8f, 8f, 8f, 8f, 8f, 8f, 30f };
					table = new PdfPTable(widths1);
					// 开始写表头
					cell = new PdfPCell(new Phrase("个人客户号", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("姓名", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("帐单号", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("费用类别", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("费用项目", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("申请金额", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase("扣除费用", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("合理费用", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("扣除原因", Font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					String SQL = "select CustomerNo,MainFeeNo,feeitemtype,FeeItemName,Fee,RefuseAmnt,AdjSum,AdjRemark from LLCaseReceipt where ClmNo='"
							+ RgtNo
							+ "' and  mainfeeno <> '0000000000' order by  CustomerNo";

					try {
						tSSRS = myExeSQL.execSQL(SQL);
						if (tSSRS == null) {
							state = "打印赔款清单的数据库连接时出错,数据库连接失败!";
							Stringtools.log("", state, "");
							return state;
						}

						if (tSSRS == null) {
							String ErrMsg = "打印理赔费用清单时出错!!";
							logger.debug(ErrMsg);
							Stringtools.log("", ErrMsg, "");
						} else {
							String insuredNo = "";
							String Name = "";
							String feeitemtype = "";
							String feeitemname = "";
							for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
								// 客户号
								InsuredNo = tSSRS.GetText(tmpi, 1); // .getString(1);
								cell = new PdfPCell(new Phrase(InsuredNo,
										Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);

								// 姓名
								Name = my
										.getOneValue("select name from LCInsured where GrpContNo='"
												+ GrpcontNo
												+ "'  and InsuredNO='"
												+ InsuredNo + "'");
								cell = new PdfPCell(new Phrase(Name, Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);

								// 帐单号
								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 2), Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);

								// 费用类别(A-门诊B-住院)
								feeitemtype = tSSRS.GetText(tmpi, 3); // rs.getString(3);
								if (feeitemtype == null) {
									feeitemtype = "";
								}
								feeitemname = "其它";
								if (Stringtools.stringequals(feeitemtype, "A")) {
									feeitemname = feeitemtype.trim() + "--门诊";
								}
								if (Stringtools.stringequals(feeitemtype, "B")) {
									feeitemname = feeitemtype.trim() + "--住院";
								}
								cell = new PdfPCell(new Phrase(feeitemname,
										Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);

								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 4), Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);
								// 申请金额
								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 5), Font10));
								cell
										.setHorizontalAlignment(Element.ALIGN_RIGHT);
								table.addCell(cell);
								// 扣除费用
								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 6), Font10));
								cell
										.setHorizontalAlignment(Element.ALIGN_RIGHT);
								table.addCell(cell);
								// 合理费用
								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 7), Font10));
								cell
										.setHorizontalAlignment(Element.ALIGN_RIGHT);
								table.addCell(cell);
								// 扣除原因
								cell = new PdfPCell(new Phrase(tSSRS.GetText(
										tmpi, 8), Font10));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								table.addCell(cell);
							}
							table.setWidthPercentage(105);
							document.add(table);
						}

					} catch (Exception e) {
						String ErrMsg = "打印理赔费用清单时出错!!" + e.getMessage();
						logger.debug(ErrMsg);
						Stringtools.log("", ErrMsg, "");
					}
				} catch (Exception e) {
					state = "打印赔款清单的数据库连接时出错！" + e.getMessage();
					Stringtools.log("", state, "");
					return state;
				}
			}
			document.add(new Paragraph("  ", FontChinese));
			document.add(new Paragraph("  ", FontChinese));
			document.add(new Paragraph("  ", FontChinese));
			document.add(new Paragraph(Stringtools.createspace(100)
					+ "泰康人寿保险股份有限公司", FontChinese));
			document.add(new Paragraph(Stringtools.createspace(120)
					+ Stringtools.longtoday(), FontChinese));

			/** **************************结束打印费用清单**************************************************************** */

			document.close();

		} catch (Exception e) {
			state = "打印理赔赔款清单出错！" + e.getMessage();
			Stringtools.log("", state, "");
			return state;
		}
		return state;
	}

	// 打印理赔支付清单===>无模板打印===>(文件名,赔案号,管理机构)
	public static String CreateOutBill(String filename, String RgtNo,
			String ManageCom) {
		String state = "";
		int UpSpace = 5;
		Stringtools.log("", "支付清单：" + filename + "|" + RgtNo + "|" + ManageCom,
				"");
		// *************************************A4
		// 横放**********************************/
		try {
			// 定义字体
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bf, 12, Font.NORMAL);
			Font Font10 = new Font(bf, 10, Font.NORMAL);
			// Document document = new Document(PageSize.A4.rotate());
			Document document = new Document(PageSize.A4);

			// 生成的PDF文件文件名
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(filename));

			// 页边距的定义
			// 页边距的定义
			// Paragraph paragraph = new Paragraph();
			// paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
			/** **********与mm的换算：1 mm = 2.8346 pt***** 72pt=1 英寸***************** */
			/** **************** 左 右 上 下 ************************ */
			document.setMargins(36, 36, 36, 36);
			document.open();
			UpSpace = 1;
			for (int i = 1; i <= UpSpace; i++) {
				document.add(new Paragraph("   ", FontChinese));
			}

			ExeSQL my = new ExeSQL();
			document.add(new Paragraph(Stringtools.createspace(80)
					+ "理 赔 支 付 清 单", FontChinese));
			document.add(new Paragraph(" ", FontChinese));
			// 取投保单位
			String CustomerName = my
					.getOneValue("select GrpName from LLRegister where RgtNo='"
							+ RgtNo + "' and rownum<=1");
			// 取保单号
			String GrpcontNo = my
					.getOneValue("select GrpContNo from LLRegister where RgtNo='"
							+ RgtNo + "'  and rownum<=1");
			document.add(new Paragraph("投保单位： " + CustomerName, FontChinese));
			document.add(new Paragraph("保单号： " + GrpcontNo, FontChinese));
			document.add(new Paragraph("赔案号： " + RgtNo, FontChinese));
			document.add(new Paragraph("    ", FontChinese));

			// 初始化表格
			float[] widths = { 10f, 8f, 13f, 10f, 8f, 13f, 14f };
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

			// 数据准备SQL文
			String Sql = "select  CustomerNo,sum(realpay)";
			Sql = Sql + " from LLClaimDetail ";
			Sql = Sql + " where   ";
			Sql = Sql + " (GiveType='0' or givetype='2' or givetype='3') "; // 不是拒赔
			// 判断是否结案
			Sql = Sql + " and  clmno in (select RgtNo from  LLRegister ";
			Sql = Sql + " where EndCaseDate is not null ";
			Sql = Sql + " and EndCaseFlag='1' ";
			Sql = Sql + " and RgtNo='" + RgtNo + "')";
			Sql = Sql + " and ClmNo='" + RgtNo + "'";
			Sql = Sql + "   group by CustomerNo";

			Stringtools.log("", Sql, "");
			ExeSQL myExeSQL = new ExeSQL();
			// 数据库连接
			try {
				SSRS tSSRS = myExeSQL.execSQL(Sql);
				if (tSSRS == null) {
					state = "打印赔款清单的数据库连接时出错,数据库连接失败!";
					Stringtools.log("", state, "");
					return state;
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
				for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
					InsuredNo = tSSRS.GetText(tmpi, 1); // .getString(1);
					if (InsuredNo == null) {
						InsuredNo = "";
						state = "打印理赔支付清单时，被保险人客号为空值!赔案号" + RgtNo;
						Stringtools.log("", state, "");
						return state;
					}
					cell = new PdfPCell(new Phrase(InsuredNo, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					InsuredName = my
							.getOneValue("select trim(Name) from LCInsured where GrpContNo='"
									+ GrpcontNo
									+ "' and  Insuredno='"
									+ InsuredNo + "'");
					if (InsuredName == null) {
						InsuredName = "";
						state = "打印赔清单时，被保险人姓名为空值!赔案号" + RgtNo + "客户号"
								+ InsuredNo;
						Stringtools.log("", state, "");
						return state;
					}
					cell = new PdfPCell(new Phrase(InsuredName, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					IDNo = my
							.getOneValue("select trim(IDNO) from LDPerson where CustomerNo='"
									+ InsuredNo + "'");
					cell = new PdfPCell(new Phrase(IDNo, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					RealPay = Stringtools.stringtostring(
							tSSRS.GetText(tmpi, 2), 2);
					cell = new PdfPCell(new Phrase(RealPay, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
					dRealPay = dRealPay + Stringtools.stringtodouble(RealPay);

					// 收款人
					// AccName=my.getOneValue("select AccName from LCInsured
					// where GrpContNo='"+GrpcontNo+ "' and
					// InsuredNo='"+InsuredNo+"'");
					AccName = my
							.getOneValue("select Name from LLAccount  where GrpContNo='"
									+ GrpcontNo
									+ "' and InsuredNo='"
									+ InsuredNo + "'");
					cell = new PdfPCell(new Phrase(AccName, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					// 银行代码
					// BankNo=my.getOneValue("select BankAccNo from LCInsured
					// where GrpContNo='"+GrpcontNo+ "' and
					// InsuredNo='"+InsuredNo+"'");
					BankNo = my
							.getOneValue("select BankCode from LLAccount  where GrpContNo='"
									+ GrpcontNo
									+ "' and InsuredNo='"
									+ InsuredNo + "'");

					if (BankNo == null) {
						BankNo = "";
					}
					String tmpsql = "select codename from ldcode  where codetype='bank'  and code='"
							+ BankNo + "'";
					// 银行
					BankName = my.getOneValue(tmpsql);
					tmpsql = "";
					cell = new PdfPCell(new Phrase(BankName, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					// 帐号
					BankAccNo = my
							.getOneValue("select Account from LLAccount where GrpContNo='"
									+ GrpcontNo
									+ "' and InsuredNo='"
									+ InsuredNo + "'");
					if (BankAccNo == null) {
						BankAccNo = "";
					}
					cell = new PdfPCell(new Phrase(BankAccNo, Font10));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

				} // 循环结束
				table.setWidthPercentage(105);
				document.add(table);

				document.add(new Paragraph("本次赔案理赔金额合计:"
						+ Stringtools.stringtostring(Stringtools
								.doubletostring(dRealPay), 2), FontChinese));
				document.add(new Paragraph("  ", FontChinese));
				document.add(new Paragraph("  ", FontChinese));

				document.add(new Paragraph(Stringtools.createspace(120)
						+ "泰康人寿保险股份有限公司", FontChinese));
				document.add(new Paragraph(Stringtools.createspace(140)
						+ Stringtools.longtoday(), FontChinese));
				document.close();

			} catch (Exception e) {
				state = "打印理赔支付清单的数据库连接时出错！" + e.getMessage();
				Stringtools.log("", state, "");
				return state;
			}

		} catch (Exception e) {
			state = "打印理赔支付清单出错！" + e.getMessage();
			Stringtools.log("", state, "");
			return state;
		}

		return state;
	}

	/** *******************************理赔支付清单生成excel文件******************************************************** */
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
		Sql = Sql + " and RgtNo='" + RgtNo + "')";
		Sql = Sql + " and ClmNo='" + RgtNo + "'";
		

		ExeSQL my = new ExeSQL();
		tmpcount = Stringtools.stringtoint(my.getOneValue(Sql));
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
		String CustomerName = my
				.getOneValue("select GrpName from LLRegister where RgtNo='"
						+ RgtNo + "' and rownum<=1 ");
		// 取保单号
		String GrpcontNo = my
				.getOneValue("select GrpContNo from LLRegister where RgtNo='"
						+ RgtNo + "'  and rownum<=1");

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

//		 数据准备SQL文
		Sql = "select  CustomerNo,sum(realpay)";
		Sql = Sql + " from LLClaimDetail ";
		Sql = Sql + " where   ";
		Sql = Sql + " (GiveType='0' or givetype='2' or givetype='3') "; // 不是拒赔
		// 判断是否结案
		Sql = Sql + " and  clmno in (select RgtNo from  LLRegister ";
		Sql = Sql + " where EndCaseDate is not null ";
		Sql = Sql + " and EndCaseFlag='1' ";
		Sql = Sql + " and RgtNo='" + RgtNo + "')";
		Sql = Sql + " and ClmNo='" + RgtNo + "'";
		Sql = Sql + "   group by CustomerNo";

		Stringtools.log("", Sql, "");
		ExeSQL myExeSQL = new ExeSQL();
		// 数据库连接
		try {
			SSRS tSSRS = myExeSQL.execSQL(Sql);
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
				InsuredName = my
						.getOneValue("select trim(Name) from LDPerson where CustomerNo='"
								+ InsuredNo + "'");
				if (InsuredName == null) {
					InsuredName = "";
					data[0] = "0";
					data[1] = "打印赔清单时，被保险人姓名为空值!赔案号" + RgtNo + "客户号"
							+ InsuredNo;
					Stringtools.log("", data[1], "");
					return data;
				}
				data[k + 1] = InsuredName;

				IDNo = my
						.getOneValue("select trim(IDNO) from LDPerson where CustomerNo='"
								+ InsuredNo + "'");
				data[k + 2] = IDNo;

				RealPay = Stringtools.stringtostring(tSSRS.GetText(tmpi, 2), 2);
				data[k + 3] = "double" + RealPay;
				AccName = my
						.getOneValue("select Name from LLAccount where GrpContNo='"
								+ GrpcontNo
								+ "' and InsuredNo='"
								+ InsuredNo
								+ "'");
				data[k + 4] = AccName;

				// 银行代码
				// BankNo=my.getOneValue("select BankAccNo from LCInsured where
				// GrpContNo='"+GrpcontNo+ "' and InsuredNo='"+InsuredNo+"'");
				BankNo = my
						.getOneValue("select BankCode from LLAccount  where GrpContNo='"
								+ GrpcontNo
								+ "' and InsuredNo='"
								+ InsuredNo
								+ "'");

				if (BankNo == null) {
					BankNo = "";
				}

				// 银行中文
				String tmpsql = "select codename from ldcode  where codetype='bank'  and code='"
						+ BankNo + "'";
				BankName = my.getOneValue(tmpsql);
				tmpsql = "";
				data[k + 5] = BankName;

				// BankAccNo=my.getOneValue("select BankAccNo from LCInsured
				// where GrpContNo='"+GrpcontNo+ "' and
				// InsuredNo='"+InsuredNo+"'");
				BankAccNo = my
						.getOneValue("select Account from LLAccount where GrpContNo='"
								+ GrpcontNo
								+ "' and InsuredNo='"
								+ InsuredNo
								+ "'");
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
		String ClmState = my
				.getOneValue("select ClmState from llregister where RgtNo='"
						+ RgtNo + "'");
		if (ClmState == null) {
			ClmState = "0";
		}
		if (Stringtools.stringtoint(ClmState) >= 80) {
			return true; // 拒赔
		} else {
			return false; // 赔付
		}
	}

	/** ***********************************************888拒赔批单(理赔决定通知书)****************************************************** */
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
			/** **********与mm的换算：1 mm = 2.8346 pt***** 72pt=1 英寸***************** */
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
			String GrpcontNo = my
					.getOneValue("select GrpContno from llregister where rgtno='"
							+ RgtNo + "'");
			String InsuredName = my
					.getOneValue("select Name from lcinsured where GrpContNo='"
							+ GrpcontNo
							+ "' and InsuredNO in (select CustomerNo from   LLClaimDetail where  ClmNo='"
							+ RgtNo + "' )");
			// 人数
			String CountInsured = my
					.getOneValue("select count(*) from (select unique CustomerNo from LLClaimDetail where  ClmNo='"
							+ RgtNo + "')");

			// 全部保险责任形成一个串
			String Duty = "";
			// 全部保单号开成一个串
			String StrGrpContNO = "";

			ExeSQL myExeSQL = new ExeSQL();
			// 数据库连接
			try {
				String Sql = "select unique grpcontno from lcpol  where PolNo in (select unique polno from LLClaimDetail  where  RgtNo='"
						+ RgtNo + "'  )";
				SSRS tSSRS = myExeSQL.execSQL(Sql);
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

			String EndCaseDate = my
					.getOneValue("select to_char(EndCaseDate,'yyyy-mm-dd') from LLRegister where RgtNo='"
							+ RgtNo + "' ");

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
			String AccStartDate = my
					.getOneValue("select to_char(accdate ,'yyyy')||'年'||to_char(accdate ,'mm')||'月'||to_char(accdate ,'dd')||'日' from llcase where RgtNo='"
							+ RgtNo + "'  and rownum<=1");

			strRefuse = strRefuse
					+ "在 "
					+ AccStartDate
					+ " 出险事件的理赔申请及相关资料，本公司已经收悉。根据保险条款及相关法律规定，经我公司理赔部门审慎核定，本公司对您的理赔申请决定拒付保险金。";
			document.add(new Paragraph(strRefuse, FontChinese));
			// 拒赔原因
			String strReson = my
					.getOneValue("select trim(CodeName)  from ldcode where codetype = 'llprotestreason' and trim(code) in ( select trim(AuditNoPassReason) from LLClaimUWMain where ClmNo='"
							+ RgtNo + "' or RgtNo='" + RgtNo + "' )");
			if (strReson.trim().length() >= 1) {
				document.add(new Paragraph(Stringtools.createspace(10)
						+ "本公司作出上述处理决定的理由为：" + strReson, FontChinese));
				// 拒赔依据
				String strGist = my
						.getOneValue("select trim(AuditNoPassDesc) from LLClaimUWMain where ClmNo='"
								+ RgtNo + "' or RgtNo='" + RgtNo + "' ");
				if (strGist.trim().length() >= 1) {
					document.add(new Paragraph(Stringtools.createspace(10)
							+ "拒赔依据：" + strGist, FontChinese));
				}
			}
			document.add(new Paragraph(" ", FontChinese));
			document.add(new Paragraph(" ", FontChinese));
			document
					.add(new Paragraph(Stringtools.createspace(10)
							+ "若您对以上处理决定有异议，可于接到本通知之日起十日内要求本公司理赔部门详细解释。 ",
							FontChinese));

			// 加空行
			for (int i = 1; i <= 5; i++) {
				document.add(new Paragraph("   ", FontChinese));
			}
			document.add(new Paragraph(Stringtools.createspace(100)
					+ "泰康人寿保险股份有限公司", FontChinese));
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

	/** ***************理赔费用清单的excel版********************************************* */
	public String CreateFeeBill(String filename, String RgtNo, String ManageCom) {
		String state = "";
		ExeSQL my = new ExeSQL();
		String GrpcontNo = my
				.getOneValue("select GrpContNo from LLRegister where RgtNo='"
						+ RgtNo + "'");
		int datacount = Stringtools.stringtoint(my
				.getOneValue("select count(*) from LLCaseReceipt where ClmNo='"
						+ RgtNo + "' and  mainfeeno <> '0000000000'"));
		if (GrpcontNo.equals(new String("230101077373"))) {
			String[] data = new String[datacount * 10 + 10];
			// 开始写表头
			data[0] = "员工编号";
			data[1] = "姓名";
			data[2] = "部门/科室";
			data[3] = "帐单号";
			data[4] = "费用类别";
			data[5] = "费用项目";
			data[6] = "申请金额";
			data[7] = "扣除费用";
			data[8] = "合理费用";
			data[9] = "扣除原因";

			String SQL = "select a.CustomerNo,MainFeeNo,feeitemtype,FeeItemName,Fee,RefuseAmnt,AdjSum,AdjRemark,b.othidno,b.department from LLCaseReceipt a,LDPersonRider b where ClmNo='"
					+ RgtNo
					+ "' and mainfeeno <> '0000000000' and a.customerno=b.customerno order by  a.CustomerNo";
			ExeSQL myExeSQL = new ExeSQL();
			try {
				SSRS tSSRS = myExeSQL.execSQL(SQL);
				if (tSSRS == null) {
					state = "打印赔款清单的数据库连接时出错,数据库连接失败!";
					Stringtools.log("", state, "");
					return state;
				}

				if (tSSRS == null) {
					String ErrMsg = "打印理赔费用清单时出错!!";
					logger.debug(ErrMsg);
					Stringtools.log("", ErrMsg, "");
					state = ErrMsg;
					return state;
				}

				String InsuredNo = "";
				String Name = "";
				String feeitemtype = "";
				String feeitemname = "";
				String WorkNo = "";// 员工编号
				String Department = "";// 部门/科室
				int i = 10;
				for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
					// 客户号
					InsuredNo = tSSRS.GetText(tmpi, 1); // .getString(1);
					// 员工编号
					WorkNo = tSSRS.GetText(tmpi, 9);
					data[i] = WorkNo;

					// 姓名
					Name = my
							.getOneValue("select Name from LDPerson where CustomerNo='"
									+ InsuredNo + "'");
					logger.debug("name=" + Name);
					data[i + 1] = Name;
					logger.debug("dsfsaffasssssssssssssssssname=" + Name);
					// 部门/科室
					Department = tSSRS.GetText(tmpi, 10);
					data[i + 2] = Department;
					// 帐单号
					data[i + 3] = tSSRS.GetText(tmpi, 2);
					// 费用类别(A-门诊B-住院)
					feeitemtype = tSSRS.GetText(tmpi, 3); // rs.getString(3);

					if (feeitemtype == null) {
						feeitemtype = "";
					}
					feeitemname = "其它";
					if (Stringtools.stringequals(feeitemtype, "A")) {
						feeitemname = feeitemtype.trim() + "--门诊";
					}
					if (Stringtools.stringequals(feeitemtype, "B")) {
						feeitemname = feeitemtype.trim() + "--住院";
					}
					data[i + 4] = feeitemname;
					data[i + 5] = tSSRS.GetText(tmpi, 4); // rs.getString(4);
					// 申请金额
					data[i + 6] = tSSRS.GetText(tmpi, 5); // rs.getString(5);
					// 扣除费用
					data[i + 7] = tSSRS.GetText(tmpi, 6); // rs.getString(6);
					// 合理费用
					data[i + 8] = tSSRS.GetText(tmpi, 7); // rs.getString(7);
					// 扣除原因
					data[i + 9] = tSSRS.GetText(tmpi, 8); // rs.getString(8);
					logger.debug("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
					i = i + 10;
				}
				logger.debug("i:" + i);

				/*
				 * String [] tmpdata=new String[i+1]; for (int k=0;k<i;k++) {
				 * tmpdata[k]=tmpdata[i]; logger.debug("K:"+k); }
				 */
				if (state.trim().length() == 0) {
					CreateExcel myexcel = new CreateExcel();
					state = myexcel.Crtexcel(filename, data, 10);
				}

			} catch (Exception e) {
				String ErrMsg = "理赔费用清单excel版组织数据出错!!" + e.getMessage();
				logger.debug(ErrMsg);
				Stringtools.log("", ErrMsg, "");
			}

		} else {
			String[] data = new String[datacount * 9 + 9];
			// 开始写表头
			data[0] = "个人客户号";
			data[1] = "姓名";
			data[2] = "帐单号";
			data[3] = "费用类别";
			data[4] = "费用项目";
			data[5] = "申请金额";
			data[6] = "扣除费用";
			data[7] = "合理费用";
			data[8] = "扣除原因";

			String SQL = "select CustomerNo,MainFeeNo,feeitemtype,FeeItemName,Fee,RefuseAmnt,AdjSum,AdjRemark from LLCaseReceipt where ClmNo='"
					+ RgtNo
					+ "' and mainfeeno <> '0000000000' order by  CustomerNo";
			ExeSQL myExeSQL = new ExeSQL();
			try {
				SSRS tSSRS = myExeSQL.execSQL(SQL);
				if (tSSRS == null) {
					state = "打印赔款清单的数据库连接时出错,数据库连接失败!";
					Stringtools.log("", state, "");
					return state;
				}

				if (tSSRS == null) {
					String ErrMsg = "打印理赔费用清单时出错!!";
					logger.debug(ErrMsg);
					Stringtools.log("", ErrMsg, "");
					state = ErrMsg;
					return state;
				}

				String InsuredNo = "";
				String Name = "";
				String feeitemtype = "";
				String feeitemname = "";
				int i = 9;
				for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
					// 客户号
					InsuredNo = tSSRS.GetText(tmpi, 1); // .getString(1);
					data[i] = InsuredNo;

					// 姓名
					Name = my
							.getOneValue("select Name from LDPerson where CustomerNo='"
									+ InsuredNo + "'");
					data[i + 1] = Name;
					// 帐单号
					data[i + 2] = tSSRS.GetText(tmpi, 2);
					// 费用类别(A-门诊B-住院)
					feeitemtype = tSSRS.GetText(tmpi, 3); // rs.getString(3);

					if (feeitemtype == null) {
						feeitemtype = "";
					}
					feeitemname = "其它";
					if (Stringtools.stringequals(feeitemtype, "A")) {
						feeitemname = feeitemtype.trim() + "--门诊";
					}
					if (Stringtools.stringequals(feeitemtype, "B")) {
						feeitemname = feeitemtype.trim() + "--住院";
					}
					data[i + 3] = feeitemname;
					data[i + 4] = tSSRS.GetText(tmpi, 4); // rs.getString(4);
					// 申请金额
					data[i + 5] = tSSRS.GetText(tmpi, 5); // rs.getString(5);
					// 扣除费用
					data[i + 6] = tSSRS.GetText(tmpi, 6); // rs.getString(6);
					// 合理费用
					data[i + 7] = tSSRS.GetText(tmpi, 7); // rs.getString(7);
					// 扣除原因
					data[i + 8] = tSSRS.GetText(tmpi, 8); // rs.getString(8);
					i = i + 9;
				}
				logger.debug("i:" + i);

				/*
				 * String [] tmpdata=new String[i+1]; for (int k=0;k<i;k++) {
				 * tmpdata[k]=tmpdata[i]; logger.debug("K:"+k); }
				 */
				if (state.trim().length() == 0) {
					CreateExcel myexcel = new CreateExcel();
					state = myexcel.Crtexcel(filename, data, 9);
				}

			} catch (Exception e) {
				String ErrMsg = "理赔费用清单excel版组织数据出错!!" + e.getMessage();
				logger.debug(ErrMsg);
				Stringtools.log("", ErrMsg, "");
			}
		}
		return state;
	} // 本方法

	// 理赔个人清单 (生成的pdf文件名,赔案号,模板文件名)
	public String CreateClaimPer(String filename, String RgtNo,
			String TemplateFilename) {
		String state = "";

		String SQL = "select distinct customerno from llclaimdetail ";
		SQL = SQL + " where clmno='" + RgtNo + "' ";
		ExeSQL myExeSQL = new ExeSQL();
		// 取公共数据（每页都相同的数据）
		String[] Publicstr = new String[8];

		// 保单号：
		Publicstr[0] = myExeSQL
				.getOneValue("select Grpcontno from llregister where rgtno='"
						+ RgtNo + "'");
		// 投保单位：
		Publicstr[1] = myExeSQL
				.getOneValue("select GrpName from lcgrpcont where GrpContNo='"
						+ Publicstr[0] + "'");
		// 保单生效日期
		Publicstr[2] = myExeSQL
				.getOneValue("select to_char(cValidate,'yyyy-mm-dd') from lcgrpcont where GrpContno='"
						+ Publicstr[0] + "'");
		// 赔案号
		Publicstr[4] = RgtNo;
		// 页码(生成文件时自己计算)
		Publicstr[3] = "";
		// 受理日期
		Publicstr[5] = myExeSQL
				.getOneValue("select to_char(RgtDate,'yyyy-mm-dd') from llregister where rgtno='"
						+ RgtNo + "' ");
		// 打印日期
		Publicstr[6] = myExeSQL
				.getOneValue("select to_char(sysdate,'yyyy-mm-dd') from dual");
		// 公司名称及地址
		Publicstr[7] = "泰康人寿保险股份有限公司  "
				+ myExeSQL
						.getOneValue("select name||'  地址：'||address from ldcom  where comcode=(select substr(managecom,1,2) from lcgrpcont where grpcontno='"
								+ Publicstr[0] + "')");

		SSRS tSSRS = myExeSQL.execSQL(SQL);
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
				String Name = myExeSQL
						.getOneValue("select Name from lcinsured where GrpContno='"
								+ Publicstr[0]
								+ "' and insuredno='"
								+ Customerno + "'");
				getData[k] = Stringtools.inttostring(i);
				getData[k + 1] = Name;
				getData[k + 2] = Customerno;
				getData[k + 3] = myExeSQL
						.getOneValue("select IdNo from ldperson where Customerno='"
								+ Customerno + "'");
				/***************************************************************
				 * 门诊
				 */
				// 保额 1
				sql = "select StandMoney from lcget where GrpContno='"
						+ Publicstr[0] + "' ";
				sql = sql + " and insuredno='" + Customerno + "' ";
				sql = sql
						+ " and getdutycode in (select getdutycode from llclaimdetail where clmno='"
						+ RgtNo + "'";
				sql = sql + "                  and Customerno='" + Customerno
						+ "'";
				sql = sql
						+ "                  and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='1')";
				sql = sql + " )";
				getData[k + 4] = myExeSQL.getOneValue(sql);
				// 申请费用 1
				sql = "select sum(Fee) from llcasereceipt where clmno='"
						+ RgtNo + "'";
				sql = sql + " and feeitemtype='A' and Customerno='"
						+ Customerno + "'";
				getData[k + 5] = myExeSQL.getOneValue(sql);
				// 扣除费用
				sql = "select sum(RefuseAmnt) from llcasereceipt where clmno='"
						+ RgtNo + "'";
				sql = sql + "' and feeitemtype='A' and Customerno='"
						+ Customerno + "'";
				getData[k + 6] = myExeSQL.getOneValue(sql);
				// 合理费用
				sql = "select sum(AdjSum) from llcasereceipt ";
				sql = sql + "  where clmno='" + RgtNo + "'";
				sql = sql + " and feeitemtype='A' and Customerno='"
						+ Customerno + "'";
				getData[k + 7] = myExeSQL.getOneValue(sql);
				// 津贴
				getData[k + 8] = "-";
				// 住院天数
				getData[k + 9] = "-";
				// 本次门诊赔付
				sql = "select sum(realpay) from llclaimdetail ";
				sql = sql + " where clmno='" + RgtNo + "' ";
				sql = sql + "  and Customerno='" + Customerno + "'";
				sql = sql
						+ " and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='1')";

				getData[k + 10] = myExeSQL.getOneValue(sql);
				// 累计门诊赔付
				sql = " select sum(realpay) from llclaimdetail ";
				sql = sql + " where  Customerno='" + Customerno + "'";
				sql = sql
						+ " and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='1')";
				getData[k + 11] = myExeSQL.getOneValue(sql);
				// 扣除原因
				sql = "select AdjRemark from llcasereceipt ";
				sql = sql + " where clmno='" + RgtNo + "'";
				sql = sql + " and feeitemtype='A' ";
				sql = sql + " and Customerno='" + Customerno
						+ "' and AdjRemark is not null";
				getData[k + 12] = myExeSQL.getOneValue(sql);
				/***************************************************************
				 * 住院
				 */
				// 保额 2
				k = k + 9;
				sql = "select StandMoney from lcget where GrpContno='"
						+ Publicstr[0] + "' ";
				sql = sql + " and insuredno='" + Customerno + "' ";
				sql = sql
						+ " and getdutycode in (select getdutycode from llclaimdetail where clmno='"
						+ RgtNo + "'";
				sql = sql + "                  and Customerno='" + Customerno
						+ "'";
				sql = sql
						+ "                  and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='2')";
				sql = sql + " )";
				getData[k + 4] = myExeSQL.getOneValue(sql);
				// 申请费用 2
				sql = "select sum(Fee) from llcasereceipt where clmno='"
						+ RgtNo + "'";
				sql = sql + " and feeitemtype='B' and Customerno='"
						+ Customerno + "'";
				getData[k + 5] = myExeSQL.getOneValue(sql);
				// 扣除费用 2
				sql = "select sum(RefuseAmnt) from llcasereceipt where clmno='"
						+ RgtNo + "'";
				sql = sql + "' and feeitemtype='B' and Customerno='"
						+ Customerno + "'";
				getData[k + 6] = myExeSQL.getOneValue(sql);
				// 合理费用
				sql = "select sum(AdjSum) from llcasereceipt ";
				sql = sql + "  where clmno='" + RgtNo + "'";
				sql = sql + " and feeitemtype='B' and Customerno='"
						+ Customerno + "'";
				getData[k + 7] = myExeSQL.getOneValue(sql);
				// 津贴
				sql = "select StandMoney from lcget ";
				sql = sql + "where Grpcontno='" + Publicstr[0] + "'";
				sql = sql + "  and insuredno='" + Customerno + "'";
				sql = sql
						+ " and dutycode in (select dutycode from lmduty where dutyname  like '%津贴%' )";
				getData[k + 8] = myExeSQL.getOneValue(sql);
				// 住院天数
				sql = "select sum(daycount) from llcasereceipt";
				sql = sql + "  where  clmno='" + RgtNo + "' ";
				sql = sql + " and feeitemtype='B' and Customerno='"
						+ Customerno + "'";
				getData[k + 9] = myExeSQL.getOneValue(sql);
				// 本次住院赔付
				sql = "select sum(realpay) from llclaimdetail ";
				sql = sql + " where clmno='" + RgtNo + "' ";
				sql = sql + "  and Customerno='" + Customerno + "'";
				sql = sql
						+ " and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='2')";
				getData[k + 10] = myExeSQL.getOneValue(sql);
				// 累计住院赔付
				sql = " select sum(realpay) from llclaimdetail ";
				sql = sql + " where  Customerno='" + Customerno + "'";
				sql = sql
						+ " and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='2')";
				getData[k + 11] = myExeSQL.getOneValue(sql);
				// 扣除原因
				sql = "select AdjRemark from llcasereceipt ";
				sql = sql + " where clmno='" + RgtNo + "'";
				sql = sql + " and feeitemtype='B' ";
				sql = sql + " and Customerno='" + Customerno
						+ "' and AdjRemark is not null";
				getData[k + 12] = myExeSQL.getOneValue(sql);
				/***************************************************************
				 * 其它
				 */
				// 保额 1
				k = k + 9;
				sql = "select StandMoney from lcget where GrpContno='"
						+ Publicstr[0] + "' ";
				sql = sql + " and insuredno='" + Customerno + "' ";
				sql = sql
						+ " and getdutycode in (select getdutycode from llclaimdetail where clmno='"
						+ RgtNo + "'";
				sql = sql + "                  and Customerno='" + Customerno
						+ "'";
				sql = sql
						+ "                  and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='3')";
				sql = sql + " )";
				getData[k + 4] = myExeSQL.getOneValue(sql);
				// 申请费用 1
				sql = "select sum(Fee) from llcasereceipt";
				sql = sql + " where clmno='" + RgtNo + "'";
				sql = sql + " and feeitemtype<>'B' and feeitemtype<>'A' ";
				sql = sql + " and Customerno='" + Customerno + "'";
				getData[k + 5] = myExeSQL.getOneValue(sql);
				// 扣除费用
				sql = "select sum(RefuseAmnt) from llcasereceipt ";
				sql = sql + " where clmno='" + RgtNo + "'";
				sql = sql + " and feeitemtype='B'  and feeitemtype<>'A'";
				sql = sql + " and Customerno='" + Customerno + "'";
				getData[k + 6] = myExeSQL.getOneValue(sql);
				// 合理费用
				sql = "select sum(AdjSum) from llcasereceipt ";
				sql = sql + " where clmno='" + RgtNo + "'";
				sql = sql + " and feeitemtype<>'B'  and feeitemtype<>'A'";
				sql = sql + "  and Customerno='" + Customerno + "'";
				getData[k + 7] = myExeSQL.getOneValue(sql);
				// 津贴
				getData[k + 8] = "-";
				// 住院天数 3
				sql = "select sum(daycount) from llcasereceipt ";
				sql = sql + " where  clmno='" + RgtNo + "'";
				sql = sql + " and feeitemtype<>'B' and feeitemtype<>'A' ";
				sql = sql + " and Customerno='" + Customerno + "'";
				getData[k + 9] = myExeSQL.getOneValue(sql);
				// 本次住院赔付
				sql = "select sum(realpay) from llclaimdetail ";
				sql = sql + " where clmno='" + RgtNo + "' ";
				sql = sql + "  and Customerno='" + Customerno + "'";
				sql = sql
						+ " and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='3')";
				getData[k + 10] = myExeSQL.getOneValue(sql);
				// 累计住院赔付
				sql = " select sum(realpay) from llclaimdetail ";
				sql = sql + " where  Customerno='" + Customerno + "'";
				sql = sql
						+ " and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='3')";
				getData[k + 11] = myExeSQL.getOneValue(sql);
				// 扣除原因
				sql = "select AdjRemark from llcasereceipt ";
				sql = sql + " where clmno='" + RgtNo + "'";
				sql = sql + " and feeitemtype<>'B'  and feeitemtype<>'A'";
				sql = sql + " and Customerno='" + Customerno
						+ "' and AdjRemark is not null";
				getData[k + 12] = myExeSQL.getOneValue(sql);

				/***************************************************************
				 * 合计
				 */
				// 保额 1
				k = k + 9;
				sql = "select StandMoney from lcget where GrpContno='"
						+ Publicstr[0] + "' ";
				sql = sql + " and insuredno='" + Customerno + "' ";
				sql = sql
						+ " and getdutycode in (select getdutycode from llclaimdetail where clmno='"
						+ RgtNo + "'";
				sql = sql + "                  and Customerno='" + Customerno
						+ "'";
				sql = sql
						+ "                  and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='4')";
				sql = sql + " )";
				getData[k + 4] = myExeSQL.getOneValue(sql);
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
				sql = sql + " where clmno='" + RgtNo + "' ";
				sql = sql + "  and Customerno='" + Customerno + "'";
				sql = sql
						+ " and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='4')";
				getData[k + 10] = myExeSQL.getOneValue(sql);
				// 累计住院赔付
				sql = " select sum(realpay) from llclaimdetail ";
				sql = sql + " where  Customerno='" + Customerno + "'";
				sql = sql
						+ " and (getdutycode||getdutykind) in (select code||codename from reportcode where codealias='4')";
				getData[k + 11] = myExeSQL.getOneValue(sql);
				// 扣除原因
				getData[k + 12] = "-";
				k = k + 9;
				k = k + 4;

			}
			MU_Template tMU_Template = new MU_Template();
			if (!tMU_Template.createreport(getData, filename, TemplateFilename,
					160, Publicstr)) {
				state = "生成文件失败！";
				return state;
			}
		} catch (Exception e) {
			state = "出错误信息：" + e.getMessage();
			logger.debug(state);
			return state;
		}
		return state;
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
			/** **********与mm的换算：1 mm = 2.8346 pt***** 72pt=1 英寸***************** */
			/** **************** 左 右 上 下 ************************ */
			document.setMargins(36, 36, 80, 36);
			String StrLeft = Stringtools.createspace(10);
			Image bmp = Image.getInstance("logo.bmp");
			bmp.setAlignment(Image.LEFT | Image.UNDERLYING);
			document.add(new Paragraph(StrLeft + "泰康人寿保险股份有限公司", FontChinese));
			document.add(new Paragraph(StrLeft + "团体健康保险理赔清单", FontChinese));
			// 画一条水平线

		} catch (Exception e) {
			state = "出错信息：" + e.getMessage();
		}
		return state;
	}

	// ---------------------------------理赔赔款清单Excel版-------------------------------------//
	public String CreatePayBillExcel(String filename, String RgtNo,
			String ManageCom) {
		String state = "";
		ExeSQL myExeSQL = new ExeSQL();
		String GrpcontNo = myExeSQL
				.getOneValue("select GrpContNo from LLRegister where RgtNo='"
						+ RgtNo + "'");

		// 针对广州本田做特殊处理开始－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		if (GrpcontNo.equals(new String("230101077373"))) {
			// 数据准备SQL文
			String Sql = "select a.CustomerNo,a.RiskCode,sum(a.amnt),sum(a.realpay),b.othidno,b.department,b.number1,b.number2";
			Sql = Sql + "    from LLClaimDetail a,LDpersonrider b";
			Sql = Sql
					+ " where  (GiveType='0' or givetype='2' or givetype='3' ) and a.customerno=b.customerno "; // 不是拒赔
			// 判断是否结案
			Sql = Sql + " and 1=1 ";
			Sql = Sql + " and a.ClmNo='" + RgtNo + "'";
			Sql = Sql
					+ " group by a.CustomerNo,a.RiskCode,b.customerno,b.othidno,b.department,b.number1,b.number2 ";

			SSRS tSSRS = myExeSQL.execSQL(Sql);
			int row = tSSRS.getMaxRow();
			int col = tSSRS.getMaxCol();
			// 数组长度：
			int num = (row + 1 + 1) * 11;
			String[] Data = new String[num];

			Data[0] = "理赔款清单Excel版";
			Data[1] = "保单号:";

			Data[2] = GrpcontNo;
			Data[3] = "赔案号：";
			Data[4] = RgtNo;
			// 投保单位
			Data[5] = "投保单位：";
			Data[6] = myExeSQL
					.getOneValue("select GrpName from LLRegister where RgtNo='"
							+ RgtNo + "'  ");
			Data[7] = "";
			Data[8] = "";
			Data[9] = "";
			Data[10] = "";
			Data[11] = "员工编号"; // ldpersonrider.othidno
			Data[12] = "姓名";
			Data[13] = "部门/科室"; // ldpersonrider.department
			Data[14] = "内诊比例"; // ldpersonrider.number1
			Data[15] = "外诊比例"; // ldpersonrider.number2
			Data[16] = "险种代码";
			Data[17] = "保额";
			Data[18] = "本次赔付";
			Data[19] = "累计赔付";
			Data[20] = "手机号码"; // lcaddress.Mobile
			Data[21] = "E_MAIL地址"; // lcaddress.EMail
			int k = 22;

			String InsuredNo = "";
			String WorkNo = "";

			try {
				for (int i = 1; i <= row; i++) {
					// 个人客户号
					InsuredNo = tSSRS.GetText(i, 1);
					WorkNo = tSSRS.GetText(i, 5);
					Data[k] = WorkNo;
					// 姓名
					Data[k + 1] = myExeSQL
							.getOneValue("select name from LCInsured where grpcontno='"
									+ GrpcontNo
									+ "' and InsuredNO='"
									+ InsuredNo + "'");
					Data[k + 2] = tSSRS.GetText(i, 6);
					Data[k + 3] = tSSRS.GetText(i, 7);
					Data[k + 4] = tSSRS.GetText(i, 8);
					Data[k + 5] = tSSRS.GetText(i, 2);
					Data[k + 6] = "double" + tSSRS.GetText(i, 3);
					Data[k + 7] = "double" + tSSRS.GetText(i, 4);
					String tmpsql = "select sum(realpay) from LLClaimDetail where GRpContno='"
							+ GrpcontNo
							+ "' and CustomerNo='"
							+ InsuredNo
							+ "'  and RiskCode='" + Data[k + 5] + "'";
					tmpsql = tmpsql
							+ "   and RgtNo in (select RgtNo from  LLRegister ";
					tmpsql = tmpsql
							+ " where EndCaseFlag='1' and clmstate!='80' and EndCaseDate is not null  )";
					Data[k + 8] = "double" + myExeSQL.getOneValue(tmpsql);
					tmpsql = "select Mobile from lcaddress where customerno='"
							+ InsuredNo
							+ "' and addressno = (select addressno from lcinsured where grpcontno='"
							+ GrpcontNo + "' and InsuredNo='" + InsuredNo
							+ "') and rownum<=1";
					Data[k + 9] = myExeSQL.getOneValue(tmpsql);
					tmpsql = "select EMail from lcaddress where customerno='"
							+ InsuredNo
							+ "' and addressno = (select addressno from lcinsured where grpcontno='"
							+ GrpcontNo + "' and InsuredNo='" + InsuredNo
							+ "') and rownum<=1";
					Data[k + 10] = myExeSQL.getOneValue(tmpsql);
					k = k + 11; // k=k+11;
				}
			} catch (Exception e) {
				state = "出错信息:" + e.getMessage();
				return state;
			}
			try {
				CreateExcel myExcel = new CreateExcel();
				state = myExcel.Crtexcel(filename, Data, 11);
			} catch (Exception e) {
				state = "出错信息:" + e.getMessage();
				return state;
			}
			// 针对广州本田做特殊处理结束－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		} else {
			// 数据准备SQL文
			String Sql = "select CustomerNo,RiskCode,sum(amnt),sum(realpay)  from LLClaimDetail";
			Sql = Sql
					+ " where  (GiveType='0' or givetype='2' or givetype='3' )  "; // 不是拒赔
			// 判断是否结案
			Sql = Sql + " and 1=1 ";
			Sql = Sql + " and ClmNo='" + RgtNo + "'";
			Sql = Sql + " group by CustomerNo,RiskCode ";
			SSRS tSSRS = myExeSQL.execSQL(Sql);
			int row = tSSRS.getMaxRow();
			int col = tSSRS.getMaxCol();
			// 数组长度：
			int num = (row + 1 + 1) * 8;
			String[] Data = new String[num];

			Data[0] = "理赔款清单Excel版";
			Data[1] = "保单号:";
			Data[2] = GrpcontNo;
			Data[3] = "赔案号：";
			Data[4] = RgtNo;
			// 投保单位
			Data[5] = "投保单位：";
			Data[6] = myExeSQL
					.getOneValue("select GrpName from LLRegister where RgtNo='"
							+ RgtNo + "'  ");
			Data[7] = "";
			Data[8] = "个人客户号";
			Data[9] = "姓名";
			Data[10] = "险种代码";
			Data[11] = "保额";
			Data[12] = "本次赔付";
			Data[13] = "累计赔付";
			Data[14] = "手机号码"; // lcaddress.Mobile
			Data[15] = "E_MAIL地址"; // lcaddress.EMail
			int k = 16;

			String InsuredNo = "";
			try {
				for (int i = 1; i <= row; i++) {
					// 个人客户号
					InsuredNo = tSSRS.GetText(i, 1);
					Data[k] = InsuredNo;
					// 姓名
					Data[k + 1] = myExeSQL
							.getOneValue("select name from LCInsured where grpcontno='"
									+ GrpcontNo
									+ "' and InsuredNO='"
									+ InsuredNo + "'");
					Data[k + 2] = tSSRS.GetText(i, 2);
					Data[k + 3] = "double" + tSSRS.GetText(i, 3);
					Data[k + 4] = "double" + tSSRS.GetText(i, 4);
					String tmpsql = "select sum(realpay) from LLClaimDetail where GRpContno='"
							+ GrpcontNo
							+ "' and CustomerNo='"
							+ InsuredNo
							+ "'  and RiskCode='" + Data[k + 2] + "'";
					tmpsql = tmpsql
							+ "   and RgtNo in (select RgtNo from  LLRegister ";
					tmpsql = tmpsql
							+ " where EndCaseFlag='1' and clmstate!='80' and EndCaseDate is not null  )";
					Data[k + 5] = "double" + myExeSQL.getOneValue(tmpsql);
					tmpsql = "select Mobile from lcaddress where customerno='"
							+ InsuredNo
							+ "' and addressno = (select addressno from lcinsured where grpcontno='"
							+ GrpcontNo + "' and InsuredNo='" + InsuredNo
							+ "') and rownum<=1";
					Data[k + 6] = myExeSQL.getOneValue(tmpsql);
					tmpsql = "select EMail from lcaddress where customerno='"
							+ InsuredNo
							+ "' and addressno = (select addressno from lcinsured where grpcontno='"
							+ GrpcontNo + "' and InsuredNo='" + InsuredNo
							+ "') and rownum<=1";
					Data[k + 7] = myExeSQL.getOneValue(tmpsql);
					k = k + 8; // k=k+6;
				}
			} catch (Exception e) {
				state = "出错信息:" + e.getMessage();
				return state;
			}
			try {
				CreateExcel myExcel = new CreateExcel();
				state = myExcel.Crtexcel(filename, Data, 8);
			} catch (Exception e) {
				state = "出错信息:" + e.getMessage();
				return state;
			}
		}
		if (state.length() > 1) {
			state = state.substring(1, state.length());
		}
		return state;
	}
}
