package com.sinosoft.lis.reagent;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.reagent.*;

public class SpePolBankTagUI {
private static Logger logger = Logger.getLogger(SpePolBankTagUI.class);
    public CErrors mErrors=new CErrors();
    private VData mInputData=new VData();
    public SpePolBankTagUI() {
    }
    public static void main(String[] args) {
    	SpePolBankTagUI tSpePolBankTagUI =new SpePolBankTagUI();
      VData tVData=new VData();
      GlobalInput tGI=new GlobalInput();
      LJSPaySet tLJSPaySet = new LJSPaySet();
      LJSPaySchema tLJSPaySchema = new LJSPaySchema();
      tLJSPaySchema.setOtherNo("86321220040210000979");
      tLJSPaySet.add(tLJSPaySchema);
      tVData.addElement(tLJSPaySet);
      tVData.add(tGI);
      tSpePolBankTagUI.submitData(tVData,"001");
    }
    public boolean submitData(VData cInputData,String cOperate)
    {
        mInputData=(VData)cInputData.clone();
        SpePolBankTagBL tSpePolBankTagBL=new SpePolBankTagBL();
        logger.debug("begin SpePolBankTagUI");
        if(tSpePolBankTagBL.submitData(mInputData,cOperate))
        {
          if(tSpePolBankTagBL.mErrors.needDealError())
          {
            this.mErrors.copyAllErrors(tSpePolBankTagBL.mErrors);
          }
        }
        else
        {
          this.mErrors.copyAllErrors(tSpePolBankTagBL.mErrors);
          CError tError=new CError();
          tError.moduleName="SpePolBankTagUI";
          tError.functionName="submitData";
          tError.errorMessage="提交数据失败";
          this.mErrors.addOneError(tError);
          return false;
        }
        logger.debug("end SpePolBankTagUI");
        mInputData=null;
        return true;
    }
}
