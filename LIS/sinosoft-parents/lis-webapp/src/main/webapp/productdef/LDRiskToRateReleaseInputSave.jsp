<%@include file="../i18n/language.jsp"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.*" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.sinosoft.productdef.LDRiskToRateReleaseBL "%>

<%
	//后面要执行的动作：添加，修改，删除
	System.out.println("*****************ldc1***********"+"here");
	String transact = "";	
	String FlagStr = "";
	String Content = "";
	VData result = null;
	FileInputStream tFin;
	byte[] tBuf = new byte[1024];
	GlobalInput tGI = new GlobalInput();
	LDRiskToRateReleaseBL tLDRiskToRateReleaseBL=new LDRiskToRateReleaseBL();
    tGI = (GlobalInput)session.getAttribute("GI");	
	CErrors tError =new CErrors();	
	    transact= request.getParameter("Transact");
	    String busiName="IFServerSourceInfoUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();		
		String saveFlag = "";
		String filePath = "";
		Boolean flag = true;
		  try{  // 准备传输数据 VData					    
			    VData cInputData = new VData();
			    TransferData tTransferData= new TransferData();
			   // LPRiskToRateTraceSchema info =new LPRiskToRateTraceSchema();
				cInputData.add(tGI);
	System.out.println("*****************ldc***********"+"here");
				String  parameter = request.getParameter("State");
				String  changeReason =request.getParameter("changeReason");
				String  auditConclusion =request.getParameter("auditConclusion");
				tTransferData.setNameAndValue("parameter",parameter);
				tTransferData.setNameAndValue("changeReason",changeReason);
				tTransferData.setNameAndValue("auditConclusion",auditConclusion);
						
				cInputData.add(tTransferData);
				//cInputData.add(info);
				System.out.println(transact);
				
			   if( tLDRiskToRateReleaseBL.submitData(cInputData,transact)==false){
				   FlagStr = "Fail";
					Content = ""+"生成数据文件失败！"+"";
					System.out.println("************生成数据文件失败！**********");
					

			} else {
					Content = ""+"生成日志文件成功！"+"";
					System.out.println("************生成日志文件成功！**********");
					FlagStr = "Succ";
			}
			   
				//result = tLDRiskToRateReleaseBL.getResult();
		  }catch(Exception ex){
		    Content = transact+""+"失败，原因是:"+"" + ex.toString();
		    FlagStr = "Fail";
		  }

			if("Succ".equals(FlagStr)){
				VData fileContent=tLDRiskToRateReleaseBL.getResult();
				String filepath=(String)fileContent.get(0);
				String fileName=(String)fileContent.get(1);
				String fileNP=filepath+"/"+fileName;
				File zipfile = new File(fileNP);
			try{	
				tFin = new FileInputStream(fileNP);
				String tFileName = null;
				if (tFin != null) {
					response.reset();
					response.setContentType("application/x-download");
					tFileName = URLEncoder.encode(fileName, "UTF-8");
					response.addHeader("Content-Disposition",
							"attachment;filename=" + tFileName);
					OutputStream tFou = response.getOutputStream();
					int start;
					tBuf = new byte[1024];
					while ((start = tFin.read(tBuf)) != -1) {
						tFou.write(tBuf, 0, start);
					}
					tFou.flush();
					tFou.close();
				}
				response.flushBuffer();
				out.clear();
				out = pageContext.pushBody();
				tFin.close();
				Content = ""+"产品费率数据下载成功！"+"";
			} catch (IOException e) {
				Content=""+"下载文件失败！"+"";
				e.printStackTrace();
			} finally {
				if (zipfile.exists()) {
					zipfile.delete();
				}
			}
				
			}
%>
<html>
<head>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");		
</script>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
</html>
