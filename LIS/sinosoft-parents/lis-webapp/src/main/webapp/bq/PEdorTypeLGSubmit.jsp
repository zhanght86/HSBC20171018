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
    System.out.println("-----BG submit---");
    GlobalInput tG = new GlobalInput();
    
    LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();

    CErrors tErrors = new CErrors();
    //����Ҫִ�еĶ�������ӣ��޸�

    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String transact = "";
    
    //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    transact = request.getParameter("fmtransact");

    String ContNo = request.getParameter("ContNo");
    String EdorNo = request.getParameter("EdorNo");

    tG = (GlobalInput)session.getValue("GI");
    
    //�������ı���
    if (transact.equals("EdorSave")) {

        //����������Ϣ
        tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
        tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
        tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));        
    LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
	// String tChk[] = request.getParameterValues("InpPolGridChk"); 
    String tInsuAccBala[] = request.getParameterValues("PolGrid10");
    String tPolNo[] = request.getParameterValues("PolGrid8");
    String tInsuAccNo[] = request.getParameterValues("PolGrid9");  
        
    for(int nIndex = 0; nIndex < tInsuAccBala.length; nIndex++ ) 
    {
      // If this line isn't selected, continue
      LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
      
      tLCInsureAccSchema.setContNo(request.getParameter("ContNo"));
      tLCInsureAccSchema.setPolNo(tPolNo[nIndex]);
      tLCInsureAccSchema.setInsuAccNo(tInsuAccNo[nIndex]);
      if (tInsuAccBala[nIndex] != null)
      {
       System.out.println("tInsuAccBala[nIndex]:"+tInsuAccBala[nIndex]);
       tLCInsureAccSchema.setInsuAccBala(tInsuAccBala[nIndex]);
       tLCInsureAccSet.add(tLCInsureAccSchema);
      }  

    }
    
    String LPBnfName[] = request.getParameterValues("CustomerActBnfGrid1"); //����
    String LPBnfSex[] = request.getParameterValues("CustomerActBnfGrid2"); //�Ա�
    String LPBnfDate[] = request.getParameterValues("CustomerActBnfGrid3"); //����
    String LPBnfIDType[] = request.getParameterValues("CustomerActBnfGrid4"); //֤������
    String LPBnfIDNo[] = request.getParameterValues("CustomerActBnfGrid5"); //֤������
    String LPBnfRelation[] = request.getParameterValues("CustomerActBnfGrid6"); //�뱻���˹�ϵ
    String LPBnfLot[] = request.getParameterValues("CustomerActBnfGrid7"); //��ȡ����
    String LPBnfGetFrom[] = request.getParameterValues("CustomerActBnfGrid8"); //��ȡ��ʽ
    String LPBnfBankCode[] = request.getParameterValues("CustomerActBnfGrid9"); //���б���
    String LPBnfBankAcc[] = request.getParameterValues("CustomerActBnfGrid10"); //�����˺�
    String LPBnfBankAccName[] = request.getParameterValues("CustomerActBnfGrid11"); //�����ʻ���
    
    LPBnfSet tLPBnfSet = new LPBnfSet();
    for(int i=0;i<LPBnfName.length;i++)
    {
    	LPBnfSchema tLPBnfSchema = new LPBnfSchema();
    	tLPBnfSchema.setName(LPBnfName[i]);
    	tLPBnfSchema.setSex(LPBnfSex[i]);
    	tLPBnfSchema.setBirthday(LPBnfDate[i]);
    	tLPBnfSchema.setIDType(LPBnfIDType[i]);
    	tLPBnfSchema.setIDNo(LPBnfIDNo[i]);
    	tLPBnfSchema.setRelationToInsured(LPBnfRelation[i]);
    	tLPBnfSchema.setBnfLot(LPBnfLot[i]);
    	tLPBnfSchema.setRemark(LPBnfGetFrom[i]);
    	tLPBnfSchema.setBankCode(LPBnfBankCode[i]);
    	tLPBnfSchema.setBankAccNo(LPBnfBankAcc[i]);
    	tLPBnfSchema.setAccName(LPBnfBankAccName[i]);
    	tLPBnfSet.add(tLPBnfSchema);
    }
    
        // ׼���������� VData
        VData tVData = new VData();
//        EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
        String busiName="EdorDetailUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        try
        {
            tVData.add(tG);
            tVData.add(tLPEdorItemSchema);
            tVData.add(tLCInsureAccSet);
            tVData.add(tLPBnfSet);
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
//                tErrors = tEdorDetailUI.mErrors;
                tErrors = tBusinessDelegate.getCErrors(); 
                if (!tErrors.needDealError())
                {
                    Content ="����ɹ���";
                    FlagStr = "Succ";
                }
                else
                {
                    Content = "����ʧ�ܣ�ԭ����:" + tErrors.getFirstError();
                    FlagStr = "Fail";
                }
        }
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
       parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
    </script>
</html>