

/**
 * <p>Title: PDDutyPayAddFee</p>
 * <p>Description: 险种责任加费定义</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-13
 */
 
package com.sinosoft.productdef;

import java.util.ArrayList;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


public class PDDutyPayAddFeeBL implements BusinessService {
 /** 错误处理类，每个需要错误处理的类中都放置该类 */
 public  CErrors mErrors=new CErrors();
 private VData mResult = new VData();
 /** 往后面传输数据的容器 */
 private VData mInputData= new VData();
 /** 全局数据 */
 private GlobalInput mGlobalInput =new GlobalInput() ;
 /** 数据操作字符串 */
 private String mOperate;
 /** 业务处理相关变量 */
 private MMap map=new MMap();
 
 public PDDutyPayAddFeeBL() {
 }

/**
* 传输数据的公共方法
 * @param: cInputData 输入的数据
   *         cOperate 数据操作
* @return:
*/
 public boolean submitData(VData cInputData,String cOperate)
 {
    if(!check())
    {
     return false;
    }
    TransferData tTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
    String mCalCodeType = (String)tTransferData.getValueByName("CalCodeType");
    ArrayList tList  = (ArrayList)tTransferData.getValueByName("list");
    String tCalCode = tList.get(4)==null?"":(String)tList.get(4);
    if(tCalCode.equals(""))
		{
			tCalCode = PubFun1.CreateRuleCalCode("PD",mCalCodeType);
			tList.set(4, tCalCode);
			this.mResult.add(0,tCalCode);
		}
		
		else
		{
			//校验算法类型和算法编码的关系
			if((tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("N"))
			||!tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("Y"))
			{
				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
				return false;
			}
			this.mResult.add(0,tCalCode);
		}
    //进行业务处理
 CommonBase commonBase = new CommonBase();
 boolean result = commonBase.submitData(cInputData, cOperate);
 
 if(!result)
 {
  this.mErrors.addOneError("PDDutyPayAddFeeBL.submitData处理失败，" + commonBase.mErrors.getFirstError());
  return false;
 }
 String tRiskCode =  (String)((TransferData)cInputData.getObjectByObjectName("TransferData", 0)).getValueByName("RiskCode");
	RiskState.setState(tRiskCode, "契约业务控制->险种责任加费定义","1");
    return true;
}
 public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}
 private boolean check()
 {
  return true;
 }

 public static void main(String[] args) {
 }
}
