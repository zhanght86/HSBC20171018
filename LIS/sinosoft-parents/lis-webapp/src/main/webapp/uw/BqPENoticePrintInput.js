
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
  fm.submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
function printPol()
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

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();

  if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
		tMissionID = PolGrid.getRowColData(tSel-1,8);
		tSubMissionID = PolGrid.getRowColData(tSel-1,9);
		tActivityID = PolGrid.getRowColData(tSel-1,10);
		tPrtNo = PolGrid.getRowColData(tSel-1,7);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.ActivityID.value = tActivityID;
		fmSave.ContNo.value = tContNo ;
		fmSave.PrtNo.value = tPrtNo ;
		fmSave.fmtransact.value = "PRINT";
		fmSave.target = "../f1print";
		fmSave.submit();
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
function easyQueryClick()
{

	initPolGrid();
	// ��дSQL���
	var strSQL = "";
	// ��дSQL���
//	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4, LWMission.MissionProp5,LWMission.MissionProp6,LWMission.MissionProp7 ,LWMission.MissionProp1,LWMission.MissionID ,LWMission.SubMissionID,LWMission.ActivityID FROM LWMission WHERE LWMission.ActivityID in(select p.activityid from lwactivity p where p.FunctionID='"+fmSave.FunctionID.value+"') "  //ActivityID = '0000001106' ��֤����Ϊ�б����
//	        + " and exists (select 1 from loprtmanager where LWMission.MissionProp3 = prtseq and code = '23') " //��ȫ������
//	        + getWherePart('LWMission.MissionProp1', 'PrtNo')
//	        + getWherePart('LWMission.MissionProp2', 'ContNo')
//			+ getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
//			+ getWherePart('LWMission.MissionProp4','AgentCode')
//			+ getWherePart('LWMission.MissionProp5','AgentGroup')
//			+ getWherePart('LWMission.MissionProp6','BranchGroup');
//
////	alert(strSQL);
//	
	 var  FunctionID1 = fmSave.FunctionID.value;
	 var  PrtNo1 = window.document.getElementsByName(trim("PrtNo"))[0].value;
	 var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value;
	 var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	 var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	 var  AgentGroup1 = window.document.getElementsByName(trim("AgentGroup"))[0].value;
	 var  BranchGroup1 = window.document.getElementsByName(trim("BranchGroup"))[0].value;
	 var sqlid1="BqPENoticePrintInputSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.BqPENoticePrintInputSql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(FunctionID1);//ָ���������
	 mySql1.addSubPara(PrtNo1);//ָ���������
	 mySql1.addSubPara(ContNo1);//ָ���������
	 mySql1.addSubPara(ManageCom1);//ָ���������
	 mySql1.addSubPara(AgentCode1);//ָ���������
	 mySql1.addSubPara(AgentGroup1);//ָ���������
	 mySql1.addSubPara(BranchGroup1);//ָ���������
	 strSQL = mySql1.getString();

	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) 
  {
//      strSQL = "SELECT a.MissionProp3, a.MissionProp2,a.MissionProp4, a.MissionProp5,a.MissionProp6,a.MissionProp7 ,a.MissionProp1,a.MissionID ,a.SubMissionID,a.ActivityID FROM LWMission a WHERE a.ActivityID in(select activityid from lwactivity where functionid='"+fmSave.FunctionID.value+"') "  //ActivityID = '0000001106' ��֤����Ϊ�б����
//	        + "and  exists (select 'X' from loprtmanager where code = '23' and prtseq = a.missionprop3) " //��ȫ������
//	        + " and exists(select 1 from lwmission where missionid = a.missionid and activityid in(select activityid from lwactivity where functionid='10020004') and missionprop7 like '" + fm.ManageCom.value + "')"
//	        + getWherePart('a.MissionProp1', 'PrtNo')
//	        + getWherePart('a.MissionProp2', 'ContNo')
//			+ getWherePart('a.MissionProp4','AgentCode')
//			+ getWherePart('a.MissionProp5','AgentGroup')
//			+ getWherePart('a.MissionProp6','BranchGroup');
      
     var  FunctionID2 = fmSave.FunctionID.value;
     var  ManageCom2 = fm.ManageCom.value;
 	 var  PrtNo2 = window.document.getElementsByName(trim("PrtNo"))[0].value;
 	 var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
 	 var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
 	 var  AgentGroup2 = window.document.getElementsByName(trim("AgentGroup"))[0].value;
 	 var  BranchGroup2 = window.document.getElementsByName(trim("BranchGroup"))[0].value;
 	 var sqlid2="BqPENoticePrintInputSql2";
 	 var mySql2=new SqlClass();
 	 mySql2.setResourceName("uw.BqPENoticePrintInputSql");
 	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
 	 mySql2.addSubPara(FunctionID2);//ָ���������
 	 mySql2.addSubPara(ManageCom2);//ָ���������
 	 mySql2.addSubPara(PrtNo2);//ָ���������
 	 mySql2.addSubPara(ContNo2);//ָ���������
 	 mySql2.addSubPara(AgentCode2);//ָ���������
 	 mySql2.addSubPara(AgentGroup2);//ָ���������
 	 mySql2.addSubPara(BranchGroup2);//ָ���������
 	 strSQL = mySql2.getString();
      
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
	  if (!turnPage.strQueryResult)
	  {
          alert("û��Ҫ��ӡ�ı�ȫ���֪ͨ�飡");
          return false;
      }
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