package com.sinosoft.lis.pubfun;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;

import com.f1j.ss.BookModelImpl;
import com.f1j.ss.CellFormat;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class HashReport implements Comparator {
	private static Logger logger = Logger.getLogger(HashReport.class);
	public CErrors mErrors = new CErrors();
	private ExeSQL calExeSQL = new ExeSQL();
	private String keyString = new String();
	private int returnCol = 0;
	private VData keyVData = new VData();
	private VData itemVData = new VData();
	private TransferData tTransferData = new TransferData();

	// 记录<=128*0.75，则HashMap不会重构，一般的报表记录数可能在50左右
	private HashMap tHashMap = new HashMap(128, 0.75F);
	private String returnFormat = "";
	private int[] NoArray;
	private String[] FormatArray;
	private int[] OrderArray;
	private int No = 0;
	private String Format = "";

	// 默认是升序(0),降序(1)
	private int Order = 0;
	private int KeyRow = 0;

	// 初始化文件体生成位置(开始位置为1)
	private int BodyRow = 2;
	private int BodyCol = 1;

	public HashReport() {
	}

	/*
	 * 构造函数 参数_keyString中传递的是分组SQL 参数_itemVData中传递的是VData对象，其中存储的是指标SQL
	 * 参数_returnCol中传递的是返回的列数
	 */
	public HashReport(String _keyString, VData _itemVData, int _returnCol) {
		keyString = _keyString;
		itemVData = (VData) _itemVData.clone();
		returnCol = _returnCol;
	}

	/*
	 * 构造函数 参数_keyString中传递的是分组SQL 参数_itemVData中传递的是VData对象，其中存储的是指标SQL
	 * 参数_returnFormat中传递的是指标返回指标的类型，默认为整形，"Double"为浮点型
	 */
	public HashReport(String _keyString, VData _itemVData, int _returnCol,
			String _returnFormat) {
		keyString = _keyString;
		itemVData = (VData) _itemVData.clone();
		returnCol = _returnCol;
		returnFormat = _returnFormat;
	}

	// 设置排序的列数以及格式(对单列进行排序)
	// 排序数组的列数，从0开始(默认第一列排序)
	// 排序数组的格式，double,int,string(默认按照字符串排序规则)
	public void setCompareFormat(int _No, String _Format) {
		No = _No;
		Format = _Format;
		Order = 0;
		NoArray = new int[1];
		NoArray[0] = No;
		FormatArray = new String[1];
		FormatArray[0] = Format;
		OrderArray = new int[1];
		OrderArray[0] = Order;
	}

	// 设置排序的列数以及格式(对单列进行排序)
	// 排序数组的列数，从0开始(默认第一列排序)
	// 排序数组的格式，double,int,string(默认按照字符串排序规则)
	// 排序数组的排序方法：0为升序，1为降序,默认为升序
	public void setCompareFormat(int _No, String _Format, int _Order) {
		No = _No;
		Format = _Format;
		Order = _Order;
		NoArray = new int[1];
		NoArray[0] = No;
		FormatArray = new String[1];
		FormatArray[0] = Format;
		OrderArray = new int[1];
		OrderArray[0] = Order;
	}

	// 设置排序的列数以及格式(对多列进行排序)

	/*
	 * 1)_NoArray设置排序的列数,_FormatArray设置排序的格式,_OrderArray设置排序是按升序还是降序。
	 * index越小优先级越高(例如:对二维数组的第1,3列排序,分别按照String和double排序,第1列要升序,第3列降序) int[]
	 * A={0,2}; String[] B={"string","double"}; int[] C={0,1};
	 * setCompareFormat(A,B,C); 2)如果参数_OrderArray不设置则默认为升序排序。
	 */
	public void setCompareFormat(int[] _NoArray, String[] _FormatArray) {
		NoArray = _NoArray;
		FormatArray = _FormatArray;
		Order = 0;
		OrderArray = new int[NoArray.length];

		for (int index = 0; index < NoArray.length; index++) {
			OrderArray[index] = 0;
		}
	}

	public void setCompareFormat(int[] _NoArray, String[] _FormatArray,
			int[] _OrderArray) {
		NoArray = _NoArray;
		FormatArray = _FormatArray;
		OrderArray = _OrderArray;
	}

	// 得到统计项的行数
	public int getKeyRow() {
		return KeyRow;
	}

	/*
	 * 返回的行数为分组SQL中能查询到的行数 返回的列数为指标SQL中的列数
	 */
	public String[][] calReportItem() {
		SSRS ssrs = calExeSQL.execSQL(keyString);
		int MaxRow = ssrs.getMaxRow();
		logger.debug("MaxRow:" + MaxRow);

		int MaxCol = ssrs.getMaxCol();
		logger.debug("MaxCol:" + MaxCol);
		logger.debug("itemVData.size():" + itemVData.size());

		if (MaxRow == 0) {
			CError tError = new CError();
			tError.buildErr(this, "没有符合条件的统计项！");

			return null;
		}

		if ((MaxCol > returnCol) || (returnCol == 0)) {
			CError tError = new CError();
			tError.buildErr(this, "返回的列小于统计项！");

			return null;
		}

		// 将分组SQL中的主键取出，放入HashMap
		for (int i = 1; i <= MaxRow; i++) {
			VData mVData = new VData();
			VData tVData = new VData();

			// 初始化返回的行数
			for (int j = 1; j <= (returnCol - MaxCol); j++) {
				// 初始化为0
				tVData.add(j - 1, "0");
			}

			for (int j = 1; j <= MaxCol; j++) {
				mVData.add(j - 1, ssrs.GetText(i, j));
			}

			// mVData存储了主键相关的字符串，tVData存储了主键的指标的默认字符串
			if (!tHashMap.containsKey(mVData)) {
				tHashMap.put(mVData, tVData);
			}
		}

		KeyRow = tHashMap.size();
		logger.debug("KeyRow:" + KeyRow);
		logger.debug("开始解析VData中的指标SQL");

		// 解析VData中的指标SQL
		int count = MaxCol;
		int itemcount = 1;

		for (int i = 1; i <= itemVData.size(); i++) {
			String _SQL = (String) itemVData.get(i - 1);
			ssrs.Clear();
			ssrs = calExeSQL.execSQL(_SQL);
			count += (ssrs.MaxCol - MaxCol);

			if (itemcount > (returnCol - MaxCol)) {
				break;
			}

			for (int _Row = 1; _Row <= ssrs.getMaxRow(); _Row++) {
				VData tVData = new VData();

				for (int _Col = 1; _Col <= MaxCol; _Col++) {
					tVData.add(_Col - 1, ssrs.GetText(_Row, _Col));
				}

				if (tHashMap.containsKey(tVData)) {
					VData mVData = new VData();
					mVData = (VData) tHashMap.get(tVData);

					// ssrs.MaxCol-MaxCol为该条sql的指标数，可能为多条
					for (int num = 1, tempitemcount = itemcount; num <= (ssrs.MaxCol - MaxCol); num++, tempitemcount++) {
						mVData.set(tempitemcount - 1, String.valueOf(Double
								.parseDouble(ssrs.GetText(_Row, MaxCol + num))
								+ Double.parseDouble((String) mVData
										.get(tempitemcount - 1))));
					}

					tHashMap.put(tVData, mVData);
				}
			}

			itemcount += (ssrs.MaxCol - MaxCol);
		}

		logger.debug("开始循环HashMap表中的数据！");

		String[][] Result = new String[KeyRow][returnCol];
		int num = 0;

		for (Iterator iter = tHashMap.keySet().iterator(); iter.hasNext();)
		/*
		 * for (Iterator iter = tHashMap.entrySet().iterator(); iter.hasNext();)
		 * { HashMap.Entry entry = (HashMap.Entry) iter.next(); VData keyVData =
		 * (VData) entry.getKey(); VData tempVData = (VData) entry.getValue();
		 */
		{
			VData keyVData = (VData) iter.next();
			VData tempVData = (VData) tHashMap.get(keyVData);

			int i;

			for (i = 1; i <= keyVData.size(); i++) {
				Result[num][i - 1] = (String) keyVData.get(i - 1);
			}

			for (int j = 1; j <= tempVData.size(); i++, j++) {
				if (!returnFormat.toLowerCase().equals("double")) {
					Result[num][i - 1] = this
							.changeDoubleToInt((String) tempVData.get(j - 1));
				} else {
					Result[num][i - 1] = (String) tempVData.get(j - 1);
				}
			}

			num++;
		}

		logger.debug("End HashReport");
		ssrs = null;
		tHashMap.clear();
		tHashMap = null;

		return Result;
	}

	/*
	 * 返回的行数为分组SQL中能查询到的行数 返回的列数为指标SQL中的列数
	 * 提示:用法同calReportItem,但该方法必须保证指标记录的唯一性,因为存在多条记录,String没法进行累加
	 */
	public String[][] calReportItemForString() {
		SSRS ssrs = calExeSQL.execSQL(keyString);
		int MaxRow = ssrs.getMaxRow();
		logger.debug("MaxRow:" + MaxRow);

		int MaxCol = ssrs.getMaxCol();
		logger.debug("MaxCol:" + MaxCol);
		logger.debug("itemVData.size():" + itemVData.size());

		if (MaxRow == 0) {
			CError tError = new CError();
			tError.buildErr(this, "没有符合条件的统计项！");

			return null;
		}

		if ((MaxCol > returnCol) || (returnCol == 0)) {
			CError tError = new CError();
			tError.buildErr(this, "返回的列小于统计项！");

			return null;
		}

		// 将分组SQL中的主键取出，放入HashMap
		for (int i = 1; i <= MaxRow; i++) {
			VData mVData = new VData();
			VData tVData = new VData();

			// 初始化返回的行数
			for (int j = 1; j <= (returnCol - MaxCol); j++) {
				// 初始化为0
				tVData.add(j - 1, "");
			}

			for (int j = 1; j <= MaxCol; j++) {
				mVData.add(j - 1, ssrs.GetText(i, j));
			}

			// mVData存储了主键相关的字符串，tVData存储了主键的指标的默认字符串
			if (!tHashMap.containsKey(mVData)) {
				tHashMap.put(mVData, tVData);
			}
		}

		KeyRow = tHashMap.size();
		logger.debug("KeyRow:" + KeyRow);
		logger.debug("开始解析VData中的指标SQL");

		// 解析VData中的指标SQL
		int count = MaxCol;
		int itemcount = 1;

		for (int i = 1; i <= itemVData.size(); i++) {
			String _SQL = (String) itemVData.get(i - 1);
			ssrs.Clear();
			ssrs = calExeSQL.execSQL(_SQL);
			count += (ssrs.MaxCol - MaxCol);

			if (itemcount > (returnCol - MaxCol)) {
				break;
			}

			for (int _Row = 1; _Row <= ssrs.getMaxRow(); _Row++) {
				VData tVData = new VData();

				for (int _Col = 1; _Col <= MaxCol; _Col++) {
					tVData.add(_Col - 1, ssrs.GetText(_Row, _Col));
				}

				if (tHashMap.containsKey(tVData)) {
					VData mVData = new VData();
					mVData = (VData) tHashMap.get(tVData);

					for (int num = 1, tempitemcount = itemcount; num <= (ssrs.MaxCol - MaxCol); num++, tempitemcount++) {
						// 直接覆盖以前存在的字段
						mVData.set(tempitemcount - 1,
								ssrs.GetText(_Row, MaxCol + num));
					}

					tHashMap.put(tVData, mVData);
				}
			}

			itemcount += (ssrs.MaxCol - MaxCol);
		}

		logger.debug("开始循环HashMap表中的数据！");

		String[][] Result = new String[KeyRow][returnCol];
		int num = 0;

		for (Iterator iter = tHashMap.keySet().iterator(); iter.hasNext();) {
			VData keyVData = (VData) iter.next();
			VData tempVData = (VData) tHashMap.get(keyVData);

			int i;

			for (i = 1; i <= keyVData.size(); i++) {
				Result[num][i - 1] = (String) keyVData.get(i - 1);
			}

			for (int j = 1; j <= tempVData.size(); i++, j++) {
				Result[num][i - 1] = (String) tempVData.get(j - 1);
			}

			num++;
		}

		logger.debug("End HashReportForString");
		ssrs = null;
		tHashMap.clear();
		tHashMap = null;

		return Result;
	}

	/***************************************************************************
	 * 判断传入的字符串是否为数字 如果是数字，则返回true;否则返回false
	 * 只要能Double.parseDouble(str)的都支持，比如:0000.89=0.89
	 **************************************************************************/
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern
				.compile("([\\+\\-])?([0-9])+(.[0-9])?([0-9])*");
		Matcher isNum = pattern.matcher(str);

		if (!isNum.matches()) {
			return false;
		}

		return true;
	}

	/***************************************************************************
	 * 用法: aHashReport.set Arrays.sort(aArray,aHashReport)
	 * 
	 **************************************************************************/
	public int compare(Object o1, Object o2) {
		int flag = -1;
		String[] a = (String[]) o1;
		String[] b = (String[]) o2;
		int index = 0;

		for (int i = 0; i < NoArray.length; i++) {
			index = i;

			if (FormatArray[i].toLowerCase().equals("int")) {
				int Da = Integer.parseInt(a[NoArray[i]]);
				int Db = Integer.parseInt(b[NoArray[i]]);

				if (Da < Db) {
					flag = -1;

					break;
				} else if (Da > Db) {
					flag = 1;

					break;
				} else {
					continue;
				}
			} else if (FormatArray[i].toLowerCase().equals("double")) {
				double Da = Double.parseDouble(a[NoArray[i]]);
				double Db = Double.parseDouble(b[NoArray[i]]);

				if (Da < Db) {
					flag = -1;

					break;
				} else if (Da > Db) {
					flag = 1;

					break;
				} else {
					continue;
				}
			} else // 按照String的排序规则
			{
				String Da = String.valueOf(a[NoArray[i]]);
				String Db = String.valueOf(b[NoArray[i]]);

				if (Da.compareTo(Db) < 0) {
					flag = -1;

					break;
				} else if (Da.compareTo(Db) > 0) {
					flag = 1;

					break;
				} else {
					continue;
				}
			}
		}

		// 降序
		if (OrderArray[index] == 1) {
			flag = -flag;
		}

		return flag;
	}

	// 将内容为浮点型的字符串转化为整形的字符串
	public String changeDoubleToInt(String _aDoubleString) {
		return String.valueOf((int) Double.parseDouble(_aDoubleString));
	}

	/***************************************************************************
	 * 说明:该函数用来生成Excel报表 _createPath为生成Excel文件的路径+文件名 _templatePath为读取模板的路径+文件名
	 * _tableBody为需要显示的表格,在模板中用"&blank"表示 _TransferData支持传入标签
	 **************************************************************************/
	public boolean outputArrayToFile1(String _createPath, String _templatePath,
			String[][] _tableBody, TransferData _TransferData) {
		tTransferData = _TransferData;
		outputArrayToFile1(_createPath, _templatePath, _tableBody);

		return true;
	}

	/***************************************************************************
	 * 说明:该函数用来生成Excel报表 _createPath为生成Excel文件的路径+文件名 _templatePath为读取模板的路径+文件名
	 * _tableBody为需要显示的表格,在模板中用"&blank"表示
	 **************************************************************************/
	public boolean outputArrayToFile1(String _createPath, String _templatePath,
			String[][] _tableBody) {
		FileInputStream is = null;
		FileOutputStream fos = null;
		try {
			is = new FileInputStream(_templatePath);
			fos = new FileOutputStream(_createPath);
			BookModelImpl bm = new BookModelImpl();
			bm.read(is, new com.f1j.ss.ReadParams());

			int NumSheets = bm.getNumSheets();
			NumSheets = 1;

			for (int sheetNum = 0; sheetNum < NumSheets; sheetNum++) {
				bm.setSheetSelected(sheetNum, true);
				bm.setSheet(sheetNum);
				int ExcelRow = bm.getLastRow();
				int ExcelCol = bm.getLastCol();
				for (int i = 0; i <= ExcelRow; i++) {
					boolean flag = false;

					for (int j = 0; j <= ExcelCol; j++) {
						if (bm.getText(i, j).toLowerCase().equals("&blank")
								&& !flag) {
							BodyRow = i + 1;
							BodyCol = j + 1;
							flag = true;
						} else {
							for (int k = 0; k < tTransferData.getValueNames()
									.size(); k++) {
								String ValueName = (String) tTransferData
										.getValueNames().get(k);

								if (bm.getText(i, j).indexOf(ValueName) != -1) {
									bm.setEntry(
											sheetNum,
											i,
											j,
											bm.getText(i, j)
													.replaceAll(
															ValueName,
															(String) tTransferData
																	.getValueByName(tTransferData
																			.getValueNames()
																			.get(k))));
								}
							}
						}
					}
				}
				int BodyRowSize = _tableBody.length;

				for (int i = 0; i < BodyRowSize; i++) {
					ExcelRow = bm.getLastRow();
					ExcelCol = bm.getLastCol();

					int BodyColSize = _tableBody[i].length;

					if (i != 0) {
						bm.setAllowMoveRange(true);

						// 看文档D:\Formula One 9 for Java\help\swingdoc\index.html
						bm.insertRange((BodyRow + i) - 1, 0, (BodyRow + i) - 1,
								ExcelCol, Short.parseShort(String.valueOf(3)));
						bm.copyRange((BodyRow + i) - 1, 0, (BodyRow + i) - 1,
								ExcelCol, bm, (BodyRow + i) - 1 - 1, 0,
								(BodyRow + i) - 1 - 1, ExcelCol,
								Short.parseShort(String.valueOf(7)));

						for (int j = 0; j < BodyColSize; j++) {
							// if(!_tableBody[i][j].equals(""))
							bm.setEntry(sheetNum, BodyRow - 1 + i, BodyCol - 1
									+ j, "");
						}
					}

					for (int j = 0; j < BodyColSize; j++) {
						if (!_tableBody[i][j].equals(""))
							bm.setEntry(sheetNum, BodyRow - 1 + i, BodyCol - 1
									+ j, _tableBody[i][j]);

					}
				}
				for (int i = 0; i <= sheetNum; i++) {
					bm.setSheetSelected(i, false);
				}
			}
			bm.write(fos, new com.f1j.ss.WriteParams(
					com.f1j.ss.BookModelImpl.eFileExcel97And5));
			bm.destroy();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
		}

		return true;
	}

	/***************************************************************************
	 * 该方法将二维数组转化为文件 参数_createPath为生成文件的路径+文件名
	 * 参数_tableHead为表头信息，支持多行多列的情况，起始位置为左上方 参数_tableBody为表格内容信息
	 **************************************************************************/
	public boolean outputArrayToFile(String _createPath, String[][] _tableHead,
			String[][] _tableBody) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(_createPath);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFCellStyle cs = wb.createCellStyle();
			cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			wb.setSheetName(0, "ReportList");

			// 写文件头
			int HeadRowSize = _tableHead.length;

			if (BodyRow < (HeadRowSize + 1)) {
				BodyRow = HeadRowSize + 1;
			}

			for (int i = 0; i < HeadRowSize; i++) {
				HSSFRow row = s.createRow(i);
				int HeadColSize = _tableHead[i].length;

				for (int j = 0; j < HeadColSize; j++) {
					HSSFCell cell = row.createCell((short)Integer.parseInt(String
							.valueOf(j)));
					// cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16); // 支持中文
					cell.setCellValue(_tableHead[i][j]);
					cell.setCellStyle(cs);

					if ((_tableHead[i][j] != null)
							&& _tableHead[i][j].equals("") && (j > 0)) {
						//s.addMergedRegion(new CellRangeAddress(i, (j - 1), i, j));
						
						Region region1 = new Region(i, (short)(j - 1), i,(short)j);
						s.addMergedRegion(region1);
					}
				}
			}

			int BodyRowSize = _tableBody.length;

			for (int i = 0; i < BodyRowSize; i++) {
				HSSFRow row_Data = s.createRow((BodyRow + i) - 1);
				int BodyColSize = _tableBody[i].length;

				for (int j = 0; j < BodyColSize; j++) {
					HSSFCell cell_Data = row_Data.createCell((short)Integer
							.parseInt(String.valueOf(((BodyCol + j) - 1))));
					// cell_Data.setEncoding(HSSFCell.ENCODING_UTF_16); // 支持中文

					cell_Data.setCellValue(_tableBody[i][j]);
				}
			}

			wb.write(fos);
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();

			CError.buildErr(this, ex.getMessage());
			return false;
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception e) {
			}
		}

		return true;
	}

	/***************************************************************************
	 * 该方法将二维数组转化为文件 参数_createPath为生成文件的路径+文件名
	 * 参数_tableHead为表头信息，支持多行多列的情况，起始位置为左上方 参数_tableBody为表格内容信息
	 * 参数_BodyRow为表格内容的起始行数，初始化行数为2，起始行数为1 参数_BodyCol为表格内容的起始列数，初始化列数为1，起始列数为1
	 **************************************************************************/
	public boolean outputArrayToFile(String _createPath, String[][] _tableHead,
			String[][] _tableBody, int _BodyRow, int _BodyCol) {
		if (_BodyRow <= _tableHead.length) {
			CError tError = new CError();
			tError.buildErr(this, "传入的表格起始行号应该大于表头的行数" + _tableHead.length);

			return false;
		}

		if (_BodyCol < BodyCol) {
			CError tError = new CError();
			tError.buildErr(this, "传入的表格起始列号不应该小于" + BodyCol);

			return false;
		}

		BodyRow = _BodyRow;
		BodyCol = _BodyCol;
		outputArrayToFile(_createPath, _tableHead, _tableBody);

		return true;
	}

	/***************************************************************************
	 * 说明:该函数用来生成Excel报表，主要用在动态格式定制,动态的表格能自动加上粗边框
	 * 该功能支持大数据量的Excel显示，将会根据打印数量动态生成sheet。 _createPath为生成Excel文件的路径+文件名
	 * _templatePath为读取模板的路径+文件名 _tableBody为需要显示的表格,在模板中用"&blank"表示 *
	 * _TransferData支持传入标签
	 **************************************************************************/
	public boolean createReportFile(String _createPath, String _templatePath,
			String[][] _tableBody, String[] _format, TransferData _TransferData) {
		/* 设定每个sheet中表格的最大行数 */
		int basecount = 60001;
		try {
			FileOutputStream fos = new FileOutputStream(_createPath);
			FileInputStream InputStream = new FileInputStream(_templatePath);
			BookModelImpl bm = new BookModelImpl();
			bm.read(InputStream, new com.f1j.ss.ReadParams());
			CellFormat tCellFormat = new CellFormat();
			int NumSheets = bm.getNumSheets();
			int BodyRowSize = _tableBody.length;
			int BodyColSize = _tableBody[0].length;
			NumSheets = BodyRowSize / basecount + 1;

			for (int sheetNum = 0; sheetNum < NumSheets; sheetNum++) {
				logger.debug("sheetNum:" + sheetNum);
				int SheetRowSize = 0;
				if ((BodyRowSize - sheetNum * basecount) / basecount >= 1) {
					SheetRowSize = basecount;
				} else {
					SheetRowSize = BodyRowSize - sheetNum * basecount;
				}
				bm.setSheetSelected(sheetNum, true);
				bm.setSheet(sheetNum);

				int ExcelRow = bm.getLastRow();
				int ExcelCol = bm.getLastCol();
				logger.debug("ExcelRow:" + ExcelRow);
				logger.debug("ExcelCol:" + ExcelCol);

				for (int i = 0; i <= ExcelRow; i++) {
					boolean flag = false;

					for (int j = 0; j <= ExcelCol; j++) {
						if (bm.getText(i, j).toLowerCase().equals("&blank")
								&& !flag) {
							BodyRow = i + 1;
							BodyCol = j + 1;
							flag = true;
						} else {
							for (int k = 0; k < _TransferData.getValueNames()
									.size(); k++) {
								String ValueName = (String) _TransferData
										.getValueNames().get(k);

								if (bm.getText(i, j).indexOf(ValueName) != -1) {
									bm.setEntry(
											sheetNum,
											i,
											j,
											bm.getText(i, j)
													.replaceAll(
															ValueName,
															(String) _TransferData
																	.getValueByName(_TransferData
																			.getValueNames()
																			.get(k))));
								}
							}
						}
					}
				}

				ExcelRow = bm.getLastRow();
				ExcelCol = bm.getLastCol();
				for (int j = 0; j < BodyColSize; j++) {
					logger.debug("BodyColSize:" + j);
					logger.debug("SheetRowSize:" + SheetRowSize);
					for (int i = 0; i < SheetRowSize; i++) {
						int k = 0;
						if (sheetNum > 0)
							k = 1;
						if (sheetNum == 0 && i == 1) {
							bm.copyRange(BodyRow, j, BodyRow + SheetRowSize - 1
									- 1, j, bm, BodyRow - 1, j, BodyRow - 1, j,
									Short.parseShort(String.valueOf(4)));
						} else if (sheetNum > 0 && i == 0) {
							bm.copyRange(BodyRow, j, BodyRow + SheetRowSize - 1
									- 1 + k, j, bm, BodyRow - 1, j,
									BodyRow - 1, j,
									Short.parseShort(String.valueOf(4)));
						}

						if (!_tableBody[i][j].equals("")) {
							if (_format[j] != null
									&& _format[j].toLowerCase()
											.equals("double") && i >= 1) {
								bm.setNumber(
										sheetNum,
										BodyRow - 1 + i + k,
										BodyCol - 1 + j,
										Double.parseDouble(_tableBody[i
												+ sheetNum * basecount][j]));
							} else if (_format[j] != null
									&& _format[j].toLowerCase().equals("int")
									&& i >= 1) {
								bm.setNumber(
										sheetNum,
										BodyRow - 1 + i + k,
										BodyCol - 1 + j,
										Integer.parseInt(_tableBody[i
												+ sheetNum * basecount][j]));
							} else
								bm.setEntry(sheetNum, BodyRow - 1 + i + k,
										BodyCol - 1 + j, _tableBody[i
												+ sheetNum * basecount][j]);
							tCellFormat.setLeftBorder(Short.parseShort("2"));
							tCellFormat.setTopBorder(Short.parseShort("2"));
							tCellFormat.setRightBorder(Short.parseShort("2"));
							tCellFormat.setBottomBorder(Short.parseShort("2"));
							bm.setCellFormat(tCellFormat, BodyRow - 1 + i + k,
									BodyCol - 1 + j, BodyRow - 1 + i + k,
									BodyCol - 1 + j);
						}
					}
				}
				if (sheetNum + 1 < NumSheets) {
					bm.insertSheets(sheetNum + 1, 1);
					// 从第一个sheet开始将格式复制到后一个sheet的内容
					bm.copyRange(sheetNum + 1, 0, 0, BodyRow - 1, BodyCol - 1
							+ BodyColSize, bm, sheetNum, 0, 0, BodyRow - 1,
							BodyCol - 1 + BodyColSize,
							Short.parseShort(String.valueOf(7)));
				}
			}
			// for (int i = 0; i < NumSheets; i++)
			// {
			// bm.setSheetSelected(i, false);
			// }
			// bm.setSheetSelected(0, false);

			bm.write(fos, new com.f1j.ss.WriteParams(
					com.f1j.ss.BookModelImpl.eFileExcel97And5));
			bm.destroy();
			fos.close();
			InputStream.close();
			bm = null;
			fos = null;
			InputStream = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return true;
	}

	public static void main(String[] args) {
	}
}
