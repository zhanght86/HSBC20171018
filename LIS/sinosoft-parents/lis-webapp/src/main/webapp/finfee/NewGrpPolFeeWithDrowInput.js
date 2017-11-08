//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sumMoney=0;
var flag = 0;
var sqlresourcename = "finfee.NewGrpPolFeeWithDrowInputSql";
//提交，保存按钮对应操作
function submitForm()
{
  if(ManageCom.length != 6)
 {
	//alert(manageCom);
	alert("溢交退费操作只能在六位机构下进行，请重新选择六位机构登陆！");
	return false;
 }
    if(!GetRecord())
	{
		alert("请先选择付费纪录！");
		return false;	
	}
	
	if(!check())
    {	
  	    return false;  
    }
  
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 lockScreen('lkscreen');  
  fm.action= "./NewGrpPolFeeWithDrow.jsp";
  document.getElementById("fm").submit(); //提交

}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();

	/*
    if(typeof(showInfo)=="object")
	{
		showInfo.close();
		if(typeof(showInfo.parent)=="object")
		{
			showInfo.parent.focus();
			if(typeof(showInfo.parent.parent)=="object")
			{
				showInfo.parent.parent.blur();
			}
		}
  	}
  	*/
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	 //divDifShow.style.display = 'none';
	// document.all('Dif').value=0;
  }
  // document.all("BankCodeName").value ="";
  // document.all("BankAccNo").value ="";
 //  document.all("BankCode").value="";
 //  document.all("Confirm").disabled=false;
   	queryClick();
	 
}

function check()
{	

	var i = 0;	
	var sum = 0;
	var tRow = -1;
	var selno = FinFeeWithDrawGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return false;
	}	
/*
   if(FinFeeWithDrawGrid.getRowColData(selno,6) == null || FinFeeWithDrawGrid.getRowColData(selno,6) == '')
   {      
   	      if(document.all("BankCodeName").value==""||document.all("BankCodeName").value==null)
   	      {
    			alert("保单"+FinFeeWithDrawGrid.getRowColData(i,1)+"没有投保单位开户银行信息，请录入后再作退费处理！");
    			return false;
    		  }
   }//两处都没有银行信息
   if(FinFeeWithDrawGrid.getRowColData(selno,8) == null || FinFeeWithDrawGrid.getRowColData(selno,8) == '')
   {      if(document.all("BankAccNo").value==""||document.all("BankAccNo").value==null)
   	      {
    			alert("保单"+FinFeeWithDrawGrid.getRowColData(selno,1)+"没有投保单位银行帐户信息，请录入后再作退费处理！");
    			return false;
    		  }
   }//两处都没有帐户信息
   if(document.all("BankCodeName").value!=""&&document.all("BankCodeName").value!=null)
   {
   	if(document.all("BankCode").value==null||document.all("BankCode").value=="")
   	{
   		alert("您已经输入了开户银行信息，请确认!");
   		return false;
   	}
   	
   }//没有点击确认的校验
   	*/
				
  
	return true;
}

function queryClick()
{
	if(ManageCom.length != 6)
 {
	//alert(manageCom);
	alert("溢交退费操作只能在六位机构下进行，请重新选择六位机构登陆！");
	return false;
 }
	initFinFeeWithDrawGrid();
		
	var strSQL = "select q.grpcontno,q.grpname,q.dif,q.signdate, "
				+" case when q.bankcode is not null then (select codename from ldcode where codetype = 'bank' and code = q.bankcode ) else '' end,q.bankaccno,q.managecom  "
				+ " from lcgrpcont q where q.ManageCom like '"+ManageCom+"%%' "
				+ " and q.dif>0 "
				+ getWherePart( 'q.GrpContNo','GrpContNo' )
	           	+ getWherePart( 'q.PrtNo','PrtNo' )
	           	+ getWherePart( 'q.AgentCode','AgentCode' );
	          	
/*	   var strSQL ="";        	
		var sqlid1="NewGrpPolFeeWithDrowInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(ManageCom);
		mySql1.addSubPara(fm.GrpContNo.value);
		mySql1.addSubPara(fm.PrtNo.value);
		mySql1.addSubPara(fm.AgentCode.value);
	           	
	      strSQL =   mySql1.getString();   */	
	           	if(fm.GrpName.value !="")
			     {
			    	 strSQL+= " and q.grpname like '%%"+fm.GrpName.value+"%%' ";
			     }
			    
				strSQL+=" order by q.signdate desc,q.grpcontno desc";
				//prompt("",strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  

                  
    //判断是否查询成功
    if (!turnPage.strQueryResult) 
    {      
	    alert("未查到满足条件的结果！");
	    return false;
    }
	
	turnPage.pageLineNum = 10;
    //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
    //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
    turnPage.pageDisplayGrid = FinFeeWithDrawGrid;    
          
    //保存SQL语句
    turnPage.strQuerySql     = strSQL; 
  
    //设置查询起始位置
    turnPage.pageIndex       = 0;  
  
    //在查询结果数组中取出符合页面显示大小设置的数组
    var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}


function queryAgent()
{
    window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+ManageCom, "AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/*********************************************************************
 *  查询业务人员编码后设置业务人员编码
 *  描述:查询业务人员编码
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterQuery2(arrResult)
{
    if(arrResult != null)
    {
        fm.AgentCode.value  = arrResult[0][0];    //显示代理人代码
    }
}

function GetRecord()
{
	var i = 0;	
	var sum = 0;
	var tRow = -1;

	//判定是否选择了被保险人
	var selno = FinFeeWithDrawGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return false;
	}	
		return true;	

}

function creatBankCode()
{
	if(document.all("BankCodeName").value == "")
	{
		alert("请输入开户行名称!");
		return false;
	}
	var selno = FinFeeWithDrawGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条记录为其添加或修改银行信息！");
	      return false;
	}	
	var ManageCom = FinFeeWithDrawGrid.getRowColData(selno,8);
	var BankCodeName = document.all("BankCodeName").value;	

	//var sql="select code from ldcode where codetype='bank' and codename = '"+document.all("BankCodeName").value+"' ";
      	
		var sqlid2="NewGrpPolFeeWithDrowInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(document.all("BankCodeName").value);

	var arrResult = easyExecSql(mySql2.getString(), 1, 0,1);	
	/*
	if(!confBankCode())
	{
		return false;
	}	
	*/
	if(arrResult == null)
	{		
		fm.action="../appgrp/CreatBankCode.jsp?BankType=2&BankCodeName="+document.all('BankCodeName').value+"";
		document.getElementById("fm").submit(); //提交
	}
	else
	{
		document.all("BankCode").value = arrResult[0][0];
		document.all("Confirm").disabled=true;
	}
}

/*
function GetRecord()
{
	var i = 0;	
	var sum = 0;
	var tRow = -1;

	//判定是否选择了被保险人
	for( i = 0; i < FinFeeWithDrawGrid.mulLineCount; i++ )
	{
		if( FinFeeWithDrawGrid.getChkNo(i) == true )
		{
			sumMoney= sumMoney+ parseFloat(FinFeeWithDrawGrid.getRowColData(i,4));
			flag = 1;			
		}
	}
	
	document.all('Dif').value = sumMoney; 
	sumMoney =0;
}

*/