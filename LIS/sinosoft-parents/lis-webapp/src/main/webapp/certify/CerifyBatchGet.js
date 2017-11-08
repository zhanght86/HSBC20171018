var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();



//jinsh------导入的执行完后调这个-------2007-07-02
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
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
     
    //}

  
}

//显示frmSubmit框架，用来调试
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



//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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



//导入出险人信息
function getin() {
	
    getImportPath();
    fmload.all('ImportPath').value = ImportPath;
    
    if (fmload.all('FileName').value==null||fmload.all('FileName').value=="")
    {
       alert ("请输入文件地址!");	
       return false;
    }
    
    if (fmload.all('CertifyState').value==null||fmload.all('CertifyState').value=="")
    {
       alert ("请选择单证状态!");	
       return false;
    }
    
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fmload.action = "./CerifyBatchGetSave.jsp?ImportPath="+fmload.all('ImportPath').value+"&CertifyState="+fmload.all('CertifyState').value;
    //alert(fmload.action);
    fmload.submit(); //提交

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
		    alert("未找到上传路径");
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
       alert ("请输入统计开始时间!");	
       return false;
    }
    
    if (document.all('EndDate').value==null||document.all('EndDate').value=="")
    {
       alert ("请输入统计结束时间!");	
       return false;
    }
	     var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();        
			// fmload.action = './CertifyPrint.jsp';
          fm.target="f1print";
          showInfo.close();
          document.getElementById("fm").submit();
}

