<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�NSProposalSave.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//only use for bq
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
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
  //loggerDebug("NSProposalSave","asdasdsd");
  //�����жϣ����û��¼�����ղ���¼�븽���� write by yaory
    String triskcode=request.getParameter("RiskCode");
    loggerDebug("NSProposalSave","������-��ͬ��-Session====="+request.getParameter("ContNo")+"-"+request.getParameter("RiskCode")+"-"+(String)session.getValue("MainRiskNo"));
    tLoadFlag = request.getParameter( "LoadFlag" );
    loggerDebug("NSProposalSave","===============LoadFlag:");
    if(!tLoadFlag.equals("8")){
       
       if(tOperate.equals("INSERT||PROPOSAL") && !triskcode.equals((String)session.getValue("MainRiskNo")))
       {
          loggerDebug("NSProposalSave","�����ж�");
          ExeSQL xeSql = new ExeSQL();
          SSRS gSSRS = new SSRS();
          gSSRS = xeSql.execSQL("select * from lcpol where riskcode='"+(String)session.getValue("MainRiskNo")+"' and contno='"+request.getParameter("ContNo")+"'");
          if(gSSRS.MaxRow<1){
          remark="1";
          %>
             <script language="javascript">
             alert("������������գ�");
             </script>
          <%
          }
       }
    }
    //ͬһ�����ֲ���¼���� verify by yaory
    if(tOperate.equals("INSERT||PROPOSAL"))
    {
    loggerDebug("NSProposalSave","�����ж�");
      ExeSQL xeSql = new ExeSQL();
      SSRS gSSRS = new SSRS();
      gSSRS = xeSql.execSQL("select * from lcpol where riskcode='"+request.getParameter("RiskCode")+"' and contno='"+request.getParameter("ContNo")+"' and mainpolno='"+(String)session.getValue("MainRiskPolNo")+"'");
      if(gSSRS.MaxRow>0){
        remark="1";
        %>
        <script language="javascript">
        alert("���Ѿ���ӹ�������֣�");
        </script>
        <%
        }
    }
    if(!remark.equals("1")){
      //end �ж�
      LCPolSchema mLCPolSchema = new LCPolSchema();
      LCDutyBLSet mLCDutyBLSet=new LCDutyBLSet();
      try  {
        //ProposalUI tProposalUI  = new ProposalUI();
        String busiName2="tbProposalUI";
        BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
        GlobalInput tG = new GlobalInput();
        tG = ( GlobalInput )session.getValue( "GI" );

        VData tVData = new VData();

        tOldAction = tAction;
        loggerDebug("NSProposalSave","�û�ѡ��Ĳ���ΪtAction:"+tAction);

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
            loggerDebug("NSProposalSave","111 tAction:"+tAction);
            loggerDebug("NSProposalSave","111 tAction:������ܳ�����ȡ����");
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
            loggerDebug("NSProposalSave","222 tAction:"+tAction);

            LCPolSchema tLCPolSchema = new LCPolSchema();
            tLCPolSchema.setPolNo(request.getParameter("ProposalNo"));
            VData VData2 = new VData();
            VData2.add(tLCPolSchema);
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
          { //�����ɹ�ʱ
          VData vResult = tBusinessDelegate.getResult();
          approveFlag = (String)vResult.get(0);
          UWFlag = (String)vResult.get(1);
          approveCode = (String)vResult.get(2);
          approveDate = (String)vResult.get(3);

          loggerDebug("NSProposalSave","approveFlag:" + approveFlag + " UWFlag:" + UWFlag);
        }
      }

      //��ӡˢ��
      loggerDebug("NSProposalSave","tOldAction.indexOf_Modify:"+tOldAction.indexOf("Modify"));
      loggerDebug("NSProposalSave","GrpContNo:"+request.getParameter("GrpContNo"));
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
        String busiName="cbcheckChangePlanUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if (!tBusinessDelegate.submitData(VData3, "INSERT",busiName))
        {
          VData rVData = tBusinessDelegate.getResult();
          loggerDebug("NSProposalSave","LockTable Failed! " + (String)rVData.get(0));
        }
        else
        {
          loggerDebug("NSProposalSave","LockTable Succed!");
        }
      }

      if (tAction.indexOf("Modify") != -1)
      tAction = tAction.substring(6);
      loggerDebug("NSProposalSave","tAction" + tAction);
      LCContSchema tLCContSchema = new LCContSchema();
      tLCContSchema.setContNo(request.getParameter("ContNo"));
      loggerDebug("NSProposalSave","ContNo"+request.getParameter("ContNo"));
      // ������Ϣ����
      LCPolSchema tLCPolSchema = new LCPolSchema();
      LCDutySchema tLCDutySchema = new LCDutySchema();

      tLCPolSchema.setProposalNo(request.getParameter("ProposalNo"));
      tLCPolSchema.setPrtNo(request.getParameter("PrtNo"));
      tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
      //tLCPolSchema.setManageCom(tG.ManageCom);
      tLCPolSchema.setSaleChnl(request.getParameter("SaleChnl"));
      tLCPolSchema.setAgentCom(request.getParameter("AgentCom"));
      tLCPolSchema.setAgentType(request.getParameter("AgentType"));
      tLCPolSchema.setAgentCode(request.getParameter("AgentCode"));
      tLCPolSchema.setAgentGroup(request.getParameter("AgentGroup"));
      tLCPolSchema.setHandler(request.getParameter("Handler"));
      tLCPolSchema.setAgentCode1(request.getParameter("AgentCode1"));
      tLCPolSchema.setInsuredAppAge(request.getParameter("InsuredAppAge")); //������Ͷ������
      tLCPolSchema.setInsuredPeoples(request.getParameter("InsuredPeoples")); //����������
      tLCPolSchema.setPolTypeFlag(request.getParameter("PolTypeFlag")); //�������ͱ��
      tLCPolSchema.setContNo(request.getParameter("ContNo"));
      //tLCPolSchema.setGrpPolNo(request.getParameter("GrpPolNo"));
      tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
      //��tLCPolSchema���жϵı�־������̨-����Ӹ�����֮ǰ�Ƿ����������
      //tLCPolSchema.setKeepValueOpt(remark); //��ȫ�����������
      //tLCPolSchema.setMainPolNo(request.getParameter("MainPolNo"));
      //add verify �������û����ӣ�����ֱ�����Ӹ����� write by yaory
      //loggerDebug("NSProposalSave","��־====="+request.getParameter("tLoadFlag"));
      //edit by yaory-2005-7-18//����Ǹ��������SESSION
      ExeSQL texeSql = new ExeSQL();
      SSRS FlagSSRS = new SSRS();
      //loggerDebug("NSProposalSave","��������====="+request.getParameter("RiskCode"));
      FlagSSRS = texeSql.execSQL("select subriskflag from lmriskapp where riskcode='"+request.getParameter("RiskCode")+"'");
      if(FlagSSRS.GetText(1,1).equals("S"))
      {
        tLCPolSchema.setMainPolNo((String)session.getValue("MainRiskPolNo"));
      }else{
        tLCPolSchema.setMainPolNo(request.getParameter("MainPolNo"));
      }
      //loggerDebug("NSProposalSave","��������===="+(String)session.getValue("MainRiskPolNo"));
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
        loggerDebug("NSProposalSave","add by LiuYansong 2004-11-02");
        //}


        // add for GrpAddName
        tLCPolSchema.setMasterPolNo(request.getParameter("MasterPolNo"));
        loggerDebug("NSProposalSave","MasterPolNo:" + request.getParameter("MasterPolNo"));

        loggerDebug("NSProposalSave","���ñ���������Ϣ...");

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
        tLCPolSchema.setCValiDate(request.getParameter("CValiDate"));             //������Ч����
        //tLCPolSchema.setPolApplyDate(request.getParameter("CValiDate"));          //Ͷ������������
        tLCPolSchema.setHealthCheckFlag(request.getParameter("HealthCheckFlag")); //�Ƿ�����
        //tLCPolSchema.setOutPayFlag(request.getParameter("OutPayFlag"));           //�罻����ʽ
        tLCPolSchema.setPayLocation(request.getParameter("PayLocation"));         //�շѷ�ʽ
        //tLCPolSchema.setBankCode(request.getParameter("BankCode"));               //������
        //tLCPolSchema.setBankAccNo(request.getParameter("BankAccNo"));             //�����ʺ�
        //tLCPolSchema.setAccName(request.getParameter("AccName"));                 //����
        tLCPolSchema.setLiveGetMode(request.getParameter("LiveGetMode"));         //���汣�ս���ȡ��ʽ
        loggerDebug("NSProposalSave","init lcol1 ") ;                                                                         //��ȡ���ޣ�ͨ��TRANSFERDATA����
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
          tLCPolSchema.setSpecifyValiDate(request.getParameter("SpecifyValiDate")); //�Ƿ�ָ����Ч����
          tLCPolSchema.setFloatRate(request.getParameter("FloatRate"));             //
          //tLCPolSchema.setGetPolMode(request.getParameter("GetPolMode"));           //�����ʹ﷽ʽ
          tLCPolSchema.setBonusMan(request.getParameter("BonusMan"));               //������ȡ��
          tLCPolSchema.setBonusGetMode(request.getParameter("BonusGetMode"));       //������ȡ��ʽ
          tLCPolSchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));       //�ڲ�����
          tLCPolSchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
          tLCPolSchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));       //��ӡ��־printflag
          //tLCPolSchema.setStandbyFlag4(request.getParameter("StandbyFlag4"));       //���б�����foreighpol


          //add by guoxiang at 2004-9-7 17:14 for  ��ҵ �Ĺ���ѱ����洢
          tLCPolSchema.setManageFeeRate(request.getParameter("ManageFeeRate"));       //����ѱ���
          //add end

          //add by HL @20050521 ���Ӳ�ɥʧ��ֵѡ��
          tLCPolSchema.setKeepValueOpt(request.getParameter("KeepValueOpt"));
          //end add




          loggerDebug("NSProposalSave","init LCDuty ");

          //����ҳ����Ϣδ����
          tLCDutySchema.setPayIntv(request.getParameter("PayIntv"));                //���ѷ�ʽ
          loggerDebug("NSProposalSave","payIntv:"+request.getParameter("PayIntv"));
          loggerDebug("NSProposalSave","1");
          tLCDutySchema.setInsuYear(request.getParameter("InsuYear"));              //�����ڼ�
          loggerDebug("NSProposalSave","2");
          tLCDutySchema.setInsuYearFlag(request.getParameter("InsuYearFlag"));
          loggerDebug("NSProposalSave","3");
          tLCDutySchema.setPayEndYear(request.getParameter("PayEndYear"));          //��������
          loggerDebug("NSProposalSave","4");
          tLCDutySchema.setPayEndYearFlag(request.getParameter("PayEndYearFlag"));
          loggerDebug("NSProposalSave","5");
          tLCDutySchema.setGetYear(request.getParameter("GetYear"));                //���ʼ��ȡ����
          loggerDebug("NSProposalSave","6");
          tLCDutySchema.setGetYearFlag(request.getParameter("GetYearFlag"));
          loggerDebug("NSProposalSave","7");
          loggerDebug("NSProposalSave","getyear:"+request.getParameter("GetYear")+"GetYearFlag:"+request.getParameter("GetYearFlag"));
          tLCDutySchema.setGetStartType(request.getParameter("GetStartType"));
          tLCDutySchema.setCalRule(request.getParameter("CalRule"));
          tLCDutySchema.setFloatRate(request.getParameter("FloatRate"));
          loggerDebug("NSProposalSave","FloatRate:"+request.getParameter("FloatRate"));
          tLCDutySchema.setPremToAmnt(request.getParameter("PremToAmnt"));
          tLCDutySchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));
          tLCDutySchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
          tLCDutySchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));
          tLCDutySchema.setPrem(request.getParameter("Prem"));
          tLCDutySchema.setAmnt(request.getParameter("Amnt"));
          tLCDutySchema.setMult(request.getParameter("Mult"));
          tLCDutySchema.setGetLimit(request.getParameter("GetLimit"));
          loggerDebug("NSProposalSave","GetLimit:"+request.getParameter("GetLimit"));
          tLCDutySchema.setGetRate(request.getParameter("GetRate"));
          tLCDutySchema.setSSFlag(request.getParameter("SSFlag"));
          tLCDutySchema.setPeakLine(request.getParameter("PeakLine"));

          loggerDebug("NSProposalSave","���ñ�����������Ϣ...");
          loggerDebug("NSProposalSave","init LCAppntInd ");

          // Ͷ������Ϣ����
          // ����Ͷ����
          LCAppntSchema tLCAppntSchema = new LCAppntSchema();
          tLCAppntSchema.setContNo(tLCContSchema.getContNo());
          tLCAppntSchema.setAppntNo(request.getParameter("AppntCustomerNo"));  //�ͻ���
          tLCAppntSchema.setAppntName(request.getParameter("AppntName"));              //����
          tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));                //�Ա�
          tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));    //��������

          // ��������
          LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
          //tLCInsuredSchema.setInsuredGrade("M");
          tLCInsuredSchema.setContNo(tLCContSchema.getContNo());
          tLCInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"));  //�ͻ���

          loggerDebug("NSProposalSave","end set schema ��������Ϣ...");

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

          int BnfCount = 0;
          if (tBnfNum != null) BnfCount = tBnfNum.length;
          for (int i = 0; i < BnfCount; i++)
          {
            if( tName[i] == null || tName[i].equals( "" )) break;

            LCBnfSchema tLCBnfSchema = new LCBnfSchema();

            tLCBnfSchema.setBnfType(tBnfType[i]);
            tLCBnfSchema.setName(tName[i]);
            //tLCBnfSchema.setSex(tSex[i]);
            tLCBnfSchema.setIDType(tIDType[i]);
            tLCBnfSchema.setIDNo(tIDNo[i]);
            //tLCBnfSchema.setBirthday(tBirthday[i]);
            tLCBnfSchema.setRelationToInsured(tBnfRelationToInsured[i]);
            tLCBnfSchema.setBnfLot(tBnfLot[i]);
            tLCBnfSchema.setBnfGrade(tBnfGrade[i]);
            //tLCBnfSchema.setAddress(tAddress[i]);
            //tLCBnfSchema.setZipCode(tZipCode[i]);
            //tLCBnfSchema.setPhone(tPhone[i]);
            tLCBnfSet.add(tLCBnfSchema);
          }

          loggerDebug("NSProposalSave","end set schema ��������Ϣ...");

          // ��֪��Ϣ
          LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();

          String tImpartNum[] = request.getParameterValues("ImpartGridNo");
          String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //��֪���
          String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //��֪����
          String tImpartContent[] = request.getParameterValues("ImpartGrid4");        //��д����
          String tImpartCustomerNoType[] = request.getParameterValues("ImpartGrid5"); //��֪�ͻ�����

          int ImpartCount = 0;
          if (tImpartNum != null) 
             ImpartCount = tImpartNum.length;

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

          loggerDebug("NSProposalSave","end set schema ��֪��Ϣ...");

          // �ر�Լ��
          LCSpecSet tLCSpecSet = new LCSpecSet();

          String tSpecNum[] = request.getParameterValues("SpecGridNo");
          String tSpecType[] = request.getParameterValues("SpecGrid1");
          String tSpecCode[] = request.getParameterValues("SpecGrid2");
          String tSpecContent[] = request.getParameterValues("SpecGrid3");

          int SpecCount = 0;
          if (tSpecNum != null) SpecCount = tSpecNum.length;
          for (int i = 0; i < SpecCount; i++)  {
             if(tSpecCode[i]==null || tSpecCode[i].trim().equals("")) 
                 break;
             if(tSpecContent[i]==null || tSpecContent[i].trim().equals("")) 
                 break;

             LCSpecSchema tLCSpecSchema = new LCSpecSchema();
             tLCSpecSchema.setSpecCode(tSpecCode[i]);
             tLCSpecSchema.setSpecType(tSpecType[i]);
             tLCSpecSchema.setSpecContent(tSpecContent[i]);
             tLCSpecSet.add(tLCSpecSchema);
          }

          loggerDebug("NSProposalSave","end set schema ��Լ��Ϣ...");

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


          int DutyCount = 0;
          if (tDutyCode != null) DutyCount = tDutyCode.length;
          for (int i = 0; i < DutyCount; i++)  
          {
            if(tDutyCode[i]==null || tDutyCode[i].equals("")) break;

            tLCDutySchema1= new LCDutySchema();

            if(!tDutyCode[i].equals("") && tDutyChk[i].equals("1"))  
            {
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
              tLCDutySchema1.setAmnt(tAmnt[i]);
              tLCDutySchema1.setMult(tMult[i]);
              tLCDutySchema1.setCalRule(tCalRule[i]);
              tLCDutySchema1.setFloatRate(tFloatRate[i]);
              tLCDutySchema1.setPayIntv(tPayIntv[i]);
              tLCDutySchema1.setGetLimit(tGetLimit[i]);
              tLCDutySet.add(tLCDutySchema1);
            } 
          } 
        } 



        loggerDebug("NSProposalSave","end set schema ������Ϣ..."+tNeedDutyGrid);


        //�Ƿ��ж��������
        String tNeedPremGrid="0";
        LCPremSet tLCPremSet=new LCPremSet();
        LCPremSchema tLCPremSchema1=new LCPremSchema();
        LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
        LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();

        try
        {  //���񱣷������
           tNeedPremGrid=request.getParameter("inpNeedPremGrid");

           loggerDebug("NSProposalSave"," tNeedPremGrid:"+tNeedPremGrid);
           if (tNeedPremGrid.equals("1")) 
           {       //�б�����
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
               for (int i = 0; i < PremCount; i++)
               {
                  if(tPremDutyCode[i]==null || tPremDutyCode[i].equals("")) break;

                  tLCPremSchema1= new LCPremSchema();
                  tLCPremToAccSchema = new LCPremToAccSchema();

                  if(!tPremDutyCode[i].equals("") && tPremChk[i].equals("1"))
                  {
                     loggerDebug("NSProposalSave"," tPremDutyCode:"+tPremDutyCode[i]);
                     tLCPremSchema1.setDutyCode(tPremDutyCode[i]);
                     tLCPremSchema1.setPayPlanCode(tPremPayCode[i]);

                     tLCPremSchema1.setStandPrem(tPremStandPrem[i]);   //ÿ���������ϵı�����jsҳ�����
                     tLCPremSchema1.setPrem(tPremStandPrem[i]);
                     loggerDebug("NSProposalSave","--------------prem"+i+":"+tPremStandPrem[i]);
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

        } 
        catch(Exception ex)
        {
           loggerDebug("NSProposalSave","set schema ��������Ϣ Error!");
        }

        loggerDebug("NSProposalSave","end set schema ��������Ϣ...");

        //���ݷ�SCHEMA��Ϣ
        TransferData tTransferData = new TransferData();

        tTransferData.setNameAndValue("getIntv", request.getParameter("getIntv"));                //��ȡ�������ʽ��
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
        loggerDebug("NSProposalSave","SavePolType : " + request.getParameter("BQFlag"));


        // ׼���������� VData
        tVData.addElement(tLCContSchema);
        tVData.addElement(tLCPolSchema);
        tVData.addElement(tLCAppntSchema);
       // tVData.addElement(tLCGrpAppntSchema);
        tVData.addElement(tLCInsuredSchema);
        LCInsuredRelatedSet mLCInsuredRelatedSet=new LCInsuredRelatedSet();
        //add by yaory write on 2005-7-6-17:43
        try{
           String tRadio[] = request.getParameterValues("InpInsuredGridSel");

           //loggerDebug("NSProposalSave","tRadio.length===="+tRadio.length);
           String tGrid1 [] = request.getParameterValues("InsuredGrid1"); //�õ���1�е�����ֵ
           String tGrid2 [] = request.getParameterValues("InsuredGrid2");
           String tGrid3 [] = request.getParameterValues("InsuredGrid3");
           String tGrid4 [] = request.getParameterValues("InsuredGrid4");
           String tGrid5 [] = request.getParameterValues("InsuredGrid5");
           LCInsuredRelatedSchema tLCInsuredRelatedSchema=new LCInsuredRelatedSchema();
           loggerDebug("NSProposalSave","�ܹ�������===="+tRadio.length);
           for(int i=0; i<tRadio.length;i++)
           {
              if(tRadio[i].equals("1"))
              {
                 loggerDebug("NSProposalSave","�������");
                 //˵��-���������˿ͻ���δ������ 
                 tLCInsuredRelatedSchema.setCustomerNo(tGrid1 [i]);          
                 tLCInsuredRelatedSchema.setName(tGrid2 [i]);          
                 tLCInsuredRelatedSchema.setSex(tGrid3 [i]);          
                 tLCInsuredRelatedSchema.setBirthday(tGrid4 [i]);          
                 tLCInsuredRelatedSchema.setRelationToInsured(tGrid5 [i]);
              tLCPolSchema.setStandbyFlag1(tGrid1 [i]);
              tLCDutySchema.setStandbyFlag1(tGrid1 [i]);
              tLCInsuredRelatedSchema.setOperator(request.getParameter("AgentCode"));
              tLCInsuredRelatedSchema.setMakeDate(request.getParameter("AgentCode"));
              tLCInsuredRelatedSchema.setMakeTime(request.getParameter("AgentCode"));
              //tLCInsuredRelatedSchema.setSequenceNo(request.getParameter("SequenceNo"));
              tLCInsuredRelatedSchema.setSequenceNo("01");
              if(tGrid5 [i].equals("00"))
              {            
                 tLCInsuredRelatedSchema.setMainCustomerNo(tGrid5 [i]);
              }
              tLCInsuredRelatedSchema.setPolNo(request.getParameter("ProposalNo"));
           }
           if(tRadio[i].equals("0"))
        {
          //loggerDebug("NSProposalSave","����δ��ѡ��");
          if(tGrid5 [i].equals("00"))
          {
            //loggerDebug("NSProposalSave","Ҫ�ľ�����"); //�������˿ͻ���
            tLCInsuredRelatedSchema.setMainCustomerNo(tGrid5 [i]);
          }
        }
      }
      for(int i=0; i<tRadio.length;i++)
      {
        if(tRadio[i].equals("1"))
        {
          mLCInsuredRelatedSet.add(tLCInsuredRelatedSchema);
        }
      }
    }catch(Exception ex){}
    ///end add by yaory 2005-7-6

    tVData.addElement(mLCInsuredRelatedSet);
    tVData.addElement(tLCBnfSet);
    tVData.addElement(tLCCustomerImpartSet);
    tVData.addElement(tLCSpecSet);
    tVData.addElement(tG);
    tVData.addElement(tTransferData);

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

  if( !tBusinessDelegate2.submitData( tVData, tOperate,busiName2 ) ) {
    Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate2.getCErrors().getError(0).errorMessage;
    FlagStr = "Fail";
  }
  else {
    Content = "*************Proposal ����ɹ�!*************";
    FlagStr = "Succ";

    tVData.clear();
    tVData = tBusinessDelegate2.getResult();

    // ��ʾ
    // ������Ϣ
    mLCPolSchema.setSchema(( LCPolSchema )tVData.getObjectByObjectName( "LCPolSchema", 0 ));
    mLCDutyBLSet.set((LCDutyBLSet)tVData.getObjectByObjectName( "LCDutyBLSet", 0 ));
    ///////////add by yaory -2005-7-13 �����������---
    loggerDebug("NSProposalSave","������===="+tOperate);
    if(tOperate.equals("INSERT||PROPOSAL"))
    {
      ExeSQL rexeSql = new ExeSQL();
      SSRS riskFlagSSRS = new SSRS();
      riskFlagSSRS = rexeSql.execSQL("select subriskflag from lmriskapp where riskcode in (select riskcode from lcpol where polno='"+mLCPolSchema.getPolNo()+"')");
      if(riskFlagSSRS.GetText(1,1).equals("M"))
      {
        loggerDebug("NSProposalSave","����==="+(String)session.getValue("MainRiskPolNo"));
        session.putValue("MainRiskPolNo",mLCPolSchema.getPolNo());
      }
    }else{
      loggerDebug("NSProposalSave","���Ǹ����վ�����������===="+(String)session.getValue("MainRiskPolNo"));
      session.putValue("MainRiskPolNo",null);
    }
    /////////end add by yaory
  }
  //��������������ֱ��ǩ��
  try  {
    if (FlagStr.equals("Succ") && request.getParameter("BQFlag")!=null && request.getParameter("BQFlag").equals("4")) {
      loggerDebug("NSProposalSave","\nNewProposalNo:" + mLCPolSchema.getProposalNo());

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
    var dutyArray = new Array();
    </script>
    <%
    loggerDebug("NSProposalSave",mLCDutyBLSet.encode());
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

            if (parent.fraInterface.document.all('inpNeedDutyGrid').value==1)
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

              parent.fraInterface.document.all("Prem").value = "<%=mLCPolSchema.getStandPrem()%>";

              parent.fraInterface.document.all("Amnt").value = "<%=mLCPolSchema.getAmnt()%>";

            }
            parent.fraInterface.document.all("ContNo").value = "<%=mLCPolSchema.getContNo()%>";
            //parent.fraInterface.document.all("GrpPolNo").value = "<%=mLCPolSchema.getGrpPolNo()%>";
            parent.fraInterface.document.all("ProposalNo").value = "<%=mLCPolSchema.getProposalNo()%>";


            parent.fraInterface.document.all("ContType").value = "<%=mLCPolSchema.getContType()%>";

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

          loggerDebug("NSProposalSave",Content);

          //�滻��������Ϣ�������ַ�
          Content = Content.replace('\n', ' ');

          while (Content.indexOf("\"") != -1) {
            Content = Content.replace('\"', ' ');
            //loggerDebug("NSProposalSave",Content);
          }


          loggerDebug("NSProposalSave",Content);
          loggerDebug("NSProposalSave",FlagStr);
          %>

          <html>
            <script language="javascript">
              try
              {
                parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
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

