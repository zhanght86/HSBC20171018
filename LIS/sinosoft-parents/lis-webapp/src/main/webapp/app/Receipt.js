var turnPage = new turnPageClass();
var arrResult1;
var showInfo;
/**
 * @author lzj at 2016/4/29
 * @param id
 * @returns document.getElementById(id)
 * @desc 用于简化后续操作
 */
function $(id){
	return document.getElementById(id);
}
/**
 * add by lzj at 2016/4/29
 * @desc 根据印刷号(保单号)和签单日期来查询
 */
function easyQueryClick(){
	// 初始化表格
	initPolGrid();
	//-----------------
	var strSQL ="";
	var sqlid1="ReceiptSql1";
	var mySql1=new SqlClass();
	/*if(($("ProposalContNo").value==null||trim($("ProposalContNo").value)=='') && ($("ValidStartDate").value==null||trim($("ValidStartDate").value)=='')){
		alert('请录入印刷号或签单日期!');
		return false;
	}*/
	mySql1.setResourceName("app.ReceiptSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara($("ProposalContNo").value);//指定传入的参数
	mySql1.addSubPara($("ValidStartDate").value);//指定传入的参数
	strSQL=mySql1.getString();
	var flag=turnPage.queryModal(strSQL, PolGrid);
	if(flag==false){
		alert("提示:没有您要找的查询结果！");
		return false;
	}
  return true;
}
/**
 * 判断多选框checkbox是否被选中
 */
function isSelect(){
	var flag=false;
	for(var i=0;i<PolGrid.mulLineCount;i++){
		if(PolGrid.getChkNo(i)){
			flag=true;
		}
	}
	if(flag==true){
		return true;
	}else{
		alert("请选择要更新的数据、再执行更新操作！");return false;
	}
}

/**
 * @author add by lzj  at 2016/04/29
 * @returns {Boolean}
 * @desc 方法updateData()用于更新、于2016/05/05将更新操作提交至ui/app/ReceiptUpdate.jsp进行处理
 */
function updateData(){
	var getSelectChkRow=0;
	var strSQL="";
	var iArray = new Array(0);
	var mySql185=new SqlClass();
	mySql185.setResourceName("app.ContInputSql"); //指定使用的properties文件名
	mySql185.setSqlId(sqlid185);//指定使用的Sql的id
	var str="update lccont l set l.accname='宋江' where l.prtno in(";
	var strPro="";
	for(var i=0;i<PolGrid.mulLineCount;i++){
		if(PolGrid.getChkNo(i)){ 
			++getSelectChkRow;
			for(var j=0;j<getSelectChkRow;j++){
				if(iArray.indexOf(PolGrid.getRowColData(i,3)) == -1){
					iArray.push(PolGrid.getRowColData(i,3));
			}
		}
	}
}
	//主要用于拼接sql语句
	var len=iArray.length;
	for(var i=0;i<len;i++){
		if(i < len-1){
			strPro=strPro+"'"+iArray[i]+"',";
		}else{
			strPro=strPro+"'"+iArray[i]+"')";
		}
	}
	strSQL=str+strPro;
	if(confirm("确定更新你选中的"+getSelectChkRow+"行数据?")){
		try{
		arrResult1 = easyExecSql(strSQL,1,0);
		}catch(ex){
			alert(ex);
		}
		easyQueryClick();
		}else{
		return false;
	}
}
/**
 * 检查回销日期是否为空 add by lzj at 2016/05/06
 * @returns {Boolean}
 */
function isNull(){
	if($("ValidStartDate1").value==null || trim($("ValidStartDate1").value)==''){
		alert("请选择回销日期!");return false;
	}
	/*if(($("ProposalContNo").value==null||trim($("ProposalContNo").value)=='') && ($("ValidStartDate").value==null||trim($("ValidStartDate").value)=='')){
		alert("请先进行查询、然后进行更新操作!");return false;
	}*/
	return true;
}
/**
 * 如果回销日期不为空、并且有数据要选择更新时则提交至ReceiptUpdate.jsp进行更新操作
 */
function submitForm(){
	if(isSelect() && isNull()){
		var showStr="正在修改数据，请您稍候并且不要修改屏幕上的值或链接其他页面...";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		fm.action="ReceiptUpdate.jsp"; //提交给ReceiptUpdate.jsp进行更新操作
		$("fm").submit(); //提交
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ){
	//无论打印结果如何，都重新激活打印按钮
	showInfo.close();
	if (FlagStr == "Fail" ){
		//如果打印失败，展现错误信息
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
	}else{
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
	easyQueryClick();
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