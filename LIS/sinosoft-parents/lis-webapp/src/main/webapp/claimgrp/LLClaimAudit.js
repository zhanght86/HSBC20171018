var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��֤���õ���
function showLLMedicalFeeAdj()
{
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;     //�ͻ���

    //alert(tClaimNo);
    var strUrl="LLMedicalFeeAdjMain.jsp?RptNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//���б�������ƥ��
function showMatchDutyPay()
{
    mOperate="MATCH||INSERT";
    var showStr="���ڽ��б�������ƥ������������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
 

    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ
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
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql1");
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
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and c.code=a.GetDutyKind),"
       +" a.TabFeeMoney,a.SelfAmnt,a.ClaimMoney,a.SocPay,a.OthPay,RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql2");
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
       +" a.StandPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.ClmNo = '"+tClaimNo+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql3");
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
    //+" to_char(b.PayEndDate)," //�������� + ������--+ a.GracePeriod
      initPolDutyCodeGrid();
    tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetClm c where c.GetDutyKind = a.GetDutyKind and c.GetDutyCode = a.GetDutyCode),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0),"
       +" a.Amnt,a.YearBonus,a.EndBonus,"
       +" a.StandPay,a.GiveType, "
       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion2' and e.code=a.GiveType),"
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
       +" a.dutycode,a.CustomerNo "
       +" , (select codename from ldcode where codetype='polstate' and code in(select polstate from lcpol t where t.polno=a.PolNo))"
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode" 
       +" and a.ClmNo = '"+tClaimNo+"'"   
       //+" and a.GiveType not in ('1')"
       +" and a.CustomerNo = '"+tCustNo+"'";
//    prompt("",tSql);
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql4");
	mySql.addSubPara(tClaimNo); 
	mySql.addSubPara(tCustNo); */
    arr = easyExecSql( tSql );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
    else
    {
        initPolDutyCodeGrid();
    }
    
     //��ѯLLClaimPolicy,��ѯ��ȫ��Ŀ��Ϣ      
     tSql = " select a.PolNo,(select RiskCode from LCPol where PolNo=a.PolNo),(select c.codename from ldcode c where c.codetype = 'edortypecode' and c.code=a.EdorType),"
     +" a.EdorValiDate,a.GetMoney "
     +" from LPEdorItem a where 1=1 and a.PolNo in(select PolNo from LLClaimPolicy where ClmNo = '"+tClaimNo+"') ";
 	/*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql5");
	mySql.addSubPara(tClaimNo);  */
    arr = easyExecSql( tSql  );
  
    if (arr)
    {		
        displayMultiline(arr,LPEdorItemGrid);
    }
    else
    {
        initLPEdorItemGrid();
    }
}

