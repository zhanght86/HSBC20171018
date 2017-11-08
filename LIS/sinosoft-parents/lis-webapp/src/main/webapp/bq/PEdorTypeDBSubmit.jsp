<!--用户校验类-->
 <%@page import="com.sinosoft.utility.*"%>
 <%@page import="com.sinosoft.lis.schema.*"%>
 <%@page import="com.sinosoft.lis.db.*"%>
 <%@page import="com.sinosoft.lis.vschema.*"%>
 <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
 <%@page import="com.sinosoft.service.*" %>  

<%
    //接收信息，并作校验处理。
    //输入参数
    //个人批改信息
    System.out.println("-----BG submit---");
    GlobalInput tG = new GlobalInput();
    
    LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();

    CErrors tErrors = new CErrors();
    //后面要执行的动作：添加，修改

    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String transact = "";
    
    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
    transact = request.getParameter("fmtransact");

    String ContNo = request.getParameter("ContNo");
    String EdorNo = request.getParameter("EdorNo");

    tG = (GlobalInput)session.getValue("GI");
    
    //调整金额的保存
    if (transact.equals("EdorSave")) {

        //个人批改信息
        tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
        tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
        tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));        
    LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
	 String tChk[] = request.getParameterValues("InpPolGridChk"); 
	  String tSumInsuAccBala[] = request.getParameterValues("PolGrid6");
    String tInsuAccBala[] = request.getParameterValues("PolGrid7");
    String tPolNo[] = request.getParameterValues("PolGrid8");
    String tInsuAccNo[] = request.getParameterValues("PolGrid9");  
    String tInsuAccLX[] = request.getParameterValues("PolGrid10");  
        
    for(int nIndex = 0; nIndex < tChk.length; nIndex++ ) 
    {
      // If this line isn't selected, continue
      if( !tChk[nIndex].equals("1") ) 
      {
        continue;
      }   
      LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
      
      tLCInsureAccSchema.setContNo(request.getParameter("ContNo"));
      tLCInsureAccSchema.setPolNo(tPolNo[nIndex]);
      tLCInsureAccSchema.setInsuAccNo(tInsuAccNo[nIndex]);
      if (tInsuAccBala[nIndex] != null)
      {
       tLCInsureAccSchema.setInsuAccBala(tInsuAccBala[nIndex]);
       //字段借用，存放截止本日时的利息
       tLCInsureAccSchema.setLastAccBala(tInsuAccLX[nIndex]);
       tLCInsureAccSchema.setInsuAccGetMoney(tSumInsuAccBala[nIndex]);
       tLCInsureAccSet.add(tLCInsureAccSchema);
      }  

    }
        // 准备传输数据 VData
        VData tVData = new VData();
//        EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
        String busiName="EdorDetailUI";
	 	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        try
        {
            tVData.add(tG);
            tVData.add(tLPEdorItemSchema);
            tVData.add(tLCInsureAccSet);
//            tEdorDetailUI.submitData(tVData, transact);
            tBusinessDelegate.submitData(tVData, transact,busiName);
        }
        catch(Exception ex)
        {
              Content = "保存失败，原因是:" + ex.toString();
              FlagStr = "Fail";
        }
        if ("".equals(FlagStr))
        {
//                tErrors = tEdorDetailUI.mErrors;
                tErrors = tBusinessDelegate.getCErrors();
                if (!tErrors.needDealError())
                {
                    Content ="保存成功！";
                    FlagStr = "Succ";
                }
                else
                {
                    Content = "保存失败，原因是:" + tErrors.getFirstError();
                    FlagStr = "Fail";
                }
        }
    }

    //页面反馈信息
    if (!tErrors.needDealError())
    {
        FlagStr = "Success";
        Content = "操作成功！";
    }
    else
    {
        FlagStr = "Fail";
        Content = "操作失败，原因是：" + tErrors.getFirstError();
    }
    tErrors = null;
%>


<html>
<head>
    <script language="JavaScript">
       parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
    </script>
</html>