<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.agentprint.*"%>
<%@page import="java.io.*"%>
<%
	loggerDebug("EdorOutDayDoneSave","start EdorOutDayDoneSave.jsp");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	CError cError = new CError( );
	//����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��	  
	String FlagStr = "";
	String Content = "";
    String tManageCom = request.getParameter("ManageCom");
    String tSaleChnl = request.getParameter("SaleChnl");
    String tStartDate = request.getParameter("StartDate");
    String tEndDate = request.getParameter("EndDate");
	//�õ�ģ��·��������.
    LDSysVarDB tLDSysVarDB =  new LDSysVarDB();;

    tLDSysVarDB.setSysVar("XSEXCelTemplate");
   if (tLDSysVarDB.getInfo() == false)
   {
    loggerDebug("EdorOutDayDoneSave","LDSysVarȡ�ļ�·��XSEXCelTemplate����ʧ��");
   
   }
    String mFilePath = tLDSysVarDB.getSysVarValue();
   String TemplatePath = application.getRealPath(mFilePath)+"/";

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tStartDate);
	tVData.addElement(tEndDate);
	tVData.addElement(tManageCom);
	tVData.addElement(tSaleChnl);
    tVData.addElement(tG);
    tVData.addElement(TemplatePath);

	CErrors tError = null;
	
	String strErrMsg = "";
	
	
    EdorOutDayDoneUI  tPrt = new EdorOutDayDoneUI ();
    

    
	int tDelType = -1;      //��������

  String Flag="";  
   
  String action = request.getParameter("fmtransact");
  loggerDebug("EdorOutDayDoneSave","action = " + action);
  
  String myconfirm = request.getParameter("myconfirm");
	
  if (action.equals("download")) {
	String url=request.getParameter("Url");
	String filename=request.getParameter("FileName");
	File tempFile= new File(url+filename);
	loggerDebug("EdorOutDayDoneSave",url+filename);
    	if(!tempFile.exists())
    	{
    	    loggerDebug("EdorOutDayDoneSave","NO");
    	    Flag="Fail";
            Content = " ��������Ҫ���ص��ļ����������������أ� ";   
            tDelType=1;
    	}
    	else
    	{
            tDelType = 0;
        }
	} else {
	
	String url=request.getParameter("Url");
	String filename=request.getParameter("FileName");
	File tempFile= new File(url+filename);
	loggerDebug("EdorOutDayDoneSave","url+filename["+(url+filename)+"]");
	
	loggerDebug("EdorOutDayDoneSave","exists["+tempFile.exists()+"]");
	loggerDebug("EdorOutDayDoneSave","myconfirm["+myconfirm+"]");
	//myconfirm ��һ�����ص�����
	if (tempFile.exists() && !myconfirm.equals("1"))
  {
  //��������JavaScript����ص�ConfirmSelect���ٴ��ύ
	%>
		<script language="javascript">
	   parent.fraInterface.ConfirmSelect();
		</script>
  <%
	}
	else {
        //ɾ�����ļ� �ļ������ڻ��ļ���������Ҫ���¼���ʱ�ߴ˷�֧
        //comment by jiaqiangli 2007-07-06 У�鲻ͨ��֮��ɾ�����ļ�
        //tempFile.delete();
        tDelType = 1;
        if(!tPrt.submitData(tVData,"Create")) {
            Flag="Fail";
           Content = " û�з������������ݣ���";
		if( tPrt.mErrors.needDealError() ) {
			strErrMsg = tPrt.mErrors.getFirstError();
		} else {
			strErrMsg = "AgentNewContactReport�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
		}
  }
        else 
        { Flag="Success";
           Content = " ����ɹ� ";
        }
        loggerDebug("EdorOutDayDoneSave",Content + "\n" + Flag + "\n---������� End---\n\n");
    }
  }
%>
 
<html>
<script language="javascript"> 
  <!--alert('<%=tDelType%>');-->
	if (<%=tDelType%> == 1) {
		 <!--alert('<%=Content%>');-->
		 <!--��document.all('myconfirm').value ������Ϊ��ʼ״̬ "0"	�𵽿�������	 --> 
		 parent.fraInterface.document.all('myconfirm').value = '0';
		 parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
	} else {
	  <!-- tDelType = 0; -->
	  <!-- ������治����������� -->
	  if (<%=tDelType%> != -1)
	  parent.fraInterface.downAfterSubmit(parent.fraInterface.fm.Url.value,parent.fraInterface.fm.FileName.value );
	}
</script>
</html>
