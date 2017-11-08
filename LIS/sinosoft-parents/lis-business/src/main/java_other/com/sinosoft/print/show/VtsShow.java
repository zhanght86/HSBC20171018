package com.sinosoft.print.show;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.ss.CellFormat;
import com.f1j.ss.Constants;
import com.f1j.ss.GRObject;
import com.f1j.ss.GRObjectPos;
import com.f1j.ss.ReadParams;
import com.f1j.util.Format;
import com.sinosoft.print.func.PrintFunc;
import com.sinosoft.print.func.XmlFunc;
import com.sinosoft.report.f1report.BarCode;
import com.sinosoft.report.f1report.DBarCode;
import com.sinosoft.report.f1report.JRptUtility;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: VtsShow
 * </p>
 * <p>
 * Description: 解析模板vts文件，并用数据文件中的内容替换它，从而生成最后要打印的 vts文件
 * </p>
 * 
 * @author ZhuXF
 * @version 1.0
 * @modify 2011-1-20
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class VtsShow {
	private static Logger logger = Logger.getLogger(VtsShow.class);

	/**
	 * 字符标识位
	 */
	protected static final char FLAG_FLAG = '$';

	/**
	 * 循环变量数据，不唯一变量
	 */
	protected static final String FLAG_MULTI_RECORD = "$*";

	/**
	 * 默认的变量模式，唯一变量
	 */
	protected static final String FLAG_ONE_RECORD = "$=";

	/**
	 * 控制显示 - 开始
	 */
	protected static final String FLAG_DISPLAY_START = "$<";

	/**
	 * 控制显示 - 结束
	 */
	protected static final String FLAG_DISPLAY_END = "$>";

	/**
	 * 行列结束符号
	 */
	protected static final String FLAG_MODEL_END = "$/";

	/**
	 * 添加空白行
	 */
	private final String FLAG_BLANK_LINE = "$K";

	/**
	 * 添加分页符号
	 */
	protected static final String FLAG_PAGE_BREAK = "$B";

	/**
	 * 合并文件显示
	 */
	protected static final String FLAG_VTS_FILE = "$F";

	/**
	 * 表头变量
	 */
	private final String FLAG_TABLE_TITLE = "$t";

	/**
	 * 行变量解析
	 */
	protected static final String FLAG_FULL_ROW = "$R";

	/**
	 * 覆盖式插入数据
	 */
	protected static final String FLAG_DATA_LIST = "$L";

	private XmlFunc mXmlFunc;

	/**
	 * 应用的物理目录
	 */
	protected String mAppPath = "";

	protected TransferData mTransferData;

	/**
	 * 输出对象，记录输出信息
	 */
	protected BookModelImpl mBookModelImpl;

	/**
	 * 解析对象，读取模板信息
	 */
	protected BookModelImpl mTempBookModelImpl;

	private Logger mLogger = Logger.getLogger(VtsShow.class);

	/**
	 * 构造函数
	 * 
	 * @param cXmlFunc
	 * @param cAppPath
	 * @param cOutputType
	 * @param cLanguage
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public VtsShow(XmlFunc cXmlFunc, String cAppPath, TransferData cTransferData)
			throws FileNotFoundException, IOException {
		mXmlFunc = cXmlFunc;
		mAppPath = cAppPath;
		mTransferData = cTransferData;
	}

	/**
	 * 解析并将结果输出到输出流中
	 * 
	 * @param cOutputStream
	 *            OutputStream
	 * @return boolean
	 */
	public boolean output(OutputStream cOutputStream) {
		try {
			return this.output(cOutputStream, Constants.eFileCurrentFormat);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 解析并将结果输出到输出流中
	 * 
	 * @param cOutputStream
	 * @param cFileFormat
	 * @return
	 */
	public boolean output(OutputStream cOutputStream, short cFileFormat) {
		// 同步F1PrintParser，独占解析，防止由于并发引起问题
		// kevin 2006-10-12
		synchronized (VtsShow.class) {
			try {
				if (mXmlFunc.getTemplate().equals("new.vts")) {
					return true;
				}
				if (parse()) {
					mBookModelImpl.write(cOutputStream,
							new com.f1j.ss.WriteParams(cFileFormat));
					this.destroy();
					return true;
				}
				return false;
			} catch (Exception ex) {
				ex.printStackTrace();
				mLogger.error("output", ex);
				return false;
			}
		}
	}

	/**
	 * 解析操作
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	protected boolean parse() throws Exception {
		// 获取模板文件物理路径
		String tOutputType = (String) mTransferData
				.getValueByName("OutputType");
		String tLanguage = (String) mTransferData.getValueByName("Language");
		String tPicType = (String) mTransferData.getValueByName("PicType");
		String tTemplateFile = PrintFunc.getTempleteFile(mXmlFunc
				.getPrintName(), tOutputType, tLanguage);
		if (tTemplateFile.equals("")) {
			return false;
		}

		tTemplateFile = mAppPath + tTemplateFile;
		mLogger.info("VTS Template = " + tTemplateFile);

		CellFormat tCellFormat = null;

		if (mTempBookModelImpl == null) {
			mTempBookModelImpl = new BookModelImpl();
			mTempBookModelImpl.read(tTemplateFile, new ReadParams());
		}
		// 在这里进行显示控制的处理
		controlDisplay(mTempBookModelImpl, mXmlFunc);// 由于bookmodel的读取方式，必须先把不显示的部分去掉

		// 2006-07-24 Kevin
		// Error, which makes F1PrintParser crashed, occures in function
		// copyAll() because of unknown reason,
		// I have to call controlDisplay() again.
		mBookModelImpl = new BookModelImpl();// 
		mBookModelImpl.read(tTemplateFile, new ReadParams());
		controlDisplay(mBookModelImpl, mXmlFunc);
		int tTempMaxRow = mTempBookModelImpl.getMaxRow();// 模板文件的最大行数
		int tTempMaxCol = mTempBookModelImpl.getMaxCol();// 模板文件的最大列数

		// we have get all format we needed, now get a blank vts file for output
		mBookModelImpl.clearRange(0, 0, tTempMaxRow, tTempMaxCol,
				Constants.eClearValues);// 获取了一样大小（页面大小）的输出对象后，将页面内容清空
		tCellFormat = mTempBookModelImpl.getCellFormat(0, 0, 0, 0);// 获取单元格的格式

		int nNewRow = 0, // 输出对象的正在写入行
		nNewCol = 0, // 输出对象的正在写入列
		nListLengthCol = 0, // 输出对象的额外写入列（在一次读取多列时会用到）
		nRowHeight = 0, // 输出对象的行高（在一次读取多列时会用到）
		nNewMaxRow = 0, // 输出对象目前的最大打印行数
		nNewMaxCol = 0, // 输出对象目前的最大打印列数
		nChildPos = 0, // 数据所在节点的索引
		nCurrentRow = 0, //
		nRow, // 读取模板的目前行数
		nCol, // 模板的目前列数
		nMaxListLength = 0;// 在一行扫描解析过程中，最大的“动态增加的行”的数量（解析“$*”标签而出现“动态增加的行”）。
		String strText = "", // 单元格的文本
		strSubText, // 单元格文本的头两个字符
		strXPath, // 数据在xml中的路径
		strNodeName;// 数据所在节点名称

		for (nRow = 0; nRow < tTempMaxRow - 1; nRow++) {// 循环行
			nNewRow += nMaxListLength;// 输出对象目前行数为之前行数加上之前输出的额外行数
			nMaxListLength = 0;// 额外行数归零
			nRowHeight = mTempBookModelImpl.getRowHeight(nRow);
			if (nNewMaxRow < nNewRow + 1)
				nNewMaxRow = nNewRow + 1;// 记录输出对象的最大行数
			// 以下开始此行的列循环
			for (nCol = 0; nCol < tTempMaxCol - 1; nCol = mTempBookModelImpl
					.getSelEndCol() + 1) {// 此处使用getSelEndCol来获得下一个列号
				nNewCol = nCol;// 输出对象目前列数=模板目前读取列数
				nNewCol += nListLengthCol;// 加上额外的输出列数
				nListLengthCol = 0;// 归零
				strText = mTempBookModelImpl.getText(nRow, nCol);// 获取文本
				mTempBookModelImpl.setSelection(nRow, nCol, nRow, nCol);// 选中此单元格
				mTempBookModelImpl.getCellFormat(tCellFormat);// 获取格式
				tCellFormat.useAllFormats();// 应用格式
				// 2006-07-24 Kevin
				// 一次跳过合并的所有单元格
				// 将setActiveCell和getSelEndCol函数结合使用
				mTempBookModelImpl.setActiveCell(nRow, nCol);
				// 2006-07-19 Kevin
				// 处理长度为0的字符串的情况，提高解析的效率。
				if (strText.length() < 1) { // == 0
					continue;// 空白列不处理
				} else if (strText.length() == 1) { // == 1
					strSubText = strText;
				} else { // > 1
					strSubText = strText.substring(0, 2);
				}

				// 以下开始根据文本的标志来处理文件
				if (strSubText.equals(FLAG_MODEL_END)
						|| FLAG_BLANK_LINE.equals(strSubText)) {// 此行结束标志，或者空白行标志，跳出列循环
					if (nNewMaxCol < nNewCol + 1)
						nNewMaxCol = nNewCol + 1;
					break;
				} else if (strSubText.equals(FLAG_MULTI_RECORD)) {// 多数据标志，处理方式为，读取本列的信息，将本列所有数据写入输出对象
					strText = strText.substring(2);
					nChildPos = strText.lastIndexOf("/");
					strXPath = "/DATASET" + strText.substring(0, nChildPos);
					strNodeName = strText.substring(nChildPos + 1);
					mXmlFunc.query(strXPath);// 获取路径后查询
					int nColIndex = mXmlFunc.getColIndex(strNodeName);// 获取节点索引
					nCurrentRow = nNewRow;// 数据写入的当前行数（行号）
					int nListLen = 0;// 监听写了多少行
					while (mXmlFunc.next()) {
						if (nListLen > nMaxListLength)
							mBookModelImpl.insertRange(nCurrentRow, nNewCol,
									nCurrentRow, mTempBookModelImpl
											.getSelEndCol(),
									Constants.eShiftRows);// 如果需要增加新单元格，则在输出对象中增加
						if (strText.length() > 0)
							setValue(mBookModelImpl, tCellFormat, nCurrentRow,
									nNewCol, mXmlFunc.getString(nColIndex));// 赋值
						nListLen++;
						nCurrentRow++;
					}
					// 2006-07-24 Kevin
					// 一次设置多行的格式
					if (nListLen > 0)
						mBookModelImpl.copyRange(nNewRow, nNewCol, nNewRow
								+ nListLen - 1, mTempBookModelImpl
								.getSelEndCol(), mTempBookModelImpl, nRow,
								nCol, nRow, mTempBookModelImpl.getSelEndCol(),
								Constants.eCopyFormats);
					if (nListLen > nMaxListLength)
						nMaxListLength = nListLen;// 获取新增额外行数
				} else if (strSubText.equals(FLAG_PAGE_BREAK)) {
					mBookModelImpl.addRowPageBreak(nNewRow);// 在当前行增加分页符
				} else if (strSubText.equals(FLAG_VTS_FILE)) {

				} else if (strSubText.equals(FLAG_FULL_ROW)) {
					// 一次解析一行的数据，此方法待考虑，实现的比较少，此方法思想为:根据行名获得整行的索引，逐行获得数据，写数据
					strXPath = "/DATASET" + strText.substring(2);
					mXmlFunc.query(strXPath);
					int nColCount = mXmlFunc.getColCount();
					int nColIndex = 0;
					nCurrentRow = nNewRow;
					int nListLen = 0;
					while (mXmlFunc.next()) {
						if (nListLen > nMaxListLength)
							mBookModelImpl.insertRange(nCurrentRow, nNewCol,
									nCurrentRow, nNewCol + nColCount,
									Constants.eShiftRows);
						for (nColIndex = 0; nColIndex < nColCount; nColIndex++) {
							mTempBookModelImpl.setSelection(nRow, nCol
									+ nColIndex, nRow, nCol + nColIndex);
							tCellFormat = mTempBookModelImpl.getCellFormat();
							tCellFormat.useAllFormats();
							setValue(mBookModelImpl, tCellFormat, nCurrentRow,
									nNewCol + nColIndex, mXmlFunc
											.getString(nColIndex));
							mBookModelImpl.setSelection(nCurrentRow, nNewCol
									+ nColIndex, nCurrentRow, nNewCol
									+ nColIndex);
							mBookModelImpl.setCellFormat(tCellFormat);
						}
						nListLen++;
						nCurrentRow++;
						mBookModelImpl.setRowHeight(nCurrentRow, nRowHeight);
					}
					nListLengthCol = nColCount;
					if (nListLen > nMaxListLength)
						nMaxListLength = nListLen;
				} else if (strSubText.equals(FLAG_DATA_LIST)) {
					// 覆盖行而不插入行，可以大幅度的提高操作效率，与multiRecord的差别在于不再一个个插入单元格
					strText = strText.substring(2);
					nChildPos = strText.lastIndexOf("/");
					strXPath = "/DATASET" + strText.substring(0, nChildPos);
					strNodeName = strText.substring(nChildPos + 1);
					mXmlFunc.query(strXPath);
					int nColIndex = mXmlFunc.getColIndex(strNodeName);
					nCurrentRow = nNewRow;
					int nListLen = 0;
					while (mXmlFunc.next()) {
						if (strText.length() > 0)
							setValue(mBookModelImpl, tCellFormat, nCurrentRow,
									nNewCol, mXmlFunc.getString(nColIndex));
						nListLen++;
						nCurrentRow++;
					}
					// 2006-07-24 Kevin
					// 一次设置多行的格式
					if (nListLen > 0)
						mBookModelImpl.copyRange(nNewRow, nNewCol, nNewRow
								+ nListLen - 1, mTempBookModelImpl
								.getSelEndCol(), mTempBookModelImpl, nRow,
								nCol, nRow, mTempBookModelImpl.getSelEndCol(),
								Constants.eCopyFormats);
					if (nListLen > nMaxListLength)
						nMaxListLength = nListLen;
				} else if (FLAG_TABLE_TITLE.equals(strSubText)) {// 如果是表头变量，则去掉该标志，为了与pdfjxl兼容
					strText = strText.substring(2);
					setValue(mBookModelImpl, tCellFormat, nNewRow, nNewCol,
							parseString(strText, mXmlFunc));
					mBookModelImpl.setSelection(nNewRow, nNewCol, nNewRow,
							nNewCol);
					mBookModelImpl.setCellFormat(tCellFormat);
				} else {
					setValue(mBookModelImpl, tCellFormat, nNewRow, nNewCol,
							parseString(strText, mXmlFunc));
					mBookModelImpl.setSelection(nNewRow, nNewCol, nNewRow,
							nNewCol);
					mBookModelImpl.setCellFormat(tCellFormat);
				}
			}// end of for(nCol = 0; nCol < nMaxCol - 1; nCol ++)列循环结束
			// modify nMaxListLength
			if (nMaxListLength < 1) {
				nMaxListLength = 1;// 新增行数，最少也是一行
			}
			if (0 == nCol && FLAG_MODEL_END.equals(strText)) // 如果第一列为结束符，则表示模板读取结束，跳出行循环
				break;
		}

		// 判断图片的输出模式，如果为true，则需要在模板中生成图片
		if (tPicType.equalsIgnoreCase("true")) {
			// 设置循环处理的BarCodeInfo逻辑
			strXPath = "/DATASET/BarCodeInfo/BarCode";
			mXmlFunc.query(strXPath);
			int tCodeIndex = mXmlFunc.getColIndex("Code");// 条码code部分
			int tContentIndex = mXmlFunc.getColIndex("Content");// 二维码加密数据部分
			int tParamIndex = mXmlFunc.getColIndex("Param");// 一维码参数
			int tNameIndex = mXmlFunc.getColIndex("Name");// 对象名称，实际上不使用xml中的名称，而读取模板中的名称
			int tBarTypeIndex = mXmlFunc.getColIndex("Type");// 条码类型，同上，由模板中决定
			nCurrentRow = nNewRow;
			// 条形码生成逻辑判断
			String tCode = "";
			String tContent = "";
			String tParam = "";
			String tName = "";
			String tBarType = "";
			GRObject tGRObject = null;
			int tBarCodeIndex = 1;
			int tGRObjectIndex = 1;
			while (mXmlFunc.next()) {
				tName = mXmlFunc.getString(tNameIndex);

				// 根据对象名称查找对象
				do {
					tGRObject = mTempBookModelImpl.getObject(tGRObjectIndex);
					tGRObjectIndex++;
					if (tGRObject == null) {
						tName = "";
						continue;
					}
					tName = tGRObject.getName();
				} while (tName.indexOf("BarCode") == -1
						&& tGRObjectIndex < nNewMaxRow);

				if (tGRObject == null) {
					mLogger.error("模板中没有条形码对象");
					break;
				}

				String t_Str = "";
				tCode = mXmlFunc.getString(tCodeIndex);
				tContent = mXmlFunc.getString(tContentIndex);
				tParam = mXmlFunc.getString(tParamIndex);
				tBarType = mXmlFunc.getString(tBarTypeIndex);
				// tongmeng 2011-04-25 add
				// 根据条码名称再判断类型，1:一维码，2:二维码
				// 注:如果以后不需要在程序中设置条码类型.再使用此部分程序

				if ((tBarType == null || tBarType.equals(""))
						&& tName.indexOf(",") != -1) {
					String[] tTempCode = tName.split(",");
					if (tTempCode.length == 2) {
						if (tTempCode[1].equals("1")) {
							tBarType = "BarCode";
						} else if (tTempCode[1].equals("2")) {
							tBarType = "DBarCode";
						}
					}
				}

				// 使用什么条形码类型
				if (tBarType.equals("DBarCode")) {
					DBarCode tDBarCode = new DBarCode(tCode + tContent);// 图片数据信息
					GRObjectPos pos = tGRObject.getPos();// 图片位置信息
					mBookModelImpl.addPicture(pos.getX1(), pos.getY1(), pos
							.getX2(), pos.getY2(), tDBarCode.getBytes());
				} else {
					BarCode bcode = new BarCode(tCode);
					// 获得条形码参数
					if (tParam != null && !tParam.equalsIgnoreCase("")) {
						String params[] = tParam.split("&");
						for (int j = 0; j < params.length; j++) {
							// 获得条形码宽度
							if (params[j].toLowerCase().startsWith("barheight")) {
								t_Str = params[j].split("=")[1];
								if (JRptUtility.IsNumeric(t_Str))
									bcode.setBarHeight(Integer.parseInt(t_Str));
							}
							// 获得条形码宽度
							if (params[j].toLowerCase().startsWith("barwidth")) {
								t_Str = params[j].split("=")[1];
								if (JRptUtility.IsNumeric(t_Str))
									bcode.setBarWidth(Integer.parseInt(t_Str));
							}
							// 获得条形码粗细线条比例
							if (params[j].toLowerCase().startsWith("barratio")) {
								t_Str = params[j].split("=")[1];
								if (JRptUtility.IsNumeric(t_Str))
									bcode.setBarRatio(Integer.parseInt(t_Str));
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
								if (JRptUtility.IsNumeric(t_Str))
									bcode.setXMargin(Integer.parseInt(t_Str));
							}
							// 获得条形码图片竖向空白区长度
							if (params[j].toLowerCase().startsWith("ymargin")) {
								t_Str = params[j].split("=")[1];
								if (JRptUtility.IsNumeric(t_Str))
									bcode.setYMargin(Integer.parseInt(t_Str));
							}
						}
					}
					bcode.setFormatType(BarCode.FORMAT_GIF);
					GRObjectPos pos = tGRObject.getPos();
					mBookModelImpl.addPicture(pos.getX1(), pos.getY1(), pos
							.getX2(), pos.getY2(), bcode.getBytes());
				}
				tBarCodeIndex++;
			}
		}

		mBookModelImpl.objectBringToFront();
		// ---------------------------end
		mBookModelImpl.setSelection(0, 0, nNewMaxRow - 1, nNewMaxCol - 1);
		mBookModelImpl.setPrintArea();
		mBookModelImpl.setAllowSelections(false); // 打印显示界面不能选中
		mBookModelImpl.setAllowObjectSelections(false); // 打印显示界面不能选中
		mBookModelImpl.setShowGridLines(false); // 去掉显示中的网格
		mBookModelImpl.setShowColHeading(false); // 去掉显示的列头
		mBookModelImpl.setShowRowHeading(false); // 去掉显示的行头
		mBookModelImpl.setShowEditBar(false); // 去掉显示的bar
		mBookModelImpl.setShowEditBarCellRef(false); // 去掉显示的bar
		mBookModelImpl.setShowTabs(Constants.eTabsOff); // 去掉显示的sheet

		// 不显示选中的区域，缺省会用黑色的边框将选中的区域标注出来。
		mBookModelImpl.setShowSelections(Constants.eShowOff);

		mBookModelImpl.setPrintGridLines(false); // 在打印时去掉网格
		mBookModelImpl.setPrintHeader(""); // 在打印时去掉头标题
		mBookModelImpl.setPrintFooter(""); // 在打印时去掉页码

		mBookModelImpl.saveViewInfo();

		// Yangyl Added at 2005/9/12 20:10
		mLogger.info("00 00 00 模板为:[" + mXmlFunc.getTemplate() + "];数据行数为:["
				+ nNewRow + "]");
		tCellFormat = null;
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
	protected static void controlDisplay(BookModelImpl bmTemplate,
			XmlFunc cXmlFunc) throws Exception {
		int nRow = 0;
		int nEndRow = 0; // end row of delete range
		boolean bNeedDelete = false;
		char[] cDelCtrl = null;

		cDelCtrl = new char[bmTemplate.getLastRow() + 1];

		// In the first scan, we keep track whether a line should be deleted in
		// cDelCtrl. 'D' for delete; 'R' for reserve.
		nRow = 0;

		for (nRow = 0; nRow < bmTemplate.getMaxRow(); nRow++) {
			// First, we assume that this line will be reserved
			cDelCtrl[nRow] = 'R';

			String strText = bmTemplate.getText(nRow, 0);
			if (strText != null && strText.length() > 1) {
				String strSubText = strText.substring(0, 2);
				if (strSubText.equals(FLAG_DISPLAY_START)) {
					if (cXmlFunc.getDisplayControl(strText.substring(2))
							.equals("")) {
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
	protected static String parseString(String str, XmlFunc cXmlFunc) {
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
							strTemp = cXmlFunc.getNodeValue("/DATASET"
									+ strTemp);
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
				strTemp = cXmlFunc.getNodeValue("/DATASET" + strTemp);
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
			if (strValue.matches("\\d+")) {
				bm.setNumber(nRow, nCol, Double.parseDouble(strValue));
			} else {
				bm.setText(nRow, nCol, strValue);
			}
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
			if (this.mTempBookModelImpl != null) {
				this.mTempBookModelImpl.destroy();
				this.mTempBookModelImpl = null;
			}
		} catch (Exception ex) {
			mLogger.error("destroy", ex);
		}

		try {
			if (this.mBookModelImpl != null) {
				this.mBookModelImpl.destroy();
				this.mBookModelImpl = null;
			}
		} catch (Exception ex) {
			mLogger.error("destroy", ex);
		}

		this.mXmlFunc = null;
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
		mBookModelImpl = new BookModelImpl();
		try {
			if (!parse()) {
				logger.debug("解析SQL错误");
				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mBookModelImpl;
	}

	/**
	 * 重新读取模板.
	 * 
	 * @param templateFile
	 *            String
	 */
	public void resetTemplate() {
		mTempBookModelImpl = null;
	}

	// 测试函数QueryComVTS
	public static void main(String[] args) throws Exception {
		String var3 = "BarHeight=20&amp;BarRation=3&amp;BarWidth=1&amp;BgColor=FFFFFF&amp;ForeColor=000000&amp;XMargin=10&amp;YMargin=10#BarHeight=20&amp;BarRation=3&amp;BarWidth=1&amp;BgColor=FFFFFF&amp;ForeColor=000000&amp;XMargin=10&amp;YMargin=10!0,18.43,12.09,621.74,86.75#0,18.43,12.09,621.74,86.75!100011111028!";
		String[] tArrBarCode = var3.split("!");
		logger.debug(tArrBarCode.length);
		String[] tArrParam = tArrBarCode[0].split("#");
		logger.debug(tArrParam.length);
		String[] tArrAlocations = tArrBarCode[1].split("#");
		logger.debug(tArrAlocations.length);
		String[] tArrCode = tArrBarCode[2].split("#");
		logger.debug(tArrCode.length);
	}
}
