//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage2 = new turnPageClass();
var turnPage  = new turnPageClass();
var mySql = new SqlClass();
//提交完成后所作操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        //直接取得数据跳转到立案界面
        var i = SelfLLClaimSimpleAllGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = SelfLLClaimSimpleAllGrid.getRowColData(i,1);
            var tClmState = SelfLLClaimSimpleAllGrid.getRowColData(i,2);
            var tMissionID = SelfLLClaimSimpleAllGrid.getRowColData(i,7);
            var tSubMissionID = SelfLLClaimSimpleAllGrid.getRowColData(i,8);
            location.href="LLClaimSimpleInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
        }
    }
    
    tSaveFlag ="0";
    initForm();
}

function returnparent()
{
    var backstr=document.all("ContNo").value;
    mSwitch.deleteVar("ContNo");
    mSwitch.addVar("ContNo", "", backstr);
    mSwitch.updateVar("ContNo", "", backstr);
    location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType;
}

function showNotePad()
{
    alert("开发中")
    return;
	  var selno = SelfPolGrid.getSelNo()-1;
	  if (selno <0)
	  {
	        alert("请选择一条任务");
	        return;
	  }
	
	  var MissionID = SelfPolGrid.getRowColData(selno, 6);
	  var SubMissionID = SelfPolGrid.getRowColData(selno, 7);
	  var ActivityID = SelfPolGrid.getRowColData(selno, 8);
	  var PrtNo = SelfPolGrid.getRowColData(selno, 2);
	  if(PrtNo == null || PrtNo == "")
	  {
	    alert("印刷号为空！");
	    return;
	  }
	  var NoType = "1";
	
	  var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	  var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");

}


// 初始化表格2
function querySelfGrid()
{
	//登陆机构操作控制,登陆机构代码小于4位的不允许进行操作
  if( fm.ManageCom.value.length < 4){
    fm.riskbutton.disabled=true;
    
  }else{
    fm.riskbutton.disabled=false;
   
  }
	  var tManageCom = fm.ManageCom.value;
    var tCustomerName = fm.CustomerName.value;
    var tOperator = fm.Operator.value;
    
	 /* var strSQL = "";
    strSQL = "select RgtNo,'处理中',AppntNo,GrpName,"
           +" Peoples2,RgtDate,Operator,MngCom "
           +" from LLRegister a where appntno is not null  and rgtstate = '03' and"
           +" ClmState = '25'"
           + getWherePart( 'RgtNo' ,'RptNo')
           + getWherePart( 'AppntNo' ,'CustomerNo')*/
           
		mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimSimpleAllInputSql");
		mySql.setSqlId("LLGrpClaimSimpleAllSql1");
		mySql.addSubPara(fm.RptNo.value ); 
		mySql.addSubPara(fm.CustomerNo.value );
		mySql.addSubPara(tManageCom);
           if (tCustomerName!= '' && tCustomerName != null)
           {
               //strSQL = strSQL + " and GrpName like '%%" + tCustomerName + "%%'"
               mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpClaimSimpleAllInputSql");
				mySql.setSqlId("LLGrpClaimSimpleAllSql2");
				mySql.addSubPara(fm.RptNo.value ); 
				mySql.addSubPara(fm.CustomerNo.value );
				mySql.addSubPara(tManageCom);
				mySql.addSubPara(tCustomerName);
           }
    //strSQL = strSQL + " and MngCom like '" + tManageCom+"%%'";
    //strSQL = strSQL + " order by RgtNo,RgtDate";
    turnPage2.queryModal(LLGrpClaimSimpleAll,SelfLLClaimSimpleAllGrid);
}


//SelfLLClaimSimpleGrid点击事件
function SelfLLClaimSimpleGridClick()
{
    var i = SelfLLClaimSimpleAllGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tFlag = "FROMSIMALL";
        var tRptNo = SelfLLClaimSimpleAllGrid.getRowColData(i,1);
        location.href="LLGrpSimpleAllInput.jsp?RptNo="+tRptNo+"&Flag="+tFlag;
    }

}

//提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.submit(); //提交
    tSaveFlag ="0";
}

function getin()
{
   var tFlag  = "TOSIMALL";
   var tRptNo = fm.RptNo.value;
//   var strUrl = "../claim/GrpCustomerDiskInput.jsp?grpcontno="+tRptNo+"&Flag="+tFlag;
   var strUrl = "../claim/GrpCustomerDiskMain.jsp?Flag="+tFlag;
   //showInfo=window.open(strUrl,"被保人清单导入","");
   showInfo=window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}