var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
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

/**=========================================================================
�޸�״̬����ʼ
�޸�ԭ�򣺴�ӡ��֤
�� �� �ˣ�����
�޸����ڣ�2005.07.28
�޸ģ�2005.08.22����ӡ��֤ʱ��ǰ̨�жϡ��Ƿ���ڻ�����Ҫ���򡷣���ѯ��ˮ�ţ������̨��
=========================================================================
**/
function showPrtAffix()
{
	var row = SubReportGrid.getSelNo();	
	if(row < 1) {
	    alert("��ѡ��һ�м�¼��");
	    return;
	}
	var CustomerNo = SubReportGrid.getRowColData(row-1 ,1);
	fm.customerNo.value = CustomerNo;
	/*var tAffixSql = "select * from LLReportAffix where rptno='"+fm.RptNo.value
	+"' and customerno='"+CustomerNo+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql1");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(CustomerNo); 
	var arrAffix = easyExecSql(mySql.getString());
	if(!arrAffix){
		alert("û����Ҫ��ӡ���������ϣ�");
		return false;
	}
	fm.action = '../claim/LLPRTCertificatePutOutSave.jsp';  
	if(beforePrtCheck(fm.ClmNo.value, CustomerNo, "PCT002")==false)
	{
	  return;
	} 
	//  fm.target = "../f1print";
	//  document.getElementById("fm").submit();
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
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ���ж��ʻ��Ƿ����
    �� �� �ˣ�P.D
    �޸����ڣ�2006.07.12
   =========================================================================
**/
//���б�������ƥ��
function showMatchDutyPay()
{
    /*var strSQL = "select count(*) from lcinsureaccclass where accascription = '0'"
                + " and grpcontno = '" + fm.GrpContNo.value+"'";*/
   	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql2");
	mySql.addSubPara(fm.GrpContNo.value); 
	
    var tCount = easyExecSql(mySql.getString());
    if(tCount > 0){
       if(confirm("��ȷ���Ѿ����˹���?"))
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

      }else{
        alert("�뵽��ȫ������!");
        return false;
      }
    }else{

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
    /*tSql = " select a.realpay,b.BeAdjSum "
         + " from LLClaim a,LLRegister b  where 1=1 "
         + " and a.ClmNo = b.RgtNo and a.ClmNo = '"+tClaimNo+"'"*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql3");
	mySql.addSubPara(tClaimNo); 
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        arr[0][0]==null||arr[0][0]=='null'?'0':fm.standpay.value  = arr[0][0];
        arr[0][1]==null||arr[0][1]=='null'?'0':fm.adjpay.value  = arr[0][1];
        if (fm.adjpay.value == "0")
        {
            fm.adjpay.value = fm.standpay.value;
        }
    }

    //��ѯ�����ⰸ�Ľ��
   /* tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql4");
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
        +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and c.code=a.GetDutyKind),"
        +" a.TabFeeMoney,a.SelfAmnt,a.ClaimMoney,a.SocPay,a.OthPay,RealPay "
        +" from LLClaimDutyKind a  where 1=1 "
        +" and a.ClmNo = '"+tClaimNo+"'"*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql5");
	mySql.addSubPara(tClaimNo); 
    //prompt("��ѯLLClaimDutyKind�������ⰸ�������ͽ��в���",tSql);
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
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql6");
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
    ////to_char(b.PayEndDate,'yyyy-mm-dd')+a.GracePeriod," //�������� + ������--+ a.GracePeriod
     initPolDutyCodeGrid();
/*
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
       +" and a.CustomerNo = '"+tCustNo+"'"
*/
    /*tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
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
       +" a.dutycode,a.CustomerNo,a.GrpContNo,a.ContNo "
       +" , (select codename from ldcode where codetype='polstate' and code in(select polstate from lcpol t where t.polno=a.PolNo))"
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode" 
       +" and a.ClmNo = '"+tClaimNo+"'"   
       //+" and a.GiveType not in ('1')"
       +" and a.CustomerNo = '"+tCustNo+"'"
       ;*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql7");
	mySql.addSubPara(tClaimNo); 
	mySql.addSubPara(tCustNo); 
    //prompt("��ѯ������",tSql);
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
    else
    {
        initPolDutyCodeGrid();
    }
  
      
    //��ѯLLClaimPolicy,��ѯ��ȫ��Ŀ��Ϣ      
/*     tSql = " select a.PolNo,(select RiskCode from LCPol where PolNo=a.PolNo),(select c.codename from ldcode c where c.codetype = 'edortypecode' and c.code=a.EdorType),"
     +" a.EdorValiDate,a.GetMoney "
     +" from LPEdorItem a where 1=1 and a.PolNo in(select PolNo from LLClaimPolicy where ClmNo = '"+tClaimNo+"') ";
    arr = easyExecSql( tSql );
  
    if (arr)
    {
        displayMultiline(arr,LPEdorItemGrid);
    }
    else
    {
        initLPEdorItemGrid();
    }
*/

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
    var tRiskCode = '';
    //���ð�ť
    fm.addUpdate.disabled = false;//����޸�
    //�õ�mulline��Ϣ
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.PolNo.value = PolDutyCodeGrid.getRowColData(i,1);//������
        /*var psql="select polstate from lcpol where polno='"+fm.PolNo.value+"' ";
        var tpolstate=new Array;
        tpolstate=easyExecSql(psql); 
        fm.polstate.value = tpolstate;
        showOneCodeName('polstate','polstate','polstateName');    */
        
        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,14);//�⸶����
        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,22);
        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,23);//����ԭ��
        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,24);//
        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,25);//������ע
        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,16);//�ܸ�ԭ�����
        fm.GiveReasonDesc.value = PolDutyCodeGrid.getRowColData(i,18);//�ܸ�����
        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,19);//���ⱸע
        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
        showOneCodeName('llprotestreason','GiveReason','GiveReasonName');
        tRiskCode = PolDutyCodeGrid.getRowColData(i,3);//���ֱ���
    }
    //�ʻ��������жϣ��ǵĲ������޸�������
    //var sql1 = " select insuaccflag From lmrisk where riskcode = '"+tRiskCode+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql8");
	mySql.addSubPara(tRiskCode); 
    var tInsuaccFlag = easyExecSql(mySql.getString());
    //���������жϣ��ǵĲ������޸�������
   // var sql2 = " select riskperiod from lmriskapp where riskcode = '"+tRiskCode+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql9");
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
    else if (fm.GiveType.value == '2'||fm.GiveType.value == '3')
    {
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
        divGiveTypeUnit3.style.display= "none";        
    }
    
}

//�Ա��������޸�
function AddUpdate()
{
    if(PolDutyCodeGrid.getSelNo() <= 0)
    {
        alert("����ѡ��Ҫ����ı�����Ϣ,��ִ�д˲�����");
        return;
    }       
    
    checkAdjMoney();//��鱣�������

    
    var i = PolDutyCodeGrid.getSelNo() - 1;//�õ�������
    //alert(385);
    PolDutyCodeGrid.setRowColData(i,14,fm.GiveType.value);//�⸶����
    PolDutyCodeGrid.setRowColData(i,16,fm.GiveReason.value);//�ܸ�ԭ�����
    PolDutyCodeGrid.setRowColData(i,18,fm.GiveReasonDesc.value);//�ܸ�����
    PolDutyCodeGrid.setRowColData(i,19,fm.SpecialRemark.value);//���ⱸע
    PolDutyCodeGrid.setRowColData(i,22,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReason.value);//����ԭ��
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,25,fm.AdjRemark.value);//������ע
    if(fm.GiveType.value == 0){
    PolDutyCodeGrid.setRowColData(i,15,"����");//�⸶��������
    }else if(fm.GiveType.value == 1){
    PolDutyCodeGrid.setRowColData(i,15,"�ܸ�");//�⸶��������
    }else if(fm.GiveType.value == 2){
    PolDutyCodeGrid.setRowColData(i,15,"ͨ���⸶");//�⸶��������
    }else if(fm.GiveType.value == 3){
    PolDutyCodeGrid.setRowColData(i,15,"Э���⸶");//�⸶��������
    }
    //alert(403);
    fm.saveUpdate.disabled = false;//�����޸�
}

//�Ա����޸ı��浽��̨
function SaveUpdate()
{
   /*   
   if (fm.GiveType.value==2||fm.GiveType.value==3)
    {      
      var tsql="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('tOperator').value+"' ";
      var tappndmax=new Array;
      tappndmax=easyExecSql(tsql);
      
      if (tappndmax<document.all('RealPay').value) 
      {
      	 alert("����ͨ�ڡ�Э���⸶Ȩ�޲�����");
      	 return false;
      }     	
    }else if (fm.GiveType.value==1)
    {
      var tsql="select checklevel from llclaimuser where usercode='"+document.all('tOperator').value+"' ";	
      var tchecklevel=new Array;
      tchecklevel=easyExecSql(tsql);
      if (tchecklevel=="A"||tchecklevel=="B1"||tchecklevel=="B2"||tchecklevel=="B3")
      {
        alert("��û�оܸ�Ȩ�ޣ�");	
        return false;
      }    
    }
    */
    //�ڸ���ʱУ��     
    
    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    submitForm();
    fm.saveUpdate.disabled = true;//�����޸�
}

