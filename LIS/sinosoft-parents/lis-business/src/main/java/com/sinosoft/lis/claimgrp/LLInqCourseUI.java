package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLInqCourseSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author zhangxing
 * @version 1.0
 */


public class LLInqCourseUI
{
private static Logger logger = Logger.getLogger(LLInqCourseUI.class);


    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();

    public LLInqCourseUI()
    {}

    public static void main(String[] args)
    {
        CErrors tError = null;
        String FlagStr = "Fail";
        String Content = "";

        boolean flag = true;
        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ManageCom = "86110000";
        tG.ComCode = "86110000";

        //接收信息

       LLInqCourseSchema tLLInqCourseSchema = new LLInqCourseSchema();

        tLLInqCourseSchema.setInqDate("2005-05-12");
        tLLInqCourseSchema.setInqMode("1");
        tLLInqCourseSchema.setInqByPer("001");
        tLLInqCourseSchema.setInqCourse("zhuyuable dfdf");

        // 准备传输数据 VData
        VData tVData = new VData();

        tVData.add(tLLInqCourseSchema);

        tVData.add(tG);

        // 数据传输
        LLInqCourseUI tLLInqCourseUI = new LLInqCourseUI();
        if (!tLLInqCourseUI.submitData(tVData, ""))
        {

            int n = tLLInqCourseUI.mErrors.getErrorCount();
            Content = " 调查任务分配失败，原因是: " +
                      tLLInqCourseUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
        }

    }


    // @Method
    /**
     * 数据提交方法
     * @param: cInputData 传入的数据
     * cOperate 数据操作字符串
     * @return: boolean
     **/

    public boolean submitData(VData cInputData, String cOperate)
    {
        // 数据操作字符串拷贝到本类中
        this.mOperate = cOperate;

        LLInqCourseBL tLLInqCourseBL = new LLInqCourseBL();

        logger.debug("----UI BEGIN---");
        if (tLLInqCourseBL.submitData(cInputData, mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLInqCourseBL.mErrors);
            return false;
        }
        logger.debug("----UI END---");
        return true;
    }

}
