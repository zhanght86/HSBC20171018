<%
//�������ƣ�PEdorTypePTSubmit.jsp
//�����ܣ�
//�������ڣ�2005-07-3 16:49:22
//������  ��lizhuo
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
 <%@page import="com.sinosoft.utility.*"%>
 <%@page import="com.sinosoft.lis.schema.*"%>
 <%@page import="com.sinosoft.lis.db.*"%>
 <%@page import="com.sinosoft.lis.vschema.*"%>
 <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
 <%@page import="com.sinosoft.service.*" %>

<%
    //������Ϣ������У�鴦��
    //�������
    //����������Ϣ
    System.out.println("-----PT submit---");
    GlobalInput tG = new GlobalInput();
    LPPolSchema tLPPolSchema   = new LPPolSchema();
    LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();

    CErrors tErrors = new CErrors();
    //����Ҫִ�еĶ�������ӣ��޸�

    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String[] Result = new String[14];


    //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    transact = request.getParameter("fmtransact");
    String Flag266 = request.getParameter("Flag266");
    String Flag267 = request.getParameter("Flag267");
    String ContNo = request.getParameter("ContNo");
    String EdorNo = request.getParameter("EdorNo");
    String SubScale = request.getParameter("SubScale");
    System.out.println("SubScale"+SubScale);
    String sAppObj = request.getParameter("AppObj");
    System.out.println("sAppObj"+sAppObj);
    
    String tPTFlagIn = null;

    if(!SubScale.trim().equals("NO"))
    {
        tLPPolSchema.setStandbyFlag1(SubScale);
    }

    tG = (GlobalInput)session.getValue("GI");
    String tMinusNumber = null;
    
    System.out.println("Flag266"+Flag266);
    System.out.println("Flag267"+Flag267);
    //�˴�����lizhuo��Ӧ������д�� comment by jiaqiangli 2008-09-04
    if(Flag266.trim().equals("00266000") || Flag267.trim().equals("00267000"))
    {
        System.out.println("Flag266=======" + Flag266.substring(2,5));
        String Str1 = "select * from lcpol where mainpolno = polno and contno = '" + ContNo + "'";
        System.out.println("Str1:===" + Str1);
        LCPolDB tLCPolDB = new LCPolDB();
        LCPolSchema tLCPolSchema = new LCPolSchema();
        LCPolSet tLCPolSet = new LCPolSet();
        tLCPolSet = tLCPolDB.executeQuery(Str1);
        tLCPolSchema = tLCPolSet.get(1);
        String Str2 = "select * from lppol where mainpolno = polno and contno = '" + ContNo + "' and edorno = '" + EdorNo + "'";
        System.out.println("Str2:===" + Str2);
        LPPolDB aLPPolDB = new LPPolDB();
        LPPolSchema aLPPolSchema = new LPPolSchema();
        LPPolSet aLPPolSet = new LPPolSet();
        aLPPolSet = aLPPolDB.executeQuery(Str2);
        aLPPolSchema = aLPPolSet.get(1);
        String Str3 = "select * from lcpol where contno = '" + ContNo + "' and appflag = '1' and substr(trim(riskcode),3,3) = '" + Flag266.trim().substring(2,5) + "'";
        System.out.println("Str3:===" + Str3);
        tLCPolSet.clear();
        tLCPolSet = tLCPolDB.executeQuery(Str3);
        System.out.println("Amnt");
        System.out.println("LCPol Amnt" +tLCPolSchema.getAmnt());
        System.out.println("LPPol Amnt" +aLPPolSchema.getAmnt());
        tLPPolSchema.setAmnt(tLCPolSchema.getAmnt() - aLPPolSchema.getAmnt());
        tLPPolSchema.setPolNo(tLCPolSet.get(1).getPolNo());
    }
    else
    {
        String theCurrentDate = PubFun.getCurrentDate();
        String theCurrentTime = PubFun.getCurrentTime();

        String tChk[] = request.getParameterValues("InpPolGridSel");
        String tRiskCode[] = request.getParameterValues("PolGrid1");
        String tPolNo[] = request.getParameterValues("PolGrid10");
        String tAmntFlag[] = request.getParameterValues("PolGrid11");
        
        //add by jiaqiangli 2008-11-20 PTFlag PTFlag 0���ٱ���1���ٷ���2���ٱ���
        String tPTFlag[] = request.getParameterValues("PolGrid12");
        
        //System.out.println("tChk.length =========>"+tChk.length);
        //System.out.println("tChk[0] =========>"+tChk[0]);
        for(int index=0;index<tChk.length;index++)
        {
           if(tChk[index].equals("1"))
           {
               tLPPolSchema.setPolNo(tPolNo[index]);
               try{
                  System.out.println(tMinusNumber);

                  if (tPTFlag[index].equals("0")) {
                	 tMinusNumber = request.getParameter("MinusPTPrem");
                     tLPPolSchema.setStandPrem(tMinusNumber);
                  }
                  else if(tPTFlag[index].equals("1")) {
                	 tMinusNumber = request.getParameter("MinusPTMut");
                     tLPPolSchema.setMult(tMinusNumber);
                     System.out.println("tLPPolSchema" + tLPPolSchema.getAmnt());
                  }
                  else {
                	 tMinusNumber = request.getParameter("MinusPTAmnt");
                     tLPPolSchema.setAmnt(tMinusNumber);
                  }
                  tPTFlagIn = tPTFlag[index];
               }
               catch(Exception e){
                  System.out.println("�������ݲ����Ϲ淶!");
                  Content = "�������ݲ����Ϲ淶!";
                  FlagStr = "Fail";
               }
               break;
           }
        }
    }

    if (!FlagStr.equals("Fail"))
    {
        //����������Ϣ
        tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
        tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
        tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
        tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
        tLPEdorItemSchema.setPolNo("000000");
        tLPEdorItemSchema.setInsuredNo("000000");
        tLPEdorItemSchema.setEdorReason(request.getParameter("SurrReasonName"));
        tLPEdorItemSchema.setEdorReasonCode(request.getParameter("ReasonCode"));
        tLPEdorItemSchema.setStandbyFlag2(request.getParameter("RelationToAppnt"));

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tLPEdorItemSchema);
        tVData.addElement(tLPPolSchema);
        tVData.addElement(tPTFlagIn);
        System.out.println(" tLPPolSchema ========>"+tLPPolSchema.encode());

        //���ָ��������ŵ�����
        if (sAppObj != null && sAppObj.trim().equalsIgnoreCase("G"))
        {
            //LPGrpEdorItemSchema
            LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
            tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
            tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
            tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
            tVData.add(tLPGrpEdorItemSchema);
            tLPGrpEdorItemSchema = null;
            //GrpEdorPTDetailBLF
            GrpEdorPTDetailBLF tGrpEdorPTDetailBLF = new GrpEdorPTDetailBLF();
//            String GbusiName="tGrpEdorPTDetailBLF";
//	 	 	  BusinessDelegate GBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            if (!tGrpEdorPTDetailBLF.submitData(tVData, "OPERATION"))
            {
                tErrors.copyAllErrors(tGrpEdorPTDetailBLF.mErrors);
            }
            tGrpEdorPTDetailBLF = null;
        }
        else
        {
            //PEdorPTDetailUI
//            PEdorPTDetailUI tPEdorPTDetailUI = new PEdorPTDetailUI();
            String busiName="tPEdorPTDetailUI";
	 	 	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//            if (!tPEdorPTDetailUI.submitData(tVData, "OPERATION"))
            if (!tBusinessDelegate.submitData(tVData, "OPERATION", busiName))
            {
//                tErrors.copyAllErrors(tPEdorPTDetailUI.mErrors);
                tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
            }
//            tPEdorPTDetailUI = null;
            tBusinessDelegate = null;
        }
        tVData = null;
    }

    //ҳ�淴����Ϣ
    if (!tErrors.needDealError())
    {
        FlagStr = "Success";
        Content = "�����ɹ���";
    }
    else
    {
        FlagStr = "Fail";
        Content = "����ʧ�ܣ�ԭ���ǣ�" + tErrors.getFirstError();
    }
    tErrors = null;
%>


<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>