//��鱣�������
function checkAdjMoney()
{
    var i = PolDutyCodeGrid.getSelNo()-1;
    if (i != '0')
    {

        //i = i - 1;
        var tContNo   = parseFloat(PolDutyCodeGrid.getRowColData(i,33));//������ͬ��
        var tRiskCode = parseFloat(PolDutyCodeGrid.getRowColData(i,3));//���ֱ���
        var tDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,29));//���α���
        var tRealM    = parseFloat(PolDutyCodeGrid.getRowColData(i,22));//�������
        var tAmnt     = parseFloat(PolDutyCodeGrid.getRowColData(i,10));//����
        var tGrpContNo= parseFloat(PolDutyCodeGrid.getRowColData(i,31));//���屣����
        var tGetDutyKind = parseFloat(PolDutyCodeGrid.getRowColData(i,2));
        var tGetDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,4));
        var tAdjM     = parseFloat(fm.RealPay.value);
        var tContPlanCode = "0";
        var tDeadTopvalFlag = "";

       //�жϽ����Ͳ�������
       /*var strSQL4 = " select deadtopvalueflag from lmdutygetclm where "
                   + " getdutycode='"+tGetDutyCode+"' and getdutykind='"+tGetDutyKind+"'"*/
       mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql10");
	mySql.addSubPara(tGetDutyCode); 
	mySql.addSubPara(tGetDutyKind); 
       var arr4= easyExecSql(mySql.getString());
          if(arr4 != null){
          tDeadTopvalFlag = arr4[0][0];
             }

        if(tRiskCode == '140' || tDeadTopvalFlag == 'N'){
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
             if(arr2 != null){
            tAmnt = arr2[0][0];
                 }else{
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
            fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,13);
            return;
        } else if(tAdjM < 0){
            alert("��������С����!");
            fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,13);
            return;
        }

    }
    if (fm.GiveType.value=="")
    {
    	alert("�������⸶����!");
    	return;
    }else if (fm.GiveType.value=="0" || fm.GiveType.value=="2" || fm.GiveType.value=="3")
    {
       if (fm.RealPay.value=="")
       {
       	alert("������������!");
       	return;
       }    
       if (fm.AdjReason.value=="")
       {
       	alert("���������ԭ��!");
       	return;
       }      	
    }else if (fm.GiveType.value=="1")
     {
        if (fm.GiveReason.value=="")
        {
        	alert("������ܸ�ԭ��!");
        	return;
        }         	
     }else
     {
     		alert("��������ȷ���⸶���۴���!");
        return;     		
      }
    if(fm.AdjReason.value == "00"||fm.AdjReason.value == "14"){
    	if(fm.AdjRemark.value == null || fm.AdjRemark.value =="null" || fm.AdjRemark.value == ""){
    		alert("����ԭ��Ϊͨ�ڸ�����Э�����ʱ������¼�������ע!");
    		return false;
    	}
    }
    
}

