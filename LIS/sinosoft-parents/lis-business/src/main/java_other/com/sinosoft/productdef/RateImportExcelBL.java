

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
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.sinosoft.ibrms.DataParserXML;
import com.sinosoft.productdef.RateInfoPrepare;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LPRiskToRateDB;
import com.sinosoft.lis.pubfun.ExtPubSubmit;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPRiskToRateSchema;
import com.sinosoft.lis.vschema.LPRiskToRateSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RateImportExcelBL implements BusinessService {
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

	private String[] m_strDataFiles = null;

	private String mXmlFileName = "";

	// 数据Xml解析节点描述
	private String mFilePath = "F:/Temp/Import";

	// 临时的Document对象
	private org.w3c.dom.Document m_doc = null;

	private TransferData mTransferData = new TransferData();

	String mPD_TableName = "";

	private String mTableName = "";

	private String mRemark = "";

	private String mRiskCode = "";

	private String mRiskName = "";

	private String mRateType = "";

	private String mExcelSavePath;

	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	private Hashtable mKeyHashtable = new Hashtable();

	public RateImportExcelBL() {
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
			this.mOperate = cOperate;

			if (!getInputData()) {
				return false;
			}

			if (!check()) {
				return false;
			}

			if (!createConfigXML()) {
				return false;
			}

			long l = new Date().getTime();
			// 进行业务处理
			if (!deal(cInputData, cOperate)) {
				return false;
			}
			System.out.println("运行时间：" + (new Date().getTime() - l) / 1000);
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

		String[][] tDTColums = RateInfoPrepare.getDtColumn(mTableName);
		for (int i = 0; i < tDTColums.length; i++) {
			Element colEle = sheet1Node.addElement("COL" + i);
			colEle.setText(tDTColums[i][0]);

			if (!tDTColums[i][0].toUpperCase().equals("RATE")) {
				this.mKeyHashtable.put(tDTColums[i][0], tDTColums[i][0]);
			}
		}
		FileOutputStream out = new FileOutputStream(mConfigFileName);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
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
		String targetFileName = (String) mTransferData
		.getValueByName("targetFileName");
		String tFileName = targetFileName.substring(targetFileName.lastIndexOf("/")+1,targetFileName.lastIndexOf("."));
		if(!mTableName.equals(tFileName)){
			this.mErrors.addOneError("上传文件名不正确");
			return false;
		}
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
		DataParserXML tJSParser = new DataParserXML(1); // 默认Sheet数量为1

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

	/**
	 * 目的：初始化上传文件
	 * 
	 * @return
	 */
	private boolean initImportFile() {
		System.out
				.println("--------------------校验导入文件是否存在-----开始--------------------");

		this.getFilePath();

		// mImportFileName = mFilePath + mImportFileName;

		System.out.println("-----开始导入文件-----" + mImportFileName);

		File tFile = new File(mConfigFileName);
		if (!tFile.exists()) {
			this.mErrors.addOneError("未上传文件到指定路径[" + this.mConfigFileName
					+ "]!!!");
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

		Element root = document.getRootElement();
		for (Iterator sheet = root.elementIterator("SHEET1"); sheet.hasNext();) {

			ArrayList tInsertArray = new ArrayList();
			ArrayList tDeleteArray = new ArrayList();

			int maxRow = 0;
			Element sheetEL = (Element) sheet.next();

			// 校验是否有重复值
			Hashtable tKeyHashtable = new Hashtable();
			for (Iterator row = sheetEL.elementIterator("ROW"); row.hasNext();) {
				maxRow++;
				Element node = (Element) row.next();
				String colNames = "";
				String colValues = "";
				String tDeleteSQL = "";
				// deal one row

				String tKeyValue = "";
				for (Iterator index = node.elementIterator(); index.hasNext();) {
					Element element = (Element) index.next();
					String colName = element.getName();
					String colValue = element.getTextTrim();

					if (colValue==null||"null".equals(colValue)) {
						colValue = "";
					}

					colNames += colName + ",";
					colValues += colValue + ",";
					// 校验模板maxcol-1列是否有重复
					if (this.mKeyHashtable.containsKey(colName)) {
						tKeyValue = tKeyValue +"," + colValue;
					}
				}

				colNames = colNames.substring(0, colNames.length() - 1);
				colValues = colValues.substring(0, colValues.length() - 1);

				// 校验是否有重复值
				if (tKeyHashtable.containsKey(tKeyValue)) {
					int tempRow = (Integer) tKeyHashtable.get(tKeyValue);
					this.mErrors.addOneError("模板第" + (maxRow+1) + "行与第" + (tempRow+1)
							+ "行数据重复!");
					System.out.println(tKeyHashtable.get(maxRow));
					System.out.println(tKeyHashtable.get(tempRow));
					return false;
				}
				tKeyHashtable.put(tKeyValue, maxRow);
				System.out.println("$$$$$$$$$$$$$$$$$$:tKeyValue:maxRow:"
						+ tKeyValue + ":" + maxRow);

				colValues = colValues.replaceAll(",", "','");
				tInsertArray.add("(" + colNames + ") values('" + colValues
						+ "')");
				
				tDeleteArray.add(" where 1=1 " + tDeleteSQL);
			}

			if (this.mOperate.toUpperCase().equals("ADD")) {
				// 如果是增量导入,并且之前的数据是非数据表样式保存的,需要先获取之前的数据.
				String tTableName = mTableName;
				ArrayList tArrayList = RateInfoPrepare
						.getRuleDataArray(tTableName);
				if (tArrayList.size() > 0) {
					for (int i = 0; i < tArrayList.size(); i++) {
						maxRow++;
						String tKeyValue = "";
						String colNames = "";
						String colValues = "";
						String[][] temp = (String[][]) tArrayList.get(i);
						for (int n = 0; n < temp.length; n++) {
							String tColName = temp[n][0];
							String tColValue = temp[n][1];
							if (tColValue==null||"null".equals(tColValue)) {
								tColValue = "";
							}
							colNames += tColName + ",";
							colValues += "'" + tColValue + "',";
							//增量导入与原有数据是否重复
							if (this.mKeyHashtable.containsKey(tColName)) {
								tKeyValue = tKeyValue+"," + tColValue;
							}
						}
						if (tKeyHashtable.containsKey(tKeyValue)) {
							int tempRow = (Integer) tKeyHashtable
									.get(tKeyValue);
							this.mErrors.addOneError("模板第" + (tempRow+1)
									+ "行与原有数据重复!");
							return false;
						}
						tKeyHashtable.put(tKeyValue, maxRow);
						System.out
								.println("$$$$$$$$$$$$$$$$$$:tKeyValue:maxRow:"
										+ tKeyValue + ":" + maxRow);
						colNames = colNames.substring(0, colNames.length() - 1);
						colValues = colValues.substring(0,
								colValues.length() - 1);
						tInsertArray.add("(" + colNames + ") values("
								+ colValues + ")");
					}
				}
			}

			
			mPD_TableName = "PD_" + mTableName;
			// 处理插入和删除语句
			if (RateInfoPrepare.existsTable(mPD_TableName)) {
				this.map.put("delete from " + mPD_TableName, "DELETE");
			} else {
				this.map.put("create table " + mPD_TableName
						+ " as (select * from " + mTableName + " where 1=2)",
						"CREATE");
			}
			for (int i = 0; i < tInsertArray.size(); i++) {
				String insert = "insert into " + mPD_TableName
						+ tInsertArray.get(i);
				this.map.put(insert, "INSERT");
			}
			// }
		}
		dealLPRiskToRate();
		// 提交到数据库操作
		mInputData.clear();
		mInputData.add(map);
		if (!pubSubmit()) {
			this.mErrors.addOneError("提交数据库操作失败");
			File configFile = new File(mConfigFileName);
			configFile.delete();
			tFile.delete();
			return false;
		}

		// 解析完后删除XML文件、配置文件,备份Excel文件
		File configFile = new File(mConfigFileName);
		if (!configFile.delete()) {
			System.out.println("配置文件:" + mConfigFileName + "删除失败!");
		}

		File excelFile = new File(mImportFileName);
		String suffix = "_" + PubFun.getCurrentDate().replace('-', '_') + "_"
				+ PubFun.getCurrentTime().replace(':', '_');

		File bakPath = new File(this.mExcelSavePath + "Bak\\");
		if (!bakPath.exists()) {
			bakPath.mkdir();
		}
		File bakExcelFile = new File(this.mExcelSavePath + "Bak\\"
				+ this.mTableName + suffix + ".csv");
		if (!excelFile.renameTo(bakExcelFile)) {
			this.mErrors.addOneError("备份" + excelFile + "失败");
			return false;
		}

		try {
			if (!tFile.delete()) {
				System.out.println("Xml文件:" + mXmlFileName + "删除失败!");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		System.out
				.println("--------------------解析XML文件并导入到数据库中-----结束--------------------");
		return true;
	}

	private void dealLPRiskToRate() {
		LPRiskToRateSchema tRiskToRateSchema = new LPRiskToRateSchema();
		LPRiskToRateDB tRiskToRateDB = new LPRiskToRateDB();
		tRiskToRateDB.setRateTable(this.mTableName);
		tRiskToRateDB.setRateType(this.mRateType);
		tRiskToRateDB.setRiskCode(this.mRiskCode);
		if (tRiskToRateDB.getInfo()) {
			LPRiskToRateSet toRateSet = tRiskToRateDB.query();
			tRiskToRateSchema = toRateSet.get(1);
		} else {
			tRiskToRateSchema.setRateTable(this.mTableName);
			tRiskToRateSchema.setRateType(this.mRateType);
			tRiskToRateSchema.setRiskCode(this.mRiskCode);
			tRiskToRateSchema.setRiskName(this.mRiskName);
			tRiskToRateSchema.setMakeDate(mCurrentDate);
			tRiskToRateSchema.setMakeTime(mCurrentTime);
		}
		tRiskToRateSchema.setRemark(this.mRemark);
		tRiskToRateSchema.setModifyDate(mCurrentDate);
		tRiskToRateSchema.setModifyTime(mCurrentTime);
		tRiskToRateSchema.setOperator(mGlobalInput.Operator);
		tRiskToRateSchema.setAuditFlag("0");
		this.map.put(tRiskToRateSchema, "DELETE&INSERT");
	}

	/**
	 * 校验生成的XML文件是否存在
	 * 
	 * @return
	 */
	private boolean checkXmlFileName() {
		System.out
				.println("--------------------校验生成的XML文件是否存在-----开始--------------------");

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
		ExtPubSubmit tPubSubmit = new ExtPubSubmit();
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

		// 获得业务数据
		if (mTransferData == null) {
			this.mErrors.addOneError("前台传输业务数据失败!");
			return false;
		}

		this.mTableName = (String) mTransferData.getValueByName("TableName");
		mRemark = (String) mTransferData.getValueByName("Remark");
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		mRiskName = (String) mTransferData.getValueByName("RiskName");
		mRateType = (String) mTransferData.getValueByName("RateType");
		String targetFileName = (String) mTransferData
				.getValueByName("targetFileName");
		System.out.println("targetFileName:" + targetFileName);
		File file = new File(targetFileName);

		if (!file.getParentFile().exists()) {
			if (!file.mkdir()) {
				this.mErrors.addOneError("创建文件夹"
						+ (file.getParentFile()).getPath() + "失败");
				return false;
			}
		}

		mExcelSavePath = (file.getParentFile()).getPath() + File.separator;
		System.out.println("ExcelSavePath:" + mExcelSavePath);

		if (mTableName == null || mTableName.equals("")) {
			this.mErrors.addOneError("前台传入的模板名为空");
			return false;
		}

		if (mExcelSavePath.equals("")) {
			this.mErrors.addOneError("没有找到Excel文件存放路径");
			return false;
		}

		mConfigFileName = mExcelSavePath + mTableName + "Config.xml";
		this.mImportFileName = mExcelSavePath + mTableName + ".csv";

		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String string = "01_sourcecode/ui/temp/RATE_IBE07.csv";
		string = string.substring(string.lastIndexOf("/")+1, string.lastIndexOf("."));
		System.out.println(string);
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		System.out.println("//////////////" + mErrors.getFirstError());
		return mErrors;
	}

	public VData getResult() {

		return this.mResult;
	}
}

