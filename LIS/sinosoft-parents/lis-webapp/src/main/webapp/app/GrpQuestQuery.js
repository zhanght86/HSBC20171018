//�������ƣ�QuestQuery.js
//�����ܣ��������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //���������λ�� 1.�˱�
var canReplyFlag = false;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
function IfPrint()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
��cGrpContNo = fm.GrpContNo.value;  //��������	

	var sqlid15="ProposalQuerySql15";
var mySql15=new SqlClass();
mySql15.setResourceName("uw.QuestInputSql15"); //ָ��ʹ�õ�properties�ļ���
mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
mySql15.addSubPara(cGrpContNo);//ָ������Ĳ���
��var strSql=mySql15.getString();		 

��//var strSql = "select * from LCPol where PolNo='" + cGrpContNo + "' and  approveflag = '2'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {     
   ����alert("�ѷ��˱�֪ͨ�飬��δ��ӡ,�������ڴ�ʱ�޸�������Ĵ�ӡ��־��");
          return;
      }      
  fm.action = "./QuestQueryUpdatePrintFlag.jsp";
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showInfo.close();
    alert(content);
  }
  else if (FlagStr == "Succ") {
    content = "�����ɹ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QuestQuery(fm.GrpContNo.value, fm.HideOperatePos.value);
  }
  else
  { 
	var showStr="��ӡ��Ǹ��³ɹ�";
  	alert(showStr);
  	//showInfo.close();
  	
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
	document.getElementById("fm").submit();
}

