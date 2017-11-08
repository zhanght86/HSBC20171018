<%
//**************************************************************************************************
//Name：LLClaimPrepayMissInit.jsp
//Function：待审核工作队列工作队列信息（准备用于“预付”处理）
//Author：yuejw
//Date: 2005-7-5 16:00
//更新记录：
//**************************************************************************************************
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

function initForm()
{
  try
  {
    initParam(); 
    //initLLClaimAuditGrid();
    //initLLClaimPrepayGrid()
    //LLClaimPrepayGridQuery();
    //LLClaimAuditGridQuery();
    initClaimPrepayPool();
  }
  catch(re)
  {
    alert("LLClaimAuditMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//modify by lzf 
function initClaimPrepayPool(){
	var config = {
			//activityId : "0000005025",
			functionId : "10030004",
			operator : operator,
			public : {show : false},
			private : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : " 赔案号  ", style : 1,colNo : 1},
							result1 : {title : "报案状态", style : 3},
							result2 : {title : "出险人编码", style : 3},
							result3 : {title : "出险人姓名", style : 3},
							result4 : {title : "出险人性别", style : 3},
							result5 : {title : "事故日期", style : 3},
							result6 : {title : "申请类型", style : 3},
							result7 : {title : "号码类型 ", style : 3},
							result8 : {title : "其他号码", style : 3},
							result9 : {title : "案件核赔权限", style : 3},
							result10 : {title : "操作人", style : 3},
							result11 : {title : "来自", style : 3},
							result12 : {title : "审核人", style : 3},
							result13 : {title : "权限跨级标志 ", style : 3},
							result14 : {title : "审批操作人", style : 3},
							result15 : {title : "机构", style : 3}
						}
					}
				},
				resultTitle : "工作队列",
				result : {
					selBoxEventFuncName : "LLClaimPrepayGridClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : "and MngCom like'"
							+ comcode
							+ "%'"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",	
							newCol0 : {
								title : " 赔案状态   ",
								style : 1,
								colNo : 2,
								colName : "(select codename from ldcode where codetype='llclaimstate' and missionprop2=trim(code)) ",
								rename : "clmstate"
							},
							newCol1 : {
								title : "性别   ",
								style : 1,
								colNo : 5,
								colName : "(case trim(t.missionprop5) when '0' then '男' when '1' then '女' else '不详' end) ",
								rename : "sex"
							},
							result0 : {title : "  赔案号   ", style : 1,colNo : 1},
							result1 : {
								title : "报案状态", 
								style : 3
								},
							result2 : {
								title : "客户编码 ",
								style : 1,
								colNo : 3
								},
							result3 : {
								title : "客户姓名 ", 
								style : 1,
								colNo : 4
								},
							result4 : {
								title : "性别 ", 
								style : 3
								
								},
							result5 : {
								title : "事故日期  ",
								style : 1,
								colNo : 6
								},
							result6 : {title : "申请类型", style : 3},
							result7 : {title : "号码类型 ", style : 3},
							result8 : {title : "其他号码", style : 3},
							result9 : {title : "案件核赔权限", style : 3},
							result10 : {title : "操作人", style : 3},
							result11 : {title : "来自", style : 3},
							result12 : {title : "审核人", style : 3},
							result13 : {title : "权限跨级标志 ", style : 3},
							result14 : {title : "审批操作人", style : 3},
							result15 : {title : "机构", style : 3}
						}
					}
				}
			}
	}
	jQuery("#ClaimPrepayPool").workpool(config);
	jQuery("#privateSearch").click();
}
//end by lzf

