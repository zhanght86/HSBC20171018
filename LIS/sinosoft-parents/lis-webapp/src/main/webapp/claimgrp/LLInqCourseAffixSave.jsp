<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//���ճ������ƣ�LAFRAgentAffixSave.jsp
//�����ܣ�
//�������ڣ�2006-07-06
//������  ��liuyu
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="java.lang.*"%>
  <%@page import="java.util.*"%>
  <%@page import="java.text.SimpleDateFormat"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.claimgrp.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
  <%@ page language="java" import="com.jspsmart.upload.*"%>
  
<%
  // �������
  CErrors tError = null;
  String FlagStr = "";
  String Content = "";
  String operate = "UPDATE";
  String strRealPath = application.getRealPath("/").replace('\\','/');
  loggerDebug("LLInqCourseAffixSave","------------------------------------------------");
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  String currentDate = PubFun.getCurrentDate();
  String newPath=null;
 
  if(tGI==null)
  {
    loggerDebug("LLInqCourseAffixSave","ҳ��ʧЧ,�����µ�½");
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
  }
  else //ҳ����Ч
  {
  
    loggerDebug("LLInqCourseAffixSave","-----------------11111111-------------------------------");
    
    String ClmNo= StrTool.GBKToUnicode(request.getParameter("ClmNo"));
        loggerDebug("LLInqCourseAffixSave","********"+ClmNo+"********");
        loggerDebug("LLInqCourseAffixSave","------------3222222------------------------------------");
         com.jspsmart.upload.SmartUpload smartu=new com.jspsmart.upload.SmartUpload();
	       smartu.initialize(pageContext);
	       //smartu.setTotalMaxFileSize(10000000);
	       smartu.setTotalMaxFileSize(5242880);/*�ļ���С���Ϊ5M--���ֽ���*/
	        loggerDebug("LLInqCourseAffixSave","begin upload !");	
	       //smartu.setAllowedFilesList("jpg,JPG,GIF,gif,doc,txt");
	       smartu.setAllowedFilesList("doc");
         try
         {
              smartu.upload();
              loggerDebug("LLInqCourseAffixSave","begin upload 2 !");
         }
         catch(Exception ex)
         {
                 loggerDebug("LLInqCourseAffixSave",ex);
                 FlagStr="Fail"; 
                 Content="�ļ��ϴ�ʧ�ܣ��������ص��ļ����ܲ�ΪWord�ļ�(*.doc)�������ص��ļ���С�����������5M����������������أ�";         
         }
	       // ���ϴ��ļ�ȫ�����浽ָ��Ŀ¼
	       String app1="";
	       String app2="";
	       int count=0;

	       ExeSQL tExeSQL = new ExeSQL();
	       String sql="select to_char(to_date('"+currentDate+"'),'YYYYMMDD') from dual ";
	       currentDate=tExeSQL.getOneValue(sql);
	       loggerDebug("LLInqCourseAffixSave","currentDate:"+currentDate);
	       
	      if (Content=="")
        {    
         try
         {
              String lsql="select code from ldcode where codetype='upload' "; 
              String UpLoad=tExeSQL.getOneValue(lsql);
              loggerDebug("LLInqCourseAffixSave","UpLoad:"+UpLoad);
              //newPath=strRealPath+"HRAffix/"+currentDate.substring(0,4)+"/"+currentDate.substring(4,6)+"/"+currentDate.substring(6,8);
              newPath=strRealPath+UpLoad+"/"+currentDate.substring(0,4)+"/"+currentDate.substring(4,6)+"/"+currentDate.substring(6,8);
              
              loggerDebug("LLInqCourseAffixSave","newPath:"+newPath);
         
              java.io.File newFile=new java.io.File(newPath);
              if(!newFile.exists())
              {
                 //newFile.mkdir();
                 newFile.mkdirs();
              }        

              //count= smartu.save("./HRAffix/"+currentDate.substring(0,4)+"/"+currentDate.substring(4,6)+"/"+currentDate.substring(6,8),smartu.SAVE_VIRTUAL);    
              count= smartu.save("./"+UpLoad+"/"+currentDate.substring(0,4)+"/"+currentDate.substring(4,6)+"/"+currentDate.substring(6,8),smartu.SAVE_VIRTUAL);    
             
              loggerDebug("LLInqCourseAffixSave","count:"+count);
             for (int i=0;i<count;i++)
             {           
                  com.jspsmart.upload.File file = smartu.getFiles().getFile(i);        
                  if (file.isMissing()) 
                  {
                       loggerDebug("LLInqCourseAffixSave","file missing!");
                       continue;
                  }
                  // ��浽��WEBӦ�ó���ĸ�Ŀ¼Ϊ�ļ���Ŀ¼��Ŀ¼��
                  //String serial=PubFun.CreateSeq("seqHRAffix");                  
                  String serial=PubFun1.CreateMaxNo("SERIALNO",ClmNo.substring(2,6)); //�����к����ļ����������ļ�����������
                  //file.saveAs("./HRAffix/"+currentDate.substring(0,4)+"/"+currentDate.substring(4,6)+"/"+currentDate.substring(6,8)+"/"+serial+"."+file.getFileExt() , smartu.SAVE_VIRTUAL);         
                  file.saveAs("./"+UpLoad+"/"+currentDate.substring(0,4)+"/"+currentDate.substring(4,6)+"/"+currentDate.substring(6,8)+"/"+serial+"."+file.getFileExt() , smartu.SAVE_VIRTUAL);         
                  //java.io.File old=new java.io.File(strRealPath+"HRAffix/"+currentDate.substring(0,4)+"/"+currentDate.substring(4,6)+"/"+currentDate.substring(6,8)+"/"+file.getFileName());
                  java.io.File old=new java.io.File(strRealPath+UpLoad+"/"+currentDate.substring(0,4)+"/"+currentDate.substring(4,6)+"/"+currentDate.substring(6,8)+"/"+file.getFileName());
                  if(i==0)
                  {
                      app2=file.getFileName();
                  }
                  old.delete(); 
                  if(i==0)
                  {
                      app1=currentDate.substring(0,4)+"/"+currentDate.substring(4,6)+"/"+currentDate.substring(6,8)+"/"+serial+"."+file.getFileExt();
                  }
              }      
         }
         catch (Exception ex)
         {
            loggerDebug("LLInqCourseAffixSave","upload failed! ");
            FlagStr="Fail";
            Content="�����ϴ��ļ�ʱ��������";
         }
       }      
             
        if (Content=="")
        {     
           try
           { 
    
           LLInqApplySchema tLLInqApplySchema = new LLInqApplySchema();
           tLLInqApplySchema.setClmNo(ClmNo);
           if(count>0)
           {
              tLLInqApplySchema.setAffix(app1);
              tLLInqApplySchema.setAffixName(app2);
           }
           
           VData tVData = new VData();
           tVData.addElement(tGI);
           tVData.addElement(tLLInqApplySchema);
//           LLInqCourseAffixDoBL tLLInqCourseAffixDoBL = new LLInqCourseAffixDoBL();
//           
//
//                 if(!tLLInqCourseAffixDoBL.submitData(tVData,operate))
//                 {
//                    Content = " ����ʧ�ܣ�ԭ����: " + tLLInqCourseAffixDoBL.mErrors.getError(0).errorMessage;
//                    FlagStr = "Fail";
//                 }
				String busiName="grpLLInqCourseAffixDoBL";
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if(!tBusinessDelegate.submitData(tVData,operate,busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "����ʧ��";
						FlagStr = "Fail";				
					}
				}

                 else 
                 {   
                    Content = " ����ɹ�" ;
                    FlagStr = "True";
                  
                 }
           }
           catch(Exception ex)
           {
                Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
                FlagStr = "Fail";
           }
       }    
         loggerDebug("LLInqCourseAffixSave","------------------------------------"+FlagStr);
         loggerDebug("LLInqCourseAffixSave","------------------------------------"+Content);
         loggerDebug("LLInqCourseAffixSave","------------555555555555------------------------------------");
   }
%>
<html>
<script language="javascript">
       parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>
