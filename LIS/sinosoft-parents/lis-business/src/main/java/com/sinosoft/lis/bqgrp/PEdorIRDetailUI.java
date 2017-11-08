/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.*;

import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;

import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.Reflections;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCCustomerImpartDetailSet;
import com.sinosoft.lis.pubfun.PubFun;

/*
 * <p>ClassName: PEdorIRDetailUI </p>
 * <p>Description: PEdorIRDetailUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-4-13 10:09:44
 */
public class PEdorIRDetailUI
{
private static Logger logger = Logger.getLogger(PEdorIRDetailUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();


    /** 往后面传输数据的容器 */
//    private VData mInputData = new VData();


    /** 数据操作字符串 */
    private String mOperate;


//业务处理相关变量
    /** 全局数据 */
//    private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
    public PEdorIRDetailUI()
    {
    }


    /**
     * 传输数据的公共方法
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        PEdorIRDetailBL tPEdorIRDetailBL = new PEdorIRDetailBL();

//        logger.debug("Start OLCInsured UI Submit...");
        tPEdorIRDetailBL.submitData(cInputData, mOperate);
        //logger.debug("End OLCInsured UI Submit...");
        //如果有需要处理的错误，则返回
        if (tPEdorIRDetailBL.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPEdorIRDetailBL.mErrors);
            return false;
        }
        if (!mOperate.equals("DELETE||CONTINSURED"))
        {
            this.mResult.clear();
            this.mResult = tPEdorIRDetailBL.getResult();
        }
//        mInputData = null;
        return true;
    }

    /**
     * 得到处理后的结果集
     * @return 结果集
     */
    public VData getResult()
    {
        return this.mResult;
    }

    public static void main(String[] args)
    {
        GlobalInput tg = new GlobalInput();
        tg.ManageCom = "86";
        tg.Operator = "001";
        String edorNo = "430110000000763";
        String edorType = "IR";
        String contNo = "230110000000355";
        String polNo = "000000";
        String insuredNo = "0000003330";
        String edorAcceptNo = "86110000000993";
        Reflections ref = new Reflections();
        LCContSchema tLCContSchema = new LCContSchema();
        LPContSchema tLPContSchema = new LPContSchema();
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(contNo);
        tLCContDB.getInfo();
        tLCContSchema = tLCContDB.getSchema();
        ref.transFields(tLPContSchema,tLCContSchema);
        tLPContSchema.setEdorNo(edorNo);
        tLPContSchema.setEdorType(edorType);
        tLPContSchema.setInsuredSex("0");



        LDPersonSchema tLDPersonSchema = new LDPersonSchema();
        LPPersonSchema tLPPersonSchema = new LPPersonSchema();
        LDPersonDB tLDPersonDB = new LDPersonDB();
        tLDPersonDB.setCustomerNo(insuredNo);
        tLDPersonDB.getInfo();
        ref.transFields(tLPPersonSchema,tLDPersonDB.getSchema());
        tLPPersonSchema.setEdorNo(edorNo);
        tLPPersonSchema.setEdorType(edorType);
        tLPPersonSchema.setSex("0");

        LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
        LCInsuredDB mLCInsuredDB = new LCInsuredDB();
        mLCInsuredDB.setContNo(contNo);
        mLCInsuredDB.setInsuredNo(insuredNo);
        mLCInsuredDB.getInfo();
        ref.transFields(tLPInsuredSchema,mLCInsuredDB.getSchema());
        tLPInsuredSchema.setEdorNo(edorNo);
        tLPInsuredSchema.setEdorType(edorType);
        tLPInsuredSchema.setSex("0");

        LCAddressSet mLCAddressSet = new LCAddressSet();
        LPAddressSchema mLPAddressSchema = new LPAddressSchema();
        LCAddressDB tLCAddressDB = new LCAddressDB();
        tLCAddressDB.setCustomerNo(insuredNo);
        mLCAddressSet = tLCAddressDB.query();
        if(mLCAddressSet.size()>0)
        ref.transFields(mLPAddressSchema,mLCAddressSet.get(1));
        mLPAddressSchema.setEdorNo(edorNo);
        mLPAddressSchema.setEdorType(edorType);

        LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB();
        LCCustomerImpartSet tLCCustomerImpartSet =new LCCustomerImpartSet();
        LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
        tLCCustomerImpartDB.setCustomerNo(insuredNo);
        tLCCustomerImpartDB.setCustomerNoType("1");
        tLCCustomerImpartDB.setContNo(contNo);
        tLCCustomerImpartSet = tLCCustomerImpartDB.query();
        if(tLCCustomerImpartSet.size()>0)
        ref.transFields(tLPCustomerImpartSchema,tLCCustomerImpartSet.get(1));
        tLPCustomerImpartSchema.setEdorNo(edorNo);
        tLPCustomerImpartSchema.setEdorType(edorType);

        LCCustomerImpartDetailDB tLCCustomerImpartDetailDB = new LCCustomerImpartDetailDB();
        LCCustomerImpartDetailSet tLCCustomerImpartDetailSet =new LCCustomerImpartDetailSet();
        LPCustomerImpartDetailSchema tLPCustomerImpartDetailSchema = new LPCustomerImpartDetailSchema();
        tLCCustomerImpartDetailDB.setCustomerNo(insuredNo);
        tLCCustomerImpartDetailDB.setCustomerNoType("1");
        tLCCustomerImpartDetailDB.setContNo(contNo);
        tLCCustomerImpartDetailSet = tLCCustomerImpartDetailDB.query();
        if(tLCCustomerImpartDetailSet.size()>0)
        ref.transFields(tLPCustomerImpartDetailSchema,tLCCustomerImpartDetailSet.get(1));
        tLPCustomerImpartDetailSchema.setEdorNo(edorNo);
        tLPCustomerImpartDetailSchema.setEdorType(edorType);



        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("ContNo", contNo);
        tTransferData.setNameAndValue("CustomerNo", insuredNo);

        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        tLPEdorItemSchema.setEdorNo(edorNo);
        tLPEdorItemSchema.setEdorType(edorType);
        tLPEdorItemSchema.setContNo(contNo);
        tLPEdorItemSchema.setInsuredNo(insuredNo);
        tLPEdorItemSchema.setPolNo(polNo);
        tLPEdorItemSchema.setEdorAcceptNo(edorAcceptNo);
        tLPEdorItemSchema.setOperator("001");
        tLPEdorItemSchema.setManageCom("86");
        tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
        tLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
        tLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
        tLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
        tLPEdorItemSchema.setEdorState("3");

        VData tVData = new VData();
        tVData.add(tLPEdorItemSchema);
        tVData.add(tLPContSchema);
        tVData.add(tLPPersonSchema);
        tVData.add(tLPInsuredSchema);
        tVData.add(mLPAddressSchema);
//        tVData.add(tLPCustomerImpartSet);
//        tVData.add(tLPCustomerImpartDetailSet);
        tVData.add(tTransferData);
//

        tVData.add(tg);
        PEdorIRDetailUI tPEdorIRDetailUI = new PEdorIRDetailUI();
        //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
        if (tPEdorIRDetailUI.submitData(tVData, "REPLACE||CONTINSURED"))
        {
            VData tempVData = tPEdorIRDetailUI.getResult();
        }

    }
}
