<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpEdorTypeZTLoadSubmit.jsp
//�����ܣ�BB����
//�������ڣ�
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>



<%
 GlobalInput tG=(GlobalInput)session.getValue("GI");

%>
<%@page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@page import="com.sinosoft.lis.schema.LPSubmitApplySchema"%>


<%

String FlagStr = "Fail";
String Content = "";

response.setContentType("text/html; charset=GB2312");


	String path = application.getRealPath("").replace('\\','/');
	if(!path.endsWith("/"))
	{
	    path += "/";
	}
	DiskFileUpload fu  = new DiskFileUpload();
	fu.setSizeMax(10000000);
	fu.setSizeThreshold(4096);
	fu.setRepositoryPath(path+"/bqconfig/BqSub/");
	List fileItems = fu.parseRequest(request);
	loggerDebug("BqSubmitApplyDealUp","Path:"+path);
	boolean isUpSucc = true;
	String ImportPath = "";
	String FileName = "";
	String tGrpContNo = ""; //���屣����
	String tEdorType = ""; //��ȫ����
	String tAppText = ""; //�������ݼ���
	String tSubDesc = "";
	String tDateFile = "";
	String tSubNo = "";
	tSubNo = request.getParameter("SubNo");
	// ���δ���ÿ���ϴ����ļ�
	Iterator iter = fileItems.iterator();
	while (iter.hasNext())
	{
		  FileItem item = (FileItem) iter.next();
		  if (item.getFieldName().compareTo("ImportPath")==0)
		  {
			    ImportPath = item.getString();
		  }

		  if (item.getFieldName().compareTo("GrpContNo")==0)
		  {
			  tGrpContNo = item.getString();
		  }
		  if (item.getFieldName().compareTo("EdorType")==0)
		  {
			  tEdorType = item.getString();
		  }
		  if (item.getFieldName().compareTo("SubDesc")==0)
		  {
			  tSubDesc = item.getString();
		  }
		  if (item.getFieldName().compareTo("AppText")==0)
		  {
			  tAppText = item.getString();
		  }
		  if (!item.isFormField())
		  {
			    String name = item.getName();
			    long size = item.getSize();
			    if((name==null||name.equals("")) && size==0)
			      continue;
			    LDSysVarDB tLDSysVarDB = new LDSysVarDB();
				tLDSysVarDB.setSysVar("BqSubPath");
				if (!tLDSysVarDB.getInfo()) {
					out.print("<center>�ϴ��ļ�·��������!<Input  value=' �� �� ' type=button onclick='javascript:history.back();'></center>");
					return;
				}
				ImportPath = tLDSysVarDB.getSysVarValue();
				tDateFile = PubFun.getCurrentDate().replace('-', '/')+"/";
				ImportPath += tDateFile;
			    File file = new File(ImportPath);
			    if(!file.exists()){
		        	  loggerDebug("BqSubmitApplyDealUp","��ʼ�½��ļ���!");
		        	  file.mkdirs();
		          }else{
		        	  loggerDebug("BqSubmitApplyDealUp","Ŀ¼�Ѿ�����: ");
		          } //add end
			    FileName = name.replace('\\','/');
			    FileName = FileName.substring(FileName.lastIndexOf("."));
			    try
			    {
				      item.write(new File(ImportPath +tSubNo+ FileName));

			    }
			    catch(Exception e)
			    {
			       loggerDebug("BqSubmitApplyDealUp","upload file error ...");
			       isUpSucc = false;
			       Content = "�����ϴ�ʧ��!";
			    }
		  }
		  
	}
    if(isUpSucc){
       //******************�ύ����̨��������*****************//
       loggerDebug("BqSubmitApplyDealUp","begin prepore data !");
       LPSubmitApplyDB tLPSubmitApplyDB = new LPSubmitApplyDB();
       tLPSubmitApplyDB.setSubNo(tSubNo);
       LPSubmitApplySchema tLPSubmitApplySchema=tLPSubmitApplyDB.query().get(1);
       tLPSubmitApplySchema.setGrpContNo(tGrpContNo);
       tLPSubmitApplySchema.setEdorType(tEdorType);
       if(tAppText != null && !tAppText.equals("") ){
      	 	tLPSubmitApplySchema.setAppText1(tAppText);
       }
       if(FileName != null && !FileName.equals("") ){
    	   
       		tLPSubmitApplySchema.setAppText2(FileName);
       		if(tDateFile != null && !tDateFile.equals("")){
       			tLPSubmitApplySchema.setAppText3(tDateFile);
       		}
       }
       if(tSubDesc != null && !tSubDesc.equals("") ){
    	   
       		tLPSubmitApplySchema.setSubDesc(tSubDesc);
       }
       tLPSubmitApplySchema.setModifyDate(PubFun.getCurrentDate());
       tLPSubmitApplySchema.setModifyTime(PubFun.getCurrentTime());
       
       
       MMap tMap = new MMap();
       tMap.put(tLPSubmitApplySchema, "DELETE&INSERT");
       VData tVData = new VData();
       tVData.add(tMap);
       //�����ύ
       PubSubmit tSubmit = new PubSubmit();
       if (!tSubmit.submitData(tVData, "")) {
       	Content = " ����ʧ�ܣ�ԭ����:" + tSubmit.mErrors.getFirstError();
       	FlagStr = "Fail";  
       	isUpSucc = false;
       }
       
    }

	if (isUpSucc)
	{

		  Content = " �ύ�ɹ�! ";
		  FlagStr = "Succ";
	}
    
%>

<%@page import="com.sinosoft.lis.db.LPSubmitApplyDB"%>
<%@page import="com.sinosoft.lis.db.LDSysVarDB"%>
<HTML>
  <script language="javascript">
  parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>','<%=tSubNo%>');
  </script>
</HTML>