//�򿪷���ʱ�
function showBeginSubmit()
{

  var strUrl="LLSubmitApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value//+"&custVip="+fm.IsVip.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

function showScanInfo()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tClaimNo = fm.RptNo.value;         //�ⰸ��  
    var tCustNo = fm.customerNo.value;     //�ͻ���
    var tCustomerName = fm.customerName.value;     //�ͻ�����
    
  if (tCustNo == "")
  {
      alert("��ѡ������ˣ�");
      return;
  }
    var strUrl="SubScanClaimQuery.jsp?ClaimNo="+tClaimNo+"&CustomerNo="+tCustNo+"&CustomerName="+tCustomerName;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function AddAffixClick()
{
   var tClmNo=document.all("RptNo").value;
   var urlStr;
   urlStr="./LLInqCourseAffixFrame.jsp?ClmNo="+tClmNo+""; 
   window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   			
}

function LoadAffixClick()
{
   var tClmNo=document.all("RptNo").value;
   var urlStr;
   urlStr="./LLInqCourseShowAffixFrame.jsp?ClmNo="+tClmNo+""; 
   window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   			
}

//¼��ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeInp()
{
    var tSel = SubReportGrid.getSelNo();

    if( tSel == 0 || tSel == null ){
        alert( "����ѡ��һ����¼����ִ�д˲���!!!" );
    }
    else{
        var tClaimNo = fm.RptNo.value;         //�ⰸ��
        var tCaseNo = fm.RptNo.value;          //�ְ���
        var tCustNo = fm.customerNo.value;          //�ͻ���
    	 var strUrl="../claim/LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.AccidentDate.value+"&otherAccDate="+fm.AccidentDate.value;
    	    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
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

//�������
function showSecondUWInput()
{
    var strUrl="LLSecondUWMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value+"&InsuredName="+fm.customerName.value;    
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
}

//���˴���
function SecondUWInput()
{
    var strUrl="LLDealUWsecondMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value;    
   // window.open(strUrl,"���˴���");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//[���⴦��]��ť<>
function showExempt()
{
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var strUrl="LLClaimExemptMain.jsp?ClaimNo="+tClaimNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�򿪷������
function showBeginInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    //var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000009125','0000009145','0000009165','0000009175') and missionprop1='"+fm.RptNo.value+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql6");
	mySql.addSubPara(fm.RptNo.value); 
    var JustStateCount=easyExecSql(mySql.getString());
    if(parseInt(JustStateCount)>0)
    {      				
    		alert("�ð����Ѿ�������飬�벻Ҫ�ظ�����!");
    		return;
    }       
    var strUrl="LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&custVip="+fm.SocialInsuFlag.value+"&initPhase=02";
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
    
    //�жϸ��ⰸ�Ƿ���ڵ���
    /*var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql7");
	mySql.addSubPara(fm.RptNo.value); 
    var tCount = easyExecSql(mySql.getString());
    if (tCount == "0")
    {
        alert("���ⰸ��û�е�����Ϣ��");
        return;
    }
    var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�¹�������Ϣ");
}

//�򿪳ʱ���ѯ
function showQuerySubmit()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    
  //�жϸ��ⰸ�Ƿ���ڳʱ�
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql8");
	mySql.addSubPara(fm.RptNo.value); 
    var tCount = easyExecSql(mySql.getString());
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

//��������
function showLcContSuspend()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContSuspendMain.jsp?InsuredNo="+tCustomerNo+"&ClmNo="+fm.ClmNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//������ѯ
function showLLClaimRegisterQuery()
{
    window.open("LLClaimRegisterQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');    
//    window.open("LLClaimRegisterQueryInput.jsp","������ѯ");    
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
//    window.open(strUrl,"���������");
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

//�ɲ�ѯ��LLClaimRegisterQuery.js������ʱ����
function afterQueryLL2(arr)
{
//select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,
//       operator,accidentsite,accidentdate,accidentreason,RgtConclusion,ClmState, from llregister  
    //ֱ�ӻ�ȡ������ѯ����
    fm.RptNo.value = arr[0];
    fm.RptorName.value = arr[1];
    fm.RptorPhone.value = arr[2];
    fm.RptorAddress.value = arr[3];
    fm.Relation.value = arr[4];
    fm.Operator.value = arr[5];
    fm.AccidentSite.value = arr[6];
    fm.RptDate.value = arr[7];
    fm.occurReason.value = arr[8];
    fm.RgtConclusion.value = arr[9];
    fm.CaseState.value = arr[10];    
    fm.MngCom.value = arr[11];    //alert(425);
    showOneCodeName('relation','Relation','RelationName');//���������¹��˹�ϵ
//    showOneCodeName('RptMode','RptMode','RptModeName');//������ʽ(?????)    
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������
    
    //��ѯ����¼���
    var LLAccident = new Array;
    /*var strSQL1 = "select AccNo,AccDate from LLAccident where AccNo in (select CaseRelaNo from LLCaseRela "
                + "where CaseNo = '"+arr[0]+"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql9");
	mySql.addSubPara(arr[0]); 
//    alert(LLAccident);
    LLAccident = easyExecSql(mySql.getString());
    fm.AccNo.value = LLAccident[0][0];
    fm.AccidentDate.value = LLAccident[0][1];
    
    //��ѯ��ó�������Ϣ
    initSubReportGrid();
   /* var strSQL2 = " select CustomerNo,Name,"
        + " Sex,"
        + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
        + " Birthday,"
        + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
        + " from LDPerson where "
        + " CustomerNo in("
        + " select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
//    alert(strSQL2);     
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql10");
	mySql.addSubPara(rptNo);             
    easyQueryVer3Modal(mySql.getString(),SubReportGrid);    
    
    //���ÿɲ�����
    fm.AuditIdea.disabled = false;
    fm.AuditConclusion.disabled = false;
    fm.SpecialRemark.disabled = false;
    //fm.ConclusionSave.disabled = false;
    //fm.ConclusionUp.disabled = false;
    fm.ConclusionBack.disabled = false;
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
  fm.SocialInsuFlag.value = "";
  fm.hospital.value = "";
  fm.TreatAreaName.value = "";
  fm.AccidentDate2.value = "";
  fm.accidentDetail.value = "";
  fm.accidentDetailName.value = "";
//  fm.IsDead.value = "";
//  fm.IsDeadName.value = "";
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
        fm.SexName.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,4));
        
        fm.SocialInsuFlag.value = SubReportGrid.getRowColData(i,6);

        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql11");
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
//                alert(tClaim);
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
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,EditFlag,AffixConclusion,(case EditFlag when '0' then '��' when '1' then '��' end),(case AffixConclusion when '0' then '��' when '1' then '��' end) from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql12");
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
    fm.IsModify.value = tSubReport[0][9];
    fm.IsAllReady.value = tSubReport[0][10];
    fm.IsModifyName.value = tSubReport[0][11];
    fm.IsAllReadyName.value = tSubReport[0][12];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult2();
//    showOneCodeName('IsAllReady','IsModify','IsModifyName');//��Ҫ��Ϣ�޸ı�־
//    showOneCodeName('IsAllReady','IsAllReady','IsAllReadyName');//��֤��ȫ��־

//����ѡ�еĳ�������ʾ��ص�������
afterMatchDutyPayQuery();
}

//ѡ��PolDutyCodeGrid��Ӧ�¼�
function PolDutyCodeGridClick()
{
    //alert("ttttt");
    //��ձ�    
    fm.GiveType.value = "";//�⸶����
    fm.RealPay.value = "";
    fm.AdjReason.value = "";//����ԭ��
    fm.AdjReasonName.value = "";//
    fm.AdjRemark.value = "";//������ע
    fm.GiveReasonDesc.value = "";//�ܸ�����
    fm.GiveReason.value = "";//�ܸ�ԭ�����
    fm.SpecialRemark.value = "";//���ⱸע
    //���ð�ť
    fm.addUpdate.disabled = false;//����޸�
    //�õ�mulline��Ϣ
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.PolNo.value = PolDutyCodeGrid.getRowColData(i,1);//������
        //������,20070418ע..
        //var psql="select polstate from lcpol where polno='"+fm.PolNo.value+"' ";
        //var tpolstate=new Array;
        //tpolstate=easyExecSql(psql); 
        //fm.polstate.value = tpolstate;
        //showOneCodeName('polstate','polstate','polstateName');        
        //jinsh20070410
        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,13);//�⸶����
        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,21);//�������
        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,22);//����ԭ��
        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,23);//����ԭ��
        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,24);//������ע
        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,15);//�ܸ�ԭ�����
        fm.GiveReasonDesc.value = PolDutyCodeGrid.getRowColData(i,17);//�ܸ�����
        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,18);//���ⱸע
        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
        showOneCodeName('llprotestreason','GiveReason','GiveReasonName');
    }
    //��ʾ���ز�
    divBaseUnit5.style.display= "";
    choiseGiveTypeType();
}

//�Ա��������޸�
function AddUpdate()
{
	if(fm.AdjReason.value == "00"||fm.AdjReason.value == "14"){
    	if(fm.AdjRemark.value == null || fm.AdjRemark.value =="null" || fm.AdjRemark.value == ""){
    		alert("����ԭ��Ϊͨ�ڸ�����Э�����ʱ������¼�������ע!");
    		return false;
    	}
    }
    if(PolDutyCodeGrid.getSelNo() <= 0)
    {
        alert("����ѡ��Ҫ����ı�����Ϣ,��ִ�д˲�����");
        return;
    }
    
    if(fm.GiveType.value==null || fm.GiveType.value=='')
    {
    	alert ("����ѡ�����⸶����!");
    	return false;
    }
    //������20070419�޸�,ԭ��:����ԭ���Ϊ��¼��.....
//    if(fm.AdjReason.value=="")
//    {
//    	alert ("����ѡ�����ԭ��!");
//    }
    
    if(fm.GiveType.value=='0')
    {
        //������20070419�޸�,ԭ��:����ԭ���Ϊ��¼��.....
        if(fm.AdjReason.value==null || fm.AdjReason.value=="")
        {
        	alert ("����ѡ�����ԭ��!");
        	return false;
        }
    }
    
    if(fm.GiveType.value=='1')
    {
        if(fm.GiveReason.value ==null || fm.GiveReason.value =="")
        {
        	alert ("����ѡ��ܸ�ԭ��!");
        	return false;
        }
        
        if(fm.GiveReasonDesc.value ==null || fm.GiveReasonDesc.value =="")
        {
        	alert ("����¼��ܸ�����!");
        	return false;
        }
    }
       
   
    if(checkAdjMoney()==false)
    {
    	return false;
    }

    
    //��ͬһ�ⰸ���µ��⸶���۶���һ����....������20070418�޸�
    for(var j =0;j<PolDutyCodeGrid.mulLineCount;j++)
    {
    	PolDutyCodeGrid.setRowColData(j,13,fm.GiveType.value);//�⸶����
    }
    
    
    
    //�޸����.......
    var i = PolDutyCodeGrid.getSelNo() - 1;//�õ�������

    PolDutyCodeGrid.setRowColData(i,13,fm.GiveType.value);//�⸶����
    PolDutyCodeGrid.setRowColData(i,15,fm.GiveReason.value);//�ܸ�ԭ�����
    PolDutyCodeGrid.setRowColData(i,17,fm.GiveReasonDesc.value);//�ܸ�����
    PolDutyCodeGrid.setRowColData(i,18,fm.SpecialRemark.value);//���ⱸע
    PolDutyCodeGrid.setRowColData(i,21,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,22,fm.AdjReason.value);//����ԭ��
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjRemark.value);//������ע
    
    if(fm.GiveType.value == 1)
    {
    	PolDutyCodeGrid.setRowColData(i,14,"�ܸ�");//�⸶��������
    }
    
    if(fm.GiveType.value == 0)
    {
    	PolDutyCodeGrid.setRowColData(i,14,"����");//�⸶��������
    }

//    if(fm.GiveType.value == 0)
//    {
//    	//��ͬһ�ⰸ���µ��⸶���۶���һ����....������20070418�޸�
//    	for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
//    	{
//    		PolDutyCodeGrid.setRowColData(k,14,"����");//�⸶��������
//    	}
//    }
    //������20070417�޸�ԭ��:��ǰֻ�������⸶���� 
    //------------------------------------jinsh20070515--------------------------------------//
		/*if(fm.GiveType.value == 2)
		{
			//��ͬһ�ⰸ���µ��⸶���۶���һ����....������20070418�޸�
    	for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
    	{
    		PolDutyCodeGrid.setRowColData(k,14,"Э���⸶");//�⸶��������
    	}
		}
		if(fm.GiveType.value == 3)
		{
			//��ͬһ�ⰸ���µ��⸶���۶���һ����....������20070418�޸�
			for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
			{
			    PolDutyCodeGrid.setRowColData(k,14,"ͨ���⸶");//�⸶��������
			}
		}*/
		//------------------------------------jinsh20070515--------------------------------------//
//    if(fm.GiveType.value == 1)
//    {
//    	//��ͬһ�ⰸ���µ��⸶���۶���һ����....������20070418�޸�
//			for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
//			{
//			    PolDutyCodeGrid.setRowColData(k,14,"�ܸ�");//�⸶��������
//			}
//    }

    
    fm.saveUpdate.disabled = false;//�����޸�
}

//�Ա����޸ı��浽��̨
function SaveUpdate()
{
	
	 var tClaimNo = fm.RptNo.value;
	  
	//У�鱣�����⸶���뱾���⸶��֮�Ͳ��ܴ����˻����
	//tSQL = "select distinct polno from llclaimpolicy where clmno='"+tClaimNo+"' and riskcode in (select riskcode from lmrisk where insuaccflag='Y')";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql13");
	mySql.addSubPara(tClaimNo);      
	arr = easyExecSql(mySql.getString());

	//tSQL1 = "select count (distinct polno) from llclaimdetail where clmno='"+tClaimNo+"' and riskcode in (select riskcode from lmrisk where insuaccflag='Y')";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql14");
	mySql.addSubPara(tClaimNo);       
	arr1 = easyExecSql(mySql.getString());


 if(arr1[0][0] > 0)
 {
	for (var t =0;t<arr1[0];t++)
	 {

	var j=0;

	for(var k =0;k<PolDutyCodeGrid.mulLineCount;k++)
	 {
    var tPolNo=PolDutyCodeGrid.getRowColData(k,1);

    var tPolNo1=arr[t][0];

	     if (tPolNo == tPolNo1)
	        {
	    	     	var tRealM  = parseFloat(PolDutyCodeGrid.getRowColData(k,21));//�������
	    	 			j=j+tRealM;
	        }
	       
	  }
	  


	// var strSQL2 = "select sum(money) from lcinsureacctrace where polno='"+arr[t]+"'";
	 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql15");
	mySql.addSubPara(arr[t]);  
	 arr2 = easyExecSql(mySql.getString());


	 //var strSQL3 = "select nvl(sum(b.RealPay),0) from LLClaim a,LLClaimDetail b where a.ClmNo = b.ClmNo and a.ClmState in ('50') and a.ClmNo <>'"+tClaimNo+"' and b.GiveType != '1' and b.PolNo ='"+arr[t]+"'";
	 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql16");
	mySql.addSubPara(tClaimNo);  
	mySql.addSubPara(arr[t]);  
	 arr3 = easyExecSql(mySql.getString());

	 var p = pointTwo(j) + pointTwo(arr3[0][0]);
	 var u=arr2[0];

	 var intev = (pointTwo(p))-(pointTwo(u));

	if(intev>0)
	    {
		    alert("������������������֮�ͳ����ʻ��������!");
			  return false;
	    }

	 }
	
 }
 

    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    submitForm();
}

//�����ύ����
function submitForm()
{
    //�ύ����
    //showInfo.close();
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


   
   // showSubmitFrame(mDebug);
//    fm.fmtransact.value = "INSERT"
//    fm.action = './LLClaimAuditSave.jsp';
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";    
}

//�ύ�����,����
function afterSubmit1( FlagStr, content )
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

//�ύ�����,���²�ѯ
function afterSubmit3( FlagStr, content , AuditConclusion)
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


        queryRegister();
    }
    if(AuditConclusion != '4' && AuditConclusion != '5'){
       //goToBack();
    }
    tSaveFlag ="0";
    fm.conclusionSave1.disabled = false;
}

//���ذ�ť
function goToBack()
{
    location.href="LLClaimAuditMissInput.jsp";
}

//��˽��۱���
function ConclusionSaveClick()
{  
  if(!(confirm("ȷ�ϱ���������ۣ�")==true)){
    return false;
  }
  
  if(fm.AuditIdea.value==null||fm.AuditIdea.value==""){
	  alert("����д������");
	  return false;
  }
  
  //var tclmSql="select clmstate from llclaim where clmno='"+fm.RptNo.value+"' ";
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql17");
	mySql.addSubPara(fm.RptNo.value);   
  var ttclmstate=easyExecSql(mySql.getString());
  if (ttclmstate=="60"||ttclmstate=="70"||ttclmstate=="80")
  {
    alert("���ⰸ�Ѿ��᰸��");
     return;
  }   
   
     //��ѯ�Ƿ���й�ƥ����� 2009-08-04 9:08
 /* var Detailsql = "select count(1) from LLClaimDetail where"
           + " ClmNo = '" + fm.RptNo.value + "'";*/
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql18");
	mySql.addSubPara(fm.RptNo.value);   
  var tDetailDutyKind = easyExecSql(mySql.getString());
  if (tDetailDutyKind == 0)
  {
      alert("���Ƚ���ƥ�䲢����!");
      return;
  }
   
  //�жϱ������Ƿ�Ϊ��
  if (fm.AuditConclusion.value == "" || fm.AuditConclusion.value == null)
  { 
    alert("������д��˽���!");
    return;
  } 
  else {        
      if (fm.AuditConclusion.value == "1")
        {            
            var tGivetype = new Array;
           /* var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";*/
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql19");
			mySql.addSubPara(fm.RptNo.value);   
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                for (var i=0; i<tGivetype.length; i++)
                {
                    if (tGivetype[i] != "1")
                    {                      
                        alert("���ȫ��Ϊ�ܸ�,������ȫ���ܸ�����!");
                        return;
                    }
                }
            }
            
            if (fm.ProtestReason.value == "" || fm.ProtestReason.value == null)
            { 
              alert("����ѡ��ܸ�ԭ��!");
              return;
            } 
            
            if (fm.ProtestReasonDesc.value == "" || fm.ProtestReasonDesc.value == null)
            { 
              alert("������д�ܸ�����!");
              return;
            } 
        }
  else if (fm.AuditConclusion.value == "0"||fm.AuditConclusion.value == "4")
        {//---------------jinsh--20070524--����ð������ڵ�������в�׼�᰸---------------//
        	//var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000009125','0000009145','0000009165','0000009175') and missionprop1='"+fm.RptNo.value+"'";
        	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql20");
			mySql.addSubPara(fm.RptNo.value);   
        	var JustStateCount=easyExecSql(mySql.getString());
        	if(parseInt(JustStateCount)>0)
        	{      				
        		alert("�ð������ڵ�����������Ժ������᰸!");
        		return;
        	}
        //---------------jinsh--20070524--����ð������ڵ�������в�׼�᰸---------------//
           
            var tGivetype = new Array;
            /*var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql21");
			mySql.addSubPara(fm.RptNo.value);  
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGivetype.length; i++)
                {
                    if (tGivetype[i] == "0" || tGivetype[i] == "2" || tGivetype[i] == "3")
                    {
                        tYesNo = 1;
                    }
                }
                if (tYesNo == 0)
                {
                    showInfo.close();                
                    alert("����ȫ��Ϊ�ܸ�,�����¸�������!");
                    return;
                }
            }
        }
    }
    

    //����Ǹ�����ܸ�����,�ύǰ����
    //alert("fm.AuditConclusion.value:"+fm.AuditConclusion.value);
  	if (!(fm.AuditConclusion.value ==null||fm.AuditConclusion.value==""))
    {
  		if(fm.AuditConclusion.value == "0" || fm.AuditConclusion.value == "1" || fm.AuditConclusion.value == "4")
  		{
  	        //���顢�ʱ���������Ͷ���У��,�������㡢��ͬ����
  	        if (!checkPre())
  	        {
  	            return;
  	        } 
  	        
  	        //���ֻ�л��ⲻ���ж�������
  	       /* var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
  	                 + getWherePart( 'ClmNo','ClmNo' );*/
  	        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql22");
			mySql.addSubPara(fm.ClmNo.value);  
  	        var tDutyKind = easyExecSql(mySql.getString());
  	        if (tDutyKind != '09')
  	        {
  	            //����¸�������,У�������˷���
  	            if (fm.AuditConclusion.value == "0"|| fm.AuditConclusion.value == "4"|| fm.AuditConclusion.value == "5")
  	            {
  	               /* var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
  	                            + getWherePart( 'ClmNo','ClmNo' );*/
  	                mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
					mySql.setSqlId("LLClaimAuditSql23");
					mySql.addSubPara(fm.ClmNo.value);  
  	                var tBNF = easyExecSql(mySql.getString());                
  	                if (tBNF)
  	                {
  	                    for (var i = 0; i < tBNF.length; i++)
  	                    {
  	                        if (tBNF[i] == '0')
  	                        {
  	                            alert("�����˷���û�����!");
  	                            return;
  	                        }
  	                    }
  	                }
  	            }
  	        }
  		}

    }

    
    //����Ǹ����������ж��Ƿ��н����ͬ����
/*
    if (fm.AuditConclusion.value == "0")
    {
        var strSql5 = "select count(1) from LLContState where DealState in ('D01','D02','D03') "
                    + getWherePart( 'ClmNo','ClmNo' );
        var tCount = easyExecSql(strSql5);
        if (tCount > 0)
        {
            alert("�ѽ��н����ͬ���������¸������ۣ�");
            return;
        }
    }
*/

   //�ж�4λ�������������ϱ���������
//    if (fm.AuditConclusion.value == "4" && document.allManageCom.value.length == 4 )
//    {        
//        alert("����2λ�����ϱ���������");
//        return false;
//    }
    
    /**
     * zhangzheng 2009-02-10 
     * MS��������Ȩ����ι���û��ȷ��,��ʱ��ס���У��;
     */
    
    //������ԱȨ���ж�
//    if (fm.AuditConclusion.value == "0")
//    {
//	    var tClaimType1 ;
//	    var tClaimType2 ;
//	    var tRealpay ;
//	    var tGiveType ;
//	    var tappndmax ;
//	    var tappndmax1 ;
//	    var tappndmax2 ;
//	
//	    var txsql="select distinct givetype from llclaimdetail where clmno='"+fm.RptNo.value+"' ";
//	    tGiveType=easyExecSql(txsql);
//	    
//	    if (tGiveType.length>1)
//	    {
//	        showInfo.close();
//	        fm.conclusionSave1.disabled=false;        
//	       alert('�⸶���۲�ͳһ������˵�����������޸ģ�');	
//	       return;
//	    }
//    
//     if(tGiveType == '0' && tGiveType.length==1 )     
//     {
//	     //01.��ѯ���ⰸ���������ֵ     
//	     var csql="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
//	     var tcustomerno=new Array();
//	     tcustomerno=easyExecSql(csql);
//	     for (var i=0;i<tcustomerno.length;i++)
//	     {         
//	         var strSql00 = " select sum(realpay) from llclaimpolicy "
//	                      + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='10') and insuredno='"+tcustomerno[i]+"'";                  
//	         
//	         var tSubReport = new Array;
//	         tSubReport = easyExecSql(strSql00);    
//	         var   tRealpay1 = tSubReport[0][0];
//	         //alert ("�����⸶:"+tRealpay1);
//	         //var tInsuredno1 = tSubReport[0][1];
//	         
//	          if(tRealpay1==null || tRealpay1 == "")
//	             {
//	               //  alert("δ��ѯ�����ⰸ���⸶��");
//	              //   return false;
//	              tRealpay1=0;       
//	             }
//	          else
//	           	{
//	           		//alert("tClaimType1");
//	           	   tClaimType1='10';
//	           	}
//	         
//	         var strSql01 = " select sum(realpay) from llclaimpolicy "
//	                       + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='30') and insuredno='"+tcustomerno[i]+"'";                  
//	         
//	         var tSubReport1 = new Array;
//	         tSubReport1 = easyExecSql(strSql01);
//	         var   tRealpay2 = tSubReport1[0][0];
//             //alert ("�������⸶:"+tRealpay2);
//             // var tInsuredno2 = tSubReport[0][1];
//             if(tRealpay2==null || tRealpay2 == "")
//                 {
//             //         alert("δ��ѯ�����ⰸ���⸶��");
//             //         return;
//                    tRealpay2=0;           
//             }
//             else
//             {
//               	   	tClaimType2='30';
//             }
//               
//         //02.��ѯ���������ֵ�Ŀͻ��ĳ�������
//              /*var strSql02 = "select distinct reasoncode from LLAppClaimReason where caseno = '"+fm.ClmNo.value+"' and customerno = '"+fm.customerNo.value+"'";
//              var tSubReport2 = new Array;
//              tSubReport2 = easyExecSql(strSql02);
//                if(tSubReport2 == null ){
//                     alert("δ��ѯ�����ⰸ�ĳ������ͣ�");
//                     return;
//                    }
//              for (var i= 0;i < tSubReport2.length ; i++ )
//              {
//                  var tReasonCode = tSubReport2[i][0].substring(1,3);
//                  if(tReasonCode == 01 || tReasonCode == 02 || tReasonCode == 04){
//                       tClaimType = 10;//����
//                       break;
//                      }else{
//                       tClaimType = 30;//������
//                      }
//              }*/
//         //03.1��ѯ������Ա��������͵�����Ȩ��
//         //0301.1��ѯ������Ա������Ȩ��
//                 var strSql0301 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
//                 var tBasemax0301 = easyExecSql(strSql0301);
//                 if (tBasemax0301 == null || tBasemax0301 == "")
//                 {
//         //            alert("δ��ѯ����������Ȩ�ޣ�");
//         //            return;
//                       tBasemax0301 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
//                 }
//         //0302.1��ѯ������Ա������Ȩ��
//                 var strSql0302 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
//                 var tBasemax0302 = easyExecSql(strSql0302);
//                 if (tBasemax0302 == null || tBasemax0302 == "")
//                 {
//         //            alert("δ��ѯ����������Ȩ�ޣ�");
//         //            return;
//                       tBasemax0302 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
//                 }
//         //0303.1������Ա�������ԱȨ���ж�
//                 tBasemax0301 = tBasemax0301*1;
//                 tBasemax0302 = tBasemax0302*1;
//                 if(tBasemax0301 > tBasemax0302){
//                   tBasemax1 = tBasemax0301;
//                 }else{
//                   tBasemax1 = tBasemax0302;
//                 }
//         
//         //03.2��ѯ������Ա��������͵�����Ȩ�� 
//         //0301.2��ѯ������Ա������Ȩ��        
//                 var strSql03011 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
//                 var tBasemax03011 = easyExecSql(strSql03011);
//                 if (tBasemax03011 == null || tBasemax03011 == "")
//                 {
//         //            alert("δ��ѯ����������Ȩ�ޣ�");
//         //            return;
//                       tBasemax03011 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
//                 }
//         //0302.2��ѯ������Ա������Ȩ��
//                 var strSql03022 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
//                 var tBasemax03022 = easyExecSql(strSql03022);
//                 if (tBasemax03022== null || tBasemax03022 == "")
//                 {
//         //            alert("δ��ѯ����������Ȩ�ޣ�");
//         //            return;
//                       tBasemax03022 = 0; //Ϊ��ѯ����Ĭ��Ϊ0
//                 }
//         //0303.2������Ա�������ԱȨ���ж�
//                 tBasemax03011 = tBasemax03011*1;
//                 tBasemax03022 = tBasemax03022*1;
//                 if(tBasemax03011 > tBasemax03022){
//                   tBasemax2 = tBasemax03011;
//                 }else{
//                   tBasemax2 = tBasemax03022;
//                 }
//                 
//         //04.Ȩ���ж�
//                 tBasemax1 = tBasemax1*1;
//                 tBasemax2 = tBasemax2*1;
//         
//               		tRealpay1 = tRealpay1*1;
//               		tRealpay2 = tRealpay2*1;      	                    
//                        
//                 //alert ("��������⸶:"+tBasemax1);
//                           
//                 //alert ("����������⸶:"+tBasemax2);
//              if(tRealpay1 > tBasemax1 || tRealpay2 > tBasemax2)
//               {
//            	  //05.��Ȩ���ϱ������������ж�
//            	  //0501.��ѯ�û����������Ȩ��,ͬʱ���û��������userstate 0 ��Ч 1ʧЧ
//                 var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//                 var tChecklevel  = easyExecSql(strSql0501);
//                 //0502.��ѯ�û������Ȩ�޵��⸶���
//                 var strSql0502 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType1+"' and Popedomlevel = '"+tChecklevel+"'";
//                 var strSql05022 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType2+"' and Popedomlevel = '"+tChecklevel+"'";
//                 var tBasemax3  = easyExecSql(strSql0502);
//                 var tBasemax4  = easyExecSql(strSql05022);
//                 tBasemax3 = tBasemax3*1;
//                 tBasemax4 = tBasemax4*1;
//                 
//                 if(tRealpay1 > tBasemax3 || tRealpay2 > tBasemax4)
//                   {
//                	 //�ؼ�Ȩ���趨
//                    fm.ConclusionSave.disabled = false;
//                    fm.ConclusionUp.disabled = true;
//                    fm.ConclusionBack.disabled = true;
//                    showInfo.close();
//                    fm.conclusionSave1.disabled=false;                     
//                     alert("����Ȩ�޲��㣡�뽫�ð����ϱ�����������");
//                     return;
//                   }                  
//                   else{
//                	   //�ؼ�Ȩ���趨 
//                    fm.ConclusionSave.disabled = true;
//                    fm.ConclusionUp.disabled = false;
//                    fm.ConclusionBack.disabled = true;
//                    showInfo.close();
//                    fm.conclusionSave1.disabled=false;                         
//                     alert("����Ȩ�޲��㣡�뽫�ð�����Ȩ���ϱ���");
//                     return;
//                   }
//                   
//               }
//     }  //for customerno
//   }else if ((tGiveType == '2'||tGiveType == '3') && tGiveType.length== 1 )
//    {            
//      var tsql1="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('tOperator').value+"' "; //������Ա
//      tappndmax1=easyExecSql(tsql1);
//      var tsql2="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('Operator').value+"' ";  //������Ա
//      tappndmax2=easyExecSql(tsql2);     
//      if (parseFloat(tappndmax2)>parseFloat(tappndmax1))
//        {      	   
//      	    tappndmax=tappndmax2;      	    
//      	}else
//      		{
//      			tappndmax=tappndmax1;  
//      		}          	
//       var csql2="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
//       var xcustomerno=new Array();
//       xcustomerno=easyExecSql(csql2);
//       for (var k=0;k<xcustomerno.length;k++)
//       {         		
//         var maxsql="select sum(realpay) from llclaimpolicy where clmno = '"+fm.RptNo.value+"' and insuredno='"+xcustomerno[k]+"' ";
//         tRealpay=easyExecSql(maxsql);      
//         if (parseFloat(tappndmax)<parseFloat(tRealpay)) 
//         { 
//            var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//            var tChecklevel  = easyExecSql(strSql0501);
//            
//            var strSql0502 = "select distinct appendmax from LLClaimPopedom where Popedomlevel = '"+tChecklevel+"'";                 
//            var tBasemax3  = easyExecSql(strSql0502);                 
//            tBasemax3 = tBasemax3*1;                 
//            
//            if(tRealpay > tBasemax3)
//              {
//               fm.ConclusionSave.disabled = false;
//               fm.ConclusionUp.disabled = true;
//               fm.ConclusionBack.disabled = true;
//               showInfo.close();
//               fm.conclusionSave1.disabled=false;                     
//                alert("����ͨ�ڡ�Э���⸶Ȩ�޲������뽫�ð����ϱ�����������");
//                return;
//              }                  
//              else{         
//               fm.ConclusionSave.disabled = true;
//               fm.ConclusionUp.disabled = false;
//               fm.ConclusionBack.disabled = true;
//               showInfo.close();
//               fm.conclusionSave1.disabled=false;                         
//                alert("����ͨ�ڡ�Э���⸶Ȩ�޲��㣡�뽫�ð�����Ȩ���ϱ���");
//                return;
//              }         	        	 
//          } 
//       }    	
//    }else if(tGiveType == '1' && tGiveType.length==1 )         
//    {
//      var tsql="select checklevel from llclaimuser where usercode='"+document.all('tOperator').value+"' ";	//������Ա
//      var tchecklevel=new Array;
//      tchecklevel=easyExecSql(tsql);
//      if (tchecklevel=="A"||tchecklevel=="B1"||tchecklevel=="B2"||tchecklevel=="B3")
//      {
//          var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//          var tChecklevel  = easyExecSql(strSql0501);              
//          
//          if (tChecklevel=="A"||tChecklevel=="B1"||tChecklevel=="B2"||tChecklevel=="B3")
//            {
//             fm.ConclusionSave.disabled = false;
//             fm.ConclusionUp.disabled = true;
//             fm.ConclusionBack.disabled = true;
//             showInfo.close();
//             fm.conclusionSave1.disabled=false;                     
//              alert("���ľܸ�Ȩ�޲������뽫�ð����ϱ�����������");
//              return;
//            }                  
//            else{         
//             fm.ConclusionSave.disabled = true;
//             fm.ConclusionUp.disabled = false;
//             fm.ConclusionBack.disabled = true;
//             showInfo.close();
//             fm.conclusionSave1.disabled=false;                         
//              alert("���ľܸ�Ȩ�޲��㣡�뽫�ð�����Ȩ���ϱ���");
//              return;
//            }          
//
//      }                
//    }
// } //fm.AuditConclusion.value == "0"

/*
        //����л��Ᵽ��,������ʾ,Ҫ����л��⴦��
    if (fm.AuditConclusion.value == "0")
    {
        var tSqlExe = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 +" and clmno = '" + fm.RptNo.value + "'"
                 +" and getdutykind in ('109','209') "
                 ;
        var tDutyExe = easyExecSql(tSqlExe);
        if (tDutyExe == '09')
        {
            alert("ƥ������ı����л�����Ϣ,��ȷ���Ƿ�����˻��⴦��!!!");
            alert("ƥ������ı����л�����Ϣ,��ȷ���Ƿ�����˻��⴦��!!!");   
        }
    }
*/
    
    //���꿹���ڵ�У��:���Ϊ����������Ч�ճ�����������2�꣨��2�꣩���������ʾ��Ϣ
    if (!checkAccDate(fm.RptNo.value))
    {
        return;
    }    

    fm.conclusionSave1.disabled=true;
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

  
    //�ύ
    fm.action="./LLClaimAuditConclusionSave.jsp"
    fm.fmtransact.value = "INSERT";
    showInfo.close();
    submitForm();
    
}

//���꿹���ڵ�У��:���Ϊ����������Ч�ճ�����������2�꣨��2�꣩���������ʾ��Ϣ
function checkAccDate(tRptNo)
{	
	var flag=false;//�Ƿ񳬹������ʾ
	var tContno="";//��������ı�����
	
	var tAccDate;//���յĳ�������:LLAccident.AccDate
    /*var strQSql = "select AccDate from LLAccident where AccNo in (select CaseRelaNo from LLCaseRela "
                + "where CaseNo = '"+tRptNo+"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql24");
	mySql.addSubPara(tRptNo);  
    var tDate = easyExecSql(mySql.getString());
    if (tDate == null)
    {
        alert("δ��ѯ����������!");
        return true;
    }else{
    	tAccDate=tDate[0][0];
	}

    /*var strQSql = "select cvalidate,contno from lccont a where a.contno in "
    			+" (select distinct b.contno from LLClaimPolicy b where b.clmno = '"+tRptNo+"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql25");
	mySql.addSubPara(tRptNo);  
    var tCvalidate = easyExecSql(mySql.getString());//�ŵ��и��˱�����Ӧ��Ч����
    if (tCvalidate == null)
    {
        alert("δ��ѯ���˰�����Ӧ��������Ч����!");
        return true;
    }
	for(var i=0;i<tCvalidate.length;i++){
		//alert("tCvalidate["+i+"]:"+tCvalidate[i][0]);
		//alert("tAccDate:"+tAccDate);
		if(dateDiff(tCvalidate[i][0]+"",tAccDate,'M') >= 24){	//������Ч�������������ȣ�������ڻ��������(24��)
			tContno+="["+tCvalidate[i][1]+"]";
			flag=true;
		}
	}

	if(flag){
		if(!confirm("��ע�⣺�ð����漰������������"+tContno+"����Ч�������������Ѵﵽ�򳬹����꣬���ò��ɿ��硣")){
			return false;
		}
	}
	
	return true;
}

//�����˷���ǰȷ�������\����\�ʱ�\�����Ƿ����,��������\��ͬ����
function checkPre()
{
//    alert("��ʼ");
    //�����
    /*var strSql0 = "select AffixConclusion from LLAffix where "
               + "RgtNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql26");
	mySql.addSubPara(fm.RptNo.value);  
    var tAffixConclusion = easyExecSql(mySql.getString());
    
    if (tAffixConclusion)
    {
        for (var i = 0; i < tAffixConclusion.length; i++)
        {
//            alert(tAffixConclusion[i]);
            if (tAffixConclusion[i] == null || tAffixConclusion[i] == "")
            {
//                alert("���������û�����!");
//                return false;
            }
        }
    }
    //����
    /*var strSql1 = "select FiniFlag from LLInqConclusion where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql27");
	mySql.addSubPara(fm.RptNo.value);  
    var tFiniFlag = easyExecSql(mySql.getString());
    
    if (tFiniFlag)
    {
        for (var i = 0; i < tFiniFlag.length; i++)
        {
            if (tFiniFlag[i] != '1')
            {
                alert("����ĵ���û�����!");
                return false;
            }
        }
    }
    //�ʱ�

    /*var strSql2 = "select SubState from LLSubmitApply where "
               + "clmno = '" + fm.RptNo.value + "'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql28");
	mySql.addSubPara(fm.RptNo.value);  
    var tSubState = easyExecSql(mySql.getString());
    
    if (tSubState)
    {
        for (var i = 0; i < tSubState.length; i++)
        {
            if (tSubState[i] != '1')
            {
                alert("����ĳʱ�û�����!");
                return false;
            }
        }
    }

    //����=============================================================Ŀǰ�д�����
/*
    //��������\��ͬ�����Ƿ����
    var strSql3 = "select conbalflag,condealflag from llclaim where "
                + "clmno = '" + fm.RptNo.value + "'";
    var tFlag = easyExecSql(strSql3);
//    alert(tFlag);
    if (tFlag == null)
    {
        alert("δ���б�������ͺ�ͬ�������!");
        return;
    }
    if (tFlag[0][0] != '1')
    {
        alert("δ���б����������!");
        return;
    }
    if (tFlag[0][1] != '1')
    {
        alert("δ���к�ͬ�������!");
        return;
    }
*/
    return true;
}

//��˽���ȷ��
function AuditConfirmClick()
{
    //�ж���˽���
    var mngcom = new Array;
    var strSql = "select AuditConclusion from LLClaimUWMain where "
               + "clmno = '" + fm.RptNo.value + "'";//
//               +" and ExamConclusion is null";
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql29");
	mySql.addSubPara(fm.RptNo.value);  */
    var tt=easyExecSql(strSql);
    if(!tt){
    	alert("���������δ������˽��ۣ�");
        return false;
    }
    var tGivetype = tt[0][0];
    var pGivetype = tt[0][0];
    
    if (tGivetype == null)
    {
        alert("���ȱ�����˽��ۣ�");
        return;
    }
    else
    {    	     
		if (tGivetype == '5')
    	{    	         	     
    	    var tsql="select mngcom,operator from llregister where rgtno='"+fm.RptNo.value+"' ";    	  
    	   /*   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql30");
	mySql.addSubPara(fm.RptNo.value);  */
    	     mngcom = easyExecSql(tsql);
    	     var tmanagecom=mngcom[0][0];   	      	  
    	     var toperator=mngcom[0][1];    	        	      	  
    	      if(tmanagecom==null||tmanagecom=="")
    	      {
    	      		alert("������������ʧ��!");
    	      		return;
    	      }
    	      if(toperator==null||toperator=="")
    	      {
    	      		alert("������������Աʧ��!");
    	      		return;
    	      } 	
		}
        else if (tGivetype == "1")
        {
            var tGivetype = new Array;
           /* var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql31");
			mySql.addSubPara(fm.RptNo.value);  
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                for (var i=0; i<tGivetype.length; i++)
                {
                    if (tGivetype[i] != "1")
                    {
                        alert("���ȫ��Ϊ�ܸ�,������ȫ���ܸ�����!");
                        return;
                    }
                }
            }
        }
        else if (tGivetype == "0")
        {
            var tGivetype = new Array;
           /* var strSql = "select givetype from LLClaimDetail where "
                       + "clmno = '" + fm.RptNo.value + "'";*/
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql32");
			mySql.addSubPara(fm.RptNo.value);  
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGivetype.length; i++)
                {
                    if (tGivetype[i] == "0")
                    {
                        tYesNo = 1;
                    }
                }
                if (tYesNo == 0)
                {
                    alert("����ȫ��Ϊ�ܸ�,�����¸�������!");
                    return;
                }
            }
        }
    }
    
    //����Ǹ�����ܸ�����,�ύǰ����
    if (tGivetype == "0" || tGivetype == "1")
    {
        //���顢�ʱ���������Ͷ���У��,�������㡢��ͬ����
        if (!checkPre())
        {
            return;
        } 
        
        //ֻ�л���ʱ����Ҫ�������˷���
/*
        var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 + getWherePart( 'ClmNo','ClmNo' );
        var tDutyKind = easyExecSql(tSQL);
        if (tDutyKind != '09')
        {
            //����¸�������,У�������˷���
            if (tGivetype == "0")
            {
                var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
                            + getWherePart( 'ClmNo','ClmNo' );
                var tBNF = easyExecSql(strSql4);
                
                if (tBNF)
                {
                    for (var i = 0; i < tBNF.length; i++)
                    {
                        if (tBNF[i] == '0')
                        {
                            alert("�����˷���û�����!");
                            return;
                        }
                    }
                }
            }
        }
*/
    }
    

    //����Ǹ����������ж��Ƿ��н����ͬ����
