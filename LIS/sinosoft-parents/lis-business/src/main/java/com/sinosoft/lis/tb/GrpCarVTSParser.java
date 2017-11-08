package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

import com.f1j.ss.BookModelImpl;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class GrpCarVTSParser {
private static Logger logger = Logger.getLogger(GrpCarVTSParser.class);
	public CErrors mErrors = new CErrors();

	private String m_strBatchNo = "";
	private String m_GrpContNo = "";

	// 保存磁盘投保文件的文件名
	private String m_strFileName = "";
	private String m_strPathName = "";

	// 保存列名元素名映射文件的文件名
	private String m_strConfigFileName = "";

	// 保存Sheet相关的信息，如最大行，最大列，当前处理到的行等
	private Hashtable[] m_hashInfo = null;

	// 常量定义
	private static String PROP_MAX_ROW = "max_row";
	private static String PROP_MAX_COL = "max_col";
	private static String PROP_CUR_ROW = "cur_row";
	private static String PROP_COL_NAME = "col_name";

	private static final int SHEET_COUNT = 2;

	/** sheet代表的数据集,以及索引所在的列 */
	private final int SHEET_CAR = 0;
	private final int STRID_COL_CAR = 0;
	private final int SHEET_LCPOL = 1;
	private final int STRID_COL_LCPOL = 0;

	private static final int ROW_LIMIT = 1000; // 一次处理的行数

	private BookModelImpl m_book = new BookModelImpl();

	// 用来保存每一个小部分生成的XML文件名
	private List m_listFiles = new ArrayList();

	public GrpCarVTSParser() {
		m_hashInfo = new Hashtable[SHEET_COUNT];

		for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++) {
			m_hashInfo[nIndex] = new Hashtable();
			m_hashInfo[nIndex].put(PROP_CUR_ROW, "1"); // 从第一行开始解析
		}

	}

	// 设置要处理的文件名
	public boolean setFileName(String strFileName) {
		File file = new File(strFileName);
		if (!file.exists()) {
			buildError("setFileName", "指定的文件不存在！");
			return false;
		}

		m_strFileName = strFileName;
		m_strPathName = file.getParent();

		int nPos = strFileName.lastIndexOf('.');

		if (nPos == -1) {
			nPos = strFileName.length();
		}

		m_strBatchNo = strFileName.substring(strFileName.lastIndexOf("/") + 1,
				nPos);

		nPos = m_strBatchNo.lastIndexOf('\\');

		if (nPos != -1) {
			m_strBatchNo = m_strBatchNo.substring(nPos + 1);
		}

		nPos = m_strBatchNo.lastIndexOf('/');

		if (nPos != -1) {
			m_strBatchNo = m_strBatchNo.substring(nPos + 1);
		}

		// 总单合同号
		if (m_strBatchNo.indexOf("_") > 0) {
			m_GrpContNo = m_strBatchNo.substring(0, m_strBatchNo.indexOf("_"));
		} else {
			m_GrpContNo = m_strBatchNo;
		}
		return true;
	}

	public String getFileName() {
		return m_strFileName;
	}

	// 设置映射文件名
	public boolean setConfigFileName(String strConfigFileName) {
		File file = new File(strConfigFileName);
		if (!file.exists()) {
			buildError("setFileName", "指定的文件不存在！");
			return false;
		}

		m_strConfigFileName = strConfigFileName;
		return true;
	}

	public String getConfigFileName() {
		return m_strConfigFileName;
	}

	public String[] getDataFiles() {
		String[] files = new String[m_listFiles.size()];

		for (int nIndex = 0; nIndex < m_listFiles.size(); nIndex++) {
			files[nIndex] = (String) m_listFiles.get(nIndex);
		}

		return files;
	}

	/**
	 * 转换操作，将磁盘投保文件转换成指定格式的XML文件。
	 * 
	 * @return boolean
	 */
	public boolean transform() {
		// ////add verify by yaory 2005-7-12//////

		// ///end add////////////////////////////
		String strFileName = "";
		int nCount = 0;

		try {
			int nCurRow = getPropNumber(0, PROP_CUR_ROW);
			int nMaxRow = getPropNumber(0, PROP_MAX_ROW);
			logger.debug("=beforverify== nCurRow ==" + nCurRow);
			logger.debug("=beforverify== nMaxRow ==" + nMaxRow);

			verify();

			int nCurRow2 = getPropNumber(0, PROP_CUR_ROW);
			int nMaxRow2 = getPropNumber(0, PROP_MAX_ROW);
			logger.debug("=afterverify== nCurRow ==" + nCurRow2);
			logger.debug("=afterverify== nMaxRow ==" + nMaxRow2);

			while (hasMoreData()) {
				strFileName = m_strPathName + File.separator + m_strBatchNo
						+ String.valueOf(nCount++) + ".xml";
				logger.debug("strFileName:" + strFileName);
				genPart(strFileName);

				m_listFiles.add(strFileName);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("transfrom", ex.getMessage());
			return false;
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "GrpPolVTSParser";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 读取配置信息，将配置信息的每一sheet中的列名缓存
	 * 
	 * @throws Exception
	 */
	private void verify() throws Exception {
		if (m_strFileName.equals("")) {
			throw new Exception("请先调用setFileName函数设置要处理的文件名。");
		}

		m_book.read(m_strFileName, new com.f1j.ss.ReadParams());

		// ///////////add by yaory//////////
		logger.debug("哈哈哈哈哈哈哈哈哈哈===" + SHEET_COUNT);
		if (m_book.getNumSheets() < SHEET_COUNT) {
			throw new Exception("磁盘投保文件不完整，缺少Sheet。");
		}

		// 将第一行中元素的值设为对应的XML元素的名字
		// 如在Sheet0中，每行的第一列对应的XML元素名字为ID。
		DOMBuilder db = new DOMBuilder();
		Document doc = db.build(new FileInputStream(m_strConfigFileName));
		Element eleRoot = doc.getRootElement();
		Element ele = null;
		String strColName = "";

		for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++) {
			int nMaxRow = getMaxRow(nIndex);
			String sheetId = "Sheet" + String.valueOf(nIndex + 1);
			try {
				ele = eleRoot.getChild(sheetId);
				int nMaxCol = getMaxCol(nIndex);
				String[] strArr = new String[nMaxCol];

				for (int nCol = 0; nCol < nMaxCol; nCol++) {
					strColName = ele.getChildText("COL" + String.valueOf(nCol));
					strArr[nCol] = strColName;
				}
				setPropArray(nIndex, PROP_COL_NAME, strArr);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception("找不到对应的配置信息，Sheet"
						+ String.valueOf(nIndex + 1));
			}
		}
	}

	/**
	 * 执行一次生成，将ROW_LIMIT行数的VTS数据生成一个Document
	 * 
	 * @param strFileName
	 *            String
	 * @throws Exception
	 */
	private void genPart(String strFileName) throws Exception {
		Element root = new Element("DATASET");
		Document doc = new Document(root);

		Element ele = new Element("BATCHID");
		ele.setText(m_strBatchNo);
		root.addContent(ele);

		ele = new Element("GRPCONTNO");
		ele.setText(m_GrpContNo);
		root.addContent(ele);

		ele = new Element("CONTTABLE");
		root.addContent(ele);
		genXMLPart(ele, this.SHEET_CAR);

		XMLOutputter xo = new XMLOutputter("  ", true, "GBK");
		xo.output(doc, new FileOutputStream(strFileName));
	}

	/**
	 * 判断是否还有数据没有处理 如果在存放保单信息的Sheet中，已经处理到了最大行，返回false； 否则，返回true;
	 * 
	 * @return boolean
	 */
	private boolean hasMoreData() {
		int nCurRow = getPropNumber(0, PROP_CUR_ROW);
		int nMaxRow = getPropNumber(0, PROP_MAX_ROW);

		if (nCurRow >= nMaxRow) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 取最大行值
	 * 
	 * @param nSheetIndex
	 *            int
	 * @return int
	 * @throws Exception
	 */
	private int getMaxRow(int nSheetIndex) throws Exception {
		String str = "";
		int nMaxRow = getPropNumber(nSheetIndex, PROP_MAX_ROW);

		if (nMaxRow == -1) {
			for (nMaxRow = 0; nMaxRow < m_book.getMaxRow(); nMaxRow++) {
				str = m_book.getText(nSheetIndex, nMaxRow, 0);
				if (str == null || str.trim().equals("")) {
					break;
				}
			}
			setPropNumber(nSheetIndex, PROP_MAX_ROW, nMaxRow);
		}

		return nMaxRow;
	}

	/**
	 * 取最大列值
	 * 
	 * @param nSheetIndex
	 *            int
	 * @return int
	 * @throws Exception
	 */
	private int getMaxCol(int nSheetIndex) throws Exception {
		String str = "";
		int nMaxCol = getPropNumber(nSheetIndex, PROP_MAX_COL);

		if (nMaxCol == -1) {
			for (nMaxCol = 0; nMaxCol < m_book.getMaxCol(); nMaxCol++) {
				str = m_book.getText(nSheetIndex, 0, nMaxCol);
				if (str == null || str.trim().equals("")) {
					break;
				}
			}

			setPropNumber(nSheetIndex, PROP_MAX_COL, nMaxCol);
		}
		return nMaxCol;
	}

	/**
	 * 读取当前行值或最大行值、列值等
	 * 
	 * @param nSheetIndex
	 *            int
	 * @param strPropName
	 *            String
	 * @return int
	 */
	private int getPropNumber(int nSheetIndex, String strPropName) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		String str = (String) hash.get(strPropName);
		if (str != null && !str.equals("")) {
			return Integer.parseInt(str);
		} else {
			return -1;
		}
	}

	/**
	 * 缓存当前行值或最大行值、列值等
	 * 
	 * @param nSheetIndex
	 *            int
	 * @param strPropName
	 *            String
	 * @param nValue
	 *            int
	 */
	private void setPropNumber(int nSheetIndex, String strPropName, int nValue) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		hash.put(strPropName, String.valueOf(nValue));
	}

	/**
	 * 从缓存中读取当前sheet页的列名
	 * 
	 * @param nSheetIndex
	 *            int
	 * @param strPropName
	 *            String
	 * @return String[]
	 */
	private String[] getPropArray(int nSheetIndex, String strPropName) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		String[] strArr = (String[]) hash.get(strPropName);
		return strArr;
	}

	/**
	 * 将底层配置文件CarImport.xml的列名缓存以备用
	 * 
	 * @param nSheetIndex
	 *            int
	 * @param strPropName
	 *            String
	 * @param strArr
	 *            String[]
	 */
	private void setPropArray(int nSheetIndex, String strPropName,
			String[] strArr) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		hash.put(strPropName, strArr);
	}

	/**
	 * 生成xml
	 * 
	 * @param eleParent
	 *            Element
	 * @param sheetNo
	 *            int
	 * @throws Exception
	 */
	private void genXMLPart(Element eleParent, int sheetNo) throws Exception {
		// 保单信息存放在第一个Sheet中。
		int nCurRow = getPropNumber(sheetNo, PROP_CUR_ROW);
		int nMaxRow = getMaxRow(sheetNo);

		String strID = "";

		int nRow = 0;
		int nCount = 0;

		// 对于有主附险的磁盘导入文件，只是前面的保单信息不同，
		// 后面的保费、责任、被保险人及受益人等信息都是相同的，不需要每次都扫描。
		// 主附险的唯一号是一样的。
		String strIDCached = "";
		// nRow控制sheet行递增
		for (nRow = nCurRow, nCount = 0; nRow < nMaxRow; nRow++) {

			// 取得唯一号信息 -- 合同ID
			strID = m_book.getText(sheetNo, nRow, this.STRID_COL_CAR);
			// nCount控制xml中合同行递增
			// 如果超出最小行限制并且合同ID已变 则跳出生成新文件
			// 目的是保证同一合同的信息保存在同一文件中
			if (++nCount > GrpCarVTSParser.ROW_LIMIT
					&& !strID.equals(strIDCached)) {

				break;
			}

			// ///////////////////////////////
			// 如果是同一张保单，后面的信息不用重新生成
			// BTW：现在也没法重新生成，因为现在只做一次扫描
			if (!strID.equals(strIDCached)) {
				strIDCached = strID; // 缓存上一次操作的保单唯一号:合同ID

				Element eleRow = new Element("ROW");
				eleParent.addContent(eleRow);
				// Element eleCached = new Element("CACHED");

				Element eleField = new Element("CONTID");
				eleField.setText(strID);
				eleRow.removeChild("CONTID"); // 每一个保单信息都要重新生成ContId
				eleRow.addContent(eleField);

				// 被保人信息
				Element eleCar = new Element("CARTABLE");
				eleRow.removeChild("CARTABLE");
				eleRow.addContent(eleCar);
				genPartSubTable(eleCar, this.SHEET_CAR, strID,
						this.STRID_COL_CAR);

				// 保单信息
				Element elePol = new Element("LCPOLTABLE");
				eleRow.removeChild("LCPOLTABLE");
				eleRow.addContent(elePol);
				genPartSubTable(elePol, this.SHEET_LCPOL, strID,
						this.STRID_COL_LCPOL);

				// eleRow.setMixedContent(eleCached.getMixedContent());

			}

		}

		setPropNumber(sheetNo, PROP_CUR_ROW, nRow);

	}

	/**
	 * 处理子表 因为在Sheet的数据中，是按照“唯一号”ID排序的，所以对子表的数据， 我们也只需要扫描一次就可以了。
	 * 
	 * @param eleParent
	 *            Element
	 * @param nSheetIndex
	 *            int
	 * @param strID
	 *            String
	 * @param strIDColIndex
	 *            int
	 * @throws Exception
	 */
	private void genPartSubTable(Element eleParent, int nSheetIndex,
			String strID, int strIDColIndex) throws Exception {
		int nCurRow = getPropNumber(nSheetIndex, PROP_CUR_ROW);
		int nMaxRow = getMaxRow(nSheetIndex);
		int nMaxCol = getMaxCol(nSheetIndex);

		String[] strColName = getPropArray(nSheetIndex, PROP_COL_NAME);

		int nRow = 0;

		for (nRow = nCurRow; nRow < nMaxRow; nRow++) {
			// 不是同一合同ID跳出
			if (!strID.equals(m_book.getText(nSheetIndex, nRow, strIDColIndex))) {
				break;
			}

			Element eleRow = new Element("ROW");
			eleParent.addContent(eleRow);

			for (int nCol = 0; nCol < nMaxCol; nCol++) {

				Element ele = new Element(strColName[nCol]);
				ele.setText(m_book.getText(nSheetIndex, nRow, nCol));
				eleRow.addContent(ele);
			}
			if (nSheetIndex == this.SHEET_LCPOL) {

			}

		}
		setPropNumber(nSheetIndex, PROP_CUR_ROW, nRow);
	}

	public static void main(String[] args) {
		GrpCarVTSParser grpcarvtsparser = new GrpCarVTSParser();
	}
}
