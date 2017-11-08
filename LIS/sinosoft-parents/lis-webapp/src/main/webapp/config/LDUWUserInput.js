//               该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

function trimcode(){
  fm.UserCode.value = trim(document.all('UserCode').value);
}
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}
function checkuseronly(comname)
{
	//add by yaory
	

} 
//提交，保存按钮对应操作
function submitForm()
{
 if(document.all("UserCode").value == ""||document.all("UserCode").value==null )
 { 
 	alert("请输入核保师编码！");
 	return ;
 }
 
 if(document.all("UWPopedom").value == ""||document.all("UWPopedom").value==null )
 { 
 	alert("请选择核保级别！");
 	return ;
 }
 
 if(document.all('UWPopedom').value == document.all('MaxGrade').value)//当该核保师的核保权限为E-当前为最高是 上级核保级别置为'' modify by liuqh 2008-12-12
   {
      alert("此核保师的核保权限为最高:"+document.all('UWPopedom').value+", 其上级核保级别将为空！");
      document.all('UpUwPopedom').value ="";
      document.all('UpUserCodeName').value ="";
   }
 if((document.all("UpUwPopedom").value == ""||document.all("UpUwPopedom").value==null)&&document.all('UWPopedom').value!=document.all('MaxGrade').value )
 { 
 	alert("请输入上级核保级别！");
 	return ;
 }
 if(checkUWGrade()==false) return false;
 
 //if(document.all("ClaimPopedom").value == ""||document.all("ClaimPopedom").value==null )
 //{ 
 //	alert("请选择理赔级别！");
 //	return ;
 //}
// var tt1=document.all("UWPopedom").value;
// var tt2=document.all("UpUwPopedom").value;
// if(tt1>=tt2)
// {
// 	alert("上级权限必须大于自己的权限！");
// 	return ;
//}
 
  var i = 0;
  //if( verifyInput1() == false ) return false;
  
  fm.fmtransact.value="INSERT||MAIN";
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
  cleartDate();//清空数据
 
}
//提交后操作,服务器数据返回后执行的操作
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

    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    queryClick();
    //执行下一步操作
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
}
//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在LDUWUser.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 
//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
   //if(!UWResultGrid.checkValue("UWResultGrid"))
   //{
   //	return false;
   //}
  //添加操作	
  //if(UWResultGrid. mulLineCount < 1 )
  //{
  //	alert("请设置核保结论!");
  //	return false;
  //}
  //if(!UWMaxAmountGrid.checkValue("UWMaxAmountGrid"))
  // {
  // 	return false;
  // }
  //if(UWMaxAmountGrid. mulLineCount < 1 )  
  //{
  //	alert("请设置核保保额上限!");
  //	return false;
  //}
  return true;

}

