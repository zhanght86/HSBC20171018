//�������ƣ�UWApp.js
//�����ܣ�����Ͷ����Ϣ��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  �� WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var tPOLNO = "";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var cflag = "1" // ����λ�� 1.����Ͷ����Ϣ��ѯ
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

// �ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  // showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
	var iWidth=550;      // �������ڵĿ��;
	var iHeight=250;     // �������ڵĸ߶�;
	var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
	var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  // showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); // �ύ
  // alert("submit");
}


// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  // showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    // showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
	var iWidth=550;      // �������ڵĿ��;
	var iHeight=250;     // �������ڵĸ߶�;
	var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
	var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    // ִ����һ������
  }
}


// ��ʾfrmSubmit��ܣ���������
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

// ����Ͷ����Ϣ
function showApp()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  // showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
	var iWidth=550;      // �������ڵĿ��;
	var iHeight=250;     // �������ڵĸ߶�;
	var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
	var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cProposalNo=fm.ProposalNoHide.value;
  cInsureNo = fm.InsuredNoHide.value;
  
  if (cProposalNo != "")
  {
  	// showModalDialog("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	window.open("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,"window2",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");  	
  }
}                   

// �����˱���¼
function showOldUWSub()
{
  var tSelNo = PolGrid.getSelNo();

	if( tSelNo == 0 || tSelNo == null )
	{
		alert( "����ѡ��һ����¼��" );
		return;
	}
	
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    // showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
	var iWidth=550;      // �������ڵĿ��;
	var iHeight=250;     // �������ڵĸ߶�;
	var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
	var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
	var cProposalNo = PolGrid.getRowColData(tSelNo - 1,1);	
  // cProposalNo=fm.ProposalNoHide.value;
  // showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  if (cProposalNo != "")
  {
  	window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window2",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");  	
  }
}

// ��ǰ�˱���¼
function showNewUWSub()
{
	var tSelNo = PolGrid.getSelNo();
  
  if( tSelNo == 0 || tSelNo == null )
	{
		alert( "����ѡ��һ����¼��" );
		return;
	}
	
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    // showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
	var iWidth=550;      // �������ڵĿ��;
	var iHeight=250;     // �������ڵĸ߶�;
	var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
	var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	var cProposalNo = PolGrid.getRowColData(tSelNo - 1,1);	
  // cProposalNo=fm.ProposalNoHide.value;
  if (cProposalNo != "")
  {
  	window.open("./UWErrMain.jsp?ProposalNo1="+cProposalNo,"window2",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");  
  }
}                      
      

// ������ϸ��Ϣ
function showPolDetail()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  // showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var tSelNo = PolGrid.getSelNo();
  
  if( tSelNo == 0 || tSelNo == null )
	{
		alert( "����ѡ��һ����¼��" );
		return;
	} 
	var cPolNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
  if (cPolNo != "")
  {
  	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  	mSwitch.updateVar("PolNo", "", cPolNo);
		
	var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 3);
  	window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+prtNo,"",sFeatures);
  	// showInfo.close();
  
  }
  else
  {  	
  	alert("����ѡ�񱣵�!");	
  }

}

// ������ϲ�ѯ
function showHealthQ()
{
  var cContNo = document.all('ContNoHide').value;	
  var tSelNo = PolGrid.getSelNo();
  var cPrtNo = PolGrid.getRowColData(tSelNo - 1,3);
	if( tSelNo == 0 || tSelNo == null )
	{
		alert( "����ѡ��һ����¼��" );
		return;
	}
	// alert(2);
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    // showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
	var iWidth=550;      // �������ڵĿ��;
	var iHeight=250;     // �������ڵĸ߶�;
	var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
	var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo,"window2",sFeatures);
  	showInfo.close();

}           

