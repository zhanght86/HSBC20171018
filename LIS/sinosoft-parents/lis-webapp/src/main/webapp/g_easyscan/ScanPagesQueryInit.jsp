<%
/***************************************************************
 * <p>ProName：ScanPagesQueryInit.jsp</p>
 * <p>Title：影像查询</p>
 * <p>Description：影像查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 刘锦祥
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<script language="JavaScript">
	
function initForm() {

	try {
		initScanPagesGrid();
		divPages.style.display = 'none';
		divPages1.style.display = 'none';
		scanQuery();
	} catch(ex) {
		alert("初始化界面错误!");
	}
}

function initScanPagesGrid() {
	try {
		var iArray = new Array();
		var i=0;
		iArray[i]=new Array();
		iArray[i][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[i][1]="40px";         			//列宽
		iArray[i][2]=10;          			    //列最大值

		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		
		iArray[i]=new Array();
		iArray[i][0]="业务号码";         			//列名
		iArray[i][1]="120px";            		//列宽
		iArray[i][2]=80;            			//列最大值

		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="业务类型编码";         		//列名
		iArray[i][1]="80px";            		//列宽
		iArray[i][2]=90;            			//列最大值

		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="业务类型";         		//列名
		iArray[i][1]="100px";            		//列宽
		iArray[i][2]=90;            			//列最大值

		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="单证类型编码";    	        //列名
		iArray[i][1]="80px";            		//列宽
		iArray[i][2]=90;            			//列最大值

		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="单证类型";    	        //列名
		iArray[i][1]="150px";            		//列宽
		iArray[i][2]=90;            			//列最大值

		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="单证号";    	        //列名
		iArray[i][1]="0px";            		//列宽
		iArray[i][2]=90;            			//列最大值

		iArray[i++][3]=3;
		
		ScanPagesGrid = new MulLineEnter("fm","ScanPagesGrid");
		ScanPagesGrid.mulLineCount = 0;
		ScanPagesGrid.displayTitle = 1;
		ScanPagesGrid.locked = 0;
		ScanPagesGrid.canSel = 1;                   // 单选按钮：1 显示 ；0 隐藏（缺省值）
		ScanPagesGrid.selBoxEventFuncName = "showPages"; //响应的函数名，不加扩号

		ScanPagesGrid.hiddenPlus=1;        //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		ScanPagesGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
		ScanPagesGrid.loadMulLine(iArray);
	
	} catch(ex) {
		alert(ex);
	}  
}

</script>
