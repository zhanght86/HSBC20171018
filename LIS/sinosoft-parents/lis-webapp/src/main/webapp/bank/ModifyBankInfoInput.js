//ModifyBankInfoInput.js该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

var showInfo;

//提交，保存按钮对应操作
function submitForm() {
  if (verifyInput() == false) return false;  
  if (fm.BankCode.value == "0101") {
    if (trim(fm.AccNo.value).length!=19 || !isInteger(trim(fm.AccNo.value))) {
      alert("工商银行的账号必须是19位的数字，最后一个星号（*）不要！");
      return false;
    }
  }
 /* if(document.all('FeeType').value=="2")
  {
  	if(document.all('PayMode').value!="")
  	{
  		alert("该类型收费不允许修改交费方式");
  		return;
  	}
  }

  if(document.all('FeeType').value=="1"&& document.all('PayMode').value=="")
  {
  	alert("请选择交费方式");
  	return false;
  }*/
  
  //if(document.all('FeeType').value=="2" || (document.all('FeeType').value=="1" &&  document.all('PayMode').value=="4"))
 // {
  	if(document.all('BankCode').value=="" || document.all('AccNo').value=="" || document.all('AccName').value=="")
  	{
  		alert("银行代码、银行账号、账户名称不能为空，请核实！");
  		return false;
  	}
 // }
   
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
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ) {
  try { 
  	initForm();
  	showInfo.close(); 
  } catch(e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}   

// 查询按钮
function easyQueryClick() {
	//if(document.all('FeeType').value=="2")
	//{
	  if (fm.TempfeeNo.value == "") {
	    alert("请先输入收据号");
	    return; 
	  }
	  initBankGrid();
		// 书写SQL语句			
		 fm.butQuery.disabled = true;       
		
	/*	var strSql = "SELECT tempfeeno,'"+document.all('PrtNo').value+"',paymode, paymoney, bankcode, accname, bankaccno FROM ljtempfeeclass WHERE 1 = 1"
	               + " AND tempfeeno IN (SELECT tempfeeno FROM ljtempfee WHERE "
	               + " enteraccdate IS NULL AND otherno IN (SELECT contno FROM lccont WHERE prtno = '" + trim(document.all('PrtNo').value) + "'))"
	               + "union all select tempfeeno,'"+document.all('PrtNo').value+"',paymode, paymoney, bankcode, accname, bankaccno FROM ljtempfeeclass WHERE 1 = 1"
	               + " AND tempfeeno IN (SELECT tempfeeno FROM ljtempfee WHERE "
	               + " enteraccdate IS NULL AND otherno IN (SELECT familycontno FROM lccont WHERE prtno = '" + trim(document.all('PrtNo').value) + "')) "
	               + " union all select a.tempfeeno,'"+document.all('PrtNo').value+"',a.paymode, a.paymoney, bankcode, accname, bankaccno from ljtempfeeclass a, ljtempfee b where a.tempfeeno=  b.tempfeeno"
	               + " and b.enteraccdate IS NULL and b.otherno= '" + trim(document.all('PrtNo').value) + "'";*/
		//var strSql = "select tempfeeno,'',paymode, paymoney,bankcode,bankaccno,accname from ljtempfeeclass where tempfeeno='" + trim(document.all('TempfeeNo').value) + "' and enteraccdate is null "
		//					 + " and paymode='4'  and managecom like '"+document.all('FManageCom').value+"%'";
		
			var strSql = wrapSql1("LJTempFeeClass1",[trim(document.all('TempfeeNo').value),document.all('FManageCom').value]);
		turnPage.queryModal(strSql, BankGrid);
		if (BankGrid.mulLineCount<=0)
		{
			alert("未查到相关暂收数据！");
			return false;
		}
		//fm.PrtNo2.value = fm.PrtNo.value;
		document.all('TempfeeNo1').value = document.all('TempfeeNo').value;
		
  //}
 /* if(document.all('FeeType').value=="1")
  {
  	if(fm.GetNoticeNo.value=="")
  	{
  		alert("请输入交费通知书号");
  		return false;
  	}
  	initForm();
  	fm.butQuery.disabled = true;     
  	var strSql = "select '',GetNoticeNo,(select payform from LPEdorApp where GetNoticeNo=a.GetNoticeNo and rownum=1),bankcode, accname, bankaccno "
  	           + " from ljspay a where GetNoticeNo='"+trim(document.all('GetNoticeNo').value)+"' and (bankonthewayflag='0' or bankonthewayflag is null) and othernotype='10' ";
         
  	turnPage.queryModal(strSql, BankGrid);
  	if (BankGrid.mulLineCount<=0)
		{
			alert("未查到相关应收数据！");
			return false;
		}
		fm.GetNoticeNo2.value = fm.GetNoticeNo.value;
  }*/

}

/*function afterCodeSelect(cCodeName, Field) 
{
  if(document.all('FeeType').value=="1")
  {
  	divFeeType1.style.display='';
  	divFeeType2.style.display='none';
  }
    if(document.all('FeeType').value=="2")
  {
  	divFeeType1.style.display='none';
  	divFeeType2.style.display='';
  }
}*/

function checkBank(tBankCode,tBankAccNo){
	if(tBankCode.value.length>0 && tBankAccNo.value.length>0){
		if(!checkBankAccNo(tBankCode.value,tBankAccNo.value)){
			tBankAccNo.value = "";
			return false;
		}
	}
}

function afterCodeSelect(cCodeName, Field) 
{
	if(cCodeName == "bank" ){
			checkBank(document.all('BankCode'),document.all('AccNo'));
  }
	

}


/**
	mysql工厂，根据传入参数生成Sql字符串
	
	sqlId:页面中某条sql的唯一标识
	param:数组类型,sql中where条件里面的参数
**/
function wrapSql1(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("bank.ModifyBankInfoInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}