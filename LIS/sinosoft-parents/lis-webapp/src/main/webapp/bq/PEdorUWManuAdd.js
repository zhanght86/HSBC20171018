//�������ƣ�PEdorUWManuAdd.js
//�����ܣ���ȫ�˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var str2 = "";
var turnPage = new turnPageClass();
//var turnPageReason = new turnPageClass();
//var turnPage2 = new turnPageClass();
//var turnPageReason2 = new turnPageClass();
//var turnPageMission = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var str_sql = "",sql_id = "", my_sql = ""; 
//�ύ�����水ť��Ӧ����
function submitForm(tEdorNo,tMissionID)
{
  var i = 0;
  var cPolNo = fm.PolNo2.value ;
  if(cPolNo == null || cPolNo == "")
  {
  	alert("δѡ��ӷѵı���!");
  	return;
  }
  
  if(fm.SubMissionID.value == "")
  {
  	alert("��¼��˱�֪ͨ����Ϣ,��δ��ӡ,������¼���µĺ˱�֪ͨ��ӷ���Ϣ!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cPrtNo = fm.PrtNo.value;
  	initForm(cPolNo,cPrtNo,tEdorNo,tMissionID,cEdorType);
  	return;
  }

  var i = SpecGrid.mulLineCount ; 
  if(i==0)
  {
  	alert("δ¼���µĺ˱�֪ͨ��ӷ���Ϣ!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cPrtNo = fm.PrtNo.value;
  	initForm(cPolNo,cPrtNo,tEdorNo,tMissionID,cEdorType);
  	return;
  }
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
  fm.action = "./PEdorUWManuAddChk.jsp";
  fm.submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
function submitForm2(tEdorNo,tMissionID)
{
  var i = 0;
  var cPolNo = fm.PolNo2.value ;
  if(cPolNo == null || cPolNo == "")
  {
  	alert("δѡ��ӷѵı���!");
  	return;
  }
  
  if(fm.SubMissionID.value == "")
  {
  	alert("��¼��˱�֪ͨ����Ϣ,��δ��ӡ,������¼���µĺ˱�֪ͨ��ӷ���Ϣ!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cPrtNo = fm.PrtNo.value;
  	initForm(cPolNo,cPrtNo,tEdorNo,tMissionID,cEdorType);
  	return;
  }

  var i = Spec2Grid.mulLineCount ; 
  if(i==0)
  {
  	alert("δ¼���µĺ˱�֪ͨ��ӷ���Ϣ!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cPrtNo = fm.PrtNo.value;
  	initForm(cPolNo,cPrtNo,tEdorNo,tMissionID,cEdorType);
  	return;
  }
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
  fm.action ="./PEdorUWManuModifyCBAddFeeChk.jsp";
  fm.submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  { 
	var showStr="�����ɹ�";  	
  	showInfo.close();
  	alert(showStr);
  	//parent.close();
  	
    //ִ����һ������
  }
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}
         

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


function manuchkspecmain()
{
	fm.submit();
}

// ��ѯ��ť
function initlist(tPolNo,tEdorNo,tEdorType)
{
	// ��дSQL���
	k++;
	var strSQL = "";
	//strSQL = "select dutycode,firstpaydate,payenddate from lcduty where "+k+" = "+k
	//	+ " and polno = '"+tPolNo+"'";
//	strSQL = "select dutycode,PEdorFirstPayDate(dutycode,'"+tPolNo+"','"+tEdorType+"'),PEdorEndPayDate(dutycode,'"+tPolNo+"','"+tEdorType+"') from lpduty where "+k+" = "+k
//		   + " and polno = '"+tPolNo+"'"
//		   + " and edortype = '"+tEdorType+"'"
//		   + " and edorno = '"+tEdorNo+"'";
	sql_id = "PEdorUWManuAddSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	my_sql.addSubPara(tEdorType);//ָ������Ĳ���
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	my_sql.addSubPara(tEdorType);//ָ������Ĳ���
	my_sql.addSubPara(k);//ָ������Ĳ���
	my_sql.addSubPara(k);//ָ������Ĳ���
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	my_sql.addSubPara(tEdorType);//ָ������Ĳ���
	my_sql.addSubPara(tEdorNo);//ָ������Ĳ���
	strSQL = my_sql.getString();
    str  = easyQueryVer3(strSQL, 1, 0, 1); 
    return str;
}


// ��ѯ��ť
function initlist2(tPolNo,tEdorNo,tEdorType)
{
	// ��дSQL���
	k++;
	var strSQL = "";
	//strSQL = "select dutycode,firstpaydate,payenddate from lcduty where "+k+" = "+k
		//+ " and polno = '"+tPolNo+"'";
//	strSQL = "select dutycode,PEdorFirstPayDate(dutycode,'"+tPolNo+"','"+tEdorType+"'),PEdorEndPayDate(dutycode,'"+tPolNo+"','"+tEdorType+"') from lpduty where "+k+" = "+k
//		   + " and polno = '"+tPolNo+"'"
//		   + " and edortype = '"+tEdorType+"'"
//		   + " and edorno = '"+tEdorNo+"'";
	sql_id = "PEdorUWManuAddSql2";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	my_sql.addSubPara(tEdorType);//ָ������Ĳ���
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	my_sql.addSubPara(tEdorType);//ָ������Ĳ���
	my_sql.addSubPara(k);//ָ������Ĳ���
	my_sql.addSubPara(k);//ָ������Ĳ���
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	my_sql.addSubPara(tEdorType);//ָ������Ĳ���
	my_sql.addSubPara(tEdorNo);//ָ������Ĳ���
	strSQL = my_sql.getString();
    str  = easyQueryVer3(strSQL, 1, 0, 1); 
    return str;
}
//��ѯ�Ѿ��ӷ���Ŀ
function QueryGrid(tPolNo,tPolNo2,tEdorNo,tEdorType)
{
	// ��ʼ�����
	//initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
   //��ȡԭ�ӷ���Ϣ
//	strSQL = "select dutycode,payplantype,paystartdate,payenddate,prem,suppriskscore from LPPrem where 1=1 "
//			 + " and PolNo ='"+tPolNo2+"'"
//			 + " and EdorNO = '" + tEdorNo + "'"
//			 + " and EdorType = '" + tEdorType+ "'"
//			 + " and payplancode like '000000%%'"
//			 + " and state = '2'";			
	sql_id = "PEdorUWManuAddSql3";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tPolNo2);//ָ������Ĳ���
	my_sql.addSubPara(tEdorNo);//ָ������Ĳ���
	my_sql.addSubPara(tEdorType);//ָ������Ĳ���
	strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = SpecGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
else
  SpecGrid.clearData();  


  //��ȡ�������������
	var tMissionID = fm.MissionID.value;	
//	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + tEdorNo +"'"
//				 + " and LWMission.MissionProp2 = '"+ tPolNo + "'"
//				 + " and LWMission.ActivityID = '0000000002'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";		
	var strsql = "";
	sql_id = "PEdorUWManuAddSql4";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tEdorNo);//ָ������Ĳ���
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	my_sql.addSubPara(tMissionID);//ָ������Ĳ���
	strsql = my_sql.getString();
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);    
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    fm.SubMissionID.value = "";
    return ;
  }  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
  return true;	
}


//��ѯ�Ѿ��ӷ���Ŀ
function QueryGrid2(tPolNo,tPolNo2,tEdorNo,tEdorType)
{
	// ��ʼ�����
	//initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
   //��ȡԭ�ӷ���Ϣ
//	strSQL = "select dutycode,payplantype,paystartdate,payenddate,prem ,payplancode,suppriskscore  from LPPrem where 1=1 "
//			 + " and PolNo ='"+tPolNo2+"'"
//			 + " and EdorNO = '" + tEdorNo + "'"
//			 + " and EdorType = '" + tEdorType+ "'"
//			 + " and payplancode like '000000%%'"
//			 + " and state <>'2'"
//			 + " order by payplancode";			
	sql_id = "PEdorUWManuAddSql5";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tPolNo2);//ָ������Ĳ���
	my_sql.addSubPara(tEdorNo);//ָ������Ĳ���
	my_sql.addSubPara(tEdorType);//ָ������Ĳ���
	strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = Spec2Grid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
else
{
	Spec2Grid.clearData();  
	alert("�ñ����޼����б��ӷ���Ϣ");
	return false;
}


  //��ȡ�������������
	var tMissionID = fm.MissionID.value;	
//	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + tEdorNo +"'"
//				 + " and LWMission.MissionProp2 = '"+ tPolNo + "'"
//				 + " and LWMission.ActivityID = '0000000002'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";	
	var strsql = "";
	sql_id = "PEdorUWManuAddSql6";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tEdorNo);//ָ������Ĳ���
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	my_sql.addSubPara(tMissionID);//ָ������Ĳ���
	strsql = my_sql.getString();
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);    
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    fm.SubMissionID.value = "";
    return ;
  }  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
  return true;	
}

