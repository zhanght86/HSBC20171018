<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�PubAmntSave.jsp
//�����ܣ����������
//�������ڣ�2005-10-10 20:05:52
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
//������Ϣ������У�鴦��
//�������
//LCContSchema tLCContSchema = new LCContSchema();
LCPolSchema tLCPolSchema = new LCPolSchema();
LCDutySet tLCDutySet = new LCDutySet();
LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
LCInsuredSet tLCInsuredSet = new LCInsuredSet();
LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
TransferData tTransferData = new TransferData(); 
//ParseGuideIn tParseGuideIn   = new ParseGuideIn();
String busiName="tbParseGuideIn";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//�������
String FlagStr = "";
String Content = "";

GlobalInput tGI = new GlobalInput(); //repair:
tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
loggerDebug("PubAmntSave","tGI"+tGI);
if(tGI==null)
{
    loggerDebug("PubAmntSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
}
else //ҳ����Ч
{
    CErrors tError = null;
    String tBmCert = "";
    loggerDebug("PubAmntSave","��ʼ����");

    /*        
      String tLimit="";
      String CustomerNo="";
    */   
    int lineCount = 0;
    String arrCount[] = request.getParameterValues("PubAmntGridNo");
    if (arrCount != null)
    {
    	String tRadio[] = request.getParameterValues("InpPubAmntGridSel");	//Radioѡ��       
        String tRiskCode[] = request.getParameterValues("PubAmntGrid6");  //���ֱ���   
        String tPubAmntName[] = request.getParameterValues("PubAmntGrid3"); //������������
        String tDutyCode[] = request.getParameterValues("PubAmntGrid7"); //���α���
        String tAmnt[] = request.getParameterValues("PubAmntGrid4");  //����
        String tPrem[] = request.getParameterValues("PubAmntGrid5");  //����
                
        lineCount = arrCount.length; //����

    	//ѡ��ѡ��ѡ�е������ύ
    	for(int i=0;i<lineCount;i++)
    	{
    		if(tRadio[i].equals("1"))
    		{
    		    loggerDebug("PubAmntSave","���ֱ��� "+tRiskCode[i]);
                loggerDebug("PubAmntSave","������������ "+tPubAmntName[i]);
                loggerDebug("PubAmntSave","���α��� "+tDutyCode[i]);
                loggerDebug("PubAmntSave","���� "+tPrem[i]);
                loggerDebug("PubAmntSave","���� "+tAmnt[i]);
                        
    		    tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
                tLCGrpContSchema.setProposalGrpContNo(request.getParameter("GrpContNo"));
                loggerDebug("PubAmntSave","����Ͷ���� ====" +tLCGrpContSchema.getProposalGrpContNo());
    
                tmLCInsuredSchema.setInsuredNo("");
                tmLCInsuredSchema.setName(tPubAmntName[i]); //������������
                tmLCInsuredSchema.setRelationToMainInsured("00");
                tmLCInsuredSchema.setContNo("1");
                tmLCInsuredSchema.setPrtNo("1");
                tmLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
                //tmLCInsuredSchema.setContPlanCode(request.getParameter("ContPlanCode"));
                //tmLCInsuredSchema.setExecuteCom(request.getParameter("ExecuteCom"));
                tmLCInsuredSchema.setInsuredPeoples(0);
                tmLCInsuredSchema.setCreditGrade("2"); //���� ���������õȼ� �ֶα��� �������ͱ��
                tmLCInsuredSchema.setSex("0");
                tmLCInsuredSchema.setBirthday("1975-01-01");
    
                loggerDebug("PubAmntSave","tmLCInsuredSchema.getPrtNo()===="+tmLCInsuredSchema.getPrtNo());
    
                tLCInsuredSet.add(tmLCInsuredSchema);
    
                tLCPolSchema.setInsuredNo("1");
                tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
                tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
                tLCPolSchema.setInsuredPeoples(0); //����������
                tLCPolSchema.setPolTypeFlag("2"); //�������ͱ��
                tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
                tLCPolSchema.setMult(1);                       //����
                tLCPolSchema.setPrem(tPrem[i]);                 //����
                tLCPolSchema.setAmnt(tAmnt[i]);                    //����
                tLCPolSchema.setRiskCode(tRiskCode[i]);
                tLCPolSchema.setMainPolNo("1");
                tLCPolSchema.setContNo("1");
                 
                //LCDutySchema tLCDutySchema = new LCDutySchema(); //���ж�����������δ��������õġ������жϣ���ʱû���жϡ�
                //tLCDutySchema.setDutyCode(tDutyCode[i]);
                //tLCDutySet.add(tLCDutySchema);
                //loggerDebug("PubAmntSave","tLCDutySchema.getDutyCode()  "+tLCDutySchema.getDutyCode());
            }
        }
    }
		
    LCBnfSet tLCBnfSet = new LCBnfSet();
    LCBnfSchema tLCBnfSchema = new LCBnfSchema();
    tLCBnfSchema.setRelationToInsured("00");
    tLCBnfSet.add(tLCBnfSchema);
     
    LCInsuredRelatedSet tLCInsuredRelatedSet=new LCInsuredRelatedSet();
    
    tTransferData.setNameAndValue("SavePolType","0"); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ  
    //tTransferData.setNameAndValue("FamilyType","0"); //��ͥ����� 
    tTransferData.setNameAndValue("ContType","2"); //�ŵ������˵����
    tTransferData.setNameAndValue("PolTypeFlag","2"); //���������
    tTransferData.setNameAndValue("InsuredPeoples",0); //������������
    tTransferData.setNameAndValue("PubAmntFlag","Y"); //����������
    loggerDebug("PubAmntSave","------PubAmntFlag-----"+tTransferData.getValueByName("PubAmntFlag"));

    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add(tTransferData);
        tVData.add(tGI);
        tVData.add(tLCGrpContSchema);
        tVData.add(tLCInsuredSet);
        tVData.add(tLCPolSchema);
        tVData.add(tLCBnfSet);
        tVData.add(tLCDutySet);
        tVData.addElement(tLCInsuredRelatedSet);
          
         //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
        //if ( tParseGuideIn.submitData2(tVData,""))
        if ( tBusinessDelegate.submitData(tVData,"",busiName))
        {
	    	    loggerDebug("PubAmntSave","Start to save PubAmnt **************");
	    	    tVData.clear();
	    	    tVData = tBusinessDelegate.getResult();
        }
	}
		            
    catch(Exception ex)
    {
        Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
  

    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr=="")
    {
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
    loggerDebug("PubAmntSave","FlagStr:"+FlagStr+"Content:"+Content);
  
} //ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterAmntSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

