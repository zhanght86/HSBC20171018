var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//�������
function showSecondUWInput()
{
	var strUrl="SecondUWInput.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value;
    //window.open(strUrl,"�������");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//�ɿͻ���ѯ��LLLdPersonQuery.js�����ص�����¼ʱ����
function afterQueryLL(arr)
{
    fm.customerNo.value = arr[0];
    fm.customerName.value = arr[1];
    fm.customerSex.value = arr[2];
    fm.customerAge.value = arr[3];
    
    //2008-11-25 ȥ��VIP��־����ʾ
//    fm.IsVip.value = arr[4];
    //if(arr[4]==null || arr[4]=="" || arr[4]==0)
    //{
     	//fm.VIPValueName.value ="��";
     	//fm.IsVip.value = "0";
    //}
    //else
    //{
     	//fm.IsVip.value = arr[4];
     	//fm.VIPValueName.value ="��";
    //}
    showOneCodeName('sex','customerSex','SexName');//�Ա�
    //��ʼ��¼����
    fm.hospital.value = "";
    fm.TreatAreaName.value = "";
    fm.OtherAccidentDate.value = "";
    fm.MedicalAccidentDate.value = "";
    fm.accidentDetail.value = "";
//    fm.IsDead.value = "";
    fm.cureDesc.value = "";
//    fm.Remark.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //���ÿɲ�����ť
    fm.addbutton.disabled=false;
    fm.QueryCont2.disabled = false;
    fm.QueryCont3.disabled = false;
}


//������ѯ
function queryRegister()
{
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    //�����¼��š������¹ʷ�������(һ��)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql1");
	mySql.addSubPara(rptNo); 
    var AccNo = easyExecSql(mySql.getString());

    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18---------19
   /* var strSQL2 =" select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate, "
                +" (select username from llclaimuser where usercode = llregister.Operator),mngcom, "
                +" (case assigneetype when '0' then 'ҵ��Ա' when '1' then '�ͻ�' end),assigneecode,assigneename, "
                +" (case assigneesex when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end), "
                +" assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass, "
                +" (case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end), "
                +" (case getmode when '1' then 'һ��ͳһ����' when '2' then '�����ʽ��ȡ' when '3' then '����֧��' end),"
                +" accepteddate,ApplyDate,Rgtantmobile,postcode,(select ReAffixDate from LLAffix where rgtno=llregister.rgtno and rownum=1) "
                +" from llregister where 1=1 "
                +" and rgtno = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql2");
	mySql.addSubPara(rptNo); 
    var RptContent = easyExecSql(mySql.getString());

    //����ҳ������
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
    fm.AccidentSite.value = RptContent[0][5];
    fm.RptDate.value = RptContent[0][6];
    fm.Operator.value = RptContent[0][7];
    fm.MngCom.value = RptContent[0][8];
    fm.AssigneeType.value = RptContent[0][9];
    fm.AssigneeCode.value = RptContent[0][10];
    fm.AssigneeName.value = RptContent[0][11];
    fm.AssigneeSex.value = RptContent[0][12];
    fm.AssigneePhone.value = RptContent[0][13];
    fm.AssigneeAddr.value = RptContent[0][14];
    fm.AssigneeZip.value = RptContent[0][15];
    fm.occurReason.value = RptContent[0][16];
    fm.RgtConclusion.value = RptContent[0][17];
    //fm.rgtType.value = RptContent[0][18];
    fm.RgtClass.value = RptContent[0][19];
    fm.RgtClassName.value = RptContent[0][20];
    fm.GetMode.value = RptContent[0][21];
    fm.AcceptedDate.value = RptContent[0][22]; 
    fm.ApplyDate.value = RptContent[0][23];
    fm.RptorMoPhone.value = RptContent[0][24];
    fm.AppntZipCode.value = RptContent[0][25];
    fm.AddAffixAccDate.value = RptContent[0][26];
    //showOneCodeName('rgtType','rgtType','rgtTypeName');
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������
    fm.ModifyRgtState.value = RptContent[0][18];//�������� Modify by zhaorx 2006-04-17  
    showOneCodeName('llrgttype','ModifyRgtState','ModifyRgtStateName');
    //****************************************************
    //����ҳ��Ȩ��
    //****************************************************
    fm.AccidentDate.readonly = true;

//    fm.QueryPerson.disabled=true;
//    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;
    fm.Remark.disabled=true;

    //���ð�ť++++++++++++++++++++++++++++++++++++++++�����

    //�����ְ�������������Ϣ(����)
    /*var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from LLCase where CaseNo = '"+ rptNo +"')";*/
//    alert("strSQL3= "+strSQL3);
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql3");
	mySql.addSubPara(rptNo); 
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
    
    //��ѯ�ⰸ��־
    QueryClaimFlag();
    
    //��ѯ��˽���
    queryAudit();
}

//��˽��۲�ѯ
function queryAudit()
{
   /* var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc,ExamConclusion,ExamIdea,ExamNoPassReason,ExamNoPassDesc from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "' and checktype='0'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql4");
	mySql.addSubPara(fm.ClmNo.value ); 
    //prompt("strSql= ",strSql);
    var tAudit = easyExecSql(mySql.getString());
//    alert(tAudit);
    if (tAudit != null)
    {
        fm.AuditConclusion.value = tAudit[0][0];
        fm.AuditIdea.value = tAudit[0][1];
        fm.SpecialRemark1.value = tAudit[0][2];
        fm.ProtestReason.value = tAudit[0][3];
        fm.ProtestReasonDesc.value = tAudit[0][4];
        
        fm.DecisionSP.value = tAudit[0][5];
        fm.DecisionSPName.value = tAudit[0][6];
        fm.ExamNoPassReason.value = tAudit[0][7];
        fm.ExamNoPassDesc.value = tAudit[0][8];

        showOneCodeName('llprotestreason','ProtestReason','ProtestReasonName');
        showOneCodeName('llclaimconclusion','AuditConclusion','AuditConclusionName');
        //��ʾ���ز�
        choiseConclusionType();
    }
}

//ѡ����˽���
function choiseConclusionType()
{
    if (fm.AuditConclusion.value == '1')
	{
        divLLAudit2.style.display= "";
    }
    else
    {
        divLLAudit2.style.display= "none";
    }
}

//ѡ����������
function choiseConfirmConclusionType()
{
    if (fm.DecisionSP.value == '1')
	{
        divLLConfirm2.style.display= "";
    }
    else
    {
        divLLConfirm2.style.display= "none";
    }
}

//ѡ��SubReportGrid��Ӧ�¼�,tPhase=0Ϊ��ʼ��ʱ����
function SubReportGridClick(tPhase)
{
	//********************************************Beg
	//�ÿ���ر�
	//********************************************
	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	//fm.IsVip.value = "";
	//fm.VIPValueName.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.OtherAccidentDate.value = "";
	fm.MedicalAccidentDate.value = "";
	fm.accidentDetail.value = "";
	fm.accidentDetailName.value = "";
//	fm.IsDead.value = "";
//	fm.IsDeadName.value = "";
	fm.claimType.value = "";
	fm.cureDesc.value = "";
	fm.cureDescName.value = "";
	fm.AccResult1.value = "";
	fm.AccResult1Name.value = "";
	fm.AccResult2.value = "";
	fm.AccResult2Name.value = "";
	//���������ÿ�
    for (var j = 0;j < fm.claimType.length; j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //********************************************End

    //ȡ������
    var i = "";
    if (tPhase == "0")
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != '0')
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,5));
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
        //fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
    /*var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql5");
	mySql.addSubPara(fm.RptNo.value );    
	mySql.addSubPara(fm.customerNo.value );             
//    alert(tClaimType);
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
    	  alert("��������Ϊ�գ�����˼�¼����Ч�ԣ�");
    	  return;
    }
    else
    {
        for(var j=0;j<fm.claimType.length;j++)
        {
        	  for (var l=0;l<tClaimType.length;l++)
        	  {
        	  	  var tClaim = tClaimType[l].toString();
        	  	  tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//ȡ�������ͺ���λ
//        	  	  alert(tClaim);
        	  	  if(fm.claimType[j].value == tClaim)
        	  	  {
                	  fm.claimType[j].checked = true;
            	  }
            }
        }
    }
    //��ѯ�ְ�����Ϣ
    var tSubReport = new Array;
    // var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2 from LLCase where 1=1 "
    //             + getWherePart( 'CaseNo','RptNo' )
    //             + getWherePart( 'CustomerNo','customerNo' );
   //modifyed by niuzj,2005-11-05
  /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,EditFlag,AffixConclusion,(case EditFlag when '0' then '��' when '1' then '��' end),(case AffixConclusion when '0' then '��' when '1' then '��' end),medaccdate  from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql6");
	mySql.addSubPara(fm.RptNo.value );    
	mySql.addSubPara(fm.customerNo.value );               
    //alert(strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.OtherAccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    
    //*************Added by niuzj,2005-11-05*********************
       fm.IsModify.value = tSubReport[0][9];
       fm.IsAllReady.value = tSubReport[0][10];
       fm.IsModifyName.value = tSubReport[0][11];
       fm.IsAllReadyName.value = tSubReport[0][12];
       fm.MedicalAccidentDate.value = tSubReport[0][13];
    //************************************************************
    
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
    queryHospital('hospital','TreatAreaName');
}

//ѡ��PolDutyCodeGrid��Ӧ�¼�
function PolDutyCodeGridClick()
{
    //��ձ�
    fm.GiveType.value = "";//�⸶����
    fm.RealPay.value = "";
    fm.AdjReason.value = "";//����ԭ��
    fm.AdjReasonName.value = "";//
    fm.AdjRemark.value = "";//������ע
    fm.GiveReasonDesc.value = "";//�ܸ�����
    fm.GiveReason.value = "";//�ܸ�ԭ�����
    fm.SpecialRemark.value = "";//���ⱸע
    //�õ�mulline��Ϣ
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,14);//�⸶����
        fm.Currency.value = PolDutyCodeGrid.getRowColData(i,9);
        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,22);
        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,23);//����ԭ��
        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,24);//
        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,25);//������ע
        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,16);//�ܸ�ԭ�����
        fm.GiveReasonDesc.value = PolDutyCodeGrid.getRowColData(i,18);//�ܸ�����
        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,19);//���ⱸע
        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
        showOneCodeName('llprotestreason','GiveReason','GiveReasonName');
    }
    //��ʾ���ز�
    divBaseUnit5.style.display= "";
    choiseGiveTypeType();
}

//ѡ���⸶����
function choiseGiveTypeType()
{
    if (fm.GiveType.value == '0')
	{
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
    }
    else if (fm.GiveType.value == '1')
    {
        divGiveTypeUnit1.style.display= "none";
        divGiveTypeUnit2.style.display= "";
    }
}

//ƥ���Ĳ�ѯ
function afterMatchDutyPayQuery()
{
    var tSql;
    var arr;

    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���


    //��ѯ�����ⰸ�Ľ��
   /* tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql7");
	mySql.addSubPara(tClaimNo);      
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,ClaimGrid);
    }
    else
    {
        initClaimGrid();
    }
    
    
    //��ѯLLClaimDutyKind�������ⰸ�������ͽ��в���
   /* tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and trim(c.code)=trim(a.GetDutyKind)),"
       +" a.TabFeeMoney,a.SelfAmnt,a.ClaimMoney,a.StandPay,a.SocPay,a.OthPay,a.RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/

	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql8");
	mySql.addSubPara(tClaimNo);      
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,DutyKindGrid);
    }
    else
    {
        initDutyKindGrid();
    }


    //��ѯLLClaimPolicy,��ѯ�����������Ͳ������Ϣ
   /* tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.ClmNo = '"+tClaimNo+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql9");
	mySql.addSubPara(tClaimNo);      
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyKindGrid);
    }
    else
    {
        initPolDutyKindGrid();
    }
    

    //��ѯLLClaimDetail,��ѯ�����������ͱ���������Ϣ
    /*tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetClm c where trim(c.GetDutyKind) = trim(a.GetDutyKind) and trim(c.GetDutyCode) = trim(a.GetDutyCode)),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0)," //�������� + ������--+ a.GracePeriod
       +" a.Amnt,a.YearBonus,a.EndBonus,"
       +" a.StandPay,a.GiveType, "
       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion' and trim(e.code)=trim(a.GiveType)),"
       +" a.GiveReason,"
       +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and trim(f.code)=trim(a.GiveReason)),"
       +" a.GiveReasonDesc,a.SpecialRemark,"
       +" a.PrepaySum ,"//Ԥ�����
       +" '',"
       +" a.RealPay,a.AdjReason,"
       +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and trim(g.code)=trim(a.AdjReason)),"
       +" a.AdjRemark, "
       +" a.PrepayFlag,case a.PrepayFlag when '0' then '��Ԥ��' when '1' then '��Ԥ��' end,"
       +" case a.PolSort when 'A' then '�б�ǰ' when 'B' then '����' when 'C' then '����' end "
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode" 
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GiveType not in ('2')"
       ;*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql10");
	mySql.addSubPara(tClaimNo);      
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
    else
    {
        initPolDutyCodeGrid();
    }    
}

//���˴���
function SecondUWInput()
{
	  var strUrl="LLDealUWsecondMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value;    
   /// window.open(strUrl,"���˴���");
   window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//[���⴦��]��ť<>
function showExempt()
{
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var strUrl="LLColQueryExemptInput.jsp?ClaimNo="+tClaimNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��ͬ����(��xutao��2005��7��14�����)
function showContDeal()
{
    var strUrl="LLColQueryContDealMain.jsp?ClmNo="+fm.ClmNo.value;
    strUrl= strUrl + "&CustNo=" + fm.customerNo.value;
    strUrl= strUrl + "&ConType="+fm.RgtClass.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��������
function showPolDeal()
{
    var strUrl="LLColQueryPolDealInput.jsp?ClmNo="+fm.ClmNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����˷���
function showBnf()
{
	var rptNo = fm.RptNo.value;//�ⰸ��
    var strUrl="LLBnfInput.jsp?claimNo="+rptNo+"&BnfKind=A&InsuredNo="+fm.customerNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�����˷���");
}

//�ʻ�����(��yuejw��2005��7��11�����)
function showLCInsureAccont()
{
	var rptNo = fm.RptNo.value;//�ⰸ��
    var strUrl="LCInsureAccMain.jsp?RptNo="+rptNo+"";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�ʻ�����");
}
 
 
//¼��ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeInp()
{
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;     //�ͻ���
    
    var strUrl="LLColQueryMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.MedicalAccidentDate.value+"&otherAccDate="+fm.OtherAccidentDate.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');


}
  
//����ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeCal()
{
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;     //�ͻ���

    var strUrl="LLClaimRegMedFeeCalMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
    



//������ѯ
//���ա��ͻ��š���LCpol�н��в�ѯ����ʾ�ÿͻ��ı�����ϸ
function showInsuredLCPol()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
//	var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-24
  var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����ⰸ��ѯ
function showOldInsuredCase()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
//	var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-24
  var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//����ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeCal()
{
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���

    var strUrl="LLClaimRegMedFeeCalMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�鿴����
function showQueryInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
	  //***************************************
	  //�жϸ��ⰸ�Ƿ���ڵ���
	  //***************************************
    /*var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql11");
	mySql.addSubPara(fm.RptNo.value );      
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("���ⰸ��û�е�����Ϣ��");
    	  return;
    }
	  var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�鿴����");
}

//���¹�������Ϣ
function showClaimDesc()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }    
    var strUrl="LLClaimDescMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&startPhase=03";
//    window.open(strUrl,"�¹�������Ϣ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
   // window.open(strUrl,"�¹�������Ϣ");
   window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//�򿪳ʱ���ѯ
function showQuerySubmit()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
	//***************************************
	//�жϸ��ⰸ�Ƿ���ڳʱ�
	//***************************************
   /* var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql12");
	mySql.addSubPara(fm.RptNo.value );    
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("���ⰸ��û�гʱ���Ϣ��");
    	  return;
    }
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
//    window.open(strUrl,"�ʱ���ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
   // window.open(strUrl,"�ʱ���ѯ");
   window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//�����ύ
function submitForm()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
   // showSubmitFrame(mDebug);
//    fm.fmtransact.value = "INSERT" 

    fm.action = './LLClaimConfirmSave.jsp';
    
    //���ӷֹ�˾�ϱ����̵Ĵ����ϱ���û���й�������ת��ֻ������ȷ�������ˣ����ܹ�˾��������
    if(fm.DecisionSP.value == '2')
    {
    	
    	//var tPopedomSql="select count(1) from LLClaimUser where JobCategory='A' and UserCode='"+document.all('UserCode').value+"'";
    	//prompt("",tPopedomSql);
    	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql13");
	mySql.addSubPara(document.all('UserCode').value );    
    	var PopedomCount=easyExecSql(mySql.getString());
    	if(parseInt(PopedomCount)>0)
		{
		        alert("��ǰ�û��Ѿ������Ȩ��,�������ϱ�!");
		        return;
		 }
    	
    	/****�����������������ϱ��ϲ�������ͨ��saveҳ���̨������*****/
       //fm.fmtransact.value = "UPREPORT";
       //fm.action = './LLClaimConfirmReportBackSave.jsp';
    }
    else
    {
    	    //���Ӷ��˵���ʾ 2010-4-8 16:25
    //�����δ����Ķ��ˣ�����ϵͳ��ʾ��������ȷ��
		 // var strSqlCuw = "select count(*) from LLCUWBatch where  caseno = '" + fm.RptNo.value + "' and InEffectFlag='0'" ;
		  mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql14");
	mySql.addSubPara(fm.RptNo.value );    
		  var tCountcuw = easyExecSql(mySql.getString());
	
		  if (tCountcuw>0)
		  {     
			  if(!confirm("��ע�⣬��δ������Ķ��˽��ۣ���ȷ�Ͻ��н᰸����"))
		       {
		          return false;
		       }
		   
	    }
   }
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

//���ذ�ť
function goToBack()
{
	
  if (fm.RgtObjNo.value!=null && fm.RgtObjNo.value!="")
  { 
    location.href="../claimgrp/LLGrpClaimConfirmMissInput.jsp";
  }    
  else
  {
  	location.href="LLClaimConfirmMissInput.jsp";
  }

}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        goToBack();
    }
    tSaveFlag ="0";
}

