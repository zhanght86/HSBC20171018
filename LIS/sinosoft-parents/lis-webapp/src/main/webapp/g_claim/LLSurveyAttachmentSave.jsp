<%
/***************************************************************
 * <p>ProName：LLClaimPermissionSave.jsp</p>
 * <p>Title：用户权限管理</p>
 * <p>Description：用户权限管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
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
	//输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI");
	
	if(tGI == null){
		FlagStr = "Fail";
		Content = "页面失效,请重新登陆";
		System.out.println("页面失效,请重新登陆");    
	}
	String mtransact = request.getParameter("fmtransact");//操作状态
	String ImportPath = "";//服务器路径
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
		
	//获取界面发过来的值
	LDAttachmentSchema mLDAttachmentSchema = new LDAttachmentSchema();
	mLDAttachmentSchema.setOtherNo(request.getParameter("InqNo"));
	mLDAttachmentSchema.setAttachID(request.getParameter("AttachNo"));
	mLDAttachmentSchema.setAttachNum(request.getParameter("AffixNumber"));
	mLDAttachmentSchema.setAttachName((result==null)?null:result.trim());//附件名称
	mLDAttachmentSchema.setAttachFlag((request.getParameter("OriginalLogo")==null)?null:request.getParameter("OriginalLogo").trim());//原件标识
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
		// 设置允许用户上传文件大小,单位:字节
		fu.setSizeMax(100000000);
		// maximum size that will be stored in memory?
		// 设置最多只允许在内存中存储的数据,单位:字节
		fu.setSizeThreshold(4096);
		// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
		fu.setRepositoryPath(path+"/temp");
		File pathfile = new File(path+"/temp");
			if(!pathfile.exists()) {
				pathfile.mkdirs();
			}
		
		fu.setHeaderEncoding("GBK");
		//开始读取上传信息
		List fileItems = fu.parseRequest(request);
		// 依次处理每个上传的文件
		Iterator iter = fileItems.iterator();
		while (iter.hasNext())
		{
			FileItem item = (FileItem) iter.next();
			if (item.getFieldName().compareTo("ImportPath")==0)
			{
				ImportPath = item.getString()+"/"+currentDate.substring(0,4);
				System.out.println("===========ImportPath11111========="+ImportPath);
			}
			//忽略其他不是文件域的所有表单信息
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
				Content = "上传文件成功";
				}
				catch(Exception e)
				{
				  FlagStr = "Fail";
					Content = "上传文件失败";
					System.out.println("upload file error ...");
				}
			}	
		}
}


	//保存到表中
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
						Content = "删除失败";
				}else if(mtransact.equals("ATTACHINSERT")||mtransact.equals("INSERT")){
						Content = "保存失败";
				}else if(mtransact.equals("UPDATE")||mtransact.equals("ATTACHUPDATE")){
						Content = "修改失败";
				}else if(mtransact.equals("DELETEFILE")){
						Content = "删除文件失败";
				}else {
					Content = "上传文件失败";
				}
				FlagStr = "Fail";
		
		} else {
				if(mtransact.equals("DELETE")) {
						Content = "删除成功";
				}else if(mtransact.equals("ATTACHINSERT")||mtransact.equals("INSERT")){
						Content = "保存成功";
				}else if(mtransact.equals("UPDATE")||mtransact.equals("ATTACHUPDATE")){
						Content = "修改成功";
				}else if(mtransact.equals("DELETEFILE")){
						Content = "删除文件成功";
				}else {
						Content = "上传文件成功";
				}
				FlagStr = "Succ";			
		}			   
}

%>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>