var showInfo;
var turnPage = new turnPageClass();

//��֤����
function showRgtMAffixList()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
    var strUrl="LLRgtMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=Rgt";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    //window.open(strUrl,"��֤����");
}

//������֤����
function showRgtAddMAffixList()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
    var strUrl="LLRgtAddMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=RgtAdd";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    //window.open(strUrl,"��֤����");
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�������������۱���󣬽���ƥ�䣬���㣬ȷ�ϵȺ���������
    �� �� �ˣ�����
    �޸����ڣ�2005.06.01
   =========================================================================
**/
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
    fm.target="fraSubmit";
    fm.submit(); //�ύ

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
    var arr = new Array;

    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���

    //��ѯLLClaim�������ⰸ�Ľ��
    tSql = " select a.realpay,b.BeAdjSum "
         + " from LLClaim a,LLRegister b  where 1=1 "
         + " and a.ClmNo = b.RgtNo and a.ClmNo = '"+tClaimNo+"'"

    arr = easyExecSql( tSql );
    if (arr)
    {
        arr[0][0]==null||arr[0][0]=='null'?'0':fm.standpay.value  = arr[0][0];
        arr[0][1]==null||arr[0][1]=='null'?'0':fm.adjpay.value  = arr[0][1];
        //fm.adjpay.value = fm.standpay.value;
        if (fm.adjpay.value == "0")
        {
            fm.adjpay.value = fm.standpay.value;
        }
    }

    //��ѯ�����ⰸ�Ľ��
    tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;
    arr = easyExecSql( tSql );
    if (arr)
    {
        displayMultiline(arr,ClaimGrid);
    }
    else
    {
        initClaimGrid();
    }

    //��ѯLLClaimDutyKind�������ⰸ�������ͽ��в���
    tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and trim(c.code)=trim(a.GetDutyKind)),"
       +" a.TabFeeMoney,a.ClaimMoney,a.StandPay,a.SocPay,a.OthPay,RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"


    arr = easyExecSql( tSql );
    if (arr)
    {
        displayMultiline(arr,DutyKindGrid);
    }
    else
    {
        initDutyKindGrid();
    }


    //��ѯLLClaimPolicy,��ѯ�����������Ͳ������Ϣ
    tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"
       +" and a.ClmNo = '"+tClaimNo+"'"

    arr = easyExecSql( tSql );
    if (arr)
    {
        displayMultiline(arr,PolDutyKindGrid);
    }
    else
    {
        initPolDutyKindGrid();
    }


    //��ѯLLClaimDetail,��ѯ�����������ͱ���������Ϣ
    ////to_char(b.PayEndDate,'yyyy-mm-dd')+a.GracePeriod," //�������� + ������--+ a.GracePeriod
    tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetClm c where trim(c.GetDutyKind) = trim(a.GetDutyKind) and trim(c.GetDutyCode) = trim(a.GetDutyCode)),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0),"
       +" a.Amnt,a.YearBonus,a.EndBonus,"
       +" a.RealPay,a.GiveType, "
       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion' and trim(e.code)=trim(a.GiveType)),"
       +" case a.PolSort when 'A' then '�б�ǰ' when 'B' then '����' when 'C' then '����' end ,"
       +" a.DutyCode "
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"
       +" and a.DutyCode = b.DutyCode"
       +" and a.GetDutyCode = b.GetDutyCode"
       +" and a.ClmNo = '"+tClaimNo+"'"

    arr = easyExecSql( tSql );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
    else
    {
        initPolDutyCodeGrid();
    }


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


//����ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeCal()
{
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���

    var strUrl="LLClaimRegMedFeeCalMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}


/**=========================================================================
    �޸�״̬������
    �޸�ԭ�������������۱���󣬽���ƥ�䣬���㣬ȷ�ϵȺ���������
    �� �� �ˣ�����
    �޸����ڣ�2005.06.01
   =========================================================================
**/

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
  var tCustomerNo = fm.customerNo.value;
  if (tCustomerNo == "")
  {
      alert("��ѡ������ˣ�");
      return;
  }
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�鿴����
function showQueryInq()
{
  var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    //---------------------------------------
    //�жϸ��ⰸ�Ƿ���ڵ���
    //---------------------------------------
    var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";
    var tCount = easyExecSql(strSQL);
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

//�򿪳ʱ���ѯ
function showQuerySubmit()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
  //---------------------------------------
  //�жϸ��ⰸ�Ƿ���ڳʱ�
  //---------------------------------------
    var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";
    var tCount = easyExecSql(strSQL);
//    alert(tCount);
    if (tCount == "0")
    {
        alert("���ⰸ��û�гʱ���Ϣ��");
        return;
    }
    var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
//    window.open(strUrl,"�ʱ���ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    window.open(strUrl,"");
}

//���¹�������Ϣ
function showClaimDesc()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    var strUrl="LLClaimDescMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&startPhase=02";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�¹�������Ϣ");
}