/*
    if (tGivetype == "0")
    {
        var strSql5 = "select count(1) from LLContState where DealState in ('D01','D02','D03') "
                    + getWherePart( 'ClmNo','ClmNo' );
        var tCount = easyExecSql(strSql5);
        if (tCount > 0)
        {
            alert("�ѽ��н����ͬ���������¸������ۣ�");
            return;
        }
    }
*/

    //�ύ
    fm.action="./LLClaimAuditSave.jsp?pAuditConclusion="+pGivetype+"&mngNo="+tmanagecom+"&operator="+toperator+"&mRgtState="+fm.rgtType.value;
    submitForm();
}

//������ѯ
function IsReportExist()
{
    //�������ˡ���Ϣ
    if (!checkInput1())
    {
        reture;
    }
    queryReport();

}

//�������ˡ���Ϣ
function checkInput1()
{
  //��ȡ������Ϣ
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccDate = fm.AccidentDate.value;//�����¹ʷ�������
    //ȡ����������Ϣ        
    var now = new Date();
    var nowYear = now.getFullYear();
    var nowMonth = now.getMonth() + 1;
    var nowDay = now.getDate();
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10); 
    //����������Ϣ
    if (CustomerNo == null || CustomerNo == '')
    {
        alert("����ѡ������ˣ�");
        return false;
    }
    //��������¹ʷ�������
    if (AccDate == null || AccDate == '')
    {
        alert("�����������¹ʷ������ڣ�");
        return false;
    }  
    else
    {
        if (AccYear > nowYear)
        {
            alert("�����¹ʷ������ڲ������ڵ�ǰ����");
            return false;  
        }
        else if (AccYear == nowYear)
        {
            if (AccMonth > nowMonth)
            {
            
                alert("�����¹ʷ������ڲ������ڵ�ǰ����AccMonth > nowMonth");
                return false;
            }
            else if (AccMonth == nowMonth && AccDay > nowDay)
            {
                alert("�����¹ʷ������ڲ������ڵ�ǰ����other");
                return false;        
            }
        }
    }
    return true;
}

