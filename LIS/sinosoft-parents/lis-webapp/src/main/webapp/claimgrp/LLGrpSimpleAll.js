var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mySql = new SqlClass();
//�ɿͻ���ѯ��LLLdPersonQuery.js�����ص�����¼ʱ����
function afterQueryLL(arr)
{
  
    fm.customerNo.value   = arr[0];//Ͷ���˿ͻ���
    fm.customerName.value = arr[1];//Ͷ��������
    fm.customerSex.value  = arr[2];//�Ա�
    fm.customerAge.value  = arr[3];//���
    fm.IDNo.value         = arr[6];//ID��
    fm.IDTypeName.value   = arr[5];//֤����������
    fm.IDType.value       = arr[7];//֤������
    showOneCodeName('sex','customerSex','SexName');//�Ա�
    fm.QueryCont2.disabled = false; //�������ݺ󣬽�QueryCont2����Ϊ����
    fm.QueryCont3.disabled = false; //�������ݺ󣬽�QueryCont3����Ϊ����
    //��ʼ��¼����
    fm.AccidentDate2.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
        fm.claimType[j].checked = false;
    }

}

function afterQuery(mRgtNo){
if(mRgtNo!=''){
document.all('RptNo').value = mRgtNo;
queryRegister();
}
}
//������ѯ
function queryRegister()
{
  
    var rptNo = document.all('RptNo').value;
    var tFlag = fm.Flag.value;
    if(rptNo == "")
    {
      
     fm.updatebutton.disabled = true;
     fm.QueryCont2.disabled = true;
     fm.QueryCont3.disabled = true;
     fm.QueryReport.disabled = true;
     
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = true;
     fm.addUpdate.disabled = true;
     fm.simpleClaim.disabled = true;
     divSimpleClaim2.style.display= "";
     divSimpleClaim3.style.display= "none";
    }else{
      
     fm.updatebutton.disabled = false;
     fm.QueryCont2.disabled = false;
     fm.QueryCont3.disabled = false;
     fm.QueryReport.disabled = false;
     if(tFlag =="FROMSIMALL")
        fm.dutySet.disabled = true;
     else
         fm.dutySet.disabled = false;
     fm.QuerydutySet.disabled = false;
     fm.addUpdate.disabled = false;
     fm.simpleClaim.disabled = false;
    }
      
    //�����¼��š������¹ʷ�������(һ��)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql1");
mySql.addSubPara(rptNo); 
    var AccNo = easyExecSql(mySql.getString());
    //���������ż�����������Ϣ(һ��)
   /* var strSQL2 = "select AppntNo,GrpName,GrpContNo,RgtNo,Peoples2,AppPeoples,RgtantName,AccidentReason,RgtConclusion,RgtClass,clmState,RiskCode,Operator from llregister where "
                + "rgtno = '"+rptNo+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql2");
mySql.addSubPara(rptNo); 
    var RptContent = easyExecSql(mySql.getString());
    //����ҳ������
    if(AccNo!=null)
    {
        fm.AccNo.value = AccNo[0][0];
        //fm.AccidentDate.value = AccNo[0][1];
    }

    if(RptContent!=null)
    {
        fm.GrpCustomerNo.value = RptContent[0][0];
        fm.GrpName.value = RptContent[0][1];
        fm.GrpContNo.value = RptContent[0][2];
        fm.RptNo.value = RptContent[0][3];
        fm.Peoples.value = RptContent[0][4];
        fm.customerName.value = RptContent[0][6];
        fm.occurReason.value = RptContent[0][7];
        fm.clmState.value = RptContent[0][8];
        fm.RgtClass.value = RptContent[0][9];
        fm.clmState.value = RptContent[0][10];
        fm.Polno.value = RptContent[0][11];
        fm.tOperator.value = RptContent[0][12];

        showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    
        fm.clmState2.value = RptContent[0][10]
        //�᰸��������
        if( fm.clmState2.value == '60' )
        {
             fm.QueryPerson.disabled = true;
             fm.addbutton.disabled = true;
             fm.Inputbutton.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton2.disabled = true;
             fm.updatebutton.disabled = true;
             fm.deletebutton.disabled = true;
             fm.QueryCont2.disabled = true;
             fm.QueryCont3.disabled = true;
             fm.QueryReport.disabled = true;
             fm.dutySet.disabled = true;
             fm.addUpdate.disabled = true;
             fm.simpleClaim.disabled = true;
             fm.SimpleConclusion.value = '0';
             showOneCodeName('llexamconclusion','SimpleConclusion','SimpleConclusionName');//����ԭ��
             divSimpleClaim2.style.display= "none";
             divSimpleClaim3.style.display= "";
         }else if( fm.clmState2.value == '40')//���˰�������
         {
             fm.QueryPerson.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton2.disabled = true;
             fm.updatebutton.disabled = true;
             fm.deletebutton.disabled = true;
             fm.QueryCont2.disabled = true;
             fm.QueryCont3.disabled = true;
             fm.QueryReport.disabled = true;
             fm.dutySet.disabled = true;
             fm.addUpdate.disabled = true;
            if(fm.Flag.value == '2')
            {
                 divSimpleClaim2.style.display= "none";
                 divSimpleClaim3.style.display= "";
            }
            else
            {
                 divSimpleClaim2.style.display= "";
                 divSimpleClaim3.style.display= "none";
            }
        }
        else
        {
             divSimpleClaim2.style.display= "";
             divSimpleClaim3.style.display= "none";
        }

    }
  
   // var strSQL4 = "select count(*) CustomerNo from llcase where caseno = '"+rptNo+"'";
     mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql3");
mySql.addSubPara(rptNo); 
    var CustomerNoCount = easyExecSql(mySql.getString());
    if(CustomerNoCount!=null)
    {
        fm.PeopleNo.value = CustomerNoCount[0][0];
    }
    
    //****************************************************
    //����ҳ��Ȩ��
    //****************************************************
    fm.AccidentDate.readonly = true;
    fm.claimType.disabled=true;
    
    //�����ְ�������������Ϣ(����)
   /* var strSQL1 = "select count(*) from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
 //     alert("rptNo"+rptNo);          
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql4");
mySql.addSubPara(rptNo); 
    var count = easyExecSql(mySql.getString());
   
    if(count > 0)
    {
      
      /* var strSQL3 = "select a.CustomerNo,a.Name,a.Sex,a.Birthday,"
                    + " a.VIPValue,(select codename from ldcode where "
                    + " codetype = 'idtype' and code = a.IDType),a.IDNo,"
                    + " a.IDType, b.accdate from LDPerson a,LLCase b where "
                    + " a.CustomerNo=b.CustomerNo "                 
                    + " and b.CaseNo = '"+ rptNo +"' order by lpad(b.seconduwflag,4,'0')";*/
       mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql5");
mySql.addSubPara(rptNo);               
       //  var strSQL3 = "select a.CustomerNo,a.Name,a.Sex,a.Birthday,"
       //             + " a.VIPValue from LDPerson a,LLCase b where "
        //            + " a.CustomerNo=b.CustomerNo "                 
         //           + " and b.CaseNo = '"+ rptNo +"' order by lpad(b.seconduwflag,4,'0')";            
                    
        turnPage3.queryModal(mySql.getString(),SubReportGrid);
        if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
        {
            SubReportGridClick(0);
        }
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
  fm.AccidentDate2.value = "";
  fm.claimType.value = "";
  fm.IDType.value = "";
  fm.IDTypeName.value = "";
  fm.IDNo.value = "";
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
        fm.IDTypeName.value = SubReportGrid.getRowColData(i,6);
        fm.IDNo.value = SubReportGrid.getRowColData(i,7);
        fm.IDType.value = SubReportGrid.getRowColData(i,8);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
        fm.AccidentDate.value = SubReportGrid.getRowColData(i,9);//��������
    }

    //��ѯ�����������
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql6");
mySql.addSubPara(fm.RptNo.value);  
mySql.addSubPara(fm.customerNo.value);  
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
                if(fm.claimType[j].value == tClaim)
                {
                    fm.claimType[j].checked = true;
                }
            }
        }
    }
