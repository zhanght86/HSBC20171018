<%
//程序名称：WFEdorCancelInit.jsp
//程序功能：保全工作流-保全撤销
//创建日期：2005-04-30 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//
%>

<script language="JavaScript">

function initForm()
{
  try
  {
  	initCancelPool();
   // initAllGrid();
   // initSelfGrid();
   // querySelfGrid();
  }
  catch(re)
  {
    alert("WFEdorCancelInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initCancelPool(){
	var sql1 = "";
	var sql2 = "";
	var sql3 = "";
	var sql4 = "";
	var sql5 = "";
	var sql6 = "";
	if(_DBT==_DBO){
		sql1 = " and defaultoperator is null  and createoperator = '"  + operator + "' and manageCom like '"  + manageCom + "%%'"+
		  "  and not exists (select ean, es from (select EdorAcceptNo ean, EdorState es from LPEdorApp) where 1 = 1 and ean = EdorAcceptNo and es in ('4', '8', '9'))" +
			  " order by (select edorappdate "+
			  " from lpedoritem p where p.EdorAcceptNo = EdorAcceptNo and rownum = 1), makedate, maketime ";
	}else if(_DBT==_DBM){
		sql1 = " and defaultoperator is null  and createoperator = '"  + operator + "' and manageCom like '"  + manageCom + "%%'"+
		  "  and not exists (select ean, es from (select EdorAcceptNo ean, EdorState es from LPEdorApp) l where 1 = 1 and ean = EdorAcceptNo and es in ('4', '8', '9'))" +
			  " order by (select edorappdate "+
			  " from lpedoritem p where p.EdorAcceptNo = EdorAcceptNo limit 0,1), makedate, maketime ";
	}
	if(_DBT==_DBO){
		sql2 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '''' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1)";
	}else if(_DBT==_DBM){
		sql2 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '''' end) from lpedoritem where edoracceptno = t.missionprop1 limit 0,1)";
	}
	if(_DBT==_DBO){
		sql3 = "(select count(1) from ldcalendar where commondate > (select edorappdate "+
			  " from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y')";
	}else if(_DBT==_DBM){
		sql3 = "(select count(1) from ldcalendar where commondate > (select edorappdate "+
			  " from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) and workdateflag = 'Y')";
	}
	if(_DBT==_DBO){
		sql4 = " and defaultoperator ='" + operator + "' "+
		  " and (exists (select 1 from lpedorapp b where b.edoracceptno = edoracceptno and b.othernotype = '1' "+
		  " and (exists (select 1 from lpconttempinfo c where c.icedoracceptno = edoracceptno and c.edoracceptno = c.icedoracceptno) "+ 
		  "  or not exists (select 1 from lpconttempinfo c where c.edoracceptno = edoracceptno))) "+
		  " or exists (select 1 from lpedorapp b where b.edoracceptno = edoracceptno and b.othernotype = '3')) "+
			  " order by (select edorappdate  from lpedoritem p where p.EdorAcceptNo = EdorAcceptNo and rownum = 1), makedate, maketime ";
	}else if(_DBT==_DBM){
		sql4 = " and defaultoperator ='" + operator + "' "+
		  " and (exists (select 1 from lpedorapp b where b.edoracceptno = edoracceptno and b.othernotype = '1' "+
		  " and (exists (select 1 from lpconttempinfo c where c.icedoracceptno = edoracceptno and c.edoracceptno = c.icedoracceptno) "+ 
		  "  or not exists (select 1 from lpconttempinfo c where c.edoracceptno = edoracceptno))) "+
		  " or exists (select 1 from lpedorapp b where b.edoracceptno = edoracceptno and b.othernotype = '3')) "+
			  " order by (select edorappdate  from lpedoritem p where p.EdorAcceptNo = EdorAcceptNo limit 0,1), makedate, maketime ";
	}
	if(_DBT==_DBO){
		sql5 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '''' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1)";
	}else if(_DBT==_DBM){
		sql5 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '''' end) from lpedoritem where edoracceptno = t.missionprop1 limit 0,1)";
	}
	if(_DBT==_DBO){
		sql6 = "(select count(1) from ldcalendar where commondate > (select edorappdate "+
			  " from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y')";
	}else if(_DBT==_DBM){
		sql6 = "(select count(1) from ldcalendar where commondate > (select edorappdate "+
			  " from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) and workdateflag = 'Y')";
	}
	var config = {
			functionId : "10020006",
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"号码类型",//列的标题
								  style : 2,
								  colNo : 2,
								  maxLength:10,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"edornotype",
								  colName:"MissionProp3",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							 newcol1:{ 
								  title:"申请方式",//列的标题
								  style : 2,
								  colNo : 5,
								  maxLength:10,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"edorapptype",
								  colName:"MissionProp5",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							newcol2:{ 
								  title:"录入日期",//列的标题
								  style : 8,
								  colNo : 6,
								  colName:"makedate"
								  }, 
							newcol3:{ 
								  title:"管理机构",//列的标题
								  colNo : 7,
								  style : 2,
								  colName:"MissionProp7",
								  refercode1:"station",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  }, 
							result0  : {title : " 保全受理号       ",style : 1,colNo : 1},  
							result1  : {title : " 客户/保单号         ",style : 1,colNo : 3},            
							result2  : {title : " 申请号码类型     ",style : 3},  
							result3 : {title : " 申请人       ",style : 1,colNo : 4},  
							result4  : {title : " 申请方式         ",style : 3}, 
							result5  : {title : " 申请日期         ",style : 3}, 
							result6  : {title : " 管理机构         ",style : 3},  
							result7  : {title : " 批改状态         ",style : 3},  
							result8  : {title : " 投保人           ",style : 3},            
							result9  : {title : " 交费对应日       ",style : 3}           
						}
					}
				},
				resultTitle : "共享工作池",
				result : {
					selBoxEventFuncName : "HighlightAllRow",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : sql1
					},
					mulLineCount : 5,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "号码类型",
								style : 0,
								colNo : 3,
								width : "70px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "申请方式",
								style : 0,
								colNo : 5 ,
								width : "70px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "录入日期",
								style : 0,
								colNo : 8 ,
								width : "70px",
								colName : "makedate "
							},
							newCol3 : {
								title : "受理日期",
								style : 0,
								colNo : 9 ,
								width : "70px",
								colName : sql2,
								rename : "edor_appdate"
							},
							newCol4 : {
								title : "录入员",
								style : 0,
								colNo : 7 ,
								width : "70px",
								colName : "createoperator "
							},
							newCol5 : {
								title : "操作员",
								style : 3,
								colName : "defaultoperator "
							},
							newCol6 : {
								title : "超过日期",
								style : 0,
								colNo : 10 ,
								width : "50px",
								colName : sql3,
								rename : "out_day"
							},
							newCol7 : {
								title : "录入日期",
								style : 3,
								colName : "maketime "
							},
							result0  : {title : " 保全受理号       ",width : "145px", style : 0,colNo : 1},  
							result1  : {title : " 客户/保单号         ",width : "120px", style : 0,colNo : 2},            
							result2  : {title : " 申请号码类型     ",style : 3},  
							result3 : {title : " 申请人       ",style : 3},  
							result4  : {title : " 申请方式         ",style : 3}, 
							result5  : {title : " 申请日期         ",style : 3}, 
							result6  : {title : " 管理机构         ",width : "90px", style : 0,colNo : 6 },  
							result7  : {title : " 批改状态         ",style : 3},  
							result8  : {title : " 投保人           ",width : "70px",  style : 0,colNo : 4},            
							result9  : {title : " 交费对应日       ",style : 3}            
						}
					}
				}	
			},
			private : {
				query :{
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"号码类型",//列的标题
								  style : 2,
								  colNo : 2,
								  maxLength:10,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"edornotype",
								  colName:"MissionProp3",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							 newcol1:{ 
								  title:"申请方式",//列的标题
								  style : 2,
								  colNo : 5,
								  maxLength:10,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"edorapptype",
								  colName:"MissionProp5",
								   addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							newcol2:{ 
								  title:"录入日期",//列的标题
								  style : 8,
								  colNo : 6,
								  colName:"makedate"
								  }, 
							newcol3:{ 
								  title:"管理机构",//列的标题
								  colNo : 7,
								  style : 2,
								  colName:"MissionProp7",
								  refercode1:"station",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  }, 
							result0  : {title : " 保全受理号       ",style : 1,colNo : 1},  
							result1  : {title : " 客户/保单号         ",style : 1,colNo : 3},            
							result2  : {title : " 申请号码类型     ",style : 3},  
							result3 : {title : " 申请人       ",style : 1,colNo : 4},  
							result4  : {title : " 申请方式         ",style : 3}, 
							result5  : {title : " 申请日期         ",style : 3}, 
							result6  : {title : " 管理机构         ",style : 3},  
							result7  : {title : " 批改状态         ",style : 3},  
							result8  : {title : " 投保人           ",style : 3},            
							result9  : {title : " 交费对应日       ",style : 3}           
						}
					}
				},
				resultTitle : "我的任务",
				result : {
					selBoxEventFuncName : "HighlightSelfRow",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : sql4
					},
					mulLineCount : 5,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "号码类型",
								style : 0,
								colNo : 3,
								width : "70px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "申请方式",
								style : 0,
								colNo : 5 ,
								width : "70px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "录入日期",
								style : 0,
								colNo : 8 ,
								width : "70px",
								colName : "makedate "
							},
							newCol3 : {
								title : "受理日期",
								style : 0,
								colNo : 9 ,
								width : "70px",
								colName : sql5,
								rename : "edor_appdate"
							},
							newCol4 : {
								title : "录入员",
								style : 0,
								colNo : 7 ,
								width : "70px",
								colName : "createoperator "
							},
							newCol5 : {
								title : "操作员",
								style : 3,
								colName : "defaultoperator "
							},
							newCol6 : {
								title : "超过日期",
								style : 0,
								colNo : 10 ,
								width : "50px",
								colName : sql6,
								rename : "out_day"
							},
							newCol7 : {
								title : "录入日期",
								style : 3,
								colName : "maketime "
							},
							result0  : {title : " 保全受理号       ",width : "145px", style : 0,colNo : 1},  
							result1  : {title : " 客户/保单号         ",width : "120px", style : 0,colNo : 2},            
							result2  : {title : " 申请号码类型     ",style : 3},  
							result3 : {title : " 申请人       ",style : 3},  
							result4  : {title : " 申请方式         ",style : 3}, 
							result5  : {title : " 申请日期         ",style : 3}, 
							result6  : {title : " 管理机构         ",width : "90px", style : 0,colNo : 6 },  
							result7  : {title : " 批改状态         ",style : 3},  
							result8  : {title : " 投保人           ",width : "70px",  style : 0,colNo : 4},            
							result9  : {title : " 交费对应日       ",style : 3}            
						}
					}
				}	
			},
			midContent : { 
			id : 'MidContent',
			show : true,
			<!--html : '<INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="applyMission()">'-->
			html : '<a id="riskbutton" href="javascript:void(0);" class="button" onClick="applyMission();">申    请</a>'
			}
	};
	jQuery("#CancelInputPool").workpool(config);
	jQuery("#privateSearch").click();
}

