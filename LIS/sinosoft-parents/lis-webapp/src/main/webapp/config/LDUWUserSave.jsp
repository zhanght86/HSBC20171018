<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LDUWUserSave.jsp
//�����ܣ�
//�������ڣ�2005-01-24 18:15:01
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.config.*"%>
<%
  //������Ϣ������У�鴦��
  //�������
  LDUWUserSchema tLDUWUserSchema   = new LDUWUserSchema();
  LDUWUserSchema tLDUWUserSchema1   = new LDUWUserSchema();
  OLDUWUserUI tOLDUWUserUI   = new OLDUWUserUI();
  //�������
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
 // String tImpartNum[] = request.getParameterValues("LCUWReponsComGridNo");            //
 // String tManageCom[] = request.getParameterValues("LCUWReponsComGrid1");      // �������
 // String tValidStartDate[] = request.getParameterValues("LCUWReponsComGrid2"); //��Ч��ʼ���� 
 // String tValidEndDate[] = request.getParameterValues("LCUWReponsComGrid3");   //��Ч��������?
  
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
	
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
   tLDUWUserSchema.setUserCode(request.getParameter("UserCode"));
    tLDUWUserSchema.setUWType("2");
    tLDUWUserSchema.setUWPopedom(request.getParameter("UWPopedom"));
    tLDUWUserSchema.setUpUWPopedom(request.getParameter("UpUwPopedom"));
    tLDUWUserSchema.setEdorPopedom("1");
    tLDUWUserSchema.setClaimPopedom(request.getParameter("ClaimPopedom"));
    //tLDUWUserSchema.setAddPoint(request.getParameter("AddPoint"));
    //tLDUWUserSchema.setRemark(request.getParameter("Remark"));
    //tLDUWUserSchema.setOperator(request.getParameter("Operator"));
    //tLDUWUserSchema.setManageCom(request.getParameter("ManageCom"));
    //tLDUWUserSchema.setUWRate(request.getParameter("Rate"));
    //tLDUWUserSchema.setSpecJob(request.getParameter("SpecJob"));
    //tLDUWUserSchema.setLowestAmnt(request.getParameter("LowestAmnt"));
    //tLDUWUserSchema.setOverAge(request.getParameter("OverAge"));
      if(transact.equals("UPDATE||MAIN"))
    {
    	tLDUWUserSchema1.setUserCode(request.getParameter("UserCode1"));
    	tLDUWUserSchema1.setUWType(request.getParameter("UWType1"));
    	tLDUWUserSchema1.setUWPopedom(request.getParameter("UWPopedom1"));   
    }    
   // tLDUWGradeSchema.setUserCode(request.getParameter("UserCode"));
   // tLDUWGradeSchema.setUWType(request.getParameter("UWType"));
   // tLDUWGradeSchema.setUWPopedom(request.getParameter("UWPopedom"));

   // tLDUWAmntGradeSchema.setUserCode(request.getParameter("UserCode"));
   // tLDUWAmntGradeSchema.setUWType(request.getParameter("UWType"));
   // tLDUWAmntGradeSchema.setUWPopedom(request.getParameter("UWPopedom"));
    
    //�˱�����    UserCode UserName UWGrade UWType UWRate

      LDUWGradeSet tLDUWGradeSet = new LDUWGradeSet();  
   
	String tUWState[] = request.getParameterValues("UWResultGrid1");    
	String tUWType[] = request.getParameterValues("UWResultGrid2");	
      String tResultCount[] = request.getParameterValues("UWResultGridNo");	
    
	int ResultCount = 0;
	if (tResultCount != null) ResultCount = tResultCount.length;
	for (int i = 0; i < ResultCount; i++)	
	{
                LDUWGradeSchema tLDUWGradeSchema   = new LDUWGradeSchema();
                tLDUWGradeSchema.setUserCode(request.getParameter("UserCode"));
                tLDUWGradeSchema.setUWType(request.getParameter("UWType"));
                tLDUWGradeSchema.setUWPopedom(request.getParameter("UWPopedom"));
                tLDUWGradeSchema.setUWPopedomName(request.getParameter("UWPopedomName"));
		tLDUWGradeSchema.setUWState(tUWState[i]);
		tLDUWGradeSchema.setUWStateName(tUWType[i]);
		tLDUWGradeSet.add(tLDUWGradeSchema);
		
	}
	loggerDebug("LDUWUserSave","ResultCount:"+ResultCount);
    	loggerDebug("LDUWUserSave","UWPopedomName: " + request.getParameter("UWPopedomName"));
    //�˱���������
      LDUWAmntGradeSet tLDUWAmntGradeSet = new LDUWAmntGradeSet();
        String MaxAmntCount[] = request.getParameterValues("UWMaxAmountGridNo");
        String UWKind[]=request.getParameterValues("UWMaxAmountGrid1");
        String MaxAmnt[]=request.getParameterValues("UWMaxAmountGrid3");
	int Count = 0;
	if (MaxAmntCount != null) Count = MaxAmntCount.length;
	loggerDebug("LDUWUserSave","UserCode"+request.getParameter("UserCode"));
	for (int i = 0; i < Count; i++)	
	{
		
		 LDUWAmntGradeSchema tLDUWAmntGradeSchema = new LDUWAmntGradeSchema();
               tLDUWAmntGradeSchema.setUserCode(request.getParameter("UserCode"));
               tLDUWAmntGradeSchema.setUWType(request.getParameter("UWType"));
               tLDUWAmntGradeSchema.setUWPopedom(request.getParameter("UWPopedom"));
               tLDUWAmntGradeSchema.setUWPopedomName(request.getParameter("UWPopedomName"));
		tLDUWAmntGradeSchema.setUWKind(UWKind[i]);
		tLDUWAmntGradeSchema.setMaxAmnt(MaxAmnt[i]);
                tLDUWAmntGradeSet.add(tLDUWAmntGradeSchema);
	}
	loggerDebug("LDUWUserSave","Count:"+Count);
    
  try
  {
  // ׼���������� VData
  	VData tVData = new VData();
	tVData.add(tLDUWUserSchema);
	tVData.add(tLDUWGradeSet);
	tVData.add(tLDUWAmntGradeSet);
	if(transact.equals("UPDATE||MAIN"))
	{
	  tVData.add(tLDUWUserSchema1);
	  loggerDebug("LDUWUserSave","UPDATE MAIN " + tLDUWUserSchema1.getUserCode());
	}
  	tVData.add(tG);
  	  loggerDebug("LDUWUserSave","������:"+transact);
    tOLDUWUserUI.submitData(tVData,transact);
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tOLDUWUserUI.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " ����ɹ�! ";
    	FlagStr = "Success";
    }
    else                                                                           
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