// �˱�������
function showReport()
{
  var tSelNo = PolGrid.getSelNo();

	if( tSelNo== 0 || tSelNo == null )
	{
		alert( "����ѡ��һ����¼��" );
		return;
	}
	  
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    // showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   // ��ҳ���ƣ���Ϊ��;
	var iWidth=550;      // �������ڵĿ��;
	var iHeight=250;     // �������ڵĸ߶�;
	var iTop = (window.screen.availHeight - iHeight) / 2; // ��ô��ڵĴ�ֱλ��
	var iLeft = (window.screen.availWidth - iWidth) / 2;  // ��ô��ڵ�ˮƽλ��
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
	var cProposalNo = PolGrid.getRowColData(tSelNo - 1,1);	
  // cProposalNo=fm.ProposalNoHide.value;
  if (cProposalNo != "")
  {
  	// window.open("./UWManuReportMain.jsp?ProposalNo1="+cProposalNo+"&flag="+cflag,"window2",sFeatures);
  	window.open("UWQueryOldSubReportMain.jsp?PolNo="+cProposalNo,"window2",sFeatures);
 	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}                    
    

// ��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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

// ѡ��Ҫ�˹��˱�����
function queryUWSub()
{
	// showSubmitFrame(mDebug);
	document.getElementById("fm").submit();
}

// ��ѯ��ť
function easyQueryClick()
{
	
	// ��дSQL���
	// alert("test");
	var strSQL = "";
	var i, j, m, n;
	var contno = document.all('ContNoHide').value;// alert("contno====="+contno);
	var insureno = document.all('InsureNoHide').value;// alert("insuredno====="+insuredno);
	var appntno = document.all('AppntNoHide').value;// alert("appntno====="+appntno);
	if ((insureno != null) && (insureno != "null") && (insureno != ""))
	{
		/*
		 * strSQL = "select
		 * LCPol.polno,LCPol.proposalno,LCPol.prtno,LCPol.cvalidate,LMRisk.riskname,LCPol.prem,LCPol.amnt,case
		 * substr(LCPol.polstate,1,2) when '' then '99'
		 * end,LCPol.OccupationType,LCUWMaster.healthflag,LCUWMaster.specflag,LCUWMaster.passflag
		 * ,LCPol.ClaimTimes from LCPol,LCUWMaster,LMRisk where 1=1 " + " and
		 * LCPol.insuredno ='"+insureno+"'" + " and LCPol.contno <>
		 * '"+contno+"'" + " and LCPol.polno = LCUWMaster.polno" + " and
		 * LMRisk.riskcode = lcpol.riskcode" + " and LCPol.uwcode is not null" + "
		 * union" + " select
		 * LCPol.polno,LCPol.proposalno,LCPol.prtno,LCPol.cvalidate,LMRisk.riskname,LCPol.prem,LCPol.amnt,case
		 * substr(LCPol.polstate,1,2) when '' then '99'
		 * end,LCPol.OccupationType,'0','0','0',LCPol.ClaimTimes from
		 * LCPol,LMRisk where 1=1 " + " and LCPol.insuredno ='"+insureno+"'" + "
		 * and LCPol.contno <> '"+contno+"'" + " and LCPol.uwcode is null" + "
		 * and LMRisk.riskcode = lcpol.riskcode" + " order by 1";
		 */		
				 
		var sqlid1="UWAppSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(contno);// ָ������Ĳ���
		mySql1.addSubPara(insureno);// ָ������Ĳ���
	    strSQL=mySql1.getString();	
				 
// strSQL = " select
// t.polno,t.prtno,a.riskname,t.insuredname,t.prem,t.amnt,t.mult from lcpol
// t,lmrisk a where 1=1"
// + " and t.contno <> '"+contno+"'"
// + " and t.insuredno = '"+insureno+"'"
// + " and t.riskcode = a.riskcode"
// ;
		 	 	 
	}
	else
	{
		/*
		 * strSQL = "select
		 * LCPol.polno,LCPol.proposalno,LCPol.prtno,LCPol.cvalidate,LMRisk.riskname,LCPol.prem,LCPol.amnt,case
		 * substr(LCPol.polstate,1,2) when '' then '99'
		 * end,LCPol.OccupationType,LCUWMaster.healthflag,LCUWMaster.specflag,LCUWMaster.passflag
		 * ,LCPol.ClaimTimes from LCPol,LCUWMaster,LMRisk where 1=1 " + " and
		 * LCPol.AppntNo ='"+appntno+"'" + " and LCPol.contno <> '"+contno+"'" + "
		 * and LCPol.polno = LCUWMaster.polno" + " and LMRisk.riskcode =
		 * lcpol.riskcode" + " and LCPol.uwcode is not null" + " union" + "
		 * select
		 * LCPol.polno,LCPol.proposalno,LCPol.prtno,LCPol.cvalidate,LMRisk.riskname,LCPol.prem,LCPol.amnt,case
		 * substr(LCPol.polstate,1,2) when '' then '99'
		 * end,LCPol.OccupationType,'0','0','0',LCPol.ClaimTimes from
		 * LCPol,LMRisk where 1=1 " + " and LCPol.AppntNo ='"+appntno+"'" + "
		 * and LCPol.contno <> '"+contno+"'" + " and LCPol.uwcode is null" + "
		 * and LMRisk.riskcode = lcpol.riskcode" + " order by 1";
		 */			
		
		var sqlid2="UWAppSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(appntno);// ָ������Ĳ���
		mySql2.addSubPara(insureno);// ָ������Ĳ���
	    strSQL=mySql2.getString();	
				 
// strSQL = " select
// t.polno,t.prtno,a.riskname,t.insuredname,t.prem,t.amnt,t.mult from lcpol
// t,lmrisk a where 1=1"
// + " and t.contno <> '"+appntno+"'"
// + " and t.insuredno = '"+insureno+"'"
// + " and t.riskcode = a.riskcode"
// ;
	}// prompt("",strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  // �ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�޼���Ͷ����Ϣ��");
    return "";
  }
  
  // ��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  // ���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;    
          
  // ����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  // ���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  // �ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  // alert(arrDataSet);
  // ����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;

	
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		// alert("result:"+arrResult);
	} // end of if
}

function ChoClick()
{
 	var tsel = PolGrid.getSelNo() - 1;
    if (tsel >= 0 )
    {
    	document.all("GridNoHide").value = '1';
    	document.all("ContNoHide").value = PolGrid.getRowColData(tsel, 1);// alert("fm.ContNoHide.value
																			// "+fm.ContNoHide.value);
    	document.all("PrtNoHide").value = PolGrid.getRowColData(tsel, 2);
    	document.all("GrpContNoHide").value = '';
    	// ͬʱ��Ҫ������mullineѡ�м�¼���
			initNotPolGrid();
			initGrpPolGrid();
			queryNotPol(document.all("OldContNo").value,document.all("OldCustomerNo").value);
			queryGrpPol(document.all("OldContNo").value,document.all("OldCustomerNo").value);
			
			// ���ư�ť����
		ctrlButtonDisabled(document.all("ContNoHide").value);
    }

}

function ChoNotClick()
{
    var tNsel = NotPolGrid.getSelNo() - 1;     
    if (tNsel >= 0 )
    {
    	document.all("GridNoHide").value = '2';
    	document.all("ContNoHide").value = NotPolGrid.getRowColData(tNsel, 1);// alert("fm.ContNoHide.value
																				// "+fm.ContNoHide.value);
    	document.all("PrtNoHide").value = NotPolGrid.getRowColData(tNsel, 2);
    	document.all("GrpContNoHide").value = '';
    	// ͬʱ��Ҫ������mullineѡ�м�¼���
    	initPolGrid();
			initGrpPolGrid();
			queryPol(document.all("OldContNo").value,document.all("OldCustomerNo").value);
			queryGrpPol(document.all("OldContNo").value,document.all("OldCustomerNo").value);
		// ���ư�ť����
		ctrlButtonDisabled(document.all("ContNoHide").value);
    }

}

function ChoGrpClick()
{  
    var tGsel = GrpPolGrid.getSelNo() - 1;
    if (tGsel >= 0 )
    {
    	document.all("GridNoHide").value = '3';
    	document.all("ContNoHide").value = GrpPolGrid.getRowColData(tGsel, 1);// alert("fm.ContNoHide.value
																				// "+fm.ContNoHide.value);
    	document.all("PrtNoHide").value = GrpPolGrid.getRowColData(tGsel, 2);
    	document.all("GrpContNoHide").value = GrpPolGrid.getRowColData(tGsel, 13);
    	// ͬʱ��Ҫ������mullineѡ�м�¼���
    	initPolGrid();
			initNotPolGrid();
			queryPol(document.all("OldContNo").value,document.all("OldCustomerNo").value);
			queryNotPol(document.all("OldContNo").value,document.all("OldCustomerNo").value);
		
		// ���ư�ť����
		ctrlButtonDisabled(document.all("ContNoHide").value);
		document.all('button4').disabled=true;
    }

}

/*******************************************************************************
 * ��ѯ���˺�ͬ��Ϣ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */	
