
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>  
<%@ page language="java" %>
<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload"/>

<%
    //������Ϣ������У�鴦��
	//�������
	// Variables
    //System.out.println("save");
    int count = 0;
	//�ϴ�·��
	//ȫ�ֱ���
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
    String ImportPath = request.getParameter("ImportPath");
    System.out.println("ImportPath:" + ImportPath);
    String FileName = "";

    String tTransact = request.getParameter("transact");
    System.out.println("�������ǣ�" + tTransact);


	// Initialization
    mySmartUpload.initialize(pageContext);
    mySmartUpload.setTotalMaxFileSize(10000000);

    System.out.println("...��ʼ�����ļ�");
	// Upload
    try {
        mySmartUpload.upload();
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    try {
        // Save the files with their original names in the virtual path "/upload"
        // if it doesn't exist try to save in the physical path "/upload"
        FileName = mySmartUpload.getFiles().getFile(0).getFileName(); //�ļ���

        System.out.println("FileName:" + FileName);
        System.out.println(ImportPath);
        count = mySmartUpload.save(ImportPath);

        // Save the files with their original names in the virtual path "/upload"
        // count = mySmartUpload.save(ImportPath, mySmartUpload.SAVE_VIRTUAL);

        // Display the number of files uploaded
        System.out.println(count + " file(s) uploaded.");

    } catch (Exception e) {
        e.printStackTrace();
    }


//�������
    String FlagStr = "Fail";
    String Content = "";
    TransferData tTransferData = new TransferData();
    BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();    
    CErrors tError = null;

    if (count > 0) {
    	 // ׼���������� VData
        VData tVData = new VData();
        FlagStr = "";
        tTransferData.setNameAndValue("FileName", FileName);
        tTransferData.setNameAndValue("ImportPath", ImportPath);

        
        tVData.add(tTransferData);
        tVData.add(tG);
        try {
            System.out.println("PDLCalculatorULBL--------------");
            if (tBusinessDelegate.submitData(tVData, tTransact, "PDLCalculatorULBL")) {
                Content = "�ļ�������ʾ:����ɹ�";
                FlagStr = "Succ";
            } else {

                tError = tBusinessDelegate.getCErrors();
                Content = "�ļ�����ʧ��: " + tError.getFirstError();
                FlagStr = "Fail";
            }
        } catch (Exception ex) {
            Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
        }
    } else {
        Content = "�����ļ�ʧ��!";
        FlagStr = "Fail";
    }
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

