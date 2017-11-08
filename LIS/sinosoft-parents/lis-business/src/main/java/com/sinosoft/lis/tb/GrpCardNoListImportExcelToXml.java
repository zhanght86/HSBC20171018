package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

import com.f1j.ss.BookModelImpl;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

/**
 * <p>
 * ClassName: GrpCardNoListImportExcelToXml
 * </p>
 * <p>
 * Description: 用于无名单卡单导入时将excel文件解析为xml文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author:
 * @version: 1.0
 * @date: 2007-04-01
 */
public class GrpCardNoListImportExcelToXml {
private static Logger logger = Logger.getLogger(GrpCardNoListImportExcelToXml.class);

	public CErrors mErrors = new CErrors();
	// 保存磁盘投保文件的文件名
	private String m_strExcelFileName = "";
	// 保存列名元素名映射文件的文件名
	private String m_strConfigFileName = "";
	// 用来保存生成的xml 文件名
	private String m_strXmlFileName;
	// 保存Sheet相关的信息，如最大行，最大列，当前处理到的行等
	private Hashtable[] m_hashInfo = null;
	// 常量定义
	private static String PROP_MAX_ROW = "max_row";
	private static String PROP_MAX_COL = "max_col";
	private static String PROP_CUR_ROW = "cur_row";
	private static String PROP_COL_NAME = "col_name";
	// sheet的数目
	private static final int SHEET_COUNT = 1;

	// 读取excel的类
	private BookModelImpl m_book = new BookModelImpl();

	public GrpCardNoListImportExcelToXml() {
		this.initInfo();
	}

	/*
	 * @param strFileName String 要被转换的excel文件名 @param strConfigFileName String
	 * xml配置文件名 @param mFile String excel转换后的xml文件名 @param reWrite boolean
	 * 如果已经有文件和想要生成的文件同名，是否覆盖 @throws Exception
	 * 如果不存在指定的excel和xml配置文件或则已经存在希望生成的文件(reWrite 为false),抛出异常
	 */
	public GrpCardNoListImportExcelToXml(String strFileName,
			String strConfigFileName, String mFile, boolean reWrite)
			throws Exception {
		boolean haveProblem = false;

		// 校验需要转换的 源excel文件是否存在
		File file = new File(strFileName);
		if (!file.exists()) {
			haveProblem = true;
			throw new Exception("未找到要转换的excel文件!");
		}
		// 校验转换的模板配置文件是否存在
		file = new File(strConfigFileName);
		if (!file.exists()) {
			haveProblem = true;
			throw new Exception("未找到配置文件!");
		}

		file = new File(mFile);
		if (file.exists() && (!reWrite)) {
			haveProblem = true;
			throw new Exception("要生成的文件已经存在，并且您没选择覆盖!");
		}

		if (!haveProblem) {
			this.m_strExcelFileName = strFileName;
			this.m_strConfigFileName = strConfigFileName;
			this.m_strXmlFileName = mFile;
			this.initInfo();
		}

	}

	/**
	 * 转换操作，将磁盘投保文件转换成指定格式的XML文件。
	 * 
	 * @return boolean
	 */
	public boolean transform() {
		try {
			verify();
			// 生成一个DATASET接点，BATCHID，GRPCONTNO作为其直接子接点
			Element root = new Element("CONTSET");
			Document doc = new Document(root);
			while (hasMoreData(0)) {
				// 每次生成一个LCCont表相关联的一组数据，是sheet0（卡单所在的sheet）的PROP_CUR_ROW属性加一
				Element oneContRow = new Element("ROW");

				// 取保单信息
				Element oneContData = genOneCont();
				oneContRow.addContent(oneContData);
				// //取被保人信息
				// oneContData = genRelatedInformationInOneCont(1,
				// "INSUREDTABLE");
				// oneContRow.addContent(oneContData);
				// // //取险种信息
				// oneContData = genRelatedInformationInOneCont(2,
				// "LCPOLTABLE");
				// oneContRow.addContent(oneContData);
				// // 取受益人信息
				// oneContData = genRelatedInformationInOneCont(3, "BNFTABLE");
				// oneContRow.addContent(oneContData);

				this.nextRow(0);
				root.addContent(oneContRow);
			}
			// 生成xml文件
			XMLOutputter xo = new XMLOutputter("  ", true, "GBK");
			xo.output(doc, new FileOutputStream(m_strXmlFileName));
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("transfrom", ex.getMessage());
			return false;
		}
		return true;
	}

