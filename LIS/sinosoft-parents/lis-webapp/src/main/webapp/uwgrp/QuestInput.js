//�������ƣ�QuestInput.js
//�����ܣ������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var sqlresourcename = "uwgrp.QuestInputSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
	if(LoadFlag=="1"&&fm.all('BackObj').value=="1"){
	  alert("������Լ¼��ʱ������ѡ�񡰷��Ͷ���Ϊ��������޸ĸڡ���������ѡ�񡰷��Ͷ��󡱣�");
	  return;
	}
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
    
    showInfo.close();
   
    //parent.close();
  }
  else
  { 
	var content="�����ɹ�";
  	var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  	showInfo.close();
  	
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
  else 
  {
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

function QuestQuery(tContNo, tFlag)
{
	// ��ʼ�����
	var i,j,m,n;
	//initQuestGrid();
	
	
	// ��дSQL���
	k++;
	var strSQL = "";
	//if (tFlag == "1")
	//{
	/*
		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'Question'"
				 + " and code <> '99'";
	//}
	*/
	//alert(strSQL);
	
  //��ѯSQL�����ؽ���ַ���
            var mysql1= new SqlClass();
            var sqlid1 = "QuestInputSql1";
			mysql1.setResourceName(sqlresourcename);
			mySql1.setSqlId(sqlid1);
			mysql1.addSubPara(k);
			mysql1.addSubPara(k);
  
  turnPage.strQueryResult  = easyQueryVer3(mysql1.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  //turnPage.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  //turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  //turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;  
    if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("��ѯʧ��!!");
  			return "";
  		}
  	}
}
else
{
	alert("��ѯʧ��!");
	return "";
}
  //alert("returnstr:"+returnstr);		
  fm.Quest.CodeData = returnstr;
  return "";	
}


function QueryCont(tContNo, tFlag)
{	
	
	// ��дSQL���

	k++;

	var strSQL = "";

	//if (tFlag == "1")
	//{
		//strSQL = "select issuecont from lcissuepol where "+k+"="+k				 	
		//		 + " and ContNo = '"+tContNo+"'";
	//}
	var mysql2= new SqlClass();
	var sqlid2 = "QuestInputSql2";
			mysql2.setResourceName(sqlresourcename);
			mySql2.setSqlId(sqlid2);
			mysql2.addSubPara(k);
			mysql2.addSubPara(k);
			mysql2.addSubPara(tContNo);
	//alert(strSQL);
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mysql2.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û��¼����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			returnstr = turnPage.arrDataCacheSet[0][0];
  		}
  		else
  		{
  			alert("û��¼����������");
  			return "";
  		}
  	
  }
  else
  {
  	alert("û��¼����������");
	return "";
  }

  if (returnstr == "")
  {
  	alert("û��¼����������");
  }
  
  //alert("returnstr:"+returnstr);		
  fm.all('Content').value = returnstr;
  //alert("�Ѿ�¼�����������뿼�����������¼�룡");
  return returnstr;
}

function afterCodeSelect( cCodeName, Field )
{

	var tObjFlag = fm.all('BackObj').value;
	
	var tCustomerNoFlag = fm.all('QuestionObj').value;
	
	
	if(cCodeName=="backobj")
	{
	if(tObjFlag == '2')
	{
		
	divCustomerqustion.style.display = '';
  }
else if(tObjFlag == '1')
	{
		divCustomerqustion.style.display = 'none';
		divquestobjname.style.display = 'none';
	  divquestobj.style.display = 'none';
	}
}

if(cCodeName == "question")
{
	
if(tCustomerNoFlag =='0')
{
	
  initAppnt();
}
if(tCustomerNoFlag =='1')
{
	
	initInsured();
}
if(tCustomerNoFlag == '2'||tCustomerNoFlag == '3')
{
	fm.all('CustomerNo').value = "";
	fm.all('CustomerName').value = "";
}
	
}
}
function initAppnt()
{
	var tContNo = fm.ContNo.value;
	//strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
	var mysql3= new SqlClass();
	var sqlid3 = "QuestInputSql3";
	mysql3.setResourceName(sqlresourcename);
	mySql3.setSqlId(sqlid3);
	mysql3.addSubPara(tContNo);

  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(mysql3.getString(), 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
      
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
}
function initInsured()
{
	var tContNo = fm.ContNo.value;
//	alert(tContNo+","+fm.all('QuestionObj').value);
    /*
	strsql = "select insuredno,Name from lcinsured "
	       + "where  ContNo = '"+tContNo+"' "
	       + "and SequenceNo = '"+fm.all('QuestionObj').value+"'";
	*/
	var mysql4= new SqlClass();
	var sqlid4 = "QuestInputSql4";
	mysql4.setResourceName(sqlresourcename);
	mySql4.setSqlId(sqlid4);
	mysql4.addSubPara(tContNo);
    mysql4.addSubPara(fm.all('QuestionObj').value);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(mysql4.getString(), 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
      
    alert("�޴˿ͻ�");
    return ;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
}