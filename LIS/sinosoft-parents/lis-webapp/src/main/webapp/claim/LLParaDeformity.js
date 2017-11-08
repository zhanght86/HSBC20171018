var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
//2008-10-17 zhangzheng 比例给付级别响应函数:当比例类型选择为LF:伤残,LZ:骨折时才需要选择双击本下拉框选择给付级别，其他的则不需要选择
function respondDefoGrade()
{
	if (fm.DefoType.value == null || trim(fm.DefoType.value) ==''){
        alert('请先选择比例给付类型!');
        return false;
	}
	//LZ;骨折,LF:伤残两种类型
	else if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF')
	{
	    return showCodeList('lldefograde',[fm.DefoGrade,fm.DefoGradeName],[0,1],null,fm.DefoType.value,'DefoType','1','300');
	}
	else
	{
		alert("比例给付类型:"+fm.DefoType.value+"-"+fm.DefoTypeName.value+"不需选择比例给付级别,请直接选择比例给付代码!");
		return false;
	}
}

//2008-10-17 zhangzheng 比例给付级别响应函数:当比例类型选择为LF:伤残,LZ:骨折时才需要选择双击本下拉框选择给付级别，其他的则直接选择具体的比例给付代码
function respondDefoCode()
{
	if (fm.DefoType.value == null || trim(fm.DefoType.value) ==''){
		
        alert('请先选择比例给付类型!');
        return false;
	}
	//LZ;骨折,LF:伤残两种类型
	else if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF')
	{
		if(fm.DefoGrade.value == null || trim(fm.DefoGrade.value) ==''){
			
			alert('请先选择比例给付级别!');
	        return false;
		}
		else
		{
			//同时关联比例给付类型和比例给付级别查询
			return showCodeList('lldefocode',[fm.DefoCode,fm.DefoCodeName],[0,1],null,fm.DefoType.value,fm.DefoGrade.value,'1','300');
		}
	}
	//其他类型
	else
	{
		//只关联比例给付类型查询,比例给付级别默认为1
		return showCodeList('lldefocode',[fm.DefoCode,fm.DefoCodeName],[0,1],null,fm.DefoType.value,'1','1','300');
	}
}


//2008-10-17 zhangzheng 双击下拉框后的响应函数
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    
	//比例给付类型
    if(cCodeName=="lldefotype"){
    	if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF'){
    		//当比例给付类型不是LZ;骨折,LF:伤残两种类型时，比例给付级别不需要录入，需要清空
    		return true;
    	}
    	else{
    		//其他类型则需要将比例给付级别清空
    		fm.DefoGrade.value='';
    		fm.DefoGradeName.value='';
    	}
        return true;
    }
}

/*[公共函数]提交到后台*/
function submitDeformityForm()
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
    fm.action = "./LLParaDeformitySave.jsp";
    document.getElementById("fm").submit(); //提交

}


/*[公共函数]交后操作,服务器数据返回后执行的操作*/
function afterDeformitySubmit( FlagStr, content )
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
        mOperate = '';
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

        if ( mOperate=="Deformity||INSERT"){
            DeformityAddAfterClick();
        }
        if ( mOperate=="Deformity||DELETE"){
            DeformityDeleteAfterClick();
        }
        if ( mOperate=="Deformity||UPDATE"){
            DeformityEditAfterClick();
        }
    }

}


/*[增加]按钮对应操作*/
function DeformityAddClick()
{
	if (beforeDeformitySubmit())
	{
    mOperate="Deformity||INSERT";
    submitDeformityForm();
  }
}
/*[修改]按钮对应操作*/
function DeformityEditClick()
{
	if (beforeDeformitySubmit())
	{
    mOperate="Deformity||UPDATE";
    submitDeformityForm();
	}
}
/*[删除]按钮对应操作*/
function DeformityDeleteClick()
{
    if (confirm("您确实想删除该记录吗?")){
        mOperate="Deformity||DELETE";
        submitDeformityForm();
    }else{
        mOperate="";
    }
}
/*[重置]按钮对应操作*/
function DeformityResetClick()
{
    //清空录入信息
    fm.DefoType.value = '';
    fm.DefoTypeName.value = '';
    fm.DefoGrade.value = '';
    fm.DefoGradeName.value = '';
    fm.DefoCode.value = '';
    fm.DefoCodeName.value = '';
    fm.DefoRate.value = '';
    
    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = true;
}


