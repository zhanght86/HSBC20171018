<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
	function initForm()
	{
		  try
		  {
			initPersonAgeGrid();
		  }
		  catch(re)
		  {
		    alert("GroupPolInputInit.jsp-->InitForm1函数中发生异常:初始化界面错误!"+re);
		  }
	}
	function initPersonAgeGrid()
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
			iArray[1][0]="开始年龄";         		//列名
			iArray[1][1]="60px";            		//列宽
			iArray[1][2]=3;            			//列最大值
			iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许
			
			iArray[2]=new Array();
			iArray[2][0]="终止年龄";         		//列名
			iArray[2][1]="60px";            		//列宽
			iArray[2][2]=3;            			//列最大值
			iArray[2][3]=1;
	
			iArray[3]=new Array();
			iArray[3][0]="在职男性";         		//列名
			iArray[3][1]="60px";            		//列宽
			iArray[3][2]=9;            			//列最大值
			iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
	
			
			iArray[4]=new Array();
			iArray[4][0]="在职女性";         		//列名
			iArray[4][1]="60px";            		//列宽
			iArray[4][2]=9;            			//列最大值
			iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
			
			iArray[5]=new Array(); 
			iArray[5][0]="退休男性";   
			iArray[5][1]="60px";   
			iArray[5][2]=9;        
			iArray[5][3]=1;
			
			iArray[6]=new Array(); 
			iArray[6][0]="退休女性";   
			iArray[6][1]="60px";   
			iArray[6][2]=9;        
			iArray[6][3]=1;
			
			iArray[7]=new Array(); 
			iArray[7][0]="配偶男性";   
			iArray[7][1]="60px";   
			iArray[7][2]=9;        
			iArray[7][3]=1;
			
			iArray[8]=new Array(); 
			iArray[8][0]="配偶女性";   
			iArray[8][1]="60px";   
			iArray[8][2]=9;        
			iArray[8][3]=1;
			
			iArray[9]=new Array(); 
			iArray[9][0]="子女男性";   
			iArray[9][1]="60px";   
			iArray[9][2]=9;        
			iArray[9][3]=1;
			
			iArray[10]=new Array(); 
			iArray[10][0]="子女女性";   
			iArray[10][1]="60px";   
			iArray[10][2]=9;        
			iArray[10][3]=1;
			
			iArray[11]=new Array(); 
			iArray[11][0]="其他男性";   
			iArray[11][1]="60px";   
			iArray[11][2]=9;        
			iArray[11][3]=1;
			
			iArray[12]=new Array(); 
			iArray[12][0]="其他女性";   
			iArray[12][1]="60px";   
			iArray[12][2]=9;        
			iArray[12][3]=1;
			
			PersonAgeGrid = new MulLineEnter( "fm" , "PersonAgeGrid" ); 
			//这些属性必须在loadMulLine前
			PersonAgeGrid.mulLineCount = 1;   
			PersonAgeGrid.displayTitle = 1;
			//ImpartGrid.tableWidth   ="500px";
			PersonAgeGrid.loadMulLine(iArray);  
			
			//这些操作必须在loadMulLine后面
			//ImpartGrid.setRowColData(1,1,"asdf");
	    }
	    catch(ex)
	    {
	    	  alert(ex);
	    }
	}
						
</script>