//¼��ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeInp()
{

    //var tSel = SubReportGrid.getSelNo();
    //var tClaimNo = SubReportGrid.getRowColData(tSel - 1,8);         //�ⰸ��
    //var tCaseNo = SubReportGrid.getRowColData(tSel - 1,2);          //�ְ���
    //var tCustNo = SubReportGrid.getRowColData(tSel - 1,1);          //�ͻ���
    //if( tSel == 0 || tSel == null ){
//        alert( "����ѡ��һ���ͻ���¼����ִ�д˲���!" );
//        return false;
//    }
//else{
//        window.open("LLMedicalFeeInpInput.jsp?clamNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&custName="+tCustName+"&custSex="+tCustSex,"ҽ�Ƶ�֤��Ϣ");
//    }

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
    var strUrl="../claim/LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.AccidentDate.value+"&otherAccDate="+fm.AccidentDate.value+"&GrpFlag=1";
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
//  var tCustomerNo = fm.customerNo.value;
//  if (tCustomerNo == "")
//  {
//      alert("��ѡ������ˣ�");
//      return;
//  }
//    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo;
	var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tRgtNo = fm.RptNo.value;
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+tRgtNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//������Ϣ��ѯ
function showCard(){
    var strUrl="../card/CardContInput.jsp?flag=1";//flag = 1 ������ѯ���Button������ʾ
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�鿴����
function showQueryInq()
{
//  var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
//    if(fm.RptNo.value == "" || fm.RptNo.value == null)
//    {
//        alert("��ѡ���ⰸ��");
//        return;
//    }
    //---------------------------------------
    //�жϸ��ⰸ�Ƿ���ڵ���
    //---------------------------------------
   /* var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
                mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql11");
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

//�򿪳ʱ���ѯ
function showQuerySubmit()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
//    if(fm.RptNo.value == "" || fm.RptNo.value == null)
//    {
//        alert("��ѡ���ⰸ��");
//        return;
//    }
  //---------------------------------------
  //�жϸ��ⰸ�Ƿ���ڳʱ�
  //---------------------------------------
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql12");
	mySql.addSubPara(fm.RptNo.value); 
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
        alert("���ⰸ��û�гʱ���Ϣ��");
        return;
    }
    var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
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
}

//������ѯ
function showLLReportQuery()
{
    //window.open("LLReportQueryInput.jsp","");
    window.open("LLReportQueryInput.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
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
    fm.customerAge.value = arr[4];
    //fm.SocialInsuFlag.value = arr[4];
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
    fm.deletebutton.disabled=false;
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
        fm.customerAge.value = getAge(SubReportGrid.getRowColData(i,5));
        fm.SocialInsuFlag.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql13");
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

                    //���Ϊ�˲У���ʾ�˲м���֪ͨ2005-8-13 11:04
                    if (tClaim == '01')
                    {
                        //fm.MedCertForPrt.disabled = false;
                    }
                }
            }
        }
    }
    //��ѯ�ְ�����Ϣ
    var tSubReport = new Array;
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccDesc,AccResult1,AccResult2,HospitalName,(select codename from ldcode where codetype='accidentDetail' and code=AccidentDetail) from LLSubReport where 1=1 "
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
 	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql14");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
    tSubReport = easyExecSql(mySql.getString());
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
    fm.TreatAreaName.value = tSubReport[0][9];
    fm.accidentDetailName.value = tSubReport[0][10];
//    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ

//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult2();
    afterMatchDutyPayQuery();
    var claimNum = new Array;
	/*var strSQL3 = "select  count(1) from  llregister where clmstate in ('50','60','70') and rgtno in "
					+"(select rgtno from llcase where 1=1 "
					+ getWherePart( 'CustomerNo','customerNo' )
					+")";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql15");
	mySql.addSubPara(fm.customerNo.value); 
	claimNum = easyExecSql(mySql.getString());
	if(tSubReport!=null){
		fm.claimNum.value = claimNum[0][0];
	}
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
    //------------------------------------------**End

    //--------------------------------------------Beg
  //��ʾ��������
  //--------------------------------------------
  spanText1.style.display = "none";
  spanText2.style.display = "";
  spanText3.style.display = "";
  spanText4.style.display = "none";
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
        //alert("fm.customerName.value:"+fm.customerName.value)
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = getAge(SubReportGrid.getRowColData(i,5));
        fm.SocialInsuFlag.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql16");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
    /*var strSQL1 = "select ReasonCode from LLReportReason where "
                + " RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql17");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
    tClaimType = easyExecSql(mySql.getString());
    if(tClaimType == null){
        alert("��������Ϊ�գ�����˼�¼����Ч�ԣ�");
        return;
        }
    }
    if(tClaimType!= '')
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
                    //���Ϊ�˲У���ʾ�˲м���֪ͨ2005-8-13 11:04
                    if (tClaim == '01')
                    {
                        //fm.MedCertForPrt.disabled = false;
                    }
                }
            }
        }
    }
    //��ѯ�ְ�����Ϣ
    var tSubReport = new Array;
    //-------------------------1----------2-----------3---------4--------5-------6--------7-----------8-----------9------------10-------------------------------------------11
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,AffixConclusion,(case AffixConclusion when '0' then '��' when '1' then '��' end),standpay,HospitalName,(select codename from ldcode where codetype='accidentcode' and code=AccidentDetail) from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql18");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
    tSubReport = easyExecSql(mySql.getString());//prompt("",strSQL2);
if(tSubReport!=null){
    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.AccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.RemarkDisabled.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccDescDisabled.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.IsAllReady.value = tSubReport[0][9];
    fm.IsAllReadyName.value = tSubReport[0][10];
    fm.standpay.value = tSubReport[0][11];
    fm.TreatAreaName.value = tSubReport[0][12];
    fm.accidentDetailName.value = tSubReport[0][13];
    //alert("fm.accidentDetailName.value:"+fm.accidentDetailName.value);
}
	var claimNum = new Array;
	/*var strSQL3 = "select  count(1) from  llregister where clmstate in ('50','60','70') and rgtno in "
					+"(select rgtno from llcase where 1=1 "
					+ getWherePart( 'CustomerNo','customerNo' )
					+")";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql19");
	mySql.addSubPara(fm.customerNo.value); 
	claimNum = easyExecSql(mySql.getString());
	if(tSubReport!=null){
		fm.claimNum.value = claimNum[0][0];
	}
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
   // showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
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
//����ѡ�еĳ�������ʾ��ص�������
    afterMatchDutyPayQuery();
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
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";

}

//�ύǰ��У�顢����
function beforeSubmit()
{
	
	/////////////////////////////////////////////////////////////////////////
	// * ���ӳ����˵ĳ���ԭ�����һ�µ�У��  09-04-17                         //
	/////////////////////////////////////////////////////////////////////////
	var tClmNo = fm.RptNo.value;
	var tCustomerNo = fm.customerNo.value;
	//var tReasonSql = "select AccidentType from LLSubReport where subrptno='"+tClmNo+"' and customerno<>'"+tCustomerNo+"'";
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql20");
	mySql.addSubPara(tClmNo); 
	mySql.addSubPara(tCustomerNo); 
	var occurReasonResult = easyExecSql(mySql.getString());//prompt("",tReasonSql);
	if(occurReasonResult){
		if(fm.occurReason.value!=occurReasonResult[0][0]){
			alert("�����˵ĳ���ԭ����뱣��һ�£�");
			return false;
		}
	}
    //�ж�����ͻ���
    if(fm.GrpCustomerNo.value == ''){
     alert("����ͻ��Ų���Ϊ��!");
     return false;
    }
    //�ж����屣����
    if(fm.GrpContNo.value == ''){
     alert("���屣���Ų���Ϊ��!");
     return false;
    }
    //�ж�Ͷ����λ
    if(fm.GrpName.value=='')
    {
    	alert("�����뵥λ����!");
    	return false;
    }
  //�ж���������
    if (fm.AcceptedDate.value == "" || fm.AcceptedDate.value == null)
    {
        alert("�������������ڣ�");
        return false;
    }
    
    
    //��ȡ������Ϣ
    var RptNo = fm.RptNo.value;//�ⰸ��
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccReason = fm.occurReason.value;//����ԭ��
    var AccDate = fm.AccidentDate.value;//�¹�����
    var AccDate2 = fm.AccidentDate.value;//��������
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
/*
    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.AccidentDate2.value))
    {
        alert("����ԭ��Ϊ����ʱ���¹����ڱ�����ڳ������ڣ�");
        return false;
    }
*/
    //---------------------------------------------------------------------------------**End

    //5 �����������
    if (ClaimType == null || ClaimType == '')
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }
    //6 ���ҽԺ����
    if (fm.hospital.value == '' && fm.occurReason.value == '2')
    {
        alert("����ԭ��Ϊ����,����ҽԺ���벻��Ϊ�գ�");
        return false;
    }
    //---------------------------------------------*Beg*2005-6-13 20:26
    //�������������ѡ��"ҽ��"����ѡ��ҽԺ���ж�
    //---------------------------------------------*
//    if(fm.claimType[5].checked == true && (fm.hospital.value == "" || fm.hospital.value == null))
//    {
//        alert("��ѡ��ҽԺ��");
//        return false;
//    }
    //---------------------------------------------*End
    //---------------------------------------------------------Beg*2005-6-27 11:55
    //�������������ѡ�л���,����ѡ��������߲�֮һ���ж�
    //---------------------------------------------------------
    //if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false)
    //{
        //alert("ѡ�л���,����ѡ��������߲�֮һ!");
        //return false;
    //}
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
//ɾ�����غ���ķ���
if(fm.fmtransact.value == "DELETE"){
    queryReport();
//    queryProposer();
}else{
    queryRegister();
}
      //���ÿɲ�����ť
        if(fm.isNew.value == '0')
        {
            operateButton21.style.display="";
            operateButton23.style.display="";
            operateButton22.style.display="none";
            fm.QueryPerson.disabled = false;
            //fm.QueryReport.disabled = false;
        }
        if(fm.isNew.value == '1')
        {
            operateButton21.style.display="";
            operateButton23.style.display="";
            operateButton22.style.display="";
            fm.QueryPerson.disabled = false;
            fm.addbutton.disabled = true;
            //fm.QueryReport.disabled = true;
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

        afterMatchDutyPayQuery();
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
    
    //����Ƿ���ɨ��� liuyu-20070827
   /* var ssql="select count(*) from es_doc_main where doccode='"+fm.RptNo.value+"' ";
    var scancount=easyExecSql(ssql);
    if (scancount==0)
    {
        alert("���ⰸ��û��ɨ�����Ϣ����������ȷ�ϣ�");
        return;    	
    }*/ 
          
    //У���⸶������������Ƿ����  liuyu-2008-2-29
    //var accSql="select nvl(sum(pay),0) from llbalance where clmno='"+fm.RptNo.value+"' ";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql21");
	mySql.addSubPara(fm.RptNo.value); 
	
	var accMoney=easyExecSql(mySql.getString());
    //var polSql="select nvl(sum(realpay),0) from llclaimpolicy where clmno='"+fm.RptNo.value+"' ";
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql22");
	mySql.addSubPara(fm.RptNo.value); 

    var polMoney=easyExecSql(mySql.getString());
     
    if (parseFloat(accMoney)!=parseFloat(polMoney))
    {
        alert("���ⰸ�⸶�����������ȣ���������ȷ�ϣ���������������ԣ�");
        return;      	
    }           

    //-------------------------------------------------------------------BEG2005-8-9 8:58
    //����������ۡ�ƥ����㡢��֤��ȫ
    //-------------------------------------------------------------------
   /* var sql1 = " select RgtConclusion from llregister where "
            + " RgtNo = '" + fm.RptNo.value + "'"*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql23");
	mySql.addSubPara(fm.RptNo.value); 
    var tRgtConclusion = easyExecSql(mySql.getString());
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
           /* var sql2 = "select count(1) from LLClaimDetail where"
                     + " ClmNo = '" + fm.RptNo.value + "'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql24");
			mySql.addSubPara(fm.RptNo.value); 
            var tDutyKind = easyExecSql(mySql.getString());
            if (tDutyKind == 0)
            {
                alert("���Ƚ���ƥ�䲢����!");
                return;
            }
            
            //var txsql="select distinct givetype from llclaimdetail where clmno='"+fm.RptNo.value+"' ";
           	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql25");
			mySql.addSubPara(fm.RptNo.value); 
            var tGiveType=easyExecSql(mySql.getString());
            
            if (tGiveType.length>1)
            {     
               alert('�⸶���۲�ͳһ�����޸ĺ�������ȷ�ϣ�');	
               return;
            }              
             
            //��鵥֤��ȫ��־
            //alert(fm.IsAllReady.value);
            if (fm.IsAllReady.value != '1')
            {
            	//alert(fm.IsAllReady.value);
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
                //var tResult = new Array;
                //var sql = " select affixconclusion from llcase where "
                //         + " caseno = '" + fm.RptNo.value + "'"
                //tResult = easyExecSql(sql);
                //if (tResult != null)
                //{
                //    for (var i=0; i < tResult.length; i++)
                //    {
                //        if (tResult[i] != "0")
                //        {
//                            if (confirm("��֤����ȫ,�Ƿ�����!"))
//                            {
//                                yesORno = 1;
//                            }
//                            else
//                            {
//                                yesORno = 0;
//                                return;
//                            }
                //            alert("��֤����ȫ,��������ͨ��!");
                //            return;
                //        }
                //        else
                //        {
                //            yesORno = 1;
                //        }
                //    }
                // }
                
                if (fm.IsAllReady.value != "1")
                {
//                    if (confirm("��֤����ȫ,�Ƿ�����!"))
//                    {
//                        yesORno = 1;
//                    }
//                    else
//                    {
//                        yesORno = 0;
//                        return;
//                    }
                    alert("��֤����ȫ,��������ͨ��!");
                    return;
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
/*
    if (tRgtConclusion == '01')
    {
        var tGivetype = new Array;
        var strSql = "select givetype from LLClaimDetail where "
                   + "clmno = '" + fm.RptNo.value + "'";
        tGivetype = easyExecSql(strSql);
        var tYes = 0;
        if (tGivetype != null)
        {
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
*/
    //-------------------------------------------------------------------END

    //�Ƿ��ύ
    //if (yesORno == 1)
    //{
        fm.RgtConclusion.value = tRgtConclusion;
        fm.action = './LLClaimRegisterConfirmSave.jsp';
        submitForm();
    //}
}

//[����]��ť��Ӧ����
function saveClick()
{
    //���Ƚ��зǿ��ֶμ���
    if (beforeSubmit())
    {
//        var row = SubReportGrid.getSelNo();
//        if(row < 1)
//        {
//            alert("��ѡ��һ�м�¼��");
//            return;
//        }
        
    	      //var tsql="select riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"' ";
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql26");
			mySql.addSubPara(fm.GrpContNo.value); 
            var tsub=new Array;
            tsub=easyExecSql(mySql.getString());
        
           if (tsub != null)
           {
             for (var i=0; i < tsub.length; i++)
             {
              //alert("tsub[i]:"+tsub[i]);        

          	  //var accSql="select risktype6 from lmriskapp where riskcode='"+tsub[i]+"' ";
	           mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql27");
			mySql.addSubPara(tsub[i]);  
	            var tRiskType6 = easyExecSql(mySql.getString());	
	                       //prompt("",accSql);
             if(tRiskType6!="8")   //Ŀǰ��307���ŵ���ֻ��307һ������,risktype6='8'����307������
              {
              if(TempCustomer() == false && showDate() == false)
                {
                alert("�ñ���δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڣ�");         
                return false;
                }
              
              }
             }
           }
    	 
        /*update by wood�����ڱ��������������ƣ����ﲻ���������ˡ�
          ���ӶԳ����˵�������������жϣ������δ�᰸�İ������ڣ�����������
         *2006-03-06 P��D
         
         
        var StrSQL = "select count(*) from llcase a, llregister b where a.caseno = b.rgtno and b.grpcontno='"+fm.GrpContNo.value+"' and a.customerno = '"+fm.customerNo.value+"' and  b.clmstate not in ('60','80','50','70')";
        var arr= easyExecSql(StrSQL);
        if(arr > 0){
           alert("�ó�������δ�᰸������᰸������������");
           return false;
        }
        
        */
        /*�Ա����źͿͻ��Ž���У��
         *2007-02-13 L��Y 
                 
        var StrSQL = "select count(*) from lcgrpcont where appntno='"+fm.GrpCustomerNo.value+"' and grpcontno='"+fm.GrpContNo.value+"' ";
        var arr= easyExecSql(StrSQL);
        if(arr == 0){
           alert("������ͻ��������屣���Ų�ƥ�䣬���޸ĺ��ٲ�����");
           return false;
        }
        */
        
        /*================================================================
         * �޸�ԭ�����Ӷ�����������������죩�ı������˵��жϲ���������
         * �� �� �ˣ�P.D
         * �޸����ڣ�2006-8-16
         *=================================================================
         */
        /*var SqlPol = " select count(*) From lcpol where polstate = '6'"
                   + " and  insuredno = '"+fm.customerNo.value+"'"
                   + " and  grpcontno = '"+fm.GrpContNo.value+"'";*/
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql28");
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value);  
        if(fm.Polno.value != ''){
           // SqlPol += " and riskcode = '"+fm.Polno.value+"'";
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql29");
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value);  
			mySql.addSubPara(fm.Polno.value);  
             }
        var tPolState = easyExecSql(mySql.getString());
        if(tPolState > 0){
           alert("�ó������Ѿ����������������������������");
           return false;
         }
        //���ӳ��նԱ�ȫ���жϣ���ȫδȷ�Ϻ��˱��Ĳ��������� 2006-8-14
        //var SqlBq = "select  count(*) from LPEdorItem where insuredno = '"+fm.customerNo.value+"' and edorstate != '0' and edortype = 'CT'";
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql30");
			mySql.addSubPara(fm.customerNo.value); 
        var tEdorState = easyExecSql(mySql.getString());
       /* var sqlC = "select count(*) from lmriskapp where "
                 + "riskcode in (select riskcode From lcpol where "
                 + "grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"') "
                 + "and RiskPeriod = 'L'";*/
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql31");
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(fm.customerNo.value);  
			
        var tcount = easyExecSql(mySql.getString());

        if(tEdorState > 0 && tcount > 0 ){
           alert("�ó�������δȷ�ϵı�ȫ���Ѿ��˱�����ȷ�Ϻ�����������");
           return false;
        }
         
      /* var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
           strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
           strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'";*/
        	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql32");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value);  
       if (fm.GrpCustomerNo.value!=null)
          {
            //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql33");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpCustomerNo.value);  
          }
       if (fm.GrpContNo.value!=null)
          {
           // strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql34");
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
/*
        //�ֺ��ж�
        var SQLbonusflag = "select count(*) from lmriskapp where  bonusflag = '1' ";
        if(fm.Polno.value != '')
        {
            SQLbonusflag += " and riskcode = '"+fm.Polno.value+"'";
        }else{
            SQLbonusflag += " and riskcode in (select polno From lcpol where grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"')";
        }
        var tBonusFlag = easyExecSql(SQLbonusflag);
        if (tBonusFlag > 0)//����Ҫ�ֺ������
        {//-----------------1

        var tAgetDate = fm.AccidentDate.value.substring(0,4);
        var SQLCount = "select count(*) from lobonuspol where agetdate like '"+tAgetDate+"%%' and  polno in "
                     + " (select polno From lcpol where grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"' )";
        var tCount = easyExecSql(SQLCount);
        if (tCount < 0)
        {
            alert("�������ֺ촦���������⣡");
            return false;
        }

        }
*/
        //�ж������������������������Ǳ��������        
        //if (fm.isReportExist.value == "false")
        //{
            fm.fmtransact.value = "INSERT";
        //}
        //else
        //{
        //    fm.fmtransact.value = "insert||customer";
        //}
        //alert(fm.fmtransact.value);
        fm.action = './LLClaimRegisterSave.jsp';
        submitForm();

    }
}

//[ɾ��]��ť��Ӧ����

function deleteClick()
{
  var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
     fm.fmtransact.value = "DELETE";
     fm.action = './LLClaimRegisterSave.jsp';
     submitForm();
}

//[����]��ť��Ӧ����
function saveConclusionClick()
{
    //���Ƚ��зǿ��ֶμ���
    if(fm.RgtConclusion.value == ''){
        alert("����д�������ۣ�");
        return false;
    }

        //��ѯ�Ƿ����Ԥ�����¼��
    /*var sql2 = "select count(1) from LLStandPayInfo where"
                 + " CaseNo = '" + fm.RptNo.value + "'";
    var tDutyKind = easyExecSql(sql2);
        if (tDutyKind == 0)
        {
            alert("���Ƚ���Ԥ�����¼��!");
            return;
        }*/

      //��ѯ���������Ƿ����
    var tResult = new Array;
    /*var sql = " select affixconclusion from llcase where "
            + " caseno = '" + fm.RptNo.value + "'"*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql35");
			mySql.addSubPara(fm.RptNo.value); 
			
   tResult = easyExecSql(mySql.getString());//prompt("",sql);
   if (tResult != null)
     {
      for (var i=0; i < tResult.length; i++)
         {
          if (tResult[i] != '1')
             {
              alert("�������ϲ���ȫ,���ܽ��۱���!");
              return;
              }
         }
     }

    if (fm.RgtConclusion.value == '02' && (fm.NoRgtReason.value == '' || fm.NoRgtReason.value == null))
    {
        alert("����д��������ԭ��!");
        return;
    }
    
    if (fm.RgtConclusion.value == '03' && (fm.DeferRgtReason.value == '' || fm.DeferRgtReason.value == null))
    {
        alert("����д�ӳ�����ԭ��!");
        return;
    }    
/*
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
*/
    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimRegisterConclusionSave.jsp?DeferRgtRemark='+fm.DeferRgtRemark.value+'&RgtFlag=1';
    submitForm();
}

//[����]��ť��Ӧ����
//jixf 20051212
function saveConclusionClick1()
{
    //���Ƚ��зǿ��ֶμ���
    if (fm.RgtConclusion.value == '02' && (fm.NoRgtReason.value == '' || fm.NoRgtReason.value == null))
    {
        alert("����д��������ԭ��!");
        return;
    }
    if (fm.RgtConclusion.value == '01')
    {
        //��ѯ�Ƿ���й�ƥ�����
       /* var sql2 = "select count(1) from LLClaimDetail where"
                 + " ClmNo = '" + fm.RptNo.value + "'";*/
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql36");
			mySql.addSubPara(fm.RptNo.value); 
        var tDutyKind = easyExecSql(mySql.getString());
        if (tDutyKind == 0)
        {
            alert("���Ƚ���ƥ�䲢����!");
            return;
        }
    }
    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimRegisterConclusionSave1.jsp';
    submitForm();
}






//��������Ϣ�޸�
function updateClick()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    if (confirm("��ȷʵ���޸ĸü�¼��"))
    {
        
        if (beforeSubmit())
        {
            //var tsql="select riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"' ";
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql37");
			mySql.addSubPara(fm.GrpContNo.value); 
            var tsub=new Array;
            tsub=easyExecSql(mySql.getString());
        
           if (tsub != null)
           {
             for (var i=0; i < tsub.length; i++)
             {
              //alert("tsub[i]:"+tsub[i]);        

          	  //var accSql="select risktype6 from lmriskapp where riskcode='"+fm.Polno.value+"' ";
	           mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql38");
			mySql.addSubPara(fm.GrpContNo.value); 
	            var tRiskType6 = easyExecSql(mySql.getString());	
	                       
             if(tRiskType6!="8")   //Ŀǰ��307���ŵ���ֻ��307һ������,risktype6='8'����307������
              {
              if(TempCustomer() == false && showDate() == false)
                {
                alert("�ñ���δ��Ч����γ������ڲ��ڸñ������˵ı����ڼ��ڣ�");         
                return false;
                }
              
              }
             }
           }
           /* //jixf add 20051213
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
             } */
             
            /*var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
            strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
            strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'";*/
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql39");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value); 
             if (fm.GrpCustomerNo.value!=null)
             {
               //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
               mySql = new SqlClass();
               mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql40");
			mySql.addSubPara(fm.AccidentDate.value); 
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpCustomerNo.value); 
             }
            if (fm.GrpContNo.value!=null)
            {
              //strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
              mySql = new SqlClass();
              mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql41");
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
            fm.action = './LLClaimRegisterSave.jsp';
            submitForm();
        }
    }
}