//������ѯ
function showLLReportQuery()
{
//    window.open("LLReportQueryInput.jsp","������ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    window.open("LLReportQueryInput.jsp","");
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
    var strUrl="LLLcContSuspendMain.jsp?InsuredNo="+tCustomerNo+"&ClmNo="+fm.ClmNo.value;//�����˿ͻ���==�����˿ͻ���
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

//���¼���ѯ����
function afterQueryLL2(tAccNo,tOccurReason,tAccDesc,tAccidentSite)
{
  //�õ�����ֵ
    fm.AccNo.value = tAccNo;
    fm.occurReason.value = tOccurReason;
    fm.AccDesc.value = tAccDesc;
    fm.AccDescDisabled.value = tAccDesc;
    fm.AccidentSite.value = tAccidentSite;
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
}

//ѡ��SubReportGrid��Ӧ�¼�,tPhase=0Ϊ��ʼ��ʱ����
function SubReportGridClick(tPhase)
{
  //--------------------------------------------Beg
  //�ÿ���ر�
  //--------------------------------------------
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
    //--------------------------------------------End

  //--------------------------------------------Beg
  //��ʾ��������
  //--------------------------------------------
  spanText1.style.display = "none";
  spanText2.style.display = "";
    spanText3.style.display = "none";
  spanText4.style.display = "";
  //--------------------------------------------End

    //ȡ������
    var i = "";
    if (tPhase == 0)
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != 0)
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = getAge(SubReportGrid.getRowColData(i,4));
        fm.IsVip.value = SubReportGrid.getRowColData(i,5);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
    var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";
//    alert(tClaimType);
    tClaimType = easyExecSql(strSQL1);
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

                    //���Ϊ�˲У���ʾ�˲м���֪ͨ2005-8-13 11:04
                    if (tClaim == '01')
                    {
                        fm.MedCertForPrt.disabled = false;
                    }
                }
            }
        }
    }
    //��ѯ�ְ�����Ϣ
    var tSubReport = new Array;
    var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccDesc,AccResult1,AccResult2 from LLSubReport where 1=1 "
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );
//    alert(strSQL2);
    tSubReport = easyExecSql(strSQL2);
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.RemarkDisabled.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccDescDisabled.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult2();
    afterMatchDutyPayQuery();
}

//��ѯ�����ְ���Ϣ,tPhase=0Ϊ��ʼ��ʱ����
function SubReportGridClick2(tPhase)
{
  //------------------------------------------**Beg
  //�ÿ���ر�
  //------------------------------------------**
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
    //------------------------------------------**End

    //--------------------------------------------Beg
  //��ʾ��������
  //--------------------------------------------
  spanText1.style.display = "none";
  spanText2.style.display = "";
    spanText3.style.display = "none";
  spanText4.style.display = "";
  //--------------------------------------------End

    //ȡ������
    var i = "";
    if (tPhase == 0)
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != 0)
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = getAge(SubReportGrid.getRowColData(i,4));
        fm.IsVip.value = SubReportGrid.getRowColData(i,5);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
    var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";
//    alert(tClaimType);
    tClaimType = easyExecSql(strSQL1);
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

                    //���Ϊ�˲У���ʾ�˲м���֪ͨ2005-8-13 11:04
                    if (tClaim == '01')
                    {
                        fm.MedCertForPrt.disabled = false;
                    }
                }
            }
        }
    }
    //��ѯ�ְ�����Ϣ
    var tSubReport = new Array;
    //-------------------------1----------2-----------3---------4--------5-------6--------7-----------8-----------9------------10-------------------------------------------11
    var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,AffixConclusion,(case AffixConclusion when '0' then '��' when '1' then '��' end) from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );
