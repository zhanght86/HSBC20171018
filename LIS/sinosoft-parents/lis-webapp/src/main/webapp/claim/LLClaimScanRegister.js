var showInfo;
var turnPage = new turnPageClass();
var handle;//��ʱ���
var mySql = new SqlClass();

//��֤����
function showRgtMAffixList()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }	
    var tClmNo = fm.ClmNo.value;
    var tcustomerNo = fm.customerNo.value;
    var strUrl="LLRgtMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=Rgt";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
    //window.open(strUrl,"��֤����");
}

//������֤����
function showRgtAddMAffixList()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
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

	if(!KillTwoWindows(fm.RptNo.value,'20'))
	{
	  	  return false;
	}	 
	// var tMissionID = fm.MissionID.value;
    // var tSubMissionID = fm.SubMissionID.value;
	// var strSQL=" select a.missionprop11 from lwmission a where a.processid='0000000005' "
    //           +" and a.activityid='0000005015' "
    //           +" and a.missionid='" + tMissionID + "' "
    //           +" and a.submissionid='" + tSubMissionID + "' ";
    var tClmNo=fm.ClmNo.value;   
    //alert("ClmNo"+tClmNo); 
    /*var strSQL=" select a.FeeInputFlag from llregister a "
              +" where a.rgtno='"+ tClmNo +"' ";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql1");
	mySql.addSubPara(tClmNo );
    //////prompt("���б�������ƥ��",strSQL);
    var arr = easyExecSql( mySql.getString() );
    if(arr[0][0]==1)
    {
    	alert("ҽ�Ƶ�֤���¼�뻹δ���,�㲻�ܽ���ƥ���������!");
    } 
    else
    {
        //alert("��ʼƥ�䲢����");
        mOperate="MATCH||INSERT";
        fm.hideOperate.value=mOperate;
        fm.action = "./LLClaimRegisterMatchCalSave.jsp";
        fm.target="fraSubmit";
        var  showStr="���ڽ��б�������ƥ������������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var  urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

        document.getElementById("fm").submit(); //�ύ
        
    }
    
}

//���������������ʱ����Χ������,���ݴ������������ͨ��������������ͬ�Ĵ���
function firstSaveConclusionClick()
{
    //������ѡ����������
    if (fm.RgtConclusion.value == null || fm.RgtConclusion.value == "")
    {
        alert("��ѡ����������!");
        return;
    }
    
    //������ѡ�񰸼�����
    if (fm.rgtType.value == null || fm.rgtType.value == "")
    {
        alert("��ѡ�񰸼�����!");
        return;
    }
    
	
	if(!KillTwoWindows(fm.RptNo.value,'20'))
	{
	  	  return false;
	}	

    //���Ƚ��зǿ��ֶμ���
    if (fm.RgtConclusion.value == '02' && (fm.NoRgtReason.value == null || fm.NoRgtReason.value == ""))
    {
        alert("����д��������ԭ��!");
        return;
    }
    
    //������������ʶ
    if (fm.rgtType.value == null || fm.rgtType.value == "")
    {
        alert("��ѡ�񰸼���ʶ!");
        return;
    }
    
    //���������ͨ��������ƥ�䲢����,����ֱ��ִ�н��۱��洦���߼�
    if(fm.RgtConclusion.value == '01')
    {
    	showMatchDutyPay2();
    }
    else
    {
    	saveConclusionClick();
    }

}

/**=========================================================================
�޸�״̬����ʼ
�޸�ԭ���ڵ����������ʱ���õĺ���
�� �� �ˣ�zhangzheng 
�޸����ڣ�2008.12.13
=========================================================================
**/
//���б�������ƥ��
function showMatchDutyPay2()
{  

	if(!KillTwoWindows(fm.RptNo.value,'20'))
	{
	  	  return false;
	}	 
	// var tMissionID = fm.MissionID.value;
	// var tSubMissionID = fm.SubMissionID.value;
	// var strSQL=" select a.missionprop11 from lwmission a where a.processid='0000000005' "
	//           +" and a.activityid='0000005015' "
	//           +" and a.missionid='" + tMissionID + "' "
	//           +" and a.submissionid='" + tSubMissionID + "' ";
	var tClmNo=fm.ClmNo.value;   
	//alert("ClmNo"+tClmNo); 
	/*var strSQL=" select a.FeeInputFlag from llregister a "
	          +" where a.rgtno='"+ tClmNo +"' ";*/
	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql2");
	mySql.addSubPara(tClmNo );          
	//////prompt("���б�������ƥ��",strSQL);
	var arr = easyExecSql( mySql.getString() );
	if(arr[0][0]==1)
	{
		alert("ҽ�Ƶ�֤���¼�뻹δ���,�㲻�ܽ���ƥ���������!");
	} 
	else
	{
	    //alert("��ʼƥ�䲢����");
	    mOperate="MATCH||INSERT";
	    fm.hideOperate.value=mOperate;
	    fm.action = "./LLClaimRegisterMatchCalSave2.jsp";
	    fm.target="fraSubmit";
	    document.getElementById("fm").submit(); //�ύ
	    
	}	

}


//���ƥ�䲢���㰴ť��Ķ���
function afterMatchDutyPay2(FlagStr, content)
{
	//����������ʾ,����ִ�н��۱���
    if (FlagStr == "FAIL" ||FlagStr == "Call")
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
    else if (FlagStr == "SUCC2")
    {
    	//����ɹ���û�д�����ʾ��������ʾ
        afterMatchDutyPayQuery();
        saveConclusionClick();
    }
    else
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

        afterMatchDutyPayQuery();
        saveConclusionClick();
    }


}

