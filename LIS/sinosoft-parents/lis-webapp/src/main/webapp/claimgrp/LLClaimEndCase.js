var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//������
function zhoulei()
{
	alert("������!");
}

//�ύ����
function submitForm()
{
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
 

//    showSubmitFrame(mDebug);
//    fm.fmtransact.value = "INSERT"
    fm.submit(); //�ύ
    tSaveFlag ="0";
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


        //�����б���Ϣ
        LLClaimQuery();
        fm.addSave.disabled = true;
    }
    
	unlockScreen('lp002screen');
    tSaveFlag ="0";
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
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

//[����]��ť��Ӧ����
function saveClick()
{
    if (fm.ClmNo.value == "" || fm.ClmNo.value == null)
    {
    	  alert("��ѡ����Ҫ������ⰸ!");
    	  return;
    }
	lockScreen('lp002screen');
    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimEndCaseSave.jsp';
    submitForm();
}

//[�᰸ȷ��]��ť��Ӧ����
function EndSaveClick()
{
    if (fm.ClmNo.value == "" || fm.ClmNo.value == null)
    {
    	  alert("��ѡ����Ҫ������ⰸ!");
    	  return;
    }
    if (fm.ClmState.value != "60")
    {
        alert("�������᰸����!");
        return;
    }
//    //�����Ƿ�ʵ��
//    var strSql = " select count(1) from ljaget a where a.enteraccdate is null "
//               + " and a.otherno = '" + fm.ClmNo.value + "'";
//    var tConfDate = easyExecSql(strSql);
//    if (tConfDate != "0")
//    {
//        alert("�����δʵ�����,��������ȷ��!");
//        return;
//    }
    fm.fmtransact.value = "";
    fm.action = './LLClaimEndCaseConfirmSave.jsp';
    fm.target="fraSubmit"
    submitForm();
}

//LLClaimGrid��Ӧ�¼�
function LLClaimGridClick()
{
	var i = LLClaimGrid.getSelNo();
    if (i != 0)
    {
        i = i - 1;
        fm.ClmNo.value = LLClaimGrid.getRowColData(i,1);
        fm.GetDutyKind.value = LLClaimGrid.getRowColData(i,2);
//        fm.ClmState.value = LLClaimGrid.getRowColData(i,3);
        fm.ClmState.value = '10';
        fm.StandPay.value = LLClaimGrid.getRowColData(i,4);
        fm.BeforePay.value = LLClaimGrid.getRowColData(i,5);
        fm.RealPay.value = LLClaimGrid.getRowColData(i,6);
        fm.GiveType.value = LLClaimGrid.getRowColData(i,7);
        fm.GiveTypeDesc.value = LLClaimGrid.getRowColData(i,8);
        fm.ClmUWer.value = LLClaimGrid.getRowColData(i,9);
        fm.DeclineNo.value = LLClaimGrid.getRowColData(i,10);
        fm.EndCaseDate.value = LLClaimGrid.getRowColData(i,11);
        fm.CasePayType.value = LLClaimGrid.getRowColData(i,12);
        fm.CheckType.value = LLClaimGrid.getRowColData(i,13);
        showOneCodeName('llclaimstate','ClmState','ClmStateName');
    }
}

//�ⰸ��ѯ
function LLClaimQuery()
{
    //---------------------1-------2---------3-------4----------5--
   /* var strSQL = "select clmno,clmstate,givetype,clmuwer,endcasedate "
               + " from llclaim "
               + " where clmno = '"+fm.ClaimNo.value+"' "
               + " order by clmno";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimEndCaseInputSql");
	mySql.setSqlId("LLClaimEndCaseSql1");
	mySql.addSubPara(fm.ClaimNo.value ); 
    var tClaim = easyExecSql(mySql.getString());
//    alert(tClaim);
    if (tClaim != null)
    {
        fm.ClmNo.value = tClaim[0][0];
        fm.ClmState.value = tClaim[0][1];
        fm.GiveType.value = tClaim[0][2];
        fm.ClmUWer.value = tClaim[0][3];
        fm.EndCaseDate.value = tClaim[0][4];
        showOneCodeName('llclaimstate','ClmState','ClmStateName');
        showOneCodeName('llclaimconclusion','GiveType','GiveTypeName');
    }
    
    if (fm.ClmState.value == "60" && fm.EndCaseDate.value != "")
    {
        fm.addSave.disabled = true;
    }
    //��ѯʵ����־
    queryConfDate();
}


//���ⰸ��صĿͻ���Ϣ�б�
function LLReCustomerGridQuery()
{
	var rptno=fm.ClmNo.value;
//	var strSQL = "select customerno,name,sex,birthday,vipvalue from ldperson where "
//	        + "customerno in("
//	        + "select customerno from llsubreport where subrptno = '"+ rptno +"')";
	
   /* var strSQL = "select CustomerNo,Name," 
	    	   + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
	    	   + " Birthday,"
	    	   + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
	    	   + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
	    	   + " from LDPerson where "
	    	   + " CustomerNo in( select CustomerNo from LLCase where CaseNo = '"+ rptno +"')";*/
	 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimEndCaseInputSql");
	mySql.setSqlId("LLClaimEndCaseSql2");
	mySql.addSubPara(rptno); 
	//prompt("strSQL",strSQL);
	easyQueryVer3Modal(mySql.getString(), LLReCustomerGrid);
}

