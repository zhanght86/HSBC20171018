<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�PubAccSave.jsp
//�����ܣ�
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
    LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
    LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
    LCPolSchema tLCPolSchema = new LCPolSchema();
    LCPolDB tLCPolDB = new LCPolDB();
    LCDutySet tLCDutySet = new LCDutySet();
    LCInsuredSet tLCInsuredSet = new LCInsuredSet();
    LCBnfSet tLCBnfSet = new LCBnfSet();
    LCInsuredRelatedSet tLCInsuredRelatedSet=new LCInsuredRelatedSet();
    TransferData tTransferData = new TransferData(); 
    //ParseGuideIn tParseGuideIn   = new ParseGuideIn();
    String tAction = request.getParameter("mOperate");
    //ProposalUI tProposalUI = new ProposalUI();
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    LCDutySchema tLCDutySchema = new LCDutySchema();
    LCDutyDB tLCDutyDB = new LCDutyDB();
    LCBnfSchema tLCBnfSchema = new LCBnfSchema();
    LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();
    LCContSchema tLCContSchema = new LCContSchema();  
    LCAppntSchema tLCAppntSchema = new LCAppntSchema();    
    
    //�������
    String FlagStr = "";
    String Content = "";
    
    GlobalInput tGI = new GlobalInput(); //repair:
    tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
    loggerDebug("PubAccSave","tGI"+tGI);
    if(tGI==null)
    {
        loggerDebug("PubAccSave","ҳ��ʧЧ,�����µ�½");   
        FlagStr = "Fail";        
        Content = "ҳ��ʧЧ,�����µ�½";  
    }
    else //ҳ����Ч
    {
        CErrors tError = null;
        String tBmCert = "";
        loggerDebug("PubAccSave","��ʼ����");
    
        /*        
          String tLimit="";
          String CustomerNo="";
        */ 
        int lineCount = 0;
        String arrCount[] = request.getParameterValues("PubAccGridNo");
        if (arrCount != null)
        {
    	    String tRadio[] = request.getParameterValues("InpPubAccGridSel");	//Radioѡ��          
            String tRiskCode[] = request.getParameterValues("PubAccGrid5");  //���ֱ���   
            String tPubAccName[] = request.getParameterValues("PubAccGrid3"); //�����ʻ�����
            String tDutyCode[] = request.getParameterValues("PubAccGrid6"); //���α���
            String tPrem[] = request.getParameterValues("PubAccGrid4");  //����
            String tAmnt[] = request.getParameterValues("PubAccGrid11");  //����
            String tPubAccFlag[] = request.getParameterValues("PubAccGrid7"); //�Զ�Ӧ�ù����ʻ����
            String tPolNo[] = request.getParameterValues("PubAccGrid12"); 
            String tInsuredNo[] = request.getParameterValues("PubAccGrid8");
            String tContNo[] = request.getParameterValues("PubAccGrid9");
            
            lineCount = arrCount.length; //����
        
                //ѡ��ѡ��ѡ�е������ύ
        	for(int i=0;i<lineCount;i++)
        	{
        		if(tRadio[i].equals("1"))
        		{
        		
                    loggerDebug("PubAccSave","���ֱ��� "+tRiskCode[i]);
                    loggerDebug("PubAccSave","�����ʻ����� "+tPubAccName[i]);
                    loggerDebug("PubAccSave","���α��� "+tDutyCode[i]);
                    loggerDebug("PubAccSave","���� "+tPrem[i]);
                    loggerDebug("PubAccSave","���� "+tAmnt[i]);
                    loggerDebug("PubAccSave","�Զ�Ӧ�ù����ʻ���� "+tPubAccFlag[i]);                    
                    
                    if (tAction.equals("INSERT||PUBACC"))
                    {
                    
                        tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
                        tLCGrpContSchema.setProposalGrpContNo(request.getParameter("GrpContNo"));
                        loggerDebug("PubAccSave","����Ͷ������ͬ�� " +tLCGrpContSchema.getProposalGrpContNo());
                        tmLCInsuredSchema.setInsuredNo("");
                        tmLCInsuredSchema.setContNo("1");
                        tmLCInsuredSchema.setPrtNo("1");
                    }
                    else if (tAction.equals("UPDATE||PUBACC"))
                    {                        
                        tLCContSchema.setContNo(tContNo[i]);
                        tLCGrpAppntSchema.setGrpContNo(request.getParameter("GrpContNo"));
                        tLCGrpAppntSchema.setCustomerNo(request.getParameter("AppntNo"));
                        tLCAppntSchema.setContNo(tContNo[i]);
                        //tLCAppntSchema.setAppntNo(request.getParameter("AppntCustomerNo"));  //�ͻ���
                        //tLCAppntSchema.setAppntName(request.getParameter("AppntName"));              //����
                        //tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));                //�Ա�
                        //tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));      //��������
                        tmLCInsuredSchema.setInsuredNo(tInsuredNo[i]);
                        tmLCInsuredSchema.setContNo(tContNo[i]);
                        tmLCInsuredSchema.setPrtNo(tContNo[i]);
                    }
                    tmLCInsuredSchema.setName(tPubAccName[i]); //�����ʻ�����
                    tmLCInsuredSchema.setRelationToMainInsured("00");
                    
                    tmLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
                    //tmLCInsuredSchema.setContPlanCode(request.getParameter("ContPlanCode"));
                    //tmLCInsuredSchema.setExecuteCom(request.getParameter("ExecuteCom"));
                    tmLCInsuredSchema.setInsuredPeoples(0);
                    tmLCInsuredSchema.setCreditGrade("2"); //���� ���������õȼ� �ֶα��� �������ͱ��
                    tmLCInsuredSchema.setSex("0");
                    tmLCInsuredSchema.setBirthday("1979-01-01");
                    
                    tLCInsuredSet.add(tmLCInsuredSchema);
                    
                    if (tAction.equals("INSERT||PUBACC"))
                    {
                        tLCPolSchema.setInsuredNo("1");
                        tLCPolSchema.setMainPolNo(tRiskCode[i]);
                        tLCPolSchema.setContNo("1");                    
                        tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
                        tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
                        tLCPolSchema.setInsuredPeoples(0); //����������
                        tLCPolSchema.setPolTypeFlag("2"); //�������ͱ��                        
                        //tLCPolSchema.setPayRuleCode("0");
                        tLCPolSchema.setRiskCode(tRiskCode[i]); 
                    }
                    if (tAction.equals("UPDATE||PUBACC"))
                    {                        
                        tLCPolDB.setPolNo(tPolNo[i]);
                        if (tLCPolDB.getInfo())
                        {
                            tLCPolSchema = tLCPolDB.getSchema();
                        }
                    } 
                    
                    tLCPolSchema.setAutoPubAccFlag(tPubAccFlag[i]); //AutoPubAccFlag
                    tLCPolSchema.setMult(1);                       //����
                    tLCPolSchema.setPrem(tPrem[i]);                 //����
                    tLCPolSchema.setAmnt(0);                    //����
         	      
        	 	    
                    tLCBnfSchema.setRelationToInsured("00");
                    tLCBnfSet.add(tLCBnfSchema);
                    
                    
                    if (tAction.equals("UPDATE||PUBACC"))
                    {
                        tLCDutySchema.setPolNo(tPolNo[i]);
                    }
                    tLCDutySchema.setDutyCode(tDutyCode[i]);
                    tLCDutySet.add(tLCDutySchema);
        
                }
            }
        }        
        
        
        tTransferData.setNameAndValue("SavePolType","0"); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ  
        //tTransferData.setNameAndValue("FamilyType","0"); //��ͥ����� 
        tTransferData.setNameAndValue("ContType","2"); //�ŵ������˵����
        tTransferData.setNameAndValue("PolTypeFlag","2"); //���������
        tTransferData.setNameAndValue("InsuredPeoples",0); //������������
        tTransferData.setNameAndValue("PubAmntFlag","N"); //����������
        loggerDebug("PubAccSave","------PubAmntFlag-----"+tTransferData.getValueByName("PubAmntFlag"));

        try
         {
            // ׼���������� VData
            VData tVData = new VData();
            tVData.add(tTransferData);
            tVData.add(tGI);                       
            tVData.add(tLCPolSchema);            
            if (tAction.equals("INSERT||PUBACC"))
            {
                tVData.add(tLCGrpContSchema); 
                tVData.add(tLCBnfSet);
                tVData.add(tLCDutySet);
                tVData.add(tLCInsuredSet);
            }
            else if (tAction.equals("UPDATE||PUBACC"))
            {
                tVData.add(tLCContSchema);
                tVData.add(tLCBnfSchema);
                tVData.add(tLCDutySchema);
                tVData.add(tmLCInsuredSchema);
                tVData.add(tLCGrpAppntSchema);
                tVData.add(tLCAppntSchema);
            }
            tVData.addElement(tLCInsuredRelatedSet);
            
            loggerDebug("PubAccSave","tLCPolSchema=" + tLCPolSchema.encode());
            loggerDebug("PubAccSave","tLCGrpContSchema=" + tLCGrpContSchema.encode());
            loggerDebug("PubAccSave","tLCBnfSchema=" + tLCBnfSchema.encode());
            loggerDebug("PubAccSave","tLCDutySchema=" + tLCDutySchema.encode());
            loggerDebug("PubAccSave","tLCGrpAppntSchema=" + tLCGrpAppntSchema.encode());
            loggerDebug("PubAccSave","tmLCInsuredSchema=" + tmLCInsuredSchema.encode());
            loggerDebug("PubAccSave","Start to save PubAcc **************");  
             //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
            if (tAction.equals("INSERT||PUBACC"))
            {
            	String busiName="tbParseGuideIn";
                //if (tParseGuideIn.submitData2(tVData,""))
                if (tBusinessDelegate.submitData(tVData,"",busiName))
                {
        		    tVData.clear();
        		    tVData = tBusinessDelegate.getResult();
        		    //loggerDebug("PubAccSave","tVData-----size:"+tVData.size());
    	        }
    	    }
    	    else    	    {
    	        
            	String busiName="tbProposalUI";
            	
    	        if (tBusinessDelegate.submitData(tVData,"UPDATE||PROPOSAL",busiName))
                {
        		    tVData.clear();
        		    tVData = tBusinessDelegate.getResult();
        		    //loggerDebug("PubAccSave","tVData-----size:"+tVData.size());
    	        }
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
            if (tAction.equals("INSERT||PUBACC"))
            {
                tError = tBusinessDelegate.getCErrors();
            }
            else
            {
                tError = tBusinessDelegate.getCErrors();
            }
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
        loggerDebug("PubAccSave","FlagStr:"+FlagStr+"Content:"+Content);
      
    } //ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterAccSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

