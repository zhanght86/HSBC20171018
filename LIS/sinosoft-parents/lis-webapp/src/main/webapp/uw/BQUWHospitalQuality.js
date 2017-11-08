//程序名称：UWCustomerQuality.js
//程序功能：客户品质管理
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();

function queryClick()
{
	// 初始化表格
	//initQuestGrid();
	//initContent();
	
	// 书写SQL语句
//  var strSql = "select PrtNo, ProposalContNo, NotePadCont, Operator, MakeDate, MakeTime from LCNotePad where PrtNo='" + PrtNo 
//             + "' and ContNo='" + ContNo + "' and OperatePos='" + OperatePos + "'"
//             + getWherePart('Operator', 'Operator')
//             + getWherePart('MakeDate', 'MakeDate');
  
     var  Operator1 = window.document.getElementsByName(trim("Operator"))[0].value;
	 var  MakeDate1 = window.document.getElementsByName(trim("MakeDate"))[0].value;
     var sqlid1="BQUWHospitalQualitySql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.BQUWHospitalQualitySql");
	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
	 mySql1.addSubPara(PrtNo);//指定传入参数
	 mySql1.addSubPara(ContNo);//指定传入参数
	 mySql1.addSubPara(OperatePos);//指定传入参数
	 mySql1.addSubPara(Operator1);//指定传入参数
	 mySql1.addSubPara(MakeDate1);//指定传入参数
	 var strSql = mySql1.getString();
	
  turnPage.queryModal(strSql, QuestGrid); 
  
  fm.Content.value = "";
}


function showOne(parm1, parm2) {	
  //判断该行是否确实被选中
	if(document.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = document.all(parm1).all('QuestGridNo').value - 1;
	  fm.Content.value = turnPage.arrDataCacheSet[index][2];
  }
}

function UpdateClick() {
 // if (trim(fm.Remark.value) == "") {
//    alert("必须填写备注内容！");
//    return;
//  }
//

  //校验体检医院编码是否录入
  if(document.all('HospitalCode').value==null||document.all('HospitalCode').value=='')
  {
  	 alert("请先选择体检医院编码!");
  	 return false;
  }
  getAllChecked();
  //return false;
  
  fm.PrtNo.value = PrtNo;
  fm.ContNo.value = ContNo;
//  fm.OperatePos.value = OperatePos; 
  
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {   
  showInfo.close();         
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  initForm(PrtNo);
}

function initHospitCode()
{
	//alert(cCodeName+":"+Field.value);
 	//医院编码,医院名称,机构编码,机构名称
 	//alert(fm.customer.value);
// 	var strSQL="select a.hospitcode,a.hospitalname,a.mngcom,(select shortname from ldcom where comcode=a.mngcom) "
// 	          + " from ldhospital a ,LPPENotice b"
// 	          + " where a.hospitcode = b.hospitcode and b.EdorNo='"+EdorNo+"' and b.ContNo='"+ContNo+"'"
// 	          + " order by b.modifydate desc,b.modifytime desc";
 	
     var sqlid2="BQUWHospitalQualitySql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.BQUWHospitalQualitySql");
	 mySql2.setSqlId(sqlid2);//指定使用SQL的id
	 mySql2.addSubPara(EdorNo);//指定传入参数
	 mySql2.addSubPara(ContNo);//指定传入参数
	 var strSQL = mySql2.getString();
 	
 	//alert(strSQL);
   var arrResult=easyExecSql(strSQL);
   //alert(arrResult);    
   if(arrResult!=null)
   {                     
    	document.all('HospitalCode').value = arrResult[0][0];
    	document.all('HospitalCode1').value = arrResult[0][0];
    	document.all('HospitalName').value = arrResult[0][1];
    	document.all('ManageCom').value = arrResult[0][2];
    	document.all('ManageComName').value = arrResult[0][3];
    	//查询既往记录.
//	  	var tSQL = "select hospitalquality,managerquality,insidequality,qualityflag,remark,"
//	  	         + " (select codename from ldcode where codetype='qualityflag' and code=a.qualityflag) "
//	  	         + " from LCHospitalQualityRecord a "
//	             + " where hospitcode='"+document.all('HospitalCode').value+"' and contno='"+ContNo+"' ";
	  	
	  	 var sqlid3="BQUWHospitalQualitySql3";
		 var mySql3=new SqlClass();
		 mySql3.setResourceName("uw.BQUWHospitalQualitySql");
		 mySql3.setSqlId(sqlid3);//指定使用SQL的id
		 mySql3.addSubPara(document.all('HospitalCode').value);//指定传入参数
		 mySql3.addSubPara(ContNo);//指定传入参数
		 var tSQL = mySql3.getString();
	  	
	  	var arrResult1=easyExecSql(tSQL);
	  	if(arrResult1!=null)
	   	{
	   		 //初始化体检医院评分
	   		 showBodyCheck(arrResult1[0][0],'Score');
	   		 //showBodyCheck(arrResult1[0][1],'Manage');
	   		 //showBodyCheck(arrResult1[0][2],'Inner');
	   		 document.all('QualityFlag').value = arrResult1[0][3];
	   		 document.all('QualityFlagName').value = arrResult1[0][5];
	    	document.all('Remark').value = arrResult1[0][4];
	  	}
  	}
  	else
  	{
  		 alert("体检医院查询失败!");
  		 return false;
  	}	
  	  
   return;       
}	

