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
  <%@page import="com.sinosoft.service.*" %>
<%
//1-�õ����м�¼����ʾ��¼ֵ
  int index=0;
  int TempCount=0;
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "��ѡ�񱣵�";
  
  //�õ�radio�е�����
  loggerDebug("QuestQueryChk","---4----");  
  
  String tRadio[] = request.getParameterValues("InpQuestGridSel");  
  String tTempClassNum[] = request.getParameterValues("QuestGridNo");
  String tPolCode[] = request.getParameterValues("QuestGrid1");
  String tQuest[] = request.getParameterValues("QuestGrid2");
  String tReply[] = request.getParameterValues("QuestGrid3");
  
  int temp = tRadio.length;
  loggerDebug("QuestQueryChk","radiolength:"+temp);
  
  //������ 
  
  if(tTempClassNum!=null) //������ǿռ�¼	
  {
  	TempCount = tTempClassNum.length; //��¼��      
   	loggerDebug("QuestQueryChk","Start query Count="+TempCount);   
  	while(index<TempCount)
  	{
  		loggerDebug("QuestQueryChk","----3-----");
  		loggerDebug("QuestQueryChk","polcode:"+tPolCode[index]);
  		loggerDebug("QuestQueryChk","radio:"+tRadio[index]);
  		if (!tPolCode[index].equals("")&&tRadio[index].equals("1"))
  		{
  			loggerDebug("QuestQueryChk","GridNO="+tTempClassNum[index]);
  			loggerDebug("QuestQueryChk","Grid 1="+tPolCode[index]);
  			loggerDebug("QuestQueryChk","Radio:"+tRadio[index]);
    			loggerDebug("QuestQueryChk","this radio is selected");
  			
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
    		loggerDebug("QuestQueryChk","index:"+index);          
	}
}
   
%>  
<html>
<script language="javascript">
  try { parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>"); } catch(e) {}
</script>
</html>