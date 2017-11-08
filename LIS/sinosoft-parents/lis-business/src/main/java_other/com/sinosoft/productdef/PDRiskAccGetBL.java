

/**
 * <p>Title: PDRiskAccGet</p>
 * <p>Description: 给付账户定义</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-16
 */
 
package com.sinosoft.productdef;

import java.util.ArrayList;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


public class PDRiskAccGetBL  implements BusinessService{
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
 private RiskState mRiskState;
 private ArrayList mList = new ArrayList();
 public PDRiskAccGetBL() {
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
    
    VData cVData = new VData();
    mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0);
    TransferData cTransferData= new  TransferData();
    cVData.add(mGlobalInput);
    cTransferData.setNameAndValue("tableName", tTransferData.getValueByName("tableName"));
    ArrayList list = new ArrayList();
    list.add(tTransferData.getValueByName("GETDUTYCODE"));
    list.add(tTransferData.getValueByName("INSUACCNO"));
    list.add(tTransferData.getValueByName("RISKCODE"));
    list.add(tTransferData.getValueByName("DEFAULTRATE"));
    list.add(tTransferData.getValueByName("NEEDINPUT"));
    list.add(tTransferData.getValueByName("CALCODEMONEY"));
    list.add(tTransferData.getValueByName("DEALDIRECTION"));
    list.add(tTransferData.getValueByName("CALFLAG"));
    list.add(tTransferData.getValueByName("ACCCREATEPOS"));
    list.add(tTransferData.getValueByName("GETDUTYNAME"));
    cTransferData.setNameAndValue("list", list);
    cVData.add(cTransferData);
    CommonBase commonBase = new CommonBase();
    boolean result = commonBase.submitData(cVData, cOperate);
    
    if(!result)
    {
     this.mErrors.addOneError("PDRiskAccPayBL.submitData处理失败，" + commonBase.mErrors.getFirstError());
     return false;
    }
    
    String tRiskCode = "";
    //TransferData tTransferData =  (TransferData) cInputData.getObjectByObjectName(
//   			"TransferData", 0);
    if(tTransferData!=null)
    {
     tRiskCode =  (String)((TransferData)cInputData.getObjectByObjectName("TransferData", 0)).getValueByName("RiskCode");
   	mRiskState.setState(tRiskCode, "产品条款定义->账户给付定义","1");
    }
      
       return true;
}
 
 private boolean check()
 {
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
	
 public static void main(String[] args) {
 }
}

