//该文件中包含客户端需要处理的函数和事件

var showInfo;
var turnPage = new turnPageClass();
var tResourceName="certify.CertifySendOutReplyInputSql";
//查询待批复的增领申请
function queryClick()
{
  initCertifyList();
  /*var strSql = "select a.applyno, a.certifycode, (select certifyname from lmcertifydes b where b.certifycode = a.certifycode),"
			 +"(select nvl(sum(c.sumcount), 0) from lzcard c where c.certifycode = a.certifycode and c.receivecom = a.sendoutcom and c.stateflag in ('2', '3')),"
			 +"(select nvl(sum(d.sumcount), 0) from lzcard d where d.certifycode = a.certifycode and d.receivecom = a.receivecom and d.stateflag in ('2', '3')),"
			 +" a.startno, a.endno, a.sumcount, a.reason, a.receivecom, (select managecom from laagent where 'D'||agentcode = a.receivecom),"
          	 +" a.operator,a.makedate from lzcardapp a where a.OperateFlag='1' and a.stateflag='1' "
			 + getWherePart('a.sendoutcom', 'SendOutCom')
			 + getWherePart('a.receivecom', 'ReceiveCom')
			 + getWherePart('a.ApplyCom', 'ComCode', 'like');
  strSql+=" and (a.sendoutcom ='A" + fm.ComCode.value + "' or a.sendoutcom like 'B" + fm.ComCode.value +"%')";
  strSql+=" order by a.makedate,a.certifycode";*/
  var strSql = wrapSql(tResourceName,"querysqldes3",[fm.SendOutCom.value,fm.ReceiveCom.value,fm.ComCode.value,fm.ComCode.value,fm.ComCode.value]);
  
  turnPage.pageDivName = "divCertifyList";
  turnPage.queryModal(strSql, CertifyList);
  divCertifyList.style.display='';	 
  if(CertifyList.mulLineCount==0){
 	 alert("没有待批复的记录！");
     return false;  
  } 
}

//提交，保存按钮对应操作
function submitForm()
{
  CertifyList.delBlankLine("CertifyList");
  var nSelectedCount = CertifyList.getChkCount();
  if (nSelectedCount == null || nSelectedCount <= 0)
  {
    alert("请至少选择一个要批复的单证！");
    return false;
  }
  if( verifyInput()==false || CertifyList.checkValue()==false ) 
  {
  	 return false;
  }
  
  if (confirm("您确认同意增领操作吗?")){
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
	  	fm.operateFlag.value='true';
	  	document.getElementById("fm").submit(); //提交	 
    } catch(ex) {
  	  showInfo.close( );
  	  alert(ex);
    }
  }else{
    alert("您取消了同意增领操作！");
  }	  
}

function submitForm2()
{
  CertifyList.delBlankLine("CertifyList");
  var nSelectedCount = CertifyList.getChkCount();
  if (nSelectedCount == null || nSelectedCount <= 0)
  {
    alert("请至少选择一个要批复的单证！");
    return false;
  }
  if( verifyInput()==false || CertifyList.checkValue()==false ) 
  {
  	 return false;
  }
    
  if (confirm("您确认增领批复操作吗?")){
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
	  	fm.operateFlag.value='false';
	  	document.getElementById("fm").submit(); //提交	 
    } catch(ex) {
  	  showInfo.close( );
  	  alert(ex);
    }
  }else{
    alert("您取消了增领批复操作！");
  }	  
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  fm.btnOp.disabled = false;  	
  if(FlagStr == "Fail" ) {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");    
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  }else { 
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;	    
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  queryClick();
  fm.note.value = '';
}

