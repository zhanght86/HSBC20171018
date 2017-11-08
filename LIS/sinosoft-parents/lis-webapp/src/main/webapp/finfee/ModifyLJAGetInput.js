//ModifyLJAGetInput.js该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

var showInfo;
var qFlag=false;

//提交，保存按钮对应操作
function submitForm() {
  if (verifyInput() == false) return false;  
  if(checkPayMode()==false) return false;
   fm.butSave.disabled = true;
  var showStr="正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit(); //提交
  initInput();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ) {
  try { showInfo.close(); } catch(e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	easyQueryClick();
 // try { showInfo.close(); } catch(e) {}
}   

// 查询按钮
function easyQueryClick() {
	if ((fm.ActuGetNo.value==null||fm.ActuGetNo.value=="")&&(fm.OtherNo.value==null||fm.OtherNo.value==""))
	{
		alert("无查询数据，请输入至少一条查询条件。");
		fm.ActuGetNo.focus();
		return;
		}
		
	// 书写SQL语句			     
	//var strSql = "select a.actugetno, a.otherno, (select name from ldperson where customerno = a.appntno), a.paymode, a.sumgetmoney, a.bankcode, a.bankaccno, a.accname,a.drawertype,a.drawerid "
	//           + " from ljaget a where  EnterAccDate is null and managecom like '"+document.all('FManageCom').value+"%' "
  //        	 + getWherePart("ActuGetNo", "ActuGetNo")
  //        	 + getWherePart("OtherNo", "OtherNo");

		var strSql=wrapSql1('LJAGet1',[document.all('FManageCom').value,fm.ActuGetNo.value,fm.OtherNo.value]);

	turnPage.queryModal(strSql, BankGrid);
  
  if (!turnPage.strQueryResult) {
    alert("对不起,未查到相应数据。");
    return ;
  }
  else
  {
		fm.ActuGetNo2.value = fm.ActuGetNo.value;
		qFlag=true;
  }
}

function initInput(){
   try{
      fm.ChequeNo2.value ='';
      fm.BankCode2.value ='';
      fm.BankCode2Name.value ='';
      fm.ChequeNo3.value ='';
      fm.BankCode3.value ='';
      fm.BankCode3Name.value ='';
      fm.BankCode4.value ='';
      fm.BankCode4Name.value ='';
      fm.BankAccNo4.value ='';
      fm.AccName4.value ='';
      fm.IDType.value ='';
      fm.IDTypeName.value ='';
      fm.IDNo.value ='';
    }catch(ex){}
}

function afterCodeSelect(cCodeName, Field) {
  if (cCodeName == "PayMode") {
  	/*
    if (Field.value == "1") {
      fm.BankCode.value = "";
      fm.AccNo.value = "";
      fm.AccName.value = "";
      
      fm.BankCode.verify = "";
      fm.AccNo.verify = "";
      fm.AccName.verify = "";
    }
    else if (Field.value == "4") {
      fm.BankCode.verify = "银行代码|notnull&code:bank";
      fm.AccNo.verify = "银行账号|notnull";
      fm.AccName.verify = "账户名称|notnull";
    }*/
    initInput();
  for(i=1;i<=4;i++)
	{		
		if(i==Field.value)
		{
			document.all("divPayMode"+i).style.display='';
		}
		else
		{
		  document.all("divPayMode"+i).style.display='none';
		}
	}
  }
  	if(cCodeName == "bank" ){
				checkBank(document.all('BankCode4'),document.all('BankAccNo4'));
	  }
}


function showOne(parm1, parm2) {	
  //判断该行是否确实被选中
	if(document.all(parm1).all('InpBankGridSel').value == '1' ) {
	  var index = (document.all(parm1).all('BankGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
	  fm.ActuGetNo2.value = turnPage.arrDataCacheSet[index][0];
	  fm.PayMode.value = turnPage.arrDataCacheSet[index][3];
	  if(fm.PayMode.value=='2')
	  {
	  	document.all("divPayMode2").style.display='';
		  fm.BankCode2.value = turnPage.arrDataCacheSet[index][5];
		  fm.ChequeNo2.value = turnPage.arrDataCacheSet[index][6];
	  }
	  if(fm.PayMode.value=='3')
	  {
	  	document.all("divPayMode3").style.display='';
		  fm.BankCode3.value = turnPage.arrDataCacheSet[index][5];
		  fm.ChequeNo3.value = turnPage.arrDataCacheSet[index][6];
	  }
	  if(fm.PayMode.value=='4')
	  {
	  	document.all("divPayMode4").style.display='';
		  fm.BankCode4.value = turnPage.arrDataCacheSet[index][5];
		  fm.BankAccNo4.value = turnPage.arrDataCacheSet[index][6];
		  fm.AccName4.value = turnPage.arrDataCacheSet[index][7];
		  fm.IDType.value = turnPage.arrDataCacheSet[index][8];
		  fm.IDNo.value = turnPage.arrDataCacheSet[index][9];
	  }
  }
}

function readBankInfo() {
	if(!qFlag)
	{
		alert("请先进行查询操作或者请核实查询付款结果存在！");
		return;
	}
  //var strSql = "select OtherNo, OtherNoType from ljaget where ActuGetNo='" + fm.ActuGetNo2.value + "'";
	  var strSql =wrapSql1('LJAGet2',[fm.ActuGetNo2.value]);
  var arrResult = easyExecSql(strSql, 1, 0, 1, 0, 1);
  

  if (arrResult[0][1] != "10") {
    alert("不是保全数据！现在只支持保全类型数据的账户信息查询！");
    return;
  }
  
  //strSql = "select bankcode, bankaccno, accname from lccont where contno in (select contno from lpedoritem where edoracceptno='" + arrResult[0][0] + "') "
  //       + "union select bankcode, bankaccno, accname from lccont where contno in (select contno from lpedoritem where edorno='" + arrResult[0][0] + "') ";
	
	var strSql=wrapSql1('LCCont1',arrResult[0][0]);
  var arrResult2 = easyExecSql(strSql, 1, 0, 1, 0, 1);
  
//  if (arrResult2 == null) {
 //   strSql = "select bankcode, bankaccno, accname from lccont where contno in (select contno from lpedoritem where edorno='" + arrResult[0][0] + "')";
 //   arrResult2 = easyExecSql(strSql, 1, 0, 1, 0, 1);
  //}
  
  if (arrResult2 == null) {
    alert("没有查询到保单数据！");
    return;
  }  
  
  if (arrResult2[0][0] != "") {
  	document.all("divPayMode4").style.display='';
    fm.PayMode.value = "4";
    fm.BankCode4.value = arrResult2[0][0];
    fm.BankAccNo4.value = arrResult2[0][1];
    fm.AccName4.value = arrResult2[0][2];
  }
}

function checkPayMode()
{
   if(document.all('PayMode').value=='4')  //如果交费方式为4-银行转账，必须填写 开户银行,银行账号,户名并且到帐日期必须为空
    {
      if(trim(document.all('BankCode4').value)==''||trim(document.all('BankAccNo4').value)==''||trim(document.all('AccName4').value)=='')
        {
           alert("银行转账时，必须填写 开户银行,银行账号,户名");
           return false;
        }
        
      if (trim(document.all('BankCode4').value)=="0101") 
      {
        if (trim(document.all('BankAccNo4').value).length!=19 || !isInteger(trim(document.all('BankAccNo4').value))) 
        {
          alert("工商银行的账号必须是19位的数字，最后一个星号（*）不要！");
          return false;
        }
      }
      
    }
//如果为现金支票则不加控制
    if(document.all('PayMode').value=='3')  //如果交费方式为2||3-支票类，则票据号码和银行不能为空
    {
      if(trim(document.all('ChequeNo3').value)==''||trim(document.all('BankCode3').value)=='')
        {
         alert("交费方式为支票时，票据号码和银行不能为空");
         return false;
        }
    }
    
    if(document.all('PayMode').value=='2')  //如果交费方式为2||3-支票类，则票据号码和银行不能为空
    {
      if(trim(document.all('ChequeNo2').value)==''||trim(document.all('BankCode2').value)=='')
        {
         alert("交费方式为支票时，票据号码和银行不能为空");
         return false;
        }
    }
}

function checkBank(tBankCode,tBankAccNo){
	if(tBankCode.value.length>0 && tBankAccNo.value.length>0){
		if(!checkBankAccNo(tBankCode.value,tBankAccNo.value)){
			tBankAccNo.value = "";
			return false;
		}
	}
}


/**
	mysql工厂，根据传入参数生成Sql字符串
	
	sqlId:页面中某条sql的唯一标识
	param:数组类型,sql中where条件里面的参数
**/
function wrapSql1(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("finfee.ModifyLAGetInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}