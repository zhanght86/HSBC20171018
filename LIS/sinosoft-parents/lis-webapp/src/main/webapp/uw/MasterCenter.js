//�������ƣ�MasterCenter.js
//�����ܣ���������
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var cflag = "4";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// ���ݷ��ظ�����
//function returnParent()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
//	else
//	{
//		try
//		{
//			arrReturn = getQueryResult();
//			top.opener.afterQuery( arrReturn );
//		}
//		catch(ex)
//		{
//			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
//		}
//		top.close();
//	}
//}

// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass(); 
var queryBug = 1;
//function initQuery() {
//	// ��ʼ�����
//	//initPolGrid();
//	 
//	// ��дSQL���
///*	var strSql = "select ProposalNo,PrtNo,RiskCode,AppntName,InsuredName from LCPol where " + ++queryBug + "=" + queryBug
//    				 + " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pi')=1 "
//    				 + " and AppFlag='0' "                 //���˱���
//    				 + " and GrpPolNo='" + grpPolNo + "'"  //���嵥�ţ�����Ϊ20��0
//    				 + " and ContNo='" + contNo + "'"      //���ݺţ�����Ϊ20��0
//    				 + " and ManageCom like '" + comcode + "%%'"
//    				 + " and uwflag <> '0'"
//    				 //+ " and uwflag = '7'"
//    				 + " and approveflag = '2'"
//    				 //+ " and polno in (select proposalno from lcuwmaster where printflag in ('1','2'))"
//    				 + " and PolNo in (select distinct proposalno from lcissuepol where ReplyResult is null and backobjtype = '4')";
//*/
//
//		 var sqlid1="MasterCenter1";
//		var mySql1=new SqlClass();
//		mySql1.setResourceName("uw.MasterCenterSql"); //ָ��ʹ�õ�properties�ļ���
//		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
////		mySql1.addSubPara(tContNo);//ָ������Ĳ���
//	    var strSql =mySql1.getString();	
//
////		var strSql = " select missionprop1,missionprop3,missionid,submissionid,missionprop4 "
////							 + " from lwmission where activityid='0000001020' and processid='0000000003' ";
////							 + " and defaultoperator is null";
//  //alert(strSql);
//	turnPage.queryModal(strSql, PolGrid);
//}

//var mSwitch = parent.VD.gVSwitch;
//function modifyClick() {
//  var i = 0;
//  var checkFlag = 0;
//  
//  for (i=0; i<PolGrid.mulLineCount; i++) {
//    if (PolGrid.getSelNo(i)) { 
//      checkFlag = PolGrid.getSelNo();
//      break;
//    }
//  }
//  
//  if (checkFlag) { 
//  	var cPolNo = PolGrid.getRowColData(checkFlag - 1, 1); 	
//  	mSwitch.deleteVar( "PolNo" );
//  	mSwitch.addVar( "PolNo", "", cPolNo );
//  	
//    urlStr = "./ProposalMain.jsp?LoadFlag=3";
//    window.open(urlStr,"",sFeatures);
//  }
//  else {
//    alert("����ѡ��һ��������Ϣ��"); 
//  }
//}

//function QuestQuery()
//{
//	  var i = 0;
//	  var checkFlag = 0;
//	  var cProposalNo = "";
//	  var cMissionId = "";
//	  var cSubMissionId = "";
//	  
//	  for (i=0; i<SelfPolGrid.mulLineCount; i++) {
//	    if (SelfPolGrid.getSelNo(i)) { 
//	      checkFlag = SelfPolGrid.getSelNo();
//	      break;
//	    }
//	  }
//	  //QuestInputMain.jsp?ContNo="+cProposalNo+"&Flag=5&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020
//	  if (checkFlag > 0) { 
//	  	cProposalNo = SelfPolGrid.getRowColData(checkFlag - 1, 1); 	
//	  	cMissionId  = SelfPolGrid.getRowColData(checkFlag - 1, 3); 	
//	  	cSubMissionId = SelfPolGrid.getRowColData(checkFlag - 1, 4); 	
//	  	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020&MasterCenter=Y","window1",sFeatures);
//	  }
//	  else {
//	    alert("����ѡ��һ��������Ϣ��"); 	    
//	  }
//	
//	
//	
//}

