<%
//程序名称：LLClaimSimpleMissInit.jsp
//程序功能：
//创建日期：2005-6-6 11:30
//创建人  ：zl
//更新记录：
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//接收参数
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
}

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

// 输入框的初始化
function initInpBox()
{ 

  try
  {                                   
  }
  catch(ex)
  {
    alert("在LLClaimSimpleMissInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("sex","0=男&1=女&2=不详");        
  }
  catch(ex)
  {
    alert("在LLClaimSimpleMissInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    //initLLClaimSimpleGrid();
   // initSelfLLClaimSimpleGrid();
   // querySelfGrid();
//	initSelfPolGrid();
//	easyQueryClickSelf();
	initSimpleClaimPool();
  }
  catch(re)
  {
    alert("LLClaimSimpleMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//清空页面
function emptyForm() 
{
	//emptyFormElements();     //清空页面所有输入框，在COMMON.JS中实现
   
	initSubInsuredGrid();
	initBnfGrid();
	initImpartGrid();
	initSpecGrid();
	initDutyGrid();
	
	//SubInsuredGrid.clearData();
	//BnfGrid.clearData();
	//ImpartGrid.clearData();
	//SpecGrid.clearData();
	//DutyGrid.clearData();
	spanDutyGrid.innerHTML="";
}

//add by lzf  
function initSimpleClaimPool(){
	 var config = {
				//activityId : "0000005065",
				functionId : "10030007",
				operator : operator,
				public : {
					show : true,
					query : {
						queryButton : {
							"1" : {
								title : "申  请",
								func : "ApplyClaim"
								}
						},
						arrayInfo : {
							col : {
								newCol0 : {
									title : "立案日期",
									style : 8, 
									colNo : 6,
									refercode1 : "rptdate",
									addCondition : function(colName ,value){
										return "and exists(select 1 from llregister where rgtno=t.missionprop1 and rgtdate='"+value+"')";
									}
									
								},
								result0 : {title : " 赔案号   ", style : 1,colNo : 1},
								result1 : {
									title : "报案状态",
									style : 2,
									colNo : 2,
									refercode2 : "RptorState",
									refercodedata2 : "0|3^10|已报案^20|已立案^30|审核",
									addCondition : function(colName ,value){
										return " and t.missionprop2 = '"+value+"'";
									}
									},
								result2 : {title : "客户编码", style : 1 , colNo : 3 },
								result3 : {
									title : "客户姓名", 
									style : 1 , 
									colNo : 4 ,
									refercode1 : "CustomerName",
									addCondition : function(colName,value){
										return " and t.MissionProp4 like '"+value+"%'";
									}
								    },
								result4 : {
									title : "客户性别", 
									style : 2,
									colNo : 5,
									refercode1 : "sex",
									addCondition : function(colName,value){
										return " and t.MissionProp5 = '"+value+"'";
									}
								    },
								result5 : {title : "事故日期", style : 3},
								result6 : {title : "申请类型", style : 3},
								result7 : {title : "号码类型", style : 3},
								result8 : {title : "其他号码", style : 3},
								result9 : {title : "机构", style : 3}
							}
						}
					},
					result : {
						condition : {
							"0" : false,
							"1" : false,
							"2" : false,
							"3" : false,
							"4" : true,
							"5" : "and MngCom like '%"
								+ comcode
								+ "%' and exists (select 'X' from llclaimuser where simpleflag='1' and usercode='"+ operator +"')"
						},
						mulLineCount : 0,
						arrayInfo : {
							col : {
								col5 : "0",
								col6 : "0",
								col7 : "0",	
								newCol0 : {
									title : "立案日期",
									style : 1,
									colNo : 6,
									colName : "(select rgtdate from llregister where rgtno=t.missionprop1) ",
									rename : "RptDate"
								},
								newCol1 : {
									title : "立案操作人",
									style : 1,
									colNo : 7,
									colName : "(select UserName from LLClaimUser where UserCode = t.lastoperator) ",
									rename : "Rptoperator"
								},
								result0 : {title : " 立案号     ", style : 1,colNo : 1},
								result1 : {
									title : "报案状态",
									style : 1,
									colNo : 2,
									colName : "(case t.missionprop2 when '20' then '立案' when '30' then '审核' when '40' then '审批' end) ",
									rename : "RptorState"
									},
								result2 : {title : "客户编码", style : 1,colNo : 3},
								result3 : {title : "客户姓名", style : 1,colNo : 4},
								result4 : {
									title : "客户性别", 
									style : 1,
									colNo : 5,
									colName : "(case t.missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end) ",
									rename : "CustomerSex"
									},
								result5 : {title : "事故日期", style : 3},
								result6 : {
									title : "申请类型",
									style : 3},
								result7 : {title : "号码类型", style : 3 },
								result8 : {title : "其他号码", style : 3 },
								result9 : {title : " 机构  ", style : 1,colNo : 8}

							}
						}
					}
				},
				private : {
					query : {
						queryButton : {},
						arrayInfo : {
							col : {
								result0 : {title : " 赔案号   ", style : 1,colNo : 1},
								result1 : {
									title : "报案状态",
									style : 3
									},
								result2 : {title : "客户编码", style : 3},
								result3 : {title : "客户姓名", style : 3},
								result4 : {title : "客户性别", style : 3},
								result5 : {title : "事故日期", style : 3},
								result6 : {title : "申请类型", style : 3},
								result7 : {title : "号码类型", style : 3},
								result8 : {title : "其他号码", style : 3},
								result9 : {title : "机构", style : 3}
							}
						}
					},
					resultTilte : "工作队列",
					result : {
						selBoxEventFuncName : "SelfLLClaimSimpleGridClick",
						selBoxEventFuncParam : "",
						condition : {
							"0" : false,
							"1" : false,
							"2" : false,
							"5" : " order by RptNo"
						},
						mulLineCount : 0,
						arrayInfo : {
							col : {
								col5 : "0",
								col6 : "0",
								col7 : "0",	
								newCol0 : {
									title : "立案日期",
									style : 1,
									colNo : 6,
									colName : "(select rgtdate from llregister where rgtno=t.missionprop1) ",
									rename : "RptDate"
								},
								newCol1 : {
									title : "立案操作人",
									style : 1,
									colNo : 7,
									colName : "(select UserName from LLClaimUser where UserCode = t.lastoperator) ",
									rename : "Rptoperator"
								},
								result0 : {title : " 立案号     ", style : 1,colNo : 1},
								result1 : {
									title : "报案状态",
									style : 1,
									colNo : 2,
									colName : "(case t.missionprop2 when '20' then '立案' when '30' then '审核' when '40' then '审批' end) ",
									rename : "RptorState"
									},
								result2 : {title : "客户编码", style : 1,colNo : 3},
								result3 : {title : "客户姓名", style : 1,colNo : 4},
								result4 : {
									title : "客户性别", 
									style : 1,
									colNo : 5,
									colName : "(case t.missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end) ",
									rename : "CustomerSex"
									},
								result5 : {title : "事故日期", style : 3},
								result6 : {
									title : "申请类型",
									style : 3},
								result7 : {title : "号码类型", style : 3 },
								result8 : {title : "其他号码", style : 3 },
								result9 : {title : " 机构  ", style : 1,colNo : 8}

							}
						}
					}
				}
		 }
		 jQuery("#SimpleClaimPool").workpool(config);
		 jQuery("#privateSearch").click();
}

//end by lzf


// 报案信息列表的初始化
/*function initLLClaimSimpleGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";                //列宽
    iArray[0][2]=10;                  //列最大值
    iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="赔案号";             //列名
    iArray[1][1]="160px";                //列宽
    iArray[1][2]=160;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="赔案状态";             //列名
    iArray[2][1]="100px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="客户编码";             //列名
    iArray[3][1]="100px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="客户姓名";             //列名
    iArray[4][1]="100px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="性别";             //列名
    iArray[5][1]="80px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="立案日期";             //列名
    iArray[6][1]="100px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //列名
    iArray[7][1]="80px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //列名
    iArray[8][1]="100px";                //列宽
    iArray[8][2]=200;                  //列最大值
    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //列名
    iArray[9][1]="100px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许            
    
    iArray[10]=new Array();
    iArray[10][0]="立案操作人";             //列名
    iArray[10][1]="100px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
    iArray[11][0]="机构";             //列名
    iArray[11][1]="80px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许   
    
    LLClaimSimpleGrid = new MulLineEnter( "fm" , "LLClaimSimpleGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimSimpleGrid.mulLineCount = 0;   
    LLClaimSimpleGrid.displayTitle = 1;
    LLClaimSimpleGrid.locked = 0;
//    LLClaimSimpleGrid.canChk = 1;
    LLClaimSimpleGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimSimpleGrid.selBoxEventFuncName ="LLClaimSimpleGridClick"; //响应的函数名，不加扩号
//        LLClaimSimpleGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项     
    LLClaimSimpleGrid.hiddenPlus=1;
    LLClaimSimpleGrid.hiddenSubtraction=1;
    LLClaimSimpleGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 报案信息列表的初始化
function initSelfLLClaimSimpleGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";                //列宽
    iArray[0][2]=10;                  //列最大值
    iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="赔案号";             //列名
    iArray[1][1]="160px";                //列宽
    iArray[1][2]=160;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="赔案状态";             //列名
    iArray[2][1]="100px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="客户编码";             //列名
    iArray[3][1]="100px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="客户姓名";             //列名
    iArray[4][1]="100px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="性别";             //列名
    iArray[5][1]="80px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="立案日期";             //列名
    iArray[6][1]="100px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //列名
    iArray[7][1]="80px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //列名
    iArray[8][1]="100px";                //列宽
    iArray[8][2]=200;                  //列最大值
    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //列名
    iArray[9][1]="100px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许            
    
    iArray[10]=new Array();
    iArray[10][0]="立案操作人";             //列名
    iArray[10][1]="100px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
    iArray[11][0]="机构";             //列名
    iArray[11][1]="80px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许   
    
    SelfLLClaimSimpleGrid = new MulLineEnter( "fm" , "SelfLLClaimSimpleGrid" ); 
    //这些属性必须在loadMulLine前
    SelfLLClaimSimpleGrid.mulLineCount = 0;   
    SelfLLClaimSimpleGrid.displayTitle = 1;
    SelfLLClaimSimpleGrid.locked = 0;
//    SelfLLClaimSimpleGrid.canChk = 1;
    SelfLLClaimSimpleGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    SelfLLClaimSimpleGrid.selBoxEventFuncName ="SelfLLClaimSimpleGridClick"; //响应的函数名，不加扩号
//        SelfLLClaimSimpleGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项    
    SelfLLClaimSimpleGrid.hiddenPlus=1;
    SelfLLClaimSimpleGrid.hiddenSubtraction=1;
    SelfLLClaimSimpleGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
</script>
