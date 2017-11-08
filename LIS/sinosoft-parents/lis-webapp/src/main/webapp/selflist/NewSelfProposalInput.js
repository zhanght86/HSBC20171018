//该文件中包含客户端需要处理的函数和事件
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var showInfo;
var queryflag=0;////查询标志，限制在点击下载按钮前必须点击查询按钮，查看一下下载的记录:0表示未点击，1表示点击了记录，而且查询出了记录


//根据投保人职业的选择给界面中的相应元素赋值
function afterAppntLOccupationSelect()
{
	var vRow = AppntCodeGrid.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	
	vRow = vRow - 1;
	
	//根据选中的记录给界面的职业信息赋值
	fm.AppntOccupationType.value 		= AppntCodeGrid.getRowColData(vRow, 1);
	fm.AppntOccupationName.value 		= AppntCodeGrid.getRowColData(vRow, 2);
	fm.AppntWorkName.value 				= AppntCodeGrid.getRowColData(vRow, 3);
	fm.AppntOccupationCode.value 		= AppntCodeGrid.getRowColData(vRow, 4);
	
	//alert(fm.AppntOccupationType.value);
	//alert(fm.AppntOccupationCode.value);
	
}



//查询投保人职业代码，职业类别，职业名称信息
function queryAppntOccupation()
{

  	if((document.all( 'AppntOccupationName' ).value==null||document.all( 'AppntOccupationName' ).value=="")&&(document.all( 'AppntWorkName' ).value==null||document.all( 'AppntWorkName' ).value==""))
	{
		 alert("请输入职业名称，行业名称中任意一个或多个条件进行模糊查询!");
		 return false;
	}
	
	var str="";
	
	//根据模糊条件查询职业信息
//	var strSQL="select OccupationType,OccupationName,WorkName,Occupationcode from LDOccupation where occupationver='002'";
	
	
	
	
	//模糊条件根据用户输入的条件进行模糊查询
	if(!(document.all( 'AppntOccupationName' ).value==null||document.all( 'AppntOccupationName' ).value==""))
	{
		strSQL=strSQL+" and OccupationName like  '%25" + document.all( 'AppntOccupationName' ).value + "%25'" ;
	}
	
	if(!(document.all( 'AppntWorkName' ).value==null||document.all( 'AppntWorkName' ).value==""))
	{
		strSQL=strSQL+" and Workname like  '%25" + document.all( 'AppntWorkName' ).value + "%25'" ;
	}

	
	var sqlid1="NewSelfProposalInputSql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("selflist.NewSelfProposalInputSql");
 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
 	mySql1.addSubPara(document.all( 'AppntOccupationName' ).value);//传入指定参数
 	mySql1.addSubPara(document.all( 'AppntWorkName' ).value);
 	
 	
 	var strSQL = mySql1.getString();
	

    //prompt("",strSQL);
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
   //判断是否查询成功
   if (!turnPage.strQueryResult) 
   {
  		queryflag=0;
    	alert("没有所要查询的职业信息！");
    	return false;
   }
    
   queryflag=1;
   //查询成功则拆分字符串，返回二维数组
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
   //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
   turnPage.pageDisplayGrid = AppntCodeGrid;    
          
   //保存SQL语句
   turnPage.strQuerySql     = strSQL; 
  
   //设置查询起始位置
   turnPage.pageIndex       = 0;  
  
   //在查询结果数组中取出符合页面显示大小设置的数组
   arrDataSet= turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
   //tArr=chooseArray(arrDataSet,[0]) 
   //调用MULTILINE对象显示查询结果
  
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
   //displayMultiline(tArr, turnPage.pageDisplayGrid);
}

//根据被保险人职业的选择给界面中的相应元素赋值
function afterLOccupationSelect()
{
	var vRow = LCInsuredCodeGrid.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	
	vRow = vRow - 1;
	
	//根据选中的记录给界面的职业信息赋值
	fm.LCInsuredOccupationType.value 		= LCInsuredCodeGrid.getRowColData(vRow, 1);
	fm.LCInsuredOccupationName.value 		= LCInsuredCodeGrid.getRowColData(vRow, 2);
	fm.LCInsuredWorkName.value 				= LCInsuredCodeGrid.getRowColData(vRow, 3);
	fm.LCInsuredOccupationCode.value 		= LCInsuredCodeGrid.getRowColData(vRow, 4);
	
	//alert(fm.LCInsuredOccupationCode.value);
	
}

