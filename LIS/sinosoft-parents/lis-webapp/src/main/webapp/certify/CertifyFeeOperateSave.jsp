<%
 loggerDebug("CertifyFeeOperateSave","Start CertifyFeeOperateSave.jsp Submit...1");
//�������ƣ�CertifyFeeOperateSave.jsp
//�����ܣ�
//�������ڣ�2007-01-16 
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%

  //������Ϣ������У�鴦��
  //�������
  //�������
  

  String FlagStr = "";
  String Content = "";
  String ManageCom="";
  String Operator="";
  String tPayDate="";
  String mOperateType="";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  CErrors tError = null;
  
  loggerDebug("CertifyFeeOperateSave","��ʼ���л�ȡ���ݵĲ���������");
  mOperateType = request.getParameter("OperateType");
  ManageCom = request.getParameter("StationCode");
  
  //���������ǲ��룬��ѯ������
  loggerDebug("CertifyFeeOperateSave","������������"+mOperateType);
	loggerDebug("CertifyFeeOperateSave","ҳ��¼��ĵ�֤������"+request.getParameter("CertifyCode"));
  loggerDebug("CertifyFeeOperateSave","ҳ��¼��ĵ�֤������"+request.getParameter("CertifyName"));
  loggerDebug("CertifyFeeOperateSave","ҳ��¼��ĵ�֤������"+request.getParameter("Count"));
  loggerDebug("CertifyFeeOperateSave","ҳ��¼��Ľɷѽ����"+request.getParameter("PayMoney"));
  loggerDebug("CertifyFeeOperateSave","ҳ��¼��Ľɷ�ʱ����"+request.getParameter("PayDate"));
  loggerDebug("CertifyFeeOperateSave","ҳ��¼��Ĺ��������"+ManageCom);
 
  //���ȫ�ֱ���
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null) 
  {
			out.println("ȫ�ֱ���Ϊ��");
			return;
  }
  else
  {
	    Operator=tG.Operator;
	    loggerDebug("CertifyFeeOperateSave","����Ա��"+Operator);
  }
  

  //��֤������LZCardFeeOper
  LZCardFeeOperSchema mLZCardFeeOperSchema = new LZCardFeeOperSchema();
  LZCardFeeOperSet mLZCardFeeOperSet = new LZCardFeeOperSet();
  CertifyFeeOperateUI mCertifyFeeOperateUI = new CertifyFeeOperateUI();
  //��LZCardFeeSchema��ʵ����ֵ,�ѱ���BL���л����Щ����,������Щ������ѯ���ݿ⣬����һЩУ���жϣ��Ƿ���Խ��в���Ȳ���
  mLZCardFeeOperSchema.setCertifyCode(request.getParameter("CertifyCode"));
  mLZCardFeeOperSchema.setCertifyName(request.getParameter("CertifyName"));
  mLZCardFeeOperSchema.setCount(request.getParameter("Count"));
  mLZCardFeeOperSchema.setPayMoney(request.getParameter("PayMoney")); 
  mLZCardFeeOperSchema.setPayDate(request.getParameter("PayDate")); 
  
  //��Schema����Set��
  mLZCardFeeOperSet.add(mLZCardFeeOperSchema);
  VData tVData = new VData();
  
  if(mOperateType.equals("INSERT"))
  {
    mDescType = "����";
  }
  
  //�������ύ����̨UI,������VData�Ͳ�������
  try
  {
	    //���������ͣ��������������Ա��ӵ�������
	    tVData.addElement(mOperateType);
	    tVData.addElement(ManageCom);
	    tVData.addElement(Operator);
	    tVData.addElement(mLZCardFeeOperSet);
	   
	    mCertifyFeeOperateUI.submitData(tVData,mOperateType);
  }
  catch(Exception ex)
  {
    Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = mCertifyFeeOperateUI.mErrors;
    if (!tError.needDealError())
    {
      Content = mDescType+" �ɹ�";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = mDescType+" ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  loggerDebug("CertifyFeeOperateSave","Flag : " + FlagStr + " -- Content : " + Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

