<%
//�������ƣ�PEdorTypeWTSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
  //�������
  //����������Ϣ
  
  
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
	if(!transact.equals("EDORITEM|UPDATE"))
	{
		transact = "EDORITEM|INPUT";
	}
	String edorAcceptNo = request.getParameter("EdorAcceptNo");
	String edorNo = request.getParameter("EdorNo");
	String edorType = request.getParameter("EdorType");
	String contNo = request.getParameter("ContNo");
	String sEdorReasonCode = request.getParameter("SurrReason");
	String sEdorReason = request.getParameter("Remark");
	String sRelationToAppnt = request.getParameter("RelationToAppnt");
	String sWTContFLag=request.getParameter("WTContFLag");

	System.out.println("HHHHHHHHHHH"+sEdorReason);
    String tPolNo[]= request.getParameterValues("PolGrid9");
    String tChk[] = request.getParameterValues("InpPolGridChk");
    for (int i=0; i<tChk.length; i++)
    {
        System.out.println("$$$$$$$$$$$44"+tChk[i]);
        if (tChk[i].equals("1"))
        {
            LPPolSchema tLPPolSchema=new LPPolSchema();
            tLPPolSchema.setPolNo(tPolNo[i]);
            System.out.println("++++++++++++++++"+tPolNo[i]);
            mLPPolSet.add(tLPPolSchema);
        }
    }
    if (mLPPolSet == null || mLPPolSet.size() < 1)
    {
	      Content = "��ѡ��Ҫ�˱��ı���!";
	      FlagStr = "Fail";
    }            

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
  tLPEdorItemSchema.setStandbyFlag3(sWTContFLag); //�����ж���ѡ�������˱�ʱ�����ѡ�����գ���Ҫ�󽫸�����һ��ѡ��
		
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
//	EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	try
	{		   
		tVData.add(tGlobalInput);
		tVData.add(tLPEdorItemSchema);
		tVData.add(mLPPolSet);
		tVData.add(tLPBnfSet);
		//tEdorDetailUI.submitData(tVData, "EDORITEM|INPUT");
//		tEdorDetailUI.submitData(tVData, transact);
		tBusinessDelegate.submitData(tVData, transact,busiName);
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
//		    tError = tEdorDetailUI.mErrors;
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

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

