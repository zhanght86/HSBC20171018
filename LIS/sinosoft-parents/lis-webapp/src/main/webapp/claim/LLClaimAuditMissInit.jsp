<%
//程序名称：LLClaimAuditMissInit.jsp
//程序功能：
//创建日期：2005-6-6 11:30
//创建人  ：zl
//更新记录：
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
   String tCurrentDate = PubFun.getCurrentDate();
%>

<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
var CurrentDate="<%=tCurrentDate%>";

//接收参数
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
    document.all('CurDate').value = CurrentDate;
}

//把null的字符串转为空
function nullToEmpty(tstring)
{
	if ((tstring == "null") || (tstring == "undefined"))
	{
		tstring = "";
	}
	return tstring;
}

// 输入框的初始化
function initInpBox()
{ 

  try
  {                                   
  }
  catch(ex)
  {
    alert("在LLClaimAuditMissInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LLClaimAuditMissInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    //initLLClaimAuditGrid();
    //initSelfLLClaimAuditGrid();
    //querySelfGrid();
//	initSelfPolGrid();
//	easyQueryClickSelf();
	initAuditClaimPool();
  }
  catch(re)
  {
    alert("LLClaimAuditMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re.name +":"+re.message);
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
 function initAuditClaimPool(){
	var sql1 = "";
	if(_DBT==_DBO){
		sql1 = "(Case (select 1 from llaffix where rgtno=t.missionprop1 And Rownum=1) When 1 Then (Case (select 1 from llaffix where rgtno=t.missionprop1 And (subflag is null or subflag='1') And Rownum=1) When 1 Then '未全部回销' Else '已回销'  End) Else '未发起' End ) ";
	}else if(_DBT==_DBM){
		sql1 = "(Case (select 1 from llaffix where rgtno=t.missionprop1 limit 0,1) When 1 Then (Case (select 1 from llaffix where rgtno=t.missionprop1 And (subflag is null or subflag='1') limit 0,1) When 1 Then '未全部回销' Else '已回销'  End) Else '未发起' End ) ";
	}
	
	
	 var config = {
			//activityId : "0000005035",
			functionId : "10030005",
			//operator : operator,
			public : {
				show : false
			},
			private : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : " 立案号   ", style : 1,colNo : 1},
							result1 : {title : "报案状态", style : 3},
							result2 : {title : "客户编码", style : 3},
							result3 : {title : "客户姓名", style : 3},
							result4 : {title : "性别", style : 3},
							result5 : {title : "事故日期", style : 3},
							result6 : {title : "申请类型", style : 3},
							result7 : {title : "号码类型", style : 3},
							result8 : {title : "其他号码", style : 3},
							result9 : {title : "审核权限", style : 3},
							result10 : {title : "预付标志", style : 3},
							result11 : {title : "来自", style : 3},
							result12 : {title : "权限跨级标志", style : 3},
							result13 : {title : "审核操作人", style : 3 },
							result14 : {title : "机构", style : 3},
							result15 : {
								title : "审核人", 
								style : 3
							}
						}
					}
				},
				resultTilte : "工作队列",
				result : {
					selBoxEventFuncName : "SelfLLClaimAuditGridClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : "and MngCom like'"
							+ comcode
							+ "%' and ( defaultoperator = '"+ operator +"' or auditer ='"+ operator +"') and RgtObj ='1' order by AcceptedDate,RptNo"
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
								colNo : 10,
								colName : "(select UserName from LLClaimUser where UserCode = (select operator from llregister where trim(rgtno) = t.missionprop1)) ",
								rename : "Rptoperator"
							},
							newCol2 : {
								title : "补充材料是否回销",
								style : 1,
								colNo : 12,
								colName : sql1,
								rename : "resellingflag"
							},
							newCol3 : {
								title : "受理日期",
								style : 3,
								colName : "(select accepteddate from llregister where rgtno=t.missionprop1) ",
								rename : "Accepteddate"
							},
							newCol4 : {
								title : "预付标志",
								style : 1,
								colNo : 7,
								colName : "(case t.MissionProp11 when '0' then '无预付' when '1' then '有预付' end) ",
								rename : "PrepayFlag1"
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
								title : "性别", 
								style : 1,
								colNo : 5,
								colName : "(case t.missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end) ",
								rename : "CustomerSex"
								},
							result5 : {title : "事故日期", style : 3},
							result6 : {
								title : "申请类型",
								style : 1,
								colNo : 8,
								colName : "(case t.MissionProp7 when '1' then '个人' when '2' then '团体' end) ",
								rename : "RgtClass"},
							result7 : {title : "号码类型", style : 3 },
							result8 : {title : "其他号码", style : 3 },
							result9 : {title : "审核权限", style : 3 },
							result10 : {
								title : "预付标志",
								style : 3
								},
							result11 : {
								title : "来 自", 
								style : 1,
								colNo : 9,
								colName : "(case t.MissionProp12 when 'A' then '立案' when 'B' then '审核' when 'C' then '审批' when 'D' then '简易案件' end) ",
								rename : "ComeWhere"},
							result12 : {title : "权限跨级标志", style : 3},
							result13 : {title : "审核操作人", style : 3},
							result14 : {title : "机构", style : 1,colNo : 11},
							result15 : {title : "审核人", style : 3}
						}
					}
				}
			}
	 }
	 jQuery("#AuditClaimPool").workpool(config);
	 jQuery("#privateSearch").click();
 }
 
