//�������ƣ�Underwrite.js
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
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
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();


  var tSelNo = PolAddGrid.getSelNo();
  document.all('PrtNo').value = PolAddGrid.getRowColData(tSelNo - 1,3);
  document.all('PolNo').value = PolAddGrid.getRowColData(tSelNo - 1,1);

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
function afterSubmit(FlagStr,content)
{
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (showInfo != null)
  {
    showInfo.close();
  }
  if (bContUWNormChk)
  {
	  var cGrpContNo = document.all("GrpContNo").value;
	  if (FlagStr == "Succ")
	  {
		  alert("�����ɹ���");
	  }
	  easyQueryClick(cGrpContNo);
	  bContUWNormChk = false;
	  document.all("ContNo").value = "";
	
	  divMain.style.display = "none";
	  DivLCContButton.style.display="none";
	  DivLCCont.style.display="none";
	  DivLCAppntInd.style.display="none";
	  DivLCAppntIndButton.style.display="none";
	  divContUWResult.style.display="none";
	  divLCPol2.style.display= "none";
	  divLCPolButton.style.display= "none";
	  divUWResult.style.display="none";
  }
 
  if (bUWNormChk)
  {
	   
	   var ContNo = document.all("ContNo").value;
	   
	   if(fm.isContPlan.value=="true")
	   {
	   	 queryPlanInfo(ContNo);
	   }
	  else
	   {
	  		queryPolInfo(ContNo);
	   }
	   
	/*   initPolAddGrid();
     initPolBox();
     

	   strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.UWFlag,LCPol.AppntName,LCPol.InsuredName from LCPol where ContNo='"+ContNo+"' "
	   //��ѯSQL�����ؽ���ַ���
	   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  	//�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult)
	  {
		  alert("û��δͨ���˱��ĸ������ֵ���");
		  InitClick();
		  return "";
	  }
	  divLCPol1.style.display= "none";
	  divSearch.style.display= "none";
	  divLCPol2.style.display= "";
	  divLCPolButton.style.display= "";
	  divMain.style.display = "";
	  DivLCContButton.style.display="";
	  DivLCCont.style.display="";
	  DivLCAppntInd.style.display="";
	  DivLCAppntIndButton.style.display="";
	  divUWResult.style.display="";

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
	  //alert("PolAddGrid");
	  initFlag(arrDataSet.length);*/
	  

	  bUWNormChk = false;
	  document.all("PolNo").value = "";
	  if (FlagStr == "Succ")
	  {
		  alert("�����ɹ���");
	  }
  }
  if (FlagStr == "Fail" )
  {
    alert(content);
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


function afterSubmit3(FlagStr,content)
{
	
	showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    
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
function afterApply(FlagStr,content)
{
  if(FlagStr == "Fail" )
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
	  	window.open("./UWManuAddMain.jsp?PrtNo="+cPrtNo+"&PolNo="+cPolNo+"&EdorNo="+cEdorNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&EdorType="+cEdorType,"window1",sFeatures);
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
 	else
  {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

/*********************************************************************
 *  ѡ��˱����Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{
	    //alert("uwstate" + cCodeName + Field.value);
		if( cCodeName == "uwstate" )
		{
			DoUWStateCodeSelect(Field.value);//loadFlag��ҳ���ʼ����ʱ������
		}
}

/*********************************************************************
 *  ���ݲ�ͬ�ĺ˱�����,����ͬ������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DoUWStateCodeSelect(cSelectCode) 
{
	if(trim(cSelectCode) == '6')//�ϱ��˱�
	{
		 uwgrade = document.all('UWGrade').value;
         appgrade= document.all('AppGrade').value;
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
  var tGrpContNo = document.all("GrpContNo").value;
  var tContNo = document.all("ContNo").value;
  var tPolNo = document.all("PolNo").value;
  if (cProposalNo != "")
  {
    var tSelNo = PolAddGrid.getSelNo()-1;
  	showAppFlag[tSelNo] = 1 ;
  	window.open("../uw/UWAppGMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,"window1",sFeatures);
  }
  else if (tContNo != "")
  {
  	window.open("../uw/UWAppGMain.jsp?InsureNo1="+cInsureNo + "&GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1",sFeatures);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//�����˱���¼
function showOldUWSub()
{
  var tGrpContNo = document.all("GrpContNo").value;
  var tContNo = document.all("ContNo").value;
  var tPolNo = document.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
  	window.open("./UWSubGMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1",sFeatures);
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
  var tGrpContNo = document.all("GrpContNo").value;
  var tContNo = document.all("ContNo").value;
  var tPolNo = document.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
  	window.open("./UWErrGMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1",sFeatures);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}
function showNewPolUWSub()
{
  var tSelNo = PolAddGrid.getSelNo();
  var tGrpContNo = document.all("GrpContNo").value;
  var tContNo = document.all("ContNo").value;
  var tPolNo = document.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
  	window.open("./UWErrMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1",sFeatures);
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

	if(tSel == 0 ||tSel == null)
		alert("����ѡ��һ����¼.");
	else
	{
	    var cPolNo = PolAddGrid.getRowColData(tSel - 1,2);
		  if (cPolNo == "")
		      return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);
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
		  window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo,"",sFeatures);
	}
}

//������ϸ��Ϣ
function showPolDetail()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  cContNo=document.all("ContNo").value ;
  cPrtNo=document.all("PrtNo").value;
  cGrpContNo=document.all("GrpContNo").value;
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
  	//window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&Auditing=1","window1");
  		window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&checktype=2&ContType=2&Auditing=1&ProposalGrpContNo="+cGrpContNo+"&ContNo="+cContNo+"&NameFlag=0","window1",sFeatures);
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
		alert("����ѡ��һ����¼��");
	else
	{
	    var prtNo = PolAddGrid.getRowColData(tSel - 1,3);
		if (prtNo == "")
		    return;
		  window.open("../sys/ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	}
}

//������ϲ�ѯ
function showHealthQ()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();


  var cContNo = fm.ContNo.value;
  
  var cPrtNo = fm.PrtNo.value;

	

  if (cContNo!= ""  )
  {
  	
  	window.open("./GrpUWManuHealthQMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo,"window1",sFeatures);
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
		window.open("../sys/AllPBqItemQueryMain.jsp?EdorNo=" + cEdorNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);

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
			document.all('EdorNo').value = PolAddGrid.getRowColData(tSel - 1,1);
			document.all('PolNo').value = PolAddGrid.getRowColData(tSel - 1,2);

			var taction = parent.fraInterface.fm.action;
			var ttarget = parent.fraInterface.fm.target;
			parent.fraInterface.fm.action = "../f1print/EndorsementF1P.jsp";
			parent.fraInterface.fm.target="f1print";
			fm.submit();

			parent.fraInterface.fm.action = taction;
			parent.fraInterface.fm.target=ttarget;
			document.all('EdorNo').value = '';
			document.all('PolNo').value = '';
		}
}

//�������
function showHealth()
{
  var cContNo = fm.ContNo.value;
  //alert(cContNo);
  var cPrtNo = fm.PrtNo.value;
  var cGrpContNo = fm.GrpContNo.value;
  var cInsuredNo = fm.InsuredNo.value;
  if (cContNo != "")
  {
  	
		var sqlid1="GroupContQuery1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(cGrpContNo);//ָ������Ĳ���
		mySql1.addSubPara(cContNo);//ָ������Ĳ���
		mySql1.addSubPara(cGrpContNo);//ָ������Ĳ���
		mySql1.addSubPara(cContNo);//ָ������Ĳ���
	    var checkSQL=mySql1.getString();	
	
//  		var checkSQL="select StateFlag from LOPRTManager where standbyflag3='"+cGrpContNo+"' and OtherNo='"+cContNo+"' and standbyflag1=(select insuredno from lcinsured where grpcontno='"+cGrpContNo+"' and contno='"+cContNo+"')";
  		turnPage.strQueryResult  = easyQueryVer3(checkSQL, 1, 0, 1);
  var arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
 if(arrSelected==null||arrSelected==2)
  {//alert(arrSelected);
  	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	  window.open("./GrpUWManuHealthMain.jsp?ContNo1="+cContNo+"&PrtNo="+cPrtNo+"&GrpContNo="+cGrpContNo+"&InsuredNo="+cInsuredNo,"window1",sFeatures);
	  showInfo.close();
	 }
	else
		if(arrSelected==0||arrSelected==1)
	{
		//alert(arrSelected);
		var showStr="�ú�ͬ�Ѿ����������֪ͨ�飬�����ٽ���¼��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
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
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

  var cContNo=document.all('ContNo').value;
  //alert("ccontno"+cContNo);
  
  		var sqlid2="GroupContQuery2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(cContNo);//ָ������Ĳ���
	    var strSql=mySql2.getString();	
  
//  var strSql = "select InsuredNo from lccont where ContNo = '"+cContNo+"'";
  //alert(strSql);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
  var arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  var cInsuredNo= arrSelected[0][0];

	window.open("../uw/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cInsuredNo+"&type=1","",sFeatures);

 showInfo.close();	
}
//��Լ�б�
function showSpec()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var tGrpContNo = document.all("GrpContNo").value;
  var tContNo = document.all("ContNo").value;
  var tPolNo = document.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
	 window.open("./UWGrpManuSpecMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1",sFeatures);
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
  var tGrpContNo = document.all("GrpContNo").value;
  var tContNo = document.all("ContNo").value;
  var tPolNo = document.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
	window.open("./UWGrpManuAddMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1",sFeatures);
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
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

  
  var cContNo = fm.ContNo.value;
  
 
  var cPrtNo = fm.PrtNo.value;
 
  if (cContNo != "")
  {
  	
  	window.open("./GrpUWManuRReportMain.jsp?GrpContNo="+cGrpContNo+"&ContNo1="+cContNo+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();
  }
      
 
}

//�˱�������
function showReport()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

  var cContNo=fm.ContNo.value;
  tUWIdea = document.all('UWIdea').value;
  if (cContNo != "")
  {
  	window.open("../uw/UWManuReportMain.jsp?ContNo="+cContNo,"window1",sFeatures);
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
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();


  cProposalNo=fm.ProposalNo.value;
  cOtherNoType="00"; //������������
  cCode="06";        //��������

  if (cProposalNo != "")
  {
  	//showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open ("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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
	// ��ʼ�����
	HideChangeResult();
	initPolGrid();
	divSearch.style.display= "";
	divLCPol1.style.display= "";
	divLCPolButton.style.display= "none";
	divLCPol2.style.display= "none";
	divMain.style.display = "none";

	// ��дSQL���
	k++;
	//var uwgradestatus = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	//var state = fm.State.value;       //��������״̬
	var strSQL = "";

	//if (fm.all("SearchFlag").checked)
//		strSQL = "select ContNo,PrtNo,ContType,UWFlag,InsuredName,ManageCom from LCCont where GrpContNo='" + cGrpContNo + "' and UWFlag not in ('0', '1', '4', '9') " + getWherePart("ManageCom", "QManageCom") + getWherePart("InsuredName", "QInsuredName")
// 		       + " union select ContNo,PrtNo,RiskCode,ContType,UWFlag,InsuredName,ManageCom from lcpol where grpcontno ='" + cGrpContNo + "' and UWFlag not in ('0', '1', '4', '9') " + getWherePart("ManageCom", "QManageCom") + getWherePart("InsuredName", "QInsuredName");
//		strSQL = "select ContNo,PrtNo,RiskCode,ContType,UWFlag,InsuredName,ManageCom from lcpol where grpcontno ='" + cGrpContNo + "' and UWFlag not in ('0', '1', '4', '9') " + getWherePart("ManageCom", "QManageCom") + getWherePart("InsuredName", "QInsuredName");


//	else

  		var sqlid3="GroupContQuery3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(cGrpContNo);//ָ������Ĳ���
		mySql3.addSubPara(fm.QManageCom.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.QInsuredName.value);//ָ������Ĳ���
	    strSQL=mySql3.getString();	

//	strSQL = "select ContNo,PrtNo,ContType,UWFlag,InsuredName,ManageCom from LCCont where GrpContNo='" + cGrpContNo +"'"+ getWherePart("ManageCom", "QManageCom") + getWherePart("InsuredName", "QInsuredName");
//           + " union select ContNo,PrtNo,RiskCode,ContType,UWFlag,InsuredName,ManageCom from lcpol where grpcontno ='" + cGrpContNo + "' and UWFlag not in ('0', '1', '4', '9') " + getWherePart("ManageCom", "QManageCom") + getWherePart("InsuredName", "QInsuredName");
//		strSQL = "select ContNo,PrtNo,RiskCode,ContType,UWFlag,InsuredName,ManageCom from lcpol where grpcontno ='" + cGrpContNo + "' and UWFlag not in ('0', '1', '4', '9') " + getWherePart("ManageCom", "QManageCom") + getWherePart("InsuredName", "QInsuredName");

	//alert(strSQL);
	//execEasyQuery( strSQL );
	//��ѯSQL�����ؽ���ַ���
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("û��δͨ���˱��ĸ��˵���");
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
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	
	 isContPlan(); //�ж��Ƿ��Ʒ������ֱ���

	return true;
}

//���mutline��ѡ�򴥷��¼�
function easyQueryAddClick(parm1,parm2)
{
	// ��дSQL���
  ctrlButtonDisabled();
	k++;
	//var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	//var state = fm.State.value;       //Ͷ��������״̬
	var tProposalNo = "";
	var tPEdorNo = "";
	var strSQL = "";
	var ContNo = document.all(parm1).all('PolGrid1').value;
	document.all("ContNo").value = ContNo;
	
/****************************************************************************************************************************************
**************************************************************************************************************************/

	// ��ʼ�����
	initPolBox();
	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
	
if(fm.isContPlan.value!="true")
 {
	queryPolInfo(ContNo);
 }	 
 else
 {
	 queryPlanInfo(ContNo);	
 }

	var arrSelected = new Array();

//	strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	
	    var sqlid4="GroupContQuery4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(ContNo);//ָ������Ĳ���
	    strSQL=mySql4.getString();	
	
//	strSQL = "select ProposalNo,PrtNo,AgentCode,GrpContNo,PolNo from lcpol where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	if (arrSelected != null)
	{
		document.all('ProposalContNo').value = arrSelected[0][0];
		document.all('PrtNo').value = arrSelected[0][1];
//		fm.all('ManageCom').value = arrSelected[0][2];
//		fm.all('SaleChnl').value = arrSelected[0][3];
		document.all('AgentCode').value = arrSelected[0][2];
		document.all('GrpContNo').value = arrSelected[0][3];
		document.all('PolNo').value = arrSelected[0][4];
//		fm.all('AgentGroup').value = arrSelected[0][5];
//		fm.all('AgentCode1').value = arrSelected[0][6];
//		fm.all('AgentCom').value = arrSelected[0][7];
//		fm.all('AgentType').value = arrSelected[0][8];
//		fm.all('ReMark').value = arrSelected[0][9];
	 }
/***********************************************************************************************************************
************************************************************************************************************************/
	
	cGrpContNo = document.all("GrpContNo").value;
	
	    var sqlid5="GroupContQuery5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(cGrpContNo);//ָ������Ĳ���
	    strSQL=mySql5.getString();	
	
//	strSQL = "select Name,PostalAddress,ZipCode,Phone from lCGrpAppnt where GrpContNo='"+cGrpContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	if (arrSelected != null)
	{
		document.all('GrpName').value = arrSelected[0][0];
		document.all('PostalAddress').value = arrSelected[0][1];
		document.all('ZipCode').value = arrSelected[0][2];
		document.all('Phone').value = arrSelected[0][3];
	}
	
		 var sqlid6="GroupContQuery6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(ContNo);//ָ������Ĳ���
	    strSQL=mySql6.getString();	
	
//	strSQL = "select insuredname,insuredsex,insuredbirthday,insuredidtype,insuredidno,insuredno from lccont where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);	
	if (arrSelected != null)
	{
		document.all('InsuredName').value = arrSelected[0][0];
		document.all('InsuredSex').value = arrSelected[0][1];
		document.all('InsuredBirthday').value = arrSelected[0][2];
		document.all('InsuredIDType').value = arrSelected[0][3];
		document.all('InsuredIDno').value = arrSelected[0][4];
		document.all('InsuredNo').value = arrSelected[0][5];
		InsuredNo = arrSelected[0][5];
	}
	
		 var sqlid7="GroupContQuery7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(InsuredNo);//ָ������Ĳ���
		mySql7.addSubPara(ContNo);//ָ������Ĳ���
	    strSQL=mySql7.getString();	
	
//	strSQL = "select OccupationType from lcinsured where InsuredNo='"+InsuredNo+"' and ContNo='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);	
	if (arrSelected != null)
	{
		document.all('OccupationType').value = arrSelected[0][0];
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
	
		var sqlid8="GroupContQuery8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(ProposalNo);//ָ������Ĳ���
	    var strSql =mySql8.getString();	
	
//	var strSql = "select * from LCApplyRecallPol where ProposalNo='" + ProposalNo + "' and InputState='0'";
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
	if ((cContFlag == 1)&&(fm.isContPlan.value!="true"))
	{
		var tSelNo = PolAddGrid.getSelNo()-1;
		if (tSelNo < 0)
		{
		    alert("����ѡ��������Ϣ��");
			return;
		}
		flag = document.all('UWState').value;
		var ProposalNo = "";
		ProposalNo = document.all('PolNo').value;

		if (ProposalNo == "")
		{
			alert("����ѡ�񱣵���");
			return;
		}
		var MainPolNo = PolAddGrid.getRowColData(tSelNo, 2);

		if (trim(fm.UWState.value) == "") 
		{
			alert("������¼��˱����ۣ�");
		  return;
		}

		if (!checkBackPol(ProposalNo)) 
		{
		  if (!confirm("��Ͷ�����г������룬�����´˽�����")) return;
		}

		if (trim(fm.UWState.value) == "6") 
		{
		  fm.UWOperater.value = operator;
		}
		var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		fm.action = "./UWManuNormGChk.jsp";
		fm.submit();
		bUWNormChk = true;
 }
	else if (cContFlag == 2)
	{
		flag = document.all('ContUWState').value;
		var cContNo = "";
		cContNo = document.all('ContNo').value;

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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		fm.action = "./UWContManuNormGChk.jsp";
		fm.submit();
		bContUWNormChk = true;
	}
	else if (cContFlag == 3)
	{
		flag = "9";
		var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		fm.action = "./UWBatchManuNormGChk.jsp";
		fm.submit();
		bContUWNormChk = true;
	}
 else if((cContFlag == 1)&&(fm.isContPlan.value=="true"))
 {//��Ʒ����º˱�����
 	PlanUWRsulltCommit();
 }
}


//������ظ�
function QuestReply()
{
	cProposalNo = fm.ProposalNo.value;  //��������

	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestReplyMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);

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
  	if (tSelNo < 0)
  	{
  	    alert("��ѡ��һ����¼��");
  	    return;
  	}
  	QuestQueryFlag[tSelNo] = 1 ;
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestQueryMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window2",sFeatures);
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
  cPrtNo = fm.PrtNoHide.value; //ӡˢ��  
  
  if (cContNo != "")
  {			
	window.open("./RReportQueryMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo,sFeatures);
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
	window.open("./BackPolQueryMain.jsp?ProposalNo1="+cProposalNo,"",sFeatures);
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
	window.open("./OutTimeQueryMain.jsp?ProposalNo1="+cProposalNo,"",sFeatures);
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
  	
		var sqlid9="GroupContQuery9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(cProposalNo);//ָ������Ĳ���
	    var strSql =mySql9.getString();	
	
//  	 var strSql = "select * from LCIssuepol where ProposalNo='" + cProposalNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
     var arrResult = easyExecSql(strSql);
     if (arrResult != null) {
       var tInfo = "��δ�ظ��������,ȷ��Ҫ���гб��ƻ����?";
       if(!window.confirm( tInfo ))
         	    return;
      }
    window.open("../app/ProposalEasyScan.jsp?LoadFlag=3&Type="+cType+"&prtNo="+cPrtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

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
		
		var sqlid10="GroupContQuery10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(cProposalNo);//ָ������Ĳ���
	    var strSql =mySql10.getString();	
		
//		var strSql = "select * from LCIssuepol where ProposalNo='" + cProposalNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
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
	document.all('edoruwstate').value = "";
	document.all('UWPopedomCode').value = "";
	document.all('UWIdea').value = "";
}

function setproposalno()
{
	var count = PolGrid.getSelNo();
	document.all('ProposalNo').value = PolGrid.getRowColData(count - 1,1,PolGrid);
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
  	window.open("../uw/UWNotePadMain.jsp?ContNo="+cContNo+"&PrtNo="+PrtNo+"&OperatePos=3", "window1",sFeatures);
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
	divOperateButtion.style.display="none";
	divLCPlan.style.display="none";
	divLCPolButton.style.display= "none";
	divMain.style.display = "none";
	DivLCAppntInd.style.display="none";
	DivLCAppntIndButton.style.display="none";
	divUWResult.style.display="none";
	divContUWResult.style.display="none";
	var cGrpContNo = document.all("GrpContNo").value
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
	  
	  	var sqlid11="GroupContQuery11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(tPolNo);//ָ������Ĳ���
	    var tSql =mySql11.getString();	
	  
//	  var tSql = "select passflag ,uwidea from lcuwmaster where polno = '"+tPolNo+"'";
  	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
		document.all('UWState').value = arrSelected[0][0];
		document.all('UWIdea').value = arrSelected[0][1];
	
}
function amntAccumulate()
{
	var tcontno=document.all('ContNo').value;
	//alert("tcontno"+tcontno);
	
		var sqlid12="GroupContQuery12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(tcontno);//ָ������Ĳ���
	    var strSql =mySql12.getString();	
	
//	var strSql = "select InsuredNo from lccont where ProposalContNo = '"+tcontno+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	var tInsuredNo= arrSelected[0][0];
	//alert("arrResult"+tInsuredNo);
	
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+tInsuredNo,"window1",sFeatures);	
}
function queryHealthImpart(){
	
	var cContNo=document.all('ContNo').value;
	
		var sqlid13="GroupContQuery13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(cContNo);//ָ������Ĳ���
	    var strSql =mySql13.getString();	
	
//	var strSql = "select InsuredNo from lccont where ProposalContNo = '"+cContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	var tInsuredNo= arrSelected[0][0];
	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+tInsuredNo,"window1",sFeatures);
}
function queryProposal(){
	  
//	var cContNo = fm.all('GrpContNo').value;
//	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
//	var arr=easyExecSql(strsql);
//	
//	if(arr!=null){
//		fm.all('AppntNo').value=arr[0][0];
//	}
	
    window.open("./ProposalQueryMain.jsp?CustomerNo="+InsuredNo,"",sFeatures);

}
function queryNotProposal(){
  
//  var cContNo = fm.all('GrpContNo').value;
//	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
//	var arr=easyExecSql(strsql);
//	if(arr!=null){
//		fm.all('AppntNo').value=arr[0][0];
//	}


	window.open("./NotProposalQueryMain.jsp?CustomerNo="+InsuredNo,"",sFeatures);

}
//*****************add by haopan********************//
function ctrlButtonDisabled()
{		
		var tSel = PolGrid.getSelNo();
		var cContNo=PolGrid.getRowColData(tSel - 1,1);

		var sqlid14="GroupContQuery14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(cContNo);//ָ������Ĳ���
	    var strSql =mySql14.getString();	

//		var strSql = "select InsuredNo from lccont where ProposalContNo = '"+cContNo+"'";
		turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		var tInsuredNo= arrSelected[0][0];
		//alert(tInsuredNo);
		var tGrpContNo = document.all("GrpContNo").value;
		 
	  var tSQL = "";
  	var arrResult;
  	var arrButtonAndSQL = new Array;
  	
	  arrButtonAndSQL[0] = new Array;
	  arrButtonAndSQL[0][0] = "uwButton1";
	  arrButtonAndSQL[0][1] = "����������ϸ��Ϣ";
	  arrButtonAndSQL[0][2] = "";
	  	  
	  arrButtonAndSQL[1] = new Array;
	  arrButtonAndSQL[1][0] = "uwButton2";
	  arrButtonAndSQL[1][1] = "�����˱����ۼ���Ϣ";
	  arrButtonAndSQL[1][2] = "";
	  
	  arrButtonAndSQL[2] = new Array;
	  arrButtonAndSQL[2][0] = "uwButton3";
	  arrButtonAndSQL[2][1] = "�������ѳб�������ѯ";
	  arrButtonAndSQL[2][2] = "select * from lcpol a ,lccont b where a.contno=b.contno and a.insuredno='"+tInsuredNo+"' and a.appflag in ('1','4')";
	  
	  arrButtonAndSQL[3] = new Array;
	  arrButtonAndSQL[3][0] = "uwButton4";
	  arrButtonAndSQL[3][1] = "������δ�б�������ѯ";
	  arrButtonAndSQL[3][2] = "select * from lcpol a ,lccont b where a.contno=b.contno and a.insuredno='"+tInsuredNo+"' and a.appflag = '0'";
	  
	  arrButtonAndSQL[4] = new Array;
	  arrButtonAndSQL[4][0] = "uwButton5";
	  arrButtonAndSQL[4][1] = "�����Զ��˱���Ϣ";
	  arrButtonAndSQL[4][2] = "";
	  
	  arrButtonAndSQL[5] = new Array;
	  arrButtonAndSQL[5][0] = "uwButton6";
	  arrButtonAndSQL[5][1] = "�����˱��켣";
	  arrButtonAndSQL[5][2] = "";
	  
	  arrButtonAndSQL[6] = new Array;
	  arrButtonAndSQL[6][0] = "uwButton7";
	  arrButtonAndSQL[6][1] = "������֪��ѯ";
	  arrButtonAndSQL[6][2] = "select * from lccustomerimpart where grpcontno='"+tGrpContNo+"' and customerno='"+tInsuredNo+"' and rownum=1";
	
		arrButtonAndSQL[7] = new Array;
	  arrButtonAndSQL[7][0] = "uwButton8";
	  arrButtonAndSQL[7][1] = "������ϲ�ѯ";
	  arrButtonAndSQL[7][2] = "select * from lcpenotice where grpcontno='"+tGrpContNo+"' and customerno='"+tInsuredNo+"' and rownum=1";
	  
	  arrButtonAndSQL[8] = new Array;
	  arrButtonAndSQL[8][0] = "uwButton9";
	  arrButtonAndSQL[8][1] = "�������¼��";
	  arrButtonAndSQL[8][2] = "";
	
		arrButtonAndSQL[9] = new Array;
	  arrButtonAndSQL[9][0] = "uwButton10";
	  arrButtonAndSQL[9][1] = "��������¼��";
	  arrButtonAndSQL[9][2] = "";
	  
	  arrButtonAndSQL[10] = new Array;
	  arrButtonAndSQL[10][0] = "uwButton14";
	  arrButtonAndSQL[10][1] = "�����˼�����ȫ��ѯ";
	  arrButtonAndSQL[10][2] = "select * from LPEdorMain a where a.contno in (select c.contno from lccont c where insuredno='"+tInsuredNo+"')";
	  
	  arrButtonAndSQL[11] = new Array;
	  arrButtonAndSQL[11][0] = "uwButton15";
	  arrButtonAndSQL[11][1] = "�����˼��������ѯ";
	  arrButtonAndSQL[11][2] = "select * from llregister a,llcase b where a.rgtno = b.caseno  and b.CustomerNo ='"+tInsuredNo+"'";
	  
	
		for(var i=0; i<arrButtonAndSQL.length; i++)
			{
				if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!="")
				{
					//alert(arrButtonAndSQL[i][1]+":"+arrButtonAndSQL[i][2]);
					arrResult = easyExecSql(arrButtonAndSQL[i][2]);
				
					if(arrResult!=null)
					{
					
						eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
					}
					else
					{
							eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
					}
				}
				else
				{
					continue;
				}	
			}
	}
	
	//������ȫ��ѯ
function queryEdor(){
	var cContNo=PolGrid.getRowColData(0,1);

		var sqlid15="GroupContQuery15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
		mySql15.addSubPara(cContNo);//ָ������Ĳ���
	    var strSql =mySql15.getString();	

//		var strSql = "select InsuredNo from lccont where ProposalContNo = '"+cContNo+"'";
		turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		var tInsuredNo= arrSelected[0][0];

	window.open("./EdorQueryMain.jsp?CustomerNo="+tInsuredNo,"window1",sFeatures);
}

//���������ѯ
function queryClaim(){
	var cContNo=PolGrid.getRowColData(0,1);

		var sqlid16="GroupContQuery16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
		mySql16.addSubPara(cContNo);//ָ������Ĳ���
	    var strSql =mySql16.getString();	

//		var strSql = "select InsuredNo from lccont where ProposalContNo = '"+cContNo+"'";
		turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		var tInsuredNo= arrSelected[0][0];

	window.open("./ClaimQueryMain.jsp?CustomerNo="+tInsuredNo,"window1",sFeatures);
}

function queryPolInfo(ContNo)
{
	initPolAddGrid();
//modify by zhangxing
	//strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.UWFlag,LCPol.AppntName,LCPol.InsuredName,LCPol.Mult,LCPol.Prem,LCPol.Amnt from LCPol where ContNo='"+ContNo+"'" + " and UWFlag not in ('0', '1', '4', '9')"
	
		var sqlid17="GroupContQuery17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
		mySql17.addSubPara(ContNo);//ָ������Ĳ���
	    strSQL=mySql17.getString();	
	
//	strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.UWFlag,LCPol.AppntName,LCPol.InsuredName,LCPol.Mult,LCPol.Prem,LCPol.Amnt from LCPol where ContNo='"+ContNo+"'"
	  //��ѯSQL�����ؽ���ַ���
	turnPage.strQueryResult  = easyQueryVer3(strSQL,1,0,1);

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) 
	{
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
		divOperateButtion.style.display="";
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
		initFlag(arrDataSet.length);
	}
}

//*************add end****************//

/******************************************
*���²�Ʒ�����Ϣ��ѯ
*
******************************************/
function isContPlan()
{
	//�ж��Ƿ�Ϊ��Ʒ��ϱ���
	
		var sqlid18="GroupContQuery18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
		mySql18.addSubPara(document.all("GrpContNo").value);//ָ������Ĳ���
	    var sql=mySql18.getString();	
	
	//var sql="select contplancode from lccontplan where 1=1 and plantype='6' and grpcontno='"+fm.all("GrpContNo").value+"'";
	var arrResult=new Array();
	arrResult=easyExecSql(sql);
	if(arrResult !=null)
	{
		fm.isContPlan.value="true"; //Ϊ��Ʒ������ֱ���
		fm.AddPremInput.disabled=true;
		fm.AddSpecInput.disabled=true;
	}
  else
	{
		fm.isContPlan.value="false";
	}
}
function queryPlanInfo(tContNo)
{//��Ʒ�����Ϣ��ѯ
	initPlanAddGrid();
	
		var sqlid19="GroupContQuery19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
		mySql19.addSubPara(tContNo);//ָ������Ĳ���
		mySql19.addSubPara(tContNo);//ָ������Ĳ���
		mySql19.addSubPara(tContNo);//ָ������Ĳ���
		
		mySql19.addSubPara(tContNo);//ָ������Ĳ���
		mySql19.addSubPara(tContNo);//ָ������Ĳ���
		mySql19.addSubPara(tContNo);//ָ������Ĳ���
	    var planSQL=mySql19.getString();	
	
//	var planSQL=" select (select contplancode from lcinsured where contno='"+tContNo+"'),'6',"
//	           +" (select appntname from lccont where contno='"+tContNo+"'),"
//	           +" (select insuredname from lccont where contno='"+tContNo+"'),"
//             +" (select sum(mult)/count(*) from lcpol where contno = '"+tContNo+"'),"
//             +" (select sum(prem) from lcpol where contno = '"+tContNo+"'),"            
//             +" (select sum(amnt) from lcpol where contno = '"+tContNo+"') from dual ";   
  turnPage.queryModal(planSQL,PlanAddGrid);
  if(PlanAddGrid.mulLineCount==0)
  {
  	alert("û��δͨ���˱��ĸ������ֵ���");
		InitClick();
		return "";
  }
   
  divContUWResult.style.display="";
  divLCPlan.style.display= "";
  divOperateButtion.style.display="";
  divUWResult.style.display="";
	divLCPolButton.style.display= "";
           
}
//��ѯ��Ʒ��Ϻ˱�����
function InitPlanUWFlag()
{   
	  var tSelNo = PlanAddGrid.getSelNo()-1;;
	 
	  var tPlanCode = PlanAddGrid.getRowColData(tSelNo,1);
	  var tPlanType = PlanAddGrid.getRowColData(tSelNo,2);
	  
	  	var sqlid20="GroupContQuery20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("uw.GroupContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
		mySql20.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		mySql20.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql20.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
		
		mySql20.addSubPara(tPlanCode);//ָ������Ĳ���
		mySql20.addSubPara(tPlanType);//ָ������Ĳ���
	    var tSql=mySql20.getString();	
	  
//	  var tSql = "select passflag,uwidea from lcplanuwmaster where  grpcontno='"+fm.GrpContNo.value+"'"
//	           + " and contno = '"+fm.ContNo.value+"' and insuredno='"+fm.InsuredNo.value+"'"
//	           + " and contplancode='"+tPlanCode+"' and plantype='"+tPlanType+"'";
  	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		var arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
		document.all('UWState').value = arrSelected[0][0];
		document.all('UWIdea').value = arrSelected[0][1];	
}

//��Ʒ��Ϻ˱������ύ

function PlanUWRsulltCommit()
{
		var tSelNo = PlanAddGrid.getSelNo()-1;
		if (tSelNo < 0)
		{
		    alert("���Ȳ�Ʒ�����Ϣ��");
			return;
		}
		flag = document.all('UWState').value;

		if (trim(fm.UWState.value) == "") 
		{
			alert("������¼��˱����ۣ�");
		  return;
		}
/*
		if (!checkBackPol(ProposalNo)) 
		{
		  if (!confirm("��Ͷ�����г������룬�����´˽�����")) return;
		}
*/

		if (trim(fm.UWState.value) == "6") 
		{
		  //fm.UWOperater.value = operator;
		}
		
		fm.ContPlanCode.value = PlanAddGrid.getRowColData(tSelNo, 1);
		fm.PlanType.value = PlanAddGrid.getRowColData(tSelNo, 2);
		var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		fm.action = "./PlanUWManuNormGChk.jsp";
		fm.submit();
		bUWNormChk = true;
}

function afterPlanUWSubmit()
{
	
}