function afterInsuredSelect()
{
	var vRow = LCInsuredGrid.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	
	vRow = vRow - 1;
	fm.LCInsuredName.value 		= LCInsuredGrid.getRowColData(vRow, 1);
	fm.LCInsuredSex.value 		= LCInsuredGrid.getRowColData(vRow, 2);
	fm.LCInsuredBirthday.value 				= LCInsuredGrid.getRowColData(vRow, 3);
	fm.LCInsuredIDType.value 		= LCInsuredGrid.getRowColData(vRow, 4);
	fm.LCInsuredIDNo.value 		= LCInsuredGrid.getRowColData(vRow, 5);
	fm.LCInsuredOccupationType.value 		= LCInsuredGrid.getRowColData(vRow, 6);
	fm.LCInsuredOccupationCode.value 				= LCInsuredGrid.getRowColData(vRow, 7);
	fm.LCInsuredPostalAddress.value 		= LCInsuredGrid.getRowColData(vRow, 8);
	fm.LCInsuredZipCode.value 		= LCInsuredGrid.getRowColData(vRow, 9);
	fm.LCInsuredPhone.value 		= LCInsuredGrid.getRowColData(vRow, 10);
	fm.LCInsuredEMail.value 				= LCInsuredGrid.getRowColData(vRow, 11);
	fm.RelationToAppnt.value 				= LCInsuredGrid.getRowColData(vRow, 12);
}

//查询被保险人职业代码，职业类别，职业名称信息
function queryLcInsuredOccupation()
{

  	if((document.all( 'LCInsuredOccupationName' ).value==null||document.all( 'LCInsuredOccupationName' ).value=="")&&(document.all( 'LCInsuredWorkName' ).value==null||document.all( 'LCInsuredWorkName' ).value==""))
	{
		 alert("请输入职业名称，行业名称中任意一个或多个条件进行模糊查询!");
		 return false;
	}
	
	var str="";
	
	//根据模糊条件查询职业信息
//	var strSQL="select OccupationType,OccupationName,WorkName,Occupationcode from LDOccupation where occupationver='002'";
	
	
	//模糊条件根据用户输入的条件进行模糊查询
	if(!(document.all( 'LCInsuredOccupationName' ).value==null||document.all( 'LCInsuredOccupationName' ).value==""))
	{
		strSQL=strSQL+" and OccupationName like  '%25" + document.all( 'LCInsuredOccupationName' ).value + "%25'" ;
	}
	
	if(!(document.all( 'LCInsuredWorkName' ).value==null||document.all( 'LCInsuredWorkName' ).value==""))
	{
		strSQL=strSQL+" and Workname like  '%25" + document.all( 'LCInsuredWorkName' ).value + "%25'" ;
	}

	
	var sqlid2="NewSelfProposalInputSql2";
 	var mySql2=new SqlClass();
 	mySql2.setResourceName("selflist.NewSelfProposalInputSql");
 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
 	mySql2.addSubPara(document.all( 'LCInsuredOccupationName' ).value);//传入指定参数
 	mySql2.addSubPara(document.all( 'LCInsuredWorkName' ).value);
 	var strSQL = mySql2.getString();
	
	

    //prompt("",strSQL);
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
   //判断是否查询成功
   if (!turnPage.strQueryResult) 
   {
  		queryflag=0;
    	alert("没有所要查询的职业信息！");
    	return false;
   }
    
   queryflag=1;
   //查询成功则拆分字符串，返回二维数组
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
   //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
   turnPage.pageDisplayGrid = LCInsuredCodeGrid;    
          
   //保存SQL语句
   turnPage.strQuerySql     = strSQL; 
  
   //设置查询起始位置
   turnPage.pageIndex       = 0;  
  
   //在查询结果数组中取出符合页面显示大小设置的数组
   arrDataSet= turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
   //tArr=chooseArray(arrDataSet,[0]) 
   //调用MULTILINE对象显示查询结果
  
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
   //displayMultiline(tArr, turnPage.pageDisplayGrid);
}


//选择指定生效日期复选框时触发的操作
function callCValiDate(ttype)
{
    //alert(ttype);
	switch (ttype)
	{  
	        case "01" : 
	            document.all("CValiDate").readOnly = false;
	            document.all("CValiDate").value = "";
	        default :
	            return;
	}	
}


