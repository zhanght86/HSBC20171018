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
	var iHeight=250;     //�������ڵĸ߶�; 
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
   document.getElementById("fm").submit();
 
  var filename="EdorOutDayDone_"+document.all('ManageCom').value+"_"+document.all('StartDate').value+"_"+document.all('EndDate').value+"_"+document.all('SaleChnl').value+".xls";

	fm.FileName.value=filename; 
	//fm.FileName.value="ReAgentAutoDown_86_200510.xls";
//	var strSql = "select SysVarValue from LDSysVar where SysVar = 'TKReportCreat'";
	var strSql = "";
	var sqlid2="EdorOutDayDoneSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.EdorOutDayDoneSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	strSql=mySql2.getString();
	
   filePath = easyExecSql(strSql);
   //filePath="d:/zhujc/";
   fm.Url.value=filePath;
   fm.fmtransact.value = "download";
   fm.action="./EdorOutDayDoneSave.jsp";
    document.getElementById("fm").submit(); 
}
 
function downAfterSubmit(cfilePath,fileName) { 
	showInfo.close();
  //alert("adadad");
  fileUrl.href = cfilePath + fileName; 
  //alert(fileUrl.href);
  //fileUrl.click();
  fm.action="../agentprint/download.jsp?file="+fileUrl.href;
   document.getElementById("fm").submit();
}
 
function fmsubmit()
{

	if( verifyInput() == false ) 
		return false; 
 	
		
		
		//parent.fraInterface.document.all('compute').disabled = true;
		
		var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
   document.getElementById("fm").submit();

 
	var filename="EdorOutDayDone_"+document.all('ManageCom').value+"_"+document.all('StartDate').value+"_"+document.all('EndDate').value+"_"+document.all('SaleChnl').value+".xls";

	fm.FileName.value=filename; 
	//fm.FileName.value="ReAgentAutoDown_86_200510.xls";
	
//	var strSql = "select SysVarValue from LDSysVar where SysVar = 'TKReportCreat'";
	var strSql = "";
	var sqlid1="EdorOutDayDoneSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorOutDayDoneSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	strSql=mySql1.getString();
    filePath = easyExecSql(strSql);
   //filePath="d:/zhujc/";
   fm.Url.value=filePath;
	����fm.fmtransact.value = "Create";
	    fm.action="./EdorOutDayDoneSave.jsp";
	     document.getElementById("fm").submit();
}

//ȷ�Ͽ�Ļص�����
function ConfirmSelect()
{
	 showInfo.close();
	 
	 if(confirm("�����Ѿ����ɣ��Ƿ����¼���?������㽫����ԭʼ���ݡ�"))
	 {
	 	   var i = 0;
       var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
       var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        document.getElementById("fm").submit();
	 	   //��ȷ��֮����ֵ��Ϊ1 �𵽿��ص�����
	 	   document.all('myconfirm').value = "1";
	 	   //�൱��window.location�ض�������
	 	    document.getElementById("fm").submit();
	  }
	else
		{
		}
}



	