function queryCont()
{
	// ��дSQL���
	// alert("test");
	var strSQL = "";
	var i, j, m, n;
	var contno = document.all('ContNoHide').value;
	var insureno = document.all('InsureNoHide').value;
	var appntno = document.all('AppntNoHide').value;
	if ((insureno != null) && (insureno != "null") && (insureno != ""))
	{
		
		var sqlid3="UWAppSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);// ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(contno);// ָ������Ĳ���
		mySql3.addSubPara(insureno);// ָ������Ĳ���
	    strSQL=mySql3.getString();	
		
// strSQL = "select contno,prtno,CValiDate,peoples,prem,amnt,mult from lccont
// where 1=1 "
// + " and contno <> '"+contno+"'"
// + " and insuredno = '"+insureno+"'"
// ;
	}
	else
	{
		
		var sqlid4="UWAppSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);// ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(contno);// ָ������Ĳ���
		mySql4.addSubPara(appntno);// ָ������Ĳ���
	    strSQL=mySql4.getString();	
		
// strSQL = "select contno,prtno,CValiDate,peoples,prem,amnt,mult from lccont
// where 1=1 "
// + " and contno <> '"+contno+"'"
// + " and appntno = '"+appntno+"'"
// ;
	}
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  // �ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�޼�����ͬ��Ϣ��");
    return "";
  }
  
  // ��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  // ���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = ContGrid;    
          
  // ����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  // ���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  // �ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  // alert(arrDataSet);
  // ����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  return true;
	
}

/*******************************************************************************
 * ��ѯ�����ѳб�������Ϣ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */	
function queryPol(tContNo,tCustomerNo)
{
	// ��дSQL���
	// alert("tContNo!!!!!!!!!!!"+tContNo);
	var strSQL = "";
	// var insureno = document.all('InsureNoHide').value;
	// var appntno = document.all('AppntNoHide').value;
	
		var sqlid5="UWAppSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);// ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(tCustomerNo);// ָ������Ĳ���
		mySql5.addSubPara(tCustomerNo);// ָ������Ĳ���
		mySql5.addSubPara(tCustomerNo);// ָ������Ĳ���
// strSQL=mySql5.getString();
	var addStr1 = " and 1=1 ";	
	var addStr2 = " and 2=2 ";	
	
// strSQL = "select contno,prtno,(select riskname from lmriskapp where riskcode
// = l.riskcode),appntname,insuredname"
// + " ,(select max(codename) from ldcode where codetype='occupationtype' and
// code in ((select occupationtype from lcinsured where insuredno='"+
// tCustomerNo +"' and contno=l.contno),"
// + " (select occupationtype from lcappnt where appntno = '"+tCustomerNo+"' and
// contno=l.contno)))"
// + " ,amnt,mult,prem"
// + " ,CValiDate,decode(appflag,'1','��ǩ��','����ֹ')"
// + " ,(select count(1) from lcpenotice where contno=l.contno and printflag is
// not null and customerno ='"+tCustomerNo+"')"
// + " ,(select count(1) from LCRReport where contno=l.contno and replyflag is
// not null )"
// + " ,decode(uwflag,'a',(select commonreason from lccuwmaster where
// contno=l.contno),(select codename from ldcode where codetype = 'contuwstate'
// and code=l.uwflag))"
// + " ,decode((select count(1) from LLClaimPolicy where
// contno=l.contno),'0','N','Y')"
// + " ,decode((select count(1) from LPEdorMain where
// contno=l.contno),'0','N','Y')"
	
		                 
/*
 * strSQL = strSQL + " from lcpol l where 1=1 " + " and contno <> '"+tContNo+"'"
 */	
		         // tongmeng 2009-05-12 modify
		         // ֻ�鱻������Ϊ�����˳б�������
		         /**
					 * ˵���˴�:09-12-09 type=="2"�������� type=="1"����Ͷ����
					 * ��������Ҫ��ѯ���ÿͻ�������Ϊ�����˵����е����Լ���ΪͶ��������Ϊ121301�����е���
					 * Ͷ�������ѯ���ÿͻ�������ΪͶ�����˵����е��� ����������Լ���߼�һ��
					 */
		         if(fm.type.value=="2"){
		        	 addStr1 = " and (insuredno = '"+tCustomerNo+"' or (appntno = '"+tCustomerNo+"' and riskcode='121301')) and conttype='1' and appflag in ('1','4')" ;
		         }
    			if(fm.type.value=="1"){
// strSQL = strSQL+ " and (insuredno = '"+tCustomerNo+"' or (appntno =
// '"+tCustomerNo+"' and riskcode='121301')) and conttype='1' and appflag in
// ('1','4')" ;
    				addStr2 = " and (insuredno = '"+tCustomerNo+"' or appntno = '"+tCustomerNo+"' ) and conttype='1' and appflag in ('1','4')" ;
    			} 
// strSQL = strSQL+ " order by contno, insuredno, polno" ;
	
	mySql5.addSubPara(tContNo);// ָ������Ĳ���
	mySql5.addSubPara(addStr1);
	mySql5.addSubPara(addStr2);	
	strSQL=mySql5.getString(); 	 
	
	// ��ѯSQL�����ؽ���ַ���
  turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
// prompt("",strSQL);
  // �ж��Ƿ��ѯ�ɹ�
  if (!turnPage1.strQueryResult) {
  	divPol.style.display = 'none';
    return false;
 }
 turnPage1.queryModal(strSQL, PolGrid);// alert(111);
	
}

/*******************************************************************************
 * ��ѯ����δ�б�������Ϣ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */	
