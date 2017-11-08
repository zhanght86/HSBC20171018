var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.PostMidplatCertifyQuerySql";
// 查询按钮
function easyQueryClick()
{
	initCardInfo();
	
	//校验必录项必须录入
	if(verifyInput() == false)
	{
		return;
	}

	if(fm.StateFlag.value!=null && fm.StateFlag.value=="3"){  // 发放查询lzcard
	/*	var strSQL="select (select managecom from laagent where agentcode = substr(a.receivecom, 2)) managecom, "
			 +"substr(a.receivecom, 2), a.startno, a.endno, a.sumcount, a.makedate, '',  "
       		 +"(case a.stateflag when '3' then '已发放未核销' else '非法' end) "
			 +"from lzcard a where 1=1 and a.receivecom like 'D%'"
			+ getWherePart('a.CertifyCode', 'CertifyCode2')
			+ getWherePart('a.StateFlag', 'StateFlag')
			+ getWherePart('a.MakeDate', 'MakeDateB', '>=')
			+ getWherePart('a.MakeDate', 'MakeDateE', '<=')
			+ getWherePart('a.StartNo', 'StartNo', '>=')
			+ getWherePart('a.startno', 'EndNo', '<=');*/
		
		mySql = new SqlClass();
		mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
		mySql.setSqlId("PostMidplatCertifyQuerySql1");
		mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
		if(fm.Agentcode.value!=null && fm.Agentcode.value!=""){
			/*strSQL+=" and a.receivecom=concat('D','"+fm.Agentcode.value+"')";*/
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql2");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
			mySql.addSubPara(Agentcode); 
		}
		if(fm.ManageCom.value!=null && fm.ManageCom.value!=""){
			//strSQL+="and exists (select 1 from laagent where agentcode = substr(a.receivecom, 2) and managecom like '"+fm.ManageCom.value+"%')";
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql3");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("Agentcode"))[0].value); 
			mySql.addSubPara(ManageCom); 
		}
			/*strSQL+="order by a.makedate,a.startno";*/
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql4");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
	}else{	// 核销、作废、遗失、销毁查询lzcardB
		/*var strSQL="select (select managecom from laagent where agentcode = substr(a.sendoutcom, 2)) managecom, "
			 +"substr(a.sendoutcom, 2), a.startno, a.endno, a.sumcount, '', a.makedate, "
       		 +"(case a.stateflag when '4' then '自动缴销' when '5' then '手工缴销' when '6' then '使用作废' when '7' then '停用作废' when '10' then '遗失' when '11' then '销毁' else '非法' end) "
			 +"from lzcardB a where 1=1 and a.sendoutcom like 'D%'"
			+ getWherePart('a.CertifyCode', 'CertifyCode2')
			+ getWherePart('a.StateFlag', 'StateFlag')
			+ getWherePart('a.MakeDate', 'MakeDateB', '>=')
			+ getWherePart('a.MakeDate', 'MakeDateE', '<=')
			+ getWherePart('a.StartNo', 'StartNo', '>=')
			+ getWherePart('a.startno', 'EndNo', '<=');*/
		mySql = new SqlClass();
		mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
		mySql.setSqlId("PostMidplatCertifyQuerySql5");
		mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
		mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
		if(fm.Agentcode.value!=null && fm.Agentcode.value!=""){
			/*strSQL+=" and a.sendoutcom=concat('D','"+fm.Agentcode.value+"')";*/
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql6");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
			mySql.addSubPara(Agentcode); 
		}
		if(fm.ManageCom.value!=null && fm.ManageCom.value!=""){
			/*strSQL+="and exists (select 1 from laagent where agentcode = substr(a.sendoutcom, 2) and managecom like '"+fm.ManageCom.value+"%')";*/
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql7");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
			mySql.addSubPara(ManageCom); 
		}
			/*strSQL+="order by a.makedate,a.startno";*/
			mySql = new SqlClass();
			mySql.setResourceName("certify.PostMidplatCertifyQuerySql");
			mySql.setSqlId("PostMidplatCertifyQuerySql8");
			mySql.addSubPara(document.getElementsByName(trim("CertifyCode2"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StateFlag"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateB"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("MakeDateE"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("StartNo"))[0].value); 
			mySql.addSubPara(document.getElementsByName(trim("EndNo"))[0].value); 
	}			 

   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(mySql.getString(), CardInfo, 1);
   	if(CardInfo.mulLineCount==0)
   	{
   		alert("没有查询到任何单证信息！");
   		return false;
   	}
}


//打印查询结果
function easyPrint()
{
   	if(CardInfo.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
	easyQueryPrint(2,'CardInfo','turnPage');
}