//����ѡ�еĳ�������ʾ��ص�������
afterMatchDutyPayQuery();
}

//ѡ��PolDutyCodeGrid��Ӧ�¼�
function PolDutyCodeGridClick()
{
    if(document.all('Flag').value != '2'){
    //��ձ�
    fm.GiveType.value = "";//�⸶����
    fm.RealPay.value = "";
    fm.AdjReason.value = "";//����ԭ��
    fm.AdjReasonName.value = "";//
    fm.AdjRemark.value = "";//������ע
    fm.GiveTypeDesc.value = "";//�ܸ�ԭ�����
    fm.GiveReason.value = "";//�ܸ�����
    fm.SpecialRemark.value = "";//���ⱸע
    var tRiskCode = '';
    //���ð�ť
    if(fm.clmState2.value == '60'){
    fm.addUpdate.disabled = true;//����޸�
    }else{
    fm.addUpdate.disabled = false;//����޸�
    }
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
        tRiskCode = PolDutyCodeGrid.getRowColData(i,3);//���ֱ���
    }
    //�ʻ��������жϣ��ǵĲ������޸�������
   // var sql1 = " select insuaccflag From lmrisk where riskcode = '"+tRiskCode+"'";
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql7");
mySql.addSubPara(tRiskCode);  
    var tInsuaccFlag = easyExecSql(mySql.getString());
    //���������жϣ��ǵĲ������޸�������
   // var sql2 = " select riskperiod from lmriskapp where riskcode = '"+tRiskCode+"'";
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql8"); 
mySql.addSubPara(tRiskCode); 
    var tRiskPeriod = easyExecSql(mySql.getString());
    //��ʾ���ز�
    if(tInsuaccFlag != 'Y' && tRiskPeriod != 'L'){
    divBaseUnit5.style.display= "";
    choiseGiveTypeType();
    }else{
    divBaseUnit5.style.display= "none";
    }
    }
}

//�Ա��������޸�
function AddUpdate()
{
    checkAdjMoney();//��鱣�������
    var i = PolDutyCodeGrid.getSelNo() - 1;//�õ�������

    PolDutyCodeGrid.setRowColData(i,13,fm.GiveType.value);//�⸶����
    PolDutyCodeGrid.setRowColData(i,15,fm.GiveTypeDesc.value);//�ܸ�ԭ�����
    PolDutyCodeGrid.setRowColData(i,17,fm.GiveReason.value);//�ܸ�����
    PolDutyCodeGrid.setRowColData(i,18,fm.SpecialRemark.value);//���ⱸע
    PolDutyCodeGrid.setRowColData(i,21,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,22,fm.AdjReason.value);//����ԭ��
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjRemark.value);//������ע
    if(fm.GiveType.value == 0){
    PolDutyCodeGrid.setRowColData(i,14,"����");//�⸶��������
    }else{
    PolDutyCodeGrid.setRowColData(i,14,"�ܸ�");//�⸶��������
    }
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
    /*var strSQL = "select count(*) from lcinsureaccclass where accascription = '0'"
                + " and grpcontno = '" + fm.GrpContNo.value+"'";*/
     mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql9"); 
