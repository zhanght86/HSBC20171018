<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GrpPlanRiskSave.jsp
//程序功能：
//创建日期：2006-11-22 17:39:52
//创建人  ：Chenrong
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
    //接收信息，并作校验处理。
    //输入参数
    
    LCGrpContSchema tLCGrpContSchema   = new LCGrpContSchema();
    LCGrpPolSet mLCGrpPolSet =new LCGrpPolSet();
    
    TransferData tTransferData = new TransferData(); 
    //GrpPlanRiskUI tGrpPlanRiskUI   = new GrpPlanRiskUI();
     String busiName="tbGrpPlanRiskUI";
     BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //输出参数
    String FlagStr = "";
    String Content = ""; 
    
    GlobalInput tGI = new GlobalInput(); //repair:
    tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
    loggerDebug("GrpPlanRiskSave","tGI"+tGI);
    if(tGI==null)
    {
        loggerDebug("GrpPlanRiskSave","页面失效,请重新登陆");   
        FlagStr = "Fail";        
        Content = "页面失效,请重新登陆";  
    }
    else //页面有效
    {
        String Operator  = tGI.Operator ;  //保存登陆管理员账号
        String ManageCom = tGI.ComCode  ; //保存登陆区站,ManageCom=内存中登陆区站代码
      
        CErrors tError = null;
        String fmAction=request.getParameter("fmAction");

        tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLCGrpContSchema.setPrtNo(request.getParameter("PrtNo"));
        tLCGrpContSchema.setMult(request.getParameter("Mult")); //在LCGrpContSchema暂存份数
        
        loggerDebug("GrpPlanRiskSave","团险录单产品组合录入->操作方式：" + fmAction);
        LDPlanSchema tLDPlanSchema = new LDPlanSchema();
        if(fmAction.equals("INSERT||PLANRISK"))
        {
            tLDPlanSchema.setContPlanCode(request.getParameter("PlanRiskCode"));
        }
        
        if(fmAction.equals("DELETE||PLANRISK"))
        {
            String tRiskNum[] = request.getParameterValues("PlanGridNo");
    		String tContPlanCode[] = request.getParameterValues("PlanGrid1");            //险种编码
    		String tRadio[] = request.getParameterValues("InpPlanGridSel"); 
    		loggerDebug("GrpPlanRiskSave","tRadio.length"+tRadio.length);
            for(int index=0;index<tRadio.length;index++)
            {
                if(tRadio[index].equals("1"))
                {
                    tLDPlanSchema.setContPlanCode(tContPlanCode[index]);              
                }           
            }                   
        }    
        
        try
        {
            // 准备传输数据 VData
            VData tVData = new VData();
            
            tVData.add(tLDPlanSchema);
            tVData.add(tLCGrpContSchema);
            tVData.add(tTransferData);
            tVData.add(tGI);            
             
              
             //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
            if (tBusinessDelegate.submitData(tVData,fmAction,busiName))
            {
    		    if (fmAction.equals("INSERT||PLANRISK"))
		        {
		    	    loggerDebug("GrpPlanRiskSave","团单产品组合录入");			        	
		    	    tVData.clear();
		    	    tVData = tBusinessDelegate.getResult();		    	    
		    	    LCContPlanSchema tLCContPlanSchema = new LCContPlanSchema();
		    	    tLCContPlanSchema=(LCContPlanSchema)tVData.getObjectByObjectName("LCContPlanSchema",0);
		            %>
    		            <SCRIPT language="javascript">
    		           
    		            	parent.fraInterface.PlanGrid.addOne("PlanGrid");     
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,1,"<%=tLCContPlanSchema.getContPlanCode()%>");
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,2,"<%=tLCContPlanSchema.getContPlanName()%>");
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,3,"0");
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,4,"0");
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,5,"0");
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,6,"0");
                           
    
    		            </SCRIPT>
		            <%
		        }
		        else if (fmAction.equals("DELETE||PLANRISK"))
		        {
		             %>
    		            <SCRIPT language="javascript">
    		                parent.fraInterface.PlanGrid.delRadioTrueLine();    
    		            </SCRIPT>
		            <%
		        }
	        }
        }
    
        catch(Exception ex)
        {
            Content = "保存失败，原因是:" + ex.toString();
            loggerDebug("GrpPlanRiskSave","aaaa"+ex.toString());
            FlagStr = "Fail";
        }
  

        //如果在Catch中发现异常，则不从错误类中提取错误信息
        if (FlagStr=="")
        {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {                          
                Content ="保存成功！";
                FlagStr = "Succ";
            }
            else                                                                           
            {
                Content = "保存失败，原因是:" + tError.getFirstError();
                FlagStr = "Fail";
            }
        }
 
    } //页面有效区
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.initPlanRiskGrid(); 
	parent.fraInterface.document.all('Mult').value="";	
	parent.fraInterface.document.all('PlanRiskCode').value="";
	parent.fraInterface.document.all('PlanRiskCodeName').value="";
</script>
</html>