//�����˲�ѯ
function ClientQuery()
{
    window.open("LLLdPersonQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

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
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
                mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql42");
			mySql.addSubPara(rptNo); 
			
    var AccNo = easyExecSql(mySql.getString());
    //���������ż�����������Ϣ(һ��)
    /*var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator,RgtClass,GRPCONTNO,APPNTNO,PEOPLES2,GRPNAME,RiskCode from LLReport where "
                + "RptNo = '"+rptNo+"'";*/
                mySql = new SqlClass();
     mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql43");
			mySql.addSubPara(rptNo); 
    var RptContent = easyExecSql(mySql.getString());
//    prompt("",strSQL2);
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

    fm.GrpContNo.value = RptContent[0][12];
    fm.GrpCustomerNo.value = RptContent[0][13];
    fm.Peoples.value = RptContent[0][14];
    fm.GrpName.value = RptContent[0][15];
    fm.Polno.value = RptContent[0][16];
}
    showOneCodeName('relation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ

    //---------------------------------------------------*
    //����ҳ��Ȩ��
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;
    fm.QueryPerson.disabled=false;
    //fm.QueryReport.disabled=true;
    fm.claimType.disabled=true;

    //�����ְ�������������Ϣ(����)
   /* var strSQL4 = "select count(*) from ldperson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
                mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql44");
			mySql.addSubPara(rptNo); 
    var tCount = easyExecSql(mySql.getString());
    if( tCount > '0' )
    {
	   /* var strSQL3 = "select CustomerNo,Name,Sex,(case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,Birthday,"
	    			+ " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
	    			+ "(case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־"
	    			+" from ldperson where "
	                + "CustomerNo in("
	                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
	                mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
			mySql.setSqlId("LLGrpClaimRegisterSql45");
			mySql.addSubPara(rptNo); 
	    //prompt("strSQL3",strSQL3);
	    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
	    
	    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
	    {
	        allSubReportGridClick(0);
	    }
  }

}

//�����˲�ѯ,������Ϊ��������ʱ������Ϊ������
function queryProposer()
{
    /*var strSQL = "select count(1) from LLReportReason a where "
                + "a.rpno = '" + fm.RptNo.value + "'"
                + "and substr(a.reasoncode,2,2) = '02'";*/
     mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql46");
	mySql.addSubPara(fm.RptNo.value ); 
    var tCount = easyExecSql(mySql.getString());
    //û��������������
    if (tCount == "0")
    {
       /* var strSQL2 = "select a.phone,a.postaladdress from LCAddress a where "
                    + "a.customerno = '" + fm.customerNo.value + "'";*/
        mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql47");
	mySql.addSubPara(fm.customerNo.value ); 
        var tLCAddress = easyExecSql(mySql.getString());
        //fm.RptorName.value = fm.customerName.value;
     if(tLCAddress!=null){
//        fm.RptorPhone.value = tLCAddress[0][0];
//        fm.RptorAddress.value = tLCAddress[0][1];
//        fm.Relation.value = "GX01";
       }
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
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql48");
	mySql.addSubPara(rptNo );             
    var AccNo = easyExecSql(mySql.getString());
    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18------------19-------20------------------------------------21-----------------------------------------------------22--------23------24------25------26--------27----------28------
   /* var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end),GRPCONTNO,APPNTNO,PEOPLES2,GRPNAME,RiskCode,clmstate,DeferRgtReason,AcceptedDate from llregister where "
                + "rgtno = '"+rptNo+"'";*/
    mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql49");
	mySql.addSubPara(rptNo );  
    var RptContent = new Array();
    RptContent = easyExecSql(mySql.getString());
//    prompt("321",strSQL2);
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
    fm.GrpContNo.value = RptContent[0][21];
    fm.GrpCustomerNo.value = RptContent[0][22];
    fm.Peoples.value = RptContent[0][23];
    fm.GrpName.value = RptContent[0][24];
    fm.Polno.value = RptContent[0][25];
    fm.clmState.value = RptContent[0][26];
    fm.DeferRgtReason.value=RptContent[0][27];   
    fm.AcceptedDate.value = RptContent[0][28];
}
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������
    showOneCodeName('llnorgtreason','NoRgtReason','NoRgtReasonName');//������ԭ��
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//�������Ա�
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//����������

//ȡ�� ����Ԥ����� 
    getGrpstandpay();

    //---------------------------------------------------*
    //����ҳ��Ȩ��
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;
    fm.QueryPerson.disabled=false;
    //fm.QueryReport.disabled=true;
    fm.claimType.disabled=true;

    //���ð�ť
    if (fm.RgtConclusion.value == "" || fm.RgtConclusion.value == null)
    {
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        //fm.printPassRgt.disabled = true;
        fm.printNoRgt.disabled = true;
        //fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = true;
    }
    else
    {
        fm.MedicalFeeInp.disabled = false;
    }

    //�����ְ�������������Ϣ(����)   
   /* var strSQL6 = "select count(*) from ldperson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
    mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql50");
	mySql.addSubPara(rptNo ); 
    var tCount = easyExecSql(mySql.getString());    
  if( tCount > '0' ){  	
   /* var strSQL3 = "select CustomerNo,Name,Sex,(case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,Birthday,"
    			+ "(nvl(SocialInsuFlag,0)) as SocialInsuFlag,"
    			+ "(case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־  from ldperson where "
                + "CustomerNo in("
                + "select CustomerNo from llcase where CaseNo = '"+ rptNo +"')";*/
    mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql51");
	mySql.addSubPara(rptNo ); 
    //prompt("strSQL3",strSQL3);

    turnPage2.queryModal(mySql.getString(),SubReportGrid);

    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
  }  
    
    /*var strSQL5 = "select reasoncode from Llappclaimreason where "
                + "RgtNo = '"+rptNo+"'";*/
   	mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql52");
	mySql.addSubPara(rptNo ); 
    var ReasonCode = easyExecSql(mySql.getString());
    
    if ( ReasonCode!=null&&ReasonCode!="")
    {      
      fm.occurReason.value=	ReasonCode[0][0].substring(0,1);      
      showCodeName('occurReason','occurReason','ReasonName');
    }    
    
    //��������������ʾ���ز�
    showDIV();
    //�������������ж��Ƿ��ѯƥ��������Ϣ
    if (fm.RgtConclusion.value == '01')
  {
      afterMatchDutyPayQuery();
        /*var strSQL4 = "select RgtState from llregister where "
                    + "rgtno = '"+rptNo+"'";*/
        mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql53");
	mySql.addSubPara(rptNo ); 
        var tRgtState = easyExecSql(mySql.getString());
        if (tRgtState)
        {
            fm.rgtType.value = tRgtState;
            showOneCodeName('rgtType','rgtType','rgtTypeTypeName');
        }
  }
}

