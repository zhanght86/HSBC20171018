//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;

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
    parent.fraInterface.document.all('compute').disabled = false;
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
    parent.fraInterface.document.all('compute').disabled = false;
  }
}

function fileDownload() { 
	
 if( verifyInput() == false ) 
		return false; 
		var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus(); 
	var filename="GorupScanDetil_"+document.all('ManageCom').value+"_"+document.all('PrtNO').value+"_"+document.all('StartDay').value+"_"+document.all('EndDay').value+"_"+document.all('tjtype').value+".xls";

	fm.FileName.value=filename; 
	//fm.FileName.value="ReAgentAutoDown_86_200510.xls";
//	var strSql = "select SysVarValue from LDSysVar where SysVar = 'TKReportCreat'";
	
	var sqlid1="GorupScanDetilSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("agentprint.GorupScanDetilSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	var strSql=mySql1.getString();
	
   filePath = easyExecSql(strSql);
 // filePath="d:/test/";
   fm.Url.value=filePath;
   fm.fmtransact.value = "download";
   fm.action="./GorupScanDetilSave.jsp";
   document.getElementById("fm").submit();}
 
function downAfterSubmit(cfilePath,fileName) { 
	showInfo.close();
  //alert("adadad");
  fileUrl.href = cfilePath + fileName; 
  //alert(fileUrl.href);
  //fileUrl.click();
  fm.action="./download.jsp?file="+fileUrl.href;
  document.getElementById("fm").submit();}
 
function fmsubmit()
{

	if( verifyInput() == false ) 
		return false; 
 	
		
		
		//parent.fraInterface.document.all('compute').disabled = true;
		
		var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 
	var filename="GorupScanDetil_"+document.all('ManageCom').value+"_"+document.all('PrtNO').value+"_"+document.all('StartDay').value+"_"+document.all('EndDay').value+"_"+document.all('tjtype').value+".xls";

	fm.FileName.value=filename; 
	//fm.FileName.value="ReAgentAutoDown_86_200510.xls";
//	var strSql = "select SysVarValue from LDSysVar where SysVar = 'TKReportCreat'";
	
	var sqlid1="GorupScanDetilSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("agentprint.GorupScanDetilSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	var strSql=mySql1.getString();
	
   filePath = easyExecSql(strSql);
 // filePath="d:/test/";
   fm.Url.value=filePath;
	����fm.fmtransact.value = "Create";
	    fm.action="./GorupScanDetilSave.jsp";
	    document.getElementById("fm").submit();}

//ȷ�Ͽ�Ļص�����
function ConfirmSelect()
{
	 showInfo.close();
	 
	 if(confirm("�����Ѿ����ɣ��Ƿ����¼���?������㽫����ԭʼ���ݡ�"))
	 {
	 	   var i = 0;
       var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
       var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	 	   //��ȷ��֮����ֵ��Ϊ1 �𵽿��ص�����
	 	   document.all('myconfirm').value = "1";
	 	   //�൱��window.location�ض�������
	 	   document.getElementById("fm").submit();}
	else
		{
		}
}



	
