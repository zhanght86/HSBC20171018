

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
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.sinosoft.lis.db.LDMsgInfo_MsgDB;
import com.sinosoft.lis.db.LRRuleDataDB;
import com.sinosoft.lis.db.LRTemplateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.PD_LMDutyGetSchema;
import com.sinosoft.lis.schema.PD_LMDutyPaySchema;
import com.sinosoft.lis.vschema.LDMsgInfo_MsgSet;
import com.sinosoft.lis.vschema.LRRuleDataSet;
import com.sinosoft.lis.vschema.LRTemplateSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConn;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLString;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// import com.sinosoft.msreport.CircJSDateParserXML;
// import org.dom4j.*;
// import org.dom4j.io.*;

public class PDDeployNewBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	// private String mOperate;
	/** 业务处理相关变量 */
	private MMap map = new MMap();

	private TransferData mTransferData = new TransferData();

	private String mRiskCode;

	private String mSQLParaName = "RISKCODE";

	private String mSQLSepaChar = "@";

	private String mIsDeleteCoreDataBeforeInsert; // 1:执行删除操作,0:不执行
	
	//tongmeng 2011-06-27 add
	private String mReleasePlatform = "";
	/**
	 * 01 LIS
	 * 02  Proposal
	 */
	private String mSysType = ""; //系统类别
	
	private String mEnvType = ""; //环境类别
	/**
	 * N-不包含PD 表 N-包含PD 表
	 */
	private String mPDNOY ="";

	private LRTemplateSet mLRTemplateSet = new LRTemplateSet();
	
	private Hashtable mCalCodeHashtable = new Hashtable();
	
	private Hashtable mRuleKeyHashtable = new Hashtable();
	//tongmeng 2011-09-07
	ArrayList mSQLArray = new ArrayList();
	public PDDeployNewBL() {
		
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

			ExeSQL tExeSQL = new ExeSQL();
			//String tSQL = "select comcode from ldcode where  codetype='pd_release' and code='"+mReleasePlatform+"'";
			//String tDeployMode = tExeSQL.getOneValue(tSQL);
			//通过Comcode描述 当前发布的环境是否与本机环境一直,如果一致的话,就不需要发布费率表和 规则引擎的数据了.
			
			//comcode=1 表示一致
			
			if (!check()) {
				return false;
			}
			
			if(this.mSysType.equals("01")&&this.mPDNOY.equals("Y")){
				getDeleteSqlPD();//查询PD表Delete 语句
				getDepolySqlPD();//查询PD表Insert语句
			}
			
			// 删除核心系统中原有的该产品信息
			if (!deleteCoreData()) {
				return false;
			}

			// 进行业务处理
			//tongmeng 2011-06-27 modify 
			//发布采用直接生产数据库脚本方式插入
			
			if (!deploy()) {
				return false;
			}
			
			
			
			
			//需要判断是否已有现价表
			// 处理费率表、现金价值表
			//????好像可以不用判断环境 了
			//if(tDeployMode!=null&&!tDeployMode.equals("1"))
//			{
				if (!deployRateCVData()) {
					return false;
				}
				
				if (!deployRateCVProDeData()) {
					return false;
				}
				
				//tongmeng 2011-06-29 add
				//增加规则引擎数据的发布
				if(!deployRuleEngine())
				{
					return false;
				}
//			}
			//tongmeng 2011-08-16 add 
			//增加公共规则的发布
				
			this.mResult.clear();
			this.mResult.add(this.map);
			
			//-------- add by jucy
			//将最大号写入生成脚本
			String tSQL1 = "Select maxno From ldmaxno Where notype = 'PE'";
			String tSQL2 = "Select maxno From ldmaxno Where notype = 'PE_LACOMMISSIONRATE'";
			String tSQL3 = "Select maxno From ldmaxno Where notype = 'CALCODECOPY'";
			String tSQL4 = "Select maxno From ldmaxno Where notype = 'IBRMSTEMPLATEID'";
			String tSQL5 = "Select maxno From ldmaxno Where notype = 'RISKTEMPID'";
			String tSQL6 = "Select maxno From ldmaxno Where notype = 'RUPD'";
			String tSQL7 = "Select maxno From ldmaxno Where notype = 'CLPD'";
			ExeSQL exeSQL = new ExeSQL();
	        String result1 = exeSQL.getOneValue(tSQL1);
	        String result2 = exeSQL.getOneValue(tSQL2);
	        String result3 = exeSQL.getOneValue(tSQL3);
	        String result4 = exeSQL.getOneValue(tSQL4);
	        String result5 = exeSQL.getOneValue(tSQL5);
	        String result6 = exeSQL.getOneValue(tSQL6);
	        String result7 = exeSQL.getOneValue(tSQL7);
	        String resultSql1 = "update ldmaxno set maxno='"+result1+"' where notype='PE';";
	        String resultSql2 = "update ldmaxno set maxno='"+result2+"' where notype='PE_LACOMMISSIONRATE';";
	        String resultSql3 = "update ldmaxno set maxno='"+result3+"' where notype='CALCODECOPY';";
	        String resultSql4 = "update ldmaxno set maxno='"+result4+"' where notype='IBRMSTEMPLATEID';";
	        String resultSql5 = "update ldmaxno set maxno='"+result5+"' where notype='RISKTEMPID';";
	        String resultSql6 = "update ldmaxno set maxno='"+result6+"' where notype='RUPD';";
	        String resultSql7 = "update ldmaxno set maxno='"+result7+"' where notype='CLPD';";

			this.mSQLArray.add(resultSql1);
			this.mSQLArray.add(resultSql2);
			this.mSQLArray.add(resultSql3);
			this.mSQLArray.add(resultSql4);
			this.mSQLArray.add(resultSql5);
			this.mSQLArray.add(resultSql6);
			this.mSQLArray.add(resultSql7);
			//-------- end
			
			// this.pubSubmit();
		} catch (Exception ex) {
			this.mErrors.addOneError(ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 查询PD表Insert语句
	 *
	 */
	private void getDepolySqlPD() {
		try {
			SSRS tableNameAndSelect = getTableNameAndSelect();
			if (tableNameAndSelect != null) {
				for (int tableIndex = 0; tableIndex < tableNameAndSelect.getMaxRow(); tableIndex++) {
					String pdTableCode = tableNameAndSelect.GetText(tableIndex + 1,1);
					String coreTableCode = tableNameAndSelect.GetText(tableIndex + 1, 2);
					String tSQL_Data = tableNameAndSelect.GetText(tableIndex + 1, 3);
					String tSQL_ColSSRS = "select column_name,data_type from user_tab_cols where lower(table_name) ='"+pdTableCode.toLowerCase()+"' order by column_id";
					ExeSQL tExeSQL = new ExeSQL();
					SSRS tColsSSRS = new SSRS();
					
					tColsSSRS = tExeSQL.execSQL(tSQL_ColSSRS);
					SSRS tDataSSRS = new SSRS();
					tDataSSRS = tExeSQL.execSQL(tSQL_Data);
					if(tColsSSRS.getMaxRow()!=tDataSSRS.getMaxCol())
					{
						System.out.println("费率表的列数错误");
					}
					
					for(int m=1;m<=tDataSSRS.getMaxRow();m++){
						//tongmeng 
						String tInsertSQL = "insert into "+pdTableCode ;
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
							//added by Elroy Wu on 20130925 to fix data type issue
							else if(tColType.toUpperCase().equals("INTEGER") || 
									tColType.toUpperCase().indexOf("NUMBER") > -1 || 
									tColType.toUpperCase().indexOf("DECIMAL") > -1 || 
									tColType.toUpperCase().indexOf("DOUBLE") > -1)
							{
								tValues = tValues + (tColValue != null && tColValue.trim().length() > 0 ? tColValue : "null");
							}
							else if(tColType.toUpperCase().indexOf("DATE") > -1)
							{
								tValues = tValues + (tColValue != null && tColValue.trim().length() > 0 ? "to_date('" + tColValue + "', 'yyyy-MM-dd')" : "null");
							}
							//added end
							else 
							{
								//-------- update by jucy
								tValues = tValues + "'" + tColValue + "'" ;
								//-------- end
								//tValues = tValues + tColValue ;
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
						//this.map.put(tInsertSQL, "INSERT");
						mSQLArray.add(tInsertSQL+";");
					}
				} 
			}
		}catch (Exception e) {
			System.out.println("getDepolySqlPD is error");
			e.printStackTrace();
		}
		
		
	}

	private boolean check() {
		
		return true;
	}
	
	public ArrayList getSQLArray()
	{
		return this.mSQLArray;
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
		String content="";
		tempLRTemplateSet = tempLRTemplateDB.executeQuery(tSQL);
		if(tempLRTemplateSet.size()>0)
		{
			this.mLRTemplateSet.add(tempLRTemplateSet);
			for(int i=1;i<=tempLRTemplateSet.size();i++)
			{
				//删除
				/*SQLString sqlObj = new SQLString("LRTemplate");
				sqlObj.setSQL(4, tempLRTemplateSet.get(i).getSchema());
				sqlObj.getSQL();
				this.mSQLArray.add(sqlObj.getSQL()+";");*/
				content=PDDeploySQLMaker.makeDeleteSQL(tempLRTemplateSet.get(i).getSchema());
				this.mSQLArray.add(content);
				//插入
/*				sqlObj = new SQLString("LRTemplate");
				sqlObj.setSQL(1, tempLRTemplateSet.get(i).getSchema());
				//System.out.println(sqlObj.getSQL());
				this.mSQLArray.add(sqlObj.getSQL()+";");*/
				
				content=PDDeploySQLMaker.makeInsertSQL(tempLRTemplateSet.get(i).getSchema());
//				content=content.replaceAll("\n", "")+"\n";
//				content=content.replaceAll("UPDATE", "\nUPDATE");
				this.mSQLArray.add(content);
				//补充ldmsginfo_msg的发布 
				String tSQL_MSG = "select * from ldmsginfo_msg where keyid like '"+tempLRTemplateSet.get(i).getRuleName()+"%' ";
				LDMsgInfo_MsgSet tLDMsgInfo_MsgSet = new LDMsgInfo_MsgSet();
				LDMsgInfo_MsgDB tLDMsgInfo_MsgDB = new LDMsgInfo_MsgDB();
				tLDMsgInfo_MsgSet = tLDMsgInfo_MsgDB.executeQuery(tSQL_MSG);
				if(tLDMsgInfo_MsgSet.size()>0)
				{
					this.map.put(tLDMsgInfo_MsgSet, "DELETE&INSERT");
					for(int n=1;n<=tLDMsgInfo_MsgSet.size();n++)
					{
						//删除
						SQLString sqlObj1 = new SQLString("LDMsgInfo_Msg");
						sqlObj1.setSQL(4, tLDMsgInfo_MsgSet.get(n).getSchema());
						sqlObj1.getSQL();
						this.mSQLArray.add(sqlObj1.getSQL()+";");
						//插入
						sqlObj1 = new SQLString("LDMsgInfo_Msg");
						sqlObj1.setSQL(1, tLDMsgInfo_MsgSet.get(n).getSchema());
						//System.out.println(sqlObj.getSQL());
						this.mSQLArray.add(sqlObj1.getSQL()+";");
					}
					
				}
			}
		}
		
	}
	
	/**
	 * 备份lremplate 
	 * @return
	 */
	private boolean deployRuleEngine()
	{
		boolean tResultFlag = true;
		String content="";
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
						
						//删除
/*						SQLString sqlObj = new SQLString("LRRuleData");
						sqlObj.setSQL(4, tLRRuleDataDB.getSchema());
						sqlObj.getSQL();
						this.mSQLArray.add(sqlObj.getSQL()+";");*/
						content=PDDeploySQLMaker.makeDeleteSQL(tLRRuleDataDB.getSchema());
						this.mSQLArray.add(content);
						//插入
/*						sqlObj = new SQLString("LRRuleData");
						sqlObj.setSQL(1, tLRRuleDataDB.getSchema());
						//System.out.println(sqlObj.getSQL());
						this.mSQLArray.add(sqlObj.getSQL()+";");*/
						
						content=PDDeploySQLMaker.makeInsertSQL(tLRRuleDataDB.getSchema());
						content=content.replaceAll("\n", "")+"\n";
						content=content.replaceAll("UPDATE", "\nUPDATE");
						this.mSQLArray.add(content);
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
								mSQLArray.add(tDropSQL+";");
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
					
					if(!(tSQL_DDL==null||"".equals(tSQL_DDL.trim()))){
					//this.map.put(tSQL_DDL, "CREATE");
					mSQLArray.add(tSQL_DDL+";");
					}
					//生成插入决策表的语句
					SSRS tDTSSRS = new SSRS();
					String tSQL_DT = "select * from "+ tTableName;
					tDTSSRS = tExeSQL.execSQL(tSQL_DT);
					
					String tSQL_ColSSRS = "select column_name,data_type from user_tab_cols where upper(table_name) =upper('"+tTableName.toLowerCase()+"') order by column_id";
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
							//added by Elroy Wu on 20130925 to fix data type issue
							else if(tColType.toUpperCase().equals("INTEGER") || 
									tColType.toUpperCase().indexOf("NUMBER") > -1 || 
									tColType.toUpperCase().indexOf("DECIMAL") > -1 || 
									tColType.toUpperCase().indexOf("DOUBLE") > -1)
							{
								tValues = tValues + (tColValue != null && tColValue.trim().length() > 0 ? tColValue : "null");
							}
							else if(tColType.toUpperCase().indexOf("DATE") > -1)
							{
								tValues = tValues + (tColValue != null && tColValue.trim().length() > 0 ? "to_date('" + tColValue + "', 'yyyy-MM-dd')" : "null");
							}
							//added end
							else 
							{
								//-------- update by jucy
								tValues = tValues + "'" + tColValue + "'" ;
								//-------- end
								//tValues = tValues + tColValue ;
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
						mSQLArray.add(tInsertSQL+";");
					}
				}

			}
			
			//this.map.put(mLRTemplateSet, "DELETE&INSERT");
			
//			if(tLRRuleDataSet.size()>0)
//			{
//				this.map.put(tLRRuleDataSet, "DELETE&INSERT");
//			}
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
							mSQLArray.add(tDropSQL+";");
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
				
				
				
				if(createTable!=null&&!"".equals(createTable.trim()))
				{
					//this.map.put(createTable, "CREATE");
					mSQLArray.add(createTable+";");
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
						//added by Elroy Wu on 20130925 to fix data type issue
						else if(tColType.toUpperCase().equals("INTEGER") || 
								tColType.toUpperCase().indexOf("NUMBER") > -1 || 
								tColType.toUpperCase().indexOf("DECIMAL") > -1 || 
								tColType.toUpperCase().indexOf("DOUBLE") > -1)
						{
							tValues = tValues + (tColValue != null && tColValue.trim().length() > 0 ? tColValue : "null");
						}
						else if(tColType.toUpperCase().indexOf("DATE") > -1)
						{
							tValues = tValues + (tColValue != null && tColValue.trim().length() > 0 ? "to_date('" + tColValue + "', 'yyyy-MM-dd')" : "null");
						}
						//added end
						else 
						{
							//-------- update by jucy
							tValues = tValues + "'" + tColValue + "'" ;
							//-------- end
							//tValues = tValues + tColValue ;
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
					//this.map.put(tInsertSQL, "INSERT");
					mSQLArray.add(tInsertSQL+";");
				}			
			}
		}

		return true;
	}
	
	
	
	
	
	public boolean deployRateCVProDeData() throws Exception {
		String select = "select coretablename,execsql from Pd_Ratecvsql where riskcode = '"
				+ this.mRiskCode + "' and sqltype = '2'";
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
							mSQLArray.add(tDropSQL+";");
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
				
				
				
				if(createTable!=null&&!"".equals(createTable.trim()))
				{
					//this.map.put(createTable, "CREATE");
					mSQLArray.add(createTable+";");
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
						//added by Elroy Wu on 20130925 to fix data type issue
						else if(tColType.toUpperCase().equals("INTEGER") || 
								tColType.toUpperCase().indexOf("NUMBER") > -1 || 
								tColType.toUpperCase().indexOf("DECIMAL") > -1 || 
								tColType.toUpperCase().indexOf("DOUBLE") > -1)
						{
							tValues = tValues + (tColValue != null && tColValue.trim().length() > 0 ? tColValue : "null");
						}
						else if(tColType.toUpperCase().indexOf("DATE") > -1)
						{
							tValues = tValues + (tColValue != null && tColValue.trim().length() > 0 ? "to_date('" + tColValue + "', 'yyyy-MM-dd')" : "null");
						}
						//added end
						else 
						{
							//-------- update by jucy
							tValues = tValues + "'" + tColValue + "'" ;
							//-------- end
							//tValues = tValues + tColValue ;
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
					//this.map.put(tInsertSQL, "INSERT");
					mSQLArray.add(tInsertSQL+";");
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

			this.mRiskCode = (String) mTransferData.getValueByName("RiskCode");

			if (this.mRiskCode == null) {
				this.mErrors.addOneError("险种代码不能为空!");
				return false;
			}
			
			mReleasePlatform = (String) mTransferData
			.getValueByName("ReleasePlatform");
			
			mSysType = (String) mTransferData
			.getValueByName("SysType");
			
			mEnvType = (String) mTransferData
			.getValueByName("EnvType");
			mPDNOY = (String) mTransferData
			.getValueByName("PDNOY");
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
		getDeleteSqlLM();	
		return true;
	}
	/**
	 * 查询PD表Delete语句
	 *
	 */
	private void getDeleteSqlPD() {
		try {
			SSRS ssrs =getTableNameAndSelect();
			for (int i = 1; i <= ssrs.getMaxRow(); i++) {
				String pdTabeName = ssrs.GetText(i, 1);
				String coreTableName =ssrs.GetText(i, 2);
				String selectSql = ssrs.GetText(i, 3);
				if(selectSql!=null&&!"".equals(selectSql.trim())){
					mSQLArray.add("delete from "+selectSql.substring(13)+";");
				}
			}
		} catch (Exception e) {
			System.out.println("getDeleteSqlPD is error");
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * 查询非备份表Delete语句
	 * @return
	 */
	public boolean getDeleteSqlLM(){
		SSRS deleteSQLs = getDeleteSQLs();
		if (deleteSQLs != null) {
			for (int index = 0; index < deleteSQLs.getMaxRow(); index++) {
				String deleteSQL = deleteSQLs.GetText(index + 1, 1);
				String deleteAccSQL = deleteSQLs.GetText(index + 1, 2);
				if (deleteSQL!=null&&!"".equals(deleteSQL.trim())) {
//					if(deleteAccSQL.equals("PD_LMRiskInsuAcc")){
//						//String deleteaccSQLs = "delete from lmriskinsuacc a where  exists (select 1 from LMRiskToAcc where riskcode =Trim('"+this.mRiskCode+"') and InsuAccNo=a.insuaccno) and  exists (select 1 from PD_LMRiskToAcc where riskcode =Trim('"+this.mRiskCode+"') and InsuAccNo=a.insuaccno)";
//						String deleteaccSQLs = "delete from lmriskinsuacc a where  a.insuaccno in (select InsuAccNo from PD_LMRiskToAcc where riskcode =Trim('"+this.mRiskCode+"'))";
//						this.map.put(deleteaccSQLs, "DELETE");
//						mSQLArray.add(deleteaccSQLs+";");
//					}else{
						this.map.put(deleteSQL, "DELETE");
						mSQLArray.add(deleteSQL+";");
//					}
				}
			}
		}
		return true;
	}

	/**
	 * 获取非PD 表的Delete 语句
	 * @return
	 */
	private SSRS getDeleteSQLs() {
		SSRS ssrs = new SSRS();
		String deleteSQLs = "select standbyflag1,tablecode from Pd_Tablemap where standbyflag1 is not null "
			              + " and substr(standbyflag2,-1,1) = '1' "
			              + " and upper(Coretablecode) in (select upper(tablename) from PD_PlatformToTablesMap where PlatformName='"+this.mSysType+"') "
			              + " order by standbyflag3";
		ssrs = getSSRSResult(deleteSQLs, 1);
		return ssrs;

	}

	public boolean deploy() throws Exception {
		// get product define table names, core table names, select sql
		SSRS tableNameAndSelect = getTableNameAndSelect();
		//产品定义表名  核心表名
		if (tableNameAndSelect != null) {
			for (int tableIndex = 0; tableIndex < tableNameAndSelect
					.getMaxRow(); tableIndex++) {
				// deal one table

				String pdTableCode = tableNameAndSelect.GetText(tableIndex + 1,1);
				
				System.out.println("pdTableCode:"+pdTableCode);
				String coreTableCode = tableNameAndSelect.GetText(tableIndex + 1, 2);
				String selectSQL = tableNameAndSelect.GetText(tableIndex + 1, 3);
				//-------- add by jucy
				if(pdTableCode.equals("PD_LMRiskInsuAcc")){
					selectSQL="select * from PD_LMRiskInsuAcc where InsuAccNo in (select InsuAccNo from PD_LMRiskToAcc where riskcode =Trim('"+ this.mRiskCode +"')) ";
				}
				//-------- end
				//tongmeng 2011-07-01 修改
				//需要获取目标数据库的字段和字段类型
				
				
				String tSQL_ColSSRS = "select column_name,data_type from user_tab_cols where lower(table_name) ='"+coreTableCode.toLowerCase()+"' and column_name not like '%$' order by column_id";
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
				//
				if(pdTableCode.toUpperCase().equals("PD_LMUW")||pdTableCode.toUpperCase().equals("PD_LMCHECKFIELD"))
				{
					String temps=this.mSysType.equals("01")?"standbyflag1 in('01','03')":"standbyflag1 in('02','03')";
					selectSQL = selectSQL + " and "+temps;
				}
/*				if(pdTableCode.toUpperCase().equals("PDT_RISKTYPETEMPLATE")){
					if(this.mSysType.trim().equals("01")){
						selectSQL = selectSQL + " and (standbyflag1='"+this.mSysType+"' or standbyflag1='' )";
					}else{
						selectSQL = selectSQL + " and standbyflag1='"+this.mSysType+"' ";
					}
				}
				if(pdTableCode.toUpperCase().equals("PDT_RISKSHOWTYPE")){
					if(this.mSysType.trim().equals("01")){
						selectSQL = selectSQL + " and (standbyflag1='"+this.mSysType+"' or standbyflag1=''  ))";
					}else{
						selectSQL = selectSQL + " and standbyflag1='"+this.mSysType+"'  )";
					}
				}
				if(pdTableCode.toUpperCase().equals("PDT_RISKSHOWCOL")){
					if(this.mSysType.trim().equals("01")){
						selectSQL = selectSQL + " and (standbyflag1='"+this.mSysType+"' or standbyflag1=''  ))";
					}else{
						selectSQL = selectSQL + " and standbyflag1='"+this.mSysType+"'  )";
					}
				}*/
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
				//将基金账户的插入语句虑掉，不生成到sql脚本中
				boolean flag=false;
				if("PD_LMRiskInsuAcc".equals(pdTableCode)||"LMRiskInsuAcc".equals(pdTableCode)){
					
					for(int n=1;n<=tColsSSRS.getMaxRow();n++)
					{
						String aColName = tColsSSRS.GetText(n, 1);
						String aColRealValue="";
						for(int m=0;m<pdSchema.getFieldCount();m++)
						{		
							String aCurrentFieldName = pdSchema.getFieldName(m);
							if(aCurrentFieldName.toUpperCase().equals(aColName.toUpperCase())){
								aColRealValue=pdSchema.getV(m);
								break;
							}
						}
						System.out.println("valueName is--"+aColName.toLowerCase());
						System.out.println("value is--"+aColRealValue);
						if("007".equals(aColRealValue)&&"acctype".equals(aColName.toLowerCase())){
							flag=true;
							break;
						}
						
					}	
					if(flag){
						continue;
					}
				}
				
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
							tColType.toUpperCase().equals("VARCHAR")) //||tColType.toUpperCase().equals("DATE"))
					{
						tValues = tValues + "'" + tColValue + "'" ;
					}
					//added by Elroy Wu on 20130925 to fix data type issue
					else if(tColType.toUpperCase().equals("INTEGER") || 
							tColType.toUpperCase().indexOf("NUMBER") > -1 || 
							tColType.toUpperCase().indexOf("DECIMAL") > -1 || 
							tColType.toUpperCase().indexOf("DOUBLE") > -1)
					{
						tValues = tValues + (tColValue != null && tColValue.trim().length() > 0 ? tColValue : "null");
					}
					else if(tColType.toUpperCase().indexOf("DATE") > -1)
					{
						tValues = tValues + (tColValue != null && tColValue.trim().length() > 0 ? "to_date('" + tColValue + "', 'yyyy-MM-dd')" : "null");
					}
					//added end
					else 
					{
						
						//-------- update by jucy
						tValues = tValues + "'" + tColValue + "'" ;
						//-------- end
						//tValues = tValues + tColValue ;
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
				if(null==tCols||null==tValues||"()".equals(tCols.trim())||"()".equals(tValues.trim())){			
					System.out.println(coreTableCode+"表没有表数据，不保存SQL");
					continue;
				}
				this.map.put(tInsertSQL, "INSERT");
				mSQLArray.add(tInsertSQL+";");
				
			
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
	private SSRS getTableNameAndSelect() throws Exception {
		String selectSQL = "select a.Tablecode,a.Coretablecode,b.Standbyflag1 from Pd_Tablemap a "
				+ " inner join Pd_Basetable b "
				+ " on upper(a.Tablecode) = upper(b.Tablecode) "
				+ " and b.Standbyflag1 is not null and substr(a.standbyflag2,-1,1) = '1'"
				+ " and upper(a.Coretablecode) in (select upper(tablename) from PD_PlatformToTablesMap where PlatformName='"+this.mSysType+"') "
				+ " order by a.tablecode " ;
		SSRS ssrs = getSSRSResult(selectSQL, 3);
		return ssrs;
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
//			String sql = "SELECT * FROM pd_lmdutyget WHERE getdutycode like '4122%'";
//			com.sinosoft.lis.db.PD_LMDutyGetDB db = new com.sinosoft.lis.db.PD_LMDutyGetDB();
//			SchemaSet pdSet = db.executeQuery(sql);
//			System.out.println("pdSet:" + pdSet.size());
//			SchemaSet newSet = new SchemaSet();
//			java.util.HashMap hashMap = new java.util.HashMap();
//			for (int i = 1; i <= pdSet.size(); i++) {
//				PD_LMDutyGetSchema pdSchema = (PD_LMDutyGetSchema) pdSet
//						.getObj(i);
//				String tDutyCode = pdSchema.getDutyCode();
//				String tGetDutycode = pdSchema.getGetDutyCode();
//				System.out.println("tDutyCode:" + tDutyCode + ";tGetDutycode:"
//						+ tGetDutycode);
//				if (hashMap.containsKey(tGetDutycode)) {
//					continue;
//				} else {
//					hashMap.put(tGetDutycode, tDutyCode);
//					newSet.add(pdSchema);
//				}
//			}
//			System.out.println("newSet:" + newSet.size());
			String sql ="select * from PD_LMRiskParamsDef where riskcode =Trim('IBC02')";
			System.out.println("delete from "+sql.substring(13));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}

