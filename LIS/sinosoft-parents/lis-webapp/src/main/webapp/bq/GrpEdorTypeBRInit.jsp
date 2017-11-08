<%

//程序名称：GrpEdorTypeBSInput.jsp
//程序功能：保险期间中断操作
//创建日期：2006-04-19 11:10:36
//创建人  ：万泽辉

%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

function initGrpEdor()
{
	document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
  document.all('GrpContNo').value = top.opener.document.all('OtherNo').value;
  document.all('EdorType').value = top.opener.document.all('EdorType').value;
  document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
  var strSQL = "select max(startdate) from lcgrpcontstate where grpcontno = '"+document.all('GrpContNo').value+"' and enddate is null and statetype='Available' and state = '1'";
  var trr = easyExecSql(strSQL);
  if(trr)
	{
			fm.OldEndDate.value = trr[0][0];
	}else {
		alert("查询合同中断日期失败!");
	}  
}

function initForm() 
{
 
  initGrpEdor();
  QueryEdorInfo();
	initLCGrpPolGrid();
	queryClick();
	initLCGrpContStateGrid();
	GrpPolSel();
}

//新增被保人列表
function initLCGrpPolGrid()
{
    var iArray = new Array();
      
      try
      {
		    iArray[0]=new Array();
		    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		    iArray[0][1]="30px";         			//列宽
		    iArray[0][2]=10;          				//列最大值
		    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		    iArray[1]=new Array();
		    iArray[1][0]="团体保单号";    				//列名1
		    iArray[1][1]="100px";            		//列宽
		    iArray[1][2]=100;            			//列最大值
		    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		    iArray[2]=new Array();
		    iArray[2][0]="团体险种号";         			//列名2
		    iArray[2][1]="100px";            		//列宽
		    iArray[2][2]=100;            			//列最大值
		    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		    iArray[3]=new Array();
		    iArray[3][0]="险种号码";         			//列名8
		    iArray[3][1]="60px";            		//列宽
		    iArray[3][2]=100;            			//列最大值
		    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		    iArray[4]=new Array();
		    iArray[4][0]="团单名称";         			//列名5
		    iArray[4][1]="100px";            		//列宽
		    iArray[4][2]=100;            			//列最大值
		    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
		
		    iArray[5]=new Array();
		    iArray[5][0]="生效日期";         		//列名6
		    iArray[5][1]="60px";            		//列宽
		    iArray[5][2]=100;            			//列最大值
		    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		    iArray[6]=new Array();
		    iArray[6][0]="中断日期";         		//列名6
		    iArray[6][1]="60px";            		//列宽
		    iArray[6][2]=100;            			//列最大值
		    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		    iArray[7]=new Array();
		    iArray[7][0]="保单终止日期";         		//列名6
		    iArray[7][1]="60px";            		//列宽
		    iArray[7][2]=100;            			//列最大值
		    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许		

		    iArray[8]=new Array();
		    iArray[8][0]="恢复日期";         		//列名6
		    iArray[8][1]="60px";            		//列宽
		    iArray[8][2]=100;            			//列最大值
		    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[9]=new Array();
		    iArray[9][0]="恢复后保单终止日期";         		//列名6
		    iArray[9][1]="80px";            		//列宽
		    iArray[9][2]=100;            			//列最大值
		    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		    

	      LCGrpPolGrid = new MulLineEnter( "fm" , "LCGrpPolGrid" ); 
	      //这些属性必须在loadMulLine前
	      LCGrpPolGrid.mulLineCount = 0;   
	      LCGrpPolGrid.displayTitle = 1;
		    LCGrpPolGrid.canSel = 0;
	      LCGrpPolGrid.hiddenPlus = 1;
	      LCGrpPolGrid.hiddenSubtraction = 1;
	      LCGrpPolGrid.loadMulLine(iArray);  
	      //LCGrpPolGrid.selBoxEventFuncName = "GrpPolSel";
	      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initLCGrpContStateGrid()
{
    var iArray = new Array();
      
      try
      {
		    iArray[0]=new Array();
		    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		    iArray[0][1]="30px";         			//列宽
		    iArray[0][2]=10;          				//列最大值
		    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		    iArray[1]=new Array();
		    iArray[1][0]="团体保单号";    				//列名1
		    iArray[1][1]="100px";            		//列宽
		    iArray[1][2]=100;            			//列最大值
		    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		    iArray[2]=new Array();
		    iArray[2][0]="团体险种号";         			//列名2
		    iArray[2][1]="100px";            		//列宽
		    iArray[2][2]=100;            			//列最大值
		    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		    iArray[3]=new Array();
		    iArray[3][0]="状态";         			//列名8
		    iArray[3][1]="60px";            		//列宽
		    iArray[3][2]=100;            			//列最大值
		    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		    iArray[4]=new Array();
		    iArray[4][0]="状态开始时间";         			//列名5
		    iArray[4][1]="80px";            		//列宽
		    iArray[4][2]=100;            			//列最大值
		    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
		
		    iArray[5]=new Array();
		    iArray[5][0]="状态结束时间";         		//列名6
		    iArray[5][1]="80px";            		//列宽
		    iArray[5][2]=100;            			//列最大值
		    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		

	      LCGrpContStateGrid = new MulLineEnter( "fm" , "LCGrpContStateGrid" ); 
	      //这些属性必须在loadMulLine前
	      LCGrpContStateGrid.mulLineCount = 0;   
	      LCGrpContStateGrid.displayTitle = 1;
		    //LCGrpPolGrid.canChk = 0;
		    LCGrpContStateGrid.canSel = 0;
	      LCGrpContStateGrid.hiddenPlus = 1;
	      LCGrpContStateGrid.hiddenSubtraction = 1;
	      LCGrpContStateGrid.loadMulLine(iArray);  
	      //LCGrpContStateGrid.selBoxEventFuncName = "GrpPolSel";
	      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
