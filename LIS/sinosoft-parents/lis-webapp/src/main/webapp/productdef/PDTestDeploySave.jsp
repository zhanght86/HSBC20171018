<%@include file="../i18n/language.jsp"%>



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
  
<%
 //������Ϣ������У�鴦��
 //�������
 


 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 String tFromPath = getServletContext().getRealPath( "/")+ "productdef\\sugconfig\\";
 
 /*
 	fm.all("RiskCode").value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.all("RequDate").value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.all("MissionID").value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.all("SubMissionID").value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.all("ActivityID").value = Mulline9Grid.getRowColData(selNo-1,7);
 */
  //��transferData���뷢��ԭ��
 

 String tChk[] = request.getParameterValues("InpMulline11GridChk"); 

 if(request.getParameter("PlatformType") != null && request.getParameter("PlatformType").trim().equals("1") && tChk != null){
 for(int index=0;index<tChk.length;index++)
 {
   if(tChk[index].equals("0")) { 
	   FlagStr = "Fail";
	   Content = ""+"��δ���Ե�Ҫ��"+"";
%>
	<script type="text/javascript">
 		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	</script>
<% 
   }
 }  
 }
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 String tChk1[] = request.getParameterValues("InpMulline9GridChk"); 
 String[] Mulline9Grid1=request.getParameterValues("Mulline9Grid1");
 String[] Mulline9Grid2=request.getParameterValues("Mulline9Grid2");
 String[] Mulline9Grid3=request.getParameterValues("Mulline9Grid3");
 String[] Mulline9Grid5=request.getParameterValues("Mulline9Grid5");
 String[] Mulline9Grid6=request.getParameterValues("Mulline9Grid6");
 String[] Mulline9Grid7=request.getParameterValues("Mulline9Grid7");
 String ReleasePlatform=request.getParameter("ReleasePlatform");
 String SysType=request.getParameter("SysType");
 String EnvType=request.getParameter("EnvType");
 String PDNOY=request.getParameter("pd_noy");
 String IsDeleteCoreDataBeforeInsert=request.getParameter("IsDeleteCoreDataBeforeInsert");
 String deployReason=request.getParameter("deployReason");
 System.out.println(request.getParameter("PlatformType") + " "+"����ԭ��Ϊ��"+"" + request.getParameter("deployReason"));
//������ʽ=�� Inp+MulLine������+Chk��  
for(int index=0;index<tChk1.length;index++){
	
	transferData = new TransferData();
    if(tChk1[index].equals("1")){
             transferData.setNameAndValue("RiskCode",Mulline9Grid1[index]);  
             transferData.setNameAndValue("RequDate",Mulline9Grid3[index]);
             transferData.setNameAndValue("MissionID",Mulline9Grid5[index]);
             transferData.setNameAndValue("ActivityID",Mulline9Grid7[index]);
             transferData.setNameAndValue("SubMissionID",Mulline9Grid6[index]);
             transferData.setNameAndValue("RiskName",Mulline9Grid2[index]);
             transferData.setNameAndValue("DeplDate",PubFun.getCurrentDate());
             transferData.setNameAndValue("Operator",tG.Operator); 
             transferData.setNameAndValue("ReleasePlatform",ReleasePlatform);
             transferData.setNameAndValue("SysType",SysType);
             transferData.setNameAndValue("EnvType",EnvType);
             transferData.setNameAndValue("flag","N");// ������������ύ����
             transferData.setNameAndValue("IsDeleteCoreDataBeforeInsert",IsDeleteCoreDataBeforeInsert);
             transferData.setNameAndValue("deployReason",deployReason);
             transferData.setNameAndValue("PDNOY",PDNOY);
	try {
		  // ׼���������� VData
		  VData tVData = new VData();
		  tVData.add(tG);
		  transferData.setNameAndValue("FromPath",tFromPath);
		  tVData.add(transferData);
		  PDTestDeployBL pDTestDeployBL = new PDTestDeployBL();
		  pDTestDeployBL.submitData(tVData,operator);
	 }catch(Exception ex){
		  Content =Content+ ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
		  FlagStr = "Fail";
	 }                   
 	}             
}

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
   Content = " "+"����ɹ�!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"����ʧ��!"+"";
   FlagStr = "Fail";
  }

 //��Ӹ���Ԥ����
%>                      
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

