//               该文件中包含客户端需要处理的函数和事件
//程序名称：
//程序功能：万能利率输入界面
//创建日期：2007-11-09 17:55:57
//创建人  ：彭送庭
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{

  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    showInfo.close();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    showInfo.close();
  }
  queryClick();
}



//addClickClick事件
//function addClick()
//{

//	var tClag=fm.RiskCode.value;
//	if(tClag==null || tClag=='')
//	{
//		alert("数据不完整，请重新输入");
//		return;
//		}
//
//   var tFlag=false;
//   var tLlag=false;   
//   var tRlag=false;
//   var tIlag=false;
//   var tBalaDate=fm.BalaDate.value;
//
//   tBalaDate=tBalaDate.substring(8,tBalaDate.length);
//   if('01'!=tBalaDate)
//   {
//   	       if (window.confirm("结算日不正确，是否继续?"))
//          {
//            tFlag=true;
//          }else{
//          	return ;
//          }
//   	}
//   
//   var tRate=fm.Rate.value;
//   if(tRate>0.05 || tRate<0.0 || tRate=='' || tRate==null)
//   {
//          if (window.confirm("利率输入不正确或过大，是否继续?"))
//          {
//             tLlag=true;
//          }else{
//          	return ;
//          }
//   	}
//   	
//   	var tRiskCode=fm.RiskCode.value;
//   	var tSQL="select RiskCode from lmrisk where RiskCode='"+tRiskCode+"'";
//   	var tResult=easyExecSql(tSQL,1,0,1);
//   	if(tResult!='' || tResult!=null)
//   	{
//   		 tRlag=true;
//   		}
//   else{
//   		alert("险种不存在，请重新输入");   
//   		return;
//   			}
//   	
//   	var tInsuAccNo=fm.InsuAccNo.value;  	
//   	var tSQL_0="select InsuAccNo from lmrisktoacc where InsuAccNo='"+tInsuAccNo+"'";
//   	var tResult_0=easyExecSql(tSQL_0,1,0,1);
//   	if(tResult_0!='' || tResult_0!=null)
//   	{
//   		 tIlag=true;
//   		}
//   	else{
//   		 alert("帐户不存在，请重新输入");  
//   		 return; 			
//   			}
//   		
//     if(tLlag==true&&tFlag==true&&tRlag==true&&tIlag==true)
//           {
//           var showStr="正在增加数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//           var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//           showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//           fm.transact.value="insert"; 
//           fm.action="./AccRateSave.jsp";
//           document.getElementById("fm").submit(); //提交
//          }
//
//
//}           

//updateClick事件
function updateClick()
{	
		   var tFlag=fm.RiskCode.value;
	        if(tFlag==null || tFlag=='')
	      {
	         alert("请选择您要复核的记录！");
		       return;
		     }	        
           var showStr="正在修改数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
           var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//           showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
           var name='提示';   //网页名称，可为空; 
           var iWidth=550;      //弹出窗口的宽度; 
           var iHeight=250;     //弹出窗口的高度; 
           var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
           var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
           showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

           showInfo.focus();

           fm.transact.value="update"; 
           fm.action="./AccRateCheckSave.jsp";
           document.getElementById("fm").submit(); //提交		
}           

//queryClick事件
function queryClick()
{
//    var tSql_0="select RISKCODE,(select riskshortname from lmrisk a where  a.riskcode=c.riskcode),INSUACCNO,"
//             +" BALADATE,RATE,RATEINTV,SRATEDATE,ARATEDATE,(select codename from ldcode b where b.code"
//             +" =ratestate and codetype='ratestate') from  LMInsuAccRate c  order by BALADATE desc";
    
    	var sqlid1="AccRateCheckInputSql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.AccRateCheckInputSql");
	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	 	var tSql_0 = mySql1.getString();
    
//    var tSql_1="select RISKCODE,(select riskshortname from lmrisk a where  a.riskcode=c.riskcode),INSUACCNO,"
//             +" ' ',RATE,RATEINTV,RATESTARTDATE,RATEENDDATE,(select codename from ldcode b where b.code"
//             +" =ratestate and codetype='ratestate') from  LMAccGuratRate  c order by RATESTARTDATE desc";
    
	    var sqlid2="AccRateCheckInputSql2";
	 	var mySql2=new SqlClass();
	 	mySql2.setResourceName("sys.AccRateCheckInputSql");
	 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
	 	var tSql_1 = mySql2.getString();
    
    if(fm.AccType.value==1)
    {
    	 turnPage.queryModal(tSql_0, LMInsuAccRateGrid);
    	}
    	else{
    	turnPage.queryModal(tSql_1, LMInsuAccRateGrid);
    		}             
         
}           

//deleteClick事件
//function deleteClick()
//{
//	var tFlag=fm.RiskCode.value;
//	if(tFlag==null || tFlag=='')
//	{
//		alert("请选择您要删除的记录！");
//		return;
//		}
//	else{
//		if(window.confirm("删除不可恢复，是否继续?")){
//		  var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//      showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//		var name='提示';   //网页名称，可为空; 
//		var iWidth=550;      //弹出窗口的宽度; 
//		var iHeight=250;     //弹出窗口的高度; 
//		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
//		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
//		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
//		
//		showInfo.focus();

//      fm.transact.value="delete"; 
//      fm.action="./AccRateSave.jsp";
//      document.getElementById("fm").submit(); //提交
//     }
//     else{
//     	return ;
//     	}
//			}
//
//}           

function displayInfo()
{
	 var tFlag =fm.AccRate[1].value
	 var tLength=LMInsuAccRateGrid.mulLineCount;
	 var tCow=0;
	  if(fm.AccRate[0].checked==true) //回显结算利率信息
	  {

	  tCow=LMInsuAccRateGrid.getSelNo()
	  tCow=tCow-1;  	
    fm.RiskCode.value =LMInsuAccRateGrid.getRowColData(tCow,1);
    fm.InsuAccNo.value=LMInsuAccRateGrid.getRowColData(tCow,3);
    fm.BalaDate.value =LMInsuAccRateGrid.getRowColData(tCow,4);
    fm.Rate.value = LMInsuAccRateGrid.getRowColData(tCow,5);
    fm.SRateDate.value = LMInsuAccRateGrid.getRowColData(tCow,7);
    fm.ARateDate.value =LMInsuAccRateGrid.getRowColData(tCow,8);
    }
   	if(fm.AccRate[1].checked==true) //回显保证利率信息
   	{
	  tCow=LMInsuAccRateGrid.getSelNo()	
	  tCow=tCow-1; 
    fm.RiskCode.value =LMInsuAccRateGrid.getRowColData(tCow,1);
    fm.InsuAccNo.value=LMInsuAccRateGrid.getRowColData(tCow,3);
    fm.BalaDate.value =LMInsuAccRateGrid.getRowColData(tCow,4);
    fm.GruRate.value = LMInsuAccRateGrid.getRowColData(tCow,5);
    fm.RateStartDate.value = LMInsuAccRateGrid.getRowColData(tCow,7);
    fm.RateEndDate.value =LMInsuAccRateGrid.getRowColData(tCow,8);   		
   	  	}

	
	}
	

