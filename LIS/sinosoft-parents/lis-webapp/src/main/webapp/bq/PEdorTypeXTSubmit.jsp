<%
//�������ƣ�PEdorTypeWTSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %> 

<%
    //������Ϣ������У�鴦��

    CErrors tError = null;
    //����Ҫִ�еĶ�������ӣ��޸�
    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String transact = "";
    LPPolSet mLPPolSet=new LPPolSet();

    //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    transact = request.getParameter("fmtransact");
    String edorAcceptNo = request.getParameter("EdorAcceptNo");
    String edorNo = request.getParameter("EdorNo");
    String edorType = request.getParameter("EdorType");
    String contNo = request.getParameter("ContNo");
    String sEdorReasonCode = request.getParameter("SurrReason");
    String sEdorReason = request.getParameter("ReasonContent");
    String sRelationToAppnt = request.getParameter("RelationToAppnt");
    String sWTContFLag=request.getParameter("WTContFLag");

    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    tLPEdorItemSchema.setEdorAcceptNo(edorAcceptNo);
    tLPEdorItemSchema.setEdorNo(edorNo);
    tLPEdorItemSchema.setContNo(contNo);
    tLPEdorItemSchema.setEdorType(edorType);
    tLPEdorItemSchema.setInsuredNo("000000");
    tLPEdorItemSchema.setPolNo("000000");
    tLPEdorItemSchema.setEdorReasonCode(sEdorReasonCode);
    tLPEdorItemSchema.setEdorReason(sEdorReason);
    tLPEdorItemSchema.setStandbyFlag2(sRelationToAppnt); //Ͷ������ҵ��Ա��ϵ
    tLPEdorItemSchema.setStandbyFlag3(sWTContFLag);

    String tPolNo[]= request.getParameterValues("PolGrid8");
    String tChk[] = request.getParameterValues("InpPolGridChk");
    for (int i=0; i<tChk.length; i++)
    {
        if (tChk[i].equals("1"))
        {
            LPPolSchema tLPPolSchema=new LPPolSchema();
            tLPPolSchema.setPolNo(tPolNo[i]);
            mLPPolSet.add(tLPPolSchema);
        }
    }
    if (mLPPolSet == null || mLPPolSet.size() < 1)
    {
          Content = "��ѡ��Ҫ�˱��ı���!";
          FlagStr = "Fail";
    }

    LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
    if (transact.equals("EdorSave"))
    {
        //���ݵ�����Ľ��
        String mPolNo[]= request.getParameterValues("CTFeeDetailGrid1");
        String mFeeFinaType[]= request.getParameterValues("CTFeeDetailGrid8");
        String mSubFeeOperationType[]= request.getParameterValues("CTFeeDetailGrid9");
        String mSubFeeOperationTypeName[]= request.getParameterValues("CTFeeDetailGrid5");
        //String mGetMoney[]= request.getParameterValues("CTFeeDetailGrid6");
        String mNewGetMoney[]= request.getParameterValues("CTFeeDetailGrid7");
         String mGetNoticNo[]= request.getParameterValues("CTFeeDetailGrid10");          
         String mDutyCode[]= request.getParameterValues("CTFeeDetailGrid12");
         String mPayPlanCode[]= request.getParameterValues("CTFeeDetailGrid13");
        LJSGetEndorseSchema tLJSGetEndorseSchema;
        for (int i = 0; i< mPolNo.length; i++)
        {
            tLJSGetEndorseSchema = new LJSGetEndorseSchema();
            tLJSGetEndorseSchema.setPolNo(mPolNo[i]);
            tLJSGetEndorseSchema.setFeeFinaType(mFeeFinaType[i]);
            tLJSGetEndorseSchema.setSubFeeOperationType(mSubFeeOperationType[i]);
            tLJSGetEndorseSchema.setGrpName(mSubFeeOperationTypeName[i]); //���ø��ֶδ��ݷ�������
            tLJSGetEndorseSchema.setGetMoney(mNewGetMoney[i]); //������Ľ��
            tLJSGetEndorseSchema.setGetNoticeNo(mGetNoticNo[i]);
            tLJSGetEndorseSchema.setDutyCode(mDutyCode[i]); //���α���
            tLJSGetEndorseSchema.setPayPlanCode(mPayPlanCode[i]); //�ɷѼƻ�����

            mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
        }
        if (mLJSGetEndorseSet == null || mLJSGetEndorseSet.size() < 1)
        {
            //Q: ���û���˱����ã���δ��� zhangtao 2005-11-17
        }
    }

    //�˱�����
    if (transact.equals("EdorCal")||transact.equals("EDORITEM|UPDATE"))
    {
    	  String LPBnfName[] = request.getParameterValues("CustomerActBnfGrid1"); //����
    	  String LPBnfSex[] = request.getParameterValues("CustomerActBnfGrid2"); //�Ա�
    	  String LPBnfDate[] = request.getParameterValues("CustomerActBnfGrid3"); //����
    	  String LPBnfIDType[] = request.getParameterValues("CustomerActBnfGrid4"); //֤������
    	  String LPBnfIDNo[] = request.getParameterValues("CustomerActBnfGrid5"); //֤������
    	  //String LPBnfRelation[] = request.getParameterValues("CustomerActBnfGrid6"); //�뱻���˹�ϵ
    	  String LPBnfLot[] = request.getParameterValues("CustomerActBnfGrid6"); //��ȡ���
    	  String LPBnfGetFrom[] = request.getParameterValues("CustomerActBnfGrid7"); //��ȡ��ʽ
    	  String LPBnfBankCode[] = request.getParameterValues("CustomerActBnfGrid8"); //���б���
    	  String LPBnfBankAcc[] = request.getParameterValues("CustomerActBnfGrid9"); //�����˺�
    	  String LPBnfBankAccName[] = request.getParameterValues("CustomerActBnfGrid10"); //�����ʻ���
    	  
    	  LPBnfSet tLPBnfSet = new LPBnfSet();
    	  if(LPBnfName!=null)
    	  {
    		  for(int i=0;i<LPBnfName.length;i++)
    		  {
    		  	LPBnfSchema tLPBnfSchema = new LPBnfSchema();
    		  	tLPBnfSchema.setName(LPBnfName[i]);
    		  	tLPBnfSchema.setSex(LPBnfSex[i]);
    		  	tLPBnfSchema.setBirthday(LPBnfDate[i]);
    		  	tLPBnfSchema.setIDType(LPBnfIDType[i]);
    		  	tLPBnfSchema.setIDNo(LPBnfIDNo[i]);
    		  	//tLPBnfSchema.setRelationToInsured(LPBnfRelation[i]);
    		  	tLPBnfSchema.setBnfLot(LPBnfLot[i]);
    		  	tLPBnfSchema.setRemark(LPBnfGetFrom[i]);
    		  	tLPBnfSchema.setBankCode(LPBnfBankCode[i]);
    		  	tLPBnfSchema.setBankAccNo(LPBnfBankAcc[i]);
    		  	tLPBnfSchema.setAccName(LPBnfBankAccName[i]);
    		  	tLPBnfSet.add(tLPBnfSchema);
    		  }
    	  }
        // ׼���������� VData
        VData tVData = new VData();
        
        String busiName="EdorDetailUI";
	 	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        try
        {
            tVData.add(tGlobalInput);
            tVData.add(tLPEdorItemSchema);
            tVData.add(mLPPolSet);
            tVData.add(tLPBnfSet);
            tBusinessDelegate.submitData(tVData, transact ,busiName);
        }
        catch(Exception ex)
        {
              Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
              FlagStr = "Fail";
        }
        if ("".equals(FlagStr))
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
    }

    //�����󱣴�
    if (transact.equals("EdorSave"))
    {
        // ׼���������� VData
        VData tVData = new VData();
//        EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
        String busiName="EdorDetailUI";
	 	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        try
        {
            tVData.add(tGlobalInput);
            tVData.add(tLPEdorItemSchema);
            tVData.add(mLJSGetEndorseSet);
//            tEdorDetailUI.submitData(tVData, transact);
            tBusinessDelegate.submitData(tVData, transact ,busiName);
        }
        catch(Exception ex)
        {
              Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
              FlagStr = "Fail";
        }
        if ("".equals(FlagStr))
        {
//                tError = tEdorDetailUI.mErrors;
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
    }

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
