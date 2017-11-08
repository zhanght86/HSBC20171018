package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 领取方式变更（保全项目代码：GM）功能类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author WuHao
 * @version 1.0
 */
public class GrpEdorGMDetailUI implements BusinessService{
private static Logger logger = Logger.getLogger(GrpEdorGMDetailUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 重算后的领取标准 */
    private String mStandMoney;

    public GrpEdorGMDetailUI() {
    }

    /**
     传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        GrpEdorGMDetailBL tGrpEdorGMDetailBL = new GrpEdorGMDetailBL();
        if (tGrpEdorGMDetailBL.submitData(cInputData, mOperate) == false) {
            // @@错误处理
            this.mErrors.copyAllErrors(tGrpEdorGMDetailBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "PEdorGMDetailUI";
            tError.functionName = "submitData";
            tError.errorMessage = "UI层数据提交失败!";
            this.mErrors.addOneError(tError);
            mResult.clear();
            return false;
        } else {
            mResult = tGrpEdorGMDetailBL.getResult();
        }
        mResult.clear();
        return true;
    }

    /**
     * 数据输出方法，供外界获取数据处理结果
     * @return 包含有数据查询结果字符串的VData对象
     */
    public VData getResult()
    {
        return mResult;
    }

    public String getStandMoney()
    {
        return this.mStandMoney;
    }


    public static void main(String[] args)
    {

        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        LPGetSchema tLPGetSchema = new LPGetSchema();
        LPDutySchema tLPDutySchema = new LPDutySchema();
        LPPolSchema tLPPolSchema = new LPPolSchema();
        //tLPEdorItemSchema.decode("86000000001527|||GB|||230110000000577|||||||||||0|0|0|0|bq|2005-07-01|13:50:16|2005 - 07 - 01 | 13 : 50 : 16 || || || | ");

        tLPEdorItemSchema.setEdorAcceptNo("6120010306000022");
        tLPEdorItemSchema.setEdorNo("6020010306000020");
        tLPEdorItemSchema.setEdorType("GM");
        tLPEdorItemSchema.setContNo("000000003102");
        tLPEdorItemSchema.setInsuredNo("0000550550");
        tLPEdorItemSchema.setPolNo("210110000005116");

        //tLPGetSchema.decode("|||||145000|145040|0|||0|0|||0||||0|0|0|0|||||||||||");
		tLPGetSchema.setGetDutyCode("618041");
		tLPGetSchema.setGetDutyKind("3");
        tLPGetSchema.setDutyCode("618000");
		tLPGetSchema.setContNo("000000003102");
		tLPGetSchema.setPolNo("210110000005116");
        tLPGetSchema.setEdorNo("6020010306000020");
        tLPGetSchema.setEdorType("GM");

        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ManageCom = "86";

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tLPEdorItemSchema);
        tVData.addElement(tLPGetSchema);

//        PEdorGMDetailUI tPEdorGMDetailUI = new PEdorGMDetailUI();
//        if (!tPEdorGMDetailUI.submitData(tVData, "UPDATE||MAIN"))
//        {
//            logger.debug(tPEdorGMDetailUI.mErrors.getErrContent());
//        }
//		else
//		{
//			logger.debug("GM Submit OK!");
//		}
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
