
<%
	//程序名称：bomUpdateInit.jsp
	//程序功能：对BOM和词条的初始化
	//创建日期：2008-8-13
	//创建人  ：
	//更新记录：
%>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<%
	//添加页面控件的初始化。
%>

<script language="JavaScript">
	var tResourceName = 'ibrms.bomUpdateSql';
	
	function initInpBox() {
		try {
			// 父BOM
			document.all('fBOM').value = "";
			var eName = document.all("eName").value;
			//var bomsql = "select * from lrbom where name='" + eName + "'";
			
		var sqlid1="bomUpdateSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(eName);//指定传入的参数
	  var strSql=mySql1.getString();	
	
			
			
			//prompt(this,bomsql);
			var arrResult = easyExecSql(strSql, 1, 0);
			var i = arrResult.length;
			//alert('i:'+i);
			if (arrResult == null) {
				return;
			} else {
				for (j = 0; j < i; j++) {
					//alert('j:'+arrResult[j].length);
					document.all('eName').value = arrResult[j][0];
					document.all('cName').value = arrResult[j][1];
					
					// 父BOM的初始化
					document.all('fBOM').value = arrResult[j][2];
					//对本BOM字段的初始化
					document.all('localItem').value = arrResult[j][3];
					//父BOM字段的初始化
					document.all('fatherItem').value = arrResult[j][4];
					document.all('bomLevel').value = arrResult[j][5];
					document.all('Business').value = arrResult[j][6];
					document.all('Valid').value = arrResult[j][9];

					document.all('Description').value = arrResult[j][7];
					//var fbomsql;
					var sqlid2="bomUpdateSql2";
					var mySql2=new SqlClass();
					mySql2.setResourceName(tResourceName); //指定使用的properties文件名
					mySql2.setSqlId(sqlid2);//指定使用的Sql的id
					//mySql2.addSubPara(eName);//指定传入的参数
		
					if (eName == '') {
						//fbomsql = "select name,cnname from (select * from lrbom where name<>' ')";
					} else {
						/*fbomsql = "select name,cnname from (select * from lrbom where name<>'"
								+ arrResult[j][0] + "')";
								*/
								mySql2.addSubPara(arrResult[j][0]);
					}
					//alert('1');
					var tem = easyQueryVer3(mySql2.getString(), 1, 0,1);
					if (tem == false) {
						document.all('fBOM').CodeData = '';
					} else {
						document.all('fBOM').CodeData = tem;
					}
					//alert(arrResult[j][2]);
					if(document.all('fBOM').value!='')
					{
						var sqlid3="bomUpdateSql3";
						var mySql3=new SqlClass();
						mySql3.setResourceName(tResourceName); //指定使用的properties文件名
						mySql3.setSqlId(sqlid3);//指定使用的Sql的id
						//if(typeof(document.all('fBOM').value)!="undefined"&&document.all('fBOM').value!=null&&document.all('fBOM').value!='')
						//{
							mySql3.addSubPara(document.all('fBOM').value);//指定传入的参数
						//}
					
	 			 	 var strSql3=mySql3.getString();	
	  
	  
					//	var temsql = "select cnname from lrbom where name='"
						//		+ arrResult[j][2] + "'";
						//prompt(this,temsql);
						var temResult = easyExecSql(strSql3, 1, 0);
						//alert(temResult[0][0]);
						if (temResult != null) {
							document.all('ibrmsfBOMName').value = temResult[0][0];
						} else {
							document.all('ibrmsfBOMName').value = '';
						}
					}
					//var fbomitemsql;
					
					var sqlid4="bomUpdateSql4";
					var mySql4=new SqlClass();
					mySql4.setResourceName(tResourceName); //指定使用的properties文件名
					mySql4.setSqlId(sqlid4);//指定使用的Sql的id
					mySql4.addSubPara(document.all('fBOM').value);//指定传入的参数
	 			  var strSql4=mySql4.getString();	
	 			  
				/*	if (document.all('fBOM').value == '') {
						fbomitemsql = "select name,cnname from (select * from lrbomitem)";
					} else {
						fbomitemsql = "select name,cnname from (select * from lrbomitem where bomname='"
								+ arrResult[j][2] + "')";
					}*/
					var tem1 = easyQueryVer3(strSql4, 1, 1, 1);
					if (tem1 == false) {
						document.all('fatherItem').CodeData = '';
					} else {
						document.all('fatherItem').CodeData = tem1;
					}
					
					if(document.all('fatherItem').value!='')
					{
						var sqlid5="bomUpdateSql5";
						var mySql5=new SqlClass();
						mySql5.setResourceName(tResourceName); //指定使用的properties文件名
						mySql5.setSqlId(sqlid5);//指定使用的Sql的id
						mySql5.addSubPara(document.all('fatherItem').value);//指定传入的参数
						mySql5.addSubPara(document.all('fBOM').value);//指定传入的参数
	 			  	var strSql5=mySql5.getString();	
	 			  
						/*var temsql = "select cnname from lrbomitem where name='"
								+ arrResult[j][4] + "' and bomname='"
								+ arrResult[j][2] + "'";
								*/
						var temResult = easyExecSql(strSql5, 1, 0);
						if (temResult != null) {
							document.all('fatherItemName').value = temResult[0][0];
						} else {
							document.all('fatherItemName').value = '';
						}
					}
					var localbomsql;
					if (eName == '') {
						document.all('localItem').CodeData = '';
						//localbomsql = "select name,cnname from (select * from lrbomitem where 1=2)";
					} else {
					
							var sqlid6="bomUpdateSql6";
						  var mySql6=new SqlClass();
						  mySql6.setResourceName(tResourceName); //指定使用的properties文件名
						  mySql6.setSqlId(sqlid6);//指定使用的Sql的id
					   	mySql6.addSubPara(eName);//指定传入的参数
	 			  	  var strSql6=mySql6.getString();	
	 			 
							var tem2 = easyQueryVer3(strSql6, 1, 1, 1);
							if (tem2 != false)
							{
									document.all('localItem').CodeData = tem2;
							}
					}
				
					//alert('value:'+document.all('localItem').value);
					if(document.all('localItem').value!='')
					{
						var sqlid7="bomUpdateSql7";
						var mySql7=new SqlClass();
						mySql7.setResourceName(tResourceName); //指定使用的properties文件名
						mySql7.setSqlId(sqlid7);//指定使用的Sql的id
						mySql7.addSubPara(document.all('localItem').value);//指定传入的参数
						mySql7.addSubPara(document.all('fBOM').value);//指定传入的参数
	 			 	 	var strSql7=mySql7.getString();	
	 			 
						var temResult = easyExecSql(strSql7, 1, 0);
						if (temResult != null) {
							document.all('localItemName').value = temResult[0][0];
						} else {
							document.all('localItemName').value = '';
						}
					}

				}
			}

					var sqlid8="bomUpdateSql8";
					var mySql8=new SqlClass();
					mySql8.setResourceName(tResourceName); //指定使用的properties文件名
					mySql8.setSqlId(sqlid8);//指定使用的Sql的id
					
			if (eName == '') {
				//var fbomsql = "select name,cnname from (select * from lrbom where name<>' ')";
			} else {
				//var fbomsql = "select name,cnname from (select * from lrbom where name<>'"
				//		+ eName + "')";
					mySql2.addSubPara(eName);
			}
			
			 var strSql8=mySql8.getString();	
			var tem = easyQueryVer3(strSql8, 1, 1, 1);
			if (tem == false) {
				document.all('fBOM').CodeData = '';
			} else {
				document.all('fBOM').CodeData = tem;
			}

		} catch (ex) {
				alert(ex);
		}
	}

	function initSelBox() {
		try {

		} catch (ex) {
			alert("在bomUpdateInit.jsp   InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm1(viewFlag) {
		try {
			initInpBox();
			initSelBox();
			initButton(viewFlag);
		} catch (re) {
			alert("bomUpdateInit.jsp   InitForm函数中发生异常:初始化界面错误!");
		}
	}

	function initButton(viewFlag) {
        if(viewFlag=='view'){        
           document.all("ok").style.display="none";
           document.all("ret").style.display="none"; 
           document.all("close").style.display="";          
        }else{
           document.all("ok").style.display="";
           document.all("ret").style.display=""; 
           document.all("close").style.display="none";
        }
	}
</script>
