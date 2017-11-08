<% 
//程序名称：WFEdorConfirmInit.jsp
//程序功能：保全工作流-保全确认
//创建日期：2005-04-30 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

function initInpBox()
{ 

  try
  {                                   
	//查询条件置空
    document.all('EdorAcceptNo').value = '';
    document.all('OtherNo').value = '';
    document.all('OtherNoType').value = '';
    
    document.all('EdorAppName').value = '';
    document.all('AppType').value = '';
    document.all('ManageCom').value = '';
    
    document.all('Operator').value="<%=tGI.Operator%>";
       
  }
  catch(ex)
  {
    alert("WFEdorAppInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                            

function initForm()
{
  try
  {    
  initConfirmInputPool();
    //initInpBox();
    //initAllGrid();  //初始化共享工作池
    //initSelfGrid(); //初始化我的任务队列
   // easyQueryClickSelf();  //查询我的任务队列
    //checkPrintNotice();
  }
  catch(re)
  {
    alert("WFEdorAppInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initConfirmInputPool(){
	var config = {
			functionId : "10020007",
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
							result5  : {title : " 管理机构         ",style : 3},  
							result6  : {title : " 投保人           ",style : 3},            
							result7  : {title : " 交费对应日       ",style : 3}           
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
						"5" : " and defaultoperator is null order by edor_appdate, MakeDate, MakeTime "
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
								width : "60px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "申请方式",
								style : 0,
								colNo : 5 ,
								width : "60px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "录入日期",
								style : 0,
								colNo : 8 ,
								width : "80px",
								colName : "makedate "
							},
							newCol3 : {
								title : "管理机构",
								style : 0,
								colNo : 6 ,
								width : "60px",
								colName : "(select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7))",
								rename : "p_station"
							},
							newCol4 : {
								title : "录入员",
								style : 0,
								colNo : 7 ,
								width : "60px",
								colName : "createoperator "
							},
							newCol5 : {
								title : "操作人",
								colNo : 11 ,
								width : "50px ",
								style : 0,
								colName : " (select  b.operator from  lpedorapp b where t.missionprop1 = b.edoracceptno )",
								rename : "uw_operator"
							},
							newCol6 : {
								title : "核保结论",
								colNo : 9 ,
								width : "70px",
								style : 0,
								colName : "(select d.codename from ldcode d,lpedormain c where t.missionprop1 = c.EdorAcceptNo and  d.code = c.uwstate and d.codetype = 'edorcontuw' )",
								rename : "uw_state_name" 
							},
							newCol7 : {
								title : "客户意见",
								colNo : 10 ,
								width : "60px",
								style : 0,
								colName : " (select case count(1) when 0 then '同意' else '不同意' end  from lpcuwmaster d4,lpedorapp b  where t.missionprop1 = b.edoracceptno and d4.edoracceptno = b.edoracceptno and customeridea = '1') ",
								rename : "cus_opinion" 
							},
							newCol8 : {
								title : "受理日期",
								style : 0,
								width : "60px", 
								colNo : 12 ,
								colName : " (select c.edorappdate from  lpedormain c where t.missionprop1 = c.EdorAcceptNo ) ",
								rename : "edor_appdate" 
							},
							newCol9 : {
								title : "超过日期",
								colNo : 13 ,
								width : "50px", 
								style : 0,
								colName : "(select count(1) from ldcalendar l ,lpedormain c where t.missionprop1 = c.EdorAcceptNo and l.commondate > c.edorappdate and workdateflag = 'Y') ",
								rename : "out_day" 
							},
							newCol10 : {
								title : "核保结论代码",
								style : 3,
								colName : " (select c.uwstate from  lpedormain c where t.missionprop1 = c.EdorAcceptNo) ",
								rename : "uw_state" 
							},
							newCol11 : {
								title : "录入时间",
								style : 3,
								colName : "maketime "
							},
							newCol12 : {
								title : "默认操作员",
								style : 3,
								colName : "defaultoperator "
							},
							result0  : {title : " 保全受理号       ",style : 0,width : "150px",colNo : 1},  
							result1  : {title : " 客户/保单号         ",style : 0,width : "150px",colNo : 2},            
							result2 : {title : "号码类型", style : 3 }, 
							result3  : {title : " 申请人       ",style : 3},  
							result4 : {title : "申请方式 ", style : 3 }, 
							result5  : {title : " 管理机构         ",style : 3},  
							result6  : {title : " 投保人           ",style : 0,width : "60px",colNo : 4},            
							result7  : {title : " 交费对应日       ",style : 3}           
						}
					}
				}	
			},
			private : {
				query :{
					show : false
				},
				resultTitle : "我的任务",
				result : {
					selBoxEventFuncName : "checkPayNotice",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and defaultoperator ='" + operator + "' order by MakeDate, MakeTime "
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
								width : "60px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "申请方式",
								style : 0,
								colNo : 5 ,
								width : "60px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "录入日期",
								style : 0,
								colNo : 8 ,
								width : "80px",
								colName : "makedate "
							},
							newCol3 : {
								title : "管理机构",
								style : 0,
								colNo : 6 ,
								width : "60px",
								colName : "(select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7))",
								rename : "p_station"
							},
							newCol4 : {
								title : "录入员",
								style : 0,
								colNo : 7 ,
								width : "60px",
								colName : "createoperator "
							},
							newCol5 : {
								title : "核保结论",
								colNo : 9 ,
								width : "70px",
								style : 0,
								colName : "(select d.codename from ldcode d,lpedormain c where t.missionprop1 = c.EdorAcceptNo and  d.code = c.uwstate and d.codetype = 'edorcontuw' )",
								rename : "uw_state_name" 
							},
							newCol6 : {
								title : "客户意见",
								colNo : 10 ,
								width : "60px",
								style : 0,
								colName : " (select case count(1) when 0 then '同意' else '不同意' end  from lpcuwmaster d4,lpedorapp b  where t.missionprop1 = b.edoracceptno and d4.edoracceptno = b.edoracceptno and customeridea = '1') ",
								rename : "cus_opinion" 
							},
							newCol7 : {
								title : "受理日期",
								style : 0,
								width : "60px", 
								colNo : 12 ,
								colName : " (select c.edorappdate from  lpedormain c where t.missionprop1 = c.EdorAcceptNo ) ",
								rename : "edor_appdate" 
							},
							newCol8 : {
								title : "超过日期",
								colNo : 13 ,
								width : "50px", 
								style : 0,
								colName : "(select count(1) from ldcalendar l ,lpedormain c where t.missionprop1 = c.EdorAcceptNo and l.commondate > c.edorappdate and workdateflag = 'Y') ",
								rename : "out_day" 
							},
							newCol9 : {
								title : "核保结论代码",
								style : 3,
								colName : " (select c.uwstate from  lpedormain c where t.missionprop1 = c.EdorAcceptNo) ",
								rename : "uw_state" 
							},
							newCol10 : {
								title : "录入时间",
								style : 3,
								colName : "maketime "
							},
							newCol11 : {
								title : "默认操作员",
								style : 3,
								colName : "defaultoperator "
							},
							result0  : {title : " 保全受理号       ",style : 0,width : "150px",colNo : 1},  
							result1  : {title : " 客户/保单号         ",style : 0,width : "150px",colNo : 2},            
							result2 : {title : "号码类型", style : 3 }, 
							result3  : {title : " 申请人       ",style : 3},  
							result4 : {title : "申请方式 ", style : 3 }, 
							result5  : {title : " 管理机构         ",style : 3},  
							result6  : {title : " 投保人           ",style : 0,width : "60px",colNo : 4},            
							result7  : {title : " 交费对应日       ",style : 3}           
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
	jQuery("#ConfirmInputPool").workpool(config);
	jQuery("#privateSearch").click();
}
/*
function initAllGrid()
{                                  
	var iArray = new Array();      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全受理号";         		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户/保单号";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="号码类型";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="投保人";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="申请方式";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="管理机构";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许      
      iArray[6][4] = "Station";
      iArray[6][18] = 236;     

      iArray[7]=new Array();
      iArray[7][0]="录入员";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="录入日期";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=8;              			//是否允许输入,1表示允许，0表示不允许


      iArray[9]=new Array();
      iArray[9][0]="工作流任务号";         	//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="工作流子任务号";        //列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="工作流活动Id";         	//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许  


      iArray[12]=new Array();
      iArray[12][0]="核保结论";         	//列名
      iArray[12][1]="70px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许  

      iArray[13]=new Array();
      iArray[13][0]="核保结论编码";         	//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=200;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许  

      iArray[14]=new Array();
      iArray[14][0]="客户意见";         	//列名
      iArray[14][1]="60px";            		//列宽
      iArray[14][2]=200;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      
      iArray[15]=new Array();
      iArray[15][0]="操作人";         	//列名
      iArray[15][1]="50px";            		//列宽
      iArray[15][2]=200;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[16]=new Array();
      iArray[16][0]="受理日期";         	//列名
      iArray[16][1]="60px";            		//列宽
      iArray[16][2]=200;            			//列最大值
      iArray[16][3]=8;              			//是否允许输入,1表示允许，0表示不允许  
      
      
      iArray[17]=new Array();
      iArray[17][0]="超过日期";         	//列名
      iArray[17][1]="50px";            		//列宽
      iArray[17][2]=200;            			//列最大值
      iArray[17][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
    
       
      AllGrid = new MulLineEnter( "fm" , "AllGrid" ); 
      //这些属性必须在loadMulLine前
      AllGrid.mulLineCount = 5;   
      AllGrid.displayTitle = 1;
      AllGrid.locked = 1;
      AllGrid.canSel = 1;
      AllGrid.canChk = 0;
      AllGrid.hiddenPlus = 1;
      AllGrid.hiddenSubtraction = 1; 
      AllGrid.selBoxEventFuncName = "HighlightAllRow";       
      AllGrid.loadMulLine(iArray);  

      //这些操作必须在loadMulLine后面

	}
	catch(ex)
	{
		alert(ex);
	}
}

//
function initSelfGrid()
{                                  
	var iArray = new Array();      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全受理号";         		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户/保单号";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="号码类型";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="投保人";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="申请方式";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="管理机构";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许     
      iArray[6][4] = "Station";
      iArray[6][18] = 236; 

      iArray[7]=new Array();
      iArray[7][0]="录入员";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="录入日期";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=8;              			//是否允许输入,1表示允许，0表示不允许


      iArray[9]=new Array();
      iArray[9][0]="工作流任务号";         	//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="工作流子任务号";        //列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="工作流活动Id";         	//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许   



      iArray[12]=new Array();
      iArray[12][0]="核保结论";         	//列名
      iArray[12][1]="70px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许  

      iArray[13]=new Array();
      iArray[13][0]="核保结论编码";         	//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=200;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许  

      iArray[14]=new Array();
      iArray[14][0]="申请人名称EdorAppName";         	//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=200;            			//列最大值
      iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许  

      iArray[15]=new Array();
      iArray[15][0]="交费对应日PaytoDate";         	//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=200;            			//列最大值
      iArray[15][3]=3;              			//是否允许输入,1表示允许，0表示不允许  

  //    iArray[16]=new Array();
  //    iArray[16][0]="客户号CustomerNo";         	//列名
  //    iArray[16][1]="0px";            		//列宽
  //    iArray[16][2]=200;            			//列最大值
  //    iArray[16][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
  //
  //    iArray[17]=new Array();
  //    iArray[17][0]="第一被保人InsuredName1";         	//列名
  //    iArray[17][1]="0px";            		//列宽
  //    iArray[17][2]=200;            			//列最大值
  //    iArray[17][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
  //
  //    iArray[18]=new Array();
  //    iArray[18][0]="第二被保人InsuredName2";         	//列名
  //    iArray[18][1]="0px";            		//列宽
  //    iArray[18][2]=200;            			//列最大值
  //    iArray[18][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
  //
  //    iArray[19]=new Array();
  //    iArray[19][0]="第三被保人InsuredName3";         	//列名
  //    iArray[19][1]="0px";            		//列宽
  //    iArray[19][2]=200;            			//列最大值
  //    iArray[19][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
  //
  //    iArray[20]=new Array();
  //    iArray[20][0]="批改类型EdorType";         	//列名
  //    iArray[20][1]="0px";            		//列宽
  //    iArray[20][2]=200;            			//列最大值
  //    iArray[20][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
  //
  //
  //    iArray[21]=new Array();
  //    iArray[21][0]="转核日期theCurrentDate";         	//列名
  //    iArray[21][1]="0px";            		//列宽
  //    iArray[21][2]=200;            			//列最大值
  //    iArray[21][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
  //
  //    iArray[22]=new Array();
  //    iArray[22][0]="最后回复日期BackDate";         	//列名
  //    iArray[22][1]="0px";            		//列宽
  //    iArray[22][2]=200;            			//列最大值
  //    iArray[22][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
  //
  //    iArray[23]=new Array();
  //    iArray[23][0]="VIP客户VIP";         	//列名
  //    iArray[23][1]="0px";            		//列宽
  //    iArray[23][2]=200;            			//列最大值
  //    iArray[23][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
  //
  //    iArray[24]=new Array();
  //    iArray[24][0]="星级业务员StarAgent";         	//列名
  //    iArray[24][1]="0px";            		//列宽
  //    iArray[24][2]=200;            			//列最大值
  //    iArray[24][3]=3;              			//是否允许输入,1表示允许，0表示不允许

	  //modify by jiaqiangli 2009-03-25 以前版本是客户不同意才强制人工核保
	  //modify by jiaqiangli 2009-04-03 保全确认需要看客户意见是否同意
      iArray[16]=new Array();
      iArray[16][0]="客户意见";         	//列名
      iArray[16][1]="60px";            		//列宽
      iArray[16][2]=200;            			//列最大值
      iArray[16][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[17]=new Array();
      iArray[17][0]="客户意见编码";         	//列名
      iArray[17][1]="0px";            		//列宽
      iArray[17][2]=200;            			//列最大值
      iArray[17][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[18]=new Array();
      iArray[18][0]="受理日期";         	//列名
      iArray[18][1]="60px";            		//列宽
      iArray[18][2]=200;            			//列最大值
      iArray[18][3]=8;              			//是否允许输入,1表示允许，0表示不允许  
      
      
      iArray[19]=new Array();
      iArray[19][0]="超过日期";         	//列名
      iArray[19][1]="50px";            		//列宽
      iArray[19][2]=200;            			//列最大值
      iArray[19][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      SelfGrid = new MulLineEnter( "fm" , "SelfGrid" ); 
      //这些属性必须在loadMulLine前
      SelfGrid.mulLineCount = 5;   
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;   
      SelfGrid.selBoxEventFuncName="checkPayNotice";
      SelfGrid.loadMulLine(iArray);  


      //这些操作必须在loadMulLine后面

	}
	catch(ex)
	{
		alert(ex);
	}
}
*/
</script>