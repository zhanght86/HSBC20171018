//�������ƣ�PDLDPlanFeeRelaInput.js
//������: �ۼ�������
//�������ڣ�2016-5-12
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var Mulline1GridturnPage = new turnPageClass();
// ����sql�����ļ�
var tResourceName = "productdef.PDLDPlanFeeRelaInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function returnParent() {
	top.opener.focus();
	top.close();
}


/* ��ԃ�ۼ���������Ϣ */
function queryFeeInfo() {
	// �U�N���a
	var getdutycode=document.all("GetDutyCode").value;
	if (getdutycode == null || getdutycode == "") {
		alter("������������α���!");
		return;
	} else {
		var mySql_1 = new SqlClass();
		var sqlid = "PDLDPlanFeeRelaInputSql1"; // ָ��sqlID
		mySql_1.setResourceName(tResourceName); // ָ��ʹ�õ�properties�ļ���
		mySql_1.setSqlId(sqlid);// ָ��ʹ�õ�Sql��id
		// mySql.addSubPara(triskcode);//ָ������Ĳ���
		mySql_1.addSubPara(getdutycode);// ָ������Ĳ���
		var sql1 = mySql_1.getString();
		// ��ԃ��ԓ�U�N�����е��ۼ�����Ϣ
		Mulline1GridturnPage.pageLineNum = 10;
		Mulline1GridturnPage.queryModal(sql1, Mulline1Grid);

		if (Mulline1Grid.mulLineCount <= 0) {
			initMulline1Grid();
			alert("û�в�ѯ���˵�������Ϣ!");
			return false;
		}
	}
}
/*ѡ��һ���˵���Ϣ������ʾ*/
function showFeeCodeInfo(){
	var selNo = Mulline1Grid.getSelNo();
	if(selNo == 0)
	{
		alert("��ѡ��һ����¼��");
		return;
	} else {
	try{
		// ���ϼƻ����� PlanCode
		var plancode=Mulline1Grid.getRowColData(selNo-1,1);
		document.all("PlanCode").value=plancode;
	}catch(ex){}
	try{
		// ���ϼƻ����� PlanCodename
		var plancodename=Mulline1Grid.getRowColData(selNo-1,2);
		if(plancodename=="" && Mulline1Grid.getRowColData(selNo-1,1)=="A"){
			document.all("PlanCodeName").value="����";
		}else{
			document.all("PlanCodeName").value=plancodename;
		}
	}catch(ex){}
	try{
		// riskcode1
		var RiskCode1=Mulline1Grid.getRowColData(selNo-1,3);
		document.all("RiskCode1").value=RiskCode1;
	}catch(ex){}
	try{
		// riskcode1
		var DutyCode=Mulline1Grid.getRowColData(selNo-1,4);
		document.all("DutyCode").value=DutyCode;
	}catch(ex){}
	try{
		// getdutycode1
		var GetDutyCode1=Mulline1Grid.getRowColData(selNo-1,5);
		document.all("GetDutyCode1").value=GetDutyCode1;
	}catch(ex){}
	try{
		// �˵���������� FeeCode
		var FeeCode=Mulline1Grid.getRowColData(selNo-1,6);
		document.all("FeeCode").value=FeeCode;
	}catch(ex){}
	try{
		// �˵���������� FeeCodename
		var FeeCodename=Mulline1Grid.getRowColData(selNo-1,7);
		if(FeeCodename=="" && Mulline1Grid.getRowColData(selNo-1,6)=='0000'){
			document.all("FeeCodeName").value='����';
		}else{
			document.all("FeeCodeName").value=FeeCodename;
		}
	}catch(ex){}
	try{
		// �������� FeeType
		var FeeType=Mulline1Grid.getRowColData(selNo-1,8);
		document.all("FeeType").value=FeeType;
	}catch(ex){}
	try{
		// �������� FeeTypeName
		var FeeTypeName=Mulline1Grid.getRowColData(selNo-1,9);
		document.all("FeeTypeName").value=FeeTypeName;
	}catch(ex){}
	try{
		// �Ƿ�۳��Ը�����  standflag1
		var ifselfPayPercent=Mulline1Grid.getRowColData(selNo-1,10);
		document.all("ifselfPayPercent").value=ifselfPayPercent;
	}catch(ex){}
	try{
		// �Ƿ�۳��Ը�����  standflag1Name
		var ifselfPayPercentName=Mulline1Grid.getRowColData(selNo-1,11);
		document.all("ifselfPayPercentName").value=ifselfPayPercentName;
	}catch(ex){}
	try{
		// �Ը����� standflag2
		var selfPayPercent=Mulline1Grid.getRowColData(selNo-1,12);
		document.all("selfPayPercent").value=selfPayPercent;
	}catch(ex){}
	try{
		// ����� preauthflag
		var IfPayMoney=Mulline1Grid.getRowColData(selNo-1,13);
		document.all("IfPayMoney").value=IfPayMoney;
	}catch(ex){}
	try{
		// ������Ƚ������
		var tMoneyLimitAnnual=Mulline1Grid.getRowColData(selNo-1,14);
		document.all("MoneyLimitAnnual").value=tMoneyLimitAnnual;
	}catch(ex){}
	try{
		// ������ȴ�������
		var tCountLimitAnnual=Mulline1Grid.getRowColData(selNo-1,15);
		document.all("CountLimitAnnual").value=tCountLimitAnnual;
	}catch(ex){}
	try{
		// ���������������
		var tDaysLimitAnnual=Mulline1Grid.getRowColData(selNo-1,16);
		document.all("DaysLimitAnnual").value=tDaysLimitAnnual;
	}catch(ex){}
	try{
		// ÿ���⳥�������
		var tMoneyLimitEveryTime=Mulline1Grid.getRowColData(selNo-1,17);
		document.all("MoneyLimitEveryTime").value=tMoneyLimitEveryTime;
	}catch(ex){}
	try{
		// ÿ���⳥��������
		var tDaysLimitEveryTime=Mulline1Grid.getRowColData(selNo-1,18);
		document.all("DaysLimitEveryTime").value=tDaysLimitEveryTime;
	}catch(ex){}
	try{
		// ÿ���⸶�������
		var tMoneyLimitEveryDay=Mulline1Grid.getRowColData(selNo-1,19);
		document.all("MoneyLimitEveryDay").value=tMoneyLimitEveryDay;
	}catch(ex){}
	try{
		// ÿ���⳥�̶����
		var tPayMoneyEveryDay=Mulline1Grid.getRowColData(selNo-1,20);
		document.all("PayMoneyEveryDay").value=tPayMoneyEveryDay;
	}catch(ex){}
	try{
		// �ȴ���
		var tObsPeriod=Mulline1Grid.getRowColData(selNo-1,21);
		document.all("ObsPeriod").value=tObsPeriod;
	}catch(ex){}
	try{
		// �ȴ��ڵ�λ
		var tObsPeriodUn=Mulline1Grid.getRowColData(selNo-1,22);
		document.all("ObsPeriodUn").value=tObsPeriodUn;
	}catch(ex){}
	try{
		// �ȴ��ڵ�λ name
		var tObsPeriodUnName=Mulline1Grid.getRowColData(selNo-1,23);
		document.all("ObsPeriodUnName").value=tObsPeriodUnName;
	}catch(ex){}
 }
}

