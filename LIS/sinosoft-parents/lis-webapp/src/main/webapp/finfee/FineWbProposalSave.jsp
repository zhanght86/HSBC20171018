<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�FineWBProposalSave.jsp
//�����ܣ�
//�������ڣ�2008-06-27
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.finfee.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>

<%
	//�������
	CErrors tError = null;
	String FlagStr = "Succ";
	String Content = "";
	
  loggerDebug("FineWbProposalSave","Start Save"+request.getParameter("TempFeeNo")+"...");
	VData tVData = new VData();
  GlobalInput tG = new GlobalInput();	
	tG = ( GlobalInput )session.getValue( "GI" );
	loggerDebug("FineWbProposalSave","tG: "+tG);
	LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema(); 
	LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema(); 
	SSRS tRiskInfoSSRS = null;
	String tProposalNo=request.getParameter("TempFeeNo");
	try
	{	  
	  tLJTempFeeSchema.setTempFeeNo(request.getParameter("TempFeeNo"));
	  tLJTempFeeSchema.setManageCom(request.getParameter("ManageCom"));
	  tLJTempFeeSchema.setAgentCode(request.getParameter("AgentCode"));
	  //tLJTempFeeSchema.setTempFeeType(request.getParameter("TempFeeType"));
	  tLJTempFeeSchema.setOtherNo(request.getParameter("OtherNo"));
	  tLJTempFeeSchema.setTempFeeType("1"); //�µ�����
	  tLJTempFeeSchema.setTempFeeNoType("");
	  tLJTempFeeSchema.setOperState("0");
 	  tVData.add(tLJTempFeeSchema);  
 	  loggerDebug("FineWbProposalSave","tLJTempFeeSchema:"+tLJTempFeeSchema.encode());
 	  
 	  tLJTempFeeClassSchema.setTempFeeNo(request.getParameter("TempFeeNo"));
 	  tLJTempFeeClassSchema.setManageCom(request.getParameter("ManageCom"));
 	  tLJTempFeeClassSchema.setOperState("0");
      
 	 String tClassNum[]   = request.getParameterValues("TempClassGridNo");
 	  String tPayMode[]   = request.getParameterValues("TempClassGrid1");
	  String tPayMoney[]   = request.getParameterValues("TempClassGrid2");
	  String tChequeNo[]  = request.getParameterValues("TempClassGrid3");
	  String tBankCode[]   = request.getParameterValues("TempClassGrid5");
	  String tBankAccNo[]     = request.getParameterValues("TempClassGrid6");
	  String tAccName[]   = request.getParameterValues("TempClassGrid7");
	  String tIDNo[]     = request.getParameterValues("TempClassGrid8");
	  
	  int RiskCount = 0;
	  if (tClassNum != null) 
	  RiskCount = tClassNum.length;	  
	  
	  if ( RiskCount >0)
	  {
		  tLJTempFeeClassSchema.setPayMode(tPayMode[0]);
		  if(tPayMode[0] == null || "".equals(tPayMode[0]))
	      {
	      }
	      else if("4".equals(tPayMode[0]))
	      {
	    	  tLJTempFeeSchema.setTempFeeNoType("2");
	      }
	      else
	    	  tLJTempFeeSchema.setTempFeeNoType("1");
		  
		  tLJTempFeeClassSchema.setPayMoney(Double.parseDouble(tPayMoney[0]));
		  tLJTempFeeClassSchema.setChequeNo(tChequeNo[0]);
		  tLJTempFeeClassSchema.setBankCode(tBankCode[0]);
		  tLJTempFeeClassSchema.setBankAccNo(tBankAccNo[0]);
		  tLJTempFeeClassSchema.setOriBankAccNo(tBankAccNo[0]); //��¼ԭʼ�����˺���Ϣ
		  tLJTempFeeClassSchema.setAccName(tAccName[0]);
		  tLJTempFeeClassSchema.setIDNo(tIDNo[0]);
		  if(tIDNo[0]!=null && !tIDNo[0].equals(""))
    		  tLJTempFeeClassSchema.setIDType("0"); //���֤����

	  }
	  
	  tVData.add(tLJTempFeeClassSchema);
	  loggerDebug("FineWbProposalSave","tLJTempFeeClassSchema:"+tLJTempFeeClassSchema.encode());
	  
	  RiskCount = 0;
	  
	  VData tRiskVData = new VData();
	  
	  String tRiskNum[]   = request.getParameterValues("TempGridNo");
	  String tRiskCode[]   = request.getParameterValues("TempGrid1");
	  String tMoney[]  = request.getParameterValues("TempGrid2");
	  String tPayIntv[]   = request.getParameterValues("TempGrid4");
	  String tPayYears[]     = request.getParameterValues("TempGrid5");
	  
	  if (tRiskNum != null) 
	  RiskCount = tRiskNum.length;
	  
	  for (int i = 0; i < RiskCount; i++)
	  {
	  	FineRiskBasicInfo tRiskBasicInfo = new FineRiskBasicInfo();
	  	tRiskBasicInfo.setRiskCode(tRiskCode[i]);
	  	tRiskBasicInfo.setPayMoney(tMoney[i]);
	  	tRiskBasicInfo.setPayYears(tPayYears[i]);
	  	tRiskBasicInfo.setPayIntv(tPayIntv[i]);
	  	
	  	tRiskBasicInfo.convertRiskinfo();   //ת��������Ϣ
	  	loggerDebug("FineWbProposalSave","tRiskBasicInfo:"+tRiskBasicInfo.encode());	  	
	  	tRiskVData.add(tRiskBasicInfo);
	  }
	  tVData.add(tRiskVData);
	  tVData.add(tG);
	  
	  TransferData tTransferData = new TransferData();
	  String tDealType=request.getParameter("DealType");
 	  loggerDebug("FineWbProposalSave","tDealType:"+tDealType);
 	  tTransferData.setNameAndValue("DealType",tDealType);
	  loggerDebug("FineWbProposalSave","DealType:"+tTransferData.getValueByName("DealType"));
 	  tVData.add(tTransferData);	  	  
 	  
 	  String fmAction = request.getParameter("fmAction");
 	  loggerDebug("FineWbProposalSave","fmAction:"+fmAction);
 	 PubConcurrencyLock mPubLock = new PubConcurrencyLock();
 	 try{
	     //���벢�����ƣ�ͬһ��������ͬһʱ��ֻ�ܴ���һ��	     
	     if (!mPubLock.lock(tLJTempFeeSchema.getTempFeeNo(), "LF0007")) {
	    	 FlagStr="Fail";
	 	  	if(mPubLock.mErrors.needDealError())
	 	  	{
	 	  		Content="�����շѵ�����֤�ţ�"+tLJTempFeeSchema.getTempFeeNo()+")���ڴ����У����Ժ����ԣ�";
	 	  	}
	 	  	loggerDebug("FineWbProposalSave","FailFailFailFailFailFail" );
		}
	     else{
	    	 ExeSQL tExe = new ExeSQL();
	     	String tExist=tExe.getOneValue("select tempfeeno from ljtempfee where tempfeeno='"+tLJTempFeeSchema.getTempFeeNo()+"' ");
	     	loggerDebug("FineWbProposalSave","tExist:"+tExist);
	     	if(tExist!=null && !tExist.equals(""))
	     	{
	     		FlagStr="Fail";
		 	    Content = "���շ���Ϣ�Ѿ����棬�����ٴα��棡";
	     	}
	     	else
	     	{
	     		FineBPODealInputDataBL tBPODealInputDataBL = new FineBPODealInputDataBL();
		    	  
		    	  if(fmAction.equals("INSERT"))
		    	  {
		    	  	loggerDebug("FineWbProposalSave","fmAction:"+fmAction);       
		    	  	boolean aaa=tBPODealInputDataBL.DealOneRightPol(tVData,request.getParameter("PayDate"),1);
		    	  	if(!aaa)
		    	  	{
		    	  		FlagStr="Fail";
		    	  		if(tBPODealInputDataBL.mErrors.needDealError())
		    	  		{
		    	  			Content=tBPODealInputDataBL.mErrors.getFirstError();
		    	  		}
		    	  		loggerDebug("FineWbProposalSave","FailFailFailFailFailFail" );
		    	  		
		    	  		loggerDebug("FineWbProposalSave","***��ʼ�����Ѿ�����Ĳ����շѵ�����..." );
		    	  	  if(!tBPODealInputDataBL.redoInputedPol(tLJTempFeeSchema.getTempFeeNo()))
		           {
		             loggerDebug("FineWbProposalSave","***�����Ѿ�����Ĳ����շѵ�����ʧ��"+ tLJTempFeeSchema.getTempFeeNo() +" : "+tBPODealInputDataBL.mErrors.getLastError());
		           }
		          else
		         	{
		         	 loggerDebug("FineWbProposalSave","***�����Ѿ�����Ĳ����շѵ��������..." );
		         	}
		    	  	 }
		    	  	loggerDebug("FineWbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
		    	  } 
		    	  if(fmAction.equals("DELETE"))
		    	  {
		    	          boolean aaa=tBPODealInputDataBL.updateBPOState(tLJTempFeeSchema.getTempFeeNo(),tDealType,"2");
		    	  	if(!aaa)
		    	  	{
		    	  		FlagStr="Fail";
		    	  		if(tBPODealInputDataBL.mErrors.needDealError())
		    	  		{
		    	  			Content=tBPODealInputDataBL.mErrors.getFirstError();
		    	  		}
		    	  		loggerDebug("FineWbProposalSave","FailFailFailFailFailFail" );
		    	  	}
		    	  	loggerDebug("FineWbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
		    	  }
	     	}	    	 
	     }	  
 	   }
	 	catch(Exception e)
		{
	 		FlagStr="Fail";
	 	    Content = "����ʱ�����쳣:e.toString()";
		}
		finally
		{
			mPubLock.unLock();
		}
 	  
	  if(FlagStr.equals("Fail")){
		  tProposalNo = "";
		  loggerDebug("FineWbProposalSave","����ʧ�ܣ�" + tProposalNo);
    
	  } // end of if
	  //FlagStr="Succ";
	  loggerDebug("FineWbProposalSave",Content);
	  
	  //�滻��������Ϣ�������ַ�
	  Content = Content.replace('\n', ' ');
	  
	  while (Content.indexOf("\"") != -1) 
	  {
      		Content = Content.replace('\"', ' ');
      		loggerDebug("FineWbProposalSave",Content);
	  }
    
    
    	loggerDebug("FineWbProposalSave",Content);
    	loggerDebug("FineWbProposalSave",FlagStr); 
    	
     }
     catch(Exception ex)
     {
      FlagStr="Fail";
      Content = "����ʱ�����쳣��";
     }
     
   %>    

                  
<html>
<script language="javascript">
	try 
	{	    
		parent.fraInterface.document.all('TempFeeNoHide').value='<%=tProposalNo%>';
		parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
	}	catch(ex) { 
		//alert("after Save but happen err:" + ex);
	}
</script>
 
</html>

