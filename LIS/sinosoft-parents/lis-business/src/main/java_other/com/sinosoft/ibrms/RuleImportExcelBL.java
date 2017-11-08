

/**
 * <p>Title: PDAlgoTempLib</p>
 * <p>Description: 算法模板库</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-17
 */

package com.sinosoft.ibrms;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LRTemplateTDB;
import com.sinosoft.lis.pubfun.ExtPubSubmit;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LRRuleDataSchema;
import com.sinosoft.productdef.PDImportExcelBL;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RuleImportExcelBL implements BusinessService{
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

	private String mTemplateID = "";
	
	private String mNewTableName = "";

	private String mExcelSavePath;

	
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	
	
	private Hashtable mKeyHashtable = new Hashtable();
	
	public RuleImportExcelBL() {
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

			long l=new Date().getTime();
			// 进行业务处理
			if (!deal(cInputData, cOperate)) {
				return false;
			}
			System.out.println("运行时间："+(new Date().getTime()-l)/1000);
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

//			String sql = "select column_name,table_name from user_tab_columns where lower(table_name) "
//					   + " = ( "
//					   + " select lower(tablename) from lrtemplatet where id='"+this.mTemplateID+"') "
//					   + " order by column_id ";
//			ExeSQL exec = new ExeSQL();
//			SSRS ssrs = exec.execSQL(sql);

			//tongmeng 2011-05-09 modify
			String[][] tDTColums = RuleInfoPrepare.getDtColumn(mTemplateID) ;
			for (int i = 0; i < tDTColums.length; i++) {
				Element colEle = sheet1Node.addElement("COL" + i);
				colEle.setText(tDTColums[i][0]);
				
				//记录KeyID
				/*
				 * DTRate;RuleId
					Result;RuleId;UWLevel
				 */
				if(!tDTColums[i][0].toUpperCase().equals("DTRATE")
						&&!tDTColums[i][0].toUpperCase().equals("RULEID")
						&&!tDTColums[i][0].toUpperCase().equals("RESULT")
						&&!tDTColums[i][0].toUpperCase().equals("UWLEVEL"))
				{
					this.mKeyHashtable.put(tDTColums[i][0], tDTColums[i][0]);
				}
			}

			FileOutputStream out = new FileOutputStream(mConfigFileName);
			OutputFormat format = OutputFormat.createPrettyPrint();
			//format.setEncoding("GBK");
			//tongmeng 2011-05-09 modify
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
/////////////
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
		//String newTableName = (String) this.mTransferData.getValueByName("newTableName");
		
		
		LRTemplateTDB tLRTemplateTDB = new LRTemplateTDB();
		tLRTemplateTDB.setId(this.mTemplateID);
		if(!tLRTemplateTDB.getInfo())
		{
			this.mErrors.addOneError("查询规则模板表失败");
			return false;
		}
		
		Element root = document.getRootElement();
		for(Iterator sheet=root.elementIterator("SHEET1"); sheet.hasNext();){
		
			ArrayList tInsertArray = new ArrayList();
			ArrayList tDeleteArray = new ArrayList();
			
			int maxRow = 0;
			Element sheetEL = (Element) sheet.next();
			//String xml = "";
			//xml = "<records>";
			StringBuffer sb=new StringBuffer();
			sb.append("<records>");
			
			//校验是否有重复值
			Hashtable tKeyHashtable = new Hashtable();
			for(Iterator row=sheetEL.elementIterator("ROW"); row.hasNext();){
				maxRow ++;
				Element node = (Element) row.next();
				String colNames = "";
				String colValues = "";
				String tDeleteSQL = "";
				// deal one row
				//int j=0;
				//xml = xml + "<rows>";
				sb.append("<rows>");
				String tKeyValue = "";
				for (Iterator index = node.elementIterator(); index.hasNext();) {
					Element element = (Element) index.next();

					String colName = element.getName();
					String colValue = element.getTextTrim();

					if (colValue.equals("")) {
						colValue = "''";
					}
					
					//tongmeng 2011-06-15 modify
					if(colName.toUpperCase().equals("RULEID"))
					{
						colValue = PubFun1.CreateMaxNo("ibrms" + tLRTemplateTDB.getRuleName(), 4);
					}

					colNames += colName + ",";
					colValues += colValue + ",";
					if(this.mKeyHashtable.containsKey(colName))
					{
						tKeyValue = tKeyValue + colValue;
					
						tDeleteSQL = tDeleteSQL+ " and " + colName + " = '" +  colValue +"'";
					}
					//xml = xml +  "<"+colName+">"+colValue+"</"+colName+">";
					sb.append("<").append(colName).append(">").append(colValue).append("</").append(colName).append(">");
					
				}
				//xml = xml + "</rows>";
				sb.append("</rows>");

				colNames = colNames.substring(0, colNames.length() - 1);
				colValues = colValues.substring(0, colValues.length() - 1);
				
				//校验是否有重复值
				if(tKeyHashtable.containsKey(tKeyValue))
				{
					int tempRow = (Integer) tKeyHashtable.get(tKeyValue);
					this.mErrors.addOneError("第"+maxRow+"行与第"+tempRow+"行数据重复!");
					System.out.println(tKeyHashtable.get(maxRow));
					System.out.println(tKeyHashtable.get(tempRow));
					return false;
				}
				tKeyHashtable.put(tKeyValue, maxRow);
				System.out.println("$$$$$$$$$$$$$$$$$$:tKeyValue:maxRow:"+tKeyValue+":"+maxRow);
				//if(!this.mNewTableName.toLowerCase().equals("ldsysvar")&&!this.mNewTableName.toLowerCase().equals("ruledata"))
				//{
//					String insert = "insert into " + this.mNewTableName + "(" + colNames
//							+ ") values(" + colValues + ")";
//					String delete = "delete from "+mNewTableName+ " where 1=1 " + tDeleteSQL;
//					System.out.println(delete);
//					this.map.put(delete, "DELETE");
//					this.map.put(insert, "INSERT");
				//}
				colValues=colValues.replaceAll(",", "','") ;
				tInsertArray.add("(" + colNames+ ") values('" + colValues + "')");
					//tInsertArray.add("(" + colNames+ ") values(" + colValues + ")");

					tDeleteArray.add(" where 1=1 " + tDeleteSQL);
			}
			
			if(this.mOperate.toUpperCase().equals("ADD"))
			{
				//如果是增量导入,并且之前的数据是非数据表样式保存的,需要先获取之前的数据.
				String TableName = tLRTemplateTDB.getTableName();
				if(!TableName.toLowerCase().equals("ldsysvar"))
				{
					ArrayList tArrayList = RuleInfoPrepare.getRuleDataArray(mTemplateID,TableName);
					if(tArrayList.size()>0)
					{
						//maxRow = maxRow -1;
						
						for(int i=0;i<tArrayList.size();i++)
						{
							maxRow ++;
							String tKeyValue = "";
							String tDeleteSQL = "";
							String colNames = "";
							String colValues = "";
							//xml = xml + "<rows>";
							sb.append("<rows>");
							String[][] temp = (String[][])tArrayList.get(i);
							for(int n=0;n<temp.length;n++)
							{
								String tColName = temp[n][0];
								String tColValue = temp[n][1];
								
								colNames += tColName + ",";
								colValues += "'"+tColValue + "',";
								if(this.mKeyHashtable.containsKey(tColName))
								{
									tKeyValue = tKeyValue + tColValue;
								
									tDeleteSQL = tDeleteSQL+ " and " + tColName + " = '" +  tColValue+"'";
								}
								//xml = xml +  "<"+tColName+">"+tColValue+"</"+tColName+">";
								sb.append("<").append(tColName).append(">").append(tColValue).append("</").append(tColName).append(">");
							}
							
							//
							if(tKeyHashtable.containsKey(tKeyValue))
							{
								int tempRow = (Integer) tKeyHashtable.get(tKeyValue);
								this.mErrors.addOneError("第"+tempRow+"行与原有数据重复!");
								return false;
							}
							tKeyHashtable.put(tKeyValue, maxRow);
							System.out.println("$$$$$$$$$$$$$$$$$$:tKeyValue:maxRow:"+tKeyValue+":"+maxRow);
							colNames = colNames.substring(0, colNames.length() - 1);
							colValues = colValues.substring(0, colValues.length() - 1);
							//if(!this.mNewTableName.toLowerCase().equals("ldsysvar")&&!this.mNewTableName.toLowerCase().equals("ruledata"))
							//{
//								String insert = "insert into " + this.mNewTableName + "(" + colNames
//										+ ") values(" + colValues + ")";
//								String delete = "delete from "+mNewTableName+ " where 1=1 " + tDeleteSQL;
//								System.out.println(delete);
//								this.map.put(delete, "DELETE");
//								this.map.put(insert, "INSERT");
							//}
								tInsertArray.add("(" + colNames+ ") values(" + colValues + ")");
								tDeleteArray.add(" where 1=1 " + tDeleteSQL);
								//xml = xml + "</rows>";
								sb.append("</rows>");
						}
						
							
					}
							
				}
				else
				{
					
				}
			}
			
			//xml = xml + "</records>";
			sb.append("</records>");
			
			//比较下是否需要创建决策表
			System.out.println("maxRow:"+maxRow);
			boolean creatFlag = false;
			if(maxRow>RuleInfoPrepare.rowNumLimit)
			{
				creatFlag = true;
			}
			
			
			TransferData tTransferData = RuleInfoPrepare.getColumnNameAndTypes(mTemplateID);
			if(tTransferData!=null)
			{
				String ColumnNames  = (String)tTransferData.getValueByName("columnnames");
				String ColumnTypes  = (String)tTransferData.getValueByName("columntypes");
				
				if(!creatFlag)
				{
					this.map = new MMap();
					LRRuleDataSchema tLRRuleDataSchema = new LRRuleDataSchema();
					tLRRuleDataSchema.setId(this.mTemplateID);
					
					int Version = 1;
					String sql = "select max(version) from LRTemplateB where Id='"
							+ this.mTemplateID + "'";
					ExeSQL exec = new ExeSQL();
					SSRS result = exec.execSQL(sql);
					System.out.println("SSRS maxRow::" + result.getMaxRow());
					int rowCount = result.getMaxRow();
					if ("null".equalsIgnoreCase(result.GetText(rowCount, 1))) {
						Version = 1;
						System.out.println("SSRS value is null!");
					} else {
						System.out.println("SSRS value is not null!That is:"
								+ result.GetText(rowCount, 1));
						Version = Integer.parseInt(result.GetText(rowCount, 1)) + 1;
					}
					System.out.println("Version::" + Version);
					
					tLRRuleDataSchema.setVersion(Version);
					//new StringReader(xml)
					//String content = new String(xml);
					String content=new String(sb.toString());
					tLRRuleDataSchema.setColumnNames(ColumnNames);
					tLRRuleDataSchema.setColumnTypes(ColumnTypes);
					//InputStream is = new ByteArrayInputStream(content.getBytes());
//					InputStream is;
//					try {
//						is = new ByteArrayInputStream(content.getBytes("UTF-8"));
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						is = null;
//					}
					//tLRRuleDataSchema.setRuleData(is);
					tLRRuleDataSchema.setRuleData(content);
					
					//tongmeng 2011-05-10 add
					//直接记录结果集的SQL
					
					//String tResultSet = RuleInfoPrepare.prepartResultSet(xml, ColumnNames, ColumnTypes);
					String tResultSet = RuleInfoPrepare.prepartResultSet(content, ColumnNames, ColumnTypes);
					tLRRuleDataSchema.setRuleDataSQL(tResultSet);
					tLRRuleDataSchema.setCreator(this.mGlobalInput.Operator);
					tLRRuleDataSchema.setMakeDate(this.mCurrentDate);
					tLRRuleDataSchema.setMakeTime(this.mCurrentTime);
					tLRRuleDataSchema.setModifyDate(this.mCurrentDate);
					tLRRuleDataSchema.setModifyTime(this.mCurrentTime);

					String tDeleteSQL = "delete from lrruledata where id='"+this.mTemplateID+"' ";
					this.map.put(tDeleteSQL,"DELETE");
					//this.map.put(tLRRuleDataSchema,"BLOBINSERT");
					LRRuleDataSchema tLRRuleDataSchema1 = new LRRuleDataSchema();
					tLRRuleDataSchema1.setSchema(tLRRuleDataSchema);
					tLRRuleDataSchema.setRuleDataSQL("");
					//this.map.put(tLRRuleDataSchema,"BLOBINSERT");
					this.map.put(tLRRuleDataSchema,"INSERT");
					this.map.put(tLRRuleDataSchema1,"UPDATE");
					
					
					
					//替换表名
					if(!tLRTemplateTDB.getTableName().toLowerCase().equals("ruledata")&&!tLRTemplateTDB.getTableName().toLowerCase().equals("ldsysvar"))
					{
						String tSQL = tLRTemplateTDB.getSQLStatement();
						String tDropSQL = "drop table "+tLRTemplateTDB.getTableName() + "";
						
						tSQL = StrTool.replaceEx(tSQL, " "+tLRTemplateTDB.getTableName()+" "," $RuleData$ ");  
						tSQL = StrTool.replaceEx(tSQL, ""+tLRTemplateTDB.getTableName()+".","$RuleData$.");  
						tLRTemplateTDB.setSQLStatement(tSQL);
						tLRTemplateTDB.setTableName("RuleData");
						this.map.put(tLRTemplateTDB.getSchema(),"UPDATE");
						this.map.put(tDropSQL,"DROP");
	
					}
				}
				else
				{
					String TableName = tLRTemplateTDB.getTableName();
					if(TableName.toLowerCase().equals("ruledata")||TableName.toLowerCase().equals("ldsysvar"))
					{
						String DTTableSerialNumber = PubFun1.CreateMaxNo("ibrmsDTTNo", 4);
						System.out.println("获取的流水号(DTTableSerialNumber)是："
								+ DTTableSerialNumber);

						TableName = "DTT" + DTTableSerialNumber;
						String tCreatTable_SQL = " create table "+TableName;
						tCreatTable_SQL = tCreatTable_SQL + " ( ";
						String[] tColumns = ColumnNames.split(";");
						String[] tTypes = ColumnTypes.split(";");
						
						//alter table LDMENU
						//  add constraint PK_LDMENU primary key (NODECODE)
						//增加主键的创建  
						String tPK_STR  = "alter table " + TableName 
						                + " add  constraint PK_"+ TableName
						                + " primary key ( ";
						for(int i=0;i<tColumns.length;i++)
						{
							String tType = tTypes[i];
							if(tType.toLowerCase().equals("string"))
							{
								if(tColumns[i].toLowerCase().equals("ruleid"))
								{
									tType = "VARCHAR2(20)";
								}
								else
								{
									tType = "VARCHAR2(1000)";
								}
								
								
							}
							
							if(this.mKeyHashtable.containsKey(tColumns[i]))
							{
								tPK_STR = tPK_STR + tColumns[i];
								tPK_STR = tPK_STR + ",";
							}
							
							tCreatTable_SQL = tCreatTable_SQL + " " + tColumns[i] + " " + tType ;
							if(i!=tColumns.length-1)
							{
								tCreatTable_SQL = tCreatTable_SQL + ",";
								
							}
						}
						
						tCreatTable_SQL = tCreatTable_SQL + " ) ";
						tPK_STR = tPK_STR.substring(0,tPK_STR.lastIndexOf(","));
						tPK_STR = tPK_STR + ")";
						this.map.put(tCreatTable_SQL,"CREATE");
						
						System.out.println("this.mKeyHashtable.keySet().size():"+this.mKeyHashtable.keySet().size());
						if(this.mKeyHashtable.keySet().size()>=1)
						{
							this.map.put(tPK_STR,"UPDATE");
						}
						
						
						String tSQL = tLRTemplateTDB.getSQLStatement();
						
						tSQL = StrTool.replaceEx(tSQL, " $RuleData$ "," "+TableName+" ");  
						tSQL = StrTool.replaceEx(tSQL, "$RuleData$."," "+TableName+".");  
						tLRTemplateTDB.setSQLStatement(tSQL);
						tLRTemplateTDB.setTableName(TableName);
						this.map.put(tLRTemplateTDB.getSchema(),"UPDATE");
						
						String tDeleteSQL = "delete from lrruledata where id='"+this.mTemplateID+"' ";
						this.map.put(tDeleteSQL,"DELETE");
						
						
					}
					//处理插入和删除语句
					
					if(this.mOperate.toUpperCase().equals("ALL"))
					{
						this.map.put("delete from "+TableName , "DELETE");
					}
					for(int i=0;i<tInsertArray.size();i++)
					{
						String insert = "insert into " + TableName + tInsertArray.get(i);
						String delete = "delete from "+TableName + tDeleteArray.get(i);

						this.map.put(delete, "DELETE");
						this.map.put(insert, "INSERT");
					}
					
				}
			}
		}

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

//		 获得业务数据
		if (mTransferData == null) {
			this.mErrors.addOneError("前台传输业务数据失败!");
			return false;
		}

		this.mTemplateID = (String)mTransferData.getValueByName("UploadTempalteID");

		String targetFileName = (String)mTransferData.getValueByName("targetFileName");
		System.out.println("targetFileName:"+targetFileName);
		File file = new File(targetFileName);

		if(!file.getParentFile().exists())
		{
			if(!file.mkdir())
			{
				this.mErrors.addOneError("创建文件夹" + (file.getParentFile()).getPath() + "失败");
				return false;
			}
		}

		mExcelSavePath = (file.getParentFile()).getPath() + File.separator;
		System.out.println("ExcelSavePath:"+mExcelSavePath);

		if(mTemplateID == null || mTemplateID.equals(""))
		{
			this.mErrors.addOneError("前台传入的模板名为空");
			return false;
		}

		if(mExcelSavePath.equals(""))
		{
			this.mErrors.addOneError("没有找到Excel文件存放路径");
			return false;
		}

		String tNewTableName = "";
		//获取生成的决策表名
		String tFileName = (String)mTransferData.getValueByName("FileName");
		String tSQL_tablename = "select tablename from lrtemplatet where id='"+mTemplateID+"'";
		ExeSQL tExeSQL = new ExeSQL();
		tNewTableName = tExeSQL.getOneValue(tSQL_tablename);
		if(tNewTableName==null||tNewTableName.equals(""))
		{
			this.mErrors.addOneError("获取数据表名出错!");
			return false;
		}
		if(tFileName.indexOf(".xls")==-1)
		{
			this.mErrors.addOneError("文件类型错误,请重新选择!");
			return false;
		}
		if(!tFileName.substring(0,tFileName.indexOf(".")).equals(tNewTableName))
		{
			this.mErrors.addOneError("模板错误,请重新下载模板!");
			return false;
		}
		mNewTableName = tNewTableName;
		mConfigFileName = mExcelSavePath + mNewTableName + "Config.xml";
		this.mImportFileName = mExcelSavePath + mNewTableName + ".xls";

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
	
	 public CErrors getErrors() {
			// TODO Auto-generated method stub
			System.out.println("//////////////"+mErrors.getFirstError());
			return mErrors;
		}
	    public VData getResult() {
			
			return this.mResult;
		}
}
