//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var mOperation = "";
window.onfocus=myonfocus;
var turnPage1 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var tResourceName="certify.GrpSysCertTakeBackInputSql";
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


function isCheckSignature(){
    var tFlag = fm.SignatureFlag.value;
    if(tFlag=="false"){
       alert("未核对签名，不能录入回执！");
       return false;
    }
    return true;
}

function checkScan(){
     var lastNo = fm.LastCertifyNo.value;
     var nowNo = fm.CertifyNo.value;
     if(lastNo==nowNo){
     }else{
        fm.SignatureFlag.value="false";
     }
     fm.LastCertifyNo.value = fm.CertifyNo.value;
}

function queryScanSignature(){
   fm.SignatureFlag.value="true";
   //window.open("./ContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan", "", sFeatures);        
   var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
   //var tSql ="select prtno from lccont where contno = '"+fm.CertifyNo.value+"'";
   var tSql = wrapSql(tResourceName,"querysqldes1",[fm.CertifyNo.value]);
   //var tSql ="select prtno from lcpol where polno = '210130000000041'";
   tPrtNo = easyExecSql(tSql);
   //window.open("./DisplayScanSignature.jsp?ScanFlag=1&scantype=scan&prtNo="+tPrtNo, "", sFeatures);        
   //window.open("./DisplayScanSignature.jsp?ScanFlag=1&scantype=scan&prtNo=86160100596242&SubType=TB1001", "", sFeatures);        
   window.open("./DisplayScanSignature.jsp?ScanFlag=1&scantype=scan&prtNo="+tPrtNo+"&SubType=TB1001", "", sFeatures);        
}

//校验录入的回收日期必须录入两次，而且两次必须一致
function checkDouble()
{

	if(document.all('TakeBackDate').value==null||document.all('TakeBackDate').value=="")
	{
		alert("回执日期不能为空");
		return false;
	}
	
	if(document.all('hideTakeBackDate').value==null||document.all('hideTakeBackDate').value=="")
	{
		document.all('hideTakeBackDate').value=document.all('TakeBackDate').value;
		document.all('TakeBackDate').value="";
	}
	else if(document.all('hideTakeBackDate').value!=document.all('TakeBackDate').value)
	{
		alert("两次输入日期不符,请重新确认");
		document.all('hideTakeBackDate').value="";
		document.all('TakeBackDate').value="";
      	return false;
	}
}


