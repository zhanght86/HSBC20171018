<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PolFeeStatusChk.jsp
//�����ܣ�Ͷ��������״̬��ѯ
//�������ڣ�2003-07-07 11:10:36
//������  ��SXY
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  loggerDebug("PolFeeStatusChk","PolFeeStatusChk-----Begin!");
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //У�鴦��
  //���ݴ����
  
  	//������Ϣ
  	// Ͷ�����б�
	LCPolSet tLCPolSet = new LCPolSet();

	String tProposalNo[] = request.getParameterValues("PolFeeGrid1");
	String tPrtNo[]=request.getParameterValues("PolFeeGrid3");
	String tSel[] = request.getParameterValues("InpPolFeeGridSel");
	boolean flag = false;
	int proposalCount = tProposalNo.length;	

	for (int i = 0; i < proposalCount; i++)
	{
		if (!tProposalNo[i].equals("")&&!tPrtNo[i].equals("") && tSel[i].equals("1"))
		{
			loggerDebug("PolFeeStatusChk","ProposalNo:"+i+":"+tProposalNo[i]);
	  		LCPolSchema tLCPolSchema = new LCPolSchema();
	
		    tLCPolSchema.setPolNo( tProposalNo[i] );
		    tLCPolSchema.setPrtNo( tPrtNo[i] );
	        tLCPolSet.add( tLCPolSchema );
		    flag = true;
		}
	}

try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCPolSet );
		tVData.add( tG );
		
		// ���ݴ���
//		PolFeeStatusChkUI tPolFeeStatusChkUI   = new PolFeeStatusChkUI();
//		if (tPolFeeStatusChkUI.submitData(tVData,"INSERT") == false)
//		{
//			int n = tPolFeeStatusChkUI.mErrors.getErrorCount();
//			for (int i = 0; i < n; i++)
//			loggerDebug("PolFeeStatusChk","Error: "+tPolFeeStatusChkUI.mErrors.getError(i).errorMessage);
//			Content = " ��ѯʧ�ܣ�ԭ����: " + tPolFeeStatusChkUI.mErrors.getError(0).errorMessage;
//			FlagStr = "Fail";
//		}
String busiName="PolFeeStatusChkUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,"INSERT",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            loggerDebug("PolFeeStatusChk","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = " ��ѯʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		   Content = "��ѯʧ��";
		   FlagStr = "Fail";				
		} 
}

		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    //tError = tPolFeeStatusChkUI.mErrors;
		    tError = tBusinessDelegate.getCErrors();
		    //tErrors = tPolFeeStatusChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	FlagStr = "Succ";
		    	
		    	LMCalModeSet tLMCalModeSet = new LMCalModeSet();
		    	//VData tResult = tPolFeeStatusChkUI.getResult();
		    	VData tResult = tBusinessDelegate.getResult();
		    	if(tResult != null)
		    	{
		    		tLMCalModeSet = (LMCalModeSet)tResult.getObjectByObjectName("LMCalModeSet",0);
		    	}
		    	
		    	if(tLMCalModeSet.size() > 0)
		    	{
%>
				<script language="javascript">					
					parent.fraInterface.PolFeeStatuGrid.clearData ();				
				</script>         				
<%
		    	
		    		for(int i = 1;i <= tLMCalModeSet.size();i++)
		    		{
		    			LMCalModeSchema tLMCalModeSchema = new LMCalModeSchema();
		    			tLMCalModeSchema = tLMCalModeSet.get(i);
%>
					<script language="javascript">					
						parent.fraInterface.PolFeeStatuGrid.addOne();
						parent.fraInterface.PolFeeStatuGrid.setRowColData(<%=i-1%>,1,"<%=tLMCalModeSchema.getRemark()%>");						
                    			</script>         
<%		    			
		    		}
		    	}
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}
	}
	else
	{
		Content = "��ѡ�񱣵���";
	}  
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim() +" ��ʾ:�쳣�˳�.";
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
