package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Hashtable;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

public class PrintJavaToExcel {
private static Logger logger = Logger.getLogger(PrintJavaToExcel.class);

	private WritableWorkbook book = null; // 定义生成Excel

	// 错误处理类，每个需要错误处理的类中都放置该类
	private CErrors mErrors = new CErrors();

	// 传入调用的第几次Sheet
	private int mSheetCount = 0;

	// 当前Sheet的名称
	private String SheetName = "";

	// 当前Sheet的抬头文件的名称

	private String[] SheetTitleName = null;

	// 创建一个sheet
	private WritableSheet sheet = null;

	// 设置行

	private int mRow = 2;

	private Hashtable mHashTable = new Hashtable();

	/**
	 * 生成Excel的路径
	 * 
	 */

	public void CreateExcel(String strFileName) {
		// 打开文件
		try {

			book = Workbook.createWorkbook(new File(strFileName + ".xls"));
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("生成", ex.getMessage());
		}
	}

	public void CreateSheet(String sheetName) {
		this.SheetName = sheetName;
		mRow = 0;
		this.mSheetCount = 1;
		mHashTable.put(sheetName, "0");
		sheet = book.createSheet(SheetName, 0);
	}

	public void SetSheet(String mName) {

		sheet = book.getSheet(mName);
		String srt = String.valueOf(mHashTable.get(mName));
		mRow = Integer.parseInt(srt);
	}

	/**
	 * 生成一个Sheet 生成头标题 arg 加入文件头 param sheetName
	 */

	public void CreateSheet(String sheetName, String[] arg) {
		this.SheetName = sheetName;
		this.mSheetCount = 1;
		this.SheetTitleName = arg;
		sheet = book.createSheet(SheetName, 0);
		mRow = 2;
		mHashTable.put(sheetName, String.valueOf(mRow));
		int i = SheetTitleName.length;
		try {
			for (int j = 0; j < i; j++) {
				Label label = new Label(j, 1, SheetTitleName[j]);

				// 将定义好的单元格添加到工作表中
				sheet.addCell(label);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("生成", ex.getMessage());
		}
	}

	/**
	 * 加入元素
	 * 
	 */
	public void addCell(String[] arg) {
		try {
			if (mSheetCount == 0) {
				throw new NullPointerException("没有创建Sheet,请先创建");
			}

			// 增加一组元素
			for (int cs = 0; cs < arg.length; cs++) {
				logger.debug(cs + "======" + mRow);
				Label label = new Label(cs, mRow, arg[cs]);

				// 将定义好的单元格添加到工作表中
				sheet.addCell(label);
			}
			// 增加一行的数据;
			mRow += 1;
			mHashTable.put(sheet.getName(), String.valueOf(mRow));
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("生成", ex.getMessage());
		}
	}

	/**
	 * 合并单元格
	 * 
	 */

	public void combinCell(String str) {
		try {

			sheet.mergeCells(0, 0, 12, 0);

			WritableFont font1 = new WritableFont(WritableFont.TIMES, 15,
					WritableFont.BOLD);

			WritableCellFormat format1 = new WritableCellFormat(font1);
			Colour t = Colour.TEAL;

			format1.setBackground(t);

			Label label = new Label(0, 0, str, format1);

			// 将此时定义好的数据放入到Excel中

			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);

		} catch (Exception e) {
			e.printStackTrace();
			buildError("生成Excel", e.getMessage());
		}
	}

	public void combinCell(StringBuffer str) {
		try {
			String tTempStr = str.toString();
			sheet.mergeCells(0, 0, 12, 0);
			// 将此时定义好的数据放入到Excel中
			Label label = new Label(0, 0, tTempStr);
			sheet.setRowView(0, 200);

			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);

		} catch (Exception e) {
			e.printStackTrace();
			buildError("生成Excel", e.getMessage());
		}
	}

	/**
	 * 创建Excel文件完成
	 * 
	 */

	public void endCreate() {
		try {
			book.write();
			book.close();
		} catch (Exception cx) {
			cx.printStackTrace();
			buildError("生成", cx.getMessage());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PrintJavaToExcel t = new PrintJavaToExcel();
		t.CreateExcel("sdf");

		String[] str = new String[5];
		str[0] = "我是第一";
		str[1] = "我是第er";
		str[2] = "我是第s";
		str[3] = "我是第s";
		str[4] = "我是第w";

		t.CreateSheet("yyf", str);

		str[0] = "我是第一";
		str[1] = "2";
		str[2] = "4";
		str[3] = "5s";
		str[4] = "7";
		t.addCell(str);
		str[0] = "我是第一";
		str[1] = "1";
		str[2] = "2";
		str[3] = "3";
		str[4] = "4w";
		t.addCell(str);
		str[0] = "2";
		str[1] = "3";
		str[2] = "4";
		str[3] = "6";
		str[4] = "7";
		t.addCell(str);
		str[0] = "2";
		str[1] = "3";
		str[2] = "3";
		str[3] = "5";
		str[4] = "6";
		t.addCell(str);
		t.endCreate();

	}

	/*
	 * add 返回错误的信息, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "CardSendOutBLS";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
