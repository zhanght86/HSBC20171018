/*
 * @(#)GrpPolVTSParser.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 将磁盘投保文件解析成指定格式的XML文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Kevin
 * @version 6.0
 * @date 2003-07-09
 */
public class GrpPolVTSParser {
private static Logger logger = Logger.getLogger(GrpPolVTSParser.class);
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

	private static final int SHEET_COUNT = 3;

	/** sheet代表的数据集,以及索引所在的列 */
	private final int SHEET_INSURED = 0;
	private final int STRID_COL_INSURED = 0;
	private final int SHEET_LCPOL = 1;
	private final int STRID_COL_LCPOL = 0;
	// private int SHEET_PREM = 2;
	// private final int SHEET_PREM = 3;
	// private final int STRID_COL_PREM = 0;
	// private int SHEET_DUTY = 3;
	// private final int SHEET_DUTY = 2;
	// private final int STRID_COL_DUTY = 0;
	private final int SHEET_BNF = 2;
	private final int STRID_COL_BNF = 0;
	// private final int SHEET_INS_RELA = 4;
	// private final int STRID_COL_INS_RELA = 0;
	private final int STRID_POL = 0;

	private final int POL_COL_RISKCODE = 1; // Excel的险种保单中，险种编码所在列
	private final int POL_COL_DUTYCODE = 2; // Excel的险种保单中，责任编码所在列
	private final int POL_COL_PAYPLANCODE = 3; // Excel的险种保单中，责任编码所在列

	private static final int ROW_LIMIT = 1000; // 一次处理的行数
	private int contIndex = 0; // 一次最多处理的合同数

	private BookModelImpl m_book = new BookModelImpl();

	// 用来保存每一个小部分生成的XML文件名
	private List m_listFiles = new ArrayList();

	private HashMap mContRiskCache = new HashMap();

	/**
	 * 构造函数
	 */
	public GrpPolVTSParser() {
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
	 * 校验格式 要求磁盘投保文件中的数据按照唯一标识，从小到大排列
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

		int nMaxID = -1;
		int nID = -1;

		int nMaxID2 = -1;
		int nID2 = -1;
		int ID_COL = 0;

		String strID = "";

		// 检查数据是否是按“唯一号”排序的
		for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++) {
			int nMaxRow = getMaxRow(nIndex);

			// nMaxID = -1;
			// nMaxID2 = -1;
			//
			// for (int nRow = 1; nRow < nMaxRow; nRow++)
			// {
			// strID = m_book.getText(nIndex, nRow, 0);
			// nID = Integer.parseInt(strID);
			//
			// if (nID > nMaxID )
			// {
			// nMaxID = nID;
			// }
			// else if (nID == nMaxID)
			// {
			// // do nothing
			// }
			// else
			// {
			// throw new Exception("投保文件中的数据不是按照唯一号从小到大排序的");
			// }
			//
			// //如果是被保人、险种保单 或受益人sheet,需判断两列是否排序
			// if (nIndex == SHEET_INSURED || nIndex == SHEET_LCPOL
			// || nIndex == SHEET_BNF) {
			// //被保人、险种保单、受益人sheet中还需判断排序的列号
			// if (nIndex == SHEET_INSURED) {
			// ID_COL = STRID_COL_INSURED;
			// }
			// if (nIndex == SHEET_LCPOL) {
			// ID_COL = STRID_COL_LCPOL;
			// }
			// if (nIndex == SHEET_BNF) {
			// ID_COL = 1;
			// }
			//
			// strID = m_book.getText(nIndex, nRow, ID_COL);
			// nID2 = Integer.parseInt(strID);
			//
			// if (nID2 > nMaxID2 )
			// {
			// nMaxID2 = nID2;
			// }
			// else if (nID2 == nMaxID2)
			// {
			// // do nothing
			// }
			// else
			// {
			// throw new Exception("投保文件中的数据不是按照唯一号从小到大排序的");
			// }
			// }
			//
			// }
		}

		// 将第一行中元素的值设为对应的XML元素的名字
		// 如在Sheet0中，每行的第一列对应的XML元素名字为ID。
		DOMBuilder db = new DOMBuilder();
		Document doc = db.build(new FileInputStream(m_strConfigFileName));
		Element eleRoot = doc.getRootElement();
		Element ele = null;
		String strColName = "";

