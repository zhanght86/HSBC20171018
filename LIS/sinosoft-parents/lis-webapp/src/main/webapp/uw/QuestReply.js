//�������ƣ�QuestReply.js
//�����ܣ�������ظ�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ�����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "1";  //���������λ�� 1.�˱�

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ���; 
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
	// ��ʼ������
	
	initQuestGrid();
	
	
	// ��дSQL���
	k++;
	var strSQL = "";
	if (tFlag == "1")
	{
		
		var sqlid98="ContQuerySQL98";
		var mySql98=new SqlClass();
		mySql98.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql98.setSqlId(sqlid98);//ָ��ʹ�õ�Sql��id
		mySql98.addSubPara(k);//ָ������Ĳ���
		mySql98.addSubPara(k);//ָ������Ĳ���
	    strsql=mySql98.getString();	
		
//		strSQL = "select code,codename,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
	}
	
	//alert(strSQL);
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;	
}


function QueryCont(tContNo, tFlag, tQuest)
{	
	
	// ��дSQL���
	//alert("begin");
	k++;

	var strSQL = "";

	if (tContNo == "")
	{
		alert("�����Ų���Ϊ�գ�");
	}
	if (tQuest == "")
	{
		alert("���������Ϊ�գ�");
	}
	if (tFlag == "")
	{
		alert("����λ�ò���Ϊ�գ�");	
	}

		var sqlid99="ContQuerySQL99";
		var mySql99=new SqlClass();
		mySql99.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql99.setSqlId(sqlid99);//ָ��ʹ�õ�Sql��id
		mySql99.addSubPara(k);//ָ������Ĳ���
		mySql99.addSubPara(k);//ָ������Ĳ���
		mySql99.addSubPara(tContNo);//ָ������Ĳ���
		mySql99.addSubPara(tFlag);//ָ������Ĳ���
		mySql99.addSubPara(tQuest);//ָ������Ĳ���
	    strSQL=mySql99.getString();	

//	strSQL = "select issuecont,replyresult from lcissuepol where "+k+"="+k				 	
//			 + " and ContNo = '"+tContNo+"'"
//			 + " and operatepos = '"+tFlag+"'"
//			 + " and issuetype = '"+tQuest+"'";
	
	
	//alert(strSQL);
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û��¼����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var tcont = "";
  var treply = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 1)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			tcont = turnPage.arrDataCacheSet[0][0];
			treply = turnPage.arrDataCacheSet[0][1];
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
 
  if (tcont == "")
  {
  	alert("û��¼����������");
  	return "";
  }
  //alert(1);
  if (treply != "")
  {
 	alert("������Ѿ��ظ������ܼ���������");
  	
  	//window.close();
  	parent.close();
  	return "";
  }
  
  //alert("returnstr:"+returnstr);		
  document.all('Content').value = tcont;
  document.all('ReplyResult').value = treply;
  //alert("�Ѿ�¼�����������뿼�����������¼�룡");
  return returnstr;
}
