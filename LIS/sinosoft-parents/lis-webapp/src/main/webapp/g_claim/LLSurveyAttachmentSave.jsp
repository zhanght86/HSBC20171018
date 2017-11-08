<%
/***************************************************************
 * <p>ProName��LLClaimPermissionSave.jsp</p>
 * <p>Title���û�Ȩ�޹���</p>
 * <p>Description���û�Ȩ�޹���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>

<%
	//�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI");
	
	if(tGI == null){
		FlagStr = "Fail";
		Content = "ҳ��ʧЧ,�����µ�½";
		System.out.println("ҳ��ʧЧ,�����µ�½");    
	}
	String mtransact = request.getParameter("fmtransact");//����״̬
	String ImportPath = "";//������·��
	String SystemName ="";
	String FileName = "";
	String FileType = "";
	String mFilePath = "";
	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();
	currentDate = currentDate.replace("-","");
	currentTime = currentTime.replace(":","");
	String type = request.getParameter("AffixName");
	StrTool tStrTool = new StrTool();
	String result = tStrTool.unicodeToGBK(type);
		
	//��ȡ���淢������ֵ
	LDAttachmentSchema mLDAttachmentSchema = new LDAttachmentSchema();
	mLDAttachmentSchema.setOtherNo(request.getParameter("InqNo"));
	mLDAttachmentSchema.setAttachID(request.getParameter("AttachNo"));
	mLDAttachmentSchema.setAttachNum(request.getParameter("AffixNumber"));
	mLDAttachmentSchema.setAttachName((result==null)?null:result.trim());//��������
	mLDAttachmentSchema.setAttachFlag((request.getParameter("OriginalLogo")==null)?null:request.getParameter("OriginalLogo").trim());//ԭ����ʶ
	System.out.println("mtransact:"+mtransact);
	if(mtransact.equals("INSERT")||mtransact.equals("UPDATE")||mtransact.equals("UPLOADFILE"))
	{

		String path = request.getParameter("AffixPatch");
		
		System.out.println("path:"+path);
		if(!path.endsWith("/"))
		{
			path += "/";
		}
		
		DiskFileUpload fu = new DiskFileUpload();
		// ���������û��ϴ��ļ���С,��λ:�ֽ�
		fu.setSizeMax(100000000);
		// maximum size that will be stored in memory?
		// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
		fu.setSizeThreshold(4096);
		// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
		fu.setRepositoryPath(path+"/temp");
		File pathfile = new File(path+"/temp");
			if(!pathfile.exists()) {
				pathfile.mkdirs();
			}
		
		fu.setHeaderEncoding("GBK");
		//��ʼ��ȡ�ϴ���Ϣ
		List fileItems = fu.parseRequest(request);
		// ���δ���ÿ���ϴ����ļ�
		Iterator iter = fileItems.iterator();
		while (iter.hasNext())
		{
			FileItem item = (FileItem) iter.next();
			if (item.getFieldName().compareTo("ImportPath")==0)
			{
				ImportPath = item.getString()+"/"+currentDate.substring(0,4);
				System.out.println("===========ImportPath11111========="+ImportPath);
			}
			//�������������ļ�������б���Ϣ
			if (!item.isFormField())
			{
				String name = item.getName();
				long size = item.getSize();
				if((name==null||name.equals("")) && size==0)
					continue;
				FileName = name.replace('\\','/');
				FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
				FileType = FileName.substring(FileName.lastIndexOf(".")+1);
				System.out.println("===========FileName========="+FileName);
				System.out.println("===========ImportPath========="+ImportPath);
				
				try
				{
				File files = new File(ImportPath);
				if(!files.exists()) {
					files.mkdirs();
				}
				System.out.println("FileType:"+FileType);
				mFilePath = ImportPath+"/"+currentDate+currentTime+"."+FileType;
				SystemName=currentDate+currentTime;
				System.out.println(mFilePath+"path ...");
				item.write(new File(mFilePath));
				FlagStr = "Succ";
				Content = "�ϴ��ļ��ɹ�";
				}
				catch(Exception e)
				{
				  FlagStr = "Fail";
					Content = "�ϴ��ļ�ʧ��";
					System.out.println("upload file error ...");
				}
			}	
		}
}


	//���浽����
	if(FlagStr=="Succ"||mtransact.equals("DELETE")||mtransact.equals("ATTACHINSERT")||mtransact.equals("ATTACHUPDATE")||mtransact.equals("DELETEFILE"))
	{
		VData tVData = new VData();	  
		TransferData tTransferData = new TransferData(); 
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		mLDAttachmentSchema.setAttachPath(mFilePath);
		mLDAttachmentSchema.setAttachSysName(SystemName);
		mLDAttachmentSchema.setExtenName(FileType);
		mLDAttachmentSchema.setRemark(FileName);
		
		tVData.add(tGI);	
		tVData.add(mLDAttachmentSchema)	;
		tVData.add(tTransferData);
		if (!tBusinessDelegate.submitData(tVData, mtransact, "LLInqUploadUI")) {
			
			if(mtransact.equals("DELETE")) {
						Content = "ɾ��ʧ��";
				}else if(mtransact.equals("ATTACHINSERT")||mtransact.equals("INSERT")){
						Content = "����ʧ��";
				}else if(mtransact.equals("UPDATE")||mtransact.equals("ATTACHUPDATE")){
						Content = "�޸�ʧ��";
				}else if(mtransact.equals("DELETEFILE")){
						Content = "ɾ���ļ�ʧ��";
				}else {
					Content = "�ϴ��ļ�ʧ��";
				}
				FlagStr = "Fail";
		
		} else {
				if(mtransact.equals("DELETE")) {
						Content = "ɾ���ɹ�";
				}else if(mtransact.equals("ATTACHINSERT")||mtransact.equals("INSERT")){
						Content = "����ɹ�";
				}else if(mtransact.equals("UPDATE")||mtransact.equals("ATTACHUPDATE")){
						Content = "�޸ĳɹ�";
				}else if(mtransact.equals("DELETEFILE")){
						Content = "ɾ���ļ��ɹ�";
				}else {
						Content = "�ϴ��ļ��ɹ�";
				}
				FlagStr = "Succ";			
		}			   
}

%>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>