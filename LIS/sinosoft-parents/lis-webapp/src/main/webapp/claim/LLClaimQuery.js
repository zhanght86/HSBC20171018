var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();


//�����ݲ��ṩ�Ĺ���
function Block()
{
   	//alert("*****");
   	fm.BarCodePrint.disabled = true;//��ӡ������
    fm.ColQueryImage.disabled = true;//Ӱ���ѯ  
    
    fm.MedicalFeeInp.disabled = true;//ҽ�Ƶ�֤
    fm.MedicalFeeCal.disabled = true;//���ü���鿴
    fm.MedicalFeeAdj.disabled = true;//���õ����鿴
    
    fm.ViewReport.disabled = true;//�鿴�ʱ�
    fm.CreateNote.disabled = true;//�����ѯ
    fm.SecondUWResult.disabled = true;//���˽���
    
}

//�������
function showSecondUWInput()
{
	var strUrl="SecondUWInput.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value;
   // window.open(strUrl,"�������");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//�ɿͻ���ѯ��LLLdPersonQuery.js�����ص�����¼ʱ����
function afterQueryLL(arr)
{
    fm.customerNo.value = arr[0];
    fm.customerName.value = arr[1];
    fm.customerSex.value = arr[2];
    fm.customerAge.value = arr[3];
//    fm.IsVip.value = arr[4];
    if(arr[4]==null || arr[4]=="" || arr[4]==0)
    {
     	fm.VIPValueName.value ="��";
     	fm.IsVip.value = "0";
    }
    else
    {
     	fm.IsVip.value = arr[4];
     	fm.VIPValueName.value ="��";
    }
    showOneCodeName('sex','customerSex','SexName');//�Ա�
    //��ʼ��¼����
    fm.hospital.value = "";
    fm.TreatAreaName.value = "";
    fm.MedicalAccidentDate.value = "";//ҽ�Ƴ�������
    fm.OtherAccidentDate.value = "";//������������
    fm.accidentDetail.value = "";
    fm.IsDead.value = "";
    fm.cureDesc.value = "";
//    fm.Remark.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  fm.claimType[j].checked = false;
    }
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
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql1");
	mySql.addSubPara(rptNo ); 
    //prompt("�����ⰸ��ѯ-�����¼��š������¹ʷ�������(һ��)",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18---------19
   /* var strSQL2 =" select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate, "
                +" (select username from llclaimuser where usercode = llregister.Operator),mngcom, "
                +" (case assigneetype when '0' then 'ҵ��Ա' when '1' then '�ͻ�' end),assigneecode,assigneename, "
                +" (case assigneesex when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end), "
                +" assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass, "
                +" (case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end),clmstate, "
                +" (case getmode when '1' then 'һ��ͳһ����' when '2' then '�����ʽ��ȡ' when '3' then '����֧��' end),"
                +" accepteddate ,applydate,Rgtantmobile,postcode,(select ReAffixDate from LLAffix where rgtno=llregister.rgtno and rownum=1)  "
                +" from llregister where 1=1 "
                +" and rgtno = '"+rptNo+"'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql2");
	mySql.addSubPara(rptNo );            
    //prompt("�����ⰸ��ѯ-���������ż�����������Ϣ(һ��)",strSQL2);
    var RptContent = easyExecSql(mySql.getString());
    
    if (null == RptContent) {
    	alert('û�в�ѯ�������������ⰸ��');
    	alert("���ڼ����رգ�");
    	top.close();
    	return;
    }

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
    fm.rgtType.value = RptContent[0][18];
    fm.RgtClass.value = RptContent[0][19];
    fm.RgtClassName.value = RptContent[0][20];
    fm.ClmState.value = RptContent[0][21];
    fm.GetMode.value = RptContent[0][22];
    fm.AcceptedDate.value = RptContent[0][23];
    fm.ApplyDate.value = RptContent[0][24];
    fm.RptorMoPhone.value = RptContent[0][25];
    fm.AppntZipCode.value = RptContent[0][26];
    fm.AddAffixAccDate.value = RptContent[0][27];
   // fm.AddAffixAccDate.value = '';
    showOneCodeName('llrgttype','rgtType','rgtTypeName');
    showOneCodeName('llclaimstate','ClmState','ClmStateName');
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������

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
    //��������߰���,����ʾ[ԭ�ⰸ�鿴]��ť,by niuzj,2005-11-14
    if(fm.rgtType.value=='13')
    {
    	spanIsShow.style.display="";
    }

    //�����ְ�������������Ϣ(����)
    /*var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' else '����' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag, "
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from LLCase where CaseNo = '"+ rptNo +"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql3");
	mySql.addSubPara(rptNo ); 
    //prompt("�����ⰸ��ѯ-�����ְ�������������Ϣ(����)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
    
    
    //��ѯ�ⰸ��־
    QueryClaimFlag();
    //����ͨ��
    if (fm.RgtConclusion.value == '01')
    {
        divPart2.style.display = '';
        //��ѯ��˽���
        queryAudit();
        //��ѯ��������
        queryConfirm();
        //��ѯ�᰸��Ϣ
        queryEndCase();
        //��ѯ����ʵ��
        queryConfDate();
    }                       
    //�������� Add by zhaorx 2006-03-06
    if (fm.RgtConclusion.value == '02')
    {
        divPart3.style.display = '';
        //��ѯ��������ԭ��
        queryNoRgtReason();
    }    
    
}

//��˽��۲�ѯ
function queryAudit()
{
   /* var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc,(select b.username from llclaimuser b where b.usercode = LLClaimUWMain.AuditPer),AuditDate from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql4");
	mySql.addSubPara(fm.ClmNo.value ); 
//    alert("strSql= "+strSql);
    var tAudit = easyExecSql(mySql.getString());
//    alert(tAudit);
    if (tAudit != null)
    {
        fm.AuditConclusion.value = tAudit[0][0];
        fm.AuditIdea.value = tAudit[0][1];
        fm.SpecialRemark1.value = tAudit[0][2];
        fm.ProtestReason.value = tAudit[0][3];
        fm.ProtestReasonDesc.value = tAudit[0][4];
        fm.AuditPer.value = tAudit[0][5];
        fm.AuditDate.value = tAudit[0][6];
        showOneCodeName('llprotestreason','ProtestReason','ProtestReasonName');
        showOneCodeName('llclaimconclusion','AuditConclusion','AuditConclusionName');
        if (fm.AuditConclusion.value != '')
        {
            spanAudit.style.display = '';
        }
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

//�������۲�ѯ
function queryConfirm()
{
    /*var strSql = " select ExamConclusion,ExamIdea,ExamNoPassReason,ExamNoPassDesc,(select b.username from llclaimuser b where b.usercode = LLClaimUWMain.ExamPer),ExamDate from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql5");
	mySql.addSubPara(fm.ClmNo.value );            
//    alert("strSql= "+strSql);
    var tConfirm = easyExecSql(mySql.getString());
//    alert(tConfirm);
    if (tConfirm != null)
    {
        fm.DecisionSP.value = tConfirm[0][0];
        fm.RemarkSP.value = tConfirm[0][1];
        fm.ExamNoPassReason.value = tConfirm[0][2];
        fm.ExamNoPassDesc.value = tConfirm[0][3];
        fm.ExamPer.value = tConfirm[0][4];
        fm.ExamDate.value = tConfirm[0][5];
        showOneCodeName('llexamconclusion','DecisionSP','DecisionSPName');
        showOneCodeName('llexamnopassreason','ExamNoPassReason','ExamNoPassReasonName');
        if (fm.DecisionSP.value != '')
        {
            spanConfirm.style.display = '';
        }
        //��ʾ���ز�
        choiseQueryConclusionType();
    }
}

//ѡ����������
function choiseQueryConclusionType()
{
    if (fm.DecisionSP.value == '1')
	{
        divLLQuery2.style.display= "";
    }
    else
    {
        divLLQuery2.style.display= "none";
    }
}

//�᰸��Ϣ��ѯ
function queryEndCase()
{
    /*var strSql = " select a.realpay,(select b.username from llclaimuser b where b.usercode = a.operator),a.endcasedate from llclaim a where "
               + " ClmNo = '" + fm.ClmNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql6");
	mySql.addSubPara(fm.ClmNo.value );               
//    alert("strSql= "+strSql);
    var tEndCase = easyExecSql(mySql.getString());
    if (tEndCase != null)
    {
    	fm.ClmCurrency.value = tEndCase[0][0];
        fm.ClmEndRealPay.value = "��" + tEndCase[0][1];
        fm.ClmEndPer.value = tEndCase[0][2];
        fm.ClmEndDate.value = tEndCase[0][3];

        if (fm.ClmEndDate.value != '')
        {
            spanEndCase.style.display = '';
        }
    }
}

//����ʵ����ѯ
function queryConfDate()
{
    //��ѯ����ʵ����¼
    /*var strSql = " select count(1) from ljaget a where a.enteraccdate is null "
               + " and a.otherno = '" + fm.ClmNo.value + "'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql7");
	mySql.addSubPara(fm.ClmNo.value );             
    var tConfDate = easyExecSql(mySql.getString());
    var tValue = "";
    if (tConfDate == "0")
    {
        tValue = "��";
    }
    else
    {
        tValue = "��";
    }
    
//    //�ڹرյ�״̬ʱʵ�����޼�¼
//    var strSql2 = " select count(1) from ljaget a where 1=1 "
//                + " and a.otherno = '" + fm.ClmNo.value + "'";
//    var tCount = easyExecSql(strSql2);
//    if (tCount == "0")
//    {
//        tValue = "��";
//    }
    
    fm.confGetMoney.value = tValue;
}

//��������ԭ���ѯ
function queryNoRgtReason()
{
	/*var tSQLF = " select a.codename from ldcode a where 1 = 1 and a.codetype= 'llnorgtreason' "
	          + " and exists (select 'X' from llregister where rgtno= '" + fm.ClmNo.value + "' "
	          + " and norgtreason= a.code and rgtconclusion= '02' ) "; */
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql8");
	mySql.addSubPara(fm.ClmNo.value );    
	var tResult = easyExecSql(mySql.getString());
	if(tResult != null)
	{ 
		fm.NoRgtReasonName.value = tResult[0][0];
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
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.MedicalAccidentDate.value = "";
	fm.OtherAccidentDate.value = "";
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
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql9");
	mySql.addSubPara(fm.RptNo.value );      
	mySql.addSubPara(fm.customerNo.value );              
    //prompt("�����ⰸ��ѯ-��ѯ�����������",strSQL1);
    tClaimType = easyExecSql(mySql.getString());
    //alert("tClaimType"+tClaimType);
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
    //--------------------------1----------2---------3-----------4---------5------6-----7-------------8----------9----------10---------11
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,EditFlag,AffixConclusion,"
    	          + " (case EditFlag when '0' then '��' when '1' then '��' end),(case AffixConclusion when '0' then '��' when '1' then '��' end),Medaccdate from LLCase where 1=1 "
                  + getWherePart( 'CaseNo','RptNo' )
                  + getWherePart( 'CustomerNo','customerNo' );*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql10");
	mySql.addSubPara(fm.RptNo.value );      
	mySql.addSubPara(fm.customerNo.value );                
    //prompt("�����ⰸ��ѯ-��ѯ�ְ�����Ϣ:",strSQL2);
    tSubReport = easyExecSql(mySql.getString());
    //alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.OtherAccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.IsModify.value = tSubReport[0][9];
    fm.IsAllReady.value = tSubReport[0][10];
    fm.IsModifyName.value = tSubReport[0][11];
    fm.IsAllReadyName.value = tSubReport[0][12];
    fm.MedicalAccidentDate.value = tSubReport[0][13];
//    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    queryHospital('hospital','TreatAreaName');
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
//    showOneCodeName('IsAllReady','IsModify','IsModifyName');//��Ҫ��Ϣ�޸ı�־
//    showOneCodeName('IsAllReady','IsAllReady','IsAllReadyName');//��֤��ȫ��־

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
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql11");
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
    /*tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and trim(c.code)=trim(a.GetDutyKind)),"
       +" a.TabFeeMoney,a.ClaimMoney,a.StandPay,a.SocPay,a.OthPay,a.RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/

	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql12");
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
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql13");
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
   /* tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
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
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql14");
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
	  var strUrl="LLDealUWsecondMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value+"&Flag=N";
   // window.open(strUrl,"���˴���");
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
	var tSel = SubReportGrid.getSelNo();

    if( tSel == 0 || tSel == null ){
    alert( "����ѡ��һ����¼����ִ�д˲���!!!" );
    }
    else
    {
	var rptNo = fm.RptNo.value;//�ⰸ��
    var strUrl="LLColQueryBnfInput.jsp?claimNo="+rptNo+"&InsuredNo="+fm.customerNo.value+"&BnfKind=A";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
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
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql15");
	mySql.addSubPara(fm.RptNo.value);              
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
    window.open(strUrl,"");
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
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql16");
	mySql.addSubPara(fm.RptNo.value);                   
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("���ⰸ��û�гʱ���Ϣ��");
    	  return;
    }
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
//    window.open(strUrl,"�ʱ���ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//�����ύ
function submitForm()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   // showSubmitFrame(mDebug);
//    fm.fmtransact.value = "INSERT"
    fm.action = './LLClaimQuerySave.jsp';
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

//���ذ�ť
function goToBack()
{
//    if (fm.Phase.value == '0')
//    {
//        location.href = "LLClaimQueryMissInput.jsp"; //�ۺϲ�ѯ����
//    }
    if (fm.Phase.value == '0')
    {
        top.close();  //�᰸����
    }
    else
    {
        location.href = "LLLClaimQueryMain.jsp?AppntNo=" + fm.Phase.value + "&ClmNo=" + fm.RptNo.value; //�����ⰸ��ѯ����
    }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
       // showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
   /* var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql17");
	mySql.addSubPara(document.all(tCode).value);    
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//��ѯҽ������ Modify by zhaorx 2007-01-18
function queryHospital(tCode,tName)
{
    /*var strSql = " select Hospitalname from LLCommendHospital "
               + " where hospitalcode = '" + document.all(tCode).value + "' ";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql18");
	mySql.addSubPara(document.all(tCode).value);  
    var tHosName = easyExecSql(mySql.getString());
    if (tHosName)
    {
        document.all(tName).value = tHosName;
    }
}

//��������������--��ѯ 2005-08-16���
function LLQueryUWMDetailClick()
{
//	alert("��������������ѯ");
    var strUrl="LLColQueryClaimUWMDetailMain.jsp?ClmNo="+fm.ClmNo.value;
//    window.open(strUrl,"��������������ѯ");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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

//������ѯ
function showReport()
{
    //var strUrl="LLClaimQueryReportInput.jsp?claimNo="+document.all('ClmNo').value+"&phase=1";
    //window.open(strUrl,"������ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
   var clmType=document.all('ClmNo').value.substr(11,1);

   if(clmType=="6")
   {
   	 var strUrl="LLClaimQueryReportInput.jsp?claimNo="+document.all('ClmNo').value+"&phase=1";
     window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
   }
   else
   {
 
   	 //var strUrl="LLClaimQueryReportInput.jsp?claimNo="+document.all('ClmNo').value+"&phase=1";
   	 var strUrl="LLClaimReportQueryMain.jsp?claimNo="+document.all('ClmNo').value+"&customerNo="+document.all('customerNo').value+"&Flag=query";
     window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

   }

   
}

//�������ѯ
function queryLLIssue()
{
	//��ϢУ��
    var rptNo = document.all('ClmNo').value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    var strUrl="LLClaimIssueQueryMain.jsp?claimNo="+document.all('ClmNo').value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//¼��ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeInp()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���
    if (tCustNo == "")
    {
      alert("��ѡ������ˣ�");
      return;
    }
    
    //var tClaimNo = fm.RptNo.value;         //�ⰸ��
    //var tCaseNo = fm.RptNo.value;          //�ְ���
    //var tCustNo = fm.customerNo.value;     //�ͻ���
    
    var strUrl="LLColQueryMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate1="+fm.AccidentDate.value+"&accDate2="+fm.OtherAccidentDate.value;    
    window.open("LLColQueryMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate1="+fm.AccidentDate.value+"&accDate2="+fm.OtherAccidentDate.value,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

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

//ҽ�Ƶ�֤������Ϣ
function showMedicalAdjClick()
{
    var tRptNo = fm.RptNo.value;         //�ⰸ��

    var strUrl="LLColQueryMedicalFeeAdjInput.jsp?tRptNo="+tRptNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�ռ�����Ϣ¼��,Added by niuzj,2005-10-27
function showReciInfoInp()
{
	var tClmNo = fm.RptNo.value;   //�ⰸ��
	var tcustomerNo=fm.customerNo.value;  //�����˴���
	var tIsShow = 0;               //[����]��ť�ܷ���ʾ,0-����ʾ,1-��ʾ
  var tRgtObj = 1;               //�������ձ�־,1-����,2-����
	
	//�õ���ҳʱ,���дһ��Mainҳ��
	var strUrl="LLClaimReciInfoMain.jsp?ClmNo="+tClmNo+"&IsShow="+tIsShow+"&RgtObj="+tRgtObj+"&CustomerNo="+tcustomerNo;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 
	//location.href="LLClaimReciInfoInput.jsp?claimNo="+tClmNo; 
}


//��֤������Ϣ��ѯ,Added by niuzj,2005-11-01
function PrtAgainInfo()
{ 
	//alert("��֤������Ϣ��ѯ");
	var tClmNo = fm.RptNo.value;   //�ⰸ��
	/*var strSQL =" Select count(*) from loprtmanager t "
	           +" where t.otherno='" + tClmNo + "' "
	           +" and t.patchflag='1' ";*/
	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql19");
	mySql.addSubPara(tClmNo);  
	var arr = easyExecSql( mySql.getString() );
  if(arr==0 || arr==null || arr=="")  //���ֵΪ1,��������
  {
    alert("���ⰸ��û�в�����ĵ�֤!");
  } 
  else
  {
	  var strUrl = "LLClaimPrtAgainInfoMain.jsp?ClmNo="+tClmNo;
	  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 
  }
 
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
	  var rptNo = fm.RptNo.value;//�ⰸ��
	
	  //Added by niuzj,2005-11-07
	  //����һ������,���ڿ���"�ص���Ϣ�޸�"ҳ���ϵ�"�޸�ȷ��"��ť
	  var tIsShow=1;  //Ϊ0ʱ�ð�ť��ʹ��,Ϊ1ʱ�ð�ť����ʹ��
	  
    var strUrl="LLClaimImportModifyMain.jsp?AppntNo="+tCustomerNo+"&RptNo="+rptNo+"&IsShow="+tIsShow;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//by niuzj,2005-11-08
//�����������Ϊ���߰���,����ʾ[ԭ�ⰸ�鿴]��ť
function OriClmLook()
{
	//alert("��������Ϊ���߰���ʱ,���Բ鿴ԭ�ⰸ��Ϣ!");
	//���ߺ���ⰸ��
	var tAppNo = fm.RptNo.value; 
	//alert("tAppNo="+tAppNo);
	//��ѯ������ǰ���ⰸ��
	///var strSQL =" select a.caseno from llappeal a where a.appealno='" + tAppNo + "' ";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimQueryInputSql");
	mySql.setSqlId("LLClaimQuerySql20");
	mySql.addSubPara(tAppNo);  
	var arr = easyExecSql(mySql.getString());
	if(arr[0]=="" || arr[0]==null)
	{
		alert("�����߰���û�ж�Ӧ��ԭ�ⰸ!");
	}
  else
  {
  	var tClmNo = arr[0];
  	//alert("tClmNo="+tClmNo);����������ҳ��
  	var strUrl="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
  	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  }

}
