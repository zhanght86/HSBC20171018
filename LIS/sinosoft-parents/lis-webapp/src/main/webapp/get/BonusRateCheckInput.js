// 该文件中包含客户端需要处理的函数和事件
//程序名称：
//程序功能：分红险系数校验界面
//创建日期：2008-11-09 17:55:57
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
  	showInfo.close();
  }
  queryClick();
}
       

//updateClick事件
function updateClick()
{  	
   	var tFiscalYear=fm.FiscalYear.value; 
   	
   	var sqlid830155948="DSHomeContSql830155948";
var mySql830155948=new SqlClass();
mySql830155948.setResourceName("get.BonusRateCheckInputSql");//指定使用的properties文件名
mySql830155948.setSqlId(sqlid830155948);//指定使用的Sql的id
mySql830155948.addSubPara(fm.FiscalYear.value);//指定传入的参数
var tSQL_0=mySql830155948.getString();
   	 	
//   	var tSQL_0="select FiscalYear from LOBonusMain  where FiscalYear='"+fm.FiscalYear.value+"'";
   	var tResult_0=easyExecSql(tSQL_0,1,0,1);
   	if(tFiscalYear!=tResult_0)
   	{
   		 alert("红利主表数据不存在，请重新输入");
   		 return;   		
   		} 		
     var showStr="正在修改数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
     var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     fm.transact.value="update"; 
     fm.action="./BonusRateCheckSave.jsp";
     document.getElementById("fm").submit(); //提交	
	
}           

//queryClick事件
function queryClick()
{
    var sqlid830160055="DSHomeContSql830160055";
var mySql830160055=new SqlClass();
mySql830160055.setResourceName("get.BonusRateCheckInputSql");//指定使用的properties文件名
mySql830160055.setSqlId(sqlid830160055);//指定使用的Sql的id
mySql830160055.addSubPara();//指定传入的参数
var tSql_0=mySql830160055.getString();
    
//    var tSql_0="select FiscalYear,groupid, nvl(DistributeValue,0),nvl(DistributeRate,0),nvl(BonusCoefSum,0),decode(state,'0','未复核','1','已复核')  from  LOBonusMain order by FiscalYear";

    	 turnPage.queryModal(tSql_0, LOBonusMainGrid);             
}           

         

function displayInfo()
{

	 var tLength=LOBonusMainGrid.mulLineCount;
	 var tCow=0;

	  tCow=LOBonusMainGrid.getSelNo()	
	  tCow=tCow-1; 
    fm.FiscalYear.value =LOBonusMainGrid.getRowColData(tCow,1);
    fm.DistributeValue.value=LOBonusMainGrid.getRowColData(tCow,3);
    fm.DistributeRate.value =LOBonusMainGrid.getRowColData(tCow,4);
    fm.BonusCoefSum.value = LOBonusMainGrid.getRowColData(tCow,5);		
}
	

