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

//¼��ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeInp()
{

    //var tSel = SubReportGrid.getSelNo();
    //var tClaimNo = SubReportGrid.getRowColData(tSel - 1,8);         //�ⰸ��
    //var tCaseNo = SubReportGrid.getRowColData(tSel - 1,2);          //�ְ���
    //var tCustNo = SubReportGrid.getRowColData(tSel - 1,1);          //�ͻ���
//    if( tSel == 0 || tSel == null ){
//        alert( "����ѡ��һ����¼����ִ�д˲���!!!" );
//    }else{
//        window.open("LLMedicalFeeInpInput.jsp?clamNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&custName="+tCustName+"&custSex="+tCustSex,"ҽ�Ƶ�֤��Ϣ");
//    }

    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���

    //alert(tClaimNo);
    var strUrl="LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate1="+fm.AccidentDate.value+"&accDate2="+fm.AccidentDate2.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

//�ɿͻ���ѯ��LLLdPersonQuery.js�����ص�����¼ʱ����
function afterQueryLL(arr)
{
    fm.customerNo.value = arr[0];
    fm.customerName.value = arr[1];
    fm.customerSex.value = arr[2];
    fm.customerAge.value = arr[3];
    fm.IsVip.value = arr[4];
    showOneCodeName('sex','customerSex','SexName');//�Ա�
    //��ʼ��¼����
    fm.hospital.value = "";
    fm.TreatAreaName.value = "";
    fm.AccidentDate2.value = "";
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
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql1");
	mySql.addSubPara(rptNo); 
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
//    alert("AccNo= "+AccNo);
    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18---------19
   /* var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,(case assigneetype when '0' then 'ҵ��Ա' when '1' then '�ͻ�' end),assigneecode,assigneename,(case assigneesex when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass,(case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end) from llregister where "
                + "rgtno = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql2");
	mySql.addSubPara(rptNo); 
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(mySql.getString());
//    alert("RptContent= "+RptContent);
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
    showOneCodeName('llrgttype','rgtType','rgtTypeName');
    showOneCodeName('relation','Relation','RelationName');//���������¹��˹�ϵ
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

    //�����ְ�������������Ϣ(����)
   /* var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql3");
	mySql.addSubPara(rptNo);             
//    alert("strSQL3= "+strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
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
	fm.IsVip.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.AccidentDate2.value = "";
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
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,4));
        fm.IsVip.value = SubReportGrid.getRowColData(i,5);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
    /*var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql4");
	mySql.addSubPara(fm.RptNo.value);   
		mySql.addSubPara(fm.customerNo.value);                
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
    /*var strSQL2 = "select HospitalCode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2 from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql5");
	mySql.addSubPara(fm.RptNo.value);   
		mySql.addSubPara(fm.customerNo.value);              
//    alert(strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult2();
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
    fm.GiveTypeDesc.value = "";//�ܸ�ԭ�����
    fm.GiveReason.value = "";//�ܸ�����
    fm.SpecialRemark.value = "";//���ⱸע
    //���ð�ť
    fm.addUpdate.disabled = false;//����޸�
    //�õ�mulline��Ϣ
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,13);//�⸶����
        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,21);
        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,22);//����ԭ��
        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,23);//
        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,24);//������ע
        fm.GiveTypeDesc.value = PolDutyCodeGrid.getRowColData(i,15);//�ܸ�ԭ�����
        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,17);//�ܸ�����
        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,18);//���ⱸע
        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
        showOneCodeName('llprotestreason','GiveTypeDesc','GiveTypeDescName');
    }
    //��ʾ���ز�
    divBaseUnit5.style.display= "";
    choiseGiveTypeType();
}

//�Ա��������޸�
function AddUpdate()
{
    var i = PolDutyCodeGrid.getSelNo() - 1;//�õ�������

    PolDutyCodeGrid.setRowColData(i,13,fm.GiveType.value);//�⸶����
    PolDutyCodeGrid.setRowColData(i,15,fm.GiveTypeDesc.value);//�ܸ�ԭ�����
    PolDutyCodeGrid.setRowColData(i,17,fm.GiveReason.value);//�ܸ�����
    PolDutyCodeGrid.setRowColData(i,18,fm.SpecialRemark.value);//���ⱸע
    PolDutyCodeGrid.setRowColData(i,21,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,22,fm.AdjReason.value);//����ԭ��
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjRemark.value);//������ע
    
    fm.saveUpdate.disabled = false;//�����޸�
}

//�Ա����޸ı��浽��̨
function SaveUpdate()
{
    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    submitForm();
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

//���б�������ƥ��
function showMatchDutyPay()
{
    mOperate="MATCH||INSERT";
    var showStr="���ڽ��б�������ƥ������������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    fm.submit(); //�ύ
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
    /*tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql6");
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
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql7");
	mySql.addSubPara(tClaimNo);   
    arr = easyExecSql(  mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,DutyKindGrid);
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
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql8");
	mySql.addSubPara(tClaimNo);   
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyKindGrid);
    }


    //��ѯLLClaimDetail,��ѯ�����������ͱ���������Ϣ
    /*tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetRela c where trim(c.GetDutyCode) = trim(a.GetDutyCode)),"
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
       +" case a.PolSort when 'A' then '�б�ǰ' when 'B' then '����' when 'C' then '����' end ,"
       +" a.DutyCode "
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode"        
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GiveType not in ('1')"*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql9");
	mySql.addSubPara(tClaimNo);   
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
}

//�����˷���
function showBnf()
{
	var rptNo = fm.RptNo.value;//�ⰸ��
    var strUrl="LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A";
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

//������ѯ
//���ա��ͻ��š���LCpol�н��в�ѯ����ʾ�ÿͻ��ı�����ϸ
function showInsuredLCPol()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
	var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����ⰸ��ѯ
function showOldInsuredCase()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
	var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo;
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

//��֤���õ���
function showLLMedicalFeeAdj()
{
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���

    //alert(tClaimNo);
    var strUrl="LLMedicalFeeAdjMain.jsp?RptNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
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
  /*  var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value + "'";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql10");
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
    //window.open(strUrl,"�¹�������Ϣ");
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
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql11");
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
    //window.open(strUrl,"�ʱ���ѯ");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//�����ύ
function submitForm()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.submit(); //�ύ
    tSaveFlag ="0";
}

//ƥ���Ķ���
function afterMatchDutyPay(FlagStr, content)
{
    showInfo.close();
    if (FlagStr == "FAIL" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        mOperate = '';
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    afterMatchDutyPayQuery();

}

//���ذ�ť
function goToBack()
{
    location.href="LLClaimSimpleMissInput.jsp";
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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

//�ύ�����,������
function afterSubmit2( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        afterMatchDutyPayQuery();
    }
    tSaveFlag ="0";
}

//��������ȷ��
function ConfirmSaveClick()
{
	//�жϱ������Ƿ�Ϊ��
	if (fm.DecisionSP.value == "" || fm.DecisionSP.value == null)
	{
		alert("������д��������!");
        return;
	}
    //�жϴ������Ƿ������˽���
    if (fm.AuditConclusion.value == null)
    {
    	alert("��������û����˽���!");
    	return;
    }
    //�жϴ������Ƿ��Ѵ�����������
   /* var strSQL1 = "select ExamConclusion from LLClaimUWMain where 1=1"
                + getWherePart( 'ClmNo','RptNo' ) ;*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql12");
	mySql.addSubPara(fm.RptNo.value);  
    var ExamConclusion = easyExecSql(mySql.getString());
//    alert(strSQL1);
    if (ExamConclusion != "")
    {
    	alert("�������Ѵ�����������!");
    	fm.isReportExist.value = "true";
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

//���������
function showRgtMAffixList()
{
    if(SubReportGrid.getSelNo() <= 0)
    {
        alert("��ѡ������ˣ�");
        return;
    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
    var strUrl="LLRgtMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=Rgt";

    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//���������
function showRgtAddMAffixList()
{
    if(SubReportGrid.getSelNo() <= 0)
    {
        alert("��ѡ������ˣ�");
        return;
    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
    var strUrl="LLRgtAddMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=RgtAdd";

    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//���װ���
function confirmClick()
{
	//���ǿ�
	if (fm.SimpleConclusion.value == "" || fm.SimpleConclusion.value == null)
	{
		  alert("����д���װ������ۣ�");
		  return;
	}
	fm.action="./LLClaimSimpleSave.jsp"
	fm.fmtransact.value = "";
	submitForm();
}

//��ѯ���ս��2
function queryResult2()
{
   /* var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql13");
	mySql.addSubPara(fm.AccResult2.value);  
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        fm.AccResult2Name.value = tICDName;
    }
}

//��鱣�������,ֻ�ܵ�С
function checkAdjMoney()
{
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tRealM = parseFloat(PolDutyCodeGrid.getRowColData(i,9));
        var tAdjM = parseFloat(fm.RealPay.value);
        if (tRealM < tAdjM)
        {
            alert("�������ܴ��ڱ���!");
            fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,21);
            return;
        }
    }
}

//Ӱ���ѯ---------------2005-08-14���
function colQueryImage()
{
    var strUrl="LLColQueryImageMain.jsp?claimNo="+fm.all('ClmNo').value+"&subtype=LP1001";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��ӡ�ⰸ��������
function colBarCodePrint()
{
    fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    fm.submit();
}