function queryNotPol(tContNo,tCustomerNo)
{
	// ��дSQL���
	// alert("test");
	var strSQL = "";
	// var insureno = document.all('InsureNoHide').value;
	// var appntno = document.all('AppntNoHide').value;
	var sqlid16or17="";
	if(_DBT==_DBO){
//		 strSQL = "select contno,prtno,(select riskname from lmriskapp where riskcode = l.riskcode),appntname,insuredname,amnt,mult,prem,polapplydate"
//  + " ,(case (select activityid from lwmission where missionprop1=l.prtno and activityid in (select activityid from lwactivity  where functionid in('10010028','10010005','10010042')) and rownum=1) when '0000001003' then '���Ժ�' when '0000001100' then (select (select concat(concat('�˹��˱�',' '),codename) from ldcode where codetype='uwstatus' and code= (select uwstate from lccuwmaster where contno= w.missionprop1)) from lwmission w where missionprop1=l.prtno and activityid in (select activityid from lwactivity  where functionid ='10010028')) when '0000001150' then '��ǩ��' else (select (case when (select concat('��Ͷ������',codename) from ldcode where codetype='uwstate' and code=lccont.uwflag) is not null then (select concat('��Ͷ������',codename) from ldcode where codetype='uwstate' and code=lccont.uwflag) else '¼�븴��״̬' end) from lccont where contno=l.contno) end)";
        
		var sqlid16or17="UWAppSql16";

		
	}else if(_DBT==_DBM){
//		strSQL = "select contno,prtno,(select riskname from lmriskapp where riskcode = l.riskcode),appntname,insuredname,amnt,mult,prem,polapplydate"
//
//			+ " ,(case (select activityid from lwmission where missionprop1=l.prtno and activityid in (select activityid from lwactivity  where functionid in('10010028','10010005','10010042')) limit 1) when '0000001003' then '���Ժ�' when '0000001100' then (select (select concat(concat('�˹��˱�',' '),codename) from ldcode where codetype='uwstatus' and code= (select uwstate from lccuwmaster where contno= w.missionprop1)) from lwmission w where missionprop1=l.prtno and activityid in (select activityid from lwactivity  where functionid ='10010028')) when '0000001150' then '��ǩ��' else (select (case when (select concat('��Ͷ������',codename) from ldcode where codetype='uwstate' and code=lccont.uwflag) is not null then (select concat('��Ͷ������',codename) from ldcode where codetype='uwstate' and code=lccont.uwflag) else '¼�븴��״̬' end) from lccont where contno=l.contno) end)";
		var sqlid16or17="UWAppSql17";
		// if(tType == '1')
		// {
//		var sqlid17="UWAppSql17";
//		var mySql17=new SqlClass();
//		mySql17.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
//		mySql17.setSqlId(sqlid17);// ָ��ʹ�õ�Sql��id
//		mySql17.addSubPara(tCustomerNo);// ָ������Ĳ���
//		mySql17.addSubPara(tCustomerNo);// ָ������Ĳ���
//		mySql17.addSubPara(tCustomerNo);// ָ������Ĳ���
//		mySql17.addSubPara(tCustomerNo);// ָ������Ĳ���
//		mySql17.addSubPara(tCustomerNo);// ָ������Ĳ���
//		mySql17.addSubPara(tCustomerNo);// ָ������Ĳ���
//		mySql17.addSubPara(tContNo);// ָ������Ĳ���
//		var strSql=mySql17.getString();
		
	}
//     strSQL = strSQL  + " ,(select count(1) from lcpenotice where contno=l.contno and printflag is not null and customerno ='"+tCustomerNo+"')"
//		                 + " ,(select count(1) from LCRReport where contno=l.contno and replyflag is not null )"
//		                 + " ,(case ((select count(1) from lwmission m,lbmission n where m.missionprop1 = l.prtno and m.missionprop1=n.missionprop1 and m.activityid=n.activityid and m.activityid in (select activityid from lwactivity  where functionid in('10010040')))) when 0 then 'N' else 'Y' end)"
//		                  
//		                 + " ,(case (select count(distinct(PassFlag)) from LCUWSub where contno=l.contno and insuredno ='"+tCustomerNo+"' and changepolflag='1' ) when 0 then 'N' else 'Y' end)"
//		                 + " ,(case (select count(1) from lcissuepol where contno=l.contno "
// 
//		                 + " ) when 0 then 'N' else 'Y' end)"
//		                 + " ,(case (select count(1) from lccustomerimpart where impartver in ( '101','A01','B01','C01') and contno=l.contno and customerno ='"+tCustomerNo+"') when 0 then 'N' else 'Y' end)";
//		   
//    strSQL = strSQL  + " from lcpol l where 1=1 "
//		         + " and contno <> '"+tContNo+"'"
		         
		         var tCustomerNo1="";
    		     var tCustomerNo2="";
    		     var tCustomerNo3="";
    		     var tCustomerNo4="";
 		         if(tType == '1')
                 {
 		        	tCustomerNo1=tCustomerNo;
 		        	tCustomerNo2=tCustomerNo;
//		         	strSQL = strSQL  + " and (insuredno = '"+tCustomerNo+"' or appntno = '"+tCustomerNo+"') "
// 		        	 var sqlid18="UWAppSql18";
// 		        	 var mySql18=new SqlClass();
// 		        	 mySql18.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
// 		        	 mySql18.setSqlId(sqlid18);// ָ��ʹ�õ�Sql��id
// 		        	 mySql18.addSubPara(tCustomerNo);// ָ������Ĳ���
// 		        	 mySql18.addSubPara(tCustomerNo);// ָ������Ĳ���
// 		        	 mySql18.addSubPara(tCustomerNo);// ָ������Ĳ���
// 		        	 mySql18.addSubPara(tCustomerNo1);// ָ������Ĳ���
// 		        	 mySql18.addSubPara(tCustomerNo2);// ָ������Ĳ���
// 		        	 mySql18.addSubPara(tCustomerNo);// ָ������Ĳ���
// 		        	 mySql18.addSubPara(tCustomerNo);// ָ������Ĳ���
// 		        	 mySql18.addSubPara(tContNo);// ָ������Ĳ���
 		        	 //var strSql=mySql18.getString();
		         }
		         else
		         {
		        	 
		        	 tCustomerNo3=tCustomerNo;
	 		         tCustomerNo4=tCustomerNo;
//		         	strSQL = strSQL  + " and (insuredno = '"+tCustomerNo+"' or (appntno = '"+tCustomerNo+"' and riskcode='121301')) "
//
//		        		var sqlid19="UWAppSql19";
//		        		var mySql19=new SqlClass();
//		        		mySql19.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
//		        		mySql19.setSqlId(sqlid19);// ָ��ʹ�õ�Sql��id
//		        		mySql19.addSubPara(tCustomerNo);// ָ������Ĳ���
//		        		mySql19.addSubPara(tCustomerNo);// ָ������Ĳ���
//		        		mySql19.addSubPara(tCustomerNo);// ָ������Ĳ���
//		        		mySql19.addSubPara(tCustomerNo);// ָ������Ĳ���
//		        		mySql19.addSubPara(tCustomerNo);// ָ������Ĳ���
//		        		mySql19.addSubPara(tCustomerNo);// ָ������Ĳ���
//		        		mySql19.addSubPara(tCustomerNo);// ָ������Ĳ���
//		        		mySql19.addSubPara(tContNo);// ָ������Ĳ���
//		        		var strSql=mySql19.getString();
//		        		
                 }
//          	strSQL = strSQL + " and conttype='1' and appflag ='0'"  
//          		         + " order by contno, insuredno, polno" ;  	
	
  
       	 
     	 var mySql18=new SqlClass();
     	 mySql18.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
     	 mySql18.setSqlId(sqlid16or17);// ָ��ʹ�õ�Sql��id
     	 mySql18.addSubPara(tCustomerNo);// ָ������Ĳ���
     	 mySql18.addSubPara(tCustomerNo);// ָ������Ĳ���
     	 mySql18.addSubPara(tCustomerNo);// ָ������Ĳ���
     	 mySql18.addSubPara(tCustomerNo1);// ָ������Ĳ���
     	 mySql18.addSubPara(tCustomerNo2);// ָ������Ĳ���
     	 mySql18.addSubPara(tCustomerNo3);// ָ������Ĳ���
     	 mySql18.addSubPara(tCustomerNo4);// ָ������Ĳ���
     	 mySql18.addSubPara(tContNo);// ָ������Ĳ���
     	 var strSql=mySql18.getString();

     	 
	// ��ѯSQL�����ؽ���ַ���
  turnPage2.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 

  // �ж��Ƿ��ѯ�ɹ�
  if (!turnPage2.strQueryResult) {
  	divNotPol.style.display = 'none';
    return false;
 }
// prompt("",strSQL);
 turnPage2.queryModal(strSql, NotPolGrid);
}

