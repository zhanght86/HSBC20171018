<% 
//程序名称：WFEdorAppInit.jsp
//程序功能：保全工作流-保全申请
//创建日期：2005-04-27 11:49:22
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
    //document.all('PrtNo').value = '';
   // document.all('ManageCom').value = '';
   // document.all('InputDate').value = '';
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
    initInpBox();
    initWFEdorAppPool();
   // initAllGrid();  //初始化共享工作池
  //  initSelfGrid(); //初始化我的任务队列
  //  easyQueryClickSelf();  //查询我的任务队列
  }
  catch(re)
  {
    alert("WFEdorAppInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re);
  }
}
function initWFEdorAppPool(){
	var sql1 = "";
	var sql2 = "";
	var sql3 = "";
	var sql4 = "";
	var sql5 = "";
	var sql6 = "";
	if(_DBT==_DBO){
		sql1 = "  and defaultoperator is null and manageCom like '"  + manageCom + "%%' order by (select edorappdate from lpedoritem l  "+
		  " where l.EdorAcceptNo = EdorAcceptNo and rownum = 1), makedate, maketime";
	}else if(_DBT==_DBM){
		sql1 = "  and defaultoperator is null and manageCom like '"  + manageCom + "%%' order by (select edorappdate from lpedoritem l  "+
		  " where l.EdorAcceptNo = EdorAcceptNo limit 0,1), makedate, maketime";
	}
	 if(_DBT==_DBO){
		 sql2 = " (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd')  else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) ";
	 }else if(_DBT==_DBM){
		 sql2 = " (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd')  else '' end) from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) ";
	 }
	if(_DBT==_DBO){
		sql3 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y') ";
	}else if(_DBT==_DBM){
		sql3 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) and workdateflag = 'Y') ";
	}
	if(_DBT==_DBO){
		sql4 = "  and trim(defaultoperator) ='"+operator+"'  order by (select edorappdate from lpedoritem l  "+
		  " where l.EdorAcceptNo = EdorAcceptNo and rownum = 1), makedate, maketime";
	}else if(_DBT=_DBM){
		sql4 = "  and trim(defaultoperator) ='"+operator+"'  order by (select edorappdate from lpedoritem l  "+
		  " where l.EdorAcceptNo = EdorAcceptNo limit 0,1), makedate, maketime";
	}
	if(_DBT==_DBO){
		sql5 = " (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) ";
	}else if(_DBT==_DBM){
		sql5 = " (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) ";
	}
	if(_DBT==_DBO){
		sql6 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y') ";
	}else if(_DBT==_DBM){
		sql6 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) and workdateflag = 'Y') ";
	}
		var config = {
			functionId : "10020001",
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"管理机构",//列的标题
								  colNo : 1,
								  style : 2,
								  width : "80px",
								  colName:"MissionProp2",
								  refercode1:"station",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  }, 
							result0  : {title : " 保全受理号       ",style : 3},  
							result1  : {title : " 管理机构        ",style : 3},            
							result2  : {title : " 扫描员   ",style : 3},  
							result3  : {title : " 扫描日期       ",width : "80px", style : 8,colNo : 2},  
							result4  : {title : " 单证类型      ",style : 3}, 
							result5  : {title : " 单证号       ",style : 3} 
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
								title : "录入日期",
								style : 0,
								colNo : 3 ,
								width : "120px",
								colName : "makedate "
							},
							newCol1 : {
								title : "操作员",
								style : 0,
								colNo : 4 ,
								width : "60px",
								colName : "createoperator "
							},
							newCol2 : {
								title : "操作员名称",
								style : 0,
								colNo : 4 ,
								width : "60px",
								colName : " (select username from lduser where usercode = createoperator) ",
								rename : "createoperator_name"
					
							},
							newCol3 : {
								title : "受理日期",
								style : 0,
								colNo : 5 ,
								width : "70px",
								colName : sql2,
								rename : "edor_appdate"
					
							},
							newCol4 : {
								title : "超过日期",
								style : 0,
								colNo : 6 ,
								width : "50px",
								colName : sql3,
								rename : "out_day"
							},
							newCol5 : {
								title : "操作员",
								style : 3,
								colName : "defaultoperator "
							},
							newCol6 : {
								title : "录入时间",
								style : 3,
								colName : "maketime "
							},
							result0  : {title : " 保全受理号       ",width : "160px", style : 0,colNo : 1},  
							result1  : {title : " 管理机构        ",width : "120px",  style : 0,colNo : 2},            
							result2  : {title : " 扫描员   ",style : 3},  
							result3 : {title : " 扫描日期       ",style : 3},  
							result4  : {title : " 单证类型      ",style : 3}, 
							result5  : {title : " 单证号       ",style : 3}         
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
								title : "录入日期",
								style : 0,
								colNo : 3 ,
								width : "120px",
								colName : "makedate "
							},
							newCol1 : {
								title : "保全操作",
								style : 0,
								colNo : 4 ,
								width : "80px",
								colName : "case (select count(0) from lbmission where activityid in (select activityid from lwactivity where functionid = '10020005') and missionid = t.missionid) when 0 then '保全申请' else '审批修改' end ",
								rename : "edor_operate_name"
							},
							newCol2 : {
								title : "受理日期",
								style : 0,
								colNo : 5 ,
								width : "70px",
								colName : sql5,
								rename : "edor_appdate"
					
							},
							newCol3 : {
								title : "超过日期",
								style : 0,
								colNo : 6 ,
								width : "50px",
								colName : sql6,
								rename : "out_day"
							},
							newCol4 : {
								title : "操作员",
								style : 3,
								colName : "defaultoperator "
							},
							newCol5 : {
								title : "录入时间",
								style : 3,
								colName : "maketime "
							},
							result0  : {title : " 保全受理号       ",width : "160px", style : 1,colNo : 1},  
							result1  : {title : " 管理机构        ",width : "120px",  style : 1,colNo : 2},            
							result2  : {title : " 扫描员   ",style : 3},  
							result3 : {title : " 扫描日期       ",style : 3},  
							result4  : {title : " 单证类型      ",style : 3}, 
							result5  : {title : " 单证号       ",style : 3}         
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
	jQuery("#WFEdorInputPool").workpool(config);
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
      iArray[1][0]="单证号";         	//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=150;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[2]=new Array();
      iArray[2][0]="保全受理号";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=170;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="管理机构";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="录入日期";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=8;              			//是否允许输入,1表示允许，0表示不允许


      iArray[5]=new Array();
      iArray[5][0]="工作流任务号";         	//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="工作流子任务号";        //列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流活动Id";         	//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[8]=new Array();
      iArray[8][0]="单证类型";         	//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许       
           
      //add by jiaqiangli 2009-05-21 增加操作员列
      iArray[9]=new Array();
      iArray[9][0]="操作员编码";         	//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      //add by jiaqiangli 2009-05-21 增加操作员列
      iArray[10]=new Array();
      iArray[10][0]="操作员姓名";         	//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="受理日期";
      iArray[11][1]="70px";
      iArray[11][2]=0;
      iArray[11][3]=8;
      
      iArray[12]=new Array();
      iArray[12][0]="超过日期";
      iArray[12][1]="50px";
      iArray[12][2]=0;
      iArray[12][3]=0;
    
      AllGrid = new MulLineEnter( "document" , "AllGrid" ); 
      //这些属性必须在loadMulLine前
      AllGrid.mulLineCount = 5;   
      AllGrid.displayTitle = 1;
      AllGrid.locked = 1;
      AllGrid.canSel = 1;
      AllGrid.canChk = 0;
      AllGrid.selBoxEventFuncName = "HighlightAllRow";
      AllGrid.hiddenPlus = 1;
      AllGrid.hiddenSubtraction = 1;        
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
      iArray[1][0]="单证号";         	//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=1500;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[2]=new Array();
      iArray[2][0]="保全受理号";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=170;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="管理机构";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="录入日期";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=8;              			//是否允许输入,1表示允许，0表示不允许


      iArray[5]=new Array();
      iArray[5][0]="工作流任务号";         	//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="工作流子任务号";        //列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流活动Id";         	//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[8]=new Array();
      iArray[8][0]="单证类型";         	//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许  


        iArray[9]=new Array();
        iArray[9][0]="默认创建人";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="保全操作";
        iArray[10][1]="80px";
        iArray[10][2]=0;
        iArray[10][3]=0;
        
      iArray[11]=new Array();
      iArray[11][0]="受理日期";
      iArray[11][1]="70px";
      iArray[11][2]=0;
      iArray[11][3]=8;
      
      iArray[12]=new Array();
      iArray[12][0]="超过日期";
      iArray[12][1]="50px";
      iArray[12][2]=0;
      iArray[12][3]=0;
      
      SelfGrid = new MulLineEnter( "document" , "SelfGrid" ); 
      //这些属性必须在loadMulLine前
      SelfGrid.mulLineCount = 5;   
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.selBoxEventFuncName = "HighlightSelfRow";
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;        
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