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
var cflag = "1" //����λ�á�1.����Ͷ����Ϣ��ѯ
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.UWAppFamilySql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
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

  //showSubmitFrame(mDebug);
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
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
    //ִ����һ������
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

  cProposalNo=fm.ProposalNoHide.value;
  cInsureNo = fm.InsuredNoHide.value;
  
  if (cProposalNo != "")
  {
  	//showModalDialog("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	window.open("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,"window2",sFeatures);
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
  var tSelNo = PolGrid.getSelNo();

	if( tSelNo == 0 || tSelNo == null )
	{
		alert( "����ѡ��һ����¼��" );
		return;
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
  
	var cProposalNo = PolGrid.getRowColData(tSelNo - 1,1);	
  //cProposalNo=fm.ProposalNoHide.value;
  //showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
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

//��ǰ�˱���¼
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

	var cProposalNo = PolGrid.getRowColData(tSelNo - 1,1);	
  //cProposalNo=fm.ProposalNoHide.value;
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
      

//������ϸ��Ϣ
function showPolDetail()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
  	window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+prtNo,"",sFeatures);
  	//showInfo.close();
  
  }
  else
  {  	
  	alert("����ѡ�񱣵�!");	
  }

}

//������ϲ�ѯ
function showHealthQ()
{
  var cContNo = fm.all('ContNoHide').value;	
  var tSelNo = PolGrid.getSelNo();
  var cPrtNo = PolGrid.getRowColData(tSelNo - 1,3);
	if( tSelNo == 0 || tSelNo == null )
	{
		alert( "����ѡ��һ����¼��" );
		return;
	}
	alert(2);
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

  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo,"window2",sFeatures);
  	showInfo.close();

}           

//�˱�������
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
  
	var cProposalNo = PolGrid.getRowColData(tSelNo - 1,1);	
  //cProposalNo=fm.ProposalNoHide.value;
  if (cProposalNo != "")
  {
  	//window.open("./UWManuReportMain.jsp?ProposalNo1="+cProposalNo+"&flag="+cflag,"window2");  	
  	window.open("UWQueryOldSubReportMain.jsp?PolNo="+cProposalNo,"window2",sFeatures);
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

//ѡ��Ҫ�˹��˱�����
function queryUWSub()
{
	//showSubmitFrame(mDebug);
	fm.submit();
}

// ��ѯ��ť
function easyQueryClick()
{
	// ��дSQL���
	//alert("test");
	var tSelNo = FamilyGrid.getSelNo();
  fm.all('InsureNoHide').value = FamilyGrid.getRowColData(tSelNo - 1,1);
	var strSQL = "";
	var i, j, m, n;
	var contno = fm.all('ContNoHide').value;
	var insureno = fm.all('InsureNoHide').value;
	
/*
	strSQL = "select LCPol.polno,LCPol.proposalno,LCPol.prtno,LCPol.cvalidate,LMRisk.riskname,LCPol.prem,LCPol.amnt,nvl(substr(LCPol.polstate,1,2),99),LCPol.OccupationType,LCUWMaster.healthflag,LCUWMaster.specflag,LCUWMaster.passflag ,LCPol.ClaimTimes from LCPol,LCUWMaster,LMRisk where 1=1 "
			 + " and LCPol.insuredno ='"+insureno+"'"
			 + " and LCPol.polno not in (select polno from lcpol where contno= '"+contno+"')"
			 + " and LCPol.polno = LCUWMaster.polno"
			 + " and LMRisk.riskcode = lcpol.riskcode"
			 + " and LCPol.uwcode is not null"
			 + " union"
			 + " select LCPol.polno,LCPol.proposalno,LCPol.prtno,LCPol.cvalidate,LMRisk.riskname,LCPol.prem,LCPol.amnt,nvl(substr(LCPol.polstate,1,2),99),LCPol.OccupationType,'0','0','0',LCPol.ClaimTimes from LCPol,LMRisk where 1=1 "
			 + " and LCPol.insuredno ='"+insureno+"'"
			 + " and LCPol.polno not in (select polno from lcpol where contno= '"+contno+"')"
			 + " and LCPol.uwcode is null"
			 + " and LMRisk.riskcode = lcpol.riskcode" 
			 + " order by 1";				 	 
*/
		var sqlid1="UWAppFamilySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(insureno);
		mySql1.addSubPara(contno);
		mySql1.addSubPara(insureno);
		mySql1.addSubPara(contno);

	turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�޼���Ͷ����Ϣ��");
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

// ��ѯ��ť
function easyQueryClickFamily()
{
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;
	var ContNo = fm.all('ContNoHide').value;
	/*
	strSQL = "select InsuredNo,Name,sex,Birthday,RelationToMainInsured from lcinsured where 1=1 "
		       + " and contno='"+ContNo+"'"
	
						;
			*/			
var sqlid2="UWAppFamilySql2";
		var mySql1=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(ContNo);
	turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�޼���Ͷ����Ϣ��");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = FamilyGrid;    
          
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

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	alert(1);
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
		alert("result:"+arrResult);
	} // end of if
}

function ChoClick(parm1,parm2)
{
	if(fm.all(parm1).all('InpPolGridSel').value == '1' )
	{
	//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
   		fm.ProposalNoHide.value = fm.all(parm1).all('PolGrid2').value;
  	}

}



