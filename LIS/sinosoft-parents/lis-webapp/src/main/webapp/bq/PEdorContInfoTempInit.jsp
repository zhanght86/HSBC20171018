<%
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-12-3
 * @direction: 客户重要资料变更
 ******************************************************************************/
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%
    //添加页面控件的初始化。
    String tCurDate=PubFun.getCurrentDate();
%>

<script language="JavaScript">

var ContGrid;

function initForm()
{
    try
    {
        initContGrid();  
        easyQueryClickCont();
    	initContInfoPool();
        //初始化我的任务队列
        fm.EdorAppDate.value=NullToEmpty("<%= tCurDate %>");  
        fm.AppType.value="IC"; //用于区分无扫描工作流节点，MissionProp5
        //alert(fm.EdorAppDate.value);
       // initSelfGrid();
        //easyQueryClickSelf();    //查询我的任务队列
    }
    catch (ex)
    {
        alert("在 PEdorContInfoTempInit.jsp --> initForm 函数中发生异常:初始化界面错误！ ");
    }
}

function initContInfoPool(){
	var sql1 = "";
	if(_DBT==_DBO){
		sql1 =" and trim(defaultoperator) ='"+operator+ "' and OtherNo in (select contno from lpconttempinfo where state = '0') and ICFlag = 'IC' "+
		  " order by (select edorappdate from lpedoritem l where l.EdorAcceptNo = EdorAcceptNo and rownum=1), makedate, maketime";
	}else if(_DBT==_DBM){
		sql1 =" and trim(defaultoperator) ='"+operator+ "' and OtherNo in (select contno from lpconttempinfo where state = '0') and ICFlag = 'IC' "+
		  " order by (select edorappdate from lpedoritem l where l.EdorAcceptNo = EdorAcceptNo limit 0,1), makedate, maketime";
	}
	var config = {
			functionId : "10020002",
			public : {
				show : false 	
			},
			private : {
				query :{
					queryButton : {},
					arrayInfo : {
							col : {
							result0  : {title : " 保全受理号       ",style : 1,width : "145px",colNo : 1},  
							result1  : {title : " 管理机构        ",style :3},           
							result2 : {title : "投保人", style : 3 }, 
							result3  : {title : " 交费对应日       ",style : 3},  
							result4  : {title : " 保单号       ",style : 1,width : "130px",colNo : 2},   
							result5  : {title : " 申请号码类型           ",style : 3},            
							result6  : {title : " 申请人名称       ",style : 3} ,          
							result7  : {title : " 申请方式       ",style : 3} ,          
							result8  : {title : " 申请日期       ",style : 3},           
							result9  : {title : " 申请操作员      ",style : 3} ,          
							result10  : {title : " 重要资料变更标记      ",style : 3}         
							}
						}
				},
				resultTitle : "正在处理的保单",
				result : {
					selBoxEventFuncName : "HighlightSelfRow",
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
								title : "申请号码",
								style : 0,
								colNo : 3,
								width : "130px",
								colName : "MissionProp7 ",
								rename : "other_no"
								},
							newCol1 : {
								title : "柜面受理日期",
								style : 0,
								colNo : 4 ,
								width : "70px",
								colName : "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1)",
								rename : "edor_appdate"
							},
							newCol2 : {
								title : "受理日期",
								style : 0,
								colNo : 7 ,
								width : "70px",
								colName : " (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1)",
								rename : "edor_appdate1"
							},
							newCol3 : {
								title : "保全状态",
								style : 0,
								colNo : 6 ,
								width : "50px",
								colName : "(select (select codename from ldcode where codetype = 'edorstate' and code = edorstate) from lpedorapp where EdorAcceptNo = t.missionprop1)",
								rename : "edorstate_name"
							},
							newCol4 : {
								title : "超过日期",
								style : 0,
								colNo : 8 ,
								width : "50px",
								colName : "(select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1) )",
								rename : "out_day"
							},
							newCol5 : {
								title : "录入时间",
								style : 3,
								colName : "maketime "
							},
							newCol6 : {
								title : "录入日期",
								style : 3,
								colName : "makedate "
							},
							result0  : {title : " 保全受理号       ",style : 0,width : "145px",colNo : 1},  
							result1  : {title : " 管理机构        ",style : 0,width : "70px",colNo : 5},           
							result2 : {title : "投保人", style : 3 }, 
							result3  : {title : " 交费对应日       ",style : 3},  
							result4  : {title : " 保单号       ",style : 0,width : "130px",colNo : 2},   
							result5  : {title : " 申请号码类型           ",style : 3},            
							result6  : {title : " 申请人名称       ",style : 3} ,          
							result7  : {title : " 申请方式       ",style : 3} ,          
							result8  : {title : " 申请日期       ",style : 3},           
							result9  : {title : " 申请操作员      ",style : 3} ,          
							result10  : {title : " 重要资料变更标记      ",style : 3}
						}
					}
				}	
			}
	};
	jQuery("#PEdorContInfoInputPool").workpool(config);
	jQuery("#privateSearch").click();
}


function initContGrid()
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
        iArray[1][1]="160px";
        iArray[1][2]=200;
        iArray[1][3]=3;


        iArray[2]=new Array();
        iArray[2][0]="保单号";
        iArray[2][1]="200px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="投保人号码";
        iArray[3][1]="150px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="被保人号码";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="是否需要重算标志";
        iArray[5][1]="120px";
        iArray[5][2]=100;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="确认日期";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=8;
        iArray[6][21]=3;

        iArray[7]=new Array();
        iArray[7][0]="状态";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
      	iArray[8][0]="受理日期";
      	iArray[8][1]="70px";
      	iArray[8][2]=0;
      	iArray[8][3]=8;
      	       
      	iArray[9]=new Array();
      	iArray[9][0]="超过日期";
      	iArray[9][1]="50px";
      	iArray[9][2]=0;
      	iArray[9][3]=0;

        
        ContGrid = new MulLineEnter("document", "ContGrid");
        //这些属性必须在loadMulLine前
        ContGrid.mulLineCount = 5;
        ContGrid.displayTitle = 1;
        ContGrid.locked = 1;
        ContGrid.canSel = 1;
        ContGrid.canChk = 0;
        ContGrid.hiddenPlus = 1;
        ContGrid.hiddenSubtraction = 1;
        ContGrid.selBoxEventFuncName="displayInfo";
        ContGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert("在 PEdorContInfoTempInit.jsp --> initContGrid 函数中发生异常:初始化界面错误！ ");
    }
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
        iArray[2][0]="保单号";
        iArray[2][1]="130px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="申请号码";
        iArray[3][1]="130px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="柜面受理日期";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=8;

        iArray[5]=new Array();
        iArray[5][0]="管理机构";
        iArray[5][1]="70px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=3;   
        
        iArray[6]=new Array();
        iArray[6][0]="工作流任务号";
        iArray[6][1]="0px";
        iArray[6][2]=0;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="工作流子任务号";
        iArray[7][1]="0px";
        iArray[7][2]=0;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="工作流活动ID";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="默认创建人";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="保全状态";
        iArray[10][1]="50px";
        iArray[10][2]=100;
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
        
        SelfGrid = new MulLineEnter("document", "SelfGrid");
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
        alert("在 PEdorContInfoTempInit.jsp --> initSelfGrid 函数中发生异常:初始化界面错误！ ");
    }
}
*/
</script>