//��������ȷ��
function ConfirmSaveClick()
{
	  if(!KillTwoWindows(fm.RptNo.value,'40'))
	  {
	  	  return false;
	  }
	  
	  //���������˷������Ƿ������У��
	  if (fm.AuditConclusion.value == "0" || fm.AuditConclusion.value == "1")
	  {
	  	        //ֻ�л���ʱ����Ҫ�������˷���
       /* var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 + getWherePart( 'ClmNo','ClmNo' );*/
        mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimConfirmInputSql");
	mySql.setSqlId("LLClaimConfirmSql15");
	mySql.addSubPara(fm.ClmNo.value ); 
        var tDutyKind = easyExecSql(mySql.getString());
        if (tDutyKind != '09')
        {
           /* var tBalanceMoney =0.00;
            var tBnfMoney=0.00;
            var tBalanceMoneySql = "select nvl(sum(pay),0) from LLBalance where clmno='"+document.all('RptNo').value+"'";
            var tBnfMoneySql = "select nvl(sum(getmoney),0) from llbnf where clmno='"+document.all('RptNo').value+"'";
             tBalanceMoney = easyExecSql(tBalanceMoneySql);
             tBnfMoney = easyExecSql(tBnfMoneySql);
            if(Arithmetic(tBalanceMoney,'-',tBnfMoney,2)!=0)
						{
						  alert("�����������������˷����ܽ�һ�£����ʵ�Ƿ����δ����ľ����")
						  return false;
						}*/
						//2009-9-29 18:30 zy ����У��������ʱ����
						//		var tFeetype ="select distinct feeoperationtype from llbnf where clmno='"+document.all('RptNo').value+"'";
								
								mySql = new SqlClass();
								mySql.setResourceName("claim.LLClaimConfirmInputSql");
								mySql.setSqlId("LLClaimConfirmSql23");
								mySql.addSubPara(document.all('RptNo').value ); 
								
								var tFeetypeValue = easyExecSql(mySql.getString());
								for (var t=0;t<tFeetypeValue.length;t++)
								{
									var tBalanceMoney =0.00;
                	var tBnfMoney=0.00;
									if(tFeetypeValue[t]=='A' || tFeetypeValue[t]=='B')
									 {
								//	 		var tBalanceMoneySql = "select nvl(sum(pay),0) from LLBalance where clmno='"+document.all('RptNo').value+"' and feeoperationtype ='A'";
						            mySql = new SqlClass();
									mySql.setResourceName("claim.LLClaimConfirmInputSql");
									mySql.setSqlId("LLClaimConfirmSql16");
									mySql.addSubPara(document.all('RptNo').value ); 
									tBalanceMoney = easyExecSql(mySql.getString());
				             // var tBnfMoneySql = "select nvl(sum(getmoney),0) from llbnf where clmno='"+document.all('RptNo').value+"' and feeoperationtype  in ('A','B')";
						            mySql = new SqlClass();
									mySql.setResourceName("claim.LLClaimConfirmInputSql");
									mySql.setSqlId("LLClaimConfirmSql17");
									mySql.addSubPara(document.all('RptNo').value ); 			               
						            tBnfMoney = easyExecSql(mySql.getString());
				              if(Arithmetic(tBalanceMoney,'-',tBnfMoney,2)!=0)
											{
											  alert("�����������������˷����ܽ�һ�£����ʵ�Ƿ����δ����ľ����")
											  return false;
											}
									 }
									else
										{
											//var tBalanceMoneySql = "select nvl(sum(pay),0) from LLBalance where clmno='"+document.all('RptNo').value+"' and feeoperationtype not in ('A','B')";
						                    mySql = new SqlClass();
											mySql.setResourceName("claim.LLClaimConfirmInputSql");
											mySql.setSqlId("LLClaimConfirmSql18");
											mySql.addSubPara(document.all('RptNo').value ); 	
											tBalanceMoney = easyExecSql(mySql.getString());	
						                    //var tBnfMoneySql = "select nvl(sum(getmoney),0) from llbnf where clmno='"+document.all('RptNo').value+"' and feeoperationtype not in ('A','B')";
						                    mySql = new SqlClass();
											mySql.setResourceName("claim.LLClaimConfirmInputSql");
											mySql.setSqlId("LLClaimConfirmSql19");
											mySql.addSubPara(document.all('RptNo').value ); 	 
						                    tBnfMoney = easyExecSql(mySql.getString());
				              if(Arithmetic(tBalanceMoney,'-',tBnfMoney,2)!=0)
											{
											  alert("�����������������˷����ܽ�һ�£����ʵ�Ƿ����δ����ľ����")
											  return false;
											}

										}
									}
        }
	  }
	  
	//�жϱ������Ƿ�Ϊ��
	if (fm.DecisionSP.value == "" || fm.DecisionSP.value == null)
	{
		alert("������д��������!");
        return;
	}
	
	//�жϱ������Ƿ�Ϊ��
	if (fm.RemarkSP.value == "" || fm.RemarkSP.value == null)
	{
		alert("������д�������!");
        return;
	}
	
	
    //ͨ��ʱ��У��
    if (fm.DecisionSP.value == "0")
    {
        //����Ƿ���ڶ��˵ļӷ���Ϣ�����б������(by zl 2006-1-6 11:40)
        if (!checkLjspay(fm.RptNo.value))
        {
            return;//�˴����ù��з���
        }
        
        //��������У��
       /* var strSQL2 = "select realpay from llclaim where 1=1"
                    + getWherePart( 'ClmNo','RptNo' ) ;*/
         mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimConfirmInputSql");
		mySql.setSqlId("LLClaimConfirmSql20");
		mySql.addSubPara(document.all('RptNo').value ); 	
        var tRealPay = easyExecSql(mySql.getString());
        var tMoney = parseInt(tRealPay);

        if (fm.AuditConclusion.value == '0' && tMoney < 0)
        {
        	alert("���ⰸ�⸶���Ϊ����,������ͨ��!");
        	fm.isReportExist.value = "true";
        	return;
        }
    }

    //��������Ϊ'1'����ͨ��ʱ����ͨ��ԭ����Ϊ�� Add by zhaorx 2006-03-08
    if(fm.DecisionSP.value == '1' && (fm.ExamNoPassReason.value == null || fm.ExamNoPassReason.value == ""))
    {
    	alert("������������ͨ��ԭ��");
    	return;
    }
    
    if(fm.DecisionSP.value == '1' && (fm.ExamNoPassDesc.value == null || fm.ExamNoPassDesc.value == ""))
    {
    	alert("������������ͨ�����ݣ�");
    	return;
    }
    
    //����
    if (confirm("����:�����������,�Ƿ���Ҫ�޸Ľ���?"))
    {
        return;
    }
    
    // �����������700�ַ�ʱ����ʾ��2006-01-01 С��
    if(fm.RemarkSP.value.length >=700)
    {
    	alert("����������ܳ���700���ַ�������������д��");
    	return;
    }

    //�ύ
    fm.isReportExist.value = "false";
    fm.fmtransact.value = "UPDATE";
    submitForm();
}

