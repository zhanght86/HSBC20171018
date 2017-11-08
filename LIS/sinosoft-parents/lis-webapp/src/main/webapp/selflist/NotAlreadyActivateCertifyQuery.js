
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var showInfo;
var queryflag=0;////查询标志，限制在点击下载按钮前必须点击查询按钮，查看一下下载的记录:0表示未点击，1表示点击了记录，而且查询出了记录

// 查询按钮
function easyQueryClick()
{

	initCardInfo();
	
	//校验必录项必须录入
	if(vertifyInput() == false)
	{
		  return;
	}
	if(dateDiff(document.all('MakeDateB').value,document.all('MakeDateE').value,"D")>10 )
	{
		alert("缴费起止期间请控制在十天内，防止数据量过大");
		return ;
	}


	// 书写SQL语句
	var str="";
	var strSQL = "";
	//未激活卡单信息
	if(fm.IsPlan.value=="0"){
	//第一部分:已缴费未激活的卡单
//	strSQL=" select 卡单号, 核销标志, 缴费到帐标志, 缴费日期, 到帐日期 "
//	                    +" from (select /*+rule+*/ t.tempfeeno 卡单号,'已核销' 核销标志,(case when t.confmakedate is null then '未到帐' else '已到帐' end) 缴费到帐标志,"
//				     +" t.paydate 缴费日期,t.ConfMakeDate 到帐日期 from ljtempfee t, ratecard k where t.riskcode = k.riskcode and t.paymoney = k.prem"
//					+" and exists (select 1 from lccont where contno = t.tempfeeno and customgetpoldate is null)"        
//                         +  getWherePart( 't.tempfeeno','CertifyNo' );
	
             
   	if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate>='"+document.all('MakeDateB').value+"'";
   	}
   
   	if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate<='"+document.all('MakeDateE').value+"'";
   	}
   
   //第二部分:已缴费未核销
//   	strSQL=strSQL+" union select t.tempfeeno 卡单号,'未核销' 核销标志,(case when t.confmakedate is null then '未到帐' else '已到帐' end) 缴费到帐标志,"
//						  +" t.paydate 缴费日期, t.confmakedate 到帐日期 from ljtempfee t, ratecard k where t.riskcode = k.riskcode and t.paymoney = k.prem"
//					       +" and not exists (select 1 from lccont where contno = t.tempfeeno)"
//                                +getWherePart( 't.tempfeeno','CertifyNo' );
//   	
   	 	
          
    if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate>='"+document.all('MakeDateB').value+"'";
   	}
   
   	if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate<='"+document.all('MakeDateE').value+"'";
   	}
   	
   	
   	var sqlid1="NotAlreadyActivateCertifyQuerySql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("selflist.NotAlreadyActivateCertifyQuerySql");
 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
 	mySql1.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);//指定传入参数
 	mySql1.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);
 	strSQL = mySql1.getString();
   	
   }else if(fm.IsPlan.value=="1"){
    //第三部分:综拓 未核销

//   	strSQL=strSQL+" select 卡单号, 核销标志, 缴费到帐标志, 缴费日期, 到帐日期 from (select t.tempfeeno 卡单号,'未核销' 核销标志,(case when t.confmakedate is null then '未到帐' else '已到帐' end) 缴费到帐标志,"
//						  +" t.paydate 缴费日期, t.confmakedate 到帐日期 from ljtempfee t, ldplan d where t.riskcode = d.contplancode and d.portfolioflag=1"
//					       +" and not exists (select 1 from lccont where familyid = t.tempfeeno)"
//                                +getWherePart( 't.tempfeeno','CertifyNo' );
          
    if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate>='"+document.all('MakeDateB').value+"'";
   	}
   
   	if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate<='"+document.all('MakeDateE').value+"'";
   	}
     //第四部分:综拓 已核销

//   	strSQL=strSQL+" union select t.tempfeeno 卡单号,'已核销' 核销标志,(case when t.confmakedate is null then '未到帐' else '已到帐' end) 缴费到帐标志,"
//						  +" t.paydate 缴费日期, t.confmakedate 到帐日期 from ljtempfee t, ldplan d where t.riskcode = d.contplancode and d.portfolioflag=1"
//					       +" and not exists (select 1 from lccont where familyid = t.tempfeeno and customgetpoldate is null)"
//                                +getWherePart( 't.tempfeeno','CertifyNo' );
         
    if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate>='"+document.all('MakeDateB').value+"'";
   	}
   
   	if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate<='"+document.all('MakeDateE').value+"'";
   	}
   }
   
 //  strSQL=strSQL+" )order by 核销标志";
   
	var sqlid2="NotAlreadyActivateCertifyQuerySql2";
 	var mySql2=new SqlClass();
 	mySql2.setResourceName("selflist.NotAlreadyActivateCertifyQuerySql");
 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
 	mySql2.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);//指定传入参数
 	mySql2.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);
 	strSQL = mySql2.getString();
   
   
        
   //prompt("",strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) 
  {
  	queryflag=0;
    alert("没有所要查询的卡单信息！");
    return false;
  }
    
  queryflag=1;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = CardInfo;    
          
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
 
  strSQL=strSQL+"order by g.makedate;";
}


//校验必录项不能为空
function vertifyInput()
{	
	  //alert("document.all('MakeDateB').value"+document.all('MakeDateB').value);
	  //alert("document.all('MakeDateE').value"+document.all('MakeDateE').value);
	  
	   if(fm.CertifyNo.value == "" && fm.MakeDateB.value == "" && fm.MakeDateE.value == "")
	   {
	   	     alert("请录入卡号或者缴费起止日期");
	  	 	return false;
	   }
	  //如果录入激活日期,则必须录入起始和终止日期,不能只录入一个
	  if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
	  {	
	  	 if(document.all('MakeDateE').value==null||document.all('MakeDateE').value=="")
	  	 {
	  	 	alert("请录入缴费截止日期");
	  	 	return false;
	  	 }
	  }
	  
	  if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
	  {	
	  	 if(document.all('MakeDateB').value==null||document.all('MakeDateB').value=="")
	  	 {
	  	 	alert("请录入缴费起始日期");
	  	 	return false;
	  	 }
	  }
	  
	  
}

//打印查询结果
function easyPrint()
{
	//alert(queryflag);
	if(queryflag==0)
	{
		 alert("请先点击查询需要打印的记录");
		 return false;
	}
	easyQueryPrint(2,'CardInfo','turnPage');
}