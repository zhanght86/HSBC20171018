
//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
//turnPage.showTurnPageDiv = 0;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}
//�ύ�����水ť��Ӧ����
function submitForm()
{
	//lockPart("NativeConfig","���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��");
	lockPage("���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��");
	if(beforeSubmit())
	{
		document.all('fmtransact').value='INSERT||MAIN';
    var i = 0;
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
  	//unlockPart("NativeConfig");
  	unLockPage();
  }
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	//unlockPart("NativeConfig");
	unLockPage();
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
    initForm();
    //ִ����һ������
  }
}
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
  if(!verifyInput2())
     return false;
  return true;
} 
          



function queryMaxNoGrid()
{
	var sqlid1="ConfigLDMaxNoInputSql1";
	var mySql1=new SqlClass();
	
	mySql1.setResourceName("config.ConfigLDMaxNoInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
  mySql1.addSubPara('1');//ָ������Ĳ���
	var aSQL=mySql1.getString();
	
  turnPage.queryModal(aSQL, MaxNoGrid);
}               
        
function showRuleDetail()
{
	var tSelNo = MaxNoGrid.getSelNo();
	
	if(tSelNo!=-1)
	{
		var tNoCode=MaxNoGrid.getRowColData(tSelNo-1,1);
		document.all('NoCode').value = tNoCode;
		var tNoName=MaxNoGrid.getRowColData(tSelNo-1,2);
		document.all('NoName').value = tNoName;
		var tShowRule=MaxNoGrid.getRowColData(tSelNo-1,3);
		document.all('ShowRule').value = tShowRule;
		var tStartDate=MaxNoGrid.getRowColData(tSelNo-1,4);
		document.all('StartDate').value = tStartDate;
		var tEndDate=MaxNoGrid.getRowColData(tSelNo-1,5);
		document.all('EndDate').value = tEndDate;
	}
}


function configRule()
{
	var tNoCode = document.all('NoCode').value;
	if(tNoCode==null||tNoCode=='')
	{
		alert('���ȱ������֮��������!');
		return false;
		
	}
	var tStartDate = document.all('StartDate').value;
	var urlStr = "./ConfigLDMaxNoRuleMain.jsp?NoCode="+tNoCode+"&LimitType=0"+"&StartDate="+tStartDate;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:800px;dialogHeight:450px;scroll:no");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=800;      //�������ڵĿ��; 
	var iHeight=450;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

function configRuleLimit()
{
	var tNoCode = document.all('NoCode').value;
	if(tNoCode==null||tNoCode=='')
	{
		alert('���ȱ������֮��������!');
		return false;
		
	}
	var tStartDate = document.all('StartDate').value;
	var urlStr = "./ConfigLDMaxNoRuleMain.jsp?NoCode="+tNoCode+"&LimitType=1"+"&StartDate="+tStartDate;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:800px;dialogHeight:450px;scroll:no");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=800;      //�������ڵĿ��; 
	var iHeight=450;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

function query()
{
	var sqlid2="ConfigLDMaxNoInputSql2";
	var mySql2=new SqlClass();
	
	mySql2.setResourceName("config.ConfigLDMaxNoInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
  mySql2.addSubPara('1');//ָ������Ĳ���
  mySql2.addSubPara(document.all('NoCodeQuery').value);//ָ������Ĳ���
  mySql2.addSubPara(document.all('NoNameQuery').value);//ָ������Ĳ���
  
	var aSQL=mySql2.getString();
	
  turnPage.queryModal(aSQL, MaxNoGrid);
}

$(function(){
		$('#w').window('close');
});
