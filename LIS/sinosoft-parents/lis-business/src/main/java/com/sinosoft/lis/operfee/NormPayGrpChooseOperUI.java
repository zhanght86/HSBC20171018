package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.operfee.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

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
public class NormPayGrpChooseOperUI
{
private static Logger logger = Logger.getLogger(NormPayGrpChooseOperUI.class);

    //业务处理相关变量
    private VData mInputData;
    public CErrors mErrors = new CErrors();

    public NormPayGrpChooseOperUI()
    {
    }

    public static void main(String[] args)
    {
        //集体保单表-放置集体保单号，交费日期，操作员，管理机构
        LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();

        LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();

        String Operator = "001"; //保存登陆管理员账号
        String ManageCom = "86110000"; //保存登陆区站,ManageCom=内存中登陆区站代码

        //表单中的隐藏字段
        String GrpPolNo = "86110020040220000049"; //集体保单号码
        String PayDate = "2004-9-25"; //交费日期
        String ManageFeeRate = "0"; //管理费比例

        tLCGrpPolSchema.setGrpPolNo(GrpPolNo);
        tLCGrpPolSchema.setPaytoDate(PayDate); //交至日期字段保存交费日期
        tLCGrpPolSchema.setManageFeeRate(ManageFeeRate);
        tLCGrpPolSchema.setManageCom(ManageCom);
        tLCGrpPolSchema.setOperator(Operator);

        VData tVData = new VData();
        tVData.addElement(tLCGrpPolSchema);

        NormPayGrpChooseOperUI tNormPayGrpChooseOperUI = new NormPayGrpChooseOperUI();

        //
        //for health:edit by guoxiang 2004-9-25 13:08  普通的为VERIFY
        //tNormPayGrpChooseOperUI.submitData(tVData,"VERIFY");
        //
        tNormPayGrpChooseOperUI.submitData(tVData, "HEATHVERIFY");
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();

        NormPayGrpChooseOperBL tNormPayGrpChooseOperBL = new NormPayGrpChooseOperBL();
        logger.debug("Start NormPayGrpChooseOper UI Submit...");
        tNormPayGrpChooseOperBL.submitData(mInputData, cOperate);

        logger.debug("End FinFeeVerify UI Submit...");

        mInputData = null;

        //如果有需要处理的错误，则返回
        if (tNormPayGrpChooseOperBL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tNormPayGrpChooseOperBL.mErrors);
            logger.debug("error num=" + mErrors.getErrorCount());

            return false;
        }

        return true;
    }
}