//��ѯ����Ԥ�����
function getGrpstandpay(){
   // var strSQL3 = "select count(StandPay),sum(StandPay) from LLStandPayInfo  where caseno = '"+fm.RptNo.value+"'";
   mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql54");
	mySql.addSubPara(fm.RptNo.value); 
    var Grpstandpay = easyExecSql(mySql.getString());
    if(Grpstandpay[0][0] > 0){
    fm.Grpstandpay.value = Grpstandpay[0][1];
    }
  }

//��ѯҵ��Ա
function queryAgent()
{
    if(fm.AssigneeCode.value != "")
    {
       /* var strSQL = "select t.name,t.sex,t.phone,t.homeaddress,t.zipcode from laagent t where "
                    + "t.agentcode = '"+fm.AssigneeCode.value+"'";*/
        mySql = new SqlClass();
    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
	mySql.setSqlId("LLGrpClaimRegisterSql55");
	mySql.addSubPara(fm.AssigneeCode.value); 
        var tAgent = easyExecSql(mySql.getString());
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
        fm.SocialInsuFlag.value = "";
        fm.AccidentDate.value = "";
        fm.occurReason.value = "";
        fm.ReasonName.value = "";
        fm.hospital.value = "";
        fm.TreatAreaName.value = "";
        fm.AccidentDate2.value = "";
        fm.accidentDetail.value = "";
//        fm.IsDead.value = "";
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
    location.href="LLGrpClaimRegisterMissInput.jsp";
    if(fm.isClaimState.value == '1')
    {
     location.href="LLStateQueryInput.jsp";	
    }
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
        spanConclusion3.style.display="none";
        spanConclusion4.style.display="none";
        fm.dutySet.disabled = false;
        fm.medicalFeeCal.disabled = false;
        fm.printNoRgt.disabled = true;
        fm.printDelayRgt.disabled = false;
        fm.printPassRgt.disabled = false;
        fm.MedicalFeeInp.disabled = false;
  }
  else if (fm.RgtConclusion.value == '02')
  {
      //��ʾ��������ԭ���
        spanConclusion1.style.display="none";
        spanConclusion2.style.display="";
        spanConclusion3.style.display="none";
        spanConclusion4.style.display="none";
        fm.printNoRgt.disabled = false;
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = false;
        fm.printPassRgt.disabled = false;
        fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = true;
  }
    else if (fm.RgtConclusion.value == '03')
    {
        spanConclusion1.style.display="none";
        spanConclusion2.style.display="none";
        spanConclusion3.style.display="";
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = true;
        fm.printPassRgt.disabled = false;
        fm.printDelayRgt.disabled = false;
        fm.MedicalFeeInp.disabled = true;
        
        //var dSql="select DeferRgtRemark from llregister where rgtno='"+fm.ClmNo.value+"' "; 
        mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql56");
		mySql.addSubPara(fm.ClmNo.value); 
        var tDeferRgtRemark=easyExecSql(mySql.getString());
        
        if (tDeferRgtRemark!=null&&tDeferRgtRemark!="")
        {    	
        	spanConclusion4.style.display="";   //��ʾ�ӳ���������ӳ�������ע
        	fm.DeferRgtRemark.value=tDeferRgtRemark;
        }          
    }
    //var aSql="select auditidea from llclaimuwmain where clmno='"+fm.ClmNo.value+"' "; 
   mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql57");
		mySql.addSubPara(fm.ClmNo.value); 
    var tauditidea=easyExecSql(mySql.getString());
    
    if (tauditidea!=null&&tauditidea!="")
    {    	
    	spanLLClaimAudit.style.display=""; //��ʾ���˺��������
    	fm.AuditIdea.value=tauditidea;
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
   /* var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";*/
   mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql58");
		mySql.addSubPara(fm.AccidentDate.value);
		mySql.addSubPara(fm.customerNo.value);  
    var tAccNo = easyExecSql(mySql.getString());
    if (tAccNo == null)
    {
        alert("û������¼���");
        return;
    }
    //���¼���ѯ����
  var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����˲�ѯ
function showInsPerQuery()
{
    var strUrl = "LLGrpLdPersonQueryMain.jsp?GrpContNo="+fm.GrpContNo.value+"&GrpCustomerNo="+fm.GrpCustomerNo.value+"&GrpName="+fm.GrpName.value+"&ManageCom="+document.allManageCom.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�õ�0��icd10��
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//��ѯ���ս��2
function queryResult2()
{
    /*var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";*/
    mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql59");
		mySql.addSubPara(fm.AccResult2.value);
		
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        fm.AccResult2Name.value = tICDName;
    }
}

//��ӡ��֤ǩ���嵥,����ͨ��(01)ʱ����ʹ��-----���ߴ�ӡ�������ж�
function prtPassRgt()
{
    //�������Ƿ�Ϊ����ͨ��,�����ܴ�ӡ
    /*var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql60");
		mySql.addSubPara(fm.RptNo.value);
    var tResult = easyExecSql(mySql.getString());
    if (tResult == null)
    {
        alert("���ȱ�����������!");
        return;
    }
    if(fm.IsAllReady.value!="1"){
    	alert("��֤����ȫ���޷���ӡ��");
    	return;
    }

    //������д��ӡ�ύ����
    fm.action = './LLPRTCertificateSignforSave.jsp';
    fm.target = "../f1print";
    document.getElementById("fm").submit();
}

//��ӡ��������֪ͨ��,��������(02)ʱ����ʹ��
function prtNoRgt()
{
    //�������Ƿ�Ϊ��������,�����ܴ�ӡ
   /* var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql60");
		mySql.addSubPara(fm.RptNo.value);           
    var tResult = easyExecSql(mySql.getString());
    if (tResult != '02')
    {
        alert("���ȱ�����������!");
        return;
    }
    //������д��ӡ�ύ����
    fm.action = './LLPRTProtestNoRegisterSave.jsp';
    
    if(beforePrtCheck(fm.ClmNo.value,"","PCT007")==false)
    {
      return;
    }
//    fm.target = "../f1print";
//    document.getElementById("fm").submit();
}

//��ӡ���䵥֤֪ͨ��,�ӳ�����(03)ʱ����ʹ��
function prtDelayRgt()
{
    //�������Ƿ�Ϊ�ӳ�����,�����ܴ�ӡ
//    var strSQL = "select rgtconclusion from llregister where 1=1 "
//               + " and rgtno = '" + fm.RptNo.value + "'";
//    var tResult = easyExecSql(strSQL);
//    if (tResult != '03')
//    {
//        alert("���ȱ�����������!");
//        return;
//    }
    
	var row = SubReportGrid.getSelNo();	
	if(row < 1) {
	    alert("��ѡ��һ�м�¼��");
	    return;
	}
	
	var CustomerNo = SubReportGrid.getRowColData(row-1 ,1);
	fm.customerNo.value = CustomerNo;
	
    //������д��ӡ�ύ����
    fm.action = './LLPRTCertificateRenewSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,CustomerNo,"PCT003")==false)
    {
      return;
    }

    fm.target = "../f1print";
    document.getElementById("fm").submit();
    
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
    
	var CustomerNo = SubReportGrid.getRowColData(row-1 ,1);
	fm.customerNo.value = CustomerNo;

    fm.action = './LLPRTAppraisalSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,CustomerNo,"PCT001")==false)
    {
      return;
    }
//    fm.target = "../f1print";
//    document.getElementById("fm").submit();

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
//��ӡǰ���飨������Ҫ���루��֤���롢�ⰸ�ţ�--------2005-08-22���
***********************************/
function beforePrtCheck(clmno,CustomerNo,code)
{
  //��ѯ��֤��ˮ�ţ���Ӧ�������루�ⰸ�ţ����������͡���ӡ��ʽ����ӡ״̬�������־
     var tclmno=trim(clmno);
     var tCustomerNo =trim(CustomerNo);
     var tcode =trim(code);
     if(tclmno=="" ||tcode=="")
     {
       alert("������ⰸ�Ż�֤���ͣ����룩Ϊ��");
       return false;
     }
     
     var strSql="";
     
     if(tCustomerNo==null||tCustomerNo=="")
     {
    	 /*strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
    	        +" and t.otherno='" + tclmno + "'"
    	        +" and trim(t.code)='" + tcode + "'";*/
   		mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql61");
		mySql.addSubPara(tclmno);  
		mySql.addSubPara(tcode);  
     }
     else
     {
       	/* strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
 	        +" and t.otherno='" + tclmno + "'"
 	        +" and trim(t.code)='" + tcode + "'"
 	        +" and t.standbyflag4='" + tCustomerNo + "'";*/
 	     mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql62");
		mySql.addSubPara(tclmno);  
		mySql.addSubPara(tcode);  
		mySql.addSubPara(tCustomerNo);  
	 
     }
    
    
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
    fm.PrtSeq.value=arrLoprt[0][0];//��֤��ˮ��
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
//         window.open(strUrl,"��֤����");
         window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
         return false;
       }
      else
      {
    	  return false;
      }
    }
  }
}