mySql.addSubPara(fm.GrpContNo.value); 
    var tCount = easyExecSql(mySql.getString());
    if(tCount > 0){
       if(confirm("��ȷ���Ѿ����˹���?"))
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

      }else{
        alert("�뵽��ȫ������!");
        return false;
      }
    }else{

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
       +" and a.caseno = '"+tClaimNo+"'"
       ;*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql10"); 
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

    initPolDutyCodeGrid();
    //��ѯLLClaimDetail,��ѯ�����������ͱ���������Ϣ
   /* tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetRela c where c.GetDutyCode = a.GetDutyCode),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0)," //�������� + ������--+ a.GracePeriod
       +" a.Amnt,a.YearBonus,a.EndBonus,"
       +" a.StandPay,a.GiveType, "
       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion' and e.code=a.GiveType),"
       +" a.GiveReason,"
       +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and f.code=a.GiveReason),"
       +" a.GiveReasonDesc,a.SpecialRemark,"
       +" a.PrepaySum ,"//Ԥ�����
       +" '',"
       +" a.RealPay,a.AdjReason,"
       +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and g.code=a.AdjReason),"
       +" a.AdjRemark, "
       +" a.PrepayFlag,case a.PrepayFlag when '0' then '��Ԥ��' when '1' then '��Ԥ��' end,"
       +" case a.PolSort when 'A' then '�б�ǰ' when 'B' then '����' when 'C' then '����' end ,"
       +" a.DutyCode,a.CustomerNo,a.GrpContNo,a.ContNo,"
       +" (select name from ldperson where customerno=a.CustomerNo) "
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"
       +" and a.DutyCode = b.DutyCode"
       +" and a.GetDutyCode = b.GetDutyCode"
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GiveType not in ('2')"
       +" and a.CustomerNo = '"+tCustNo+"'"*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql11"); 
	mySql.addSubPara(tClaimNo);    
	mySql.addSubPara(tCustNo);    
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }

    //��ѯLLFeeMain��LLCaseReceipt����ʾ����/סԺ��Ϣ
    
     /* tSql = "select a.customerno, a.FeeType,(select hospitalname from LLCommendHospital where hospitalcode=a.hospitalcode), a.HospitalCode, "
         +" a.HospitalGrade,a.MainFeeNo, (select icdname from lddisease where icdcode=b.diseasecode),b.diseasecode,"
         +" b.startdate,b.EndDate,decode(a.FeeType,'A','',b.DayCount),(select codename from ldcode where codetype = 'llfeeitemtype' and Code = b.FeeItemCode),b.FeeItemCode,b.Fee,"
         +" b.RefuseAmnt,(select codename from ldcode where codetype = 'deductreason' and Code = b.AdjReason),b.AdjReason,b.AdjSum,b.SecurityAmnt,b.HospLineAmnt,b.AdjRemark,b.FeeDetailNo "
         +" from llfeemain a, LLCaseReceipt b,LLCase c "
         +" where a.clmno = '"+tClaimNo+"' and a.clmno = b.clmno and a.clmno=c.caseno and b.customerno=c.customerno"
         +" and a.customerno = b.customerno and a.mainfeeno = b.mainfeeno order by lpad(c.seconduwflag,4,'0')";*/
         //+" and a.hospitalcode = c.hospitalcode  and trim(b.diseasecode) = trim(d.icdcode) ";   
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql12"); 
	mySql.addSubPara(tClaimNo);        
    turnPage2.queryModal(mySql.getString(),MedFeeInHosInpGrid);

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
    var tFlag = fm.Flag.value;
    if(tFlag == "FROMSIMALL")
    {
       location.href="LLGrpClaimSimpleAllInput.jsp";
    }
    else if(tFlag == '2')
    {
        location.href="LLGrpClaimConfirmAllInput.jsp";
    }
    else
    {
        location.href="LLGrpClaimSimpleInput.jsp";
    }
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
    if(fm.SimpleConclusion2.value != '1')
    {
      goToBack();
    }    
    queryRegister();
    tSaveFlag ="0";
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
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
  //      queryRegister();
        alert("2");
    }
    tSaveFlag ="0";
}

//�ύ�����,������
function afterSubmit3( FlagStr, content )
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

    }
      queryRegister();
      afterMatchDutyPayQuery();
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


