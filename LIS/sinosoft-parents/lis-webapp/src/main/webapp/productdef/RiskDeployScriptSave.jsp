<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.schema.LDDeployScriptInfoSchema"%>
<%@page import="com.sinosoft.lis.vschema.LDDeployScriptInfoSet"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%
//�������ƣ�
//�����ܣ���Ʒ����ƽ̨�����ű�����
//�������ڣ�2011-11-17
//������  ��ranjun
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.productdef.*" %>

<%
  String FlagStr = "Succ";
  String Content = ""+"�ύ�ɹ���"+"";
      	 
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");
  
  TransferData tTransferData = new TransferData();
 String operator = request.getParameter("operator");
 tTransferData.setNameAndValue("operator",operator);
  LDDeployScriptInfoSet aLDDeployScriptInfoSet=new LDDeployScriptInfoSet();
 try{
 String tChk[] = request.getParameterValues("InpMullineGridChk"); 
 //String tCh[] = request.getParameterValues("InpMullineGridSel"); 
 String[] MullineGrid1=request.getParameterValues("MullineGrid1");
 String[] MullineGrid2=request.getParameterValues("MullineGrid2");
 String[] MullineGrid3=request.getParameterValues("MullineGrid3");
 String[] MullineGrid4=request.getParameterValues("MullineGrid4");
 String[] MullineGrid5=request.getParameterValues("MullineGrid5");
 String[] MullineGrid6=request.getParameterValues("MullineGrid6");
 String[] MullineGrid7=request.getParameterValues("MullineGrid7");
 String[] MullineGrid8=request.getParameterValues("MullineGrid8");
 String[] MullineGrid9=request.getParameterValues("MullineGrid9");

  for(int index=0;index<tChk.length;index++){
  if("1".equals(tChk[index])){
  LDDeployScriptInfoSchema aLDDeployScriptInfoSchema=new LDDeployScriptInfoSchema();
  aLDDeployScriptInfoSchema.setRiskCode(MullineGrid1[index]);
  aLDDeployScriptInfoSchema.setType(MullineGrid2[index]);
  aLDDeployScriptInfoSchema.setEnvironment(MullineGrid3[index]);
  aLDDeployScriptInfoSchema.setVersion(MullineGrid4[index]);
  aLDDeployScriptInfoSchema.setName(MullineGrid5[index]);
  aLDDeployScriptInfoSchema.setPath(MullineGrid6[index]);
  aLDDeployScriptInfoSchema.setMakeDate(MullineGrid7[index]);
  aLDDeployScriptInfoSchema.setSeriNo(MullineGrid8[index]);
  aLDDeployScriptInfoSchema.setNote(MullineGrid9[index]);
  aLDDeployScriptInfoSet.add(aLDDeployScriptInfoSchema);
  }
  }
}catch(Exception e){
e.printStackTrace();
}
  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
  tVData.addElement(aLDDeployScriptInfoSet);
  File file=null;
RiskDeployScriptDownloadBL tRiskDeployScriptDownloadBL=new RiskDeployScriptDownloadBL();
System.out.println(operator);
if("del".equals(operator)){
Content=""+"ɾ���ɹ���"+"";
 if(!tRiskDeployScriptDownloadBL.submitData(tVData,operator)){

Content=""+"ɾ��ʧ�ܣ�"+"";
 }
  FlagStr = "del";
 %>
  <script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
<%
}else{
 if(!tRiskDeployScriptDownloadBL.submitData(tVData,operator)){
 FlagStr = "Fail";
Content=""+"û���ҵ���Ҫ���ص��ļ���"+"";
 }else{
  file=tRiskDeployScriptDownloadBL.downLoadFiles();
  if(file==null){
   FlagStr = "Fail";
    Content=""+"û���ҵ���Ҫ���ص��ļ���"+"";
 %>
  <script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
<%
  }else{
  //���´�����tomcat���Խ�������,������WebSphere���������޷��ϵ��ļ�,δ�ҵ���������޷����
    BufferedOutputStream output = null;
    BufferedInputStream input = null;  
    output = new BufferedOutputStream(response.getOutputStream());
    response.reset();
    response.setContentType("application/octet-stream"); 
   	int count = 0; 
    byte[] buffer = new byte[4096];
  
//д������
        try {

            input = new BufferedInputStream(new FileInputStream(file));
            response.setHeader("Content-Disposition","attachment; filename="+file.getName()+"");
    		response.setContentLength((int) file.length());
            int len = 0;
            while((len = input.read(buffer)) >0)
            {
              output.write(buffer,0,len);

            }
           output.flush();
           output.close();
           input.close();
        }
        catch (Exception e) {
          e.printStackTrace();
        }finally {
            if (input != null) input.close();
            if (output != null) output.close();
        }
 }
}
}
%>
<html>
</html>
 