// ����
function save() {
	// У���Ƿ�ѡ��һ������
	 var selNo = Mulline1Grid.getSelNo();
		if(selNo != 0)
		{
			alert("��ִ������������");
			return;
		}
	// �ύ��ʽΪINSERT��
	document.all("operator").value="INSERT";
	submitForm();
}
// ѡ��һ�������޸�
function update(){
	// У���Ƿ�ѡ��һ������
	 var selNo = Mulline1Grid.getSelNo();
		if(selNo == 0)
		{
			alert("��ѡ��һ����¼���޸�");
			return;
		}
		// �ύ��ʽΪUPDATE��
		document.all("operator").value="UPDATE";
		submitForm();	
	
}
// ѡ��һ������ɾ��
function del(){
	// У���Ƿ�ѡ��һ������
	 var selNo = Mulline1Grid.getSelNo();
		if(selNo == 0)
		{
			alert("��ѡ��һ����¼��ɾ��");
			return;
		}
		// �ύ��ʽΪDELETEETE��
		document.all("operator").value="DELETE";
		submitForm1();	
}
/*�����ύ*/
function submitForm()
{
if(document.all("IsReadOnly").value == "1")
  {
  	alert("����Ȩִ�д˲���");
  	return;
  }
  
	if(!checkNullable())
	{
		return false;
	}
  lockPage("���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��");
  
  document.getElementById("fm1").submit();
}

function submitForm1()
{
if(document.all("IsReadOnly").value == "1")
  {
  	alert("����Ȩִ�д˲���");
  	return;
  }
  
// if(!checkNullable())
// {
// return false;
// }
  lockPage("���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��");
  
  document.getElementById("fm1").submit();
}

// ����У��
function checkNullable()
{
  if(!verifyInput())
  {
  	return false;
  }
  
  return true;
}
// �����ύ��������
function afterSubmit( FlagStr, content )
{
unLockPage();
if (FlagStr == "Fail" )
{             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
// showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
// [start] add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
var iWidth=550;      // �������ڵĿ��;
var iHeight=350;     // �������ڵĸ߶�;
var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
showInfo.focus();
// [end]
}
else
{
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
// showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
// [start] add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
var iWidth=550;      // �������ڵĿ��;
var iHeight=350;     // �������ڵĸ߶�;
var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
showInfo.focus();


initForm1();// ��ʼ��ҳ��

}
}

// ҳ���ʼ��
function initBox(){
	document.getElementById('MoneyLimitAnnual').value='';
	document.getElementById('CountLimitAnnual').value='';
	document.getElementById('DaysLimitAnnual').value='';
	document.getElementById('MoneyLimitEveryTime').value='';
	document.getElementById('DaysLimitEveryTime').value='';
	document.getElementById('MoneyLimitEveryTime').value='';
	document.getElementById('DaysLimitEveryTime').value='';
	document.getElementById('MoneyLimitEveryDay').value='';
	document.getElementById('PayMoneyEveryDay').value='';
	document.getElementById('ObsPeriod').value='';
	document.getElementById("selfPayPercent").value='';
	document.getElementById("IfPayMoney").value='';
	
	$("input.codeno").val("");
	$("input.codename").val("");
	$("input.coolDatePicker").val("");
}

//��������
function clickUpload(){
    if(document.all("FileName").value == ""){
        alert("�ļ�����Ϊ��");
        return;
    }
    var i = 0;
    var ImportFile = document.all("FileName").value.toLowerCase();
    ImportPath = "/lis/Ldsysvar/";
    //fm1.all('conf').disable = true;
	//fm1.Transact.value = "INSERT";
    lockPage("���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��");
    fm2.action = "./PDLDPlanFeeRelaInputULSave.jsp?ImportPath=" + ImportPath
            + "&ImportFile=" + ImportFile + "&transact=INSERT";
//	alert(fm1.CampaignCode.value);
    document.getElementById("fm2").submit();
}
