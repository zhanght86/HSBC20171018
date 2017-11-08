<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：LDPersonSave.jsp
//程序功能：
//创建日期：2002-06-27 08:49:52
//创建人  ：CrtHtml程序创建
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
  <%@page import="java.text.DecimalFormat"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%
  //接收信息，并作校验处理。
  //输入参数
  
  LCGrpContSchema tLCGrpContSchema   = new LCGrpContSchema();
  LCGrpPolSet mLCGrpPolSet =new LCGrpPolSet();
 
  TransferData tTransferData = new TransferData(); 
  //GroupRiskUI tGroupRiskUI   = new GroupRiskUI();
  String busiName="tbGroupRiskUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  String FlagStr = "";
  String Content = "";
  String mLoadFlag = "";
  mLoadFlag=request.getParameter("LoadFlag");
  String askflag=(String)request.getParameter("AskFlag");
  loggerDebug("GroupRiskSave","@@@@@@@@@@@ASKFLAG:"+askflag);
  tTransferData.setNameAndValue("LoadFlag",mLoadFlag);
  
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  loggerDebug("GroupRiskSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("GroupRiskSave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
   String Operator  = tGI.Operator ;  //保存登陆管理员账号
   String ManageCom = tGI.ComCode  ; //保存登陆区站,ManageCom=内存中登陆区站代码
  
  CErrors tError = null;
  String tBmCert = "";
  loggerDebug("GroupRiskSave","aaaa");
  //后面要执行的动作：添加，修改，删除
  String fmAction=request.getParameter("fmAction");
    loggerDebug("GroupRiskSave","fmAction:"+fmAction); 

/*        
  String tLimit="";
  String CustomerNo="";
*/ 
    if(fmAction.equals("INSERT||GROUPRISK"))
    {
        tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLCGrpContSchema.setPrtNo(request.getParameter("PrtNo"));
        
        LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
        tLCGrpPolSchema.setRiskCode(request.getParameter("RiskCode"));
        tLCGrpPolSchema.setCValiDate(request.getParameter("CValiDate"));
        tLCGrpPolSchema.setPayIntv(request.getParameter("PayIntv"));
        tLCGrpPolSchema.setExpPremium(request.getParameter("ExpPrem"));
        tLCGrpPolSchema.setExpAmnt(request.getParameter("ExpAmnt"));
        tLCGrpPolSchema.setBonusFlag(request.getParameter("BonusFlag"));
        //因为界面中不会传入个值，故在此将此值赋为1000000
        tLCGrpPolSchema.setExpPeoples(request.getParameter("Peoples")); 
           //管理费用率和销售费用率------haopan
       tLCGrpPolSchema.setPeoples2(request.getParameter("Peop"));
       tLCGrpPolSchema.setPrem(request.getParameter("ExpPrem"));
       tLCGrpPolSchema.setAmnt(request.getParameter("ExpAmnt"));
        tLCGrpPolSchema.setStandbyFlag2(request.getParameter("ManaFloatRate"));
        tLCGrpPolSchema.setStandbyFlag3(request.getParameter("SaleFloatRate"));

        mLCGrpPolSet.add(tLCGrpPolSchema);
    }
    if(fmAction.equals("DELETE||GROUPRISK"))
    {
        String tRiskNum[] = request.getParameterValues("RiskGridNo");
		String tRiskCode[] = request.getParameterValues("RiskGrid1");            //险种编码
		String tChk[] = request.getParameterValues("InpRiskGridChk"); 
		loggerDebug("GroupRiskSave","tChk.length"+tChk.length);
         tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLCGrpContSchema.setPrtNo(request.getParameter("PrtNo"));
        for(int index=0;index<tChk.length;index++)
        {
          if(tChk[index].equals("1"))
          {
            LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
            loggerDebug("GroupRiskSave","Riskcode"+tRiskCode[index]+":"+request.getParameter("GrpContNo"));
            tLCGrpPolSchema.setRiskCode(tRiskCode[index]);
            tLCGrpPolSchema.setGrpContNo(request.getParameter("GrpContNo")); 
            mLCGrpPolSet.add(tLCGrpPolSchema);
          
          }           
        }                   
    }    
        try
         {
            // 准备传输数据 VData
             VData tVData = new VData();
             
             tVData.add(mLCGrpPolSet);
             tVData.add(tLCGrpContSchema);
             tVData.add(tTransferData);
             tVData.add(tGI);
             
             
              
             //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
            if ( tBusinessDelegate.submitData(tVData,fmAction,busiName))
            {
    		    if (fmAction.equals("INSERT||GROUPRISK"))
		        {
		    	    loggerDebug("GroupRiskSave","11111------return");
			        	
		    	    tVData.clear();
		    	    tVData = tBusinessDelegate.getResult();
		    	    
		    	    LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet(); 
			        tLCGrpPolSet=(LCGrpPolSet)tVData.getObjectByObjectName("LCGrpPolSet",0);
			        TransferData mTransferData = new TransferData();
			        mTransferData=(TransferData)tVData.getObjectByObjectName("TransferData",0);
			        String RiskName=(String)mTransferData.getValueByName("RiskName");
			        loggerDebug("GroupRiskSave","RiskName"+RiskName);
		            %>
		            <SCRIPT language="javascript">
		           var askflag=<%=askflag%>;
		    				<%  String FORMATMODOL = "0.00";
    					 DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);
		    				String prem=mDecimalFormat.format(tLCGrpPolSet.get(1).getPrem());
		    				String Amnt=mDecimalFormat.format(tLCGrpPolSet.get(1).getAmnt());%>
		            	parent.fraInterface.RiskGrid.addOne("RiskGrid");     
                        parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,1,"<%=tLCGrpPolSet.get(1).getRiskCode()%>");
                        parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,2,"<%=RiskName%>");
                        parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,3,"<%=tLCGrpPolSet.get(1).getPayIntv()%>");
                        if(askflag=="1"||askflag==1)
                        parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,4,"<%=tLCGrpPolSet.get(1).getPeoples2()%>");
                      else
                        parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,4,"<%=tLCGrpPolSet.get(1).getExpPeoples()%>");
                        if (<%=tLCGrpPolSet.get(1).getAmnt()%>==null)
                            parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,5,"0");
                        else
                        	if(askflag=="1"||askflag==1)
                        	parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,5,"<%=Amnt%>");
                        else
                            parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,5,"<%=tLCGrpPolSet.get(1).getPeoples2()%>");
                            
                        if (<%=tLCGrpPolSet.get(1).getPrem()%>==null)
                            parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,6,"0");
                        else
                            parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,6,"<%=prem%>");
												if(askflag=="1"||askflag==1)
												{
													parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,7,"<%=tLCGrpPolSet.get(1).getStandbyFlag2()%>");
													parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,8,"<%=tLCGrpPolSet.get(1).getStandbyFlag3()%>");
													}
		            </SCRIPT>
		            <%
		        }
		        else if (fmAction.equals("DELETE||GROUPRISK"))
		        {
		             %>
		            <SCRIPT language="javascript">
		                parent.fraInterface.RiskGrid.delCheckTrueLine ();    
		            </SCRIPT>
		            <%
		        }
	        }
    }
    
    catch(Exception ex)
    {
      Content = "保存失败，原因是:" + ex.toString();
      loggerDebug("GroupRiskSave","aaaa"+ex.toString());
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
	parent.fraInterface.document.all('RiskCode').value="";
	parent.fraInterface.document.all('PayIntv').value="";
	//parent.fraInterface.document.all('BonusFlag').value="";
	//parent.fraInterface.divBonusFlag.style.display='none';
</script>
</html>

