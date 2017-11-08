<%
//**************************************************************************************************
//Name��LLClaimConfirmSave.jsp
//Function�����������ύ
//Author��zhoulei
//Date:
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

//�������
LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //���������

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
String DecisionSP=request.getParameter("DecisionSP");

//ҳ����Ч
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimConfirmSave","ҳ��ʧЧ,�����µ�½");    
}
else
{
    //��ȡ����insert||update
    String transact = request.getParameter("fmtransact");
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");
    String mRptNo = request.getParameter("RptNo");

    //��ȡ����ҳ����Ϣ
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //�ⰸ��
    tLLClaimUWMainSchema.setExamConclusion(request.getParameter("DecisionSP")); //��������
    tLLClaimUWMainSchema.setExamIdea(request.getParameter("RemarkSP")); //�������
    tLLClaimUWMainSchema.setExamNoPassReason(request.getParameter("ExamNoPassReason")); //��ͨ��ԭ��
    tLLClaimUWMainSchema.setExamNoPassDesc(request.getParameter("ExamNoPassDesc")); //��ͨ������
    
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "50");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate2"));
    mTransferData.setNameAndValue("RgtClass", "2");  //��������
    mTransferData.setNameAndValue("RgtObj", "1");  //��������
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //��������
    mTransferData.setNameAndValue("RgtState", request.getParameter("RgtState")); //��������
    
    mTransferData.setNameAndValue("MngCom", request.getParameter("ManageComAll"));  //����
    loggerDebug("LLClaimConfirmSave","ManageComAll +++++++++++++++ "+request.getParameter("ManageComAll"));   
    mTransferData.setNameAndValue("Popedom",request.getParameter("AuditPopedom")); //���Ȩ��
    mTransferData.setNameAndValue("PrepayFlag", request.getParameter("BudgetFlag")); //Ԥ����־
    mTransferData.setNameAndValue("Auditer", request.getParameter("Auditer")); //�����
    
    //ת������
    mTransferData.setNameAndValue("Reject", request.getParameter("DecisionSP"));  //�����Ƿ�ͨ��
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));


    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);  
    tVData.add(tLLClaimUWMainSchema); 
    boolean IFDSFlag = false;
  //09-04-09  ����˫�����������̣�������������ܹ�˾����ǩ��������Ҫ��˫������������
    String tSql = "select code from ldcode where codetype='lldsflag'";
    ExeSQL tExeSQL = new ExeSQL();
	String tResult = tExeSQL.getOneValue(tSql);
	//1-˫��������Ч
	//�жϵ�ǰ����Ա�Ƿ����ܹ�˾ǩ����   ������ܹ�˾ǩ�������ж�Ŀǰ�ǵڼ��� �����Ƿ���ߵڶ��ڵ�������
	//DSClaimFlag������1
	if("1".equals(tResult)){
		//��ѯ��ǰ����Ա�Ƿ�Ϊ�ܹ�˾ǩ����  �����DSFlag=1
		String tDSFlagSql = "select distinct dsflag from llgrpclaimpopedom "
				+ "where jobcategory = "
				+ "(select jobcategory from llgrpclaimuser where usercode='"+tGI.Operator+"')";
		String tDSFlag = tExeSQL.getOneValue(tDSFlagSql);
		if("1".equals(tDSFlag)){
			String tDSClaimFlagSql = "select DSClaimFlag from LLClaimUWMain where clmno='"
					+ mRptNo + "'";
			String tDSClaimFlag = tExeSQL.getOneValue(tDSClaimFlagSql);
			//tDSClaimFlag��Ϊ1  ��˫������������
			if(!"1".equals(tDSClaimFlag)){
				IFDSFlag = true;
			}
		}
	}
	//���һ�����Ϊ��ͨ��,��ֱ�ӻ��˵������,������˫��,ֻ����������Ϊ0-ͨ��ʱ����˫��
	if(IFDSFlag==true&&tLLClaimUWMainSchema.getExamConclusion().trim().equals("0")){
		//˫������
		//QueryDefaultOperator tQueryDefaultOperator = new QueryDefaultOperator();
		String busiName="grpQueryDefaultOperator";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		try{
//			if(!tQueryDefaultOperator.submitData(tVData,"")){
//				Content = " ����ȷ��ʧ�ܣ�ԭ����: " + tQueryDefaultOperator.mErrors.getError(0).errorMessage;
//	            FlagStr = "Fail";
//			}
			if(!tBusinessDelegate.submitData(tVData,"",busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "����ȷ��ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "����ȷ��ʧ��";
					FlagStr = "Fail";				
				}
			}

			else{
				Content = " ����ȷ�ϳɹ���";
	            FlagStr = "Succ";
	            if (DecisionSP.equals("0"))
	            {
	            	//tongmeng 2009-01-06 modify
	            	//ע�����ٱ�����
	            	/*
	                 //�����ٱ��մ���     sujd   2007-12-17
	                 ReLifeNeed tReLifeNeed=new ReLifeNeed();
	                 VData tmpvdata=new VData();
	                 
	                 TransferData tmpTransferData = new TransferData();
	                 tmpTransferData.setNameAndValue("ManageCom",tGI.ManageCom);
	                 tmpTransferData.setNameAndValue("Operator",tGI.Operator);
	                 tmpTransferData.setNameAndValue("OtherNo",request.getParameter("RptNo"));
	                 
	                 tmpvdata.add(tmpTransferData);
	                 
	                 if (!tReLifeNeed.submitdata(tmpvdata,"2"))
	                 {
	                      Content=Content+";�ٱ��մ���ʧ��,����ϵ��Ϣ��������Ա(������Ҫ�᰸)!";
	                      FlagStr = "Fail";
	                 }
	                 else
	                 {
	                     Content=Content+";�ٱ��մ���ɹ�!";           
	                 }
	                 loggerDebug("LLClaimConfirmSave","==================�ٱ����������=================");
	                 */
	            }                        
	       
	            
	        }
	             /*��������ֹ�м��2�ν᰸��ť*/             
			MMap tempMMap=new MMap();
			ExeSQL ttExeSQL=new ExeSQL();
			SSRS tSSRS=new SSRS();
			String ttsql="select getdutykind from llclaim where clmno='"+mRptNo+"' and getdutykind is not null ";
			tSSRS=ttExeSQL.execSQL(ttsql);
			if(tSSRS.MaxRow>0 && tSSRS.GetText(1,1).equals("1")) {
				tempMMap.put("update llclaim set getdutykind='' where clmno='"+mRptNo+"' ","UPDATE");
				PubSubmit ttempSubmit = new PubSubmit();
		        VData temVData = new VData();
		        temVData.add(tempMMap);
		        if (!ttempSubmit.submitData(temVData, null)) {
		        	CError.buildErr(this, "�������ʧ�ܣ�");                
		        }
			}          
	        loggerDebug("LLClaimConfirmSave","-------------------end workflow---------------------");
		}catch(Exception ex){
			Content = "����ȷ��ʧ�ܣ�ԭ����:" + ex.toString();
	        FlagStr = "Fail";
		}
	}else{
	    try
	    {  
	        //�ύ����
	        loggerDebug("LLClaimConfirmSave","-------------------start workflow---------------------");
	        wFlag = "0000009055";
//	        ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//	        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//	        {
//	            Content = " ����ȷ��ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//	            FlagStr = "Fail";
//	        }
			String busiName1="grpClaimWorkFlowUI";
			BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
			if(!tBusinessDelegate1.submitData(tVData,wFlag,busiName1))
			{    
			    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			    { 
					Content = "����ȷ��ʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "����ȷ��ʧ��";
					FlagStr = "Fail";				
				}
			}

	        else
	        {
	            Content = " ����ȷ�ϳɹ���";
	            FlagStr = "Succ";
	            if (DecisionSP.equals("0"))
	            {
	            	//tongmeng 2009-01-06 modify
	            	//ע�����ٱ�����
	            	/*
	                 //�����ٱ��մ���     sujd   2007-12-17
	                 ReLifeNeed tReLifeNeed=new ReLifeNeed();
	                 VData tmpvdata=new VData();
	                 
	                 TransferData tmpTransferData = new TransferData();
	                 tmpTransferData.setNameAndValue("ManageCom",tGI.ManageCom);
	                 tmpTransferData.setNameAndValue("Operator",tGI.Operator);
	                 tmpTransferData.setNameAndValue("OtherNo",request.getParameter("RptNo"));
	                 
	                 tmpvdata.add(tmpTransferData);
	                 
	                 if (!tReLifeNeed.submitdata(tmpvdata,"2"))
	                 {
	                      Content=Content+";�ٱ��մ���ʧ��,����ϵ��Ϣ��������Ա(������Ҫ�᰸)!";
	                      FlagStr = "Fail";
	                 }
	                 else
	                 {
	                     Content=Content+";�ٱ��մ���ɹ�!";           
	                 }
	                 loggerDebug("LLClaimConfirmSave","==================�ٱ����������=================");
	                 */
	            }                        
	       
	            
	        }
	             /*��������ֹ�м��2�ν᰸��ť*/             
			         MMap tempMMap=new MMap();
			         ExeSQL ttExeSQL=new ExeSQL();
			         SSRS tSSRS=new SSRS();
			         String ttsql="select getdutykind from llclaim where clmno='"+mRptNo+"' and getdutykind is not null ";
			         tSSRS=ttExeSQL.execSQL(ttsql);
			         if(tSSRS.MaxRow>0 && tSSRS.GetText(1,1).equals("1"))
			         {
			           tempMMap.put("update llclaim set getdutykind='' where clmno='"+mRptNo+"' ","UPDATE");
			           PubSubmit tempSubmit = new PubSubmit();
	               VData temVData = new VData();
	               temVData.add(tempMMap);
	               if (!tempSubmit.submitData(temVData, null)) 
	               {
	                 CError.buildErr(this, "�������ʧ�ܣ�");                
	               }
			         }          
	        
	        loggerDebug("LLClaimConfirmSave","-------------------end workflow---------------------");
	    }
	    catch(Exception ex)
	    {
	        Content = "����ȷ��ʧ�ܣ�ԭ����:" + ex.toString();
	        FlagStr = "Fail";
	    } 
	}
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