//���ƥ�䲢���㰴ť��Ķ���
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
    var arr = new Array;

    var tClaimNo = fm.RptNo.value;         //�ⰸ��
    var tCaseNo = fm.RptNo.value;          //�ְ���
    var tCustNo = fm.customerNo.value;          //�ͻ���

    //��ѯLLClaim�������ⰸ�Ľ��
   /* tSql = " select a.realpay,b.BeAdjSum "
         + " from LLClaim a,LLRegister b  where 1=1 "
         + " and a.ClmNo = b.RgtNo and a.ClmNo = '"+tClaimNo+"'"*/
	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql3");
	mySql.addSubPara(tClaimNo );  
    //prompt("��ѯ�����ⰸ�Ľ����µ������:",tSql);
    arr = easyExecSql( mySql.getString()
 );
    if (arr)
    {
        arr[0][0]==null||arr[0][0]=='null'?'0':fm.standpay.value  = arr[0][0];
        arr[0][1]==null||arr[0][1]=='null'?'0':fm.adjpay.value  = arr[0][1];
//        fm.adjpay.value = fm.standpay.value;
        if (fm.adjpay.value < fm.standpay.value)
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
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql4");
	mySql.addSubPara(tClaimNo ); 
    //prompt("��ѯ�����ⰸ�Ľ������ⰸsql:",tSql);
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
       +" a.TabFeeMoney,a.SelfAmnt,a.ClaimMoney,a.StandPay,a.SocPay,a.OthPay,RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
*/
    	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql5");
	mySql.addSubPara(tClaimNo ); 
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
    /*tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.ClmNo = '"+tClaimNo+"'"*/
    	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql6");
	mySql.addSubPara(tClaimNo ); 
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
   /* tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
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
       +" and a.ClmNo = '"+tClaimNo+"'"   */
       	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql7");
	mySql.addSubPara(tClaimNo ); 
    //prompt("��ѯ����������sql:",tSql);
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
	  if(!KillTwoWindows(fm.RptNo.value,'20'))
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
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//�鿴����
function showQueryInq()
{
//	var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
  //Modifyed by niuzj,2005-12-23
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
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
    /*var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
   mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql8");
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

//�򿪳ʱ���ѯ
function showQuerySubmit()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }	
  //Modifyed by niuzj,2005-12-23
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
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
   /* var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
       mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql9");
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
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
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
    window.open("LLReportQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 //   window.open("LLReportQueryInput.jsp","������ѯ");
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


//���¼���ѯ����
function afterQueryLL2(tArr)
{
	//�õ�����ֵ
    fm.AccNo.value = tArr[0];//�¼���
    fm.AccDesc.value = tArr[2];//�¹�����
    fm.AccidentSite.value = tArr[3];//�¹ʵص�
    
    fm.occurReason.value = tArr[4];//����ԭ�����
    fm.ReasonName.value = tArr[5];//����ԭ������
    
    fm.accidentDetail.value = tArr[6];//����ϸ�ڱ���
    fm.accidentDetailName.value = tArr[7];//����ϸ������
    
    fm.AccResult1.value = tArr[8];//���ս��1����
    fm.AccResult1Name.value = tArr[9];//���ս��1����
    
    fm.AccResult2.value = tArr[10];//���ս��2����
    fm.AccResult2Name.value = tArr[11];//���ս��2����
    
    fm.Remark.value = tArr[12];//��ע��Ϣ
    

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
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
        //fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
    /*var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    
       	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql10");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.customerNo.value ); 
    ////prompt("SubReportGridClick:����-��ѯ��������:",strSQL1);
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
                	  
                	  //���Ϊ�˲У���ʾ�˲м���֪ͨ2005-8-13 11:04
//                	  if (tClaim == '01')
//                	  {
//                	      fm.MedCertForPrt.disabled = false;
//                	  }
            	  }
            }
        }
    }
    //��ѯ�ְ�����Ϣ
    var tSubReport = new Array;
    /*var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccDesc,AccResult1,AccResult2,medaccdate ,(select  codename from LDcode where  codetype='accidentcode'  and code=AccidentDetail) from LLSubReport where 1=1 "
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql11");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.customerNo.value );             
    ////prompt("SubReportGridClick:����-��ѯ�ְ�����Ϣ",strSQL2);
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
    fm.MedicalAccidentDate.value = tSubReport[0][9];
    fm.accidentDetailName.value = tSubReport[0][10];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
//    queryHospital('hospital','TreatAreaName');
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

    //------------------------------------------**End

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
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql12");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.customerNo.value );  
    ////prompt("��ѯ�����ְ���Ϣ-�����������*****",strSQL1);
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
                	  
                	  //���Ϊ�˲У���ʾ�˲м���֪ͨ2005-8-13 11:04
//                	  if (tClaim == '01')
//                	  {
//                	      fm.MedCertForPrt.disabled = false;
//                	  }
            	  }
            }
        }
    }

    
    //��ѯ�ְ�����Ϣ
    var tSubReport = new Array;
    //-------------------------1----------2-----------3---------4--------5-------6--------7-----------8-----------9------------10-------------------------------------------11
    /*var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,AffixConclusion,(case AffixConclusion when '0' then '��' when '1' then '��' end),medaccdate,(select  codename from LDcode where  codetype='accidentcode'  and code=AccidentDetail)  from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql13");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.customerNo.value );  
    //prompt("��ѯ�����ְ���Ϣ-��ѯ�ְ�����Ϣ:",strSQL2);
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
    fm.IsAllReady.value = tSubReport[0][9];
    fm.IsAllReadyName.value = tSubReport[0][10];
    fm.MedicalAccidentDate.value = tSubReport[0][11];
    fm.accidentDetailName.value = tSubReport[0][12];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
//    queryHospital('hospital','TreatAreaName');
//    showOneCodeName('IsAllReady','IsAllReady','IsAllReadyName');//��֤��ȫ��־
}

//ѡ��SubReportGrid��Ӧ�¼�,tPhase=1Ϊѡ����ʱ����
function allSubReportGridClick(tPhase)
{
    if(fm.isRegisterExist.value == "true")
    {
        //alert("SubReportGridClick2");
        SubReportGridClick2(tPhase);
    }
    else
    {
        //alert("SubReportGridClick");
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

//У���ʱ�λ��
function checkziplength(zipIdNo)
{
	if (zipIdNo.length!=6)
	 {
	   alert("�������ʱ�λ������");
	 }
}

//У���ʱ�λ��
function checkRptorMolength(moIdNo)
{
	if (moIdNo.length!=11)
	 {
	   alert("�������ֻ�����λ������");
	 }
}

//�ύǰ��У�顢����
function beforeSubmit()
{
	//��ȡ������Ϣ
    var RptNo = fm.RptNo.value;//�ⰸ��
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccReason = fm.occurReason.value;//����ԭ��
    var AccDesc = fm.accidentDetail.value;//����ϸ��
    
    
    //----------------------------------------------------------BEG2005-7-12 16:45
    //���������������͹�ϵ�ķǿ�У��
    //----------------------------------------------------------
    if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("������������������");
        return false;
    }
    //���������˵绰,��ַУ�� Add by zhaorx 2006-03-16
    if (fm.RptorPhone.value == "" || fm.RptorPhone.value == null)
    {
        alert("�����������˵绰��");
        return false;
    }    
    if (fm.RptorAddress.value == "" || fm.RptorAddress.value == null)
    {
        alert("��������������ϸ��ַ��");
        return false;
    }    
    if (fm.AppntZipCode.value == "" || fm.AppntZipCode.value == null)
    {
        alert("�������������ʱ࣡");
        return false;
    }  
    if (fm.Relation.value == "" || fm.Relation.value == null)
    {
        alert("���������������¹��˹�ϵ��");
        return false;
    }
    if (fm.AcceptedDate.value == "" || fm.AcceptedDate.value == null)
    {
        alert("�����뽻�����ڣ�");
        return false;
    }
    
    //var strSQL = "select rgtdate from llregister where rgtno = '" + fm.RptNo.value + "'";
        mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql14");
	mySql.addSubPara(fm.RptNo.value ); 

    //��ѯ��������
    var tRptdate = easyExecSql(mySql.getString());

    if (tRptdate != null && tRptdate != "")
    {
    	if (dateDiff(tRptdate[0][0],fm.AcceptedDate.value,'D') > 0)
		{
			alert("����������С�ڻ���������Ų�������!");
			return false; 
		}  
    }
    else
    {
    	if (dateDiff(mCurrentDate,fm.AcceptedDate.value,'D') > 0)
    		{
    			alert("�������ڻ�δ���ɣ�����������С�ڻ���ڵ�ǰ����!");
    			return false; 
    		} 
    }
    if (fm.ApplyDate.value == "" || fm.ApplyDate.value == null)
    {
        alert("������ͻ��������ڣ�");
        return false;
    }
    var tAssigneeZip = fm.AssigneeZip.value;
    if (tAssigneeZip.length > 6)
    {
        alert("�ʱ����");
        return false;
    }
    //----------------------------------------------------------end
    
    //1 �������ˡ���Ϣ
    if (checkInput1() == false)
    {
    	  return false;
    }
    
    var claimType=0;//ѡ��������������

    
    //��������
    for(var j=0;j<fm.claimType.length;j++)
    {   	     	  
    	  if(fm.claimType[j].checked == true)
    	  {
    		  claimType++;
          }
    }
    
    //alert("claimType:"+claimType);
    
    //5 �����������
    if (claimType==0)
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }
    
    
    //������ҽ����������ʱ��ҪУ��ҽ�Ƴ�������
    if(fm.claimType[5].checked == true)
    {
		//У��ҽ�Ƴ������ں��¹�����
		if (checkDate(fm.AccidentDate.value,fm.MedicalAccidentDate.value,"ҽ�Ƴ�������") == false)
		{
		     return false;
		}
    }
    
    //�����ڶ���һ���������ͻ���ڷ�ҽ�Ƶ���������ʱУ��������������
    if((claimType>1)||((fm.claimType[5].checked == false)&&claimType==1))
    {
        //У�������������ں��¹�����
		if (checkDate(fm.AccidentDate.value,fm.OtherAccidentDate.value,"������������") == false)
		{
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
    //Modify by zhaorx 2006-07-04
//    if (fm.occurReason.value == '1' && (fm.AccResult1.value ==""  || fm.AccResult1.value == null || fm.AccResult2.value == "" || fm.AccResult2.value == null))
//    {
//        alert("����ԭ��Ϊ����ʱ�����ս��1�����ս��2������Ϊ�գ�");
//        return false;
//    }    
    //-----------------------------------------------------------**Beg*2005-7-12 16:27
    //��ӳ���ԭ��Ϊ����ʱ������������Ϊҽ��ʱ���¹����ڵ���ҽ�Ƴ�������
    //-----------------------------------------------------------**
    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.MedicalAccidentDate.value)&& fm.claimType[5].checked == true)
    {
        alert("����ԭ��Ϊ����ʱ���¹����ڱ������ҽ�Ƴ������ڣ�");
        return false;
    }
    //Modify by zhaorx 2006-07-04
//    if(fm.occurReason.value == "2" && (fm.AccResult1.value =="" || fm.AccResult1.value==null))
//    {
//        alert("����ԭ��Ϊ����ʱ�����ս��1����Ϊ�գ�");
//        return false;
//    }

    //-----------------------------------------------------------**Beg*2007-02-05 15:02
    //��ӳ��ս��1,���ս��2Ϊ��¼��
    //-----------------------------------------------------------**
  
    if(fm.AccResult1.value ==""  || fm.AccResult1.value == null)
    {
        alert("���ս��1����Ϊ�գ�");
        return false;
    }   
    if(fm.AccResult2.value == "" || fm.AccResult2.value == null)
    {
        alert("���ս��2����Ϊ�գ�");
        return false;
    }  
    //-----------------------------------------------------------**End

    //5 �����������
    if (claimType==0)
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }
    
    //----------------------------------------------------------*Beg*2005-6-13 20:26
    //�������������ѡ��"ҽ��"����ѡ��ҽԺ���ж�
    //----------------------------------------------------------*
    if(fm.claimType[5].checked == true && (fm.hospital.value == "" || fm.hospital.value == null))
    {
        alert("��ѡ��ҽԺ��");
        return false;
    }
    //---------------------------------------------------------*End
    
    //---------------------------------------------------------Beg*2005-6-27 11:55
    //�������������ѡ�л���,����ѡ��������߲л��ش󼲲�֮һ
    //---------------------------------------------------------
    if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false && fm.claimType[2].checked == false)
    {
        alert("ѡ�л���,����ѡ��������߲л��ش󼲲�֮һ��");
        return false;
    }
    //---------------------------------------------------------End

    var tAccDate="";//�Ƚ�����,ȡС���Ǹ�������Ϊ�Ƚ�����
    
    //������Ϊ��ʱ,ȡ����С���Ǹ�������Ϊ�Ƚ�����
    if((!(fm.MedicalAccidentDate.value==null||fm.MedicalAccidentDate.value==""))&&(!(fm.OtherAccidentDate.value==null||fm.OtherAccidentDate.value.value=="")))
    {
    	
    	//ȡ���������н�С���Ǹ�
        if (dateDiff(fm.OtherAccidentDate.value,fm.MedicalAccidentDate.value,'D') > 0)
        {
        	tAccDate=fm.OtherAccidentDate.value;
        }
    	else
    	{
    		tAccDate=fm.MedicalAccidentDate.value;
    	}
    }
    else if(!(fm.MedicalAccidentDate.value==null||fm.MedicalAccidentDate.value=="")){
    	
    	tAccDate=fm.MedicalAccidentDate.value;
    }
    else
    {
    	tAccDate=fm.OtherAccidentDate.value;
    }

    
    //alert("tAccDate:"+tAccDate);

    //---------------------------------------------------------Beg*2005-12-28 16:22
    //����ԭ��Ϊ����,ֻҪ����һ�������������¹�����180��󣬼���ʾ
    //---------------------------------------------------------
    if(fm.occurReason.value == '1')
    {
    	if (dateDiff(fm.AccidentDate.value,tAccDate,'D') > 180)
        {
            alert("�����������¹�����180���!");
        }
    }

    
    //---------------------------------------------------------Beg*2005-6-27 13:59
    //���δ�ڳ��պ�ʮ���ڱ������ж�,ҽ�Ƴ������ں��������������ĸ�������ĸ����¹����ڱ�
    //---------------------------------------------------------
    if (dateDiff(fm.AccidentDate.value,mCurrentDate,'D') > 10)
    {
        alert("δ�ڳ��պ�ʮ���ڱ���!"); 
    }
    
    
    //---------------------------------------------------------Beg*2005-11-30 11:01
    //�����쵼���ʼ�,��������������������
    //1.�������ں�ĳ������ڻ��¹����ڲ��ܱ�������
    //2.�������ڵ����֮ǰ�ĳ������ڻ��¹������ڱ���ʱ����Ҫ������ʾ��
    //---------------------------------------------------------
    //var strSQL = "select deathdate from LDPerson where CustomerNo = '" + fm.customerNo.value + "'";
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql15");
	mySql.addSubPara(fm.customerNo.value ); 
    ////prompt("��ѯ�ͻ��Ƿ����sql",strSQL);
    var tDeathDate = easyExecSql(mySql.getString());
    if (tDeathDate != null && tDeathDate != "")
    {
        if (dateDiff(tAccDate,tDeathDate[0][0],'D') < 0)
        {
            alert("�ͻ�"+fm.customerName.value+"�ѱ�ȷ����"+tDeathDate+"��ʣ��������Ժ���ⰸ��������");
            return;
        }
        else
        {
            if (!confirm("�ͻ�"+fm.customerName.value+"�ѱ�ȷ����"+tDeathDate+"��ʣ��Ƿ�������У�"))
            {
                return;
            }
        }
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
        fm.isReportExist.value = "true";
        fm.isRegisterExist.value = "true";
        notDisabledButton();
        fm.RgtConclusionName.value = "";
        //alert("fm.fmtransact.value = "+fm.fmtransact.value);
        
        operateButton21.style.display="none";
        operateButton22.style.display="";      
        

        document.all('MedicalAccidentDate').disabled=false;
        document.all('OtherAccidentDate').disabled=false;
        
        if(fm.fmtransact.value=="update")
        {
           //alert("queryRegister");
           queryRegister();
        }
        else
        {
           //alert("queryNewRegister");
           queryNewRegister();
        }

        //���ÿɲ�����ť
        if(fm.isNew.value == '0')
        {
            fm.QueryPerson.disabled = false;
            fm.QueryReport.disabled = false;
        }
        if(fm.isNew.value == '1')
        {
            
            fm.QueryPerson.disabled = true;
            fm.QueryReport.disabled = true;
        }
        

        
 
    }
    tSaveFlag ="0";
    
}


//������������ִ�еĲ���
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
        fm.isReportExist.value = "true";
        fm.isRegisterExist.value = "true";
        
        operateButton21.style.display="none";
        operateButton22.style.display="";
        
        document.all('MedicalAccidentDate').disabled=false;
        document.all('OtherAccidentDate').disabled=false;
      
        notDisabledButton();
        fm.RgtConclusionName.value = "";
        queryConclusionSaveRegister();
        //���ÿɲ�����ť
        if(fm.isNew.value == '0')
        {
			
            fm.QueryPerson.disabled = false;
            fm.QueryReport.disabled = false;
        }
        if(fm.isNew.value == '1')
        {
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
        top.close();
    }
    tSaveFlag ="0";
}

//����ȷ��
function RegisterConfirm()
{
	if(!KillTwoWindows(fm.RptNo.value,'20'))
	{
	  	  return false;
	}	
	
    var yesORno = 0;
    if (fm.RptNo.value == "")
    {
   	    alert("�ⰸ��Ϊ�գ�");
   	    return;
    }
    //-------------------------------------------------------------------BEG2005-8-9 8:58
    //����������ۡ�ƥ����㡢��֤��ȫ
    //-------------------------------------------------------------------
    /*var sql1 = " select RgtConclusion from llregister where "
            + " RgtNo = '" + fm.RptNo.value + "'"*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
	mySql.setSqlId("LLClaimScanRegisterSql16");
	mySql.addSubPara(fm.RptNo.value ); 
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
        	//2008-12-16 zhangzheng
        	//ȡ�����������׶ε�ƥ�䲢����,�����ڽ��۱���ʱ�Զ��ں�̨ƥ�䲢����,��������Ҫ��ֹ���㲻������޷���������ȷ�ϵ����⣬��ס�������У��

            //var sql2 = "select count(1) from LLClaimDetail where"
            //         + " ClmNo = '" + fm.RptNo.value + "'";
            //var tDutyKind = easyExecSql(sql2);
            //if (tDutyKind == 0)
            //{
                //alert("���Ƚ���ƥ�䲢����!");
                //return;
            //}
            
            //2008-12-16 ֻ���ύ����Ҫ�ͻ��ύ�ĵ�֤���ϲŽ��б����������ͨ����У��
           /* var sql2 = " select * from LLAffix where "
                + " RgtNo = '" + fm.RptNo.value + "'"*/
                mySql = new SqlClass();
				mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
				mySql.setSqlId("LLClaimScanRegisterSql17");
				mySql.addSubPara(fm.RptNo.value ); 
            //prompt("��ѯ�Ƿ���Ҫ�ͻ��ύ�ĵ�֤����sql",sql2);
            var tResult2 = new Array;
            tResult2 = easyExecSql(mySql.getString());
            //alert(tResult2);
            if (tResult2 != null)
            {
                
                //��鵥֤��ȫ��־
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
                else
                {
                    var tResult = new Array;
                    /*var sql = " select affixconclusion from llcase where "
                             + " caseno = '" + fm.RptNo.value + "'"*/
                                    mySql = new SqlClass();
				mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
				mySql.setSqlId("LLClaimScanRegisterSql18");
				mySql.addSubPara(fm.RptNo.value ); 
                    tResult = easyExecSql(mySql.getString());
                    if (tResult != null)
                    {
                        for (var i=0; i < tResult.length; i++)
                        {
                            if (tResult[0][i] == "0")
                            {
//                                if (confirm("��֤����ȫ,�Ƿ�����!"))
//                                {
//                                    yesORno = 1;
//                                }
//                                else
//                                {
//                                    yesORno = 0;
//                                    return;
//                                }
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
        /*var strSql = "select givetype from LLClaimDetail where "
                   + "clmno = '" + fm.RptNo.value + "'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql19");
		mySql.addSubPara(fm.RptNo.value ); 
      tGivetype = easyExecSql(mySql.getString());
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
        else
        {
        	//�����㲻�����ʱ��У�鱣��ĸ�������,�Զ�ͨ��
        	 tYes = 1;
        }
        if (tYes == 0)
        {
            alert("����ȫ��Ϊ����ʱ��������ͨ��!");
            return;
        }
    }
    
    //2008-12-13 zhangzheng ��������ʱ����Ҫ��ӡ��������֪ͨ��
    if (tRgtConclusion == '02')
    {
 	    if(checkPrtNotice(fm.ClmNo.value,"PCT007")==false)
 	    {
 	    	alert("��δ��ӡ��������֪ͨ�飬���ܽ�������ȷ��!");
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


/**********************************
//����ȷ��ǰ�����ӡ��������֪ͨ��
***********************************/
function checkPrtNotice(tclmno,tcode){
	
	if(tclmno=="" ||tcode=="")
   	{
   		alert("������ⰸ�Ż�֤���ͣ����룩Ϊ��");
   		return false;
   	}
    /*var strSql="select t.stateflag from loprtmanager t where 1=1 "
			+" and t.otherno='"+tclmno+"' "
			+" and trim(t.code)='"+tcode+"' ";*/
	        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql20");
		mySql.addSubPara(tclmno ); 
		mySql.addSubPara(tcode ); 
    //prompt("�жϵ�֤�Ƿ�Ϊδ��ӡsql",strSql);
	var arrLoprt = easyExecSql(mySql.getString());
	if(arrLoprt==null)
	{
		alert("û���ҵ��õ�֤����ˮ��");
		return false;
	}	
	else 
	{
		var tstateflag=arrLoprt[0][0];//��ӡ״̬
		if(tstateflag!="1")
		{
			return false;
		}

	}
	
	return true;
}

//[����]��ť��Ӧ����
function saveClick()
{
    //���Ƚ��зǿ��ֶμ���
    if (beforeSubmit())
    {
    	var CustomerNo = fm.customerNo.value;
    	
    	
    	//��ѯ�Ƿ������������
    	//var tRptSql = "select * from llsubreport,llreportreason"
    				//+ " where llsubreport.subrptno=llreportreason.rpno and"
    				//+ " llsubreport.customerno=llreportreason.customerno and"
    				//+ " llsubreport.customerno='"+CustomerNo+"' and reasoncode in('102','202')" 
    			    //+ " and subrptno!='"+fm.RptNo.value+"'";
    	
    	/*var tRptSql = "select * from llcase,LLAppClaimReason"
    	+	" where LLCase.caseno=LLAppClaimReason.CaseNo and"
		+ " LLCase.customerno=LLAppClaimReason.CustomerNo and"
		+ " LLCase.customerno='"+CustomerNo+"' and reasoncode in('102','202')";*/
    	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql21");
		mySql.addSubPara(CustomerNo ); 
    	//prompt("��ѯ�Ƿ������������",tRptSql);
    	
    	var arrRpt = easyExecSql(mySql.getString());
    	if(arrRpt){
    		alert("�ÿͻ��Ѵ���������������������ע��");
    		//return false;
    	}
    	
        if (fm.RgtClass.value == "" || fm.RgtClass.value == null)
        {
            fm.RgtClass.value = "1";
        }
    
        fm.fmtransact.value = "insert||first";

        fm.action = './LLClaimScanRegisterSave.jsp';

        submitForm();
    }
}

//[����]��ť��Ӧ����
function saveConclusionClick()
{


    //������ѡ����������
    if (fm.RgtConclusion.value == null || fm.RgtConclusion.value == "")
    {
        alert("��ѡ����������!");
        return;
    }
    
	
	if(!KillTwoWindows(fm.RptNo.value,'20'))
	{
	  	  return false;
	}	

    //���Ƚ��зǿ��ֶμ���
    if (fm.RgtConclusion.value == '02' && (fm.NoRgtReason.value == null || fm.NoRgtReason.value == ""))
    {
        alert("����д��������ԭ��!");
        return;
    }
    
    //������������ʶ
    if (fm.rgtType.value == null || fm.rgtType.value == "")
    {
        alert("��ѡ�񰸼�����!");
        return;
    }
    
    //�Բ���������Ҫ����ĵ��飬�ʱ������ Add by zhaorx 2006-03-08
    if(fm.RgtConclusion.value == '02')
    {
	    //�����Ƿ����У��
	    /*var strSql1 = " select FiniFlag from LLInqConclusion where "
	                + " clmno = '" + fm.RptNo.value + "'";*/
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql22");
		mySql.addSubPara(fm.RptNo.value ); 
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
	    //�ʱ��Ƿ����У��
	   /* var strSql2 = " select SubState from LLSubmitApply where "
	                + " clmno = '" + fm.RptNo.value + "'";*/
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql23");
		mySql.addSubPara(fm.RptNo.value ); 
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
    }
    

    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimRegisterConclusionSave.jsp';
    submitForm();


}


//�ύ
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
	fm.target="fraSubmit";
	document.getElementById("fm").submit(); //�ύ
	tSaveFlag ="0";

}


//��������Ϣ�޸�
function updateClick()
{
	  if(!KillTwoWindows(fm.RptNo.value,'20'))
	  {
	  	  return false;
	  }	
    if (confirm("��ȷʵ���޸ĸü�¼��"))
    {
        if (beforeSubmit())
        {
            fm.fmtransact.value = "update";
            fm.action = './LLClaimScanRegisterSave.jsp';
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
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("�¹����ڲ������ڵ�ǰ����!");
            return false; 
        }  
    }
    
    return true;
}

//������ѯ
function queryReport()
{
    //��ϢУ��
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    
    //�����¼���(һ��)
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql24");
		mySql.addSubPara(rptNo ); 
    ////prompt("queryReport:�����¼���(һ��)",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //���������ż�����������Ϣ(һ��)
   /* var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,(select username from llclaimuser where usercode = LLReport.Operator),RgtClass from LLReport where "
                + "RptNo = '"+rptNo+"'";*/
    	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql25");
		mySql.addSubPara(rptNo );     
    ////prompt("queryReport:ѡ��һ��������¼:���������ż�����������Ϣ",strSQL2);
    var RptContent = easyExecSql(mySql.getString());

    //����ҳ������
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];

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
    
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
//    showOneCodeName('RptMode','RptMode','RptModeName');//������ʽ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
//    showOneCodeName('lloccurreason','IsDead','IsDeadName');//������־

    //����ҳ��Ȩ��
    fm.AccidentDate.readonly = true;

    fm.QueryPerson.disabled=true;
    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;

    //���ð�ť++++++++++++++++++++++++++++++++++++++++�����

    //�����ְ�������������Ϣ(����)
   /* var strSQL3 = " select CustomerNo,Name,"
                + " Sex,"
                + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                + " Birthday,"
                + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                + " from LDPerson where "
                + " CustomerNo in("
                + " select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
       	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql26");
		mySql.addSubPara(rptNo );    
    ////prompt("queryReport:����-�����ְ�������������Ϣ(����)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
    
    //��ѯ�ⰸ��־
    QueryClaimFlag();
}

//�����˲�ѯ,������Ϊ��������ʱ������Ϊ������
function queryProposer()
{
   /* var strSQL = "select count(1) from LLReportReason a where "
                + "a.rpno = '" + fm.RptNo.value + "'"
                + "and substr(a.reasoncode,2,2) = '02'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql27");
		mySql.addSubPara(fm.RptNo.value );    
    //////prompt("ѡ��һ��������¼��ѯ������",strSQL);
    var tCount = easyExecSql(mySql.getString());
    //û��������������
    if (tCount == "0")
    {
        //var strSQL2 = "select a.phone,a.postaladdress from LCAddress a where "
                    //+ "a.customerno = '" + fm.customerNo.value + "'";
        //var tLCAddress = easyExecSql(strSQL2);
        //fm.RptorName.value = fm.customerName.value;
        //fm.RptorPhone.value = tLCAddress[0][0];
        //fm.RptorAddress.value = tLCAddress[0][1];
        ///fm.Relation.value = "00";
        showOneCodeName('relation','Relation','RelationName');//���������¹��˹�ϵ
    } 
}



//ִ��������ҳ���н��۱��水ť��Ĳ���
function queryConclusionSaveRegister()
{
    //��ϢУ��
    //alert("*********************");
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    
    //�����¼��š��¹�����(һ��)
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo  "
                + "and LLCaseRela.caseno= '"+rptNo+"'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql28");
		mySql.addSubPara(rptNo); 
    ////prompt("queryConclusionSaveRegister:ִ��������ҳ���н��۱��水ť��Ĳ���-�����¼��š��¹�����(һ��)",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18------------19-------20------------------------------------21
    /*var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,(select username from llclaimuser where usercode = llregister.Operator),mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end),AcceptedDate,ApplyDate,Rgtantmobile,postcode from llregister  "
                + "where RgtNo='"+rptNo+"'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql29");
		mySql.addSubPara(rptNo); 
    ////prompt("queryConclusionSaveRegister:ִ��������ҳ���н��۱��水ť��Ĳ���-���������ż�����������Ϣ(һ��)",strSQL2);
    var RptContent = easyExecSql(mySql.getString());
    

    //����ҳ������
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];

    fm.RptNo.value = RptContent[0][0];
    //alert("RptNo�ı��:"+fm.RptNo.value);
    fm.ClmNo.value = RptContent[0][0];
    //alert("ClmNo�ı��:"+fm.ClmNo.value);
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
    fm.AcceptedDate.value = RptContent[0][21];
    fm.ApplyDate.value = RptContent[0][22];
    fm.RptorMoPhone.value = RptContent[0][23];
    fm.AppntZipCode.value = RptContent[0][24];
    
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������
    showOneCodeName('llnorgtreason','NoRgtReason','NoRgtReasonName');//������ԭ��
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//�������Ա�
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//����������

    //����ҳ��Ȩ��
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
//        fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = false;              
    }
    else
    {
        fm.MedicalFeeInp.disabled = true;
    }

    //�����ְ�������������Ϣ(����)
    /*var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from llcase where CaseNo = '"+ RptContent[0][0] +"')";*/
            mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql30");
		mySql.addSubPara(RptContent[0][0]); 
    ////prompt("queryConclusionSaveRegister:ִ��������ҳ���н��۱��水ť��Ĳ���-�����ְ�������������Ϣ(����)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
    
    //��������������ʾ���ز�
    showDIV();
    
    //��ѯ�ⰸ��־
    QueryClaimFlag();
    
    //�������������ж��Ƿ��ѯƥ��������Ϣ
    if (fm.RgtConclusion.value == '01')
	{
	    afterMatchDutyPayQuery();
        /*var strSQL4 = "select RgtState from llregister where "
                    + "rgtno = '"+rptNo+"'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql31");
		mySql.addSubPara(rptNo); 
        var tRgtState = easyExecSql(mySql.getString());
        if (tRgtState)
        {
            fm.rgtType.value = tRgtState;
            showOneCodeName('rgtType','rgtType','rgtTypeTypeName');
        }
	}
	
	
}



//�滻�걨���ŵ�������ѯ
function queryNewRegister()
{
    //��ϢУ��
    //alert("*********************");
    var RptNo = fm.RptNo.value;
    //alert("rptNo",rptNo);
    if(RptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    
    //�����¼��š��¹�����(һ��)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo  "
                + "and llcaserela.caseno= '"+RptNo+"'";*/
            mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql32");
		mySql.addSubPara(RptNo); 
    //prompt("queryNewRegister:�滻�걨���ŵ�������ѯ-�����¼��š��¹�����(һ��)",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18------------19-------20------------------------------------21
   /* var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,(select username from llclaimuser where usercode = llregister.Operator),mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end),AcceptedDate,ApplyDate,Rgtantmobile,postcode from llregister  "
                + "where RgtNo='"+RptNo+"'";*/
                mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql33");
		mySql.addSubPara(RptNo); 
    //prompt("queryNewRegister:�滻�걨���ŵ�������ѯ-���������ż�����������Ϣ(һ��)",strSQL2);
    var RptContent = easyExecSql(mySql.getString());
    //prompt("strSQL2",strSQL2);
    

    //����ҳ������
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];

    fm.RptNo.value = RptContent[0][0];
    //alert("RptNo�ı��:"+fm.RptNo.value);
    fm.ClmNo.value = RptContent[0][0];
    //alert("ClmNo�ı��:"+fm.ClmNo.value);
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
    fm.AcceptedDate.value = RptContent[0][21];
    fm.ApplyDate.value = RptContent[0][22];
    fm.RptorMoPhone.value = RptContent[0][23];
    fm.AppntZipCode.value = RptContent[0][24];
    
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������
    showOneCodeName('llnorgtreason','NoRgtReason','NoRgtReasonName');//������ԭ��
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//�������Ա�
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//����������

    //����ҳ��Ȩ��
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
//        fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = false;              
    }
    else
    {
        fm.MedicalFeeInp.disabled = true;
    }

    //�����ְ�������������Ϣ(����)
    /*var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from llcase where CaseNo = '"+ RptContent[0][0] +"')";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql34");
		mySql.addSubPara(RptContent[0][0]); 
    //prompt("queryNewRegister:�滻�걨���ŵ�������ѯ-�����ְ�������������Ϣ(����)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
    
    //��������������ʾ���ز�
    showDIV();
    
    //��ѯ�ⰸ��־
    QueryClaimFlag();
    
    //�������������ж��Ƿ��ѯƥ��������Ϣ
    if (fm.RgtConclusion.value == '01')
	{
	    afterMatchDutyPayQuery();
        /*var strSQL4 = "select RgtState from llregister where "
                    + "rgtno = '"+rptNo+"'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql35");
		mySql.addSubPara(rptNo); 
        var tRgtState = easyExecSql(mySql.getString());
        if (tRgtState)
        {
            fm.rgtType.value = tRgtState;
            showOneCodeName('rgtType','rgtType','rgtTypeTypeName');
        }
	}
	
}


//��ʼ����������ѯ
function queryRegister()
{
    //��ϢУ��
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    
    //alert("��ʼ����������ѯ");

    
    //�����¼��š��¹�����(һ��)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
            mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql36");
		mySql.addSubPara(rptNo); 
    ////prompt("queryRegister:��ʼ����������ѯ-�����¼��š��¹�����(һ��)",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //���������ż�����������Ϣ(һ��)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18------------19-------20------------------------------------21
   /* var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,(select username from llclaimuser where usercode = llregister.Operator),mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '����' when '2' then '����' when '3' then '��ͥ' end),AcceptedDate,applydate,Rgtantmobile,postcode from llregister "
                + "where rgtno= '"+rptNo+"'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql37");
		mySql.addSubPara(rptNo); 
    var RptContent = easyExecSql(mySql.getString());
    ////prompt("queryRegister:��ʼ����������ѯ-���������ż�����������Ϣ(һ��)",strSQL2);
    

    //����ҳ������
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];

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
    fm.AcceptedDate.value = RptContent[0][21];
    fm.ApplyDate.value = RptContent[0][22];
    fm.RptorMoPhone.value = RptContent[0][23];
    fm.AppntZipCode.value = RptContent[0][24];
    

    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//��������
    showOneCodeName('llnorgtreason','NoRgtReason','NoRgtReasonName');//������ԭ��
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//�������Ա�
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//����������

    //����ҳ��Ȩ��
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
//        fm.printDelayRgt.disabled = true;
        fm.MedicalFeeInp.disabled = false;              
    }
    else
    {
        fm.MedicalFeeInp.disabled = true;
    }


    //�����ְ�������������Ϣ(����)
    /*var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from llcase where CaseNo = '"+ rptNo +"')";*/
            mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql38");
		mySql.addSubPara(rptNo); 
    //prompt("queryRegister:��ʼ����������ѯ-�����ְ�������������Ϣ(����)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        allSubReportGridClick(0);
    }
    
    //��������������ʾ���ز�
    showDIV();

    
    //��ѯ�ⰸ��־
    QueryClaimFlag();

    
    //�������������ж��Ƿ��ѯƥ��������Ϣ
    if (fm.RgtConclusion.value == '01')
	{
	    afterMatchDutyPayQuery();
       /* var strSQL4 = "select RgtState from llregister where "
                    + "rgtno = '"+rptNo+"'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql39");
		mySql.addSubPara(rptNo);
        var tRgtState = easyExecSql(mySql.getString());
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
       /* var strSQL = "select t.name,t.sex,t.phone,t.homeaddress,t.zipcode from laagent t where "
                    + "t.agentcode = '"+fm.AssigneeCode.value+"'";*/
                mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql40");
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
		    fm.AcceptedDate.value = "";
		    fm.ApplyDate.value = "";

		    fm.customerName.value = "";
		    fm.customerAge.value = "";
	 	    fm.customerSex.value = "";
	 	    fm.SexName.value = "";
		    //fm.IsVip.value = "";
		    //fm.VIPValueName.value = "";
		    fm.AccidentDate.value = "";
	 	    fm.occurReason.value = "";
	 	    fm.ReasonName.value = "";
		    fm.hospital.value = "";
		    fm.TreatAreaName.value = "";
		    fm.OtherAccidentDate.value = "";
		    fm.MedicalAccidentDate.value = "";
	 	    fm.accidentDetail.value = "";
//	 	    fm.IsDead.value = "";
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
    location.href="LLClaimScanRegisterMissInput.jsp";
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
        case "04" :
        	if (fm.claimType[2].checked == true)
        	{
        		fm.claimType[4].checked = true;
        	}
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

//��ʾ���ز�
function showDIV()
{
	if (fm.RgtConclusion.value == '01')
	{
	    //��ʾ�����
        //spanConclusion1.style.display="";
        spanConclusion2.style.display="none";
        fm.dutySet.disabled = false;
        fm.medicalFeeCal.disabled = false;
        fm.printNoRgt.disabled = true;
        //fm.printDelayRgt.disabled = true;
        fm.printPassRgt.disabled = false;
        
        //fm.medicalFeeCal.disabled = true;//��֤������Ϣ
   		//fm.printPassRgt.disabled = true;//��ӡ��֤ǩ���嵥
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
        //fm.printDelayRgt.disabled = true;
        
        //fm.printPassRgt.disabled = true;//��ӡ��֤ǩ���嵥
	}
    else if (fm.RgtConclusion.value == '03')
    {
        spanConclusion1.style.display="none";
	    spanConclusion2.style.display="none";
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = true;
        fm.printPassRgt.disabled = false;
        //fm.printDelayRgt.disabled = false;
        
        //fm.printPassRgt.disabled = true;//��ӡ��֤ǩ���嵥
        //fm.printDelayRgt.disabled = true;//��ӡ���䵥֤֪ͨ��
    }
}

//������ѯ�����
function queryLLReport()
{
    //��ϢУ��
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    var strUrl="LLClaimReportQueryMain.jsp?claimNo="+document.all('ClmNo').value+"&customerNo="+fm.customerNo.value+"&Flag=common";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    
}

//�������ѯ
function queryLLIssue()
{
	//��ϢУ��
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    var strUrl="LLClaimIssueQueryMain.jsp?claimNo="+document.all('ClmNo').value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
    /*var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql41");
		mySql.addSubPara(fm.AccidentDate.value );  
		mySql.addSubPara(fm.customerNo.value );    
    var tAccNo = easyExecSql(mySql.getString());
//    alert(tAccNo);
    if (tAccNo == null)
    {
        alert("û������¼���");
        return;
    }
    //���¼���ѯ����
//	var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value+"&AccType="+fm.occurReason.value;
	var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
//	alert(strUrl);
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

//��ѯ���ս��
function queryResult(tCode,tName)
{
    /*var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";*/
    		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql42");
		mySql.addSubPara(document.all(tCode).value );  
    
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//��ӡ��֤ǩ���嵥,����ͨ��(01)ʱ����ʹ��-----���ߴ�ӡ�������ж�
function prtPassRgt()
{
    //�������Ƿ�Ϊ����ͨ��,�����ܴ�ӡ
    /*var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";*/
        		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql43");
		mySql.addSubPara(fm.RptNo.value);  
    var tResult = easyExecSql(mySql.getString());
    if (tResult == null)
    {
        alert("���ȱ�����������!");
        return;
    }

    //���ӵ�֤�Ƿ������У��
    /*var tCertSql = " select affixno,affixcode,affixname,subflag,"
				+ "(case needflag when '0' then '0-��' when '1' then '1-��' end),"
				+ "readycount,shortcount,property "
				+ "from llaffix where '1' = '1' "
				+ "and 1 = 1 "
				+ "and rgtno = '"+fm.RptNo.value+"' "
				+ "and customerno = '"+fm.customerNo.value+"' "
				+ "and (subflag is null or subflag = '1')";*/
	   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql44");
		mySql.addSubPara(fm.RptNo.value);
		mySql.addSubPara(fm.customerNo.value);   
    var tArrCert = easyExecSql(mySql.getString());//prompt("",);
    if(tArrCert){
    	alert("�е�֤��δ������");
    	return false;
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
    /*var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";*/
    	   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql45");
		mySql.addSubPara(fm.RptNo.value);

    var tResult = easyExecSql(mySql.getString());
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
//    document.getElementById("fm").submit();
}

//��ӡ���䵥֤֪ͨ��,�ӳ�����(03)ʱ����ʹ��
function prtDelayRgt()
{
    //�������Ƿ�Ϊ�ӳ�����,�����ܴ�ӡ
    /*var strSQL = "select rgtconclusion from llregister where 1=1 "
               + " and rgtno = '" + fm.RptNo.value + "'";*/
       	   mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql46");
		mySql.addSubPara(fm.RptNo.value);
    var tResult = easyExecSql(mySql.getString());
    if (tResult != '03')
    {
//        alert("���ȱ�����������!");
//        return;
    }
    //������д��ӡ�ύ����
    fm.action = './LLPRTCertificateRenewSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT003")=="false")
    {
    	return;
    }
   
//    fm.target = "../f1print";
//    document.getElementById("fm").submit();
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
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
    
    fm.action = './LLPRTAppraisalSave.jsp';   
    if(beforePrtCheck(fm.ClmNo.value,"PCT001")=="false")
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
    /*var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
			+" and t.otherno='"+tclmno+"' "
			+" and trim(t.code)='"+tcode+"' ";*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql47");
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
		fm.PrtSeq.value=arrLoprt[0][0];//��֤��ˮ��
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
//	 			window.open(strUrl,"��֤����");
	 			window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	 		}
		}
	} 
}

//ҽԺģ����ѯ
function showHospital(tCode,tName)
{
	var strUrl="LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//����ϸ�ڲ�ѯ
function showAccDetail(tCode,tName)
{
	var strUrl="LLColQueryAccDetailInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��֤������ӡ����
function showPrtControl()
{
    var tClmNo = fm.RptNo.value;
	/*var strSQL="select count(1) from loprtmanager a,llparaprint b where 1=1 and a.code=b.prtcode "
			+" and a.stateflag='3' and a.othernotype='05' and a.otherno='"+tClmNo+"'"
			+" order by a.code";*/
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql48");
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


//�ռ�����Ϣ¼��,Added by niuzj,2005-10-24
function showReciInfoInp()
{
	var tClmNo = fm.RptNo.value;   //�ⰸ��
	var tcustomerNo=fm.customerNo.value;  //�����˴���
	var tIsShow = 1;               //[����]��ť�ܷ���ʾ,0-����ʾ,1-��ʾ
  var tRgtObj = 1;               //�������ձ�־,1-����,2-����
	
	//�õ���ҳʱ,���дһ��Mainҳ��
	var strUrl="LLClaimReciInfoMain.jsp?ClmNo="+tClmNo+"&IsShow="+tIsShow+"&RgtObj="+tRgtObj+"&CustomerNo="+tcustomerNo;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 
	//location.href="LLClaimReciInfoInput.jsp?claimNo="+tClmNo; 
}

//�������¼��
function showWaiBao()
{
	  if(!KillTwoWindows(fm.RptNo.value,'20'))
	  {
	  	  return false;
	  }	
	var tClmNo = fm.ClmNo.value;
  var tClmState = fm.clmState.value;
  tClmState = tClmState.substring(0,2);
  var tMissionID = fm.MissionID.value;
  var tSubMissionID = fm.SubMissionID.value;
  var tActivityID = "0000005015";
  
  //���llregister����FeeInputFlag�ֶε�ֵΪ0��2,�����Է������¼��
  /*var strSQL=" select a.FeeInputFlag from llregister a "
            +" where a.rgtno='"+ tClmNo +"' ";*/
      mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql49");
		mySql.addSubPara(tClmNo); 
  var arr = easyExecSql( mySql.getString() );
  if(arr[0][0]==1)  //���ֵΪ1,��������
  {
    alert("ҽ�Ƶ�֤���¼�뻹δ���,�㲻���ٴη������¼��!");
  } 
  else
  {
	  fm.action="LLClaimFaQiWaiBaoSave.jsp?ClmNo="+tClmNo+"&ClmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	  submitForm();
  }
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����������˺ͱ�������ͬһ���˵Ļ�����ô�����˵���Ϣ�ӳ����˵�
              ��Ϣ�еõ�
    �� �� �ˣ������
    �޸����ڣ�2005.11.15
   =========================================================================
**/
function getRptorInfo()
{
   var tCustomerNo = fm.customerNo.value ;
   var tRelation = fm.Relation.value;
   var tRelationName = fm.RelationName.value;
   if(tRelation == "00"|| tRelationName == "����")
   {
      if( tCustomerNo != null  && tCustomerNo != "")
      {
         // var strSQL = "select postaladdress,phone from lcaddress where customerno ='"+tCustomerNo+"'";
         mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql50");
		mySql.addSubPara(tCustomerNo ); 
          var strQueryResult = easyExecSql(mySql.getString());
          if(strQueryResult==null || strQueryResult=="")
          {
          	return;
          }
          else
          {
            fm.RptorAddress.value = strQueryResult[0][0];
            fm.RptorPhone.value = strQueryResult[0][1];
            fm.RptorName.value = fm.customerName.value;
          }
      }else{
          return;
      }
   }
   
 //�޸�ԭ������������˺ͳ����˹�ϵ��Ϊ��GX02-����������Ա��ʱ����ô�����˵���Ϣȡ����������Ա����Ϣ
   //�� �� �ˣ�zhangzheng 
   //�޸�ʱ�䣺2008-12-16
   //MSû�б���������Ա,ȥ������߼�
   
   //else if(tRelation == "GX02" || tRelationName == "����������Ա")
   //{
   	 //if( tCustomerNo != null  && tCustomerNo != "")
   	 //{
   	 	   //var strSQL =" select b.name, b.homeaddress, b.phone from laagent b " 
           //         +" where 1=1 "
           //         +" and b.agentcode in (select distinct trim(a.agentcode) from lccont a where a.insuredno = '"+tCustomerNo+"') ";
         //var arr = easyExecSql(strSQL);
         // if(arr==null || arr=="")
         // {
         // 	return;
         // }
         // else
         // {
         //    fm.RptorName.value = arr[0][0];
         //    fm.RptorAddress.value = arr[0][1];
         //    fm.RptorPhone.value = arr[0][2];
         // }
         
   	// }
   	// else
   	 //{
   	 	// return;
   	 //}
   //}
   else{
       return;
   }
}

//��ѯ����ҽԺ��Added by niuzj,2005-11-26
function queryHospital(tCode,tName)
{
	  /*var strSql =" select hospitalname from llcommendhospital  "
	             +" where trim(hospitalcode)='"+document.all(tCode).value+"' ";*/
	  mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql51");
		mySql.addSubPara(document.all(tCode).value); 
	  var tICDName = easyExecSql(strSql);
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}


/*
 * ����У��,У���������ڶ��������ڵ�ǰ���ڣ��ҵ�2�����ڲ������ڵ�1������
 * Date1,����ĵ�һ������,�˴�Ϊ�¹�����
 * Date2 ����ĵڶ�������,�˴��������Ϊҽ�Ƴ������ڻ�������������
 * tDateName ������������ƣ�������ɵ�������ʾ����
 */
function checkDate(tDate1,tDate2,tDateName)
{
    
    var AccDate  =  tDate1;//�¹�����
    var AccDate2 =  tDate2;//������������
   
    //alert("AccDate:"+AccDate);
    //alert("AccDate2:"+AccDate2);
    //alert("tDateName:"+tDateName);
       
    //����¹�����
    if (AccDate == null || AccDate == '')
    {
        alert("�������¹����ڣ�");
        return false;
    }
    else
    {       
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("�¹����ڲ������ڵ�ǰ����!");
            return false; 
        }
    }
    
    //��֤�¹��������������ϵĴ������ⰸ--------BEG
    if (fm.BaccDate.value == null || fm.BaccDate.value == '')//Modify by zhaorx 2006-07-31
    {
    	fm.BaccDate.value = AccDate; //��������ʱ����У���¹�����
    }    
    
    var OAccDate = fm.BaccDate.value;//ԭ�¹�����Modify by zhaorx 2006-07-04
   
    if (OAccDate != AccDate)
    {    
        /*var strSQL = " select count(*) from llreport a left join llregister b on a.rptno = b.rgtno "
                    + " where nvl(clmstate,0) != '70' "
                    + " and rptno in (select caseno from llcaserela where caserelano = '" +	fm.AccNo.value + "')"*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimScanRegisterInputSql");
		mySql.setSqlId("LLClaimScanRegisterSql52");
		mySql.addSubPara(fm.AccNo.value ); 
        
        //prompt("��֤�¹��������������ϵĴ������ⰸ",strSQL);
        
		var tCount = easyExecSql(mySql.getString());
		
        if (tCount != null && tCount != "1" && tCount != "0")
        {
            alert("�¹��������������ϵĴ������ⰸ���������޸��¹����ڣ�");
            fm.AccidentDate.value = OAccDate;
            return false;
        }
    }
    

    //У���������
    if (AccDate2 == null || AccDate2 == '')
    {
        alert(tDateName+"����Ϊ�գ�");
        return false;
    }
    else
    {
       	//�Ƚϳ������ں͵�ǰ����
    	if (dateDiff(mCurrentDate,AccDate2,'D') > 0)
        {
        	alert(tDateName+"�������ڵ�ǰ����!");
            return false; 
        }

        //�Ƚϳ������ں��¹�����       
    	if (dateDiff(AccDate2,AccDate,'D') > 0)
        {
        	alert(tDateName+"���������¹�����!");
            return false; 
        }

    }
    
    return true;
}
