

/**
 * <p>Title: PD_LMDutyGetAlive</p>
 * <p>Description: 责任给付生存定义</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-11
 */
 
package com.sinosoft.productdef;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


public class PD_LMDutyGetAliveBL  {
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
 
 public PD_LMDutyGetAliveBL() {
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
    
    //进行业务处理
 CommonBase commonBase = new CommonBase();
 boolean result = commonBase.submitData(cInputData, cOperate);
 
 if(!result)
 {
  this.mErrors.addOneError("PD_LMDutyGetAliveBL.submitData处理失败，" + commonBase.mErrors.getFirstError());
  return false;
 }
   
    return true;
}
 
 private boolean check()
 {
  return true;
 }

 public static void main(String[] args) {
 }
}
