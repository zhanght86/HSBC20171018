 <%
//�������ƣ�FinFeePayQuery.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//         
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
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
   
   loggerDebug("FinFeePayQuery","ʵ������:::::::::::::::::::::::"+ActuGetNo);
   
   LJAGetSchema  tLJAGetSchema  ; //ʵ���ܱ�
   LJAGetSet     tLJAGetSet     ;

//2-��ѯʵ���ܱ�
    tLJAGetSchema = new LJAGetSchema();
    if (ActuGetNo!=null&&!ActuGetNo.equals(""))
    {
      tLJAGetSchema.setActuGetNo(ActuGetNo);
    }
    //�����ݲ�֧�ֶ�һ��ӡˢ�Ŷ��ʵ���ŵĸ���ȷ�ϣ��������ε�ֻ¼��ӡˢ�ŵĲ���
  //  if (OtherNo!=null&&!OtherNo.equals(""))
   // tLJAGetSchema.setOtherNo(OtherNo);
    tLJAGetSchema.setEnterAccDate("");
    VData tVData = new VData();
    tVData.add(tLJAGetSchema);
    tVData.add(tGI);
    loggerDebug("FinFeePayQuery","Start ��ѯʵ���ܱ�");
    //LJAGetQueryUI tLJAGetQueryUI = new LJAGetQueryUI();
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
    if(!tBusinessDelegate.submitData(tVData,"QUERY","LJAGetQueryUI"))
    {
        Content = " ��ѯʵ���ܱ�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
        FlagStr = "Fail";
    }  
    else
    {
//3-��ȡ����ʾ����
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
		    loggerDebug("FinFeePayQuery","��ʾ����");
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
      Content = " ��ѯ�ɹ�";
      FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " ��ѯʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }            
    } //��Ӧ3                  
%> 
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML> 
