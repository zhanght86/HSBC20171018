//�������ƣ�UWGrpSpec.js
//�����ܣ������˹��˱���Լ¼��
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
    parent.close();
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
function initlist(tProposalNo)
{
	
	// ��дSQL���
	k++;
	var strSQL = "";
	
//	strSQL = "select dutycode,firstpaydate,payenddate from lcduty where "+k+" = "+k
//		+ " and polno = '"+tProposalNo+"'";
	
	var sqlid1="UWGSpecSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWGSpecSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(k);//ָ������Ĳ���
	mySql1.addSubPara(k);//ָ������Ĳ���
	mySql1.addSubPara(tProposalNo);//ָ������Ĳ���
	strSQL=mySql1.getString();
	
    str  = easyQueryVer3(strSQL, 1, 0, 1); 
  
  return str;
}

//��ѯ�Ѿ��ӷ���Ŀ
function QueryGrid(tProposalNo)
{
	// ��ʼ�����
	//initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select dutycode,payplantype,paystartdate,payenddate,prem from LCPrem where 1=1 "
//			 + " and PolNo ='"+tProposalNo+"'"
//			 + " and payplancode like '000000%%'";			
	
	var sqlid2="UWGSpecSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("uw.UWGSpecSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tProposalNo);//ָ������Ĳ���
	strSQL=mySql2.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
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
  return true;	
}

//��ѯ�Ѿ�¼����Լԭ��
function QuerySpecReason(tProposalNo)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select specreason from LCGUWMaster where 1=1 "
//			 + " and grppolno = '"+tProposalNo+"'";
	
	var sqlid3="UWGSpecSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("uw.UWGSpecSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tProposalNo);//ָ������Ĳ���
	strSQL=mySql3.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.SpecReason.value = turnPage.arrDataCacheSet[0][0];
  
  return true;	
}


//��ѯ�Ѿ�¼����Լ����
function QuerySpec(tProposalNo)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select speccontent from LCSpec where 1=1 "
//			 + " and specno = (select max(specno) from lcspec where polno = '"+tProposalNo+"')";
	
	var sqlid4="UWGSpecSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("uw.UWGSpecSql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tProposalNo);//ָ������Ĳ���
	strSQL=mySql4.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.Remark.value = turnPage.arrDataCacheSet[0][0];	
  
  return true;	
}

