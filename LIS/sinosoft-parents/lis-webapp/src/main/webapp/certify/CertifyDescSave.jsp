
<%
//�������ƣ�CertifyDescSave.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("CertifyDescSave","��ʼִ��Saveҳ��");
  LMCertifyDesSchema mLMCertifyDesSchema = new LMCertifyDesSchema();
  LMCertifyDesSet mLMCertifyDesSet = new LMCertifyDesSet();
  //LMCardRiskSet mLMCardRiskSet = new LMCardRiskSet();
  //CertifyDescUI mCertifyDescUI = new CertifyDescUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  loggerDebug("CertifyDescSave","������������ "+mOperateType);
  
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  String mCertifyClass = request.getParameter("CertifyClass"); //��¼��֤������
  
  loggerDebug("CertifyDescSave","��ʼ���л�ȡ���ݵĲ���������");
  mLMCertifyDesSchema.setCertifyCode(request.getParameter("CertifyCode"));
  mLMCertifyDesSchema.setCertifyName(request.getParameter("CertifyName"));
  mLMCertifyDesSchema.setCertifyClass(request.getParameter("CertifyClass"));
  mLMCertifyDesSchema.setCertifyClass2(request.getParameter("CertifyClass2"));
  mLMCertifyDesSchema.setState(request.getParameter("State"));
  //mLMCertifyDesSchema.setHavePrice(request.getParameter("HavePrice"));
  mLMCertifyDesSchema.setHaveNumber(request.getParameter("HaveNumber"));
  mLMCertifyDesSchema.setCertifyLength(request.getParameter("CertifyLength"));  
  mLMCertifyDesSchema.setTackBackFlag(request.getParameter("TackBackFlag"));
  mLMCertifyDesSchema.setCertifyPrice(request.getParameter("CertifyPrice"));  
  mLMCertifyDesSchema.setUnit(request.getParameter("Unit"));  
  mLMCertifyDesSchema.setMaxPrintNo(request.getParameter("MaxPrintNo"));
  mLMCertifyDesSchema.setHaveLimit(request.getParameter("HaveLimit"));
  mLMCertifyDesSchema.setMaxUnit1(request.getParameter("MaxUnit1"));
  mLMCertifyDesSchema.setMaxUnit2(request.getParameter("MaxUnit2"));
  mLMCertifyDesSchema.setHaveValidate(request.getParameter("HaveValidate"));
  mLMCertifyDesSchema.setValidate1(request.getParameter("Validate1"));
  mLMCertifyDesSchema.setValidate2(request.getParameter("Validate2"));
  mLMCertifyDesSchema.setNote(request.getParameter("Note"));
  //mLMCertifyDesSchema.setState(request.getParameter("State"));
  //mLMCertifyDesSchema.setManageCom(request.getParameter("ManageCom"));  
  mLMCertifyDesSet.add(mLMCertifyDesSchema);
   
  /****************************************************************************
   * �жϵ�֤�����ͣ����Ƕ��֤��Ҫ�Զ��������Ϣ���������
   ***************************************************************************/
  /**if(mCertifyClass.equals("D"))
  {
    String[] strNumber = request.getParameterValues("CardRiskGridNo");
    String[] strRiskCode = request.getParameterValues("CardRiskGrid1");
    String[] strPrem = request.getParameterValues("CardRiskGrid2");
    String[] strPremProp = request.getParameterValues("CardRiskGrid3");
    String[] strPremLot = request.getParameterValues("CardRiskGrid4");
    int tLength = strNumber.length;
    if(strNumber!=null)
    {
      for(int i = 0 ;i < tLength ;i++)
      {
        LMCardRiskSchema tLMCardRiskSchema = new LMCardRiskSchema();
        tLMCardRiskSchema.setCertifyCode(request.getParameter("CertifyCode"));
        tLMCardRiskSchema.setRiskCode(strRiskCode[i]);
        tLMCardRiskSchema.setPrem(strPrem[i]);
        tLMCardRiskSchema.setPremProp(strPremProp[i]);
        tLMCardRiskSchema.setPremLot(String.valueOf(strPremLot[i]));
        mLMCardRiskSet.add(tLMCardRiskSchema);
      }
    }
  }**/

  if(mOperateType.equals("INSERT"))
  {
    mDescType = "����";
  }
  if(mOperateType.equals("UPDATE"))
  {
    mDescType = "�޸�";
    mLMCertifyDesSchema.setRiskVersion(request.getParameter("CertifyCode_1"));
    loggerDebug("CertifyDescSave","�޸�ʱ��У�鵥֤������"+mLMCertifyDesSchema.getRiskVersion());
  }
  if(mOperateType.equals("DELETE"))
  {
    mDescType = "ɾ��";
  }

  VData tVData = new VData();
    tVData.addElement(mCertifyClass);
    tVData.addElement(tG);
    tVData.addElement(mLMCertifyDesSet);
    //if(mCertifyClass.equals("D"))
    //{
    //  tVData.addElement(mLMCardRiskSet);
    //}
    //mCertifyDescUI.submitData(tVData,mOperateType);
  String busiName="CertifyDescUI";
  if(!tBusinessDelegate.submitData(tVData,mOperateType,busiName))
  {    
       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
       { 
			Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
	   }
	   else
	   {
			Content = mDescType+"ʧ��";
			FlagStr = "Fail";				
	   }
  }
  else
  {
     	Content = mDescType+"�ɹ�! ";
      	FlagStr = "Succ";  
  }

%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
