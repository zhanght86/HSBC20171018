package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BonusNoticeGrpPolListUI
{
private static Logger logger = Logger.getLogger(BonusNoticeGrpPolListUI.class);


    public CErrors mErrors=new CErrors();
    private VData mResult = new VData();
    private GlobalInput mGlobalInput =new GlobalInput() ;
    private LOBonusGrpPolParmSchema mLOBonusGrpPolParmSchema = new LOBonusGrpPolParmSchema();

    public BonusNoticeGrpPolListUI()
    {
    }
    public static void main(String[] args)
    {
        BonusNoticeGrpPolListUI bonusNoticeGrpPolListUI1 = new BonusNoticeGrpPolListUI();
        LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema=new LOBonusGrpPolParmSchema();
        tLOBonusGrpPolParmSchema.setGrpPolNo("");
        tLOBonusGrpPolParmSchema.setFiscalYear("2003");
        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ComCode  = "86";

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tLOBonusGrpPolParmSchema);

        if( !bonusNoticeGrpPolListUI1.submitData(tVData,"CONFIRM") )
        {
            if( bonusNoticeGrpPolListUI1.mErrors.needDealError() )
            {
                logger.debug(bonusNoticeGrpPolListUI1.mErrors.getFirstError());
            }
            else
            {
                logger.debug("BonusNoticeGrpPolListUI发生错误，但是没有提供详细的出错信息");
            }
        }
        else
        {
            VData vData = bonusNoticeGrpPolListUI1.getResult();
            XmlExport xe = (XmlExport)vData.get(0);

            try
            {
                InputStream ins = xe.getInputStream();
                FileOutputStream fos = new FileOutputStream("LCPolData.xml");
                int n = 0;

                while( ( n = ins.read() ) != -1 )
                {
                    fos.write(n);
                }

                fos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public boolean submitData(VData cInputData, String cOperate) {
        logger.debug(cOperate);
        if( !cOperate.equals("CONFIRM") &&
            !cOperate.equals("PRINT")) {
            buildError("submitData", "不支持的操作字符串");
            return false;
        }

        if( !getInputData(cInputData) )
            return false;

        if( !dealData() )
            return false;

        VData vData = new VData();
        if( !prepareOutputData(vData) )
            return false;

        BonusNoticeGrpPolListBL tBonusNoticeGrpPolListBL = new BonusNoticeGrpPolListBL();
        logger.debug("Start BonusNoticeGrpUI Submit ...");

        if( !tBonusNoticeGrpPolListBL.submitData(vData, cOperate) ) {
            if( tBonusNoticeGrpPolListBL.mErrors.needDealError() ) {
                mErrors.copyAllErrors(tBonusNoticeGrpPolListBL.mErrors);
                return false;
            } else {
                buildError("submitData", "BonusNoticeGrpBL发生错误，但是没有提供详细的出错信息");
                return false;
            }
        } else {
            mResult = tBonusNoticeGrpPolListBL.getResult();
            return true;
        }
    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData(VData vData) {
        try {
            vData.clear();
            vData.add(mGlobalInput);
            vData.add(mLOBonusGrpPolParmSchema);
        } catch (Exception ex) {
            ex.printStackTrace();
            buildError("prepareOutputData", "发生异常");
            return false;
        }
        return true;
    }

    private boolean dealData()
    {
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        //全局变量
        mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
        mLOBonusGrpPolParmSchema.setSchema((LOBonusGrpPolParmSchema)cInputData.getObjectByObjectName("LOBonusGrpPolParmSchema",0));

        if( mLOBonusGrpPolParmSchema.getGrpPolNo() == null ) {
            buildError("getInputData", "没有得到足够的信息！");
            return false;
        }

        return true;
    }

    public VData getResult() {
        return this.mResult;
    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError( );

        cError.moduleName = "BonusNoticeGrpUI";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    public CErrors getErrors() {
        return mErrors;
    }


}