//�жϲ�ѯ���봰�ڵİ��������Ƿ��ǻس���
//����ǻس����ò�ѯ�ͻ�����

function QueryOnKeyDown(at)
{
   var keycode = event.keyCode;
   //�س���ascii����13
   if(keycode=="13"||keycode=="9")
   {
     if(at == '1'){
      ClientQuery1();
      }else if(at == '2'){
      ClientQuery2();
      }else if(at == '3'){
      ClientQuery3();
      }else if(at == '4'){
      ClientQuery4();
      }
   }
}
//����ϸ����Ϣ��ѯ
function ClientQuery1()
{

   if ( fm.accidentDetailName.value == "")
   {
      alert("�������ѯ����");
      return false;
   }
   //var strSQL = " select ICDCode, ICDName from LDDisease where icdlevel <= 1 and ASCII(icdcode) >= 86 and ICDName like '%%"+fm.accidentDetailName.value+"%%' order by ICDCode";
   mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql63");
		mySql.addSubPara(fm.accidentDetailName.value);  
		
   var arr= easyExecSql(mySql.getString());
   if ( arr == null )
   {
     alert("û�з������������ݣ�");
     fm.accidentDetail.value = "";
     fm.accidentDetailName.value = "";
     return false;
   }
      try
      {
       //�����ѯ����������һ������ʾ��ҳ�棬����Ƕ���������
       //��һ��ҳ�棬��ʾ��mulline��
       if(arr.length==1)
       {
     fm.accidentDetail.value = arr[0][0];
     fm.accidentDetailName.value = arr[0][1];
       }else{
        var varSrc = "&accidentDetailName=" + fm.accidentDetailName.value;
        showInfo = window.open("./LLMainAccidentDetailQuery.jsp?Interface= LLAccidentDetailQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
       }

      }catch(ex)
      {
            alert("����:"+ ex.message);
      }
//showOneCodeName('idtype','tIDType','tIDTypeName');
}

function afterLLRegister(arr)
{
     fm.accidentDetail.value = arr[0][0];
     fm.accidentDetailName.value = arr[0][1];
}

//ҽԺ��Ϣ��ѯ
function ClientQuery2()
{

   if ( fm.TreatAreaName.value == "")
   {
      alert("�������ѯ����");
      return false;
   }
  // var strSQL = " select HospitalCode,HospitalName from LLCommendHospital where HospitalName like '%%"+ fm.TreatAreaName.value +"%%'";
  mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql64");
		mySql.addSubPara(fm.TreatAreaName.value);  
   var arr= easyExecSql(mySql.getString());
   if ( arr == null )
   {
     alert("û�з������������ݣ�");
     fm.hospital.value = "";
     fm.TreatAreaName.value = "";
     return false;
   }
      try
      {
       //�����ѯ����������һ������ʾ��ҳ�棬����Ƕ���������
       //��һ��ҳ�棬��ʾ��mulline��
       if(arr.length==1)
       {
     fm.hospital.value = arr[0][0];
     fm.TreatAreaName.value = arr[0][1];
       }else{
        var varSrc = "&HospitalName=" + fm.TreatAreaName.value;
        showInfo = window.open("./LLMainHospitalQuery.jsp?Interface= LLHospitalQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
       }

      }catch(ex)
      {
            alert("����:"+ ex.message);
      }
//showOneCodeName('idtype','tIDType','tIDTypeName');
}

function afterLLRegister2(arr)
{
     fm.hospital.value = arr[0][0];
     fm.TreatAreaName.value = arr[0][1];
}

//������Ϣ��ѯ
function ClientQuery3()
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
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql65");
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);  
		mySql.addSubPara(document.all('AllManageCom').value);  
      if(document.all('GrpName').value!=''){
         //strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
         mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql66");
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);  
		mySql.addSubPara(document.all('AllManageCom').value); 
		mySql.addSubPara(document.all('GrpName').value); 
      }
      //���ӶԻ������ж� ��½����ֻ�ܴ��������ĵ��� �ܻ������Դ���ֻ����ĵ��� 2006-02-18 P.D
     // strSQL += " and g.managecom like '"+document.all('AllManageCom').value+"%%'";
      arrResult=easyExecSql(mySql.getString());
       if(arrResult != null)//-----------------1
       {
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             
             //jixf del 20060614 ���ڿ�����������ֶζ���0�������������п���������
             //if(document.all('Peoples').value == null ||
             //   document.all('Peoples').value == "0")
             //{
             //   alert("Ͷ������Ϊ�գ�");
             //   return;
             //}
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

      }
       }//------------------1
      else{//---------------1
      //ȡ������������� 2006-02-18 P.D
      //var SQLEx = "select ExecuteCom From lcgeneral where grpcontno='"+document.all('GrpContNo').value+"'";
      mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql67");
		mySql.addSubPara(document.all('GrpContNo').value);  
		
      var tExecuteCom = new Array();
      var tExecuteCom = easyExecSql(mySql.getString());
      if(tExecuteCom != null){//--------------2
      for(var i = 0;i<tExecuteCom.length;i++){//-------------2.1
      //�жϵ�½�����Ƿ��Ǳ���ָ������
      if(tExecuteCom[i][0] == document.all('AllManageCom').value){//-------2.2
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a ,lcgeneral b " +
      " where a.grpcontno = g.grpcontno and g.grpcontno = b.grpcontno and g.appflag in ('1','4') "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
      	mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql68");
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);  
		mySql.addSubPara(tExecuteCom[i][0]);  
      if(document.all('GrpName').value!=''){
         //strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
         mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql69");
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);  
		mySql.addSubPara(tExecuteCom[i][0]);  
		mySql.addSubPara(document.all('GrpName').value);  
      }
        // strSQL += " and b.ExecuteCom = '"+tExecuteCom[i][0]+"'";
      var arrResult = new Array();
      arrResult = easyExecSql(mySql.getString());
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
     }//-------2.2
    }//-------------2.1
   }//------------------2
       else
       {//----------------3
         //**********yaory ��ѯ�������û�н�����ڱ��� ��������Ҫ�����뱣����
        /* var tsql="select AppntName from LZNoNameCard where grpcontno='"+document.all('GrpContNo').value+"'"
                 +" and managecom like '"+document.all('AllManageCom').value+"%%'";*/
         mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql70");
		mySql.addSubPara(document.all('GrpContNo').value);  
		mySql.addSubPara(document.all('AllManageCom').value);  
		
         arrResult=easyExecSql(mySql.getString());
         if(arrResult==null)
         {
         alert("û�з������������ݣ�");
         document.all('GrpCustomerNo').value = "";
         document.all('GrpName').value = "";
         document.all('GrpContNo').value = "";
         document.all('Peoples').value = "";
         return;
        }else{
        try{document.all('GrpCustomerNo').value="000000"}catch(ex){alert(ex.message+"GrpCustomerNo")}
        try{document.all('GrpName').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpName")}
        //��ѯ����
      //  tsql="select count(*) from lcinsured where grpcontno='"+document.all('GrpContNo').value+"'";
          mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql71");
		mySql.addSubPara(document.all('GrpContNo').value);  
		 
         arrResult=easyExecSql(mySql.getString());
         if(arrResult!=null)
         {
           try{document.all('Peoples').value=arrResult[0][0]}catch(ex){alert(ex.message+"Peoples")}
         }
      }
    }//----------------3
  }//------------------1
   }
   else
   {
      alert("�������ѯ����");
   }
}

