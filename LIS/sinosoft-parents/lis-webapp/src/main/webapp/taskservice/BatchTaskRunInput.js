
var showInfo;                          //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();    //全局变量, 查询结果翻页, 必须有
var isThisSaved = false;               //全局变量, 标记是否已成功保存过

/**
 * 执行批处理-测试
 */
function exeTask()
{
    var tExeDate = document.getElementsByName("tExeDate")[0].value;
 	if (tExeDate == null || trim(tExeDate) == "") {
 		alert("请输入您要执行的批处理的日期！"); 
 		return;
 	} 
 	
 	var tTaskClass = document.getElementsByName("tTaskCode")[0].value;
 	if (tTaskClass == null || trim(tTaskClass) == "") {
 		alert("请输入您要执行的批处理的对应任务编码！");
 		return;
 	}  
    
 	//add by fengxueqian 2014-03-10 增加DivLockScreen遮罩层的处理 加锁-start
	lockScreen('DivLockScreen');
	//add by fengxueqian 2014-03-10 增加DivLockScreen遮罩层的处理 加锁-end
 	
    var MsgContent = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(MsgContent));
    showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
    document.forms(0).action = "BatchTaskRunSave.jsp";
    document.forms(0).submit();   
}

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{ 
    try { showInfo.close(); } catch (ex) {}
    
    //add by fengxueqian 2014-03-10 增加DivLockScreen遮罩层的处理 解锁-start
	unlockScreen('DivLockScreen');
	//add by fengxueqian 2014-03-10 增加DivLockScreen遮罩层的处理 解锁-end
    
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + encodeURI(encodeURI(MsgContent));
            showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + encodeURI(encodeURI(MsgContent));
            showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + encodeURI(encodeURI(MsgContent));
            showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
            break;
    }
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        isThisSaved = true;
    }
}


//<!-- JavaScript Document END -->
/**
	mysql工厂，根据传入参数生成Sql字符串
	
	sqlId:页面中某条sql的唯一标识
	param:数组类型,sql中where条件里面的参数
**/
function wrapSql(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("operfee.TaskTestInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}