function QueryPolAddGrid(tPrtNo,tEdorNo)
{
	// ��ʼ�����
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
   //��ȡԭ������Ϣ
//    strSQL = "select LCPol.PolNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "				 			
//			 + "  PrtNo ='"+tPrtNo+"'"
//			 + "  order by polno";			
	sql_id = "PEdorUWManuAddSql7";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tPrtNo);//ָ������Ĳ���
	strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolAddGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
 
  return true;	
}
	
function getPolGridCho()
{
  /*var tSelNo = PolAddGrid.getSelNo()-1;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,1);
  var cPolNo = fm.PolNo.value;
  var cEdorNo = fm.EdorNo.value ; 	
  var cEdorType = fm.EdorType.value ; 	
  fm.PolNo2.value = cPolNo2 ;
  if(cPolNo != null && cPolNo != "" && cEdorType != "")
  {
  	str = initlist(cPolNo2,cEdorNo,cEdorType);
    initUWSpecGrid(str);
    QueryGrid(cPolNo,cPolNo2,cEdorNo,cEdorType);	
    QueryAddReason(cPolNo,cEdorNo);
  }	*/
}

function QueryBQFee()
{
var tSelNo = PolAddGrid.getSelNo()-1;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,1);
  var cPolNo = fm.PolNo.value;
  var cEdorNo = fm.EdorNo.value ; 	
  var cEdorType = fm.EdorType.value ; 	
  fm.PolNo2.value = cPolNo2 ;
  if(cPolNo != null && cPolNo != "" && cEdorType != "")
  {
  	showDiv(divUWSpec1,"false");
    showDiv(divUWSpec2,"false");
	str2="";
  	str2 = initlist(cPolNo2,cEdorNo,cEdorType);
    initUWSpecGrid(str2);
    if(QueryGrid(cPolNo,cPolNo2,cEdorNo,cEdorType))	
    { 
    QueryAddReason(cPolNo,cEdorNo);
    showDiv(divUWSpec1,"true");
    showDiv(divUWSpec2,"false");
   }
  }

}

