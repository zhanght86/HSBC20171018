/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDMaxNoRuleDB;
import com.sinosoft.lis.db.LDMaxNoRuleLimitDB;
import com.sinosoft.lis.db.LDMaxNoRuleLimitPropDB;
import com.sinosoft.lis.db.LDMaxNoRulePropDB;
import com.sinosoft.lis.schema.LDMaxNoRuleLimitSchema;
import com.sinosoft.lis.schema.LDMaxNoRuleSchema;
import com.sinosoft.lis.vschema.LDMaxNoRuleLimitPropSet;
import com.sinosoft.lis.vschema.LDMaxNoRuleLimitSet;
import com.sinosoft.lis.vschema.LDMaxNoRulePropSet;
import com.sinosoft.lis.vschema.LDMaxNoRuleSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/*
 * <p>Title: 险种相关信息缓存类 </p> <p>Description: 缓存程序中经常会用到的险种相关信息 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft</p> @author Kevin
 * 
 * @version 1.0 @date 2003-07-29
 */
public class CachedLDMaxNo {
private static Logger logger = Logger.getLogger(CachedLDMaxNo.class);
	// @Field
	public CErrors mErrors = new CErrors(); // 错误信息

	// 用来缓存信息的结构

	private  Hashtable m_hashRuleData = new Hashtable();

	// 静态变量
	private static String NOFOUND = "NOFOUND";
	private static CachedLDMaxNo m_cri = null;

	// @Constructor
	private CachedLDMaxNo() {
	}

	public static CachedLDMaxNo getInstance() {
		if (m_cri == null) {
			m_cri = new CachedLDMaxNo();
		}

		m_cri.mErrors.clearErrors();

		return m_cri;
	}

	public void refresh() {

		m_hashRuleData.clear();
		
	}

	public void refresh(String tKey) {

		m_hashRuleData.remove(tKey.toUpperCase());
		m_hashRuleData.remove(tKey.toUpperCase()+"LIMIT");
		
	}
	