function afterCodeSelect( cName, Filed)
{
  if(cName=='DepartmentNo')//选择部门编码后
  {
    if(fm.Department.value==null || fm.Department.value==""){
      alert("请选择【部门来源】");
    }else if(fm.Department.value=="1"){
      fm.SendOutCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
      fm.SendOutCom.readOnly = true;
      fm.ReceiveCom.value = "";
      fm.ReceiveCom.readOnly = false;
    }else if(fm.Department.value=="2"){
      fm.ReceiveCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
      fm.ReceiveCom.readOnly = true;
      fm.SendOutCom.value = "";
      fm.SendOutCom.readOnly = false;        
    }else{      
      alert("选择【部门编码】有误");
      fm.DepartmentNo.value="";
    }
  }
  if(cName=='Department')//选择部门后
  {
    if(fm.DepartmentNo.value!=null && fm.DepartmentNo.value!=""){
 	  if(fm.Department.value=="1"){
        fm.SendOutCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
        fm.SendOutCom.readOnly = true;
        fm.ReceiveCom.value = "";
        fm.ReceiveCom.readOnly = false;         
      }else if(fm.Department.value=="2"){
        fm.ReceiveCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
        fm.ReceiveCom.readOnly = true;   
        fm.SendOutCom.value = "";
        fm.SendOutCom.readOnly = false;       
      }else if(fm.Department.value=="3"){
        fm.DepartmentNo.value = "";
        fm.DepartmentNoName.value = "";
        fm.SendOutCom.value = "";
        fm.SendOutCom.readOnly = false; 
        fm.ReceiveCom.value = "";
        fm.ReceiveCom.readOnly = false;          
      }
    }
  }
  if(cName=='CertifyCode')//选择单证编码后,自动查询发放者和接收者的库存并显示
  {
    var SendOutCom = fm.SendOutCom.value;
    var ReceiveCom = fm.ReceiveCom.value;
    if(SendOutCom=="" || ReceiveCom==""){
      alert("请先录入【发放者】和【接收者】后再选择单证代码！");
      return false;
    }
    for(var i=0;i<CertifyList.mulLineCount;i++){//循环查询mulLine，若发现有未查询库存的将其查询出来显示
      var CertifyCode=CertifyList.getRowColData(i,1);
      var sum1=CertifyList.getRowColData(i,3);
      var sum2=CertifyList.getRowColData(i,3);
      if(CertifyCode!="" && (sum1=="" || sum2==""))
        {
         //alert("第"+(i+1)+"行单证代码="+CertifyCode);         
         //var sqlSum1="select nvl(sum(a.sumcount),0),min(a.startno) from lzcard a where a.certifycode='"+CertifyCode+"' and a.receivecom='"+ SendOutCom +"' and a.stateflag in ('2','3')";
         var sqlSum1 = wrapSql(tResourceName,"querysqldes1",[CertifyCode,SendOutCom]);
         //var sqlSum2="select nvl(sum(a.sumcount),0) from lzcard a where a.certifycode='"+CertifyCode+"' and a.receivecom='"+ ReceiveCom +"' and a.stateflag in ('2','3')";
         var sqlSum2 = wrapSql(tResourceName,"querysqldes2",[CertifyCode,ReceiveCom]);
         var arrResult1 = easyExecSql(sqlSum1);
         var arrResult2 = easyExecSql(sqlSum2);
         //alert("本级库存="+arrResult1[0][0]);
         //alert("下级库存="+arrResult2[0][0]);
         CertifyList.setRowColData(i,3,arrResult1[0][0]);//发放者库存
         CertifyList.setRowColData(i,4,arrResult2[0][0]);//接收者库存
         
         //var sqlHavnumber="select a.havenumber from lmcertifydes a where a.certifycode='"+CertifyCode+"'";
         var sqlHavnumber = wrapSql(tResourceName,"querysqldes4",[CertifyCode]);
         var Havnumber = easyExecSql(sqlHavnumber);
         if(Havnumber=="Y"){
           CertifyList.setRowColData(i,5,arrResult1[0][1]);//如果为有号单证，自动带出最小的起始单号
         }
        }
    }
  }  
}

