<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�LRFeeRateImportSave.jsp
	//�����ܣ�
	//�������ڣ�2008-1-8
	//������  ��JavaBean���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%
	String FileName = "";
	String filePath = "";
	int count = 0;

	//�õ�excel�ļ��ı���·��
	String ImportPath = request.getParameter("ImportPath");
	String path = application.getRealPath("").replace('\\', '/') + '/';
	String tBatchNo = request.getParameter("BatchNo");
	String tFeeTableNo = request.getParameter("FeeTableNo");


	DiskFileUpload fu = new DiskFileUpload();
	// ���������û��ϴ��ļ���С,��λ:�ֽ�
	fu.setSizeMax(10000000);
	// maximum size that will be stored in memory?
	// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
	fu.setSizeThreshold(4096);
	// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
	fu.setRepositoryPath(path + "temp");
	//��ʼ��ȡ�ϴ���Ϣ

	List fileItems = null;
	try {

		fileItems = fu.parseRequest(request);

	} catch (Exception ex) {
		ex.printStackTrace();
	}

	// ���δ���ÿ���ϴ����ļ�
	Iterator iter = fileItems.iterator();
	while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		//�������������ļ�������б���Ϣ
		if (!item.isFormField()) {
			String name = item.getName();
			long size = item.getSize();
			if ((name == null || name.equals("")) && size == 0)
				continue;
			ImportPath = path + ImportPath;
			FileName = name.substring(name.lastIndexOf("\\") + 1);
			//�����ϴ����ļ���ָ����Ŀ¼

			try {
				item.write(new File(ImportPath + FileName));
				count = 1;
			} catch (Exception e) {
			}
		}
	}
	//�������
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String Result = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	VData tVData = new VData();
	tVData.add(tG);
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("BatchNo", tBatchNo);
	tTransferData.setNameAndValue("FeeTableNo", tFeeTableNo);
	tTransferData.setNameAndValue("Path", ImportPath);
	tTransferData.setNameAndValue("FileName", FileName);
	tVData.add(tTransferData);
	//�õ����ݵ����ļ�
	if (count > 0) {
		BusinessDelegate uiBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (!uiBusinessDelegate.submitData(tVData, "",
				"LRFeeRateImport")) {
			if (uiBusinessDelegate.getCErrors() != null
					&& uiBusinessDelegate.getCErrors().getErrorCount() > 0) {
				Content = ""+"����ʧ�ܣ�ԭ���ǣ�"+""
						+ uiBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
			} else {
				Content = ""+"����ʧ��"+"";
				FlagStr = "Fail";
			}
		}
		if ("".equals(FlagStr)) {
			tError = uiBusinessDelegate.getCErrors();
			if (!tError.needDealError()) {
				Content = ""+"����ɹ�"+"";
				FlagStr = "Succ";
			} else {
				Content = ""+"����ʧ�ܣ�ԭ���ǣ�"+"" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
	} else {
		Content = ""+"�����ļ�ʧ��!"+" ";
		FlagStr = "Fail";
	}
	//��Ӹ���Ԥ����
%>
<html>
<script type="text/javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


