<%
//�������ƣ�PEdorTypeBMSubmit.jsp
//�����ܣ�
//�������ڣ�2008-07-23
//������  ��pst
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
  //�����ඨ��
  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
  LPPolSchema tLPPolSchema = null;
  LPPolSet tLPPolSet = new LPPolSet();

//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput = (GlobalInput)session.getValue("GI");

  System.out.println("-----" + tGlobalInput.Operator);
  //�������
  CErrors tError = null;
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��

  String FlagStr = "";
  String Content = "";
  VData Result = new VData();
  String transact = "";

  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");

  //����������Ϣ
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setInsuredNo("000000");
  tLPEdorItemSchema.setPolNo("000000");
  tLPEdorItemSchema.setOperator(tGlobalInput.Operator);

	//����û�ѡ�����ȡ��Ϣ�������ڱ�LJAGetDrawɾ����Щ��Ϣ��Ӧ�ļ�¼
	String tGridNo[] = request.getParameterValues("PolGridNo");
	String tChk[] = request.getParameterValues("InpPolGridChk");
	String tPolNo[] =request.getParameterValues("PolGrid3");
	String tBonusGetMode[] = request.getParameterValues("PolGrid7");
		
	int iCount = tGridNo.length;
	for (int i=0;i<iCount;i++)
	{ 	
		if (tChk[i].equals("1"))
		{
			tLPPolSchema = new LPPolSchema();
			tLPPolSchema.setPolNo(tPolNo[i]);
			tLPPolSchema.setBonusGetMode(tBonusGetMode[i]);
			System.out.println("�µĺ�����ȡ��ʽ"+tLPPolSchema.getBonusGetMode());
			tLPPolSet.add(tLPPolSchema);
		}
	}

  try
  {
      // ׼���������� VData
      VData tVData = new VData();

      //������Ϣ(��ȫ)
      tVData.addElement(tGlobalInput);
      tVData.addElement(tLPEdorItemSchema);
      tVData.addElement(tLPPolSet);

//      boolean tag = tEdorDetailUI.submitData(tVData,"");
      boolean tag = tBusinessDelegate.submitData(tVData,"",busiName);
      if (tag)
      {
          System.out.println("Successful");
      }
      else
      {
          System.out.println("Fail");
          FlagStr = "Fail";         //ʧ��
      }
  }
  catch (Exception ex)
  {
      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
      FlagStr = "Fail";
  }
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
//  tError = tEdorDetailUI.mErrors;
  tError = tBusinessDelegate.getCErrors();
  if (!tError.needDealError())
      {
          Content = " ����ɹ���";
          FlagStr = "Success";
      }
      else
      {
          Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
          FlagStr = "Fail";
      }
  System.out.println("OK!");

  //��Ӹ���Ԥ����

%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
</html>
