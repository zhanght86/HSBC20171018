<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
  }
  catch(ex)
  {
    alert("在BQUWConfirmPoolInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                            

function initForm()
{
  try
  {
    initInpBox();
    initBQUWConfirmPool();
    //initSelfGrpGrid();
    //initGrpGrid();
   // easyQueryClickSelf();
  }
  catch(re)
  {
    alert("BQUWConfirmPoolInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initBQUWConfirmPool(){
	var config = {
			functionId : "10020330",
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"业务类别",//列的标题
								  style : 2,
								  colNo : 4,
								  maxLength:10,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"edorcode",
								  colName:"MissionProp5",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							newcol1:{ 
								  title:"管理机构",//列的标题
								  colNo : 1,
								  style : 2,
								  colName:"MissionProp3",
								  refercode1:"station",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							result0  : {title : " 打印流水号      ",style : 3},  
							result1  : {title : " 保单号码      ",style : 1,colNo : 2},            
							result2 : {title : "管理机构", style : 3 }, 
							result3  : {title : " 批单号       ",style : 3,colNo : 3},  
							result4 : {title : "业务类别", style : 3 }, 
							result5  : {title : " 保全受理号        ", style : 3}         
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
						"5" : " and (defaultoperator is null OR defaultoperator = '') order by ContNo,PrtSeq" 
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
					
							newCol0 : {
								title : "受理日期",
								style : 0,
								width : "60px", 
								colNo : 7 ,
								colName : "  (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop6) ",
								rename : "edor_appdate" 
							},
							newCol1 : {
								title : "超过日期",
								colNo : 8 ,
								width : "60px", 
								style : 0,
								colName : " (select count(1) from ldcalendar where commondate > (select edorappdate "+
                  					 	 " from lpedoritem  where edoracceptno = t.missionprop6) and workdateflag = 'Y') ",
								rename : "out_day" 
							},
							newCol2 : {
								title : "默认操作员",
								style : 3,
								colName : "defaultoperator "
							},
							result0  : {title : " 打印流水号      ",width : "120px", style : 0,colNo : 4},  
							result1  : {title : " 保单号     ",width : "120px", style : 0,colNo : 3},            
							result2 : {title : "管理机构",width : "60px",  style : 0, colNo : 5}, 
							result3  : {title : " 批单号       ",width : "120px", style : 0,colNo : 6},  
							result4 : {title : "批改类型", width : "60px", style : 0, colNo : 2}, 
							result5  : {title : " 保全受理号        ",width : "120px", style : 0,colNo : 1}
						}
					}
				}	
			},
			private : {
				query :{
					show : false
				},
				resultTitle : "个人工作池",
				result : {
					selBoxEventFuncName : "InitGoToInput",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and defaultoperator ='" + operator + "'  and ManageCom like '" + comcode + "%%'" +
							 " order by ContNo,PrtSeq"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
					
							newCol0 : {
								title : "受理日期",
								style : 0,
								width : "60px", 
								colNo : 7 ,
								colName : "  (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop6) ",
								rename : "edor_appdate" 
							},
							newCol1 : {
								title : "超过日期",
								colNo : 8 ,
								width : "60px", 
								style : 0,
								colName : " (select count(1) from ldcalendar where commondate > (select edorappdate "+
                  					 	 " from lpedoritem  where edoracceptno = t.missionprop6) and workdateflag = 'Y') ",
								rename : "out_day" 
							},
							newCol2 : {
								title : "默认操作员",
								style : 3,
								colName : "defaultoperator "
							},
							result0  : {title : " 打印流水号      ",width : "120px", style : 0,colNo : 4},  
							result1  : {title : " 保单号     ",width : "120px", style : 0,colNo : 3},            
							result2 : {title : "管理机构",width : "60px",  style : 0, colNo : 5}, 
							result3  : {title : " 批单号       ",width : "120px", style : 0,colNo : 6},  
							result4 : {title : "批改类型", width : "60px", style : 0, colNo : 2}, 
							result5  : {title : " 保全受理号        ",width : "120px", style : 0,colNo : 1}
						}
					}
				}	
			},
			midContent : { 
			id : 'MidContent',
			show : true,
			html : '<INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW()">'
			}
	};
	jQuery("#BQUWConfirmPool").workpool(config);
	jQuery("#privateSearch").click();
}
// 保单信息列表的初始化
/*
function initGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全受理号";         	//列名
      iArray[1][1]="120px";            	//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批改类型";         	//列名
      iArray[2][1]="60px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保单号";         	//列名
      iArray[3][1]="120px";            	//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[4]=new Array();
      iArray[4][0]="打印流水号";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[5]=new Array();
      iArray[5][0]="管理机构";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许    

      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";      //列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";    //列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";      //列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[9]=new Array();
      iArray[9][0]="批单号";         	//列名
      iArray[9][1]="120px";            	//列宽
      iArray[9][2]=170;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[10]=new Array();
      iArray[10][0]="受理日期";      //列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=8;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[11]=new Array();
      iArray[11][0]="超过日期";         	//列名
      iArray[11][1]="60px";            	//列宽
      iArray[11][2]=170;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount =0;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.selBoxEventFuncName = "HighlightAllRow";
      GrpGrid.hiddenSubtraction = 1;        
      GrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initSelfGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全受理号";         	//列名
      iArray[1][1]="120px";            	//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批改类型";         	//列名
      iArray[2][1]="80px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保单号";         	//列名
      iArray[3][1]="80px";            	//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[4]=new Array();
      iArray[4][0]="打印流水号";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[5]=new Array();
      iArray[5][0]="管理机构";         		//列名
      iArray[5][1]="140px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许    

      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";      //列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";    //列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";      //列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[9]=new Array();
      iArray[9][0]="批单号";         	//列名
      iArray[9][1]="120px";            	//列宽
      iArray[9][2]=170;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="受理日期";      //列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=8;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[11]=new Array();
      iArray[11][0]="超过日期";         	//列名
      iArray[11][1]="60px";            	//列宽
      iArray[11][2]=170;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //这些属性必须在loadMulLine前
      SelfGrpGrid.mulLineCount =0;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 1;
      SelfGrpGrid.canChk = 0;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;   
      SelfGrpGrid.selBoxEventFuncName = "InitGoToInput";     
      SelfGrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
*/
</script>