	/** ******获取保单信息sheet数据******************** */
	private Element genOneCont() {
		// 保存卡单信息的element
		Element temp = new Element("CONTTABLE");
		String[] colName = getPropArray(0, PROP_COL_NAME);
		int len = colName.length;
		for (int i = 0; i < len; i++) {
			Element oneContProp = new Element(colName[i]);
			String oneProp = "";
			try {
				oneProp = m_book.getText(0, getPropNumber(0, PROP_CUR_ROW), i);
			} catch (Exception ex) {
				oneProp = "";
			}
			//
			if (!oneProp.equals("")) {
				oneContProp.setText(oneProp);
			}
			temp.addContent(oneContProp);
		}
		return temp;
	}

	/***************************************************************************
	 * //保存在一张卡单下相关的信息,sheetIndex 1-被保人信息,2-险种信息,3-收益人信息
	 * 以CardNo为分界线得到sheet1(第二个sheet)中的几条被保人记录
	 **************************************************************************/
	private Element genRelatedInformationInOneCont(int sheetIndex,
			String codeName) {

		// Element temp=new Element("INSUREDTABLE");
		Element temp = new Element(codeName);
		String[] colName = getPropArray(sheetIndex, PROP_COL_NAME);
		int len = colName.length;

		// 保单的默认CardNo 与其他sheet的默认CardNo不同,以方便判定
		String curCardNoInCont = ".";
		try {
			curCardNoInCont = m_book.getText(0, getPropNumber(0, PROP_CUR_ROW),
					3).trim();
		} catch (Exception ex) {
			curCardNoInCont = ".";
		}

		while (this.hasMoreData(sheetIndex)) {
			String curCardNoInInsured = "";
			try {
				// 除了卡单信息的sheet,别的sheet都默认把CardNo放在sheet的第一列,所以用0做列号
				curCardNoInInsured = m_book.getText(sheetIndex,
						getPropNumber(sheetIndex, PROP_CUR_ROW), 0).trim();
			} catch (Exception ex) {
				curCardNoInInsured = "";
			}
			// 如果得到CradNo和卡单sheet中的CardNo不等，则跳出
			if (!curCardNoInInsured.equals(curCardNoInCont))
				break;
			// 保存一个被保人记录
			Element oneInsuredRow = new Element("ROW");
			for (int i = 0; i < len; i++) {
				Element oneContProp = new Element(colName[i]);
				String oneProp = "";
				try {
					oneProp = m_book.getText(sheetIndex, getPropNumber(
							sheetIndex, PROP_CUR_ROW), i);
				} catch (Exception ex) {
					oneProp = "";
				}

				if (!oneProp.equals("")) {
					oneContProp.setText(oneProp);
				}

				oneInsuredRow.addContent(oneContProp);
			}
			temp.addContent(oneInsuredRow);
			this.nextRow(sheetIndex);
		}
		return temp;
	}

	/*
	 * 是某个sheet的PROP_CUR_ROW 属性往前加一
	 */
	private void nextRow(int sheetIndex) {
		int curRow = getPropNumber(sheetIndex, PROP_CUR_ROW) + 1;
		setPropNumber(sheetIndex, PROP_CUR_ROW, curRow);
	}

	/**
	 * 校验格式 要求磁盘投保文件中的数据按照唯一标识，从小到大排列
	 * 
	 * @throws Exception
	 *             在配置文件中得到每个sheet的字段属性
	 */
	private void verify() throws Exception {
		if (m_strExcelFileName.equals("")) {
			throw new Exception("请先调用setFileName函数设置要处理的文件名。");
		}
		m_book.read(m_strExcelFileName, new com.f1j.ss.ReadParams());
		if (m_book.getNumSheets() != SHEET_COUNT) {
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
			getMaxRow(nIndex);
			String sheetId = "Sheet" + String.valueOf(nIndex + 1);
			try {
				// 得到子接点
				ele = eleRoot.getChild(sheetId);
				int nMaxCol = getMaxCol(nIndex);
				String[] strArr = new String[nMaxCol];
				for (int nCol = 0; nCol < nMaxCol; nCol++) {
					// 得到某接点的Text
					strColName = ele.getChildText("COL" + String.valueOf(nCol));
					strArr[nCol] = strColName;
				}
				// 得到一个sheet的所有字段在xml文件中的命名</p>
				setPropArray(nIndex, PROP_COL_NAME, strArr);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception("找不到对应的配置信息，Sheet"
						+ String.valueOf(nIndex + 1));
			}
		}
	}

