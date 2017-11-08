<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDRiskRoleInit.jsp
  //程序功能：险种角色定义
  //创建日期：2009-3-13
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		initMulline9Grid();
		queryMulline9Grid();
		initMulline10Grid();
		queryMulline10Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		isshowbutton();
	}
	catch(re){
		myAlert("PDRiskRoleInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}
function updateDisplayState()
{
 // rowCount:显示的字段数量
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:下拉项对应的selectcode的数组
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1); 

 var j = 0;
 
 for(var i = 0; i < rowCount; i++)
 {
   if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,fm.all("RiskCode").value,null,"readonly"); 		 
	 }
	 else 
	 {
	 	var selNo = Mulline10Grid.getSelNo();
 		if(selNo==0 || selNo == null)
 		{
 			Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");    
 		}else
 		{
 			/*if(Mulline9Grid.getRowColData(i,2) == "RISKROLE")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,2),null,"readonly"); 		 
	 		}else if(Mulline9Grid.getRowColData(i,2) == "RISKROLESEX")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,3),null,"readonly"); 		 
	 		}else if(Mulline9Grid.getRowColData(i,2) == "RISKROLENO")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,4),null,"readonly"); 		 
	 		}else
	 		{ 			
	 	 		var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+fm.all("tableName").value+" where riskcode = '"+fm.all('RiskCode').value+"' and RISKROLE = '"+Mulline10Grid.getRowColData(selNo-1,2)+"'and RISKROLESEX = '"+Mulline10Grid.getRowColData(selNo-1,3)+"'and RISKROLENO = '"+Mulline10Grid.getRowColData(selNo-1,4)+"'";
   	 		var tContent = easyExecSql(tDefaultValueSQL);
   	 
   	 		var cData = null;
   	 		if(tContent!=null&&tContent[0][0]!="null")
   	 		{
   	 	 		cData = tContent[0][0];
   	 		}
     		Mulline9Grid.setRowColDataCustomize(i,4,cData,null,rowcode[i][1]);*/
     		

    	 	var selNo = Mulline10Grid.getSelNo();
     		if(selNo==0 || selNo == null)
     		{
     			Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");    
     		}else
     		{
     			if(Mulline9Grid.getRowColData(i,2) == "RISKROLE")
    	 		{
    		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,2),null,"readonly"); 		 
    	 		}else if(Mulline9Grid.getRowColData(i,2) == "RISKROLESEX")
    	 		{
    		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,3),null,"readonly"); 		 
    	 		}else if(Mulline9Grid.getRowColData(i,2) == "RISKROLENO")
    	 		{
    		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,4),null,"readonly"); 		 
    	 		}else
    	 		{ 			
//     	 	 		var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+document.all("tableName").value+" where riskcode = '"+document.all('RiskCode').value+"' and RISKROLE = '"+Mulline10Grid.getRowColData(selNo-1,2)+"'and RISKROLESEX = '"+Mulline10Grid.getRowColData(selNo-1,3)+"'and RISKROLENO = '"+Mulline10Grid.getRowColData(selNo-1,4)+"'";
       	 		 mySql=new SqlClass();
    	mySql.setResourceName("productdef.PDRiskRoleInputSql"); //指定使用的properties文件名
    	mySql.setSqlId("PDRiskRoleInputSql5");//指定使用的Sql的id
    	mySql.addSubPara(Mulline9Grid.getRowColData(i,2));//指定传入的参数
    	mySql.addSubPara(document.all("tableName").value);//指定传入的参数
    	mySql.addSubPara(document.all("RiskCode").value);//指定传入的参数
    	mySql.addSubPara(Mulline10Grid.getRowColData(selNo-1,2));//指定传入的参数
    	mySql.addSubPara(Mulline10Grid.getRowColData(selNo-1,3));//指定传入的参数
    	mySql.addSubPara(Mulline10Grid.getRowColData(selNo-1,4));//指定传入的参数
    	var tDefaultValueSQL = mySql.getString();
       	 		var tContent = easyExecSql(tDefaultValueSQL);
       	 
       	 		var cData = null;
       	 		if(tContent!=null&&tContent[0][0]!="null")
       	 		{
       	 	 		cData = tContent[0][0];
       	 		}
         		Mulline9Grid.setRowColDataCustomize(i,4,cData,null,rowcode[i][1]);
         	}
        }
      
     	}
    }
  }
}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="属性名称";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="属性代码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="属性数据类型";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="属性值";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="字段说明";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="业务人员备注";
		iArray[6][1]="0px";
		iArray[6][2]=0;
		iArray[6][3]=3;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=0;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=60;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种编码";
		iArray[1][1]="60px";
		iArray[1][2]=60;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="角色";
		iArray[2][1]="40px";
		iArray[2][2]=60;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="性别";
		iArray[3][1]="30px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="序号（级别）";
		iArray[4][1]="30px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="险种版本";
		iArray[5][1]="0px";
		iArray[5][2]=100;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="最小年龄单位";
		iArray[6][1]="60px";
		iArray[6][2]=60;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="最小年龄";
		iArray[7][1]="60px";
		iArray[7][2]=60;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="最大年龄单位";
		iArray[8][1]="60px";
		iArray[8][2]=60;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="最大年龄";
		iArray[9][1]="60px";
		iArray[9][2]=60;
		iArray[9][3]=0;


		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.selBoxEventFuncName ="updateDisplayState";

		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
