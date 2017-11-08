var showInfo;
var mDebug="1";
 var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//根据选择的外包商编码查询数据库中是否已有定义的规则，如果有显示在界面上，如果没有，则提示对该外包商没有已定义的复核抽查规则
function queryClick() 
{
//	 var strsql="select bpoid,ManageCom,rate,maxmum from BPOCheckCalMode where 1=1 "
//	       + " and bussnotype='OF' "
//	       + getWherePart( 'BPOID','BussNo' )
//	       + getWherePart( 'rate','checkRate' )
//	       + getWherePart( 'maxmum','checkMax' )
//	       + getWherePart( 'ManageCom','ManageCom' ) ;
	 //prompt("",strsql);
	    var  BussNo = window.document.getElementsByName(trim("BussNo"))[0].value;
	    var  checkRate = window.document.getElementsByName(trim("checkRate"))[0].value;
	    var  checkMax = window.document.getElementsByName(trim("checkMax"))[0].value;
	    var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	    var  sqlid1="ProposalSpotCheckInputSql0";
  	    var  mySql1=new SqlClass();
  	    mySql1.setResourceName("finfee.ProposalSpotCheckInputSql"); //指定使用的properties文件名
  	    mySql1.setSqlId(sqlid1);//指定使用的Sql的id
  	    mySql1.addSubPara(BussNo);//指定传入的参数
  	    mySql1.addSubPara(checkRate);//指定传入的参数
  	    mySql1.addSubPara(checkMax);//指定传入的参数
  	    mySql1.addSubPara(ManageCom);//指定传入的参数
  	    var strsql=mySql1.getString();
   	 
   	 turnPage.queryModal(strsql, CheckGrid);

}

//提交
function save( OperateType)
{
  document.all("OperateType").value = OperateType;
  
  //var arrReturn = new Array();
	//var tSel = CheckGrid.getSelNo();
	
	
	
	document.all("BussNoa").value = document.all("BussNo").value;
	document.all("ManageComa").value = document.all("ManageCom").value;
	document.all("checkRatea").value = document.all("checkRate").value;	
	document.all("checkMaxa").value = document.all("checkMax").value;	
		
  if((document.all('BussNoa').value=="")||(document.all('BussNoa').value==null))
  {
    alert("请选择外包公司！");
    return false;
  }	
  		
  if(OperateType==1 &&(vertifyInput() == false))
  {
	 return false;
  }
    
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //fm.action = './CertifyFeeSave.jsp';
  document.getElementById("fm").submit(); //
}


//返回
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
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    initForm();
  }
}


//对数据的数据进行格式与录入要求的校验
function vertifyInput()
{
  //alert("11111");
  
  if((document.all('checkRatea').value=="")||(document.all('checkRatea').value==null))
  {
    	alert("请录入抽检比例！");
    	return false;
  }
  else
  {
        //alert("222 "+isInteger(document.all('checkRate').value));
  		if(isInteger(document.all('checkRatea').value)==false||isOneToHundred(document.all('checkRatea').value)==0)
  		{
  		      //alert(isOneToHundred(document.all('checkRate').value));
  		      //alert(isInteger(document.all('checkRate').value));
  			  alert("抽检比例不是0-100之间的整数，请重新录入！");
  			  return false;
  		}
  }
  
  if((document.all('checkMaxa').value=="")||(document.all('checkMaxa').value==null))
  {
    	alert("请录入抽检上限！");
    	return false;
  }
  else
  {
  		if(isInteger(document.all('checkMaxa').value)==false)
  		{
  		      //alert(isOneToHundred(document.all('checkRate').value));
  		      //alert(isInteger(document.all('checkRate').value));
  			  alert("抽检上限不是整数，请重新录入！");
  			  return false;
  		}
  }
  

  
  return true;
}


//校验输入的整数是否在0-100之间
function isOneToHundred(strvalue)
{
      //alert("3= "+parseInt(strvalue));
	  if(parseInt(strvalue)>100||parseInt(strvalue)<0)
	  {
  	      return 0;
	  }
  	
  	return 1;
}


//根据选择的外包商编码查询数据库中是否已有定义的规则，如果有显示在界面上，如果没有，则提示对该外包商没有已定义的复核抽查规则
function afterCodeSelect( cCodeName, Field ) 
{
	if(cCodeName=='queryBPOID')
	{
	// var strsql="select riskcode,rate,maxmum,remark,managecom from BPOCheckCalMode where BPOID='"+document.all('BussNo').value+"'";
	    var  sqlid2="ProposalSpotCheckInputSql1";
	    var  mySql2=new SqlClass();
	    mySql2.setResourceName("finfee.ProposalSpotCheckInputSql"); //指定使用的properties文件名
	    mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	    mySql2.addSubPara(document.all('BussNo').value);//指定传入的参数
	    var strsql=mySql2.getString();
	 //prompt("",strsql);
	 var strResult= decodeEasyQueryResult(easyQueryVer3(strsql, 1, 1, 1));  
   
     //判断是否查询成功
     if (!strResult) 
     {  
   	  	alert("外包商"+document.all('BussNo').value+"在系统中没有已定义的复核抽查规则");
      	return false;
     }
     
   	 
   	 //给界面的元素赋值  
   	 if(!((strResult[0][0]==null)||(strResult[0][0]=="")))
   	 {
   	    document.all('RiskCode').value=strResult[0][0];
   	 }
   	 
   	 if(!((strResult[0][1]==null)||(strResult[0][1]=="")))
   	 {
   	    document.all('checkRate').value=strResult[0][1]*100;
   	 }
   	 
   	 if(!((strResult[0][2]==null)||(strResult[0][2]=="")))
   	 {
   	    document.all('checkMax').value=strResult[0][2];
   	 }
   	 
   	 if(!((strResult[0][3]==null)||(strResult[0][3]=="")))
   	 {
   	    document.all('Remark').value=strResult[0][3];
   	 }
		if(!((strResult[0][4]==null)||(strResult[0][4]=="")))
   	 {
   	    document.all('ManageCom').value=strResult[0][4];
   	 }
  	}
}