//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();



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
	//initPolGrid();
  lockScreen('lkscreen');  
  //showSubmitFrame("1");
  fmSave.submit(); //�ύ
}



//�ύ�����水ť��Ӧ����
function printPol()
{
  var tRow = PolGrid.getSelNo();	
  if( tRow == 0 || tRow == null )
		alert( "����ѡ��һ����¼���ٵ������ť��" );
	else
	{
		fmSave.PrtSeq.value = PolGrid.getRowColData(tRow-1,1);
		fmSave.Code.value = PolGrid.getRowColData(tRow-1,2);
		fmSave.ContNo.value = PolGrid.getRowColData(tRow-1,3);
		fmSave.MissionID.value = PolGrid.getRowColData(tRow-1,7);
		fmSave.SubMissionID.value = PolGrid.getRowColData(tRow-1,8);
		fmSave.PrtNo.value = PolGrid.getRowColData(tRow-1,6);
		fmSave.ActivityID.value = PolGrid.getRowColData(tRow-1,9);
		fmSave.fmtransact.value = "CONFIRM";
		submitForm();
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
	//arrSelected[0] = arrDataSet[tRow-1];
	arrSelected[0]=new Array();
	arrSelected[0][0]=PolGrid.getRowColData(tRow-1,1);
	arrSelected[0][1]=PolGrid.getRowColData(tRow-1,3);
	arrSelected[0][1]
	return arrSelected;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");       	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  
  easyQueryClick();
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
	 //��ʼ�����
	initPolGrid();
	
		 //��дSQL���
	var strSQL = "";
	var len = tmanageCom.length;
	//tongmeng 2007-11-29 modify
	//���Ӻ˱�֪ͨ��(�Ǵ�ӡ��)����
//	strSQL = " select LWMission.MissionProp14,LWMission.MissionProp4,LWMission.MissionProp2,LWMission.MissionProp5,LWMission.MissionProp6,LWMission.MissionProp1,LWMission.MissionID,LWMission.SubMissionID,activityid from LWMission where 1=1"
//		//+ " and LWMission.ProcessID in ('0000000003','0000000004')"
//		//+ " and LWMission.ActivityID in ('0000001114','0000001024','0000001115','0000001116','0000001018','0000001290','0000002210','0000002114','0000002314','0000001210','0000001240','0000002240','0000001301')"
//		+ " and lwmission.activityid in (select lwactivity.activityid from lwactivity where functionid in ('10010008','10010051','10010059','10010032','10010033','10010043','10010046','10010034','20010019','20010014')) "
//		+ " and LWMission.MissionProp5 like '"+tmanageCom+"%%'"
//		//+ " and Substr(LWMission.MissionProp5,1,"+len+") = '"+tmanageCom+"'"
//		+ getWherePart('LWMission.MissionProp7','StartDay','>=')
//		+ getWherePart('LWMission.MissionProp7','EndDay','<=')
//		+ getWherePart('LWMission.MissionProp2','OtherNo','like') 
//	    + getWherePart('LWMission.MissionProp6','AgentCode') 
//	    + getWherePart('LWMission.MissionProp3','PrtSeq') 
//	    + getWherePart('LWMission.MissionProp4','Code')
//        + " union "
//        + "  select a.prtseq,a.code,a.otherno,a.managecom,a.agentcode,a.otherno,'TBJB','1','' "
//	      + " from loprtmanager a "
//	      + " where 1 = 1 "
//		    + " and a.standbyflag7 = 'TBJB' "
//		    + " and a.stateflag in ( '1','3') "
//		    //+ " and (patchflag is null or patchflag<>'1')"
//		    + " and a.managecom like '"+tmanageCom+"%%' "
//		    + getWherePart('a.DoneDate','StartDay','>=')
//			+ getWherePart('a.DoneDate','EndDay','<=')
//			+ getWherePart('a.otherno','OtherNo','like') 
//	    + getWherePart('a.agentcode','AgentCode') 
//	    + getWherePart('a.prtseq','PrtSeq') 
//	    + getWherePart('a.code','Code')
//	    + " union "
//	    + "  select a.prtseq,a.code,a.otherno,a.managecom,a.agentcode,a.otherno,'TBJB','1','' "
//	      + " from loprtmanager a "
//	      + " where 1 = 1 "
//		    + " and a.code in ('08') "
//		    + " and a.stateflag in ( '1','3') "
//		    //+ " and (patchflag is null or patchflag<>'1')"
//		    + " and a.managecom like '"+tmanageCom+"%%' "
//		    + getWherePart('a.DoneDate','StartDay','>=')
//			+ getWherePart('a.DoneDate','EndDay','<=')
//			+ getWherePart('a.otherno','OtherNo','like') 
//	    + getWherePart('a.agentcode','AgentCode') 
//	    + getWherePart('a.prtseq','PrtSeq') 
//	    + getWherePart('a.code','Code')
//	    
//	    + " union "
//	    + "  select a.prtseq,a.code,a.otherno,a.managecom,a.agentcode,a.otherno,'TBJB','1','' "
//	    + " from loprtmanager a "
//	    + " where 1 = 1 "
//		+ " and a.code in ('21') "
//		+ " and a.stateflag in ( '1','3') "
//		+ " and not exists(select 1 from ljtempfee where otherno = a.otherno and enteraccdate is not null) "
//		+ " and not exists(select 1 from lccont where prtno = a.otherno and (appflag = '1' or uwflag = 'a')) "
//		+ " and a.managecom like '"+tmanageCom+"%%' "
//		+ getWherePart('a.DoneDate','StartDay','>=')
//		+ getWherePart('a.DoneDate','EndDay','<=')
//		+ getWherePart('a.otherno','OtherNo','like') 
//	    + getWherePart('a.agentcode','AgentCode') 
//	    + getWherePart('a.prtseq','PrtSeq') 
//	    + getWherePart('a.code','Code')
//
//	  ;
	
	 var  StartDay = window.document.getElementsByName(trim("StartDate"))[0].value;
     var  EndDay = window.document.getElementsByName(trim("EndDate"))[0].value;
     var  OtherNo = window.document.getElementsByName(trim("OtherNo"))[0].value;
     var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
     var  PrtSeq = window.document.getElementsByName(trim("PrtSeq"))[0].value;
     var  Code = window.document.getElementsByName(trim("Code"))[0].value;
 	   var  sqlid1="RePrintSql0";
 	   var  mySql1=new SqlClass();
 	   mySql1.setResourceName("uw.RePrintSql"); //ָ��ʹ�õ�properties�ļ���
 	   mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
 	   mySql1.addSubPara(tmanageCom);//ָ������Ĳ���
 	   mySql1.addSubPara(StartDay);//ָ������Ĳ���
 	   mySql1.addSubPara(EndDay);//ָ������Ĳ���
 	   mySql1.addSubPara(OtherNo);//ָ������Ĳ���
 	   mySql1.addSubPara(AgentCode);//ָ������Ĳ���
 	   mySql1.addSubPara(PrtSeq);//ָ������Ĳ���
	   mySql1.addSubPara(Code);//ָ������Ĳ���
	   mySql1.addSubPara(tmanageCom);//ָ������Ĳ���
 	   mySql1.addSubPara(StartDay);//ָ������Ĳ���
 	   mySql1.addSubPara(EndDay);//ָ������Ĳ���
 	   mySql1.addSubPara(OtherNo);//ָ������Ĳ���
 	   mySql1.addSubPara(AgentCode);//ָ������Ĳ���
 	   mySql1.addSubPara(PrtSeq);//ָ������Ĳ���
	   mySql1.addSubPara(Code);//ָ������Ĳ���
	   mySql1.addSubPara(tmanageCom);//ָ������Ĳ���
 	   mySql1.addSubPara(StartDay);//ָ������Ĳ���
 	   mySql1.addSubPara(EndDay);//ָ������Ĳ���
 	   mySql1.addSubPara(OtherNo);//ָ������Ĳ���
 	   mySql1.addSubPara(AgentCode);//ָ������Ĳ���
 	   mySql1.addSubPara(PrtSeq);//ָ������Ĳ���
	   mySql1.addSubPara(Code);//ָ������Ĳ���
	   mySql1.addSubPara(tmanageCom);//ָ������Ĳ���
 	   mySql1.addSubPara(StartDay);//ָ������Ĳ���
 	   mySql1.addSubPara(EndDay);//ָ������Ĳ���
 	   mySql1.addSubPara(OtherNo);//ָ������Ĳ���
 	   mySql1.addSubPara(AgentCode);//ָ������Ĳ���
 	   mySql1.addSubPara(PrtSeq);//ָ������Ĳ���
	   mySql1.addSubPara(Code);//ָ������Ĳ���
 	   strSQL=mySql1.getString();
	  //tongmeng 2009-06-03 modify
	  //�޸Ĳ�ѯ�߼�
	  turnPage.queryModal(strSQL,PolGrid);
	  if (!turnPage.strQueryResult) {
   alert("û��Ҫ�ύ����֪ͨ�����Ϣ��");
    return false;
  }
	 /* 
	turnPage.strQueryResult  = easyQueryVer3(strSQL);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
   alert("û��Ҫ�ύ����֪ͨ�����Ϣ��");
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
  //tArr = chooseArray(arrDataSet,[0,1,3,4]) 
  //����MULTILINE������ʾ��ѯ���
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  */
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
}
//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
}