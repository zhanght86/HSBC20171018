package com.sinosoft.print.show.pdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.CellFormat;
import jxl.format.PageOrientation;

import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.ss.ReadParams;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.print.func.PrintFunc;
import com.sinosoft.print.func.XmlFunc;
import com.sinosoft.report.f1report.BarCode;
import com.sinosoft.report.f1report.DBarCode;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: PdfJxlPrint
 * </p>
 * <p>
 * Description: 根据xls文件定制的样式动态输出pdf文件，目前对段落的支持不是很友好。
 * </p>
 * 
 * @author ZhuXF
 * @version 2.0
 * @modify 2011-8-1
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class PdfJxlPrint implements BusinessService {
	private static Logger logger = Logger.getLogger(PdfJxlPrint.class);

	/**
	 * 字符标识位
	 */
	private final char FLAG_FLAG = '$';

	/**
	 * 循环变量数据，不唯一变量
	 */
	private final String FLAG_MULTI_RECORD = "$*";

	/**
	 * 类似$*逻辑
	 */
	private final String FLAG_DATA_LIST = "$L";

	/**
	 * 表头变量
	 */
	private final String FLAG_TABLE_TITLE = "$t";

	/**
	 * 条形码变量标志
	 */
	private final String FLAG_IMAGE_BAR = "$T";

	/**
	 * 显示控制区域开始
	 */
	private final String FLAG_DISPLAY_START = "$<";

	/**
	 * 显示控制区域结束
	 */
	private final String FLAG_DISPLAY_END = "$>";

	/**
	 * 行列结束符号
	 */
	private static final String FLAG_MODEL_END = "$/";

	/**
	 * 添加分页符号
	 */
	private final String FLAG_PAGE_BREAK = "$B";

	/**
	 * 添加空白行
	 */
	private final String FLAG_BLANK_LINE = "$K";

	/**
	 * 固定图片文件
	 */
	private final String FLAG_IMAGE = "$I";

	/**
	 * 默认值，就不设置变量了 private final String FLAG_ONE_RECORD = "$=";<br>
	 * 下面的逻辑，暂时未采用<br>
	 * private final String FLAG_FULL_ROW = "$R";<br>
	 * private final String FLAG_GROUP_SUM = "$G";<br>
	 * private final String FLAG_ACRMULTI_RECORD = "$+";
	 */

	/**
	 * jxl可以读取的单元格描述信息量总数，方便扩展定义
	 */
	private final int DESC_SIZE = 14;

	/**
	 * 应用的物理目录
	 */
	private String mAppPath;

	/**
	 * 待定变量
	 */
	private TransferData mTransferData;

	/**
	 * 用户信息
	 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/**
	 * 错误信息
	 */
	public CErrors mErrors = new CErrors();

	/**
	 * 日志输出类
	 */
	private Logger mLogger = Logger.getLogger(PdfJxlPrint.class);

	/**
	 * 缓存输入流的byte数组
	 */
	private byte[] mbyte;

	/**
	 * 返回对象
	 */
	private VData mResult = new VData();

	/**
	 * 一个工作表页面的宽度单位，统计得到的，为了匹配pdf页面的单位
	 */
	private float SHEET_WIDTH = 26000;

	/**
	 * 一个工作表页面的高度单位，统计得到的
	 */
	private float SHEET_HEIGHT = 13000;

	/**
	 * 1个pdf单位相对于sheet单位的个数，宽度的
	 */
	private float UNIT_RATE_WIDTH = 0;

	/**
	 * 1个pdf单位相对于sheet单位的个数，高度的
	 */
	private float UNIT_RATE_HEIGHT = 0;

	/**
	 * 图片的宽高和位置xy信息
	 * 
	 */
	private double[][] imageDataArr;

	private List<Image> imageArr;

	/**
	 * 生成pdf文件
	 * 
	 * @param cOutputFile
	 * @param cTempleteFile
	 * @param cXmlFunc
	 */
	@SuppressWarnings("unchecked")
	private boolean printPDF(GlobalInput cGlobalInput, String cAppPath,
			XmlFunc cXmlFunc) {

		Document tDocument = null;// 文档对象
		FileOutputStream tFileOutputStream = null;// 输出文件流对象

		// 获取pdf模板的信息
		String tOutputType = (String) mTransferData.getValueByName("Output");
		String tLanguage = (String) mTransferData.getValueByName("Language");
		String tTemplateFile = PrintFunc.getTempleteFile(cXmlFunc
				.getPrintName(), tOutputType, tLanguage);
		this.mLogger.info(tTemplateFile);

		// 输出文件名
		String tTempFileName = PrintFunc.getTempFileName(cGlobalInput) + ".pdf";

		try {
			Workbook tWorkbook = Workbook.getWorkbook(new File(cAppPath
					+ tTemplateFile));
			Sheet tSheet = tWorkbook.getSheet(0);
			int tMaxRow = tSheet.getRows(), // 模板的总行数
			tMaxCol = tSheet.getColumns(), // 表格的总列数，使用默认模式获得，或者根据$/判断结束
			tRow, // 读取的当前行
			tCol, // 读取的当前列
			tColSpan = 0, // 所占列数，例如：三个合并单元格，就表示3
			tRowSpan = 0;// 所占行数，例如：2个合并单元格，就表示2
			String tStrText = null;// 单元格内的文字信息

			String tSubText;// 单元格内的文字信息的展现类型，例如：$=、S*等
			int tIgnoreFlagNum = 0;// 是否输出标志，默认模板信息都输出,当大于零时表示要经过对应个数的%>才显示
			String[] tCellDescArr = null;// 描述信息数组，用来存放行数、列数、边框、文字位置、文字颜色
			boolean tTableHeaderFlag = false;// 是否存在表格表头的标志
			Cell tXlsCell = null;// 单元格对象
			int tLeftCol, // 单元格左边列坐标，合并单元格使用
			tRightCol, // 单元格右边列坐标，合并单元格使用
			tTopRow, // 单元格上边行坐标，合并单元格使用
			tBottomRow, // 单元格下边行坐标，合并单元格使用
			tTableTitleRow = 0;// 表头的行数
			PdfPTable tTable = null;// 表格对象

			PdfPageSize tPdfPageSize = new PdfPageSize();// 设置页面的类型
			Rectangle tRectangle = tPdfPageSize.getPageSize(tSheet
					.getSettings().getPaperSize());
			if (PageOrientation.LANDSCAPE.equals(tSheet.getSettings()
					.getOrientation())) {// 判断纸张的版式：横、纵
				tRectangle = tRectangle.rotate();// 加上这句可以实现页面的横置
			}
			tDocument = new Document(tRectangle);// 根据纸张类型创建doc对象
			tFileOutputStream = new FileOutputStream(cAppPath + "vtsfile/"
					+ tTempFileName);// 设置输出文件路径，文件名需要返回给前台调用
			PdfWriter tPdfWriter = PdfWriter.getInstance(tDocument,
					tFileOutputStream);
			tPdfWriter.setPageEvent(new PdfPageEvent());// 可以输出页码和页数，不过具体的坐标需要修改
			tDocument.addAuthor("SinoSoft");// 增加作者信息
			tDocument.open();

			tTable = newTable(tMaxCol);// 根据总列数创建表格
			Range[] tRangeArr = tSheet.getMergedCells();// 获取模板中全部合并单元格信息集，jxl单独提供的方法

			initUnitRate(tDocument);
			initImageData(tSheet, cXmlFunc, tTemplateFile);

			// 循环模板中的行记录信息
			for (tRow = 0; tRow < tMaxRow; tRow++) {
				// 循环模板中的列记录信息
				int tCurrentColNum = tSheet.getRow(tRow).length;// 当前行的列数
				addImage(tRow, tDocument, tSheet);
				for (tCol = 0; tCol < tMaxCol; tCol++) {
					tXlsCell = tSheet.getCell(tCol, tRow);
					tBottomRow = tTopRow = tRow;
					tRightCol = tLeftCol = tCol;

					int[] tIntArr = judgeMergedCell(tRangeArr, tCol, tRow);
					// 得到最右边单元格的列和行
					tBottomRow = tIntArr[0];
					tRightCol = tIntArr[1];
					if (tIntArr[2] == 1)
						continue;// 如果是行合并单元格，则跳过这个单元格处理

					tStrText = tXlsCell.getContents().trim();// 获取单元格中的文字信息，去除了空格
					tCellDescArr = getCellFormat(tSheet, tLeftCol, tRightCol,
							tTopRow, tBottomRow, tStrText); // 获取单元格的描述信息
					tRowSpan = Integer.valueOf(tCellDescArr[0]) + 1; // 所占行
					tColSpan = Integer.valueOf(tCellDescArr[1]) + 1; // 所占列
					// 判断单元格文字信息
					if (tStrText.equals("") || tStrText.length() == 0) {
						// 判断内容是否需要显示
						if (tIgnoreFlagNum > 0) {
							// 处理合并单元格跨列的模式
							if (tRightCol != tLeftCol)
								tCol += tRightCol - tLeftCol;
							continue;
						}
						if (!tTableHeaderFlag || tCol != 0)// 如果正在读取表格时，不在此处生成表前的缩进空格
							// 如果内容为空，则根据所占行列创建一个空的单元格
							tTable.addCell(new PdfCell("　", tRowSpan, tColSpan,
									tCellDescArr));
					} else {
						// 获取单元格的描述标志
						if (tStrText.length() <= 1) {
							tSubText = tStrText;
						} else {
							tSubText = tStrText.substring(0, 2);
						}

						// 判定显示输出状态
						if (FLAG_DISPLAY_START.equals(tSubText)) {
							// 显示开始处理
							if (cXmlFunc.getDisplayControl(
									tStrText.substring(2)).equals("")) {
								tIgnoreFlagNum++;// 如果找不到要显示的设置，则表示该信息无需处理，设置显示内容标志+1
							}
							break;
						} else if (FLAG_DISPLAY_END.equals(tSubText)) {
							// 显示结束处理
							if (tIgnoreFlagNum > 0) {
								tIgnoreFlagNum--;// 重新设置要显示内容标志，当标志为0时则显示
							}
							break;
						}
						// 判断内容是否需要显示
						if (tIgnoreFlagNum > 0) {
							if (FLAG_MODEL_END.equals(tSubText)
									|| FLAG_BLANK_LINE.equals(tSubText)) {
								// 行结束符或者空白行操作，，跳出循环
								break;
							}
							// 处理合并单元格跨列的模式
							if (tRightCol != tLeftCol)
								tCol += tRightCol - tLeftCol;
							continue;
						}
						if (FLAG_MULTI_RECORD.equals(tSubText)
								|| FLAG_DATA_LIST.equals(tSubText)) {
							// 循环变量处理，$*，读取本行所有列
							if (tTableHeaderFlag) {
								// 设置表头，pdfptable是通过headerrows的行数来控制的
								tTable.setHeaderRows(tRow - tTableTitleRow);
								tTableTitleRow = 0;
							} else {
								tDocument.add(tTable);// 没有表头而出现多数据标志，需要保存一下之前的内容
							}

							tStrText = tStrText.substring(2);
							int tChildPos = tStrText.lastIndexOf("/");
							String tStrXPath = "/DATASET"
									+ tStrText.substring(0, tChildPos);
							String tStrNodeName;
							// 查询循环体数据信息
							cXmlFunc.query(tStrXPath);

							// 确定循环体每个部位的索引列值
							int[] tColIndex = new int[tCurrentColNum];
							String[][] tColCellDesArr = new String[tCurrentColNum][DESC_SIZE];
							for (int i = tCol; i < tCurrentColNum; i++) {
								tStrText = tSheet.getCell(i, tRow)
										.getContents().trim();
								this.mLogger.debug("-- " + tStrText + " -- "
										+ i);
								if ("".equals(tStrText)
										|| FLAG_MODEL_END.equals(tStrText)) {
									tCurrentColNum = i;
									break;// 到数据最右边一列，停止读取，并且修改读取最大列
								}
								if (tStrText.length() >= 2)
									tStrText = tStrText.substring(2);

								// 2011-9-21 增加循环体合并单元格判断
								tIntArr = judgeMergedCell(tRangeArr, i, tTopRow);
								// 得到最右边单元格的列和行
								tBottomRow = tIntArr[0];
								tRightCol = tIntArr[1];
								tLeftCol = i;

								// 需要读取每个单元格的属性
								tColCellDesArr[i] = getCellFormat(tSheet, i,
										tRightCol, tTopRow, tBottomRow,
										tStrText);
								if (tStrText.indexOf("|") >= 0) {
									tStrText = tStrText.substring(0, tStrText
											.indexOf("|"));
								}
								if (!"".equals(tStrText)) {
									tStrNodeName = tStrText
											.substring(tChildPos + 1);
								} else {
									tStrNodeName = tStrText;
								}
								tColIndex[i] = cXmlFunc
										.getColIndex(tStrNodeName);
								if (tRightCol != tLeftCol) {
									i += tRightCol - tLeftCol;
								}
							}

							String tStrValue;
							PdfDataFormat tPdfDataFormat = new PdfDataFormat();
							int tTailCol = tMaxCol - tCurrentColNum;
							final int limitNumConstent = 2280;
							int limitNum = 0;// 防止生成太大的table对象
							tTable = newTable(tTable);// 重新初始化表，只留下表头，防止空格的出现
							// 循环生成表格行
							while (cXmlFunc.next()) {
								if (tCol != 0) {
									String tTemp = tCellDescArr[2];
									tCellDescArr[2] = "0";
									tTable.addCell(new PdfCell(" ", tRowSpan,
											tCol, tCellDescArr));// 调整表格位置，如果不是从第0列开始的表格，填充表格左侧
									tCellDescArr[2] = tTemp;
								}
								// 循环生成表格列的信息
								for (int i = tCol; i < tCurrentColNum; i++) {
									tStrValue = cXmlFunc
											.getString(tColIndex[i]);
									// 判断是否有格式化信息
									if (!"".equals(tColCellDesArr[i][10])
											&& tColCellDesArr[i][10] != null) {
										tStrValue = tPdfDataFormat.formatData(
												tColCellDesArr[i][10],
												tStrValue);
									}
									tRowSpan = Integer
											.valueOf(tColCellDesArr[i][0]) + 1; // 所占行
									tColSpan = Integer
											.valueOf(tColCellDesArr[i][1]) + 1; // 所占列
									// tColCellDesArr[i][2] = "1";
									tTable.addCell(new PdfCell(tStrValue,
											tRowSpan, tColSpan,
											tColCellDesArr[i]));
									if (tColSpan > 1) {
										i += tColSpan - 1;
									}
								}
								if (tTailCol > 0) {// 调整表格位置，如果表格右侧有空位的话，填充空格
									String tTemp = tCellDescArr[2];
									tCellDescArr[2] = "0";
									tTable.addCell(new PdfCell(" ", tRowSpan,
											tTailCol, tCellDescArr));
									tCellDescArr[2] = tTemp;
								}

								tTable.completeRow();// 结束当前行，预防出错
								limitNum++;
								if (limitNum == limitNumConstent) {
									// 每隔一定数据量，将表格添加到Docment中，然后重新初始化表格，防止表格过大
									tDocument.add(tTable);
									tDocument.newPage();
									tPdfWriter.flush();
									tTable.deleteBodyRows();
									limitNum = 0;
								}
							}// 生成表结束
							tDocument.add(tTable);

							// 重新初始化表格对象
							tTable = newTable(tMaxCol);
							// 重新初始化表格表头标志
							tTableHeaderFlag = false;
							break;
						} else if (FLAG_PAGE_BREAK.equals(tSubText)) {
							// 换页的操作，跳出列循环；
							tDocument.add(tTable); // 先输出文档上半部分的信息
							tDocument.newPage(); // 做分页处理
							tTable = newTable(tMaxCol); // 重新设置表格信息
							break;
						} else if (FLAG_MODEL_END.equals(tSubText)
								|| FLAG_BLANK_LINE.equals(tSubText)) {
							// 行结束符或者空白行操作，，跳出循环
							tTable.addCell(new PdfCell("　", 1, tMaxCol - tCol,
									tCellDescArr));
							break;
						} else if (FLAG_TABLE_TITLE.equals(tSubText)) {
							// 清单表头处理
							if (!tTableHeaderFlag) {
								// 表头设置
								tDocument.add(tTable);// 将已有文档加入document中
								tTable = newTable(tMaxCol);
								tTableHeaderFlag = true;
								if (tCol != 0) {
									String tTemp = tCellDescArr[2];
									tCellDescArr[2] = "0";
									tTable.addCell(new PdfCell(" ", tRowSpan,
											tCol, tCellDescArr));
									tCellDescArr[2] = tTemp;
								}
								tTableTitleRow = tRow;
							}
							tCellDescArr[2] = "1";
							tTable.addCell(new PdfCell(tStrText.substring(2),
									tRowSpan, tColSpan, tCellDescArr));
						} else if (FLAG_IMAGE.equals(tSubText)) {
							// 固定图片文件处理 2011-9-6增加，需要图片文件放在指定目录下
							Image tImage = Image.getInstance(cAppPath
									+ "print/templete/image/"
									+ tStrText.substring(2));
							// 图片的单独处理
							tTable.addCell(new PdfCell(tImage, tColSpan));
						} else if (FLAG_IMAGE_BAR.equals(tSubText)) {
							// 条形码处理
							Image tImage = createImage(cXmlFunc, tStrText
									.substring(2));
							// 图片的单独处理
							tTable.addCell(new PdfCell(tImage, tColSpan));
						} else {
							// 非循环变量处理，$=
							String tTitle = "";
							// 解析字符串信息
							tTitle += parseString(tStrText, cXmlFunc);
							tTable.addCell(new PdfCell(tTitle, tRowSpan,
									tColSpan, tCellDescArr));
						}
					}
					// 处理合并单元格跨列的模式
					if (tRightCol != tLeftCol)
						tCol += tRightCol - tLeftCol;
				}
				if (tCol == 0 && FLAG_MODEL_END.equals(tStrText))
					break;// 首列为终止符，模板结束
			}
			// 如果表格不为空，则添加到doc对象中
			if (tTable != null)
				tDocument.add(tTable);

		} catch (Exception e) {
			mLogger.error("生成pdf文件异常……");
			e.printStackTrace();
			return false;
		} finally {
			if (tDocument != null) {
				tDocument.close();
			}
			if (tFileOutputStream != null) {
				try {
					tFileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		mResult.add(tTempFileName);
		return true;
	}

	/**
	 * 根据遍历到的行，添加图片
	 * 
	 * @param tRow
	 * @param tDocument
	 * @param tSheet
	 */
	private void addImage(int tRow, Document tDocument, Sheet tSheet) {
		// TODO Auto-generated method stub
		try {
			for (int imageNo = 0, size = this.imageDataArr.length; imageNo < size; imageNo++) {
				int rowOfImage = (int) this.imageDataArr[imageNo][2];
				int colOfImage = (int) this.imageDataArr[imageNo][3];
				if (rowOfImage == tRow) {
					if (this.imageArr.size() == 0)
						break;// 没有数据退出循环
					float width = 0, height = 0;// 逐个逐行的计算出高宽
					int i, j;
					for (i = rowOfImage, j = rowOfImage
							+ (int) this.imageDataArr[imageNo][1]; i <= j; i++) {
						height += (float) (tSheet.getRowView(i).getSize());
					}
					height += (float) (tSheet.getRowView(j).getSize() * (this.imageDataArr[imageNo][1]
							- (int) this.imageDataArr[imageNo][1] - 1));
					height = height / this.UNIT_RATE_HEIGHT;
					for (i = colOfImage, j = colOfImage
							+ (int) this.imageDataArr[imageNo][0]; i <= j; i++) {
						width += (float) (tSheet.getColumnView(i).getSize());
					}
					width += (float) (tSheet.getColumnView(j).getSize() * (this.imageDataArr[imageNo][0]
							- (int) this.imageDataArr[imageNo][0] - 1));
					width = width / this.UNIT_RATE_WIDTH;

					//设置宽高
					Image d = this.imageArr.remove(0);//获取当前图片数据
					d.scaleAbsolute(width, height);
					//准备X,Y值
					float positionX = 0, positionY = 0;
					for (i = 0; i <= colOfImage; i++) {
						positionX += tSheet.getColumnView(i).getSize();
					}
					positionX = (positionX / this.UNIT_RATE_WIDTH)
							+ tDocument.leftMargin();
					for (i = 0; i <= rowOfImage; i++) {
						positionY += tSheet.getRowView(i).getSize();
					}
					positionY = (positionY / this.UNIT_RATE_HEIGHT)
							+ tDocument.topMargin();
					positionY = tDocument.getPageSize().getHeight() - positionY;
					d.setAbsolutePosition(positionX, positionY);//设置X,Y值
					tDocument.add(d);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 根据sheet中的图片对象生成相应行列数据,和相应的图片数据
	 * 
	 * @param document
	 * @param sheet
	 */
	private void initImageData(Sheet sheet, XmlFunc cXmlFunc,
			String tTemplateFile) {
		// TODO Auto-generated method stub
		int size = sheet.getNumberOfImages();
		// 先初始化图片大小位置信息
		imageDataArr = new double[size][4];
		jxl.Image a;
		for (int i = 0; i < size; i++) {
			a = sheet.getDrawing(i);
			this.imageDataArr[i][0] = a.getWidth();
			this.imageDataArr[i][1] = a.getHeight();
			this.imageDataArr[i][2] = a.getRow();
			this.imageDataArr[i][3] = a.getColumn();
		}
		try {
//			BookModelImpl tTempBookModelImpl = new BookModelImpl();
//			tTempBookModelImpl.read(this.mAppPath + tTemplateFile,
//					new ReadParams());// 为了获得对象名称，必须使用此类来解析xls模板

			// 初始化图片内容数据
			// 查询条形码信息
			String tBarCodePath = "/DATASET/BarCodeInfo/BarCode";
			cXmlFunc.query(tBarCodePath);
			int tCodeIndex = cXmlFunc.getColIndex("Code");
			int tNameIndex = cXmlFunc.getColIndex("Name");// 对象名称，实际上不使用xml中的名称，而读取模板中的名称
			int tBarTypeIndex = cXmlFunc.getColIndex("Type");// 条码类型，同上，由模板中决定
			int tContentIndex = cXmlFunc.getColIndex("Content");// 二维码加密数据部分
			// 条形码生成逻辑判断
			String tCode = "";
			String tName = "";
			String tBarType = "";
			String tContent = "";
			this.imageArr = new ArrayList<Image>();
			for (int i = 0; i < size; i++) {
				if (cXmlFunc.next()) {
					tName = cXmlFunc.getString(tNameIndex);
//					tName = tTempBookModelImpl.getObject(i + 1).getName();
					tCode = cXmlFunc.getString(tCodeIndex);
					tContent = cXmlFunc.getString(tContentIndex);
					tBarType = cXmlFunc.getString(tBarTypeIndex);
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
						// 二维条码处理
						DBarCode tDCode = new DBarCode(tCode + tContent);
						imageArr.add(i, Image.getInstance(tDCode.getBytes()));
					} else {
						// 一维条码处理
						BarCode tBarCode = new BarCode(tCode);
						imageArr.add(i, Image.getInstance(tBarCode.getBytes()));
					}
				}
			}
//			tTempBookModelImpl.destroy();
//			tTempBookModelImpl = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 初始化页面大小单位换算率
	 * 
	 * @param document
	 */
	private void initUnitRate(Document document) {
		// TODO Auto-generated method stub
		this.UNIT_RATE_HEIGHT = this.SHEET_HEIGHT
				/ (document.getPageSize().getHeight() - document.bottomMargin() - document
						.topMargin());
		this.UNIT_RATE_WIDTH = this.SHEET_WIDTH
				/ (document.getPageSize().getWidth() - document.leftMargin() - document
						.rightMargin());
	}

	/**
	 * 根据传入列数创建表格，并返回
	 * 
	 * @param cCols
	 * @return PdfPTable
	 */
	private PdfPTable newTable(int cCols) {
		PdfPTable tPdfTable = new PdfPTable(cCols);
		tPdfTable.setWidthPercentage(100); // 100%
		tPdfTable.setHorizontalAlignment(Element.ALIGN_CENTER); // 水平位置
		return tPdfTable;
	}

	/**
	 * 根据传入列数创建表格，并返回
	 * 
	 * @param cCols
	 * @return PdfPTable
	 */
	private PdfPTable newTable(PdfPTable tTable) {
		PdfPTable aPdfPTable = newTable(tTable.getNumberOfColumns());
		int aHeaderNum = tTable.getHeaderRows();
		PdfPRow[] aPdfPRowArr = new PdfPRow[aHeaderNum];
		for (int i = 0; i < aHeaderNum; i++)
			aPdfPRowArr[i] = tTable.getRow(i);
		for (int i = 0; i < aHeaderNum; i++)
			for (PdfPCell aPdfPCell : aPdfPRowArr[i].getCells())
				if (aPdfPCell != null)
					aPdfPTable.addCell(aPdfPCell);
		return aPdfPTable;
	}

	/**
	 * 判断单元格是否合并单元格，并返回合并单元格的所占行、所占列及是否合并标志
	 * 
	 * @param cRangeArr
	 * @param cLeftCol
	 * @param cTopRow
	 * @return int[]
	 */
	private int[] judgeMergedCell(Range[] cRangeArr, int cLeftCol, int cTopRow) {
		int[] tIntArr = new int[3];
		tIntArr[0] = cTopRow;
		tIntArr[1] = cLeftCol;
		tIntArr[2] = 0;
		for (Range tRange : cRangeArr) {
			// 判断该单元格是否合并单元格
			if (cTopRow >= tRange.getTopLeft().getRow()
					&& cTopRow <= tRange.getBottomRight().getRow()
					&& cLeftCol >= tRange.getTopLeft().getColumn()
					&& cLeftCol <= tRange.getBottomRight().getColumn()) {
				// 得到最右边单元格的列和行
				tIntArr[0] = tRange.getBottomRight().getRow();
				tIntArr[1] = tRange.getBottomRight().getColumn();
				// 针对合并多行的单元格的一个处理
				if (tIntArr[0] != tRange.getTopLeft().getRow()
						&& cTopRow > tRange.getTopLeft().getRow()) {
					// tCellMeraged = true;
					tIntArr[2] = 1;
					break;
				}
			}
		}
		return tIntArr;
	}

	/**
	 * 返回指定单元格的格式信息<br>
	 * 包括：所占行数、所占列数、边框状态、文字位置、文字颜色、文字大小、文字样式、粗体、斜体、下划线、单元格格式
	 * 
	 * @param cSheet
	 * @param cLeftCol
	 * @param cRightCol
	 * @param cTopRow
	 * @param cBottomRow
	 * @return String[]
	 */
	private String[] getCellFormat(Sheet cSheet, int cLeftCol, int cRightCol,
			int cTopRow, int cBottomRow, String cStrText) {
		String tDataFormatType = "";
		if (cStrText.indexOf("|") >= 0) {
			tDataFormatType = cStrText.substring(cStrText.indexOf("|") + 1);
		}

		// 单元格的信息数组
		String[] tCellDescArr = new String[DESC_SIZE];
		tCellDescArr[0] = "0"; // 行宽
		tCellDescArr[1] = "0"; // 列宽
		tCellDescArr[2] = "0"; // 边框信息 默认无边框
		tCellDescArr[3] = "0"; // 文字位置 默认居左
		tCellDescArr[4] = "0"; // 文字颜色 默认黑色
		tCellDescArr[5] = "11"; // 字体的大小
		tCellDescArr[6] = "0"; // 字体的样式
		tCellDescArr[7] = "000"; // 是否加粗，升级后包括：粗体、斜体、下划线
		tCellDescArr[8] = "0"; // 是否斜体，废弃
		tCellDescArr[9] = "0"; // 是否下划线，废弃
		tCellDescArr[10] = ""; // 格式
		tCellDescArr[11] = "none,none,none,none"; // 边框的格式，例如：虚线、双线等
		tCellDescArr[12] = "true"; // 是否自动换行
		tCellDescArr[13] = "0"; // 是否设置最小行高
		try {
			CellFormat cf = cSheet.getCell(cLeftCol, cTopRow).getCellFormat();
			// 单元格格式
			// tCellDescArr[10] = cSheet.getCell(cLeftCol,
			// cTopRow).getType().toString();
			// 单元格格式，目前支持$*类型，采用自配置处理
			if (!"".equals(tDataFormatType)) {
				tCellDescArr[10] = tDataFormatType;
			}

			if (cf != null) {
				tCellDescArr[13] = String.valueOf(cSheet.getRowView(cTopRow)
						.getSize()
						/ this.UNIT_RATE_HEIGHT);// 最小行高,这里除以一个数是为了换算成pdf中的单位
				if (!cf.getWrap()) {
					tCellDescArr[12] = "false";// noWrap不自动换行
				}
				// 单元格的边框信息，先判断左和上
				tCellDescArr[2] = cf.getBorder(Border.LEFT).getValue() + ""
						+ cf.getBorder(Border.TOP).getValue() + "";
				// 单元格的边框样式信息，先判断左和上
				tCellDescArr[11] = cf.getBorderLine(Border.LEFT)
						.getDescription()
						+ ","
						+ cf.getBorderLine(Border.TOP).getDescription()
						+ ",";

				// 文字位置：0左1中2右
				tCellDescArr[3] = (cf.getAlignment().getValue() - 1) + "";
				// 文字颜色，默认黑色
				tCellDescArr[4] = cf.getFont().getColour().getDescription()
						+ "";
				// 字体的大小
				tCellDescArr[5] = cf.getFont().getPointSize() + "";
				// 字体的样式
				tCellDescArr[6] = cf.getFont().getName() + "";
				// 字体样式描述串
				String tFontStyle = "";
				// 是否加粗，700粗400无，jxl的反馈很诡异
				if (cf.getFont().getBoldWeight() == 400) {
					tCellDescArr[7] = "0"; // 正常
					tFontStyle = tFontStyle + "0";
				} else {
					tCellDescArr[7] = "1"; // 粗体
					tFontStyle = tFontStyle + "1";
				}
				tCellDescArr[8] = cf.getFont().isItalic() + "";
				// 是否斜体，true斜false无，置换为0无1有
				if (cf.getFont().isItalic()) {
					tFontStyle = tFontStyle + "1";
				} else {
					tFontStyle = tFontStyle + "0";
				}
				// 是否下划线，0无1有
				tCellDescArr[9] = cf.getFont().getUnderlineStyle().getValue()
						+ "";
				tFontStyle = tFontStyle
						+ cf.getFont().getUnderlineStyle().getValue();
				tCellDescArr[7] = tFontStyle; // 统一格式，包含粗体、斜体、下划线

				// 判断是否合并单元格，用来判断边框的右和下，以及合并的行宽、列宽
				if (cLeftCol == cRightCol && cTopRow == cBottomRow) {
					tCellDescArr[0] = "0"; // 行宽
					tCellDescArr[1] = "0"; // 列宽
				} else {
					cf = cSheet.getCell(cRightCol, cBottomRow).getCellFormat();
					tCellDescArr[0] = (cBottomRow - cTopRow) + ""; // 行宽
					tCellDescArr[1] = (cRightCol - cLeftCol) + ""; // 列宽
					// // 边框信息，例如：1111，0000
					// tCellDescArr[2] += cf.getBorder(Border.RIGHT).getValue()
					// + ""
					// + cf.getBorder(Border.BOTTOM).getValue();
				}
				// 边框信息，例如：1111，0000
				tCellDescArr[2] += cf.getBorder(Border.RIGHT).getValue() + ""
						+ cf.getBorder(Border.BOTTOM).getValue();
				// 边框样式信息，例如：none,none,none,none
				tCellDescArr[11] += cf.getBorderLine(Border.RIGHT)
						.getDescription()
						+ ","
						+ cf.getBorderLine(Border.BOTTOM).getDescription();
			}
		} catch (Exception e) {
			// 如果异常，使用默认值
			// e.printStackTrace();
			mLogger.error("解析单元格信息异常...");
		}
		return tCellDescArr;
	}

	/**
	 * 创建条形码图像
	 * 
	 * @param cXmlFunc
	 * @param cName
	 * @return Image
	 */
	private Image createImage(XmlFunc cXmlFunc, String cName) {
		// 查询条形码信息
		String tBarCodePath = "/DATASET/BarCodeInfo/BarCode";
		cXmlFunc.query(tBarCodePath);
		int tCodeIndex = cXmlFunc.getColIndex("Code");
		int tNameIndex = cXmlFunc.getColIndex("Name");
		int tBarTypeIndex = cXmlFunc.getColIndex("Type");

		// 条形码生成逻辑判断
		String tCode = "";
		String tName = "";
		String tBarType = "";

		Image tImage = null;
		while (cXmlFunc.next()) {
			tName = cXmlFunc.getString(tNameIndex);
			if (cName.equals(tName)) {
				tCode = cXmlFunc.getString(tCodeIndex);
				tBarType = cXmlFunc.getString(tBarTypeIndex);

				try {
					// 使用什么条形码类型
					if (tBarType.equals("DBarCode")) {
						// 二维条码处理
						DBarCode tDBarCode = new DBarCode(tCode);
						tImage = Image.getInstance(tDBarCode.getBytes());
					} else {
						logger.debug("进入这里。。。");
						// 一维条码处理
						BarCode tBarCode = new BarCode(tCode);
						tImage = Image.getInstance(tBarCode.getBytes());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			} else {
				continue;
			}
		}
		// 兼容以往的数据模式
		if (tImage == null) {
			for (int k = 1; k <= 10; k++) {
				// 老模式条形码编号是BarCode1、BarCode2顺序排列，不超过10个，旧模式不支持二维码
				if (cName.equals("BarCode" + k)) {
					tCode = cXmlFunc.getNodeValue("/DATASET/BarCode" + k);
					if (tCode != null && !tCode.equalsIgnoreCase("")) {
						BarCode tBarCode = new BarCode(tCode);
						try {
							tImage = Image.getInstance(tBarCode.getBytes());
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
		return tImage;
	}

	/**
	 * 解析字符串信息，并整合要替换的数据项
	 * 
	 * @param cStrText
	 * @param cXmlFunc
	 * @return String
	 */
	private String parseString(String cStrText, XmlFunc cXmlFunc) {
		String tStrText = "";
		String tStrTemp = "";
		int tFlag = 0;

		for (int i = 0; i < cStrText.length(); i++) {
			if (FLAG_FLAG == cStrText.charAt(i)) {
				switch (tFlag) {
				case 0: // ordinary string
					tFlag = 1;
					tStrText += tStrTemp;
					tStrTemp = "";
					break;
				case 1: // special string
					tFlag = 0;
					if (tStrTemp.equals("")) {
						tStrText += FLAG_FLAG;
					} else {
						if (tStrTemp.length() > 1 && '=' == tStrTemp.charAt(0)) {
							tStrTemp = tStrTemp.substring(1);
							tStrTemp = cXmlFunc.getNodeValue("/DATASET"
									+ tStrTemp);
						}
						tStrText += tStrTemp;
					}
					tStrTemp = "";
					break;
				}
			} else {
				tStrTemp += cStrText.charAt(i);
			}
		}

		if (1 == tFlag) {
			if (tStrTemp.length() > 1 && '=' == tStrTemp.charAt(0)) {
				tStrTemp = tStrTemp.substring(1);
				tStrTemp = cXmlFunc.getNodeValue("/DATASET" + tStrTemp);
			}
		}

		return tStrText + tStrTemp;
	}

	/**
	 * 初始方法
	 * 
	 * @param cVData
	 * @param cOperater
	 * @return boolean
	 */
	public boolean submitData(VData cVData, String cOperater) {
		boolean tFlag = true;

		if (!getInputData(cVData)) {
			CError tError = new CError();
			tError.moduleName = "PdfJxlPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "获得信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		XmlFunc tXmlFunc = parserIS(new ByteArrayInputStream(mbyte));
		if (tFlag && tXmlFunc == null) {
			CError tError = new CError();
			tError.moduleName = "PdfJxlPrint";
			tError.functionName = "parserIS";
			tError.errorMessage = "传入的xml信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}

		if (tFlag && !printPDF(mGlobalInput, mAppPath, tXmlFunc)) {
			CError tError = new CError();
			tError.moduleName = "PdfJxlPrint";
			tError.functionName = "printPDF";
			tError.errorMessage = "打印异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}
		if (tFlag) {
			// 打印成功
			PrintFunc.addPrintLog(mTransferData, "0", "打印成功",
					mGlobalInput.Operator);
		} else {
			// 打印失败，错误信息放到第三个参数
			PrintFunc.addPrintLog(mTransferData, "1", mErrors.getFirstError(),
					mGlobalInput.Operator);
		}

		return tFlag;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @param cInputData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		InputStream tInputStream = (InputStream) cInputData.get(0);
		mbyte = PrintFunc.transformInputstream(tInputStream);
		mGlobalInput = (GlobalInput) cInputData.get(1);
		mAppPath = (String) cInputData.get(2);
		mTransferData = (TransferData) cInputData.get(3);

		return true;
	}

	/**
	 * 返回容器给前台
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息给前台
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 解析传入流的xml信息
	 * 
	 * @param cInputStream
	 * @return XmlFunc
	 */
	private XmlFunc parserIS(InputStream cInputStream) {
		try {
			return (new XmlFunc(cInputStream));
		} catch (Exception e) {
			mLogger.error("xml流解析异常...");
			return null;
		}
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";

		File tFile = new File("c:/2.xml");
		FileInputStream tFis = new FileInputStream(tFile);

		Object tObj = tFis;

		InputStream is = (InputStream) tObj;

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("OutputType", "1");
		tTransferData.setNameAndValue("Output", "1");
		tTransferData.setNameAndValue("Language", "zh");

		VData tVData = new VData();
		tVData.add(is);
		tVData.add(tGI);
		tVData.add("D:/Work/LIS7/Develop/lis-national/ui/");
		// tVData.add("D:/");
		// tVData.add("D:/lis-national-Works/lis-national/ui/");
		tVData.add(tTransferData);

		PdfJxlPrint tSP = new PdfJxlPrint();
		tSP.submitData(tVData, "");
	}
}