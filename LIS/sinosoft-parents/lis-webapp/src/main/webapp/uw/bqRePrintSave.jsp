<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PEdorReprintSava.jsp
//程序功能：保全人工核保补打通知书
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.report.f1report.*"%>
  <%@page import="java.io.*"%>
<%
  
    //Get the path of VTS file from LDSysVar table	
    LDSysVarDB tLDSysVarDB = new LDSysVarDB();
 
    String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
    LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);    
    LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
    String strFilePath = tLDSysVarSchema.getV("SysVarValue");
    String strVFFileName = strFilePath + FileQueue.getFileName()+".vts";
    
    //获取存放临时文件的路径
    String strRealPath = application.getRealPath("/").replace('\\','/');
    String strVFPathName = strRealPath + strVFFileName;
  
    //输出参数
    XmlExport txmlExport = new XmlExport();
    CombineVts tcombineVts = null;
    CErrors tError = null;
    String FlagStr = "Fail";
    VData mResult = new VData();
    String Content = "";
    GlobalInput tG = new GlobalInput(); 
	  tG=(GlobalInput)session.getValue("GI");  
	  if(tG == null) {
	  	 loggerDebug("bqRePrintSave","session has expired");
	  	 return;
	  }
    	//接收信息
	  TransferData tTransferData = new TransferData();
	  LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	  String tPrtSeq = request.getParameter("PrtSeq");
	  String tCode = request.getParameter("Code");
	  String tContNo = request.getParameter("ContNo");	
	  String tActivityID = request.getParameter("ActivityID");	
	  loggerDebug("bqRePrintSave","ContNo:"+tContNo);
	  loggerDebug("bqRePrintSave","tCode:"+tCode);
	  boolean flag = true;
	  if (!tContNo.equals(""))
	  {
	  	   //准备公共传输信息
	  	   tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
	  	   tLOPRTManagerSchema.setCode(tCode);
	  	   tLOPRTManagerSchema.setOtherNo(tContNo);
	  	   tLOPRTManagerSchema.setOtherNoType("2");
	  }
	  else
	  {
	  	   flag = false;
	  	   Content = "数据不完整!";
	  }	
	  loggerDebug("bqRePrintSave","flag:"+flag);
	  loggerDebug("bqRePrintSave","tCode:"+tCode);
try
{ 
	      
		if(flag == true){
		    VData tVData = new VData();
		    tVData.add(tLOPRTManagerSchema);
		    tVData.add(tG);
		    
		    //数据传输		    
		    if(tCode.substring(0,3).equals("BQ8")){		        
           // EdorUWNoticeUI tEdorUWNoticeUI = new EdorUWNoticeUI();
            String busiName="EdorUWNoticeUI";
   		  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            if(!tBusinessDelegate.submitData(tVData,"PRINT",busiName))
            {
	              flag=false;
	              Content=tBusinessDelegate.getCErrors().getFirstError().toString();	          
            }
            else
            {
	              mResult = tBusinessDelegate.getResult();	          
	              txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);	          
	              if(txmlExport==null)
	              {
	          	     flag=false;
	          	     Content="没有得到要显示的数据文件";
	              }            
            }
        }
        if(tCode.equals("BQ90")){
           // BqPENoticePrintUI tBqPENoticePrintUI = new BqPENoticePrintUI();
           String busiName="BqPENoticePrintUI";
   		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            if(!tBusinessDelegate.submitData(tVData,"PRINT",busiName))
            {
	              flag=false;
	              Content=tBusinessDelegate.getCErrors().getFirstError().toString();	          
            }
            else
            {
	              mResult = tBusinessDelegate.getResult();	          
	              txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);	          
	              if(txmlExport==null)
	              {
	          	     flag=false;
	          	     Content="没有得到要显示的数据文件";
	              }            
            }
        }
        if(flag == true){
            //合并VTS文件 
             String strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
             tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
               
             ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
             tcombineVts.output(dataStream);
              
             //把dataStream存储到磁盘文件
             AccessVtsFile.saveToFile(dataStream,strVFPathName);
             loggerDebug("bqRePrintSave","==> Write VTS file to disk ");            

             //loggerDebug("bqRePrintSave","===strVFFileName : "+strVFFileName);
             session.putValue("RealPath", strVFPathName);
             //response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
             session.putValue("PrintNo",tPrtSeq );
	         session.putValue("Code","bqnotice"); 
	         session.putValue("ActivityID",tActivityID); 
	         
	         //session.putValue("PrintStream", txmlExport.getInputStream());
	         //response.sendRedirect("../uw/GetF1Print.jsp");
	         request.getRequestDispatcher("../uw/GetF1PrintJ1.jsp").forward(request,response);
        }
    }	
}
catch(Exception e)
{
	  e.printStackTrace();
	  Content = Content.trim()+".提示：异常终止!";
}
if( !Content.equals("") ) {
		loggerDebug("bqRePrintSave","outputStream object has been open");
%>                      
<html>
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<body>
<script language="javascript">	
	alert("<%=Content%>");
	top.close();	
</script>
</body>
</html>
<%
	}
%>

