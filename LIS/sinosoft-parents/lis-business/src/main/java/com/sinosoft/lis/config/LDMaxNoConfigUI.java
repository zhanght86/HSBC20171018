/*
 * <p>ClassName: LDCurrencyConfigUI </p>
 * <p>Description:  LDCurrencyConfigUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database:

 */
package com.sinosoft.lis.config;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.sinosoft.lis.maxnomanage.CreateMaxNo;
import com.sinosoft.lis.maxnomanage.CreateMaxNoImp;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LDMaxNoConfigUI implements BusinessService {
	private static Logger logger = Logger.getLogger(LDMaxNoConfigUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量
	/** 全局数据 */
	public LDMaxNoConfigUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		this.mResult.clear();
		if(cOperate.equals("InitElement"))
		{
			InitElementBL  tInitElementBL = new InitElementBL();
			if(tInitElementBL.submitData(cInputData, cOperate))
			{
				this.mResult = tInitElementBL.getResult();
			}
		}
		else if(cOperate.equals("InitRule"))
		{
			InitLDMaxNoRuleBL  tInitLDMaxNoRuleBL = new InitLDMaxNoRuleBL();
			if(tInitLDMaxNoRuleBL.submitData(cInputData, cOperate))
			{
				this.mResult = tInitLDMaxNoRuleBL.getResult();
			}
		}
		else if(cOperate.equals("SaveLDMaxNoRule"))
		{
			LDMaxNoRuleBL tLDMaxNoRuleBL = new LDMaxNoRuleBL();
			if(!tLDMaxNoRuleBL.submitData(cInputData, cOperate))
			{
				this.mErrors = tLDMaxNoRuleBL.mErrors;
			
				return false;
			}
			else
			{
				return true;
			}
			
		}
		else if(cOperate.equals("INSERT||MAIN"))
		{
			LDMaxNoConfigBL tLDMaxNoConfigBL = new LDMaxNoConfigBL();
			if(!tLDMaxNoConfigBL.submitData(cInputData, cOperate))
			{
				this.mErrors = tLDMaxNoConfigBL.mErrors;
				return false;
			}
		}else if(cOperate.equals("previewRule"))
		{
			 String cNoType = (String)cInputData.get(1);
			 CreateMaxNo tC = new CreateMaxNoImp();
			 String tMaxNo = tC.getPreviewMaxNo(cNoType);
			 logger.debug("cNoType:"+cNoType+":tMaxNo:"+tMaxNo);
			 JSONArray jsonArray = new JSONArray();   
			 Map item = new HashMap();
			 item.put("type",cNoType);
			 item.put("value",tMaxNo);
			 jsonArray.add(0,item);
			 this.mResult.add(jsonArray);
		}
		else if(cOperate.equals("testRule"))
		{
			 String cNoType = (String)cInputData.get(1);
			 CreateMaxNo tC = new CreateMaxNoImp();
			 String tMaxNo = tC.getMaxNo(cNoType);
			 logger.debug("cNoType:"+cNoType+":tMaxNo:"+tMaxNo);
			 JSONArray jsonArray = new JSONArray();   
			 Map item = new HashMap();
			 item.put("type",cNoType);
			 item.put("value",tMaxNo);
			 jsonArray.add(0,item);
			 this.mResult.add(jsonArray);
		}
		
		
			
			//this.mResult = tLDCurrencyConfigBL.getResult();
			mInputData = null;
			return true;
	}

	public static void main(String[] args) {

	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
