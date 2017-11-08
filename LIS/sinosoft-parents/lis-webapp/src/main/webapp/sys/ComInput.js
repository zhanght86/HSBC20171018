//               该文件中包含客户端需要处理的函数和事件

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
var mySql = new SqlClass();
//使得从该窗口弹出的窗口能够聚焦
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
	if(mOperate=="")
	{
		addClick();
	}
  if (!beforeSubmit())
    return false;
	
/*	if (!changeGroup())
		return false;
  if(mOperate=="INSERT||MAIN")
  {
  	if(!CheckBranchValid())
  		return false;
  }
*/  
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.fmtransact.value=mOperate;
//  if (fm.hideOperate.value=="")
//  {
//    alert("操作控制数据丢失！");
//  }
  //showSubmitFrame(mDebug);
  //fm.fmtransact.value = "INSERT||COM";
  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //执行下一步操作
  }
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
	  initForm();
  }
  catch(re)
  {
  	alert("在OLDCom.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//提交前的校验、计算
function beforeSubmit()
{
  var ComCode;
  var ComGrade;
  //添加操作
  if (!verifyInput()) return false;
  //alert(mOperate);
  if (trim(mOperate) == 'INSERT||COM')
  {
    //校验机构代码是否存在
  /*  var strSQL = "";
    strSQL = "select Comcode from LDCom where 1=1 "
	    + getWherePart('ComCode')*/
		
	mySql = new SqlClass();
	mySql.setResourceName("sys.ComInputSql");
	mySql.setSqlId("ComSql1");
	mySql.addSubPara(fm.ComCode.value );     
    var strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
    //alert(strSQL);
    if (strQueryResult) {  	
  		alert('该管理机构代码已存在！');
  		document.all('ComCode').value = '';
  		return false;
  	}
   //校验代码输入和级别是否一致
    ComCode = document.all('ComCode').value;
    ComGrade = document.all('ComGrade').value;
    if(ComGrade=='01') //总公司
    {
   		if(ComCode.length!=2)
   		{
   			alert("该级别的机构编码长度不符合要求！");
   			document.all('ComCode').value = '';
   			return false;
   		}
   	}
   	else if(ComGrade=='02') //分公司
   	{	
   		if(ComCode.length!=4)
   		{
   			alert("该级别的机构编码长度不符合要求！");
   			document.all('ComCode').value = '';
   			return false;
   		}
   			
   }
		else if(ComGrade=='03') //中支
		{
   		if(ComCode.length!=6)
   		{
   			alert("该级别的机构编码长度不符合要求！");
   			document.all('ComCode').value = '';
   			return false;
   		}
   						
		}
		else if (ComGrade=='04')//营销服务部
		{
			if(ComCode.length!=8)
   		{
   			alert("该级别的机构编码长度不符合要求！");
   			document.all('ComCode').value = '';
   			return false;
   		}
		}
  }
  
  return true;
}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  mOperate="INSERT||COM";
  //showDiv(operateButton,"false");
  //showDiv(inputButton,"true");
  //fm.fmtransact.value = "INSERT||COM";
  submitForm();
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  if (confirm("您确实想修改该记录吗?"))
  {
  
  mOperate = "UPDATE||COM";
  submitForm();
  }
  else
  {
    mOperate="";
    alert("您取消了修改操作！");
  }
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  mOperate="QUERY||MAIN";
  showInfo=window.open("./ComQuery.html","" ,"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的删除代码
  if (confirm("您确实想删除该记录吗?"))
  {
  var i = 0;
  var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  //showSubmitFrame(mDebug);
  fm.fmtransact.value = "DELETE||COM";

  document.getElementById("fm").submit(); //提交
  initForm();
  }
  else
  {
    //mOperate="";
    alert("您取消了删除操作！");
  }
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
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all( 'ComCode' ).value = arrResult[0][0];
		document.all('OutComCode').value = arrResult[0][1];
		document.all('Name').value = arrResult[0][2];
		document.all('ShortName').value = arrResult[0][3];
		document.all('Address').value = arrResult[0][4];
		document.all('ZipCode').value = arrResult[0][5];
		document.all('Phone').value = arrResult[0][6];
		document.all('Fax').value = arrResult[0][7];
		document.all('EMail').value = arrResult[0][8];
		document.all('WebAddress').value = arrResult[0][9];
		document.all('SatrapName').value = arrResult[0][10];
		document.all('Sign').value = arrResult[0][11];
		//document.all('ComAreaType').value = arrResult[0][12];
		document.all('ComCitySize').value = arrResult[0][12];
	    document.all('UpComCode').value = arrResult[0][13];
	    document.all('ComGrade').value = arrResult[0][14];
	    document.all('IsDirUnder').value = arrResult[0][15];
	  
	    //showOneCodeName('ComAreaType','ComAreaType','ComAreaTypeName');
	    showOneCodeName('ComCitySize','ComCitySize','ComCitySizeName');
	    showOneCodeName('ComLevel','ComGrade','ComGradeName');
	    showOneCodeName('comdirectattr','IsDirUnder','IsDirUnderName');
	}
}

function SelectCom(){
    showInfo=window.open('../treeCom/jsp/SelectSysCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
	
  
}