//������ѯ
function queryReport()
{
    var AccNo;
    var RptContent = new Array();
    var queryResult = new Array();
    //�����¼���(һ��)
   /* var strSQL1 = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
                + ")";*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql33");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value);             
//    alert("strSQL1= "+strSQL1);
    AccNo = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
//    alert("AccNo= "+AccNo);
    if (AccNo == null )
    {
        fm.isReportExist.value = "false";
        alert("����������!");
        return
    }
    else
    {
        //���������ż�����������Ϣ(һ��)
        /*var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";*/
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql34");
			mySql.addSubPara(AccNo); 
			mySql.addSubPara(fm.customerNo.value);                
//        alert("strSQL2= "+strSQL2);
        RptContent = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
//        alert("RptContent= "+RptContent);
        if (RptContent == null)
        {
            fm.isReportExist.value = "false";
            alert("����������!");
            return;
        }
        else
        {
            fm.isReportExist.value = "true";
            //����ҳ������
            fm.AccNo.value = AccNo;
            fm.RptNo.value = RptContent[0][0];
            fm.RptorName.value = RptContent[0][1];
            fm.RptorPhone.value = RptContent[0][2];
            fm.RptorAddress.value = RptContent[0][3];
            fm.Relation.value = RptContent[0][4];
            fm.RptMode.value = RptContent[0][5];
            fm.AccidentSite.value = RptContent[0][6];
            fm.occurReason.value = RptContent[0][7];
            fm.RptDate.value = RptContent[0][8];
            fm.MngCom.value = RptContent[0][9];
            fm.Operator.value = RptContent[0][10];//alert(1576);
            showOneCodeName('relation','Relation','RelationName');//���������¹��˹�ϵ
            showOneCodeName('RptMode','RptMode','RptModeName');//������ʽ            
            showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
            showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
//            showOneCodeName('lloccurreason','IsDead','IsDeadName');//������־
            //����ҳ��Ȩ��
//            fm.AccidentDate.readonly = true;
//            fm.occurReason.disabled=true;
//            fm.accidentDetail.disabled=true;
//            fm.claimType.disabled=true;
            fm.Remark.disabled=true;

            //�����ְ�������������Ϣ(����)
           /* var strSQL3 = "select CustomerNo,Name," 
            	+ " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                + " Birthday,"
                + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                + " from LDPerson where "
                        + "CustomerNo in("
                        + "select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";*/
            //prompt("�����ְ�������������Ϣ(����)",strSQL3);
//            personInfo = decodeEasyQueryResult(easyQueryVer3(strSQL3));
//            alert("personInfo= "+personInfo);
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql35");
			mySql.addSubPara( RptContent[0][0]);  
            easyQueryVer3Modal(mySql.getString(), SubReportGrid);
        }//end else
    }
}

