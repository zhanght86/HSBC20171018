<script language="JavaScript">
var loadFlag=<%=tLoadFlag%>;
//初始化输入框
function initInpBox() { 
  fm.PrtNo.value=prtNo;
  fm.ManageCom.value=ManageCom;
  fm.MissionID.value=tMissionID;
  fm.SubMissionID.value=tSubMissionID;
}

// 下拉框的初始化
function initSelBox(){

}

//表单初始化
function initForm(){
  initInpBox();
  initImpartGrid();
  initDetail();
}
// 告知信息列表的初始化
function initImpartGrid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户名称";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="客户风险保额";         		//列名
      iArray[3][1]="200px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="工作流任务号";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="工作流子任务号";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount = 0;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}

</script>