		for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++) {
			String sheetId = "Sheet" + String.valueOf(nIndex + 1);
			try {
				ele = eleRoot.getChild(sheetId);
				int nMaxCol = getMaxCol(nIndex);
				String[] strArr = new String[nMaxCol];

				for (int nCol = 0; nCol < nMaxCol; nCol++) {

					strColName = ele.getChildText("COL" + String.valueOf(nCol));
					// if( strColName == null || strColName.equals("") ) {

					// }

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
		genXMLPart(ele, this.SHEET_INSURED);

		// ele = new Element("INSUREDTABLE");
		// root.addContent(ele);
		// genInsuredPart(ele);
		//
		// ele = new Element("LCPOLTABLE");
		// root.addContent(ele);
		// if ( !genOtherPart(ele, this.SHEET_LCPOL,2) )
		// {
		// String err = this.mErrors.getError(1).errorMessage ;
		// this.mErrors.clearErrors();
		// throw new Exception(err);
		// }
		//
		// ele = new Element("INSURED_RELA_TABLE");
		// root.addContent(ele);
		// if ( !genOtherPart(ele, this.SHEET_INS_RELA , 0))
		// {
		// String err = this.mErrors.getError(1).errorMessage ;
		// this.mErrors.clearErrors();
		// throw new Exception(err);
		//
		// }
		//
		// ele = new Element("BNFTABLE");
		// root.addContent(ele);
		// if (!genOtherPart(ele, this.SHEET_BNF , 0))
		// {
		// String err = this.mErrors.getError(1).errorMessage ;
		// this.mErrors.clearErrors();
		// throw new Exception(err);
		//
		// }

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

	private int getMaxCol(int nSheetIndex) throws Exception {
		String str = "";
		int nMaxCol = getPropNumber(nSheetIndex, PROP_MAX_COL);

		if (nMaxCol == -1) {
			for (nMaxCol = 0; nMaxCol < m_book.getMaxRow(); nMaxCol++) {
				str = m_book.getText(nSheetIndex, 0, nMaxCol);
				if (str == null || str.trim().equals("")) {
					break;
				}
			}

			setPropNumber(nSheetIndex, PROP_MAX_COL, nMaxCol);
		}
		return nMaxCol;
	}

	private int getPropNumber(int nSheetIndex, String strPropName) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		String str = (String) hash.get(strPropName);
		if (str != null && !str.equals("")) {
			return Integer.parseInt(str);
		} else {
			return -1;
		}
	}

	private void setPropNumber(int nSheetIndex, String strPropName, int nValue) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		hash.put(strPropName, String.valueOf(nValue));
	}

	private String[] getPropArray(int nSheetIndex, String strPropName) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		String[] strArr = (String[]) hash.get(strPropName);
		return strArr;
	}

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
		// int nMaxCol = getMaxCol(sheetNo);

		// String[] strColName = getPropArray(sheetNo, PROP_COL_NAME);
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
			strID = m_book.getText(sheetNo, nRow, this.STRID_COL_INSURED);
			// nCount控制xml中合同行递增
			// 如果超出最小行限制并且合同ID已变 则跳出生成新文件
			// 目的是保证同一合同的信息保存在同一文件中
			if (++nCount > GrpPolVTSParser.ROW_LIMIT
					&& !strID.equals(strIDCached)) {

				break;
			}
			contIndex = Integer.parseInt(strID);

			// strIDCached= strID ;

			// ///////////////////////////////
			// 如果是同一张保单，后面的信息不用重新生成
			// BTW：现在也没法重新生成，因为现在只做一次扫描
			if (!strID.equals(strIDCached)) {
				strIDCached = strID; // 缓存上一次操作的保单唯一号:合同ID

				Element eleRow = new Element("ROW");
				eleParent.addContent(eleRow);
				Element eleCached = new Element("CACHED");

				// //
				Element eleField = new Element("CONTID");
				eleField.setText(strID);
				eleRow.removeChild("CONTID"); // 每一个保单信息都要重新生成ContId
				eleRow.addContent(eleField);

				// setPropNumber(this.SHEET_INSURED, PROP_CUR_ROW, nRow);

				// 被保人信息
				Element eleInsured = new Element("INSUREDTABLE");
				eleRow.removeChild("INSUREDTABLE");
				eleRow.addContent(eleInsured);
				genPartSubTable(eleInsured, this.SHEET_INSURED, strID,
						this.STRID_COL_INSURED);

				// 保单信息
				Element elePol = new Element("LCPOLTABLE");
				eleRow.removeChild("LCPOLTABLE");
				eleRow.addContent(elePol);
				genPartSubTable(elePol, this.SHEET_LCPOL, strID,
						this.STRID_COL_LCPOL);

				// 连身被保人关联
				// Element elePrem = new Element("INSURED_RELA_TABLE");
				// eleCached.removeChild("INSURED_RELA_TABLE");
				// eleCached.addContent(elePrem);
				// genPartSubTable(elePrem, this.SHEET_INS_RELA, strID,
				// this.STRID_COL_INS_RELA);

				// 受益人信息
				Element eleBnf = new Element("BNFTABLE");
				eleRow.removeChild("BNFTABLE");
				eleRow.addContent(eleBnf);
				genPartSubTable(eleBnf, this.SHEET_BNF, strID,
						this.STRID_COL_BNF);

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
				String tDutyCode = m_book.getText(nSheetIndex, nRow,
						POL_COL_DUTYCODE);
				String tPayPlanCode = m_book.getText(nSheetIndex, nRow,
						POL_COL_PAYPLANCODE);

				if (tDutyCode != null && !"".equals(tDutyCode)
						&& (tPayPlanCode == null || "".equals(tPayPlanCode))) {
					Element eleDuty = new Element("SUBDUTYTABLE");
					eleRow.removeChild("SUBDUTYTABLE");
					eleRow.addContent(eleDuty);
					setPropNumber(nSheetIndex, PROP_CUR_ROW, nRow);
					nRow = genPartDutyTable(eleDuty, this.SHEET_LCPOL, strID,
							this.STRID_COL_LCPOL);

				}

				if (tDutyCode != null && !"".equals(tDutyCode)
						&& tPayPlanCode != null && !"".equals(tPayPlanCode)) {
					Element elePrem = new Element("SUBPREMTABLE");
					eleRow.removeChild("SUBPREMTABLE");
					eleRow.addContent(elePrem);
					setPropNumber(nSheetIndex, PROP_CUR_ROW, nRow);
					nRow = genPartPremTable(elePrem, this.SHEET_LCPOL, strID,
							this.STRID_COL_LCPOL);

				}

				// // 取得唯一号信息:险种ID
				// String polId = m_book.getText(nSheetIndex, nRow,
				// this.STRID_POL);

				// // 如果是同一张保单，后面的信息不用重新生成
				// // BTW：现在也没法重新生成，因为现在只做一次扫描
				// if (!polId.equals(polIdCached))
				// {
				// polIdCached = polId; // 缓存上一次操作的保单唯一号

				// 保费信息
				// Element elePrem = new Element("SUBPREMTABLE");
				// eleRow.removeChild("SUBPREMTABLE");
				// eleRow.addContent(elePrem);
				//
				// genPartSubTable(elePrem, this.SHEET_PREM, polId,
				// this.STRID_COL_PREM);

				// 责任信息
				// Element eleDuty = new Element("SUBDUTYTABLE");
				// eleRow.removeChild("SUBDUTYTABLE");
				// eleRow.addContent(eleDuty);
				//
				// genPartSubTable(eleDuty, this.SHEET_DUTY, polId,
				// this.STRID_COL_DUTY);
			}
			// }

		}
		setPropNumber(nSheetIndex, PROP_CUR_ROW, nRow);
	}

	private int genPartDutyTable(Element eleParent, int nSheetIndex,
			String strID, int strIDColIndex) throws Exception {
		int nCurRow = getPropNumber(nSheetIndex, PROP_CUR_ROW);
		int nMaxRow = getMaxRow(nSheetIndex);
		int nMaxCol = getMaxCol(nSheetIndex);

		String[] strColName = getPropArray(nSheetIndex, PROP_COL_NAME);

		int tRow = 0;

		for (tRow = nCurRow; tRow < nMaxRow; tRow++) {
			// 不是同一合同ID跳出
			if (!strID.equals(m_book.getText(nSheetIndex, tRow, strIDColIndex))) {
				break;
			}

			Element eleRow = new Element("ROW");
			eleParent.addContent(eleRow);
			String tDutyCode = m_book.getText(nSheetIndex, tRow,
					POL_COL_DUTYCODE);
			String tPayPlanCode = m_book.getText(nSheetIndex, tRow,
					POL_COL_PAYPLANCODE);
			if (tDutyCode == null || "".equals(tDutyCode)
					|| (!"".equals(tDutyCode) && !"".equals(tPayPlanCode))) {
				break;
			} else {
				for (int nCol = 0; nCol < nMaxCol; nCol++) {
					Element ele = new Element(strColName[nCol]);
					ele.setText(m_book.getText(nSheetIndex, tRow, nCol));
					eleRow.addContent(ele);
				}
			}
		}
		tRow = tRow - 1;
		return tRow;
	}

	private int genPartPremTable(Element eleParent, int nSheetIndex,
			String strID, int strIDColIndex) throws Exception {
		int nCurRow = getPropNumber(nSheetIndex, PROP_CUR_ROW);
		int nMaxRow = getMaxRow(nSheetIndex);
		int nMaxCol = getMaxCol(nSheetIndex);

		String[] strColName = getPropArray(nSheetIndex, PROP_COL_NAME);

		int tRow = 0;

		for (tRow = nCurRow; tRow < nMaxRow; tRow++) {
			// 不是同一合同ID跳出
			if (!strID.equals(m_book.getText(nSheetIndex, tRow, strIDColIndex))) {
				break;
			}

			Element eleRow = new Element("ROW");
			eleParent.addContent(eleRow);
			String tPayPlanCode = m_book.getText(nSheetIndex, tRow,
					POL_COL_PAYPLANCODE);
			if (tPayPlanCode == null || "".equals(tPayPlanCode)) {
				break;
			} else {
				for (int nCol = 0; nCol < nMaxCol; nCol++) {
					Element ele = new Element(strColName[nCol]);
					ele.setText(m_book.getText(nSheetIndex, tRow, nCol));
					eleRow.addContent(ele);
				}
			}
		}
		tRow = tRow - 1;
		return tRow;
	}

}
