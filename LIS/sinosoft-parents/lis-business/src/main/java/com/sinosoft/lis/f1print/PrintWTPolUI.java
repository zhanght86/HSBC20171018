package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;
import com.sinosoft.utility.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: SinoSoft</p>
 * @author Fuqx
 * @version 1.0
 */

public class PrintWTPolUI
{
private static Logger logger = Logger.getLogger(PrintWTPolUI.class);

 public CErrors mErrors = new CErrors();
 private VData mResult = new VData();

 public PrintWTPolUI()
 {
 }

 public boolean submitData(VData cInputData, String cOperate)
 {
    PrintWTPolBL tPrintWTPolBL = new PrintWTPolBL();
    if(!tPrintWTPolBL.submitData(cInputData,cOperate))
    {
     mErrors.copyAllErrors(tPrintWTPolBL.mErrors);
     return false;
    }
    else
    {
      mResult = (VData)tPrintWTPolBL.getResult();
    }
    return true;
 }
 public VData getResult()
 {
   return this.mResult;
  }
 public static void main(String[] args)
 {
  PrintWTPolUI PrintWTPolUI1 = new PrintWTPolUI();
 }
}
