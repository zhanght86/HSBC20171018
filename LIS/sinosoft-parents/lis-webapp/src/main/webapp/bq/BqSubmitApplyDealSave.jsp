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
<%@page import="com.sinosoft.lis.bqgrp.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>



<%
 GlobalInput tG=(GlobalInput)session.getValue("GI");

%>
<%@page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@page import="com.sinosoft.lis.schema.LPSubmitApplySchema"%>


<%

response.setContentType("text/html; charset=GB2312");

CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
String errMess = "";
String Result = "";
    int count=0;


	String path = application.getRealPath("").replace('\\','/');
	if(!path.endsWith("/"))
	{
	    path += "/";
	}
	DiskFileUpload fu  = new DiskFileUpload();
	fu.setSizeMax(10000000);
	fu.setSizeThreshold(4096);
	fu.setRepositoryPath(path+"/bqconfig");
	List fileItems = fu.parseRequest(request);
	loggerDebug("BqSubmitApplyDealSave","Path:"+path);
	boolean isUpSucc = true;
	String ImportPath = "";
	String FileName = "";
	String tGrpContNo = ""; //���屣����
	String tEdorType = ""; //��ȫ����
	String tDispDept = ""; //�нӻ���
	String tDispPer = ""; //�н���
	String tSubPer = ""; //�б���
	String tAppText = ""; //�������ݼ���
	String tDateFile = "";
	String tSubDesc = "";
	String tSubNo = "";
	tSubNo = PubFun1.CreateMaxNo("SERIALNO",PubFun.getNoLimit(tG.ManageCom));
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
		  if (item.getFieldName().compareTo("DispDept")==0)
		  {
			  tDispDept = item.getString();
		  }
		  if (item.getFieldName().compareTo("DispPer")==0)
		  {
			  tDispPer = item.getString();
		  }
		  if (item.getFieldName().compareTo("SubPer")==0)
		  {
			  tSubPer = item.getString();
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
			    //ImportPath= path + "bqconfig/BqSub/";
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
		        	  loggerDebug("BqSubmitApplyDealSave","��ʼ�½��ļ���!");
		        	  file.mkdirs();
		          }else{
		        	  loggerDebug("BqSubmitApplyDealSave","Ŀ¼�Ѿ�����: ");
		          } //add end
			    FileName = name.replace('\\','/');
			    FileName = FileName.substring(FileName.lastIndexOf("."));
			    try
			    {
				      item.write(new File(ImportPath +tSubNo+ FileName));
				      count = 1;
			    }
			    catch(Exception e)
			    {
			    	out.print("<center>�ϴ��ļ�ʧ��!<Input  value=' �� �� ' type=button onclick='javascript:history.back();'></center>");
					return;
			    }
		  }
		  
	}
    if(isUpSucc){
       //******************�ύ����̨��������*****************//
       loggerDebug("BqSubmitApplyDealSave","begin prepore data !");
       
       LPSubmitApplySchema tLPSubmitApplySchema=new LPSubmitApplySchema();
       tLPSubmitApplySchema.setSubNo(tSubNo);
       tLPSubmitApplySchema.setGrpContNo(tGrpContNo);
       tLPSubmitApplySchema.setEdorType(tEdorType);
       tLPSubmitApplySchema.setDispDept(tDispDept);
       tLPSubmitApplySchema.setDispPer(tDispPer);
       tLPSubmitApplySchema.setSubPer(tSubPer);
       tLPSubmitApplySchema.setManageCom(tG.ManageCom);
       tLPSubmitApplySchema.setAppText1(tAppText);
       tLPSubmitApplySchema.setAppText2(FileName);
       tLPSubmitApplySchema.setAppText3(tDateFile);
       tLPSubmitApplySchema.setSubTimes(1);
       tLPSubmitApplySchema.setSubState("1");
       tLPSubmitApplySchema.setSubDesc(tSubDesc);
       tLPSubmitApplySchema.setHasDealed("0");
       tLPSubmitApplySchema.setOperator(tG.Operator);
       tLPSubmitApplySchema.setSubDate(PubFun.getCurrentDate());
       tLPSubmitApplySchema.setMakeDate(PubFun.getCurrentDate());
       tLPSubmitApplySchema.setMakeTime(PubFun.getCurrentTime());
       tLPSubmitApplySchema.setModifyDate(PubFun.getCurrentDate());
       tLPSubmitApplySchema.setModifyTime(PubFun.getCurrentTime());
       
       LPSubmitApplyTraceSchema tLPSubmitApplyTraceSchema = new LPSubmitApplyTraceSchema();
       tLPSubmitApplyTraceSchema.setSubNo(tSubNo);
       tLPSubmitApplyTraceSchema.setSerialNo("1");
       tLPSubmitApplyTraceSchema.setGrpContNo(tGrpContNo);
       tLPSubmitApplyTraceSchema.setEdorType(tEdorType);
       tLPSubmitApplyTraceSchema.setDispDept(tDispDept);
       tLPSubmitApplyTraceSchema.setDispPer(tDispPer);
       tLPSubmitApplyTraceSchema.setSubPer(tSubPer);
       tLPSubmitApplyTraceSchema.setManageCom(tG.ManageCom);
       tLPSubmitApplyTraceSchema.setAppText1(tAppText);
       tLPSubmitApplyTraceSchema.setAppText2(FileName);
       tLPSubmitApplyTraceSchema.setAppText3(tDateFile);
       tLPSubmitApplyTraceSchema.setSubTimes(1);
       tLPSubmitApplyTraceSchema.setSubState("1");
       tLPSubmitApplyTraceSchema.setSubDesc(tSubDesc);
       tLPSubmitApplyTraceSchema.setHasDealed("0");
       tLPSubmitApplyTraceSchema.setDealType("0");
       tLPSubmitApplyTraceSchema.setDealIdea(tSubDesc);
       tLPSubmitApplyTraceSchema.setDealDate(PubFun.getCurrentDate());
       tLPSubmitApplyTraceSchema.setOperator(tG.Operator);
       tLPSubmitApplyTraceSchema.setSubDate(PubFun.getCurrentDate());
       tLPSubmitApplyTraceSchema.setMakeDate(PubFun.getCurrentDate());
       tLPSubmitApplyTraceSchema.setMakeTime(PubFun.getCurrentTime());
       tLPSubmitApplyTraceSchema.setModifyDate(PubFun.getCurrentDate());
       tLPSubmitApplyTraceSchema.setModifyTime(PubFun.getCurrentTime());
       
       MMap tMap = new MMap();
       tMap.put(tLPSubmitApplySchema, "DELETE&INSERT");
       tMap.put(tLPSubmitApplyTraceSchema, "DELETE&INSERT");
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
<%@page import="com.sinosoft.lis.schema.LPSubmitApplyTraceSchema"%>
<%@page import="com.sinosoft.lis.db.LDSysVarDB"%>
<HTML>
  <script language="javascript">
  parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>','<%=tSubNo%>');
  </script>
</HTML>
