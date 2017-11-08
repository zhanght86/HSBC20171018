/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 * Change Log
 * ---------------------------------
 * Yang Yalin 2005/11/15  增加模板缓存功能,提高处理效率.
 */
package com.sinosoft.utility;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.ss.CellFormat;
import com.f1j.ss.Constants;
import com.f1j.ss.GRObject;
import com.f1j.ss.GRObjectPos;
import com.f1j.util.Format;
import com.sinosoft.report.f1report.BarCode;
import com.sinosoft.report.f1report.JRptUtility;

/**
 * <p>
 * Title: Life Information System
 * </p>
 * <p>
 * Description: 解析模板vts文件，并用数据文件中的内容替换它，从而生成最后要打印的 vts文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author kevin
 * @version 1.0
 */
public class F1PrintParser {
private static Logger logger = Logger.getLogger(F1PrintParser.class);
	protected static final char FLAG_FLAG = '$';

	protected static final String FLAG_ACRMULTI_RECORD = "$+";
	protected static final String FLAG_MULTI_RECORD = "$*";
	protected static final String FLAG_ONE_RECORD = "$=";
	protected static final String FLAG_DISPLAY_START = "$<";
	protected static final String FLAG_DISPLAY_END = "$>";
	protected static final String FLAG_MODEL_END = "$/";
	protected static final String FLAG_PAGE_BREAK = "$B";
	protected static final String FLAG_FULL_ROW = "$R";
	protected static final String FLAG_DATA_LIST = "$L";
	protected static final String FLAG_GROUP_SUM = "$G";

	protected F1Print m_fp = null;
	protected String m_strTemplatePath = "";
	protected com.f1j.ss.BookModelImpl m_bmOutput;

	private Logger m_log = Logger.getLogger(F1PrintParser.class);

	public F1PrintParser(InputStream in, String strTemplatePath)
			throws FileNotFoundException, IOException {

		m_fp = new F1Print(in);
		m_strTemplatePath = strTemplatePath;
	}

	/**
	 * 指定是否使用模板缓存,如果使用,应当显式调用destory回收资源.
	 */
	protected boolean useCache = false;

	public F1PrintParser(InputStream in, String strTemplatePath,
			boolean useCache) throws FileNotFoundException, IOException {
		this(in, strTemplatePath);
		this.useCache = useCache;
	}

