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
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  
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
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许


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
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      SubInsuredGrid = new MulLineEnter( "fm" , "SubInsuredGrid" ); 
      //这些属性必须在loadMulLine前
      SubInsuredGrid.mulLineCount = 1;   
      SubInsuredGrid.displayTitle = 1;
      //SubInsuredGrid.tableWidth = 200;
      SubInsuredGrid.hiddenPlus = 1;

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
function initBnfGrid() {                               
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="序号"; 			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";		//列宽
    iArray[0][2]=10;			//列最大值
    iArray[0][3]=0;			//是否允许输入,1表示允许，0表示不允许
  
    iArray[1]=new Array();
    iArray[1][0]="受益人类别"; 		//列名
    iArray[1][1]="80px";		//列宽
    iArray[1][2]=40;			//列最大值
    iArray[1][3]=0;			//是否允许输入,1表示允许，0表示不允许

   
    iArray[2]=new Array();
    iArray[2][0]="受益人姓名"; 	//列名
    iArray[2][1]="80px";		//列宽
    iArray[2][2]=40;			//列最大值
    iArray[2][3]=0;			//是否允许输入,1表示允许，0表示不允许
   
    iArray[3]=new Array();
    iArray[3][0]="性别"; 	//列名
    iArray[3][1]="30px";		//列宽
    iArray[3][2]=100;			//列最大值
    iArray[3][3]=0;			//是否允许输入,1表示允许，0表示不允许

  
    iArray[4]=new Array();
    iArray[4][0]="证件类型"; 		//列名
    iArray[4][1]="60px";		//列宽
    iArray[4][2]=80;			//列最大值
    iArray[4][3]=0;			//是否允许输入,1表示允许，0表示不允许

  
    iArray[5]=new Array();
    iArray[5][0]="证件号码"; 		//列名
    iArray[5][1]="150px";		//列宽
    iArray[5][2]=80;			//列最大值
    iArray[5][3]=0;			//是否允许输入,1表示允许，0表示不允许
   
  
    iArray[6]=new Array();
    iArray[6][0]="出生日期"; 		//列名
    iArray[6][1]="80px";		//列宽
    iArray[6][2]=80;			//列最大值
    iArray[6][3]=0;			//是否允许输入,1表示允许，0表示不允许

  
    iArray[7]=new Array();
    iArray[7][0]="与被保人关系"; 	//列名
    iArray[7][1]="90px";		//列宽
    iArray[7][2]=100;			//列最大值
    iArray[7][3]=0;			//是否允许输入,1表示允许，0表示不允许

  
    iArray[8]=new Array();
    iArray[8][0]="受益比例"; 		//列名
    iArray[8][1]="60px";		//列宽
    iArray[8][2]=80;			//列最大值
    iArray[8][3]=0;			//是否允许输入,1表示允许，0表示不允许
   
  
    iArray[9]=new Array();
    iArray[9][0]="受益顺序"; 		//列名
    iArray[9][1]="60px";		//列宽
    iArray[9][2]=80;			//列最大值
    iArray[9][3]=0;			//是否允许输入,1表示允许，0表示不允许

  
    iArray[10]=new Array();
    iArray[10][0]="联系地址"; 		//列名
    iArray[10][1]="400px";		//列宽
    iArray[10][2]=240;			//列最大值
    iArray[10][3]=0;			//是否允许输入,1表示允许，0表示不允许0

  
    iArray[11]=new Array();
    iArray[11][0]="邮编"; 		//列名
    iArray[11][1]="60px";		//列宽
    iArray[11][2]=80;			//列最大值
    iArray[11][3]=0;			//是否允许输入,1表示允许，0表示不允许

  
    iArray[12]=new Array();
    iArray[12][0]="电话"; 		//列名
    iArray[12][1]="100px";		//列宽
    iArray[12][2]=80;			//列最大值
    iArray[12][3]=0;			//是否允许输入,1表示允许，0表示不允许

  
    BnfGrid = new MulLineEnter( "fm" , "BnfGrid" ); 
    //这些属性必须在loadMulLine前
    BnfGrid.mulLineCount = 1; 
    BnfGrid.displayTitle = 1;
    BnfGrid.hiddenPlus = 1;

    BnfGrid.loadMulLine(iArray); 

  } catch(ex) {
    alert(ex);
  }
}

