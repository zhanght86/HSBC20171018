//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var manageCom;

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
  initPolGrid();
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
function printPol()
{
  var i = 0;

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();
  if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		PrtSeq = PolGrid.getRowColData(tSel-1,1);

		if( null == arrReturn ) {
			alert("��Ч������");
			return;
		}

		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
		tMissionID = PolGrid.getRowColData(tSel-1,6);
		tSubMissionID = PolGrid.getRowColData(tSel-1,7);
		tPrtNo = PolGrid.getRowColData(tSel-1,2);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tOldPrtSeq = PolGrid.getRowColData(tSel-1,8);
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.OldPrtSeq.value = tOldPrtSeq;
		fmSave.PrtNo.value = tPrtNo;
		fmSave.ContNo.value = tContNo ;
		fmSave.fmtransact.value = "PRINT";
		//fmSave.target = "../f1print";

		fmSave.submit();
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
  showInfo.close();
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

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	// ��дSQL���
	var strSQL = "";
	// ��дSQL���
//	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4,LWMission.MissionProp7 ,LWMission.MissionProp13,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp14 FROM LWMission WHERE LWMission.ActivityID = (select activityid from lwactivity where functionId = '10047010') "  //ActivityID = '0000001108' ��֤����Ϊ��������
//	        + "and (exists (select 1 from Lwprocess a  where  a.Processid=LWMission.Processid and a.Busitype='1004')) " //�������˹�����	       
//	        + getWherePart('LWMission.MissionProp2', 'ContNo')
//		    	+ getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
//			    + getWherePart('LWMission.MissionProp4','AgentCode')
//			    + getWherePart('LWMission.MissionProp13','SaleChnl')
//		      ;
	    var  ContNo = window.document.getElementsByName(trim("ContNo"))[0].value;
	    var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
        var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
        var  SaleChnl = window.document.getElementsByName(trim("SaleChnl"))[0].value;
        var  sqlid1="RnewMeetSql0";
	 	var  mySql1=new SqlClass();
	 	mySql1.setResourceName("uw.RnewMeetSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	 	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	 	mySql1.addSubPara(ManageCom);//ָ������Ĳ���
	 	mySql1.addSubPara(AgentCode);//ָ������Ĳ���
	 	mySql1.addSubPara(SaleChnl);//ָ������Ĳ���
	    var strSQL=mySql1.getString();

	turnPage.strQueryResult  = easyQueryVer3(strSQL);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û��Ҫ��ӡ�����֪ͨ�飡");
    return false;
  }
  
turnPage.queryModal(strSQL, PolGrid);

//  //��ѯ�ɹ������ַ��������ض�ά����
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
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
//  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
//  //tArr = chooseArray(arrDataSet,[0,1,3,4])
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