//�����˲�ѯ
function ClientQuery()
{
    window.open("LLLdPersonQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryInput.jsp");

}

//�õ�0��icd10��
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//��ѯ���ս��
function queryResult(tCode,tName)
{
    /*var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimConfirmInputSql");
		mySql.setSqlId("LLClaimConfirmSql21");
		mySql.addSubPara(document.all(tCode).value); 	
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//Ӱ���ѯ---------------2005-08-14���
function colQueryImage()
{
    var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��ӡ�ⰸ��������
function colBarCodePrint()
{
    fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    document.getElementById("fm").submit();
}


//�ص���Ϣ�޸�,by niuzj,2005-11-07
function editImpInfo()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
//	  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-24
  var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
	  var rptNo = fm.ClmNo.value;//�ⰸ��
	
	  //Added by niuzj,2005-11-07
	  //����һ������,���ڿ���"�ص���Ϣ�޸�"ҳ���ϵ�"�޸�ȷ��"��ť
	  var tIsShow=1;  //Ϊ0ʱ�ð�ť��ʹ��,Ϊ1ʱ�ð�ť����ʹ��
	  
    var strUrl="LLClaimImportModifyMain.jsp?AppntNo="+tCustomerNo+"&RptNo="+rptNo+"&IsShow="+tIsShow;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��ѯ����ҽԺ��Added by niuzj,2005-11-26
function queryHospital(tCode,tName)
{
	  /*var strSql =" select hospitalname from llcommendhospital  "
	             +" where trim(hospitalcode)='"+document.all(tCode).value+"' ";*/
	   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimConfirmInputSql");
		mySql.setSqlId("LLClaimConfirmSql22");
		mySql.addSubPara(document.all(tCode).value); 	
	  var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//ҽ�Ƶ�֤������Ϣ
function showMedicalAdjClick()
{
    var tRptNo = fm.RptNo.value;         //�ⰸ��

    var strUrl="LLColQueryMedicalFeeAdjInput.jsp?tRptNo="+tRptNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��������������--��ѯ 2006-01-13���
function LLQueryUWMDetailClick()
{
    var strUrl="LLColQueryClaimUWMDetailMain.jsp?ClmNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

