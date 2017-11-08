//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var arrDataSet;
var k = 0;
var tDate ;

//提交，保存按钮对应操作
function signCardPol()
{
	//getChkNo(row)
	var j;
	var ChkNum=0;//选中行数
	var ChkSelNo;
	//alert("总行数："+CardPolGrid.mulLineCount);
	//循环判断选中行数
	for(j=1;j<=CardPolGrid.mulLineCount;j++)
	{
		if(ChkNum>=2) { break; }
		if(CardPolGrid.getChkNo(j-1)==true){ ChkNum++;}
	} 	
	//if(ChkNum==0 || ChkNum>=2)
	//{
	//	alert("没有选择投保单或选择了多个投保单。\n签单时只能选择一个投保单！！！");
	//	return ;
	//}
	//循环判断第几行被选中,并取出选中行的值
	for(j=1;j<=CardPolGrid.mulLineCount;j++)
	{
		if(CardPolGrid.getChkNo(j-1)==true)
		{
			 //alert("选中行："+j);
			 fm.ProposalContNo.value=CardPolGrid.getRowColData(j-1,1);//投保单号
			 fm.ContCardNo.value=CardPolGrid.getRowColData(j-1,1);//卡号
			 break;
		}
	} 
	//alert("投保单号："+fm.ProposalContNo.value+"\n"+"对应的 卡号："+fm.ContCardNo.value);
	//修改按纽提交事件
    if (trim(fm.ProposalContNo.value) == "" || trim(fm.ContCardNo.value) == "")
    {
        alert("卡号或投保单号为空，无法签单！");
        return;
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
	document.all("signbutton").disabled = true;
	document.getElementById("fm").submit(); //提交 

}

//查询前做的操作
function beforeQuery()
{

    if (   ((document.all('AgentCode').value == '') || (document.all('AgentCode').value == null))
        && ((document.all('ContNo').value == '')    || (document.all('ContNo').value == null)) 
        && ((document.all('ManageCom').value == '') || (document.all('ManageCom').value == null))
        && ((document.all('AppntName').value == '') || (document.all('AppntName').value == null))
        && ((document.all('StartDate').value == '') || (document.all('StartDate').value == null)) 
        && ((document.all('EndDate').value == '')   || (document.all('EndDate').value == null))   
        && ((document.all('PrtNo').value == '')   || (document.all('PrtNo').value == null))   )
    {
    	if((document.all('StartDate').value == '') || (document.all('StartDate').value == null)){
    	   alert("请录入起始时间和结束时间！");
    	   return false;
    	}
    }
    
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content )
{
	showInfo.close();
	unlockScreen('lkscreen');  
	if (FlagStr == "Fail" )
	{   
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ; 
		//showModelessDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:550px;dialogHeight:350px");   
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
		//showModelessDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQueryClick();//刷新页面
	}
	document.all("signbutton").disabled=false;
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

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}


// 查询按钮
function easyQueryClick()
{
	if (beforeQuery()==false) { return ;}
	CardPolGrid.clearData();
	var strOperate="like";
	
		var sqlid1="CardProposalSignSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.CardProposalSignSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(ComCode);//指定传入的参数
		
		mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
        mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
        mySql1.addSubPara(fm.AppntName.value);//指定传入的参数
        
		mySql1.addSubPara(fm.AgentCode.value);//指定传入的参数
		mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql1.addSubPara(fm.StartDate.value);//指定传入的参数
		mySql1.addSubPara(fm.EndDate.value);//指定传入的参数s
		
	    var strSql=mySql1.getString();	
	
//	var strSql = "select b.contno,b.forceuwreason,(select riskcode from lcpol where contno =b.contno),b.appntname,b.managecom,b.agentcode,b.makedate,b.maketime"
//			+ " from lccont b where b.appflag='0' and b.cardflag='4'" 
//			+ " and b.managecom like '"+ComCode+"%%'"
//			+ getWherePart('b.contno','ContNo',strOperate)
//			+ getWherePart('b.managecom','ManageCom',strOperate)
//			+ getWherePart('b.appntname','AppntName',strOperate)
//			+ getWherePart('b.agentcode','AgentCode',strOperate)
//			+ getWherePart('b.prtno','PrtNo',strOperate)
//			+ getWherePart('b.makedate','StartDate',">=")
//			+ getWherePart('b.makedate','EndDate',"<=")
//			//+ " and state not in('1001&&&&','1002&&&&')"  //由于在IE里【 & 】符号被截断，所以转换为16进制代码
//			//+ " and state not in ('1001%26%26%26%26','1002%26%26%26%26')"  
//			+ " order by b.managecom,b.makedate,b.contno ";
	turnPage.queryModal(strSql,CardPolGrid);
}

//双击 业务员编码 输入框触发函数
function queryAgent()
{
	if(document.all('AgentCode').value == "")	
	{
	var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != "")
	{
		var cAgentCode = fm.AgentCode.value;  //业务员编号
		if(cAgentCode.length!=8) { return; }
		
			var sqlid1="CardProposalSignSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.CardProposalSignSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(cAgentCode);//指定传入的参数
		
	    var strSQL=mySql1.getString();	
		
//	    var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.branchtype in ('1','4') and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
       
	    var arrResult = easyExecSql(strSQL);
	    if (arrResult != null) 
	    {
	    	fm.AgentCode.value = arrResult[0][0];
	    }
	    else
	    {
		     //fm.AgentCode.value="";
		     alert("编码为:["+document.all('AgentCode').value+"]的业务员不存在，请确认!");
	    }
	}
}
//代理人代码查询返回执行函数
function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
    	fm.AgentCode.value = arrResult[0][0];
		//fm.BranchAttr.value = arrResult[0][10];
		//fm.AgentGroup.value = arrResult[0][1];
		//fm.AgentName.value = arrResult[0][3];
		//fm.AgentManageCom.value = arrResult[0][2];
  }
}
