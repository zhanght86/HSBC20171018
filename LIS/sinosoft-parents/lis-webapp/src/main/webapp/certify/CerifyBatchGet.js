var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();



//jinsh------�����ִ���������-------2007-07-02
function afterSubmitDiskInput( FlagStr,content,Result )
{    
	
    showInfo.close();        
   /* if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    }
    else
    { */

        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
     
    //}

  
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
        parent.fraMain.rows = "0,0,0,0,*";
    }
     else
     {
        parent.fraMain.rows = "0,0,0,0,*";
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



//�����������Ϣ
function getin() {
	
    getImportPath();
    fmload.all('ImportPath').value = ImportPath;
    
    if (fmload.all('FileName').value==null||fmload.all('FileName').value=="")
    {
       alert ("�������ļ���ַ!");	
       return false;
    }
    
    if (fmload.all('CertifyState').value==null||fmload.all('CertifyState').value=="")
    {
       alert ("��ѡ��֤״̬!");	
       return false;
    }
    
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fmload.action = "./CerifyBatchGetSave.jsp?ImportPath="+fmload.all('ImportPath').value+"&CertifyState="+fmload.all('CertifyState').value;
    //alert(fmload.action);
    fmload.submit(); //�ύ

}

function getImportPath ()
{
	  var strSQL = "";
	  //strSQL = "select SysvarValue from ldsysvar where sysvar ='CertifyBatchXmlPath'";
	    var mysql=new SqlClass();
		mysql.setResourceName("certify.CerifyBatchGetInputSql");
		mysql.setSqlId("querysqldes1");	
		mysql.addSubPara("1");
		strSQL = mysql.getString();
		
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
	  if (!turnPage.strQueryResult) 
	  {
		    alert("δ�ҵ��ϴ�·��");
		    return;
	  }
	  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  ImportPath = turnPage.arrDataCacheSet[0][0];
	  
	  //alert(ImportPath);
	  
	  //ImportPath="D:\\log\\";
}

function certifyPrint()
{
	    if (document.all('StartDate').value==null||document.all('StartDate').value=="")
    {
       alert ("������ͳ�ƿ�ʼʱ��!");	
       return false;
    }
    
    if (document.all('EndDate').value==null||document.all('EndDate').value=="")
    {
       alert ("������ͳ�ƽ���ʱ��!");	
       return false;
    }
	     var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();        
			// fmload.action = './CertifyPrint.jsp';
          fm.target="f1print";
          showInfo.close();
          document.getElementById("fm").submit();
}

