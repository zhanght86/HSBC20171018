package com.sinosoft.lis.tb;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDRiskRuleDB;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDRiskRuleSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:产品上市停售校验类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.0
 */
public class ProductSaleControlBL implements BusinessService{
private static Logger logger = Logger.getLogger(ProductSaleControlBL.class);

	private LCPolSet mLCPolSet = new LCPolSet();
	
	//tongmeng 2008-12-13 add
	//增加对团险产品的上市停售控制
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/**
	 * sql绑定变量类
	 */
	private SQLwithBindVariables sqlbv= new SQLwithBindVariables();
	public ProductSaleControlBL() {
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProductSaleControlBL tProductSaleControlBL = new ProductSaleControlBL();
		VData tVData = new VData();
		// TODO Auto-generated method stub
//		LCPolDB tLCPolDB = new LCPolDB();
//		//20080909163800
//		tLCPolDB.setContNo("20081114000001");
//		LCPolSet tLCPolSet = new LCPolSet();
//		tLCPolSet = tLCPolDB.query();

//		tVData.add(tLCPolSet);
		
		//select * from lcgrppol where grpcontno='240110000000017'
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo("240110000000017");
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		tLCGrpPolSet = tLCGrpPolDB.query();
		tLCGrpPolSet.get(1).setSaleChnl("01");
		tVData.add(tLCGrpPolSet);
		
		
		if(!tProductSaleControlBL.submitData(tVData, ""))
		{
			int tCount = tProductSaleControlBL.mErrors.getErrorCount();
			for(int i=1; i <= tCount;i++)
			{
				logger.debug(tProductSaleControlBL.mErrors.getFirstError().toString());
			}
		}
		else
		{
			VData tRes = tProductSaleControlBL.getResult();
			logger.debug(tRes.size());
			for(int i=0;i<tRes.size();i++)
			{
				String tRe = (String)tRes.get(i);
				logger.debug("%%"+tRe);
			}
		}

	}