/*******************************************************************************
 * ��ѯ�ŵ�������Ϣ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */	
function queryGrpPol(tContNo,tCustomerNo)
{
	// ��дSQL���
	// alert("test");
	var strSQL = "";
	// var insureno = document.all('InsureNoHide').value;
	// var appntno = document.all('AppntNoHide').value;
	
		var sqlid6="UWAppSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);// ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(tCustomerNo);// ָ������Ĳ���
		mySql6.addSubPara(tContNo);// ָ������Ĳ���
		mySql6.addSubPara(tCustomerNo);// ָ������Ĳ���
	    strSQL=mySql6.getString();	
	
// strSQL = "select contno,prtno,(select riskname from lmriskapp where riskcode
// = l.riskcode),insuredname,amnt,mult,CValiDate"
// + " ,decode(appflag,'0','�б�','1','��ǩ��','����ֹ')"
// + " ,decode((select count(1) from LLClaimPolicy where
// contno=l.contno),'0','N','Y')"
// + " ,decode((select count(1) from LPEdorMain where
// contno=l.contno),'0','N','Y')"
// + " ,(select codename from ldcode where codetype='occupationtype' and
// code=(select occupationtype from lcinsured where insuredno='"+ tCustomerNo
// +"' and contno=l.contno))"
// + " ,nvl(l.signtime,'0')"
// + " ,grpcontno"
// + " from lcpol l where 1=1 "
// + " and contno <> '"+tContNo+"'"
// + " and (insuredno = '"+tCustomerNo+"') and conttype='2'"
// + " order by contno, insuredno, polno" ;
	
	// ��ѯSQL�����ؽ���ַ���
  turnPage3.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  // �ж��Ƿ��ѯ�ɹ�
  if (!turnPage3.strQueryResult) {
  	divGrpPol.style.display = 'none';
    return false;
 }
// prompt("",strSQL);
 turnPage3.queryModal(strSQL, GrpPolGrid);

}

/*******************************************************************************
 * ��ѯ�ͻ���Ϣ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function queryPersonInfo(tCustomerNo){
	
		var sqlid7="UWAppSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);// ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(tCustomerNo);// ָ������Ĳ���
	    var aSQL=mySql7.getString();	
	
// var aSQL = "select a.name from ldperson a where
// a.customerno='"+tCustomerNo+"'";
  var arrResult = easyExecSql(aSQL);
  if(arrResult!=null)
  	document.all('CustomerName').value = arrResult[0][0];
  document.all('CustomerNo').value = tCustomerNo;
}

/*******************************************************************************
 * ��ѯ�ѳб�������ϸ��Ϣ
 * ********************************************************************
 */