//������ѯ
function queryRegister()
{
    var rptNo = fm.ClmNo.value;
//    alert("rptno="+rptNo);
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    
    //�����¼��š������¹ʷ�������(һ��)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql36");
			mySql.addSubPara( rptNo);  
    //alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
    //alert("AccNo= "+AccNo);
    //���������ż�����������Ϣ(һ��)
    //-----------------------0-------1----------2-----------3-----------4------------5--------6-------7-------8--------------9-------------------------------------------------------------10------------11---------------12----------------------------------------------------------------------------13------------14-----------15-----------16------------17----------18-------19-------------20
    /*var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,(case assigneetype when '0' then 'ҵ��Ա' when '1' then '�ͻ�' end),assigneecode,assigneename,(case assigneesex when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass,(case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end),accepteddate from llregister where "
                + "rgtno = '"+rptNo+"'";*/
     mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql37");
			mySql.addSubPara( rptNo);  
    //alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(mySql.getString());
    //alert("RptContent= "+RptContent);
    //����ҳ������
     //alert("end!");
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
   if(RptContent!=null)
   {
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];//alert(1640);
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
    fm.AcceptedDate.value = RptContent[0][21];
 //   alert("have content!");
  }//alert(1659);
    showOneCodeName('llrgttype','rgtType','rgtTypeTypeName');//��������
    showOneCodeName('relation','Relation','RelationName');//alert(1661);//���������¹��˹�ϵ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������
   
    //****************************************************
    //����ҳ��Ȩ��
    //****************************************************
    fm.AccidentDate.readonly = true;
    fm.claimType.disabled=true;
    fm.Remark.disabled=true;
