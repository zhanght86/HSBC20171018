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

<%
  //������Ϣ������У�鴦��
  //�������

  LCGrpContSchema tLCGrpContSchema   = new LCGrpContSchema();
  LCGrpPolSet mLCGrpPolSet =new LCGrpPolSet();

  TransferData tTransferData = new TransferData();
  GroupPlanRiskUI tGroupPlanRiskUI   = new GroupPlanRiskUI();
  //�������
  String FlagStr = "";
  String Content = "";
  String mLoadFlag = "";
  mLoadFlag=request.getParameter("LoadFlag");

  tTransferData.setNameAndValue("LoadFlag",mLoadFlag);


  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  loggerDebug("GroupPlanRiskSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("GroupPlanRiskSave","ҳ��ʧЧ,�����µ�½");
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
  }
  else //ҳ����Ч
  {
   String Operator  = tGI.Operator ;  //�����½����Ա�˺�
   String ManageCom = tGI.ComCode  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

  CErrors tError = null;
  String tBmCert = "";
  loggerDebug("GroupPlanRiskSave","aaaa");
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String fmAction=request.getParameter("mOperate");
  String tGrpContNo = request.getParameter("GrpContNo");
  String tCValiDate=request.getParameter("CValiDate");
  String mRiskCode="";
    loggerDebug("GroupPlanRiskSave","fmAction:"+fmAction);
    if(fmAction.equals("INSERT||GROUPRISK"))
    {
        tLCGrpContSchema.setGrpContNo(tGrpContNo);
        tLCGrpContSchema.setPrtNo(tGrpContNo);
        int lineCount = 0;
        String[] arrCount = request.getParameterValues("ContPlanGridNo");
        if (arrCount != null){
        	String[] tChk = request.getParameterValues("InpContPlanGridChk");
        	String[] tRiskCode = request.getParameterValues("ContPlanGrid2");	//���ֱ���

        	lineCount = arrCount.length; //����
        		for(int i=0;i<lineCount;i++){
                   LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
                   if(!tRiskCode[i].equals(mRiskCode))
                   {
                   mRiskCode=tRiskCode[i];
                   tLCGrpPolSchema.setRiskCode(tRiskCode[i]);
                   tLCGrpPolSchema.setCValiDate(tCValiDate);
                   tLCGrpPolSchema.setPayIntv("0");
                    //��Ϊ�����в��ᴫ���ֵ�����ڴ˽���ֵ��Ϊ1000000
                   tLCGrpPolSchema.setExpPeoples("1000000");
                   tLCGrpPolSchema.setDistriFlag("0");
                   mLCGrpPolSet.add(tLCGrpPolSchema);
                   }
                   else
                   {
                   continue;
                   }
                   }
          }
    }
    if(fmAction.equals("DELETE||GROUPRISK"))
    {
         tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLCGrpContSchema.setPrtNo(request.getParameter("PrtNo"));
        int lineCount = 0;
        String[] arrCount = request.getParameterValues("ContPlanGridNo");
        if (arrCount != null){
        	String[] tChk = request.getParameterValues("InpContPlanGridChk");
        	String[] tRiskCode = request.getParameterValues("ContPlanGrid2");	//���ֱ���

        	lineCount = arrCount.length; //����
        		for(int i=0;i<lineCount;i++){
                   LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
                   if(!tRiskCode[i].equals(mRiskCode))
		   {
		     mRiskCode=tRiskCode[i];
		     tLCGrpPolSchema.setRiskCode(tRiskCode[i]);
		     tLCGrpPolSchema.setCValiDate(tCValiDate);
		     tLCGrpPolSchema.setPayIntv("0");
		     //��Ϊ�����в��ᴫ���ֵ�����ڴ˽���ֵ��Ϊ1000000
		     tLCGrpPolSchema.setExpPeoples("1000000");
		     tLCGrpPolSchema.setDistriFlag("0");
		     mLCGrpPolSet.add(tLCGrpPolSchema);
		   }
                   else
                   {
                   continue;
                   }
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
             tGroupPlanRiskUI.submitData(tVData,fmAction);
            
          }

    catch(Exception ex)
    {
      FlagStr = "Fail";
    }


  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tGroupPlanRiskUI.mErrors;
    if (!tError.needDealError())
    {
    	FlagStr = "Succ";
    }
    else
    {
    	FlagStr = "Fail";
    }
   }
     loggerDebug("GroupPlanRiskSave","aaaa:"+FlagStr);
%>
<script language="javascript">
	parent.fraInterface.fm.all("mFlagStr").value="<%=FlagStr%>";
</script>
<%
} //ҳ����Ч��
%>


