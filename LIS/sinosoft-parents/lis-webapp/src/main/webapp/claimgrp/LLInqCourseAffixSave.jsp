<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//参照程序名称：LAFRAgentAffixSave.jsp
//程序功能：
//创建日期：2006-07-06
//创建人  ：liuyu
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
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
  // 输出参数
  CErrors tError = null;
  String FlagStr = "";
  String Content = "";
  String operate = "UPDATE";
  String strRealPath = application.getRealPath("/").replace('\\','/');
  loggerDebug("LLInqCourseAffixSave","------------------------------------------------");
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  String currentDate = PubFun.getCurrentDate();
  String newPath=null;
 
  if(tGI==null)
  {
    loggerDebug("LLInqCourseAffixSave","页面失效,请重新登陆");
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
  }
  else //页面有效
  {
  
    loggerDebug("LLInqCourseAffixSave","-----------------11111111-------------------------------");
    
    String ClmNo= StrTool.GBKToUnicode(request.getParameter("ClmNo"));
        loggerDebug("LLInqCourseAffixSave","********"+ClmNo+"********");
        loggerDebug("LLInqCourseAffixSave","------------3222222------------------------------------");
         com.jspsmart.upload.SmartUpload smartu=new com.jspsmart.upload.SmartUpload();
	       smartu.initialize(pageContext);
	       //smartu.setTotalMaxFileSize(10000000);
	       smartu.setTotalMaxFileSize(5242880);/*文件大小最大为5M--按字节算*/
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
                 Content="文件上传失败！您所上载的文件可能不为Word文件(*.doc)或者上载的文件大小超过最大上限5M，请调整后重新上载！";         
         }
	       // 将上传文件全部保存到指定目录
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
                  // 另存到以WEB应用程序的根目录为文件根目录的目录下
                  //String serial=PubFun.CreateSeq("seqHRAffix");                  
                  String serial=PubFun1.CreateMaxNo("SERIALNO",ClmNo.substring(2,6)); //用序列号做文件名避免了文件重名的问题
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
            Content="保存上传文件时发生错误！";
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
//                    Content = " 保存失败，原因是: " + tLLInqCourseAffixDoBL.mErrors.getError(0).errorMessage;
//                    FlagStr = "Fail";
//                 }
				String busiName="grpLLInqCourseAffixDoBL";
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if(!tBusinessDelegate.submitData(tVData,operate,busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "保存失败";
						FlagStr = "Fail";				
					}
				}

                 else 
                 {   
                    Content = " 保存成功" ;
                    FlagStr = "True";
                  
                 }
           }
           catch(Exception ex)
           {
                Content = "保存失败，原因是:" + ex.toString();
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