//����Ҫ����ʱ���������ȷ�ϰ�ť�ɼ�������ֱ�ӽ᰸��
    //if(fm.AuditConclusion.value == '4'){//��������
    ////fm.ConclusionSave.disabled = false;
    //}else{
    //fm.ConclusionSave.disabled = true;
    //}
   if( fm.AuditConclusion.value == '5'){//��������
    fm.ConclusionBack.disabled = false;
    }else{
    fm.ConclusionBack.disabled = true;
    }
    //���ð�ť++++++++++++++++++++++++++++++++++++++++�����

    //�����ְ�������������Ϣ(����)
   var strSQL3 = "select CustomerNo,Name," 
    		    +" (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                + " Birthday,"
                + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                + " from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLCase where CaseNo = '"+ rptNo +"')";
    /* mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql38");
			mySql.addSubPara( rptNo);  */
    //prompt("�����ְ�������������Ϣ(����)",strSQL3);
    easyQueryVer3Modal(strSQL3, SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
   
    //��ѯ��˽���
    queryAudit();
}

//��˽��۲�ѯ
function queryAudit()
{
    /*var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "' ";*/
               //+"and ExamConclusion is null";
//    alert("strSql= "+strSql);
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql39");
			mySql.addSubPara( fm.ClmNo.value);  
    var tAudit = easyExecSql(mySql.getString());
    //alert(tAudit);
    if (tAudit != null)
    {
        fm.AuditConclusion.value = tAudit[0][0];
        fm.AuditIdea.value = tAudit[0][1];
        fm.SpecialRemark1.value = tAudit[0][2];
        fm.ProtestReason.value = tAudit[0][3];
        fm.ProtestReasonDesc.value = tAudit[0][4];
        showOneCodeName('llprotestreason','ProtestReason','ProtestReasonName');
        showOneCodeName('llexamconclusion2','AuditConclusion','AuditConclusionName');
        //��ʾ���ز�
        choiseConclusionType();
        //fm.printAuditRpt.disabled = false;
    }
  //  alert("query audit!");
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

//�ص���Ϣ�޸�
function editImpInfo()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
  	var rptNo = fm.RptNo.value;//�ⰸ��
  	var tIsShow=0;  //Ϊ0ʱ�ð�ť��ʹ��,Ϊ1ʱ�ð�ť����ʹ��
    var strUrl="LLClaimImportModifyMain.jsp?AppntNo="+tCustomerNo+"&RptNo="+rptNo;
    //var strUrl="../claim/LLClaimImportModifyMain.jsp?AppntNo="+tCustomerNo+"&RptNo="+rptNo+"&IsShow="+tIsShow;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//ѡ���⸶����
function choiseGiveTypeType()
{
    if (fm.GiveType.value == '0')
    {
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
        divGiveTypeUnit3.style.display= "none";
    }
    else if (fm.GiveType.value == '1')
    {
        divGiveTypeUnit1.style.display= "none";
        divGiveTypeUnit2.style.display= "";
        divGiveTypeUnit3.style.display= "none";
    }
//------------------------------------jinsh20070515--------------------------------------//    
    //else if (fm.GiveType.value == '2'||fm.GiveType.value == '3')
    //{
        //divGiveTypeUnit1.style.display= "";
        //divGiveTypeUnit2.style.display= "none";
        //divGiveTypeUnit3.style.display= "";
    //}
    choiseAdjReasonType();
}
//------------------------------------jinsh20070515--------------------------------------//
function choiseAdjReasonType()
{
	 if(fm.AdjReason.value == '14'||fm.AdjReason.value == '15')
    {        
        divGiveTypeUnit3.style.display= "none";
    }else
    	{
    		 divGiveTypeUnit3.style.display= "none";
    	}
}
//------------------------------------jinsh20070515--------------------------------------//
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
    //fm.conclusionSave1.disabled = false;
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
    var strUrl="../claim/LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    
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
    var tClmNo = fm.RptNo.value;
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+tClmNo;
    //var strUrl="../claim/LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value+"&flag=''";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����˷���
function showBnf()
{
    var tSel = SubReportGrid.getSelNo();

    if( tSel == 0 || tSel == null ){
        alert( "����ѡ��һ����¼����ִ�д˲���!!!" );
    }
    else{
    	 /**=========================================================================BEG
        �޸�״̬��
        �޸�ԭ���������˷���ǰ����ҽ����������ͱ��������ܽ���У��
        �� �� �ˣ�����
        �޸����ڣ�2005.08.11
       =========================================================================**/
    var tSql ;
    var tClaimNo = fm.RptNo.value;
    
    //��ѯҽ�����������͵Ľ��
   /* tSql = " select nvl(sum(a.RealPay),0) from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       ;*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql40");
			mySql.addSubPara(tClaimNo);     
    var arr = easyExecSql( mySql.getString() );    
    var tSumDutyKind = arr[0][0];


    //��ѯ�������ҽ�����������͵Ľ��
   /* tSql = " select nvl(sum(a.RealPay),0) from LLClaimDetail a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       +" and a.GiveType not in ('1') "       
       ;*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql41");
			mySql.addSubPara(tClaimNo);      
    var brr = easyExecSql( mySql.getString() );    
    
    var tSumDutyCode = brr[0][0];
            
    if (tSumDutyKind != tSumDutyCode)
    {
        alert("��ʾ:ҽ�����⸶�ܽ��Ϊ:["+tSumDutyKind+"],�������⸶�ܽ��Ϊ:["+tSumDutyCode+"],���Ƚ��б�����������,�Ա���б�����!");
        return;
    }
    /**=========================================================================END**/
    
    //���顢�ʱ���������Ͷ���У��,�������㡢��ͬ����
    if (!checkPre())
    {
        return;
    } 
    
    var rptNo = fm.RptNo.value;//�ⰸ��
    var strUrl="../claim/LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A&InsuredNo="+fm.customerNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"");
    }

   
}

