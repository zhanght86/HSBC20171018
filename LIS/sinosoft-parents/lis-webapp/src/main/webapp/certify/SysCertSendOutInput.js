//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
window.onfocus=myonfocus;
var tResourceName="certify.SysCertSendOutInputSql";
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

//提交，保存按钮对应操作
function submitForm()
{
    if(document.all('CertifyCode').value!=null&&document.all('CertifyCode').value!="")
    {
        //var sql="select * from lmcertifydes where JugAgtFlag in ('2','3') " +getWherePart('CertifyCode', 'CertifyCode');
    	var sql = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value]);
        var arrResult = easyExecSql(sql);

	    if(arrResult!=null)
	    {
	        if(!confirm("该单证发放未做校验！是否发放？？"))
	        {
	           return; 
	        }
	    }
	            if( verifyInput() == true && CertifyList.checkValue("CertifyList") ) {
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
    else
    {
        alert("请输入单证编码！！");    
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

    //执行下一步操作
  }
}

// 查询功能
function query()
{
	fm.hideOperate.value = "QUERY";
	showInfo=window.open("./SysCertSendOutQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
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
  	alert("SysCertSendOutInput.js-->resetForm函数中发生异常:初始化界面错误!");
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

/*********************************************************************
 *  Click事件，当点击增加图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addClick()
{
	//下面增加相应的代码
	showDiv( operateButton, "false" ); 
	showDiv( inputButton, "true" ); 
}           

/*********************************************************************
 *  Click事件，当点击“查询”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryClick()
{
	fm.hideOperation = "QUERY||MAIN";
	window.open("./SysCertSendQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}           

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery(arrResult)
{
  if(arrResult!=null)
  {
//    //document.all('ComCode').value     = arrResult[0][0];
//    //此处编写从新的查询窗口返回的数据
//    document.all('PrtNo').value = arrResult[0][0];
//    document.all('CertifyCode').value = arrResult[0][1];
//    document.all('MaxMoney').value = arrResult[0][5];
//    document.all('MaxDate').value = arrResult[0][6];
//    document.all('ComCode').value = arrResult[0][7];
//    document.all('Phone').value = arrResult[0][8];
//    document.all('LinkMan').value = arrResult[0][9];
//    document.all('CertifyPrice').value = arrResult[0][10];
//    document.all('ManageCom').value = arrResult[0][11];
//    document.all('OperatorInput').value = arrResult[0][12];
//    document.all('InputDate').value = arrResult[0][13];
//    document.all('InputMakeDate').value = arrResult[0][14];
//    document.all('GetMan').value = arrResult[0][15];
//    document.all('GetDate').value = arrResult[0][16];
//    document.all('OperatorGet').value = arrResult[0][17];
//    document.all('StartNo').value = arrResult[0][18];
//    document.all('EndNo').value = arrResult[0][19];
//    document.all('GetMakeDate').value = arrResult[0][20];
//    document.all('SumCount').value = arrResult[0][21];
//    document.all('State').value = arrResult[0][24];

		if( document.all('State').value == '0' ) {
			// 设置提交信息，在CertifyPrintInit.jsp中
			setGetInfo();
		}
  }
}

function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}
