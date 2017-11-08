

/**
 * <p>Title: PDAlgoTempLib</p>
 * <p>Description: 算法模板库</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-17
 */

package com.sinosoft.productdef;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;

// import javax.swing.text.html.HTMLDocument.Iterator;
// import javax.xml.parsers.DocumentBuilderFactory;
//
// import org.apache.xpath.XPathAPI;
// import org.apache.xpath.objects.XObject;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;

import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import org.dom4j.*;
import org.dom4j.io.*;

public class PDImportExcelBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private MMap map = new MMap();

	private String mImportFileName;

	private String mConfigFileName;

	private String XmlFileName;

	private String[] m_strDataFiles = null;

	private String mXmlFileName = "";

	// 数据Xml解析节点描述
	private String mFilePath = "F:/Temp/Import";

	private static String XMLPAHT = "TranDataPath";

	// 临时的Document对象
	private org.w3c.dom.Document m_doc = null;

	private org.jdom.Document myDocument;

	private TransferData mTransferData = new TransferData();

	private String mNewTableName;

	private String mExcelSavePath;

	public PDImportExcelBL() {
		this.bulidDocument();
	}

	/**
	 * 传输数据的公共方法
	 *
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {

			this.mInputData = cInputData;

			if (!getInputData()) {
				return false;
			}

			if (!check()) {
				return false;
			}

			if (!createConfigXML()) {
				return false;
			}

			// 进行业务处理
			if (!deal(cInputData, cOperate)) {
				return false;
			}
		} catch (Exception ex) {
			this.mErrors.addOneError(ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean createConfigXML() throws Exception {

			File file = new File(mConfigFileName);
			if (file.exists()) {
				File configFile = new File(mConfigFileName);
				if (!configFile.delete()) {
					this.mErrors.addOneError("配置文件删除失败!");
					return false;
				}
			}

			DocumentFactory factory = DocumentFactory.getInstance();

			Document doc = factory.createDocument();
			Element root = doc.addElement("DataConfigdesc");
			root.addAttribute("name", "DataConfigdesc");

			Element sheet1Node = root.addElement("Sheet1");
			sheet1Node.addAttribute("name", "Sheet1");

			String sql = "select column_name from User_Tab_Cols where table_name = upper('"
					+ mNewTableName + "') order by column_id";
			ExeSQL exec = new ExeSQL();
			SSRS ssrs = exec.execSQL(sql);

			for (int i = 0; i < ssrs.getMaxRow(); i++) {
				Element colEle = sheet1Node.addElement("COL" + i);
				colEle.setText(ssrs.GetText(i + 1, 1));
			}

			FileOutputStream out = new FileOutputStream(mConfigFileName);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gb2312");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			writer.flush();

		return true;
	}

	private boolean deal(VData cInputData, String cOperate) throws Exception {

		// 把Excel转化为Xml
		if (!parseVts()) {
			return false;
		}

		for (int i = 0; i < m_strDataFiles.length; i++) {
			// 生成XML文件
			mXmlFileName = m_strDataFiles[i];
			System.out.println("mXmlFileName==" + mXmlFileName);
			if (!ParseXml()) {
				return false;
			}
		}


		return true;
	}

	private boolean check() {
//		mImportFileName = (String) mTransferData.getValueByName("TargetFileName");
//
//		if (mImportFileName == null || mImportFileName.trim().equals("")) {
//			this.mErrors.addOneError("前台传入导入Excel文件名信息丢失!");
//			return false;
//		}

		return true;
	}

	/**
	 * 解析Excel并转换成XML文件
	 *
	 * @return
	 */
	/**
	 * @return
	 * @throws Exception
	 */
	private boolean parseVts() throws Exception {
		System.out
				.println("--------------------解析Excel并转换成XML文件-----开始--------------------");
		PDDataParserXML tJSParser = new PDDataParserXML(1); // 默认Sheet数量为1

		// 初始化上传文件
		if (!initImportFile()) {
			return false;
		}

		// 检查导入配置文件是否存在
		if (!checkImportConfig()) {
			return false;
		}

		// 设置解析文件
		if (!tJSParser.setFileName(mImportFileName)) {
			mErrors.copyAllErrors(tJSParser.mErrors);
			return false;
		}

		// 设置配置文件
		if (!tJSParser.setConfigFileName(mConfigFileName)) {
			mErrors.copyAllErrors(tJSParser.mErrors);
			return false;
		}

		// 转换excel到xml
		if (!tJSParser.transform()) {
			mErrors.copyAllErrors(tJSParser.mErrors);
			return false;
		}

		// 得到生成的XML文件名列表
		m_strDataFiles = tJSParser.getDataFiles();

		System.out
				.println("--------------------解析Excel并转换成XML文件-----结束--------------------");
		return true;
	}

	/*
	 * 目的：初始化上传文件 @return
	 */
	private boolean initImportFile() {
		System.out
				.println("--------------------校验导入文件是否存在-----开始--------------------");

		this.getFilePath();

		// mImportFileName = mFilePath + mImportFileName;

		System.out.println("-----开始导入文件-----" + mImportFileName);

		File tFile = new File(mConfigFileName);
		if (!tFile.exists()) {
			this.mErrors.addOneError("未上传文件到指定路径[" + this.mConfigFileName + "]!!!");
			return false;
		}

		System.out
				.println("--------------------校验导入文件是否存在-----结束--------------------");
		return true;
	}

	/**
	 * 检查导入配置文件是否存在
	 *
	 * @return
	 */
	private boolean checkImportConfig() {
		System.out
				.println("--------------------校验导入配置文件是否存在-----开始--------------------");

		this.getFilePath();

		System.out.println("-----导入路径-----" + mFilePath);

		File tFile1 = new File(mFilePath);
		if (!tFile1.exists()) {
			// 初始化创建目录
			tFile1.mkdirs();
		}

		System.out.println("-----配置文件-----" + mConfigFileName);
		File tFile2 = new File(mConfigFileName);
		if (!tFile2.exists()) {
			this.mErrors.addOneError("请上传配置文件到指定路径[" + mFilePath + "]!!!");
			return false;
		}

		System.out
				.println("--------------------校验导入配置文件是否存在-----结束--------------------");
		return true;
	}

	/**
	 * 得到生成文件路径
	 *
	 * @return
	 */
	private boolean getFilePath() {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("TranDataPath");
		if (!tLDSysVarDB.getInfo()) {
			this.mErrors.addOneError("缺少文件导入路径!");
			return false;
		} else {
			mFilePath = tLDSysVarDB.getSysVarValue();
		}
		return true;
	}

	/**
	 * 解析xml
	 *
	 * @return
	 */
	public boolean ParseXml() throws Exception {
		System.out
				.println("--------------------解析XML文件并导入到数据库中-----开始--------------------");

		// 校验生成的XML文件是否存在
		if (!checkXmlFileName()) {
			return false;
		}
		// 解析XML文件
		File tFile = new File(mXmlFileName);

		SAXReader reader = new SAXReader();

		Document document = reader.read(tFile);
		
		//改用别的方法
		String newTableName = (String) this.mTransferData.getValueByName("newTableName");
		Element root = document.getRootElement();
		for(Iterator sheet=root.elementIterator("SHEET1"); sheet.hasNext();){
			Element sheetEL = (Element) sheet.next();
			for(Iterator row=sheetEL.elementIterator("ROW"); row.hasNext();){
				Element node = (Element) row.next();
				String colNames = "";
				String colValues = "";
				// deal one row
				for (Iterator index = node.elementIterator(); index.hasNext();) {
					Element element = (Element) index.next();

					String colName = element.getName();
					String colValue = element.getTextTrim();

					if (colValue.equals("")) {
						colValue = "''";
					}

					colNames += colName + ",";
					colValues += colValue + ",";
				}

				colNames = colNames.substring(0, colNames.length() - 1);
				colValues = colValues.substring(0, colValues.length() - 1);

				String insert = "insert into " + newTableName + "(" + colNames
						+ ") values(" + colValues + ")";
				this.map.put(insert, "INSERT");
			}
		}

		// document.
//		List list = document.selectNodes("//ROW");//怎么弄都无法使用。。。。
//
//		String newTableName = (String) this.mTransferData
//				.getValueByName("newTableName");
//
//		for (int i = 0; i < list.size(); i++) {
//			Element node = (Element) list.get(i);
//
//			String colNames = "";
//			String colValues = "";
//
//			// deal one row
//			for (Iterator index = node.elementIterator(); index.hasNext();) {
//				Element element = (Element) index.next();
//
//				String colName = element.getName();
//				String colValue = element.getTextTrim();
//
//				if (colValue.equals("")) {
//					colValue = "''";
//				}
//
//				colNames += colName + ",";
//				colValues += colValue + ",";
//			}
//
//			colNames = colNames.substring(0, colNames.length() - 1);
//			colValues = colValues.substring(0, colValues.length() - 1);
//
//			String insert = "insert into " + newTableName + "(" + colNames
//					+ ") values(" + colValues + ")";
//			this.map.put(insert, "INSERT");
//		}

		// 提交到数据库操作
		mInputData.clear();
		mInputData.add(map);
		if (!pubSubmit()) {
			this.mErrors.addOneError("提交数据库操作失败");
			return false;
		}

		// 解析完后删除XML文件、配置文件,备份Excel文件
		File configFile = new File(mConfigFileName);
		if (!configFile.delete()) {
			System.out.println("配置文件:" + mConfigFileName + "删除失败!");
		}

		File excelFile = new File(mImportFileName);
		String suffix = "_" + PubFun.getCurrentDate().replace('-', '_') + "_" + PubFun.getCurrentTime().replace(':', '_');

		File bakPath = new File(this.mExcelSavePath + "Bak\\" );
		if(!bakPath.exists())
		{
			bakPath.mkdir();
		}
		File bakExcelFile = new File(this.mExcelSavePath + "Bak\\" + this.mNewTableName + suffix + ".xls");
		if(!excelFile.renameTo(bakExcelFile))
		{
			this.mErrors.addOneError("备份" + excelFile +"失败");
			return false;
		}

		try
		{
			if (!tFile.delete()) {
				System.out.println("Xml文件:" + mXmlFileName + "删除失败!");
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}

		System.out
				.println("--------------------解析XML文件并导入到数据库中-----结束--------------------");
		return true;
	}

	/**
	 * 校验生成的XML文件是否存在
	 *
	 * @return
	 */
	private boolean checkXmlFileName() {
		System.out
				.println("--------------------校验生成的XML文件是否存在-----开始--------------------");

		// this.getFilePath();
		//
		// System.out.println("-----存放路径-----"+mFilePath);
		//
		// File tFile1 = new File(mFilePath);
		// if (!tFile1.exists())
		// {
		// //初始化创建目录
		// tFile1.mkdirs();
		// }
		//
		// mXmlFileName = mFilePath + mXmlFileName;
		// System.out.println("-----生成的XML文件-----"+mXmlFileName);
		File tFile2 = new File(mXmlFileName);
		if (!tFile2.exists()) {
			this.mErrors.addOneError("请解析XML文件到指定路径[" + mFilePath + "]!!!");
			return false;
		}

		System.out
				.println("--------------------校验生成的XML文件是否存在-----结束--------------------");
		return true;
	}

	/**
	 * Detach node from original document, fast XPathAPI process.
	 *
	 * @param node
	 * @return
	 * @throws Exception
	 */
	private org.w3c.dom.Node transformNode(org.w3c.dom.Node node)
			throws Exception {
		org.w3c.dom.Node nodeNew = m_doc.importNode(node, true);

		return nodeNew;
	}

	private void bulidDocument() {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		dfactory.setNamespaceAware(true);

		try {
			m_doc = dfactory.newDocumentBuilder().newDocument();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 提交数据，进行数据库操作
	 *
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			this.mErrors.addOneError("数据提交失败!");
			return false;
		}
		return true;

	}

	/**
	 * 得到传入数据
	 */
	private boolean getInputData() throws Exception {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			this.mErrors.addOneError("无操作员信息，请重新登录!");
			return false;
		}

//		 获得业务数据
		if (mTransferData == null) {
			this.mErrors.addOneError("前台传输业务数据失败!");
			return false;
		}

		this.mNewTableName = (String)mTransferData.getValueByName("newTableName");

		String targetFileName = (String)mTransferData.getValueByName("targetFileName");
		File file = new File(targetFileName);

		if(!file.getParentFile().exists())
		{
			if(!file.mkdir())
			{
				this.mErrors.addOneError("创建文件夹" + (file.getParentFile()).getPath() + "失败");
				return false;
			}
		}

		mExcelSavePath = (file.getParentFile()).getPath();

		if(mNewTableName == null || mNewTableName.equals(""))
		{
			this.mErrors.addOneError("前台传入的表名为空");
			return false;
		}

		if(mExcelSavePath.equals(""))
		{
			this.mErrors.addOneError("没有找到Excel文件存放路径");
			return false;
		}
		mConfigFileName = mExcelSavePath.replaceAll("\\//", "/")+"/"+ mNewTableName + "Config.xml";

		this.mImportFileName = mExcelSavePath.replaceAll("\\//", "/") +"/"+ mNewTableName + ".xls";

		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			File excelFile = new File("F:\\temp\\targetFiles\\CV_4061.xls");
			String suffix = PubFun.getCurrentDate().replace('-', '_') + PubFun.getCurrentTime().replace(':', '_');
			File bakExcelFile = new File("F:\\temp\\targetFiles\\Bak\\"+suffix+"CV_4061.xls");
			boolean b = excelFile.renameTo(bakExcelFile);

			GlobalInput tG = new GlobalInput();
			tG.Operator = "001";

			TransferData tData = new TransferData();
			tData.setNameAndValue("FileName", "F:\\Temp\\RATE_65_650001.xls");
			tData.setNameAndValue("ConfigFileName",
					"F:\\Temp\\ExcelImportLFJSConfig.xml");

			VData vData = new VData();
			vData.add(tG);
			vData.add(tData);
			//
			PDImportExcelBL tPDImportExcelBL = new PDImportExcelBL();

			// tPDImportExcelBL.ParseXml();
			tPDImportExcelBL.submitData(vData, "insert");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}