/*
function initAllGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=30;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全受理号";
      iArray[1][1]="145px";
      iArray[1][2]=170;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="客户/保单号";
      iArray[2][1]="120px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="号码类型";
      iArray[3][1]="70px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="投保人";
      iArray[4][1]="70px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="申请方式";
      iArray[5][1]="70px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="管理机构";
      iArray[6][1]="90px";
      iArray[6][2]=100;
      iArray[6][3]=2;
      iArray[6][4]="Station";

      iArray[7]=new Array();
      iArray[7][0]="录入员";
      iArray[7][1]="70px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="录入日期";
      iArray[8][1]="70px";
      iArray[8][2]=100;
      iArray[8][3]=8;
      iArray[8][21]=3;

      iArray[9]=new Array();
      iArray[9][0]="工作流任务号";
      iArray[9][1]="0px";
      iArray[9][2]=0;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="工作流子任务号";
      iArray[10][1]="0px";
      iArray[10][2]=0;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="工作流活动Id";
      iArray[11][1]="0px";
      iArray[11][2]=0;
      iArray[11][3]=3;
      
      iArray[12]=new Array();
      iArray[12][0]="受理日期";
      iArray[12][1]="70px";
      iArray[12][2]=0;
      iArray[12][3]=8;
      
      iArray[13]=new Array();
      iArray[13][0]="超过日期";
      iArray[13][1]="50px";
      iArray[13][2]=0;
      iArray[13][3]=0;

      AllGrid = new MulLineEnter("fm", "AllGrid");
      //这些属性必须在loadMulLine前
      AllGrid.mulLineCount = 5;
      AllGrid.displayTitle = 1;
      AllGrid.locked = 1;
      AllGrid.canSel = 1;
      AllGrid.canChk = 0;
      AllGrid.hiddenPlus = 1;
      AllGrid.selBoxEventFuncName = "HighlightAllRow";
      AllGrid.hiddenSubtraction = 1;
      AllGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

function initSelfGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=30;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全受理号";
      iArray[1][1]="145px";
      iArray[1][2]=170;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="客户/保单号";
      iArray[2][1]="120px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="号码类型";
      iArray[3][1]="70px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="投保人";
      iArray[4][1]="70px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="申请方式";
      iArray[5][1]="70px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="管理机构";
      iArray[6][1]="90px";
      iArray[6][2]=100;
      iArray[6][3]=2;
      iArray[6][4]="Station";

      iArray[7]=new Array();
      iArray[7][0]="录入员";
      iArray[7][1]="70px";
      iArray[7][2]=200;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="录入日期";
      iArray[8][1]="100px";
      iArray[8][2]=100;
      iArray[8][3]=8;
      iArray[8][21]=3;

      iArray[9]=new Array();
      iArray[9][0]="工作流任务号";
      iArray[9][1]="0px";
      iArray[9][2]=0;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="工作流子任务号";
      iArray[10][1]="0px";
      iArray[10][2]=0;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="工作流活动Id";
      iArray[11][1]="0px";
      iArray[11][2]=0;
      iArray[11][3]=3;
      
      iArray[12]=new Array();
      iArray[12][0]="受理日期";
      iArray[12][1]="100px";
      iArray[12][2]=0;
      iArray[12][3]=8;
      
      iArray[13]=new Array();
      iArray[13][0]="超过日期";
      iArray[13][1]="50px";
      iArray[13][2]=0;
      iArray[13][3]=0;

      SelfGrid = new MulLineEnter( "fm" , "SelfGrid" );
      //这些属性必须在loadMulLine前
      SelfGrid.mulLineCount = 5;
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;
      SelfGrid.selBoxEventFuncName = "HighlightSelfRow";
      SelfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
</script>
