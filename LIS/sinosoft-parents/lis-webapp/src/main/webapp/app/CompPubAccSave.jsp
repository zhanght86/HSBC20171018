<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�PubAccSave.jsp
//�����ܣ�
//�������ڣ�2005-10-10 20:05:52
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦��
  //�������
  LCPolSchema tLCPolSchema = new LCPolSchema();
  TransferData tTransferData = new TransferData(); 
  //PreComputAccBala tPreComputAccBala = new PreComputAccBala();
  String busiName="tbPreComputAccBala";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�������
  String FlagStr = "";
  String Content = "";
  String SumAccBala = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  loggerDebug("CompPubAccSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("CompPubAccSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
  CErrors tError = null;
  String tBmCert = "";
  loggerDebug("CompPubAccSave","��ʼ����");

  
int lineCount = 0;
String arrCount[] = request.getParameterValues("PubAccGridNo");
if (arrCount != null){
	String tRadio[] = request.getParameterValues("InpPubAccGridSel");	//Radioѡ��       
        String tPolNo[] = request.getParameterValues("PubAccGrid12");           //���˱�����  
                
        lineCount = arrCount.length; //����

	//ѡ��ѡ��ѡ�е������ύ
	for(int i=0;i<lineCount;i++)
	{
		if(tRadio[i].equals("1"))
		{
		    loggerDebug("CompPubAccSave","����Ͷ������ "+tPolNo[i]);

                    LCPolDB tLCPolDB = new LCPolDB();
                    tLCPolDB.setPolNo(tPolNo[i]);
                    tLCPolSchema = tLCPolDB.query().get(1);
                    //loggerDebug("CompPubAccSave","���˱�����Ϣ��"+tLCPolSchema.encode());

                }
         }
}


        try
         {
            // ׼���������� VData
            VData tVData = new VData();
            tVData.add(tGI);
            tVData.add(tLCPolSchema);

             //ִ�ж���
            loggerDebug("CompPubAccSave","Start to save PreComputAccBala **************");
            if ( tBusinessDelegate.submitData(tVData,"",busiName))
            {
        		/*    	
        		tVData.clear();
        		tVData = tPreComputAccBala.getResult();
        		loggerDebug("CompPubAccSave","tVData-----size:"+tVData.size());
        		*/
        	    //SumAccBala = tBusinessDelegate.getResult();
        		VData ttVData = tBusinessDelegate.getResult();
        		if(null!=ttVData&&ttVData.size()>0)
        		{
        			SumAccBala=(String)ttVData.getObject(0);
        		}
        		loggerDebug("CompPubAccSave","SumAccBala:" +SumAccBala);	    		            
    	   }
	 }
		            
    catch(Exception ex)
    {
      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
      FlagStr = "Fail";
    }
  

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content ="����ɹ���";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  loggerDebug("CompPubAccSave","FlagStr:"+FlagStr+"Content:"+Content);
  
} //ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterCompAcc("<%=FlagStr%>","<%=Content%>","<%=SumAccBala%>");
</script>
</html>

