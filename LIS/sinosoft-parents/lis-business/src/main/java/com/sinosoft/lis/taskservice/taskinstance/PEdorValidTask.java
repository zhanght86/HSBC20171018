
package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

/*******************************************************************************
 * @author   : sinosoft
 * @direction: 保全逾期服务批处理：原lis6确认批处理
 ******************************************************************************/

import com.sinosoft.lis.bq.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.workflow.bq.*;

//******************************************************************************


public class PEdorValidTask extends TaskThread 
{
private static Logger logger = Logger.getLogger(PEdorValidTask.class);

    //==========================================================================

    //全局变量
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mCurrentDate = PubFun.getCurrentDate();

    //==========================================================================

    public PEdorValidTask() {}

    //==========================================================================

    public boolean dealMain()
    {
        initTaskService();                     //初始化本服务类需要的数据
        EdorUnuseNoScanApply();                //保全未使用无扫描申请数据清理批处理
        EdorTestFinish();                      //保全试算数据清理批处理
        EdorAutoUWFinish();                    //保全自动核保数据清理批处理
        EdorDelayNoReplyTimeoutTerminate();    //保全客户延期未响应逾期终止批处理
        //modify by jiaqiangli 2009-05-19 核保的不删除 
        //EdorUWTimeoutTerminate();              //保全核保逾期终止批处理
        //收费逾期终止不需要 确认by zhanglina2009-05-12
        //EdorChargeTimeoutTerminate();          //保全收费逾期终止批处理
        //EdorConfirm();                         //保全复核通过的确认生效批处理, 财务确认后会自动调用, 这里取消
//        EdorValid();                           //保全确认未生效的生效批处理
        return true ;
    } //function run end

    //==========================================================================
    public boolean dealMain(VData tVData)
    {
		mGlobalInput=(GlobalInput) tVData.getObjectByObjectName(
				"GlobalInput", 0);	    	
    	initTaskService();                     //初始化本服务类需要的数据
//		日志监控，性能监控
		PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1],"保全未使用无扫描申请数据清理批处理","11");
        EdorUnuseNoScanApply();                //保全未使用无扫描申请数据清理批处理
//		日志监控，性能监控
		PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1],"保全试算数据清理批处理","12");
        EdorTestFinish();                      //保全试算数据清理批处理
//		日志监控，性能监控
		PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1],"保全自动核保数据清理批处理","13");
        EdorAutoUWFinish();                    //保全自动核保数据清理批处理
//		日志监控，性能监控
		PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1],"保全客户延期未响应逾期终止批处理","14");
        EdorDelayNoReplyTimeoutTerminate();    //保全客户延期未响应逾期终止批处理
        //modify by jiaqiangli 2009-05-19 核保的不删除 
        //EdorUWTimeoutTerminate();              //保全核保逾期终止批处理
        //收费逾期终止不需要 确认by zhanglina2009-05-12
        //EdorChargeTimeoutTerminate();          //保全收费逾期终止批处理
        //EdorConfirm();                         //保全复核通过的确认生效批处理, 财务确认后会自动调用, 这里取消
