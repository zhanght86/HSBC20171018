package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.FIOperationLogSchema;
import com.sinosoft.lis.schema.FIOperationParameterSchema;
import com.sinosoft.lis.vschema.FIOperationParameterSet;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;

import java.io.File;

import com.sinosoft.lis.fininterface.GenTxtFile;

public class LogInfoDeal
{
private static Logger logger = Logger.getLogger(LogInfoDeal.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    private VData mInputData ;
    private String LogFileName = "";
    private String LogFilePath = "";

    private FIOperationLogSchema mFIOperationLogSchema = new FIOperationLogSchema();
    private FIOperationParameterSet mFIOperationParameterSet = new FIOperationParameterSet();

    public LogInfoDeal( String tOperator, String tEventType)  throws Exception
    {
        if(! this.InitInfo(tOperator,tEventType))
        {
            Exception tErr = new Exception(this.mErrors.getFirstError());
            throw tErr;
        }

    }

    private boolean InitInfo(String tOperator, String tEventType)
    {

        try
        {
            if (tOperator == null || tOperator.equals(""))
            {
                buildError("LogInfoDeal", "initInfo", "日志处理类初始化失败，传入操作员参数为空");
                return false;
            }
            if (tEventType == null || tEventType.equals(""))
            {
                buildError("LogInfoDeal", "initInfo", "日志处理类初始化失败，传入事件类型参数为空");
                return false;
            }

            String currentDate = PubFun.getCurrentDate();
            String currentTime = PubFun.getCurrentTime();

            String sql = "";
            ExeSQL tExeSQL = new ExeSQL();
            sql = "select code from ficodetrans where codetype = 'logfilepath'";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(sql);
            LogFilePath = tExeSQL.getOneValue(sqlbv);
            if (LogFilePath == null || LogFilePath.equals(""))
            {
                buildError("LogInfoDeal", "initInfo", "日志处理类初始化失败，查询日志文件路径参数为空");
                return false;
            }
            LogFilePath = LogFilePath.toString();


            File myfolderPath = new File(LogFilePath);
            try
            {
                if (!myfolderPath.isDirectory())
                {
                    myfolderPath.mkdir();
                }
            }
            catch (Exception e)
            {
                buildError("LogInfoDeal", "initInfo", "日志处理类初始化失败，创建目录"+ myfolderPath + "失败" );
            }

            String EventNo = PubFun1.CreateMaxNo("EventNo", 20);
            LogFileName = EventNo + "[" + tEventType + "]" + ".txt";

            this.mFIOperationLogSchema.setEventNo(EventNo);
            this.mFIOperationLogSchema.setEventType(tEventType);
            this.mFIOperationLogSchema.setLogFileName(LogFileName);
            this.mFIOperationLogSchema.setLogFilePath(LogFilePath);
            this.mFIOperationLogSchema.setPerformState("1");
            this.mFIOperationLogSchema.setOperator(tOperator);
            this.mFIOperationLogSchema.setMakeDate(currentDate);
            this.mFIOperationLogSchema.setMakeTime(currentTime);

            MMap map = new MMap();
            map.put(mFIOperationLogSchema, "INSERT");
            this.mInputData = new VData();
            this.mInputData.add(map);

            String cOperate = "INSERT||MAIN";
            PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, cOperate))
            {
                buildError("LogInfoDeal", "initInfo", "日志处理类初始化失败,日志提交数据库失败" + tPubSubmit.mErrors.getFirstError());
                return false;
            }
            return true;

        }
        catch (Exception ex)
        {
            buildError("LogInfoDeal", "initInfo", "日志处理类初始化异常,异常信息为" + ex.getMessage());
            return false;
        }
    }


    public void AddLogParameter(String tParameterType,String tParameterMark,String tParameterValue)
    {

        FIOperationParameterSchema tFIOperationParameterSchema = new FIOperationParameterSchema();
        tFIOperationParameterSchema.setParameterType(tParameterType);
        tFIOperationParameterSchema.setParameterMark(tParameterMark);
        tFIOperationParameterSchema.setParameterValue(tParameterValue);
        mFIOperationParameterSet.add(tFIOperationParameterSchema);

    }


    public boolean SaveLogParameter()
    {
        try
        {
            for (int i = 0; i < mFIOperationParameterSet.size(); i++)
            {
                FIOperationParameterSchema tFIOperationParameterSchema = mFIOperationParameterSet.get(i + 1);
                if (tFIOperationParameterSchema.getParameterType() == null || tFIOperationParameterSchema.getParameterType().equals(""))
                {
                    buildError("LogInfoDeal", "SaveLogParameter", "传入操作参数检查未通过，参数类型为空");
                    return false;
                }
                if (tFIOperationParameterSchema.getParameterMark() == null || tFIOperationParameterSchema.getParameterMark().equals(""))
                {
                    buildError("LogInfoDeal", "SaveLogParameter", "传入操作参数检查未通过，参数描述为空");
                    return false;
                }
                if (tFIOperationParameterSchema.getParameterValue() == null || tFIOperationParameterSchema.getParameterValue().equals(""))
                {
                    buildError("LogInfoDeal", "SaveLogParameter", "传入操作参数检查未通过，参数值为空");
                    return  false;
                }
                tFIOperationParameterSchema.setEventNo(mFIOperationLogSchema.getEventNo());
                tFIOperationParameterSchema.setEventType(mFIOperationLogSchema.getEventType());
            }

            MMap map = new MMap();
            map.put(mFIOperationParameterSet, "INSERT");
            this.mInputData = new VData();
            this.mInputData.add(map);

            String cOperate = "INSERT||MAIN";
            PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, cOperate))
            {
                buildError("LogInfoDeal", "SaveLogParameter", "日志处理出错,操作参数提交数据库失败" + tPubSubmit.mErrors.getFirstError());
                return false;
            }

            return true;
        }
        catch (Exception e)
        {
            buildError("LogInfoDeal", "SaveLogParameter", "保存日志操作参数异常，异常信息为：" + e.getMessage());
            return false;
        }

    }

    public boolean WriteLogTxt(String txt)
    {
        try
        {
            String currentDate = PubFun.getCurrentDate();
            String currentTime = PubFun.getCurrentTime();
            String AllTxt = "[日期：" + currentDate + "][时间：" + currentTime + "]" + "日志内容：" + txt;
            String LogFile = LogFilePath + LogFileName;
            GenTxtFile gTxtFile = new GenTxtFile(LogFile, AllTxt);
            gTxtFile.writeTxt();
        }
        catch (Exception e)
        {
            buildError("LogInfoDeal", "WriteLogTxt", "信息写入日志文件异常，异常信息为：" + e.getMessage());
            return  false;
        }
        return true;
    }

    public boolean WriteLogTxt(String tLogFilePath,String tLogFileName,String txt)
    {
        try
        {
            String currentDate = PubFun.getCurrentDate();
            String currentTime = PubFun.getCurrentTime();
            String AllTxt = "[日期：" + currentDate + "][时间：" + currentTime + "]" + "内容：" + txt;
            String LogFile = tLogFilePath + tLogFileName;
            GenTxtFile gTxtFile = new GenTxtFile(LogFile, AllTxt);
            gTxtFile.writeTxt();
        }
        catch (Exception e)
        {
            buildError("LogInfoDeal", "WriteLogTxt", "信息写入日志文件异常，异常信息为：" + e.getMessage());
            return  false;
        }
        return true;
    }

    public boolean Complete(boolean FinishMark)
    {
        try
        {
            String Flag = "0";
            if(FinishMark == false)
            {
               Flag = "1" ;
            }
            this.mFIOperationLogSchema.setPerformState(Flag);
            this.mFIOperationLogSchema.setothernoMark(Flag);

            MMap map = new MMap();
            map.put(mFIOperationLogSchema, "UPDATE");

            this.mInputData = new VData();
            this.mInputData.add(map);

            String cOperate = "UPDATE||MAIN";
            PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, cOperate))
            {
                buildError("LogInfoDeal", "Complete", "日志结束处理出错，执行状态提交数据库失败" + tPubSubmit.mErrors.getFirstError());
                return false;
            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("LogInfoDeal", "Complete", "日志结束处理异常，异常信息为：" + ex.getMessage());
            return false;
        }

    }

    private void buildError(String szModuleName, String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = szModuleName;
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        logger.debug(szErrMsg);
    }

    public static void main(String[] args) {




        try
        {
            LogInfoDeal t = new LogInfoDeal("jianan","01");
            t.AddLogParameter("111","111","111");
            t.AddLogParameter("222","222","222");
            t.SaveLogParameter();
            t.WriteLogTxt("测试一下");

            t.WriteLogTxt("D:\\","1.txt","测试一下");
            t.Complete(true);
            t.Complete(false);

        } catch (Exception ex)
        {
            logger.debug("11" + ex.getMessage());
        }

    }

}
