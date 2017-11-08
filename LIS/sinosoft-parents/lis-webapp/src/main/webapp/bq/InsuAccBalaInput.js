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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
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
  //queryClick();
}




//updateClick事件
function updateClick()
{		        
	         var tRiskCode=fm.RiskCode.value;
	         var tBalaDate=fm.BalaDate.value;

	         	if(tRiskCode==null || tRiskCode=='')
	         {
	         	     alert("险种不能为空");
                 return false;
	         	}	         
	         if(tBalaDate==null || tBalaDate=='')
	         {
	         	     alert("结算日不能为空");
                 return false;
	         	}

           var showStr="正在修改数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
           var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
           //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
		  	var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

           fm.action="./InsuAccBalaSave.jsp";
           document.getElementById("fm").submit(); //提交		
           fm.updatebutton.disabled=true;

}           

//queryClick事件
function queryClick()
{
   var tRiskCode=fm.RiskCode.value;
   
   var sqlid901094741="DSHomeContSql901094741";
var mySql901094741=new SqlClass();
mySql901094741.setResourceName("bq.InsuAccBalaInputSql");//指定使用的properties文件名
mySql901094741.setSqlId(sqlid901094741);//指定使用的Sql的id
mySql901094741.addSubPara(tRiskCode);//指定传入的参数
var tSql_1=mySql901094741.getString();
   
//   var tSql_1="select RISKCODE,(select riskshortname from lmrisk a where  a.riskcode=c.riskcode),PolNo,INSUACCNO,"
//             +" (select cvalidate from lcpol d where d.polno =c.polno),paydate,Money,(select trim(codename) from ldcode where codetype = 'finfeetype' and trim(code)=trim(MONEYTYPE))"
//             +" ,(select sum(money) from lcinsureacctrace r where  r.PolNo = c.PolNo and paydate >=date'1900-01-01' and paydate <=c.paydate) from  lcinsureacctrace  c where paydate like '%01%' and riskcode = '"+tRiskCode+"' order by PolNo,paydate ";             
    var brr = easyExecSql(tSql_1);
    if(!brr)
    {
        LMInsuAccRateGrid.clearData();
        alert("数据库中没有满足条件的数据！");
        return false;
    }
    turnPage.queryModal(tSql_1,LMInsuAccRateGrid);       
}           


