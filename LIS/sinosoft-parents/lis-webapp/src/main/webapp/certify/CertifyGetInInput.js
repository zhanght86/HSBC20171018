//该文件中包含客户端需要处理的函数和事件
var showInfo;
var turnPage = new turnPageClass();
var tResourceName="certify.CertifyGetInInputSql";
//查询待入库单证
function queryClick()
{
  initCertifyListGrid();
 /* var strSql = "select * from (select a.certifycode, (select certifyname from lmcertifydes where certifycode = a.certifycode),"
			 +" '00' sendoutcom, 'A' || a.managecom receivecom, a.startno, a.endno, a.sumcount,"
			 +" (case a.managecom when '86' then '总公司印刷' else '分公司自印' end), a.prtno, '' Reason,a.printdate makedate"
			 +" from LZCardPrint a where a.state = '1'"
			 + getWherePart('a.certifycode', 'certifycode')
	 		 + getWherePart('a.managecom', 'managecom')
	 		 +" and exists (select 1 from lmcertifydes where certifycode = a.certifycode "
 			 + getWherePart('certifyclass', 'CertifyClass') 
 			 + getWherePart('CertifyClass2', 'CertifyClass2') + ")"
			 +" union "
			 +" select b.certifycode, (select certifyname from lmcertifydes where certifycode = b.certifycode),"
			 +" b.sendoutcom, b.receivecom, b.startno, b.endno, b.sumcount, "
			 +" (case when b.Reason is null then '上级发放' when b.Reason = '2' then '调拨' else '下级退库' end), '' prtno, b.Reason,b.makedate"
			 +" from lzcard b where b.stateflag = '1'"
			 + getWherePart('b.certifycode', 'certifycode')
			 +" and exists (select 1 from lmcertifydes where certifycode = b.certifycode "
 			 + getWherePart('certifyclass', 'CertifyClass') 
 			 + getWherePart('CertifyClass2', 'CertifyClass2') + ")";
  if(fm.managecom.value!=""){
  	strSql+=" and b.receivecom='A"+fm.managecom.value+"'";
  }
  strSql+=" ) order by makedate,certifycode,sendoutcom,receivecom,startno";*/
    var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value,document.all('managecom').value,document.all('CertifyClass').value
                                       ,document.all('CertifyClass2').value,document.all('CertifyCode').value,document.all('CertifyClass').value
                                       ,document.all('CertifyClass2').value,fm.managecom.value]);
  
  turnPage.pageDivName = "divCertifyList";
  turnPage.queryModal(strSql, CertifyListGrid);
  divCertifyList.style.display='';
  if(CertifyListGrid.mulLineCount==0){
    alert("没有待入库的单证！");
    return false;  
  }  	    
}

//提交，确认入库
function submitForm()
{
  //避免双击
  fm.btnOp1.disabled = true;
  fm.btnOp2.disabled = true;
  
  var nSelectedCount = CertifyListGrid.getChkCount();
  if (nSelectedCount == null || nSelectedCount <= 0)
  {
    alert("请至少选择一个要入库的单证！ ");
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;
    return false;
  }
  
  if( verifyInput()==false || CertifyListGrid.checkValue()==false) {
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;
    return false;  
  }
    
  if (confirm("您确实想确认入库吗?")){
    try{
	  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();	  
		fm.operateFlag.value = "INSERT";
	    document.getElementById("fm").submit(); //提交
    }catch(ex) {
  	  showInfo.close( );
  	  alert(ex);
    }
  }else{
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;
    alert("您取消了入库操作！");
  }	
}

//提交，拒绝入库
function cancelGetIn()
{
  //避免双击
  fm.btnOp1.disabled = true;
  fm.btnOp2.disabled = true;
  
  var nSelectedCount = CertifyListGrid.getChkCount();
  if (nSelectedCount == null || nSelectedCount <= 0)
  {
    alert("请至少选择一个要拒绝入库的单证！ ");
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;
    return false;
  }
  
  if( verifyInput()==false || CertifyListGrid.checkValue()==false) {
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;
    return false;  
  }  
    
  for (var i=0;i<CertifyListGrid.mulLineCount;i++ )
  {
    if (CertifyListGrid.getChkNo(i))
    {
	  if(CertifyListGrid.getRowColData(i,3)=="00"){
	    alert("单证编码["+CertifyListGrid.getRowColData(i,1)+"]为印刷入库的单证，不允许拒绝！");
    	fm.btnOp1.disabled = false;
    	fm.btnOp2.disabled = false;	    
        return false;
	  }
	  if(CertifyListGrid.getRowColData(i,10)==""){
	    alert("单证编码["+CertifyListGrid.getRowColData(i,1)+"]请录入拒绝入库原因！");
        fm.btnOp1.disabled = false;
    	fm.btnOp2.disabled = false;
        return false;
	  }
	  if(CertifyListGrid.getRowColData(i,10)!="3" && CertifyListGrid.getRowColData(i,10)!="4" && CertifyListGrid.getRowColData(i,10)!="5" ){
	    alert("单证编码["+CertifyListGrid.getRowColData(i,1)+"]拒绝入库原因只能为3、4、5！");
        fm.btnOp1.disabled = false;
    	fm.btnOp2.disabled = false;
        return false;
	  }
    }
  }
  
  if (confirm("您确实想拒绝入库吗?")){
  try {
	  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
      var name='提示';   //网页名称，可为空; 
	  var iWidth=550;      //弹出窗口的宽度; 
	  var iHeight=250;     //弹出窗口的高度; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();	  
	  fm.operateFlag.value = "CANCEL";
	  document.getElementById("fm").submit(); //提交
  } catch(ex) {
  	showInfo.close( );
  	alert(ex);
  }
  }else{
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;  
    alert("您取消了入库操作！");
  }	  
  
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content, TakeBackNo )
{
  showInfo.close();	
  fm.operateFlag.value = "";//操作类型标志，INSERT为确认入库，CANCEL为拒绝入库
  fm.btnOp1.disabled = false;
  fm.btnOp2.disabled = false;
  if (FlagStr == "Fail" ) {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");    
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }else{ 
	content="操作成功！";	
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;	    
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	queryClick();//重新查询未入库数据
  }
}
