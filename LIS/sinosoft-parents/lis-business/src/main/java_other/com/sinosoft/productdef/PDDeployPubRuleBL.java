

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

// import java.io.File;
// import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import com.sinosoft.lis.db.LRRuleDataDB;
import com.sinosoft.lis.db.LRTemplateDB;
import com.sinosoft.lis.db.PD_LMCheckFieldDB;
import com.sinosoft.lis.db.PD_LMUWDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDDeployScriptInfoSchema;
import com.sinosoft.lis.schema.LMCheckFieldSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.schema.LRRuleDataSchema;
import com.sinosoft.lis.schema.LRTemplateSchema;
import com.sinosoft.lis.schema.PD_LMCheckFieldSchema;
import com.sinosoft.lis.schema.PD_LMDutyGetSchema;
import com.sinosoft.lis.schema.PD_LMDutyPaySchema;
import com.sinosoft.lis.schema.PD_LMUWSchema;
import com.sinosoft.lis.vschema.LRRuleDataSet;
import com.sinosoft.lis.vschema.LRTemplateSet;
import com.sinosoft.lis.vschema.PD_LMCheckFieldSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConn;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// import com.sinosoft.msreport.CircJSDateParserXML;
// import org.dom4j.*;
// import org.dom4j.io.*;

public class PDDeployPubRuleBL implements BusinessService{
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

	private TransferData mTransferData = new TransferData();

	private String mRiskCode;

	private String mSQLParaName = "RISKCODE";

	private String mSQLSepaChar = "@";

	private String mIsDeleteCoreDataBeforeInsert; // 1:执行删除操作,0:不执行
	
	//tongmeng 2011-06-27 add
	private String mReleasePlatform = "";
	private String mSysType = ""; //系统类别
	private String mEnvType = ""; //环境类别
	private String mRuleType = ""; //规则类型

	private String mDeplotTable = "";
	private LRTemplateSet mLRTemplateSet = new LRTemplateSet();
	
	private Hashtable mCalCodeHashtable = new Hashtable();
	
	private Hashtable mRuleKeyHashtable = new Hashtable();
	
	ArrayList mSQLArray = new ArrayList();
	ArrayList mSQLArrayMultLanguage = new ArrayList();
	
