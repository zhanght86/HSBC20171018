<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�ReInsuUpLodeSave.jsp
//�����ܣ�
//�������ڣ�2006-08-21 17:25:18
//������  ��zhangbin���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
	
	//�õ�excel�ļ��ı���·��
	GlobalInput tG = new GlobalInput(); 	 	 
	tG=(GlobalInput)session.getAttribute("GI");
	String ImportPath = "temp/reinsure";
	String path = application.getRealPath("").replace('\\','/')+'/';
	DiskFileUpload fu = new DiskFileUpload();
	// ���������û��ϴ��ļ���С,��λ:�ֽ�
	fu.setSizeMax(10000000);
	// maximum size that will be stored in memory?
	// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
	fu.setSizeThreshold(4096);
	// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
	fu.setRepositoryPath(path+"temp");
	//��ʼ��ȡ�ϴ���Ϣ
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
	
	// ���δ���ÿ���ϴ����ļ�
	Iterator iter = fileItems.iterator();
	System.out.println(tG.ManageCom);
	while (iter.hasNext()) {
	  FileItem item = (FileItem) iter.next();
	  //�������������ļ�������б���Ϣ
	  if (!item.isFormField()) {
	    String name = item.getName();
			System.out.println("Ҫ�ϴ���FileName: "+name);	    
	    long size = item.getSize();
	    if((name==null||name.equals("")) && size==0)
	      continue;
	    ImportPath= path + ImportPath ;
	    FileName = name.substring(name.lastIndexOf("\\") + 1);
	    Random rand = new Random();
			int tSelect = rand.nextInt(9999);
	    FilePath = ImportPath + "/"+ PubFun.getCurrentDate()+"-"+tSelect+"/";
	    System.out.println("�ϴ�·��FilePath:  "+FilePath);
	    System.out.println("�ϴ��ļ���FileName:  "+FileName);    
	    mFile = new File(FilePath);  
	     
	    if (!mFile.exists()) { 
	    	System.out.println("��Ҫ����·��!");
	      if(mFile.mkdir()){
	      	System.out.println("����·���ɹ�!");
	      }else{
	      	System.out.println("·������ʧ��!");
	      }
	    }
	    //�����ϴ����ļ���ָ����Ŀ¼
	    try {
	    	System.out.println("׼���ϴ��ļ�!");
	      item.write(new File(FilePath +"//"+  FileName));
	      System.out.println("�Ѿ��ϴ��ļ�!");
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


