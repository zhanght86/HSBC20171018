<%@include file="../i18n/language.jsp"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDSugIncomeDataAlgSave.jsp
//�����ܣ������㷨����
//�������ڣ�2011-10-24
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>  
<%@page import="org.codehaus.xfire.addressing.RandomGUID" %>  
<%@ page language="java" import="com.jspsmart.upload.*"%>
<jsp:useBean id="mySmartUpload" scope="page"
	class="com.jspsmart.upload.SmartUpload" />
  
<%
 //������Ϣ������У�鴦��
 //�������

 
	String FlagStr = "";
	String Content = "";
	String myFileName = "";
	
	String tfilepath = request.getParameter("filepath");
	String tid = request.getParameter("id");
	String sql = "select filename from pd_lmtypemsg where id='"+tid+"'";
	String tname = (new ExeSQL()).getOneValue(sql);
	String filename = "";
	System.out.println(tfilepath);
	
	mySmartUpload.initialize(pageContext);
	mySmartUpload.setTotalMaxFileSize(20000000);
	System.out.println("...��ʼ�����ļ�");
	try	{
		mySmartUpload.upload();
	}
	catch(Exception ex)	{
		ex.printStackTrace();
	}
	
	try {
		for (int i = 0;i < mySmartUpload.getFiles().getCount(); i++) {
			com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(i);
			if (!myFile.isMissing()) {
			    myFileName = myFile.getFileName();
				String   path=getServletContext().getRealPath( "/")+ "productdef\\sugconfig\\"+tname+"\\"; 
				System.out.println(path);
		        String   trace=path+myFileName;
		        Content = ""+"�ϴ��ļ�"+""+myFileName+""+"�ɹ�!"+" ";
			  	FlagStr = "Succ";
				myFile.saveAs(trace, mySmartUpload.SAVE_PHYSICAL);
				myFileName = tname+"/"+myFileName;
				System.out.println(trace);
			} 
		}
	}
	catch (Exception e) { 
		Content = ""+"�ϴ��ļ�ʧ��!"+" ";
	  	FlagStr = "Fail";
		e.printStackTrace();
	}

 

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
	
		parent.unLockPage();
		if ("<%=FlagStr%>" == "Fail" ){             
		    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI('<%=Content%>')) ;  
		    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		}
		else{
			parent.document.getElementById("FILEPATH").value = "<%=myFileName%>";   
	  	    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + encodeURI(encodeURI('<%=Content%>'))  ;  
	    	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		}
	 
</script>
</html>


