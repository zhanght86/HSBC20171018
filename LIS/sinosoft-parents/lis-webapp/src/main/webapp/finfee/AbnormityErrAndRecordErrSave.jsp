<%
//�������ƣ�AbnormityErrAndRecordErrSave.jsp
//�����ܣ��쳣������ԭ���ѯ�Լ���¼�����
//�������ڣ�2008-06-26
//������  ��ln
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


<%
  loggerDebug("AbnormityErrAndRecordErrSave","��ʼִ��Saveҳ��");
  //����ȫ�ֱ�������������Ͳ���Ա
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  
  //����¼��BPOErrLog
  BPOErrLogSet mBPOErrLogSet = new BPOErrLogSet();
  
  //�����������
  CErrors tError = null;
  loggerDebug("AbnormityErrAndRecordErrSave","��ʼ���л�ȡ���ݵĲ���������");
  String mOperateType = "INSERT";
  String rgtNo=request.getParameter("TempFeeNo");
  String type=request.getParameter("bussNoType");
  //���������ǲ��룬��ѯ������
  loggerDebug("AbnormityErrAndRecordErrSave","��֤ӡˢ����"+rgtNo);

  
  //��ȡmulitiline�е�ֵ
  String tImpartNum[] = request.getParameterValues("ErrGridNo");
  String terrorType[] = request.getParameterValues("ErrGrid1");            //������
  String terrorCode[] = request.getParameterValues("ErrGrid2");           //������
  String terrorContent[] = request.getParameterValues("ErrGrid3");        //�������

 
  //��BPOErrLogSchema��ʵ����ֵ,�ѱ���BL���л����Щ����,������Щ������ѯ���ݿ⣬����һЩУ���жϣ��Ƿ���Խ��в���Ȳ���
  int ImpartCount = 0;
  
  
  if (tImpartNum != null)
  {
       ImpartCount = tImpartNum.length;
       loggerDebug("AbnormityErrAndRecordErrSave","ImpartCount= "+ImpartCount);
       
      for (int i = 0; i < ImpartCount; i++)	
  	   {
  	      BPOErrLogSchema mBPOErrLogSchema = new BPOErrLogSchema();
  	      loggerDebug("AbnormityErrAndRecordErrSave","i="+i);
		  mBPOErrLogSchema.setBussNo(rgtNo);
		  loggerDebug("AbnormityErrAndRecordErrSave","��֤ӡˢ����"+rgtNo);
	      mBPOErrLogSchema.setErrCode(terrorCode[i]);
	      loggerDebug("AbnormityErrAndRecordErrSave","������terrorCode["+i+"]="+terrorCode[i]);
		  mBPOErrLogSchema.setBussNoType(type);
		  mBPOErrLogSchema.setErrorContent(terrorContent[i]) ;
		  loggerDebug("AbnormityErrAndRecordErrSave","�������terrorContent["+i+"]="+terrorContent[i]);
		  mBPOErrLogSchema.setErrVer(terrorType[i]);
		  loggerDebug("AbnormityErrAndRecordErrSave","������terrorType["+i+"]="+terrorType[i]);
		  mBPOErrLogSet.add(mBPOErrLogSchema);
  	   }
  }

	
  
  
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  

  AbnormityErrAndRecordErrUI mAbnormityErrAndRecordErrUI = new AbnormityErrAndRecordErrUI();

  VData tVData = new VData();
  
  //�������ύ����̨UI,������VData�Ͳ�������
  try
  {
    //���������ͣ��������������Ա��ӵ�������
    tVData.addElement(mOperateType);
    tVData.addElement(mBPOErrLogSet);
    tVData.addElement(tG);
   
	mAbnormityErrAndRecordErrUI.submitData(tVData,mOperateType);
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = mAbnormityErrAndRecordErrUI.mErrors;
    if (!tError.needDealError())
    {
      Content = "����ɹ�";
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
	parent.fraInterfacereason.afterSubmit("<%=FlagStr%>","<%=Content%>");
	//alert("<%=Content%>");
</script>
</html>