	/**
	 * 解析并将结果输出到输出流中
	 * 
	 * @param out
	 *            OutputStream
	 * @return boolean
	 */
	public boolean output(OutputStream out) {
		try {
			return this.output(out, Constants.eFileCurrentFormat);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 解析并将结果输出到输出流中
	 * 
	 * @param out
	 *            OutputStream
	 * @return boolean
	 */
	public boolean output(OutputStream out, short fileFormat) {
		// 同步F1PrintParser，独占解析，防止由于并发引起问题
		// kevin 2006-10-12
		synchronized (F1PrintParser.class) {
			try {
				m_log.info("VTS Template = " + m_fp.getTemplate());
				if (m_fp.getTemplate().equals("new.vts")) {
					return true;
				}
				if (parse()) {
					m_bmOutput.write(out,
							new com.f1j.ss.WriteParams(fileFormat));

					this.destroy();

					return true;
				}
				return false;
			} catch (Exception ex) {
				ex.printStackTrace();
				m_log.error("output", ex);
				return false;
			}
		}
	}

	com.f1j.ss.BookModelImpl bmTemplate;

	/**
	 * 解析操作
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	protected boolean parse() throws Exception {
		String strTemplateFile = m_strTemplatePath + m_fp.getTemplate();
		m_log.info("VTS Template = " + strTemplateFile);

		com.f1j.ss.CellFormat cellFormat = null;

		if (bmTemplate == null) {
			bmTemplate = new com.f1j.ss.BookModelImpl();
			bmTemplate.read(strTemplateFile, new com.f1j.ss.ReadParams());
		}

		// 在这里进行显示控制的处理
		controlDisplay(bmTemplate, m_fp);

		// 2006-07-24 Kevin
		// Error, which makes F1PrintParser crashed, occures in function
		// copyAll() because of unknown reason,
		// I have to call controlDisplay() again.
		m_bmOutput = new com.f1j.ss.BookModelImpl(); // 同一对象错误,切切

		int nMaxRow = bmTemplate.getLastRow();
		int nMaxCol = bmTemplate.getMaxCol();

		m_bmOutput.copyAll(bmTemplate);
		int nMaxColForRow = 0;
//		int nMaxCol = 0;
//		
		// we have get all format we needed, now get a blank vts file for output
		m_bmOutput.clearRange(0, 0, bmTemplate.getMaxRow(), bmTemplate
				.getMaxCol(), com.f1j.ss.BookModelImpl.eClearValues);
		cellFormat = bmTemplate.getCellFormat(0, 0, 0, 0);

		int nNewRow = 0;
		int nNewCol = 0;
		// int nListLength = 0;
		int nListLengthCol = 0;
		int nRowHeight = 0;
		int nColWidth = 0;
		int nNewMaxRow = 0;
		int nNewMaxCol = 0;
		// String strText, strSubText, strXPath, strNodeName, strNodeValue;
		String strText, strSubText, strXPath, strNodeName;
		String[] strListVal = null;
		int nChildPos = 0;
		int nCurrentRow = 0;
		int nCurrentCol = 0;
		int nRow, nCol;

		// 在一行扫描解析过程中，最大的“动态增加的行”的数量（解析“$*”标签而出现“动态增加的行”）。
		int nMaxListLength = 0;

		for (nRow = 0; nRow <= nMaxRow; nRow++) {
			nNewRow += nMaxListLength;
			nMaxListLength = 0;

			nRowHeight = bmTemplate.getRowHeight(nRow);
			// nNewMaxRow = nNewMaxRow < nNewRow + 1 ? nNewRow + 1 : nNewMaxRow;
			if (nNewMaxRow < nNewRow + 1) {
				nNewMaxRow = nNewRow + 1;
			}
			nMaxColForRow = bmTemplate.getLastColForRow(nRow);
			if (nMaxColForRow < 0)
				nMaxColForRow = 0;
			boolean ismultirowadd = false;
			boolean ismultirow=false;
			logger.debug("row:" + nRow);
			for (nCol = 0; nCol <= nMaxColForRow; nCol = bmTemplate.getSelEndCol() + 1) {

				nColWidth = bmTemplate.getColWidth(nCol);
				nNewCol = nCol;
				nNewCol += nListLengthCol;
				nListLengthCol = 0;
				strText = bmTemplate.getText(nRow, nCol);
				bmTemplate.setActiveCell(nRow, nCol);
				if (strText.length() == 0) {
					continue;
				} else if (strText.length() > 1) {
					strSubText = strText.substring(0, 2);
				} else {
					strSubText = strText;
				}
				bmTemplate.getCellFormat(cellFormat);
				cellFormat.useAllFormats();

				if (strSubText.equals(FLAG_MODEL_END)) {
					break;

				} else if (strSubText.equals(FLAG_MULTI_RECORD)) {
					strText = strText.substring(2);
					nChildPos = strText.lastIndexOf("/");
					strXPath = "/DATASET" + strText.substring(0, nChildPos);
					strNodeName = strText.substring(nChildPos + 1);

					m_fp.query(strXPath);
					int nColIndex = m_fp.getColIndex(strNodeName);

					nCurrentRow = nNewRow;
					ismultirow=true;
					int nListLen = 0;
					logger.debug("col:" + nCol);
					if (!ismultirowadd) {
						ismultirowadd = true;
						nListLen = m_fp.getCount(strXPath);
						if (nListLen > 1)
							m_bmOutput.insertRange(nCurrentRow + 1, nNewCol,
									nCurrentRow + nListLen - 1, nNewCol,
									com.f1j.ss.BookModelImpl.eShiftRows);
						
						nMaxListLength = nListLen;
					}
					while (m_fp.next()) {
						if (strText.length() > 0) {
							setValue(m_bmOutput, cellFormat, nCurrentRow,
									nNewCol, m_fp.getString(nColIndex));
						}

						// m_bmOutput.setRowHeight(nCurrentRow, nRowHeight);
						// m_bmOutput.setSelection(nCurrentRow, nNewCol,
						// nCurrentRow, nNewCol);
						// m_bmOutput.setCellFormat(cellFormat);

//						nListLen++;
						nCurrentRow++;
					}

					// 2006-07-24 Kevin
					// 一次设置多行的格式
//					if (nListLen > 0) {
//						m_bmOutput
//								.copyRange(nNewRow, nNewCol, nNewRow + nListLen
//										- 1, bmTemplate.getSelEndCol(),
//										bmTemplate, nRow, nCol, nRow,
//										bmTemplate.getSelEndCol(),
//										Constants.eCopyFormats);
//					}
					// if (nListLen > nListLength) {
					// nListLength = nListLen;
					// }
					//
					if (nListLen > nMaxListLength) {
						nMaxListLength = nListLen;
					}

				} else if (strSubText.equals(FLAG_ACRMULTI_RECORD)) {
					strText = strText.substring(2);
					nChildPos = strText.lastIndexOf("/");
					strXPath = "/DATASET" + strText.substring(0, nChildPos);
					strNodeName = strText.substring(nChildPos + 1);
					strListVal = m_fp.getNodeListValue(strXPath, strNodeName);
					if (nListLengthCol < strListVal.length) {
						nListLengthCol = strListVal.length;
					}
					for (int k = 0; k < strListVal.length; k++) {
						nCurrentCol = nNewCol + k;
						if (k > 0 && nNewRow == 0) {
							m_bmOutput.insertRange(nNewRow, nCurrentCol,
									nNewRow, nCurrentCol, Constants.eShiftRows);
						}
						if (strText.length() > 0) {
							setValue(m_bmOutput, cellFormat, nNewRow,
									nCurrentCol, strListVal[k]);
						}
						m_bmOutput.setColWidth(nCurrentCol, nColWidth);
						// m_bmOutput.setRowHeight(nCurrentRow, nRowHeight);
						m_bmOutput.setSelection(nNewRow, nCurrentCol, nNewRow,
								nCurrentCol);
						m_bmOutput.setCellFormat(cellFormat);
					}

				} else if (strSubText.equals(FLAG_PAGE_BREAK)) {
					m_bmOutput.addRowPageBreak(nNewRow);

				} else if (strSubText.equals(FLAG_FULL_ROW)) {
					// 一次解析一行的数据
					strXPath = "/DATASET" + strText.substring(2);

					m_fp.query(strXPath);

					int nColCount = m_fp.getColCount();
					int nColIndex = 0;

					nCurrentRow = nNewRow;

					int nListLen = 0;

					while (m_fp.next()) {
						if (nListLen > nMaxListLength) {
							m_bmOutput.insertRange(nCurrentRow, nNewCol,
									nCurrentRow, nNewCol + nColCount,
									Constants.eShiftRows);
						}

						for (nColIndex = 0; nColIndex < nColCount; nColIndex++) {

							bmTemplate.setSelection(nRow, nCol + nColIndex,
									nRow, nCol + nColIndex);
							cellFormat = bmTemplate.getCellFormat();
							cellFormat.useAllFormats();

							setValue(m_bmOutput, cellFormat, nCurrentRow,
									nNewCol + nColIndex, m_fp
											.getString(nColIndex));

							m_bmOutput.setSelection(nCurrentRow, nNewCol
									+ nColIndex, nCurrentRow, nNewCol
									+ nColIndex);
							m_bmOutput.setCellFormat(cellFormat);

						}

						nListLen++;
						nCurrentRow++;
						m_bmOutput.setRowHeight(nCurrentRow, nRowHeight);
					}

					nListLengthCol = nColCount;

					// if (nListLen > nListLength) {
					// nListLength = nListLen;
					// }

					if (nListLen > nMaxListLength) {
						nMaxListLength = nListLen;
					}

				} else if (strSubText.equals(FLAG_DATA_LIST)) {
					// 覆盖行而不插入行
					strText = strText.substring(2);
					nChildPos = strText.lastIndexOf("/");
					strXPath = "/DATASET" + strText.substring(0, nChildPos);
					strNodeName = strText.substring(nChildPos + 1);

					m_fp.query(strXPath);
					int nColIndex = m_fp.getColIndex(strNodeName);

					nCurrentRow = nNewRow;

					int nListLen = 0;

					while (m_fp.next()) {

						if (strText.length() > 0) {
							setValue(m_bmOutput, cellFormat, nCurrentRow,
									nNewCol, m_fp.getString(nColIndex));
						}

						// m_bmOutput.setRowHeight(nCurrentRow, nRowHeight);
						// m_bmOutput.setSelection(nCurrentRow, nNewCol,
						// nCurrentRow, nNewCol);
						// m_bmOutput.setCellFormat(cellFormat);
						//
						nListLen++;
						nCurrentRow++;
					}

					// 2006-07-24 Kevin
					// 一次设置多行的格式
					if (nListLen > 0) {
						m_bmOutput
								.copyRange(nNewRow, nNewCol, nNewRow + nListLen
										- 1, bmTemplate.getSelEndCol(),
										bmTemplate, nRow, nCol, nRow,
										bmTemplate.getSelEndCol(),
										Constants.eCopyFormats);
					}

					if (nListLen > nMaxListLength) {
						nMaxListLength = nListLen;
					}

				} else if (strSubText.equals(FLAG_GROUP_SUM)) { // 分类汇总
					strText = strText.substring(2);
					String[] strItems = strText.split(",");

					String strFuncID = strItems[0]; // 计算函数编号
					int nGroupColIndex = findColIndex(bmTemplate, nRow,
							strItems[1]); // 分类类的列序号
					if (nGroupColIndex == -1) {
						logger.debug("找不到分类列[" + strItems[1] + "]的列序号");
						return false;
					}

					int[] nSumColIndex = new int[strItems.length - 2]; // 分配汇总列数组
					int nIndex = 0;

					for (nIndex = 0; nIndex < nSumColIndex.length; nIndex++) {
						nSumColIndex[nIndex] = findColIndex(bmTemplate, nRow,
								strItems[nIndex + 2]);
						if (nSumColIndex[nIndex] == -1) {
							logger.debug("找不到分类列[" + strItems[nIndex + 2]
									+ "]的列序号");
							return false;
						}
					}

					// nNewRow变量中保存着当前的数据起始行
					// nListLength变量中保存着数据行的个数
					nCurrentRow = nNewRow + nMaxListLength - 1;

					// 获取最后一行的数据
					String strGroupColValue = null;
					String strOldGroupColValue = null; // 保存上一个分类列的值
					double[] dSumColValue = new double[strItems.length - 2]; // 保存汇总列的数值

					// 取得最后一行的明细数据。注意，必须是明细数据。如果在这一行之前做过分类汇总的解析操作，这样在数据行中不仅包含明细数据行，
					// 也包含汇总数据行，所以在这里需要注意区分一下。在下面的操作中也有这样的问题。
					while (nCurrentRow >= nNewRow) {
						if (!m_bmOutput.getText(nCurrentRow, nGroupColIndex)
								.equals("")) {
							strOldGroupColValue = m_bmOutput.getText(
									nCurrentRow, nGroupColIndex); // 保存上一个分类列的值
							break;
						}
						nCurrentRow--;
					}

					// 为了避免动态增加数据行对循环遍历清单列表数据的影响，
					// 我们从数据行的最后一行往前遍历。
					int nInsertedRow = nCurrentRow + 1;
					int nColIndex = 0;

					while (nCurrentRow >= nNewRow) {
						// 需要判断是否是数据行。如何判断？
						strGroupColValue = m_bmOutput.getText(nCurrentRow,
								nGroupColIndex);

						// 判断是否是数据行，就是判断分类列的值是否为空。为空，就不是数据行。而是汇总行。
						if (!strGroupColValue.equals("")) {
							if (strGroupColValue.equals(strOldGroupColValue)) {
								getGroupSum(m_bmOutput, nCurrentRow,
										nSumColIndex, dSumColValue, true);
							} else {
								// 插入一行，并且进行格式化。
								insertRowWithFormat(bmTemplate, nRow,
										m_bmOutput, nInsertedRow, nCol);
								m_bmOutput.setText(nInsertedRow,
										nGroupColIndex, "汇总");
								for (nIndex = 0; nIndex < nSumColIndex.length; nIndex++) {
									nColIndex = nSumColIndex[nIndex];

									setValue(
											m_bmOutput,
											cellFormat,
											nInsertedRow,
											nColIndex,
											String
													.valueOf(dSumColValue[nIndex]));

								}

								nInsertedRow = nCurrentRow + 1;
								strOldGroupColValue = strGroupColValue;
								getGroupSum(m_bmOutput, nCurrentRow,
										nSumColIndex, dSumColValue, false);

								// nListLength++;
								nMaxListLength++;
							}
						}
						nCurrentRow--;
					}

					// 最后一个边界条件的检查
					insertRowWithFormat(bmTemplate, nRow, m_bmOutput,
							nInsertedRow, nCol);
					m_bmOutput.setText(nInsertedRow, nGroupColIndex, "汇总");
					for (nIndex = 0; nIndex < nSumColIndex.length; nIndex++) {
						m_bmOutput.setText(nInsertedRow, nSumColIndex[nIndex],
								String.valueOf(dSumColValue[nIndex]));
					}

					// nInsertRowIndex = nCurrentRow + 1;
					// strOldGroupColValue = strGroupColValue;
					// getGroupSum(m_bmOutput, nCurrentRow, nSumColIndex, dSumColValue, false);
					//
					// nListLength++;
					nMaxListLength++;
				} else {
					setValue(m_bmOutput, cellFormat, nNewRow, nNewCol,
							parseString(strText, m_fp));
//					m_bmOutput.setSelection(nNewRow, nNewCol, nNewRow, nNewCol);
//					m_bmOutput.setCellFormat(cellFormat);
					// m_bmOutput.setRowHeight(nNewRow, nRowHeight);
					// if (nMaxListLength < 1) {
					// nMaxListLength = 1;
					// }
				}
			}
			// end of for(nCol = 0; nCol < nMaxCol - 1; nCol ++)

			if (nNewMaxCol < nNewCol + 1) {
				nNewMaxCol = nNewCol + 1;
			}
			
			// modify nMaxListLength
			if (nMaxListLength < 1) {
				nMaxListLength = 1;
			}
			
			if (ismultirow && nMaxListLength>1)
			{
				logger.debug("Start set format,listLength:"+nMaxListLength);
				m_bmOutput.copyRange(nNewRow+1 , 0, nNewRow+(nMaxListLength-1>3000?3000:nMaxListLength-1)
						, nMaxColForRow, bmTemplate,
						nRow, 0, nRow, nMaxColForRow,
						BookModelImpl.eCopyFormats);
				logger.debug("Finish set format");
			}
			
			logger.debug("finish row:" + nRow + "\tcol:" + nCol);
			if (nMaxCol < nMaxColForRow)
				nMaxCol = nMaxColForRow;
			if (0 == nCol) {
				break;
			}
		}

		// --------------------------add by wangyc:处理条形码,最多有10个条形码
		GRObject gr = null;
		String sCode = null;
		String sParam = null;
		for (int k = 1; k <= 10; k++) {
			gr = bmTemplate.getObject("BarCode" + k);
			sCode = m_fp.getNodeValue("/DATASET/BarCode" + k);
			sParam = m_fp.getNodeValue("/DATASET/BarCodeParam" + k);

			if (gr != null && sCode != null && !sCode.equalsIgnoreCase("")) {
				String t_Str = "";
				BarCode bcode = new BarCode(sCode);
				// 获得条形码参数
				if (sParam != null && !sParam.equalsIgnoreCase("")) {
					String params[] = sParam.split("&");
					for (int j = 0; j < params.length; j++) {
						// 获得条形码宽度
						if (params[j].toLowerCase().startsWith("barheight")) {
							t_Str = params[j].split("=")[1];
							if (JRptUtility.IsNumeric(t_Str)) {
								bcode.setBarHeight(Integer.parseInt(t_Str));
							}
						}
						// 获得条形码宽度
						if (params[j].toLowerCase().startsWith("barwidth")) {
							t_Str = params[j].split("=")[1];
							if (JRptUtility.IsNumeric(t_Str)) {
								bcode.setBarWidth(Integer.parseInt(t_Str));
							}
						}
						// 获得条形码粗细线条比例
						if (params[j].toLowerCase().startsWith("barratio")) {
							t_Str = params[j].split("=")[1];
							if (JRptUtility.IsNumeric(t_Str)) {
								bcode.setBarRatio(Integer.parseInt(t_Str));
							}
						}
						// 获得条形码图片背景色
						if (params[j].toLowerCase().startsWith("bgcolor")) {
							t_Str = params[j].split("=")[1];
							bcode.setBgColor(t_Str);
						}
						// 获得条形码颜色
						if (params[j].toLowerCase().startsWith("forecolor")) {
							t_Str = params[j].split("=")[1];
							bcode.setForeColor(t_Str);
						}
						// 获得条形码图片横向空白区长度
						if (params[j].toLowerCase().startsWith("xmargin")) {
							t_Str = params[j].split("=")[1];
							if (JRptUtility.IsNumeric(t_Str)) {
								bcode.setXMargin(Integer.parseInt(t_Str));
							}
						}
						// 获得条形码图片竖向空白区长度
						if (params[j].toLowerCase().startsWith("ymargin")) {
							t_Str = params[j].split("=")[1];
							if (JRptUtility.IsNumeric(t_Str)) {
								bcode.setYMargin(Integer.parseInt(t_Str));
							}
						}
					}
				}
				bcode.setFormatType(BarCode.FORMAT_GIF);
				GRObjectPos pos = gr.getPos();
				m_bmOutput.addPicture(pos.getX1(), pos.getY1(), pos.getX2(),
						pos.getY2(), bcode.getBytes());

			}
		}
		m_bmOutput.objectBringToFront();

		// ---------------------------end

		m_bmOutput.setSelection(0, 0, nNewMaxRow - 1, nNewMaxCol - 1);
		m_bmOutput.setPrintArea();

		m_bmOutput.setAllowSelections(false); // 打印显示界面不能选中
		m_bmOutput.setAllowObjectSelections(false); // 打印显示界面不能选中
		m_bmOutput.setShowGridLines(false); // 去掉显示中的网格
		m_bmOutput.setShowColHeading(false); // 去掉显示的列头
		m_bmOutput.setShowRowHeading(false); // 去掉显示的行头
		m_bmOutput.setShowEditBar(false); // 去掉显示的bar
		m_bmOutput.setShowEditBarCellRef(false); // 去掉显示的bar
		m_bmOutput.setShowTabs(Constants.eTabsOff); // 去掉显示的sheet

		// 不显示选中的区域，缺省会用黑色的边框将选中的区域标注出来。
		m_bmOutput.setShowSelections(Constants.eShowOff);
		// m_bmOutput.setShowVScrollBar(com.f1j.ss.BookModelImpl.eShowOff);
		// m_bmOutput.setShowHScrollBar(com.f1j.ss.BookModelImpl.eShowOff);

		m_bmOutput.setPrintGridLines(false); // 在打印时去掉网格
		m_bmOutput.setPrintHeader(""); // 在打印时去掉头标题
		m_bmOutput.setPrintFooter(""); // 在打印时去掉页码
		m_bmOutput.setActiveCell(0, 0);
		m_bmOutput.showActiveCell();
		m_bmOutput.saveViewInfo();

		// Yangyl Added at 2005/9/12 20:10
		m_log.info("00 00 00 模板为:[" + m_fp.getTemplate() + "];数据行数为:["
				+ nNewRow + "]");

		cellFormat = null;
		if (!useCache) {
			if (this.bmTemplate == null) {
				this.bmTemplate.destroy();
				this.bmTemplate = null;
			}
		}

		return true;
	}

	/**
	 * 去掉模板中不显示的部分。模板中的显示控制用“$</name”和“$>”来表示，显示控制只
	 * 能出现在第一列，如果在数据xml文件的“/DataSet/Control/”节点下没有找到对应的“name” 值，则将“$</name”和“$>”之间的行全部删除，包括控制所在的两行。
	 * 在目前的实现中，显示控制不支持嵌套。
	 * 
	 * @param bmTemplate
	 *            BookModelImpl
	 * @param fp
	 *            F1Print
	 * @throws Exception
	 */
	protected static void controlDisplay(BookModelImpl bmTemplate, F1Print fp)
			throws Exception {
		int nRow = 0;
		int nEndRow = 0; // end row of delete range
		boolean bNeedDelete = false;
		char[] cDelCtrl = null;

		cDelCtrl = new char[bmTemplate.getLastRow() + 1];

		// In the first scan, we keep track whether a line should be deleted in
		// cDelCtrl. 'D' for delete; 'R' for reserve.
		nRow = 0;

		for (nRow = 0; nRow <= bmTemplate.getLastRow(); nRow++) {
			// First, we assume that this line will be reserved
			cDelCtrl[nRow] = 'R';

			String strText = bmTemplate.getText(nRow, 0);
			if (strText != null && strText.length() > 1) {
				String strSubText = strText.substring(0, 2);
				if (strSubText.equals(FLAG_DISPLAY_START)) {
					if (fp.getDisplayControl(strText.substring(2)).equals("")) {
						bNeedDelete = true;
					}

					// The display control should be deleted itself.
					cDelCtrl[nRow] = 'D';
				} else if (strSubText.equals(FLAG_DISPLAY_END)) {
					bNeedDelete = false;

					// The display control should be deleted itself.
					cDelCtrl[nRow] = 'D';

				} else if (strSubText.equals(FLAG_MODEL_END)) {
					break; // When end of model is found, jump out of loop

				} else {
					if (bNeedDelete) {
						cDelCtrl[nRow] = 'D';
					}
				}
			} else { // end of if( strText != null && strText.length() > 2 )
				if (bNeedDelete) {
					cDelCtrl[nRow] = 'D';
				}
			}
		}

		// Then, we analysis cDelCtrl, delete lines
		//
		// TIP : When deleting lines, we begin with the last line and end with
		// the first line. It is so boring that deleting lines by begining with
		// the first line.
		nEndRow = -1;
		while (nRow-- > 0) {
			// We want the end row of the delete range. Attention, we begin with
			// the last line.
			if (nEndRow == -1) {
				if (cDelCtrl[nRow] == 'D') {
					nEndRow = nRow;
				}
			} else {
				// If we get nEndRow already, we want nBeginRow.
				if (cDelCtrl[nRow] == 'R') {
					deleteRow(bmTemplate, nRow + 1, nEndRow);
					nEndRow = -1;
				}
			}
		}

		// boundary check.
		if (nEndRow != -1) {
			deleteRow(bmTemplate, 0, nEndRow);
		}
		bmTemplate.setSelection(0, 0, 0, 0);
	}

	/**
	 * 删除vts文件中的一行
	 * 
	 * @param bm
	 *            要操作的vts文件
	 * @param nBeginRow
	 *            要删除的起始行
	 * @param nEndRow
	 *            要删除的终止行
	 * @throws Exception
	 */
	protected static void deleteRow(com.f1j.ss.BookModelImpl bm, int nBeginRow,
			int nEndRow) throws Exception {
		bm.deleteRange(nBeginRow, bm.getMaxCol(), nEndRow, bm.getMaxCol(),
				Constants.eShiftRows);
	}

	/**
	 * 解析字符串。 由一个既包含普通字符串又包含控制字符串的字符串和数据xml文件生成最后的输出字符串。
	 * 
	 * @param str
	 *            需要解析的字符串
	 * @param fp
	 *            数据xml文件的句柄
	 * @return 例如：输入“测试数据$$$=/f1$测试数据”，得到“测试数据$2002-11-11测试数据”
	 */
	protected static String parseString(String str, F1Print fp) {
		String strNew = "";
		String strTemp = "";
		int nFlag = 0;

		for (int nIndex = 0; nIndex < str.length(); nIndex++) {
			if (FLAG_FLAG == str.charAt(nIndex)) {
				switch (nFlag) {
				case 0: // ordinary string
					nFlag = 1;
					strNew += strTemp;
					strTemp = "";
					break;
				case 1: // special string
					nFlag = 0;
					if (strTemp.equals("")) {
						strNew += FLAG_FLAG;
					} else {
						if (strTemp.length() > 1 && '=' == strTemp.charAt(0)) {
							strTemp = strTemp.substring(1);
							strTemp = fp.getNodeValue("/DATASET" + strTemp);
						}
						strNew += strTemp;
					}
					strTemp = "";
					break;
				}
			} else {
				strTemp += str.charAt(nIndex);
			}
		} // end of for

		if (1 == nFlag) { // special string
			if (strTemp.length() > 1 && '=' == strTemp.charAt(0)) {
				strTemp = strTemp.substring(1);
				strTemp = fp.getNodeValue("/DATASET" + strTemp);
			}
		}

		return strNew + strTemp;
	}

	/**
	 * Kevin 2003-04-01 根据指定的格式设置输出的VTS文件中指定单元格的值。
	 * 在原来的程序中，对于所有的赋值操作都只是调用BookModelImpl.setText()来实现。这样的话，
	 * 如果你在VTS模板文件中指定了数值型的格式，将不会在输出的VTS文件中体现出来。因为，对于
	 * 数值型的数据应该调用BookModelImpl.setNumber()来实现。
	 * 
	 * @param bm
	 *            BookModelImpl
	 * @param cf
	 *            CellFormat
	 * @param nRow
	 *            int
	 * @param nCol
	 *            int
	 * @param strValue
	 *            String
	 * @throws Exception
	 */
	protected static void setValue(BookModelImpl bm, CellFormat cf, int nRow,
			int nCol, String strValue) throws Exception {
		if (strValue == null || strValue.equals("")) {
			return;
		}

		// logger.debug(nRow+":"+nCol);
		if (cf.getValueFormatType() == Format.eValueFormatTypeNumber) {
			bm.setNumber(nRow, nCol, Double.parseDouble(strValue));
		} else {
			bm.setText(nRow, nCol, strValue);
		}
	}

	// 得到管理机构对应的VTS模板文件名，by niuzj 2005-7-18
	public static String QueryComVTS(String strDefVtsName, String strComCode) {
		String Sql1 = "";
		String Sql2 = "";
		String strIter = "";
		if (strComCode.equals("86")) { // if(strComCode=="86"),如果管理机构代码是86
			return (strDefVtsName); // 返回缺省的VTS模板文件名
		} else { // 如果管理机构代码不是86
			ExeSQL es = new ExeSQL();
			SSRS ssrs1 = new SSRS();
			SSRS ssrs2 = new SSRS();
			// 则根据缺省的VTS模板文件名和管理机构代码，去数据库查询该管理机构对应的VTS模板文件名
			Sql1 = "select VtsFileName from ManageComVTS where"
					+ " DefVtsName='?strDefVtsName?' and ComCode='?strComCode?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(Sql1);
			sqlbv.put("strDefVtsName", strDefVtsName);
			sqlbv.put("strComCode", strComCode);
			ssrs1 = es.execSQL(sqlbv);

			if ((ssrs1.getMaxRow() == 1) && (!ssrs1.GetText(1, 1).equals(""))) { // 如果查询有结果
				return (ssrs1.GetText(1, 1)); // 返回该管理机构对应的VTS模板文件名
			} else { // 如果查询没有结果
				// 则去查询该管理机构对应的上级管理机构
				Sql2 = "select UPCOMCODE from ldcom" + " where COMCODE='?strComCode?'";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(Sql2);
				sqlbv1.put("strComCode", strComCode);
				ssrs2 = es.execSQL(sqlbv1);
				if (ssrs2.getMaxRow() == 1 && (!ssrs2.GetText(1, 1).equals(""))) {
					strComCode = ssrs2.GetText(1, 1); // 以上级管理机构代码为新的管理机构代码，重新去查找
					strIter = QueryComVTS(strDefVtsName, strComCode); // 函数迭代
					return (strIter);
				} else {
					return (strDefVtsName); // 什么都查询不出来时，还是使用缺省VTS文件名
				}
			}
		}
	}

	public void destroy() {
		try {
			if (this.bmTemplate != null) {
				this.bmTemplate.destroy();
				this.bmTemplate = null;
			}
		} catch (Exception ex) {
			m_log.error("destroy", ex);
		}

		try {
			if (this.m_bmOutput != null) {
				this.m_bmOutput.destroy();
				this.m_bmOutput = null;
			}
		} catch (Exception ex) {
			m_log.error("destroy", ex);
		}

		this.m_fp = null;
	}

	public int findColIndex(BookModelImpl bmTemplate, int nRow,
			String strColInfo) {
		int nIndex = -1;
		int nCol = 0;

		String strText = null;

		try {
			nCol = 0;
			strText = bmTemplate.getText(nRow, nCol);
			while (!strText.equals(FLAG_MODEL_END)) {
				if (strText.length() > 2) {
					strText = strText.substring(2);
					if (strText.equals(strColInfo)) {
						nIndex = nCol;
						break;
					}
				}
				nCol++;
				strText = bmTemplate.getText(nRow, nCol);
			}
			return nIndex;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return nIndex;
	}

	/**
	 * 
	 * @param bmOutput
	 *            BookModelImpl
	 * @param nRow
	 *            int
	 * @param dGroupSumCol
	 *            double[]
	 * @param dGroupSumValue
	 *            double[]
	 * @param bAppend
	 *            boolean
	 */
	protected void getGroupSum(BookModelImpl bmOutput, int nRow,
			int[] nSumColIndex, double[] dSumColValue, boolean bAppend) {
		int nIndex = 0;
		String strValue = null;

		try {
			for (nIndex = 0; nIndex < nSumColIndex.length; nIndex++) {
				strValue = bmOutput.getText(nRow, nSumColIndex[nIndex]);
				if (bAppend) {
					dSumColValue[nIndex] += Double.parseDouble(strValue);
				} else {
					dSumColValue[nIndex] = Double.parseDouble(strValue);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 带格式插入一行
	 * 
	 * @param bmTemplate
	 *            BookModelImpl
	 * @param nTemplateRow
	 *            int
	 * @param bmOutput
	 *            BookModelImpl
	 * @param nOutputRow
	 *            int
	 * @param nMaxCol
	 *            int
	 */
	protected void insertRowWithFormat(BookModelImpl bmTemplate,
			int nTemplateRow, BookModelImpl bmOutput, int nOutputRow,
			int nMaxCol) {
		int nColIndex;
		com.f1j.ss.CellFormat cellFormat = new CellFormat();

		try {
			bmOutput.insertRange(nOutputRow, 0, nOutputRow, 0,
					Constants.eShiftRows);

			for (nColIndex = 0; nColIndex < nMaxCol; nColIndex++) {
				bmTemplate.setSelection(nTemplateRow, nColIndex, nTemplateRow,
						nColIndex);
				bmTemplate.getCellFormat(cellFormat);
				cellFormat.useAllFormats();

				bmOutput.setSelection(nOutputRow, nColIndex, nOutputRow,
						nColIndex);
				bmOutput.setCellFormat(cellFormat);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 采用更透明的方法,更好控制F1对象,提高处理操作.
	 * 
	 * @author: Yang Yalin 2005/11/15
	 * @return BookModelImpl
	 */
	public BookModelImpl getBookModel() {
		m_bmOutput = new BookModelImpl();
		try {
			if (!parse()) {
				logger.debug("解析SQL错误");
				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return m_bmOutput;
	}

	/**
	 * 重新读取模板.
	 * 
	 * @param templateFile
	 *            String
	 */
	public void resetTemplate() {
		bmTemplate = null;
	}

	/**
	 * 设置输入数据流.
	 * 
	 * @param in
	 *            InputStream
	 */
	public void setInputStream(InputStream in) {
		m_fp = new F1Print(in);
	}

	// 测试函数QueryComVTS
	public static void main(String[] args) throws Exception {
		String strVTS = "";
		// 这是一个静态方法，不用初始化，新建类的对象
		// strVTS=F1PrintParser.QueryComVTS("a.vts","86110002");
		// strVTS=F1PrintParser.QueryComVTS("a.vts","86110001");
		strVTS = F1PrintParser.QueryComVTS("a.vts", "86110005");
		// strVTS=F1PrintParser.QueryComVTS("a.vts","86");
		logger.debug(strVTS);
	}
}
