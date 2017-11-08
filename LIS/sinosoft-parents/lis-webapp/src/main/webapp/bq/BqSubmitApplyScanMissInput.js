//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="1";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var returnPage= new turnPageClass();
//提交完成后所作操作
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
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    tSaveFlag ="0";
}

////初始化"接收呈报队列"表格------呈报类型 missionprop6 = '0'--中心到分公司
function easyQueryClick()
{ 
	var sqlid817145124="DSHomeContSql817145124";
var mySql817145124=new SqlClass();
mySql817145124.setResourceName("bq.BqSubmitApplyScanMissInputSql");//指定使用的properties文件名
mySql817145124.setSqlId(sqlid817145124);//指定使用的Sql的id
mySql817145124.addSubPara();//指定传入的参数
var strSQL=mySql817145124.getString();
	
//	var strSQL ="select missionid,MissionProp2,CreateOperator,MakeDate,maketime from LWCPMission where ActivityStatus='1'";
    turnPage.pageLineNum=10;    //每页5条
    turnPage.queryModal(strSQL,LLSubmitApplyGrid);
}


//LLSubmitApplyGrid点击事件
function LLSubmitApplyGridClick()
{
    var i = LLSubmitApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tSubNo = LLSubmitApplyGrid.getRowColData(i,1); 
        
        
    }
    //LoadFlag=1:接收呈报，并处理
    var strUrl= "BqSubmitApplyDealInput.jsp?LoadFlag=5&flag=5&SubNo="+tSubNo;    
    //location.href="LLSubmitApplyDealInput.jsp?";
    location.href=strUrl;
}
//初始化“反馈队列”表格  呈报类型 missionprop6 = '2'--总公司到分公司
function ReQueryClick()
{
	var sqlid817145356="DSHomeContSql817145356";
var mySql817145356=new SqlClass();
mySql817145356.setResourceName("bq.BqSubmitApplyScanMissInputSql");//指定使用的properties文件名
mySql817145356.setSqlId(sqlid817145356);//指定使用的Sql的id
mySql817145356.addSubPara(usercode);//指定传入的参数
var strSQL=mySql817145356.getString();
	
//	var strSQL ="select SubNo,SubCount,SubType,GrpContNo,SubState,CustomerName from LCSubmitApply where Operator='"+usercode+"' and HaveDealed in ('1','0')";//没有处理完毕的
    turnPage.pageLineNum=5;    //每页5条
    returnPage.queryModal(strSQL,ReLLSubmitApplyGrid);
}

//LLSubmitApplyGrid点击事件
function ReLLSubmitApplyGridClick()
{
    var i = ReLLSubmitApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tSubNo = ReLLSubmitApplyGrid.getRowColData(i,1); 
        
        
        
    }
    var strUrl= "BqSubmitApplyDealInput.jsp?LoadFlag=3&SubNo="+tSubNo;    
//    location.href="LLSubmitApplyDealInput.jsp?";
    location.href=strUrl;
}
//申请按纽事件
function apply()
{
	//var arrQueryResult = easyExecSql("select * from lcgrpcont where grpcontno='" + fm.PrtNo.value + "'", 1, 0);
  //
  //if(arrQueryResult==null)
  //{
  //	alert("不存在此合同号！");
  //	return;
  //}
	
	var strUrl= "BqSubmitApplyDealInput.jsp?LoadFlag=2";      
  location.href=strUrl;
}