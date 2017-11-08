<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWHealthInit.jsp
//程序功能：个单新契约扫描件保单录入
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">
// 输入框的初始化（表单记录部分）
function initInpBox()
{
	try
	{
		// 保单查询条件
		document.all('QContNo').value = '';
		document.all('QPrtSeq').value = '';
		document.all('QHandler').value = '';
	}
	catch(ex)
	{
		alert("在UWHealthInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initTr();
		initInpBox();
		//initGrpGrid();
		initUWHealthPool();
	}
	catch(re)
	{
		alert("在UWHealthInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re.name +":"+re.message);
	}
}

//add by lzf 2013-03-22
function initUWHealthPool(){
	var config = {
			functionId : "10010036",
			//activityId : "0000001121",
			operator : operator,
			public : {
				id : "UWHealthPool",
				show : true,
				query : {
					arrayInfo : {
						col : {
							
							result0 : {title : " 印刷号",verify : "印刷号|num&len=14" ,colNo : 1 ,style : 1},
							result1 : {title : "合同号",verify : "合同号|num&len=14" ,style : 3},
							result2 : {title : "通知书流水号",colNo : 2 ,style : 1},
							result3 : {title : "体检人姓名",style : 3},
							result4 : {title : "经办人",colNo : 4 ,style : 1},
							result5 : {title : "经办日期",colNo : 3 ,style : 8}
						}
					}
				},
				resultTitle : "保单信息",
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"3" : false,
						"4" : true,
						"5" : "and managecom like'"
							+ comcode
							+ "%'  order by contno"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newcol0 : {
								title : "管理机构",
								colName : "(select a.managecom from lccont a where a.contno= t.missionprop2) ",
								rename : "managecom",
								style : "3"
							},
							result0 : {title : "印刷号",verify : "印刷号|num&len=14" ,style : "3"},
							result1 : {title : "  合同号  ",verify : "合同号|num&len=14" ,colNo : 1 ,style : "1"},
							result2 : {title : "体检通知书流水号",colNo : 2 ,style : "1"},
							result3 : {title : "体检人姓名",style : "3"},
							result4 : {title : " 经办人",colNo : 4 ,style : "1"},
							result5 : {title : " 经办日期  ",colNo : 3 ,style : "8"}
						}
						
					}
				}
			},
			private : {show : false}			
	};
	jQuery("#UWHealthPool").workpool(config);
}
// end by lzf 2013-03-22

// 保单信息列表的初始化
/**function initGrpGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="合同号";
		iArray[1][1]="120px";
		iArray[1][2]=170;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="体检通知书流水号";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="经办日期";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=8;

		iArray[4]=new Array();
		iArray[4][0]="经办人";         	
		iArray[4][1]="100px";           
		iArray[4][2]=100;            	
		iArray[4][3]=0;              	
		
		iArray[5]=new Array();
		iArray[5][0]="工作流任务号";  
		iArray[5][1]="0px";
		iArray[5][2]=200;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="工作流子任务号";
		iArray[6][1]="0px";
		iArray[6][2]=200;
		iArray[6][3]=3;

      iArray[7]=new Array();
      iArray[7][0]="工作流活动Id";      //列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[8]=new Array();
      iArray[8][0]="体检人客户号码";      //列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
    
      
		GrpGrid = new MulLineEnter( "fm" , "GrpGrid" );
		//这些属性必须在loadMulLine前
		GrpGrid.mulLineCount = 10;
		GrpGrid.displayTitle = 1;
		GrpGrid.locked = 1;
		GrpGrid.canSel = 1;
		GrpGrid.canChk = 0;
		GrpGrid.hiddenSubtraction = 1;
		GrpGrid.hiddenPlus = 1;
		GrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}*/
</script>
