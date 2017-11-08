<%
//程序名称：WFEdorNoscanAppInit.jsp
//程序功能：保全工作流-保全无扫描申请
//创建日期：2005-04-27 15:13:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//            XinYQ     2006-11-08   格式调整
//
%>


<script language="JavaScript">

//var SelfGrid;

function initForm()
{
    try
    {
       initSearchPool();
        //initSelfGrid();          //初始化我的任务队列
       	//easyQueryClickSelf();    //查询我的任务队列
    }
    catch (ex)
    {
        alert("在 WFEdorNoscanAppInit.jsp --> initForm 函数中发生异常:初始化界面错误111！ ");
    }
}
function initSearchPool(){
   var sql1 = "";
   var sql2 = "";
   if(_DBT==_DBO){
	   sql1 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) ";
   }else if(_DBT==_DBM){
	   sql1 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) ";
   }
   if(_DBT==_DBO){
	   sql2 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1  and rownum = 1) and workdateflag = 'Y') ";
   }else if(_DBT==_DBM){
	   sql2 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1  and rownum = 1) and workdateflag = 'Y') ";
   }
	var config = {
			activityId : '0000000002',
			//functionId : "10020002",
			//operator : operator,
			public : {
				show : false
			},
			private : {
				query : {
					queryButton : {
						"1" : {
							title : "申  请",
							func : "applyMission"
						}
					},
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
								  colName:"MissionProp2",
								  refercode1:"station",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  }, 
								  
							result0  : {title : " 保全受理号       ",style : 1,colNo : 1},  
							result1  : {title : " 管理机构         ",style : 3},  
							result2  : {title : " 投保人           ",style : 3},            
							result3  : {title : " 交费对应日       ",style : 3},            
							result4  : {title : " 客户/保单号         ",style : 1,colNo : 3},            
							result5  : {title : " 申请号码类型     ",style : 3},  
							result6  : {title : " 申请人       ",style : 1,colNo : 4},  
							result7  : {title : " 申请方式         ",style : 3},  
							result8  : {title : " 申请日期         ",style : 3},            
							result9  : {title : " 默认创建人       ",style : 3},           
							result10  : {title : " 重要资料变更标记       ",style : 3}           
						}
					}
				},
				resultTitle : "我的任务",
				result : {
					selBoxEventFuncName : "HighlightByRow",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and missionprop18 is null and missionid not in (select missionid from lwmission  where activityid in"+
              		 		  " (select activityid "+
                 			  " from lwactivity "+
                 			  " where functionid = '10020000')) and trim(defaultoperator) ='"+operator+"'"// and OtherNo like'"+ + comcode+ "%'"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "号码类型",
								style : 1,
								colNo : 3,
								width : "70px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "申请方式",
								style : 1,
								colNo : 6 ,
								width : "85px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "录入日期",
								style : 1,
								colNo : 9 ,
								width : "100px",
								colName : "makedate "
							},
							newCol3 : {
								title : "管理机构",
								style : 1,
								colNo : 7,
								width : "65px",
								colName : "(select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop2))",
								rename : "p_station"
							},
							newCol4 : {
								title : "录入员",
								style : 1,
								colNo : 8 ,
								width : "60px",
								colName : "createoperator "
							},
							newCol5 : {
								title : "保全操作",
								style : 1,
								colNo : 10 ,
								width : "80px",
								colName : "case (select count(0) from lbmission where activityid in (select activityid from lwactivity "+
										  "where functionid = '10020005') and missionid = t.missionid) when 0 then '保全申请' "+
         								  " else ' 审批修改 ' end ",
         					    rename : "bq_optype"
							},
							newCol6 : {
								title : "受理日期",
								style : 1,
								colNo : 11 ,
								width : "100px",
								colName : sql1,
								rename : "edor_appdate"
							},
							
							newCol7 : {
								title : "超过日期",
								style : 1,
								colNo : 12 ,
								width : "50px",
								colName : sql2,
								rename : "pass_day"
							},
							
							newCol8 : {
								title : "条件",
								style : 3,
								colNo : 13 ,
								colName : "missionprop18"
							},
							result0  : {title : " 保全受理号       ",style : 1,width : "145px",colNo : 1},  
							result1  : {title : " 管理机构         ",style : 3},  
							result2  : {title : " 投保人           ",style : 1,width : "60px",colNo : 4},            
							result3  : {title : " 交费对应日       ",style : 1,width : "100px",colNo : 5},            
							result4  : {title : " 客户/保单号         ",style : 1,width : "145px",colNo : 2},            
							result5 : {title : "号码类型", style : 3 }, 
							result6  : {title : " 申请人       ",style : 3},  
							result7 : {title : "申请方式 ", style : 3 },  
							result8  : {title : " 申请日期         ",style : 3,colName : "MissionProp6",rename : "EdorAppDate"},            
							result9  : {title : " 默认创建人       ",style : 3,colName : "DefaultOperator"},
							result10  : {title : " 重要资料变更标记       ",style : 3,colName: "MissionProp18",rename : "ICFlag"}          
						}
					}
				}
			}
	};
	jQuery("#NoScanAppInputPool").workpool(config);
}

