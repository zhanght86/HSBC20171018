//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//window.onfocus=myonfocus;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var sqlresourcename = "logmanage.LogComponentSql";
//�ύ�����水ť��Ӧ����

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
	var iHeight=350;     //�������ڵĸ߶�; 
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
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  LogReset();
  LogQuery();
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function LogReset()
{
  try
  {
	  initInpBox();
  }
  catch(re)
  {
  	alert("LogComponent.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

function LogUpdate()
{
	//check!!!!!!
	var tSel = LogSubjectGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "���Ȳ�ѯ/ѡ���ٽ����޸�!" );
		return;
	}	
	//fm.LogSubjectID.value = LogSubjectGrid.getRowColData(tSel-1,1);
	fm.Flag.value = "LOG";
    document.all('Transact').value ="UPDATE";
    var i = 0;
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     document.getElementById("fm").submit(); //�ύ
}

function LogSubmit()
{
	//check!!!!!!
	fm.Flag.value = "LOG";
    document.all('Transact').value ="INSERT";
    var i = 0;
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     document.getElementById("fm").submit(); //�ύ
}

function LogDelete()
{
	//check!!!!!!
	var tSel = LogSubjectGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "���Ȳ�ѯ/ѡ���ٽ���ɾ��!" );
		return;
	}	
	fm.LogSubjectID.value = LogSubjectGrid.getRowColData(tSel-1,1);
	fm.Flag.value = "LOG";
    document.all('Transact').value ="DELETE";
    var i = 0;
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     document.getElementById("fm").submit(); //�ύ
}

function ShowLog()
{
	var arrResult = new Array();
	var tSel = LogSubjectGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼!" );
		return;
	}			
	else
	{
	    fm.LogSubjectID.value = LogSubjectGrid.getRowColData(tSel-1,1);
	    fm.LogSubjectDes.value = LogSubjectGrid.getRowColData(tSel-1,2);
	    fm.LogDept.value = LogSubjectGrid.getRowColData(tSel-1,3);
	    fm.LogServiceModule.value = LogSubjectGrid.getRowColData(tSel-1,4);
	    fm.TaskCode.value = LogSubjectGrid.getRowColData(tSel-1,5);
	    fm.LogType.value = LogSubjectGrid.getRowColData(tSel-1,7);                         
	    fm.Switch.value = LogSubjectGrid.getRowColData(tSel-1,8);
	    showCodeListKey('logtype', document.getElementsByName("LogType"), null, null, null, null, 0);
	}
}

function LogQuery()
{
	// ��ʼ�����
	//����ҳ���ʱ���Ѿ���ʼ���˸�MuLine�˴�����Ҫ�ظ���ʼ��(�˷Ѳ���Ҫ�ĵȴ�ʱ��)
	//initLogSubjectGrid();

	// ��дSQL���
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("LogComponentSql0");
	    mySql.addSubPara(fm.LogSubjectID.value);
	    mySql.addSubPara(fm.LogSubjectDes.value);
	    mySql.addSubPara(fm.LogDept.value);
	    mySql.addSubPara(fm.LogServiceModule.value);
	    mySql.addSubPara(fm.TaskCode.value);
	    mySql.addSubPara(fm.LogType.value);
	    mySql.addSubPara(fm.Switch.value);

	turnPage.queryModal(mySql.getString(), LogSubjectGrid);
	if(LogSubjectGrid.mulLineCount <= 0){
		alert("û�з������������ݣ�");
		return false;
	}
	divLogSubject.style.display = "";

}


function LogToItem()
{
	//check!!!!!!
	var tSel = LogSubjectGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����־�����¼!" );
		return;
	}
	fm.LogSubjectID.value = LogSubjectGrid.getRowColData(tSel-1,1);
	var tLogSubjectID = LogSubjectGrid.getRowColData(tSel-1,1);
	var varSrc="&SubjectID=" + tLogSubjectID;
	var newWindow = OpenWindowNew("../logmanage/LogItemInputFrame.jsp?Interface=../logmanage/LogItemInput.jsp" + varSrc,"","left");

}