//    alert(strSQL2);
    tSubReport = easyExecSql(strSQL2);
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.RemarkDisabled.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccDescDisabled.value = tSubReport[0][6];
//        alert(fm.AccDescDisabled.value);
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.IsAllReady.value = tSubReport[0][9];
    fm.IsAllReadyName.value = tSubReport[0][10];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult2();
//    showOneCodeName('IsAllReady','IsAllReady','IsAllReadyName');//��֤��ȫ��־
}

//ѡ��SubReportGrid��Ӧ�¼�,tPhase=1Ϊѡ����ʱ����
function allSubReportGridClick(tPhase)
{
    if(fm.isRegisterExist.value == "true")
    {
        SubReportGridClick2(tPhase);
    }
    else
    {
        SubReportGridClick(tPhase);
    }
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

//
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


   // showSubmitFrame(mDebug);
    fm.target="fraSubmit";
    fm.submit(); //�ύ
    tSaveFlag ="0";

}

//�ύǰ��У�顢����
function beforeSubmit()
{
    //��ȡ������Ϣ
    var RptNo = fm.RptNo.value;//�ⰸ��
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccDate = fm.AccidentDate.value;//�¹�����
    var AccReason = fm.occurReason.value;//����ԭ��
    var AccDate2 = fm.AccidentDate2.value;//��������
    var AccDesc = fm.accidentDetail.value;//����ϸ��
    var ClaimType = new Array;
    //��������
    for(var j=0;j<fm.claimType.length;j++)
    {
        if(fm.claimType[j].checked == true)
        {
            ClaimType[j] = fm.claimType[j].value;
        }
    }
//    alert("ClaimType = "+ClaimType);
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
    if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("������������������");
        return false;
    }
    if (fm.Relation.value == "" || fm.Relation.value == null)
    {
        alert("���������������¹��˹�ϵ��");
        return false;
    }
//    if (fm.AssigneeName.value == "" || fm.AssigneeName.value == null)
//    {
//        alert("������������������");
//        return false;
//    }
//    if (fm.AssigneePhone.value == "" || fm.AssigneePhone.value == null)
//    {
//        alert("�����������˵绰��");
//        return false;
//    }
//    if (fm.AssigneeAddr.value == "" || fm.AssigneeAddr.value == null)
//    {
//        alert("�����������˵�ַ��");
//        return false;
//    }
//    if (fm.AssigneeZip.value == "" || fm.AssigneeZip.value == null)
//    {
//        alert("�������������ʱ࣡");
//        return false;
//    }
//    else
//    {
    var tAssigneeZip = fm.AssigneeZip.value;
    if (tAssigneeZip.length > 6)
    {
        alert("�ʱ����");
        return false;
    }
//    }
    //----------------------------------------------------------end
    //1 �������ˡ���Ϣ
    if (checkInput1() == false)
    {
        return false;
    }
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
    //4 ������ϸ��
    if ((AccDesc == null || AccDesc == '') && fm.occurReason.value == '1')
    {
        alert("����ԭ��Ϊ����,����ϸ�ڲ���Ϊ�գ�");
        return false;
    }
    //---------------------------------------------------------------------------------**Beg*2005-7-12 16:27
    //��ӳ���ԭ��Ϊ����ʱ���¹����ڵ��ڳ�������
    //---------------------------------------------------------------------------------**
    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.AccidentDate2.value))
    {
        alert("����ԭ��Ϊ����ʱ���¹����ڱ�����ڳ������ڣ�");
        return false;
    }
    //---------------------------------------------------------------------------------**End

    //5 �����������
    if (ClaimType == null || ClaimType == '')
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }
    //---------------------------------------------*Beg*2005-6-13 20:26
    //�������������ѡ��"ҽ��"����ѡ��ҽԺ���ж�
    //---------------------------------------------*
    /*
    if(fm.claimType[5].checked == true && (fm.hospital.value == "" || fm.hospital.value == null))
    {
        alert("��ѡ��ҽԺ��");
        return false;
    }
    */
    //---------------------------------------------*End
    //---------------------------------------------------------Beg*2005-6-27 11:55
    //�������������ѡ�л���,����ѡ��������߲�֮һ���ж�
    //---------------------------------------------------------
    if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false)
    {
        alert("ѡ�л���,����ѡ��������߲�֮һ!");
        return false;
    }
    //---------------------------------------------------------End
    return true;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
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


        fm.isReportExist.value = "true";
        fm.isRegisterExist.value = "true";

        notDisabledButton();
        fm.RgtConclusionName.value = "";
        queryRegister();
        //���ÿɲ�����ť
        if(fm.isNew.value == '0')
        {
            operateButton21.style.display="";
            operateButton22.style.display="none";
            fm.QueryPerson.disabled = false;
            fm.QueryReport.disabled = false;
        }
        if(fm.isNew.value == '1')
        {
            operateButton21.style.display="none";
            operateButton22.style.display="";
            fm.QueryPerson.disabled = true;
            fm.QueryReport.disabled = true;
        }
    }
    tSaveFlag ="0";
}