//������װ���
function confirmClick()
{
  //���ǿ�
  if (fm.Flag.value != '2' && (fm.SimpleConclusion.value == "" || fm.SimpleConclusion.value == null))
  {
      alert("����д���װ������ۣ�");
      return;
  }
  //���ǿ�
  if (fm.Flag.value == '2'&&(fm.SimpleConclusion2.value == "" || fm.SimpleConclusion2.value == null))
  {
      alert("����д���װ������˽��ۣ�");
      return;
  }
  //ƥ�䲢�����ж�
  var i = PolDutyCodeGrid.mulLineCount;
  if(i == '0')
  {
      alert("����ƥ�䲢���㣡");
      return;
  }

   //������ԱȨ���ж�
    if (fm.Flag.value == '2')
    {
    	//var tclmSql="select clmstate from llclaim where clmno='"+fm.RptNo.value+"' ";
    	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql13"); 
	mySql.addSubPara(fm.RptNo.value); 
    	var ttclmstate=easyExecSql(mySql.getString());
    	if (ttclmstate=="60"||ttclmstate=="70"||ttclmstate=="80")
    	{
    		alert("���ⰸ�Ѿ��᰸��");    	  
    	  return;    	  
    	}
    	
    fm.simpleClaim2.disabled=true;
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

    	
    if(fm.SimpleConclusion2.value == '0'){
    var tClaimType1 ;
    var tClaimType2 ;
    var tRealpay ;
//01.��ѯ���ⰸ���������ֵ

     //var csql="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql14"); 
	mySql.addSubPara(fm.RptNo.value); 
     var tcustomerno=new Array();
     tcustomerno=easyExecSql(mySql.getString());
     for (var i=0;i<tcustomerno.length;i++)
     {
          /*var strSql01 = " select realpay, insuredno from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and realpay = (select max(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' )"   */
        /* var strSql00 = " select sum(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and substr(getdutykind,2,3) in ('01','02','04') and insuredno='"+tcustomerno[i]+"'";                  
       */  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql15"); 
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(tcustomerno[i]  );            
         var tSubReport = new Array;
         tSubReport = easyExecSql(mySql.getString());
         var  tRealpay1 = tSubReport[0][0];
         //alert ("�����⸶:"+tRealpay1);
        // var tInsuredno = tSubReport[0][1];
        
             if(tRealpay1 == '' || tRealpay1 == null)
             {
               //  alert("δ��ѯ�����ⰸ���⸶��");
               //  return;
                 tRealpay1=0;  
             }
           else
           	{
           	  tClaimType1='10';	
           	}
           	
        /* var strSql01 = " select sum(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and substr(getdutykind,2,3) not in ('01','02','04') and insuredno='"+tcustomerno[i]+"'"; */                 
         mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql16"); 
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(tcustomerno[i]  );  
         var tSubReport1 = new Array;
         tSubReport1 = easyExecSql(mySql.getString());
         var   tRealpay2 = tSubReport1[0][0];
        //alert ("�������⸶:"+tRealpay2);
        // var tInsuredno2 = tSubReport[0][1];
           if(tRealpay2==null || tRealpay2 == "")
             {
        //         alert("δ��ѯ�����ⰸ���⸶��");
        //         return;
                tRealpay2=0;           
             }
           else
           	{
           	   	tClaimType2='30';
           	}      	
           	
        /*//02.��ѯ���������ֵ�Ŀͻ��ĳ�������
             var strSql02 = "select reasoncode from LLAppClaimReason where caseno = '"+fm.RptNo.value+"' and customerno = '"+tInsuredno+"'";
             var tSubReport2 = new Array;
             tSubReport2 = easyExecSql(strSql02);
               if(tSubReport2 == null ){
                    alert("δ��ѯ�����ⰸ�ĳ������ͣ�");
                    return;
                   }
             for (var i= 0;i < tSubReport2.length ; i++ )
             {
                 var tReasonCode = tSubReport2[i][0].substring(1,3);
                 if(tReasonCode == 01 || tReasonCode == 02 || tReasonCode == 04){
                      tClaimType = 10;//����
                      break;
                     }else{
                      tClaimType = 30;//������
                     }
             }*/
             
        //03.1��ѯ������Ա��������͵�����Ȩ��
        //0301.1��ѯ������Ա������Ȩ��
            //   var strSql0301 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
                mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql17"); 
	mySql.addSubPara(document.all('tOperator').valuee); 
	mySql.addSubPara(tClaimType1);
                var tBasemax0301 = easyExecSql(mySql.getString());
                if (tBasemax0301 == null || tBasemax0301 == "")
                {
        //            alert("δ��ѯ����������Ȩ�ޣ�");
        //            return;
                      tBasemax0301 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
                }
        //0302.1��ѯ������Ա������Ȩ��
              //  var strSql0302 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
                mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql18"); 
	mySql.addSubPara(document.all('tOperator').valuee); 
	mySql.addSubPara(tClaimType1);
                var tBasemax0302 = easyExecSql(mySql.getString());
                if (tBasemax0302 == null || tBasemax0302 == "")
                {
        //            alert("δ��ѯ����������Ȩ�ޣ�");
        //            return;
                      tBasemax0302 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
                }
        //0303.1������Ա�������ԱȨ���ж�
                tBasemax0301 = tBasemax0301*1;
                tBasemax0302 = tBasemax0302*1;
                if(tBasemax0301 > tBasemax0302){
                  tBasemax1 = tBasemax0301;
                }else{
                  tBasemax1 = tBasemax0302;
                }
        
        //03.2��ѯ������Ա��������͵�����Ȩ�� 
        //0301.2��ѯ������Ա������Ȩ��        
                //var strSql03011 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
               mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql19"); 
	mySql.addSubPara(document.all('tOperator').valuee); 
	mySql.addSubPara(tClaimType2);
                var tBasemax03011 = easyExecSql(mySql.getString());
                if (tBasemax03011 == null || tBasemax03011 == "")
                {
        //            alert("δ��ѯ����������Ȩ�ޣ�");
        //            return;
                      tBasemax03011 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
                }
        //0302.2��ѯ������Ա������Ȩ��
             //   var strSql03022 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
               mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql20"); 
	mySql.addSubPara(document.all('tOperator').valuee); 
	mySql.addSubPara(tClaimType2);
                var tBasemax03022 = easyExecSql(mySql.getString());
                if (tBasemax03022== null || tBasemax03022 == "")
                {
        //            alert("δ��ѯ����������Ȩ�ޣ�");
        //            return;
                      tBasemax03022 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
                }
        //0303.2������Ա�������ԱȨ���ж�
                tBasemax03011 = tBasemax03011*1;
                tBasemax03022 = tBasemax03022*1;
                if(tBasemax03011 > tBasemax03022){
                  tBasemax2 = tBasemax03011;
                }else{
                  tBasemax2 = tBasemax03022;
                }
        
        //04.Ȩ���ж�
                tBasemax1 = tBasemax1*1;
                tBasemax2 = tBasemax2*1;
        
              		tRealpay1 = tRealpay1*1;
              		tRealpay2 = tRealpay2*1;
              		
              	//alert ("��������⸶:"+tBasemax1);
                          
                //alert ("����������⸶:"+tBasemax2);
              		
                if(tRealpay1 > tBasemax1 ||tRealpay2 > tBasemax2)
                {
                    showInfo.close();
                    fm.simpleClaim2.disabled=false;                    
                    alert("����Ȩ�޲��㣡���ϱ��ܹ�˾����");
                    return;
                }     	
             }
             

   }if(fm.SimpleConclusion2.value == '1')
      {
    	  var mngcom = new Array;
    	 // var tsql="select mngcom from llregister where rgtno='"+fm.RptNo.value+"' ";    	  
    	   mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql21"); 
			mySql.addSubPara(fm.RptNo.value); 
    	  mngcom = easyExecSql(mySql.getString());   	      	  
    	      	  
    	  if (fm.AllManageCom.value!=mngcom)
    	  {
           showInfo.close();
           fm.simpleClaim2.disabled=false;    	  	
    	     alert ("���¼�������� "+mngcom+" ���л��˲�����");	
    	     return;
    	  }         
      }
      	
    }

  fm.action="./LLGrpClaimSimpleSave.jsp"
  fm.fmtransact.value = "";
 if (fm.Flag.value == '2')
  {    
    showInfo.close();
  }
  
  submitForm();
}

