//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
//var turnPage1 = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeLRInputSql";

function returnParent()
{
  top.opener.getEdorItemGrid();
  top.close();
  top.opener.focus();
}

function edorTypeLRQuery()
{
    alert("Wait...");
}
function edorTypeLRSave()
{
    var tReason = fm.GoonGetMethod1.value;
    var tGetMoney = fm.GetMoney.value;
    
    if(tReason == null || tReason =="")
    {
        alert("请先选择补发原因！");
        fm.GoonGetMethod1.focus();
        return;
    }
    
    if(trim(tGetMoney)!="" && !isNumeric(tGetMoney))
		{
				errorMessage("请输入合法的数字");
				fm.GetMoney.focus();
				fm.GetMoney.select();
				return;
		}
		
		if(tGetMoney < 0)
		{
			alert("交费金额不能小于0！");
			fm.GetMoney.focus();
			fm.GetMoney.select();
			return;
		}


    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.all('fmtransact').value = "INSERT";
    fm.submit();

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
	var iHeight=250;     //弹出窗口的高度; 
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
    showmoney();
    queryBackFee();
    //alert(FlagStr);
    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //执行下一步操作
  }
}

function showmoney(){
		 
//     		 var Str3 = "select getmoney from lpedoritem where edorno = '" + document.all('EdorNo').value + "' and edortype = 'LR'";
    		 
    var Str3 = "";
	var sqlid1="PEdorTypeLRInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all('EdorNo').value);//指定传入的参数
	Str3=mySql1.getString();
    		 
    		 var arr = easyExecSql(Str3);
    	 	 if(arr != null)
    	 	 {
         	  document.all('GetMoney').value = arr[0][0];
    		 }
    		 else
    		 {
         	  document.all('GetMoney').value = "10.0";
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

//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  alert("update click");
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
    alert("query click");
      //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("delete click");
}
//---------------------------
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
function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;
    var ActivityID = '0000000003';
    var OtherNo = top.opener.document.all("OtherNo").value;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}


function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}
function initLRQuery()
{
    //查询已录入金额
//    var strsql = "select edorstate from lpedoritem where edorno = '" + document.all('EdorNo').value + "' and edortype = 'LR' and contno = '"+document.all('ContNo').value+"'";
    
    var strsql = "";
	var sqlid2="PEdorTypeLRInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(document.all('EdorNo').value);//指定传入的参数
	mySql2.addSubPara(document.all('ContNo').value);
	strsql=mySql2.getString();
    
    var ret = easyExecSql(strsql);
    var state = ret[0][0];
    if(state != "3")//等待录入。
    {
/*        strsql = "select nvl(sum(getmoney),0.0) from ljsgetendorse "
               + " where contno = '"+fm.ContNo.value+"' "
               + " and endorsementno = '"+fm.EdorNo.value+"' "
               + " and feeoperationtype = '"+fm.EdorType.value+"' "
               + " and feefinatype = 'GB' and subfeeoperationtype = 'P012' ";
*/        
	var sqlid3="PEdorTypeLRInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql3.addSubPara(fm.EdorNo.value);
	mySql3.addSubPara(fm.EdorType.value);
	strsql=mySql3.getString();
        
        var brr = easyExecSql(strsql);
        if(brr)
        {
            fm.GetMoney.value = pointTwo(brr[0][0]);
        }
        //查询已录入补发原因
/*        strsql = "select trim(edorreasoncode),edorreason from lpedoritem "
           + " where contno = '"+fm.ContNo.value+"' "
           + " and edorno = '"+fm.EdorNo.value+"' "
           + " and edortype = '"+fm.EdorType.value+"' ";
*/        
    var sqlid4="PEdorTypeLRInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql4.addSubPara(fm.EdorNo.value);
	mySql4.addSubPara(fm.EdorType.value);
	strsql=mySql4.getString();
        
        brr = easyExecSql(strsql);
        if(brr)
        {
            fm.GoonGetMethod1.value = brr[0][0];
            fm.GoonGetMethod1Name.value = brr[0][1];
        }
      }
      else
      {
     			document.all('GetMoney').value = "10.0";
     	}
}

function queryLostTimes()
{
	var tQuerySQL,arrResult;
/*	tQuerySQL = "select nvl(LostTimes,0)||'次' "
             +   "from lCCont "
             +  "where 1 = 1 "
             +     getWherePart("ContNo", "ContNo");
*/  
 	var sqlid5="PEdorTypeLRInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(fm.ContNo.value);//指定传入的参数
	tQuerySQL=mySql5.getString();
  
  try
    {
        arrResult = easyExecSql(tQuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保单补发信息出现异常！ ");
        return;
    }
    if(arrResult != null)
    {
    	fm.LostTimes.value = arrResult[0][0];
    }
}

function initDivLayer()
{
    var sAppobj;
    try
    {
        sAppobj = document.getElementsByName("AppObj")[0].value;
    }
    catch (ex) {}
    if (sAppobj != null)
    {
        if (sAppobj.trim() == "I")
        {
            try
            {
                showOneCodeName('PEdorType', 'EdorType', 'EdorTypeName');
                document.all("divGetMoneyTitle").style.display = "";
                document.all("divGetMoneyInput").style.display = "";
            }
            catch (ex) {}
        }
        else if (sAppobj.trim() == "G")
        {
            try
            {
                showOneCodeName('GEdorType', 'EdorType', 'EdorTypeName');
                document.all("divGetMoneyTitle").style.display = "none";
                document.all("divGetMoneyInput").style.display = "none";
            }
            catch (ex) {}
        }
    }
}