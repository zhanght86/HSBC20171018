//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var QueryCount = 0;
var mulLineCount = 0;

var turnPage = new turnPageClass();
window.onfocus=myonfocus;


function save()
{
	//У�������Ƿ�¼��������
	if(fm.GrpContNo.value=="")
	{
		alert("�ŵ���ͬ��δ����!");
		return false;
	}
		if(fm.AppntNo.value=="")
	{
		alert("�ͻ�����δ����!");
		return false;
	}
//		if(fm.Degree.value=="")
//	{
//		alert("������ͻ���ʶ!");
//		return false;
//	}
		if(fm.RiskCode.value=="")
	{
		alert("��ѡ�����ִ���!");
		return false;
	}
		if(fm.InsuYear.value=="")
	{
		alert("�����뱣���ڼ�!");
		return false;
	}
     //    alert (fm.RewardValue.value);
//return false;
	if(fm.RewardValue.value>=0.035)
	{
		alert("�˴�¼��ķֺ����ʲ�������������2.5������,��ȷ����������!");
		//return false;       Ӧ����Ҫ��,ȡ���������(ֻ��ʾ,����ֹ)
	}
	
	//add  by sujd
	if(fm.RewardValue.value>=0.10)
	{
		alert("�ֺ����ʳ���10%,���ݹ���!");
		return false;      
	}
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.frmAction.value="INSERT";
	fm.submit(); //�ύ
	initForm();
	easyQueryClick();
}
//�޸�
function Mod()
{
	//У�������Ƿ�¼��������
	if(fm.GrpContNo.value=="")
	{
		alert("�ŵ���ͬ��δ����!");
		return false;
	}
		if(fm.AppntNo.value=="")
	{
		alert("�ͻ�����δ����!");
		return false;
	}
//		if(fm.Degree.value=="")
//	{
//		alert("������ͻ���ʶ!");
//		return false;
//	}
		if(fm.RiskCode.value=="")
	{
		alert("��ѡ�����ִ���!");
		return false;
	}
		if(fm.InsuYear.value=="")
	{
		alert("�����뱣���ڼ�!");
		return false;
	}
				if(fm.RewardValue.value>=0.035)
	{
		alert("�˴�¼��ķֺ������������������3.5������,������������!");
		return false;
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.frmAction.value="UPDATE";
	fm.submit(); //�ύ
	initForm();
	easyQueryClick();
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
   
	try {
		if(showInfo!=null)
			showInfo.close();
		}
	catch(e)
  {
	}
	if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
	}else{
	  var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
	  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
  	  
}
}
function showMul()
{
	var tSelNo = BonusGrid.getSelNo()-1;
	fm.GrpContNo.value=BonusGrid.getRowColData(tSelNo,1);	
	fm.AppntNo.value=BonusGrid.getRowColData(tSelNo,2);
//	fm.Degree.value=BonusGrid.getRowColData(tSelNo,3);
	fm.GrpPolNo.value=BonusGrid.getRowColData(tSelNo,4);
	fm.RiskCode.value=BonusGrid.getRowColData(tSelNo,5);
	fm.InsuYear.value=BonusGrid.getRowColData(tSelNo,6);
	fm.InsuYearFlag.value=BonusGrid.getRowColData(tSelNo,7);
	fm.RewardType.value=BonusGrid.getRowColData(tSelNo,8);
	fm.RewardValue.value=BonusGrid.getRowColData(tSelNo,9);
	fm.StartDate.value=BonusGrid.getRowColData(tSelNo,10);
	fm.EndDate.value=BonusGrid.getRowColData(tSelNo,11);
	fm.PayMoney.value=BonusGrid.getRowColData(tSelNo,12);
	fm.Note.value=BonusGrid.getRowColData(tSelNo,13);
	
	fm.GrpPolNo1.value=BonusGrid.getRowColData(tSelNo,4);

}
function easyQueryClick()
{

	// ��ʼ�����
	initBonusGrid();
	// ��дSQL���
	
	var strSQL = "";
	var GrpContNo=fm.all('GrpContNo').value;
  var RiskCode=fm.all('QueryRiskCode').value;
	strSQL="select grpcontno,appntno,degree,grppolno,riskcode,insuyear,insuyearflag,rewardtype,rewardvalue,startdate,enddate,paymoney,note from lcgrpbonus where 1=1 and grpcontno='"+GrpContNo+"'"
	+ getWherePart('riskcode','QueryRiskCode');
	//��ѯSQL�����ؽ���ַ���
   turnPage.queryModal(strSQL,BonusGrid);
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.queryModal) {

  	BonusGrid.clearData();
    return false;
  }

}
//����ֺ��˱�
function easyQueryClick2()
{

	// ��ʼ�����
	initCanclePolGrid();
	// ��дSQL���
	
	var strSQL = "";
	var GrpContNo=fm.all('GrpContNo').value;
//  var RiskCode2=fm.all('QueryRiskCode2').value;
	strSQL="select grpcontno,appntno,grppolno,riskcode,AccountPassYears,AccountValueRate from lcgrpbonussub where 1=1 and grpcontno='"+GrpContNo+"'"
	+ getWherePart('riskcode','QueryRiskCode2');
	//��ѯSQL�����ؽ���ַ���
   turnPage.queryModal(strSQL,CanclePolGrid);
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.queryModal) 
  {  	
  	BonusGrid.clearData();
    return false;
  }

}

