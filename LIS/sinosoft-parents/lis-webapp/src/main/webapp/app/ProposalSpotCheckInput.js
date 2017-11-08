var showInfo;
var mDebug="1";
 var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sqlresourcename = "app.ProposalSpotCheckInputSql";

//function afterCodeSelect( cName, Filed)
//{
  //if(cName=='CertifyClassListNew')
  //{
   // displayMulLine();
  
	//} 
	//else if( cName == 'CertifyHaveNumber' ) 
  //{
		//displayNumberInfo();		
  //}
//}

//function displayMulLine()
//{
  //var displayType = fm.CertifyClass.value;

  //document.all('divShow').style.display="";
  //document.all('divCardRisk').style.display="";

//}

//根据选择的外包商编码查询数据库中是否已有定义的规则，如果有显示在界面上，如果没有，则提示对该外包商没有已定义的复核抽查规则
function afterCodeSelect( cCodeName, Field ) 
{
  if(cCodeName == 'queryBPOID')
  {
//	 var strsql="select riskcode,rate,maxmum,remark,ManageCom from BPOCheckCalMode where BPOID='"+document.all('BussNo').value+"'";
	 
var sqlid1="ProposalSpotCheckInputSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(document.all('BussNo').value);
	 
	 //prompt("",strsql);
	 var strResult= decodeEasyQueryResult(easyQueryVer3(mySql1.getString(), 1, 1, 1));  
   
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

//提交
function submitForm()
{

  if(vertifyInput() == false)
  {
	 return false;
  }
    
  fm.OperateType.value = "INSERT";
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
   
  if((document.all('BussNo').value=="")||(document.all('BussNo').value==null))
  {
    alert("请选择外包方编码");
    return false;
  }

  
  if((document.all('checkRate').value=="")||(document.all('checkRate').value==null))
  {
    	alert("请录入抽查比率");
    	return false;
  }
  else
  {
        //alert("222 "+isInteger(document.all('checkRate').value));
  		if(isInteger(document.all('checkRate').value)==false||isOneToHundred(document.all('checkRate').value)==0)
  		{
  		      //alert(isOneToHundred(document.all('checkRate').value));
  		      //alert(isInteger(document.all('checkRate').value));
  			  alert("抽查比率不是0-100之间的整数，请重新录入");
  			  return false;
  		}
  }
  
  if((document.all('checkMax').value=="")||(document.all('checkMax').value==null))
  {
    	alert("请录入抽查上限");
    	return false;
  }
  else
  {
  		if(isInteger(document.all('checkMax').value)==false)
  		{
  		      //alert(isOneToHundred(document.all('checkRate').value));
  		      //alert(isInteger(document.all('checkRate').value));
  			  alert("抽查上限不是整数，请重新录入");
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


