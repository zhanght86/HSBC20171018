<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�ProposalSave.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//Modify by niuzj,2006-08-23,Ӣ����Ҫ��¼����������Ϣʱ����һ�����Ա��ֶ�
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sinosoft.service.*" %>
<%


  //�������
  String FORMATMODOL = "0.00"; //���ѱ�����������ľ�ȷλ��
  DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); //����ת������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String tAction = "";
  String tOldAction = "";
  String tOperate = "";
  boolean ChangePlanFlag = false;
  //tongmeng 2008-12-18 add
  //�ָ��˱�״̬Ϊ�޸�ǰ��״̬
  boolean ReSaveUWFlag = false;
  String approveFlag = "";
  String UWFlag = "";
  String approveCode = "";
  String approveDate = "";
  String tLoadFlag = "";
  String mLoadFlag = "";
  String remark="";
  String ttDutyCode = "";
  String tAutoPayFlag ="";//�Զ��潻��־
  GlobalInput tG = new GlobalInput();
        tG = ( GlobalInput )session.getValue( "GI" );
  tAction = request.getParameter( "fmAction" );
  ttDutyCode = request.getParameter( "DutyCode" );
  if( tAction.equals( "INSERTPERSON" )) tOperate = "INSERT||PROPOSAL";
  if( tAction.equals( "INSERTGROUP" )) tOperate = "INSERT||PROPOSAL";
  if( tAction.equals( "UPDATEPERSON" )) tOperate = "UPDATE||PROPOSAL";
  if( tAction.equals( "UPDATEGROUP" )) tOperate = "UPDATE||PROPOSAL";
  if( tAction.equals( "ChangePlanUPDATEGROUP" )) tOperate = "UPDATE||PROPOSAL";
  if( tAction.equals( "DELETE" )) tOperate = "DELETE||PROPOSAL";

    String triskcode=request.getParameter("RiskCode");
    loggerDebug("ProposalSave","������-��ͬ��-Session====="+request.getParameter("ContNo")+"-"+request.getParameter("RiskCode")+"-"+(String)session.getValue("MainRiskNo"));
    loggerDebug("ProposalSave","���²��ԣ�");
    String tt=(String)session.getValue("MainRiskNo");

    if(!remark.equals("1")){
      //end �ж�
      loggerDebug("ProposalSave","save....111111");
      LCPolSchema mLCPolSchema = new LCPolSchema();
      LCDutyBLSet mLCDutyBLSet=new LCDutyBLSet();
      try  {
        //ProposalUI tProposalUI  = new ProposalUI();
        String busiName2="tbProposalUI";
        BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
        

        VData tVData = new VData();

        tOldAction = tAction;
        loggerDebug("ProposalSave","�û�ѡ��Ĳ���ΪtAction:"+tAction);

        if( tAction.equals( "DELETE" ))  //ѡ��ɾ�����������Ĵ���
        {
          LCPolSchema tLCPolSchema  = new LCPolSchema();
          tLCPolSchema.setProposalNo( request.getParameter( "ProposalNo" ));
          // ׼���������� VData
          tVData.addElement( tLCPolSchema );
          tVData.addElement( tG );
        }
        else
        {
        	//tongmeng 2008-12-19 add
        	//������Լ��,ֻ�к˱�ʦ�����޸����ֽ���,�����.����������޸�,�Զ��˱�,�����޸����ֽ���
          if (tAction.indexOf("UPDATEPERSON") != -1)
          {
        	  //QueryPolInfoBL tQueryPolInfoBL = new QueryPolInfoBL();
        	  String busiName="cbcheckQueryPolInfoBL";
              BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        	  LCPolSchema tLCPolSchema = new LCPolSchema();
              tLCPolSchema.setPolNo(request.getParameter("ProposalNo"));
              VData VData2 = new VData();
              VData2.add(tLCPolSchema);
              VData2.add(tG);
              
              TransferData tTransferData = new TransferData();
        	  if (!tBusinessDelegate.submitData(VData2, "QUERY",busiName))
              {  //����ʧ��ʱ
             	 Content = "����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
              	FlagStr = "Fail";
              	throw new Exception();
              }
                else
               {//�����ɹ�ʱ
                VData vResult = tBusinessDelegate.getResult();
                tTransferData = (TransferData)vResult.getObjectByObjectName("TransferData",0);
                approveFlag = (String)tTransferData.getValueByName("ApproveFlag");
                UWFlag = (String)tTransferData.getValueByName("UWFlag");
                approveCode = (String)tTransferData.getValueByName("ApproveCode");
                approveDate = (String)tTransferData.getValueByName("ApproveDate");
                ReSaveUWFlag = true;
                loggerDebug("ProposalSave","##approveFlag:" + approveFlag + " UWFlag:" + UWFlag);
              }
          }
          else if (tAction.indexOf("ChangePlan") != -1) //�ڳб��ƻ���������汣��ʱ����ȡͶ����״̬
          {
            ChangePlanFlag = true;
            loggerDebug("ProposalSave","111 tAction:"+tAction);
            loggerDebug("ProposalSave","111 tAction:������ܳ�����ȡ����");
            if(tOldAction.indexOf("Modify") != 1)
            {
              if(tAction.equals("ChangePlanUPDATEGROUP")) //--����ж�
              {
                tAction = tAction.substring(10);
              }
              else
              { tAction = tAction.substring(16);}
            }
            else
            tAction = tAction.substring(10);
            loggerDebug("ProposalSave","222 tAction:"+tAction);

            LCPolSchema tLCPolSchema = new LCPolSchema();
            tLCPolSchema.setPolNo(request.getParameter("ProposalNo"));
            VData VData2 = new VData();
            VData2.add(tLCPolSchema);
            VData2.add(tG);
            //ChangePlanUI tChangePlanUI = new ChangePlanUI();
            String busiName="cbcheckChangePlanUI";
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            //���̨��������
            if (!tBusinessDelegate.submitData(VData2, "QUERY||CHANGEPLAN",busiName))
            {  //����ʧ��ʱ
            Content = "����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
            FlagStr = "Fail";
            throw new Exception();
            }
              else
              {//�����ɹ�ʱ
              VData vResult = tBusinessDelegate.getResult();
              approveFlag = (String)vResult.get(0);
              UWFlag = (String)vResult.get(1);
              approveCode = (String)vResult.get(2);
              approveDate = (String)vResult.get(3);
    
              loggerDebug("ProposalSave","approveFlag:" + approveFlag + " UWFlag:" + UWFlag);
            }
        }

      //��ӡˢ��
      loggerDebug("ProposalSave","tOldAction.indexOf_Modify:"+tOldAction.indexOf("Modify"));
      loggerDebug("ProposalSave","GrpContNo:"+request.getParameter("GrpContNo"));
      if (request.getParameter("GrpContNo").equals("00000000000000000000") && tOldAction.indexOf("Modify") == -1)
      {
        LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
        tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
        tLDSysTraceSchema.setCreatePos("�б�¼��");
        tLDSysTraceSchema.setPolState("1002");
        LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
        inLDSysTraceSet.add(tLDSysTraceSchema);
        VData VData3 = new VData();
        VData3.add(tG);
        VData3.add(inLDSysTraceSet);

        //LockTableUI LockTableUI1 = new LockTableUI();
        String busiName="pubfunLockTableUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if (!tBusinessDelegate.submitData(VData3, "INSERT",busiName))
        {
          VData rVData = tBusinessDelegate.getResult();
          loggerDebug("ProposalSave","LockTable Failed! " + (String)rVData.get(0));
        }
        else
        {
          loggerDebug("ProposalSave","LockTable Succed!");
        }
      }

      if (tAction.indexOf("Modify") != -1)
        tAction = tAction.substring(6);
      loggerDebug("ProposalSave","tAction=" + tAction);
      LCContSchema tLCContSchema = new LCContSchema();
      tLCContSchema.setContNo(request.getParameter("ContNo"));
      loggerDebug("ProposalSave","LCCont Information ----------------------");
      loggerDebug("ProposalSave","ContNo="+request.getParameter("ContNo"));
      // ������Ϣ����
      LCPolSchema tLCPolSchema = new LCPolSchema();
      LCDutySchema tLCDutySchema = new LCDutySchema();

      tLCPolSchema.setProposalNo(request.getParameter("ProposalNo"));
      tLCPolSchema.setPrtNo(request.getParameter("PrtNo"));
      tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
      loggerDebug("ProposalSave","LCPol Information ----------------------");
      loggerDebug("ProposalSave","ProposalNo="+request.getParameter("ProposalNo"));
      loggerDebug("ProposalSave","ProposalNo="+request.getParameter("PrtNo"));
      loggerDebug("ProposalSave","ProposalNo="+request.getParameter("ManageCom"));
      loggerDebug("ProposalSave","SaleChnl="+request.getParameter("SaleChnl"));
      loggerDebug("ProposalSave","������Ϣ="+request.getParameter("CurrencyCode")+"--"+request.getParameter("CurrencyName"));
      //tLCPolSchema.setManageCom(tG.ManageCom);
      tLCPolSchema.setSaleChnl(request.getParameter("SaleChnl"));
      //��ѯ�����������������Ϊ3,���������Ϊ1 ����Ϊ2,ֱ��Ϊ5
//      LMRiskAppDB aLMRiskAppDB = new LMRiskAppDB();
//      aLMRiskAppDB.setRiskCode(triskcode);
//      aLMRiskAppDB.getInfo();
      loggerDebug("ProposalSave","Riskcode="+triskcode);
//      tLCPolSchema.setSaleChnl("01");
 //     if(aLMRiskAppDB.getRiskProp()!=null&&aLMRiskAppDB.getRiskProp().equals("I")){
 //       tLCPolSchema.setSaleChnl("02");
 //     }
 //     else if(aLMRiskAppDB.getRiskProp()!=null&&aLMRiskAppDB.getRiskProp().equals("Y")){
 //       tLCPolSchema.setSaleChnl("03");
  //    }
//      else if(aLMRiskAppDB.getRiskProp()!=null&&aLMRiskAppDB.getRiskProp().equals("G")){
//        tLCPolSchema.setSaleChnl("01");
//      }
 //     else if(aLMRiskAppDB.getRiskProp()!=null&&aLMRiskAppDB.getRiskProp().equals("T")){
 //       tLCPolSchema.setSaleChnl("05");
//      }
//      else{}
	
	String tCurrencyCode = request.getParameter("CurrencyCode");
	if(tCurrencyCode==null||tCurrencyCode.equals(""))
	{
		tCurrencyCode  = "01";
	}
      tLCPolSchema.setCurrency(tCurrencyCode);// ��Ӷ������Ϣ
      tLCPolSchema.setAgentCom(request.getParameter("AgentCom"));
      tLCPolSchema.setAgentType(request.getParameter("AgentType"));
      tLCPolSchema.setAgentCode(request.getParameter("AgentCode"));
      tLCPolSchema.setAgentGroup(request.getParameter("AgentGroup"));
      tLCPolSchema.setHandler(request.getParameter("Handler"));
      tLCPolSchema.setAgentCode1(request.getParameter("AgentCode1"));
      tLCPolSchema.setInsuredAppAge(request.getParameter("InsuredAppAge"));//������Ͷ������
      tLCPolSchema.setInsuredPeoples(request.getParameter("InsuredPeoples"));//����������
      //tLCPolSchema.setInsuredPeoples(10);
      tLCPolSchema.setPolTypeFlag(request.getParameter("PolTypeFlag"));//�������ͱ��
      tLCPolSchema.setContNo(request.getParameter("ContNo"));
      //tLCPolSchema.setGrpPolNo(request.getParameter("GrpPolNo"));
      tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
      tAutoPayFlag = request.getParameter("AutoPayFlag");
      //��tLCPolSchema���жϵı�־������̨-����Ӹ�����֮ǰ�Ƿ����������
      //tLCPolSchema.setKeepValueOpt(remark);//��ȫ�����������
      //tLCPolSchema.setMainPolNo(request.getParameter("MainPolNo"));
      //add verify �������û����ӣ�����ֱ�����Ӹ����� write by yaory
      //loggerDebug("ProposalSave","��־====="+request.getParameter("tLoadFlag"));
      //edit by yaory-2005-7-18//����Ǹ��������SESSION
      ExeSQL texeSql = new ExeSQL();
      SSRS FlagSSRS = new SSRS();
      //loggerDebug("ProposalSave","��������====="+request.getParameter("RiskCode"));
      //tongmeng 2008-07-03 Modify
      //֧�ֶ����պͶ౻���˵�¼��
//      FlagSSRS = texeSql.execSQL("select subriskflag,riskname from lmriskapp where riskcode='"+request.getParameter("RiskCode")+"'");
      String sql = "select subriskflag,riskname from lmriskapp where riskcode='"+request.getParameter("RiskCode")+"'" ;
      TransferData sTransferData=new TransferData();
      sTransferData.setNameAndValue("SQL", sql);
      VData sVData = new VData();
      sVData.add(sTransferData);
      BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      if(tBusinessDelegate.submitData(sVData, "execSQL", "ExeSQLUI"))
      {
    	  FlagSSRS = (SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
      }           
      String tMainPolNo = request.getParameter("MainPolNo");
      if(FlagSSRS.GetText(1,1).equals("S"))
      {
       
        if(tMainPolNo==null||tMainPolNo.equals(""))
        {
       		 //����Ǹ�����,���ж��Ƿ�ѡ����������Ϣ,���û��ѡ��,���ش���!
       		 Content = "����ѡ������'"+FlagSSRS.GetText(1,2)+"'��������������Ϣ!" ;
						FlagStr = "Fail";
				%>
				<script language="javascript">    
					parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
				</script>
					<%
							return ;
	      }            
           
        //tLCPolSchema.setMainPolNo((String)session.getValue("MainRiskPolNo"));
        tLCPolSchema.setMainPolNo(tMainPolNo);
        //���������150�򸽼���Ҫ���ӵ��ڶ���������
        loggerDebug("ProposalSave","MainPolNo="+tMainPolNo+"$#$$$$$$$$$$$$$$$$$$$$$$$$");      
        
      }else{
        tLCPolSchema.setMainPolNo(request.getParameter("MainPolNo"));
        loggerDebug("ProposalSave","MainPolNo="+request.getParameter("MainPolNo"));
      }
      //loggerDebug("ProposalSave","��������===="+(String)session.getValue("MainRiskPolNo"));
      tLCPolSchema.setFirstPayDate(request.getParameter("FirstPayDate"));
      //tLCPolSchema.setLang(request.getParameter("Lang"));
      //tLCPolSchema.setCurrency(request.getParameter("Currency"));
      //tLCPolSchema.setDisputedFlag(request.getParameter("DisputedFlag"));
      tLCPolSchema.setAgentPayFlag(request.getParameter("AgentPayFlag"));
      tLCPolSchema.setAgentGetFlag(request.getParameter("AgentGetFlag"));
      tLCPolSchema.setRemark(request.getParameter("Remark"));
      //add by LiuYansong��ӿͻ��ڲ�����
      //if(!(request.getParameter("SequenceNo")==null||request.getParameter("SequenceNo").equals("")))
      //{
        //  tLCPolSchema.setSequenceNo(request.getParameter("SequenceNo"));
        loggerDebug("ProposalSave","add by LiuYansong 2004-11-02");
        //}

        // add for GrpAddName
        tLCPolSchema.setMasterPolNo(request.getParameter("MasterPolNo"));
        loggerDebug("ProposalSave","MasterPolNo:" + request.getParameter("MasterPolNo"));

        loggerDebug("ProposalSave","���ñ���������Ϣ...");

        // ������Ϣ����

        if(request.getParameter("PolApplyDate")==null||request.getParameter("PolApplyDate").equals(""))
        {
          tLCPolSchema.setPolApplyDate(request.getParameter("CValiDate"));
        }
        else
        {
          tLCPolSchema.setPolApplyDate(request.getParameter("PolApplyDate"));
        }
        tLCPolSchema.setRiskCode(request.getParameter("RiskCode"));
        tLCPolSchema.setRiskVersion(request.getParameter("RiskVersion"));
        loggerDebug("ProposalSave","CValidate=="+request.getParameter("CValiDate"));
        tLCPolSchema.setCValiDate(request.getParameter("CValiDate"));             //������Ч����
        //tLCPolSchema.setPolApplyDate(request.getParameter("CValiDate"));          //Ͷ������������
        tLCPolSchema.setHealthCheckFlag(request.getParameter("HealthCheckFlag")); //�Ƿ�����
        //tLCPolSchema.setOutPayFlag(request.getParameter("OutPayFlag"));           //�罻����ʽ
        tLCPolSchema.setPayLocation(request.getParameter("PayLocation"));         //�շѷ�ʽ
        //tLCPolSchema.setBankCode(request.getParameter("BankCode"));               //������
        //tLCPolSchema.setBankAccNo(request.getParameter("BankAccNo"));             //�����ʺ�
        //tLCPolSchema.setAccName(request.getParameter("AccName"));                 //����
        tLCPolSchema.setLiveGetMode(request.getParameter("LiveGetMode"));         //���汣�ս���ȡ��ʽ
        loggerDebug("ProposalSave","init lcol1 ") ;                                                                         //��ȡ���ޣ�ͨ��TRANSFERDATA����
        //��ȡ��ʽ��ͨ��TRANSFERDATA����

        //������㷽��Ϊ�����㱣��򱣴���㷽�������ŵ���ӡʱ��ӡ����
        //String mPremToAmnt=request.getParameter("PremToAmnt");
        //if(mPremToAmnt!=null&&mPremToAmnt.equals("O")){
          //  tLCPolSchema.setPremToAmnt(mPremToAmnt);
          //}
          
          loggerDebug("ProposalSave","@@@@@@@@prem:"+request.getParameter("Prem"));
          tLCPolSchema.setMult(request.getParameter("Mult"));                       //����
          tLCPolSchema.setPrem(request.getParameter("Prem"));                       //����
          tLCPolSchema.setAmnt(request.getParameter("Amnt"));                       //����
          if(request.getParameter("RnewFlag")==null||request.getParameter("RnewFlag").equals(""))
          {
            //�������û������ѡ���Ĭ�����Զ�����-��̨�жϣ���������ֿ����������򲻱䣻�粻�����������Ϊ-2������
            //��Ϊ���ֶ�����Ϊint�����Բ�������̨Ĭ����0-�˹��������г�ͻ�������޸�
            tLCPolSchema.setRnewFlag("-1");

          }
          else
          {
            tLCPolSchema.setRnewFlag(request.getParameter("RnewFlag"));               //
          }
          //tLCPolSchema.setSpecifyValiDate(request.getParameter("SpecifyValiDate")); //�Ƿ�ָ����Ч����
          tLCPolSchema.setSpecifyValiDate("N");
          tLCPolSchema.setPayIntv(request.getParameter("PayIntv"));
          tLCPolSchema.setFloatRate(request.getParameter("FloatRate"));             //
          //tLCPolSchema.setGetPolMode(request.getParameter("GetPolMode"));           //�����ʹ﷽ʽ
          tLCPolSchema.setBonusMan(request.getParameter("BonusMan"));               //������ȡ��
          tLCPolSchema.setBonusGetMode(request.getParameter("BonusGetMode"));       //������ȡ��ʽ
          tLCPolSchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));       //�ڲ�����
          loggerDebug("ProposalSave","��־======"+request.getParameter("StandbyFlag1"));
          tLCPolSchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
          tLCPolSchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));       //��ӡ��־printflag
          //tLCPolSchema.setStandbyFlag4(request.getParameter("StandbyFlag4"));       //���б�����foreighpol


          //add by guoxiang at 2004-9-7 17:14 for  ��ҵ �Ĺ���ѱ����洢
          tLCPolSchema.setManageFeeRate(request.getParameter("ManageFeeRate"));       //����ѱ���
          //add end
          
          tLCPolSchema.setPayRuleCode(request.getParameter("PayRuleCode"));
          tLCPolSchema.setAscriptionRuleCode(request.getParameter("AscriptionRuleCode"));
          tLCPolSchema.setAutoPubAccFlag(request.getParameter("AutoPubAccFlag"));

          //add by HL @20050521 ���Ӳ�ɥʧ��ֵѡ��
          tLCPolSchema.setKeepValueOpt(request.getParameter("KeepValueOpt"));
          loggerDebug("ProposalSave","LCPol.Encode=" + tLCPolSchema.encode());
          loggerDebug("ProposalSave","LCPol Information ---------------------- End");
          //end add




          loggerDebug("ProposalSave","init LCDuty ");

          //����ҳ����Ϣδ����
          tLCDutySchema.setPayIntv(request.getParameter("PayIntv"));                //���ѷ�ʽ
          loggerDebug("ProposalSave","payIntv:"+request.getParameter("PayIntv"));
          if (ttDutyCode != null && !"".equals(ttDutyCode))
          {
              tLCDutySchema.setDutyCode(ttDutyCode);
          }
          tLCDutySchema.setCurrency(tCurrencyCode);// ��Ӷ������Ϣ
          tLCDutySchema.setInsuYear(request.getParameter("InsuYear"));              //�����ڼ�
          tLCDutySchema.setInsuYearFlag(request.getParameter("InsuYearFlag"));
          tLCDutySchema.setPayEndYear(request.getParameter("PayEndYear"));          //��������
          tLCDutySchema.setPayEndYearFlag(request.getParameter("PayEndYearFlag"));
          tLCDutySchema.setGetYear(request.getParameter("GetYear"));                //���ʼ��ȡ����
          tLCDutySchema.setGetYearFlag(request.getParameter("GetYearFlag"));
          loggerDebug("ProposalSave","getyear:"+request.getParameter("GetYear")+"GetYearFlag:"+request.getParameter("GetYearFlag"));
          tLCDutySchema.setGetStartType(request.getParameter("GetStartType"));
          tLCDutySchema.setCalRule(request.getParameter("CalRule"));
          tLCDutySchema.setFloatRate(request.getParameter("FloatRate"));
          loggerDebug("ProposalSave","ǰ̨FloatRate:"+request.getParameter("FloatRate"));
          tLCDutySchema.setPremToAmnt(request.getParameter("PremToAmnt"));
          tLCDutySchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));
          tLCDutySchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
          tLCDutySchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));
          tLCDutySchema.setPrem(request.getParameter("Prem"));
          tLCDutySchema.setAmnt(request.getParameter("Amnt"));
          tLCDutySchema.setMult(request.getParameter("Mult"));
          tLCDutySchema.setGetLimit(request.getParameter("GetLimit"));
          loggerDebug("ProposalSave","GetLimit:"+request.getParameter("GetLimit"));
          tLCDutySchema.setGetRate(request.getParameter("GetRate"));
          tLCDutySchema.setSSFlag(request.getParameter("SSFlag"));
          tLCDutySchema.setPeakLine(request.getParameter("PeakLine"));
          
          loggerDebug("ProposalSave","tLCDutySchema.Encode=" + tLCDutySchema.encode());

          loggerDebug("ProposalSave","���ñ�����������Ϣ...");
          loggerDebug("ProposalSave","init LCAppntInd ");

          // Ͷ������Ϣ����
          // ����Ͷ����
          LCAppntSchema tLCAppntSchema = new LCAppntSchema();
          tLCAppntSchema.setContNo(tLCContSchema.getContNo());
          tLCAppntSchema.setAppntNo(request.getParameter("AppntCustomerNo"));  //�ͻ���
          tLCAppntSchema.setAppntName(request.getParameter("AppntName"));              //����
          tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));                //�Ա�
          tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));      //��������
          //���䣬���ύ
          //      tLCAppntSchema.setAppntIDType(request.getParameter("AppntIDType"));          //֤������
          //      tLCAppntSchema.setAppntIDNo(request.getParameter("AppntIDNo"));              //֤������
          //      tLCAppntSchema.setNativePlace(request.getParameter("AppntNativePlace"));//����
          //      tLCAppntSchema.setRgtAddress(request.getParameter("AppntRgtAddress"));  //�������ڵ�
          //      tLCAppntSchema.setMarriage(request.getParameter("AppntMarriage"));      //����״��
          //      tLCAppntSchema.setNationality(request.getParameter("AppntNationality"));//����
          //      tLCAppntSchema.setDegree(request.getParameter("AppntDegree"));          //ѧ��
          //      tLCAppntSchema.setRelationToInsured(request.getParameter("AppntRelationToInsured")); //�뱻�����˹�ϵ
          //      tLCAppntSchema.setPostalAddress(request.getParameter("AppntPostalAddress"));         //��ϵ��ַ
          //      tLCAppntSchema.setZipCode(request.getParameter("AppntZipCode"));        //��������
          //      tLCAppntSchema.setHomeAddress(request.getParameter("AppntHomeAddress"));//סַ
          //      tLCAppntSchema.setHomeZipCode(request.getParameter("AppntHomeZipCode"));//סַ��������
          //      tLCAppntSchema.setPhone(request.getParameter("AppntPhone"));            //��ϵ�绰��1��
          //      tLCAppntSchema.setPhone2(request.getParameter("AppntPhone2"));          //��ϵ�绰��2��
          //      tLCAppntSchema.setMobile(request.getParameter("AppntMobile"));          //�ƶ��绰
          //      tLCAppntSchema.setEMail(request.getParameter("AppntEMail"));            //��������
          //      tLCAppntSchema.setGrpName(request.getParameter("AppntGrpName"));        //������λ
          //      tLCAppntSchema.setGrpPhone(request.getParameter("AppntGrpPhone"));      //��λ�绰
          //      tLCAppntSchema.setGrpAddress(request.getParameter("AppntGrpAddress"));  //��λ��ַ
          //      tLCAppntSchema.setGrpZipCode(request.getParameter("AppntGrpZipCode"));  //��λ��������
          //      tLCAppntSchema.setWorkType(request.getParameter("AppntWorkType"));      //ְҵ�����֣�
          //      tLCAppntSchema.setPluralityType(request.getParameter("AppntPluralityType"));        //��ְ�����֣�
          //      tLCAppntSchema.setOccupationType(request.getParameter("AppntOccupationType"));      //ְҵ���
          //      tLCAppntSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));      //ְҵ����
          //      tLCAppntSchema.setSmokeFlag(request.getParameter("AppntSmokeFlag"));    //�Ƿ�����

          loggerDebug("ProposalSave","tLCAppntSchema.Encode=" + tLCAppntSchema.encode());
          // ����Ͷ����
          LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();

          tLCGrpAppntSchema.setCustomerNo(request.getParameter("AppGrpNo"));
          tLCGrpAppntSchema.setGrpContNo(request.getParameter("GrpContNo"));
          loggerDebug("ProposalSave","tLCGrpAppntSchema.Encode=" + tLCGrpAppntSchema.encode());
          /*
          tLCGrpAppntSchema.setGrpAddress(request.getParameter("AppGrpAddress"));
          tLCGrpAppntSchema.setGrpZipCode(request.getParameter("AppGrpZipCode"));
          tLCGrpAppntSchema.setGrpNature(request.getParameter("GrpNature"));
          tLCGrpAppntSchema.setBusinessType(request.getParameter("BusinessType"));
          tLCGrpAppntSchema.setPeoples(request.getParameter("Peoples"));
          tLCGrpAppntSchema.setRgtMoney(request.getParameter("RgtMoney"));
          tLCGrpAppntSchema.setAsset(request.getParameter("Asset"));
          tLCGrpAppntSchema.setNetProfitRate(request.getParameter("NetProfitRate"));
          tLCGrpAppntSchema.setMainBussiness(request.getParameter("MainBussiness"));
          tLCGrpAppntSchema.setCorporation(request.getParameter("Corporation"));
          tLCGrpAppntSchema.setComAera(request.getParameter("ComAera"));

          //������ϵ��һ
          tLCGrpAppntSchema.setLinkMan1(request.getParameter("LinkMan1"));
          tLCGrpAppntSchema.setDepartment1(request.getParameter("Department1"));
          tLCGrpAppntSchema.setHeadShip1(request.getParameter("HeadShip1"));
          tLCGrpAppntSchema.setPhone1(request.getParameter("Phone1"));
          tLCGrpAppntSchema.setE_Mail1(request.getParameter("E_Mail1"));
          tLCGrpAppntSchema.setFax1(request.getParameter("Fax1"));

          //������ϵ�˶�
          tLCGrpAppntSchema.setLinkMan2(request.getParameter("LinkMan2"));
          tLCGrpAppntSchema.setDepartment2(request.getParameter("Department2"));
          tLCGrpAppntSchema.setHeadShip2(request.getParameter("HeadShip2"));
          //tLCGrpAppntSchema.setPhone2(request.getParameter("Phone2"));
          tLCGrpAppntSchema.setE_Mail2(request.getParameter("E_Mail2"));
          tLCGrpAppntSchema.setFax2(request.getParameter("Fax2"));
          tLCGrpAppntSchema.setGetFlag(request.getParameter("GetFlag"));
          tLCGrpAppntSchema.setBankCode(request.getParameter("GrpBankCode"));
          tLCGrpAppntSchema.setBankAccNo(request.getParameter("GrpBankAccNo"));

          loggerDebug("ProposalSave","����Ͷ������Ϣ...");
          */
          // ��������Ϣ
          //LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();

          // ��������
          LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
          //tLCInsuredSchema.setInsuredGrade("M");
          tLCInsuredSchema.setContNo(tLCContSchema.getContNo());
          tLCInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"));  //�ͻ���
          //���150�򸽼��ո��ӵ��ڶ���������
          if(tt!=null&&tt.equals("00150000")&&request.getParameter("RiskCode")!=null&&!request.getParameter("RiskCode").equals("00150000")){
            //��ѯ�ڶ������˵Ŀͻ���
            ExeSQL aExeSQL = new ExeSQL();
            String aSQL ="select customerno from lcinsuredrelated where polno='"+(String)session.getValue("MainRiskPolNo")+"'";
//            SSRS aSSRS = aExeSQL.execSQL(aSQL);
			 SSRS aSSRS = new SSRS();
             TransferData sTransferData3=new TransferData();
             sTransferData3.setNameAndValue("SQL", aSQL);
		     VData sVData3 = new VData();
		     sVData3.add(sTransferData3);
	         BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
	         if(tBusinessDelegate3.submitData(sVData3, "execSQL", "ExeSQLUI"))
	         {
	        	 aSSRS = (SSRS)tBusinessDelegate3.getResult().getObjectByObjectName("SSRS", 0);
	         }
            
            tLCInsuredSchema.setInsuredNo((String)aSSRS.GetText(1,1));
            loggerDebug("ProposalSave","%%%%%%%%%%%Insuredno="+aSSRS.GetText(1,1));
          }
          //      tLCInsuredSchema.setName(request.getParameter("Name"));              //����
          //      tLCInsuredSchema.setSex(request.getParameter("Sex"));                //�Ա�
          //      tLCInsuredSchema.setBirthday(request.getParameter("Birthday"));      //��������
          //���䣬���ύ
          //      tLCInsuredSchema.setIDType(request.getParameter("IDType"));          //֤������
          //      tLCInsuredSchema.setIDNo(request.getParameter("IDNo"));              //֤������
          //      tLCInsuredSchema.setNativePlace(request.getParameter("NativePlace"));//����
          //      tLCInsuredSchema.setRgtAddress(request.getParameter("RgtAddress"));  //�������ڵ�
          //      tLCInsuredSchema.setMarriage(request.getParameter("Marriage"));      //����״��
          //      tLCInsuredSchema.setNationality(request.getParameter("Nationality"));//����
          //      tLCInsuredSchema.setDegree(request.getParameter("Degree"));          //ѧ��
          //      tLCInsuredSchema.setSmokeFlag(request.getParameter("SmokeFlag"));    //�Ƿ�����
          //      tLCInsuredSchema.setPostalAddress(request.getParameter("PostalAddress"));         //��ϵ��ַ
          //      tLCInsuredSchema.setZipCode(request.getParameter("ZipCode"));        //��������
          //      tLCInsuredSchema.setHomeAddress(request.getParameter("HomeAddress"));//סַ
          //      tLCInsuredSchema.setHomeZipCode(request.getParameter("HomeZipCode"));//סַ��������
          //      tLCInsuredSchema.setPhone(request.getParameter("Phone"));            //��ϵ�绰��1��
          //      tLCInsuredSchema.setPhone2(request.getParameter("Phone2"));          //��ϵ�绰��2��
          //      tLCInsuredSchema.setMobile(request.getParameter("Mobile"));          //�ƶ��绰
          //      tLCInsuredSchema.setEMail(request.getParameter("EMail"));            //��������
          //      tLCInsuredSchema.setGrpName(request.getParameter("GrpName"));        //������λ
          //      tLCInsuredSchema.setGrpPhone(request.getParameter("GrpPhone"));      //��λ�绰
          //      tLCInsuredSchema.setGrpAddress(request.getParameter("GrpAddress"));  //��λ��ַ
          //      tLCInsuredSchema.setGrpZipCode(request.getParameter("GrpZipCode"));  //��λ��������
          //      tLCInsuredSchema.setWorkType(request.getParameter("WorkType"));      //ְҵ�����֣�
          //      tLCInsuredSchema.setPluralityType(request.getParameter("PluralityType"));         //��ְ�����֣�
          //      tLCInsuredSchema.setOccupationType(request.getParameter("OccupationType"));       //ְҵ���
          //      tLCInsuredSchema.setOccupationCode(request.getParameter("OccupationCode"));       //ְҵ����

          //����ҳ����Ϣδ����
          //      tLCInsuredSchema.setHealth(request.getParameter("Health"));
          //      tLCInsuredSet.add(tLCInsuredSchema);
        loggerDebug("ProposalSave","tLCInsuredSchema.Encode=" + tLCInsuredSchema.encode());
          loggerDebug("ProposalSave","end set schema ��������Ϣ...");
          /*
          // ������������(δ����)
          String tInsuredNum[] = request.getParameterValues("SubInsuredGridNo");
          String tInsuredCustomerNo[] = request.getParameterValues("SubInsuredGrid1");
          String tInsuredName[] = request.getParameterValues("SubInsuredGrid2");
          String tInsuredSex[] = request.getParameterValues("SubInsuredGrid3");
          String tInsuredBirthday[] = request.getParameterValues("SubInsuredGrid4");
          String tRelationToInsured[] = request.getParameterValues("SubInsuredGrid5");

          int InsuredCount = 0;
          if (tInsuredNum != null) InsuredCount = tInsuredNum.length;

          for (int i = 0; i < InsuredCount; i++)
          {
            if(tInsuredCustomerNo[i]==null || tInsuredCustomerNo[i].equals("")) break;

            tLCInsuredSchema = new LCInsuredSchema();
            tLCInsuredSchema.setInsuredGrade("S");
            tLCInsuredSchema.setCustomerNo(tInsuredCustomerNo[i]);
            tLCInsuredSchema.setName(tInsuredName[i]);
            tLCInsuredSchema.setSex(tInsuredSex[i]);
            tLCInsuredSchema.setBirthday(tInsuredBirthday[i]);
            tLCInsuredSchema.setRelationToInsured(tRelationToInsured[i]);

            tLCInsuredSet.add(tLCInsuredSchema);
          }
          */
          // ��������Ϣ
          LCBnfSet tLCBnfSet = new LCBnfSet();

          String tBnfNum[]   = request.getParameterValues("BnfGridNo");
          String tBnfType[]  = request.getParameterValues("BnfGrid1");
          String tName[]     = request.getParameterValues("BnfGrid2");
          String tSex[]     = request.getParameterValues("BnfGrid3");
          String tIDType[]   = request.getParameterValues("BnfGrid4");
          String tIDNo[]     = request.getParameterValues("BnfGrid5");
          String tBnfRelationToInsured[] = request.getParameterValues("BnfGrid6");
          String tBnfGrade[] = request.getParameterValues("BnfGrid7");
          String tBnfLot[]   = request.getParameterValues("BnfGrid8");
          String tAddress[]  = request.getParameterValues("BnfGrid9");
          String tInsurno[]  = request.getParameterValues("BnfGrid12");//add by yaory
          String tBnfIDExpDate[]  = request.getParameterValues("BnfGrid10");//add by yaory
          
          double tBnfCount = 0;
          double tRateCount = 0;
          double tRateCount1 = 0;
          double tRateCount2 = 0;
          double tRateCount3 = 0;
          double tRateCount4 = 0;
          double tRateCount5 = 0;
          int BnfCount = 0;
          int tInsuredNum=0;
          String tInsuredNo = "";
          if (tBnfNum != null) BnfCount = tBnfNum.length;
          for (int i = 0; i < BnfCount; i++)
          {
            if( tName[i] == null || tName[i].equals( "" )) break;

            LCBnfSchema tLCBnfSchema = new LCBnfSchema();
           //modify by zhangxing �������Ϊ00150000
            if(request.getParameter("RiskCode").equals("00150000") )
            {
               ExeSQL yeSql = new ExeSQL();
               ExeSQL tExeSQL = new ExeSQL();
               SSRS zSSRS = new SSRS();
               SSRS tSSRS = new SSRS();
               String nsql="select sequenceno from lcinsured where contno='"+request.getParameter("ContNo")+"' and insuredno='"+tInsurno[i]+"' "; 
               String tsql="select name,idtype,idno from lcinsured where contno='"+request.getParameter("ContNo")+"' and sequenceno='2'"; 
//               zSSRS = yeSql.execSQL(nsql);
//               tSSRS = tExeSQL.execSQL(tsql);
               TransferData sTransferData4=new TransferData();
		       sTransferData4.setNameAndValue("SQL", nsql);
		       VData sVData4 = new VData();
	           sVData4.add(sTransferData4);
	           BusinessDelegate tBusinessDelegate4=BusinessDelegate.getBusinessDelegate();
	           if(tBusinessDelegate4.submitData(sVData4, "execSQL", "ExeSQLUI"))
	           {
	        	   zSSRS = (SSRS)tBusinessDelegate4.getResult().getObjectByObjectName("SSRS", 0);
	           }
	           sTransferData4.removeByName("SQL");
	           sVData4.clear();
	           sTransferData4.setNameAndValue("SQL", tsql);
	           sVData4.add(sTransferData4);
	           tBusinessDelegate4=BusinessDelegate.getBusinessDelegate();
	           if(tBusinessDelegate4.submitData(sVData4, "execSQL", "ExeSQLUI"))
	           {
	        	   tSSRS = (SSRS)tBusinessDelegate4.getResult().getObjectByObjectName("SSRS", 0);
	           }
               loggerDebug("ProposalSave","��ѯ��������Ϣ====="+nsql);
               loggerDebug("ProposalSave","tSSRS.GetText(1,1):"+tSSRS.GetText(1,1));
               loggerDebug("ProposalSave","tSSRS.GetText(1,1):"+tSSRS.GetText(1,2));
               loggerDebug("ProposalSave","tSSRS.GetText(1,1):"+tSSRS.GetText(1,3));
               if(zSSRS.MaxRow>0 && zSSRS.GetText(1,1).equals("1") && (!tSSRS.GetText(1,1).equals(tName[i]) || !tSSRS.GetText(1,2).equals(tIDType[i]) || !tSSRS.GetText(1,3).equals(tIDNo[i])))
               {
                  	Content = "����150�����������������������Ϊ��һ��������ʱ,��ô������ӦΪ�ڶ���������! " ;
			             	FlagStr = "Fail";
			     %>
			  	          <script language="javascript">    
         	          parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

                    </script>
            <%
	                 return ;
               }
            }
            
               //modify by zhangxing �������Ϊ00146000
            if(request.getParameter("RiskCode").equals("00146000") )
            {
               ExeSQL yeSql = new ExeSQL();
               ExeSQL tExeSQL = new ExeSQL();
               SSRS zSSRS = new SSRS();
               SSRS tSSRS = new SSRS();
               String nsql="select sequenceno from lcinsured where contno='"+request.getParameter("ContNo")+"' and insuredno='"+tInsurno[i]+"' "; 
               String tsql="select name,idtype,idno from lcinsured where contno='"+request.getParameter("ContNo")+"' and sequenceno='1'"; 
//               zSSRS = yeSql.execSQL(nsql);
//               tSSRS = tExeSQL.execSQL(tsql);
               TransferData sTransferData4=new TransferData();
		       sTransferData4.setNameAndValue("SQL", nsql);
		       VData sVData4 = new VData();
	           sVData4.add(sTransferData4);
	           BusinessDelegate tBusinessDelegate4=BusinessDelegate.getBusinessDelegate();
	           if(tBusinessDelegate4.submitData(sVData4, "execSQL", "ExeSQLUI"))
	           {
	        	   zSSRS = (SSRS)tBusinessDelegate4.getResult().getObjectByObjectName("SSRS", 0);
	           }
	           sTransferData4.removeByName("SQL");
	           sVData4.clear();
	           sTransferData4.setNameAndValue("SQL", tsql);
	           sVData4.add(sTransferData4);
	           tBusinessDelegate4=BusinessDelegate.getBusinessDelegate();
	           if(tBusinessDelegate4.submitData(sVData4, "execSQL", "ExeSQLUI"))
	           {
	        	   tSSRS = (SSRS)tBusinessDelegate4.getResult().getObjectByObjectName("SSRS", 0);
	           }
               
               loggerDebug("ProposalSave","��ѯ��������Ϣ====="+nsql);
               loggerDebug("ProposalSave","tSSRS.GetText(1,1):"+tSSRS.GetText(1,1));
               loggerDebug("ProposalSave","tSSRS.GetText(1,1):"+tSSRS.GetText(1,2));
               loggerDebug("ProposalSave","tSSRS.GetText(1,1):"+tSSRS.GetText(1,3));
               if(zSSRS.MaxRow>0 && zSSRS.GetText(1,1).equals("2") && (!tSSRS.GetText(1,1).equals(tName[i]) || !tSSRS.GetText(1,2).equals(tIDType[i]) || !tSSRS.GetText(1,3).equals(tIDNo[i])))
               {
                  	Content = "����146�����������������������Ϊ�ڶ���������ʱ,��ô������ӦΪ��һ��������! " ;
			             	FlagStr = "Fail";
			     %>
			  	          <script language="javascript">    
         	          parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

                    </script>
            <%
	                 return ;
               }
            }
            if(request.getParameter("RiskCode").equals("00146000") || request.getParameter("RiskCode").equals("00150000") || request.getParameter("RiskCode").equals("00144000") )
            {
               
				if(Double.parseDouble(tBnfLot[i])<=1)
				{
				}
				else
				{
					Content = "ͬһ˳�������˵�����ݶ�֮��ֻ�ܵ���1��" ;
					FlagStr = "Fail";
					%>
					<script language="javascript">    
					parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
					</script>
					<%
					return ;
	             }            
            }    

            tLCBnfSchema.setBnfType(tBnfType[i]);
            tLCBnfSchema.setName(tName[i]);
            String tempSex = "";
            tempSex = tSex[i];
            loggerDebug("ProposalSave","tSex[i]:"+tSex[i]);
            if(tempSex==null||tempSex.equals("")||tempSex.equals("null"))
            {
            	tempSex = "0";
            }
            tLCBnfSchema.setSex(tempSex);
            tLCBnfSchema.setIDType(tIDType[i]);
            tLCBnfSchema.setIDNo(tIDNo[i]);
            //tLCBnfSchema.setBirthday(tBirthday[i]);
            tLCBnfSchema.setRelationToInsured(tBnfRelationToInsured[i]);
            tLCBnfSchema.setBnfLot(tBnfLot[i]);
            tLCBnfSchema.setBnfGrade(tBnfGrade[i]);
            loggerDebug("ProposalSave","%%%%%%%%%%%%%%%%%tInsurno[i]=="+tInsurno[i]);
            tLCBnfSchema.setIDExpDate(tBnfIDExpDate[i]);
            tLCBnfSchema.setInsuredNo(tInsurno[i]);//add by yaory
            
                       
            //tLCBnfSchema.setAddress(tAddress[i]);
            //tLCBnfSchema.setZipCode(tZipCode[i]);
            //tLCBnfSchema.setPhone(tPhone[i]);
            loggerDebug("ProposalSave","tLCBnfSchema.Encode=" + tLCBnfSchema.encode());
            tLCBnfSet.add(tLCBnfSchema);
            
          }
			if(request.getParameter("RiskCode").equals("00146000") || request.getParameter("RiskCode").equals("00150000") || request.getParameter("RiskCode").equals("00144000") )
			{
				//�����ж�����ݶÿ���������µ�ÿ������˳�������ݶ�֮��ֻ�ܵ���1
				for(int k=1;k<=5;k++)
				{
				    //�����ÿ������˳�����������ݶ�֮�� �� ÿ������˳��ı����������Ƚ�
					double ttRateCount=0;
					ArrayList ttBnfInsureNo = new ArrayList();
					for (int n = 0; n < BnfCount; n++)
					{
						if(Integer.parseInt(tBnfGrade[n])==k )
						{
							ttRateCount=ttRateCount+Double.parseDouble(tBnfLot[n]);
							String BnfInsurdno=tInsurno[n];
							String IsExist="0";
							for(int m=0;m<ttBnfInsureNo.size();m++)
							{
								if(BnfInsurdno.equals(ttBnfInsureNo.get(m)))
								{
									IsExist="1";
									 break;
								}
							}
							if(IsExist=="0")
							{
								if(!BnfInsurdno.equals(""))
								{
								ttBnfInsureNo.add(BnfInsurdno);	
								}
							}  
							
						}
					}
					loggerDebug("ProposalSave","����˳��Ϊ["+k+"]������ݶ�֮��=="+ttRateCount);
					loggerDebug("ProposalSave","����˳��Ϊ["+k+"]��������������������=="+ttBnfInsureNo.size());
					if(ttRateCount!=ttBnfInsureNo.size())
					{
						loggerDebug("ProposalSave","ÿ���������µ�ÿ������˳�������ݶ�֮��ֻ�ܵ���1");
						Content = "ͬһ˳�������˵�����ݶ�֮��ֻ�ܵ���1��" ;
						FlagStr = "Fail";
						%>
						<script language="javascript">    
						parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
						</script>
						<%
						return ;
					}
					loggerDebug("ProposalSave","------------------");
				}

			}
          loggerDebug("ProposalSave","end set schema ��������Ϣ...");

          // ��֪��Ϣ
          LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();

          String tImpartNum[] = request.getParameterValues("ImpartGridNo");
          String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //��֪���
          String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //��֪����
          String tImpartContent[] = request.getParameterValues("ImpartGrid4");        //��д����
          String tImpartCustomerNoType[] = request.getParameterValues("ImpartGrid5"); //��֪�ͻ�����

          int ImpartCount = 0;
          if (tImpartNum != null) ImpartCount = tImpartNum.length;

          for (int i = 0; i < ImpartCount; i++)  {
            if( !(tImpartCustomerNoType[i].trim().equals( "A" ) || tImpartCustomerNoType[i].trim().equals( "I" )))
            break;

            LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();

            if( tImpartCustomerNoType[i].trim().equals( "A" ))
            tLCCustomerImpartSchema.setCustomerNo( request.getParameter( "AppntCustomerNo" ));
            if( tImpartCustomerNoType[i].trim().equals( "I" ))
            tLCCustomerImpartSchema.setCustomerNo( request.getParameter( "CustomerNo" ));

            tLCCustomerImpartSchema.setCustomerNoType( tImpartCustomerNoType[i] );
            tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
            tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
            tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
             loggerDebug("ProposalSave","tLCCustomerImpartSchema.Encode=" + tLCCustomerImpartSchema.encode());
            tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
          }
         

          loggerDebug("ProposalSave","end set schema ��֪��Ϣ...");

          // �ر�Լ��
          LCSpecSet tLCSpecSet = new LCSpecSet();

          String tSpecNum[] = request.getParameterValues("SpecGridNo");
          String tSpecType[] = request.getParameterValues("SpecGrid1");
          String tSpecCode[] = request.getParameterValues("SpecGrid2");
          String tSpecContent[] = request.getParameterValues("SpecGrid3");

          int SpecCount = 0;
          if (tSpecNum != null) SpecCount = tSpecNum.length;
          for (int i = 0; i < SpecCount; i++)  {
            if(tSpecCode[i]==null || tSpecCode[i].trim().equals("")) break;
            if(tSpecContent[i]==null || tSpecContent[i].trim().equals("")) break;

            LCSpecSchema tLCSpecSchema = new LCSpecSchema();

            tLCSpecSchema.setSpecCode(tSpecCode[i]);
            tLCSpecSchema.setSpecType(tSpecType[i]);
            tLCSpecSchema.setSpecContent(tSpecContent[i]);
            loggerDebug("ProposalSave","tLCSpecSchema.Encode=" + tLCSpecSchema.encode());
            tLCSpecSet.add(tLCSpecSchema);
          }

          loggerDebug("ProposalSave","end set schema ��Լ��Ϣ...");

          //�Ƿ���������
          String tNeedDutyGrid=request.getParameter("inpNeedDutyGrid");
          LCDutySet tLCDutySet=new LCDutySet();
          LCDutySchema tLCDutySchema1=new LCDutySchema();

          if (tNeedDutyGrid.equals("1")) {      //��������
          tLCDutySchema1 = new LCDutySchema();
          tLCDutySet=new LCDutySet();
          String tDutyChk[] = request.getParameterValues("InpDutyGridChk");

          String tDutyCode[] =request.getParameterValues("DutyGrid1");
          String tInsuYear[] =request.getParameterValues("DutyGrid3");
          String tInsuYearFlag[] =request.getParameterValues("DutyGrid4");
          String tPayEndYear[] =request.getParameterValues("DutyGrid5");
          String tPayEndYearFlag[] =request.getParameterValues("DutyGrid6");
          String tGetYearFlag[] =request.getParameterValues("DutyGrid7");
          String tGetYear[] =request.getParameterValues("DutyGrid8");
          String tStandbyFlag1[] =request.getParameterValues("DutyGrid9");
          String tStandbyFlag2[] =request.getParameterValues("DutyGrid10");
          String tStandbyFlag3[] =request.getParameterValues("DutyGrid11");
          String tPremToAmnt[] =request.getParameterValues("DutyGrid12");
          String tPrem[] =request.getParameterValues("DutyGrid13");
          String tAmnt[] =request.getParameterValues("DutyGrid14");
          String tMult[] =request.getParameterValues("DutyGrid15");
          String tCalRule[] =request.getParameterValues("DutyGrid16");
          String tFloatRate[] =request.getParameterValues("DutyGrid17");
          String tPayIntv[] =request.getParameterValues("DutyGrid18");
          String tGetLimit[] =request.getParameterValues("DutyGrid19");
          String tGetRate[] =request.getParameterValues("DutyGrid20");
          String tSSFlag[] =request.getParameterValues("DutyGrid21");
          String tPeakLine[] =request.getParameterValues("DutyGrid22");




          //        String tDutyCode[] =request.getParameterValues("DutyGrid1");
          //        String tDutyPrem1[] =request.getParameterValues("DutyGrid3");
          //        String tDutyGet1[] =request.getParameterValues("DutyGrid4");
          //        String tDutyPayEndYear[] =request.getParameterValues("DutyGrid5");
          //        String tDutyPayEndYearFlag[] =request.getParameterValues("DutyGrid6");
          //        String tDutyGetYear[] =request.getParameterValues("DutyGrid7");
          //        String tDutyGetYearFlag[] =request.getParameterValues("DutyGrid8");
          //        String tDutyInsuYear[] =request.getParameterValues("DutyGrid9");
          //        String tDutyInsuYearFlag[] =request.getParameterValues("DutyGrid10");
          //        String tDutyPayIntv[] =request.getParameterValues("DutyGrid11");
          //

          int DutyCount = 0;
          if (tDutyCode != null) DutyCount = tDutyCode.length;

          //kaishi
          
          for (int i = 0; i < DutyCount; i++)  {
            if(tDutyCode[i]==null || tDutyCode[i].equals("")) break;

            tLCDutySchema1= new LCDutySchema();

            
            System.out.println("tDutyCode[i]____"+tDutyCode[i]+"|tDutyChk[i]_____"+tDutyChk[i]+"|tPrem[i]_____"+tPrem[i]);
            
            //Ͷ�B���f�ܮaƷ���ж�Ҫ��
            String checkSQL = "select count(*) from lmriskapp where riskcode='"+request.getParameter("RiskCode")+"' and risktype3 in ('3','4')";
            ExeSQL checkExe = new ExeSQL();
			      String checkULflag = checkExe.getOneValue(checkSQL);

            if((!tDutyCode[i].equals("")&&tDutyChk[i].equals("1") && checkULflag!=null&&checkULflag.equals("0") )
              || (!tDutyCode[i].equals("") && checkULflag!=null&&!checkULflag.equals("0") ))  {            	
              tLCDutySchema1.setDutyCode(tDutyCode[i]);
              tLCDutySchema1.setInsuYear(tInsuYear[i]);
              tLCDutySchema1.setInsuYearFlag(tInsuYearFlag[i]);
              tLCDutySchema1.setPayEndYear(tPayEndYear[i]);
              tLCDutySchema1.setPayEndYearFlag(tPayEndYearFlag[i]);
              tLCDutySchema1.setGetYearFlag(tGetYearFlag[i]);
              tLCDutySchema1.setGetYear(tGetYear[i]);
              tLCDutySchema1.setStandbyFlag1(tStandbyFlag1[i]);
              tLCDutySchema1.setStandbyFlag2(tStandbyFlag2[i]);
              tLCDutySchema1.setStandbyFlag3(tStandbyFlag3[i]);
              tLCDutySchema1.setPremToAmnt(tPremToAmnt[i]);
              tLCDutySchema1.setPrem(tPrem[i]);
              //tLCDutySchema1.setAmnt(tAmnt[i]);
              tLCDutySchema1.setMult(tMult[i]);
              tLCDutySchema1.setCalRule(tCalRule[i]);
              tLCDutySchema1.setFloatRate(tFloatRate[i]);
              	System.out.println("^^^^^^^^^^^^^^^^^^");
              	System.out.println("^^^^^^^^^^^^^^^^^^");
              	System.out.println("^^^^^^^^^^^^^^^^^^");
              	System.out.println("^^^^^^^^^^^^^^^^^^");
              	System.out.println("tPayIntv[i]=="+tPayIntv[i]);
              if(request.getParameter("RiskCode").equals("00607000"))
              	{
              	System.out.println("tPayIntv[i]=="+tPayIntv[i]);
	              if(tPayIntv[i]!=null&&!tPayIntv[i].equals(""))
		          {
		          	tLCDutySchema1.setPayIntv(tPayIntv[i]);
		          }
	              else
		          {
		             %>
			        <script type="text/javascript">
			        //alert("��δ���뽻�ѷ�ʽ��");
			        parent.fraInterface.afterSubmit('Fail','��δ���뽻�ѷ�ʽ��');
			      
			        </script>
	<%
			        
		          	
	        		FlagStr = "Fail";
	        		System.out.println("FlagStr------------------------"+FlagStr);
	        		
	        		
	        		return;
		          }
		 }
		else
		{
		  //tongmeng 2010-11-16 �����Ͷ����,��Ҫ���⴦��
		  String tSQL = "select count(*) from lmriskapp where riskcode='"+request.getParameter("RiskCode")+"' and risktype3 in ('3','4') " ;
		  ExeSQL tExeSQL = new ExeSQL();
		  String tULflag = "";
		  tULflag = tExeSQL.getOneValue(tSQL);
		  if(tULflag!=null&&!tULflag.equals("0"))
		  {
			  String tSQL_Payintv = "select payintv from lmdutypay where payplancode in "
			             	      + " (select payplancode from lmdutypayrela where dutycode='"+tLCDutySchema1.getDutyCode()+"') ";
			  String tTempPayintv = "";
			  tTempPayintv = tExeSQL.getOneValue(tSQL_Payintv);
			  String SQL = "select count(*) from lmdutyctrl where dutycode='"+tLCDutySchema1.getDutyCode()+"' and fieldname='PayIntv' and ctrltype='P' and inpflag='Y'";
			  int pCount = Integer.parseInt(new ExeSQL().getOneValue(SQL));
			  if(tTempPayintv==null||tTempPayintv.equals("")||pCount>0)
			  {
				  tLCDutySchema1.setPayIntv(request.getParameter("PayIntv"));
			  }
			  else
			  {
				  tLCDutySchema1.setPayIntv(tPayIntv[i]);
			  }
			  
			  // ���yTactics PayEndYear add by kongyan 20110414
			  tSQL = "select count(*) from lmriskapp where riskcode='"+request.getParameter("RiskCode")+"' and risktype3 in ('3','4')";
			  tULflag = tExeSQL.getOneValue(tSQL);
			  if (tULflag!=null&&!tULflag.equals("0")) {
			  System.out.println("-------------------"+request.getParameter("PayEndYear"));
			    tLCDutySchema1.setPayEndYear(request.getParameter("PayEndYear"));
			    tLCDutySchema1.setPayEndYearFlag(request.getParameter("PayEndYearFlag"));
			  }
			  // end by kongyan 
		  }
		  else
		  {
			  tLCDutySchema1.setPayIntv(tPayIntv[i]);
		  }
			
		}
	         
              //tLCDutySchema1.setPayIntv(tPayIntv[i]);
              tLCDutySchema1.setGetLimit(tGetLimit[i]);

              //add by baidan 2011-9-16
              
              if(tLCDutySchema1.encode().substring(1,7).equals("N01002")||tLCDutySchema1.encode().substring(1,7).equals("N01001")
              ||tLCDutySchema1.encode().substring(1,7).equals("N00002")||tLCDutySchema1.encode().substring(1,7).equals("N00001")
              ||tLCDutySchema1.encode().substring(1,7).equals("M05R01")||tLCDutySchema1.encode().substring(1,7).equals("M05R02")
              ){
           	   System.out.println("***************baidan************IBN01�������⴦��ʼ");
           	   tLCDutySchema1.setCurrency(tCurrencyCode);// ��Ӷ������Ϣ
           	   tLCDutySchema1.setPayIntv(request.getParameter("PayIntv"));
           	   tLCDutySchema1.setInsuYear(request.getParameter("InsuYear"));              //�����ڼ�
           	   tLCDutySchema1.setInsuYearFlag(request.getParameter("InsuYearFlag"));
           	   tLCDutySchema1.setPayEndYear(request.getParameter("PayEndYear"));          //��������
           	   tLCDutySchema1.setPayEndYearFlag(request.getParameter("PayEndYearFlag"));
           	   tLCDutySchema1.setGetYear(request.getParameter("GetYear"));                //���ʼ��ȡ����
           	   tLCDutySchema1.setGetYearFlag(request.getParameter("GetYearFlag"));
           	   tLCDutySchema1.setGetStartType(request.getParameter("GetStartType"));
           	   tLCDutySchema1.setCalRule(request.getParameter("CalRule"));
           	   tLCDutySchema1.setFloatRate(request.getParameter("FloatRate"));
           	   tLCDutySchema1.setPremToAmnt(request.getParameter("PremToAmnt"));
           	   tLCDutySchema1.setStandbyFlag1(request.getParameter("StandbyFlag1"));
           	   tLCDutySchema1.setStandbyFlag2(request.getParameter("StandbyFlag2"));
           	   tLCDutySchema1.setStandbyFlag3(request.getParameter("StandbyFlag3"));
           	   //tLCDutySchema1.setAmnt(request.getParameter("Amnt"));
           	   tLCDutySchema1.setMult(request.getParameter("Mult"));
           	   tLCDutySchema1.setGetLimit(request.getParameter("GetLimit"));
                  System.out.println("GetLimit:"+request.getParameter("GetLimit"));
                  tLCDutySchema1.setGetRate(request.getParameter("GetRate"));
                  tLCDutySchema1.setSSFlag(request.getParameter("SSFlag"));
                  tLCDutySchema1.setPeakLine(request.getParameter("PeakLine"));
                  
                  System.out.println("tLCDutySchema1.Encode=" + tLCDutySchema1.encode());
                  System.out.println("***************baidan************IBN01�������⴦�����");
              }
              //add end 2011-9-16

              //            tLCDutySchema1.setDutyCode(tDutyCode[i]);
              //            tLCDutySchema1.setPrem(tDutyPrem1[i]);
              //            tLCDutySchema1.setAmnt(tDutyGet1[i]);
              //            tLCDutySchema1.setPayEndYear(tDutyPayEndYear[i]);
              //            tLCDutySchema1.setPayEndYearFlag(tDutyPayEndYearFlag[i]);
              //            tLCDutySchema1.setGetYear(tDutyGetYear[i]);
              //            tLCDutySchema1.setGetYearFlag(tDutyGetYearFlag[i]);
              //            tLCDutySchema1.setInsuYear(tDutyInsuYear[i]);
              //            tLCDutySchema1.setInsuYearFlag(tDutyInsuYearFlag[i]);
              //            tLCDutySchema1.setPayIntv(tDutyPayIntv[i]);
              //                System.out.println("payintv:"+tDutyPayIntv[i]);
              //                System.out.println("prem:"+tDutyPrem1[i]);
              System.out.println("tLCDutySchema1.Encode=" + tLCDutySchema1.encode());
              tLCDutySet.add(tLCDutySchema1);

            } // end of if
          }
          
          //jieshu
          
          
          // end of for
        } // end of if



        loggerDebug("ProposalSave","end set schema ������Ϣ..."+tNeedDutyGrid);


        //�Ƿ��ж��������
        String tNeedPremGrid="0";
        LCPremSet tLCPremSet=new LCPremSet();
        LCPremSchema tLCPremSchema1=new LCPremSchema();
        LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
        LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();

        try
        {//���񱣷������
        tNeedPremGrid=request.getParameter("inpNeedPremGrid");

        loggerDebug("ProposalSave"," tNeedPremGrid:"+tNeedPremGrid);
        
        
        String tneedPremCurrency=request.getParameter("needPremCurrency");

        loggerDebug("ProposalSave"," tneedPremCurrency:"+tneedPremCurrency);
        if (tNeedPremGrid.equals("1")) {      //�б�����
        tLCPremSchema1 = new LCPremSchema();
        tLCPremSet=new LCPremSet();
        //���α���
        String tPremDutyCode[] =request.getParameterValues("PremGrid1");
        //�ɷ������
        String tPremPayCode[] =request.getParameterValues("PremGrid2");
        //����������
        String tPremPayCodeName[] =request.getParameterValues("PremGrid3");
        //����
        String tPremStandPrem[] =request.getParameterValues("PremGrid4");
        //�������
        String tPremRate[] =request.getParameterValues("PremGrid5");
        //�����ʻ���
        String tPremAccNo[] =request.getParameterValues("PremGrid6");
        //�ʻ��������
        String tPremAccRate[] =request.getParameterValues("PremGrid7");

        //����ѱ��� add by guoxiang 2004-9-7 10:34 ��ʱû��
        //String tManageFeeRate[]=request.getParameterValues("PremGrid8");
        String tPremChk[] = request.getParameterValues("InpPremGridChk");

        //����ռԱ�����ʱ���
        String tSalaryRate[] = request.getParameterValues("PremGrid9");
        //tongmeng 2010-11-29 add
        //����ֽɷ�
				String tPremCurrency[] = request.getParameterValues("PremGrid10");
        double actuPrem=0;
        int PremCount = 0;
        if (tPremDutyCode != null) PremCount = tPremDutyCode.length;
        for (int i = 0; i < PremCount; i++)
        {
          if(tPremDutyCode[i]==null || tPremDutyCode[i].equals("")) break;

          tLCPremSchema1= new LCPremSchema();
          tLCPremToAccSchema = new LCPremToAccSchema();

          if(!tPremDutyCode[i].equals("") && tPremChk[i].equals("1"))
          {
            loggerDebug("ProposalSave"," tPremDutyCode:"+tPremDutyCode[i]);
            //tLCDutySchema.setDutyCode(tPremDutyCode[i]);
            tLCPremSchema1.setDutyCode(tPremDutyCode[i]);
            tLCPremSchema1.setPayPlanCode(tPremPayCode[i]);

            //actuPrem=Double.parseDouble(tPremStandPrem[i])*Double.parseDouble(tPremRate[i]);
            //loggerDebug("ProposalSave"," tPremStandPrem:"+Double.parseDouble(tPremStandPrem[i]));
            //loggerDebug("ProposalSave"," tPremRate:"+Double.parseDouble(tPremRate[i]));
            //loggerDebug("ProposalSave"," actuPrem:"+actuPrem);
            if(request.getParameter("RiskCode").equals("212403")){
                try{
                    if (tPremStandPrem[i] == null || "".equals(tPremStandPrem[i])
                        || Double.parseDouble(tPremStandPrem[i]) <= 0)
                    {
                         %>
        			        <script language="javascript">
        			        parent.fraInterface.afterSubmit('Fail','��������ȷ�ı��ѣ�');			      
        			        </script>
        			     <%           
        	        		FlagStr = "Fail";
        	        		return;
                    }
                }
                catch(Exception ex){
                    %>
        		        <script language="javascript">
        		        parent.fraInterface.afterSubmit('Fail','��������ȷ�ı��ѣ�');			      
        		        </script>
        		     <%           
                		FlagStr = "Fail";
                		return;
                }
            }


            tLCPremSchema1.setStandPrem(tPremStandPrem[i]);   //ÿ���������ϵı�����jsҳ�����
            tLCPremSchema1.setPrem(tPremStandPrem[i]);
            loggerDebug("ProposalSave","--------------prem"+i+":"+tPremStandPrem[i]);
            //tLCPremSchema1.setStandPrem(actuPrem);
            //tLCPremSchema1.setPrem(actuPrem);
            tLCPremSchema1.setRate(tPremRate[i]);

            //add by guoxiang 2004-9-7 10:33 for ����ѱ��� ��ʱû��
            //tLCPremSchema1.setManageFeeRate(tManageFeeRate[i]);

            tLCPremToAccSchema.setDutyCode( tPremDutyCode[i] );
            tLCPremToAccSchema.setPayPlanCode( tPremPayCode[i] );
            tLCPremToAccSchema.setInsuAccNo( tPremAccNo[i] );
            tLCPremToAccSchema.setRate( tPremAccRate[i] );
            
            loggerDebug("ProposalSave","tLCPremToAccSchema.Encode=" + tLCPremToAccSchema.encode());
            loggerDebug("ProposalSave","tLCPremSchema1.Encode=" + tLCPremSchema1.encode());
            //tongmeng 2010-11-29
        	if(tneedPremCurrency!=null&&tneedPremCurrency.equals("1"))
        	{
        		loggerDebug("ProposalSave","tPremCurrency[i]:"+tPremCurrency[i]);
        		tLCPremSchema1.setCurrency(tPremCurrency[i]);
            //tPremCurrency
        	}
        	
            tLCPremSet.add(tLCPremSchema1);
            tLCPremToAccSet.add(tLCPremToAccSchema);

          } // end of if
        } // end of for
      } // end of if

    } catch(Exception ex){loggerDebug("ProposalSave","set schema ��������Ϣ...error !!!");}

    loggerDebug("ProposalSave","end set schema ��������Ϣ...");
    
  //�����ۿ���
    LCDiscountSet tLCDiscountSet=new LCDiscountSet();
    LCDiscountSchema tLCDiscountSchema=new LCDiscountSchema();
    
    String tDiscountChk[] = request.getParameterValues("InpDiscountGridChk");

    String tDiscountCode[] =request.getParameterValues("DiscountGrid1");//�ۿ۱���
    String tDutyCode[] =request.getParameterValues("DiscountGrid5");//���α���
    String tCOrder[] =request.getParameterValues("DiscountGrid8");//�ۿ�˳��

    int DiscountCount = 0;
    if (tDiscountChk != null) DiscountCount = tDiscountChk.length;
    for (int i = 0; i < DiscountCount; i++)  {
      if(tDiscountCode[i]==null || tDiscountCode[i].equals("")) break;

      tLCDiscountSchema=new LCDiscountSchema();

      if(!tDiscountCode[i].equals("") && tDiscountChk[i].equals("1"))  {
    	  tLCDiscountSchema.setContNo(request.getParameter("ContNo"));
    	  tLCDiscountSchema.setPolNo(request.getParameter("ProposalNo"));
    	  tLCDiscountSchema.setDutyCode(tDutyCode[i]);
    	  tLCDiscountSchema.setDiscountCode(tDiscountCode[i]);
    	  tLCDiscountSchema.setCOrder(tCOrder[i]);
    	  
    	  tLCDiscountSet.add(tLCDiscountSchema);
      }
    }
    loggerDebug("ProposalSave","end set schema �ۿ�����Ϣ...");

    //���ݷ�SCHEMA��Ϣ
    TransferData tTransferData = new TransferData();

    tTransferData.setNameAndValue("getIntv", request.getParameter("getIntv"));                //��ȡ�������ʽ��
    tTransferData.setNameAndValue("GetDutyKind", request.getParameter("GetDutyKind"));        //���ʼ��ȡ����
    tTransferData.setNameAndValue("AutoPayFlag", tAutoPayFlag);        //�Զ��潻��־

    
    loggerDebug("ProposalSave","getIntv=" + request.getParameter("getIntv"));
    loggerDebug("ProposalSave","GetDutyKind=" + request.getParameter("GetDutyKind"));
    if (request.getParameter("SamePersonFlag") == null)
    tTransferData.setNameAndValue("samePersonFlag", "0"); //Ͷ����ͬ�����˱�־
    else
    tTransferData.setNameAndValue("samePersonFlag", "1"); //Ͷ����ͬ�����˱�־
     loggerDebug("ProposalSave","SamePersonFlag=" + request.getParameter("SamePersonFlag"));

    if (!ChangePlanFlag)
    {
      tTransferData.setNameAndValue("deleteAccNo", "1");
      tTransferData.setNameAndValue("ChangePlanFlag", "1");
       loggerDebug("ProposalSave","deleteAccNo=" + 1);
        loggerDebug("ProposalSave","deleteAccNo=" + 1);
    }
    else
    {
      tTransferData.setNameAndValue("deleteAccNo", "0");
      tTransferData.setNameAndValue("ChangePlanFlag", "0");
      loggerDebug("ProposalSave","deleteAccNo=" + 0);
        loggerDebug("ProposalSave","deleteAccNo=" + 0);
    }

    tTransferData.setNameAndValue("LoadFlag", request.getParameter("tLoadFlag"));
    tTransferData.setNameAndValue("SavePolType", request.getParameter("BQFlag")); //��ȫ������
    tTransferData.setNameAndValue("EdorType", request.getParameter("EdorType"));
    loggerDebug("ProposalSave","SavePolType : " + request.getParameter("BQFlag"));
    loggerDebug("ProposalSave","LoadFlag=" + request.getParameter("tLoadFlag"));
    loggerDebug("ProposalSave","EdorType=" + request.getParameter("EdorType"));

    // ׼���������� VData
    //tVData.addElement(tLCContSchema);
    //tVData.addElement(tLCPolSchema);
    //tVData.addElement(tLCAppntSchema);
    //tVData.addElement(tLCGrpAppntSchema);
    //tVData.addElement(tLCInsuredSchema);
    LCInsuredRelatedSet mLCInsuredRelatedSet=new LCInsuredRelatedSet();
    ////add by yaory write on 2005-7-6-17:43
    try{
      String tRadio[] = request.getParameterValues("InpInsuredGridSel");

      //loggerDebug("ProposalSave","�����Ǻ�����===="+tRadio.length);
      ////////ȡ������MULTINE��ֵ///
      String tGrid1 [] = request.getParameterValues("InsuredGrid1"); //�õ���1�е�����ֵ
      String tGrid2 [] = request.getParameterValues("InsuredGrid2");
      String tGrid3 [] = request.getParameterValues("InsuredGrid3");
      String tGrid4 [] = request.getParameterValues("InsuredGrid4");
      String tGrid5 [] = request.getParameterValues("InsuredGrid5");
      LCInsuredRelatedSchema tLCInsuredRelatedSchema=new LCInsuredRelatedSchema();
      loggerDebug("ProposalSave","�ܹ�������===="+tRadio.length);
      for(int i=0; i<tRadio.length;i++)
      {
        if(tRadio[i].equals("1"))
        {
          loggerDebug("ProposalSave","�������");
          /////˵��-���������˿ͻ���δ������
          //loggerDebug("ProposalSave","���б�ѡ��=="+ tGrid1 [i]);
          tLCInsuredRelatedSchema.setCustomerNo(tGrid1 [i]);
          //loggerDebug("ProposalSave","���б�ѡ��=="+ tGrid2 [i]);
          tLCInsuredRelatedSchema.setName(tGrid2 [i]);
          //loggerDebug("ProposalSave","���б�ѡ��=="+ tGrid3 [i]);
          tLCInsuredRelatedSchema.setSex(tGrid3 [i]);
          //loggerDebug("ProposalSave","���б�ѡ��=="+ tGrid4 [i]);
          tLCInsuredRelatedSchema.setBirthday(tGrid4 [i]);
          loggerDebug("ProposalSave","���б�ѡ��=="+ tGrid5 [i]);
          tLCInsuredRelatedSchema.setRelationToInsured(tGrid5 [i]);

          loggerDebug("ProposalSave","########################");
          loggerDebug("ProposalSave","########################");
          loggerDebug("ProposalSave","aaaa="+tGrid1 [i]);
          loggerDebug("ProposalSave","########################");
          loggerDebug("ProposalSave","########################");
          tLCPolSchema.setStandbyFlag1(tGrid1 [i]);
          tLCDutySchema.setStandbyFlag1(tGrid1 [i]);
          tLCInsuredRelatedSchema.setOperator(request.getParameter("AgentCode"));
          loggerDebug("ProposalSave","!!!!!!!!!!!!!MakeDate=="+request.getParameter("MakeDate"));
          tLCInsuredRelatedSchema.setMakeDate(request.getParameter("MakeDate"));
          tLCInsuredRelatedSchema.setMakeTime(request.getParameter("MakeTime"));
          tLCInsuredRelatedSchema.setSequenceNo(request.getParameter("SequenceNo"));
          //tLCInsuredRelatedSchema.setSequenceNo("01");
          if(tGrid5 [i].equals("00"))
          {
            loggerDebug("ProposalSave","Ҫ�ľ�����");//�������˿ͻ���
            tLCInsuredRelatedSchema.setMainCustomerNo(tGrid5 [i]);
          }
          tLCInsuredRelatedSchema.setPolNo(request.getParameter("ProposalNo"));
        }
        if(tRadio[i].equals("0"))
        {
          //loggerDebug("ProposalSave","����δ��ѡ��");
          if(tGrid5 [i].equals("00"))
          {
            //loggerDebug("ProposalSave","Ҫ�ľ�����");//�������˿ͻ���
            tLCInsuredRelatedSchema.setMainCustomerNo(tGrid5 [i]);
          }
        }
      }
      for(int i=0; i<tRadio.length;i++)
      {
        if(tRadio[i].equals("1"))
        {
          loggerDebug("ProposalSave","tLCInsuredRelatedSchema.Encode=" + tLCInsuredRelatedSchema.encode());
          mLCInsuredRelatedSet.add(tLCInsuredRelatedSchema);
        }
      }
    }catch(Exception ex){}
    ///end add by yaory 2005-7-6
      loggerDebug("ProposalSave","��־55======"+request.getParameter("StandbyFlag1"));
      loggerDebug("ProposalSave","��־55======"+tLCPolSchema.getStandbyFlag1());
    tVData.addElement(tLCContSchema);
    tVData.addElement(tLCPolSchema);
    tVData.addElement(tLCAppntSchema);
    tVData.addElement(tLCGrpAppntSchema);
    tVData.addElement(tLCInsuredSchema);
    tVData.addElement(mLCInsuredRelatedSet);
    tVData.addElement(tLCBnfSet);
    tVData.addElement(tLCCustomerImpartSet);
    tVData.addElement(tLCSpecSet);
    tVData.addElement(tG);
    tVData.addElement(tTransferData);
    
    loggerDebug("ProposalSave","tLCPolSchema:"+tLCPolSchema.encode());
    
    loggerDebug("ProposalSave","tLCDutySchema:"+tLCDutySchema.encode());
    
    loggerDebug("ProposalSave","tLCDutySet:"+tLCDutySet.encode());

    //����ǿ�ѡ������
    if (("1").equals(tNeedDutyGrid))
    tVData.addElement(tLCDutySet);
    else
    tVData.addElement(tLCDutySchema);
    loggerDebug("ProposalSave","tNeedDutyGrid=" + tNeedDutyGrid);

    //����ǿ�ѡ������
    if (tNeedPremGrid!=null&&tNeedPremGrid.equals("1"))
    {
      tVData.addElement(tLCPremSet);
      tVData.addElement(tLCPremToAccSet);
    }
    loggerDebug("ProposalSave","tNeedPremGrid=" + tNeedPremGrid);
    
    tVData.addElement(tLCDiscountSet);

  } // end of if




  // ���ݴ���

  if( !tBusinessDelegate2.submitData( tVData, tOperate,busiName2 ) ) {
    tError = tBusinessDelegate2.getCErrors();
    Content = tError.getFirstError();
    FlagStr = "Fail";
  }
  else {
    Content = "����ɹ�!"; 
    FlagStr = "Succ";

    tVData.clear();
    tVData = tBusinessDelegate2.getResult();

    // ��ʾ
    // ������Ϣ
    mLCPolSchema.setSchema(( LCPolSchema )tVData.getObjectByObjectName( "LCPolSchema", 0 ));
    mLCDutyBLSet.set((LCDutyBLSet)tVData.getObjectByObjectName( "LCDutyBLSet", 0 ));
    ///////////add by yaory -2005-7-13 �����������---
    loggerDebug("ProposalSave","������===="+tOperate);
    if(tOperate.equals("INSERT||PROPOSAL"))
    {
      ExeSQL rexeSql = new ExeSQL();
      SSRS riskFlagSSRS = new SSRS();
//      riskFlagSSRS = rexeSql.execSQL("select subriskflag from lmriskapp where riskcode in (select riskcode from lcpol where polno='"+mLCPolSchema.getPolNo()+"')");
      String sql = "select subriskflag from lmriskapp where riskcode in (select riskcode from lcpol where polno='"+mLCPolSchema.getPolNo()+"')";
      TransferData sTransferData5=new TransferData();
      sTransferData5.setNameAndValue("SQL", sql);
      VData sVData5 = new VData();
      sVData5.add(sTransferData5);
      BusinessDelegate tBusinessDelegate5=BusinessDelegate.getBusinessDelegate();
      if(tBusinessDelegate5.submitData(sVData5, "execSQL", "ExeSQLUI"))
      {
    	  riskFlagSSRS = (SSRS)tBusinessDelegate5.getResult().getObjectByObjectName("SSRS", 0);
      }
      
      if(riskFlagSSRS.GetText(1,1).equals("M"))
      {
        loggerDebug("ProposalSave","����==="+(String)session.getValue("MainRiskPolNo"));
        session.putValue("MainRiskPolNo",mLCPolSchema.getPolNo());
      }
    }
    else{
      loggerDebug("ProposalSave","���Ǹ����վ�����������===="+(String)session.getValue("MainRiskPolNo"));
      //session.putValue("MainRiskPolNo",null);
    }
    /////////end add by yaory
  }
  //��������������ֱ��ǩ��
  try  {
    if (FlagStr.equals("Succ") && request.getParameter("BQFlag")!=null && request.getParameter("BQFlag").equals("4")) {
      loggerDebug("ProposalSave","\nNewProposalNo:" + mLCPolSchema.getProposalNo());

      GrpAddNameSignBL tGrpAddNameSignBL = new GrpAddNameSignBL();
      if (!tGrpAddNameSignBL.dealSign(mLCPolSchema, tG)) {
        Content = "������������ǩ��ʧ�ܣ�ԭ����: " + tGrpAddNameSignBL.mErrors.getError(0).errorMessage;
        FlagStr = "Fail";
      }
      else {
        Content = "���������������桢�˱���ǩ���ɹ���";
}
    } // end of if
  }
  catch ( Exception e2 ) {
    Content = "������������ǩ��ʧ�ܣ�ԭ����: " + e2.toString();
    FlagStr = "Fail";
  }

  //tongmeng 2008-12-18 add
  //�޸�Ͷ����,��Ҫ�ָ���������
  if(ReSaveUWFlag)
  {
	  	LCPolSchema tLCPolSchema = new LCPolSchema();
	    tLCPolSchema.setPolNo(request.getParameter("ProposalNo"));
	    tLCPolSchema.setApproveFlag(approveFlag);
	    tLCPolSchema.setUWFlag(UWFlag);
	    tLCPolSchema.setApproveCode(approveCode);
	    tLCPolSchema.setApproveDate(approveDate);

	    VData VData3 = new VData();
	    VData3.add(tLCPolSchema);
		 	VData3.add(tG);
		 	QueryPolInfoBL tQueryPolInfoBL = new QueryPolInfoBL();
	    if (!tQueryPolInfoBL.submitData(VData3, "INSERT")) {
	      Content = "����ʧ�ܣ�ԭ����: " + tQueryPolInfoBL.mErrors.getError(0).errorMessage;
	      FlagStr = "Fail";
	      throw new Exception();
	    }
  }
  //�б��ƻ�������ָ�Ͷ����״̬
  if (ChangePlanFlag) {
    LCPolSchema tLCPolSchema = new LCPolSchema();
    tLCPolSchema.setPolNo(request.getParameter("ProposalNo"));
    tLCPolSchema.setApproveFlag(approveFlag);
    tLCPolSchema.setUWFlag(UWFlag);
    tLCPolSchema.setApproveCode(approveCode);
    tLCPolSchema.setApproveDate(approveDate);

    VData VData3 = new VData();
    VData3.add(tLCPolSchema);
	 	VData3.add(tG);
    ChangePlanUI tChangePlanUI = new ChangePlanUI();
    if (!tChangePlanUI.submitData(VData3, "INSERT||CHANGEPLAN")) {
      Content = "����ʧ�ܣ�ԭ����: " + tChangePlanUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
      throw new Exception();
    }

  }

} // end of try
catch( Exception e1 )  {
  Content = " ����ʧ�ܣ�ԭ����: " + e1.toString().trim();
  FlagStr = "Fail";
}

