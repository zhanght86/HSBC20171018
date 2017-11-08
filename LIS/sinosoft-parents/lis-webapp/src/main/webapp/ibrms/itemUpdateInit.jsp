
<%
	//程序名称：itemUpdateInit.jsp
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
	var tResourceNameItem = 'ibrms.itemUpdateSql';
	function initInpBox() {
		try {
			var ItemEName = document.all("ItemEName").value;
			var bomName = document.all("bomName").value;
		//	var itemsql = "select * from lrbomitem where name='" + ItemEName
		//			+ "' and bomname='" + bomName + "'";
			var sqlid1="itemUpdateSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceNameItem); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(ItemEName);//指定传入的参数
		mySql1.addSubPara(bomName);//指定传入的参数
	  var strSql=mySql1.getString();	
	  
			//prompt(this,itemsql);
			var arrResult = easyExecSql(strSql, 1, 0);
			var i = arrResult.length;
			if (arrResult == null) {
				return;
			} else {
				for (j = 0; j < i; j++) {
					document.all('ItemEName').value = arrResult[j][0];
					document.all('bomName').value = arrResult[j][1];
					document.all('CNName').value = arrResult[j][2];
					document.all('Connector').value = arrResult[j][3];
					document.all('IsHierarchical').value = arrResult[j][4];
					document.all('IsBase').value = arrResult[j][5];
					document.all('CommandType').value = arrResult[j][8];
					document.all('SourceType').value = arrResult[j][6];
					document.all('Source').value = arrResult[j][7];
					// 词条预校验的初始化
					document.all('PreCheck').value = arrResult[j][9];
						document.all('Valid').value = arrResult[j][10];
					document.all('Description').value = arrResult[j][11];
					var CommandType = arrResult[j][8];
					if (CommandType == '') {
						//temitemsql = "select code1,codename from ldcode1 where 1=2";
						document.all('PreCheck').CodeData = '';
					} else {
						//temitemsql = "select code1,codename from ldcode1 where code='"
							//	+ CommandType + "'";
								var sqlid2="itemUpdateSql2";
								var mySql2=new SqlClass();
								mySql2.setResourceName(tResourceNameItem); //指定使用的properties文件名
								mySql2.setSqlId(sqlid2);//指定使用的Sql的id
								mySql2.addSubPara(CommandType);//指定传入的参数
							  var strSql2=mySql2.getString();	
					
					//prompt(this,temitemsql);
						var tem = easyQueryVer3(strSql2, 1, 1, 1);
						if (tem == false) {
							document.all('PreCheck').CodeData = '';
						} else {
							document.all('PreCheck').CodeData = tem;
						}
				}
				if(document.all('PreCheck').value!='')
				{
					var sqlid3="itemUpdateSql3";
					var mySql3=new SqlClass();
					mySql3.setResourceName(tResourceNameItem); //指定使用的properties文件名
					mySql3.setSqlId(sqlid3);//指定使用的Sql的id
					mySql3.addSubPara(document.all('CommandType').value);//指定传入的参数
					mySql3.addSubPara(document.all('PreCheck').value);//指定传入的参数
					 var strSql3=mySql3.getString();	
					//var temsql = "select codename from ldcode1 where code='"
					//		+ arrResult[j][8] + "' and code1='"
					//		+ arrResult[j][9] + "'";
					//prompt(this,temsql);
					var temResult = easyExecSql(strSql3, 1, 0);
					if (temResult != null) {
							document.all('ibrmsPreCheckName').value = temResult[0][0];
					} else {
							document.all('ibrmsPreCheckName').value = '';
					}

				}
			}
		}

		} catch (ex) {

		}
	}

	function initSelBox() {
		try {

		} catch (ex) {
			alert("在itemUpdateInit.jsp   InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm1(viewFlag) {
		try {
			//alert('1');
			initInpBox();
			//alert('2');
			initSelBox();
			//alert('3');
			initButton(viewFlag)
		} catch (re) {
			alert("itemUpdateInit.jsp   InitForm函数中发生异常:初始化界面错误!");
		}
	}

	function initButton(viewFlag) {
		if (viewFlag == 'view') {
			document.all("ok").style.display = "none";
			document.all("ret").style.display = "none";
			document.all("close").style.display = "";			
		} else {
			document.all("ok").style.display = "";
			document.all("ret").style.display = "";
			document.all("close").style.display = "none";
		}
	}
</script>
