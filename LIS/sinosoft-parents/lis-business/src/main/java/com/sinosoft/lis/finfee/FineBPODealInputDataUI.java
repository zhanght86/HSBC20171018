package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;
import com.sinosoft.utility.*;
/**
 * <p>Title: FineBPODealInputDataUI</p>
 * <p>Description: 录入外包数据导入处理</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SinoSoft</p>
 * @author ln
 * @version 1.0
 */

public class FineBPODealInputDataUI
{
private static Logger logger = Logger.getLogger(FineBPODealInputDataUI.class);

  /**错误处理类*/
  public CErrors mErrors = new CErrors();

  public FineBPODealInputDataUI()
  {
  }

  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   *         cOperate 数据操作
   * @return:
   */
  public boolean submitData(VData cInputData,String cOperate)
  {

    FineBPODealInputDataBL tFineBPODealInputDataBL = new FineBPODealInputDataBL();
    tFineBPODealInputDataBL.submitData(cInputData, cOperate);

    if (tFineBPODealInputDataBL.mErrors.needDealError())
    {
      this.mErrors.clearErrors();
      boolean flag;

      for (int i = 0; i < tFineBPODealInputDataBL.mErrors.getErrorCount(); i++)
      {
        flag = true;

        for (int j = 0; j < this.mErrors.getErrorCount(); j++)
        {
          if (tFineBPODealInputDataBL.mErrors.getError(i).errorMessage.equals(
              this.mErrors.getError(j).errorMessage))
        {
          flag = false;

          break;
        }
        }

        if (flag)
        {
          this.mErrors.addOneError(tFineBPODealInputDataBL.mErrors.getError(i));
        }
      }
      return false;
    }

    return true;
  }

  public static void main(String[] args)
  {
    VData cInputData = new VData();
	FineBPODealInputDataUI FineBPODealInputDataUI1 = new FineBPODealInputDataUI();
    FineBPODealInputDataUI1.submitData(cInputData, "");
  }
}
