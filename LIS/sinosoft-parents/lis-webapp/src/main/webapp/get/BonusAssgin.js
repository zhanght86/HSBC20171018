//               该文件中包含客户端需要处理的函数和事件
//程序名称：
//程序功能：分红险分配批处理
//创建日期：2008-11-09 17:55:57
//创建人  ：彭送庭
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  queryClick();
  if (LOBonusPolGrid.mulLineCount==0 )
  { 
  	content="分配失败,分配的失败原因可能是：1,保单被挂起,,请及时解挂,挂起原因可能是此保单已经申请保全项目! \n2,年度的分红方案不存在 \n3,保单已经终止 \n4,保单红利应分日未到等等，请核实!";            
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
  	content="分配成功";
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
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
  //queryClick();
}




//updateClick事件
function updateClick()
{		        
	         var tFiscalYear=fm.FiscalYear.value;
	         var tContNo=fm.ContNo.value;
	         	if(tFiscalYear==null || tFiscalYear=='')
	         {
	         	     alert("红利分配会计年度不能为空");
                 return false;
	         	}	
	        // var tNextFisCalYear="";
	        // var sql="select max(FiscalYear) from lobonuspol where contno='"+tContNo+"' and bonusflag='1' and  agetdate is not null";
    	    // var arrResult = easyExecSql(sql);
    	    // //说明保单是首次分红
    	    // //则查询已经计算未分配的红利
    	    // var tMaxFisCalYear="";
    	    // if(arrResult==null ||arrResult[0][0]=='')
    	    // {
    	    // 	var tSql="select min(FiscalYear) from lobonuspol where contno='"+tContNo+"' and bonusflag='0' and  agetdate is  null";
    	    //  var tarrResult = easyExecSql(tSql);	
    	    //  if(tarrResult==null || tarrResult[0][0]=='')
    	    //  {
    	    //  		 alert("查询红利历史分红年度出错");
          //       return false;
    	    //  	}else
    	    //  		{
    	    //  			tMaxFisCalYear=tarrResult[0][0];
    	    //  			tNextFisCalYear=(parseInt(tarrResult[0][0])+1).toString();
    	    //  		}
    	    // 	}else
    	    // 		{   tMaxFisCalYear=arrResult[0][0]; 	     			
    	    //    	  tNextFisCalYear=(parseInt(arrResult[0][0])+1).toString(); 			
    	    // 			}
    	    // 	if(tNextFisCalYear!=tFiscalYear)
    	    // 	{
    	    // 			 alert("红利分配年度输入有误，本保单已分红年度最大值为"+tMaxFisCalYear+"，应该是"+tNextFisCalYear);
          //       return false;
    	    // 		
    	    // 		}		
	         	if(tContNo==null || tContNo=='')
	         {
	         	     alert("保单号不能为空，请重新输入");
                 return false;
	         	}	  
	          queryClick();
	          if(LOBonusPolGrid.mulLineCount>0)
            {   
	         	     alert("保单此分红年度已经分配，无需重复分配！");
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
           fm.action="./BonusAssginSave.jsp";
           document.getElementById("fm").submit(); //提交		
           fm.updatebutton.disabled=true;

}           

//queryClick事件
function queryClick()
{
   var tFiscalYear=fm.FiscalYear.value;
   
   var sqlid830160838="DSHomeContSql830160838";
var mySql830160838=new SqlClass();
mySql830160838.setResourceName("get.BonusAssginInputSql");//指定使用的properties文件名
mySql830160838.setSqlId(sqlid830160838);//指定使用的Sql的id
mySql830160838.addSubPara(fm.FiscalYear.value);//指定传入的参数
mySql830160838.addSubPara(fm.ContNo.value);//指定传入的参数
var tSql_1=mySql830160838.getString();

   
//   var tSql_1=" select polno,contno,FiscalYear,BONUSMONEY,decode(BONUSFLAG,'1','已分配','0','已计算'),BONUSCOEF,SGETDATE,"
//             +" decode(BONUSGETMODE,'1','累计生息','2','现金','3','抵交续期保费','5','增额交清') from LOBonusPol where FiscalYear='"+fm.FiscalYear.value+"' AND contno='"+fm.ContNo.value+"'  and BONUSFLAG='1' order by PolNo,SGETDATE "; 
    turnPage.queryModal(tSql_1,LOBonusPolGrid);       
}           