//�ʻ�����(��yuejw��2005��7��11�����)
function showLCInsureAccont()
{
  var rptNo = fm.RptNo.value;//�ⰸ��
    var strUrl="LCInsureAccMain.jsp?RptNo="+rptNo+"";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�ʻ�����");
}

//��ͬ����(��xutao��2005��7��14�����)
function showContDeal()
{
    var strUrl="LLClaimContDealMain.jsp?ClmNo="+fm.ClmNo.value;
    strUrl= strUrl + "&CustNo=" + fm.customerNo.value;
    strUrl= strUrl + "&ConType="+fm.RgtClass.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��������
function showPolDeal()
{
    var strUrl="LLClaimPolDealMain.jsp?ClmNo="+fm.ClmNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��ѯ���ս��2
function queryResult2()
{
    /*var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql42");
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
        var tContNo   = parseFloat(PolDutyCodeGrid.getRowColData(i,1));//������
        var tRiskCode = PolDutyCodeGrid.getRowColData(i,3);//���ֱ���
        var tDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,28));//���α���
        var tGetDutyCode = PolDutyCodeGrid.getRowColData(i,4);//���α���
        var tRealM    = parseFloat(PolDutyCodeGrid.getRowColData(i,21));//�������
        var tAmnt     = parseFloat(PolDutyCodeGrid.getRowColData(i,9));//����
        var tAdjM     = parseFloat(fm.RealPay.value);
        
        var tPolNo   = PolDutyCodeGrid.getRowColData(i,1);//���ֱ�����
       
        var strPSQL = "select riskamnt from lcpol where polno='"+tPolNo+"'";
       /* mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql43");
			mySql.addSubPara(tPolNo);   */
        var arrp= easyExecSql(strPSQL);
        
        if(arrp[0][0]== null){
        	alert("���ձ������");
        }
        var strSQL = "select 1 from lmrisksort where riskcode='"+tRiskCode+"' and risksorttype='26' and risksortvalue='"+tGetDutyCode+"'";
        //prompt("У����������Ƿ��ǽ�����:",strSQL);
        /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql44");
			mySql.addSubPara(tRiskCode);   
				mySql.addSubPara(tGetDutyCode);  */
        var arr= easyExecSql(strSQL);
        
       var strSQL1 = "select InsuAccFlag from lmrisk where riskcode='"+tRiskCode+"'";
		/*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql45");
			mySql.addSubPara(tRiskCode);    */
        var arr1= easyExecSql(strSQL1);

        
        if(fm.GiveType.value!='1')
        {
        //�ſ��Խ��������ֵ�����
        if(arr == null){
        	//�Է��ʻ��������÷��ձ������У��
        	if (arr1[0][0] != 'Y')
        	{

        		if (arrp[0][0] < tAdjM)
        			{
        				alert("�������ܴ��ڷ��ձ���!");
        				fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
        				return false;
        			} 
        	}
            else if(tAdjM < 0){
                alert("��������С����!");
                fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
                return false;
            }
        }
        else
        {
        	return true;
        }
        }
        else
        {
        	return true;
        }

    }
}

//��ӡ�ⰸ��˱���----2005-08-12���
function LLPRTClaimAuditiReport()
{
//  alert("��ӡ�ⰸ��˱���");
  fm.action="LLPRTClaimAuditiReportSave.jsp";
  fm.target = "../f1print";
  document.getElementById("fm").submit();
}


//���ɱ���֪ͨ��----2005-08-16���
function LLPRTPatchFeePrint()
{
   /* var strSql = " select count(1) from loprtmanager where 1=1 "
                + " and otherno='" + fm.ClmNo.value + "'"
                + " and t.code='PCT008'";*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql46");
			mySql.addSubPara( fm.ClmNo.value );  
    var tCount = easyExecSql(mySql.getString());
    
    if (tCount == 0)
    {
        alert("û�пɴ�ӡ������!");
        return;
    }
    fm.action="LLPRTPatchFeeSave.jsp";
//  fm.target = "../f1print";
//  document.getElementById("fm").submit();
    if(beforePrtCheck(fm.ClmNo.value,"PCT008")=="false")
    {
      return;
    } 

}

//��������������--��ѯ 2005-08-16���
function LLQueryUWMDetailClick()
{
//  alert("��������������ѯ");
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

/**********************************
//��ӡǰ���飨������Ҫ�����������֤���롢�ⰸ�ţ�--------2005-08-22���
***********************************/
function beforePrtCheck(clmno,code)
{
  //��ѯ��֤��ˮ�ţ���Ӧ�������루�ⰸ�ţ����������͡���ӡ��ʽ����ӡ״̬�������־
     var tclmno=trim(clmno);
     var tcode =trim(code);
     if(tclmno=="" ||tcode=="")
     {
       alert("������ⰸ�Ż�֤���ͣ����룩Ϊ��");
       return false;
     }
  /*  var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
      +" and t.otherno='"+tclmno+"' "
      +" and t.code='"+tcode+"' ";*/
  mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql47");
			mySql.addSubPara( tclmno);  
			mySql.addSubPara( tcode);  
  var arrLoprt = easyExecSql(mySql.getString());
  if(arrLoprt==null)
  {
    alert("û���ҵ��õ�֤����ˮ��");
    return false;
  }  
  else 
  {
    var tprtseq=arrLoprt[0][0];//��֤��ˮ��
    var totherno=arrLoprt[0][1];//��Ӧ�������루�ⰸ�ţ�
    var tcode=arrLoprt[0][2];//��������
    var tprttype=arrLoprt[0][3];//��ӡ��ʽ
    var tstateflag=arrLoprt[0][4];//��ӡ״̬
    var tpatchflag=arrLoprt[0][5];//�����־
    fm.PrtSeq.value=arrLoprt[0][0];
    //���ڵ�δ��ӡ����ֱ�ӽ����ӡҳ��
     if(tstateflag=="0")
     {
//      fm.action = './LLPRTCertificatePutOutSave.jsp';   //
      fm.target = "../f1print";
      document.getElementById("fm").submit();
     }
    else
    {
      //���ڵ��Ѿ���ӡ����tstateflag[��ӡ״̬��1 -- ��ɡ�2 -- ��ӡ�ĵ����Ѿ��ظ���3 -- ��ʾ�Ѿ����߰�֪ͨ��]
      if(confirm("�õ�֤�Ѿ���ӡ��ɣ���ȷʵҪ������"))
       {
         //���벹��ԭ��¼��ҳ��
         var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value;
         window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
       }
    }
  } 
}

//��Ȩ���ϱ��ύ����
function ConclusionUpClick(){
    fm.AuditConclusion.value = 6 ;//��Ȩ���ϱ�
    ConclusionSaveClick();//ֱ�ӱ�����˽���
}

//�ϱ������������ύ����
function ConclusionSaveClick2(){
//    fm.AuditConclusion.value = 4 ;//��������
//    ConclusionSaveClick();//�ȱ�����˽���
//if(fm.AuditConclusion.value == 4){
     //��ѯ�Ƿ���й�ƥ����� 2009-08-04 9:08
  var Detailsql = "select count(1) from LLClaimDetail where"
           + " ClmNo = '" + fm.RptNo.value + "'";
   /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql48");
			mySql.addSubPara( fm.RptNo.value);  */ 
  var tDetailDutyKind = easyExecSql(Detailsql);
  if (tDetailDutyKind == 0)
  {
      alert("���Ƚ���ƥ�䲢����!");
      return;
  }
		
	//�ж���˽���
    var strSql = "select AuditConclusion from LLClaimUWMain where "
               + "clmno = '" + fm.RptNo.value + "'";
     /* mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql49");
			mySql.addSubPara( fm.RptNo.value);    */       
    var tGivetype = easyExecSql(strSql);
    if (tGivetype == null)
    {
        alert("���ȱ�����˽��ۣ�");
        return;
    }
    else
    {
        if (tGivetype == "1")
        {
            var tGtype = new Array;
            var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";
             /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql50");
			mySql.addSubPara( fm.RptNo.value); */ 
            tGtype = easyExecSql(strSql);
            if (tGtype != null)
            {
                //alert(tGtype.length);
                for (var i=0; i<tGtype.length; i++)
                {
//                    alert(tGtype[i])
                    if (tGtype[i] != "1")
                    {
                        alert("���ȫ��Ϊ�ܸ�,������ȫ���ܸ�����!");
                        return;
                    }
                }
            }
        }
        else if (tGtype == "0")
        {
            var tGtype = new Array;
            var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";
           /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql51");
			mySql.addSubPara( fm.RptNo.value);  */
            tGtype = easyExecSql(strSql);
            if (tGtype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGtype.length; i++)
                {
//                    alert(tGtype[i])
                    if (tGtype[i] == "0"||tGtype == "4")
                    {
                        tYesNo = 1;
                    }
                }
                if (tYesNo == 0)
                {
                    alert("����ȫ��Ϊ�ܸ�,�����¸�������!");
                    return;
                }
            }
        }
    }
    
    //����Ǹ�����ܸ�����,�ύǰ����
    if (tGivetype == "0" || tGivetype == "1"||tGivetype == "4")
    {
        //���顢�ʱ���������Ͷ���У��,�������㡢��ͬ����
        if (!checkPre())
        {
            return;
        } 
        
        //ֻ�л���ʱ����Ҫ�������˷���
        var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 + getWherePart( 'ClmNo','ClmNo' );
         /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql52");
			mySql.addSubPara( fm.ClmNo.value);  */
        var tDutyKind = easyExecSql(tSQL);
        if (tDutyKind != '09')
        {
//            //����¸�������,У�������˷���
//            if (tGivetype == "0")
//            {
                var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
                            + getWherePart( 'ClmNo','ClmNo' );
                /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql53");
			mySql.addSubPara( fm.ClmNo.value);  */
                var tBNF = easyExecSql(strSql4);
                
                if (tBNF)
                {
                    for (var i = 0; i < tBNF.length; i++)
                    {
                        if (tBNF[i] == '0')
                        {
                            alert("�����˷��仹û�����!");
                            return;
                        }
                    }
                }
//            }
        }
    }

		/**
		 * 2009-02-10 zhangzheng  
		 * MS����Ȩ�޹���δ��,��ʱ��ס���У��
		 */
//		var tManageCom = fm.ManageCom.value.substring(0,2);
//		var strSql0501 = " select checklevel,username,usercode from llclaimuser where comcode like '"+tManageCom+"%%' and StateFlag = '1' and checklevel = (select max(checklevel) from llclaimuser a , Lduser b where a.comcode like '"+tManageCom+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0') order by comcode" ;
//		var tSubReport2 = new Array;
//		    tSubReport2 = easyExecSql(strSql0501);
//		if(tSubReport2 != null)
//		{
//		    var tChecklevel = tSubReport2[0][0];
//		    var tUserName   = tSubReport2[0][1];
//		    var tUserCode   = tSubReport2[0][2];
//		}else{
//		    alert("δ��ѯ���÷ֻ�������߼���������Ա!");
//		    return false;
//		} 
//		if(tUserCode != fm.tOperator.value)
//		{
//        fm.ConclusionSave.disabled = true;
//        fm.ConclusionUp.disabled = false;
//        fm.ConclusionBack.disabled = true;                    
//        fm.conclusionSave1.disabled=false;  		    
//		    alert("�뱾�ֹ�˾������⼶��, "+tUserName+" ��¼�Ѱ����ϱ������Ƚ��г�Ȩ���ϱ���");
//		    return false;
//		}		
    
  var strSql5 = "select RealPay from LLClaim where "
        + "clmno = '" + fm.RptNo.value + "'";
  /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql54");
			mySql.addSubPara( fm.RptNo.value);  */
  var tRealPay = easyExecSql(strSql5);
  if (tRealPay == null)
   {
     alert("�ⰸ��Ϣȱʧ��LLClaim���⸶��");
     return;
   }
  else
  {
	  fm.adjpay.value=tRealPay;
  }

    AuditConfirmClick();//�ٽ��й���������
//}else{
    //alert("���ȱ�����˽���!");
    //return false;
//}

}



//---------------------------------------------------------------------------------------*
//���ܣ����������߼��ж�
//����1 �������߲С�ҽ������ֻ��ѡһ
//    2 ѡ��������߲�ʱ��Ĭ��ѡ�л���
//---------------------------------------------------------------------------------------*
function callClaimType(ttype)
{
  switch (ttype)
  {
      case "02" : //����
          if (fm.claimType[0].checked == true)
          {
              fm.claimType[4].checked = true;
          }
//          if ((fm.claimType[1].checked == true || fm.claimType[5].checked == true) && fm.claimType[0].checked == true)
//          {
//              alert("�������߲С�ҽ������ֻ��ѡһ!");
//              fm.claimType[0].checked = false;
//              return;
//          }
//          fm.claimType[4].checked = true;
      case "03" :
          if (fm.claimType[1].checked == true)
          {
              fm.claimType[4].checked = true;
          }
//          if ((fm.claimType[0].checked == true || fm.claimType[5].checked == true)&& fm.claimType[1].checked == true)
//          {
//              alert("�������߲С�ҽ������ֻ��ѡһ!");
//              fm.claimType[1].checked = false;
//              return;
//          }
//          fm.claimType[4].checked = true;
      case "09" :
//          if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[4].checked == false)
//          {
//              alert("ѡȡ�������߲б���ѡ�����!");
//              fm.claimType[4].checked = true;
//              return;
//          }
      case "00" :
//          if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[5].checked == true)
//          {
//              alert("�������߲С�ҽ������ֻ��ѡһ!");
//              fm.claimType[5].checked = false;
//              return;
//          }
      default :
          return;
  }
}



//���б�������ƥ��
function showMatchDutyPay2(tAccNo)
{  
	fm.AccNo.value=tAccNo;

	fm.hideOperate.value="MATCH||INSERT";

	fm.action = "./LLClaimRegisterMatchCalSave2.jsp";
	fm.target="fraSubmit";
	document.getElementById("fm").submit(); //�ύ
}

//���ƥ�䲢���㰴ť��Ķ���
function afterMatchDutyPay2(FlagStr, content)
{
	//����������ʾ,����ִ�н��۱���
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
 

    }
    else
    {
    	afterMatchDutyPayQuery();
    }
}


