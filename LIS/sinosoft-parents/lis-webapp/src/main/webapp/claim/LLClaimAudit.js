var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
var tTLFlag="0";

//��֤���õ���
function showLLMedicalFeeAdj()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;     //�ͻ���

    //alert(tClaimNo);
    var strUrl="LLMedicalFeeAdjMain.jsp?RptNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//���б�������ƥ��
function showMatchDutyPay2(tAccNo)
{  
	fm.AccNo.value=tAccNo;
	//alert(fm.AccNo.value);
	
	if(!KillTwoWindows(fm.ClmNo.value,'30'))
	{
	  	  return false;
	}	 

	//alert("��ʼƥ�䲢����");
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
    	afterMatchDutyPayQuery();
    }
}

//��ʼ��ʱ���б�������ƥ��
function initShowMatchDutyPay()
{  	
	if(!KillTwoWindows(fm.ClmNo.value,'30'))
	{
	  	  return false;
	}	 
	
    //�ж��Ƿ���Ԥ�������,�����ʼ����������,û������������
   /* var strSql = "select k from ("
    			+ "select 1 k from llprepayclaim where "
                + "clmno = '" + fm.RptNo.value + "'"
                + " union "
                + " select 1 k from llcaseback where "
                + "clmno = '" + fm.RptNo.value + "')"*/
   mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql1");
	mySql.addSubPara(fm.RptNo.value );  
	mySql.addSubPara(fm.RptNo.value );       
    //prompt("�жϰ����Ƿ����Ԥ�������:",strSql);
    
    var tFlag = easyExecSql(mySql.getString());

    if (!(tFlag == null||tFlag == ""))
    {
    	//�ж��Ƿ��Ѿ������������˷���,���򲻽����������㣡
    	//strSql=" select 1 from llbnf where clmno='"+fm.RptNo.value+"' and bnfkind='A'";
    	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql2");
		mySql.addSubPara(fm.RptNo.value );  

    	tFlag = easyExecSql(mySql.getString());

    	//Ϊ�ձ�־û�м�¼
    	if(tFlag == null||tFlag == "")
    	{
        	//alert("tFlag:"+tFlag);
        	fm.hideOperate.value="MATCH||INSERT";
        	fm.action = "./LLClaimRegisterInitMatchCalSave.jsp";
        	fm.target="fraSubmit";
        	document.getElementById("fm").submit(); //�ύ
    	}

    }
    else
    {
    	//ֱ�Ӳ�ѯ������
        afterMatchDutyPayQuery();
    }

}


//���ƥ�䲢���㰴ť��Ķ���
function afterInitMatchDutyPay(FlagStr, content)
{
	//����������ʾ,����ִ�н��۱���

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
    }

    afterMatchDutyPayQuery();

}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ��������˽׶Σ�����ƥ�䣬���㣬ȷ�ϵȺ���������
    �� �� �ˣ�����
    �޸����ڣ�2005.06.01
   =========================================================================
**/

//���б�������ƥ��
function showMatchDutyPay()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	  
	  //��Ͷ�����Ƿ���б�����������ж�
	  /*var tSql=" select count(*) from lmrisktoacc c, lmriskinsuacc r "
  		  + " where r.insuaccno = c.insuaccno and r.acckind = '2' "
  			+ " and c.riskcode in (select riskcode from LLClaimPolicy where "
             + " caseno = '" + fm.RptNo.value + "')";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql68");
	mySql.addSubPara(fm.RptNo.value );
  	var count=easyExecSql(mySql.getString());
		if(count>0)
		{
			tTLFlag="1";
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql69");
			mySql.addSubPara(fm.RptNo.value );
			var countLP=easyExecSql(mySql.getString());
			if(!countLP)
			{
				alert("���Ƚ��н���!");
				return;
			}
			var ifGoon=false;//�Ƿ�����˼Ƽ� Ĭ��Ϊû����
			if(countLP[0][0]=='2'&&countLP[0][1])
			{
				ifGoon=true;
			}
			if(!ifGoon)
			{
				alert("��ȴ��Ƽ۴������!");
				return;
			}
		}
		
    mOperate="MATCH||INSERT";
    var showStr="���ڽ��б�������ƥ������������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
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
   /* tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql3");
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
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql4");
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
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql5");
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
   /* tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetClm c where trim(c.GetDutyKind) = trim(a.GetDutyKind) and trim(c.GetDutyCode) = trim(a.GetDutyCode)),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0),"
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
       +" a.dutycode,a.customerno"
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode" 
       +" and a.ClmNo = '"+tClaimNo+"'"   
       +" and a.GiveType not in ('2')"
       ;*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql6");
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

//¼��ҽ�Ƶ�֤��Ϣ
function showLLMedicalFeeInp()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }
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
    var strUrl="LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.MedicalAccidentDate.value+"&otherAccDate="+fm.OtherAccidentDate.value;
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
    �޸�ԭ��������˽׶Σ�����ƥ�䣬���㣬ȷ�ϵȺ���������
    �� �� �ˣ�����
    �޸����ڣ�2005.06.01
   =========================================================================
**/