function getContDetailInfo()
{    
    if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    
    if (document.all("GridNoHide").value == "2")
    {
        var contno = document.all("ContNoHide").value;
        var prtno = document.all("PrtNoHide").value;
        	
        // var tSql = "select salechnl from lccont where
		// contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
        /*
		 * var tSql = "select case lmriskapp.riskprop" + " when 'I' then" + "
		 * '1'" + " when 'G' then" + " '2'" + " when 'Y' then" + " '3'" + " end" + "
		 * from lmriskapp" + " where riskcode in (select riskcode" + " from
		 * lcpol" + " where polno = mainpolno" + " and contno = '" + contno +
		 * "')"
		 * 
		 * var BankFlag = "" var brrResult = easyExecSql(tSql); if (brrResult !=
		 * null) { BankFlag = brrResult[0][0]; }
		 */
        	// ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
		var BankFlag = "";
		var Subtype = "";
	    var tSplitPrtNo = contno.substring(0,4);
	    // alert("tSplitPrtNo=="+tSplitPrtNo);
	    // 8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
	    // 09-05-31����subtype=01���˱���Ͷ���� subtype=03���ʱ�ͨͶ����
	    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
	    	BankFlag="3";
	    	Subtype = "03"
	    }else{
	    	BankFlag="1";
	    	Subtype = "01"
	    }
	        window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo=" +  prtno + "&ContNo=" +  contno + "&BankFlag=" + BankFlag + "&SubType=" + Subtype, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
    }
    
    if (document.all("GridNoHide").value == "1" )
    {    
    	var contno = document.all("ContNoHide").value;
        var prtNo = document.all("PrtNoHide").value;
              
        	// ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
		var BankFlag = "";
		var Subtype = "";
	    var tSplitPrtNo = contno.substring(0,4);
	    // alert("tSplitPrtNo=="+tSplitPrtNo);
	    // 8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
	    // 09-05-31����subtype=01���˱���Ͷ���� subtype=03���ʱ�ͨͶ����
	    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
	    	BankFlag="3";
	    }else{
	    	BankFlag="1";
	    }
		    window.open("../sys/AllProQueryMain.jsp?LoadFlag=6&prtNo="+prtNo+"&ContNo="+contno+"&BankFlag="+BankFlag,"window1",sFeatures);
	 }
    
    if (document.all("GridNoHide").value == "3")
    {
    	 // var tGsel = GrpPolGrid.getSelNo() - 1;
  	    // window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&Auditing=1","window1");
  		// window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&checktype=2&ContType=2&Auditing=1&ProposalGrpContNo="+GrpPolGrid.getRowColData(tGsel,
		// 13)+"&ContNo="+GrpPolGrid.getRowColData(tGsel,
		// 1)+"&NameFlag=0"+"&GrpContNo="+GrpPolGrid.getRowColData(tGsel,
		// 13)+"&prtNo="+GrpPolGrid.getRowColData(tGsel, 2),"window1");
  		var cPrtNo = document.all("PrtNoHide").value;
    	var cGrpContNo = document.all("GrpContNoHide").value;
  		window.open("../sys/GrpPolDetailQueryMain.jsp?PrtNo=" + cPrtNo + "&GrpContNo=" + cGrpContNo ,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
}

// Ӱ�����ϲ�ѯ
function showImage()
{
   
    if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    var ContNo = document.all("ContNoHide").value;
    //var tPrtNoSql = "select prtno from lccont where contno='"+ContNo+"'";
    
    var sqlid20="UWAppSql20";
	var mySql20=new SqlClass();
	mySql20.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql20.setSqlId(sqlid20);// ָ��ʹ�õ�Sql��id
	mySql20.addSubPara(ContNo);// ָ������Ĳ���
    aSQL=mySql20.getString();	
    
    var cPrtNo = document.all("PrtNoHide").value;
    window.open("./ImageQueryMain.jsp?ContNo=" + cPrtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}

function showUWReport()
{
	if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    var ContNo = document.all("ContNoHide").value;
	
		var sqlid8="UWAppSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);// ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(ContNo);// ָ������Ĳ���
	    var reason_SQL=mySql8.getString();	
	
// var reason_SQL ="select prtno from lccont where contno='"+ContNo+"'";
    var prtno = easyExecSql(reason_SQL, 1, 1, 1); 
    if(document.all('type').value == "1") // ����Լ��prtno�����ѯ
    {
       window.open("./UWReportMain.jsp?ContNo="+prtno+"&NoType=1&QueryFlag=1","window5",sFeatures);// &OperatePos=
    }
    if(document.all('type').value == "2") // ��ȫ�����Լ�����������contno�����ѯ��notype=5���ú˱���ѯ
    {
       window.open("./UWReportMain.jsp?ContNo="+ContNo+"&NoType=5&QueryFlag=1","window5",sFeatures);// &OperatePos=
    }
}

// ����������ѯ
function QueryRecord(){
	if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    var ContNo = document.all("PrtNoHide").value;
	window.open("./RecordQueryMain.jsp?ContNo="+ContNo,"window1",sFeatures);
}

// �˱��ȴ���ѯ
function queryReason()
{
	if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    var ContNo = document.all("ContNoHide").value;

		var sqlid9="UWAppSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);// ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(ContNo);// ָ������Ĳ���
		mySql9.addSubPara(ContNo);// ָ������Ĳ���
	    var aSQL=mySql9.getString();	

// var aSQL = " select 1 from lwmission w where w.activityid='0000001100' and
// w.missionprop18 ='6' and w.MissionProp2=(select prtno from lccont where
// contno='"+ContNo+"') "
// + " union "
// + " select 1 from lbmission w where w.activityid='0000001100' and
// w.missionprop18 ='6' and w.MissionProp2=(select prtno from lccont where
// contno='"+ContNo+"') ";
	  var arrResult = easyExecSql(aSQL);
	  if(!arrResult)
	  {
	     alert("û�к˱��ȴ���Ϣ��");
	     return false;
	  }

	  window.open("./WaitReasonMain.jsp?ContNo="+ContNo+"&MissionID=''&SubMissionID=''&Type=2&OtherFlag=1","window3",sFeatures);
	  
}

// ��ѯ�����
function queryHealthReportResult(){
	var tFlag = "1"; 	
	if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    var ContNo = document.all("ContNoHide").value;
    var PrtNo = document.all("PrtNoHide").value;
 window.open("./UWManuHealthQMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&Flag="+tFlag,"window1",sFeatures);
}


// ��ѯ�������
function queryRReportResult(){

	if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    var tContNo = document.all("ContNoHide").value;
    var tPrtNo =document.all('PrtNoHide').value;
	var tFlag = "1";

 window.open("./RReportQueryMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo+"&Flag="+tFlag+"&QueryFlag=2","window1",sFeatures);

}

// �������ѯ
function QuestQuery()
{
    if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
	var cContNo = document.all("ContNoHide").value;  // ��������
	window.open("./QuestQueryMain.jsp?ContNo="+cContNo+"&Flag=1","window2",sFeatures);

}

 
 /***************************************************************************
	 * �ٱ���Ϣ�ظ� add by zhangxing 2005-08-10
	 * 
	 * ********************************************************************
	 */
function showUpReportReply()
{
    // alert(document.all("ContNoHide").value);
	if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
  var tPrtNo =document.all('PrtNoHide').value;
  var tContNo =document.all('ContNoHide').value;

	window.open("./UWManuUpReportReplyMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo,"window1",sFeatures);
}

// ���������ѯ
function queryClaim(){

	if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
  var tContNo =document.all('ContNoHide').value;
	window.open("./ClaimQueryMain.jsp?ContNo="+tContNo+"&CustomerNo="+tCustomerNo,"window1",sFeatures);
}
// �ͻ����������ѯ
function queryClaimCus(){

	if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
  var tContNo =document.all('ContNoHide').value;
	window.open("./ClaimQueryMainCus.jsp?ContNo="+tContNo+"&CustomerNo="+tCustomerNo,"window1",sFeatures);
}

// ������ȫ��ѯ
function queryEdor(){

	if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
  var tContNo =document.all('ContNoHide').value;

	window.open("./EdorQueryMain.jsp?ContNo="+tContNo,"window1",sFeatures);
}
// �ͻ�������ȫ��ѯ
function queryEdorCus(){

	if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
  var tContNo =document.all('ContNoHide').value;
  var tCustomerNo =document.all('CustomerNo').value;
	window.open("./EdorQueryMainCus.jsp?ContNo="+tContNo+"&CustomerNo="+tCustomerNo,"window1",sFeatures);
}

/*******************************************************************************
 * ��ʾ���շ���Ϣ add by heyq 2005-04-13
 * 
 * ********************************************************************
 */
function showTempFee()
{
  if (document.all("ContNoHide").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
  var Prtno =document.all('PrtNoHide').value;
  
  		var sqlid10="UWAppSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);// ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(document.all("ContNoHide").value);// ָ������Ĳ���
	    var aSQL=mySql10.getString();	
  
// var aSQL = "select AppntNo,AppntName from lccont where
// contno='"+document.all("ContNoHide").value+"'";
  var arrResult = easyExecSql(aSQL);
	var AppntNo = arrResult[0][0];
	var AppntName = arrResult[0][1];

  window.open("../uw/UWTempFeeQry.jsp?Prtno=" + Prtno + "&AppntNo=" + AppntNo + "&AppntName=" +AppntName,"",sFeatures);
}

/*******************************************************************************
 * ��ʼ����ť����
 * 
 * ********************************************************************
 */
function submitShow()
{
  
}

// ���ƽ��水ť��������ʾ
function ctrlButtonDisabled(tContNo){
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array;

  arrButtonAndSQL[0] = new Array;
  arrButtonAndSQL[0][0] = "button1";
  arrButtonAndSQL[0][1] = "Ͷ������ϸ��ѯ";
 // arrButtonAndSQL[0][2] = "select 1 from dual where 1=1";	

  var sqlid21="UWAppSql21";
	var mySql21=new SqlClass();
	mySql21.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql21.setSqlId(sqlid21);// ָ��ʹ�õ�Sql��id
	arrButtonAndSQL[0][2]=mySql21.getString();	
  
  arrButtonAndSQL[1] = new Array;
  arrButtonAndSQL[1][0] = "button2";
  arrButtonAndSQL[1][1] = "Ӱ�����ϲ�ѯ";
  if(_DBT==_DBO){
	  //arrButtonAndSQL[1][2] = "select 1 from es_doc_relation where bussno=(select prtno from lccont where contno='"+tContNo+"') and bussnotype='11' and rownum=1";
 
	  var sqlid22="UWAppSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql22.setSqlId(sqlid22);// ָ��ʹ�õ�Sql��id
		mySql22.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[1][2]=mySql22.getString();	
	  
  }else if(_DBT==_DBM){
	 // arrButtonAndSQL[1][2] = "select 1 from es_doc_relation where bussno=(select prtno from lccont where contno='"+tContNo+"') and bussnotype='11' limit 1";
  
	  var sqlid23="UWAppSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
		mySql23.setSqlId(sqlid23);// ָ��ʹ�õ�Sql��id
		mySql23.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[1][2]=mySql23.getString();	
  }
// prompt("",arrButtonAndSQL[1][2]);
  arrButtonAndSQL[2] = new Array;
  arrButtonAndSQL[2][0] = "button4";
  arrButtonAndSQL[2][1] = "����������ѯ";
 // arrButtonAndSQL[2][2] = "select 1 from dual where 1=1";

  var sqlid24="UWAppSql24";
	var mySql24=new SqlClass();
	mySql24.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql24.setSqlId(sqlid24);// ָ��ʹ�õ�Sql��id
	arrButtonAndSQL[2][2]=mySql24.getString();	
  
  arrButtonAndSQL[3] = new Array;
  arrButtonAndSQL[3][0] = "button5";
  arrButtonAndSQL[3][1] = "�˱��ȴ���ѯ";
// arrButtonAndSQL[3][2] = "select 1 from lwmission w where
// '1240648382000'='1240648382000' and w.activityid='0000001100' and
// w.missionprop18 ='6' and w.MissionProp2=(select prtno from lccont where
// contno='"+tContNo+"') "
// + " union "
//  arrButtonAndSQL[3][2] = "select 1 from lwmission w where '1240648382000'='1240648382000' and  w.activityid in (select activityid from lwactivity  where functionid ='10010028') and exists (select 1 from lccuwmaster where contno= w.missionprop1 and uwstate ='6') and w.MissionProp2=(select prtno from lccont where contno='"+tContNo+"') "
//                        + " union "
//                        + "select 1 from lbmission w where '1240648382000'='1240648382000' and  w.activityid in (select activityid from lwactivity  where functionid ='10010028') and exists (select 1 from lccuwmaster where contno= w.missionprop1 and uwstate ='6') and w.MissionProp2=(select prtno from lccont where contno='"+tContNo+"') ";

  var sqlid25="UWAppSql25";
	var mySql25=new SqlClass();
	mySql25.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql25.setSqlId(sqlid25);// ָ��ʹ�õ�Sql��id
	mySql25.addSubPara(tContNo);// ָ������Ĳ���
	mySql25.addSubPara(tContNo);// ָ������Ĳ���
	arrButtonAndSQL[3][2]=mySql25.getString();	
  
  arrButtonAndSQL[4] = new Array;
  arrButtonAndSQL[4][0] = "button3";
  arrButtonAndSQL[4][1] = "�˱����������ѯ";
//  arrButtonAndSQL[4][2] = "select * from LCUWReport where othernotype = '1'  and otherno in (select prtno from lccont where contno='"+tContNo+"' union select '"+tContNo+"' from dual "
//				 + " union select proposalno from lcpol where contno='"+tContNo+"')";
//  
  var sqlid26="UWAppSql26";
	var mySql26=new SqlClass();
	mySql26.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql26.setSqlId(sqlid26);// ָ��ʹ�õ�Sql��id
	mySql26.addSubPara(tContNo);// ָ������Ĳ���
	mySql26.addSubPara(tContNo);// ָ������Ĳ���
	mySql26.addSubPara(tContNo);// ָ������Ĳ���
	arrButtonAndSQL[4][2]=mySql26.getString();	
  
  arrButtonAndSQL[5] = new Array;
  arrButtonAndSQL[5][0] = "button6";
  arrButtonAndSQL[5][1] = "����������ϲ�ѯ";
  //arrButtonAndSQL[5][2] = "select * from lcpenotice where contno='"+tContNo+"'";
  
  var sqlid27="UWAppSql27";
	var mySql27=new SqlClass();
	mySql27.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql27.setSqlId(sqlid27);// ָ��ʹ�õ�Sql��id
	mySql27.addSubPara(tContNo);// ָ������Ĳ���
	arrButtonAndSQL[5][2]=mySql27.getString();	

  arrButtonAndSQL[6] = new Array;
  arrButtonAndSQL[6][0] = "button7";
  arrButtonAndSQL[6][1] = "�����������ϲ�ѯ";
 // arrButtonAndSQL[6][2] = "select 1 from lcpol a where a.polno in (select
	// i.polno from lcpol i where i.InsuredNo = (select appntno from lcappnt
	// where contno = '"+tContNo+"') or (i.appntno = (select appntno from
	// lcappnt where contno = '"+tContNo+"') and i.riskcode in ('00115000'))
	// union select polno from lcinsuredrelated where
	// lcinsuredrelated.customerno = (select appntno from lcappnt where contno =
	// '"+tContNo+"')) and a.uwflag not in ('1', '2', 'a') and not exists
	// (select 'X' from lccont where ContNo = a.contno and uwflag in ('1', '2',
	// 'a')) and rownum = 1";
 // arrButtonAndSQL[6][2] = "select * from lcrreport where contno='"+tContNo+"'";

  var sqlid28="UWAppSql28";
	var mySql28=new SqlClass();
	mySql28.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql28.setSqlId(sqlid28);// ָ��ʹ�õ�Sql��id
	mySql28.addSubPara(tContNo);// ָ������Ĳ���
	arrButtonAndSQL[6][2]=mySql28.getString();	
  
  arrButtonAndSQL[7] = new Array;
  arrButtonAndSQL[7][0] = "button8";
  arrButtonAndSQL[7][1] = "�����������ѯ";
  //arrButtonAndSQL[7][2] = "select * from lcissuepol where contno='"+tContNo+"'";

  var sqlid29="UWAppSql29";
	var mySql29=new SqlClass();
	mySql29.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql29.setSqlId(sqlid29);// ָ��ʹ�õ�Sql��id
	mySql29.addSubPara(tContNo);// ָ������Ĳ���
	arrButtonAndSQL[7][2]=mySql29.getString();	
  
  arrButtonAndSQL[8] = new Array;
  arrButtonAndSQL[8][0] = "button9";
  arrButtonAndSQL[8][1] = "�ٱ���ѯ";
  //arrButtonAndSQL[8][2] = "select * from LCReinsureReport where contno='"+tContNo+"'";
  
  var sqlid30="UWAppSql30";
	var mySql30=new SqlClass();
	mySql30.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql30.setSqlId(sqlid30);// ָ��ʹ�õ�Sql��id
	mySql30.addSubPara(tContNo);// ָ������Ĳ���
	arrButtonAndSQL[8][2]=mySql30.getString();	

  arrButtonAndSQL[9] = new Array;
  arrButtonAndSQL[9][0] = "button10";
  arrButtonAndSQL[9][1] = "�б����۱����ѯ";
  //arrButtonAndSQL[9][2] = "select 1 from dual where 1=2";
  
  var sqlid31="UWAppSql31";
	var mySql31=new SqlClass();
	mySql31.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql31.setSqlId(sqlid31);// ָ��ʹ�õ�Sql��id
	arrButtonAndSQL[9][2]=mySql31.getString();	

  arrButtonAndSQL[10] = new Array;
  arrButtonAndSQL[10][0] = "button11";
  arrButtonAndSQL[10][1] = "�����������ϲ�ѯ";
 // arrButtonAndSQL[10][2] = "select * from LLCase where customerno='"+tCustomerNo+"'";
  
  var sqlid32="UWAppSql32";
	var mySql32=new SqlClass();
	mySql32.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql32.setSqlId(sqlid32);// ָ��ʹ�õ�Sql��id
	mySql32.addSubPara(tCustomerNo);// ָ������Ĳ���
	arrButtonAndSQL[10][2]=mySql32.getString();	
  
// alert(tCustomerNo);
// prompt("",arrButtonAndSQL[10][2]);
  arrButtonAndSQL[11] = new Array;
  arrButtonAndSQL[11][0] = "button12";
  arrButtonAndSQL[11][1] = "������ȫ���ϲ�ѯ";
  //arrButtonAndSQL[11][2] = "select * from LPEdorMain a where a.contno ='"+tContNo+"'";

  var sqlid33="UWAppSql33";
	var mySql33=new SqlClass();
	mySql33.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql33.setSqlId(sqlid33);// ָ��ʹ�õ�Sql��id
	mySql33.addSubPara(tContNo);// ָ������Ĳ���
	arrButtonAndSQL[11][2]=mySql33.getString();	
  
  arrButtonAndSQL[12] = new Array;
  arrButtonAndSQL[12][0] = "button13";
  arrButtonAndSQL[12][1] = "�������Ѳ�ѯ";
 // arrButtonAndSQL[12][2] = "select * from ljtempfee where otherno='"+tContNo+"' and othernotype in('6','7','4') ";
  
  var sqlid34="UWAppSql34";
	var mySql34=new SqlClass();
	mySql34.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql34.setSqlId(sqlid34);// ָ��ʹ�õ�Sql��id
	mySql34.addSubPara(tContNo);// ָ������Ĳ���
	arrButtonAndSQL[12][2]=mySql34.getString();	
  
  arrButtonAndSQL[13] = new Array;
  arrButtonAndSQL[13][0] = "button14";
  arrButtonAndSQL[13][1] = "�ͻ������������ϲ�ѯ";
 // arrButtonAndSQL[13][2] = " select * from llsubreport b where  b.customerno='"+tCustomerNo+"'";
  
  var sqlid35="UWAppSql35";
	var mySql35=new SqlClass();
	mySql35.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql35.setSqlId(sqlid35);// ָ��ʹ�õ�Sql��id
	mySql35.addSubPara(tCustomerNo);// ָ������Ĳ���
	arrButtonAndSQL[13][2]=mySql35.getString();	

  arrButtonAndSQL[14] = new Array;
  arrButtonAndSQL[14][0] = "button15";
  arrButtonAndSQL[14][1] = "�ͻ�������ȫ���ϲ�ѯ";
 // arrButtonAndSQL[14][2] = "select * from LPEdorMain a where a.contno ='"+tContNo+"'";
  
  var sqlid36="UWAppSql36";
	var mySql36=new SqlClass();
	mySql36.setResourceName("uw.UWAppSql"); // ָ��ʹ�õ�properties�ļ���
	mySql36.setSqlId(sqlid36);// ָ��ʹ�õ�Sql��id
	mySql36.addSubPara(tContNo);// ָ������Ĳ���
	arrButtonAndSQL[14][2]=mySql36.getString();	
/**
 * //Ӱ�����ϲ�ѯ tSQL="select * from lcissuepol where contno='"+tContNo+"' and
 * rownum=1"; arrResult = easyExecSql(tSQL); if(arrResult!=null){
 * document.all("Button4").disabled=""; } else{
 * document.all("Button4").disabled="true"; }
 * 
 * //�������Ϣ��ѯ tSQL="select * from lcissuepol where contno='"+tContNo+"' and
 * rownum=1"; arrResult = easyExecSql(tSQL); if(arrResult!=null){
 * document.all("Button4").disabled=""; } else{
 * document.all("Button4").disabled="true"; }
 */

  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
    	// alert(arrButtonAndSQL[i][1]+":"+arrButtonAndSQL[i][2]);
      arrResult = easyExecSql(arrButtonAndSQL[i][2]);
      if(arrResult!=null){
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");
      }
      else{
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");
      }
    }
    else{
      continue;
    }
  }
}

function initButton()
{
	document.all('button1').disabled=true;
	document.all('button2').disabled=true;
	document.all('button3').disabled=true;
	document.all('button4').disabled=true;
	document.all('button5').disabled=true;
	document.all('button6').disabled=true;
	
	document.all('button7').disabled=true;
	document.all('button8').disabled=true;
	document.all('button9').disabled=true;
	document.all('button10').disabled=true;
	document.all('button11').disabled=true;
	document.all('button12').disabled=true;
	document.all('button13').disabled=true;
	document.all('button14').disabled=true;
	document.all('button15').disabled=true;
}

