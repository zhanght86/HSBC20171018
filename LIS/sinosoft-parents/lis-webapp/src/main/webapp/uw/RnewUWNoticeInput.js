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
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
function printPol()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showSubmitFrame(mDebug);

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();
  //var testPol = PolGrid.getRowColData();
  //alert(tSel);
  if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();		
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
	  	
	  	fmSave.action="./RnewUWF1PSave.jsp";
	  	} 	
		fmSave.submit();
		showInfo.close();


	}
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

	initPolGrid();
	// ��дSQL���
	//tongmeng 2007-11-12 modify
	//ͳһ��ӡ�˱�֪ͨ��
	var strSQL = "";
	var strSql = "";
	var sqlid1="RnewUWNoticeInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.RnewUWNoticeInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	// ��дSQL���
//	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4,"
//	        //+ " LWMission.MissionProp5 "
//	        + "a.code "
//	        + ",'�����˱�֪ͨ��' "
//	        + ",LWMission.MissionProp7 "
//	        + " ,c.salechnl,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp14 "
//	        + " FROM LWMission,loprtmanager a,lccont c "
//	        + " WHERE LWMission.ActivityID in (select activityid from lwactivity where functionId = '10047004') "
//	        + "and exists (select 1 from Lwprocess a  where  a.Processid=LWMission.Processid and a.Busitype='1004')" //�б�������
//	        + " and c.contno=a.otherno "
//	        + " and a.prtseq=LWMission.MissionProp3"
	//tongmeng 2007-12-11 add
	if(document.all('NoticeType').value!=null&&trim(document.all('NoticeType').value)!='')   
	{
		strSql = " and exists (select '1' from loprtmanager where prtseq=LWMission.MissionProp3 and code='"+trim(document.all('NoticeType').value)+"')";
	}     
//	        strSQL = strSQL + getWherePart('LWMission.MissionProp1', 'PrtNo')
//	        + getWherePart('LWMission.MissionProp2', 'ContNo')
//			    + getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
//			    + getWherePart('LWMission.MissionProp4','AgentCode')
//			   // + getWherePart('LWMission.MissionProp5','NoticeType')
//			    + getWherePart('c.salechnl','SaleChnl');
	//tongmeng 2007-10-30 add
	//���Ӻ˱��½��ۺ�ϵͳֱ�ӷ��ŵ�֪ͨ��Ĵ�ӡ
//	var tSQL_b = " union "
//	           + "select a.prtseq,a.otherno,a.agentcode,a.code,'�����˱�֪ͨ��',a.managecom,(select salechnl from lccont where contno=a.otherno) "
//	           + ",'TBJB','1',a.prtseq from loprtmanager a " 
//                   + " where 1=1 and a.standbyflag7='TBJB' and a.stateflag='0' "
//	           + getWherePart('a.otherno', 'ContNo')
//     	           + getWherePart('a.managecom', 'ManageCom', 'like')
//	           + getWherePart('a.agentcode','AgentCode')
//	           + getWherePart('a.code','NoticeType')
	var tSql_b = "";
	if(document.all('SaleChnl').value!=null && trim(document.all('SaleChnl').value)!='')
	{
	   tSql_b = " and exists (select '1' from lccont where salechnl='"+trim(document.all('SaleChnl').value)+"')"
	}	
//	strSQL = strSQL + tSQL_b;
	
	mySql1.addSubPara(strSql);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("PrtNo")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("ContNo")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("ManageCom")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("AgentCode")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("SaleChnl")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("ContNo")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("ManageCom")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("AgentCode")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName("NoticeType")[0].value);//ָ������Ĳ���
	mySql1.addSubPara(tSql_b);//ָ������Ĳ���
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