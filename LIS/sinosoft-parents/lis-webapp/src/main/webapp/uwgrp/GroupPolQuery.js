//�������ƣ�Underwrite.js
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var pflag = "1";  //�������� 1.���˵�

// ��Ǻ˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ
var showPolDetailFlag ;
var showAppFlag ;
var showHealthFlag ;
var QuestQueryFlag ;

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var tSelNo = PolAddGrid.getSelNo();
  fm.all('PrtNo').value = PolAddGrid.getRowColData(tSelNo - 1,3);
  fm.all('PolNo').value = PolAddGrid.getRowColData(tSelNo - 1,1);

  var tEdorUWState = fm.edoruwstate.value;
  var tUWDelay = fm.UWDelay.value;
  var tUWIdea = fm.UWIdea.value;
  var tUWPopedomCode = fm.UWPopedomCode.value;
  if(tEdorUWState == "")
   {
   	showInfo.close();
    alert("����¼�뱣ȫ�˱�����!");

  	return ;
  }
  if(tEdorUWState == "6" && tUWPopedomCode == "")
  {
  	showInfo.close();
   alert("��Ҫ�ϱ��˱�,��ѡ���ϱ�����!");

  	return ;
  }

  if(tEdorUWState == "2" && tUWDelay == "")
  {
  	showInfo.close();
   ��alert("��Ҫ���ڳб�,��¼���ӳ�ʱ����Ϣ!");
  	return ;
  }
  fm.action = "./UWManuNormChk.jsp";
  fm.submit(); //�ύ
  //alert("submit");
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    //var showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  {
	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  	//var showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	//showInfo.close();
  	//alert(content);
  	//parent.close();

    //ִ����һ������
  }

}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    showInfo.close();
	alert(content);
     //parent.close();
  }
  else
  {
  	showInfo.close();
	alert(content);
	easyQueryClick();
  	//parent.close();
  }

}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    alert(content);
    	// ��ʼ�����
	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
	divLCPol2.style.display= "none";
	divMain.style.display = "none";

  }
  else
  {
  		makeWorkFlow();
  }
}

function afterAddFeeApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    alert(content);
    	// ��ʼ�����
 }
  else
  {
  	  var cPolNo=fm.ProposalNo.value;
	  var cMissionID =fm.MissionID.value;
	  var cSubMissionID =fm.SubMissionID.value;
	  var tSelNo = PolAddGrid.getSelNo()-1;
	  var cEdorNo = PolAddGrid.getRowColData(tSelNo,1);
	  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
	  var cEdorType = PolAddGrid.getRowColData(tSelNo,7);

	 if (cPrtNo != ""&& cEdorNo !="" && cMissionID != "" )
	  {
	  	window.open("./UWManuAddMain.jsp?PrtNo="+cPrtNo+"&PolNo="+cPolNo+"&EdorNo="+cEdorNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&EdorType="+cEdorType,"window1");
	  	showInfo.close();
	  }
	  else
	  {
	  	showInfo.close();
	  	alert("����ѡ�񱣵�!");
	  }
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
		parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

/*********************************************************************
 *  ѡ��˱����Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) {

	    //alert("uwstate" + cCodeName + Field.value);
		if( cCodeName == "uwstate" ) {
			DoUWStateCodeSelect(Field.value);//loadFlag��ҳ���ʼ����ʱ������
		}
}

/*********************************************************************
 *  ���ݲ�ͬ�ĺ˱�����,����ͬ������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DoUWStateCodeSelect(cSelectCode) {

	if(trim(cSelectCode) == '6')//�ϱ��˱�
	{
		 uwgrade = fm.all('UWGrade').value;
         appgrade= fm.all('AppGrade').value;
         if(uwgrade==null||uwgrade<appgrade)
         {
         	uwpopedomgrade = appgrade ;
         }
        else
         {
        	uwpopedomgrade = uwgrade ;
         }
        //alert(uwpopedomgrade);
        codeSql="#1# and Comcode like #"+ comcode+"%%#"+" and Edorpopedom > #"+uwpopedomgrade+"#"	;
        // alert(codeSql);
	}
	else
	codeSql="";
}


//����Ͷ����Ϣ
function showApp()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var cProposalNo=fm.ProposalNo.value;
  var cInsureNo = fm.InsuredNo.value;
  if (cProposalNo != "")
  {
    var tSelNo = PolAddGrid.getSelNo()-1;
  	showAppFlag[tSelNo] = 1 ;
  	//showModalDialog("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	window.open("../uwgrp/UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//�����˱���¼
function showOldUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cProposalNo=fm.ProposalNo.value;
  //showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  if (cProposalNo != "")
  {
  	window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//��ǰ�˱���¼
function showNewUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var tSelNo = PolAddGrid.getSelNo();
  cPolNo=fm.ProposalNo.value;
  if (cPolNo != "" )
  {
  	//window.open("./PEdorUWErrMain.jsp?ProposalNo1="+cProposalNo,"window1");
  	window.open("./UWErrMain.jsp?ProposalNo1="+cPolNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}


// ��Ͷ�������������ѯ
function ClaimGetQuery2()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolAddGrid.getRowColData(tSel - 1,2);
		if (cPolNo == "")
		    return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);
	}
}
// ���������ѯ
function ClaimGetQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cInsuredNo = fm.InsuredNo.value;
		if (cInsuredNo == "")
		    return;
		  window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo);
	}
}

//������ϸ��Ϣ
function showPolDetail()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  cProposalNo=fm.ProposalNo.value;
  if (cProposalNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	showPolDetailFlag[tSelNo] = 1 ;
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cProposalNo );
  	mSwitch.updateVar("PolNo", "", cProposalNo);
  	var prtNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo() - 1, 2);
  	window.open("../sys/AllProQueryMain.jsp?LoadFlag=3","window1");

  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }

}


//ɨ�����ѯ
function ScanQuery()
{
	var tSel = PolAddGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var prtNo = PolAddGrid.getRowColData(tSel - 1,3);
		if (prtNo == "")
		    return;
		  window.open("../sys/ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
	}
}


//������ϲ�ѯ
function showHealthQ()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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


  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;


  if (cContNo!= ""  )
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	var tEdorNo = PolAddGrid.getRowColData(tSelNo,1);
  	showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1");
  	showInfo.close();

  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

// ��Ŀ��ϸ��ѯ
function ItemQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ����ϸ��ѯ��ť��" );
	else
	{
	    var cEdorNo = PolAddGrid.getRowColData(tSel - 1,1);
	    var cSumGetMoney = 	PolAddGrid.getRowColData(tSel - 1,8);

		if (cEdorNo == "")
		   {
		   	alert( "����ѡ��һ�������˱�ȫ��Ŀ�ļ�¼���ٵ����ȫ��Ŀ��ѯ��ť��" );
		   	return;
		   }
		window.open("../sys/AllPBqItemQueryMain.jsp?EdorNo=" + cEdorNo + "&SumGetMoney=" + cSumGetMoney);

	}
}

//�鿴������Ϣ
function PrtEdor()
{
	var tSel = PolAddGrid.getSelNo();
    if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�������鿴��ť��" );
	else
		{
			var cEdorNo = PolAddGrid.getRowColData(tSel - 1,1);
			if (cEdorNo == "")
		   {
			   	alert( "����ѡ��һ�������˱�ȫ��Ŀ�ļ�¼���ٵ����ȫ��ϸ��ѯ��ť��" );
			   	return;
		   }
			fm.all('EdorNo').value = PolAddGrid.getRowColData(tSel - 1,1);
			fm.all('PolNo').value = PolAddGrid.getRowColData(tSel - 1,2);

			var taction = parent.fraInterface.fm.action;
			var ttarget = parent.fraInterface.fm.target;
			parent.fraInterface.fm.action = "../f1printgrp/EndorsementF1P.jsp";
			parent.fraInterface.fm.target="f1print";
			fm.submit();

			parent.fraInterface.fm.action = taction;
			parent.fraInterface.fm.target=ttarget;
			fm.all('EdorNo').value = '';
			fm.all('PolNo').value = '';
		}
}


//�������
function showHealth()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;

  if (cContNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	var tEdorNo = PolAddGrid.getRowColData(tSelNo,1);
  	showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//��Լ�б�
function showSpec()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var cPolNo=fm.ProposalNo.value;
  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var tSelNo = PolAddGrid.getSelNo()-1;
  tUWIdea = fm.all('UWIdea').value;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
  if (cPolNo != ""&& cPrtNo !="" && cMissionID != "" )
  {
  	window.open("./UWManuSpecMain.jsp?PolNo="+cPolNo+"&PrtNo="+cPrtNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//�ӷѳб�
function showAdd()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var cPolNo=fm.ProposalNo.value;
  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
  if (cPrtNo != "" && cMissionID != "" )
  {
	window.open("./UWManuAddMain.jsp?PrtNo="+cPrtNo+"&PolNo="+cPolNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID,"window1");
  }
  else
  {
  alert("����ѡ�񱣵�!");
  }
}
//������鱨��
function showRReport()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cProposalNo=fm.ProposalNo.value;
  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
  if (cProposalNo != ""  && cMissionID != "")
  {
  	window.open("./UWManuRReportMain.jsp?PolNo="+cProposalNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag="+pflag,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//�˱�������
function showReport()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  var cContNo=fm.ContNo.value;
  tUWIdea = fm.all('UWIdea').value;
  if (cContNo != "")
  {
  	window.open("../uwgrp/UWManuReportMain.jsp?ContNo="+cContNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//���˱�֪ͨ��
function SendNotice()
{
  cProposalNo = fm.ProposalNo.value;
  fm.edoruwstate.value = "8";

  if (cProposalNo != "")
  {
	//manuchk();
	 var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

	  cProposalNo=fm.ProposalNo.value;
	  var cMissionID =fm.MissionID.value;
	  var cSelNo = PolAddGrid.getSelNo()-1;
	  var cPrtNo = PolAddGrid.getRowColData(cSelNo,3);
	  fm.PrtNoHide.value = cPrtNo ;
	  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 + " and LWMission.MissionProp1 = '" + cPrtNo +"'"
				 + " and LWMission.MissionProp2 = '"+ cProposalNo + "'"
//				 + " and LWMission.ActivityID = '0000001105'"
//				 + " and LWMission.ProcessID = '0000000003'"
				 + " and LWMission.ActivityID  in (select activityid from lwactivity  where functionid ='10010024') "
				 + " and LWMission.MissionID = '" +cMissionID +"'";
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("���������µĺ˱�֪ͨ��,ԭ�������:1.�ѷ��˱�֪ͨ��,��δ��ӡ.2.δ¼��˱�֪ͨ������.");
        fm.SubNoticeMissionID.value = "";
        return ;
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    fm.SubNoticeMissionID.value = turnPage.arrDataCacheSet[0][0];
    var tSubNoticeMissionID =   fm.SubNoticeMissionID.value ;
    if (cProposalNo != "" && cPrtNo != "" && cMissionID != "" && tSubNoticeMissionID != "" )
	  {
	  	showInfo.close();
	  	fm.action = "./UWManuSendNoticeChk.jsp";
	    fm.submit();
	  }
	  else
	  {
  	   showInfo.close();
  	   alert("����ѡ�񱣵�!");
      }
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

function SendHealth()
{
	//
	}
//���׽�֪ͨ��
function SendFirstNotice()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cProposalNo=fm.ProposalNo.value;
  cOtherNoType="00"; //������������
  cCode="07";        //��������

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//���߰�֪ͨ��
function SendPressNotice()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

  cProposalNo=fm.ProposalNo.value;
  cOtherNoType="00"; //������������
  cCode="06";        //��������

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  	 }
  else

  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}


//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}

var withdrawFlag = false;
//���������ѯ,add by Minim
function withdrawQueryClick() {
  withdrawFlag = true;
  easyQueryClick();
}

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
    divLCPol2.style.display= "none";
    divMain.style.display = "none";

	// ��дSQL���
	k++;
	var uwgradestatus = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //��������״̬
	var strSQL = "";
	if (uwgradestatus == "1")//��������
	{
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp3,LWMission.MissionProp5,LWMission.MissionProp7,LWMission.MissionProp9 ,LWMission.MissionProp10 ,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //�����˱�������
//				 + " and LWMission.ActivityID = '0000001100' " //�����˱��������еĴ��˹��˱�����ڵ�
				 + " and LWMission.ActivityID  in (select activityid from lwactivity  where functionid ='10010028') " //�����˱��������еĴ��˹��˱�����ڵ�
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp8','QInsuredName' )
				 + getWherePart( 'LWMission.MissionProp4','QRiskCode' )
				 + getWherePart( 'LWMission.MissionProp2','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select EdorPopedom from LDUser where usercode = '"+mOperate+"') = (select AppGrade from LccUWMaster where ContNo = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������
	}
	else
	  if (uwgradestatus == "2")//�¼�����
	  {
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp3,LWMission.MissionProp5,LWMission.MissionProp7,LWMission.MissionProp9 ,LWMission.MissionProp10 ,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //��ȫ�˱�������
//				 + " and LWMission.ActivityID = '0000001100' " //��ȫ�˱��������еĴ��˹��˱�����ڵ�
				 + " and LWMission.ActivityID  in (select activityid from lwactivity  where functionid ='10010028') " //��ȫ�˱��������еĴ��˹��˱�����ڵ�
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp8','QInsuredName' )
				 + getWherePart( 'LWMission.MissionProp4','QRiskCode' )
				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') > (select AppGrade from LCCUWMaster where ContNo = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������
	   }
	   else //����+�¼�����
	   {
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp3,LWMission.MissionProp5,LWMission.MissionProp7,LWMission.MissionProp9 ,LWMission.MissionProp10 ,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //�����˱�������
//				 + " and LWMission.ActivityID = '0000001100' " //�����˱��������еĴ��˹��˱�����ڵ�
		
		 		+ " and LWMission.ActivityID  in (select activityid from lwactivity  where functionid ='10010028') " //�����˱��������еĴ��˹��˱�����ڵ�
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp8','QInsuredName' )
				 + getWherePart( 'LWMission.MissionProp4','QRiskCode' )
				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') >= (select AppGrade from LccUWMaster where ContNo = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������
	}

     //alert(strSQL);
     var tOperator = fm.QOperator.value;
	if(state == "1")
	{
			strSQL = strSQL + " and  LWMission.ActivityStatus = '1'"
		      + " and ( LWMission.DefaultOperator is null or LWMission.DefaultOperator = '" + tOperator + "')";
	}
	if(state == "2")
	{
		strSQL = strSQL + " and  LWMission.ActivityStatus = '3'"
		      + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
	}
	if(state == "3")
	{
		strSQL = strSQL + " and  LWMission.ActivityStatus = '2'"
		    + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
	}


	if (withdrawFlag) {
	  //strSQL = strSQL + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0') ";
	  strSQL = "select LCPol.ProposalNo,LCPol.PrtNo,LMRisk.RiskName,LCPol.AppntName,LCPol.InsuredName "
           + " from LCPol,LCUWMaster,LMRisk where 10=10 "
           + " and LCPol.AppFlag='0'  "
           + " and LCPol.UWFlag not in ('1','2','a','4','9')  "
           + " and LCPol.grppolno = '00000000000000000000' and LCPol.contno = '00000000000000000000' "
           + " and LCPol.ProposalNo = LCPol.MainPolNo  and LCPol.ProposalNo= LCUWMaster.ProposalNo  "
           + " and LCUWMaster.appgrade <= (select UWPopedom from LDUser where usercode = '"+mOperate+"') "
           + " and LCPol.ManageCom like '" + manageCom + "%%'"
           + " and LMRisk.RiskCode = LCPol.RiskCode "
           + getWherePart( 'LCUWMaster.Operator','QOperator')
           + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0')";

	  withdrawFlag = false;
	}

	//alert(strSQL);
	//execEasyQuery( strSQL );
	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��ûͨ�������˱��ĸ��˵���");
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  return true;
}

//���mutline��ѡ�򴥷��¼�
function easyQueryAddClick(parm1,parm2)
{

	// ��дSQL���
	k++;
	var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //Ͷ��������״̬
	var tProposalNo = "";
	var tPEdorNo = "";
	var strSQL = "";

	if(fm.all(parm1).all('InpPolGridSel').value == '1' ){
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
		tProposalNo = fm.all(parm1).all('PolGrid2').value;
		tPEdorNo = fm.all(parm1).all('PolGrid1').value;
	}

	fm.all('ContNo').value = fm.all(parm1).all('PolGrid2').value;
	fm.all('MissionID').value = fm.all(parm1).all('PolGrid7').value;
	fm.all('PrtNo').value = fm.all(parm1).all('PolGrid1').value;
	fm.all('SubMissionID').value = fm.all(parm1).all('PolGrid8').value;
	var ContNo = fm.all('ContNo').value;

	//alert("contno11="+fm.all('ContNo').value);
	//alert("PrtNo="+fm.all('PrtNo').value);

	if(state == "1")
	{
		checkDouble(tProposalNo);

	//���ɹ������½ڵ�
	}


	// ��ʼ�����
	initPolAddGrid();
	initPolBox();

	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divLCPol2.style.display= "";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
	if (uwgrade == "1")
	{
		strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "+k+"="+k
				  + " and LCPol.Contno = '" + tProposalNo + "'";
				  //+ " union"
				  //+ " select '  ',LCPol.PolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.AppntName,LCPol.InsuredName,' ',' ' from LCPol where "+k+"="+k
				  //+ " and LCPol.prtno = (select prtno from lcpol where polno ='" + tProposalNo + "')"
				  //+ " and LCPol.polno <> '" + tProposalNo + "'"
				  //+ " order by 2 ";
	}
	else
	{
		strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "+k+"="+k
				  + " and LCPol.contno = '" + tProposalNo + "'";
	}

	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��ûͨ�������˱��ĸ��˵���");
    divLCPol1.style.display= "";
    divMain.style.display= "none";
    divLCPol2.style.display= "none";
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolAddGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("��ѯeasyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );

  //alert("contno21="+fm.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+fm.all('PrtNo').value);

	var arrSelected = new Array();

	strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('ProposalContNo').value = arrSelected[0][0];
	fm.all('PrtNo').value = arrSelected[0][1];
	fm.all('ManageCom').value = arrSelected[0][2];
	fm.all('SaleChnl').value = arrSelected[0][3];
	fm.all('AgentCode').value = arrSelected[0][4];
	fm.all('AgentGroup').value = arrSelected[0][5];
	fm.all('AgentCode1').value = arrSelected[0][6];
	fm.all('AgentCom').value = arrSelected[0][7];
	fm.all('AgentType').value = arrSelected[0][8];
	fm.all('ReMark').value = arrSelected[0][9];

	strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo from lcappnt where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('AppntName').value = arrSelected[0][0];
	fm.all('AppntSex').value = arrSelected[0][1];
	fm.all('AppntBirthday').value = arrSelected[0][2];
	fm.all('AppntNo').value = arrSelected[0][3];
	fm.all('AddressNo').value = arrSelected[0][4];


  return true;
}


function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		initPolGrid();

		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			}// end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

//����Ҫ�˹��˱�����
function checkDouble(tProposalNo)
{
	fm.PolNoHide.value = tProposalNo;
	fm.action = "./ManuUWApply.jsp";
	fm.submit();

}

//ѡ��Ҫ�˹��˱�����
function getPolGridCho()
{
	//setproposalno();

	var cSelNo = PolAddGrid.getSelNo()-1;

	codeSql = "code";
	fm.UWPopedomCode.value = "";
	fm.action = "./ManuUWCho.jsp";
	fm.submit();
}

function makeWorkFlow()
{
	fm.action = "./ManuUWChk.jsp";
	fm.submit();
}

function checkBackPol(ProposalNo) {
  var strSql = "select * from LCApplyRecallPol where ProposalNo='" + ProposalNo + "' and InputState='0'";
  var arrResult = easyExecSql(strSql);
  //alert(arrResult);

  if (arrResult != null) {
    return false;
  }
  return true;
}

//  ��ʼ���˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ�������
function initFlag(  tlength )
{
	// ��Ǻ˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ
      showPolDetailFlag =  new Array() ;
      showAppFlag = new Array() ;
      showHealthFlag = new Array() ;
      QuestQueryFlag = new Array() ;

     var i=0;
	  for( i = 0; i < tlength ; i++ )
		{
			showPolDetailFlag[i] = 0;
			showAppFlag[i] = 0;
			showHealthFlag[i] = 0;
			QuestQueryFlag[i] = 0;
		}

	}
//�º˱�����
function manuchk()
{

	flag = fm.all('UWState').value;
	var ProposalNo = fm.all('ProposalNo').value;
	var MainPolNo = fm.all('MainPolNoHide').value;

	if (trim(fm.UWState.value) == "") {
    alert("������¼��˱����ۣ�");
    return;
  }

	if (!checkBackPol(ProposalNo)) {
	  if (!confirm("��Ͷ�����г������룬�����´˽�����")) return;
	}

    if (trim(fm.UWState.value) == "6") {
      if(trim(fm.UWPopedomCode.value) !="")
         fm.UWOperater.value = fm.UWPopedomCode.value
      else
         fm.UWOperater.value = operator;
}

    var tSelNo = PolAddGrid.getSelNo()-1;

	if(fm.State.value == "1"&&(fm.UWState.value == "1"||fm.UWState.value == "2"||fm.UWState.value =="4"||fm.UWState.value =="6"||fm.UWState.value =="9"||fm.UWState.value =="a")) {
      if( showPolDetailFlag[tSelNo] == 0 || showAppFlag[tSelNo] == 0 || showHealthFlag[tSelNo] == 0 || QuestQueryFlag[tSelNo] == 0 ){
         var tInfo = "";
         if(showPolDetailFlag[tSelNo] == 0)
            tInfo = tInfo + " [Ͷ������ϸ��Ϣ]";
         if(showAppFlag[tSelNo] == 0)
            tInfo = tInfo + " [����Ͷ����Ϣ]";
         if( PolAddGrid.getRowColData(tSelNo,1,PolAddGrid) == PolAddGrid.getRowColData(tSelNo ,2,PolAddGrid)) {
         	if(showHealthFlag[tSelNo] == 0)
              tInfo = tInfo + " [�������¼��]";
         }
         if(QuestQueryFlag[tSelNo] == 0)
            tInfo = tInfo + " [�������ѯ]";
         if ( tInfo != "" ) {
         	tInfo = "����Ҫ��Ϣ:" + tInfo + " δ�鿴,�Ƿ�Ҫ�¸ú˱�����?";
         	if(!window.confirm( tInfo ))
         	    return;
             }

        }
     }
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

	fm.action = "./UWManuNormChk.jsp";
	fm.submit();

	if (flag != "b"&&ProposalNo == MainPolNo)
	{
		initInpBox();
   		initPolBox();
		initPolGrid();
		initPolAddGrid();
	}
}

//function manuchk()
//{
//
//	flag = fm.all('UWState').value;
//	tUWIdea = fm.all('UWIdea').value;
//
//	//¼��б��ƻ��������Ҫ����������
//	if( flag == "b")
//	{
//		cProposalNo=fm.PolNoHide.value;
//	}
//	else
//	{
//		cProposalNo=fm.ProposalNo.value;
//	}
//
//	//alert("flag:"+flag);
//	if (cProposalNo == "")
//	{
//		alert("����ѡ�񱣵�!");
//	}
//	else
//	{
//		if (flag == "0"||flag == "1"||flag == "4"||flag == "6"||flag == "9"||flag == "b")
//		{
//			showModalDialog("./UWManuNormMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//		}
//
//		if (flag == "2") //����
//		{
//			//showModalDialog("./UWManuDateMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//			window.open("./UWManuDateMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,"window1");
//		}
//
//		if (flag == "3") //�����б�
//		{
//			//showModalDialog("./UWManuSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//			window.open("./UWManuSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,"window1");
//		}
//		if (flag == "7") //�����¼��
//		{
//			QuestInput();
//		}
//
//		if (flag != "b")
//		{
//		initInpBox();
//    		initPolBox();
//		initPolGrid();
//		initPolAddGrid();
//		}
//	}
//}

//�����¼��
function QuestInput()
{
	cProposalNo = fm.ProposalNo.value;  //��������

	var strSql = "select * from LCUWMaster where ProposalNo='" + cProposalNo + "' and ChangePolFlag ='1'";
    //alert(strSql);
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      var tInfo = "�б��ƻ����δ�ظ�,ȷ��Ҫ¼���µ������?";
   if(!window.confirm( tInfo )){
          return;
        }
      }
	window.open("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1");

}

//������ظ�
function QuestReply()
{
	cProposalNo = fm.ProposalNo.value;  //��������

	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestReplyMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1");

	initInpBox();
    initPolBox();
	initPolGrid();

}

//�������ѯ
function QuestQuery()
{
  cProposalNo = fm.ProposalNo.value;  //��������


  if (cProposalNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestQueryMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window2");
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }

}

//������鱨���ѯ
function RReportQuery()
{
  cProposalNo = fm.ProposalNo.value;  //��������
  var cEdorNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 1);


  if (cProposalNo != "")
  {
	window.open("./RReportQueryMain.jsp?ProposalNo1="+cProposalNo);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//�������������ѯ
function BackPolQuery()
{
  cProposalNo = fm.ProposalNo.value;  //��������


  if (cProposalNo != "")
  {
	window.open("./BackPolQueryMain.jsp?ProposalNo1="+cProposalNo);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//�߰쳬ʱ��ѯ
function OutTimeQuery()
{
  cProposalNo = fm.ProposalNo.value;  //��������


  if (cProposalNo != "")
  {
	window.open("./OutTimeQueryMain.jsp?ProposalNo1="+cProposalNo);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//���ռƻ����
function showChangePlan()
{
  cProposalNo = fm.ProposalNo.value;  //��������
  cPrtNo = fm.PrtNoHide.value; //ӡˢ��
  var cType = "ChangePlan";
  mSwitch.deleteVar( "PolNo" );
  mSwitch.addVar( "PolNo", "", cProposalNo );

  if (cProposalNo != ""&&cPrtNo != "")
  {
  	 var strSql = "select * from LCIssuepol where ProposalNo='" + cProposalNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
     var arrResult = easyExecSql(strSql);
     if (arrResult != null) {
       var tInfo = "��δ�ظ��������,ȷ��Ҫ���гб��ƻ����?";
       if(!window.confirm( tInfo ))
         	    return;
      }
    window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=3&Type="+cType+"&prtNo="+cPrtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

   }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//���ռƻ��������¼����ʾ
function showChangeResultView()
{
	fm.ChangeIdea.value = "";
	fm.PolNoHide.value = fm.ProposalNo.value;  //��������
	divUWResult.style.display= "none";
	divChangeResult.style.display= "";
}


//��ʾ���ռƻ��������
function showChangeResult()
{
	fm.UWState.value = "b";
	fm.UWIdea.value = fm.ChangeIdea.value;

	cChangeResult = fm.UWIdea.value;

	if (cChangeResult == "")
	{
		alert("û��¼�����!");
	}
	else
	{
		var strSql = "select * from LCIssuepol where ProposalNo='" + cProposalNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
        var arrResult = easyExecSql(strSql);
        //alert(arrResult);
       if (arrResult != null) {
       var tInfo = "��δ�ظ��������,ȷ��Ҫ���гб��ƻ����?";
       if(!window.confirm( tInfo )){
       	      HideChangeResult()
         	    return;
          }
       }
	  // manuchk();

    }
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.edoruwstate.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}

//���ر��ռƻ��������
function HideChangeResult()
{
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.edoruwstate.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}


function cancelchk()
{
	fm.all('edoruwstate').value = "";
	fm.all('UWPopedomCode').value = "";
	fm.all('UWIdea').value = "";
}

function setproposalno()
{
	var count = PolGrid.getSelNo();
	fm.all('ProposalNo').value = PolGrid.getRowColData(count - 1,1,PolGrid);
}

//�����հ�ť���غ���
function hideAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "none";
	parent.fraInterface.divAddButton2.style.display= "none";
	parent.fraInterface.divAddButton3.style.display= "none";
	parent.fraInterface.divAddButton4.style.display= "none";
	parent.fraInterface.divAddButton5.style.display= "none";
	parent.fraInterface.divAddButton6.style.display= "none";
	parent.fraInterface.divAddButton7.style.display= "none";
	parent.fraInterface.divAddButton8.style.display= "none";
	parent.fraInterface.divAddButton9.style.display= "none";
	parent.fraInterface.divAddButton10.style.display= "none";
	//parent.fraInterface.fm.UWState.CodeData = "0|^4|ͨ�ڳб�^9|�����б�";
	//parent.fraInterface.UWResult.innerHTML="�˱�����<Input class=\"code\" name=UWState CodeData = \"0|^4|ͨ��/�����б�^9|�����б�\" ondblclick= \"showCodeListEx('UWState1',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState1',[this,''],[0,1]);\">";


}

//��ʾ���ذ�ť
function showAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "";
	parent.fraInterface.divAddButton2.style.display= "";
	parent.fraInterface.divAddButton3.style.display= "";
	parent.fraInterface.divAddButton4.style.display= "";
	parent.fraInterface.divAddButton5.style.display= "";
	parent.fraInterface.divAddButton6.style.display= "";
	parent.fraInterface.divAddButton7.style.display= "";
	parent.fraInterface.divAddButton8.style.display= "";
	parent.fraInterface.divAddButton9.style.display= "";
	parent.fraInterface.divAddButton10.style.display= "";
	//parent.fraInterface.UWResult.innerHTML="�˱�����<Input class=\"code\" name=UWState CodeData = \"0|^1|�ܱ�^2|����^4|ͨ��/�����б�^6|���ϼ��˱�^9|�����б�^a|����Ͷ����\" ondblclick= \"showCodeListEx('UWState',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState',[this,''],[0,1]);\">";
	//parent.fraInterface.fm.UWState.CodeData = "0|^1|�ܱ�^2|����^4|ͨ�ڳб�^6|���ϼ��˱�^9|�����б�^a|����Ͷ����";
}

function showNotePad() {
  var cContNo = fm.ContNo.value;//PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 1);
  var PrtNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 3);

  if (cContNo!="" && PrtNo!="") {
  	window.open("../uwgrp/UWNotePadMain.jsp?ContNo="+cContNo+"&PrtNo="+PrtNo+"&OperatePos=3", "window1");
  }
  else {
  	alert("����ѡ�񱣵�!");
  }
}

function InitClick(){
	divSearch.style.display="";
	divLCPol1.style.display= "";
	DivLCContButton.style.display="none";
	DivLCCont.style.display="none";
	divLCPol2.style.display= "none";
	divMain.style.display = "none";
	DivLCAppntInd.style.display="none";
	DivLCAppntIndButton.style.display="none";
	fm.reset();
	initForm();
}