/*********************************************************************
 *  选择与被保人关系后的动作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect(cCodeName, Field) 
{
	if(cCodeName == "RelationToLCInsured") 
	{  
	    //alert(Field.value);
	    //如果"被保人关系选择为父亲或母亲,则显示被保人关系"
	    if(Field.value=="00")
	    {
			divLCInsure1.style.display="none";  	
	    }
	    else
	    {
	        divLCInsure1.style.display="";   
	    }

	}
}


//投保人客户号查询按扭事件
function queryAppntNo() 
{
  if (document.all("AppntCustomerNo").value == "")
   {
      //打开客户信息查询界面
      showAppnt1();
  } 
  else 
  {
//    var sql="select CustomerNo,name,sex,birthday,idtype,idno,PostalAddress,ZipCode,Mobile,Email,OccupationType,OccupationCode from LDPerson where CustomerNo = '" + document.all("AppntCustomerNo").value + "'";
    
    var sqlid3="NewSelfProposalInputSql3";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("selflist.NewSelfProposalInputSql");
 	mySql3.setSqlId(sqlid3); //指定使用SQL的id
 	mySql3.addSubPara(document.all("AppntCustomerNo").value);//指定传入参数
 	var sql = mySql3.getString();
    
    prompt("根据投保人客户号查询客户信息",sql);
    arrResult = easyExecSql(sql, 1, 0);
    
    if (arrResult == null) 
    {
      alert("未查到投保人信息");
      displayAppnt(new Array());
    } 
    else 
    {
      displayAppnt(arrResult[0]);
    }
  }
}


//打开客户信息查询界面,进行客户信息查询*********************************************************************
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 1;
		showInfo = window.open( "../sys/LDPersonQuery.html" );
	}
}



/*********************************************************************
 *  把数组中的数据显示到投保人部分
 *  参数  ：  个人客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt(cArr) 
{
	// 从LDPerson表取数据
	try
	{
	  	document.all( 'AppntCustomerNo' ).value          = cArr[0];
  		document.all( 'AppntName' ).value                = cArr[1];
  		document.all( 'AppntSex' ).value                 = cArr[2];
  		document.all( 'AppntBirthday' ).value            = cArr[3];
  		document.all( 'AppntIDType' ).value              = cArr[4];
  		document.all( 'AppntIDNo' ).value                = cArr[5];
  		document.all( 'AppntPostalAddress' ).value       = cArr[6];
  		document.all( 'AppntZipCode' ).value             = cArr[7];
  		document.all( 'AppntPhone' ).value              = cArr[8];
  		document.all( 'AppntEMail' ).value               = cArr[9];
  		document.all( 'AppntOccupationType' ).value      = cArr[10];
  		document.all( 'AppntOccupationCode' ).value      = cArr[11];
	}
	catch(ex)
	{
	   alert("回置投保人信息错误！");
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
	if( arrQueryResult != null )
	 {
		arrResult = arrQueryResult;
		
		if( mOperate == 1 ) 
		{		// 投保人信息	  
			
//			var sql="select CustomerNo,name,sex,birthday,idtype,idno,PostalAddress,ZipCode,Mobile,Email,OccupationType,OccupationCode from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'";
			
			var sqlid4="NewSelfProposalInputSql4";
		 	var mySql4=new SqlClass();
		 	mySql4.setResourceName("selflist.NewSelfProposalInputSql");
		 	mySql4.setSqlId(sqlid4); //指定使用SQL的id
		 	mySql4.addSubPara(arrQueryResult[0][0]);//指定传入参数
		 	var sql = mySql4.getString();
			
			arrResult = easyExecSql(sql, 1, 0);
			if (arrResult == null)
			 {
			  alert("未查到投保人信息");
			} 
			else 
			{
			   displayAppnt(arrResult[0]);
			}

	   }
	   
		if( mOperate == 2 )	
		{		// 主被保人信息
//			var sql="select CustomerNo,name,sex,birthday,idtype,idno,PostalAddress,ZipCode,Mobile,Email,OccupationType,OccupationCode from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'"
			
			var sqlid5="NewSelfProposalInputSql5";
		 	var mySql5=new SqlClass();
		 	mySql5.setResourceName("selflist.NewSelfProposalInputSql");
		 	mySql5.setSqlId(sqlid5); //指定使用SQL的id
		 	mySql5.addSubPara(arrQueryResult[0][0]);//指定传入参数
		 	var sql = mySql5.getString();
			
			arrResult = easyExecSql(sql, 1, 0);
			if (arrResult == null) 
			{
			  alert("未查到主被保人信息");
			} 
			else
			{
			   displayInsured(arrResult[0]);
			}

	    }

	}
	mOperate = 0;		// 恢复初态

}






//*************************************************************
//被保人客户号查询按扭事件
function queryInsuredNo()
 {
 
   if (document.all("LCInsuredCustomerNo").value == "") 
   {
      showInsured1();
   }  
   else 
   {
//     var sql="select CustomerNo,name,sex,birthday,idtype,idno,PostalAddress,ZipCode,Mobile,Email,OccupationType,OccupationCode from LDPerson where CustomerNo = '" + document.all("LCInsuredCustomerNo").value + "'";
    
        var sqlid6="NewSelfProposalInputSql6";
	 	var mySql6=new SqlClass();
	 	mySql6.setResourceName("selflist.NewSelfProposalInputSql");
	 	mySql6.setSqlId(sqlid6); //指定使用SQL的id
	 	mySql6.addSubPara(document.all("LCInsuredCustomerNo").value);//指定传入参数
	 	var sql = mySql6.getString();
     
     //prompt("根据投保人客户号查询客户信息",sql);
     arrResult = easyExecSql(sql, 1, 0);
    
     if (arrResult == null) 
     {
        alert("未查到被保人信息");
     } 
     else 
     {
       displayInsured(arrResult[0]);
     }
  }
}


//*********************************************************************
function showInsured1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQuery.html" );
	}
} 


/*********************************************************************
 *  把查询返回的客户数据显示到被保人部分
 *  参数  ：  客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayInsured(cArr) 
{
	// 从LDPerson表取数据
	try
	{
	  	document.all( 'LCInsuredCustomerNo' ).value          = cArr[0];
  		document.all( 'LCInsuredName' ).value                = cArr[1];
  		document.all( 'LCInsuredSex' ).value                 = cArr[2];
  		document.all( 'LCInsuredBirthday' ).value            = cArr[3];
  		document.all( 'LCInsuredIDType' ).value              = cArr[4];
  		document.all( 'LCInsuredIDNo' ).value                = cArr[5];
  		document.all( 'LCInsuredPostalAddress' ).value       = cArr[6];
  		document.all( 'LCInsuredZipCode' ).value             = cArr[7];
  		document.all( 'LCInsuredPhone' ).value              = cArr[8];
  		document.all( 'LCInsuredEMail' ).value               = cArr[9];
  		document.all( 'LCInsuredOccupationType' ).value      = cArr[10];
  		document.all( 'LCInsuredOccupationCode' ).value      = cArr[11];
	}
	catch(ex)
	{
	   alert("回置被保人信息错误！");
	}
}


/*********************************************************************
 *  校验输入的数据非空,并且如果投保人与被保人的关系是父母,则姓名,性别,出生日期不能全部相同,否则认定是同一人
 *  参数  ：  客户的信息
 *  返回值：  无
 *********************************************************************
 */
