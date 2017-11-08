//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
//var turnPage1 = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeRCInputSql";

function returnParent()
{
  top.opener.getEdorItemGrid();
  top.close();
  top.opener.focus();
}

function edorTypeRCSave()
{
    var tRemindMode = fm.RemindMode.value;
    var tOldRemindMode = fm.OldRemindMode.value;
    
    if(tRemindMode == null || tRemindMode =="")
    {
        alert("请选择变更交费提醒方式");
        fm.RemindMode.focus();
        return;
    }
    if(tRemindMode == tOldRemindMode){
    	 alert("变更交费提醒方式与原交费提醒方式一样，请变更！");
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

    //alert(FlagStr);
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
function initRCQuery()
{
    //查询已录入金额
//    var strsql = "select edorstate from lpedoritem where edorno = '" + document.all('EdorNo').value + "' and edortype = 'RC' and contno = '"+document.all('ContNo').value+"'";
    
    var strsql = "";
	var sqlid1="PEdorTypeRCInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all('EdorNo').value);//指定传入的参数
	mySql1.addSubPara(document.all('ContNo').value);
	strsql=mySql1.getString();
    
    var ret = easyExecSql(strsql);
    var state = ret[0][0];
//    strsql = "select nvl(XQremindflag,'1') from lccont where contno = '"+document.all('ContNo').value+"'";
		
	var sqlid2="PEdorTypeRCInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(document.all('ContNo').value);//指定传入的参数
	strsql=mySql2.getString();
		
		ret = easyExecSql(strsql);
		fm.OldRemindMode.value = ret[0][0];
    if(state != "3")//等待录入。
    {
//    	strsql = "select XQremindflag from lpcont where contno = '"+document.all('ContNo').value+"' and edorno = '"+fm.EdorNo.value+"'";
			
	var sqlid3="PEdorTypeRCInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(document.all('ContNo').value);//指定传入的参数
	mySql3.addSubPara(fm.EdorNo.value);
	strsql=mySql3.getString();
			
			ret = easyExecSql(strsql);
			fm.RemindMode.value = ret[0][0];
    }
    else
    {

    }
    
    if(fm.OldRemindMode.value == '1')
    {
    	fm.OldRemindModeName.value = "需要交费提醒";
    }else{
    	fm.OldRemindModeName.value = "不需要交费提醒";
    }
    
    if(fm.RemindMode.value == '1')
    {
    	fm.RemindModeName.value = "需要交费提醒";
    }
    if(fm.RemindMode.value == '0'){
    	fm.RemindModeName.value = "不需要交费提醒";
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