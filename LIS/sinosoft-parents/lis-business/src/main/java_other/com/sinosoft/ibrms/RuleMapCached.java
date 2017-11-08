package com.sinosoft.ibrms;
import org.apache.log4j.Logger;


/*
 * <p>ClassName: GlobalPools </p>
 * <p>Description: 线程号与连接池映射缓存类 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @author tongmeng
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.GlobalPools;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

public final class RuleMapCached {
private static Logger logger = Logger.getLogger(RuleMapCached.class);

	private static RuleMapCached mRuleMapCached = null;
	// @Field
	public CErrors mErrors = new CErrors(); // 错误信息
	/* 保存线程号和连接池名映射 */
	private Hashtable mRuleMap = new Hashtable();
	private SSRS mRuleSSRS = new SSRS();
	
	private RuleMapCached() {
		//初始化所有的信息
		String tSQL_Map = " select language,msgtype,keyid,msg from ldmsginfo_bom" 
			            + " union "
			            + " select 'sysvar',sysvar,sysvar,sysvarvalue from ldsysvar where sysvar='ibrmsdeftype' ";
		SSRS tMapSSRS = new SSRS();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL_Map);
		ExeSQL tExeSQL = new ExeSQL();
		tMapSSRS = tExeSQL.execSQL(sqlbv);
		this.mRuleSSRS = tMapSSRS;
		for(int i=1;i<=tMapSSRS.getMaxRow();i++)
		{
			String tLan = tMapSSRS.GetText(i, 1);
			String tMsgType = tMapSSRS.GetText(i, 2);
			String tKeyId = tMapSSRS.GetText(i, 3);
			String tMsg = tMapSSRS.GetText(i, 4);
			mRuleMap.put(tKeyId+tLan, tMsgType+":"+tMsg);
		}
		
	}

	public  SSRS getRuleSSRS()
	{
		return mRuleSSRS;
	}
	public static RuleMapCached getInstance() {
		if (mRuleMapCached == null) {
			mRuleMapCached = new RuleMapCached();
		}

		mRuleMapCached.mErrors.clearErrors();
		return mRuleMapCached;
	}

	public String getMsg(String tKeyId,String tLanguage) {
		return this.mRuleMap.get(tKeyId+tLanguage) == null ? ""
				: (String) this.mRuleMap.get(tKeyId+tLanguage);

	}
	
	public String getMsg(String tKeyId,String tLanguage,String tDefalutValue) {
		String tValue =mRuleMap.get(tKeyId+tLanguage) == null ? ""
				: (String) this.mRuleMap.get(tKeyId+tLanguage);
		
		if(tValue.equals(""))
		{
			return tDefalutValue;
		}
		else
		{
			String[] temp = tValue.split(":");
			if(temp.length==1)
			{
				return tDefalutValue;
			}
			return tValue.split(":")[1];
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
