package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPAppntIndSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:被保险人资料变更功能类
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Tjj
 * @version 1.0
 */
public class PGrpEdorAADetailUI implements BusinessService
{
private static Logger logger = Logger.getLogger(PGrpEdorAADetailUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public PGrpEdorAADetailUI()
    {}

    /**
       传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        PGrpEdorAADetailBLF tPGrpEdorAADetailBLF = new PGrpEdorAADetailBLF();
        logger.debug("---PGrpEdorAADetailUI BEGIN---" + mOperate);
        if (tPGrpEdorAADetailBLF.submitData(cInputData, mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPGrpEdorAADetailBLF.mErrors);
            CError tError = new CError();
            tError.moduleName = "PEdorPTDetailUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据查询失败!";
            this.mErrors.addOneError(tError);
            mResult.clear();
            return false;
        }
        else
            mResult = tPGrpEdorAADetailBLF.getResult();
       
        mResult.clear();
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    public static void main(String[] args)
    {
        LPAppntIndSchema tLPAppntIndSchema = new LPAppntIndSchema();
        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        PGrpEdorAADetailUI tPGrpEdorAADetailUI = new PGrpEdorAADetailUI();
        String transact = "INSERT||MAIN";
//      logger.debug("------transact:"+transact);
        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ManageCom = "86";

        String polNo = "210110000001001";
        String edorNo = "410000000000386";
        String edorType = "PT";
        String contNo = "230110000000461";
        String InsuredNo = "0000491300";
        String edorAcceptNo = "86000000000364";
        //个人批改信息
        tLPEdorItemSchema.setPolNo(polNo);
        tLPEdorItemSchema.setEdorNo(edorNo);
        tLPEdorItemSchema.setEdorType(edorType);
        tLPEdorItemSchema.setInsuredNo(InsuredNo);
        tLPEdorItemSchema.setEdorAcceptNo(edorAcceptNo);
        tLPEdorItemSchema.setContNo(contNo);
        try
        {
            // 准备传输数据 VData

            VData tVData = new VData();

            //保存个人保单信息(保全)
            tVData.addElement(tG);
            tVData.addElement(tLPEdorItemSchema);

            LPPolSchema tLPPolSchema = new LPPolSchema();
            tLPPolSchema.setPolNo(polNo);
            tLPPolSchema.setEdorNo(edorNo);
            tLPPolSchema.setEdorType(edorType);
            tLPPolSchema.setAmnt(1);
            tLPPolSchema.setMult(1);

            tVData.addElement(tLPPolSchema);

            logger.debug("start UI....");
            tPGrpEdorAADetailUI.submitData(tVData, transact);

        }
        catch (Exception ex)
        {
//                        Content = transact + "失败，原因是:" + ex.toString();
//                        FlagStr = "Fail";
        }

    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