// 报案信息列表的初始化
function initLLClaimAuditGrid()
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
    iArray[1][1]="150px";                //列宽
    iArray[1][2]=100;                  //列最大值
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
    iArray[4][1]="120px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="性别编码";             //列名
    iArray[5][1]="0px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=3; 
    
    iArray[6]=new Array();
    iArray[6][0]="性别";             //列名
    iArray[6][1]="80px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0;     

    iArray[7]=new Array();
    iArray[7][0]="事故日期";             //列名
    iArray[7][1]="80px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="申请类型";             //列名
    iArray[8][1]="0px";                //列宽
    iArray[8][2]=200;                  //列最大值
    iArray[8][3]=3; 
    
    iArray[9]=new Array();
    iArray[9][0]="号码类型";             //列名
    iArray[9][1]="0px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=3; 
    
    iArray[10]=new Array();
    iArray[10][0]="其他号码";             //列名
    iArray[10][1]="0px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=3; 
    
    iArray[11]=new Array();
    iArray[11][0]="核赔师权限";             //列名
    iArray[11][1]="0px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=3;     

    iArray[12]=new Array();
    iArray[12][0]="预付标志";             //列名
    iArray[12][1]="0px";                //列宽
    iArray[12][2]=200;                  //列最大值
    iArray[12][3]=3;     
    
    iArray[13]=new Array();
    iArray[13][0]="来自";             //列名
    iArray[13][1]="0px";                //列宽
    iArray[13][2]=200;                  //列最大值
    iArray[13][3]=3;     
    
    iArray[14]=new Array();
    iArray[14][0]="审核操作人";             //列名
    iArray[14][1]="0px";                //列宽
    iArray[14][2]=200;                  //列最大值
    iArray[14][3]=3;     

    iArray[15]=new Array();
    iArray[15][0]="机构";             //列名
    iArray[15][1]="0px";                //列宽
    iArray[15][2]=200;                  //列最大值
    iArray[15][3]=3;   

    iArray[16]=new Array();
    iArray[16][0]="Missionid";             //列名
    iArray[16][1]="0px";                //列宽
    iArray[16][2]=200;                  //列最大值
    iArray[16][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[17]=new Array();
    iArray[17][0]="submissionid";             //列名
    iArray[17][1]="0px";                //列宽
    iArray[17][2]=200;                  //列最大值
    iArray[17][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[18]=new Array();
    iArray[18][0]="activityid";             //列名
    iArray[18][1]="0px";                //列宽
    iArray[18][2]=200;                  //列最大值
    iArray[18][3]=3;                    //是否允许输入,1表示允许，0表示不允许            
  
    iArray[19]=new Array();
    iArray[19][0]="ComFlag";             //列名---------权限跨级标志
    iArray[19][1]="0px";                //列宽
    iArray[19][2]=200;                  //列最大值
    iArray[19][3]=3;                    //是否允许输入,1表示允许，0表示不允许   
    
    iArray[20]=new Array();
    iArray[20][0]="赔案状态代码";             //列名
    iArray[20][1]="100px";                //列宽
    iArray[20][2]=100;                  //列最大值
    iArray[20][3]=3;                    //是否允许输入,1表示允许，0表示不允许      
        
    LLClaimAuditGrid = new MulLineEnter( "document" , "LLClaimAuditGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimAuditGrid.mulLineCount = 5;   
    LLClaimAuditGrid.displayTitle = 1;
    LLClaimAuditGrid.locked = 0;
    LLClaimAuditGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimAuditGrid.selBoxEventFuncName ="LLClaimAuditGridClick"; //响应的函数名，不加扩号   
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
/*function initLLClaimPrepayGrid()
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
    iArray[1][1]="150px";                //列宽
    iArray[1][2]=100;                  //列最大值
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
    iArray[4][1]="120px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="性别代码";             //列名
    iArray[5][1]="0px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=3; 
    
    iArray[6]=new Array();
    iArray[6][0]="性别";             //列名
    iArray[6][1]="80px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0;     

    iArray[7]=new Array();
    iArray[7][0]="事故日期";             //列名
    iArray[7][1]="80px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="Missionid";             //列名
    iArray[8][1]="80px";                //列宽
    iArray[8][2]=200;                  //列最大值
    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
    iArray[9][0]="submissionid";             //列名
    iArray[9][1]="100px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="activityid";             //列名
    iArray[10][1]="100px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许 
    
    iArray[11]=new Array();
    iArray[11][0]="赔案状态代码";             //列名
    iArray[11][1]="100px";                //列宽
    iArray[11][2]=100;                  //列最大值
    iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许                  

    iArray[12]=new Array();
    iArray[12][0]="案件审核人";             //列名
    iArray[12][1]="30px";                //列宽
    iArray[12][2]=30;                  //列最大值
    iArray[12][3]=3;                    //是否允许输入,1表示允许，0表示不允许         

    iArray[13]=new Array();
    iArray[13][0]="受理日期";             //列名
    iArray[13][1]="30px";                //列宽
    iArray[13][2]=30;                  //列最大值
    iArray[13][3]=3;                    //是否允许输入,1表示允许，0表示不允许         
    
    LLClaimPrepayGrid = new MulLineEnter( "fm" , "LLClaimPrepayGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimPrepayGrid.mulLineCount =0;   
    LLClaimPrepayGrid.displayTitle = 1;
    LLClaimPrepayGrid.locked = 0;
    LLClaimPrepayGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimPrepayGrid.selBoxEventFuncName ="LLClaimPrepayGridClick"; //响应的函数名，不加扩号
    LLClaimPrepayGrid.hiddenPlus=1;
    LLClaimPrepayGrid.hiddenSubtraction=1;
    LLClaimPrepayGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
</script>
