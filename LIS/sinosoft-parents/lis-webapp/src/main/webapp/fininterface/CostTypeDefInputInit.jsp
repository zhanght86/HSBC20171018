<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//创建日期：2008-08-11
//创建人  ：ZhongYan
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
             
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {          	
  	document.all('VersionNo').value=VersionNo;
  	//alert(document.all('VersionNo').value);
  	document.all('CertificateID').value=CertificateID;  	
  	//alert(document.all('CertificateID').value);
  	//状态State这个字段在FICostTypeDef（凭证费用类型定义）定义完毕之后置为空；在FICostDataAcquisitionDef（凭证费用数据采集定义）完毕之后置为01-定义完毕
  	document.all('State').value = '02'; 	
  	
  	//当版本状态不为02-维护的时候，增删改按钮为灰色		
  	//alert(VersionState);
		if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}                    
  	//document.all('VersionNo').readOnly=false;       
  }
  catch(ex)
  {
    alert("在CostTypeDefInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("在CostTypeDefInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                   


function initForm()
{
  try
  {    
    initInpBox();    
    initSelBox();
		initCostTypeDefGrid();  
  	initCostTypeDefQuery();    
  }
  
  catch(re)
  {
    alert("在CostTypeDefInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


 function initCostTypeDefGrid()
{
	var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="版本编号";    	//列名
      iArray[1][1]="110px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="凭证类型编号";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[3]=new Array();
      iArray[3][0]="费用类型编码";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[4]=new Array();
      iArray[4][0]="费用类型名称";         			//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[5]=new Array();
      iArray[5][0]="借贷标志";         			//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="借贷标志";         			//列名
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[7]=new Array();
      iArray[7][0]="科目类型编码";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
  
      iArray[8]=new Array();
      iArray[8][0]="科目类型名称";         		//列名
      iArray[8][1]="150px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      iArray[9]=new Array();
      iArray[9][0]="费用类型描述";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;     		    			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      //iArray[9]=new Array();
      //iArray[9][0]="状态";         		//列名
      //iArray[9][1]="40px";            		//列宽
      //iArray[9][2]=100;            			//列最大值
      //iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      

      CostTypeDefGrid = new MulLineEnter( "document" , "CostTypeDefGrid" ); 
      CostTypeDefGrid.mulLineCount = 5;   
      CostTypeDefGrid.displayTitle = 1;
      CostTypeDefGrid.canSel=1;
      //选中一行自动给输入框赋值
      CostTypeDefGrid.selBoxEventFuncName = "ShowCostTypeDef";			      
      CostTypeDefGrid.locked = 1;	
			CostTypeDefGrid.hiddenPlus = 1;
			CostTypeDefGrid.hiddenSubtraction = 1;

      CostTypeDefGrid.loadMulLine(iArray);  
      CostTypeDefGrid.detailInfo="单击显示详细信息";
      //CostTypeDefGrid.detailClick=reportDetailClick;     
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

</script>
