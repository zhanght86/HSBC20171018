//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var ContNo;

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  //showSubmitFrame(mDebug);
  initPolGrid();
  document.getElementById("fm").submit();//�ύ
}

//�ύ�����水ť��Ӧ����
function printPol()
{
//  var i = 0;
//  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showSubmitFrame(mDebug);
/**
  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();
  //var testPol = PolGrid.getRowColData();
  //alert(tSel);
  if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	*/
//	   showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
		/*
		//arrReturn = getQueryResult();
		//ContNo=arrReturn[0][0];
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
	  tOldPrtSeq = PolGrid.getRowColData(tSel-1,10); 
		tPrtNo = PolGrid.getRowColData(tSel-1,2);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tNoticeType = PolGrid.getRowColData(tSel-1,4);
		tMissionID = PolGrid.getRowColData(tSel-1,8);
		tSubMissionID = PolGrid.getRowColData(tSel-1,9);
	
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.OldPrtSeq.value = tOldPrtSeq;
		fmSave.PrtNo.value = tPrtNo;
		fmSave.ContNo.value = tContNo ;
		fmSave.NoticeType.value = tNoticeType;
		fmSave.fmtransact.value = "PRINT";
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.target = "../f1print";
	  if(tNoticeType==89)
	    {
	   	  //alert(11111);
	   	  fmSave.action="./MeetF1PSave.jsp";
	   	}
	   	
	  	  else
	  	{
*/	  	

var count=0;
	for(var j=0;j<PolGrid.mulLineCount;j++){
		if(PolGrid.getChkNo(j)){
			count++;
		}	
	}
	if(count==0){
		alert("����ѡ��һ����¼���ٵ����ӡ��ť��");	
		return;
	}
	fmSave.fmtransact.value = "PRINT";
		fmSave.target = "../f1print";
	  	fmSave.action="./UWF1BatchPSave.jsp?ChkCount="+count;
//	  	} 	
		fmSave.submit();


	//}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	//{alert("111");
	return arrSelected;
	//}
	//alert("222");
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrDataSet[tRow-1];

	return arrSelected;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
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
  }
  else
  {

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //ִ����һ������
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

function returnParent()
{
    tContNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?ContNo="+tContNo;
}


// ��ѯ��ť
function easyQueryClick()
{
if(document.all('NoticeType').value == "" ){
		alert("��ѡ���֪ͨ�������ٽ��в�ѯ");
		return;
	}

	initPolGrid();
	// ��дSQL���
	//tongmeng 2007-11-12 modify
	//ͳһ��ӡ�˱�֪ͨ��
	var strSQL = "";
	// ��дSQL���
//	var ssql = "SELECT processid FROM LWCORRESPONDING where busitype='1001'";
	
	var sqlid0="UWNoticeBatchInputSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("uw.UWNoticeBatchInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	var ssql=mySql0.getString();
	
	var tProcessID = easyExecSql(ssql);
//	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4,"
//	        //+ " LWMission.MissionProp5 "
//	        + "a.code "
//	        + ",(select codename from ldcode where codetype='noticetype' and code=a.code) "
//	        + ",LWMission.MissionProp7 "
//	        + " ,c.salechnl,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp14 "
//	        + " FROM LWMission,loprtmanager a,lccont c "
////	        + " WHERE LWMission.ActivityID in ('0000001280','0000001017','0000001107','0000001302') "
////	        + "and LWMission.ProcessID = '0000000003'" //�б�������
//	        + " WHERE LWMission.ActivityID in (select activityid from lwactivity  where functionid in('10010007','10010026','10010050','10010058')) "
//	        + "and LWMission.ProcessID = '" + tProcessID + "' " //�б�������
//	        + " and c.prtno=a.otherno "
//	        + " and a.prtseq=LWMission.MissionProp3"
//	//tongmeng 2007-12-11 add
//	if(document.all('NoticeType').value!=null&&trim(document.all('NoticeType').value)!='')   
//	{
//		strSQL = strSQL + " and exists (select '1' from loprtmanager where prtseq=LWMission.MissionProp3 and code='"+trim(document.all('NoticeType').value)+"')"
//	}     
//	        
//	        
//	        strSQL = strSQL + getWherePart('LWMission.MissionProp1', 'PrtNo')
//	        + getWherePart('LWMission.MissionProp2', 'ContNo')
//			    + getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
//			    + getWherePart('LWMission.MissionProp4','AgentCode')
//			   // + getWherePart('LWMission.MissionProp5','NoticeType')
//			    + getWherePart('c.salechnl','SaleChnl');
//	//tongmeng 2007-10-30 add
//	//���Ӻ˱��½��ۺ�ϵͳֱ�ӷ��ŵ�֪ͨ��Ĵ�ӡ
//	var tSQL_b = " union "
//	           + "select a.prtseq,a.otherno,a.agentcode,a.code,(select codename from ldcode where codetype='noticetype' and code=a.code),a.managecom,(select salechnl from lccont where contno=a.otherno) "
//	           + ",'TBJB','1',a.prtseq from loprtmanager a " 
//                   + " where 1=1 and a.standbyflag7='TBJB' and a.stateflag='0' "
//	           + getWherePart('a.otherno', 'ContNo')
//     	           + getWherePart('a.managecom', 'ManageCom', 'like')
//	           + getWherePart('a.agentcode','AgentCode')
//	           + getWherePart('a.code','NoticeType')
//	if(document.all('SaleChnl').value!=null && trim(document.all('SaleChnl').value)!='')
//	{
//	   tSQL_b = tSQL_b + " and exists (select '1' from lccont where salechnl='"+trim(document.all('SaleChnl').value)+"')"
//	}	
//	strSQL = strSQL + tSQL_b;
	
	var  PrtNo1 = window.document.getElementsByName(trim("PrtNo"))[0].value;
	var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value;
	var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	var  SaleChnl1 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var  NoticeType1 = window.document.getElementsByName(trim("NoticeType"))[0].value;
	var sqlid1="UWNoticeBatchInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWNoticeBatchInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tProcessID);//ָ������Ĳ���
	mySql1.addSubPara(trim(document.all('NoticeType').value));//ָ������Ĳ���
	mySql1.addSubPara(PrtNo1);//ָ������Ĳ���
	mySql1.addSubPara(ContNo1);//ָ������Ĳ���
	mySql1.addSubPara(ManageCom1);//ָ������Ĳ���
	mySql1.addSubPara(AgentCode1);//ָ������Ĳ���
	mySql1.addSubPara(SaleChnl1);//ָ������Ĳ���
	mySql1.addSubPara(ContNo1);//ָ������Ĳ���
	mySql1.addSubPara(ManageCom1);//ָ������Ĳ���
	mySql1.addSubPara(AgentCode1);//ָ������Ĳ���
	mySql1.addSubPara(NoticeType1);//ָ������Ĳ���
	mySql1.addSubPara(trim(document.all('SaleChnl').value));//ָ������Ĳ���
	strSQL=mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);


  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�޴���ӡ�ĺ˱�֪ͨ�飡");
    return false;
    }
    turnPage.queryModal(strSQL, PolGrid);
////��ѯ�ɹ������ַ��������ض�ά����
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//
//  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
//  turnPage.pageDisplayGrid = PolGrid;
//
//  //����SQL���
//  turnPage.strQuerySql     = strSQL;
//
//  //���ò�ѯ��ʼλ��
//  turnPage.pageIndex       = 0;
//
//  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
// arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
// //tArr=chooseArray(arrDataSet,[0])
//  //����MULTILINE������ʾ��ѯ���
//
//  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
//  //displayMultiline(tArr, turnPage.pageDisplayGrid);
	fmSave.printButton.disabled = false;
}

function queryBranch()
{
  showInfo = window.open("../certify/AgentTrussQuery.html");
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{

  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
  }
}