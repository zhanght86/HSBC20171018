<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2006-12-06, 2006-12-18
 * @direction: 影像迁移保存
 ******************************************************************************/
%>

<%@ page import="com.sinosoft.lis.easyscan.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    //后台处理标记
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();
    
    //接收数据变量
    String sOldManageCom = request.getParameter("OldManageCom");
    String sNewManageCom = request.getParameter("NewManageCom");
    String sStartDate = request.getParameter("StartDate");
    String sEndDate = request.getParameter("EndDate");
    String sLoginManageCom = request.getParameter("LoginManageCom");
    String sLoginOperator = request.getParameter("LoginOperator");
    String sDocID = request.getParameter("DocID"); //重传才会传过此参数

    ExeSQL tes = new ExeSQL();
    SSRS ss = new SSRS();
    
    //DocID为空说明不是重传
    if(sDocID == null || sDocID.equals("") || sDocID.toLowerCase().equals("null")){
        //检查一下数据库中是否有与执行本次迁移相同的临时数据
        String sql = "select * from ES_DOCMOVE_TASK "
                  + " where startdate = '"+ sStartDate+"'"
                  + " and enddate = '"+ sEndDate+"'"
                  + " and tomanagecom = '"+ sNewManageCom+"'"
                  + " and managecom = '"+ sOldManageCom+"'"
                  + " and tasktype = '1'"
                  + " and endtime='00:00:00'";
        ss = tes.execSQL(sql);
        if(ss == null || ss.getMaxRow()< 1){
            //收集整理数据
            //GlobalInput
            GlobalInput tGlobalInput = new GlobalInput();
            tGlobalInput.ManageCom = sLoginManageCom;
            tGlobalInput.Operator = sLoginOperator;
            //TransferData
            TransferData tTransferData = new TransferData();
            tTransferData.setNameAndValue("OldManageCom", sOldManageCom);
            tTransferData.setNameAndValue("NewManageCom", sNewManageCom);
            tTransferData.setNameAndValue("StartDate", sStartDate);
            tTransferData.setNameAndValue("EndDate", sEndDate);
            //VData
            VData tVData = new VData();
            tVData.add(tGlobalInput);
            tVData.add(tTransferData);
            //垃圾处理
            tGlobalInput = null;
            tTransferData = null;
            
            //调用后台处理
            DocMoveUI tDocMoveUI = new DocMoveUI();
            if (!tDocMoveUI.submitData(tVData, "OPERATION"))
            {
                tErrors.copyAllErrors(tDocMoveUI.getErrors());
            }
            tDocMoveUI = null;
            tVData = null;
            
            //页面反馈信息
            if (!tErrors.needDealError())
            {
                FlagStr = "Success";
                Content = "迁移验证成功，启动影像迁移线程！";
            }
            else
            {
                FlagStr = "Fail";
                Content = "迁移验证失败，原因是：" + tErrors.getFirstError();
            }
            tErrors = null;
        
        }else{
            FlagStr = "Fail";
            Content = "后台线程正在执行迁移,请等待......<br>稍后可在日志查看中查询该次传送结果.";      
        }
    }
    //DocID不为空则说明是重传
    else if(sDocID != null && !sDocID.equals("")){
    
        //检查一下数据库中是否有与执行本次重新迁移相同的临时数据
        String sql = "select * from ES_DOCMOVE_LOG "
                  + " where 1 = 1"
                  + " and DocID = '"+ sDocID +"'"
                  + " and flag = '2'"; //Flag 0:正确(包括重传后正确),1:错误,2:重传中
        ss = tes.execSQL(sql);
        if(ss == null || ss.getMaxRow()< 1){
            //收集整理数据
            //GlobalInput
            GlobalInput tGlobalInput = new GlobalInput();
            tGlobalInput.ManageCom = sLoginManageCom;
            tGlobalInput.Operator = sLoginOperator;
            //TransferData
            TransferData tTransferData = new TransferData();
            tTransferData.setNameAndValue("OldManageCom", sOldManageCom);
            tTransferData.setNameAndValue("NewManageCom", sNewManageCom);
            tTransferData.setNameAndValue("StartDate", sStartDate);
            tTransferData.setNameAndValue("EndDate", sEndDate);
            tTransferData.setNameAndValue("DocID", sDocID);
            //VData
            VData tVData = new VData();
            tVData.add(tGlobalInput);
            tVData.add(tTransferData);
            //垃圾处理
            tGlobalInput = null;
            tTransferData = null;
            
            //调用后台处理
            DocMoveUI tDocMoveUI = new DocMoveUI();
            if (!tDocMoveUI.submitData(tVData, "RESEND"))
            {
                tErrors.copyAllErrors(tDocMoveUI.getErrors());
            }
            tDocMoveUI = null;
            tVData = null;
            
            //页面反馈信息
            if (!tErrors.needDealError())
            {
                FlagStr = "Success";
                Content = "迁移验证成功，启动影像迁移线程！";
            }
            else
            {
                FlagStr = "Fail";
                Content = "迁移验证失败，原因是：" + tErrors.getFirstError();
            }
            tErrors = null;
        
        }else{
            FlagStr = "Fail";
            Content = "后台线程正在执行迁移,请等待......<br>稍后可在日志查看中查询该次传送结果.";      
        }   
    } 
    //返回页面信息
    out.print("0" + "~" + FlagStr + "~" + Content);
%>