function afterCodeSelect( cCodeName, Field )
{
	//alert(cCodeName+":"+Field.value);
 if(cCodeName=="HospitalCode"){
 	//医院编码,医院名称,机构编码,机构名称
 	//alert(fm.customer.value);
// 	var strSQL="select hospitcode,hospitalname,mngcom,(select shortname from ldcom where comcode=a.mngcom) "
// 	          + " from ldhospital a "
// 	          + " where hospitcode = '"+Field.value+"'";
 	
 	 var sqlid4="BQUWHospitalQualitySql4";
	 var mySql4=new SqlClass();
	 mySql4.setResourceName("uw.BQUWHospitalQualitySql");
	 mySql4.setSqlId(sqlid4);//指定使用SQL的id
	 mySql4.addSubPara(Field.value);//指定传入参数
	 var strSQL = mySql4.getString();
 	
 	//alert(strSQL);
   var arrResult=easyExecSql(strSQL);
   //alert(arrResult);    
   if(arrResult!=null)
   {                     
    //	document.all('HospitalCode').value = arrResult[0][0];
    	document.all('HospitalCode1').value = arrResult[0][0];
    	document.all('HospitalName').value = arrResult[0][1];
    	document.all('ManageCom').value = arrResult[0][2];
    	document.all('ManageComName').value = arrResult[0][3];
  	}
  	else
  	{
  		 alert("体检医院查询失败!");
  		 return false;
  	}
  	
  	//查询既往记录.
//  	var tSQL = "select hospitalquality,managerquality,insidequality,qualityflag,remark,"
//  	         + " (select codename from ldcode where codetype='qualityflag' and code=a.qualityflag) "
//  	         + " from LCHospitalQualityRecord a "
//             + " where hospitcode='"+document.all('HospitalCode').value+"' and contno='"+PrtNo+"' ";
  	
  	 var sqlid5="BQUWHospitalQualitySql5";
	 var mySql5=new SqlClass();
	 mySql5.setResourceName("uw.BQUWHospitalQualitySql");
	 mySql5.setSqlId(sqlid5);//指定使用SQL的id
	 mySql5.addSubPara(document.all('HospitalCode').value);//指定传入参数
	 mySql5.addSubPara(PrtNo);//指定传入参数
	 var tSQL = mySql5.getString();
  	
  	var arrResult1=easyExecSql(tSQL);
  	if(arrResult1!=null)
   	{
   		 //初始化体检医院评分
   		 showBodyCheck(arrResult1[0][0],'Score');
   		 showBodyCheck(arrResult1[0][1],'Manage');
   		 showBodyCheck(arrResult1[0][2],'Inner');
   		 document.all('QualityFlag').value = arrResult1[0][3];
   		 document.all('QualityFlagName').value = arrResult1[0][5];
    	document.all('Remark').value = arrResult1[0][4];
  	}
   
   return;       
 }	
// if(cCodeName=="paymode"){
//   	if(document.all('PayMode').value=="4"){
//   	  //divLCAccount1.style.display="";
//    }
//    else
//    {
//   	  //divLCAccount1.style.display="none";
//      //alert("accountImg===");	
//    }
// }
 //alert("aaa");
// afterCodeSelect2( cCodeName, Field );
}


function returnParent(){
  top.close();
	
}