if(FlagStr.equals("Succ")&&!tAction.equals( "DELETE" ))  {
  %>
  <script language="javascript">
  	 //tongmeng 2008-07-03 modify
          //���ӶԶ�������Ϣ�ĳ�ʼ��
//          alert(<%=triskcode%>);
   var tFLag=<%=request.getParameter("LoadFlag")%>
   //alert(tFLag);
   if(tFLag!='7'&& tFLag!='8'&& tFLag!=null)
   {
      parent.fraInterface.showMultRiskGrid('<%=triskcode%>');
   }
     var dutyArray = new Array();
    </script>
    <%
    loggerDebug("ProposalSave",mLCDutyBLSet.encode());
    for (int j=1;j<=mLCDutyBLSet.size();j++)
    {
      %>
      <script language="javascript">
        dutyArray[<%=j-1%>]=new Array();
        dutyArray[<%=j-1%>][0]='<%=mLCDutyBLSet.get(j).getDutyCode()%>';
        dutyArray[<%=j-1%>][1]='<%=mDecimalFormat.format(mLCDutyBLSet.get(j).getPrem())%>';
        dutyArray[<%=j-1%>][2]='<%=mDecimalFormat.format(mLCDutyBLSet.get(j).getAmnt())%>';
        </script>
        <%
        }
        %>
        <script language="javascript">
          try
          {

            if (parent.fraInterface.document.all('inpNeedDutyGrid').value==1)
            {
              var mulLineCount=parent.fraInterface.DutyGrid.mulLineCount;
              //alert("muLineCount=" + mulLineCount);
              for (i=0;i<mulLineCount;i++)
              {
                var dutycode=parent.fraInterface.DutyGrid.getRowColData(i,1);
                for (j=0;j<dutyArray.length;j++)
                {
                  if (dutyArray[j][0]==dutycode)
                  {
                    parent.fraInterface.DutyGrid.setRowColData(i,13,dutyArray[j][1]);
                    parent.fraInterface.DutyGrid.setRowColData(i,14,dutyArray[j][2]);
                    //alert("here");
                  }
                }

              }
            }
            else
            {
              parent.fraInterface.document.all("Prem").value = "<%=PubFun.round(mLCPolSchema.getPrem(),2)%>";

              parent.fraInterface.document.all("Amnt").value = "<%=PubFun.round(mLCPolSchema.getAmnt(),2)%>";
              
              try
              {
                parent.fraInterface.document.all("FloatRate").value = "<%=PubFun.round(mLCPolSchema.getFloatRate(),2)%>"
              }
              catch(ex){}
              //alert("no duty here");

            }
            
            parent.fraInterface.document.all("ContNo").value = "<%=mLCPolSchema.getContNo()%>";
            //parent.fraInterface.document.all("GrpPolNo").value = "<%=mLCPolSchema.getGrpPolNo()%>";
            parent.fraInterface.document.all("ProposalNo").value = "<%=mLCPolSchema.getProposalNo()%>";
            //alert("ProposalNo=" + parent.fraInterface.document.all("ProposalNo").value);

            parent.fraInterface.document.all("ContType").value = "<%=mLCPolSchema.getContType()%>";
            //alert("ContType=" + parent.fraInterface.document.all("ContType").value);
            if(parent.VD.gVSwitch.addVar("mainRiskPolNo","","<%=mLCPolSchema.getMainPolNo()%>")==false){
              parent.VD.gVSwitch.updateVar("mainRiskPolNo","","<%=mLCPolSchema.getMainPolNo()%>");
            }
            //alert("LoadFlag=" + parent.fraInterface.LoadFlag);
            //¼���޸�
            if (parent.fraInterface.LoadFlag == 2) {
			  //parent.fraInterface.document.all("EndDate").value = "<%=mLCPolSchema.getEndDate()%>";
              //alert("EndDate=" + parent.fraInterface.document.all("EndDate").value);
              parent.fraInterface.inputButton.style.display = "none";
              parent.fraInterface.divButton.style.display = "";
              parent.fraInterface.modifyButton.style.display = "";
              //parent.fraInterface.riskbutton31.disabled='';
              //parent.fraInterface.riskbutton32.disabled='';

            }
          } catch(ex) {
            alert("after Save but happen err:" + ex);
          }       
          </script>
	        
          <%
          } // end of if

          loggerDebug("ProposalSave",Content);

          //�滻��������Ϣ�������ַ�
          Content = Content.replace('\n', ' ');

          while (Content.indexOf("\"") != -1) {
            Content = Content.replace('\"', ' ');
            //loggerDebug("ProposalSave",Content);
          }


          loggerDebug("ProposalSave",Content);
          loggerDebug("ProposalSave",FlagStr);
          
         
          
          %>

          <html>
            <script language="javascript">
              try
              {
                parent.fraInterface.afterSubmita('<%=FlagStr%>','<%=Content%>');
                //tongmeng 2011-02-18 modifyButton
               // alert('<%=FlagStr%>');
                if('<%=FlagStr%>'!='Fail')
                {
                	 parent.fraInterface.afterSubmitQuery('<%=mLCPolSchema.getPolNo()%>');
                }
              }  catch(ex) {
                //alert("after Save but happen err:" + ex);
              }
              </script>
              <%
              if(( tAction.equals( "INSERTPERSON" ) || tAction.equals( "UPDATEPERSON" )) && FlagStr.equals( "Succ" ))
              {
                %>
                <script language="javascript">
                  try {
                    var prtNo = "<%=mLCPolSchema.getPrtNo()%>";
                    if (prtNo.substring(2, 4) == "11") {
                      parent.fraPic.goToPic(3); top.fraPic.scrollTo(0, 0);
                    }
                    else if (prtNo.substring(2, 4) == "15") {
                      parent.fraPic.goToPic(0); top.fraPic.scrollTo(300, 2000);
                    }
                    //parent.fraInterface.showFee();
                  }
                  catch(ex) {}
                  </script>
                  <%
                  } // end of if
                } //add by yaory for //�����жϣ����û��¼�����ղ���¼�븽���� write by yaory
%>
</html>