	public VData findLDMaxNoRuleConfig(String tNoCode)
	{
		Hashtable hash = this.m_hashRuleData;

		Object obj = hash.get(tNoCode.toUpperCase());

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (VData) obj;
			}
		} else {
			//首先判断该号段是否已经注册过,如果没有,返回空
			String tSQL_check = "select 1 from LDMaxNoConfig where upper(nocode)='"+"?nocode?"+"' ";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSQL_check);
			sqlbv1.put("nocode", tNoCode.toUpperCase());
			ExeSQL tExeSQL = new ExeSQL();
			String tCheckValue = tExeSQL.getOneValue(sqlbv1);
			if(tCheckValue==null||tCheckValue.equals(""))
			{
				return null;
			}
			
			
			VData tVData = new VData();
			logger.debug("Rule_check..............");
			String tSQL = "select * from LDMaxNoRule where upper(nocode)='"+"?nocode1?"+"' order by idx";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tSQL);
			sqlbv2.put("nocode1", tNoCode.toUpperCase());
			LDMaxNoRuleSet tLDMaxNoRuleSet = new LDMaxNoRuleSet();
			LDMaxNoRuleDB tLDMaxNoRuleDB = new LDMaxNoRuleDB();
			tLDMaxNoRuleSet = tLDMaxNoRuleDB.executeQuery(sqlbv2);
			if(tLDMaxNoRuleSet.size()<=0)
			{
				return null;
			}
			logger.debug("end Rule_check..............");
			for(int i=1;i<=tLDMaxNoRuleSet.size();i++)
			{
				VData tempVData = new VData();
				LDMaxNoRuleSchema tempLDMaxNoRuleSchema = new LDMaxNoRuleSchema();
				tempLDMaxNoRuleSchema = tLDMaxNoRuleSet.get(i);
				tempVData.add(tempLDMaxNoRuleSchema);
				
				String tSQL_Prop = "select * from LDMaxNoRuleProp where rulecode='"+"?a1?"+"' "
				                 + " and upper(nocode)='"+"?nocode1?"+"' order by propcode ";
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql(tSQL_Prop);
				sqlbv3.put("a1", tempLDMaxNoRuleSchema.getRuleCode());
				sqlbv3.put("nocode1", tNoCode.toUpperCase());
				LDMaxNoRulePropSet tLDMaxNoRulePropSet = new LDMaxNoRulePropSet();
				LDMaxNoRulePropDB tLDMaxNoRulePropDB = new LDMaxNoRulePropDB();
				tLDMaxNoRulePropSet = tLDMaxNoRulePropDB.executeQuery(sqlbv3);
				Hashtable tPropHashTable = new Hashtable();
				for(int n=1;n<=tLDMaxNoRulePropSet.size();n++)
				{
					tPropHashTable.put(tLDMaxNoRulePropSet.get(n).getPropCode(),tLDMaxNoRulePropSet.get(n).getPropValue());
					
				}
				//tempVData.add(tLDMaxNoRulePropSet);
				tempVData.add(tPropHashTable);
				
				tVData.add(tempVData);
			}

			hash.put(tNoCode.toUpperCase(), tVData);
			return tVData;
		}
	}
	
	public VData findLDMaxNoRuleLimit(String tNoCode)
	{
		Hashtable hash = this.m_hashRuleData;

		Object obj = hash.get(tNoCode.toUpperCase()+"LIMIT");

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (VData) obj;
			}
		} else {
			//首先判断该号段是否已经注册过,如果没有,返回空
			logger.debug("tSQL_check..............");
			String tSQL_check = "select 1 from LDMaxNoConfig where upper(nocode)='"+"?nocode3?"+"' ";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSQL_check);
			sqlbv4.put("nocode3", tNoCode.toUpperCase());
			ExeSQL tExeSQL = new ExeSQL();
			String tCheckValue = tExeSQL.getOneValue(sqlbv4);
			if(tCheckValue==null||tCheckValue.equals(""))
			{
				return null;
			}
			logger.debug("end tSQL_check..............");
			
			VData tVData = new VData();
			String tSQL = "select * from LDMaxNoRuleLimit where upper(nocode)='"+"?nocode4?"+"' order by idx";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(tSQL);
			sqlbv5.put("nocode4", tNoCode.toUpperCase());
			LDMaxNoRuleLimitSet tLDMaxNoRuleLimitSet = new LDMaxNoRuleLimitSet();
			LDMaxNoRuleLimitDB tLDMaxNoRuleLimitDB = new LDMaxNoRuleLimitDB();
			tLDMaxNoRuleLimitSet = tLDMaxNoRuleLimitDB.executeQuery(sqlbv5);
			//约束数据如果为空,不返回null
			if(tLDMaxNoRuleLimitSet.size()<=0)
			{
				//return null;
				tVData = new VData();
				hash.put(tNoCode.toUpperCase()+"LIMIT", tVData);
				return tVData;
			}
			
			for(int i=1;i<=tLDMaxNoRuleLimitSet.size();i++)
			{
				VData tempVData = new VData();
				LDMaxNoRuleLimitSchema tempLDMaxNoRuleLimitSchema = new LDMaxNoRuleLimitSchema();
				tempLDMaxNoRuleLimitSchema = tLDMaxNoRuleLimitSet.get(i);
				tempVData.add(tempLDMaxNoRuleLimitSchema);
				
				String tSQL_Prop = "select * from LDMaxNoRuleLimitProp where rulecode='"+"?m1?"+"' "
				                 + " and upper(nocode)='"+"?m2?"+"' order by propcode ";
				SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
				sqlbv6.sql(tSQL_Prop);
				sqlbv6.put("m1", tempLDMaxNoRuleLimitSchema.getRuleCode());
				sqlbv6.put("m2", tNoCode.toUpperCase());
				LDMaxNoRuleLimitPropSet tLDMaxNoRuleLimitPropSet = new LDMaxNoRuleLimitPropSet();
				LDMaxNoRuleLimitPropDB tLDMaxNoRuleLimitPropDB = new LDMaxNoRuleLimitPropDB();
				tLDMaxNoRuleLimitPropSet = tLDMaxNoRuleLimitPropDB.executeQuery(sqlbv6);
				Hashtable tPropHashTable = new Hashtable();
				for(int n=1;n<=tLDMaxNoRuleLimitPropSet.size();n++)
				{
					tPropHashTable.put(tLDMaxNoRuleLimitPropSet.get(n).getPropCode(),tLDMaxNoRuleLimitPropSet.get(n).getPropValue());
					
				}
				//tempVData.add(tLDMaxNoRulePropSet);
				tempVData.add(tPropHashTable);
				
				tVData.add(tempVData);
			}

			hash.put(tNoCode.toUpperCase()+"LIMIT", tVData);
			return tVData;
		}
	}
		
	
}