//����ȷ�Ϸ��غ�ִ�еĲ���
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

        goToBack();
    }
    tSaveFlag ="0";
}

//����ȷ��
function RegisterConfirm()
{
    var yesORno = 0;
    if (fm.RptNo.value == "")
    {
         alert("�ⰸ��Ϊ�գ�");
         return;
    }
    //-------------------------------------------------------------------BEG2005-8-9 8:58
    //����������ۡ�ƥ����㡢��֤��ȫ
    //-------------------------------------------------------------------
    var sql1 = " select RgtConclusion from llregister where "
            + " RgtNo = '" + fm.RptNo.value + "'"
    var tRgtConclusion = easyExecSql(sql1);
    if (tRgtConclusion == null || tRgtConclusion == "")
    {
        alert("���ȱ�����������!");
        return;
    }
    else
    {
        if (tRgtConclusion == "01")
        {
            //��ѯ�Ƿ���й�ƥ�����
            var sql2 = "select count(1) from LLClaimDetail where"
                     + " ClmNo = '" + fm.RptNo.value + "'";
            var tDutyKind = easyExecSql(sql2);
            if (tDutyKind == 0)
            {
                alert("���Ƚ���ƥ�䲢����!");
                return;
            }
            //��鵥֤��ȫ��־
            if (fm.IsAllReady.value != "1")
            {
//                if (confirm("��֤����ȫ,�Ƿ�����!"))
//                {
//                    yesORno = 1;
//                }
//                else
//                {
//                    yesORno = 0;
//                    return;
//                }
                alert("��֤����ȫ,��������ͨ��!");
                return;
            }
            else
            {
                var tResult = new Array;
                var sql = " select affixconclusion from llcase where "
                         + " caseno = '" + fm.RptNo.value + "'"
                tResult = easyExecSql(sql);
                if (tResult != null)
                {
                    for (var i=0; i < tResult.length; i++)
                    {
                        if (tResult[0][i] == "0")
                        {
//                            if (confirm("��֤����ȫ,�Ƿ�����!"))
//                            {
//                                yesORno = 1;
//                            }
//                            else
//                            {
//                                yesORno = 0;
//                                return;
//                            }
                            alert("��֤����ȫ,��������ͨ��!");
                            return;
                        }
                        else
                        {
                            yesORno = 1;
                        }
                    }
                }
            }
        }
        else
        {
            yesORno = 1;
        }
    }
    //-------------------------------------------------------------------END

    //-------------------------------------------------------------------BEG
    //��鱣����ۣ�ȫ������ʱ��������
    //-------------------------------------------------------------------
    if (tRgtConclusion == '01')
    {
        var tGivetype = new Array;
        var strSql = "select givetype from LLClaimDetail where "
                   + "clmno = '" + fm.RptNo.value + "'";
        tGivetype = easyExecSql(strSql);
        var tYes = 0;
        if (tGivetype != null)
        {
            //alert(tGivetype.length);
            for (var i=0; i<tGivetype.length; i++)
            {
                if (tGivetype[i] == "0")
                {
                    tYes = 1;
                }
            }
        }
        if (tYes == 0)
        {
            alert("����ȫ��Ϊ����ʱ��������ͨ��!");
            return;
        }
    }
    //-------------------------------------------------------------------END

    //�Ƿ��ύ
    if (yesORno == 1)
    {
        fm.RgtConclusion.value = tRgtConclusion;
        fm.action = './LLClaimRegisterConfirmSave.jsp';
        submitForm();
    }
}

