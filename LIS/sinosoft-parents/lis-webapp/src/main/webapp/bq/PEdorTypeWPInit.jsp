<%
//PEdorTypeWPInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">  
//单击时查询

function initForm()
{
  try
  {
    initInpBox();
    initCustomerGrid();
    queryCustomerInfo();
    initPolGrid();
    initNoteGrid(); //追加记录
    getCustomerGrid();
    queryNoteGrid();
    showRiskInfo();   
  }
  catch(ex)
  {
    alert("无法初始化界面!原因可能是:主界面已关闭!");
    return;
  }
}

function initInpBox()
{ 
   
  	Edorquery();
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;   
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;  
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    //document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    showOneCodeName('PEdorType','EdorType','EdorTypeName');

}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在PEdorTypeWPInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        
 //客户基本信息列表
function initCustomerGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                  //列宽
        iArray[0][2]=30;                      //列最大值
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="客户号码";
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="90px";
        iArray[2][2]=50;
        iArray[2][3]=0;
        iArray[2][21]=2;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="90px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        iArray[3][21]=2;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="80px";
        iArray[4][2]=30;
        iArray[4][3]=0;
        iArray[4][21]=2;

        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=2;

        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="150px";
        iArray[7][2]=200;
        iArray[7][3]=0;

        CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
        //这些属性必须在loadMulLine前
        CustomerGrid.mulLineCount = 0;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.canSel=0;
        CustomerGrid.hiddenPlus = 1;
        CustomerGrid.hiddenSubtraction = 1;
        CustomerGrid.selBoxEventFuncName = "";
        CustomerGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorTypeFTInit.jsp --> initCustomerGrid 函数中发生异常:初始化界面错误！");
    }
}

function queryCustomerInfo()
{
  var tContNo=top.opener.document.all('ContNo').value;
	var strSQL="";
	if(tContNo!=null || tContNo !='')
	  {
	  strSQL = " Select a.appntno,'投保人',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
										+" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
										+" Union"
										+" Select i.insuredno,'被保人',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
										+" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";

	}
	else
	{
		alert('没有相应的投保人或被保人信息！');
	}
	var arrSelected = new Array();		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		  return false;
	}
	
	//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = CustomerGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL;
	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function initPolGrid()
{
    var iArray = new Array();
    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;
      
      iArray[1]=new Array();
      iArray[1][0]="险种代码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="50px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";					//列名1
      iArray[2][1]="200px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="被保人姓名";         			//列名2
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="基本保额";         		//列名8
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=30;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="份数";     //列名6
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="保费标准";         		//列名8
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=30;            			//列最大值
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="险种起期";         		//列名5
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      
      iArray[8]=new Array();
      iArray[8][0]="险种止期";         		//列名5
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前        
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel=0;
      PolGrid.hiddenPlus = 1; 
      PolGrid.hiddenSubtraction = 1;
      PolGrid.selBoxEventFuncName ="";
      PolGrid.loadMulLine(iArray);  
      PolGrid.detailInfo="单击显示详细信息";      
      PolGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
      
      }
}

function showRiskInfo()
{
    
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {        
        var strSQL = "select a.riskcode,b.riskname,a.insuredname,a.amnt,a.mult,a.prem,a.cvalidate,a.enddate from lcpol a,lmrisk b where a.riskcode = b.riskcode and a.contno = '"+tContNo+"' order by a.PolNo asc "; 
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult) 
        {
           return;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = PolGrid;    
        //保存SQL语句
        turnPage.strQuerySql = strSQL; 
        //设置查询起始位置
        turnPage.pageIndex = 0;  
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }

}

function initMRPolGrid()
{
    var iArray = new Array();
    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;
      
      iArray[1]=new Array();
      iArray[1][0]="险种代码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="50px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";					//列名1
      iArray[2][1]="200px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="被保人姓名";         			//列名2
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="基本保额";         		//列名8
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=30;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="份数";     //列名6
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="保费标准";         		//列名8
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=30;            			//列最大值
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="险种起期";         		//列名5
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      
      iArray[8]=new Array();
      iArray[8][0]="险种止期";         		//列名5
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    

      MRPolGrid = new MulLineEnter( "fm" , "MRPolGrid" ); 
      //这些属性必须在loadMulLine前        
      MRPolGrid.mulLineCount = 0;   
      MRPolGrid.displayTitle = 1;
      MRPolGrid.canSel=0;
      MRPolGrid.hiddenPlus = 1; 
      MRPolGrid.hiddenSubtraction = 1;
      MRPolGrid.selBoxEventFuncName ="";
      MRPolGrid.loadMulLine(iArray);  
      MRPolGrid.detailInfo="单击显示详细信息";      
      MRPolGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
      
      }
}

//追加记录
function initNoteGrid()
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
        iArray[1][0]="险种代码";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="险种名称";
        iArray[2][1]="200px";
        iArray[2][2]=300;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="被保人姓名";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="追加金额";
        iArray[4][1]="70px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        iArray[4][21]=3;

        iArray[5]=new Array();
        iArray[5][0]="险种号";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="初始管理费";
        iArray[6][1]="90px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        iArray[6][21]=3;

        iArray[7]=new Array();
        iArray[7][0]="初始管理费收取比例";
        iArray[7][1]="110px";
        iArray[7][2]=110;
        iArray[7][3]=0;
        iArray[7][21]=3;

        NoteGrid = new MulLineEnter("fm", "NoteGrid");
        //这些属性必须在loadMulLine前
        NoteGrid.mulLineCount = 0;
        NoteGrid.displayTitle = 1;
        NoteGrid.canSel=1;
        NoteGrid.hiddenPlus=1;
        NoteGrid.hiddenSubtraction=1;
        //NoteGrid.selBoxEventFuncName ="getInsuAccb" ;
        NoteGrid.loadMulLine(iArray);
     }
     catch(ex)
     {
           alert("在PEdorTypeWPInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
     }
}

function showMRRiskInfo()
{
    
    var tContNo=document.all("ContNo").value;
    var tEdorNo=document.all("EdorNo").value;
    if(tContNo!=null&&tContNo!="")
    {        
        var strSQL = "select distinct a.riskcode,b.riskname,a.insuredname,a.amnt,a.mult,a.prem,a.cvalidate,a.enddate from lppol a,lmrisk b where a.riskcode = b.riskcode and a.contno = '"+tContNo+"' and a.edorno = '"+tEdorNo+"'"; 
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult) 
        {
           return;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = MRPolGrid;    
        //保存SQL语句
        turnPage.strQuerySql = strSQL; 
        //设置查询起始位置
        turnPage.pageIndex = 0;  
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

    }

}






</script>