<%--
    ���漯�屣����Ϣ 2004-11-16 wzw
--%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.taskservice.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.net.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.taskservice.crontab.CrontabParser"%>
<%	         
	
	String FlagStr="";      //�������
	String Content = "";    //����̨��Ϣ
	String tAction = "";    //�������ͣ�delete update insert
	String tOperate = "";   //��������

	VData tData = new VData();

	GlobalInput tGI = new GlobalInput();
	tAction = request.getParameter("fmAction");
	
	LDTaskPlanSchema tLDTaskPlanSchema = new LDTaskPlanSchema();
	LDTaskPlanSet tLDTaskPlanSet = new LDTaskPlanSet();
	LDTaskParamSchema tLDTaskParamSchema = new LDTaskParamSchema();
	LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
	
	tLDTaskPlanSchema.setTaskPlanCode(request.getParameter("TaskPlanCode"));
	tLDTaskPlanSchema.setTaskCode(request.getParameter("TaskCode"));
	tLDTaskPlanSchema.setRunFlag(request.getParameter("RunFlag"));
	tLDTaskPlanSchema.setRecycleType(request.getParameter("RecycleType"));
	tLDTaskPlanSchema.setStartTime(request.getParameter("StartTime"));
	tLDTaskPlanSchema.setEndTime(request.getParameter("EndTime"));
	tLDTaskPlanSchema.setInterval(request.getParameter("Interval"));
	//tLDTaskPlanSchema.setTimes(request.getParameter("Times"));
	tLDTaskPlanSchema.setPlanModeFlag(request.getParameter("PlanModeFlag"));
	String tActionAfterFail = request.getParameter("RunAfterFlag");
	if(tActionAfterFail==null||tActionAfterFail.equals(""))
	{
		tActionAfterFail = "00";
	}
	tLDTaskPlanSchema.setActionAfterFail(tActionAfterFail);
	
 	String tCrontab1[] = request.getParameterValues("CrontabGrid1");
	String tCrontab2[] = request.getParameterValues("CrontabGrid2");
	String tCrontab3[] = request.getParameterValues("CrontabGrid3");
  	String tCrontab4[] = request.getParameterValues("CrontabGrid4");
 	String tCrontab5[] = request.getParameterValues("CrontabGrid5");
  	String tCrontab6[] = request.getParameterValues("CrontabGrid6");
  
  	String tCrontabString = "";
 	String sCrontab1 = tCrontab1[0]==null?"":tCrontab1[0].trim();
 	String sCrontab2 = tCrontab2[0]==null?"":tCrontab2[0].trim();
    String sCrontab3 = tCrontab3[0]==null?"":tCrontab3[0].trim();
  	String sCrontab4 = tCrontab4[0]==null?"":tCrontab4[0].trim();
  	String sCrontab5 = tCrontab5[0]==null?"":tCrontab5[0].trim();
  	String sCrontab6 = tCrontab6[0]==null?"":tCrontab6[0].trim();
  
  	tCrontabString = sCrontab1 + "#" + sCrontab2 + "#" + sCrontab3 + "#" + sCrontab4 + "#" + sCrontab5 + "#" + sCrontab6;
  	loggerDebug("TaskPlanDefChk","--"+"tCrontabString:"+tCrontabString+"--");
  	if(tCrontabString.trim().equals("#####"))
  	{
  		tCrontabString = "";
  	}
  	else 
  	{
		if(tAction.equals("INSERT"))
		{
	    	String tCurrentTime = PubFun.getCurrentDate() + " " + PubFun.getCurrentTime();
      		try
      		{
        		String[] tConfig = tCrontabString.split("#");
        		String tContabConfig = "";
            	for(int i=0;i<tConfig.length;i++)
            	{
                	  loggerDebug("TaskPlanDefChk","##tConfig[i]:"+tConfig[i]+"##");
                	  if(tConfig[i]==null||tConfig[i].equals(""))
                	  {
                	  		Content = " ����ʧ�ܣ�ԭ����: Crontab" +  "��"+String.valueOf(i+1)+"�в���Ϊ��!";
							FlagStr = "Fail";   
							break;
                	  }
                	  if(i==0)
                	  {
                			tContabConfig =  tConfig[i];
                	  }
                	  else
                	  {
                			tContabConfig = tContabConfig + " " +  tConfig[i];
                	  }
             	}
             	loggerDebug("TaskPlanDefChk","##tContabConfig:"+tContabConfig+"##");
            	 if(!FlagStr.equals("Fail"))
             	{	
            		CrontabParser.calNext(tContabConfig,(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tCurrentTime)));
            		loggerDebug("TaskPlanDefChk","--"+"tCrontabString:--");
             	}
     	    }
      		catch(Exception ex)
      		{
         		 Content = " ����ʧ�ܣ�ԭ����: " +  ex.toString();
				 FlagStr = "Fail";   
      		}
		}
	}
	if(!FlagStr.equals("Fail"))
	{
 		 tLDTaskPlanSchema.setContabConfig(tCrontabString.trim());
  		 tLDTaskPlanSet.add(tLDTaskPlanSchema);

 		 //
  		//��ȡ����¼��������ַ
		String tServerParamName[] = request.getParameterValues("ParamGrid1");
		String tServerParamValue[] = request.getParameterValues("ParamGrid2");
	    String ServerInfo="";
        String ServerIP="";
        String ServerPort="";
    	loggerDebug("TaskPlanDefChk","����try����.........");
    	if(tServerParamName!=null&&tServerParamName.length>=1)
    	{
    		//���Ĭ�ϵĲ������ƺͲ���ֵ����������
    		/*MultiTaskServer tMultiTaskServer=new MultiTaskServer(); //Server
        	try{
           	 ServerInfo=tMultiTaskServer.getServerIP()+":"+String.valueOf(tMultiTaskServer.getServerPort()); //Server
          	}catch(Exception ex)
         	{
           		ex.printStackTrace();
            	ServerInfo="";
          	}*/
          	//�����Ϣ�ں�̨׼��
		  	tLDTaskParamSchema.setParamName(tServerParamName[0]);
		 	tLDTaskParamSchema.setParamValue(tServerParamValue[0]);
    	}
	//��Ӷ���Ĳ������ƺͲ���ֵ
	String tParamName[] = request.getParameterValues("MoreParamGrid1");
	String tParamValue[] = request.getParameterValues("MoreParamGrid2");
	if(tParamName!=null)
	{
		int n = tParamName.length;
        tLDTaskParamSet.add(tLDTaskParamSchema);
		for (int i = 0; i < n; i++)
		{ 
			//�жϲ�����Ϣ�Ƿ�Ϊ�գ�ʡȥɾ�����п��еı�Ҫ
			if (tParamValue[i] != null && !tParamValue[i].equals(""))
			{   
				tLDTaskParamSchema = new LDTaskParamSchema();
				tLDTaskParamSchema.setParamName(tParamName[i]);
				tLDTaskParamSchema.setParamValue(tParamValue[i]);
				tLDTaskParamSet.add(tLDTaskParamSchema);
			}
		}
	}
	
	//����������Ϣ
	
	String[] Msg_Types = request.getParameterValues("Msg_Type");
	LDTaskMsgItemSet tLDTaskMsgItemSet = new LDTaskMsgItemSet();
	if(Msg_Types!=null)
	{
		for (int i=0; i<Msg_Types.length; i++){
			loggerDebug("TaskPlanDefChk",i+":"+Msg_Types[i]);
			if(Msg_Types[i].toUpperCase().equals("01"))
			{
				//������Ϣ����
				String tMsg_Type = "mailto";
				String tMailAddress = request.getParameter("MailAddress");
				tLDTaskParamSchema = new LDTaskParamSchema();
				tLDTaskParamSchema.setParamName(tMsg_Type);
				tLDTaskParamSchema.setParamValue(tMailAddress);
				tLDTaskParamSet.add(tLDTaskParamSchema);
			
			
			}
			//��¼��ѡ��ʾ����
			if(Msg_Types.length>0)
			{
				String tChk[] = request.getParameterValues("InpLogItemGridChk"); 
				String MsgItems[] = request.getParameterValues("LogItemGrid2"); 
				String SubjectID[] = request.getParameterValues("LogItemGrid1"); 
				for(int n=0;n<tChk.length;n++)
				{
					if(tChk[n].equals("1"))
					{
						LDTaskMsgItemSchema	tLDTaskMsgItemSchema = new LDTaskMsgItemSchema();
						tLDTaskMsgItemSchema.setMsgType(Msg_Types[i]);
						tLDTaskMsgItemSchema.setItemID(MsgItems[n]);
						tLDTaskMsgItemSchema.setSubjectID(SubjectID[n]);
						tLDTaskMsgItemSchema.setTaskCode(request.getParameter("TaskCode"));
						tLDTaskMsgItemSet.add(tLDTaskMsgItemSchema);
					}
				}
			}
		}
	}

	

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");



  // ׼���������� VData
	tData.add( tG );
	tData.add(tLDTaskPlanSet);
	tData.add(tLDTaskParamSet);
	tData.add(tLDTaskMsgItemSet);
	
	TaskService tTaskService = new TaskService();
	if( tTaskService.submitData( tData, tAction ) < 0 )
	{
		Content = " ����ʧ�ܣ�ԭ����: " + tTaskService.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = " �����ɹ�! ";
		FlagStr = "Succ";

		tData.clear();

%>
<%		
	}
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
