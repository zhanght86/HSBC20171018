//�������ƣ�PEdorUWGrpManuAdd.js
//�����ܣ���ȫ�˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var cPolNo = fm.PolNo2.value ;
  if(cPolNo == null || cPolNo == "")
  {
  	alert("δѡ��ӷѵ�Ͷ����!");
  	return;
  }
  
/*  if(fm.SubMissionID.value == "")
  {
  	alert("��¼��˱�֪ͨ����Ϣ,��δ��ӡ,������¼���µĺ˱�֪ͨ��ӷ���Ϣ!");
  	var cPolNo = fm.PolNo.value;
  	var cContNo = fm.ContNo.value;
  	initForm(cPolNo,cContNo);
  	return;
  }
*/
  var i = SpecGrid.mulLineCount ; 
  if(i==0)
  {
  	alert("δ¼���µĺ˱�֪ͨ��ӷ���Ϣ!");
  	var cEdorType = fm.EdorType.value;
  	var cPolNo = fm.PolNo.value;
  	var cContNo = fm.ContNo.value;
  	initForm(cPolNo,cContNo);
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
  	parent.close();
  	
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
function initlist(tPolNo)
{
	// ��дSQL���
	k++;
	var strSQL = "";
	strSQL = "select dutycode,firstpaydate,payenddate from lcduty where "+k+" = "+k
		+ " and polno = '"+tPolNo+"'";
    str  = easyQueryVer3(strSQL, 1, 0, 1); 
    return str;
}

//��ѯ�Ѿ��ӷ���Ŀ
function QueryGrid(tPolNo,tPolNo2)
{
	// ��ʼ�����
	//initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
   //��ȡԭ�ӷ���Ϣ
	strSQL = "select dutycode,payplantype,paystartdate,payenddate,prem,suppriskscore from LCPrem where 1=1 "
			 + " and PolNo ='"+tPolNo2+"'"
			 + " and payplancode like '000000%%'"
			 + " and state = '1'";			
	
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


  //��ȡ�������������
/*	var tMissionID = fm.MissionID.value;	
	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 + " and LWMission.MissionProp2 = '"+ tPolNo + "'"
				 + " and LWMission.ActivityID = '0000000102'"
				 + " and LWMission.ProcessID = '0000000001'"
				 + " and LWMission.MissionID = '" +tMissionID +"'";				 
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);    
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    fm.SubMissionID.value = "";
    return ;
  }  */
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
  return true;	
}

function QueryPolAddGrid(tPolNo, tContNo)
{
	// ��ʼ�����
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
   //��ȡԭ������Ϣ
    strSQL = "select LCPol.PolNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "				 			
			 + "  ContNo ='"+tContNo+"'" + " and PolNo = '" + tPolNo + "'"
			 + "  order by polno";			
	
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
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,1);
  var cPolNo = PolAddGrid.getRowColData(tSelNo,1);
  fm.PolNo.value = cPolNo
  fm.PolNo2.value = cPolNo2 ;
  if(cPolNo != null && cPolNo != "" )
  {
  	str = initlist(cPolNo2);
    initUWSpecGrid(str);
    QueryGrid(cPolNo,cPolNo2);	
    QueryAddReason(cPolNo);
  }	
}


//��ѯ�Ѿ�¼��ӷ���Լԭ��
function QueryAddReason(tPolNo)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
	strSQL = "select addpremreason from LCUWMaster where 1=1 "
			 + " and polno = '"+tPolNo+"'"
	
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
