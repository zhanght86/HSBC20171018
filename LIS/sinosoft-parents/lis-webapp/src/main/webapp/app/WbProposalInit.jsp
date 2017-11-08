<%
//程序名称：ProposalInput.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox() { 
  try {  
                                   
	  // 保单信息部分
   // document.all('ProposalNo').value = '';
    document.all('PrtNo').value = '';
    document.all('ManageCom').value = '';
    document.all('SaleChnl').value = '';
    document.all('AgentCom').value = '';
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = '';
    document.all('Handler').value = '';
    document.all('AgentCode1').value = '';
 

  //  document.all('ContNo').value = '';
    document.all('GrpPolNo').value = '';
    document.all('MainPolNo').value = '';
    document.all('FirstPayDate').value = '';
    document.all('Lang').value = '';
    document.all('Currency').value = '';
    document.all('DisputedFlag').value = '';
    document.all('AgentPayFlag').value = '';
    document.all('AgentGetFlag').value = '';
    document.all('Remark').value = '';
      

  	// 个人投保人信息部分   
    document.all( 'AppntCustomerNo' ).value          = "";
  	document.all( 'AppntName' ).value                = "";
  	document.all( 'AppntSex' ).value                 = "";
  	document.all( 'AppntBirthday' ).value            = "";
  	document.all( 'AppntAge' ).value                 = "";
  	document.all( 'AppntIDType' ).value              = "";
  	document.all( 'AppntIDNo' ).value                = "";
  	document.all( 'AppntNativePlace' ).value         = "";
  	document.all( 'AppntRgtAddress' ).value          = "";
  	document.all( 'AppntMarriage' ).value            = "";
  	document.all( 'AppntNationality' ).value         = "";
  	document.all( 'AppntDegree' ).value              = "";
  	document.all( 'AppntRelationToInsured' ).value   = "";
  	document.all( 'AppntPostalAddress' ).value       = "";
  	document.all( 'AppntZipCode' ).value             = "";
  	document.all( 'AppntPhone' ).value               = "";
  	document.all( 'AppntMobile' ).value              = "";
  	document.all( 'AppntEMail' ).value               = "";
  	document.all( 'AppntGrpName' ).value             = "";
  	document.all( 'AppntGrpPhone' ).value            = "";
  	document.all( 'AppntGrpAddress' ).value          = "";
  	document.all( 'AppntGrpZipCode' ).value          = "";
  	document.all( 'AppntWorkType' ).value            = "";
  	document.all( 'AppntPluralityType' ).value       = "";
  	document.all( 'AppntOccupationType' ).value      = "";
  	document.all( 'AppntOccupationCode' ).value      = "";
  	document.all( 'SmokeFlag' ).value                = "";

  	// 集体投保人信息部分    
  	document.all('GrpNo').value = '';
    document.all('GrpName').value = '';
    document.all('LinkMan').value = '';
    document.all('GrpRelation').value = '';
    document.all('GrpPhone').value = '';
    document.all('GrpFax').value = '';
    document.all('GrpEMail').value = '';
    document.all('GrpZipCode').value = '';
    document.all('GrpAddress').value = '';

	  // 被保人信息部分  
  	document.all( 'CustomerNo' ).value          = "";
  	document.all( 'Name' ).value                = "";
  	document.all( 'Sex' ).value                 = "";
  	document.all( 'Birthday' ).value            = "";
  	document.all( 'Age' ).value                 = "";
  	document.all( 'IDType' ).value              = "";
  	document.all( 'IDNo' ).value                = "";
  	document.all( 'NativePlace' ).value         = "";
  	document.all( 'RgtAddress' ).value          = "";
  	document.all( 'Marriage' ).value            = "";
  	document.all( 'Nationality' ).value         = "";
  	document.all( 'Degree' ).value              = "";
  	document.all( 'SmokeFlag' ).value           = "";
  	document.all( 'PostalAddress' ).value       = "";
  	document.all( 'ZipCode' ).value             = "";
  	document.all( 'Phone' ).value               = "";
  	document.all( 'Mobile' ).value              = "";
  	document.all( 'EMail' ).value               = "";
  	document.all( 'GrpName' ).value             = "";
  	document.all( 'GrpPhone' ).value            = "";
  	document.all( 'GrpAddress' ).value          = "";
  	document.all( 'GrpZipCode' ).value          = "";	
  	document.all( 'WorkType' ).value            = "";
  	document.all( 'PluralityType' ).value       = "";
  	document.all( 'OccupationType' ).value      = "";
  	document.all( 'OccupationCode' ).value      = "";
  
    // 险种信息部分
    document.all('RiskCode').value              = '';
    document.all('RiskVersion').value           = '';
    document.all('CValiDate').value             = '';
    document.all('PolApplyDate').value          = '';    
    document.all('HealthCheckFlag').value       = '';
    document.all('Years').value                 = '';
    document.all('PayYears').value              = '';
    document.all('PayIntv').value               = '';
    document.all('OutPayFlag').value            = '';
    document.all('PayLocation').value           = '';
    document.all('BankCode').value              = '';
    document.all('countName').value             = '';
    document.all('BankAccNo').value             = '';
    document.all('LiveGetMode').value           = '';
    document.all('getTerm').value               = '';
    document.all('getIntv').value               = '';
    document.all('BonusGetMode').value          = '';
    document.all('Mult').value                  = '';
    document.all('Prem').value                  = '';
    document.all('Amnt').value                  = '';
   
    document.all('PayEndYear').value = '';
    document.all('PayEndYearFlag').value = '';
    document.all('GetYear').value = '';
    document.all('GetYearFlag').value = '';
    document.all('GetStartType').value = '';
    document.all('InsuYear').value = '';
    document.all('InsuYearFlag').value = '';
    document.all('AutoPayFlag').value = '';
    document.all('InterestDifFlag').value = '';
    document.all('SubFlag').value = '';
   
    
  } catch(ex) {
    //alert("在ProposalInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  } 
  
  //附加字段
  try{
  document.all('StandbyFlag1').value = '';
  document.all('StandbyFlag2').value = '';
  document.all('StandbyFlag3').value = '';
  
  }     
  catch(ex){alert(ex); }
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在ProposalInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

// 被保人信息列表的初始化
function initSubInsuredGrid()
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
      iArray[1][0]="连带被保人客户号";    	//列名
      iArray[1][1]="180px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][7]="showSubInsured";
      iArray[1][8]="['SubInsured']";        //是否使用自己编写的函数 

      iArray[2]=new Array();
      iArray[2][0]="姓名";         			//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="性别";         			//列名
      iArray[3][1]="140px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="出生日期";         		//列名
      iArray[4][1]="140px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="与被保人关系";         		//列名
      iArray[5][1]="160px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][4]="Relation";

      SubInsuredGrid = new MulLineEnter( "fm" , "SubInsuredGrid" ); 
      //这些属性必须在loadMulLine前
      SubInsuredGrid.mulLineCount = 0;   
      SubInsuredGrid.displayTitle = 1;
      //SubInsuredGrid.tableWidth = 200;
      SubInsuredGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //SubInsuredGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 受益人信息列表的初始化
<%for(int i=1;i<=3;i++){%>
function initBnf<%=i%>Grid() {                               
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="序号"; 			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";		//列宽
    iArray[0][2]=10;			//列最大值
    iArray[0][3]=0;			//是否允许输入,1表示允许，0表示不允许
    
    iArray[1]=new Array();
    iArray[1][0]="被保人序号";         		//列名
    iArray[1][1]="0px";            		//列宽
    iArray[1][2]=80;            			//列最大值
    iArray[1][3]=0; 
      
    iArray[2]=new Array();
    iArray[2][0]="主险序号";         		//列名
    iArray[2][1]="40px";            		//列宽
    iArray[2][2]=80;            			//列最大值
    iArray[2][3]=2; 
    iArray[2][9]="主险序号|notnull";
    iArray[2][10]="MainRiskNo";
    iArray[2][11]="0|^-1|非多主险|^0|所有主险|^1|第一主险|^2|第二主险|^3|第三主险";
  
    iArray[3]=new Array();
    iArray[3][0]="受益人类别"; 		//列名
    iArray[3][1]="60px";		//列宽
    iArray[3][2]=40;			//列最大值
    iArray[3][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[3][4]="BnfType";
    iArray[3][9]="受益人类别|notnull&code:BnfType";
    iArray[3][19]=1;
   
    iArray[4]=new Array();
    iArray[4][0]="姓名"; 	//列名
    iArray[4][1]="80px";		//列宽
    iArray[4][2]=80;			//列最大值
    iArray[4][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[4][9]="受益人姓名|notnull&len<=20"; //校验
  
    iArray[5]=new Array();
    iArray[5][0]="证件类型"; 		//列名
    iArray[5][1]="40px";		//列宽
    iArray[5][2]=40;			//列最大值
    iArray[5][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[5][4]="IDType";
    iArray[5][9]="受益人证件类型|code:IDType";
    iArray[5][19]=1;
  
    iArray[6]=new Array();
    iArray[6][0]="证件号码"; 		//列名
    iArray[6][1]="140px";		//列宽
    iArray[6][2]=100;			//列最大值
    iArray[6][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[6][9]="受益人证件号码|len<=20";

    iArray[7]=new Array();
    iArray[7][0]="与被保人关系"; 	//列名
    iArray[7][1]="60px";		//列宽
    iArray[7][2]=60;			//列最大值
    iArray[7][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[7][4]="Relation";
    iArray[7][9]="受益人与被保人关系|notnull&code:relation";
    iArray[7][19]=1;
  
    iArray[8]=new Array();
    iArray[8][0]="受益比例"; 		//列名
    iArray[8][1]="40px";		//列宽
    iArray[8][2]=40;			//列最大值
    iArray[8][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[8][9]="受益比例|notnull&num&len<=10";
  
    iArray[9]=new Array();
    iArray[9][0]="受益顺序"; 		//列名
    iArray[9][1]="40px";		//列宽
    iArray[9][2]=40;			//列最大值
    iArray[9][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[9][4]="OccupationType";
    iArray[9][9]="受益顺序|notnull&code:OccupationType";
    iArray[9][19]=1;
  
    iArray[10]=new Array();
    iArray[10][0]="住址（填序号）"; 		//列名
    iArray[10][1]="160px";		//列宽
    iArray[10][2]=100;			//列最大值
    iArray[10][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[10][9]="受益人住址|len<=80";
    
    iArray[11]=new Array();
    iArray[11][0]="受益人证件有效期"; 		//列名
    iArray[11][1]="160px";		//列宽
    iArray[11][2]=100;			//列最大值
    iArray[11][3]=1;			//是否允许输入,1表示允许，0表示不允许
    //iArray[11][9]="";
  
    iArray[12]=new Array();
    iArray[12][0]="速填"; 		//列名
    iArray[12][1]="30px";		//列宽
    iArray[12][2]=30;			//列最大值
    iArray[12][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[12][10]="customertype<%=i%>";
    iArray[12][11]="0|^0|投保人|^1|被保人";
  
    Bnf<%=i%>Grid = new MulLineEnter( "fm" , "Bnf<%=i%>Grid" ); 
    //这些属性必须在loadMulLine前
    Bnf<%=i%>Grid.mulLineCount = 1; 
    Bnf<%=i%>Grid.displayTitle = 1;
    Bnf<%=i%>Grid.loadMulLine(iArray); 
  
    //这些操作必须在loadMulLine后面
    //BnfGrid.setRowColData(0,8,"1");
    //BnfGrid.setRowColData(0,9,"1");
  } catch(ex) {
    alert(ex);
  }
}
<%}%>

// 生存保险金、年金、红利处理方式信息列表的初始化
<%for(int i=1;i<=3;i++){%>
function initDealType<%=i%>Grid() {                               
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="序号"; 			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";		//列宽
    iArray[0][2]=10;			//列最大值
    iArray[0][3]=0;			//是否允许输入,1表示允许，0表示不允许
    
    iArray[1]=new Array();
    iArray[1][0]="被保人序号";         		//列名
    iArray[1][1]="0px";            		//列宽
    iArray[1][2]=80;            			//列最大值
    iArray[1][3]=0; 
      
    iArray[2]=new Array();
    iArray[2][0]="主险序号";         		//列名
    iArray[2][1]="40px";            		//列宽
    iArray[2][2]=80;            			//列最大值
    iArray[2][3]=2; 
    iArray[2][9]="主险序号|notnull";
    iArray[2][10]="MainRiskNo";
    iArray[2][11]="0|^-1|非多主险|^1|第一主险|^2|第二主险|^3|第三主险";
  
    iArray[3]=new Array();
    iArray[3][0]="领取(起领)年龄"; 		//列名
    iArray[3][1]="60px";		//列宽
    iArray[3][2]=40;			//列最大值
    iArray[3][3]=1;			//是否允许输入,1表示允许，0表示不允许        
   
    iArray[4]=new Array();
    iArray[4][0]="起领期间单位"; 	//列名
    iArray[4][1]="60px";		//列宽
    iArray[4][2]=80;			//列最大值
    iArray[4][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[4][10]="GetYearFlag<%=i%>";
    iArray[4][11]="0|^Y|年|^A|岁";
  
    iArray[5]=new Array();
    iArray[5][0]="领取期间"; 		//列名
    iArray[5][1]="40px";		//列宽
    iArray[5][2]=40;			//列最大值
    iArray[5][3]=1;			//是否允许输入,1表示允许，0表示不允许
  
    iArray[6]=new Array();
    iArray[6][0]="年金领取方式"; 		//列名
    iArray[6][1]="60px";		//列宽
    iArray[6][2]=100;			//列最大值
    iArray[6][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[6][4]="dsgetdutykind";
    //iArray[6][11]="0|^1|按月领取|^2|按年领取^3|一次性领取^4|其它";

    iArray[7]=new Array();
    iArray[7][0]="生存金领取方式"; 	//列名
    iArray[7][1]="60px";		//列宽
    iArray[7][2]=60;			//列最大值
    iArray[7][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[7][4]="LiveGetMode";
  
    iArray[8]=new Array();
    iArray[8][0]="红利领取方式"; 		//列名
    iArray[8][1]="60px";		//列宽
    iArray[8][2]=60;			//列最大值
    iArray[8][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[8][4]="BonusGetMode";
  
  
    DealType<%=i%>Grid = new MulLineEnter( "fm" , "DealType<%=i%>Grid" ); 
    //这些属性必须在loadMulLine前
    DealType<%=i%>Grid.mulLineCount = 1; 
    DealType<%=i%>Grid.displayTitle = 1;
    DealType<%=i%>Grid.loadMulLine(iArray); 
  
    //这些操作必须在loadMulLine后面
    //BnfGrid.setRowColData(0,8,"1");
    //BnfGrid.setRowColData(0,9,"1");
  } catch(ex) {
    alert(ex);
  }
}
<%}%>

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
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="ImpartVer";                     //新版投保单对应告知
      iArray[1][5]="1";
      iArray[1][6]="0";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][15]="1";
      iArray[1][16]="1 and code not in (#101#,#102#,#103#,#105#,#106#)";
      iArray[1][18]=300;

      iArray[2]=new Array();             
      iArray[2][0]="告知编码";         	
      iArray[2][1]="60px";            	
      iArray[2][2]=60;            		
      iArray[2][3]=2;              		
      iArray[2][4]="ImpartCode";         
      iArray[2][5]="2|3|4";                
      iArray[2][6]="0|1|2";                
      iArray[2][9]="告知编码|len<=4";    
      iArray[2][15]="ImpartVer";     //查询数据库中对应列名
      iArray[2][17]="1";             //依赖第6列的值，即传入的查询条件为 ImpartVer ='001'  

      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

      iArray[5]=new Array();
      iArray[5][0]="告知客户类型";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=90;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][10]="CustomerNoType";
      iArray[5][11]="0|^0|投保人|^1|被保人|^2|业务员";      
      
      iArray[6]=new Array();                           //隐藏列
      iArray[6][0]="客户内部号码";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=2;  
      iArray[6][10]="CustomerToInsured";
      iArray[6][11]="0|^1|被保人|^2|第2被保人|^3|第3被保人";   
      
      iArray[7]=new Array();                           //隐藏列
      iArray[7][0]="codetype";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=10;            			//列最大值
      iArray[7][3]=1; 


      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 1;   
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}




// 险种列表的初始化
<%for(int i=1;i<=3;i++){%>
	<%for(int j=1;j<=3;j++){%>
function initRisk<%=i%><%=j%>Grid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[1]=new Array();
      iArray[1][0]="被保人序号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=80;            			//列最大值
      iArray[1][3]=0; 
      
      iArray[2]=new Array();
      iArray[2][0]="主险序号";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=80;            			//列最大值
      iArray[2][3]=2; 
      iArray[2][10]="MainRiskNo";
      iArray[2][11]="0|^-1|非多主险|^1|第一主险|^2|第二主险|^3|第三主险";
      
      iArray[3]=new Array();
      iArray[3][0]="险种编码";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=80;            			//列最大值
      iArray[3][3]=2; 
      iArray[3][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[3][5]="3|4";  
      iArray[3][6]="0|1";            	                //引用代码对应第几列，'|'为分割符
      iArray[3][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[3][18]=250;
      iArray[3][19]= 0 ;
      
      iArray[4]=new Array();
      iArray[4][0]="险种名称";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=80;            			//列最大值
      iArray[4][3]=1; 

      iArray[5]=new Array();             
      iArray[5][0]="保险金额";         	
      iArray[5][1]="60px";            	
      iArray[5][2]=60;            		
      iArray[5][3]=1;              		
      
      iArray[6]=new Array();
      iArray[6][0]="投保份数";         		//列名
      iArray[6][1]="40px";            		//列宽
      iArray[6][2]=40;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许      
     
      iArray[7]=new Array();
      iArray[7][0]="保险年期/年龄";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=70;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="保险年期/年龄标志";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=70;            			//列最大值
      iArray[8][3]=2;              			//2表示代码选择
      iArray[8][4]="GetYearFlag";       //引用代码:
      
      iArray[9]=new Array();
      iArray[9][0]="缴费年期/年龄";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=70;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="缴费年期/年龄标志";         		//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=70;            			//列最大值
      iArray[10][3]=2;              			//2表示代码选择
      iArray[10][4]="GetYearFlag";       //引用代码:      
      
      iArray[11]=new Array();
      iArray[11][0]="保费";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="职业加费";         		//列名
      iArray[12][1]="100px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=1;              			//是否允许输入,1表示允许，0表示不允许
　
      iArray[13]=new Array();
      iArray[13][0]="填单保费";         		//列名
      iArray[13][1]="100px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      Risk<%=i%><%=j%>Grid = new MulLineEnter( "fm" , "Risk<%=i%><%=j%>Grid" ); 
      //这些属性必须在loadMulLine前
      Risk<%=i%><%=j%>Grid.mulLineCount = 1;   
      Risk<%=i%><%=j%>Grid.displayTitle = 1;
      //RiskGrid.tableWidth   ="500px";
      Risk<%=i%><%=j%>Grid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //RiskGrid.setRowColData(0,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}
<%}}%>



// 特别约定信息列表的初始化
function initSpecGrid() {                               
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="0px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="特约类型";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="特约编码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="特约内容";         		//列名
      iArray[3][1]="540px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="特约内容|len<=255";
      
      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" );
      //这些属性必须在loadMulLine前
      SpecGrid.mulLineCount = 1;   
      SpecGrid.displayTitle = 0;
      SpecGrid.hiddenPlus = 1;
      SpecGrid.hiddenSubtraction = 1;
      SpecGrid.loadMulLine(iArray);           
      //这些操作必须在loadMulLine后面
      //SpecGrid.setRowColData(1,1,"asdf");
      SpecGrid.setRowColData(0,1,"1");
      SpecGrid.setRowColData(0,2,"1");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//责任列表
function initDutyGrid()
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
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="50px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="责任名称";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保费";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保额";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="交费年期";         			//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="年期单位";         			//列名
      iArray[6][1]="40px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许


      iArray[7]=new Array();
      iArray[7][0]="领取年期";         			//列名
      iArray[7][1]="40px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="年期单位";         			//列名
      iArray[8][1]="40px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;             			//是否允许输入,1表示允许，0表示不允许


      iArray[9]=new Array();
      iArray[9][0]="保险年期";         			//列名
      iArray[9][1]="40px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="年期单位";         			//列名
      iArray[10][1]="40px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="交费方式";         			//列名
      iArray[11][1]="40px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[12]=new Array();
      iArray[12][0]="管理费比例";         			//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=1;              			//是否允许输入,1表示允许，0表示不允许
 
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //这些属性必须在loadMulLine前
      DutyGrid.mulLineCount = 0;   
      DutyGrid.displayTitle = 1;
      DutyGrid.canChk = 1;
      DutyGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


//保费项列表
function initPremGrid()
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
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="交费编码";         			//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="交费名称";         			//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保费";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="分配比例";         			//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="关联帐户号";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许


      iArray[7]=new Array();
      iArray[7][0]="帐户分配比率";         			//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[8]=new Array();
      iArray[8][0]="管理费比例";         			//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
	  
     
        if(typeof(window.spanPremGrid) == "undefined" )
        {
              //alert("out");
              return false;
        }
        else
        {
      			
      			
      			
      			//alert("in");
      	    PremGrid = new MulLineEnter( "fm" , "PremGrid" );
	  	    //这些属性必须在loadMulLine前
	 	     PremGrid.mulLineCount = 0;   
	 	     PremGrid.displayTitle = 1;
	 	     PremGrid.canChk = 1;
	 	     PremGrid.loadMulLine(iArray);  
     	 }

      
     }
     catch(ex)
    {
		return false;
    }
    return true;
}



function emptyForm() {
	//emptyFormElements();     //清空页面所有输入框，在COMMON。JS中实现

	//initInpBox();
	//initSelBox();	
	<%for(int i=1;i<=3;i++){%>		
		initBnf<%=i%>Grid();
		initDealType<%=i%>Grid();		
		initRisk<%=i%>1Grid();
		initRisk<%=i%>2Grid();
		initRisk<%=i%>3Grid();
	<%}%>
	initImpartGrid();
	initSpecGrid();
	initSubInsuredGrid();
	//initDutyGrid();
	//initPremGrid();
}

function initForm() {
	try	{   
		
		if (loadFlag == "3"||loadFlag == "4") {	//个人投保单明细查询
			//var tPrtNo = top.opener.parent.VD.gVSwitch.getVar( "PrtNo" );
			//alert("top.opener.parent"+top.opener.parent.location);
			
			//alert("tPrtNo:"+tPrtNo);
//			var tSql = " select bussno from bpomissionstate where bussno='"+prtNo+"' and dealtype <>'05' and State is not null and State!='0'";
		    
			 var sqlid71="WbProposalInputSql71";
			  var mySql71=new SqlClass();
			  mySql71.setResourceName("app.WbProposalInputSql");
			  mySql71.setSqlId(sqlid71);//指定使用SQL的id
			  mySql71.addSubPara(prtNo);//指定传入参数
			  var tSql = mySql71.getString();
			var arrResult = easyExecSql( tSql );
			if (arrResult != null) 
			{
				document.all("ProposalNo").value = arrResult[0][0];
				//alert(document.all("ProposalNo").value);
				document.all("save").disabled=true; //已保存，不能重新保存
				document.all("deleteCont").disabled=false; //可以删除保存内容并重新保存
			}
			if(document.all("ProposalNo").value==null || document.all("ProposalNo").value=="")			
				queryPolDetail();
			else
			{
				queryAllContInfo();
				if (loadFlag == 3 || loadFlag == 4) {
			    divButton.style.display = "none";
			    //增加随动处理
			    try { setFocus(); } catch(e) {}
			  }
			  
			  //复核修改、问题修改
			  if (loadFlag == 3) {
			    inputButton.style.display = ""; 
			    inputQuest.style.display = "";
			    divButton.style.display = "";    
			    deleteButton.style.display="";
			    quaryagentgroup();
			    quaryOccupationType();		    
			    
			  }
			  
			  //复核抽查
			  if (loadFlag == 4) {
			    inputButton.style.display = ""; 
			    inputQuest.style.display = "";
			    divButton.style.display = "";    
			    deleteButton.style.display="none";
			    quaryagentgroup();
			    quaryOccupationType();		   
			    
			  }
			  document.all('BussNoType').value = BussNoType;  
		   }
		} 
	} catch(ex) {
	}			
}

 

</script>