/*********************************************************************
 *  代码选择后触发时间
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{

		if( cCodeName == "UWType")
        	{
        		
	          try
	          {
	            if (Field.value!='1')
	            {   
	              
	            	  divLDUWUser3.style.display = "";
	            	   
	             	  document.all('AddPoint').value="0";
	                document.all('AddPoint').readOnly=true;
	               
	            }
	            else
	            {
	            	divLDUWUser3.style.display = "none";
	            	   
	             	  document.all('AddPoint').value="";
	                document.all('AddPoint').readOnly=false;
	            }
	          }
	          catch(ex) {} 
	        } 	    	          
}           
//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
		parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function getMaxGrade(){
//  var tMaxGradeSQL = "select max(code) from ldcode where codetype = 'uwgrppopedom'";
  
	var sqlid0="LDUWUserInputSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("config.LDUWUserInputSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	var tMaxGradeSQL=mySql0.getString();
  
  var tMaxGrade = easyExecSql(tMaxGradeSQL);
  fm.MaxGrade.value = tMaxGrade;
}
//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  //mOperate="INSERT||MAIN";
  //beforeSubmit();
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
  //beforeSubmit();
  fm.fmtransact.value = "INSERT||MAIN" ;
}           
//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
//  var tSql="select count(*) from lduwuser where uwtype='2' and usercode='"+fm.UserCode.value+"'";
  
	var sqlid1="LDUWUserInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("config.LDUWUserInputSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.UserCode.value);//指定传入的参数
	var tSql=mySql1.getString();
  
  var tCount= easyExecSql(tSql);
  if(tCount=="0")
  {
     alert("编码为[ "+fm.UserCode.value+" ]的核保师不存在,请添加！");
     return false;
  }
  if((fm.UserCode.value == null)||(fm.UserCode.value == "")||fm.UWPopedom.value==""||fm.UWPopedom.value==null
      ||fm.UWPopedom1.value == null||fm.UWPopedom1.value=="")
  {
  	alert("请先查询记录再作修改！");  
  	return;	
  }
  //当该核保师的核保权限为最高时 上级核保级别置为'' modify by liuqh 2008-12-12
  if(document.all('UWPopedom').value == document.all('MaxGrade').value)
   {
      alert("此核保师的核保权限为最高:"+document.all('UWPopedom').value+", 其上级核保级别将为空！");
      document.all('UpUwPopedom').value ="";
      document.all('UpUserCodeName').value ="";
   }
   if((document.all("UpUwPopedom").value == ""||document.all("UpUwPopedom").value==null)&&document.all('UWPopedom').value!=document.all('MaxGrade').value )
   { 
    	alert("请输入上级核保级别！");
    	return ;
   }
   if(checkUWGrade()==false) return false;
  //下面增加相应的代码
  if (confirm("您确实想修改该记录吗?"))
  {
  	//if(!beforeSubmit()) return false;
  var i = 0;
  var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  
  //showSubmitFrame(mDebug);
  fm.fmtransact.value = "UPDATE||MAIN";
  document.getElementById("fm").submit(); //提交
  cleartDate();//清空数据

  }
  else
  {
    //mOperate="";
    alert("您取消了修改操作！");
  }
}           
//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  //mOperate="QUERY||MAIN";
  //showInfo=window.open("./LDUWUserQuery.html");
  if(document.all("UserCode").value == ""||document.all("UserCode").value==null )
 { 
 	alert("请输入核保师编码！");
 	return false;
 }
  
//  var strSql = " select uwpopedom,(select max(codename) from ldcode where codetype='uwgrppopedom' "
//              +" and trim(code)=a.uwpopedom),upuwpopedom,(select max(codename) from ldcode where "
//              +" codetype='uwgrppopedom' and trim(code)=a.upuwpopedom),claimpopedom from lduwuser a "
//              +" where usercode='"+fm.UserCode.value+"' and UWType='2' ";
  
	var sqlid2="LDUWUserInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("config.LDUWUserInputSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(fm.UserCode.value);//指定传入的参数
	var strSql=mySql2.getString();
  
	var arrResult = easyExecSql(strSql);
  if(arrResult != null)
  {
	//alert(arrResult);
	fm.UWPopedom.value=arrResult[0][0];
	fm.UWPopedomName.value=arrResult[0][1];
	fm.UpUwPopedom.value=arrResult[0][2];
	fm.UpUserCodeName.value=arrResult[0][3];
	fm.ClaimPopedom.value=arrResult[0][4];
	fm.UWPopedom1.value=arrResult[0][0];
	fm.UserCode1.value=fm.UserCode.value;
	fm.UWType1.value="2";
	}else{
	  alert("编码为[ "+fm.UserCode.value+" ]的核保师不存在!");
	  //fm.UserCode.value = "";
	  fm.UWPopedom.value = "";
	  fm.UWPopedomName.value = "";
	  fm.UpUwPopedom.value = "";
	  fm.UpUserCodeName.value = "";
	  return false;
	  }
   return true;
}           
//Click事件，当点击“删除”图片时触发该函数

function deleteClick()
{
  //下面增加相应的删除代码
  if(queryClick()==false) return false;
  if (confirm("您确实想删除该记录吗?"))
  {
  var i = 0;
  var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  //showSubmitFrame(mDebug);
  fm.fmtransact.value = "DELETE||MAIN";
  document.getElementById("fm").submit(); //提交
  initForm();
  cleartDate();//清空数据
  }
  else
  {
    alert("您取消了删除操作！");
  }
}  
//清空数据
function cleartDate(){
	document.all("UserCode").value="";
	document.all("UWPopedom").value="";
	document.all("UpUwPopedom").value="";
	document.all("UWPopedomName").value="";
	document.all("UpUserCodeName").value="";
}
function checkUWGrade(){
   var tUWPopedom = fm.UWPopedom.value;
   var tUpUWPopedom = fm.UpUwPopedom.value;
   if(tUWPopedom>tUpUWPopedom&&tUWPopedom!=document.all('MaxGrade').value)
   {
     alert("您选择的[上级核保级别]低于[核保权限],请重新选择！");
     return false;
   }
   return true;
}
//Click事件，当点击“设置保额上限”时触发该函数      
function setclick()
{  
  
    getMaxAmntInfo();
   
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
/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult )
{
	//var arrResult = new Array();
	//alert(arrQueryResult[0][0]);
	if( arrQueryResult != null )
	{   
		//alert(arrQueryResult[0][1]);
		var type;
                if(arrQueryResult[0][1]=='个险')//edit by yaory 要区别个团险 
              	{
              		type="1";
              		divLDUWUser3.style.display = "none";
              	}
              	else
              	{
              		
              		type="2";
	            	divLDUWUser3.style.display = "";
              	}//write by yaory   
              
              	//alert(type);
//		var strSQL="select UserCode,UWType,UpUserCode,UpUWPopedom,UWPopedom,Remark,Operator,ManageCom,MakeDate,MakeTime,ModifyDate,ModifyTime, AddPoint,ClaimPopedom,EdorPopedom,riskrate,specjob,lowestamnt,overage from LDUWUser where uwtype='"+type+"' and UserCode='"+arrQueryResult[0][0]+"'";
	 	//alert(strSQL);
		
		var sqlid3="LDUWUserInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("config.LDUWUserInputSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(type);//指定传入的参数
		mySql3.addSubPara(arrQueryResult[0][0]);//指定传入的参数
		var strSQL=mySql3.getString();
		
	 	arrResult = easyExecSql(strSQL);
	 	//alert(arrResult[0][11]); 
	 	document.all('UserCode').value= arrResult[0][0];
	 	document.all('UserCode1').value= arrResult[0][0];
                //alert(3)
                document.all('UWType').value= arrResult[0][1];
                document.all('UWType1').value= arrResult[0][1];
               // document.all('UpUserCode').value= arrResult[0][2];
                // alert(3)
               document.all('UpUWPopedom').value= arrResult[0][3];
               document.all('UWPopedom1').value= arrResult[0][4];
                //alert(arrResult[0][4]);
               document.all('UWPopedom').value= arrResult[0][4];
                 //alert(3)
               document.all('Remark').value= arrResult[0][5];
                document.all('Operator').value= arrResult[0][6];
                document.all('ManageCom').value= arrResult[0][7];
                //alert(3)
                document.all('MakeDate').value= arrResult[0][8];
                document.all('MakeTime').value= arrResult[0][9];
                //alert(4)
                document.all('ModifyDate').value= arrResult[0][10];
                document.all('ModifyTime').value= arrResult[0][11];
                document.all('AddPoint').value= arrResult[0][12];
                document.all('ClaimPopedom').value= arrResult[0][13];
                document.all('edpopedom').value= arrResult[0][14];
                document.all('Rate').value= arrResult[0][15];
                document.all('SpecJob').value= arrResult[0][16];
                document.all('LowestAmnt').value= arrResult[0][17];
                document.all('OverAge').value= arrResult[0][18];
                //alert(arrResult[0][14])
                //alert(arrResult[0][1]);
                if(arrResult[0][1]=="1")//add by yaory
                {tt="contuwstate";}
                if(arrResult[0][1]=="2")
                {tt="tdhbconlusion";}
                initUWResultGrid();//add by yaory end add重新初始化是为了改变条件
//                var strSQL2="select UWState,UWStateName from LDUWGrade where uwtype='"+type+"' and UserCode='"+arrQueryResult[0][0]+"'";
                //alert(strSQL2);
                
        		var sqlid4="LDUWUserInputSql4";
        		var mySql4=new SqlClass();
        		mySql4.setResourceName("config.LDUWUserInputSql"); //指定使用的properties文件名
        		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
        		mySql4.addSubPara(type);//指定传入的参数
        		mySql4.addSubPara(arrQueryResult[0][0]);//指定传入的参数
        		var strSQL2=mySql4.getString();
                
                turnPage.queryModal(strSQL2, UWResultGrid);
                //add by yaory
                //alert(arrQueryResult[0][1]);
                
              	//alert(type);
//                strSQL2="select a.UWKind,b.codename,a.MaxAmnt from LDUWAmntGrade a,ldcode b where a.UWType='"+type+"' and a.UserCode='"+arrQueryResult[0][0]+"' and trim(a.Uwkind)=trim(b.code) and b.codetype='uwkind'"  ;
                
        		var sqlid5="LDUWUserInputSql5";
        		var mySql5=new SqlClass();
        		mySql5.setResourceName("config.LDUWUserInputSql"); //指定使用的properties文件名
        		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
        		mySql5.addSubPara(type);//指定传入的参数
        		mySql5.addSubPara(arrQueryResult[0][0]);//指定传入的参数
        		strSQL2=mySql5.getString();
                
                turnPage.queryModal(strSQL2, UWMaxAmountGrid);
                fm.UWType.style.readonly=true;
                showCodeName();                 
      }
} 

function hh()
{
	
	//alert(tt);
	var mm=fm.UWType.value;
	if(mm=="1")
	{tt="contuwstate";}
	if(mm=="2")
	{tt="tdhbconlusion";}
	//alert(tt);
	initUWResultGrid();
	//alert(UWType);
}
//查询核保保额上限信息  
function getMaxAmntInfo()
{
	
	var UWType=document.all("UWType").value;
	
	var UWPopedom=document.all("UWPopedom").value;
	if((UWType==null||UWType=="")||(UWPopedom==null||UWPopedom==""))
	{
		alert("请输入核保师类别和核保权限");
		return ;
	}
	
	if(UWType!=null&&UWPopedom!=null)
	{ 
		
  //      var strSQL ="select Uwkind,(select codename from ldcode a where trim(a.codetype)='uwkind' and trim(a.code)=trim(Uwkind)),MaxAmnt from LDUWGradePerson where UWType='"+UWType+"' and UWPopedom='"+UWPopedom+"'";
//       var strSQL ="select uwkind,(select codename from ldcode a where trim(a.codetype)='uwkind' and trim(a.code)=trim(Uwkind)),MaxAmnt from LDUWGradePerson where UWType='"+UWType+"' and UWPopedom='"+UWPopedom+"'";
       
		var sqlid6="LDUWUserInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("config.LDUWUserInputSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(UWType);//指定传入的参数
		mySql6.addSubPara(UWPopedom);//指定传入的参数
		var strSQL=mySql6.getString();
	
	} 
       // alert("uwtype:"+UWType)
       // alert("UWPopedom:"+UWPopedom)
       //fm.Remark.value = strSQL;
       
      	turnPage.queryModal(strSQL, UWMaxAmountGrid);
}

function showCodeName()
{
	showOneCodeName('UWType','UWType','UWTypeName');
	showOneCodeName('uwgrppopedom','UWPopedom','UWPopedomName');
	showOneCodeName('edpopedom','edpopedom','edpopedomName');
	showOneCodeName('clPopedom','ClaimPopedom','claimpopedomName');
	showOneCodeName('uwgrppopedom','UpUwPopedom','UpUserCodeName');
	showOneCodeName('SpecJob','SpecJob','SpecJobName');
	showOneCodeName('LowestAmnt','LowestAmnt','LowestAmntName');
	showOneCodeName('OverAge','OverAge','OverAgeName');
}

