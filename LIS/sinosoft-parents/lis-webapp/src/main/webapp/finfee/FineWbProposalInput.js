//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var spanObj;
var mDebug = "100";
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var mShowCustomerDetail = "PROPOSAL";
var mCurOperateFlag = ""	// "1--录入，"2"--查询
var mGrpFlag = ""; 	//个人集体标志,"0"表示个人,"1"表示集体.
var cflag = "WB";        //文件件录入位置

var arrGrpRisk = null;

window.onfocus = myonfocus;
var hiddenBankInfo = "";
 

function quaryagentgroup()
{
	//var sql="select agentgroup from laagent where agentcode='"+document.all('AgentCode').value+"'";
	var sqlid1="FineWbProposalInputSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("finfee.FineWbProposalInputSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all('AgentCode').value);//指定传入的参数
	var sql=mySql1.getString();
	var tResult = easyExecSql(sql, 1, 1, 1); 
	if(tResult!=null)
	{
		document.all('AgentGroup').value=tResult[0][0];
	}
} 
 
function changeOtherNo()
{
    var aOtherNo=document.all('OtherNo').value;	
	var aSelNo=TempGrid.mulLineCount;
	if( aSelNo == 0 || aSelNo == null )
		return;
	else
	{
		while(aSelNo-->0)
		{
		   TempGrid.setRowColData(aSelNo,3,aOtherNo);
		 }
	}
}  

function changeEnterDate()
{
    var aPayDate=document.all('PayDate').value;	
	var aNo=TempClassGrid.mulLineCount;
	if( aNo == 0 || aNo == null )
		return;
	else
	{
		while(aNo-->0)
		{
		   TempClassGrid.setRowColData(aNo,4,aPayDate);
		 }
	}
} 
 
function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
	var sqlid2="FineWbProposalInputSql1";
	var mySql2=new SqlClass();
	mySql2.setResourceName("finfee.FineWbProposalInputSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(cAgentCode);//指定传入的参数
	var strSql=mySql2.getString();
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentName.value = arrResult[0][1];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}	
}

function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
    fm.AgentCode.value = arrResult[0][0];
  }
}

function queryAgent2()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
	var sqlid3="FineWbProposalInputSql2";
	var mySql3=new SqlClass();
	mySql3.setResourceName("finfee.FineWbProposalInputSql"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(cAgentCode);//指定传入的参数
	mySql3.addSubPara(document.all('ManageCom').value);//指定传入的参数
	var strSql=mySql3.getString();
    var arrResult = easyExecSql(strSql);
      // alert(arrResult);
    if (arrResult != null) {
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}	
}

