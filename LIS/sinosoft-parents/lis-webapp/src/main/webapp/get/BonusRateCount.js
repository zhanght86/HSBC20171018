//               该文件中包含客户端需要处理的函数和事件
//程序名称：
//程序功能：保单分红险系数计算
//创建日期：2008-11-09 17:55:57
//创建人  ：彭送庭
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 


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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
  }
  //queryClick();
}




//updateClick事件
function updateClick()
{		        
	         var tFiscalYear=fm.FiscalYear.value;

	         	if(tFiscalYear==null || tFiscalYear=='')
	         {
	         	     alert("红利分配会计年度不能为空");
                 return false;
	         	}	         
           var showStr="正在计算数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
           var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
           var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
           fm.action="./BonusRateCountSave.jsp";
           document.getElementById("fm").submit(); //提交		
           fm.updatebutton.disabled=true;

}           

//queryClick事件
function queryClick()
{
   var tFiscalYear=fm.FiscalYear.value;
   
   var sqlid830160400="DSHomeContSql830160400";
var mySql830160400=new SqlClass();
mySql830160400.setResourceName("get.BonusRateCountInputSql");//指定使用的properties文件名
mySql830160400.setSqlId(sqlid830160400);//指定使用的Sql的id
mySql830160400.addSubPara(fm.FiscalYear.value);//指定传入的参数
var tSql_1=mySql830160400.getString();
   
//   var tSql_1=" select polno,contno,FiscalYear,BONUSMONEY,decode(BONUSFLAG,'1','已领','0','未领'),BONUSCOEF,SGETDATE,"
//             +" decode(BONUSGETMODE,'1','累计生息','2','现金','3','抵交续期保费','5','增额交清') from LOBonusPol where FiscalYear='"+fm.FiscalYear.value+"' order by PolNo,SGETDATE "; 
    turnPage.queryModal(tSql_1,LOBonusPolGrid);       
}           