//[����]��ť��Ӧ����
function saveClick()
{
    //���Ƚ��зǿ��ֶμ���
    if (beforeSubmit())
    {
        if (fm.RgtClass.value == "" || fm.RgtClass.value == null)
        {
            fm.RgtClass.value = "1";
        }

        //�ж������������������������Ǳ��������
        if (fm.isReportExist.value == "false")
        {
            fm.fmtransact.value = "insert||first";
        }
        else
        {
            fm.fmtransact.value = "insert||customer";
        }
        fm.action = './LLClaimRegisterSave.jsp';
        submitForm();
    }
}

//[����]��ť��Ӧ����
function saveConclusionClick()
{
	alert('11');
    //���Ƚ��зǿ��ֶμ���
    if (fm.RgtConclusion.value == '02' && (fm.NoRgtReason.value == '' || fm.NoRgtReason.value == null))
    {
        alert("����д��������ԭ��!");
        return;
    }
    if (fm.RgtConclusion.value == '01')
    {
        //��ѯ�Ƿ���й�ƥ�����
        var sql2 = "select count(1) from LLClaimDetail where"
                 + " ClmNo = '" + fm.RptNo.value + "'";
        var tDutyKind = easyExecSql(sql2);
        if (tDutyKind == 0)
        {
            alert("���Ƚ���ƥ�䲢����!");
            return;
        }
    }
    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimRegisterConclusionSave.jsp';
    submitForm();
}

//��������Ϣ�޸�
function updateClick()
{
    if (confirm("��ȷʵ���޸ĸü�¼��"))
    {
        if (beforeSubmit())
        {
            fm.fmtransact.value = "update";
            fm.action = './LLClaimRegisterSave.jsp';
            submitForm();
        }
    }
}

//�����˲�ѯ
function ClientQuery()
{
    window.open("LLLdPersonQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryInput.jsp");

}

//��������
function addClick()
{
    resetForm();

}


//����ԭ���ж�
function checkOccurReason()
{
    if (fm.occurReason.value == '1')
    {
        fm.accidentDetail.disabled = false;
    }
    else if (fm.occurReason.value == '2')
    {
        fm.accidentDetail.disabled = true;
    }
}

//������ѯ
function IsReportExist()
{
    //�������ˡ���Ϣ
    if (!checkInput1())
    {
        return;
    }
    queryReport();

}

//�������ˡ���Ϣ
function checkInput1()
{
    //��ȡ������Ϣ
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccDate = fm.AccidentDate.value;//�¹�����
    //ȡ����������Ϣ
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);
    //����������Ϣ
    if (CustomerNo == null || CustomerNo == '')
    {
        alert("����ѡ������ˣ�");
        return false;
    }
    //����¹�����
    if (AccDate == null || AccDate == '')
    {
        alert("�������¹����ڣ�");
        return false;
    }
    else
    {
        if (AccYear > mNowYear)
        {
            alert("�¹����ڲ������ڵ�ǰ����!");
            return false;
        }
        else if (AccYear == mNowYear)
        {
            if (AccMonth > mNowMonth)
            {

                alert("�¹����ڲ������ڵ�ǰ����!");
                return false;
            }
            else if (AccMonth == mNowMonth && AccDay > mNowDay)
            {
                alert("�¹����ڲ������ڵ�ǰ����!");
                return false;
            }
        }
    }
    return true;
}

