<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="java.io.*"%>
<%
	loggerDebug("ContGrpInsuInfoSave","start ContGrpInsuInfo.jsp");
	//String tStartDay="";
	//String tEndDay="";
	//String tCalManageCom="";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	CError cError = new CError( );
	//����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��	  
	String FlagStr = "";
	String Content = "";
	String tGrppolno=request.getParameter("ProposalGrpContNo");
	String ManageCom=request.getParameter("ManageCom");
	loggerDebug("ContGrpInsuInfoSave","����Ĺ��������:  "+request.getParameter("ManageCom"));
	String tInsuredNo=request.getParameter("InsuredNo");
	String tName=request.getParameter("Name");
	String tIDNo=request.getParameter("IDNo");
	String tContPlanCode=request.getParameter("ContPlanCode");
	String url=request.getParameter("Url");
	String filename=request.getParameter("FileName");
	String mFinalPath = "";
	String mStrRealPath = application.getRealPath("/").replace('\\', '/');
	mFinalPath = mStrRealPath + "" + url + "" + filename;
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tGrppolno);
	tVData.addElement(ManageCom);
	tVData.addElement(tInsuredNo);
	tVData.addElement(tName);
	tVData.addElement(tIDNo);
	tVData.addElement(tContPlanCode);
	tVData.addElement(mFinalPath);
	tVData.addElement(mStrRealPath);
	//tVData.addElement(tStartDay);
	//tVData.addElement(tEndDay);
	//tVData.addElement(tCalManageCom);
	//tVData.addElement(AgentA00);
    tVData.addElement(tG);

	CErrors tError = null;
	
	String strErrMsg = "";
	
	
	ContGrpInsuredExcelUI  tSExcel = new ContGrpInsuredExcelUI ();
    

    
	int tDelType = -1;      //��������

  String Flag="";  
   
  String action = request.getParameter("fmtransact");
  loggerDebug("ContGrpInsuInfoSave","action = " + action);
  
  String myconfirm = request.getParameter("myconfirm");

  
  
  if (action.equals("download")) {
	
	
//	File tempFile= new File(mFinalPath);
File tempFile= new File(mFinalPath);
	loggerDebug("ContGrpInsuInfoSave",mFinalPath);
    	if(!tempFile.exists())
    	{
    	    loggerDebug("ContGrpInsuInfoSave","NO");
    	    Flag="Fail";
            Content = " ��������Ҫ���ص��ļ����������������أ� ";   
            tDelType=1;
    	}
    	else
    	{
            tDelType = 0;
        }
	} else {
	loggerDebug("ContGrpInsuInfoSave","����Excel�ļ�!");
	//String url=request.getParameter("Url");
	//String filename=request.getParameter("FileName");
	File tempFile= new File(mFinalPath);
	loggerDebug("ContGrpInsuInfoSave","url+filename["+(mFinalPath)+"]");
	
	loggerDebug("ContGrpInsuInfoSave","exists["+tempFile.exists()+"]");
	loggerDebug("ContGrpInsuInfoSave","myconfirm["+myconfirm+"]");
	//myconfirm ��һ�����ص�����
	if (tempFile.exists()&&!myconfirm.equals("1") )
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
        if(!tSExcel.submitData(tVData,"Create")) {
        Flag="Success";
           Content = " ����ɹ� ";
           
		if( tSExcel.mErrors.needDealError() ) {
			strErrMsg = tSExcel.mErrors.getFirstError();
		} else {
			strErrMsg = "AgentNewContactReport�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
		}
		return;
  }
        else 
        { Flag="Success";
           Content = " ����ɹ� ";
        }
        loggerDebug("ContGrpInsuInfoSave",Content + "\n" + Flag + "\n---������� End---\n\n");
    }
  }
%>
 
<html>
<script language="javascript"> 
  <!--alert('<%=tDelType%>');-->
	if (<%=tDelType%> == 1) {
		 <!--alert('<%=Content%>');-->
		 <!--��fm.all('myconfirm').value ������Ϊ��ʼ״̬ "0"	�𵽿�������	 --> 
		 parent.fraInterface.fm.all('myconfirm').value = '0';
		 parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
	} else {
	  <!-- tDelType = 0; -->
	  <!-- ������治����������� -->
	  if (<%=tDelType%> != -1)
	  parent.fraInterface.downAfterSubmit('<%=mFinalPath%>',parent.fraInterface.fm.FileName.value );
	}
</script>
</html>
