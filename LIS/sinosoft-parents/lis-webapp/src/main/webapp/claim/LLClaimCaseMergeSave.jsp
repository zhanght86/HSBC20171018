<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.*" %>
<%
  

	  String mlastClmNo="";
	  String mlastAccNo="";
	
	  String tRadio[] = request.getParameterValues("InpLLClaimMergeGridSel");             
	  String lastClmNo[] = request.getParameterValues("LLClaimMergeGrid1");
	  String lastAccNo[] = request.getParameterValues("LLClaimMergeGrid8");
	 
	
	  //���ж���һ�б�ѡ��
	   for(int i=0;i<tRadio.length;i++)
	   {
	      loggerDebug("LLClaimCaseMergeSave","***"+i);
	       if(tRadio[i].equals("1"))     
	       {      
	        	loggerDebug("LLClaimCaseMergeSave","��"+(i+1)+"�б�ѡ��");
	        
	        	//�õ��ѽ᰸������������
	        	mlastClmNo=lastClmNo[i];
	        	mlastAccNo=lastAccNo[i];
	  			loggerDebug("LLClaimCaseMergeSave","�ѽ᰸�������ⰸ��:"+mlastClmNo+",�¼���:"+mlastAccNo);
	  		}
	   }     
	  
		  
	  String mAccNo=request.getParameter("AccNo");
      String mClmNo=request.getParameter("ClmNo");
      
      loggerDebug("LLClaimCaseMergeSave","���ΰ������ⰸ��:"+mClmNo+",�¼���:"+mAccNo);

	  
	  //�������ͱ�־
	  String FlagStr = "";
	  String Content = "";
	  CErrors tError = null;
	
	  GlobalInput tG = new GlobalInput();
	  tG=(GlobalInput)session.getValue("GI");
	  
	  String mfmtransact=request.getParameter("fmtransact");
	  loggerDebug("LLClaimCaseMergeSave","��������:"+mfmtransact);

	    
	  //Stringʹ��TransferData������ύ
	  TransferData mTransferData = new TransferData();
	  mTransferData.setNameAndValue("ClmNo",mClmNo);
	  mTransferData.setNameAndValue("AccNo",mAccNo);
	  mTransferData.setNameAndValue("LastClmNo",mlastClmNo);
	  mTransferData.setNameAndValue("LastAccNo",mlastAccNo);
	  
	  //LLClaimMergeBL tLLClaimMergeBL=new LLClaimMergeBL();
	  String busiName="LLClaimMergeBL";
	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  
	  String newAccNo=""; //���ص����¼���

	  try
	  {
	    VData tVData = new VData();
	    tVData.addElement(mTransferData);
	    tVData.addElement(tG);

	    //�ύ����
//        if(!tLLClaimMergeBL.submitData(tVData,mfmtransact))
//        {
//            Content = " �����ύʧ�ܣ�ԭ����: " + tLLClaimMergeBL.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		 if(!tBusinessDelegate.submitData(tVData,mfmtransact,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "�����ύʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "�����ύʧ��";
						   FlagStr = "Fail";				
				}
		   }

        else
        {
            Content = " �����ύ�ɹ���";
            FlagStr = "Succ";
            
            VData tResult = new VData();
           // tResult = tLLClaimMergeBL.getResult();	
             tResult = tBusinessDelegate.getResult();	
            newAccNo = (String)tResult.getObjectByObjectName("String",0);
            loggerDebug("LLClaimCaseMergeSave","����"+mClmNo+"ԭ���¼���"+mAccNo+",���¼���:"+newAccNo);
        }
	  }
	  catch(Exception ex)
	  {
	    Content = "�����ύʧ��ʧ�ܣ�ԭ����:" + ex.toString();
	    FlagStr = "Fail";
	  }	  
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=newAccNo%>");
</script>
</html>
