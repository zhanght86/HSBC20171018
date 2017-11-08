<%@include file="../i18n/language.jsp"%>
<%
	//程序名称：PDRiskParamsDefInit.jsp
	//程序功能：缴费字段控制定义
	//创建日期：2009-3-13
	//创建人  ：CM
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.RiskCode.value = "<%=request.getParameter("riskcode")%>";
		fm.DutyCode.value = "<%=request.getParameter("payplancode")%>";
		queryDutyCode();
		initMulline9Grid();
		queryMulline9Grid();
		initMulline10Grid();                                                                                                                                                                                                                                                                                                                                                                                                                   
		queryMulline10Grid(); 
		//updateDisplayState();
		fm.IsReadOnly.value = "<%=session.getAttribute("IsReadOnly")%>";
			isshowbutton();
		} catch (re) {
			myAlert("PDRiskParamsDefInit.jsp-->" + "InitForm函数中发生异常:初始化界面错误!");
		}
	}
	function updateDisplayState() {
		// rowCount:显示的字段数量
		var sql = "select count(1) from Pd_Basefield where tablecode = upper('"
				+ fm.all("tableName").value + "') and isdisplay = 1";
		var result = easyExecSql(sql, 1, 1, 1);
		var rowCount = result[0][0];
		// rowcode:下拉项对应的selectcode的数组

		for (var i = 0; i < rowCount; i++) {
			var rowcode = easyExecSql(sql, 1, 1, 1);
			sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"
					+ fm.all("tableName").value
					+ "') and isdisplay = 1 order by Pd_Basefield.Displayorder";
			var selNo = Mulline10Grid.getSelNo();
			/*if(Mulline9Grid.getRowColData(i,2) == "OTHERCODE")
			 {
				 Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,2),null,""); 		 
			 }else if(Mulline9Grid.getRowColData(i,2) == "DUTYCODE")
			 {
				 Mulline9Grid.setRowColDataCustomize(i,4,fm.DutyCode.value,null,"readonly"); 		 
			 }
			 else 
			 {
				if(selNo==0 || selNo == null)
				{
					Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");    
				}else
				{
					if(Mulline9Grid.getRowColData(i,2) == "FIELDNAME")
			 		{
				 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,3),null,"readonly"); 		 
			 		}else
			 		{ 			
			 	 		var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+fm.tableName.value+" where dutycode = '"+fm.DutyCode.value +"' and othercode = '"+fm.PayPlanCode.value+"' and fieldname = '"+Mulline10Grid.getRowColData(selNo-1,3)+"'";
			 		var tContent = easyExecSql(tDefaultValueSQL);
			 
			 		var cData = null;
			 		if(tContent!=null&&tContent[0][0]!="null")
			 		{
			 	 		cData = tContent[0][0];
			 		}
			 		Mulline9Grid.setRowColDataCustomize(i,4,cData,null,rowcode[i][1]);
			 	}
			}
			}
			 */
			if (Mulline9Grid.getRowColData(i, 2) == "OTHERCODE") {
				//Mulline9Grid.setRowColDataCustomize(i,4,document.all("PayPlanCode").value,null,"");

				Mulline9Grid.setRowColDataCustomize(i, 4, Mulline10Grid
						.getRowColData(selNo - 1, 2), null, "");
			} else if (Mulline9Grid.getRowColData(i, 2) == "DUTYCODE") {
				Mulline9Grid.setRowColDataCustomize(i, 4, document
						.all("DutyCode").value, null, "readonly");
			} else {
				var selNo = Mulline10Grid.getSelNo();
				if (selNo == 0 || selNo == null) {
					Mulline9Grid.setRowColDataCustomize(i, 4, null, null,
							rowcode[i][1], "11");
				} else {
					if (Mulline9Grid.getRowColData(i, 2) == "FIELDNAME") {
						Mulline9Grid.setRowColDataCustomize(i, 4, Mulline10Grid
								.getRowColData(selNo - 1, 3), null, "readonly");
					} else {
						//	 	 	 		var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+document.all("tableName").value+" where dutycode = '"+document.all('DutyCode').value +"' and othercode = '"+document.all('PayPlanCode').value+"' and fieldname = '"+Mulline10Grid.getRowColData(selNo-1,3)+"'";
						mySql = new SqlClass();
						mySql
								.setResourceName("productdef.PDRiskParamsDefInputSql"); //指定使用的properties文件名
						mySql.setSqlId("PDRiskParamsDefInputSql9");//指定使用的Sql的id
						mySql.addSubPara(Mulline9Grid.getRowColData(i, 2));//指定传入的参数
						mySql.addSubPara(document.all("tableName").value);//指定传入的参数
						mySql.addSubPara(document.all('DutyCode').value);//指定传入的参数
						mySql.addSubPara(document.all('PayPlanCode').value);//指定传入的参数
						mySql.addSubPara(Mulline10Grid.getRowColData(selNo - 1,
								3));//指定传入的参数
						var tDefaultValueSQL = mySql.getString();
						var tContent = easyExecSql(tDefaultValueSQL);

						var cData = null;
						if (tContent != null && tContent[0][0] != "null") {
							cData = tContent[0][0];
						}
						Mulline9Grid.setRowColDataCustomize(i, 4, cData, null,
								rowcode[i][1]);
					}
				}
			}

		}
	}

	function initMulline9Grid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号";
			iArray[0][1] = "30px";
			iArray[0][2] = 100;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "属性名称";
			iArray[1][1] = "60px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "属性代码";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 3;

			iArray[3] = new Array();
			iArray[3][0] = "属性数据类型";
			iArray[3][1] = "90px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "属性值";
			iArray[4][1] = "79px";
			iArray[4][2] = 100;
			iArray[4][3] = 2;

			iArray[5] = new Array();
			iArray[5][0] = "官方字段描述";
			iArray[5][1] = "90px";
			iArray[5][2] = 100;
			iArray[5][3] = 0;

			Mulline9Grid = new MulLineEnter("fm", "Mulline9Grid");

			Mulline9Grid.mulLineCount = 0;
			Mulline9Grid.displayTitle = 1;
			Mulline9Grid.canSel = 0;
			Mulline9Grid.canChk = 0;
			Mulline9Grid.hiddenPlus = 1;
			Mulline9Grid.hiddenSubtraction = 1;

			Mulline9Grid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
	function initMulline10Grid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号";
			iArray[0][1] = "30px";
			iArray[0][2] = 100;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "责任编码";
			iArray[1][1] = "60px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "其他编码";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "字段名称";
			iArray[3][1] = "60px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "录入标志";
			iArray[4][1] = "60px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "控制类型";
			iArray[5][1] = "60px";
			iArray[5][2] = 100;
			iArray[5][3] = 0;

			Mulline10Grid = new MulLineEnter("fm", "Mulline10Grid");

			Mulline10Grid.mulLineCount = 0;
			Mulline10Grid.displayTitle = 1;
			Mulline10Grid.canSel = 1;
			Mulline10Grid.canChk = 0;
			Mulline10Grid.hiddenPlus = 1;
			Mulline10Grid.hiddenSubtraction = 1;
			Mulline10Grid.selBoxEventFuncName = "updateDisplayState";

			Mulline10Grid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
