//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
var mOperate;

/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ��ҳ���ʼ���Ĳ�ѯ
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
    +" and (a.AppntNo in ('"+fm.CustNo.value+"') or InsuredNo in ('"+fm.CustNo.value+"'))"
   =========================================================================
**/


function initQuery()
{
    //��ʾ�⸶δ�漰�ĺ�ͬ��Ϣ
    /*var strSql = " select a.GrpContNo,a.ContNo,"
       +" case a.ContType when '1' then '����' when '2' then '����' end , "
       +" a.AppntNo,a.AppntName,a.AppntSex,a.AppntBirthday, "
       +" a.InsuredNo,a.InsuredName,a.InsuredSex,a.InsuredBirthday, "
       +" a.PolApplyDate,a.SignCom,a.SignDate,a.CValiDate, "
       +" case a.AppFlag when '0' then 'Ͷ��' when '1' then '�б�' when '2' then '����' end  "
       +" from LCCont a where 1=1 "
       +" and a.ContNo not in (select distinct ContNo from LLClaimPolicy where ClmNo='"+fm.ClmNo.value+"')"
       +" and (a.AppntNo in ('"+fm.CustNo.value+"') or InsuredNo in ('"+fm.CustNo.value+"'))"*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimContDealInputSql");
	mySql.setSqlId("LLClaimContDealSql1");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(fm.CustNo.value ); 
	mySql.addSubPara(fm.CustNo.value ); 
    //prompt("�⸶δ�漰�ĺ�ͬ",strSql);
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,ContNoReferGrid);
        //arr[0][0]==null||arr[0][0]=='null'?'0':fm.PreGiveAmnt.value  = arr[0][0];
    }


    //       +"(case (select conbalflag from llclaim where clmno = '"+fm.ClmNo.value+"') when '1' then '�����ѽ���' else '����δ����' end) "
    //��ʾ�⸶�漰�ĺ�ͬ��Ϣ
    /*var strSqlA = " select distinct a.GrpContNo,a.ContNo,"
       +" case a.ContType when '1' then '����' when '2' then '����' end , "
       +" a.AppntNo,a.AppntName,a.AppntSex,a.AppntBirthday, "
       +" a.InsuredNo,a.InsuredName,a.InsuredSex,a.InsuredBirthday, "
       +" a.PolApplyDate,a.SignCom,a.SignDate,a.CValiDate, "
       +" case a.AppFlag when '0' then 'Ͷ��' when '1' then '�б�' when '2' then '����' when '4' then '��ֹ' when '9' then '����' end,  "
       +" '0',"
       +" '�ⰸ���'"
       +" from LCCont a where 1=1 "
       +" and a.ContNo in (select distinct ContNo from LLClaimPolicy where ClmNo='"+fm.ClmNo.value+"' and GiveType in ('0','1'))"
       ;

    var strSqlB = " select distinct a.GrpContNo,a.ContNo,"
       +" case a.ContType when '1' then '����' when '2' then '����' end , "
       +" a.AppntNo,a.AppntName,a.AppntSex,a.AppntBirthday, "
       +" a.InsuredNo,a.InsuredName,a.InsuredSex,a.InsuredBirthday, "
       +" a.PolApplyDate,a.SignCom,a.SignDate,a.CValiDate, "
       +" case a.AppFlag when '0' then 'Ͷ��' when '1' then '�б�' when '2' then '����' when '4' then '��ֹ' when '9' then '����' end,  "
       +" '0',"
       +" '�ⰸ�޹�'"
       +" from LCCont a where 1=1 "
       +" and a.ContNo in (select distinct ContNo from LLCUWBatch where CaseNo='"+fm.ClmNo.value+"' and InEffectFlag in ('1','2') and State = '1' and ClaimRelFlag='1')"
       ;
       
       
    var strSqlC = strSqlA + " union " + strSqlB;*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimContDealInputSql");
	mySql.setSqlId("LLClaimContDealSql2");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(fm.ClmNo.value ); 
    //var strSqlC = strSqlA;
    //prompt("�⸶�漰�ĺ�ͬ",strSqlC);
    var brr = easyExecSql(mySql.getString());

    //fm.NewAdjRemark.value = strSqlC;
    if (brr)
    {
        displayMultiline(brr,ContReferGrid);
    }


}

/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ�򣺵���ѡťѡ���ⰸ�漰��ͬ��MutiLineʱ�����ĺ���
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function getContReferGrid(spanID,parm)
{
    divContDeal.style.display="";
    divContCalResult.style.display="";

    divContPolRefer.style.display="";
    divLLContState.style.display="";
    divContPolDeal.style.display="";

    var tNo = ContReferGrid.getSelNo();
    var tContNo = ContReferGrid.getRowColData(tNo - 1,2);//��ͬ��

    fm.ContNo.value = tContNo;  //����ͬ�Ÿ�ֵ�����صı���

    getLCPol(tContNo);          //�õ�������Ϣ
    getLLContState(tContNo,'null');
    getLLBalance(tContNo);      //���ò�ѯ�������ķ���


    /*strSql = " select a.dealstate,"
       +" (select c.codename from ldcode c where c.codetype = 'llcontdealtype' and trim(c.code)=trim(a.dealstate)) "
       +" from LLContState a where 1=1 "
       +" and a.ClmNo  in ('"+fm.ClmNo.value+"')"
       +" and a.ContNo  in ('"+tContNo+"')";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimContDealInputSql");
	mySql.setSqlId("LLClaimContDealSql3");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(tContNo); 
//    alert(strSql);
    arr = easyExecSql(mySql.getString());
    if (arr)
    {
        //arr[0][0]==null||arr[0][0]=='null'?'0':fm.StateReason.value  = arr[0][0];
        //arr[0][1]==null||arr[0][1]=='null'?'0':fm.StateReasonName.value  = arr[0][1];
    }
    else
    {
        fm.StateReason.value  = "";
        fm.StateReasonName.value  = "";
    }

    fm.PolStateReason.value  = "";
    fm.PolStateReasonName.value  = "";
}


/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ�򣺵���ѡťѡ�����ֵ�MutiLineʱ�����ĺ���
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function getContPolReferGrid(spanID,parm)
{

    var tNo = ContPolReferGrid.getSelNo();
    var tContNo = ContPolReferGrid.getRowColData(tNo - 1,3);//��ͬ��
    var tPolNo = ContPolReferGrid.getRowColData(tNo - 1,4);//���ֺ�

    fm.ContNo.value = tContNo;  //����ͬ�Ÿ�ֵ�����صı���
    fm.PolNo.value = tPolNo;  //����ͬ�Ÿ�ֵ�����صı���



    fm.PolStateReason.value  = "";
    fm.PolStateReasonName.value  = "";

    getLLContState(tContNo,tPolNo,'Y');
    getLLBalance(tContNo);
}


/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ�򣺵���ѡťѡ�н�����Ϣ��MutiLineʱ�����ĺ���
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function getContCalResultGrid(spanID,parm)
{

    var tNo = ContCalResultGrid.getSelNo();
    divContCalResultAdjust.style.display="";

    var tPolNo = ContCalResultGrid.getRowColData(tNo - 1,5);//���ֺ�
    var tFeeOperationType = ContCalResultGrid.getRowColData(tNo - 1,1);//ҵ������
    var tSubFeeOperationType = ContCalResultGrid.getRowColData(tNo - 1,3);//��ҵ������
    var tNewPay = ContCalResultGrid.getRowColData(tNo - 1,9);//��ǰ���
    var tOldPay = ContCalResultGrid.getRowColData(tNo - 1,10);//ԭʼ���
    var tOldAdjRemark = ContCalResultGrid.getRowColData(tNo - 1,11);//��ʷ����ԭ��

    fm.PolNo.value = tPolNo;  //����ͬ�Ÿ�ֵ�����صı���
    fm.FeeOperationType.value = tFeeOperationType;
    fm.SubFeeOperationType.value = tSubFeeOperationType;
    fm.NewPay.value = tNewPay;
    fm.OldPay.value = tOldPay;
    fm.OldAdjRemark.value = tOldAdjRemark;
    fm.Currency.value = ContCalResultGrid.getRowColData(tNo - 1,8);


    fm.PolStateReason.value  = "";
    fm.PolStateReasonName.value  = "";
}




/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ�򣺵õ�LCPol���ֵ���Ϣ
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/

function getLCPol(pContNo)
{
    //ȡ�������¹ʷ�������
    //var tSql = "select accidentdate from llregister where rgtno = '" + fm.ClmNo.value + "'";
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimContDealInputSql");
	mySql.setSqlId("LLClaimContDealSql4");
	mySql.addSubPara(fm.ClmNo.value ); 
    var tAccDate = easyExecSql(mySql.getString());
	
   /* var strSqlA = " select a.GrpContNo,a.GrpPolNo,a.ContNo,a.PolNo,a.ContType,"
       +" a.MainPolNo,a.RiskCode,"
       +" a.InsuredNo,a.InsuredName,a.InsuredSex,a.InsuredBirthday,"
       +" '',a.SignCom,a.SignDate,a.CValiDate,"
       +" case a.AppFlag when '0' then 'Ͷ��' when '1' then '�б�' when '4' then '��ֹ' when '9' then '����' end  ,"
       +" 1 "
       +" from LCPol a where 1=1 "
       +" and a.ContNo in ('" + pContNo + "')"
       +" and a.PolNo = a.MainPolNo"       
       //��Ϊ��ʾ��������֮�����������������ֹ���
       +" and (a.paytodate >= to_date('" + tAccDate + "') or a.appflag in ('1','4'))"
       ;
    
    var strSqlB = " select a.GrpContNo,a.GrpPolNo,a.ContNo,a.PolNo,a.ContType,"
       +" a.MainPolNo,a.RiskCode,"
       +" a.InsuredNo,a.InsuredName,a.InsuredSex,a.InsuredBirthday,"
       +" '',a.SignCom,a.SignDate,a.CValiDate,"
       +" case a.AppFlag when '0' then 'Ͷ��' when '1' then '�б�' when '4' then '��ֹ' when '9' then '����' end, "
       +" 2 "       
       +" from LCPol a where 1=1 "
       +" and a.ContNo in ('" + pContNo + "')"
       +" and a.PolNo <> a.MainPolNo"              
       //��Ϊ��ʾ��������֮�����������������ֹ���
       +" and (a.paytodate >= to_date('" + tAccDate + "') or a.appflag in ('1','4'))"
       +" order by 16,17"
       ;
       

    var strSql = strSqlA + " union " + strSqlB;*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimContDealInputSql");
	mySql.setSqlId("LLClaimContDealSql5");
	mySql.addSubPara(pContNo); 
	mySql.addSubPara(tAccDate); 
	mySql.addSubPara(pContNo ); 
	mySql.addSubPara(tAccDate); 
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,ContPolReferGrid);
    }
    else
    {
        initContPolReferGrid();         //������
    }
}

/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ�򣺵õ�LLContState����Ϣ
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/

function getLLContState(pContNo,pPolNo,pType)
{
//    alert(pContNo);
   /* var strSql = " select a.ContNo,a.InsuredNo,a.PolNo,a.StateType,"
       +" a.State, case a.State when '0' then '��Ч' when '1' then '��ֹ' end,"
       +" a.StartDate,a.EndDate,"
       +" a.DealState, "
       +" (select c.codename from ldcode c where c.codetype in ('llcontpoldealtype','llcontdealtype') and trim(c.code)=trim(a.DealState)),"
       +" a.ClmState,case a.ClmState when '0' then '��ͬ����' when '1' then '�ⰸ����' when '2' then '��ͬ�ⰸ����' end "
       +" from LLContState a where 1=1 "
       +" and a.ClmNo in ('"+fm.ClmNo.value+"') "
       +" and a.ContNo in ('"+pContNo+"') "
       +" and a.StateType in ('Terminate') "
       +" and a.ClmState not in ('9') "
       ;*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimContDealInputSql");
	mySql.setSqlId("LLClaimContDealSql6");
	mySql.addSubPara(fm.ClmNo.value); 
	mySql.addSubPara(pContNo); 

	
    if ( pType == "Y" )
    {
       //strSql = strSql + " and a.PolNo in ('"+pPolNo+"')"
       mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimContDealInputSql");
		mySql.setSqlId("LLClaimContDealSql7");
		mySql.addSubPara(fm.ClmNo.value); 
		mySql.addSubPara(pContNo); 
		mySql.addSubPara(pContNo); 
    }

    //strSql = strSql + " order by a.PolNo "

    //alert(strSql);
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,LLContStateGrid);
    }
    else
    {
        initLLContStateGrid();         //
    }
}


/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ�򣺵õ�LLBalance��ͬ��ֹ�ļ�����
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function getLLBalance(pContNo)
{
    //alert(pContNo);
   /* var strSql = " select a.FeeOperationType,"
       +" (select c.codename from ldcode c where c.codetype = 'llcontdealtype' and trim(c.code)=trim(a.FeeOperationType)), "
       +" a.SubFeeOperationType,"
       +" (select f.SubBalTypeDesc from LLBalanceRela f where f.SubBalType=a.SubFeeOperationType),"
       +" a.PolNo,RiskCode,GetDate,a.Pay, "
       +" a.OriPay,a.AdjRemark "
       +" from LLBalance a where 1=1 "
       +" and a.ClmNo  in ('"+fm.ClmNo.value+"')"
       +" and a.ContNo in ('"+pContNo+"')"
       +" and substr(a.FeeOperationType,1,1) in ('C','D') "*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimContDealInputSql");
		mySql.setSqlId("LLClaimContDealSql8");
		mySql.addSubPara(fm.ClmNo.value); 
		mySql.addSubPara(pContNo); 
		
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,ContCalResultGrid);
    }
    else
    {
        initContCalResultGrid();         //������
    }
}


/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ��ִ�б�����۲�����Ĺ���
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function saveContAutoClick()
{

    mOperate="CONTAUTO";
    fm.action = "./LLClaimContPolDealAutoSave.jsp";
    //alert("�Զ�"+fm.action);
    submitForm();
}

/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ��ִ�б�����۲�����Ĺ���
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function saveAndCalClick()
{
    mOperate="CONT";
    fm.action = "./LLClaimContDealSave.jsp";
    submitForm();
}

/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ��ִ�����ֽ��۱���Ĺ���
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function saveContPolClick()
{
    var tNo = ContPolReferGrid.getSelNo();
    if (tNo<1)
    {
        alert("����ѡ��һ��������Ϣ,��ִ�д˲���!");
        return;
    }
    mOperate="POL";
    fm.action = "./LLClaimContPolDealSave.jsp";
    submitForm();
}

/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ�򣺱����ͬ������ۿ�ʼʱ��ĺ���
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function saveLLContStateAdjustDate()
{

    var tNo = LLContStateGrid.getSelNo();
    if (tNo<1)
    {
        alert("����ѡ��һ����ͬ��ֹ��Ϣ,��ִ�д˲���!");
        return;
    }


    fm.ContNo.value = LLContStateGrid.getRowColData(tNo-1,1);;
    fm.PolNo.value = LLContStateGrid.getRowColData(tNo-1,3);;
    fm.ContStaDate.value = LLContStateGrid.getRowColData(tNo-1,7);;

    
    
    mOperate="AdjustDate";
    fm.action = "./LLClaimContPolDealAdjustPaySave.jsp";
    submitForm();

}


/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ�򣺵���������ʱ�����ĺ���
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function saveContCalResultGridAdjust()
{

    var tNo = ContCalResultGrid.getSelNo();
    if (tNo<1)
    {
        alert("����ѡ��һ��������Ϣ,��ִ�д˲���!");
        return;
    }

    var tNewPay = fm.NewPay.value;

    if (tNewPay==null || tNewPay=="")
    {
        alert("���������Ϊ��!!!");
        return;
    }
    mOperate="AdjustPay";
    fm.action = "./LLClaimContPolDealAdjustPaySave.jsp";
    submitForm();

}

/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ�򣺹�������--�ύ����̨����
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/

function submitForm()
{
    var showStr="���ڱ�����۲����ɼ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.hideOperate.value=mOperate;
    document.getElementById("fm").submit(); //�ύ

}

/**=========================================================================
    �޸�״̬���½�
    �޸�ԭ�򣺹�������--Saveҳ��--���������ݷ��غ�ִ�еĲ���
    �� �� �ˣ�����
    �޸����ڣ�2005.07.14
   =========================================================================
**/
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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
        mOperate = '';
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

        getLLBalance(fm.ContNo.value);
        if (fm.hideOperate.value=='POL')
        {
            getLLContState(fm.ContNo.value,fm.PolNo.value,'Y');
        }
        else
        {
            getLLContState(fm.ContNo.value,fm.PolNo.value);
        }

        //�����ͬ��������,ˢ�º�ͬ������Ϣ
        if (fm.hideOperate.value=='BalAdjust')
        {
            getLLBalance(fm.ContNo.value)
        }


    }

}
