var mDebug="1";
var mOperate="";
var showInfo;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
window.onfocus=myonfocus;
var ImportPath;
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


function submitForm()
{
	    if(document.all('FeeSum').value=="")
    {
    	alert("�����뽻���ܽ��");
	  	return ;
    	}
    	if(document.all('BankCode').value=="")
    	{
    		alert("��ѡ���տ�����");
    		return ;
    		}
		if(document.all('FileName').value=="")
	  {
	  	alert("��ѡ����Ҫ�ϴ����ļ�");
	  	return ;
	  }
	  var i = 0;
		var ImportFile = document.all('FileName').value.toLowerCase();
		//Ӧ�ͻ�Ҫ�����Ӷ�ģ���ʽ�����ƣ�zy
		var j = ImportFile.indexOf(".xls");
		if(j==-1)
		{
			alert("�����excleģ��");
		  return ;
		}
		
		
		getImportPath();
   	   document.all('conf').disable=true;
	  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	  var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus();   	
		fm.action = "./YBTFinfeeSureSave.jsp?ImportPath="+ImportPath+"&ImportFile="+ImportFile;
	  document.getElementById("fm").submit(); //�ύ


}


function getImportPath ()
{
		// ��дSQL���
	var strSQL = "";

	//strSQL = "select SysvarValue from ldsysvar where sysvar ='YBTSavePath'";
	//alert("getImportPath");
	
	var mysql=new SqlClass();
	mysql.setResourceName("finfee.YBTFinfeeSureInputSql");
	mysql.setSqlId("YBTSavePath");
			 
	turnPage.strQueryResult  = easyQueryVer3(mysql.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	alert("δ�ҵ��ϴ�·�����뵽ldsysvar��鿴sysvar ='YBTSavePath'�����ݼ�¼��Ϣ");
    return;
	}
	
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

	//��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  ImportPath = turnPage.arrDataCacheSet[0][0];
  
}


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
}