// 报案信息列表的初始化
/*function initLLClaimAuditGrid()
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
    iArray[1][0]="立案号";             //列名
    iArray[1][1]="160px";                //列宽
    iArray[1][2]=160;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="赔案状态";             //列名
    iArray[2][1]="70px";                //列宽
    iArray[2][2]=70;                  //列最大值
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
    iArray[5][1]="50px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="事故日期";             //列名
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
    iArray[10][0]="预付标志";             //列名
    iArray[10][1]="60px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
    iArray[11][0]="申请类型";             //列名
    iArray[11][1]="60px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[12]=new Array();
    iArray[12][0]="来自";             //列名
    iArray[12][1]="60px";                //列宽
    iArray[12][2]=200;                  //列最大值
    iArray[12][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[13]=new Array();
    iArray[13][0]="权限";             //列名
    iArray[13][1]="50px";                //列宽
    iArray[13][2]=200;                  //列最大值
    iArray[13][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[14]=new Array();
    iArray[14][0]="预付值";             //列名
    iArray[14][1]="120px";                //列宽
    iArray[14][2]=200;                  //列最大值
    iArray[14][3]=3;                    //是否允许输入,1表示允许，0表示不允许
   
    iArray[15]=new Array();
    iArray[15][0]="进入日期";             //列名
    iArray[15][1]="80px";                //列宽
    iArray[15][2]=200;                  //列最大值
    iArray[15][3]=3;                    //是否允许输入,1表示允许，0表示不允许 
    
    iArray[16]=new Array();
    iArray[16][0]="立案操作人";             //列名
    iArray[16][1]="80px";                //列宽
    iArray[16][2]=200;                  //列最大值
    iArray[16][3]=0;                    //是否允许输入,1表示允许，0表示不允许 
    
    iArray[17]=new Array();
    iArray[17][0]="机构";             //列名
    iArray[17][1]="80px";                //列宽
    iArray[17][2]=200;                  //列最大值
    iArray[17][3]=0;                    //是否允许输入,1表示允许，0表示不允许 
    
    LLClaimAuditGrid = new MulLineEnter( "document" , "LLClaimAuditGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimAuditGrid.mulLineCount = 0;   
    LLClaimAuditGrid.displayTitle = 1;
    LLClaimAuditGrid.locked = 0;
//    LLClaimAuditGrid.canChk = 1;
    LLClaimAuditGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimAuditGrid.selBoxEventFuncName ="LLClaimAuditGridClick"; //响应的函数名，不加扩号
//        LLClaimAuditGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项     
    LLClaimAuditGrid.hiddenPlus=1;
    LLClaimAuditGrid.hiddenSubtraction=1;
    LLClaimAuditGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 报案信息列表的初始化
/*function initSelfLLClaimAuditGrid()
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
    iArray[1][0]="立案号";             //列名
    iArray[1][1]="160px";                //列宽
    iArray[1][2]=160;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="赔案状态";             //列名
    iArray[2][1]="70px";                //列宽
    iArray[2][2]=70;                  //列最大值
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
    iArray[5][1]="50px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许

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
    iArray[10][0]="预付标志";             //列名
    iArray[10][1]="60px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
    iArray[11][0]="申请类型";             //列名
    iArray[11][1]="60px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[12]=new Array();
    iArray[12][0]="来自";             //列名
    iArray[12][1]="60px";                //列宽
    iArray[12][2]=200;                  //列最大值
    iArray[12][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[13]=new Array();
    iArray[13][0]="权限";             //列名
    iArray[13][1]="50px";                //列宽
    iArray[13][2]=200;                  //列最大值
    iArray[13][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[14]=new Array();
    iArray[14][0]="预付值";             //列名
    iArray[14][1]="120px";                //列宽
    iArray[14][2]=200;                  //列最大值
    iArray[14][3]=3;                    //是否允许输入,1表示允许，0表示不允许
   
    iArray[15]=new Array();
    iArray[15][0]="进入日期";             //列名
    iArray[15][1]="80px";                //列宽
    iArray[15][2]=200;                  //列最大值
    iArray[15][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[16]=new Array();
    iArray[16][0]="立案操作人";             //列名
    iArray[16][1]="80px";                //列宽
    iArray[16][2]=200;                  //列最大值
    iArray[16][3]=0;                    //是否允许输入,1表示允许，0表示不允许 
    
    iArray[17]=new Array();
    iArray[17][0]="机构";             //列名
    iArray[17][1]="80px";                //列宽
    iArray[17][2]=200;                  //列最大值
    iArray[17][3]=0;                    //是否允许输入,1表示允许，0表示不允许  

    iArray[18]=new Array();
    iArray[18][0]="受理日期";             //列名
    iArray[18][1]="80px";                //列宽
    iArray[18][2]=200;                  //列最大值
    iArray[18][3]=3;                    //是否允许输入,1表示允许，0表示不允许  

    iArray[19]=new Array();
    iArray[19][0]="补充材料是否回销";             //列名
    iArray[19][1]="100px";                //列宽
    iArray[19][2]=200;                  //列最大值
    iArray[19][3]=0;                    //是否允许输入,1表示允许，0表示不允许  
    
    SelfLLClaimAuditGrid = new MulLineEnter( "document" , "SelfLLClaimAuditGrid" ); 
    //这些属性必须在loadMulLine前
    SelfLLClaimAuditGrid.mulLineCount = 0;   
    SelfLLClaimAuditGrid.displayTitle = 1;
    SelfLLClaimAuditGrid.locked = 0;
//    SelfLLClaimAuditGrid.canChk = 1;
    SelfLLClaimAuditGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    SelfLLClaimAuditGrid.selBoxEventFuncName ="SelfLLClaimAuditGridClick"; //响应的函数名，不加扩号
//        SelfLLClaimAuditGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项    
    SelfLLClaimAuditGrid.hiddenPlus=1;
    SelfLLClaimAuditGrid.hiddenSubtraction=1;
    SelfLLClaimAuditGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
</script>