//����  ѯ����ť��������������ѯ���������Ĺ����ض���
/*function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	var strOperate="like";
	// ��дSQL���
	var strSql = "";
	var addStr = " and 1=1 ";               

		 var sqlid2="MasterCenter2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.MasterCenterSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.ComCode.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
//	    strSql=mySql2.getString();	


//	 strSql = " select missionprop1,missionprop3,missionid,submissionid,ActivityID "
//							 + " from lwmission where activityid='0000001020' and processid='0000000003' "
//							 + " and defaultoperator is null"
//							 + " and exists "
//						           +" (select 1 from lccont where ContNo=missionprop1 "
//						           + getWherePart('ManageCom','ComCode',strOperate)
//						           + getWherePart('ManageCom','ManageCom','like')
//						           +" )"		
//               +  getWherePart('MissionProp2','PrtNo');
       if (trim(fm.ManageCom.value) == "" || fm.ManageCom.value == null)
           addStr = addStr;
       else
				    addStr = " and exists "
				        +" (select 1 from lccont where ContNo=missionprop1 and ManageCom='"+fm.all('ManageCom').value+"')";		
	
	mySql2.addSubPara(addStr);//ָ������Ĳ���
	strSql=mySql2.getString();	
	turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("δ��ѯ���������������ݣ�");
    return false;
    }
//��ѯ�ɹ������ַ��������ض�ά����
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = arrDataSet;
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}
*/
////����  �롿��ť������������������ӹ��������뵽���˶���
//function ApplyUW()
//{   
//
//	var selno = PolGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("��ѡ��Ҫ�����Ͷ������");
//	      return;
//	}
//	fm.MissionID.value = PolGrid.getRowColData(selno, 3);
//	fm.SubMissionID.value = PolGrid.getRowColData(selno, 4);
//	fm.ActivityID.value = PolGrid.getRowColData(selno, 5);
//	fm.hiddenContNo.value =  PolGrid.getRowColData(selno, 1);
//	//alert("aaaaaaa--->"+PolGrid.getRowColData(selno, 5));
//	//09-06-06  ����У����������ѱ����뵽���˳��򷵻ش�����ʾ ˢ�½���
//	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
//					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000001020'"
//					+ " and defaultoperator is not null";
//	var tOperator = easyExecSql(tOperatorSql);
//	if(tOperator){
//		alert("�����ѱ�������Ա���뵽���˹����أ�");
//		easyQueryClickSelf();		
//		easyQueryClick();
//		return false;
//	}
//	var i = 0;
//	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//	lockScreen('lkscreen');  
//	fm.action = "MasterCenterChk.jsp";
//	document.getElementById("fm").submit(); //�ύ
//}
//
//
//
///*********************************************************************
// *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
// *  ����  ��  ��
// *  ����ֵ��  ��
// *********************************************************************
// */
//function afterSubmit( FlagStr, content )
//{
//	unlockScreen('lkscreen');  
//	showInfo.close();
//	
//	//���ӡˢ�ŵ�����
//  var prtNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 2);
//  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+prtNo+"&CreatePos=�б�����&PolState=1003&Action=DELETE";
//  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
//	
//	if (FlagStr == "Fail" )
//	{             
//		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//		return;
//	}
//  // ˢ�²�ѯ���
//	easyQueryClickSelf();		
//	easyQueryClick();
//	//���´���
//		  var	cProposalNo = fm.hiddenContNo.value;
//	  	var cMissionId  = fm.MissionID.value; 	
//	  	var cSubMissionId = fm.SubMissionID.value; 	
//	  	var ActivityID = fm.ActivityID.value;
//	  	
//	  	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID="+ActivityID+"&MasterCenter=Y","window1",sFeatures);
//}


//modify by lzf