function initHospitalCode(tEdorNo,tContNo)
{
    var i,j,m,n;
    var returnstr;

//    var strSql = " select hospitcode,(select hospitalname from ldhospital where hospitcode=a.hospitcode) "
//        			 + " from lppenotice a where edorno='"+tEdorNo+"' and contno='"+tContNo+"' ";
   
     var sqlid6="BQUWHospitalQualitySql6";
	 var mySql6=new SqlClass();
	 mySql6.setResourceName("uw.BQUWHospitalQualitySql");
	 mySql6.setSqlId(sqlid6);//指定使用SQL的id
	 mySql6.addSubPara(tEdorNo);//指定传入参数
	 mySql6.addSubPara(tContNo);//指定传入参数
	 var strSql = mySql6.getString();
    
    //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);

  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    //alert("查询失败！");
    return "";
  }

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
    for( i = 0;i < n; i++)
    {
        m = turnPage.arrDataCacheSet[i].length;
        //alert("M:"+m);
        if (m > 0)
        {
            for( j = 0; j< m; j++)
            {
                if (i == 0 && j == 0)
                {
                    returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
                }
                if (i == 0 && j > 0)
                {
                    returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
                }
                if (i > 0 && j == 0)
                {
                    returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
                }
                if (i > 0 && j > 0)
                {
                    returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
                }

            }
        }
        else
        {
            //alert("查询失败!!");
            return "";
        }
    }
}
else
{
    //alert("查询失败!");
    return "";
}
 // alert("returnstr:"+returnstr);
  fm.HospitalCode.CodeData = returnstr;
  //prompt('',returnstr);
  return returnstr;
}

//tongmeng 2008-10-10 add
//按照体检组合自动带出体检项目
function showBodyCheck(SpiltString,ShowTYPE)
{
	clearAllCheck(ShowTYPE);
	
	var strValue;
     strValue=SpiltString.split("#");
    // alert(SpiltString+":"+strValue.length);
    for(n=0;n<strValue.length;n++)
    {
      //alert(strValue[n]);
      if(strValue[n]!=null&&strValue[n]!='')
      {
      	//alert("document.fm."+strValue[n]+".checked = true");
      	eval("document.fm."+strValue[n]+".checked = true");
      }
    }
}


//清除所有check,设置为不选择
function clearAllCheck(ShowTYPE)
{
  for (elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++)
		{
			if (window.document.forms[0].elements[elementsNum].type != null && window.document.forms[0].elements[elementsNum].type != "")
			{
				if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				&&window.document.forms[0].elements[elementsNum].ShowTYPE ==ShowTYPE
				//||window.document.forms[0].elements[elementsNum].ShowTYPE=='radio'
				)
				{
					//alert(window.document.forms[0].elements[elementsNum].ShowTYPE);
				  //alert(window.document.forms[0].elements[elementsNum].id);
				  window.document.forms[0].elements[elementsNum].checked= false;
				}
			}
		}
}


//获取所有已经被勾选的数据
function getAllChecked()
{
   var tAllChecked = "";
   var ShowTYPE = "";
   for(n=1;n<=3;n++)
   {
   	  if(n==1)
   	  {
   	  	ShowTYPE = "Score";
   	  }
   	  else if(n==2)
   	  {
   	  	 ShowTYPE = "Manage";
   	  }	
   	  else if(n==3)
   	  {
   	  	 ShowTYPE = "Inner";
   	  }	
   	  
   	  tAllChecked = "";
   		for (elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++)
			{
				if (window.document.forms[0].elements[elementsNum].type != null && window.document.forms[0].elements[elementsNum].type != "")
				{
					if(window.document.forms[0].elements[elementsNum].type=='checkbox'
					&&window.document.forms[0].elements[elementsNum].ShowTYPE==ShowTYPE
				//||window.document.forms[0].elements[elementsNum].type=='radio'
					)
					{
				  //alert(window.document.forms[0].elements[elementsNum].id);
				  	if(window.document.forms[0].elements[elementsNum].checked)
				  	{
				     	tAllChecked = tAllChecked + "#" + window.document.forms[0].elements[elementsNum].id;
				  	}
					}
				}
			}
			//alert(tAllChecked+":"+"fm.CheckedItem"+n+".value='"+tAllChecked+"'");
			eval("fm.CheckedItem"+n+".value='"+tAllChecked+"'");
		}
	//	alert(tAllChecked);
	//fm.CheckedItem.value = tAllChecked;
}