	/**
	 * 
	 * @param tInputVData
	 * @param tOperator
	 * @return
	 */
	public boolean submitData(VData tInputData,String tOperator)
	{
		try {
			//获取界面参数
			if(!this.getInputData(tInputData))
			{
				
				return false;
			}
			
			//准备校验数据
			//校验个险数据
			if (this.mLCPolSet!=null&&this.mLCPolSet.size() > 0) {
				if (!this.dealData()) {
					return false;
				}
			}
			//校验团险数据
			if(this.mLCGrpPolSet!=null&&this.mLCGrpPolSet.size() > 0)
			{
				if (!this.dealDataGrp()) {
					return false;
				}
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 团险产品上市停售校验
	 * @return
	 */
	private boolean dealDataGrp()
	{
		//循环校验
		for(int i=1;i<=this.mLCGrpPolSet.size();i++)
		{
			String tResult = "";
			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolSchema.setSchema(mLCGrpPolSet.get(i));
			
			//获取险种编码.销售渠道和管理机构所在机构组.
			String tRiskCode = tLCGrpPolSchema.getRiskCode();
			String tSaleChnl = tLCGrpPolSchema.getSaleChnl(); 
			String tManageCom = tLCGrpPolSchema.getManageCom();
			
			String tComGroup = "";
			
			if(tRiskCode==null||tRiskCode.equals(""))
			{
				CError.buildErr(this,"险种信息为空.");
				return false;
			}
			if(tSaleChnl==null||tSaleChnl.equals(""))
			{
				CError.buildErr(this,"销售渠道为空.");
				return false;
			}
			if(tManageCom==null||tManageCom.equals(""))
			{
				CError.buildErr(this,"管理机构为空.");
				return false;
			}
			
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
			if(!tLCGrpContDB.getInfo())
			{
				CError.buildErr(this, "查询"+tLCGrpPolSchema.getGrpContNo()+"合同信息失败");
				return false;
			}
			
			//查询机构组编码
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			String tSQL_ComGroup = "select 1,comgroup from ldcomtocomgroup where comcode ='?tManageCom1?' "
                                 + " union "
                                 + " select 2,comgroup from ldcomtocomgroup where comcode = '?tManageCom2?' "
                                 + " union "
                                 + " select 3,comgroup from ldcomtocomgroup where comcode = '?tManageCom3?' "
                                 + " union "
                                 + " select 4,comgroup from ldcomtocomgroup where comcode = '?tManageCom4?' "
                                 + " union "
                                 + " select 5,'' from dual ";
			sqlbv.sql(tSQL_ComGroup);
			sqlbv.put("tManageCom1", tManageCom);
			sqlbv.put("tManageCom2", tManageCom.substring(0,6));
			sqlbv.put("tManageCom3", tManageCom.substring(0,4));
			sqlbv.put("tManageCom4", tManageCom.substring(0,2));
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			logger.debug("tSQL_ComGroup:"+sqlbv.sql());
			tSSRS = tExeSQL.execSQL(sqlbv);
			Hashtable tHashtable_group = new Hashtable();
			if(tSSRS==null||tSSRS.getMaxRow()<=0)
			{
				CError.buildErr(this,"管理机构:"+tComGroup+"没有定义机构组!");
				return false;
			}
			for(int n=1;n<=tSSRS.getMaxRow();n++)
			{
				
				//tComGroup = tSSRS.GetText(n,2);
				tHashtable_group.put(tSSRS.GetText(n,2), tSSRS.GetText(n,2));
			}
			//查询险种上市停售限制.
			//获得投保单申请时间
			String tPolApplyDate = "";
			ExeSQL tGrpExeSQL = new ExeSQL();
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			String tSQLPolApplyDate = "select polapplydate from lcgrpcont where grpcontno='?GrpContNo?' ";
			sqlbv.sql(tSQLPolApplyDate);
			sqlbv.put("GrpContNo", tLCGrpPolSchema.getGrpContNo());
			tPolApplyDate = tGrpExeSQL.getOneValue(sqlbv);
			if(tPolApplyDate==null||tPolApplyDate.equals(""))
			{
				CError.buildErr(this,"该团单投保申请时间有误,请检查!");
				return false;
			}
			
			SSRS sScanDate_SSRS = new SSRS();
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
		    String sql = "select makedate,maketime from es_doc_main where doccode='?GrpContNo?' ";
		    sqlbv.sql(sql);
		    sqlbv.put("GrpContNo", tLCGrpPolSchema.getGrpContNo());
		    sScanDate_SSRS = tExeSQL.execSQL(sqlbv);
		    String scanDate = "";
		    String scanTime = "";
		    //scanDate = sScanDate_SSRS.g
		    logger.debug(scanDate);
		    if (sScanDate_SSRS.getMaxRow()<=0) {
		    	SSRS tempSSRS = new SSRS();
		      //无扫描件,取录入时间
		      //传入的一定是主险数据.所以只要根据保单号查即可
		    	scanDate = tLCGrpContDB.getMakeDate();
		    	scanTime = tLCGrpContDB.getMakeTime();
		    }
		    else
		    {
		    	scanDate = sScanDate_SSRS.GetText(1, 1);
		    	scanTime = sScanDate_SSRS.GetText(1, 2);
		    }
			
		    //循环hashtable判断
		    Enumeration eKey=tHashtable_group.keys(); 
		    boolean tCheckFlag = false;
			while(eKey.hasMoreElements()) 
			{ 
				String key=(String)eKey.nextElement();
				tComGroup = (String)tHashtable_group.get(key);
				logger.debug("key:"+key+"tComGroup:"+tComGroup);
			
				//查询险种限制规则表
				LDRiskRuleDB tLDRiskRuleDB = new LDRiskRuleDB();
				tLDRiskRuleDB.setComGroup(tComGroup);
				tLDRiskRuleDB.setRiskCode(tRiskCode);
				tLDRiskRuleDB.setSaleChnl(tSaleChnl);
				if(!tLDRiskRuleDB.getInfo())
				{
					//查询无结果,认为不允许销售
					if(tResult.equals(""))
					{
						tResult = "险种:"+tRiskCode+"销售渠道:"+tSaleChnl+"机构组:"+tComGroup+"没有定义销售规则,不允许销售";
					}
				}
				else
				{
					LDRiskRuleSchema tLDRiskRuleSchema = new LDRiskRuleSchema();
					tLDRiskRuleSchema = tLDRiskRuleDB.getSchema();
					if(tLDRiskRuleSchema.getEndPolApplyDate()==null||tLDRiskRuleSchema.getEndPolApplyDate().equals(""))
		    		{
						tLDRiskRuleSchema.setEndPolApplyDate("2999-12-01");
		    		}
					if(tLDRiskRuleSchema.getEndScanDate()==null||tLDRiskRuleSchema.getEndScanDate().equals(""))
					{
						tLDRiskRuleSchema.setEndScanDate("2999-12-01");
					}
					if(tLDRiskRuleSchema.getEndScanTime()==null||tLDRiskRuleSchema.getEndScanTime().equals(""))
					{
						tLDRiskRuleSchema.setEndScanTime("23:59:59");
					}
					//update by cxq 修改绑定变量，修改||为concat
					sqlbv = new SQLwithBindVariables();
					String tSQL = "select count(*) from dual where 1=1 "
		    		        	+ " and '?tPolApplyDate?'>='?StartPolApplyDate?' "
		    		        	+ " and '?tPolApplyDate?'<='?EndPolApplyDate?' "
		    		        	+ " and concat(concat('?scanDate?',' '),'?scanTime?')>= concat(concat('?StartScanDate?',' '),'?StartScanTime?') "
		    		        	+ " and concat(concat('?scanDate?',' '),'?scanTime?')<= concat(concat('?EndScanDate?',' '),'?EndScanTime?') "
		    		        	+ " ";
					sqlbv.sql(tSQL);
					sqlbv.put("tPolApplyDate", tPolApplyDate);
					sqlbv.put("StartPolApplyDate", tLDRiskRuleSchema.getStartPolApplyDate());
					sqlbv.put("EndPolApplyDate", tLDRiskRuleSchema.getEndPolApplyDate());
					sqlbv.put("scanDate", scanDate);
					sqlbv.put("scanTime", scanTime);
					sqlbv.put("StartScanDate", tLDRiskRuleSchema.getStartScanDate());
					sqlbv.put("StartScanTime", tLDRiskRuleSchema.getStartScanTime());
					sqlbv.put("EndScanDate", tLDRiskRuleSchema.getEndScanDate());
					sqlbv.put("EndScanTime", tLDRiskRuleSchema.getEndScanTime());
					ExeSQL tExeSQL_temp = new ExeSQL();
					String tValue = tExeSQL_temp.getOneValue(sqlbv);
					if(tValue==null||tValue.equals(""))
					{
						CError.buildErr(this,"校验销售规则出错!");
						return false;
					}
					if(Integer.parseInt(tValue)<=0)
					{
						tResult = "险种:"+tRiskCode+"销售渠道:"+tSaleChnl+"机构组:"+tComGroup+"销售限制,不允许销售";
					}
					else
					{
						tCheckFlag = true;
					}

				}
				logger.debug("tResult:"+tResult);
			}
		    if(!tCheckFlag)
		    {
		    	//this.mResult.add(i-1,tResult);
		    	this.mResult.add(tResult);
		    }
		}
		return true;
	}
	/**
	 * 业务数据处理
	 * @return
	 */
	private boolean dealData()
	{
		//循环校验
		for(int i=1;i<=this.mLCPolSet.size();i++)
		{
			String tResult = "";
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema.setSchema(mLCPolSet.get(i));
			
			//获取险种编码.销售渠道和管理机构所在机构组.
			String tRiskCode = tLCPolSchema.getRiskCode();
			String tSaleChnl = tLCPolSchema.getSaleChnl(); 
			String tManageCom = tLCPolSchema.getManageCom();
			
			String tComGroup = "";
			
			if(tRiskCode==null||tRiskCode.equals(""))
			{
				CError.buildErr(this,"险种信息为空.");
				return false;
			}
			if(tSaleChnl==null||tSaleChnl.equals(""))
			{
				CError.buildErr(this,"销售渠道为空.");
				return false;
			}
			if(tManageCom==null||tManageCom.equals(""))
			{
				CError.buildErr(this,"管理机构为空.");
				return false;
			}
			//查询机构组编码
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			String tSQL_ComGroup = "select 1,comgroup from ldcomtocomgroup where comcode ='?tManageCom1?' "
                                 + " union "
                                 + " select 2,comgroup from ldcomtocomgroup where comcode = '?tManageCom2?' "
                                 + " union "
                                 + " select 3,comgroup from ldcomtocomgroup where comcode = '?tManageCom3?' "
                                 + " union "
                                 + " select 4,comgroup from ldcomtocomgroup where comcode = '?tManageCom4?' "
                                 + " union "
                                 + " select 5,'' from dual where 1=2 ";
			sqlbv.sql(tSQL_ComGroup);
			sqlbv.put("tManageCom1", tManageCom);
			sqlbv.put("tManageCom2", tManageCom.substring(0,6));
			sqlbv.put("tManageCom3", tManageCom.substring(0,4));
			sqlbv.put("tManageCom4", tManageCom.substring(0,2));
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			logger.debug("tSQL_ComGroup:"+sqlbv.sql());
			tSSRS = tExeSQL.execSQL(sqlbv);
			Hashtable tHashtable_group = new Hashtable();
			if(tSSRS==null||tSSRS.getMaxRow()<=0)
			{
				CError.buildErr(this,"管理机构:"+tComGroup+"没有定义机构组!");
				return false;
			}
			for(int n=1;n<=tSSRS.getMaxRow();n++)
			{
				
				//tComGroup = tSSRS.GetText(n,2);
				tHashtable_group.put(tSSRS.GetText(n,2), tSSRS.GetText(n,2));
			}
			//查询险种上市停售限制.
			//获得投保单申请时间
			String tPolApplyDate = tLCPolSchema.getPolApplyDate();
			SSRS sScanDate_SSRS = new SSRS();
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
		    String sql = "select makedate,maketime from es_doc_main where doccode='?ContNo?' ";
		    sqlbv.sql(sql);
		    sqlbv.put("ContNo", tLCPolSchema.getContNo());
		    sScanDate_SSRS = tExeSQL.execSQL(sqlbv);
		    String scanDate = "";
		    String scanTime = "";
		    //scanDate = sScanDate_SSRS.g
		    logger.debug(scanDate);
		    if (sScanDate_SSRS.getMaxRow()<=0) {
		    	SSRS tempSSRS = new SSRS();
		      //无扫描件,取录入时间
		      //传入的一定是主险数据.所以只要根据保单号查即可
		    	scanDate = tLCPolSchema.getMakeDate();
		    	scanTime = tLCPolSchema.getMakeTime();
		    }
		    else
		    {
		    	scanDate = sScanDate_SSRS.GetText(1, 1);
		    	scanTime = sScanDate_SSRS.GetText(1, 2);
		    }
			
		    //循环hashtable判断
		    Enumeration eKey=tHashtable_group.keys(); 
		    boolean tCheckFlag = false;
			while(eKey.hasMoreElements()) 
			{ 
				String key=(String)eKey.nextElement();
				tComGroup = (String)tHashtable_group.get(key);
				logger.debug("key:"+key+"tComGroup:"+tComGroup);
			
				//查询险种限制规则表
				LDRiskRuleDB tLDRiskRuleDB = new LDRiskRuleDB();
				tLDRiskRuleDB.setComGroup(tComGroup);
				tLDRiskRuleDB.setRiskCode(tRiskCode);
				tLDRiskRuleDB.setSaleChnl(tSaleChnl);
				if(!tLDRiskRuleDB.getInfo())
				{
					//查询无结果,认为不允许销售
					if(tResult.equals(""))
					{
						tResult = "险种:"+tRiskCode+"销售渠道:"+tSaleChnl+"机构组:"+tComGroup+"没有定义销售规则,不允许销售";
						//tCheckFlag = false;
					}
				}
				else
				{
					LDRiskRuleSchema tLDRiskRuleSchema = new LDRiskRuleSchema();
					tLDRiskRuleSchema = tLDRiskRuleDB.getSchema();
					if(tLDRiskRuleSchema.getEndPolApplyDate()==null||tLDRiskRuleSchema.getEndPolApplyDate().equals(""))
		    		{
						tLDRiskRuleSchema.setEndPolApplyDate("2999-12-01");
		    		}
					if(tLDRiskRuleSchema.getEndScanDate()==null||tLDRiskRuleSchema.getEndScanDate().equals(""))
					{
						tLDRiskRuleSchema.setEndScanDate("2999-12-01");
					}
					if(tLDRiskRuleSchema.getEndScanTime()==null||tLDRiskRuleSchema.getEndScanTime().equals(""))
					{
						tLDRiskRuleSchema.setEndScanTime("23:59:59");
					}
		    	
					//update by cxq 修改绑定变量   ||替换concat
					sqlbv = new SQLwithBindVariables();
					String tSQL = "select count(*) from dual where 1=1 "
		    		        	+ " and '?tPolApplyDate?'>='?StartPolApplyDate?' "
		    		        	+ " and '?tPolApplyDate?'<='?EndPolApplyDate?' "
		    		        	+ " and concat(concat('?scanDate?',' '),'?scanTime?')>= concat(concat('?StartScanDate?',' '),'?StartScanTime?') "
		    		        	+ " and concat(concat('?scanDate?',' '),'?scanTime?')<= concat(concat('?EndScanDate?',' '),'?EndScanTime?') "
		    		        	+ " ";
					sqlbv.sql(tSQL);
					sqlbv.put("tPolApplyDate", tPolApplyDate);
					sqlbv.put("StartPolApplyDate", tLDRiskRuleSchema.getStartPolApplyDate());
					sqlbv.put("EndPolApplyDate", tLDRiskRuleSchema.getEndPolApplyDate());
					sqlbv.put("scanDate", scanDate);
					sqlbv.put("scanTime", scanTime);
					sqlbv.put("StartScanDate", tLDRiskRuleSchema.getStartScanDate());
					sqlbv.put("StartScanTime", tLDRiskRuleSchema.getStartScanTime());
					sqlbv.put("EndScanDate", tLDRiskRuleSchema.getEndScanDate());
					sqlbv.put("EndScanTime", tLDRiskRuleSchema.getEndScanTime());
					ExeSQL tExeSQL_temp = new ExeSQL();
					String tValue = tExeSQL_temp.getOneValue(sqlbv);
					if(tValue==null||tValue.equals(""))
					{
						CError.buildErr(this,"校验销售规则出错!");
						return false;
					}
					if(Integer.parseInt(tValue)<=0)
					{
						tResult = "险种:"+tRiskCode+"销售渠道:"+tSaleChnl+"机构组:"+tComGroup+"销售限制,不允许销售";
						//tCheckFlag = false;
					}
					else
					{
						tCheckFlag = true;
					}

				}
				logger.debug("tResult:"+tResult);
			}
		    if(!tCheckFlag)
		    {
		    	//this.mResult.add(i-1,tResult);
		    	this.mResult.add(tResult);
		    }
		}
		return true;
	}
	
	/**
	 * 获取界面参数
	 * @param tInputData
	 * @return
	 */
	private boolean getInputData(VData tInputData)
	{
		this.mLCPolSet = (LCPolSet) tInputData.getObjectByObjectName(
				"LCPolSet", 0);
		this.mLCGrpPolSet = (LCGrpPolSet) tInputData.getObjectByObjectName(
				"LCGrpPolSet", 0);
		return true;
	}
	
	/**
	 * 获得结果
	 * @return
	 */
	public VData getResult()
	{
		return this.mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
	
}
