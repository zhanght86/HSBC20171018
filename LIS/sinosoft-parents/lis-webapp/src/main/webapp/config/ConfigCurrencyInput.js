//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
turnPage.showTurnPageDiv = 0;
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
  if(!verifyInput())
     return false;
  return true;
} 
          



function queryCurrencyGrid()
{
	var sqlid1="ConfigCurrencyInputSql1";
	var mySql1=new SqlClass();
	
	mySql1.setResourceName("config.ConfigCurrencyInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
  mySql1.addSubPara('1');//ָ������Ĳ���
	var aSQL=mySql1.getString();
	
  turnPage.queryModal(aSQL, CurrencyGrid,1,0,100);
}               
        
