<%
//�������ƣ�ProposalSpotCheckSave.jsp
//�����ܣ����˳�������
//�������ڣ�2007-07-26 
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.service.*" %>

<%
  loggerDebug("ProposalSpotCheckSave","��ʼִ��Saveҳ��");
  //����ȫ�ֱ�������������Ͳ���Ա
  String Operator="";
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
  //���˳���㷨��BPOCheckCalMode
  BPOCheckCalModeSchema mBPOCheckCalModeSchema = new BPOCheckCalModeSchema();
  BPOCheckCalModeSet mBPOCheckCalModeSet = new BPOCheckCalModeSet();

  //ProposalSpotCheckUI mProposalSpotCheckUI = new ProposalSpotCheckUI();
  String busiName="tbProposalSpotCheckUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�����������
  CErrors tError = null;
  loggerDebug("ProposalSpotCheckSave","��ʼ���л�ȡ���ݵĲ���������");
  String mOperateType = request.getParameter("OperateType");
  //���������ǲ��룬��ѯ������
  loggerDebug("ProposalSpotCheckSave","������������"+mOperateType);
  loggerDebug("ProposalSpotCheckSave","ҳ��¼��������������"+request.getParameter("BussNo"));
  loggerDebug("ProposalSpotCheckSave","ҳ��¼������ֱ�����"+request.getParameter("RiskCode"));
  loggerDebug("ProposalSpotCheckSave","ҳ��¼��ĳ�������"+request.getParameter("checkRate"));
  loggerDebug("ProposalSpotCheckSave","ҳ��¼��ĳ��������"+request.getParameter("checkMax"));
  loggerDebug("ProposalSpotCheckSave","ҳ��¼��Ĺ��������"+request.getParameter("ManageCom"));
  loggerDebug("ProposalSpotCheckSave","ҳ��¼��ı�ע��"+request.getParameter("Remark"));
  
  
  //��BPOCheckCalModeSchema��ʵ����ֵ,�ѱ���BL���л����Щ����,������Щ������ѯ���ݿ⣬����һЩУ���жϣ��Ƿ���Խ��в���Ȳ���
  mBPOCheckCalModeSchema.setBPOID(request.getParameter("BussNo"));
  mBPOCheckCalModeSchema.setBussNoType("TB");
  mBPOCheckCalModeSchema.setRiskCode(request.getParameter("RiskCode"));
  mBPOCheckCalModeSchema.setRate(request.getParameter("checkRate"));
  mBPOCheckCalModeSchema.setMaxMum(request.getParameter("checkMax"));
  mBPOCheckCalModeSchema.setRemark(request.getParameter("Remark"));
  mBPOCheckCalModeSchema.setManageCom(request.getParameter("ManageCom"));

  if(mOperateType.equals("INSERT"))
  {
      mDescType = "����";
  }
  if(mOperateType.equals("UPDATE"))
  {
      mDescType = "�޸�";
  } 
  if(mOperateType.equals("QUERY"))
  {
      mDescType = "��ѯ";
  }

  //��Schema����Set��
  mBPOCheckCalModeSet.add(mBPOCheckCalModeSchema);
  VData tVData = new VData();
  
  //�������ύ����̨UI,������VData�Ͳ�������
  try
  {
    //���������ͣ��������������Ա��ӵ�������
    tVData.addElement(mOperateType);
    tVData.addElement(mBPOCheckCalModeSet);
   
    tBusinessDelegate.submitData(tVData,mOperateType,busiName);
  }
  catch(Exception ex)
  {
    Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
      Content = "����ɹ�";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = mDescType+" ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
