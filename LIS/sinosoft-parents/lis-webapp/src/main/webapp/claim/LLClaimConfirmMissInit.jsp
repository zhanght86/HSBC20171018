<%
//程序名称：LLClaimConfirmMissInit.jsp
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
    alert("在LLClaimConfirmMissInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LLClaimConfirmMissInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    //initLLClaimConfirmGrid();
  //  initSelfLLClaimConfirmGrid();
   // querySelfGrid();
    
    initConfirmClaimPool();
    
    
//	initSelfPolGrid();
//	easyQueryClickSelf();
	//if(fm.ManageCom.value == '86')
	//{
		//divSearch1.style.display="";
		//divSearch2.style.display="none";
	//}
	//else
	//{
		//divSearch1.style.display="none";
		//divSearch2.style.display="";
	//}
  }
  catch(re)
  {
    alert("LLClaimConfirmMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re.name +":"+re.message);
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

//add by lzf  SelfLLClaimConfirmGridClick
function initConfirmClaimPool(){
	var config = {
			//activityId : "0000005055",
			functionId : "10030006",
			operator : operator,
			public : {
				show : false
			},
			private : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : " 立案号     ", style : 1,colNo : 1},
							result1 : {title : "报案状态", style : 3},
							result2 : {title : "客户编码", style : 3},
							result3 : {title : "客户姓名", style : 3},
							result4 : {title : "性别", style : 3},
							result5 : {title : "出险日期", style : 3},
							result6 : {title : "申请类型", style : 3},
							result7 : {title : "号码类型", style : 3},
							result8 : {title : "其他号码", style : 3},
							result9 : {title : "审批权限", style : 3},
							result10 : {title : "预付标志", style : 3},
							result11 : {title : "审核权限", style : 3},					
							result12 : {title : "审核人", style : 3},
							result13 : {title : "权限跨级标志", style : 3 },
							result14 : {title : "机构", style : 3}
						}
					}
				},
				resultTilte : "工作队列",
				result : {
					selBoxEventFuncName : "SelfLLClaimConfirmGridClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : "and MngCom like'"
							+ comcode
							+ "%'and RgtObj ='1' order by AcceptedDate,RptNo"
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
								colName : "(select UserName from LLClaimUser where UserCode = t.MissionProp13) ",
								rename : "Rptoperator"
							},
							newCol2 : {
								title : "受理日期",
								style : 3,
								colName : "(select accepteddate from llregister where rgtno=t.missionprop1) ",
								rename : "Accepteddate"
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
								style : 3},
							result7 : {title : "号码类型", style : 3 },
							result8 : {title : "其他号码", style : 3 },
							result9 : {title : "审批权限", style : 1,colNo : 7},
							result10 : {title : "预付标志", style : 3},
							result11 : {title : "审核权限", style : 3},					
							result12 : {title : "审核人", style : 3},
							result13 : {title : "权限跨级标志", style : 3 },
							result14 : {title : " 机  构", style : 1,colNo : 8}
						}
					}
				}
			}
	 }
	 jQuery("#ConfirmClaimPool").workpool(config);
	 jQuery("#privateSearch").click();
}



