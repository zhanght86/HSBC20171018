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
  //showSubmitFrame("1");
  document.getElementById("fm").submit(); //�ύ
}



//�ύ�����水ť��Ӧ����
function printPol()
{
   var tRow = PolGrid.getSelNo();	
   //alert(tRow);
  if( tRow == 0 || tRow == null )
		alert( "����ѡ��һ����¼���ٵ������ť��" );
	else
	{
		fm.PrtSeq.value = PolGrid.getRowColData(tRow-1,1);
		fm.Code.value = PolGrid.getRowColData(tRow-1,2);		
		fm.ContNo.value = PolGrid.getRowColData(tRow-1,3);
		fm.MissionID.value = PolGrid.getRowColData(tRow-1,6);
		fm.SubMissionID.value = PolGrid.getRowColData(tRow-1,7);
		fm.ActivityID.value = PolGrid.getRowColData(tRow-1,8);
		fm.fmtransact.value = "CONFIRM";
		submitForm();
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
	//tongmeng 2007-11-29 modify
	//���Ӻ˱�֪ͨ��(�Ǵ�ӡ��)����
//	strSQL = " select LWMission.MissionProp14,LWMission.MissionProp4,LWMission.MissionProp2,LWMission.MissionProp5,LWMission.MissionProp6 "
//	    + " ,LWMission.MissionID,LWMission.SubMissionID,LWMission.ActivityID "
//	    + " from LWMission where 1=1"
//		+ " and LWMission.ActivityID in (select  ActivityID from Lwactivity a where Functionid in ('10047005','10047008','10047011'))"
//		+ " and LWMission.MissionProp5 like '"+strManageCom+"%%'"
//		+ " and exists (select 1 from Lwprocess a  where  a.Processid=lwmission.Processid and a.Busitype='1004') "
//		//+ " and Substr(LWMission.MissionProp5,1,"+len+") = '"+tmanageCom+"'"
//		+ getWherePart('LWMission.MissionProp7','StartDay','>=')
//		+ getWherePart('LWMission.MissionProp7','EndDay','<=')
//		+ getWherePart('LWMission.MissionProp2','ContNoS','like') 
//	    + getWherePart('LWMission.MissionProp14','PrtSeqS') 
//	    + getWherePart('LWMission.MissionProp4','CodeS')	  ; 
  //alert(aResult);
	
	var sqlid1="RnewRePrintSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("xb.RnewRePrintSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(strManageCom);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('StartDay'))[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('EndDay'))[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('ContNoS'))[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('PrtSeqS'))[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('CodeS'))[0].value);//ָ������Ĳ���
    strSQL =mySql1.getString();
    
	turnPage.strQueryResult  = easyQueryVer3(strSQL);  
  
  //alert(turnPage.strQueryResult);
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

function checkdata(aContNo,aPrtSeq,aCode)
{
	if(aContNo == "" || aContNo == null)
	{
		alert("������Ϊ�գ�");
		return false;
	}
	if(aPrtSeq == "" || aPrtSeq == null)
	{
		alert("������Ϊ�գ�");
		return false;
	}
	if(aCode == "" || aCode == null)
	{
		alert("������Ϊ�գ�");
		return false;
	}
	
	
//	var wherepart = " and activityid = ";
//	if(aCode == "BQ90")
//	   wherepart += "'0000001106'";
//	else
//	   wherepart += "'0000001280'";
//	var tResult = easyExecSql("select 1 from lwmission where missionprop2 = '" + aContNo + "' and missionprop3 = '" + aPrtSeq + "'" + wherepart,1,0);
	var sqlid1="";
	if(aCode == "BQ90"){
		sqlid1="RnewRePrintSql2"
	}else{
		sqlid1="RnewRePrintSql3"
	}
	var mySql2=new SqlClass();
	mySql2.setResourceName("xb.RnewRePrintSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(aContNo);//ָ������Ĳ���
	mySql2.addSubPara(aPrtSeq);//ָ������Ĳ���
	var tResult = easyExecSql(mySql2.getString(),1,0);
	if(tResult != null)
	{
		alert("��֪ͨ��δ��ӡ�������������ӡ��");
		return false;
	}
	return true;
		
}