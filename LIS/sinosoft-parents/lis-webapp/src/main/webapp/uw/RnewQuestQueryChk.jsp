<%@page contentType="text/html;charset=GBK"%> 
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestQueryChk.jsp
//�����ܣ��������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
<%
//1-�õ����м�¼����ʾ��¼ֵ
  int index=0;
  int TempCount=0;
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "��ѡ�񱣵�";
  
  //�õ�radio�е�����
  loggerDebug("RnewQuestQueryChk","---4----");  
  
  String tRadio[] = request.getParameterValues("InpQuestGridSel");  
  String tTempClassNum[] = request.getParameterValues("QuestGridNo");
  String tPolCode[] = request.getParameterValues("QuestGrid1");
  String tQuest[] = request.getParameterValues("QuestGrid2");
  String tReply[] = request.getParameterValues("QuestGrid3");
  
  int temp = tRadio.length;
  loggerDebug("RnewQuestQueryChk","radiolength:"+temp);
  
  //������ 
  
  if(tTempClassNum!=null) //������ǿռ�¼	
  {
  	TempCount = tTempClassNum.length; //��¼��      
   	loggerDebug("RnewQuestQueryChk","Start query Count="+TempCount);   
  	while(index<TempCount)
  	{
  		loggerDebug("RnewQuestQueryChk","----3-----");
  		loggerDebug("RnewQuestQueryChk","polcode:"+tPolCode[index]);
  		loggerDebug("RnewQuestQueryChk","radio:"+tRadio[index]);
  		if (!tPolCode[index].equals("")&&tRadio[index].equals("1"))
  		{
  			loggerDebug("RnewQuestQueryChk","GridNO="+tTempClassNum[index]);
  			loggerDebug("RnewQuestQueryChk","Grid 1="+tPolCode[index]);
  			loggerDebug("RnewQuestQueryChk","Radio:"+tRadio[index]);
    			loggerDebug("RnewQuestQueryChk","this radio is selected");
  			
  			FlagStr = "succ";
  			Content = "�ɹ�!";
    			
    			//��ѯ����ʾ������Ϣ
    					
%>
			<script language="javascript">
			try {
				parent.fraInterface.fm.ProposalNo.value="<%=tPolCode[index]%>";
				parent.fraInterface.fm.Quest.value="<%=tQuest[index]%>";
				parent.fraInterface.queryone();
		  } catch(e) {}
                    	</script>         
<%    			
      		}
    		
    		index=index+1;
    		loggerDebug("RnewQuestQueryChk","index:"+index);          
	}
}
   
%>  
<html>
<script language="javascript">
  try { parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>"); } catch(e) {}
</script>
</html>