// 报案信息列表的初始化
/*function initLLClaimConfirmGrid()
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
    iArray[2][1]="65px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="客户编码";             //列名
    iArray[3][1]="100px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="客户姓名";             //列名
    iArray[4][1]="120px";                //列宽
    iArray[4][2]=200;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="性别";             //列名
    iArray[5][1]="50px";                //列宽
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
    iArray[10][0]="预付标志";             //列名
    iArray[10][1]="60px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
    iArray[11][0]="审核权限";             //列名
    iArray[11][1]="60px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[12]=new Array();
    iArray[12][0]="审核人";             //列名
    iArray[12][1]="120px";                //列宽
    iArray[12][2]=200;                  //列最大值
    iArray[12][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[13]=new Array();
    iArray[13][0]="权限";             //列名
    iArray[13][1]="60px";                //列宽
    iArray[13][2]=200;                  //列最大值
    iArray[13][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[14]=new Array();
    iArray[14][0]="进入日期";             //列名
    iArray[14][1]="80px";                //列宽
    iArray[14][2]=200;                  //列最大值
    iArray[14][3]=3;                    //是否允许输入,1表示允许，0表示不允许 
    
    iArray[15]=new Array();
    iArray[15][0]="审核操作人";             //列名
    iArray[15][1]="80px";                //列宽
    iArray[15][2]=200;                  //列最大值
    iArray[15][3]=0;                    //是否允许输入,1表示允许，0表示不允许 

    iArray[16]=new Array();
    iArray[16][0]="机构";             //列名
    iArray[16][1]="80px";                //列宽
    iArray[16][2]=200;                  //列最大值
    iArray[16][3]=0;                    //是否允许输入,1表示允许，0表示不允许 
    
    LLClaimConfirmGrid = new MulLineEnter( "fm" , "LLClaimConfirmGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimConfirmGrid.mulLineCount = 0;   
    LLClaimConfirmGrid.displayTitle = 1;
    LLClaimConfirmGrid.locked = 0;
//    LLClaimConfirmGrid.canChk = 1;
    LLClaimConfirmGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimConfirmGrid.selBoxEventFuncName ="LLClaimConfirmGridClick"; //响应的函数名，不加扩号
//        LLClaimConfirmGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项     
    LLClaimConfirmGrid.hiddenPlus=1;
    LLClaimConfirmGrid.hiddenSubtraction=1;
    LLClaimConfirmGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 报案信息列表的初始化
function initSelfLLClaimConfirmGrid()
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
    iArray[2][1]="65px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="客户编码";             //列名
    iArray[3][1]="100px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="客户姓名";             //列名
    iArray[4][1]="120px";                //列宽
    iArray[4][2]=200;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="性别";             //列名
    iArray[5][1]="50px";                //列宽
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
    iArray[10][0]="预付标志";             //列名
    iArray[10][1]="60px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
    iArray[11][0]="审核权限";             //列名
    iArray[11][1]="60px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[12]=new Array();
    iArray[12][0]="审核人";             //列名
    iArray[12][1]="120px";                //列宽
    iArray[12][2]=200;                  //列最大值
    iArray[12][3]=3;                    //是否允许输入,1表示允许，0表示不允许    
    
    iArray[13]=new Array();
    iArray[13][0]="权限";             //列名
    iArray[13][1]="60px";                //列宽
    iArray[13][2]=200;                  //列最大值
    iArray[13][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[14]=new Array();
    iArray[14][0]="进入日期";             //列名
    iArray[14][1]="80px";                //列宽
    iArray[14][2]=200;                  //列最大值
    iArray[14][3]=3;                    //是否允许输入,1表示允许，0表示不允许 
    
    iArray[15]=new Array();
    iArray[15][0]="审核操作人";             //列名
    iArray[15][1]="80px";                //列宽
    iArray[15][2]=200;                  //列最大值
    iArray[15][3]=0;                    //是否允许输入,1表示允许，0表示不允许 

    iArray[16]=new Array();
    iArray[16][0]="机构";             //列名
    iArray[16][1]="80px";                //列宽
    iArray[16][2]=200;                  //列最大值
    iArray[16][3]=0;                    //是否允许输入,1表示允许，0表示不允许 

    iArray[17]=new Array();
    iArray[17][0]="受理日期";             //列名
    iArray[17][1]="80px";                //列宽
    iArray[17][2]=200;                  //列最大值
    iArray[17][3]=3;                    //是否允许输入,1表示允许，0表示不允许 
    
    SelfLLClaimConfirmGrid = new MulLineEnter( "fm" , "SelfLLClaimConfirmGrid" ); 
    //这些属性必须在loadMulLine前
    SelfLLClaimConfirmGrid.mulLineCount = 0;   
    SelfLLClaimConfirmGrid.displayTitle = 1;
    SelfLLClaimConfirmGrid.locked = 0;
//    SelfLLClaimConfirmGrid.canChk = 1;
    SelfLLClaimConfirmGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    SelfLLClaimConfirmGrid.selBoxEventFuncName ="SelfLLClaimConfirmGridClick"; //响应的函数名，不加扩号
//        SelfLLClaimConfirmGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项    
    SelfLLClaimConfirmGrid.hiddenPlus=1;
    SelfLLClaimConfirmGrid.hiddenSubtraction=1;
    SelfLLClaimConfirmGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
</script>
