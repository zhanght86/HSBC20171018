<%
//�������ƣ�PEdorTypeNSSubmit.jsp
//�����ܣ�
//�������ڣ�2005-07-20 16:49:22
//������  ��lizhuo
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.db.LCContDB"%>
<%@page import="com.sinosoft.lis.db.LPPolDB"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.tb.ProductSaleControlBL"%>
  <%@page import="com.sinosoft.service.*" %> 
<%
    //����������Ϣ
    //System.out.println("---NS submit---");

    //����Ҫִ�еĶ�������ӣ��޸�
    String FlagStr = "";
    String Content = "";
    CErrors tErrors = new CErrors();

    //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    String transact = request.getParameter("fmtransact");
    String EdorNo = request.getParameter("EdorNo");
    String EdorType = request.getParameter("EdorType");
    String PolNo = request.getParameter("PolNo");
    String InsuredNo = request.getParameter("InsuredNo");
    String NewAddType = request.getParameter("NewAddType");
    String NewCvaliDate = request.getParameter("NewCvaliDate");
    //System.out.println("EdorNo: " + EdorNo);

    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    tLPEdorItemSchema.setPolNo(PolNo);
    //�����������
    tLPEdorItemSchema.setStandbyFlag1(NewAddType);
    tLPEdorItemSchema.setStandbyFlag2(NewCvaliDate);
    //if(PolNo.equals("") || PolNo == null){
    //  tLPEdorItemSchema.setPolNo("000000");
    //}
      tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
      tLPEdorItemSchema.setInsuredNo(InsuredNo);
      if(InsuredNo.equals("") || InsuredNo == null){
        tLPEdorItemSchema.setInsuredNo("000000");
      }
      tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));

      tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
      tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));

      String tImpartNum[] = request.getParameterValues("ImpartGridNo");
      String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //��֪���
      String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //��֪����
      String tImpartContent[] = request.getParameterValues("ImpartGrid3");        //��֪����
      String tImpartParamModle[] = request.getParameterValues("ImpartGrid4");        //��д����
    int ImpartCount = 0;
      
  //add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno ��������Լ�÷�
	LCContDB tLCContDB = new LCContDB();
	tLCContDB.setContNo(request.getParameter("ContNo"));
	tLCContDB.getInfo();
	//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno ��������Լ�÷�
	
    LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
    LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
      if (tImpartNum != null) ImpartCount = tImpartNum.length;
      System.out.println(ImpartCount);
      if(ImpartCount != 0){
         for (int i = 0; i < ImpartCount; i++)  {
            if(tImpartCode[i] == null || tImpartCode[i].equals("") || tImpartContent[i] == null || tImpartContent[i].equals("")
            || tImpartParamModle[i] == null ||tImpartParamModle[i].equals("")
            || tImpartVer[i] == null || tImpartVer[i].equals("")){
            }
            else{
                tLCCustomerImpartSchema = new LCCustomerImpartSchema();
              //tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
              tLCCustomerImpartSchema.setProposalContNo(tLCContDB.getPrtNo());
              tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
              //modify by jiaqiangli 2009-04-13
                      tLCCustomerImpartSchema.setCustomerNo(request.getParameter("InsuredNo"));
                      tLCCustomerImpartSchema.setCustomerNoType("1");
                      tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
                      tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
                      tLCCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
                      tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
                      tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
                }
           }
       //System.out.println("end set schema ��֪��Ϣ..."+ImpartCount);
    }

    //System.out.println("tLPEdorItemSchema ================>" + tLPEdorItemSchema.encode());


    LCPolSet tLCPolSet = new LCPolSet();
    if (transact.equals("DELETE"))
    {
      String tChk[] = request.getParameterValues("InpNewPolGridSel");
      String tPolNo[] = request.getParameterValues("NewPolGrid8");
      for(int index=0;index<tChk.length;index++)
      {
          if(tChk[index].equals("1"))
          {
             //System.out.println("��ѡ�е����ֺ�"+tPolNo[index]);
             LCPolSchema tLCPolSchema = new LCPolSchema();
             tLCPolSchema.setPolNo(tPolNo[index]);
             tLCPolSet.add(tLCPolSchema);
          }
          //if(tChk[index].equals("0"))
          //System.out.println("δ��ѡ�е����ֺ�"+tPolNo[index]);
      }
      //System.out.println("tLCPolSet ===============>" + tLCPolSet.encode());
    }

    VData tVData = new VData();
    tVData.addElement(tGlobalInput);
    tVData.addElement(tLPEdorItemSchema);
    tVData.addElement(tLCCustomerImpartSet);
    tVData.add(tLCPolSet);

    if (transact.equals("DELETE"))
    {
//        PEdorNSDetailBLF tPEdorNSDetailBLF = new PEdorNSDetailBLF();
        String busiNameNS="tPEdorNSDetailBLF";
	 	BusinessDelegate tNSBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//        if (!tPEdorNSDetailBLF.submitData(tVData, transact))
        if (!tNSBusinessDelegate.submitData(tVData, transact, busiNameNS))
        {
//            tErrors.copyAllErrors(tPEdorNSDetailBLF.mErrors);
            tErrors.copyAllErrors(tNSBusinessDelegate.getCErrors());
        }
//        tPEdorNSDetailBLF = null;
        tNSBusinessDelegate = null;
    }
    else
    {
//        EdorDetailUI tEdorDetailUI = new EdorDetailUI();
        String busiName="EdorDetailUI";
	 	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//        if (!tEdorDetailUI.submitData(tVData, transact))
        if (!tBusinessDelegate.submitData(tVData, transact, busiName))
        {
//            tErrors.copyAllErrors(tEdorDetailUI.mErrors);
            tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
        }
//        tEdorDetailUI = null;
        tBusinessDelegate = null;
    }

    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (!tErrors.needDealError())
    {
        FlagStr = "Success";
        if (transact.equals("DELETE"))
        {
           Content = " ɾ���ɹ���";
        }
        else
        {
        	//add by jiaqiangli 2009-03-12 ����ͣ�����ֵ���ʾ��Ϣ
    		VData tPolVData = new VData();
    		LCPolSet tStopSaleLCPolSet = new LCPolSet();
    		LCPolSchema tStopSaleLCPolSchema = new LCPolSchema();
    		LPPolSet tStopSaleLPPolSet = new LPPolSet();
    		LPPolDB tStopSaleLPPolDB = new LPPolDB();
    		tStopSaleLPPolDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
    		tStopSaleLPPolDB.setEdorType(tLPEdorItemSchema.getEdorType());
    		tStopSaleLPPolDB.setContNo(tLPEdorItemSchema.getContNo());
    		tStopSaleLPPolSet = tStopSaleLPPolDB.query();
    		tStopSaleLCPolSet.add(tStopSaleLCPolSchema);
    		Reflections tRef = new Reflections();
    		tRef.transFields(tStopSaleLCPolSet,tStopSaleLPPolSet);
    		tPolVData.add(tStopSaleLCPolSet);
//    		ProductSaleControlBL tProductSaleControlBL = new ProductSaleControlBL();
    		String busiName="tProductSaleControlBL";
	 		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    		int tCount = 0;
//    		tProductSaleControlBL.submitData(tPolVData, "");
    		tBusinessDelegate.submitData(tPolVData, "", busiName);
//    		tCount = tProductSaleControlBL.getResult().size();
    		tCount = tBusinessDelegate.getResult().size();
    		if (tCount > 0)  {
    			Content = " ����ɹ���"+ "������������Ϊͣ�۵�����!";
    		}
    		//add by jiaqiangli 2009-03-12 ����ͣ�����ֵ���ʾ��Ϣ
    		else
    			Content = " ����ɹ���";
        }
    }
    else
    {
        FlagStr = "Fail";
        if (transact.equals("DELETE"))
        {
           Content = " ɾ��ʧ�ܣ�ԭ����:" + tErrors.getFirstError();
        }
        else
        {
           Content = " ����ʧ�ܣ�ԭ����:" + tErrors.getFirstError();
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
