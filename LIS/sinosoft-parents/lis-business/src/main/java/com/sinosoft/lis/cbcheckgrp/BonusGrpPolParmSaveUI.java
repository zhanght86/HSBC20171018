package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BonusGrpPolParmSaveUI implements BusinessService
{
private static Logger logger = Logger.getLogger(BonusGrpPolParmSaveUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public BonusGrpPolParmSaveUI()
    {
    }
    public static void main(String[] args)
    {
        BonusGrpPolParmSaveUI bonusGrpPolParmSaveUI1 = new BonusGrpPolParmSaveUI();
    }

    /**
传输数据的公共方法
*/
    public boolean submitData(VData cInputData,String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        BonusGrpPolParmSaveBL tBonusGrpPolParmSaveBL = new BonusGrpPolParmSaveBL();

        logger.debug("---BonusGrpPolParmSaveBL UI BEGIN---");
        if (tBonusGrpPolParmSaveBL.submitData(cInputData,mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tBonusGrpPolParmSaveBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "BonusGrpPolParmSaveUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        mResult.clear();
        return true;
    }
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}
}
