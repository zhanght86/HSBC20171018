<%@include file="../i18n/language.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%><%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDTestDeploySave.jsp
//�����ܣ���Ʒ�����뷢��
//�������ڣ�2009-3-18
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>  
<%
 //������Ϣ������У�鴦��
 //�������
 

 //PDDeployPubRuleBL pPDDeployPubRuleBL = new PDDeployPubRuleBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 System.out.println(1);
 operator = request.getParameter("operator");
 try{
  System.out.println(2);
 transferData.setNameAndValue("DeplDate",PubFun.getCurrentDate());
 transferData.setNameAndValue("ReleasePlatform",request.getParameter("ReleasePlatform"));
 transferData.setNameAndValue("RuleType",request.getParameter("RuleType"));
 transferData.setNameAndValue("SysType",request.getParameter("SysType"));
 transferData.setNameAndValue("EnvType",request.getParameter("EnvType"));
 transferData.setNameAndValue("flag","N");// ������������ύ����
 }catch(Exception e){e.printStackTrace();}
 if("UWDEPLOY".equals(operator)){
  	transferData.setNameAndValue("Operator",operator);
 	List<String> list=new ArrayList<String>();
    String tChk[] = request.getParameterValues("InpMulline8GridChk");     
    String[] Mulline8Grid1=request.getParameterValues("Mulline8Grid1");  
    for(int i=0;i<tChk.length;i++){
        if(tChk[i].equals("1")){
        list.add(Mulline8Grid1[i]);
        }
    }
    transferData.setNameAndValue("Mulline8Grid",list);
  }else if("UWDEPLOYSELECT".equals(operator)){
     transferData.setNameAndValue("Operator",operator);
     transferData.setNameAndValue("SQL",request.getParameter("SQL"));         
  }else{
   transferData.setNameAndValue("Operator",tG.Operator);
  }
  
  
 //String tChk[] = request.getParameterValues("InpMulline11GridChk"); 
 // if(request.getParameter("PlatformType") != null && request.getParameter("PlatformType").trim().equals("1") && tChk != null){
 //for(int index=0;index<tChk.length;index++)
 //{
  // if(tChk[index].equals("0")) { 
	   //FlagStr = "Fail";
	  // Content = "��δ���Ե�Ҫ��";
%>
	<script type="text/javascript">
 		//parent.fraInterface.afterSubmit("</%=FlagStr%>","</%=Content%>");
	</script>
<% 
	//return;  
  // }
 //}  
 //}

 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  String busiName="PDDeployPubRuleBL";
  tBusinessDelegate.submitData(tVData, operator,busiName);
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = tBusinessDelegate.getCErrors();
  if (!tError.needDealError())
  {                          
   Content = " "+"����ɹ�!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