//����ϸ����Ϣ��ѯ
function ClientQuery4()
{

   if ( fm.AccResult2Name.value == "")
   {
      alert("�������ѯ����");
      return false;
   }
   var strSQL;
   if (document.all('occurReason').value==null||document.all('occurReason').value=="")
   {
      alert("��ѡ�����ԭ��");      
      return false;	
   }
   if (document.all('occurReason').value=='1')
   {
      //	strSQL = "select ICDCode,ICDName from LDDisease where icdlevel = 1 and ASCII(icdcode) < 86 and ASCII(icdcode) > 82 and ICDName like '%%"+fm.AccResult2Name.value+"%%' order by ICDCode";
   		 mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql72");
		mySql.addSubPara(fm.AccResult2Name.value);  
   }else 
   {
      //	strSQL = "select ICDCode,ICDName from LDDisease where icdlevel = 1 and ASCII(icdcode) < 83 and ICDName like '%%"+fm.AccResult2Name.value+"%%' order by ICDCode";
   		 mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql73");
		mySql.addSubPara(fm.AccResult2Name.value);  
   }
   var arr= easyExecSql(mySql.getString());
   if ( arr == null )
   {
     alert("û�з������������ݣ�");
     fm.AccResult2.value = "";
     fm.AccResult2Name.value = "";
     return false;
   }
      try
      {
       //�����ѯ����������һ������ʾ��ҳ�棬����Ƕ���������
       //��һ��ҳ�棬��ʾ��mulline��
       if(arr.length==1)
       {
         fm.AccResult2.value = arr[0][0];
         fm.AccResult2Name.value = arr[0][1];
       }else{
        var tAccResult2Name = fm.AccResult2Name.value;
        var toccurReason = fm.occurReason.value;
       
        //var varSrc = "&AccResultName="+tAccResult2Name+"&occurReason="+toccurReason;
        //showInfo = window.open("./LLMainAccidentDetailQuery.jsp?Interface= LLAccidentDetailQuery.jsp"+varSrc,"LLAccidentDetailQuery",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');

        //location.href="LLAccidentDetailQuery.jsp?AccResult2Name="+AccResult2Name+"&occurReason="+occurReason;
        
        var strUrl="LLAccidentDetailQuery.jsp?AccResultName="+tAccResult2Name+"&occurReason="+toccurReason;
        window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

       }

      }catch(ex)
      {
            alert("����:"+ ex.message);
      }
//showOneCodeName('idtype','tIDType','tIDTypeName');
}
function afterLLRegister4(arr)
{
     fm.AccResult2.value = arr[0][0];
     fm.AccResult2Name.value = arr[0][1];
}