var mSwitch = parent.VD.gVSwitch;
function modifyClick() {
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<ManPublicPoolGrid.mulLineCount; i++) {
    if (ManPublicPoolGrid.getSelNo(i)) { 
      checkFlag = ManPublicPoolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	var cPolNo = ManPublicPoolGrid.getRowColData(checkFlag - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  	
    urlStr = "./ProposalMain.jsp?LoadFlag=3";
    window.open(urlStr,"",sFeatures);
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
}

function QuestQuery()
{
	  var i = 0;
	  var checkFlag = 0;
	  var cProposalNo = "";
	  var cMissionId = "";
	  var cSubMissionId = "";
	  var cActivityId = "";
	  
	  for (i=0; i<ManPrivatePoolGrid.mulLineCount; i++) {
	    if (ManPrivatePoolGrid.getSelNo(i)) { 
	      checkFlag = ManPrivatePoolGrid.getSelNo();
	      break;
	    }
	  }	 
	  //QuestInputMain.jsp?ContNo="+cProposalNo+"&Flag=5&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020
	  if (checkFlag > 0) { 
	  	cProposalNo = ManPrivatePoolGrid.getRowColData(checkFlag - 1, 1); 	
	  	cMissionId  = ManPrivatePoolGrid.getRowColData(checkFlag - 1, 5); 	
	  	cSubMissionId = ManPrivatePoolGrid.getRowColData(checkFlag - 1, 6); 
	  	cActivityId = ManPrivatePoolGrid.getRowColData(checkFlag - 1, 8);
	  	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID="+cActivityId+"&MasterCenter=Y","window1",sFeatures);
	  }
	  else {
	    alert("����ѡ��һ��������Ϣ��"); 	    
	  }
	
	
	
}
//���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = ManPublicPoolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		top.close();
	}
}

//����  �롿��ť������������������ӹ��������뵽���˶���
function ApplyUW()
{   

	var selno = ManPublicPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}	
	fm.MissionID.value = ManPublicPoolGrid.getRowColData(selno, 5);
	fm.SubMissionID.value = ManPublicPoolGrid.getRowColData(selno, 6);
	fm.ActivityID.value = ManPublicPoolGrid.getRowColData(selno, 8);
	fm.hiddenContNo.value =  ManPublicPoolGrid.getRowColData(selno, 1);
	//alert("aaaaaaa--->"+PolGrid.getRowColData(selno, 5));
	//09-06-06  ����У����������ѱ����뵽���˳��򷵻ش�����ʾ ˢ�½���
//	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
//					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid in (select a.activityid from lwactivity a where a.functionid = '10010010')"
//					+ " and defaultoperator is not null";
	
	var  sqlid1="MasterCenter4";
 	var  mySql1=new SqlClass();
 	mySql1.setResourceName("uw.MasterCenterSql"); //ָ��ʹ�õ�properties�ļ���
 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
 	mySql1.addSubPara(fm.MissionID.value);//ָ������Ĳ���
 	mySql1.addSubPara(fm.SubMissionID.value);//ָ������Ĳ���
 	var tOperatorSql=mySql1.getString();
	var tOperator = easyExecSql(tOperatorSql);
	if(tOperator){
		alert("�����ѱ�������Ա���뵽���˹����أ�");
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
		return false;
	}
	var i = 0;
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

	lockScreen('lkscreen');  
	fm.action = "MasterCenterChk.jsp";
	document.getElementById("fm").submit(); //�ύ
}



/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
	
	//���ӡˢ�ŵ�����
  var prtNo = ManPublicPoolGrid.getRowColData(ManPublicPoolGrid.getSelNo()-1, 2);
  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+prtNo+"&CreatePos=�б�����&PolState=1003&Action=DELETE";
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		return;
	}
  // ˢ�²�ѯ���
	jQuery("#privateSearch").click();
	jQuery("#publicSearch").click();
	//���´���
		  var	cProposalNo = fm.hiddenContNo.value;
	  	var cMissionId  = fm.MissionID.value; 	
	  	var cSubMissionId = fm.SubMissionID.value; 	
	  	var ActivityID = fm.ActivityID.value;
	  	
	  	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID="+ActivityID+"&MasterCenter=Y","window1",sFeatures);
}
