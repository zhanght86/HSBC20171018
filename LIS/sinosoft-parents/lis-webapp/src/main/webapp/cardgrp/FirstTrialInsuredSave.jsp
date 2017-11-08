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
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  //接收信息，并作校验处理。
  //输入参数
  LCContSchema tLCContSchema = new LCContSchema();
  LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
  LCInsuredDB tOLDLCInsuredDB=new LCInsuredDB();
  LCAddressSchema tLCAddressSchema = new LCAddressSchema();
  LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
  LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
  TransferData tTransferData = new TransferData(); 
  String busiName1="tbContInsuredUI";
  BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
  
  //ContInsuredUI tContInsuredUI   = new ContInsuredUI();
  //输出参数
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("FirstTrialInsuredSave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
  CErrors tError = null;
  String tBmCert = "";
  //后面要执行的动作：添加，修改，删除
  String fmAction=request.getParameter("fmAction");
         
        tLCContSchema.setGrpContNo(request.getParameter("ContNo"));  
        tLCContSchema.setContNo(request.getParameter("ContNo")); 
        tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
        tLCContSchema.setManageCom(request.getParameter("ManageCom"));
        
        tmLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
        tmLCInsuredSchema.setContNo(request.getParameter("ContNo"));
        tmLCInsuredSchema.setGrpContNo(request.getParameter("ContNo"));
                
        tLDPersonSchema.setCustomerNo(request.getParameter("InsuredNo"));
        tLDPersonSchema.setName(request.getParameter("InsuredName"));
        tLDPersonSchema.setSex(request.getParameter("InsuredSex"));      
        tLDPersonSchema.setBirthday(request.getParameter("InsuredBirthday"));
        tLDPersonSchema.setIDType(request.getParameter("InsuredIDType"));
        tLDPersonSchema.setIDNo(request.getParameter("InsuredIDNo"));
        
        tTransferData.setNameAndValue("SavePolType","0"); //保全保存标记，默认为0，标识非保全  
        tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo")); 
        tTransferData.setNameAndValue("FamilyType","0"); //家庭单标记  //
        tTransferData.setNameAndValue("ContType","1"); //团单，个人单标记
        tTransferData.setNameAndValue("PolTypeFlag","0"); //无名单标记
        tTransferData.setNameAndValue("InsuredPeoples","0"); //被保险人人数
        tTransferData.setNameAndValue("InsuredAppAge","0"); //被保险人年龄
        tTransferData.setNameAndValue("SequenceNo","0"); //
        
        tOLDLCInsuredDB.setInsuredNo(request.getParameter("InsuredNo"));
                
        try
         {
             VData tVData = new VData();
             tVData.add(tLCContSchema);
             tVData.add(tLDPersonSchema);
             tVData.add(tLCCustomerImpartSet);
             tVData.add(tLCCustomerImpartDetailSet);             
             tVData.add(tmLCInsuredSchema);
             tVData.add(tLCAddressSchema);
             tVData.add(tOLDLCInsuredDB);
             
             tVData.add(tTransferData);
             tVData.add(tGI);
             
            if (tBusinessDelegate1.submitData(tVData,fmAction,busiName1))
            {
    		    if (fmAction.equals("INSERT||CONTINSURED"))
		        {
		    	    tVData.clear();
		    	    tVData = tBusinessDelegate1.getResult();
		    	    LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
			        mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
			
			        String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
			        String strContNo =  mLCInsuredSchema.getContNo();
			        String strAddressNo= mLCInsuredSchema.getAddressNo();
		            %>
		            <SCRIPT language="javascript">
		            	      parent.fraInterface.GrpGrid.addOne("GrpGrid");    	            	 
                        parent.fraInterface.GrpGrid.setRowColData(parent.fraInterface.GrpGrid.mulLineCount-1,1,"<%=mLCInsuredSchema.getInsuredNo()%>");
                        parent.fraInterface.GrpGrid.setRowColData(parent.fraInterface.GrpGrid.mulLineCount-1,2,"<%=mLCInsuredSchema.getName()%>");
                        parent.fraInterface.GrpGrid.setRowColData(parent.fraInterface.GrpGrid.mulLineCount-1,3,"");       

                        
		            </SCRIPT>
		            <%
		        }
		        if (fmAction.equals("UPDATE||CONTINSURED"))
		        {
		    	    loggerDebug("FirstTrialInsuredSave","------return");
			        	
		    	    tVData.clear();
		    	    tVData = tBusinessDelegate1.getResult();
		    	    loggerDebug("FirstTrialInsuredSave","-----size:"+tVData.size());
		    	    
		    	    LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
			        mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
			
			        String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
			        String strContNo =  mLCInsuredSchema.getContNo();
			        String strAddressNo= mLCInsuredSchema.getAddressNo();
			        loggerDebug("FirstTrialInsuredSave","jsp"+strAddressNo);
		            %>
		            <SCRIPT language="javascript">
		                parent.fraInterface.fm.all("InsuredNo").value="<%=strCustomerNo%>";
		                parent.fraInterface.fm.all("AddressNo").value="<%=strAddressNo%>";  
		                if (parent.fraInterface.fm.all("FamilyType").value=="1"){  
                            parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,1,"<%=mLCInsuredSchema.getInsuredNo()%>");
                            parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,2,"<%=mLCInsuredSchema.getName()%>");
                            parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,3,"<%=mLCInsuredSchema.getSex()%>");
                            parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,4,"<%=mLCInsuredSchema.getBirthday()%>");
                            parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,5,"<%=mLCInsuredSchema.getRelationToMainInsured()%>");
                        }
		            </SCRIPT>
		            <%
		        }
		        if (fmAction.equals("DELETE||CONTINSURED"))
		        {
		          Content = "删除成功！！";
		            %>
		            <SCRIPT language="javascript">
		                parent.fraInterface.fm.all("InsuredNo").value=""; 
		            </SCRIPT>
		            <%
		        }
	        }
    }
    catch(Exception ex)
    {
      Content = "保存失败，原因是:" + ex.toString();
      FlagStr = "Fail";
    }
  

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tBusinessDelegate1.getCErrors();
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
  loggerDebug("FirstTrialInsuredSave","FlagStr:"+FlagStr+"Content:"+Content);
  
} //页面有效区
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

