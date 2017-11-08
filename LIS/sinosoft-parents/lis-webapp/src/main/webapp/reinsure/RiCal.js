//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var currentTask;
var turnPage1 = new turnPageClass();  


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	initForm();
}

function initTaskList(){
	//var strSQL = " select count(*) from RIWFLog a where a.tasktype='01' and a.nodestate<>'99' ";
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.RiCalSql"); //指定使用的properties文件名
	 	mySql100.setSqlId("RiCalSql100");//指定使用的Sql的id
		mySql100.addSubPara("1");
	var strSQL=mySql100.getString();
	arrResult = easyExecSql(strSQL);
	if(arrResult=='0'){
		currentTask='0';
		//strSQL = " select '',a.AccumulateDefNO,'','','','' from RIAccumulateDef a where a.state='01' order by AccumulateDefNO";
		
		var mySql101=new SqlClass();
			mySql101.setResourceName("reinsure.RiCalSql"); //指定使用的properties文件名
	 		mySql101.setSqlId("RiCalSql101");//指定使用的Sql的id
	    	mySql101.addSubPara("1");
	    	strSQL=mySql101.getString();
		arrResult = easyExecSql(strSQL);
		displayMultiline(arrResult,TaskListGrid);
	}
	else{
		currentTask='1';
		//strSQL = " select a.batchno,a.taskcode,a.startdate,a.enddate,decode(a.nodestate,'00','任务申请','01','业务数据提取','02','数据校验','03','风险计算','04','再保方分配','08','分保数据生成','10','初始化参数','11','计算项计算','12','接口表数据备份','99','处理完成','不知名状态'),a.nodestate from RIWFLog a where a.tasktype='01' and a.nodestate<>'99' ";
		
		var mySql102=new SqlClass();
			mySql102.setResourceName("reinsure.RiCalSql"); //指定使用的properties文件名
	 		mySql102.setSqlId("RiCalSql102");//指定使用的Sql的id
	    	mySql102.addSubPara("1");
	    	strSQL=mySql102.getString();		
		arrResult = easyExecSql(strSQL);
		displayMultiline(arrResult,TaskListGrid);
		fm.StartDate.value=arrResult[0][2];
		fm.EndDate.value=arrResult[0][3];
	}
	
	return true;
}

function SubmitForm()
{
	if(!verifyInput()){
		return false;
	}
	if(!verifyInput1()){
		return false;
	}
	if(fm.ProcessType.value=='00'){
		if(!taskApply()){
			return false;
		}
	}
	if(fm.ProcessType.value=='01'){
		if(!distillData()){
			return false;
		}
	}
	if(fm.ProcessType.value=='02'){
		if(!checkData()){
			return false;
		}
	}
	if(fm.ProcessType.value=='03'){
		if(!calData()){
			return false;
		}
	}
	if(fm.ProcessType.value=='98'){
		if(!delTask()){
			return false;
		}
	}
}

function taskApply(){
	if(currentTask=='1'){
		myAlert(""+"对不起。当前任务未完成，不能申请新处理任务。"+"");
		return false;
	}
	
	if(fm.StartDate.value == "" || fm.EndDate.value == "")
	{
		return false;
		myAlert(""+"请录入起始日期和终止日期!"+"");
	}
	var selCount=TaskListGrid.mulLineCount;
	var chkFlag=false; 
	
	for (i=0;i<selCount;i++){
		if(TaskListGrid.getChkNo(i)==true){
			chkFlag=true;
			if(TaskListGrid.getRowColData(i,6)>='01'){
				myAlert(""+"所选任务全部进行过数据提取操作"+","+"不能重复提取。"+"");
				return false;
			}
		}
	}
	if (chkFlag==false)
	{
		myAlert(""+"请选中要申请的任务"+"");
		return false;
	}
	
	try {
		var i = 0;
    var showStr=""+"正在进行申请任务操作	，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
    fm.submit();
    
  }catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function distillData(){
	if(currentTask=='0'){
		myAlert(""+"对不起。当前没有待处理任务，不能进行提数操作。"+"");
		return false;
	}
	var selCount=TaskListGrid.mulLineCount;
	var chkFlag=false; 
	for (i=0;i<selCount;i++){
		if(TaskListGrid.getChkNo(i)==true){
			chkFlag=true;
			if(TaskListGrid.getRowColData(i,6)>='01'){
				myAlert(""+"所选任务全部进行过数据提取操作"+","+"不能重复提取。"+"");
				return false;
			}
		}
	}
	if (chkFlag==false)
	{
		myAlert(""+"请选中要处理的任务"+"");
		return false;
	}	
	try {
		var i = 0;
    var showStr=""+"正在进行业务数据提取，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
    fm.submit();
    
  }catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
	
}

function checkData(){
	if(currentTask=='0'){
		myAlert(""+"对不起。当前没有待处理任务，不能进行数据校验操作。"+"");
		return false;
	}
	var selCount=TaskListGrid.mulLineCount;
	var chkFlag=false; 
	for (i=0;i<selCount;i++){
		if(TaskListGrid.getChkNo(i)==true){
			chkFlag=true;
		}
	}
	if (chkFlag==false)
	{
		myAlert(""+"请选中要处理的任务"+"");
		return false;
	}
	try {
		var i = 0;
    var showStr=""+"正在进行数据校验，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
    fm.submit();
    
  }catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function calData(){
	if(currentTask=='0'){
		myAlert(""+"对不起。当前没有待处理任务，不能进行数据校验操作。"+"");
		return false;
	}
	var chkFlag=false; 
	var selCount=TaskListGrid.mulLineCount;
	for (i=0;i<selCount;i++){
		if(TaskListGrid.getChkNo(i)==true){
			chkFlag=true;
			if(TaskListGrid.getRowColData(i,6)<'01'){
				myAlert(""+"请提取业务数据后再进行再保计算。"+"");
				return false;
			}
		}
	}
	if (chkFlag==false)
	{
		myAlert(""+"请选中要处理的任务"+"");
		return false;
	}
	try {
		var i = 0;
    var showStr=""+"正在进行再保计算，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
    fm.submit();
    
  }catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function delTask(){
	if(currentTask=='0'){
		myAlert(""+"对不起。当前没有待处理任务，不能进行删除操作。"+"");
		return false;
	}
	var chkFlag=false; 
	var selCount=TaskListGrid.mulLineCount;
	for (i=0;i<selCount;i++){
		if(TaskListGrid.getChkNo(i)==true){
			chkFlag=true;
		}
	}
	if (chkFlag==false)
	{
		myAlert(""+"请选中要处理的任务"+"");
		return false;
	}
	if (confirm(""+"将删除选中的任务日志及所有该任务的业务数据，确定吗?"+"")) 
	{
    try {
		var i = 0;
    var showStr=""+"正在进行任务删除操作，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
    fm.submit();
	  }catch(ex){
	  	showInfo.close( );
	  	myAlert(ex);
	  }
	}
}

function verifyInput1() //UPDATE 校验
{	//alert(currentDate);
	if(compareDate(fm.StartDate.value,fm.EndDate.value)==1){
		myAlert(""+"起始日期不能大于终止日期!"+"");
		return false;
	}
	if(compareDate(fm.EndDate.value,currentDate)==1){
		myAlert(""+"终止日期不能大于当前日期！"+"");
		return false;
	}
	return true;
	
}
