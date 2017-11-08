<%
//程序名称：LLInqCourseQueryInit.jsp
//程序功能：调查过程信息查询页面初始化
//创建日期：2005-06-23
//创建人  ：yuejw
//更新记录：
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            
<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
    fm.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    fm.all('InqNo').value = nullToEmpty("<%= tInqNo %>");
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

function initInpBox()
{ 
    try
    {         
	   fm.ClmNo1.value = "";            
	   fm.InqNo1.value = "";              
	   fm.CouNo.value = ""; 	    
	   fm.InqDate.value = "";            
	   fm.InqMode.value = ""; 
	   fm.InqModeName.value = "";              
	   fm.InqSite.value = "";   
	   fm.InqByPer.value = "";  	   
	   fm.InqCourse.value = "";                 
	   fm.InqPer1.value = "";           
	   fm.InqPer2.value = "";           
	   fm.Remark.value = "";       	                          
    }
    catch(ex)
    {
        alert("在LLInqCourseQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }      
}

                             
function initForm()
{
    try
    {
        initInpBox(); 
        initLLInqCourseGrid();
        initLLInqCertificateGrid();
        initParam();
        LLInqCourseQuery();
     }
    catch(re)
    {
        alert("LLInqCourseQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
// 调查结论表的初始化
function initLLInqCourseGrid()
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
        iArray[1][1]="0px";                //列宽
        iArray[1][2]=100;                  //列最大值
        iArray[1][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="调查序号";             //列名
        iArray[2][1]="0px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[3]=new Array();
        iArray[3][0]="过程序号";             //列名
        iArray[3][1]="80px";                //列宽
        iArray[3][2]=100;                  //列最大值
        iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="调查日期";             //列名
        iArray[4][1]="80px";                //列宽
        iArray[4][2]=100;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="调查方式";             //列名
        iArray[5][1]="60px";                //列宽
        iArray[5][2]=100;                  //列最大值
        iArray[5][3]=0; 

        iArray[6]=new Array();
        iArray[6][0]="调查地点";             //列名
        iArray[6][1]="100px";                //列宽
        iArray[6][2]=200;                  //列最大值
        iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="被调查人";             //列名
        iArray[7][1]="100px";                //列宽
        iArray[7][2]=100;                  //列最大值
        iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[8]=new Array();
        iArray[8][0]="调查过程";             //列名
        iArray[8][1]="0px";                //列宽
        iArray[8][2]=100;                  //列最大值
        iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[9]=new Array();
        iArray[9][0]="调查机构";             //列名
        iArray[9][1]="50px";                //列宽
        iArray[9][2]=100;                  //列最大值
        iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[10]=new Array();
        iArray[10][0]="第一调查人";             //列名
        iArray[10][1]="80px";                //列宽
        iArray[10][2]=100;                  //列最大值
        iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[11]=new Array();
        iArray[11][0]="第一调查人";             //列名
        iArray[11][1]="0px";                //列宽
        iArray[11][2]=100;                  //列最大值
        iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[12]=new Array();
        iArray[12][0]="备注";             //列名
        iArray[12][1]="0px";                //列宽
        iArray[12][2]=100;                  //列最大值
        iArray[12][3]=3;                    //是否允许输入,1表示允许，0表示不允许
                             
        LLInqCourseGrid = new MulLineEnter( "fm" , "LLInqCourseGrid" ); 
        //这些属性必须在loadMulLine前
        LLInqCourseGrid.mulLineCount = 0;   
        LLInqCourseGrid.displayTitle = 1;
        LLInqCourseGrid.locked = 1;
        LLInqCourseGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLInqCourseGrid.selBoxEventFuncName = "LLInqCourseGridClick"; //点击RadioBox时响应的函数名
        LLInqCourseGrid.hiddenPlus=1;
        LLInqCourseGrid.hiddenSubtraction=1;
        LLInqCourseGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}


//调查过程单证信息
function initLLInqCertificateGrid()
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
        iArray[1][0]="单证序号";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;
           
        iArray[2]=new Array();
        iArray[2][0]="单证类型";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

		iArray[3]=new Array();
        iArray[3][0]="单证名称";
        iArray[3][1]="200px";
        iArray[3][2]=100;
        iArray[3][3]=0;
            
        iArray[4]=new Array()
        iArray[4][0]="原件标志";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;
//		iArray[4][10]="OriFlag";
//        iArray[4][11]="0|^0|原件^1|复印件";  
//        iArray[4][14]="0";
        
        iArray[5]=new Array();
        iArray[5][0]="张数";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;
//		iArray[5][14]=1;

		iArray[6]=new Array();
        iArray[6][0]="备注信息";
        iArray[6][1]="200px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        LLInqCertificateGrid = new MulLineEnter("fm","LLInqCertificateGrid");
        LLInqCertificateGrid.mulLineCount = 0;
        LLInqCertificateGrid.displayTitle = 1;
        LLInqCertificateGrid.locked = 0;
        LLInqCertificateGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
        LLInqCertificateGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LLInqCertificateGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        LLInqCertificateGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}
</script>
