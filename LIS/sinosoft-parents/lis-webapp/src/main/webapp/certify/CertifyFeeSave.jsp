<%
//�������ƣ�CertifyFeeSave.jsp
//�����ܣ�
//�������ڣ�2007-01-15 
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
 <%@page import="com.sinosoft.lis.certify.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%
  loggerDebug("CertifyFeeSave","��ʼִ��Saveҳ��");
  //����ȫ�ֱ�������������Ͳ���Ա
  String Operator="";
  String tManageCom="";
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
  //��֤�շѶ����LZCardFee
  LZCardFeeSchema mLZCardFeeSchema = new LZCardFeeSchema();
  LZCardFeeSet mLZCardFeeSet = new LZCardFeeSet();

  CertifyFeeUI mCertifyFeeUI = new CertifyFeeUI();
  //�����������
  CErrors tError = null;
  
  loggerDebug("CertifyFeeSave","��ʼ���л�ȡ���ݵĲ���������");
  String mOperateType = request.getParameter("OperateType");
  //���������ǲ��룬��ѯ������
  loggerDebug("CertifyFeeSave","������������"+mOperateType);
  loggerDebug("CertifyFeeSave","ҳ��¼��ĵ�֤������"+request.getParameter("CertifyCode"));
  loggerDebug("CertifyFeeSave","ҳ��¼��ĵ�֤������"+request.getParameter("CertifyName"));
  loggerDebug("CertifyFeeSave","ҳ��¼��ĵ�֤��λ��"+request.getParameter("Price"));
  loggerDebug("CertifyFeeSave","ҳ��¼��ĵ�֤������"+request.getParameter("Unit"));
  loggerDebug("CertifyFeeSave","��½������"+request.getParameter("ManageCom"));
  
  
  //��LZCardFeeSchema��ʵ����ֵ,�ѱ���BL���л����Щ����,������Щ������ѯ���ݿ⣬����һЩУ���жϣ��Ƿ���Խ��в���Ȳ���
  mLZCardFeeSchema.setCertifyCode(request.getParameter("CertifyCode"));
  mLZCardFeeSchema.setCertifyName(request.getParameter("CertifyName"));
  mLZCardFeeSchema.setPrice(request.getParameter("Price"));
  mLZCardFeeSchema.setUnit(request.getParameter("Unit"));
  mLZCardFeeSchema.setManageCom(request.getParameter("ManageCom"));
  
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
      loggerDebug("CertifyFeeSave","����Ա��"+Operator);
      tManageCom=tG.ManageCom;
      loggerDebug("CertifyFeeSave","���������"+tManageCom);
  }
 

  if(mOperateType.equals("INSERT"))
  {
      mDescType = "����";
  }
  if(mOperateType.equals("UPDATE"))
  {
      mDescType = "�޸�";
  } 
  if(mOperateType.equals("DELETE"))
  {
      mDescType = "ɾ��";
  }
  if(mOperateType.equals("QUERY"))
  {
      mDescType = "��ѯ";
  }

  //��Schema����Set��
  mLZCardFeeSet.add(mLZCardFeeSchema);
  VData tVData = new VData();
  
  //�������ύ����̨UI,������VData�Ͳ�������
  try
  {
    //���������ͣ��������������Ա��ӵ�������
    tVData.addElement(mOperateType);
    tVData.addElement(Operator);
    tVData.addElement(tManageCom);
    tVData.addElement(mLZCardFeeSet);
   
	mCertifyFeeUI.submitData(tVData,mOperateType);
  }
  catch(Exception ex)
  {
    Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = mCertifyFeeUI.mErrors;
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
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
