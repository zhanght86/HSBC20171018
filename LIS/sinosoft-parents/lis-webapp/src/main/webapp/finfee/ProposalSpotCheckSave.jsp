<%
//�������ƣ�ProposalSpotCheckSave.jsp
//�����ܣ����˳�������
//�������ڣ�2008-06-30 
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%
  loggerDebug("ProposalSpotCheckSave","��ʼִ��Saveҳ��");
  //����ȫ�ֱ�������������Ͳ���Ա
  String Operator="";
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  String mContent = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
  //���˳���㷨��BPOCheckCalMode
  BPOCheckCalModeSchema mBPOCheckCalModeSchema = new BPOCheckCalModeSchema();
  BPOCheckCalModeSet mBPOCheckCalModeSet = new BPOCheckCalModeSet();

  ProposalSpotCheckUI mProposalSpotCheckUI = new ProposalSpotCheckUI();
  //�����������
  CErrors tError = null;
  loggerDebug("ProposalSpotCheckSave","��ʼ���л�ȡ���ݵĲ���������");
  String mOperateType = request.getParameter("OperateType");
  //���������ǲ��룬��ѯ������
  loggerDebug("ProposalSpotCheckSave","������������"+mOperateType);
  
  String tBussNo   = request.getParameter("BussNoa");
  String tManageCom   = request.getParameter("ManageComa");
  String tcheckRate  = request.getParameter("checkRatea");
  String tcheckMax   = request.getParameter("checkMaxa");
  
  loggerDebug("ProposalSpotCheckSave","ҳ��¼��������˾��"+tBussNo);
  loggerDebug("ProposalSpotCheckSave","ҳ��¼��ĳ�������"+tManageCom);
  loggerDebug("ProposalSpotCheckSave","ҳ��¼��ĳ��������"+tcheckRate);
  loggerDebug("ProposalSpotCheckSave","ҳ��¼��Ĺ��������"+tcheckMax);
  //loggerDebug("ProposalSpotCheckSave","ҳ��¼��ı�ע��"+request.getParameter("Remark"));
  
  //��BPOCheckCalModeSchema��ʵ����ֵ,�ѱ���BL���л����Щ����,������Щ������ѯ���ݿ⣬����һЩУ���жϣ��Ƿ���Խ��в���Ȳ���
  mBPOCheckCalModeSchema.setBussNoType("OF");
  mBPOCheckCalModeSchema.setBPOID(tBussNo);
  mBPOCheckCalModeSchema.setRate(tcheckRate);
  mBPOCheckCalModeSchema.setMaxMum(tcheckMax);
  mBPOCheckCalModeSchema.setManageCom(tManageCom);
  
  if(mOperateType.equals("1"))
  {
      mDescType = "INSERT";
      mContent = "����";
  }
  if(mOperateType.equals("2"))
  {
      mDescType = "UPDATE";
      mContent = "�޸�";
  }

  if(mOperateType.equals("3"))
  {
      mDescType = "DELETE";
      mContent = "ɾ��";
  }
  
  loggerDebug("ProposalSpotCheckSave","mDescType"+mDescType); 

  //��Schema����Set��
  mBPOCheckCalModeSet.add(mBPOCheckCalModeSchema);
  VData tVData = new VData();
  
  //�������ύ����̨UI,������VData�Ͳ�������
  try
  {
    //���������ͣ��������������Ա��ӵ�������
    tVData.addElement(mDescType);
    tVData.addElement(mBPOCheckCalModeSet);
   
	mProposalSpotCheckUI.submitData(tVData,mDescType);
  }
  catch(Exception ex)
  {
    Content = mContent+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = mProposalSpotCheckUI.mErrors;
    if (!tError.needDealError())
    {
      Content = mContent+"�ɹ�";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = mContent+"ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
