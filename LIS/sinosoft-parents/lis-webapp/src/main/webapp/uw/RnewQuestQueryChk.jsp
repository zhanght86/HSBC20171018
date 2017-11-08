<%@page contentType="text/html;charset=GBK"%> 
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestQueryChk.jsp
//程序功能：问题件查询
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
<%
//1-得到所有纪录，显示纪录值
  int index=0;
  int TempCount=0;
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "请选择保单";
  
  //得到radio列的数组
  loggerDebug("RnewQuestQueryChk","---4----");  
  
  String tRadio[] = request.getParameterValues("InpQuestGridSel");  
  String tTempClassNum[] = request.getParameterValues("QuestGridNo");
  String tPolCode[] = request.getParameterValues("QuestGrid1");
  String tQuest[] = request.getParameterValues("QuestGrid2");
  String tReply[] = request.getParameterValues("QuestGrid3");
  
  int temp = tRadio.length;
  loggerDebug("RnewQuestQueryChk","radiolength:"+temp);
  
  //保单表 
  
  if(tTempClassNum!=null) //如果不是空纪录	
  {
  	TempCount = tTempClassNum.length; //记录数      
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
  			Content = "成功!";
    			
    			//查询并显示单条信息
    					
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
