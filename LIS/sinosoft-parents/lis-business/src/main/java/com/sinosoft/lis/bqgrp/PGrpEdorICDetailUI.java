package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:团体保单被保险人资料变更项目明细功能类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Tjj
 * @ReWrite ZhangRong
 * @version 1.0
 */
public class PGrpEdorICDetailUI implements BusinessService
{
private static Logger logger = Logger.getLogger(PGrpEdorICDetailUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public PGrpEdorICDetailUI()
    {}

    /**
       传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        PGrpEdorICDetailBL tPGrpEdorICDetailBL = new PGrpEdorICDetailBL();
        logger.debug("---UI BEGIN---" + mOperate);
        if (tPGrpEdorICDetailBL.submitData(cInputData, mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPGrpEdorICDetailBL.mErrors);
            return false;
        }
        else
        {
            mResult = tPGrpEdorICDetailBL.getResult();
        }
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    public static void main(String[] args)
    {
        VData tInputData = new VData();
        GlobalInput tGlobalInput = new GlobalInput();
        PGrpEdorICDetailUI aPGrpEdorICDetailUI = new PGrpEdorICDetailUI();
        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
        LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
        LPBnfSet tLPBnfSet = new LPBnfSet();
        tGlobalInput.ManageCom = "86110000";
        tGlobalInput.Operator = "001";

		tLPGrpEdorItemSchema.setEdorAcceptNo("86110000000046");
		tLPGrpEdorItemSchema.setEdorNo("410110000000046");
		tLPGrpEdorItemSchema.setEdorType("IC");
		tLPGrpEdorItemSchema.setGrpContNo("240110000000159");
		//个人保单批改信息
		tLPEdorItemSchema.setEdorAcceptNo("86110000000046");
		tLPEdorItemSchema.setGrpContNo("240110000000159");
		tLPEdorItemSchema.setContNo("230110000000181");
		tLPEdorItemSchema.setEdorNo("410110000000046");
		tLPEdorItemSchema.setEdorType("IC");
		tLPEdorItemSchema.setInsuredNo("0000387300");

		tLPInsuredSchema.setGrpContNo("240110000000159");
		tLPInsuredSchema.setContNo("230110000000181");
		tLPInsuredSchema.setEdorNo("410110000000046");
		tLPInsuredSchema.setEdorType("IC");
		tLPInsuredSchema.setInsuredNo("0000387300");

		tLPInsuredSchema.setName("df");
		tLPInsuredSchema.setSex("0");
		tLPInsuredSchema.setBirthday("1981-01-01");
		tLPInsuredSchema.setIDType("4");
		tLPInsuredSchema.setIDNo("33123");

        tInputData.addElement(tLPEdorItemSchema);
        tInputData.addElement(tLPGrpEdorItemSchema);
        tInputData.addElement(tLPInsuredSchema);
        tInputData.addElement(tGlobalInput);
        aPGrpEdorICDetailUI.submitData(tInputData, "INSERT||GRPMAIN");

    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
