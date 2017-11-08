
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
    //接收信息，并作校验处理。
	//输入参数
	// Variables
    //System.out.println("save");
    int count = 0;
	//上传路径
	//全局变量
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
    String ImportPath = request.getParameter("ImportPath");
    System.out.println("ImportPath:" + ImportPath);
    String FileName = "";

    String tTransact = request.getParameter("transact");
    System.out.println("操作符是：" + tTransact);


	// Initialization
    mySmartUpload.initialize(pageContext);
    mySmartUpload.setTotalMaxFileSize(10000000);

    System.out.println("...开始上载文件");
	// Upload
    try {
        mySmartUpload.upload();
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    try {
        // Save the files with their original names in the virtual path "/upload"
        // if it doesn't exist try to save in the physical path "/upload"
        FileName = mySmartUpload.getFiles().getFile(0).getFileName(); //文件名

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


//输出参数
    String FlagStr = "Fail";
    String Content = "";
    TransferData tTransferData = new TransferData();
    BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();    
    CErrors tError = null;

    if (count > 0) {
    	 // 准备传输数据 VData
        VData tVData = new VData();
        FlagStr = "";
        tTransferData.setNameAndValue("FileName", FileName);
        tTransferData.setNameAndValue("ImportPath", ImportPath);

        
        tVData.add(tTransferData);
        tVData.add(tG);
        try {
            System.out.println("PDLCalculatorULBL--------------");
            if (tBusinessDelegate.submitData(tVData, tTransact, "PDLCalculatorULBL")) {
                Content = "文件导入提示:保存成功";
                FlagStr = "Succ";
            } else {

                tError = tBusinessDelegate.getCErrors();
                Content = "文件导入失败: " + tError.getFirstError();
                FlagStr = "Fail";
            }
        } catch (Exception ex) {
            Content = "保存失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }
    } else {
        Content = "上载文件失败!";
        FlagStr = "Fail";
    }
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