//��鱣�������,ֻ�ܵ�С
function checkAdjMoney()
{
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tContNo   = parseFloat(PolDutyCodeGrid.getRowColData(i,31));//������ͬ��
        var tRiskCode = parseFloat(PolDutyCodeGrid.getRowColData(i,3));//���ֱ���
        var tDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,28));//���α���
        var tRealM    = parseFloat(PolDutyCodeGrid.getRowColData(i,21));//�������
        var tAmnt     = parseFloat(PolDutyCodeGrid.getRowColData(i,9));//����
        var tGrpContNo= parseFloat(PolDutyCodeGrid.getRowColData(i,30));//�����ͬ��
        var tGetDutyKind = parseFloat(PolDutyCodeGrid.getRowColData(i,2));
        var tGetDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,4));
        var tAdjM     = parseFloat(fm.RealPay.value);
        var tContPlanCode = "0";
        var tDeadTopvalFlag = "";

       //�жϽ����Ͳ�������
      /* var strSQL4 = " select deadtopvalueflag from lmdutygetclm where "
                   + " getdutycode='"+tGetDutyCode+"' and getdutykind='"+tGetDutyKind+"'"*/
       mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql22"); 
			mySql.addSubPara(tGetDutyCode); 
			mySql.addSubPara(tGetDutyKind); 
       var arr4= easyExecSql(mySql.getString());
          if(arr4 != null){
          tDeadTopvalFlag = arr4[0][0];
             }

        if(tRiskCode == '140' || tDeadTopvalFlag == 'N')
        {
          /*
         var strSQL3 = " select contplancode from lcinsured  where contno = '"+tContNo+"'"
         var arr3= easyExecSql(strSQL3);
            if(arr3 != null){
            tContPlanCode = arr3[0][0];
               }
         var strSQL = "select a.TotalLimit from lldutyctrl a"
                    + " where trim(a.ContType) = '2' and trim(a.ContNo) = '"+tGrpContNo+"'"
                    + " and trim(a.DutyCode) = '"+tDutyCode+"' and trim(a.RiskCode) = '"+tRiskCode+"'"
                    + " and trim(a.ContPlanCode) = '"+tContPlanCode+"'";
         var arr= easyExecSql(strSQL);
            if(arr != null){
            tAmnt = arr[0][0];
               }else{
         var strSQL2 = "select a.TotalLimit from lldutyctrl a"
                    + " where trim(a.ContType) = '1' and trim(a.ContNo) = '"+tContNo+"'"
                    + " and trim(a.DutyCode) = '"+tDutyCode+"' and trim(a.RiskCode) = '"+tRiskCode+"'"
                    + " and trim(a.ContPlanCode) = '"+tContPlanCode+"'";
         var arr2= easyExecSql(strSQL2);
             if(arr2 != null)
             {
                tAmnt = arr2[0][0];
             }
             else{
               alert("δ��ѯ��140���ֵ��⸶�޶");
               fm.RealPay.value = 0;
               return;
                  }
               }
               */
               tAmnt=1000000;
            }
        if (tAmnt < tAdjM)
        {
            alert("�������ܴ��ڱ���!");
            fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
            return;
        } else if(tAdjM < 0){
            alert("��������С����!");
            fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
            return;
        }
    }
}


