//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var mySql = new SqlClass();


//�ύ��ɺ���������
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
    }

    if(fm.IssueConclusion.value == "01")
    {

    	 //var tSql = " select MissionID,SubMissionID,ActivityID from lwmission where missionprop1 = '"+document.all('ClmNo').value+"' and activityid='0000005010'";
    	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterAuditInputSql");
		mySql.setSqlId("LLClaimRegisterAuditSql1");
		mySql.addSubPara( document.all('ClmNo').value);
         arr = easyExecSql( mySql.getString() );
    	// location.href="../claim/LLClaimScanRegisterInput.jsp?claimNo="+document.all('ClmNo').value+"&isNew=0&MissionID="+arr[0][0]+"&SubMissionID="+arr[0][1]+"&ActivityID="+arr[0][2];
    	window.open("./LLClaimScanRegisterMain.jsp?claimNo="+document.all('ClmNo').value+"&isNew=0&prtNo="+document.all('ClmNo').value+"&SubType=CA001&MissionID="+arr[0][0]+"&SubMissionID="+arr[0][1]+"&ActivityID="+arr[0][2]," ","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    }
    else
    {
    	goToBack();
    }
}

//�ͻ���ѯ
function showInsPerQuery()
{
	var strUrl="LLLdPersonQueryMain.jsp?Flag=1";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryMain.jsp");
}

//������ѯ
//���ա��ͻ��š���LCpol�н��в�ѯ����ʾ�ÿͻ��ı�����ϸ
function showInsuredLCPol()
{
  var strUrl="LLLcContQueryMainClmScan.jsp";
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//Ӱ���ѯ---------------2005-08-14���
function colQueryImage()
{
    var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//������֤����
function showRgtAddMAffixListClmScan()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
    var tClmNo=fm.ClmNo.value;
//    var tcustomerNo=fm.CustomerNo.value;
    var strUrl="LLRgtAddMAffixListClmScanMain.jsp?ClmNo="+tClmNo+"&Proc=RgtAdd";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
    //window.open(strUrl,"��֤����");
}

function IssueConclusionSave()
{

    if(fm.IssueConclusion.value == "")
    {
        alert("��ѡ�������ۣ�");
        return;
    }
	
	if(fm.IssueConclusion.value == "02")
	{
		//var strSQL="select 1 from LLAffix where rgtno='" + fm.ClmNo.value + "' and SupplyStage='01'";
		    	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterAuditInputSql");
		mySql.setSqlId("LLClaimRegisterAuditSql2");
		mySql.addSubPara(fm.ClmNo.value);
		var LLAffixExist = easyExecSql(mySql.getString());
		if(LLAffixExist == null || LLAffixExist == "")
		{
			alert("ѡ���˲��ϲ����˻ؽ���,��δ���䵥֤�����ܽ��г�����۱��棡");
			return false;
		}
	}
	else if (fm.IssueConclusion.value == "03")
	{
		if(fm.AccDesc.value == null || fm.AccDesc.value =="")
		{
			alert("ѡ���������˻ؽ���,��δ¼���˻�˵�������ܽ��г�����۱��棡");
			return false;
		}
	}

		fm.Operate.value="Insert";
		submitForm();

}

function submitForm()
{
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.submit();  
}

//���ذ�ť
function goToBack()
{
    location.href="LLClaimScanRegisterMissInput1.jsp";
}

function choiseIssueConclusion()
{
	  if (fm.IssueConclusion.value == '02')
		{
		    divAffix.style.display= "";
		    divAccDesc.style.display= "";
	    }
	  else if (fm.IssueConclusion.value == '03')
	    {
	    	divAffix.style.display= "none";
	    	divAccDesc.style.display= "";
	    }
	  else 
	  {
		    divAffix.style.display= "none";
		    divAccDesc.style.display= "none";
	  }
}