//������ѯ
function queryReport()
{
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    //�����¼���(һ��)
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(strSQL1);
//    alert("AccNo= "+AccNo);
    //���������ż�����������Ϣ(һ��)
    var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator,RgtClass from LLReport where "
                + "RptNo = '"+rptNo+"'";
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(strSQL2);
//    alert("RptContent= "+RptContent);
    //����ҳ������
if(AccNo!=null){
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
}
if(RptContent!=null){
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
//    fm.RptMode.value = RptContent[0][5];
    fm.AccidentSite.value = RptContent[0][6];
    fm.occurReason.value = RptContent[0][7];
    fm.RptDate.value = RptContent[0][8];
    fm.MngCom.value = RptContent[0][9];
    fm.Operator.value = RptContent[0][10];
}
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
//    showOneCodeName('RptMode','RptMode','RptModeName');//������ʽ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
//    showOneCodeName('lloccurreason','IsDead','IsDeadName');//������־

    //---------------------------------------------------*
    //����ҳ��Ȩ��
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;

    fm.QueryPerson.disabled=true;
    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;

    //���ð�ť++++++++++++++++++++++++++++++++++++++++�����

    //�����ְ�������������Ϣ(����)
    var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";
//    alert("strSQL3= "+strSQL3);
    easyQueryVer3Modal(strSQL3, SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
}

//�����˲�ѯ,������Ϊ��������ʱ������Ϊ������
function queryProposer()
{
    var strSQL = "select count(1) from LLReportReason a where "
                + "a.rpno = '" + fm.RptNo.value + "'"
                + "and substr(a.reasoncode,2,2) = '02'";
    var tCount = easyExecSql(strSQL);
    //û��������������
    if (tCount == "0")
    {
        var strSQL2 = "select a.phone,a.postaladdress from LCAddress a where "
                    + "a.customerno = '" + fm.customerNo.value + "'";
        var tLCAddress = easyExecSql(strSQL2);
        fm.RptorName.value = fm.customerName.value;
        fm.RptorPhone.value = tLCAddress[0][0];
        fm.RptorAddress.value = tLCAddress[0][1];
        fm.Relation.value = "GX01";
        showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
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
    //�����¼��š��¹�����(һ��)
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(strSQL1);
//    alert("AccNo= "+AccNo);
    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18------------19-------20------------------------------------21
    var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end) from llregister where "
                + "rgtno = '"+rptNo+"'";
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(strSQL2);
//    alert("RptContent= "+RptContent);
    //����ҳ������
if(AccNo!=null){
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
}
if(RptContent!=null){
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
    fm.NoRgtReason.value = RptContent[0][18];
    fm.RgtClass.value = RptContent[0][19];
    fm.RgtClassName.value = RptContent[0][20];
}
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������
    showOneCodeName('llnorgtreason','NoRgtReason','NoRgtReasonName');//������ԭ��
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//�������Ա�
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//����������

    //---------------------------------------------------*
    //����ҳ��Ȩ��
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;

    fm.QueryPerson.disabled=true;
    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;

    //���ð�ť
    if (fm.RgtConclusion.value == "" || fm.RgtConclusion.value == null)
    {
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printPassRgt.disabled = true;
        fm.printNoRgt.disabled = true;
        fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = false;
    }
    else
    {
        fm.MedicalFeeInp.disabled = true;
    }

    //�����ְ�������������Ϣ(����)
    var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from llcase where CaseNo = '"+ rptNo +"')";
    easyQueryVer3Modal(strSQL3, SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
    //��������������ʾ���ز�
    showDIV();
    //�������������ж��Ƿ��ѯƥ��������Ϣ
    if (fm.RgtConclusion.value == '01')
  {
      afterMatchDutyPayQuery();
        var strSQL4 = "select RgtState from llregister where "
                    + "rgtno = '"+rptNo+"'";
        var tRgtState = easyExecSql(strSQL4);
        if (tRgtState)
        {
            fm.rgtType.value = tRgtState;
            showOneCodeName('rgtType','rgtType','rgtTypeTypeName');
        }
  }
}

//��ѯҵ��Ա
function queryAgent()
{
    if(fm.AssigneeCode.value != "")
    {
        var strSQL = "select t.name,t.sex,t.phone,t.homeaddress,t.zipcode from laagent t where "
                    + "t.agentcode = '"+fm.AssigneeCode.value+"'";
        var tAgent = easyExecSql(strSQL);
        if (tAgent)
        {
            fm.AssigneeName.value = tAgent[0][0];
            fm.AssigneeSex.value = tAgent[0][1];
            fm.AssigneePhone.value = tAgent[0][2];
            fm.AssigneeAddr.value = tAgent[0][3];
            fm.AssigneeZip.value = tAgent[0][4];
            showOneCodeName('sex','AssigneeSex','AssigneeSexName');
        }
    }
}

//���ý����ϵ����а�ťΪdisabled
function disabledButton()
{
    var elementsNum = 0;//FORM�е�Ԫ����
    //����FORM�е�����ELEMENT
    for (elementsNum=0; elementsNum<fm.elements.length; elementsNum++)
    {
        if (fm.elements[elementsNum].type == "button")
        {
            fm.elements[elementsNum].disabled = true;
        }
    }
}

//���ý����ϵ����а�ťΪnot disabled
function notDisabledButton()
{
    var elementsNum = 0;//FORM�е�Ԫ����
    //����FORM�е�����ELEMENT
    for (elementsNum=0; elementsNum<fm.elements.length; elementsNum++)
    {
        if (fm.elements[elementsNum].type == "button")
        {
            fm.elements[elementsNum].disabled = false;
        }
    }
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
    try
    {
        initForm();
        fm.AccNo.value = "";
         fm.RptNo.value = "";
        fm.RptorName.value = "";
        fm.RptorPhone.value = "";
        fm.RptorAddress.value = "";
        fm.Relation.value = "";
        fm.RelationName.value = "";
        fm.RptMode.value = "";
        fm.RptModeName.value = "";
        fm.AccidentSite.value = "";
        fm.occurReason.value = "";
        fm.RptDate.value = "";
         fm.MngCom.value = "";
         fm.Operator.value = "";
        fm.ClmState.value = "";

        fm.customerName.value = "";
        fm.customerAge.value = "";
         fm.customerSex.value = "";
         fm.SexName.value = "";
        fm.IsVip.value = "";
        fm.AccidentDate.value = "";
         fm.occurReason.value = "";
         fm.ReasonName.value = "";
        fm.hospital.value = "";
        fm.TreatAreaName.value = "";
        fm.AccidentDate2.value = "";
         fm.accidentDetail.value = "";
//         fm.IsDead.value = "";
         fm.claimType.value = "";
         fm.cureDesc.value = "";
         fm.Remark.value = "";

        //���������ÿ�
        for (var j = 0;j < fm.claimType.length; j++)
        {
            fm.claimType[j].checked = false;
        }
    }
    catch(re)
    {
        alert("��LLClaimRegister.js-->resetForm�����з����쳣:��ʼ���������!");
    }
}

//���ذ�ť
function goToBack()
{
    location.href="LLClaimRegisterMissInput.jsp";
}

//---------------------------------------------------------------------------------------*
//���ܣ����������߼��ж�
//����1 �������߲С�ҽ������ֻ��ѡһ
//      2 ѡ��������߲�ʱ��Ĭ��ѡ�л���
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
//            if ((fm.claimType[1].checked == true || fm.claimType[5].checked == true) && fm.claimType[0].checked == true)
//            {
//                alert("�������߲С�ҽ������ֻ��ѡһ!");
//                fm.claimType[0].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "03" :
            if (fm.claimType[1].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[0].checked == true || fm.claimType[5].checked == true)&& fm.claimType[1].checked == true)
