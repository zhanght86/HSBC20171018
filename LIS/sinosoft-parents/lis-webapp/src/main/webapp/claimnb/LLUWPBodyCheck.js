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

	showInfo.focus();
  //showSubmitFrame(mDebug);
  initPolGrid();
  document.getElementById("fm").submit(); //�ύ
}

////�ύ�����水ť��Ӧ����
//function printPol()
//{
//  var i = 0;
//  //showSubmitFrame(mDebug);
//
//  var arrReturn = new Array();
//  var tSel = PolGrid.getSelNo();
//
//  if( tSel == 0 || tSel == null )
//		alert( "����ѡ��һ����¼���ٵ����ӡ֪ͨ�鰴ť��" );
//	else
//	{
//		var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//		showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
//		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
//		tMissionID = PolGrid.getRowColData(tSel-1,8);
//		tSubMissionID = PolGrid.getRowColData(tSel-1,9);
//		tPrtNo = PolGrid.getRowColData(tSel-1,7);
//		tContNo = PolGrid.getRowColData(tSel-1,2);
//		tActivityID =
//		//alert(ContNo);
//		fmSave.PrtSeq.value = tPrtSeq;
//		fmSave.MissionID.value = tMissionID;
//		fmSave.SubMissionID.value = tSubMissionID;
//		fmSave.ContNo.value = tContNo ;
//		fmSave.PrtNo.value = tPrtNo ;
//		fmSave.PrtNo.value = tActivityID ;
//		fmSave.fmtransact.value = "PRINT";
//		fmSave.target = "../f1print";
//		fmSave.submit();
//		showInfo.close();
//	}
//}

//�ύ�����水ť��Ӧ���� lzf 20130531
function printPol()
{
  var i = 0;
  //showSubmitFrame(mDebug);

  var arrReturn = new Array();
  var tSel = PublicWorkPoolGrid.getSelNo();

  if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ����ӡ֪ͨ�鰴ť��" );
	else
	{
		var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		tPrtSeq = PublicWorkPoolGrid.getRowColData(tSel-1,1);
		tMissionID = PublicWorkPoolGrid.getRowColData(tSel-1,11);
		tSubMissionID = PublicWorkPoolGrid.getRowColData(tSel-1,12);
		tPrtNo = PublicWorkPoolGrid.getRowColData(tSel-1,7);
		tContNo = PublicWorkPoolGrid.getRowColData(tSel-1,2);
		tActivityID =PublicWorkPoolGrid.getRowColData(tSel-1,14);
		//alert(tPrtSeq+" 2: "+tMissionID+" 3: "+tPrtNo+" 4: "+tContNo+" 5: "+tActivityID);return false;
		fm.PrtSeq.value = tPrtSeq;
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
		fm.ContNo.value = tContNo ;
		fm.PrtNo.value = tPrtNo ;
		fm.PrtNo.value = tActivityID ;
		fm.fmtransact.value = "PRINT";
		fm.target = "../f1print";
		document.getElementById("fm").submit();
		showInfo.close();
	}
}
function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	return arrSelected;
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
	var iHeight=350;     //�������ڵĸ߶�; 
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
/*function easyQueryClick()
{

	initPolGrid();
	// ��дSQL���
	var strSQL = "";
	// ��дSQL���
	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4, LWMission.MissionProp5,LWMission.MissionProp6,LWMission.MissionProp7 ,LWMission.MissionProp1,LWMission.MissionID ,LWMission.SubMissionID FROM LWMission WHERE LWMission.ActivityID = '0000005531' "  //ActivityID = '0000005531' �������
	        + "and LWMission.ProcessID in ('0000000005') " //���⹤����
	        + getWherePart('LWMission.MissionProp1', 'PrtNo') 
	        + getWherePart('LWMission.MissionProp2', 'ContNo')
			//+ getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
			+ getWherePart('LWMission.MissionProp4','AgentCode')
			+ getWherePart('LWMission.MissionProp5','AgentGroup')
//			+ getWherePart('LWMission.MissionProp6','BranchGroup');

//	alert(strSQL);

	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
	  alert("û��Ҫ��ӡ���������֪ͨ�飡");
	  return false;
	}
//��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
 arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 //tArr=chooseArray(arrDataSet,[0])
  //����MULTILINE������ʾ��ѯ���

  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
}
*/
function queryBranch()
{
  showInfo = window.open("../certify/AgentTrussQuery.html");
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{

  if(arrResult!=null)
  {
//  	fm.BranchGroup.value = arrResult[0][3];
  }
}