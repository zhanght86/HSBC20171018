<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�ProposalSave.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="com.sinosoft.service.*" %>
<%


  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String tAction = "";
  String tOldAction = "";
  String tOperate = "";
  boolean ChangePlanFlag = false;
  String approveFlag = "";
  String UWFlag = "";
  String approveCode = "";
  String approveDate = "";
  String tLoadFlag = "";
  String mLoadFlag = "";
  String remark="";
  tAction = request.getParameter( "fmAction" );
  if( tAction.equals( "INSERTPERSON" )) tOperate = "INSERT||PROPOSAL";
  if( tAction.equals( "INSERTGROUP" )) tOperate = "INSERT||PROPOSAL";
  if( tAction.equals( "UPDATEPERSON" )) tOperate = "UPDATE||PROPOSAL";
  if( tAction.equals( "UPDATEGROUP" )) tOperate = "UPDATE||PROPOSAL";
  if( tAction.equals( "DELETE" )) tOperate = "DELETE||PROPOSAL";
  //loggerDebug("ProposalSave","asdasdsd");
  //�����жϣ����û��¼�����ղ���¼�븽���� write by yaory
    String triskcode=request.getParameter("RiskCode");
    
   String tt=(String)session.getValue("MainRiskNo");


    if(!remark.equals("1")){
      //end �ж�
      loggerDebug("ProposalSave","save....111111");
      LCPolSchema mLCPolSchema = new LCPolSchema();
      LCDutyBLSet mLCDutyBLSet=new LCDutyBLSet();
      try  {
    	   String busiName1="tbProposalUI";
    	   BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
       // ProposalUI tProposalUI  = new ProposalUI();
        GlobalInput tG = new GlobalInput();
        tG = ( GlobalInput )session.getValue( "GI" );

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
          if (tAction.indexOf("ChangePlan") != -1) //�ڳб��ƻ���������汣��ʱ����ȡͶ����״̬
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
            String busiName2="cbcheckgrpChangePlanUI";
            BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
           // ChangePlanUI tChangePlanUI = new ChangePlanUI();
            //���̨��������
            if (!tBusinessDelegate2.submitData(VData2, "QUERY||CHANGEPLAN",busiName2))
            {  //����ʧ��ʱ
            Content = "����ʧ�ܣ�ԭ����: " + tBusinessDelegate2.getCErrors().getError(0).errorMessage;
            FlagStr = "Fail";
            throw new Exception();
          }
          else
          { //�����ɹ�ʱ
          VData vResult = tBusinessDelegate2.getResult();
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
        
        String busiName3="pubfunLockTableUI";
        BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
        //LockTableUI LockTableUI1 = new LockTableUI();
        if (!tBusinessDelegate3.submitData(VData3, "INSERT",busiName3))
        {
          VData rVData = tBusinessDelegate3.getResult();
          loggerDebug("ProposalSave","LockTable Failed! " + (String)rVData.get(0));
        }
        else
        {
          loggerDebug("ProposalSave","LockTable Succed!");
        }
      }

      if (tAction.indexOf("Modify") != -1)
      tAction = tAction.substring(6);
      loggerDebug("ProposalSave","tAction" + tAction);
      LCContSchema tLCContSchema = new LCContSchema();
      tLCContSchema.setContNo(request.getParameter("ContNo"));
      loggerDebug("ProposalSave","ContNo"+request.getParameter("ContNo"));
      // ������Ϣ����
      LCPolSchema tLCPolSchema = new LCPolSchema();
      LCDutySchema tLCDutySchema = new LCDutySchema();

      tLCPolSchema.setProposalNo(request.getParameter("ProposalNo"));
      tLCPolSchema.setPrtNo(request.getParameter("PrtNo"));
      tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
      //tLCPolSchema.setManageCom(tG.ManageCom);
      //tLCPolSchema.setSaleChnl(request.getParameter("SaleChnl"));
      //��ѯ�����������������Ϊ3,���������Ϊ1 ����Ϊ2
      LMRiskAppDB aLMRiskAppDB = new LMRiskAppDB();
      aLMRiskAppDB.setRiskCode(triskcode);
      aLMRiskAppDB.getInfo();
      tLCPolSchema.setSaleChnl("1");
      if(aLMRiskAppDB.getRiskProp()!=null&&aLMRiskAppDB.getRiskProp().equals("I")){
        tLCPolSchema.setSaleChnl("1");
      
      }
      else if(aLMRiskAppDB.getRiskProp()!=null&&aLMRiskAppDB.getRiskProp().equals("Y")){
        tLCPolSchema.setSaleChnl("3");
      
      }
      else if(aLMRiskAppDB.getRiskProp()!=null&&aLMRiskAppDB.getRiskProp().equals("G")){
        tLCPolSchema.setSaleChnl("2");
      
      }
      
      tLCPolSchema.setAgentCom(request.getParameter("AgentCom"));
      tLCPolSchema.setAgentType(request.getParameter("AgentType"));
      tLCPolSchema.setAgentCode(request.getParameter("AgentCode"));
      tLCPolSchema.setAgentGroup(request.getParameter("AgentGroup"));
      tLCPolSchema.setHandler(request.getParameter("Handler"));
      tLCPolSchema.setAgentCode1(request.getParameter("AgentCode1"));
      tLCPolSchema.setInsuredAppAge(request.getParameter("InsuredAppAge")); //������Ͷ������
      //loggerDebug("ProposalSave",">>>>>>>>>>>>"+request.getParameter("InsuredAppAge"));
      tLCPolSchema.setInsuredPeoples(request.getParameter("InsuredPeoples")); //����������
      loggerDebug("ProposalSave","****����**"+request.getParameter("InsuredPeoples"));
      tLCPolSchema.setPolTypeFlag(request.getParameter("PolTypeFlag")); //�������ͱ��
      //loggerDebug("ProposalSave",">>>>>>>>>>>>"+request.getParameter("PolTypeFlag"));
      tLCPolSchema.setContNo(request.getParameter("ContNo"));
      //tLCPolSchema.setGrpPolNo(request.getParameter("GrpPolNo"));
      tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
      //loggerDebug("ProposalSave","000000000000000="+request.getParameter("GrpContNo")); //yaory
      //��tLCPolSchema���жϵı�־������̨-����Ӹ�����֮ǰ�Ƿ����������
      //tLCPolSchema.setKeepValueOpt(remark); //��ȫ�����������
      //tLCPolSchema.setMainPolNo(request.getParameter("MainPolNo"));
      
     
      
        tLCPolSchema.setMainPolNo(request.getParameter("MainPolNo"));
      
      
      tLCPolSchema.setFirstPayDate(request.getParameter("FirstPayDate"));
      //tLCPolSchema.setLang(request.getParameter("Lang"));
      //tLCPolSchema.setCurrency(request.getParameter("Currency"));
      //tLCPolSchema.setDisputedFlag(request.getParameter("DisputedFlag"));
      tLCPolSchema.setAgentPayFlag(request.getParameter("AgentPayFlag"));
      tLCPolSchema.setAgentGetFlag(request.getParameter("AgentGetFlag"));
      tLCPolSchema.setRemark(request.getParameter("Remark"));
      


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
       String tCValiDate = request.getParameter("CValiDate");
        tLCPolSchema.setCValiDate(tCValiDate);             //������Ч����
        if(tCValiDate!=null&&!tCValiDate.equals("")&&!tCValiDate.equals("null"))
        {
        	tLCPolSchema.setSpecifyValiDate("Y");
        }
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
         // tLCPolSchema.setSpecifyValiDate("N");
          tLCPolSchema.setPayIntv(request.getParameter("PayIntv"));
           loggerDebug("ProposalSave","��־======");
          tLCPolSchema.setFloatRate(request.getParameter("FloatRate"));             //
          
          tLCPolSchema.setInsuYearFlag(request.getParameter("InsuYearFlag"));
          tLCPolSchema.setInsuYear(request.getParameter("InsuYear")); 
          //20050926 yaory endinit
          tLCPolSchema.setBonusMan(request.getParameter("BonusMan"));               //������ȡ��
          tLCPolSchema.setBonusGetMode(request.getParameter("BonusGetMode"));       //������ȡ��ʽ
          tLCPolSchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));       //�ڲ�����
          loggerDebug("ProposalSave","��־======"+request.getParameter("StandbyFlag1"));
          tLCPolSchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
          tLCPolSchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));       //��ӡ��־printflag
          tLCPolSchema.setGetYear(request.getParameter("GetYear"));                //���ʼ��ȡ����
          tLCPolSchema.setGetYearFlag(request.getParameter("GetYearFlag"));
          //tLCPolSchema.setStandbyFlag4(request.getParameter("StandbyFlag4"));       //���б�����foreighpol


          // ��ҵ �Ĺ���ѱ����洢
          tLCPolSchema.setManageFeeRate(request.getParameter("ManageFeeRate"));       //����ѱ���
          //add end

      
          tLCPolSchema.setKeepValueOpt(request.getParameter("KeepValueOpt"));
      




          loggerDebug("ProposalSave","init LCDuty ");

          //����ҳ����Ϣδ����
          tLCDutySchema.setPayIntv(request.getParameter("PayIntv"));                //���ѷ�ʽ
          loggerDebug("ProposalSave","payIntv:"+request.getParameter("PayIntv"));
        
          tLCDutySchema.setInsuYear(request.getParameter("InsuYear"));              //�����ڼ�
        
          tLCDutySchema.setInsuYearFlag(request.getParameter("InsuYearFlag"));
          loggerDebug("ProposalSave",request.getParameter("InsuYearFlag"));
          
          tLCDutySchema.setPayEndYear(request.getParameter("PayEndYear"));          //��������
          
          tLCDutySchema.setPayEndYearFlag(request.getParameter("PayEndYearFlag"));
       
          tLCDutySchema.setGetYear(request.getParameter("GetYear"));                //���ʼ��ȡ����
          loggerDebug("ProposalSave","6"+request.getParameter("GetYear"));
          tLCDutySchema.setGetYearFlag(request.getParameter("GetYearFlag"));
          tLCDutySchema.setGetIntv(request.getParameter("GetIntv"));  //����ʹ�õ�������ȡ��ʽ
          loggerDebug("ProposalSave","getyear:"+request.getParameter("GetYear")+"GetYearFlag:"+request.getParameter("GetYearFlag")+"GetIntv"+request.getParameter("GetIntv"));
          tLCDutySchema.setGetStartType(request.getParameter("GetStartType"));
          tLCDutySchema.setCalRule(request.getParameter("CalRule"));
           loggerDebug("ProposalSave","ǰ̨CalRule:"+request.getParameter("CalRule"));
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
          //      tLCAppntSchema.setNativePlace(request.getParameter("AppntNativePlace")); //����
          //      tLCAppntSchema.setRgtAddress(request.getParameter("AppntRgtAddress"));  //�������ڵ�
          //      tLCAppntSchema.setMarriage(request.getParameter("AppntMarriage"));      //����״��
          //      tLCAppntSchema.setNationality(request.getParameter("AppntNationality")); //����
          //      tLCAppntSchema.setDegree(request.getParameter("AppntDegree"));          //ѧ��
          //      tLCAppntSchema.setRelationToInsured(request.getParameter("AppntRelationToInsured")); //�뱻�����˹�ϵ
          //      tLCAppntSchema.setPostalAddress(request.getParameter("AppntPostalAddress"));         //��ϵ��ַ
          //      tLCAppntSchema.setZipCode(request.getParameter("AppntZipCode"));        //��������
          //      tLCAppntSchema.setHomeAddress(request.getParameter("AppntHomeAddress")); //סַ
          //      tLCAppntSchema.setHomeZipCode(request.getParameter("AppntHomeZipCode")); //סַ��������
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

          // ����Ͷ����
          LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();

          tLCGrpAppntSchema.setCustomerNo(request.getParameter("AppGrpNo"));
          tLCGrpAppntSchema.setGrpContNo(request.getParameter("GrpContNo"));
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
          
          //      tLCInsuredSchema.setName(request.getParameter("Name"));              //����
          //      tLCInsuredSchema.setSex(request.getParameter("Sex"));                //�Ա�
          //      tLCInsuredSchema.setBirthday(request.getParameter("Birthday"));      //��������
          //���䣬���ύ
          //      tLCInsuredSchema.setIDType(request.getParameter("IDType"));          //֤������
          //      tLCInsuredSchema.setIDNo(request.getParameter("IDNo"));              //֤������
          //      tLCInsuredSchema.setNativePlace(request.getParameter("NativePlace")); //����
          //      tLCInsuredSchema.setRgtAddress(request.getParameter("RgtAddress"));  //�������ڵ�
          //      tLCInsuredSchema.setMarriage(request.getParameter("Marriage"));      //����״��
          //      tLCInsuredSchema.setNationality(request.getParameter("Nationality")); //����
          //      tLCInsuredSchema.setDegree(request.getParameter("Degree"));          //ѧ��
          //      tLCInsuredSchema.setSmokeFlag(request.getParameter("SmokeFlag"));    //�Ƿ�����
          //      tLCInsuredSchema.setPostalAddress(request.getParameter("PostalAddress"));         //��ϵ��ַ
          //      tLCInsuredSchema.setZipCode(request.getParameter("ZipCode"));        //��������
          //      tLCInsuredSchema.setHomeAddress(request.getParameter("HomeAddress")); //סַ
          //      tLCInsuredSchema.setHomeZipCode(request.getParameter("HomeZipCode")); //סַ��������
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
          String tIDType[]   = request.getParameterValues("BnfGrid3");
          String tIDNo[]     = request.getParameterValues("BnfGrid4");
          String tBnfRelationToInsured[] = request.getParameterValues("BnfGrid5");

          String tBnfGrade[] = request.getParameterValues("BnfGrid6");
          String tBnfLot[]   = request.getParameterValues("BnfGrid7");
          String tAddress[]  = request.getParameterValues("BnfGrid8");
          String tInsurno[]  = request.getParameterValues("BnfGrid10"); //add by yaory
          int BnfCount = 0;
          if (tBnfNum != null) BnfCount = tBnfNum.length;
          for (int i = 0; i < BnfCount; i++)
          {
            if( tName[i] == null || tName[i].equals( "" )) break;

            LCBnfSchema tLCBnfSchema = new LCBnfSchema();
          
               
            //tLCBnfSchema.setBnfType("1");
            tLCBnfSchema.setBnfType(tBnfType[i]);
            tLCBnfSchema.setName(tName[i]);
            //tLCBnfSchema.setSex(tSex[i]);
            tLCBnfSchema.setIDType(tIDType[i]);
            tLCBnfSchema.setIDNo(tIDNo[i]);
            //tLCBnfSchema.setBirthday(tBirthday[i]);
            tLCBnfSchema.setRelationToInsured(tBnfRelationToInsured[i]);
            tLCBnfSchema.setBnfLot(tBnfLot[i]);
            tLCBnfSchema.setBnfGrade(tBnfGrade[i]);
            tLCBnfSchema.setInsuredNo(tInsurno[i]); //add by yaory
            
                       
            //tLCBnfSchema.setAddress(tAddress[i]);
            //tLCBnfSchema.setZipCode(tZipCode[i]);
            //tLCBnfSchema.setPhone(tPhone[i]);
            tLCBnfSet.add(tLCBnfSchema);
            
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
          String tGetIntv[]=request.getParameterValues("DutyGrid26");
          String tAscription[]=request.getParameterValues("DutyGrid27"); //��������
          String tPayrule[]=request.getParameterValues("DutyGrid28"); //�ɷѹ���
          String tBonusGetMode[]=request.getParameterValues("DutyGrid29"); //������ȡ��ʽ
          String tGetStartType[]=request.getParameterValues("DutyGrid31"); //�������ڼ��㷽ʽ
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
          for (int i = 0; i < DutyCount; i++)  {
            if(tDutyCode[i]==null || tDutyCode[i].equals("")) break;

            tLCDutySchema1= new LCDutySchema();

            if(!tDutyCode[i].equals("") && tDutyChk[i].equals("1"))  {
              tLCDutySchema1.setDutyCode(tDutyCode[i]);
              tLCDutySchema1.setInsuYear(tInsuYear[i]);
              loggerDebug("ProposalSave","<<<<<<<<<<<<<<>>>>>>>>>>"+tInsuYear[i]);
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
              tLCDutySchema1.setGetIntv(tGetIntv[i]); //������ȡ��ʽ
              tLCDutySchema1.setAscriptionRuleCode(tAscription[i]); //���ӹ������� 
              tLCDutySchema1.setGetStartType(tGetStartType[i]);
              if(tDutyCode[i].equals("188001"))
              {
                if(tLCDutySchema1.getAscriptionRuleCode()!=null && !tLCDutySchema1.getAscriptionRuleCode().equals(""))
                {
                  %>
			        <script language="javascript">
			        
			        parent.fraInterface.afterSubmit('Fail','���˽��Ѳ���ѡ��ɷѹ���');
			      
			        </script>
			     <%   
			     FlagStr = "Fail";
	        		
	        		
	        		
	        		return;
                }
              }
              tLCDutySchema1.setPayRuleCode(tPayrule[i]); //���ӽɷѹ���
              if(tBonusGetMode[i]==null || tBonusGetMode[i].equals(""))
              {
              tLCDutySchema1.setBonusGetMode("1"); //������ȡ��ʽ
              }else{
              tLCDutySchema1.setBonusGetMode(tBonusGetMode[i]); //������ȡ��ʽ
              }
              tLCPolSchema.setBonusGetMode(tLCDutySchema1.getBonusGetMode());
              tLCDutySchema1.setAmnt(tAmnt[i]);
              tLCDutySchema1.setMult(tMult[i]);
                            
              tLCDutySchema1.setFloatRate(tFloatRate[i]);
              if(tFloatRate[i]!=null && tFloatRate[i]!="0" && tCalRule[i]=="0")
              {
                tLCDutySchema1.setCalRule("2");
              }else{
              tLCDutySchema1.setCalRule(tCalRule[i]);
              loggerDebug("ProposalSave","***"+tCalRule[i]);
              }
              loggerDebug("ProposalSave","�������� IN PROPOSALSAVE3��"+tFloatRate[i]);
              tLCDutySchema1.setSSFlag(tSSFlag[i]);
              tLCDutySchema1.setGetRate(tGetRate[i]);
              tLCDutySchema1.setPayIntv(tPayIntv[i]);
              tLCDutySchema1.setPeakLine(tPeakLine[i]); //majj
              	loggerDebug("ProposalSave","^^^^^^^^^^^^^^^^^^");
              	loggerDebug("ProposalSave","^^^^^^^^^^^^^^^^^^");
              	loggerDebug("ProposalSave","^^^^^^^^^^^^^^^^^^");
              	loggerDebug("ProposalSave","^^^^^^^^^^^^^^^^^^");
              	loggerDebug("ProposalSave","tPayIntv[i]=="+tPayIntv[i]);
              if(request.getParameter("RiskCode").equals("00607000"))
              	{
              	loggerDebug("ProposalSave","tPayIntv[i]=="+tPayIntv[i]);
	              if(tPayIntv[i]!=null&&!tPayIntv[i].equals(""))
		          {
		          	tLCDutySchema1.setPayIntv(tPayIntv[i]);
		          }
	              else
		          {
		             %>
			        <script language="javascript">
			        //alert("��δ���뽻�ѷ�ʽ��");
			        parent.fraInterface.afterSubmit('Fail','��δ���뽻�ѷ�ʽ��');
			      
			        </script>
			     <%   
			        
		          	
	        		FlagStr = "Fail";
	        		loggerDebug("ProposalSave","FlagStr------------------------"+FlagStr);
	        		
	        		
	        		return;
		          }
		 }
		else
		{
			tLCDutySchema1.setPayIntv(tPayIntv[i]);
		}
	         
              //tLCDutySchema1.setPayIntv(tPayIntv[i]);
              tLCDutySchema1.setGetLimit(tGetLimit[i]);



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
              //                loggerDebug("ProposalSave","payintv:"+tDutyPayIntv[i]);
              //                loggerDebug("ProposalSave","prem:"+tDutyPrem1[i]);
              tLCDutySet.add(tLCDutySchema1);

            } // end of if
          } // end of for
        } // end of if



        loggerDebug("ProposalSave","end set schema ������Ϣ..."+tNeedDutyGrid);


        //�Ƿ��ж��������
        String tNeedPremGrid="0";
        LCPremSet tLCPremSet=new LCPremSet();
        LCPremSchema tLCPremSchema1=new LCPremSchema();
        LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
        LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();

        try
        { //���񱣷������
        tNeedPremGrid=request.getParameter("inpNeedPremGrid");

        loggerDebug("ProposalSave"," tNeedPremGrid:"+tNeedPremGrid);
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

        double actuPrem=0;
        int PremCount = 0;
        if (tPremDutyCode != null) PremCount = tPremDutyCode.length;
        loggerDebug("ProposalSave","&&&&&&&&&&&&&&&&"+PremCount);
        for (int i = 0; i < PremCount; i++)
        {
          if(tPremDutyCode[i]==null || tPremDutyCode[i].equals("")) break;

          tLCPremSchema1= new LCPremSchema();
          tLCPremToAccSchema = new LCPremToAccSchema();

          if(!tPremDutyCode[i].equals("")) //&& tPremChk[i].equals("1")
          {
            loggerDebug("ProposalSave"," tPremDutyCode:"+tPremDutyCode[i]);
            tLCPremSchema1.setDutyCode(tPremDutyCode[i]);
            tLCPremSchema1.setPayPlanCode(tPremPayCode[i]);

            //actuPrem=Double.parseDouble(tPremStandPrem[i])*Double.parseDouble(tPremRate[i]);
            //loggerDebug("ProposalSave"," tPremStandPrem:"+Double.parseDouble(tPremStandPrem[i]));
            //loggerDebug("ProposalSave"," tPremRate:"+Double.parseDouble(tPremRate[i]));
            //loggerDebug("ProposalSave"," actuPrem:"+actuPrem);



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

            tLCPremSet.add(tLCPremSchema1);
            tLCPremToAccSet.add(tLCPremToAccSchema);

          } // end of if
        } // end of for
      } // end of if

    } catch(Exception ex){loggerDebug("ProposalSave","set schema ��������Ϣ...error !!!");}

    loggerDebug("ProposalSave","end set schema ��������Ϣ...");

    //���ݷ�SCHEMA��Ϣ
    TransferData tTransferData = new TransferData();

    tTransferData.setNameAndValue("GetIntv", request.getParameter("GetIntv"));                //��ȡ�������ʽ��
    tTransferData.setNameAndValue("GetDutyKind", request.getParameter("GetDutyKind"));        //���ʼ��ȡ����


    if (request.getParameter("SamePersonFlag") == null)
    tTransferData.setNameAndValue("samePersonFlag", "0"); //Ͷ����ͬ�����˱�־
    else
    tTransferData.setNameAndValue("samePersonFlag", "1"); //Ͷ����ͬ�����˱�־

    if (!ChangePlanFlag)
    {
      tTransferData.setNameAndValue("deleteAccNo", "1");
      tTransferData.setNameAndValue("ChangePlanFlag", "1");
    }
    else
    {
      tTransferData.setNameAndValue("deleteAccNo", "0");
      tTransferData.setNameAndValue("ChangePlanFlag", "0");
    }


    tTransferData.setNameAndValue("SavePolType", request.getParameter("BQFlag")); //��ȫ������
    tTransferData.setNameAndValue("EdorType", request.getParameter("EdorType"));
    tTransferData.setNameAndValue("EdorTypeCal", request.getParameter("EdorTypeCal"));
    loggerDebug("ProposalSave","^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+request.getParameter("EdorTypeCal"));
    loggerDebug("ProposalSave","SavePolType : " + request.getParameter("BQFlag"));


    // ׼���������� VData
    //tVData.addElement(tLCContSchema);
    //tVData.addElement(tLCPolSchema);
    //tVData.addElement(tLCAppntSchema);
    //tVData.addElement(tLCGrpAppntSchema);
    //tVData.addElement(tLCInsuredSchema);
    LCInsuredRelatedSet mLCInsuredRelatedSet=new LCInsuredRelatedSet();
    ////add by yaory write on 2005-7-6-17:43
//    try{
//      String tRadio[] = request.getParameterValues("InpInsuredGridSel");
//
//      //loggerDebug("ProposalSave","�����Ǻ�����===="+tRadio.length);
//      ////////ȡ������MULTINE��ֵ///
//      String tGrid1 [] = request.getParameterValues("InsuredGrid1"); //�õ���1�е�����ֵ
//      String tGrid2 [] = request.getParameterValues("InsuredGrid2");
//      String tGrid3 [] = request.getParameterValues("InsuredGrid3");
//      String tGrid4 [] = request.getParameterValues("InsuredGrid4");
//      String tGrid5 [] = request.getParameterValues("InsuredGrid5");
//      LCInsuredRelatedSchema tLCInsuredRelatedSchema=new LCInsuredRelatedSchema();
//      loggerDebug("ProposalSave","�ܹ�������===="+tRadio.length);
//      for(int i=0; i<tRadio.length;i++)
//      {
//        if(tRadio[i].equals("1"))
//        {
//          loggerDebug("ProposalSave","�������");
//          /////˵��-���������˿ͻ���δ������
//          //loggerDebug("ProposalSave","���б�ѡ��=="+ tGrid1 [i]);
//          tLCInsuredRelatedSchema.setCustomerNo(tGrid1 [i]);
//          //loggerDebug("ProposalSave","���б�ѡ��=="+ tGrid2 [i]);
//          tLCInsuredRelatedSchema.setName(tGrid2 [i]);
//          //loggerDebug("ProposalSave","���б�ѡ��=="+ tGrid3 [i]);
//          tLCInsuredRelatedSchema.setSex(tGrid3 [i]);
//          //loggerDebug("ProposalSave","���б�ѡ��=="+ tGrid4 [i]);
//          tLCInsuredRelatedSchema.setBirthday(tGrid4 [i]);
//          loggerDebug("ProposalSave","���б�ѡ��=="+ tGrid5 [i]);
//          tLCInsuredRelatedSchema.setRelationToInsured(tGrid5 [i]);
//
//          loggerDebug("ProposalSave","########################");
//          loggerDebug("ProposalSave","########################");
//          loggerDebug("ProposalSave","aaaa="+tGrid1 [i]);
//          loggerDebug("ProposalSave","########################");
//          loggerDebug("ProposalSave","########################");
//          tLCPolSchema.setStandbyFlag1(tGrid1 [i]);
//          tLCDutySchema.setStandbyFlag1(tGrid1 [i]);
//          tLCInsuredRelatedSchema.setOperator(request.getParameter("AgentCode"));
//          tLCInsuredRelatedSchema.setMakeDate(request.getParameter("AgentCode"));
//          tLCInsuredRelatedSchema.setMakeTime(request.getParameter("AgentCode"));
//          tLCInsuredRelatedSchema.setSequenceNo(request.getParameter("SequenceNo"));
//          //tLCInsuredRelatedSchema.setSequenceNo("01");
//          if(tGrid5 [i].equals("00"))
//          {
//            loggerDebug("ProposalSave","Ҫ�ľ�����"); //�������˿ͻ���
//            tLCInsuredRelatedSchema.setMainCustomerNo(tGrid5 [i]);
//          }
//          tLCInsuredRelatedSchema.setPolNo(request.getParameter("ProposalNo"));
//        }
//        if(tRadio[i].equals("0"))
//        {
//          //loggerDebug("ProposalSave","����δ��ѡ��");
//          if(tGrid5 [i].equals("00"))
//          {
//            //loggerDebug("ProposalSave","Ҫ�ľ�����"); //�������˿ͻ���
//            tLCInsuredRelatedSchema.setMainCustomerNo(tGrid5 [i]);
//          }
//        }
//      }
//      for(int i=0; i<tRadio.length;i++)
//      {
//        if(tRadio[i].equals("1"))
//        {
//          mLCInsuredRelatedSet.add(tLCInsuredRelatedSchema);
//        }
//      }
//    }catch(Exception ex){}
    ///end add by yaory 2005-7-6
      
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

    //����ǿ�ѡ������
    if (tNeedPremGrid!=null&&tNeedPremGrid.equals("1"))
    {
      tVData.addElement(tLCPremSet);
      tVData.addElement(tLCPremToAccSet);
    }


  } // end of if




  // ���ݴ���

  if( !tBusinessDelegate1.submitData( tVData, tOperate ,busiName1) ) {
    Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
    FlagStr = "Fail";
  }
  else {
    Content = "*************Proposal ����ɹ�!*************";
    FlagStr = "Succ";

    tVData.clear();
    tVData = tBusinessDelegate1.getResult();

    // ��ʾ
    // ������Ϣ
    mLCPolSchema.setSchema(( LCPolSchema )tVData.getObjectByObjectName( "LCPolSchema", 0 ));
    mLCDutyBLSet.set((LCDutyBLSet)tVData.getObjectByObjectName( "LCDutyBLSet", 0 ));
    
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
    String busiName4="cbcheckgrpChangePlanUI";
    BusinessDelegate tBusinessDelegate4=BusinessDelegate.getBusinessDelegate();
   // ChangePlanUI tChangePlanUI = new ChangePlanUI();
    if (!tBusinessDelegate4.submitData(VData3, "INSERT||CHANGEPLAN",busiName4)) {
      Content = "����ʧ�ܣ�ԭ����: " + tBusinessDelegate4.getCErrors().getError(0).errorMessage;
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
        dutyArray[<%=j-1%>][1]='<%=mLCDutyBLSet.get(j).getPrem()%>';
        dutyArray[<%=j-1%>][2]='<%=mLCDutyBLSet.get(j).getAmnt()%>';
        </script>
        <%
        }
        %>
        <script language="javascript">
          try
          {

            if (parent.fraInterface.fm.all('inpNeedDutyGrid').value==1)
            {
              var mulLineCount=parent.fraInterface.DutyGrid.mulLineCount;
              for (i=0;i<mulLineCount;i++)
              {
                var dutycode=parent.fraInterface.DutyGrid.getRowColData(i,1);
                for (j=0;j<dutyArray.length;j++)
                {
                  if (dutyArray[j][0]==dutycode)
                  {
                    parent.fraInterface.DutyGrid.setRowColData(i,13,dutyArray[j][1]);
                    parent.fraInterface.DutyGrid.setRowColData(i,14,dutyArray[j][2]);
                  }
                }

              }
            }
            else
            {

              parent.fraInterface.fm.all("Prem").value = "<%=mLCPolSchema.getPrem()%>";

              parent.fraInterface.fm.all("Amnt").value = "<%=mLCPolSchema.getAmnt()%>";

            }
            parent.fraInterface.fm.all("ContNo").value = "<%=mLCPolSchema.getContNo()%>";
            //parent.fraInterface.fm.all("GrpPolNo").value = "<%=mLCPolSchema.getGrpPolNo()%>";
            parent.fraInterface.fm.all("ProposalNo").value = "<%=mLCPolSchema.getProposalNo()%>";


            parent.fraInterface.fm.all("ContType").value = "<%=mLCPolSchema.getContType()%>";

            if(parent.VD.gVSwitch.addVar("mainRiskPolNo","","<%=mLCPolSchema.getMainPolNo()%>")==false){
              parent.VD.gVSwitch.updateVar("mainRiskPolNo","","<%=mLCPolSchema.getMainPolNo()%>");
            }

            //¼���޸�
            if (parent.fraInterface.LoadFlag == 2) {

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
                }
%>
</html>

