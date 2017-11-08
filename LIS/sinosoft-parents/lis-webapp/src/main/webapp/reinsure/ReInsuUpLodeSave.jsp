<%@include file="/i18n/language.jsp"%>
<%
//程序名称：ReInsuUpLodeSave.jsp
//程序功能：
//创建日期：2006-08-21 17:25:18
//创建人  ：zhangbin程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>

<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.reinsure.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="java.util.*"%>
	<%@page import="java.io.*"%>
	<%@page import="org.apache.commons.fileupload.*"%>
<%
	String FileName = "";
	String filePath = "";
  File mFile = null;
	int count = 0;
	
	//得到excel文件的保存路径
	GlobalInput tG = new GlobalInput(); 	 	 
	tG=(GlobalInput)session.getAttribute("GI");
	String ImportPath = "temp/reinsure";
	String path = application.getRealPath("").replace('\\','/')+'/';
	DiskFileUpload fu = new DiskFileUpload();
	// 设置允许用户上传文件大小,单位:字节
	fu.setSizeMax(10000000);
	// maximum size that will be stored in memory?
	// 设置最多只允许在内存中存储的数据,单位:字节
	fu.setSizeThreshold(4096);
	// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
	fu.setRepositoryPath(path+"temp");
	//开始读取上传信息
	System.out.println(path);
	System.out.println(ImportPath);
	System.out.println(PubFun.getCurrentDate());
	List fileItems = null;
	try{
	 fileItems = fu.parseRequest(request);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	String FilePath="";
	
	// 依次处理每个上传的文件
	Iterator iter = fileItems.iterator();
	System.out.println(tG.ManageCom);
	while (iter.hasNext()) {
	  FileItem item = (FileItem) iter.next();
	  //忽略其他不是文件域的所有表单信息
	  if (!item.isFormField()) {
	    String name = item.getName();
			System.out.println("要上传的FileName: "+name);	    
	    long size = item.getSize();
	    if((name==null||name.equals("")) && size==0)
	      continue;
	    ImportPath= path + ImportPath ;
	    FileName = name.substring(name.lastIndexOf("\\") + 1);
	    Random rand = new Random();
			int tSelect = rand.nextInt(9999);
	    FilePath = ImportPath + "/"+ PubFun.getCurrentDate()+"-"+tSelect+"/";
	    System.out.println("上传路径FilePath:  "+FilePath);
	    System.out.println("上传文件名FileName:  "+FileName);    
	    mFile = new File(FilePath);  
	     
	    if (!mFile.exists()) { 
	    	System.out.println("需要创建路径!");
	      if(mFile.mkdir()){
	      	System.out.println("创建路径成功!");
	      }else{
	      	System.out.println("路径创建失败!");
	      }
	    }
	    //保存上传的文件到指定的目录
	    try {
	    	System.out.println("准备上传文件!");
	      item.write(new File(FilePath +"//"+  FileName));
	      System.out.println("已经上传文件!");
	      count = 1; 
	    } catch(Exception e) {
	    	e.printStackTrace();
	      System.out.println("upload file error ...");
	    }
	  }
	}
%>                      
<html>
<script type="text/javascript">
	parent.fraInterface.UpLodeReInsure("<%=FilePath%>","<%=FileName%>");
</script>
</html>


