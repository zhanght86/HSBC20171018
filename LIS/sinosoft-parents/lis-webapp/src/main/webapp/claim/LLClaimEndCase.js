var showInfo;
var printflag=false;
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
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
	unlockScreen('lp001screen');
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
        //�����б���Ϣ
        LLClaimQuery();

        //fm.confGetMoney.value='�Ѹ�';
    }
        
    if (fm.ClmState.value == "60" && fm.EndCaseDate.value != "")
    {
        fm.addSave.disabled = true;
    }
    else
    {
        fm.addSave.disabled = false;
    }
    
    tSaveFlag ="0";
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
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
        goToBack();
    }
    tSaveFlag ="0";
}

//[����]��ť��Ӧ����
function saveClick()
{
	//�����δ����Ķ��ˣ�����ϵͳ��ʾ������Ӱ��᰸����
	  /*var strSqlCuw = "select count(*) from LLCUWBatch where "
	              + " caseno = '" + fm.ClmNo.value + "' and InEffectFlag='0'" ;*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql1");
		mySql.addSubPara(fm.ClmNo.value ); 
	  var tCountcuw = easyExecSql(mySql.getString());

	  if (tCountcuw>0)
	    {     
		  if(!confirm("��ע�⣬��δ������Ķ��˽��ۣ���ȷ�Ͻ��н᰸����"))
	       {
	          return false;
	       }
	    }

	  if(!KillTwoWindows(fm.ClmNo.value,'50'))
	  {
	  	  return false;
	  }	
    if (fm.ClmNo.value == "" || fm.ClmNo.value == null)
    {
    	  alert("��ѡ����Ҫ������ⰸ!");
    	  return;
    }
    
	if(fm.opYZLX.value==""){
		alert("��ѡ���Ƿ����������Ϣ");
		return;
	}
	
	lockScreen('lp001screen');
    fm.fmtransact.value = "UPDATE";
    fm.action = './LLClaimEndCaseSave.jsp';
    submitForm();
}

//[����]�����ӳ���Ϣ��ť
function YZLX_compute()
{
	if (fm.ClmNo.value=="")
	{
		alert("�����ⰸ��ѯ");
		return;
	}
	if(fm.opYZLX.value=="N")
	fm.fmtransact.value="YZLX_delete";
	else if(fm.opYZLX.value=="Y")
	fm.fmtransact.value="YZLX_compute";
	else{
		alert("��ѡ���Ƿ����������Ϣ");
		return false;
		}
	fm.btnYZLX.disabled=true;
		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";

	fm.action="./LLClaimYZLX.jsp";
	fm.target = "fraSubmit";
	submitForm();
	
}

//[�᰸ȷ��]��ť��Ӧ����
function EndSaveClick()
{
	if(!KillTwoWindows(fm.ClmNo.value,'60'))
	{
	  	  return false;
	}
	
    if (fm.ClmNo.value == "" || fm.ClmNo.value == null)
    {
    	  alert("��ѡ����Ҫ������ⰸ!");
    	  return;
    }
    if (fm.ClmState.value != "60" && fm.EndCaseDate.value == "")
    {
        alert("�������᰸����!");
        return;
    }
    
    //����ȷ��ǰ�����ӡ���������ע��ܸ�֪ͨ�� 	��ӡ״̬��0�����ã�1�����Ѵ�2�����Ѵ�3���򲻴�,4����δ����
   /* var strSql="select distinct t.stateflag from loprtmanager t where code in('PCT010','PCT006') "
		+" and t.otherno='"+fm.ClmNo.value+"' ";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql2");
		mySql.addSubPara(fm.ClmNo.value ); 
    //prompt("����ȷ��ǰ�����ӡ���������ע��ܸ�֪ͨ��",strSql);
    var arrLoprt = easyExecSql(mySql.getString());
    if(arrLoprt==null)
    {
    	alert("û���ҵ��õ�֤����ˮ��");
    	return false;
    }	
    else 
    {
    	for(var i=0;i<arrLoprt[0].length;i++)
    	{
    		if(arrLoprt[0][i]!='1')
    		{
    			printflag=true;
    			break;
    		}
    	}
    }
    
    
    //У������ӡ���������ע�ͺ�ͬ������ע
    if(printflag ==true)
    {
        alert("���ȴ�ӡ֪ͨ��!");
        return false;
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
    /*var strSQL = "select clmno,clmstate,givetype,(select username from llclaimuser where usercode = llclaim.clmuwer),endcasedate "
               + " from llclaim "
               + " where clmno = '"+fm.ClaimNo.value+"' "
               + " order by clmno";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql3");
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
    
    //��ѯ����������
    afterMatchDutyPayQuery();
    
    //��ѯ�Ƿ�����ӳ���Ϣ
    afterClaimYZLXBLQuery();
}


//���ⰸ��صĿͻ���Ϣ�б�
function LLReCustomerGridQuery()
{
	var rptno=fm.ClmNo.value;
	/*var strSQL = " select customerno,name,"
	                   + " sex,"
	                   + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' else '����' end) as SexNA,"
	                   + " birthday,"
                       + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                       + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
	                   + " from ldperson where "
	                   + " customerno in("
	                   + " select customerno from llsubreport where subrptno = '"+ rptno +"')";*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql4");
		mySql.addSubPara(rptno ); 
//	alert("strSQL= "+strSQL);
	easyQueryVer3Modal(mySql.getString(), LLReCustomerGrid);
}

//���ذ�ť
function goToBack()
{
	
	if (fm.RgtObjNo.value!=null && fm.RgtObjNo.value!="")
  { 
    location.href="../claimgrp/LLGrpClaimEndCaseMissInput.jsp";
  }    
  else
  {
  	location.href="LLClaimEndCaseMissInput.jsp";
  }	

}

//[��ӡ��֤]��ť
function SinglePrtPrint()
{
	printflag=false;
	
	fm.prtType.value = fm.PrtCode.value;
    //���û�н᰸���棬�������ӡ��֤��by niuzj,2005-12-09
    var tClmState=fm.ClmState.value;
    if(tClmState != '60')	
    {
    	alert("û�н᰸���棬�������ӡ��֤��");
    	return false;
    }
    
    if(fm.PrtCode.value=="")
    {
    	alert("��ѡ��һ�ֵ�֤!");
    	return;
    }
    if(fm.PrtCode.value=="PCT013")
    {
		fm.action="LLClaimEndCasePrintContSave.jsp";
    }
    else if(fm.PrtCode.value=="PCT005" || fm.PrtCode.value=="PCT006")
    {
        fm.action="LLClaimEndCaseProtestPrint.jsp";
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
	printflag=false;
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
	printflag=false;
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


/**********************************
//��ӡǰ���飨������Ҫ�����������֤���롢�ⰸ�ţ�--------2005-08-22���
***********************************/
function beforePrtCheck(clmno,code)
{
	//��ѯ��֤��ˮ�ţ���Ӧ�������루�ⰸ�ţ����������͡���ӡ��ʽ����ӡ״̬�������־
	printflag=false;
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
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql5");
		mySql.addSubPara(tclmno ); 
		mySql.addSubPara(tcode ); 
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
			fm.submit();
	 	}
		else
		{
			//���ڵ��Ѿ���ӡ��
			if(confirm("�õ�֤�Ѿ���ӡ��ɣ���ȷʵҪ������"))
	 		{
	 			//���벹��ԭ��¼��ҳ��
	 			var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value+"&prtType="+fm.prtType.value;
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
    //��ѯ����ʵ����¼
   /* var strSql = " select count(1) from ljaget a where a.enteraccdate is null "
               + " and a.otherno = '" + fm.ClmNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql6");
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
    
    //�ڹرյ�״̬ʱʵ�����޼�¼
    /*var strSql2 = " select count(1) from ljaget a where 1=1 "
                + " and a.otherno = '" + fm.ClmNo.value + "'";*/
      mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql7");
		mySql.addSubPara(fm.ClmNo.value ); 
    var tCount = easyExecSql(mySql.getString());
    if (tCount == "0")
    {
        tValue = "��";
    }
    
    //fm.confGetMoney.value = tValue;
}

//��֤������ӡ����
function showPrtControl()
{
	printflag=false;
    var tClmNo = fm.ClmNo.value;
	/*var strSQL="select count(1) from loprtmanager a,llparaprint b where 1=1 and a.code=b.prtcode "
			+" and a.stateflag='3' and a.othernotype='05' and a.otherno='"+tClmNo+"'"
			+" order by a.code";*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql8");
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
	var tsel = LLReCustomerGrid.getSelNo()-1;
	if(tsel<0)
  {
  	alert("��ѡ��һ���ͻ���¼!");
  	return;
  }
	fm.CustomerNo.value=LLReCustomerGrid.getRowColData(tsel,1);
	var tcustomerNo=fm.CustomerNo.value;  //�����˴���
	var tIsShow = 1;               //[����]��ť�ܷ���ʾ,0-����ʾ,1-��ʾ
  var tRgtObj = 1;               //�������ձ�־,1-����,2-����
	
	//�õ���ҳʱ,���дһ��Mainҳ��
	var strUrl="LLClaimReciInfoMain.jsp?ClmNo="+tClmNo+"&IsShow="+tIsShow+"&RgtObj="+tRgtObj+"&CustomerNo="+tcustomerNo;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 
	//location.href="LLClaimReciInfoInput.jsp?claimNo="+tClmNo; 
}


function afterCodeSelect(){
	if(fm.opYZLX.value=="N"){
		fm.btnYZLX.value="ȡ��������Ϣ";
		fm.btnYZLX.disabled=false;
		}
	else if(fm.opYZLX.value=="Y"){
		fm.btnYZLX.value="����������Ϣ";
		fm.btnYZLX.disabled=true;
		}
	else{
		fm.opYZLX.value="N";
		fm.btnYZLX.value="ȡ��������Ϣ";
		fm.btnYZLX.disabled=false;
//		alert("��ѡ���Ƿ����������Ϣ");
//		fm.btnYZLX.disabled=false;
		
//		return false;
		}
	fm.btnYZLX.disabled=false;
}

//��ʼ����ѯ������
//ƥ���Ĳ�ѯ
function afterMatchDutyPayQuery()
{
    var tSql;
    var arr = new Array;

    var tClaimNo = fm.ClmNo.value;         //�ⰸ��
        

    //��ѯLLClaimDetail,��ѯ�����������ͱ���������Ϣ
    ////to_char(b.PayEndDate,'yyyy-mm-dd')+a.GracePeriod," //�������� + ������--+ a.GracePeriod
    /*tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
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
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql9");
		mySql.addSubPara(tClaimNo);   
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


//��ѯ�Ƿ�����ӳ���Ϣ
function afterClaimYZLXBLQuery()
{
  var tSql;
  var arr = new Array;

  var tClaimNo = fm.ClmNo.value;         //�ⰸ��
      
  //tSql = " select 1 from ljsgetclaim where otherno='"+tClaimNo+"' and feefinatype='YCLX'";   
 	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimEndCaseInputSql");
		mySql.setSqlId("LLClaimEndCaseSql10");
		mySql.addSubPara(tClaimNo);       
  //prompt("��ѯ�Ƿ��Ѿ������ӳ���Ϣsql:",tSql);
  arr = easyExecSql( mySql.getString() );
  if (arr)
  {
      fm.opYZLX.value='Y';
      fm.opYZLXName.value='��';
  }
 
}