//����ֺ��˱�����
function canclePol()
{
	//У�������Ƿ�¼��������
	if(fm.GrpContNo2.value=="")
	{
		alert("�ŵ���ͬ��δ����!");
		return false;
	}
		if(fm.AppntNo2.value=="")
	{
		alert("�ͻ�����δ����!");
		return false;
	}
		if(fm.RiskCode2.value=="")
	{
		alert("��ѡ�����ִ���!");
		return false;
	}
		if(fm.AccountPassYear.value=="")
	{
		alert("�������ʻ���������!");
		return false;
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.frmAction.value="ADD";
	fm.submit(); //�ύ
	initForm();
	easyQueryClick2();
}
//����ֺ��˱��޸Ĳ���
function canclePolMod()
{
	//У�������Ƿ�¼��������
	if(fm.GrpContNo2.value=="")
	{
		alert("�ŵ���ͬ��δ����!");
		return false;
	}
		if(fm.AppntNo2.value=="")
	{
		alert("�ͻ�����δ����!");
		return false;
	}
		if(fm.RiskCode2.value=="")
	{
		alert("��ѡ�����ִ���!");
		return false;
	}
		if(fm.AccountPassYear.value=="")
	{
		alert("�������ʻ���������!");
		return false;
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.frmAction.value="CancleMod";
	fm.submit(); //�ύ
	initForm();
	easyQueryClick2();
}
//����ֺ��˱�ɾ�� 
function canclePolDel()
{
	var tSel = CanclePolGrid.getSelNo();
	if(tSel==0)
	{
		alert("����ѡ��һ���ֺ��˱���¼��");
		return;
	}
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.frmAction.value="CancleDEL";
	fm.submit(); //�ύ
  initForm();
  easyQueryClick2();
}

function showMul2()
{
	var tSelNo = CanclePolGrid.getSelNo();
	if(tSelNo!=0)
	{
	fm.GrpContNo2.value=CanclePolGrid.getRowColData(tSelNo-1,1);	
	fm.AppntNo2.value=CanclePolGrid.getRowColData(tSelNo-1,2);
	fm.GrpPolNo2.value=CanclePolGrid.getRowColData(tSelNo-1,3);
	fm.RiskCode2.value=CanclePolGrid.getRowColData(tSelNo-1,4);
	fm.AccountPassYear.value=CanclePolGrid.getRowColData(tSelNo-1,5);
	fm.AccountValueRate.value=CanclePolGrid.getRowColData(tSelNo-1,6);
	
  }
	
}