// 告知信息列表的初始化
function initImpartGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知客户类型";         		//列名
      iArray[1][1]="90px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许



      iArray[3]=new Array();
      iArray[3][0]="告知版别";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许



      iArray[4]=new Array();
      iArray[4][0]="告知内容";         		//列名
      iArray[4][1]="360px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="填写内容";         		//列名
      iArray[5][1]="200px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 1;   
      ImpartGrid.displayTitle = 1;
      ImpartGrid.hiddenPlus = 1;

      ImpartGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 特别约定信息列表的初始化
function initSpecGrid() {                               
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
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
      iArray[3][1]="740px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" );
      //这些属性必须在loadMulLine前
      SpecGrid.mulLineCount = 1;   
      SpecGrid.displayTitle = 1;
      SpecGrid.hiddenPlus = 1;

      SpecGrid.loadMulLine(iArray);           
      //这些操作必须在loadMulLine后面
      //SpecGrid.setRowColData(1,1,"asdf");
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
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="责任名称";         			//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保费";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保额";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="交费年期";         			//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="领取年期";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //这些属性必须在loadMulLine前
      DutyGrid.mulLineCount = 0;   
      DutyGrid.displayTitle = 1;
      DutyGrid.hiddenPlus = 1;
      DutyGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


function emptyForm() {
	emptyFormElements();     //清空页面所有输入框，在COMMON。JS中实现
	
	//initInpBox();
	//initSelBox();    
	initSubInsuredGrid();
	initBnfGrid();
	initImpartGrid();
	initSpecGrid();
	initDutyGrid();
	
}

function initForm() {
	try	{
	    
	     
		if (loadFlag == "2") {	//集体下个人投保单录入
			var tRiskCode = parent.VD.gVSwitch.getVar( "RiskCode" );
			getRiskInput( tRiskCode, loadFlag );
			
		}
		
		if (loadFlag == "3") {	//个人保单明细查询
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			//alert("polno:"+tPolNo);
			queryPolDetail( tPolNo );
			
		}

		if (loadFlag == "4") {	//集体下个人保单明细查询
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			
            //alert("polno:"+tPolNo);
			queryPolDetail( tPolNo );
		}

		if (loadFlag == "5") {	//个人投保单明细查询
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}
		if (loadFlag == "6") {	//个人销户保单明细查询
		   	var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryDescPolDetail( tPolNo );
		}
		if (loadFlag == "7") {	//集体下个人销户保单明细查询
		    var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryDescPolDetail( tPolNo );
		}
		
	} catch(ex) {
	}			
}

