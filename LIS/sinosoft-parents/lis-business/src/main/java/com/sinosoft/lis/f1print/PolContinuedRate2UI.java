package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;
import com.sinosoft.utility.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: SinoSoft</p>
 * @author Fuqx
 * @version 1.0
 */

public class PolContinuedRate2UI
{
private static Logger logger = Logger.getLogger(PolContinuedRate2UI.class);

 public CErrors mErrors = new CErrors();
 private VData mResult = new VData();

 public PolContinuedRate2UI()
 {
 }

 public boolean submitData(VData cInputData, String cOperate)
 {
	 NewPolContinuedRate2BL tPolContinuedRate2BL = new NewPolContinuedRate2BL();
    if(!tPolContinuedRate2BL.submitData(cInputData,cOperate))
    {
     mErrors.copyAllErrors(tPolContinuedRate2BL.mErrors);
     return false;
    }
    else
    {
      mResult = (VData)tPolContinuedRate2BL.getResult();
    }
    return true;
 }
 public VData getResult()
 {
   return this.mResult;
  }
 public static void main(String[] args)
 {
  PolContinuedRate2UI PolContinuedRate2UI1 = new PolContinuedRate2UI();
 }
}