//���ذ�ť
function goToBack()
{
    location.href="LLClaimEndCaseMissInput.jsp";
}

//[��ӡ��֤]��ť
function SinglePrtPrint()
{
    if(fm.PrtCode.value=="")
    {
    	alert("��ѡ��һ�ֵ�֤!");
    	return;
    }
    if(fm.PrtCode.value=="PCT013")
    {
		fm.action="LLClaimEndCasePrintContSave.jsp";
    }
	else
	{
		fm.action="LLClaimEndCaseParaPrint.jsp";
	}
    fm.fmtransact.value="SinglePrt||Print";
    if(beforePrtCheck(fm.ClmNo.value,fm.PrtCode.value)==false)
    {
    	return;
    }
//    fm.target = "../f1print";
//	fm.submit();
}

//[������ӡ]��ť-----����������ӡ��PrtSubType----B��
function BatchPrtPrintB()
{
	var tsel = LLReCustomerGrid.getSelNo()-1; 
    if(tsel<0)
    {
    	alert("��ѡ��һ���ͻ���¼!");
    	return;
    }
     fm.CustomerNo.value=LLReCustomerGrid.getRowColData(tsel,1);
//    fm.fmtransact.value="BatchPrtB||Print";
//    fm.action="LLEndCaseParaPrint.jsp";
////    fm.target = "../f1print";
//    submitForm();    
}

//[�嵥��ӡ]��ť-----���嵥����ӡ��PrtSubType----C��
function BatchPrtPrintC()
{
	var tsel = LLReCustomerGrid.getSelNo()-1; 
    if(tsel<0)
    {
    	alert("��ѡ��һ���ͻ���¼!");
    	return;
    }
    fm.CustomerNo.value=LLReCustomerGrid.getRowColData(tsel,1);    
//    fm.fmtransact.value="BatchPrtC||Print";
//    fm.action="LLEndCaseParaPrint.jsp";
////    fm.target = "../f1print";
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
	mySql.setResourceName("claimgrp.LLClaimEndCaseInputSql");
	mySql.setSqlId("LLClaimEndCaseSql3");
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
	 	if(tstateflag=="0")
	 	{
//			fm.action = './LLPRTCertificatePutOutSave.jsp';   //
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
//	 			window.open(strUrl,"��֤����");
	 			window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	 		}
		}
	} 
}

//�ⰸ��ѯ
function queryClick()
{
    if (fm.ClaimNo.value == '')
    {
        alert("�ⰸ��Ϊ��!");
        return;
    }
    var strUrl="LLClaimQueryInput.jsp?claimNo="+fm.ClaimNo.value+"&phase=0";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//����ʵ����ѯ
function queryConfDate()
{
   /* var strSql = " select nvl(count(1),0) from ljaget a where a.enteraccdate is null "
               + " and a.otherno = '" + fm.ClmNo.value + "' and operstate='0' ";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimEndCaseInputSql");
	mySql.setSqlId("LLClaimEndCaseSql4");
	mySql.addSubPara(fm.ClmNo.value);
    //prompt(" ����ʵ����ѯ",strSql);
    var tConfDate = easyExecSql(mySql.getString());
    if (tConfDate == "0")
    {
        fm.confGetMoney.value = "��";
    }
    else
    {
        fm.confGetMoney.value = "��";
    }
}