function checkAppnt()
{
    //如果勾选了指定生效日期,则必须选择生效日期
    //alert(document.all('CValiDateType').checked);
    
    //指定生效日期标志和填写生效日期必须同时进行,要填写都要填写
    if(document.all('CValiDateType').checked)
    {
        if(document.all( 'CValiDate' ).value==null||document.all( 'CValiDate' ).value=="")
        {
        	alert("请选择生效日期!");
	    	return false;
        }
    }
    
    if(!(document.all( 'CValiDate' ).value==null||document.all( 'CValiDate' ).value==""))
    {
    	if(!document.all('CValiDateType').checked)
    	{
        	alert("请勾选指定生效日期标志!");
	    	return false;
    	}
    }
    
	 //投保人信息校验
		if(document.all( 'AppntName' ).value==null||document.all( 'AppntName' ).value=="")
		{
		    alert("请输入投保人姓名!");
		    return false;
		}
	if(document.all( 'AppntSex' ).value==null||document.all( 'AppntSex' ).value=="")
		{
		    alert("请选择投保人性别!");
		    return false;
		}
	if(document.all( 'AppntSex' ).value=='2')
		{
		    alert("投保人性别不能为2-不详!");
		    return false;
		}
	if(document.all( 'AppntBirthday' ).value==null||document.all( 'AppntBirthday' ).value=="")
		{
		    alert("请选择投保人的出生日期!");
		    return false;
		}
	if(document.all( 'AppntIDType' ).value==null||document.all( 'AppntIDType' ).value=="")
		{
		    alert("请选择投保人的证件类型!");
		    return false;
		}
	//有证件时必须录入证件号
		if(document.all( 'AppntIDType' ).value!=9)
		{
			if(document.all( 'AppntIDNo' ).value==null||document.all( 'AppntIDNo' ).value=="")
			{
		    	alert("请输入投保人的证件号码!");
		    	return false;
			}
		}
		else
		{
			alert("投保人证件类型不能为9-无证件!");
		    return false;
		}
	if(document.all( 'AppntIDType' ).value=="0")
		{
		    if(document.all( 'AppntIDNo' ).value.length!=15&&document.all( 'AppntIDNo' ).value.length!=18)
		    {
		    	alert("身份证号必须是15或18位的!");
		    	return false;
		    }
		}
	if(document.all( 'AppntPhone' ).value==null||document.all( 'AppntPhone' ).value=="")
		{
		    alert("请输入投保人的联系电话!");
		    return false;
		}
		if(document.all( 'AppntZipCode' ).value==null||document.all( 'AppntZipCode' ).value=="")
		{
		    alert("请输入投保人联系地址所在地的邮编!");
		    return false;
		}

		
		if(document.all( 'AppntZipCode' ).value.length!=6)
		{
	    	alert("邮编长度必须是6位!");
	    	return false;
		}

		if(!doCheckNumber(document.all('AppntZipCode' ).value))
		{
			alert("邮编必须是数字");
			return false;
		}
    return true;
}
function checkGrid(){
	var vRow = LCInsuredGrid.mulLineCount;
	
	if( vRow == null || vRow == 0 ) 
	{
		alert("请录入被保人信息");
		return;
	}
	if(vRow!=fm.Peoples.value){
		alert("录入被保人数量错误，应录入"+fm.Peoples.value+"名被保人，实录"+vRow+"名被保人");
		return;
	}
	vRow = vRow -1 ;
	for(var i = 0;i<=vRow;i++){
		var tLCInsuredName 		= LCInsuredGrid.getRowColData(i, 1);
		var tLCInsuredSex 		= LCInsuredGrid.getRowColData(i, 2);
		var tLCInsuredBirthday 				= LCInsuredGrid.getRowColData(i, 3);
		var tLCInsuredIDType 		= LCInsuredGrid.getRowColData(i, 4);
		var tLCInsuredIDNo 		= LCInsuredGrid.getRowColData(i, 5);
		var tLCInsuredOccupationType 		= LCInsuredGrid.getRowColData(i, 6);
		var tLCInsuredOccupationCode 				= LCInsuredGrid.getRowColData(i, 7);
		var tLCInsuredPostalAddress 		= LCInsuredGrid.getRowColData(i, 8);
		var tLCInsuredZipCode 		= LCInsuredGrid.getRowColData(i, 9);
		var tLCInsuredPhone 		= LCInsuredGrid.getRowColData(i, 10);
		var tLCInsuredEMail 				= LCInsuredGrid.getRowColData(i, 11);
		var tRelationToAppnt 				= LCInsuredGrid.getRowColData(i, 12);
	
			 	//被保人信息校验
	  if(tLCInsuredName==null||tLCInsuredName=="")
		{
	    	alert("请输入被保人姓名!");
	    	return false;
		}
	
		if(tRelationToAppnt==null||tRelationToAppnt=="")
		{
	    	alert("请选择与投保人关系!");
	    	return false;
		}
		if(tLCInsuredSex==null||tLCInsuredSex=="")
		{
	    	alert("请选择被保人性别!");
	    	return false;
		}
		else if(tLCInsuredSex=='2')
		{
			alert("被保人性别不能为2-不详!");
	    	return false;
		}
		if(tLCInsuredBirthday==null||tLCInsuredBirthday=="")
		{
		    alert("请选择被保人的出生日期!");
		    return false;
		}
		
		if(tLCInsuredIDType==null||tLCInsuredIDType=="")
		{
		    alert("请选择被保人的证件类型!");
		    return false;
		}
	
		if(tLCInsuredIDType!=9)
		{
		  if(tLCInsuredIDNo==null||tLCInsuredIDNo=="")
			{
		    	alert("请输入被保人的证件号码!");
		    	return false;
			}
		}
		else
		{
			alert("被保人证件类型不能为9-无证件!");
		    return false;
		}
		
		//如果证件类型选择为身份证,则证件类型必须要是15-18位的
		if(tLCInsuredIDType=="0")
		{
	    	if(tLCInsuredIDNo.length!=15&&tLCInsuredIDNo.length!=18)
	    	{
	    		alert("身份证号必须是15或18位的!");
	    		return false;
	    	}
		}
	
		if(tLCInsuredPostalAddress==null||tLCInsuredPostalAddress=="")
		{
		    alert("请输入被保人的联系地址!");
		    return false;
		}
		
		if(tLCInsuredZipCode==null||tLCInsuredZipCode=="")
		{
		    alert("请输入被保人的联系地址所在地的邮编!");
		    return false;
		}

		
		if(tLCInsuredZipCode.length!=6)
		{
	    	alert("邮编长度必须是6位!");
	    	return false;
		}

		if(!doCheckNumber(tLCInsuredZipCode))
		{
			alert("邮编必须是数字");
			return false;
		}
		
		if(tLCInsuredPhone==null||tLCInsuredPhone=="")
		{
		    alert("请输入被保人的联系电话!");
		    return false;
		}
		
		
	}//循环结束
	return true;
}
function checkInsured(){

		var tLCInsuredName 		= fm.LCInsuredName.value;
		var tLCInsuredSex 		= fm.LCInsuredSex.value;
		var tLCInsuredBirthday 				= fm.LCInsuredBirthday.value;
		var tLCInsuredIDType 		= fm.LCInsuredIDType.value;
		var tLCInsuredIDNo 		= fm.LCInsuredIDNo.value;
		var tLCInsuredOccupationType 		= fm.LCInsuredOccupationType.value;
		var tLCInsuredOccupationCode 				= fm.LCInsuredOccupationCode.value;
		var tLCInsuredPostalAddress 		= fm.LCInsuredPostalAddress.value;
		var tLCInsuredZipCode 		= fm.LCInsuredZipCode.value;
		var tLCInsuredPhone 		= fm.LCInsuredPhone.value;
		var tLCInsuredEMail 				= fm.LCInsuredEMail.value;
		var tRelationToAppnt 				= fm.RelationToAppnt.value;
	
			 	//被保人信息校验
	  if(tLCInsuredName==null||tLCInsuredName=="")
		{
	    	alert("请输入被保人姓名!");
	    	return false;
		}
	
		if(tRelationToAppnt==null||tRelationToAppnt=="")
		{
	    	alert("请选择与投保人关系!");
	    	return false;
		}
		if(tLCInsuredSex==null||tLCInsuredSex=="")
		{
	    	alert("请选择被保人性别!");
	    	return false;
		}
		else if(tLCInsuredSex=='2')
		{
			alert("被保人性别不能为2-不详!");
	    	return false;
		}
		if(tLCInsuredBirthday==null||tLCInsuredBirthday=="")
		{
		    alert("请选择被保人的出生日期!");
		    return false;
		}
		
		if(tLCInsuredIDType==null||tLCInsuredIDType=="")
		{
		    alert("请选择被保人的证件类型!");
		    return false;
		}
	
		if(tLCInsuredIDType!=9)
		{
		  if(tLCInsuredIDNo==null||tLCInsuredIDNo=="")
			{
		    	alert("请输入被保人的证件号码!");
		    	return false;
			}
		}
		else
		{
			alert("被保人证件类型不能为9-无证件!");
		    return false;
		}
		
		//如果证件类型选择为身份证,则证件类型必须要是15-18位的
		if(tLCInsuredIDType=="0")
		{
	    	if(tLCInsuredIDNo.length!=15&&tLCInsuredIDNo.length!=18)
	    	{
	    		alert("身份证号必须是15或18位的!");
	    		return false;
	    	}
		}
	
		if(tLCInsuredPostalAddress==null||tLCInsuredPostalAddress=="")
		{
		    alert("请输入被保人的联系地址!");
		    return false;
		}
		
		if(tLCInsuredZipCode==null||tLCInsuredZipCode=="")
		{
		    alert("请输入被保人的联系地址所在地的邮编!");
		    return false;
		}

		
		if(tLCInsuredZipCode.length!=6)
		{
	    	alert("邮编长度必须是6位!");
	    	return false;
		}

		if(!doCheckNumber(tLCInsuredZipCode))
		{
			alert("邮编必须是数字");
			return false;
		}
		
		if(tLCInsuredPhone==null||tLCInsuredPhone=="")
		{
		    alert("请输入被保人的联系电话!");
		    return false;
		}
		
	return true;
}
function doCheckNumber(str)
{ 
    //alert(str);
	for(var i=0; i<str.length; i++)
	{
		if(str.charAt(i)<'0' || str.charAt(i)>'9')
		{
			return false;
		}
	}
	
	return true;
}

