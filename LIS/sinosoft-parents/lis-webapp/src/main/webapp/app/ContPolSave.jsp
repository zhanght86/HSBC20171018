<%--
    ���漯�屣����Ϣ 2004-11-16 wzw
--%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.text.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%	         
	
  String FlagStr="";      //�������
  String Content = "";    //����̨��Ϣ
  String tAction = "";    //�������ͣ�delete update insert
  String tOperate = "";   //��������
  String mLoadFlag="";
  String Flag="";         //�����ж�ҵ��ԱӶ��������޿�ֵ
  //�����֪��Ϣ
  String tImpartNum[] = request.getParameterValues("ImpartGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //��֪���
	String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //��֪����
	String tImpartContent[] = request.getParameterValues("ImpartGrid3");        //��֪����
	String tImpartParamModle[] = request.getParameterValues("ImpartGrid4"); 
  //������֪
  String tImpartNum2[] = request.getParameterValues("HistoryImpartGridNo");
	String tInsuYear[] = request.getParameterValues("HistoryImpartGrid1");
	String tInsuYearFlag[] = request.getParameterValues("HistoryImpartGrid2");            //��֪���
	String tInsuContent[] = request.getParameterValues("HistoryImpartGrid3");           //��֪����
	String tRate[] = request.getParameterValues("HistoryImpartGrid4");        //��֪����
	String tEnsureContent[] = request.getParameterValues("HistoryImpartGrid5");
	String tPeoples[] = request.getParameterValues("HistoryImpartGrid6");	 
	String tRecompensePeoples[] = request.getParameterValues("HistoryImpartGrid7"); 
	String tOccurMoney[] = request.getParameterValues("HistoryImpartGrid8"); 
	String tRecompenseMoney[] = request.getParameterValues("HistoryImpartGrid9"); 
	String tPendingMoney[] = request.getParameterValues("HistoryImpartGrid10");
	String tSerialNo1[] = request.getParameterValues("HistoryImpartGrid11");  
	//���ؼ��������֪
	String tImpartNum3[] = request.getParameterValues("DiseaseGridNo");
	String tOcurTime[] = request.getParameterValues("DiseaseGrid1");
	String tDiseaseName[] = request.getParameterValues("DiseaseGrid2");            //��֪���
	String tDiseasePepoles[] = request.getParameterValues("DiseaseGrid3");           //��֪����
	String tCureMoney[] = request.getParameterValues("DiseaseGrid4");        //��֪����
	String tRemark[] = request.getParameterValues("DiseaseGrid5");
	String tSerialNo2[] = request.getParameterValues("DiseaseGrid6"); 
	//�ͻ���������
	String tServInfoGridNum[] = request.getParameterValues("ServInfoGridNo");
	String tServKind[] = request.getParameterValues("ServInfoGrid1");
	loggerDebug("ContPolSave","�ͻ��������� :"+tServKind);
	String tServDetail[] = request.getParameterValues("ServInfoGrid2");            //��֪���
	String tServChoose[] = request.getParameterValues("ServInfoGrid3");           //��֪����
	String tServRemark[] = request.getParameterValues("ServInfoGrid4"); 

  VData tVData = new VData();
  LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();      //���屣��
  LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();   //�ŵ�Ͷ����
  LDGrpSchema tLDGrpSchema   = new LDGrpSchema();                //����ͻ�
  LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema(); //����ͻ���ַ
  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();	//�ͻ���֪
  LCHistoryImpartSet tLCHistoryImpartSet = new LCHistoryImpartSet(); //������֪          //������֪
  LCDiseaseImpartSet tLCDiseaseImpartSet = new LCDiseaseImpartSet(); //���ؼ�����֪		
  LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
  LACommisionDetailSchema mLACommisionDetailSchema = new LACommisionDetailSchema();

  //�ͻ�������Ϣ
  LCGrpServInfoSet tLCGrpServInfoSet = new LCGrpServInfoSet(); 
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
  tAction = request.getParameter( "fmAction" );
  //���屣����Ϣ  LCGrpCont
	tLCGrpContSchema.setProposalGrpContNo(request.getParameter("ProposalGrpContNo"));  //����Ͷ��������
	tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
	tLCGrpContSchema.setPrtNo(request.getParameter("PrtNo"));                  //ӡˢ����
	tLCGrpContSchema.setManageCom(request.getParameter("ManageCom"));          //��������
	tLCGrpContSchema.setSaleChnl("2");            //��������


	tLCGrpContSchema.setConferNo(request.getParameter("ConferNo"));            //����Э�����
	tLCGrpContSchema.setSignReportNo(request.getParameter("SignReportNo"));    //ǩ����
	tLCGrpContSchema.setReportNo(request.getParameter("ReportNo"));            //�ʱ�����
	tLCGrpContSchema.setAgentConferNo(request.getParameter("AgentConferNo"));  //����Э�����
	
	
	tLCGrpContSchema.setAgentCom(request.getParameter("AgentCom"));            //��������
	tLCGrpContSchema.setAgentType(request.getParameter("AgentType"));           //������������
	tLCGrpContSchema.setAgentCode(request.getParameter("AgentCode"));          //�����˱���
	tLCGrpContSchema.setAgentGroup(request.getParameter("AgentGroup"));        //���������
	tLCGrpContSchema.setAgentCode1(request.getParameter("AgentCode1"));        //���ϴ����˴���
	tLCGrpContSchema.setGrpSpec(request.getParameter("GrpSpec"));              //������Լ
	tLCGrpContSchema.setAppntNo(request.getParameter("GrpNo"));                //�ͻ�����
	tLCGrpContSchema.setAddressNo(request.getParameter("GrpAddressNo"));       //��ַ����
	tLCGrpContSchema.setGrpName(request.getParameter("GrpName"));              //��λ����
	tLCGrpContSchema.setGetFlag(request.getParameter("GetFlag"));              //���ʽ
	tLCGrpContSchema.setBankCode(request.getParameter("BankCode"));            //���б���
	tLCGrpContSchema.setBankAccNo(request.getParameter("BankAccNo"));          //�����ʺ�
	tLCGrpContSchema.setCurrency(request.getParameter("Currency"));            //�ұ�
	tLCGrpContSchema.setCValiDate(request.getParameter("CValiDate"));          //������Ч����
	tLCGrpContSchema.setPolApplyDate(request.getParameter("PolApplyDate"));    //����Ͷ������
	tLCGrpContSchema.setOutPayFlag(request.getParameter("OutPayFlag"));        //�罻������ʽ
	tLCGrpContSchema.setEnterKind(request.getParameter("EnterKind"));          //�α���ʽ
	tLCGrpContSchema.setAmntGrade(request.getParameter("AmntGrade"));          //����ȼ�
	tLCGrpContSchema.setPeoples3(request.getParameter("Peoples"));						 //��λ��Ͷ������
  tLCGrpContSchema.setOnWorkPeoples(request.getParameter("OnWorkPeoples"));		//��λ��Ͷ������	    
  tLCGrpContSchema.setOffWorkPeoples(request.getParameter("OffWorkPeoples"));		//��λ��Ͷ������	    
  tLCGrpContSchema.setOtherPeoples(request.getParameter("OtherPeoples"));		//��λ��Ͷ������
  tLCGrpContSchema.setRelaPeoples(request.getParameter("RelaPeoples"));		//��������������
  tLCGrpContSchema.setRelaMatePeoples(request.getParameter("RelaMatePeoples"));		//��ż����	    
  tLCGrpContSchema.setRelaYoungPeoples(request.getParameter("RelaYoungPeoples"));		//��Ů����
  tLCGrpContSchema.setRelaOtherPeoples(request.getParameter("RelaOtherPeoples"));		//����������Ա��	    
	    
	    
	tLCGrpContSchema.setGrpNature(request.getParameter("GrpNature"));         //��λ����
	tLCGrpContSchema.setBusinessType(request.getParameter("BusinessType"));   //��ҵ���
	tLCGrpContSchema.setPeoples(request.getParameter("Peoples"));             //������
	tLCGrpContSchema.setRgtMoney(request.getParameter("RgtMoney"));           //ע���ʱ�
	tLCGrpContSchema.setAsset(request.getParameter("Asset"));                 //�ʲ��ܶ�
	tLCGrpContSchema.setNetProfitRate(request.getParameter("NetProfitRate")); //���ʲ�������
	tLCGrpContSchema.setMainBussiness(request.getParameter("MainBussiness")); //��Ӫҵ��
	tLCGrpContSchema.setCorporation(request.getParameter("Corporation"));     //����
	tLCGrpContSchema.setComAera(request.getParameter("ComAera"));             //�����ֲ�����
	tLCGrpContSchema.setPhone(request.getParameter("Phone"));             		//�ܻ�
	tLCGrpContSchema.setFax(request.getParameter("Fax"));             				//����
	tLCGrpContSchema.setFoundDate(request.getParameter("FoundDate"));         //����ʱ��
	tLCGrpContSchema.setRemark(request.getParameter("Remark"));		
	
	tLCGrpContSchema.setAgentCom(request.getParameter("MediAgentCom")); //�н����
	tLCGrpContSchema.setHandlerName(request.getParameter("MediAgentCode")); //�н����ҵ��Ա
			
	//�ŵ�Ͷ������Ϣ  LCGrpAppnt
	tLCGrpAppntSchema.setGrpContNo(request.getParameter("ProposalGrpContNo"));     //����Ͷ��������
	tLCGrpAppntSchema.setCustomerNo(request.getParameter("GrpNo"));            //�ͻ�����
	tLCGrpAppntSchema.setPrtNo(request.getParameter("PrtNo"));                 //ӡˢ����
	tLCGrpAppntSchema.setName(request.getParameter("GrpName"));
	tLCGrpAppntSchema.setPostalAddress(request.getParameter("GrpAddress"));
	tLCGrpAppntSchema.setZipCode(request.getParameter("GrpZipCode"));
	tLCGrpAppntSchema.setAddressNo(request.getParameter("GrpAddressNo"));
	tLCGrpAppntSchema.setPhone(request.getParameter("Phone"));
	//����ͻ���Ϣ  LDGrp
	tLDGrpSchema.setCustomerNo(request.getParameter("GrpNo"));            //�ͻ�����
	tLDGrpSchema.setGrpName(request.getParameter("GrpName"));             //��λ����
	tLDGrpSchema.setGrpNature(request.getParameter("GrpNature"));         //��λ����
	tLDGrpSchema.setBusinessType(request.getParameter("BusinessType"));   //��ҵ���
	tLDGrpSchema.setPeoples(request.getParameter("Peoples"));             //������
	tLDGrpSchema.setOnWorkPeoples(request.getParameter("AppntOnWorkPeoples"));             //������
	tLDGrpSchema.setOffWorkPeoples(request.getParameter("AppntOffWorkPeoples"));             //������
	tLDGrpSchema.setOtherPeoples(request.getParameter("AppntOtherPeoples"));             //������	    	    	    
	tLDGrpSchema.setRgtMoney(request.getParameter("RgtMoney"));           //ע���ʱ�
	tLDGrpSchema.setAsset(request.getParameter("Asset"));                 //�ʲ��ܶ�
	tLDGrpSchema.setNetProfitRate(request.getParameter("NetProfitRate")); //���ʲ�������
	tLDGrpSchema.setMainBussiness(request.getParameter("MainBussiness")); //��Ӫҵ��
	tLDGrpSchema.setCorporation(request.getParameter("Corporation"));     //����
	tLDGrpSchema.setComAera(request.getParameter("ComAera"));             //�����ֲ�����
	tLDGrpSchema.setPhone(request.getParameter("Phone"));             //�ܻ�
	tLDGrpSchema.setFax(request.getParameter("Fax"));             //����
	tLDGrpSchema.setFoundDate(request.getParameter("FoundDate"));             //����ʱ��
	tLDGrpSchema.setVIPValue(request.getParameter("AppntVIPValue"));
	tLDGrpSchema.setSocialInsuNo(request.getParameter("BankAccNo1"));
	//����ͻ���ַ  LCGrpAddress	    
	tLCGrpAddressSchema.setCustomerNo(request.getParameter("GrpNo"));            //�ͻ�����
	tLCGrpAddressSchema.setAddressNo(request.getParameter("GrpAddressNo"));      //��ַ����
	tLCGrpAddressSchema.setGrpAddress(request.getParameter("GrpAddress"));       //��λ��ַ
	loggerDebug("ContPolSave","*******************"+request.getParameter("GrpAddress"));
	tLCGrpAddressSchema.setGrpZipCode(request.getParameter("GrpZipCode"));       //��λ�ʱ�
	//������ϵ��һ
	tLCGrpAddressSchema.setLinkMan1(request.getParameter("LinkMan1"));
	tLCGrpAddressSchema.setDepartment1(request.getParameter("Department1"));
	tLCGrpAddressSchema.setHeadShip1(request.getParameter("HeadShip1"));
	tLCGrpAddressSchema.setPhone1(request.getParameter("Phone1"));
	tLCGrpAddressSchema.setE_Mail1(request.getParameter("E_Mail1"));
	tLCGrpAddressSchema.setFax1(request.getParameter("Fax1"));
	//������ϵ�˶�
	tLCGrpAddressSchema.setLinkMan2(request.getParameter("LinkMan2"));
	tLCGrpAddressSchema.setDepartment2(request.getParameter("Department2"));
	tLCGrpAddressSchema.setHeadShip2(request.getParameter("HeadShip2"));
	tLCGrpAddressSchema.setPhone2(request.getParameter("Phone2"));
	tLCGrpAddressSchema.setE_Mail2(request.getParameter("E_Mail2"));
	tLCGrpAddressSchema.setFax2(request.getParameter("Fax2"));
	
  
  //����ж�ҵ��Ա���������Ҫ¼���ҵ��Ա��Ϣ��
  String multiAgentFlag = request.getParameter("multiagentflag");
  loggerDebug("ContPolSave","#######multiAgentFlag=="+multiAgentFlag);
  loggerDebug("ContPolSave","#######ProposalGrpContNo=="+request.getParameter("ProposalGrpContNo"));
  if(multiAgentFlag!=null && multiAgentFlag.equals("true")){
    //��ȡ����ҵ��Ա����Ϣ
    
    String mainAgentCode = request.getParameter("AgentCode");
    //ȡ������ҵ��Ա����Ϣ
    String tMultAgentNum[] = request.getParameterValues("MultiAgentGridNo"); 
    String tMultAgentCode[] = request.getParameterValues("MultiAgentGrid1"); 
    String tMultBusiRate[] = request.getParameterValues("MultiAgentGrid5"); 
    String tMultAgentGroup[] = request.getParameterValues("MultiAgentGrid6"); 
    
    int agentCount = 0;
    String polType = "0";
    double tBusiRate = 0;
    //���ڿ���С������
    DecimalFormat df = new DecimalFormat("0.00");
    
    if(tMultAgentNum!=null){
      agentCount = tMultAgentNum.length;

    }
    
    for(int i=0; i<agentCount; i++){

      LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
      tLACommisionDetailSchema.setGrpContNo(request.getParameter("ProposalGrpContNo"));
      tLACommisionDetailSchema.setAgentCode(tMultAgentCode[i]); 
      if (tMultAgentCode[i] == null || "".equals(tMultAgentCode[i])){
         Flag="false";
         Content = " ����ʧ�ܣ�ԭ����: ҵ��Ա���벻��Ϊ�գ�";
         break;
      }  
      tLACommisionDetailSchema.setBusiRate(tMultBusiRate[i]);
      loggerDebug("ContPolSave",tMultBusiRate[i]);
      if(tMultBusiRate[i].equals(""))
        {
        	 Flag="false";
        	 Content = " ����ʧ�ܣ�ԭ����: ҵ��ԱӶ���������Ϊ�գ�";
           break;
      	}
      tLACommisionDetailSchema.setAgentGroup(tMultAgentGroup[i]);
      tLACommisionDetailSchema.setPolType("0");
      tLACommisionDetailSchema.setMakeDate(request.getParameter("AppntMakeDate"));  
      tLACommisionDetailSchema.setMakeTime(request.getParameter("AppntMakeTime"));  

//      tBusiRate = tBusiRate + Double.parseDouble(tMultBusiRate[i]);
      
      tLACommisionDetailSet.add(tLACommisionDetailSchema);

    }

    loggerDebug("ContPolSave","--tBusiRate==" + tBusiRate);
    
  }
    
    if(Flag.equals("false"))
    {    	
		FlagStr = "Fail";
    } 
   
   else{  //������ҵ��Ա����Ϣ
  
      mLACommisionDetailSchema.setGrpContNo(request.getParameter("ProposalGrpContNo"));
      mLACommisionDetailSchema.setAgentCode(request.getParameter("AgentCode"));
      mLACommisionDetailSchema.setAgentGroup(request.getParameter("AgentGroup"));
      mLACommisionDetailSchema.setBusiRate(1);
      mLACommisionDetailSchema.setPolType("0");
      mLACommisionDetailSchema.setMakeDate(request.getParameter("MakeDate"));  
      mLACommisionDetailSchema.setMakeTime(request.getParameter("MakeTime"));  

	///////////////////////////////////////////////////////////////
	
	//�ͻ���֪
	int ImpartCount = 0;
	if (tImpartNum != null) ImpartCount = tImpartNum.length;
	    
	for (int i = 0; i < ImpartCount; i++)	
	{
	LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
	tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
		
		tLCCustomerImpartSchema.setCustomerNo(tLDGrpSchema.getCustomerNo());
		tLCCustomerImpartSchema.setCustomerNoType("0");
		tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
		tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
		tLCCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
		tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
		tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
	}
	//���������֪
	int ImpartCont2 = 0;
	if (tImpartNum2 != null) ImpartCont2 = tImpartNum2.length;
					//loggerDebug("ContPolSave","���󷨰�������"+ImpartCont2);    
				for (int i = 0; i < ImpartCont2; i++)	
	{	    				//loggerDebug("ContPolSave","���󷨰���������������ssss");    
	LCHistoryImpartSchema tLCHistoryImpartSchema = new LCHistoryImpartSchema();
	  //tLCHistoryImpartSchema.setPrtNo(request.getParameter("PrtNo"));
		//tLCHistoryImpartSchema.setGrpContNo(request.getParameter("GrpContNo"));
		//tLCHistoryImpartSchema.setSerialNo(request.getParameter("SerialNo"));

		tLCHistoryImpartSchema.setInsuYear(tInsuYear[i]);
		loggerDebug("ContPolSave","gjgjhgjj"+tInsuYear[i]);                                                            
		tLCHistoryImpartSchema.setInsuYearFlag(tInsuYearFlag[i]);                                                             
		tLCHistoryImpartSchema.setInsuContent(tInsuContent[i]);                                                             
		tLCHistoryImpartSchema.setRate(tRate[i]) ;                                                             
		tLCHistoryImpartSchema.setEnsureContent(tEnsureContent[i]) ;                                                             
		tLCHistoryImpartSchema.setPeoples(tPeoples[i]) ;                                                             
		tLCHistoryImpartSchema.setRecompensePeoples(tRecompensePeoples[i]) ;                                                     
		tLCHistoryImpartSchema.setOccurMoney(tOccurMoney[i]) ;                                                             
		tLCHistoryImpartSchema.setRecompenseMoney(tRecompenseMoney[i]) ;                                                         
		tLCHistoryImpartSchema.setPendingMoney(tPendingMoney[i]) ;
		tLCHistoryImpartSchema.setSerialNo(tSerialNo1[i]) ;				                                                       
		
		tLCHistoryImpartSet.add(tLCHistoryImpartSchema);
	}
	//���ؼ��������֪
	int ImpartCont3 = 0;
	if (tImpartNum3 != null) ImpartCont3 = tImpartNum3.length;
	 
	for (int i = 0; i < ImpartCont3; i++)	
	{  //loggerDebug("ContPolSave","���󷨰�������"+tOcurTime[i]);  
	  LCDiseaseImpartSchema tLCDiseaseImpartSchema = new LCDiseaseImpartSchema();
 		tLCDiseaseImpartSchema.setOcurTime(tOcurTime[i]);                                                             
		tLCDiseaseImpartSchema.setDiseaseName(tDiseaseName[i]);                                                             
		tLCDiseaseImpartSchema.setDiseasePepoles(tDiseasePepoles[i]) ;                                                             
		tLCDiseaseImpartSchema.setCureMoney(tCureMoney[i]) ;                                                             
		tLCDiseaseImpartSchema.setRemark(tRemark[i]) ; 
		tLCDiseaseImpartSchema.setSerialNo(tSerialNo2[i]) ;	                                                            
		
		tLCDiseaseImpartSet.add(tLCDiseaseImpartSchema);
	}
	int m = 0;
	if (tServKind != null) m = tServKind.length;
	 loggerDebug("ContPolSave","----��ʼ�ͻ�������Ϣ----");
	 loggerDebug("ContPolSave","Mulline ���� ��"+m);
	for (int i = 0; i < m; i++)	
	{  
	   LCGrpServInfoSchema tLCGrpServInfoSchema = new LCGrpServInfoSchema();
 		//���Mulline�е�����
 		tLCGrpServInfoSchema.setServKind(tServKind[i]);                                                             
		tLCGrpServInfoSchema.setServDetail(tServDetail[i]);                                                             
		tLCGrpServInfoSchema.setServChoose(tServChoose[i]) ;                                                             
		tLCGrpServInfoSchema.setServRemark(tServRemark[i]) ;  
		//��ҳ���ϻ�ȡ������
		tLCGrpServInfoSchema.setPrtNo(request.getParameter("PrtNo"));
		//�������ݴӺ�̨��ȡ
		tLCGrpServInfoSet.add(tLCGrpServInfoSchema);
	}
  loggerDebug("ContPolSave","end setSchema:");
  // ׼���������� VData
  tVData.add( tLCGrpContSchema );
  tVData.add( tLCGrpAppntSchema );
  tVData.add( tLDGrpSchema );
  tVData.add( tLCGrpAddressSchema );
  tVData.add( tLCCustomerImpartSet );   //�����֪��Ϣ
  tVData.add( tLCHistoryImpartSet );			//������֪
  tVData.add( tLCDiseaseImpartSet );			//���ؼ�����֪
  loggerDebug("ContPolSave",";lajsdkjfas;jkdf");
  tVData.add( tLCGrpServInfoSet );
  loggerDebug("ContPolSave","tVData"+tVData);
	tVData.add(tLACommisionDetailSet); 
	tVData.add(mLACommisionDetailSchema);      
  tVData.add( tG );
  
  loggerDebug("ContPolSave","tLCGrpContSchema:"+tLCGrpContSchema.encode());    
  loggerDebug("ContPolSave","tLCGrpAppntSchema:"+tLCGrpAppntSchema.encode());    
  loggerDebug("ContPolSave","tLDGrpSchema:"+tLDGrpSchema.encode());
  loggerDebug("ContPolSave","tLCGrpAddressSchema:"+tLCGrpAddressSchema.encode());    
  loggerDebug("ContPolSave","tLCCustomerImpartSet:"+tLCCustomerImpartSet.encode());    
  loggerDebug("ContPolSave","tLCHistoryImpartSet:"+tLCHistoryImpartSet.encode());
  loggerDebug("ContPolSave","tLACommisionDetailSet:"+tLACommisionDetailSet.encode());    
  loggerDebug("ContPolSave","mLACommisionDetailSchema:"+mLACommisionDetailSchema.encode());	
  //���ݷ�SCHEMA��Ϣ
  TransferData tTransferData = new TransferData();
  String SavePolType="";
  String BQFlag=request.getParameter("BQFlag");
  if(BQFlag==null) SavePolType="0";
  else if(BQFlag.equals("")) SavePolType="0";
  else  SavePolType=BQFlag;
  
  //����LoadFlag
  mLoadFlag=request.getParameter("LoadFlag"); 
  tTransferData.setNameAndValue("LoadFlag",mLoadFlag);
  loggerDebug("ContPolSave","LoadFlag is : " + request.getParameter("LoadFlag"));

  
  tTransferData.setNameAndValue("SavePolType",SavePolType); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ
  loggerDebug("ContPolSave","SavePolType��BQ is 2��other is 0 : " + request.getParameter("BQFlag"));
  tVData.addElement(tTransferData);
  
  
  
	if( tAction.equals( "INSERT" )) tOperate = "INSERT||GROUPPOL";
	if( tAction.equals( "UPDATE" )) tOperate = "UPDATE||GROUPPOL";
	if( tAction.equals( "DELETE" )) tOperate = "DELETE||GROUPPOL";
	//GroupContUI tGroupContUI = new GroupContUI();
	String busiName="cardgrpGroupContUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if( tBusinessDelegate.submitData( tVData, tOperate,busiName ) == false )
	{
		Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = " ����ɹ�! ";
		FlagStr = "Succ";

		tVData.clear();
		tVData = tBusinessDelegate.getResult();

		// ��ʾ
		if(( tAction.equals( "INSERT" ))||( tAction.equals( "UPDATE" )))
		{
			// ������Ϣ
			LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema(); 
			mLCGrpContSchema.setSchema(( LCGrpContSchema )tVData.getObjectByObjectName( "LCGrpContSchema", 0 ));
			%>
			   <script language="javascript">
			    	parent.fraInterface.document.all("ProposalGrpContNo").value = "<%=mLCGrpContSchema.getProposalGrpContNo()%>";
			    	parent.fraInterface.document.all("GrpContNo").value = "<%=mLCGrpContSchema.getGrpContNo()%>";
			     parent.fraInterface.document.all("GrpNo").value = "<%=mLCGrpContSchema.getAppntNo()%>";
			     parent.fraInterface.document.all("GrpAddressNo").value = "<%=mLCGrpContSchema.getAddressNo()%>"; 
			         	
			   </script>
			<%		
		}
		else
		{
			%>
			   <script language="javascript">
			     parent.fraInterface.emptyFormElements();
			    </script>
			<%
		
		}
	}
        loggerDebug("ContPolSave","Content:"+Content);	
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
