<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�LDPersonSave.jsp
//�����ܣ�
//�������ڣ�2002-06-27 08:49:52
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
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦��
  //�������
  
  LCGrpContSchema tLCGrpContSchema   = new LCGrpContSchema();
  LCGrpPolSet mLCGrpPolSet =new LCGrpPolSet();
 
  TransferData tTransferData = new TransferData(); 
  String busiName="cardgrpGroupRiskUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 // GroupRiskUI tGroupRiskUI   = new GroupRiskUI();
  //�������
  String FlagStr = "";
  String Content = "";
  String mLoadFlag = "";
  mLoadFlag=request.getParameter("LoadFlag");
  
  tTransferData.setNameAndValue("LoadFlag",mLoadFlag);
  
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  loggerDebug("GroupRiskSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("GroupRiskSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
   String Operator  = tGI.Operator ;  //�����½����Ա�˺�
   String ManageCom = tGI.ComCode  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
  
  CErrors tError = null;
  String tBmCert = "";
  loggerDebug("GroupRiskSave","aaaa");
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String fmAction=request.getParameter("fmAction");
    loggerDebug("GroupRiskSave","fmAction:"+fmAction); 

/*        
  String tLimit="";
  String CustomerNo="";
*/ 
    if(fmAction.equals("INSERT||GROUPRISK"))
    {
        tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLCGrpContSchema.setPrtNo(request.getParameter("PrtNo"));
        
        LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
        tLCGrpPolSchema.setRiskCode(request.getParameter("RiskCode"));
        tLCGrpPolSchema.setCValiDate(request.getParameter("CValiDate"));
        tLCGrpPolSchema.setPayIntv(request.getParameter("PayIntv"));
        tLCGrpPolSchema.setChargeFeeRate(request.getParameter("ChargeFeeRate"));
        tLCGrpPolSchema.setCommRate(request.getParameter("CommRate"));
        //��Ϊ�����в��ᴫ���ֵ�����ڴ˽���ֵ��Ϊ1000000
        tLCGrpPolSchema.setExpPeoples("1000000"); 
        //tLCGrpPolSchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));
        tLCGrpPolSchema.setDistriFlag(request.getParameter("DistriFlag"));
        loggerDebug("GroupRiskSave","BonusRate: "+request.getParameter("BonusRate"));
        loggerDebug("GroupRiskSave","FixprofitRate: "+request.getParameter("FixprofitRate"));
        tLCGrpPolSchema.setBonusRate(request.getParameter("BonusRate")); //add by liuqh 2008-11-25
        tLCGrpPolSchema.setFixprofitRate(request.getParameter("FixprofitRate"));
        mLCGrpPolSet.add(tLCGrpPolSchema);
    }
    if(fmAction.equals("DELETE||GROUPRISK"))
    {
        String tRiskNum[] = request.getParameterValues("RiskGridNo");
		String tRiskCode[] = request.getParameterValues("RiskGrid1");            //���ֱ���
		String tChk[] = request.getParameterValues("InpRiskGridChk"); 
		loggerDebug("GroupRiskSave","tChk.length"+tChk.length);
         tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLCGrpContSchema.setPrtNo(request.getParameter("PrtNo"));
        for(int index=0;index<tChk.length;index++)
        {
          if(tChk[index].equals("1"))
          {
            LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
            loggerDebug("GroupRiskSave","Riskcode"+tRiskCode[index]+":"+request.getParameter("GrpContNo"));
            tLCGrpPolSchema.setRiskCode(tRiskCode[index]);
            tLCGrpPolSchema.setGrpContNo(request.getParameter("GrpContNo")); 
            mLCGrpPolSet.add(tLCGrpPolSchema);
          
          }           
        }                   
    }    
        try
         {
            // ׼���������� VData
             VData tVData = new VData();
             
             tVData.add(mLCGrpPolSet);
             tVData.add(tLCGrpContSchema);
             tVData.add(tTransferData);
             tVData.add(tGI);
             
             
              
             //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
            if ( tBusinessDelegate.submitData(tVData,fmAction,busiName))
            {
    		    if (fmAction.equals("INSERT||GROUPRISK"))
		        {
		    	    loggerDebug("GroupRiskSave","11111------return");
			        	
		    	    tVData.clear();
		    	    tVData = tBusinessDelegate.getResult();
		    	    
		    	    LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet(); 
			        tLCGrpPolSet=(LCGrpPolSet)tVData.getObjectByObjectName("LCGrpPolSet",0);
			        TransferData mTransferData = new TransferData();
			        mTransferData=(TransferData)tVData.getObjectByObjectName("TransferData",0);
			        String RiskName=(String)mTransferData.getValueByName("RiskName");
			        loggerDebug("GroupRiskSave","RiskName"+RiskName);
		            %>
		            <SCRIPT language="javascript">
		           
		            	parent.fraInterface.RiskGrid.addOne("RiskGrid");     
                        parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,1,"<%=tLCGrpPolSet.get(1).getRiskCode()%>");
                        parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,2,"<%=RiskName%>");
                        parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,3,"<%=tLCGrpPolSet.get(1).getPayIntv()%>");
                        parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,4,"<%=tLCGrpPolSet.get(1).getExpPeoples()%>");
                        if (<%=tLCGrpPolSet.get(1).getPeoples2()%>==null)
                            parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,5,"0");
                        else
                            parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,5,"<%=tLCGrpPolSet.get(1).getPeoples2()%>");
                            
                        if (<%=tLCGrpPolSet.get(1).getPrem()%>==null)
                            parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,6,"0");
                        else
                            parent.fraInterface.RiskGrid.setRowColData(parent.fraInterface.RiskGrid.mulLineCount-1,6,"<%=tLCGrpPolSet.get(1).getPrem()%>");

		            </SCRIPT>
		            <%
		        }
		        else if (fmAction.equals("DELETE||GROUPRISK"))
		        {
		             %>
		            <SCRIPT language="javascript">
		                parent.fraInterface.RiskGrid.delCheckTrueLine ();    
		            </SCRIPT>
		            <%
		        }
	        }
    }
    
    catch(Exception ex)
    {
      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
      loggerDebug("GroupRiskSave","aaaa"+ex.toString());
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
 
} //ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