//            {
//                alert("�������߲С�ҽ������ֻ��ѡһ!");
//                fm.claimType[1].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "09" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[4].checked == false)
//            {
//                alert("ѡȡ�������߲б���ѡ�����!");
//                fm.claimType[4].checked = true;
//                return;
//            }
        case "00" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[5].checked == true)
//            {
//                alert("�������߲С�ҽ������ֻ��ѡһ!");
//                fm.claimType[5].checked = false;
//                return;
//            }
        default :
            return;
    }
}

//��ʾ���ز�
function showDIV()
{
  if (fm.RgtConclusion.value == '01')
  {
      //��ʾ�����
        spanConclusion1.style.display="";
        spanConclusion2.style.display="none";
        fm.dutySet.disabled = false;
        fm.medicalFeeCal.disabled = false;
        fm.printNoRgt.disabled = true;
        fm.printDelayRgt.disabled = true;
        fm.printPassRgt.disabled = false;
  }
  else if (fm.RgtConclusion.value == '02')
  {
      //��ʾ��������ԭ���
      spanConclusion1.style.display="none";
      spanConclusion2.style.display="";
      fm.printNoRgt.disabled = false;
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = false;
        fm.printPassRgt.disabled = false;
        fm.printDelayRgt.disabled = true;
  }
    else if (fm.RgtConclusion.value == '03')
    {
        spanConclusion1.style.display="none";
      spanConclusion2.style.display="none";
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = true;
        fm.printPassRgt.disabled = false;
        fm.printDelayRgt.disabled = false;
    }
}