//�������
function showSecondUWInput()
{
//����������15�첻�ܷ�����˵�У�� ���ݶ�Ϊ15�죩
//	var tClmNo = fm.RptNo.value;
//	var tJudgeSql = "select 1 from dual where sysdate-(select makedate from llregister a where a.rgtno='"
//					+ tClmNo +"')>=15";
//	var whetherUW = easyExecSql( tJudgeSql );
//	if(whetherUW){
//		if(whetherUW=="1"){
//			alert("�������ڵ���ǰ�����ѳ���15�죬���ܷ�����ˣ�");
//			return false;
//		}
//	}
	var tClmNo = fm.RptNo.value;
    /*var tJudgeSql = "select count(*) from LLCUWBatch where "
        + " caseno = '" + tClmNo + "'";*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql7");
		mySql.addSubPara(tClmNo); 
    var CUWCount = easyExecSql(mySql.getString());
    
    if (CUWCount > 0){
    	alert("�ѷ�������ˣ��뵽���˽��۴������ٴ�������ˣ�");
        return false;
      }
	
	
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	  
//	  //����Ƿ���ڶ��˵ļӷ���Ϣ�����б������(by zl 2006-1-6 11:40)
//    if (!checkLjspay(fm.RptNo.value))
//     {
//        return;//�˴����ù��з���
//     }
	  
      //alert(fm.customerName.value);
      //alert(fm.RptorName.value);
    
	  var strUrl="LLSecondUWMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value+"&InsuredName="+fm.customerName.value+"&RptorName="+fm.RptorName.value+"&MissionID="+fm.MissionID.value;    
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
//      window.open(strUrl,"�������");
}

//���˴���
function SecondUWInput()
{   
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	 
	  var tFlag = "N";
	  
	  var strUrl="LLDealUWsecondMain.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value+"&Flag="+tFlag;    
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
//      window.open(strUrl,"���˴���");
}
//[���⴦��]��ť<>
function showExempt()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var strUrl="LLClaimExemptMain.jsp?ClaimNo="+tClaimNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�򿪷������
function showBeginInq()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    var strUrl="LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&initPhase=02";//+"&custVip="+fm.IsVip.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�������");
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
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql8");
		mySql.addSubPara( fm.RptNo.value ); 
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
	//***************************************
	//�жϸ��ⰸ�Ƿ���ڳʱ�
	//***************************************
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql9");
		mySql.addSubPara( fm.RptNo.value ); 
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("���ⰸ��û�гʱ���Ϣ��");
    	  return;
    }
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
//    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    window.open(strUrl,"�ʱ���ѯ","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//��������
function showLcContSuspend()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
//    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-23
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
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
//    if(SubReportGrid.getSelNo() <= 0)
//    {
//        alert("��ѡ������ˣ�");
//        return;
//    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
//Modifyed by niuzj,2005-12-23
	if (tcustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}    
    var strUrl="LLRgtMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=Rgt";

    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"���������");
}

//���������
function showRgtAddMAffixList()
{
//    if(SubReportGrid.getSelNo() <= 0)
//    {
//        alert("��ѡ������ˣ�");
//        return;
//    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
//Modifyed by niuzj,2005-12-23
	if (tcustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}      
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
    fm.MngCom.value = arr[11];   
    showOneCodeName('relation','Relation','RelationName');//���������¹��˹�ϵ
//    showOneCodeName('RptMode','RptMode','RptModeName');//������ʽ(?????)  	
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������
    
    //��ѯ����¼���
    var LLAccident = new Array;
   /* var strSQL1 = "select AccNo,AccDate from LLAccident where AccNo in (select CaseRelaNo from LLCaseRela "
                + "where CaseNo = '"+arr[0]+"')";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql10");
		mySql.addSubPara( arr[0]); 
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
                         + " select CustomerNo from LLSubReport where SubRptNo = '"+ arr[0] +"')";*/
 		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql11");
		mySql.addSubPara( arr[0]); 
//    alert(strSQL2);                 
    easyQueryVer3Modal(mySql.getString(),SubReportGrid);    
    
    //���ÿɲ�����
    fm.AuditIdea.disabled = false;
    fm.AuditConclusion.disabled = false;
    fm.ConclusionSave.disabled = false;
   
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
       // fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql12");
		mySql.addSubPara( fm.RptNo.value); 
		mySql.addSubPara( fm.customerNo.value); 
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
    //--------------------------1----------2---------3-----------4---------5------6-----7-------------8----------9----------10---------11
    /*var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,EditFlag,AffixConclusion,"
    	        + " (case EditFlag when '0' then '��' when '1' then '��' end),"
    	        + " (case AffixConclusion when '0' then '��' when '1' then '��' end),MedAccDate from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
 	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql13");
		mySql.addSubPara( fm.RptNo.value); 
		mySql.addSubPara( fm.customerNo.value); 
//    alert(strSQL2);
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
    fm.IsModify.value = tSubReport[0][9];
    fm.IsAllReady.value = tSubReport[0][10];
    fm.IsModifyName.value = tSubReport[0][11];
    fm.IsAllReadyName.value = tSubReport[0][12];
    fm.MedicalAccidentDate.value = tSubReport[0][13];
    //showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('accidentcode','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
    queryHospital('hospital','TreatAreaName');
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

    //���ð�ť
    fm.addUpdate.disabled = false;//����޸�
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
    if(PolDutyCodeGrid.getSelNo() <= 0)
    {
        alert("����ѡ��Ҫ����ı�����Ϣ,��ִ�д˲�����");
        return;
    }
    var i = PolDutyCodeGrid.getSelNo() - 1;//�õ�������
    
    //�ܸ�ʱ,Ҫ��д�ܸ�ԭ��
    if (fm.GiveType.value == "1" && fm.GiveReason.value == "")//�ܸ�
    {
        alert("����д�ܸ�ԭ��");
        return;
    }
    //�������ʱ������ԭ����Ϊ�� Modify by zhaorx 2006-05-15
    if(fm.GiveType.value == "0" && fm.AdjReason.value == "")
    {
    	alert("����д����ԭ��");
    	return;
    }
    
    if(checkAdjMoney()==false)
    {
    	return false;
    }
    
    //�����޸�
    PolDutyCodeGrid.setRowColData(i,14,fm.GiveType.value);//�⸶����
    PolDutyCodeGrid.setRowColData(i,16,fm.GiveReason.value);//�ܸ�ԭ�����
    PolDutyCodeGrid.setRowColData(i,18,fm.GiveReasonDesc.value);//�ܸ�����
    PolDutyCodeGrid.setRowColData(i,22,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReason.value);//����ԭ��
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,25,fm.AdjRemark.value);//������ע
    
    
    fm.saveUpdate.disabled = false;//�����޸�
}

//�Ա����޸ı��浽��̨
function SaveUpdate()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    fm.saveUpdate.disabled = true;//�����޸ĺ󣬰�ť���� Modify by zhaorx 2006-05-15
    submitForm();
}

//�����ύ����
function submitForm()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.target="fraSubmit";
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";    
}

//ת��Ԥ����������,����
function afterSubmit( FlagStr, content )
{
	showInfo.close();
    if (FlagStr == "Fail" )
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
        
        fm.whetherPrePay.value="";
        fm.whetherPrePayName.value="";
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
        
        location.href="../claim/LLClaimPrepayMain.jsp?ClmNo="+fm.ClmNo.value+"&CustomerNo="+fm.customerNo.value+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value+"&ActivityID="+fm.ActivityID.value;
        
        //goToBack();
    }
    
  

}

//�ύ�����,����
function afterSubmit1( FlagStr, content )
{
    showInfo.close();

    if (FlagStr == "Fail" )
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

//�ύ�����,������
function afterSubmit2( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
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
        afterMatchDutyPayQuery();
    }
    tSaveFlag ="0";
}

//�ύ�����,���²�ѯ
function afterSubmit3( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
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
        queryRegister();
    }
    tSaveFlag ="0";
}



//���ذ�ť
function goToBack()
{
	
  if (fm.RgtObjNo.value!=null && fm.RgtObjNo.value!="")
  { 
    location.href="../claimgrp/LLGrpClaimAuditMissInput.jsp";
  }    
  else
  {
  	location.href="LLClaimAuditMissInput.jsp";
  }	

}


//��˽��۱���
function ConclusionSaveClick()
{	
	//zy ���Ӱ������͵�У��
	if(!(fm.ModifyRgtState.value=="11" || fm.ModifyRgtState.value=="12" ||fm.ModifyRgtState.value=="14" ))
	{
		alert("������ʶ������Ҫ�����ʵ");
		return ;
	}
//�����δ����Ķ��ˣ�����ϵͳ��ʾ������Ӱ����˽��۱���
  /*var strSqlCuw = "select count(*) from LLCUWBatch where "
              + " caseno = '" + fm.RptNo.value + "' and InEffectFlag='0'" ;*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql14");
		mySql.addSubPara( fm.RptNo.value); 
  var tCountcuw = easyExecSql(mySql.getString());

  if (tCountcuw>0)
    {     
	  if(!confirm("��ע�⣬��δ������Ķ��˽��ۣ���ȷ�Ͻ��н��۱���"))
       {
          return false;
        }
    }

	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	  
	  //��Ͷ�����Ƿ���б�����������ж�
  	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql68");
	mySql.addSubPara(fm.RptNo.value );
  	var count=easyExecSql(mySql.getString());
		if(count>0)
		{
			tTLFlag="1";
			/*tSql="select state,dealdate,makedate||maketime,(select max(makedate||maketime) from llclaim where clmno=AccAlterNo) from LOPolAfterDeal where busytype='CL' and AccAlterNo='"+fm.ClmNo.value
							+"' and AccAlterType='4' order by state desc";*/
		    mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql69");
			mySql.addSubPara(fm.ClmNo.value );
			var countLP=easyExecSql(mySql.getString());
			if(!countLP)
			{
				alert("���Ƚ��н���!");
				return;
			}
			var ifGoon=false;//�Ƿ�����˼Ƽ� Ĭ��Ϊû����
			if(countLP[0][0]=='2'&&countLP[0][1])
			{
				ifGoon=true;
			}
			if(!ifGoon)
			{
				alert("��ȴ��Ƽ۴������!");
				return;
			}
			//ȷ���Ƿ����½��������㣺���������Ϣ����������Ϣ��û�����½�������
			if(countLP[0][2]>=countLP[0][3])
			{
				alert("�����½�������!");
				return;
			}
		}
		
	//�жϱ������Ƿ�Ϊ��
	if (fm.AuditConclusion.value == "" || fm.AuditConclusion.value == null)
	{
		alert("������д��˽���!");
        return;
	}
    else
    {
    	   //�ܸ�ʱ,Ҫ��д�ܸ�ԭ��by niuzj,2005-11-22
        if (fm.AuditConclusion.value == "1" && fm.ProtestReason.value == "")//�ܸ�
        {
           alert("����д�ܸ�ԭ��");
           return;
        }
        
        if (fm.AuditConclusion.value == "1")
        {
            var tGivetype = new Array;
           /* var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";*/
            mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql15");
		mySql.addSubPara( fm.RptNo.value); 
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                //alert(tGivetype.length);
                for (var i=0; i<tGivetype.length; i++)
                {
//                    alert(tGivetype[i])
                    if (tGivetype[i] != "1")
                    {
                        alert("���ȫ��Ϊ�ܸ�,������ȫ���ܸ�����!");
                        return;
                    }
                }
            }
        }
        else if (fm.AuditConclusion.value == "0")
        {
            var tGivetype = new Array;
            /*var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";*/
           mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql16");
		mySql.addSubPara( fm.RptNo.value); 
            tGivetype = easyExecSql(mySql.getString());
            if (tGivetype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGivetype.length; i++)
                {
//                    alert(tGivetype[i])
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
    if (fm.AuditConclusion.value == "0" || fm.AuditConclusion.value == "1")
    {
        //���顢�ʱ���������Ͷ���У��,�������㡢��ͬ����
        if (!checkPre())
        {
            return;
        } 
        //���ֻ�л��ⲻ���ж�������
       /* var tSQL = "select  nvl(count(*),0) from llclaimdutykind where 1=1 and substr(getdutykind,2,2)='09'"
                 + getWherePart( 'ClmNo','ClmNo' );*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql17");
		mySql.addSubPara( fm.ClmNo.value); 
        var tDutyKind = easyExecSql(mySql.getString());
        if (tDutyKind == 0||tDutyKind>1)
        {
//            //����¸�������,У�������˷���
//            if (fm.AuditConclusion.value == "0")
//            {
                /*var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
                            + getWherePart( 'ClmNo','ClmNo' );*/
                mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql18");
		mySql.addSubPara( fm.ClmNo.value); 
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
                else
                {
                	alert("�����˷���û�����!");
                    return;
                }
//            }
        }
    }
    
//===����===����е��ⰸ�¶��ű����еĸ����еĽ�����ͨ������У�飬��ȥ�������ֻ��Ϊ�˷�ֹ�������˷ѵ����û�б�Ҫ��=======beg===2006-5-22 15:04
//    //����Ǹ����������ж��Ƿ��н����ͬ����
//    if (fm.AuditConclusion.value == "0")
//    {
//        var strSql5 = "select count(1) from LLContState where DealState in ('D01','D02','D03') "
//                    + getWherePart( 'ClmNo','ClmNo' );
//        var tCount = easyExecSql(strSql5);
//        if (tCount > 0)
//        {
//            alert("�ѽ��н����ͬ���������¸������ۣ�");
//            return;
//        }
//    }
//===����===����е��ⰸ�¶��ű����еĸ����еĽ�����ͨ������У�飬��ȥ�������ֻ��Ϊ�˷�ֹ�������˷ѵ����û�б�Ҫ��=======end===2006-5-22 15:04
    
    //����л��Ᵽ��,������ʾ,Ҫ����л��⴦��
    if (fm.AuditConclusion.value == "0")
    {
       /* var tSqlExe = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 +" and clmno = '" + fm.RptNo.value + "'"
                 +" and getdutykind in ('109','209') "
                 ;*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql19");
		mySql.addSubPara( fm.RptNo.value); 
        var tDutyExe = easyExecSql(mySql.getString());
        if (tDutyExe != null)
        {
        	alert("ƥ������ı����л�����Ϣ,�ǵý��л��⴦��!!!");  
        }
    }
    
	if (fm.AuditIdea.value == "" || fm.AuditIdea.value == null)
	{
		alert("������д������!");
        return;
	}
    
    // ����������700�ַ�ʱ����ʾ��2006-01-01 С��
    if(fm.AuditIdea.value.length >=700)
    {
    	alert("���������ܳ���700���ַ�������������д��");
    	return;
    }
    //������ʶҪ���¼ Modify by zhaorx 2006-08-15
    if(fm.ModifyRgtState.value ==""||fm.ModifyRgtState.value==null)
    {
    	alert("������д������ʶ��");
    	return;
    }
    
//����Ƿ���ڶ��˵ļӷ���Ϣ�����б������(by zl 2006-1-6 11:40)
//    if (!checkLjspay(fm.RptNo.value))
//    {
//        return;//�˴����ù��з���
//    }
    
    //���꿹���ڵ�У��:���Ϊ����������Ч�ճ�����������2�꣨��2�꣩���������ʾ��Ϣ
    if (!checkAccDate(fm.RptNo.value))
    {
        return;
    }    
    
    //�ύ
    fm.action="./LLClaimAuditConclusionSave.jsp"
    fm.fmtransact.value = "INSERT";
    submitForm();
}

//���꿹���ڵ�У��:���Ϊ����������Ч�ճ�����������2�꣨��2�꣩���������ʾ��Ϣ
function checkAccDate(tRptNo)
{	
	var flag=false;//�Ƿ񳬹������ʾ
	var tContno="";//��������ı�����
	
	var tAccDate;//���յĳ������ڣ����������������Ϊ�����ȡҽ�Ƴ�������
    //var strQSql = "select AccDate, MedAccDate from LLCase where CaseNo ='"+tRptNo+"' ";
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql20");
		mySql.addSubPara(tRptNo); 
    var tDate = easyExecSql(mySql.getString());
    if (tDate == null)
    {
        alert("δ��ѯ����������!");
        return true;
    }
    else if (tDate[0][0]==null || tDate[0][0]=="")
    {
        tAccDate=tDate[0][1];
    }else{
    	tAccDate=tDate[0][0];
    }

   /* var strQSql = "select cvalidate,contno from lccont a where a.contno in "
    			+" (select distinct b.contno from LLClaimPolicy b where b.clmno = '"+tRptNo+"')";
   */ mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql21");
		mySql.addSubPara(tRptNo); 
    var tCvalidate = easyExecSql(mySql.getString());//���ձ�����Ӧ��Ч����
    if (tCvalidate == null)
    {
        alert("δ��ѯ���˰�����Ӧ��������Ч����!");
        return true;
    }
	for(var i=0;i<tCvalidate.length;i++){
		//alert("tCvalidate[i]:"+tCvalidate[i]);
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
   /* var strSql0 = "select AffixConclusion from LLAffix where "
               + "RgtNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql22");
		mySql.addSubPara(fm.RptNo.value); 
    var tAffixConclusion = easyExecSql(mySql.getString());
    
    if (tAffixConclusion)
    {
        for (var i = 0; i < tAffixConclusion.length; i++)
        {
//            alert(tAffixConclusion[i]);
            if (tAffixConclusion[i] == null || tAffixConclusion[i] == "")
            {
                alert("���������û�����!");
                return false;
            }
        }
    }
    //����
    /*var strSql1 = "select FiniFlag from LLInqConclusion where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql23");
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
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql24");
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
    //����
//    var strSql4 = "select InEffectFlag from LLCUWBatch where "
//                + " caseno = '" + fm.RptNo.value + "'"
//                //+ " and InEffectFlag not in ('1','2') and state = '1'"
//                ;
//    var tState = easyExecSql(strSql4);
    
//    if (tState)
//    {
//        for (var i = 0; i < tState.length; i++)
//        {
//            if (tState[i] == '0')
//            {
//                alert("����Ķ���û�����Ƿ���Ч����!");
//                return false;
//            }
//        }
//����Ƿ���ڶ��˵ļӷ���Ϣ�����б������(by zl 2006-1-6 11:40)
//        if (!checkLjspay(fm.RptNo.value))
//        {
//            return false;//�˴����ù��з���
//        }
//    }
    
    //��������\��ͬ�����Ƿ����
    /*var strSql3 = "select conbalflag,condealflag from llclaim where "
                + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql25");
		mySql.addSubPara(fm.RptNo.value); 
    var tFlag = easyExecSql(mySql.getString());
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
    return true;
}

//��˽���ȷ��
function AuditConfirmClick()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	  
	  //��Ͷ�����Ƿ���б�����������ж�
	  mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql68");
	mySql.addSubPara(fm.RptNo.value );
  	var count=easyExecSql(mySql.getString());
		if(count>0)
		{
			tTLFlag="1";
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql69");
			mySql.addSubPara(fm.RptNo.value );
		  	var countLP=easyExecSql(mySql.getString());
			if(!countLP)
			{
				alert("���Ƚ��н���!");
				return;
			}
			var ifGoon=false;//�Ƿ�����˼Ƽ� Ĭ��Ϊû����
			if(countLP[0][0]=='2'&&countLP[0][1])
			{
				ifGoon=true;
			}
			if(!ifGoon)
			{
				alert("��ȴ��Ƽ۴������!");
				return;
			}
			//ȷ���Ƿ����½��������㣺���������Ϣ����������Ϣ��û�����½�������
			if(countLP[0][2]>=countLP[0][3])
			{
				alert("�����½�������!");
				return;
			}
		}
	  
	  
    //�ж���˽���
    /*var strSql = "select AuditConclusion from LLClaimUWMain where "
               + "clmno = '" + fm.RptNo.value + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql26");
		mySql.addSubPara(fm.RptNo.value); 
    var tGivetype = easyExecSql(mySql.getString());
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
          /*  var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";*/
            mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql27");
		mySql.addSubPara(fm.RptNo.value); 
            tGtype = easyExecSql(mySql.getString());
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
          /*  var strSql = "select givetype from LLClaimDetail where "
                       + " clmno = '" + fm.RptNo.value + "'"
                       + " and givetype != '2'";*/
             mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql28");
		mySql.addSubPara(fm.RptNo.value); 
            tGtype = easyExecSql(mySql.getString());
            if (tGtype != null)
            {
                var tYesNo = 0;
                for (var i=0; i<tGtype.length; i++)
                {
//                    alert(tGtype[i])
                    if (tGtype[i] == "0")
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
        /*var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
                 + getWherePart( 'ClmNo','ClmNo' );*/
         mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql29");
		mySql.addSubPara(fm.ClmNo.value); 
        var tDutyKind = easyExecSql(mySql.getString());
        if (tDutyKind != '09')
        {
//            //����¸�������,У�������˷���
//            if (tGivetype == "0")
//            {
               /* var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
                            + getWherePart( 'ClmNo','ClmNo' );*/
                mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql30");
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
                /*var tBalanceMoney =0.00;
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
								//var tFeetype ="select distinct feeoperationtype from llbnf where clmno='"+document.all('RptNo').value+"'";
								mySql = new SqlClass();
								mySql.setResourceName("claim.LLClaimAuditInputSql");
								mySql.setSqlId("LLClaimAuditSql31");
								mySql.addSubPara(document.all('RptNo').value); 
								var tFeetypeValue = easyExecSql(mySql.getString());
								for (var t=0;t<tFeetypeValue.length;t++)
								{
									var tBalanceMoney =0.00;
                	var tBnfMoney=0.00;
									if(tFeetypeValue[t]=='A' || tFeetypeValue[t]=='B')
									 {
									 	//var tBalanceMoneySql = "select nvl(sum(pay),0) from LLBalance where clmno='"+document.all('RptNo').value+"' and feeoperationtype ='A'";
						                mySql = new SqlClass();
										mySql.setResourceName("claim.LLClaimAuditInputSql");
										mySql.setSqlId("LLClaimAuditSql32");
										mySql.addSubPara(document.all('RptNo').value); 
										tBalanceMoney = easyExecSql(mySql.getString());
						               //var tBnfMoneySql = "select nvl(sum(getmoney),0) from llbnf where clmno='"+document.all('RptNo').value+"' and feeoperationtype  in ('A','B')";
						               mySql = new SqlClass();
										mySql.setResourceName("claim.LLClaimAuditInputSql");
										mySql.setSqlId("LLClaimAuditSql33");
										mySql.addSubPara(document.all('RptNo').value); 
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
					                 		mySql.setResourceName("claim.LLClaimAuditInputSql");
											mySql.setSqlId("LLClaimAuditSql35");
											mySql.addSubPara(document.all('RptNo').value); 
							                 tBalanceMoney = easyExecSql(mySql.getString());
							                 
								             // var tBnfMoneySql = "select nvl(sum(getmoney),0) from llbnf where clmno='"+document.all('RptNo').value+"' and feeoperationtype not in ('A','B')";
								              mySql = new SqlClass();
											mySql.setResourceName("claim.LLClaimAuditInputSql");
											mySql.setSqlId("LLClaimAuditSql36");
											mySql.addSubPara(document.all('RptNo').value); 
				            
						               tBnfMoney = easyExecSql(mySql.getString());
						              if(Arithmetic(tBalanceMoney,'-',tBnfMoney,2)!=0)
											{
											  alert("�����������������˷����ܽ�һ�£����ʵ�Ƿ����δ����ľ����")
											  return false;
											}

										}
									}
//            }
        }
    }
    
//    //����Ǹ����������ж��Ƿ��н����ͬ����
//    if (tGivetype == "0")
//    {
//        var strSql5 = "select count(1) from LLContState where DealState in ('D01','D02','D03') "
//                    + getWherePart( 'ClmNo','ClmNo' );
//        var tCount = easyExecSql(strSql5);
//        if (tCount > 0)
//        {
//            alert("�ѽ��н����ͬ���������¸������ۣ�");
//            return;
//        }
//    }
    
//����Ƿ���ڶ��˵ļӷ���Ϣ�����б������(by zl 2006-1-6 11:40)
//    if (!checkLjspay(fm.RptNo.value))
//    {
//        return;//�˴����ù��з���
//    }
    
            
    //�ύ
    fm.action="./LLClaimAuditSave.jsp"
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
    /*var strSQL1 = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
                + ")";*/
   mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql37");
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
       /* var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,(select username from llclaimuser where usercode = LLReport.Operator) from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql38");
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
      		  fm.Operator.value = RptContent[0][10];
      		  showOneCodeName('relation','Relation','RelationName');//���������¹��˹�ϵ
      		  showOneCodeName('RptMode','RptMode','RptModeName');//������ʽ      		  
      		  showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
      		  showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
//      		  showOneCodeName('lloccurreason','IsDead','IsDeadName');//������־

      		  //����ҳ��Ȩ��
      		  fm.Remark.disabled=true;

            //�����ְ�������������Ϣ(����)
           /* var strSQL3 = " select CustomerNo,Name,"
                                 + " Sex,"
                                 + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA," 
                                 + " Birthday,"
                                 + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                                 + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                                 + " from LDPerson where "
                                 + " CustomerNo in("
                                 + " select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";
		*/mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql39");
		mySql.addSubPara(RptContent[0][0]); 
		
            easyQueryVer3Modal(mySql.getString(), SubReportGrid);
        }//end else
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
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql40");
		mySql.addSubPara(rptNo); 
    var AccNo = easyExecSql(mySql.getString());
    
    //prompt("",strSQL1);

    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-------------------------------------------------------13----------------------------------------14---------15-----------16------------17------------18---------19
    /*var strSQL2 =" select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate, "
                +" (select username from llclaimuser where usercode = llregister.Operator),mngcom, "
                +" (case assigneetype when '0' then 'ҵ��Ա' when '1' then '�ͻ�' end),assigneecode,assigneename, "
                +" (case assigneesex when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end), "
                +" assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass, "
                +" (case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end), "
                +" (case getmode when '1' then 'һ��ͳһ����' when '2' then '�����ʽ��ȡ' when '3' then '����֧��' end), "
                +" accepteddate,ApplyDate,Rgtantmobile,postcode,(select ReAffixDate from LLAffix where rgtno=llregister.rgtno and rownum=1) "
                +" from llregister where 1=1 "
                +" and rgtno = '"+rptNo+"'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql41");
		mySql.addSubPara(rptNo); 
    var RptContent = easyExecSql(mySql.getString());
    
    //prompt("",strSQL2);

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
    //showOneCodeName('llrgttype','rgtType','rgtTypeTypeName');
    showOneCodeName('relation','Relation','RelationName');//���������¹��˹�ϵ
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
   /* var strSQL3 = " select CustomerNo,Name,"
                 + " Sex,"
                 + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                 + " Birthday,"
                 + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                 + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                 + " from LDPerson where "
                 + "CustomerNo in("
                 + "select CustomerNo from LLCase where CaseNo = '"+ rptNo +"')";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql42");
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
   /* var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc from LLClaimUWMain where "
               + " ClmNo = '" + fm.ClmNo.value + "' and checktype='0'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql43");
		mySql.addSubPara(fm.ClmNo.value);
    //prompt("��ѯ��˽���sql",strSql);
    var tAudit = easyExecSql(mySql.getString());
//    alert(tAudit);
    if (tAudit != null)
    {
        fm.AuditConclusion.value = tAudit[0][0];
        fm.AuditIdea.value = tAudit[0][1];
        fm.SpecialRemark1.value = tAudit[0][2];
        fm.ProtestReason.value = tAudit[0][3];
        fm.ProtestReasonDesc.value = tAudit[0][4];
        showOneCodeName('llprotestreason','ProtestReason','ProtestReasonName');
        showOneCodeName('llclaimconclusion','AuditConclusion','AuditConclusionName');
        //��ʾ���ز�
        choiseConclusionType();
        fm.printAuditRpt.disabled = false;
    }
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
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
//	  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-23
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
	  var rptNo = fm.RptNo.value;//�ⰸ��
	
	  //Added by niuzj,2005-11-07
	  //����һ������,���ڿ���"�ص���Ϣ�޸�"ҳ���ϵ�"�޸�ȷ��"��ť
	  var tIsShow=0;  //Ϊ0ʱ�ð�ť��ʹ��,Ϊ1ʱ�ð�ť����ʹ��
	  
    var strUrl="LLClaimImportModifyMain.jsp?AppntNo="+tCustomerNo+"&RptNo="+rptNo+"&IsShow="+tIsShow;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
//	  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-23
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

	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
	
	var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value+"&flag='1'";
//	var strUrl="LLLClaimQueryInput.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value+"&flag='1'";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//�����˷���
function showBnf()
{

	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }
	  
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
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql44");
		mySql.addSubPara(tClaimNo);
    var arr = easyExecSql( mySql.getString() );    
    var tSumDutyKind = arr[0][0];


    //��ѯ�������ҽ�����������͵Ľ��
    /*tSql = " select nvl(sum(a.RealPay),0) from LLClaimDetail a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       +" and a.GiveType in ('0') "       
       ;*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql45");
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
    var strUrl="LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A&InsuredNo="+document.all('customerNo').value;
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

//��ͬ����(��xutao��2005��7��14�����)
function showContDeal()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	/*======================================================
	 * �޸�ԭ����Ӷ����Ƿ����У��
	 * �޸���  �������
	 * �޸�ʱ�䣺2005/12/16 15:40
	 ======================================================
	 */
//	if(!checkSecondClaim())
//	{
//		 return;
//	}
	 
//����Ƿ���ڶ��˵ļӷ���Ϣ�����б������(by zl 2006-1-6 11:40)
//    if (!checkLjspay(fm.RptNo.value))
//    {
//        return;//�˴����ù��з���
//    }
	 
    var strUrl="LLClaimContDealMain.jsp?ClmNo="+fm.ClmNo.value;
    strUrl= strUrl + "&CustNo=" + fm.customerNo.value;
    strUrl= strUrl + "&ConType="+fm.RgtClass.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��������
function showPolDeal()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
//����Ƿ���ڶ��˵ļӷ���Ϣ�����б������(by zl 2006-1-6 11:40)
//    if (!checkLjspay(fm.RptNo.value))
//    {
//        return;//�˴����ù��з���
//    }
    
    //QC5117BUG,by niuzj,2005-12-09
   // var strSql = " select count(1) from llclaim t where t.clmno='"+fm.RptNo.value+"' ";	
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql46");
		mySql.addSubPara(fm.RptNo.value);
    var tCount = easyExecSql(mySql.getString());
    if(tCount == 0)
    {
    	alert("û��ƥ��������Ϣ,��������б������������");
    	return;
    }
    else
    {
      var strUrl="LLClaimPolDealMain.jsp?ClmNo="+fm.ClmNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
}

//��ѯ���ս��
function queryResult(tCode,tName)
{
   /* var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql47");
		mySql.addSubPara(document.all(tCode).value);
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//��鱣�������,ֻ�ܵ�С
function checkAdjMoney()
{
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tRealM = parseFloat(PolDutyCodeGrid.getRowColData(i,10));
        var tRiskCode = parseFloat(PolDutyCodeGrid.getRowColData(i,3));//���ֱ���
        var tGetDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,4));//���α���
        var tAdjM = parseFloat(fm.RealPay.value);
        var tPolno = PolDutyCodeGrid.getRowColData(i,1);//���ֱ�����PolNo
        //var strPSQL = "select riskamnt from lcpol where polno='"+tPolno+"'";
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql48");
		mySql.addSubPara(tPolno);
        var arrp= easyExecSql(mySql.getString());
        
        if(arrp[0][0]== null){
        	alert("���ձ������");
        }
        
        //var strSQL = "select 1 from lmrisksort where riskcode='"+tRiskCode+"' and risksorttype='26' and risksortvalue='"+tGetDutyCode+"'";
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql49");
		mySql.addSubPara(tRiskCode);
		mySql.addSubPara(tGetDutyCode);
        //prompt("У����������Ƿ��ǽ�����:",strSQL);
        var arr= easyExecSql(mySql.getString());
        
        //�ſ��Խ��������ֵ�����
        if(arr == null){
            if (arrp[0][0] < tAdjM)
            {
                alert("�������ܴ��ڷ��ձ���!");
                fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,22);
                return false;
            }
        }
  
    }
    
    return true;
}

//��ӡ�ⰸ��˱���----2005-08-12���
function LLPRTClaimAuditiReport()
{
//	alert("��ӡ�ⰸ��˱���");
	fm.action="LLPRTClaimAuditiReportSave.jsp";
	fm.target = "../f1print";
	document.getElementById("fm").submit();
}


//���ɱ���֪ͨ��----2005-08-16���
//�޸�,niuzj 20050921,ԭSQL�����ڴ���trim(t.code),�ȸ�Ϊtrim(code)
function LLPRTPatchFeePrint()
{
    /*var strSql = " select count(1) from loprtmanager where 1=1 "
                + " and otherno='" + fm.ClmNo.value + "'"
                + " and trim(code)='PCT008'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql50");
		mySql.addSubPara(fm.ClmNo.value);
		
    var tCount = easyExecSql(mySql.getString());
    
    if (tCount == 0)
    {
        alert("û�пɴ�ӡ������!");
        return;
    }
    fm.action="LLPRTPatchFeeSave.jsp";
//	fm.target = "../f1print";
//	document.getElementById("fm").submit();
    if(beforePrtCheck(fm.ClmNo.value,"PCT008")=="false")
    {
    	return;
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
   /* var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
			+" and t.otherno='"+tclmno+"' "
			+" and trim(t.code)='"+tcode+"' ";*/
	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql51");
		mySql.addSubPara(tclmno);
		mySql.addSubPara(tcode);
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
	 	if(tstateflag!="1")
	 	{
//			fm.action = './LLPRTCertificatePutOutSave.jsp';   //
			fm.target = "../f1print";
			document.getElementById("fm").submit();
	 	}
		else
		{
			//���ڵ��Ѿ���ӡ�� 
			if(confirm("�õ�֤�Ѿ���ӡ��ɣ���ȷʵҪ������"))
	 		{
	 			//���벹��ԭ��¼��ҳ��
	 			var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value;
	 			window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	 		}
		}
	} 
}

//��ӡ���䵥֤֪ͨ��
function prtDelayRgt()
{
   /* var strSql = " select count(1) from loprtmanager where 1=1 "
                + " and otherno='" + fm.ClmNo.value + "'"
                + " and trim(code)='PCT003'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql52");
		mySql.addSubPara(fm.ClmNo.value);
		
    var tCount = easyExecSql(mySql.getString());
    
    if (tCount == 0)
    {
        alert("û�пɴ�ӡ������!");
        return;
    }
    //������д��ӡ�ύ����
    fm.action = './LLPRTCertificateRenewSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT003")=="false")
    {
    	return;
    }
}

//��֤������ӡ����
function showPrtControl()
{
    var tClmNo = fm.ClmNo.value;
	/*var strSQL="select count(1) from loprtmanager a,llparaprint b where 1=1 and a.code=b.prtcode "
			+" and a.stateflag='3' and a.othernotype='05' and a.otherno='"+tClmNo+"'"
			+" order by a.code";*/
	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql53");
		mySql.addSubPara(tClmNo);
	var arrCerti = easyExecSql(mySql.getString());
	if(arrCerti==null || arrCerti[0][0]=="0")
	{
		alert("û����Ҫ����������Ƶĵ�֤");
		return false;
	}	
    var strUrl="LLClaimCertiPrtControlMain.jsp?ClmNo="+tClmNo;
//    window.open(strUrl,"��֤��ӡ����");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
}

//�ռ�����Ϣ¼��,Added by niuzj,2005-10-27
function showReciInfoInp()
{
	var tClmNo = fm.ClmNo.value;   //�ⰸ��
	var tcustomerNo = fm.customerNo.value;  //�����˴���
	var tIsShow = 1;               //[����]��ť�ܷ���ʾ,0-����ʾ,1-��ʾ
  var tRgtObj = 1;               //�������ձ�־,1-����,2-����
	
	//�õ���ҳʱ,���дһ��Mainҳ��
	var strUrl="LLClaimReciInfoMain.jsp?ClmNo="+tClmNo+"&IsShow="+tIsShow+"&RgtObj="+tRgtObj+"&CustomerNo="+tcustomerNo;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 
	//location.href="LLClaimReciInfoInput.jsp?claimNo="+tClmNo; 
}

//��ʼ�����˽��۰�ť,������˻ظ����ð�ť����;�������δ�ظ�,�ð�ť���
function initSecondUWResult()
{
	var tClmNo = fm.ClmNo.value;   //�ⰸ��	
	//var strSQL = "select distinct 1 from llcuwbatch where caseno = '"+tClmNo+"' and state = '0'";
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql54");
		mySql.addSubPara(tClmNo);
	var tCount = easyExecSql(mySql.getString());
	
  //�������û�лظ�,Button"���˽���"Ӧ�ûҵ�
	if(tCount == "1")
	{
      fm.SecondUWResult.disabled = "true";
  }
  
	/*var strSQL1 = "select distinct 1 from llcuwbatch where caseno = '"+tClmNo+"'"
	            + " and InEffectFlag <> '1' and  InEffectFlag <> '2' and state = '1'";*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql55");
		mySql.addSubPara(tClmNo);       
	var tCount1 = easyExecSql(mySql.getString());   

	if(tCount1 == "1")    
	{
		  fm.SecondUW.disabled = "true";
	}   
}

//��ѯ����ҽԺ��Added by niuzj,2005-11-26
function queryHospital(tCode,tName)
{
	  /*var strSql =" select hospitalname from llcommendhospital  "
	             +" where trim(hospitalcode)='"+document.all(tCode).value+"' ";*/
	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql56");
		mySql.addSubPara(document.all(tCode).value);       
	  var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

/*================================================================================
 * �޸�ԭ���ں�ͬ����ǰȷ�϶����Ƿ����
 * �޸���  �������
 * �޸�ʱ�䣺2005/12/16/ 15:35
 *
 *================================================================================
 */

//ȡ����У�飬�����в����ô˺���
function checkSecondClaim()
{

    //����
   /* var strSql4 = "select InEffectFlag from LLCUWBatch where "
                + " caseno = '" + fm.RptNo.value + "'";*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql57");
		mySql.addSubPara(fm.RptNo.value);   
    var tState = easyExecSql(mySql.getString());
    
    if (tState)
    {

        for (var i = 0; i < tState.length; i++)
        {
            if (tState[i] == '0')
            {
                alert("����Ķ���û�����Ƿ���Ч����!");
                return false;
            }
        }
    }
    
    return true;
}

/**
 * 2008-12-27
 * zhangzheng
 * ����Ѿ����б�������,��ͬ����,�����˷���,���⴦�������ٴ����ת��Ԥ������
 * ���û�н�������������Ҳ���뽫����ĵ���,�ʱ�,��֤������!
 * @return
 */
function checkPrePay()
{
	//�����
    /*var strSql0 = "select AffixConclusion from LLAffix where "
               + "RgtNo = '" + fm.RptNo.value + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql58");
		mySql.addSubPara(fm.RptNo.value);   
    var tAffixConclusion = easyExecSql(mySql.getString());
    
    if (tAffixConclusion)
    {
        for (var i = 0; i < tAffixConclusion.length; i++)
        {
//            alert(tAffixConclusion[i]);
            if (tAffixConclusion[i] == null || tAffixConclusion[i] == "")
            {
                alert("���������û�����,����ת��Ԥ������!");
                return false;
            }
        }
    }
    
    //����
   /* var strSql1 = "select FiniFlag from LLInqConclusion where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql59");
		mySql.addSubPara(fm.RptNo.value);   
    var tFiniFlag = easyExecSql(mySql.getString());
    //alert("tFiniFlag:"+tFiniFlag);
    
    if (tFiniFlag)
    {
        for (var i = 0; i < tFiniFlag.length; i++)
        {
            if (tFiniFlag[i] != '1')
            {
                alert("����ĵ���û�����,����ת��Ԥ������!");
                return false;
            }
        }
    }
    
    //�ʱ�
   /* var strSql2 = "select SubState from LLSubmitApply where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql60");
		mySql.addSubPara(fm.RptNo.value);   
    var tSubState = easyExecSql(mySql.getString());
    
    if (tSubState)
    {
        for (var i = 0; i < tSubState.length; i++)
        {
            if (tSubState[i] != '1')
            {
                alert("����ĳʱ�û�����,����ת��Ԥ������!");
                return false;
            }
        }
    }
    
    
    //����
   /* var strSql4 = "select InEffectFlag from LLCUWBatch where "
                + " caseno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql61");
		mySql.addSubPara(fm.RptNo.value);   
    var tState = easyExecSql(mySql.getString());
    
    if (tState)
    {
        alert("�ѷ����˶���,����ת��Ԥ������!");
        return false;
    }
    
    
    //����
   /* var strSql4 = "select count(*) from LLExempt where "
                + " ClmNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql62");
		mySql.addSubPara(fm.RptNo.value);   
    var tExempt = easyExecSql(mySql.getString());
    
    //alert("tExempt:"+tExempt);
    if (tExempt>0)
    {
        alert("�ѽ����˻��⴦��,����ת��Ԥ������!");
        return false;
    }
    
    //��������\��ͬ�����Ƿ����
   /* var strSql3 = "select conbalflag,condealflag from llclaim where "
                + "clmno = '" + fm.RptNo.value + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql63");
		mySql.addSubPara(fm.RptNo.value);   
    var tFlag = easyExecSql(mySql.getString());

    if (tFlag)
    {
        if (tFlag[0][0] == '1')
        {
            alert("�ѽ��б����������,����ת��Ԥ������!");
            return false;
        }
        if (tFlag[0][1] == '1')
        {
            alert("�ѽ��к�ͬ�������,����ת��Ԥ������!");
            return false;
        }
    }
    
    //У�������˷���
    /*var tSQL = "select  nvl(count(*),0) from llclaimdutykind where 1=1 and substr(getdutykind,2,2)='09'"
             + getWherePart( 'ClmNo','ClmNo' );*/
  	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql64");
		mySql.addSubPara(fm.ClmNo.value);  
    var tDutyKind = easyExecSql(mySql.getString());
    //prompt("У�������˷���:",tSQL);

    if (tDutyKind == 0||tDutyKind>1)
    {

         /*var strSql4 = "select bnfkind from llbnf where 1=1 and  bnfkind='A' "
                        + getWherePart( 'ClmNo','ClmNo' );*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql65");
		mySql.addSubPara(fm.ClmNo.value);  
         //prompt("У���Ƿ�����������˷���:",strSql4);
         
         var tBNF = easyExecSql(mySql.getString());

         if (tBNF)
         {
             alert("�ѽ��������˷���,����ת��Ԥ������!");
             return false;
        }
    }
    else
    {
        alert("��������ֻ�л���,����ת��Ԥ������!");
        return false;
    }
    //zy 2009-10-23 16:38 ����Ԥ��ֻ����һ�ε�У��
    //var ySql ="select count(*) from ljagetclaim where otherno='"+fm.RptNo.value+"' and feeoperationtype='B' ";
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql66");
		mySql.addSubPara(fm.RptNo.value); 
    var bCount = easyExecSql(mySql.getString());
    if(bCount>=1)
    {
    	alert("�ð�������һ��Ԥ�������������ٴ�Ԥ�������ʵ");
    	return false;
    }
    
    return true;
}



//2008-12-27 zhangzheng ˫������������Ӧ����
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    
	//�Ƿ�Ԥ��
    if(cCodeName=="whetherpreconclusion"){
    	
    	if(fm.whetherPrePay.value=='01'){
    		
    		//alert("ѡ�����Ԥ��!");
    		
            if (!confirm("�Ƿ�Ҫ�԰���ִ��Ԥ������?"))
            {
                return;
            }
    		
            //����,�ʱ�,�����,����У��,��������,��ͬ����,�����˷���,���⴦��
            if (!checkPrePay())
            {
                return;
            } 
             
            location.href="../claim/LLClaimPrepayAuditMain.jsp?ClmNo="+fm.ClmNo.value+"&CustomerNo="+fm.customerNo.value+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value+"&ActivityID="+fm.ActivityID.value+"&AuditPopedom="+document.all('AuditPopedom').value+"&RgtClass=1&RgtObj=1";
    	}
    	else{
    		
    		//alert("ѡ��������!");
    	}
    	
        return true;
    }
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
      	
      	getChangeDate();
      
          return;
  }
}

/**
 * 2008-11-18
 * zhangzheng
 * ����ѡ����������;���ҽ�Ƴ������ں��������������Ƿ����¼��
 * 
**/
function getChangeDate()
{
	var flag=false;//�����Ƿ�׼��¼�������������ڱ�־,Ĭ�ϲ�׼��¼��
	
	//�������Ͱ���ҽ��ʱ,׼��¼��ҽ�Ƴ�������
	if(fm.claimType[5].checked == true)
	{
	    document.all('MedicalAccidentDate').disabled=false;
	}
	else
	{
	    document.all('MedicalAccidentDate').value="";
	    document.all('MedicalAccidentDate').disabled=true;
	}
	
	//��ֻ����ҽ������ʱ,�����������ڲ�׼��¼��
	var ClaimType = new Array;
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  if((fm.claimType[j].checked == true)&&(j!=5))
    	  {
    		  flag=true;
    	  }
    }
    
    if(flag==true)
    {
    	document.all('OtherAccidentDate').disabled=false;
    }
    else
    {
	    document.all('OtherAccidentDate').value="";
    	document.all('OtherAccidentDate').disabled=true;
    }
}

function clearAcc()//Ͷ������
{
	//���û�н������㣬�Ƚ�������
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql67");
	mySql.addSubPara(fm.RptNo.value);
    var tStateDuty = easyExecSql(mySql.getString());
    if (tStateDuty<1)
    {
    	showMatchDutyPay();//��������
    }
    
    //���������δ�Ƽ۵���ȴ��Ƽۺ���ܽ���
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql70");
		mySql.addSubPara(fm.RptNo.value);
    var count = easyExecSql(mySql.getString());
		if(count>0)
		{
			alert("�������Ƽ���Ҫ�������Ժ��ٽ��н���!");
			return;
		}

        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditInputSql");
		mySql.setSqlId("LLClaimAuditSql68");
		mySql.addSubPara(fm.RptNo.value);
    var count = easyExecSql(mySql.getString());
		if(count>0)
		{
			tTLFlag="1";
			//�ж��Ƿ��Ѿ����й�����
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLClaimAuditInputSql");
			mySql.setSqlId("LLClaimAuditSql69");
			mySql.addSubPara(fm.RptNo.value);
			var countLP=easyExecSql(mySql.getString());
			if(countLP)
			{			
				if(countLP[0][0]=='2'&&countLP[0][1])
				{
					alert("���Ѿ����й�������!");
					return;
				}
				else
				{
					alert("���Ѿ��ύ���ף���ȴ��Ƽ�!");
					return;
				}			
			}
		}

	if(tTLFlag!="1")
	{
		alert("���������û��Ͷ����,����Ҫ����!");
		return;
	}

	mOperate="INSERT";
    var showStr="���ڽ��б�������ƥ������������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimClearAccSave.jsp";
    document.getElementById("fm").submit(); //�ύ	
}
//Benefit Breakdown
function benefit()
{
	
   var strUrl="LLBenefitMain.jsp?CustomerNo="+fm.customerNo.value+"";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}

//Claim Check
function claimCheck()
{
	//alert(1);
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditInputSql");
	mySql.setSqlId("LLClaimAuditSql71");
	mySql.addSubPara(fm.RptNo.value);
	var countLP=easyExecSql(mySql.getString());
	if(countLP!=null){
		
		var Contno = countLP[0][0];
	}else{
		myAlert("û�в�ѯ�������ţ����Ƚ���ƥ�����㣡");
		return;
	}
    var strUrl="LLClaimCheckMain.jsp?CustomerNo='"+fm.customerNo.value+"'&ContNo='"+Contno+"'&RgtNo='"+fm.ClmNo.value+"'";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

