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
  ItemReset();
  ItemQuery();
}

function ItemReset()
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

function ItemUpdate()
{
	//check!!!!!!
	var tSel = LogItemGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "���Ȳ�ѯ/ѡ���ٽ����޸�!" );
		return;
	}	
	fm.ItemID.value = LogItemGrid.getRowColData(tSel-1,1);
	fm.Flag.value = "ITEM";
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

function ItemSubmit()
{
	//check!!!!!!
	fm.Flag.value = "ITEM";
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

function ItemDelete()
{
	//check!!!!!!
	var tSel = LogItemGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "���Ȳ�ѯ/ѡ���ٽ���ɾ��!" );
		return;
	}	
	fm.ItemID.value = LogItemGrid.getRowColData(tSel-1,1);
	fm.Flag.value = "ITEM";
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

function ShowItem()
{
	var arrResult = new Array();
	var tSel = LogItemGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼!" );
		return;
	}			
	else
	{
	    fm.ItemID.value = LogItemGrid.getRowColData(tSel-1,1);
	    fm.ItemDes.value = LogItemGrid.getRowColData(tSel-1,2);
	    fm.KeyType.value = LogItemGrid.getRowColData(tSel-1,3);
	    fm.Grammar.value = LogItemGrid.getRowColData(tSel-1,4);
	    fm.ClassName.value = LogItemGrid.getRowColData(tSel-1,5);
	    fm.Func.value = LogItemGrid.getRowColData(tSel-1,6);                         
	    fm.Remark.value = LogItemGrid.getRowColData(tSel-1,7);
	    fm.ItemSwitch.value = LogItemGrid.getRowColData(tSel-1,8);
	    //showCodeListKey('logtype', document.getElementsByName("LogType"), null, null, null, null, 0);
	}
}

function ItemQuery()
{
	// ��ʼ�����
	initLogItemGrid();

	// ��дSQL���
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("LogComponentSql1");

	    mySql.addSubPara(fm.SubIDForItem.value);
	    mySql.addSubPara("'"+fm.SubIDForItem.value+"'");
	    mySql.addSubPara(fm.SubIDForItem.value);

	turnPage.queryModal(mySql.getString(), LogItemGrid);
	if(LogItemGrid.mulLineCount <= 0){
		alert("δ��ѯ���������Ӧ���Ƶ���Ϣ��");
		return false;
	}
	divLogItemMain.style.display = "";

}

function AddItem()
{
	//check!!!!!!
	
	//mulLineNum
	
	//alert(LogItemGrid.mulLineCount);
	for(i=0;i<LogItemGrid.mulLineCount;i++)
	{
		var KeyType=LogItemGrid.getRowColData(i,4);
		var switchType = LogItemGrid.getRowColData(i,5);
		//alert(i+":"+KeyType+":"+switchType);
		if(KeyType==null||KeyType==''
		   ||switchType==null||switchType=='')
		   {
		   	alert('��'+eval(i+1)+'�����ݲ�ȫ!');
		   	return false;
		   }
	}
	//return ;
	/*
	var sSel = LogItemGrid.getSelNo();	
	if( sSel == 0 || sSel == null )
	{
		alert( "����ѡ����Ҫ��ӵĿ��Ƶ�!" );
		return;
	}	
	fm.ItemID.value = LogItemGrid.getRowColData(sSel-1,1);
	*/
	fm.Flag.value = "DOMAIN";
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

function DelItem()
{
	//check!!!!!!	
	var sSel = LogItemGrid.getSelNo();	
	if( sSel == 0 || sSel == null )
	{
		alert( "����ѡ����Ҫɾ���Ŀ��Ƶ�!" );
		return;
	}	
	fm.ItemID.value = LogItemGrid.getRowColData(sSel-1,1);
	fm.Flag.value = "DOMAIN";
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

function returnParent()
{
    //top.opener.initSelfGrid();
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}