//�����˲�ѯ
function showInsPerQuery()
{
    var strUrl = "LLGrpLdPersonQueryMain.jsp?GrpContNo="+fm.GrpContNo.value+"&GrpCustomerNo="+fm.GrpCustomerNo.value+"&GrpName="+fm.GrpName.value+"&ManageCom="+fm.AllManageCom.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//[����]��ť��Ӧ����
function saveClick()
{
    //���Ƚ��зǿ��ֶμ���
    if (beforeSubmit())
    {
        if (fm.RgtClass.value == '' || fm.RgtClass.value == null)
        {
            fm.RgtClass.value = "2";//����
        }
        
        /*���ӶԳ����˵�������������жϣ������δ�᰸�İ������ڣ�����������
         *2006-03-06 P��D
         */
      //  var StrSQL = "select count(*) from llcase a, llregister b where a.caseno = b.rgtno and a.customerno = '"+fm.customerNo.value+"' and  b.clmstate not in ('60','80','50','70')";
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql23"); 
			mySql.addSubPara(fm.customerNo.value); 
        var arr= easyExecSql(mySql.getString());
        if(arr > 0){
           alert("�ó�������δ�᰸������᰸������������");
           return false;
        }
        /*================================================================
         * �޸�ԭ�����Ӷ�����������������죩�ı������˵��жϲ���������
         * �� �� �ˣ�P.D
         * �޸����ڣ�2006-8-16
         *=================================================================
         */
       /* var SqlPol = " select count(*) From lcpol where polstate = '6'"
                   + " and  insuredno = '"+fm.customerNo.value+"'"
                   + " and  grpcontno = '"+fm.GrpContNo.value+"'";*/
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql24"); 
			mySql.addSubPara(fm.customerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value);           
        if(fm.Polno.value != ''){
           // SqlPol += " and riskcode = '"+fm.Polno.value+"'";
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql25"); 
			mySql.addSubPara(fm.customerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value);
			mySql.addSubPara(fm.Polno.value);    
             }
        var tPolState = easyExecSql(mySql.getString());
        if(tPolState > 0){
           alert("�ó������Ѿ����������������������������");
           return false;
         }
         
        /*===================================================================
         * �޸�ԭ�����ӶԳ������Ƿ���������������жϣ���������������������
         * �� �� �ˣ�JINSH
         * �޸����ڣ�2007-05-17
         *===================================================================
         */
       /* var Polsqlflag = " select count(*) From lcpol where polstate = '7'"
                   + " and  insuredno = '"+fm.customerNo.value+"'"
                   + " and  grpcontno = '"+fm.GrpContNo.value+"'";*/
                   //alert(fm.customerNo.value);
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql26"); 
			mySql.addSubPara(fm.customerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value);            
        var Polflag = easyExecSql(mySql.getString());
        if(Polflag > 0)
        {
           alert("�ó�����"+fm.customerNo.value+"�Ѿ������������⣬��������������");
           return false;
        }
         
         
        /*================================================================
         * �޸�ԭ�����ӶԳ��ձ�ȫ���жϣ���ȫδȷ�Ϻ��˱��Ĳ���������
         * �� �� �ˣ�P.D
         * �޸����ڣ�2006-8-14
         =================================================================
         */
       // var SqlBq = "select  count(*) from LPEdorItem where insuredno = '"+fm.customerNo.value+"' and edorstate != '0' and edortype = 'CT'";
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql27"); 
			mySql.addSubPara(fm.customerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value);   
        var tEdorState = easyExecSql(mySql.getString());
       /* var sqlC = "select count(*) from lmriskapp where "
                 + "riskcode in (select riskcode From lcpol where "
                 + "grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"') "
                 + "and RiskPeriod = 'L'";*/
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql28"); 
			mySql.addSubPara(fm.GrpContNo.value);   
			mySql.addSubPara(fm.customerNo.value);   
			
        var tcount = easyExecSql(mySql.getString());
        if(tEdorState > 0 && tcount > 0 ){
           alert("�ó�������δȷ�ϵı�ȫ���Ѿ��˱�����ȷ�Ϻ�����������");
           return false;
        }

/*
        //jixf add 20051213
        var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
        strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
         if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
         {
         strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
         }
        if (fm.GrpContNo.value!=null)
        {
        strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
        }
        
         var arr= easyExecSql(strSQL);
         if ( arr == null )
         {
         alert("�ñ���δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڣ�");         
         return false;
         }
*/
         if(TempCustomer() == false && showDate() == false){
         alert("�ñ���δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڣ�");         
         return false;
          }
         
       /*  var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
            strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
            strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql29"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);   
             if (fm.GrpCustomerNo.value!=null)
             {
               //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql30"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
			mySql.addSubPara(fm.GrpCustomerNo.value);  
             }
            if (fm.GrpContNo.value!=null)
            {
              //strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql31"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value); 
            }
            
             var arrbq= easyExecSql(mySql.getString());
             if ( arrbq != null )
             {
               alert("���ؾ��棺�ñ��������ڳ�������֮���������ܸ��ı���ı�ȫ��");                      
             }


        //�ж������������������������Ǳ��������
        if(fm.RptNo.value == ''){
         fm.isReportExist.value = false;
        }
        if (fm.isReportExist.value == "false")
        {
            fm.fmtransact.value = "insert||first";
        }
        else
        {
            fm.fmtransact.value = "insert||customer";
        }
        fm.action = './LLGrpRegisterSave.jsp';
        submitForm();
        
  
    
    }
}

//��������Ϣ�޸�
function updateClick()
{
    if (confirm("��ȷʵ���޸ĸü�¼��"))
    {
        if (beforeSubmit())
        {
            alert("�޸���Ϣ��������ƥ�����㣡");
            //jixf add 20051213
          /*  var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
            strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";*/
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql32"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
             if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
             {
             //strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql33"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
			mySql.addSubPara(fm.GrpCustomerNo.value);  
             }
            if (fm.GrpContNo.value!=null)
            {
            //strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql34"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value);  
            }
            
             var arr= easyExecSql(mySql.getString());
             if ( arr == null )
             {
             alert("�ñ���δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڣ�");         
             return false;
             }
             
            /* var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
            strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
            strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql35"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
			
             if (fm.GrpCustomerNo.value!=null)
             {
              // strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql36"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
             }
            if (fm.GrpContNo.value!=null)
            {
             // strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
              mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql37"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
            }
            
             var arrbq= easyExecSql(mySql.getString());
             if ( arrbq != null )
             {
               alert("���ؾ��棺�ñ��������ڳ�������֮���������ܸ��ı���ı�ȫ��");                      
             }
                       
            fm.fmtransact.value = "update";
            fm.action = './LLGrpRegisterSave.jsp';
            submitForm();
        }
    }
}

//������Ϣ��ѯ
function ClientQuery2()
{
   var keycode = event.keyCode;
   if(keycode=="13" || keycode=="9")
   {
   if(document.all('GrpCustomerNo').value!=null && document.all('GrpCustomerNo').value!='' ||
      (document.all('GrpContNo').value!=null && document.all('GrpContNo').value!='') ||
      (document.all('GrpName').value!=null && document.all('GrpName').value!='') )
   {
      var arrResult = new Array();
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a " +
      " where a.grpcontno = g.grpcontno and g.appflag in ('1','4') "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
       mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql38"); 
			mySql.addSubPara(fm.GrpCustomerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value); 
      if(document.all('GrpName').value!=''){
        // strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
          mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql39"); 
			mySql.addSubPara(fm.GrpCustomerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(document.all('GrpName').value);   
			
      }
      //���ӶԻ������ж� ��½����ֻ�ܴ��������ĵ��� �ܻ������Դ���ֻ����ĵ��� 2006-02-18 P.D
     // strSQL += " and g.managecom like '"+document.all('AllManageCom').value+"%%'";
       mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql40"); 
			mySql.addSubPara(fm.GrpCustomerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(document.all('GrpName').value);   
			mySql.addSubPara(document.all('AllManageCom').value);   
      arrResult=easyExecSql(mySql.getString());
       if(arrResult != null)
       {
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             if(document.all('Peoples').value == null ||
                document.all('Peoples').value == "0")
             {
                alert("Ͷ������Ϊ�գ�");
                return;
             }
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      }
       }else{
      //ȡ������������� 2006-02-18 P.D
     // var SQLEx = "select ExecuteCom From lcgeneral where grpcontno='"+document.all('GrpContNo').value+"'";
       mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql41"); 
			mySql.addSubPara(document.all('GrpContNo').value);   
      var tExecuteCom = new Array();
      tExecuteCom = easyExecSql(mySql.getString());
      for(var i = 0;i<tExecuteCom.length;i++){
      //�жϵ�½�����Ƿ��Ǳ���ָ������
      if(tExecuteCom[i][0] == document.all('AllManageCom').value){
      var arrResult = new Array();
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a ,lcgeneral b " +
      " where a.grpcontno = g.grpcontno and g.grpcontno = b.grpcontno and g.appflag = '1' "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
       mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql42"); 
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value);
			mySql.addSubPara(fm.tExecuteCom[i][0].value);   
      if(document.all('GrpName').value!=''){
        // strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql43"); 
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(document.all('GrpName').value); 
			mySql.addSubPara(fm.tExecuteCom[i][0].value); 
      }
         //strSQL += " and b.ExecuteCom = '"+tExecuteCom[i][0]+"'";
      arrResult=easyExecSql(mySql.getString());

   if(arrResult != null){
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             if(document.all('Peoples').value == null ||
                document.all('Peoples').value == "0")
             {
                alert("Ͷ������Ϊ�գ�");
                return;
             }
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

      }
       } else {
         alert("û�з������������ݣ�");
         document.all('GrpCustomerNo').value = "";
         document.all('GrpName').value = "";
         document.all('GrpContNo').value = "";
         document.all('Peoples').value = "";
         return;
       }
         }
      }
     }
   }
   else
   {
      alert("�������ѯ����");
   }
   }
}
function afterLCGrpQuery(arrReturn)
{
     document.all('GrpCustomerNo').value=arrReturn[0][0];
     document.all('GrpName').value=arrReturn[0][1];
//     document.all('GrpContNo').value=arrReturn[0][2];
     document.all('Peoples').value=arrReturn[0][3];
}

//����Ϊ��������,��1980-5-9
function getAge(birth)
{
    var oneYear = birth.substring(0,4);
    var age = mNowYear - oneYear;
    if (age <= 0)
    {
        age = 0
    }
    return age;
}


//�ύǰ��У�顢����
function beforeSubmit()
{
    //��ȡ������Ϣ
    var RptNo = fm.RptNo.value;//�ⰸ��
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccDate = fm.AccidentDate.value;//�¹�����
    var AccReason = fm.occurReason.value;//����ԭ��
    var AccDate2 = fm.AccidentDate.value;//��������
//    var AccDesc = fm.accidentDetail.value;//����ϸ��
    var ClaimType = new Array;
    //��������
    for(var j=0;j<fm.claimType.length;j++)
    {
        if(fm.claimType[j].checked == true)
        {
            ClaimType[j] = fm.claimType[j].value;
        }
    }
    //ȡ����������Ϣ
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);
    var AccYear2 = AccDate2.substring(0,4);
    var AccMonth2 = AccDate2.substring(5,7);
    var AccDay2 = AccDate2.substring(8,10);
    //----------------------------------------------------------BEG2005-7-12 16:45
    //���������������͹�ϵ�ķǿ�У��
    //----------------------------------------------------------
    if (fm.GrpCustomerNo.value == "" || fm.GrpCustomerNo.value == null)
    {
        alert("����������ͻ��ţ�");
        return false;
    }
    if (fm.GrpName.value == "" || fm.GrpName.value == null)
    {
        alert("�����뵥λ���ƣ�");
        return false;
    }
    if (fm.GrpContNo.value == "" || fm.GrpContNo.value == null)
    {
        alert("���������屣���ţ�");
        return false;
    }
/*
    var tAssigneeZip = fm.AssigneeZip.value;
    if (tAssigneeZip.length > 6)
    {
        alert("�ʱ����");
        return false;
    }
*/

    //2-1 ����������
    if (AccDate2 == null || AccDate2 == '')
    {
        alert("�������ڲ���Ϊ�գ�");
        return false;
    }
    else
    {
        if (AccYear2 > mNowYear)
        {
            alert("�������ڲ������ڵ�ǰ����");
            return false;
        }
        else if (AccYear2 == mNowYear)
        {
            if (AccMonth2 > mNowMonth)
            {

                alert("�¹����ڲ������ڵ�ǰ����");
                return false;
            }
            else if (AccMonth2 == mNowMonth && AccDay2 > mNowDay)
            {
                alert("�������ڲ������ڵ�ǰ����");
                return false;
            }
        }
    }
    //2-2 ����������
    if (AccYear > AccYear2 )
    {
        alert("�¹����ڲ������ڳ�������");
        return false;
    }
    else if (AccYear == AccYear2)
    {
        if (AccMonth > AccMonth2)
        {

            alert("�¹����ڲ������ڳ�������");
            return false;
        }
        else if (AccMonth == AccMonth2 && AccDay > AccDay2)
        {
            alert("�¹����ڲ������ڳ�������");
            return false;
        }
    }
    //3 ������ԭ��
    if (AccReason == null || AccReason == '')
    {
        alert("����ԭ����Ϊ�գ�");
        return false;
    }

    //5 �����������
    if (ClaimType == null || ClaimType == '')
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }

    return true;
}

//Ԥ�������Ϣ¼��
function operStandPayInfo()
{
     var varSrc ="";
     var CaseNo = fm.RptNo.value;
     pathStr="./StandPayInfoMain.jsp?CaseNo="+CaseNo;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

//������ͳ�Ʋ�ѯ
function QuerydutySetInfo()
{
     var varSrc ="";
     var CaseNo = fm.RptNo.value;
     pathStr="./QuerydutySetInfoMain.jsp?CaseNo="+CaseNo;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

//�ʵ���Ϣ¼��
function StandPaySave()
{
    var row = MedFeeInHosInpGrid.mulLineCount-1;
    var i = 0;
    var arr = new Array();
    var tCustomerNo = '';
    var tClinicStartDate = '';
    var tClinicEndDate = '';
    var tClinicDayCount1 = '';
    var tFeeType = '';
    var numFlag = false;
  if(row < 0){
    alert("�������������ٱ���");
    return false;
    }
  for(var m=0;m<=row;m++ )
  {
   var i = MedFeeInHosInpGrid.getChkNo(m);//�õ�������
   if(i==true)
   {
    numFlag = true;
   fm.hideOperate.value = "INSERT";
   tCustomerNo = MedFeeInHosInpGrid.getRowColData(m ,1);//�����˺���
   tClinicStartDate = MedFeeInHosInpGrid.getRowColData(m ,9);//�ʵ���ʼ����
   tClinicEndDate = MedFeeInHosInpGrid.getRowColData(m ,10);//�ʵ���������
   tClinicDayCount1 = MedFeeInHosInpGrid.getRowColData(m ,11);//����
   tFeeType = MedFeeInHosInpGrid.getRowColData(m ,2);//�ʵ�����
   tMainFeeNo = MedFeeInHosInpGrid.getRowColData(m ,6);//�ʵ�����
   tClinicMedFeeTypeName = MedFeeInHosInpGrid.getRowColData(m ,12);//��������
   tClinicMedFeeSum = MedFeeInHosInpGrid.getRowColData(m ,14);//ԭʼ����

//�����˺������
  if(tCustomerNo=='')
  {
    alert("������������˺���");
    return false;
  }
//�ʵ�����
  if(tFeeType=='')
  {
    alert("���������ʵ�����");
    return false;
  }
//�ʵ��������
  if(tMainFeeNo=='')
  {
    alert("���������ʵ�����");
    return false;
  }
//�ʵ���ʼ���ڼ���
  if(tClinicStartDate=='')
  {
    alert("�������뿪ʼ����");
    return false;
  }

if(tClinicStartDate != ''){
 if(!isDate(trim(tClinicStartDate)))
  {
    alert('��'+m+'�е��ʵ���ʼ���ڸ�ʽӦΪXXXX-XX-XX');
    return false;
  }
}

if(tClinicEndDate != ''){
 if(!isDate(trim(tClinicEndDate)))
  {
    alert('��'+m+'�е��ʵ��������ڸ�ʽӦΪXXXX-XX-XX');
    return false;
  }
}
//�������ͼ���
  if(tFeeType == 'A'&& tClinicEndDate != '' )
  {
    alert("�������Ͳ�Ӧ�н�������");
    return false;
  }
  if(tFeeType == 'B'&& tClinicEndDate == '' )
  {
    alert("סԺ���ͽ������ڲ���Ϊ��");
    return false;
  }
   //���ڼ���
    if (dateDiff(tClinicStartDate,mCurrentDate,'D') < 0 || dateDiff(tClinicEndDate,mCurrentDate,'D') < 0)
    {
        alert("���ڴ���");
        return;
    }
    /*var date4 = dateDiff(fm.AccidentDate.value,tClinicStartDate,'D');
    if(date4 < 0)
    {
       alert("��֤��ʼ�������ڳ�������");
       return false;
    }*/
//�������Ƽ���
  if(tClinicMedFeeTypeName=='')
  {
    alert("���������������");
    return false;
  }
//ԭʼ���ü���
  if(tClinicMedFeeSum=='')
  {
    alert("��������ԭʼ����");
    return false;
  }
 }else
   {
      alert ("û��ѡ��Ҫ���������!")
      return false;  
   }
   
   
  }

    fm.fmtransact.value="INSERT||MAIN";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.action ="./LLGrpMedicalFeeInpSave.jsp";
    fm.submit();
}
//ɾ���ʵ�
function deleteFeeClick()
{
    var numFlag=false;
    var row = MedFeeInHosInpGrid.mulLineCount-1;
    for(var m=0;m<=row;m++)
    {
      var i = MedFeeInHosInpGrid.getChkNo(m);//�õ�������
      if(i==true)
      {
         numFlag = true;
         var feeNo = MedFeeInHosInpGrid.getRowColData(m,20);
         if(feeNo=="")
          {
             alert("����ϸδ���棬����ֱ��ɾ����");
          }
      }
    }
     if(numFlag==false)
    {
        alert("��ѡ��Ҫɾ�����ʵ�");
        return false;
    }
    else
    {
          fm.hideOperate.value = "DELETE";
          fm.action ='./LLGrpMedicalFeeInpSave.jsp';
          submitForm();  
    }     
    
}
function getMedFeeInHosInpGrid()
{
    var i = MedFeeInHosInpGrid.getSelNo() - 1;//�õ�������

    MedFeeInHosInpGrid.setRowColData(i,1,fm.customerNo.value);//�����˱���

}
//Ԥ�����ط���
function getGrpstandpay(){
}

function getin()
{

   var strUrl = "../claim/GrpCustomerDiskInput.jsp?grpcontno="+ fm.RptNo.value
   //showInfo=window.open(strUrl,"�������嵥����","");
   showInfo=window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

/*��������������������������������������������������������������
  * �޸�ԭ����ӳ�������Ϣɾ������
  * �� �� �ˣ������
  * �޸����ڣ�2006-02-17
  ��������������������������������������������������������������
  */
function deleteClick()
{
    if (confirm("��ȷʵ��ɾ���ü�¼��"))
    {
        alert("ɾ����Ϣ��������ƥ�����㣡");
        fm.fmtransact.value = "DELETE";
        fm.action = './LLGrpRegisterSave.jsp';
        submitForm();
    }
}
/*��������������������������������������������������������������
  * �޸�ԭ����ӳ�������Ϣɾ����ķ����ύ
  * �� �� �ˣ������
  * �޸����ڣ�2006-02-17
  ��������������������������������������������������������������
  */
function afterSubmit4( FlagStr, content )
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
//      showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        initForm();
    }
    queryRegister();
    tSaveFlag ="0";
}

//У�����ڸ�ʽ
function checkapplydate(){
  if(fm.AccidentDate.value.length==8){
  if(fm.AccidentDate.value.indexOf('-')==-1){ 
    var Year =     fm.AccidentDate.value.substring(0,4);
    var Month =    fm.AccidentDate.value.substring(4,6);
    var Day =      fm.AccidentDate.value.substring(6,8);
    fm.AccidentDate.value = Year+"-"+Month+"-"+Day;
    if(Year=="0000"||Month=="00"||Day=="00"){
        alert("���������������!");
        fm.AccidentDate.value = ""; 
        return;
      }
  }
} else {var Year =     fm.AccidentDate.value.substring(0,4);
      var Month =    fm.AccidentDate.value.substring(5,7);
      var Day =      fm.AccidentDate.value.substring(8,10);
      if(Year=="0000"||Month=="00"||Day=="00"){
        alert("���������������!");
        fm.AccidentDate.value = "";
        return;
         }
  }
}
//����ת���������ڼ��ж�
function TempCustomer(){
       // var StrSQL = "select grpcontno From lctempcustomer where startdate <= '"+fm.AccidentDate.value+"' and paytodate >= '"+fm.AccidentDate.value+"' and grpcontno = '"+fm.GrpContNo.value+"'";
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql44"); 
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.GrpContNo.value); 
        var arr= easyExecSql(mySql.getString());
        if(arr != null ){
        return true;
        }else{
        return false;
        }
}
//�жϱ����ڼ�
function showDate(){
        //jixf add 20051213
      /*  var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
        strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";*/
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql45"); 
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.customerNo.value); 
         if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
         {
         //strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
          mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql46"); 
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpCustomerNo.value); 
         }
        if (fm.GrpContNo.value!=null)
        {
       // strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql47"); 
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
        }
        
         var arr= easyExecSql(mySql.getString());
        if(arr != null ){
        return true;
        }else{
        return false;
        }
}