/*[增加]按钮后台成功执行后对应的操作*/
function DeformityAddAfterClick()
{
    /*//需要返回成功标志后，才进行以下操作
    var tNo = ParaDeformityGrid.mulLineCount;
    if (tNo<=0)
    {
        tNo = 0;
    }

    ParaDeformityGrid.addOne();
    ParaDeformityGrid.setRowColData(tNo, 1,fm.DefoType.value);           	//伤残类型
    ParaDeformityGrid.setRowColData(tNo, 2,fm.DefoGrade.value);						//伤残级别
    ParaDeformityGrid.setRowColData(tNo, 3,fm.DefoCode.value);						//伤残代码
    ParaDeformityGrid.setRowColData(tNo, 4,fm.DefoName.value);						//伤残级别名称
    ParaDeformityGrid.setRowColData(tNo, 5,fm.DefoRate.value);						//残疾给付比例*/
    
    var strSql="";
//    strSql = " select * from LLParaDeformity order by DefoType,DefoCode" ;
    sql_id = "LLParaDeformitySql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaDeformitySql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	str_sql = my_sql.getString();
    
  
    //显示所有数据
    turnPage.pageLineNum=10;    //每页10条
    turnPage.queryModal(str_sql,ParaDeformityGrid);
  

    //清空界面录入信息
    fm.DefoType.value = '';
    fm.DefoTypeName.value = '';
    fm.DefoGrade.value = '';
    fm.DefoGradeName.value = '';
    fm.DefoCode.value = '';
    fm.DefoCodeName.value = '';
    fm.DefoRate.value = '';

    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = true;
}

/*[删除]按钮后台成功执行后对应的操作*/
function DeformityDeleteAfterClick()
{
        //需要返回成功标志后，才进行以下操作
        //ParaDeformityGrid.delRadioTrueLine();
        var strSql="";
//    		strSql = "select * from LLParaDeformity order by DefoType,DefoCode" ;
    		sql_id = "LLParaDeformitySql0";
    		my_sql = new SqlClass();
    		my_sql.setResourceName("claim.LLParaDeformitySql"); //指定使用的properties文件名
    		my_sql.setSqlId(sql_id);//指定使用的Sql的id
    		str_sql = my_sql.getString();
    		//显示所有数据
    		turnPage.pageLineNum=10;    //每页10条
    		turnPage.queryModal(str_sql,ParaDeformityGrid);
    
        //清空录入信息
		    fm.DefoType.value = '';
		    fm.DefoTypeName.value = '';
		    fm.DefoGrade.value = '';
		    fm.DefoGradeName.value = '';
		    fm.DefoCode.value = '';
		    fm.DefoCodeName.value = '';
		    fm.DefoRate.value = '';
}

/*[修改]按钮后台成功执行后对应的操作*/
function DeformityEditAfterClick(){
    var strSql="";
//    strSql = " select * from LLParaDeformity order by DefoType,DefoCode" ;
    sql_id = "LLParaDeformitySql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaDeformitySql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	str_sql = my_sql.getString();
    //显示所有数据
    turnPage.pageLineNum=10;    //每页10条
    turnPage.queryModal(str_sql,ParaDeformityGrid);
  
    //清空界面录入信息
    fm.DefoType.value = '';
    fm.DefoTypeName.value = '';
    fm.DefoGrade.value = '';
    fm.DefoGradeName.value = '';
    fm.DefoCode.value = '';
    fm.DefoCodeName.value = '';
    fm.DefoRate.value = '';
    
}


/*理赔权限信息MulLine]的触发函数*/
function getParaDeformityGrid(spanID,parm)
{

    //得到MulLine的理赔权限信息
    var tNo = ParaDeformityGrid.getSelNo();

    //赋值信息
    fm.DefoType.value = ParaDeformityGrid.getRowColData(tNo - 1,1);
    fm.DefoGrade.value = ParaDeformityGrid.getRowColData(tNo - 1,2);
    fm.DefoGradeName.value = ParaDeformityGrid.getRowColData(tNo - 1,3);
    fm.DefoCode.value = ParaDeformityGrid.getRowColData(tNo - 1,4);
    fm.DefoCodeName.value = ParaDeformityGrid.getRowColData(tNo - 1,5);
    fm.DefoRate.value = ParaDeformityGrid.getRowColData(tNo - 1,6);
  
    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = false;

}

//理赔权限信息查询
function queryParaDeformity()
{
  	var strSql="";
//    strSql = " select * from LLParaDeformity order by DefoType,DefoCode" ;
    sql_id = "LLParaDeformitySql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaDeformitySql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	str_sql = my_sql.getString();
    //显示所有数据
    turnPage.pageLineNum=10;    //每页10条
    turnPage.queryModal(str_sql,ParaDeformityGrid);
}

/*提交后台前的页面数据校验*/
function beforeDeformitySubmit()
{
		var strDefoType = fm.DefoType.value;//伤残类型
		var strDefoCode = fm.DefoCode.value;//伤残代码
	
    
    if (strDefoType == null || strDefoType == '')
    {
        alert("伤残类型不能为空！");
        return false;
    }
    if (strDefoCode == null || strDefoCode == '')
    {
        alert("伤残代码不能为空！");
        return false;
    }
    return true;
}




