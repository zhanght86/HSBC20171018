 <%
//程序名称：FinFeePayQuery.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//         
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>    
  <%@page import="com.sinosoft.service.*" %>
  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
   GlobalInput tGI = new GlobalInput(); 
   tGI=(GlobalInput)session.getValue("GI");  
   CErrors tError = null;          
   String FlagStr = "";
   String Content = "";
   String ActuGetNo = request.getParameter("ActuGetNo");
  // String OtherNo = request.getParameter("OtherNo");
   
   loggerDebug("FinFeePayQuery","实付号码:::::::::::::::::::::::"+ActuGetNo);
   
   LJAGetSchema  tLJAGetSchema  ; //实付总表
   LJAGetSet     tLJAGetSet     ;

//2-查询实付总表
    tLJAGetSchema = new LJAGetSchema();
    if (ActuGetNo!=null&&!ActuGetNo.equals(""))
    {
      tLJAGetSchema.setActuGetNo(ActuGetNo);
    }
    //由于暂不支持对一个印刷号多个实付号的付费确认，所以屏蔽掉只录入印刷号的操作
  //  if (OtherNo!=null&&!OtherNo.equals(""))
   // tLJAGetSchema.setOtherNo(OtherNo);
    tLJAGetSchema.setEnterAccDate("");
    VData tVData = new VData();
    tVData.add(tLJAGetSchema);
    tVData.add(tGI);
    loggerDebug("FinFeePayQuery","Start 查询实付总表");
    //LJAGetQueryUI tLJAGetQueryUI = new LJAGetQueryUI();
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
    if(!tBusinessDelegate.submitData(tVData,"QUERY","LJAGetQueryUI"))
    {
        Content = " 查询实付总表失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
        FlagStr = "Fail";
    }  
    else
    {
//3-提取并显示数据
		    tVData.clear();    
		    tLJAGetSet = new LJAGetSet();
		    tLJAGetSchema = new LJAGetSchema();     
		    tVData = tBusinessDelegate.getResult();
		    tLJAGetSet.set((LJAGetSet)tVData.getObjectByObjectName("LJAGetSet",0));  
		    tLJAGetSchema =(LJAGetSchema)tLJAGetSet.get(1);
		    String PayMode= tLJAGetSchema.getPayMode();
		    if(PayMode==null) PayMode="";
		    String BankCode=tLJAGetSchema.getBankCode();
		    if(BankCode==null) BankCode="";
        String AccNo=tLJAGetSchema.getBankAccNo();
        if(AccNo==null) AccNo="";
        String AccName=tLJAGetSchema.getAccName();
        if(AccName==null) AccName="";  
		    loggerDebug("FinFeePayQuery","显示数据");
		    loggerDebug("FinFeePayQuery","PayMode:"+PayMode);
		    String EnterAccDate = tLJAGetSchema.getEnterAccDate();
		    if(EnterAccDate==null) EnterAccDate="";
		    String mOtherno=tLJAGetSchema.getOtherNo();
		    if(mOtherno==null) mOtherno="";
		    String mOtherNoType=tLJAGetSchema.getOtherNoType();
		    if(mOtherNoType==null) mOtherNoType="";
		    String mDrawer=tLJAGetSchema.getDrawer();
		    if(mDrawer==null) mDrawer="";
		    String mODrawerID=tLJAGetSchema.getDrawerID();
		    if(mODrawerID==null) mODrawerID="";
%>
<Script language="javascript">
	 	//parent.fraInterface.QueryLJAGetGrid.addOne("QueryLJAGetGrid");
   	parent.fraInterface.QueryLJAGetGrid.setRowColData(0,1,"<%=tLJAGetSchema.getActuGetNo()%>");
   	parent.fraInterface.QueryLJAGetGrid.setRowColData(0,2,"<%=mOtherno%>");
   	parent.fraInterface.QueryLJAGetGrid.setRowColData(0,3,"<%=mOtherNoType%>");
   	parent.fraInterface.QueryLJAGetGrid.setRowColData(0,4,"<%=PayMode%>");
   	parent.fraInterface.QueryLJAGetGrid.setRowColData(0,5,"<%=tLJAGetSchema.getCurrency()%>");
   	parent.fraInterface.QueryLJAGetGrid.setRowColData(0,6,"<%=tLJAGetSchema.getSumGetMoney()%>");
   	parent.fraInterface.QueryLJAGetGrid.setRowColData(0,7,"<%=EnterAccDate%>");
  	parent.fraInterface.QueryLJAGetGrid.setRowColData(0,8,"<%=mDrawer%>");
   	parent.fraInterface.QueryLJAGetGrid.setRowColData(0,9,"<%=mODrawerID%>");
    parent.fraInterface.fmSave.ActuGetNo.value="<%=tLJAGetSchema.getActuGetNo()%>";
    parent.fraInterface.fmSave.PolNo.value="<%=mOtherno%>";
    parent.fraInterface.fmSave.Currency.value="<%=tLJAGetSchema.getCurrency()%>";
    parent.fraInterface.fmSave.GetMoney.value="<%=tLJAGetSchema.getSumGetMoney()%>"; 
    parent.fraInterface.fmSave.PayMode.value="<%=PayMode%>";  
    var tPaymode ="<%=tLJAGetSchema.getPayMode()%>";  
//    if(tPaymode=="4")
//    {
//	    parent.fraInterface.fmSave.BankCode.value="<%=tLJAGetSchema.getBankCode()%>";
//	    parent.fraInterface.fmSave.BankAccNo.value="<%=tLJAGetSchema.getBankAccNo()%>";  
//	    parent.fraInterface.fmSave.BankAccName.value="<%=tLJAGetSchema.getAccName()%>"; 
//    } 
		if(tPaymode=="9")
		{
		  parent.fraInterface.fmSave.BankCode9.value="<%=tLJAGetSchema.getBankCode()%>";
	    parent.fraInterface.fmSave.BankAccNo9.value="<%=tLJAGetSchema.getBankAccNo()%>";  
	    parent.fraInterface.fmSave.BankAccName9.value="<%=tLJAGetSchema.getAccName()%>"; 
		}
    else if(tPaymode=="2"|| tPaymode=="3")
    {
    	parent.fraInterface.fmSave.BankCode2.value="<%=BankCode%>";
    	parent.fraInterface.fmSave.ChequeNo.value="<%=tLJAGetSchema.getBankAccNo()%>";
    }
    else
    {

	    parent.fraInterface.fmSave.BankCode2.value="";
	    parent.fraInterface.fmSave.ChequeNo.value="";
	    parent.fraInterface.fmSave.BankCode.value="";
	    parent.fraInterface.fmSave.BankAccNo.value="";  
	    parent.fraInterface.fmSave.BankAccName.value="";
    }                      

    parent.fraInterface.showBankAccNo();
</Script>
<% 
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content = " 查询成功";
      FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " 查询失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }            
    } //对应3                  
%> 
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML> 
