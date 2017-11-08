package com.sinosoft.lis.reagent;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 在职单孤儿单应收清单生成</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 * @author duanyh
 * @version 1.0
 */
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.reagent.*;

public class LRPolListCDownUI {
private static Logger logger = Logger.getLogger(LRPolListCDownUI.class);
    public CErrors mErrors=new CErrors();
    private VData mInputData=new VData();

    public static void main(String[] args) {
      LRPolListCDownUI tLRPolListCDownUI =new LRPolListCDownUI();
      VData tVData=new VData();
      TransferData tTransferData =new TransferData();
      tTransferData.setNameAndValue("AgentCode","86110006");
      tTransferData.setNameAndValue("StartDate","2005-10-1");
      tTransferData.setNameAndValue("EndDate","2005-10-31");
      tTransferData.setNameAndValue("Type","1");
      tTransferData.setNameAndValue("Operator","111");
      GlobalInput tGI=new GlobalInput();
      tVData.add(tTransferData);
      tVData.add(tGI);
      tLRPolListCDownUI.submitData(tVData,"");
    }
    public boolean submitData(VData cInputData,String cOperate)
    {
	mInputData=(VData)cInputData.clone();
	LRPolListCDownBL tLRPolListCDownBL=new LRPolListCDownBL();
	logger.debug("begin LRPolListCDownUI");
	tLRPolListCDownBL.submitData(mInputData,cOperate);
	logger.debug("end LRPolListCDownUI");
	if(tLRPolListCDownBL.mErrors.needDealError())
	{
	    this.mErrors.copyAllErrors(tLRPolListCDownBL.mErrors);
	    CError tError=new CError();
	    tError.moduleName="LRPolListCDownUI";
	    tError.functionName="submitData";
	    tError.errorMessage="提交数据失败";
	    this.mErrors.addOneError(tError);
	    return false;
	}
	mInputData=null;
	return true;
    }
}
