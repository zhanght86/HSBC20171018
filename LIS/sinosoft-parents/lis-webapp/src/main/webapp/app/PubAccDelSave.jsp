<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//�������
LCContSchema tLCContSchema = new LCContSchema();
TransferData tTransferData = new TransferData(); 
//ContDeleteUI tContDeleteUI = new ContDeleteUI();
String busiName="tbContDeleteUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//�������
String FlagStr = "";
String Content = "";

//���session�е���Ա
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");
if(tG==null)
{
    loggerDebug("PubAccDelSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
}
else //ҳ����Ч
{
    CErrors tError = null;
    loggerDebug("PubAccDelSave","start to delete PubAcc ......");

    int lineCount = 0;
    //���mutline�е�������Ϣ
    String arrCount[] = request.getParameterValues("PubAccGridNo");
    if (arrCount != null)
    {
        String tRadio[] = request.getParameterValues("InpPubAccGridSel");  //Radioѡ��
    	String tContNo[] = request.getParameterValues("PubAccGrid9");      //��ͬ��
    
    	lineCount = arrCount.length; //����
    
    	//ѡ��ѡ��ѡ�е������ύ
    	for(int i=0;i<lineCount;i++)
    	{
    		if(tRadio[i].equals("1"))
    		{
    
    			loggerDebug("PubAccDelSave","ContNo: "+tContNo[i]);
    			
    			LCContDB tLCContDB = new LCContDB();
    			tLCContDB.setContNo(tContNo[i]);
    			tLCContSchema = tLCContDB.query().get(1);			
    		}
    	}
    }

    try
    {
		//������������
		VData vData = new VData();

		vData.addElement(tG);
		vData.addElement(tLCContSchema);
		
		//ִ�ж���
        loggerDebug("PubAccDelSave","Start to save ContDeleteUI **************");	
	
   		if(!tBusinessDelegate.submitData(vData,"DELETE",busiName))
		{       
			loggerDebug("PubAccDelSave","ɾ��ʧ�ܣ�");
   				FlagStr = "Fail";
   				Content=tBusinessDelegate.getCErrors().getFirstError().toString();                 
		}
  			//���û��ʧ�ܣ��򷵻�ɾ���ɹ�
			if (!FlagStr.equals("Fail"))
			{
				loggerDebug("PubAccDelSave","ɾ���ɹ�!");
				Content = "����ɾ���ɹ�! ";
				FlagStr = "Succ";
			}
  	}

    catch(Exception ex)
    {
            Content = "ɾ��ʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
    }
}
%>                                       

<html>
<script language="javascript">

parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

</script>
</html>
