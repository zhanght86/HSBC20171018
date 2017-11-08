package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.db.FICodeTransDB;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author jw
 * @version 1.0
 */


public class Face //implements DistillType
{
private static Logger logger = Logger.getLogger(Face.class);

    GlobalInput mGlobalInput = new GlobalInput();
    String StartDate = "";
    String EndDate = "";
    String BatchNo = "";
    String VersionNo = "";
    private LogInfoDeal m_oLogInfoDeal = null;
    public Face() {
    }

    /**
     * DitillInfo
     *
     * @param cInputData VData
     * @return FIAboriginalDataSet
     * @throws Exception
     * @todo Implement this com.sinosoft.lis.fininterface.DistillType method
     */
    public FIAboriginalDataSet DitillInfo(VData cInputData) throws Exception {
        if (!getInputData(cInputData)) {
            return null;
        }
        return DealWithData();
    }

    private FIAboriginalDataSet DealWithData() {
        FIAboriginalDataSet d = new FIAboriginalDataSet();
        return d;
    }

    private boolean getInputData(VData cInputData) {
//        m_oLogInfoDeal.printlog("start...getInputData()...");
//        m_oLogInfoDeal.complete(true);
        mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0);
        StartDate = (String) cInputData.get(1);
        EndDate = (String) cInputData.get(2);
        BatchNo = (String) cInputData.get(3);
        VersionNo = (String) cInputData.get(4);
        m_oLogInfoDeal = (LogInfoDeal) cInputData.getObjectByObjectName(
                "LogInfoDeal", 0);

        if (mGlobalInput == null) {
            //m_oLogInfoDeal.printlog("前台传入用户信息为空");

            return false;
        }
        if (StartDate == null && StartDate.equals("")) {
            //m_oLogInfoDeal.printlog("没有起始日期");

            return false;
        }
        if (EndDate == null && EndDate.equals("")) {
            //m_oLogInfoDeal.printlog("没有终止日期");

            return false;
        }
        if (BatchNo == null && BatchNo.equals("")) {
           // m_oLogInfoDeal.printlog("没有批次号码");

            return false;
        }
        if (VersionNo == null && VersionNo.equals("")) {
            //m_oLogInfoDeal.printlog("没有版本号码");

            return false;
        }
        if (m_oLogInfoDeal == null) {
          //  m_oLogInfoDeal.printlog("没有日志对象");

            return false;
        }

        return true;
    }

    /**
     * haveDataUnsettled
     *
     * @param cInputData VData
     * @return boolean
     * @throws Exception
     * @todo Implement this com.sinosoft.lis.fininterface.DistillType method
     */
    public boolean haveDataUnsettled(VData cInputData) throws Exception {
        return false;
    }

    public static void main(String[] args)
    {




    }
}