function checkComCode()
{
	if((document.all('TempFeeNo').readOnly == true) && (document.all('TempFeeNo').value != ""))
	{
		
		//var sql = "SELECT managecom FROM es_doc_main where doccode='"+ trim(document.all('TempFeeNo').value) + "'";
		var sqlid4="FineWbProposalInputSql3";
		var mySql4=new SqlClass();
		mySql4.setResourceName("finfee.FineWbProposalInputSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(trim(document.all('TempFeeNo').value));//指定传入的参数
		var sql=mySql4.getString();
		var ManageCom = decodeEasyQueryResult(easyQueryVer3(sql));
		var Managecom2 =trim(document.all('ManageCom').value);	
		if(ManageCom!=null)
		{
		   if(ManageCom[0][0]!=Managecom2)
		    {
			alert("扫描件与录入机构不是同一个机构!");
			return false;
		    }
	        } 
        }
	return true;
}


/*********************************************************************
 *  保存财务收费单的提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm() {
	document.all("save").disabled = true;
	//alert(fm.TempFeeNoHide.value);
	//var sSQL ="select 1 from ljtempfee where tempfeeno='"+fm.TempFeeNo.value+"' ";
	var sqlid5="FineWbProposalInputSql4";
	var mySql5=new SqlClass();
	mySql5.setResourceName("finfee.FineWbProposalInputSql"); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(fm.TempFeeNo.value);//指定传入的参数
	var sSQL=mySql5.getString();
	var arrCount=easyExecSql(sSQL); 
	if(fm.TempFeeNoHide.value !="" || arrCount!=null)
	{
		alert("该收费信息已经保存，不能再次保存！");
		return;
	}
	//alert("document.all(save).disabled"+document.all("save").disabled);
//	alert("document.all(save).disabled"+document.all("save").src);
	if(document.all("aftersave").value==1)
	{
		return ;
	}
	//检验银行账号是否录入规范
    if(!verifyBankAccNo()) return false;
	
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var passVerify = true;
    var verifyGrade = "1";
		 
	document.all('fmAction').value = "INSERT";
	//alert("mAction"+mAction);
	
	//return;
 
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	lockScreen('lkscreen');   
	fm.submit(); //提交
	document.all("aftersave").value="1";
}

/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	//document.all("save").disabled=false;
	unlockScreen('lkscreen');
	try { showInfo.close(); } catch(e) {}
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//inputQuestButton.style.display="";
	}
	else
	{ 
 
		if(loadFlag == '3'){
		//inputQuestButton.style.display="";
		}		
		  content = "处理成功！";
		  var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
		  var name='提示';   //网页名称，可为空; 
		  var iWidth=550;      //弹出窗口的宽度; 
		  var iHeight=350;     //弹出窗口的高度; 
		  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		  showInfo.focus();
		  top.opener.easyQueryClick();
   		  top.close();		
	} 
	mAction = ""; 
}

/*********************************************************************
 *  Click事件，当点击“删除”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteClick() { 
	var tPrtNo = document.all('TempFeeNo').value;
	
	if(tPrtNo==null || tPrtNo=="") {
		alert( "单证印刷号信息丢失，不能进行删除!" );
	}
	else 
	{
		if(fm.DealType.value != "03")
		{
			alert("此财务收费单非外包方无法处理的异常件，不能删除！");
			return false;
		}
		
		if(!confirm("确定要删除吗？"))
		{
			return false;
		}

		var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )	
		{
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   \var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "DELETE";
			document.all( 'fmAction' ).value = mAction;
			lockScreen('lkscreen');
			fm.submit(); //提交
		}
	}
}           

/*********************************************************************
 *  "重置"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function resetForm()
{
	document.all("save").disabled=false;
	document.all("aftersave").value="0";
	changeImage(document.all("save"),'../common/images/butSave.gif');
} 

/*********************************************************************
 *  "取消"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function cancelForm()
{
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
}
 
/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	//if( cDebug == "1" )
		//parent.fraMain.rows = "0,0,50,82,*";
	//else 
		//parent.fraMain.rows = "0,0,80,72,*";
}

/*********************************************************************
 *  根据查询返回的信息查询投保单明细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryPolDetail( cPolNo )
{

	emptyForm(); 
	//var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  

	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	//parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
	parent.fraTitle.window.location = "./FineWbProposalQueryDetail.jsp?TempFeeNo=" + TempFeeNo;

}
           
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}

function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//问题件录入
function QuestInput()
{
	if(inputQuestButton.disabled == true)
	   return;
	cProposalNo = fm.TempFeeNo.value;  //财务单号码
	if(cProposalNo == "")
	{
		alert("尚无投单证印刷号，请先保存!");
	}
	else
	{	
		window.open("../uw/QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1");
	}
}

//检验银行账号录入是否规范
function verifyBankAccNo()
{
	var count=TempClassGrid.mulLineCount;
	//alert(count);
	var tBankCode = '';
	var tBankAccNo = '';
	for(;count>0;count--)
	{
		tBankCode = TempClassGrid.getRowColData(count-1,5);
		tBankAccNo = TempClassGrid.getRowColData(count-1,6);
		//alert(tBankCode);
		//alert(tBankAccNo);
		if(tBankCode!=null && tBankCode!='' 
			&& tBankAccNo!=null && tBankAccNo!='')
			{
				if(!checkBankAccNo(tBankCode,tBankAccNo))
			 		return false;
			}
	}
	
	return true;
}
