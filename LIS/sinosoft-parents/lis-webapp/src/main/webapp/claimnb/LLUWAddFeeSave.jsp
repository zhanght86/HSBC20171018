<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpecChk.jsp
//�����ܣ��˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.claimnb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
    CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";
    String tOperate = "";
    boolean flag = true;
    GlobalInput tG = new GlobalInput();  
    tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
    loggerDebug("LLUWAddFeeSave","------------------����˱�-----�ӷѴ���Save-----��ʼ------------------");
    VData tVData = new VData();
    TransferData tTransferData = new TransferData(); 
  
	//������Ϣ
	LLUWPremMasterSet tLLUWPremMasterSet = new LLUWPremMasterSet();


	String tPolNo           = request.getParameter("PolNo"); //��ȫ��Ŀ����Եı���
	String tPolNo2          = request.getParameter("PolNo2"); //��ȫ�ӷ�����Եı���
	String tContNo          = request.getParameter("ContNo");
	String tAddReason       = request.getParameter("AddReason");
    String tClmNo           = request.getParameter("ClmNo");
    String tBatNo           = request.getParameter("BatNo");
        	
    String tChk[]           = request.getParameterValues("InpSpecGridSel");            
	String tdutycode[]      = request.getParameterValues("SpecGrid1"); //�ӷ�����
    String tPayPlanType[]   = request.getParameterValues("SpecGrid2"); //�ӷ�ԭ��
    String tstartdate[]     = request.getParameterValues("SpecGrid3"); //��ʼ����
	String tenddate[]       = request.getParameterValues("SpecGrid4"); //��ֹ����
    String tsuppriskscore[] = request.getParameterValues("SpecGrid5"); //�ӷ�����
	String tSecondScore[]   = request.getParameterValues("SpecGrid6"); //�ڶ��������˼ӷ�����
	String AddObj[]         = request.getParameterValues("SpecGrid7"); //�ӷѶ���
	String trate[]          = request.getParameterValues("SpecGrid8"); //�ӷѽ��

        
    tOperate = request.getParameter("hideOperate");
        
    loggerDebug("LLUWAddFeeSave","��ͬ��ContNo:"+tContNo);	
    loggerDebug("LLUWAddFeeSave","���κ�BatNo:"+tBatNo);       
	loggerDebug("LLUWAddFeeSave","���ֺ�PolNo:"+tPolNo);
	loggerDebug("LLUWAddFeeSave","�ӷ�ԭ��AddReason:"+tAddReason);
    loggerDebug("LLUWAddFeeSave","����:"+tOperate);
    loggerDebug("LLUWAddFeeSave","����:"+tChk.length);	
	
	int feeCount = tChk.length;
	if (feeCount == 0 ){
		Content = "��¼��ӷ���Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}else{
		if (!tPolNo.equals("")){
		
	    	//׼����Լ�ӷ���Ϣ
	    	if (feeCount > 0){
                  for (int i = 0; i < feeCount; i++) {
                        if (tChk[i].equals("1") && !tdutycode[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals("")) {
                                LLUWPremMasterSchema tLLUWPremMasterSchema = new LLUWPremMasterSchema();
                                tLLUWPremMasterSchema.setPolNo(tPolNo);
                                tLLUWPremMasterSchema.setDutyCode(tdutycode[i]);
                                tLLUWPremMasterSchema.setPayPlanType(tPayPlanType[i]);
                                //tLLUWPremMasterSchema.setAddFeeType(tAddFeeType[i]);                                                                
                             
                                tLLUWPremMasterSchema.setPayStartDate(tstartdate[i]);
                                tLLUWPremMasterSchema.setPayEndDate(tenddate[i]);
                                
                                tLLUWPremMasterSchema.setAddFeeDirect(AddObj[i]);
                                tLLUWPremMasterSchema.setSecInsuAddPoint(tSecondScore[i]);
                                tLLUWPremMasterSchema.setPrem( trate[i]);
                                tLLUWPremMasterSchema.setSuppRiskScore( tsuppriskscore[i]);
                                tLLUWPremMasterSet.add( tLLUWPremMasterSchema );
                                flag = true;	    		  	    
                                loggerDebug("LLUWAddFeeSave","���α���"+i+":  "+tdutycode[i]);
                            } // End of if

				  } // End of for				    
			  } // End of if
		    //׼������������Ϣ
            tTransferData.setNameAndValue("ClmNo",tClmNo);
            tTransferData.setNameAndValue("BatNo",tBatNo);            
            tTransferData.setNameAndValue("ContNo",tContNo);
            tTransferData.setNameAndValue("PolNo",tPolNo);                                   
            tTransferData.setNameAndValue("AddReason",tAddReason);
            
			tVData.add(tG);
			tVData.add(tTransferData);
			tVData.add(tLLUWPremMasterSet);

			
		} // End of if
		else{
			Content = "��������ʧ��!";
			flag = false;
		}
	}
    loggerDebug("LLUWAddFeeSave","flag:"+flag);
    try{
        if (flag == true){
			
            // ���ݴ���
//            LLUWAddFeeUI tLLUWAddFeeUI = new LLUWAddFeeUI();
//            if (!tLLUWAddFeeUI.submitData(tVData,tOperate)) {
//                int n = tLLUWAddFeeUI.mErrors.getErrorCount();
//                Content = "����˱��ӷ���ʾ��Ϣ: " + tLLUWAddFeeUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
            String busiName="LLUWAddFeeUI";
		 	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "����˱��ӷ���ʾ��Ϣ:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "����˱��ӷ�";
						   FlagStr = "Fail";				
				}
		   }
            
            //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
          if (FlagStr == "Fail"){
		          //tError = tLLUWAddFeeUI.mErrors;
		           tError = tBusinessDelegate.getCErrors();
		          if (!tError.needDealError()){                          
		    	           Content = "����˱��ӷѴ���ɹ�! ";
		    	           FlagStr = "Succ";
		          } else{
		    	          FlagStr = "Fail";
		         }
		     }
	   } 
 }catch(Exception e){
	   e.printStackTrace();
	   Content = Content.trim()+".��ʾ���쳣��ֹ!";
}

loggerDebug("LLUWAddFeeSave","------------------����˱�-----�ӷѴ���Save-----����------------------");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