//提交，保存按钮对应操作
function submitForm()
{
     //var sql1="select * from lmcertifydes where JugAgtFlag in ('1','3') " +getWherePart('CertifyCode', 'CertifyCode');
     var sql1 = wrapSql(tResourceName,"querysqldes2",[document.all('CertifyCode').value]);
     var arrResult1 = easyExecSql(sql1);
     
        //alert(arrResult1);
     if(arrResult1!=null)
     {
        //alert(sql1);
        if(!confirm("该单证回收不做校验，是否回收？？"))
        {
            return;
        }
     }
     
     //回收日期必须录入
     if(document.all('TakeBackDate').value==null||document.all('TakeBackDate').value=="")
     {
     	alert("回收日期不能为空!");
      	return false;
     }

	//if(isCheckSignature()==false) return false;
    //var strSql = "select AgentState from laagent where agentcode = '" + document.all('SendOutCom').value.substring(1) + "' ";
    var strSql = wrapSql(tResourceName,"querysqldes3",[document.all('SendOutCom').value.substring(1)]);
    //prompt(strSql);
     var strResult = easyExecSql(strSql);
    
    if (strResult==null || strResult=="") {
      alert("该业务员不存在");
      return false;
    }
    
    if(strResult!="01" && strResult!="02")
    {
        if(!confirm("该业务员已经失效，是否确认回收此单证？"))
        {
            return ;
        }
    }    
	if(verifyInput() == true) {
//	  if(fm.CertifyCode.value == "9995")		//不是个单回执的话也需要录入回收日期！！！
		if(fm.TakeBackDate.value == "")
		{
			alert("必须录入回收日期!");
			return ;
		}
		
		//var strSql = "select * from LZSysCertify where CertifyCode='" + fm.CertifyCode.value + "' and CertifyNo='"+ fm.CertifyNo.value + "' and stateflag='1'";
		var strSql = wrapSql(tResourceName,"querysqldes4",[fm.CertifyCode.value,fm.CertifyNo.value]);
		var arrResult = easyExecSql(strSql);
		if (arrResult != null) 
		{
			alert("警告：该单证已回收，不容许进行二次回收！");
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
	
	  // showSubmitFrame(mDebug);
	  fm.hideOperation.value = "INSERT||MAIN";
	  document.getElementById("fm").submit(); //提交
	}
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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    content="保存成功！";
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1") {
	parent.fraMain.rows = "0,0,50,82,*";
  }	else {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  mOperation = "QUERY||MAIN";
  fm.sql_where.value = " and StateFlag = '1' ";
  fm.QUERY_FLAG.value='1';
  showInfo = window.open("./GrpSysCertTakeBackQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function query()
{
	mOperation = "QUERY||MAIN";
	fm.sql_where.value = " and StateFlag = '0' ";
	fm.QUERY_FLAG.value='0';
	showInfo = window.open("GrpSysCertTakeBackQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery(arrResult)
{   
	try {
	  if(arrResult!=null)
	  {
	  	fm.CertifyCode.value = "";
			fm.CertifyNo.value = "";
			fm.ValidDate.value = "";
			fm.SendOutCom.value = "";
			fm.ReceiveCom.value = "";
			fm.Handler.value = "";
			fm.HandleDate.value = "";
			fm.SendNo.value = "";
			fm.TakeBackNo.value = "";
			fm.Operator.value = "";
			fm.MakeDate.value = "";
			fm.TakeBackDate.value = "";
			//tongmeng 2007-12-28 modify
			//对于已经回收过的单证不允许回收
	  	if(arrResult[0][12]!=null&&arrResult[0][12]=='1')
	  	{
	  		alert("该单证已经回收!");
	  		return false;
	  	}
	  	//tongmeng 2007-12-29 add
	  	//增加对保单是否打印过的判断
	  	//var tSQL = " select '1' from lccont where contno='"+arrResult[0][1]+"' and printcount<=0 ";
	  	var tSQL = wrapSql(tResourceName,"querysqldes5",[arrResult[0][1]]);
	  	tempResult = easyExecSql(tSQL);
	  	//alert(tempResult);
				if(tempResult!=null)
				{
					//if(tempResult[0][0]=='1')
					{
						alert('该保单未打印,不允许回收');
						return false;
					}
				}
				
			fm.CertifyCode.value = arrResult[0][0];
			fm.CertifyNo.value = arrResult[0][1];
			fm.ValidDate.value = arrResult[0][5];
			fm.SendOutCom.value = arrResult[0][3];
			fm.ReceiveCom.value = arrResult[0][2];
			fm.Handler.value = arrResult[0][6];
			fm.HandleDate.value = arrResult[0][7];
			fm.SendNo.value = arrResult[0][8];
			fm.TakeBackNo.value = arrResult[0][9];
			fm.Operator.value = arrResult[0][4];
			fm.MakeDate.value = arrResult[0][10];
			//fm.TakeBackDate.value = arrResult[0][11];
		}
	} catch (ex) {
		alert("在afterQuery中发生错误");
	}
}
function confirmSecondInput1(aObject,aEvent){
	if(aEvent=="onkeyup"){
	  var theKey = window.event.keyCode;
	  if(theKey=="13"){
	    if(theFirstValue!=""){
      	theSecondValue = aObject.value;
	      if(theSecondValue==""){
	      	alert("请再次录入！");
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	      if(theSecondValue==theFirstValue){
	        aObject.value = theSecondValue;
	        return;
	      }
	      else{
	        alert("两次录入结果不符，请重新录入！");
	        theFirstValue="";
	        theSecondValue="";
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	    }
      else{
      	theFirstValue = aObject.value;
      	if(theFirstValue==""){
      		theSecondValue="";
      	  alert("请录入内容！");
      	  return;
      	}
      	aObject.value="";
      	aObject.focus();
        return;
      }
    }
  }
  else if(aEvent=="onblur"){
	  //alert("theFirstValue="+theFirstValue);
	  if(theFirstValue!=""){
    	theSecondValue = aObject.value;
	    if(theSecondValue==""){
	    	alert("请再次录入！");
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	    if(theSecondValue==theFirstValue){
	      aObject.value = theSecondValue;
	      theSecondValue="";
	      theFirstValue="";
	      return;
	    }
	    else{
	      alert("两次录入结果不符，请重新录入！");
	      theFirstValue="";
	      theSecondValue="";
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	  }
    else{
    	theFirstValue = aObject.value;
     	theSecondValue="";
    	if(theFirstValue==""){
      	//alert("aa");
    	  return;
    	}
    	aObject.value="";
    	aObject.focus();
      return;
    }
  }
}

function queryagent()
{
		if(trim(fm.CertifyCode.value)=='')
		{
			 alert("请先输入单证编码");
			 fm.CertifyNo.value ='';
			 return false;
		}
		if(fm.CertifyNo.value!=""&&fm.CertifyNo.value!=null)
		{
			var arrResult;
			if(trim(fm.CertifyCode.value)=="9994")
			{
				//var sqlStr="select certifycode,certifyno,sendoutcom,receivecom,operator,validdate,handler,handledate,sendno,takebackno,makedate,takebackdate,StateFlag from LZSysCertify  where CertifyNo='"+fm.CertifyNo.value+"' and CertifyCode='9995' ";
				var sqlStr = wrapSql(tResourceName,"querysqldes6",[fm.CertifyNo.value]);
				arrResult = easyExecSql(sqlStr);
				if(arrResult!=null) { afterQuery(arrResult);}
				else
				{	
						//var sqlStr = "select agentcode,managecom from lccont where contno = '"+fm.CertifyNo.value+"'";
						var sqlStr = wrapSql(tResourceName,"querysqldes7",[fm.CertifyNo.value]);
						arrResult = easyExecSql(sqlStr);
						if(arrResult!=null)
						{
							document.all('ReceiveCom').value = "A"+arrResult[0][1];
							document.all('SendOutCom').value = "D"+arrResult[0][0];
						}
						else
						{alert("未查到相应信息，请检查保单号是否输入有误!");}
						
							  	//tongmeng 2007-12-29 add
	  							//增加对保单是否打印过的判断
	  							//var tSQL = " select '1' from lccont where contno='"+fm.CertifyNo.value+"' and printcount<=0 ";
	  							var tSQL = wrapSql(tResourceName,"querysqldes5",[fm.CertifyNo.value]);
	  							tempResult = easyExecSql(tSQL);
	  							//alert(tempResult);
									if(tempResult!=null)
									{
										//if(tempResult[0][0]=='1')
										{
												alert('该保单未打印,不允许回收');
												fm.CertifyNo.value ='';
												document.all('ReceiveCom').value = '';
												document.all('SendOutCom').value = '';
												return false;
										}
									}
				}
			}
			else
			{
				//var sqlStr="select certifycode,certifyno,sendoutcom,receivecom,operator,validdate,handler,handledate,sendno,takebackno,makedate,takebackdate from LZSysCertify  where CertifyNo='"+fm.CertifyNo.value+"'";
				var sqlStr = wrapSql(tResourceName,"querysqldes8",[fm.CertifyNo.value]);
				arrResult = easyExecSql(sqlStr);
				if(arrResult!=null)
				  { afterQuery(arrResult);}
				else
				  { alert("未查到相应信息，请检查单证号是否输入有误!");}
 	 
			}
		}
}