//        EdorValid();                           //保全确认未生效的生效批处理
        return true ;
    } //function run end
    /**
     * 初始化本服务类需要的数据
     */
    private void initTaskService()
    {
        mGlobalInput.Operator = "batch";
        mGlobalInput.ManageCom = "86";
        mGlobalInput.ComCode = "86";
    } //function initTaskService end


    //==========================================================================

    /**
     * 获取各种保全逾期终止批处理的逾期天数
     */
    private String getEdorTypeTimeoutDays(String sEdorType)
    {
        String sResultTimeoutDays = new String("");

        //查询逾期天数计算代码
        String QuerySQL = "select * from LMEdorCal where CalType = '" + "?sEdorType?" + "'";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(QuerySQL);
		sqlbv1.put("sEdorType", sEdorType);
        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
        LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
        try
        {
            tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv1);
        }
        catch (Exception ex)
        {
            logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 执行 SQL 查询出错");
            logger.debug("\t   错误原因 : " + tLMEdorCalDB.mErrors.getFirstError());
            return null;
        }
        if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0)
        {
            logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 未知的保全类型！ 查询逾期天数失败！");
            return null;
        }
        else
        {
            String sTempCalCode = tLMEdorCalSet.get(1).getCalCode();
            if (sTempCalCode == null || sTempCalCode.trim().equals(""))
            {
                logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 查询逾期天数计算代码失败！");
                return null;
            }
            else
            {
                //根据计算代码计算逾期天数
                Calculator tCalculator = new Calculator();
                tCalculator.setCalCode(sTempCalCode);
                sResultTimeoutDays = tCalculator.calculate();
                if (sResultTimeoutDays == null || sResultTimeoutDays.trim().equals(""))
                {
                    logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 根据计算代码计算逾期天数失败！");
                    return null;
                }
                tCalculator = null;
            }
        }
        tLMEdorCalDB = null;
        tLMEdorCalSet = null;

        return sResultTimeoutDays;
    } //function getEdorTypeTimeoutDays end

    //==========================================================================

    /**
     * 保全未使用无扫描申请数据清理批处理
     */
    private void EdorUnuseNoScanApply()
    {
        logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理启动");
        //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理启动");

        //if (!(mCurrentTime.compareTo("23:00:00") >= 0 && mCurrentTime.compareTo("23:59:59") <= 0))
        //{
        //    return;    //在晚上 11 点以后运行
        //}
        //if (!(mCurrentTime.compareTo("00:00:00") >= 0 && mCurrentTime.compareTo("05:59:59") <= 0))
        //{
        //    return;    //在凌晨 06 点以前运行
        //}

        //查询逾期终止天数
        String sTimeoutDays = getEdorTypeTimeoutDays("WFOverDate");
        if (sTimeoutDays == null || sTimeoutDays.trim().equals(""))
        {
            logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理, 查询逾期终止天数失败! ");
            //日志监控,过程监控        
    	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理, 系统未设置逾期终止天数");
            return;
        }

        //选取需要清理的数据
        String DeleteSQL = new String("");
        DeleteSQL = "delete from LWMission "
                  +  "where ActivityID = '0000000002' "
                  +    "and MissionProp3 is null "
                  +    "and MakeDate <= (to_date('" + "?mCurrentDate?" + "','yyyy-mm-dd') - " + "?sTimeoutDays?" + ")";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(DeleteSQL);
		sqlbv2.put("mCurrentDate", mCurrentDate);
		sqlbv2.put("sTimeoutDays", sTimeoutDays);
        //logger.debug(DeleteSQL);

        MMap tMap = new MMap();
        tMap.put(sqlbv2, "DELETE");
        VData tVData = new VData();
        tVData.add(tMap);
        tMap = null;

        //PubSubmit
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, ""))
        {
            logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理, 删除数据失败！");
            return;
        }
        tPubSubmit = null;
        tVData = null;

        logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理完毕");
        //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理完毕");
    }  //function EdorUnuseNoScanApply end

    //==========================================================================

    /**
     * 保全试算数据清理批处理
     */
    private void EdorTestFinish()
    {
        logger.debug("\t@> PEdorValidTask.EdorTestFinish() : 保全试算数据清理批处理启动");
        //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全试算数据清理批处理启动");

        int nSuccCount = 0;    //统计成功的纪录条数
        int nFailCount = 0;    //统计失败的纪录条数

        //查询出当前系统等待保全试算数据清理的纪录
        String QuerySQL = "select * from LWMission where ActivityID = '0000000098'";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(QuerySQL);
        RSWrapper rsWrapper = new RSWrapper();
        LWMissionSet tLWMissionSet = new LWMissionSet();
        if (!rsWrapper.prepareData(tLWMissionSet, sqlbv3))
        {
            logger.debug("\t@> PEdorValidTask.EdorTestFinish() : 保全试算数据清理批处理, 数据准备失败");
            return;
        }

        //循环读取操作
        do
        {
            rsWrapper.getData();
            if (tLWMissionSet == null || tLWMissionSet.size() <= 0)
            {
                break;
            }
            for (int i = 1; i <= tLWMissionSet.size(); i++)
            {
                //LWMissionSchema
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                tLWMissionSchema = tLWMissionSet.get(i);
                //LPEdorAppSchema
                LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
                tLPEdorAppSchema.setEdorAcceptNo(tLWMissionSchema.getMissionProp1());
                //TransferData
                TransferData tTransferData = new TransferData();
                tTransferData.setNameAndValue("MissionID", tLWMissionSchema.getMissionID());
                tTransferData.setNameAndValue("SubMissionID", tLWMissionSchema.getSubMissionID());
                tTransferData.setNameAndValue("DelReason", "");    //删除原因
                tTransferData.setNameAndValue("ReasonCode", "");   //原因编号
                //VData
                VData tVData = new VData();
                tVData.add(mGlobalInput);
                tVData.add(tLPEdorAppSchema);
                tVData.add(tTransferData);
                tLPEdorAppSchema = null;
                tTransferData = null;
                //EdorWorkFlowUI
                EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
                if (!tEdorWorkFlowUI.submitData(tVData, "0000000098"))
                {
                    nFailCount ++;
                    logger.debug("\t@> PEdorValidTask.EdorTestFinish() : 保全试算数据清理批处理, 一条纪录清理失败");
                    logger.debug("\t   失败纪录对应的保全受理号: " + tLWMissionSchema.getMissionProp1());
                    logger.debug("\t   该记录清理失败的原因: " + tEdorWorkFlowUI.mErrors.getFirstError());
                }
                else
                {
                    nSuccCount ++;
                }
                tEdorWorkFlowUI = null;
                tVData = null;
                tLWMissionSchema = null;
            }
        }
        while (tLWMissionSet != null && tLWMissionSet.size() > 0);
        rsWrapper.close();
        rsWrapper = null;
        tLWMissionSet = null;

        logger.debug("\t@> PEdorValidTask.EdorTestFinish() : 保全试算数据清理批处理完毕");
        logger.debug("\t   本次数据清理结果统计: " + nSuccCount + " 成功; " + nFailCount + " 失败。");
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"本次保全试算数据清理结果统计: " + nSuccCount + " 成功; " + nFailCount + " 失败。");
        //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全试算数据清理批处理完毕");
    }  //function EdorTestFinish end

    //==========================================================================

    /**
     * 保全自动核保数据清理批处理
     */
    private void EdorAutoUWFinish()
    {
        logger.debug("\t@> PEdorValidTask.EdorAutoUWFinish() : 保全自动核保数据清理批处理启动");
        //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全自动核保数据清理批处理启动");

        int nSuccCount = 0;    //统计成功的纪录条数
        int nFailCount = 0;    //统计失败的纪录条数

        //查询出当前系统等待保全试算数据清理的纪录
        String QuerySQL = "select * from LWMission where ActivityID = '0000000004'";
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(QuerySQL);
        RSWrapper rsWrapper = new RSWrapper();
        LWMissionSet tLWMissionSet = new LWMissionSet();
        if (!rsWrapper.prepareData(tLWMissionSet, sqlbv4))
        {
            logger.debug("\t@> PEdorValidTask.EdorAutoUWFinish() : 保全自动核保数据清理批处理, 数据准备失败");
            return;
        }

        //循环读取操作
        do
        {
            rsWrapper.getData();
            if (tLWMissionSet == null || tLWMissionSet.size() < 1)
            {
                break;
            }
            for (int i = 1; i <= tLWMissionSet.size(); i++)
            {
                //LWMissionSchema
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                tLWMissionSchema = tLWMissionSet.get(i);
                //LPEdorAppSchema
                LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
                tLPEdorAppSchema.setEdorAcceptNo(tLWMissionSet.get(i).getMissionProp1());
                //TransferData
                TransferData tTransferData = new TransferData();
                //select * from LWFieldMap where ActivityID = '0000000004'
                tTransferData.setNameAndValue("MissionID", tLWMissionSchema.getMissionID());
                tTransferData.setNameAndValue("SubMissionID", tLWMissionSchema.getSubMissionID());
                tTransferData.setNameAndValue("EdorAcceptNo", tLWMissionSchema.getMissionProp1());
                tTransferData.setNameAndValue("OtherNo", tLWMissionSchema.getMissionProp2());
                tTransferData.setNameAndValue("OtherNoType", tLWMissionSchema.getMissionProp3());
                tTransferData.setNameAndValue("EdorAppName", tLWMissionSchema.getMissionProp4());
                tTransferData.setNameAndValue("Apptype", tLWMissionSchema.getMissionProp5());
                tTransferData.setNameAndValue("ManageCom", tLWMissionSchema.getMissionProp7());
                tTransferData.setNameAndValue("AppntName", tLWMissionSchema.getMissionProp11());
                tTransferData.setNameAndValue("PaytoDate", tLWMissionSchema.getMissionProp12());
                //VData
                VData tVData = new VData();
                tVData.add(mGlobalInput);
                tVData.add(tLPEdorAppSchema);
                tVData.add(tTransferData);
                tLPEdorAppSchema = null;
                tTransferData = null;
                //EdorWorkFlowUI
                EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
                if (!tEdorWorkFlowUI.submitData(tVData, "0000000004"))
                {
                    nFailCount ++;
                    logger.debug("\t@> PEdorValidTask.EdorAutoUWFinish() : 保全自动核保数据清理批处理, 一条纪录清理失败");
                    logger.debug("\t   失败纪录对应的保全受理号: " + tLWMissionSchema.getMissionProp1());
                    logger.debug("\t   该记录清理失败的原因: " + tEdorWorkFlowUI.mErrors.getFirstError());
                }
                else
                {
                    nSuccCount ++;
                }
                tEdorWorkFlowUI = null;
                tVData = null;
                tLWMissionSchema = null;
            }
        }
        while (tLWMissionSet != null && tLWMissionSet.size() > 0);
        rsWrapper.close();
        rsWrapper = null;
        tLWMissionSet = null;

        logger.debug("\t@> PEdorValidTask.EdorAutoUWFinish() : 保全自动核保数据清理批处理完毕");
        //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全自动核保数据清理批处理完毕");
        logger.debug("\t   本次数据清理结果统计: " + nSuccCount + " 成功; " + nFailCount + " 失败。");
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"本次保全自动核保数据清理结果统计: " + nSuccCount + " 成功; " + nFailCount + " 失败。");
    }  //function EdorTestFinish end

    //==========================================================================

    /**
     * 保全客户延期未响应逾期终止批处理
     */
    private void EdorDelayNoReplyTimeoutTerminate()
    {
        logger.debug("\t@> PEdorValidTask.EdorDelayNoReplyTimeoutTerminate() : 保全客户延期未响应逾期终止批处理启动");
        //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全客户延期未响应逾期终止批处理启动");

        //查询逾期终止天数
        String sTimeoutDays = getEdorTypeTimeoutDays("CUOverDate");
        if (sTimeoutDays == null || sTimeoutDays.trim().equals(""))
        {
            logger.debug("\t@> PEdorValidTask.EdorDelayNoReplyTimeoutTerminate() : 保全客户延期未响应逾期终止批处理, 查询逾期终止天数失败! ");
            //日志监控,过程监控        
    	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全客户延期未响应逾期终止批处理, 系统未设置逾期终止天数");
            return;
        }

        //选取需要清理的数据
        String QuerySQL = new String("");
        QuerySQL = "select * "
                 +   "from LPEdorApp a "
                 
                 +  "where OtherNoType = '3' "
                 +    "and EdorState = '3' "
                 //+    "and EdorAppDate <= (to_date('" + mCurrentDate + "','yyyy-mm-dd') - " + sTimeoutDays + ")"
                 // modify by jiaqiangli 2009-05-13 EdorAppDate日期是可以往前指的
                 +    "and MakeDate <= subdate(to_date('" + "?mCurrentDate?" + "','yyyy-mm-dd') , " + "?sTimeoutDays?" + ")"
                 +    "and not exists (select 1 from loprtmanager where otherno=a.otherno and code='BQ37' and standbyflag1=a.edoracceptno) "
                 + "union select * "
                 +   "from LPEdorApp a "
                
                 +  "where OtherNoType = '3' "
                 +    "and EdorState = '3' "
                 //+    "and EdorAppDate <= (to_date('" + mCurrentDate + "','yyyy-mm-dd') - " + sTimeoutDays + ")"
                 +    "and MakeDate <= subdate(to_date('" + "?mCurrentDate?" + "','yyyy-mm-dd') , " + "?sTimeoutDays?" + ")"
                 +    "and exists (select 1 from loprtmanager where stateflag='2' and otherno=a.otherno and code='BQ37' and standbyflag1=a.edoracceptno) ";
        //add by jiaqiangli 2009-04-09 未下发函件逾期或已下发函件且已回销逾期 stateflag = '2'
        //logger.debug(QuerySQL);
        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
     		sqlbv5.sql(QuerySQL);
     		sqlbv5.put("mCurrentDate", mCurrentDate);
     		sqlbv5.put("sTimeoutDays", sTimeoutDays);
        RSWrapper rsWrapper = new RSWrapper();
        LPEdorAppSet tLPEdorAppSet = new LPEdorAppSet();
        if (!rsWrapper.prepareData(tLPEdorAppSet, sqlbv5))
        {
            logger.debug("\t@> PEdorValidTask.EdorDelayNoReplyTimeoutTerminate() : 保全客户延期未响应逾期终止批处理, 数据准备失败! ");
            return;
        }

        //循环读取操作
        do
        {
            rsWrapper.getData();
         // 数据提交
    		PubSubmit tPubSubmit = new PubSubmit();
            for (int i = 1; i <= tLPEdorAppSet.size(); i++)
            {
                //LPEdorAppSchema
                LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
                tLPEdorAppSchema = tLPEdorAppSet.get(i);
                //PEdorOverDueBL
                PEdorOverDueBL tPEdorOverDueBL = new PEdorOverDueBL(mGlobalInput);
                if (!tPEdorOverDueBL.setOverDue(tLPEdorAppSchema.getEdorAcceptNo(), "4"))
                {
                    logger.debug(tPEdorOverDueBL.mErrors.getFirstError());
                }
                // add by jiaqiangli 2009-04-09 分在两个事务里，逾期终止成功打印 保全申请撤销通知书
                else {
                	LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
            		LOPRTManagerSchema mmLOPRTManagerSchema;
            		MMap map = new MMap();
            		try {
            			LCContDB tLCContDB = new LCContDB();
        		        tLCContDB.setContNo(tLPEdorAppSchema.getOtherNo());
        		        if (!tLCContDB.getInfo()) {
        		        	CError.buildErr(this, "保单查询失败");
        		        	continue;
        		        }
            			String Code = PrintManagerBL.CODE_PEdorCancel;// 个险保全撤销通知书
            			mmLOPRTManagerSchema = new LOPRTManagerSchema();
            			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
            			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
            			mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
            			mmLOPRTManagerSchema.setOtherNo(tLPEdorAppSchema.getOtherNo());
            			mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
            			mmLOPRTManagerSchema.setCode(Code); // 打印类型
            			mmLOPRTManagerSchema.setManageCom(tLPEdorAppSchema.getManageCom()); // 管理机构
            			mmLOPRTManagerSchema.setAgentCode(tLCContDB.getAgentCode()); // 代理人编码
            			mmLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
            			mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
            			mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
            			mmLOPRTManagerSchema.setStateFlag("0"); // 打印状态
            			mmLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
            			mmLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
            			mmLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
            			mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
            			mmLOPRTManagerSchema.setStandbyFlag1(tLPEdorAppSchema.getEdorAcceptNo());//受理号
            			//backreason
            			//modify by jiaqiangli 2009-05-17 应彭送庭修改 统一成edorcancelsreason
            			//edorcancelsreason
            			mmLOPRTManagerSchema.setStandbyFlag2("E05-");//保存申请撤销原因
            			mLOPRTManagerSet.add(mmLOPRTManagerSchema);
            			map.put(mLOPRTManagerSet, "DELETE&INSERT");
            		}
            		catch (Exception e) {
            			logger.debug("EdorDelayNoReplyTimeoutTerminate"+tLPEdorAppSchema.getOtherNo()+"插入保全撤销通知书失败");
            			CError.buildErr(this, "插入保全撤销通知书失败!");
            			continue;
            		}
            		VData mInputData = new VData();
            		mInputData.clear();
            		mInputData.add(map);
            		
            		if (!tPubSubmit.submitData(mInputData, "")) {
            			CError.buildErr(this, "保全逾期终止数据提交失败");
            			continue;
            		}
                }
                tPEdorOverDueBL = null;
                tLPEdorAppSchema = null;
            }
        }
        while (tLPEdorAppSet != null && tLPEdorAppSet.size() > 0);
        rsWrapper.close();
        rsWrapper = null;
        tLPEdorAppSet = null;
        //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全客户延期未响应逾期终止批处理完毕");
        logger.debug("\t@> PEdorValidTask.EdorDelayNoReplyTimeoutTerminate() : 保全客户延期未响应逾期终止批处理完毕");
    } //function EdorDelayNoReplyTimeoutTerminate end

    //==========================================================================

    /**
     * 保全核保逾期终止批处理
     */
    private void EdorUWTimeoutTerminate()
    {
        logger.debug("\t@> PEdorValidTask.EdorUWTimeoutTerminate() : 保全核保逾期终止批处理启动");

        //查询逾期终止天数
        String sTimeoutDays = getEdorTypeTimeoutDays("UMOverDate");
        if (sTimeoutDays == null || sTimeoutDays.trim().equals(""))
        {
            logger.debug("\t@> PEdorValidTask.EdorUWTimeoutTerminate() : 保全核保逾期终止批处理, 查询逾期终止天数失败! ");
            return;
        }

        //按照需求修改为: 发送核保通知书后30天为逾期, 并非进入核保状态30天
        String QuerySQL = new String("");
//        QuerySQL = "select * "
//                 +   "from LWMission a "
//                 +  "where a.ActivityID = '0000000005' "
//                 +    "and a.ActivityStatus = '2' "
//                 +    "and exists "
//                 +        "(select 'X' "
//                 +           "from LWMission b "
//                 +          "where b.ActivityID in ('0000001300', '0000001111', '0000001121') "
//                 +             "and b.MakeDate <= (to_date('" + mCurrentDate + "','yyyy-mm-dd') - " + sTimeoutDays + ") "
//                 +    "and a.MissionID = b.MissionID) ";
        //add by jiaqiangli 2009-04-09
//        0000000303	保全回收体检通知书
//        0000000316	保全生调结果录入
//        0000000353	保全回收补资料通知书
        QuerySQL = "select * "
          +   "from LWMission a "
          +  "where a.ActivityID in ('0000000353', '0000000303', '0000000316') "
          +  "and a.MakeDate <= (to_date('" + "?mCurrentDate?" + "','yyyy-mm-dd') - " + "?sTimeoutDays?" + ") "
          +    "and a.ActivityStatus = '2' ";
        //logger.debug(QuerySQL);
        SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
 		sqlbv6.sql(QuerySQL);
 		sqlbv6.put("mCurrentDate", mCurrentDate);
 		sqlbv6.put("sTimeoutDays", sTimeoutDays);
        RSWrapper rsWrapper = new RSWrapper();
        LWMissionSet tLWMissionSet = new LWMissionSet();
        if (!rsWrapper.prepareData(tLWMissionSet, sqlbv6))
        {
            logger.debug("\t@> PEdorValidTask.EdorUWTimeoutTerminate() : 保全核保逾期终止批处理, 数据准备失败! ");
            return;
        }
        //循环读取操作
        do
        {
            rsWrapper.getData();
            if (tLWMissionSet == null || tLWMissionSet.size() < 1)
            {
                break;
            }
            for (int i = 1; i <= tLWMissionSet.size(); i++)
            {
                //LWMissionSchema
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                tLWMissionSchema = tLWMissionSet.get(i);
                //PEdorOverDueBL
                PEdorOverDueBL tPEdorOverDueBL = new PEdorOverDueBL(mGlobalInput);
                if (!tPEdorOverDueBL.setUWOverDue(tLWMissionSchema.getMissionProp1()))
                {
                    logger.debug(tPEdorOverDueBL.mErrors.getFirstError());
                }
                tPEdorOverDueBL = null;
                tLWMissionSchema = null;
            }
        }
        while (tLWMissionSet != null && tLWMissionSet.size() > 0);
        rsWrapper.close();
        rsWrapper = null;
        tLWMissionSet = null;

        logger.debug("\t@> PEdorValidTask.EdorUWTimeoutTerminate() : 保全核保逾期终止批处理完毕");
    } //function EdorUWTimeoutTerminate end

    //==========================================================================

    /**
     * 保全收费逾期终止批处理
     */
    private void EdorChargeTimeoutTerminate()
    {
        logger.debug("\t@> PEdorValidTask.EdorChargeTimeoutTerminate() : 保全收费逾期终止批处理启动");
        String sTimeoutDays = getEdorTypeTimeoutDays("OverDate");
        String QuerySQL = new String("");
        QuerySQL = "select * "
                 +   "from LJSPay a "
                 +    "where OtherNoType='"+"?OtherNoType?"+"' and exists "
                 +        "(select 'X' "
                 +           "from LWMission b "
                 +          "where b.ActivityID='0000000009' "
                 +             "and b.MakeDate <= (to_date('" + "?mCurrentDate?" + "','yyyy-mm-dd') - " + "?sTimeoutDays?" + ") "
                 +    "and a.otherno = b.Missionprop1) ";
                 
        logger.debug(QuerySQL);
        SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
 		sqlbv7.sql(QuerySQL);
 		sqlbv7.put("OtherNoType", BqCode.LJ_OtherNoType);
 		sqlbv7.put("mCurrentDate", mCurrentDate);
 		sqlbv7.put("sTimeoutDays", sTimeoutDays);
        RSWrapper rsWrapper = new RSWrapper();
        LJSPaySet tLJSPaySet = new LJSPaySet();
        if (!rsWrapper.prepareData(tLJSPaySet, sqlbv7))
        {
            logger.debug("\t@> PEdorValidTask.EdorChargeTimeoutTerminate() : 保全收费逾期终止批处理, 数据准备失败! ");
            return;
        }

        //循环读取操作
        do
        {
            rsWrapper.getData();
            if (tLJSPaySet == null || tLJSPaySet.size() < 1)
            {
                break;
            }
            for (int i = 1; i <= tLJSPaySet.size(); i++)
            {
                //LJSPaySchema
                LJSPaySchema tLJSPaySchema = new LJSPaySchema();
                tLJSPaySchema = tLJSPaySet.get(i);
                //PEdorOverDueBL
                PEdorOverDueBL tPEdorOverDueBL = new PEdorOverDueBL(mGlobalInput);
                if (!tPEdorOverDueBL.setOverDue(tLJSPaySchema.getOtherNo(), "4"))
                {
                    logger.debug(tPEdorOverDueBL.mErrors.getFirstError());
                }
                tPEdorOverDueBL = null;
                tLJSPaySchema = null;
            }
        }
        while (tLJSPaySet != null && tLJSPaySet.size() > 0);
        rsWrapper.close();
        rsWrapper = null;
        tLJSPaySet = null;

        logger.debug("\t@> PEdorValidTask.EdorChargeTimeoutTerminate() : 保全收费逾期终止批处理完毕");
    } //function EdorChargeTimeoutTerminate end

    //==========================================================================

    /**
     * 保全复核通过的确认生效批处理
     */
    private void EdorConfirm()
    {
        logger.debug("\t@> PEdorValidTask.EdorConfirm() : 保全复核通过的确认生效批处理启动");

        //查询出当前系统等待保全确认生效的纪录
        String QuerySQL = " select * from LPEdorApp where 1=1 " +
                          " and exists (select 'X' from ljtempfee where tempfeeno in (select getnoticeno from ljspay where otherno = edoracceptno)) " +
                          " and getmoney > 0 and  EdorState = 'a'";
        SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
     		sqlbv8.sql(QuerySQL);
        RSWrapper rsWrapper = new RSWrapper();
        LPEdorAppSet tLPEdorAppSet = new LPEdorAppSet();
        if (!rsWrapper.prepareData(tLPEdorAppSet, sqlbv8))
        {
            logger.debug("\t@> PEdorValidTask.EdorConfirm() : 保全复核通过的确认生效批处理, 数据准备失败");
            return;
        }

        //循环读取操作
        do
        {
            rsWrapper.getData();
            if (tLPEdorAppSet == null || tLPEdorAppSet.size() < 1)
            {
                break;
            }
            for (int i = 1; i <= tLPEdorAppSet.size(); i++)
            {
                //LPEdorAppSchema
                LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
                tLPEdorAppSchema = tLPEdorAppSet.get(i);
                //GlobalInput
                GlobalInput tGlobalInput = new GlobalInput();
                tGlobalInput.Operator = "001";
                tGlobalInput.ManageCom = tLPEdorAppSet.get(i).getManageCom();
                //PEdorAutoConfirmBL
                PEdorAutoConfirmBL tPEdorAutoConfirmBL = new PEdorAutoConfirmBL(tGlobalInput);
                if (!tPEdorAutoConfirmBL.AutoConfirm(tLPEdorAppSchema.getEdorAcceptNo()))
                {
                    logger.debug("\t@> PEdorValidTask.EdorConfirm() : EdorAcceptNo : " + tLPEdorAppSchema.getEdorAcceptNo());
                    logger.debug("\t@> PEdorValidTask.EdorConfirm() : " + tPEdorAutoConfirmBL.mErrors.getFirstError());
                }
                tPEdorAutoConfirmBL = null;
                tGlobalInput = null;
                tLPEdorAppSchema = null;
            }
        }
        while (tLPEdorAppSet != null && tLPEdorAppSet.size() > 0);
        rsWrapper.close();
        rsWrapper = null;
        tLPEdorAppSet = null;

        logger.debug("\t@> PEdorValidTask.EdorConfirm() : 保全复核通过的确认生效批处理完毕");
    } //function EdorConfirm end

    //==========================================================================

    /**
     * 保全确认未生效的生效批处理
     */
    private void EdorValid()
    {
        logger.debug("\t@> PEdorValidTask.EdorValid() : 保全确认未生效的生效批处理启动");

        //查询出当前系统等待保全确认生效的纪录
        String QuerySQL = "select * from LPEdorApp where EdorState = '6'";
        SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
 		sqlbv9.sql(QuerySQL);
        RSWrapper rsWrapper = new RSWrapper();
        LPEdorAppSet tLPEdorAppSet = new LPEdorAppSet();
        if (!rsWrapper.prepareData(tLPEdorAppSet, sqlbv9))
        {
            logger.debug("\t@> PEdorValidTask.EdorValid() : 保全确认未生效的生效批处理, 数据准备失败");
            return;
        }

        //循环读取操作
        do
        {
            rsWrapper.getData();
            if (tLPEdorAppSet == null || tLPEdorAppSet.size() < 1)
            {
                break;
            }
            for (int i = 1; i <= tLPEdorAppSet.size(); i++)
            {
                //LPEdorAppSchema
                LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
                tLPEdorAppSchema = tLPEdorAppSet.get(i);
                //GlobalInput
                GlobalInput tGlobalInput = new GlobalInput();
                tGlobalInput.Operator = "001";
                tGlobalInput.ManageCom = tLPEdorAppSchema.getManageCom();
                //TransferData
                TransferData tTransferData = new TransferData();
                tTransferData.setNameAndValue("EdorAcceptNo", tLPEdorAppSchema.getEdorAcceptNo());
                //VData
                VData tVData = new VData();
                tVData.add(tGlobalInput);
                tVData.add(tTransferData);
                tGlobalInput = null;
                tTransferData = null;

                if (tLPEdorAppSchema.getOtherNoType().trim().equals("1") ||
                      tLPEdorAppSchema.getOtherNoType().trim().equals("3"))
                {
                    PEdorValidBL tPEdorValidBL = new PEdorValidBL();
                    if (!tPEdorValidBL.submitData(tVData, ""))
                    {
                        logger.debug("\t@> PEdorValidTask.EdorValid() : EdorAcceptNo : " +
                                           tLPEdorAppSchema.getEdorAcceptNo());
                        logger.debug("\t@> PEdorValidTask.EdorValid() : " +
                                           tPEdorValidBL.mErrors.getFirstError());
                    }
                    tPEdorValidBL = null;
                }
                else if (tLPEdorAppSchema.getOtherNoType().trim().equals("2") ||
                      tLPEdorAppSchema.getOtherNoType().trim().equals("4"))
             {
                 GEdorValidBL tGEdorValidBL = new GEdorValidBL();
                 if (!tGEdorValidBL.submitData(tVData, ""))
                 {
                     logger.debug(
                             "\t@> GEdorValidTask.EdorValid() : PdorAcceptNo : " +
                             tLPEdorAppSchema.getEdorAcceptNo());
                     logger.debug("\t@> GEdorValidTask.EdorValid() : " +
                                        tGEdorValidBL.mErrors.getFirstError());
                 }
                 tGEdorValidBL = null;
             }
             tVData = null;
            }
        }
        while (tLPEdorAppSet != null && tLPEdorAppSet.size() > 0);
        rsWrapper.close();
        rsWrapper = null;
        tLPEdorAppSet = null;

        logger.debug("\t@> PEdorValidTask.EdorValid() :保全确认未生效的生效批处理完毕");
    } //function EdorValid end

    //==========================================================================

    /**
     * 测试主程序业务方法
     * @param    String[]
     */
    public static void main(String[] args)
    {
        PEdorValidTask tPEdorValidTask = new PEdorValidTask();
        tPEdorValidTask.dealMain();
    }  //function main end

    //==========================================================================

} //class PEdorValidTask end
