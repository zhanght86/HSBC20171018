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
var pflag = "1";  //�������� 1.���˵�δ
var bUWNormChk = false;
var bContUWNormChk = false;

	var InsuredNo = "";

// ��Ǻ˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ
var showPolDetailFlag ;
var showAppFlag ;
var showHealthFlag ;
var QuestQueryFlag ;

//�ύ�����水ť��Ӧ����
function confirmform()
{
	if(fm.Drawer.value=="")
	{
		alert("��¼����ȡ�ˣ�");
		return;
	}
	//alert("ok");
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

  fm.action = "./FeeManage.jsp";
  fm.submit(); //�ύ
 
}

//�ύ ���޸İ�Ŧ
function confirmform2()
{
	var NotCheck = false;
  for (i=0; i<HavePolGrid.mulLineCount; i++) {
    if (HavePolGrid.getChkNo(i)) {
      NotCheck = true;
      break;
    }
  }
  if (NotCheck) {
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
  
  fm.action = "./FeeManageUpdate.jsp";
  fm.submit(); //�ύ
  }
  else {
    alert("����ѡ��һ���ݽ�����Ϣ����ѡ����д򹳣�");
  }
}

//�ύ ��ɾ����Ŧ
function confirmform3()
{
	var NotCheck = false;
  for (i=0; i<HavePolGrid.mulLineCount; i++) {
    if (HavePolGrid.getChkNo(i)) {
      NotCheck = true;
      break;
    }
  }
  if (NotCheck) {
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

  
  fm.action = "./FeeManageDelete.jsp";
  fm.submit(); //�ύ
  }
  else {
    alert("����ѡ��һ���ݽ�����Ϣ����ѡ����д򹳣�");
  }
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  
    showInfo.close();
    easyQueryClick(cGrpContNo);
    easyQueryHaveClick(cGrpContNo);
 
		alert(content);


}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    showInfo.close();
	//alert(content);
     //parent.close();
  }
  else
  {
  	showInfo.close();
	//alert(content);
	easyQueryClick();
  	//parent.close();
  }

}


