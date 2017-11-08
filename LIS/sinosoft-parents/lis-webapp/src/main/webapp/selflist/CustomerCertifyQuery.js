
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var showInfo;
var queryflag=0;////查询标志，限制在点击下载按钮前必须点击查询按钮，查看一下下载的记录:0表示未点击，1表示点击了记录，而且查询出了记录


// 保单明细查询
function PolClick()
{
	var arrReturn = new Array();
	//alert("**");
	var tSel = CardInfo.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    var cContNo = CardInfo.getRowColData(tSel-1,1);				
		if (cContNo == "")
		    return;		
		
	    window.open("../sys/PolDetailQueryMain.jsp?ContNo=" + cContNo +"&IsCancelPolFlag=0");	
	}
}

//提交前的校验、计算  
function beforeSubmit()
{
    //输入条件校验
    if(fm.CertifyNo.value == ""
       && fm.Name.value == ""
       //&& fm.Birthday.value == ""
      // && fm.Sex.value == ""
       //&& fm.IDType.value == ""
       && fm.IDNo.value == ""
       && fm.CustomerNo.value == "")
    {
        alert("请至少输入卡号、客户号码、姓名、证件号码中的一个条件!");
        return false;
    }
    return true;
}  

// 查询按钮
function easyQueryClick()
{

	initCardInfo();
	
	//输入条件校验
    if (!beforeSubmit())
    {
        return;
    }
    
    //alert("111");
    //return false;


	// 书写SQL语句
	var str="";
	var ttsql="";
	if(fm.IDNo.value!=""&&fm.IDNo.value!=null)
	{
		 ttsql = " and exists(select 1 from ldperson where idno='"+fm.IDNo.value+"')";
	}
	//客户信息查询,只有激活的卡单才会有客户信息，所以只查询已激活的卡单
//	var strSQL = "select t1, t2, t3, t4, t5, t6, t7, t8, t9, t10,t11, t12, t13, t14, t15, t16 from (select g.contno t1, (select riskname	from lmrisk where g.riskcode = riskcode) t2,"
//					 + " g.cvalidate t3, g.enddate t4, (select customgetpoldate from lccont where contno=g.contno) t5, case when now() > g.enddate then '失效'	 else	'有效' end t6,"
//					 + " d.AppntName t7,d.AppntNo t8,d.AppntBirthday t9,d.idtype t10,d.idno t11,a.phone t12,a.postaladdress t13,a.zipcode t14, (select LDOccupation.Occupationname"
//					 + " from LDOccupation where LDOccupation.Occupationcode = d.occupationcode and LDOccupation.Occupationtype = d.Occupationtype) t15, '投保人' t16"
//					 + " from lcappnt d, lcpol g,LCAddress a where d.contno = g.contno and d.appntno = g.appntno and a.customerno = d.appntno and a.addressno=d.addressno"
//					 + " and exists (select 1 from lccont where contno=g.contno and customgetpoldate is not null) "
//					 + getWherePart( 'g.contno','CertifyNo' )
//                          + getWherePart( 'd.AppntBirthday','Birthday' )
//                          + getWherePart( 'd.idtype','IDType' )
//                          //+ getWherePart( 'd.idno','IDNo' )
//                          + getWherePart( 'd.AppntName','Name' )
//                          + getWherePart( 'd.AppntSex','Sex' )
//                          + getWherePart( 'd.AppntNo','CustomerNo' )     
//                          + ttsql      
//				      + " union all"
//				      + " select g.contno t1,(select riskname from lmrisk where g.riskcode = riskcode) t2,g.cvalidate t3,g.enddate t4,(select customgetpoldate from lccont where contno=g.contno) t5,"
//					 + " case when now() > g.enddate then '失效' else '有效' end t6, e.name t7,e.insuredno t8,e.birthday t9,e.idtype t10,e.idno t11,b.phone t12,b.postaladdress t13,b.zipcode t14,"
//					 + " (select LDOccupation.Occupationname from LDOccupation where LDOccupation.Occupationcode = e.occupationcode and LDOccupation.Occupationtype = e.Occupationtype) t15,'被保人' t16"
//					 + " from lcinsured e, lcpol g, lcappnt d,LCAddress b where e.contno = g.contno and e.insuredno = g.insuredno and e.contno = d.contno and b.customerno = e.insuredno"
//                          + " and b.addressno=e.addressno and exists (select 1 from lccont where contno=g.contno and customgetpoldate is not null) and d.relationtoinsured <> '00'"
//					 + getWherePart( 'g.contno','CertifyNo' )
//                          + getWherePart( 'e.birthday','Birthday' )
//                          + getWherePart( 'e.idtype','IDType' )
//                          //+ getWherePart( 'e.idno','IDNo' )
//                          + getWherePart( 'e.name','Name' )
//                          + getWherePart( 'e.sex','Sex' )
//                          + getWherePart( 'e.insuredno','CustomerNo' )
//                          + ttsql
//                          + ") order by t8, t5 desc";	
	
		var sqlid1="CustomerCertifyQuerySql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("selflist.CustomerCertifyQuerySql");
	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);//指定传入参数
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Birthday"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("IDType"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Name"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Sex"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CustomerNo"))[0].value);
	 	mySql1.addSubPara(fm.IDNo.value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Birthday"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("IDType"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Name"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Sex"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CustomerNo"))[0].value);	 	
	 	mySql1.addSubPara(fm.IDNo.value);
	 	var strSQL = mySql1.getString();
	

 // prompt("",strSQL);
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) 
  {
  	queryflag=0;
    alert("没有所要查询的客户信息！");
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