<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//�������ƣ�PDRiskGraceSave.jsp
//�����ܣ�
//�������ڣ�2011-03-03
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
	<%@page import="com.sinosoft.service.*" %>
	<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
 //������Ϣ������У�鴦��
 //�������
 //�������
  String FlagStr = "";
  String Content = "";
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String fmAction=request.getParameter("gOperator");
  System.out.println("gOperator:"+fmAction); 
  String mGraceCalCode = "";
  
  TransferData tTransferData = new TransferData(); 
  String busiName="PDRiskGraceUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  String tCalCodeType = request.getParameter("DutyGraceCalCodeSwitchFlag"); 
  tTransferData.setNameAndValue("CalCodeType",tCalCodeType);
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getAttribute("GI");  //�μ�loginSubmit.jsp
  System.out.println("tGI"+tGI);
  if(tGI==null)
  {
    System.out.println("ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = ""+"ҳ��ʧЧ,�����µ�½"+"";  
  }
  else //ҳ����Ч
  {
    CErrors tError = null;
    
    String riskCode = request.getParameter("gRiskcode");
    
    PD_LMRiskPaySchema mPD_LMRiskPaySchema = new PD_LMRiskPaySchema();
    mPD_LMRiskPaySchema.setRiskCode(riskCode);
    mPD_LMRiskPaySchema.setGracePeriod(request.getParameter("GracePeriod2"));
    mPD_LMRiskPaySchema.setGracePeriodUnit(request.getParameter("GracePeriodUnit"));
    mPD_LMRiskPaySchema.setGraceDateCalMode(request.getParameter("GraceDateCalMode"));
    mPD_LMRiskPaySchema.setOverdueDeal(request.getParameter("OverdueDeal"));
    mPD_LMRiskPaySchema.setUrgePayFlag(request.getParameter("UrgePayFlag"));
    
     try{
     	 VData tVData = new VData();
	     tVData.add(mPD_LMRiskPaySchema);
	     
	     tVData.add(tTransferData);
	     tVData.add(tGI);
	     //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	     if (tBusinessDelegate.submitData(tVData,fmAction,busiName))
	     {
   			 System.out.println("submit success");
	     }
     }
		 catch(Exception ex)
		 {
		   Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
		   FlagStr = "Fail";
		 }
		 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	   if ("".equals(FlagStr))
	   {
	     tError = tBusinessDelegate.getCErrors();
	     if (!tError.needDealError())
	     {
	       Content =""+"�����ɹ�"+"";
	     	 FlagStr = "Succ";
	     	 if("save".equals(fmAction) || "update".equals(fmAction)){
	     	    VData tVData = new VData();
	      		tVData = tBusinessDelegate.getResult();
	      		PD_LMRiskPaySchema tPD_LMRiskPaySchema = new PD_LMRiskPaySchema();
	      		tPD_LMRiskPaySchema = (PD_LMRiskPaySchema)tVData.getObjectByObjectName("PD_LMRiskPaySchema",0);
	      		mGraceCalCode = tPD_LMRiskPaySchema.getGraceCalCode();
	      		if(mGraceCalCode==null){
	      		  mGraceCalCode = "";
	      		}
	      		System.out.println("mGraceCalCode:" + mGraceCalCode);
	      		RiskState.setState(riskCode, ""+"��Ʒ�����"+"->"+"��������Ϣ"+"", "1");
	     	 }else{
	     	 	 RiskState.setState(riskCode, ""+"��Ʒ�����"+"->"+"��������Ϣ"+"", "0");
	     	 }
	     }
	     else                                                                           
	     {
	     	 Content = ""+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
	     	 FlagStr = "Fail";
	     }
	   }
   }
 
%>
<html>
<script type="text/javascript">
	parent.fraInterface.fmP.all("GraceCalCode").value="<%=mGraceCalCode%>";
	parent.fraInterface.afterSubmitRiskPay("<%=FlagStr%>","<%=Content%>","<%=fmAction%>");
</script>
</html>