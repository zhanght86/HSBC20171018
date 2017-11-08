//程序名称：UWscoreRReport.js
//程序功能：核保生调评分
//创建人  ：ln
//创建日期：2008-10-24
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
//提交，保存按钮对应操作
function submitForm()
{ 
    calcuScoreH();       
	
  	if(document.all('SScoreH').value != document.all('SScore').value)
	{
		alert("请重新获取合计扣分！");
    	return;
  	}
  	if(document.all('AScoreH').value != document.all('AScore').value)
	{
		alert("请重新获取合计加分！");
    	return;
  	} 	
  	
  	if(document.all('ScoreH').value != document.all('Score').value)
	{
	   // alert(document.all('ScoreH').value );
		alert("请重新获取得分！");
    	return;
  	}
  	
  	fm.action = "./RnewUWscoreRReportChk.jsp";

  //alert(tempSubMissionID);
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  fm.submit(); //提交 
}

//提交，打印按钮对应操作
function printResult()
{
  //alert("print");
  fm.action = "./RReportScorePrint.jsp";
  fm.target = "../f1print";
  fm.submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showInfo.close();
       
  }
  else
  { 
		var content="操作成功！";
  	var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  	//parent.close();
   // top.close();   
    initHide();
    easyQueryClickItem();
  }
}

//生调基本信息查询
function easyQueryClickItem()
{
    if(tContNo != "")
    {
//      var strsql= "select (select prtno from lccont where contno=l.contno),managecom,replyoperator,(select username from lduser where usercode=replyoperator) from RnewRReport l where 1=1 and ContNo = '"+tContNo+"' and PrtSeq='"+tPrtSeq+"'";			
      var strsql = "";
      var sqlid1="RnewUWscoreRReportSql1";
      var mySql1=new SqlClass();
      mySql1.setResourceName("uw.RnewUWscoreRReportSql"); //指定使用的properties文件名
      mySql1.setSqlId(sqlid1);//指定使用的Sql的id
      mySql1.addSubPara(tContNo);//指定传入的参数
      mySql1.addSubPara(tPrtSeq);//指定传入的参数
      strsql=mySql1.getString();
	  var arrReturn = new Array();
        arrReturn = easyExecSql(strsql);
       
       if(arrReturn!=null)
       {
        document.all('PrtNo').value = arrReturn[0][0];
        document.all('ManageCom').value = arrReturn[0][1];	
		document.all('CustomerNo').value = arrReturn[0][2];	
		document.all('Name').value = arrReturn[0][3];	
		
		//alert(1);
		//生调评审基本信息查询
		if(tContNo != "")
       {
//      var strsqlt= "select sscore,ascore,score,conclusion from RnewScoreRReport  where 1=1 and ContNo = '"+tContNo+"' and customerno='"+arrReturn[0][2]+"'";			
      var strsqlt = "";
      var sqlid2="RnewUWscoreRReportSql2";
      var mySql2=new SqlClass();
      mySql2.setResourceName("uw.RnewUWscoreRReportSql"); //指定使用的properties文件名
      mySql2.setSqlId(sqlid2);//指定使用的Sql的id
      mySql2.addSubPara(tContNo);//指定传入的参数
      mySql2.addSubPara(arrReturn[0][2]);//指定传入的参数
      strsqlt=mySql2.getString();
	  var arrReturnt = new Array();
        arrReturnt = easyExecSql(strsqlt);
       //alert(2);
       if(arrReturnt!=null)
       {
        document.all('SScore').value = arrReturnt[0][0];
        document.all('AScore').value = arrReturnt[0][1];	
		document.all('Score').value = arrReturnt[0][2];	
		document.all('Conclusion').value = arrReturnt[0][3];	
		//alert(3);
//		var strsqll= "select assessitem,score from RnewScoreRReportSub  where 1=1 and ContNo = '"+tContNo+"' and customerno='"+arrReturn[0][2]+"'";			
		var strsqll = "";
	      var sqlid3="RnewUWscoreRReportSql3";
	      var mySql3=new SqlClass();
	      mySql3.setResourceName("uw.RnewUWscoreRReportSql"); //指定使用的properties文件名
	      mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	      mySql3.addSubPara(tContNo);//指定传入的参数
	      mySql3.addSubPara(arrReturn[0][2]);//指定传入的参数
	      strsqll=mySql3.getString();
	  var arrReturnl = new Array();
        arrReturnl = easyExecSql(strsqll);
       //alert(33);
       if(arrReturnl!=null)
       { 
         var i=0;
         var name="";
         var name1="";
         for (i=1 ;i<7 ;i++)
         {           
           name1="Addition"+i;
           //alert(name1);
           if(arrReturnl[i-1][1]!='0')
               document.all(name1).checked = true;
           document.all(arrReturnl[i-1][0]).value = arrReturnl[i-1][1];
         }    
         
         for (i=1 ;i<9 ;i++)
         {           
           name="Subtraction"+i;
           //alert(name);
           if(arrReturnl[i+5][1]!='0')
               document.all(name).checked = true;
           document.all(arrReturnl[i+5][0]).value = arrReturnl[i+5][1];
         }   
       }
       }
       else
          fm.Print.disabled = true;
    }
       }
       
    }
}



