<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LRAscriptionSave.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:12:33
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.wagecal.*"%>
  <%@page import="com.sinosoft.lis.reagent.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    //������Ϣ������У�鴦��
    //�������
    LJSPaySchema tLJSPaySchema;
    LJSPaySet tLJSPaySet = new LJSPaySet();
    
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
    
    
        
    //SpePolBankTagUI tSpePolBankTagUI  = new SpePolBankTagUI();

    //�������
    CErrors tError = null;
    String tOperate="INSERT||MAIN";
    String tRela  = "";
    String FlagStr = "Fail";
    String Content = "";

    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");

    loggerDebug("SpePolBankTagSave","begin tSpePolBankTagUI schema...");
    //ȡ����Ȩ��Ϣ
    int lineCount = 0;
    String tChk[] = request.getParameterValues("InpAscriptionGridChk");
    loggerDebug("SpePolBankTagSave","22@@"+tChk.length);
    int number = 0; 
    for(int index=0;index<tChk.length;index++) {
        if(tChk[index].equals("1"))           
           number++;
    }
    loggerDebug("SpePolBankTagSave","number"+number);    
    if(number==0) {
        Content = " ʧ�ܣ�ԭ��:û��ѡ��Ҫ�����ı�����";
       	FlagStr = "Fail";		
	  }else {
	      String tContNo[] = request.getParameterValues("AscriptionGrid3");      
	      lineCount = tChk.length; //���� 
		
	      for(int i=0;i<lineCount;i++) {	        
	         if(tChk[i].trim().equals("1"))
	       {  
	           loggerDebug("SpePolBankTagSave","*********"+tContNo[i].trim());  
              loggerDebug("SpePolBankTagSave","###"+i);
              tLJSPaySchema = new LJSPaySchema();
              tLJSPaySchema.setOtherNo(tContNo[i]);
              tLJSPaySet.add(tLJSPaySchema);
	         } 
	      } 
	      loggerDebug("SpePolBankTagSave","end ������Ϣ...");
	  
	      // ׼���������� VData
        VData tVData = new VData();
        
        FlagStr=""; 
        tVData.addElement(tLJSPaySet); 
        tVData.add(tG);  
        boolean t=false;
        try { 
            
           //t = tSpePolBankTagUI.submitData(tVData,tOperate); 
             t = tBusinessDelegate.submitData(tVData,tOperate,"SpePolBankTagUI");
           loggerDebug("SpePolBankTagSave","weikai ���ԣ�����");
        } catch(Exception ex) {
           Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
           FlagStr = "Fail";
        } 
        if (!FlagStr.equals("Fail")) {
           //tError = tSpePolBankTagUI.mErrors;
           tError =  tBusinessDelegate.getCErrors(); 
           if( t )
           {
               if ( !tError.needDealError()) 
               {
        	        Content = " ����ɹ�! ";
        	        FlagStr = "Succ";
               }
               else
               {
                   Content = "ע����𱣵�û�д���ɹ���ԭ���磺 " +tError.getFirstError();
                   FlagStr="Succ";
               }
           }
           else 
           {
    	        Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	        FlagStr = "Fail";
           } 
        } 
	  }
    
  
  
  //��Ӹ���Ԥ����

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

