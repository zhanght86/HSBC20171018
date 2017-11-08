<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.schema.LDDeployScriptInfoSchema"%>
<%@page import="com.sinosoft.lis.vschema.LDDeployScriptInfoSet"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%
//程序名称：
//程序功能：产品定义平台发布脚本下载
//创建日期：2011-11-17
//创建人  ：ranjun
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.productdef.*" %>

<%
  String FlagStr = "Succ";
  String Content = ""+"提交成功！"+"";
      	 
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
Content=""+"删除成功！"+"";
 if(!tRiskDeployScriptDownloadBL.submitData(tVData,operator)){

Content=""+"删除失败！"+"";
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
Content=""+"没有找到您要下载的文件！"+"";
 }else{
  file=tRiskDeployScriptDownloadBL.downLoadFiles();
  if(file==null){
   FlagStr = "Fail";
    Content=""+"没有找到您要下载的文件！"+"";
 %>
  <script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
<%
  }else{
  //以下代码在tomcat可以进行下载,但是在WebSphere上下在世无法上到文件,未找到解决方法无法解决
    BufferedOutputStream output = null;
    BufferedInputStream input = null;  
    output = new BufferedOutputStream(response.getOutputStream());
    response.reset();
    response.setContentType("application/octet-stream"); 
   	int count = 0; 
    byte[] buffer = new byte[4096];
  
//写缓冲区
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
 