//该文件中包含客户端需要处理的函数和事件

var showInfo;
var tResourceName="certify.CertifySendOutInputSql";
//发放单证操作
function submitForm()
{ //避免双击
  fm.btnOp.disabled = true;
  //删除空白行
  CertifyList.delBlankLine("CertifyList");
  //校验列表至少有一行
  var nmulLineCount = CertifyList.mulLineCount;
  if (nmulLineCount == null || nmulLineCount <= 0)
  {
    alert("请至少输入一个要发放的单证！ ");
    fm.btnOp.disabled = false;
    return false;
  }
  //校验页面信息
  if( verifyInput() == false || !CertifyList.checkValue("CertifyList"))
  {
    fm.btnOp.disabled = false;
    return false;
  }
  
  //add by duanyh 2009-8-18 校验需要单证拆分时，拆分数量最多为30000
  if(fm.ReceiveCom.value.charAt(0)=='B'||fm.ReceiveCom.value.charAt(0)=='D'||(fm.ReceiveCom.value.charAt(0)=='A'&&fm.ReceiveCom.value.length==9))
  {
  	for(var i=1; i<=CertifyList.mulLineCount; i++)
	{
		varStartNo=CertifyList.getRowColData(i-1,5);
		varEndNo=CertifyList.getRowColData(i-1,6);
		//var strSql = "select '"+varEndNo+"'-'"+varStartNo+"'+1 from dual";
		var strSql = wrapSql(tResourceName,"querysqldes5",[varEndNo,varStartNo]);
          var arrResult = easyExecSql(strSql);
          //alert(arrResult);
		if(arrResult>30000)
		{
			alert("第"+i+"行记录需要拆分的单证数超过30000条，请减少一次性发放的单证数量！");
			fm.btnOp.disabled = false;
               return false;
		}
	}
  }
  

  //重要操作，弹出确认信息
  if (confirm("您确认发放操作吗?")){
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
		document.getElementById("fm").submit(); //提交
    } catch(ex) {
  	  showInfo.close();
  	  alert(ex);
    }
  }else{
    fm.btnOp.disabled = false;
    alert("您取消了发放操作！");
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
	content="保存成功！";
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
}

//选择代码后触发的动作
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
      alert("选择【部门来源】有误");
      fm.DepartmentNo.value="";
    }
  }
  if(cName=='Department')//选择部门来源后
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
      if(CertifyCode!="")
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
           //CertifyList.setRowColData(i,6,'');
        }
    }
  }  
}

//获取非个人代理人所在机构代码、名称
function getAgentCom()
{
	var receivecom = document.all('ReceiveCom').value;
	if(receivecom!=null && receivecom != "" && receivecom.substring(0,1) == 'D')	 
	{
		/*var strSQL="select a.agentcom,(select name from lacom where agentcom=a.agentcom) "
		+" from lacomtoagent a where a.relatype='1' "//银代 3
		+" and a.agentcode='" + receivecom.substring(1, 12) + "' "
		+" union "
		+" select b.agentcom,b.name "
		+" from lacom b where b.comtoagentflag='1' "//联办 6
		+" and b.agentcode='" + receivecom.substring(1, 12) + "' "
		+" union "
		+" select c.agentcom,(select name from lacom where agentcom=c.agentcom)  "
		+" from lacomtoagentnew c where c.relatype='2' "//中介 7
		+" and c.agentcode='" + receivecom.substring(1, 12) + "'";*/
		var strSQL = wrapSql(tResourceName,"querysqldes3",[receivecom.substring(1, 12),receivecom.substring(1, 12),receivecom.substring(1, 12)]);
		
		if(easyQueryVer3(strSQL)!=false){
			fm.agentCom.value="";
			fm.agentComName.value="";
			fm.agentCom.CodeData = easyQueryVer3(strSQL); 	
		}else{
			fm.agentCom.value="";
			fm.agentComName.value="";
			fm.agentCom.CodeData = "";
		}
	}
}

