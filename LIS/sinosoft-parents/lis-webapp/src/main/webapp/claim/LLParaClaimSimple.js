var showInfo;
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库


// 查询按钮
function InitQueryClick()
{
	if(fm.ComCodeQ.value==null||fm.ComCodeQ.value=='')
	{
	      alert("请先选择管理机构!");
	      return false;
	}
	
//	var strSQL="select * from LLParaClaimSimple where 1=1 "
//		+ " and ComCode like '"+fm.ComCodeQ.value+"%'"
//		+ " order by ComCode ";
	//alert(strSQL);
	sql_id = "LLParaClaimSimpleSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaClaimSimpleSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(fm.ComCodeQ.value);//指定传入的参数
	str_sql = my_sql.getString();
	turnPage.pageLineNum=5;
	turnPage.queryModal(str_sql, LLParaClaimSimpleGrid);        
}


/*提交后台前的页面数据校验*/
function beforeSimpleSubmit()
{
		var AccDate1 = fm.StartDate.value;//启用日期
		var AccDate2 = fm.EndDate.value;//结束日期
		var AccYear1 = AccDate1.substring(0,4);
    var AccMonth1 = AccDate1.substring(5,7);
    var AccDay1 = AccDate1.substring(8,10);
    var AccYear2 = AccDate2.substring(0,4);
    var AccMonth2 = AccDate2.substring(5,7);
    var AccDay2 = AccDate2.substring(8,10);
    
    if (AccDate1 == null || AccDate1 == '')
    {
        alert("启用日期不能为空！");
        return false;
    }
    if (AccDate2 != null && AccDate2 != '')
    {
	   	if (AccYear1 > AccYear2)
	    {
	        alert("启用日期不能晚于结束日期");
	        return false;
	    }
	   	else if (AccYear1 == AccYear2)
	    {
	        if (AccMonth1 > AccMonth2)
	        {
	
	            alert("启用日期不能晚于结束日期");
	            return false;
	        }
	        else if (AccMonth1 == AccMonth2 && AccDay1 > AccDay2)
	        {
	            alert("启用日期不能晚于结束日期");
	            return false;
	        }
	    }
	  }
    var BaseMin = parseFloat(fm.BaseMin.value);//最小金额
    var BaseMax = parseFloat(fm.BaseMax.value);//最大金额
    if (BaseMin > BaseMax)
    {
    		alert("最小金额不能大于最大金额");
    		return false;
  	} 
  	var ComCode=fm.ComCode.value;
  	var ComCodeName=fm.ComCodeName.value;
  	if (ComCode == null || ComCode == '')
  	{
  			alert("机构编码不能为空");
    		return false;
  	}
  	if (ComCodeName == null || ComCodeName == '')
  	{
  			alert("机构名称不能为空");
    		return false;
  	}
    return true;

}


 /*[增加]按钮对应操作*/
function SimpleAddClick()
{

	if (beforeSimpleSubmit())
	{
    mOperate="Simple||INSERT";
    submitSimpleForm();
    SimpleResetClick();//清空数据
  }
}

/*[修改]按钮对应操作*/
function SimpleEditClick()
{
	if (beforeSimpleSubmit)
	{
    fm.ComCode.disabled=false;	
    fm.ComCodeName.disabled=false;
    fm.UpComCode.disabled=false;
    fm.StartDate.disabled=false;
    mOperate="Simple||UPDATE";
    submitSimpleForm();
    SimpleResetClick();//清空数据
  }
}

/*[删除]按钮对应操作*/
function SimpleDeleteClick()
{
	if (beforeSimpleSubmit()){
    if (confirm("您确实想删除该记录吗?"))
    {
        fm.ComCode.disabled=false;	
        fm.ComCodeName.disabled=false;
        fm.UpComCode.disabled=false;
        fm.StartDate.disabled=false;
        mOperate="Simple||DELETE";
        submitSimpleForm();
        SimpleResetClick();//清空数据
        }
    }
    else
    {
        fm.fmtransact.value="";
        return;
    }
}

/*[重置]按钮对应操作*/
function SimpleResetClick()
{
    //清空录入信息
    fm.ComCode.value = '';
    fm.ComCodeName.value = '';
    fm.UpComCode.value = '';
    fm.BaseMin.value = '';
    fm.BaseMax.value = '';
    fm.StartDate.value = '';
    fm.EndDate.value = '';
    fm.Operator.value = '';
    fm.MakeDate.value = '';
    fm.MakeTime.value = '';
    fm.ModifyDate.value = '';
    fm.ModifyTime.value = '';
    fm.UpComCodeName.value='';
    
    fm.ComCode.disabled=false;
		fm.UpComCode.disabled=false;
    fm.editSimpleButton.disabled = true;
    fm.saveSimpleButton.disabled = false; //[增加]按钮   
}

/*简单案例标准MulLine的触发函数*/
function LLParaClaimSimpleGridClick()
{

    //得到MulLine的简单案例标准
    
    fm.ComCode.disabled=true;
    //fm.ComCodeName.disabled=true;
    fm.UpComCode.disabled=true;
    fm.StartDate.disabled=true;
    var tNo = LLParaClaimSimpleGrid.getSelNo();

    //赋值信息
    fm.ComCode.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,1);
    fm.ComCodeName.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,2);
    fm.UpComCode.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,3);
    fm.BaseMin.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,4);
    fm.BaseMax.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,5);
    fm.StartDate.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,6);
    fm.EndDate.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,7);
    fm.Operator.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,8);
    fm.MakeDate.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,9);
    fm.MakeTime.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,10);
    fm.ModifyDate.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,11);
    fm.ModifyTime.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,12);
   	
    fm.editSimpleButton.disabled = false;
    fm.deleteSimpleButton.disabled =false;
    fm.saveSimpleButton.disabled =true;     
}

/*[公共函数]提交到后台*/
function submitSimpleForm()
{
  	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
    fm.hideOperate.value=mOperate;
    fm.action = "./LLParaClaimSimpleSave.jsp";
    document.getElementById("fm").submit(); //提交

}


/*[公共函数]交后操作,服务器数据返回后执行的操作*/
function afterSimpleSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
				InitQueryClick();//刷新列表及界面    
    }

}