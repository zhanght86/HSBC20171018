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
	 String tChk[] = request.getParameterValues("InpPolGridChk"); 
	  String tSumInsuAccBala[] = request.getParameterValues("PolGrid6");
    String tInsuAccBala[] = request.getParameterValues("PolGrid7");
    String tPolNo[] = request.getParameterValues("PolGrid8");
    String tInsuAccNo[] = request.getParameterValues("PolGrid9");  
    String tInsuAccLX[] = request.getParameterValues("PolGrid10");  
        
    for(int nIndex = 0; nIndex < tChk.length; nIndex++ ) 
    {
      // If this line isn't selected, continue
      if( !tChk[nIndex].equals("1") ) 
      {
        continue;
      }   
      LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
      
      tLCInsureAccSchema.setContNo(request.getParameter("ContNo"));
      tLCInsureAccSchema.setPolNo(tPolNo[nIndex]);
      tLCInsureAccSchema.setInsuAccNo(tInsuAccNo[nIndex]);
      if (tInsuAccBala[nIndex] != null)
      {
       tLCInsureAccSchema.setInsuAccBala(tInsuAccBala[nIndex]);
       //�ֶν��ã���Ž�ֹ����ʱ����Ϣ
       tLCInsureAccSchema.setLastAccBala(tInsuAccLX[nIndex]);
       tLCInsureAccSchema.setInsuAccGetMoney(tSumInsuAccBala[nIndex]);
       tLCInsureAccSet.add(tLCInsureAccSchema);
      }  

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
//            tEdorDetailUI.submitData(tVData, transact);
            tBusinessDelegate.submitData(tVData, transact,busiName);
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