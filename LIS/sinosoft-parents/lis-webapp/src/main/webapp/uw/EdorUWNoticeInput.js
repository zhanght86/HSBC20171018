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
  //var testPol = PolGrid.getRowColData();
  //alert(tSel);
  if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		//arrReturn = getQueryResult();
		//ContNo=arrReturn[0][0];
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
	
		tPrtNo = PolGrid.getRowColData(tSel-1,6);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tNoticeType = PolGrid.getRowColData(tSel-1,4);
		tMissionID = PolGrid.getRowColData(tSel-1,8);
		tSubMissionID = PolGrid.getRowColData(tSel-1,9);
	  tActivityID = PolGrid.getRowColData(tSel-1,13);
		var tEdroNo = PolGrid.getRowColData(tSel-1,11);
		var tEdroType = PolGrid.getRowColData(tSel-1,12);
	
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
	
		fmSave.PrtNo.value = tPrtNo;
		fmSave.ContNo.value = tContNo ;
		fmSave.NoticeType.value = tNoticeType;
		fmSave.fmtransact.value = "PRINT";
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.ActivityID.value = tActivityID
		fmSave.EdorNo.value = tEdroNo;
		fmSave.EdorType.value = tEdroType;//alert("tEdroNo=="+fmSave.EdorNo.value+" tEdroType=="+fmSave.EdorType.value);
		fmSave.target = "../f1print";
	    if(tNoticeType=="BQ88")
	    {
	   	  fmSave.action="./EdorAskSave.jsp";
	   	}
	    else
	    {
	      fmSave.action="./EdorUWF1PSave.jsp";	
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
  var CodeType = fm.NoticeType.value;
	initPolGrid();
	// ��дSQL���

	var strSQL = "";
	// ��дSQL���
//	strSQL = "SELECT MissionProp3, MissionProp2,MissionProp4, MissionProp5,MissionProp7 ,MissionProp1,(SELECT salechnl FROM LCCONT WHERE CONTNO=MISSIONPROP2),MissionID ,SubMissionID ,'',(SELECT DISTINCT MAX(EDORNO) FROM LPEDORITEM WHERE CONTNO=MISSIONPROP2 AND EDORSTATE!='0'),(SELECT DISTINCT EDORTYPE FROM LPEDORITEM WHERE CONTNO =MISSIONPROP2 AND EDORSTATE!='0'),ACTIVITYID  FROM LWMission WHERE ActivityID in (Select activityid from lwactivity where functionid='10020320') "
//	        + " and trim(MissionProp5) in (select trim(code) from ldcode where codetype = 'bquwnotice')"//Add By QianLy on 2006-10-14
//	            + getWherePart('MissionProp1', 'PrtNo')
//	            + getWherePart('MissionProp2', 'ContNo')
//			    + getWherePart('MissionProp7', 'ManageCom', 'like')
//			    + getWherePart('MissionProp4','AgentCode')
//			    + getWherePart('MissionProp5','NoticeType');
		
	 var  PrtNo1 = window.document.getElementsByName(trim("PrtNo"))[0].value;
     var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value;
     var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
     var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
     var  NoticeType1 = window.document.getElementsByName(trim("NoticeType"))[0].value;
     var sqlid1="EdorUWNoticeInputSql1";
   	 var mySql1=new SqlClass();
   	 mySql1.setResourceName("uw.EdorUWNoticeInputSql");
   	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
   	 mySql1.addSubPara(PrtNo1);//ָ���������
   	 mySql1.addSubPara(ContNo1);//ָ���������
   	 mySql1.addSubPara(ManageCom1);//ָ���������
   	 mySql1.addSubPara(AgentCode1);//ָ���������
   	 mySql1.addSubPara(NoticeType1);//ָ���������
   	 strSQL = mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) 
  {
//      	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4,"
//	        //+ " LWMission.MissionProp5 "
//	        + "a.code "
//	        + ",(select codename from ldcode where codetype='bquwnotice' and code=a.code) "
//	        + ",LWMission.MissionProp7 "
//	        + " ,c.salechnl,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp14 "
//	        + " ,LWMission.MissionProp8,LWMission.MissionProp9,LWMission.ActivityID "
//	        + " FROM LWMission,loprtmanager a,lccont c "
//	        + " WHERE LWMission.ActivityID in (Select activityid from lwactivity where functionid in('10020320','10020351')) "
//	        + " and c.contno=a.otherno "
//	        + " and a.prtseq=LWMission.MissionProp3";
//	    strSQL = strSQL + getWherePart('LWMission.MissionProp1', 'PrtNo')
//	                    + getWherePart('LWMission.MissionProp2', 'ContNo')
//		                + getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
//		                + getWherePart('LWMission.MissionProp4','AgentCode')
//		 	            + getWherePart('LWMission.MissionProp5','NoticeType');
//		    //+ getWherePart('c.salechnl','SaleChnl');
//		var tSQL_b = " union "
//	           + "select a.prtseq,a.otherno,a.agentcode,a.code,(select codename from ldcode where codetype='bquwnotice' and code=a.code),a.managecom,(select salechnl from lccont where contno=a.otherno) "
//	           + ",'BQJB','1',a.prtseq,'','','' from loprtmanager a " 
//               + " where 1=1 and a.standbyflag7='BQJB' and a.stateflag='0' "
//               + " and exists (select 1 from lwmission where  ActivityID in(select activityid from lwactivity where functionid='10020007') and MissionProp2=a.otherno)"
//	           + getWherePart('a.otherno', 'ContNo')
//     	       + getWherePart('a.managecom', 'ManageCom', 'like')
//	           + getWherePart('a.agentcode','AgentCode')
//	           + getWherePart('a.code','NoticeType');
//	    //if(fm.NoticeType.value=="BQ84"){
//	    	strSQL=strSQL+tSQL_b;
//	    //}
	    
	     var  PrtNo2 = window.document.getElementsByName(trim("PrtNo"))[0].value;
	     var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
	     var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	     var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	     var  NoticeType2 = window.document.getElementsByName(trim("NoticeType"))[0].value;
	     
//	     var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value;
//	     var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
//	     var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
//	     var  NoticeType1 = window.document.getElementsByName(trim("NoticeType"))[0].value;
	     
	     var sqlid2="EdorUWNoticeInputSql2";
	   	 var mySql2=new SqlClass();
	   	 mySql2.setResourceName("uw.EdorUWNoticeInputSql");
	   	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	   	 mySql2.addSubPara(PrtNo2);//ָ���������
	   	 mySql2.addSubPara(ContNo2);//ָ���������
	   	 mySql2.addSubPara(ManageCom2);//ָ���������
	   	 mySql2.addSubPara(AgentCode2);//ָ���������
	   	 mySql2.addSubPara(NoticeType2);//ָ���������
	   	 mySql2.addSubPara(ContNo2);//ָ���������
	   	 mySql2.addSubPara(ManageCom2);//ָ���������
	   	 mySql2.addSubPara(AgentCode2);//ָ���������
	   	 mySql2.addSubPara(NoticeType2);//ָ���������
	   	 strSQL = mySql2.getString();
	    	
	    	
      turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
      if (!turnPage.strQueryResult)
      {
          alert("�޴���ӡ�ĺ˱�֪ͨ�飡");
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