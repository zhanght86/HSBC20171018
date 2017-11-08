<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PEdorReprintSava.jsp
//�����ܣ���ȫ�˹��˱�����֪ͨ��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
    
    //��ȡ�����ʱ�ļ���·��
    String strRealPath = application.getRealPath("/").replace('\\','/');
    String strVFPathName = strRealPath + strVFFileName;
  
    //�������
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
    	//������Ϣ
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
	  	   //׼������������Ϣ
	  	   tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
	  	   tLOPRTManagerSchema.setCode(tCode);
	  	   tLOPRTManagerSchema.setOtherNo(tContNo);
	  	   tLOPRTManagerSchema.setOtherNoType("2");
	  }
	  else
	  {
	  	   flag = false;
	  	   Content = "���ݲ�����!";
	  }	
	  loggerDebug("bqRePrintSave","flag:"+flag);
	  loggerDebug("bqRePrintSave","tCode:"+tCode);
try
{ 
	      
		if(flag == true){
		    VData tVData = new VData();
		    tVData.add(tLOPRTManagerSchema);
		    tVData.add(tG);
		    
		    //���ݴ���		    
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
	          	     Content="û�еõ�Ҫ��ʾ�������ļ�";
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
	          	     Content="û�еõ�Ҫ��ʾ�������ļ�";
	              }            
            }
        }
        if(flag == true){
            //�ϲ�VTS�ļ� 
             String strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
             tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
               
             ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
             tcombineVts.output(dataStream);
              
             //��dataStream�洢�������ļ�
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
	  Content = Content.trim()+".��ʾ���쳣��ֹ!";
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