function query()
{
	// ��ʼ�����
	initQuestGrid();
	initContent();

	// ��дSQL���
	k++;
	var strSQL = "";
	var ifreply = fm.IfReply.value;
	var tOPos = fm.OperatePos.value;
	var tOperatePos = fm.Flag.value;
	var tGrpContNo = fm.GrpContNo.value;

	if (tOperatePos == "")
	{
		alert("����λ�ô���ʧ��!");
		return "";
	}

	//if(tOPos == "")
	//{
	//	alert("����λ�ò���Ϊ�գ�");
	//	return "";
	//}
	
	if (tOperatePos == "0"||tOperatePos == "2"||tOperatePos == "4")
	{
		if(tGrpContNo == "")
		{
			alert("�����Ų���Ϊ��!");
			return "";
		}
	}
	
	
		var sqlid16="ProposalQuerySql16";
var mySql16=new SqlClass();
mySql16.setResourceName("uw.QuestInputSql16"); //ָ��ʹ�õ�properties�ļ���
mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
mySql16.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql16.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql16.addSubPara(fm.ManageCom.value);//ָ������Ĳ���

mySql16.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql16.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql16.addSubPara(fm.Quest.value);//ָ������Ĳ���

strSQL=mySql16.getString();	
	
//		strSQL = "select GrpContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from LCGrpIssuePol where "				 	
//				 + " OperatePos in ('0','2','4')"
//				 + getWherePart( 'GrpContNo','GrpContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	if (document.all("IfReply").value == "Y")
	{
	 strSQL = strSQL + " and replyresult is not null";
	}
	else if (document.all("IfReply").value == "N")
	{
	 strSQL = strSQL + " and replyresult is null";
	}

	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult == false) {
    alert("û�������");
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


function queryone(parm1,parm2)
{	
	
	// ��дSQL���
	//alert("begin");
	k++;

	var strSQL = "";
	var tcho;
	
	if(document.all(parm1).all('InpQuestGridSel').value == '1' )
	{
	//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
   		tPos = document.all(parm1).all('QuestGrid7').value;
   		tQuest = document.all(parm1).all('QuestGrid2').value;
   		tSerialNo = document.all(parm1).all('QuestGrid10').value;
  	}
  	
	document.all('HideOperatePos').value=tPos;
	document.all('HideQuest').value=tQuest;
    document.all('HideSerialNo').value=tSerialNo;
    document.all('SerialNo').value=tSerialNo;
	tGrpContNo = fm.GrpContNo.value;
	//tQuest = fm.Quest.value;
	//tPos = fm.OperatePos.value;
	if (tPos == "")
	{
		alert("��ѡ�������!")
		return "";
	}	
	
	if (tGrpContNo == "")
	{
		alert("�����Ų���Ϊ�գ�");
		return "";
	}
	if (tQuest == "")
	{
		alert("���������Ϊ�գ�");
		return "";
	}
	if (tSerialNo == "")
	{
		alert("��������벻��Ϊ�գ�");
		return "";
	}
	
var sqlid17="ProposalQuerySql17";
var mySql17=new SqlClass();
mySql17.setResourceName("uw.QuestInputSql17"); //ָ��ʹ�õ�properties�ļ���
mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
mySql17.addSubPara(k);//ָ������Ĳ���
mySql17.addSubPara(k);//ָ������Ĳ���

mySql17.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql17.addSubPara(fm.issuetype.value);//ָ������Ĳ���
mySql17.addSubPara(fm.OperatePos.value);//ָ������Ĳ���

mySql17.addSubPara(fm.SerialNo.value);//ָ������Ĳ���
strSQL=mySql17.getString();	
	
//	strSQL = "select issuecont,replyresult,issuetype,OperatePos from LCGrpIssuePol where "+k+"="+k+" "
//			 + getWherePart( 'GrpContNo','GrpContNo' )
//			 + getWherePart( 'issuetype','HideQuest' )
//			 + getWherePart( 'OperatePos','HideOperatePos')
//			 + getWherePart( 'SerialNo','HideSerialNo');

	
	
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
  var ttype = "";
  var tOperatePos = "";
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
			ttype = turnPage.arrDataCacheSet[0][2];
			tOperatePos = turnPage.arrDataCacheSet[0][3];
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
  
  document.all('Content').value = tcont;
  
  if (treply == "") {
    document.all('replyresult').readOnly = false;
    canReplyFlag = true;
  }
  else {
    document.all('replyresult').readOnly = true;
    canReplyFlag = false;
  }
  document.all('replyresult').value = treply;
  
  document.all('Type').value = ttype;
  document.all('OperatePos').value = tOperatePos;
  return returnstr;
}

function input()
{
	cGrpContNo = fm.GrpContNo.value;  //��������
	
	//showModalDialog("./QuestInputMain.jsp?GrpContNo1="+cGrpContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestInputMain.jsp?GrpContNo1="+cGrpContNo+"&Flag="+cflag,"window1",sFeatures);
	
}

function quickReply() {
  if (QuestGrid.getSelNo() == "0") {
    alert("����ѡ��һ���������");
    return;
  }
  
  fm.ReplyResult.value = "�����޸���ɣ�";
  reply();
}

function reply()
{ 
  if (fm.ReplyResult.value == "") {
    alert("û�лظ����ݣ����ܻظ���");  
    return;
  }
  
  if (!canReplyFlag) {
    alert("��������ѻظ��������ٴλظ���");  
    return;
  }
  
	cGrpContNo = fm.GrpContNo.value;  //��������
	cQuest = fm.HideQuest.value;            //���������
	cflag = fm.HideOperatePos.value;        //���������λ�� 
	
	fm.GrpContNoHide.value = fm.GrpContNo.value;  //��������
	fm.Quest.value = fm.HideQuest.value;            //���������
	fm.Flag.value = fm.HideOperatePos.value;        //���������λ�� 
	fm.SerialNo.value = fm.HideSerialNo.value;
	if(cQuest == "")
	{
		alert("��ѡ��Ҫ�ظ������!");
	}
	else
	{
		//showModalDialog("./QuestInputMain.jsp?GrpContNo1="+cGrpContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		//window.open("./QuestReplyMain.jsp?GrpContNo1="+cGrpContNo+"&Flag="+cflag+"&Quest="+cQuest,"window2");
		fm.action = "./GrpQuestReplyChk.jsp";
		document.getElementById("fm").submit();
	}
	
}

function QuestQuery(tGrpContNo, tFlag)
{
	// ��ʼ�����
	var i,j,m,n;
	//initQuestGrid();
	
	
	// ��дSQL���
	k++;
	var strSQL = "";
	//if (tFlag == "1")
	//{
	
	var sqlid18="ProposalQuerySql18";
var mySql18=new SqlClass();
mySql18.setResourceName("uw.QuestInputSql18"); //ָ��ʹ�õ�properties�ļ���
mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
mySql18.addSubPara(k);//ָ������Ĳ���
mySql18.addSubPara(k);//ָ������Ĳ���

strSQL=mySql18.getString();	
	
//		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'GrpQuestion' order by to_number(code)";
	//}
	
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
function returnParent()
{
	var arrReturn = new Array();
	var tSel = QuestGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{       
			var strreturn = getQueryResult();
			top.opener.afterQuery( strreturn );
			
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		top.close();
	}
}
function getQueryResult()
{
	var arrSelected = null;	
	var tRow = QuestGrid.getSelNo();
	if( tRow == 0 || tRow == null || QuestGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = QuestGrid.getRowData(tRow-1);
	var streturn=arrSelected;
	return streturn;
}