//取得集体投保单的信息
function getGrpPolInfo()
{

	try { document.all( 'ContNo' ).value = parent.VD.gVSwitch.getVar( "ContNo" ); } catch(ex) { };
	try { document.all( 'RiskCode' ).value = parent.VD.gVSwitch.getVar( "RiskCode" ); } catch(ex) { };
	try { document.all( 'RiskVersion' ).value = parent.VD.gVSwitch.getVar( "RiskVersion" ); } catch(ex) { };
	try { document.all( 'CValiDate' ).value = parent.VD.gVSwitch.getVar( "CValiDate" ); } catch(ex) { };

	try { document.all( 'PrtNo' ).value = parent.VD.gVSwitch.getVar( "PrtNo" ); } catch(ex) { };
	try { document.all( 'GrpPolNo' ).value = parent.VD.gVSwitch.getVar( "GrpProposalNo" ); } catch(ex) { };

	try { document.all( 'ManageCom' ).value = parent.VD.gVSwitch.getVar( "ManageCom" ); } catch(ex) { };
	try { document.all( 'SaleChnl' ).value = parent.VD.gVSwitch.getVar( "SaleChnl" ); } catch(ex) { };
	try { document.all( 'AgentCom' ).value = parent.VD.gVSwitch.getVar( "AgentCom" ); } catch(ex) { };
	try { document.all( 'AgentCode' ).value = parent.VD.gVSwitch.getVar( "AgentCode" ); } catch(ex) { };
	try { document.all( 'AgentGroup' ).value = parent.VD.gVSwitch.getVar( "AgentGroup" ); } catch(ex) { };
	try { document.all( 'AgentCode1' ).value = parent.VD.gVSwitch.getVar( "AgentCode1" ); } catch(ex) { };

	try { document.all('AppGrpNo').value = parent.VD.gVSwitch.getVar('GrpNo'); } catch(ex) { };
	try { document.all('Password').value = parent.VD.gVSwitch.getVar('Password'); } catch(ex) { };
	try { document.all('AppGrpName').value = parent.VD.gVSwitch.getVar('GrpName'); } catch(ex) { };
	try { document.all('AppGrpAddressCode').value = parent.VD.gVSwitch.getVar('GrpAddressCode'); } catch(ex) { };
	try { document.all('AppGrpAddress').value = parent.VD.gVSwitch.getVar('GrpAddress'); } catch(ex) { };
	try { document.all('AppGrpZipCode').value = parent.VD.gVSwitch.getVar('GrpZipCode'); } catch(ex) { };
	try { document.all('BusinessType').value = parent.VD.gVSwitch.getVar('BusinessType'); } catch(ex) { };
	try { document.all('GrpNature').value = parent.VD.gVSwitch.getVar('GrpNature'); } catch(ex) { };
	try { document.all('Peoples').value = parent.VD.gVSwitch.getVar('Peoples'); } catch(ex) { };
	try { document.all('RgtMoney').value = parent.VD.gVSwitch.getVar('RgtMoney'); } catch(ex) { };
	try { document.all('Asset').value = parent.VD.gVSwitch.getVar('Asset'); } catch(ex) { };
	try { document.all('NetProfitRate').value = parent.VD.gVSwitch.getVar('NetProfitRate'); } catch(ex) { };
	try { document.all('MainBussiness').value = parent.VD.gVSwitch.getVar('MainBussiness'); } catch(ex) { };
	try { document.all('Corporation').value = parent.VD.gVSwitch.getVar('Corporation'); } catch(ex) { };
	try { document.all('ComAera').value = parent.VD.gVSwitch.getVar('ComAera'); } catch(ex) { };
	try { document.all('LinkMan1').value = parent.VD.gVSwitch.getVar('LinkMan1'); } catch(ex) { };
	try { document.all('Department1').value = parent.VD.gVSwitch.getVar('Department1'); } catch(ex) { };
	try { document.all('HeadShip1').value = parent.VD.gVSwitch.getVar('HeadShip1'); } catch(ex) { };
	try { document.all('Phone1').value = parent.VD.gVSwitch.getVar('Phone1'); } catch(ex) { };
	try { document.all('E_Mail1').value = parent.VD.gVSwitch.getVar('E_Mail1'); } catch(ex) { };
	try { document.all('Fax1').value = parent.VD.gVSwitch.getVar('Fax1'); } catch(ex) { };
	try { document.all('LinkMan2').value = parent.VD.gVSwitch.getVar('LinkMan2'); } catch(ex) { };
	try { document.all('Department2').value = parent.VD.gVSwitch.getVar('Department2'); } catch(ex) { };
	try { document.all('HeadShip2').value = parent.VD.gVSwitch.getVar('HeadShip2'); } catch(ex) { };
	try { document.all('Phone2').value = parent.VD.gVSwitch.getVar('Phone2'); } catch(ex) { };
	try { document.all('E_Mail2').value = parent.VD.gVSwitch.getVar('E_Mail2'); } catch(ex) { };
	try { document.all('Fax2').value = parent.VD.gVSwitch.getVar('Fax2'); } catch(ex) { };
	try { document.all('Fax').value = parent.VD.gVSwitch.getVar('Fax'); } catch(ex) { };
	try { document.all('Phone').value = parent.VD.gVSwitch.getVar('Phone'); } catch(ex) { };
	try { document.all('GetFlag').value = parent.VD.gVSwitch.getVar('GetFlag'); } catch(ex) { };
	try { document.all('Satrap').value = parent.VD.gVSwitch.getVar('Satrap'); } catch(ex) { };
	try { document.all('EMail').value = parent.VD.gVSwitch.getVar('EMail'); } catch(ex) { };
	try { document.all('FoundDate').value = parent.VD.gVSwitch.getVar('FoundDate'); } catch(ex) { };
	try { document.all('BankAccNo').value = parent.VD.gVSwitch.getVar('BankAccNo'); } catch(ex) { };
	try { document.all('BankCode').value = parent.VD.gVSwitch.getVar('BankCode'); } catch(ex) { };
	try { document.all('GrpGroupNo').value = parent.VD.gVSwitch.getVar('GrpGroupNo'); } catch(ex) { };
	try { document.all('State').value = parent.VD.gVSwitch.getVar('State'); } catch(ex) { };
	try { document.all('Remark').value = parent.VD.gVSwitch.getVar('Remark'); } catch(ex) { };
	try { document.all('BlacklistFlag').value = parent.VD.gVSwitch.getVar('BlacklistFlag'); } catch(ex) { };
	
}

</script>
