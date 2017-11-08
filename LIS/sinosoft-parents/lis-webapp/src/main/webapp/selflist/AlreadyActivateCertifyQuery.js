
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var showInfo;
var queryflag=0;////查询标志，限制在点击下载按钮前必须点击查询按钮，查看一下下载的记录:0表示未点击，1表示点击了记录，而且查询出了记录

// 查询按钮
function easyQueryClick()
{
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
	if(fm.Portfolio.value.length>0){
		initFamilyCardInfo();
		divFamilyCardInfo.style.display="";	
		divContInfo.style.display="";	
		divCardInfo.style.display="none";
//		var strSQL = "select c.familyid, max(d.contplanname),max(c.appntno), "
//				+ " max(c.appntname), max(c.makedate), max(c.customgetpoldate),  max(g.cvalidate) ,  max(g.enddate), max(g.standbyflag1)  "
//				+ " from lcpol g, lccont c, ldplan d,lmcardrisk lm "
//				+ "  where  d.portfolioflag=1 and lm.riskcode=d.contplancode and g.contno=c.contno"
//                + getWherePart( 'c.familyid','CertifyNo' )
//                + getWherePart( 'd.contplancode','Portfolio' )
//		        + " and c.familyid is not null and c.customgetpoldate is not null ";
		
		
		
		if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
	   	{
	   		strSQL=strSQL+" and c.customgetpoldate >= '"+document.all('MakeDateB').value+"'";
	   	}
   
		if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
		{
			strSQL=strSQL+" and c.customgetpoldate <= '"+document.all('MakeDateE').value+"'";
		}
//		strSQL=strSQL + "  and  exists(select 1 from lzcard lz where lz.certifycode=lm.certifycode and c.familyid=startno  union select 1 from lzcardb lzb where lzb.certifycode=lm.certifycode and c.familyid=startno ) group by c.familyid";
				
		
		
		var sqlid1="AlreadyActivateCertifyQuerySql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("selflist.AlreadyActivateCertifyQuerySql");
	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);//指定传入参数
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Portfolio"))[0].value);
	 	mySql1.addSubPara(document.all('MakeDateB').value);
	 	mySql1.addSubPara(document.all('MakeDateE').value);
	 	var strSQL = mySql1.getString();
		
		
		turnPage.queryModal(strSQL,FamilyCardInfo);
	}else if (fm.RiskCode.value.length>0){
		initCardInfo();
		divFamilyCardInfo.style.display="none";	
		divContInfo.style.display="none";	
		divCardInfo.style.display="";
		// 书写SQL语句
		var str="";
		//已激活的卡单
//		var strSQL =" select g.contno,(select riskname from lmrisk where g.riskcode = riskcode),g.appntno,g.appntname,g.insuredno,g.insuredname,"
//                          +" g.makedate,c.customgetpoldate,g.cvalidate,g.enddate,g.standbyflag1 from lcpol g, lccont c,ratecard k where g.contno=c.contno"
//                          +" and c.customgetpoldate is not null and  g.riskcode = k.riskcode and g.prem = k.prem"
//	                     + getWherePart( 'g.contno','CertifyNo' )
//  	                     + getWherePart( 'g.riskcode','RiskCode' );
	                     
				
             
	   if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
	   {
	   		strSQL=strSQL+" and c.customgetpoldate >= '"+document.all('MakeDateB').value+"'";
	   }
   
		if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
		{
			strSQL=strSQL+" and c.customgetpoldate <= '"+document.all('MakeDateE').value+"'";
		}
   
//	   strSQL=strSQL+" order by c.customgetpoldate desc ";
	   
	   
	    var sqlid2="AlreadyActivateCertifyQuerySql2";
	 	var mySql2=new SqlClass();
	 	mySql2.setResourceName("selflist.AlreadyActivateCertifyQuerySql");
	 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
	 	mySql2.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);//指定传入参数
	 	mySql2.addSubPara(window.document.getElementsByName(trim("RiskCode"))[0].value);
	 	mySql2.addSubPara(document.all('MakeDateB').value);
	 	mySql2.addSubPara(document.all('MakeDateE').value);
	 	var strSQL = mySql2.getString();
	   
	   
		turnPage.queryModal(strSQL,CardInfo);
		return;
	}
}


//校验必录项不能为空
function vertifyInput()
{	
	  //alert("document.all('MakeDateB').value"+document.all('MakeDateB').value);
	  //alert("document.all('MakeDateE').value"+document.all('MakeDateE').value);
	   if(fm.CertifyNo.value == "" && fm.MakeDateB.value == "" && fm.MakeDateE.value == "")
	   {
	   	     alert("请录入卡号或者激活起止日期");
	  	 	return false;
	   }
	  
	  //如果录入激活日期,则必须录入起始和终止日期,不能只录入一个
	  if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
	  {	
	  	 if(document.all('MakeDateE').value==null||document.all('MakeDateE').value=="")
	  	 {
	  	 	alert("请录入激活截止日期");
	  	 	return false;
	  	 }
	  }
	  
	  if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
	  {	
	  	 if(document.all('MakeDateB').value==null||document.all('MakeDateB').value=="")
	  	 {
	  	 	alert("请录入激活起始日期");
	  	 	return false;
	  	 }
	  }
	
	  if(fm.RiskCode.value.length>0 && fm.Portfolio.value.length>0){
			alert("产品名称与险种代码只能录入一项");
	  	 	return false;	  
	  }
	  if(fm.RiskCode.value.length<=0 && fm.Portfolio.value.length<=0){
			alert("产品名称与险种编码请至少录入一项");
	  	 	return false;	  
	  }
}
function getFamilyCont(){
	var vRow = FamilyCardInfo.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	
	vRow = vRow - 1;
	
	//根据选中的记录给界面的职业信息赋值
	var tFamilyId	= FamilyCardInfo.getRowColData(vRow, 1);
//	var strSQL = "select c.prtno,c.contno,(select riskname from lmrisk where g.riskcode = riskcode),g.insuredno,g.insuredname "
//			+ " from lcpol g, lccont c "
//			+ " where g.contno = c.contno      and c.familyid is not null  and c.customgetpoldate is not null "
//			+ " and c.familyid='" + tFamilyId + "'";
	
	var sqlid3="AlreadyActivateCertifyQuerySql3";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("selflist.AlreadyActivateCertifyQuerySql");
 	mySql3.setSqlId(sqlid3); //指定使用SQL的id
 	mySql3.addSubPara(tFamilyId);//指定传入参数
 	var strSQL = mySql3.getString();
	
	turnPage.queryModal(strSQL,ContInfoGrid);
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