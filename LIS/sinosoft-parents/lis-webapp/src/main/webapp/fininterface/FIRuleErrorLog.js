//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var confirmFlag=false;

var todayDate = new Date();
var sdate = todayDate.getDate();
var smonth= todayDate.getMonth() +1;
var syear= todayDate.getYear();
var sToDate = syear + "-" +smonth+"-"+sdate

//提交，保存按钮对应操作
function submitForm()
{   
   
   //可以不属于对应的业务号码
   //if(fm.OtherNo.value == null || fm.OtherNo.value == ""){   		
   //		alert("请输入对应的业务号码");
   //		return ;   	
   //} 


  
  var strSQL = "";
 
   if(1==1){
   	strSQL = getSqSQL();
   }
 
  fm.strSQLValue.value = strSQL;          
  turnPage.queryModal(strSQL, ErrLogGrid);
  if(ErrLogGrid.mulLineCount < 1){
  	alert("没有找到对应的数据");
  	return ;  	
  } 

}

function getSqSQL(){
/**
	var tSQL = "select distinct a.RuleDealBatchNo,(select b.rulename from FIBusinessRuleDef b where b.ruleid = a.RuleID),a.RuleDealResult,(select username from lduser where usercode=a.DealOperator),a.RuleDealDate,a.RuleID from FIBusinessRuleDealLog a where 1=1 and a.ruledealresult = 'Fail' and exists(select 'X' from fibusinessruledealerrlog g where g.ruleid = a.ruleid and a.ruledealbatchno = g.aserialno)  " ;
	  if(fm.StartDate.value!='')
  {
  	tSQL = tSQL + " and a.RuleDealDate >='"+fm.StartDate.value+"' ";
  }
  if(fm.EndDate.value!=''){
  	tSQL = tSQL + " and a.RuleDealDate <='"+fm.EndDate.value+"' ";
  }
  */
  var mySql1=new SqlClass();
	  mySql1.setResourceName("fininterface.FIRuleErrorLogSql"); //指定使用的properties文件名
	  mySql1.setSqlId("FIRuleErrorLogSql1");//指定使用的Sql的id
	  mySql1.addSubPara(1);//指定传入的参数
	  mySql1.addSubPara(fm.StartDate.value);//指定传入的参数
	  mySql1.addSubPara(fm.EndDate.value);//指定传入的参数
  var tSQL= mySql1.getString();
	return tSQL;
}
function getSqSQLErr(){
	
	
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIRuleErrorLogSql"); //指定使用的properties文件名
		mySql2.setSqlId("FIRuleErrorLogSql2");//指定使用的Sql的id
		mySql2.addSubPara(fm.RuleDealBatchNo.value);//指定传入的参数
	var tSQL= mySql2.getString();
	tSQL = "select  distinct c.aserialno,(select b.rulename from FIBusinessRuleDef b where b.ruleid = c.RuleID),c.errinfo,(select username from lduser where usercode=d.DealOperator),d.RuleDealDate from FIBusinessRuleDealErrLog c ,FIBusinessRuleDealLog d where c.aserialno='"+fm.RuleDealBatchNo.value+"'and c.aserialno=d.RuleDealBatchNo and  c.ruleid = d.ruleid and  d.ruledealresult = 'Fail' ";
	return tSQL;
}

function printFinFeeToExcel()
{
	var tNo = ErrLogGrid.getSelNo();
   if(ErrLogGrid.mulLineCount < 1){
  	   alert("没有找到对应的数据");
  	   return ;  	
   }
    	if (tNo==0||tNo==null)
	{
		alert("请选择一条记录");
		return;
	}
	 var strSQL1 = "";
	var tRuleDealBatchNo=ErrLogGrid.getRowColData(tNo-1,1);
	var flag=ErrLogGrid.getRowColData(tNo-1,3);
	if(flag=='Succ')
	{
		alert("该批次经校验没有差异");
	}
	if(flag=='Fail')
	{
		fm.RuleDealBatchNo.value=tRuleDealBatchNo;
		strSQL1 = getSqSQLErr();
    fm.strSQLValueErr.value = strSQL1 + " and c.ruleid = '" + ErrLogGrid.getRowColData(tNo-1,6) + "' ";   
    //alert(fm.strSQLValueErr.value);
    //return false;
    fm.method="post";
	  fm.action = "./FIRuleErrorLogToExcel.jsp";
	    fm.target="fraSubmit";
	    document.getElementById("fm").submit(); 
   }
	   
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
  }
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}     

function returnParent()
{
    top.close();

}

function clearshowInfo()
{  
  
  showInfo.close();
}

function mf()
{  
 //fm.
}