function QueryCBFee()
{
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,1);
  var cPolNo = fm.PolNo.value;
  var cEdorNo = fm.EdorNo.value ; 	
  var cEdorType = fm.EdorType.value ; 	
  fm.PolNo2.value = cPolNo2 ;
  if(cPolNo != null && cPolNo != "" && cEdorType != "")
  {
  	//showDiv(divUWSpec1,"false");
    //showDiv(divUWSpec2,"false");
	str2="";
  	str2 = initlist2(cPolNo2,cEdorNo,cEdorType);
    initUWSpec2Grid(str2);
    if(QueryGrid2(cPolNo,cPolNo2,cEdorNo,cEdorType))	
    { 
    QueryAddReason2(cPolNo,cEdorNo);
    showDiv(divUWSpec2,"true");
    showDiv(divUWSpec1,"false");
   }

  }	
}

//��ѯ�Ѿ�¼��ӷ���Լԭ��
function QueryAddReason(tPolNo,tEdorNo)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select addpremreason from LPUWMasterMain where 1=1 "
//			 + " and polno = '"+tPolNo+"'"
//			 + " and edorno = '" + tEdorNo +"'";
	sql_id = "PEdorUWManuAddSql8";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	my_sql.addSubPara(tEdorNo);//ָ������Ĳ���
	strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	fm.AddReason.value = "";
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.AddReason.value = turnPage.arrDataCacheSet[0][0];
  
  return true;	
}

//��ѯ�Ѿ�¼��ӷ���Լԭ��
function QueryAddReason2(tPolNo,tEdorNo)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select addpremreason from LCUWMaster where 1=1 "
//			 + " and polno = '"+tPolNo+"'";
			 //+ " and edorno = '" + tEdorNo +"'";
	sql_id = "PEdorUWManuAddSql9";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorUWManuAddSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	fm.AddReason.value = "";
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.AddReason2.value = turnPage.arrDataCacheSet[0][0];
  
  return true;	
}

