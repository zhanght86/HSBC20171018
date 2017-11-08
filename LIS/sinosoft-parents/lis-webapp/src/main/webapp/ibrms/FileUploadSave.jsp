<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	//�������ƣ�FileUploadSave.jsp
	//�����ܣ��ļ�����ҳ��
	//�������ڣ�2011-12-12
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.ibrms.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="jxl.*"%>
<%@page import="org.json.*"%>
<%
	//������Ϣ������У�鴦��
	//�������

	String FlagStr = "";
	String Content = "";

	String targetPath = "";
	String excelSavePath = "select sysvarvalue from LDSYSVAR where sysvar = 'ImportExcelSavePath'";
	ExeSQL exec = new ExeSQL();

	String relativePath = exec.getOneValue(excelSavePath);
	String appPath = application.getRealPath("/").replace('\\', '/');

	targetPath = appPath + relativePath;

	String FileName = "";
	String dealType = "";
	String isUpload = request.getParameter("IsUpload");
	// �ϴ�
	//if((isUpload == null) || !(isUpload.equals("1")))

	try {
		if (!targetPath.endsWith("/")) {
			targetPath += "/";
		}

		DiskFileUpload fu = new DiskFileUpload();// ���������û��ϴ��ļ���С,��λ:�ֽ�
		fu.setSizeMax(50000000);
		// maximum size that will be stored in memory?
		// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
		fu.setSizeThreshold(409600);
		// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
		fu.setRepositoryPath(targetPath);

		// ��ʼ��ȡ�ϴ���Ϣ
		List fileItems = fu.parseRequest(request);

		//  ���δ���ÿ���ϴ����ļ�
		Iterator iter = fileItems.iterator();

		if (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();

			if (!item.isFormField()) {
				String name = item.getName();
				loggerDebug("FileUploadSave","����������"+name);
				if (name != null && !name.endsWith(".xls")) {
					out.println("{error:'��ʹ��Excel��Ϊģ�壬������Ҫ����߱��������ͬ'}");
					return;
				}

				FileName = System.currentTimeMillis()
						+ name.replace('\\', '/');
				FileName = FileName
						.substring(FileName.lastIndexOf("/") + 1);

				//�����ϴ����ļ���ָ����Ŀ¼
				try {
					File tFile = new File(targetPath);
					if (!tFile.exists()) {
						tFile = null;
						tFile = new File(targetPath.replace('/', '\\'));
						tFile.mkdirs();
					}
					loggerDebug("FileUploadSave","FilePath=============="
							+ targetPath + "/" + FileName);

					item.write(new File(targetPath + FileName));

					FileName = targetPath + FileName;
				} catch (Exception e) {
					FlagStr = "Fail";
					Content = "�ϴ��ļ�ʧ�ܣ�";
					e.printStackTrace();
				}
			}
		}

	} catch (Exception ex) {
		FlagStr = "Fail";
		Content = "����ʧ�ܣ�";
		ex.printStackTrace();
		out.println("{error:'����ʧ��'}");
		return;
	}

	if (FlagStr != null && !FlagStr.equals("Fail")) {
		Workbook wb = Workbook.getWorkbook(new File(FileName));
		Sheet st = wb.getSheet(0);

		JSONArray array = new JSONArray();

		int RowNum = st.getRows();
		for (int i = 1; i < RowNum; i++) {// �ӵڶ��п�ʼ��ȡ���ݡ�
			Cell[] arrCe = st.getRow(i);
			HashMap map = new HashMap();
			for (int j = 0; j < arrCe.length; j++) {

				if (arrCe[j] != null && !arrCe[j].equals("")) {

					if (j == arrCe.length - 1) {
						map.put("DTRate", arrCe[j].getContents());
						//map.put("RuleId", "RuleId");
					} else {
						map.put("COLUMN" + j, arrCe[j].getContents());
					}
				}

			}
			array.put(map);
		}

		JSONObject obj = new JSONObject();
		//obj.put("success", "Success");
		//obj.put("msg", FileName);
		obj.put("rows", array);
		loggerDebug("FileUploadSave",obj.toString());
		out.println(obj.toString());

	}
%>
