

/**
 * <p>Title: PDTestDeploy</p>
 * <p>Description: 产品测试与发布</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-18
 */

package com.sinosoft.productdef;

// import java.lang.reflect.InvocationTargetException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.sinosoft.lis.db.PD_DutyLibMapDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.RiskState;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LDDeployScriptInfoSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.PD_DutyLibMapSchema;
import com.sinosoft.lis.schema.PD_LMCalFactorSortSchema;
import com.sinosoft.lis.schema.PD_ReleaseTraceSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.PD_LBRiskInfoSet;
import com.sinosoft.utility.CError;
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
import com.sinosoft.workflow.proddef.ProdDefWorkFlowBL;

public class PDTestDeployBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	// private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	// private String mOperate;
	/** 业务处理相关变量 */
	private MMap map = new MMap();

	private VData mResultMaps = new VData();

	private String mReleasePlatform;

	private String mRiskCode;

	private String mGetDutyOrPayPlanCode2;

	//private String mPlanType;
	private String mSysType; //系统类别
	private String mEnvType; //环境类别
	private String mFromPath;
	private TransferData mTransferData;

	private RiskState mRiskState;
	
	private String tDeployMode;//发布模式，0：dblink方式，其它本地

	public PDTestDeployBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {

			getInputData(cInputData);
			if (!check(cInputData, cOperate)) {
				return false;
			}
			
			System.out.println("mEnvType:"+mEnvType+":mReleasePlatform:"+mReleasePlatform);
			

//			if ("03".equals(mEnvType)) {
				ProdDefWorkFlowBL tProdDefWorkFlowBL = new ProdDefWorkFlowBL();

				if (!tProdDefWorkFlowBL.submitData(cInputData, cOperate)) {
					this.mErrors.addOneError(tProdDefWorkFlowBL.mErrors
							.getFirstError());
					return false;
				}

				this.mResult.add(tProdDefWorkFlowBL.getResult());
//			} else {
//				System.out.println("非正式环境，不需要流转！");
//			}

//			if (!modifyDutyLib()) {
//				return false;
//			}

//			if (!setCalFactorSort(this.mRiskCode)) {
//				this.mErrors.addOneError("理赔计算要素处理失败");
//				return false;
//			}
			
//			ExeSQL tExeSQL = new ExeSQL();
//			String tSQL = "select comcode from ldcode where  codetype='pd_release' and code='"+mReleasePlatform+"'";
//			tDeployMode = tExeSQL.getOneValue(tSQL);
//			
//			//tongmeng 2011-06-27 add
//			//此处需要修改成多连接方式 
//			//并且费率表也不能这么创建
//			if (!createRateCVTables(this.mRiskCode, this.mReleasePlatform)) {
//				this.mErrors.addOneError("创建费率表、现金价值表失败");
//				return false;
//			}
			
			
			
			//tongmeng 2011-06-27 end add

			// create synonyms
			//tongmeng modify
			//tongmeng 2011-06-27 add
			//采用多连接方式后,发布不需要创建dblink
//			if(tDeployMode!=null&&!tDeployMode.equals("")&&tDeployMode.equals("0"))
//			{
//				//需要创建同义词
//				if (!tExeSQL.execProcedure(mReleasePlatform)) {
//					this.mErrors.addOneError("创建别名失败!");
//					return false;
//				}
//			}
			//tongmeng 2011-06-27 end add
			//所有跟发布环境有关的数据都转移到 PDDeployNewBL 中处理 
			// deploy product data
			
			VData mReleaseResult = new VData();
			
			
			PDDeployNewBL tPDDeployBL = new PDDeployNewBL();
 			if(tDeployMode==null){
 				tDeployMode = "-1";
 			}
 			cInputData.add(tDeployMode);//将发布模式传入
 			if (!tPDDeployBL.submitData(cInputData, cOperate)) {
 				this.mErrors.addOneError(tPDDeployBL.mErrors.getFirstError());
 				return false;
 			}

 			//this.mResult.add(tPDDeployBL.getResult());
 			mReleaseResult=tPDDeployBL.getResult();
			// record release trace
			if (!recordReleaseTrace(cInputData)) {
				this.mErrors.addOneError("记录发布轨迹失败");
				return false;
			}
			
			PDRiskOperationTraceBL bl = new PDRiskOperationTraceBL();
			boolean flag = bl.submitData(cInputData, "");
			if (flag) {
				PD_LBRiskInfoSet set = (PD_LBRiskInfoSet) bl.saveResult
						.getObjectByObjectName("PD_LBRiskInfoSet", 0);
				map.put(set, "DELETE&INSERT");

			}

			if (!prepareOutputData()) {
				return false;
			}
			ExeSQL tExeSQL = new ExeSQL();
			//String tSQL = "select comcode from ldcode where  codetype='pd_release' and comcode='"+mReleasePlatform+"'";
			//String tDeployMode = tExeSQL.getOneValue(tSQL);
/*			ExeSQL tExeSQL = new ExeSQL();
			String tSQL = "select comcode from ldcode where  codetype='pd_release' and code='"+mReleasePlatform+"'";
			String tDeployMode = tExeSQL.getOneValue(tSQL);*/
			//通过Comcode描述 当前发布的环境是否与本机环境一直,如果一致的话,就不需要发布费率表和 规则引擎的数据了.
			
			
			//为保持事务一致.此处同时获取本地连接和发布环境连接,手工进行提交.
			DBConn connLocal = null;
			DBConn connRelease = null;
			//if(mReleasePlatform!=null&&"LIS".equals(mReleasePlatform)){
			if(mSysType!=null&&"01".equals(mSysType)){
			try {
				//获取本地连接
				connLocal = DBConnPool.getConnection();
/*				//获取目标环境连接 
				if(mSysType!=null&&!mSysType.equals("01")&&!mSysType.equals("02"))
				{
					connRelease = DBConnPool.getConnection(mReleasePlatform);
				}
				else
				{
					//本机环境,直接获取默认 的连接 
					connRelease = DBConnPool.getConnection();
				}*/
				connRelease = DBConnPool.getConnection();
				if(connLocal!=null&&connRelease!=null)
				{
					 connLocal.setAutoCommit(false);
					 connRelease.setAutoCommit(false);
					 PubSubmit pubSubmitLocal = new PubSubmit(connLocal);
					 PubSubmit pubSubmitRelease = new PubSubmit(connRelease);
					 boolean tSussLocal = pubSubmitLocal.submitData(this.mResultMaps);
					 
					 boolean tSussRelease = pubSubmitRelease.submitData(mReleaseResult);
					 
					 if(tSussLocal&&tSussRelease)
					 {
						 connLocal.commit();
						 connLocal.close();
						 connLocal = null;
						 connRelease.commit();
						 connRelease.close();
						 connRelease = null;
						 String tDate = PubFun.getCurrentDate();
						 //String tTime = PubFun.getCurrentTime();
						 String pattern = "HH-mm-ss";
						 SimpleDateFormat df = new SimpleDateFormat(pattern);
						 Date today = new Date();
						 String tString = df.format(today);
						 String tTime = tString;
						 //
						 //String tFileName = "Risk_"+mRiskCode+"_"+mReleasePlatform+"_"+tDate+"_"+tTime+".sql";
						 String tFileName =PubFun.getScripName("PRODUCT", mReleasePlatform, mRiskCode);
						 LDDeployScriptInfoSchema aLDDeployScriptInfoSchema=new LDDeployScriptInfoSchema();
						 aLDDeployScriptInfoSchema.setRiskCode(mRiskCode);
						 aLDDeployScriptInfoSchema.setType("1");
						 aLDDeployScriptInfoSchema.setName(tFileName);
						 aLDDeployScriptInfoSchema.setPath(PubFun.getProductDeployPath());
						 aLDDeployScriptInfoSchema.setEnvironment(mEnvType);
						 aLDDeployScriptInfoSchema.setOperator(mGlobalInput.Operator);
						 if(!PDDeployScriptDownload.recored(aLDDeployScriptInfoSchema)){
							this.mErrors.addOneError(mRiskCode+"产品发布脚本信息记录失败!"); 
							throw new Exception(mRiskCode+"产品发布脚本信息记录失败！");
						 }
						//在配置环境执行Product Engine生成sql时使产品信息查询中可以查到 
						ArrayList ResultArrayList= addMissionInformation(tPDDeployBL.getSQLArray());
						PubFun.ProductWriteToFile(tFileName, ResultArrayList);
						 //PubFun.writeToFile(tFileName, tPDDeployBL.getSQLArray());
					 }
					 else
					 {
						 
						 connLocal.rollback();
						 connLocal.close();
						 connLocal = null;
						 connRelease.rollback();
						 connRelease.close();
						 connRelease = null;
						 
						 CError.buildErr(this,"提交 数据失败!");
						 return false;
					 }
	
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					if(connLocal!=null)
					{
						connLocal.rollback();
						connLocal.close();
						connLocal = null;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					if(connRelease!=null)
					{
						connRelease.rollback();
						connRelease.close();
						connRelease = null;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			}else if(mSysType!=null&&"02".equals(mSysType)){
				 String tDate = PubFun.getCurrentDate();
				 String pattern = "HH-mm-ss";
				 SimpleDateFormat df = new SimpleDateFormat(pattern);
				 Date today = new Date();
				 String tString = df.format(today);
				 String tTime = tString;
				 //String tFileName = "Risk_"+mRiskCode+"_"+mReleasePlatform+"_"+tDate+"_"+tTime+".sql";
				 String tFileName =PubFun.getScripName("PRODUCT", mReleasePlatform, mRiskCode);
				 LDDeployScriptInfoSchema aLDDeployScriptInfoSchema=new LDDeployScriptInfoSchema();
				 aLDDeployScriptInfoSchema.setRiskCode(mRiskCode);
				 aLDDeployScriptInfoSchema.setType("1");
				 aLDDeployScriptInfoSchema.setName(tFileName);
				 aLDDeployScriptInfoSchema.setPath(PubFun.getProductDeployPath());
				 aLDDeployScriptInfoSchema.setEnvironment(mEnvType);
				 aLDDeployScriptInfoSchema.setOperator(mGlobalInput.Operator);
				 if(!PDDeployScriptDownload.recored(aLDDeployScriptInfoSchema)){
					this.mErrors.addOneError(mRiskCode+"产品发布脚本信息记录失败!"); 
					throw new Exception(mRiskCode+"产品发布脚本信息记录失败！");
				 }
				 PubFun.ProductWriteToFile(tFileName, tPDDeployBL.getSQLArray());
				 copyFileToPub();
			}
			
		} catch (Exception ex) {
			this.mErrors.addOneError("提交错误:" + ex.getMessage());
			return false;
		}
		mRiskState.setState(mRiskCode, "测试发布->测试发布","1");
		return true;
	}
	
	private ArrayList addMissionInformation(ArrayList cInputDaTa){
		ArrayList tcInputDaTa=cInputDaTa;
		String LWMissionSqlInst="";
		String LWMissionSqlDel="";
		MMap s= (MMap)this.mResultMaps.getObjectByObjectName("MMap",0);

		LWMissionSet mLWMissionSet=(LWMissionSet) s.getObjectByObjectName("LWMissionSet", 0);
		LWMissionSchema mLWMissionSchema=mLWMissionSet.get(1);
		String mMissionID=mLWMissionSchema.getMissionID();
		mMissionID=mMissionID.substring(2, mMissionID.length());
		mMissionID="PE"+mMissionID;
		
		LWMissionSqlDel="delete from LWMission where MISSIONID='"+mMissionID+"'and SUBMISSIONID='"+mLWMissionSchema.getSubMissionID()+"' and ACTIVITYID='"+mLWMissionSchema.getActivityID()+"'";
		
		LWMissionSqlInst="insert into LWMission ( MissionID,SubMissionID,ProcessID,ActivityID,ActivityStatus,MissionProp1,MissionProp2,MissionProp3,MissionProp4,LastOperator,CreateOperator,MakeDate,MakeTime,ModifyDate,ModifyTime,InDate,InTime,OperateCom,MainMissionID ) values ( '"+
			mMissionID+"', '"+mLWMissionSchema.getSubMissionID()+"', '"+mLWMissionSchema.getProcessID()+"', '"+
			mLWMissionSchema.getActivityID()+"', '"+mLWMissionSchema.getActivityStatus()+"', '"+mLWMissionSchema.getMissionProp1()+"', '"+
			mLWMissionSchema.getMissionProp2()+"', '"+mLWMissionSchema.getMissionProp3()+"', '"+mLWMissionSchema.getMissionProp4()+"', '"+
			mLWMissionSchema.getLastOperator()+"', '"+mLWMissionSchema.getCreateOperator()+"', to_date('"+mLWMissionSchema.getMakeDate()+"', 'YYYY-MM-DD'), '"+
			mLWMissionSchema.getMakeTime()+"', to_date('"+mLWMissionSchema.getModifyDate()+"', 'YYYY-MM-DD'), '"+mLWMissionSchema.getModifyTime()+"', to_date('"+mLWMissionSchema.getInDate()+"', 'YYYY-MM-DD'), '"+mLWMissionSchema.getInTime()+"', '"+mLWMissionSchema.getOperateCom()+"', '"+mLWMissionSchema.getMainMissionID()+"')";
		
		tcInputDaTa.add(LWMissionSqlDel);
		tcInputDaTa.add(LWMissionSqlInst);

		return tcInputDaTa;
	}

	private boolean recordReleaseTrace(VData cInputData) throws Exception {
		PD_ReleaseTraceSchema tPD_ReleaseTraceSchema = new PD_ReleaseTraceSchema();

		PDGetMaxNo tPDGetMaxNo = new PDGetMaxNo();
		String aReleaseId = tPDGetMaxNo.getMaxNo("ReleaseId", "0");

		tPD_ReleaseTraceSchema.setReleaseId(aReleaseId);
		tPD_ReleaseTraceSchema.setReleasePlatform(mReleasePlatform);
		tPD_ReleaseTraceSchema.setCode(mRiskCode);
		tPD_ReleaseTraceSchema.setType("1");

		ExeSQL exec = new ExeSQL();

		String sql = "select codename from ldcode where codetype = 'pdreleaserisktype' and code = '1'";
		String releaserisktype = exec.getOneValue(sql);

		sql = "select sysvarvalue from ldsysvar where Sysvar = 'pdworkflow01cond'";
		String workflow01cond = exec.getOneValue(sql);

		sql = " select " + releaserisktype
				+ " from lbmission where missionprop2 = '" + this.mRiskCode
				+ "' and " + workflow01cond
				+ " order by outdate desc, outtime desc";
		String aSubType = exec.getOneValue(sql);

		tPD_ReleaseTraceSchema.setSubType(aSubType);

		tPD_ReleaseTraceSchema.setOperator(mGlobalInput.Operator);
		tPD_ReleaseTraceSchema.setMakeDate(PubFun.getCurrentDate());
		tPD_ReleaseTraceSchema.setMakeTime(PubFun.getCurrentTime());
		tPD_ReleaseTraceSchema.setModifyDate(PubFun.getCurrentDate());
		tPD_ReleaseTraceSchema.setModifyTime(PubFun.getCurrentTime());

		this.map.put(tPD_ReleaseTraceSchema, "DELETE&INSERT");

		return true;
	}

	/***************************************************************************
	 * 将产品的责任信息存储在责任库，以备以后参考复用。
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean modifyDutyLib() throws Exception {
		String select = "select distinct b.tablecode,a.standbyflag1 from Pd_Basetable a join PD_TableMap b on a.Tablecode = upper(b.Tablecode) where substr(a.standbyflag2,-3,1) = '1'";
		SSRS ssrs = new ExeSQL().execSQL(select);

		if (ssrs != null) {
			for (int i = 0; i < ssrs.getMaxRow(); i++) {
				// 遍历四个责任表
				String tablecode = ssrs.GetText(i + 1, 1);
				String sql = ssrs.GetText(i + 1, 2);

				if (!modifyOneDutyLib(tablecode + "_Lib", tablecode, sql)) {
					return false;
				}
			}
		}

		return true;
	}

	/***************************************************************************
	 * 修改一个责任库的信息
	 * 
	 * @param libTableName
	 * @param tableCode
	 * @return
	 * @throws Exception
	 */
	private boolean modifyOneDutyLib(String libTableName, String tableCode,
			String sql) throws Exception {

		if (!sql.equals("")) {
			sql = sql.replaceAll("@RISKCODE@", this.mRiskCode);

			SchemaSet set = getSchemaSet("com.sinosoft.lis.db." + tableCode
					+ "DB", sql);

			int size = set.size();
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					// 遍历每一条责任信息
					Schema schema = (Schema) set.getObj(i + 1);

					if (!modifyOneDuty(libTableName, schema, tableCode)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private SchemaSet getSchemaSet(String className, String sql)
			throws Exception {

		Class fromDBClass = Class.forName(className);
		Object fromDB = fromDBClass.newInstance();

		Class[] c = new Class[1];
		c[0] = String.class;
		Method m = fromDB.getClass().getMethod("executeQuery", c);

		Object[] o = new Object[1];
		o[0] = sql;
		SchemaSet set = (SchemaSet) m.invoke(fromDB, o);
		return set;
	}

	private boolean modifyOneDuty(String libTableName, Schema fromSchema,
			String tableCode) throws Exception {

		ExeSQL exec = new ExeSQL();
		String tConcDutyCode = "", tDutyType = "0", tDutyType2 = "";

		// 查找PD_DutyLibMap中是否有关联的记录
		SSRS ssrs = exec
				.execSQL("select codename,codealias,comcode,othersign from ldcode where codetype = 'PD_DutyLibMapWhere'"
						+ " and code = '" + tableCode + "'");
		if (ssrs == null) {
			this.mErrors.addOneError("查询ldcode失败");
			return false;
		}

		String wherePart = ssrs.GetText(1, 1);
		tConcDutyCode = fromSchema.getV(ssrs.GetText(1, 2));
		if (!ssrs.GetText(1, 3).equals("")) {
			tDutyType = fromSchema.getV(ssrs.GetText(1, 3));
		}
		tDutyType2 = ssrs.GetText(1, 4);

		String[] pkStr = fromSchema.getPK();
		int len = pkStr.length;
		for (int i = 0; i < len; i++) {
			wherePart = wherePart.replaceAll("@" + pkStr[i] + "@", fromSchema
					.getV(pkStr[i]));
		}

		String selectInLib = "select dutylibcode,id from PD_DutyLibMap where "
				+ wherePart + " and standbyflag3 = '" + ssrs.GetText(1, 4)
				+ "'";

		String dutyLibCode = "";
		String id = "";

		ssrs = exec.execSQL(selectInLib);
		if (ssrs != null && ssrs.getMaxRow() == 1) {
			dutyLibCode = ssrs.GetText(1, 1);
			id = ssrs.GetText(1, 2);
		} else if (ssrs != null && ssrs.getMaxRow() > 1) {
			this.mErrors.addOneError("关联关系有误");// 一个具体责任只能对应库中的一条责任，不能对应多条
			return false;
		}

		if (dutyLibCode == "") {
			// 向lib中插入
			if (!modifyLib(libTableName, fromSchema, "", "DELETE&INSERT", tableCode)) {
				return false;
			}

			// 在PD_DutyLibMap中记录关联
			if (!addOneToLibMap(tConcDutyCode, tDutyType, tDutyType2)) {
				return false;
			}

		} else {
			Schema libSchema = getLibSchema(dutyLibCode, libTableName);

			if (libSchema == null) {
				return false;
			}

			// 如果库中有，但内容有变化
			if (!SchemaUtilities.isContentSame(fromSchema, libSchema)) {
				String s = " select count(1) from PD_DutyLibMap where dutylibcode = '"
						+ dutyLibCode + "'";

				if (!(new ExeSQL().getOneValue(s).equals("1"))) {
					// 库中信息被其它险种引用，再创建新的记录,修改关联
					if (!modifyLib(libTableName, fromSchema, "", "DELETE&INSERT",
							tableCode)) {
						return false;
					}

					if (!modifyLibMap(id)) {
						return false;
					}
				} else {
					// 没有被其它险种引用，修改库中记录
					modifyLib(libTableName, fromSchema, dutyLibCode, "UPDATE",
							tableCode);
				}
			}
		}

		return true;
	}

	private Schema getLibSchema(String dutyLibCode, String libTableName)
			throws Exception {

		String select = new ExeSQL()
				.getOneValue("select standbyflag1 from Pd_Basetable where tablecode = upper('"
						+ libTableName + "')");
		if (select.equals("")) {
			this.mErrors.addOneError("查询PD_Basetable出错");
			return null;
		}

		select = select.replaceAll("@key@", dutyLibCode);

		Class dbClass = Class.forName("com.sinosoft.lis.db." + libTableName
				+ "DB");
		Object dbObj = dbClass.newInstance();
		Class[] c = new Class[1];
		c[0] = String.class;

		Method m = dbClass.getMethod("executeQuery", c);
		Object[] o = new Object[1];
		o[0] = select;

		SchemaSet set = (SchemaSet) m.invoke(dbObj, o);
		if (set == null) {
			this.mErrors.addOneError("查询" + libTableName + "失败");
			return null;
		}

		return (Schema) set.getObj(1);
	}

	private boolean modifyLibMap(String id) {
		PD_DutyLibMapDB tPD_DutyLibMapDB = new PD_DutyLibMapDB();

		tPD_DutyLibMapDB.setId(id);

		if (!tPD_DutyLibMapDB.getInfo()) {
			this.mErrors.addOneError("访问表责任库关联出错");
			return false;
		}

		tPD_DutyLibMapDB.setDutyLibCode(this.mGetDutyOrPayPlanCode2);
		tPD_DutyLibMapDB.setModifyDate(PubFun.getCurrentDate());
		tPD_DutyLibMapDB.setModifyTime(PubFun.getCurrentTime());

		PD_DutyLibMapSchema tPD_DutyLibMapSchema = tPD_DutyLibMapDB.getSchema();

		this.map.put(tPD_DutyLibMapSchema, "UPDATE");

		return true;
	}

	private boolean addOneToLibMap(String concDutyCode, String dutyType,
			String dutyType2) throws Exception {
		PDGetMaxNo tPDGetMaxNo = new PDGetMaxNo();
		String newId = tPDGetMaxNo.getMaxNo("PDLibTableNo", "PD_DutyLibMap");
		if (newId == null) {
			this.mErrors.addOneError(tPDGetMaxNo.mErrors.getFirstError());
			return false;
		}

		PD_DutyLibMapSchema tPD_DutyLibMapSchema = new PD_DutyLibMapSchema();
		tPD_DutyLibMapSchema.setId(newId);
		tPD_DutyLibMapSchema.setDutyLibCode(mGetDutyOrPayPlanCode2);
		tPD_DutyLibMapSchema.setConcDutyCode(concDutyCode);
		tPD_DutyLibMapSchema.setDutyType(dutyType);
		tPD_DutyLibMapSchema.setStandbyflag3(dutyType2);
		tPD_DutyLibMapSchema.setSubKind("0");
		tPD_DutyLibMapSchema.setOperator(this.mGlobalInput.Operator);
		tPD_DutyLibMapSchema.setMakeDate(PubFun.getCurrentDate());
		tPD_DutyLibMapSchema.setMakeTime(PubFun.getCurrentTime());
		tPD_DutyLibMapSchema.setModifyDate(PubFun.getCurrentDate());
		tPD_DutyLibMapSchema.setModifyTime(PubFun.getCurrentTime());

		this.map.put(tPD_DutyLibMapSchema, "DELETE&INSERT");

		return true;
	}

	private boolean modifyLib(String libTableName, Schema fromSchema,
			String dutyLibCode, String operator, String tableCode)
			throws Exception {

		Class schemaClass = Class.forName("com.sinosoft.lis.schema."
				+ libTableName + "Schema");
		Schema schema = (Schema) schemaClass.newInstance();

		PDGetMaxNo tPDGetMaxNo = new PDGetMaxNo();
		mGetDutyOrPayPlanCode2 = tPDGetMaxNo.getMaxNo("PDLibTableNo",
				libTableName);
		if (mGetDutyOrPayPlanCode2 == null) {
			this.mErrors.addOneError(tPDGetMaxNo.mErrors.getFirstError());
			return false;
		}

		SchemaUtilities schemaUtilities = new SchemaUtilities();
		schemaUtilities.convertData(fromSchema, schema);

		String selectFieldMapName = "select fieldcode1 from pd_fieldmap2 where tablecode1 = '"
				+ libTableName
				+ "' and tablecode2 = 'PD_DutyLibMap' and fieldcode2 = 'concdutycode'";
		String keyName = new ExeSQL().getOneValue(selectFieldMapName);

		if (dutyLibCode.equals("")) {
			schema.setV(keyName, mGetDutyOrPayPlanCode2);
		} else {
			schema.setV(keyName, dutyLibCode);
		}
		schema.setV("ModifyDate", PubFun.getCurrentDate());
		schema.setV("ModifyTime", PubFun.getCurrentTime());

		this.map.put(schema, operator);

		return true;
	}

	private boolean getInputData(VData cInputData) throws Exception {

		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mSysType = (String) mTransferData.getValueByName("SysType");
		mEnvType = (String) mTransferData.getValueByName("EnvType");
		mFromPath = (String) mTransferData.getValueByName("FromPath");
		mReleasePlatform = (String) mTransferData
				.getValueByName("ReleasePlatform");

		mRiskCode = (String) mTransferData.getValueByName("RiskCode");

		this.mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		return true;
	}

	/***************************************************************************
	 * create rate and cashvalue tables
	 * 
	 * @param riskCode
	 * @param tReleasePlatform
	 * @return
	 * @throws Exception
	 */
	public boolean createRateCVTables(String riskCode, String tReleasePlatform)
			throws Exception {
		String select = "select coretablename, tabletype, execsql from Pd_Ratecvsql where riskcode = '"
				+ riskCode + "' and sqltype = '1'";
		ExeSQL exec = new ExeSQL();
		SSRS ssrs = exec.execSQL(select);
		if (ssrs.MaxRow > 0) {
			for (int i = 1; i <= ssrs.MaxRow; i++) {
				String coreTableName = ssrs.GetText(i, 1);

				// create
				String createSQL = ssrs.GetText(i, 3);

				try {
					if(tDeployMode!=null && "0".equals(tDeployMode)){
						if (!execDDLByDBMSSQL(tReleasePlatform, coreTableName, createSQL)) {
							this.mErrors.addOneError(coreTableName + "表创建失败");
							return false;
						}
					}else{
						if (!execDDLLocal(coreTableName, createSQL)) {
							this.mErrors.addOneError(coreTableName + "表创建失败");
							return false;
						}
					}
				} catch (Exception ex) {
					this.mErrors.addOneError(coreTableName + "表创建失败:"
							+ ex.getMessage());
					return false;
				}
			}
		}

		return true;
	}

	public boolean execDDLByDBMSSQL(String dbLinkName, String sql)
			throws Exception {
		String dbmsSQL = "DECLARE    sqlstr  VARCHAR2(1000);    tCursor PLS_INTEGER;  RetVal  NUMBER; "
				+ " begin   sqlstr := '"
				+ sql
				+ "';         tCursor := dbms_sql.open_cursor@"
				+ dbLinkName
				+ "; "
				+ " dbms_sql.parse@"
				+ dbLinkName
				+ "(tCursor, sqlstr, dbms_sql.native);  RetVal := dbms_sql.execute@"
				+ dbLinkName
				+ "(tCursor); "
				+ " dbms_sql.close_cursor@"
				+ dbLinkName + "(tCursor);        END;";

		ExeSQL exec = new ExeSQL();

		if (!exec.execUpdateSQL(dbmsSQL)) {
			this.mErrors.addOneError(exec.mErrors.getFirstError());
			return false;
		}

		return true;
	}

	/**
	 * @author NicolE 重构execDDLByDBMSSQL(param1,param2);如果表已存在，则drop掉
	 * @param dbLinkName
	 * @param tableCode
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public boolean execDDLByDBMSSQL(String dbLinkName, String tableCode,
			String sql) throws Exception {
		
		// 必须转换成大写
		tableCode = tableCode.toUpperCase();
		
		String dbmsSQL = ""
				+ "	DECLARE    "
				+ "	  	sqlstr  VARCHAR2(1000);"
				+ "	  	tCursor PLS_INTEGER;"
				+ "	  	RetVal  NUMBER;"
				+ "	    NUM  NUMBER;"
				+ " BEGIN   "
				+ "		SELECT COUNT(1) INTO NUM FROM USER_TABLES WHERE TABLE_NAME ='" + tableCode + "';"
				+ " 	IF NUM = 0 THEN "
				+ "			EXECUTE IMMEDIATE '"+sql+"';"
				+ "		END IF; "
				+ "		SELECT COUNT(1) INTO NUM FROM USER_TABLES@"+dbLinkName+" WHERE TABLE_NAME ='" + tableCode + "';"
				+ " 	IF NUM > 0 THEN "
				+ "		    sqlstr  := 'DROP TABLE "+tableCode+"'; "
				+ "		    tCursor := dbms_sql.open_cursor@" + dbLinkName+ "; "
				+ " 	    dbms_sql.parse@" + dbLinkName + "(tCursor, sqlstr, dbms_sql.native); "
				+ " 	    RetVal  := dbms_sql.execute@" + dbLinkName + "(tCursor); " 
				+ " 	    dbms_sql.close_cursor@"	+ dbLinkName + "(tCursor);"
				+ "		END IF; "
				+ "		sqlstr  := '"+ sql + "'; "
				+ "		tCursor := dbms_sql.open_cursor@" + dbLinkName+ "; "
				+ " 	dbms_sql.parse@" + dbLinkName + "(tCursor, sqlstr, dbms_sql.native); "
				+ " 	RetVal  := dbms_sql.execute@" + dbLinkName + "(tCursor); " 
				+ " 	dbms_sql.close_cursor@"	+ dbLinkName + "(tCursor);"
				+ "	END;";

		ExeSQL exec = new ExeSQL();
		if (!exec.execUpdateSQL(dbmsSQL)) {
			this.mErrors.addOneError(exec.mErrors.getFirstError());
			return false;
		}

		return true;
	}
	
	/**
	 * @author 创建本地库的数据库表，不需要dblink;如果表已存在，则drop掉
	 * @param tableCode
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public boolean execDDLLocal(String tableCode, String sql) throws Exception {
		
		// 必须转换成大写
		tableCode = tableCode.toUpperCase();
		
		String dbmsSQL = ""
				+ "	DECLARE    "
				+ "	  	sqlstr  VARCHAR2(1000);"
				+ "	  	tCursor PLS_INTEGER;"
				+ "	  	RetVal  NUMBER;"
				+ "	    NUM  NUMBER;"
				+ " BEGIN   "
				+ "		SELECT COUNT(1) INTO NUM FROM USER_TABLES WHERE TABLE_NAME ='" + tableCode + "';"
				+ " 	IF NUM = 0 THEN "
				+ "			EXECUTE IMMEDIATE '"+sql+"';"
				+ "		END IF; "
				+ "	END;";

		ExeSQL exec = new ExeSQL();
		if (!exec.execUpdateSQL(dbmsSQL)) {
			this.mErrors.addOneError(exec.mErrors.getFirstError());
			return false;
		}

		return true;
	}

	/***************************************************************************
	 * 是否存在问题件
	 * 
	 * @param data
	 * @return
	 */
	private boolean hasIssue(VData data) {
		String riskCode = (String) ((TransferData) data.getObjectByObjectName(
				"TransferData", 0)).getValueByName("RiskCode");
		String selectIssue = "select 1 from dual where exists (select 1 from PD_Issue where ISSUESTATE in ('0','1') and riskcode = '"
				+ riskCode + "')";

		ExeSQL exec = new ExeSQL();
		String result = exec.getOneValue(selectIssue);
		if (result.equals("1")) {
			return true;
		}

		return false;
	}

	/***************************************************************************
	 * 将理赔赔付算法中用到的要素存储在PD_LMCalFactorSort
	 * 
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	private boolean setCalFactorSort(String riskCode) throws Exception {
		String sql = "select distinct d.calsql "
				+ " from pd_lmcalmode d, Pd_Lmdutygetclm c, Pd_Lmdutyget b, Pd_Lmriskduty a "
				+ " where b.dutycode = a.Dutycode "
				+ " and b.getdutycode = c.getdutycode "
				+ " and c.Calcode = d.calcode " + " and a.riskcode = '"
				+ riskCode + "' ";

		SSRS ssrs = new ExeSQL().execSQL(sql);

		if (ssrs.MaxRow > 0) {
			for (int i = 1; i <= ssrs.MaxRow; i++) {
				String calsql = ssrs.GetText(i, 1);

				String tSQLSepaChar = "?";
				String tParaStr, tStr1;
				while (true) {
					tParaStr = PubFun.getStr(calsql, 2, tSQLSepaChar);
					if (tParaStr.equals("")) {
						break;
					} else {
						tStr1 = tSQLSepaChar + tParaStr.trim() + tSQLSepaChar;
						// 替换变量
						calsql = StrTool.replaceEx(calsql, tStr1, "");

						PD_LMCalFactorSortSchema aPD_LMCalFactorSortSchema = new PD_LMCalFactorSortSchema();
						aPD_LMCalFactorSortSchema.setDutyCode("000000");
						aPD_LMCalFactorSortSchema.setGetDutyCode("000000");
						aPD_LMCalFactorSortSchema.setFactorType("3");
						aPD_LMCalFactorSortSchema.setRiskCode(riskCode);
						aPD_LMCalFactorSortSchema
								.setFactorName(tParaStr.trim());
						aPD_LMCalFactorSortSchema.setFactorSubType("0");

						this.map
								.put(aPD_LMCalFactorSortSchema, "DELETE&INSERT");
					}
				}
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			MMap tmap = new MMap();
			tmap.add(this.map);

			for (int i = 0; i < mResult.size(); i++) {
				VData tData = new VData();
				tData = (VData) mResult.get(i);
				MMap map = (MMap) tData.getObjectByObjectName("MMap", 0);
				tmap.add(map);
			}

			mResultMaps.add(tmap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean check(VData cInputData, String cOperate) {

		if (hasIssue(cInputData)) {
			this.mErrors.addOneError("有未处理完毕的问题件，请先处理!");
			return false;
		}

		return true;
	}
	public boolean copyFileToPub(){
		
		BufferedReader br = null;
		BufferedWriter bw = null;
		String sql="select filepath from pd_lmriskfile where riskcode='"+mRiskCode+"'";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS ssrs =null;
		ssrs =tExeSQL.execSQL(sql);
		if(ssrs==null)return false;
		for(int i =1;i<=ssrs.MaxRow;i++){
			String fromfile = ssrs.GetText(i, 1);
			System.out.println(mFromPath);
			//String fromfile ="rule/11 08.pdm";
			String fromfileArr[] = fromfile.split("/");
			fromfile = mFromPath + fromfile;
			fromfile = fromfile.replaceAll("\\\\", "/");
			fromfile = fromfile.replaceAll("//", "/");
			File fFrom = new File(fromfile);
			
			String tFilePath = PubFun.getProductDeployPath();
			if (tFilePath == null || tFilePath.equals("")) {
				return false;
			}
			String pathr = PubFun.getClassPath();
			tFilePath = pathr + tFilePath;
			if (!tFilePath.endsWith("/"))
				tFilePath += "/";
			tFilePath = tFilePath+fromfileArr[0];
			tFilePath = tFilePath.replaceAll("\\\\", "/");
			tFilePath = tFilePath.replaceAll("//", "/");
			if (!tFilePath.endsWith("/"))
				tFilePath += "/";
			File fTo = new File(tFilePath);
			if (!fTo.exists()){
				fTo.mkdirs();
			}
			tFilePath = tFilePath + fromfileArr[1];
			fTo = new File(tFilePath);
			System.out.println(fromfile);
			System.out.println(tFilePath);
			try{
				br = new BufferedReader(new InputStreamReader(new FileInputStream(fFrom),"GBK"));
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fTo), "GBK"));
				String myreadline;    //定义一个String类型的变量,用来每次读取一行
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                bw.write(myreadline); //写入文件
	                bw.newLine();
	            }
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return true;
	}

	public static void main(String[] args) {
		try {

			//System.out.println(Escape.unescape(("StartDate=1234&EndDate=1234&btnSubmit=%B4%F2%D3%A1%B1%A8%B1%ED")));
			// String s = "select * from PD_LMDutyPay where DutyCode in (select
			// dutycode from PD_LMRiskDuty where riskcode =Trim('@RISKCODE@'))";
			//
			// sql.replaceAll("@RISKCODE@", this.mRiskCode);

			// GlobalInput tG = new GlobalInput();
			// tG.Operator = "001";
			// TransferData transferData = new TransferData();

			// transferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
			// transferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
			// transferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
			// transferData.setNameAndValue("RiskCode", "4058");
			// transferData.setNameAndValue("RequDate",request.getParameter("RequDate"));
			// transferData.setNameAndValue("tableName",request.getParameter("tableName"));
			// transferData.setNameAndValue("DeplDate",
			// PubFun.getCurrentDate());
			// transferData.setNameAndValue("Operator", tG.Operator);
			// transferData.setNameAndValue("ReleasePlatform", "devlis");
			// transferData.setNameAndValue("PlatformType", "1");
			// transferData.setNameAndValue("flag", "N"); // 工作流组件不提交数据
			// transferData.setNameAndValue("IsDeleteCoreDataBeforeInsert",
			// "1");

			// VData tVData = new VData();

			// tVData.add(tG);
			// tVData.add(transferData);

			// PDTestDeployBL pDTestDeployBL = new PDTestDeployBL();
			// pDTestDeployBL.getInputData(tVData);
			// pDTestDeployBL.recordReleaseTrace(tVData);

			// pDTestDeployBL.submitData2(tVData, "sdj");

			String select = "select coretablename, tabletype, execsql from Pd_Ratecvsql where riskcode = '4059' and sqltype = '1'";
			ExeSQL exec = new ExeSQL();
			SSRS ssrs = exec.execSQL(select);
			if (ssrs.MaxRow > 0) {
				for (int i = 1; i <= ssrs.MaxRow; i++) {
					String coreTableName = ssrs.GetText(i, 1);

					String createSQL = "drop table " + coreTableName + ";";

					try {
						if (!exec.execUpdateSQL(createSQL)) {
							System.out.println(coreTableName + "表Drop失败");
						} else {
							System.out.println(coreTableName + "表Drop成功");
						}
					} catch (Exception ex) {
						ex.printStackTrace();

					}
				}
				for (int i = 1; i <= ssrs.MaxRow; i++) {
					String coreTableName = ssrs.GetText(i, 1);

					// create
					String createSQL = ssrs.GetText(i, 3);
					createSQL = "drop table " + coreTableName + ";commit;"
							+ createSQL;

					try {
						if (!exec.execUpdateSQL(createSQL)) {
							System.out.println(coreTableName + "表创建失败");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
