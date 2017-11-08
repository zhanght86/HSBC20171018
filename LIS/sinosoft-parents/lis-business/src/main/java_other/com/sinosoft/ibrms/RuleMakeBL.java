package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.sinosoft.lis.db.PD_LMCalModeDB;
import com.sinosoft.lis.pubfun.ExtPubSubmit;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LRRuleDataBSchema;
import com.sinosoft.lis.schema.LRRuleDataSchema;
import com.sinosoft.lis.schema.LRTemplateTSchema;
import com.sinosoft.lis.schema.PD_LMCalModeSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RuleMakeBL  {
private static Logger logger = Logger.getLogger(RuleMakeBL.class);

	public CErrors mErrors = new CErrors();
	private VData mResult=new VData();
	private TransferData returnTransferData  = new TransferData();

	// 日期时间
	private String sCurrentDate = PubFun.getCurrentDate();
	private String sCurrentTime = PubFun.getCurrentTime();

	private TransferData tTransferData = new TransferData();
	private GlobalInput tGlobalInput = new GlobalInput();

	//tongmeng 2011-02-16 add
	private boolean mDropTableFlag = true;
	
	//tongmeng 2011-05-06 add
	//false 采用老方案
	//true 采用新方案
	private boolean mConvertFlag = false;
	private boolean mCalFlag = false;
	private String mRuleTableName = "";
	public RuleMakeBL() {

	}

	public boolean submitData(VData tVData) {
		if (tVData == null) {
			logger.debug("\t@> RuleMakeBL.submitData() :传入的数据为空！");
			return false;
		}

		if (!getData(tVData))
			return false;
		if (!checkData())
			return false;

		if (!prepareData())
			return false;

		if (!dealData())

			return false;

		relateToRisk();
			return true;
	}

	private void relateToRisk() {
		String riskcode=(String)tTransferData.getValueByName("RiskCode");
		String RuleName = (String) tTransferData.getValueByName("RuleName");
		if(!"000000".equals(riskcode)){
			PD_LMCalModeDB tPD_LMCalModeDB=new PD_LMCalModeDB();
			tPD_LMCalModeDB.setCalCode(RuleName);
			if(!tPD_LMCalModeDB.getInfo()){
				PD_LMCalModeSchema tPD_LMCalModeSchema=new PD_LMCalModeSchema();
				tPD_LMCalModeSchema.setCalCode(RuleName);
				tPD_LMCalModeSchema.setType("RC");
				tPD_LMCalModeSchema.setRiskCode(riskcode);
				tPD_LMCalModeSchema.setCalSQL(RuleName.substring(2));
				tPD_LMCalModeSchema.setRemark("");
				tPD_LMCalModeSchema.setOperator(tGlobalInput.Operator);
				tPD_LMCalModeSchema.setMakeDate(sCurrentDate);
				tPD_LMCalModeSchema.setMakeTime(sCurrentTime);
				tPD_LMCalModeSchema.setModifyDate(sCurrentDate);
				tPD_LMCalModeSchema.setModifyTime(sCurrentTime);	
				tPD_LMCalModeDB.setSchema(tPD_LMCalModeSchema);
				tPD_LMCalModeDB.insert();		
			}
		}
		}
	
	private boolean getData(VData tVData) {
		this.tTransferData = (TransferData) tVData.getObjectByObjectName(
				"TransferData", 0);
		this.tGlobalInput = (GlobalInput) tVData.getObjectByObjectName(
				"GlobalInput", 0);

		if (tTransferData == null || tGlobalInput == null) {
			logger.debug("\t@> RuleMakeBL.getData() :参数传递错误！");
			return false;
		}

		return true;
	}

	private boolean checkData() {
		if (tGlobalInput.Operator == null
				|| tGlobalInput.Operator.equalsIgnoreCase("")) {
			logger.debug("\t@> RuleMakeBL.checkData() :操作员信息记录错误！");
			return false;
		}

		String BOMS = (String) tTransferData.getValueByName("BOMS");
		String SQLPara = (String) tTransferData.getValueByName("SQLPara");
		String ViewPara = (String) tTransferData.getValueByName("ViewPara");
		String SQLStatement = (String) tTransferData
				.getValueByName("SQLStatement");
		String RuleCh = (String) tTransferData.getValueByName("RuleCh");

		String RuleName = (String) tTransferData.getValueByName("RuleName");
		String RuleDes = (String) tTransferData.getValueByName("RuleDes");
		String Creator = (String) tTransferData.getValueByName("Creator");
		String RuleStartDate = (String) tTransferData
				.getValueByName("RuleStartDate");
		String RuleEndDate = (String) tTransferData
				.getValueByName("RuleEndDate");
		String TempalteLevel = (String) tTransferData
				.getValueByName("TempalteLevel");
		String Business = (String) tTransferData.getValueByName("Business");
		String State = (String) tTransferData.getValueByName("State");
		String Valid = (String) tTransferData.getValueByName("Valid");

		String Operation = (String) tTransferData.getValueByName("Operation");
		String flag = (String) tTransferData.getValueByName("flag");
		String TableName = (String) tTransferData.getValueByName("TableName");
		String LRTemplate_ID = (String) tTransferData
				.getValueByName("LRTemplate_ID");

		boolean dataTransmissionOK = true;

		if (flag.equalsIgnoreCase("4")) {
			if (TableName == null || TableName.equalsIgnoreCase("")) {
				logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，DT表名传递错误！");
				dataTransmissionOK = false;
			}
			if (LRTemplate_ID == null || LRTemplate_ID.equalsIgnoreCase("")) {
				logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，LRTemplate_ID表名传递错误！");
				dataTransmissionOK = false;
			}
		}

//		if (BOMS == null || BOMS.equalsIgnoreCase("")) {
//			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，BOMS传递错误！");
//			dataTransmissionOK = false;
//		}
//		if (SQLPara == null || SQLPara.equalsIgnoreCase("")) {
//			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，SQLPara传递错误！");
//			dataTransmissionOK = false;
//		}
		if (ViewPara == null || ViewPara.equalsIgnoreCase("")) {
			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，ViewPara传递错误！");
			dataTransmissionOK = false;
		}
		if (SQLStatement == null || SQLStatement.equalsIgnoreCase("")) {
			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，SQLStatement传递错误！");
			dataTransmissionOK = false;
		}
		if (RuleCh == null || RuleCh.equalsIgnoreCase("")) {
			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，RuleCh传递错误！");
			dataTransmissionOK = false;
		}
		if (Creator == null || Creator.equalsIgnoreCase("")) {
			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，Creator传递错误！");
			dataTransmissionOK = false;
		}
		if (RuleStartDate == null || RuleStartDate.equalsIgnoreCase("")) {
			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，RuleStartDate传递错误！");
			dataTransmissionOK = false;
		}
		if (TempalteLevel == null || TempalteLevel.equalsIgnoreCase("")) {
			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，TempalteLevel传递错误！");
			dataTransmissionOK = false;
		}

		if (Business == null || Business.equalsIgnoreCase("")) {
			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，Business传递错误！");
			dataTransmissionOK = false;
		}
		if (State == null || State.equalsIgnoreCase("")) {
			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，State传递错误！");
			dataTransmissionOK = false;
		}
		if (Valid == null || Valid.equalsIgnoreCase("")) {
			logger.debug("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，Valid传递错误！");
			dataTransmissionOK = false;
		}
		if (!dataTransmissionOK)
			return false;

		return true;
	}
	
	//tongmeng 2011-05-05 modify
	//

	boolean prepareData() {

		String Operation = (String) tTransferData.getValueByName("Operation");
		String flag = (String) tTransferData.getValueByName("flag");
		String TableName = (String) tTransferData.getValueByName("TableName");
		String State = (String) tTransferData.getValueByName("State");
		String Creator = (String) tTransferData.getValueByName("Creator");

		String LRTemplate_ID = (String) tTransferData
				.getValueByName("LRTemplate_ID");

		String SQLStatement = (String) tTransferData
				.getValueByName("SQLStatement");
		String CreateTable = (String) tTransferData
				.getValueByName("CreateTable");
		String DTData = (String) tTransferData.getValueByName("DTData");
		logger.debug("准备数据总的DTData是::" + DTData);
		Map DTColumnTypeMap = (Map) tTransferData
				.getValueByName("DTColumnTypeMap");
		String Modifier = null;
		String tOldTableName = TableName;
		
		this.mRuleTableName = (String)tTransferData.getValueByName("RuleTableName");
		
		if (flag.equalsIgnoreCase("4")) {
			Modifier = Creator;

			String sql = "select Creator from lrtemplatet where id='?LRTemplate_ID?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("LRTemplate_ID", LRTemplate_ID);
			ExeSQL exec = new ExeSQL();
			SSRS result = exec.execSQL(sqlbv);
			if (result.GetText(1, 1) != null) {
				Creator = result.GetText(1, 1);
			} else {
				logger.debug("Creator获取错误！");
			}
			
			if(TableName.toLowerCase().equals("ldsysvar")||TableName.toLowerCase().equals("ruledata"))
			{
				String DTTableSerialNumber = PubFun1.CreateMaxNo("ibrmsDTTNo", 4);
				logger.debug("获取的流水号(DTTableSerialNumber)是："
						+ DTTableSerialNumber);

				TableName = "DTT" + DTTableSerialNumber;
			}

		} else {

			String DTTableSerialNumber = PubFun1.CreateMaxNo("ibrmsDTTNo", 4);
			logger.debug("获取的流水号(DTTableSerialNumber)是："
					+ DTTableSerialNumber);
			LRTemplate_ID = PubFun1.CreateMaxNo("ibrmsTemplateID", 20);
			logger.debug("获取的流水号(LRTemplateTSerialNumber)是："
					+ LRTemplate_ID);

			TableName = "DTT" + DTTableSerialNumber;

		}
		logger.debug("TableName是：" + TableName);
		SQLStatement = SQLStatement.replaceAll("#DTTable#", TableName);
		CreateTable = CreateTable.replaceAll("#DTTable#", TableName);

		//tongmeng 2010-12-15 modify
		//增加对计算型规整引擎的支持
		String CalSQLStatement = tTransferData.getValueByName("CalSQLStatement")==null?"":(String)tTransferData.getValueByName("CalSQLStatement");
		if(CalSQLStatement!=null&&!CalSQLStatement.equals(""))
		{
			//替换算法
			this.mCalFlag = true;
			logger.debug("CalSQLStatement："+CalSQLStatement);
			SQLStatement = SQLStatement.replaceAll("#CalSQLStatement#", CalSQLStatement);
			//替换决策表的比例
			//SQLStatement = SQLStatement.replaceAll("BOMDTTable.DTRate", TableName+".DTRate");
			//if(TableName.indexOf("DTT")==-1)
		    {
				SQLStatement = StrTool.replaceEx(SQLStatement, "?BOMDTTable.DTRate?", TableName+".DTRate");
		    }
		    
		    if(this.mRuleTableName!=null&&!this.mRuleTableName.equals(""))
		    {
		    	SQLStatement = StrTool.replaceEx(SQLStatement, ",RuleID", ",1");
		    	String BOMS = (String) tTransferData.getValueByName("BOMS");
		    	if(BOMS==null||BOMS.equals(""))
		    	{
		    		tTransferData.removeByName("BOMS");
		    		tTransferData.setNameAndValue("BOMS", "NULL");
		    	}
				String SQLPara = (String) tTransferData.getValueByName("SQLPara");
				if(SQLPara==null||SQLPara.equals(""))
				{
					tTransferData.removeByName("SQLPara");
		    		tTransferData.setNameAndValue("SQLPara", "NULL.NULL");
				}
		    }
		    
			logger.debug("final SQLStatement："+SQLStatement);
			
		}
		
		
		int Version = 1;
		String sql = "select (case when max(version) is not null then max(version) else 1 end) from LRTemplateB where Id='?LRTemplate_ID?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("LRTemplate_ID", LRTemplate_ID);
		ExeSQL exec = new ExeSQL();
		SSRS result = exec.execSQL(sqlbv1);
		logger.debug("SSRS maxRow::" + result.getMaxRow());
		int rowCount = result.getMaxRow();
		if ("null".equalsIgnoreCase(result.GetText(rowCount, 1))) {
			Version = 1;
			logger.debug("SSRS value is null!");
		} else {
			logger.debug("SSRS value is not null!That is:"
					+ result.GetText(rowCount, 1));
			Version = Integer.parseInt(result.GetText(rowCount, 1)) + 1;
		}
		logger.debug("Version::" + Version);
		
		
		//String tOldSQL,String DTData,Map DTColumnTypeMap
		
		//tongmeng 2011-05-05 modify
		//规则引擎生成决策表替代方案修改
		//准备数据
		
		
		//ArrayList SQLArray = prepareInsert(DTData, TableName, DTColumnTypeMap);

		TransferData preTransferData = new TransferData();
		preTransferData.setNameAndValue("OldSQL", SQLStatement);
		preTransferData.setNameAndValue("DTData", DTData);
		preTransferData.setNameAndValue("TableName", TableName);
		preTransferData.setNameAndValue("LRTemplate_ID", LRTemplate_ID);
		preTransferData.setNameAndValue("Version", Integer.toString(Version));
		
		preTransferData.setNameAndValue("DTColumnTypeMap", DTColumnTypeMap);
		
		TransferData resTransferData = new TransferData();
		//调用RuleInfoPrepare 获取转换后的返回值
		resTransferData = RuleInfoPrepare.prepareSQLAndData(mCalFlag, preTransferData);
		
		SQLStatement = (String)resTransferData.getValueByName("FinalSQL");
		TableName = (String)resTransferData.getValueByName("TableName");
		
		ArrayList SQLArray = (ArrayList)resTransferData.getValueByName("SQLArray");
		
		//获取LRRuleDataSchema
		LRRuleDataSchema tLRRuleDataSchema = new LRRuleDataSchema();
		tLRRuleDataSchema = (LRRuleDataSchema)resTransferData.getValueByName("LRRuleDataSchema");
		
		
		String tConvertFlag = (String)resTransferData.getValueByName("ConvertFlag");
		if(tConvertFlag.equals("0"))
		{
			this.mConvertFlag = false;
		}
		else if(tConvertFlag.equals("1"))
		{
			this.mConvertFlag = true;
		}
		
		String tDeleteLRRuleDataSQL = "";
		tDeleteLRRuleDataSQL = "delete from LRRuleData where id='?LRTemplate_ID?'";
		String tDeleteLRRuleDataSQL2 = "delete from LRRuleData where id='"+LRTemplate_ID+"'";

		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tDeleteLRRuleDataSQL);
		sqlbv2.put("LRTemplate_ID", LRTemplate_ID);
		
		tTransferData.setNameAndValue("DeleteLRRuleDataSQL", tDeleteLRRuleDataSQL2);
		
		String tDeleteLRRuleDataBSQL = "";
		tDeleteLRRuleDataBSQL = "delete from LRRuleDatab where id='?LRTemplate_ID?'";
		String tDeleteLRRuleDataBSQL2 = "delete from LRRuleDatab where id='"+LRTemplate_ID+"'";

		sqlbv2.sql(tDeleteLRRuleDataBSQL);
		
		tTransferData.setNameAndValue("DeleteLRRuleDataBSQL", tDeleteLRRuleDataBSQL2);
		
		if(mConvertFlag)
		{
			CreateTable = "";
			
			tLRRuleDataSchema.setCreator(Creator);
			tLRRuleDataSchema.setMakeDate(sCurrentDate);
			tLRRuleDataSchema.setMakeTime(sCurrentTime);
			tLRRuleDataSchema.setModifyDate(sCurrentDate);
			tLRRuleDataSchema.setModifyTime(sCurrentTime);
			
			tTransferData.setNameAndValue("LRRuleDataSchema", tLRRuleDataSchema);
			
			
		}
		
		if(this.mRuleTableName!=null&&!this.mRuleTableName.equals(""))
		{
			TableName = this.mRuleTableName;
		}
		//tongmeng 2010-12-29 modify
		//如果决策表中没有数据,转换成 查ldsysvar  ldsysvar  where sysvalue='onerow'
		if(SQLArray.size()<=0)
		{
			//where 1=1
			//0001
			//替换算法
			if(this.mRuleTableName==null||this.mRuleTableName.equals(""))
			{
				SQLStatement = SQLStatement.replaceAll("RuleID", " '0001' ");
				SQLStatement = SQLStatement.replaceAll("where 1=1", " ");
//				if(TableName.toUpperCase().equals("RULEDATA"))
//				{
//					SQLStatement = SQLStatement.replaceAll("$"+TableName+"$", " ldsysvar  where sysvar='onerow' ");
//				}
//				else
				{
					SQLStatement = SQLStatement.replaceAll(TableName, " ldsysvar  where sysvar='onerow' ");
				}
			
				
				TableName = "ldsysvar";
			}
			CreateTable = "";
		}
		
		String tNewTableName = TableName;
		if((tOldTableName.toLowerCase().trim().equals("ldsysvar")
				||tOldTableName.toLowerCase().trim().equals("ruledata"))
				&&!tOldTableName.equals(tNewTableName))
		{
			this.mDropTableFlag = false;
		}
		logger.debug(SQLArray.size()+":插入数据准备结束：" + SQLArray.toString());
		tTransferData.removeByName("TableName");
		tTransferData.setNameAndValue("TableName", TableName);
		logger.debug("保存的TableName是：" + TableName);
		tTransferData.removeByName("SQLStatement");
		tTransferData.setNameAndValue("SQLStatement", SQLStatement);

		tTransferData.removeByName("SQLArray");
		tTransferData.setNameAndValue("SQLArray", SQLArray);
		tTransferData.removeByName("CreateTable");
		tTransferData.setNameAndValue("CreateTable", CreateTable);

		State = "1";
		tTransferData.removeByName("State");
		tTransferData.setNameAndValue("State", State);
		logger.debug("State::" + State);

		tTransferData.removeByName("LRTemplate_ID");
		tTransferData.setNameAndValue("LRTemplate_ID", LRTemplate_ID);

		tTransferData.removeByName("Creator");
		tTransferData.setNameAndValue("Creator", Creator);

		tTransferData.setNameAndValue("Modifier", Modifier);

		tTransferData.setNameAndValue("MakeDate", sCurrentDate);
		tTransferData.setNameAndValue("MakeTime", sCurrentTime);
		tTransferData.setNameAndValue("ModifyDate", sCurrentDate);
		tTransferData.setNameAndValue("ModifyTime", sCurrentTime);
		tTransferData.setNameAndValue("Version", Integer.toString(Version));
		
		

		return true;
	}

	private ArrayList prepareInsert(String DTData, String TableName,
			Map DTColumnTypeMap) {
		// PubFun1 pf = new PubFun1();
		ArrayList SQLArray = new ArrayList();

		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(DTData);
			String xml = XML.toString(jsonObj, "records");
			logger.debug("xml:= " + xml);

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new StringReader(xml));
			Element elem = doc.getRootElement();
			List nodes = elem.getChildren();

			for (int i = 0; i < nodes.size(); i++) {
				String RuleId = PubFun1.CreateMaxNo("ibrms" + TableName, 4);

				StringBuffer columnBuffer = new StringBuffer(256);
				StringBuffer valuesBuffer = new StringBuffer(256);

				Element node = (Element) nodes.get(i);
				List childNodes = node.getChildren();
				try {
					for (int j = 0; j < childNodes.size(); j++) {
						Element childNode = (Element) childNodes.get(j);
						columnBuffer.append(childNode.getName() + ",");

						String dataType = (String) DTColumnTypeMap.get(childNode
								.getName());
						if (dataType.equalsIgnoreCase("String")) {
							//logger.debug("childNode:"+childNode.getText());
							//tongmeng 2011-02-22 modify
							//只有算法定义时才做如下处理
							if(mCalFlag&&(childNode.getText()==null||childNode.getText().equals("")))
							{
								continue;
							}
							valuesBuffer.append("'" + childNode.getText() + "',");
						} else if (dataType.equalsIgnoreCase("Number")) {
							
							valuesBuffer.append(Double.parseDouble(childNode
									.getText())
									+ ",");
							
						} else if (dataType.equalsIgnoreCase("Date")) {
							
							valuesBuffer.append("to_date('" + childNode.getText()
									+ "','yyyy-mm-dd hh24:mi:ss'),");
							
						}
						else if (dataType.equalsIgnoreCase("INT")) {
							
							valuesBuffer.append(Integer.parseInt(childNode
									.getText())
									+ ",");
						}
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					continue;
				}
				if(valuesBuffer!=null&&!valuesBuffer.toString().equals(""))
				{
					columnBuffer.append("RuleId" + ")");
					valuesBuffer.append("'" + RuleId + "')");

					StringBuffer sqlBuf = new StringBuffer("insert into "
							+ TableName + " (" + columnBuffer.toString()
							+ " values( " + valuesBuffer.toString());
					logger.debug("INSERT 语句::" + sqlBuf.toString());

					SQLArray.add(sqlBuf.toString());
				}
			}

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();

		}
		return SQLArray;
	}

	private boolean dealData() {
		// 获取要处理的数据
		String LRTemplate_ID = (String) tTransferData
				.getValueByName("LRTemplate_ID");

		String BOMS = (String) tTransferData.getValueByName("BOMS");

		String SQLPara = (String) tTransferData.getValueByName("SQLPara");
		String ViewPara = (String) tTransferData.getValueByName("ViewPara");
		String SQLStatement = (String) tTransferData
				.getValueByName("SQLStatement");
		String RuleCh = (String) tTransferData.getValueByName("RuleCh");
		String RuleName = (String) tTransferData.getValueByName("RuleName");
		String RuleDes = (String) tTransferData.getValueByName("RuleDes");
		String TableName = (String) tTransferData.getValueByName("TableName");
		logger.debug("获取到的TableName是：" + TableName);
		String Creator = (String) tTransferData.getValueByName("Creator");
		String RuleStartDate = (String) tTransferData
				.getValueByName("RuleStartDate");
		String RuleEndDate = (String) tTransferData
				.getValueByName("RuleEndDate");
		String TempalteLevel = (String) tTransferData
				.getValueByName("TempalteLevel");
		String Business = (String) tTransferData.getValueByName("Business");
		String State = (String) tTransferData.getValueByName("State");
		String Valid = (String) tTransferData.getValueByName("Valid");
		String MakeDate = (String) tTransferData.getValueByName("MakeDate");
		String MakeTime = (String) tTransferData.getValueByName("MakeTime");
		String ModifyDate = (String) tTransferData.getValueByName("ModifyDate");
		String ModifyTime = (String) tTransferData.getValueByName("ModifyTime");
		String Version = (String) tTransferData.getValueByName("Version");

		String Modifier = (String) tTransferData.getValueByName("Modifier");

		/*
		 * SQLStatement = SQLStatement.replaceAll("#DTTable#", "DTT" +
		 * maxIndex);
		 */
		String CalRuleCh = (String) tTransferData.getValueByName("CalRuleCh");
		// 准备要处理的数据
		LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
		tLRTemplateTSchema.setId(LRTemplate_ID);
		logger.debug("LRTemplate_ID:" + LRTemplate_ID);
		tLRTemplateTSchema.setVersion(Version);
		logger.debug("setVersion:" + Version);
		tLRTemplateTSchema.setSQLStatement(SQLStatement);
		logger.debug("setSQLStatement:" + SQLStatement);
		tLRTemplateTSchema.setRuleCh(RuleCh);
		logger.debug("RuleCh:" + RuleCh);
		tLRTemplateTSchema.setBOMs(BOMS);
		logger.debug("setBOMs:" + BOMS);
		tLRTemplateTSchema.setSQLParameter(SQLPara);
		logger.debug("setSQLParameter:" + SQLPara);
		tLRTemplateTSchema.setRuleCalCh(CalRuleCh);
		logger.debug("RuleCalCh:" + CalRuleCh);
		//add by yuanzw
		returnTransferData.setNameAndValue("SQL", SQLStatement);
		returnTransferData.setNameAndValue("BOMS", BOMS);
		returnTransferData.setNameAndValue("SQLPara", SQLPara);
		this.mResult.add(returnTransferData);
		

		
		tLRTemplateTSchema.setViewParameter(ViewPara);
		logger.debug("setViewParameter:" + ViewPara);
		tLRTemplateTSchema.setValid(Valid);
		logger.debug("setValid:" + Valid);
		tLRTemplateTSchema.setState(State);
		logger.debug("setState:" + State);
		tLRTemplateTSchema.setTemplateLevel(TempalteLevel);
		logger.debug("setTemplateLevel:" + TempalteLevel);
		tLRTemplateTSchema.setBusiness(Business);
		logger.debug("setBusiness:" + Business);

		tLRTemplateTSchema.setStartDate(RuleStartDate);
		logger.debug("setStartDate:" + RuleStartDate);
		tLRTemplateTSchema.setEndDate(RuleEndDate);
		logger.debug("RuleEndDate:" + RuleEndDate);
		tLRTemplateTSchema.setDescription(RuleDes);
		logger.debug("setDescription:" + RuleDes);
		tLRTemplateTSchema.setCreator(Creator);
		logger.debug("setOperator:" + Creator);
		tLRTemplateTSchema.setMakeDate(MakeDate);
		logger.debug("setMakeDate:" + sCurrentDate);
		tLRTemplateTSchema.setMakeTime(MakeTime);
		logger.debug("setMakeTime:" + sCurrentTime);
		tLRTemplateTSchema.setModifyDate(ModifyDate);
		logger.debug("sCurrentDate:" + sCurrentDate);
		tLRTemplateTSchema.setModifyTime(ModifyTime);
		logger.debug("setModifyTime:" + sCurrentTime);
		tLRTemplateTSchema.setRuleName(RuleName);
		logger.debug("setRuleName:" + RuleName);
		tLRTemplateTSchema.setTableName(TableName);
		logger.debug("setTableName:" + TableName);

		tLRTemplateTSchema.setModifier(Modifier);
		logger.debug("Modifier:" + Modifier);

		String CreateTable = (String) tTransferData
				.getValueByName("CreateTable");
		ArrayList SQLArray = (ArrayList) tTransferData
				.getValueByName("SQLArray");

		//TransferData transferData = new TransferData();

		String flag = (String) tTransferData.getValueByName("flag");
		String Operation = (String) tTransferData.getValueByName("Operation");
		// String TableName=(String)tTransferData.getValueByName("TableName");

		//transferData.setNameAndValue("flag", flag);
		//transferData.setNameAndValue("Operation", Operation);
		tTransferData.setNameAndValue("tLRTemplateTSchema", tLRTemplateTSchema);
		//transferData.setNameAndValue("TableName", TableName);
		//transferData.setNameAndValue("CreateTable", CreateTable);
		//transferData.setNameAndValue("SQLArray", SQLArray);

		if (!submitToDataBase(tTransferData))
			return false;

		return true;
	}

	private boolean submitToDataBase(TransferData transferData) {

		String flag = (String) transferData.getValueByName("flag");
		String Operation = (String) transferData.getValueByName("Operation");
		String TableName = (String) transferData.getValueByName("TableName");

		LRTemplateTSchema tLRTemplateTSchema = (LRTemplateTSchema) transferData
				.getValueByName("tLRTemplateTSchema");

		String CreateTable = (String) transferData
				.getValueByName("CreateTable");
		logger.debug("====================CreateTable:\n"
				+ CreateTable.toString());
		ArrayList SQLArray = (ArrayList) transferData
				.getValueByName("SQLArray");
		logger.debug("submitToDataBase中的SQLArray是:\n"
				+ SQLArray.toString());

		MMap mMMap = new MMap();
		mMMap.put(tLRTemplateTSchema, "DELETE&INSERT");

		if (flag.equalsIgnoreCase("4")) {
			if (Operation.equalsIgnoreCase("Logic_Modification")) {
				//tongmeng 2011-02-16 modify
				//如果是ldsysvar 不能drop
				if(!TableName.toLowerCase().trim().equals("ldsysvar")&&!TableName.toLowerCase().trim().equals("ruledata"))
				{
					if(mDropTableFlag)
					{
						mMMap.put("drop table " + TableName, "DROP");
					}
					
				}
				if(!CreateTable.equals(""))
				{
					mMMap.put(CreateTable, "CREATE");
				}

			} else {
				mMMap.put("truncate table " + TableName, "TRUNCATE");
			}
		} else {
			if(!CreateTable.equals(""))
			{
				mMMap.put(CreateTable, "CREATE");
			}
		}
		
		String tDeleteLRRuleDataSQL = (String)transferData.getValueByName("DeleteLRRuleDataSQL");
		
		if(!tDeleteLRRuleDataSQL.equals(""))
		{
			mMMap.put(tDeleteLRRuleDataSQL, "DELETE");
		}
		
		String tDeleteLRRuleDataBSQL = (String)transferData.getValueByName("DeleteLRRuleDataBSQL");
		
		if(!tDeleteLRRuleDataBSQL.equals(""))
		{
			mMMap.put(tDeleteLRRuleDataBSQL, "DELETE");
		}
		
		if(!this.mConvertFlag)
		{
			logger.debug("插入表语句执行开始！");

			for (int i = 0; i < SQLArray.size(); i++) {
				String sql = (String) SQLArray.get(i);
				logger.debug("正在执行的插入表语句是：\n" + sql);
				mMMap.put(sql, "INSERT");
			}
		}
		else
		{
			LRRuleDataSchema tLRRuleDataSchema = new LRRuleDataSchema();
			tLRRuleDataSchema = (LRRuleDataSchema)transferData.getValueByName("LRRuleDataSchema");
			
			LRRuleDataSchema tLRRuleDataSchema1 = new LRRuleDataSchema();
			tLRRuleDataSchema1.setSchema(tLRRuleDataSchema);
//			tLRRuleDataSchema.setRuleDataSQL("1");
			mMMap.put(tLRRuleDataSchema,"BLOBINSERT");
			mMMap.put(tLRRuleDataSchema1,"BLOBUPDATE");
			
			LRRuleDataBSchema tLRRuleDataBSchema = new LRRuleDataBSchema();
			LRRuleDataBSchema tLRRuleDataBSchema1 = new LRRuleDataBSchema();
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLRRuleDataBSchema,tLRRuleDataSchema);
			tLRRuleDataBSchema1.setSchema(tLRRuleDataBSchema);
			mMMap.put(tLRRuleDataBSchema,"BLOBINSERT");
			mMMap.put(tLRRuleDataBSchema1,"BLOBUPDATE");
			
			//mMMap.put(tLRRuleDataSchema, "BLOBINSERT");

		}

		VData tVData = new VData();
		tVData.add(mMMap);
		ExtPubSubmit p = new ExtPubSubmit();

		if (!p.submitData(tVData, ""))
			return false;

		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

	public VData getResult() {
		
		return this.mResult;
	}

	public boolean submitData(VData data, String Operater) {
		// TODO Auto-generated method stub
		return false;
	}
}