//2009-04-22 zhangzheng ˫������������Ӧ����
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    
	//��˽���
    if(cCodeName=="llexamconclusion2"){
    	
    	//������ԭ��Ϊ�ܸ�ʱ,��վܸ�ԭ��;ܸ�����
    	if(fm.AuditConclusion.value !='1'){
    		
       		fm.ProtestReason.value='';
    		fm.ProtestReasonName.value='';
    		fm.ProtestReasonDesc.value='';	
    		fm.SpecialRemark1.value='';
    	}
    	else
    	{
    		//������ⱸע
    		fm.SpecialRemark1.value='';
    	}
        return true;
    }
}

//zy 2009-07-28 17:43 ��ȡ������Ʒ������������Ϣ
function getLLEdorTypeCA()
{

	// var tAccRiskCodeSql="select distinct riskcode,grpcontno from lcgrppol where grpcontno in (select grpcontno from llclaimpolicy where clmno='"+fm.RptNo.value+"') and riskcode='211901' and grpname like 'MS���ٱ��չɷ����޹�˾%'";
	 //prompt("",tAccRiskCodeSql);
  mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql55");
			mySql.addSubPara( fm.RptNo.value);  
   var arr=easyExecSql(mySql.getString());
   if (arr)
   {
   	var tAccRiskCode = arr[0][0];
   	var GrpContNo= arr[0][1];
   	 if(tAccRiskCode=="211901")
	   {
	   	fm.AccRiskCode.value=tAccRiskCode;
	   }
	   else
	   {
	   	fm.AccRiskCode.value="";   
	   }
	   if(GrpContNo==null || GrpContNo=="")
	   {
	   	alert("û����Ӧ���ŵ��ţ����ʵ");
	   	return ;
	   	}
	   	else
	   		fm.GrpContNo.value=GrpContNo;

    }


}

//zy 2009-07-28 14:58 �˻��ʽ����
function ctrllcinsureacc()
{
	
    var strUrl="./LLGrpClaimEdorTypeCAMain.jsp?GrpContNo="+fm.GrpContNo.value+"&RptNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}