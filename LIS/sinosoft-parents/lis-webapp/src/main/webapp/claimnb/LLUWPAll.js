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
 // initPolGrid();
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
//function printPol()
//{
//  var i = 0;
//  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
//  var arrReturn = new Array();
//  var tSel = PolGrid.getSelNo();
//  if( tSel == 0 || tSel == null ){
//		alert( "����ѡ��һ����¼" );
//	}
//	else
//	{
//		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
//	    tOldPrtSeq = PolGrid.getRowColData(tSel-1,9); 
//		tPrtNo = PolGrid.getRowColData(tSel-1,6);
//		tContNo = PolGrid.getRowColData(tSel-1,2);
//		tNoticeType = PolGrid.getRowColData(tSel-1,4);
//		
//		if(tNoticeType=="LP89"||tNoticeType=="LP81")
//		{
//			var strSQL = "";
//		    strSQL = "select missionid,submissionid from lwmission where "+
//		             "activityid = '0000001280' and missionprop2 = '"+tContNo+"'";
//		    arrResult = easyExecSql(strSQL);
//	        if (arrResult != null) {
//		        tMissionID = arrResult[0][0];
//		        tSubMissionID = arrResult[0][1];
//		        fmSave.MissionID.value = tMissionID;
//				fmSave.SubMissionID.value = tSubMissionID;
//      		}
//
//	    }
//	  	if(tNoticeType=="LP90"){
//	  		fmSave.MissionID.value = PolGrid.getRowColData(tSel-1,7);
//	  		fmSave.SubMissionID.value = PolGrid.getRowColData(tSel-1,8);
//	  		fmSave.ClmNo.value = PolGrid.getRowColData(tSel-1,10);
//	  		fmSave.BatNo.value = PolGrid.getRowColData(tSel-1,11);
//  			//alert("fmSave.MissionID.value = tMissionID;==  "+fmSave.MissionID.value);return false;
//	  		
//	  	}
//	  	fmSave.PrtSeq.value = tPrtSeq;
//	  	fmSave.OldPrtSeq.value = tOldPrtSeq;
//	  	fmSave.PrtNo.value = tPrtNo;
//	  	fmSave.ContNo.value = tContNo ;
//	  	fmSave.NoticeType.value = tNoticeType;
//	  	fmSave.fmtransact.value = "PRINT";
//	  	fmSave.target = "../f1print";
//		//alert("tNoticeType"+tNoticeType);
//		
//	  if(tNoticeType=="LP89")
//	    {
//	   	  fmSave.action="../uw/MeetF1PSave.jsp";
//	   	}
//	   	if(tNoticeType=="LP90"){
//	   		fmSave.action="../uw/LLUWF1PSave.jsp";
//	   	}
//	  //alert(fmSave.action);
//		fmSave.submit();
//		showInfo.close();
//
//
//	}
//}

//modify by lzf 20130603
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

	showInfo.focus();
  var arrReturn = new Array();
  var tSel = PublicWorkPoolGrid.getSelNo();
  if( tSel == 0 || tSel == null ){
		alert( "����ѡ��һ����¼" );
	}
	else
	{
		tPrtSeq = PublicWorkPoolGrid.getRowColData(tSel-1,1);
	    tOldPrtSeq = PublicWorkPoolGrid.getRowColData(tSel-1,12); 
		tPrtNo = PublicWorkPoolGrid.getRowColData(tSel-1,5);
		tContNo = PublicWorkPoolGrid.getRowColData(tSel-1,2);
		tNoticeType = PublicWorkPoolGrid.getRowColData(tSel-1,6);
		
		if(tNoticeType=="LP89"||tNoticeType=="LP81")
		{
			var strSQL = "";
//		    strSQL = "select missionid,submissionid from lwmission where "+
//		             "activityid = '0000001280' and missionprop2 = '"+tContNo+"'";
		    
			var sqlid0="LLUWPAllSql0";
			var mySql0=new SqlClass();
			mySql0.setResourceName("claimnb.LLUWPAllSql"); //ָ��ʹ�õ�properties�ļ���
			mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
			mySql0.addSubPara(tContNo);//ָ������Ĳ���
			strSQL=mySql0.getString();
		    
		    
		    arrResult = easyExecSql(strSQL);
	        if (arrResult != null) {
		        tMissionID = arrResult[0][0];
		        tSubMissionID = arrResult[0][1];
		        fm.MissionID.value = tMissionID;
				fm.SubMissionID.value = tSubMissionID;
      		}

	    }
	  	if(tNoticeType=="LP90"){
	  		fm.MissionID.value = PublicWorkPoolGrid.getRowColData(tSel-1,13);
	  		fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(tSel-1,14);
	  		fm.ClmNo.value = PublicWorkPoolGrid.getRowColData(tSel-1,7);
	  		fm.BatNo.value = PublicWorkPoolGrid.getRowColData(tSel-1,8);
  		//	alert("fm.MissionID.value = tMissionID;==  "+fm.MissionID.value);return false;
	  		
	  	}
	  	fm.PrtSeq.value = tPrtSeq;
	  	fm.OldPrtSeq.value = tOldPrtSeq;
	  	fm.PrtNo.value = tPrtNo;
	  	fm.ContNo.value = tContNo ;
	  	fm.NoticeType.value = tNoticeType;
	  	fm.fmtransact.value = "PRINT";
	  	fm.target = "../f1print";
		//alert("tNoticeType"+tNoticeType);
		
	  if(tNoticeType=="LP89")
	    {
		  fm.action="../uw/MeetF1PSave.jsp";
	   	}
	   	if(tNoticeType=="LP90"){
	   		fm.action="../uw/LLUWF1PSave.jsp";
	   	}
	  //alert(fmSave.action);
	   	document.getElementById("fm").submit();
		showInfo.close();


	}
}

//end

function getQueryResult()
{
	var arrSelected = null;
	tRow = PublicWorkPoolGrid.getSelNo();
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
	//if(document.all('NoticeType').value==null||document.all('NoticeType').value=="")
	//{
		//alert("��ѡ��֪ͨ�����ͣ�");
		//return;
		//}
	initPolGrid();
	var strSQL = "";
	// ��дSQL���
	if(document.all('NoticeType').value=="LP81"||document.all('NoticeType').value=="LP89"||document.all('NoticeType').value=="LP90")
	{
		strSQL = "select missionprop3,missionprop2,missionprop4,missionprop5,missionprop7,"
		        +"missionprop1,missionid,submissionid,missionprop14,MissionProp8,MissionProp9 from lwmission where "
		        +"activityid = '0000001280' or activityid='0000005551' and missionprop5 like 'LP%%'"
		        +getWherePart('missionprop5','NoticeType')
		        +getWherePart('missionprop2', 'ContNo')
		        +getWherePart('missionprop7', 'ManageCom', 'like');
		}
  else{
	strSQL = "SELECT prtseq,otherno,agentcode,code,managecom,otherno FROM loprtmanager WHERE stateflag = '0' and code like 'LP%%' "
	        + getWherePart('otherno', 'ContNo')
			+ getWherePart('managecom', 'ManageCom', 'like')
			+ getWherePart('code','NoticeType'); 
   }
	//prompt("strSQL",strSQL);
	turnPage.queryModal(strSQL, PolGrid);
	
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
  	fm.BranchGroup.value = arrResult[0][3];
  }
}