/*
function initSelfGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保全受理号";
        iArray[1][1]="145px";
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="客户/保单号";
        iArray[2][1]="145px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="号码类型";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="投保人";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="下次交费对应日";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=8;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="申请方式";
        iArray[6][1]="85px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="管理机构";
        iArray[7][1]="65px";
        iArray[7][2]=100;
        iArray[7][3]=2;
        iArray[7][4]="currency";

        iArray[8]=new Array();
        iArray[8][0]="录入员";
        iArray[8][1]="60px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="录入日期";
        iArray[9][1]="100px";
        iArray[9][2]=100;
        iArray[9][3]=8;
        iArray[9][21]=3;

        iArray[10]=new Array();
        iArray[10][0]="工作流任务号";
        iArray[10][1]="0px";
        iArray[10][2]=0;
        iArray[10][3]=3;

        iArray[11]=new Array();
        iArray[11][0]="工作流子任务号";
        iArray[11][1]="0px";
        iArray[11][2]=0;
        iArray[11][3]=3;

        iArray[12]=new Array();
        iArray[12][0]="工作流活动ID";
        iArray[12][1]="0px";
        iArray[12][2]=0;
        iArray[12][3]=3;

        iArray[13]=new Array();
        iArray[13][0]="默认创建人";
        iArray[13][1]="0px";
        iArray[13][2]=0;
        iArray[13][3]=3;
        
        iArray[14]=new Array();
        iArray[14][0]="保全操作";
        iArray[14][1]="80px";
        iArray[14][2]=0;
        iArray[14][3]=0;
        
        iArray[15]=new Array();
        iArray[15][0]="受理日期";
        iArray[15][1]="100px";
        iArray[15][2]=0;
        iArray[15][3]=8;
        
        iArray[16]=new Array();
        iArray[16][0]="超过日期";
        iArray[16][1]="50px";
        iArray[16][2]=0;
        iArray[16][3]=0;
        
        SelfGrid = new MulLineEnter("fm", "SelfGrid");
        //这些属性必须在loadMulLine前
        SelfGrid.mulLineCount = 0;
        SelfGrid.displayTitle = 1;
        SelfGrid.locked = 1;
        SelfGrid.canSel = 1;
        SelfGrid.canChk = 0;
        SelfGrid.selBoxEventFuncName = "HighlightByRow";
        SelfGrid.hiddenPlus = 1;
        SelfGrid.hiddenSubtraction = 1;
        SelfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert("在 WFEdorNoscanAppInit.jsp --> initSelfGrid 函数中发生异常:初始化界面错误！ ");
    }
}
*/
</script>