function AddInsured()
{
	if(!checkInsured()) return;
	LCInsuredGrid.addOne();
	var maxLine = LCInsuredGrid.mulLineCount;

	LCInsuredGrid.setRowColData(maxLine-1,1,fm.LCInsuredName.value);
	LCInsuredGrid.setRowColData(maxLine-1,2,fm.LCInsuredSex.value);
	LCInsuredGrid.setRowColData(maxLine-1,3,fm.LCInsuredBirthday.value);
	LCInsuredGrid.setRowColData(maxLine-1,4,fm.LCInsuredIDType.value);
	LCInsuredGrid.setRowColData(maxLine-1,5,fm.LCInsuredIDNo.value);
	LCInsuredGrid.setRowColData(maxLine-1,6,fm.LCInsuredOccupationType.value);
	LCInsuredGrid.setRowColData(maxLine-1,7,fm.LCInsuredOccupationCode.value);
	LCInsuredGrid.setRowColData(maxLine-1,8,fm.LCInsuredPostalAddress.value);
	LCInsuredGrid.setRowColData(maxLine-1,9,fm.LCInsuredZipCode.value);
	LCInsuredGrid.setRowColData(maxLine-1,10,fm.LCInsuredPhone.value);
	LCInsuredGrid.setRowColData(maxLine-1,11,fm.LCInsuredEMail.value);
	LCInsuredGrid.setRowColData(maxLine-1,12,fm.RelationToAppnt.value);
	
	fm.EditPeoples.value=maxLine;
}

