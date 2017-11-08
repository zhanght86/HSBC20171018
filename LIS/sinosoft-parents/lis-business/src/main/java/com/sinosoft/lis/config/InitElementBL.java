/*
 * <p>ClassName: InitElementBL </p>
 * <p>Description: InitElementBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company:  </p>
 * @Database:
 * @CreateDate：2005-01-24 18:15:01
 */
package com.sinosoft.lis.config;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.vschema.LDCurrencySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class InitElementBL {
private static Logger logger = Logger.getLogger(InitElementBL.class);
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
	
	public InitElementBL() {
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
		String tLimitType = (String)cInputData.get(2);
		JSONArray jsonArray = new JSONArray();   
		String tSQL = "";
		logger.debug("tLimitType:"+tLimitType);
		if(tLimitType==null||tLimitType.equals("")||tLimitType.equals("0"))
		{
			tSQL = "select code,name,detail from LDMaxNoElement order by code";
		}
		else
		{
			tSQL = "select code,name,detail from LDMaxNoElement where code!='SerialNo' order by code";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		SSRS tSSRS = new SSRS();
		ExeSQL  tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		for(int i=1;i<=tSSRS.getMaxRow();i++)
		{
			Map item = new HashMap();
			
			item.put("code",tSSRS.GetText(i, 1));
			item.put("name",tSSRS.GetText(i, 2));
			item.put("detail",tSSRS.GetText(i, 3));
			
			//查询属性
			String tSQL_prop = "select propcode,propname,propvalue,modifyflag,showflag from  LDMaxNoElementProp "
				             + " where code='"+"?code?"+"' order by propcode";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL_prop);
			sqlbv2.put("code",tSSRS.GetText(i, 1));
			SSRS tempSSRS = new SSRS();
			tempSSRS = tExeSQL.execSQL(sqlbv2);
			
			Map tempitem = new HashMap();
			JSONArray tjsonArray = new JSONArray();   
			for(int n=1;n<=tempSSRS.getMaxRow();n++)
			{
				
				tempitem = new HashMap();
				tempitem.put("propcode",tempSSRS.GetText(n, 1));
				tempitem.put("propname",tempSSRS.GetText(n, 2));
				tempitem.put("propvalue",tempSSRS.GetText(n, 3));
				tempitem.put("modifyflag",tempSSRS.GetText(n, 4));
				tempitem.put("showflag",tempSSRS.GetText(n, 5));
				tjsonArray.add(n-1,tempitem);
			}
			item.put("prop",tjsonArray);
			//"editor":"text"
			//list.add(item);
			jsonArray.add(i-1,item);
		}
		
		this.mResult.add(jsonArray);
		
		return true;
	}


	public VData getResult() {
		return this.mResult;
	}
}