	/***************************************************************************
	 * //是否还有数据可读，是以第一个sheet,即卡单信息为是否有信息可读的依据,0做一个sheet的索引，是因为
	 * //m_book.getText(nSheetIndex, nMaxRow, 0);中的nSheetIndex是从0开始的
	 **************************************************************************/
	private boolean hasMoreData(int sheetIndex) {
		int nCurRow = getPropNumber(sheetIndex, PROP_CUR_ROW);
		int nMaxRow = getPropNumber(sheetIndex, PROP_MAX_ROW);
		if (nCurRow >= nMaxRow) {
			return false;
		} else {
			return true;
		}
	}

	/***************************************************************************
	 * 得到sheet+ nSheetIndex的最大行数
	 **************************************************************************/
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

	/*
	 * <p>得到某个sheet的最大列数</p>
	 */
	private int getMaxCol(int nSheetIndex) throws Exception {
		String str = "";
		int nMaxCol = getPropNumber(nSheetIndex, PROP_MAX_COL); // 得到某个sheet中prop_max_col属性
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

	/*
	 * * <p>得到excel中某个sheeet中某个属性所在列的序列,如果在m_hashInfo[nSheetIndex]没有设置，返回-1</p>
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

	/*
	 * 设置excel中某个sheeet中某个属性所在列的序列
	 */
	private void setPropNumber(int nSheetIndex, String strPropName, int nValue) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		hash.put(strPropName, String.valueOf(nValue));
	}

	/*
	 * <p>获取某个sheet中某一个字段所有行的值</p>
	 */
	private String[] getPropArray(int nSheetIndex, String strPropName) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		String[] strArr = (String[]) hash.get(strPropName);
		return strArr;
	}

	/*
	 * <p>直接设置某个sheet中某一个字段所有行的值</p>
	 */
	private void setPropArray(int nSheetIndex, String strPropName,
			String[] strArr) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		hash.put(strPropName, strArr);
	}

	/** ****捕获错误后，将错误信息保存起来****** */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpCardNoListImportExcelToXml";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/** ****初始化每个sheet，建立Hashtable****** */
	private void initInfo() {
		m_hashInfo = new Hashtable[SHEET_COUNT];
		for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++) {
			m_hashInfo[nIndex] = new Hashtable();
			// 从第一行开始解析
			m_hashInfo[nIndex].put(PROP_CUR_ROW, "1");
		}
	}

	/** ***获取导入源文件(Excel文件)所在路径及名称** */
	public String getExcelFileName() {
		return m_strExcelFileName;
	}

	/** ***获取导入配置文件所在路径及名称** */
	public String getConfigFileName() {
		return m_strConfigFileName;
	}

	/** ***获取解析后生成的数据文件（Xml文件）所在路径及名称** */
	public String getXmlFileName() {
		return this.m_strXmlFileName;
	}

	/*
	 * <p>设置要转换的excel的文件名</p>
	 */
	public boolean setExcelFileName(String strFileName) {
		File file = new File(strFileName);
		if (!file.exists()) {
			buildError("setFileName", "指定的要转换的文件不存在！");
			return false;
		}
		return true;
	}

	/*
	 * <p>设置解析模板文件名</p>
	 */
	public boolean setConfigFileName(String strConfigFileName) {
		File file = new File(strConfigFileName);
		if (!file.exists()) {
			buildError("setConfigFileName", "指定的解析模板文件不存在！");
			return false;
		}
		m_strConfigFileName = strConfigFileName;
		return true;
	}

	/** *****设置解析后生成文件名称及路径***** */
	public void setXmlFileName(String mFile) {
		this.m_strXmlFileName = mFile;
	}

	/**
	 * * 测试函数 *
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		try {
			String previousExcelFileName = "D:/lis/ui/upload/GrpCard20070409.xls";
			String configFileName = "D:/lis/ui/upload/GrpCardImport.xml";
			String createdFileName = "D:/lis/ui/upload/GrpCard20070409.xml";
			/*
			 * 调用excel转换到xml的类
			 */
			GrpCardNoListImportExcelToXml parser = new GrpCardNoListImportExcelToXml(
					previousExcelFileName, configFileName, createdFileName,
					true);
			if (parser.transform() == false) {
				logger.debug("***导入解析文件失败：");
			}

			logger.debug("*****************");
		} catch (Exception e) {
			logger.debug("***抛出如下异常：");
			e.printStackTrace();

		}
	}

}