	private List<String> depolyList;
	public PDDeployPubRuleBL() {
		
		mCalCodeHashtable.put("CALCODE", "CALCODE");
		mCalCodeHashtable.put("ACCCANCELCODE", "ACCCANCELCODE");
		mCalCodeHashtable.put("ADDAMNTCOEFCODE", "ADDAMNTCOEFCODE");
		mCalCodeHashtable.put("ADDFEECALCODE", "ADDFEECALCODE");
		mCalCodeHashtable.put("BASICCALCODE", "BASICCALCODE");
		mCalCodeHashtable.put("BONUSCOEFCODE", "BONUSCOEFCODE");
		mCalCodeHashtable.put("CALCODE1", "CALCODE1");
		mCalCodeHashtable.put("CALCODE10", "CALCODE10");
		
		mCalCodeHashtable.put("CALCODE2", "CALCODE2");
		mCalCodeHashtable.put("BASICCALCODE", "BASICCALCODE");
		mCalCodeHashtable.put("BONUSCOEFCODE", "BONUSCOEFCODE");
		mCalCodeHashtable.put("CALCODE1", "CALCODE1");
		mCalCodeHashtable.put("CALCODE3", "CALCODE3");
		
		
		mCalCodeHashtable.put("CALCODE4", "CALCODE4");
		mCalCodeHashtable.put("CALCODE5", "CALCODE5");
		mCalCodeHashtable.put("CALCODE6", "CALCODE6");
		mCalCodeHashtable.put("CALCODE7", "CALCODE7");
		mCalCodeHashtable.put("CALCODE8", "CALCODE8");
		
		mCalCodeHashtable.put("CALCODE9", "CALCODE9");
		mCalCodeHashtable.put("CALCODEMONEY", "CALCODEMONEY");
		mCalCodeHashtable.put("CALCODEUNIT", "CALCODEUNIT");
		mCalCodeHashtable.put("CASHVALUECODE", "CASHVALUECODE");
		mCalCodeHashtable.put("CHGPREMCALCODE", "CHGPREMCALCODE");
		
		mCalCodeHashtable.put("CLMFEEDEFVALUE", "CLMFEEDEFVALUE");
		mCalCodeHashtable.put("CNTERCALCODE", "CNTERCALCODE");
		mCalCodeHashtable.put("CYCPAYCALCODE", "CYCPAYCALCODE");
		mCalCodeHashtable.put("DAYFEEMAXCALCODE", "DAYFEEMAXCALCODE");
		mCalCodeHashtable.put("FACTORCALCODE", "FACTORCALCODE");
		
		mCalCodeHashtable.put("FACTORCODE", "FACTORCODE");
		mCalCodeHashtable.put("FEECALCODE", "FEECALCODE");
		mCalCodeHashtable.put("FILTERCALCODE", "FILTERCALCODE");
		mCalCodeHashtable.put("GRACECALCODE", "GRACECALCODE");
		mCalCodeHashtable.put("INNERCALCODE", "INNERCALCODE");

		mCalCodeHashtable.put("INTERESTCALCODE", "INTERESTCALCODE");
		mCalCodeHashtable.put("ONEPAYCALCODE", "ONEPAYCALCODE");
		mCalCodeHashtable.put("OTHCALCODE", "OTHCALCODE");
		mCalCodeHashtable.put("OTHERCODE", "OTHERCODE");
		mCalCodeHashtable.put("RISKCALCODE", "RISKCALCODE");
		
		mCalCodeHashtable.put("SETMEALCODE", "SETMEALCODE");
		mCalCodeHashtable.put("TOTALFEEMAXCALCODE", "TOTALFEEMAXCALCODE");
		
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
			if(!check()){
				return false;
			}
			System.out.println("RuleType:"+this.mRuleType);
			//01,公共规则
			//02,规则引擎
			if(!dealData())return false;
			
			this.mResult.clear();
			this.mResult.add(this.map);

			 PubSubmit pubSubmitRelease = new PubSubmit();
			 boolean tSussRelease=true;
			 ExeSQL exe=new ExeSQL();
			 String sql="select comcode from ldcode where codetype = 'pd_release' and code='"+mReleasePlatform+"' ";
			 String env=exe.getOneValue(sql);
			 if("01".equals(env)){
			  tSussRelease = pubSubmitRelease.submitData(mResult);
			 }
			 String tDate = PubFun.getCurrentDate();
			 //String tTime = PubFun.getCurrentTime();
			 String pattern = "HH-mm-ss";
			 SimpleDateFormat df = new SimpleDateFormat(pattern);
			 Date today = new Date();
			 String tString = df.format(today);
			 String tTime = tString;
			 //String tFileName = mReleasePlatform+"_PubRule_"+tDate+"_"+tTime+".sql";
			 String tFileName =PubFun.getScripName("PUBRULE", mReleasePlatform, mRiskCode==null?"000000":mRiskCode);
			 LDDeployScriptInfoSchema aLDDeployScriptInfoSchema=new LDDeployScriptInfoSchema();
			 aLDDeployScriptInfoSchema.setRiskCode(mRiskCode==null?"000000":mRiskCode);
			 aLDDeployScriptInfoSchema.setType("2");
			 aLDDeployScriptInfoSchema.setName(tFileName);
			 aLDDeployScriptInfoSchema.setPath(PubFun.getProductDeployPath());
			 aLDDeployScriptInfoSchema.setEnvironment(env);
			 aLDDeployScriptInfoSchema.setOperator(mGlobalInput.Operator);
			 if(!PDDeployScriptDownload.recored(aLDDeployScriptInfoSchema)){
				this.mErrors.addOneError("公共信息发布脚本记录失败！"); 
				throw new Exception("公共信息发布脚本记录失败！");
			 }
			 for (int i = 0; i <mSQLArrayMultLanguage.size(); i++) {
				 mSQLArray.add(mSQLArrayMultLanguage.get(i));
				
			}
			 mSQLArrayMultLanguage.clear();
			 PubFun.ProductWriteToFile(tFileName, this.mSQLArray);
			 return tSussRelease;
		} catch (Exception ex) {
			this.mErrors.addOneError(ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		//return true;
	}
private boolean dealData(){
	//按条件发发布规则
	if("UWDEPLOYSELECT".equals(mOperate)){
		String SQL=(String)mTransferData.getValueByName("SQL");
		if(SQL==null||"".equals(SQL)){
			this.mErrors.addOneError("查询条件SQL为空！");
			return false;
		}
		SQLParseUtil sqlParse = new SQLParseUtil();
		SQL = sqlParse.parseSQL(SQL);
		SSRS ssrs=new SSRS();
		ssrs=new ExeSQL().execSQL(SQL);
		if(ssrs.getMaxRow()<=0){
			this.mErrors.addOneError("没有查得所需数据！");
			return false;
		}
		for (int i = 1; i <=ssrs.MaxNumber; i++) {
			System.out.println(ssrs.GetText(i, 1));
			this.depolyList.add(ssrs.GetText(i, 1));
		}
		if(!deal()){
			return false;
		}
		
	}else if("UWDEPLOY".equals(mOperate)){//按选择发布规则

	
	if(!deal()){
		return false;
	}
	}else{//发布所有的规则
		if (!deleteCoreData()) {
			return false;
		}	
		try {
			if (!deploy()) {
				return false;
			}
		} catch (Exception e) {
			this.mErrors.addOneError(e.getMessage());
			return false;
		}

		
		deployRuleEngine();
	}
	return true;
}
	private boolean deal() {
	if(depolyList==null||depolyList.size()<=0){
		this.mErrors.addOneError("没有选择要发布的规则！");
		return false;
	}else{
		String uwcode="";
		String calcode="";
		PD_LMCheckFieldSchema tPD_LMCheckFieldSchema;
		PD_LMUWSchema tPD_LMUWSchema;
		PD_LMCheckFieldDB tPD_LMCheckFieldDB;
		PD_LMUWDB tPD_LMUWDB;
		for (int i = 0; i <depolyList.size(); i++) {
			uwcode=depolyList.get(i);
			tPD_LMUWDB=new PD_LMUWDB();
			tPD_LMUWDB.setUWCode(uwcode);
			if(tPD_LMUWDB.getInfo()){
				//发布LMUW表
				tPD_LMUWSchema=tPD_LMUWDB.getSchema();
				LMUWSchema tLMUWSchema=new LMUWSchema();
				for (int j = 0; j <tLMUWSchema.FIELDNUM; j++) {
					tLMUWSchema.setV(tLMUWSchema.getFieldName(j), tPD_LMUWSchema.getV(tLMUWSchema.getFieldName(j)));
				}
				calcode=tLMUWSchema.getCalCode();
				
				getSQL(tLMUWSchema);
				getSQL(tPD_LMUWSchema);
//				getMultLanguageInfo(uwcode,"zh");
//				getMultLanguageInfo(uwcode,"tr");
				map.put(tLMUWSchema, "DELETE&INSERT");
				map.put(tPD_LMUWSchema, "DELETE&INSERT");
				
			}//else{
				//发布LMCheckField表
			tPD_LMCheckFieldDB=new PD_LMCheckFieldDB();
			tPD_LMCheckFieldDB.setCalCode(uwcode);
			PD_LMCheckFieldSet tPD_LMCheckFieldSet=tPD_LMCheckFieldDB.query();
			for (int j = 1; j <=tPD_LMCheckFieldSet.size(); j++) {
				tPD_LMCheckFieldSchema=tPD_LMCheckFieldSet.get(j);
				LMCheckFieldSchema tLMCheckFieldSchema=new LMCheckFieldSchema();
				for (int k = 0; k <tLMCheckFieldSchema.FIELDNUM; k++) {
					tLMCheckFieldSchema.setV(tLMCheckFieldSchema.getFieldName(k), tPD_LMCheckFieldSchema.getV(tLMCheckFieldSchema.getFieldName(k)));				
				}
				getSQL(tLMCheckFieldSchema);
				getSQL(tPD_LMCheckFieldSchema);
//				getMultLanguageInfo(uwcode,"zh");
//				getMultLanguageInfo(uwcode,"tr");
				map.put(tLMCheckFieldSchema, "DELETE&INSERT");
				map.put(tPD_LMCheckFieldSchema, "DELETE&INSERT");
				
				calcode=uwcode;
			}
			
//			}
			getMultLanguageInfo(calcode,"zh");
			getMultLanguageInfo(calcode,"tr");

//			getLRTemplate(uwcode);	
			getLRTemplate(calcode);	
		}
		deployRuleEngine();
	}
		//PubFun.writeToFile("Test11.sql", mSQLArray);
		return true;
	}
	//发布语言信息
public  void getMultLanguageInfo(String rulename,String language){	
		String deteleSQL="";
		String RuleId="";
		String KeyId="";
		String SQL="";
		String sql="";
		ExeSQL exe=new ExeSQL();
		List<String> list=new ArrayList<String>();
		String id="select id,tablename from lrtemplate where (business='01' or business='02') and rulename ='"+rulename+"' union select id, tablename from lrtemplatet where (business='01' or business='02') and rulename ='"+rulename+"'";
		SSRS tSSRSid=exe.execSQL(id);
		if(tSSRSid==null||tSSRSid.MaxRow<=0)return;
		if("RuleData".equals(tSSRSid.GetText(1, 2))||"ldsysvar".equals(tSSRSid.GetText(1, 2))){
			SQL="select ruledatasql from lrruledata where id='"+tSSRSid.GetText(1, 1)+"'";
			sql=exe.getOneValue(SQL);
			if(sql==null||"".equals(sql))return ;
			deteleSQL="delete from ldmsginfo_msg where language='"+language+"' and keyid like '"+rulename+"%';";
			sql="select ruleid from "+sql;
			list.add(deteleSQL);
			SSRS tSSRS=exe.execSQL(sql);
			for (int i = 1; i<=tSSRS.MaxRow; i++) {
				RuleId=tSSRS.GetText(i, 1);
				KeyId=rulename+RuleId;
				String mul=exe.getOneValue("select msg from ldmsginfo_msg where keyid='"+KeyId+"' and language='"+language+"'");
				if("tr".equals(language)){
					if(mul==null||"".equals(mul)){
						mul="-- 规则 "+rulename+" 没有繁体多语言信息";
					System.out.println(mul);
					list.add(mul);
					}else{
						list.add("insert into ldmsginfo_msg (language,keyid,msg) values('"+language+"','"+KeyId+"','"+mul+"');");
					}
				}else{
				if(mul==null||"".equals(mul.trim()))mul=exe.getOneValue("select remark from pd_lmuw where uwcode='"+rulename+"' union select msg from pd_lmcheckfield where calcode='"+rulename+"'");		
				list.add("insert into ldmsginfo_msg (language,keyid,msg) values('"+language+"','"+KeyId+"','"+mul+"');");
				}	
		}
			
		}else{
			sql="select * from "+tSSRSid.GetText(1, 2);
			deteleSQL="delete from ldmsginfo_msg where language='"+language+"' and keyid like '"+rulename+"%';";
			list.add(deteleSQL);
			SSRS tSSRS=exe.execSQL(sql);
			for (int i = 1; i <=tSSRS.MaxRow; i++) {
				RuleId=tSSRS.GetText(i, tSSRS.MaxCol);
				KeyId=rulename+RuleId;
				String mul=exe.getOneValue("select msg from ldmsginfo_msg where keyid='"+KeyId+"' and language='"+language+"'");
				if("tr".equals(language)){
					if(mul==null||"".equals(mul)){
						mul="-- 规则 "+rulename+" 没有繁体多语言信息";
					System.out.println(mul);
					list.add(mul);
					}else{
						list.add("insert into ldmsginfo_msg (language,keyid,msg) values('"+language+"','"+KeyId+"','"+mul+"');");
					}
				}else{
				if(mul==null||"".equals(mul.trim()))mul=exe.getOneValue("select remark from pd_lmuw where uwcode='"+rulename+"' union select msg from pd_lmcheckfield where calcode='"+rulename+"'");		
				list.add("insert into ldmsginfo_msg (language,keyid,msg) values('"+language+"','"+KeyId+"','"+mul+"');");
				}
			}
			
			
		}
			for (int i = 0; i <list.size(); i++) {
				//System.out.println(list.get(i));
				mSQLArrayMultLanguage.add(list.get(i));
			}
	}
private void  getSQL(Schema e){
	String SQL=null;
	SQL=PDDeploySQLMaker.makeDeleteSQL(e);
	mSQLArray.add(SQL);
	SQL=PDDeploySQLMaker.makeInsertSQL(e);
	//SQL=SQL.replaceAll("\n", "")+"\n";
	//SQL=SQL.replaceAll("UPDATE", "\nUPDATE");
	mSQLArray.add(SQL);	
}
	private boolean check() {
 if(mReleasePlatform==null||"".equals(mReleasePlatform.trim())){
	 this.mErrors.addOneError("没选择发布的平台！");
	 return false;
 }
		return true;
	}
	
	private boolean deployPubRule()
	{
		//tongmeng 2011-07-01 修改
		//需要获取目标数据库的字段和字段类型
		String pdTableCode = "PD_LMUW";
		String coreTableCode = "LMUW";
		String selectSQL = "select * from pd_lmuw where riskcode='000000' ";
		String tSQL_ColSSRS = "select column_name,data_type from user_tab_cols where lower(table_name) ='"+coreTableCode.toLowerCase()+"' order by column_id";
		SSRS tColsSSRS = new SSRS();
		DBConn connRelease = null;
		try {
			try {
				//connRelease = DBConnPool.getConnection(mReleasePlatform);
				connRelease = DBConnPool.getConnection();
				if(connRelease!=null)
				{
					ExeSQL tExeSQL = new ExeSQL(connRelease);
					tColsSSRS = tExeSQL.execSQL(tSQL_ColSSRS);
					connRelease.close();
					connRelease = null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(connRelease!=null)
				{
					connRelease.close();
					connRelease = null;
				}
			}
			finally
			{
				if(connRelease!=null)
				{
					connRelease.close();
					connRelease = null;
				}
			}
			

			
			// get data
			buildInsertData(pdTableCode, coreTableCode, selectSQL,
					tColsSSRS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return true;
	}
	
	
	private void getLRTemplate(String tRuleName)
	{
		LRTemplateDB tempLRTemplateDB = new LRTemplateDB();
		LRTemplateSet tempLRTemplateSet = new LRTemplateSet();
		//tempLRTemplateDB.setRuleName(aRuleName)
		String tSQL = "select * from lrtemplate where rulename='"+tRuleName+"' ";
		tempLRTemplateSet = tempLRTemplateDB.executeQuery(tSQL);
		if(tempLRTemplateSet.size()>0)
		{
			this.mLRTemplateSet.add(tempLRTemplateSet);

		}


	}
	
	private boolean deployRuleEngine()
	{
		boolean tResultFlag = true;
		
		if(this.mLRTemplateSet.size()>0)
		{
			LRRuleDataSet tLRRuleDataSet =  new LRRuleDataSet();
			//需要带上LRRuleData
			for(int i=1;i<=mLRTemplateSet.size();i++)
			{
				String tId = mLRTemplateSet.get(i).getId();
				
				String tTableName = mLRTemplateSet.get(i).getTableName();
				
				if(tTableName.toUpperCase().equals("RULEDATA"))
				{
					LRRuleDataDB tLRRuleDataDB = new LRRuleDataDB();
					tLRRuleDataDB.setId(tId);
					if(tLRRuleDataDB.getInfo())
					{
						tLRRuleDataSet.add(tLRRuleDataDB.getSchema());
					}
				}
				else if(tTableName.toUpperCase().equals("LDSYSVAR"))
				{
					continue;
				}
				else if(tTableName.toUpperCase().indexOf("DT")!=-1)
				{
					//决策表,需要获取决策表的建表语句和数据
					
					//先判断目标库是否已经有该表,如果有,先drop,然后重新创建. 
					DBConn connRelease = null;
					try {
						//connRelease = DBConnPool.getConnection(mReleasePlatform);
						connRelease = DBConnPool.getConnection();
						if(connRelease!=null)
						{
							String tSQL_CTTable =  "select 1 from user_tables where table_name='"+tTableName.toUpperCase()+"' ";
							ExeSQL tReleaseExeSQL = new ExeSQL(connRelease);
							String tReleaseValue =  tReleaseExeSQL.getOneValue(tSQL_CTTable);
							if(tReleaseValue!=null&&tReleaseValue.equals("1"))
							{
								String tDropSQL = "drop table "+tTableName;
								//this.map.put(tDropSQL, "DROP");
								mSQLArray.add(tDropSQL);
								
							}
							
							connRelease.close();
							connRelease = null;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						if(connRelease!=null)
						{
							try {
								connRelease.close();
								connRelease = null;
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					finally
					{
						if(connRelease!=null)
						{
							try {
								connRelease.close();
								connRelease = null;
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					//获取当前表的建表语句

					String tQuery_SQL_DDL = "select  DBMS_METADATA.GET_DDL('TABLE',UPPER('"+tTableName+"')) FROM DUAL ";
					ExeSQL tExeSQL = new ExeSQL();
					System.out.println(tExeSQL.getOneValue(tQuery_SQL_DDL));
					String tSQL_DDL = "";
					tSQL_DDL = tExeSQL.getOneValue(tQuery_SQL_DDL);

					if(tSQL_DDL.indexOf("USING INDEX")!=-1)
					{
						tSQL_DDL = tSQL_DDL.substring(0,tSQL_DDL.indexOf("USING INDEX"));
						tSQL_DDL = tSQL_DDL + " )";
					}
					else if(tSQL_DDL.indexOf("PCTFREE")!=-1)
					{
						tSQL_DDL = tSQL_DDL.substring(0,tSQL_DDL.indexOf("PCTFREE"));
					}
					
					tSQL_DDL = StrTool.replaceEx(tSQL_DDL, "\"","");  
					
					//获取当前的用户
					String tSQL_CurrentUser = "select SYS_CONTEXT('USERENV','CURRENT_USER') from dual ";
					String tTableUser = tExeSQL.getOneValue(tSQL_CurrentUser);
					System.out.println("CurrentUser:"+tTableUser);
					if(tTableUser!=null&&!tTableUser.equals(""))
					{
						tSQL_DDL = StrTool.replaceEx(tSQL_DDL, tTableUser+".","");
					}
					
					System.out.println("建表语句:"+tSQL_DDL);
					
					
					this.map.put(tSQL_DDL, "CREATE");
					mSQLArray.add(tSQL_DDL);
					
					//生成插入决策表的语句
					SSRS tDTSSRS = new SSRS();
					String tSQL_DT = "select * from "+ tTableName;
					tDTSSRS = tExeSQL.execSQL(tSQL_DT);
					
					String tSQL_ColSSRS = "select column_name,data_type from user_tab_cols where lower(table_name) ='"+tTableName.toLowerCase()+"' order by column_id";
					SSRS tColsSSRS = new SSRS();
					tColsSSRS = tExeSQL.execSQL(tSQL_ColSSRS);
					
					
					for(int m=1;m<=tDTSSRS.getMaxRow();m++)
					{
						//每一行的数据
						String tInsertSQL = "insert into "+tTableName ;
						
						String tCols = "";
						String tValues = " ";
						tCols = tCols + "(";
						tValues = tValues + "(";
						for(int n=1;n<=tColsSSRS.getMaxRow();n++)
						{
							String tColName = tColsSSRS.GetText(n, 1);
							String tColType = tColsSSRS.GetText(n, 2);
							String tColValue = tDTSSRS.GetText(m, n);
							//pdSchema.getFieldCount()
	
							
							tCols = tCols + tColName ;
							if(tColType.toUpperCase().equals("VARCHAR2")||
									tColType.toUpperCase().equals("CHAR")||
									tColType.toUpperCase().equals("VARCHAR")||tColType.toUpperCase().equals("DATE"))
							{
								tValues = tValues + "'" + tColValue + "'" ;
							}
							else 
							{
								
								tValues = tValues + tColValue ;
							}
							if(n!=tColsSSRS.getMaxRow())
							{
								tCols = tCols + ",";
								tValues = tValues + ",";
							}
						}
						tCols = tCols + ")";
						tValues = tValues + ")";
						
						tInsertSQL = tInsertSQL + " " + tCols + " values " + tValues;
						System.out.println("tInsertSQL:"+tInsertSQL);
						//this.map.put(tInsertSQL, "INSERT");
						mSQLArray.add(tInsertSQL);
					}
				}

			}			
			for (int i = 1; i <=mLRTemplateSet.size(); i++) {
				LRTemplateSchema e=mLRTemplateSet.get(i);
					getSQL(e);
			}
			if(tLRRuleDataSet.size()>0)
			{
				for (int i = 1; i <= tLRRuleDataSet.size(); i++) {
					LRRuleDataSchema e=tLRRuleDataSet.get(i);
					getSQL(e);

				}
			}
		}
		
		
		
		return tResultFlag;
	}
	
	

	// deploy rate and cashvalue data
	public boolean deployRateCVData() throws Exception {
		String select = "select coretablename,execsql from Pd_Ratecvsql where riskcode = '"
				+ this.mRiskCode + "' and sqltype = '1'";
		ExeSQL exec = new ExeSQL();
		SSRS ssrs = exec.execSQL(select);
		if (ssrs.MaxRow > 0) {
			for (int i = 1; i <= ssrs.MaxRow; i++) {
				String coreTableName = ssrs.GetText(i, 1);
				String createTable = ssrs.GetText(i, 2);
				//先判断目标库是否已经有该表,如果有,先drop,然后重新创建. 
				DBConn connRelease = null;
				try {
					//connRelease = DBConnPool.getConnection(mReleasePlatform);
					connRelease = DBConnPool.getConnection();
					if(connRelease!=null)
					{
						String tSQL_CTTable =  "select 1 from user_tables where table_name='"+coreTableName.toUpperCase()+"' ";
						ExeSQL tReleaseExeSQL = new ExeSQL(connRelease);
						String tReleaseValue =  tReleaseExeSQL.getOneValue(tSQL_CTTable);
						if(tReleaseValue!=null&&tReleaseValue.equals("1"))
						{
							String tDropSQL = "drop table "+coreTableName;
							this.map.put(tDropSQL, "DROP");
							mSQLArray.add(tDropSQL);
						}
						
						connRelease.close();
						connRelease = null;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(connRelease!=null)
					{
						connRelease.close();
						connRelease = null;
					}
				}
				finally
				{
					if(connRelease!=null)
					{
						connRelease.close();
						connRelease = null;
					}
				}
				
				
				
				if(createTable!=null&&!createTable.equals(""))
				{
					this.map.put(createTable, "CREATE");
					mSQLArray.add(createTable);
				}
					
				
				//tongmeng 2011-06-29 add
				//采用生成SQL语句方法进行数据的发布 				
				
				String tSQL_ColSSRS = "select column_name,data_type from user_tab_cols where lower(table_name) ='"+coreTableName.toLowerCase()+"' order by column_id";
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tColsSSRS = new SSRS();
				tColsSSRS = tExeSQL.execSQL(tSQL_ColSSRS);
				
				
				
				
				
				SSRS tDataSSRS = new SSRS();
				
				String tSQL_Data = "select * from "+coreTableName+" ";
				tDataSSRS = tExeSQL.execSQL(tSQL_Data);
				
				if(tColsSSRS.getMaxRow()!=tDataSSRS.getMaxCol())
				{
					System.out.println("费率表的列数错误");
					return false;
				}
				
				for(int m=1;m<=tDataSSRS.getMaxRow();m++)
				{
					//tongmeng 
					String tInsertSQL = "insert into "+coreTableName ;
					
					String tCols = "";
					String tValues = "";
					tCols = tCols + "(";
					tValues = tValues + "(";
					for(int n=1;n<=tDataSSRS.getMaxCol();n++)
					{
						String tColName = tColsSSRS.GetText(n, 1);
						String tColType = tColsSSRS.GetText(n, 1);
						String tColValue = tDataSSRS.GetText(m, n);
						
						tCols = tCols + tColName;
						
						if(tColType.toUpperCase().equals("VARCHAR2")||
								tColType.toUpperCase().equals("CHAR")||
								tColType.toUpperCase().equals("VARCHAR"))
						{
							tValues = tValues + "'" + tColValue + "'" ;
						}
						else 
						{
							tValues = tValues + tColValue ;
						}
						if(n!=tDataSSRS.getMaxCol())
						{
							tCols = tCols + ",";
							tValues = tValues + ",";
						}
					
					}
					
					tCols = tCols + ")";
					tValues = tValues + ")";
					
					tInsertSQL = tInsertSQL + " " + tCols + " values " + tValues;
					System.out.println("tInsertSQL:"+tInsertSQL);
					this.map.put(tInsertSQL, "INSERT");
					mSQLArray.add(tInsertSQL);
				}			
			}
		}

		return true;
	}

	/**
	 * 提交数据，进行数据库操作
	 * 
	 * @return
	 */
	// private boolean pubSubmit() {
	// // 进行数据提交
	// PubSubmit tPubSubmit = new PubSubmit();
	// if (!tPubSubmit.submitData(this.mResult, "")) {
	// this.mErrors.addOneError("数据提交失败!");
	// return false;
	// }
	// return true;
	//
	// }
	/**
	 * 得到传入数据
	 */
	@SuppressWarnings("unchecked")
	private boolean getInputData() {
		try {
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

			mOperate=(String)mTransferData.getValueByName("Operator");
			depolyList=(List<String>)mTransferData.getValueByName("Mulline8Grid");
			mReleasePlatform = (String) mTransferData
			.getValueByName("ReleasePlatform");
			
			mSysType = (String) mTransferData
			.getValueByName("SysType");
			
			mEnvType = (String) mTransferData
			.getValueByName("EnvType");
			
			this.mRuleType = (String) mTransferData
			.getValueByName("RuleType");
			if(depolyList==null||depolyList.size()<=0)depolyList=new ArrayList<String>();			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		
//		this.mIsDeleteCoreDataBeforeInsert = (String) mTransferData
//				.getValueByName("IsDeleteCoreDataBeforeInsert");

		return true;
	}

	public boolean deleteCoreData() {
		//if (this.mIsDeleteCoreDataBeforeInsert.equals("1")) 
		{
			String[] deleteSQLs = getDeleteSQLs();

			if (deleteSQLs != null) {
				for (int index = 0; index < deleteSQLs.length; index++) {
					String deleteSQL = deleteSQLs[index];

					if (!deleteSQL.equals("")) {
						this.map.put(deleteSQL, "DELETE");
						mSQLArray.add(deleteSQL);
					}
				}
			}
		}

		return true;
	}

	private String[] getDeleteSQLs() {
		String[] tDelArray = null;
		if("01".equals(this.mRuleType))
		{
			tDelArray =  new String[2];
			//tDelArray[0] = "delete  from lmuw where riskcode='000000'  ";
			//tDelArray[1] = "delete  from lmcheckfield where riskcode='000000'  ";
		}
		else
		{
			tDelArray =  new String[3];
			tDelArray[0] = "delete  from lrbom ";
			tDelArray[1] = "delete  from lrbomitem ";
			tDelArray[2] = "delete  from ldmsginfo_bom ";
		}
		

		return tDelArray;

	}

	public boolean deploy() throws Exception {
		// get product define table names, core table names, select sql
		ArrayList tableNameAndSelect = getTableNameAndSelect();
		//产品定义表名  核心表名
		if (tableNameAndSelect != null) {
			for (int tableIndex = 0; tableIndex < tableNameAndSelect.size(); tableIndex++) {
				// deal one table
				String[] temp = (String[])tableNameAndSelect.get(tableIndex);
				String pdTableCode = temp[0];
				
				System.out.println("pdTableCode:"+pdTableCode);
				String coreTableCode = temp[1];
				String selectSQL = temp[2];

				
				//tongmeng 2011-07-01 修改
				//需要获取目标数据库的字段和字段类型
				
				
				String tSQL_ColSSRS = "select column_name,data_type from user_tab_cols where upper(table_name) =upper('"+coreTableCode.toLowerCase()+"') order by column_id";
				SSRS tColsSSRS = new SSRS();
				DBConn connRelease = null;
				try {
					//connRelease = DBConnPool.getConnection(mReleasePlatform);
					connRelease = DBConnPool.getConnection();
					if(connRelease!=null)
					{
						ExeSQL tExeSQL = new ExeSQL(connRelease);
						tColsSSRS = tExeSQL.execSQL(tSQL_ColSSRS);
						connRelease.close();
						connRelease = null;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(connRelease!=null)
					{
						connRelease.close();
						connRelease = null;
					}
				}
				finally
				{
					if(connRelease!=null)
					{
						connRelease.close();
						connRelease = null;
					}
				}
				
			
				
				// get data
				buildInsertData(pdTableCode, coreTableCode, selectSQL,
						tColsSSRS);
			}
		}

		return true;
	}

	private void buildInsertData(String pdTableCode, String coreTableCode,
			String selectSQL, SSRS tColsSSRS) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		SchemaSet pdSet = getProductData(pdTableCode, selectSQL);

		if (pdSet != null) {
			int schemaCount = pdSet.size();

			for (int i = 0; i < schemaCount; i++) {
				Schema pdSchema = (Schema) pdSet.getObj(i + 1);
				//if(coreTableCode.toUpperCase().equals("LMCALMODE"))
				//{
				//	System.out.println("1");
				//}
				//tongmeng 
				String tInsertSQL = "insert into "+coreTableCode ;
				//if(coreTableCode.equals("LMRiskApp"))
				//{
				//	System.out.println("tttttt");
				//}
				String tCols = "";
				String tValues = " ";
				tCols = tCols + "(";
				tValues = tValues + "(";
				for(int n=1;n<=tColsSSRS.getMaxRow();n++)
				{
					String tColName = tColsSSRS.GetText(n, 1);
					String tColType = tColsSSRS.GetText(n, 2);
					String tColValue = "";
					//pdSchema.getFieldCount()

					for(int m=0;m<pdSchema.getFieldCount();m++)
					{
						String tCurrentFieldName = pdSchema.getFieldName(m);
						if(tCurrentFieldName.toUpperCase().equals(tColName.toUpperCase()))
						{
							tColValue = pdSchema.getV(m);
							if(tColValue.equals("null"))
							{
								tColValue = "";
							}
							tColValue = StrTool.replace(tColValue, "'","''");
							break;
						}
					}
					//判断是否跟规则引擎相关
					if(this.mCalCodeHashtable.containsKey(tColName))
					{
						if(!this.mRuleKeyHashtable.containsKey(tColValue))
						{
							this.getLRTemplate(tColValue);
							this.mRuleKeyHashtable.put(tColValue, tColValue);
						}
					}
					
					tCols = tCols + tColName ;
					if(tColType.toUpperCase().equals("VARCHAR2")||
							tColType.toUpperCase().equals("CHAR")||
							tColType.toUpperCase().equals("VARCHAR")||tColType.toUpperCase().equals("DATE"))
					{
						tValues = tValues + "'" + tColValue + "'" ;
					}
					else 
					{
						
						tValues = tValues + tColValue ;
					}
					if(n!=tColsSSRS.getMaxRow())
					{
						tCols = tCols + ",";
						tValues = tValues + ",";
					}
				}
				tCols = tCols + ")";
				tValues = tValues + ")";
				
				tInsertSQL = tInsertSQL + " " + tCols + " values " + tValues;
				System.out.println("tInsertSQL:"+tInsertSQL);
				this.map.put(tInsertSQL, "INSERT");
				mSQLArray.add(tInsertSQL);
				
			
			}
		}
	}

	private void convertData(Schema fromSchema, Schema toSchema)
			throws Exception {
		int fieldCount = fromSchema.getFieldCount();
		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++) {
			String fieldName = fromSchema.getFieldName(fieldIndex);
			convertData(fromSchema, toSchema, fieldName, fieldName);
		}
	}

	/***************************************************************************
	 * 
	 * @return SSRS:PDTableName,SelectSQL,CoreTableName
	 */
	private ArrayList getTableNameAndSelect() throws Exception {
		ArrayList tSelArray = new ArrayList();
		if(("01").equals(this.mRuleType))
		{
			String[] temp = new String[3];
			temp[0] = "PD_LMUW";
			temp[1] = "LMUW";
			temp[2] = "select * from pd_lmuw where riskcode='000000' and (standbyflag1='03' or standbyflag1='"+this.mSysType+"') ";
			
			tSelArray.add(temp);
			
			temp = new String[3];
			temp[0] = "PD_LMCheckField";
			temp[1] = "LMCHECKFIELD";
			temp[2] = "select * from pd_lmcheckfield where riskcode='000000' and (standbyflag1='03' or standbyflag1='"+this.mSysType+"') ";
			
			tSelArray.add(temp);
			
		}
		else
		{
			String[] temp = new String[3];
			temp[0] = "LRBOM";
			temp[1] = "LRBOM";
			temp[2] = "select * from lrbom ";
			
			tSelArray.add(temp);
			
			 temp = new String[3];
			 temp[0] = "LRBOMItem";
			 temp[1] = "LRBOMItem";
			 temp[2] = "select * from lrbomitem ";
			 
			 tSelArray.add(temp);
			 
			 temp = new String[3];
			 temp[0] = "LDMsgInfo_BOM";
			 temp[1] = "LDMsgInfo_BOM";
			 temp[2] = "select * from ldmsginfo_bom ";
			
			 tSelArray.add(temp);
		}
		return tSelArray;
	}

	private SSRS getSSRSResult(String selectSQL, int indexOfRiskCode) {
		SSRS ssrs = new SSRS();
		ExeSQL exec = new ExeSQL();
		ssrs = exec.execSQL(selectSQL);

		System.out.println("\n查询语句：");

		for (int i = 0; i < ssrs.getMaxRow(); i++) {
			String sql = ssrs.GetText(i + 1, indexOfRiskCode);

			String tParaStr, tStr1;
			while (true) {
				tParaStr = PubFun.getStr(sql, 2, mSQLSepaChar);
				if (tParaStr.equals("")) {
					break;
				} else if (tParaStr.equalsIgnoreCase(this.mSQLParaName)) {
					tStr1 = mSQLSepaChar + tParaStr.trim() + mSQLSepaChar;
					// 替换变量
					sql = StrTool.replaceEx(sql, tStr1, this.mRiskCode);
				} else {
					// 针对发布产品和套餐时都需要用到的表，第二个参数是没有用的
					// delete from LMRiskComCtrl where
					// riskcode=Trim('@RISKCODE@') or riskcode =
					// Trim('@CARDSETMEAL@')
					tStr1 = mSQLSepaChar + tParaStr.trim() + mSQLSepaChar;
					// 替换变量
					sql = StrTool.replaceEx(sql, tStr1, "无用");

				}
			}

			System.out.println(sql);

			ssrs.setTextAt(i + 1, indexOfRiskCode, sql);
		}
		return ssrs;
	}

	/**
	 * @author NicolE 修改PD_LMdutyGet表的数据，对单给付责任对应多责任的情况进行处理
	 * @param pdTableCode
	 * @param selectSQL
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	private SchemaSet getProductData(String pdTableCode, String selectSQL)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		// get data using pd schema

		if (selectSQL.equals("")) {
			return null;
		}

		Class pdSetClass = Class.forName("com.sinosoft.lis.vschema."
				+ pdTableCode + "Set");
		SchemaSet pdSet = (SchemaSet) pdSetClass.newInstance();

		Class pdDBClass = Class.forName("com.sinosoft.lis.db." + pdTableCode
				+ "DB");
		Schema pdDB = (Schema) pdDBClass.newInstance();

		Class[] paras = new Class[1];
		paras[0] = String.class;
		Object[] args = new Object[1];
		args[0] = selectSQL;

		Method m = pdDB.getClass().getMethod("executeQuery", paras);
		pdSet = (SchemaSet) m.invoke(pdDB, args);

		//pdSet = dealDutyGetData(pdTableCode, pdSet);

		return pdSet;
	}

	private void dealMap(String pdTableCode, String coreTableCode,
			Schema pdSchema, Schema coreSchema) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String sql = "select PDFieldCode,CoreFiledCode from Pd_Fieldmap where upper(tablecode) = upper('"
				+ pdTableCode
				+ "') and upper(coreTableCode) = upper('"
				+ coreTableCode + "')";
		SSRS ssrs = new SSRS();
		ExeSQL exec = new ExeSQL();
		ssrs = exec.execSQL(sql);

		if (ssrs != null) {
			for (int fieldIndex = 0; fieldIndex < ssrs.getMaxRow(); fieldIndex++) {
				String pdFieldCode = ssrs.GetText(fieldIndex + 1, 1);
				String coreFieldCode = ssrs.GetText(fieldIndex + 1, 2);

				convertData(pdSchema, coreSchema, pdFieldCode, coreFieldCode);
			}
		}
	}

	/***************************************************************************
	 * 将fromSchema中的fromFieldCode的值传递给toSchema的toFieldCode。
	 * 
	 * @param fromSchema
	 * @param toSchema
	 * @param fromFieldCode
	 * @param toFieldCode
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void convertData(Schema fromSchema, Schema toSchema,
			String fromFieldCode, String toFieldCode)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Method m;

		try {
			Class[] c = new Class[1];
			c[0] = String.class;
			m = fromSchema.getClass().getMethod("getV", c);

			Object[] o = new Object[1];
			o[0] = fromFieldCode;
			String fieldValue = (String) m.invoke(fromSchema, o);

			Class[] coreClass = new Class[2];
			coreClass[0] = String.class;
			coreClass[1] = String.class;
			m = toSchema.getClass().getMethod("setV", coreClass);

			Object[] coreObject = new Object[2];
			coreObject[0] = toFieldCode;
			coreObject[1] = fieldValue;
			m.invoke(toSchema, coreObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fromSchema:"+fromSchema+":fromFieldCode:"+fromFieldCode+":toFieldCode:"+toFieldCode);
		} 
	}

	public VData getResult() {
		return this.mResult;
	}

	public void test(String pdTableCode) throws Exception {
		Class pdSchemaClass = Class.forName("com.sinosoft.lis.schema."
				+ pdTableCode + "Schema");
		Schema pdSchema = (Schema) pdSchemaClass.newInstance();

		Class[] c = new Class[1];
		c[0] = String.class;

		Method m = pdSchema.getClass().getMethod("getCode", null);
		Object[] o = new Object[1];
		o[0] = "Code";

		Object value = m.invoke(pdSchema, null);

		if (value == null) {
			System.out.println("null");
		} else if (value.equals("null")) {
			System.out.println(value);
		}
	}

	/**
	 * @author NicolE 从PD_LMDutyGet/PD_LMDutyPay表中获取唯一的GetDutycode/Payplancode数据
	 * @return
	 */
	private SchemaSet dealDutyGetData(String tTableCode, SchemaSet pdSet) {

		SchemaSet newSet = new SchemaSet();
		java.util.HashMap hashMap = new java.util.HashMap();
		if (tTableCode.equalsIgnoreCase("PD_LMDutyGet")) {
			for (int i = 1; i <= pdSet.size(); i++) {
				PD_LMDutyGetSchema pdSchema = (PD_LMDutyGetSchema) pdSet
						.getObj(i);
				String tDutyCode = pdSchema.getDutyCode();
				String tGetDutycode = pdSchema.getGetDutyCode();
				if (hashMap.containsKey(tGetDutycode)) {
					continue;
				} else {
					hashMap.put(tGetDutycode, tDutyCode);
					newSet.add(pdSchema);
				}
			}
			return newSet;
		} else if (tTableCode.equalsIgnoreCase("PD_LMDutyPay")) {
			for (int i = 1; i <= pdSet.size(); i++) {
				PD_LMDutyPaySchema pdSchema = (PD_LMDutyPaySchema) pdSet
						.getObj(i);
				String tDutyCode = pdSchema.getDutyCode();
				String tPayPlanCode = pdSchema.getPayPlanCode();
				if (hashMap.containsKey(tPayPlanCode)) {
					continue;
				} else {
					hashMap.put(tPayPlanCode, tDutyCode);
					newSet.add(pdSchema);
				}
			}
			return newSet;
		} else {
			return pdSet;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String sql = "SELECT * FROM pd_lmdutyget WHERE getdutycode like '4122%'";
			com.sinosoft.lis.db.PD_LMDutyGetDB db = new com.sinosoft.lis.db.PD_LMDutyGetDB();
			SchemaSet pdSet = db.executeQuery(sql);
			System.out.println("pdSet:" + pdSet.size());
			SchemaSet newSet = new SchemaSet();
			java.util.HashMap hashMap = new java.util.HashMap();
			for (int i = 1; i <= pdSet.size(); i++) {
				PD_LMDutyGetSchema pdSchema = (PD_LMDutyGetSchema) pdSet
						.getObj(i);
				String tDutyCode = pdSchema.getDutyCode();
				String tGetDutycode = pdSchema.getGetDutyCode();
				System.out.println("tDutyCode:" + tDutyCode + ";tGetDutycode:"
						+ tGetDutycode);
				if (hashMap.containsKey(tGetDutycode)) {
					continue;
				} else {
					hashMap.put(tGetDutycode, tDutyCode);
					newSet.add(pdSchema);
				}
			}
			System.out.println("newSet:" + newSet.size());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public CErrors getErrors() {
		return this.mErrors;
	}

	
	
}
