//�������ƣ�GedorAutoUW.js
//�����ܣ��ŵ���ȫ�Զ��˱�
//�������ڣ�
//������  ��
//���¼�¼��  ������ ZhangRong   ��������   2004.12  ����ԭ��/���� Upgrade

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var i = 0;
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	initPGrpEdorMainGrid();
	fm.submit(); //�ύ
}

//�Զ��˱���ȫ����
function edorAutoUW()
{
	var tGrpContNo ;
	var tEdorNo;
	tGrpContNo = document.all('GrpContNo').value;
	tEdorNo = document.all('EdorNo').value;
	
	if (window.confirm("�Ƿ�˱���������?"))
	{
		var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		fm.submit();
	}
}

//�˹��˱���ȫ
function edorManuUW()
{
	alert("waiting....");
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
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
  }
  initForm();
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

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
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

// ��ѯ��ť
function easyQueryClick()
{
	var EdorState;
	
	tEdorState='2';
	
	// ��ʼ�����
	initPGrpEdorMainGrid();
	
	// ��дSQL���
	var strSQL = "";
	var tReturn = parseManageComLimit();
	var tGrpContNo = document.all('GrpContNo').value;
	var tEdorNo = document.all('EdorNo').value;	
//	strSQL = "select EdorNo,GrpContNo,EdorAppDate,EdorValiDate,GetMoney from LPGrpEdorMain where EdorState='" + tEdorState + "' and "+ tReturn
//				 + " and UWState in ('0')"
//				 + getWherePart( 'GrpContNo' )
//				 + getWherePart('EdorNo');				 
	var sqlid1="GEdorAutoUwSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorAutoUwSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdorState);//ָ������Ĳ���
	mySql1.addSubPara(tReturn);//ָ������Ĳ���
	mySql1.addSubPara(tGrpContNo);//ָ������Ĳ���
	mySql1.addSubPara(tEdorNo);//ָ������Ĳ���
	strSQL=mySql1.getString();	
	//��ѯSQL�����ؽ���ַ���
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	  
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) 
	{
		alert("δ��ѯ�����Զ��˱���������");
	}
	else
	{
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = PGrpEdorMainGrid;    
			  
		//����SQL���
		turnPage.strQuerySql     = strSQL; 

		//���ò�ѯ��ʼλ��
		turnPage.pageIndex       = 0;  

		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		arrGrid = arrDataSet;
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
}

//��ѯ��ϸ��Ϣ
function detailEdor()
{
	var arrReturn = new Array();
	var tSel = PGrpEdorMainGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
	else
	{
		arrReturn = getQueryResult();
		document.all('EdorType').value =arrReturn[0][2];
		document.all('EdorNo').value = arrReturn[0][0];
		document.all('GrpContNo').value=arrReturn[0][1];
		detailEdorType();
	}
}
	
function getQueryResult()
{
	var arrSelected = null;
	tRow = PGrpEdorMainGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}