function RemoveInsured()
{
	var vRow = LCInsuredGrid.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	vRow = vRow -1 ;

	LCInsuredGrid.delRadioTrueLine();
	
	fm.EditPeoples.value=LCInsuredGrid.mulLineCount;
}

function UpdateInsured()
{
	if(!checkInsured()) return;
	var vRow = LCInsuredGrid.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	vRow = vRow -1 ;
	LCInsuredGrid.setRowColData(vRow,1,fm.LCInsuredName.value);
	LCInsuredGrid.setRowColData(vRow,2,fm.LCInsuredSex.value);
	LCInsuredGrid.setRowColData(vRow,3,fm.LCInsuredBirthday.value);
	LCInsuredGrid.setRowColData(vRow,4,fm.LCInsuredIDType.value);
	LCInsuredGrid.setRowColData(vRow,5,fm.LCInsuredIDNo.value);
	LCInsuredGrid.setRowColData(vRow,6,fm.LCInsuredOccupationType.value);
	LCInsuredGrid.setRowColData(vRow,7,fm.LCInsuredOccupationCode.value);
	LCInsuredGrid.setRowColData(vRow,8,fm.LCInsuredPostalAddress.value);
	LCInsuredGrid.setRowColData(vRow,9,fm.LCInsuredZipCode.value);
	LCInsuredGrid.setRowColData(vRow,10,fm.LCInsuredPhone.value);
	LCInsuredGrid.setRowColData(vRow,11,fm.LCInsuredEMail.value);
	LCInsuredGrid.setRowColData(vRow,12,fm.RelationToAppnt.value);

}





/*********************************************************************
 *  提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm() 
{

	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  

  
    
	// 校验如果与被保人关系选的是父母,则投被保人信息不能相同
    try 
    { 
    	if (!checkAppnt()) return false; 
      if (!checkGrid()) return false;
      mAction = "Confirm";
			document.all( 'fmAction' ).value = mAction;	
			fm.action="../selflist/NewSelfProposalSave.jsp"	
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
    catch(e) 
    {
        alert("进行输入数据校验错误");
        return false;
    }
}

/*********************************************************************
 *  保存自助卡丹的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close(); 
    mAction = ""; 
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
		  var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  		
		  var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		  //通过校验则转向客户具体信息录入界面
          window.location.href="./ActivateInput.jsp";
	}	
}













