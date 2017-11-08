  package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description:应交费用类（界面输入）（暂对个人）
 * 从错误对象处理类继承，用来保存错误对象,在每个类中都存在
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author HZM
 * @version 1.0
 */

public class PreParePayPersonUI implements BusinessService{
private static Logger logger = Logger.getLogger(PreParePayPersonUI.class);

  //业务处理相关变量	
  private VData mInputData ;
  public  CErrors mErrors=new CErrors();
      
  public PreParePayPersonUI() {
  }
  public static void main(String[] args) {
    PreParePayPersonUI PreParePayPersonUI1 = new PreParePayPersonUI();
  }

  //传输数据的公共方法
  public boolean submitData(VData cInputData,String cOperate)
  {
    //首先将数据在本类中做一个备份
    mInputData=(VData)cInputData.clone() ;
    
    PreParePayPersonBL tPreParePayPersonBL=new PreParePayPersonBL();
    logger.debug("Start PreParePayPerson UI Submit...");
    tPreParePayPersonBL.submitData(mInputData,cOperate);

    logger.debug("End PreParePayPerson UI Submit...");

    mInputData=null;
    //如果有需要处理的错误，则返回
    if (tPreParePayPersonBL .mErrors .needDealError() )
	        {this.mErrors .copyAllErrors(tPreParePayPersonBL.mErrors ) ;
	logger.debug("error num="+mErrors.getErrorCount());  	
	         return false; 
	        }
	    return true;
  }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mInputData;
	}	

} 