function afterSubmit3( FlagStr, content )
{
	
	showInfo.close();
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
    
  }
  else
  {

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //ִ����һ������
    alert(content);
  }
	 	
	divMain.style.display = "none";
	DivLCContButton.style.display="";
	DivLCCont.style.display="none";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
	divContUWResult.style.display="";
	divLCPol2.style.display= "none";
	divLCPolButton.style.display= "none";
	divUWResult.style.display="none";
	
	
	
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
	divLCPolButton.style.display= "none";
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

  var cProposalNo=fm.ProposalNo.value;
  var cInsureNo = fm.InsuredNo.value;
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (cProposalNo != "")
  {
    var tSelNo = PolAddGrid.getSelNo()-1;
  	showAppFlag[tSelNo] = 1 ;
  	window.open("../uwgrp/UWAppGMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,"window1");
  }
  else if (tContNo != "")
  {
  	window.open("../uwgrp/UWAppGMain.jsp?InsureNo1="+cInsureNo + "&GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//�����˱���¼
function showOldUWSub()
{
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
  	window.open("./UWSubGMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//��ǰ�˱���¼
function showNewUWSub()
{
  var tSelNo = PolAddGrid.getSelNo();
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
  	window.open("./UWErrGMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}
function showNewPolUWSub()
{
  var tSelNo = PolAddGrid.getSelNo();
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
  	window.open("./UWErrMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
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

  cContNo=fm.all("ContNo").value ;
  cPrtNo=fm.all("PrtNo").value;
  cGrpContNo=fm.all("GrpContNo").value;
  if (cContNo != "")
  {
  	
  	mSwitch.deleteVar( "ContNo" );
  	mSwitch.addVar( "ContNo", "", cContNo );
  	mSwitch.updateVar("ContNo", "", cContNo);
  	mSwitch.deleteVar( "ProposalContNo" );
  	mSwitch.addVar( "ProposalContNo", "", cContNo );
  	mSwitch.updateVar("ProposalContNo", "", cContNo);
  	mSwitch.deleteVar( "PrtNo" );
  	mSwitch.addVar( "PrtNo", "", cPrtNo );
  	mSwitch.updateVar("PrtNo", "", cPrtNo);
  	mSwitch.deleteVar( "GrpContNo" );
  	mSwitch.addVar( "GrpContNo", "", cGrpContNo );
  	mSwitch.updateVar("GrpContNo", "", cGrpContNo);
  	window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&Auditing=1","window1");

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
  
  var cPrtNo = fm.PrtNo.value;


  if (cContNo!= ""  )
  {
  	
  	window.open("./GrpUWManuHealthQMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo,"window1");
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
  var cPrtNo = fm.PrtNo.value;
  var cGrpContNo = fm.GrpContNo.value;
  if (cContNo != "")
  {
  	window.open("./GrpUWManuHealthMain.jsp?ContNo1="+cContNo+"&PrtNo="+cPrtNo+"&GrpContNo="+cGrpContNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}
//�����������
function showBeforeHealthQ()
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

  var cContNo=fm.all('ContNo').value;
  //alert("ccontno"+cContNo);
  var strSql = "select InsuredNo from lccont where ContNo = '"+cContNo+"'";
  //alert(strSql);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
  var arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  var cInsuredNo= arrSelected[0][0];


	window.open("../uwgrp/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cInsuredNo+"&type=1");

 showInfo.close();	
}
//��Լ�б�
function showSpec()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
	window.open("./UWGrpManuSpecMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
  alert("����ѡ�񱣵�!");
  }
  
}

//�ӷѳб�
function showAdd()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
	window.open("./UWGrpManuAddMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
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

  
  var cContNo = fm.ContNo.value;
  
 
  var cPrtNo = fm.PrtNo.value;
 
  if (cContNo != "")
  {
  	
  	window.open("./GrpUWManuRReportMain.jsp?GrpContNo="+cGrpContNo+"&ContNo1="+cContNo+"&PrtNo="+cPrtNo,"window1");
  	showInfo.close();
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
function showDiv(cDiv,cShow){
	if (cShow=="true"){
		cDiv.style.display="";
	}
	else{
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
function easyQueryClick(cGrpContNo){
	
	
	k++;

	var strSQL = "";
  
	strSQL = "select grppolno,grpcontno,riskcode,prem,0,0,'' from lcgrppol  where grpcontno='"+cGrpContNo+"'";
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	
	
  
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
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

	return true;
}


//��ѯ���е�������

function easyQueryHaveClick(cGrpContNo)
{
	var strSql = "select grppolno,grpcontno,riskcode,FeeRate,fee,Drawer,countno,GetNoticeNo,DrawerID,Remark From lcFeeControl where grpcontno='"+cGrpContNo+"' order by GetNoticeNo";
	    turnPage.queryModal(strSql, HavePolGrid);
	
}
//���mutline��ѡ�򴥷��¼�
function easyQueryAddClick(parm1,parm2){
	// ��дSQL���
	k++;
	//var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	//var state = fm.State.value;       //Ͷ��������״̬
	var tProposalNo = "";
	var tPEdorNo = "";
	var strSQL = "";
	var ContNo = fm.all(parm1).all('PolGrid1').value;
	fm.all("ContNo").value = ContNo;

	// ��ʼ�����
	initPolAddGrid();
	initPolBox();

	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
//modify by zhangxing
	//strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.UWFlag,LCPol.AppntName,LCPol.InsuredName,LCPol.Mult,LCPol.Prem,LCPol.Amnt from LCPol where ContNo='"+ContNo+"'" + " and UWFlag not in ('0', '1', '4', '9')"
	strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.UWFlag,LCPol.AppntName,LCPol.InsuredName,LCPol.Mult,LCPol.Prem,LCPol.Amnt from LCPol where ContNo='"+ContNo+"'"
	  //��ѯSQL�����ؽ���ַ���
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("û��δͨ���˱��ĸ������ֵ���");
		divContUWResult.style.display="";
		divLCPol2.style.display= "none";
		divUWResult.style.display="none";
		divLCPolButton.style.display= "";
	}
	else
	{
		divContUWResult.style.display="";
		divLCPol2.style.display= "";
		divUWResult.style.display="";
		divLCPolButton.style.display= "";

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
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		initFlag(  arrDataSet.length );
		
		
	}

	var arrSelected = new Array();

//	strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	strSQL = "select ProposalNo,PrtNo,AgentCode,GrpContNo,PolNo from lcpol where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	if (arrSelected != null)
	{
		fm.all('ProposalContNo').value = arrSelected[0][0];
		fm.all('PrtNo').value = arrSelected[0][1];
//		fm.all('ManageCom').value = arrSelected[0][2];
//		fm.all('SaleChnl').value = arrSelected[0][3];
		fm.all('AgentCode').value = arrSelected[0][2];
		fm.all('GrpContNo').value = arrSelected[0][3];
		fm.all('PolNo').value = arrSelected[0][4];
//		fm.all('AgentGroup').value = arrSelected[0][5];
//		fm.all('AgentCode1').value = arrSelected[0][6];
//		fm.all('AgentCom').value = arrSelected[0][7];
//		fm.all('AgentType').value = arrSelected[0][8];
//		fm.all('ReMark').value = arrSelected[0][9];
	}
	
	cGrpContNo = fm.all("GrpContNo").value;
	strSQL = "select Name,PostalAddress,ZipCode,Phone from lCGrpAppnt where GrpContNo='"+cGrpContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	if (arrSelected != null)
	{
		fm.all('GrpName').value = arrSelected[0][0];
		fm.all('PostalAddress').value = arrSelected[0][1];
		fm.all('ZipCode').value = arrSelected[0][2];
		fm.all('Phone').value = arrSelected[0][3];
	}
	
	strSQL = "select insuredname,insuredsex,insuredbirthday,insuredidtype,insuredidno,insuredno from lccont where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);	
	if (arrSelected != null)
	{
		fm.all('InsuredName').value = arrSelected[0][0];
		fm.all('InsuredSex').value = arrSelected[0][1];
		fm.all('InsuredBirthday').value = arrSelected[0][2];
		fm.all('InsuredIDType').value = arrSelected[0][3];
		fm.all('InsuredIDno').value = arrSelected[0][4];
		fm.all('InsuredNo').value = arrSelected[0][5];
		InsuredNo = arrSelected[0][5];
	}
	
	
	strSQL = "select OccupationType from lcinsured where InsuredNo='"+InsuredNo+"' and ContNo='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);	
	if (arrSelected != null)
	{
		fm.all('OccupationType').value = arrSelected[0][0];
	}

	return true;
}


function displayEasyResult( arrResult ){
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
function checkDouble(tProposalNo){
	fm.PolNoHide.value = tProposalNo;
	fm.action = "./ManuUWApply.jsp";
	fm.submit();
}

//ѡ��Ҫ�˹��˱�����
function getPolGridCho(){
	var cSelNo = PolAddGrid.getSelNo()-1;
	codeSql = "code";
	fm.action = "./GroupPolQuery.jsp";
	fm.submit();
}

function makeWorkFlow(){
	fm.action = "./ManuUWChk.jsp";
	fm.submit();
}

function checkBackPol(ProposalNo){
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
function manuchk(cContFlag)
{
	if (cContFlag == 1)
	{
		flag = fm.all('UWState').value;
		var ProposalNo = "";
		ProposalNo = fm.all('PolNo').value;

		if (ProposalNo == "")
		{
			alert("����ѡ�񱣵���");
			return;
		}
    if(PolAddGrid.getSelNo()==0)
    {
    	alert("��ѡ��һ��������Ϣ��");
    	return;
    }
		var MainPolNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 2);
		fm.tempgrpcontno.value=PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 3);
		fm.tempriskcode.value=PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 4);
    //alert("ok");
		if (trim(fm.UWState.value) == "") {
			alert("������¼��˱����ۣ�");
		return;
		}

		if (!checkBackPol(ProposalNo)) {
		  if (!confirm("��Ͷ�����г������룬�����´˽�����")) return;
		}

		if (trim(fm.UWState.value) == "6") {
		  fm.UWOperater.value = operator;
		}

		var tSelNo = PolAddGrid.getSelNo()-1;

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

		fm.action = "./UWManuNormGChk.jsp";
		fm.submit();
		bUWNormChk = true;
	}
	else if (cContFlag == 2)
	{
		flag = fm.all('ContUWState').value;
		var cContNo = "";
		cContNo = fm.all('ContNo').value;

		if (cContNo == "")
		{
			alert("����ѡ�񱣵���");
			return;
		}

		if (trim(fm.ContUWState.value) == "") {
			alert("������¼��˱����ۣ�");
		return;
		}


		if (trim(fm.ContUWState.value) == "6") {
		  fm.UWOperater.value = operator;
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

		fm.action = "./UWContManuNormGChk.jsp";
		fm.submit();
		bContUWNormChk = true;
	}
	else if (cContFlag == 3)
	{
		//alert("ok");
		flag = "9";
		var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

		fm.action = "./UWBatchManuNormGChk.jsp";
		fm.submit();
		bContUWNormChk = true;
	}
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
  cContNo = fm.ContNo.value;  //��������

  
  
  if (cContNo != "")
  {			
	window.open("./RReportQueryMain.jsp?ContNo="+cContNo);
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
	//divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.edoruwstate.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}

//���ر��ռƻ��������
function HideChangeResult()
{
	//divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	//fm.edoruwstate.value = "";
	//fm.UWIdea.value = "";
	//fm.UWPopedomCode.value = "";
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
	divLCPolButton.style.display= "none";
	divMain.style.display = "none";
	DivLCAppntInd.style.display="none";
	DivLCAppntIndButton.style.display="none";
	divUWResult.style.display="none";
	divContUWResult.style.display="none";
	var cGrpContNo = fm.all("GrpContNo").value
	fm.reset();
	initForm(cGrpContNo);
}

function goBack()
{
	top.close();
}

function InitUWFlag()
{   
	
	  var tSelNo = PolAddGrid.getSelNo()-1;;
	 
	  var tPolNo = PolAddGrid.getRowColData(tSelNo,1);
	  
	  var tSql = "select passflag ,uwidea from lcuwmaster where polno = '"+tPolNo+"'";
  	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
		fm.all('UWState').value = arrSelected[0][0];
		fm.all('UWIdea').value = arrSelected[0][1];
	
}
function amntAccumulate()
{
	var tcontno=fm.all('ContNo').value;
	//alert("tcontno"+tcontno);
	var strSql = "select InsuredNo from lccont where ProposalContNo = '"+tcontno+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	var tInsuredNo= arrSelected[0][0];
	//alert("arrResult"+tInsuredNo);
	
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+tInsuredNo,"window1");	
}
function queryHealthImpart(){
	
	var cContNo=fm.all('ContNo').value;
	var strSql = "select InsuredNo from lccont where ProposalContNo = '"+cContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	var tInsuredNo= arrSelected[0][0];
	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+tInsuredNo,"window1");
}
function queryProposal(){
	  
//	var cContNo = fm.all('GrpContNo').value;
//	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
//	var arr=easyExecSql(strsql);
//	
//	if(arr!=null){
//		fm.all('AppntNo').value=arr[0][0];
//	}
	
    window.open("./ProposalQueryMain.jsp?CustomerNo="+InsuredNo);

}
function queryNotProposal(){
  
//  var cContNo = fm.all('GrpContNo').value;
//	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
//	var arr=easyExecSql(strsql);
//	if(arr!=null){
//		fm.all('AppntNo').value=arr[0][0];
//	}


	window.open("./NotProposalQueryMain.jsp?CustomerNo="+InsuredNo);

}