function afterLCGrpQuery(arrReturn)
{
     document.all('GrpCustomerNo').value=arrReturn[0][0];
     document.all('GrpName').value=arrReturn[0][1];
     document.all('GrpContNo').value=arrReturn[0][2];
     document.all('Peoples').value=arrReturn[0][3];
}


//�򿪷������
function showBeginInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    //var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000005125','0000005145','0000005165','0000005175') and missionprop1='"+fm.RptNo.value+"'";
    	mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql74");
		mySql.addSubPara(fm.RptNo.value);  
    var JustStateCount=easyExecSql(mySql.getString());
    if(parseInt(JustStateCount)>0)
    {      				
    		alert("�ð����Ѿ�������飬�벻Ҫ�ظ�����!");
    		return;
    }    
    var strUrl="../claim/LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&custVip="+fm.IsVip.value+"&initPhase=01";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//Ԥ�������Ϣ¼��
function operStandPayInfo()
{
   var tSel = SubReportGrid.getSelNo();
/*
   if( tSel == 0 || tSel == null ){
        alert( "���ڳ�������Ϣ��ѡ��һ����¼" );
        return false;
     }
else {
     customerNo = SubReportGrid.getRowColData(tSel-1 ,1);
    }
*/
     var varSrc ="";
     var CaseNo = fm.RptNo.value;//
     var customerName = fm.customerName.value;
     var customerNo=fm.customerNo.value;
     pathStr="./StandPayInfoMain.jsp?CaseNo="+CaseNo+"&customerName="+customerName+"&customerNo="+customerNo;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

//����ת���������ڼ��ж�
function TempCustomer(){
//        var StrSQL = "select grpcontno From lctempcustomer where startdate <= '"+fm.AccidentDate.value+"' and paytodate >= '"+fm.AccidentDate.value+"' and grpcontno = '"+fm.GrpContNo.value+"'";
//        var arr= easyExecSql(StrSQL);prompt("",StrSQL);
//        if(arr != null ){
        return true;
//        }else{
//        return false;
//        }
}
//�жϱ����ڼ�
function showDate(){
        //jixf add 20051213
       // var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
       // strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
        mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql75");
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.customerNo.value);  
         if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
         {
        // strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
         mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql76");
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.customerNo.value);  
		mySql.addSubPara(fm.GrpCustomerNo.value); 
		 
         }
        if (fm.GrpContNo.value!=null)
        {
        //strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
        mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql77");
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

//�������ⰸ�����г����˼����⸶��Ϣ
function getLastCaseInfo(){
var CaseNo = fm.RptNo.value;
var GrpContNo = fm.GrpContNo.value
if(CaseNo == "" || CaseNo == null){
	alert("���ȵ㱣�棡");
	return false;
}else{
	var row = SubReportGrid.getSelNo();
    if(row < 1) {
        alert("��ѡ��һ�м�¼��");
        return;
    }
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//alert("bb");
//  var tSQL = "select a.clmno,a.grpcontno,a.insuredno,a.insuredname,a.getdutykind,"
// + " b.accdate,b.endcasedate,a.riskcode,a.standpay," 
// + "a.realpay from llclaimpolicy a,llcase b "
// +"where  a.clmno=b.caseno and a.insuredno=b.customerno  and b.rgtstate='60' and "
// +" a.insuredno in (select distinct customerno from llsubreport where subrptno='"
//+ CaseNo + "' ) and a.grpcontno = '" + GrpContNo + "'";
	/*var tSQL = "select a.clmno,a.grpcontno,a.insuredno,a.insuredname,a.getdutykind,"
		 + " b.accdate,b.endcasedate,a.riskcode,a.standpay," 
		 + "a.realpay from llclaimpolicy a,llcase b "
		 +"where  a.clmno=b.caseno and a.insuredno=b.customerno "
		 //+" and b.rgtstate='60'"
		 +" and "
		 +" a.insuredno = '"+ tCustomerNo +"'"
	     +" and a.clmno <>'"+CaseNo+"'";
		 //+" and a.grpcontno = '" + GrpContNo + "'";*/
	//prompt("",tSQL);
	 mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql78");
		mySql.addSubPara(tCustomerNo);  
		mySql.addSubPara(CaseNo);  
	 
		  var arr = easyExecSql(mySql.getString());
		  if(!arr){
			  alert("δ��ѯ�������ⰸ��Ϣ���޷�����");
			  return false;
		  }
fm.tSQL.value = tSQL;
  var Title="�����ⰸ��Ϣ��ѯ";
	var SheetName="�����ⰸ��Ϣ��ѯ";       
	var ColName = "�ⰸ��@���屣����@�ͻ���@�ͻ�����@��������@��������@�᰸����@���ִ���@Ӧ�⸶���@ʵ���⸶���";
	fm.action = "./PubCreateExcelSave.jsp?tSQl="+tSQL+"&Title=�����ⰸ��Ϣ��ѯ"+"&SheetName="+Title+"&ColName="+ColName;
	fm.target="../claimgrp";		 	
	document.getElementById("fm").submit();
}   
}

function saveRgtMAffixListAll()
{
  
 
        /*strSQL="select affixno,affixcode,affixname,subflag,(case needflag when '0' then '0-��' when '1' then '1-��'  end ),readycount,shortcount,property from llaffix where 1=1 and"
               + " rgtno='"+fm.ClmNo.value+"' and"
     
             + " (subflag is null or subflag='1')";  */
      mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql79");
		mySql.addSubPara(fm.ClmNo.value);  
		  
      arr=easyExecSql(mySql.getString());
        if(arr!=null)
        {
          if (confirm("��ȷʵ����������������ϣ�"))
           {
             fm.Operate.value="Rgt||UPDATE";
             fm.action="LLRgtMAffixListSaveAll.jsp";
             document.getElementById("fm").submit();
           }  
        }else{
      alert("û��Ҫ��������������!");
      }
  
}

function afterSubmit4( FlagStr, content )
{
    //showInfo.close();
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

        returnParent();    //[����]��ť   
    }
}

function LLQueryUWMDetailClick()
{
//  alert("��������������ѯ");
    var strUrl="LLColQueryClaimUWMDetailMain.jsp?ClmNo="+fm.ClmNo.value;
//    window.open(strUrl,"��������������ѯ");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//�������ο���
function ctrlclaimduty()
{
    var strUrl="../appgrp/CtrlClaimDuty.jsp?ProposalGrpContNo="+fm.GrpContNo.value+"&GrpContNo="+fm.GrpContNo.value+"&LoadFlag=2&LPFlag=1";
    //  window.open(strUrl,"��������������ѯ");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

    //showInfo = window.open();
    //parent.fraInterface.window.location = "../appgrp/CtrlClaimDuty.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo="+fm.GrpContNo.value+"&ProposalGrpContNo="+tGrpContNo+"&LoadFlag="+LoadFlag;
}

function QueryRgtState(){
	var tmissionid=fm.MissionID.value;
	//var tRgtSql="select missionprop15 from lwmission where activityid='0000009015' and missionid='"+tmissionid+"'";
	mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql80");
		mySql.addSubPara(tmissionid); 
	var tRgtState = easyExecSql(mySql.getString());
	fm.RgtState.value = tRgtState;
}

//����ϸ�ڲ�ѯ
function showAccDetail(tCode,tName)
{//alert(3157);
	var strUrl="../claim/LLColQueryAccDetailInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//ҽԺģ����ѯ
function showHospital(tCode,tName)
{
	var strUrl="../claim/LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//zy 2009-07-28 17:43 ��ȡ������Ʒ������������Ϣ
function getLLEdorTypeCA()
{

	 //var tAccRiskCodeSql="select distinct riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"' and riskcode='211901' and grpname like 'MS���ٱ��չɷ����޹�˾%'";
	mySql = new SqlClass();
	    mySql.setResourceName("claimgrp.LLGrpClaimRegisterSql");
		mySql.setSqlId("LLGrpClaimRegisterSql81");
		mySql.addSubPara(fm.GrpContNo.value); 
	 //prompt("",tAccRiskCodeSql);
   var tAccRiskCode=easyExecSql(mySql.getString());
   if(tAccRiskCode=="211901")
   {
   	fm.AccRiskCode.value=tAccRiskCode;
   }
   else
   {
   	fm.AccRiskCode.value="";   
   }

}

//zy 2009-07-28 14:58 �˻��ʽ����
function ctrllcinsureacc()
{
	
    var strUrl="./LLGrpClaimEdorTypeCAMain.jsp?GrpContNo="+fm.GrpContNo.value+"&RptNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

function checkCurrency()
{
	if(fm.Currency.value!=null && fm.Currency.value!="")
    	{
	    	fm.RealPay.moneytype=fm.Currency.value;
    	}
}
