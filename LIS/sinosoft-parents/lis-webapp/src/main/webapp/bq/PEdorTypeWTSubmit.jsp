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
	String edorAcceptNo = request.getParameter("EdorAcceptNo");
	String edorNo = request.getParameter("EdorNo");
	String edorType = request.getParameter("EdorType");
	String contNo = request.getParameter("ContNo");
	String sEdorReasonCode = request.getParameter("SurrReason");
	String sEdorReason = request.getParameter("Remark");
	String sRelationToAppnt = request.getParameter("RelationToAppnt");
	String sFeeGetFlag=request.getParameter("FeeGetFlag"); //����Ƿ�۳������ѵı��
	 //��ԥ���˱���־��ȷ���������˻���ֻ�˸����գ�0,ֻ�˸����գ�1 ��ȫ��
	String sWTContFLag=request.getParameter("WTContFLag");
	System.out.println("��۳������ѵı��(0,���۳���1���۳�);"+sFeeGetFlag);
	
	System.out.println("��ԥ���˱���־��ȷ���������˻���ֻ�˸�����(0,ֻ�˸����գ�1 ��ȫ��);"+sWTContFLag);

		System.out.println("�˱�ԭ��;"+sEdorReason);
    String tPolNo[]= request.getParameterValues("PolGrid8");
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
	tLPEdorItemSchema.setStandbyFlag1(sFeeGetFlag); //�Ƿ�۳������ѣ�Ĭ�Ͽ۳� Add By PST
  tLPEdorItemSchema.setStandbyFlag3(sWTContFLag); //�����ж���ѡ�������˱�ʱ�����ѡ�����գ���Ҫ�󽫸�����һ��ѡ��
	

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
//		tEdorDetailUI.submitData(tVData, "");
		tBusinessDelegate.submitData(tVData, "" ,busiName);
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