//�¼���ѯ
//2005-8-1 16:08 �޸�:ȥ������ԭ��Ĳ�ѯ��������λ�¼�
function queryLLAccident()
{
  //�ǿռ��
  if (checkInput1() == false)
    {
        return;
    }
//    if (fm.occurReason.value == "")
//    {
//        alert("����ԭ��Ϊ�գ�");
//        return;
//    }
    //�����¼���
    var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";
    var tAccNo = easyExecSql(strSQL);
//    alert(tAccNo);
    if (tAccNo == null)
    {
        alert("û������¼���");
        return;
    }
    //���¼���ѯ����
//  var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value+"&AccType="+fm.occurReason.value;
  var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
//  alert(strUrl);
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"����¼�");
}

//�����˲�ѯ
function showInsPerQuery()
{
    window.open("LLLdPersonQueryMain.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryMain.jsp");
}

//�õ�0��icd10��
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//��ѯ���ս��2
function queryResult2()
{
    var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";
    var tICDName = easyExecSql(strSql);
    if (tICDName)
    {
        fm.AccResult2Name.value = tICDName;
    }
}

//��ӡ��֤ǩ���嵥,����ͨ��(01)ʱ����ʹ��-----���ߴ�ӡ�������ж�
function prtPassRgt()
{
    //�������Ƿ�Ϊ����ͨ��,�����ܴ�ӡ
    var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";
    var tResult = easyExecSql(strSQL);
    if (tResult == null)
    {
        alert("���ȱ�����������!");
        return;
    }

    //������д��ӡ�ύ����
    fm.action = './LLPRTCertificateSignforSave.jsp';
    fm.target = "../f1print";
    fm.submit();
}

//��ӡ��������֪ͨ��,��������(02)ʱ����ʹ��
function prtNoRgt()
{
    //�������Ƿ�Ϊ��������,�����ܴ�ӡ
    var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";
    var tResult = easyExecSql(strSQL);
    if (tResult != '02')
    {
        alert("���ȱ�����������!");
        return;
    }
    //������д��ӡ�ύ����
    fm.action = './LLPRTProtestNoRegisterSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT007")=="false")
    {
      return;
    }
//    fm.target = "../f1print";
//    fm.submit();
}

//��ӡ���䵥֤֪ͨ��,�ӳ�����(03)ʱ����ʹ��
function prtDelayRgt()
{
    //�������Ƿ�Ϊ�ӳ�����,�����ܴ�ӡ
    var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";
    var tResult = easyExecSql(strSQL);
    if (tResult != '03')
    {
        alert("���ȱ�����������!");
        return;
    }
    //������д��ӡ�ύ����
    fm.action = './LLPRTCertificateRenewSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT003")=="false")
    {
      return;
    }

//    fm.target = "../f1print";
//    fm.submit();
}

//���Ԥ��������,ֻ�ܴ���С
function checkAdjPay()
{
    var tStandM = parseFloat(fm.standpay.value);
    var tAdjM = parseFloat(fm.adjpay.value);
    if (tStandM > tAdjM)
    {
        alert("��������С��Ԥ�����!");
        fm.adjpay.value = fm.standpay.value;
        return;
    }
}

//��ӡ�˲м���֪ͨ��
function PrintMedCert()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }

    fm.action = './LLPRTAppraisalSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT001")=="false")
    {
      return;
    }
//    fm.target = "../f1print";
//    fm.submit();

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
    fm.submit();
}


/**********************************
//��ӡǰ���飨������Ҫ���루��֤���롢�ⰸ�ţ�--------2005-08-22���
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
    var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
      +" and t.otherno='"+tclmno+"' "
      +" and trim(t.code)='"+tcode+"' ";
  var arrLoprt = easyExecSql(strSql);
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
    fm.PrtSeq.value=arrLoprt[0][0];//��֤��ˮ��
    //���ڵ�δ��ӡ����ֱ�ӽ����ӡҳ��
     if(tstateflag=="0")
     {
//      fm.action = './LLPRTCertificatePutOutSave.jsp';   //
      fm.target = "../f1print";
      fm.submit();
     }
    else
    {
      //���ڵ��Ѿ���ӡ����tstateflag[��ӡ״̬��1 -- ��ɡ�2 -- ��ӡ�ĵ����Ѿ��ظ���3 -- ��ʾ�Ѿ����߰�֪ͨ��]
      if(confirm("�õ�֤�Ѿ���ӡ��ɣ���ȷʵҪ������"))
       {
         //���벹��ԭ��¼��ҳ��
         var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value;
//         window.open(strUrl,"��֤����");
         window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
       }
    }
  }
}