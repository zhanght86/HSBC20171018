package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;

import com.sinosoft.utility.CErrors;

public class ReadFromExcel {
private static Logger logger = Logger.getLogger(ReadFromExcel.class);
	private String mFilePath = ""; // 得到所需要读取的文件的路径与名称

	private CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private File mFile = null;

	private Workbook book = null; // 定义生成Excel

	private WritableSheet sheet = null;// 创建一个sheet

	private String[] mStrSheet = null;

	private Sheet mWorkSheet = null;

	public ReadFromExcel(String tFile) {
		this.mFilePath = tFile;
		mFile = new File(mFilePath);
		ReadExcelFile();
	}

	// 读取文件
	private void ReadExcelFile() {
		try {
			book = Workbook.getWorkbook(mFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 读取发现的几个Sheet
	public String[] ReadSheetNum() {
		this.mStrSheet = book.getSheetNames();
		return mStrSheet;
	}

	// //读取文件中的Sheet
	// private void ReadExcelSheet(String str)
	// {
	// mWorkSheet = book.getSheet(str);
	// }

	public void setSheet(String str1) {
		mWorkSheet = book.getSheet(str1);
	}

	public int getSheetCol() {
		return mWorkSheet.getColumns();
	}

	public int getSheetRow() {
		return mWorkSheet.getRows();
	}

	public String ReadValue(int row, int col) {
		ReadSheetNum();
		boolean ExSheet = false;
		String str = "";

		try {
			for (int i = 0; i < mStrSheet.length; i++) {
				if (mStrSheet[i].equals(mWorkSheet.getName())) {
					ExSheet = true;
				}
			}
			if (!ExSheet) {
				logger.debug("=========" + mWorkSheet.getName()
						+ "查无此Sheet==========");
				throw new NullPointerException("无此" + mWorkSheet.getName()
						+ "Sheet");

			}
			Cell strCell = mWorkSheet.getCell(col, row);
			str = strCell.getContents();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;

	}

	// 得到一个Sheet所有的值
	public String[][] getSheetValues(String str1) {
		boolean ExSheet = false;
		mWorkSheet = book.getSheet(str1);
		for (int i = 0; i < mStrSheet.length; i++) {
			if (mStrSheet[i].equals(str1)) {
				ExSheet = true;
			}
		}
		if (!ExSheet) {
			logger.debug("=========" + str1 + "查无此Sheet==========");
		}
		// Cell strCell = mWorkSheet.getCell(row,col);
		logger.debug("============共" + mWorkSheet.getColumns() + "列");
		logger.debug("============共" + mWorkSheet.getRows() + "行");
		String[][] str = new String[mWorkSheet.getRows()][mWorkSheet
				.getColumns()];
		for (int i = 0; i < mWorkSheet.getRows(); i++) {
			for (int j = 0; j < mWorkSheet.getColumns(); j++) {
				str[i][j] = mWorkSheet.getCell(j, i).getContents();
				logger.debug(mWorkSheet.getCell(j, i).getContents());
			}
		}
		return str;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReadFromExcel t = new ReadFromExcel("E:\\temp\\apse.xls");
		String[] ar = t.ReadSheetNum();
		for (int i = 0; i < ar.length; i++) {
			logger.debug(ar[i]);
		}
		// t.ReadExcelSheet("Sheet1");
		// logger.debug(t.ReadValue("Sheet1",0,0));
		// logger.debug(t.ReadValue("Sheet1",1,1));
		// String[][] sr = t.getSheetValues("Sheet1");
		// for(int i = 0 ; i < sr.length ; i++)
		// {
		// for(int j = 0 ; j < sr[i].length; j++)
		// logger.debug(sr[i][j]);
		// }
		t.setSheet("Sheet1");
		for (int i = 0; i < t.getSheetRow(); i++) {
			for (int j = 0; j < t.getSheetCol(); j++) {
				logger.debug(t.ReadValue(i, j));
			}
		}
	}

}
