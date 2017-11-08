/*
 * <p>ClassName: InitElementBL </p>
 * <p>Description: InitElementBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company:  </p>
 * @Database:
 * @CreateDate：2005-01-24 18:15:01
 */
package com.sinosoft.lis.config;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDMaxNoConfigDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDMaxNoRulePropSchema;
import com.sinosoft.lis.schema.LDMaxNoRuleSchema;
import com.sinosoft.lis.vschema.LDMaxNoConfigSet;
import com.sinosoft.lis.vschema.LDMaxNoRulePropSet;
import com.sinosoft.lis.vschema.LDMaxNoRuleSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class LDMaxNoConfigBL {
private static Logger logger = Logger.getLogger(LDMaxNoConfigBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	
	public LDMaxNoConfigBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
//		if (!getInputData(cInputData)) {
//			return false;
//		}
//		// 进行业务处理
//		if (!dealData()) {
//			// @@错误处理
//			CError.buildErr(this,"数据处理失败OLDUWUserBL-->dealData!");
//			return false;
//		}
		String tNoCode = "";
		LDMaxNoConfigSet tLDMaxNoConfigSet = new LDMaxNoConfigSet();
		tLDMaxNoConfigSet  = (LDMaxNoConfigSet)cInputData.getObjectByObjectName("LDMaxNoConfigSet", 0);
		for(int i=1;i<=tLDMaxNoConfigSet.size();i++)
		{
			tNoCode = tLDMaxNoConfigSet.get(i).getNoCode();
			if(tLDMaxNoConfigSet.get(i).getNoCode()==null||tLDMaxNoConfigSet.get(i).getNoName().equals("")
					||tLDMaxNoConfigSet.get(i).getNoName()==null||tLDMaxNoConfigSet.get(i).getNoName().equals("")
					||tLDMaxNoConfigSet.get(i).getStartDate()==null||tLDMaxNoConfigSet.get(i).getStartDate().equals(""))
			{
				CError.buildErr(this,"录入信息不全!");
				return false;
			}
		}
		
		MMap map = new MMap();
		
		String tDelete_LDMaxNoRuleProp = "delete from ldmaxnoconfig where nocode='"+"?tNoCode?"+"'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tDelete_LDMaxNoRuleProp);
		sqlbv1.put("tNoCode",tNoCode);
		map.put(sqlbv1,"DELETE");
		

		if(tLDMaxNoConfigSet.size()>0)
		{
			map.put(tLDMaxNoConfigSet,"DELETE&INSERT");
		}
		
		mResult.clear();
		mResult.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError.buildErr(this,"数据提交失败");
			return false;
		}
		
		
		return true;
	}


	public VData getResult() {
		return this.mResult;
	}
}
